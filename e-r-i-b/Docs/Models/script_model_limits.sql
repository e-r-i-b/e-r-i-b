-- Номер ревизии: 57367
-- Номер версии: 1.18
-- Комментарий: LimitsApp. Создание БД.

/*==============================================================*/
/* Table: PROFILES                                              */
/*==============================================================*/
create table PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100)        not null,
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_PROFILES primary key (ID)
)

go

create sequence S_PROFILES
go

/*==============================================================*/
/* Table: PROPERTIES                                            */
/*==============================================================*/
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)

go

create sequence S_PROPERTIES
go

/*==============================================================*/
/* Table: TRANSACTIONS_JOURNAL                                  */
/*==============================================================*/
create table TRANSACTIONS_JOURNAL 
(
   EXTERNAL_ID          VARCHAR2(100)        not null,
   DOCUMENT_EXTERNAL_ID VARCHAR2(100)        not null,
   PROFILE_ID           INTEGER              not null,
   AMOUNT               NUMBER(19,4),
   CURRENCY             CHAR(3),
   OPERATION_DATE       TIMESTAMP            not null,
   OPERATION_TYPE       VARCHAR2(10)         not null,
   CHANNEL_TYPE         VARCHAR2(10)         not null,
   LIMITS_INFO          VARCHAR2(4000)       not null
)
partition by range
 (OPERATION_DATE)
    interval (NUMTODSINTERVAL(1,'DAY'))
 (partition
         P_FIRST
        values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_TRANSACTIONS_JOURNAL
go

/*==============================================================*/
/* Index: DOC_DATE_I                                            */
/*==============================================================*/
create index DOC_DATE_I on TRANSACTIONS_JOURNAL (
   DOCUMENT_EXTERNAL_ID ASC,
   OPERATION_DATE ASC
)
local
go

/*==============================================================*/
/* Index: PROFILE_DATE_I                                        */
/*==============================================================*/
create index PROFILE_DATE_I on TRANSACTIONS_JOURNAL (
   PROFILE_ID ASC,
   OPERATION_DATE ASC
)
local
go


create index "DXFK_PROFILE_ID" on TRANSACTIONS_JOURNAL
(
   PROFILE_ID
)
go

alter table TRANSACTIONS_JOURNAL
   add constraint FK_TRANSACT_FK_PROFIL_PROFILES foreign key (PROFILE_ID)
      references PROFILES (ID)
go

-- Номер ревизии: 57804
-- Номер версии: 1.18
-- Комментарий: BUG067964: Не записывается инфомация в БД Лимитов 

alter table TRANSACTIONS_JOURNAL modify (CHANNEL_TYPE varchar2(25))
go


-- Номер ревизии: 61772
-- Номер версии: 1.18
-- Комментарий: Создать сущности для хранения данных профиля клиента. 


create table PROFILE_INFORMATION(
    PROFILE_ID integer not null,
    INFORMATION_TYPE varchar2(128) not null,
    DATA clob,
    constraint PK_PROFILE_INFORMATION primary key(PROFILE_ID, INFORMATION_TYPE)   
)
go

alter table PROFILE_INFORMATION
   add constraint FK_PROFILE_INF_TO_PROFILES foreign key (PROFILE_ID)
      references PROFILES (ID)
      on delete cascade
go

-- Номер ревизии: 62233
-- Номер версии: 1.18
-- Комментарий: Индекс по ФИО ДУЛ ДР ТБ

create unique index INDEX_PROFILES_UID on PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC
)
go

