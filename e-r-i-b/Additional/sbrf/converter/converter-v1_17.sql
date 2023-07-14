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
   TEMPLATE_NAME        VARCHAR2(128),
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
	
	-- поставщики услуг
    FOR p IN (SELECT * FROM SERVICE_PROVIDERS) LOOP
        --добавляем ПУ значение для поля CODE_SERVICE
        UPDATE SERVICE_PROVIDERS sp SET sp.CODE_SERVICE = 'CODE_'||sp.ID    WHERE (p.ID = sp.ID);
        --изменяем внешний идентификатор ПУ (добавляем id услуги)
        UPDATE SERVICE_PROVIDERS sp SET sp.EXTERNAL_ID  = p.SERVICE_ID||'@'||p.EXTERNAL_ID WHERE (p.ID = sp.ID);
    END LOOP;
 
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
        --для биллинговых получателей платежа (получатели 'gate-dictionary' и 'client-dictionary' биллинговые, если их тип 'payment-system')
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
    
                SELECT ef.VALUE INTO debt FROM DOCUMENT_EXTENDED_FIELDS ef WHERE (bd.ID = ef.PAYMENT_ID) AND (ef.NAME = 'debt'); 
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'debtFixed',            'string',   'false',        '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'debtValue',            'string',   SUBSTR(debt, 0, LENGTH(debt) -3 ),  '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'debtValue-currency',   'string',   SUBSTR(debt, -3, 3),    '0');

                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'accounts-chargeoff-support',    'string',    'true',        '0');
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'cards-chargeoff-support',    'string',    'false',    '0');

                FOR p IN (SELECT sp.ID, sp.EXTERNAL_ID, sp.CODE_SERVICE FROM SERVICE_PROVIDERS sp WHERE ((SELECT ef.VALUE FROM DOCUMENT_EXTENDED_FIELDS ef WHERE (bd.ID = ef.PAYMENT_ID) AND (ef.NAME = 'receiverId')) = SUBSTR(sp.EXTERNAL_ID, INSTR(sp.EXTERNAL_ID, '@') + 1))) LOOP
                    
                    UPDATE DOCUMENT_EXTENDED_FIELDS ef SET ef.VALUE = p.EXTERNAL_ID WHERE (ef.PAYMENT_ID = bd.ID) AND (ef.NAME = 'receiverId');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'recipient',    'string',    p.ID,              '0');
                    INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, bd.ID, 'codeService',  'string',    p.CODE_SERVICE,    '0');
                
                END LOOP;
                
                paymentType := '';
                
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
    orderIndex := 0;
    FOR r IN (SELECT * FROM RECEIVERS ORDER BY ID) LOOP
       
        SELECT S_BUSINESS_DOCUMENTS.NEXTVAL INTO paymentId FROM DUAL;
        -- не биллинговые получатели платежей
        IF (r.KIND != 'B')
        THEN
              
            INSERT INTO BUSINESS_DOCUMENTS VALUES (paymentId, '', r.LOGIN_ID, (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayment'), 
                CURRENT_DATE, '', 'TEMPLATE', 'Шаблон', '', (SELECT u.DEPARTMENT_ID FROM USERS u WHERE (u.LOGIN_ID = r.LOGIN_ID)), '', 
                '1', '0', 'H', CURRENT_DATE, '', '', r.RECEIVER_ACCOUNT, r.RECEIVER_NAME, '', '', '', '', 'PaymentStateMachine', '', '', '', '',
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
            INSERT INTO BUSINESS_DOCUMENTS VALUES (paymentId, '', r.LOGIN_ID, (SELECT pf.ID FROM PAYMENTFORMS pf WHERE pf.NAME = 'RurPayJurSB'), 
                CURRENT_DATE, '', 'TEMPLATE', 'Шаблон', '', (SELECT u.DEPARTMENT_ID FROM USERS u WHERE (u.LOGIN_ID = r.LOGIN_ID)), '', 
                '1', '0', 'P', CURRENT_DATE, '', '', r.RECEIVER_ACCOUNT, r.RECEIVER_NAME, '', '', '', '', 'PaymentStateMachine', '', '', '', '',
                CURRENT_DATE, '', (SELECT u.CLIENT_ID FROM USERS u WHERE u.LOGIN_ID = r.LOGIN_ID));
        
            --добавляем дополнительные поля бил. получателя платежа
            FOR field IN (SELECT * FROM FIELD_DESCRIPTIONS fd WHERE fd.RECIPIENT_ID = (SELECT sp.ID FROM SERVICE_PROVIDERS sp WHERE SUBSTR(sp.EXTERNAL_ID, INSTR(sp.EXTERNAL_ID, '@') + 1) = r.SERVICE_PROVIDER_KEY)) LOOP
                INSERT INTO DOCUMENT_EXTENDED_FIELDS VALUES (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentId, field.EXTERNAL_ID,     'string',  '',    '0');    
            END LOOP;
           
        
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

EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go
ALTER TABLE BUSINESS_DOCUMENTS DROP (TEMPLATE_NAME)
go
ALTER TABLE SERVICE_PROVIDERS MODIFY (CODE_SERVICE	VARCHAR2(20) NOT NULL)

