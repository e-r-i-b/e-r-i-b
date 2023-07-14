-- Последовательности

create sequence S_CARD_PRODUCTS
/
create sequence S_CAS_NSI_CARD_PRODUCT
/
create sequence S_INFORM_MESSAGES
/
create sequence S_MOBILE_OPERATORS
/
create sequence S_PAGES
/
create sequence S_PEREODICAL_TASK
/
create sequence S_PEREODICAL_TASK_ERROR
/
create sequence S_PEREODICAL_TASK_RESULT
/
create sequence S_PEREODICAL_TASK_TRIGGE
/
create sequence S_PROMO_CODE_SETTINGS
/
create sequence S_SYSTEM_IDLE_ADDIT_REPORT
/
create sequence S_TECHNOBREAKS
/
create sequence S_COUNT_IOS_USERS
/
create sequence S_EXTERNAL_SYSTEM_ROUTE_INFO
/

-- Новые таблицы

/*==============================================================*/
/* Table: CARD_PRODUCT_KINDS                                    */
/*==============================================================*/
create table CARD_PRODUCT_KINDS  (
   CARD_PRODUCT_ID      INTEGER                         not null,
   PRODUCT_KIND_ID      INTEGER                         not null,
   constraint PK_CARD_PRODUCT_KINDS primary key (CARD_PRODUCT_ID, PRODUCT_KIND_ID)
)
/

/*==============================================================*/
/* Table: INFORM_MESSAGES                                       */
/*==============================================================*/
create table INFORM_MESSAGES  (
   ID                   INTEGER                         not null,
   TEXT                 VARCHAR2(500)                   not null,
   STATE                VARCHAR2(32)                    not null,
   START_PUBLISH_DATE   TIMESTAMP                       not null,
   CANCEL_PUBLISH_DATE  TIMESTAMP,
   NAME                 VARCHAR2(40),
   VIEW_TYPE            VARCHAR2(32) default 'STATIC_MESSAGE' not null,
   IMPORTANCE           VARCHAR2(1)  default '2'              not null,
   constraint PK_INFORM_MESSAGES primary key (ID)
)
/

/*==============================================================*/
/* Table: MAIL_RECIPIENTS                                       */
/*==============================================================*/
create table MAIL_RECIPIENTS  (
   MAIL_ID              INTEGER                         not null,
   RECIPIENT_ID         INTEGER                         not null,
   constraint PK_MAIL_RECIPIENTS primary key (MAIL_ID, RECIPIENT_ID)
)
/

/*==============================================================*/
/* Table: EXTERNAL_SYSTEM                                       */
/*==============================================================*/
create table EXTERNAL_SYSTEM  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(50)                    not null,
   ADAPTER_CODE         VARCHAR2(64),
   INTERACTION          VARCHAR2(10)                    not null,
   TB_ID                INTEGER,
   SYSTEM_ID            VARCHAR2(64),
   constraint PK_EXTERNAL_SYSTEM primary key (ID)
)
/

/*==============================================================*/
/* Table: PAGE_PARAMETERS                                       */
/*==============================================================*/
create table PAGE_PARAMETERS  (
   ID                   INTEGER                         not null,
   KEY_PARAMETER        VARCHAR2(64)                    not null,
   VALUE_PARAMETER      VARCHAR2(64)                    not null,
   constraint PK_PAGE_PARAMETERS primary key (ID, KEY_PARAMETER)
)
/

/*==============================================================*/
/* Table: PEREODICAL_TASK                                       */
/*==============================================================*/
create table PEREODICAL_TASK  (
   ID                   NUMBER(22,0)                    not null,
   KIND                 CHAR(1)                         not null,
   LOGIN_ID             NUMBER(22,0)                    not null,
   CREATION_DATE        TIMESTAMP(6)                    not null,
   STATE_CODE           VARCHAR2(20)                    not null,
   TRIGGER_NAME         VARCHAR2(50)                    not null,
   OPERATION_NAME       VARCHAR2(100)                   not null,
   CRON_EXP             VARCHAR2(25),
   TIME_INTERVAL        INTEGER,
   UNLOAD_DIR           VARCHAR2(255),
   constraint PK_PEREODICAL_TASK primary key (ID),
   constraint UNIQUE_TRIGGER_NAME unique (TRIGGER_NAME)
)
/

/*==============================================================*/
/* Table: SYSTEM_IDLE_ADDIT_REPORT                              */
/*==============================================================*/
create table SYSTEM_IDLE_ADDIT_REPORT  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   KIND                 CHAR(1)                         not null,
   START_DATE           TIMESTAMP                       not null,
   END_DATE             TIMESTAMP                       not null,
   TYPE                 VARCHAR2(10)                    not null,
   constraint PK_SYSTEM_IDLE_ADDIT_REPORT primary key (ID)
)
/

/*==============================================================*/
/* Table: CAS_NSI_CARD_PRODUCT                                  */
/*==============================================================*/
create table CAS_NSI_CARD_PRODUCT  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(255)                   not null,
   PRODUCT_ID           INTEGER                         not null,
   PRODUCT_SUB_KIND     INTEGER                         not null,
   STOP_OPEN_DEPOSIT    TIMESTAMP                       not null,
   CURRENCY             CHAR(3)                         not null,
   LAST_UPDATE_DATE     TIMESTAMP                       not null,
   constraint PK_CAS_NSI_CARD_PRODUCT primary key (ID)
)
/

/*==============================================================*/
/* Table: MESSAGES_PAGES                                        */
/*==============================================================*/
create table MESSAGES_PAGES  (
   MESSAGE_ID           INTEGER                         not null,
   PAGE_ID              INTEGER                         not null,
   constraint PK_MESSAGES_PAGES primary key (MESSAGE_ID, PAGE_ID)
)
/

/*==============================================================*/
/* Table: MOBILE_OPERATORS                                      */
/*==============================================================*/
create table MOBILE_OPERATORS  (
   ID                   INTEGER                         not null,
   CODE                 INTEGER                         not null,
   NAME                 VARCHAR2(255)                   not null,
   constraint PK_MOBILE_OPERATORS primary key (ID)
)
/

/*==============================================================*/
/* Table: PROMO_CODE_SETTINGS                                   */
/*==============================================================*/
create table PROMO_CODE_SETTINGS  (
   ID                   INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   START_DATE           TIMESTAMP                       not null,
   END_DATE             TIMESTAMP,
   constraint PK_PROMO_CODE_SETTINGS primary key (ID),
   constraint UK_TBID_PRMCODE_SETTINGS unique (TB_ID)
)
/

/*==============================================================*/
/* Table: CARD_PRODUCTS                                         */
/*==============================================================*/
create table CARD_PRODUCTS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(255),
   TYPE                 INTEGER,
   IS_ONLINE            CHAR(1),
   STOP_OPEN_DATE       TIMESTAMP,
   constraint PK_CARD_PRODUCTS primary key (ID)
)
/

/*==============================================================*/
/* Table: MESSAGES_DEPARTMENTS                                  */
/*==============================================================*/
create table MESSAGES_DEPARTMENTS  (
   MESSAGE_ID           INTEGER                         not null,
   DEPARTMENT_ID        INTEGER                         not null,
   constraint PK_MESSAGES_DEPARTMENTS primary key (MESSAGE_ID, DEPARTMENT_ID)
)
/

/*==============================================================*/
/* Table: TECHNOBREAKS                                          */
/*==============================================================*/
create table TECHNOBREAKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_SYSTEM_ID   INTEGER                         not null,
   FROM_DATE            TIMESTAMP                       not null,
   TO_DATE              TIMESTAMP                       not null,
   PERIODIC             VARCHAR2(15)                    not null,
   IS_DEFAULT_MESSAGE   CHAR(1)                        default '1' not null,
   MESSAGE              VARCHAR2(200)                   not null,
   STATUS               VARCHAR2(10)                    not null,
   constraint PK_TECHNOBREAKS primary key (ID)
)
/

/*==============================================================*/
/* Table: PEREODICAL_TASK_ERROR                                 */
/*==============================================================*/
create table PEREODICAL_TASK_ERROR  (
   ID                   NUMBER(22,0)                         not null,
   RESULT_ID            NUMBER(22,0)                         not null,
   ERR_TEXT             VARCHAR2(255)                   not null,
   constraint PK_PEREODICAL_TASK_ERROR primary key (ID)
)
/

/*==============================================================*/
/* Table: PEREODICAL_TASK_RESULT                                */
/*==============================================================*/
create table PEREODICAL_TASK_RESULT  (
   ID                   NUMBER(22,0)                         not null,
   TASK_ID              NUMBER(22,0)                         not null,
   SUCCESS_COUNT        NUMBER(22,0)                        default 0 not null,
   TOTAL_COUNT          NUMBER(22,0)                        default 0 not null,
   START_DATE           TIMESTAMP(6)                       not null,
   END_DATE             TIMESTAMP(6),
   constraint PK_PEREODICAL_TASK_RESULT primary key (ID)
)
/

/*==============================================================*/
/* Table: PAGES                                                 */
/*==============================================================*/
create table PAGES  (
   ID                   INTEGER                         not null,
   PAGE_KEY             VARCHAR2(64)                    not null,
   PAGE_NAME            VARCHAR2(128),
   PAGE_URL             VARCHAR2(128),
   PARENT               INTEGER,
   ORDER_NUMBER         INTEGER,
   constraint PK_PAGES primary key (ID)
)
/

/*==============================================================*/
/* Table: COUNT_IOS_USERS                                       */
/*==============================================================*/
create table COUNT_IOS_USERS  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   OSB_NAME             VARCHAR2(256),
   VSP_NAME             VARCHAR2(256),
   TOTAL_COUNT          INTEGER                         not null,
   LAST_COUNT           INTEGER                         not null,
   constraint PK_COUNT_IOS_USERS primary key (ID)
)
/

/*==============================================================*/
/* Table: EXTERNAL_SYSTEM_ROUTE_INFO                                       */
/*==============================================================*/
create table EXTERNAL_SYSTEM_ROUTE_INFO (
	ID integer                         not null,
	SYSTEM_ID varchar(64)              not null,
	PRODUCT_TYPE varchar(32)           not null,
	TB_CODE varchar(2)                 not null
)
/

--Констрейнты и индексы новых таблиц

/*==============================================================*/
/* Table: CARD_PRODUCT_KINDS                                    */
/*==============================================================*/
create index "DXFK_KIND_TO_PRODUCT_KIND" on CARD_PRODUCT_KINDS
(
   PRODUCT_KIND_ID
)
/

alter table CARD_PRODUCT_KINDS
   add constraint FK_KIND_TO_PRODUCT_KIND foreign key (PRODUCT_KIND_ID)
      references CAS_NSI_CARD_PRODUCT (ID)
/


create index "DXFK_PRODUCT_TO_PRODUCT_KIND" on CARD_PRODUCT_KINDS
(
   CARD_PRODUCT_ID
)
/

alter table CARD_PRODUCT_KINDS
   add constraint FK_PRODUCT_TO_PRODUCT_KIND foreign key (CARD_PRODUCT_ID)
      references CARD_PRODUCTS (ID)
/

/*==============================================================*/
/* Table: MAIL_RECIPIENTS                                       */
/*==============================================================*/
create index "DXFK_MAIL_RECIPIENTS_TO_MAIL" on MAIL_RECIPIENTS
(
   MAIL_ID
)
/
alter table MAIL_RECIPIENTS
   add constraint FK_MAIL_REC_FK_MAIL_R_MAIL foreign key (MAIL_ID)
      references MAIL (ID)
      on delete cascade
/
create index "DXFK_MAIL_REC_TO_RECIPIENTS" on MAIL_RECIPIENTS
(
   RECIPIENT_ID
)
/
alter table MAIL_RECIPIENTS
   add constraint FK_MAIL_REC_FK_MAIL_R_RECIPIEN foreign key (RECIPIENT_ID)
      references RECIPIENTS (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: EXTERNAL_SYSTEM                                       */
/*==============================================================*/
create unique index UK_NAME on EXTERNAL_SYSTEM (
   NAME ASC
)
/
create unique index UK_ADAPTER_CODE on EXTERNAL_SYSTEM (
   ADAPTER_CODE ASC
)
/
create unique index UK_SYSTEM_ID on EXTERNAL_SYSTEM (
   SYSTEM_ID ASC
)
/
create index "DXFK_EXT_SYS_TO_DEPARTMENTS" on EXTERNAL_SYSTEM
(
   TB_ID
)
/

alter table EXTERNAL_SYSTEM
   add constraint FK_EXT_SYS_TO_DEPARTMENTS foreign key (TB_ID)
      references DEPARTMENTS (ID)
/

/*==============================================================*/
/* Table: PAGE_PARAMETERS                                       */
/*==============================================================*/

-- нет индексов или ограничений

/*==============================================================*/
/* Table: PEREODICAL_TASK                                       */
/*==============================================================*/
create index "DXFK_LOGIN_TO_PEREODICAL_TASK" on PEREODICAL_TASK
(
   LOGIN_ID
)
/
alter table PEREODICAL_TASK
   add constraint FK_PEREODIC_FK_LOGIN__LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

/*==============================================================*/
/* Table: SYSTEM_IDLE_ADDIT_REPORT                              */
/*==============================================================*/
create index IDLE_ADD_REP_I_START_DATE on SYSTEM_IDLE_ADDIT_REPORT (
   START_DATE ASC
)
/
create index IDLE_ADD_REP_I_END_DATE on SYSTEM_IDLE_ADDIT_REPORT (
   END_DATE ASC
)
/
create index "DXFK_SYSTEM_IDLE_ADDIT_REPORT" on SYSTEM_IDLE_ADDIT_REPORT
(
   REPORT_ID
)
/
alter table SYSTEM_IDLE_ADDIT_REPORT
   add constraint FK_SYSTEM_IDLE_ADDIT_REPORT foreign key (REPORT_ID)
      references REPORTS (ID)
/

/*==============================================================*/
/* Table: CAS_NSI_CARD_PRODUCT                                  */
/*==============================================================*/
-- нет индексов или ограничений

/*==============================================================*/
/* Table: MESSAGES_PAGES                                        */
/*==============================================================*/
create index "DXFK_MESSAGES_PAGES_TO_MESSAGE" on MESSAGES_PAGES
(
   MESSAGE_ID
)
/
alter table MESSAGES_PAGES
   add constraint FK_MESSAGES_PAGES_TO_MESSAGES foreign key (MESSAGE_ID)
      references INFORM_MESSAGES (ID)
      on delete cascade
/
create index "DXFK_MESSAGES_PAGES_TO_PAGES" on MESSAGES_PAGES
(
   PAGE_ID
)
/
alter table MESSAGES_PAGES
   add constraint FK_MESSAGES_PAGES_TO_PAGES foreign key (PAGE_ID)
      references PAGES (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: MOBILE_OPERATORS                                      */
/*==============================================================*/
create index INDEX_MOBILE_OPERATORS_NAME on MOBILE_OPERATORS (
   NAME ASC
)
/

/*==============================================================*/
/* Table: PROMO_CODE_SETTINGS                                   */
/*==============================================================*/
create index "DXFK_PRMSETTINGS_TO_DPRTMNTS" on PROMO_CODE_SETTINGS
(
   TB_ID
)
/
alter table PROMO_CODE_SETTINGS
   add constraint FK_PRMSETTINGS_TO_DPRTMNTS foreign key (TB_ID)
      references DEPARTMENTS (ID)
/
    
/*==============================================================*/
/* Table: CARD_PRODUCTS                                         */
/*==============================================================*/
-- нет индексов или ограничений

/*==============================================================*/
/* Table: MESSAGES_DEPARTMENTS                                  */
/*==============================================================*/
create index "DXFK_MESSAGES_DEP_TO_DEPARTMEN" on MESSAGES_DEPARTMENTS
(
   DEPARTMENT_ID
)
/

alter table MESSAGES_DEPARTMENTS
   add constraint FK_MESSAGES_DEP_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete cascade
/


create index "DXINFORM_MESSAGES" on MESSAGES_DEPARTMENTS
(
   MESSAGE_ID
)
/

alter table MESSAGES_DEPARTMENTS
   add constraint FK_MESSAGES_DEP_TO_MESSAGES foreign key (MESSAGE_ID)
      references INFORM_MESSAGES (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: TECHNOBREAKS                                          */
/*==============================================================*/
create index "DXFK_TECHNOBREAKS_TO_EXT_SYS" on TECHNOBREAKS
(
   EXTERNAL_SYSTEM_ID
)
/

alter table TECHNOBREAKS
   add constraint FK_TECHNOBR_FK_TECHNO_EXTERNAL foreign key (EXTERNAL_SYSTEM_ID)
      references EXTERNAL_SYSTEM (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: PEREODICAL_TASK_ERROR                                 */
/*==============================================================*/
create index "DXFK_UT_RESULT_TO_UT_ERROR" on PEREODICAL_TASK_ERROR
(
   RESULT_ID
)
/

alter table PEREODICAL_TASK_ERROR
   add constraint FK_PEREODIC_FK_UT_RES_PEREODIC foreign key (RESULT_ID)
      references PEREODICAL_TASK_RESULT (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: PEREODICAL_TASK_RESULT                                */
/*==============================================================*/
create index "DXFK_PER_TASK_TO_UT_RESULT" on PEREODICAL_TASK_RESULT
(
   TASK_ID
)
/

alter table PEREODICAL_TASK_RESULT
   add constraint FK_PEREODIC_FK_PER_TA_PEREODIC foreign key (TASK_ID)
      references PEREODICAL_TASK (ID)
      on delete cascade
/

/*==============================================================*/
/* Table: PAGES                                                 */
/*==============================================================*/
create unique index INDEX_PAGE_KEY on PAGES (
   PAGE_KEY ASC
)
/

create index "DXFK_PAGES_TO_PAGES" on PAGES (
   PARENT
)
/

alter table PAGES
   add constraint FK_PAGES_TO_PAGES foreign key (PARENT)
      references PAGES (ID)
/

/*==============================================================*/
/* Table: COUNT_IOS_USERS                                       */
/*==============================================================*/
create index "DXFK_COUNT_IOS_REPORT" on COUNT_IOS_USERS
(
   REPORT_ID
)
/

alter table COUNT_IOS_USERS
   add constraint FK_COUNT_IO_FK_COUNT__REPORTS foreign key (REPORT_ID)
      references REPORTS (ID)
/

/*==============================================================*/
