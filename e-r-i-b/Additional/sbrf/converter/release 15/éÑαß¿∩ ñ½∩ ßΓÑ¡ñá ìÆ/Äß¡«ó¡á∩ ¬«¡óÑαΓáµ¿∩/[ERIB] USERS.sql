alter table SRB_IKFL.USERS rename to FORDEL$USERS;

alter table SRB_IKFL.FORDEL$USERS rename constraint PK_USERS to FORDEL$USERS_C_1;

alter table SRB_IKFL.FORDEL$USERS rename constraint FK_USERS_REG_ADDRESS to FORDEL$USERS_C_23;
alter table SRB_IKFL.FORDEL$USERS rename constraint FK_USERS_RES_ADDRESS to FORDEL$USERS_C_24;
alter table SRB_IKFL.FORDEL$USERS rename constraint FK_USERS_TO_USERS to FORDEL$USERS_C_25;
alter table SRB_IKFL.FORDEL$USERS rename constraint FK_USERS_USERS_LOG_LOGINS to FORDEL$USERS_C_26;
alter table SRB_IKFL.FORDEL$USERS rename constraint FK_USERS_TO_DEPARTMENTS to FORDEL$USERS_C_27;

alter table SRB_IKFL.USER_TIMINGS drop constraint FK_USER_TIMINGS;
alter table SRB_IKFL.DOCUMENTS drop constraint FK_DOCUMENT_FK_DOCUME_USERS;
alter table SRB_IKFL.ERMB_DELAYED_COMMANDS drop constraint FK_DELAYED_TO_USERS;
alter table SRB_IKFL.ERMB_PROFILE drop constraint FK_ERMB_PROFILE_USERS;
alter table SRB_IKFL.PERSON_OLD_IDENTITY drop constraint FK_PERS_OLD_IDEN_USERS;

alter index SRB_IKFL.USERS_TRUSTING_ID rename to FORDEL$USERS_IDX_2;
alter index SRB_IKFL.COMLOG_INDEX_3 rename to FORDEL$USERS_IDX_3;
alter index SRB_IKFL.DXFK_USERS_REG_ADDRESS rename to FORDEL$USERS_IDX_4;
alter index SRB_IKFL.DXFK_USERS_RES_ADDRESS rename to FORDEL$USERS_IDX_5;
alter index SRB_IKFL.DXFK_USERS_TO_DEPARTMENTS rename to FORDEL$USERS_IDX_6;
alter index SRB_IKFL.DXFK_USERS_TO_USERS rename to FORDEL$USERS_IDX_7;
alter index SRB_IKFL.FIO_BD_PERSON_INDEX rename to FORDEL$USERS_IDX_8;
alter index SRB_IKFL.AGREEMENT_NUMBER_INDEX rename to FORDEL$USERS_IDX_9;
alter index SRB_IKFL.AGR_NUMB_SINGLE_INDEX rename to FORDEL$USERS_IDX_10;
alter index SRB_IKFL.USERS_TRIMMED_FIO rename to FORDEL$USERS_IDX_11;
alter index SRB_IKFL.USERS_STATE_STATUS rename to FORDEL$USERS_IDX_12;
alter index SRB_IKFL.USERS_BIRTHDAY_TRIMMED_FIO rename to FORDEL$USERS_IDX_13;
alter index SRB_IKFL.PK_USERS rename to FORDEL$USERS_IDX_14;
alter index SRB_IKFL.I_USERS_DEP_STATE_CR_TYPE rename to FORDEL$USERS_IDX_15;
alter index SRB_IKFL.IO_BD_PERSON_INDEX rename to FORDEL$USERS_IDX_16;
alter index SRB_IKFL.INDEX_CLIENT rename to FORDEL$USERS_IDX_17;
alter index SRB_IKFL.IDX_USR_LOGIN rename to FORDEL$USERS_IDX_18;

alter session force parallel ddl parallel 32;

create table USERS as
select /*+parallel(u, 32)*/
  ID,
  CLIENT_ID,
  LOGIN_ID,
  STATUS,
  FIRST_NAME,
  SUR_NAME,
  PATR_NAME,
  BIRTHDAY,
  BIRTH_PLACE,
  REGISTRATION_ADDRESS,
  RESIDENCE_ADDRESS,
  HOME_PHONE,
  JOB_PHONE,
  MOBILE_OPERATOR,
  AGREEMENT_NUMBER,
  AGREEMENT_DATE,
  INSERTION_OF_SERVICE,
  GENDER,
  TRUSTING_ID,
  CITIZEN_RF,
  PROLONGATION_REJECTION_DATE,
  STATE,
  DEPARTMENT_ID,
  CONTRACT_CANCELLATION_COUSE,
  SECRET_WORD,
  RESIDENTAL,
  DISPLAY_CLIENT_ID,
  CREATION_TYPE,
  LAST_UPDATE_DATE,
  REG_IN_DEPO,
  MDM_STATE,
  EMPLOYEE_ID,
  SEGMENT_CODE,
  case 
   when TARIF_PLAN_CODE = 'PREMIER' then '1'
   when TARIF_PLAN_CODE = 'GOLD'    then '2'
   when TARIF_PLAN_CODE = 'FIRST'   then '3'
   else null
  end as TARIF_PLAN_CODE,
  TARIF_PLAN_CONNECT_DATE,
  MANAGER_ID,
  SECURITY_TYPE,
  MANAGER_TB,
  MANAGER_OSB,
  MANAGER_VSP,
  CHECK_LOGIN_COUNT,
  LAST_FAILURE_LOGIN_CHECK,
  USER_REGISTRATION_MODE,
  STORE_SECURITY_TYPE
from FORDEL$USERS;

alter table SRB_IKFL.USERS add constrain PK_USERS primary key (ID) using index (
  create unique index SRB_IKFL.PK_USERS on SRB_IKFL.USERS (ID)  parallel 32 tablespace INDX 
) enable novalidate;

create index SRB_IKFL.USERS_TRUSTING_ID on SRB_IKFL.USERS (STATUS, TRUSTING_ID)  parallel 32 tablespace INDX ;
create index SRB_IKFL.COMLOG_INDEX_3 on SRB_IKFL.USERS (SUR_NAME, FIRST_NAME)  parallel 32 tablespace INDX; 
create index SRB_IKFL.DXFK_USERS_REG_ADDRESS on SRB_IKFL.USERS (REGISTRATION_ADDRESS)  parallel 32 tablespace INDX ;
create index SRB_IKFL.DXFK_USERS_RES_ADDRESS on SRB_IKFL.USERS (RESIDENCE_ADDRESS)  parallel 32 tablespace INDX; 
create index SRB_IKFL.DXFK_USERS_TO_DEPARTMENTS on SRB_IKFL.USERS (DEPARTMENT_ID)  parallel 32 tablespace INDX; 
create index SRB_IKFL.DXFK_USERS_TO_USERS on SRB_IKFL.USERS (TRUSTING_ID)  parallel 32 tablespace INDX; 
create index SRB_IKFL.FIO_BD_PERSON_INDEX on SRB_IKFL.USERS (UPPER(REPLACE(REPLACE(SUR_NAME||FIRST_NAME||PATR_NAME,' ',''),'-','')), BIRTHDAY)  parallel 32 tablespace INDX; 
create index SRB_IKFL.AGREEMENT_NUMBER_INDEX on SRB_IKFL.USERS (UPPER(SUR_NAME), UPPER(FIRST_NAME), NVL(UPPER(AGREEMENT_NUMBER),'x'))  parallel 32 tablespace INDX ;
create index SRB_IKFL.AGR_NUMB_SINGLE_INDEX on SRB_IKFL.USERS (AGREEMENT_NUMBER)  parallel 32 tablespace INDX ;
create index SRB_IKFL.USERS_TRIMMED_FIO on SRB_IKFL.USERS (UPPER(TRIM(SUR_NAME)||TRIM(FIRST_NAME)||TRIM(PATR_NAME)), BIRTHDAY)  parallel 32 tablespace INDX; 
create index SRB_IKFL.USERS_STATE_STATUS on SRB_IKFL.USERS (STATE, STATUS)  parallel 32 tablespace INDX; 
create index SRB_IKFL.USERS_BIRTHDAY_TRIMMED_FIO on SRB_IKFL.USERS (BIRTHDAY, UPPER(REPLACE(SUR_NAME,' ','')||REPLACE(FIRST_NAME,' ','')||REPLACE(PATR_NAME,' ','')))  parallel 32 tablespace INDX; 
create index SRB_IKFL.I_USERS_DEP_STATE_CR_TYPE on SRB_IKFL.USERS (STATUS, DEPARTMENT_ID, CREATION_TYPE)  parallel 32 tablespace INDX; 
create index SRB_IKFL.IO_BD_PERSON_INDEX on SRB_IKFL.USERS (UPPER(REPLACE(REPLACE(FIRST_NAME||PATR_NAME,' ',''),'-','')), BIRTHDAY)  parallel 32 tablespace INDX ;
create index SRB_IKFL.INDEX_CLIENT on SRB_IKFL.USERS (CLIENT_ID)  parallel 32 tablespace INDX ;
create unique index SRB_IKFL.IDX_USR_LOGIN on SRB_IKFL.USERS (LOGIN_ID)  parallel 32 tablespace INDX; 

alter table SRB_IKFL.DOCUMENTS 
  add constrain FK_DOCUMENT_FK_DOCUME_USERS foreign key (PERSON_ID)
    references SRB_IKFL.USERS (ID) on delete cascade enable novalidate;

alter table SRB_IKFL.ERMB_DELAYED_COMMANDS 
  add constrain FK_DELAYED_TO_USERS foreign key (USER_ID)
    references SRB_IKFL.USERS (ID) on delete cascade enable novalidate;

alter table SRB_IKFL.ERMB_PROFILE 
  add constrain FK_ERMB_PROFILE_USERS foreign key (PERSON_ID)
    references SRB_IKFL.USERS (ID) enable novalidate;

alter table SRB_IKFL.PERSON_OLD_IDENTITY 
  add constrain FK_PERS_OLD_IDEN_USERS foreign key (PERSON_ID)
    references SRB_IKFL.USERS (ID) enable novalidate;

alter table SRB_IKFL.USERS 
  add constrain FK_USERS_REG_ADDRESS foreign key (REGISTRATION_ADDRESS)
    references SRB_IKFL.ADDRESS (ID) enable novalidate;

alter table SRB_IKFL.USERS 
  add constrain FK_USERS_RES_ADDRESS foreign key (RESIDENCE_ADDRESS)
    references SRB_IKFL.ADDRESS (ID) enable novalidate;

alter table SRB_IKFL.USERS 
  add constraint FK_USERS_TO_USERS foreign key (TRUSTING_ID)
    references SRB_IKFL.USERS (ID) enable novalidate;

alter table SRB_IKFL.USERS 
  add constraint FK_USERS_USERS_LOG_LOGINS foreign key (LOGIN_ID)
    references SRB_IKFL.LOGINS (ID) enable novalidate;

alter table SRB_IKFL.USERS 
  add constraint FK_USERS_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
    references SRB_IKFL.DEPARTMENTS (ID) enable novalidate;

alter table SRB_IKFL.USER_TIMINGS 
  add constraint FK_USER_TIMINGS foreign key (USER_ID)
    references SRB_IKFL.USERS (ID) on delete cascade enable novalidate;

alter index SRB_IKFL.USERS_TRUSTING_ID noparallel;
alter index SRB_IKFL.COMLOG_INDEX_3 noparallel
alter index SRB_IKFL.DXFK_USERS_REG_ADDRESS noparallel;
alter index SRB_IKFL.DXFK_USERS_RES_ADDRESS noparallel;
alter index SRB_IKFL.DXFK_USERS_TO_DEPARTMENTS noparallel;
alter index SRB_IKFL.DXFK_USERS_TO_USERS  noparallel;
alter index SRB_IKFL.FIO_BD_PERSON_INDEX  noparallel;
alter index SRB_IKFL.AGREEMENT_NUMBER_INDEX  noparallel;
alter index SRB_IKFL.AGR_NUMB_SINGLE_INDEX  noparallel;
alter index SRB_IKFL.USERS_TRIMMED_FIO  noparallel;
alter index SRB_IKFL.USERS_STATE_STATUS  noparallel;
alter index SRB_IKFL.USERS_BIRTHDAY_TRIMMED_FIO  noparallel;
alter index SRB_IKFL.PK_USERS  noparallel;
alter index SRB_IKFL.I_USERS_DEP_STATE_CR_TYPE  noparallel;
alter index SRB_IKFL.IO_BD_PERSON_INDEX  noparallel;
alter index SRB_IKFL.INDEX_CLIENT  noparallel;
alter index SRB_IKFL.IDX_USR_LOGIN  noparallel;	