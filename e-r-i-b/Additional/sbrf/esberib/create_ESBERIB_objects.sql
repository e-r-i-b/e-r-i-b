declare
  c_PrimarySchema constant varchar2(100) := 'SBRF_118';
  c_SecondarySchema constant varchar2(100) := 'ESBERIB';
begin
   execute immediate
     'create user '||c_SecondarySchema||' profile "DEFAULT" identified by '||c_SecondarySchema||' account unlock
             default tablespace "USERS"
             temporary tablespace "TEMP"
             quota unlimited on "USERS"';
   execute immediate
   'grant connect, resource, create view to '||c_SecondarySchema;

  execute immediate 'alter session set current_schema = '||c_SecondarySchema;
  execute immediate
    'CREATE TABLE  GFL
    (
      ID          INTEGER,
      RBTBBRCHID  VARCHAR2(8 BYTE)                  NOT NULL,
      BIRTHDAY    DATE                              NOT NULL,
      LASTNAME    VARCHAR2(120 BYTE)                NOT NULL,
      FIRSTNAME   VARCHAR2(120 BYTE)                NOT NULL,
      MIDDLENAME  VARCHAR2(120 BYTE),
      IDTYPE      VARCHAR2(4 BYTE)                  NOT NULL,
      IDSERIES    VARCHAR2(12 BYTE),
      IDNUM       VARCHAR2(12 BYTE)                 NOT NULL,
      ISSUEDBY    VARCHAR2(255 BYTE),
      ISSUEDT     DATE,
      CARDNUM     VARCHAR2(20 BYTE)
    )';

  execute immediate 'COMMENT ON COLUMN  GFL.RBTBBRCHID IS ''��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��''';
  execute immediate 'COMMENT ON COLUMN  GFL.BIRTHDAY IS ''���������� � ���������� ����. ���� ��������''';
  execute immediate 'COMMENT ON COLUMN  GFL.LASTNAME IS ''������� ����������� ����''';

  execute immediate 'COMMENT ON COLUMN  GFL.FIRSTNAME IS ''��� ����������� ����''';

  execute immediate 'COMMENT ON COLUMN  GFL.MIDDLENAME IS ''�������� ����������� ����''';

  execute immediate 'COMMENT ON COLUMN  GFL.IDTYPE IS ''��� ���������. ���� ����������, �������������� ��������.  ������������ ��������� ����������� pb_document ��� ��� (���� code)''';

  execute immediate 'COMMENT ON COLUMN  GFL.IDSERIES IS ''����� ���������''';

  execute immediate 'COMMENT ON COLUMN  GFL.IDNUM IS ''����� ���������. ��� �������� way ����� ��������� ���������� � ���� � ��� ����, ��� ������ �� ���.''';

  execute immediate 'COMMENT ON COLUMN  GFL.ISSUEDBY IS ''��� �����''';

  execute immediate 'COMMENT ON COLUMN  GFL.ISSUEDT IS ''���� ������''';

  execute immediate 'COMMENT ON COLUMN  GFL.CARDNUM IS ''������������� ���������� �����. ����� �����''';

  execute immediate 'CREATE UNIQUE INDEX  GFL_PK ON  GFL (ID)';

  execute immediate 'CREATE INDEX  IDX_GFL ON  GFL (IDNUM, RBTBBRCHID)';

  execute immediate 'ALTER TABLE  GFL ADD ( CONSTRAINT GFL_PK PRIMARY KEY (ID))';

  execute immediate '
  CREATE TABLE  PERSONINFO
  (
    ID                   INTEGER                  NOT NULL,
    CUSTID               VARCHAR2(255 BYTE)       NOT NULL,
    BIRTHDAY             DATE                     NOT NULL,
    BIRTHPLACE           VARCHAR2(255 BYTE),
    TAXID                VARCHAR2(15 BYTE),
    CITIZENSHIP          VARCHAR2(60 BYTE),
    LASTNAME             VARCHAR2(120 BYTE)       NOT NULL,
    FIRSTNAME            VARCHAR2(120 BYTE)       NOT NULL,
    MIDDLENAME           VARCHAR2(120 BYTE),
    IDTYPE               VARCHAR2(4 BYTE)         NOT NULL,
    IDSERIES             VARCHAR2(12 BYTE),
    IDNUM                VARCHAR2(12 BYTE)        NOT NULL,
    ISSUEDBY             VARCHAR2(255 BYTE),
    ISSUEDCODE           VARCHAR2(10 BYTE),
    ISSUEDT              DATE,
    EXPDT                DATE,
    ADDITIONALINFO       VARCHAR2(255 BYTE),
    EMAILADDR            VARCHAR2(255 BYTE),
    MESSAGEDELIVERYTYPE  VARCHAR2(1 BYTE)         NOT NULL
  )';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.TAXID IS ''���''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.CITIZENSHIP IS ''�����������''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.LASTNAME IS ''�������''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.FIRSTNAME IS ''���''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.MIDDLENAME IS ''��������''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.IDTYPE IS ''��� ���������. ���� ����������, �������������� ��������. ������������ ��������� ����������� pb_document ��� ��� (���� code)''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.IDSERIES IS ''����� ���������.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.IDNUM IS ''����� ���������. ��� �������� way ����� ��������� ���������� � ���� � ��� ����, ��� ������ �� ���.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ISSUEDBY IS ''��� �����.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ISSUEDCODE IS ''��� �������������.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ISSUEDT IS ''���� ������.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.EXPDT IS ''������������ ��.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ADDITIONALINFO IS ''�������������� ����������.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.EMAILADDR IS ''����� ����������� �����.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.MESSAGEDELIVERYTYPE IS ''������������ �� ����� �� Email? E � ��, N � ���.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.CUSTID IS ''������������� �����������.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.BIRTHDAY IS ''���� ��������''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.BIRTHPLACE IS ''����� ��������''';


  execute immediate 'CREATE UNIQUE INDEX  PERSONINFO_PK ON  PERSONINFO (ID)';


  execute immediate 'ALTER TABLE  PERSONINFO ADD ( CONSTRAINT PERSONINFO_PK PRIMARY KEY (ID))';         

  execute immediate 'CREATE TABLE  CARDS (
    ID                  INTEGER                   NOT NULL,
    BRANCHID            VARCHAR2(5 BYTE)          NOT NULL,
    AGENCYID            VARCHAR2(5 BYTE)          NOT NULL,
    REGIONID            VARCHAR2(3 BYTE)          NOT NULL,
    RBBRCHID            VARCHAR2(6 BYTE)          NOT NULL,
    BANKACCTSTATUSCODE  VARCHAR2(16 BYTE),
    SYSTEMID            VARCHAR2(40 BYTE)         NOT NULL,
    CARDNUM             VARCHAR2(20 BYTE)         NOT NULL,
    ACCTID              VARCHAR2(24 BYTE),
    ACCTCODE            INTEGER                   NOT NULL,
    ACCTSUBCODE         INTEGER                   NOT NULL,
    CARDTYPE            VARCHAR2(2 BYTE)          NOT NULL,
    CARDNAME            VARCHAR2(50 BYTE)         NOT NULL,
    ACCTCUR             VARCHAR2(3 BYTE)          NOT NULL,
    ENDDT               DATE                      NOT NULL,
    PMTDT               DATE,
    ISSDT               DATE                      NOT NULL,
    CARDHOLDER          VARCHAR2(80 BYTE)         NOT NULL,
    MAINCARD            VARCHAR2(20 BYTE),
    ADDITIONALCARD      VARCHAR2(15 BYTE),
    ENDDTFORWAY         VARCHAR2(4 BYTE),
    ACCTBAL             VARCHAR2(255 BYTE),
    PERSONINFO          INTEGER,
    STATUSDESC          VARCHAR2(255 BYTE)
  )';

  execute immediate 'COMMENT ON COLUMN  CARDS.BRANCHID IS ''����� �������, � ������� ������� �������. �� ����� ���� ������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.AGENCYID IS ''����� ���''';

  execute immediate 'COMMENT ON COLUMN  CARDS.REGIONID IS ''����� ��������(��) ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.RBBRCHID IS ''����� ���, �������� ���� �����''';

  execute immediate 'COMMENT ON COLUMN  CARDS.BANKACCTSTATUSCODE IS ''��� �������. Active � ��������, Stoped � ����������, Blocked � ���������������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.SYSTEMID IS ''������������� �������-��������� ��������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDNUM IS ''����� �����''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTID IS ''����� ���������� ����� ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTCODE IS ''��� ������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTSUBCODE IS ''������ ������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDTYPE IS ''��� �����. debit (���������), credit (���������), overdraft (������������)''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDNAME IS ''������������ �����. ������������ ���� �����, ������� ��� ����������� ������������.''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTCUR IS ''������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ENDDT IS ''���� ��������� �������� �����''';

  execute immediate 'COMMENT ON COLUMN  CARDS.PMTDT IS ''���� �������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ISSDT IS ''���� ������� �����''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDHOLDER IS ''��� �� �����''';

  execute immediate 'COMMENT ON COLUMN  CARDS.MAINCARD IS ''����� �������� �����. ���������� � ������, ���� ������ ����� � ��������������, ����� ������ �������������''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ADDITIONALCARD IS ''��� �������������� �����. ���������� � ������, ���� ������ ����� � ��������������, ����� ������ �������������. ��������� ��������: �Client2Client� - �����, ���������� �� ��� ������� � � ��� ������������ �����, �Client2Other� - �������� ����� ����� � ��������� ������ ����.''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ENDDTFORWAY IS ''���� �������� ����� � ������� ����������� YYMM ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTBAL IS ''��� �������: 1)��������� ��������� ����� �� ����� (�Avail�), 2)��������� ����� ��� ��������� �������� (�AvailCash�), 3)��������� ����� ��� ������ �������/����� (�AvailPmt�) ��� ��������� ���� � ���� � �����������: 4)����� ������������� (�Debt�) 5)����� ������������ ������� (�MinPmt�) 6) ����� ���������� ������ � ��������������� ������ ����������� ��������� (�CR_LIMIT�) 7) ����������� ������ ��������� ��� ����� �������� ����������� liability-���������� (�OWN_BALANCE�)''';

  execute immediate 'COMMENT ON COLUMN  CARDS.PERSONINFO IS ''���������� � ���������.''';

  execute immediate 'COMMENT ON COLUMN  CARDS.STATUSDESC IS ''�������� ������� �����''';


  execute immediate 'CREATE UNIQUE INDEX  CARDS_PK ON  CARDS (ID)';


  execute immediate 'ALTER TABLE  CARDS ADD ( CONSTRAINT CARDS_PK PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  CARDS ADD ( FOREIGN KEY (PERSONINFO) REFERENCES  PERSONINFO (ID))';

  execute immediate 'CREATE TABLE  DEPOSIT
  (
    ID                          INTEGER           NOT NULL,
    BRANCHID                    VARCHAR2(5 BYTE)  NOT NULL,
    AGENCYID                    VARCHAR2(5 BYTE)  NOT NULL,
    REGIONID                    VARCHAR2(3 BYTE)  NOT NULL,
    RBBRCHID                    VARCHAR2(6 BYTE)  NOT NULL,
    SYSTEMID                    VARCHAR2(40 BYTE) NOT NULL,
    ACCTID                      VARCHAR2(24 BYTE) NOT NULL,
    ACCTCUR                     VARCHAR2(3 BYTE)  NOT NULL,
    ACCTNAME                    VARCHAR2(255 BYTE) NOT NULL,
    ACCTCODE                    INTEGER           NOT NULL,
    ACCTSUBCODE                 INTEGER           NOT NULL,
    OPENDATE                    DATE,
    STATUS                      VARCHAR2(16 BYTE),
    INTERESTONDEPOSITACCTID     VARCHAR2(24 BYTE),
    INTERESTONDEPOSITCARDNUM    VARCHAR2(20 BYTE),
    PERIOD                      INTEGER,
    RATE                        NUMBER(15,4),
    ENDDATE                     DATE,
    ISCREDITALLOWED             CHAR(1 BYTE)      DEFAULT 0,
    ISDEBITALLOWED              CHAR(1 BYTE)      DEFAULT 0,
    ISPROLONGATIONALLOWED       CHAR(1 BYTE)      DEFAULT 0,
    ISCREDITCROSSAGENCYALLOWED  CHAR(1 BYTE)      DEFAULT 0,
    ISDEBITCROSSAGENCYALLOWED   CHAR(1 BYTE)      DEFAULT 0,
    ISPASSBOOK                  CHAR(1 BYTE)      DEFAULT 0,
    BANKACCTPERMISS             VARCHAR2(255 BYTE),
    ACCTBAL                     VARCHAR2(255 BYTE) NOT NULL,
    PERSONINFO                  INTEGER,
    ISNEEDDEPACCINFO            CHAR(1 BYTE)      DEFAULT 0                     NOT NULL,
    ISNEEDBANKACCTPERMISS       CHAR(1 BYTE)      DEFAULT 0                     NOT NULL
  )';

  execute immediate 'COMMENT ON COLUMN  DEPOSIT.BRANCHID IS ''����� �������, � ������� ������ ����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.AGENCYID IS ''������������� ��������� (���), � ������� ������ ����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.REGIONID IS ''����� ��������(��)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.RBBRCHID IS ''����� ���, �������� ���� �� ������''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.SYSTEMID IS ''������������� �������-��������� ��������''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTID IS ''����� �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTCUR IS ''������ �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTNAME IS ''������� ������������ ����� �� ���� qsname ����������� qvb ��� ��� ������ ��������� ����� �� ������� ����� ��������� ������� ��� ����������� ������������ ��� ����.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTCODE IS ''��� ������''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTSUBCODE IS ''������ ������''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.OPENDATE IS ''���� �������� �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.STATUS IS ''������ ����� :Opened � ������,.Closed � ������, Arrested � ���������, Lost-passbook � ������� ����������.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.INTERESTONDEPOSITACCTID IS ''����������, ��������������� ��� ������������ ���������. ����� �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.INTERESTONDEPOSITCARDNUM IS ''����������, ��������������� ��� ������������ ���������. ����� �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.PERIOD IS ''���� ������ � ����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.RATE IS ''���������� ������ �� ������''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ENDDATE IS ''���� �������� �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISCREDITALLOWED IS ''��������� �� �������� �� �����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISDEBITALLOWED IS ''��������� �� ���������� �� ����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISPROLONGATIONALLOWED IS ''��������� �� ����������� �� ��������� ����''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISCREDITCROSSAGENCYALLOWED IS ''��������� �� �������� �� ����� � ������ ��� (������� �������� �����)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISDEBITCROSSAGENCYALLOWED IS ''��������� �� ���������� �� ���� � ������ ��� (������� �������� �����)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISPASSBOOK IS ''������� ������� ����������''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.BANKACCTPERMISS IS ''����� ������� � ����� ��� �����. ��� ��� ���������� 2. ����������� ��� ����������� �������, ��� ������� ������������� �����, �������� ������������� (�BP_ES� - ����������, �BP_ERIB� - ����),  � ����� ������� ���������� ���������� �����(���� false � �� ���� �����). ������������ � ���� ���� ��� ������� [���] - [�������], ������������� ����� �������.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTBAL IS ''��� �������: 1)��������� ��������� ����� �� ����� (�Avail�), 2)��������� ����� ��� ��������� �������� (�AvailCash�), 3)��������� ����� ��� ������ �������/����� (�AvailPmt�) ��� ��������� ���� � ���� � �����������: 4)����� ������������� (�Debt�) 5)����� ������������ ������� (�MinPmt�) 6) ����� ���������� ������ � ��������������� ������ ����������� ��������� (�CR_LIMIT�) 7) ����������� ������ ��������� ��� ����� �������� ����������� liability-���������� (�OWN_BALANCE�)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.PERSONINFO IS ''���������� � ���������.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISNEEDDEPACCINFO IS ''��������� ��  ���������� � ������� �������������� ���������� �� ������ (��� DepAccInfo �������� ������)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISNEEDBANKACCTPERMISS IS ''��������� �� ���������� ���������� � ������ ������� � ����� (��� BankAcctPermiss �������� ������)''';
  execute immediate 'CREATE UNIQUE INDEX  DEPOSIT_PK ON  DEPOSIT (ID)';

  execute immediate 'ALTER TABLE  DEPOSIT ADD ( CONSTRAINT DEPOSIT_PK PRIMARY KEY (ID))';
  execute immediate 'ALTER TABLE  DEPOSIT ADD ( FOREIGN KEY (PERSONINFO) REFERENCES  PERSONINFO (ID))';

  execute immediate 'CREATE TABLE  IMACCOUNT
  (
    ID               INTEGER                      NOT NULL,
    BRANCHID         VARCHAR2(5 BYTE)             NOT NULL,
    AGENCYID         VARCHAR2(5 BYTE)             NOT NULL,
    REGIONID         VARCHAR2(3 BYTE)             NOT NULL,
    RBBRCHID         VARCHAR2(6 BYTE)             NOT NULL,
    SYSTEMID         VARCHAR2(40 BYTE)            NOT NULL,
    ACCTID           VARCHAR2(22 BYTE)            NOT NULL,
    ACCTCUR          VARCHAR2(3 BYTE)             NOT NULL,
    ACCTNAME         VARCHAR2(30 BYTE)            NOT NULL,
    STARTDATE        DATE                         NOT NULL,
    ENDDATE          DATE,
    STATUS           VARCHAR2(16 BYTE),
    AGREEMENTNUMBER  VARCHAR2(64 BYTE),
    PERSONINFO       INTEGER,
    ACCTBAL          VARCHAR2(255 BYTE)           NOT NULL
  )';

  execute immediate 'comment on column IMACCOUNT.BRANCHID is ''����� �������(���)''';
  execute immediate 'comment on column IMACCOUNT.AGENCYID is ''����� �������������(���)''';
  execute immediate 'comment on column IMACCOUNT.REGIONID is ''����� ��������(��)''';
  execute immediate 'comment on column IMACCOUNT.RBBRCHID is ''����� ���, �������� ���� ����''';
  execute immediate 'comment on column IMACCOUNT.SYSTEMID is ''������������� �������-��������� ��������''';
  execute immediate 'comment on column IMACCOUNT.ACCTID is ''����� �����''';
  execute immediate 'comment on column IMACCOUNT.ACCTCUR is ''��� �������''';
  execute immediate 'comment on column IMACCOUNT.ACCTNAME is ''������� ������������ �� ������� �����, ������� ��� ����������� ������������''';
  execute immediate 'comment on column IMACCOUNT.STARTDATE is ''���� �������� �����''';
  execute immediate 'comment on column IMACCOUNT.ENDDATE is ''���� �������� �����. ����������� ��� �������� ������.''';
  execute immediate 'comment on column IMACCOUNT.STATUS is ''������ ��� (Opened � ������, Closed � ������)''';
  execute immediate 'comment on column IMACCOUNT.AGREEMENTNUMBER is ''����� �������� ����� ���''';
  execute immediate 'comment on column IMACCOUNT.PERSONINFO is ''���������� � ���������''';
  execute immediate 'comment on column IMACCOUNT.ACCTBAL is ''������ � ������� �� ������� � ������ ��������. ������ ���� Avail - [�������], AvailCash - [����� c�������]. ������� � ������� (�������� ������� ����� ������ ���� ������� � ��������� �� ������� �����)''';
  
  execute immediate 'ALTER TABLE  IMACCOUNT ADD ( CONSTRAINT IMACCOUNT_PK PRIMARY KEY (ID))';
  execute immediate 'ALTER TABLE  IMACCOUNT ADD ( CONSTRAINT IMACCOUNT_R01 FOREIGN KEY (PERSONINFO) REFERENCES  PERSONINFO (ID))';

  execute immediate 'CREATE TABLE  CRDWI
  (
    ID          INTEGER                           NOT NULL,
    RBTBBRCHID  VARCHAR2(8 BYTE)                  NOT NULL,
    SYSTEMID    VARCHAR2(40 BYTE),
    CARDNUM     VARCHAR2(20 BYTE)                 NOT NULL,
    RBBRCHID    VARCHAR2(6 BYTE),
    CARDID      INTEGER
  )';
  
  execute immediate 'create index idx_crdwi_RBTBBRCHID_CARDNUM on CRDWI (rbtbbrchid, cardnum)';

  execute immediate 'COMMENT ON COLUMN  CRDWI.RBTBBRCHID IS ''��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.SYSTEMID IS ''������������� �������-��������� �������� ''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.CARDNUM IS ''����� �����''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.RBBRCHID IS ''����� ���, �������� �����''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.CARDID IS ''������������� �����, ������������ �� ������ �������''';

  execute immediate 'CREATE UNIQUE INDEX  CRDWI_PK ON  CRDWI (ID)';

  execute immediate 'ALTER TABLE  CRDWI ADD ( CONSTRAINT CRDWI_PK PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  CRDWI ADD ( FOREIGN KEY (CARDID) REFERENCES  CARDS (ID))';

  execute immediate 'CREATE TABLE  ACC_DI
  (
    ID          INTEGER                           NOT NULL,
    RBTBBRCHID  VARCHAR2(8 BYTE)                  NOT NULL,
    SYSTEMID    VARCHAR2(40 BYTE)                 NOT NULL,
    ACCTID      VARCHAR2(24 BYTE)                 NOT NULL,
    RBBRCHID    VARCHAR2(6 BYTE),
    DEPOSITID   INTEGER
  )';

  execute immediate 'create index idx_accdi_RBTBBRCHID_ACCTID on ACC_DI (rbtbbrchid,acctid)';
  
  execute immediate 'COMMENT ON COLUMN  ACC_DI.RBTBBRCHID IS ''��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.SYSTEMID IS ''������������� �������-��������� ��������''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.ACCTID IS ''����� �����''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.RBBRCHID IS ''����� ���, �������� ���� �� ������''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.DEPOSITID IS ''������������� ������, ������������� �� ������ �������''';

  execute immediate 'CREATE UNIQUE INDEX  ACC_DI_PK ON  ACC_DI (ID)';

  execute immediate 'ALTER TABLE  ACC_DI ADD ( CONSTRAINT ACC_DI_PK PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  ACC_DI ADD ( FOREIGN KEY (DEPOSITID) REFERENCES  DEPOSIT (ID))';

  execute immediate 'CREATE TABLE  IMA_IS
  (
    ID           INTEGER                          NOT NULL,
    RBTBBRCHID   VARCHAR2(8 BYTE)                 NOT NULL,
    SYSTEMID     VARCHAR2(40 BYTE)                NOT NULL,
    ACCTID       VARCHAR2(22 BYTE)                NOT NULL,
    RBBRCHID     VARCHAR2(6 BYTE),
    IMACCOUNTID  INTEGER
  )';

  execute immediate 'create index idx_imais_RBTBBRCHID_ACCTID on IMA_IS (rbtbbrchid,acctid)';
  
  execute immediate 'COMMENT ON COLUMN  IMA_IS.RBTBBRCHID IS ''��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.SYSTEMID IS ''������������� �������-��������� ��������''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.ACCTID IS ''����� �����''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.RBBRCHID IS ''����� ���, �������� ���� ���''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.IMACCOUNTID IS ''������������� ���, ������������� �� ������ �������''';
  
  execute immediate 'ALTER TABLE  IMA_IS ADD ( PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  IMA_IS ADD ( FOREIGN KEY (IMACCOUNTID) REFERENCES  IMACCOUNT (ID) ON DELETE SET NULL)';
  
  execute immediate 'create table CREDIT
  (
    ID         INTEGER not null,
    SYSTEMID   VARCHAR2(20) not null,
	ACCTID     VARCHAR2(30) not null,
	AGREEMTNUM VARCHAR2(16) not null,
	PRODTYPE   VARCHAR2(6) not null,
	LOANTYPE   VARCHAR2(30) not null,
	ACCTCUR    VARCHAR2(3) not null,
	CURAMT     NUMBER(15,4) not null,
	BRANCHID   VARCHAR2(5),
	AGENCYID   VARCHAR2(5),
	REGIONID   VARCHAR2(3) not null,
	RBBRCHID   VARCHAR2(6) not null,
	ANN        CHAR(1) default 0 not null,
	STARTDT    DATE not null,
	EXPDT      DATE not null,
	PERSONINFO INTEGER not null,
	PERSONROLE CHAR(1) default 1 not null
  )';
  
  execute immediate 'comment on column CREDIT.SYSTEMID is ''������������� �������-��������� ��������''';
  execute immediate 'comment on column CREDIT.ACCTID is ''����� �������� �����''';
  execute immediate 'comment on column CREDIT.AGREEMTNUM is ''����� ���������� ��������''';
  execute immediate 'comment on column CREDIT.PRODTYPE is ''(�� � ����) ������������� ����������� ��������''';
  execute immediate 'comment on column CREDIT.LOANTYPE is ''������� �������� �������''';
  execute immediate 'comment on column CREDIT.ACCTCUR is ''������''';
  execute immediate 'comment on column CREDIT.CURAMT is ''����� �� ��������''';
  execute immediate 'comment on column CREDIT.BRANCHID is ''����� �������, � ������� ������� �������''';
  execute immediate 'comment on column CREDIT.AGENCYID is ''����� ���, ��� ������� ������''';
  execute immediate 'comment on column CREDIT.REGIONID is ''����� ��������(��)''';
  execute immediate 'comment on column CREDIT.RBBRCHID is ''����� ���, ��� ������� ������� ����''';
  execute immediate 'comment on column CREDIT.ANN is ''������� ������������� (True-�����������, false � ������������������)''';
  execute immediate 'comment on column CREDIT.STARTDT is ''���� ���������� ��������''';
  execute immediate 'comment on column CREDIT.EXPDT is ''���� ���������/�������� ��������''';
  execute immediate 'comment on column CREDIT.PERSONINFO is ''������ � ��������''';
  execute immediate 'comment on column CREDIT.PERSONROLE is ''���� ������� � �������� (1 � �������, ���������, 2 � ����������, ������������)''';
  
  execute immediate 'alter table CREDIT add constraint CREDIT_PK primary key (ID)';
  execute immediate 'alter table CREDIT add foreign key (PERSONINFO) references PERSONINFO (ID)';

  execute immediate 'CREATE TABLE LN_CI
  (
	ID         INTEGER not null,
	RBTBBRCHID VARCHAR2(8) not null,
	SYSTEMID   VARCHAR2(20),
	ACCTID     VARCHAR2(24) not null,
	RBBRCHID   VARCHAR2(6),
	CREDITID   INTEGER
  )';
  execute immediate 'comment on column LN_CI.RBTBBRCHID is ''��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��''';
  execute immediate	'comment on column LN_CI.SYSTEMID is ''������������� �������-��������� ��������''';
  execute immediate 'comment on column LN_CI.ACCTID is ''����� �������� �����''';
  execute immediate 'comment on column LN_CI.RBBRCHID is ''����� ���, ��� ������� ������� ����''';
  execute immediate 'comment on column LN_CI.CREDITID is ''������, ������������ �� ������ �������''';
  
  execute immediate 'alter table LN_CI add constraint LN_CI_PK primary key (ID)';
  execute immediate 'alter table LN_CI add foreign key (CREDITID) references CREDIT (ID)';
  execute immediate 'create index idx_lnci_RBTBBRCHID_ACCTID on ln_ci (rbtbbrchid,acctid)';
  
  execute immediate 'CREATE TABLE  GFL_PRODUCTS
  (
    ID            INTEGER                         NOT NULL,
    GFL_ID        INTEGER                         NOT NULL,
    PRODUCT_ID    INTEGER                         NOT NULL,
    PRODUCT_TYPE  VARCHAR2(10 BYTE)               NOT NULL
  )';

  execute immediate 'COMMENT ON COLUMN  GFL_PRODUCTS.GFL_ID IS ''������������� ������� GFL, �� �������� ������ ���� ������� �������''';
  execute immediate 'COMMENT ON COLUMN  GFL_PRODUCTS.PRODUCT_ID IS ''������������� ��������''';
  execute immediate 'COMMENT ON COLUMN  GFL_PRODUCTS.PRODUCT_TYPE IS ''��� ��������. ��������� ��������: Deposit, IMA, Card, Credit, DepoAcc, LongOrd''';
  execute immediate 'CREATE UNIQUE INDEX  GFL_PRODUCTS_PK ON  GFL_PRODUCTS (ID)';
  execute immediate 'CREATE INDEX  IDX_GFL_PRODUCTS ON  GFL_PRODUCTS (GFL_ID)';
  execute immediate 'ALTER TABLE  GFL_PRODUCTS ADD ( CONSTRAINT GFL_PRODUCTS_PK PRIMARY KEY (ID), UNIQUE (GFL_ID, PRODUCT_ID, PRODUCT_TYPE) )';

  execute immediate 'CREATE SEQUENCE S_ACCDI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_CARDS
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_CRDWI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
	
  execute immediate 'create sequence S_CREDIT
	START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_DEPOSIT
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_GFL
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_GFL_PRODUCT
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_IMACCOUNT
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_IMAIS
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_PERSONINFO
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
        
  execute immediate 'CREATE SEQUENCE S_LNCI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
  
  execute immediate 'grant select on s_personinfo to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on PERSONINFO to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_gfl to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on GFL to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_cards to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on CARDS to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_gfl_product to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on gfl_products to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_crdwi to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on crdwi to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_deposit to '|| c_PrimarySchema;  
  execute immediate 'grant select,insert on deposit to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_accdi to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on acc_di to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_imaccount to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on imaccount to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_imais to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on ima_is to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_credit to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on credit to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_lnci to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on ln_ci to '|| c_PrimarySchema;
end;
