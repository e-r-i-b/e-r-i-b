--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session enable parallel DML
/
alter session enable parallel query
/
alter session enable parallel DDL
/

--перепривязка таблиц
alter table SRB_IKFL.PAYMENTS_DOCUMENTS rename to SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS
/
alter table SRB_IKFL.PAYMENTS_DOCUMENTS_EXT rename to SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS_EXT
/
alter table SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS rename constraint PK_PAYMENTS_DOCUMENTS to FORDEL$PK_PAYMENTS_DOCUMENTS
/
alter index SRB_IKFL.IND_TEMPLATE_OWNER rename to SRB_IKFL.FORDEL$IND_TEMPLATE_OWNER
/
alter index SRB_IKFL.IND_U_TEMPLATE_NAME rename to SRB_IKFL.FORDEL$IND_U_TEMPLATE_NAME
/
alter index SRB_IKFL.PK_PAYMENTS_DOCUMENTS rename to SRB_IKFL.FORDEL$PK_PAYMENTS_DOCUMENTS
/
alter table SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS_EXT rename constraint PK_PAYMENTS_DOCUMENTS_EXT to FORDEL$PK_PAYMENTS
/
alter table SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS_EXT rename constraint FK_TEMPLATE_EXT_TO_TEMPLATE to FORDEL$FK_TEMPLATE
/
alter index SRB_IKFL.PK_PAYMENTS_DOCUMENTS_EXT rename to SRB_IKFL.FORDEL$PK_PAYMENTS_DOCUMENTS_EXT
/
alter index SRB_IKFL.DXFK_TEMPLATE_EXT_TO_TEMPLATE rename to SRB_IKFL.FORDEL$DXFK_TEMPLATE_EXT
/

--таблицы в новой структуре:
--основные реквизиты 
create table SRB_IKFL.PAYMENTS_DOCUMENTS(
  ID                           number(*, 0) not null ,
  KIND                         char(1) not null ,
  CHANGED                      timestamp(6) not null ,
  EXTERNAL_ID                  varchar2(80) ,
  DOCUMENT_NUMBER              number(*, 0) ,
  CLIENT_CREATION_DATE         timestamp(6) not null ,
  CLIENT_CREATION_CHANNEL      char(1) not null ,
  CLIENT_OPERATION_DATE        timestamp(6) ,
  CLIENT_OPERATION_CHANNEL     char(1) ,
  EMPLOYEE_OPERATION_DATE      timestamp(6) ,
  EMPLOYEE_OPERATION_CHANNEL   char(1) ,
  CLIENT_GUID                  varchar2(24) not null ,
  CREATED_EMPLOYEE_GUID        varchar2(24) ,
  CREATED_EMPLOYEE_FULL_NAME   varchar2(250) ,
  CONFIRMED_EMPLOYEE_GUID      varchar2(24) ,
  CONFIRMED_EMPLOYEE_FULL_NAME varchar2(250) ,
  OPERATION_UID                varchar2(32) ,
  STATE_CODE                   varchar2(25) not null ,
  STATE_DESCRIPTION            varchar2(265) ,
  FORM_TYPE                    varchar2(100) not null ,
  CHARGEOFF_RESOURCE           varchar2(30) ,
  DESTINATION_RESOURCE         varchar2(30) ,
  GROUND                       varchar2(1024) ,
  CHARGEOFF_AMOUNT             number(19, 4) ,
  CHARGEOFF_CURRENCY           char(3) ,
  DESTINATION_AMOUNT           number(19, 4) ,
  DESTINATION_CURRENCY         char(3) ,
  SUMM_TYPE                    varchar2(50) ,
  RECEIVER_NAME                varchar2(256) ,
  INTERNAL_RECEIVER_ID         number(*, 0) ,
  RECEIVER_POINT_CODE          varchar2(128) ,
  EXTENDED_FIELDS              clob ,
  REGION_ID                  varchar2(4) ,
  AGENCY_ID                  varchar2(4) ,
  BRANCH_ID                  varchar2(6) ,
  CLASS_TYPE                 varchar2(200) ,
  TEMPLATE_NAME              varchar2(128) not null ,
  TEMPLATE_USE_IN_MAPI       char(1) not null ,
  TEMPLATE_USE_IN_ATM        char(1) not null ,
  TEMPLATE_USE_IN_ERMB       char(1) not null ,
  TEMPLATE_USE_IN_ERIB       char(1) not null ,
  TEMPLATE_IS_VISIBLE        char(1) not null ,
  TEMPLATE_ORDER_IND         number(*, 0) not null ,
  TEMPLATE_STATE_CODE        varchar2(50) not null ,
  TEMPLATE_STATE_DESCRIPTION varchar2(128),
  MULTI_BLOCK_RECEIVER_CODE  varchar2(32)
)
tablespace "USERS" partition by hash(CLIENT_GUID) partitions 128
/
--"хвосты" реквизитов
create table SRB_IKFL.PAYMENTS_DOCUMENTS_EXT (
  ID         number(*, 0) not null ,
  KIND       varchar2(50) not null ,
  NAME       varchar2(64) not null ,
  VALUE      varchar2(4000) ,
  PAYMENT_ID number(*, 0) not null ,
  CHANGED    char(1) default '0' not null
) 
partition by hash(PAYMENT_ID) partitions 128 tablespace "USERS"
/

--переливка данных в новую структуру
insert /*+append parallel(rdp, 128)*/into SRB_IKFL.PAYMENTS_DOCUMENTS rdp ( ID,KIND,CHANGED,EXTERNAL_ID,DOCUMENT_NUMBER,CLIENT_CREATION_DATE,CLIENT_CREATION_CHANNEL,CLIENT_OPERATION_DATE,CLIENT_OPERATION_CHANNEL,EMPLOYEE_OPERATION_DATE,EMPLOYEE_OPERATION_CHANNEL,CLIENT_GUID,CREATED_EMPLOYEE_GUID,CREATED_EMPLOYEE_FULL_NAME,CONFIRMED_EMPLOYEE_GUID,CONFIRMED_EMPLOYEE_FULL_NAME,OPERATION_UID,STATE_CODE,STATE_DESCRIPTION,FORM_TYPE,CHARGEOFF_RESOURCE,DESTINATION_RESOURCE,GROUND,CHARGEOFF_AMOUNT,CHARGEOFF_CURRENCY,DESTINATION_AMOUNT,DESTINATION_CURRENCY,SUMM_TYPE,RECEIVER_NAME,INTERNAL_RECEIVER_ID,RECEIVER_POINT_CODE,EXTENDED_FIELDS,REGION_ID,AGENCY_ID,BRANCH_ID,CLASS_TYPE,TEMPLATE_NAME,TEMPLATE_USE_IN_MAPI,TEMPLATE_USE_IN_ATM,TEMPLATE_USE_IN_ERMB,TEMPLATE_USE_IN_ERIB,TEMPLATE_IS_VISIBLE,TEMPLATE_ORDER_IND,TEMPLATE_STATE_CODE,TEMPLATE_STATE_DESCRIPTION,MULTI_BLOCK_RECEIVER_CODE )
select /*+parallel(pd, 128) */ID,KIND,CHANGED,EXTERNAL_ID,DOCUMENT_NUMBER,CLIENT_CREATION_DATE,CLIENT_CREATION_CHANNEL,CLIENT_OPERATION_DATE,CLIENT_OPERATION_CHANNEL,EMPLOYEE_OPERATION_DATE,EMPLOYEE_OPERATION_CHANNEL,CLIENT_GUID,CREATED_EMPLOYEE_GUID,CREATED_EMPLOYEE_FULL_NAME,CONFIRMED_EMPLOYEE_GUID,CONFIRMED_EMPLOYEE_FULL_NAME,OPERATION_UID,STATE_CODE,STATE_DESCRIPTION,FORM_TYPE,CHARGEOFF_RESOURCE,DESTINATION_RESOURCE,GROUND,CHARGEOFF_AMOUNT,CHARGEOFF_CURRENCY,DESTINATION_AMOUNT,DESTINATION_CURRENCY,SUMM_TYPE,RECEIVER_NAME,INTERNAL_RECEIVER_ID,RECEIVER_POINT_CODE,EXTENDED_FIELDS,REGION_ID,AGENCY_ID,BRANCH_ID,CLASS_TYPE,TEMPLATE_NAME,TEMPLATE_USE_IN_MAPI,TEMPLATE_USE_IN_ATM,TEMPLATE_USE_IN_ERMB,TEMPLATE_USE_IN_ERIB,TEMPLATE_IS_VISIBLE,TEMPLATE_ORDER_IND,TEMPLATE_STATE_CODE,TEMPLATE_STATE_DESCRIPTION, decode(INTERNAL_RECEIVER_ID, null, null, (select uuid from service_providers where id=INTERNAL_RECEIVER_ID)) MULTI_BLOCK_RECEIVER_CODE 
  from SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS pd;

insert /*+append parallel(rdpe, 128)*/into SRB_IKFL.PAYMENTS_DOCUMENTS_EXT rdpe ( ID, KIND, NAME, VALUE, PAYMENT_ID, CHANGED)
select /*+parallel(pde, 128) */ ID, KIND, NAME, VALUE, PAYMENT_ID, '0' CHANGED
  from SRB_IKFL.FORDEL$PAYMENTS_DOCUMENTS_EXT pde;

--индексы и ограничения:
create unique index SRB_IKFL.PK_PAYMENTS_DOCUMENTS on SRB_IKFL.PAYMENTS_DOCUMENTS(
  id
) global partition by hash(ID) partitions 64 parallel 64 tablespace "INDX"
/
alter table SRB_IKFL.PAYMENTS_DOCUMENTS 
  add constraint PK_PAYMENTS_DOCUMENTS primary key (id) 
    using index SRB_IKFL.PK_PAYMENTS_DOCUMENTS
/

create unique index SRB_IKFL.IND_U_TEMPLATE_NAME on SRB_IKFL.PAYMENTS_DOCUMENTS (
  CLIENT_GUID asc, 
  TEMPLATE_NAME asc
) local parallel 128 tablespace "INDX"
/

create unique index SRB_IKFL.PK_PAYMENTS_DOCUMENTS_EXT on SRB_IKFL.PAYMENTS_DOCUMENTS_EXT(
  id
) global partition by hash(ID) partitions 64  parallel 64 tablespace "INDX" 
/
alter table SRB_IKFL.PAYMENTS_DOCUMENTS_EXT 
  add constraint PK_PAYMENTS_DOCUMENTS_EXT 
    primary key (id) using index SRB_IKFL.PK_PAYMENTS_DOCUMENTS_EXT
/

create index SRB_IKFL.DXFK_TEMPLATE_EXT_TO_TEMPLATE on SRB_IKFL.PAYMENTS_DOCUMENTS_EXT(
  PAYMENT_ID asc
) local parallel 64 tablespace "INDX"
/
alter table SRB_IKFL.PAYMENTS_DOCUMENTS_EXT 
  add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key(PAYMENT_ID) 
    references SRB_IKFL.PAYMENTS_DOCUMENTS(ID) on delete cascade enable novalidate
/ 

alter index SRB_IKFL.PK_PAYMENTS_DOCUMENTS noparallel
/
alter index SRB_IKFL.IND_U_TEMPLATE_NAME noparallel
/
alter index SRB_IKFL.DXFK_TEMPLATE_EXT_TO_TEMPLATE noparallel
/
alter index SRB_IKFL.PK_PAYMENTS_DOCUMENTS_EXT noparallel
/

/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'PAYMENTS_DOCUMENTS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'PAYMENTS_DOCUMENTS_EXT', degree => 32, cascade => true); end;
*/
/*Права
grant select on SRB_IKFL.PAYMENTS_DOCUMENTS  to OSDBO_USER;
grant select on SRB_IKFL.PAYMENTS_DOCUMENTS_EXT  to OSDBO_USER;
*/
