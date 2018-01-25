create sequence hibernate_sequence start with 1 increment by 1
create table exchange_rate (id number(19,0) not null, buying_rate number(19,2) not null, currency_from varchar2(3 char) not null, currency_to varchar2(3 char) not null, selling_rate number(19,2) not null, primary key (id))
