-- Номер ревизии: 54522
-- Номер версии: 1.18
-- Комментарий: доработка отправки уведомлений

drop table QUEUE_PUSH_MESSAGES_TAB
go
drop table QUEUE_PUSH_RECIPIENTS_TAB
go
drop sequence QPM_SEQ 
go
drop sequence QPR_SEQ
go


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

create table QPE_DEVICES_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   QPE_ID               number(20)           not null,
   DEVICE_ID            varchar2(1)          not null,
   PROC_STATUS          varchar2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           varchar2(128),
   constraint PK_QPE_DEVICES_TAB primary key (ID)
)

go

create sequence QED_SEQ
go

create table QUEUE_PUSH_DEVICES_TAB 
(
   ID                   number(20)           not null,
   TIMESTAMP            DATE                 not null,
   DEVICE_ID            varchar2(64)         not null,
   CLIENT_ID            varchar2(64)         not null,
   STATUS               varchar2(1)          not null,
   SECURITY_TOKEN       varchar2(4000),
   PROC_STATUS          varchar2(1)          not null,
   PROC_STATUS_AT       DATE                 default SYSDATE not null,
   PROC_ERROR           varchar2(128),
   constraint PK_QUEUE_PUSH_DEVICES_TAB primary key (ID)
)

go

create sequence QPD_SEQ
go


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
