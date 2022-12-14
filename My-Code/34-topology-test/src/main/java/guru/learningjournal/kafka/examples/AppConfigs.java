package guru.learningjournal.kafka.examples;

class AppConfigs {

    public final static String applicationID = "AdvertCTRDemo";
    public final static String bootstrapServers = "localhost:9092,localhost:9093";
    public final static String impressionTopic = "ad-impressions";
    public final static String clicksTopic = "ad-clicks";
    public final static String stateStoreName = "tmp/state-store";
    public final static String stateStoreLocationUT = "tmp/state-store-unit-test";
    public final static String stateStoreNameCP = "state-store-campaign-performance";
    public final static String outputTopic = "ad-impressions-output";
}
