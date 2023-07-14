create table CODLOG  (
   ID                   integer                         not null,
   START_DATE           timestamp                       not null,
   EXECUTION_TIME       integer,
   APPLICATION          varchar2(20)                    not null,
   MESSAGE_TYPE         varchar2(80)                    not null,
   MESSAGE_DEMAND_ID    varchar2(40)                    not null,
   MESSAGE_DEMAND       clob                            not null,
   MESSAGE_ANSWER_ID    varchar2(40),
   MESSAGE_ANSWER       clob,
   DIRECTION            varchar2(10)                    not null,
   LINK                 varchar2(32),
   SYST                 varchar2(10)                    not null,
   MESSAGE_ANSWER_TYPE  varchar2(80),
   LOGIN_ID             integer,
   DEPARTMENT_ID        number(22,0),
   SESSION_ID           varchar2(64),
   PERSON_ID            number(22,0),
   FIRST_NAME           varchar2(42),
   SUR_NAME             varchar2(42),
   PATR_NAME            varchar2(42),
   DEPARTMENT_NAME      varchar2(256),
   DOC_NUMBER           varchar2(512),
   DOC_SERIES           varchar2(512),
   BIRTHDAY             timestamp,
   OPERATION_UID        varchar2(32),
   DOCUMENT_ID          integer
)
partition by range (START_DATE) (
	partition P_2013_02 values less than (to_date('20130103', 'YYYYDDMM')),
	partition P_2013_03 values less than (to_date('20130104', 'YYYYDDMM')),
	partition P_MAXVALUE values less than (MAXVALUE)
)
/

create unique index CL_PK on CODLOG (
    ID
) reverse
/

create index CL_APP_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)  local
/

create index CL_APP_SYST_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)  local
/

create index CL_FIO_DATE_INDEX on CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
   START_DATE DESC
) local
/

create index CL_DI_DATE_INDEX on CODLOG (
   DOC_NUMBER,   
   START_DATE DESC
) local
/

create index CL_LOGIN_DATE_INDEX on CODLOG (
    LOGIN_ID,
    START_DATE DESC
) local
/

create index CL_MESSAGE_DEMAND_ID_INDEX on CODLOG (
   MESSAGE_DEMAND_ID ASC
)
/

create index CL_OUID_INDEX on CODLOG (
   OPERATION_UID ASC
)
/

create index CL_SESSION_DATE_INDEX on CODLOG (
   SESSION_ID,   
   START_DATE DESC
) local
/

create sequence S_CODLOG cache 15000