/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     24.02.2015 17:37:05                          */
/*==============================================================*/


drop procedure "create_sequence"
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXREFERENCE_8'; 
    EXECUTE IMMEDIATE('drop index DXREFERENCE_8');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SHOP_ORDERS'; 
    EXECUTE IMMEDIATE('alter table SHOP_ORDERS drop constraint FK_SHOP_ORD_REFERENCE_SHOP_PRO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UNIQ_PROV'; 
    EXECUTE IMMEDIATE('drop index UNIQ_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FACILITATOR_PROVIDERS'; 
    EXECUTE IMMEDIATE('drop table FACILITATOR_PROVIDERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_FACILITATOR_PROVIDERS';
    EXECUTE IMMEDIATE('drop sequence  S_FACILITATOR_PROVIDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_MBK_CAST_PHONE'; 
    EXECUTE IMMEDIATE('drop index IDX_MBK_CAST_PHONE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CAST'; 
    EXECUTE IMMEDIATE('drop table MBK_CAST cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PHONE_UPDATE_JURNAL'; 
    EXECUTE IMMEDIATE('drop table PHONE_UPDATE_JURNAL cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'PROPERTIES_UNIQ'; 
    EXECUTE IMMEDIATE('drop index PROPERTIES_UNIQ');
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
    WHERE UPPER (index_name) = 'IDX_SHOP_NOTIF_STATE_DATE'; 
    EXECUTE IMMEDIATE('drop index IDX_SHOP_NOTIF_STATE_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SHOP_NOTIFICATIONS'; 
    EXECUTE IMMEDIATE('drop table SHOP_NOTIFICATIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SHOP_ORD_UUID'; 
    EXECUTE IMMEDIATE('drop index IDX_SHOP_ORD_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SH_ORD_PROF_ID_DATE'; 
    EXECUTE IMMEDIATE('drop index IDX_SH_ORD_PROF_ID_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SH_ORD_EXT_ID_REC_CODE'; 
    EXECUTE IMMEDIATE('drop index IDX_SH_ORD_EXT_ID_REC_CODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SHOP_ORDERS_ID'; 
    EXECUTE IMMEDIATE('drop index IDX_SHOP_ORDERS_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SHOP_ORDERS'; 
    EXECUTE IMMEDIATE('drop table SHOP_ORDERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SHOP_ORDERS';
    EXECUTE IMMEDIATE('drop sequence  S_SHOP_ORDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SHOP_PROFILE_UNIQ'; 
    EXECUTE IMMEDIATE('drop index IDX_SHOP_PROFILE_UNIQ');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SHOP_PROFILES'; 
    EXECUTE IMMEDIATE('drop table SHOP_PROFILES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SHOP_PROFILES';
    EXECUTE IMMEDIATE('drop sequence  S_SHOP_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SH_RECALLS_ORDER_UUID'; 
    EXECUTE IMMEDIATE('drop index IDX_SH_RECALLS_ORDER_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SHOP_RECALLS'; 
    EXECUTE IMMEDIATE('drop table SHOP_RECALLS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

/*==============================================================*/
/* Table: FACILITATOR_PROVIDERS                                 */
/*==============================================================*/
create table FACILITATOR_PROVIDERS 
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
   MB_CHECK_ENABLED     CHAR(1)               not null
)

go

create sequence S_FACILITATOR_PROVIDERS
go

alter table FACILITATOR_PROVIDERS
   add constraint PK_FACILITATOR_PROVIDERS primary key (ID)
go

/*==============================================================*/
/* Index: UNIQ_PROV                                             */
/*==============================================================*/
create unique index UNIQ_PROV on FACILITATOR_PROVIDERS (
   FACILITATOR_CODE ASC,
   CODE ASC
)
go

/*==============================================================*/
/* Table: MBK_CAST                                              */
/*==============================================================*/
create table MBK_CAST 
(
   PHONE                VARCHAR2(11)         not null
)
partition by hash
 (PHONE)
 partitions 128
go

/*==============================================================*/
/* Index: IDX_MBK_CAST_PHONE                                    */
/*==============================================================*/
create index IDX_MBK_CAST_PHONE on MBK_CAST (
   PHONE ASC
)
local
go

/*==============================================================*/
/* Table: PHONE_UPDATE_JURNAL                                   */
/*==============================================================*/
create table PHONE_UPDATE_JURNAL 
(
   UPDATE_DATE          TIMESTAMP            not null,
   UPDATED_ID           INTEGER              not null,
   NEW_ITEM             CHAR(1)              not null
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
/* Table: SHOP_NOTIFICATIONS                                    */
/*==============================================================*/
create table SHOP_NOTIFICATIONS 
(
   CREATE_DATE          TIMESTAMP            not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(30)         not null,
   NOTIF_STATUS         VARCHAR2(30)         not null,
   NOTIF_DATE           TIMESTAMP,
   NOTIF_COUNT          INTEGER              not null,
   NOTIF_STATUS_DESCRIPTION VARCHAR2(255),
   RECEIVER_CODE        VARCHAR2(32)         not null
)
partition by range
 (CREATE_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SN
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: IDX_SHOP_NOTIF_STATE_DATE                             */
/*==============================================================*/
create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   CREATE_DATE ASC
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
   ORDER_DATE           TIMESTAMP            not null,
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
   KIND                 VARCHAR2(20)         not null,
   MOBILE_CHECKOUT      CHAR(1)              default '0' not null,
   DELAYED_PAY_DATE     TIMESTAMP,
   FACILITATOR_PROVIDER_CODE VARCHAR2(100),
   IS_NEW               CHAR(1)              not null
)
partition by range
 (ORDER_DATE)
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
   ORDER_DATE DESC
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
/* Table: SHOP_PROFILES                                         */
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
   RECALL_DATE          TIMESTAMP            not null,
   ORDER_UUID           VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(20)         not null,
   TYPE                 VARCHAR2(10)         not null,
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3)
)
partition by range
 (RECALL_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SR
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

alter table SHOP_RECALLS
   add constraint PK_SHOP_RECALLS primary key (UUID)
go

/*==============================================================*/
/* Index: IDX_SH_RECALLS_ORDER_UUID                             */
/*==============================================================*/
create index IDX_SH_RECALLS_ORDER_UUID on SHOP_RECALLS (
   ORDER_UUID ASC
)
local
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


create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- если этот сиквенс - обновляемый
    then    -- ставим ему кэш 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;
go

