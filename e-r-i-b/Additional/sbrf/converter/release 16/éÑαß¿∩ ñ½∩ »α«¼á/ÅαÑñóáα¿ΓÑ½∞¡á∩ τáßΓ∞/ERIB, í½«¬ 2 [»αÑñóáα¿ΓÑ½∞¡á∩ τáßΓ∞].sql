/*
	Схема: SRB_IKFL2
	Табличное пространство таблиц: USER_METADATA, USER_DATA
	Табличное пространство индексов: USER_METADATA_IDX, USER_DATA_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = SRB_IKFL2
/

-- Номер ревизии: 71700
-- Комментарий: Загрузка справочника "Возрастные ограничения клиентов для включения в сегмент"
create table SRB_IKFL2.AGE_REQUIREMENT 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(10 BYTE)    not null,
   DATE_BEGIN           TIMESTAMP(6)         not null,
   LOW_LIMIT_FEMALE     INTEGER,
   LOW_LIMIT_MALE       INTEGER,
   TOP_LIMIT            INTEGER,
   constraint PK_AGE_REQUIREMENT primary key (ID) using index (
      create unique index SRB_IKFL2.PK_AGE_REQUIREMENT on SRB_IKFL2.AGE_REQUIREMENT(ID) tablespace USER_METADATA_IDX
   )
) tablespace USER_METADATA
/

create sequence SRB_IKFL2.S_AGE_REQUIREMENT
/

-- Номер ревизии: 71910
-- Комментарий: Докомит (BKI_DURATION_UNITS)
create table SRB_IKFL2.BKI_DURATION_UNITS (
   CODE                 varchar2(2)          not null,
   NAME                 varchar2(20)         not null,
   constraint PK_BKI_DURATION_UNITS primary key (CODE) using index (
      create unique index SRB_IKFL2.PK_BKI_DURATION_UNITS on SRB_IKFL2.BKI_DURATION_UNITS(CODE) tablespace USER_METADATA_IDX   
   )
) tablespace USER_METADATA
/

create sequence SRB_IKFL2.S_BKI_DURATION_UNITS
/

-- Номер ревизии: 72052
-- Комментарий: Применение промо-кодов для открытия промо-вкладов.
create table SRB_IKFL2.PROMO_CODES_DEPOSIT 
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
   constraint PK_PROMO_CODES_DEPOSIT primary key (ID) using index (
      create unique index SRB_IKFL2.PK_PROMO_CODES_DEPOSIT on SRB_IKFL2.PROMO_CODES_DEPOSIT(ID) tablespace USER_METADATA_IDX   
   )
) tablespace USER_METADATA
/

create sequence SRB_IKFL2.S_PROMO_CODES_DEPOSIT
/

comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.CODE is 'Уникальный код «промо»'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.CODE_G is 'Код промо-акции'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.MASK is 'Маска промо- кода'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.CODE_S is 'Код сегмента клиента'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.DATE_BEGIN is 'Дата начала периода активации промо- кода'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.DATE_END is 'Дата окончания периода активации промо-кода'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.SROK_BEGIN is 'Срок действия промо-кода, начиная со дня ввода клиентом'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.SROK_END is 'Срок действия промо-кода, начиная со дня окончания периода активации промо-кода'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.NUM_USE is 'Возможное количество раз использования клиентом'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.PRIORITY is 'Приоритет условий с промо-кодом «0» - используются ставки для промо-кода; «1» - отображается как отдельный вклад'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.AB_REMOVE is 'Возможность удаления введенного промо-кода:«0» - нельзя удалять;«1» - можно удалять'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.ACTIVE_COUNT is 'Количество действующих промо- кодов у клиента'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.HIST_COUNT is 'Количество введенных промо - кодов клиентом'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.NAME_ACT is 'Описание промо акции'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.NAME_S is 'Краткое описание промо-кода'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.NAME_F is 'Подробное описание промо-кода'
/
comment on column SRB_IKFL2.PROMO_CODES_DEPOSIT.UUID is 'Кросблочный ID'
/

create unique index SRB_IKFL2.IDX_PROMO_CODES_DEP_UUID on SRB_IKFL2.PROMO_CODES_DEPOSIT (
   UUID ASC
) tablespace USER_METADATA_IDX      
/

create unique index SRB_IKFL2.IDX_PROMO_CODES_DEP_CODE on SRB_IKFL2.PROMO_CODES_DEPOSIT (
   CODE ASC
) tablespace USER_METADATA_IDX      
/

-- Номер ревизии: 72437
-- Комментарий: Добавление таблицы вопросов для заявки на кредит.
create table SRB_IKFL2.LOAN_CLAIM_QUESTION 
(
   ID                   INTEGER              not null,
   QUESTION             VARCHAR2(500)        not null,
   ANSWER_TYPE          INTEGER              not null,
   STATUS               INTEGER,
   constraint PK_LOAN_CLAIM_QUESTION primary key (ID) using index (
		create unique index SRB_IKFL2.PK_LOAN_CLAIM_QUESTION on SRB_IKFL2.LOAN_CLAIM_QUESTION(ID) tablespace USER_METADATA_IDX      
   )
) tablespace USER_METADATA
/

create sequence SRB_IKFL2.S_LOAN_CLAIM_QUESTION
/

create index SRB_IKFL2.IDX_ID_STATUS on SRB_IKFL2.LOAN_CLAIM_QUESTION (
   ID ASC,
   STATUS ASC
) tablespace USER_METADATA_IDX  
/

create index SRB_IKFL2.IDX_STATUS on SRB_IKFL2.LOAN_CLAIM_QUESTION (
   STATUS ASC
) tablespace USER_METADATA_IDX  
/

-- Номер ревизии: 72543
-- Комментарий: Розничный CRM. Модель БД предодобренных предложений
create table SRB_IKFL2.CRM_OFFER_LOGINS 
(
   LOGIN_ID             INTEGER              not null,
   LAST_RQ_UID          VARCHAR2(32)         not null,
   LAST_RQ_TIME         TIMESTAMP            not null,
   LAST_RQ_STATUS       VARCHAR2(20)         not null,
   constraint CRM_OFFER_LOGINS_PK primary key (LOGIN_ID) using index (
		create unique index SRB_IKFL2.CRM_OFFER_LOGINS_PK on SRB_IKFL2.CRM_OFFER_LOGINS(LOGIN_ID) tablespace USER_DATA_IDX      
   )
) tablespace USER_DATA
/

alter table SRB_IKFL2.CRM_OFFER_LOGINS
   add constraint FK_CRM_OFLOGIN_TO_LOGIN foreign key (LOGIN_ID)
      references SRB_IKFL2.LOGINS (ID)
      on delete cascade
/

create table CRM_OFFERS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   CAMPAIGN_ID          VARCHAR2(15)         not null,
   CAMPAIGN_NAME        VARCHAR2(255),
   PRODUCT_TYPE         VARCHAR2(20)         not null,
   SOURCE_CODE          VARCHAR2(20)         not null,
   SOURCE_NAME          VARCHAR2(200)        not null,
   PRODUCT_AS_NAME      VARCHAR2(200)        not null,
   PRODUCT_TYPE_CODE    VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   PRODUCT_SUB_CODE     VARCHAR2(50),
   CAMPAIGN_MEMBER_ID   VARCHAR2(50)         not null,
   CLIENT_ID            VARCHAR2(100)        not null,
   TB                   VARCHAR2(4)          not null,
   PRIORITY             INTEGER              not null,
   PERSONAL_TEXT        VARCHAR2(500),
   EXP_DATE             TIMESTAMP            not null,
   CURRENCY_CODE        CHAR(3)              not null,
   RATE_MIN             NUMBER(7,2),
   RATE_MAX             NUMBER(7,2),
   LIMIT_MIN            NUMBER(19,2),
   LIMIT_MAX            NUMBER(19,2),
   PERIOD_MIN           INTEGER,
   PERIOD_MAX           INTEGER,
   LOAD_DATE            DATE                 not null,
   IS_OFFER_USED        CHAR(1)              default '0' not null,
   constraint CRM_OFFERS_PK primary key (ID) using index (
		create unique index SRB_IKFL2.CRM_OFFERS_PK on SRB_IKFL2.CRM_OFFERS(ID) tablespace USER_DATA_IDX      
   )
) tablespace USER_DATA
/

create sequence SRB_IKFL2.S_CRM_OFFERS
/

alter table SRB_IKFL2.CRM_OFFERS
   add constraint FK_CRM_OFFER_TO_LOGIN foreign key (LOGIN_ID)
      references SRB_IKFL2.LOGINS (ID)
      on delete cascade
/

create table SRB_IKFL2.CRM_OFFER_CONDITIONS 
(
   OFFER_ID             INTEGER              not null,
   RATE                 NUMBER(7,2)          not null,
   PERIOD               INTEGER              not null,
   AMOUNT               NUMBER(19,2)         not null
) tablespace USER_DATA
/

alter table SRB_IKFL2.CRM_OFFER_CONDITIONS
   add constraint FK_CRM_OFCOND_TO_OFFER foreign key (OFFER_ID)
      references SRB_IKFL2.CRM_OFFERS (ID)
      on delete cascade
/

create table SRB_IKFL2.CRM_OFFER_FEEDBACKS 
(
   LOGIN_ID             INTEGER              not null,
   SOURCE_CODE          VARCHAR2(20)         not null,
   CAMPAIGN_MEMBER_ID   VARCHAR2(50)         not null,
   INFORM_TIME          TIMESTAMP,
   INFORM_CHANNEL       VARCHAR2(20),
   PRESENT_TIME         TIMESTAMP,
   PRESENT_CHANNEL      VARCHAR2(20),
   OFFER_END_DATE       TIMESTAMP            not null,
   constraint CRM_OFFER_FEEDBACKS_PK primary key (LOGIN_ID, SOURCE_CODE, CAMPAIGN_MEMBER_ID) using index (
		create unique index SRB_IKFL2.CRM_OFFER_FEEDBACKS_PK on SRB_IKFL2.CRM_OFFER_FEEDBACKS(LOGIN_ID, SOURCE_CODE, CAMPAIGN_MEMBER_ID) tablespace USER_DATA_IDX      
   )
) tablespace USER_DATA
/

alter table SRB_IKFL2.CRM_OFFER_FEEDBACKS
   add constraint FK_CRM_OFFEEDBACK_TO_LOGIN foreign key (LOGIN_ID)
      references SRB_IKFL2.LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 72612
-- Комментарий: Промо коды клиента
create table SRB_IKFL2.CLIENT_PROMO_CODE 
(
   ID                   INTEGER                not null,
   NAME                 VARCHAR2(50)         not null,
   LOGIN_ID             INTEGER              not null,
   INPUT_DATE           TIMESTAMP(6)         not null,
   USED                 INTEGER              not null,
   CLOSE_REASON         VARCHAR2(50),
   PROMO_ID             INTEGER              not null,
   END_DATE             TIMESTAMP(6)
) tablespace USER_DATA
partition by range (END_DATE) interval (NUMTOYMINTERVAL(1,'YEAR'))
(
	partition CPC_PART values less than (to_date('01-01-2015','DD-MM-YYYY')) tablespace USER_DATA
)
/

create sequence SRB_IKFL2.S_CLIENT_PROMO_CODE cache 1000
/

create index SRB_IKFL2.CPC_E_DATE_LOGIN_IDX on SRB_IKFL2.CLIENT_PROMO_CODE (
   LOGIN_ID ASC,
   END_DATE ASC
)
local tablespace USER_DATA_IDX
/

create index SRB_IKFL2.CPC_ID_IDX on SRB_IKFL2.CLIENT_PROMO_CODE (
   ID ASC
) tablespace USER_DATA_IDX
/

-- Номер ревизии: 73347
-- Комментарий: КУКО2 - Доработка справочников по кредитной заявке
create table SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD 
(
   CODE                 VARCHAR2(20 BYTE)    not null,
   NAME                 VARCHAR2(100 BYTE)   not null,
   AVAILABLE_IN_CLAIM   CHAR(1)              not null,
   NEW_PRODUCT_FOR_LOAN CHAR(1)              not null,
   PRODUCT_FOR_LOAN     VARCHAR2(15 BYTE)    not null,
   constraint PK_LOANCLAIM_LOAN_ISSUE_METHOD primary key (CODE) using index (
		create unique index SRB_IKFL2.PK_LOANCLAIM_LOAN_ISSUE_METHOD on SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD(CODE) tablespace USER_METADATA_IDX      
   )
) tablespace USER_METADATA      
/

insert into SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD ( CODE, NAME, AVAILABLE_IN_CLAIM, NEW_PRODUCT_FOR_LOAN, PRODUCT_FOR_LOAN ) values ('1', 'На имеющийся вклад', 1, 0, 'DEPOSIT')
/
insert into SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD ( CODE, NAME, AVAILABLE_IN_CLAIM, NEW_PRODUCT_FOR_LOAN, PRODUCT_FOR_LOAN ) values ('2', 'На новый вклад', 1, 1, 'DEPOSIT')
/
insert into SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD ( CODE, NAME, AVAILABLE_IN_CLAIM, NEW_PRODUCT_FOR_LOAN, PRODUCT_FOR_LOAN ) values ('3', 'На имеющуюся карту', 1, 0, 'CARD')
/
insert into SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD ( CODE, NAME, AVAILABLE_IN_CLAIM, NEW_PRODUCT_FOR_LOAN, PRODUCT_FOR_LOAN) values ('4', 'На новую карту', 1, 1, 'CARD')
/
insert into SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD ( CODE, NAME, AVAILABLE_IN_CLAIM, NEW_PRODUCT_FOR_LOAN, PRODUCT_FOR_LOAN) values ('5', 'На имеющийся текущий счет', 1, 0, 'CURRENT_ACCOUNT')
/
insert into SRB_IKFL2.LOANCLAIM_LOAN_ISSUE_METHOD ( CODE, NAME, AVAILABLE_IN_CLAIM, NEW_PRODUCT_FOR_LOAN, PRODUCT_FOR_LOAN) values ('6', 'На новый текущий счет', 1, 1, 'CURRENT_ACCOUNT')
/

-- Номер ревизии: 73492
-- Комментарий: [СБНКД] лимиты
create table SRB_IKFL2.PHONE_LIMITS 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR(15)          not null,
   TYPE_LIMIT           VARCHAR(25)          not null,
   COUNTER              INTEGER              not null,
   LAST_DATE            TIMESTAMP            not null,
   constraint PK_PHONE_LIMITS primary key (ID) using index (
		create unique index SRB_IKFL2.PK_PHONE_LIMITS on SRB_IKFL2.PHONE_LIMITS(ID) tablespace USER_DATA_IDX            
   )
) tablespace USER_DATA
/

create sequence SRB_IKFL2.S_PHONE_LIMITS
/

create unique index SRB_IKFL2.IDX_UNIQUE_PHONE_LIMITS on SRB_IKFL2.PHONE_LIMITS (
   PHONE ASC,
   TYPE_LIMIT ASC
) tablespace USER_DATA_IDX
/

-- Номер ревизии: 73567
-- Комментарий: Интеграция с Яндекс.Деньги. Слепок МБК.
create table SRB_IKFL2.PHONE_UPDATE_JURNAL 
(
   UPDATE_DATE          TIMESTAMP            not null,
   UPDATED_ID           INTEGER              not null,
   NEW_ITEM             CHAR(1)              not null
) tablespace USER_DATA
/

-- Номер ревизии: 73815 
-- Комментарий: СБНКД: Связь с базой данных 
create table SRB_IKFL2.ISSUE_CARD_CLAIM 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32),
   OWNER_ID             INTEGER              not null,
   PHONE                VARCHAR2(11)         not null,
   IS_GUEST             CHAR(1)              not null,
   STATUS               VARCHAR2(30)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   EMAIL                VARCHAR2(100),
   SYSTEM_ID            VARCHAR2(50),
   STAGE_NUMBER         INTEGER,
   TB                   VARCHAR2(3),
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(5),
   CLIENT_FOUND         CHAR(1)              not null,
   VERIFIED             CHAR(1)              not null,
   MESSAGE_DELIVERY_TYPE VARCHAR2(1),
   CARD_AUTO_PAY_INFO   INTEGER,
   CONTACT_TYPE_0       VARCHAR2(4),
   CONTACT_NUMBER_0     VARCHAR2(11),
   CONTACT_PHONE_OPERATOR_0 VARCHAR2(3),
   CONTACT_TYPE_1       VARCHAR2(4),
   CONTACT_NUMBER_1     VARCHAR2(11),
   CONTACT_PHONE_OPERATOR_1 VARCHAR2(3),
   CONTACT_TYPE_2       VARCHAR2(4),
   CONTACT_NUMBER_2     VARCHAR2(11),
   CONTACT_PHONE_OPERATOR_2 VARCHAR2(3),
   ADDRESS_REGISTER_POSTAL_CODE VARCHAR2(6),
   ADDRESS_REGISTER_COUNTRY VARCHAR2(100),
   ADDRESS_REGISTER_REGION VARCHAR2(100),
   ADDRESS_REGISTER_CITY VARCHAR2(100),
   ADDRESS_REGISTER_AFTER_CITY VARCHAR2(200),
   ADDRESS_LIVE_POSTAL_CODE VARCHAR2(6),
   ADDRESS_LIVE_COUNTRY VARCHAR2(100),
   ADDRESS_LIVE_REGION  VARCHAR2(100),
   ADDRESS_LIVE_CITY    VARCHAR2(100),
   ADDRESS_LIVE_AFTER_CITY VARCHAR2(200),
   CONTRACT_BRANCH_ID   VARCHAR2(5),
   CONTRACT_AGENCY_ID   VARCHAR2(4),
   CONTRACT_REGION_ID   VARCHAR2(3),
   CONTRACT_CREDIT_CARD_OFFICE VARCHAR2(150),
   EDBO_BRANCH_ID       VARCHAR2(5),
   EDDO_AGENCY_ID       VARCHAR2(4),
   EDBO_PHONE           VARCHAR2(11),
   EDBO_PHONE_OPERATOR  VARCHAR2(4),
   EDBO_ORDER_NUMBER    VARCHAR2(150),
   EDBO_TB              VARCHAR2(3),
   IDENTITY_CARD_TYPE   VARCHAR2(10),
   IDENTITY_CARD_SERIES VARCHAR2(12),
   IDENTITY_CARD_NUMBER VARCHAR2(25),
   IDENTITY_CARD_ISSUED_BY VARCHAR2(255),
   IDENTITY_CARD_ISSUED_CODE VARCHAR2(10),
   IDENTITY_CARD_ISSUE_DATE TIMESTAMP,
   IDENTITY_CARD_EXP_DATE TIMESTAMP,
   PERSON_BIRTHDAY      TIMESTAMP,
   PERSON_BIRTHPLACE    VARCHAR2(255),
   PERSON_CITIZENSHIP   VARCHAR2(30),
   PERSON_GENDER        CHAR(1)              not null,
   PERSON_RESIDENT      CHAR(1),
   PERSON_TAX_ID        VARCHAR2(12),
   PERSON_LAST_NAME     VARCHAR2(120)        not null,
   PERSON_FIRST_NAME    VARCHAR2(120)        not null,
   PERSON_MIDDLE_NAME   VARCHAR2(120),
   CARD_COUNT           INTEGER,
   FIRST_CARD_NAME      VARCHAR2(50),
   FIRST_CARD_CURRENCY  CHAR(3),
   constraint PK_ISSUE_CARD_CLAIM primary key (ID) using index (
		create unique index SRB_IKFL2.PK_ISSUE_CARD_CLAIM on SRB_IKFL2.ISSUE_CARD_CLAIM(ID) tablespace USER_DATA_IDX      
   )
) tablespace USER_DATA
partition by range (CREATION_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('01-02-2015','DD-MM-YYYY')) tablespace USER_DATA
)
/

create sequence SRB_IKFL2.S_ISSUE_CARD_CLAIM cache 10000
/

create index SRB_IKFL2.IDX_ISSUE_CARD_UUID on SRB_IKFL2.ISSUE_CARD_CLAIM (
   UUID ASC
)
local tablespace USER_DATA_IDX
/

create index SRB_IKFL2.IDX_ISSUE_CARD_OWNER_DATE on SRB_IKFL2.ISSUE_CARD_CLAIM (
   OWNER_ID ASC,
   CREATION_DATE ASC
)
local tablespace USER_DATA_IDX
/

create table SRB_IKFL2.ISSUE_CARD_CLAIM_CARD_INFOS 
(
   UUID                 VARCHAR2(32),
   CREATION_DATE        TIMESTAMP            not null,
   CLAIM_ID             INTEGER              not null,
   FIRST_CARD           CHAR(1)              not null,
   STATUS               VARCHAR2(10)         not null,
   CARD_NUMBER          VARCHAR2(35),
   ACCOUNT_NUMBER       VARCHAR2(35),
   CONTRACT_NUMBER      VARCHAR2(35),
   CARD_END_DATE        TIMESTAMP,
   CARD_TYPE            INTEGER,
   CARD_CURRENCY        CHAR(3),
   PIN_PACK             INTEGER,
   MBK_STATUS           CHAR(1)              not null,
   MBK_PHONE            VARCHAR2(16),
   MBK_PHONE_OPERATOR   VARCHAR2(50),
   PRODUCT_CODE         VARCHAR2(50),
   CREDIT_LIMIT         INTEGER,
   CARD_CONTRACT_DATE   VARCHAR2(30),
   BONUS_INFO           VARCHAR2(255),
   BONUS_INFO_NUMBER    VARCHAR2(6),
   CONTRACT_CODE        VARCHAR2(50),
   RISK_FACTOR          VARCHAR2(50),
   CLIENT_CATEGORY      INTEGER,
   BIODATA              CHAR(1)              not null,
   IS_PIN               CHAR(1)              not null,
   IS_OWNER             CHAR(1)              not null,
   CONTRACT_EMBOSSED_TEXT VARCHAR2(19),
   CLIENT_CARD_NAME     VARCHAR2(50),
   MBK_CONTRACT_TYPE    INTEGER
) tablespace USER_DATA
partition by range (CREATION_DATE) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('01-02-2015','DD-MM-YYYY')) tablespace USER_DATA
)
/

create index SRB_IKFL2.IDX_ISSUE_CARD_CARD_UUID on SRB_IKFL2.ISSUE_CARD_CLAIM_CARD_INFOS (
   UUID ASC
) local tablespace USER_DATA_IDX
/

create index SRB_IKFL2.IDX_ICAR_FK_CAR_CL on SRB_IKFL2.ISSUE_CARD_CLAIM_CARD_INFOS (
   CLAIM_ID ASC
) local tablespace USER_DATA_IDX
/
   
alter table SRB_IKFL2.ISSUE_CARD_CLAIM_CARD_INFOS
   add constraint FK_ISSU_CARD_CLA_CARD foreign key (CLAIM_ID)
      references SRB_IKFL2.ISSUE_CARD_CLAIM (ID)
/
   
-- Номер ревизии: 74144
-- Комментарий:  Управление лимитами кредитных карт
create table SRB_IKFL2.UESI_MESSAGES 
(
   ID                   INTEGER              not null,
   CREATION_DATE        TIMESTAMP(6)         not null,
   EXTERNAL_ID          VARCHAR2(80),
   PHONE                VARCHAR2(11),
   EVENT_DATE_TIME      TIMESTAMP(6),
   STATE                VARCHAR2(10)         not null
) tablespace USER_DATA
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_FIRST values less than (to_date('01-02-2015','DD-MM-YYYY')) tablespace USER_DATA
)
/

create sequence SRB_IKFL2.S_UESI_MESSAGES
/

create index SRB_IKFL2.UESI_EXT_ID_IDX on SRB_IKFL2.UESI_MESSAGES (
   EXTERNAL_ID ASC
) local tablespace USER_DATA_IDX
/

create index SRB_IKFL2.UESI_ID_IDX on SRB_IKFL2.UESI_MESSAGES (
   ID ASC
) local tablespace USER_DATA_IDX
/

create index SRB_IKFL2.UESI_STATE_IDX on SRB_IKFL2.UESI_MESSAGES (
   STATE ASC,
   CREATION_DATE ASC
) local tablespace USER_DATA_IDX
/   

-- Номер ревизии: 75154
-- Комментарий: BUG084689: АРМ. На странице мобильного банка в анкете клиента не отображаются счетчики последних активностей
create table SRB_IKFL2.ERMB_PROFILE_STATISTIC 
(
   PROFILE_ID           INTEGER              not null,
   LAST_REQUEST_TIME    TIMESTAMP,
   constraint PK_ERMB_PROFILE_STATISTIC primary key (PROFILE_ID) using index (
		create unique index SRB_IKFL2.PK_ERMB_PROFILE_STATISTIC on SRB_IKFL2.ERMB_PROFILE_STATISTIC(PROFILE_ID) tablespace USER_DATA_IDX      
   )
) tablespace USER_DATA
/

create sequence SRB_IKFL2.S_ERMB_PROFILE_STATISTIC
/

alter table SRB_IKFL2.ERMB_PROFILE_STATISTIC
   add constraint FK_ERMB_PRO_FK_ERMB_P_ERMB_PRO foreign key (PROFILE_ID)
      references SRB_IKFL2.ERMB_PROFILE (PERSON_ID)
/

-- Номер ревизии: 75307
-- Комментарий: BUG082567: [ISUP] mAPI. Долгая обработка запроса на открытие вклада
create table SRB_IKFL2.DEPOSITS_DCF_TAR 
(
   ID                   INTEGER              not null,
   KOD_VKL_QDTN1        INTEGER              not null,
   KOD_VKL_QDTSUB       INTEGER              not null,
   KOD_VKL_QVAL         CHAR(1)              not null,
   KOD_VKL_CLNT         INTEGER              not null,
   DCF_SROK             INTEGER              not null,
   DATE_BEG             TIMESTAMP(6)         not null,
   SUM_BEG              NUMBER(21,4)         not null,
   SUM_END              NUMBER(21,4)         not null,
   TAR_VKL              NUMBER(21,4)         not null,
   TAR_NRUS             NUMBER(21,4),
   DCF_VAL              VARCHAR2(3)          not null,
   DCF_SEG              INTEGER              not null,
   constraint PK_DEPOSITS_DCF_TAR primary key (ID) using index (
		create unique index SRB_IKFL2.PK_DEPOSITS_DCF_TAR on SRB_IKFL2.DEPOSITS_DCF_TAR(ID) tablespace USER_METADATA_IDX      
   )
) tablespace USER_METADATA
/

create sequence SRB_IKFL2.S_DEPOSITS_DCF_TAR
/

create index SRB_IKFL2.DEPOSITS_DCF_TAR_IDX on SRB_IKFL2.DEPOSITS_DCF_TAR (
   KOD_VKL_QDTN1 ASC,
   KOD_VKL_QDTSUB ASC
) tablespace USER_METADATA_IDX
/

-- Номер ревизии: 75832
-- Комментарий: BUG084691  ЕРМБ. При подключении услуги МБ, делать регистрацию у разных ОСС, как сейчас у мегафона (бд)
create table SRB_IKFL2.ERMB_MOBILE_OPERATOR 
(
   MNC                  CHAR(2)              not null,
   NAME                 VARCHAR2(20)         not null,
   USE_INTEGRATION      CHAR(1)              not null,
   SERVICE_URL          VARCHAR2(256),
   SERVICE_LOGIN        VARCHAR2(100),
   SERVICE_PASSWORD     VARCHAR2(100),
   constraint PK_ERMB_MOBILE_OPERATOR primary key (MNC) using index (
		create unique index SRB_IKFL2.PK_ERMB_MOBILE_OPERATOR on SRB_IKFL2.ERMB_MOBILE_OPERATOR(MNC) tablespace USER_METADATA_IDX      
   )
) tablespace USER_METADATA
/