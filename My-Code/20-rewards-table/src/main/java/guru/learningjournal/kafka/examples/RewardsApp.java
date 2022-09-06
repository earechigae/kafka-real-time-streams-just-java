package guru.learningjournal.kafka.examples;

import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.Notification;
import guru.learningjournal.kafka.examples.types.PosInvoice;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class RewardsApp {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,0);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Notification> ks0 = builder.stream(AppConfigs.posTopicName,
            Consumed.with(AppSerdes.String(), AppSerdes.PosInvoice()))
                .filter((key, value) -> value.getCustomerType().equalsIgnoreCase(AppConfigs.CUSTOMER_TYPE_PRIME))
                //The map() method is necessary to change the Key from StoreId to CustomerId.
                //This will automatically repartition the stream based on the CustomerId
                .map((key, invoice) -> new KeyValue<>(
                        invoice.getCustomerCardNo(),
                        Notifications.getNotificationFrom(invoice)
                ));

        //The groupByKey() uses a temporary state store. Therefore, we need to specify the appropriate Serdes.
        KGroupedStream<String, Notification> kgs0 = ks0.groupByKey(Grouped.with(AppSerdes.String(), AppSerdes.Notification()));
        //The reduce() method calculates the customer's total rewards.
        //Keep in mind the reduce() method does not allow to change the type of the stream.
        //The aggregateValue is the accumulated value for the state store.
        // The new value is the current value of this notification event.
        KTable<String, Notification> kt0 = kgs0.reduce( (aggregateValue, newValue) -> {
            newValue.setTotalLoyaltyPoints(newValue.getEarnedLoyaltyPoints() + aggregateValue.getTotalLoyaltyPoints());
            return newValue;
        });

        //Push the notification to the notification topic
        kt0.toStream().to(AppConfigs.notificationTopic, Produced.with(AppSerdes.String(), AppSerdes.Notification()));

        logger.info("Starting Stream");
        KafkaStreams stream = new KafkaStreams(builder.build(), props);
        stream.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping Streams");
            stream.cleanUp();
        }));
    }
}
