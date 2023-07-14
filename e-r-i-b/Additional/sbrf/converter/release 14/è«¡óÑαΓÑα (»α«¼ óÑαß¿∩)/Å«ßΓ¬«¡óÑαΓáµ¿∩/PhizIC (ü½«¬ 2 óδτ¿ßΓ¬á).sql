--БЛОК2
drop table MY$TEMP_DEF
/
drop table MY$TEMP_BD
/
drop table FORDEL$BUSINESS_DOCUMENTS_P1
/
drop table FORDEL$BUSINESS_DOCUMENTS_RES
/
drop table FORDEL$BUSINESS_DOCUMENTS_TL
/

alter session enable parallel dml
/
alter session enable parallel ddl
/
alter session enable parallel query
/
alter session force parallel dml parallel 128
/

/*
**	Перенос INPUT_REGISTER_JOURNAL
*/
alter table SRB_IKFL2.INPUT_REGISTER_JOURNAL rename to FORDEL$INPUT_REGISTER_JOURNAL
/
alter index  SRB_IKFL2.INDEX_REGISTER_JOURNAL_SESSION rename to FORDEL$I_R_J_SESSION
/
alter index SRB_IKFL2.RL_CARD_DATE_INDEX rename to FOR_DEL$I_R_J_CARD_DATE_INDEX
/
alter index SRB_IKFL2.RL_FIO_DATE_INDEX rename to FOR_DEL$I_R_J_FIO_DATE_INDEX
/
alter index SRB_IKFL2.RL_IP_DATE_INDEX rename to FOR_DEL$I_R_J_IP_DATE_INDEX 
/
alter index SRB_IKFL2.RL_LOGIN_DATE_INDEX rename to FOR_DEL$I_R_J_LOGIN_DATE_INDEX 
/
alter index SRB_IKFL2.RL_PK rename to FOR_DEL$I_R_J_PK
/

create table SRB_IKFL2."INPUT_REGISTER_JOURNAL" (
  "ID"            number(*,0) not null enable,
  "LOGIN_DATE"    timestamp (6) not null enable,
  "LOGIN_ID"      number(*,0),
  "STATE"         varchar2(30),
  "APPLICATION"   varchar2(10) not null enable,
  "SESSION_ID"    varchar2(64) not null enable,
  "IP_ADDRESS"    varchar2(15),
  "OPERATION_UID" varchar2(32),
  "FIRST_NAME"    varchar2(42),
  "SUR_NAME"      varchar2(42),
  "PATR_NAME"     varchar2(42),
  "BIRTHDAY"      timestamp (6),
  "CARD_NUMBER"   varchar2(20)
) tablespace "INPUT_REGISTER_JOURNAL" partition by range("LOGIN_DATE") interval (numtoyminterval(1,'MONTH'))
(
  partition "P_FIRST" values less than (timestamp' 2014-01-01 00:00:00')   tablespace "INPUT_REGISTER_JOURNAL"
)
/
--переливка данных
insert /*+append parallel(irj, 128)*/ into SRB_IKFL2."INPUT_REGISTER_JOURNAL"
	select /*parallel(irjo, 128)*/ * from SRB_IKFL2."FORDEL$INPUT_REGISTER_JOURNAL" irjo
/

create unique index SRB_IKFL2."INDEX_REGISTER_JOURNAL_SESSION" on SRB_IKFL2."INPUT_REGISTER_JOURNAL"(
  "SESSION_ID"
) parallel 128 tablespace "INPUT_REGISTER_JOURNAL_IDX" 
/
create index SRB_IKFL2."RL_CARD_DATE_INDEX" on SRB_IKFL2."INPUT_REGISTER_JOURNAL"(
  "CARD_NUMBER",
  "LOGIN_DATE" desc
) local parallel 128 tablespace "INPUT_REGISTER_JOURNAL_IDX"
/
create index SRB_IKFL2."RL_FIO_DATE_INDEX" on SRB_IKFL2."INPUT_REGISTER_JOURNAL"(
  upper(replace(replace("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
  "LOGIN_DATE" desc
) local parallel 128 tablespace "INPUT_REGISTER_JOURNAL_IDX"
/
create index SRB_IKFL2."RL_IP_DATE_INDEX" on SRB_IKFL2."INPUT_REGISTER_JOURNAL"(
  "IP_ADDRESS",
  "LOGIN_DATE" desc
) local parallel 128 tablespace "INPUT_REGISTER_JOURNAL_IDX" 
/
create index SRB_IKFL2."RL_LOGIN_DATE_INDEX" on SRB_IKFL2."INPUT_REGISTER_JOURNAL"(
  "LOGIN_ID",
  "LOGIN_DATE" desc
) local parallel 128 tablespace "INPUT_REGISTER_JOURNAL_IDX" 
/
create unique index SRB_IKFL2."RL_PK" on SRB_IKFL2."INPUT_REGISTER_JOURNAL"(
  "ID"
) reverse parallel 128 tablespace "INPUT_REGISTER_JOURNAL_IDX" 
/

alter index  SRB_IKFL2.INDEX_REGISTER_JOURNAL_SESSION noparallel
/
alter index  SRB_IKFL2.RL_CARD_DATE_INDEX noparallel
/
alter index  SRB_IKFL2.RL_FIO_DATE_INDEX noparallel
/
alter index  SRB_IKFL2.RL_IP_DATE_INDEX noparallel
/
alter index  SRB_IKFL2.RL_LOGIN_DATE_INDEX noparallel
/
alter index  SRB_IKFL2.RL_PK noparallel
/

/*
**	Перенос DOCUMENT_OPERATIONS_JOURNAL
*/
alter table SRB_IKFL2.DOCUMENT_OPERATIONS_JOURNAL rename to FORDEL$DOC_OPERATIONS_JOURNAL
/

alter index  SRB_IKFL2.CARD_CHANNEL_OPER_DATE_IDX rename to FORDEL$DOJ_CARD_C_OPER_IDX
/
alter index SRB_IKFL2.DOC_OPER_DATE_IDX rename to FORDEL$DOJ_DOC_OPER_DATE_IDX
/
alter index SRB_IKFL2.PHONE_CHANNEL_OPER_DATE_IDX rename to FORDEL$DOJ_PHONE_CHANNEL_IDX
/
alter index SRB_IKFL2.PROFILE_CHANNEL_OPER_DATE_IDX rename to FORDEL$DOJ_PROFILE_CHANNEL_IDX
/

create table SRB_IKFL2."DOCUMENT_OPERATIONS_JOURNAL"
(
  "EXTERNAL_ID"          varchar2(100) not null enable,
  "DOCUMENT_EXTERNAL_ID" varchar2(100) not null enable,
  "OPERATION_DATE"       timestamp (6) not null enable,
  "PROFILE_ID"           number(*,0) not null enable,
  "AMOUNT"               number(19,4),
  "AMOUNT_CURRENCY"      char(3),
  "OPERATION_TYPE"       varchar2(20) not null enable,
  "CHANNEL_TYPE"         varchar2(25) not null enable,
  "LIMITS_INFO"          varchar2(4000),
  "EXTERNAL_CARD"        varchar2(19),
  "EXTERNAL_PHONE"       varchar2(16)
)
tablespace "DOCUMENT_OPERATIONS_JOURNAL" partition by range("OPERATION_DATE") interval (numtodsinterval(7,'DAY'))
(
  partition "P_FIRST" values less than (timestamp' 2014-03-02 00:00:00') tablespace "DOCUMENT_OPERATIONS_JOURNAL"
)
/

--переливка данных
insert /*+append parallel(doj, 128)*/ into SRB_IKFL2."DOCUMENT_OPERATIONS_JOURNAL" doj
	select /*parallel(dojo, 128)*/ * from SRB_IKFL2."FORDEL$DOC_OPERATIONS_JOURNAL" dojo
/

create index SRB_IKFL2."CARD_CHANNEL_OPER_DATE_IDX" on SRB_IKFL2."DOCUMENT_OPERATIONS_JOURNAL"(
  "EXTERNAL_CARD",
  "CHANNEL_TYPE",
  "OPERATION_DATE"
) tablespace "DOCUMENT_OPERATIONS_JOURNA_IDX" local parallel 128
/

create index SRB_IKFL2."DOC_OPER_DATE_IDX" on SRB_IKFL2."DOCUMENT_OPERATIONS_JOURNAL"(
  "DOCUMENT_EXTERNAL_ID",
  "OPERATION_DATE"
) tablespace "DOCUMENT_OPERATIONS_JOURNA_IDX" local parallel 128
/
create index SRB_IKFL2."PHONE_CHANNEL_OPER_DATE_IDX" on SRB_IKFL2."DOCUMENT_OPERATIONS_JOURNAL"(
  "EXTERNAL_PHONE",
  "CHANNEL_TYPE",
  "OPERATION_DATE"
) tablespace "DOCUMENT_OPERATIONS_JOURNA_IDX" local parallel 128
/
create index SRB_IKFL2."PROFILE_CHANNEL_OPER_DATE_IDX" on SRB_IKFL2."DOCUMENT_OPERATIONS_JOURNAL"(
  "PROFILE_ID",
  "CHANNEL_TYPE",
  "OPERATION_DATE"
) tablespace "DOCUMENT_OPERATIONS_JOURNA_IDX" local parallel 128
/


alter index  SRB_IKFL2.CARD_CHANNEL_OPER_DATE_IDX noparallel
/
alter index SRB_IKFL2.DOC_OPER_DATE_IDX noparallel
/
alter index SRB_IKFL2.PHONE_CHANNEL_OPER_DATE_IDX noparallel
/
alter index SRB_IKFL2.PROFILE_CHANNEL_OPER_DATE_IDX noparallel
/


/*
**	Перенос CARD_OPERATIONS
*/
alter table SRB_IKFL2."CARD_OPERATIONS" rename to FORDEL$CARD_OPERATIONS
/

alter table SRB_IKFL2."FORDEL$CARD_OPERATIONS" drop constraint "FK_CARDOP_CATEGORY" 
/
alter index  SRB_IKFL2.IDX_EXTERNAL_ID rename to FORDEL$IDX_EXTERNAL_ID
/
alter index SRB_IKFL2.I_CARDOP_CATEGORY rename to FORDEL$I_CARDOP_CATEGORY
/
alter index SRB_IKFL2.I_CARDOP_LCO rename to FORDEL$I_CARDOP_LCO
/
alter index SRB_IKFL2.I_CARD_OPERATIONS_ID rename to FORDEL$I_CARD_OPERATIONS_ID
/

create table SRB_IKFL2."CARD_OPERATIONS"
(
  "ID"             number(*,0) not null enable,
  "EXTERNAL_ID"    varchar2(64),
  "OPERATION_DATE" timestamp (6) not null enable,
  "CARD_NUMBER"    varchar2(20),
  "DESCRIPTION"    varchar2(100),
  "NATIONAL_SUMM"  number(15,4) not null enable,
  "CARD_SUMM"      number(15,4),
  "IO_CASH"        char(1) not null enable,
  "DEVICE_NUMBER"  varchar2(10),
  "LOGIN_ID"       number(*,0) not null enable,
  "CATEGORY_ID"    number(*,0) not null enable,
  "MCC_CODE"       number(*,0),
  "LOAD_DATE"      timestamp (6) not null enable,
  "OPERATION_TYPE" varchar2(7),
  "ORIGINAL_DESCRIPTION"            varchar2(100), 
  "BUSINESS_DOCUMENT_ID"            number(38),    
  "HIDDEN"                          char(1),       
  "ORIGINAL_COUNTRY"                varchar2(3),
  "CLIENT_COUNTRY"                  varchar2(3),   
  "ORIGINAL_CATEGORY_NAME"          varchar2(100)   
) 
tablespace "USER_DATA" 
partition by range( "OPERATION_DATE" ) interval (  numtoyminterval(1,'MONTH') )
(
  partition "P_FIRST" values less than (timestamp' 2007-01-01 00:00:00') tablespace "USER_DATA"
)
/

--переливка данных
insert /*+append parallel(co, 128)*/ into SRB_IKFL2."CARD_OPERATIONS" co
	select /*parallel(fdco, 128)*/ * from SRB_IKFL2."FORDEL$CARD_OPERATIONS" fdco
/

alter table SRB_IKFL2."CARD_OPERATIONS" add constraint "FK_CARDOP_CATEGORY" 
  foreign key ("CATEGORY_ID") references SRB_IKFL2."CARD_OPERATION_CATEGORIES" ("ID") enable novalidate
/

create index SRB_IKFL2."IDX_EXTERNAL_ID" on SRB_IKFL2."CARD_OPERATIONS"(
  "EXTERNAL_ID"
) tablespace "USER_DATA_IDX" parallel 128
/

create index SRB_IKFL2."I_CARDOP_CATEGORY" on SRB_IKFL2."CARD_OPERATIONS" (
  "CATEGORY_ID"
) tablespace "USER_DATA_IDX" local parallel 128

/
create index SRB_IKFL2."I_CARDOP_LCO" on SRB_IKFL2."CARD_OPERATIONS"(
  "LOGIN_ID",
  "OPERATION_DATE"
) tablespace "USER_DATA_IDX" local parallel 128
/
create index SRB_IKFL2."I_CARD_OPERATIONS_ID" on SRB_IKFL2."CARD_OPERATIONS"(
  "ID"
) tablespace "USER_DATA_IDX" local parallel 128

/

alter index  SRB_IKFL2.IDX_EXTERNAL_ID noparallel
/
alter index SRB_IKFL2.I_CARDOP_CATEGORY noparallel
/
alter index SRB_IKFL2.I_CARDOP_LCO noparallel
/
alter index SRB_IKFL2.I_CARD_OPERATIONS_ID noparallel
/

/*
**	Перенос EXCEPTIONS_LOG
*/
alter table SRB_IKFL2.EXCEPTIONS_LOG rename to FORDEL$EXCEPTIONS_LOG
/
create table SRB_IKFL2."EXCEPTIONS_LOG"
(
  "CREATION_DATE" timestamp (6) not null enable,
  "HASH"          varchar2(256) not null enable
)
tablespace "USER_DATA" partition by range("CREATION_DATE") interval ( numtodsinterval(1,'DAY'))
(
  partition "P_FIRST" values less than (to_date('01.08.2014', 'dd.mm.yyyy') tablespace "USER_DATA"
) 
/

/*
**	Перенос BUSINESS_DOCUMENTS
*/
alter table SRB_IKFL2.BUSINESS_DOCUMENTS split partition "P_2014_07"
  at (to_date('01.07.2014', 'dd.mm.yyyy')) into (partition "P_2014_06", partition "P_2014_07") 
parallel 128
/
alter table SRB_IKFL2.BUSINESS_DOCUMENTS split partition "P_MAXVALUE"
  at (to_date('01.09.2014', 'dd.mm.yyyy')) into (partition "P_2014_08", partition "P_MAXVALUE")  
parallel 128
/
alter table SRB_IKFL2.BUSINESS_DOCUMENTS split partition "P_MAXVALUE"
  at (to_date('01.10.2014', 'dd.mm.yyyy')) into (partition "P_2014_09", partition "P_MAXVALUE") 
update global indexes 
parallel 128
/


