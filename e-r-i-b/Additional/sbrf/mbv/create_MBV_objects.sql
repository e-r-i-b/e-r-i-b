declare
  c_Schema constant varchar2(100) := 'MBV_MOCK';
begin

   execute immediate
     'create user '||c_Schema||' profile "DEFAULT" identified by '||c_Schema||' account unlock
             default tablespace "USERS"
             temporary tablespace "TEMP"
             quota unlimited on "USERS"';
   execute immediate
   'grant connect, resource, create view to '||c_Schema;
   
   execute immediate 'alter session set current_schema = '||c_Schema;
   
   execute immediate 'CREATE SEQUENCE S_CLIENT_ACC_PH
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
   execute immediate 'create table CLIENT_ACC_PH  (
   ID                   INTEGER                         not null,
   BIRTHDAY             TIMESTAMP                       not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   DOC_TYPE             VARCHAR2(32)                    not null,
   DOC_SERIES           VARCHAR2(16),
   DOC_NUMBER           VARCHAR2(16)                    not null,
   RET_CODE             VARCHAR2(16)                     not null,
   RESULT_MESSAGE        VARCHAR2(500),
   constraint PK_CLIENT_ACC_PH primary key (ID)
   )';
   
   execute immediate 'create unique index UNIQ_CLIENT_ACC_PH on CLIENT_ACC_PH 
    (
       SUR_NAME,
       FIRST_NAME,
       PATR_NAME,
       BIRTHDAY,
       DOC_TYPE,
       DOC_SERIES,
       DOC_NUMBER
    )';

   execute immediate 'CREATE SEQUENCE S_CLIENT_ACC_PH_PHONE
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
   execute immediate 'create table CLIENT_ACC_PH_PHONE  (
   ID                   INTEGER                         not null,
   CLIENT_ID            INTEGER                         not null,
   PHONE_NUMBER         VARCHAR2(11)                    not null,
   USAGE_DATE           TIMESTAMP,
   constraint PK_CLIENT_ACC_PH_PHONE primary key (ID))';

   execute immediate 'alter table CLIENT_ACC_PH_PHONE
   add constraint FK_CLIENT_PHONE_IDENT foreign key (CLIENT_ID)
      references CLIENT_ACC_PH (ID)
      on delete cascade';
      
      
   execute immediate 'CREATE SEQUENCE S_CLIENT_ACC_PH_ACC
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
   execute immediate 'create table CLIENT_ACC_PH_ACC  (
   ID                   INTEGER                         not null,
   CLIENT_ID            INTEGER                         not null,
   CLIENT_ACC_NUMBER    VARCHAR2(25)               not null,
   constraint PK_CLIENT_ACC_PH_ACC primary key (ID))';

   execute immediate 'alter table CLIENT_ACC_PH_ACC
   add constraint FK_CLIENT_ACC_IDENT foreign key (CLIENT_ID)
      references CLIENT_ACC_PH (ID)
      on delete cascade';
      
    execute immediate 'CREATE SEQUENCE S_GET_CLIENT_BY_PHONE
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
    execute immediate 'create table GET_CLIENT_BY_PHONE  (
   ID                   INTEGER                         not null,
   PHONE_NUMBER         VARCHAR2(11)         unique     not null,
   RET_CODE             VARCHAR2(16)                     not null,
   RESULT_MESSAGE        VARCHAR2(500),
   constraint PK_GET_CLIENT_BY_PHONE primary key (ID)
   )';
 
    execute immediate 'CREATE SEQUENCE S_GET_CLIENT_BY_PHONE_IDENTITY
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

   execute immediate 'create table GET_CLIENT_BY_PHONE_IDENTITY  (
   ID                   INTEGER                         not null,
   PHONE_ID             INTEGER                         not null,
   BIRTHDAY             TIMESTAMP                       not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   DOC_TYPE             VARCHAR2(32)                    not null,
   DOC_SERIES           VARCHAR2(16),
   DOC_NUMBER           VARCHAR2(16)                    not null,

   constraint PK_GET_CLIENT_BY_PHONE_IDENT primary key (ID)
   )';
   
   execute immediate 'alter table GET_CLIENT_BY_PHONE_IDENTITY
   add constraint FK_GET_CLIENT_BY_PHONE_IDENT foreign key (PHONE_ID)
      references GET_CLIENT_BY_PHONE (ID)
      on delete cascade';
      
      
    execute immediate 'CREATE SEQUENCE S_BEGIN_MIGRATION
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
   execute immediate 'create table BEGIN_MIGRATION  (
   ID                   INTEGER                         not null,
   BIRTHDAY             TIMESTAMP                       not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   DOC_TYPE             VARCHAR2(32)                    not null,
   DOC_SERIES           VARCHAR2(16),
   DOC_NUMBER           VARCHAR2(16)                    not null,
   RET_CODE             VARCHAR2(16)                     not null,
   RESULT_MESSAGE       VARCHAR2(500),
   MIGRATION_ID         VARCHAR2(32)                     not null,
   constraint PK_BEGIN_MIGRATION primary key (ID)
)';

   execute immediate 'create unique index UNIQ_BEGIN_MIGRATION on BEGIN_MIGRATION 
    (
       SUR_NAME,
       FIRST_NAME,
       PATR_NAME,
       BIRTHDAY,
       DOC_TYPE,
       DOC_SERIES,
       DOC_NUMBER
    )';
    
    execute immediate 'CREATE SEQUENCE S_COMMIT_MIGRATION
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
    execute immediate 'create table COMMIT_MIGRATION  (
       ID                   INTEGER                         not null,
       RET_CODE             VARCHAR2(16)                     not null,
       RESULT_MESSAGE       VARCHAR2(500),
       DISCONNECT_TIME      TIMESTAMP,
       MIGRATION_ID         VARCHAR2(32)                     not null,
       constraint PK_COMMIT_MIGRATION primary key (ID)
    )';

    execute immediate 'create unique index UNIQ_COMMIT_MIGRATION on COMMIT_MIGRATION 
    (
       MIGRATION_ID
    )';
    
    execute immediate 'CREATE SEQUENCE S_ROLLBACK_MIGRATION
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
    execute immediate 'create table ROLLBACK_MIGRATION  (
    ID                   INTEGER                         not null,
    RET_CODE             VARCHAR2(16)                     not null,
    MIGRATION_ID         VARCHAR2(32)                       not null,
    RESULT_MESSAGE       VARCHAR2(500),
    constraint PK_ROLLBACK_MIGRATION primary key (ID)
    )';
    
    execute immediate 'create unique index UNIQ_ROLLBACK_MIGRATION on ROLLBACK_MIGRATION 
    (
       MIGRATION_ID
    )';

    execute immediate 'CREATE SEQUENCE S_REVERSE_MIGRATION
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

    execute immediate 'create table REVERSE_MIGRATION  (
    ID                   INTEGER                         not null,
    RET_CODE             VARCHAR2(16)                     not null,
    MIGRATION_ID         VARCHAR2(32)                       not null,
    RESULT_MESSAGE       VARCHAR2(500),
    constraint PK_REVERSE_MIGRATION primary key (ID)
    )';

    execute immediate 'create unique index UNIQ_REVERSE_MIGRATION on REVERSE_MIGRATION
    (
       MIGRATION_ID
    )';

    execute immediate 'CREATE SEQUENCE S_DISC_BY_PHONE
    START WITH 1
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
    
    execute immediate 'create table DISC_BY_PHONE  (
    ID                   INTEGER                         not null,
    RET_CODE             VARCHAR2(16)                     not null,
    PHONE_NUMBER         VARCHAR2(11)         unique     not null,
    RESULT_MESSAGE        VARCHAR2(500),
    constraint PK_DISC_BY_PHONE primary key (ID)
    )';
        
end;
