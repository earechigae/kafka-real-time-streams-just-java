package guru.learningjournal.kafka.examples;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class StreamingTableApp {
    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreName);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KTable<String, String> kt0 = streamsBuilder.table(AppConfigs.topicName);
        kt0.toStream().print(Printed.<String, String>toSysOut().withLabel("kt0"));

        KTable<String, String> kt1 = kt0.filter((key, value) -> key.matches(AppConfigs.regExSymbol) && !value.isEmpty(),
                Materialized.as(AppConfigs.stateStoreName));
        kt1.toStream().print(Printed.<String, String>toSysOut().withLabel("kt1"));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);

        //Query server
        QueryServer queryServer = new QueryServer(streams, AppConfigs.queryServerHost, AppConfigs.queryServerPort);
        streams.setStateListener((newState, oldState) -> {
            logger.info("State changing to " + newState + " from " + oldState);
            queryServer.setActive(newState == KafkaStreams.State.RUNNING && oldState == KafkaStreams.State.REBALANCING);
        });

        streams.start();
        queryServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            logger.info("Shutting down servers");
            queryServer.stop();
            streams.close();
        }));
    }
}
