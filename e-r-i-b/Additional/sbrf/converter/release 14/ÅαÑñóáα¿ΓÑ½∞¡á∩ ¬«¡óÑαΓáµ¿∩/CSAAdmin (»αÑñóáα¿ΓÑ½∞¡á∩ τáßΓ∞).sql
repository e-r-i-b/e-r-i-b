-- Номер ревизии: 60926
-- Номер версии: 1.18
-- Комментарий: Связь справочника видов (групп) услуг и справочника категорий в АЛФ(многоблочный режим)
create table CARD_OPERATION_CATEGORIES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   INCOME               CHAR(1)              not null,
   CASH                 CHAR(1)              not null,
   CASHLESS             CHAR(1)              not null,
   LOGIN_ID             INTEGER,
   EXTERNALID           VARCHAR2(100),
   ALLOW_INCOMPATIBLE_OPERATIONS CHAR(1)              default '1' not null,
   IS_DEFAULT           CHAR(1)              default '0',
   VISIBLE              CHAR(1)              default '1' not null,
   FOR_INTERNAL_OPERATIONS CHAR(1)              default '0' not null,
   IS_TRANSFER          CHAR(1)              default '0' not null,
   ID_IN_MAPI           VARCHAR2(30),
   COLOR                VARCHAR2(6),
   UUID                 VARCHAR2(32)         null,
   constraint PK_CARD_OPERATION_CATEGORIES primary key (ID)
)
/
create sequence S_CARD_OPERATION_CATEGORIES
/

create unique index IDX_NAME_LOGIN_INCOME on CARD_OPERATION_CATEGORIES (
   (nvl(LOGIN_ID, -1)) ASC,
   INCOME ASC,
   NAME ASC
)
/

create table MERCHANT_CATEGORY_CODES 
(
   CODE                 INTEGER              not null,
   INCOME_OPERATION_CATEGORY_ID VARCHAR2(100),
   OUTCOME_OPERATION_CATEGORY_ID VARCHAR2(100),
   constraint PK_MERCHANT_CATEGORY_CODES primary key (CODE)
)
/
create sequence S_MERCHANT_CATEGORY_CODES
/

create table CARD_OPERATION_TYPES 
(
   CODE                 INTEGER              not null,
   CASH                 CHAR(1)              not null,
   constraint PK_CARD_OPERATION_TYPES primary key (CODE)
)
/
create sequence S_CARD_OPERATION_TYPES
/

-- Номер ревизии: 61979
-- Номер версии: 1.18
-- Комментарий: ФОС. Синхронизация справочников площадок и тематик сообщений
create table CONTACT_CENTER_AREAS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   AREA_NAME            VARCHAR2(50)         not null,
   constraint PK_CONTACT_CENTER_AREAS primary key (ID)
)
/
create sequence S_CONTACT_CENTER_AREAS start with 100
/

create unique index I_CONTACT_CENTER_AREAS_UUID on CONTACT_CENTER_AREAS (
   UUID ASC
)
/
create unique index I_CONTACT_CENTER_AREAS_NAME on CONTACT_CENTER_AREAS (
   AREA_NAME ASC
)
/

create table C_CENTER_AREAS_DEPARTMENTS 
(
   C_C_AREA_ID          INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_C_CENTER_AREAS_DEPARTMENTS primary key (C_C_AREA_ID, TB)
)
/
create sequence S_C_CENTER_AREAS_DEPARTMENTS
/

create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   TB ASC
)
/

alter table C_CENTER_AREAS_DEPARTMENTS
   add constraint FK_C_C_AREA_DEP_TO_C_C_AREA foreign key (C_C_AREA_ID)
      references CONTACT_CENTER_AREAS (ID)
      on delete cascade
/

create table MAIL_SUBJECTS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   DESCRIPTION          VARCHAR2(50)         not null,
   constraint PK_MAIL_SUBJECTS primary key (ID)
)
/
create sequence S_MAIL_SUBJECTS
/

create unique index I_MAIL_SUBJECTS_UUID on MAIL_SUBJECTS (
   UUID ASC
)
/