--переименование CODLOG
alter table CODLOG rename to CODLOG_OLD
/
alter index CL_PK rename to CL_PK_OLD
/
alter index CL_OUID_INDEX rename to CL_OUID_IDXOLD
/
alter index CL_DI_DATE_INDEX rename to CL_DI_DATE_IDXOLD
/
alter index CL_APP_DATE_INDEX rename to CL_APP_DATE_IDXOLD
/
alter index CL_FIO_DATE_INDEX rename to CL_FIO_DATE_IDXOLD
/
alter index CL_LOGIN_DATE_INDEX rename to CL_LOGIN_DATE_IDXOLD
/
alter index CL_SESSION_DATE_INDEX rename to CL_SESSION_DATE_IDXOLD
/
alter index CL_APP_SYST_DATE_INDEX rename to CL_APP_SYST_DATE_IDXOLD
/
alter index CL_MESSAGE_DEMAND_ID_INDEX rename to CL_M_DEMAND_ID_IDXOLD
/
create table CODLOG  (
   ID                   INTEGER                         not null,
   START_DATE           TIMESTAMP                       not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)                    not null,
   MESSAGE_TYPE         VARCHAR2(80)                    not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)                    not null,
   MESSAGE_DEMAND       CLOB                            not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)                    not null,
   LOGIN_ID             INTEGER,
   DEPARTMENT_ID        NUMBER(22,0),
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32)
)
partition by range( START_DATE ) interval (numtoyminterval(1,'MONTH'))
(
	partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
tablespace ERIBLOG
/

create index CL_PK on CODLOG(ID) reverse tablespace ERIBLOGINDEXES
/

create index CL_APP_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local tablespace ERIBLOGINDEXES
/

create index CL_APP_SYST_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
local tablespace ERIBLOGINDEXES
/

create index CL_OUID_INDEX on CODLOG (
   OPERATION_UID ASC
) tablespace ERIBLOGINDEXES
/

create index CL_SESSION_DATE_INDEX on CODLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
local tablespace ERIBLOGINDEXES
/

create index CL_MESSAGE_DEMAND_ID_INDEX on CODLOG (
   MESSAGE_DEMAND_ID ASC
) tablespace ERIBLOGINDEXES
/

create index CL_DI_DATE_INDEX on CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local tablespace ERIBLOGINDEXES
/
create index CL_LOGIN_DATE_INDEX on CODLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
local tablespace ERIBLOGINDEXES
/

create index CL_FIO_DATE_INDEX on CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
   START_DATE DESC
)
local tablespace ERIBLOGINDEXES
/

