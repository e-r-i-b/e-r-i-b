-- Номер ревизии: 62080
-- Номер версии: 1.18
-- Комментарий: Корзина платежей.MQ(изменение модели БД OfflineDoc) 
create table BASKETINVOICE_PROCESS_STATE 
(
   KEY                  VARCHAR2(32)
)
/
insert into BASKETINVOICE_PROCESS_STATE values ('invoice_basket_processing_state')
/

create table BASKET_ROUTE_INFO 
(
   OPER_UID             VARCHAR2(32),
   BLOCK_NUMBER         INTEGER
)
/
create unique index I_BASKET_ROUTE_INFO_OPER_UID on BASKET_ROUTE_INFO (
   OPER_UID ASC
)
/

-- Номер ревизии: 62442
-- Номер версии: 1.18
-- Комментарий: Корзина платежей: Properties для слушателя сообщения от АС "AutoPay"
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)
/
create sequence S_PROPERTIES
/
create unique index U_PROPETIES_CATEGORY_KEY on PROPERTIES (
   CATEGORY ASC,
   PROPERTY_KEY ASC
)
/