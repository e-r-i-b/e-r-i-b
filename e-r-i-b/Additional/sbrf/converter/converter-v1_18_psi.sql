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
-------------------------------------------------------------------
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
create sequence S_IMACCOUNT_LINKS
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
alter table NEWS modify (IMPORTANT VARCHAR2(10))
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
alter table USERS add (CREATION_TYPE        VARCHAR2(25)   NULL)  
go
update users set CREATION_TYPE='SBOL'
go
alter table users modify (CREATION_TYPE VARCHAR2(25) NOT NULL)
go
create index deps_index on USERS (  
   DEPARTMENT_ID ASC
)
go
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

create sequence S_LIMITS
go

create sequence S_LOAN_LINKS
go

create sequence S_REPORTS

go
ALTER TABLE REPORTS ADD KIND CHAR(1) NOT NULL
go
-----ДПВБЧМЕООЩЕ УЛТЙРФЩ ЙЪ ТБУУЩМЛЙ НПДЙЖЙЛБГЙЙ вд 10.09.2010------------

create sequence S_COUNT_CONTRACTS_OKR
go

create sequence S_COUNT_CONTRACTS_OSB
go

create sequence S_COUNT_CONTRACTS_TB
go

create sequence S_COUNT_CONTRACTS_VSP
go

--create sequence S_COUNT_QUALITY_OPERATIONS
--go

--create sequence S_REPORTS
--go
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
go

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
go

create sequence S_COUNT_BUSINESS_PARAMS_SBRF
go
------------20.09.2010-----------------------------------------------------

alter  table LOAN_LINKS add PERSON_ROLE varchar2(25) not null
go
alter table LOAN_LINKS modify EXTERNAL_ID varchar2(80)
go

ALTER TABLE CARD_LINKS MODIFY EXTERNAL_ID VARCHAR2(80)
go


-----------04.10.2010----------------------------------------------------
create sequence S_USERSLIMIT
go

create table USERSLIMIT (
ID INTEGER not null,
LOGIN_ID INTEGER not null,
OPERATION_DATE TIMESTAMP not null,
AMOUNT NUMBER(15,4) not null,
CURRENCY CHAR(3) not null,
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

alter table long_offer_links add PERCENT_OF_REMAIND number(15)
go

alter table long_offer_links add EXECUTION_EVENT_TYPE varchar(40)
go

ALTER TABLE ACCOUNT_LINKS
MODIFY EXTERNAL_ID VARCHAR2(80)
go
-- Удаление счетов удаленных клиентов
delete from ACCOUNT_LINKS
where id in (select al.id from ACCOUNT_LINKS al inner join users u on al.login_id=u.login_id where u.status='D')
go
--переименование дублирующихся счетов клиентов 
declare 
    counter number;
begin
  for acct in (select max(id) as maxId, account_number from ACCOUNT_LINKS group by account_number having count(id)>1) loop

    counter := 0;   
    for acctInner in (select * from ACCOUNT_LINKS where account_number = acct.account_number) loop
        if acctInner.id <> acct.maxId then
            counter := counter + 1;
            update ACCOUNT_LINKS set account_number= acct.account_number || '#' || to_char(counter) where id= acctInner.id;    
        end if;
    end loop;
   

  end loop;
end;

go

ALTER TABLE ACCOUNT_LINKS ADD CONSTRAINT UNIQUE_ACCOUNT_NUMBER UNIQUE(ACCOUNT_NUMBER)

go

create table RATE (
ID INTEGER not null,
CREATION_DATE TIMESTAMP not null,
ORDER_DATE TIMESTAMP,
ORDER_NUMBER VARCHAR2(50),
EFF_DATE TIMESTAMP not null,
FROM_CURRENCY VARCHAR2(10) not null,
TO_CURRENCY VARCHAR2(10) not null,
FROM_VALUE NUMBER(15,4) not null,
TO_VALUE NUMBER(15,4) not null,
DEPARTMENT_ID INTEGER not null,
RATE_TYPE INTEGER not null,
constraint PK_RATE primary key (ID)
)
go

alter table RATE
add constraint FK_RATE_TO_DEPARTMENT foreign key (DEPARTMENT_ID)
references DEPARTMENTS (ID)
go

create sequence S_RATE

--КОНВЕРТИРОВАНИЕ ДАННЫХ
go
DECLARE
    destAmount NVARCHAR2(19);
    destCurrency CHAR(3);

-- Обмен валюты
begin
    for doc in (select bd.id, bd.external_id from BUSINESS_DOCUMENTS bd inner join PAYMENTFORMS pf on bd.form_id = pf.id where pf.name = 'ConvertCurrencyPayment') loop
        
        IF INSTR(doc.external_id,'v6') <> 0 
        THEN 
          SELECT df.value INTO destAmount FROM DOCUMENT_EXTENDED_FIELDS df where df.payment_id = doc.id and df.name = 'buy-amount';
          SELECT df.value INTO destCurrency FROM DOCUMENT_EXTENDED_FIELDS df where df.payment_id = doc.id and df.name = 'buy-amount-currency';

          UPDATE BUSINESS_DOCUMENTS SET destination_amount=destAmount, destination_currency=destCurrency WHERE id=doc.id;         

        END IF;

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'national-currency', 'string', 'RUB', '0');

        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'exact-amount', value = 'charge-off-field-exact' WHERE name = 'type' and value = 'SALE' and payment_id = doc.id;
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'exact-amount', value = 'destination-field-exact' WHERE name = 'type' and value = 'BUY' and payment_id = doc.id;    

        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'to-resource-currency' WHERE name = 'receiver-account-currency' and payment_id = doc.id;            
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'from-account-type' WHERE name = 'payer-account-type' and payment_id = doc.id;        
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'to-account-type' WHERE name = 'receiver-account-type' and payment_id = doc.id;        
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'to-account-name' WHERE name = 'receiver-account-name' and payment_id = doc.id;        
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'convertion-rate' WHERE name = 'course' and payment_id = doc.id;                
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'from-resource-currency' WHERE name = 'payer-account-currency' and payment_id = doc.id;
        UPDATE DOCUMENT_EXTENDED_FIELDS SET name = 'from-account-name' WHERE name = 'payer-account-name' and payment_id = doc.id;    
        
    end loop;

    EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
end;
go
--Перевод между счетами
begin
    FOR doc IN (SELECT bd.id FROM BUSINESS_DOCUMENTS bd INNER JOIN PAYMENTFORMS pf ON bd.form_id = pf.id WHERE pf.name = 'InternalPayment') LOOP
        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'national-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'from-resource-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'exact-amount', 'string', 'charge-off-field-exact', '0');
    
    END LOOP;
end;
go
begin
-- Перевести деньги

    FOR doc IN (SELECT bd.id FROM BUSINESS_DOCUMENTS bd INNER JOIN PAYMENTFORMS pf ON bd.form_id = pf.id WHERE pf.name = 'RurPayment') LOOP
        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'national-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'from-resource-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'to-resource-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'exact-amount', 'string', 'charge-off-field-exact', '0');
    
    END LOOP;
end;
go
    --услуги
begin
    FOR doc IN (SELECT bd.id FROM BUSINESS_DOCUMENTS bd INNER JOIN PAYMENTFORMS pf ON bd.form_id = pf.id WHERE pf.name = 'RurPayJurSB') LOOP
        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'national-currency', 'string', 'RUB', '0');        

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'exact-amount', 'string', 'charge-off-field-exact', '0');
    
    END LOOP;
end;
go
    --налоги
begin
    FOR doc IN (SELECT bd.id FROM BUSINESS_DOCUMENTS bd INNER JOIN PAYMENTFORMS pf ON bd.form_id = pf.id WHERE pf.name = 'TaxPayment') LOOP
        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'national-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'from-resource-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'to-resource-currency', 'string', 'RUB', '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS (id, payment_id, name, type, value, is_changed) 
        VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, doc.id, 'exact-amount', 'string', 'charge-off-field-exact', '0');
    
    END LOOP;

end;
go
-- Обмен валют через перевести деньги
update BUSINESS_DOCUMENTS set kind = 'E' where kind = 'R'
go 

delete from sended_abstracts

go

delete from CARD_LINKS

go

-- 06.10.2010

alter table long_offer_links add OPERATION_TYPE varchar(100)

go

drop table COUNT_CONTRACTS_TB

go

 

drop sequence S_COUNT_CONTRACTS_TB

go

 

drop table COUNT_CONTRACTS_OSB

go

 

drop sequence S_COUNT_CONTRACTS_OSB

go

 

drop table COUNT_CONTRACTS_VSP

go

 

drop sequence S_COUNT_CONTRACTS_VSP

go

 

drop table COUNT_CONTRACTS_OKR

go

 

drop sequence S_COUNT_CONTRACTS_OKR

go

 

drop table COUNT_ACTIVE_USERS_TB

go

 

drop sequence S_COUNT_ACTIVE_USERS_TB

go

 

drop table COUNT_ACTIVE_USERS_VSP

go

 

drop sequence S_COUNT_ACTIVE_USERS_VSP

go

 

drop table COUNT_OPERATIONS_SBRF

go

 

drop sequence S_COUNT_OPERATIONS_SBRF

go

 

drop table COUNT_OPERATIONS

go

 

drop sequence S_COUNT_OPERATIONS

go

 

drop table COUNT_QUALITY_OPERATIONS

go

 

drop sequence S_COUNT_QUALITY_OPERATIONS

go

 

alter table business_documents

add  COUNT_ERROR INTEGER   default '0' not null 

go

 

create index DOC_EXT_FLDS_PAY_ID_NAME_VALUE on DOCUMENT_EXTENDED_FIELDS (

   PAYMENT_ID ASC,

   NAME ASC,

   VALUE ASC

)

go

 

create table COUNT_OPERATIONS  (

   ID                   INTEGER                         not null,

   KIND                 CHAR(1)                         not null,

   COUNT_OPERATIONS     INTEGER                         not null,

   DESCRIPTION          VARCHAR2(100),

   CURRENCY             VARCHAR2(5),

   AMOUNT               INTEGER,

   TB_ID                INTEGER,

   TB_NAME              VARCHAR2(256),

   OSB_ID               INTEGER,

   VSP_ID               INTEGER,

   COUNT_ERRORS         INTEGER,

   OPERATION_DATE       TIMESTAMP,

   REPORT_ID            INTEGER                         not null,

   constraint PK_COUNT_OPERATIONS primary key (ID)

)

go

 

 

create table COUNT_CONTRACTS  (

   REPORT_ID            INTEGER                         not null,

   TB_ID                INTEGER                         not null,

   TB_NAME              VARCHAR2(256),

   COUNT_UDBO_ALL       INTEGER                         not null,

   COUNT_UDBO_MONTH     INTEGER                         not null,

   COUNT_UDBO_YEAR      INTEGER                         not null,

   COUNT_SBOL_ALL       INTEGER                         not null,

   COUNT_SBOL_MONTH     INTEGER                         not null,

   COUNT_SBOL_YEAR      INTEGER                         not null,

   ID                   INTEGER                         not null,

   KIND                 CHAR(1)                         not null,

   OSB_ID               INTEGER,

   VSP_ID               INTEGER,

   EMPLOYE_NAME         VARCHAR2(256),

   constraint PK_COUNT_CONTRACTS primary key (ID)

)

go

 

create table COUNT_ACTIVE_USERS  (

   ID                   INTEGER                         not null,

   REPORT_ID            INTEGER                         not null,

   TB_ID                INTEGER                         not null,

   TB_NAME              VARCHAR2(256),

   OSB_ID               INTEGER,

   VSP_ID               INTEGER,

   COUNT_GET_LOGINS     INTEGER                         not null,

   COUNT_AUTH           INTEGER                         not null,

   COUNT_UDBO_ALL       INTEGER                         not null,

   COUNT_SBOL_ALL       INTEGER                         not null,

   KIND                 CHAR(1)                         not null,

   constraint PK_COUNT_ACTIVE_USERS primary key (ID)

)

go

 

create sequence S_COUNT_ACTIVE_USERS

go

 

create sequence S_COUNT_CONTRACTS

go

 

create sequence S_COUNT_OPERATIONS

go

 

alter table COUNT_ACTIVE_USERS

   add constraint FK_COUNT_ACTIVE_USRES_VSP foreign key (REPORT_ID)

      references REPORTS (ID)

go

 

alter table COUNT_CONTRACTS

   add constraint FK_COUNT_CONTRACTS_TB foreign key (REPORT_ID)

      references REPORTS (ID)

go

 

alter table COUNT_OPERATIONS

   add constraint FK_COUNT_OP_REPORTS foreign key (REPORT_ID)

      references REPORTS (ID)

go

alter table long_offer_links modify (external_id varchar(60))




-- 07.10.2010
go

drop table COUNT_BUSINESS_PARAMS_TB

go 

CREATE TABLE COUNT_BUSINESS_PARAMS_TB ( 

    ID                            NUMBER NOT NULL,

    REPORT_ID                         NUMBER NOT NULL,

    TB_ID                      NUMBER NOT NULL,

    TB_NAME                            VARCHAR2(256) NULL,

    COUNT_DOCS_DAY_AVG                FLOAT NOT NULL,

    COUNT_DOCS_DAY_MAX                NUMBER NOT NULL,

    COUNT_OPERATIONS_SECOND_MAX         NUMBER(15,5) NULL,

    PRIMARY KEY(ID)

)

go

 

ALTER TABLE COUNT_BUSINESS_PARAMS_TB

    ADD ( CONSTRAINT FK_COUNT_BUSINESS_PARAM

            FOREIGN KEY(REPORT_ID)

            REFERENCES REPORTS(ID)

            ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE VALIDATE )

go

 

drop table COUNT_BUSINESS_PARAMS_SBRF

go

 

 

CREATE TABLE COUNT_BUSINESS_PARAMS_SBRF ( 

    ID                            NUMBER NOT NULL,

    REPORT_ID             NUMBER NOT NULL,

    COUNT_USERS_DAY_AVG  FLOAT NOT NULL,

    COUNT_USERS_DAY_MAX  NUMBER NOT NULL,

    PRIMARY KEY(ID)

)

go

ALTER TABLE COUNT_BUSINESS_PARAMS_SBRF

    ADD ( CONSTRAINT FK_COUNT_BUSINESS_PARAM_SBRF

            FOREIGN KEY(REPORT_ID)

            REFERENCES REPORTS(ID)

            ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE VALIDATE )

go

 

drop table COUNT_OPERATIONS_TB

 

go 

drop sequence S_COUNT_OPERATIONS_TB

 

go



