package guru.learningjournal.kafka.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.AdClick;
import guru.learningjournal.kafka.examples.types.AdInventories;
import guru.learningjournal.kafka.examples.types.ClicksByNewsType;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;


public class GKTableJoinDemo {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreName);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 0);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        GlobalKTable<String, AdInventories> GKT0 = streamsBuilder.globalTable(AppConfigs.inventoryTopic,
            Consumed.with(AppSerdes.String(), AppSerdes.AdInventories())
        );

        KStream<String, AdClick> KS1 = streamsBuilder.stream(AppConfigs.clicksTopic,
            Consumed.with(AppSerdes.String(), AppSerdes.AdClick())
        );

        // The purpose of the join is to enrich the click events with the news type information
        // The join works because the keys of the messages in both topics match
        KTable<String, Long> KT1 = KS1.join(
                // KTable to join
                GKT0,
                // Since we are using a Global KTable, the argument takes a KeyValueMapper lambda.
                // This parameter has the capability to return a new Key different to the current KStream.
                // This feature is to join a Global KTable to a foreign key.
                // For this requirement/example we do not need a new different key. For this reason, the same key is returned
                (k, v) -> k,
                // Value joiner lambda.
                // The lambda is triggered if both keys coming from the 2 different topics match.
                (v1KS1, v2GKT0) -> v2GKT0)
        // The outcome of the join is a new KStream.
        // The key k is a String
        // The value is an AdInventory result
        // The output represent clicked inventories!!!
        .groupBy((k, v) -> v.getNewsType(), Grouped.with(AppSerdes.String(), AppSerdes.AdInventories()))
        .count();


        KT1.groupBy((k, v) -> {
                ClicksByNewsType value = new ClicksByNewsType();
                value.setNewsType(k);
                value.setClicks(v);
                // The Key is a fixed String to send the messages to the same partition
                return KeyValue.pair(AppConfigs.top3AggregateKey, value);
            }, Grouped.with(AppSerdes.String(), AppSerdes.ClicksByNewsType()))
        // To keep thing sorted as they arrive we compute an aggregation
        // Remember that with KTables we need to specify a substractor.
        .aggregate(
                // Initializer
                () -> new Top3NewTypes(),
                //Adder
                (k, newV, aggV) -> {
                    aggV.add(newV);
                    return aggV;
                },
                // Substractor
                (k, newV, aggV)  -> {
                    aggV.remove(newV);
                    return aggV;
                },
                // Serialization/Materialization details
                Materialized.<String, Top3NewTypes, KeyValueStore<Bytes, byte[]>>
                        as("top3-clicks")
                        .withKeySerde(AppSerdes.String())
                        .withValueSerde(AppSerdes.Top3NewsTypes())
        ).toStream().foreach((k, v) -> {
            try {
                logger.info("k = " + k + ", v = " + v.getTop3Sorted());
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        });

        logger.info("Starting Stream...");
        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping Streams...");
            streams.close();
        }));

    }
}
