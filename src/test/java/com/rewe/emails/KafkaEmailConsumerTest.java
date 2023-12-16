package com.rewe.emails;

import com.rewe.models.Email;
import com.rewe.models.Statistics;
import com.rewe.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEmailConsumerTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private KafkaEmailConsumer consumer;

    @Mock
    private Email mockEmail;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(consumer, "emailDomains", List.of("gmail.com", "yahoo.com"));
    }

    @Test
    public void shouldSaveYahooEmailStats() {
        when(mockEmail.getFrom()).thenReturn("aaa@yahoo.com");
        when(statisticsRepository.findByDomain("yahoo.com")).thenReturn(Optional.empty());

        consumer.handleEmail(mockEmail, 1);
        verify(statisticsRepository, times(1)).findByDomain("yahoo.com");
        verify(statisticsRepository, times(1)).save(any(Statistics.class));
    }

    @Test
    public void shouldNotSaveYahoo_InvalidPartition() {
        when(mockEmail.getFrom()).thenReturn("aaa@yahoo.com");

        consumer.handleEmail(mockEmail, 0);
        verify(statisticsRepository, never()).findByDomain(anyString());
        verify(statisticsRepository, never()).save(any(Statistics.class));
    }

    @Test
    public void shouldSaveOtherEmailStats() {
        when(mockEmail.getFrom()).thenReturn("aaa@amazon.com");
        when(statisticsRepository.findByDomain("Other")).thenReturn(Optional.empty());

        consumer.handleEmail(mockEmail, 2);
        verify(statisticsRepository, times(1)).findByDomain("Other");
        verify(statisticsRepository, times(1)).save(any(Statistics.class));
    }

    @Test
    public void shouldNotSaveOther_InvalidPartition() {
        when(mockEmail.getFrom()).thenReturn("aaa@amazon.com");

        consumer.handleEmail(mockEmail, 0);
        verify(statisticsRepository, never()).findByDomain(anyString());
        verify(statisticsRepository, never()).save(any(Statistics.class));
    }

}