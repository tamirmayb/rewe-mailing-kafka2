package com.rewe.emails;

import com.rewe.models.Email;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.List;

public class KafkaEmailProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaEmailProducer.class);

    @Autowired
    private KafkaTemplate<String, Email> kafkaTemplate;

    @Value("${emails.domains}")
    private List<String> emailDomains;

    public void send(String topic, Email email) {
        int partition = getPartitionByDomain(email);
        if(partition > -1) {
            ListenableFuture<SendResult<String, Email>> future = kafkaTemplate.send(topic, partition, "", email);
            SuccessCallback<SendResult<String, Email>> successCallback = sendResult -> log.info("Sent email='{}' to topic='{}', partition = {}", email, topic, partition);
            FailureCallback failureCallback = throwable -> log.info("Could not send email='{}' to topic='{}' error was: '{}'", topic, email, throwable.getMessage());
            future.addCallback(successCallback, failureCallback);
        }
    }

    private int getPartitionByDomain(Email email) {
        if (EmailValidator.getInstance().isValid(email.getFrom())) {
            String domain = email.getFrom().split("@")[1];
            int foundIndex = emailDomains.indexOf(domain);
            if (foundIndex > -1) {
                return foundIndex;
            }
            return emailDomains.size();
        }
        return -1;
    }
}
