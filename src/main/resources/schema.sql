-- auto-generated definition
create table company
(
    id      bigint  not null
        primary key,
    version integer not null,
    name    varchar(255)
);

alter table company
    owner to postgres;

-- auto-generated definition
create table contact
(
    id         bigint  not null
        primary key,
    version    integer not null,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    company_id bigint  not null
        constraint fkpgbqt6dnai52x55o1qvsx1dfn
            references company,
    status_id  bigint  not null
        constraint fktp0gbknv4j92yko7ucc3tpp2y
            references status
);

alter table contact
    owner to postgres;

-- auto-generated definition
create table status
(
    id      bigint  not null
        primary key,
    version integer not null,
    name    varchar(255)
);

alter table status
    owner to postgres;