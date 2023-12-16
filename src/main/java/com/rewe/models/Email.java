package com.rewe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Email implements Serializable {
    private static final long serialVersionUID = -8303082751887676310L;
    private UUID id;
    private String from;
    private List<String> to;
    private String subject;
    private String content;

    public Email(String from, List<String> to, String subject, String content) {
        this.id = UUID.randomUUID();
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.content = content;
    }

    public Email() {
        this.id = UUID.randomUUID();
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(to, email.to) &&
                Objects.equals(from, email.from) &&
                Objects.equals(subject, email.subject)
                && Objects.equals(content, email.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, from, subject, content);
    }

    @Override
    public String toString() {
        return "Email{" +
                "to=" + to +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
