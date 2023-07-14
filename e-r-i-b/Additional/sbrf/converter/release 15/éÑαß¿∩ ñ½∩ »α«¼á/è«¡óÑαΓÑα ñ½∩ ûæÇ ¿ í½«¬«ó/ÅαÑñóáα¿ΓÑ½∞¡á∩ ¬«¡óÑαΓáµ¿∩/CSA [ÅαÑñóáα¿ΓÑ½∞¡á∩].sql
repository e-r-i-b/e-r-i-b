--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSA_IKFL
/

-- Номер ревизии: 67348
-- Комментарий: Push III: Добавить в БД ЦСА таблицу PUSH_PARAMS
create table CSA_IKFL.PUSH_PARAMS 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR(20)          not null,
   KEY                  VARCHAR(255)         not null,
   TEXT                 CLOB                 not null,
   SHORT_TEXT           VARCHAR(255)         not null,
   DESCRIPTION          VARCHAR(255),
   PRIORITY             INTEGER,
   CODE                 NUMBER(2)            not null,
   PRIVACY_TYPE         VARCHAR(20)          not null,
   PUBLICITY_TYPE       VARCHAR(20)          not null,
   SMS_BACKUP           CHAR(1)              not null,
   VARIABLES            VARCHAR(4000),
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   ATTRIBUTES           CLOB,
   constraint PK_PUSH_PARAMS primary key (ID) using index (
	create unique index CSA_IKFL.PK_PUSH_PARAMS on CSA_IKFL.PUSH_PARAMS(ID) tablespace CSAINDEXES
   )
)tablespace CSA
/
create sequence CSA_IKFL.S_PUSH_PARAMS
/

create unique index CSA_IKFL.I_PUSH_PARAMS_KEY on CSA_IKFL.PUSH_PARAMS (
   KEY ASC
) tablespace CSAINDEXES
/

-- Номер ревизии: 67466
-- Комментарий: Тех-перерывы МБК - Реализация кэша хранимых процедур МБК
create table CSA_IKFL.MBK_CACHE_CARDS_BY_PHONE 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   RESULT_SET           CLOB,
   constraint PK_MBK_CACHE_CARDS_BY_PHONE primary key (PHONE_NUMBER) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_CARDS_BY_PHONE on CSA_IKFL.MBK_CACHE_CARDS_BY_PHONE(PHONE_NUMBER) tablespace CSAINDEXES
   )
)tablespace CSA
/

create table CSA_IKFL.MBK_CACHE_CLIENT_BY_CARD 
(
   CARD_NUMBER          VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   AUTH_IDT             VARCHAR2(10)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_CARD primary key (CARD_NUMBER) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_CLIENT_BY_CARD on CSA_IKFL.MBK_CACHE_CLIENT_BY_CARD(CARD_NUMBER) tablespace CSAINDEXES
   )
)tablespace CSA
/

create table CSA_IKFL.MBK_CACHE_CLIENT_BY_LOGIN 
(
   AUTH_IDT             VARCHAR2(10)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   CARD_NUMBER          VARCHAR2(20)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_LOGIN primary key (AUTH_IDT) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_CLIENT_BY_LOGIN on CSA_IKFL.MBK_CACHE_CLIENT_BY_LOGIN(AUTH_IDT) tablespace CSAINDEXES
   )
)tablespace CSA
/

create table CSA_IKFL.MBK_CACHE_IMSI_CHECK_RESULT 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   MESSAGE_ID           INTEGER              not null,
   VALIDATION_RESULT    INTEGER,
   constraint PK_MBK_CACHE_IMSI_CHECK_RESULT primary key (PHONE_NUMBER) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_IMSI_CHECK_RESULT on CSA_IKFL.MBK_CACHE_IMSI_CHECK_RESULT(PHONE_NUMBER) tablespace CSAINDEXES
   )
)tablespace CSA
/

create table CSA_IKFL.MBK_CACHE_REGISTRATIONS 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS primary key (STR_CARD_NUMBER) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_REGISTRATIONS on CSA_IKFL.MBK_CACHE_REGISTRATIONS(STR_CARD_NUMBER) tablespace CSAINDEXES
   )
)tablespace CSA
/

create table CSA_IKFL.MBK_CACHE_REGISTRATIONS2 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS2 primary key (STR_CARD_NUMBER) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_REGISTRATIONS2 on CSA_IKFL.MBK_CACHE_REGISTRATIONS2(STR_CARD_NUMBER) tablespace CSAINDEXES
   )
)tablespace CSA
/

create table CSA_IKFL.MBK_CACHE_REGISTRATIONS3 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS3 primary key (STR_CARD_NUMBER) using index (
	create unique index CSA_IKFL.PK_MBK_CACHE_REGISTRATIONS3 on CSA_IKFL.MBK_CACHE_REGISTRATIONS3(STR_CARD_NUMBER) tablespace CSAINDEXES
   )
)tablespace CSA
/

-- Номер ревизии: 69382
-- Комментарий: Добавление механизма технологических перерывов в ЦСА
create table CSA_IKFL.TECHNOBREAKS 
(
   ID                   INTEGER              not null,
   ADAPTER_UUID         VARCHAR2(64)         not null,
   FROM_DATE            TIMESTAMP            not null,
   TO_DATE              TIMESTAMP            not null,
   PERIODIC             VARCHAR2(15)         not null,
   IS_DEFAULT_MESSAGE   CHAR(1)              default '1' not null,
   MESSAGE              VARCHAR2(200)        not null,
   STATUS               VARCHAR2(10)         not null,
   IS_AUTO_ENABLED      char(1)              default '0' not null,
   IS_ALLOWED_OFFLINE_PAY CHAR(1)              default '0' not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_TECHNOBREAKS primary key (ID) using index (
	create unique index CSA_IKFL.PK_TECHNOBREAKS on CSA_IKFL.TECHNOBREAKS(ID) tablespace CSAINDEXES
   )
)tablespace CSA
/
create sequence CSA_IKFL.S_TECHNOBREAKS
/

create index CSA_IKFL.I_TECHNOBREAKS on CSA_IKFL.TECHNOBREAKS (
   ADAPTER_UUID ASC,
   STATUS ASC,
   TO_DATE ASC
) tablespace CSAINDEXES
/

create unique index CSA_IKFL.UI_TECHNOBREAKS on CSA_IKFL.TECHNOBREAKS (
   UUID ASC
) tablespace CSAINDEXES
/

-- Номер ревизии: 69762
-- Комментарий: Обработка обратного потока way4(модель БД).
create table CSA_IKFL.WAY4_NOTIFICATION_JOURNAL 
(
   ID                   INTEGER              not null,
   CLIENTID             VARCHAR2(100)        not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   CB_CODE              VARCHAR2(20)         not null,
   BIRTHDATE            TIMESTAMP            not null,
   AMND_DATE            TIMESTAMP            not null
)
partition by range (AMND_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) subpartition by hash (CLIENTID) subpartitions 64
(
    partition P_FIRST values less than (to_date('01-01-2014','DD-MM-YYYY')) tablespace CSA
) tablespace CSA
/

create sequence CSA_IKFL.S_WAY4_NOTIFICATION_JOURNAL cache 10000 
/

create index CSA_IKFL.WAY4_NOTIF_CLIENTID_DATE_INDEX on CSA_IKFL.WAY4_NOTIFICATION_JOURNAL (
   CLIENTID ASC,
   AMND_DATE DESC
)
local tablespace CSAINDEXES
/

create table CSA_IKFL.CLIENT_IDS 
(
   PROFILE_ID           INTEGER              not null,
   CLIENTID             VARCHAR2(100)        not null,
   AMND_DATE            TIMESTAMP            not null
) tablespace CSA
/

create sequence CSA_IKFL.S_CLIENT_IDS
/

create index CSA_IKFL.CLIENT_IDS_PROFILE_ID_INDEX on CSA_IKFL.CLIENT_IDS (
   PROFILE_ID ASC
) tablespace CSAINDEXES
/

create index CSA_IKFL.CLIENT_IDS_CLIENT_ID_INDEX on CSA_IKFL.CLIENT_IDS (
   CLIENTID ASC
) tablespace CSAINDEXES
/

alter table CLIENT_IDS
   add constraint FK_CLIENT_IDS_TO_PROFILES foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
/


