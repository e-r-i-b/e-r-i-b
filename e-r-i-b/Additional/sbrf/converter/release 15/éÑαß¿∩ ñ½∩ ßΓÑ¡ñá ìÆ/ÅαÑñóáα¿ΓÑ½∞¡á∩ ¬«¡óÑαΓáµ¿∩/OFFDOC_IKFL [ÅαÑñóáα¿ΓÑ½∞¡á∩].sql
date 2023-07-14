--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = OFFDOC_IKFL
/

-- Номер ревизии: 68395
-- Комментарий: Интеграция с БКИ (приём ответа) - маршрутизация
create table OFFDOC_IKFL.OUTGOING_REQUESTS 
(
   RQ_UID               VARCHAR(32)          not null,
   REQUEST_TIME         TIMESTAMP            not null,
   OPER_UID             VARCHAR(32)          not null,
   NODE_ID              INTEGER              not null,
   PERSON_ID            INTEGER              not null,
   PAYMENT_ID           INTEGER,
   constraint PK_OUTGOING_REQUESTS primary key (RQ_UID) using index (
	create unique index OFFDOC_IKFLPK_OUTGOING_REQUESTS on OFFDOC_IKFLOUTGOING_REQUESTS(RQ_UID) tablespace OFFLINE_DOC_IDX
   )
) tablespace OFFLINE_DOC_DATA
/
