drop table if exists email_statistics;

create table email_statistics
(
    domain_name           varchar(100) not null,
    domain_count          bigint(20),
    primary key (domain_name)
);