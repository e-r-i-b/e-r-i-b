--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 52300
-- Комментарий:  ЦСА. История изменений идентификационных данных клиента
create table CSA_PROFILES_HISTORY 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   EXPIRE_DATE          TIMESTAMP            default SYSDATE not null,
   PROFILE_ID           INTEGER              not null,
   constraint PK_CSA_PROFILES_HISTORY primary key (ID) using index (
      create unique index PK_CSA_PROFILES_HISTORY on CSA_PROFILES_HISTORY(ID) tablespace CSAINDEXES 
   )   
)
/

create sequence S_CSA_PROFILES_HISTORY
/
create index "DXFK_CSA_PROF_REFERENCE_CSA_PR" on CSA_PROFILES_HISTORY ( PROFILE_ID ) tablespace CSAINDEXES 
/

alter table CSA_PROFILES_HISTORY
   add constraint FK_CSA_PROF_FK_CSA_PR_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
/

/*==============================================================*/
/* Index: CSA_PROFILES_HISTORY_UID                              */
/*==============================================================*/
create index CSA_PROFILES_HISTORY_UID on CSA_PROFILES_HISTORY (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC
) tablespace CSAINDEXES 
/


-- Номер ревизии: 52479
-- Комментарий:  ЦСА. Блокировки профиля (БД)
create table CSA_PROFILES_LOCK 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   REASON               VARCHAR2(1024)       not null,
   DATE_FROM            TIMESTAMP            not null,
   DATE_TO              TIMESTAMP,
   LOCKER_FIO           VARCHAR2(100)        not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   constraint PK_CSA_PROFILES_LOCK primary key (ID) using index (
      create unique index PK_CSA_PROFILES_LOCK on CSA_PROFILES_LOCK(ID) tablespace CSAINDEXES 
   )      
)
/

create sequence S_CSA_PROFILES_LOCK
/

create index "DXREFERENCE_5" on CSA_PROFILES_LOCK( PROFILE_ID ) tablespace CSAINDEXES 
/

alter table CSA_PROFILES_LOCK
   add constraint FK_CSA_PROF_REFERENCE_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
/

-- Номер ревизии: 52827
-- Комментарий:  ЦСА. вход клиента в многоблочной архитектуре (БД)
create table CSA_NODES 
(
	ID                   INTEGER              not null,
	NAME                 VARCHAR2(100)        not null,
	HOSTNAME             VARCHAR2(100)        not null,
	EXISTING_USERS_ALLOWED CHAR(1)              not null,
	NEW_USERS_ALLOWED    CHAR(1)              not null,
	TEMPORARY_USERS_ALLOWED CHAR(1)              not null,
	USERS_TRANSFER_ALLOWED CHAR(1)              not null,
	SMS_QUEUE_NAME       VARCHAR2(64)         not null,
	SMS_FACTORY_NAME     VARCHAR2(64)         not null,
	DICTIONARY_QUEUE_NAME VARCHAR2(64)         not null,
	DICTIONARY_FACTORY_NAME VARCHAR2(64)         not null,
	SERVICE_PROFILE_QUEUE_NAME VARCHAR2(64)         not null,
	SERVICE_PROFILE_FACTORY_NAME VARCHAR2(64)         not null,
	SERVICE_CLIENT_QUEUE_NAME VARCHAR2(64)         not null,
	SERVICE_CLIENT_FACTORY_NAME VARCHAR2(64)         not null,
	SERVICE_RESOURCE_QUEUE_NAME VARCHAR2(64)         not null,
	SERVICE_RESOURCE_FACTORY_NAME VARCHAR2(64)         not null,
	constraint PK_CSA_NODES primary key (ID) using index (
	  create unique index PK_CSA_NODES on CSA_NODES(ID) tablespace CSAINDEXES 
	)
)
/

create sequence S_CSA_NODES start with 10
/

create table CSA_PROFILE_NODES 
(
   ID                   INTEGER              not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   STATE                VARCHAR2(20)         not null,
   PROFILE_TYPE         VARCHAR2(20)         not null,
   PROFILE_ID           INTEGER              not null,
   NODE_ID              INTEGER              not null,
   constraint PK_CSA_PROFILE_NODES primary key (ID) using index (
      create unique index PK_CSA_PROFILE_NODES on CSA_PROFILE_NODES(ID) tablespace CSAINDEXES 
   )    
)
/

create sequence S_CSA_PROFILE_NODES
/

create index "DXFK_CSA_PROFILENODES_TO_NODE" on CSA_PROFILE_NODES ( NODE_ID ) tablespace CSAINDEXES 
/

alter table CSA_PROFILE_NODES
   add constraint FK_CSA_PROFILENODES_TO_NODE foreign key (NODE_ID)
      references CSA_NODES (ID)
/

create index "DXFK_CSA_PROFILENODES_TO_PROFI" on CSA_PROFILE_NODES ( PROFILE_ID ) tablespace CSAINDEXES 
/

alter table CSA_PROFILE_NODES
   add constraint FK_CSA_PROFILENODES_TO_PROFILE foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
/


-- Номер ревизии: 53465
-- Комментарий: ЦСА. прихранивание паролей (ч1. модель БД)
create table CSA_STORED_PASSWORDS 
(
   ID                   INTEGER              not null,
   LOGIN                VARCHAR2(10)         not null,
   HASH                 VARCHAR2(64)         not null,
   SALT                 VARCHAR2(32)         not null,
   CHANGED              TIMESTAMP            not null,
   constraint PK_CSA_STORED_PASSWORDS primary key (ID) using index (
      create unique index PK_CSA_STORED_PASSWORDS on CSA_STORED_PASSWORDS(ID) tablespace CSAINDEXES 
   )    
)
/

create sequence S_CSA_STORED_PASSWORDS
/

create unique index CSA_STORED_PASSWORD_U_LOGIN on CSA_STORED_PASSWORDS ( LOGIN ASC ) tablespace CSAINDEXES 
/

-- Номер ревизии: 54300
-- Комментарий: Перенос CONNECTOR_INFO в ЦСА
create table CONNECTORS_INFO 
(
   ID                   integer              not null,
   GUID                 VARCHAR2(35)         not null,
   LOGIN_TYPE           VARCHAR2(10)         not null,
   constraint PK_CONNECTORS_INFO primary key (ID) using index (
      create unique index PK_CONNECTORS_INFO on CONNECTORS_INFO(ID) tablespace CSAINDEXES 
   )
)
/

create sequence S_CONNECTORS_INFO
/

create unique index CONNECTORS_INFO_INDEX on CONNECTORS_INFO ( GUID ASC ) tablespace CSAINDEXES 
/

