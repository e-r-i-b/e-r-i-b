/*
	Схема: CSAADMIN_IKFL
	Табличное пространство таблиц: CSA_ADM_DATA
	Табличное пространство индексов: CSA_ADM_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSAADMIN_IKFL
/

-- Номер ревизии: 72684
-- Комментарий: Интеграция с Яндекс.Деньги Модель БД. Таблица PROVIDER_PROPERTIES
create table CSAADMIN_IKFL.PROVIDER_PROPERTIES 
(
   PROVIDER_ID          INTEGER              not null,
   MCHECKOUT_DEFAULT_ENABLED CHAR(1)              not null,
   MB_CHECK_ENABLED     CHAR(1)              not null,
   EINVOICE_DEFAULT_ENABLED CHAR(1)              not null,
   MB_CHECK_DEFAULT_ENABLED CHAR(1)              not null,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_PROVIDER_PROPERTIES primary key (PROVIDER_ID) using index (
		create unique index CSAADMIN_IKFL.PK_PROVIDER_PROPERTIES on CSAADMIN_IKFL.PROVIDER_PROPERTIES(PROVIDER_ID) tablespace CSA_ADM_IDX
   )
) tablespace CSA_ADM_DATA
/

create sequence CSAADMIN_IKFL.S_PROVIDER_PROPERTIES
/

alter table CSAADMIN_IKFL.PROVIDER_PROPERTIES
   add constraint PROVIDER_PROP_TO_PROVIDER foreign key (PROVIDER_ID)
      references CSAADMIN_IKFL.SERVICE_PROVIDERS (ID)
      on delete cascade
/

-- Номер ревизии: 72770
-- Комментарий: Применение промо-кодов для открытия промо-вкладов.
create table CSAADMIN_IKFL.PROMO_CODES_DEPOSIT 
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
		create unique index CSAADMIN_IKFL.PK_PROMO_CODES_DEPOSIT on CSAADMIN_IKFL.PROMO_CODES_DEPOSIT(ID) tablespace CSA_ADM_IDX
   )
) tablespace CSA_ADM_DATA
/

create sequence CSAADMIN_IKFL.S_PROMO_CODES_DEPOSIT
/

comment on table CSAADMIN_IKFL.PROMO_CODES_DEPOSIT is 'Справочник промо-кодов для открытия вкладов'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.CODE is 'Уникальный код «промо»'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.CODE_G is 'Код промо-акции'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.MASK is 'Маска промо- кода'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.CODE_S is 'Код сегмента клиента'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.DATE_BEGIN is 'Дата начала периода активации промо- кода'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.DATE_END is 'Дата окончания периода активации промо-кода'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.SROK_BEGIN is 'Срок действия промо-кода, начиная со дня ввода клиентом'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.SROK_END is 'Срок действия промо-кода, начиная со дня окончания периода активации промо-кода'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.NUM_USE is 'Возможное количество раз использования клиентом'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.PRIORITY is 'Приоритет условий с промо-кодом «0» - используются ставки для промо-кода; «1» - отображается как отдельный вклад'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.AB_REMOVE is 'Возможность удаления введенного промо-кода:«0» - нельзя удалять;«1» - можно удалять'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.ACTIVE_COUNT is 'Количество действующих промо- кодов у клиента'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.HIST_COUNT is 'Количество введенных промо - кодов клиентом'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.NAME_ACT is 'Описание промо акции'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.NAME_S is 'Краткое описание промо-кода'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.NAME_F is 'Подробное описание промо-кода'
/
comment on column CSAADMIN_IKFL.PROMO_CODES_DEPOSIT.UUID is 'Кросблочный ID'
/

create unique index CSAADMIN_IKFL.IDX_PROMO_CODES_DEP_UUID on CSAADMIN_IKFL.PROMO_CODES_DEPOSIT (
   UUID ASC
) tablespace CSA_ADM_IDX
/

create unique index CSAADMIN_IKFL.IDX_PROMO_CODES_DEP_CODE on CSAADMIN_IKFL.PROMO_CODES_DEPOSIT (
   CODE ASC
) tablespace CSA_ADM_IDX
/

-- Номер ревизии: 73567
-- Комментарий: Интеграция с Яндекс.Деньги. Слепок МБК.
create table CSAADMIN_IKFL.MBK_PHONE_JURNAL 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR2(11)         not null,
   ADDED                CHAR(1)              not null,
   LAST_UPDATE_TIME     TIMESTAMP            not null
) tablespace CSA_ADM_DATA
partition by range (LAST_UPDATE_TIME) interval (numtoyminterval(1,'MONTH'))
(
	partition P_START values less than (to_date('01-02-2015','DD-MM-YYYY')) tablespace CSA_ADM_DATA
)
/

create sequence CSAADMIN_IKFL.S_MBK_PHONE_JURNAL cache 1000
/

create index CSAADMIN_IKFL.IDX_PH_JUR_LAST_DATE on CSAADMIN_IKFL.MBK_PHONE_JURNAL (
   ID ASC
) local tablespace CSA_ADM_IDX
/