import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

public class WordCountApp {
    private static final Logger logger = LogManager.getLogger();
    public static void main(final String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreLocation);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> ks0 = streamsBuilder.stream(AppConfigs.topicName);
        KStream<String, String> ks1 = ks0.flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")));

        //Grouping by other attribute which is not the key. I this key grouping by the value
        KGroupedStream<String, String> kgs1 = ks1.groupBy((key, value) -> value);
        //After grouping we count the number of words
        KTable<String, Long> kt1 = kgs1.count();
        //We print the KTable content
        kt1.toStream().print(Printed.<String, Long>toSysOut().withLabel("KT1"));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook( new Thread( () -> {
            logger.info("Shutting down stream");
            streams.close();
        }));
    }
}
