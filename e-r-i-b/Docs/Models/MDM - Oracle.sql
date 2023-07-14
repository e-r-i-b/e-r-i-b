/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     29.07.2015 17:24:37                          */
/*==============================================================*/


DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_ACCOUNT_TO_POFILE'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_ACCOUNT_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_ACCOUNTS'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_ACCOUNTS drop constraint FK_MDM_ACCOUNT_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_CARD_TO_PROFILE'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_CARD_TO_PROFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_CARDS'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_CARDS drop constraint FK_MDM_CARD_TO_PROFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_DEPO_TO_POFILE'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_DEPO_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_DEPO_ACCOUNTS'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_DEPO_ACCOUNTS drop constraint FK_MDM_DEPO_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_DOCUMENT_TO_POFILE'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_DOCUMENT_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_DOCUMENTS'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_DOCUMENTS drop constraint FK_MDM_DOCUMENT_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_IDS_TO_PROFILES'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_IDS_TO_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_INNER_IDS'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_INNER_IDS drop constraint FK_MDM_IDS_TO_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_LOAN_TO_POFILE'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_LOAN_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_LOANS'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_LOANS drop constraint FK_MDM_LOAN_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_MDM_SERVICE_TO_POFILE'; 
    EXECUTE IMMEDIATE('drop index DXFK_MDM_SERVICE_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_SERVICES'; 
    EXECUTE IMMEDIATE('alter table MDM_PROFILE_SERVICES drop constraint FK_MDM_SERVICE_TO_POFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILES'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILES';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_ACCOUNTS'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_ACCOUNTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILE_ACCOUNTS';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILE_ACCOUNTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_CARDS'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_CARDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILE_CARDS';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILE_CARDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_DEPO_ACCOUNTS'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_DEPO_ACCOUNTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILE_DEPO_ACCOUNTS';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILE_DEPO_ACCOUNTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_DOCUMENTS'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_DOCUMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILE_DOCUMENTS';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILE_DOCUMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_MDM_PROFILE_IDS_ID'; 
    EXECUTE IMMEDIATE('drop index I_MDM_PROFILE_IDS_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_INNER_IDS'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_INNER_IDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_LOANS'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_LOANS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILE_LOANS';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILE_LOANS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MDM_PROFILE_SERVICES'; 
    EXECUTE IMMEDIATE('drop table MDM_PROFILE_SERVICES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MDM_PROFILE_SERVICES';
    EXECUTE IMMEDIATE('drop sequence  S_MDM_PROFILE_SERVICES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

/*==============================================================*/
/* Table: MDM_PROFILES                                          */
/*==============================================================*/
create table MDM_PROFILES 
(
   ID                   INTEGER              not null,
   MDM_ID               VARCHAR2(64)         not null,
   LAST_NAME            VARCHAR2(320)        not null,
   FIRST_NAME           VARCHAR2(320)        not null,
   MIDDLE_NAME          VARCHAR2(320),
   GENDER               VARCHAR2(7)          not null,
   BIRTHDAY             TIMESTAMP,
   BIRTH_PLACE          VARCHAR2(510),
   TAX_ID               VARCHAR2(20),
   RESIDENT             CHAR(1)              not null,
   EMPLOYEE             CHAR(1)              not null,
   SHAREHOLDER          CHAR(1)              not null,
   INSIDER              CHAR(1)              not null,
   CITIZENSHIP          VARCHAR2(120),
   LITERACY             CHAR(1)              not null,
   constraint PK_MDM_PROFILES primary key (ID),
   constraint AK_MDM_PROFILE_ID unique (MDM_ID)
         using index (create unique index I_MDM_PROFILE_ID on MDM_PROFILES (
      MDM_ID ASC
   ))
)

go

create sequence S_MDM_PROFILES
go

/*==============================================================*/
/* Table: MDM_PROFILE_ACCOUNTS                                  */
/*==============================================================*/
create table MDM_PROFILE_ACCOUNTS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(255)        not null,
   START_DATE           TIMESTAMP,
   CLOSING_DATE         TIMESTAMP,
   constraint PK_MDM_PROFILE_ACCOUNTS primary key (ID)
)

go

create sequence S_MDM_PROFILE_ACCOUNTS
go

/*==============================================================*/
/* Table: MDM_PROFILE_CARDS                                     */
/*==============================================================*/
create table MDM_PROFILE_CARDS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(32)         not null,
   START_DATE           TIMESTAMP,
   EXPIRED_DATE         TIMESTAMP,
   constraint PK_MDM_PROFILE_CARDS primary key (ID)
)

go

create sequence S_MDM_PROFILE_CARDS
go

/*==============================================================*/
/* Table: MDM_PROFILE_DEPO_ACCOUNTS                             */
/*==============================================================*/
create table MDM_PROFILE_DEPO_ACCOUNTS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(255)        not null,
   constraint PK_MDM_PROFILE_DEPO_ACCOUNTS primary key (ID)
)

go

create sequence S_MDM_PROFILE_DEPO_ACCOUNTS
go

/*==============================================================*/
/* Table: MDM_PROFILE_DOCUMENTS                                 */
/*==============================================================*/
create table MDM_PROFILE_DOCUMENTS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER,
   TYPE                 VARCHAR2(80)         not null,
   DOC_SERIES           VARCHAR2(20),
   DOC_NUMBER           VARCHAR2(255)        not null,
   ISSUED_BY            VARCHAR2(510),
   ISSUED_DATE          TIMESTAMP,
   constraint PK_MDM_PROFILE_DOCUMENTS primary key (ID)
)

go

create sequence S_MDM_PROFILE_DOCUMENTS
go

/*==============================================================*/
/* Table: MDM_PROFILE_INNER_IDS                                 */
/*==============================================================*/
create table MDM_PROFILE_INNER_IDS 
(
   MDM_ID               VARCHAR2(64)         not null,
   CSA_PROFILE_ID       INTEGER              not null
)
go

/*==============================================================*/
/* Index: I_MDM_PROFILE_IDS_ID                                  */
/*==============================================================*/
create unique index I_MDM_PROFILE_IDS_ID on MDM_PROFILE_INNER_IDS (
   CSA_PROFILE_ID ASC
)
go

/*==============================================================*/
/* Table: MDM_PROFILE_LOANS                                     */
/*==============================================================*/
create table MDM_PROFILE_LOANS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(64)         not null,
   ADDITIONAL_NUMBER    VARCHAR2(64),
   LEGAL_NUMBER         VARCHAR2(200),
   LEGAL_NAME           VARCHAR2(255),
   START_DATE           TIMESTAMP,
   CLOSING_DATE         TIMESTAMP,
   constraint PK_MDM_PROFILE_LOANS primary key (ID)
)

go

create sequence S_MDM_PROFILE_LOANS
go

/*==============================================================*/
/* Table: MDM_PROFILE_SERVICES                                  */
/*==============================================================*/
create table MDM_PROFILE_SERVICES 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   TYPE                 VARCHAR2(80)         not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   constraint PK_MDM_PROFILE_SERVICES primary key (ID)
)

go

create sequence S_MDM_PROFILE_SERVICES
go


create index "DXFK_MDM_ACCOUNT_TO_POFILE" on MDM_PROFILE_ACCOUNTS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_ACCOUNTS
   add constraint FK_MDM_ACCOUNT_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_CARD_TO_PROFILE" on MDM_PROFILE_CARDS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_CARDS
   add constraint FK_MDM_CARD_TO_PROFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_DEPO_TO_POFILE" on MDM_PROFILE_DEPO_ACCOUNTS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_DEPO_ACCOUNTS
   add constraint FK_MDM_DEPO_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_DOCUMENT_TO_POFILE" on MDM_PROFILE_DOCUMENTS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_DOCUMENTS
   add constraint FK_MDM_DOCUMENT_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_IDS_TO_PROFILES" on MDM_PROFILE_INNER_IDS
(
   MDM_ID
)
go

alter table MDM_PROFILE_INNER_IDS
   add constraint FK_MDM_IDS_TO_PROFILES foreign key (MDM_ID)
      references MDM_PROFILES (MDM_ID)
      on delete cascade
go


create index "DXFK_MDM_LOAN_TO_POFILE" on MDM_PROFILE_LOANS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_LOANS
   add constraint FK_MDM_LOAN_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_SERVICE_TO_POFILE" on MDM_PROFILE_SERVICES
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_SERVICES
   add constraint FK_MDM_SERVICE_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go

