-----------------------ВАЖНО!!!---------------------------------

--В Северном банке есть поля NUMBER_GOROD и PASSWORD_GOROD в таблице EXTERNAL_SYSTEMS. Что с ними делать? И для чего они нужны? Как их учесть в конвертере?

----------------------------------------------------------------
-----Конвертирование стурктуры БД v1.16 Северного банка к  структуре БД v1.17

CREATE SEQUENCE S_ADAPTERS
go
CREATE SEQUENCE S_BILLINGS
go
CREATE SEQUENCE S_FIELD_DESCRIPTIONS
go
CREATE SEQUENCE S_FIELD_VALIDATORS_DESCR
go
CREATE SEQUENCE S_FIELD_VALIDATORS_PARAM
go
CREATE SEQUENCE S_NODES
go
CREATE SEQUENCE S_NODE_ADAPTERS
go
CREATE SEQUENCE S_PAYMENT_SERVICES
go
CREATE SEQUENCE S_REGIONS
go
CREATE SEQUENCE S_SERVICE_PROVIDERS
go
ALTER TABLE ACCOUNT_LINKS MODIFY(EXTERNAL_ID VARCHAR2(36))
go
ALTER TABLE CARD_LINKS MODIFY(EXTERNAL_ID VARCHAR2(36))
go
ALTER TABLE BUSINESS_DOCUMENTS MODIFY(EXTERNAL_OWNER_ID VARCHAR2(65))
go
CREATE TABLE ADAPTERS  (
   ID                   INTEGER                         NOT NULL,
   UUID                 VARCHAR2(64)                    NOT NULL,
   NAME                 VARCHAR2(128)                   NOT NULL,
   CONSTRAINT PK_ADAPTERS primary key (ID)
)
go
CREATE TABLE NODES  (
   ID                   INTEGER                         NOT NULL,
   URL                  VARCHAR2(128)                   NOT NULL,
   NAME                 VARCHAR2(128)                   NOT NULL,
   CONSTRAINT PK_NODES primary key (ID)
)
go
CREATE TABLE NODE_ADAPTERS  (
   ID                   INTEGER                         NOT NULL,
   ADAPTER_ID           INTEGER                         NOT NULL,
   NODE_ID              INTEGER                         NOT NULL,
   CONSTRAINT PK_NODE_ADAPTERS primary key (ID)
)
go
CREATE TABLE BILLINGS  (
   ID                   INTEGER                         NOT NULL,
   EXTERNAL_ID          VARCHAR2(32)                    NOT NULL,
   CODE                 VARCHAR2(50)                    NOT NULL,
   NAME                 VARCHAR2(128)                   NOT NULL,
   NEED_UPLOAD_JBT      CHAR(1)                         NOT NULL,
   ADAPTER_ID           INTEGER                         NOT NULL,
   CONSTRAINT PK_BILLINGS primary key (ID)
)
go
CREATE TABLE PAYMENT_SERVICES  (
   ID                   INTEGER                         NOT NULL,
   CODE                 VARCHAR2(50)                    NOT NULL,
   NAME                 NVARCHAR2(128)                  NOT NULL,
   PARENT_ID            INTEGER,
   CONSTRAINT PK_PAYMENT_SERVICES primary key (ID)
)
go
CREATE UNIQUE INDEX PAYMENT_SERVICES_INDEX_CODE ON PAYMENT_SERVICES (
   CODE ASC
)
go
ALTER TABLE RECEIVERS add(SERVICE_PROVIDER_KEY VARCHAR2(128))
go
CREATE TABLE REGIONS  (
   ID                   INTEGER                         NOT NULL,
   CODE                 VARCHAR2(20)                    NOT NULL,
   NAME                 NVARCHAR2(128)                  NOT NULL,
   PARENT_ID            INTEGER,
   CONSTRAINT PK_REGIONS primary key (ID)
)
go
CREATE UNIQUE INDEX REGIONS_INDEX_CODE ON REGIONS (
   CODE ASC
)
go
CREATE TABLE SERVICE_PROVIDERS  (
   ID                   INTEGER                         NOT NULL,
   EXTERNAL_ID          VARCHAR2(128)                   NOT NULL,
   CODE                 VARCHAR2(20)                    NOT NULL,
   NAME                 VARCHAR2(128)                   NOT NULL,
   DESCRIPTION          VARCHAR2(512),
   INN                  VARCHAR2(10)                    NOT NULL,
   ACCOUNT              VARCHAR2(25)                    NOT NULL,
   BANK_CODE            VARCHAR2(9)                     NOT NULL,
   BANK_NAME            VARCHAR2(128)                   NOT NULL,
   CORR_ACCOUNT         VARCHAR2(25)                    NOT NULL,
   BILLING_ID           INTEGER                         NOT NULL,
   SERVICE_ID           INTEGER                         NOT NULL,
   DEPT_AVAILABLE       CHAR(1)                         NOT NULL,
   MAX_COMISSION_AMOUNT number(15,4),
   MIN_COMISSION_AMOUNT number(15,4),
   COMISSION_RATE       number(15,4),
   DEPARTMENT_ID        INTEGER                         NOT NULL,
   TRANSIT_ACCOUNT      VARCHAR2(25)                    NOT NULL,
   ATTR_DELIMITER       CHAR(1)                         NOT NULL,
   ATTR_VALUES_DELIMITER CHAR(1)                        NOT NULL,
   NSI_CODE             VARCHAR2(128),
   STATE                VARCHAR2(16)                    NOT NULL,
   IS_GROUND            CHAR(1)                         NOT NULL,
   CONSTRAINT PK_SERVICE_PROVIDERS primary key (ID)
)
go
CREATE UNIQUE INDEX SERVICE_PROVIDERS_INDEX_EXT_ID ON SERVICE_PROVIDERS (
   EXTERNAL_ID ASC
)
go
CREATE TABLE SERVICE_PROVIDER_REGIONS  (
   REGION_ID            INTEGER                         NOT NULL,
   SERVICE_PROVIDER_ID  INTEGER                         NOT NULL,
   CONSTRAINT PK_SERVICE_PROVIDER_REGIONS primary key (REGION_ID, SERVICE_PROVIDER_ID)
)
go
ALTER TABLE USERS MODIFY(CLIENT_ID VARCHAR2(48))
go
ALTER TABLE DEPARTMENTS ADD(ADAPTER_ID INTEGER)
go
CREATE TABLE FIELD_DESCRIPTIONS  (
   ID                   INTEGER                         NOT NULL,
   EXTERNAL_ID          VARCHAR2(40)                    NOT NULL,
   NAME                 NVARCHAR2(40)                   NOT NULL,
   DESCRIPTION          NVARCHAR2(200),
   HINT                 NVARCHAR2(200),
   TYPE                 VARCHAR2(20),
   MAX_LENGTH           INTEGER,
   MIN_LENGTH           INTEGER,
   IS_REQUIRED          CHAR(1)                         NOT NULL,
   IS_EDITABLE          CHAR(1)                         NOT NULL,
   IS_VISIBLE           CHAR(1)                         NOT NULL,
   IS_SUM               CHAR(1)                         NOT NULL,
   IS_KEY               CHAR(1)                         NOT NULL,
   INITIAL_VALUE        NVARCHAR2(50),
   RECIPIENT_ID         INTEGER                         NOT NULL,
   LIST_INDEX           INTEGER                         NOT NULL,
   CONSTRAINT PK_FIELD_DESCRIPTIONS primary key (ID)
)
go
CREATE TABLE FIELD_VALIDATORS_DESCR  (
   ID                   INTEGER                         NOT NULL,
   FIELD_ID             INTEGER                         NOT NULL,
   TYPE                 VARCHAR2(50)                    NOT NULL,
   MESSAGE              NVARCHAR2(500)                  NOT NULL,
   LIST_INDEX           INTEGER                         NOT NULL,
   CONSTRAINT PK_FIELD_VALIDATORS_DESCR primary key (ID)
)
go
CREATE TABLE FIELD_VALIDATORS_PARAM  (
   ID                   INTEGER                         NOT NULL,
   FIELD_ID             INTEGER                         NOT NULL,
   NAME                 VARCHAR2(64)                    NOT NULL,
   TYPE                 VARCHAR2(64)                    NOT NULL,
   VALUE                VARCHAR2(1024)                  NOT NULL,
   CONSTRAINT PK_FIELD_VALIDATORS_PARAM primary key (ID)
)
go
CREATE TABLE FIELD_VALUES_DESCR  (
   FIELD_ID             INTEGER                         NOT NULL,
   VALUE                NVARCHAR2(128)                  NOT NULL,
   LIST_INDEX           INTEGER                         NOT NULL,
   CONSTRAINT PK_FIELD_VALUES_DESCR primary key (FIELD_ID, LIST_INDEX)
)
go
ALTER TABLE SERVICES MODIFY (SERVICE_KEY VARCHAR2(60))
go
ALTER TABLE BILLINGS
   ADD CONSTRAINT FK_ADAPTERS_TO_BILLINGS FOREIGN KEY (ADAPTER_ID)
      REFERENCES ADAPTERS (ID)
go
ALTER TABLE NODE_ADAPTERS
   ADD CONSTRAINT FK_ADAPTERS_TO_NODE_ADAPTERS FOREIGN KEY (ADAPTER_ID)
      REFERENCES ADAPTERS (ID)
	ON DELETE CASCADE	
go
ALTER TABLE NODE_ADAPTERS
   ADD CONSTRAINT FK_NODES_TO_NODE_ADAPTERS FOREIGN KEY (NODE_ID)
      REFERENCES NODES (ID)
go
ALTER TABLE PAYMENT_SERVICES
   ADD CONSTRAINT FK_P_SERVICE_TO_P_SERVICE FOREIGN KEY (PARENT_ID)
      REFERENCES PAYMENT_SERVICES (ID)
go
ALTER TABLE REGIONS
   ADD CONSTRAINT FK_REGION_TO_REGION FOREIGN KEY (PARENT_ID)
      REFERENCES REGIONS (ID)
go
ALTER TABLE SERVICE_PROVIDERS
   ADD CONSTRAINT FK_S_PROVIDERS_TO_BILLINGS FOREIGN KEY (BILLING_ID)
      REFERENCES BILLINGS (ID)
go
ALTER TABLE SERVICE_PROVIDERS
   ADD CONSTRAINT FK_S_PROVIDERS_TO_DEPARTMENTS FOREIGN KEY (DEPARTMENT_ID)
      REFERENCES DEPARTMENTS (ID)
go
ALTER TABLE SERVICE_PROVIDERS
   ADD CONSTRAINT FK_S_PROVIDERS_TO_P_SERVICES FOREIGN KEY (SERVICE_ID)
      REFERENCES PAYMENT_SERVICES (ID)
go
ALTER TABLE SERVICE_PROVIDER_REGIONS
   ADD CONSTRAINT FK_PROV_REG_TO_PROV FOREIGN KEY (SERVICE_PROVIDER_ID)
      REFERENCES SERVICE_PROVIDERS (ID)
go
ALTER TABLE SERVICE_PROVIDER_REGIONS
   ADD CONSTRAINT FK_PROV_REG_TO_REG FOREIGN KEY (REGION_ID)
      REFERENCES REGIONS (ID)
go
ALTER TABLE DEPARTMENTS
   ADD CONSTRAINT FK_DEPARTMENTS_TO_ADAPTERS FOREIGN KEY (ADAPTER_ID)
      REFERENCES ADAPTERS (ID)
go
ALTER TABLE FIELD_VALIDATORS_DESCR
   ADD CONSTRAINT FK_F_VALID_TO_FIELD FOREIGN KEY (FIELD_ID)
      REFERENCES FIELD_DESCRIPTIONS (ID)
	ON DELETE CASCADE
go
ALTER TABLE FIELD_VALUES_DESCR
   ADD CONSTRAINT FK_F_VALUE_TO_FIELD FOREIGN KEY (FIELD_ID)
      REFERENCES FIELD_DESCRIPTIONS (ID)
	ON DELETE CASCADE	
go
ALTER TABLE FIELD_VALIDATORS_PARAM
   ADD CONSTRAINT FK_VAL_PARAM_TO_VALID FOREIGN KEY (FIELD_ID)
      REFERENCES FIELD_VALIDATORS_DESCR (ID)
	ON DELETE CASCADE	
go
CREATE UNIQUE INDEX ADAPTERS_INDEX_UUID on ADAPTERS (
   UUID ASC
)
go
CREATE UNIQUE INDEX BILLINGS_INDEX_CODE on BILLINGS (
   CODE ASC
)
go
ALTER TABLE CALENDARS ADD CONSTRAINT AK_NAME_CALENDAR UNIQUE (NAME)
go

  -- удаление платежей по городу
DECLARE
  id    NUMBER;
  COUNTER NUMBER;

BEGIN

   FOR bd IN (SELECT * FROM BUSINESS_DOCUMENTS) LOOP

      SELECT COUNT(def.ID) 
      INTO COUNTER
      FROM BUSINESS_DOCUMENTS d
      LEFT JOIN DOCUMENT_EXTENDED_FIELDS def ON def.PAYMENT_ID = d.ID 
      WHERE (d.ID = bd.ID) AND (def.NAME = 'appointment') AND (def.VALUE = 'gorod') AND (d.FORM_ID = (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayJurSB'));          

     IF (COUNTER > 0)
       THEN
          DELETE FROM DOCUMENT_EXTENDED_FIELDS def WHERE def.PAYMENT_ID = bd.ID;
          DELETE FROM BUSINESS_DOCUMENTS doc WHERE doc.ID = bd.ID;
        COMMIT;
                
       END IF;           
    
    END LOOP;
        
--EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

  --конвертирование платежей 
DECLARE

BEGIN

   FOR bd IN (SELECT * FROM BUSINESS_DOCUMENTS d WHERE d.FORM_ID = (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayJurSB')) LOOP

	UPDATE DOCUMENT_EXTENDED_FIELDS def SET def.VALUE = 'custom' 
	WHERE (def.PAYMENT_ID = bd.ID) AND 
	      ((def.NAME = 'receivPayType') OR (def.NAME = 'receiver-pay-type')) AND 
	      ((def.VALUE = 'contract') OR (def.VALUE = 'another'));	
    
	UPDATE DOCUMENT_EXTENDED_FIELDS def SET def.VALUE = 'simple' 
	WHERE (def.PAYMENT_ID = bd.ID) AND (def.NAME = 'payment-type');

   END LOOP;
        
--EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE
  id  NUMBER;
  id1 NUMBER;
  id2 NUMBER;
  code VARCHAR2(100);
  name VARCHAR2(100);
  url  VARCHAR2(100);
  uid  VARCHAR2(100); 

  CURSOR card_link        IS SELECT cl.EXTERNAL_ID FROM CARD_LINKS cl;
  CURSOR account_link     IS SELECT al.EXTERNAL_ID FROM ACCOUNT_LINKS al;
  CURSOR users            IS SELECT usr.CLIENT_ID  FROM USERS usr;  
  CURSOR documents        IS SELECT doc.EXTERNAL_OWNER_ID FROM BUSINESS_DOCUMENTS doc;
  CURSOR departments      IS SELECT deps.OFFICE_ID FROM DEPARTMENTS deps;
  CURSOR external_systems IS SELECT es.SYSTEM_UID, es.NAME, es.URL FROM EXTERNAL_SYSTEMS es;
BEGIN

  --заполняем таблицу адаптеров, узлов из таблицы внешних систем
--  OPEN external_systems; 
  FOR ext_sys IN (SELECT SYSTEM_UID as code, NAME, URL FROM EXTERNAL_SYSTEMS) LOOP
    
--    FETCH external_systems INTO code, name, url;
--    EXIT WHEN external_systems%NOTFOUND;
    
    SELECT S_ADAPTERS.NEXTVAL INTO id1 FROM DUAL;
    INSERT INTO ADAPTERS (ID, UUID, NAME) VALUES (id1, ext_sys.code, ext_sys.name);
    
    SELECT S_NODES.NEXTVAL INTO id2 FROM DUAL;     
    INSERT INTO NODES (ID, URL, NAME) VALUES (id2, ext_sys.url, ext_sys.name);

    SELECT S_NODE_ADAPTERS.NEXTVAL INTO id FROM DUAL;     
    INSERT INTO NODE_ADAPTERS (ID, ADAPTER_ID, NODE_ID) VALUES (id, id1, id2);
    
  END LOOP;
--  CLOSE external_systems;

  --изменяем поле OFFICE_ID таблицы DEPARTMENTS
  --ТБ|ОСБ|ВСП|идентификатор вн.системы -> ТБ|ОСБ|ВСП|код адаптера,
  --добавляем в связь с адаптером, в поле ADAPTER_ID вставляем идентификатор адаптера
--  OPEN departments;
  FOR dep IN (SELECT OFFICE_ID as code FROM DEPARTMENTS) LOOP
    
--    FETCH departments INTO code;
--    EXIT WHEN departments%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es
    WHERE es.ID = TO_NUMBER(SUBSTR(dep.code, (INSTR(dep.code, '|', -1) + 1), LENGTH(dep.code)));    

    UPDATE DEPARTMENTS deps SET deps.OFFICE_ID = CONCAT(SUBSTR(dep.code, 1, (INSTR(dep.code, '|', -1))), uid), deps.ADAPTER_ID = (SELECT a.ID FROM ADAPTERS a WHERE a.UUID = uid)
    WHERE deps.OFFICE_ID = dep.code;
     
  END LOOP;
--  CLOSE departments;

  --изменяем поле CLIENT_ID таблицы USERS
--  OPEN users;
  FOR usrs IN (SELECT CLIENT_ID as code  FROM USERS) LOOP
  
--    FETCH users INTO code;
--    EXIT WHEN users%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es
    WHERE es.ID = TO_NUMBER(SUBSTR(usrs.code, (INSTR(usrs.code, '|', -1) + 1), LENGTH(usrs.code)));    

    UPDATE USERS usr SET usr.CLIENT_ID = CONCAT(SUBSTR(usrs.code, 1, (INSTR(usrs.code, '|', -1))), uid) WHERE usr.CLIENT_ID = usrs.code;
     
  END LOOP;
--  CLOSE users;

  --изменяем поле EXTERNAL_ID таблицы CARD_LINKS
  --EXTERNAL_ID|идентификатор вн.системы -> EXTERNAL_ID|код адаптера  
--  OPEN card_link;
  FOR c_link IN (SELECT EXTERNAL_ID as code FROM CARD_LINKS) LOOP
  
--    FETCH card_link INTO code;
--    EXIT WHEN card_link%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es		
    WHERE es.ID = TO_NUMBER(SUBSTR(c_link.code, (INSTR(c_link.code, '|', -1) + 1), LENGTH(c_link.code)));
    
    UPDATE CARD_LINKS cl SET cl.EXTERNAL_ID = CONCAT(SUBSTR(c_link.code, 1, (INSTR(c_link.code, '|', -1))), uid) WHERE cl.EXTERNAL_ID = c_link.code; 
     
  END LOOP;
--  CLOSE card_link;

  --изменяем поле EXTERNAL_ID таблицы ACCOUNT_LINKS
  --EXTERNAL_ID|идентификатор вн.системы -> EXTERNAL_ID|код адаптера  

--в БД Северного банка есть записи ACCOUNT_LINKS, не привязанные ни к одной из внешних систем. Для них также отсутствует связка с таблицей USERS
--их необходимо удалить. Для проверки наличия таких записей можно выполнить скрипт
/*
    select * from external_systems es RIGHT JOIN account_links al
    ON es.id=TO_NUMBER(SUBSTR(al.external_id, (INSTR(al.external_id, '|', -1) + 1), LENGTH(al.external_id))) 
    where es.id is null
*/
--Для удаления - выполнить скрипт
/*
    delete from account_links where external_id in
        (select external_id from external_systems es RIGHT JOIN account_links al
            ON es.id=TO_NUMBER(SUBSTR(al.external_id, (INSTR(al.external_id, '|', -1) + 1), LENGTH(al.external_id))) 
            where es.id is null)
*/

--  OPEN account_link;
  FOR acc_link IN (SELECT EXTERNAL_ID as code FROM ACCOUNT_LINKS) LOOP
  
--    FETCH account_link INTO code;
--    EXIT WHEN account_link%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es		
    WHERE es.ID = TO_NUMBER(SUBSTR(acc_link.code, (INSTR(acc_link.code, '|', -1) + 1), LENGTH(acc_link.code)));
    
    UPDATE ACCOUNT_LINKS al SET al.EXTERNAL_ID = CONCAT(SUBSTR(acc_link.code, 1, (INSTR(acc_link.code, '|', -1))), uid) WHERE al.EXTERNAL_ID = acc_link.code; 
     
  END LOOP;
--  CLOSE account_link;

  --изменяем поле EXTERNAL_OWNER_ID таблицы BUSINESS_DOCUMENTS
  --EXTERNAL_OWNER_ID|идентификатор вн.системы -> EXTERNAL_OWNER_ID|код адаптера  
--  OPEN documents;
  FOR docum IN (SELECT EXTERNAL_OWNER_ID as code FROM BUSINESS_DOCUMENTS) LOOP
  
--    FETCH documents INTO code;
--    EXIT WHEN documents%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es		
    WHERE es.ID = TO_NUMBER(SUBSTR(docum.code, (INSTR(docum.code, '|', -1) + 1), LENGTH(docum.code)));
    
    UPDATE BUSINESS_DOCUMENTS doc SET doc.EXTERNAL_OWNER_ID = CONCAT(SUBSTR(docum.code, 1, (INSTR(docum.code, '|', -1))), uid) WHERE doc.EXTERNAL_OWNER_ID = docum.code; 
     
  END LOOP;
--  CLOSE documents;

--EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
go
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE S_EXTERNAL_SYSTEMS'; END;
go
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE S_EXTERNAL_SYSTEMS_LINKS'; END;
go
DROP TABLE EXTERNAL_SYSTEMS_LINKS
go
DROP TABLE EXTERNAL_SYSTEMS
go

-----КОНЕЦ Конвертирование стурктуры БД v1.16 Северного банка к  структуре БД v1.17

-----Конвертирование стурктуры БД v1.17 к  структуре БД v1.18

--ИЗМЕНЕНИЕ СТРУКТУРЫ БД	
create sequence S_BUSINESS_DOCUMENTS_RES
go
CREATE SEQUENCE S_FAVOURITE_LINKS
go
CREATE SEQUENCE S_IMAGES
go
CREATE SEQUENCE S_KBK
go
CREATE SEQUENCE S_MB_PAYMENT_TEMPLATE_UPDATES
go
CREATE SEQUENCE S_MB_SMS_ID
go
CREATE SEQUENCE S_MENU_LINKS
go
CREATE SEQUENCE S_PROFILE
go
create sequence S_SENDED_ABSTRACTS
go
create table SENDED_ABSTRACTS  (
   ID INTEGER not null,
   SENDED_DATE TIMESTAMP not null,
   FROM_DATE TIMESTAMP not null,
   TO_DATE TIMESTAMP not null,
   CARDLINK_ID INTEGER not null,
   constraint PK_SENDED_ABSTRACTS primary key (ID)
)
go
alter table SENDED_ABSTRACTS
   add constraint FK_SENDED_A_TO_CARD_LIN foreign key (CARDLINK_ID)
      references CARD_LINKS (ID)
go 
ALTER TABLE PAYMENT_SERVICES ADD (SYSTEM  CHAR(1) default '0' not null)
go
alter table OPERATIONDESCRIPTORS add (STRATEGY varchar(200))
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (BILLING_ID INTEGER NULL)
go
alter table DOCUMENT_EXTENDED_FIELDS modify (VALUE VARCHAR2(512))
go
ALTER TABLE CARD_LINKS ADD (CARD_NAME    VARCHAR2(56) default 'Псевдоним карты')
go
ALTER TABLE CARD_LINKS ADD (SHOW_IN_MAIN CHAR(1) default '1')
go
ALTER TABLE CARD_LINKS ADD (IS_CHANGED   CHAR(1) default '0')
go
ALTER TABLE ACCOUNT_LINKS ADD (ACCOUNT_NAME  VARCHAR2(56) default 'Псевдоним счета')
go
ALTER TABLE ACCOUNT_LINKS ADD (SHOW_IN_MAIN    CHAR(1) default '1')
go
ALTER TABLE ACCOUNT_LINKS ADD (SHOW_IN_SYSTEM CHAR(1) DEFAULT 1 NOT NULL)
go
ALTER TABLE CARD_LINKS ADD (SHOW_IN_SYSTEM CHAR(1) DEFAULT 1 NOT NULL)
go
ALTER TABLE PSEUDONYMS MODIFY VALUE VARCHAR2(30)
go
ALTER TABLE SERVICE_PROVIDERS ADD (MOBILEBANK CHAR(1) default '0' not null, MOBILEBANK_CODE VARCHAR2(32))
go
create unique index SERVICE_PROVIDERS_IDX_MB_CODE on SERVICE_PROVIDERS(MOBILEBANK_CODE ASC)
go
ALTER TABLE SERVICE_PROVIDERS ADD(BANK_DETAILS char(1) DEFAULT '1' NOT NULL)
go
ALTER TABLE SERVICE_PROVIDERS ADD(KPP varchar2(9))
go
ALTER TABLE SERVICE_PROVIDERS MODIFY(INN varchar2(10) null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY(ACCOUNT varchar2(25) null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY(BANK_CODE varchar2(9) null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY(BANK_NAME varchar2(128) null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY(CORR_ACCOUNT varchar2(25) null)
go
ALTER TABLE SERVICE_PROVIDERS ADD (KIND  CHAR(1) default 'B' not null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (KIND  CHAR(1))
go
ALTER TABLE LOGINS ADD (CSA_USER_ID VARCHAR2(25))
go
CREATE TABLE BUSINESS_DOCUMENTS_RES  (
   ID                   INTEGER                         not null,
   BUSINESS_DOCUMENT_ID INTEGER                         not null,
   TEMPLATE_NAME        VARCHAR2(286),
   IS_USE               CHAR(1)                         not null,
   ORDER_IND            INTEGER                         not null,
   constraint PK_BUSINESS_DOCUMENTS_RES primary key (ID)
)
go
CREATE TABLE FAVOURITE_LINKS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   NAME                 VARCHAR2(64)                    not null,
   LINK                 VARCHAR2(512)                   not null,
   IS_USE               CHAR(1)                         not null,
   ORDER_IND            INTEGER                         not null,
   constraint PK_FAVOURITE_LINKS primary key (ID)
)
go
CREATE TABLE IMAGES  (
   ID                   INTEGER                         not null,
   IMAGE                BLOB                            not null,
   IMAGE_HELP           VARCHAR2(25),
   constraint PK_IMAGES primary key (ID)
)
go
DROP TABLE KBK
go
CREATE TABLE KBK  (
   ID                   INTEGER                         not null,
   CODE                 INTEGER                         not null,
   DESCRIPTION          VARCHAR2(300),
   APPOINTMENT          VARCHAR2(210),
   PAYMENT_TYPE         VARCHAR2(50),
   MIN_COMMISSION_AMOUNT number(15,4),
   MIN_COMMISSION_CURRENCY VARCHAR2(20),
   MAX_COMMISSION_AMOUNT number(15,4),
   MAX_COMMISSION_CURRENCY VARCHAR2(20),
   RATE                 number(15,4),
   constraint PK_KBK primary key (ID, CODE)
)
go
create unique index KBK_INDEX_CODE on KBK (
   CODE ASC
)
go
create table MB_PAYMENT_TEMPLATE_UPDATES  
(
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   PHONE_NUMBER         VARCHAR2(32)                    not null,
   CARD_NUMBER          VARCHAR2(32)                    not null,
   DESTLIST             CLOB                            not null,
   TYPE 				SMALLINT 		DEFAULT 1 		NOT NULL,
   constraint PK_MB_PAYMENT_TEMPLATE_UPDATES primary key (ID)
)
go
create table MENU_LINKS
(
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   LINK_ID              INTEGER                         not null,
   ORDER_IND            INTEGER                         not null,
   IS_USE               CHAR(1)                         not null,
   constraint PK_MENU_LINKS primary key (ID)
)
go
CREATE TABLE MB_SMS_ID
(
   ID INTEGER NOT NULL,
   CONSTRAINT PK_MB_SMS_ID PRIMARY KEY (ID)
)
go
create table PROFILE
(
   LOGIN_ID             INTEGER                         not null,
   ID                   INTEGER                         not null,
   REGION_ID            INTEGER,
   SHOW_GIRL 			CHAR(1),
   constraint PK_PROFILE primary key (ID)
)
go
create unique index PROFILE_LOGIN_ID_UNIQUE on PROFILE (LOGIN_ID ASC)
go
ALTER TABLE LOGINS ADD (LOGON_DATE TIMESTAMP)
go
ALTER TABLE LOGINS ADD (LAST_LOGON_DATE TIMESTAMP)
go
ALTER TABLE PAYMENT_SERVICES  ADD (IMAGE_ID INTEGER)
go
ALTER TABLE PAYMENT_SERVICES  ADD (POPULAR CHAR(1) default '0' not null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (ATTR_DELIMITER CHAR(1) null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (ATTR_VALUES_DELIMITER CHAR(1) null)
go
ALTER TABLE SERVICE_PROVIDERS ADD (CODE_SERVICE	VARCHAR2(20) null)
go
ALTER TABLE SERVICE_PROVIDERS ADD (IMAGE_ID	INTEGER)
go
ALTER TABLE SERVICE_PROVIDERS ADD (POPULAR	CHAR(1) default '0' not null)
go
ALTER TABLE SERVICE_PROVIDERS ADD (PROPS_ONLINE CHAR(1) default '0' not null)
go
ALTER TABLE SERVICE_PROVIDERS ADD (ACCOUNT_TYPE VARCHAR2(16)  default 'ALL' not null)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (ACCOUNT_TYPE VARCHAR2(16))
go
ALTER TABLE PAYMENT_SERVICES  ADD DESCRIPTION VARCHAR2(512)
go
ALTER TABLE SESSION_PARAMETERS MODIFY (RANDOM_RECORD_ID VARCHAR2(50))
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (SERVICE_ID	INTEGER	NULL)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (DEPT_AVAILABLE	CHAR(1)	NULL)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_GROUND	CHAR(1)	NULL)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (POPULAR   CHAR(1) NULL)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (PROPS_ONLINE	CHAR(1)	NULL)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (ACCOUNT_TYPE	VARCHAR2(16)	NULL)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (MOBILEBANK    CHAR(1)	default '0' NULL)
go
ALTER TABLE SERVICE_PROVIDERS ADD (IS_FULL_PAYMENT	CHAR(1))
go
ALTER TABLE SERVICE_PROVIDERS ADD (PAYMENT_TYPE	VARCHAR2(20))
go
ALTER TABLE MAIL	ADD (ATTACH	BLOB)
go
ALTER TABLE MAIL    ADD (FILE_NAME	VARCHAR2(256))
go
ALTER TABLE MAIL    ADD (EMAIL	VARCHAR2(30))
go
ALTER TABLE MAIL    ADD (PHONE	VARCHAR2(20))
go
ALTER TABLE MAIL    ADD (DELETED	CHAR(1)	default '0' NOT NULL)
go
ALTER TABLE RECIPIENTS	ADD (DELETED	CHAR(1)	default '0' NOT NULL)
go
ALTER TABLE BUSINESS_DOCUMENTS_RES
   add constraint FK_RESOURCES_TO_BUSINESS_DOCUM foreign key (BUSINESS_DOCUMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
go
ALTER TABLE FAVOURITE_LINKS
   add constraint FK_FAVOURIT_LINKS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go
ALTER TABLE PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
      on delete cascade
go
ALTER TABLE SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
      on delete cascade
go
alter table MB_PAYMENT_TEMPLATE_UPDATES
   add constraint FK_MB_PAYMENT_UPDATE_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go
alter table MENU_LINKS
   add constraint FK_MENU_LINKS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go
alter table PROFILE
   add constraint FK_PROFILE_REFERENCE_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go
alter table PROFILE
   add constraint FK_PROFILE_REG_REF_REGIONS foreign key (REGION_ID)
      references REGIONS (ID)
go

--КОНВЕРТИРОВАНИЕ ДАННЫХ
DECLARE
    debt    VARCHAR2(50);
    providerId  NUMBER;
    paymentId   NUMBER;
    orderIndex  NUMBER;          
    paymentName VARCHAR2(50);
    paymentType VARCHAR2(50);
    pid number;   
    debt_count number; 
    spk number;      
BEGIN
  
    -- удаление карточных счетов, платежей по ним (со статусом введен, шаблон) , получателей платежа имеющих карточные счета
    -- карточные счета - счета начинающиеся с '40817', '40818', '40820'  
    DELETE FROM BUSINESS_DOCUMENTS_RES res WHERE res.BUSINESS_DOCUMENT_ID IN (SELECT bd.ID FROM BUSINESS_DOCUMENTS bd WHERE ((bd.STATE_CODE = 'TEMPLATE') OR (bd.STATE_CODE = 'INITIAL') OR (bd.STATE_CODE = 'SAVED')) AND ((SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40820'))); 
    DELETE FROM BUSINESS_DOCUMENTS_RES res WHERE res.BUSINESS_DOCUMENT_ID IN (SELECT bd.ID FROM BUSINESS_DOCUMENTS bd WHERE ((bd.STATE_CODE = 'TEMPLATE') OR (bd.STATE_CODE = 'INITIAL') OR (bd.STATE_CODE = 'SAVED')) AND ((SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40820')));   
    
    DELETE FROM DOCUMENT_EXTENDED_FIELDS ef WHERE ef.PAYMENT_ID IN (SELECT bd.ID FROM BUSINESS_DOCUMENTS bd WHERE ((bd.STATE_CODE = 'TEMPLATE') OR (bd.STATE_CODE = 'INITIAL') OR (bd.STATE_CODE = 'SAVED')) AND ((SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40820'))); 
    DELETE FROM DOCUMENT_EXTENDED_FIELDS ef WHERE ef.PAYMENT_ID IN (SELECT bd.ID FROM BUSINESS_DOCUMENTS bd WHERE ((bd.STATE_CODE = 'TEMPLATE') OR (bd.STATE_CODE = 'INITIAL') OR (bd.STATE_CODE = 'SAVED')) AND ((SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40820')));   
    
    DELETE FROM BUSINESS_DOCUMENTS bd WHERE ((bd.STATE_CODE = 'TEMPLATE') OR (bd.STATE_CODE = 'INITIAL') OR (bd.STATE_CODE = 'SAVED')) AND ((SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(bd.RECEIVER_ACCOUNT, 0, 5) = '40820')); 
    DELETE FROM BUSINESS_DOCUMENTS bd WHERE ((bd.STATE_CODE = 'TEMPLATE') OR (bd.STATE_CODE = 'INITIAL') OR (bd.STATE_CODE = 'SAVED')) AND ((SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(bd.PAYER_ACCOUNT, 0, 5) = '40820'));   

    DELETE FROM ACCOUNT_LINKS al WHERE (SUBSTR(al.ACCOUNT_NUMBER, 0, 5) = '40817') OR (SUBSTR(al.ACCOUNT_NUMBER, 0, 5) = '40818') OR (SUBSTR(al.ACCOUNT_NUMBER, 0, 5) = '40820');
    DELETE FROM RECEIVERS r WHERE (SUBSTR(r.RECEIVER_ACCOUNT, 0, 5) = '40817') OR (SUBSTR(r.RECEIVER_ACCOUNT, 0, 5) = '40818') OR (SUBSTR(r.RECEIVER_ACCOUNT, 0, 5) = '40820');

    --заполняем профили
	FOR login IN (SELECT * FROM LOGINS) LOOP
	
		INSERT INTO PROFILE(id, LOGIN_ID, REGION_ID, SHOW_GIRL) VALUES(S_PROFILE.nextval, login.ID, null, '1');
	
	END LOOP;
	
	-- поставщики услуг отсутствуют, поэтому обновлять НЕ НАДО!!!
/*
    FOR p IN (SELECT * FROM SERVICE_PROVIDERS) LOOP
        --добавляем ПУ значение для поля CODE_SERVICE
        UPDATE SERVICE_PROVIDERS sp SET sp.CODE_SERVICE = 'CODE_'||sp.ID    WHERE (p.ID = sp.ID);
        --изменяем внешний идентификатор ПУ (добавляем id услуги)
        UPDATE SERVICE_PROVIDERS sp SET sp.EXTERNAL_ID  = p.SERVICE_ID||'@'||p.EXTERNAL_ID WHERE (p.ID = sp.ID);
    END LOOP;
*/ 

for pid in (select * from BUSINESS_DOCUMENTS bd where (select count(*) from DOCUMENT_EXTENDED_FIELDS where name='payment-type' and payment_id=bd.id)=0 and FORM_ID=(select id from PAYMENTFORMS where name='RurPayJurSB')) LOOP
    insert into DOCUMENT_EXTENDED_FIELDS (id,payment_id,name,type,value,is_changed) values (S_DOCUMENT_EXTENDED_FIELDS.nextval,pid.id,'payment-type','string','simple','0');
end loop;

    -- платежи (InternalPayment)
	FOR bd IN (SELECT * FROM BUSINESS_DOCUMENTS ORDER BY ID) LOOP

      SELECT pf.NAME  INTO paymentName FROM PAYMENTFORMS pf WHERE (bd.FORM_ID = pf.ID); 
     
        --платеж "перевод со счета насчет"
	  IF (paymentName = 'InternalPayment')
        THEN
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'to-resource-type',     'string',  'com.rssl.phizic.business.resources.external.AccountLink',    '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'from-resource-type',   'string',  'com.rssl.phizic.business.resources.external.AccountLink',    '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'external-card-number', 'string',  '',      '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'is-external-card',     'boolean', 'false', '0');
        END IF;           
       
        --платеж "оплата товаров и услуг"
        --для ых получателей платежа (получатели 'gate-dictionary' и 'client-dictionary' биллинговые, если их тип 'payment-system')
        IF ((paymentName = 'RurPayJurSB'))
        THEN
            SELECT ef.VALUE INTO paymentType    FROM DOCUMENT_EXTENDED_FIELDS ef    WHERE (bd.ID = ef.PAYMENT_ID) AND (ef.NAME = 'payment-type');
            
            IF (paymentType = 'payment-system')
            THEN
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'fromResourceType',     'string',  'com.rssl.phizic.business.resources.external.AccountLink',    '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'from-resource-type',   'string',  'com.rssl.phizic.business.resources.external.AccountLink',    '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'fromResource',         'string',  bd.PAYER_ACCOUNT||' "'||(SELECT ef.VALUE FROM DOCUMENT_EXTENDED_FIELDS ef WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'payer-account-type'))||'"',    '0');
                UPDATE DOCUMENT_EXTENDED_FIELDS ef SET ef.NAME = 'fromAccountType'     WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'payer-account-type');
                UPDATE DOCUMENT_EXTENDED_FIELDS ef SET ef.NAME = 'from-account-type'   WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'payerAccountSelectType');
                UPDATE DOCUMENT_EXTENDED_FIELDS ef SET ef.NAME = 'fromAccountSelect'   WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'payerAccountSelect');
                select count(*) into debt_count FROM DOCUMENT_EXTENDED_FIELDS ef WHERE (bd.ID = ef.PAYMENT_ID) AND (ef.NAME = 'debt');
                if(debt_count>0) then
                    SELECT ef.VALUE INTO debt FROM DOCUMENT_EXTENDED_FIELDS ef WHERE (bd.ID = ef.PAYMENT_ID) AND (ef.NAME = 'debt'); 
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'debtFixed',            'string',   'false',        '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'debtValue',            'string',   SUBSTR(debt, 0, LENGTH(debt) -3 ),  '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'debtValue-currency',   'string',   SUBSTR(debt, -3, 3),    '0');
                end if;
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'accounts-chargeoff-support',    'string',    'true',        '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'cards-chargeoff-support',    'string',    'false',    '0');
                FOR p IN (SELECT sp.ID, sp.EXTERNAL_ID, sp.CODE_SERVICE FROM SERVICE_PROVIDERS sp WHERE ((SELECT ef.VALUE FROM DOCUMENT_EXTENDED_FIELDS ef WHERE (bd.ID = ef.PAYMENT_ID) AND (ef.NAME = 'receiverId')) = SUBSTR(sp.EXTERNAL_ID, INSTR(sp.EXTERNAL_ID, '@') + 1))) LOOP
                    UPDATE DOCUMENT_EXTENDED_FIELDS ef SET ef.VALUE = p.EXTERNAL_ID WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'receiverId');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'recipient',    'string',    p.ID,              '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'codeService',  'string',    p.CODE_SERVICE,    '0');
                END LOOP;
            END IF;                        
    
            --платеж "оплата товаров" не бил. получателя платежа
            IF ((paymentType = 'custom') OR (paymentType = 'simple'))
            THEN
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'external-card-number', 'string',   '',     '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'is-external-card',     'boolean',  'false','0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'receiver-type',        'string',   'jur',  '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'from-resource-type',   'string',   'com.rssl.phizic.business.resources.external.AccountLink',  '0');
                UPDATE DOCUMENT_EXTENDED_FIELDS ef  SET ef.NAME  = 'from-account-type' WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'payerAccountSelectType');
                UPDATE BUSINESS_DOCUMENTS d         SET d.FORM_ID = (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayment') WHERE (d.ID = bd.ID);
                UPDATE BUSINESS_DOCUMENTS d         SET d.KIND = 'H' WHERE (d.ID = bd.ID);
            END IF;
            paymentType := '';
        END IF;
              
        --платеж "перевести деньги"
        IF (paymentName = 'RurPayment')
        THEN
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'external-card-number',     'string',   '',     '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'is-external-card',         'boolean',  'false','0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'receiver-type',            'string',   'ph',  '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'receiver-subtype',			'string',   'externalAccount',	'0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'from-resource-type',       'string',   'com.rssl.phizic.business.resources.external.AccountLink',    '0');
            UPDATE DOCUMENT_EXTENDED_FIELDS ef SET ef.NAME = 'from-account-type'     WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'payer-account-type');
        END IF;
		paymentName := '';
    END LOOP;
    
    -- переводим получателей платежа в шаблоны платежей

    delete from DOCUMENT_EXTENDED_FIELDS where payment_id in (select id from BUSINESS_DOCUMENTS where LOGIN_ID in (SELECT r.login_id FROM RECEIVERS r left join users u on r.login_id=u.login_id where u.login_id is null));
    delete from BUSINESS_DOCUMENTS where login_id in (SELECT r.login_id FROM RECEIVERS r left join users u on r.login_id=u.login_id where u.login_id is null);
    delete from receivers rec where rec.id in (SELECT r.id FROM RECEIVERS r left join users u on r.login_id=u.login_id where u.login_id is null);

    orderIndex := 0;
    FOR r IN (SELECT * FROM RECEIVERS ORDER BY ID) LOOP
        SELECT S_BUSINESS_DOCUMENTS.NEXTVAL INTO paymentId FROM DUAL;
        -- не биллинговые получатели платежей
        IF (r.KIND != 'B')
        THEN
            INSERT INTO BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID) 
		VALUES (paymentId, '', r.LOGIN_ID, (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayment'), 
                CURRENT_DATE, '', 'TEMPLATE', 'Шаблон', '', (SELECT u.DEPARTMENT_ID FROM USERS u WHERE (u.LOGIN_ID = r.LOGIN_ID)), '', 
                '1', '0', 'H', CURRENT_DATE, '', '', r.RECEIVER_ACCOUNT, r.RECEIVER_NAME, '', '', '', 'PaymentStateMachine', '', '', '', '',
                CURRENT_DATE, '', (SELECT u.CLIENT_ID FROM USERS u WHERE u.LOGIN_ID = r.LOGIN_ID));
			INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-inn',     'string',  r.RECEIVER_INN,    '0');        
			
			--юридические
			IF (r.KIND = 'J')
			THEN
				INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-type',    'string',  'jur',    '0');
				INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-kpp',     'string',  r.RECEIVER_KPP,    '0');
			END IF;
				
			--физические
			IF (r.KIND = 'P')
			THEN
				INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-type',     'string',  'ph',    '0');
				INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-subtype',	 'string',	'externalAccount',	'0');
			END IF;
        END IF;
     
	  --биллинговые получатели платежей	
        IF (r.KIND = 'B')
        THEN
            INSERT INTO BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID)
		VALUES (paymentId, '', r.LOGIN_ID, (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayJurSB'), 
                CURRENT_DATE, '', 'TEMPLATE', 'Шаблон', '', (SELECT u.DEPARTMENT_ID FROM USERS u WHERE (u.LOGIN_ID = r.LOGIN_ID)), '', 
                '1', '0', 'P', CURRENT_DATE, '', '', r.RECEIVER_ACCOUNT, r.RECEIVER_NAME, '', '', '', 'PaymentStateMachine', '', '', '', '',
                CURRENT_DATE, '', (SELECT u.CLIENT_ID FROM USERS u WHERE u.LOGIN_ID = r.LOGIN_ID));

            --добавляем дополнительные поля бил. получателя платежа
            FOR field IN (SELECT * FROM FIELD_DESCRIPTIONS fd WHERE fd.RECIPIENT_ID = (SELECT sp.ID FROM SERVICE_PROVIDERS sp WHERE SUBSTR(sp.EXTERNAL_ID, INSTR(sp.EXTERNAL_ID, '@') + 1) = r.SERVICE_PROVIDER_KEY)) LOOP
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, field.EXTERNAL_ID,     'string',  '',    '0');    
            END LOOP;
           
            select count(*) into spk from SERVICE_PROVIDERS sp where (SUBSTR(sp.EXTERNAL_ID, INSTR(sp.EXTERNAL_ID, '@') + 1) = r.SERVICE_PROVIDER_KEY);
            if (spk>0) then 
               SELECT sp.ID INTO providerId FROM SERVICE_PROVIDERS sp WHERE (SUBSTR(sp.EXTERNAL_ID, INSTR(sp.EXTERNAL_ID, '@') + 1) = r.SERVICE_PROVIDER_KEY);
                FOR p IN (SELECT sp.ID, sp.EXTERNAL_ID, sp.CODE_SERVICE, sp.TRANSIT_ACCOUNT FROM SERVICE_PROVIDERS sp WHERE sp.ID = providerId) LOOP
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'recipient',    'string',    p.ID,    '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiverId',   'string',    p.EXTERNAL_ID,    '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'codeService',  'string',    p.CODE_SERVICE,    '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'transit-receiver-account',    'string',    p.TRANSIT_ACCOUNT,    '0');
                END LOOP;
            
                FOR d IN (SELECT dep.BIC, dep.NAME FROM DEPARTMENTS dep WHERE dep.ID = (SELECT sp.DEPARTMENT_ID FROM SERVICE_PROVIDERS sp WHERE sp.ID = providerId)) LOOP
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'transit-receiver-bank-bic',    'string',    d.BIC,    '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'transit-receiver-name',        'string',    d.NAME,    '0');
                END LOOP;
            end if;

            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'accounts-chargeoff-support',    'string',    'true',        '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'cards-chargeoff-support',    'string',    'false',    '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiverAccount',        'string',    r.RECEIVER_ACCOUNT,    '0');
            INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiverName',        'string',    r.RECEIVER_NAME,    '0');
        END IF;
  
        --общие поля для всех
	    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-bank',         'string',   r.BANK_NAME,    '0');
        INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-bic',          'string',   r.BANK_CODE,    '0');
        INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'receiver-cor-account',  'string',   r.RECEIVER_CORR_ACCOUNT,    '0');
        INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'is-external-card',      'boolean',  'false',    '0');
        INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, 'external-card-number',  'string',   '',    '0');
        orderIndex := orderIndex + 1;
        INSERT INTO BUSINESS_DOCUMENTS_RES   VALUES (S_BUSINESS_DOCUMENTS_RES.nextval,   paymentId, 'Шаблон платежа по получателю '||r.RECEIVER_NAME,   '0',   orderIndex);          
   END LOOP;

   DELETE FROM RECEIVERS;
   COMMIT;

--EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go
declare a char(1);
begin 
    for d in (select * from DEPARTMENTS) loop
        select case when d.parent_department is not null then '1' else '0' end  into a from dual;
        insert into RECEPTIONTIMES (id, department_id, time_start, time_end, use_parent_settings, paymentformname, paymentformdescription)
        values (s_receptiontimes.nextval, d.id, '09:00:00', '17:00:00', a,'TaxPayment','Оплата налогов');
    end loop;
end;
go
ALTER TABLE BUSINESS_DOCUMENTS DROP (TEMPLATE_NAME)
go
---CODE_SERVICE и BILLING_ID уточнить-- должны быть allow null
---модификации SERVICE_PROVIDERS можно не производить, т.к. она пустая (на 1.17 банк не работал)
/*
ALTER TABLE SERVICE_PROVIDERS MODIFY (CODE_SERVICE	VARCHAR2(20) NOT NULL)
go
update SERVICE_PROVIDERS set ACCOUNT_TYPE='DEPOSIT'
go
--Установить значение CODE_SERVICE для городских поставщиков услуг
update SERVICE_PROVIDERS set CODE_SERVICE=CODE WHERE code like 'Abonent%'
go
--Удалить поля Задолженность 
delete from FIELD_DESCRIPTIONS where EXTERNAL_ID='debt'
go
*/
delete from CARD_LINKS
go

-------------Новейшие изменения (после перехода на 1.18)

alter table Profile drop column show_girl
go
alter table Profile add SHOW_ASSISTANT CHAR(1) default '1'
go
alter table USERS add (CB_CODE VARCHAR2(10) NULL)
go
ALTER TABLE SERVICE_PROVIDERS
    ADD ( PHONE_NUMBER VARCHAR2(15) NULL )
go
ALTER TABLE SUBSCRIPTION_PARAMETERS DROP CONSTRAINT FK_PARAMETERS_TO_SUBSCRIPTIONS
GO
ALTER TABLE SUBSCRIPTION_PARAMETERS
    ADD ( CONSTRAINT FK_PARAMETERS_TO_SUBSCRIPTIONS
            FOREIGN KEY(SUBSCRIPTION_ID)
            REFERENCES SUBSCRIPTIONS(ID)
            ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE )
GO
ALTER TABLE SERVICE_PROVIDERS ADD (ALLOW_PAYMENTS CHAR(1) default '1' not null)
go
ALTER TABLE CODLOG ADD TEMP  number
GO
UPDATE CODLOG SET TEMP = LOGIN_ID 
GO
ALTER TABLE CODLOG DROP COLUMN   LOGIN_ID
GO
ALTER TABLE CODLOG ADD   LOGIN_ID NUMBER
GO
UPDATE CODLOG SET LOGIN_ID= TEMP 
GO
ALTER TABLE CODLOG DROP COLUMN TEMP
GO
delete from FAVOURITE_LINKS
    where id not in (select min(id) from FAVOURITE_LINKS group by LOGIN_ID, LINK
)
GO
alter table FAVOURITE_LINKS add constraint FAVOURITE_LINKS_UNIQUE unique (login_id, link)
GO
create table LOAN_LINKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_ID          VARCHAR2(64)                    not null,
   LOGIN_ID             INTEGER                         not null,
   ACCOUNT_NUMBER       VARCHAR2(25)                    not null,
   LOAN_NAME            VARCHAR2(56)                    not null,
   IS_CHANGED           CHAR(1)                         not null,
   SHOW_IN_MAIN         CHAR(1)                         not null,
   SHOW_IN_SYSTEM       CHAR(1)                         not null,
   constraint PK_LOAN_LINKS primary key (ID)
)
go
create index LOAN_LINKS_IND_NUMB_LOGIN_ID on LOAN_LINKS (
   LOGIN_ID ASC,
   ACCOUNT_NUMBER ASC
)
go
ALTER TABLE NOTIFICATIONS
    ADD ACCOUNT_RESOURCE_TYPE VARCHAR2(32) NULL
go
ALTER TABLE NOTIFICATIONS ADD NAME_OR_TYPE VARCHAR2(56) NULL
go
alter table LOGINS add IP_ADDRESS varchar2(15)
go
alter table LOGINS add LAST_IP_ADDRESS varchar2(15)
go
alter table service_providers add CREATION_DATE Timestamp(6) default CURRENT_DATE
go
ALTER TABLE BUSINESS_DOCUMENTS_RES ADD (TEMPLATE_ID INTEGER)
go
ALTER TABLE BUSINESS_DOCUMENTS ADD (TEMPLATE_ID INTEGER)
go
UPDATE CARD_LINKS
    SET CARD_LINKS.EXTERNAL_ID  = concat (concat (CARD_NUMBER,'^'),LOGIN_ID)
GO

---------------Изменения из ветки v1.18---------------------------------

alter table BUSINESS_DOCUMENTS add (DESTINATION_AMOUNT   NUMBER(15,4),
                                    DESTINATION_CURRENCY CHAR(3))
go
create index create_date_index on BUSINESS_DOCUMENTS (CREATION_DATE ASC)
go
create table COUNT_ACTIVE_USERS_TB  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   COUNT_GET_LOGINS     INTEGER                         not null,
   COUNT_AUTH           INTEGER                         not null,
   COUNT_UDBO_ALL       INTEGER                         not null,
   COUNT_SBOL_ALL       INTEGER                         not null,
   constraint PK_COUNT_ACTIVE_USERS_TB primary key (ID)
)
go
create table COUNT_ACTIVE_USERS_VSP  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   OSB_ID               INTEGER                         not null,
   VSP_ID               INTEGER                         not null,
   COUNT_GET_LOGINS     INTEGER                         not null,
   COUNT_AUTH           INTEGER                         not null,
   COUNT_UDBO_ALL       INTEGER                         not null,
   COUNT_SBOL_ALL       INTEGER                         not null,
   constraint PK_COUNT_ACTIVE_USERS_VSP primary key (ID)
)
go
create table COUNT_BUSINESS_PARAMS  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   COUNT_DOCS_DAY_AVG   INTEGER                         not null,
   COUNT_DOCS_DAY_MAX   INTEGER                         not null,
   COUNT_CLIENTS_DAY_AVG FLOAT                           not null,
   COUNT_CLIENTS_DAY_MAX INTEGER                         not null,
   COUNT_OPERATIONS_SECOND_MAX INTEGER                         not null,
   constraint PK_COUNT_BUSINESS_PARAMS primary key (ID)
)
go
create table COUNT_CONTRACTS_OKR  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   OSB_ID               INTEGER                         not null,
   VSP_ID               INTEGER                         not null,
   EMPLOYE_NAME         VARCHAR2(256),
   COUNT_UDBO_ALL       INTEGER                         not null,
   COUNT_UDBO_MONTH     INTEGER                         not null,
   COUNT_UDBO_YEAR      INTEGER                         not null,
   COUNT_SBOL_ALL       INTEGER                         not null,
   COUNT_SBOL_MONTH     INTEGER                         not null,
   COUNT_SBOL_YEAR      INTEGER                         not null,
   constraint PK_COUNT_CONTRACTS_OKR primary key (ID)
)
go
create table COUNT_CONTRACTS_OSB  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   OSB_ID               INTEGER                         not null,
   COUNT_UDBO_ALL       INTEGER                         not null,
   COUNT_UDBO_MONTH     INTEGER                         not null,
   COUNT_UDBO_YEAR      INTEGER                         not null,
   COUNT_SBOL_ALL       INTEGER                         not null,
   COUNT_SBOL_MONTH     INTEGER                         not null,
   COUNT_SBOL_YEAR      INTEGER                         not null,
   constraint PK_COUNT_CONTRACTS_OSB primary key (ID)
)
go
create table COUNT_CONTRACTS_TB  (
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   COUNT_UDBO_ALL       INTEGER                         not null,
   COUNT_UDBO_MONTH     INTEGER                         not null,
   COUNT_UDBO_YEAR      INTEGER                         not null,
   COUNT_SBOL_ALL_7     INTEGER                         not null,
   COUNT_SBOL_MONTH     INTEGER                         not null,
   COUNT_SBOL_YEAR      INTEGER                         not null,
   ID                   INTEGER                         not null,
   constraint PK_COUNT_CONTRACTS_TB primary key (ID)
)
go
create table COUNT_CONTRACTS_VSP  (
   ID                   INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   OSB_ID               INTEGER                         not null,
   VSP_ID               INTEGER                         not null,
   COUNT_UDBO_ALL       INTEGER                         not null,
   COUNT_UDBO_MONTH     INTEGER                         not null,
   COUNT_UDBO_YEAR      INTEGER                         not null,
   COUNT_SBOL_ALL       INTEGER                         not null,
   COUNT_SBOL_MONTH     INTEGER                         not null,
   COUNT_SBOL_YEAR      INTEGER                         not null,
   REPORT_ID            INTEGER,
   constraint PK_COUNT_CONTRACTS_VSP primary key (ID)
)
go
create table COUNT_OPERATIONS  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(255),
   OSB_ID               INTEGER                         not null,
   VSP_ID               INTEGER                         not null,
   START_DATE           TIMESTAMP,
   ENTRY                INTEGER                         not null,
   PASSWORD             INTEGER                         not null,
   REPORT_CARD          INTEGER                         not null,
   LAST_OPERATION_CARD  INTEGER                         not null,
   STOP_CARD            INTEGER                         not null,
   STOP_ACCOUNT         INTEGER                         not null,
   REPORT_ACCOUNT       INTEGER                         not null,
   HISTORY              INTEGER                         not null,
   TEMPLATE             INTEGER                         not null,
   ASSIGMENTS1          INTEGER                         not null,
   ASSIGMENTS2          INTEGER                         not null,
   ASSIGMENTS3          INTEGER                         not null,
   CREDIT_INFO          INTEGER                         not null,
   ACCOUNT_INFO         INTEGER                         not null,
   PRINT_BLANK          INTEGER                         not null,
   PRINT_CHECKS         INTEGER                         not null,
   CARD_TO_CARD         INTEGER                         not null,
   CARD_TO_ACCOUNT      INTEGER                         not null,
   ACCOUNT_TO_CARD      INTEGER                         not null,
   ACCOUNT_TO_ACCOUNT   INTEGER                         not null,
   FOREIGNER_ACCOUNT    INTEGER                         not null,
   CREDIT_FROM_CARD     INTEGER                         not null,
   CREDIT_FROM_ACCOUNT  INTEGER                         not null,
   PAY1                 INTEGER                         not null,
   PAY2                 INTEGER                         not null,
   PAY3                 INTEGER                         not null,
   ENTRY_ERROR          INTEGER,
   PASSWORD_ERROR       INTEGER,
   REPORT_CARD_ERROR    INTEGER,
   LAST_OPERATION_CARD_ERROR INTEGER,
   STOP_CARD_ERROR      INTEGER,
   STOP_ACCOUNT_ERROR   INTEGER,
   REPORT_ACCOUNT_ERROR INTEGER,
   HISTORY_ERROR        INTEGER,
   TEMPLATE_ERROR       INTEGER,
   ASSIGMENTS1_ERROR    INTEGER,
   ASSIGMENTS2_ERROR    INTEGER,
   ASSIGMENTS3_ERROR    INTEGER,
   CREDIT_INFO_ERROR    INTEGER,
   ACCOUNT_INFO_ERROR   INTEGER,
   PRINT_BLANK_ERROR    INTEGER,
   PRINT_CHECKS_ERROR   INTEGER,
   CARD_TO_CARD_ERROR   INTEGER,
   CARD_TO_ACCOUNT_ERROR INTEGER,
   ACCOUNT_TO_CARD_ERROR INTEGER,
   ACCOUNT_TO_ACCOUNT_ERROR INTEGER,
   FOREIGNER_ACCOUNT_ERROR INTEGER,
   CREDIT_FROM_CARD_ERROR INTEGER,
   CREDIT_FROM_ACCOUNT_ERROR INTEGER,
   PAY1_ERROR           INTEGER,
   PAY2_ERROR           INTEGER,
   PAY3_ERROR           INTEGER,
   KIND                 CHAR(1)                         not null,
   constraint PK_COUNT_OPERATIONS primary key (ID)
)
go
create table COUNT_OPERATIONS_SBRF  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   OPERATION_TYPE       VARCHAR2(256),
   CURRENCY             VARCHAR2(5),
   COUNT_OPERATIONS     INTEGER                         not null,
   AMMOUNT              FLOAT                           not null,
   constraint PK_COUNT_OPERATIONS_SBRF primary key (ID)
)
go
create table COUNT_OPERATIONS_TB  (
   ID                   INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   OPERATION_TYPE       VARCHAR2(256),
   CURRENCY             VARCHAR2(5),
   COUNT_OPERATIONS     INTEGER                         not null,
   AMMOUNT              FLOAT                           not null,
   constraint PK_COUNT_OPERATIONS_TB primary key (ID)
)
go
create table COUNT_QUALITY_OPERATIONS  (
   ID                   INTEGER                         not null,
   TB_ID                INTEGER                         not null,
   TB_NAME              VARCHAR2(256),
   COUNT_SUCCESS_OPERATIONS INTEGER                         not null,
   COUNT_ERROR_OPERATIONS INTEGER                         not null,
   REPORT_ID            INTEGER                         not null,
   constraint PK_COUNT_QUALITY_OPERATIONS primary key (ID)
)
go
alter table DEPARTMENTS add (ESB_SUPPORTED CHAR(1) default '0' not null)
go
create index parent_department_index on DEPARTMENTS (PARENT_DEPARTMENT ASC)
go
create table DEPO_ACCOUNT_LINKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_ID          VARCHAR2(64)                    not null,
   LOGIN_ID             INTEGER                         not null,
   ACCOUNT_NUMBER       VARCHAR2(30)                    not null,
   ACCOUNT_NAME         VARCHAR2(50),
   IS_CHANGED           CHAR(1)                         not null,
   SHOW_IN_MAIN         CHAR(1)                         not null,
   SHOW_IN_SYSTEM       CHAR(1)                         not null,
   constraint PK_DEPO_ACCOUNT_LINKS primary key (ID)
)
go
create index DEPO_ACCOUNT_LINKS_I_NUM_LOGIN on DEPO_ACCOUNT_LINKS (
   LOGIN_ID ASC,
   ACCOUNT_NUMBER ASC)
go
create index department_index on EMPLOYEES (
   DEPARTMENT_ID ASC)
go
create index surname_index on EMPLOYEES (
   SUR_NAME ASC)
go
alter table FILTER_PARAMETERS_FIELDS add (   TYPE                 VARCHAR2(10))
go
create table IMACCOUNT_LINKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_ID          VARCHAR2(70)                    not null,
   LOGIN_ID             INTEGER,
   IMACCOUNT_NUMBER     VARCHAR2(30)                    not null,
   IMACCOUNT_NAME       VARCHAR2(50)                    not null,
   SHOW_IN_MAIN         CHAR(1)                         not null,
   IS_CHANGED           CHAR(1)                         not null,
   SHOW_IN_SYSTEM       CHAR(1)                         not null,
   constraint PK_IMACCOUNT_LINKS primary key (ID)
)
go
create table LIMITS  (
   ID                   INTEGER                         not null,
   DEPARTMENT_ID        INTEGER                         not null,
   CREATION_DATE        TIMESTAMP                       not null,
   START_DATE           TIMESTAMP                       not null,
   AMOUNT               NUMBER(15,4)                    not null,
   CURRENCY             CHAR(3)                         not null,
   constraint PK_LIMITS primary key (ID)
)
go
create index LIMITS_INDEX_START_DATE on LIMITS (
   START_DATE ASC
)
go
alter table NEWS add (STATE                VARCHAR2(15),
                      TYPE                 VARCHAR2(25),
                      AUTOMATIC_PUBLISH_DATE TIMESTAMP,
                      CANCEL_PUBLISH_DATE  TIMESTAMP)
go
create table NEWS_DEPARTMENT  (
   NEWS_ID              INTEGER                         not null,
   DEPARTMENT_ID        INTEGER                         not null,
   constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, DEPARTMENT_ID)
)
go
create index paymentforms_description_index on PAYMENTFORMS (
   DESCRIPTION ASC
)
go
alter table PAYMENTS_SYSTEM_LINKS add (BILLING_ID           INTEGER)
go
create table REPORTS  (
   ID                   INTEGER                         not null,
   CREATE_DATE          TIMESTAMP                       not null,
   START_DATE           TIMESTAMP                       not null,
   END_DATE             TIMESTAMP,
   STATE                CHAR(1)                         not null,
   TYPE                 CHAR(1)                         not null,
   PARAMS               VARCHAR2(4000),
   constraint PK_REPORTS primary key (ID)
)
go
create index REPORTS_INDEX_STATE_REPORTS on REPORTS (
   STATE ASC
)
go
create index SERVICE_PROVIDERS_IND_ACC_INN on SERVICE_PROVIDERS (
   INN ASC,
   ACCOUNT ASC
)
go
create index for_reports_index on USERLOG (
   LOGIN_ID ASC,
   DESCRIPTION ASC,
   START_DATE ASC,
   APPLICATION ASC
)
go
create index description_index on USERLOG (
   DESCRIPTION ASC,
   START_DATE ASC
)
go
create index operation_key_index on USERLOG (
   OPERATION_KEY ASC
)
go
create index success_description_index on USERLOG (
   SUCCESS ASC,
   DESCRIPTION ASC
)
go
alter table USERS add (CREATION_TYPE        VARCHAR2(25)                    not null)
go
create index deps_index on USERS (
   DEPARTMENT_ID ASC
)
alter table COUNT_ACTIVE_USERS_TB
   add constraint FK_COUNT_ACTIVE_AUSERS_VSP foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_ACTIVE_USERS_VSP
   add constraint FK_COUNT_ACTIVE_USRES_VSP foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_BUSINESS_PARAMS
   add constraint FK_COUNT_BUSINESS_PARAM foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_CONTRACTS_OKR
   add constraint FK_COUNT__CONTRACT_OKR foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_CONTRACTS_OSB
   add constraint FK_COUNT_CONTRACTS_OSB foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_CONTRACTS_TB
   add constraint FK_COUNT_CONTRACTS_TB foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_CONTRACTS_VSP
   add constraint FK_COUNT_CONTRACTS_VSP foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_OPERATIONS
   add constraint FK_COUNT_OPERATION foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_OPERATIONS_SBRF
   add constraint FK_COUNT_OPERATIONS_SBRF foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_OPERATIONS_TB
   add constraint FK_COUNT_OP_REFERENCE_REPORTS foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table COUNT_QUALITY_OPERATIONS
   add constraint FK_COUNT_QU_REFERENCE_REPORTS foreign key (REPORT_ID)
      references REPORTS (ID)
go

alter table DEPO_ACCOUNT_LINKS
   add constraint FK_DEPO_ACC_FK_DEPOLI_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go

alter table IMACCOUNT_LINKS
   add constraint FK_IMACCOUN_REFERENCE_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go

alter table LIMITS
   add constraint FK_LIMITS_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

alter table PAYMENTS_SYSTEM_LINKS
   add constraint FK_PSIL_BILLINGS foreign key (BILLING_ID)
      references BILLINGS (ID)
go

create sequence S_ACCESSSCHEMES
go
create sequence S_ACCOUNT_LINKS
go
create sequence S_ADDITIONAL_CLIENTS_IDS
go
create sequence S_ADDRESS
go
create sequence S_AUTHENTICATION_MODES
go
create sequence S_CARD_LINKS
go

create sequence S_COUNT_ACTIVE_USERS_TB
go
create sequence S_COUNT_ACTIVE_USERS_VSP
go
create sequence S_COUNT_BUSINESS_PARAMS
go
create sequence S_COUNT_CONTRACT_OKR
go
create sequence S_COUNT_CONTRACT_OSB
go
create sequence S_COUNT_CONTRACT_TB
go
create sequence S_COUNT_CONTRACT_VSP
go
create sequence S_COUNT_OPERATIONS
go
create sequence S_COUNT_OPERATIONS_SBRF
go
create sequence S_COUNT_OPERATIONS_TB
go
create sequence S_COUNT_QUALITY_OPERATIONS
go
create sequence S_DEPO_ACCOUNT_LINKS
go
create sequence S_DOCUMENTS
go
create sequence S_LIMITS
go
create sequence S_LOAN_LINKS
go
create sequence S_LOGINS
go
create sequence S_PAYMENTS_SYSTEM_LINKS
go
create sequence S_RECEIVERS
go
create sequence S_REPORTS
go
create sequence S_SCHEMEOWNS
go
create sequence S_USERS
go
ALTER TABLE REPORTS ADD KIND CHAR(1) NOT NULL
go

----------------------10.09.2010------------

create sequence S_COUNT_ACTIVE_USERS_TB
go
create sequence S_COUNT_ACTIVE_USERS_VSP
go
create sequence S_COUNT_BUSINESS_PARAMS
go
create sequence S_COUNT_CONTRACTS_OKR
go
create sequence S_COUNT_CONTRACTS_OSB
go
create sequence S_COUNT_CONTRACTS_TB
go
create sequence S_COUNT_CONTRACTS_VSP
go
create sequence S_COUNT_OPERATIONS
go
create sequence S_COUNT_OPERATIONS_SBRF
go
create sequence S_COUNT_OPERATIONS_TB
go
create sequence S_COUNT_QUALITY_OPERATIONS
go
create sequence S_REPORTS
go

------------15.09.2010-----------------------------------------------------

ALTER TABLE COUNT_CONTRACTS_TB
RENAME COLUMN COUNT_SBOL_ALL_7 to COUNT_SBOL_ALL
go
DROP TABLE COUNT_BUSINESS_PARAMS
go
CREATE TABLE COUNT_BUSINESS_PARAMS_TB ( 
    ID                            NUMBER NOT NULL,
    REPORT_ID                         NUMBER NOT NULL,
    TB_ID                       NUMBER NOT NULL,
    TB_NAME                            VARCHAR2(256) NULL,
    COUNT_DOCS_DAY_AVG                FLOAT NOT NULL,
    COUNT_DOCS_DAY_MAX                NUMBER NOT NULL,
    COUNT_OPERATIONS_SECOND      NUMBER(15,5) NULL,
    PRIMARY KEY(ID)
)
go
ALTER TABLE COUNT_BUSINESS_PARAMS_TB
    ADD ( CONSTRAINT FK_COUNT_BUSINESS_PARAM
            FOREIGN KEY(REPORT_ID)
            REFERENCES REPORTS(ID)
            ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE VALIDATE )
GO
drop sequence S_COUNT_BUSINESS_PARAMS
go
create sequence S_COUNT_BUSINESS_PARAMS_TB
go
CREATE TABLE COUNT_BUSINESS_PARAMS_SBRF ( 
    ID                            NUMBER NOT NULL,
    REPORT_ID                         NUMBER NOT NULL,
    COUNT_USERS_DAY_AVG     FLOAT NOT NULL,
    COUNT_USERS_DAY_MAX              NUMBER NOT NULL,
    PRIMARY KEY(ID)
)
go
ALTER TABLE COUNT_BUSINESS_PARAMS_SBRF
    ADD ( CONSTRAINT FK_COUNT_BUSINESS_PARAM_SBRF
            FOREIGN KEY(REPORT_ID)
            REFERENCES REPORTS(ID)
            ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE VALIDATE )
GO
create sequence S_COUNT_BUSINESS_PARAMS_SBRF
go

------------20.09.2010-----------------------------------------------------

alter  table LOAN_LINKS add PERSON_ROLE varchar2(25) not null
go
alter table LOAN_LINKS modify EXTERNAL_ID varchar2(80)
go
update users set CREATION_TYPE='SBOL'
go
ALTER TABLE CARD_LINKS MODIFY EXTERNAL_ID VARCHAR2(80)

------------23.09.2010-----------------------------------------------------

create sequence S_USERSLIMIT
go
create table USERSLIMIT  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   OPERATION_DATE       TIMESTAMP                       not null,
   AMOUNT               NUMBER(15,4)                    not null,
   CURRENCY             CHAR(3)                         not null,
   constraint PK_USERSLIMIT primary key (ID)
)
go
create index USERSLIMIT_INDEX_DATE on USERSLIMIT (
   OPERATION_DATE ASC
)
go
alter table USERSLIMIT
   add constraint FK_USERSLIMIT_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go
create or replace view CHILDREN_DEPARTMENT_BY_TB as
SELECT CONNECT_BY_ROOT children.PARENT_DEPARTMENT AS ROOT, children.ID
FROM DEPARTMENTS children
CONNECT BY PRIOR children.id = children.parent_department
go

------------27.09.2010-----------------------------------------------------

create or replace view CHILDREN_DEPARTMENT_BY_TB as
SELECT replace(sys_connect_by_path(decode(level, 1, children.PARENT_DEPARTMENT), '~'), '~') AS ROOT,
children.ID
FROM DEPARTMENTS children
CONNECT BY PRIOR children.id = children.parent_department
go

------------28.09.2010-----------------------------------------------------

create sequence S_LONG_OFFER_LINKS
go

create table LONG_OFFER_LINKS(
ID number not null,
EXTERNAL_ID varchar(36) not null,
LOGIN_ID number not null,
LONG_OFFER_NUMBER varchar(36) not null,
LONG_OFFER_NAME varchar(36),
AMOUNT NUMBER(15),
CURRENCY CHAR(3),
IS_CHANGED char(1) not null,
SHOW_IN_MAIN char(1) not null,
SHOW_IN_SYSTEM char(1) not null,
constraint PK_LONG_OFFER_LINKS primary key (ID)
)
go

alter table LONG_OFFER_LINKS
   add constraint FK_LONG_OFF_FK_LONGOF_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go

