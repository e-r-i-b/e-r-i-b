/*==============================================================*/
-- Table: PAYMENT_FORM_TRANSFORMATIONS версионность платежных форм
/*==============================================================*/
create table PAYMENT_FORM_TRANSFORMATIONS  (
   ID                   integer                         not null,
   FORM_ID              integer                         not null,
   TYPE                 varchar2(8)                     not null,
   TRANSFORMATION       clob                            not null,
   constraint PK_PAYMENT_FORM_TRANSFORMATION primary key (ID)
)
/

create sequence S_PAYMENT_FORM_TRANSFORMATIONS
/

create index "DXFK_TRANSFORM_TO_FORM" on PAYMENT_FORM_TRANSFORMATIONS
(
   FORM_ID
)
/

alter table PAYMENT_FORM_TRANSFORMATIONS
   add constraint FK_TRANSFORM_TO_FORM foreign key (FORM_ID)
      references PAYMENTFORMS (ID)
/


/*==============================================================*/
/* Table: ERMB_TARIF                                            */
/*==============================================================*/
create table ERMB_TARIF  (
   ID                         integer                         not null,
   NAME                       varchar2(128)                   not null,
   CONNECT_COST_AMOUNT        integer,
   CONNECT_COST_CURRENCY      char(3),
   CHARGE_PERIOD              integer                         not null,
   GRACE_PERIOD               integer                         not null,
   GRACE_PERIOD_COST_AMOUNT   integer,
   GRACE_PERIOD_COST_CURRENCY char(3),
   CLASS_GRACE_AMOUNT         integer,
   CLASS_GRACE_CURRENCY       char(3),
   CLASS_PREMIUM_AMOUNT       integer,
   CLASS_PREMIUM_CURRENCY     char(3),
   CLASS_SOCIAL_AMOUNT        integer,
   CLASS_SOCIAL_CURRENCY      char(3),
   CLASS_STANDARD_AMOUNT      integer,
   CLASS_STANDARD_CURRENCY    char(3),
   STATUS                     varchar2(10)                    not null,
   OP_NOTICE_CONS_INCOM_CARD  varchar2(24)                    not null,
   OP_NOTICE_CONS_INCOM_ACC   varchar2(24)                    not null,
   OP_CARD_INFO               varchar2(24)                    not null,
   OP_ACC_INFO                varchar2(24)                    not null,
   OP_CARD_MINI_INFO          varchar2(24)                    not null,
   OP_ACC_MINI_INFO           varchar2(24)                    not null,
   OP_RE_ISSUE_CARD           varchar2(24)                    not null,
   OP_JUR_PAYMENT             varchar2(24)                    not null,
   OP_TRANSFERT_THIRD_PARTIES varchar2(24)                    not null,
   CODE                       varchar2(24),
   constraint PK_ERMB_TARIF primary key (ID),
   constraint UNIQUE_CODE unique (CODE)
)
/

create sequence S_ERMB_TARIF
/

/*==============================================================*/
/* Table: ERMB_PROFILE                                          */
/*==============================================================*/
create table ERMB_PROFILE  (
   ID                       integer                         not null,
   PERSON_ID                integer                         not null,
   OLD_FIRST_NAME           varchar2(42),
   OLD_SUR_NAME             varchar2(42),
   OLD_PATR_NAME            varchar2(42),
   OLD_BIRTHDAY             timestamp,
   OLD_DOCUMENT_ID          integer,
   SERVICE_STATUS           varchar2(13)                    not null,
   END_SERVICE_DATE         timestamp,
   FOREG_PRODUCT_ID         integer,
   CONNECTION_DATE          timestamp,
   NEW_PRODUCT_NOTIFICATION char(1)             default '0' not null,
   DAYS_OF_WEEK             varchar2(28),
   TIME_START               varchar2(10),
   TIME_END                 varchar2(10),
   TIME_ZONE                integer,
   ERMB_TARIF_ID            integer                         not null,
   CLIENT_CATEGORY          char(1),
   LAST_REQUEST_TIME        timestamp,
   FAST_SERVICE             char(1)             default '0' not null,
   ADVERTISING              char(1)             default '1' not null,
   VERSION                  integer             default 1 not null,
   CONFIRM_VERSION          integer             default 0 not null,
   constraint PK_ERMB_PROFILE primary key (ID)
)
/

create sequence S_ERMB_PROFILE
/

create index "DXFK_ERMB_PROFILE_OLD_DOCUMENT" on ERMB_PROFILE
(
   OLD_DOCUMENT_ID
)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_OLD_DOCUMENT foreign key (OLD_DOCUMENT_ID)
      references DOCUMENTS (ID)
/


create index "DXFK_ERMB_PROFILE_TARIF" on ERMB_PROFILE
(
   ERMB_TARIF_ID
)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_TARIF foreign key (ERMB_TARIF_ID)
      references ERMB_TARIF (ID)
/


create index "DXFK_ERMB_PROFILE_USERS" on ERMB_PROFILE
(
   PERSON_ID
)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_USERS foreign key (PERSON_ID)
      references USERS (ID)
/

/*==============================================================*/
/* Table: ERMB_CLIENT_PHONE                                     */
/*==============================================================*/
create table ERMB_CLIENT_PHONE  (
   ID                       integer                         not null,
   PROFILE_ID               integer                         not null,
   IS_MAIN                  char(1)             default '0' not null,
   PHONE_NUMBER             varchar2(20)                    not null,
   MOBILE_PHONE_OPERATOR    varchar2(100)       default '0' not null,
   constraint PK_ERMB_CLIENT_PHONE primary key (ID)
)
/

create sequence S_ERMB_CLIENT_PHONE
/

create unique index UNIQ_ERMB_PHONE_NUMBER on ERMB_CLIENT_PHONE (
   PHONE_NUMBER ASC
)
/
create index "DXFK_ERMB_CLIENT_PHONE_PROFILE" on ERMB_CLIENT_PHONE
(
   PROFILE_ID
)
/

alter table ERMB_CLIENT_PHONE
   add constraint FK_ERMB_CLIENT_PHONE_PROFILE foreign key (PROFILE_ID)
      references ERMB_PROFILE (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: ERMB_TMP_PHONE                                        */
/*==============================================================*/
create table ERMB_TMP_PHONE  (
   ID                      number                         not null,
   PHONE_NUMBER            varchar(20)                    not null,
   MOBILE_PHONE_OPERATOR   varchar(100)                   not null,
   constraint PK_ERMB_TMP_PHONE primary key (ID)
)
/

create sequence S_ERMB_TMP_PHONE
/

create unique index ERMB_TMP_PHONE_UNIQUE_NUMBER on ERMB_TMP_PHONE (
   PHONE_NUMBER ASC
)
/

/*==============================================================*/
/* Table: CONTACTS Синхронизация мобильных контактов            */
/*==============================================================*/
create table CONTACTS  (
   ID                   integer                         not null,
   LOGIN_ID             integer                         not null,
   NAME                 varchar2(50)                    not null,
   PHONE                varchar2(11)                    not null,
   constraint PK_CONTACTS primary key (ID)
)
/

create sequence S_CONTACTS
/

create index "DXFK_CONTACTS_TO_LOGINS" on CONTACTS
(
   LOGIN_ID
)
/

alter table CONTACTS
   add constraint FK_CONTACTS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

/*==============================================================*/
/* Table: CONTACTS_SYNC Синхронизация мобильных контактов       */
/*==============================================================*/
create table CONTACTS_SYNC  (
   ID                   integer                         not null,
   LOGIN_ID             integer                         not null,
   SYNC_COUNT           integer                         not null,
   LAST_SYNC_DATE       timestamp                       not null,
   constraint PK_CONTACTS_SYNC primary key (ID)
)
/

create sequence S_CONTACTS_SYNC
/

create index "DXFK_CONTACTS_SYNC_TO_LOGINS" on CONTACTS_SYNC
(
   LOGIN_ID
)
/

alter table CONTACTS_SYNC
   add constraint FK_CONTACTS_SYNC_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

/*==============================================================*/
/* Table: WEB_PAGES                                             */
/*==============================================================*/
create table WEB_PAGES  (
   ID                   integer                         not null,
   CLASSNAME            varchar2(64)                    not null,
   LOCATION             varchar(16)                     not null,
   LAYOUT               clob                            not null,
   PROFILE_ID           integer                         not null,
   constraint PK_WEB_PAGES primary key (ID)
)
/

create sequence S_WEB_PAGES
/

create index "DXPROFILE_TO_WEB_PAGES" on WEB_PAGES
(
   PROFILE_ID
)
/

alter table WEB_PAGES
   add constraint FK_PROFILE_TO_WEB_PAGES foreign key (PROFILE_ID)
      references PROFILE (ID)
/

/*==============================================================*/
/* Table: WIDGETS                                               */
/*==============================================================*/
create table WIDGETS  (
   CODENAME             varchar2(80)                    not null,
   DEFINITION_CODENAME  varchar2(25)                    not null,
   WEB_PAGE_ID          integer                         not null,
   BODY                 clob                            not null,
   constraint PK_WIDGETS primary key (CODENAME)
)
/

create sequence S_WIDGETS
/

create index "DXWIDGETS_R01" on WIDGETS
(
   WEB_PAGE_ID
)
/

alter table WIDGETS
   add constraint WIDGETS_R01 foreign key (WEB_PAGE_ID)
      references WEB_PAGES (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: WIDGET_DEFINITIONS описания виджетов                  */
/*==============================================================*/
create table WIDGET_DEFINITIONS  (
   CODENAME             varchar2(25)                    not null,
   USERNAME             varchar2(50)                    not null,
   DESCRIPTION          varchar2(256)                   not null,
   LOAD_MODE            varchar2(5)                     not null,
   URL_PATH             varchar2(100)                   not null,
   OPERATION            varchar2(35)                    not null,
   WIDGET_CLASS         varchar2(256)                   not null,
   PICTURE              varchar2(100),
   INITIAL_SIZE         varchar2(10)                    not null,
   SIZEABLE             char(1)             default '0' not null,
   INDEX_NUMBER         number              default '0' not null,
   AVAILABILITY         char(1)             default '1' not null,
   ADDING_DATE          timestamp                       not null,
   ACCESSOR             varchar2(256)                   not null,
   INITIALIZER          varchar2(256)                   not null,
   constraint PK_WIDGET_DEFINITIONS primary key (CODENAME)
)
/

create sequence S_WIDGET_DEFINITIONS
/

comment on table WIDGET_DEFINITIONS is 'Описание (класса) виджета'
/

/*==============================================================*/
/* Table: CITIES Погодный виджет: справочник городов            */
/*==============================================================*/
create table CITIES  (
   CODE                 varchar2(20)                    not null,
   REGION_CODE          varchar2(20),
   NAME                 varchar2(128)                   not null,
   EN_NAME              varchar2(128)                   not null,
   constraint PK_CITIES primary key (CODE)
)
/

create sequence S_CITIES
/
/* Вынесена в базу логов*/
/*==============================================================*/
/* Table: QUICK_PAYMENT_PANELS_LOG Отчет по статистике ПБО      */
/*==============================================================*/
/*
create table QUICK_PAYMENT_PANELS_LOG  (
   ID                   integer                         not null,
   PANEL_ID             integer                         not null,
   TB                   varchar2(4)                     not null,
   TYPE                 varchar2(16)                    not null,
   START_DATE           timestamp                       not null,
   AMOUNT               number(15,4),
   constraint PK_QUICK_PAYMENT_PANELS_LOG primary key (ID)
)
/

create sequence S_QUICK_PAYMENT_PANELS_LOG
/
*/

/*==============================================================*/
/* Table: MONITORING_SERVICE_CONFIGS                            */
/*==============================================================*/
create table MONITORING_SERVICE_CONFIGS  (
   SERVICE                varchar2(50)                    not null,
   STATE                  varchar2(15)                    not null,
   DEGRADATION_CONFIG_ID  integer                         not null,
   INACCESSIBLE_CONFIG_ID integer                         not null,
   constraint PK_MONITORING_SERVICE_CONFIGS primary key (SERVICE)
)
/

create sequence S_MONITORING_SERVICE_CONFIGS
/

/*==============================================================*/
/* Table: MONITORING_STATE_CONFIGS                              */
/*==============================================================*/
create table MONITORING_STATE_CONFIGS  (
   ID                   integer                         not null,
   AVAILABLE            char(1)                         not null,
   USE                  char(1)                         not null,
   TIMEOUT              number,
   MONITORING_TIME      number,
   MONITORING_COUNT     number,
   DETERIORATION_TIME   timestamp,
   MESSAGE              varchar2(500),
   RECOVERY_TIME        number,
   AVAILABLE_CHANGE_INACTIVE_TYPE char(1)               not null,
   INACTIVE_TYPE        varchar2(8),
   constraint PK_MONITORING_STATE_CONFIGS primary key (ID)
)
/

create sequence S_MONITORING_STATE_CONFIGS
/

create index "DXFK_MONITORING_GATE_D_CONFIGS" on MONITORING_SERVICE_CONFIGS
(
   DEGRADATION_CONFIG_ID
)
/

alter table MONITORING_SERVICE_CONFIGS
   add constraint FK_MONITORING_GATE_D_CONFIGS foreign key (DEGRADATION_CONFIG_ID)
      references MONITORING_STATE_CONFIGS (ID)
/


create index "DXFK_MONITORING_GATE_I_CONFIGS" on MONITORING_SERVICE_CONFIGS
(
   INACCESSIBLE_CONFIG_ID
)
/

alter table MONITORING_SERVICE_CONFIGS
   add constraint FK_MONITORING_GATE_I_CONFIGS foreign key (INACCESSIBLE_CONFIG_ID)
      references MONITORING_STATE_CONFIGS (ID)
/

/*==============================================================*/
/* Table: FIELD_REQUISITE_TYPES таблицы реквизитов поля         */
/*==============================================================*/
create table FIELD_REQUISITE_TYPES  (
   FIELD_ID             integer                         not null,
   REQUISITE_TYPE       varchar2(32)                    not null,
   LIST_INDEX           integer                         not null,
   constraint PK_FIELD_REQUISITE_TYPES primary key (FIELD_ID, LIST_INDEX)
)
/

create sequence S_FIELD_REQUISITE_TYPES
/
create index "DXFK_F_REQUISITE_TYPE_TO_FIELD" on FIELD_REQUISITE_TYPES
(
   FIELD_ID
)
/

alter table FIELD_REQUISITE_TYPES
   add constraint FK_F_REQUISITE_TYPE_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: CONFIGS конфиги                                       */
/*==============================================================*/
create table CONFIGS  (
   CODENAME             varchar2(32)                    not null,
   DATA                 clob                            not null,
   constraint PK_CONFIGS primary key (CODENAME)
)
/

create sequence S_CONFIGS
/

comment on table CONFIGS is 'Конфиги в сериализованном виде (xml, json и т.д.)'
/

/*==============================================================*/
-- Table: OFFLINE_EXT_SYSTEM_EVENT  Автоматическое определение недоступности внешних систем
/*==============================================================*/
create table OFFLINE_EXT_SYSTEM_EVENT  (
   ID                   integer                         not null,
   ADAPTER_ID           integer                         not null,
   SYSTEM_TYPE          varchar2(4),
   EVENT_TIME           timestamp,
   constraint PK_OFFLINE_EXT_SYSTEM_EVENT primary key (ID)
)
/

create sequence S_OFFLINE_EXT_SYSTEM_EVENT
/

create index DX_OFFLINE_ADAPTER_TIME on OFFLINE_EXT_SYSTEM_EVENT (
   ADAPTER_ID ASC,
   SYSTEM_TYPE ASC,
   EVENT_TIME ASC
)
/

alter table OFFLINE_EXT_SYSTEM_EVENT
   add constraint FK_OFFLINE_SYSTEM_TO_ADAPTER foreign key (ADAPTER_ID)
      references ADAPTERS (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: STORED_ACCOUNT   оффлайн продукты                     */
/*==============================================================*/
create table STORED_ACCOUNT  (
   ID                        integer                         not null,
   RESOURCE_ID               integer                         not null,
   OPEN_DATE                 timestamp,
   PROLONGATION_ALLOWED      char(3),
   INTEREST_RATE             number(15,4),
   KIND                      integer,
   SUB_KIND                  integer,
   INTEREST_TRANSFER_ACCOUNT varchar2(25),
   INTEREST_TRANSFER_CARD    varchar2(25),
   DEMAND                    char(1),
   PERIOD                    varchar2(15),
   CLOSE_DATE                timestamp,
   CREDIT_CROSS_AGENCY       char(1),
   DEBIT_CROSS_AGENCY        char(1),
   ENTITY_UPDATE_TIME        timestamp,
   ENTITY_UPDATE_INFO_TIME   timestamp,
   ACCOUNT_STATE             varchar2(15),
   PASSBOOK                  char(1),
   CREDIT_ALLOWED            char(1),
   DEBIT_ALLOWED             char(1),
   BALANCE_AMOUNT            number(15,4),
   BALANCE_AMOUNT_CURRENCY   char(3),
   MAX_SUM_AMOUNT            number(15,4),
   MAX_SUM_AMOUNT_CURRENCY   char(3),
   MIN_BALANCE_AMOUNT        number(15,4),
   MIN_BALANCE_AMOUNT_CURRENCY char(3),
   DEPARTMENT_ID             integer,
   OFFICE_OSB                varchar2(5),
   OFFICE_TB                 varchar2(5),
   OFFICE_VSP                varchar2(5),
   constraint PK_STORED_ACCOUNT primary key (ID)
)
/

create sequence S_STORED_ACCOUNT cache 500
/

create index "DXSTORED_A_TO_DEPARTMENTS" on STORED_ACCOUNT
(
   DEPARTMENT_ID
)
/

alter table STORED_ACCOUNT
   add constraint FK_STORED_A_TO_DEPARTMENT_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/

create index "DXSTORED_A_TO_LINK" on STORED_ACCOUNT
(
   RESOURCE_ID
)
/

alter table STORED_ACCOUNT
   add constraint FK_STORED_A_TO_LINK_REF foreign key (RESOURCE_ID)
      references ACCOUNT_LINKS (ID)
/

/*==============================================================*/
/* Table: STORED_CARD оффлайн продукты                          */
/*==============================================================*/
create table STORED_CARD  (
   ID                      integer                         not null,
   RESOURCE_ID             integer                         not null,
   DEPARTMENT_ID           integer,
   ENTITY_UPDATE_TIME      timestamp,
   ENTITY_UPDATE_INFO_TIME timestamp,
   EXTERNAL_STATUS_CODE    varchar2(20),
   ISSUE_DATE              timestamp,
   DISPLAYED_EXPIRE_DATE   varchar2(20),
   CARD_TYPE               varchar2(20),
   VIRTUAL                 char(1),
   LIMIT_AMOUNT            number(15,4),
   LIMIT_AMOUNT_CURRENCY   char(3),
   ADDITIONAL_CARD_TYPE    varchar2(20),
   STATUS_DESCRIPTION      varchar2(255),
   CASH_LIMIT_AMOUNT       number(15,4),
   CASH_LIMIT_AMOUNT_CURRENCY char(3),
   PURCHASE_LIMIT_AMOUNT    number(15,4),
   PURCHASE_LIMIT_AMOUNT_CURRENCY char(3),
   CARD_STATE              varchar2(20),
   HOLDER_NAME             varchar2(255),
   OFFICE_OSB              varchar2(5),
   OFFICE_TB               varchar2(5),
   OFFICE_VSP              varchar2(5),
   constraint PK_STORED_CARD primary key (ID)
)
/

create sequence S_STORED_CARD cache 500
/

create index "DXSTORED_C_TO_DEPARTMENTS" on STORED_CARD
(
   DEPARTMENT_ID
)
/

alter table STORED_CARD
   add constraint FK_STORED_C_TO_DEPARTMENT_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/

create index "DXSTORED_C_TO_LINK" on STORED_CARD
(
   RESOURCE_ID
)
/

alter table STORED_CARD
   add constraint FK_STORED_C_TO_LINK_REF foreign key (RESOURCE_ID)
      references CARD_LINKS (ID)
/

/*==============================================================*/
/* Table: STORED_LOAN оффлайн продукты                          */
/*==============================================================*/
create table STORED_LOAN  (
   ID                       integer                         not null,
   RESOURCE_ID              integer                         not null,
   DEPARTMENT_ID            integer,
   IS_ANNUITY               char(1),
   STATE                    varchar2(20),
   DESCRIPTION              varchar2(255),
   AGREEMENT_NUMBER         varchar2(25),
   NEXT_PAYMENT_DATE        timestamp,
   TERM_START               timestamp,
   TERM_END                 timestamp,
   TERM_DURATION            varchar2(15),
   RATE                     number(15,4),
   LAST_PAYMENT_DATE        timestamp,
   PAST_DUE_AMOUNT          number(15,4),
   PAST_DUE_AMOUNT_CURRENCY char(3),
   NEXT_PAYMENT_AMOUNT      number(15,4),
   NEXT_PAYMENT_AMOUNT_CURRENCY char(3),
   LAST_PAYMENT_AMOUNT      number(15,4),
   LAST_PAYMENT_AMOUNT_CURRENCY char(3),
   LOAN_AMOUNT              number(15,4),
   LOAN_AMOUNT_CURRENCY     char(3),
   BALANCE_AMOUNT           number(15,4),
   BALANCE_AMOUNT_CURRENCY  char(3),
   ENTITY_UPDATE_TIME       timestamp,
   OFFICE_OSB               varchar2(5),
   OFFICE_TB                varchar2(5),
   OFFICE_VSP               varchar2(5),
   constraint PK_STORED_LOAN primary key (ID)
)
/
 
create sequence S_STORED_LOAN cache 500
/

create index "DXSTORED_L_TO_DEPARTMENTS" on STORED_LOAN
(
   DEPARTMENT_ID
)
/

alter table STORED_LOAN
   add constraint STORED_L_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/

create index "DXSTORED_L_TO_LINK" on STORED_LOAN
(
   RESOURCE_ID
)
/

alter table STORED_LOAN
   add constraint STORED_L_TO_LINK_REF foreign key (RESOURCE_ID)
      references LOAN_LINKS (ID)
/

/*==============================================================*/
/* Table: STORED_IMACCOUNT оффлайн продукты                     */
/*==============================================================*/
create table STORED_IMACCOUNT  (
   ID                      integer                         not null,
   RESOURCE_ID             integer                         not null,
   ENTITY_UPDATE_TIME      timestamp,
   DEPARTMENT_ID           integer,
   NAME                    varchar2(255),
   AGREEMENT_NUMBER        varchar2(25),
   OPEN_DATE               timestamp,
   STATE                   varchar2(20),
   CLOSING_DATE            timestamp,
   BALANCE_AMOUNT          number(15,4),
   BALANCE_AMOUNT_CURRENCY char(3),
   MAX_SUM_AMOUNT          number(15,4),
   MAX_SUM_AMOUNT_CURRENCY char(3),
   OFFICE_OSB              varchar2(5),
   OFFICE_TB               varchar2(5),
   OFFICE_VSP              varchar2(5),
   constraint PK_STORED_IMACCOUNT primary key (ID)
)
/

create sequence S_STORED_IMACCOUNT cache 500
/

create index "DXSTORED_IMA_TO_DEPARTMENTS" on STORED_IMACCOUNT
(
   DEPARTMENT_ID
)
/

alter table STORED_IMACCOUNT
   add constraint FK_STORED_I_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/

create index "DXSTORED_IMA_TO_LINK" on STORED_IMACCOUNT
(
   RESOURCE_ID
)
/

alter table STORED_IMACCOUNT
   add constraint FK_STORED_I_TO_LINK_REF foreign key (RESOURCE_ID)
      references IMACCOUNT_LINKS (ID)
/


/*==============================================================*/
/* Table: STORED_DEPO_ACCOUNT оффлайн продукты                  */
/*==============================================================*/
create table STORED_DEPO_ACCOUNT  (
   ID                   integer                         not null,
   DEPARTMENT_ID        integer,
   STATE                varchar2(20),
   AGREEMENT_NUMBER     varchar2(25),
   AGREEMENT_DATE       timestamp,
   IS_OPERATION_ALLOWED char(1),
   ENTITY_UPDATE_TIME   timestamp,
   DEBT_AMOUNT          number(15,4),
   DEBT_CURRENCY        char(3),
   RESOURCE_ID          integer,
   OFFICE_OSB           varchar2(5),
   OFFICE_TB            varchar2(5),
   OFFICE_VSP           varchar(5),
   constraint PK_STORED_DEPO_ACCOUNT primary key (ID)
)
/

create sequence S_STORED_DEPO_ACCOUNT cache 500
/

create index "DXSTORED_DA_TO_DEPARTMENTS" on STORED_DEPO_ACCOUNT
(
   DEPARTMENT_ID
)
/

alter table STORED_DEPO_ACCOUNT
   add constraint STORED_DA_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/


create index "DXSTORED_DA_TO_LINK" on STORED_DEPO_ACCOUNT
(
   RESOURCE_ID
)
/

alter table STORED_DEPO_ACCOUNT
   add constraint STORED_DA_TO_LINK_REF foreign key (RESOURCE_ID)
      references DEPO_ACCOUNT_LINKS (ID)
/

/*==============================================================*/
/* Table: STORED_LONG_OFFER                                     */
/*==============================================================*/
create table STORED_LONG_OFFER  (
   ID                   INTEGER                         not null,
   DEPARTMENT_ID        INTEGER,
   RESOURCE_ID          INTEGER,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   EXECUTION_EVENT_TYPE VARCHAR2(255),
   PAY_DAY              INTEGER,
   PERCENT              NUMBER(15,4),
   PRIORITY             INTEGER,
   SUM_TYPE             VARCHAR2(255),
   FRIENDLY_NAME        VARCHAR2(255),
   TYPE                 VARCHAR(100),
   ENTITY_UPDATE_TIME   TIMESTAMP,
   OFFICE_OSB           VARCHAR2(5),
   OFFICE_TB            VARCHAR2(5),
   OFFICE_VSP           VARCHAR2(5),
   AMOUNT               NUMBER(15,4),
   AMOUNT_CURRENCY      CHAR(3),
   FLOOR_LIMIT_AMOUNT   NUMBER(15,4),
   FLOOR_LIMIT_AMOUNT_CURRENCY CHAR(3),
   constraint PK_STORED_LONG_OFFER primary key (ID)
)
/

create sequence S_STORED_LONG_OFFER cache 500
/

create index "DXSTORED_LO_TO_DEPARTMENTS" on STORED_LONG_OFFER
(
   DEPARTMENT_ID
)
/

alter table STORED_LONG_OFFER
   add constraint FK_STORED_LO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/


create index "DXSTORED_LO_TO_LINK" on STORED_LONG_OFFER
(
   RESOURCE_ID
)
/

alter table STORED_LONG_OFFER
   add constraint FK_STORED_LO_TO_LINK_REF foreign key (RESOURCE_ID)
      references LONG_OFFER_LINKS (ID)
/


/*==============================================================*/
/* Table: CHANGES_FOR_ASYNCH_SEARCH                             */
/*==============================================================*/
create table CHANGES_FOR_ASYNCH_SEARCH  (
   ID                   integer                         not null,
   OBJECT_CLASS_NAME    varchar2(250),
   OBJECT_KEY           varchar2(100),
   OBJECT_STATE         varchar2(100),
   constraint PK_CHANGES_FOR_ASYNCH_SEARCH primary key (ID)
)
/

create sequence S_CHANGES_FOR_ASYNCH_SEARCH
/

/*==============================================================*/
/* Table: PAYMENT_EXECUTION_RECORDS                             */
/*==============================================================*/
create table PAYMENT_EXECUTION_RECORDS  (
   ID                   integer                         not null,
   DOCUMENT_ID          integer                         not null,
   JOB_NAME             varchar2(128)                   not null,
   ADDING_DATE          timestamp,
   constraint PK_PAYMENT_EXECUTION_RECORDS primary key (ID)
)
/

create sequence S_PAYMENT_EXECUTION_RECORDS
/