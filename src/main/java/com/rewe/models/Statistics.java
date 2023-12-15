package com.rewe.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_statistics")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = { "domain" })
public class Statistics {

    @Id
    @Column(name = "domain_name", unique = true, updatable = false, nullable = false)
    private String domain;

    @Column(name = "domain_count")
    private long count;
}
