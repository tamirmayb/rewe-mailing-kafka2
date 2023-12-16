package com.rewe.emails;

import com.rewe.models.Email;
import com.rewe.models.Statistics;
import com.rewe.repository.StatisticsRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;
import java.util.Optional;

public class KafkaEmailConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaEmailConsumer.class);

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Value("${emails.domains}")
    private List<String> emailDomains;

    @KafkaListener(id = "listener to google", groupId = "kafkaReweEmailsTopicsGroup",
            topicPartitions = { @TopicPartition(topic = "${kafka.topic.reweEmailsTopic}", partitions = "${kafka.topic.partitions}")})
    public void handleEmail(@Payload Email email,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {

        if (partition >= emailDomains.size()) {
            handleEmailFromOther(email);
        } else {
            String domain = emailDomains.get(partition);
            if (validateDomain(domain, email)) {
                saveStatistics(domain);
                log.info("handleEmailFrom ===> " + domain + " " + email);
            } else {
                log.error("Invalid domain for partition expecting " + domain + " got " + email);
            }
        }
    }

    private void handleEmailFromOther(Email email) {
        if(validateOtherDomain(email)) {
            saveStatistics("Other");
            log.info("handleEmailFromOther ===> " + email);
        } else {
            log.error("Invalid other domain for partition expecting valid domains that are not: " + emailDomains + " got " + email);
        }
    }

    private void saveStatistics(String domain) {
        Optional<Statistics> byDomain = statisticsRepository.findByDomain(domain);
        Statistics statistics = byDomain.orElse(new Statistics(domain));
        statistics.setCount(statistics.getCount() + 1);
        statisticsRepository.save(statistics);
    }

    private boolean validateDomain(String domain, Email email) {
        if (EmailValidator.getInstance().isValid(email.getFrom())) {
            return email.getFrom().endsWith(domain);
        }
        return false;
    }

    private boolean validateOtherDomain(Email email) {
        if (EmailValidator.getInstance().isValid(email.getFrom())) {
            return !emailDomains.contains(email.getFrom().split("@")[1]);
        }
        return false;
    }
}
