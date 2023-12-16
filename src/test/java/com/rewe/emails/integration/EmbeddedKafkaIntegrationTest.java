package com.rewe.emails.integration;

import com.rewe.emails.KafkaEmailConsumer;
import com.rewe.emails.KafkaEmailProducer;
import com.rewe.models.Email;
import com.rewe.models.Statistics;
import com.rewe.repository.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 3, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private KafkaEmailConsumer consumer;

    @Autowired
    private KafkaEmailProducer producer;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Value("${kafka.topic.reweEmailsTopic}")
    private String topic;

    // 1 test per domain send each domain
    @Test
    public void givenEmbeddedKafkaBroker_whenSendingEmailFromYahoo_thenMessageReceivedAndStatisticsUpdated() throws InterruptedException {

        Email email = new Email("aaa@yahoo.com", List.of("support@rewe.de"), "sub", "con");
        producer.send(topic, email);

        //waiting for message to be consumed and saved
        Thread.sleep(3000);
        Optional<Statistics> byDomain = statisticsRepository.findByDomain("yahoo.com");

        assertTrue(byDomain.isPresent());
        assertEquals(byDomain.get().getCount(), 1);

        producer.send(topic, email);

        Thread.sleep(3000);
        Optional<Statistics> byDomain2 = statisticsRepository.findByDomain("yahoo.com");

        assertTrue(byDomain2.isPresent());
        assertEquals(byDomain2.get().getCount(), 2);

    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingEmailFromOther_thenMessageReceivedAndStatisticsUpdated() throws InterruptedException {

        Email email = new Email("aaa@amazon.com", List.of("support@rewe.de"), "sub", "con");
        producer.send(topic, email);

        //waiting for message to be consumed and saved
        Thread.sleep(3000);
        Optional<Statistics> byDomain = statisticsRepository.findByDomain("Other");

        assertTrue(byDomain.isPresent());
        assertEquals(byDomain.get().getCount(), 1);

        producer.send(topic, email);

        Thread.sleep(3000);
        Optional<Statistics> byDomain2 = statisticsRepository.findByDomain("Other");

        assertTrue(byDomain2.isPresent());
        assertEquals(byDomain2.get().getCount(), 2);

    }
}