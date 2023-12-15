package com.rewe.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaTopicConfig {

    @Value("${kafka.servers}")
    private String servers;

    @Value("${kafka.topic.reweEmailsTopic}")
    private String topicName;

    @Value("${emails.domains}")
    private List<String> emailDomains;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic emailTopic() {
        log.info("trying to create topic with name " + topicName);
        NewTopic newTopic = new NewTopic(topicName, emailDomains.size() + 1, (short) 1);
        log.info("Topic with name " + topicName + "created with " + newTopic.numPartitions() + " partitions");
        return newTopic;
    }
}