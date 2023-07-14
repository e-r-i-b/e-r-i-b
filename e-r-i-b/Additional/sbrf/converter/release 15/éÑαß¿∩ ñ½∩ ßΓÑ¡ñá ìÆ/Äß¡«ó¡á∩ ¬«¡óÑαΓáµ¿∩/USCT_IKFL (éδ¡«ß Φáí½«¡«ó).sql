/*==============================================================*/
/* �������                                                      */
/*==============================================================*/
create table USCT_IKFL.PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID) using index (
      create unique index USCT_IKFL.PK_PROPERTIES on USCT_IKFL.PROPERTIES(ID) tablespace USCT_IDX
   )
) tablespace USCT_DATA
/
create sequence USCT_IKFL.S_PROPERTIES
/

create unique index USCT_IKFL.PROPERTIES_UNIQ on USCT_IKFL.PROPERTIES (
   CATEGORY ASC,
   PROPERTY_KEY ASC
) tablespace USCT_IDX
/

create table USCT_IKFL.PAYMENTS_DOCUMENTS 
(
   ID                   INTEGER              not null,
   KIND                 CHAR(1)              not null,
   CHANGED              TIMESTAMP            not null,
   EXTERNAL_ID          VARCHAR(80),
   DOCUMENT_NUMBER      INTEGER,
   CLIENT_CREATION_DATE TIMESTAMP            not null,
   CLIENT_CREATION_CHANNEL CHAR(1)              not null,
   CLIENT_OPERATION_DATE TIMESTAMP,
   CLIENT_OPERATION_CHANNEL CHAR(1),
   EMPLOYEE_OPERATION_DATE TIMESTAMP,
   EMPLOYEE_OPERATION_CHANNEL CHAR(1),
   USER_PROFILE_ID      INTEGER              not null,
   CREATED_EMPLOYEE_GUID VARCHAR(24),
   CREATED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   CONFIRMED_EMPLOYEE_GUID VARCHAR(24),
   CONFIRMED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   OPERATION_UID        VARCHAR2(32),
   STATE_CODE           VARCHAR2(25)         not null,
   STATE_DESCRIPTION    VARCHAR2(265),
   CHARGEOFF_RESOURCE   VARCHAR(30),
   DESTINATION_RESOURCE VARCHAR(30),
   GROUND               VARCHAR2(1024),
   CHARGEOFF_AMOUNT     NUMBER(19,4),
   CHARGEOFF_CURRENCY   CHAR(3),
   DESTINATION_AMOUNT   NUMBER(19,4),
   DESTINATION_CURRENCY CHAR(3),
   SUMM_TYPE            VARCHAR2(50),
   RECEIVER_NAME        VARCHAR2(256),
   RECEIVER_POINT_CODE  VARCHAR2(128),
   MULTI_BLOCK_RECEIVER_CODE VARCHAR2(32),
   EXTENDED_FIELDS      CLOB,
   REGION               VARCHAR2(4),
   BRANCH               VARCHAR2(4),
   OFFICE               VARCHAR2(6),
   TEMPLATE_NAME        VARCHAR2(128)        not null,
   TEMPLATE_USE_IN_MAPI CHAR(1)              not null,
   TEMPLATE_USE_IN_ATM  CHAR(1)              not null,
   TEMPLATE_USE_IN_ERMB CHAR(1)              not null,
   TEMPLATE_USE_IN_ERIB CHAR(1)              not null,
   TEMPLATE_IS_VISIBLE  CHAR(1)              not null,
   TEMPLATE_ORDER_IND   INTEGER              not null,
   TEMPLATE_STATE_CODE  VARCHAR2(50)         not null,
   TEMPLATE_STATE_DESCRIPTION VARCHAR2(128),
   REMINDER_TYPE        VARCHAR2(16),
   REMINDER_ONCE_DATE   TIMESTAMP,
   REMINDER_DAY_OF_MONTH INTEGER,
   REMINDER_MONTH_OF_QUARTER INTEGER,
   REMINDER_CREATED_DATE TIMESTAMP
) tablespace USCT_DATA 
partition by hash(USER_PROFILE_ID) partitions 128
/

create table USCT_IKFL.PAYMENTS_DOCUMENTS_EXT 
(
   ID                   INTEGER              not null,
   KIND                 VARCHAR(50)          not null,
   NAME                 VARCHAR2(64)         not null,
   VALUE                VARCHAR2(4000),
   PAYMENT_ID           INTEGER              not null,
   CHANGED              CHAR(1)              default '0' not null
) tablespace USCT_DATA 
partition by hash(PAYMENT_ID) partitions 128
/

create table USCT_IKFL.USER_PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null
)tablespace USCT_DATA
/

create table USCT_IKFL.IMP$USER_PROFILES_EXT (
  CLIENT_ID number,
  USER_PROFILES_ID number,
  NODE_ID number
) tablespace USCT_DATA 
/

create sequence USCT_IKFL.S_USER_PROFILES start with 50000000 cache 1000
/
create sequence USCT_IKFL.S_PAYMENTS_DOCUMENTS start with 50000000 cache 10000
/
create sequence USCT_IKFL.S_PAYMENTS_DOCUMENTS_EXT start with 1 cache 20000
/
/*==============================================================*/
/* ���������� ��� ���+                                          */
/* �� ���� �������� ����� ������� dblink-� � �����              */
/*==============================================================*/
create table USCT_IKFL.IMP$PROVIDERS_N1 as 
select 
  ID   INTERNAL_RECEIVER_ID, 
  UUID MULTI_BLOCK_RECEIVER_CODE,
  1 NODE_ID
from SERVICE_PROVIDERS@NODE_1
/
 
create table USCT_IKFL.IMP$PROVIDERS_N2 as 
select 
  ID   INTERNAL_RECEIVER_ID, 
  UUID MULTI_BLOCK_RECEIVER_CODE,
  2 NODE_ID
from SERVICE_PROVIDERS@NODE_2
/
 
create table USCT_IKFL.IMP$USER_PROFILES_N1 as 
select 
  u.ID NODE_CLIENT_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_SERIES PASSPORT, BIRTHDAY, dep.TB, 1 NODE_ID
from USERS@NODE_1 u
left join DOCUMENTS@NODE_1 d on d.PERSON_ID=u.ID and d.DOC_TYPE='PASSPORT_WAY'
inner join DEPARTMENTS@NODE_1 dep on dep.ID=u.DEPARTMENT_ID
where SUR_NAME is not null and DOC_SERIES is not null and BIRTHDAY is not null and ( u.STATUS!='D' and u.STATE!='D')
/

create table USCT_IKFL.IMP$USER_PROFILES_N2 as 
select 
  u.ID NODE_CLIENT_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_SERIES PASSPORT, BIRTHDAY, dep.TB, 2 NODE_ID
from USERS@NODE_2 u
left join DOCUMENTS@NODE_2 d on d.PERSON_ID=u.ID and d.DOC_TYPE='PASSPORT_WAY'
inner join DEPARTMENTS@NODE_2 dep on dep.ID=u.DEPARTMENT_ID
where SUR_NAME is not null and DOC_SERIES is not null and BIRTHDAY is not null and ( u.STATUS!='D' and u.STATE!='D')
/

create table USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_N1 as 
select 
  ID,
  KIND,
  CHANGED,
  EXTERNAL_ID,
  DOCUMENT_NUMBER,
  CLIENT_CREATION_DATE,
  CLIENT_CREATION_CHANNEL,
  CLIENT_OPERATION_DATE,
  CLIENT_OPERATION_CHANNEL,
  EMPLOYEE_OPERATION_DATE,
  EMPLOYEE_OPERATION_CHANNEL,
  CLIENT_GUID,
  CREATED_EMPLOYEE_GUID,
  CREATED_EMPLOYEE_FULL_NAME,
  CONFIRMED_EMPLOYEE_GUID,
  CONFIRMED_EMPLOYEE_FULL_NAME,
  OPERATION_UID,
  STATE_CODE,
  STATE_DESCRIPTION,
  CHARGEOFF_RESOURCE,
  DESTINATION_RESOURCE,
  GROUND,
  CHARGEOFF_AMOUNT,
  CHARGEOFF_CURRENCY,
  DESTINATION_AMOUNT,
  DESTINATION_CURRENCY,
  SUMM_TYPE,
  RECEIVER_NAME,
  INTERNAL_RECEIVER_ID,
  MULTI_BLOCK_RECEIVER_CODE,
  RECEIVER_POINT_CODE,
  EXTENDED_FIELDS,
  REGION_ID,
  AGENCY_ID,
  BRANCH_ID,
  TEMPLATE_NAME,
  TEMPLATE_USE_IN_MAPI,
  TEMPLATE_USE_IN_ATM,
  TEMPLATE_USE_IN_ERMB,
  TEMPLATE_USE_IN_ERIB,
  TEMPLATE_IS_VISIBLE,
  TEMPLATE_ORDER_IND,
  TEMPLATE_STATE_CODE,
  TEMPLATE_STATE_DESCRIPTION,
  REMINDER_TYPE,
  REMINDER_ONCE_DATE,
  REMINDER_DAY_OF_MONTH,
  REMINDER_MONTH_OF_QUARTER,
  REMINDER_CREATED_DATE
from PAYMENTS_DOCUMENTS@NODE_1
/

create table USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_N2 as 
select 
  ID,
  KIND,
  CHANGED,
  EXTERNAL_ID,
  DOCUMENT_NUMBER,
  CLIENT_CREATION_DATE,
  CLIENT_CREATION_CHANNEL,
  CLIENT_OPERATION_DATE,
  CLIENT_OPERATION_CHANNEL,
  EMPLOYEE_OPERATION_DATE,
  EMPLOYEE_OPERATION_CHANNEL,
  CLIENT_GUID,
  CREATED_EMPLOYEE_GUID,
  CREATED_EMPLOYEE_FULL_NAME,
  CONFIRMED_EMPLOYEE_GUID,
  CONFIRMED_EMPLOYEE_FULL_NAME,
  OPERATION_UID,
  STATE_CODE,
  STATE_DESCRIPTION,
  CHARGEOFF_RESOURCE,
  DESTINATION_RESOURCE,
  GROUND,
  CHARGEOFF_AMOUNT,
  CHARGEOFF_CURRENCY,
  DESTINATION_AMOUNT,
  DESTINATION_CURRENCY,
  SUMM_TYPE,
  RECEIVER_NAME,
  INTERNAL_RECEIVER_ID,
  MULTI_BLOCK_RECEIVER_CODE,
  RECEIVER_POINT_CODE,
  EXTENDED_FIELDS,
  REGION_ID,
  AGENCY_ID,
  BRANCH_ID,
  TEMPLATE_NAME,
  TEMPLATE_USE_IN_MAPI,
  TEMPLATE_USE_IN_ATM,
  TEMPLATE_USE_IN_ERMB,
  TEMPLATE_USE_IN_ERIB,
  TEMPLATE_IS_VISIBLE,
  TEMPLATE_ORDER_IND,
  TEMPLATE_STATE_CODE,
  TEMPLATE_STATE_DESCRIPTION,
  REMINDER_TYPE,
  REMINDER_ONCE_DATE,
  REMINDER_DAY_OF_MONTH,
  REMINDER_MONTH_OF_QUARTER,
  REMINDER_CREATED_DATE
from PAYMENTS_DOCUMENTS@NODE_2
/

create table USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_EXT_N1 as 
select 
  ID,    KIND,    NAME,    VALUE,    PAYMENT_ID,    CHANGED 
from PAYMENTS_DOCUMENTS_EXT@NODE_1
/

create table USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_EXT_N2 as 
select 
  ID,    KIND,    NAME,    VALUE,    PAYMENT_ID,    CHANGED 
from PAYMENTS_DOCUMENTS_EXT@NODE_2
/

/*==============================================================*/
/* ����������                                                   */
/*==============================================================*/
insert /*+append_values parallel*/
  when RN=FRN then
    into USCT_IKFL.USER_PROFILES  ( ID, FIRST_NAME, SUR_NAME, PATR_NAME, PASSPORT, BIRTHDAY, TB ) values ( FRN, FIRST_NAME, SUR_NAME, PATR_NAME, PASSPORT, BIRTHDAY, TB )
    into USCT_IKFL.IMP$USER_PROFILES_EXT ( CLIENT_ID, USER_PROFILES_ID, NODE_ID ) values ( FRN, NODE_CLIENT_ID, NODE_ID  )
  else 
    into USCT_IKFL.IMP$USER_PROFILES_EXT ( CLIENT_ID, USER_PROFILES_ID, NODE_ID ) values ( FRN, NODE_CLIENT_ID, NODE_ID  )
select /*+parallel(t, 32)*/
  rownum rn, 
  min(rownum) over ( partition by UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))), BIRTHDAY, TB, REPLACE(PASSPORT, ' ', '') ) frn,
  t.*
from (
    select * from USCT_IKFL.IMP$USER_PROFILES_N1 union all
    select * from USCT_IKFL.IMP$USER_PROFILES_N2
) t
/
create unique index USCT_IKFL.IMP$USER_PROFILES_EXT_IDX on USCT_IKFL.IMP$USER_PROFILES_EXT(
  USER_PROFILES_ID, 
  NODE_ID, 
  CLIENT_ID
) parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.IMP$USER_PROFILES_EXT_IDX noparallel logging
/

insert /*+append parallel(pd, 16)*/ into USCT_IKFL.PAYMENTS_DOCUMENTS pd (ID, KIND, CHANGED, EXTERNAL_ID, DOCUMENT_NUMBER, CLIENT_CREATION_DATE, CLIENT_CREATION_CHANNEL, CLIENT_OPERATION_DATE, CLIENT_OPERATION_CHANNEL, EMPLOYEE_OPERATION_DATE, EMPLOYEE_OPERATION_CHANNEL, USER_PROFILE_ID, CREATED_EMPLOYEE_GUID, CREATED_EMPLOYEE_FULL_NAME, CONFIRMED_EMPLOYEE_GUID, CONFIRMED_EMPLOYEE_FULL_NAME, OPERATION_UID, STATE_CODE, STATE_DESCRIPTION, CHARGEOFF_RESOURCE, DESTINATION_RESOURCE, GROUND, CHARGEOFF_AMOUNT, CHARGEOFF_CURRENCY, DESTINATION_AMOUNT, DESTINATION_CURRENCY, SUMM_TYPE, RECEIVER_NAME, MULTI_BLOCK_RECEIVER_CODE, RECEIVER_POINT_CODE, EXTENDED_FIELDS, REGION, BRANCH, OFFICE, TEMPLATE_NAME, TEMPLATE_USE_IN_MAPI, TEMPLATE_USE_IN_ATM, TEMPLATE_USE_IN_ERMB, TEMPLATE_USE_IN_ERIB, TEMPLATE_IS_VISIBLE, TEMPLATE_ORDER_IND, TEMPLATE_STATE_CODE, TEMPLATE_STATE_DESCRIPTION, REMINDER_TYPE, REMINDER_ONCE_DATE, REMINDER_DAY_OF_MONTH, REMINDER_MONTH_OF_QUARTER, REMINDER_CREATED_DATE )
select /*+parallel(pd, 16) parallel(us, 16) parallel(pn, 16)*/
  ID,
  KIND,
  CHANGED,
  EXTERNAL_ID,
  DOCUMENT_NUMBER,
  CLIENT_CREATION_DATE,
  CLIENT_CREATION_CHANNEL,
  CLIENT_OPERATION_DATE,
  CLIENT_OPERATION_CHANNEL,
  EMPLOYEE_OPERATION_DATE,
  EMPLOYEE_OPERATION_CHANNEL,
  CLIENT_ID USER_PROFILE_ID,
  CREATED_EMPLOYEE_GUID,
  CREATED_EMPLOYEE_FULL_NAME,
  CONFIRMED_EMPLOYEE_GUID,
  CONFIRMED_EMPLOYEE_FULL_NAME,
  OPERATION_UID,
  STATE_CODE,
  STATE_DESCRIPTION,
  CHARGEOFF_RESOURCE,
  DESTINATION_RESOURCE,
  GROUND,
  CHARGEOFF_AMOUNT,
  CHARGEOFF_CURRENCY,
  DESTINATION_AMOUNT,
  DESTINATION_CURRENCY,
  SUMM_TYPE,
  RECEIVER_NAME,
  coalesce(pn.MULTI_BLOCK_RECEIVER_CODE, pd.MULTI_BLOCK_RECEIVER_CODE),
  RECEIVER_POINT_CODE,
  EXTENDED_FIELDS,
  REGION_ID REGION,
  AGENCY_ID BRANCH,
  BRANCH_ID OFFICE,
  TEMPLATE_NAME,
  TEMPLATE_USE_IN_MAPI,
  TEMPLATE_USE_IN_ATM,
  TEMPLATE_USE_IN_ERMB,
  TEMPLATE_USE_IN_ERIB,
  TEMPLATE_IS_VISIBLE,
  TEMPLATE_ORDER_IND,
  TEMPLATE_STATE_CODE,
  TEMPLATE_STATE_DESCRIPTION,
  null REMINDER_TYPE,
  null REMINDER_ONCE_DATE,
  null REMINDER_DAY_OF_MONTH,
  null REMINDER_MONTH_OF_QUARTER,
  null REMINDER_CREATED_DATE
from USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_N1 pd
inner join USCT_IKFL.IMP$USER_PROFILES_EXT us on us.USER_PROFILES_ID=pd.CLIENT_GUID and us.NODE_ID=1
left join USCT_IKFL.IMP$PROVIDERS_N1 pn on pn.INTERNAL_RECEIVER_ID=pd.INTERNAL_RECEIVER_ID and pn.NODE_ID=1
/

insert /*+append parallel(pd, 16)*/ into USCT_IKFL.PAYMENTS_DOCUMENTS pd (ID, KIND, CHANGED, EXTERNAL_ID, DOCUMENT_NUMBER, CLIENT_CREATION_DATE, CLIENT_CREATION_CHANNEL, CLIENT_OPERATION_DATE, CLIENT_OPERATION_CHANNEL, EMPLOYEE_OPERATION_DATE, EMPLOYEE_OPERATION_CHANNEL, USER_PROFILE_ID, CREATED_EMPLOYEE_GUID, CREATED_EMPLOYEE_FULL_NAME, CONFIRMED_EMPLOYEE_GUID, CONFIRMED_EMPLOYEE_FULL_NAME, OPERATION_UID, STATE_CODE, STATE_DESCRIPTION, CHARGEOFF_RESOURCE, DESTINATION_RESOURCE, GROUND, CHARGEOFF_AMOUNT, CHARGEOFF_CURRENCY, DESTINATION_AMOUNT, DESTINATION_CURRENCY, SUMM_TYPE, RECEIVER_NAME, MULTI_BLOCK_RECEIVER_CODE, RECEIVER_POINT_CODE, EXTENDED_FIELDS, REGION, BRANCH, OFFICE, TEMPLATE_NAME, TEMPLATE_USE_IN_MAPI, TEMPLATE_USE_IN_ATM, TEMPLATE_USE_IN_ERMB, TEMPLATE_USE_IN_ERIB, TEMPLATE_IS_VISIBLE, TEMPLATE_ORDER_IND, TEMPLATE_STATE_CODE, TEMPLATE_STATE_DESCRIPTION, REMINDER_TYPE, REMINDER_ONCE_DATE, REMINDER_DAY_OF_MONTH, REMINDER_MONTH_OF_QUARTER, REMINDER_CREATED_DATE )
select /*+parallel(pd, 16) parallel(us, 16) parallel(pn, 16)*/
  ID+100000,
  KIND,
  CHANGED,
  EXTERNAL_ID,
  DOCUMENT_NUMBER,
  CLIENT_CREATION_DATE,
  CLIENT_CREATION_CHANNEL,
  CLIENT_OPERATION_DATE,
  CLIENT_OPERATION_CHANNEL,
  EMPLOYEE_OPERATION_DATE,
  EMPLOYEE_OPERATION_CHANNEL,
  CLIENT_ID USER_PROFILE_ID,
  CREATED_EMPLOYEE_GUID,
  CREATED_EMPLOYEE_FULL_NAME,
  CONFIRMED_EMPLOYEE_GUID,
  CONFIRMED_EMPLOYEE_FULL_NAME,
  OPERATION_UID,
  STATE_CODE,
  STATE_DESCRIPTION,
  CHARGEOFF_RESOURCE,
  DESTINATION_RESOURCE,
  GROUND,
  CHARGEOFF_AMOUNT,
  CHARGEOFF_CURRENCY,
  DESTINATION_AMOUNT,
  DESTINATION_CURRENCY,
  SUMM_TYPE,
  RECEIVER_NAME,
  coalesce(pn.MULTI_BLOCK_RECEIVER_CODE, pd.MULTI_BLOCK_RECEIVER_CODE),
  RECEIVER_POINT_CODE,
  EXTENDED_FIELDS,
  REGION_ID REGION,
  AGENCY_ID BRANCH,
  BRANCH_ID OFFICE,
  TEMPLATE_NAME,
  TEMPLATE_USE_IN_MAPI,
  TEMPLATE_USE_IN_ATM,
  TEMPLATE_USE_IN_ERMB,
  TEMPLATE_USE_IN_ERIB,
  TEMPLATE_IS_VISIBLE,
  TEMPLATE_ORDER_IND,
  TEMPLATE_STATE_CODE,
  TEMPLATE_STATE_DESCRIPTION,
  null REMINDER_TYPE,
  null REMINDER_ONCE_DATE,
  null REMINDER_DAY_OF_MONTH,
  null REMINDER_MONTH_OF_QUARTER,
  null REMINDER_CREATED_DATE
from USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_N2 pd
inner join USCT_IKFL.IMP$USER_PROFILES_EXT us on us.USER_PROFILES_ID=pd.CLIENT_GUID and us.NODE_ID=2
left join USCT_IKFL.IMP$PROVIDERS_N2 pn on pn.INTERNAL_RECEIVER_ID=pd.INTERNAL_RECEIVER_ID and pn.NODE_ID=2
/
commit
/

insert /*+append parallel(pde, 16)*/ into USCT_IKFL.PAYMENTS_DOCUMENTS_EXT pde (
  ID,    KIND,    NAME,    VALUE,    PAYMENT_ID,    CHANGED 
)
select /*+parallel(pden, 32)*/
  USCT_IKFL.S_PAYMENTS_DOCUMENTS_EXT.nextval ID,    KIND,    NAME,    VALUE,    PAYMENT_ID,    CHANGED 
from USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_EXT_N1 pden
/
commit
/

insert /*+append parallel(pde, 16)*/ into USCT_IKFL.PAYMENTS_DOCUMENTS_EXT pde (
  ID,    KIND,    NAME,    VALUE,    PAYMENT_ID,    CHANGED 
)
select /*+parallel(pden, 32)*/
  USCT_IKFL.S_PAYMENTS_DOCUMENTS_EXT.nextval ID,    KIND,    NAME,    VALUE,    PAYMENT_ID+100000,    CHANGED 
from USCT_IKFL.IMP$PAYMENTS_DOCUMENTS_EXT_N2 pden
/
commit
/

/*==============================================================*/
/* ���������� � ������                                          */
/*==============================================================*/
-- USER_PROFILES -------------------------------------------------------------------------------------------------------
create unique index USCT_IKFL.PK_USER_PROFILES on USCT_IKFL.USER_PROFILES(
  ID
) parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.PK_USER_PROFILES noparallel logging
/

create unique index USCT_IKFL.IDX_USER_PROFILE_UNIQ on USCT_IKFL.USER_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDAY ASC,
   TB ASC,
   REPLACE(PASSPORT, ' ', '') ASC
) parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.PK_USER_PROFILES noparallel logging
/

alter table USCT_IKFL.USER_PROFILES 
  add constraint PK_USER_PROFILES primary key (ID) 
    using index USCT_IKFL.PK_USER_PROFILES enable novalidate
/
-- PAYMENTS_DOCUMENTS --------------------------------------------------------------------------------------------------
create unique index USCT_IKFL.PK_PAYMENTS_DOCUMENTS on USCT_IKFL.PAYMENTS_DOCUMENTS(
  ID
) global partition by hash(ID) partitions 64 parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.PK_PAYMENTS_DOCUMENTS noparallel logging
/

--���������, ��� ��� ������ ��������:
--���� ������ ������ ������ - ����� �������� ��� ������� 
select * from (
  select p.*, count(1) over (partition by USER_PROFILE_ID, TEMPLATE_NAME) cnt from USCT_IKFL.PAYMENTS_DOCUMENTS p
) where cnt > 1
order by USER_PROFILE_ID
/

create unique index USCT_IKFL.IND_U_TEMPLATE_NAME on USCT_IKFL.PAYMENTS_DOCUMENTS (
   USER_PROFILE_ID ASC,
   TEMPLATE_NAME ASC
) local parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.IND_U_TEMPLATE_NAME noparallel logging
/

alter table USCT_IKFL.PAYMENTS_DOCUMENTS 
  add constraint PK_PAYMENTS_DOCUMENTS primary key (ID) 
    using index USCT_IKFL.PK_PAYMENTS_DOCUMENTS enable novalidate
/

alter table USCT_IKFL.PAYMENTS_DOCUMENTS
   add constraint FK_TEMPLATES_TO_OWNERS foreign key (USER_PROFILE_ID)
      references USCT_IKFL.USER_PROFILES (ID) enable novalidate
/

-- PAYMENTS_DOCUMENTS_EXT ----------------------------------------------------------------------------------------------
create unique index USCT_IKFL.PK_PAYMENTS_DOCUMENTS_EXT on USCT_IKFL.PAYMENTS_DOCUMENTS_EXT(
  ID
) global partition by hash(ID) partitions 64 parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.PK_PAYMENTS_DOCUMENTS_EXT noparallel logging
/

create index USCT_IKFL.DXFK_TEMPLATE_EXT_TO_TEMPLATE on USCT_IKFL.PAYMENTS_DOCUMENTS_EXT (
  PAYMENT_ID
) local parallel 32 nologging tablespace USCT_IDX
/
alter index USCT_IKFL.DXFK_TEMPLATE_EXT_TO_TEMPLATE noparallel logging
/

alter table USCT_IKFL.PAYMENTS_DOCUMENTS_EXT 
  add constraint PK_PAYMENTS_DOCUMENTS_EXT primary key (ID)
    using index USCT_IKFL.PK_PAYMENTS_DOCUMENTS_EXT enable novalidate
/

alter table USCT_IKFL.PAYMENTS_DOCUMENTS_EXT
   add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key (PAYMENT_ID)
      references USCT_IKFL.PAYMENTS_DOCUMENTS (ID)
        on delete cascade enable novalidate
/


begin
  dbms_stats.gather_table_stats( ownname => 'USCT_IKFL', tabname => 'USER_PROFILES', estimate_percent => 100, degree => 32, cascade => true, no_invalidate => false );
  dbms_stats.gather_table_stats( ownname => 'USCT_IKFL', tabname => 'PAYMENTS_DOCUMENTS', estimate_percent => 100, degree => 32, cascade => true, no_invalidate => false );
  dbms_stats.gather_table_stats( ownname => 'USCT_IKFL', tabname => 'PAYMENTS_DOCUMENTS_EXT', estimate_percent => 100, degree => 32, cascade => true, no_invalidate => false );
end;

