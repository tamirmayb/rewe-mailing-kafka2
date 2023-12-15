package com.rewe.emails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * MultiPartitionMessaging uses Spring Boot CommandLineRunner to send 300 consecutive messages to 10 partition of Kafka server
 */
@Component
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private Producer sender;

    @Value("${kafka.topic.reweEmailsTopic}")
    private String topicName;

    public void execute() {
        LOGGER.info("MultiPartitionMessagingExample is executing...");
        for (int i = 0; i < 30; ++i) {
            for(int partitionKey = 1; partitionKey<=10; ++ partitionKey) {
                sender.send(topicName, "key"+partitionKey, "MultiPartitionMessaging - Message No = " +partitionKey+"-"+i);
            }
        }
    }

    @Bean
    public Producer multiPartitionMessageProducer(){
        return new Producer();
    }

    @Bean
    public Consumer multiPartitionMessageConsumer(){
        return new Consumer();
    }
}
