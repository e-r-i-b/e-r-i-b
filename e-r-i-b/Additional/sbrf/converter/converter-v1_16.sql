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
        
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
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
        
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
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
  OPEN external_systems; 
  LOOP
    
    FETCH external_systems INTO code, name, url;
    EXIT WHEN external_systems%NOTFOUND;
    
    SELECT S_ADAPTERS.NEXTVAL INTO id1 FROM DUAL;
    INSERT INTO ADAPTERS (ID, UUID, NAME) VALUES (id1, code, name);
    
    SELECT S_NODES.NEXTVAL INTO id2 FROM DUAL;     
    INSERT INTO NODES (ID, URL, NAME) VALUES (id2, url, name);

    SELECT S_NODE_ADAPTERS.NEXTVAL INTO id FROM DUAL;     
    INSERT INTO NODE_ADAPTERS (ID, ADAPTER_ID, NODE_ID) VALUES (id, id1, id2);
    
  END LOOP;
  CLOSE external_systems;

  --изменяем поле OFFICE_ID таблицы DEPARTMENTS
  --ТБ|ОСБ|ВСП|идентификатор вн.системы -> ТБ|ОСБ|ВСП|код адаптера,
  --добавляем в связь с адаптером, в поле ADAPTER_ID вставляем идентификатор адаптера
  OPEN departments;
  LOOP
    
    FETCH departments INTO code;
    EXIT WHEN departments%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es
    WHERE es.ID = TO_NUMBER(SUBSTR(code, (INSTR(code, '|', -1) + 1), LENGTH(code)));    

    UPDATE DEPARTMENTS deps SET deps.OFFICE_ID = CONCAT(SUBSTR(code, 1, (INSTR(code, '|', -1))), uid), deps.ADAPTER_ID = (SELECT a.ID FROM ADAPTERS a WHERE a.UUID = uid)
    WHERE deps.OFFICE_ID = code;
     
  END LOOP;
  CLOSE departments;

  --изменяем поле CLIENT_ID таблицы USERS
  OPEN users;
  LOOP
  
    FETCH users INTO code;
    EXIT WHEN users%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es
    WHERE es.ID = TO_NUMBER(SUBSTR(code, (INSTR(code, '|', -1) + 1), LENGTH(code)));    

    UPDATE USERS usr SET usr.CLIENT_ID = CONCAT(SUBSTR(code, 1, (INSTR(code, '|', -1))), uid) WHERE usr.CLIENT_ID = code;
     
  END LOOP;
  CLOSE users;


  --изменяем поле EXTERNAL_ID таблицы CARD_LINKS
  --EXTERNAL_ID|идентификатор вн.системы -> EXTERNAL_ID|код адаптера  
  OPEN card_link;
  LOOP
  
    FETCH card_link INTO code;
    EXIT WHEN card_link%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es		
    WHERE es.ID = TO_NUMBER(SUBSTR(code, (INSTR(code, '|', -1) + 1), LENGTH(code)));
    
    UPDATE CARD_LINKS cl SET cl.EXTERNAL_ID = CONCAT(SUBSTR(code, 1, (INSTR(code, '|', -1))), uid) WHERE cl.EXTERNAL_ID = code; 
     
  END LOOP;
  CLOSE card_link;

  --изменяем поле EXTERNAL_ID таблицы ACCOUNT_LINKS
  --EXTERNAL_ID|идентификатор вн.системы -> EXTERNAL_ID|код адаптера  
  OPEN account_link;
  LOOP
  
    FETCH account_link INTO code;
    EXIT WHEN account_link%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es		
    WHERE es.ID = TO_NUMBER(SUBSTR(code, (INSTR(code, '|', -1) + 1), LENGTH(code)));
    
    UPDATE ACCOUNT_LINKS al SET al.EXTERNAL_ID = CONCAT(SUBSTR(code, 1, (INSTR(code, '|', -1))), uid) WHERE al.EXTERNAL_ID = code; 
     
  END LOOP;
  CLOSE account_link;

  --изменяем поле EXTERNAL_OWNER_ID таблицы BUSINESS_DOCUMENTS
  --EXTERNAL_OWNER_ID|идентификатор вн.системы -> EXTERNAL_OWNER_ID|код адаптера  
  OPEN documents;
  LOOP
  
    FETCH documents INTO code;
    EXIT WHEN documents%NOTFOUND;
    
    SELECT es.SYSTEM_UID 
    INTO uid
    FROM EXTERNAL_SYSTEMS es		
    WHERE es.ID = TO_NUMBER(SUBSTR(code, (INSTR(code, '|', -1) + 1), LENGTH(code)));
    
    UPDATE BUSINESS_DOCUMENTS doc SET doc.EXTERNAL_OWNER_ID = CONCAT(SUBSTR(code, 1, (INSTR(code, '|', -1))), uid) WHERE doc.EXTERNAL_OWNER_ID = code; 
     
  END LOOP;
  CLOSE documents;

EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;

go
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE S_EXTERNAL_SYSTEMS'; END;
go
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE S_EXTERNAL_SYSTEMS_LINKS'; END;
go
DROP TABLE EXTERNAL_SYSTEMS_LINKS
go
DROP TABLE EXTERNAL_SYSTEMS
go
