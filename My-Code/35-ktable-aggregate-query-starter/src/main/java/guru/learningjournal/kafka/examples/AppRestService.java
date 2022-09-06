package guru.learningjournal.kafka.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.learningjournal.kafka.examples.serde.AppSerdes;
import guru.learningjournal.kafka.examples.types.DepartmentAggregate;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.StreamsMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Spark;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppRestService {
    private static final Logger logger = LogManager.getLogger();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaStreams streams;
    private final HostInfo hostInfo;
    private Client client;
    private Boolean isActive = true;
    private final String NOT_FOUND = "{\"code\": 204, \"mesage\": \"No Content\"}";
    private final String SERVICE_UNAVAILABLE = "{\"code\": 503, \"message\": \"Service Unavailable\"}";

    public AppRestService(KafkaStreams streams, String hostName, int port){
        this.streams = streams;
        this.hostInfo = new HostInfo(hostName, port);
        this.client = ClientBuilder.newClient();
    }

    public void setActive(Boolean state){
        this.isActive = state;
    }

    private String getValue(String searchKey) throws JsonProcessingException {
        ReadOnlyKeyValueStore<String, DepartmentAggregate> departmentStore = streams.store(
                AppConfigs.stateStoreName,
                QueryableStoreTypes.keyValueStore());
        DepartmentAggregate result = departmentStore.get(searchKey);

        return (result == null)? NOT_FOUND : objectMapper.writeValueAsString(result);
    }

    private String getValueFromRemote(String searchKey, HostInfo hostInfo) {
        String result;
        String targetHost = String.format("http://%s:%d/kv/%s", hostInfo.host(), hostInfo.port(), searchKey);

        result = client.target(targetHost).request(MediaType.APPLICATION_JSON).get(String.class);
        return result;
    }

    private List<KeyValue<String, DepartmentAggregate>> getAllValues(){
        List<KeyValue<String, DepartmentAggregate>> results = new ArrayList();
        ReadOnlyKeyValueStore<String, DepartmentAggregate> departmentStore = streams.store(
                AppConfigs.stateStoreName,
                QueryableStoreTypes.keyValueStore());
        departmentStore.all().forEachRemaining(results::add);
        return results;
    }

    private List<KeyValue<String, DepartmentAggregate>> getAllValuesFromRemote(HostInfo hostInfo) throws IOException {
        String result;
        List<KeyValue<String, DepartmentAggregate>> results = new ArrayList();
        String targetHost = String.format("http://%s:%d/dept/local", hostInfo.host(), hostInfo.port());

        result = client.target(targetHost).request(MediaType.APPLICATION_JSON).get(String.class);
        if(!result.equalsIgnoreCase(NOT_FOUND)){
            results = objectMapper.readValue(result, results.getClass());
        }
        return results;
    }

    void start() {
        logger.info("Starting KTableAggDemo Query Server: http://" + hostInfo.host() + ":" + hostInfo.port());
        Spark.port(hostInfo.port());

        //Setting the /getAggregate/:deptName REST endpoint to query the information of a particular department
        //Example: http://localhost:7010/getAggregate/123213
        //Example: http://localhost:7010/getAggregate/engineering
        Spark.get("/getAggregate/:deptName", (req, res) -> {
            String results;
            String searchKey = req.params(":deptName");

            logger.info("Request: /getAggregate/" + searchKey);
            if (!isActive) {
                results = SERVICE_UNAVAILABLE;
            } else {
                //The streams API will bring the owner of this Key
                StreamsMetadata metaData = streams.metadataForKey(
                        AppConfigs.stateStoreName, searchKey, AppSerdes.String().serializer());
                if (metaData.hostInfo().equals(hostInfo)) {
                    logger.info("Retrieving key/value from Local ...");
                    results = getValue(searchKey);
                } else {
                    logger.info("Retrieving key/value from Remote ...");
                    results = getValueFromRemote(searchKey, metaData.hostInfo());
                }
            }
            return results;
        });

        //Setting the /getAggregates/all REST endpoint to query the information of all departments
        //Example: http://localhost:7010/getAggregates/all
        Spark.get("/getAggregates/all", (req, res) -> {
            String results;
            List<KeyValue<String, DepartmentAggregate>> allResults = new ArrayList<>();

            logger.info("Request: /getAggregates/all");
            if (!isActive) {
                results = SERVICE_UNAVAILABLE;
            } else {
                Collection<StreamsMetadata> allMetaData = streams.allMetadataForStore(AppConfigs.stateStoreName);
                for (StreamsMetadata metadata : allMetaData) {
                    if (metadata.hostInfo().equals(hostInfo)) {
                        logger.info("Retrieving all key/values pairs from Local ...");
                        allResults.addAll(getAllValues());
                    } else {
                        logger.info("Retrieving all key/values pairs from Remote ...");
                        allResults.addAll(getAllValuesFromRemote(metadata.hostInfo()));
                    }
                }
                results = (allResults.size() == 0) ? NOT_FOUND : objectMapper.writeValueAsString(allResults);
            }

            return results;
        });

        //This function is necessary to avoid an infinite loop
        Spark.get("/getAggregates/local", (req, res) -> {
            String results;
            List<KeyValue<String, DepartmentAggregate>> allResults = new ArrayList<>();

            if (!isActive) {
                results = SERVICE_UNAVAILABLE;
            } else {
                allResults = getAllValues();
                results = (allResults.size() == 0) ? NOT_FOUND : objectMapper.writeValueAsString(allResults);
            }
            return results;
        });
    }

    void stop(){
        client.close();
        Spark.stop();
    }
}
