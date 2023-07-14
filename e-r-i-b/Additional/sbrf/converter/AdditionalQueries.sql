CREATE OR REPLACE PROCEDURE IKFL_SBRF.AdditionalQueries IS
    
    CURSOR get_any_accessschemes(c_category varchar2) IS SELECT * FROM ACCESSSCHEMES WHERE ACCESSSCHEMES.CATEGORY = c_category;
    CURSOR get_all_departments IS SELECT * FROM DEPARTMENTS ORDER BY ID;

    current_department_id NUMBER;
    new_department_id NUMBER;
    temp_office_id VARCHAR2(32 BYTE);
    current_osb VARCHAR2(4 BYTE);
    
        
BEGIN
    /** СЕРВИСЫ **/

    --при необходимости добавления сервисов, раскоментировать функции
    --добавление новых серисов пользователям
/**
    FOR ac IN get_any_accessschemes('C') LOOP
        
        --сервис RecallPayment 
        INSERT INTO 
            SCHEMESSERVICES (SCHEME_ID, SERVICE_ID) 
        VALUES 
            (ac.ID, (SELECT ID FROM SERVICES WHERE SERVICE_KEY = 'RecallPayment'));
        
        --сервис BlockingCardClaim   
        INSERT INTO 
            SCHEMESSERVICES (SCHEME_ID, SERVICE_ID) 
        VALUES 
            (ac.ID, (SELECT ID FROM SERVICES WHERE SERVICE_KEY = 'BlockingCardClaim'));    
           
    END LOOP;
   
    --добавление новых серисов сотрудникам банка

    FOR ac IN get_any_accessschemes('E') LOOP
        
        --сервис GorodManagement 
        INSERT INTO 
            SCHEMESSERVICES (SCHEME_ID, SERVICE_ID) 
        VALUES 
            (ac.ID, (SELECT ID FROM SERVICES WHERE SERVICE_KEY = 'GorodManagement'));
        
    END LOOP;
   
    --добавление новых серисов администраторам системы

    FOR ac IN get_any_accessschemes('A') LOOP
        
        --сервис ExternalSystemsManager 
        INSERT INTO 
            SCHEMESSERVICES (SCHEME_ID, SERVICE_ID) 
        VALUES 
            (ac.ID, (SELECT ID FROM SERVICES WHERE SERVICE_KEY = 'ExternalSystemsManager'));
        
    END LOOP;
**/
    

    /** ДЕПАРТАМЕНТЫ **/
    
    -- копируем подразделение уровня терабанк из таблицы OFFICES
    FOR o IN (SELECT * FROM OFFICES WHERE (OFFICES.OFFICE IS NULL) AND (OFFICES.TB = 40) AND OFFICES.OSB IS NULL ) LOOP
      
        INSERT INTO 
        DEPARTMENTS (ID, NAME, POST_ADDRESS, PHONE, KIND, OFFICE_ID, MAIN, TIME_ZONE, SERVICE)
        VALUES
               (S_DEPARTMENTS.NEXTVAL, o.NAME, o.ADDRESS, o.TELEPHONE, 'S', o.ID, '0', '0', '0');
                                             
    END LOOP;

    -- актуализация данных
    UPDATE DEPARTMENTS deps SET deps.TB        = (SELECT o.TB  from OFFICES o where deps.OFFICE_ID = o.ID);
    UPDATE DEPARTMENTS deps SET deps.OSB       = (SELECT o.OSB from OFFICES o where deps.OFFICE_ID = o.ID);
    UPDATE DEPARTMENTS deps SET deps.OFFICE    = (SELECT o.OFFICE from OFFICES o where deps.OFFICE_ID = o.ID);
    UPDATE DEPARTMENTS deps SET deps.BIC       = (SELECT o.BIC from OFFICES o where deps.OFFICE_ID = o.ID);
    UPDATE DEPARTMENTS deps SET deps.POSTINDEX = (SELECT o.POSTINDEX from OFFICES o where deps.OFFICE_ID = o.ID);
    UPDATE DEPARTMENTS deps SET deps.SBIDNT    = (SELECT o.SBIDN from OFFICES o where deps.OFFICE_ID = o.ID);
    UPDATE DEPARTMENTS deps SET deps.OFFICE_ID = LTRIM(OFFICE_ID);
    UPDATE DEPARTMENTS deps SET deps.OFFICE_ID = LTRIM(OFFICE_ID);
    
    -- помечаем, чтобы впоследствии вернуть
    UPDATE DEPARTMENTS deps SET deps.TEMP_OFFICE = 'X' WHERE deps.OSB = deps.OFFICE;
    -- для создания иерархии 
    UPDATE DEPARTMENTS deps SET deps.OFFICE = '' WHERE deps.OSB = deps.OFFICE;
    
    --создание иерархии подразделений
    --добавление новых ОСБ

    current_osb := '0';
    FOR dep IN (SELECT * FROM DEPARTMENTS ORDER BY OSB DESC, OFFICE DESC) LOOP
        
       IF (dep.OSB IS NOT NULL) AND (dep.OFFICE IS NULL)   
       THEN
       
           current_osb := dep.OSB;
                
       END IF;           
    
       IF (dep.OFFICE IS NOT NULL) AND (current_osb != dep.OSB)
       THEN
        
           SELECT S_DEPARTMENTS.NEXTVAL INTO new_department_id FROM DUAL; 
           
           INSERT INTO 
               DEPARTMENTS (ID, NAME, KIND, MAIN, TB, OSB, OFFICE, OFFICE_ID, TIME_ZONE, SERVICE)
           VALUES 
               (new_department_id, CONCAT('TEMP_DEPARTMENT_NAME_', new_department_id), 'S', '0', dep.TB, dep.OSB, '', CONCAT(' 40|', CONCAT(dep.OSB, '|       ')), '0','0');
           
           current_osb := dep.OSB;                
       
       END IF;  
     
    END LOOP;
 
    --простановка родителя для ВСП  
    current_osb := '0';
    current_department_id := '0';
    FOR dep IN (SELECT * FROM DEPARTMENTS ORDER BY OSB DESC, OFFICE DESC) LOOP
    
        IF (dep.OFFICE IS NULL) AND (dep.OSB IS NOT NULL)
        THEN
        
            current_osb := dep.OSB;
            current_department_id := dep.ID;
                
        END IF;
        
        UPDATE DEPARTMENTS d 
        SET d.PARENT_DEPARTMENT = current_department_id 
        WHERE (d.OFFICE IS NOT NULL) AND (d.OSB IS NOT NULL) AND (d.OSB = current_osb) AND (d.ID = dep.ID);
        
    END LOOP;

    --простановка родителя для OCB
    current_department_id := '0';
    FOR dep IN (SELECT * FROM DEPARTMENTS ORDER BY OSB DESC, OFFICE DESC) LOOP
    
        IF (dep.OFFICE IS NULL) AND (dep.OSB IS NULL)
        THEN
            current_department_id := dep.ID;    
        END IF;
           
        UPDATE DEPARTMENTS d SET d.PARENT_DEPARTMENT = current_department_id WHERE (d.OFFICE IS NULL) AND (d.OSB IS NOT NULL) AND (d.ID = dep.ID);       
     
    END LOOP;
    
    -- возвращаем назад значение поля OFFICE
    UPDATE DEPARTMENTS deps SET deps.OFFICE = deps.OFFICE WHERE deps.TEMP_OFFICE = 'X'; 

    -- добавление прав главному администратору на редактирование всех подразделений 
    FOR dep IN (SELECT * FROM DEPARTMENTS) LOOP
    
        INSERT INTO ALLOWED_DEPARTMENT (DEPARTMENT_ID, LOGIN_ID) VALUES (dep.ID, (SELECT l.ID FROM LOGINS l WHERE l.USER_ID ='admin'));
    
    END LOOP;

    
    -- добавление прав обычному администратору на редактирование подразделения, в котором он зарегистрирован 
    FOR emp IN (SELECT * FROM EMPLOYEES) LOOP
    
        INSERT INTO ALLOWED_DEPARTMENT (DEPARTMENT_ID, LOGIN_ID) VALUES ((SELECT dep.ID FROM DEPARTMENTS dep WHERE emp.DEPARTMENT_ID = dep.ID), emp.LOGIN_ID );
    
    END LOOP;
    
    -- все ВСП должны быть подключены к Сбербанк Онлайн
    UPDATE DEPARTMENTS SET SERVICE = '1' WHERE OFFICE IS NOT NULL;

    -- удаление таблицы OFFICES
    EXECUTE IMMEDIATE 'DROP TABLE OFFICES'; 
 
    /** ПЛАТЕЖИ **/

    --добавление дополнительных данных платежа в таблицу EXTENDED_FIELDS
    --ConvertCurrencyPayment
    FOR ccp IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE FORM_ID = 2) LOOP
    
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, ccp.ID, 'payer-account-currency', 'string', ccp.CURRENCY, '0');
            
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, ccp.ID, 'receiver-account-currency', 'string', (SELECT ef.VALUE from DOCUMENT_EXTENDED_FIELDS ef WHERE (ef.PAYMENT_ID = ccp.ID) AND (ef.NAME = 'buy-amount-currency')), '0');    
            
    END LOOP;
  
    --LossPassbookApplication
    FOR lpa IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE FORM_ID = 4) LOOP
    
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, lpa.ID, 'deposit-account', 'string', lpa.PAYER_ACCOUNT, '0');
            
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, lpa.ID, 'receiver-account', 'string', lpa.RECEIVER_ACCOUNT, '0');    
            
    END LOOP;
   
    --PurchaseCurrencyPayment
    FOR pcp IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE FORM_ID = 5) LOOP
    
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, pcp.ID, 'payer-account-currency', 'string', pcp.CURRENCY, '0');
            
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, pcp.ID, 'receiver-account-currency', 'string', (SELECT ef.VALUE from DOCUMENT_EXTENDED_FIELDS ef WHERE (ef.PAYMENT_ID = pcp.ID) AND (ef.NAME = 'buy-amount-currency')), '0');
                      
    END LOOP;
 
    --RurPayJurSB
    FOR rpj IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE FORM_ID = 6) LOOP
    
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rpj.ID, 'receiver-kpp', 'string', rpj.RECEIVER_KPP, '0');
        
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rpj.ID, 'receiver-inn', 'string', rpj.RECEIVER_INN, '0');
        
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rpj.ID, 'receiver-bank', 'string', rpj.RECEIVER_BANK_NAME, '0'); 
            
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rpj.ID, 'receiver-bic', 'string', rpj.RECEIVER_BIC, '0');
            
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rpj.ID, 'receiver-cor-account', 'string', rpj.RECEIVER_CORR_ACCOUNT, '0');
            
    END LOOP;
    
    --RurPayment
    FOR rp IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE FORM_ID = 7) LOOP
    
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rp.ID, 'payer-account-currency', 'string', rp.CURRENCY, '0');

        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rp.ID, 'receiver-bic', 'string', rp.RECEIVER_BIC, '0');
 
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rp.ID, 'receiver-cor-account', 'string', rp.RECEIVER_CORR_ACCOUNT, '0');
       
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, rp.ID, 'receiver-bank', 'string', rp.RECEIVER_BANK_NAME, '0');            
                      
    END LOOP;
    
    --SaleCurrencyPayment
    FOR scp IN (SELECT * FROM BUSINESS_DOCUMENTS WHERE FORM_ID = 8) LOOP
    
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, scp.ID, 'payer-account-currency', 'string', scp.CURRENCY, '0');
            
        INSERT INTO DOCUMENT_EXTENDED_FIELDS def 
            (def.ID, def.PAYMENT_ID, def.NAME, def.TYPE, def.VALUE, def.IS_CHANGED)
        VALUES 
            (S_DOCUMENT_EXTENDED_FIELDS.nextval, scp.ID, 'receiver-account-currency', 'string', (SELECT ef.VALUE from DOCUMENT_EXTENDED_FIELDS ef WHERE (ef.PAYMENT_ID = scp.ID) AND (ef.NAME = 'buy-amount-currency')), '0');
  
    END LOOP;

    --tbl BUSINESS_DOCUMENTS, BUSINESS_DOCUMENTS, CLAIMS 
    UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'REFUSED'    WHERE (STATE = 'E') AND (STATE_CATEGORY = 'F'); 
    UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'EXECUTED'   WHERE (STATE = 'S') AND (STATE_CATEGORY = 'F');
    UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'DISPATCHED' WHERE (STATE = 'W') AND (STATE_CATEGORY = 'I');
    UPDATE BUSINESS_DOCUMENTS SET STATE_CODE = 'SAVED'      WHERE (STATE = 'I') AND (STATE_CATEGORY = 'N');

    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (STATE)';
    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (STATE_CATEGORY)';

    UPDATE BUSINESS_DOCUMENTS SET BUSINESS_DOCUMENTS.EXTERNAL_OWNER_ID = (SELECT USERS.CLIENT_ID FROM USERS WHERE (BUSINESS_DOCUMENTS.LOGIN_ID = USERS.LOGIN_ID));
    UPDATE BUSINESS_DOCUMENTS SET BUSINESS_DOCUMENTS.STATE_MACHINE_NAME = 'PaymentStateMachine';
     
    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (RECEIVER_CORR_ACCOUNT)';
    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (RECEIVER_BIC)';
    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (RECEIVER_INN)';
    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (RECEIVER_KPP)';
    EXECUTE IMMEDIATE 'ALTER TABLE BUSINESS_DOCUMENTS DROP (RECEIVER_BANK_NAME)';
    
    UPDATE BUSINESS_DOCUMENTS SET EXTERNAL_OFFICE_ID = LTRIM(EXTERNAL_OFFICE_ID);
    UPDATE BUSINESS_DOCUMENTS SET EXTERNAL_OFFICE_ID = LTRIM(EXTERNAL_OFFICE_ID);

    /** ВНЕШНИЕ СИСТЕМЫ **/    

    --Создание внешней системы  
    INSERT INTO EXTERNAL_SYSTEMS
    (ID, SYSTEM_UID, NAME, URL, NUMBER_GOROD, PASSWORD_GOROD)
    VALUES
     (S_EXTERNAL_SYSTEMS.NEXTVAL, 'TEMP_UID', 'TEMP_NAME', 'TEMP_URL', 'TEMP_NUMBER_GOROD', 'TEMP_PASSWORD_GOROD'); 

    --Добавление ID внешней системы к полю OFFICE_ID таблицы DEPARTMENTS
    UPDATE DEPARTMENTS deps SET deps.OFFICE_ID = CONCAT(deps.OFFICE_ID, CONCAT('|', (SELECT es.ID FROM EXTERNAL_SYSTEMS es WHERE es.NAME = 'TEMP_NAME')));
    
    --Добавление ID внешней системы к полю EXTERNAL_OFFICE_ID таблицы BUSINESS_DOCUMENTS
    UPDATE BUSINESS_DOCUMENTS bd SET bd.EXTERNAL_OFFICE_ID = CONCAT(bd.EXTERNAL_OFFICE_ID, CONCAT('|', (SELECT es.ID FROM EXTERNAL_SYSTEMS es WHERE es.NAME = 'TEMP_NAME'))) WHERE bd.EXTERNAL_OFFICE_ID IS NOT NULL;

    --Добавление ID внешней системы к полю CLIENT_ID таблицы USERS    
    UPDATE USERS u SET u.CLIENT_ID = CONCAT(u.CLIENT_ID, CONCAT('|', (SELECT es.ID FROM EXTERNAL_SYSTEMS es WHERE es.NAME = 'TEMP_NAME'))); 

    --Добавление ID внешней системы к полю EXTERNAL_ID таблицы ACCOUNT_LINKS  
    UPDATE ACCOUNT_LINKS al SET al.EXTERNAL_ID = CONCAT(al.EXTERNAL_ID, CONCAT('|', (SELECT es.ID FROM EXTERNAL_SYSTEMS es WHERE es.NAME = 'TEMP_NAME')));  
 
    --Добавление ID внешней системы к полю EXTERNAL_ID таблицы CARD_LINKS   
    UPDATE CARD_LINKS cl SET cl.EXTERNAL_ID = CONCAT(cl.EXTERNAL_ID, CONCAT('|', (SELECT es.ID FROM EXTERNAL_SYSTEMS es WHERE es.NAME = 'TEMP_NAME')));         

    --Создание зависимостей между внешней системой и департаментами
    FOR dep IN get_all_departments LOOP
    
        INSERT INTO EXTERNAL_SYSTEMS_LINKS (ID, EXTERNAL_SYSTEM_ID, OFFICE_ID) values (S_EXTERNAL_SYSTEMS_LINKS.nextval, (SELECT es.ID FROM EXTERNAL_SYSTEMS es WHERE es.NAME = 'TEMP_NAME'), dep.OFFICE_ID); 
    
    END LOOP;

    -- удаляем временное поле
    EXECUTE IMMEDIATE 'ALTER TABLE DEPARTMENTS DROP COLUMN TEMP_OFFICE';

END;
/