----- Выполнить под пользователем System-----------------------------------
create user MFM_MOCK profile "DEFAULT" identified by MFM_MOCK account unlock
  default tablespace "USERS"
  temporary tablespace "TEMP"
  quota unlimited on "USERS"
;

grant connect, resource, create view, create sequence to MFM_MOCK
; 
-----------------------------------------------------------------------
-- Выполнить под пользователем MFM_MOCK
-----------------------------------------------------------------------
create table OUTCOME_MESSAGES(
	GUID VARCHAR2(32) NOT NULL,
	ADDRESS VARCHAR2(20) NOT NULL,
	PRIORITY VARCHAR2(10) NOT NULL,
	TEXT VARCHAR2(1000) NULL,
	constraint PK_OUTCOME_MESSAGES primary key (GUID)
);