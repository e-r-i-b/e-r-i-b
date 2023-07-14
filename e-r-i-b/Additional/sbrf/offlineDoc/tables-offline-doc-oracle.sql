DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'OFFLINE_DOCUMENT_INFO'; 
    EXECUTE IMMEDIATE('drop table OFFLINE_DOCUMENT_INFO cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_OFFLINE_DOCUMENT_INFO';
    EXECUTE IMMEDIATE('drop sequence  S_OFFLINE_DOCUMENT_INFO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BASKETINVOICE_PROCESS_STATE'; 
    EXECUTE IMMEDIATE('drop table BASKETINVOICE_PROCESS_STATE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_BASKET_ROUTE_INFO_OPER_UID'; 
    EXECUTE IMMEDIATE('drop index I_BASKET_ROUTE_INFO_OPER_UID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BASKET_ROUTE_INFO'; 
    EXECUTE IMMEDIATE('drop table BASKET_ROUTE_INFO cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'U_PROPETIES_CATEGORY_KEY'; 
    EXECUTE IMMEDIATE('drop index U_PROPETIES_CATEGORY_KEY');
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

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'OUTGOING_REQUESTS'; 
    EXECUTE IMMEDIATE('drop table OUTGOING_REQUESTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

/*==============================================================*/
/* Table: OFFLINE_DOCUMENT_INFO                                 */
/*==============================================================*/
create table OFFLINE_DOCUMENT_INFO 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(80)         not null,
   BLOCK_NUMBER         INTEGER              not null,
   ADAPTER_UUID         VARCHAR2(64)         not null,
   DOC_TYPE             VARCHAR2(150),
   STATE_CODE           VARCHAR2(25),
   STATE_DESCRIPTION    VARCHAR2(265),
   ADDIT_INFO           CLOB,
   constraint PK_OFFLINE_DOCUMENT_INFO primary key (ID)
)

go

create sequence S_OFFLINE_DOCUMENT_INFO
go

create unique index I_OFFLINE_DOCUMENT_INFO on OFFLINE_DOCUMENT_INFO (
   EXTERNAL_ID ASC
)
go

/*==============================================================*/
/* Table: BASKETINVOICE_PROCESS_STATE                           */
/*==============================================================*/
create table BASKETINVOICE_PROCESS_STATE 
(
   KEY                  VARCHAR2(32)
)
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

/*==============================================================*/
/* Table: OUTGOING_REQUESTS                                     */
/*==============================================================*/
create table OUTGOING_REQUESTS 
(
   RQ_UID               VARCHAR2(32)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   REQUEST_TYPE         VARCHAR2(50)         not null,
   OPER_UID             VARCHAR2(32)         not null,
   NODE_ID              INTEGER              not null,
   PERSON_ID            INTEGER              not null,
   PAYMENT_ID           INTEGER,
   constraint PK_OUTGOING_REQUESTS primary key (RQ_UID)
)
go