--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 62080
-- Номер версии: 1.18
-- Комментарий: Корзина платежей.MQ(изменение модели БД OfflineDoc) 
create table OFFDOC_IKFL.BASKETINVOICE_PROCESS_STATE 
(
   KEY                  VARCHAR2(32)
) tablespace OFFLINE_DOC_DATA
/
insert into OFFDOC_IKFL.BASKETINVOICE_PROCESS_STATE values ('invoice_basket_processing_state')
/

create table OFFDOC_IKFL.BASKET_ROUTE_INFO 
(
   OPER_UID             VARCHAR2(32),
   BLOCK_NUMBER         INTEGER
)tablespace OFFLINE_DOC_DATA
/
create unique index OFFDOC_IKFL.I_BASKET_ROUTE_INFO_OPER_UID on OFFDOC_IKFL.BASKET_ROUTE_INFO (
   OPER_UID ASC
)tablespace OFFLINE_DOC_IDX
/

-- Номер ревизии: 62442
-- Номер версии: 1.18
-- Комментарий: Корзина платежей: Properties для слушателя сообщения от АС "AutoPay"
create table OFFDOC_IKFL.PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)tablespace OFFLINE_DOC_DATA
/
create sequence OFFDOC_IKFL.S_PROPERTIES
/
create unique index OFFDOC_IKFL.U_PROPETIES_CATEGORY_KEY on OFFDOC_IKFL.PROPERTIES (
   CATEGORY ASC,
   PROPERTY_KEY ASC
)tablespace OFFLINE_DOC_IDX
/

/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'OFFDOC_IKFL', tabname => 'PROPERTIES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'OFFDOC_IKFL', tabname => 'BASKET_ROUTE_INFO', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'OFFDOC_IKFL', tabname => 'BASKETINVOICE_PROCESS_STATE', degree => 32, cascade => true); end;
*/
/*
grant select on OFFDOC_IKFL.PROPERTIES  to OSDBO_USER;
grant select on OFFDOC_IKFL.BASKET_ROUTE_INFO  to OSDBO_USER;
grant select on OFFDOC_IKFL.BASKETINVOICE_PROCESS_STATE to OSDBO_USER;
*/