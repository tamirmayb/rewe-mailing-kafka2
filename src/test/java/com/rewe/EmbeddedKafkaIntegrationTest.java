package com.rewe;

import com.rewe.emails.KafkaEmailConsumer;
import com.rewe.emails.KafkaEmailProducer;
import com.rewe.models.Email;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 3, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private KafkaEmailConsumer consumer;
    @Autowired
    private KafkaEmailProducer producer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        String data = "Sending with our own simple KafkaProducer";

        Email email = new Email("aaa@gmail.com", List.of("support@rewe.de"), "sub", "con");
        producer.send(topic, email);

//        consumer.handleEmailFromGoogle(email.toString());
        assertThat(consumer.toString(), containsString(data));

        verify(consumer, times(1)).handleEmailFromGoogle(String.valueOf(email));

    }
}