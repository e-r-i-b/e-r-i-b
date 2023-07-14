-- ����� �������: 
-- ����� ������: 1.18
-- �����������: �������� ������ ���������������� �������� �����

CREATE TABLE OFFER_OFFICE_PRIOR
(
  ID                     INTEGER                NOT NULL,
  RQ_UID                 VARCHAR2(32 BYTE),
  OFFER_DATE             TIMESTAMP(6),
  APPLICATION_NUMBER     VARCHAR2(20 BYTE),
  FIRST_NAME             VARCHAR2(160 BYTE),
  LAST_NAME              VARCHAR2(160 BYTE),
  MIDDLE_NAME            VARCHAR2(160 BYTE),
  ID_TYPE                VARCHAR2(4 BYTE),
  ID_SERIES              VARCHAR2(12 BYTE),
  ID_NUM                 VARCHAR2(12 BYTE),
  BIRTH_DATE             TIMESTAMP(6),  
  CLIENT_CATEGORY        VARCHAR2(10 BYTE),
  ALT_PERIOD             INTEGER,
  ALT_AMOUNT             NUMBER(17,2),
  ALT_INTEREST_RATE      NUMBER(6,2),
  ALT_FULL_LOAN_COST     NUMBER(16,2),
  ALT_ANNUITY_PAYMENT    NUMBER(3,3),
  ALT_CREDIT_CARD_LIMIT  NUMBER(17,2),
  STATE                    VARCHAR2(10 BYTE)
 
)
partition by range
(OFFER_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
(partition
         P_FIRST_OOP
        values less than (to_date('15-06-2015','DD-MM-YYYY')))
;
ALTER TABLE OFFER_OFFICE_PRIOR ADD (CONSTRAINT OFFER_OFFICE_PRIOR_PK PRIMARY KEY (ID));
create sequence S_OFFER_OFFICE_PRIOR;

-- ����� �������:  83928
-- ����� ������: 1.19
-- �����������: ������������� ������ �.3
				��������� ��������� �� ���������� �����
				��������� ����� ���� � �� ���������� ������
				��������� ��� ������ ��� ��������� ����� 

alter table OFFER_OFFICE_PRIOR add PRODUCT_TYPE_CODE varchar2(10)
/
alter table OFFER_OFFICE_PRIOR add PRODUCT_CODE varchar2(10)
/
alter table OFFER_OFFICE_PRIOR add SUB_PRODUCT_CODE varchar2(10)
/
alter table OFFER_OFFICE_PRIOR add DEPARTMENT varchar2(11)
/
alter table OFFER_OFFICE_PRIOR add CURRENCY varchar2(3)
/

-- ����� �������:  84033
-- ����� ������: 1.19
-- �����������: ������������� ������ �.3 
				��������� ����� ���� ��� ����� ����������, ���� ����������,
				��������� ��� ������ 

alter table OFFER_OFFICE_PRIOR add TYPE_OF_ISSUE char(1) 
/
alter table OFFER_OFFICE_PRIOR add ACCAUNT_NUMBER varchar2(25)
/
-- ����� �������:  84195
-- ����� ������: 1.18
-- �����������: ��������� ������. ������/����� �� �������. ����������� ������ ������.
				��������� ��������� ����� ���� ������ ��������� + ����� ������.
			
alter table OFFER_OFFICE_PRIOR add ID_ISSUE_BY varchar2(160)   
/
alter table OFFER_OFFICE_PRIOR add ID_ISSUE_DATE timestamp
/

-- ����� �������:  84238
-- ����� ������: 1.19
-- �����������: ���������� �������� ��.

/*==============================================================*/
/* Table: OFFICE_LOAN_CLAIM                                     */
/*==============================================================*/
create table OFFICE_LOAN_CLAIM 
(
   APPLICATION_NUMBER   VARCHAR2(20)         not null,
   STATE                INTEGER,
   NEED_VISIT_OFFICE_REASON VARCHAR2(255),
   FIO_KI               VARCHAR2(150),
   LOGIN_KI             VARCHAR2(50),
   FIO_TM               VARCHAR2(150),
   LOGIN_TM             VARCHAR2(50),
   DEPARTMENT           VARCHAR2(11),
   CHANNEL              VARCHAR2(255),
   AGREEMENT_DATE       TIMESTAMP(6),
   TYPE                 VARCHAR2(1),
   PRODUCT_CODE         VARCHAR2(10),
   SUB_PRODUCT_CODE     VARCHAR2(10),
   LOAN_AMOUNT          NUMBER(17,2),
   LOAN_PERIOD          INTEGER,
   CURRENCY             VARCHAR2(255),
   PAYMENT_TYPE         VARCHAR2(255),
   FIRST_NAME           VARCHAR2(255),
   LAST_NAME            VARCHAR2(255),
   MIDDLE_NAME          VARCHAR2(255),
   BIRTH_DAY            TIMESTAMP(6),
   CITIZEN              VARCHAR2(255),
   DOCUMENT_SERIES      VARCHAR2(4),
   DOCUMENT_NUMBER      VARCHAR2(6),
   PASSPORT_ISSUE_DATE  TIMESTAMP(6),
   PASSPORT_ISSUE_BY_CODE VARCHAR2(20),
   PASSPORT_ISSUE_BY    VARCHAR2(20),
   HAS_OLD_PASSPORT     CHAR(1),
   OLD_DOCUMENT_SERIES  VARCHAR2(4),
   OLD_DOCUMENT_NUMBER  VARCHAR2(6),
   OLD_PASSPORT_ISSUE_DATE TIMESTAMP(6),
   OLD_PASSPORT_ISSUE_BY VARCHAR2(100),
   CREATE_DATE          TIMESTAMP(6)         not null,
   TYPE_OF_ISSUE        VARCHAR2(1),
   CARD_NUMBER          VARCHAR2(19),
   ACCOUNT_NUMBER       VARCHAR2(25),
   PRODUCT_AMOUNT       NUMBER(17,2),
   PRODUCT_PERIOD       INTEGER,
   LOAN_RATE            NUMBER(6,2)
)
partition by range
 (CREATE_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_OOP
        values less than (to_date('01-07-2015','DD-MM-YYYY')));

/*==============================================================*/
/* Index: OFFICE_LC_APPLICATION_NUM_IDX                         */
/*==============================================================*/
create unique index OFFICE_LC_APPLICATION_NUM_IDX on OFFICE_LOAN_CLAIM (
   APPLICATION_NUMBER ASC
)
global partition by hash (APPLICATION_NUMBER) partitions 64;

/*==============================================================*/
/* Index: OFFICE_LC_FIO_BD_DOC_IDX                              */
/*==============================================================*/
create index OFFICE_LC_FIO_BD_DOC_IDX on OFFICE_LOAN_CLAIM (
   UPPER(REPLACE(REPLACE("LAST_NAME"||"FIRST_NAME"||"MIDDLE_NAME",' ',''),'-','')) ASC,
   UPPER(REPLACE("DOCUMENT_SERIES",' ','')||REPLACE("DOCUMENT_NUMBER",' ','')) ASC,
   BIRTH_DAY ASC,
   CREATE_DATE ASC
)
local;

-- ����� �������:  84502
-- ����� ������: 1.19
-- �����������: ��������� � ��������� ������ ����� ��� ����������� ������ ������� (ETSMOfferHelper, ����� getActualOfferOfficePrior)
alter table OFFICE_LOAN_CLAIM add (PREAPPROVED CHAR(1) not null);

create index OFFER_OP_FIO_BD_DOC_IDX on OFFER_OFFICE_PRIOR (
   UPPER(REPLACE(REPLACE("LAST_NAME"||"FIRST_NAME"||"MIDDLE_NAME",' ',''),'-','')) ASC,
   UPPER(REPLACE("ID_SERIES",' ','')||REPLACE("ID_NUM",' ','')) ASC,
   BIRTH_DATE ASC,
   OFFER_DATE ASC
)
local; 

-- ����� �������:  84761
-- ����� ������: 1.19
-- �����������: ����3:	������� � ������ '�������' ������ �� ����
						���� ����� ����������� ��� ������ �� ���� 
						
create index OFFER_PRIOR_APP_IDX on OFFER_OFFICE_PRIOR(APPLICATION_NUMBER)local
/
alter table OFFER_OFFICE_PRIOR add REGISTRATION_ADDRESS varchar2(160)
/

-- ����� �������:  84870
-- ����� ������: 1.19
-- �����������: ����3: ��������� ���� ����� ����������� � ������
						
alter table OFFICE_LOAN_CLAIM add REGISTRATION_ADDRESS varchar2(160)
/

-- ����� �������: 85157
-- ����� ������: v1.18
-- �����������: CHG092645: ������ : ����� ������ AltAnnuitentyPayment 
delete from OFFER_OFFICE_PRIOR
/
alter table OFFER_OFFICE_PRIOR modify   ALT_ANNUITY_PAYMENT  NUMBER(12,2)
/


