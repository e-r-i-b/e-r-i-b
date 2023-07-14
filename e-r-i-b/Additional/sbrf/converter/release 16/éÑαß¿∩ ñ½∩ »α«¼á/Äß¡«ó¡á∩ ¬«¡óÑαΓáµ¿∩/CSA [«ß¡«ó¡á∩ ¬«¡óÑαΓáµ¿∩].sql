/*
	Схема: CSA_IKFL
	Табличное пространство таблиц: CSA
	Табличное пространство индексов: CSAINDEXES
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSA_IKFL
/
alter session enable parallel ddl
/
alter session enable parallel dml
/
alter session enable parallel query
/

-- Номер ревизии: 72813
-- Комментарий: Уменьшение количества jndi-имен для ЕРМБ

alter table CSA_IKFL.CSA_NODES drop ( 
	SMS_WITH_IMSI_QUEUE_NAME, 
	SMS_WITH_IMSI_FACTORY_NAME, 
	SERVICE_PROFILE_QUEUE_NAME, 
	SERVICE_PROFILE_FACTORY_NAME, 
	SERVICE_CLIENT_QUEUE_NAME, 
	SERVICE_CLIENT_FACTORY_NAME, 
	SERVICE_RESOURCE_QUEUE_NAME, 
	SERVICE_RESOURCE_FACTORY_NAME, 
	SERVICE_FEE_RES_QUEUE_NAME, 
	SERVICE_FEE_RES_FACTORY_NAME 
)
/

alter table CSA_IKFL.CSA_NODES modify SMS_FACTORY_NAME default 'jms/ermb/ErmbQCF'
/

alter table CSA_IKFL.CSA_NODES add (
	ERMB_QUEUE_NAME varchar2(64),
	ERMB_FACTORY_NAME varchar2(64)
)
/

update  CSA_IKFL.CSA_NODES set ERMB_QUEUE_NAME='jms/ermb/ErmbQueue', ERMB_FACTORY_NAME='jms/ermb/ErmbQCF'
/

alter table CSA_IKFL.CSA_NODES modify (
	ERMB_QUEUE_NAME varchar2(64) default 'jms/ermb/ErmbQueue' not null,
	ERMB_FACTORY_NAME varchar2(64) default 'jms/ermb/ErmbQCF' not null
)
/

-- Номер ревизии: 73539
-- Комментарий: Гостевой вход.Доработка бэка.
create table CSA_IKFL.CSA_LOGINS 
(
   LOGIN         not null,
   CONNECTOR_ID,
   GUEST_ID
) tablespace CSA nologging parallel 32
as 
select /*+ parallel(cc, 32)*/
	cast(LOGIN as varchar2(32)) as LOGIN, 
	cast(ID as integer) as CONNECTOR_ID, 
	cast(null as integer) as GUEST_ID 
from CSA_CONNECTORS cc
where STATE = 'ACTIVE' and TYPE in ('CSA', 'TERMINAL') and LOGIN is not null
/

create index CSA_IKFL.DXFK_CSA_LOGINS_TO_CONNECTORS on CSA_IKFL.CSA_LOGINS (
   CONNECTOR_ID ASC
) tablespace CSAINDEXES parallel 16 nologging
/

create index CSA_IKFL.DXFK_CSA_LOGINS_TO_GUEST_PROF on CSA_IKFL.CSA_LOGINS (
   GUEST_ID ASC
) tablespace CSAINDEXES parallel 16 nologging
/

create unique index CSA_IKFL.IDX_CSA_LOGINS on CSA_IKFL.CSA_LOGINS (
   UPPER(LOGIN) ASC
) tablespace CSAINDEXES parallel 16 nologging
/

alter table CSA_IKFL.CSA_LOGINS logging noparallel
/
alter index CSA_IKFL.DXFK_CSA_LOGINS_TO_CONNECTORS logging noparallel
/
alter index CSA_IKFL.DXFK_CSA_LOGINS_TO_GUEST_PROF logging noparallel
/
alter index CSA_IKFL.IDX_CSA_LOGINS logging noparallel
/


alter table CSA_IKFL.CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_CONNECTORS foreign key (CONNECTOR_ID)
      references CSA_IKFL.CSA_CONNECTORS (ID) enable novalidate
/

alter table CSA_IKFL.CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_GUEST foreign key (GUEST_ID)
      references CSA_IKFL.GUEST_PROFILES (ID) enable novalidate
/

alter table CSA_IKFL.GUEST_PASSWORDS
   add constraint FK_GUEST_PASS_TO_PROFILES foreign key (GUEST_ID)
      references CSA_IKFL.GUEST_PROFILES (ID) enable novalidate
/

-- Номер ревизии: 73670 
-- Комментарий: Гостевой вход. Миграция
alter table CSA_IKFL.CSA_NODES add GUEST_AVAILABLE char(1)
/
update CSA_IKFL.CSA_NODES set GUEST_AVAILABLE = '0'
/
alter table CSA_IKFL.CSA_NODES modify GUEST_AVAILABLE not null
/

-- Номер ревизии: 74199
-- Комментарий:  CHG084758: [ISUP] [Деловая среда] ошибка при подтверждении верификации чековым паролем 
alter table CSA_IKFL.CSA_OPERATIONS modify CONFIRM_SID VARCHAR2(64)
/

--
alter table CSA_IKFL.CSA_OPERATIONS rename to FORDEL$CSA_OPERATIONS
/
alter table CSA_IKFL.FORDEL$CSA_OPERATIONS rename constraint FK_CSA_OPER_FK_CSA_OP_CSA_PROF to FD$FK_CSA_OPER_
/
alter table CSA_IKFL.FORDEL$CSA_OPERATIONS rename constraint PK_CSA_OPERATIONS to FD$PK_CSA_OPERATIONS
/
alter index CSA_IKFL.PK_CSA_OPERATIONS rename to FD$PK_CSA_OPERATIONS
/
alter index CSA_IKFL.CSA_OPERATIONS_PTSC rename to FD$CSA_OPERATIONS_PTSC
/

/*
create table CSA_IKFL.CSA_OPERATIONS(
	OUID                       varchar2(32 byte)         not null ,
	FIRST_NAME                 varchar2(100 byte)        not null ,
	SUR_NAME                   varchar2(100 byte)        not null ,
	PATR_NAME                  varchar2(100 byte) ,
	PASSPORT                   varchar2(100 byte)        not null ,
	BIRTHDATE                  timestamp(6)              not null ,
	cb_code                    varchar2(20 byte)         not null ,
	USER_ID                    varchar2(10 byte) ,
	TYPE                       varchar2(40 byte)         not null ,
	STATE                      varchar2(40 byte)         not null ,
	CREATION_DATE              timestamp(6) default sysdate not null ,
	PROFILE_ID                 number(*, 0)              not null ,
	PARAMS                     clob ,
	CONFIRM_TYPE               varchar2(32 byte) ,
	CONFIRM_CODE_HASH          varchar2(40 byte) ,
	CONFIRM_CODE_SALT          varchar2(32 byte) ,
	CONFIRM_CODE_CREATION_DATE timestamp(6) ,
	CONFIRM_ERRORS             number(*, 0) ,
	CONFIRM_SID                varchar2(64 byte) ,
	CONFIRM_PASSWORD_NUMBER    varchar2(32 byte) ,
	CONFIRM_RECEIPT_NUMBER     varchar2(32 byte) ,
	CONFIRM_PASSWORD_LEFT      number(*, 0) ,
	CONFIRM_LAST_ATEMPTS       number(*, 0) ,
	INFO                       clob ,
	IP_ADDRESS                 varchar2(15 byte) ,
	constraint PK_CSA_OPERATIONS primary key (OUID) using index (
		create unique index CSA_IKFL.PK_CSA_OPERATIONS on CSA_IKFL.CSA_OPERATIONS(OUID) global partition by hash(OUID) partitions 64 tablespace CSAINDEXES
	)
) tablespace "CSA" 
partition by range( CREATION_DATE ) interval (numtoyminterval(1, 'MONTH'))
subpartition by hash( PROFILE_ID ) subpartitions 64
(
	partition P_FIRST values less than (to_date('01.05.2015', 'DD.MM.YYYY')) tablespace "CSA"
)
/
*/
create table CSA_IKFL.CSA_OPERATIONS( 
  OUID                       ,
  FIRST_NAME                 ,
  SUR_NAME                   ,
  PATR_NAME                  ,
  PASSPORT                   ,
  BIRTHDATE                  ,
  CB_CODE                    ,
  USER_ID                    ,
  TYPE                       ,
  STATE                      ,
  CREATION_DATE              ,
  PROFILE_ID                 ,
  PARAMS                     ,
  CONFIRM_TYPE               ,
  CONFIRM_CODE_HASH          ,
  CONFIRM_CODE_SALT          ,
  CONFIRM_CODE_CREATION_DATE ,
  CONFIRM_ERRORS             ,
  CONFIRM_SID                ,
  CONFIRM_PASSWORD_NUMBER    ,
  CONFIRM_RECEIPT_NUMBER     ,
  CONFIRM_PASSWORD_LEFT      ,
  CONFIRM_LAST_ATEMPTS       ,
  INFO                       ,
  IP_ADDRESS                 
) tablespace "CSA" nologging
partition by range( CREATION_DATE ) interval (numtoyminterval(1, 'MONTH')) subpartition by hash( PROFILE_ID ) subpartitions 64
(
  partition P_FIRST values less than (to_date('01.05.2015', 'DD.MM.YYYY')) tablespace "CSA"
)
as 
select 
  OUID,  FIRST_NAME,  SUR_NAME,  PATR_NAME,  PASSPORT,  BIRTHDATE,  CB_CODE,  USER_ID,  TYPE,  STATE,  CREATION_DATE,  PROFILE_ID,  PARAMS,  CONFIRM_TYPE,  CONFIRM_CODE_HASH,  CONFIRM_CODE_SALT,  CONFIRM_CODE_CREATION_DATE,  CONFIRM_ERRORS,  CONFIRM_SID,  CONFIRM_PASSWORD_NUMBER,  CONFIRM_RECEIPT_NUMBER,  CONFIRM_PASSWORD_LEFT,  CONFIRM_LAST_ATEMPTS,  INFO,  IP_ADDRESS
from CSA_OPERATIONS partition for (to_date('01.04.2015','dd.mm.yyyy')) op
where  CREATION_DATE > sysdate - 2;

create unique index CSA_IKFL.PK_CSA_OPERATIONS on CSA_IKFL.CSA_OPERATIONS(OUID) global partition by hash(OUID) partitions 64 tablespace CSAINDEXES parallel 64 nologging
/

alter table CSA_IKFL.CSA_OPERATIONS 
	add constraint PK_CSA_OPERATIONS primary key (OUID) using index CSA_IKFL.PK_CSA_OPERATIONS enable novalidate
/

alter table CSA_IKFL.CSA_OPERATIONS 
	add constraint FK_CSA_OPER_FK_CSA_OP_CSA_PROF 
		foreign key(PROFILE_ID) references CSA_IKFL.CSA_PROFILES(id) enable novalidate
/

create index CSA_IKFL.CSA_OPERATIONS_PTSC on CSA_IKFL.CSA_OPERATIONS (
	PROFILE_ID asc,
	TYPE asc,
	STATE asc,
	CREATION_DATE asc
) local  tablespace "CSAINDEXES"  parallel 64 nologging
/

alter index CSA_IKFL.PK_CSA_OPERATIONS noparallel logging
/
alter index CSA_IKFL.CSA_OPERATIONS_PTSC noparallel logging
/
alter table CSA_IKFL.CSA_OPERATIONS noparallel logging 
/