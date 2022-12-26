create table stock
(
    id              bigint primary key,
    ticker          varchar(10)  not null,
    description     varchar(200) not null,
    exchange        varchar(100) not null,
    last_price      numeric,
    last_price_date timestamp,

    unique (ticker, exchange)
);

create table stock_portfolio
(
    id       bigint primary key,
    username varchar(100) unique not null
);

create table stock_portfolio_position
(
    id               bigint primary key,
    stock_id         bigint references stock (id)           not null,
    portfolio_id     bigint references stock_portfolio (id) not null,
    current_position numeric                                not null
);

create table stock_favourite
(
    id             bigint primary key,
    username       varchar(100)                 not null,
    stock_id       bigint references stock (id) not null,
    targeted_price numeric
);