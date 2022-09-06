package guru.learningjournal.kafka.examples;

import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.DepartmentAggregate;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class KStreamAggDemo {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreLocation);

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // The .stream() specifies that we are going to use a Kafka Stream!! (We are NOT using a KTable)
        streamsBuilder.stream(AppConfigs.topicName,
            Consumed.with(AppSerdes.String(), AppSerdes.Employee()))
                //The group by returns a Key in a KStream and automatically preserves the same value.
                .groupBy((key, value) -> value.getDepartment(), Grouped.with(AppSerdes.String(), AppSerdes.Employee()))
                .aggregate(
                        //Initializer. We need to explicitly tell how to initialize as it does not know the state store types
                        () -> new DepartmentAggregate()
                                .withEmployeeCount(0)
                                .withTotalSalary(0)
                                .withAvgSalary(0D),
                        //Aggregator. This aggregator uses a KStream and not a KTable.
                        // For this reason, it does not take into account negative aggregation scenarios!!!!!!.
                        // In this case a negative aggregation scenario is if a couple of employees swap departments.
                        // Swapping departments means that we may need to update the department an employee is associated.
                        // The KStream keeps adding events to the stream. To update events, we need a KTable!!!!!!!
                        (key, value, aggregatorValue) -> new DepartmentAggregate()
                                .withEmployeeCount(aggregatorValue.getEmployeeCount() + 1)
                                .withTotalSalary(aggregatorValue.getTotalSalary() + value.getSalary())
                                .withAvgSalary((aggregatorValue.getTotalSalary() + value.getSalary()) / (aggregatorValue.getEmployeeCount() + 1D)),
                        //Serializer
                        Materialized.<String, DepartmentAggregate, KeyValueStore<Bytes, byte[]>>as(AppConfigs.stateStoreName)
                                .withKeySerde(AppSerdes.String())
                                .withValueSerde(AppSerdes.DepartmentAggregate())
                ).toStream().print(Printed.<String, DepartmentAggregate>toSysOut().withLabel("Department Salary Average"));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping Streams");
            streams.close();
        }));

    }
}
