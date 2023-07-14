create table SYSTEMLOG  (
   ID                   integer                         not null,
   MSG_LEVEL            char(1)                         not null,
   START_DATE           timestamp                       not null,
   LOGIN_ID             integer                         not null,
   APPLICATION          varchar2(20)                    not null,
   MESSAGE              clob,
   SESSION_ID           varchar2(64),
   IP_ADDRESS           varchar2(15),
   MESSAGE_SOURCE       varchar2(16)                    not null,
   DEPARTMENT_ID        integer,
   PERSON_ID            number(22,0),
   FIRST_NAME           varchar2(42),
   SUR_NAME             varchar2(42),
   PATR_NAME            varchar2(42),
   DEPARTMENT_NAME      varchar2(256),
   DOC_NUMBER           varchar2(512),
   DOC_SERIES           varchar2(512),
   BIRTHDAY             timestamp,
   THREAD_INFO          integer,
   USER_ID              varchar2(50)
)
partition by range (START_DATE) (
	partition P_2013_02 values less than (to_date('20130103', 'YYYYDDMM')),
	partition P_2013_03 values less than (to_date('20130104', 'YYYYDDMM')),
	partition P_MAXVALUE values less than (MAXVALUE)
)
/

create unique index SL_PK on SYSTEMLOG (
    ID
) reverse

create index SL_APP_DATE_INDEX on SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
) local


create index SL_FIO_DATE_INDEX on SYSTEMLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')),
   START_DATE DESC
) local


create index SL_DI_DATE_INDEX on SYSTEMLOG (
   DOC_NUMBER,   
   START_DATE DESC
) local

create index SL_LOGIN_DATE_INDEX on SYSTEMLOG (
    LOGIN_ID,
    START_DATE DESC
) local


create index SL_SESSION_DATE_INDEX on SYSTEMLOG (
   SESSION_ID,   
   START_DATE DESC
) local


create index SL_IP_DATE_INDEX on SYSTEMLOG (
   IP_ADDRESS,   
   START_DATE DESC
) local

create sequence S_SYSTEMLOG cache 15000