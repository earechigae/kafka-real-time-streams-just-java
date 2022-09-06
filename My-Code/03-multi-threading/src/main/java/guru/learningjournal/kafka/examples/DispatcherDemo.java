package guru.learningjournal.kafka.examples;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DispatcherDemo {
    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(AppConfigs.kafkaConfigFilelocation);
            props.load(inputStream);
            props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        }catch(IOException e){
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
            }catch(IOException ioe){
            }
        }

        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(props);
        Thread dispatchers[] = new Thread[AppConfigs.eventFiles.length];
        logger.info("Starting dispatcher threads ...");
        for(int i = 0; i < dispatchers.length; i++){
            dispatchers[i] = new Thread(new Dispatcher(producer, AppConfigs.topicName, AppConfigs.eventFiles[i]));
            dispatchers[i].start();
        }

        try{
            for(Thread t: dispatchers){
                t.join();
            }
        }catch(InterruptedException ie){
           logger.error("Main Thread Interrupted");
        }finally {
            producer.close();
            logger.info("Finished Dispatcher Demo");
        }
    }
}
