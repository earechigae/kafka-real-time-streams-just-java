package guru.learningjournal.kafka.examples;


import guru.learningjournal.kafka.examples.serde.JsonSerializer;
import guru.learningjournal.kafka.examples.types.PosInvoice;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PosSimulator {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String topicName = null;
        int noOfProducers = 0;
        int produceSpeed = 0;
        Properties properties = new Properties();

        if(args.length < 3){
            System.out.println("Please provide command line arguments: topicName noOfProducers produceSpeed");
            System.exit(-1);
        }
        topicName = args[0];
        noOfProducers = 0;
        produceSpeed = 0;

        try{
            noOfProducers = Integer.parseInt(args[1]);
        }catch(NumberFormatException nfe){
            System.out.println("The number of producers must be an integer value. Not: " + args[1]);
            System.exit(-1);
        }

        try{
            produceSpeed = Integer.parseInt(args[2]);
        }catch(NumberFormatException nfe){
            System.out.println("The produce speed must be an integer value. Not: " + args[2]);
            System.exit(-1);
        }

        if (noOfProducers < 1 || noOfProducers > 10) {
            System.out.println("The number of producers must be a number between 1 and 10. Not: " + args[1]);
            System.exit(-1);
        }

        if (produceSpeed < 10 || produceSpeed > 99999) {
            System.out.println("The produce speed must be a number between 10 and 99999 milliseconds. Not: " + args[2]);
            System.exit(-1);
        }

        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfig.applicationId);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        KafkaProducer<String, PosInvoice> kafkaProducer = new KafkaProducer<String, PosInvoice>(properties);
        ExecutorService executor = Executors.newFixedThreadPool(noOfProducers);
        final List<RunnableProducer> runnableProducers = new ArrayList<>();

        for(int i = 0; i < noOfProducers; i++){
            RunnableProducer runnableProducer = new RunnableProducer(i, kafkaProducer, topicName, produceSpeed);
            runnableProducers.add(runnableProducer);
            executor.submit(runnableProducer);
        }

        logger.info("******  Press Ctrl + C to shut down threads gracefully ... ***** ");
        final int finalProduceSpeed = produceSpeed;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for(RunnableProducer rp : runnableProducers){
                rp.shutdown();
            }
            executor.shutdown();
            logger.info("Closing executor service");
            try{
                executor.awaitTermination(finalProduceSpeed * 2, TimeUnit.MILLISECONDS);
            }catch(InterruptedException ie){
                throw new RuntimeException(ie);
            }
        }));
    }
}
