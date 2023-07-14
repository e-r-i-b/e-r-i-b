/*==============================================================*/
/* Database name:  CSA                                          */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     03.10.2013 13:56:14                          */
/*==============================================================*/


DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PUSH_EVENTS_ADDRESSES'; 
    EXECUTE IMMEDIATE('drop index DXFK_PUSH_EVENTS_ADDRESSES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QPE_ADDRESSES_TAB'; 
    EXECUTE IMMEDIATE('alter table QPE_ADDRESSES_TAB drop constraint FK_QPE_ADDR_FK_PUSH_E_QUEUE_PU');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PUSH_EVENTS_DEVICES'; 
    EXECUTE IMMEDIATE('drop index DXFK_PUSH_EVENTS_DEVICES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QPE_DEVICES_TAB'; 
    EXECUTE IMMEDIATE('alter table QPE_DEVICES_TAB drop constraint FK_QPE_DEVI_FK_PUSH_E_QUEUE_PU');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QPE_ADDRESSES_TAB'; 
    EXECUTE IMMEDIATE('drop table QPE_ADDRESSES_TAB cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QPE_ADDRESSES_TAB';
    EXECUTE IMMEDIATE('drop sequence  S_QPE_ADDRESSES_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QPE_DEVICES_TAB'; 
    EXECUTE IMMEDIATE('drop table QPE_DEVICES_TAB cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QPE_DEVICES_TAB';
    EXECUTE IMMEDIATE('drop sequence  S_QPE_DEVICES_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QUEUE_PUSH_DEVICES_TAB'; 
    EXECUTE IMMEDIATE('drop table QUEUE_PUSH_DEVICES_TAB cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QUEUE_PUSH_DEVICES_TAB';
    EXECUTE IMMEDIATE('drop sequence  S_QUEUE_PUSH_DEVICES_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QUEUE_PUSH_DEVICE_REMOVALS_TAB'; 
    EXECUTE IMMEDIATE('drop table QUEUE_PUSH_DEVICE_REMOVALS_TAB cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QUEUE_PUSH_DEVICE_REMOVALS_TAB';
    EXECUTE IMMEDIATE('drop sequence  S_QUEUE_PUSH_DEVICE_REMOVALS_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QUEUE_PUSH_EVENTS_TAB'; 
    EXECUTE IMMEDIATE('drop table QUEUE_PUSH_EVENTS_TAB cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QUEUE_PUSH_EVENTS_TAB';
    EXECUTE IMMEDIATE('drop sequence  S_QUEUE_PUSH_EVENTS_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

/*==============================================================*/
/* Table: QPE_ADDRESSES_TAB                                     */
/*==============================================================*/
create table QPE_ADDRESSES_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   QPE_ID               number(20)           not null,
   ADDRESS              varchar2(32)         not null,
   PROC_STATUS          varchar2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           varchar2(128),
   constraint PK_QPE_ADDRESSES_TAB primary key (ID)
)

go

create sequence QEA_SEQ
go

/*==============================================================*/
/* Table: QPE_DEVICES_TAB                                       */
/*==============================================================*/
create table QPE_DEVICES_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   QPE_ID               number(20)           not null,
   DEVICE_ID            varchar2(64)         not null,
   MGUID                varchar2(64)         not null,
   PROC_STATUS          varchar2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           varchar2(128),
   constraint PK_QPE_DEVICES_TAB primary key (ID)
)

go

create sequence QED_SEQ
go

/*==============================================================*/
/* Table: QUEUE_PUSH_DEVICES_TAB                                */
/*==============================================================*/
create table QUEUE_PUSH_DEVICES_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   DEVICE_ID            varchar2(64)         not null,
   CLIENT_ID            varchar2(64)         not null,
   STATUS               varchar2(1)          not null,
   SECURITY_TOKEN       varchar2(4000),
   MGUID                varchar2(64)         not null,
   PROC_STATUS          varchar2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           varchar2(128),
   constraint PK_QUEUE_PUSH_DEVICES_TAB primary key (ID)
)

go

create sequence QPD_SEQ
go

/*==============================================================*/
/* Table: QUEUE_PUSH_DEVICE_REMOVALS_TAB                        */
/*==============================================================*/
create table QUEUE_PUSH_DEVICE_REMOVALS_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   DEVICE_ID            varchar2(64)         not null,
   PROC_STATUS          varchar2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           varchar2(128),
   constraint PK_QUEUE_PUSH_DEVICE_REMOVALS_ primary key (ID)
)

go

create sequence QDR_SEQ
go

/*==============================================================*/
/* Table: QUEUE_PUSH_EVENTS_TAB                                 */
/*==============================================================*/
create table QUEUE_PUSH_EVENTS_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   EVENT_ID             VARCHAR2(64)         not null,
   CLIENT_ID            VARCHAR2(64)         not null,
   SHORT_MESSAGE        VARCHAR2(255)        not null,
   SMS_MESSAGE          VARCHAR2(500),
   SYSTEM_CODE          VARCHAR2(32)         not null,
   TYPE_CODE            VARCHAR2(32)         not null,
   CONTENT              clob                 not null,
   PRIORITY             number(2)            not null,
   START_TIME           DATE,
   STOP_TIME            DATE,
   DLV_FROM             number(4),
   DLV_TO               number(4),
   PRIVATE_FL           VARCHAR2(1)          not null,
   PROC_STATUS          VARCHAR2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           VARCHAR2(128),
   constraint PK_QUEUE_PUSH_EVENTS_TAB primary key (ID)
)

go

create sequence QPE_SEQ
go


create index "DXFK_PUSH_EVENTS_ADDRESSES" on QPE_ADDRESSES_TAB
(
   QPE_ID
)
go

alter table QPE_ADDRESSES_TAB
   add constraint FK_QPE_ADDR_FK_PUSH_E_QUEUE_PU foreign key (QPE_ID)
      references QUEUE_PUSH_EVENTS_TAB (ID)
go


create index "DXFK_PUSH_EVENTS_DEVICES" on QPE_DEVICES_TAB
(
   QPE_ID
)
go

alter table QPE_DEVICES_TAB
   add constraint FK_QPE_DEVI_FK_PUSH_E_QUEUE_PU foreign key (QPE_ID)
      references QUEUE_PUSH_EVENTS_TAB (ID)
go

