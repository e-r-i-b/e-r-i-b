alter table USERLOG rename to USERLOG_OLD
/
alter index UL_PK rename to UL_PK_OLD
/
alter index UL_DI_DATE_INDEX rename to UL_DI_DATE_IDXOLD
/
alter index UL_APP_DATE_INDEX rename to UL_APP_DATE_IDXOLD
/
alter index UL_FIO_DATE_INDEX rename to UL_FIO_DATE_IDXOLD
/
alter index UL_LOGIN_DATE_INDEX rename to UL_LOGIN_DATE_IDXOLD
/
alter index UL_FOR_REPORTS_INDEX rename to UL_FOR_REPORTS_IDXOLD
/
alter index UL_SESSION_DATE_INDEX rename to UL_SESSION_DATE_IDXOLD
/
alter index UL_SUCCESS_DESCR_INDEX rename to UL_SUCCESS_DESCR_IDXOLD
/

create table USERLOG (
   ID                   INTEGER                         not null,
   DESCRIPTION          VARCHAR2(160),
   DESCRIPTION_KEY      VARCHAR2(160),
   SUCCESS              CHAR(1)                         not null,
   START_DATE           TIMESTAMP                       not null,
   LOGIN_ID             INTEGER                         not null,
   APPLICATION          VARCHAR2(20)                    not null,
   OPERATION_KEY        CLOB,
   PARAMETERS           CLOB,
   SESSION_ID           VARCHAR2(64),
   IP_ADDRESS           VARCHAR2(15),
   EXECUTION_TIME       INTEGER,
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   DEPARTMENT_ID        NUMBER(22,0),
   USER_ID              VARCHAR2(50)
)
partition by range( START_DATE ) interval (numtoyminterval(1,'MONTH'))
(
	partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
tablespace ERIBLOG
/

create index UL_PK on USERLOG (ID) reverse tablespace ERIBLOGINDEXES
/

create index UL_FOR_REPORTS_INDEX on USERLOG (
   LOGIN_ID ASC,
   DESCRIPTION_KEY ASC,
   START_DATE ASC,
   APPLICATION ASC
)
  local tablespace ERIBLOGINDEXES
/

create index UL_SUCCESS_DESCR_INDEX on USERLOG (
   SUCCESS ASC,
   DESCRIPTION ASC
) tablespace ERIBLOGINDEXES
/

create index UL_APP_DATE_INDEX on USERLOG (
   APPLICATION ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index UL_DI_DATE_INDEX on USERLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index UL_SESSION_DATE_INDEX on USERLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index UL_LOGIN_DATE_INDEX on USERLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index UL_FIO_DATE_INDEX on USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/