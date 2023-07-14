-- Номер ревизии: 55195
-- Номер версии: 1.18
-- Комментарий: Архитектура. Доработки по E-Invoicing

/*==============================================================*/
/* Table: E_IN_PERSONS                                          */
/*==============================================================*/
create table E_IN_PERSONS 
(
   ID                   INTEGER              not null,
   FIRSTNAME            VARCHAR2(300)        not null,
   PATRNAME             VARCHAR2(300),
   SURNAME              VARCHAR(300)         not null,
   BIRTHDATE            DATE                 not null,
   PASSPORT             VARCHAR2(100)        not null,
   TB                   VARCHAR2(100)        not null
)

go

create sequence S_E_IN_PERSONS
go

alter table E_IN_PERSONS
   add constraint PK_E_IN_PERSONS primary key (ID)
go

/*==============================================================*/
/* Index: PERSONS_IDX                                           */
/*==============================================================*/
create index PERSONS_IDX on E_IN_PERSONS (
   TB ASC,
   SURNAME ASC,
   FIRSTNAME ASC,
   PATRNAME ASC,
   BIRTHDATE DESC,
   PASSPORT ASC
)
go

/*==============================================================*/
/* Table: ORDERS                                                */
/*==============================================================*/
create table ORDERS 
(
   ID                   INTEGER              not null,
   EXTENDED_ID          VARCHAR2(255),
   ORDER_TYPE           CHAR(1)              not null,
   ORDER_DATE           TIMESTAMP            not null,
   SYSTEM_NAME          VARCHAR2(32),
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   DESCRIPTION          VARCHAR2(255),
   RECEIVER_ACCOUNT     VARCHAR2(24),
   BIC                  VARCHAR2(9),
   CORRESPONDENT_ACCOUNT VARCHAR2(35),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(9),
   RECEIVER_NAME        VARCHAR2(160),
   PAYMENT_ID           VARCHAR2(80),
   USER_ID              INTEGER,
   UUID                 VARCHAR2(255)        not null,
   STATUS               VARCHAR2(10),
   NOTIFICATION_COUNT   INTEGER              default 0 not null,
   STATUS_DISCRIPTION   VARCHAR2(255),
   NOTIFICATION_TIME    TIMESTAMP,
   ADDITIONAL_FIELDS    CLOB,
   PRINT_DESC           VARCHAR2(200),
   CHECKED              CHAR(1)
)

go

create sequence S_ORDERS
go

alter table ORDERS
   add constraint PK_ORDERS primary key (ID)
go

/*==============================================================*/
/* Index: ORDERS_EXTENDED_ID                                    */
/*==============================================================*/
create index ORDERS_EXTENDED_ID on ORDERS (
   EXTENDED_ID ASC
)
go

/*==============================================================*/
/* Index: ORDERS_UUID                                           */
/*==============================================================*/
create index ORDERS_UUID on ORDERS (
   UUID ASC
)
go

/*==============================================================*/
/* Index: ORDERS_DATE                                           */
/*==============================================================*/
create index ORDERS_DATE on ORDERS (
   ORDER_DATE ASC
)
go

/*==============================================================*/
/* Index: ORDERS_PERSON                                         */
/*==============================================================*/
create index ORDERS_PERSON on ORDERS (
   USER_ID ASC
)
go

/*==============================================================*/
/* Table: ORDER_RECALLS                                         */
/*==============================================================*/
create table ORDER_RECALLS 
(
   CLAIM_ID             VARCHAR2(80)         not null,
   NOTIFICATION_STATUS  VARCHAR2(10)         not null,
   NOTIFICATION_TIME    TIMESTAMP,
   NOTIFICATION_COUNT   INTEGER              default 0 not null,
   SHOP_ORDER_ID        INTEGER              not null,
   STATUS_DESCRIPTION   VARCHAR2(1000)
)

go

create sequence S_ORDER_RECALLS
go

alter table ORDER_RECALLS
   add constraint PK_ORDER_RECALLS primary key (CLAIM_ID)
go

/*==============================================================*/
/* Index: STATUS_DESCRIPTION                                    */
/*==============================================================*/
create index STATUS_DESCRIPTION on ORDER_RECALLS (
   NOTIFICATION_TIME ASC
)
go

/*==============================================================*/
/* Table: PAYMENTS_INFO                                         */
/*==============================================================*/
create table PAYMENTS_INFO 
(
   ID                   VARCHAR2(80)         not null,
   TICKET_INFO          VARCHAR2(2000),
   TICKET_DESC          VARCHAR2(1000),
   TICKET_STATUS        INTEGER
)

go

create sequence S_PAYMENTS_INFO
go

alter table PAYMENTS_INFO
   add constraint PK_PAYMENTS_INFO primary key (ID)
go

/*==============================================================*/
/* Table: PROPERTIES                                            */
/*==============================================================*/
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80)
)

go

create sequence S_PROPERTIES
go

alter table PROPERTIES
   add constraint PK_PROPERTIES primary key (ID)
go

/*==============================================================*/
/* Index: PROPERTIES_UNIQ                                       */
/*==============================================================*/
create index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
go

/*==============================================================*/
/* Table: SHOP_ADDITIONAL_FIELDS                                */
/*==============================================================*/
create table SHOP_ADDITIONAL_FIELDS 
(
   ID                   INTEGER              not null,
   DESCRIPTION          VARCHAR2(255),
   PRODUCT_NAME         VARCHAR2(255),
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   COUNT                INTEGER              default 0 not null,
   SHOP_ORDER_ID        INTEGER              not null
)

go

create sequence S_SHOP_ADDITIONAL_FIELDS
go

alter table SHOP_ADDITIONAL_FIELDS
   add constraint PK_SHOP_ADDITIONAL_FIELDS primary key (ID)
go

/*==============================================================*/
/* Table: SHOP_FIELDS                                           */
/*==============================================================*/
create table SHOP_FIELDS 
(
   ID                   INTEGER              not null,
   ORDER_ID             INTEGER,
   BACK_URL             VARCHAR2(1000),
   AIRLINE_RESERV_ID    VARCHAR2(100),
   AIRLINE_RESERV_EXPIRATION TIMESTAMP,
   AIRLINE_RESERVATION  CLOB,
   CANCELED             CHAR(1)              default '0' not null,
   MOBILE_CHECKOUT_STATE VARCHAR2(20),
   MOBILE_CHECKOUT_PHONE VARCHAR2(30)
)

go

create sequence S_SHOP_FIELDS
go

alter table SHOP_FIELDS
   add constraint PK_SHOP_FIELDS primary key (ID)
go

/*==============================================================*/
/* Index: MOB_CHCKOUT_STTE_IDX                                  */
/*==============================================================*/
create index MOB_CHCKOUT_STTE_IDX on SHOP_FIELDS (
   MOBILE_CHECKOUT_STATE ASC
)
go

/*==============================================================*/
/* Table: WITHDRAW_DOCUMENTS                                    */
/*==============================================================*/
create table WITHDRAW_DOCUMENTS 
(
   ID                   INTEGER              not null,
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   ORDER_INFO_ID        INTEGER              not null,
   PAYMENT_ID           VARCHAR2(80)         not null,
   WITHDRAW_MODE        VARCHAR2(10)         not null
)

go

create sequence S_WITHDRAW_DOCUMENTS
go

alter table WITHDRAW_DOCUMENTS
   add constraint PK_WITHDRAW_DOCUMENTS primary key (ID)
go

/*==============================================================*/
/* Table: WITHDRAW_EXTENDED_FIELDS                              */
/*==============================================================*/
create table WITHDRAW_EXTENDED_FIELDS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   VALUE                VARCHAR(2000)        not null,
   WITHDRAW_ID          INTEGER              not null
)

go

create sequence S_WITHDRAW_EXTENDED_FIELDS
go

alter table WITHDRAW_EXTENDED_FIELDS
   add constraint PK_WITHDRAW_EXTENDED_FIELDS primary key (ID)
go


create index "DXREFERENCE_2" on ORDERS
(
   USER_ID
)
go

alter table ORDERS
   add constraint FK_ORDERS_REFERENCE_E_IN_PER foreign key (USER_ID)
      references E_IN_PERSONS (ID)
go


create index "DXREFERENCE_7" on ORDER_RECALLS
(
   SHOP_ORDER_ID
)
go

alter table ORDER_RECALLS
   add constraint FK_ORDER_RE_REFERENCE_SHOP_FIE foreign key (SHOP_ORDER_ID)
      references SHOP_FIELDS (ID)
go


create index "DXREFERENCE_3" on SHOP_ADDITIONAL_FIELDS
(
   SHOP_ORDER_ID
)
go

alter table SHOP_ADDITIONAL_FIELDS
   add constraint FK_SHOP_ADD_REFERENCE_SHOP_FIE foreign key (SHOP_ORDER_ID)
      references SHOP_FIELDS (ID)
go


create index "DXREFERENCE_1" on SHOP_FIELDS
(
   ORDER_ID
)
go

alter table SHOP_FIELDS
   add constraint FK_SHOP_FIE_REFERENCE_ORDERS foreign key (ORDER_ID)
      references ORDERS (ID)
go


create index "DXREFERENCE_4" on WITHDRAW_DOCUMENTS
(
   ORDER_INFO_ID
)
go

alter table WITHDRAW_DOCUMENTS
   add constraint FK_WITHDRAW_REFERENCE_SHOP_FIE foreign key (ORDER_INFO_ID)
      references SHOP_FIELDS (ID)
go


create index "DXREFERENCE_5" on WITHDRAW_DOCUMENTS
(
   PAYMENT_ID
)
go

alter table WITHDRAW_DOCUMENTS
   add constraint FK_WITHDRAW_REFERENCE_PAYMENTS foreign key (PAYMENT_ID)
      references PAYMENTS_INFO (ID)
go


create index "DXREFERENCE_6" on WITHDRAW_EXTENDED_FIELDS
(
   WITHDRAW_ID
)
go

alter table WITHDRAW_EXTENDED_FIELDS
   add constraint FK_WITHDRAW_REFERENCE_WITHDRAW foreign key (WITHDRAW_ID)
      references WITHDRAW_DOCUMENTS (ID)
go

-- Номер ревизии: 55271
-- Номер версии: 1.18
-- Комментарий: Доработки по E-Invoicing

drop index PERSONS_IDX 
go

create index PERSONS_IDX on E_IN_PERSONS (
   PASSPORT ASC
)
go

drop index ORDERS_DATE
go

drop index ORDERS_PERSON
go

create index ORDERS_PERSON_DATE on ORDERS (
   USER_ID ASC,
   ORDER_DATE DESC
)
go


-- Номер ревизии: 55748
-- Номер версии: 1.18
-- Комментарий: BUG064354: Неверно отображаются статусы интернет-заказов после конвертации
alter table ORDERS drop column PAYMENT_ID 
go

delete from PAYMENTS_INFO 
go

alter table PAYMENTS_INFO add ORDER_UUID varchar2(32) not null
go

alter table PAYMENTS_INFO modify ID integer 
go

alter table WITHDRAW_DOCUMENTS modify PAYMENT_ID integer 
go

--Номер ревизии: 58056
--Номер версии: 1.18
--Комментарий: EInvoicing 2
/*==============================================================*/
/* Table: SHOP_NOTIFICATIONS                                    */
/*==============================================================*/
create table SHOP_NOTIFICATIONS 
(
   "DATE"               TIMESTAMP            not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(30)         not null,
   NOTIF_STATUS         VARCHAR2(30)         not null,
   NOTIF_DATE           TIMESTAMP,
   NOTIF_COUNT          INTEGER              not null,
   NOTIF_STATUS_DESCRIPTION VARCHAR2(255)
)
partition by range
 ("DATE")
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SN
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

create sequence S_SHOP_NOTIFICATIONS
go

/*==============================================================*/
/* Index: IDX_SHOP_NOTIF_STATE_DATE                             */
/*==============================================================*/
create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   "DATE" ASC
)
local
go

/*==============================================================*/
/* Table: SHOP_ORDERS                                           */
/*==============================================================*/
create table SHOP_ORDERS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   TYPE                 CHAR(1)              not null,
   "DATE"               TIMESTAMP            not null,
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
partition by range
 ("DATE")
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SO
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

create sequence S_SHOP_ORDERS
go

/*==============================================================*/
/* Index: IDX_SHOP_ORDERS_ID                                    */
/*==============================================================*/
create index IDX_SHOP_ORDERS_ID on SHOP_ORDERS (
   ID ASC
)
local
go

/*==============================================================*/
/* Index: IDX_SH_ORD_EXT_ID_REC_CODE                            */
/*==============================================================*/
create unique index IDX_SH_ORD_EXT_ID_REC_CODE on SHOP_ORDERS (
   EXTERNAL_ID ASC,
   RECEIVER_CODE ASC
)
global
go

/*==============================================================*/
/* Index: IDX_SH_ORD_PROF_ID_DATE                               */
/*==============================================================*/
create index IDX_SH_ORD_PROF_ID_DATE on SHOP_ORDERS (
   PROFILE_ID ASC,
   "DATE" DESC
)
local
go

/*==============================================================*/
/* Index: IDX_SHOP_ORD_UUID                                     */
/*==============================================================*/
create unique index IDX_SHOP_ORD_UUID on SHOP_ORDERS (
   UUID ASC
)
global
go

/*==============================================================*/
/* Table: SHOP_PROFILE                                          */
/*==============================================================*/
create table SHOP_PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null
)

go

create sequence S_SHOP_PROFILES
go

alter table SHOP_PROFILES
   add constraint PK_SHOP_PROFILES primary key (ID)
go

/*==============================================================*/
/* Index: IDX_SHOP_PROFILE_UNIQ                                 */
/*==============================================================*/
create unique index IDX_SHOP_PROFILE_UNIQ on SHOP_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT, ' ', '') ASC
)
go

/*==============================================================*/
/* Table: SHOP_RECALLS                                          */
/*==============================================================*/
create table SHOP_RECALLS 
(
   UUID                 VARCHAR2(32)         not null,
   RECEIVER_CODE        VARCHAR2(32)         not null,
   "DATE"               TIMESTAMP            not null,
   ORDER_UUID           VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(20)         not null
)
partition by range
 ("DATE")
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SR
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

create sequence S_SHOP_RECALLS
go

alter table SHOP_RECALLS
   add constraint PK_SHOP_RECALLS primary key (UUID)
go

create index "DXREFERENCE_8" on SHOP_ORDERS
(
   PROFILE_ID
)
go

alter table SHOP_ORDERS
   add constraint FK_SHOP_ORD_REFERENCE_SHOP_PRO foreign key (PROFILE_ID)
      references SHOP_PROFILES (ID)
go

alter table SHOP_ORDERS RENAME COLUMN "DATE" to ORDER_DATE
go

-- Номер ревизии: 58327
-- Номер версии: 1.18
-- Комментарий: Изменение БД заказов

alter table SHOP_RECALLS rename column "DATE" to RECALL_DATE
go

alter table SHOP_RECALLS modify UUID varchar2(300)
go

-- Номер ревизии: 58839
-- Номер версии: 1.18
-- Комментарий: EInvoicing2
alter table SHOP_NOTIFICATIONS RENAME COLUMN "DATE" to CREATE_DATE
go
alter table SHOP_NOTIFICATIONS  add RECEIVER_CODE        VARCHAR2(32)         not null
go
drop index IDX_SHOP_NOTIF_STATE_DATE
go
create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   CREATE_DATE ASC
)
local
go
alter table SHOP_RECALLS modify UUID varchar2(32)
go
create index IDX_SH_RECALLS_ORDER_UUID on SHOP_RECALLS (
   ORDER_UUID ASC
)
local
go

-- Номер ревизии: 59013
-- Номер версии: 1.18
-- Комментарий: EInvoicing2 (ч37 SHOP_RECALLS. поле TYPE. PhizIC)

alter table SHOP_RECALLS add TYPE VARCHAR2(10) not null
go

-- Номер ревизии: 59525
-- Номер версии: 1.18
-- Комментарий: ИОК

drop table SHOP_ADDITIONAL_FIELDS
go

drop sequence S_SHOP_ADDITIONAL_FIELDS
go

drop table ORDER_RECALLS
go

drop sequence S_ORDER_RECALLS
go

drop table WITHDRAW_EXTENDED_FIELDS 
go

drop sequence S_WITHDRAW_EXTENDED_FIELDS
go

drop table WITHDRAW_DOCUMENTS 
go

drop sequence S_WITHDRAW_DOCUMENTS
go

drop table PAYMENTS_INFO 
go

drop sequence S_PAYMENTS_INFO
go

drop table SHOP_FIELDS 
go

drop sequence S_SHOP_FIELDS
go

drop table ORDERS 
go

drop sequence S_ORDERS
go

drop table E_IN_PERSONS 
go

drop sequence S_E_IN_PERSONS
go

-- Номер ревизии: 59990
-- Номер версии: 1.18
-- Комментарий: BUG070244: Ошибка при оформлении offline заказа
ALTER
	TABLE SHOP_ORDERS ADD MOBILE_CHECKOUT CHAR(1) DEFAULT '0' NOT NULL
go

-- Номер ревизии: 61445
-- Номер версии: 1.18
-- Комментарий: BUG071896: Ошибка при создании интернет-заказа

create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') 
    then    
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;
go 

-- Номер ревизии: 62313
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков
create or replace procedure create_sequence(sequenceName VARCHAR, minval INTEGER, maxval INTEGER, sequenceType VARCHAR default NULL) is
begin
    case
        -- пересоздаваемые каждый день
        when sequenceType = 'EXTENDED'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle cache 2000';

        -- строго последовательные
        when sequenceType = 'STRICT'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle nocache order';

        -- сиквенс по умолчанию
        else execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle';
   end case;
end;
go

-- Номер ревизии: 62424
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков (ограничения)
create or replace procedure create_sequence(sequenceName VARCHAR, minval INTEGER, maxval INTEGER, sequenceType VARCHAR default NULL) is
begin
    case
        -- пересоздаваемые каждый день
        when sequenceType = 'EXTENDED'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle cache 2000';

        -- строго последовательные
        -- запрещено создание nocache order для данной БД
        when sequenceType = 'STRICT'
        then raise_application_error(-20100, 'Запрещено создание STRICT счетчика для данной БД');

        -- сиквенс по умолчанию
        else execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle';
   end case;
end;
go

-- Номер ревизии: 62402
-- Номер версии: 1.18
-- Комментарий: Добавление интернет-заказов в функционал "счетов к оплате"

ALTER TABLE "SHOP_ORDERS"
	ADD ( "DELAYED_PAY_DATE" TIMESTAMP NULL ) 
GO

-- Номер ревизии: 62724
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков (откат)

create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- если этот сиквенс - обновляемый
    then    -- ставим ему кэш 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;



-- Номер ревизии: 72161
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги Модель БД
create table FACILITATOR_PROVIDERS 
(
   ID                   INTEGER              not null,
   FACILITATOR_CODE     VARCHAR2(32)         not null,
   CODE                 VARCHAR2(250)        not null,
   NAME                 VARCHAR2(160)        not null,
   INN                  VARCHAR2(12)         not null,
   URL                  VARCHAR2(500)        not null,
   DELETED              CHAR(1)              not null,
   MCHECKOUT_ENABLED    CHAR(1)              not null,
   EINVOICE_ENABLED     CHAR(1)              not null,
   MB_CHECK_ENABLED     CHAR(1)              not null
)

go

create sequence S_FACILITATOR_PROVIDERS
go

alter table FACILITATOR_PROVIDERS
   add constraint PK_FACILITATOR_PROVIDERS primary key (ID)
go

create unique index UNIQ_PROV on FACILITATOR_PROVIDERS (
   FACILITATOR_CODE ASC,
   CODE ASC
)
go


-- Номер ревизии: 73126
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги. Модель БД
alter table SHOP_ORDERS add (FACILITATOR_PROVIDER_CODE VARCHAR(100) NULL)
go

alter table FACILITATOR_PROVIDERS modify (
URL NULL,
CODE VARCHAR2(100)
)
go

alter table SHOP_RECALLS add (
AMOUNT               NUMBER(15,5),
CURRENCY             CHAR(3)
)


-- Номер ревизии: 73567
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги. Слепок МБК.
create table MBK_CAST (
    PHONE VARCHAR2(11) NOT NULL
)
partition by hash(PHONE)
partitions 128
go

create index IDX_MBK_CAST_PHONE on MBK_CAST (
   PHONE asc
)
local
go

create table PHONE_UPDATE_JURNAL (
	UPDATE_DATE TIMESTAMP not null,
	UPDATED_ID INTEGER not null,
	NEW_ITEM char(1) not null
)
go

-- Номер ревизии: 74640 
-- Номер версии: 1.18
-- Комментарий: BUG084623: Ошибка в скрипте создания основной схемы БД 

alter table SHOP_ORDERS add (IS_NEW char(1));
update SHOP_ORDERS set IS_NEW='0';
alter table SHOP_ORDERS modify (IS_NEW not null);

