package com.rewe;

import com.rewe.emails.KafkaEmailConsumer;
import com.rewe.emails.KafkaEmailProducer;
import com.rewe.models.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class EmailSender {

    @Autowired
    private KafkaEmailProducer sender;

    @Value("${kafka.topic.reweEmailsTopic}")
    private String topicName;

    private final static List<String> EMAIL_DOMAINS_FOR_TESTS = Arrays.asList("gmail.com", "yahoo.com", "amazon.com");

    public void execute() {
        log.info("Sending 30 emails is executing...");

        for (int i = 0; i < 30; ++i) {
            String domain = EMAIL_DOMAINS_FOR_TESTS.get(new Random().nextInt(EMAIL_DOMAINS_FOR_TESTS.size()));
            String randomFrom = UUID.randomUUID() + "@" + domain;

            Email email = Email.builder()
                    .withFrom(randomFrom)
                    .withTo(List.of("support@rewe.de"))
                    .withSubject("need help")
                    .withContent("I need your help please reply to " + randomFrom)
                    .build();

            sender.send(topicName, email);
        }
    }

    @Bean
    public KafkaEmailProducer multiPartitionMessageProducer(){
        return new KafkaEmailProducer();
    }

    @Bean
    public KafkaEmailConsumer multiPartitionMessageConsumer(){
        return new KafkaEmailConsumer();
    }
}
