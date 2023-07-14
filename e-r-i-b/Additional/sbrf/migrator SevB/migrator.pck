CREATE OR REPLACE PACKAGE Migrator is

    -- ������� ������ �������� �� ������� converter_set (���� ������� �� ������) 
    -- ������ �������� �� �������� ����������� �������
    procedure DeleteClients;

    -- ������� ������ �������� �� ������� converter_set (���� ���������� �� ������)
    -- ������ ����������� �� �������� ����������� �������
    procedure DeleteEmployees;

    -- ��������� ���� �������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������
    procedure MigrateClients(flag in Char); 

    -- ��������� ���� ����������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������
    procedure MigrateEmployees(flag in Char); 
    
end Migrator;
/
CREATE OR REPLACE PACKAGE BODY Migrator is

    -- ��������� �.�.�. �� �������, ��� � ��������
    procedure getFIO (fullName in varchar2, firstName out varchar2, patrName out varchar2, surName out varchar2)
    is
        str varchar2(128):='';  -- ������ ��� ������� ��� �������
        i number :=0;
        j number :=0;
    begin
        -- ��������� �.�.�.
        firstName :='';
        patrName :='';
        surName :='';
        i := instr(fullName,' ');
        j := instr(fullName,' ', i+1);
        if (i > '0') then
            surName := substr(fullName, 1, i-1);
            if (i < j) then
                -- �������� 3 �����
                firstName := substr(fullName, i+1, j-i-1);
                patrName := substr(fullName, j+1, length(fullName)-i);
            else
                -- �������� 2 �����, ��������� ����� ����������� ����� ������� - "."
                firstName := substr(fullName,i+1, length(fullName)-i);
                j := instr(firstName,'.');
                if (j > 0) then
                    str := firstName;
                    firstName := substr(str, 1,j);
                    patrName := substr(str, j+1, length(str)-j);
                end if;
            end if;
        else
          -- �������� ������ ���� �����
          surName := fullName;
        end if;
    end getFIO;

    -- ������� ���������� ����� ��� ����� c ���������� ������������ ','
    function getAmount(amount in varchar2) return number is
    begin
        if (amount is not null and length(amount) > 0) then
            begin
                return to_number(amount,'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''');
            exception
                when OTHERS then return null;
            end;
        end if;
        return null;
    end;

    procedure ImportClient(SEVB_ID in varchar2, setNumber in number) is
        -- ��������� ��� ��������� ����������
        schemeClient number;               -- c���� ������� ��� ������� (ID �����)   (������ ����� �� ��������-���������� �����) ��� �������������� �����(ID=90344)
        adapter varchar2(64);              -- ������� (������������)
        regionID number;                   -- ������ � ������� ������� (��� �������� �����)
        defaultDepartment number;          -- ������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
        schemeEmployer number;             -- ����� ����������, ��� �������� ��������������� ��������
        suffix varchar2(3);                -- ������� ��� ������, ��� ����������
        billingID number;                  -- ID ���������� ������� ��� �������� (id IQW)
        -- ��������� ���������� ��� ���������� �������� ������
        loginID number :=0;                             -- ������� ID ������ �������
        loginIDSevB number :=0;                         -- ������� ID ������ ������� � ���� ���. �����
        temp number :=0;                                -- ���������� ��� �������� � �������
        departmentID number :=0;                        -- ��� �������� id �������� ������������
        adressRegId number:=0;                          -- id ������ �����������
        adressResId number:=0;                          -- id ������ ����������
        clientID number:=0;                             -- id �������
        paymentID number:=0;                            -- id �������, ��� �������� ���. �����
        paymentFormID number :=0;                       -- id ����� �������
        tempValue varchar2 (64) := '';                  -- ��� ����������� �������� � ��� ����� �������
        operationType char(1):='';                      -- ��� �������, ��� ���������� ������������ ����� ������ ��������
        kind char(1):='';                               -- ��� ��������� (P-������ T-������ �������)
        clientState char(1):='';
        templateOrderID number :=0;
        codeService  varchar2(32) := '';
        signatureID number;
        login  varchar2(32):='';                        -- ����� �������
        pinNumber number;                               -- ����� ��� ��������
        firstName varchar2(32):='';                     -- ��� ����������
        patrName varchar2(32):='';                      -- �������� ����������
        surName varchar2(64):='';                       -- ������� ����������
        migrationFlagStatus integer;                    -- ������ ����� ��������
        fromResourceCurrency varchar2(3);               -- ������ ����� ��������
        toResourceCurrency varchar2(3);                 -- ������ ����� ����������
        debitAmount number;                             -- ����� � ������ ����� ��������
        creditAmount number;                            -- ����� � ������ ����� ����������
        paymentStateDescription varchar2 (128) :='';    -- �������� ������� �������
        isGorod char(1):='';                            -- ������� ������� �� ������
        curentConvertObject varchar2 (128) :='';        -- ������������ ������� �������������� �������� (��� ����)
        CurentClient varchar2(32);                      -- ������� ������������� ������ (ID � ������� ��������������� ��������)
        otherObjectError varchar2 (128) :='';           -- ������������ ������� �������������� �������� (��� ����)
        STARTTIME timestamp;                            -- ����� ������ ������� �������
        OraError varchar2(1024);                        -- �������� ������ oracle
    begin
        savepoint start_client_migration;
        curentConvertObject := '���������� ���������� ����������';
        otherObjectError:='';
        STARTTIME := systimestamp;
        select  S_CONVERTER_CLIENTS.NEXTVAL into CurentClient from dual;

        select "scheme_Client", "adapter", "suffix", "region_id", "default_Department", "scheme_Employer","billing_id" into
               schemeClient, adapter, suffix, regionID, defaultDepartment, schemeEmployer,billingID
        from CONVERTER_CONFIG where "id"='1';

        curentConvertObject := '������� ������ �� ������� �� ���� ���. �����'||SEVB_ID;
        -- ��������������� ��������
        for C in ( select
                        ID,
                        CLIENT_ID,
                        LOGIN_ID,
                        STATUS,
                        FIRST_NAME,
                        SUR_NAME,
                        PATR_NAME,
                        BIRTHDAY,
                        BIRTH_PLACE,
                        REGISTRATION_ADDRESS,
                        RESIDENCE_ADDRESS,
                        MESSAGE_SERVICE,
                        E_MAIL,
                        HOME_PHONE,
                        JOB_PHONE,
                        MOBILE_PHONE,
                        MOBILE_OPERATOR,
                        AGREEMENT_NUMBER,
                        AGREEMENT_DATE,
                        INSERTION_OF_SERVICE,
                        GENDER,
                        TRUSTING_ID,
                        CITIZEN_RF,
                        INN,
                        PROLONGATION_REJECTION_DATE,
                        STATE,
                        DEPARTMENT_ID,
                        CONTRACT_CANCELLATION_COUSE,
                        SECRET_WORD,
                        RESIDENTAL,
                        SMS_FORMAT,
                        DISPLAY_CLIENT_ID,
                        CLIENT_ID_NEW,
                        CLIENT_ID_OLD,
                        USE_OFERT
                from USERS@sevbLink where ID = SEVB_ID)
        loop
            select S_CONVERTER_CLIENTS.NEXTVAL into CurentClient from dual;
            loginIDSevB := C.LOGIN_ID;
            clientState:= C.STATE;

            curentConvertObject := '�������� � �������������';
            begin
                for D in ( select TB, OSB, OFFICE from DEPARTMENTS@sevbLink where ID = C.DEPARTMENT_ID )
                loop
                    -- ������� �������������, � �������� �������� ������ �� ����� ��, ��� � ���
                    if (D.OFFICE is not null) then
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and OFFICE = D.OFFICE;
                    else
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and (OFFICE is null or OFFICE = '');
                    end if;
                end loop;
            exception
                when NO_DATA_FOUND then
                    departmentID := defaultDepartment;
                    otherObjectError := '�� ������� �������������, ������������ �������� � ����������';
            end;

            curentConvertObject := '���������� ������ �������';
            for L in ( select USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID from LOGINS@sevbLink where ID = loginIDSevB )
            loop
                if (L.USER_ID is null) then
                    raise_application_error(-20001, '����������� ����� �������');
                end if;
                login := 'SB' || L.USER_ID;
                select S_LOGINS.NEXTVAL into loginID from dual;
                insert into LOGINS values (loginID, login, L.KIND, L.WRONG_LOGONS, L.DELETED, null, null, null, '', '', '', '');
            end loop;

            curentConvertObject := '���������� �������';
            select  S_ADDRESS.NEXTVAL into adressRegId from dual;
            insert into ADDRESS select adressRegId, POSTALCODE, PROVINCE, DISTRICT, CITY, STREET, HOUSE, BUILDING, FLAT, '' from ADDRESS@sevbLink where ID = C.REGISTRATION_ADDRESS;
            select  S_ADDRESS.NEXTVAL into adressResId  from dual;
            insert into ADDRESS select adressResId, POSTALCODE, PROVINCE, DISTRICT, CITY, STREET, HOUSE, BUILDING, FLAT, '' from ADDRESS@sevbLink where ID = C.RESIDENCE_ADDRESS;

            curentConvertObject := '�������� ���������� ����������';
            -- �������� ���������� �������
            for BL in ( select REASON_TYPE, REASON_DESCRIPTION, DATE_FROM, DATE_UNTIL, BLOCKER_ID from LOGIN_BLOCK@sevbLink where LOGIN_ID = loginIDSevB )
            loop
                if ( BL.BLOCKER_ID is not null ) then
                    curentConvertObject := '����� ���������� ��� ���������� �������';
                    -- ���� ���������� ��������������� �������, ���� �� �����, �� ������ � exception
                    select ID into temp from EMPLOYEES where LOGIN_ID = ( select ID from LOGINS where USER_ID = ( select USER_ID from LOGINS@sevbLink where id = ( select LOGIN_ID from EMPLOYEES@sevbLink where ID = BL.BLOCKER_ID ) ) );
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, temp);
                else
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, null);
                end if;
            end loop;

            if ( clientState!='D' ) then -- ��� �������� �������� ����� �� �����������
              curentConvertObject := '��������� �����';
              for AC in ( select PAYMENT_ABILITY, ACCOUNT_NUMBER, IS_CHANGED from ACCOUNT_LINKS@sevbLink where ( LOGIN_ID = loginIDSevB ) )
              loop
                  tempValue := substr(AC.ACCOUNT_NUMBER,1,3);
                  -- ��������� � ������������� ����� �� ���������
                  if ( tempValue='423' or tempValue='426') then
                      insert into ACCOUNT_LINKS values ( S_ACCOUNT_LINKS.NEXTVAL, AC.ACCOUNT_NUMBER ||'|'|| adapter, AC.PAYMENT_ABILITY, loginID, AC.ACCOUNT_NUMBER, AC.IS_CHANGED, ' ' , '1', '1' );
                  end if;
              end loop;
            end if;

            curentConvertObject := '��������� �������';
            select  S_USERS.NEXTVAL into clientID from dual;
            insert into USERS values (clientID, login||'|'|| adapter, loginID, C.STATUS, C.FIRST_NAME, C.SUR_NAME, C.PATR_NAME, C.BIRTHDAY, C.BIRTH_PLACE, adressRegId, adressResId, C.MESSAGE_SERVICE, C.E_MAIL, C.HOME_PHONE, C.JOB_PHONE, C.MOBILE_PHONE, C.MOBILE_OPERATOR, C.AGREEMENT_NUMBER, C.AGREEMENT_DATE, C.INSERTION_OF_SERVICE, C.GENDER, C.TRUSTING_ID, C.CITIZEN_RF,  C.INN, C.PROLONGATION_REJECTION_DATE, C.STATE, departmentID, C.CONTRACT_CANCELLATION_COUSE, C.SECRET_WORD, C.RESIDENTAL, C.SMS_FORMAT, login , C.USE_OFERT, '', 'SBOL', '', '', sysdate, '');

            curentConvertObject := '��������� ��������� �������';
            for D in ( select DOC_TYPE, DOC_NAME, DOC_NUMBER, DOC_SERIES, DOC_ISSUE_DATE, DOC_ISSUE_BY, DOC_ISSUE_BY_CODE, DOC_MAIN, PERSON_ID, DOC_TIME_UP_DATE, DOC_IDENTIFY from DOCUMENTS@sevbLink where PERSON_ID = C.ID )
            loop
                insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, D.DOC_TYPE, D.DOC_NAME, D.DOC_NUMBER, D.DOC_SERIES, D.DOC_ISSUE_DATE, D.DOC_ISSUE_BY, D.DOC_ISSUE_BY_CODE, D.DOC_MAIN, clientID, D.DOC_TIME_UP_DATE, D.DOC_IDENTIFY);
            end loop;

        end loop;

        curentConvertObject := '������ ����� ���� ��� �������';
        insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeClient, loginID, 'simple');

        curentConvertObject := '��������� ��������� ��������������';
        -- ��������� ��������� �� ���� ������������ � ������ ������������� ��������
        insert into AUTHENTICATION_MODES select S_AUTHENTICATION_MODES.NEXTVAL, loginID, ACCESS_TYPE, PROPERTIES, MODE_KEY from AUTHENTICATION_MODES@sevbLink where LOGIN_ID = loginIDSevB;

        curentConvertObject := '��������� ������� �������';
        insert into PROFILE values (loginID, S_PROFILE.NEXTVAL, regionID, '1');

        curentConvertObject := '��������� XML ������������� �������';
        insert into XMLPERSONREPRESENTATION values (S_XMLPERSONREPRESENTATION.NEXTVAL, null, clientID);

        curentConvertObject := '��������� ������';
        insert into PASSWORDS select S_PASSWORDS.NEXTVAL, KIND, loginID, VALUE, ACTIVE, ISSUE_DATE, EXPIRE_DATE, NEED_CHANGE from PASSWORDS@sevbLink where LOGIN_ID = loginIDSevB;

        curentConvertObject := '��������� ���-�������'; --(��� ���� ������ ��������� ������ �������)
        select  S_PINENVELOPES.NEXTVAL into pinNumber from dual;

        insert into PINENVELOPES values (pinNumber, 1, login, 'U', login, departmentID);

        update LOGINS set PIN_ENVELOPE_ID=pinNumber where ID=loginID;

        curentConvertObject := '��������� ������� ��������';
        for P in ( select
                        ID,
                        GROUND,
                        LOGIN_ID,
                        FORM_ID,
                        CHANGED,
                        SIGNATURE_ID,
                        CURRENCY,
                        DEPARTMENT_ID,
                        DOC_NUMBER,
                        CREATION_TYPE,
                        ARCHIVE,
                        KIND,
                        CREATION_DATE,
                        COMMISSION,
                        PAYER_NAME,
                        RECEIVER_ACCOUNT,
                        RECEIVER_NAME,
                        AMOUNT,
                        TEMPLATE_NAME,
                        EXTERNAL_ID,
                        EXTERNAL_OFFICE_ID,
                        STATE_DESCRIPTION,
                        PAYER_ACCOUNT,
                        OPERATION_DATE,
                        ADMISSION_DATE,
                        EXECUTION_DATE,
                        DOCUMENT_DATE,
                        REFUSING_REASON,
                        EXTERNAL_OWNER_ID,
                        STATE_CODE,
                        STATE_MACHINE_NAME
                   from BUSINESS_DOCUMENTS@sevbLink where LOGIN_ID = loginIDSevB )
        loop
            curentConvertObject:='��������� ������� �������';
            if ( P.SIGNATURE_ID is not null ) then
                select S_SIGNATURES.NEXTVAL into signatureID from dual;
                insert into SIGNATURES select signatureID, SIGNATURE_TEXT, OPERATION_ID, SESSION_ID, CHECK_DATE from SIGNATURES@sevbLink where ID=P.SIGNATURE_ID;
            else
                signatureID:=null;
            end if;
/*
        ������� �������� �������������� �� ID ����� �� ��������� �����. ������� ����� �������� ��������� ���������� ���������
    ������������ ID ���� �������� � ���� ��������� ����� � ���������.

                                           ����� �������� ��������� �����
                        1	AccountClosingClaim	        ������ �� �������� ����� �� ������      (-)
                        2	AccountOpeningClaim	        ������ �� �������� ����� �� ������      (-)
                        4	ConvertCurrencyPayment	    �������� ������
                        3	BlockingCardClaim           ���������� �����                        (-)
                        5	InternalPayment	            ������� ����� �������
                        8	RurPayJurSB	                �������� ������ ��� ������
                        6	LossPassbookApplication	    ������� �� ����� �������������� ������  (-)
                        7	PurchaseCurrencyPayment	    ������� ����������� ������
                        9	SaleCurrencyPayment	        ������� ����������� ������
                        10	RecallPayment	            ����� ��������                          (-)
                        11	RurPayment                  ��������� ������

                                           ����� �������� ����
                        1	BlockingCardClaim           ���������� �����
                        2	ConvertCurrencyPayment      ����� �����
                        3	InternalPayment             ������� ����� ����� �������
                        4	LossPassbookApplication     ������� �� ����� �������������� ������
                        5	RurPayJurSB                 ������ �����
                        6	RecallPayment               ����� ��������
                        7	RurPayment                  ��������� ������
                        8	TaxPayment                  ������ �������
                        9   LoanPayment                 ��������� �������
                        10	RefuseLongOffer             ������ ���������� ����������� �������
*/
            curentConvertObject:='�������� ������� �������';
            kind := 'P';
            case P.STATE_CODE
                when 'INITIAL' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '�� �������, �� ��� �� ��������� ��������');
                when 'SAVED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '�� ���������, �� ��� �� ����������� ������.');
                when 'DELAYED_DISPATCH' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '��������� �������� ��������� �� ��������� � ����.');
                when 'DISPATCHED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '������ ����������� � ������� �� ��������� � ����.');
                when 'SUCCESSED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '������ ������� �������� ������: �������� �������� ���������� �� ���� ���������� �������.');
                when 'PARTLY_EXECUTED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '������ ����������� � ������� �� ��������� � ����.');
                when 'EXECUTED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '������ ������� �������� ������: �������� �������� ���������� �� ���� ���������� �������.');
                when 'SEND' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '������ ����������� � ������� �� ��������� � ����.');
                when 'SAVED_TEMPLATE' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '�� ����������� �������� � �������� ��������.');
                when 'TEMPLATE' then
                    paymentStateDescription := NVL(P.STATE_DESCRIPTION, '�� ��������� �������� � �������� ��������.');
                    kind := 'T';
                    templateOrderID := templateOrderID + 1;
                when 'REFUSED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '��� �������� � ���������� �������� �� �����-���� �������.');
                when 'RECALLED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '�������� ������� ���� �� �����-���� �������.');
                when 'ERROR' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, '������ �� ����� ���� ��������, ��� ���������� ���������� � ����.');
                else
                    raise_application_error(-20001, '������ ������� �� ��������� ���. �'||P.DOC_NUMBER);
            end case;

            operationType := '';	-- ��� �������� ����
            curentConvertObject:='���������� ������� '||P.DOC_NUMBER;
/*
                        4	ConvertCurrencyPayment	    �������� ������
                        7	PurchaseCurrencyPayment	    ������� ����������� ������
                        9	SaleCurrencyPayment	        ������� ����������� ������
*/
            if ( P.FORM_ID in ('4', '7', '9')) then -- ��������� ����� ������ �������/������� (����� �����) E
                select ID into paymentFormID from PAYMENTFORMS where NAME = 'ConvertCurrencyPayment';
                operationType:= 'E';

                curentConvertObject:='���������� �������� ����� ������� '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, '', departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, null, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, '', 0, '0', 0);

                curentConvertObject:='������ ���. ����� ������� '||P.DOC_NUMBER;
                for PEF in ( select "NAME", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                loop
                    case PEF."NAME"
                        when 'buy-amount' then creditAmount := getAmount(PEF."VALUE");  -- ����� ���������� ������
--                          when 'buy-amount-currency' then ;
                        when 'course' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'convertion-rate' ,'decimal', PEF."VALUE", PEF.IS_CHANGED);
--                          when 'is-purchase' then ;
                        when 'operation-code' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'operation-code' ,'string', PEF."VALUE", PEF.IS_CHANGED);
                        when 'payer-account-currency' then fromResourceCurrency := PEF."VALUE";
                        when 'payer-account-type' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource', 'string',  P.PAYER_ACCOUNT||' '||PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-account-name' ,'string', PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-account-type' ,'string', PEF."VALUE", '0');
                       when 'recalculated-amount-changed' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'is-sum-modify' ,'boolean', PEF."VALUE", PEF.IS_CHANGED);
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'recalculated-amount-changed' ,'boolean', PEF."VALUE", PEF.IS_CHANGED);
                        when 'receiver-account-currency' then toResourceCurrency := PEF."VALUE";
                        when 'receiver-account-type' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-account-name' ,'string', PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-account-type' ,'string', PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource' ,'string', P.RECEIVER_ACCOUNT||' '||PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-type' ,'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                        when 'sellAmount' then debitAmount := getAmount(PEF."VALUE");  -- ����� ����������� ������
                        when 'type' then
                            if ( PEF."VALUE" = '2' ) then
                                -- ������ �������� � ���� ����� ����������
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', 'destination-field-exact', '0');
                            else
                                -- ������ �������� � ���� ����� ��������
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', 'charge-off-field-exact', '0');
                            end if;
                        else null ;
                    end case;
                end loop;

                curentConvertObject:='���������� �������� ����� ������� '||P.DOC_NUMBER;
                update BUSINESS_DOCUMENTS set CURRENCY=fromResourceCurrency, AMOUNT=debitAmount, DESTINATION_AMOUNT=creditAmount, DESTINATION_CURRENCY=toResourceCurrency where ID=paymentID;

                curentConvertObject:='���������� �������������� ����� ������� '||P.DOC_NUMBER;
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-currency' ,'string', fromResourceCurrency, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-buy-rate-from' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-buy-rate-to' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-sale-rate-from' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-sale-rate-to' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-buy-rate-from' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-buy-rate-to' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-sale-rate-from' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-sale-rate-to' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-currency' ,'string', toResourceCurrency, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-end-date' ,'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-event-type' ,'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-pay-day' ,'long', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-percent' ,'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-priority' ,'long', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-start-date' ,'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-sum-type' ,'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'national-currency' ,'string', '', '0');

                if (kind='T') then
                    curentConvertObject:='������� �������';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
/*
                        5	InternalPayment	            ������� ����� �������
*/
            elsif (P.FORM_ID = '5') then -- ��������� ����� ������ �������	E
                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                operationType:= 'E';

                curentConvertObject:='���������� �������� ����� ������� '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, P.CURRENCY, departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, P.AMOUNT, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='������ ���. ����� ������� '||P.DOC_NUMBER;
                for PEF in ( select "NAME", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                loop
                    case PEF."NAME"
                        when 'from-account-type' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  P.PAYER_ACCOUNT||' '||PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', P.CURRENCY, '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."VALUE", '0');
                        when 'to-account-type'  then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource', 'string', P.RECEIVER_ACCOUNT||' '||PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', P.CURRENCY, '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-type', 'string', PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-name', 'string', PEF."VALUE", '0');
                        else null;
                    end case;
                end loop;

                curentConvertObject:='���������� �������������� ����� ������� '||P.DOC_NUMBER;
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');

                if (kind='T') then
                    curentConvertObject:='������� �������';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
/*
                        11	RurPayment	                ��������� ������
*/
            elsif (P.FORM_ID = '11') then -- ��������� ������ H
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';
                temp :=0;

                curentConvertObject:='���������� �������� ����� ������� '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, P.CURRENCY, departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, P.AMOUNT, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                for PEF in ( select "NAME", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                loop
                    case PEF."NAME"
                        when 'payer-account-type' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', P.PAYER_ACCOUNT||' '||PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."VALUE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."VALUE", '0');
                        when 'receiver-cor-account' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', PEF."VALUE", '0');
                        when 'receiver-bic' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', PEF."VALUE", '0');
                        when 'receiver-bank' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."VALUE", '0');
                        when 'bank' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."VALUE", '0');
                        when 'is-our-bank' then
                            temp := 1;
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', NVL(PEF."VALUE", 'true'), '0');
                        when 'operation-code' then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."VALUE", '0');
                        else null;
                    end case;
                end loop;

                if ( temp='0' ) then
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                end if;
                getFIO(P.RECEIVER_NAME, firstName, patrName, surName);

                curentConvertObject:='������� �������������� ����� �������';
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', P.CURRENCY, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', P.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', firstName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', patrName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', surName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'charge-off-field-exact', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');

                if (kind='T') then
                    curentConvertObject:='������� �������';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
/*
                        8	RurPayJurSB	                �������� ������ ��� ������
*/
            elsif (P.FORM_ID = '8') then -- ������ ����� P

                select "VALUE" into tempValue from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID and "NAME"='appointment';
                if ( tempValue = 'gorod') then -- ������ � ������ ���������� "�����"
                    isGorod:='1';
                    select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                    operationType:= 'P';
                else
                    isGorod:='0';
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';
                    temp :=0;
                end if;

                curentConvertObject:='���������� �������� ����� ������� '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, P.CURRENCY, departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, P.AMOUNT, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='������ ���. ����� ������� '||P.DOC_NUMBER;
                if ( isGorod = '1' ) then
                    for PEF in ( select "NAME", "TYPE", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                    loop
                        case PEF."NAME"
                            when 'register-string' then null;
                            when 'register-number' then null;
                            when 'receiverId' then
                                curentConvertObject:='����� ���������� ����� '||PEF."VALUE";
                                select SERVICE_ID into codeService from CONVERTER_SERVICES where ABONENT_ID = REGEXP_SUBSTR( PEF."VALUE", '\d{5,}');
                                codeService := 'Service-'||codeService;
                                select ID, EXTERNAL_ID into temp, tempValue from SERVICE_PROVIDERS where BILLING_ID = billingID and CODE = codeService;
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', tempValue, '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', temp, '0');
                            when 'receiverBIC' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', PEF."VALUE", '0');
                            when 'receiver-pay-type' then null;
                            when 'receiver-alias' then null;
                            when 'receivPayType' then null;
                            when 'payment-type' then null;
                            when 'payerAccountSelectType' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', P.PAYER_ACCOUNT||' '||PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', P.PAYER_ACCOUNT||' '||PEF."VALUE", '0');
                            when 'payerAccountSelect' then null;
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', PEF."VALUE", '0');
                            when 'payerAccount' then null;
                            when 'payer-rec-account' then null;
                            when 'payer-account-type' then null;
                            when 'lastPayDate' then null;
                            when 'ground' then null;
                            when 'fine' then null;
                            when 'debt' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', P.CURRENCY, '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', PEF."VALUE", '0');
                            when 'auxiliaryAmount' then null;
                            when 'appointment' then null;
                            when 'amountCurrency' then null;
                            when 'amount' then null;
                            else insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, PEF."NAME", PEF."TYPE", PEF."VALUE", PEF."IS_CHANGED");
                        end case;
                    end loop;

                    curentConvertObject:='���������� �������������� ����� ������� '||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', P.CURRENCY, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_�HECK', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount', 'string', P.AMOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountCurrency', 'string', P.CURRENCY, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountInternal', 'string', P.AMOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'authorizeCode', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'codeService', 'string', codeService, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'nameService', 'string', '', '0');-- TODO
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'phoneNumber', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver', 'string', '', '0');

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'true', '0');
                else

                    for PEF in ( select "NAME", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                    loop
                        case PEF."NAME"
                            when 'receiver-kpp' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', PEF."VALUE", '0');
                            when 'receiver-inn' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."VALUE", '0');
                            when 'receiver-cor-account' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', PEF."VALUE", '0');
                            when 'receiver-bic' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', PEF."VALUE", '0');
                            when 'receiver-bank' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."VALUE", '0');
                            when 'payerAccountSelectType' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', P.PAYER_ACCOUNT||' '||PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."VALUE", '0');
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."VALUE", '0');
                            when 'operation-code' then
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."VALUE", '0');
                            else null;
                        end case;
                    end loop;

                    curentConvertObject:='������� �������������� ����� �������';
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', P.CURRENCY, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', P.RECEIVER_NAME, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', P.RECEIVER_ACCOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'jur', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'charge-off-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');
                end if;

                if (kind='T') then
                    curentConvertObject:='������� �������';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
            end if;

        end loop; -- �������

        -- ����������� ������� �������� ��������
        migrationFlagStatus:= migration.EndClientMigration@sevbLink(loginIDSevB,'100');
        case migrationFlagStatus
            when '-2' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL');
            when '-1' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ��� ������� � ��������� ���������������');
            when  '1' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ������ �� � ��������� "�������� ������"');
            when  '2' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: �� ������� �������� ��������� � ����� ����� ��������');
            else null;
        end case;
        -- ������ ��� ��������� ���������
        insert into CONVERTER_CLIENTS values (CurentClient, loginIDSevB, loginID , login, STARTTIME, systimestamp, setNumber,'O','������ ������������ �������, OTHER:'||otherObjectError,'','');
        commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback to start_client_migration;
            OraError:=SQLERRM;
            migrationFlagStatus := migration.EndClientMigration@sevbLink(loginIDSevB,'-1');
            case migrationFlagStatus
                when '-2' then otherObjectError := otherObjectError + ' ������: ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL';
                when '-1' then otherObjectError := otherObjectError + ' ������: ��� ������� � ��������� ���������������';
                when '1' then otherObjectError := otherObjectError + ' ������: ������ �� � ��������� "�������� ������"';
                when '2' then otherObjectError := otherObjectError + ' ������: �� ������� �������� ��������� � ����� ����� ��������';
                else null;
            end case;
            insert into CONVERTER_CLIENTS values (CurentClient, SEVB_ID, null , login, STARTTIME, systimestamp, setNumber,'E','������ ��� ������� ������� SEVB_ID: '|| SEVB_ID ||', ���: '||curentConvertObject||', OTHER:'||otherObjectError||', ERROR: '||OraError,'','');
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� ������� ������� SEVB_ID: '|| SEVB_ID ||', ���: '||curentConvertObject||', ERROR: '||OraError);
            commit;
    end ImportClient;

    procedure ImportEmployee(SEVB_ID in varchar2, setNumber in number) is
        -- ��������� ��� ��������� ����������
        suffix varchar2(3);                     -- ������� ��� ������ �����������, ��� ����������
        schemeEmployer number;                  -- ����� ������� ��� ���������� (ID �����)
        adapter varchar2(64);                   -- ������� (������������)
        defaultDepartment number;               -- �������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)

        -- ��������� ���������� ��� ���������� �������� ������
        loginID number :=0;                             -- ������� ID ������ ����������/�������
        temp number :=0;                                -- ���������� ��� �������� � �������
        temp_reserv number :=0;                         -- ���������� ��� �������� � �������
        departmentID number :=0;                        -- ��� �������� id �������� ������������
        authChoice varchar2 (5) :='';                   -- ������ ����� � �������
        confirm�hoice varchar2 (10) :='smsp';           -- ������ ������������� �������� TODO ������ ����� ���� ��������:1
        curentConvertObject varchar2 (128) :='';		-- ������������ ������� �������������� �������� (��� ����)
        otherObjectError varchar2 (128) :='';			-- ������������ ������� �������������� �������� (��� ����)

        emplFirstName varchar2(32) :='';
        emplPatrName varchar2(32) :='';
        emplSurName varchar2(64) :='';
        STARTTIME timestamp;                -- ����� ������ ������� ����������
        OraError varchar2(1024);            -- ��� � �������� ������ oracle
        CurentEmployer number;              -- id � ������ ����������������� �����������
        loginIDSevB number :=0;             -- ������� ID ������ ���������� � ���� ���. �����
        idSBOL varchar2(32);                -- id ���������� � ����
        login  varchar2(32);                -- �����
        emplPassword varchar2(32);          -- ������

    begin
        -- ��������������� �����������
        -- ��������� ���������
        curentConvertObject := '���������� �������� ����������';
        select "suffix", "adapter", "default_Department" into
                suffix,  adapter, defaultDepartment
        from CONVERTER_CONFIG where "id"='1';

        STARTTIME := systimestamp;
        select S_CONVERTER_EMPLOYEES.NEXTVAL into CurentEmployer from dual;
        otherObjectError:='';

        curentConvertObject := '������� ������ �� ���������� �� ���� ����';
        for EMP in ( select ID,
                            LOGIN_ID,
                            FIRST_NAME,
                            SUR_NAME,
                            PATR_NAME,
                            INFO,
                            E_MAIL,
                            MOBILE_PHONE,
                            DEPARTMENT_ID,
                            LOAN_OFFICE_ID,
                            SMS_FORMAT
                     from EMPLOYEES@sevbLink where ID = SEVB_ID )
        loop
            loginIDSevB:= EMP.LOGIN_ID;

            -- �������� �������������� ����������
            curentConvertObject := '���������� ������';
            for L in ( select USER_ID, KIND, WRONG_LOGONS, DELETED from LOGINS@sevbLink where ID = loginIDSevB )
            loop
                login := L.USER_ID;
                select S_LOGINS.NEXTVAL into loginID from dual;
                insert into LOGINS values (loginID, login, L.KIND, L.WRONG_LOGONS, L.DELETED, null, null, null, '', '', '','');
            end loop;

            curentConvertObject := '�������� ���������� ����������';
            -- �������� ���������� �������
            for BL in ( select REASON_TYPE, REASON_DESCRIPTION, DATE_FROM, DATE_UNTIL, BLOCKER_ID from LOGIN_BLOCK@sevbLink where LOGIN_ID = loginIDSevB )
            loop
                if ( BL.BLOCKER_ID is not null ) then
                    curentConvertObject := '����� ���������� ��� ���������� �������';
                    -- ���� ���������� ��������������� �������, ���� �� �����, �� ������ � exception
                    select ID into temp from EMPLOYEES where LOGIN_ID = ( select ID from LOGINS where USER_ID = ( select USER_ID from LOGINS@sevbLink where id = ( select LOGIN_ID from EMPLOYEES@sevbLink where ID = BL.BLOCKER_ID ) ) );
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, temp);
                else
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, null);
                end if;
            end loop;

            curentConvertObject := '�������� � �������������';
            begin
                for D in ( select TB, OSB, OFFICE from DEPARTMENTS@sevbLink where ID = EMP.DEPARTMENT_ID )
                loop
                    -- ������� �������������, � �������� �������� ��������� �� ����� ��, ��� � ���
                    if (D.OFFICE is not null) then
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and OFFICE = D.OFFICE;
                    else
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and (OFFICE is null or OFFICE = '');
                    end if;
                end loop;
            exception
                when NO_DATA_FOUND then
                    departmentID := defaultDepartment;
                    otherObjectError := '�� ������� �������������, ������������ �������� � ����������';
            end;

            curentConvertObject := '���������� ����������';
            insert into EMPLOYEES values (S_EMPLOYEES.NEXTVAL, loginID, EMP.FIRST_NAME, EMP.SUR_NAME, EMP.PATR_NAME, EMP.INFO, EMP.E_MAIL, EMP.MOBILE_PHONE, departmentID, null, EMP.SMS_FORMAT);

            curentConvertObject := '�������� ���������� � ����� ����';
            select ID_SCHEME_ERIB into schemeEmployer from CONVERTER_ACCESSSCHEMES where ID_SCHEME_SBOL = ( select SCHEME_ID from SCHEMEOWNS@sevbLink where LOGIN_ID = loginIDSevB );
            insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeEmployer, loginID, 'employee');
            curentConvertObject := '��������� �������� ������������';
            -- ����������� �������� ������������
            insert into AUTHENTICATION_MODES values(S_AUTHENTICATION_MODES.NEXTVAL, loginID, 'employee', null, null);

            curentConvertObject := '���������� ������';
            -- ��������� ������
            insert into PASSWORDS select S_PASSWORDS.NEXTVAL, KIND, loginID, VALUE, ACTIVE, ISSUE_DATE, EXPIRE_DATE, NEED_CHANGE from PASSWORDS@sevbLink where LOGIN_ID = loginIDSevB;
            curentConvertObject := '';
        end loop;
        -- ����������� ������ � �������� ����������� ���, ��������� ���������
        insert into CONVERTER_EMPLOYEES values (CurentEmployer, loginIDSevB, loginID , login, emplPassword, STARTTIME, systimestamp, setNumber,'O','��������� ������������ �������, OTHER:'||otherObjectError,'','');
        commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
            OraError:=SQLERRM;
            insert into CONVERTER_EMPLOYEES values (CurentEmployer, loginIDSevB, null, login, emplPassword, STARTTIME, systimestamp, setNumber,'E','������ ��� ������� ���������� Login: '|| login ||' ���: '||curentConvertObject||' OTHER:'||otherObjectError||' ERROR: '||OraError,'','');
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� ������� ���������� SEVB_ID: '|| SEVB_ID ||' Login: '|| login ||', ���: '||curentConvertObject||', ERROR: '||OraError);
            commit;
    end ImportEmployee;

    --  ��������� ������� ���� �������� �� ������ �� ��������,
    --  ������ �� �������� ��������� ������� � ������� converter_set, ����������� ���� �����
    procedure DeleteClients is

        loginID number :=0;                             -- ������� ID ������ �������
        clientID number :=0;                            -- ID �������
        adressRegId number:=0;                          -- id ������ �����������
        adressResId number:=0;                          -- id ������ ����������
        curentConvertObject varchar2 (128) :='';        -- ������������ ������� �������������� �������� (��� ����)
        STARTTIME timestamp;                    -- ����� ������ ������� ����������
        OraError varchar2(1024);                -- �������� ������ oracle
        idSBOL varchar2(32);                    -- id ������� � ����
        login  varchar2(32);                    -- ����� �������
        migrateStatus  integer;                 -- ������ ������������ �������
    begin
        -- �������� ��������
        for REC IN ( select ID_SBOL, LOGIN from CONVERTER_SET )
        loop
            begin 
                STARTTIME := systimestamp;
                curentConvertObject := '����� �������';
                loginID:=to_number(REC.LOGIN);
                select ID into clientID  from USERS where LOGIN_ID = loginID;
                select USER_ID into login from LOGINS where ID = loginID;

                for P in (select ID from BUSINESS_DOCUMENTS where LOGIN_ID = loginID)
                loop
                    curentConvertObject := '������� ������� ������� '||P.ID;
                    delete from BUSINESS_DOCUMENTS_RES where BUSINESS_DOCUMENT_ID = P.ID;
                    curentConvertObject := '������� ���. ���� ������� '||P.ID;
                    delete from DOCUMENT_EXTENDED_FIELDS where PAYMENT_ID = P.ID;
                    curentConvertObject := '������� ������ '||P.ID;
                    delete from BUSINESS_DOCUMENTS where ID = P.ID;
                end loop;

                curentConvertObject := '������� �����';
                delete from ACCOUNT_LINKS where LOGIN_ID = loginID;
                
                for CL in ( select "ID" from CARD_LINKS where LOGIN_ID = loginID )
                loop
                  curentConvertObject := '������� ������������ �������';
                  delete from SENDED_ABSTRACTS where CARDLINK_ID = CL.ID;
                  curentConvertObject := '������� �����';
                  delete from CARD_LINKS where "ID" = CL.ID;
                end loop;

                for M in ( select ID from MAIL where SENDER_ID=loginID )
                loop
                  curentConvertObject := '������� ���������� ������';
                  delete from RECIPIENTS where MAIL_ID=M.ID;

                  curentConvertObject := '������� ������';
                  delete from MAIL where ID=M.ID;
                end loop;
                
                curentConvertObject := '������� ������� ��� ���������� �����';
                delete from MB_PAYMENT_TEMPLATE_UPDATES where LOGIN_ID=loginID;
                
                curentConvertObject := '������� �������� � ���';
                update LOGINS SET PIN_ENVELOPE_ID=null where ID=loginID;

                curentConvertObject := '������� ��� �������';
                delete PINENVELOPES where USER_ID = login;

                curentConvertObject := '������� ������';
                delete from PASSWORDS  where LOGIN_ID = loginID;

                curentConvertObject := '������� XML ������������� �������';
                delete from XMLPERSONREPRESENTATION where USER_ID = login;

                curentConvertObject := '������� ������� �������';
                delete from PROFILE where LOGIN_ID = loginID;

                curentConvertObject := '������� ��������� ����� � ������������� ��������';
                delete from AUTHENTICATION_MODES where LOGIN_ID = loginID;

                curentConvertObject := '������� �������� � ����� ���� ��� �������';
                delete from SCHEMEOWNS where LOGIN_ID = loginID;

                curentConvertObject := '������� ��������� �������';
                delete from DOCUMENTS where PERSON_ID = clientID;

                curentConvertObject := '������� ��������� �������';
                delete from DOCUMENTS where PERSON_ID = clientID;

                curentConvertObject := '������� ������ �������';
                select REGISTRATION_ADDRESS,RESIDENCE_ADDRESS into adressRegId,adressResId from USERS where ID = clientID;
                update USERS set REGISTRATION_ADDRESS = null, RESIDENCE_ADDRESS = null, LOGIN_ID = null where LOGIN_ID = loginID;
                delete from ADDRESS where ID = adressRegId;
                delete from ADDRESS where ID = adressResId;

                curentConvertObject := '������� �������';
                delete from USERS where ID = clientID;

                curentConvertObject := '������� SMS ������ �������';
                delete from SMSPASSWORDS where LOGIN_ID = loginID;

                curentConvertObject := '������� �������� �� ��������';
                delete from PERSONAL_SUBSCRIPTION_DATA where LOGIN_ID = loginID;

                curentConvertObject := '������� ����������� ��������';
                delete from RECEIVERS where LOGIN_ID = loginID;

                curentConvertObject := '������� ��������� ������� �������� ����';
                delete from MENU_LINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� ��������� ��������';
                delete from FAVOURITE_LINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� �����';
                delete from DEPO_ACCOUNT_LINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� ����� �� ������������ �����';
                delete from IMACCOUNT_LINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� ����� �� �������';
                delete from LOAN_LINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� ����� �� ���������� ���������';
                delete from LONG_OFFER_LINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� �������� �������';
                delete from SUBSCRIPTIONS where LOGIN_ID = loginID;

                curentConvertObject := '������� ���������� ������';
                delete from LOGIN_BLOCK where LOGIN_ID = loginID;

                curentConvertObject := '������� ����������';
                delete from NOTIFICATIONS where LOGIN = login;

                curentConvertObject := '������� �����';
                delete from LOGINS where ID = loginID;

                -- ������� ����������� ������ � ����
                delete from CONVERTER_CLIENTS where ID_ERIB = REC.ID_SBOL;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, '������ login: '||login||' ������.');
                commit;
             exception when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, '������ ��� �������� ������� login: '||login||', ���: '||curentConvertObject||', ERROR: '||OraError);
                commit;
             end;
          end loop;
          -- ��� ������ �� ������ ����������, ������ ���
          delete from CONVERTER_set;
          commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, '������ ��� �������� ��������, ��������� �������� ����������� ERROR: '||OraError||', ��������� ������ ��������� ������ �������� �� ��������');
            commit;
    end DeleteClients;

    --  ��������� ������� ���� ����������� �� ������ �� ��������,
    --  ������ �� �������� ��������� ������� � ������� converter_set, ����������� ���� �����
    procedure DeleteEmployees is

        curentConvertObject varchar2 (128) :='';		-- ������������ ������� �������������� �������� (��� ����)
        loginID number :=0;                             -- ������� ID ������ ����������
        STARTTIME timestamp;                            -- ����� ������ �������� ����������
        OraError varchar2(1024);                        -- ��� � �������� ������ ��� ������ ���������
        login  varchar2(32);                            -- ����� ����������

    begin
        for REC in ( select LOGIN from CONVERTER_SET )
        loop
            begin
                STARTTIME := systimestamp;
                loginID:=to_number(REC.LOGIN);
                select USER_ID into login from LOGINS where ID = loginID;

                curentConvertObject := '�������� ������';
                delete from PASSWORDS where LOGIN_ID = loginID;

                curentConvertObject := '�������� �������� ������������';
                delete from AUTHENTICATION_MODES where LOGIN_ID = loginID;

                curentConvertObject := '���������� ���������� �� ����� ����';
                delete from SCHEMEOWNS where LOGIN_ID = loginID;

                curentConvertObject := '�������� ����������';
                delete from EMPLOYEES where LOGIN_ID = loginID;

                curentConvertObject := '������� ���������� � ������';
                delete from LOGIN_BLOCK	where LOGIN_ID = loginID;

                curentConvertObject := '�������� ������ ����������';
                delete from LOGINS where ID = loginID;

                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, '��������� login: '||login||' ������ �������');
                commit;
            exception
                when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� �������� ������ ���������� login: '||loginID||', ���: '||curentConvertObject||',ERROR: '||OraError);
                commit;
            end;
        end loop;
        delete from CONVERTER_SET;
        commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� �������� �����������, ��������� ����������� ERROR: '||OraError||'. ��������� ������ ��������� ������ ��� ��������.');
            commit;
    end DeleteEmployees;

    -- ��������� ���� �������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������ ��������
    procedure MigrateClients(flag in Char) is

        convertDelay number;                    -- �������� ����� �������������
        countForConverting number;              -- �� ������� �������� �������������� (������)

        countRec number :=0;                  	-- ���-�� ������� � ������
        OraError varchar2(1024);                -- �������� ������ oracle
        isStop char(1);                         -- ������� ������ ��������� ����������
        setNumber number;                       -- ����� ������� ������
        migrateStatus  integer;                 -- ������ ������������ �������
        paymentStatus number;                   -- ������ ��������, � - ����� �������������, ��� ������� �� � ���������; P - ������� � ���������, ������������� ������
        minId number;
        maxId number;
        idSevb varchar2(32);
        loginID varchar2(32);
    begin
        if (flag = '1') then
            -- ��������� ����� ��� ����������� (��� ������� ����)
            delete from CONVERTER_SET;
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, "ID", "LOGIN_ID" from USERS@sevbLink where STATE != 'S';
        end if;

        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, '������ �������� ����.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ������ �������� ��� ��������, ���-�� �������� = '||(maxId-minId+1));

        -- ��������� ��������� ����������
        select "convert_Delay" ,"count_For_Converting", "stop" into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where "id"='1';

        -- ���������� ����� ������
        select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
        for ind in minId..maxId
            loop
                select ID_SBOL, LOGIN into idSevb, loginID from CONVERTER_SET where ID = ind;
                migrateStatus := migration.StartClientMigration@sevbLink(to_number(loginID));
                if ( migrateStatus = 0) then
                    ImportClient(idSevb, setNumber);
                    countRec := countRec + 1;
                else
                    case migrateStatus
                        when '-1' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' ������ ��� ������������ ����� ��������: ��� ������� � ��������� ���������������');
                        when '1'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' ������ ��� ������������ ����� ��������: �������� ��� ������');
                        when '2'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' ��� ������� ������� �������� ��� ��������� �������');
                        when '3'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' ������ ��� ������������ ����� ��������: � ������� ���� �������� ������');
                        when '4'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' ������ ��� ������������ ����� ��������: ������ ��� ��������� MIG_STATE � MIG_TIME');
                        when '5'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' ������ ��� ������������ ����� ��������: �������� ��������, � �������� �������� �������� ������');
                        else insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� ������������ ����� ��������: '||migrateStatus);
                    end case;
                end if;

                -- ���� ��������������� ������, ���������� � ������ �����
                if (countRec >= countForConverting) then
                    countRec := 0;
                    -- ����������� ������� ������
                    select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
                    if (convertDelay != 0) then
                        dbms_lock.sleep(convertDelay);
                    end if;
                end if;
                -- ���� ��������� ���� ���������, �� ����������� ���������������
                select "stop" into isStop from CONVERTER_CONFIG where "id"='1';
                if ( isStop = '1') then
                    EXIT;
                end if;
            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'��������� ��� ���������� �������, ��� ������� ������� id '||idSevb);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'��������������� ���������');
            end if;
            -- ������ ������ �������� �� �����������
            delete from CONVERTER_SET;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� ������� ��������, ��������� ����������� ����������� ERROR: '||OraError);
            commit;
    end MigrateClients;

    -- ��������� ���� ����������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������ �����������
    procedure MigrateEmployees(flag in Char)  is

        convertDelay number;                    -- �������� ����� �������������
        countForConverting number;              -- �� ������� �������� �������������� (������)
        countRec number :=0;                  	-- ���-�� ������� � ������
        OraError varchar2(1024);				-- �������� ������ oracle
        isStop char(1);                         -- ������� ������ ��������� ����������
        setNumber number;                       -- ����� ������� ������
        minId number;
        maxId number;
        idSevb number;
    begin
        if (flag = '1') then
            -- ��������� ����� ��� ����������� (��� ���������� ���. �����)
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, ID, LOGIN_ID from EMPLOYEES@sevbLink;
        end if;
        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, '������ ����������� ����.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ������ ���������� ��� ��������, ���-�� ����������� = '||(maxId-minId+1));
        -- ��������� ��������� ����������
        select "convert_Delay" ,"count_For_Converting", "stop" into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where "id"='1';
        -- ���������� ����� ������
        select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
        commit;
        for ind IN minId..maxId
            loop
                select ID_SBOL into idSevb from CONVERTER_SET where ID = ind;
                ImportEmployee(idSevb, setNumber);
                countRec := countRec + 1;
                -- ���� ��������������� ������, ���������� � ������ �����
                if (countRec >= countForConverting) then
                    countRec := 0;
                    -- ����������� ������� ������
                    select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
                    if (convertDelay != 0) then
                        dbms_lock.sleep(convertDelay);
                    end if;
                end if;
                -- ���� ��������� ���� ���������, �� ����������� ���������������
                select "stop" into isStop from CONVERTER_CONFIG where "id"='1';
                if ( isStop = '1') then
                    EXIT;
                end if;
            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'��������� ��� ���������� �������, ����� ������� ���������� id:'||idSevb);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'��������������� ���������');
            end if;
            -- ������ ������ ����������� �� �����������
            delete from CONVERTER_SET;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'������ ��� ������� �����������, ��������� ����������� ����������� ERROR: '||OraError);
            commit;
    end MigrateEmployees;

end Migrator;
/