alter table CSA_SYSTEMLOG rename to CSA_SYSTEMLOG_OLD
/
alter index PK_CSA_SYSTEMLOG rename to PK_CSA_SYS_OLD
/
alter index CSA_SL_FIO_INDEX rename to CSA_SL_FIO_IDXOLD
/
alter index CSA_SL_DI_DATE_INDEX rename to CSA_SL_DI_DATE_IDXOLD
/
alter index CSA_SL_IP_DATE_INDEX rename to CSA_SL_IP_DATE_IDXOLD
/
alter index CSA_SL_APP_DATE_INDEX rename to CSA_SL_APP_DATE_IDXOLD
/
alter index CSA_SL_LOGIN_DATE_INDEX rename to CSA_SL_LOGIN_DATE_IDXOLD
/

create table CSA_SYSTEMLOG  (
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
   LOGIN                VARCHAR2(32),
   DEPARTMENT_CODE      VARCHAR2(4)
)
partition by range( START_DATE ) interval (numtoyminterval(1,'MONTH'))
(
	partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
tablespace ERIBLOG
/

create index CSA_SL_PK on CSA_SYSTEMLOG (ID) reverse tablespace ERIBLOGINDEXES
/
create index CSA_SL_APP_DATE_INDEX on CSA_SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_SL_DI_DATE_INDEX on CSA_SYSTEMLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_SL_IP_DATE_INDEX on CSA_SYSTEMLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_SL_LOGIN_DATE_INDEX on CSA_SYSTEMLOG (
   LOGIN ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_SL_FIO_INDEX on CSA_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/
