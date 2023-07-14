-- ����� �������: 55206
-- ����� ������: 1.18
-- �����������: ������������� ��������� ������. ��
ALTER TABLE OFFLINE_DOCUMENT_INFO ADD (ADAPTER_UUID VARCHAR2(64) NOT NULL)

-- ����� �������: 62080
-- ����� ������: 1.18
-- �����������: ������� ��������.MQ(��������� ������ �� OfflineDoc) 
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


-- ����� �������: 62442
-- ����� ������: 1.18
-- �����������: ������� ��������: Properties ��� ��������� ��������� �� �� "AutoPay"

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

-- ����� �������: 68395
-- ����� ������: 1.18
-- �����������: ���������� � ��� (���� ������) - �������������

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

-- ����� �������: 68519
-- ����� ������: 1.18
-- �����������: ������ ������ �� ��� - GET/CHECK ������� (��������)

ALTER TABLE OUTGOING_REQUESTS ADD PAYMENT_ID INTEGER;

-- ����� �������: 69063
-- ����� ������: 1.18
-- �����������: ��������� �� ���� (���) ����� 2
ALTER TABLE OUTGOING_REQUESTS RENAME COLUMN LOGIN_ID TO PERSON_ID;


-- ����� �������: 70427
-- ����� ������: 1.18
-- �����������: �������� ��� ���������� �������
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


-- ����� �������: 72543
-- ����� ������: 1.18
-- �����������: � ������� ��������� �������� ������ �������������� OPER_UID, �������� TB
ALTER TABLE OUTGOING_REQUESTS
MODIFY(OPER_UID NULL)
;

ALTER TABLE OUTGOING_REQUESTS
RENAME COLUMN PERSON_ID TO LOGIN_ID
;

ALTER TABLE OUTGOING_REQUESTS
ADD (TB VARCHAR2(4) NULL)
;






-- ����� �������: 82300
-- ����� ������: 1.18
-- �����������: BUG090575: [����] ������ ��������. ���������� ����������� ����������� ����� ��������

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