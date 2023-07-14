--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSAADMIN_IKFL
/

-- Номер ревизии: 67711
-- Комментарий: Редактирование локалезависимых сущностей(Баннеры)
create table CSAADMIN_IKFL.ADVERTISING_BUTTONS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(10)         not null,
   TITLE                VARCHAR2(100)        not null,
   constraint PK_ADVERTISING_BUTTONS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_ADVERTISING_BUTTONS_RES on CSAADMIN_IKFL.ADVERTISING_BUTTONS_RES(UUID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/

create table CSAADMIN_IKFL.ADVERTISINGS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(10)         not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 VARCHAR2(400)        not null,
   constraint PK_ADVERTISINGS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_ADVERTISINGS_RES on CSAADMIN_IKFL.ADVERTISINGS_RES(UUID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/

-- Номер ревизии: 67770
-- Комментарий: Редактирование локалезависимых сущностей(Новости)
create table CSAADMIN_IKFL.NEWS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   TITLE                VARCHAR2(100)        not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   TEXT                 CLOB                 not null,
   constraint PK_NEWS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_NEWS_RES on CSAADMIN_IKFL.NEWS_RES(UUID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/

-- Номер ревизии: 67915
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
create table CSAADMIN_IKFL.REGION_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   constraint PK_REGION_RES primary key (UUID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_REGION_RES on CSAADMIN_IKFL.REGION_RES(UUID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/

-- Номер ревизии: 67998
-- Комментарий: Редактирование локалезависимых сущностей(Банки)
create table CSAADMIN_IKFL.RUSBANKS_RES 
(
   ID                   VARCHAR2(64)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(256)        not null,
   PLACE                VARCHAR2(50),
   SHORT_NAME           VARCHAR2(256),
   ADDRESS              VARCHAR2(256),
   constraint PK_RUSBANKS_RES primary key (ID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_RUSBANKS_RES on CSAADMIN_IKFL.RUSBANKS_RES(ID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/

-- Номер ревизии: 68032
-- Комментарий: Редактирование локалезависимых сущностей(Редактирование поставщика услуг)
create table CSAADMIN_IKFL.SERVICE_PROVIDERS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(160)        not null,
   LEGAL_NAME           VARCHAR2(250)        not null,
   ALLIAS               VARCHAR2(250)        not null,
   BANK_NAME            VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512)        not null,
   TIP_OF_PROVIDER      VARCHAR2(255)        not null,
   COMMISSION_MESSAGE   VARCHAR2(250)        not null,
   NAME_ON_BILL         VARCHAR2(250)        not null,
   constraint PK_SERVICE_PROVIDERS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_SERVICE_PROVIDERS_RES on CSAADMIN_IKFL.SERVICE_PROVIDERS_RES(UUID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/

--Номер ревизии: 68331
--Комментарий: Синхронизация УАК
create table CSAADMIN_IKFL.INCOGNITO_PHONES_JURNAL 
(
   PHONE                VARCHAR2(11)         not null,
   LAST_UPDATE_TIME     TIMESTAMP,
   ACTIVE               CHAR(1)              not null,
   NODE_ID              INTEGER              not null
)
partition by range (LAST_UPDATE_TIME) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('10-10-2014','DD-MM-YYYY')) tablespace CSA_ADM_DATA
)tablespace CSA_ADM_DATA
/

create index CSAADMIN_IKFL.IDX_INCOG_LAST_DATE on CSAADMIN_IKFL.INCOGNITO_PHONES_JURNAL (
   LAST_UPDATE_TIME ASC,
   NODE_ID ASC
) localtablespace CSA_ADM_IDX
/

--Номер ревизии: 68431
--Комментарий: Синхронизация настроек в разных блоках
create table CSAADMIN_IKFL.PROPERTY_SYNC_INFO 
(
   ID                   INTEGER              not null,
   NODE_ID              INTEGER              not null,
   OPERATION_DATE       DATE                 not null,
   STATE                VARCHAR2(5)          not null,
   GUID                 VARCHAR2(32)         not null
)
partition by range (OPERATION_DATE) interval (NUMTOYMINTERVAL(1,'YEAR'))
(
	partition P_START values less than (to_date('10-10-2014','DD-MM-YYYY')) tablespace CSA_ADM_DATA
)tablespace CSA_ADM_DATA
/

create sequence CSAADMIN_IKFL.S_PROPERTY_SYNC_INFO
/

create index CSAADMIN_IKFL.I_PROP_SYNC_INFO_GUID on CSAADMIN_IKFL.PROPERTY_SYNC_INFO (
   GUID ASC
) localtablespace CSA_ADM_IDX
/

--Номер ревизии: 68732
--Комментарий: Хранение аватаров для АК
create table CSAADMIN_IKFL.AVATAR_CHANGE_JURNAL 
(
   PHONE                VARCHAR2(11)         not null,
   LAST_UPDATE_TIME     TIMESTAMP            not null,
   NODE_ID              INTEGER              not null,
   AVATAR_PATH          VARCHAR2(100)
)
partition by range (LAST_UPDATE_TIME) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('10-10-2014','DD-MM-YYYY'))tablespace CSA_ADM_DATA
)tablespace CSA_ADM_DATA
/

create index CSAADMIN_IKFL.IDX_AVTR_LAST_DATE on CSAADMIN_IKFL.AVATAR_CHANGE_JURNAL (
   LAST_UPDATE_TIME ASC,
   NODE_ID ASC
) localtablespace CSA_ADM_IDX
/

--Номер ревизии: 68760
--Комментарий: Многоязычность. Редактирование локалезависмых данных услуги.
create table CSAADMIN_IKFL.PAYMENT_SERVICES_RES 
(
   UUID                 VARCHAR2(50)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512),
   constraint PK_PAYMENT_SERVICES_RES primary key (UUID, LOCALE_ID) using index (
	create unique index CSAADMIN_IKFL.PK_PAYMENT_SERVICES_RES on CSAADMIN_IKFL.PAYMENT_SERVICES_RES(UUID, LOCALE_ID)tablespace CSA_ADM_IDX
   )
)tablespace CSA_ADM_DATA
/