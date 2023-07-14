--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/


--Пересоздаем таблицы логов:
alter index CSA_CL_PROMO_ID_DATE_INDEX rename to OCSA_CL_PROMO_ID_DATE_IDX
/
alter index CSA_CL_MGUID_DATE_INDEX rename to OCSA_CL_MGUID_DATE_IDX
/
alter index CSA_CL_IP_DATA_INDEX rename to OCSA_CL_IP_DATA_IDX
/
alter index CSA_CL_FIO_INDEX rename to OCSA_CL_FIO_IDX
/
alter index CSA_CL_LOGIN_DATE_INDEX rename to OCSA_CL_LOGIN_DATE_IDX
/
alter index CSA_CL_DI_DATE_INDEX rename to OCSA_CL_DI_DATE_IDX
/
alter index CSA_CL_MESSAGE_DEMAND_ID_INDEX rename to OCSA_CL_MESSAGE_DEMAND_ID_IDX
/
alter index CSA_CL_OUID_INDEX rename to OCSA_CL_OUID_IDX
/
alter index CSA_CL_APP_SYST_DATE_INDEX rename to OCSA_CL_APP_SYST_DATE_IDX
/
alter index CSA_CL_APP_DATE_INDEX rename to OCSA_CL_APP_DATE_IDX
/
alter index CSA_CL_PK rename to OCSA_CL_PK
/
alter table CSA_CODLOG rename to OCSA_CODLOG
/

create table CSA_CODLOG
  (
    ID                number         not null,
    START_DATE        timestamp (6)  not null,
    EXECUTION_TIME    number,
    APPLICATION       varchar2(20)   not null,
    MESSAGE_TYPE      varchar2(80)   not null,
    MESSAGE_DEMAND_ID varchar2(40)   not null,
    MESSAGE_DEMAND    clob           not null,
    MESSAGE_ANSWER_ID varchar2(40),
    MESSAGE_ANSWER    clob,
    SYST              varchar2(10)   not null,
    SESSION_ID        varchar2(64),
    FIRST_NAME        varchar2(42),
    SUR_NAME          varchar2(42),
    PATR_NAME         varchar2(42),
    DOC_NUMBER        varchar2(512),
    DOC_SERIES        varchar2(512),
    BIRTHDAY          timestamp (6),
    OPERATION_UID     varchar2(32),
    LOGIN             varchar2(32),
    DEPARTMENT_CODE   varchar2(4),
    MGUID             varchar2(32),
    PROMOTER_ID       varchar2(100),
    IP_ADDRESS        varchar2(15),
    ERROR_CODE        varchar2(10),
    LOG_UID           varchar2(32)
  )
  tablespace ERIBLOG 
  partition by range(START_DATE) interval ( numtoyminterval(1,'MONTH'))
  (
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY')) tablespace ERIBLOG
  )
/

create index CSA_CL_PK on CSA_CODLOG  (
    ID
  )
  reverse tablespace ERIBLOGINDEXES
/

create index CSA_CL_APP_DATE_INDEX on CSA_CODLOG  (
    APPLICATION,
    START_DATE desc
  )
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_APP_SYST_DATE_INDEX on CSA_CODLOG  (
    APPLICATION,
    SYST,
    START_DATE desc
  )
  compress 2 
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_DI_DATE_INDEX on CSA_CODLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
) 
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_FIO_INDEX on CSA_CODLOG  (
    upper(replace(replace(SUR_NAME||FIRST_NAME||PATR_NAME,' ',''),'-','')),
    START_DATE desc
  )
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_IP_DATA_INDEX on CSA_CODLOG  (
    IP_ADDRESS,
    START_DATE desc
  )
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_LOGIN_DATE_INDEX on CSA_CODLOG  (
    LOGIN,
    START_DATE desc
  )
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_MESSAGE_DEMAND_ID_INDEX on CSA_CODLOG  (
    MESSAGE_DEMAND_ID
  )
  tablespace ERIBLOGINDEXES
/
create index CSA_CL_MGUID_DATE_INDEX on CSA_CODLOG  (
    MGUID,
    START_DATE desc
  )
  compress 1 tablespace ERIBLOGINDEXES local
/
create index CSA_CL_OUID_INDEX on CSA_CODLOG  (
    OPERATION_UID
  )
  tablespace ERIBLOGINDEXES
/
create index CSA_CL_PROMO_ID_DATE_INDEX on CSA_CODLOG  (
    PROMOTER_ID,
    START_DATE desc
  )
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_CL_LOG_UID_INDEX on CSA_CODLOG ( 
  LOG_UID ASC 
) 
  tablespace ERIBLOGINDEXES 
/


alter index CSA_SL_APP_DATE_INDEX rename to OCSA_SL_APP_DATE_IDX
/
alter index CSA_SL_PK rename to OCSA_SL_PK
/
alter index CSA_SL_FIO_INDEX rename to OCSA_SL_FIO_IDX
/
alter index CSA_SL_LOGIN_DATE_INDEX rename to OCSA_SL_LOGIN_DATE_IDX
/
alter index CSA_SL_IP_DATE_INDEX rename to OCSA_SL_IP_DATE_IDX
/
alter index CSA_SL_DI_DATE_INDEX rename to OCSA_SL_DI_DATE_IDX
/
alter table CSA_SYSTEMLOG rename to OCSA_SYSTEMLOG
/
create table CSA_SYSTEMLOG
  (
    ID              number          not null,
    MSG_LEVEL       char(1)         not null,
    START_DATE      timestamp (6)   not null,
    LOGIN_ID        number          not null,
    APPLICATION     varchar2(20)    not null,
    MESSAGE         clob,
    SESSION_ID      varchar2(64),
    IP_ADDRESS      varchar2(15),
    MESSAGE_SOURCE  varchar2(16)    not null,
    DEPARTMENT_ID   number,
    FIRST_NAME      varchar2(42),
    SUR_NAME        varchar2(42),
    PATR_NAME       varchar2(42),
    DEPARTMENT_NAME varchar2(256),
    DOC_NUMBER      varchar2(512),
    DOC_SERIES      varchar2(512),
    BIRTHDAY        timestamp (6),
    THREAD_INFO     number,
    LOGIN           varchar2(32),
    DEPARTMENT_CODE varchar2(4),
    LOG_UID         varchar2(32)
  )
  tablespace ERIBLOG 
  partition by range ( START_DATE ) interval ( numtoyminterval(1,'MONTH') )
  (
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY')) tablespace ERIBLOG
  )
/

create index CSA_SL_PK on CSA_SYSTEMLOG  (
    ID
  )
  reverse 
  tablespace ERIBLOGINDEXES 
/
create index CSA_SL_APP_DATE_INDEX on CSA_SYSTEMLOG  (
    APPLICATION,
    START_DATE desc
  )
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_SL_DI_DATE_INDEX on CSA_SYSTEMLOG (
  replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_SL_FIO_INDEX on CSA_SYSTEMLOG  (
    upper(replace(replace(SUR_NAME||FIRST_NAME||PATR_NAME,' ',''),'-','')),
    START_DATE desc
  )
  tablespace ERIBLOGINDEXES local
/
create index CSA_SL_IP_DATE_INDEX on CSA_SYSTEMLOG  (
    IP_ADDRESS,
    START_DATE desc
  )
  tablespace ERIBLOGINDEXES local
/
create index CSA_SL_LOGIN_DATE_INDEX on CSA_SYSTEMLOG  (
    LOGIN,
    START_DATE desc
  )
  compress 1 
  tablespace ERIBLOGINDEXES local
/
create index CSA_SL_LOG_UID_INDEX on CSA_SYSTEMLOG ( 
  LOG_UID ASC 
) 
  tablespace ERIBLOGINDEXES 
/


-------------------------------------------------------------------------------------------


-- Номер ревизии: 55121
-- Комментарий: ENH061673 Хранить номер блока в таблицах логирования
alter table CODLOG add (NODE_ID integer)
/
alter table SYSTEMLOG add (NODE_ID integer)
/
alter table USERLOG add (NODE_ID integer)
/



