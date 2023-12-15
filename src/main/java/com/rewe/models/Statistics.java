package com.rewe.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_statistics")
public class Statistics {

    @Id
    @Column(name = "domain_name", unique = true, updatable = false, nullable = false)
    private String domain;

    @Column(name = "domain_count")
    private long count;

    public Statistics(String domain) {
        this.domain = domain;
    }

    public Statistics() {
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
