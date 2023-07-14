/*
	Схема: LOG_IKFL
	Табличное пространство таблиц: ERIBLOG
	Табличное пространство индексов: ERIBLOGINDEXES
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = LOG_IKFL
/

--реализовать отчет по оплате услуг в ЕРМБ:
create table LOG_IKFL.SMS_SERVICE_PAYMENT_STATISTICS 
(
   PAYMENT_ID           NUMBER               not null,
   SERVICE_PROVIDER_ID  NUMBER               not null,
   SERVICE_PROVIDER_NAME VARCHAR2(160),
   PAYMENT_STATE        VARCHAR2(10 BYTE)    not null,
   AMOUNT               NUMBER(19,4)         not null,
   CURRENCY             CHAR(3)              not null,
   TB                   VARCHAR2(4 BYTE)     not null,
   FINAL_STATUS_DATE    TIMESTAMP            not null
) tablespace ERIBLOG
partition by range (FINAL_STATUS_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-01-2014','DD-MM-YYYY')) tablespace ERIBLOG
)
/

create index SRB_IKFL.SERV_PAY_STATISTICS_DATE_IDX on SRB_IKFL.SMS_SERVICE_PAYMENT_STATISTICS (
   FINAL_STATUS_DATE ASC
) local tablespace ERIBLOGINDEXES
/


/*============================================================== */
/* GUEST_CSA_SYSTEMLOG                                           */
/*============================================================== */
create sequence LOG_IKFL.S_GUEST_CSA_SYSTEMLOG cache 15000
/

create table LOG_IKFL.GUEST_CSA_SYSTEMLOG_B 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(50),
   DOC_SERIES           VARCHAR2(50),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
) 
tablespace ERIBLOG 
partition by range (START_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('1-01-2015','DD-MM-YYYY')) tablespace ERIBLOG
)
/

create index LOG_IKFL.I_G_CSA_SL_PHONE_DATE_B on LOG_IKFL.GUEST_CSA_SYSTEMLOG_B (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CSA_SL_FIO_B on LOG_IKFL.GUEST_CSA_SYSTEMLOG_B (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create table LOG_IKFL.GUEST_CSA_SYSTEMLOG_S 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(50),
   DOC_SERIES           VARCHAR2(50),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
tablespace ERIBLOG 
LOB("MESSAGE") store as SECUREFILE(COMPRESS HIGH)
compress for OLTP
partition by range (START_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('1-01-2015','DD-MM-YYYY')) tablespace ERIBLOG
)
/

create index LOG_IKFL.I_G_CSA_SL_PHONE_DATE_S on LOG_IKFL.GUEST_CSA_SYSTEMLOG_S (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CSA_SL_FIO_S on LOG_IKFL.GUEST_CSA_SYSTEMLOG_S (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create or replace force view LOG_IKFL."GUEST_CSA_SYSTEMLOG" ( "ID", "MSG_LEVEL", "START_DATE", "GUEST_CODE", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "DEPARTMENT_CODE", "LOGIN", "PHONE_NUMBER", "THREAD_INFO", "LOG_UID", "ADD_INFO" ) as
select "ID", "MSG_LEVEL", "START_DATE", "GUEST_CODE", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "DEPARTMENT_CODE", "LOGIN", "PHONE_NUMBER", "THREAD_INFO", "LOG_UID", "ADD_INFO" from GUEST_CSA_SYSTEMLOG_B 
union all
select "ID", "MSG_LEVEL", "START_DATE", "GUEST_CODE", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "DEPARTMENT_CODE", "LOGIN", "PHONE_NUMBER", "THREAD_INFO", "LOG_UID", "ADD_INFO" from GUEST_CSA_SYSTEMLOG_S
/

create or replace trigger LOG_IKFL."GUEST_CSA_SYSTEMLOG_IOI" instead of insert on LOG_IKFL.GUEST_CSA_SYSTEMLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into GUEST_CSA_SYSTEMLOG_S';
    insert_b_q constant varchar2(1024):='insert into GUEST_CSA_SYSTEMLOG_B';
    insert_f constant varchar2(1024):=insert_f constant varchar2(1024):='(ID, MSG_LEVEL, START_DATE, GUEST_CODE, APPLICATION, MESSAGE, SESSION_ID, IP_ADDRESS, MESSAGE_SOURCE, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDATE, DEPARTMENT_CODE, LOGIN, PHONE_NUMBER, THREAD_INFO, LOG_UID, ADD_INFO) values ( :ID, :MSG_LEVEL, :START_DATE, :GUEST_CODE, :APPLICATION, :MESSAGE, :SESSION_ID, :IP_ADDRESS, :MESSAGE_SOURCE, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDATE, :DEPARTMENT_CODE, :LOGIN, :PHONE_NUMBER, :THREAD_INFO, :LOG_UID, :ADD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='GUEST_CSA_SYSTEMLOG';
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
        execute immediate insert_str using :new.ID, :new.MSG_LEVEL, :new.START_DATE, :new.GUEST_CODE, :new.APPLICATION, :new.MESSAGE, :new.SESSION_ID, :new.IP_ADDRESS, :new.MESSAGE_SOURCE, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDATE, :new.DEPARTMENT_CODE, :new.LOGIN, :new.PHONE_NUMBER, :new.THREAD_INFO, :new.LOG_UID, :new.ADD_INFO;
    end if;
end;
/

/*============================================================== */
/* GUEST_SYSTEMLOG                                               */
/*============================================================== */
create sequence LOG_IKFL.S_GUEST_SYSTEMLOG cache 15000
/

create table LOG_IKFL.GUEST_SYSTEMLOG_B 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(50),
   DOC_SERIES           VARCHAR2(50),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   NODE_ID              INTEGER              not null,
   DEPARTMENT_NAME      VARCHAR2(256)
) tablespace ERIBLOG 
partition by range (START_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('1-01-2015','DD-MM-YYYY'))
)
/

create index LOG_IKFL.I_G_SL_PHONE_DATE_B on GUEST_SYSTEMLOG_B (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_SL_FIO_DATE_B on GUEST_SYSTEMLOG_B (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create table LOG_IKFL.GUEST_SYSTEMLOG_S 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(50),
   DOC_SERIES           VARCHAR2(50),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   NODE_ID              INTEGER              not null,
   DEPARTMENT_NAME      VARCHAR2(256)
)
tablespace ERIBLOG 
LOB("MESSAGE") store as SECUREFILE(COMPRESS HIGH)
compress for OLTP
partition by range (START_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('1-01-2015','DD-MM-YYYY'))
)
/

create index LOG_IKFL.I_G_SL_PHONE_DATE_S on GUEST_SYSTEMLOG_S (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_SL_FIO_DATE_S on GUEST_SYSTEMLOG_S (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create or replace force view LOG_IKFL."GUEST_SYSTEMLOG" ( "ID", "MSG_LEVEL", "START_DATE", "GUEST_CODE", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "TB", "LOGIN", "PHONE_NUMBER", "THREAD_INFO", "NODE_ID", "DEPARTMENT_NAME" ) as
select "ID", "MSG_LEVEL", "START_DATE", "GUEST_CODE", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "TB", "LOGIN", "PHONE_NUMBER", "THREAD_INFO", "NODE_ID", "DEPARTMENT_NAME"  from GUEST_SYSTEMLOG_B 
union all
select "ID", "MSG_LEVEL", "START_DATE", "GUEST_CODE", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "TB", "LOGIN", "PHONE_NUMBER", "THREAD_INFO", "NODE_ID", "DEPARTMENT_NAME"  from GUEST_SYSTEMLOG_S
/

create or replace trigger LOG_IKFL."GUEST_SYSTEMLOG_IOI" instead of insert on LOG_IKFL.GUEST_SYSTEMLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into GUEST_SYSTEMLOG_S';
    insert_b_q constant varchar2(1024):='insert into GUEST_SYSTEMLOG_B';
    insert_f constant varchar2(1024):=insert_f constant varchar2(1024):='( ID, MSG_LEVEL, START_DATE, GUEST_CODE, APPLICATION, MESSAGE, SESSION_ID, IP_ADDRESS, MESSAGE_SOURCE, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDATE, TB, LOGIN, PHONE_NUMBER, THREAD_INFO, NODE_ID, DEPARTMENT_NAME ) values ( :ID, :MSG_LEVEL, :START_DATE, :GUEST_CODE, :APPLICATION, :MESSAGE, :SESSION_ID, :IP_ADDRESS, :MESSAGE_SOURCE, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDATE, :TB, :LOGIN, :PHONE_NUMBER, :THREAD_INFO, :NODE_ID, :DEPARTMENT_NAME )';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='GUEST_SYSTEMLOG';
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
        execute immediate insert_str using :new.ID, :new.MSG_LEVEL, :new.START_DATE, :new.GUEST_CODE, :new.APPLICATION, :new.MESSAGE, :new.SESSION_ID, :new.IP_ADDRESS, :new.MESSAGE_SOURCE, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDATE, :new.TB, :new.LOGIN, :new.PHONE_NUMBER, :new.THREAD_INFO, :new.NODE_ID, :new.DEPARTMENT_NAME;
    end if;
end;
/

/*============================================================== */
/* GUEST_CODLOG                                                  */
/*============================================================== */
create sequence S_GUEST_CODLOG cache 15000
/

create table LOG_IKFL.GUEST_CODLOG_B 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   GUEST_CODE           INTEGER              not null,
   SESSION_ID           VARCHAR2(64)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   TB                   VARCHAR2(4),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
tablespace ERIBLOG 
partition by range (START_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
subpartition by hash ("ID") subpartitions 16
(
	partition P_START values less than (to_date('01-01-2015','DD-MM-YYYY')) tablespace ERIBLOG 
)
/

create index LOG_IKFL.I_G_CL_PHONE_DATE_B on LOG_IKFL.GUEST_CODLOG_B (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CL_FIO_DATE_B on LOG_IKFL.GUEST_CODLOG_B (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CL_PK_B on LOG_IKFL.GUEST_CODLOG_B (
   ID ASC
) reverse global tablespace ERIBLOGINDEXES
/

create table LOG_IKFL.GUEST_CODLOG_S 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   GUEST_CODE           INTEGER              not null,
   SESSION_ID           VARCHAR2(64)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   TB                   VARCHAR2(4),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
tablespace ERIBLOG 
LOB("MESSAGE_DEMAND") store as SECUREFILE(COMPRESS HIGH)
LOB("MESSAGE_ANSWER") store as SECUREFILE(COMPRESS HIGH)
compress for OLTP
partition by range (START_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
subpartition by hash ("ID") subpartitions 16
(
	partition P_START values less than (to_date('01-01-2015','DD-MM-YYYY')) tablespace ERIBLOG 
)
/

create index LOG_IKFL.I_G_CL_PHONE_DATE_S on LOG_IKFL.GUEST_CODLOG_S (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CL_FIO_DATE_S on LOG_IKFL.GUEST_CODLOG_S (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CL_PK_S on LOG_IKFL.GUEST_CODLOG_S (
   ID ASC
) reverse global tablespace ERIBLOGINDEXES
/

create or replace force view LOG_IKFL."GUEST_CODLOG" ( "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "GUEST_CODE", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "TB", "BIRTHDATE", "NODE_ID", "THREAD_INFO", "PHONE_NUMBER", "GUEST_LOGIN" ) as
select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "GUEST_CODE", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "TB", "BIRTHDATE", "NODE_ID", "THREAD_INFO", "PHONE_NUMBER", "GUEST_LOGIN" from GUEST_CODLOG_B 
union all
select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "GUEST_CODE", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "TB", "BIRTHDATE", "NODE_ID", "THREAD_INFO", "PHONE_NUMBER", "GUEST_LOGIN" from GUEST_CODLOG_S
/

create or replace trigger LOG_IKFL."GUEST_CODLOG_IOI" instead of insert on LOG_IKFL.GUEST_CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into GUEST_CODLOG_S';
    insert_b_q constant varchar2(1024):='insert into GUEST_CODLOG_B';
    insert_f constant varchar2(1024):=insert_f constant varchar2(1024):='( ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, GUEST_CODE, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, TB, BIRTHDATE, NODE_ID, THREAD_INFO, PHONE_NUMBER, GUEST_LOGIN ) values ( :ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :GUEST_CODE, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :TB, :BIRTHDATE, :NODE_ID, :THREAD_INFO, :PHONE_NUMBER, :GUEST_LOGIN )';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='GUEST_CODLOG';
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
        execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.GUEST_CODE, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.TB, :new.BIRTHDATE, :new.NODE_ID, :new.THREAD_INFO, :new.PHONE_NUMBER, :new.GUEST_LOGIN;
    end if;
end;
/

/*============================================================== */
/* GUEST_USERLOG                                                 */
/*============================================================== */
create sequence LOG_IKFL.S_GUEST_USERLOG cache 15000
/

create table LOG_IKFL.GUEST_USERLOG 
(
   ID                   INTEGER              not null,
   DESCRIPTION          VARCHAR2(160),
   DESCRIPTION_KEY      VARCHAR2(160),
   SUCCESS              CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   OPERATION_KEY        VARCHAR2(300),
   PARAMETERS           VARCHAR2(4000),
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   EXECUTION_TIME       INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   TB                   VARCHAR2(4),
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
tablespace ERIBLOG 
partition by range (START_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
(
	partition P_START values less than (to_date('01-01-2015','DD-MM-YYYY')) tablespace ERIBLOG 
)
/

create index LOG_IKFL.I_G_UL_PHONE_DATE on LOG_IKFL.GUEST_USERLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_UL_PK on LOG_IKFL.GUEST_USERLOG (
   ID ASC
) local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.I_G_UL_FIO_DATE on LOG_IKFL.GUEST_USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

/*============================================================== */
/* GUEST_CSA_CODLOG                                              */
/*============================================================== */
create sequence LOG_IKFL.S_GUEST_CSA_CODLOG cache 15000
/

create table LOG_IKFL.GUEST_CSA_CODLOG_B 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   OPERATION_UID        VARCHAR2(32)         not null,
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_CODE           INTEGER              not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   ERROR_CODE           VARCHAR2(10),
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
tablespace ERIBLOG 
partition by range (START_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('1-01-2015','DD-MM-YYYY'))
)
/

create index LOG_IKFL.G_CSA_CL_PK_B on LOG_IKFL.GUEST_CSA_CODLOG_B (
   ID ASC
) reverse local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.I_G_CSA_CL_FIO_B on LOG_IKFL.GUEST_CSA_CODLOG_B (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CSA_CL_PHONE_DATE_B on LOG_IKFL.GUEST_CSA_CODLOG_B (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create table LOG_IKFL.GUEST_CSA_CODLOG_S 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   OPERATION_UID        VARCHAR2(32)         not null,
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_CODE           INTEGER              not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   ERROR_CODE           VARCHAR2(10),
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
tablespace ERIBLOG 
LOB("MESSAGE_DEMAND") store as SECUREFILE(COMPRESS HIGH)
LOB("MESSAGE_ANSWER") store as SECUREFILE(COMPRESS HIGH)
compress for OLTP
partition by range (START_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('1-01-2015','DD-MM-YYYY'))
)
/

create index LOG_IKFL.G_CSA_CL_PK_S on LOG_IKFL.GUEST_CSA_CODLOG_S (
   ID ASC
) reverse local tablespace ERIBLOGINDEXES
/

create index LOG_IKFL.I_G_CSA_CL_FIO_S on LOG_IKFL.GUEST_CSA_CODLOG_S (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create index LOG_IKFL.I_G_CSA_CL_PHONE_DATE_S on LOG_IKFL.GUEST_CSA_CODLOG_S (
   PHONE_NUMBER ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES compress 1
/

create or replace force view LOG_IKFL."GUEST_CSA_CODLOG" ( "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "DEPARTMENT_CODE", "OPERATION_UID", "LOGIN", "PHONE_NUMBER", "GUEST_CODE", "IP_ADDRESS", "ERROR_CODE", "THREAD_INFO", "LOG_UID", "ADD_INFO" ) as
select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "DEPARTMENT_CODE", "OPERATION_UID", "LOGIN", "PHONE_NUMBER", "GUEST_CODE", "IP_ADDRESS", "ERROR_CODE", "THREAD_INFO", "LOG_UID", "ADD_INFO" from GUEST_CSA_CODLOG_B 
union all
select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDATE", "DEPARTMENT_CODE", "OPERATION_UID", "LOGIN", "PHONE_NUMBER", "GUEST_CODE", "IP_ADDRESS", "ERROR_CODE", "THREAD_INFO", "LOG_UID", "ADD_INFO" from GUEST_CSA_CODLOG_S
/

create or replace trigger LOG_IKFL."GUEST_CSA_CODLOG_IOI" instead of insert on LOG_IKFL.GUEST_CSA_CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into GUEST_CSA_CODLOG_S';
    insert_b_q constant varchar2(1024):='insert into GUEST_CSA_CODLOG_B';
    insert_f constant varchar2(1024):=insert_f constant varchar2(1024):='( ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDATE, DEPARTMENT_CODE, OPERATION_UID, LOGIN, PHONE_NUMBER, GUEST_CODE, IP_ADDRESS, ERROR_CODE, THREAD_INFO, LOG_UID, ADD_INFO ) values ( :ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDATE, :DEPARTMENT_CODE, :OPERATION_UID, :LOGIN, :PHONE_NUMBER, :GUEST_CODE, :IP_ADDRESS, :ERROR_CODE, :THREAD_INFO, :LOG_UID, :ADD_INFO )';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='GUEST_CSA_CODLOG';
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
        execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDATE, :new.DEPARTMENT_CODE, :new.OPERATION_UID, :new.LOGIN, :new.PHONE_NUMBER, :new.GUEST_CODE, :new.IP_ADDRESS, :new.ERROR_CODE, :new.THREAD_INFO, :new.LOG_UID, :new.ADD_INFO;
    end if;
end;
/
