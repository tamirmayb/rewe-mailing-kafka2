package com.rewe.emails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
public class KafkaEmailConsumer {

    @KafkaListener(id = "listener to google", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = { "0" })})
    public void handleEmailFromGoogle(@Payload String email) {
        log.info("handleEmailFromGoogle ===> " + email);
    }

    @KafkaListener(id = "listener to yahoo", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = { "1" })})
    public void handleEmailFromYahoo(@Payload String email) {
        log.info("handleEmailFromYahoo ===> " + email);
    }

    @KafkaListener(id = "listener to all other", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = { "2" })})
    public void handleEmailFromOther(@Payload String email) {
        log.info("handleEmailFromOther ===> " + email);
    }
}
