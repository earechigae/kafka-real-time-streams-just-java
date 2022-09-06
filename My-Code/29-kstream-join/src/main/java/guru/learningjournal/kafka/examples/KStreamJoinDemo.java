package guru.learningjournal.kafka.examples;

import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.PaymentConfirmation;
import guru.learningjournal.kafka.examples.types.PaymentRequest;
import guru.learningjournal.kafka.examples.types.TransactionStatus;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Properties;


public class KStreamJoinDemo {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreName);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 0);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, PaymentRequest> KS0 = streamsBuilder.stream(AppConfigs.paymentRequestTopicName,
            Consumed.with(AppSerdes.String(), AppSerdes.PaymentRequest())
                .withTimestampExtractor(AppTimestampExtractor.PaymentRequest())
        );

        KStream<String, PaymentConfirmation> KS1 = streamsBuilder.stream(AppConfigs.paymentConfirmationTopicName,
            Consumed.with(AppSerdes.String(), AppSerdes.PaymentConfirmation())
                .withTimestampExtractor(AppTimestampExtractor.PaymentConfirmation())
        );

        // Notice it works because both topics use the same key
        KS0.join(
                // KStream to join
                KS1,
                // Condition to join using a value joiner lambda
                (v1KS0, v2KS1) -> new TransactionStatus()
                    .withTransactionID(v1KS0.getTransactionID())
                    .withStatus(v1KS0.getOTP().equals(v2KS1.getOTP())? "Success" : "Failure"),
                // Time window to do the join.
                // Joining KStreams are always time bounded
                JoinWindows.of(Duration.ofMinutes(5)),
                // Define the Serdes. Notice that it receives the Serdes for the common key as the first argument.
                // Also is receives both Streams Serdes for each topic we are joining.
                Joined.with(AppSerdes.String(), AppSerdes.PaymentRequest(), AppSerdes.PaymentConfirmation())
        ).print(Printed.toSysOut());

        logger.info("Starting Stream...");
        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping Streams...");
            streams.close();
        }));

    }
}
