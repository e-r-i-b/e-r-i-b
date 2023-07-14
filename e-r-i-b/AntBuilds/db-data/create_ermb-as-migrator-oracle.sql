create user SBRF_118_MIGRATION profile "DEFAULT" identified by SBRF_118_MIGRATION account unlock
  default tablespace "USERS"
  temporary tablespace "TEMP"
  quota unlimited on "USERS"
;

grant connect, resource, create view, create sequence to SBRF_118_MIGRATION
;  

/* Список клиентов, для которых необходимо провести миграцию */
create table CLIENT (
   ID 					INTEGER 						not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   DOCUMENT             VARCHAR2(32)                    not null,
   BIRTH_DATE           TIMESTAMP                       not null,
   TER_BANK             VARCHAR2(4)                     not null,
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(7),
   UDBO  				CHAR(1)                         not null,
   VIP_OR_MBC			CHAR(1)                         not null,
   STATUS               VARCHAR2(12)                    not null,
   SEGMENT_1            CHAR(1)                         not null,
   SEGMENT_1_1          CHAR(1)                         not null,
   SEGMENT_1_2          CHAR(1)                         not null,
   SEGMENT_2_1          CHAR(1)                         not null,
   SEGMENT_2_2          CHAR(1)                         not null,
   SEGMENT_2_2_1        CHAR(1)                         not null,
   SEGMENT_3_1          CHAR(1)                         not null,
   SEGMENT_3_2_1        CHAR(1)                         not null,
   SEGMENT_3_2_2        CHAR(1)                         not null,
   SEGMENT_3_2_3        CHAR(1)                         not null,
   SEGMENT_4            CHAR(1)                         not null,
   SEGMENT_5_1          CHAR(1)                         not null,
   SEGMENT_5_2          CHAR(1)                         not null,
   SEGMENT_5_3          CHAR(1)                         not null,
   SEGMENT_5_4          CHAR(1)                         not null,
   SEGMENT_5_5          CHAR(1)                         not null,
   MIGRATION_BLOCK      INTEGER,
   MIGRATION_ERROR      CHAR(1)                         not null,
   constraint PK_CLIENT primary key (ID)
)
;

create sequence S_CLIENT;

/* Список телефонов*/
create table PHONES (
   ID 					      INTEGER 				   not null,
   CLIENT_ID			      INTEGER 				   not null,
   PHONE                      VARCHAR2(20)             not null,
   SOURCE                     CHAR(3)                  not null,
   SMS_COUNT			      INTEGER                  not null,
   REGISTRATION_DATE	      TIMESTAMP,
   VIP_OR_MVS                 CHAR(1)                  not null,
   HAS_ADDITIONAL             CHAR(1)                  not null,
   ADDITIONAL_CARD_OWNER      CHAR(1)                  not null,
   HAS_MAIN                   CHAR(1)                  not null,
   IS_SULTAN                  CHAR(1)                  not null,
   BELONG_CLIENT_REGISTRATION CHAR(1)                  not null,
   CARD_ACTIVITY              CHAR(1)                  not null,
   LAST_SMS_ACTIVITY          CHAR(1)                  not null,
   constraint PK_PHONES primary key (ID)
)
;

create sequence S_PHONES;

alter table PHONES
   add constraint FK_PHONES foreign key (CLIENT_ID)
      references CLIENT (ID)
;

-- конфликты по телефонам
create table CONFLICTS (
   PHONE                VARCHAR2(20)                    not null,
   STATUS               VARCHAR2(20)                    not null,
   OWNER_ID             INTEGER,
   MANUALLY 			CHAR(1)                         not null,
   EMPLOYEE_INFO        VARCHAR2(120),
   constraint PK_CONFLICTS primary key (PHONE)
)
;

-- клиент-конфликт
create table PHONES_TO_CONFLICTED_CLIENTS (
   PHONE                VARCHAR2(20)                    not null,
   CONFLICTED_CLIENT_ID INTEGER                         not null,
   constraint PK_PHONES_TO_CONFLICTED_CLIENT primary key (PHONE,CONFLICTED_CLIENT_ID)
)
;

-- Список конфликтующих клиентов
create table CONFLICTED_CLIENTS (
   ID 					INTEGER 						not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   DOCUMENT             VARCHAR2(32)                    not null,
   BIRTH_DATE           TIMESTAMP                       not null,
   TER_BANK             VARCHAR2(4),
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(7),
   VIP_OR_MBC			CHAR(1)                         not null,
   CARD_ACTIVITY        CHAR(1)                         not null,
   constraint PK_CONFLICTED_CLIENTS primary key (ID)
)
;

alter table CONFLICTS
   add constraint FK_TO_OWNER foreign key (OWNER_ID)
      references CONFLICTED_CLIENTS (ID)
;

alter table PHONES_TO_CONFLICTED_CLIENTS
   add constraint FK_LINK_TO_CONFLICTED_CLIENTS foreign key (CONFLICTED_CLIENT_ID)
      references CONFLICTED_CLIENTS (ID)
;

alter table PHONES_TO_CONFLICTED_CLIENTS
   add constraint FK_LINK_TO_CONFLICTS foreign key (PHONE)
      references CONFLICTS (PHONE)
;

create sequence S_CONFLICTED_CLIENTS;

create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- если этот сиквенс - обновляемый
    then    -- ставим ему кэш 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;

CREATE TABLE MBK (
	PHONE_NUMBER            VARCHAR2(20)     NOT NULL,
	CARD_NUMBER             VARCHAR2(19)     NOT NULL,
	INFO_CARD_NUMBER        VARCHAR2(19)     NOT NULL,
	LAST_EVENT_CARD_DATE    VARCHAR2(10),
	LAST_REGISTRATION_DATE  VARCHAR2(10),
	SMS_COUNT               INTEGER,
	LAST_INCOMING_SMS_DATE  VARCHAR2(10)
)
;

CREATE TABLE MBV (
  SUR_NAME           VARCHAR2(120)         NOT NULL,
  FIRST_NAME         VARCHAR2(120)         NOT NULL,
  PATR_NAME          VARCHAR2(120),
  DOC_SERIES         VARCHAR2(16),
  DOC_NUMBER         VARCHAR2(16),
  DOC_TYPE           VARCHAR2(32)          NOT NULL,
  BIRTH_DATE         VARCHAR2(10)          NOT NULL,
  STATE              INTEGER,
  UDBO_STATE         INTEGER,
  REGISTRATION_DATE  VARCHAR2(10)          NOT NULL,
  PHONE_NUMBER       VARCHAR2(20)          NOT NULL,
  OSB                VARCHAR2(4)           NOT NULL,
  VSP                VARCHAR2(4)           NOT NULL,
  LAST_SMS_DATE      VARCHAR2(10),
  SMS_COUNT          INTEGER
)
;

CREATE TABLE WAY4 (
  DEPARTMENT        VARCHAR2(10)           NOT NULL,
  SUR_NAME          VARCHAR2(120)          NOT NULL,
  FIRST_NAME        VARCHAR2(120)          NOT NULL,
  PATR_NAME         VARCHAR2(120),
  DOCUMENT          VARCHAR2(24),
  BIRTH_DATE        VARCHAR2(10)           NOT NULL,
  CARD_NUMBER       VARCHAR2(19)           NOT NULL,
  CARD_NUMBER_BASE  VARCHAR2(19),
  DEPARTMENT_BASE   VARCHAR2(10),
  SUR_NAME_BASE     VARCHAR2(120),
  FIRST_NAME_BASE   VARCHAR2(120),
  PATR_NAME_BASE    VARCHAR2(120),
  DOCUMENT_BASE     VARCHAR2(24),
  BIRTH_DATE_BASE   VARCHAR2(10)
)
;

CREATE TABLE MIGRATION_INFO (
   CLIENT_ID 			INTEGER 						not null,
   MIGRATION_DATE       TIMESTAMP,
   MBK_MIGRATION_ID     INTEGER,
   MBV_MIGRATION_ID     VARCHAR2(32),
   ERROR			    CHAR(1)                         not null,
   constraint PK_MIGRATION_INFO primary key (CLIENT_ID),
   constraint FK_MIGRATION_INFO foreign key (CLIENT_ID) references CLIENT (ID)
)
;

CREATE TABLE COD (
    SUR_NAME          VARCHAR2(120)          NOT NULL,
    FIRST_NAME        VARCHAR2(120)          NOT NULL,
    PATR_NAME         VARCHAR2(120),
    BIRTH_DATE        VARCHAR2(10)           NOT NULL,
    DOC_SERIES        VARCHAR2(16),
    DOC_NUMBER        VARCHAR2(16),
    EDBO_NO           VARCHAR2(16),
    EDBO_STATE        VARCHAR2(5),
    CLIENTSTATE       VARCHAR2(9)            NOT NULL,
    BRANCHNO_ZONA     VARCHAR2(4),
    OFFICE_ZONE       VARCHAR2(4),
	TER_BANK          VARCHAR2(4)            NOT NULL
)
;