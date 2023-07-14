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

-- Номер ревизии: 73357
-- Комментарий: Гостевой вход.Доработка бэка.Реализация схемы данных(ч1)
create sequence CSA_IKFL.S_GUEST_CODE_PROFILES cache 10000
/

create table CSA_IKFL.GUEST_OPERATIONS 
(
   OUID                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   PHONE                VARCHAR2(16)         not null,
   TYPE                 VARCHAR2(40)         not null,
   STATE                VARCHAR2(40)         not null,
   AUTH_CODE            VARCHAR2(40),
   AUTH_ERRORS          INTEGER,
   IP_ADDRESS           VARCHAR2(15),
   INFO                 VARCHAR2(4000),
   PARAMS               VARCHAR2(4000)
) tablespace CSA
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
subpartition by hash (PHONE) subpartitions 64
(
       partition P_2015_02 values less than (timestamp '2015-02-01 00:00:00') tablespace CSA
)
/

create unique index CSA_IKFL.IDX_GUEST_OPERATIONS_OOUD on CSA_IKFL.GUEST_OPERATIONS (
   OUID ASC
) global partition by hash (OUID) partitions 64 tablespace CSAINDEXES
/

create table CSA_IKFL.GUEST_PROFILES 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR2(16)         not null,
   CODE                 INTEGER,
   BLOCKED_UNTIL        TIMESTAMP,
   AUTH_ERRORS          INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   constraint PK_GUEST_PROFILES primary key (ID) using index (
		create unique index CSA_IKFL.PK_GUEST_PROFILES on CSA_IKFL.GUEST_PROFILES(ID) tablespace CSAINDEXES
   )
) tablespace CSA
partition by hash (PHONE) partitions 64
/

create unique index  CSA_IKFL.IDX_GUEST_PROFILES_PHONE on GUEST_PROFILES (
   PHONE ASC
) local tablespace CSAINDEXES
/

create sequence CSA_IKFL.S_GUEST_PROFILES cache 1000
/

create table CSA_IKFL.GUEST_PASSWORDS 
(
   ID                   INTEGER              not null,
   VALUE                VARCHAR2(40)         not null,
   SALT                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   ACTIVE               CHAR(1)              not null,
   GUEST_ID             INTEGER              not null,
   constraint PK_GUEST_PASSWORDS primary key (ID) using index (
		create unique index CSA_IKFL.PK_GUEST_PASSWORDS on CSA_IKFL.GUEST_PASSWORDS(ID) tablespace CSAINDEXES
   )
) tablespace CSA
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-02-2015','DD-MM-YYYY')) tablespace CSA
)
/

create sequence CSA_IKFL.S_GUEST_PASSWORDS cache 1000
/

create index  CSA_IKFL.IDX_GUEST_DATE_PASSWORDS on GUEST_PASSWORDS (
   GUEST_ID ASC,
   CREATION_DATE ASC
) local tablespace CSAINDEXES
/
