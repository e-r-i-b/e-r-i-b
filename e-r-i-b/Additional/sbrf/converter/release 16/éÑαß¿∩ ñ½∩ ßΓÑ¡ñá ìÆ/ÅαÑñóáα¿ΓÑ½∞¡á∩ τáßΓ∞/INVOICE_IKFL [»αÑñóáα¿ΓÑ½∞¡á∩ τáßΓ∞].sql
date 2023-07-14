/*
	Схема: INVOICE_IKFL
	Табличное пространство таблиц: EINVOICING_DATA
	Табличное пространство индексов: EINVOICING_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = INVOICE_IKFL
/

-- Номер ревизии: 72161
-- Комментарий: Интеграция с Яндекс.Деньги Модель БД
create table INVOICE_IKFL.FACILITATOR_PROVIDERS 
(
   ID                   INTEGER               not null,
   FACILITATOR_CODE     VARCHAR2(32)         not null,
   CODE                 VARCHAR2(100)         not null,
   NAME                 VARCHAR2(160)         not null,
   INN                  VARCHAR2(12)          not null,
   URL                  VARCHAR2(500),
   DELETED              CHAR(1)               not null,
   MCHECKOUT_ENABLED    CHAR(1)               not null,
   EINVOICE_ENABLED     CHAR(1)               not null,
   MB_CHECK_ENABLED     CHAR(1)               not null,
   constraint PK_FACILITATOR_PROVIDERS primary key (ID) using index (
		create unique index INVOICE_IKFL.PK_FACILITATOR_PROVIDERS on INVOICE_IKFL.FACILITATOR_PROVIDERS(ID) tablespace EINVOICING_IDX
   )
) tablespace EINVOICING_DATA
/

create sequence INVOICE_IKFL.S_FACILITATOR_PROVIDERS
/

create unique index INVOICE_IKFL.UNIQ_PROV on INVOICE_IKFL.FACILITATOR_PROVIDERS (
   FACILITATOR_CODE ASC,
   CODE ASC
) tablespace EINVOICING_IDX
/


-- Номер ревизии: 73567
-- Комментарий: Интеграция с Яндекс.Деньги. Слепок МБК.
create table INVOICE_IKFL.MBK_CAST 
(
   PHONE                VARCHAR2(11)         not null
) tablespace EINVOICING_DATA
partition by hash (PHONE) partitions 128
/

create index INVOICE_IKFL.IDX_MBK_CAST_PHONE on INVOICE_IKFL.MBK_CAST (
   PHONE ASC
) local tablespace EINVOICING_IDX
/

--таблица в одну строку
create table INVOICE_IKFL.PHONE_UPDATE_JURNAL 
(
   UPDATE_DATE          TIMESTAMP            not null,
   UPDATED_ID           INTEGER              not null,
   NEW_ITEM             CHAR(1)              not null
) tablespace EINVOICING_DATA
/