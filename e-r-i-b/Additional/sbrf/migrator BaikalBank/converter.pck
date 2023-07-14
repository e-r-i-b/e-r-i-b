/*
**   ��������� ��� �������� �������� � ����������� ������������ �����, ���� ���� (������ 1.18 ������ 20690)
*/
CREATE OR REPLACE PACKAGE Converter is

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
    
    -- ��������� ���-�� ������, ���������� � �������� �������
    procedure getClientObjectsCount(clientID in number, accounts out number, documents out number, templates out number);
    
    -- ������� ��������� ����������������� ������
    function getEmplPassword (emplPassword out varchar2, passwordHesh out varchar2) return char;

    -- ������� ��������� ���� ������ ����������
    function getPasswordHash ( m varchar2 ) return varchar2;

    -- ������� ������� ������, ��������������� ����������� ������������ �����
    function getPassword return varchar2;
    
    -- ������� ��� ��������� ����� ���. ���� ��������� ������ ������������� �� �������, �� ����� ���������         
    function getCreditFieldName(detailName in varchar2) return varchar2;

end Converter;
/
CREATE OR REPLACE PACKAGE BODY Converter is

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

    -- ������� ���������� ����� ��� ����� c ��������� ������������ ','
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

    function getAccountCurrencyCod(accountNumber in varchar2) return varchar2 is
    begin
        if (accountNumber is not null and length(accountNumber)>0) then
            return case substr(accountNumber,6,3)
                when '810' then 'RUR'-- ���������� ����� 
                when '840' then 'USD'-- ������ ���
                when '978' then 'EUR'-- ����
                when '826' then 'GBP'-- ���� ����������
                when '756' then 'CHF'-- ����������� �����
                when '752' then 'SEK'-- �������� �����
                when '702' then 'SGD'-- ������������ ������
                when '578' then 'NOK'-- ���������� �����
                when '392' then 'JPY'-- ����
                when '208' then 'DKK'-- ������� �����
                when '124' then 'CAD'-- ��������� ������
                when '036' then 'AUD'-- ������������� ������
                else ''
            end;        
        end if;
        return '';
    end getAccountCurrencyCod;

    -- ������� ��������� ���� ������ ����������
    function getPasswordHash ( m varchar2 ) return varchar2 as language java name 'crypto.passwordHash(java.lang.String) return java.lang.String';

    -- ������� ������� ������, ��������������� ����������� ������������ �����
    function getPassword return varchar2 as language java name 'utils.generate() return java.lang.String';

    -- ������� ��������� ����������������� ������
    --  � ������ ������ ��� ��������� ������, ��� ��� ��������� ���� - ������ ���������� ORA-20001
    function getEmplPassword (emplPassword out varchar2, passwordHesh out varchar2) return char is
    begin
        emplPassword := getPassword;
        if ( emplPassword = 'E' ) then
            -- �� ������ ��������� ������, ������� ��������� ��� ���
            dbms_lock.sleep(0.005); --  ����� ��� ����� ���������� �������, ����� ������� �� �� ������������������ ��� � � ���������� ���������
            emplPassword := getPassword;
            if ( emplPassword = 'E' ) then
                -- ��������� ��������� �� �� ���� ����������, ������� ����������
                raise_application_error(-20001, '�� ������ ������������� ������');
            else
                -- �������� ��� ��� ������
                passwordHesh := getPasswordHash(emplPassword);
                if ( passwordHesh = 'E' ) then
                    -- �� ������ �������� ���, ������� ��� ���
                    passwordHesh := getPasswordHash(emplPassword);
                    if ( passwordHesh = 'E' ) then
                        -- ��������� ������� �� �� ���� ����������, ������� ����������: �� ������ �������� ��� ��� ������
                        raise_application_error(-20001, '�� ������ �������� ��� ��� ������');
                    end if;                     
                end if;
            end if;
        else
            -- �������� ��� ��� ������
            passwordHesh := getPasswordHash(emplPassword);
            if ( passwordHesh = 'E' ) then
                -- �� ������ �������� ���, ������� ��� ���
                passwordHesh := getPasswordHash(emplPassword);
                if ( passwordHesh = 'E' ) then
                    -- ��������� ������� �� �� ���� ����������, ������� ����������: �� ������ �������� ��� ��� ������
                    raise_application_error(-20001, '�� ������ �������� ��� ��� ������');
                end if;                
            end if;
        end if;

        return 'O';

    end getEmplPassword;
    
    function validateDocuments(documentType in varchar2, documentSeries in varchar2, documentNumber in varchar2) return char is
    begin
        case documentType
            when 'REGULAR_PASSPORT_RF' then
                -- ����� ���������������� �������� �� ������ �������� �� 4 ����, � ����� �� 6.
                if ( ( regexp_instr(documentSeries, '^\d{2}\s{0,}\d{2}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{6}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'SEAMEN_PASSPORT' then
                -- ����� �������� ������ ������� �������� �� 2 ���� (��,��,��), � ����� �� 7 ����.");
                if (( regexp_instr(documentSeries, '^[�-�]{2}$') = '1' ) and ( documentSeries = '��' or documentSeries='��' or documentSeries = '��' ) and ( regexp_instr(documentNumber, '^\d{7}$') = '1')) then
                    return '1';
                else
                    return '0';
                end if;
            when 'RESIDENTIAL_PERMIT_RF' then
                -- ����� ���� �� ���������� �� ������ �������� �� 2 ����, � ����� �� 7.
                if ( regexp_instr(documentSeries, '^\d{2}$') = '1' and regexp_instr(documentNumber,'^\d{7}$') = '1' ) then
                    return '1';
                else
                    return '0';
                end if;
            else return '1';
        end case;
    end validateDocuments;

    -- ������� �������� ������ �������� ������� �� ������������ �������
    function validatePhonNumber(phoneNumber in varchar2) return char is
    begin
        -- ������ ��� ������ ��������: +7 (���_���������) �����_��������; 
        -- ����� ���� ��������� 3-5 ����, ����� ������ �������� - 5-7 ����, ����� ���� � ������, � ������ +7, ������ ���� ����� 11, c����� �����������
        if ( regexp_instr(phoneNumber, '^\+7( )?\(((\d{3}\)( )?\d{7})|(\d{4}\)( )?\d{6})|(\d{5}\)( )?\d{5}))$') = '1' ) then
            return '1';
        else
            return '0';
        end if;
    end validatePhonNumber;

    -- ������� ��� ���������� ������ �������� ������ � ������� ������������� � �������
    function buildPhonNumber(phoneNumber in varchar2) return varchar2 is
        temp varchar2(32);
    begin
        select regexp_replace(phoneNumber, '\D','') into temp from dual;
        select regexp_replace(temp, '^[78]','') into temp from dual;
        select regexp_replace(temp, '(\d{3})(\d{1,})','+7(\1)\2') into temp from dual;
        return temp;    
    end;

    -- ������� ��� ��������� ����� ���. ���� ��������� ������ ������������� �� �������, �� ����� ���������     
    function getCreditFieldName(detailName in varchar2) return varchar2 is
    begin
        return case lower(detailName)
            when 'bodydebt' then 'principal-amount'                        -- ����� ��������� �����
            when 'procent' then 'interests-amount'                         -- ����� ������ �� ���������
            when 'advancebody' then 'earlyBaseDebtAmount'                  -- ��������, � ���� ��������� �����
            when 'overduebody' then 'delayedDebtAmount'                    -- ������������ �������� ����
            when 'overdueprocent' then 'delayedPercentsAmount'             -- ������������ �������� �� ����������� ��������
            when 'accountpay' then 'accountOperationsAmount'               -- ����� �� �������� �� �������� �����
            when 'overduebodyforfeit' then 'penaltyDelayDebtAmount'        -- ��������� ��������� ����� (��������� �� ��������� ��������� �����)
            when 'overdueprocentforfeit' then 'penaltyDelayPercentAmount'  -- ��������� ��������� (��������� �� �������. %%)
        end;
    end;

    procedure ImportClient(SBOL_ID in varchar2, setNumber in number) is
        -- ��������� ��� ��������� ����������
        schemeClient number;               -- c���� ������� ��� ������� (ID �����)   (������ ����� �� ��������-���������� �����) ��� �������������� �����(ID=90344)
        adapter varchar2(64);              -- ������� (������������)
        regionID number;                   -- ������ � ������� ������� (��� �������� �����)
        defaultDepartment number;          -- ������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
        schemeEmployer number;             -- ����� ����������, ��� �������� ��������������� ��������
        suffix varchar2(3);                -- ������� ��� ������, ��� ����������  
        billingID number;                 -- ID ���������� ������� ��� �������� (id IQW)
        -- ��������� ���������� ��� ���������� �������� ������
        loginID number :=0;                             -- ������� ID ������ ����������/�������
        temp number :=0;                                -- ���������� ��� �������� � �������
        temp_reserv number :=0;                         -- ���������� ��� �������� � �������
        str varchar2(128):='';	
        departmentID number :=0;                        -- ��� �������� id �������� ������������
        adressRegId number:=0;                          -- id ������ �����������
        adressResId number:=0;                          -- id ������ ����������
        clientID number:=0;                             -- id �������
        paymentID number:=0;                            -- id �������, ��� �������� ���. �����
        paymentFormID number :=0;                       -- id ����� �������
        paymentStateDescription varchar2 (128) :='';    -- �������� ������� ������� 
        paymentState  varchar2 (32) :='';               -- ������ ������� 
        clientState char(1) := '';                      -- ������ ������� (�A� � ��������;�T� � ����������� � �.�.)
        clientStatus char(1) := '';                     -- ������ ������� (�A� � �������� ��� �D� � ������)
        authChoice varchar2 (5) :='';                   -- ������ ����� � �������
        confirm�hoice varchar2 (10) :='smsp';           -- ������ ������������� �������� TODO ������ ����� ���� ��������:1
        tempValue varchar2 (512) := '';                 -- ��� ����������� �������� � ��� ����� �������
        resourceName varchar2 (512) := '';              -- ��� ����������� �������� � ��� ����� �������
        currencyCode char(3) := '';						-- ������ �������
        clientDocType varchar(32):='';					-- ��� ���-�� � ����� �������
        clientDocName varchar(32):='';					-- ������������ ���-�� � ����� �������
        operationType char(1):='';						-- ��� �������, ��� ���������� ������������ ����� ������ ��������
        isIQWavePayment boolean := false;				-- ������� ���������� ������ ��� ���
        residental char(1):='1';						-- ������� ������������� 0 � ����������, 1 � ��������
        docVersion char(1):='0';                        -- ��� �������� ������ ���������
        creationType varchar2(25);                      -- ��� ������� �������

        -- ��� ������������ xml � AUTHENTICATION_MODES
        S1 varchar2(200) :='<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"><properties><entry key="simple-auth-choice">'; 
        S2 varchar2(50) :='</entry><entry key="secure-confirm-choice">';
        S3 varchar2(64) :='</entry>';
        curentConvertObject varchar2 (128) :='';		-- ������������ ������� �������������� �������� (��� ����)
        CurentClient varchar2(32);						-- ������� ������������� ������ (ID � ������� ��������������� ��������)
        otherObjectError varchar2 (128) :='';			-- ������������ ������� �������������� �������� (��� ����)
        STARTTIME timestamp; 							-- ����� ������ ������� ����������
        OraError varchar2(1024);						-- �������� ������ oracle
        idSBOL varchar2(32);                            -- id ������� � ����
        login  varchar2(32);                            -- ����� �������
        pinNumber number;                               -- ����� ��� ��������
        firstName varchar2(32):='';                     -- ��� ����������
        patrName varchar2(32):='';                      -- �������� ����������
        surName varchar2(64):='';                       -- ������� ����������
        reasonType varchar2(10):='';                    -- ��� ����������
        blockedUntil timestamp(0);                      -- ����� �� ������� ����������� ������
        migrationFlagStatus number;                     -- ������ ����� ��������
		phoneNumber varchar2(32);                       -- ����� �������� �������
        fromResourceCurrency varchar2(3);               -- ������ ����� ��������
        toResourceCurrency varchar2(3);                 -- ������ ����� ����������
        debitAmount number;                             -- ����� � ������ ����� ��������
        creditAmount number;                            -- ����� � ������ ����� ����������
    begin

        savepoint start_client_migration;
        curentConvertObject := '���������� ���������� ����������';
        otherObjectError:='';
        STARTTIME := SYSTIMESTAMP;

        select "scheme_Client", "adapter", "suffix", "region_id", "default_Department", "scheme_Employer","billing_id" into 
               schemeClient, adapter, suffix, regionID, defaultDepartment, schemeEmployer,billingID
        from CONVERTER_CONFIG where "id"='1';	

        curentConvertObject := '������� ������ �� ������� �� ���� ����';
        -- ��������������� ��������
        for C in ( select CLIENT_ID,
                          FIRST_NAME,
                          SUR_NAME,
                          PATR_NAME,                              
                          "simple-auth-choice",
                          CLN_LOCKED,
                          LOCK_REASON,
                          STATE,
                          DOC_TYPE,
                          DOC_SERIES,
                          DOC_NUMBER,
                          AGREEMENT_NUMBER,
                          case GENDER
                            when 1 then 'M'
                            when 0 then 'F'
                            else 'M' -- ���� ��� �� ������ � ���� ����, �� ������ �������
                          end as GENDER,
                          BIRTHDAY,
                          AGREEMENT_DATE,
                          INSERTION_OF_SERVICE, 
                          DOC_NAME, 
                          TB,
                          OSB,
                          OFFICE,
                          IS_EDBO,
                          IS_CARD,
                          EXTRACTVALUE(XML_DATA,'/ESClient/RegistrAdr') as REG_ADDRESS,
                          EXTRACTVALUE(XML_DATA,'/ESClient/LivingAdr') as RES_ADDRESS,
                          EXTRACTVALUE(XML_DATA,'/ESClient/BirthPlace') as BIRTH_PLACE,
                          CAST(TO_TIMESTAMP_TZ(EXTRACTVALUE(XML_DATA,'/ESClient/Passport/GetDate/Value'),'YYYY-MM-DD"T"HH24:MI:SS.ff TZH:TZM') as TIMESTAMP(0)) as DOC_ISSUE_DATE,
                          EXTRACTVALUE(XML_DATA,'/ESClient/Passport/GetPlace') as DOC_ISSUE_BY,
                          EXTRACTVALUE(XML_DATA,'/ESClient/Passport/BranchCode') as DOC_ISSUE_BY_CODE,
                          EXTRACTVALUE(XML_DATA,'/ESClient/AckMode') as MESSAGE_SERVICE,
                          EXTRACTVALUE(XML_DATA,'/ESClient/Email') as E_MAIL,
                          EXTRACTVALUE(XML_DATA,'/ESClient/HomePhone') as HOME_PHONE,
                          EXTRACTVALUE(XML_DATA,'/ESClient/OfficePhone') as JOB_PHONE,
                          REPLACE(EXTRACTVALUE(XML_DATA,'/ESClient/MobilePhone'),'-','') as MOBILE_PHONE,
                          EXTRACTVALUE(XML_DATA,'/ESClient/MobileOper/NaimOper') as MOBILE_OPERATOR,
                          EXTRACTVALUE(XML_DATA,'/ESClient/Citizen') as CITIZEN_RF,
                          EXTRACTVALUE(XML_DATA,'/ESClient/Inn') as INN                 
                   from V_CLIENTS@promLink where CLIENT_ID = hextoraw(SBOL_ID))
        loop

            select  S_CONVERTER_CLIENTS.NEXTVAL into CurentClient from dual;
            idSBOL:= C.CLIENT_ID;
            select  S_CONVERTER_LOGINS.NEXTVAL into temp from dual;
            login := 'BB'||temp;

            phoneNumber := '';
/*
            -- �������� ����� �������� ������� � ����, ������������� � ������� � ��������� ��� �� ������������
            buildPhonNumber(C.MOBILE_PHONE);
            if (validatePhonNumber(phoneNumber) = '0') then
                raise_application_error(-20001, '������ ��� �������� ������ ���. �������� ������� - ������������ ������: ����� '||phoneNumber); 
            end if;
*/
            -- ������� ����������� ������ � ����
            delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL; 

            curentConvertObject := '���������� ������ �������';
            -- ��������� �� ���������� ������� � ���� � ��������� ����� ��� �������
            select count(USER_ID) into temp from LOGINS where USER_ID = login;
            if ( temp != '0' ) then
                login := suffix || login; 
            end if;

            --������� ID ������ ��� �������� �������
            select  S_LOGINS.NEXTVAL into loginID from dual;
            -- ��������� ����� ��� �������
            insert into LOGINS values (loginID, login, 'C', 0, '0', null, null, null, '', '', '','','0');

            curentConvertObject := '���������� �������';
            -- �������� ������ �������
            select  S_ADDRESS.NEXTVAL into adressRegId from dual;
            insert into ADDRESS values (adressRegId, '', '', '', '', '', '', '', '', C.REG_ADDRESS);
            select  S_ADDRESS.NEXTVAL into adressResId  from dual;
            insert into ADDRESS values (adressResId, '', '', '', '', '', '', '', '', C.RES_ADDRESS);

            curentConvertObject := '�������� � �������������';
            begin
                -- ������� �������������, � �������� �������� ������ �� ����� ��, ��� � ���
                if (C.OFFICE is not null) then
                    select ID into departmentID from DEPARTMENTS where TB = ltrim(C.TB,'0') and OSB = ltrim(C.OSB,'0') and OFFICE = ltrim(C.OFFICE,'0');
                else
                    select ID into departmentID from DEPARTMENTS where TB = ltrim(C.TB,'0') and OSB = ltrim(C.OSB,'0') and (OFFICE is null or OFFICE = ''); 
                end if;			  
            exception when NO_DATA_FOUND then 
                departmentID := defaultDepartment;
                otherObjectError := '�� ������� �������������, ������������ �������� � ����������';
            end;

            clientStatus := 'A';
            -- ����� ������ �������
            case C.STATE
                when 'OK' then  -- ��������
                    clientState := 'A';  
                when 'DL' then -- ������
                    clientState := 'D'; 
                    clientStatus := 'D';
                when 'DR' then --�����������
                    clientState := 'T'; 
                when 'BL' then -- ����������
                    curentConvertObject := '������ ���� ����������';
                    clientState := 'A';  
                    temp := null;
                    -- ��������� ������� ����������
                    if ( instr(lower(C.LOCK_REASON),'��� �������') != 0 ) then
                        reasonType := 'system';
                        blockedUntil := null;

                    elsif ( instr(lower(C.LOCK_REASON),'������') != 0 ) then
                        reasonType := 'wrongLogons';
                        select cast(systimestamp+0.0021 as timestamp(0)) into blockedUntil from dual;

                    else 
                        reasonType := 'employee';
                        blockedUntil := null;
                        -- ���� ���������� � �������������, � �������� ����������� ������� � ������� �� ����������/������������� �������� (���������� 1-� �� ������)
                        -- ���� �� ����� ����������� ����������, �� ������ � exception
                        curentConvertObject := '����� ���������� ��� ���������� �������';
                        select E.ID into temp from EMPLOYEES E 
                            inner join SCHEMEOWNS S on E.LOGIN_ID = S.LOGIN_ID 
                                where DEPARTMENT_ID = departmentID and S.SCHEME_ID = schemeEmployer and rownum = 1; 
                    end if;

                    curentConvertObject := '���������� ������';
                    -- ��������� �������, ���� �� ������������ � ����
                    insert into LOGIN_BLOCK	values (S_LOGIN_BLOCK.NEXTVAL, loginID, reasonType, C.LOCK_REASON, systimestamp, blockedUntil, temp);
                else
                    raise_application_error(-20001, '������ ������� �� ���������');
            end case;
           
            -- ����������� ���� �������
            if ( C.IS_EDBO = '1' ) then
                --������� ���� - �������������� ����������� ������������.
                creationType := 'UDBO';
                -- ��������� ������������� �������
                for AC in (select ACCOUNT_NUMBER from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID)) 
                loop 
                    if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                        residental := '0';
                        exit;
                    end if;
                end loop;

            elsif ( C.IS_CARD = '1' and C.IS_EDBO is null ) then
                --��������� �������. ��������� �� ����� � ����� ������ �����.
                creationType := 'CARD';

            else
                --������� ���� - �������, ����������� � ����� ������� �� �������� ������.	
                creationType := 'SBOL';
                curentConvertObject := '��������� �����'; 
                -- ��������� ����� �������
                for AC in (select * from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID)) loop 
                    if (clientStatus = 'A') then
                        -- ��������� ������ �������� �����
                        if ( substr(AC.ACCOUNT_NUMBER,1,3)='423' or substr(AC.ACCOUNT_NUMBER,1,3)='426') then
                            insert into ACCOUNT_LINKS values (S_ACCOUNT_LINKS.NEXTVAL, AC.ACCOUNT_NUMBER ||'|'|| adapter, '0', loginID, AC.ACCOUNT_NUMBER, '0', NVL(AC.ACCOUNT_NAME, ' '), '1', '1');        
                        end if;
                    end if;
                    if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                        residental := '0';
                    end if;
                end loop;
            end if;

            case C.MESSAGE_SERVICE
                when 'Email' then tempValue := 'email';
                when 'SMS' then tempValue := 'sms';
                else tempValue := ''; -- "None" ��� "Null"
            end case;

            curentConvertObject := '��������� �������';
            -- ��������� �������
            select  S_USERS.NEXTVAL into clientID from dual;
            insert into USERS values (clientID, C.CLIENT_ID||'|'|| adapter, loginID, clientStatus, C.FIRST_NAME, C.SUR_NAME, C.PATR_NAME, C.BIRTHDAY, C.BIRTH_PLACE, adressRegId, adressResId, tempValue, C.E_MAIL, C.HOME_PHONE, C.JOB_PHONE, phoneNumber, C.MOBILE_OPERATOR, C.AGREEMENT_NUMBER, C.AGREEMENT_DATE, C.INSERTION_OF_SERVICE, C.GENDER, null, C.CITIZEN_RF,  C.INN, null, clientState, departmentID, '�', '', residental, '1', C.CLIENT_ID , '1', '', creationType,'','',sysdate,'');

            curentConvertObject := '�������� ���������� �������';
            --����� ���� ����������
            case C.DOC_TYPE
                when '0' then --  ������� ���������� ��
                  clientDocType := 'REGULAR_PASSPORT_RF';
                  clientDocName := '��������������� ������� ��';           
                when'2' then --  ������� ������
                  clientDocType := 'SEAMEN_PASSPORT';
                  clientDocName := '������� ������';           
                when '3' then -- ��� �� ���������� � ��
                  clientDocType := 'RESIDENTIAL_PERMIT_RF';
                  clientDocName := '��� �� ���������� ��';           
                when '839' then -- ������������ �����
                  clientDocType := 'MIGRATORY_CARD';
                  clientDocName := '������������ �����';           
                else -- ���� ��������
                  clientDocType := 'OTHER';
                  clientDocName := '���� ��������'; 
            end case;

            if (validateDocuments(clientDocType, C.DOC_SERIES, C.DOC_NUMBER ) = '0') then
                raise_application_error(-20001, '������ ��� �������� ��������� ������� - ������������ ������ ��� ���-��: '||clientDocType||' ����� '|| C.DOC_SERIES||'  ����� '||C.DOC_NUMBER); 
            end if;
            curentConvertObject := '��������� ��������� �������';        
            -- ��������� ��������� �������
            insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, clientDocType, clientDocName, C.DOC_NUMBER, C.DOC_SERIES, C.DOC_ISSUE_DATE, C.DOC_ISSUE_BY, C.DOC_ISSUE_BY_CODE, '1', clientID, null, '1');

            curentConvertObject := '������ ����� ���� ��� �������';   
            -- ������ ����� ���� ��� �������
            insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeClient, loginID, 'simple');		

            curentConvertObject := '��������� ��������� ��������������'; 
            -- ��������� ��������� �� ���� ������������ � ������ ������������� ��������
            if ( C."simple-auth-choice" = '1' ) then
              authChoice := 'smsp';
              S3:=S3||'<entry key="userOptionType">sms</entry></properties>';
            else
              authChoice := 'lp';
              S3:=S3||'</properties>';
            end if; 
            -- �� ������ �������� ����� ������ ������������� ��������
            insert into AUTHENTICATION_MODES values (S_AUTHENTICATION_MODES.NEXTVAL, loginID, 'simple', utl_raw.cast_to_raw( S1 || authChoice || S2 || confirm�hoice || S3 ), null);

            curentConvertObject := '��������� ������� �������';     
            -- ������� �������
            insert into PROFILE values (loginID, S_PROFILE.NEXTVAL, regionID, '1');		

            curentConvertObject := '��������� XML ������������� �������';     
            -- XMLPERSONREPRESENTATION - ����� �� ������, ����� ���, �� �� ������ ������ ������ ������ � ������� �� � ������ ��������� � ���� XML:2
            insert into XMLPERSONREPRESENTATION values (S_XMLPERSONREPRESENTATION.NEXTVAL, null, clientID);

            curentConvertObject := '��������� ������'; 
            -- ��������� ������ 
            insert into PASSWORDS values (S_PASSWORDS.NEXTVAL, 'pu', loginID, login, '1', systimestamp, cast(systimestamp+730 as timestamp(0)), '0');

            curentConvertObject := '��������� ���-�������'; 
            -- ��������� ���-������� (��� ���� ������ ��������� ������ �������)
            select  S_PINENVELOPES.NEXTVAL into pinNumber from dual;
            insert into PINENVELOPES values (pinNumber, 1, login, 'U', login, departmentID);

            update LOGINS set PIN_ENVELOPE_ID=pinNumber where ID=loginID;

            curentConvertObject := '��������� ������� ��������'; 
            -- ��������� ������� �������� (�������)
            for P in (select   ID,
                               KIND,
                               DOCUMENT_DATE,
                               DOC_NUMBER,
                               AMOUNT,
                               CREATION_DATE, 
                               XML_DATA,
                               STATE_CODE,
                               CLIENT_ID,
                               DOC_VERSION,
                               PAYER_ACCOUNT,
                               PAYER_CARD,
                               OPERATION_VALUTE,
                               OPERATION_DATE,
                               ADMISSION_DATE, 
                               XML_DATA_Q,
                               XML_DATA_A
                      from V_PAYMENTS@promLink where (CLIENT_ID = C.CLIENT_ID and ( AMOUNT!=0 or AMOUNT is not null)))
            loop 

                /*                              ����� ��������
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
                case P.STATE_CODE
                    when 'OK' then
                        paymentState := 'EXECUTED';
                        paymentStateDescription := '������ ������� �������� ������: �������� �������� ���������� �� ���� ���������� �������.';
                    when 'RF' then 
                        paymentState := 'REFUSED';
                        paymentStateDescription := '����� � ���������� ���������.';
                    when 'RC' then 
                        paymentState := 'RECALLED';
                        paymentStateDescription := '�������� ������� ���� �� �����-���� �������.';
                    else
                        raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end case;

                if (P.AMOUNT is null) then
                    currencyCode := null; -- ������ ������� (��������� ��������)
                else 
                    currencyCode := nvl(P.OPERATION_VALUTE, 'RUB'); -- ������ ������� (��������� ��������)
                end if;

                operationType := '';	-- ��� �������� � ����� �������
                isIQWavePayment := false;
                resourceName := '';

                curentConvertObject:='���������� ������� '||P.KIND;

                -- ��������� ����� ������ �������/������� (����� �����) E
                if (P.KIND in ('CDPA0','CDPA1','CDPA2','CD2C0','CD2C1','CD2C2','F0200','F0201','F0202')) then 
                    select  ID into paymentFormID from PAYMENTFORMS where NAME = 'ConvertCurrencyPayment';
                    operationType:= 'E';

                    -- ���� ������ XML_DATA < 2.002.00 �� ��� �������� 'F0200','F0201','F0202' ����� �������/���������� ����� �� ������� xml_data_a
                    if substr(P.KIND,1,1)='F' and ( substr(P.DOC_VERSION,2,1)='1' or (substr(P.DOC_VERSION,2,1)='2' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then
                        docVersion := '1';
                    else    
                        docVersion := '0';
                    end if;

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/NameOfAccount') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/Valute') as "receiver-account-currency",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/CODAccountName') as "receiver-account-name",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as "from-card-expire-date",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/CardEndDate/Value'),1,10) as "to-card-expire-date",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       EXTRACTVALUE(P.XML_DATA,'/*/CurrExchRate/Value') as "course",
                                       case 
                                           when (docVersion='1' and paymentState='EXECUTED') then EXTRACTVALUE(P.XML_DATA_A,'/*/confirmation_offline_a/debitRow/sum')
                                           else EXTRACTVALUE(P.XML_DATA,'/*/DebitSumma/Value')
                                       end as "debit-summa",
                                       case 
                                           when (docVersion='1' and paymentState='EXECUTED') then EXTRACTVALUE(P.XML_DATA_A,'/*/confirmation_offline_a/creditRow/sum')
                                           else EXTRACTVALUE(P.XML_DATA,'/*/CreditSumma/Value')
                                       end as "credit-summa",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       case SUBSTR(P.KIND,1,3)
                                           when 'CDP' then EXTRACTVALUE(P.XML_DATA,'/*/CardNumber')
                                           when 'CD2' then EXTRACTVALUE(P.XML_DATA,'/*/CardNumber')
                                           when 'F02' then EXTRACTVALUE(P.XML_DATA,'/*/Credit/ACC')
                                           else EXTRACTVALUE(P.XML_DATA,'/*/Credit/ACC')
                                       end as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       case 
                                           when (docVersion='1') then EXTRACTVALUE(P.XML_DATA_A,'/*/error_offline_a/message')
                                           else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                       end as REFUSING_REASON
                                  from dual)
                    loop
                        debitAmount := getAmount(PEF."debit-summa");
                        creditAmount := getAmount(PEF."credit-summa");
                        fromResourceCurrency := PEF."payer-account-currency";
                        toResourceCurrency := PEF."receiver-account-currency";

                        -- �������� ������� ����� ���������� � � ������
                        if (creditAmount is null) then
                            toResourceCurrency := null;
                        else 
                            if (toResourceCurrency is null) then
                                toResourceCurrency := getAccountCurrencyCod(PEF.RECEIVER_ACCOUNT);
                                if ( toResourceCurrency = '' ) then 
                                    raise_application_error(-20001, '���������� ���������� ������ ����� ����������, �������� �'||P.DOC_VERSION); 
                                end if;
                            end if;
                        end if;
                        -- �������� ������� ����� �������� � � ������
                        if (debitAmount is null) then
                            fromResourceCurrency := null;
                        else 
                            if (fromResourceCurrency is null) then
                                fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                                if ( fromResourceCurrency = '' ) then 
                                    raise_application_error(-20001, '���������� ���������� ������ ����� ��������, �������� �'||P.DOC_VERSION); 
                                end if;
                            end if;
                        end if;

                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                            tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                            resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                            tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                            isIQWavePayment := true; -- �������� ��������� ����� iqwave (����� �������� ���. ����)
                            resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, '', debitAmount, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName , P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0);

                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        curentConvertObject:='���������� �������������� ����� �������';
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        resourceName:='';

                        if ( substr(P.KIND,1,2) != 'CD') then
                            tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        else
                            tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                        end if;

                        if (PEF."receiver-account-name" is not null) then
                            resourceName:= PEF.RECEIVER_ACCOUNT ||' "'||PEF."receiver-account-name"||'"';
                        else 
                            resourceName:= PEF.RECEIVER_ACCOUNT;
                        end if;

                        if (toResourceCurrency is null) then
                            toResourceCurrency := PEF."receiver-account-currency";
                            if (toResourceCurrency is null) then
                                toResourceCurrency := getAccountCurrencyCod(PEF.RECEIVER_ACCOUNT);
                            end if;
                        end if;

                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := PEF."payer-account-currency";
                            if (fromResourceCurrency is null) then
                                fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                            end if;
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'convertion-rate' ,'decimal', PEF."course", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-buy-rate-from' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-buy-rate-to' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-sale-rate-from' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-sale-rate-to' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-buy-rate-from' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-buy-rate-to' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-sale-rate-from' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-sale-rate-to' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-account-name' ,'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-account-type' ,'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-currency' ,'string', fromResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'is-sum-modify' ,'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-end-date' ,'date', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-event-type' ,'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-pay-day' ,'long', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-percent' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-priority' ,'long', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-start-date' ,'date', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-sum-type' ,'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'national-currency' ,'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'operation-code' ,'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'recalculated-amount-changed' ,'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-account-name' ,'string', PEF."receiver-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-account-type' ,'string', PEF."receiver-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource' ,'string', resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-currency' ,'string', toResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-type' ,'string', tempValue, '0');

                        -- ������� � ����� 
                        if isIQWavePayment then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'string', PEF."from-card-expire-date", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', PEF."to-card-expire-date", '0');
                        end if;

                        if (creditAmount is not null) then
                            -- ������ �������� � ���� ����� ����������
                            tempValue:='destination-field-exact';
                        elsif (debitAmount is not null) then
                            -- ������ �������� � ���� ����� ��������
                            tempValue:='charge-off-field-exact';
                        else 
                            raise_application_error(-20001, '�� ���������� �� ����� ��������, �� ����� ����������, �������� �'||P.DOC_NUMBER); 
                        end if; 
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', tempValue, '0');	

                    end loop;

                elsif (P.KIND in ('CDPAY','CD2CD')) then -- ��������� ����� ������ �������/�������	E
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                    operationType:= 'E';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/Valute') as "receiver-account-currency",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/CODAccountName') as "receiver-account-name",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as "from-card-expire-date",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/CardEndDate/Value'),1,10) as "to-card-expire-date",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/CardNumber') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       case 
                                           when (SUBSTR(P.DOC_VERSION,2,1)='1' or (SUBSTR(P.DOC_VERSION,2,1)='2' and TO_NUMBER(SUBSTR(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then EXTRACTVALUE(P.XML_DATA_A,'/*/error_offline_a/message')
                                           else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                       end as REFUSING_REASON
                                  from dual)
                    loop                   
                        if (PEF."payer-account-currency" is not null) then
                            currencyCode := PEF."payer-account-currency";
                        else
                            currencyCode := PEF."receiver-account-currency";
                        end if;

                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                           if (currencyCode is null) then
                               currencyCode := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                           end if;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           isIQWavePayment := true; -- �������� ��������� ����� iqwave (����� �������� ��� ����)
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', currencyCode, '0');
                        resourceName:='';

                        if (PEF."receiver-account-name" is not null) then
                            resourceName:= PEF.RECEIVER_ACCOUNT ||' "'||PEF."receiver-account-name"||'"';
                        else 
                            resourceName:= PEF.RECEIVER_ACCOUNT;
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource', 'string', resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', currencyCode, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-type', 'string', PEF."receiver-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', PEF."to-card-expire-date", '0');

                        -- ������� � ����� 
                        if isIQWavePayment then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', PEF."from-card-expire-date", '0');
                        else 
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-name', 'string', PEF."receiver-account-name", '0');
                        end if;

                    end loop;

               elsif (P.KIND = 'F0100') then -- ��������� ������ H  
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';

                    -- ���� ������ XML_DATA < 1.002.06 �� ���� FIO �������� �������� ����������� ����� �������
                    if (substr(P.DOC_VERSION,2,1)='1' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.06) then
                        docVersion := '1';
                    elsif (substr(P.DOC_VERSION,2,1)='1' or (substr(P.DOC_VERSION,2,1)='2' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then
                        docVersion := '2';
                    else 
                        docVersion := '0';
                    end if;

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/NameOfAccount') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",                                            
                                       EXTRACTVALUE(P.xml_data,'/*/IsNotSber/Value') as "is-our-bank",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/SbBankName') as "receiver-bank",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/ACC') as "receiver-account-internal",
                                       case docVersion
                                           when '1' then EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio')
                                       end as "receiver-fullname",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/Last') as "receiver-surname",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/First') as "receiver-first-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/Middle') as "receiver-patr-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/creditInn') as "receiver-inn",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/ACC') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       case 
                                           when docVersion='1' or docVersion='2' then EXTRACTVALUE(P.XML_DATA_A,'/*/error_offline_a/message')
                                           else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                       end as REFUSING_REASON
                                  from dual)
                    loop
                        fromResourceCurrency := PEF."payer-account-currency";

                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                        end if;

                        if (docVersion = '1') then
                            getFIO(PEF."receiver-fullname", firstName, patrName, surName); 
                        else
                            firstName := PEF."receiver-first-name"; 
                            patrName := PEF."receiver-patr-name"; 
                            surName := PEF."receiver-surname";   
                        end if;

                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND||', �������� �'||P.DOC_NUMBER;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, surName||' '||firstName||' '||patrName, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        curentConvertObject:='������� �������������� ����� �������';
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', PEF."receiver-account-internal", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', firstName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', patrName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', surName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'charge-off-field-exact', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');
                    end loop;

                elsif (P.KIND in ('F0110','F0210','F0211','F0212')) then -- �������� ������� 	H
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';

                    --��� �������� F0110 ��� ������ XML_DATA < 01.006.00 ����� ����� ������ �� ���� Beneficiary
                    if (P.KIND='F0110' and substr(P.DOC_VERSION,2,1)='1' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 6) then
                        tempValue := '/*/Beneficiary';
                    else
                        tempValue := '/*/�reditNotSber';
                    end if;

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/NameOfAccount') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",                                            
                                       EXTRACTVALUE(P.XML_DATA,'/*/IsNotSber/Value') as "is-our-bank",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/Bank/BIK') as "receiver-bic",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/Bank/CorAcc') as "receiver-cor-account",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/Bank/Name') as "receiver-bank",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/FirmName/Last') as "receiver-surname",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/FirmName/First') as "receiver-first-name",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/FirmName/Middle') as "receiver-patr-name",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/FirmAcc') as "receiver-account-internal",
                                       EXTRACTVALUE(P.XML_DATA, tempValue||'/FirmInn') as "receiver-inn",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/Credit/ACC') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       case 
                                           when (SUBSTR(P.DOC_VERSION,2,1)='1' or (SUBSTR(P.DOC_VERSION,2,1)='2' and TO_NUMBER(SUBSTR(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then EXTRACTVALUE(P.XML_DATA_A,'/*/error_offline_a/message')
                                           else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                       end as REFUSING_REASON
                                  from dual)
                    loop
                        fromResourceCurrency := PEF."payer-account-currency";
                        toResourceCurrency := getAccountCurrencyCod(PEF."receiver-account-internal");

                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                        end if;
 
                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        if (P.KIND='F0110') then
                            insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                                values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF."receiver-account-internal", PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);
                        else
                            insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                                values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, '', departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF."receiver-account-internal", PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '0', 0);
                        end if;

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');

                        if (PEF."is-our-bank"='true') then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                        else
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', PEF."receiver-account-internal", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF."receiver-bic", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string',  PEF."receiver-cor-account", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', PEF."receiver-first-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', PEF."receiver-patr-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', PEF."receiver-surname", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');

                        if (P.KIND='F0110') then
                            -- ������ �������� � ���� ����� ��������
                            tempValue:='charge-off-field-exact';
                        elsif (debitAmount is not null) then
                            -- ������ �������� � ���� ����� ����������
                            tempValue:='destination-field-exact';
                        end if; 
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', tempValue, '0');	

                    end loop;

                elsif (P.KIND = 'LOATR') then -- ���������� ��������� - ����� �������� ������� H
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/NameOfAccount') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",                                            
                                       EXTRACTVALUE(P.XML_DATA,'/*/IsNotSber/Value') as "is-our-bank",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecBank/BIK') as "receiver-bic",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecBank/CorAcc') as "receiver-cor-account",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecBank/Name') as "receiver-bank",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecFIO/Last') as "receiver-surname",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecFIO/First') as "receiver-first-name",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecFIO/Middle') as "receiver-patr-name",
                                       EXTRACTVALUE(P.XML_DATA, '/*/RecInn') as "receiver-inn",
                                       -- ���. ���� ����������� ���������
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                       NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'),'10') as "priority",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/AddInfo') as "GROUND",
                                       -- ����� ����
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/RecAcc/Account') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON
                                  from dual)
                    loop
                        fromResourceCurrency := PEF."payer-account-currency";
                        toResourceCurrency := getAccountCurrencyCod(PEF.RECEIVER_ACCOUNT);

                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                        end if;

                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF."GROUND", loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '1', 0);

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'event-object', 'string', resourceName, '0');

                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                        if (PEF."is-our-bank"='true') then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                        else
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                        end if;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', PEF.RECEIVER_ACCOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF."receiver-bic", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string',  PEF."receiver-cor-account", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', PEF."receiver-first-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', PEF."receiver-patr-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', PEF."receiver-surname", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'destination-field-exact', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');
                        -- ��� ���� ����������� ���������:
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date' ,'date', PEF."end-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type' ,'string', PEF."event", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day' ,'long', PEF."payment-dates", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-percent' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority' ,'long', PEF."priority", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date' ,'date', PEF."begin-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type' ,'string', PEF."sum-type", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'GROUND', 'string', PEF."GROUND", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'RECEIVER_ACCOUNT', 'string', PEF.RECEIVER_ACCOUNT, '0');
                    end loop;

                elsif (P.KIND = 'F0502') then -- ������ ����� (������ ��������� � �������� ������� H ���-��� � ������� ��� ���� ����� ��� �������)
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/BIK') as "receiver-bic",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Debet/AccInfo/Valute') as "payer-account-currency",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/CorAcc') as "receiver-cor-account",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/Name') as "receiver-bank",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Account') as "receiver-account-internal",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/FirmInn') as "receiver-inn",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Beneficiary/Name') as RECEIVER_NAME,
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/Beneficiary/Account') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       case 
                                           when (SUBSTR(P.DOC_VERSION,2,1)='1' or (SUBSTR(P.DOC_VERSION,2,1)='2' and to_number(SUBSTR(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then EXTRACTVALUE(P.XML_DATA_A,'/*/error_offline_a/message')
                                           else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                       end as REFUSING_REASON
                                  from dual)
                    loop
                        fromResourceCurrency := PEF."payer-account-currency";
                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                        end if;

                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', PEF.RECEIVER_NAME, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string',  PEF."receiver-account-internal", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF."receiver-bic", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string',  PEF."receiver-cor-account", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'jur', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                    end loop;

                elsif (P.KIND in ('F0504')) then -- ������ ����� (������) P
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                    currencyCode := 'RUB'; 	
                    operationType:= 'P';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/RecIdentifier') as "RecIdentifier",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsPeriod/PayMonth') as "DPayMonth",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsPeriod/PayYear') as "DPayYear",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsCurrCode') as "DebtsCurrCode",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsSumma') as "DebtsSumma",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsComission') as "DebtsComission",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsCaseNumber') as "DebtsCaseNumber",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/FlatNumber') as "FlatNumber",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/Period/PayMonth') as "PayMonth",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/Period/PayYear') as "PayYear",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/LastIndication') as "LastIndication",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/CurrentIndication') as "CurrentIndication",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/TariffNumber/TariffVar') as "TariffVar",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/TariffNumber/TariffZone') as "TariffZone",
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/DebtsAccNumber') as "DebtsAccNumber",
                                       EXTRACTVALUE(P.XML_DATA,'/*/PayeeService/RouteCode') as "receiverId",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Payee/Title') as RECEIVER_NAME,
                                       EXTRACTVALUE (P.XML_DATA_Q,'/*/Head/MessType') as "MessType",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Payee/Account') as "receiver-account",
                                       EXTRACTVALUE(P.XML_DATA,'/*/DebetCardW4/AccInfo/CODAccountName') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/DebetCardW4/AccInfo/Valute') as "payer-account-currency",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCardW4/DateClose/Value'),1,10) as "from-card-expire-date",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/Beneficiary/Account') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON
                                  from dual)
                    loop
                        fromResourceCurrency := PEF."payer-account-currency";
                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := 'RUR';
                        end if;
                        curentConvertObject:='����� ���������� ����� '||PEF."receiverId";
                        -- ���� ���������� �����
                        temp_reserv := PEF."receiverId";
                        select ID, EXTERNAL_ID into temp, tempValue from SERVICE_PROVIDERS where CODE = PEF."receiverId" and BILLING_ID = billingID and ALLOW_PAYMENTS=1;
						
                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, P.OPERATION_VALUTE, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'true', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', PEF."from-card-expire-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', P.PAYER_CARD, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', PEF."payer-account-name", '0');

                        if (PEF."payer-account-name" is not null) then
                            resourceName:= P.PAYER_CARD ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', resourceName, '0');                        
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_�HECK', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'admissionDate', 'string', TO_CHAR(P.ADMISSION_DATE, 'yyyy-mm-dd'), '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount', 'string', P.AMOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountCurrency', 'string', currencyCode, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountInternal', 'string', P.AMOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'authorizeCode', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'chargeOffDate', 'string', TO_CHAR(P.ADMISSION_DATE, 'yyyy-mm-dd'), '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'codeService', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'commission', 'string', PEF.COMMISSION, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', PEF."DebtsSumma", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', PEF."DebtsCurrCode", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', PEF."DebtsSumma", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentDate', 'string', TO_CHAR(P.DOCUMENT_DATE, 'yyyy-mm-dd'), '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentNumber', 'string', P.DOC_NUMBER, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'nameService', 'string', '', '0');-- TODO
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationCode', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationDate', 'string', TO_CHAR(P.OPERATION_DATE, 'yyyy-mm-dd'), '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationTime', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'payPeriod', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'phoneNumber', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-description', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', PEF.RECEIVER_NAME, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', PEF."receiver-account", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', PEF.RECEIVER_NAME, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', temp, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', paymentState, '0');

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', PEF.EXTERNAL_ID, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'RecIdentifier', 'string', PEF."RecIdentifier", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'false', '0');

                        if (PEF."MessType" = 'PaymentGkhRequest') then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'PayMonth', 'string', PEF."PayMonth", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'PayYear', 'string', PEF."PayYear", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'DebtsCurrCode', 'string', PEF."DebtsCurrCode", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'DebtsSumma', 'string', PEF."DebtsSumma", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'DebtsComission', 'string', PEF."DebtsComission", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'DebtsCaseNumber', 'string', PEF."DebtsCaseNumber", '0');
                        elsif (PEF."MessType" = 'PaymentMgtsRequest')  then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'FlatNumber', 'string', PEF."FlatNumber", '0');
                        elsif (PEF."MessType" = 'PaymentMosenergoRequest')  then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'PayMonth', 'string', PEF."PayMonth", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'PayYear', 'string', PEF."PayYear", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'LastIndication', 'string', PEF."LastIndication", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'CurrentIndication', 'string', PEF."CurrentIndication", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'TariffVar', 'string', PEF."TariffVar", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'TariffZone', 'string', PEF."TariffZone", '0');
                        elsif (PEF."MessType" = 'PaymentRostelecomRequest')  then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'DebtsAccNumber', 'string', PEF."DebtsAccNumber", '0');
                        end if;
                    end loop;

                elsif (P.KIND = 'LOAPT') then -- ���������� ��������� �� ������ ����� - ����� �������� ������� H
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/NameOfAccount') as "payer-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",                                            
                                       EXTRACTVALUE(P.XML_DATA,'/*/Beneficiary/Debet[1]/IsOurAccount') as "is-our-bank",
                                       EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/BankBic') as "receiver-bic",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/BankCorAcc') as "receiver-cor-account",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/Bank/Name') as "receiver-bank",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/FullName') as "jur-receiver-name",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/Fio/Last') as "receiver-surname",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/Fio/First') as "receiver-first-name",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/Fio/First') as "receiver-patr-name",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/Account') as "receiver-account-internal",
                                       EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/FirmInn') as "receiver-inn",
                                       -- ���. ���� ����������� ���������
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                       NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'), '10') as "priority",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                       EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                       NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/AddInfo'), 
                                       EXTRACTVALUE(P.XML_DATA, '/*/Body/Form190Info/Purpose')) as "GROUND",
                                       -- ����� ����
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/RecAcc/Account') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON
                                  from dual)
                    loop
                        fromResourceCurrency := PEF."payer-account-currency";
                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := 'RUR';
                        end if;

                        toResourceCurrency := getAccountCurrencyCod(PEF."receiver-account-internal");

                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF."GROUND", loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, '', departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF."receiver-account-internal", PEF."jur-receiver-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '1', 0);

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'event-object', 'string', resourceName, '0');

                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                        if (PEF."is-our-bank"='true') then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'true', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                        else
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                        end if;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', PEF."jur-receiver-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', PEF."receiver-account-internal", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF."receiver-bic", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string',  PEF."receiver-cor-account", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', PEF."receiver-first-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', PEF."receiver-patr-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', PEF."receiver-surname", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'jur', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'destination-field-exact', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');
                        -- ��� ���� ����������� ���������:
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date' ,'date', PEF."end-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type' ,'string', PEF."event", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day' ,'long', PEF."payment-dates", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-percent' ,'decimal', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority' ,'long', PEF."priority", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date' ,'date', PEF."begin-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type' ,'string', PEF."sum-type", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'GROUND', 'string', PEF."GROUND", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'RECEIVER_ACCOUNT', 'string', PEF."receiver-account-internal", '0');

                    end loop;

                elsif (P.KIND = 'F2001') then -- ��������� �� ������ ����������	O
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LossPassbookApplication';
                    operationType:= 'O';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "deposit-account-name",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "amount-currency",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       '' as REFUSING_REASON
                                from dual)
                    loop
                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_ACCOUNT, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        resourceName:='';
                        if (PEF."deposit-account-name" is not null) then
                            resourceName:= P.PAYER_ACCOUNT ||' "'||PEF."deposit-account-name"||'"';
                        else 
                            resourceName:= P.PAYER_ACCOUNT;
                        end if;

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount-currency', 'string', PEF."amount-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account-number', 'string', P.PAYER_ACCOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount-or-passbook', 'string', '1', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'deposit-account-name', 'string', PEF."deposit-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'deposit-account-type', 'string', resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'deposit-account', 'string', P.PAYER_ACCOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'money-or-transfer', 'string', '1', '0');
                    end loop;

                elsif (P.KIND = 'CRBLK') then -- �������� ���������� �����	Q
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'BlockingCardClaim';
                    operationType:= 'Q';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in ( select EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/AccInfo/CODAccountName') as "card-name",
                                        EXTRACTVALUE(P.XML_DATA,'/*/BlockReason') as "reason",
                                        -- ����� ����
                                        EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                        TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                        EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                        EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON
                                 from dual)
                    loop
                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        if (PEF."reason" = 'STOLEN') then
                            tempVAlue := 'steal';
                        elsif (PEF."reason" = 'LOST') then
                            tempVAlue := 'lost';
                        elsif (PEF."reason" = 'ATM') then
                            tempVAlue := 'atm';
                        else -- other
                            tempVAlue := 'other';
                        end if;

                        resourceName:='';
                        if (PEF."card-name" is not null) then
                            resourceName:= P.PAYER_CARD ||' "'||PEF."card-name"||'"';
                        else 
                            resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-type', 'string', PEF."card-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'reason', 'string', tempVAlue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card', 'string', resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'externalId', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-name', 'string', PEF."card-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'full-name', 'string', C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-number', 'string', P.PAYER_CARD, '0');
                    end loop;

                elsif (P.KIND = 'FC201') then -- �������� ���������� �����	Q
                    select  ID into paymentFormID from PAYMENTFORMS where "NAME" = 'BlockingCardClaim';
                    operationType:= 'Q';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in ( select EXTRACTVALUE(P.XML_DATA_Q,'/*/POSGATE_MSG/WAY4_REQUEST/PRO_CODE') as "reason",
                                        EXTRACTVALUE(P.XML_DATA_Q,'/*/POSGATE_MSG/WAY4_REQUEST/PAN') as "card-number",
                                        -- ����� ����
                                        EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                        TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                        EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                        EXTRACTVALUE(P.XML_DATA_A,'/*/WAY4_ERROR/ERR_MESS') as REFUSING_REASON
                                 from dual )
                    loop
                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        if (PEF."reason" = 'LOCK_STEAL') then
                            tempVAlue := 'steal';
                        elsif (PEF."reason" = 'LOCK_LOST') then
                            tempVAlue := 'lost';
                        elsif (PEF."reason" = 'LOCK_ATM') then
                            tempVAlue := 'atm';
                        else -- other
                            tempVAlue := 'other';
                        end if;

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'reason', 'string', tempVAlue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card', 'string', PEF."card-number", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'externalId', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'full-name', 'string', C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-number', 'string', PEF."card-number", '0');
                    end loop;

                elsif (P.KIND in ('SZPAY')) then -- ��������� ������ ������������� �� ������� T
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LoanPayment';
                    operationType:= 'T';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in ( select EXTRACTVALUE(P.XML_DATA,'/*/Credit/Account/ACC') as "loan-account-number",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "from-resource-currency",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "from-account-name",
                                        SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as "from-card-expire-date",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/CreditSumma/Money/Value') as "loan-amount",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/CreditSumma/CurrencyCode') as "loan-currency",
                                        --EXTRACTVALUE(P.XML_DATA,'/*/Credit/No') as "loan-number",    
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[1]/Sum/Money/Value') as "loan-detail1-amount",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[1]/AccountId') as "loan-detail1-name",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[2]/Sum/Money/Value') as "loan-detail2-amount",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[2]/AccountId') as "loan-detail2-name",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[3]/Sum/Money/Value') as "loan-detail3-amount",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[3]/AccountId') as "loan-detail3-name",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[4]/Sum/Money/Value') as "loan-detail4-amount",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[4]/AccountId') as "loan-detail4-name",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON                                       
                                 from dual)
                    loop
                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, PEF."from-resource-currency", departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF."loan-account-number", '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                        if (PEF."from-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."from-account-name"||'"';
                        end if;

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||', �������� �'||P.DOC_NUMBER;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."from-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', PEF."from-resource-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', PEF."from-card-expire-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'resource-currency', 'string', PEF."from-resource-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-amount', 'string', PEF."loan-amount", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-currency', 'string', PEF."loan-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan', 'string', PEF."loan-account-number", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-account-number', 'string', PEF."loan-account-number", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-external-id', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'agreement-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'office', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'charge-off-field-exact', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date', 'date', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date', 'date', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day', 'long', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-long-offer-sum', 'boolean', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority', 'long', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'national-currency', 'string', '','0'); 
                        if (PEF."loan-detail1-name" is not null) then 
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail1-name"), 'decimal', PEF."loan-detail1-amount", '0'); 
                        end if;
                        if (PEF."loan-detail2-name" is not null) then 
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail2-name"), 'decimal', PEF."loan-detail2-amount", '0'); 
                        end if;
                        if (PEF."loan-detail3-name" is not null) then 
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail3-name"), 'decimal', PEF."loan-detail3-amount", '0'); 
                        end if;
                        if (PEF."loan-detail4-name" is not null) then 
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail4-name"), 'decimal', PEF."loan-detail4-amount", '0'); 
                        end if;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'total-payment-amount', 'decimal', replace(P.AMOUNT,',','.'), '0');
                    end loop;

                elsif (P.KIND in ('LOACR')) then -- �������� ����������� ��������� �� ������ ������� T
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LoanPayment';
                    operationType:= 'T';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in ( select EXTRACTVALUE(P.XML_DATA,'/*/Credit/Account/ACC') as "loan-account-number",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "from-resource-currency",
                                        EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "from-account-name",
                                        SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as "from-card-expire-date",
                                        EXTRACTVALUE(P.XML_DATA,'/*/ClientCredit/CreditSumma/CurrencyCode') as "loan-currency",
                                        EXTRACTVALUE(P.XML_DATA,'/*/ClientCredit/CreditSumma/Money/Value') as "loan-amount",
                                        -- ���. ���� ����������� ���������
                                        SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                        SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                        NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'),'10') as "priority",
                                        EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                        EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                        EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                        EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                        -- ����� ����
                                        EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                        TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                        EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                        EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON
                                 from dual )
                    loop
                        if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                           resourceName:= P.PAYER_ACCOUNT;
                        elsif (P.PAYER_CARD is not null) then
                           tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                           resourceName:= P.PAYER_CARD;
                        end if;

                        curentConvertObject:='���������� �������� ����� �������: '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, P.OPERATION_VALUTE, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF."loan-account-number", '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '1', 0);

                        if (PEF."from-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."from-account-name"||'"';
                        end if;

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."from-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', PEF."from-resource-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', PEF."from-card-expire-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'resource-currency', 'string', PEF."from-resource-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-amount', 'string', PEF."loan-amount", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-currency', 'string', PEF."loan-currency", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan', 'string', PEF."loan-account-number", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-account-number', 'string', PEF."loan-account-number", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'loan-external-id', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'agreement-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'office', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'charge-off-field-exact', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date', 'date', PEF."begin-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date', 'date', PEF."end-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day', 'long', PEF."payment-dates", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type', 'string', PEF."sum-type", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-long-offer-sum', 'boolean', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority', 'long', PEF."priority", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type', 'string', PEF."event", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'national-currency', 'string', '','0'); 
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'total-payment-amount', 'decimal', replace(P.AMOUNT,',','.'), '0');
                    end loop;

                elsif (P.KIND in ('LODEL')) then -- �������� ������ ����������� ��������� R
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RefuseLongOffer';
                    operationType:= 'R';

                    curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                    for PEF in (select EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderInfo/FormNumber/Value') as "parent-id", 
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderProperty/DateBegin/Value'), 1, 10) as "start-date",
                                       SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderProperty/DateEnd/Value'), 1, 10) as "end-date",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Order/Debet/ACC') as "payer-resource",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Order/Debet/AccInfo/AccountType') as "payer-resource-type",
                                       case EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderProperty/ExeEventCode')
                                           when 'ONCE_IN_MONTH' then '����������'
                                           when 'ONCE_IN_QUARTER' then '������ 3 ������ (�������������)'
                                           when 'ONCE_IN_HALFYEAR' then '��� � �������'
                                           when 'ONCE_IN_YEAR' then '��� � ���'
                                           when 'BY_ANY_RECEIPT' then '�� ������ ����������'
                                           when 'BY_CAPITAL' then '�� �������������'
                                           when 'BY_SALARY' then '�� ���������� ��������'
                                           when 'BY_PENSION' then '�� ���������� ������'
                                           when 'BY_PERCENT' then '�� ���������� ���������'
                                           when 'ON_OVER_DRAFT' then '��� ������������� ����������'
                                           when 'ON_REMAIND' then '������� �� ����� �������� ������ ������������ �������'
                                           when 'ON_EVENT' then '�� �������'
                                       end as "execution-event-type",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Order/CreditAccount/Account') as "receiver-resource",
                                       EXTRACTVALUE(P.XML_DATA,'/*/Order/CreditAccount/AccountType') as "receiver-resource-type",
                                       -- ����� ����
                                       EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                       TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                       EXTRACTVALUE(P.XML_DATA,'/*/Order/CreditAccount/Account') as RECEIVER_ACCOUNT,
                                       EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                       '' as REFUSING_REASON
                                  from dual)
                    loop
                        if (PEF."payer-resource-type" = 'Deposit') then
                           tempValue := 'ACCOUNT';
                        else
                           tempValue := 'CARD';
                        end if;

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME, PEF.RECEIVER_ACCOUNT, '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', PEF."payer-resource", P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '1', 0);

                        if (PEF."receiver-resource-type" = 'Deposit') then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource-type', 'string', 'ACCOUNT', '0');                        
                        else
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource-type', 'string', 'CARD', '0');                            
                        end if;
 
                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-id', 'string', PEF."parent-id", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-type', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'offer-external-id', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource', 'string', PEF."receiver-resource", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'payer-resource', 'string', PEF."payer-resource", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'payer-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'payer-resource-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'amount', 'string', P.AMOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'start-date', 'string', PEF."start-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'end-date', 'string', PEF."end-date", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'execution-event-type', 'string', PEF."execution-event-type", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-office', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'long-offer-name', 'string', '', '0');
                    end loop;
                end if;

            end loop; -- �������

            -- ��������� ������� ��������
            -- ��������� ����� ������ �������/�������	E
            curentConvertObject:='��������� ������� �������� (������ �����)';
            temp:=1;
            for T_CARD in (select ID, TEMPLATE_NAME, CLIENT_ID, CARD_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME, RECEIVER_ACCOUNT from V_TEMPLATES_CARD@promLink where (CLIENT_ID = C.CLIENT_ID) )
            loop
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                operationType:= 'E';

                curentConvertObject:='������� ������� (�������)';
                -- ��������� ������
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                    values (paymentID, '', loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_CARD.PAYER_LNAME||' '||T_CARD.PAYER_FNAME||' '||T_CARD.PAYER_MNAME, T_CARD.RECEIVER_ACCOUNT, '', null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='������� ���. ����� ������� (�������)';
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource', 'string', T_CARD.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', T_CARD.CARD_CUR, '0');

                curentConvertObject:='������� �������';
                insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_CARD.TEMPLATE_NAME, '1', temp);
                temp:= temp+1;
            end loop;

            for T_ACC in ( select  CLIENT_ID, TEMPLATE_NAME, RECEIVER_NAME, RECEIVER_ACCOUNT, ACC_NAME, ACC_CUR, INN, AMOUNT, GROUND, IS_NOT_SBER, BIK, CORACC, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME from V_TEMPLATES_ACCOUNT@promLink where (CLIENT_ID = C.CLIENT_ID) )
            loop
                select  ID into paymentFormID from PAYMENTFORMS where NAME = 'RurPayment';
                operationType:= 'H';
                getFIO(T_ACC.RECEIVER_NAME, firstName, patrName, surName);
                if (T_ACC.AMOUNT is null) then
                    currencyCode := null;
                else 
                    currencyCode := NVL(T_ACC.ACC_CUR,'RUR');
                end if;
                curentConvertObject:='������� ������� (�������)';
                -- ��������� ������
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                    values (paymentID, T_ACC.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', currencyCode, departmentID, temp, '1', '0', operationType, systimestamp, '', T_ACC.PAYER_LNAME||' '||T_ACC.PAYER_FNAME||' '||T_ACC.PAYER_MNAME, T_ACC.RECEIVER_ACCOUNT, T_ACC.RECEIVER_NAME, T_ACC.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);
                                                                                                                                                  
                curentConvertObject:='������� �������������� ����� ������� (�������)';
                -- ��������� ���. ����
                if (T_ACC.IS_NOT_SBER='0' or T_ACC.IS_NOT_SBER is null) then
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                else
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'true', '0');
                end if;

                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', T_ACC.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', T_ACC.BIK, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', T_ACC.CORACC, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', firstName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', T_ACC.INN, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', patrName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', surName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');

                curentConvertObject:='������� �������';
                -- ��������� ������ � �������
                insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_ACC.TEMPLATE_NAME, '1', temp);
                temp:= temp+1;
            end loop;             

/*          -- TODO ��������� �� ������ ���� �������� ������� iqw ��������
            for T_PAYMENT in ( select  CLIENT_ID, TEMPLATE_NAME, NEWNUM, RECEIVER_COR_ACC, RECEIVER_NAME, RECEIVER_ACCOUNT, RECEIVER_INN, UNIQ_NUM, ACC_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME, 
*/
--                                    TO_NUMBER(EXTRACTVALUE(XML_DATA,'/*/Summa/Value'),'9999999999999999999.99') as AMOUNT,
--                                    EXTRACTVALUE(XML_DATA, '/*/Bank/BIK') as "receiver-bic",
--                                    EXTRACTVALUE(XML_DATA, '/*/Bank/Name') as "receiver-bank"
--                                    EXTRACTVALUE(XML_DATA, '/*/Purpose') as "ground"
/*
                               from V_TEMPLATES_PAYMENT@promLink where (CLIENT_ID = C.CLIENT_ID) )
            loop

                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                operationType:= 'P';
                -- ��������� ������
                select  S_BUSINESS_DOCUMENTS.NEXTVAL INTO paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                    values (paymentID, '', loginID, paymentFormID, systimestamp, null, 'SAVED_TEMPLATE', '�� ����������� �������� � �������� ��������.', T_PAYMENT.ACC_CUR, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, '', T_PAYMENT.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, 0);

                -- ��������� ���. ����
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'true', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', resourceName, '0');                        
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_�HECK', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'admissionDate', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountCurrency', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountInternal', 'string', '','0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'authorizeCode', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'chargeOffDate', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'codeService', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'commission', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', '','0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentDate', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentNumber', 'string', temp, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'nameService', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationCode', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationDate', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationTime', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'payPeriod', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'phoneNumber', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', T_PAYMENT."receiver-bank", '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  T_PAYMENT."receiver-bic", '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', T_PAYMENT.RECEIVER_COR_ACC, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-description', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', T_PAYMENT.RECEIVER_NAME, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', T_PAYMENT.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', T_PAYMENT."receiver-bic", '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', T_PAYMENT."receiver-bank", '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', T_PAYMENT.RECEIVER_COR_ACC, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', T_PAYMENT.RECEIVER_NAME, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', '', '0');

                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', '', '0');
                -- ��������� ������ � �������
                insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_PAYMENT.TEMPLATE_NAME, '1', temp);
                temp:= temp+1;
            end loop; 
*/

            for T_PAYMENT in ( select CLIENT_ID, TEMPLATE_NAME, NEWNUM, RECEIVER_COR_ACC, RECEIVER_NAME, RECEIVER_ACCOUNT, RECEIVER_INN, UNIQ_NUM, ACC_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME, 
                                    TO_NUMBER(EXTRACTVALUE(XML_DATA,'/*/Summa/Value'),'9999999999999999999.99') as AMOUNT,
                                    EXTRACTVALUE(XML_DATA, '/*/Bank/BIK') as "receiver-bic",
                                    EXTRACTVALUE(XML_DATA, '/*/Bank/Name') as "receiver-bank",
                                    EXTRACTVALUE(XML_DATA, '/*/Purpose') as GROUND
                               from V_TEMPLATES_PAYMENT@promLink where (CLIENT_ID = C.CLIENT_ID) )                                    
            loop
                select ID into paymentFormID from PAYMENTFORMS where NAME = 'RurPayment';
                operationType:= 'H';

                if (T_PAYMENT.AMOUNT is null) then
                    currencyCode := null;
                else 
                    currencyCode := NVL(T_PAYMENT.ACC_CUR,'RUR');
                end if;

                -- ��������� ������
                curentConvertObject:='������� ������� (�������)';
                select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE)
                    values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', currencyCode, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, T_PAYMENT.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', C.CLIENT_ID ||'|'|| adapter, null, null, null, 0, '0', 0);
                
                curentConvertObject:='������� �������������� ����� ������� (�������)';
                -- ��������� �������������� ���� �������
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'event-object', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');

                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', T_PAYMENT.RECEIVER_NAME, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', T_PAYMENT.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', T_PAYMENT."receiver-bank", '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', T_PAYMENT."receiver-bic", '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', T_PAYMENT.RECEIVER_COR_ACC, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', T_PAYMENT.RECEIVER_INN, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'jur', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                
                curentConvertObject:='������� �������';
                -- ��������� ������ � �������
                insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_PAYMENT.TEMPLATE_NAME, '1', temp);
                temp:= temp+1;
            end loop; 

        end loop; -- ������
        -- ����������� ������� �������� ��������
        migrationFlagStatus:= eskadm1.migration.EndClientMigration@promLink(idSBOL,100);
        case migrationFlagStatus 
            when '-2' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL'); 
            when '-1' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ��� ������� � ��������� ���������������');
            when  '1' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ������ �� � ��������� "�������� ������"');
            when  '2' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: �� ������� �������� ��������� � ����� ����� ��������');
            else null;
        end case;
        -- ������ ��� ��������� ���������
        insert into CONVERTER_CLIENTS values (CurentClient, idSBOL, loginID , login, STARTTIME, systimestamp, setNumber,'O','������ ������������ �������, OTHER:'||otherObjectError,'','');
        commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then 
            rollback to start_client_migration;
            OraError:=SQLERRM;
            migrationFlagStatus := eskadm1.migration.EndClientMigration@promLink(idSBOL,-1);
            case migrationFlagStatus 
                when '-2' then otherObjectError := otherObjectError + ' ������: ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL';
                when '-1' then otherObjectError := otherObjectError + ' ������: ��� ������� � ��������� ���������������';
                when '1' then otherObjectError := otherObjectError + ' ������: ������ �� � ��������� "�������� ������"';
                when '2' then otherObjectError := otherObjectError + ' ������: �� ������� �������� ��������� � ����� ����� ��������';
                else null;
            end case;
            insert into CONVERTER_CLIENTS values (CurentClient, idSBOL, null , login, STARTTIME, SYSTIMESTAMP, setNumber,'E','������ ��� ������� ������� SBOL_ID: '|| SBOL_ID ||', ���: '||curentConvertObject||', OTHER:'||otherObjectError||', ERROR: '||OraError,'','');
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� ������� SBOL_ID: '|| SBOL_ID ||', ���: '||curentConvertObject||', ERROR: '||OraError);          
            commit;
    end ImportClient;

    procedure ImportEmployee(SBOL_ID in varchar2, setNumber in number) is
        -- ��������� ��� ��������� ���������� TODO ������� ��� ��������� � ����������� ������
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
        idSBOL varchar2(32);                -- id ���������� � ����
        login  varchar2(32);                -- �����
        emplPassword varchar2(32);          -- ������
        emplPasswordHash varchar2(64);      -- ��� ������

    begin
        -- ��������������� �����������
        savepoint start_employee_migration;
        -- ��������� ���������
        curentConvertObject := '���������� �������� ����������';
        select "suffix", "adapter", "default_Department" into 
                suffix,  adapter, defaultDepartment
        from CONVERTER_CONFIG where "id"='1';	

        STARTTIME := SYSTIMESTAMP;
        select S_CONVERTER_EMPLOYEES.NEXTVAL into CurentEmployer from dual;
        otherObjectError:='';

        curentConvertObject := '������� ������ �� ���������� �� ���� ����';
        for EMP in ( select ID, USR_NAME,LOGIN,USR_DELETED,USR_LOCKED, USR_LOCK_REASON, TB, OSB, OFFICE, "ROLE" from V_EMPLOYEES@promLink where ID = SBOL_ID ) 
        loop
            idSBOL:= EMP.ID;
            login:=EMP.LOGIN; 

            -- �������� �������������� ����������
            -- TODO �������� �������� �� ������ ���������� (��������� �� �����������)
            if (EMP.USR_DELETED != '1') then

                curentConvertObject := '���������� ������';
                -- ��������� �� ���������� ������� ����������� � ��������� ����� ��� ����������
                select COUNT(USER_ID) into temp from LOGINS where USER_ID = EMP.LOGIN;
                if ( temp = '0' ) then
                    select S_LOGINS.NEXTVAL into loginID from dual;
                    insert into LOGINS values (loginID, EMP.LOGIN, 'E', 0, '0', '', null, null, '', '', '','','0');
                else
                    select S_LOGINS.NEXTVAL into loginID from dual;
                    insert intO LOGINS values (loginID, suffix || EMP.LOGIN, 'E', 0, '0', '', null, null, '', '', '','','0');
                end if;

                curentConvertObject := '���������� ������';
                -- ��������� ����������, ���� �� ������������ � ���� 
                if (EMP.USR_LOCKED = '1') then
                    insert into LOGIN_BLOCK	values (S_LOGIN_BLOCK.NEXTVAL, loginID, 'wrongLogons', EMP.USR_LOCK_REASON, systimestamp, cast(systimestamp + 0.0021 as timestamp(0)), null);
                end if;

                -- ������� �������������, � �������� �������� ��������� �� ����� ��, ��� � ���
                curentConvertObject := '���������� �������������';
                begin
                    -- ������� �������������, � �������� �������� ������ �� ����� ��, ��� � ���
                    if (EMP.OFFICE is not null) then
                        select ID into departmentID from DEPARTMENTS where TB = ltrim(EMP.TB,'0') and OSB = ltrim(EMP.OSB,'0') and OFFICE = ltrim(EMP.OFFICE,'0');
                    else
                        select ID into departmentID from DEPARTMENTS where (TB = ltrim(EMP.TB,'0') and OSB = ltrim(EMP.OSB,'0') and (OFFICE is null or OFFICE = '')); 
                    end if;	
                exception when NO_DATA_FOUND then departmentID := defaultDepartment;
                    otherObjectError := '�� ������� �������������, ������������ �������� � ����������';
                end;

                -- ��������� �.�.�. ���������� �� ������� ��� � ��������, �.�. � ���� ���� ��� �������� ����� �������
                getFIO(EMP.USR_NAME, emplFirstName, emplPatrName, emplSurName);
                if (emplFirstName is null)then
                    emplFirstName := ' '; -- ���� � ���� (����) ��� ����� ����������, �� ������� �������� �.�. ���� �� ����� ���� ������
                end if;

                curentConvertObject := '���������� ����������';					
                -- ��������� ����������
                insert into EMPLOYEES values (S_EMPLOYEES.NEXTVAL, loginID, emplFirstName, emplSurName, emplPatrName, null, null, null, departmentID, null, 0);

                curentConvertObject := '�������� ���������� � ����� ����';						
                -- ����������� ���������� � ����� ����
                select ID_SCHEME_ERIB into schemeEmployer from CONVERTER_ACCESSSCHEMES where ID_SCHEME_SBOL = EMP."ROLE";
                insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeEmployer, loginID, 'employee');		

                curentConvertObject := '��������� �������� ������������';
                -- ����������� �������� ������������
                insert into AUTHENTICATION_MODES values(S_AUTHENTICATION_MODES.NEXTVAL, loginID, 'employee', null, null);

                curentConvertObject := '��������� ������';
                if ( getEmplPassword(emplPassword, emplPasswordHash) = 'O' ) then
                    curentConvertObject := '���������� ������';
                    insert into PASSWORDS values(S_PASSWORDS.NEXTVAL, 'pu', loginID, emplPasswordHash, '1', systimestamp, cast(systimestamp + 730 as timestamp(0)), '1');
                end if;
                curentConvertObject := '';
           end if;
        end loop;
        -- ����������� ������ � �������� ����������� ���, ��������� ���������
        insert into CONVERTER_EMPLOYEES values (CurentEmployer, idSBOL, loginID , login, emplPassword, STARTTIME, SYSTIMESTAMP, setNumber,'O','��������� ������������ �������, OTHER:'||otherObjectError,'','');
        commit;
    exception 
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then 
            rollback to start_employee_migration;
            OraError:=SQLERRM;
            insert into CONVERTER_EMPLOYEES values (CurentEmployer, idSBOL, null, login, emplPassword, STARTTIME, SYSTIMESTAMP, setNumber,'E','������ ��� ������� ���������� Login: '|| login ||' ���: '||curentConvertObject||' OTHER:'||otherObjectError||' ERROR: '||OraError,'','');
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� ���������� SBOL_ID: '|| SBOL_ID ||' Login: '|| login ||', ���: '||curentConvertObject||', ERROR: '||OraError);        
            commit;
    end ImportEmployee;

    --  ��������� ������� ���� �������� �� ������ �� ��������,
    --  ������ �� �������� ��������� ������� � ������� converter_set, ����������� ���� ����� 
    procedure DeleteClients is

        loginID number :=0;                             -- ������� ID ������ �������
        clientID number :=0;                            -- ID �������
        adressRegId number:=0;                          -- id ������ �����������
        adressResId number:=0;                          -- id ������ ����������
        curentConvertObject varchar2 (128) :='';		-- ������������ ������� �������������� �������� (��� ����)
        STARTTIME timestamp; 							-- ����� ������ ������� ����������
        OraError varchar2(1024);						-- �������� ������ oracle
        idSBOL varchar2(32);                            -- id ������� � ����
        login  varchar2(32);                            -- ����� �������
        migrateStatus  integer;                 		-- ������ ������������ �������
    begin
        -- �������� ��������
        for REC IN ( select ID_SBOL, LOGIN from CONVERTER_SET ) 
        loop  
            begin
                STARTTIME := SYSTIMESTAMP;
                curentConvertObject := '����� �������'; 
                select ID into loginID from LOGINS where USER_ID = REC.LOGIN;
                select ID into clientID  from USERS where LOGIN_ID = loginID;
                login := REC.LOGIN;

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

                curentConvertObject := '������� �����'; 
                delete from CARD_LINKS where LOGIN_ID = loginID;

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
                delete from CONVERTER_CLIENTS where ID_SBOL = REC.ID_SBOL; 
--                update CONVERTER_CLIENTS set ID_ERIB='', TIME_START=STARTTIME, TIME_STOP=SYSTIMESTAMP, STATE='D', STATE_DESCRIPTION='������ �� ������� ������� �� ����' where LOGIN=login;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '������ login: '||login||' ������.');        
                commit;
             exception when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '������ ��� �������� ������� login: '||login||', ���: '||curentConvertObject||', ERROR: '||OraError);        
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '������ ��� �������� ��������, ��������� �������� ����������� ERROR: '||OraError||', ��������� ������ ��������� ������ �������� �� ��������');        
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
        for REC IN ( select LOGIN from CONVERTER_SET ) 
        loop  
            begin	
                STARTTIME := SYSTIMESTAMP;

                select ID into loginID from LOGINS where USER_ID = REC.LOGIN;
                login := REC.LOGIN;

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

                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '��������� login: '||login||' ������ �������');        
                commit;
            exception 
                when OTHERS then 
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� �������� ������ ���������� login: '||loginID||', ���: '||curentConvertObject||',ERROR: '||OraError);        
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� �������� �����������, ��������� ����������� ERROR: '||OraError||'. ��������� ������ ��������� ������ ��� ��������.');                
            commit;
    end DeleteEmployees;

    -- ��������� ���� �������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������ ��������
    procedure MigrateClients(flag in Char) is

        convertDelay number;                    -- �������� ����� �������������  
        countForConverting number;              -- �� ������� �������� �������������� (������)

        countRec number :=0;                  	-- ���-�� ������� � ������
        OraError varchar2(1024);				-- �������� ������ oracle    
        isStop char(1);                         -- ������� ������ ��������� ����������
        setNumber number;                       -- ����� ������� ������
        migrateStatus  integer;                 -- ������ ������������ �������
        paymentStatus number;                   -- ������ ��������, � - ����� �������������, ��� ������� �� � ���������; P - ������� � ���������, ������������� ������
        --totalClients number;                    -- ���-�� ���������� �������� ��� ��������
        minId number;
        maxId number;
        idSBOL varchar2(32);
    begin
        if (flag = '1') then
            -- ��������� ����� ��� ����������� (��� ������� ����)
            delete from CONVERTER_SET;
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, CLIENT_ID, null
                from V_CLIENTS@promLink where (( STATE='OK' or STATE='DR' or STATE='BL') and RGN_CODE='18');
        end if;

        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, '������ �������� ����.');     
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ������ �������� ��� ��������, ���-�� �������� = '||(maxId-minId+1));

        -- ��������� ��������� ����������
        select "convert_Delay" ,"count_For_Converting", "stop" into 
               convertDelay, countForConverting, isStop 
        from CONVERTER_CONFIG where "id"='1';	

        -- ���������� ����� ������
        select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual; 
        for ind in minId..maxId
            loop  
                select ID_SBOL into idSBOL from CONVERTER_SET where ID = ind;
                migrateStatus := eskadm1.migration.StartClientMigration@promLink(idSBOL);
                if ( migrateStatus = 0) then
                    ImportClient(idSBOL, setNumber);
                    countRec := countRec + 1;
                else
                    case migrateStatus
                        when '-1'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: ��� ������� � ��������� ���������������'); 
                        when '1'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: �������� ��� ������');
                        when '2'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ��� ������� ������� �������� ��� ��������� �������');
                        when '3'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: � ������� ���� �������� ������');
                        when '4'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: ������ ��� ��������� MIG_STATE � MIG_TIME');
                        when '100' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: �� ������ ������� ������ RStyle');
                        when '101' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: ������� ������� ������������ ������ �������');
                        else insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������������ ����� ��������: '||migrateStatus);
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������� ��� ���������� �������, ��� ������� ������� id '||idSBOL);        
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������������� ���������');        
            end if;
            -- ������ ������ �������� �� �����������
            delete from CONVERTER_SET;
    exception 
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then 
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� ��������, ��������� ����������� ����������� ERROR: '||OraError);        
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
        --totalEmployees number;
        minId number;
        maxId number;
        idSBOL varchar2(32);
    begin
        if (flag = '1') then
            -- ��������� ����� ��� ����������� (��� ���������� ����)
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, ID, null from V_EMPLOYEES@promLink where USR_DELETED != '1' and TB in ('009','018','074'); -- TODO �������� �� ������
        end if;
        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, '������ ����������� ����.');     
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ������ ���������� ��� ��������, ���-�� ����������� = '||(maxId-minId+1));
        -- ��������� ��������� ����������
        select "convert_Delay" ,"count_For_Converting", "stop" into 
               convertDelay, countForConverting, isStop 
        from CONVERTER_CONFIG where "id"='1';	
        -- ���������� ����� ������
        select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual; 
        commit;
        for ind IN minId..maxId 
            loop  
                select ID_SBOL into idSBOL from CONVERTER_SET where ID = ind;
                ImportEmployee(idSBOL, setNumber);
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������� ��� ���������� �������, ����� ������� ����������');        
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������������� ���������');        
            end if;
            -- ������ ������ ����������� �� �����������
            delete from CONVERTER_SET;
    exception 
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then 
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� �����������, ��������� ����������� ����������� ERROR: '||OraError);        
            commit;        
    end MigrateEmployees;

    -- ��������� ���-�� ������, ���������� � �������� �������
    procedure getClientObjectsCount(clientID in number, accounts out number, documents out number, templates out number) is
        loginID number;
    begin
        -- �������� ����� id �� id �������
        select LOGIN_ID into loginID from USERS where ID = clientID;
        -- �������� ���-�� ������ �������
        select count(ID) into accounts from ACCOUNT_LINKS where LOGIN_ID = loginID;
        -- �������� ���-�� �������� �������
        select count(ID) into documents from BUSINESS_DOCUMENTS where LOGIN_ID = loginID and STATE_CODE not in ('TEMPLATE','SAVED_TEMPLATE');
        -- �������� ���-�� �������� �������
        select count(ID) into templates from BUSINESS_DOCUMENTS_RES
           where BUSINESS_DOCUMENT_ID in (select ID from BUSINESS_DOCUMENTS where LOGIN_ID = loginID );
    end;
end Converter;
/