-- Номер ревизии: 54795
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. Аутентификация пользователя. (Часть 7) Переход между блоками системы.
create table AUTHENTICATION_TOKEN 
(
   ID                   VARCHAR2(32)         not null,
   SESSION_ID           VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE                VARCHAR2(6)          not null,
   constraint PK_AUTHENTICATION_TOKEN primary key (ID)
)
go

create index "DXFK_AUTH_TOKEN_TO_SESSIONS" on AUTHENTICATION_TOKEN
(
   SESSION_ID
)
go

alter table AUTHENTICATION_TOKEN
   add constraint FK_AUTH_TOKEN_TO_SESSIONS foreign key (SESSION_ID)
      references SESSIONS (SID)
go


-- Номер ревизии: 54849
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. Аутентификация пользователя. (Часть 8) Дата обновления данных сотрудника.
ALTER TABLE LOGINS ADD LAST_UPDATE_DATE TIMESTAMP DEFAULT SYSDATE NOT NULL 
go


-- Номер ревизии: 54975
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. Актуализация модели БД.

create index INDEX_TYPE on ACCESSSCHEMES (
   TYPE ASC
)
go

DELETE FROM ALLOWED_DEPARTMENTS
go

ALTER TABLE ALLOWED_DEPARTMENTS MODIFY OSB NOT NULL
go

ALTER TABLE ALLOWED_DEPARTMENTS MODIFY VSP NOT NULL
go

create index I_ALLOWED_DEPARTMENTS_DEP on ALLOWED_DEPARTMENTS (
   TB || '|' || OSB || '|' || VSP ASC
)
go

ALTER TABLE EMPLOYEES MODIFY CA_ADMIN NOT NULL
go

ALTER TABLE EMPLOYEES MODIFY VSP_EMPLOYEE NOT NULL
go

ALTER TABLE LOGINS MODIFY ACCESSSCHEME_ID NULL
go


-- Номер ревизии: 55023
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. Поиск клиента в многоблочной структуре(часть 1).
alter table AUTHENTICATION_TOKEN add(ACTION VARCHAR2(256))
go

alter table AUTHENTICATION_TOKEN add(PARAMETERS CLOB)
go

create table OPERATION_CONTEXT 
(
   ID                   INTEGER              not null,
   SESSION_ID           VARCHAR2(32)         not null,
   STATE                VARCHAR2(6)          not null,
   PARAMETERS           CLOB                 not null,
   TYPE                 VARCHAR2(32)         not null,
   constraint PK_OPERATION_CONTEXT primary key (ID)
)

go

create sequence S_OPERATION_CONTEXT
go

create index "DXFK_OPER_CONTEXT_TO_SESSIONS" on OPERATION_CONTEXT
(
   SESSION_ID
)
go

alter table OPERATION_CONTEXT
   add constraint FK_OPERATIO_FK_OPER_C_SESSIONS foreign key (SESSION_ID)
      references SESSIONS (SID)
go

-- Номер ревизии: 55171 
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. улучшение плана

drop index I_ALLOWED_DEPARTMENTS
go

drop index I_ALLOWED_DEPARTMENTS_DEP
go

drop index "DXFK_A_DEPARTMENTS_TO_LOGINS"
go

drop index "DXFK_B_LOGIN_TO_LOGINS"
go

create unique index I_ALLOWED_DEPARTMENTS on ALLOWED_DEPARTMENTS (
   LOGIN_ID ASC,
   TB || '|' || OSB || '|' || VSP ASC
)
go

create index I_LOGIN_BLOCKS on LOGIN_BLOCK (
   LOGIN_ID ASC,
   DATE_FROM ASC,
   DATE_UNTIL ASC
)
go

create index I_EMPLOYEES_FIO on EMPLOYEES (
   upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', ''))
)
go


create index I_EMPLOYEES_DEPARTMENT on EMPLOYEES (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(VSP, 'NULL') ASC
)
go

-- Номер ревизии: 55395
-- Номер версии: 1.18
-- Комментарий: BUG063888: Исключение при удалении сотрудника (csaAdmin). 
alter table SESSIONS drop constraint FK_SESSIONS_TO_LOGINS
go

alter table OPERATION_CONTEXT drop constraint FK_OPERATIO_FK_OPER_C_SESSIONS
go

alter table AUTHENTICATION_TOKEN drop constraint FK_AUTH_TOKEN_TO_SESSIONS
go

alter table SESSIONS
   add constraint FK_SESSIONS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go

alter table OPERATION_CONTEXT
   add constraint FK_OPERATIO_FK_OPER_C_SESSIONS foreign key (SESSION_ID)
      references SESSIONS (SID)
      on delete cascade
go

alter table AUTHENTICATION_TOKEN
   add constraint FK_AUTH_TOKEN_TO_SESSIONS foreign key (SESSION_ID)
      references SESSIONS (SID)
      on delete cascade
go


-- Номер ревизии: 55410
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. Сохранение потенциального клиента в многоблочном режиме.
alter table OPERATION_CONTEXT drop column TYPE
go

alter table OPERATION_CONTEXT rename column PARAMETERS to CONTEXT
go

-- Номер ревизии: 55483
-- Номер версии: 1.18
-- Комментарий: BUG064171: Нет клиентских схем прав BUG063955: Не сохраняются права доступа сотрудника по умолчанию BUG063958: Исключение при установке схемы прав по умолчанию для клиентов
DELETE FROM ACCESSSCHEMES WHERE TYPE = 'C'
go

-- Номер ревизии: 55623
-- Номер версии: 1.18
-- Комментарий: BUG064229: Новый сотрудник привязывается к данным удаленного сотрудника (csaAdmin). 
ALTER TABLE LOGINS ADD DELETED CHAR(1)
go

UPDATE LOGINS SET DELETED = '0'
go

ALTER TABLE LOGINS MODIFY DELETED NOT NULL
go


-- Номер ревизии: 55863
-- Номер версии: 1.18
-- Комментарий: ENH063926: Добавить ограничение на количество неверных попыток ввода пароля. 

ALTER TABLE LOGINS ADD WRONG_LOGONS INTEGER DEFAULT 0 NOT NULL
go


-- Номер ревизии: 56667
-- Номер версии: 1.18
-- Комментарий: ENH064215: Предлагать сотруднику сменить пароль при первом входе (csaAdmin).

alter table LOGINS add (PASSWORD_EXPIRE_DATE date)
go

update LOGINS set PASSWORD_EXPIRE_DATE = SYSDATE
go

alter table LOGINS modify PASSWORD_EXPIRE_DATE not null
go

-- Номер ревизии: 56844
-- Номер версии: 1.18
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
create table PFP_AGE_CATEGORIES 
(
   ID                   INTEGER              not null,
   MIN_AGE              INTEGER              not null,
   MAX_AGE              INTEGER,
   WEIGHT               INTEGER              not null,
   constraint PK_PFP_AGE_CATEGORIES primary key (ID)
)
go

create sequence S_PFP_AGE_CATEGORIES
go

create table PFP_ANSWERS 
(
   ID                   INTEGER              not null,
   QUESTION_ID          INTEGER,
   TEXT                 VARCHAR2(250),
   WEIGHT               NUMBER(2)            not null,
   LIST_INDEX           INTEGER,
   constraint PK_PFP_ANSWERS primary key (ID)
)
go

create sequence S_PFP_ANSWERS
go

create table PFP_CARDS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(80)         not null,
   PROGRAMM_TYPE        VARCHAR2(50)         not null,
   INPUTS               NUMBER,
   BONUS                NUMBER,
   CLAUSE               VARCHAR2(250),
   CARD_ICON_ID         INTEGER              not null,
   PROGRAMM_ICON_ID     INTEGER,
   DESCRIPTION          VARCHAR2(170)        not null,
   RECOMMENDATION       VARCHAR2(500),
   DIAGRAM_USE_IMAGE    CHAR(1)              not null,
   DIAGRAM_COLOR        VARCHAR2(16),
   DIAGRAM_ICON_ID      INTEGER,
   DIAGRAM_USE_NET      CHAR(1)              not null,
   DEFAULT_CARD         CHAR(1)              default '0',
   constraint PK_PFP_CARDS primary key (ID)
)
go

create sequence S_PFP_CARDS
go

create unique index I_PFP_CARDS_NAME on PFP_CARDS (
   NAME ASC
)
go

create index I_PFP_CARDS_DEFAULT_CARD on PFP_CARDS (
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD) ASC
)
go

create table PFP_CHANNELS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   DELETED              CHAR(1)              default '0' not null,
   constraint PK_PFP_CHANNELS primary key (ID)
)
go

create sequence S_PFP_CHANNELS
go

create unique index I_PFP_CHANNELS_NAME on PFP_CHANNELS (
   decode(DELETED, '1', null, NAME) ASC
)
go

create table PFP_COMPLEX_PRODUCTS 
(
   ID                   INTEGER              not null,
   TYPE                 CHAR(1)              not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   MIN_SUM              NUMBER,
   ACCOUNT_ID           INTEGER              not null,
   IMAGE_ID             INTEGER,
   MIN_SUM_INSURANCE    NUMBER,
   USE_ICON             CHAR(1)              not null,
   constraint PK_PFP_COMPLEX_PRODUCTS primary key (ID)
)
go

create sequence S_PFP_COMPLEX_PRODUCTS
go

create unique index I_PFP_C_PROD_ACCOUNT on PFP_COMPLEX_PRODUCTS (
   TYPE ASC,
   ACCOUNT_ID ASC
)
go

create table PFP_CP_TABLE_VIEW_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   TABLE_COLUMN_ID      INTEGER              not null,
   VALUE                varchar2(100)        not null,
   constraint PK_PFP_CP_TBL_VW_PARAMETERS primary key (PRODUCT_ID, TABLE_COLUMN_ID)
)
go

create table PFP_CP_TARGET_GROUPS_BUNDLE 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_CP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

create table PFP_C_FUND_PRODUCTS 
(
   C_PRODUCT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   PRODUCT_ID           INTEGER              not null,
   constraint PK_PFP_C_FUND_PRODUCTS primary key (C_PRODUCT_ID, LIST_INDEX, PRODUCT_ID)
)
go

create table PFP_C_IMA_PRODUCTS 
(
   C_PRODUCT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   PRODUCT_ID           INTEGER              not null,
   constraint PK_PFP_C_IMA_PRODUCTS primary key (C_PRODUCT_ID, LIST_INDEX, PRODUCT_ID)
)
go

create table PFP_C_INSURANCE_PRODUCTS 
(
   C_PRODUCT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   INSURANCE_ID         INTEGER              not null,
   constraint PK_PFP_C_INSURANCE_PRODUCTS primary key (C_PRODUCT_ID, LIST_INDEX)
)
go

create table PFP_C_PRODUCT_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   KEY_PARAMETER        VARCHAR2(16)         not null,
   MIN_SUM              NUMBER               not null,
   constraint PK_PFP_C_PRODUCT_PARAMETERS primary key (PRODUCT_ID, KEY_PARAMETER)
)
go

create table PFP_DIAGRAM_STEPS 
(
   PFP_TYPE_PARAMETERS_ID INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   STEP_FROM            INTEGER              not null,
   STEP_TO              INTEGER              not null,
   STEP_NAME            VARCHAR2(50)         not null,
   constraint PK_PFP_DIAGRAM_STEPS primary key (PFP_TYPE_PARAMETERS_ID, LIST_INDEX)
)
go

create table PFP_INSURANCE_COMPANIES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   IMAGE_ID             INTEGER,
   constraint PK_PFP_INSURANCE_COMPANIES primary key (ID)
)
go

create sequence S_PFP_INSURANCE_COMPANIES
go

create unique index I_PFP_INSURANCE_COMPANIES_NAME on PFP_INSURANCE_COMPANIES (
   NAME ASC
)
go

create table PFP_INSURANCE_DATE_PERIODS 
(
   ID                   INTEGER              not null,
   TYPE_ID              INTEGER              not null,
   PERIOD               VARCHAR2(15)         not null,
   IS_DEFAULT_PERIOD    CHAR(1)              not null,
   PERIOD_MIN_SUM       NUMBER,
   PERIOD_MAX_SUM       NUMBER,
   constraint PK_PFP_I_DATE_PERIODS primary key (ID)
)
go

create sequence S_PFP_INSURANCE_DATE_PERIODS
go

create table PFP_INSURANCE_PERIOD_TYPES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   MONTHS               INTEGER,
   constraint PK_PFP_I_PERIOD_TYPES primary key (ID)
)
go

create sequence S_PFP_INSURANCE_PERIOD_TYPES
go

create unique index I_PFP_I_P_PERIOD_TYPES_NAME on PFP_INSURANCE_PERIOD_TYPES (
   NAME ASC
)
go

create table PFP_INSURANCE_PRODUCTS 
(
   ID                   INTEGER              not null,
   TYPE_ID              INTEGER,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   MIN_AGE              INTEGER,
   MAX_AGE              INTEGER,
   FOR_COMPLEX          char(1)              not null,
   COMPANY_ID           INTEGER              not null,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   UNIVERSAL            CHAR(1)              not null,
   ENABLED              CHAR(1)              not null,
   constraint PK_PFP_I_PRODUCTS primary key (ID)
)
go

create sequence S_PFP_INSURANCE_PRODUCTS
go

create unique index I_PFP_INSURANCE_PRODUCTS_NAME on PFP_INSURANCE_PRODUCTS (
   NAME ASC,
   FOR_COMPLEX ASC
)
go

create unique index I_PFP_PRODUCTS_UNIVERSAL_IP on PFP_INSURANCE_PRODUCTS (
   decode(UNIVERSAL,'0', null, '1') ASC
)
go

create table PFP_INSURANCE_TYPES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   IMAGE_ID             INTEGER,
   PARENT_ID            INTEGER,
   DESCRIPTION          VARCHAR(250)         not null,
   constraint PK_PFP_INSURANCE_TYPES primary key (ID)
)
go

create sequence S_PFP_INSURANCE_TYPES
go

create unique index I_PFP_INSURANCE_TYPES_NAME on PFP_INSURANCE_TYPES (
   NAME ASC,
   PARENT_ID ASC
)
go

create table PFP_INS_PRODUCT_TO_PERIODS 
(
   PRODUCT_ID           INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   DATE_PERIOD_ID       INTEGER              not null,
   constraint PK_PFP_INS_PRODUCT_TO_PERIODS primary key (PRODUCT_ID, LIST_INDEX)
)
go

create table PFP_INVESTMENT_PERIODS 
(
   ID                   INTEGER              not null,
   PERIOD               VARCHAR2(50)         not null,
   constraint PK_PFP_INVESTMENT_PERIODS primary key (ID)
)
go

create sequence S_PFP_INVESTMENT_PERIODS
go

create unique index I_PFP_INV_PERIODS_PERIOD on PFP_INVESTMENT_PERIODS (
   PERIOD ASC
)
go

create table PFP_IP_TARGET_GROUPS_BUNDLE 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_IP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

create table PFP_LOAN_KINDS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   FROM_AMOUNT          NUMBER               not null,
   TO_AMOUNT            NUMBER               not null,
   FROM_PERIOD          INTEGER              not null,
   TO_PERIOD            INTEGER              not null,
   DEFAULT_PERIOD       INTEGER              not null,
   FROM_RATE            NUMBER               not null,
   TO_RATE              NUMBER               not null,
   DEFAULT_RATE         NUMBER               not null,
   IMAGE_ID             INTEGER              not null,
   constraint PK_PFP_LOAN_KINDS primary key (ID),
   constraint PFP_LOAN_KINDS_NAME_UNIQUE unique (NAME)
)
go

create sequence S_PFP_LOAN_KINDS
go

create table PFP_PENSION_FUND 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   ICON_ID              INTEGER,
   constraint PK_PFP_PENSION_FUND primary key (ID)
)
go

create sequence S_PFP_PENSION_FUND
go

create unique index I_PFP_PENSION_FUND_NAME on PFP_PENSION_FUND (
   NAME ASC
)
go

create table PFP_PENSION_PRODUCT 
(
   ID                   INTEGER              not null,
   PENSION_FUND_ID      INTEGER              not null,
   ENTRY_FEE            NUMBER               not null,
   QUARTERLY_FEE        NUMBER               not null,
   MIN_PERIOD           INTEGER              not null,
   MAX_PERIOD           INTEGER              not null,
   DEFAULT_PERIOD       INTEGER              not null,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   UNIVERSAL            char(1)              not null,
   ENABLED              char(1)              not null,
   constraint PK_PFP_PENSION_PRODUCT primary key (ID)
)
go

create sequence S_PFP_PENSION_PRODUCT
go

create table PFP_PRODUCTS 
(
   ID                   INTEGER              not null,
   TYPE                 CHAR(1)              not null,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   FOR_COMPLEX          VARCHAR2(10),
   ADVISABLE_SUM        varchar2(32),
   MAX_SUM_FACTOR       NUMBER,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   SBOL_PRODUCT_ID      INTEGER,
   RISK_ID              INTEGER,
   INVESTMENT_PERIOD_ID INTEGER,
   USE_ICON             CHAR(1)              not null,
   AXIS_X               INTEGER,
   AXIS_Y               INTEGER,
   UNIVERSAL            CHAR(1)              not null,
   ENABLED              CHAR(1)              not null,
   constraint PK_PFP_PRODUCTS primary key (ID)
)
go

create sequence S_PFP_PRODUCTS
go

create unique index I_PFP_PROD_NAME on PFP_PRODUCTS (
   TYPE ASC,
   NAME ASC,
   FOR_COMPLEX ASC
)
go

create unique index I_PFP_PRODUCTS_UNIVERSAL_P on PFP_PRODUCTS (
   decode(UNIVERSAL, '0', null, '1') ASC,
   decode(UNIVERSAL,'1', TYPE, null) ASC
)
go

create unique index I_PFP_PRODUCTS_POINT_P on PFP_PRODUCTS (
   decode(AXIS_X,null,null,TYPE) ASC,
   AXIS_X ASC,
   AXIS_Y ASC
)
go

create table PFP_PRODUCTS_WEIGHTS 
(
   ID                   INTEGER              not null,
   PRODUCT              VARCHAR2(15)         not null,
   WEIGHT               INTEGER,
   constraint PK_PFP_PRODUCTS_WEIGHTS primary key (ID, PRODUCT)
)
go

create sequence S_PFP_PRODUCTS_WEIGHTS
go

create table PFP_PRODUCT_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   KEY_PARAMETER        VARCHAR2(16)         not null,
   MIN_SUM              NUMBER               not null,
   constraint PK_PFP_PRODUCT_PARAMETERS primary key (PRODUCT_ID, KEY_PARAMETER)
)
go

create table PFP_PRODUCT_TYPE_PARAMETERS 
(
   ID                   INTEGER              not null,
   DICTIONARY_TYPE      VARCHAR2(100)        not null,
   NAME                 VARCHAR2(100)        not null,
   USE                  CHAR(1)              not null,
   IMAGE_ID             INTEGER              not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   USE_ON_DIAGRAM       CHAR(1)              not null,
   USE_ON_TABLE         CHAR(1)              not null,
   AXIS_USE_ZERO        CHAR(1),
   X_AXIS_NAME          VARCHAR2(50),
   X_AXIS_USE_STEPS     CHAR(1),
   Y_AXIS_NAME          VARCHAR2(50),
   LINK_NAME            VARCHAR2(100)        not null,
   LINK_HINT            VARCHAR2(500)        not null,
   constraint PK_PFP_PRODUCT_TYPE_PARAMETERS primary key (ID)
)
go

create sequence S_PFP_PRODUCT_TYPE_PARAMETERS
go

create table PFP_PT_TARGET_GROUPS 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(16)         not null,
   constraint PK_PFP_PT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

create table PFP_P_PRODUCT_TARGET_GROUPS 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_P_PRODUCT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

create table PFP_QUESTIONS 
(
   ID                   INTEGER              not null,
   TEXT                 VARCHAR2(250),
   IS_DELETED           CHAR(1)              not null,
   SEGMENT              VARCHAR2(16)         not null,
   constraint PK_PFP_QUESTIONS primary key (ID)
)
go

create sequence S_PFP_QUESTIONS
go

create table PFP_RISKS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   constraint PK_PFP_RISKS primary key (ID)
)
go

create sequence S_PFP_RISKS
go

create unique index I_PFP_RISK_NAME on PFP_RISKS (
   NAME ASC
)
go

create table PFP_RISK_PROFILES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   MIN_WEIGHT           INTEGER              not null,
   MAX_WEIGHT           INTEGER,
   IS_DELETED           CHAR(1)              not null,
   IS_DEFAULT           CHAR(1)              not null,
   SEGMENT              VARCHAR2(16)         not null,
   constraint PK_PFP_RISK_PROFILES primary key (ID)
)
go

create sequence S_PFP_RISK_PROFILES
go

create table PFP_SP_TABLE_VIEW_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   TABLE_COLUMN_ID      INTEGER              not null,
   VALUE                VARCHAR2(100)        not null,
   constraint PK_PFP_SP_TABLE_VIEW_PARAMETER primary key (PRODUCT_ID, TABLE_COLUMN_ID)
)
go

create table PFP_SP_TARGET_GROUPS_BUNDLE 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_SP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

create table PFP_TABLE_COLUMNS 
(
   ID                   INTEGER              not null,
   PFP_TYPE_PARAMETERS_ID INTEGER,
   ORDER_INDEX          INTEGER              not null,
   COLUMN_NAME          VARCHAR2(50)         not null,
   constraint PK_PFP_TABLE_COLUMNS primary key (ID)
)
go

create sequence S_PFP_TABLE_COLUMNS
go

create table PFP_TARGETS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(250)        not null,
   IMAGE_ID             INTEGER,
   ONLY_ONE             CHAR(1)              default '0' not null,
   LATER_ALL            CHAR(1)              default '0' not null,
   LATER_LOANS          CHAR(1)              default '0' not null,
   constraint PK_PFP_TARGETS primary key (ID),
   constraint PFP_TARGETS_NAME_UNIQUE unique (NAME)
)
go

create sequence S_PFP_TARGETS
go

create index "DXFK_ANSWERS_TO_QUESTIONS" on PFP_ANSWERS
(
   QUESTION_ID
)
go

alter table PFP_ANSWERS
   add constraint FK_ANSWERS_TO_QUESTIONS foreign key (QUESTION_ID)
      references PFP_QUESTIONS (ID)
      on delete cascade
go

create index "DXFK_PFP_C_PROD_TO_ACCOUNT_PRO" on PFP_COMPLEX_PRODUCTS
(
   ACCOUNT_ID
)
go

alter table PFP_COMPLEX_PRODUCTS
   add constraint FK_PFP_C_PROD_TO_ACCOUNT_PROD foreign key (ACCOUNT_ID)
      references PFP_PRODUCTS (ID)
go

create index "DXFK_PFP_CP_T_FK_TABLE_PFP_TAB" on PFP_CP_TABLE_VIEW_PARAMETERS
(
   TABLE_COLUMN_ID
)
go

alter table PFP_CP_TABLE_VIEW_PARAMETERS
   add constraint FK_PFP_CP_T_FK_TABLE_PFP_TABL foreign key (TABLE_COLUMN_ID)
      references PFP_TABLE_COLUMNS (ID)
      on delete cascade
go

create index "DXFK_PFP_C_PROD_TO_FUND_PROD" on PFP_C_FUND_PRODUCTS
(
   PRODUCT_ID
)
go

alter table PFP_C_FUND_PRODUCTS
   add constraint FK_PFP_C_PROD_TO_FUND_PROD foreign key (PRODUCT_ID)
      references PFP_PRODUCTS (ID)
go

create index "DXFK_PFP_C_PROD_TO_IMA_PROD" on PFP_C_IMA_PRODUCTS
(
   PRODUCT_ID
)
go

alter table PFP_C_IMA_PRODUCTS
   add constraint FK_PFP_C_IM_FK_PFP_C__PFP_PROD foreign key (PRODUCT_ID)
      references PFP_PRODUCTS (ID)
go

create index "DXFK_PFP_C_I_PROD_TO_I_PROD" on PFP_C_INSURANCE_PRODUCTS
(
   INSURANCE_ID
)
go

alter table PFP_C_INSURANCE_PRODUCTS
   add constraint FK_PFP_C_I_PROD_TO_I_PROD foreign key (INSURANCE_ID)
      references PFP_INSURANCE_PRODUCTS (ID)
go

create index "DXFK_PFP_AXIS_TO_PT" on PFP_DIAGRAM_STEPS
(
   PFP_TYPE_PARAMETERS_ID
)
go

alter table PFP_DIAGRAM_STEPS
   add constraint FK_PFP_AXIS_TO_PT foreign key (PFP_TYPE_PARAMETERS_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
      on delete cascade
go

create index "DXFK_PFP_INS_P_TYPE_TO_D_RERIO" on PFP_INSURANCE_DATE_PERIODS
(
   TYPE_ID
)
go

alter table PFP_INSURANCE_DATE_PERIODS
   add constraint FK_PFP_INS_P_TYPE_TO_D_RERIOD foreign key (TYPE_ID)
      references PFP_INSURANCE_PERIOD_TYPES (ID)
go

create index "DXFK_PFP_COMPANY_TO_INS" on PFP_INSURANCE_PRODUCTS
(
   COMPANY_ID
)
go

alter table PFP_INSURANCE_PRODUCTS
   add constraint FK_PFP_COMPANY_TO_INS foreign key (COMPANY_ID)
      references PFP_INSURANCE_COMPANIES (ID)
go

create index "DXFK_PFP_I_PRODUCTS_TO_TYPES" on PFP_INSURANCE_PRODUCTS
(
   TYPE_ID
)
go

alter table PFP_INSURANCE_PRODUCTS
   add constraint FK_PFP_I_PRODUCTS_TO_TYPES foreign key (TYPE_ID)
      references PFP_INSURANCE_TYPES (ID)
go

create index "DXFK_PFP_I_TYPES_TO_I_TYPES" on PFP_INSURANCE_TYPES
(
   PARENT_ID
)
go

alter table PFP_INSURANCE_TYPES
   add constraint FK_PFP_I_TYPES_TO_I_TYPES foreign key (PARENT_ID)
      references PFP_INSURANCE_TYPES (ID)
go

create index "DXFK_PFP_PENSION_PRODUCT_TO_FU" on PFP_PENSION_PRODUCT
(
   PENSION_FUND_ID
)
go

alter table PFP_PENSION_PRODUCT
   add constraint FK_PFP_PENSION_PRODUCT_TO_FUND foreign key (PENSION_FUND_ID)
      references PFP_PENSION_FUND (ID)
go

create index "DXFK_PFP_INV_PERIOD_TO_PRODUCT" on PFP_PRODUCTS
(
   INVESTMENT_PERIOD_ID
)
go

alter table PFP_PRODUCTS
   add constraint FK_PFP_INV_PERIOD_TO_PRODUCT foreign key (INVESTMENT_PERIOD_ID)
      references PFP_INVESTMENT_PERIODS (ID)
go

create index "DXFK_PFP_RISK_TO_PRODUCT" on PFP_PRODUCTS
(
   RISK_ID
)
go

alter table PFP_PRODUCTS
   add constraint FK_PFP_RISK_TO_PRODUCT foreign key (RISK_ID)
      references PFP_RISKS (ID)
go

create index "DXFK_PFP_SEGMENT_TO_PT" on PFP_PT_TARGET_GROUPS
(
   PRODUCT_ID
)
go

alter table PFP_PT_TARGET_GROUPS
   add constraint FK_PFP_SEGMENT_TO_PT foreign key (PRODUCT_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
      on delete cascade
go

create index "DXFK_PFP_TARGET_GR_TO_P_PRODUC" on PFP_P_PRODUCT_TARGET_GROUPS
(
   PRODUCT_ID
)
go

alter table PFP_P_PRODUCT_TARGET_GROUPS
   add constraint FK_PFP_TARGET_GR_TO_P_PRODUCT foreign key (PRODUCT_ID)
      references PFP_PENSION_PRODUCT (ID)
      on delete cascade
go

create index "DXFK_PFP_SP_T_FK_TABLE_PFP_TAB" on PFP_SP_TABLE_VIEW_PARAMETERS
(
   TABLE_COLUMN_ID
)
go

alter table PFP_SP_TABLE_VIEW_PARAMETERS
   add constraint FK_PFP_SP_T_FK_TABLE_PFP_TABL foreign key (TABLE_COLUMN_ID)
      references PFP_TABLE_COLUMNS (ID)
      on delete cascade
go

create index "DXFK_PFP_TABLE_COLUMNS_TO_PT" on PFP_TABLE_COLUMNS
(
   PFP_TYPE_PARAMETERS_ID
)
go

alter table PFP_TABLE_COLUMNS
   add constraint FK_PFP_TABLE_COLUMNS_TO_PT foreign key (PFP_TYPE_PARAMETERS_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
go


-- Номер ревизии: 56947
-- Номер версии: 1.18
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
create table IMAGES 
(
   ID                   INTEGER              not null,
   IMAGE                BLOB,
   IMAGE_HELP           VARCHAR2(100),
   EXTEND_IMAGE         VARCHAR2(250),
   UPDATE_TIME          TIMESTAMP            not null,
   LINK_URL             VARCHAR2(512),
   NAME                 VARCHAR2(256),
   INNER_IMAGE          VARCHAR2(250),
   MD5                  VARCHAR(32),
   constraint PK_IMAGES primary key (ID)
)
go

create sequence S_IMAGES
go

create index IDX_IMAGES_MD5 on IMAGES (
   MD5 ASC
)
go

-- Номер ревизии: 56993
-- Номер версии: 1.18
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
create table PFP_CARD_RECOMMENDATIONS 
(
   ID                     INTEGER           not null,
   RECOMMENDATION         VARCHAR2(150),
   ACCOUNT_FROM_INCOME    NUMBER(7,2),
   ACCOUNT_TO_INCOME      NUMBER(7,2),
   ACCOUNT_DEFAULT_INCOME NUMBER(7,2)       not null,
   ACCOUNT_DESCRIPTION    VARCHAR2(150)     not null,
   THANKS_FROM_INCOME     NUMBER(7,2),
   THANKS_TO_INCOME       NUMBER(7,2),
   THANKS_DEFAULT_INCOME  NUMBER(7,2)       not null,
   THANKS_DESCRIPTION     VARCHAR2(150)     not null,
   constraint PK_PFP_CARD_RECOMMENDATIONS primary key (ID)
)
go

create sequence S_PFP_CARD_RECOMMENDATIONS
go

create table PFP_CR_CARDS 
(
   RECOMMENDATION_ID INTEGER not null,
   LIST_INDEX        INTEGER not null,
   CARD_ID           INTEGER not null,
   constraint PK_PFP_CR_CARDS primary key (RECOMMENDATION_ID, LIST_INDEX)
)
go

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_CARDS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
go

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_TO_CARDS foreign key (CARD_ID)
      references PFP_CARDS (ID)
go

create table PFP_CR_STEPS 
(
   RECOMMENDATION_ID INTEGER        not null,
   LIST_INDEX        INTEGER        not null,
   NAME              VARCHAR2(100)  not null,
   DESCRIPTION       VARCHAR2(500)  not null,
   constraint PK_PFP_CR_STEPS primary key (RECOMMENDATION_ID, LIST_INDEX)
)
go

alter table PFP_CR_STEPS
   add constraint FK_PFP_CR_STEPS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
go

-- Номер ревизии: 57006
-- Номер версии: 1.18
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
alter table PFP_PRODUCTS add ERIB_PRODUCT_ID integer
go

alter table PFP_PRODUCTS add ERIB_PRODUCT_ADDITIONAL_ID integer
go

alter table PFP_PRODUCTS drop column SBOL_PRODUCT_ID
go

-- Номер ревизии: 57060
-- Номер версии: 1.18
-- Комментарий: Доработать справочники ЦСА Админ для сквозной идентификации между блоками.
alter table PFP_CHANNELS add UUID VARCHAR2(32)
go

create unique index I_PFP_CHANNELS_UUID on PFP_CHANNELS(
    UUID
)
go

update PFP_CHANNELS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_CHANNELS modify UUID not null
go

alter table PFP_CHANNELS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_CHANNELS_DATE on PFP_CHANNELS(
    LAST_UPDATE_DATE
)
go

update PFP_CHANNELS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_CHANNELS modify LAST_UPDATE_DATE not null
go

alter table PFP_INVESTMENT_PERIODS add UUID VARCHAR2(32)
go

create unique index I_PFP_INVESTMENT_PERIODS_UUID on PFP_INVESTMENT_PERIODS(
    UUID
)
go

update PFP_INVESTMENT_PERIODS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INVESTMENT_PERIODS modify UUID not null
go

alter table PFP_INVESTMENT_PERIODS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INVESTMENT_PERIODS_DATE on PFP_INVESTMENT_PERIODS(
    LAST_UPDATE_DATE
)
go

update PFP_INVESTMENT_PERIODS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INVESTMENT_PERIODS modify LAST_UPDATE_DATE not null
go

alter table PFP_CARD_RECOMMENDATIONS add UUID VARCHAR2(32)
go

create unique index I_PFP_CARDRECOMMENDATIONS_UUID on PFP_CARD_RECOMMENDATIONS(
    UUID
)
go

update PFP_CARD_RECOMMENDATIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_CARD_RECOMMENDATIONS modify UUID not null
go

alter table PFP_CARD_RECOMMENDATIONS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_CARDRECOMMENDATIONS_DATE on PFP_CARD_RECOMMENDATIONS(
    LAST_UPDATE_DATE
)
go

update PFP_CARD_RECOMMENDATIONS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_CARD_RECOMMENDATIONS modify LAST_UPDATE_DATE not null
go

alter table PFP_CARDS add UUID VARCHAR2(32)
go

create unique index I_PFP_CARDS_UUID on PFP_CARDS(
    UUID
)
go

update PFP_CARDS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_CARDS modify UUID not null
go

alter table PFP_CARDS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_CARDS_DATE on PFP_CARDS(
    LAST_UPDATE_DATE
)
go

update PFP_CARDS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_CARDS modify LAST_UPDATE_DATE not null
go

alter table PFP_COMPLEX_PRODUCTS add UUID VARCHAR2(32)
go

create unique index I_PFP_COMPLEX_PRODUCTS_UUID on PFP_COMPLEX_PRODUCTS(
    UUID
)
go

update PFP_COMPLEX_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_COMPLEX_PRODUCTS modify UUID not null
go

alter table PFP_COMPLEX_PRODUCTS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_COMPLEX_PRODUCTS_DATE on PFP_COMPLEX_PRODUCTS(
    LAST_UPDATE_DATE
)
go

update PFP_COMPLEX_PRODUCTS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_COMPLEX_PRODUCTS modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_COMPANIES add UUID VARCHAR2(32)
go

create unique index I_PFP_INSURANCE_COMPANIES_UUID on PFP_INSURANCE_COMPANIES(
    UUID
)
go

update PFP_INSURANCE_COMPANIES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_COMPANIES modify UUID not null
go

alter table PFP_INSURANCE_COMPANIES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INSURANCE_COMPANIES_DATE on PFP_INSURANCE_COMPANIES(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_COMPANIES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_COMPANIES modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_PERIOD_TYPES add UUID VARCHAR2(32)
go

create unique index I_PFP_INS_PERIOD_TYPES_UUID on PFP_INSURANCE_PERIOD_TYPES(
    UUID
)
go

update PFP_INSURANCE_PERIOD_TYPES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_PERIOD_TYPES modify UUID not null
go

alter table PFP_INSURANCE_PERIOD_TYPES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INS_PERIOD_TYPES_DATE on PFP_INSURANCE_PERIOD_TYPES(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_PERIOD_TYPES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_PERIOD_TYPES modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_TYPES add UUID VARCHAR2(32)
go

create unique index I_PFP_INSURANCE_TYPES_UUID on PFP_INSURANCE_TYPES(
    UUID
)
go

update PFP_INSURANCE_TYPES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_TYPES modify UUID not null
go

alter table PFP_INSURANCE_TYPES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INSURANCE_TYPES_DATE on PFP_INSURANCE_TYPES(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_TYPES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_TYPES modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_PRODUCTS add UUID VARCHAR2(32)
go

create unique index I_PFP_INSURANCE_PRODUCTS_UUID on PFP_INSURANCE_PRODUCTS(
    UUID
)
go

update PFP_INSURANCE_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_PRODUCTS modify UUID not null
go

alter table PFP_INSURANCE_PRODUCTS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INSURANCE_PRODUCTS_DATE on PFP_INSURANCE_PRODUCTS(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_PRODUCTS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_PRODUCTS modify LAST_UPDATE_DATE not null
go

alter table PFP_LOAN_KINDS add UUID VARCHAR2(32)
go

create unique index I_PFP_LOAN_KINDS_UUID on PFP_LOAN_KINDS(
    UUID
)
go

update PFP_LOAN_KINDS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_LOAN_KINDS modify UUID not null
go

alter table PFP_LOAN_KINDS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_LOAN_KINDS_DATE on PFP_LOAN_KINDS(
    LAST_UPDATE_DATE
)
go

update PFP_LOAN_KINDS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_LOAN_KINDS modify LAST_UPDATE_DATE not null
go

alter table PFP_PENSION_FUND add UUID VARCHAR2(32)
go

create unique index I_PFP_PENSION_FUND_UUID on PFP_PENSION_FUND(
    UUID
)
go

update PFP_PENSION_FUND set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PENSION_FUND modify UUID not null
go

alter table PFP_PENSION_FUND add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PENSION_FUND_DATE on PFP_PENSION_FUND(
    LAST_UPDATE_DATE
)
go

update PFP_PENSION_FUND set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PENSION_FUND modify LAST_UPDATE_DATE not null
go

alter table PFP_PENSION_PRODUCT add UUID VARCHAR2(32)
go

create unique index I_PFP_PENSION_PRODUCT_UUID on PFP_PENSION_PRODUCT(
    UUID
)
go

update PFP_PENSION_PRODUCT set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PENSION_PRODUCT modify UUID not null
go

alter table PFP_PENSION_PRODUCT add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PENSION_PRODUCT_DATE on PFP_PENSION_PRODUCT(
    LAST_UPDATE_DATE
)
go

update PFP_PENSION_PRODUCT set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PENSION_PRODUCT modify LAST_UPDATE_DATE not null
go

alter table PFP_PRODUCTS add UUID VARCHAR2(32)
go

create unique index I_PFP_PRODUCTS_UUID on PFP_PRODUCTS(
    UUID
)
go

update PFP_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PRODUCTS modify UUID not null
go

alter table PFP_PRODUCTS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PRODUCTS_DATE on PFP_PRODUCTS(
    LAST_UPDATE_DATE
)
go

update PFP_PRODUCTS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PRODUCTS modify LAST_UPDATE_DATE not null
go

alter table PFP_PRODUCT_TYPE_PARAMETERS add UUID VARCHAR2(32)
go

create unique index I_PFP_PRODUCT_TYPES_UUID on PFP_PRODUCT_TYPE_PARAMETERS(
    UUID
)
go

update PFP_PRODUCT_TYPE_PARAMETERS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PRODUCT_TYPE_PARAMETERS modify UUID not null
go

alter table PFP_PRODUCT_TYPE_PARAMETERS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PRODUCT_TYPES_DATE on PFP_PRODUCT_TYPE_PARAMETERS(
    LAST_UPDATE_DATE
)
go

update PFP_PRODUCT_TYPE_PARAMETERS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PRODUCT_TYPE_PARAMETERS modify LAST_UPDATE_DATE not null
go

alter table PFP_TABLE_COLUMNS add UUID VARCHAR2(32)
go

create unique index I_PFP_TABLE_COLUMNS_UUID on PFP_TABLE_COLUMNS(
    UUID
)
go

update PFP_TABLE_COLUMNS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_TABLE_COLUMNS modify UUID not null
go

alter table PFP_RISKS add UUID VARCHAR2(32)
go

create unique index I_PFP_RISKS_UUID on PFP_RISKS(
    UUID
)
go

update PFP_RISKS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_RISKS modify UUID not null
go

alter table PFP_RISKS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_RISKS_DATE on PFP_RISKS(
    LAST_UPDATE_DATE
)
go

update PFP_RISKS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_RISKS modify LAST_UPDATE_DATE not null
go

alter table PFP_AGE_CATEGORIES add UUID VARCHAR2(32)
go

create unique index I_PFP_AGE_CATEGORIES_UUID on PFP_AGE_CATEGORIES(
    UUID
)
go

update PFP_AGE_CATEGORIES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_AGE_CATEGORIES modify UUID not null
go

alter table PFP_AGE_CATEGORIES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_AGE_CATEGORIES_DATE on PFP_AGE_CATEGORIES(
    LAST_UPDATE_DATE
)
go

update PFP_AGE_CATEGORIES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_AGE_CATEGORIES modify LAST_UPDATE_DATE not null
go

alter table PFP_ANSWERS add UUID VARCHAR2(32)
go

create unique index I_PFP_ANSWERS_UUID on PFP_ANSWERS(
    UUID
)
go

update PFP_ANSWERS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_ANSWERS modify UUID not null
go

alter table PFP_QUESTIONS add UUID VARCHAR2(32)
go

create unique index I_PFP_QUESTIONS_UUID on PFP_QUESTIONS(
    UUID
)
go

update PFP_QUESTIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_QUESTIONS modify UUID not null
go

alter table PFP_QUESTIONS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_QUESTIONS_DATE on PFP_QUESTIONS(
    LAST_UPDATE_DATE
)
go

update PFP_QUESTIONS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_QUESTIONS modify LAST_UPDATE_DATE not null
go

alter table PFP_RISK_PROFILES add UUID VARCHAR2(32)
go

create unique index I_PFP_RISK_PROFILES_UUID on PFP_RISK_PROFILES(
    UUID
)
go

update PFP_RISK_PROFILES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_RISK_PROFILES modify UUID not null
go

alter table PFP_RISK_PROFILES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_RISK_PROFILES_DATE on PFP_RISK_PROFILES(
    LAST_UPDATE_DATE
)
go

update PFP_RISK_PROFILES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_RISK_PROFILES modify LAST_UPDATE_DATE not null
go

alter table PFP_TARGETS add UUID VARCHAR2(32)
go

create unique index I_PFP_TARGETS_UUID on PFP_TARGETS(
    UUID
)
go

update PFP_TARGETS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_TARGETS modify UUID not null
go

alter table PFP_TARGETS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_TARGETS_DATE on PFP_TARGETS(
    LAST_UPDATE_DATE
)
go

update PFP_TARGETS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_TARGETS modify LAST_UPDATE_DATE not null
go


-- Номер ревизии: 57129
-- Номер версии: 1.18
-- Комментарий: Доработать справочники ЦСА Админ для сквозной идентификации между блоками.
create table DELETED_DICTIONARY_ENTITY_INFO 
(
   UUID            VARCHAR2(32)     not null,
   DICTIONARY_TYPE VARCHAR2(150)    not null,
   DELETED_DATE    TIMESTAMP        not null
)
go

create index I_DELETED_DICTIONARY_ENTITIES on DELETED_DICTIONARY_ENTITY_INFO(
    DICTIONARY_TYPE,
    DELETED_DATE
)
go


-- Номер ревизии: 57241
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.
drop table DELETED_DICTIONARY_ENTITY_INFO
go

create table DICTIONARY_CHANGE_INFO 
(
   ID                   INTEGER         not null,
   UUID                 VARCHAR2(32)    not null,
   DICTIONARY_TYPE      VARCHAR2(150)   not null,
   LAST_UPDATE_DATE     TIMESTAMP       not null,
   CHANGE_TYPE          VARCHAR2(6)     not null,
   ENTITY_DATA          CLOB
)
PARTITION BY RANGE (LAST_UPDATE_DATE) INTERVAL (NUMTODSINTERVAL(1,'day'))
(
    partition P_FIRST values less than (to_date('1-11-2013','DD-MM-YYYY'))
)
go

create index I_DICTIONARY_CHANGE_INFO ON DICTIONARY_CHANGE_INFO
(
    LAST_UPDATE_DATE,
    id
)
local
go 

create sequence S_DICTIONARY_CHANGE_INFO 
go


-- Номер ревизии: 57256
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.
drop table DICTIONARY_CHANGE_INFO
go

create table DICTIONARY_CHANGE_INFO 
(
   ID                   INTEGER         not null,
   UUID                 VARCHAR2(32)    not null,
   DICTIONARY_TYPE      VARCHAR2(150)   not null,
   UPDATE_DATE          TIMESTAMP       default SYSDATE not null,
   CHANGE_TYPE          VARCHAR2(6)     not null,
   ENTITY_DATA          CLOB
)
PARTITION BY RANGE (UPDATE_DATE) INTERVAL (NUMTOYMINTERVAL(1,'MONTH'))
(
    partition P_FIRST values less than (to_date('1-1-2014','DD-MM-YYYY'))
)
go

create index I_DICTIONARY_CHANGE_INFO ON DICTIONARY_CHANGE_INFO
(
    LAST_UPDATE_DATE,
    id
)
local
go 


-- Номер ревизии: 57275
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника услуг (Доработать справочник услуг для редактирования в многоблочном режиме.)

create table PAYMENT_SERVICES 
(
   ID                   INTEGER              not null,
   CODE                 varchar2(50)         not null,
   NAME                 nvarchar2(128)       not null,
   PARENT_ID            INTEGER,
   IMAGE_ID             INTEGER,
   POPULAR              CHAR(1)              not null,
   DESCRIPTION          VARCHAR2(512),
   SYSTEM               CHAR(1)              not null,
   PRIORITY             INTEGER,
   VISIBLE_IN_SYSTEM    CHAR(1)              not null,
   IMAGE_NAME           VARCHAR2(128)        not null,
   IS_CATEGORY          CHAR(1)              not null,
   SHOW_IN_SYSTEM       CHAR(1)              not null,
   SHOW_IN_API          CHAR(1)              not null,
   SHOW_IN_ATM          CHAR(1)              not null,
   constraint PK_PAYMENT_SERVICES primary key (ID)
)
go

create sequence S_PAYMENT_SERVICES
go

create unique index INDEX_CODE_1 on PAYMENT_SERVICES (
   CODE ASC
)
go

create index "DXFK_P_SERVICE_TO_IMAGES" on PAYMENT_SERVICES
(
   IMAGE_ID
)
go

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go


create index "DXFK_P_SERVICE_TO_P_SERVICE" on PAYMENT_SERVICES
(
   PARENT_ID
)
go

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_P_SERVICE foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
go

create table PAYMENT_SERV_PARENTS 
(
   SERVICE_ID           INTEGER              not null,
   PARENT_ID            INTEGER              not null,
   constraint PK_PAYMENT_SERV_PARENTS primary key (SERVICE_ID, PARENT_ID)
)
go

create sequence S_PAYMENT_SERV_PARENTS
go

create index "DXFK_PAY_SER_TO_PARENT" on PAYMENT_SERV_PARENTS
(
   PARENT_ID
)
go

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_PARENT foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
go


create index "DXFK_PAY_SER_TO_SERV" on PAYMENT_SERV_PARENTS
(
   SERVICE_ID
)
go

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_SERV foreign key (SERVICE_ID)
      references PAYMENT_SERVICES (ID)
go


-- Номер ревизии: 57279
-- Номер версии: 1.18
-- Комментарий: 4.2 механизм редактирования справочника регионов в многоблочном режиме.


create table REGIONS 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(20          not null,
   NAME                 NVARCHAR2(128)       not null,
   CODE_TB              VARCHAR2(2),
   PARENT_ID            INTEGER,
   EN_NAME              varchar2(128),
   constraint PK_REGIONS primary key (ID)
)
go

alter table REGIONS add EN_NAME varchar2(128)
go

create sequence S_REGIONS
go


create unique index INDEX_CODE_2 on REGIONS (
   CODE ASC
)
go

create index "DXDXFK_REGION_TO_REGION" on REGIONS
(
   PARENT_ID
)
go

alter table REGIONS
   add constraint FK_REGION_TO_REGION foreign key (PARENT_ID)
      references REGIONS (ID)
go


-- Номер ревизии: 57289
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.
create table DICTIONARY_INFORMATIONS
(
    NODE_ID INTEGER not null,
    LAST_UPDATE_DATE TIMESTAMP,
    STATE VARCHAR2(9) not null,
    ERROR_DETAIL CLOB,
    constraint PK_DICTIONARY_INFORMATIONS primary key (NODE_ID)
)
go


-- Номер ревизии: 57360
-- Номер версии: 1.18
-- Комментарий: Перевести справочник биллинговых систем на многоблочный режим. (Реализовать хранение справочника в ЦСА Админ.)

create table BILLINGS 
(
   ID                   INTEGER              not null,
   ADAPTER_UUID         varchar2(64)         not null,
   EXTERNAL_ID          varchar2(115)        not null,
   CODE                 varchar2(50)         not null,
   NAME                 varchar2(128)        not null,
   NEED_UPLOAD_JBT      CHAR(1)              not null,
   constraint PK_BILLINGS primary key (ID),
   constraint UK_BILLINGS_ADAPTER_UUID unique (ADAPTER_UUID)
)

go

create sequence S_BILLINGS
go

create unique index I_BILLINGS_CODE on BILLINGS (
   CODE ASC
)
go

ALTER INDEX INDEX_CODE_1 RENAME TO I_PAYMENT_SERVICES_CODE
go


-- Номер ревизии: 57394
-- Номер версии: 1.18
-- Комментарий: Перевести справочник групп риска на многоблочный режим. (часть 1) (БД)

create table GROUPS_RISK 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   IS_DEFAULT           CHAR(1)              not null,
   RANK                 VARCHAR2(5)          default 'HIGH' not null,
   EXTERNAL_ID          VARCHAR2(35)         not null,
   constraint PK_GROUPS_RISK primary key (ID),
   constraint UNIQUE_NAME unique (NAME)
)
go

create sequence S_GROUPS_RISK
go


-- Номер ревизии: 57399
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.

alter table PFP_CHANNELS drop column LAST_UPDATE_DATE
go

alter table PFP_INVESTMENT_PERIODS drop column LAST_UPDATE_DATE
go

alter table PFP_CARD_RECOMMENDATIONS drop column LAST_UPDATE_DATE
go

alter table PFP_CARDS drop column LAST_UPDATE_DATE
go

alter table PFP_COMPLEX_PRODUCTS drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_COMPANIES drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_PERIOD_TYPES drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_TYPES drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_PRODUCTS drop column LAST_UPDATE_DATE
go

alter table PFP_LOAN_KINDS drop column LAST_UPDATE_DATE
go

alter table PFP_PENSION_FUND drop column LAST_UPDATE_DATE
go

alter table PFP_PENSION_PRODUCT drop column LAST_UPDATE_DATE
go

alter table PFP_PRODUCTS drop column LAST_UPDATE_DATE
go

alter table PFP_PRODUCT_TYPE_PARAMETERS drop column LAST_UPDATE_DATE
go

alter table PFP_RISKS drop column LAST_UPDATE_DATE
go

alter table PFP_AGE_CATEGORIES drop column LAST_UPDATE_DATE
go

alter table PFP_QUESTIONS drop column LAST_UPDATE_DATE
go

alter table PFP_RISK_PROFILES drop column LAST_UPDATE_DATE
go

alter table PFP_TARGETS drop column LAST_UPDATE_DATE
go



-- Номер ревизии: 57465
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника подразделений. Часть 3. Перевод календарей, справочника подразделений на многоблочный режим.
create table CALENDARS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   DEPARTMENT_ID        INTEGER,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_CALENDARS primary key (ID),
   constraint AK_NAME_CALENDAR unique (NAME)
)

go

create sequence S_CALENDARS
go

/*==============================================================*/
/* Index: I_UUID_CALENDARS                                      */
/*==============================================================*/
create unique index I_UUID_CALENDARS on CALENDARS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: DEPARTMENTS                                           */
/*==============================================================*/
create table DEPARTMENTS 
(
   IS_CREDIT_CARD_OFFICE CHAR(1)              default '0' not null,
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256),
   BRANCH               VARCHAR2(4),
   DEPARTMENT           VARCHAR2(7),
   CITY                 VARCHAR2(256),
   POST_ADDRESS         VARCHAR2(256),
   LOCATION             VARCHAR2(256),
   PHONE                VARCHAR2(50),
   WEEK_OPER_TIME_BEGIN VARCHAR2(10),
   WEEK_OPER_TIME_END   VARCHAR2(10),
   WEEKEND_OPER_TIME_BEGIN VARCHAR2(10),
   WEEKEND_OPER_TIME_END VARCHAR2(10),
   FRIDAY_OPER_TIME_BEGIN VARCHAR2(10),
   FRIDAY_OPER_TIME_END VARCHAR2(10),
   TIME_SCALE           VARCHAR2(30),
   NOTIFY_CONRACT_CANCELATION INTEGER,
   CONNECTION_CHARGE    NUMBER(15,4),
   MONTHLY_CHARGE       NUMBER(15,4),
   RECONNECTION_CHARGE  NUMBER(15,4),
   KIND                 CHAR(1)              not null,
   MAIN                 CHAR(1)              default '0' not null,
   PARENT_DEPARTMENT    INTEGER,
   BIC                  VARCHAR2(26),
   TB                   VARCHAR2(4),
   OSB                  VARCHAR2(4),
   OFFICE               VARCHAR2(7),
   OFFICE_ID            VARCHAR2(64),
   SBIDNT               VARCHAR2(4),
   TIME_ZONE            INTEGER,
   USE_PARENT_CONNECTION_CHARGE CHAR(1)              default '0' not null,
   USE_PARENT_MONTHLY_CHARGE CHAR(1)              default '0' not null,
   USE_PARENT_TIME_SETTINGS CHAR(1)              default '0' not null,
   SERVICE              CHAR(1),
   USE_PARENT_TIME_ZONE CHAR(1)              default '0' not null,
   ESB_SUPPORTED        CHAR(1)              default '0' not null,
   USE_PARENT_ESB_SUPPORTED CHAR(1)              not null,
   MDM_SUPPORTED        CHAR(1)              not null,
   USE_PARENT_MDM_SUPPORTED CHAR(1)              not null,
   BILLING_ID           INTEGER,
   ADAPTER_UUID         varchar2(64),
   IS_OPEN_IMA_OFFICE   CHAR(1)              default '0' not null,
   SEND_SMS_METHOD      VARCHAR2(16),
   AUTOMATION_TYPE      VARCHAR2(15),
   constraint PK_DEPARTMENTS primary key (ID),
   constraint OFFICE_UNIQUE unique (OFFICE_ID)
)

go

create sequence S_DEPARTMENTS
go


/*==============================================================*/
/* Index: LIST_VSP_INDEX_DEPS                                   */
/*==============================================================*/
create index LIST_VSP_INDEX_DEPS on DEPARTMENTS (
   NAME ASC,
   POST_ADDRESS ASC,
   IS_CREDIT_CARD_OFFICE ASC
)
go

/*==============================================================*/
/* Index: I_DEPARTMENTS_BANK_INFO                               */
/*==============================================================*/
create unique index I_DEPARTMENTS_BANK_INFO on DEPARTMENTS (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(OFFICE, 'NULL') ASC,
   ID ASC
)
go

/*==============================================================*/
/* Index: DEPARTMENTS_INDEX_OFFICE                              */
/*==============================================================*/
create index DEPARTMENTS_INDEX_OFFICE on DEPARTMENTS (
   (TB || '|' || OSB || '|' || OFFICE) ASC
)
go

/*==============================================================*/
/* Index: I_DEPARTMENTS_TBS                                     */
/*==============================================================*/
create index I_DEPARTMENTS_TBS on DEPARTMENTS (
   DECODE(OFFICE||OSB||PARENT_DEPARTMENT,NULL,TB,NULL) ASC
)
go

create table RECEPTIONTIMES 
(
   ID                   INTEGER              not null,
   DEPARTMENT_ID        INTEGER              not null,
   CALENDAR_ID          INTEGER,
   TIME_START           VARCHAR2(10),
   TIME_END             VARCHAR2(10),
   USE_PARENT_SETTINGS  CHAR(1)              not null,
   PAYMENTTYPE          VARCHAR2(256)        not null,
   PAYMENTTYPEDESCRIPTION VARCHAR2(256)        not null,
   constraint PK_RECEPTIONTIMES primary key (ID)
)

go

create sequence S_RECEPTIONTIMES
go

/*==============================================================*/
/* Index: INDEX_DEPFORM                                         */
/*==============================================================*/
create index INDEX_DEPFORM on RECEPTIONTIMES (
   DEPARTMENT_ID ASC,
   PAYMENTTYPE ASC
)
go

create table WORK_DAYS 
(
   ID                   INTEGER              not null,
   CALENDAR_ID          INTEGER              not null,
   WORK_DATE            TIMESTAMP            not null,
   WORK_DAY             CHAR(1)              not null,
   constraint PK_WORK_DAYS primary key (ID)
)

go

create sequence S_WORK_DAYS
go

/*==============================================================*/
/* Index: WORK_DAYS_INDEX_DAY                                   */
/*==============================================================*/
create index WORK_DAYS_INDEX_DAY on WORK_DAYS (
   (TRUNC(WORK_DATE, 'DDD')) ASC
)
go

create index "DXFK_CALENDAR_TO_RT" on RECEPTIONTIMES
(
   CALENDAR_ID
)
go

alter table RECEPTIONTIMES
   add constraint FK_RECEPTIO_FK_CALEND_CALENDAR foreign key (CALENDAR_ID)
      references CALENDARS (ID)
go



-- Номер ревизии: 57470
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника банков (БИК): Дорабатываем справочник для редактирования в многоблочном режиме

/*==============================================================*/
/* Table: RUSBANKS                                              */
/*==============================================================*/

create table RUSBANKS 
(
   ID                   VARCHAR2(64)         not null,
   NAME                 VARCHAR2(256)        not null,
   BIC                  VARCHAR2(26),
   PLACE                VARCHAR2(50),
   CORR_ACCOUNT         VARCHAR2(26),
   SHORT_NAME           VARCHAR2(256),
   COUNTRY              VARCHAR2(4),
   ADDRESS              VARCHAR2(256),
   OUR                  CHAR(1),
   PARTICIPANT_CODE     VARCHAR2(8),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(12),
   constraint PK_RUSBANKS primary key (ID),
   constraint AK_UK_BANKS_BIC unique (BIC)
)

go

create sequence S_RUSBANKS
go

-- Номер ревизии: 57481
-- Номер версии: 1.18
-- Комментарий: Доработать поставщиков услуг для хранения в ЦСА Админ (ч.3 модель БД)

create table PROVIDER_SMS_ALIAS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(10)         not null,
   SERVICE_PROVIDER_ID  INTEGER              not null,
   constraint PK_PROVIDER_SMS_ALIAS primary key (ID)
)

go

create sequence S_PROVIDER_SMS_ALIAS
go

create unique index PROVIDER_SMS_ALIAS_NAME on PROVIDER_SMS_ALIAS (
   NAME ASC
)
go

create table PROVIDER_SMS_ALIAS_FIELD 
(
   ID                   INTEGER              not null,
   EDITABLE             CHAR(1)              not null,
   VALUE                NVARCHAR2(2000),
   PROVIDER_SMS_ALIAS_ID INTEGER              not null,
   FIELD_DESCRIPTION_ID INTEGER              not null,
   constraint PK_PROVIDER_SMS_ALIAS_FIELD primary key (ID)
)

go

create sequence S_PROVIDER_SMS_ALIAS_FIELD
go

create table SERVICE_PROVIDERS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(128)        not null,
   CODE                 VARCHAR2(20)         not null,
   CODE_RECIPIENT_SBOL  VARCHAR2(32),
   NAME                 VARCHAR2(160)        not null,
   DESCRIPTION          VARCHAR2(512),
   ALIAS                VARCHAR2(250),
   LEGAL_NAME           VARCHAR2(250),
   NAME_ON_BILL         VARCHAR2(250),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(9),
   ACCOUNT              VARCHAR2(25),
   BANK_CODE            VARCHAR2(9),
   BANK_NAME            VARCHAR2(128),
   CORR_ACCOUNT         VARCHAR2(25),
   BILLING_ID           INTEGER,
   CODE_SERVICE         VARCHAR2(50),
   NAME_SERVICE         VARCHAR2(150),
   IS_DEPT_AVAILABLE    CHAR(1)              default '0' not null,
   IS_FEDERAL           CHAR(1)              default '0' not null,
   MAX_COMISSION_AMOUNT number(15,4),
   MIN_COMISSION_AMOUNT number(15,4),
   COMISSION_RATE       number(15,4),
   DEPARTMENT_ID        INTEGER              not null,
   TRANSIT_ACCOUNT      varchar2(25),
   ATTR_DELIMITER       CHAR(1),
   ATTR_VALUES_DELIMITER CHAR(1),
   NSI_CODE             varchar2(128),
   STATE                varchar2(16)         not null,
   IS_GROUND            CHAR(1)              default '0' not null,
   IMAGE_ID             INTEGER,
   IS_POPULAR           CHAR(1)              default '0' not null,
   IS_PROPS_ONLINE      CHAR(1)              default '0' not null,
   IS_BANK_DETAILS      CHAR(1)              default '0' not null,
   ACCOUNT_TYPE         VARCHAR2(16),
   IS_MOBILEBANK        CHAR(1)              default '0' not null,
   MOBILEBANK_CODE      VARCHAR2(32),
   IS_FULL_PAYMENT      CHAR(1)              default '0' not null,
   PAYMENT_TYPE         VARCHAR2(20),
   KIND                 CHAR(1)              not null,
   IS_ALLOW_PAYMENTS    CHAR(1)              default '1' not null,
   PHONE_NUMBER         VARCHAR2(15),
   CREATION_DATE        TIMESTAMP            not null,
   IS_AUTOPAYMENT_SUPPORTED CHAR(1)              default '0' not null,
   URL                  VARCHAR2(256),
   TIP_OF_PROVIDER      VARCHAR2(255),
   BACK_URL             VARCHAR2(255),
   AFTER_ACTION         CHAR(1)              default '0' not null,
   CHECK_ORDER          CHAR(1)              default '0' not null,
   STANDART_SMS         CHAR(1)              default '1' not null,
   SMS_FORMAT           VARCHAR2(255),
   SMS_EXAMPLE          VARCHAR2(255),
   IS_BANKOMAT_SUPPORTED CHAR(1)              default '0' not null,
   VERSION_API          INTEGER,
   MIN_SUM_RESTRICTION  NUMBER(15,4),
   MAX_SUM_RESTRICTION  NUMBER(15,4),
   COMMISSION_MESSAGE   VARCHAR(250),
   FORM_NAME            VARCHAR2(35),
   SEND_CHARGE_OFF_INFO CHAR(1)              default '0' not null,
   PAYMENTCOUNT         INTEGER,
   IS_TEMPLATE_SUPPORTED CHAR(1)              default '1' not null,
   SORT_PRIORITY        INTEGER              not null,
   IS_BAR_SUPPORTED     CHAR(1)              default '0' not null,
   GROUP_RISK_ID        INTEGER,
   IMAGE_HELP_ID        INTEGER,
   IS_OFFLINE_AVAILABLE CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_IB CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_M_API CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_ATM_API CHAR(1)              default '0' not null,
   AVAILABLE_PAYMENTS_FOR_IB CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_M_API CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_ATM_API CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_ERMB CHAR(1)              default '1' not null,
   IS_AUTOPAYMENT_SUPPORTED_API CHAR(1)              default '0' not null,
   IS_AUTOPAYMENT_SUPPORTED_ATM CHAR(1)              default '0' not null,
   IS_AUTOPAYMENT_SUPPORTED_ERMB CHAR(1)              default '0' not null,
   IS_EDIT_PAYMENT_SUPPORTED CHAR(1)              default '1' not null,
   IS_CREDIT_CARD_SUPPORTED CHAR(1)              default '1' not null,
   VISIBLE_AUTOPAYMENTS_FOR_IB CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_API CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_ATM CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_ERMB CHAR(1)              default '0' not null,
   MOBILE_CHECKOUT_AVAILABLE CHAR(1)              default '0' not null,
   SUB_TYPE             VARCHAR2(10),
   IS_FASILITATOR       CHAR(1)              default '0' not null,
   constraint PK_SERVICE_PROVIDERS primary key (ID),
   constraint UN_CODE_S_CODE_R unique (CODE, CODE_SERVICE, BILLING_ID),
   constraint UN_CODE_S_CODE_R_SBOL unique (CODE_RECIPIENT_SBOL, CODE_SERVICE, BILLING_ID)
)

go

create sequence S_SERVICE_PROVIDERS
go

create index SERV_PROV_PAYM_COUNT on SERVICE_PROVIDERS (
   PAYMENTCOUNT DESC
)
go

create unique index INDEX_EXT_ID on SERVICE_PROVIDERS (
   EXTERNAL_ID ASC
)
go

create unique index IDX_MB_CODE on SERVICE_PROVIDERS (
   MOBILEBANK_CODE ASC
)
go

create index IDX_ALLOWP on SERVICE_PROVIDERS (
   IS_ALLOW_PAYMENTS ASC
)
go

create index IND_ACC_INN on SERVICE_PROVIDERS (
   INN ASC,
   ACCOUNT ASC
)
go

create table FIELD_DESCRIPTIONS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(40)         not null,
   NAME                 NVARCHAR2(60)        not null,
   DESCRIPTION          NVARCHAR2(200),
   HINT                 NVARCHAR2(200),
   TYPE                 VARCHAR2(20),
   MAX_LENGTH           INTEGER,
   MIN_LENGTH           INTEGER,
   NUMBER_PRECISION     INTEGER,
   IS_REQUIRED          CHAR(1)              not null,
   IS_EDITABLE          CHAR(1)              not null,
   IS_VISIBLE           CHAR(1)              not null,
   IS_SUM               CHAR(1)              not null,
   IS_KEY               CHAR(1)              not null,
   IS_INCLUDE_IN_SMS    CHAR(1)              not null,
   IS_SAVE_IN_TEMPLATE  CHAR(1)              not null,
   IS_FOR_BILL          CHAR(1)              not null,
   IS_HIDE_IN_CONFIRMATION CHAR(1)              not null,
   INITIAL_VALUE        NVARCHAR2(1024),
   RECIPIENT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   MASK                 VARCHAR2(1024),
   BUSINESS_SUB_TYPE    VARCHAR2(10),
   EXTENDED_DESC_DATA_ID VARCHAR2(50),
   constraint PK_FIELD_DESCRIPTIONS primary key (ID)
)

go

create sequence S_FIELD_DESCRIPTIONS
go

create table FIELD_VALUES_DESCR 
(
   FIELD_ID             INTEGER              not null,
   VALUE                NVARCHAR2(128)       not null,
   LIST_INDEX           INTEGER              not null,
   constraint PK_FIELD_VALUES_DESCR primary key (FIELD_ID, LIST_INDEX)
)
go

create table FIELD_VALIDATORS_DESCR 
(
   ID                   INTEGER              not null,
   FIELD_ID             INTEGER              not null,
   TYPE                 VARCHAR2(50)         not null,
   MESSAGE              NVARCHAR2(500)       not null,
   LIST_INDEX           INTEGER              not null,
   constraint PK_FIELD_VALIDATORS_DESCR primary key (ID)
)

go

create sequence S_FIELD_VALIDATORS_DESCR
go

create table FIELD_VALIDATORS_PARAM 
(
   ID                   INTEGER              not null,
   FIELD_ID             INTEGER              not null,
   NAME                 VARCHAR2(64)         not null,
   TYPE                 VARCHAR2(64)         not null,
   VALUE                VARCHAR2(1024)       not null,
   constraint PK_FIELD_VALIDATORS_PARAM primary key (ID)
)

go

create sequence S_FIELD_VALIDATORS_PARAM
go

create table FIELD_REQUISITE_TYPES 
(
   FIELD_ID             INTEGER              not null,
   REQUISITE_TYPE       VARCHAR2(32)         not null,
   LIST_INDEX           INTEGER              not null,
   constraint PK_FIELD_REQUISITE_TYPES primary key (FIELD_ID, LIST_INDEX)
)
go

create table SERVICE_PROVIDER_REGIONS 
(
   REGION_ID            INTEGER              not null,
   SERVICE_PROVIDER_ID  INTEGER              not null,
   SHOW_IN_PROMO_BLOCK  CHAR(1)              default '0' not null,
   constraint PK_SERVICE_PROVIDER_REGIONS primary key (REGION_ID, SERVICE_PROVIDER_ID)
)
go

create table SERV_PROVIDER_PAYMENT_SERV 
(
   PAYMENT_SERVICE_ID   INTEGER              not null,
   SERVICE_PROVIDER_ID  INTEGER              not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROVIDER_PAYMENT_SERV primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)
go


create table AUTOPAY_SETTINGS 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR2(10)         not null,
   RECIPIENT_ID         INTEGER              not null,
   PARAMETERS           CLOB                 not null,
   constraint PK_AUTOPAY_SETTINGS primary key (ID)
)
go

create sequence S_AUTOPAY_SETTINGS
go



create index "DXFK_AUTOPAY_SETTING_TO_PROV" on AUTOPAY_SETTINGS
(
   RECIPIENT_ID
)
go

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY__FK_AUTOPA_SERVICE_ foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
go

create index "DXFIELD_DESC_TO_PROVIDERS" on FIELD_DESCRIPTIONS
(
   RECIPIENT_ID
)
go

alter table FIELD_DESCRIPTIONS
   add constraint FIELD_DESC_TO_PROVIDERS foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

create index "DXFK_F_REQUISITE_TYPE_TO_FIELD" on FIELD_REQUISITE_TYPES
(
   FIELD_ID
)
go

alter table FIELD_REQUISITE_TYPES
   add constraint FK_F_REQUISITE_TYPE_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go


create index "DXFK_F_VALID_TO_FIELD" on FIELD_VALIDATORS_DESCR
(
   FIELD_ID
)
go

alter table FIELD_VALIDATORS_DESCR
   add constraint FK_F_VALID_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go

create index "DXFK_S_PROVIDERS_HELP_TO_IMAGE" on SERVICE_PROVIDERS
(
   IMAGE_HELP_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_SERVICE__FK_S_PROV_IMAGES foreign key (IMAGE_HELP_ID)
      references IMAGES (ID)
go


create index "DXFK_VAL_PARAM_TO_VALID" on FIELD_VALIDATORS_PARAM
(
   FIELD_ID
)
go

alter table FIELD_VALIDATORS_PARAM
   add constraint FK_VAL_PARAM_TO_VALID foreign key (FIELD_ID)
      references FIELD_VALIDATORS_DESCR (ID)
      on delete cascade
go


create index "DXFK_F_VALUE_TO_FIELD" on FIELD_VALUES_DESCR
(
   FIELD_ID
)
go

alter table FIELD_VALUES_DESCR
   add constraint FK_F_VALUE_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go

create index "DXFK_S_PROVIDERS_TO_BILLINGS" on SERVICE_PROVIDERS
(
   BILLING_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_BILLINGS foreign key (BILLING_ID)
      references BILLINGS (ID)
go


create index "DXFK_S_PROVIDERS_TO_GROUPS_RIS" on SERVICE_PROVIDERS
(
   GROUP_RISK_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_SERVICE__FK_S_PROV_GROUPS_R foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
go


create index "DXFK_S_PROVIDERS_TO_IMAGES" on SERVICE_PROVIDERS
(
   IMAGE_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go

create index "DXFK_PROV_REG_TO_PROV" on SERVICE_PROVIDER_REGIONS
(
   SERVICE_PROVIDER_ID
)
go

alter table SERVICE_PROVIDER_REGIONS
   add constraint FK_PROV_REG_TO_PROV foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_PROV_REG_TO_REG" on SERVICE_PROVIDER_REGIONS
(
   REGION_ID
)
go

alter table SERVICE_PROVIDER_REGIONS
   add constraint FK_PROV_REG_TO_REG foreign key (REGION_ID)
      references REGIONS (ID)
go


create index "DXFK_PROV_PAY_SER_TO_PAY" on SERV_PROVIDER_PAYMENT_SERV
(
   PAYMENT_SERVICE_ID
)
go

alter table SERV_PROVIDER_PAYMENT_SERV
   add constraint FK_PROV_PAY_SER_TO_PAY foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES (ID)
go


create index "DXFK_PROV_PAY_SER_TO_PROV" on SERV_PROVIDER_PAYMENT_SERV
(
   SERVICE_PROVIDER_ID
)
go

alter table SERV_PROVIDER_PAYMENT_SERV
   add constraint FK_PROV_PAY_SER_TO_PROV foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_S_PROVIDERS_TO_DEPARTMENT" on SERVICE_PROVIDERS
(
   DEPARTMENT_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

create index "DXFK_ALIAS_FI_TO_ALIAS" on PROVIDER_SMS_ALIAS_FIELD
(
   PROVIDER_SMS_ALIAS_ID
)
go

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
      references PROVIDER_SMS_ALIAS (ID)
go

create index "DXFK_ALIAS_FI_TO_FIELD" on PROVIDER_SMS_ALIAS_FIELD
(
   FIELD_DESCRIPTION_ID
)
go

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
      references FIELD_DESCRIPTIONS (ID)
go



-- Номер ревизии: 57504
-- Номер версии: 1.18
-- Комментарий: Реализовать синхронизацию справочника поставщиков в блоке с ЦСА Админ

alter table FIELD_DESCRIPTIONS add UUID VARCHAR2(32)
go

create unique index I_FIELD_DESCRIPTIONS_UUID on FIELD_DESCRIPTIONS(
    UUID
)
go

update FIELD_DESCRIPTIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table FIELD_DESCRIPTIONS modify UUID not null
go

-- Номер ревизии: 57515
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника подразделений. Часть 5. Доработки ReceptionTime для синхронизации.
alter table DICTIONARY_CHANGE_INFO modify UUID VARCHAR2(128)
go

-- Номер ревизии: 57555
-- Номер версии: 1.18
-- Комментарий: Реализовать синхронизацию справочника поставщиков в блоке с ЦСА Админ 
alter table PROVIDER_SMS_ALIAS add UUID VARCHAR2(32)
go

create unique index I_PROVIDER_SMS_ALIAS_UUID on PROVIDER_SMS_ALIAS(
    UUID
)
go

update PROVIDER_SMS_ALIAS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PROVIDER_SMS_ALIAS modify UUID not null
go

-- Номер ревизии: 57581
-- Номер версии: 1.18
-- Комментарий: убрать parent из подразделений (11 - из индекса БД)
drop index I_DEPARTMENTS_TBS 
go
create index I_DEPARTMENTS_TBS on DEPARTMENTS (
   DECODE(OFFICE||OSB,NULL,TB,NULL) ASC
)
go


-- Номер ревизии: 57668
-- Номер версии: 1.18
-- Комментарий: Доработать справочник техперерывов для редактирования в многоблочном режиме.
-- Реализовать синхронизацию справочника техперерывов в блоке с цсаАдмин

create table TECHNOBREAKS 
(
   ID                   INTEGER              not null,
   UUID                 varchar(32)          not null,
   ADAPTER_UUID         VARCHAR2(64)         not null,
   FROM_DATE            TIMESTAMP            not null,
   TO_DATE              TIMESTAMP            not null,
   PERIODIC             VARCHAR2(15)         not null,
   IS_DEFAULT_MESSAGE   CHAR(1)              default '1' not null,
   MESSAGE              VARCHAR2(200)        not null,
   STATUS               VARCHAR2(10)         not null,
   IS_AUTO_ENABLED      char(1)              default '0' not null,
   IS_ALLOWED_OFFLINE_PAY CHAR(1)              default '0' not null,
   constraint PK_TECHNOBREAKS primary key (ID),
   constraint AK_UK_TECHNOBREAKS unique(UUID)
)

go

create sequence S_TECHNOBREAKS
go



-- Номер ревизии: ---
-- Номер версии: 1.18
-- Комментарий: Удалён лишний столбец из REGIONS.

alter table REGIONS drop column EN_NAME
go

-- Номер ревизии: 57760
-- Номер версии: 1.18
-- Комментарий: убрать parent из подразделений (19 - модель БД)
alter table DEPARTMENTS drop column PARENT_DEPARTMENT cascade constraint
go

-- Номер ревизии: 57966
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника подразделений. Фоновая загрузка справочника подразделений.
create table DEPARTMENTS_REPLICA_TASKS 
(
   ID                   INTEGER              not null,
   OWNER_ID             VARCHAR2(25)         not null,
   OWNER_FIO            VARCHAR2(256)        not null,
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
   constraint PK_DEPARTMENTS_REPLICA_TASKS primary key (ID)
)
go

create sequence S_DEPARTMENTS_REPLICA_TASKS
go

create table DEPARTMENTS_TASKS_CONTENT 
(
   REPLICA_TASKS_ID     INTEGER              not null,
   CONTENT              BLOB,
   constraint PK_DEPARTMENTS_TASKS_CONTENT primary key (REPLICA_TASKS_ID)
)
go

alter table DEPARTMENTS_TASKS_CONTENT
   add constraint FK_DEPARTME_FK_CONTEN_DEPARTME foreign key (REPLICA_TASKS_ID)
      references DEPARTMENTS_REPLICA_TASKS (ID)
go

-- Номер ревизии: 57977
-- Номер версии: 1.18
-- Комментарий: ENH067661: Убрать Использовать настройку вышестоящего из подразделений. 
alter table DEPARTMENTS drop
    (USE_PARENT_TIME_SETTINGS, 
    USE_PARENT_CONNECTION_CHARGE, 
    USE_PARENT_MONTHLY_CHARGE, 
    USE_PARENT_TIME_ZONE, 
    USE_PARENT_ESB_SUPPORTED, 
    USE_PARENT_MDM_SUPPORTED)
go

-- Номер ревизии: 58035
-- Номер версии: 1.18
-- Комментарий: Доработать механизм фоновой репликации поставщиков услуг.
create table PROVIDER_REPLICA_TASKS 
(
   ID                   INTEGER              not null,
   OWNER_ID             VARCHAR2(25)         not null,
   OWNER_FIO            VARCHAR2(256)        not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE_CODE           VARCHAR2(20)         not null,
   REPORT_START_DATE    TIMESTAMP,
   REPORT_END_DATE      TIMESTAMP,
   CONTENT              BLOB,
   BILLINGS             VARCHAR2(200),
   PROPERTIES           BLOB,
   DETAILED_REPORT      CLOB,
   SOURCE_ERRORS        NUMBER(22,0),
   SOURCE_SUCCESS       NUMBER(22,0),
   DEST_INSERED         NUMBER(22,0),
   DEST_UPDATED         NUMBER(22,0),
   DEST_DELETED         NUMBER(22,0),
   constraint PK_PROVIDER_REPLICA_TASKS primary key (ID)
)
go

create sequence S_PROVIDER_REPLICA_TASKS
go

-- Номер ревизии: 58119 
-- Номер версии: 1.18
-- Комментарий:  CHG068368: Выбор ВСП при получении предодобренной кредитной карты
update regions reg1
set code_tb = (select code_tb
               from (select A.id, code_tb
                     from (SELECT id, CONNECT_BY_ROOT id as root
                     FROM regions
                     where parent_id is not null
                     START WITH parent_id is null
                     CONNECT BY PRIOR id = parent_id) A join regions B on A.root = B.id) reg2
               where reg2.id = reg1.id
               )
where parent_id is not null
go

-- Номер ревизии: 58201 
-- Номер версии: 1.18
-- Комментарий:  Реализовать синхронизацию справочника запрещенных счетов
create table BANNED_ACCOUNTS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   ACCOUNT_NUMBER       VARCHAR2(20)         not null,
   BICS                 VARCHAR2(500),
   BAN_TYPE             VARCHAR2(5)          not null,
   constraint PK_BANNED_ACCOUNTS primary key (ID),
   constraint AK_UK_BANNED_ACCOUNTS unique (UUID)
)

go

create sequence S_BANNED_ACCOUNTS
go

create index INDEX_ACCOUNT_NUMBER on BANNED_ACCOUNTS (
   ACCOUNT_NUMBER ASC
)
go


-- Номер ревизии: 58323
-- Номер версии: 1.18
-- Комментарий:  Синхронизация справочника ПБО в многоблочном режиме

/*==============================================================*/
/* Table: PANEL_BLOCKS                                          */
/*==============================================================*/
create table PANEL_BLOCKS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   Q_P_PANEL_ID         INTEGER,
   SHOW                 CHAR(1)              not null,
   ORDER_INDEX          INTEGER              not null,
   PROVIDER_NAME        varchar2(14),
   FIELD_SUMM_NAME      varchar2(40),
   SUMM                 NUMBER(15,4),
   FIELD_NAME           varchar2(40),
   IMAGE_ID             INTEGER,
   SHOW_PROVIDER_NAME   CHAR(1)              not null,
   PROVIDER_ID          INTEGER              not null,
   constraint PK_PANEL_BLOCKS primary key (ID),
   constraint AK_UNIQUE_PANEL_BLOCKS unique (UUID)
)

go

create sequence S_PANEL_BLOCKS
go

/*==============================================================*/
/* Table: QUICK_PAYMENT_PANELS                                  */
/*==============================================================*/
create table QUICK_PAYMENT_PANELS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   STATE                VARCHAR(16)          not null,
   NAME                 VARCHAR(100)         not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   constraint PK_QUICK_PAYMENT_PANELS primary key (ID),
   constraint AK_NAME_UNIQUE unique (NAME),
   constraint AK_UUID_UNIQUE unique (UUID)
)

go

create sequence S_QUICK_PAYMENT_PANELS
go

/*==============================================================*/
/* Table: Q_P_PANELS_DEPARTMENTS                                */
/*==============================================================*/
create table Q_P_PANELS_DEPARTMENTS 
(
   Q_P_PANEL_ID         INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_Q_P_PANELS_DEPARTMENTS primary key (Q_P_PANEL_ID, TB),
   constraint AK_DEPARTMENT_ID unique (TB)
)

go

create sequence S_Q_P_PANELS_DEPARTMENTS
go

create index "DXFK_PANEL_BLOCKS_TO_PROVIDERS" on PANEL_BLOCKS
(
   PROVIDER_ID
)
go

alter table PANEL_BLOCKS
   add constraint FK_PANEL_BLOCKS_TO_PROVIDERS foreign key (PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_PAN_BLCKS_TO_Q_P_PANELS" on PANEL_BLOCKS
(
   Q_P_PANEL_ID
)
go

alter table PANEL_BLOCKS
   add constraint FK_PAN_BLCKS_TO_Q_P_PANELS foreign key (Q_P_PANEL_ID)
      references QUICK_PAYMENT_PANELS (ID)
      on delete cascade
go


-- Номер ревизии: 58374
-- Номер версии: 1.18
-- Комментарий:  Синхронизация справочника событий в многоблочном режиме.
create table NEWS 
(
   ID                   INTEGER              not null,
   NEWS_DATE            TIMESTAMP            not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 CLOB                 not null,
   IMPORTANT            VARCHAR2(10)         not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   STATE                VARCHAR2(15),
   TYPE                 VARCHAR2(25),
   AUTOMATIC_PUBLISH_DATE TIMESTAMP,
   CANCEL_PUBLISH_DATE  TIMESTAMP,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_NEWS primary key (ID)
)

go

create sequence S_NEWS
go

create index NEWS_INDEX on NEWS (
   STATE ASC,
   NEWS_DATE ASC
)
go

create unique index I_NEWS_UUID on NEWS (
   UUID ASC
)
go

-- Номер ревизии: 58373
-- Номер версии: 1.18
-- Комментарий: BUG068289: Некорректно отображается список доступных подразделений у сотрудника(часть 3. инициализация контекста для джоба выполнения фоновых задач, изменение AllowedDepartmentsUtil)
delete from PROVIDER_REPLICA_TASKS
go

alter table PROVIDER_REPLICA_TASKS modify(OWNER_ID INTEGER)
go

delete from DEPARTMENTS_TASKS_CONTENT
go

delete from DEPARTMENTS_REPLICA_TASKS
go

alter table DEPARTMENTS_REPLICA_TASKS modify(OWNER_ID INTEGER)
go

-- Номер ревизии: 58386
-- Номер версии: 1.18
-- Комментарий: CHG065690: Переделать привязку ПБО к тербанкам

alter table CALENDARS add TB VARCHAR2(4)
go
update CALENDARS c set TB = (select d.TB from DEPARTMENTS d where d.id = c.DEPARTMENT_ID)
go
alter table CALENDARS drop column DEPARTMENT_ID
go 



-- Номер ревизии: 58475
-- Номер версии: 1.18
-- Комментарий: Перевод на многоблочный режим справочника депозитных продуктов.(часть 1)
/*==============================================================*/
/* Table: DEPOSITDESCRIPTIONS                                   */
/*==============================================================*/
create table DEPOSITDESCRIPTIONS 
(
   MINIMUM_BALANCE      CHAR(1 BYTE)         default '0' not null,
   CAPITALIZATION       CHAR(1 BYTE)         default '0' not null,
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   DESCRIPTION          CLOB                 not null,
   DETAILS              CLOB,
   DEPARTMENT_ID        INTEGER,
   KIND                 CHAR(1)              not null,
   PRODUCT_ID           INTEGER              not null,
   AVAILABLE_ONLINE     CHAR(1)              default '0' not null,
   LAST_UPDATE_DATE     TIMESTAMP            not null,
   constraint PK_DEPOSITDESCRIPTIONS primary key (ID)
)

go

create sequence S_DEPOSITDESCRIPTIONS
go

/*==============================================================*/
/* Index: UK_KEY                                                */
/*==============================================================*/
create unique index UK_KEY on DEPOSITDESCRIPTIONS (
   NAME ASC,
   DEPARTMENT_ID ASC
)
go

/*==============================================================*/
/* Index: UK_P_ID                                               */
/*==============================================================*/
create unique index UK_P_ID on DEPOSITDESCRIPTIONS (
   PRODUCT_ID ASC
)
go

/*==============================================================*/
/* Table: DEPOSITGLOBALS                                        */
/*==============================================================*/
create table DEPOSITGLOBALS 
(
   ROW_KEY              VARCHAR2(20)         not null,
   LIST_TRANSFORMATION  CLOB                 not null,
   CALCULATOR_TRANSFORMATION CLOB,
   ADMIN_LIST_TRANSFORMATION CLOB,
   ADMIN_EDIT_TRANSFORMATION CLOB,
   DEFAULT_DETAILS_TRANSFORMATION CLOB                 not null,
   MOBILE_DETAILS_TRANSFORMATION CLOB,
   MOBILE_LIST_TRANSFORMATION CLOB,
   VISIBILITY_TRANSFORMATION CLOB,
   constraint PK_DEPOSITGLOBALS primary key (ROW_KEY)
)
go

/*==============================================================*/
/* Table: DEPOSIT_DEPARTMENTS                                   */
/*==============================================================*/
create table DEPOSIT_DEPARTMENTS 
(
   DEPOSIT_ID           INTEGER              not null,
   DEPARTMENT_ID        INTEGER              not null,
   constraint PK_DEPOSIT_DEPARTMENTS primary key (DEPOSIT_ID, DEPARTMENT_ID)
)
go


-- Номер ревизии: 58486
-- Номер версии: 1.18
-- Комментарий: BUG068192: Не отображаются пункты лимитов при редактировании подразделения в цса админ 
create table LIMITS 
(
   ID                   INTEGER              not null,
   DEPARTMENT_ID        INTEGER              not null,
   CREATION_DATE        TIMESTAMP            not null,
   START_DATE           TIMESTAMP            not null,
   AMOUNT               NUMBER(15,4),
   CURRENCY             CHAR(3),
   GROUP_RISK_ID        INTEGER,
   LIMIT_TYPE           VARCHAR2(33)         not null,
   OPERATION_TYPE       VARCHAR2(30)         not null,
   RESTRICTION_TYPE     VARCHAR2(30)         not null,
   OPERATION_COUNT      INTEGER,
   CHANNEL_TYPE         VARCHAR2(20)         not null,
   STATE                VARCHAR2(10)         not null,
   SECURITY_TYPE        VARCHAR2(8),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_LIMITS primary key (ID)
)

go

create sequence S_LIMITS
go

create index INDEX_START_DATE_1 on LIMITS (
   START_DATE ASC
)
go

create unique index I_LIMITS_UUID on LIMITS (
   UUID ASC
)
go

-- Номер ревизии: 58532
-- Номер версии: 1.18
-- Комментарий: CHG069020: Поле синхронизации справочника поставщиков услуг 

alter table SERVICE_PROVIDERS add UUID VARCHAR2(32)
go

create unique index I_SERVICE_PROVIDERS_UUID on SERVICE_PROVIDERS(
    UUID
)
go

update SERVICE_PROVIDERS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table SERVICE_PROVIDERS modify UUID not null
go


-- Номер ревизии: 58536
-- Номер версии: 1.18
-- Комментарий: ИОК. увеличение длины поля
drop index I_EMPLOYEES_DEPARTMENT
go

alter table EMPLOYEES modify VSP VARCHAR2(7)
go

create index I_EMPLOYEES_DEPARTMENT on EMPLOYEES (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(VSP, 'NULL') ASC
)
go


-- Номер ревизии: 58551
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника ОМС продуктов.
create table IMAPRODUCT 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   NAME                 VARCHAR2(255)        not null,
   TYPE                 INTEGER              not null,
   SUBTYPE              INTEGER              not null,
   CURRENCY             CHAR(3)              not null,
   CONTRACT_TEMPLATE    CLOB                 not null,
   constraint PK_IMAPRODUCT primary key (ID)
)

go

create sequence S_IMAPRODUCT
go

create unique index I_IMAPRODUCT_UUID on IMAPRODUCT (
   UUID ASC
)
go

update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct' where DICTIONARY_TYPE = 'AccountProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory' where DICTIONARY_TYPE = 'AgeCategory'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.card.Card' where DICTIONARY_TYPE = 'Card'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.channel.Channel' where DICTIONARY_TYPE = 'Channel'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexFundInvestmentProduct' where DICTIONARY_TYPE = 'ComplexFundInvestmentProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct' where DICTIONARY_TYPE = 'ComplexIMAInvestmentProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct' where DICTIONARY_TYPE = 'ComplexInsuranceProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct' where DICTIONARY_TYPE = 'FundProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct' where DICTIONARY_TYPE = 'IMAProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompany' where DICTIONARY_TYPE = 'InsuranceCompany'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct' where DICTIONARY_TYPE = 'InsuranceProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType' where DICTIONARY_TYPE = 'InsuranceType'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod' where DICTIONARY_TYPE = 'InvestmentPeriod'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct' where DICTIONARY_TYPE = 'LoanKindProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund' where DICTIONARY_TYPE = 'PensionFund'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct' where DICTIONARY_TYPE = 'PensionProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType' where DICTIONARY_TYPE = 'PeriodType'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters' where DICTIONARY_TYPE = 'ProductTypeParameters'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question' where DICTIONARY_TYPE = 'Question'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.risk.Risk' where DICTIONARY_TYPE = 'Risk'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile' where DICTIONARY_TYPE = 'RiskProfile'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.targets.Target' where DICTIONARY_TYPE = 'Target'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.simple.TrustManagingProduct' where DICTIONARY_TYPE = 'TrustManagingProduct'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation' where DICTIONARY_TYPE = 'UseCreditCardRecommendation'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.regions.Region' where DICTIONARY_TYPE = 'Region'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreak' where DICTIONARY_TYPE = 'TechnoBreak'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.gate.dictionaries.ResidentBank' where DICTIONARY_TYPE = 'ResidentBank'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.bannedAccount.BannedAccount' where DICTIONARY_TYPE = 'BannedAccount'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider' where DICTIONARY_TYPE = 'BillingServiceProvider'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.quick.pay.QuickPaymentPanel' where DICTIONARY_TYPE = 'QuickPaymentPanel'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider' where DICTIONARY_TYPE = 'InternetShopsServiceProvider'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.providers.TaxationServiceProvider' where DICTIONARY_TYPE = 'TaxationServiceProvider'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.payment.services.PaymentService' where DICTIONARY_TYPE = 'PaymentService'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.billing.Billing' where DICTIONARY_TYPE = 'Billing'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.limits.GroupRisk' where DICTIONARY_TYPE = 'GroupRisk'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias' where DICTIONARY_TYPE = 'ServiceProviderSmsAlias'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.providers.BillingProviderService' where DICTIONARY_TYPE = 'BillingProviderService'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.calendar.WorkCalendar' where DICTIONARY_TYPE = 'WorkCalendar'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment' where DICTIONARY_TYPE = 'ExtendedDepartment'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.receptiontimes.ReceptionTime' where DICTIONARY_TYPE = 'ReceptionTime'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.limits.Limit' where DICTIONARY_TYPE = 'Limit'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.news.News' where DICTIONARY_TYPE = 'News'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.ext.sbrf.deposits.DepositProductSBRF' where DICTIONARY_TYPE = 'DepositProductSBRF'
go
update DICTIONARY_CHANGE_INFO set DICTIONARY_TYPE = 'com.rssl.phizic.business.ima.IMAProduct' where DICTIONARY_TYPE = 'IMAProduct'
go


-- Номер ревизии: 58374
-- Номер версии: 1.18
-- Комментарий: BUG069048: Ошибка при открытии событий 
create table NEWS_DEPARTMENT 
(
   NEWS_ID              INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, TB)
)
go

-- Номер ревизии: 58575 
-- Номер версии: 1.18
-- Комментарий: Синхронизация баннеров в многоблочном режиме 

create table ADVERTISINGS 
(
   ID                   INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   NAME                 VARCHAR2(100)        not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   TITLE                VARCHAR2(100),
   TEXT                 VARCHAR2(400),
   IMAGE_ID             INTEGER,
   SHOW_TIME            INTEGER              not null,
   ORDER_INDEX          INTEGER              not null,
   AVAILABLE            VARCHAR2(4)          not null,
   constraint PK_ADVERTISINGS primary key (ID)
)

go

create sequence S_ADVERTISINGS
go

/*==============================================================*/
/* Table: ADVERTISINGS_DEPARTMENTS                              */
/*==============================================================*/
create table ADVERTISINGS_DEPARTMENTS 
(
   ADVERTISING_ID       INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_ADVERTISINGS_DEPARTMENTS primary key (ADVERTISING_ID, TB)
)
go

/*==============================================================*/
/* Table: ADVERTISING_AREAS                                     */
/*==============================================================*/
create table ADVERTISING_AREAS 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   LIST_INDEX           INTEGER,
   AREA                 VARCHAR2(10)         not null,
   ORDER_INDEX          INTEGER              not null,
   constraint PK_ADVERTISING_AREAS primary key (ID)
)

go

create sequence S_ADVERTISING_AREAS
go

/*==============================================================*/
/* Table: ADVERTISING_BUTTONS                                   */
/*==============================================================*/
create table ADVERTISING_BUTTONS 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   TITLE                VARCHAR2(200),
   URL                  VARCHAR2(256),
   SHOW                 CHAR(1)              not null,
   ORDER_INDEX          INTEGER              not null,
   IMAGE_ID             INTEGER,
   constraint PK_ADVERTISING_BUTTONS primary key (ID)
)

go

create sequence S_ADVERTISING_BUTTONS
go

/*==============================================================*/
/* Table: ADVERTISING_REQUIREMENTS                              */
/*==============================================================*/
create table ADVERTISING_REQUIREMENTS 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   REQUIREMENT          VARCHAR2(20)         not null,
   REQUIREMENT_STATE    CHAR(1)              not null,
   constraint PK_ADVERTISING_REQUIREMENTS primary key (ID)
)

go

create sequence S_ADVERTISING_REQUIREMENTS
go

/*==============================================================*/
/* Table: ADVERTISING_REQ_ACC_TYPES                             */
/*==============================================================*/
create table ADVERTISING_REQ_ACC_TYPES 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   PRODUCT_ID           INTEGER,
   REQUIREMENT_STATE    CHAR(1)              not null,
   constraint PK_ADVERTISING_REQ_ACC_TYPES primary key (ID)
)

go

create sequence S_ADVERTISING_REQ_ACC_TYPES
go


create index "DXFK_ADV_DEP_TO_ADVERTISINGS" on ADVERTISINGS_DEPARTMENTS
(
   ADVERTISING_ID
)
go

alter table ADVERTISINGS_DEPARTMENTS
   add constraint FK_ADV_DEP_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_AREA_TO_ADVERTISINGS" on ADVERTISING_AREAS
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_AREAS
   add constraint FK_ADV_AREA_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_BUTT_TO_ADVERTISINGS" on ADVERTISING_BUTTONS
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_BUTTONS
   add constraint FK_ADV_BUTT_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_REQ_TO_ADVERTISINGS" on ADVERTISING_REQUIREMENTS
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_REQUIREMENTS
   add constraint FK_ADV_REQ_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_REQ_ACC_TYPES_TO_ADV" on ADVERTISING_REQ_ACC_TYPES
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_REQ_ACC_TYPES
   add constraint FK_ADV_REQ_ACC_TYPES_TO_ADV foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_REQ_ACC_T_TO_DEPOSITS" on ADVERTISING_REQ_ACC_TYPES
(
   PRODUCT_ID
)
go

alter table ADVERTISING_REQ_ACC_TYPES
   add constraint FK_ADV_REQ_ACC_T_TO_DEPOSITS foreign key (PRODUCT_ID)
      references DEPOSITDESCRIPTIONS (ID)
      on delete cascade
go

alter table ADVERTISINGS add UUID VARCHAR2(32)
go

create unique index I_ADVERTISINGS_UUID on ADVERTISINGS(
    UUID
)
go

update ADVERTISINGS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table ADVERTISINGS modify UUID not null
go

alter table ADVERTISING_BUTTONS add UUID VARCHAR2(32)
go

create unique index I_ADVERTISING_BUTTONS_UUID on ADVERTISING_BUTTONS(
    UUID
)
go

update ADVERTISING_BUTTONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table ADVERTISING_BUTTONS modify UUID not null
go

-- Номер ревизии: 58610
-- Номер версии: 1.18
-- Комментарий: BUG068403: Длина поля Код типа участника расчетов в сети Банка России не соответствует РО
alter table RUSBANKS modify PARTICIPANT_CODE VARCHAR2(100);



-- Номер ревизии: 58665
-- Номер версии: 1.18
-- Комментарий: BUG069090: Не синхронизируется видимость вклада в депозитах

alter table DEPOSIT_DEPARTMENTS add TB varchar2(4)
go
update DEPOSIT_DEPARTMENTS dd set dd.TB = (select d.TB from DEPARTMENTS d where d.ID = dd.DEPARTMENT_ID)
go
alter table DEPOSIT_DEPARTMENTS modify TB not null
go
alter table DEPOSIT_DEPARTMENTS drop constraint PK_DEPOSIT_DEPARTMENTS
go
alter table DEPOSIT_DEPARTMENTS drop column DEPARTMENT_ID
go
alter table DEPOSIT_DEPARTMENTS add constraint PK_DEPOSIT_DEPARTMENTS PRIMARY KEY (DEPOSIT_ID, TB)
go

-- Номер ревизии: 57765
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудника. Справочник подразделений ВСП (галка "возможность кредитования физ. лиц") 
alter table DEPARTMENTS add POSSIBLE_LOANS_OPERATION char(1)default '0' not null 
go

-- Номер ревизии: 58726
-- Номер версии: 1.18
-- Комментарий: CHG069141: Убрать many-to-one class="com.rssl.phizic.business.departments.Department" из лимитов.

alter table LIMITS add TB varchar2(4)
go
update LIMITS l set TB = (select d.TB from DEPARTMENTS d where d.ID = l.DEPARTMENT_ID)
go
alter table LIMITS drop column DEPARTMENT_ID
go
alter table LIMITS modify TB not null
go


-- Номер ревизии: 58784
-- Номер версии: 1.18
-- Комментарий: BUG023097: Переделать автоматическую публикацию новостей.
alter table NEWS RENAME COLUMN AUTOMATIC_PUBLISH_DATE TO START_PUBLISH_DATE
go

alter table NEWS RENAME COLUMN CANCEL_PUBLISH_DATE TO END_PUBLISH_DATE
go


create index I_NEWS_DATE  ON NEWS(
	START_PUBLISH_DATE, 
	END_PUBLISH_DATE
)
go

drop index NEWS_INDEX 
go

alter table NEWS drop column STATE
go


-- Номер ревизии: 59156
-- Номер версии: 1.18
-- Комментарий: ENH066749: Поиск по пустым фильтрам в АРМ сотрудника
drop index I_LOGINS_NAME
/
create UNIQUE index I_LOGINS_NAME
    on LOGINS(upper(NAME))
/


-- Номер ревизии: 59276
-- Номер версии: 1.18
-- Комментарий: BUG069765: Невозможно удалить группу риска
create table PAYMENTS_GROUP_RISK 
(
   ID                   INTEGER              not null,
   KIND                 VARCHAR2(3)          not null,
   GROUP_RISK_ID        INTEGER,
   TB                   VARCHAR2(4)          not null,
   constraint PK_PAYMENTS_GROUP_RISK primary key (ID)
)

go

create sequence S_PAYMENTS_GROUP_RISK
go

create index "DXFK_TO_PAY_GR_RSK_GROUP_RISK" on PAYMENTS_GROUP_RISK
(
   GROUP_RISK_ID
)
go

alter table PAYMENTS_GROUP_RISK
   add constraint FK_TO_PAY_GR_RSK_GROUP_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
go

-- Номер ревизии: 59304
-- Номер версии: 1.18
-- Комментарий: BUG069810: Исключение при входе администратора
drop index I_LOGINS_NAME
/
create UNIQUE index I_LOGINS_NAME
    on LOGINS(NAME)
/


-- Номер ревизии: 59844
-- Номер версии: 1.18
-- Комментарий: BUG070303: Не читаются настройки из БД для CSAAdmin 
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500)        not null,
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)
go

create sequence S_PROPERTIES
go

create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
go


-- Номер ревизии: 59981
-- Номер версии: 1.18
-- Комментарий: Связь справочника видов (групп) услуг и справочника категорий в АЛФ
alter table PAYMENT_SERVICES add CARD_OPERATION_CATEGORY NUMBER NULL
/
CREATE UNIQUE INDEX DXFK_CARD_OP_CATEGORY_ID
    ON PAYMENT_SERVICES(CARD_OPERATION_CATEGORY asc)
/

-- Номер ревизии: 60058
-- Номер версии: 1.18
-- Комментарий: CHG070548: Синхронизация ьсправочников - время последней синхронизации 
drop index I_DICTIONARY_CHANGE_INFO
go

create index I_DICTIONARY_CHANGE_INFO on DICTIONARY_CHANGE_INFO (  
   id ASC
)
local
go


-- Номер ревизии: 60211
-- Номер версии: 1.18
-- Комментарий: BUG070688: Нет проверки на уникальность MANAGER_ID при создании нового сотрудника в ЦСА админ

alter table EMPLOYEES add constraint AK_MANAGER_ID_UNIQUE UNIQUE (MANAGER_ID)
go 


-- Номер ревизии: 60289
-- Номер версии: 1.18
-- Комментарий: BUG070552: Наличие избыточных тех. перерывов при ошибке синхронизации блоков 
create index I_TECHNOBREAKS on TECHNOBREAKS (
   ADAPTER_UUID ASC,
   STATUS ASC,
   TO_DATE ASC
)
go


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
   UUID                 VARCHAR2(32)         not null,
   constraint PK_CARD_OPERATION_CATEGORIES primary key (ID)
)
go

create sequence S_CARD_OPERATION_CATEGORIES
go

create unique index IDX_NAME_LOGIN_INCOME on CARD_OPERATION_CATEGORIES (
   (nvl(LOGIN_ID, -1)) ASC,
   INCOME ASC,
   NAME ASC
)
go

create table MERCHANT_CATEGORY_CODES 
(
   CODE                 INTEGER              not null,
   INCOME_OPERATION_CATEGORY_ID VARCHAR2(100),
   OUTCOME_OPERATION_CATEGORY_ID VARCHAR2(100),
   constraint PK_MERCHANT_CATEGORY_CODES primary key (CODE)
)

go

create sequence S_MERCHANT_CATEGORY_CODES
go

create table CARD_OPERATION_TYPES 
(
   CODE                 INTEGER              not null,
   CASH                 CHAR(1)              not null,
   constraint PK_CARD_OPERATION_TYPES primary key (CODE)
)

go

create sequence S_CARD_OPERATION_TYPES
go


-- Номер ревизии: 60986
-- Номер версии: 1.18
-- Комментарий: Связь справочника видов (групп) услуг и справочника категорий в АЛФ(многоблочный режим) (ИОК)

alter table CARD_OPERATION_CATEGORIES modify UUID null
go

-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов

update CARD_OPERATION_CATEGORIES set COLOR='72bf44'
where NAME='Автомобиль' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='8967b0'
where NAME='Перевод с карты' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='47b082'
where NAME='Выдача наличных' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='f9b37b'
where NAME='Коммунальные платежи, связь, интернет' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='fff450'
where NAME='Здоровье и красота' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='009597'
where NAME='Одежда и аксессуары' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='74489d'
where NAME='Образование' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='d4711a'
where NAME='Отдых и развлечения' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='89c765'
where NAME='Супермаркеты' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00599d'
where NAME='Прочие расходы' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='aa55a1'
where NAME='Транспорт' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='ed1c24'
where NAME='Комиссия' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='d89016'
where NAME='Погашение кредитов' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='ce181e'
where NAME='Путешествия' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='f58220'
where NAME='Все для дома' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='62a73b'
where NAME='Рестораны и кафе' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='0066b3'
where NAME='Искусство' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='f04e4c'
where NAME='Перевод на вклад' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='faa61a'
where NAME='Перевод между своими картами' and INCOME='0' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='fecd7f'
where NAME='Траты наличными' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00a65d'
where NAME='Внесение наличных' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='1c3687'
where NAME='Зачисления' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='cf3734'
where NAME='Перевод на карту' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='e3d200'
where NAME='Возврат, отмена операций' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00aaad'
where NAME='Прочие поступления' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='8e187c'
where NAME='Перевод со вклада' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00b274'
where NAME='Перевод между своими картами' and INCOME='1' and LOGIN_ID is null
/

alter table CARD_OPERATION_CATEGORIES modify COLOR not null
/


-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов (ИОК)

alter table CARD_OPERATION_CATEGORIES modify COLOR null
/

-- Номер ревизии: 61560
-- Номер версии: 1.18
-- Комментарий: ФОС. схема сотрудника, работающего с письмами
ALTER TABLE ACCESSSCHEMES ADD MAIL_MANAGEMENT char(1)
go

UPDATE ACCESSSCHEMES SET MAIL_MANAGEMENT = '0'
go

ALTER TABLE ACCESSSCHEMES MODIFY MAIL_MANAGEMENT NOT NULL
go

затем на основной базе (базе блока) выполнить скрипт:
	select 'update ACCESSSCHEMES SET MAIL_MANAGEMENT=''1'' WHERE ID = ' || EXTERNAL_ID || chr(10) || ' go'
	from ACCESSSCHEMES 
	WHERE MAIL_MANAGEMENT = '1' AND EXTERNAL_ID IS NOT NULL
	go
результат (должны получиться скрипты вида " update ACCESSSCHEMES SET MAIL_MANAGEMENT='1' WHERE ID = 1 go") выполнить на базе ЦСААдмин.
 
-- Номер ревизии: 61699
-- Номер версии: 1.18
-- Комментарий: Доработки оплаты документов с помощью штрих-кодов.

alter table regions add provider_code_mapi varchar2(200) null
go
alter table regions add provider_code_atm  varchar2(200) null
go

 
-- Номер ревизии: 61916
-- Номер версии: 1.18
-- Комментарий: ФОС. Выбор сотрудника для переназначения.

create index I_ACCESSSCHEMES_MAIL on ACCESSSCHEMES (
   decode(MAIL_MANAGEMENT, '1', 1, null) ASC
)
go

create index "DXFK_LOGINS_TO_ACCESSSC" on LOGINS
(
   ACCESSSCHEME_ID
)
go

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
go

create sequence S_CONTACT_CENTER_AREAS
go

create unique index I_CONTACT_CENTER_AREAS_UUID on CONTACT_CENTER_AREAS (
   UUID ASC
)
go

create unique index I_CONTACT_CENTER_AREAS_NAME on CONTACT_CENTER_AREAS (
   AREA_NAME ASC
)
go

create table C_CENTER_AREAS_DEPARTMENTS 
(
   C_C_AREA_ID          INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_C_CENTER_AREAS_DEPARTMENTS primary key (C_C_AREA_ID, TB)
)
go

create sequence S_C_CENTER_AREAS_DEPARTMENTS
go

create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   TB ASC
)
go

alter table C_CENTER_AREAS_DEPARTMENTS
   add constraint FK_C_C_AREA_DEP_TO_C_C_AREA foreign key (C_C_AREA_ID)
      references CONTACT_CENTER_AREAS (ID)
      on delete cascade
go

create table MAIL_SUBJECTS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   DESCRIPTION          VARCHAR2(50)         not null,
   constraint PK_MAIL_SUBJECTS primary key (ID)
)

go

create sequence S_MAIL_SUBJECTS
go

create unique index I_MAIL_SUBJECTS_UUID on MAIL_SUBJECTS (
   UUID ASC
)
go

-- Номер ревизии: 62110 
-- Номер версии: 1.18
-- Комментарий: Справочник подразделений. Добавление признака активности подразделения.

ALTER TABLE DEPARTMENTS ADD (ACTIVE  CHAR(1) DEFAULT '1' NOT NULL);

-- Номер ревизии: 62220
-- Номер версии: 1.18
-- Комментарий: BUG073185: [ISUP] Вернуть раздельное выставление тех.перерывов в блоках

DROP TABLE TECHNOBREAKS
go

DROP SEQUENCE S_TECHNOBREAKS
go


-- Номер ревизии: 62336
-- Номер версии: 1.18
-- Комментарий: Доработка логики исполнения шаблонов

alter table SERVICE_PROVIDERS add PLANING_FOR_DEACTIVATE char(1)
go

alter table BILLINGS add TEMPLATE_STATE varchar2(30)
go


-- Номер ревизии: 63687
-- Номер версии: 1.18
-- Комментарий: Доработать доступность поставщиков в новом канале и запросы sql с учетом этих признаков

alter table SERVICE_PROVIDERS add IS_AUTOPAYMENT_SUPPORTED_S_API CHAR(1); 
update SERVICE_PROVIDERS set IS_AUTOPAYMENT_SUPPORTED_S_API = '0';
alter table SERVICE_PROVIDERS modify IS_AUTOPAYMENT_SUPPORTED_S_API CHAR(1) default '0' not null;

alter table SERVICE_PROVIDERS add VISIBLE_AUTOPAYMENTS_FOR_S_API CHAR(1);
update SERVICE_PROVIDERS set VISIBLE_AUTOPAYMENTS_FOR_S_API = '0';
alter table SERVICE_PROVIDERS modify VISIBLE_AUTOPAYMENTS_FOR_S_API CHAR(1) default '0' not null;

alter table SERVICE_PROVIDERS add VISIBLE_PAYMENTS_FOR_S_API CHAR(1);
update SERVICE_PROVIDERS set VISIBLE_PAYMENTS_FOR_S_API = '0';
alter table SERVICE_PROVIDERS modify VISIBLE_PAYMENTS_FOR_S_API CHAR(1) default '0' not null;

alter table SERVICE_PROVIDERS add AVAILABLE_PAYMENTS_FOR_S_API CHAR(1);
update SERVICE_PROVIDERS set AVAILABLE_PAYMENTS_FOR_S_API ='1';
alter table SERVICE_PROVIDERS modify AVAILABLE_PAYMENTS_FOR_S_API CHAR(1) default '1' not null;

-- Номер ревизии: 64318
-- Номер версии: 1.18
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ.

drop table CARD_OPERATION_CATEGORIES
/

drop sequence S_CARD_OPERATION_CATEGORIES
/

drop table MERCHANT_CATEGORY_CODES
/ 

drop sequence S_MERCHANT_CATEGORY_CODES
/

drop table CARD_OPERATION_TYPES 
/

drop sequence S_CARD_OPERATION_TYPES
/


alter table PAYMENT_SERVICES
drop column CARD_OPERATION_CATEGORY
/

-- Номер ревизии: 64655
-- Номер версии: 1.18
-- Комментарий: BUG074679: Признак «Предлагать по умолчанию» применяется к нескольким картам в ПФП.

drop index I_PFP_CARDS_DEFAULT_CARD
go
create unique index I_PFP_CARDS_DEFAULT_CARD on PFP_CARDS(
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD)
)
go


-- Номер ревизии: 65154
-- Номер версии: 1.18
-- Комментарий: BUG075776: Репликация поставщиков услуг: ошибка при репликации ПУ, у которого есть алиасы ЕРМБ

create index "DXFK_SMS_ALIAS_TO_PROVIDER" on PROVIDER_SMS_ALIAS
(
   SERVICE_PROVIDER_ID
)
;

alter table PROVIDER_SMS_ALIAS
add constraint FK_SMS_ALIAS_TO_PROVIDER foreign key (SERVICE_PROVIDER_ID)
    references SERVICE_PROVIDERS (ID)
    on delete cascade
;

alter table PROVIDER_SMS_ALIAS_FIELD
drop constraint FK_PROVIDER_FK_ALIAS__PROVIDER
;

alter table PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
    references PROVIDER_SMS_ALIAS (ID)
    on delete cascade
;

alter table PROVIDER_SMS_ALIAS_FIELD
drop constraint FK_PROVIDER_FK_ALIAS__FIELD_DE
;

alter table PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
    references FIELD_DESCRIPTIONS (ID)
    on delete cascade
;


-- Номер ревизии: 57966
-- Номер версии: 1.18
-- Комментарий: BUG075805: Фоновая репликация подразделений : Невозможно удалить задачу Новая 
alter table DEPARTMENTS_TASKS_CONTENT drop constraint FK_DEPARTME_FK_CONTEN_DEPARTME
go

alter table DEPARTMENTS_TASKS_CONTENT
   add constraint FK_CONTENT_TO_DEP_TASKS foreign key (REPLICA_TASKS_ID)
      references DEPARTMENTS_REPLICA_TASKS (ID)
      on delete cascade
go

-- Номер ревизии: 65616
-- Номер версии: v1.18
-- Комментарий: BUG075726 Оповещение об отключении шаблонов : Возможность редактирования (Модель БД)
update SERVICE_PROVIDERS set PLANING_FOR_DEACTIVATE = 0 where PLANING_FOR_DEACTIVATE is null
go
alter table SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE not null
go

-- Номер ревизии: 67283
-- Номер версии: v1.18
-- Комментарий: BUG077140: СПООБК: рассинхронизация признака "выдает кредитную карту" в справочнике подразделений
create table DEPARTMENTS_RECORDING 
(
   ID                   INTEGER              not null,
   TB_ERIB              VARCHAR2(3)          not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)          not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   DESPATCH             VARCHAR2(11),
   DATE_SUC             TIMESTAMP,
   constraint PK_DEPARTMENTS_RECORDING primary key (ID)
);

create sequence S_DEPARTMENTS_RECORDING;

create table DEPARTMENTS_RECORDING_TMP 
(
   ID                   INTEGER              not null,
   TB_ERIB              VARCHAR2(3)          not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)          not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   DESPATCH             VARCHAR2(11),
   DATE_SUC             TIMESTAMP,
   constraint PK_DEPARTMENTS_RECORDING_TMP primary key (ID)
);

create sequence S_DEPARTMENTS_RECORDING_TMP;

create table OFFICES_NOT_ISSUING_CARDS 
(
   ID                   INTEGER              not null,
   TB                   VARCHAR2(3)          not null,
   OSB                  VARCHAR2(4),
   OFFICE               VARCHAR2(7),
   constraint PK_OFFICES_NOT_ISSUING_CARDS primary key (ID)
);

create sequence S_OFFICES_NOT_ISSUING_CARDS;


-- Номер ревизии: 67474
-- Номер версии:  v1.18
-- Комментарий: Параметризация дополнительного соглашешения к Сберегательному счету. Исправление ошибок
alter table DEPOSITGLOBALS add PERCENT_RATES_TRANSFORMATION CLOB;

-- Номер ревизии: 67660
-- Номер версии: 1.18
-- Комментарий: Изменение логики загрузки справочника БИК(Модель БД)
alter table RUSBANKS add DATE_CH timestamp
go


-- Номер ревизии: 67711
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Баннеры)

CREATE TABLE ADVERTISING_BUTTONS_RES  ( 
    ID       	NUMBER NOT NULL,
    LOCALE_ID	VARCHAR2(10) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_ADVERTISING_BUTTONS_RES PRIMARY KEY(ID,LOCALE_ID)
)
GO

CREATE TABLE ADVERTISINGS_RES  ( 
    ID       	NUMBER NOT NULL,
    LOCALE_ID	VARCHAR2(10) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    TEXT     	VARCHAR2(400) NOT NULL,
    CONSTRAINT PK_ADVERTISINGS_RES PRIMARY KEY(ID,LOCALE_ID)	
)
GO

-- Номер ревизии: 67760
-- Номер версии: 1.18
-- Комментарий: Синхронизация локалезависимых сущностей(Баннеры, перевод на UUID)

drop table ADVERTISING_BUTTONS_RES
go

drop table ADVERTISINGS_RES
go


CREATE TABLE ADVERTISING_BUTTONS_RES  ( 
    UUID       	VARCHAR2(32) NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_ADVERTISING_BUTTONS_RES PRIMARY KEY(UUID,LOCALE_ID)
)
GO

CREATE TABLE ADVERTISINGS_RES  ( 
    UUID       	VARCHAR2(32) NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    TEXT     	VARCHAR2(400) NOT NULL,
    CONSTRAINT PK_ADVERTISINGS_RES PRIMARY KEY(UUID,LOCALE_ID)	
)
GO

-- Номер ревизии: 67770
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Новости)
create table NEWS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   TITLE                VARCHAR2(100)        not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   TEXT                 CLOB                 not null,
   constraint PK_NEWS_RES primary key (UUID, LOCALE_ID)
)
go

-- Номер ревизии: 67915
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
CREATE TABLE REGION_RES  ( 
    UUID     	VARCHAR2(32) NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    NAME     	VARCHAR2(128) NOT NULL,
    CONSTRAINT PK_REGION_RES PRIMARY KEY(UUID,LOCALE_ID)
GO
alter table REGIONS add UUID VARCHAR2(32)
go
create unique index I_REGIONS_UUID on REGIONS(
    UUID
)
go
update REGIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go
alter table REGIONS modify UUID not null
go


-- Номер ревизии: 67998
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Банки)
create table RUSBANKS_RES 
(
   ID                   VARCHAR2(64)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(256)        not null,
   PLACE                VARCHAR2(50),
   SHORT_NAME           VARCHAR2(256),
   ADDRESS              VARCHAR2(256),
   constraint PK_RUSBANKS_RES primary key (ID, LOCALE_ID)
)
go

-- Номер ревизии: 68032
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Редактирование поставщика услуг)
create table SERVICE_PROVIDERS_RES(
  UUID                              varchar2(32) NOT NULL,
  LOCALE_ID                         varchar2(30) NOT NULL,
  NAME                              VARCHAR2(160) NOT NULL,
  LEGAL_NAME                        VARCHAR2(250) NULL,
  ALIAS                             VARCHAR2(250) NULL,
  BANK_NAME                         VARCHAR2(128) NULL,
  DESCRIPTION                       VARCHAR2(512) NULL,
  TIP_OF_PROVIDER                   VARCHAR2(255) NULL,
  COMMISSION_MESSAGE                VARCHAR2(250) NULL,
  NAME_ON_BILL                      VARCHAR2(250) NULL,

  
--Номер ревизии: 68331
--Номер версии: 1.18
--Комментарий: Синхронизация УАК
create table INCOGNITO_PHONES_JURNAL (
    PHONE VARCHAR2(11) NOT NULL,
    LAST_UPDATE_TIME TIMESTAMP,
    NODE_ID INTEGER NOT NULL,
    ACTIVE char(1) not null
)
partition by range (LAST_UPDATE_TIME) interval (numtoyminterval(1,'MONTH'))
(
 partition P_START values less than (to_date('10-10-2014','DD-MM-YYYY'))
)
go

create index IDX_INCOG_LAST_DATE on INCOGNITO_PHONES_JURNAL ( 
    LAST_UPDATE_TIME asc,
    NODE_ID asc
)
local
go

CONSTRAINT PK_SERVICE_PROVIDERS_RES PRIMARY KEY(UUID, LOCALE_ID)
)

--Номер ревизии: 68431
--Номер версии: 1.18
--Комментарий: Синхронизация настроек в разных блоках
CREATE TABLE PROPERTY_SYNC_INFO  ( 
            "ID"                 INTEGER NOT NULL,
            "NODE_ID"                INTEGER NOT NULL,
            "OPERATION_DATE"           DATE NOT NULL,
            "STATE"                     VARCHAR2(5) NOT NULL,
            "GUID"          INTEGER NOT NULL
)
partition by range
 (OPERATION_DATE)
    interval (NUMTOYMINTERVAL(1,'YEAR'))
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
		
--Номер ревизии: 68580
--Номер версии: 1.18
--Комментарий: Синхронизация настроек в разных блоках

create sequence S_PROPERTY_SYNC_INFO
go
create index I_PROP_SYNC_INFO_GUID on PROPERTY_SYNC_INFO (
   GUID ASC
)
local
go

--Номер ревизии: 68732
--Номер версии: 1.18
--Комментарий: Хранение аватаров для АК

create table AVATAR_CHANGE_JURNAL (
    PHONE VARCHAR2(11) NOT NULL,
    LAST_UPDATE_TIME TIMESTAMP,
    NODE_ID INTEGER NOT NULL,
    AVATAR_PATH varchar2(100)
)
partition by range (LAST_UPDATE_TIME) interval (numtoyminterval(1,'MONTH'))
(
 partition P_START values less than (to_date('10-10-2014','DD-MM-YYYY'))
)
go

create index IDX_AVTR_LAST_DATE on AVATAR_CHANGE_JURNAL ( 
    LAST_UPDATE_TIME asc,
    NODE_ID asc
)
local
go

--Номер ревизии: 68760
--Номер версии: 1.18
--Комментарий: Многоязычность. Редактирование локалезависмых данных услуги.
create table PAYMENT_SERVICES_RES
(
   UUID                 VARCHAR2(50)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512),
   constraint PK_PAYMENT_SERVICES_RES primary key (UUID, LOCALE_ID)
)
go

-- Номер ревизии: 69528
-- Номер версии: 1.18
-- Комментарий:  BUG078393: [ISUP] Оптимизировать запрос лимитов. 

alter table LIMITS add (END_DATE timestamp)
go

update LIMITS up_l 
set up_l.END_DATE = 
    case 
        when up_l.START_DATE =
        (
            SELECT MAX(l.START_DATE)
            FROM LIMITS l
            WHERE   up_l.TB = l.TB and
                l.STATE = 'CONFIRMED' and
                current_date >= l.START_DATE and
                up_l.LIMIT_TYPE = l.LIMIT_TYPE and
                (up_l.GROUP_RISK_ID = l.GROUP_RISK_ID OR up_l.GROUP_RISK_ID IS NULL AND l.GROUP_RISK_ID IS NULL) and
                up_l.CHANNEL_TYPE  = l.CHANNEL_TYPE and
                up_l.RESTRICTION_TYPE = l.RESTRICTION_TYPE and
                (up_l.SECURITY_TYPE = l.SECURITY_TYPE OR up_l.GROUP_RISK_ID IS NULL AND l.GROUP_RISK_ID IS NULL)
        )
        then
            (
                select entered_l.START_DATE 
                from LIMITS entered_l 
                where 
                    entered_l.ID != up_l.ID and
                    entered_l.TB = up_l.TB and
                    entered_l.STATE = 'CONFIRMED' and
                    entered_l.LIMIT_TYPE = up_l.LIMIT_TYPE and
                    (up_l.GROUP_RISK_ID = entered_l.GROUP_RISK_ID OR up_l.GROUP_RISK_ID IS NULL AND entered_l.GROUP_RISK_ID IS NULL) and
                    up_l.CHANNEL_TYPE  = entered_l.CHANNEL_TYPE and
                    up_l.RESTRICTION_TYPE = entered_l.RESTRICTION_TYPE and
                    (up_l.SECURITY_TYPE = entered_l.SECURITY_TYPE OR up_l.GROUP_RISK_ID IS NULL AND entered_l.GROUP_RISK_ID IS NULL) and
                    entered_l.START_DATE > current_date
            )
        else
            up_l.START_DATE
    end
where 
    up_l.STATE = 'CONFIRMED' and
    up_l.START_DATE <= current_date
go

-- Номер ревизии: 69594
-- Номер версии: 1.18
-- Комментарий:  Блокировка учетных записей сотрудников
alter table LOGINS
	add LAST_LOGON_DATE TIMESTAMP(6) 
go

-- Номер ревизии: 69732
-- Номер версии: v.1.18
-- Комментарий: !!!Важно возможно на пром этот скрипт не нужен BUG077641: Не возможно сохранить поставщика Внешних услуг (модель БД)
alter table SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE null 
go

-- Номер ревизии: 69790
-- Номер версии: 1.18
-- Комментарий: ПРофиль адресная книга
create table IDENT_TYPE_FOR_DEPS (
   ID integer not null,
   SYSTEM_ID varchar2(20) not null,
   NAME varchar2(100) not null,
   UUID varchar2(100),

   constraint PK_IDENT_TYPE_FOR_DEPS primary key (ID)
)
go

create sequence S_IDENT_TYPE_FOR_DEPS 
go

create table ATTRIBUTES_FOR_IDENT_TYPES (
   ID integer not null,
   IDENT_ID integer not null,
   SYSTEM_ID varchar2(20) not null,
   NAME varchar2(100) not null,
   DATA_TYPE varchar2(20) not null,
   REG_EXP varchar2(100),
   MANDATORY char(1) not null,
   UUID varchar2(100),

   constraint PK_ATTRIBUTE_FOR_IDENT_TYPE primary key (ID),
   constraint FK_ATTRIBUTE_TO_IDENT foreign key (IDENT_ID) references IDENT_TYPE_FOR_DEPS (ID)
)
go

create sequence S_ATTRIBUTES_FOR_IDENT_TYPES  
go


insert into IDENT_TYPE_FOR_DEPS (ID, SYSTEM_ID, NAME, UUID)
values (S_IDENT_TYPE_FOR_DEPS.nextval, 'INN', 'ИНН', '00000000000000000000000000000001')
go

insert into IDENT_TYPE_FOR_DEPS (ID, SYSTEM_ID, NAME, UUID)
values (S_IDENT_TYPE_FOR_DEPS.nextval, 'DL', 'Водительское удостоверение', '00000000000000000000000000000002')
go

insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
 select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, 
 ID, 'NUMBER', 'ИНН', 'TEXT', '\d{12}', '0', '00000000000000000000000000000003' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'INN'
go

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
 ID, 'NUMBER', 'Номер', 'TEXT', '\d{6}', '0', '00000000000000000000000000000004' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
 ID, 'SERIES', 'Серия', 'TEXT', '\d{2}[0-9A-ZА-Я]{2}', '0', '00000000000000000000000000000005' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL'
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
  ID, 'ISSUE_DATE', 'Дата выдачи', 'DATE', '\d{2}.\d{2}.\d{4}', '0', '00000000000000000000000000000005' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 

'DL' 
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
 ID, 'ISSUE_BY', 'Кем выдано', 'TEXT', '.{150}', '0', '00000000000000000000000000000006' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
  ID, 'EXPIRE_DATE', 'Действует до', 'DATE', '\d{2}.\d{2}.\d{4}', '0', '00000000000000000000000000000007' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
go  

-- Номер ревизии: 69906
-- Номер версии: 1.18
-- Комментарий: BUG078978: Добавить группу настроек по новому каналу в блок "Настройка видимости в каналах" 

alter table PAYMENT_SERVICES add SHOW_IN_SOCIAL CHAR(1) not null;

-- Номер ревизии: 70884
-- Номер версии: 1.18
-- Комментарий: BUG082157: [Профиль] не добавляется водительское удостоверение
update ATTRIBUTES_FOR_IDENT_TYPES set REG_EXP = '.{0,150}' 
 WHERE IDENT_ID = (SELECT id FROM IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' ) and SYSTEM_ID = 'ISSUE_BY'
 
-- Номер ревизии: 72240
-- Номер версии: 1.18
-- Комментарий: Доработка профиля сотрудника в АРМ ЕРИБ
alter table EMPLOYEES add (SUDIR_LOGIN varchar2(100));
 
-- Номер ревизии: 72648
-- Номер версии: 1.18
-- Комментарий: BUG083704: АРМ. Ошибка при добавлении алиасов поставщику через разделители
 
ALTER TABLE PROVIDER_SMS_ALIAS MODIFY ( NAME VARCHAR2(20) )
GO


-- Номер ревизии: 72684
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги Модель БД. Таблица PROVIDER_PROPERTIES
CREATE TABLE PROVIDER_PROPERTIES
(
    PROVIDER_ID INTEGER not null,
    MB_CHECK_ENABLED            CHAR(1) not null,
    MCHECKOUT_DEFAULT_ENABLED   CHAR(1) not null,
    EINVOICE_DEFAULT_ENABLED   CHAR(1) not null,
    MB_CHECK_DEFAULT_ENABLED   CHAR(1) not null,
    UPDATE_DATE  TIMESTAMP,
    CONSTRAINT PROVIDER_PROPERTIES_PK PRIMARY KEY(PROVIDER_ID)
)
go


ALTER TABLE PROVIDER_PROPERTIES
ADD CONSTRAINT PROVIDER_PROP_TO_PROVIDER FOREIGN KEY (PROVIDER_ID)
  REFERENCES SERVICE_PROVIDERS (ID)
  ON DELETE CASCADE
go

-- Номер ревизии: 72770
-- Номер версии: 1.18
-- Комментарий: Применение промо-кодов для открытия промо-вкладов.
/*==============================================================*/
/* Table: PROMO_CODES_DEPOSIT                                   */
/*==============================================================*/
create table PROMO_CODES_DEPOSIT 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(9)          not null,
   CODE_G               VARCHAR2(9)          not null,
   MASK                 VARCHAR2(15)         not null,
   CODE_S               VARCHAR2(9)          not null,
   DATE_BEGIN           TIMESTAMP            not null,
   DATE_END             TIMESTAMP            not null,
   SROK_BEGIN           VARCHAR2(7)          not null,
   SROK_END             VARCHAR2(7)          not null,
   NUM_USE              INTEGER              default 0 not null,
   PRIORITY             CHAR(1)              not null,
   AB_REMOVE            CHAR(1)              not null,
   ACTIVE_COUNT         INTEGER              not null,
   HIST_COUNT           INTEGER              not null,
   NAME_ACT             VARCHAR2(250)        not null,
   NAME_S               VARCHAR2(150)        not null,
   NAME_F               VARCHAR2(500),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PROMO_CODES_DEPOSIT primary key (ID)
)

go

create sequence S_PROMO_CODES_DEPOSIT
go

comment on column PROMO_CODES_DEPOSIT.CODE is
'Уникальный код «промо»'
go

comment on column PROMO_CODES_DEPOSIT.CODE_G is
'Код промо-акции'
go

comment on column PROMO_CODES_DEPOSIT.MASK is
'Маска промо- кода'
go

comment on column PROMO_CODES_DEPOSIT.CODE_S is
'Код сегмента клиента'
go

comment on column PROMO_CODES_DEPOSIT.DATE_BEGIN is
'Дата начала периода активации промо- кода'
go

comment on column PROMO_CODES_DEPOSIT.DATE_END is
'Дата окончания периода активации промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.SROK_BEGIN is
'Срок действия промо-кода, начиная со дня ввода клиентом'
go

comment on column PROMO_CODES_DEPOSIT.SROK_END is
'Срок действия промо-кода, начиная со дня окончания периода активации промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.NUM_USE is
'Возможное количество раз использования клиентом'
go

comment on column PROMO_CODES_DEPOSIT.PRIORITY is
'Приоритет условий с промо-кодом «0» - используются ставки для промо-кода; «1» - отображается как отдельный вклад
'
go

comment on column PROMO_CODES_DEPOSIT.AB_REMOVE is
'Возможность удаления введенного промо-кода:«0» - нельзя удалять;«1» - можно удалять
'
go

comment on column PROMO_CODES_DEPOSIT.ACTIVE_COUNT is
'Количество действующих промо- кодов у клиента'
go

comment on column PROMO_CODES_DEPOSIT.HIST_COUNT is
'Количество введенных промо - кодов клиентом'
go

comment on column PROMO_CODES_DEPOSIT.NAME_ACT is
'Описание промо акции'
go

comment on column PROMO_CODES_DEPOSIT.NAME_S is
'Краткое описание промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.NAME_F is
'Подробное описание промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.UUID is
'Кросблочный ID'
go

/*==============================================================*/
/* Index: IDX_PROMO_CODES_DEP_UUID                              */
/*==============================================================*/
create unique index IDX_PROMO_CODES_DEP_UUID on PROMO_CODES_DEPOSIT (
   UUID ASC
)
go

/*==============================================================*/
/* Index: IDX_PROMO_CODES_DEP_CODE                              */
/*==============================================================*/
create unique index IDX_PROMO_CODES_DEP_CODE on PROMO_CODES_DEPOSIT (
   CODE ASC
)
go

-- Номер ревизии: 72799
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги. Справочник поставщиков услуг. Создание формы списка
/*==============================================================*/
/* Index: IDX_SP_IS_FASILITATOR                                 */
/*==============================================================*/
create index IDX_SP_IS_FASILITATOR on SERVICE_PROVIDERS (
   IS_FASILITATOR ASC
)
go

-- Номер ревизии: 73567
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги. Слепок МБК.
create table MBK_PHONE_JURNAL
(
	ID INTEGER not null,
	PHONE varchar2(11) not null,
	ADDED char(1) not null,
	LAST_UPDATE_TIME TIMESTAMP not null
)
partition by range
 (LAST_UPDATE_TIME)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-02-2015','DD-MM-YYYY')))
go

create index IDX_PH_JUR_LAST_DATE on MBK_PHONE_JURNAL
(
	ID asc
)
local
go

create sequence S_MBK_PHONE_JURNAL
cache 1000
go

-- Номер ревизии: 76108
-- Номер версии: 1.18
-- Комментарий: BUG086924: [ISUP] Ошибка удаления поставщика из-за constraint
alter table AUTOPAY_SETTINGS
   drop constraint FK_AUTOPAY__FK_AUTOPA_SERVICE_
go

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

-- Номер ревизии: 77156
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок. Не забыть заменить %линк к основной БД% !!!

/*==============================================================*/
/* Table: EXCEPTION_MAPPINGS                                    */
/*==============================================================*/
create table EXCEPTION_MAPPINGS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS primary key (HASH, GROUP_ID)
)
go

/*==============================================================*/
/* Table: EXCEPTION_MAPPINGS_RES                                */
/*==============================================================*/
create table EXCEPTION_MAPPINGS_RES 
(
   HASH                 varchar2(277)        not null,
   GROUP_ID             INTEGER              not null,
   LOCALE_ID            varchar2(30)         not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS_RES primary key (HASH, GROUP_ID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: EXCEPTION_MAPPING_RESTRICTIONS                        */
/*==============================================================*/
create table EXCEPTION_MAPPING_RESTRICTIONS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   TB                   VARCHAR2(4)          not null
)
go

/*==============================================================*/
/* Index: EXC_MAP_RESTRICTION_UK                                */
/*==============================================================*/
create unique index EXC_MAP_RESTRICTION_UK on EXCEPTION_MAPPING_RESTRICTIONS (
   HASH ASC,
   APPLICATION ASC,
   TB ASC
)
go
create index "DXEXCEPTION_MAPPING_RESTRICTIO" on EXCEPTION_MAPPING_RESTRICTIONS
(
   HASH, GROUP_ID
)
go

alter table EXCEPTION_MAPPING_RESTRICTIONS
   add constraint FK_EXCEPTIO_EXCEPTION_EXCEPTIO foreign key (HASH, GROUP_ID)
      references EXCEPTION_MAPPINGS (HASH, GROUP_ID)
      on delete cascade
go

insert into EXCEPTION_MAPPINGS (HASH, GROUP_ID, MESSAGE) 
select em.HASH, em.GROUP_ID, em.MESSAGE from EXCEPTION_MAPPINGS@%линк к основной БД% em
where not exists (
    select 1 from EXCEPTION_MAPPINGS em_insert 
    where 
        em_insert.HASH = em.HASH and
        em_insert.GROUP_ID = em.GROUP_ID
)
go

insert into EXCEPTION_MAPPING_RESTRICTIONS (HASH, GROUP_ID, APPLICATION, TB)
select emr.HASH, emr.GROUP_ID, emr.APPLICATION, emr.TB from EXCEPTION_MAPPING_RESTRICTIONS@%линк к основной БД% emr
where not exists (
    select 1 from EXCEPTION_MAPPING_RESTRICTIONS emr_insert
    where
        emr_insert.HASH = emr.HASH and
        emr_insert.APPLICATION = emr.APPLICATION and
        emr_insert.TB = emr.TB
)
go

insert into EXCEPTION_MAPPINGS_RES (HASH, GROUP_ID, MESSAGE) 
select em.HASH, em.GROUP_ID, em.MESSAGE from EXCEPTION_MAPPINGS_RES@%линк к основной БД% em
where not exists (
    select 1 from EXCEPTION_MAPPINGS_RES em_insert 
    where 
        em_insert.HASH = em.HASH and
        em_insert.GROUP_ID = em.GROUP_ID and
        em_insert.LOCALE_ID = em.LOCALE_ID 
)  
go

-- Номер ревизии: 80721  
-- Номер версии: 1.18
-- Комментарий: BUG089755: Многоязычность : локализация справочников в МАПИ

alter table SERVICE_PROVIDERS_RES add NAME_SERVICE VARCHAR2(150)
go

-- Номер ревизии: 81784
-- Номер версии: v1.18
-- Комментарий: Шаблон кредитной оферты. Доработка АРМ сотрудника. Модель БД (CSA Admin и ЕРИБ)
CREATE TABLE CREDIT_OFFER_TEMPLATE (
  ID         INTEGER      NOT NULL ,
  DATE_FROM  TIMESTAMP    NOT NULL ,
  DATE_TO    TIMESTAMP,
  OFFER      CLOB         NOT NULL ,
  UUID       VARCHAR2(32) NOT NULL
);

CREATE SEQUENCE S_CREDIT_OFFER_TEMPLATE;

CREATE UNIQUE INDEX IDX_CREDIT_OFFER_TEMPLATE_UUID ON CREDIT_OFFER_TEMPLATE (
   UUID ASC
);

-- Номер ревизии: 82043
-- Номер версии: v1.18
-- Комментарий: Доработки взаимодействия с порталом Яндекс.деньги. Адаптация запросов для шинного биллинга
ALTER TABLE PROVIDER_PROPERTIES ADD USE_ESB CHAR(1) default '0' not null;

-- Номер ревизии: 82282
-- Номер версии: v1.18
-- Комментарий: BUG085389: [Удаленное подключение УДБО] Доработать настройки в АРм сотрудника для подключения УДБО
/*==============================================================*/
/* Table: UDBO_CLAIM_RULES                                      */
/*==============================================================*/
create table UDBO_CLAIM_RULES 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   START_DATE           TIMESTAMP            not null,
   RULES_TEXT           CLOB                 not null,
   constraint PK_UDBO_CLAIM_RULES primary key (ID)
)

go

create sequence S_UDBO_CLAIM_RULES
go

/*==============================================================*/
/* Index: UDBO_CL_RU_START_DATE_IDX                             */
/*==============================================================*/
create index UDBO_CL_RU_START_DATE_IDX on UDBO_CLAIM_RULES (
   START_DATE ASC
)
go


-- Номер ревизии: 83946
-- Номер версии: v1.18
-- Комментарий: mdm ч.39. Удаление старой реализации

alter table DEPARTMENTS drop column MDM_SUPPORTED
go

-- Номер ревизии: 84128 
-- Номер версии: v1.18
-- Комментарий: Шаблон заявки на ПДП. Модель БД
ALTER TABLE CREDIT_OFFER_TEMPLATE ADD (TYPE VARCHAR2(4) DEFAULT 'ERIB');

-- Номер ревизии: 84128 
-- Номер версии: v1.18
-- Комментарий: Шаблон заявки на ПДП. Модель БД. Исправление ошибок кодирования
ALTER TABLE CREDIT_OFFER_TEMPLATE DROP COLUMN TYPE;
ALTER TABLE CREDIT_OFFER_TEMPLATE ADD (TYPE VARCHAR2(4));