alter table SYSTEMLOG rename to SYSTEMLOG_OLD
/
alter index SL_PK rename to SL_PK_OLD
/
alter index SL_DI_DATE_INDEX rename to SL_DI_DATE_IDXOLD
/
alter index SL_IP_DATE_INDEX rename to SL_IP_DATE_IDXOLD
/
alter index SL_APP_DATE_INDEX rename to SL_APP_DATE_IDXOLD
/
alter index SL_FIO_DATE_INDEX rename to SL_FIO_DATE_IDXOLD
/
alter index SL_LOGIN_DATE_INDEX rename to SL_LOGIN_DATE_IDXOLD
/
alter index SL_SESSION_DATE_INDEX rename to SL_SESSION_DATE_IDXOLD
/

create table SYSTEMLOG  (
   ID                   INTEGER                         not null,
   MSG_LEVEL            CHAR(1)                         not null,
   START_DATE           TIMESTAMP                       not null,
   LOGIN_ID             INTEGER                         not null,
   APPLICATION          VARCHAR2(20)                    not null,
   MESSAGE              CLOB,
   SESSION_ID           VARCHAR2(64),
   IP_ADDRESS           VARCHAR2(15),
   MESSAGE_SOURCE       VARCHAR2(16)                    not null,
   DEPARTMENT_ID        INTEGER,
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   THREAD_INFO          INTEGER,
   USER_ID              VARCHAR2(50)
)
partition by range( START_DATE ) interval (numtoyminterval(1,'MONTH'))
(
	partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
tablespace ERIBLOG
/

create index SL_PK on SYSTEMLOG (ID) reverse tablespace ERIBLOGINDEXES
/

create index SL_APP_DATE_INDEX on SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index SL_DI_DATE_INDEX on SYSTEMLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index SL_LOGIN_DATE_INDEX on SYSTEMLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index SL_SESSION_DATE_INDEX on SYSTEMLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index SL_IP_DATE_INDEX on SYSTEMLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index SL_FIO_DATE_INDEX on SYSTEMLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/