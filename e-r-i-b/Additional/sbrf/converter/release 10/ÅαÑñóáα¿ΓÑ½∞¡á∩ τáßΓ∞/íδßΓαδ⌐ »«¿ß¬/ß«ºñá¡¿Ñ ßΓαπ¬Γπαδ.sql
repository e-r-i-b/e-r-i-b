/*==============================================================*/
/* Table: FIELD_DESCRIPTIONS                                    */
/*==============================================================*/
create table FIELD_DESCRIPTIONS  (
   ID                   integer                         not null,
   EXTERNAL_ID          varchar2(40)                    not null,
   NAME                 nvarchar2(40)                   not null,
   DESCRIPTION          nvarchar2(200),
   HINT                 nvarchar2(200),
   TYPE                 varchar2(20),
   MAX_LENGTH           integer,
   MIN_LENGTH           integer,
   NUMBER_PRECISION     integer,
   IS_REQUIRED          char(1)                         not null,
   IS_EDITABLE          char(1)                         not null,
   IS_VISIBLE           char(1)                         not null,
   IS_SUM               char(1)                         not null,
   IS_KEY               char(1)                         not null,
   IS_INCLUDE_IN_SMS    char(1)                         not null,
   IS_SAVE_IN_TEMPLATE  char(1)                         not null,
   IS_FOR_BILL          char(1)                         not null,
   IS_HIDE_IN_CONFIRMATION char(1)                      not null,
   INITIAL_VALUE        nvarchar2(1024),
   RECIPIENT_ID         integer                         not null,
   LIST_INDEX           integer                         not null,
   constraint PK_FIELD_DESCRIPTIONS primary key (ID)
)
/

create sequence S_FIELD_DESCRIPTIONS
/

/*==============================================================*/
/* Index: FIELD_DESCRIPTIONS_EXTERNAL_ID                        */
/*==============================================================*/
create index FIELD_DESCRIPTIONS_EXTERNAL_ID on FIELD_DESCRIPTIONS (
   EXTERNAL_ID ASC
)
/

/*==============================================================*/
/* Table: GROUPS_RISK                                           */
/*==============================================================*/
create table GROUPS_RISK  (
   ID                   integer                         not null,
   NAME                 varchar2(100)                   not null,
   IS_DEFAULT           char(1)                        default '0',
   constraint PK_ID primary key (ID),
   constraint UNIQUE_NAME unique (NAME)
)
/

create sequence S_GROUPS_RISK
/

/*==============================================================*/
/* Table: PAYMENT_SERVICES                                      */
/*==============================================================*/
create table PAYMENT_SERVICES  (
   ID                   integer                         not null,
   CODE                 varchar2(50)                    not null,
   NAME                 nvarchar2(128)                  not null,
   PARENT_ID            integer,
   IMAGE_ID             integer,
   POPULAR              char(1)                         not null,
   DESCRIPTION          varchar2(512),
   SYSTEM               char(1)                         not null,
   PRIORITY             integer,
   VISIBLE_IN_SYSTEM    char(1)                         not null,
   IMAGE_NAME           varchar2(128)                   not null,
   constraint PK_PAYMENT_SERVICES primary key (ID)
)
/

create sequence S_PAYMENT_SERVICES
/

/*==============================================================*/
/* Index: INDEX_CODE_1                                          */
/*==============================================================*/
create unique index INDEX_CODE_1 on PAYMENT_SERVICES (
   CODE ASC
)
/

/*==============================================================*/
/* Table: PAYMENT_SERVICE_CATEGORIES                            */
/*==============================================================*/
create table PAYMENT_SERVICE_CATEGORIES  (
   PAYMENT_SERVICES_ID  integer                         not null,
   CATEGORY             varchar2(64)                    not null,
   constraint PK_PAYMENT_SERVICE_CATEGORIES primary key (PAYMENT_SERVICES_ID, CATEGORY)
)
/

create sequence S_PAYMENT_SERVICE_CATEGORIES
/

/*==============================================================*/
/* Table: REGIONS                                               */
/*==============================================================*/
create table REGIONS  (
   ID                   integer                         not null,
   CODE                 varchar2(20)                    not null,
   NAME                 nvarchar2(128)                  not null,
   CODE_TB              varchar2(2),
   PARENT_ID            integer,
   constraint PK_REGIONS primary key (ID)
)
/

create sequence S_REGIONS
/

/*==============================================================*/
/* Index: INDEX_CODE_2                                          */
/*==============================================================*/
create unique index INDEX_CODE_2 on REGIONS (
   CODE ASC
)
/

/*==============================================================*/
/* Table: SERVICE_PROVIDERS                                     */
/*==============================================================*/
create table SERVICE_PROVIDERS  (
   ID                   integer                         not null,
   EXTERNAL_ID          varchar2(128)                   not null,
   CODE                 varchar2(20)                    not null,
   CODE_RECIPIENT_SBOL  varchar2(32),
   NAME                 varchar2(160)                   not null,
   DESCRIPTION          varchar2(512),
   ALIAS                varchar2(250),
   LEGAL_NAME           varchar2(250),
   NAME_ON_BILL         varchar2(250),
   INN                  varchar2(12),
   KPP                  varchar2(9),
   ACCOUNT              varchar2(25),
   BANK_CODE            varchar2(9),
   BANK_NAME            varchar2(128),
   CORR_ACCOUNT         varchar2(25),
   BILLING_ID           integer,
   CODE_SERVICE         varchar2(50),
   NAME_SERVICE         varchar2(150),
   IS_DEPT_AVAILABLE    char(1)                        default '0' not null,
   IS_FEDERAL           char(1)                        default '0' not null,
   NOT_VISIBLE_IN_HIERARCHY char(1)                    default '0' not null,
   MAX_COMISSION_AMOUNT number(15,4),
   MIN_COMISSION_AMOUNT number(15,4),
   COMISSION_RATE       number(15,4),
   DEPARTMENT_ID        integer,
   TRANSIT_ACCOUNT      varchar2(25),
   ATTR_DELIMITER       char(1),
   ATTR_VALUES_DELIMITER char(1),
   NSI_CODE             varchar2(128),
   STATE                varchar2(16)                               not null,
   IS_GROUND            char(1)                        default '0' not null,
   IMAGE_ID             integer,
   IS_POPULAR           char(1)                        default '0' not null,
   IS_PROPS_ONLINE      char(1)                        default '0' not null,
   IS_BANK_DETAILS      char(1)                        default '0' not null,
   ACCOUNT_TYPE         varchar2(16),
   IS_MOBILEBANK        char(1)                        default '0' not null,
   MOBILEBANK_CODE      varchar2(32),
   IS_FULL_PAYMENT      char(1)                        default '0' not null,
   PAYMENT_TYPE         varchar2(20),
   KIND                 char(1)                                    not null,
   IS_ALLOW_PAYMENTS    char(1)                        default '1' not null,
   PHONE_NUMBER         varchar2(15),
   CREATION_DATE        timestamp                                  not null,
   MOBILE_SERVICE       char(1)                        default '0' not null,
   IS_AUTOPAYMENT_SUPPORTED char(1)                    default '0' not null,
   IS_THRESHOSD_AUTOPAY_SUPPORTED char(1),
   MIN_SUMMA_THRESHOLD  number(15,4),
   MAX_SUMMA_THRESHOLD  number(15,4),
   IS_INTERVAL_THRESHOLD char(1),
   MIN_VALUE_THRESHOLD  number(15,4),
   MAX_VALUE_THRESHOLD  number(15,4),
   DISCRETE_VALUE_THRESHOLD varchar2(256),
   CLIENT_HINT_THRESHOLD varchar2(500),
   IS_ALWAYS_AUTOPAY_SUPPORTED char(1),
   MIN_SUMMA_ALWAYS     number(15,4),
   MAX_SUMMA_ALWAYS     number(15,4),
   CLIENT_HINT_ALWAYS   varchar2(500),
   IS_INVOICE_AUTOPAY_SUPPORTED char(1),
   CLIENT_HINT_INVOICE  varchar2(500),
   URL                  varchar2(256),
   TIP_OF_PROVIDER      varchar2(255),
   BACK_URL             varchar2(255),
   AFTER_ACTION         char(1)                        default '0' not null,
   CHECK_ORDER          char(1)                        default '0' not null,
   STANDART_SMS         char(1)                        default '1' not null,
   SMS_FORMAT           varchar2(255),
   SMS_EXAMPLE          varchar2(255),
   IS_BANKOMAT_SUPPORTED char(1)                       default '0' not null,
   VERSION_API          integer,
   MIN_SUM_RESTRICTION  number(15,4),
   MAX_SUM_RESTRICTION  number(15,4),
   COMMISSION_MESSAGE   varchar(250),
   FORM_NAME            varchar2(35),
   SEND_CHARGE_OFF_INFO char(1)                        default '0' not null,
   PAYMENTCOUNT         integer,
   IS_TEMPLATE_SUPPORTED char(1)                       default '1' not null,
   ATM_AVAILABLE        char(1)                        default '0' not null,
   SORT_PRIORITY        integer                                    not null,
   IS_BAR_SUPPORTED     char(1)                        default '0' not null,
   GROUP_RISK_ID        integer,
   IMAGE_HELP_ID        integer,
   IS_OFFLINE_AVAILABLE char(1)                        default '0' not null,
   constraint PK_SERVICE_PROVIDERS primary key (ID),
   constraint UN_CODE_S_CODE_R unique (CODE, CODE_SERVICE, BILLING_ID),
   constraint UN_CODE_S_CODE_R_SBOL unique (CODE_SERVICE, CODE_RECIPIENT_SBOL, BILLING_ID)
)
/

create sequence S_SERVICE_PROVIDERS
/

/*==============================================================*/
/* Index: DXFK_S_PROVIDERS_HELP_TO_IMAGE                        */
/*==============================================================*/
create index DXFK_S_PROVIDERS_HELP_TO_IMAGE on SERVICE_PROVIDERS (
   IMAGE_HELP_ID ASC
)
/

/*==============================================================*/
/* Index: DXFK_S_PROVIDERS_TO_BILLINGS                          */
/*==============================================================*/
create index DXFK_S_PROVIDERS_TO_BILLINGS on SERVICE_PROVIDERS (
   BILLING_ID ASC
)
/

/*==============================================================*/
/* Index: DXFK_S_PROVIDERS_TO_DEPARTMENT                        */
/*==============================================================*/
create index DXFK_S_PROVIDERS_TO_DEPARTMENT on SERVICE_PROVIDERS (
   DEPARTMENT_ID ASC
)
/

/*==============================================================*/
/* Index: IDX_ALLOWP                                            */
/*==============================================================*/
create index IDX_ALLOWP on SERVICE_PROVIDERS (
   IS_ALLOW_PAYMENTS ASC
)
/

/*==============================================================*/
/* Index: IDX_PROVIDERS_MOBILE_SERVICE                          */
/*==============================================================*/
create index IDX_PROVIDERS_MOBILE_SERVICE on SERVICE_PROVIDERS (
   MOBILE_SERVICE ASC
)
/

/*==============================================================*/
/* Index: IDX_PROVIDER_KIND                                     */
/*==============================================================*/
create index IDX_PROVIDER_KIND on SERVICE_PROVIDERS (
   KIND ASC
)
/

/*==============================================================*/
/* Index: IDX_SERVICE_PROVIDER_STATE                            */
/*==============================================================*/
create index IDX_SERVICE_PROVIDER_STATE on SERVICE_PROVIDERS (
   STATE ASC
)
/

/*==============================================================*/
/* Index: INDEX_EXT_ID                                          */
/*==============================================================*/
create unique index INDEX_EXT_ID on SERVICE_PROVIDERS (
   EXTERNAL_ID ASC
)
/

/*==============================================================*/
/* Index: SERV_PROV_PAYM_COUNT                                  */
/*==============================================================*/
create index SERV_PROV_PAYM_COUNT on SERVICE_PROVIDERS (
   PAYMENTCOUNT DESC
)
/

/*==============================================================*/
/* Table: SERVICE_PROVIDER_REGIONS                              */
/*==============================================================*/
create table SERVICE_PROVIDER_REGIONS  (
   REGION_ID            INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   constraint PK_SERVICE_PROVIDER_REGIONS primary key (REGION_ID, SERVICE_PROVIDER_ID)
)
/

create sequence S_SERVICE_PROVIDER_REGIONS
/

/*==============================================================*/
/* Table: SERV_PROVIDER_PAYMENT_SERV                            */
/*==============================================================*/
create table SERV_PROVIDER_PAYMENT_SERV  (
   PAYMENT_SERVICE_ID   INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROVIDER_PAYMENT_SERV primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)
/

create sequence S_SERV_PROVIDER_PAYMENT_SERV
/

create index "DXFIELD_DESC_TO_PROVIDERS" on FIELD_DESCRIPTIONS
(
   RECIPIENT_ID
)
/

alter table FIELD_DESCRIPTIONS
   add constraint FIELD_DESC_TO_PROVIDERS foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/


create index "DXFK_P_SERVICE_TO_P_SERVICE" on PAYMENT_SERVICES
(
   PARENT_ID
)
/

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_P_SERVICE foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
/


create index "DXFK_CATEGORY_TO_P_SERV" on PAYMENT_SERVICE_CATEGORIES
(
   PAYMENT_SERVICES_ID
)
/

alter table PAYMENT_SERVICE_CATEGORIES
   add constraint FK_CATEGORY_TO_P_SERV foreign key (PAYMENT_SERVICES_ID)
      references PAYMENT_SERVICES (ID)
/

create index "DXFK_REGION_TO_REGION" on REGIONS
(
   PARENT_ID
)
/

alter table REGIONS
   add constraint FK_REGION_TO_REGION foreign key (PARENT_ID)
      references REGIONS (ID)
/

create index "DXFK_S_PROVIDERS_TO_GROUPS_RIS" on SERVICE_PROVIDERS
(
   GROUP_RISK_ID
)
/

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
/

create index "DXFK_PROV_REG_TO_PROV" on SERVICE_PROVIDER_REGIONS
(
   SERVICE_PROVIDER_ID
)
/

alter table SERVICE_PROVIDER_REGIONS
   add constraint FK_PROV_REG_TO_PROV foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/


create index "DXFK_PROV_REG_TO_REG" on SERVICE_PROVIDER_REGIONS
(
   REGION_ID
)
/

alter table SERVICE_PROVIDER_REGIONS
   add constraint FK_PROV_REG_TO_REG foreign key (REGION_ID)
      references REGIONS (ID)
/


create index "DXFK_PROV_PAY_SER_TO_PAY" on SERV_PROVIDER_PAYMENT_SERV
(
   PAYMENT_SERVICE_ID
)
/

alter table SERV_PROVIDER_PAYMENT_SERV
   add constraint FK_PROV_PAY_SER_TO_PAY foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES (ID)
/


create index "DXFK_PROV_PAY_SER_TO_PROV" on SERV_PROVIDER_PAYMENT_SERV
(
   SERVICE_PROVIDER_ID
)
/

alter table SERV_PROVIDER_PAYMENT_SERV
   add constraint FK_PROV_PAY_SER_TO_PROV foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/

