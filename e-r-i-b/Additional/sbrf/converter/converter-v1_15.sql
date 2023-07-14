DROP TABLE REPRESENTATIVES CASCADE CONSTRAINTS ; 

DROP TABLE OFFICES CASCADE CONSTRAINTS ; 

DROP TABLE BANKS CASCADE CONSTRAINTS ; 

DROP SEQUENCE S_USERS ; 

DROP SEQUENCE S_SCHEMEOWNS ; 

DROP SEQUENCE S_RECEIVERS ; 

DROP SEQUENCE S_PAYMENTS_SYSTEM_LINKS ; 

DROP SEQUENCE S_LOGINS ; 

DROP SEQUENCE S_DOCUMENTS ; 

DROP SEQUENCE S_CELLTYPES_TERMS_OF_LEASE ; 

DROP SEQUENCE S_CARD_LINKS ; 

DROP SEQUENCE S_AUTHENTICATION_MODES ; 

DROP SEQUENCE S_ADDRESS ; 

DROP SEQUENCE S_ACCOUNT_LINKS ; 

DROP SEQUENCE S_ACCESSSCHEMES ; 

CREATE SEQUENCE S_CALENDARPAYMENTFORM
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_CALENDARS
  START WITH 21
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_CANCELATION_CALLBACK_LINK
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_CITIES
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_RECEPTIONTIMES
  START WITH 17181
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_REPRESENTATIVES
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_SKINS
  START WITH 21
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE SEQUENCE S_WORK_DAYS
  START WITH 21
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER ; 

CREATE TABLE CALENDARS
(
  ID    INTEGER                                 NOT NULL,
  NAME  VARCHAR2(256 BYTE)                      NOT NULL
)
 ; 

CREATE UNIQUE INDEX PK_CALENDARS ON CALENDARS
(ID)
 ; 

ALTER TABLE CALENDARS
 ADD CONSTRAINT PK_CALENDARS
 PRIMARY KEY
 (ID) ; 

CREATE TABLE CITIES
(
  ID            INTEGER                         NOT NULL,
  NAME          VARCHAR2(32 BYTE),
  FOUNDED_DATE  TIMESTAMP(6)
)
 ; 

CREATE UNIQUE INDEX PK_CITIES ON CITIES
(ID)
 ; 

ALTER TABLE CITIES
 ADD CONSTRAINT PK_CITIES
 PRIMARY KEY
 (ID) ; 

ALTER TABLE CODLOG
MODIFY(LOGIN_ID  NOT NULL) ; 


CREATE TABLE CURBANKS
(
  ID            VARCHAR2(64 BYTE)               NOT NULL,
  NAME          VARCHAR2(256 BYTE)              NOT NULL,
  SWIFT         VARCHAR2(26 BYTE),
  PLACE         VARCHAR2(50 BYTE),
  CORR_ACCOUNT  VARCHAR2(26 BYTE),
  SHORT_NAME    VARCHAR2(256 BYTE),
  COUNTRY       VARCHAR2(4 BYTE),
  ADDRESS       VARCHAR2(256 BYTE)
)
 ; 

CREATE UNIQUE INDEX AK_UK_BANKS_SWIFT ON CURBANKS
(SWIFT)
 ; 

CREATE UNIQUE INDEX PK_CURBANKS ON CURBANKS
(ID)
 ; 

ALTER TABLE CURBANKS
 ADD CONSTRAINT PK_CURBANKS
 PRIMARY KEY
 (ID) ; 

ALTER TABLE CURBANKS
 ADD CONSTRAINT AK_UK_BANKS_SWIFT
 UNIQUE (SWIFT) ; 

ALTER TABLE DEPARTMENTS
 ADD (TIME_ZONE  INTEGER default 0) ; 

ALTER TABLE DEPARTMENTS
 ADD (SERVICE  CHAR(1 BYTE) default '1') ; 

 

ALTER TABLE GUID_TO_DOCUMENTID
MODIFY(GUID VARCHAR2(64 BYTE)) ; 


ALTER TABLE PERSONAL_SUBSCRIPTION_DATA
MODIFY(MOBILE_PHONE VARCHAR2(16 BYTE)) ; 


CREATE TABLE RECEPTIONTIMES
(
  ID                      NUMBER(22)            NOT NULL,
  DEPARTMENT_ID           NUMBER(22)            NOT NULL,
  CALENDAR_ID             NUMBER(22),
  TIME_START              VARCHAR2(10 BYTE),
  TIME_END                VARCHAR2(10 BYTE),
  USE_PARENT_SETTINGS     CHAR(1 BYTE)          DEFAULT '0'                   NOT NULL,
  PAYMENTFORMNAME         VARCHAR2(256 BYTE)    NOT NULL,
  PAYMENTFORMDESCRIPTION  VARCHAR2(256 BYTE)    NOT NULL
)
 ; 

CREATE UNIQUE INDEX PK_RECEPTIONTIMES ON RECEPTIONTIMES
(ID)
 ; 

CREATE TABLE RUSBANKS
(
  ID            VARCHAR2(64 BYTE)               NOT NULL,
  NAME          VARCHAR2(256 BYTE)              NOT NULL,
  BIC           VARCHAR2(26 BYTE),
  PLACE         VARCHAR2(50 BYTE),
  CORR_ACCOUNT  VARCHAR2(26 BYTE),
  SHORT_NAME    VARCHAR2(256 BYTE),
  COUNTRY       VARCHAR2(4 BYTE),
  ADDRESS       VARCHAR2(256 BYTE),
  OUR           CHAR(1 BYTE)
)
 ; 

CREATE UNIQUE INDEX AK_UK_BANKS_BIC ON RUSBANKS
(BIC)
 ; 

CREATE UNIQUE INDEX PK_RUSBANKS ON RUSBANKS
(ID) ; 

ALTER TABLE RUSBANKS
 ADD CONSTRAINT PK_RUSBANKS
 PRIMARY KEY
 (ID) ; 

ALTER TABLE RUSBANKS
 ADD CONSTRAINT AK_UK_BANKS_BIC
 UNIQUE (BIC) ; 

CREATE TABLE SKINS
(
  ID           INTEGER                          NOT NULL,
  NAME         VARCHAR2(32 BYTE)                NOT NULL,
  URL          VARCHAR2(100 BYTE)               NOT NULL,
  SYSTEM_NAME  VARCHAR2(32 BYTE),
  CLIENT       CHAR(1 BYTE),
  ADMIN        CHAR(1 BYTE)
)
 ; 

CREATE UNIQUE INDEX PK_SKINS ON SKINS
(ID)
 ; 

ALTER TABLE SKINS
 ADD CONSTRAINT PK_SKINS
 PRIMARY KEY
 (ID) ; 

ALTER TABLE SYSTEMLOG
 ADD (MESSAGE_SOURCE  VARCHAR2(16 BYTE)        default 'Core'      NOT NULL) ; 

ALTER TABLE USERLOG
MODIFY(OPERATION_KEY VARCHAR2(400 BYTE)) ; 


ALTER TABLE USERS
MODIFY(CLIENT_ID VARCHAR2(38 BYTE)) ; 


ALTER TABLE USERS
MODIFY(HOME_PHONE VARCHAR2(20 BYTE)) ; 


ALTER TABLE USERS
MODIFY(JOB_PHONE VARCHAR2(20 BYTE)) ; 


ALTER TABLE USERS
MODIFY(MOBILE_PHONE VARCHAR2(20 BYTE)) ; 


ALTER TABLE USERS DROP COLUMN REPRESENTATIVE_ID ; 

CREATE TABLE WORK_DAYS
(
  ID           INTEGER                          NOT NULL,
  CALENDAR_ID  INTEGER                          NOT NULL,
  WORK_DATE    TIMESTAMP(6)                     NOT NULL,
  WORK_DAY     CHAR(1 BYTE)                     NOT NULL
)
 ; 

CREATE UNIQUE INDEX PK_WORK_DAYS ON WORK_DAYS
(ID)
 ; 

ALTER TABLE WORK_DAYS
 ADD CONSTRAINT PK_WORK_DAYS
 PRIMARY KEY
 (ID) ; 

-- Difference: Status (no action taken since target is valid).
ALTER TABLE ACCOUNT_LINKS
MODIFY(EXTERNAL_ID VARCHAR2(26 BYTE)) ; 


ALTER TABLE BUSINESS_DOCUMENTS
 ADD (STATE_CODE  VARCHAR2(20 BYTE) ) ; 

ALTER TABLE BUSINESS_DOCUMENTS
MODIFY(STATE_DESCRIPTION  ) ; 


ALTER TABLE BUSINESS_DOCUMENTS
 ADD (STATE_MACHINE_NAME  VARCHAR2(50 BYTE) default 'PaymentStateMachine') ; 

ALTER TABLE BUSINESS_DOCUMENTS
MODIFY(EXTERNAL_OWNER_ID VARCHAR2(38 BYTE)) ; 

UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'REFUSED'    WHERE (STATE = 'E') AND (STATE_CATEGORY = 'F') ;  
UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'EXECUTED'   WHERE (STATE = 'S' OR STATE='V') AND (STATE_CATEGORY = 'F') ; 
UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'DISPATCHED' WHERE (STATE = 'W') AND (STATE_CATEGORY = 'I') ; 
UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'SAVED'      WHERE (STATE = 'I') AND (STATE_CATEGORY = 'N') ; 
UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'PARTLY_EXECUTED'      WHERE (STATE = 'G') AND (STATE_CATEGORY = 'F') ; 
UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'ERROR'      WHERE (STATE = 'F') AND (STATE_CATEGORY = 'F') ; 


ALTER TABLE BUSINESS_DOCUMENTS DROP COLUMN STATE ; 

ALTER TABLE BUSINESS_DOCUMENTS DROP COLUMN STATE_CATEGORY ; 

ALTER TABLE BUSINESS_DOCUMENTS MODIFY STATE_CODE NOT NULL ; 

CREATE TABLE CALENDARPAYMENTFORM
(
  ID              INTEGER                       NOT NULL,
  PAYMENTFORM_ID  INTEGER                       NOT NULL,
  CALENDAR_ID     INTEGER                       NOT NULL
)
 ; 

CREATE UNIQUE INDEX PK_CALENDARPAYMENTFORM ON CALENDARPAYMENTFORM
(ID)
 ; 

ALTER TABLE CALENDARPAYMENTFORM
 ADD CONSTRAINT PK_CALENDARPAYMENTFORM
 PRIMARY KEY
 (ID) ; 

CREATE TABLE CANCELATION_CALLBACK_LINK
(
  ID           INTEGER                          NOT NULL,
  CALLBACK_ID  VARCHAR2(50 BYTE)                NOT NULL,
  PERSON_ID    INTEGER                          NOT NULL
)
 ; 

CREATE UNIQUE INDEX PK_CANCELATION_CALLBACK_LINK ON CANCELATION_CALLBACK_LINK
(ID)
 ; 

ALTER TABLE CANCELATION_CALLBACK_LINK
 ADD CONSTRAINT PK_CANCELATION_CALLBACK_LINK
 PRIMARY KEY
 (ID) ; 

ALTER TABLE CARD_LINKS
MODIFY(EXTERNAL_ID VARCHAR2(26 BYTE)) ; 


ALTER TABLE CELLTYPES_TERMS_OF_LEASE DROP COLUMN ID ; 

CREATE UNIQUE INDEX AK_KEY_1_CELLTYPE ON CELLTYPES_TERMS_OF_LEASE
(CELLTYPE_ID, TERM_OF_LEASE_ID)
 ; 
DELETE FROM SCHEMEOWNS WHERE LOGIN_ID NOT IN (SELECT ID FROM LOGINS) ; 

ALTER TABLE SCHEMEOWNS
 MODIFY CONSTRAINT FK_SCHEMEOW_FK_SCHEME_LOGINS
 VALIDATE ; 

ALTER TABLE CELLTYPES_TERMS_OF_LEASE
 ADD CONSTRAINT AK_KEY_1_CELLTYPE
 UNIQUE (CELLTYPE_ID, TERM_OF_LEASE_ID) ; 

ALTER TABLE RECEPTIONTIMES
 ADD CONSTRAINT FK_RECEPTIONTIMES_TO_CALENDAR 
 FOREIGN KEY (CALENDAR_ID) 
 REFERENCES CALENDARS (ID) ; 

ALTER TABLE WORK_DAYS
 ADD CONSTRAINT FK_WORKDAYS_CALENDAR 
 FOREIGN KEY (CALENDAR_ID) 
 REFERENCES CALENDARS (ID) ; 

ALTER TABLE CALENDARPAYMENTFORM
 ADD CONSTRAINT FK_CALENDARPAYFORM_CALENDAR 
 FOREIGN KEY (CALENDAR_ID) 
 REFERENCES CALENDARS (ID) ; 

ALTER TABLE CALENDARPAYMENTFORM
 ADD CONSTRAINT FK_CALENDARPAYFORM_PAYFORM 
 FOREIGN KEY (PAYMENTFORM_ID) 
 REFERENCES PAYMENTFORMS (ID) ; 

ALTER TABLE CANCELATION_CALLBACK_LINK
 ADD CONSTRAINT FK_CANCELAT_FK_CANCEL_USERS 
 FOREIGN KEY (PERSON_ID) 
 REFERENCES USERS (ID) ; 


UPDATE SERVICEOPERATIONS SET SINGLE='0';