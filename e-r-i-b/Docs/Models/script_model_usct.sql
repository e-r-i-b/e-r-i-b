-- Номер ревизии: 62244
-- Номер версии: 1.18
-- Комментарий: BUG073205: Шаблоны отдельное приложение. Синхронизация. 
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
/* Index: PROPERTIES_UNIQ                                       */
/*==============================================================*/
create unique index PROPERTIES_UNIQ on PROPERTIES (
   CATEGORY ASC,
   PROPERTY_KEY ASC
)
go

alter table USER_PROFILES drop (STATE)
go

/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS                                    */
/*==============================================================*/

-- Номер ревизии: 61600
-- Номер версии: 1.18
-- Комментарий: создание основных таблиц (без секционирования)

/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS                                    */
/*==============================================================*/
create table PAYMENTS_DOCUMENTS 
(
   ID                   INTEGER              not null,
   KIND                 CHAR(1)              not null,
   CHANGED              TIMESTAMP            not null,
   EXTERNAL_ID          VARCHAR(80),
   DOCUMENT_NUMBER      INTEGER,
   CLIENT_CREATION_DATE TIMESTAMP            not null,
   CLIENT_CREATION_CHANNEL CHAR(1)              not null,
   CLIENT_OPERATION_DATE TIMESTAMP,
   CLIENT_OPERATION_CHANNEL CHAR(1),
   EMPLOYEE_OPERATION_DATE TIMESTAMP,
   EMPLOYEE_OPERATION_CHANNEL CHAR(1),
   USER_PROFILE_ID      INTEGER              not null,
   CREATED_EMPLOYEE_GUID VARCHAR(24),
   CREATED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   CONFIRMED_EMPLOYEE_GUID VARCHAR(24),
   CONFIRMED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   OPERATION_UID        VARCHAR2(32),
   STATE_CODE           VARCHAR2(25)         not null,
   STATE_DESCRIPTION    VARCHAR2(265),
   CHARGEOFF_RESOURCE   VARCHAR(30),
   DESTINATION_RESOURCE VARCHAR(30),
   GROUND               VARCHAR2(1024),
   CHARGEOFF_AMOUNT     NUMBER(19,4),
   CHARGEOFF_CURRENCY   CHAR(3),
   DESTINATION_AMOUNT   NUMBER(19,4),
   DESTINATION_CURRENCY CHAR(3),
   SUMM_TYPE            VARCHAR2(50),
   RECEIVER_NAME        VARCHAR2(256),
   INTERNAL_RECEIVER_ID INTEGER,
   RECEIVER_POINT_CODE  VARCHAR2(128),
   EXTENDED_FIELDS      CLOB,
   REGION               VARCHAR2(4),
   BRANCH               VARCHAR2(4),
   OFFICE               VARCHAR2(6),
   TEMPLATE_NAME        VARCHAR2(128)        not null,
   TEMPLATE_USE_IN_MAPI CHAR(1)              not null,
   TEMPLATE_USE_IN_ATM  CHAR(1)              not null,
   TEMPLATE_USE_IN_ERMB CHAR(1)              not null,
   TEMPLATE_USE_IN_ERIB CHAR(1)              not null,
   TEMPLATE_IS_VISIBLE  CHAR(1)              not null,
   TEMPLATE_ORDER_IND   INTEGER              not null,
   TEMPLATE_STATE_CODE  VARCHAR2(50)         not null,
   TEMPLATE_STATE_DESCRIPTION VARCHAR2(128),
   constraint PK_PAYMENTS_DOCUMENTS primary key (ID)
)

go

create sequence S_PAYMENTS_DOCUMENTS
go

/*==============================================================*/
/* Index: IND_U_TEMPLATE_NAME                                   */
/*==============================================================*/
create unique index IND_U_TEMPLATE_NAME on PAYMENTS_DOCUMENTS (
   USER_PROFILE_ID ASC,
   TEMPLATE_NAME ASC
)
go

/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS_EXT                                */
/*==============================================================*/
create table PAYMENTS_DOCUMENTS_EXT 
(
   ID                   INTEGER              not null,
   KIND                 VARCHAR(50)          not null,
   NAME                 VARCHAR2(64)         not null,
   VALUE                VARCHAR2(4000),
   PAYMENT_ID           INTEGER              not null,
   CHANGED              CHAR(1)              default '0' not null,
   constraint PK_PAYMENTS_DOCUMENTS_EXT primary key (ID)
)

go

create sequence S_PAYMENTS_DOCUMENTS_EXT
go

/*==============================================================*/
/* Table: USER_PROFILES                                         */
/*==============================================================*/
create table USER_PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   STATE                VARCHAR2(10)         not null,
   constraint PK_USER_PROFILES primary key (ID)
)

go

create sequence S_USER_PROFILES
go

/*==============================================================*/
/* Index: IDX_USER_PROFILE_UNIQ                                 */
/*==============================================================*/
create unique index IDX_USER_PROFILE_UNIQ on USER_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDAY ASC,
   TB ASC,
   REPLACE(PASSPORT, ' ', '') ASC
)
go

alter table PAYMENTS_DOCUMENTS
   add constraint FK_TEMPLATES_TO_OWNERS foreign key (USER_PROFILE_ID)
      references USER_PROFILES (ID)
go


create index "DXFK_TEMPLATE_EXT_TO_TEMPLATE" on PAYMENTS_DOCUMENTS_EXT
(
   PAYMENT_ID
)
go

alter table PAYMENTS_DOCUMENTS_EXT
   add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key (PAYMENT_ID)
      references PAYMENTS_DOCUMENTS (ID)
      on delete cascade
go

-- Номер ревизии: 63251 
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG073202: Шаблоны отдельное приложение. Добавить хранение межблочного идентификатора поставщика услуг.
alter table PAYMENTS_DOCUMENTS add  (MULTI_BLOCK_RECEIVER_CODE VARCHAR2(32))
go

alter table PAYMENTS_DOCUMENTS drop (INTERNAL_RECEIVER_ID)
go

-- Номер ревизии 68549
-- Номер версии 1.18
-- Комментарий: Корзина. Напоминания

alter table PAYMENTS_DOCUMENTS add (REMINDER_TYPE varchar2(16))
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_ONCE_DATE timestamp)
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_DAY_OF_MONTH integer)
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_MONTH_OF_QUARTER integer)
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_CREATED_DATE timestamp)
go

-- Номер ревизии:  
-- Номер версии: v.1.18
-- Комментарий: BUG089202 Ошибка, при переходе в настройки. (изменение статусов конвертирумых шаблонов)
update PAYMENTS_DOCUMENTS set STATE_CODE = 'DRAFTTEMPLATE' where STATE_CODE = 'INIT_TEMPLATE'
go