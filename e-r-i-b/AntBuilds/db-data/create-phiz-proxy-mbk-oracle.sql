----- Выполнить под пользователем System-----------------------------------
create user PHIZ_PROXY_MBK profile "DEFAULT" identified by PHIZ_PROXY_MBK account unlock
  default tablespace "USERS"
  temporary tablespace "TEMP"
  quota unlimited on "USERS"
;

grant connect, resource, create view, create sequence to PHIZ_PROXY_MBK
; 
-----------------------------------------------------------------------
-- Выполнить под пользователем PHIZ_PROXY_MBK
-----------------------------------------------------------------------
create table MBK_REGISTRATION_RESULTS
(
  ID             INTEGER NOT NULL,
  RESULT_CODE    VARCHAR2(20) NOT NULL,
  ERROR_DESCR    VARCHAR2(500),
  LAST_MODIFIED  TIMESTAMP(6) NOT NULL,
  constraint MBK_REGISTRATION_RESULTS_PK primary key (ID)
)
;

create table ERMB_PHONES(
	PHONE_NUMBER VARCHAR2(10) NOT NULL,
	PHONE_USAGE  CHAR(1) NOT NULL,
	LAST_MODIFIED TIMESTAMP NOT NULL,
	LAST_UPLOAD TIMESTAMP NOT NULL,
	constraint ERMB_PHONES primary key (PHONE_NUMBER)
);

-----------------------------------------------------------------------
-- Затем надо выполнить quartz-oracle.sql (в схеме PHIZ_PROXY_MBK)
-----------------------------------------------------------------------
