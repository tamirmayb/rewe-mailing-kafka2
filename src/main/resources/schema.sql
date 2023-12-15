drop table if exists email_statistics;

create table email_statistics
(
    domain_name           varchar(100) not null,
    received              bigint(20),
    primary key (domain)
);