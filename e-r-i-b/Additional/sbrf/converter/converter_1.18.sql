ALTER TABLE IMAGES MODIFY(UPDATE_TIME  DEFAULT NULL) go


ALTER TABLE ACCOUNT_LINKS  ADD (SHOW_OPERATIONS  CHAR(1)  DEFAULT (1)        NOT NULL)  go


ALTER TABLE CARD_LINKS ADD (SHOW_OPERATIONS  CHAR(1)       DEFAULT (1)          NOT NULL) go


ALTER TABLE DEPARTMENTS MODIFY(USE_PARENT_ESB_SUPPORTED  DEFAULT NULL) go

ALTER TABLE DEPARTMENTS MODIFY(MDM_SUPPORTED  DEFAULT NULL) go

ALTER TABLE DEPARTMENTS MODIFY(USE_PARENT_MDM_SUPPORTED  DEFAULT NULL) go

ALTER TABLE IMACCOUNT_LINKS ADD (SHOW_OPERATIONS  CHAR(1)    DEFAULT (1)        NOT NULL) go


CREATE OR REPLACE FORCE VIEW CHILDREN_DEPARTMENT_BY_TB
(ROOT, ID)
AS 
SELECT replace(sys_connect_by_path(decode(level, 1, children.PARENT_DEPARTMENT), '~'), '~') AS ROOT, children.ID FROM DEPARTMENTS children CONNECT BY PRIOR children.id = children.parent_department go

ALTER TABLE LOAN_LINKS ADD (SHOW_OPERATIONS  CHAR(1 BYTE)         DEFAULT (1)        NOT NULL) go


ALTER TABLE USERS MODIFY(LAST_UPDATE_DATE  DEFAULT NULL) go

ALTER TABLE BUSINESS_DOCUMENTS MODIFY(IS_LONG_OFFER  DEFAULT NULL) go

alter table SERVICE_PROVIDERS add (CODE_RECIPIENT_SBOL  varchar2(21)) go

alter table SERVICE_PROVIDERS modify (CODE_RECIPIENT_SBOL  varchar2(21)) go

UPDATE SERVICE_PROVIDERS sp SET sp.CODE_RECIPIENT_SBOL = sp.id WHERE sp.KIND = 'B' go

alter table SERVICE_PROVIDERS add (NAME_SERVICE          varchar2(150)) go

update service_providers sp set name_service = (select name from payment_services where id=sp.service_id)
go

alter table SERVICE_PROVIDERS add (ALIAS varchar2(1024)) go

alter table SERVICE_PROVIDERS add (LEGAL_NAME  varchar2(264)) go

alter table SERVICE_PROVIDERS add (NAME_ON_BILL           varchar2(264)) go

alter table SERVICE_PROVIDERS add (NOT_VISIBLE_IN_HIERARCHY  char(1) default '0') go

alter table SERVICE_PROVIDERS add (IS_FEDERAL    char(1) default '0') go

alter table SERVICE_PROVIDERS modify (TRANSIT_ACCOUNT varchar(25) NULL) go

ALTER TABLE SERVICE_PROVIDERS RENAME COLUMN DEPT_AVAILABLE TO IS_DEPT_AVAILABLE go

ALTER TABLE SERVICE_PROVIDERS RENAME COLUMN POPULAR TO IS_POPULAR go

ALTER TABLE SERVICE_PROVIDERS RENAME COLUMN PROPS_ONLINE TO IS_PROPS_ONLINE go

ALTER TABLE SERVICE_PROVIDERS RENAME COLUMN BANK_DETAILS TO IS_BANK_DETAILS go

ALTER TABLE SERVICE_PROVIDERS RENAME COLUMN MOBILEBANK TO IS_MOBILEBANK go

ALTER TABLE SERVICE_PROVIDERS RENAME COLUMN ALLOW_PAYMENTS TO IS_ALLOW_PAYMENTS go

alter table FIELD_DESCRIPTIONS add (NUMBER_PRECISION integer default 0) go

alter table FIELD_DESCRIPTIONS add (IS_INCLUDE_IN_SMS char(1)  default '0' not null) go

alter table FIELD_DESCRIPTIONS add (IS_SAVE_IN_TEMPLATE char(1)  default '0' not null) go

alter table FIELD_DESCRIPTIONS add (IS_FOR_BILL char(1) default '0' not null) go

alter table FIELD_DESCRIPTIONS add (IS_HIDE_IN_CONFIRMATION char(1) default '0' not null) go

alter table REGIONS add CODE_TB varchar2(2) go

create index PAYMENT_SERVICES_IND_IMAGE_ID on PAYMENT_SERVICES (
   IMAGE_ID ASC
) go

create index SERVICE_PROVIDERS_IND_IMAGE_ID on SERVICE_PROVIDERS (
   IMAGE_ID ASC
) go

create table SERV_PROVIDER_PAYMENT_SERV
(
   PAYMENT_SERVICE_ID   INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   LIST_INDEX               INTEGER,
   constraint PK_SERV_PROVIDER_PAYMENT_SERV primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
) go

alter table SERV_PROVIDER_PAYMENT_SERV add constraint FK_PROV_PAY_SER_TO_PAY foreign key (PAYMENT_SERVICE_ID) references PAYMENT_SERVICES (ID) go

alter table SERV_PROVIDER_PAYMENT_SERV add constraint FK_PROV_PAY_SER_TO_PROV foreign key (SERVICE_PROVIDER_ID) references SERVICE_PROVIDERS (ID) go

alter table IMAGES drop (UPDATE_TIME) go

alter table IMAGES add (EXTEND_IMAGE VARCHAR2(264)) go

alter table OPERATIONDESCRIPTORS modify OPERATIONKEY varchar2(45) go


ALTER TABLE IMAGES ADD ( UPDATE_TIME TIMESTAMP default current_timestamp NOT NULL )
go

ALTER TABLE BUSINESS_DOCUMENTS ADD (EXTENDED_FIELDS CLOB)
go

alter table service_providers add TEMP_F varchar(128) go

UPDATE SERVICE_PROVIDERS sp SET sp.ALIAS = sp.NAME WHERE sp.KIND = 'B' go

UPDATE SERVICE_PROVIDERS sp SET sp.temp_f = CONCAT(sp.CODE_SERVICE, SUBSTR(sp.EXTERNAL_ID, (INSTR(sp.EXTERNAL_ID, '@', -1)), LENGTH(sp.EXTERNAL_ID))) WHERE kind='B' go

BEGIN
for s in (select external_id, code_service from service_providers where kind='B') loop
  update document_extended_fields set value=CONCAT(s.CODE_SERVICE, SUBSTR(s.EXTERNAL_ID, (INSTR(s.EXTERNAL_ID, '@', -1)), LENGTH(s.EXTERNAL_ID))) where value=s.external_id;
end loop;
END; go

declare cnt integer; 
 cod varchar(256);
 idx integer;
reg_id integer;
BEGIN
update service_providers set is_allow_payments ='0' where temp_f='1@1|phiz-gate-iqwave' and transit_account <> '40911810440210002000';
for s in (select id, external_id, temp_f, is_allow_payments from service_providers where kind='B') loop
 select count(*) into cnt from service_providers where temp_f=s.temp_f and is_allow_payments='1';
 if(cnt > 1)
   then 
      select count(*) into  cnt from field_descriptions where recipient_id = s.id and external_id='r192025125';
      if( s.external_id like '%iqw%' and cnt > 0)
          then
              dbms_output.put_line(s.id);         
              select initial_value into cod from field_descriptions where recipient_id = s.id and external_id='r192025125';
              update service_providers set code_service = cod where id=s.id;
               
              for sp in (select id, name, is_popular, is_federal, description, department_id from service_providers where temp_f = s.temp_f) loop
                    update service_providers prov set prov.alias = concat(prov.alias,sp.name) where prov.id=sp.id;
                    update service_providers set is_federal = '1' where id=sp.id;
                    update service_providers set is_popular = '1' where id=sp.id;
                    update service_providers set description = sp.description where id=sp.id;
                    update service_providers set department_id=sp.department_id;
                    delete from service_provider_regions where service_provider_id=sp.id;                                                                           
              end loop;                        
          else
             for sp in (select id, service_id from service_providers where temp_f = s.temp_f) loop
                if(sp.id <> s.id)
                then
                    delete from field_values_descr where field_id in (select id from field_descriptions where recipient_id=sp.id);
                    delete from field_validators_param where field_id in (select id from field_descriptions where recipient_id=sp.id);
                    delete from field_validators_descr where field_id in (select id from field_descriptions where recipient_id=sp.id);
                    delete from field_descriptions where recipient_id=sp.id;
                    delete from service_provider_regions where service_provider_id=sp.id;
                    delete from SERV_PROVIDER_PAYMENT_SERv where service_provider_id=sp.id;
                    select count(*) into idx from serv_provider_payment_serv where payment_service_id=sp.service_id and service_provider_id=sp.id;
                    insert into serv_provider_payment_serv values (sp.service_id, s.id, idx); 
                    delete from service_providers where id=sp.id;                       
                end if;
             end loop;
      end if;
 end if;
end loop;
END; go

UPDATE SERVICE_PROVIDERS sp SET sp.temp_f = CONCAT(sp.CODE_SERVICE, SUBSTR(sp.EXTERNAL_ID, (INSTR(sp.EXTERNAL_ID, '@', -1)), LENGTH(sp.EXTERNAL_ID))) WHERE kind='B' and is_allow_payments='1' go

update service_providers sp set external_id=temp_f WHERE kind='B' and is_allow_payments='1' go
 

ALTER TABLE SERVICE_PROVIDERS add constraint UN_CODE_S_CODE_R unique (CODE, CODE_SERVICE) go

ALTER TABLE SERVICE_PROVIDERS add constraint UN_CODE_S_CODE_R_SBOL unique (CODE_SERVICE, CODE_RECIPIENT_SBOL) go

UPDATE SERVICE_PROVIDERS sp SET sp.LEGAL_NAME = sp.NAME WHERE sp.KIND = 'B' go

UPDATE SERVICE_PROVIDERS sp SET sp.NAME_ON_BILL = sp.NAME WHERE sp.KIND = 'B' go

DECLARE
BEGIN
            FOR sp IN (SELECT * FROM SERVICE_PROVIDERS WHERE KIND = 'B') LOOP

                       INSERT INTO SERV_PROVIDER_PAYMENT_SERV VALUES (sp.SERVICE_ID, sp.ID, 0);                      
            END LOOP;
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END; go

alter table SERVICE_PROVIDERS drop constraint FK_S_PROVIDERS_TO_P_SERVICES go

alter table SERVICE_PROVIDERS DROP (SERVICE_ID) go

alter table SERVICE_PROVIDERS add (IS_AUTOPAYMENT_SUPPORTED char(1) default '0' not null) go

alter table SERVICE_PROVIDERS add (MAX_SUMMA_THRESHOLD NUMBER(15,4)) go

alter table SERVICE_PROVIDERS add (MIN_SUMMA_THRESHOLD NUMBER(15,4)) go

alter table SERVICE_PROVIDERS add (MAX_VALUE_THRESHOLD NUMBER(15,4)) go

alter table SERVICE_PROVIDERS add (MIN_VALUE_THRESHOLD NUMBER(15,4)) go

alter table SERVICE_PROVIDERS add (MAX_SUMMA_ALWAYS NUMBER(15,4)) go

alter table SERVICE_PROVIDERS add (MIN_SUMMA_ALWAYS NUMBER(15,4)) go

create sequence S_INDIVIDUAL_LIMITS go

create table INDIVIDUAL_LIMITS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   START_DATE           TIMESTAMP                       not null,
   AMOUNT               NUMBER(15,4)                    not null,
   CURRENCY             CHAR(3)                         not null,
   STATE                VARCHAR2(20)                    not null,
   IS_CHANGING          CHAR(1),
   constraint PK_INDIVIDUAL_LIMITS primary key (ID)
) go

alter table INDIVIDUAL_LIMITS add constraint FK_INDIVIDUAL_LIMITS_FK_LOGINS foreign key (LOGIN_ID) references LOGINS (ID) go

ALTER TABLE USERS ADD (SNILS VARCHAR2(14)) go

create sequence S_STATEMENTS go
 
create table STATEMENTS  (
   ID                   INTEGER                         not null,
   CLAIM_ID             INTEGER                         not null,
   STATEMENT_XML        CLOB                            not null,
   IS_VALID             CHAR(1)                         not null,
   constraint PK_STATEMENTS primary key (ID)
) go

create unique index STATEMENTS_I_CLAIM_ID on STATEMENTS (
   CLAIM_ID ASC
) go

alter table STATEMENTS add constraint FK_STATEMENT_TO_BUSINESS_DOCUM foreign key (CLAIM_ID) references BUSINESS_DOCUMENTS (ID) go

alter table DEPOSITGLOBALS drop (CALCULATOR_TRANSFORMATION, ADMIN_LIST_TRANSFORMATION, ADMIN_EDIT_TRANSFORMATION) go

alter table DEPOSITGLOBALS add (CALCULATOR_TRANSFORMATION CLOB NULL, ADMIN_LIST_TRANSFORMATION CLOB NULL, ADMIN_EDIT_TRANSFORMATION CLOB NULL) go

alter table PAYMENT_SERVICES add (PRIORITY NUMBER) go

alter table PAYMENT_SERVICES add (SERVICE_GROUP NUMBER) go

create sequence S_AUTO_PAYMENT_LINKS go

create table AUTO_PAYMENT_LINKS (
   ID                   number              not null,
   EXTERNAL_ID          varchar(130)         not null,
   LOGIN_ID             number               not null,
   AUTO_PAYMENT_NUMBER    varchar(36)          not null,
   AUTO_PAYMENT_NAME      varchar(36)          null,
   IS_CHANGED           char(1)              not null,
   SHOW_IN_MAIN         char(1)              not null,
   SHOW_IN_SYSTEM       char(1)              not null,
   constraint PK_AUTO_PAYMENT_LINKS primary key  (ID)
) go

alter table AUTO_PAYMENT_LINKS add constraint FK_AUTO_PAY_FK_AUTOPA_LOGINS foreign key (LOGIN_ID) references LOGINS (ID) go

CREATE SEQUENCE S_DEFAULT_ACCESS_SCHEMES go

create table DEFAULT_ACCESS_SCHEMES  (
   ID                   INTEGER                         not null,
   CREATION_TYPE        VARCHAR2(25)                    not null,
   DEPARTMENT_ID        INTEGER,
   SCHEME_ID            INTEGER                         not null,
   constraint PK_DEFAULT_ACCESS_SCHEMES primary key (ID)
) go

alter table DEFAULT_ACCESS_SCHEMES add constraint FK_DEFAULT__FK_DEF_SC_DEPARTME foreign key (DEPARTMENT_ID) references DEPARTMENTS (ID) on delete cascade go

alter table DEFAULT_ACCESS_SCHEMES add constraint FK_DEFAULT__REFERENCE_ACCESSSC foreign key (SCHEME_ID) references ACCESSSCHEMES (ID) on delete cascade go

UPDATE SERVICE_PROVIDERS SET IS_DEPT_AVAILABLE='0' WHERE IS_DEPT_AVAILABLE IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_FEDERAL='0' WHERE IS_FEDERAL IS NULL go

UPDATE SERVICE_PROVIDERS SET NOT_VISIBLE_IN_HIERARCHY='0' WHERE NOT_VISIBLE_IN_HIERARCHY IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_GROUND='0' WHERE IS_GROUND IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_POPULAR='0' WHERE IS_POPULAR IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_PROPS_ONLINE='0' WHERE IS_PROPS_ONLINE IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_BANK_DETAILS='0' WHERE IS_BANK_DETAILS IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_MOBILEBANK='0' WHERE IS_MOBILEBANK IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_FULL_PAYMENT='0' WHERE IS_FULL_PAYMENT IS NULL go

UPDATE SERVICE_PROVIDERS SET MOBILE_SERVICE='0' WHERE MOBILE_SERVICE IS NULL go

UPDATE SERVICE_PROVIDERS SET IS_AUTOPAYMENT_SUPPORTED='0' WHERE IS_AUTOPAYMENT_SUPPORTED IS NULL go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_DEPT_AVAILABLE CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_FEDERAL CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (NOT_VISIBLE_IN_HIERARCHY CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_GROUND CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_POPULAR CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_PROPS_ONLINE CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_BANK_DETAILS CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_MOBILEBANK CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_FULL_PAYMENT CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (MOBILE_SERVICE CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_AUTOPAYMENT_SUPPORTED CHAR(1) null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_DEPT_AVAILABLE CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_FEDERAL CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (NOT_VISIBLE_IN_HIERARCHY CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_GROUND CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_POPULAR CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_PROPS_ONLINE CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_BANK_DETAILS CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_MOBILEBANK CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_FULL_PAYMENT CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (MOBILE_SERVICE CHAR(1) default '0' not null) go

ALTER TABLE SERVICE_PROVIDERS MODIFY (IS_AUTOPAYMENT_SUPPORTED CHAR(1) default '0' not null) go
 
ALTER TABLE LOGINS ADD ( "CB_CODE" VARCHAR2(10) NULL ) go

Update LOGINS l set CB_CODE = (select CB_CODE from USERS u where u.LOGIN_ID = l.id) go

alter table DEFAULT_ACCESS_SCHEMES add constraint AK_DEFAULT_SCHEMES_UNIQUE unique (CREATION_TYPE, DEPARTMENT_ID) go
 
ALTER TABLE INDIVIDUAL_LIMITS DROP ("STATE") CASCADE CONSTRAINT go

create index INDIVIDUAL_LIMITS_LOGIN_DATE on INDIVIDUAL_LIMITS (
   LOGIN_ID ASC,
   START_DATE ASC
) go

ALTER TABLE DEPOSITDESCRIPTIONS MODIFY (DETAILS NULL, DEPARTMENT_ID NULL) go

DELETE FROM DEPOSITDESCRIPTIONS go

ALTER TABLE DEPOSITDESCRIPTIONS

ADD KIND CHAR(1) NOT NULL go

DECLARE
COUNTER NUMBER;
BEGIN
	SELECT COUNT(d.ID) INTO COUNTER FROM BUSINESS_DOCUMENTS d LEFT JOIN DOCUMENT_EXTENDED_FIELDS def ON d.ID = def.PAYMENT_ID WHERE d.KIND = 'H' AND def.value = 'jur';
	IF (COUNTER > 0)
	THEN
		FOR doc IN (SELECT d.ID FROM BUSINESS_DOCUMENTS d LEFT JOIN DOCUMENT_EXTENDED_FIELDS def ON d.ID = def.PAYMENT_ID WHERE d.KIND = 'H' AND def.value = 'jur') LOOP
			INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.ID, 'tax-payment',	'string', 'false', '0');
			UPDATE BUSINESS_DOCUMENTS bd SET bd.FORM_ID = (SELECT ID FROM PAYMENTFORMS where NAME = 'JurPayment') WHERE bd.ID = doc.ID;
		END LOOP;
		
	END IF;
END; go

DECLARE
COUNTER NUMBER;
BEGIN
	SELECT COUNT(d.ID) INTO COUNTER FROM BUSINESS_DOCUMENTS d WHERE d.KIND = 'I';

	IF (COUNTER > 0)
	THEN
		FOR d IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE KIND = 'I') LOOP
			INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, d.ID, 'tax-payment',		'string', 'true', '0');
			INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, d.ID, 'receiver-type',	'string', 'jur',  '0');
		END LOOP;
		UPDATE BUSINESS_DOCUMENTS SET FORM_ID = (SELECT ID FROM PAYMENTFORMS where NAME = 'JurPayment'), KIND='H' WHERE KIND = 'I';
	END IF;
END; go

ALTER TABLE USERS DROP COLUMN PASSPORT_WAY go

ALTER TABLE USERS ADD MDM_STATE VARCHAR(16) DEFAULT 'NOT_SENT' NOT NULL go

UPDATE USERS SET MDM_STATE='ADDED' WHERE CREATION_TYPE='SBOL' go

UPDATE CODLOG SET SYST='other' WHERE SYST IS NULL go

ALTER TABLE CODLOG MODIFY ( "SYST" VARCHAR2(10) NOT NULL ) go

create index CODLOG_APP_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
go

create index CODLOG_SYS_DATE_INDEX on CODLOG (
   SYST ASC,
   START_DATE DESC
)
go

 

create index CODLOG_ALL_INDEX on CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
go

create index SYSTEMLOG_APP_DATE_INDEX on SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
)
go

create index SYSTEMLOG_SRC_DATE_INDEX on SYSTEMLOG (
   MESSAGE_SOURCE ASC,
   START_DATE DESC
)
go

create index SYSTEMLOG_LVL_DATE_INDEX on SYSTEMLOG (
   MSG_LEVEL ASC,
   START_DATE DESC
)
go

create index SYSTEMLOG_ALL_INDEX on SYSTEMLOG (
   MSG_LEVEL ASC,
   MESSAGE_SOURCE ASC,
   APPLICATION ASC,
   START_DATE DESC
)
go

create index USERLOG_APP_DATE_INDEX on USERLOG (
   APPLICATION ASC,
   START_DATE DESC
)
go

create sequence S_PFRLINKS go

create table PFRLINKS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   SHOW_IN_MAIN         CHAR(1)                         not null,
   SHOW_OPERATIONS      CHAR(1)                         not null,
   constraint PK_PFRLINKS primary key (ID)
) go

create unique index PFRLINKS_I_LOGIN_ID on PFRLINKS (
   LOGIN_ID ASC
) go

alter table PFRLINKS add constraint FK_PFRLINKS_TO_LOGINS foreign key (LOGIN_ID) references LOGINS (ID) go

ALTER TABLE ACCOUNT_LINKS ADD (SHOW_IN_ES CHAR(1) null) go

ALTER TABLE FILTER_PARAMETERS_FIELDS MODIFY (VALUE VARCHAR2(64)) go

ALTER TABLE DEPO_ACCOUNT_OWNER_FORM MODIFY (inn null) go

CREATE SEQUENCE S_XSLT go

CREATE TABLE XSLT  (
   ID                   INTEGER                         NOT NULL,
   XSLT_TEMPLATE        CLOB                            NOT NULL,
   XSLT_NAME            VARCHAR2(64)                    NOT NULL,
   XSD                  CLOB                            NOT NULL,
   CONSTRAINT PK_XSLT PRIMARY KEY (ID)
) go

CREATE UNIQUE INDEX XSLT_IDX_XSLT_NAME ON XSLT (
   XSLT_NAME ASC
) go

ALTER TABLE PFRLINKS ADD (SHOW_IN_SYSTEM  CHAR(1 BYTE)                  DEFAULT 1                     NOT NULL) go
 
create table NOT_CONVERTED_DOCUMENTS(
   ID                   INTEGER                         not null,
   constraint UNIQUE_ID unique (ID)
) go

create table TEMPIMAGES  (
   ID                   INTEGER                         not null,
   IMAGE                BLOB
) go

insert into TEMPIMAGES (id, IMAGE) select id, IMAGE from IMAGES go

alter table IMAGES drop column IMAGE go

alter table IMAGES add  IMAGE BLOB go

update IMAGES set IMAGE= (select IMAGE from TEMPIMAGES where TEMPIMAGES.id = IMAGES.id) go

drop table TEMPIMAGES go

create sequence S_FNS_FIELDS go

create sequence S_ORDERS go

create sequence S_SHOP_ADDITIONAL_FIELDS go

create sequence S_SHOP_FIELDS go
 
create table FNS_FIELDS  (
   ID                   INTEGER                         not null,
   KBK                  VARCHAR2(20),
   OKATO                VARCHAR2(11),
   INDEX_TAXATION_DOCUMENT VARCHAR2(25),
   TAX_PAY_REASON       VARCHAR2(2),
   PAYMENT_TYPE         VARCHAR2(2),
   PERIOD               VARCHAR2(10),
   ORDER_ID             INTEGER                         not null,
   TAX_STATUS           VARCHAR2(2),
   PAYER_INN            VARCHAR2(12),
   constraint PK_FNS_FIELDS primary key (ID)
)
go
 
create unique index FNS_FIELDS_ORDERS on FNS_FIELDS (
   ORDER_ID ASC
)
go

ALTER TABLE KBK ADD (SHORT_NAME  VARCHAR2(25)) go

create table ORDERS  (
   ID                   INTEGER                         not null,
   EXTENDED_ID          VARCHAR2(100)                   not null,
   ORDER_TYPE           CHAR(1)                         not null,
   ORDER_DATE           TIMESTAMP                       not null,
   SYSTEM_NAME          VARCHAR2(32),
   IS_NOTIFICATION      CHAR(1)                        default '0' not null,
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   DESCRIPTION          VARCHAR2(255),
   RECEIVER_ACCOUNT     VARCHAR2(24),
   BIC                  VARCHAR2(9),
   CORRESPONDENT_ACCOUNT VARCHAR2(35),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(9),
   RECEIVER_NAME        VARCHAR2(160),
   PAYMENT_ID           INTEGER,
   USER_ID              INTEGER,
   UUID                 VARCHAR2(255)                   not null,
   constraint PK_ORDERS primary key (ID)
)
go

create unique index ORDERS_ORDERS on ORDERS (
   EXTENDED_ID ASC,
   ORDER_TYPE ASC,
   SYSTEM_NAME ASC
) go
 
alter table SERVICE_PROVIDERS MODIFY CODE_RECIPIENT_SBOL VARCHAR2(32)                    not null go

ALTER TABLE SERVICE_PROVIDERS ADD (URL  VARCHAR2(256)) go

ALTER TABLE SERVICE_PROVIDERS ADD (IS_FNS  CHAR(1)                        default '0' not null) go

create table SHOP_ADDITIONAL_FIELDS  (
   ID                   INTEGER                         not null,
   DESCRIPTION          VARCHAR2(255),
   PRODUCT_NAME         VARCHAR2(255),
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   COUNT                INTEGER                        default 0 not null,
   SHOP_ORDER_ID        INTEGER                         not null,
   constraint PK_SHOP_ADDITIONAL_FIELDS primary key (ID)
)
go

create table SHOP_FIELDS  (
   ID                   INTEGER                         not null,
   ORDER_ID             INTEGER,
   constraint PK_SHOP_FIELDS primary key (ID)
)
go

create unique index SHOP_FIELDS_ORDERS on SHOP_FIELDS (
   ORDER_ID ASC
)
go

 
alter table FNS_FIELDS add constraint FK_FNS_FIEL_FK_FNS_FI_ORDERS foreign key (ORDER_ID)  references ORDERS (ID) go

alter table SHOP_ADDITIONAL_FIELDS add constraint FK_SHOP_ADD_REFERENCE_SHOP_FIE foreign key (SHOP_ORDER_ID) references SHOP_FIELDS (ID) go

alter table SHOP_FIELDS add constraint FK_SHOP_FIE_REFERENCE_ORDERS foreign key (ORDER_ID) references ORDERS (ID) go

alter table DOCUMENT_EXTENDED_FIELDS modify ("VALUE"  varchar2 (4000)) go

alter table auto_payment_links modify (auto_payment_number null) go

alter table DEPARTMENTS add BILLING_ID INTEGER go

alter table DEPARTMENTS add constraint FK_DEPARTMENTS_TO_BILLINGS foreign key (BILLING_ID) references BILLINGS (ID) go

alter table BUSINESS_DOCUMENTS add COMMISSION_CURRENCY char(3) null go

update  business_documents set COMMISSION_CURRENCY=CURRENCY go

update  business_documents set COMMISSION_CURRENCY=NULL where COMMISSION is null go

update  business_documents set COMMISSION_CURRENCY=CURRENCY where COMMISSION is not null go 

create index DEPARTMENTS_BILLING_ID_INDEX on DEPARTMENTS (
   BILLING_ID ASC
)  go

ALTER TABLE USERS ADD USE_INTERNET_SECURITY char(1) go

DELETE FROM DEPOSITDESCRIPTIONS go

ALTER TABLE DEPOSITDESCRIPTIONS ADD (PRODUCT_ID INTEGER NOT NULL) go

CREATE OR REPLACE VIEW TOTAL_AGREEMENT_COUNT AS
SELECT count(usr.id) as TOTAL_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
GROUP BY department.tb go

CREATE OR REPLACE VIEW TOTAL_DISOLV_AGREEMENT_COUNT AS
SELECT count(usr.id) as TOTAL_DISOLV_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
WHERE STATUS = 'D'
GROUP BY department.tb go

 

CREATE OR REPLACE VIEW TODAY_AGREEMENT_COUNT AS
SELECT count(usr.id) as TODAY_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
WHERE AGREEMENT_DATE > trunc(sysdate)
GROUP BY department.tb go

CREATE OR REPLACE VIEW TODAY_DISOLV_AGREEMENT_COUNT AS
SELECT count(usr.id) as TODAY_DISOLV_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
WHERE STATUS = 'D'
AND PROLONGATION_REJECTION_DATE > trunc(sysdate)
GROUP BY department.tb go
 
CREATE OR REPLACE VIEW PAYMENT_COUNT_TODAY AS 
SELECT count(document.id) as PAYMENT_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE > trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND in ('P','I')
GROUP BY department.tb go

CREATE OR REPLACE VIEW TRANSFER_COUNT_TODAY AS 
SELECT count(document.id) as TRANSFER_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE > trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND = 'E'
GROUP BY department.tb go

CREATE OR REPLACE VIEW LOAN_PAYMENT_COUNT_TODAY AS 
SELECT count(document.id) as LOAN_PAYMENT_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE > trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND = 'T'
GROUP BY department.tb go

CREATE OR REPLACE VIEW ERROR_DOCUMENT_COUNT_TODAY AS 
SELECT count(document.id) as ERROR_DOCUMENT_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE > trunc(sysdate)
and STATE_CODE = 'ERROR'
GROUP BY department.tb go

CREATE OR REPLACE VIEW PAYMENT_COUNT_YESTERDAY AS 
SELECT count(document.id) as PAYMENT_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND in ('P','I')
GROUP BY department.tb go

CREATE OR REPLACE VIEW TRANSFER_COUNT_YESTERDAY AS 
SELECT count(document.id) as TRANSFER_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND = 'E'
GROUP BY department.tb go

CREATE OR REPLACE VIEW LOAN_PAYMENT_COUNT_YESTERDAY AS 
SELECT count(document.id) as LOAN_PAYMENT_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND = 'T'
GROUP BY department.tb go
 
CREATE OR REPLACE VIEW ERROR_DOCUMENT_COUNT_YESTERDAY AS 
SELECT count(document.id) as ERROR_DOCUMENT_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and STATE_CODE = 'ERROR'
GROUP BY department.tb go

CREATE OR REPLACE VIEW DISPATCH_DOCUMENT_COUNT AS
SELECT count(document.id) as DISPATCH_DOCUMENT_COUNT, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department on document.department_id = department.id
WHERE STATE_CODE in ('SENDED','DISPATCH','DISPATCHED','PARTLY_EXECUTED')
GROUP BY department.tb go

CREATE OR REPLACE VIEW DELAYED_DOCUMENT_COUNT AS 
SELECT count(document.id) as DELAYED_DOCUMENT_COUNT, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department on document.department_id = department.id
WHERE STATE_CODE in ('SENDED','DISPATCH','DISPATCHED','PARTLY_EXECUTED')
AND OPERATION_DATE > sysdate - INTERVAL '30' MINUTE
GROUP BY department.tb go

create unique index DEPOSITDESCRIPTIONS_UK_P_ID on DEPOSITDESCRIPTIONS (
   PRODUCT_ID ASC
)
go

create sequence S_MONITORING_THRESHOLD_VALUES go

create table MONITORING_THRESHOLD_VALUES  (
   ID                   INTEGER                         not null,
   DEPARTMENT_ID        INTEGER,
   REPORT               VARCHAR2(32)                    not null,
   VARNING_THRESHOLD    INTEGER                         not null,
   ERROR_THRESHOLD      INTEGER                         not null,
   constraint PK_MONITORING_THRESHOLD_VALUES primary key (ID)
) go

update business_documents set state_code = 'EXECUTED' where kind = 'Y' and state_code = 'SUCCESSED' go

ALTER TABLE DEPOSITDESCRIPTIONS ADD (AVAILABLE_ONLINE CHAR(1) default '0' not null) go

UPDATE BUSINESS_DOCUMENTS SET IS_LONG_OFFER='0' WHERE IS_LONG_OFFER IS NULL go

ALTER TABLE "BUSINESS_DOCUMENTS" MODIFY ( "IS_LONG_OFFER" CHAR(1) DEFAULT 0 NOT NULL ) go

create sequence S_USER_COUNTER go

create table USER_COUNTER  (
   "id"                 INTEGER                         not null,
   APPLICATION_NAME     VARCHAR2(32)                    not null,
   MODULE               VARCHAR2(16)                    not null,
   TB                   INTEGER,
   COUNT                INTEGER                         not null,
   UPDATE_TIME          TIMESTAMP                       not null,
   constraint PK_USER_COUNTER primary key ("id")
) go

create or replace view DELAYED_DOCUMENT_COUNT as
SELECT count(document.id) as DELAYED_DOCUMENT_COUNT, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department on document.department_id = department.id
WHERE STATE_CODE in ('SENDED','DISPATCH','DISPATCHED','PARTLY_EXECUTED')
AND OPERATION_DATE < sysdate - INTERVAL '30' MINUTE
GROUP BY department.tb go

CREATE OR REPLACE VIEW USER_COUNT AS
SELECT sum(count) as USER_COUNT, TB
FROM USER_COUNTER countr
WHERE MODULE IN ('PhizIC','mobile')
AND UPDATE_TIME > sysdate - INTERVAL '15' MINUTE
GROUP BY TB go

CREATE OR REPLACE VIEW EMPLOYEE_COUNT AS
SELECT sum(count) as EMPLOYEE_COUNT, TB
FROM USER_COUNTER countr
WHERE MODULE = 'PhizIA'
AND UPDATE_TIME > sysdate - INTERVAL '15' MINUTE
GROUP BY TB go

ALTER TABLE DEPOSITOR_ACCOUNT MODIFY DESTINATION null
go

drop table USER_COUNTER go

create table USER_COUNTER  (
   ID                   INTEGER                         not null,
   APPLICATION_NAME     VARCHAR2(32)                    not null,
   MODULE               VARCHAR2(16)                    not null,
   TB                   INTEGER,
   COUNT                INTEGER                         not null,
   UPDATE_TIME          TIMESTAMP                       not null,
   constraint PK_USER_COUNTER primary key (ID)
) go

create index DOCUMENTS_DOC_SERIES on DOCUMENTS (
   DOC_SERIES ASC
)
go

create index DOCUMENTS_DOC_NUMBER on DOCUMENTS (
   DOC_NUMBER ASC
)
go

create index DOCUMENTS_DOC_TYPE on DOCUMENTS (
   DOC_TYPE ASC
)
go

alter table COUNTERS add (RESET_DATE timestamp)
go

CREATE SEQUENCE S_DOCUMENT_NUMBER INCREMENT BY 1 MAXVALUE 999999 CYCLE 
go

create table PAYMENT_SERVICE_CATEGORIES (
   PAYMENT_SERVICES_ID  integer              not null,
   CATEGORY             varchar(64)          not null,
   constraint PK_PAYMENT_SERVICE_CATEGORIES primary key  (PAYMENT_SERVICES_ID, CATEGORY)
)
go

alter table PAYMENT_SERVICE_CATEGORIES
   add constraint FK_CATEGORY_TO_P_SERV foreign key (PAYMENT_SERVICES_ID)
      references PAYMENT_SERVICES (ID)
	  go



ALTER TABLE USERS
            ADD ( EMPLOYEE_ID VARCHAR2(50) NULL ) 
go

 






