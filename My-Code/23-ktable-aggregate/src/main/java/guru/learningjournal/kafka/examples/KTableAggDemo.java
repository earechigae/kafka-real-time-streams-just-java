package guru.learningjournal.kafka.examples;

import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.DepartmentAggregate;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class KTableAggDemo {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConfigs.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(StreamsConfig.STATE_DIR_CONFIG, AppConfigs.stateStoreLocation);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,100);

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // The .table() specifies that we are going to use a KTable!!! (We are NOT using a KStream)
        streamsBuilder.table(AppConfigs.topicName, Consumed.with(AppSerdes.String(), AppSerdes.Employee()))
                //The groupBy in a KTable returns a KeyValue pair!! Do not confuse it with KStream as it returns a Key
                .groupBy((key, value) -> KeyValue.pair(value.getDepartment(), value), Grouped.with(AppSerdes.String(), AppSerdes.Employee()))
                .aggregate(
                        //Initializer. We need to explicitly tell how to initialize as it does not know the state store types
                        () -> new DepartmentAggregate()
                                .withEmployeeCount(0)
                                .withTotalSalary(0)
                                .withAvgSalary(0D),
                        //Adder Aggregator
                        (key, value, aggregatorValue) -> new DepartmentAggregate()
                                .withEmployeeCount(aggregatorValue.getEmployeeCount() + 1)
                                .withTotalSalary(aggregatorValue.getTotalSalary() + value.getSalary())
                                .withAvgSalary((aggregatorValue.getTotalSalary() + value.getSalary()) / (aggregatorValue.getEmployeeCount() + 1D)),
                        //Subtract Aggregator
                        (key, value, aggregatorValue) -> new DepartmentAggregate()
                                .withEmployeeCount(aggregatorValue.getEmployeeCount() - 1)
                                .withTotalSalary(aggregatorValue.getTotalSalary() - value.getSalary())
                                .withAvgSalary((aggregatorValue.getTotalSalary() - value.getSalary()) / (aggregatorValue.getEmployeeCount() - 1D)),
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
