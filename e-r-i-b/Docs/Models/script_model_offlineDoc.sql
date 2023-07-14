-- Номер ревизии: 55206
-- Номер версии: 1.18
-- Комментарий: Маршрутизация обратного потока. БД
ALTER TABLE OFFLINE_DOCUMENT_INFO ADD (ADAPTER_UUID VARCHAR2(64) NOT NULL)

-- Номер ревизии: 62080
-- Номер версии: 1.18
-- Комментарий: Корзина платежей.MQ(изменение модели БД OfflineDoc) 
/*==============================================================*/
/* Table: BASKETINVOICE_PROCESS_STATE                           */
/*==============================================================*/
create table BASKETINVOICE_PROCESS_STATE 
(
   KEY                  VARCHAR2(32)
)
go

insert into BASKETINVOICE_PROCESS_STATE values ('invoice_basket_processing_state')
go

/*==============================================================*/
/* Table: BASKET_ROUTE_INFO                                     */
/*==============================================================*/
create table BASKET_ROUTE_INFO 
(
   OPER_UID             VARCHAR2(32),
   BLOCK_NUMBER         INTEGER
)
go

/*==============================================================*/
/* Index: I_BASKET_ROUTE_INFO_OPER_UID                          */
/*==============================================================*/
create unique index I_BASKET_ROUTE_INFO_OPER_UID on BASKET_ROUTE_INFO (
   OPER_UID ASC
)
go


-- Номер ревизии: 62442
-- Номер версии: 1.18
-- Комментарий: Корзина платежей: Properties для слушателя сообщения от АС "AutoPay"

/*==============================================================*/
/* Table: PROPERTIES                                            */
/*==============================================================*/
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)

go

create sequence S_PROPERTIES
go

/*==============================================================*/
/* Index: U_PROPETIES_CATEGORY_KEY                              */
/*==============================================================*/
create unique index U_PROPETIES_CATEGORY_KEY on PROPERTIES (
   CATEGORY ASC,
   PROPERTY_KEY ASC
)
go

-- Номер ревизии: 68395
-- Номер версии: 1.18
-- Комментарий: Интеграция с БКИ (приём ответа) - маршрутизация

/*==============================================================*/
/* Table: OUTGOING_REQUESTS                                     */
/*==============================================================*/
create table OUTGOING_REQUESTS 
(
   RQ_UID               VARCHAR2(32)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   OPER_UID             VARCHAR2(32)         not null,
   NODE_ID              INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   constraint PK_OUTGOING_REQUESTS primary key (RQ_UID)
)
go

-- Номер ревизии: 68519
-- Номер версии: 1.18
-- Комментарий: Запрос отчета из БКИ - GET/CHECK запросы (проверка)

ALTER TABLE OUTGOING_REQUESTS ADD PAYMENT_ID INTEGER;

-- Номер ревизии: 69063
-- Номер версии: 1.18
-- Комментарий: Замечания по коду (БКИ) часть 2
ALTER TABLE OUTGOING_REQUESTS RENAME COLUMN LOGIN_ID TO PERSON_ID;


-- Номер ревизии: 70427
-- Номер версии: 1.18
-- Комментарий: Добавлен Тип исходящего запроса
ALTER TABLE OUTGOING_REQUESTS
ADD (REQUEST_TYPE VARCHAR2(50))
;

UPDATE OUTGOING_REQUESTS
SET REQUEST_TYPE = 'BKICheckCreditHistory'
WHERE PAYMENT_ID IS NULL
;

UPDATE OUTGOING_REQUESTS
SET REQUEST_TYPE = 'BKIGetCreditHistory'
WHERE PAYMENT_ID IS NOT NULL
;

ALTER TABLE OUTGOING_REQUESTS
MODIFY(REQUEST_TYPE NOT NULL)
;


-- Номер ревизии: 72543
-- Номер версии: 1.18
-- Комментарий: В таблице исходящих запросов сделан необязательным OPER_UID, добавлен TB
ALTER TABLE OUTGOING_REQUESTS
MODIFY(OPER_UID NULL)
;

ALTER TABLE OUTGOING_REQUESTS
RENAME COLUMN PERSON_ID TO LOGIN_ID
;

ALTER TABLE OUTGOING_REQUESTS
ADD (TB VARCHAR2(4) NULL)
;






-- Номер ревизии: 82300
-- Номер версии: 1.18
-- Комментарий: BUG090575: [ЕРМБ] Онлайн миграция. Невозможно подтвердить конфликтный номер телефона

CREATE TABLE SMSPASSWORDS_ERMB  ( 
    ID            	INTEGER NOT NULL,   
    ISSUE_DATE    	TIMESTAMP NOT NULL,
    EXPIRE_DATE   	TIMESTAMP NOT NULL,  
    WRONG_ATTEMPTS	INTEGER NOT NULL,
    HASH          	varchar2(100) NULL,
    PHONE_NUMBER    VARCHAR2(20) NOT NULL       
)
partition by range (EXPIRE_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
  
    partition P_2015_06 values less than (to_date('26-06-2015','DD-MM-YYYY'))
)
go
create index IDX_SMSPASS_ERMB_PHONE on SMSPASSWORDS_ERMB(
    PHONE_NUMBER asc
)
local
go

create sequence S_SMSPASSWORDS_ERMB
go