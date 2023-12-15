package com.rewe.emails;

import com.rewe.models.Statistics;
import com.rewe.repository.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Optional;

public class KafkaEmailConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaEmailConsumer.class);

    @Autowired
    private StatisticsRepository statisticsRepository;

    @KafkaListener(id = "listener to google", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = { "0" })})
    public void handleEmailFromGoogle(@Payload String email) {
        saveStatistics("google.com");
        log.info("handleEmailFromGoogle ===> " + email);
    }

    @KafkaListener(id = "listener to yahoo", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = { "1" })})
    public void handleEmailFromYahoo(@Payload String email) {
        saveStatistics("yahoo.com");
        log.info("handleEmailFromYahoo ===> " + email);
    }

    @KafkaListener(id = "listener to all other", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = { "2" })})
    public void handleEmailFromOther(@Payload String email) {
        saveStatistics("Other");
        log.info("handleEmailFromOther ===> " + email);
    }

    private void saveStatistics(String domain) {
        Optional<Statistics> byDomain = statisticsRepository.findByDomain(domain);
        Statistics statistics = byDomain.orElse(new Statistics(domain));
        statistics.setCount(statistics.getCount() + 1);
        statisticsRepository.save(statistics);
    }
}
