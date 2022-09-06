package guru.learningjournal.kafka.examples;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class HelloStreams {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        // Step 1 - Define the configuration properties
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Step 2 - Create the streams topology
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<Integer, String> kStream = streamsBuilder.stream(AppConfigs.topicName);
        kStream.foreach((k, v) -> logger.info("Key = " + k + ". Value = " + v));
        //Also you can use peek function
        //kStream.peek((k, v) -> logger.info("Key = " + k + ". Value = " + v));
        Topology topology = streamsBuilder.build();

        //Step 3 - Start the stream
        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start(); // The start function keeps an infinite loop which will not terminate the application!!!

        //Step 4 - Add a shutdown hook to clean resources gracefully if somebody decides to kill the process (CTRL + C)
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            streams.close();
        }));
    }
}
