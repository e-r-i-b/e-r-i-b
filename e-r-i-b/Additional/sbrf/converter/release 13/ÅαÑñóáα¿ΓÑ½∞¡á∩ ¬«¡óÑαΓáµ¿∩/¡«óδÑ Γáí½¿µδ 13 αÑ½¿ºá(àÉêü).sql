--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

create table WEBAPI_SESSIONS 
(
   SID                  varchar2(32)         not null,
   LOGIN_ID             INTEGER              not null,
   OPEN_DATE            TIMESTAMP(6)         not null,
   CLOSE_DATE           TIMESTAMP(6)         not null,
   PARAMS               CLOB,
   constraint PK_WEBAPI_SESSIONS primary key (SID) using index (
      create unique index PK_WEBAPI_SESSIONS on WEBAPI_SESSIONS(SID) tablespace INDX  
   )      
)
partition by range (OPEN_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_FIRST values less than(to_date('01-01-2014', 'DD-MM-YYYY'))
)
/
--Новый механизм доступных подразделений
create table ALLOWED_DEPARTMENTS 
(
   LOGIN_ID             INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   OSB                  VARCHAR2(4)          not null,
   VSP                  VARCHAR2(7)          not null,
   constraint PK_ALLOWED_DEPARTMENTS primary key (LOGIN_ID, TB, OSB, VSP) using index (
      create unique index PK_ALLOWED_DEPARTMENTS on ALLOWED_DEPARTMENTS(LOGIN_ID, TB, OSB, VSP) tablespace INDX  
   )
)
/
create index "DXFK_ALLOWED_DEPS_TO_LOGINS" on ALLOWED_DEPARTMENTS
(
   LOGIN_ID
)
/

alter table ALLOWED_DEPARTMENTS
   add constraint FK_ALLOWED_DEPS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 56812
-- Комментарий: АРМ Сотрудника: Справочник видов кредитных продуктов.
create table CREDIT_PRODUCT_TYPE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(25)         not null,
   CODE                 char(1)              not null,
   constraint PK_CREDIT_PRODUCT_TYPE primary key (ID) using index (
      create unique index PK_CREDIT_PRODUCT_TYPE on CREDIT_PRODUCT_TYPE(ID) tablespace INDX  
   )       
)
/
create sequence S_CREDIT_PRODUCT_TYPE
/
create unique index IDX_CODE_CREDIT_PRODUCT_TYPE on CREDIT_PRODUCT_TYPE (
   CODE ASC
) tablespace INDX  
/

-- Номер ревизии: 56817
-- Комментарий: АРМ Сотрудник. Справочник кредитных продуктов (ч. 1)
create table CREDIT_PRODUCT 
(
   ID                   integer              not null,
   NAME                 varchar2(25)         not null,
   CODE                 varchar2(3),
   CODE_DESCRIPTION     varchar2(100),
   ENSURING_STATUS      char(1)              default '1' not null,
   constraint PK_CREDIT_PRODUCT primary key (ID) using index (
      create unique index PK_CREDIT_PRODUCT on CREDIT_PRODUCT(ID) tablespace INDX  
   )     
)
/

create sequence S_CREDIT_PRODUCT
/

create table CREDIT_PRODUCT_SUB_TYPE 
(
   ID                   integer              not null,
   CODE                 varchar2(10),
   TO_CURRENCY          varchar2(3)          not null,
   CREDIT_PRODUCT_ID    integer              not null,
   TB                   varchar2(3)          not null,
   constraint PK_CREDIT_PRODUCT_SUB_TYPE primary key (ID) using index (
      create unique index PK_CREDIT_PRODUCT_SUB_TYPE on CREDIT_PRODUCT_SUB_TYPE(ID) tablespace INDX  
   )     
)
/

create sequence S_CREDIT_PRODUCT_SUB_TYPE
/

create index "DXFK_CRED_PR_SUB_TYPE_TO_CRED_" on CREDIT_PRODUCT_SUB_TYPE
(
   CREDIT_PRODUCT_ID
) tablespace INDX 
/

alter table CREDIT_PRODUCT_SUB_TYPE
   add constraint FK_CREDIT_P_FK_CRED_P_CREDIT_P foreign key (CREDIT_PRODUCT_ID)
      references CREDIT_PRODUCT (ID)
/

create index "DXFK_CRED_PR_SUB_TYPE_TO_CURR" on CREDIT_PRODUCT_SUB_TYPE
(
   TO_CURRENCY
) tablespace INDX 
/

alter table CREDIT_PRODUCT_SUB_TYPE
   add constraint FK_CREDIT_P_FK_CRED_P_CURRENCI foreign key (TO_CURRENCY)
      references CURRENCIES (ID)
/

-- Номер ревизии: 56993
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
create table PFP_CARD_RECOMMENDATIONS 
(
   ID                   integer              not null,
   RECOMMENDATION       varchar2(150),
   ACCOUNT_FROM_INCOME  number(7,2),
   ACCOUNT_TO_INCOME    number(7,2),
   ACCOUNT_DEFAULT_INCOME number(7,2)          not null,
   ACCOUNT_DESCRIPTION  varchar2(150)        not null,
   THANKS_FROM_INCOME   number(7,2),
   THANKS_TO_INCOME     number(7,2),
   THANKS_DEFAULT_INCOME number(7,2)          not null,
   THANKS_DESCRIPTION   varchar2(150)        not null,
   constraint PK_PFP_CARD_RECOMMENDATIONS primary key (ID) using index (
      create unique index PK_PFP_CARD_RECOMMENDATIONS on PFP_CARD_RECOMMENDATIONS(ID) tablespace INDX  
   )        
)
/
create sequence S_PFP_CARD_RECOMMENDATIONS
/

create table PFP_CR_CARDS 
(
   RECOMMENDATION_ID    integer              not null,
   LIST_INDEX           integer              not null,
   CARD_ID              integer              not null,
   constraint PK_PFP_CR_CARDS primary key (RECOMMENDATION_ID, LIST_INDEX) using index (
      create unique index PK_PFP_CR_CARDS on PFP_CR_CARDS(RECOMMENDATION_ID, LIST_INDEX) tablespace INDX  
   )   
)
/

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_CARDS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
/

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_TO_CARDS foreign key (CARD_ID)
      references PFP_CARDS (ID)
/

create table PFP_CR_STEPS 
(
   RECOMMENDATION_ID    integer              not null,
   LIST_INDEX           integer              not null,
   NAME                 varchar2(100)        not null,
   DESCRIPTION          varchar2(500)        not null,
   constraint PK_PFP_CR_STEPS primary key (RECOMMENDATION_ID, LIST_INDEX) using index (
      create unique index PK_PFP_CR_STEPS on PFP_CR_STEPS(RECOMMENDATION_ID, LIST_INDEX) tablespace INDX  
   )     
)
/

alter table PFP_CR_STEPS
   add constraint FK_PFP_CR_STEPS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
/

-- Номер ревизии: 57089
-- Комментарий: Арм Сотрудника. Справочник условий по кредитным продуктам. (Сущность + Бд  + Сервис.)
create table CREDIT_PRODUCT_CONDITION 
(
   ID                   INTEGER              not null,
   CREDIT_PRODUCT_ID    INTEGER              not null,
   CREDIT_PRODUCT_TYPE_ID INTEGER              not null,
   TRANSACT_SM_USE      CHAR(1)              default '1' not null,
   MIN_YEAR             INTEGER,
   MIN_MONTH            INTEGER,
   MAX_YEAR             INTEGER,
   MAX_MONTH            INTEGER,
   MAX_RANGE_INCLUDE    CHAR(1)              default '1' not null,
   USE_INITIAL_FEE      CHAR(1)              not null,
   MIN_INITIAL_FEE      NUMBER(15,4),
   ADDITIONAL_CONDITIONS VARCHAR2(500),
   INCLUDE_MAX_INITIAL_FEE char(1)              default '1' not null,
   MAX_INITIAL_FEE      NUMBER(15,4),
   DEPARTMENTS_STR      varchar2(500),
   IS_PUBLISHED         char(1)              default '0' not null,
   constraint PK_CREDIT_PRODUCT_CONDITION primary key (ID) using index (
      create unique index PK_CREDIT_PRODUCT_CONDITION on CREDIT_PRODUCT_CONDITION(ID) tablespace INDX  
   )   
)
/

create sequence S_CREDIT_PRODUCT_CONDITION
/


create index "DXFK_CPC_TO_CP" on CREDIT_PRODUCT_CONDITION
(
   CREDIT_PRODUCT_ID
) tablespace INDX 
/

alter table CREDIT_PRODUCT_CONDITION
   add constraint FK_CPC_TO_CP foreign key (CREDIT_PRODUCT_ID)
      references CREDIT_PRODUCT (ID)
/


create index "DXFK_CPC_TO_CPT" on CREDIT_PRODUCT_CONDITION
(
   CREDIT_PRODUCT_TYPE_ID
) tablespace INDX 
/

alter table CREDIT_PRODUCT_CONDITION
   add constraint FK_CPC_TO_CPT foreign key (CREDIT_PRODUCT_TYPE_ID)
      references CREDIT_PRODUCT_TYPE (ID)
/

create table CURR_CRED_PROD_COND 
(
   ID                   INTEGER              not null,
   CRED_PROD_COND_ID    INTEGER              not null,
   CLIENT_AVALIABLE     CHAR(1)              default '1' not null,
   START_DATE           TIMESTAMP            not null,
   PERCENT_USE          CHAR(1)              default '0' not null,
   CURRENCY             VARCHAR2(3)          not null,
   MIN_LIMIT_AMOUNT     NUMBER(15,4),
   MAX_LIMIT_AMOUNT     NUMBER(15,4)         not null,
   MAX_LIMIT_PERCENT    NUMBER(15,4),
   MAX_LIMIT_INCLUDE    CHAR(1)              default '1' not null,
   MIN_PROCENT_RATE     NUMBER(15,4)         not null,
   MAX_PROCENT_RATE     NUMBER(15,4)         not null,
   MIN_LIMIT_CURRENCY   varchar2(3)          not null,
   MAX_LIMIT_CURRENCY   varchar2(3),
   MAX_PROCENT_RATE_INCLUDE char(1)              default '1' not null,
   constraint PK_CURR_CRED_PROD_COND primary key (ID) using index (
      create unique index PK_CURR_CRED_PROD_COND on CURR_CRED_PROD_COND(ID) tablespace INDX  
   )     
)
/

create sequence S_CURR_CRED_PROD_COND
/

create index "DXFK_CCPC_TO_CPC" on CURR_CRED_PROD_COND
(
   CRED_PROD_COND_ID
) tablespace INDX 
/

alter table CURR_CRED_PROD_COND
   add constraint FK_CCPC_TO_CPC foreign key (CRED_PROD_COND_ID)
      references CREDIT_PRODUCT_CONDITION (ID)
      on delete cascade
/


create index "DXFK_CCRC_TO_CURR" on CURR_CRED_PROD_COND
(
   CURRENCY
) tablespace INDX 
/

alter table CURR_CRED_PROD_COND
   add constraint FK_CCRC_TO_CURR foreign key (CURRENCY)
      references CURRENCIES (ID)
      on delete cascade
/

-- Номер ревизии: 57178
-- Комментарий: Оповещения о персональных предложениях(АРМ Сотрудника)
create table PERSONAL_OFFER_NOTIFICATION 
(
   ID                   INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   NAME                 VARCHAR2(100)        not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   TITLE                VARCHAR2(100),
   TEXT                 VARCHAR2(400),
   IMAGE_ID             INTEGER,
   SHOW_TIME            INTEGER,
   ORDER_INDEX          INTEGER,
   PRODUCT_TYPE         VARCHAR2(16)         not null,
   DISPLAY_FREQUENCY    VARCHAR2(16)         not null,
   DISPLAY_FREQUENCY_DAY INTEGER,
   constraint PK_PERSONAL_OFFER_NOTIFICATION primary key (ID) using index (
      create unique index PK_PERSONAL_OFFER_NOTIFICATION on PERSONAL_OFFER_NOTIFICATION(ID) tablespace INDX  
   )    
)
/

create sequence S_PERSONAL_OFFER_NOTIFICATION
/

create table PERSONAL_OFFER_AREAS 
(
   ID                   INTEGER              not null,
   PERSONAL_OFFER_ID    INTEGER,
   LIST_INDEX           INTEGER,
   AREA                 VARCHAR2(10)         not null,
   ORDER_INDEX          INTEGER              not null,
   constraint PK_PERSONAL_OFFER_AREAS primary key (ID) using index (
      create unique index PK_PERSONAL_OFFER_AREAS on PERSONAL_OFFER_AREAS(ID) tablespace INDX  
   )       
)
/

create sequence S_PERSONAL_OFFER_AREAS
/

create index "DXFK_OFFER_AREA_TO_OFFER" on PERSONAL_OFFER_AREAS
(
   PERSONAL_OFFER_ID
) tablespace INDX 
/

alter table PERSONAL_OFFER_AREAS
   add constraint FK_OFFER_AREA_TO_OFFER foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
/

create table PERSONAL_OFFER_BUTTONS 
(
   ID                   INTEGER              not null,
   PERSONAL_OFFER_ID    INTEGER,
   TITLE                VARCHAR2(200),
   URL                  VARCHAR2(256),
   SHOW                 CHAR(1)              not null,
   ORDER_INDEX          INTEGER              not null,
   IMAGE_ID             INTEGER,
   constraint PK_PERSONAL_OFFER_BUTTONS primary key (ID) using index (
      create unique index PK_PERSONAL_OFFER_BUTTONS on PERSONAL_OFFER_BUTTONS(ID) tablespace INDX  
   )    
)
/

create sequence S_PERSONAL_OFFER_BUTTONS
/

create index "DXFK_OFFER_BUTT_TO_OFFER" on PERSONAL_OFFER_BUTTONS
(
   PERSONAL_OFFER_ID
) tablespace INDX 
/

alter table PERSONAL_OFFER_BUTTONS
   add constraint FK_OFFER_BUTT_TO_OFFER foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
/

create table PERSONAL_OFFER_DEPARTMENTS 
(
   PERSONAL_OFFER_ID    INTEGER              not null,
   TB                   VARCHAR2(4)          not null,   
   constraint PK_PERSONAL_OFFER_DEPARTMENTS primary key (PERSONAL_OFFER_ID, TB) using index (
      create unique index PK_PERSONAL_OFFER_DEPARTMENTS on PERSONAL_OFFER_DEPARTMENTS(PERSONAL_OFFER_ID, TB) tablespace INDX  
   )       
)
/

create sequence S_PERSONAL_OFFER_DEPARTMENTS
/

create index "DXFK_OFFER_DEP_TO_OFFER" on PERSONAL_OFFER_DEPARTMENTS
(
   PERSONAL_OFFER_ID
) tablespace INDX 
/

alter table PERSONAL_OFFER_DEPARTMENTS
   add constraint FK_OFFER_DEP_TO_OFFER foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
/

-- Номер ревизии: 57259
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.
create table DICTIONARY_SYNCH_STATE 
(
   LAST_UPDATE_ID       INTEGER,
   LAST_UPDATE_DATE     TIMESTAMP,
   STATE                VARCHAR2(9)          not null,
   ERROR_COUNT          INTEGER              not null
)
/

-- Номер ревизии: 57341
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ
create table DOCUMENT_OPERATIONS_JOURNAL 
(
   EXTERNAL_ID          VARCHAR2(100)        not null,
   DOCUMENT_EXTERNAL_ID VARCHAR2(100)        not null,
   OPERATION_DATE       TIMESTAMP            not null,
   PROFILE_ID           INTEGER              not null,
   AMOUNT               NUMBER(19,4)         not null,
   AMOUNT_CURRENCY      CHAR(3)              not null,
   OPERATION_TYPE       VARCHAR2(20)         not null,
   CHANNEL_TYPE         VARCHAR2(25)         not null,
   LIMITS_INFO          VARCHAR2(4000),
   EXTERNAL_CARD        VARCHAR2(19),
   EXTERNAL_PHONE       VARCHAR2(16)
)
partition by range (OPERATION_DATE) interval (NUMTODSINTERVAL(7,'DAY'))
( 
	partition P_FIRST values less than (to_date('02-03-2014','DD-MM-YYYY'))
)
/

create index DOC_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   DOCUMENT_EXTERNAL_ID ASC,
   OPERATION_DATE ASC
)
local tablespace INDX 
/

create index PROFILE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   PROFILE_ID ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local tablespace INDX 
/

create index PHONE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   EXTERNAL_PHONE ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local tablespace INDX 
/

create index CARD_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   EXTERNAL_CARD ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local tablespace INDX 
/

-- Номер ревизии: 57450
-- Комментарий: История изменений профиля в ЕРИБ
create table USER_KEY_HISTORY 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   FIRST_NAME           VARCHAR2(120)        not null,
   SUR_NAME             VARCHAR2(120)        not null,
   PATR_NAME            VARCHAR2(120),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   DATE_CREATED         TIMESTAMP            not null,
   constraint PK_USER_KEY_HISTORY primary key (ID) using index (
      create unique index PK_USER_KEY_HISTORY on USER_KEY_HISTORY(ID) tablespace INDX  
   )       
)
/

create sequence S_USER_KEY_HISTORY
/

create index "DXUSER_KEY_TO_LOGINS" on USER_KEY_HISTORY(
   LOGIN_ID
) tablespace INDX 
/

-- Номер ревизии: 57630 
-- Комментарий: Доработка отображения сумм комиссий
create table COMMISSIONS_SETTINGS 
(
   ID                   INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   PAYMENT_TYPE         VARCHAR2(200)        not null,
   SHOW_COMMISSION      CHAR(1)              not null,
   constraint PK_COMMISSIONS_SETTINGS primary key (ID) using index (
      create unique index PK_COMMISSIONS_SETTINGS on COMMISSIONS_SETTINGS(ID) tablespace INDX  
   )      
)
/

create sequence S_COMMISSIONS_SETTINGS
/

create index CS_TB_PAYTYPE on COMMISSIONS_SETTINGS (
   TB ASC,
   PAYMENT_TYPE ASC
) tablespace INDX 
/

-- Номер ревизии: 57677
-- Комментарий: MNP. Хранение настроек и проверка поставщиков
create table MOBILE_PROVIDER_CODES 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(20)         not null,
   NAME                 VARCHAR2(160)        not null,
   EXTERNAL_ID          VARCHAR2(128)        not null,
   constraint PK_MOBILE_PROVIDER_CODES primary key (ID) using index (
      create unique index PK_MOBILE_PROVIDER_CODES on MOBILE_PROVIDER_CODES(ID) tablespace INDX  
   )       
)
/
create sequence S_MOBILE_PROVIDER_CODES
/

create unique index MOBILE_PROVIDER_CODES_EXT_ID on MOBILE_PROVIDER_CODES (
   EXTERNAL_ID ASC
) tablespace INDX 
/

-- Номер ревизии:  57706
-- Комментарий: Оповещения о персональных предложениях(ч.3)
create table PERSONAL_OFFER_DISPLAY_DATE 
(
   PERSONAL_OFFER_ID    INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DISPLAY_DATE         TIMESTAMP            not null,
   constraint PK_PERSONAL_OFFER_DISPLAY_DATE primary key (PERSONAL_OFFER_ID, LOGIN_ID) using index (
      create unique index PK_PERSONAL_OFFER_DISPLAY_DATE on PERSONAL_OFFER_DISPLAY_DATE(PERSONAL_OFFER_ID, LOGIN_ID) tablespace INDX  
   )
)
/

create sequence S_PERSONAL_OFFER_DISPLAY_DATE
/

create index "DXFK_OFFER_TO_DATE" on PERSONAL_OFFER_DISPLAY_DATE
(
   PERSONAL_OFFER_ID
) tablespace INDX 
/

create index "DXFK_OFFER_TO_LOGINS" on PERSONAL_OFFER_DISPLAY_DATE
(
   LOGIN_ID
) tablespace INDX 
/

-- Номер ревизии: 58237
-- Комментарий: Таблицы для EInvoicing
create table SHOP_NOTIFICATIONS 
(
   CREATE_DATE          TIMESTAMP            not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(30)         not null,
   NOTIF_STATUS         VARCHAR2(30)         not null,
   NOTIF_DATE           TIMESTAMP,
   NOTIF_COUNT          INTEGER              not null,
   NOTIF_STATUS_DESCRIPTION VARCHAR2(255),
   RECEIVER_CODE        VARCHAR2(32)         not null
)
partition by range (CREATE_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_FIRST_SN values less than (to_date('01-01-2012','DD-MM-YYYY'))
)
/
create sequence S_SHOP_NOTIFICATIONS
/
create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   CREATE_DATE ASC
)
local tablespace INDX 
/

create table SHOP_ORDERS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   TYPE                 CHAR(1)              not null,
   ORDER_DATE           TIMESTAMP            not null,
   STATE                VARCHAR2(20)         not null,
   PROFILE_ID           INTEGER,
   PHONE                VARCHAR2(10),
   RECEIVER_CODE        VARCHAR2(32)         not null,
   RECEIVER_NAME        VARCHAR2(160),
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   DESCRIPTION          VARCHAR2(255),
   RECEIVER_ACCOUNT     VARCHAR2(24),
   BIC                  VARCHAR2(9),
   CORR_ACCOUNT         VARCHAR2(35),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(9),
   PRINT_DESCRIPTION    VARCHAR2(200),
   BACK_URL             VARCHAR2(1000),
   NODE_ID              INTEGER,
   UTRRNO               VARCHAR2(99),
   DETAIL_INFO          CLOB,
   TICKETS_INFO         CLOB,
   KIND                 VARCHAR2(20)         not null
)
partition by range (ORDER_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_FIRST_SO values less than (to_date('01-01-2012','DD-MM-YYYY'))
)
/
create sequence S_SHOP_ORDERS
/

create index IDX_SHOP_ORDERS_ID on SHOP_ORDERS (
   ID ASC
)
local tablespace INDX 
/
create unique index IDX_SH_ORD_EXT_ID_REC_CODE on SHOP_ORDERS (
   EXTERNAL_ID ASC,
   RECEIVER_CODE ASC
)
global tablespace INDX 
/
create index IDX_SH_ORD_PROF_ID_DATE on SHOP_ORDERS (
   PROFILE_ID ASC,
   ORDER_DATE DESC
)
local tablespace INDX 
/
create unique index IDX_SHOP_ORD_UUID on SHOP_ORDERS (
   UUID ASC
) global tablespace INDX 
/

create table SHOP_PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_SHOP_PROFILES primary key (ID) using index (
      create unique index PK_SHOP_PROFILES on SHOP_PROFILES(ID) tablespace INDX  
   )   
)
/

create sequence S_SHOP_PROFILES
/

create unique index IDX_SHOP_PROFILE_UNIQ on SHOP_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT, ' ', '') ASC
) tablespace INDX 
/

create table SHOP_RECALLS 
(
   UUID                 VARCHAR2(32)         not null,
   RECEIVER_CODE        VARCHAR2(32)         not null,
   RECALL_DATE          TIMESTAMP            not null,
   ORDER_UUID           VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(20)         not null,
   TYPE                 VARCHAR2(10)         not null,
   constraint PK_SHOP_RECALLS primary key (UUID) using index (
      create unique index PK_SHOP_RECALLS on SHOP_RECALLS(UUID) tablespace INDX  
   ) 
)
partition by range ("RECALL_DATE") interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_FIRST_SR values less than (to_date('01-01-2012','DD-MM-YYYY'))
)
/

create sequence S_SHOP_RECALLS
/

create index IDX_SH_RECALLS_ORDER_UUID on SHOP_RECALLS (
   ORDER_UUID ASC
)
local
/

create index "DXREFERENCE_8" on SHOP_ORDERS
(
   PROFILE_ID
) tablespace INDX 
/

alter table SHOP_ORDERS
   add constraint FK_SHOP_ORD_REFERENCE_SHOP_PRO foreign key (PROFILE_ID)
      references SHOP_PROFILES (ID)
/

-- Номер ревизии: 58278
-- Комментарий: Изменение реализации справочников TSM

 -- Справочник «Категория занимаемой должности»
create table LOANCLAIM_CATEGORY_OF_POSITION
(
	CODE     VARCHAR2(20 BYTE)                    NOT NULL,
	NAME     VARCHAR2(100 BYTE)                   NOT NULL,
	MAX_AGE  INTEGER                              NOT NULL,
	constraint LC_CATEGORY_OF_POSITION_PK primary key (CODE) using index (
		create unique index LC_CATEGORY_OF_POSITION_PK on LOANCLAIM_CATEGORY_OF_POSITION(CODE) tablespace INDX  
	)    
)
/

 -- Справочник «Образование»
create table LOANCLAIM_EDUCATION 
(
	CODE                 VARCHAR2(20)         not null,
	NAME                 VARCHAR2(100)        not null,
	HIGH_EDUCATION_COURSE_REQUIRED CHAR(1)    not null,
	constraint LC_EDUCATION_PK primary key (CODE) using index (
		create unique index LC_EDUCATION_PK on LOANCLAIM_EDUCATION(CODE) tablespace INDX  
	) 
) 

 -- Справочник «Родственная связь»
create table LOANCLAIM_FAMILY_RELATION
(
	CODE      VARCHAR2(20 BYTE)                   NOT NULL,
	NAME      VARCHAR2(100 BYTE)                  NOT NULL,
	IS_CHILD  CHAR(1 BYTE)                        NOT NULL,
	constraint LC_FAMILY_RELATION_PK primary key (CODE) using index (
		create unique index LC_FAMILY_RELATION_PK on LOANCLAIM_FAMILY_RELATION(CODE) tablespace INDX  
	)     
)
/

 -- Справочник «Семейное положение»
create table LOANCLAIM_FAMILY_STATUS
(
	CODE                  VARCHAR2(20 BYTE)       NOT NULL,
	NAME                  VARCHAR2(100 BYTE)      NOT NULL,
	SPOUSE_INFO_REQUIRED  CHAR(1 BYTE)            NOT NULL,
	constraint LC_FAMILY_STATUS_PK primary key (CODE) using index (
		create unique index LC_FAMILY_STATUS_PK on LOANCLAIM_FAMILY_STATUS(CODE) tablespace INDX  
	)    
)
/

 --Справочник кодов организационно - правовых форм
create table LOANCLAIM_INCORPORATION_FORM
(
	CODE        VARCHAR2(20 BYTE)                 NOT NULL,
	NAME        VARCHAR2(100 BYTE)                NOT NULL,
	SHORT_NAME  VARCHAR2(50 BYTE)                 NOT NULL,
	constraint LC_INCORPORATION_FORM_PK primary key (CODE) using index (
		create unique index LC_INCORPORATION_FORM_PK on LOANCLAIM_INCORPORATION_FORM(CODE) tablespace INDX  
	)   
)
/

 -- Справочник «Стаж на текущем месте работы»
create table LOANCLAIM_JOB_EXPERIENCE
(
	CODE              VARCHAR2(20 BYTE)           NOT NULL,
	NAME              VARCHAR2(100 BYTE)          NOT NULL,
	LOAN_NOT_ALLOWED  CHAR(1 BYTE)                NOT NULL,
	constraint LC_JOB_EXPERIENCE_PK primary key (CODE) using index (
		create unique index LC_JOB_EXPERIENCE_PK on LOANCLAIM_JOB_EXPERIENCE(CODE) tablespace INDX  
	)    
)
/

 -- Справочник «Вид деятельности компании»
create table LOANCLAIM_KIND_OF_ACTIVITY
(
	CODE           VARCHAR2(20 BYTE)              NOT NULL,
	NAME           VARCHAR2(100 BYTE)             NOT NULL,
	DESC_REQUIRED  CHAR(1 BYTE)                   NOT NULL,
	constraint LC_KIND_OF_ACTIVITY_PK primary key (CODE) using index (
		create unique index LC_KIND_OF_ACTIVITY_PK on LOANCLAIM_KIND_OF_ACTIVITY(CODE) tablespace INDX  
	)  	
)
/

 -- Справочник «Способ погашения кредита»
create table LOANCLAIM_PAYMENT_METHOD
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_PAYMENT_METHOD_PK primary key (CODE) using index (
		create unique index LC_PAYMENT_METHOD_PK on LOANCLAIM_PAYMENT_METHOD(CODE) tablespace INDX  
	)  
)
/
 
 -- Справочник «Периодичность погашения кредита»
create table LOANCLAIM_PAYMENT_PERIOD
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_PAYMENT_PERIOD_PK primary key (CODE) using index (
		create unique index LC_PAYMENT_PERIOD_PK on LOANCLAIM_PAYMENT_PERIOD(CODE) tablespace INDX  
	)  
)
/

 -- Справочник «Приблизительное количество сотрудников в компании»
create table LOANCLAIM_NUMBER_OF_EMPLOYEES
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_NUMBER_OF_EMPLOYEES_PK primary key (CODE) using index (
		create unique index LC_NUMBER_OF_EMPLOYEES_PK on LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE) tablespace INDX  
	)   
)
/
 
 -- Справочник «Регионы»
create table LOANCLAIM_REGION
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_REGION_PK primary key (CODE) using index (
		create unique index LC_REGION_PK on LOANCLAIM_REGION(CODE) tablespace INDX  
	)  	
)
/

 -- Справочник «Вид собственности жилья»
create table LOANCLAIM_RESIDENCE_RIGHT
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	NEED_REALTY_INFO CHAR(1 BYTE)       DEFAULT 0 NOT NULL,
	constraint LC_RESIDENCE_RIGHT_PK primary key (CODE) using index (
		create unique index LC_RESIDENCE_RIGHT_PK on LOANCLAIM_RESIDENCE_RIGHT(CODE) tablespace INDX  
	)	
)
/

 -- Справочник «Типы районов/округов»
create table LOANCLAIM_TYPE_OF_AREA
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_AREA_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_AREA_PK on LOANCLAIM_TYPE_OF_AREA(CODE) tablespace INDX  
	)  
)
/

 -- Справочник «Типы городов»
create table LOANCLAIM_TYPE_OF_CITY
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_CITY_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_CITY_PK on LOANCLAIM_TYPE_OF_CITY(CODE) tablespace INDX  
	)    
)
/

 -- Справочник «Типы обязательств»
create table LOANCLAIM_TYPE_OF_DEBIT
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_DEBIT_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_DEBIT_PK on LOANCLAIM_TYPE_OF_DEBIT(CODE) tablespace INDX  
	)  
)
/

 -- Справочник «Типы населенных пунктов»
create table LOANCLAIM_TYPE_OF_LOCALITY
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_LOCALITY_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_LOCALITY_PK on LOANCLAIM_TYPE_OF_LOCALITY(CODE) tablespace INDX  
	)  
)
/

 -- Справочник «Вид недвижимости в собственности»
create table LOANCLAIM_TYPE_OF_REALTY
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_REALTY_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_REALTY_PK on LOANCLAIM_TYPE_OF_REALTY(CODE) tablespace INDX  
	)    
)
/

 -- Справочник «Типы улиц»
create table LOANCLAIM_TYPE_OF_STREET
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_STREET_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_STREET_PK on LOANCLAIM_TYPE_OF_STREET(CODE) tablespace INDX  
	)     
)
/

 -- Справочник «Вид транспортного средства в собственности»
create table LOANCLAIM_TYPE_OF_VEHICLE
(
	CODE  VARCHAR2(20 BYTE)                       NOT NULL,
	NAME  VARCHAR2(100 BYTE)                      NOT NULL,
	constraint LC_TYPE_OF_VEHICLE_PK primary key (CODE) using index (
		create unique index LC_TYPE_OF_VEHICLE_PK on LOANCLAIM_TYPE_OF_VEHICLE(CODE) tablespace INDX  
	)   	
)
/

 -- Справочник «Работа по трудовому договору»
create table LOANCLAIM_WORK_ON_CONTRACT
(
	CODE                    VARCHAR2(20 BYTE)     NOT NULL,
	NAME                    VARCHAR2(100 BYTE)    NOT NULL,
	FULL_NAME_REQUIRED      CHAR(1)               NOT NULL,
	INN_REQUIRED            CHAR(1)               NOT NULL,
	BUSINESS_DESC_REQUIRED  CHAR(1)               NOT NULL,
	UNEMPLOYED              CHAR(1)               NOT NULL,
	constraint LC_WORK_ON_CONTRACT_PK primary key (CODE) using index (
		create unique index LC_WORK_ON_CONTRACT_PK on LOANCLAIM_WORK_ON_CONTRACT(CODE) tablespace INDX  
	)   
)
/

-- Справочник "Соответствие подразделений ЦАС НСИ и системы ЕТСМ"
create table CASNSI_TO_ETSM_DEP
(
	TB_CASNSI            VARCHAR2(12)         not null,
	TB_ETSM              VARCHAR2(12)         not null,
	OSB_CASNSI           VARCHAR2(12)         not null,
	OSB_ETSM             VARCHAR2(12)         not null,
	constraint CASNSI_TO_ETSM_DEP_PK primary key (TB_CASNSI, OSB_CASNSI) using index (
		create unique index CASNSI_TO_ETSM_DEP_PK on CASNSI_TO_ETSM_DEP(TB_CASNSI, OSB_CASNSI) tablespace INDX  
	)    
)
/

create sequence S_CASNSI_TO_ETSM_DEP
/

-- Заполнение справочников


INSERT INTO CASNSI_TO_ETSM_DEP(TB_CASNSI, TB_ETSM, OSB_CASNSI, OSB_ETSM) VALUES ('38', '99', '9038', '7811');


insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('1', 'Руководитель высшего звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('2', 'Руководитель среднего звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('3', 'Руководитель начального звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('4', 'Владелец предприятия/ген.Директор/Главный бухгалтер', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('5', 'Высококвалифицированный специалист', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('6', 'Специалист', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('7', 'Военнослужащий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('8', 'Рабочий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('9', 'Служащий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('10', 'Судья', 70); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('11', 'Нотариус', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('12', 'Государственный гражданский служащий', 65); 

insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('1', 'Ученая степень / MBA', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('2', 'Второе высшее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('3', 'Высшее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('4', 'Незаконченное высшее', 1);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('5', 'Среднее специальное', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('6', 'Среднее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('7', 'Ниже среднего', 0);


insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('1', 'Мать', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('2', 'Отец', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('3', 'Брат', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('4', 'Сестра', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('5', 'Сын', 1); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('6', 'Дочь', 1);
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('7', 'Иное', 0); 

insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', 'Холост/не замужем', 0); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', 'В разводе', 0); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', 'Женат/замужем', 1); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', 'Вдовец/вдова', 0); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', 'Гражданский брак', 1);

insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('005', 'Предприятие потребительской кооперации', 'Потреб.КООП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('002', 'Открытое акционерное общество', 'ОАО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('003', 'Закрытое акционерное общество', 'ЗАО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('006', 'Полное товарищество', 'Полное товарищество'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('007', 'Товарищество на вере', 'Товарищество на вере'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('008', 'Производственный кооператив', 'Произв. КООП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('009', 'Крестьянское (фермерское) хозяйство', 'КФХ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('001', 'Общество с ограниченной ответственностью', 'ООО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('012', 'Общество с дополнительной ответственностью', 'ОДО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('013', 'Унитарное предприятие на праве хозяйственного ведения', 'УПОнПХВ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('014', 'Унитарное предприятие на праве оперативного управления', 'УПОнПОУ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('015', 'Дочернее унитарное предприятие', 'ДУП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('016', 'Общественная (религиозная) организация (объединение)', 'ОРО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('017', 'Общественное движение', 'ОД'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('018', 'Фонд', 'ФОНД'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('019', 'Учреждение', 'УЧРЕЖДЕНИЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('020', 'Государственное корпорация', 'ГК'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('021', 'Орган общественной самодеятельности', 'ООС'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('022', 'Некоммерческое партнерство', 'НКП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('023', 'Автономная некоммерческая организация', 'АНО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('024', 'Объединение юридических лиц (ассоциация или союз)', 'ОЮЛ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('025', 'Ассоциация крестьянских (фермерских) хозяйств', 'АКФХ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('026', 'Территориальное общественное самоуправление', 'ТОС'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('027', 'Товарищество собственников жилья', 'ТСЖ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('028', 'Садовод., огород.или дачные некоммерческие товарищества', 'ТОВРИЩЕСТВА'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('029', 'Прочая некоммерческия организация', 'ПРОЧИЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('030', 'Финансово - промышленные группы', 'ФПГ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('031', 'Паевые инвестиционные фонды', 'ПИФ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('032', 'Простые товарищества', 'ПТ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('999', 'Представительства и филиалы', 'ПРЕДСТАВИТЕЛЬСТВА'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('034', 'Иные неюридические лица', 'ИНЫЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('033', 'Индивидуальные предприниматели (ПБЮЛ)', 'ИП');

insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('1', 'от 6 до 12 мес', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('2', 'от 1 года до 3 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('3', 'от 3 до 5 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('4', 'от 5 до 10 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('5', 'от 10 до 20 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('6', 'более 20 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('7', 'менее 6 месяцев', 1);

insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('1', 'Финансы, банки, страхование', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('2', 'Консалтинговые услуги', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('3', 'Армия', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('4', 'Промышленность и машиностроение', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('5', 'Предприятия ТЭК', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('6', 'Металлургия', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('7', 'Оптовая / розничная торговля (уточнение)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('8', 'Услуги (уточнение)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('9', 'Транспорт', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('10', 'Охранная деятельность', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('11', 'Туризм', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('12', 'Образование', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('13', 'Медицина', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('14', 'Культура и искусство', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('15', 'Органы власти и управления', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('16', 'Социальная сфера', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('17', 'Информационные технологии / телекоммуникации', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('18', 'Строительство', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('19', 'Наука', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('20', 'Другие отрасли (уточнение)', 1);

insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('2', 'Аннуитетный'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('0', 'Дифференцированный'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('9', 'Иное'); 

insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('0', 'Ежемесячно'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('1', 'Ежеквартально'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('2', 'Индивидуальный график'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('3', 'Ежегодно'); 

insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('1', 'До 10'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('2', '11-30'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('3', '31-50'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('4', '51-100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('5', 'Более 100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('6', 'Затрудняюсь ответить'); 

insert into LOANCLAIM_REGION(CODE, NAME) values ('0001', 'Республика Адыгея '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0002', 'Республика Башкортостан '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0003', 'Республика Бурятия '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0004', 'Республика Алтай '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0005', 'Республика Дагестан '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0006', 'Ингушская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0007', 'Кабардино - Балкарская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0008', 'Республика Калмыкия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0009', 'Карачаево - Черкесская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0010', 'Республика Карелия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0011', 'Республика Коми'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0012', 'Республика Марий Эл'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0013', 'Республика Мордовия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0014', 'Республика Саха (Якутия)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0015', 'Республика Северная Осетия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0016', 'Республика Татарстан (Татарстан)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0017', 'Республика Тува '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0018', 'Удмуртская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0019', 'Республика Хакасия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0020', 'Чеченская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0021', 'Чувашская Республика- Чаваш Республики '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0022', 'Алтайский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0023', 'Краснодарский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0024', 'Красноярский край '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0025', 'Приморский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0026', 'Ставропольский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0027', 'Хабаровский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0028', 'Амурская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0029', 'Архангельская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0030', 'Астраханская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0031', 'Белгородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0032', 'Брянская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0033', 'Владимирская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0034', 'Волгоградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0035', 'Вологодская область '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0036', 'Воронежская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0037', 'Ивановская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0038', 'Иркутская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0039', 'Калининградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0040', 'Калужская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0041', 'Камчатская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0042', 'Кемеровская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0043', 'Кировская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0044', 'Костромская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0045', 'Курганская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0046', 'Курская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0047', 'Ленинградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0048', 'Липецкая область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0049', 'Магаданская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0050', 'Московская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0051', 'Мурманская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0052', 'Нижегородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0053', 'Новгородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0054', 'Новосибирская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0055', 'Омская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0056', 'Оренбургская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0057', 'Орловская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0058', 'Пензенская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0059', 'Пермская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0060', 'Псковская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0061', 'Ростовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0062', 'Рязанская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0063', 'Самарская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0064', 'Саратовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0065', 'Сахалинская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0066', 'Свердловская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0067', 'Смоленская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0068', 'Тамбовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0069', 'Тверская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0070', 'Томская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0071', 'Тульская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0072', 'Тюменская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0073', 'Ульяновская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0074', 'Челябинская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0075', 'край Забайкальский'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0076', 'Ярославская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0077', 'г. Москва'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0078', 'г. Санкт – Петербург'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0079', 'Еврейская автономная область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0080', 'Агинский Бурятский автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0081', 'Коми - Пермяцкий автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0082', 'Корякский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0083', 'Ненецкий автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0084', 'Таймырский (Долгано - Ненецкий) автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0085', 'Усть - Ордынский Бурятский автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0086', 'Ханты - Мансийский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0087', 'Чукотский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0088', 'Эвенкийский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0089', 'Ямало - Ненецкий автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('9901', 'город и космодром Байконур '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0099', 'Иное (только для адресов, расположенных вне Российской Федерации');

insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('0', 'Собственная квартира',1); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('1', 'У родственников',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('2', 'Соц. Найм',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('3', 'Аренда',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('4', 'Коммунальная квартира',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('5', 'Общежитие',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('6', 'Воинская часть',0);


insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('201', 'Р-н'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('230', 'Тер'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('202', 'У'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('204', 'Кожуун'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('205', 'АО'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('103', 'г'); 

insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('310', 'Волость'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('301', 'Г'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('305', 'Дп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('304', 'Кп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('311', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('302', 'пгт'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('303', 'рп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('307', 'с/а'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('309', 'с/о'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('306', 'с/с'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('312', 'тер'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('313', 'сумон'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('314', 'с/п'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('315', 'с/мо'); 

insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('1', 'Автокредит'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('2', 'Ипотечный'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('3', 'Потребительский'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('4', 'Кредитная карта'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('5', 'Поручительство'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('6', 'Другое'); 

insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('401', 'аал'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('402', 'аул'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('403', 'волость'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('404', 'высел'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('405', 'г'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('436', 'городок'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('406', 'д'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('407', 'дп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('408', 'ж/д будка'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('409', 'ж/д казарм'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('410', 'ж/д оп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('411', 'ж/д пост'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('412', 'ж/д разд'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('438', 'ж/д платф'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('413', 'ж/д ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('414', 'заимка'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('415', 'казарма'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('416', 'кп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('417', 'м'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('418', 'мкр'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('419', 'нп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('420', 'остров'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('421', 'п'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('426', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('422', 'п/р'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('423', 'п/ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('424', 'пгт'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('425', 'починок'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('427', 'промзона'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('428', 'рзд'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('429', 'рп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('430', 'с'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('431', 'сл'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('432', 'ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('433', 'ст-ца'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('437', 'тер'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('434', 'у'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('435', 'х'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('439', 'кв-л'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('440', 'арбан'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('441', 'снт'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('442', 'лпх'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('443', 'погост'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('444', 'кордон'); 

insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('1', 'Комната'); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('2', 'Квартира'); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('3', 'Дом'); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('4', 'Участок'); 

insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('529', 'Ул'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('532', 'аал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('501', 'Аллея'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('533', 'Аул'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('502', 'б-р'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('503', 'Въезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('534', 'Высел'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('535', 'Городок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('536', 'Д'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('504', 'Дор'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('537', 'ж/д будка'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('538', 'ж/д казарм'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('539', 'ж/д оп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('540', 'ж/д пост'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('541', 'ж/д рзд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('542', 'ж/д ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('559', 'ж/д платф'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('505', 'Жт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('506', 'Заезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('543', 'Казарма'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('507', 'кв-л'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('508', 'Км'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('509', 'Кольцо'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('510', 'Линия'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('544', 'М'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('545', 'Мкр'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('511', 'Наб'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('546', 'Нп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('512', 'Остров'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('548', 'П'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('549', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('550', 'п/р'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('551', 'п/ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('513', 'Парк'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('514', 'Пер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('515', 'Переезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('516', 'Пл'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('547', 'Платф'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('517', 'пл-ка'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('552', 'Полустанок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('553', 'Починок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('519', 'пр-кт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('518', 'Проезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('520', 'просек'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('521', 'Проселок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('522', 'Проулок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('554', 'Рзд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('555', 'С'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('523', 'Сад'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('524', 'Сквер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('556', 'Сл'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('557', 'Ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('525', 'Стр'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('526', 'Тер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('527', 'Тракт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('528', 'Туп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('530', 'уч-к'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('558', 'Х'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('531', 'Ш'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('560', 'Арбан'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('561', 'Спуск'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('562', 'Канал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('563', 'Гск'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('564', 'Снт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('565', 'Лпх'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('566', 'Проток'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('567', 'коса'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('568', 'вал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('569', 'ферма'); 

insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('1', 'Наземное ТС'); 
insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('2', 'Водное ТС'); 

-- Работа по трудовому договору
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('1', 'Срочный контракт', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('2', 'Без срока (постоянная занятость)', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('3', 'Частная практика (уточнить)', 0, 0, 1, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('4', 'Индивидуальный предприниматель', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('5', 'Агент на комиссионном договоре', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('6', 'Пенсионер', 0, 0, 0, 1); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('7', 'Исполнитель по гражданско-правовому договору', 1, 1, 0, 0); 
 

-- Номер ревизии:  58459
-- Комментарий:  CHG065977: Необходимо информировать клиента, если операция производится по льготному курсу.
create table TARIF_PLAN_CONFIGS 
(
	ID                   INTEGER              not null,
	TARIF_PLAN_CODE      VARCHAR(50)          not null,
	NEED_SHOW_STANDART_RATE CHAR(1),
	PRIVILEGED_RATE_MESSAGE VARCHAR(2000),
	constraint PK_TARIF_PLAN_CONFIGS primary key (ID) using index (
		create unique index PK_TARIF_PLAN_CONFIGS on TARIF_PLAN_CONFIGS(ID) tablespace INDX  
	)   
)
/

create sequence S_TARIF_PLAN_CONFIGS
/

-- Номер ревизии: 58270
-- Комментарий: Сохранение результатов загрузки предодобренных предложений.
create table LOAN_OFFER_LOAD_RESULTS 
(
   ID                   INTEGER              not null,
   ALL_COUNT            INTEGER              not null,
   LOAD_COUNT           INTEGER              not null,
   LOAD_OFFER_ERRORS    CLOB,
   LOAD_COMMON_ERRORS   CLOB,
   constraint PK_LOAN_OFFER_LOAD_RESULTS primary key (ID) using index (
     create unique index PK_LOAN_OFFER_LOAD_RESULTS on LOAN_OFFER_LOAD_RESULTS(ID) tablespace INDX  
   ) 
)
/
create sequence S_LOAN_OFFER_LOAD_RESULTS
/

create table PFP_P_PRODUCT_TARGET_GROUPS 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_P_PRODUCT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE) using index (
     create unique index PK_PFP_P_PRODUCT_TARGET_GROUPS on PFP_P_PRODUCT_TARGET_GROUPS(PRODUCT_ID, SEGMENT_CODE) tablespace INDX  
   )   
)
/
create index "DXFK_PFP_TARGET_GR_TO_P_PRODUC" on PFP_P_PRODUCT_TARGET_GROUPS
(
   PRODUCT_ID
) tablespace INDX  
/

/*==============================================================*/
/* Table: PAYMENTS_GROUP_RISK                                   */
/*==============================================================*/
create table PAYMENTS_GROUP_RISK 
(
   ID                   INTEGER              not null,
   KIND                 VARCHAR2(3)          not null,
   GROUP_RISK_ID        INTEGER,
   TB                   VARCHAR2(4)          not null,
   constraint PK_PAYMENTS_GROUP_RISK primary key (ID) using index (
     create unique index PK_PAYMENTS_GROUP_RISK on PAYMENTS_GROUP_RISK(ID) tablespace INDX     
)
/

create sequence S_PAYMENTS_GROUP_RISK
/

create index "DXFK_TO_PAY_GR_RSK_GROUP_RISK" on PAYMENTS_GROUP_RISK
(
   GROUP_RISK_ID
)
/

alter table PAYMENTS_GROUP_RISK
   add constraint FK_TO_PAY_GR_RSK_GROUP_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
/