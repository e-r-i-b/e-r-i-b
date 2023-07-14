create table ALF_RECATEGORIZATION_LOG 
(
   ID                   INTEGER              not null,
   LOG_DATE             TIMESTAMP            not null,
   DESCRIPTION          VARCHAR2(100)        not null,
   MCC_CODE             INTEGER              not null,
   ORIGINAL_CATEGORY    VARCHAR2(100)        not null,
   NEW_CATEGORY         VARCHAR2(100)        not null,
   OPERATION_TYPE       VARCHAR2(6)          not null,
   OPERATIONS_COUNT     INTEGER,
   constraint PK_ALF_RECATEGORIZATION_LOG primary key (ID)
)
partition by range (LOG_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-01-2014','DD-MM-YYYY'))
)
/
create sequence S_ALF_RECATEGORIZATION_LOG
/

create index I_ALF_RECATEGORIZATION_LOG on ALF_RECATEGORIZATION_LOG (
   LOG_DATE ASC
)
local
/


create table CONTACT_SYNC_COUNT_EXCEED_LOG 
(
   LOGIN_ID             INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   DOCUMENT             VARCHAR2(64)         not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   SYNC_DATE            TIMESTAMP            not null,
   MESSAGE              CLOB                 not null
) partition by range (SYNC_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY'))
)
/

create index I_CONTACT_SYNC_EXCEED_DATE on CONTACT_SYNC_COUNT_EXCEED_LOG (
   SYNC_DATE ASC
)
local
/