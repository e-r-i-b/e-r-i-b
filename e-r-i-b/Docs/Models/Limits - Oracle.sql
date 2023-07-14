/*==============================================================*/
/* Database name:  Limits                                       */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     17.06.2014 16:34:35                          */
/*==============================================================*/


DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROFILE_INFORMATION'; 
    EXECUTE IMMEDIATE('alter table PROFILE_INFORMATION drop constraint FK_PROFILE_INF_TO_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROFILE_ID'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROFILE_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'TRANSACTIONS_JOURNAL'; 
    EXECUTE IMMEDIATE('alter table TRANSACTIONS_JOURNAL drop constraint FK_TRANSACT_FK_PROFIL_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_PROFILES_UID'; 
    EXECUTE IMMEDIATE('drop index INDEX_PROFILES_UID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROFILES'; 
    EXECUTE IMMEDIATE('drop table PROFILES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROFILES';
    EXECUTE IMMEDIATE('drop sequence  S_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROFILE_INFORMATION'; 
    EXECUTE IMMEDIATE('drop table PROFILE_INFORMATION cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROPERTIES'; 
    EXECUTE IMMEDIATE('drop table PROPERTIES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROPERTIES';
    EXECUTE IMMEDIATE('drop sequence  S_PROPERTIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'PROFILE_DATE_I'; 
    EXECUTE IMMEDIATE('drop index PROFILE_DATE_I');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DOC_DATE_I'; 
    EXECUTE IMMEDIATE('drop index DOC_DATE_I');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'TRANSACTIONS_JOURNAL'; 
    EXECUTE IMMEDIATE('drop table TRANSACTIONS_JOURNAL cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_TRANSACTIONS_JOURNAL';
    EXECUTE IMMEDIATE('drop sequence  S_TRANSACTIONS_JOURNAL');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

/*==============================================================*/
/* Database: "Limits"                                           */
/*==============================================================*/
create database "Limits"
go

/*==============================================================*/
/* Table: PROFILES                                              */
/*==============================================================*/
create table PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100)        not null,
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_PROFILES primary key (ID)
)

go

create sequence S_PROFILES
go

/*==============================================================*/
/* Index: INDEX_PROFILES_UID                                    */
/*==============================================================*/
create unique index INDEX_PROFILES_UID on PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC
)
go

/*==============================================================*/
/* Table: PROFILE_INFORMATION                                   */
/*==============================================================*/
create table PROFILE_INFORMATION 
(
   PROFILE_ID           INTEGER              not null,
   INFORMATION_TYPE     VARCHAR2(128)        not null,
   DATA                 CLOB,
   constraint PK_PROFILE_INFORMATION primary key (PROFILE_ID, INFORMATION_TYPE)
)
go

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
/* Table: TRANSACTIONS_JOURNAL                                  */
/*==============================================================*/
create table TRANSACTIONS_JOURNAL 
(
   EXTERNAL_ID          VARCHAR2(100)        not null,
   DOCUMENT_EXTERNAL_ID VARCHAR2(100)        not null,
   PROFILE_ID           INTEGER              not null,
   AMOUNT               NUMBER(19,4),
   CURRENCY             CHAR(3),
   OPERATION_DATE       TIMESTAMP            not null,
   OPERATION_TYPE       VARCHAR2(10)         not null,
   CHANNEL_TYPE         VARCHAR2(25)         not null,
   LIMITS_INFO          VARCHAR2(4000)       not null
)
partition by range
 (OPERATION_DATE)
    interval (NUMTODSINTERVAL(1,'DAY'))
 (partition
         P_FIRST
        values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_TRANSACTIONS_JOURNAL
go

/*==============================================================*/
/* Index: DOC_DATE_I                                            */
/*==============================================================*/
create index DOC_DATE_I on TRANSACTIONS_JOURNAL (
   DOCUMENT_EXTERNAL_ID ASC,
   OPERATION_DATE ASC
)
local
go

/*==============================================================*/
/* Index: PROFILE_DATE_I                                        */
/*==============================================================*/
create index PROFILE_DATE_I on TRANSACTIONS_JOURNAL (
   PROFILE_ID ASC,
   OPERATION_DATE ASC
)
local
go

alter table PROFILE_INFORMATION
   add constraint FK_PROFILE_INF_TO_PROFILES foreign key (PROFILE_ID)
      references PROFILES (ID)
      on delete cascade
go


create index "DXFK_PROFILE_ID" on TRANSACTIONS_JOURNAL
(
   PROFILE_ID
)
go

alter table TRANSACTIONS_JOURNAL
   add constraint FK_TRANSACT_FK_PROFIL_PROFILES foreign key (PROFILE_ID)
      references PROFILES (ID)
go

