--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

create table LIMITS_IKFL.PROFILES 
(
	ID                   INTEGER              not null,
	FIRST_NAME           VARCHAR2(100)        not null,
	SUR_NAME             VARCHAR2(100)        not null,
	PATR_NAME            VARCHAR2(100)        not null,
	PASSPORT             VARCHAR2(100)        not null,
	BIRTHDATE            TIMESTAMP            not null,
	TB                   VARCHAR2(4)          not null,
	constraint PK_PROFILES primary key (ID) using index (
		create unique index LIMITS_IKFL.PK_PROFILES on LIMITS_IKFL.PROFILES(ID) tablespace LIMITS_IDX 
	)
) tablespace LIMITS_DATA
/

create sequence LIMITS_IKFL.S_PROFILES
/

create unique index LIMITS_IKFL.INDEX_PROFILES_UID on LIMITS_IKFL.PROFILES (
	UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
	BIRTHDATE ASC,
	TB ASC,
	REPLACE(PASSPORT,' ','') ASC
) tablespace LIMITS_IDX
/

create table LIMITS_IKFL.PROFILE_INFORMATION 
(
	PROFILE_ID           INTEGER              not null,
	INFORMATION_TYPE     VARCHAR2(128)        not null,
	DATA                 CLOB,
	constraint PK_PROFILE_INFORMATION primary key (PROFILE_ID, INFORMATION_TYPE) using index (
		create unique index LIMITS_IKFL.PK_PROFILE_INFORMATION on LIMITS_IKFL.PROFILE_INFORMATION (PROFILE_ID, INFORMATION_TYPE) tablespace LIMITS_IDX 
	)
) tablespace LIMITS_DATA
/

create table LIMITS_IKFL.PROPERTIES 
(
	ID                   INTEGER              not null,
	PROPERTY_KEY         VARCHAR2(256)        not null,
	PROPERTY_VALUE       VARCHAR2(500),
	CATEGORY             VARCHAR2(80),
	constraint PK_PROPERTIES primary key (ID) using index (
		create unique index LIMITS_IKFL.PK_PROPERTIES on LIMITS_IKFL.PROPERTIES(ID) tablespace LIMITS_IDX 
	)
) tablespace LIMITS_DATA
/

create sequence LIMITS_IKFL.S_PROPERTIES
/

create table LIMITS_IKFL.TRANSACTIONS_JOURNAL 
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
partition by range (OPERATION_DATE) interval (NUMTODSINTERVAL(1,'DAY'))
(
	partition P_FIRST values less than (to_date('01-01-2014','DD-MM-YYYY')) tablespace LIMITS_DATA
) tablespace LIMITS_DATA
/

create sequence LIMITS_IKFL.S_TRANSACTIONS_JOURNAL
/

create index LIMITS_IKFL.DOC_DATE_I on LIMITS_IKFL.TRANSACTIONS_JOURNAL (
   DOCUMENT_EXTERNAL_ID ASC,
   OPERATION_DATE ASC
) local tablespace LIMITS_IDX
/

create index LIMITS_IKFL.PROFILE_DATE_I on LIMITS_IKFL.TRANSACTIONS_JOURNAL (
   PROFILE_ID ASC,
   OPERATION_DATE ASC
) local tablespace LIMITS_IDX
/

alter table LIMITS_IKFL.PROFILE_INFORMATION
   add constraint FK_PROFILE_INF_TO_PROFILES foreign key (PROFILE_ID)
      references LIMITS_IKFL.PROFILES (ID)
      on delete cascade
/

alter table LIMITS_IKFL.TRANSACTIONS_JOURNAL
   add constraint FK_TRANSACT_FK_PROFIL_PROFILES foreign key (PROFILE_ID)
      references LIMITS_IKFL.PROFILES (ID)
/

/*
grant select on LIMITS_IKFL.TRANSACTIONS_JOURNAL to OSDBO_USER;
grant select on LIMITS_IKFL.PROFILE_INFORMATION to OSDBO_USER;
grant select on LIMITS_IKFL.PROFILES to OSDBO_USER;
grant select on LIMITS_IKFL.PROPERTIES to OSDBO_USER;
*/