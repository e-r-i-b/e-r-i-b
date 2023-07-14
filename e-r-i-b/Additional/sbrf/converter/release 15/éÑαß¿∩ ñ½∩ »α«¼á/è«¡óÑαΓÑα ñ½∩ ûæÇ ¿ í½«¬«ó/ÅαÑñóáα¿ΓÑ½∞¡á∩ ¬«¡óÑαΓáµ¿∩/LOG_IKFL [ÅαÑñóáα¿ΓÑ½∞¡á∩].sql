--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = LOG_IKFL
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

--Сжатая таблица
create table LOG_IKFL.FINANCIAL_CODLOG_B 
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

create sequence LOG_IKFL.S_FINANCIAL_CODLOG cache 15000
/

create index LOG_IKFL.FCL_APP_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   APPLICATION ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_APP_SYST_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 2
/

create index LOG_IKFL.FCL_OUID_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   OPERATION_UID ASC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_SESSION_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   SESSION_ID ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_MESSAGE_DEMAND_ID_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   MESSAGE_DEMAND_ID ASC
)
local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_DI_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   DOC_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_LOGIN_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   LOGIN_ID ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_FIO_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_PK_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   ID ASC
) reverse local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_FIO_BIRTHDAY_DATE_INDEX_B on LOG_IKFL.FINANCIAL_CODLOG_B (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
) local tablespace ERIBLOGINDEXES compress 2
/

--Сжатая таблица
create table LOG_IKFL.FINANCIAL_CODLOG_S 
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
tablespace ERIBLOG 
LOB("MESSAGE_DEMAND") store as SECUREFILE(COMPRESS HIGH)
LOB("MESSAGE_ANSWER") store as SECUREFILE(COMPRESS HIGH)
compress for OLTP
partition by range("START_DATE") interval ( numtoyminterval(1,'MONTH') ) subpartition by hash("ID") subpartitions 16
(
	partition "P_START" values less than (timestamp' 2014-01-01 00:00:00')  tablespace ERIBLOG
) 
/

create index LOG_IKFL.FCL_APP_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   APPLICATION ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES  compress 1
/

create index LOG_IKFL.FCL_APP_SYST_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 2
/

create index LOG_IKFL.FCL_OUID_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   OPERATION_UID ASC
) local tablespace ERIBLOGINDEXES 
/

create index LOG_IKFL.FCL_SESSION_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   SESSION_ID ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_MESSAGE_DEMAND_ID_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   MESSAGE_DEMAND_ID ASC
)
local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_DI_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   DOC_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_LOGIN_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   LOGIN_ID ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_FIO_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.FCL_PK_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   ID ASC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.FCL_FIO_BIRTHDAY_DATE_INDEX_S on LOG_IKFL.FINANCIAL_CODLOG_S (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
) local tablespace ERIBLOGINDEXES  compress 2
/

create or replace force view "FINANCIAL_CODLOG" ( "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO" ) as
select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO" from FINANCIAL_CODLOG_B  
union all
select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO" from FINANCIAL_CODLOG_S
/

create or replace trigger "FINANCIAL_CODLOG_IOI" instead of insert on FINANCIAL_CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into FINANCIAL_CODLOG_S';
    insert_b_q constant varchar2(1024):='insert into FINANCIAL_CODLOG_B';
    insert_f constant varchar2(1024):=insert_f constant varchar2(1024):='(ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, LOGIN_ID, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, OPERATION_UID, NODE_ID, TB, OSB, VSP, THREAD_INFO)	values ( :ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :LOGIN_ID, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :OPERATION_UID, :NODE_ID, :TB, :OSB, :VSP, :THREAD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='FINANCIAL_CODLOG';
    exception
        when NO_DATA_FOUND then
            flag:='S';
        when TOO_MANY_ROWS then
            flag:='S';
    end;
    if (flag='F') then
      null;
    else
        if (flag='B') then
            insert_str :=insert_b_q || insert_f;
        else
            insert_str :=insert_s_q || insert_f;
        end if;
        execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.LOGIN_ID, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.OPERATION_UID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.THREAD_INFO;
    end if;
end;

insert into LOG_CONFIG values ('FINANCIAL_CODLOG', 'S');

-- Номер ревизии: 67924
-- Номер версии: 1.18
-- Комментарий: Stand-In. Журналы логов.
create index LOG_IKFL.CL_FIO_BIRTHDAY_DATE_INDEX_S on LOG_IKFL.CODLOG_S (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32 compress 2 nologging
/

create index LOG_IKFL.SL_FIO_BIRTHDAY_DATE_INDEX_S on LOG_IKFL.SYSTEMLOG_S (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32 compress 2 nologging
/

create index LOG_IKFL.UL_FIO_BIRTHDAY_DATE_INDEX_S on LOG_IKFL.USERLOG_S (
    UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
    BIRTHDAY ASC,
    START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32 compress 2 nologging
/


create index LOG_IKFL.CL_FIO_BIRTHDAY_DATE_INDEX_B on LOG_IKFL.CODLOG_B (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32 compress 2 nologging
/

create index LOG_IKFL.SL_FIO_BIRTHDAY_DATE_INDEX_B on LOG_IKFL.SYSTEMLOG_B (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32 compress 2 nologging
/

create index LOG_IKFL.UL_FIO_BIRTHDAY_DATE_INDEX_B on LOG_IKFL.USERLOG_B (
    UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
    BIRTHDAY ASC,
    START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32 compress 2 nologging
/

alter index LOG_IKFL.CL_FIO_BIRTHDAY_DATE_INDEX_S noparallel logging
/
alter index LOG_IKFL.SL_FIO_BIRTHDAY_DATE_INDEX_S noparallel logging
/
alter index LOG_IKFL.UL_FIO_BIRTHDAY_DATE_INDEX_S noparallel logging
/
alter index LOG_IKFL.CL_FIO_BIRTHDAY_DATE_INDEX_B noparallel logging
/
alter index LOG_IKFL.SL_FIO_BIRTHDAY_DATE_INDEX_B noparallel logging
/
alter index LOG_IKFL.UL_FIO_BIRTHDAY_DATE_INDEX_B noparallel logging
/
