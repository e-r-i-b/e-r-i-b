--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 54522
-- Комментарий: доработка отправки уведомлений
create table PUSH_PARAMS 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR(20)          not null,
   KEY                  VARCHAR(255)         not null,
   TEXT                 CLOB                 not null,
   SHORT_TEXT           VARCHAR(255)         not null,
   DESCRIPTION          VARCHAR(255),
   PRIORITY             INTEGER,
   CODE                 NUMBER(2)            not null,
   PRIVACY_TYPE         VARCHAR(20)          not null,
   PUBLICITY_TYPE       VARCHAR(20)          not null,
   SMS_BACKUP           CHAR(1)              not null,
   VARIABLES            VARCHAR(4000),
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   constraint PK_PUSH_PARAMS primary key (ID) using index (
      create unique index PK_PUSH_PARAMS on PUSH_PARAMS(ID) tablespace INDX  
   )     
)
/

create sequence S_PUSH_PARAMS
/

create unique index I_PUSH_PARAMS_KEY on PUSH_PARAMS (
   KEY ASC
) tablespace INDX 
/

-- Номер ревизии: 51023
-- Комментарий: Добавление таблицы отложенных команд
create table ERMB_DELAYED_COMMANDS 
(
   ID                   INTEGER              not null,
   USER_ID              INTEGER              not null,
   COMMAND_CLASS        VARCHAR2(256)        not null,
   COMMAND_BODY         VARCHAR2(1024)       not null,
   constraint PK_ERMB_DELAYED_COMMANDS primary key (ID) using index (
      create unique index PK_ERMB_DELAYED_COMMANDS on ERMB_DELAYED_COMMANDS(ID) tablespace INDX  
   )   
)
/

create sequence S_ERMB_DELAYED_COMMANDS
/

create index "DXFK_DELAYED_TO_USERS" on ERMB_DELAYED_COMMANDS
(
   USER_ID
) tablespace INDX 
/

alter table ERMB_DELAYED_COMMANDS
   add constraint FK_DELAYED_TO_USERS foreign key (USER_ID)
      references USERS (ID)
      on delete cascade
/

-- Номер ревизии: 53694
-- Комментарий: Сберегательные сертификаты(ч.1)
create table SECURITY_ACCOUNT_LINKS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(100)        not null,
   LOGIN_ID             INTEGER              not null,
   SERIAL_NUMBER        VARCHAR2(64)         not null,
   SECURITY_NAME        VARCHAR2(100)        not null,
   SHOW_IN_SYSTEM       CHAR(1)              not null,
   ON_STORAGE_IN_BANK   CHAR(1)              not null,
   constraint PK_SECURITY_ACCOUNT_LINKS primary key (ID) using index (
      create unique index PK_SECURITY_ACCOUNT_LINKS on SECURITY_ACCOUNT_LINKS(ID) tablespace INDX  
   )      
)
/

create sequence S_SECURITY_ACCOUNT_LINKS
/

create unique index UNIQ_SEC_NUMBER on SECURITY_ACCOUNT_LINKS (
   LOGIN_ID ASC,
   SERIAL_NUMBER ASC
) tablespace INDX 
/

-- Номер ревизии: 51812
-- Комментарий: Доработка репликации подразделений : Создание формы создание фоновой задачи, списка фоновых задач для репликации
create table DEPARTMENTS_REPLICA_TASKS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE_CODE           VARCHAR2(20)         not null,
   REPLICATION_MODE     VARCHAR2(10)         not null,
   REPORT_START_DATE    TIMESTAMP,
   REPORT_END_DATE      TIMESTAMP,
   DEPARTMENTS          VARCHAR2(3000),
   DETAILED_REPORT      CLOB,
   SOURCE_ERRORS        NUMBER(22,0),
   SOURCE_SUCCESS       NUMBER(22,0),
   DEST_INSERED         NUMBER(22,0),
   DEST_UPDATED         NUMBER(22,0),
   TOTAL_RECORDS        NUMBER(22,0),
   DEST_INSERED_REPORT  CLOB,
   DEST_INSERED_DECENTR_REPORT CLOB,
   DEST_UPDATED_REPORT  CLOB,
   ERROR_FORMAT_REPORT  CLOB,
   ERROR_PARENT_REPORT  CLOB,
   constraint PK_DEPARTMENTS_REPLICA_TASKS primary key (ID) using index (
      create unique index PK_DEPARTMENTS_REPLICA_TASKS on DEPARTMENTS_REPLICA_TASKS(ID) tablespace INDX  
   )      
)
/

create sequence S_DEPARTMENTS_REPLICA_TASKS
/

create index "DXFK_DEPARTMENTS_REPL_LOGINS" on DEPARTMENTS_REPLICA_TASKS
(
   LOGIN_ID
) tablespace INDX 
/

alter table DEPARTMENTS_REPLICA_TASKS
   add constraint FK_DEPARTMENTS_REPL_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

-- Номер ревизии: 53943 
-- Комментарий: Доработка репликации подразделений(Реализация алгоритма репликации)
create table DEPARTMENTS_TASKS_CONTENT 
(
   REPLICA_TASKS_ID     INTEGER              not null,
   CONTENT              BLOB,
   constraint PK_DEPARTMENTS_TASKS_CONTENT primary key (REPLICA_TASKS_ID) using index (
      create unique index PK_DEPARTMENTS_TASKS_CONTENT on DEPARTMENTS_TASKS_CONTENT(REPLICA_TASKS_ID) tablespace INDX  
   ) 
)
/
alter table DEPARTMENTS_TASKS_CONTENT
   add constraint FK_CONTENT_TO_DEP_TASKS foreign key (REPLICA_TASKS_ID)
      references DEPARTMENTS_REPLICA_TASKS (ID)
      on delete cascade
/

-- Номер ревизии: 54044 
-- Комментарий: Печать реквизитов банка для вкладов (модель БД)
create table TB_DETAILS 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(4)          not null,
   NAME                 VARCHAR2(256)        not null,
   BIC                  VARCHAR2(16),
   OKPO                 VARCHAR2(16),
   ADDRESS              VARCHAR2(256),
   constraint PK_TB_DETAILS primary key (ID) using index (
      create unique index PK_TB_DETAILS on TB_DETAILS(ID) tablespace INDX  
   )    
)
/

create sequence S_TB_DETAILS
/

-- Номер ревизии: 54209
-- Комментарий: Сберегательные сертификаты(тех. перерывы)
create table STORED_SECURITY_ACCOUNT 
(
   ID                   INTEGER              not null,
   RESOURCE_ID          INTEGER              not null,
   BANK_ID              VARCHAR2(32),
   BANK_NAME            VARCHAR2(300),
   BANK_ADDR            VARCHAR2(500),
   ISS_BANK_ID          VARCHAR2(32),
   ISS_BANK_NAME        VARCHAR2(300),
   COMPOSE_DATE         TIMESTAMP,
   DOC_NUMBER           VARCHAR2(100),
   DOC_DATE             TIMESTAMP,
   INCOME_RATE          NUMBER(15,4),
   START_DATE           TIMESTAMP,
   ENTITY_UPDATE_TIME   TIMESTAMP,
   NOMINAL_AMOUNT       NUMBER(15,4),
   NOMINAL_CURRENCY     CHAR(3),
   INCOME_AMOUNT        NUMBER(15,4),
   INCOME_CURRENCY      CHAR(3),
   constraint PK_STORED_SECURITY_ACCOUNT primary key (ID) using index (
      create unique index PK_STORED_SECURITY_ACCOUNT on STORED_SECURITY_ACCOUNT(ID) tablespace INDX  
   )    
)
/

create sequence S_STORED_SECURITY_ACCOUNT
/

create index "DXSTORED_S_A_TO_LINK" on STORED_SECURITY_ACCOUNT
(
   RESOURCE_ID
) tablespace INDX 
/

alter table STORED_SECURITY_ACCOUNT
   add constraint FK_STORED_S_SECURITY foreign key (RESOURCE_ID)
      references SECURITY_ACCOUNT_LINKS (ID)
      on delete cascade
/

-- Номер ревизии: 52767
-- Комментарий: Добавление таблицы Регистрация клиентов ЕРМБ
create table ERMB_REGISTRATION 
(
   ID                   INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(20)         not null,
   CONNECTION_DATE      TIMESTAMP            not null,
   PROFILE_ID           INTEGER              not null,
   constraint PK_ERMB_REGISTRATION primary key (ID) using index (
      create unique index PK_ERMB_REGISTRATION on ERMB_REGISTRATION(ID) tablespace INDX  
   )     
)
/

create sequence S_ERMB_REGISTRATION
/
create index IDX_ERMB_REGISTRATION on ERMB_REGISTRATION (
   PHONE_NUMBER ASC
) tablespace INDX 
/

-- Номер ревизии: 54365
-- Комментарий: Форма редактирования и списка площадок КЦ
create table CONTACT_CENTER_AREAS 
(
   ID                   INTEGER              not null,
   AREA_NAME            VARCHAR2(50)         not null,
   constraint PK_CONTACT_CENTER_AREAS primary key (ID) using index (
      create unique index PK_CONTACT_CENTER_AREAS on CONTACT_CENTER_AREAS(ID) tablespace INDX  
   )    
)
/

create sequence S_CONTACT_CENTER_AREAS
/

create unique index I_CONTACT_CENTER_AREAS_NAME on CONTACT_CENTER_AREAS (
   AREA_NAME ASC
) tablespace INDX 
/

create table C_CENTER_AREAS_DEPARTMENTS 
(
   C_C_AREA_ID          INTEGER              not null,
   DEPARTMENT_ID        INTEGER              not null,
   constraint PK_C_CENTER_AREAS_DEPARTMENTS primary key (C_C_AREA_ID, DEPARTMENT_ID) using index (
      create unique index PK_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS(C_C_AREA_ID, DEPARTMENT_ID) tablespace INDX  
   )      
)
/

create sequence S_C_CENTER_AREAS_DEPARTMENTS
/

create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   DEPARTMENT_ID ASC
) tablespace INDX 
/

create index "DXFK_C_C_AREA_DEP_TO_C_C_AREA" on C_CENTER_AREAS_DEPARTMENTS
(
   C_C_AREA_ID
) tablespace INDX 
/

alter table C_CENTER_AREAS_DEPARTMENTS
   add constraint FK_C_C_AREA_DEP_TO_C_C_AREA foreign key (C_C_AREA_ID)
      references CONTACT_CENTER_AREAS (ID)
      on delete cascade
/

alter table C_CENTER_AREAS_DEPARTMENTS
   add constraint FK_C_C_AREA_DEP_TO_DEP foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete cascade
/

-- Номер ревизии: 54471
-- Комментарий: ENH060229: Привести страницы со списками шаблонов и автоплатежей к макетам
create table AUTOPAYMENT_LINKS_ORDER 
(
   ID                   INTEGER              not null,
   LINK_ID              VARCHAR2(130)        not null,
   LOGIN_ID             INTEGER              not null,
   ORDER_IND            INTEGER              not null,
   constraint PK_AUTOPAYMENT_LINKS_ORDER primary key (ID) using index (
      create unique index PK_AUTOPAYMENT_LINKS_ORDER on AUTOPAYMENT_LINKS_ORDER(ID) tablespace INDX  
   )    
)
/
create sequence S_AUTOPAYMENT_LINKS_ORDER
/

create index "DXFK_AUTOPAYMENT_ORDER_TO_LOGI" on AUTOPAYMENT_LINKS_ORDER
(
   LOGIN_ID
) tablespace INDX 
/

alter table AUTOPAYMENT_LINKS_ORDER
   add constraint FK_AUTOPAYM_FK_AUTOPA_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

-- Номер ревизии: 54547
-- Комментарий: Переназначение обращений на других сотрудников
create table REASSIGN_MAIL_HISTORY 
(
   ID                   INTEGER              not null,
   MAIL_ID              INTEGER              not null,
   REASSIGN_DATE        TIMESTAMP            not null,
   EMPLOYEE_FIO         VARCHAR2(92)         not null,
   REASSIGN_REASON      VARCHAR2(500)        not null,
   constraint PK_REASSIGN_MAIL_HISTORY primary key (ID) using index (
      create unique index PK_REASSIGN_MAIL_HISTORY on REASSIGN_MAIL_HISTORY(ID) tablespace INDX  
   ) 
)
/

create sequence S_REASSIGN_MAIL_HISTORY
/

create index "DXFK_R_MAIL_HISTORY_TO_MAIL" on REASSIGN_MAIL_HISTORY
(
   MAIL_ID
) tablespace INDX 
/

alter table REASSIGN_MAIL_HISTORY
   add constraint FK_R_MAIL_HISTORY_TO_MAIL foreign key (MAIL_ID)
      references MAIL (ID)
      on delete cascade
/

-- Номер ревизии: 54561
-- Комментарий: Создание таблицы для хранения email-шаблонов
create table EMAIL_RESOURCES 
(
   ID                   INTEGER              not null,
   KEY                   VARCHAR2(255)       not null,
   THEME                 VARCHAR2(255),
   DESCRIPTION           VARCHAR2(255),
   VARIABLES             VARCHAR2(4000),
   PLAIN_TEXT           CLOB,
   HTML_TEXT            CLOB,
   PREVIOUS_PLAIN_TEXT  CLOB,
   PREVIOUS_HTML_TEXT   CLOB,
   LAST_MODIFIED        TIMESTAMP,
   EMPLOYEE_LOGIN_ID    INTEGER,
   constraint PK_EMAIL_RESOURCES primary key (ID) using index (
      create unique index PK_EMAIL_RESOURCES on EMAIL_RESOURCES(ID) tablespace INDX  
   )    
)
/

create sequence S_EMAIL_RESOURCES
/

create index INDEX_EMAIL_KEY on EMAIL_RESOURCES (
   KEY ASC
) tablespace INDX 
/

/*==============================================================*/
/* Table: OFFICES_NOT_ISSUING_CARDS                             */
/*==============================================================*/
create table OFFICES_NOT_ISSUING_CARDS 
(
   ID                   INTEGER              not null,
   TB                   VARCHAR2(3)          not null,
   OSB                  VARCHAR2(4),
   OFFICE               VARCHAR2(7),
   constraint PK_OFFICES_NOT_ISSUING_CARDS primary key (ID) using index (
      create unique index PK_OFFICES_NOT_ISSUING_CARDS on OFFICES_NOT_ISSUING_CARDS(ID) tablespace INDX  
   )      
)
/

create sequence S_OFFICES_NOT_ISSUING_CARDS
/


/*==============================================================*/
/* Table: INCOGNITO_PHONES                                      */
/*==============================================================*/
create table INCOGNITO_PHONES 
(
   ID                   NUMBER(6)            not null,
   PHONE_NUMBER         VARCHAR2(20 BYTE)    not null,
   constraint PK_INCOGNITO_PHONES primary key (ID) using index (
      create unique index PK_INCOGNITO_PHONES on INCOGNITO_PHONES(ID) tablespace INDX  
   )       
)
/

create sequence S_INCOGNITO_PHONES
/


/*==============================================================*/
/* Table: DISTRIBUTION_CHANNEL                                  */
/*==============================================================*/
create table DISTRIBUTION_CHANNEL 
(
   ID                   INTEGER              not null,
   DISTRIBUTION_ID      INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   CHANNEL              VARCHAR2(20),
   constraint PK_DISTRIBUTION_CHANNEL primary key (ID) using index (
      create unique index PK_DISTRIBUTION_CHANNEL on DISTRIBUTION_CHANNEL(ID) tablespace INDX  
   )    
)
/

create sequence S_DISTRIBUTION_CHANNEL
/

create unique index CHANNEL_UK on DISTRIBUTION_CHANNEL (
   DISTRIBUTION_ID ASC,
   CHANNEL ASC
) tablespace INDX 
/

/*==============================================================*/
/* Table: NEWS_DISTRIBUTIONS                                    */
/*==============================================================*/
create table NEWS_DISTRIBUTIONS 
(
   ID                   INTEGER              not null,
   EMPLOYEE_LOGIN_ID    INTEGER              not null,
   DATE_CREATED         TIMESTAMP            not null,
   TYPE                 VARCHAR2(32)         not null,
   NEWS_ID              INTEGER              not null,
   MAIL_COUNT           INTEGER              not null,
   TIMEOUT              INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 CLOB                 not null,
   TERBANKS             VARCHAR2(60)         not null,
   LAST_PROCESSED_ID    INTEGER,
   constraint PK_NEWS_DISTRIBUTIONS primary key (ID) using index (
      create unique index PK_NEWS_DISTRIBUTIONS on NEWS_DISTRIBUTIONS(ID) tablespace INDX  
   )    
)
/

create sequence S_NEWS_DISTRIBUTIONS
/

-- Номер ревизии: 55150
-- Комментарий: ЦСА Админ. Актуализация доступа к подразделениям в блоке, на основе данных из ЦСа Админ.
create global temporary table ALLOWED_DEPARTMENT_TEMPORARY 
(
   TB                   VARCHAR2(4)          not null,
   OSB                  VARCHAR2(4)          not null,
   VSP                  VARCHAR2(7)          not null
)
on commit delete rows


-- Номер ревизии: 55521
-- Комментарий: Новостная рассылка. Часть II
create table NEWS_SUBSCRIPTIONS(
   ID         INTEGER     not null,
   LOGIN_ID   INTEGER     not null,
   TB         VARCHAR2(2) not null
)
/

create sequence S_NEWS_SUBSCRIPTIONS
/

create index NEWS_SUBSCRIPTIONS_TB_INDEX on NEWS_SUBSCRIPTIONS (
   TB ASC
) tablespace INDX 
/

-- Номер ревизии: 55748
-- Комментарий: BUG064354: Неверно отображаются статусы интернет-заказов после конвертации
create table BUSINESS_DOCUMENTS_TO_ORDERS (
   BUSINESS_DOCUMENT_ID  integer      not null,
   ORDER_UUID            varchar2(32) not null
)
/