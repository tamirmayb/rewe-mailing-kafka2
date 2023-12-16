package com.rewe.services;

import com.rewe.emails.KafkaEmailConsumer;
import com.rewe.emails.KafkaEmailProducer;
import com.rewe.models.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class EmailSenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private KafkaEmailProducer sender;

    @Value("${kafka.topic.reweEmailsTopic}")
    private String topicName;

    private final static List<String> EMAIL_DOMAINS_FOR_TESTS = Arrays.asList("gmail.com", "yahoo.com", "amazon.com");

    public void execute(int numOfEmailToSend) {
        log.info("Sending {} emails is executing...", numOfEmailToSend);

        for (int i = 0; i < numOfEmailToSend; ++i) {
            String domain = EMAIL_DOMAINS_FOR_TESTS.get(new Random().nextInt(EMAIL_DOMAINS_FOR_TESTS.size()));
            String randomFrom = UUID.randomUUID() + "@" + domain;

            Email email = new Email(randomFrom, List.of("support@rewe.de"),
                    "need help", "I need your help please reply to " + randomFrom);

            sender.send(topicName, email);
        }
    }

    @Bean
    public KafkaEmailProducer kafkaEmailProducer(){
        return new KafkaEmailProducer();
    }

    @Bean
    public KafkaEmailConsumer kafkaEmailConsumer(){
        return new KafkaEmailConsumer();
    }
}
