CREATE OR REPLACE PACKAGE BODY IKFL_IMPORT AS

    PROCEDURE createDepartments
    IS
    BEGIN
            DECLARE                
                cursor DepartmentsCursor is select distinct OWNED_BRANCH, OFFICE from AG_RS;
            BEGIN
                for departmentsVar in DepartmentsCursor LOOP
                    createDepartment(departmentsVar.OWNED_BRANCH,departmentsVar.OFFICE);
                END LOOP;
            END;
    END createDepartments;
-------------------------------------------------
    PROCEDURE createDepartment(osbVar in VARCHAR2,officeVar in VARCHAR2)
     IS
    departmentId      Number;
    BEGIN
    INSERT INTO DEPARTMENTS(
            ID, NAME, BRANCH, DEPARTMENT, CITY, POST_ADDRESS, LOCATION, PHONE, WEEK_OPER_TIME_BEGIN, WEEK_OPER_TIME_END, 
            WEEKEND_OPER_TIME_BEGIN, WEEKEND_OPER_TIME_END, FRIDAY_OPER_TIME_BEGIN, FRIDAY_OPER_TIME_END, TIME_SCALE, 
            NOTIFY_CONRACT_CANCELATION, CONNECTION_CHARGE, MONTHLY_CHARGE, RECONNECTION_CHARGE, 
            TB, KIND, OFFICE_ID, MAIN) 
        VALUES(0, 'test', null, null, 'test', 'test', 'test', null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 'S',
        (SELECT ID FROM OFFICES where OSB=osbVar and OFFICE = officeVar) , 0)
       RETURNING ID INTO departmentId;

    INSERT INTO REPRESENTATIVES(LIST_INDEX, DEPARTMENT_ID, FIRST_NAME, 
                            SUR_NAME, PATR_NAME, POSITION, DOCUMENT) 
        VALUES(0,departmentId, 'test', 'test', 'test', 'test', 'test');
    commit;

    END createDepartment;
----------------------------------------------
    PROCEDURE importClient(id IN VARCHAR2)
    IS
        pinId Number;
        departmentId Number;
        loginId      Number;
        addrId1      Number;
        addrId2      Number;
        agreementNumber VARCHAR2(16);
        clientLogin VARCHAR2(20);
        clientId VARCHAR2(32);
        dataRow AG_RS%ROWTYPE;      
    BEGIN       
        SELECT * into dataRow from AG_RS where DEPO_NO_1=id;
        
        select ID into departmentId from departments where 
        OFFICE_ID=(select id from offices where OFFICE = dataRow.OFFICE and OSB = dataRow.OWNED_BRANCH);   

        INSERT INTO 
        LOGINS(ID, KIND, WRONG_LOGONS, DELETED, USER_ID, PIN_ENVELOPE_ID) 
        (SELECT S_LOGINS.nextval,'C', 0, 0, USER_ID,ID 
        FROM PINENVELOPES where state = 'U' and 2>rownum and not TEST_CLIENT_ID is null and DEPARTMENT_ID=departmentId );
        
        select max(id) into loginId from LOGINS;

        select USER_ID into clientLogin from LOGINS where id=loginId;    

        select PIN_ENVELOPE_ID into pinId from LOGINS where ID=loginId;    

        UPDATE PINENVELOPES 
            SET STATE='P'
            WHERE  id = pinID;


        INSERT INTO PASSWORDS(ID, KIND, LOGIN_ID, VALUE, ACTIVE, ISSUE_DATE, EXPIRE_DATE, NEED_CHANGE) 
            VALUES(S_PASSWORDS.nextval, 'pu', 
                    loginId, 
                    (select VALUE from PINENVELOPES pins where pins.ID=pinId), 
                    1, 
                    (Select Current_date from dual), To_Date('2011-10-10','YYYY-MM-DD'),0);

        INSERT INTO 
        AUTHENTICATION_MODES(ID, LOGIN_ID, ACCESS_TYPE, PROPERTIES, MODE_KEY) 
        VALUES(
                (S_AUTHENTICATION_MODES.nextval), 
                loginId, 
                'simple', 
                (select PROPERTIES from AUTHENTICATION_MODES 
                    where (not PROPERTIES is null) and ACCESS_TYPE = 'simple' and rownum<2), 
                null
        );

        INSERT INTO SCHEMEOWNS(ID, SCHEME_ID, LOGIN_ID, ACCESS_TYPE) 
            (Select S_SCHEMEOWNS.nextval, schem.*, loginId, 'simple' 
             from (SELECT id FROM ACCESSSCHEMES where SCHEME_KEY='fullAccess-user') schem);
             
        INSERT INTO ACCOUNT_LINKS(ID, EXTERNAL_ID, PAYMENT_ABILITY, LOGIN_ID, ACCOUNT_NUMBER) 
            VALUES(S_ACCOUNT_LINKS.nextval, dataRow.DEPO_NO_1, '1', loginId, dataRow.DEPO_NO_1);

        INSERT INTO ACCOUNT_LINKS(ID, EXTERNAL_ID, PAYMENT_ABILITY, LOGIN_ID, ACCOUNT_NUMBER) 
            VALUES(S_ACCOUNT_LINKS.nextval, dataRow.DEPO_NO_2, '0', loginId, dataRow.DEPO_NO_2);
            
        INSERT INTO RECEIVERS(ID, LOGIN_ID, CURRENCY, RECEIVER_ACCOUNT, BANK_CODE, RECEIVER_INN, RECEIVER_KPP, 
            RECEIVER_NAME, RECEIVER_CORR_ACCOUNT, DESCRIPTION, KIND, ALIAS, UNIQUE_NUMBER, STATUS, GROUND, BANK_NAME, 
            CODE_TYPE, OFFICE_KEY, COMMISSION) 
            VALUES(S_RECEIVERS.nextval, loginId,null, 
                    '42301810662531872536', '043601607', null, null, 'ûגאûג', 
                    '30101810200000000607', 'ûגאûג', 'S', null, null, null, null, 
                    'ûגאûג', null, ' 67|1792|     11', 0);

        INSERT INTO RECEIVERS(ID, LOGIN_ID, CURRENCY, RECEIVER_ACCOUNT, BANK_CODE, RECEIVER_INN, RECEIVER_KPP, 
            RECEIVER_NAME, RECEIVER_CORR_ACCOUNT, DESCRIPTION, KIND, ALIAS, UNIQUE_NUMBER, STATUS, GROUND, BANK_NAME, 
            CODE_TYPE, OFFICE_KEY, COMMISSION) 
            VALUES(S_RECEIVERS.nextval, loginId,null, 
                    '42301810662531872536', '043601607', '3259874939', null, 'ûגאûגאûג', 
                    '30101810200000000607', 'ûגאûג', 'G', 'ûגאûג', null, null, null, 
                    'ûגאûגאûג', null, null, 0);

        INSERT INTO ADDRESS(ID, POSTALCODE, PROVINCE, DISTRICT, CITY, STREET, HOUSE, BUILDING, FLAT) 
            VALUES(S_ADDRESS.nextval, '123456', 'ûגאûג', 'ûגאûג', 'ûגאûג', 'ûגאûג', '123', '123', '123')
        RETURNING ID INTO addrId1; 

        INSERT INTO ADDRESS(ID, POSTALCODE, PROVINCE, DISTRICT, CITY, STREET, HOUSE, BUILDING, FLAT) 
            VALUES(S_ADDRESS.nextval, '123456', 'ûגאûג', 'ûגאûג', 'ûגאûג', 'ûגאûג', '123', '123', '123')
        RETURNING ID INTO addrId2; 


        INSERT INTO USERS(ID, CLIENT_ID, LOGIN_ID, STATUS, FIRST_NAME, SUR_NAME, PATR_NAME, BIRTHDAY, 
            BIRTH_PLACE, REGISTRATION_ADDRESS, RESIDENCE_ADDRESS, 
            MESSAGE_SERVICE, E_MAIL, HOME_PHONE, JOB_PHONE, MOBILE_PHONE, MOBILE_OPERATOR, 
            AGREEMENT_NUMBER, AGREEMENT_DATE, 
            INSERTION_OF_SERVICE, GENDER, 
            TRUSTING_ID, CITIZEN_RF, INN, PROLONGATION_REJECTION_DATE, 
            STATE, DEPARTMENT_ID, REPRESENTATIVE_ID, CONTRACT_CANCELLATION_COUSE, SECRET_WORD, 
            RESIDENTAL, SMS_FORMAT) 
            VALUES(S_USERS.nextval, 
                    (select pins.TEST_CLIENT_ID 
                        from PINENVELOPES pins,LOGINS logins 
                        where logins.ID=loginId and pins.ID= logins.PIN_ENVELOPE_ID), 
                     loginId, 
                    'A', dataRow.FIRSTNAME, dataRow.SURNAME,dataRow.SECONDNAME,TRUNC(dataRow.CLIENT_BIRTHDATE,'DD'), null, 
                    addrId1, 
                    addrId2, 
                    null, null, null, null, '89091234567', 'beeline', 
                    (select pins.TEST_AGREEMENT_NUMBER from PINENVELOPES pins,LOGINS logins where logins.ID=loginId and pins.ID= logins.PIN_ENVELOPE_ID), 
                    (SELECT TRUNC(CURRENT_DATE,'DD') from dual), 
                    (SELECT TRUNC(CURRENT_DATE,'DD') from dual), substr(dataRow.CLIENT_GENDER,0,1), 
                    null, dataRow.CLIENT_CITIZEN, null, (SELECT CURRENT_DATE FROM dual), 
                    'A', departmentId, 0, null, '123', '1', 0)
                    returning AGREEMENT_NUMBER, CLIENT_ID into agreementNumber, clientId;

        INSERT INTO DOCUMENTS(ID, DOC_TYPE, DOC_NAME, DOC_NUMBER, DOC_SERIES, 
                    DOC_ISSUE_DATE, DOC_ISSUE_BY, DOC_ISSUE_BY_CODE, DOC_MAIN, PERSON_ID) 
        (Select S_DOCUMENTS.nextval, dataRow.IC_TYPE, '', dataRow.IC_NUMBER, dataRow.IC_SERIES, 
         TRUNC(dataRow.IC_DATE_OF_ISSUE,'DD'), dataRow.IC_AUTHORITY,'' , '0', S_USERS.currval from dual);
         
         UPDATE AG_RS SET AGREEMENT_NUMBER = agreementNumber, CLIENT_ID = clientId, CLIENT_LOGIN = clientLogin
         where DEPO_NO_1=dataRow.DEPO_NO_1; 
                    
    commit;
    END importClient;
    
----------------------------------------------

    PROCEDURE importAllClient
    IS  
        clientId VARCHAR2(20);                     
    BEGIN                   
        DECLARE                
            cursor ClientsCursor is select DEPO_NO_1 from AG_RS;
            BEGIN
                for clients in ClientsCursor LOOP
                    importClient(clients.DEPO_NO_1);
                END LOOP;
            END;
    END importAllClient;
        
END IKFL_IMPORT;
/