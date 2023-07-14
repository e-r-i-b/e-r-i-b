create table USERLOG  (
   ID                   integer                         not null,
   DESCRIPTION          varchar2(160),
   DESCRIPTION_KEY      varchar2(160),
   SUCCESS              char(1)                         not null,
   START_DATE           timestamp                       not null,
   LOGIN_ID             integer                         not null,
   APPLICATION          varchar2(20)                    not null,
   OPERATION_KEY        clob,
   PARAMETERS           clob,
   SESSION_ID           varchar2(64),
   IP_ADDRESS           varchar2(15),
   EXECUTION_TIME       integer,
   PERSON_ID            number(22,0),
   FIRST_NAME           varchar2(42),
   SUR_NAME             varchar2(42),
   PATR_NAME            varchar2(42),
   DEPARTMENT_NAME      varchar2(256),
   DOC_NUMBER           varchar2(512),
   DOC_SERIES           varchar2(512),
   BIRTHDAY             timestamp,
   DEPARTMENT_ID        number(22,0),
   USER_ID              varchar2(50)
)
partition by range (START_DATE) (
	partition P_2013_02 values less than (to_date('20130103', 'YYYYDDMM')),
	partition P_2013_03 values less than (to_date('20130104', 'YYYYDDMM')),
	partition P_MAXVALUE values less than (MAXVALUE)
)
/

create unique index UL_PK on USERLOG (
    ID
) reverse


create index UL_APP_DATE_INDEX on USERLOG (
   APPLICATION ASC,
   START_DATE DESC
) local


create index UL_FIO_DATE_INDEX on USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
   START_DATE DESC
) local


create index UL_DI_DATE_INDEX on USERLOG (
   DOC_NUMBER,   
   START_DATE DESC
) local


create index UL_LOGIN_DATE_INDEX on USERLOG (
    LOGIN_ID,
    START_DATE DESC
) local


create index UL_FOR_REPORTS_INDEX on USERLOG (
   LOGIN_ID ASC,
   DESCRIPTION_KEY ASC,
   START_DATE ASC,
   APPLICATION ASC
) local


create index UL_SUCCESS_DESCR_INDEX on USERLOG (
   SUCCESS ASC,
   DESCRIPTION ASC
)


create index UL_SESSION_DATE_INDEX on USERLOG (
   SESSION_ID,   
   START_DATE DESC
) local

create sequence S_USERLOG cache 15000