--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = LOG_IKFL
/

create table LOG_IKFL.FINANCIAL_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(40)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(80)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   LOGIN_ID             INTEGER,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   NODE_ID              INTEGER,
   TB                   VARCHAR2(4),
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(7),
   THREAD_INFO          INTEGER
)
partition by range("START_DATE") interval ( numtoyminterval(1,'MONTH') ) subpartition by hash("ID") subpartitions 16
(
	partition "P_START" values less than (timestamp' 2014-01-01 00:00:00')  tablespace ERIBLOG
) tablespace ERIBLOG
/

create sequence LOG_IKFLS_FINANCIAL_CODLOG cache 10000
/

create index LOG_IKFL.FCL_APP_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   APPLICATION ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_APP_SYST_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_OUID_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   OPERATION_UID ASC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_SESSION_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   SESSION_ID ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_MESSAGE_DEMAND_ID_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   MESSAGE_DEMAND_ID ASC
)
local
/

create index LOG_IKFL.FCL_DI_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_LOGIN_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   LOGIN_ID ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_FIO_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_PK on LOG_IKFL.FINANCIAL_CODLOG (
   ID ASC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_FIO_BIRTHDAY_DATE_INDEX on LOG_IKFL.FINANCIAL_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
) local tablespace ERIBLOGINDEXES
/

-- Номер ревизии: 69557
-- Номер версии: 1.18
-- Комментарий: Подбор номеров. Логирование.
create table LOG_IKFL.REQUEST_CARD_BY_PHONE_LOG 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   EVENT_DATE           TIMESTAMP            not null,
   BLOCK_ID             INTEGER              not null,
   FIO                  VARCHAR2(360)        not null,
   DOC_TYPE             VARCHAR2(32)         not null,
   DOC_NUMBER           VARCHAR2(32)         not null,
   BIRTHDAY             TIMESTAMP
)
partition by range  (EVENT_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-01-2014','DD-MM-YYYY')) tablespace ERIBLOG
) tablespace ERIBLOG
/

create sequence LOG_IKFL.S_REQUEST_CARD_BY_PHONE_LOG
/

create index LOG_IKFL.I_REQUEST_CARD_BY_PHONE_LOG on LOG_IKFL.REQUEST_CARD_BY_PHONE_LOG (
   BLOCK_ID ASC,
   EVENT_DATE ASC
)
local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.I_REQ_CARD_BY_PHONE_LOG_LOGIN on LOG_IKFL.REQUEST_CARD_BY_PHONE_LOG (
   BLOCK_ID ASC,
   LOGIN_ID ASC,
   EVENT_DATE ASC
)
local tablespace ERIBLOGINDEXES
/
