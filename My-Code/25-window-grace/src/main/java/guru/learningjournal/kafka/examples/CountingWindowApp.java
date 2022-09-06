package guru.learningjournal.kafka.examples;

import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.SimpleInvoice;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Properties;


public class CountingWindowApp {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreName);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 0);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, SimpleInvoice> KS0 = streamsBuilder.stream(AppConfigs.posTopicName,
            Consumed.with(AppSerdes.String(), AppSerdes.SimpleInvoice())
                .withTimestampExtractor(new InvoiceTimeExtractor())
        );

        KTable<Windowed<String>, Long> KT0 = KS0.groupByKey(Grouped.with(AppSerdes.String(), AppSerdes.SimpleInvoice()))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5))
                // A grace period of 2 minutes drops late comers messages.
                // This grace period controls how long Kafka Streams will wait for out-of-order data records for a window.
                // If a record arrives after the grace period of a window has passed
                // (i.e., record.ts > window-end-time + grace-period),
                // the record is discarded and will not be processed in that window.
                // THE DEFAULT GRACE PERIOD IS OF 24 HRS!!!!!!
                .grace(Duration.ofMinutes(2)))
                .count();

        KT0.toStream().foreach(
            (wKey, value) -> logger.info(
                "Store ID: " + wKey.key() + " Window ID: " + wKey.window().hashCode() +
                    " Window start: " + Instant.ofEpochMilli(wKey.window().start()).atOffset(ZoneOffset.UTC) +
                    " Window end: " + Instant.ofEpochMilli(wKey.window().end()).atOffset(ZoneOffset.UTC) +
                    " Count: " + value
            )
        );


        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping Streams");
            streams.close();
        }));

    }
}
