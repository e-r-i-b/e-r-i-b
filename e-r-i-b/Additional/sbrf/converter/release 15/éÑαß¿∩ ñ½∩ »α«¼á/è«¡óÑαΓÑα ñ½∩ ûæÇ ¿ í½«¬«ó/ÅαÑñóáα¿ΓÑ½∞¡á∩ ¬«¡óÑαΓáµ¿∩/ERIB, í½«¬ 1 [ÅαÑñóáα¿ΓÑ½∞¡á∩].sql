--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = SRB_IKFL
/

-- Номер ревизии: 63018
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок: изменение поиска сообщения для ошибки
create table SRB_IKFL.EXCEPTION_MAPPINGS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS primary key (HASH, GROUP_ID) using index (
	create unique index SRB_IKFL.PK_EXCEPTION_MAPPINGS on SRB_IKFL.EXCEPTION_MAPPINGS(HASH, GROUP_ID) tablespace INDX
   )
) tablespace USERS
/

create table SRB_IKFL.EXCEPTION_MAPPING_RESTRICTIONS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   TB                   VARCHAR2(4)          not null
) tablespace USERS
/

create unique index SRB_IKFL.EXC_MAP_RESTRICTION_UK on SRB_IKFL.EXCEPTION_MAPPING_RESTRICTIONS (
   HASH ASC,
   APPLICATION ASC,
   TB ASC
) tablespace INDX
/

create index SRB_IKFL."DXEXCEPTION_MAPPING_RESTRICTIO" on SRB_IKFL.EXCEPTION_MAPPING_RESTRICTIONS(
   HASH, GROUP_ID
) tablespace INDX
/

alter table SRB_IKFL.EXCEPTION_MAPPING_RESTRICTIONS
   add constraint FK_EXCEPTIO_EXCEPTION_EXCEPTIO foreign key (HASH, GROUP_ID)
      references SRB_IKFL.EXCEPTION_MAPPINGS (HASH, GROUP_ID)
      on delete cascade
/

-- Номер ревизии: 65936
-- Комментарий: Иерархическое администрирование. Сущность группы сервисов
create table SRB_IKFL.SERVICES_GROUPS 
(
   ID                   INTEGER              not null,
   KEY                  VARCHAR2(60)         not null,
   NAME                 VARCHAR2(100)        not null,
   PARENT_ID            INTEGER,
   CATEGORY             VARCHAR2(8)          not null,
   IS_ACTION            CHAR(1)              not null,
   GROUP_ORDER          INTEGER              not null,
   constraint PK_SERVICES_GROUPS primary key (ID) using index (
	create unique index SRB_IKFL.PK_SERVICES_GROUPS on SRB_IKFL.SERVICES_GROUPS(ID) tablespace INDX   
   )
) tablespace USERS
/

create sequence SRB_IKFL.S_SERVICES_GROUPS
/

create unique index SRB_IKFL.I_SERVICES_GROUPS_KEY on SRB_IKFL.SERVICES_GROUPS (
   KEY ASC
) tablespace INDX
/

alter table SRB_IKFL.SERVICES_GROUPS
   add constraint FK_SERVICES_GROUPS_TO_PARENT foreign key (PARENT_ID)
      references SRB_IKFL.SERVICES_GROUPS (ID)
      on delete cascade
/

create table SRB_IKFL.SERVICES_GROUPS_SERVICES 
(
   GROUP_ID             INTEGER              not null,
   SERVICE_KEY          VARCHAR2(60)         not null,
   SERVICE_MODE         VARCHAR2(4)          not null
) tablespace USERS
/

create index SRB_IKFL."DXFK_SERVICES_TO_GROUP" on SRB_IKFL.SERVICES_GROUPS_SERVICES
(
   GROUP_ID
) tablespace INDX
/

alter table SRB_IKFL.SERVICES_GROUPS_SERVICES
   add constraint FK_SERVICES_TO_GROUP foreign key (GROUP_ID)
      references SRB_IKFL.SERVICES_GROUPS (ID)
      on delete cascade
/


create index SRB_IKFL."DXFK_SERVICES_TO_SERVICE" on SRB_IKFL.SERVICES_GROUPS_SERVICES
(
   SERVICE_KEY
) tablespace INDX
/


-- Номер ревизии: 66871
-- Комментарий: Создать таблицы для хранения языка в системе.
create table SRB_IKFL.ERIB_LOCALES 
(
   ID                   varchar2(30)         not null,
   NAME                 VARCHAR2(100)        not null,
   IMAGE_ID             INTEGER              not null,
   ERIB_AVAILABLE       CHAR(1)              not null,
   MAPI_AVAILABLE       CHAR(1)              not null,
   ATMAPI_AVAILABLE     CHAR(1)              not null,
   WEBAPI_AVAILABLE     CHAR(1)              not null,
   ERMB_AVAILABLE       CHAR(1)              not null,
   STATE                VARCHAR2(10)         not null,
   constraint PK_ERIB_LOCALES primary key (ID) using index (
	create unique index SRB_IKFL.PK_ERIB_LOCALES on SRB_IKFL.ERIB_LOCALES(ID) tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 66883
-- Комментарий: Информирование клиентов о входе с нестандартного ip-адреса. Сущность оповещения
create table SRB_IKFL.UNUSUAL_IP_NOTIFICATIONS 
(
   ID                   INTEGER              not null,
   DATE_CREATED         TIMESTAMP            not null,
   LOGIN_ID             INTEGER              not null,
   ATTEMPTS_COUNT       INTEGER              not null,
   MESSAGE              varchar2(80)         not null,
   constraint PK_UNUSUAL_IP_NOTIFICATIONS primary key (ID) using index (
	create unique index SRB_IKFL.PK_UNUSUAL_IP_NOTIFICATIONS on SRB_IKFL.UNUSUAL_IP_NOTIFICATIONS(ID) tablespace INDX
   )
) tablespace USERS
/

create sequence SRB_IKFL.S_UNUSUAL_IP_NOTIFICATIONS
/

create index SRB_IKFL.I_UNUSUAL_IP_NOTIFICATIONS on SRB_IKFL.UNUSUAL_IP_NOTIFICATIONS (
   DATE_CREATED ASC
) tablespace INDX
/

-- Номер ревизии: 66935
-- Комментарий: Реализовать таблицы для хранения текстовок в БД.
create table SRB_IKFL.ERIB_STATIC_MESSAGE 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   BUNDLE               VARCHAR2(40)         not null,
   KEY                  VARCHAR2(200)        not null,
   MESSAGE              VARCHAR2(2048)       not null,
   constraint PK_ERIB_STATIC_MESSAGE primary key (ID) using index (
	create unique index SRB_IKFL.PK_ERIB_STATIC_MESSAGE on SRB_IKFL.ERIB_STATIC_MESSAGE(ID) tablespace INDX
   )
) tablespace USERS
/

create sequence SRB_IKFL.S_ERIB_STATIC_MESSAGE
/

create index SRB_IKFL.I_ERIB_STATIC_MESSAGE_LBK on SRB_IKFL.ERIB_STATIC_MESSAGE (
   LOCALE_ID ASC,
   BUNDLE ASC,
   KEY ASC
) tablespace INDX
/

create index SRB_IKFL.ATM_ERIB_STATIC_MESSAGE on SRB_IKFL.ERIB_STATIC_MESSAGE (
   DECODE(substr( KEY , -4), '.atm', LOCALE_ID||KEY, null)  ASC
) tablespace INDX
/

-- Номер ревизии: 67209
-- Комментарий: Сохранение служебной информации в профиле клиента.
create table SRB_IKFL.USER_SERVICE_INFO 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DATA                 VARCHAR2(256),
   constraint PK_USER_SERVICE_INFO primary key (ID) using index (
	create unique index SRB_IKFL.PK_USER_SERVICE_INFO on SRB_IKFL.USER_SERVICE_INFO(ID) tablespace INDX
   )   
) tablespace USERS
/

create sequence SRB_IKFL.S_USER_SERVICE_INFO
/

create unique index SRB_IKFL.IDX_USER_SERVICE_INFO on SRB_IKFL.USER_SERVICE_INFO (
   LOGIN_ID ASC
) tablespace INDX
/

-- Номер ревизии: 67466
-- Комментарий: Тех-перерывы МБК - Реализация кэша хранимых процедур МБК
create table SRB_IKFL.MBK_CACHE_CARDS_BY_PHONE 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   RESULT_SET           CLOB,
   constraint PK_MBK_CACHE_CARDS_BY_PHONE primary key (PHONE_NUMBER) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_CARDS_BY_PHONE on SRB_IKFL.MBK_CACHE_CARDS_BY_PHONE(PHONE_NUMBER) tablespace INDX
   )   
) tablespace USERS
/

create table SRB_IKFL.MBK_CACHE_CLIENT_BY_CARD 
(
   CARD_NUMBER          VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   AUTH_IDT             VARCHAR2(10)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_CARD primary key (CARD_NUMBER) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_CLIENT_BY_CARD on SRB_IKFL.MBK_CACHE_CLIENT_BY_CARD(CARD_NUMBER) tablespace INDX
   ) 
) tablespace USERS
/

create table SRB_IKFL.MBK_CACHE_CLIENT_BY_LOGIN 
(
   AUTH_IDT             VARCHAR2(10)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   CARD_NUMBER          VARCHAR2(20)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_LOGIN primary key (AUTH_IDT) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_CLIENT_BY_LOGIN on SRB_IKFL.MBK_CACHE_CLIENT_BY_LOGIN(AUTH_IDT) tablespace INDX
   ) 
) tablespace USERS
/

create table SRB_IKFL.MBK_CACHE_IMSI_CHECK_RESULT 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   MESSAGE_ID           INTEGER              not null,
   VALIDATION_RESULT    INTEGER,
   constraint PK_MBK_CACHE_IMSI_CHECK_RESULT primary key (PHONE_NUMBER) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_IMSI_CHECK_RESULT on SRB_IKFL.MBK_CACHE_IMSI_CHECK_RESULT(PHONE_NUMBER) tablespace INDX
   ) 
) tablespace USERS
/

create table SRB_IKFL.MBK_CACHE_REGISTRATIONS 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS primary key (STR_CARD_NUMBER) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_REGISTRATIONS on SRB_IKFL.MBK_CACHE_REGISTRATIONS(STR_CARD_NUMBER) tablespace INDX
   ) 
) tablespace USERS
/

create table SRB_IKFL.MBK_CACHE_REGISTRATIONS2 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS2 primary key (STR_CARD_NUMBER) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_REGISTRATIONS2 on SRB_IKFL.MBK_CACHE_REGISTRATIONS2(STR_CARD_NUMBER) tablespace INDX
   ) 
) tablespace USERS
/

create table SRB_IKFL.MBK_CACHE_REGISTRATIONS3 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS3 primary key (STR_CARD_NUMBER) using index (
	create unique index SRB_IKFL.PK_MBK_CACHE_REGISTRATIONS3 on SRB_IKFL.MBK_CACHE_REGISTRATIONS3(STR_CARD_NUMBER) tablespace INDX
   ) 
) tablespace USERS
/

-- Номер ревизии: 67513
-- Комментарий: Краудгифтинг. часть 1
create sequence SRB_IKFL.S_FUND_INITIATOR_REQUESTS cache 10000
/

create table SRB_IKFL.FUND_INITIATOR_REQUESTS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(100)        not null,
   LOGIN_ID             INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   REQUIRED_SUM         NUMBER(19,4),
   RECCOMEND_SUM        NUMBER(19,4),
   MESSAGE              VARCHAR2(256)        not null,
   TO_RESOURCE          VARCHAR2(30)         not null,
   EXPECTED_CLOSED_DATE TIMESTAMP,
   CLOSED_DATE          TIMESTAMP,
   CLOSED_REASON        VARCHAR2(16),
   CREATED_DATE         TIMESTAMP            not null,
   SENDERS_COUNT        INTEGER              not null,
   constraint I_PK_FUND_REQUESTS primary key (ID) using index (
	create unique index SRB_IKFL.I_PK_FUND_REQUESTS on SRB_IKFL.FUND_INITIATOR_REQUESTS(ID) global tablespace INDX
   ) 
)
partition by range (CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY')) tablespace USERS
) tablespace USERS
/

create index SRB_IKFL.I_FUND_I_REQUESTS_LOGIN on SRB_IKFL.FUND_INITIATOR_REQUESTS (
   LOGIN_ID ASC,
   CREATED_DATE ASC
) tablespace INDX local
/

create index SRB_IKFL.I_FUND_I_REQUESTS_STATE on SRB_IKFL.FUND_INITIATOR_REQUESTS (
   STATE ASC,
   CREATED_DATE ASC
) tablespace INDX local
/

create table SRB_IKFL.FUND_INITIATOR_RESPONSES 
(
   EXTERNAL_ID          VARCHAR2(100)        not null,
   REQUEST_ID           INTEGER              not null,
   PHONE                VARCHAR2(15)         not null,
   STATE                VARCHAR2(10)         not null,
   SUM                  NUMBER(19,4),
   MESSAGE              VARCHAR2(256),
   CREATED_DATE         TIMESTAMP            not null,
   ACCUMULATED          CHAR(1)              not null
)
partition by range(CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY')) tablespace USERS
) tablespace USERS
/

create sequence SRB_IKFL.S_FUND_INITIATOR_RESPONSES cache 10000
/

create unique index SRB_IKFL.I_FUND_I_RESPONSES_EXT_ID on SRB_IKFL.FUND_INITIATOR_RESPONSES (
   EXTERNAL_ID ASC
) tablespace INDX
/

create index SRB_IKFL.I_FUND_I_RESPONSES_REQUEST on SRB_IKFL.FUND_INITIATOR_RESPONSES (
   REQUEST_ID ASC,
   STATE ASC
) tablespace INDX
/

create index SRB_IKFL.I_FUND_I_ACCUMULATED on SRB_IKFL.FUND_INITIATOR_RESPONSES (
   decode(ACCUMULATED, '1', REQUEST_ID, null) ASC
) tablespace INDX local
/

alter table SRB_IKFL.FUND_INITIATOR_RESPONSES
   add constraint FK_FUND_INI_FK_FUND_I_FUND_INI foreign key (REQUEST_ID)
      references SRB_IKFL.FUND_INITIATOR_REQUESTS (ID)
/

create sequence SRB_IKFL.S_FUND_SENDER_RESPONSES cache 10000
/

create table SRB_IKFL.FUND_SENDER_RESPONSES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(120)        not null,
   SUR_NAME             VARCHAR2(120)        not null,
   PATR_NAME            VARCHAR2(120),
   BIRTH_DATE           TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   PASSPORT             VARCHAR2(100)        not null,
   EXTERNAL_RESPONSE_ID VARCHAR2(100)        not null,
   EXTERNAL_REQUEST_ID  VARCHAR2(100)        not null,
   STATE                VARCHAR2(16)         not null,
   SUM                  NUMBER(19,4),
   MESSAGE              VARCHAR2(256)        not null,
   PAYMENT_ID           INTEGER,
   INITIATOR_FIRST_NAME VARCHAR2(120)        not null,
   INITIATOR_SUR_NAME   VARCHAR2(120)        not null,
   INITIATOR_PATR_NAME  VARCHAR2(120),
   INITIATOR_PHONES     VARCHAR2(1500)       not null,
   REQUEST_MESSAGE      VARCHAR2(256)        not null,
   REQUEST_STATE        VARCHAR2(8)          not null,
   REQUIRES_SUM         NUMBER(19,4),
   RECCOMEND_SUM        NUMBER(19,4),
   TO_RESOURCE          VARCHAR2(30)         not null,
   CLOSED_DATE          TIMESTAMP,
   EXPECTED_CLOSED_DATE TIMESTAMP,
   CREATED_DATE         TIMESTAMP            not null
)
partition by range(CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY'))
) tablespace USERS
/

create unique index SRB_IKFL.I_FUND_S_RESP_ID on SRB_IKFL.FUND_SENDER_RESPONSES (
   ID ASC
) tablespace INDX global
/

create index SRB_IKFL.I_FUND_S_RESP_UNIVERSAL on SRB_IKFL.FUND_SENDER_RESPONSES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'()+',' '))) ASC,
   BIRTH_DATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC,
   CREATED_DATE ASC
) tablespace INDX local
/

create index SRB_IKFL.I_FUND_S_REQ_EXT_ID on SRB_IKFL.FUND_SENDER_RESPONSES (
   EXTERNAL_REQUEST_ID ASC,
   CREATED_DATE ASC
) tablespace INDX local
/

create table SRB_IKFL.FUND_GROUPS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   NAME                 VARCHAR2(500)        not null,
   constraint PK_FUND_GROUPS primary key (ID) using index (
	create unique index SRB_IKFL.PK_FUND_GROUPS on SRB_IKFL.FUND_GROUPS(ID) tablespace INDX
   ) 
) tablespace USERS
/

create sequence SRB_IKFL.S_FUND_GROUPS
/

create index SRB_IKFL.I_FUND_GROUP_LOGIN on SRB_IKFL.FUND_GROUPS (
   LOGIN_ID ASC
) tablespace INDX
/

create table SRB_IKFL.FUND_GROUP_PHONES 
(
   GROUP_ID             INTEGER              not null,
   PHONE                VARCHAR2(15)         not null
) tablespace USERS
/

create unique index SRB_IKFL.I_FUND_GROUP_PHONE on SRB_IKFL.FUND_GROUP_PHONES (
   GROUP_ID ASC,
   PHONE ASC
) tablespace INDX
/

-- Номер ревизии: 67711
-- Комментарий: Редактирование локалезависимых сущностей(Баннеры)
-- Номер ревизии: 67760
-- Комментарий: Синхронизация локалезависимых сущностей(Баннеры, перевод на UUID)
create table SRB_IKFL.ADVERTISING_BUTTONS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(10)         not null,
   TITLE                VARCHAR2(100)        not null,
   constraint PK_ADVERTISING_BUTTONS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_ADVERTISING_BUTTONS_RES on SRB_IKFL.ADVERTISING_BUTTONS_RES(UUID, LOCALE_ID) tablespace INDX
   ) 
) tablespace USERS
/

create table SRB_IKFL.ADVERTISINGS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(10)         not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 VARCHAR2(400)        not null,
   constraint PK_ADVERTISINGS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_ADVERTISINGS_RES on SRB_IKFL.ADVERTISINGS_RES(UUID, LOCALE_ID) tablespace INDX
   ) 
) tablespace USERS
/

-- Номер ревизии: 67764
-- Комментарий: Профиль. Адресная книга (хранение адресной книги)
create table SRB_IKFL.ADDRESS_BOOKS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   PHONE                VARCHAR2(11)         not null,
   FULL_NAME            VARCHAR2(60)         not null,
   SBERBANK_CLIENT      CHAR(1)              not null,
   INCOGNITO            CHAR(1)              not null,
   FIO                  VARCHAR2(100),
   ALIAS                VARCHAR2(60),
   CUT_ALIAS            VARCHAR2(7),
   AVATAR               VARCHAR2(256),
   CARD_NUMBER          VARCHAR2(20),
   CATEGORY             VARCHAR2(10)         not null,
   TRUSTED              CHAR(1)              not null,
   FREQUENCY_P2P        INTEGER              not null,
   FREQUENCY_PAY        INTEGER              not null,
   ADDED_BY             VARCHAR2(10),
   STATUS               VARCHAR2(10)         not null,
   constraint PK_ADDRESS_BOOKS primary key (ID) using index (
	create unique index SRB_IKFL.PK_ADDRESS_BOOKS on SRB_IKFL.ADDRESS_BOOKS(ID) global tablespace INDX
   ) 
) partition by hash (LOGIN_ID) partitions 128 tablespace USERS
/

create sequence SRB_IKFL.S_ADDRESS_BOOK cache 10000
/

create index SRB_IKFL.IDX_AB_PHN on SRB_IKFL.ADDRESS_BOOKS (
   PHONE ASC
) global tablespace INDX
/

create index SRB_IKFL.IDX_AB_LGID on SRB_IKFL.ADDRESS_BOOKS (
   LOGIN_ID ASC,
   PHONE ASC
) local tablespace INDX
/

-- Номер ревизии: 67767
-- Комментарий: Кредитный профиль клиента
create table SRB_IKFL.USER_CREDIT_PROFILE 
(
   USER_ID              INTEGER              not null,
   CONNECTED            CHAR(1)              not null,
   LAST_CHECK_REQUEST   TIMESTAMP            not null,
   LAST_PAYMENT         TIMESTAMP,
   LAST_REPORT          TIMESTAMP,
   REPORT_XML           CLOB,
   LAST_GET_ERROR       TIMESTAMP,
   constraint PK_USER_CREDIT_PROFILE primary key (USER_ID) using index (
	create unique index SRB_IKFL.UNIQ_USER_CREDIT_PROFILE on SRB_IKFL.USER_CREDIT_PROFILE ( USER_ID ASC )	local tablespace INDX
   )
) partition by hash (USER_ID) partitions 64 tablespace USERS
/

create sequence SRB_IKFL.S_USER_CREDIT_PROFILE
/

create index SRB_IKFL.I_USER_CREDIT_PROFILE_RQ_TIME on SRB_IKFL.USER_CREDIT_PROFILE (
   CONNECTED ASC,
   LAST_CHECK_REQUEST ASC
) tablespace INDX
/

-- Номер ревизии: 67770
-- Комментарий: Редактирование локалезависимых сущностей(Новости)
create table SRB_IKFL.NEWS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   TITLE                VARCHAR2(100)        not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   TEXT                 CLOB                 not null,
   constraint PK_NEWS_RES primary key (UUID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_NEWS_RES on SRB_IKFL.NEWS_RES ( UUID, LOCALE_ID )	tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 67898
-- Комментарий: Редактирование локалезависимых сущностей(Справочник категорий операций)
create table SRB_IKFL.CARD_OPERATION_CATEGORIES_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(100)        not null,
   constraint PK_CARD_OPERATION_CATEGOR_RES primary key (ID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_CARD_OPERATION_CATEGOR_RES on SRB_IKFL.CARD_OPERATION_CATEGORIES_RES ( ID, LOCALE_ID )	tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 67915
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
create table SRB_IKFL.REGION_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   constraint PK_REGION_RES primary key (UUID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_REGION_RES on SRB_IKFL.REGION_RES ( UUID, LOCALE_ID )	tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 67926
-- Комментарий: Stand-In. форма состояния миграции (ч.3 сущности модели)
create table SRB_IKFL.MIGRATION_INFO 
(
   ID                   INTEGER              not null,
   TOTAL_COUNT          INTEGER,
   BATCH_SIZE           INTEGER,
   NEED_STOP            CHAR(1)              not null,
   constraint PK_MIGRATION_INFO primary key (ID) using index (
	create unique index SRB_IKFL.PK_MIGRATION_INFO on SRB_IKFL.MIGRATION_INFO ( ID ) tablespace INDX
   )
) tablespace USERS
/

create sequence SRB_IKFL.S_MIGRATION_INFO
/

create unique index SRB_IKFL.I_MIGRATION_INFO_BATCH_SIZE on SRB_IKFL.MIGRATION_INFO (
   decode(BATCH_SIZE, null, '1', null) ASC
) tablespace INDX
/

create table SRB_IKFL.MIGRATION_THREAD_INFO 
(
   ID                   INTEGER              not null,
   PARENT_ID            INTEGER,
   STATE                VARCHAR2(16)         not null,
   GOOD_COUNT           INTEGER              not null,
   BAD_COUNT            INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   END_DATE             TIMESTAMP,
   constraint PK_MIGRATION_THREAD_INFO primary key (ID) using index (
	create unique index SRB_IKFL.PK_MIGRATION_THREAD_INFO on SRB_IKFL.MIGRATION_THREAD_INFO ( ID ) tablespace INDX
   )
) tablespace USERS
/

create sequence SRB_IKFL.S_MIGRATION_THREAD_INFO
/

create index SRB_IKFL.I_MIGRATION_THREAD_INFO_STATE on SRB_IKFL.MIGRATION_THREAD_INFO (
   decode(STATE, 'WAIT', '1', null) ASC
) tablespace INDX
/

create index SRB_IKFL."DXFK_MIGRATION_TOTAL_TO_THREAD" on SRB_IKFL.MIGRATION_THREAD_INFO(
   PARENT_ID
) tablespace INDX
/

alter table SRB_IKFL.MIGRATION_THREAD_INFO
   add constraint FK_MIGRATION_TOTAL_TO_THREAD foreign key (PARENT_ID)
      references SRB_IKFL.MIGRATION_INFO (ID)
      on delete cascade
/

-- Номер ревизии: 67998
-- Комментарий: Редактирование локалезависимых сущностей(Банки)
create table SRB_IKFL.RUSBANKS_RES 
(
   ID                   VARCHAR2(64)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(256)        not null,
   PLACE                VARCHAR2(50),
   SHORT_NAME           VARCHAR2(256),
   ADDRESS              VARCHAR2(256),
   constraint PK_RUSBANKS_RES primary key (ID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_RUSBANKS_RES on SRB_IKFL.RUSBANKS_RES ( ID, LOCALE_ID ) tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 68017
-- Комментарий: Интеграция с БКИ. Справочники констант БКИ. 
create table SRB_IKFL.BKI_ACCOUNT_CLASS 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(60)         not null,
   constraint PK_BKI_ACCOUNT_CLASS primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_ACCOUNT_CLASS on SRB_IKFL.BKI_ACCOUNT_CLASS ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_ACCOUNT_CLASS
/

create table SRB_IKFL.BKI_ACCOUNT_SPECIAL_STATUS
(
    CODE varchar2(2) not null,
    NAME varchar2(60) not null,
    constraint PK_BKI_ACCOUNT_SPECIAL_STATUS primary key(CODE) using index (
	create unique index SRB_IKFL.PK_BKI_ACCOUNT_SPECIAL_STATUS on SRB_IKFL.BKI_ACCOUNT_SPECIAL_STATUS ( CODE ) tablespace INDX
   )
) tablespace USERS
/

create table SRB_IKFL_S.BKI_ACCOUNT_PAYMENT_STATUS 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(60)         not null,
   constraint PK_BKI_ACCOUNT_PAYMENT_STATUS primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_ACCOUNT_PAYMENT_STATUS on SRB_IKFL.BKI_ACCOUNT_PAYMENT_STATUS ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_ACCOUNT_PAYMENT_STATUS
/

create table SRB_IKFL.BKI_ADRESS_TYPE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   REGISTRATION         CHAR(1)              not null,
   RESIDENCE            CHAR(1)              not null,
   ESB_CODE             varchar2(22),
   IS_DEFAULT           CHAR(1)              not null,
   constraint PK_BKI_ADRESS_TYPE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_ADRESS_TYPE on SRB_IKFL.BKI_ADRESS_TYPE ( CODE ) tablespace INDX
   ),
   constraint AK_ESB_BKI_ADRE unique (ESB_CODE) using index (
	create unique index SRB_IKFL.AK_ESB_BKI_ADRE on SRB_IKFL.BKI_ADRESS_TYPE ( ESB_CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_ADRESS_TYPE
/

create table SRB_IKFL.BKI_APPLICANT_TYPE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_APPLICANT_TYPE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_APPLICANT_TYPE on SRB_IKFL.BKI_APPLICANT_TYPE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_APPLICANT_TYPE
/

create table SRB_IKFL.BKI_APPLICATION_TYPE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_APPLICATION_TYPE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_APPLICATION_TYPE on SRB_IKFL.BKI_APPLICATION_TYPE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_APPLICATION_TYPE
/

create table SRB_IKFL.BKI_BANK_CONSTANT_NAME 
(
   CODE                 varchar2(50)         not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_BANK_CONSTANT_NAME primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_BANK_CONSTANT_NAME on SRB_IKFL.BKI_BANK_CONSTANT_NAME ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_BANK_CONSTANT_NAME
/

create table SRB_IKFL.BKI_CONSENT_INDICATOR 
(
   CODE                  varchar2(2)         not null,
   NAME                 varchar2(20)         not null,
   constraint PK_BKI_CONSENT_INDICATOR primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_CONSENT_INDICATOR on SRB_IKFL.BKI_CONSENT_INDICATOR ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_CONSENT_INDICATOR
/

create table SRB_IKFL.BKI_COUNTRY 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   ESB_CODE             varchar2(22),
   IS_DEFAULT           CHAR(1)              not null,
   constraint PK_BKI_COUNTRY primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_COUNTRY on SRB_IKFL.BKI_COUNTRY ( CODE ) tablespace INDX
   ),
   constraint AK_ESB_BKI_COUN unique (ESB_CODE) using index (
	create unique index SRB_IKFL.AK_ESB_BKI_COUN on SRB_IKFL.BKI_COUNTRY ( ESB_CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_COUNTRY
/

create table SRB_IKFL.BKI_CREDIT_FACILITY_STATUS 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(20)         not null,
   constraint PK_BKI_CREDIT_FACILITY_STATUS primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_CREDIT_FACILITY_STATUS on SRB_IKFL.BKI_CREDIT_FACILITY_STATUS ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_CREDIT_FACILITY_STATUS
/

create table SRB_IKFL.BKI_CURRENT_PREVIOUS_ADDRESS 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_CURRENT_PREVIOUS_ADDRES primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_CURRENT_PREVIOUS_ADDRES on SRB_IKFL.BKI_CURRENT_PREVIOUS_ADDRESS ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_CURRENT_PREVIOUS_ADDRESS
/

create table SRB_IKFL.BKI_DISPUTE_INDICATOR 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_DISPUTE_INDICATOR primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_DISPUTE_INDICATOR on SRB_IKFL.BKI_DISPUTE_INDICATOR ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_DISPUTE_INDICATOR
/

create table SRB_IKFL.BKI_FINANCE_TYPE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_FINANCE_TYPE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_FINANCE_TYPE on SRB_IKFL.BKI_FINANCE_TYPE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_FINANCE_TYPE
/

create table SRB_IKFL.BKI_INSURED_LOAN 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(20)         not null,
   constraint PK_BKI_INSURED_LOAN primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_INSURED_LOAN on SRB_IKFL.BKI_INSURED_LOAN ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_INSURED_LOAN
/

create table SRB_IKFL.BKI_PRIMARY_ID_TYPE 
(
   ID                   INTEGER              not null,
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(100)        not null,
   ESB_CODE             varchar2(22),
   IS_DEFAULT           CHAR(1)              not null,
   constraint PK_BKI_PRIMARY_ID_TYPE primary key (ID) using index (
	create unique index SRB_IKFL.PK_BKI_PRIMARY_ID_TYPE on SRB_IKFL.BKI_PRIMARY_ID_TYPE ( ID ) tablespace INDX
   ),
   constraint AK_ESB_BKI_PRIM unique (ESB_CODE) using index (
	create unique index SRB_IKFL.AK_ESB_BKI_PRIM on SRB_IKFL.BKI_PRIMARY_ID_TYPE ( ESB_CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_PRIMARY_ID_TYPE
/

create table SRB_IKFL.BKI_PURPOSE_OF_FINANCE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_PURPOSE_OF_FINANCE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_PURPOSE_OF_FINANCE on SRB_IKFL.BKI_PURPOSE_OF_FINANCE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_PURPOSE_OF_FINANCE
/

create table SRB_IKFL.BKI_REASON_FOR_CLOSURE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(60)         not null,
   constraint PK_BKI_REASON_FOR_CLOSURE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_REASON_FOR_CLOSURE on SRB_IKFL.BKI_REASON_FOR_CLOSURE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_REASON_FOR_CLOSURE
/

create table SRB_IKFL.BKI_REASON_FOR_ENQUIRY 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_REASON_FOR_ENQUIRY primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_REASON_FOR_ENQUIRY on SRB_IKFL.BKI_REASON_FOR_ENQUIRY ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_REASON_FOR_ENQUIRY
/

create table SRB_IKFL.BKI_REGION_CODE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_REGION_CODE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_REGION_CODE on SRB_IKFL.BKI_REGION_CODE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_REGION_CODE
/

create table SRB_IKFL.BKI_SEX 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(20)         not null,
   ESB_CODE             VARCHAR2(4),
   IS_DEFAULT           CHAR(1)              not null,
   constraint PK_BKI_SEX primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_SEX on SRB_IKFL.BKI_SEX ( CODE ) tablespace INDX
   ),
   constraint AK_ESB_BKI_SEX unique (ESB_CODE) using index (
	create unique index SRB_IKFL.AK_ESB_BKI_SEX on SRB_IKFL.BKI_SEX ( ESB_CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_SEX
/

create table SRB_IKFL.BKI_TITTLE 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(20)         not null,
   constraint PK_BKI_TITTLE primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_TITTLE on SRB_IKFL.BKI_TITTLE ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_TITTLE
/

create table SRB_IKFL.BKI_TYPE_OF_SCORE_CARD 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_TYPE_OF_SCORE_CARD primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_TYPE_OF_SCORE_CARD on SRB_IKFL.BKI_TYPE_OF_SCORE_CARD ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_TYPE_OF_SCORE_CARD
/

create table SRB_IKFL.BKI_TYPE_OF_SECURITY 
(
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(50)         not null,
   constraint PK_BKI_TYPE_OF_SECURITY primary key (CODE) using index (
	create unique index SRB_IKFL.PK_BKI_TYPE_OF_SECURITY on SRB_IKFL.BKI_TYPE_OF_SECURITY ( CODE ) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_BKI_TYPE_OF_SECURITY
/

-- Номер ревизии: 68032
-- Комментарий: Редактирование локалезависимых сущностей(Редактирование поставщика услуг)
create table SRB_IKFL.SERVICE_PROVIDERS_RES 
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
	create unique index SRB_IKFL.PK_SERVICE_PROVIDERS_RES on SRB_IKFL.SERVICE_PROVIDERS_RES ( UUID, LOCALE_ID ) tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 68193
-- Комментарий: Редактирование локалезависимых сущностей. Редактирование платформы.
create table SRB_IKFL.MOBILE_PLATFORM_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   PLATFORM_NAME        VARCHAR2(100)        not null,
   ERROR_TEXT           VARCHAR2(500)        not null,
   constraint PK_MOBILE_PLATFORM_RES primary key (ID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_MOBILE_PLATFORM_RES on SRB_IKFL.MOBILE_PLATFORM_RES ( ID, LOCALE_ID ) tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 68243
-- Комментарий: Редактирование локалезависимых сущностей. Техперерывы.
create table SRB_IKFL.TECHNOBREAKS_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   MESSAGE              VARCHAR2(200)        not null,
   constraint PK_TECHNOBREAKS_RES primary key (ID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_TECHNOBREAKS_RES on SRB_IKFL.TECHNOBREAKS_RES ( ID, LOCALE_ID ) tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 68267
-- Комментарий: Редактирование локалезависимых сущностей(Маппинг ошибок)
create table SRB_IKFL.EXCEPTION_MAPPINGS_RES 
(
   HASH                 varchar2(277)        not null,
   GROUP_ID             INTEGER              not null,
   LOCALE_ID            varchar2(30)         not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS_RES primary key (HASH, GROUP_ID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_EXCEPTION_MAPPINGS_RES on SRB_IKFL.EXCEPTION_MAPPINGS_RES ( HASH, GROUP_ID, LOCALE_ID ) tablespace INDX
   )
) tablespace USERS
/

-- Номер ревизии: 68331
-- Комментарий: Синхронизация УАК
create table SRB_IKFL.MBK_CAST 
(
   PHONE                VARCHAR2(11)         not null
)
partition by hash (PHONE) partitions 128  tablespace USERS
/
create sequence SRB_IKFL.S_MBK_CAST
/

create index SRB_IKFL.IDX_MBK_CAST_PHONE on SRB_IKFL.MBK_CAST (
   PHONE ASC
) local tablespace INDX
/

-- Номер ревизии 68546
-- Комментарий: Корзина. Линки для напоминаний
create table SRB_IKFL.REMINDER_LINKS 
(
   LOGIN_ID             INTEGER              not null,
   REMINDER_ID          INTEGER              not null,
   DELAYED_DATE         TIMESTAMP,
   PROCESS_DATE         TIMESTAMP,
   RESIDUAL_DATE        TIMESTAMP
) tablespace USERS
/

create unique index SRB_IKFL.I_REMINDER_LINKS on SRB_IKFL.REMINDER_LINKS (
   LOGIN_ID ASC,
   REMINDER_ID ASC
) tablespace INDX
/

--Номер ревизии: 68732
--Комментарий: Хранение аватаров в АК
create table SRB_IKFL.PHONES_TO_AVATAR 
(
   PHONE                VARCHAR2(11)         not null,
   AVATAR_PATH          VARCHAR2(100)        not null,
   LOGIN_ID             INTEGER
) partition by hash(PHONE) partitions 128 tablespace USERS
/

create index SRB_IKFL.IDX_PH_TO_AV_LID on SRB_IKFL.PHONES_TO_AVATAR (
   LOGIN_ID ASC
) tablespace INDX
/

create index SRB_IKFL.IDX_PH_TO_AV_PH on SRB_IKFL.PHONES_TO_AVATAR (
   PHONE ASC
) local tablespace INDX
/

--Номер ревизии: 68760
--Комментарий: Многоязычность. Редактирование локалезависмых данных услуги.
create table SRB_IKFL.PAYMENT_SERVICES_RES 
(
   UUID                 VARCHAR2(50)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512),
   constraint PK_PAYMENT_SERVICES_RES primary key (UUID, LOCALE_ID) using index (
	create unique index SRB_IKFL.PK_PAYMENT_SERVICES_RES on SRB_IKFL.PAYMENT_SERVICES_RES ( UUID, LOCALE_ID ) tablespace INDX
   )
) tablespace USERS
/

--Номер ревизии: 69002
--Комментарий: Бизнес отчет ЕРМБ «Количество клиентов с разбивкой по тарифным планам». История изменений тарифного плана
create table SRB_IKFL.ERMB_CLIENT_TARIFF_HISTORY 
(
   ID                   NUMBER               not null,
   ERMB_PROFILE_ID      NUMBER               not null,
   ERMB_TARIFF_ID       NUMBER               not null,
   ERMB_TARIFF_NAME     VARCHAR2(128),
   CHANGE_DATE          TIMESTAMP            not null,
   TB                   VARCHAR2(4 BYTE)     not null
)
partition by range (CHANGE_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('01-01-2014','DD-MM-YYYY')) tablespace USERS
) tablespace USERS
/
create sequence SRB_IKFL.S_ERMB_CLIENT_TARIFF_HISTORY
/

--Номер ревизии: 69090
--Комментарий: Агрегация поставщиков услуг
create table SRB_IKFL.AGGREGATION_STATE 
(
   KEY                  VARCHAR2(64)         not null,
   TIME                 TIMESTAMP
) tablespace USERS
/

-- Номер ревизии: 69417
insert into SRB_IKFL.AGGREGATION_STATE values ('catalog.last.update.timestamp', null)
/
insert into SRB_IKFL.PROPERTIES (ID, PROPERTY_VALUE, PROPERTY_KEY, CATEGORY) values (SRB_IKFL.S_PROPERTIES.nextval, '1', 'com.rssl.iccs.phizic.catalog.aggreagation.current.partition', 'phiz')
/

create table SRB_IKFL.PAYMENT_SERVICES_AGGR 
(
   SERVICE_ID           INTEGER              not null,
   PARENT_SERVICE_ID    INTEGER,
   SERVICE_NAME         VARCHAR2(128)        not null,
   IMAGE_ID             INTEGER,
   IMAGE_NAME           VARCHAR2(128)        not null,
   GUID                 VARCHAR2(50)         not null,
   CHANEL               VARCHAR2(10)         not null,
   REGION_ID            INTEGER              not null,
   PRIORITY             INTEGER,
   AVAILABLE            VARCHAR2(32)         not null,
   P_KEY                CHAR(1)              not null
)
partition by list (P_KEY)
(
	partition P_1 values ('1'),
    partition P_2 values ('2')
) tablespace INDX
/

create index SRB_IKFL.IDX_PSA on SRB_IKFL.PAYMENT_SERVICES_AGGR (
   REGION_ID ASC,
   CHANEL ASC,
   AVAILABLE ASC,
   PARENT_SERVICE_ID ASC
)
local tablespace INDX
/

create table SRB_IKFL.SERVICE_PROVIDERS_AGGR 
(
   ID                   INTEGER              not null,
   PROVIDER_NAME        VARCHAR2(300)        not null,
   ALIAS                VARCHAR2(1024),
   LEGAL_NAME           VARCHAR2(264),
   CODE_RECIPIENT_SBOL  VARCHAR2(32)         not null,
   NAME_B_SERVICE       VARCHAR2(150),
   BILLING_ID           INTEGER,
   SORT_PRIORITY        INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   INN                  VARCHAR2(12),
   ACCOUNT              VARCHAR2(25),
   IMAGE_ID             INTEGER,
   IMAGE_UPDATE_TIME    TIMESTAMP,
   IMAGE_MD5            VARCHAR(32),
   H_IMAGE_ID           INTEGER,
   H_IMAGE_UPDATE_TIME  TIMESTAMP,
   H_IMAGE_MD5          VARCHAR(32),
   IS_BAR_SUPPORTED     CHAR(1),
   SUB_TYPE             VARCHAR2(10),
   CHANEL               VARCHAR2(10)         not null,
   REGION_ID            INTEGER              not null,
   AVAILABLE_PAYMENTS   CHAR(1),
   AVAILABLE_ESB_AUTOP  CHAR(1),
   AVAILABLE_IQW_AUTOP  CHAR(1),
   AVAILABLE_BASKET     CHAR(1),
   AVAILABLE_TEMPLATES  CHAR(1),
   AVAILABLE_MB_TEMPLATES CHAR(1),
   SERVICE_ID           INTEGER              not null,
   SERVICE_NAME         VARCHAR2(128),
   SERVICE_IMAGE        VARCHAR2(128),
   SERVICE_GUID         VARCHAR2(50),
   SHOW_SERVICE         CHAR(1),
   GROUP_ID             INTEGER,
   GROUP_NAME           VARCHAR2(128),
   GROUP_IMAGE          VARCHAR2(128),
   GROUP_GUID           VARCHAR2(50),
   SHOW_GROUP           CHAR(1),
   CATEGORY_ID          INTEGER,
   CATEGORY_NAME        VARCHAR2(128),
   CATEGORY_IMAGE       VARCHAR2(128),
   CATEGORY_GUID        VARCHAR2(50),
   SHOW_CATEGORY        CHAR(1),
   P_KEY                CHAR(1)              not null,
   constraint SP_AGRR_PK primary key (P_KEY, REGION_ID, CHANEL, SERVICE_ID, ID) 
) organization index partition by list(P_KEY)
(
	partition P_1 values ('1') tablespace INDX,
	partition P_2 values ('2') tablespace INDX
) tablespace INDX
/

--Номер ревизии: 69129
--Комментарий: Перевод сообщений об ошибках, приходящих из ВС.
create table SRB_IKFL.LOCALE_EXCEPTION_MAPPINGS 
(
   ID                   INTEGER              not null,
   MESSAGE_KEY          VARCHAR2(160),
   ERROR_KEY            VARCHAR2(20),
   PATTERN              VARCHAR2(2000)       not null,
   FORMATTER            VARCHAR2(2000)       not null,
   constraint PK_LOCALE_EXCEPTION_MAPPINGS primary key (ID) using index (
	create unique index SRB_IKFL.PK_LOCALE_EXCEPTION_MAPPINGS on SRB_IKFL.LOCALE_EXCEPTION_MAPPINGS ( ID ) tablespace INDX
   )
) tablespace USERS
/

--Номер ревизии: 69322  
--Комментарий: АЛФ: категоризация операций push.
create table SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS 
(
   ID                   INTEGER              not null,
   CARD_OPERATION_ID    INTEGER              not null,
   PUSHUID              INTEGER,
   PARENT_PUSHUID       INTEGER,
   AUTHCODE             VARCHAR2(6),
   OPERATION_TYPE_WAY   VARCHAR2(2),
   LOAD_DATE_MAPI       TIMESTAMP(6),
   CATEGORY_CHANGE      CHAR(1)              not null,
   OPERATION_DATE       TIMESTAMP            not null,
   constraint PK_CARD_OPERATIONS_EXT_FIELDS primary key (ID) using index (
	create unique index SRB_IKFL.PK_CARD_OPERATIONS_EXT_FIELDS on SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS ( ID ) tablespace INDX
   )
)
partition by range (OPERATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_FIRST values less than (to_date('01-01-2012','DD-MM-YYYY'))
)
/
create sequence SRB_IKFL.S_CARD_OPERATIONS_EXT_FIELDS
/

create index SRB_IKFL.I_CARDOPEF_PUSHUID on SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS (
   PUSHUID ASC
) tablespace INDX
/

create index SRB_IKFL.I_CARDOPEF_PARENT_PUSHUID on SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS (
   PARENT_PUSHUID ASC
) tablespace INDX
/

create index SRB_IKFL.I_CARDOPEF_CARD_OPERATION_ID on SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS (
   CARD_OPERATION_ID ASC
) tablespace INDX
/

create index SRB_IKFL.I_CARDOPEF_CARD_OPERATION_DATE on SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS (
   OPERATION_DATE ASC
) local  tablespace INDX
/

-- Номер ревизии: 69790
-- Комментарий: ПРофиль адресная книга
create table SRB_IKFL.IDENT_TYPE_FOR_DEPS 
(
   ID                   INTEGER              not null,
   SYSTEM_ID            VARCHAR2(20)         not null,
   NAME                 VARCHAR2(100)        not null,
   UUID                 VARCHAR2(100),
   constraint "PK_IDENT_TYPE_FOR_DEPS " primary key (ID) using index (
      create unique index SRB_IKFL.PK_IDENT_TYPE_FOR_DEPS on SRB_IKFL.IDENT_TYPE_FOR_DEPS(ID) tablespace INDX
   )
) tablespace USERS
/
create sequence SRB_IKFL.S_IDENT_TYPE_FOR_DEPS 
/

insert into SRB_IKFL.IDENT_TYPE_FOR_DEPS (ID, SYSTEM_ID, NAME, UUID) 
	values (SRB_IKFL.S_IDENT_TYPE_FOR_DEPS.nextval, 'INN', 'ИНН', '00000000000000000000000000000001')
/
insert into SRB_IKFL.IDENT_TYPE_FOR_DEPS (ID, SYSTEM_ID, NAME, UUID) 
	values (SRB_IKFL.S_IDENT_TYPE_FOR_DEPS.nextval, 'DL', 'Водительское удостоверение', '00000000000000000000000000000002')
/

create table SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES 
(
   ID                   INTEGER              not null,
   IDENT_ID             INTEGER              not null,
   SYSTEM_ID            VARCHAR2(20)         not null,
   NAME                 VARCHAR2(100)        not null,
   DATA_TYPE            VARCHAR2(20)         not null,
   REG_EXP              VARCHAR2(100),
   MANDATORY            CHAR(1)              not null,
   UUID                 VARCHAR2(100),
   constraint PK_ATTRIBUTE_FOR_IDENT_TYPE primary key (ID) using index (
      create unique index SRB_IKFL.PK_ATTRIBUTE_FOR_IDENT_TYPE on SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES(ID) tablespace INDX
   )
) tablespace USERS
/

create sequence SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES
/

insert into SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID) 
	select SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, ID, 'NUMBER', 'ИНН', 'TEXT', '\d{12}', '0', '00000000000000000000000000000003' 
		from SRB_IKFL.IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'INN'
/
insert into SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
	select SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, ID, 'NUMBER', 'Номер', 'TEXT', '\d{6}', '0', '00000000000000000000000000000004' 
		from SRB_IKFL.IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
/
insert into SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
	select SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, ID, 'SERIES', 'Серия', 'TEXT', '\d{2}[0-9A-ZА-Я]{2}', '0', '00000000000000000000000000000005' 
		from SRB_IKFL.IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL'
/
insert into SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
	select SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, ID, 'ISSUE_DATE', 'Дата выдачи', 'DATE', '\d{2}.\d{2}.\d{4}', '0', '00000000000000000000000000000005' 
		from SRB_IKFL.IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
/
insert into SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
	select SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, ID, 'ISSUE_BY', 'Кем выдано', 'TEXT', '.{150}', '0', '00000000000000000000000000000006' 
		from SRB_IKFL.IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
/  
insert into SRB_IKFL.ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
	select SRB_IKFL.S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, ID, 'EXPIRE_DATE', 'Действует до', 'DATE', '\d{2}.\d{2}.\d{4}', '0', '00000000000000000000000000000007' 
		from SRB_IKFL.IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
/

--Вынос шаблонов:
alter session force parallel ddl parallel 32
/

create table SRB_IKFL.IMP$PROVIDERS_N1 tablespace USERS nologging 
as select /*+parallel(sp, 32)*/
  ID   INTERNAL_RECEIVER_ID, 
  UUID MULTI_BLOCK_RECEIVER_CODE,
  1 NODE_ID
from SRB_IKFL.SERVICE_PROVIDERS sp
/
create table SRB_IKFL.IMP$USER_PROFILES_N1 tablespace USERS nologging  
as select /*+parallel(u, 16) parallel(d, 16) parallel(dep, 16)*/
  u.ID NODE_CLIENT_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_SERIES PASSPORT, BIRTHDAY, dep.TB, 1 NODE_ID
from USERS u
left join DOCUMENTS d on d.PERSON_ID=u.ID and d.DOC_TYPE='PASSPORT_WAY'
inner join DEPARTMENTS dep on dep.ID=u.DEPARTMENT_ID
where SUR_NAME is not null and DOC_SERIES is not null and BIRTHDAY is not null and ( u.STATUS!='D' and u.STATE!='D')
/