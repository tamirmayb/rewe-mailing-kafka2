package com.rewe.repository;

import com.rewe.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {
    Optional<Statistics> findByDomain(String domain);
}

