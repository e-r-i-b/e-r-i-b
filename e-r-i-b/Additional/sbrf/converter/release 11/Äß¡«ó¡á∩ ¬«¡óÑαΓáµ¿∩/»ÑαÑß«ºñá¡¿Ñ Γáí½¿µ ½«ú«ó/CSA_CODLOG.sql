alter table CSA_CODLOG rename to CSA_CODLOG_OLD
/
alter index PK_CSA_CODLOG rename to PK_CSA_CODLOG_IDXO
/
alter index CSA_CL_FIO_INDEX rename to CSA_CL_FIO_IDXO
/
alter index CSA_CL_OUID_INDEX rename to CSA_CL_OUID_IDXO
/
alter index CSA_CL_DI_DATE_INDEX rename to CSA_CL_DI_DATE_IDXO
/
alter index CSA_CL_IP_DATA_INDEX rename to CSA_CL_IP_DATA_IIDXO
/
alter index CSA_CL_APP_DATE_INDEX rename to CSA_CL_APP_DATE_IDXO
/
alter index CSA_CL_LOGIN_DATE_INDEX rename to CSA_CL_LOGIN_DATE_IDXO
/
alter index CSA_CL_MGUID_DATE_INDEX rename to CSA_CL_MGUID_DATE_IDXO
/
alter index CSA_CL_APP_SYST_DATE_INDEX rename to CSA_CL_APP_SYST_DATE_IDXO
/
alter index CSA_CL_PROMO_ID_DATE_INDEX rename to CSA_CL_PROMO_ID_DATE_IDXO
/
alter index CSA_CL_MESSAGE_DEMAND_ID_INDEX rename to CSA_CL_MESSAGE_DEMAND_ID_IDXO
/

create table CSA_CODLOG  (
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
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   LOGIN                VARCHAR2(32),
   DEPARTMENT_CODE      VARCHAR2(4),
   MGUID                VARCHAR2(32),
   PROMOTER_ID          VARCHAR2(100),
   IP_ADDRESS           VARCHAR2(15),
   ERROR_CODE           VARCHAR2(10)
)
partition by range( START_DATE ) interval (numtoyminterval(1,'MONTH'))
(
	partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
tablespace ERIBLOG
/

create index CSA_CL_PK on CSA_CODLOG (ID) reverse tablespace ERIBLOGINDEXES
/

create index CSA_CL_APP_DATE_INDEX on CSA_CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_APP_SYST_DATE_INDEX on CSA_CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_OUID_INDEX on CSA_CODLOG (
   OPERATION_UID ASC
) tablespace ERIBLOGINDEXES
/

create index CSA_CL_MESSAGE_DEMAND_ID_INDEX on CSA_CODLOG (
   MESSAGE_DEMAND_ID ASC
) tablespace ERIBLOGINDEXES
/

create index CSA_CL_DI_DATE_INDEX on CSA_CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_LOGIN_DATE_INDEX on CSA_CODLOG (
   LOGIN ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_FIO_INDEX on CSA_CODLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_IP_DATA_INDEX on CSA_CODLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_MGUID_DATE_INDEX on CSA_CODLOG (
   MGUID ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

create index CSA_CL_PROMO_ID_DATE_INDEX on CSA_CODLOG (
   PROMOTER_ID ASC,
   START_DATE DESC
)
  local tablespace ERIBLOGINDEXES
/

