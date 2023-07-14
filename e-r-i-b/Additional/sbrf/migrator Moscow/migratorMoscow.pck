create or replace package MigratorMoscow is

    -- ������� ������ �������� �� ������� converter_set (���� ������� �� ����� id) 
    -- ������ �������� �� �������� ����������� �������
    procedure DeleteClients;

    -- ������� ������ �������� �� ������� converter_set (���� ���������� �� ����� id)
    -- ������ ����������� �� �������� ����������� �������
    procedure DeleteEmployees;

    -- ��������� ��������
    -- flag: 0 - ��������� ������� �������������� ������
    --       1 - ��������� ����
    -- rgnCode - ��� ������� ����������� ���������
    procedure MigrateClients(flag in Char, rgnCode number); 

    -- ��������� ���� ����������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������
    -- rgnCode - ��� ������� ����������� ���������
    procedure MigrateEmployees(flag in Char, rgnCode number); 
    
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

end MigratorMoscow;
/
create or replace package body MigratorMoscow is

cardBillingXSLT constant xmltype:=xmltype('<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:template match="ClientReqs"><xsl:element name="Attributes"><xsl:for-each select="/ClientReqs/ESNbpPaymentReq"><xsl:element name="Attribute"><xsl:element name="NameBS"><xsl:value-of select="NameBS"/></xsl:element><xsl:element name="Description"><xsl:value-of select="Description"/></xsl:element><xsl:element name="NameVisible"><xsl:value-of select="NameVisible"/></xsl:element><xsl:element name="Comment"><xsl:value-of select="Comment"/></xsl:element><xsl:element name="Type"><xsl:value-of select="Type"/></xsl:element><xsl:choose><xsl:when test="Menu/MenuItem"><xsl:element name="Menu"><xsl:for-each select="Menu/MenuItem"><xsl:element name="MenuItem"><xsl:element name="Id"><xsl:value-of select="."/></xsl:element><xsl:element name="Value"><xsl:value-of select="."/></xsl:element></xsl:element></xsl:for-each></xsl:element></xsl:when></xsl:choose><xsl:element name="NumberPrecision"><xsl:value-of select="NumberPrecision"/></xsl:element><xsl:element name="IsRequired"><xsl:choose><xsl:when test="IsRequired"><xsl:value-of select="IsRequired"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsEditable"><xsl:choose><xsl:when test="IsEditable"><xsl:value-of select="IsEditable"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsVisible"><xsl:choose><xsl:when test="IsVisible"><xsl:value-of select="IsVisible"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsMainSum"><xsl:choose><xsl:when test="IsMainSum"><xsl:value-of select="IsMainSum"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsSum"><xsl:choose><xsl:when test="IsSum"><xsl:value-of select="IsSum"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsForBill"><xsl:choose><xsl:when test="IsForBill"><xsl:value-of select="IsForBill"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsKey"><xsl:choose><xsl:when test="IsKey"><xsl:value-of select="IsKey"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="RequiredForConfirmation"><xsl:choose><xsl:when test="RequiredForConfirmation"><xsl:value-of select="RequiredForConfirmation"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="SaveInTemplate"><xsl:choose><xsl:when test="SaveInTemplate"><xsl:value-of select="SaveInTemplate"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="HideInConfirmation"><xsl:choose><xsl:when test="HideInConfirmation"><xsl:value-of select="HideInConfirmation"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="MaxLength"><xsl:value-of select="AttributeLength/MaxLength"/></xsl:element><xsl:element name="MinLength"><xsl:value-of select="AttributeLength/MinLength"/></xsl:element><xsl:copy-of select="Validators"/><xsl:element name="DefaultValue"><xsl:value-of select="DefaultValue"/></xsl:element><xsl:element name="Error"><xsl:value-of select="Error"/></xsl:element></xsl:element></xsl:for-each></xsl:element></xsl:template></xsl:stylesheet>');

    -- ��������� �.�.�. �� �������, ��� � ��������
    procedure getFIO (fullNameOr in varchar2, firstName out varchar2, patrName out varchar2, surName out varchar2)
    is
        str varchar2(128):='';  -- ������ ��� ������� ��� �������
        fullName varchar2(128):='';
        i number :=0;
        j number :=0;
    begin
        -- ��������� �.�.�.
        firstName :='';
        patrName :='';
        surName :='';
        fullName := trim(fullNameOr);
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
            dbms_lock.sleep(0.005); --  ����� ��� ����� ���������� �������, ����� ������� �� �� ������������������, ��� � � ���������� ���������
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

    --������� �������� ���������� �������
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
            when 'FOREIGN_PASSPORT_RF' then
                -- ����� ������������ �������� ������ ��������� 2 �����, � ����� 7.
                if ( ( regexp_instr(documentSeries, '^\d{2}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{7}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'IMMIGRANT_REGISTRATION' then
                -- ����� ������������� � ����������� ����������� ���������� � ��������� ��� �������� � ����� � ������� ������ ��������� 25 ������
                if ( length(documentSeries||documentNumber) = 25 ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'REFUGEE_IDENTITY' then
                -- ����� ������������� ������� ������ �������� �� 1 �����, � ����� �� 6 ����
                if ( ( regexp_instr(documentSeries, '^[�-�A-z]{1}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{6}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
/*
            when 'MIGRATORY_CARD' then
                -- ����� ������������ �������� ������ ��������� 2 �����, � ����� 7.
                if ( ( regexp_instr(documentSeries, '^\d{2}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{7}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
*/
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
            when 'tax' then 'otherCostsAmount' --����� �������� � ���� �������� �� ��������� �������������
        end;
    end;

    -- ������� ��� ��������� ������� ������� � �������� ����� �������
    function getPaymentState(docStateSBOL in varchar2, paymentState out varchar2, paymentStateDescription out varchar2) return char is
    begin
        case docStateSBOL
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
                paymentState := '';
                paymentStateDescription := '';
                return 'E';
        end case;
        return 'O';
    end;

    -- ������� ��� ��������� �����������(��� �������������) ��������� ������
    function getBankLimit(departmentID in number) return number is
        bankLimit number;
    begin
        select AMOUNT into bankLimit from ( select AMOUNT from LIMITS where DEPARTMENT_ID=departmentID and (START_DATE between START_DATE and systimestamp) order by START_DATE desc ) where rownum=1;
        return bankLimit;
    exception
        when NO_DATA_FOUND then
            begin
                select AMOUNT into bankLimit from ( select AMOUNT from LIMITS where DEPARTMENT_ID=(select PARENT_DEPARTMENT from DEPARTMENTS where ID=departmentID) and (START_DATE between START_DATE and systimestamp) order by START_DATE desc ) where rownum=1;
            exception
                when NO_DATA_FOUND then return null;
            end;
    end;

    -- ������� �������� ������� ����� ������ � ��������� �� ���� �� � ����
    function checkAccounts(idSBOL in varchar2, idERIB number) return char is
        result number;
    begin
        select count(ACCOUNT_NUMBER) into result from V_ACCOUNTS@promLink AL
            where (CLIENT_ID = hextoraw(idSBOL) and exists( select ACCOUNT_NUMBER from ACCOUNT_LINKS where LOGIN_ID=idERIB and ACCOUNT_NUMBER=AL.ACCOUNT_NUMBER ));
        if ( result > 0 ) then
            return 'E';
        end if;
        return 'O';
    end;

    -- ������� �������� ������� �����/����� � ��������� �� ���� �� � ����
    function checkAccountsOwn(idSBOL in varchar2, idERIB number, resNumber varchar2, resType char) return char is
        result number;
    begin
        if (resNumber is null) then
            return 'E';
        end if;
        if (resType='C') then
            select count(CLIENT_ID) into result from V_CARDS@promLink where CLIENT_ID=hextoraw(idSBOL) and CL_PRODUCT=resNumber;
        else
            select count(ID) into result from ACCOUNT_LINKS where LOGIN_ID=idERIB and ACCOUNT_NUMBER=resNumber;
        end if;
        if ( result > 0 ) then
            return 'O';
        end if;
        return 'E';
    end;

    -- ��������� ����������� ����� ��������� ��� ���������� �������� � �������������� ������������ ��������
    procedure updateUserLimit(loginID in number, ownLimit in number, sbolAmount in number) is
        cur_amount number;
        res_amount number;
        limitID number;
    begin
        if ( sbolAmount is not null and sbolAmount>0) then
            begin
                select ID, AMOUNT into limitID, cur_amount from USERSLIMIT where LOGIN_ID=loginID and OPERATION_DATE=trunc(sysdate);
            exception
                when NO_DATA_FOUND then
                    limitID:=null;
                    cur_amount:=null;
            end;
            if ( cur_amount is not null and limitID is not null ) then
                if ( cur_amount > sbolAmount ) then
                    res_amount:=cur_amount-sbolAmount;
                else
                    res_amount:=0;
                end if;
                update USERSLIMIT set AMOUNT=res_amount where ID=limitID and LOGIN_ID=loginID;
            else
                if ( ownLimit > sbolAmount ) then
                    res_amount:=ownLimit-sbolAmount;
                else
                    res_amount:=0;
                end if;
                insert into USERSLIMIT(ID, LOGIN_ID, OPERATION_DATE, AMOUNT, CURRENCY)
                    values( S_USERSLIMIT.NEXTVAL, loginID, trunc(sysdate), res_amount, 'RUB');
            end if;
        end if;
    end updateUserLimit;

    -- ������� ����������� ���� ����� (������� ��� ���������)
    function getAccountType(tb in number, accName in varchar2) return char is
    begin
        if (tb is not null) then
            return 'O'; -- ������� ����
        else
            if (accName is null) then
                return 'C'; -- ��������� ����
            end if;
            if ( regexp_instr(lower(accName), 'maestro|visa|mastercard|���100|pro 100')= 0 ) then
                return 'O'; -- ������� ����
            else
                return 'C'; -- ��������� ����
            end if;
        end if;
    end;

    function LockMigrateClient
    (
         clientId in varchar2
    )
         return number
    is
         pragma autonomous_transaction;
         res number:=0;
         cntr number:=0;
    begin
        loop
            begin
                cntr:=cntr+1;
                insert into CONVERTER_LOCK values (clientId);
                res:=0;
                exit;
            exception
                when OTHERS then
                    if (SQLERRM like 'ORA-00001%') then
                        dbms_lock.sleep(1);
                        if (cntr>60) then -- ���� ������ �������� ������� ������� � ������ ������
                            res:=2; -- ��������� ����� �������� ������������� �������
                            exit;
                        end if;
                    else
                        res:=1; -- � �������� ���������� �������� ������
                        exit;
                    end if;
            end;
        end loop;
        commit;
        return res;
    end;

    function UnLockMigrateClient
    (
         clientId in varchar2
    )
         return number
    is
         pragma autonomous_transaction;
    begin
        if (clientId is not null) then
            delete from CONVERTER_LOCK where CLIENT_FULL_ID=clientId;
            commit;
        end if;
        return 0;
    exception
        when NO_DATA_FOUND then
            return 0;
        when OTHERS then
            rollback;
            return 1; -- ������ ��� �������������
    end;

    -- ������� ����������� ���� ���������� (����� ��� ����)
    function getReciverType(accNumber in varchar2) return char is
    begin
        if (regexp_instr(accNumber, '^(423|426|40817|40820)\d{1,25}$') = 1) then
            return 'P';
        else
            return 'J';
        end if;
    end;

    -- ��������� ������� � ���������� ���. ����� ���� �������
    procedure addExtendedFields(xmlSource in xmltype, paymentID in number, isLongOffer char) is
        CPFLFields clob;
        addInfo xmltype;
        addRequisites xmltype;
        fields xmltype;
        nextAddInfoExist char(1):='1';
        nextAddRequisitesExist char(1):='1';
        nextFieldsExist char(1):='1';
        nextMenuExist char(1):='1';
        fNameVisible varchar2(512);
        fType varchar2(512);
        fIsRequired varchar2(512);
        fRequiredForConfirmation varchar2(512);
        fSaveInTemplate varchar2(512);
        fMenuValues varchar2(2000);
        fMaxLength number;
        fMinLength number;
        fValidators varchar2(512);
        fValues varchar2(512);
    begin
        if (xmlSource is not null) then
            CPFLFields:=to_clob('<Attributes>');
            nextAddInfoExist:='1';
            for i in 1..30
            loop
                begin
                    select extract(xmlSource, '/AddInfo['||i||']'),
                           existsNode(xmlSource, '/AddInfo['||(i+1)||']')
                    into addInfo,
                         nextAddInfoExist
                    from dual;
                    if (addInfo is not null) then
                        nextAddRequisitesExist:='1';
                        for j in 1..30
                        loop
                            begin
                                select extract(addInfo, '/AddInfo/AddRequisites['||j||']'),
                                       extractvalue(addInfo, '/AddInfo/AddRequisites['||j||']/NAME'),
                                       nvl(extractvalue(addInfo, '/AddInfo/AddRequisites['||j||']/IsMandatory'), 'false'),
                                       nvl(extractvalue(addInfo, '/AddInfo/AddRequisites['||j||']/IsForSms'), 'false'),
                                       existsNode(addInfo, '/AddInfo/AddRequisites['||(j+1)||']')
                                into addRequisites,
                                     fNameVisible,
                                     fSaveInTemplate,
                                     fRequiredForConfirmation,
                                     nextAddRequisitesExist
                                from dual;
                                if (addRequisites is not null) then
                                    fMaxLength:=0;
                                    fMinLength:=0;
                                    fValues:='';
                                    fMenuValues:='';
                                    nextFieldsExist:='1';
                                    fIsRequired:='false';
                                    for k in 1..30
                                    loop
                                        begin
                                            for F in ( select
                                                              extractvalue(addRequisites, '/AddRequisites/FIELD['||k||']/ENTERED_DATA') as "ENTERED_DATA",
                                                              extractvalue(addRequisites, '/AddRequisites/FIELD['||k||']/Template') as "Template",
                                                              nvl(extractvalue(addRequisites, '/AddRequisites/FIELD['||k||']/Length/Value'), '0') as "Length",
                                                              extractvalue(addRequisites, '/AddRequisites/FIELD['||k||']/Postfix') as "Postfix",
                                                              extractvalue(addRequisites, '/AddRequisites/FIELD['||k||']/Prefix') as "Prefix",
                                                              extract(addRequisites, '/AddRequisites/FIELD['||k||']/Menu') as "Menus",
                                                              existsNode(addRequisites, '/AddRequisites/FIELD['||(k+1)||']') as "NEXTFIELDSEXIST"
                                                       from dual
                                                     )
                                            loop
                                                if (F."Menus" is null) then
                                                    fType:='string';
                                                    fMaxLength:=fMaxLength + nvl(length(F."Postfix"), 0) + nvl(length(F."Prefix"), 0) + nvl(to_number(F."Length"), 0);
                                                    fMenuValues:='';
                                                else
                                                    fMenuValues:='';
                                                    fType:='list';
                                                    nextMenuExist:='1';
                                                    fMaxLength:=0;
                                                    fMenuValues:='<Menu>';
                                                    begin
                                                        for n in 1..30
                                                        loop
                                                            begin
                                                                for M in ( select extractvalue(F."Menus",'/Menu['||n||']') as "Menu",
                                                                                  existsNode(F."Menus",'/Menu['||(n+1)||']') as "NEXTMENUEXIST"
                                                                           from dual
                                                                         )
                                                                loop
                                                                    fMenuValues:=fMenuValues||'<MenuItem><Id>'||to_char(M."Menu")||'</Id><Value>'||to_char(M."Menu")||'</Value></MenuItem>';
                                                                    if (length(to_char(M."Menu"))>fMaxLength) then
                                                                        fMaxLength:=length(to_char(M."Menu"));
                                                                    end if;
                                                                    nextMenuExist:=M."NEXTMENUEXIST";
                                                                end loop;
                                                                if (nextMenuExist='0') then
                                                                    fMenuValues:=fMenuValues||'</Menu>';
                                                                    exit;
                                                                end if;
                                                            end;
                                                        end loop;
                                                    end;
                                                end if;
                                                if (F."Template" in ('P','A','S','X')) then
                                                    fIsRequired:='true';
                                                end if;
                                                fValues:=fValues||to_char(F."Prefix")||to_char(F."ENTERED_DATA")||to_char(F."Postfix");
                                                nextFieldsExist:=F."NEXTFIELDSEXIST";
                                            end loop;
                                            if (nextFieldsExist='0') then
                                                exit;
                                            end if;
                                        end;
                                    end loop;
                                    fMinLength:=nvl(length(fValues), 0);
                                    if ( fMaxLength < fMinLength ) then
                                        fMaxLength:=fMinLength;
                                    end if;
                                    fValidators:='';
                                    DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>extended-field-'||(j-1)||'_'||i||'</NameBS><Description></Description><NameVisible>'||fNameVisible||'</NameVisible><Comment></Comment><Type>'||fType||'</Type>'||fMenuValues||'<IsRequired>'||fIsRequired||'</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>true</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>'||fRequiredForConfirmation||'</RequiredForConfirmation><SaveInTemplate>'||fSaveInTemplate||'</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength>'||fMaxLength||'</MaxLength><MinLength>'||fMinLength||'</MinLength><DefaultValue></DefaultValue><Values></Values><Error></Error></Attribute>'));

                                    --������� ���. ����� extended-field... ���� � DEF
                                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'extended-field-'||(j-1)||'_'||i, 'string', fValues, '0');

                                end if;
                                if ( nextAddRequisitesExist='0' ) then
                                    exit;
                                end if;
                            end;
                        end loop;
                    end if;
                    if (nextAddInfoExist='0') then
                        exit;
                    end if;
                end;
            end loop;

            -- ���������� ����� ����� ���� � CLOB
            DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>amount</NameBS><Description></Description><NameVisible>�����</NameVisible><Comment></Comment><Type>money</Type><IsRequired>true</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>true</IsMainSum><IsSum>true</IsSum><IsForBill>false</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>false</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute>'));

            if (isLongOffer = '1') then
                -- ���������� ���� "AckClientBankCanChangeSumm" ���� � CLOB
                DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>AckClientBankCanChangeSumm</NameBS><Description></Description><NameVisible>��������� ��������� ����� ������� ��� ��������� ������</NameVisible><Comment></Comment><Type>list</Type><Menu><MenuItem><Id>true</Id><Value>��</Value></MenuItem><MenuItem><Id>false</Id><Value>���</Value></MenuItem></Menu><IsRequired>true</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>true</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>true</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute>'));
                -- ���������� ���� "�������� ������" ���� � CLOB
                DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>tariffValue</NameBS><Description></Description><NameVisible>�������� ������</NameVisible><Comment></Comment><Type>string</Type><IsRequired>false</IsRequired><IsEditable>false</IsEditable><IsVisible>false</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>false</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>false</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute>'));
                -- ���������� ���� "�����" ���� � CLOB
                DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>ServiceKind</NameBS><Description></Description><NameVisible>�����</NameVisible><Comment></Comment><Type>string</Type><IsRequired>true</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>true</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>true</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute></Attributes>'));
            else
                DBMS_LOB.APPEND(CPFLFields, to_clob('</Attributes>'));
            end if;
            -- ���������� ������ � EXTENDED_FIELDS
            update BUSINESS_DOCUMENTS set EXTENDED_FIELDS=CPFLFields where ID=paymentID;
        end if;
    end;

    procedure ImportClient(SBOL_ID in varchar2, setNumber in number) is
        -- ��������� ��� ��������� ����������
        schemeClient number;               -- c���� ������� ��� ������� (ID �����)
        adapter varchar2(64);              -- ������� (������������)
        adapterCPFL varchar2(64);          -- ������� (������������)
        adapterIQW varchar2(64);           -- ������� (������������ UUID)
        adapterCARD varchar2(64);          -- ������� (������������)
        regionID number;                   -- ������ � ������� ������� (��� �������� �����)
        defaultDepartment number;          -- ������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
        defaultDepartmentCARD number;      -- ������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
        schemeEmployer number;             -- ����� ����������, ��� �������� ��������������� ��������
        suffix varchar2(3);                -- ������� ��� ������, ��� ����������
        billingID number;                  -- ID ���������� ������� ��� �������� (id IQW)
        codTB number;                      -- ��� ������������ ��������
        allowedDepartments varchar2(512);  -- ���� ���������, � ������ ������� �������� ����������� ���������
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
        confirm�hoice varchar2 (10) :='smsp';           -- ������ ������������� ��������
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
        logonCardNumber varchar2(20);                   -- ����� �� ������� ������ ������ � ���� ��

        -- ��� ������������ xml � AUTHENTICATION_MODES
        S1 varchar2(200) :='<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"><properties><entry key="simple-auth-choice">';
        S2 varchar2(50) :='</entry><entry key="secure-confirm-choice">';
        S3 varchar2(64) :='</entry>';
        curentConvertObject varchar2 (128) :='';		-- ������������ ������� �������������� �������� (��� ����)
        curentClient number;                            -- ������� ������������� ������ (ID � ������� ��������������� ��������)
        otherObjectError varchar2 (3000) :='';			-- ��������� � ������ ���������, ��������� ����������� (��� ��������, ����� ������������� � �.�.)
        STARTTIME timestamp; 							-- ����� ������ ������� ����������
        OraError varchar2(1024);						-- �������� ������ oracle
        idSBOL varchar2(32);                            -- id ������� � ����
        login  varchar2(32);                            -- ����� �������
        pinNumber number;                               -- ����� ��� ��������
        clientFirstName varchar2(32):='';               -- ��� �������
        clientPatrName varchar2(32):='';                -- �������� �������
        cientSurName varchar2(64):='';                  -- ������� �������
        clientFullName varchar2(256):='';               -- ������ ���
        firstName varchar2(32):='';                     -- ��� ����������
        patrName varchar2(32):='';                      -- �������� ����������
        surName varchar2(64):='';                       -- ������� ����������
        reasonType varchar2(10):='';                    -- ��� ����������
        blockedUntil timestamp(0);                      -- ����� �� ������� ����������� ������
        agreementNumber varchar2(32);                   -- ����� ��������
        migrationFlagStatus integer;                    -- ������ ����� ��������
		phoneNumber varchar2(32);                       -- ����� �������� �������
        fromResourceCurrency varchar2(3);               -- ������ ����� ��������
        toResourceCurrency varchar2(3);                 -- ������ ����� ����������
        debitAmount number;                             -- ����� � ������ ����� ��������
        creditAmount number;                            -- ����� � ������ ����� ����������
        clientCreationType varchar2(25):='';            -- ��� �������� ������� ��� �������� ����� ���������� ��������
        isNeedUpdate char(1):='0';                      -- ������� ������������� ���������� ������
        comissionCurrency char(3):='';                  -- ������ ��������� � ��������
        amountTemp number;
        bankLimit number;                               -- ���������� �����
        ownLimit number;                                -- �������������� �����

        --��������� ����������
        distributionId number := 0;
        scheduleId number := 0;
        smsTemplateId number := 0;
        emailTemplateId number := 0;
        subscriptionId number := 0;
        tempFlag number := '0';
        receiverAccountType char(1);
        clientFullId varchar2(256);                     -- ������������� ��� ���������� ����������� �������
      
    begin

        savepoint start_client_migration;
        otherObjectError:='';
        STARTTIME := SYSTIMESTAMP;
        select S_CONVERTER_CLIENTS.NEXTVAL into curentClient from dual;
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
                          RGN_CODE,
                          SMS_CARD,
                          TB,
                          OSB,
                          OFFICE,
                          IS_EDBO,
                          IS_CARD,
                          SNILS,
                          DATE_UPDATE_LIMIT as OPERATION_DATE,
                          DAY_OPER_SUM as OPERSUM,
                          OWN_LIMIT as LIMIT,
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
            isNeedUpdate:='0';
            idSBOL:= C.CLIENT_ID;
            clientFullName:=C.SUR_NAME||' '||C.FIRST_NAME||' '||C.PATR_NAME;
            clientFirstName:=C.FIRST_NAME;
            clientPatrName:=C.PATR_NAME;
            cientSurName:=C.SUR_NAME;
            clientFullId:=lower(cientSurName||clientFirstName||clientPatrName)||C.BIRTHDAY||(replace(concat(C.DOC_SERIES, C.DOC_NUMBER),' ',''));
            logonCardNumber:=C.SMS_CARD;

            temp:=LockMigrateClient(clientFullId);
            case temp
                when 1 then raise_application_error(-20001, '������ � �������� ���������� ������� � converter_lock');
                when 2 then raise_application_error(-20001, '��������� ����� �������� ������������� ������� ������ ������� ��������');
                else null;
            end case;
            temp:=0;

            if  ( ( C.STATE='BL') and ( instr(lower(C.LOCK_REASON),'��� �������') != 0 ) ) then -- ������������� � ���� �� ������ �� ������� ��������������� ������� �� �����
                raise_application_error(-20111, '������ ������������ � ���� �� �� ������� ��������������� ������� �� �����, �� �������������� � ������ ����� �������� �� ���������');
            end if;

            curentConvertObject := '����������� ��������, � �������� ��������� ������';
            if ( C.RGN_CODE=99 or C.RGN_CODE=38 ) then
                if ( C.TB is null and C.IS_CARD = '1' and ( C.IS_EDBO is null or C.IS_EDBO!='1') ) then
                    codTB:=99; -- ���������� ��������� ������� ������������� � ����� 99 ��������
                elsif (C.TB is not null) then
                    codTB:=to_number(ltrim(C.TB,'0'));
                elsif (C.RGN_CODE is not null) then
                    codTB:=to_number(ltrim(C.RGN_CODE,'0'));
                else
                    codTB:=99;
                end if;
            elsif ( C.RGN_CODE=18 ) then
                codTB:=18;
            else
                if (C.TB is not null) then
                    codTB:=C.TB;
                else
                    codTB:=C.RGN_CODE;
                end if;
            end if;

            if (codTB is null) then
                raise_application_error(-20001, '������ �� ��������� � ���������, ��� ������� �������� ��������� �������� RGN_CODE='||C.RGN_CODE);
            end if;

            curentConvertObject := '��������� �������� ���������, ��� ������� ��������';
            select ADAPTER, SUFFIX, REGION_ID, DEFAULT_DEPARTMENT, SCHEME_EMPLOYER, BILLING_ID, nvl(CPFL_ADAPTER, ''), ALLOWED_CODE_TB into 
                   adapter, suffix, regionID, defaultDepartment, schemeEmployer, billingID, adapterCPFL, allowedDepartments
            from CONVERTER_CONFIG where COD_TB=codTB;	

            curentConvertObject := '����� �������� ��� IQW';
            select CODE into adapterIQW from BILLINGS b where b.id=billingID; 

            curentConvertObject := '����������� ���� ������� � ����� ����� ���� � �������';
            if ( C.IS_EDBO = '1' ) then
                creationType := 'UDBO'; --������� ���� - �������������� ����������� ������������.
                select SCHEME_CLIENT_UDBO into schemeClient from CONVERTER_CONFIG where COD_TB=codTB;
            elsif ( C.IS_CARD = '1' and ( C.IS_EDBO is null or C.IS_EDBO!='1') ) then
                creationType := 'CARD'; --��������� �������. ��������� �� ����� � ����� ������ �����.
                select SCHEME_CLIENT_CARD into schemeClient from CONVERTER_CONFIG where COD_TB=codTB;
            else
                creationType := 'SBOL';  --������� ���� - �������, ����������� � ����� ������� �� �������� ������.
                select SCHEME_CLIENT_SBOL into schemeClient from CONVERTER_CONFIG where COD_TB=codTB;
            end if;

            --����� ���� ����������
            case C.DOC_TYPE
                when '0' then   -- ������� ���������� ��
                  clientDocType := 'REGULAR_PASSPORT_RF';
                  clientDocName := '��������������� ������� ��';
                when '2' then   -- ������� ������
                  clientDocType := 'SEAMEN_PASSPORT';
                  clientDocName := '������� ������';
                when '3' then   -- ��� �� ���������� � ��
                  clientDocType := 'RESIDENTIAL_PERMIT_RF';
                  clientDocName := '��� �� ���������� ��';
                when '5' then   -- ����������� ������� ��
                  clientDocType := 'FOREIGN_PASSPORT_RF';
                  clientDocName := '����������� ������� ��';
                when '111' then -- ������������� � ����������� ����������� ���������� � ��������� ��� ��������
                  clientDocType := 'IMMIGRANT_REGISTRATION';
                  clientDocName := '������������� � ����������� ����������� ���������� � ��������� ��� ��������';
                when '113' then -- ������������� ������� � ��
                  clientDocType := 'REFUGEE_IDENTITY';
                  clientDocName := '������������� ������� � ��';
/*
                when '839' then -- ������������ �����
                  clientDocType := 'MIGRATORY_CARD';
                  clientDocName := '������������ �����';
*/
                else            -- ���� ��������
                  if (creationType='CARD' and C.DOC_SERIES is null and C.DOC_NUMBER is not null) then
                     clientDocType := 'PASSPORT_WAY';
                     clientDocName := '';
                  else
                     clientDocType := 'OTHER';
                     clientDocName := '���� ��������';
                  end if;
            end case;
/*
                                         ������ �������� ��������� �� ���� �� � ����
    ____________________________________________________________________________________________________________________
   | ����\���� |          ���������            |              ����                 |             ����                   |
   |___________|_______________________________|___________________________________|____________________________________|
   | ��������� | ���������� ��������, ������   | ��������� ��������� ������� �     | ��������� ��������� ������� � ���� |
   |           | ������� �� ����� ������ ����- | ���� �� ���� (������), ���������  | �� ���� (������), ��������� �������|
   |           | ���� (�� ���� ����������), ���| ������� �������� � �������. ���   | �������� � �������. ��� ���� ����- |
   |           | ��������� ������� �������� �  | ���� ���������� �������������, �  | ������ �������������, � ��������   |
   |           | �������.                      | �������� �������� ������, �       | �������� ������, � ������� �������,|
   |           |                               | ������� �������, �� ��, ���       | �� ��, ��� ������� � ���� ��������.|
   |           |                               | ������� � ���� ��������.          |                                    |
   |___________|_______________________________|___________________________________|____________________________________|
   |   ����    | � ����-������� �������� �     | ��������� �� ������� ����� ������ | ��������� ���� ������� � ���� ��   |
   |           | ���� ��������� �������        | ���� �� ���, �� ��������� �������.| ���� (������), ��������� �������   |
   |           | �������� � ������� �� ������- | ���� ���� ����� �����, �� ��������| �������� � �������. ��� ���� ����  |
   |           | ����.                         | �� ����-������ ��������, ������   | ������ ������������� , � ��������  |
   |           |                               | ������ ���������� �������� � ���� | �������� ������, � ������� �������,|
   |           |                               | �������������, ������� ������� �  | �� ��, ��� ������� � ���� ��������.|
   |           |                               | ����� ����� ���� ��������. ����   |                                    |
   |           |                               | �������� � ������ ��������� ������|                                    |
   |           |                               | ������� ��������� ��������, � ��� |                                    |
   |           |                               | ����� ������.                     |                                    |
   |___________|_______________________________|___________________________________|____________________________________|
   |   ����    | � ���� �������� � ���� �������| � ���� �������� � ���� ���������  | ��������� � ��� ���������� ��������|
   |           | �� ������� �������� � ������� | ������� �������� � ������� ��     | � ���� ������� �������� � �������  |
   |           | �� ����������.                | ����-�������.                     | �� �������� ���� ��.               |
   |___________|_______________________________|___________________________________|____________________________________|
*/
            -- �������� ������� ����� ������� � ���� ����, ����� ���� �� �.�.�., �.�.�. � ���� ��������
            curentConvertObject := '�������� ������� �������� ������� � ���� ����';
            begin
                curentConvertObject := '�������� �.�.�. ������� � ���� ����';
                for C_ID in ( select PERSON_ID from DOCUMENTS where replace(concat(DOC_SERIES, DOC_NUMBER),' ','')=replace(concat(C.DOC_SERIES, C.DOC_NUMBER),' ','') )
                loop
                    curentConvertObject := '�������� �.�.�. ������� � ���� ����';
                    if ( isNeedUpdate='0' )
                    then
                        otherObjectError := otherObjectError||',';
                        for ERIBC in ( select U.ID, U.LOGIN_ID, U.REGISTRATION_ADDRESS, U.RESIDENCE_ADDRESS, U.CREATION_TYPE, U.AGREEMENT_DATE, U.STATE, D.TB
                                       from USERS U
                                        left join DEPARTMENTS D on D.ID=U.DEPARTMENT_ID
                                            where U.ID=C_ID.PERSON_ID and lower(U.SUR_NAME)=lower(C.SUR_NAME) and lower(U.FIRST_NAME)=lower(C.FIRST_NAME) and lower(U.PATR_NAME)=lower(C.PATR_NAME) and U.STATUS='A' and U.STATE='A' and U.BIRTHDAY=C.BIRTHDAY and ( (instr(allowedDepartments,'|'||codTB||'|' )>0) and (instr(allowedDepartments,'|'||D.TB||'|' )>0) )  
                                            --where U.ID=C_ID.PERSON_ID and lower(U.SUR_NAME)=lower(C.SUR_NAME) and lower(U.FIRST_NAME)=lower(C.FIRST_NAME) and lower(U.PATR_NAME)=lower(C.PATR_NAME) and U.STATUS='A' and U.STATE='A' and U.BIRTHDAY=C.BIRTHDAY and ( ( codTB in (99,38) and D.TB in (99,38) ) or (codTB=18 and D.TB=18) )
                                      )
                        loop
                            otherObjectError := otherObjectError||'.fio';
                            curentConvertObject := '�������� �������� ������� � ����� ���� � ����';
                            if (C.STATE in ('OK','BL')) then       -- �������� ��� ������������
                                case ERIBC.CREATION_TYPE
                                    when 'UDBO' then               -- � ���� ���� ������ � ����
                                        isNeedUpdate:='2';         -- ��������� ������� � �������
                                    when 'CARD' then               -- � ���� ���� ��������� ������
                                        if (creationType!='CARD') then
                                            isNeedUpdate:='1';     -- ��������� ������, ��������� ������� � �������
                                        else
                                            if (ERIBC.AGREEMENT_DATE >= C.AGREEMENT_DATE) then
                                                isNeedUpdate:='2'; -- ��������� ������� � �������
                                            else
                                                isNeedUpdate:='1'; -- ��������� ������, ��������� ������� � �������
                                            end if;
                                        end if;
                                    when 'SBOL' then               -- � ���� ���� ������ � ��������� � "�������� ������"
                                        if (creationType='UDBO') then
                                            isNeedUpdate:='1';     -- ��������� ������, ��������� ������� � �������
                                        elsif (creationType='CARD') then
                                            isNeedUpdate:='2';     -- ��������� ������� � �������
                                        else
                                            if (ERIBC.TB!=ltrim(C.TB,'0')) then
                                                raise_application_error(-20001, '����� � ������ ���������');
                                            end if;
                                            -- ���� ��� ����� ������, ������ ��������� ��� �����������
                                            if ( checkAccounts(idSBOL, ERIBC.LOGIN_ID)='E' ) then
                                                if (ERIBC.AGREEMENT_DATE >= C.AGREEMENT_DATE) then
                                                    isNeedUpdate:='2'; -- ��������� ������� � �������
                                                else
                                                    isNeedUpdate:='1'; -- ��������� ������, ��������� ������� � �������
                                                end if;
                                            else
                                                isNeedUpdate:='0'; -- ������ ��������� �������, �.�. ��� ������ ������
                                            end if;
                                        end if;
                                end case;

                            elsif ( C.STATE = 'DR' ) then -- �����������
                                isNeedUpdate:='2'; -- ��������� ������� � �������

                            elsif ( C.STATE = 'DL' ) then -- ������
                                 isNeedUpdate:='0'; -- ��������� � �����
                            else
                                raise_application_error(-20001, '������ ������� �� ���������');
                            end if;

                            if (isNeedUpdate!='0') then
                                clientID:=ERIBC.ID;
                                loginID:=ERIBC.LOGIN_ID;
                                clientCreationType:=ERIBC.CREATION_TYPE;
                                adressRegId:=ERIBC.REGISTRATION_ADDRESS;
                                adressResId:=ERIBC.RESIDENCE_ADDRESS;
                            end if;
                        end loop;
                    end if;
                end loop;
            exception
                when OTHERS then isNeedUpdate:='0';
            end;

            otherObjectError:=otherObjectError||' ��� ��������:'||isNeedUpdate||' ';

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
                otherObjectError := otherObjectError||' �� ������� �������������, ������������ �������� � ���������� '||departmentID;
            end;
/*
**          ����������� ������ �� ����� ��������� � ���� ����, ������� ������ ������� ���������� ���������
*/
            if (isNeedUpdate='0') then -- ��� ����� ������ � ��� ������ ���������

                select S_CONVERTER_LOGINS.NEXTVAL into temp from dual;
                login := 'MB'||temp;

                -- ������ �������� ����� ������� �� "���������� �����"
                phoneNumber := '';

                curentConvertObject := '���������� ������ �������';
                -- ��������� �� ���������� ������� � ���� � ��������� ����� ��� �������
                select count(USER_ID) into temp from LOGINS where USER_ID = login;
                if ( temp != '0' ) then
                    login := suffix || login;
                end if;

                --������� ID ������ ��� �������� �������
                select  S_LOGINS.NEXTVAL into loginID from dual;
                -- ��������� ����� ��� �������
                if (C.STATE='DL') then
                    insert into LOGINS (ID, USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID, LOGON_DATE, LAST_LOGON_DATE, CSA_USER_ID, IP_ADDRESS, LAST_IP_ADDRESS, LAST_LOGON_CARD_NUMBER, IS_MOBILE_BANK_CONNECTED, IS_FIRST)
                        values (loginID, login, 'C', 0, '1', null, null, null, '', '', '', '', '0', '1');
                else
                    insert into LOGINS (ID, USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID, LOGON_DATE, LAST_LOGON_DATE, CSA_USER_ID, IP_ADDRESS, LAST_IP_ADDRESS, LAST_LOGON_CARD_NUMBER, IS_MOBILE_BANK_CONNECTED, IS_FIRST)
                        values (loginID, login, 'C', 0, '0', null, null, null, '', '', '', '', '0', '1');
                end if;

                curentConvertObject := '���������� �������';
                -- �������� ������ �������
                select  S_ADDRESS.NEXTVAL into adressRegId from dual;
                insert into ADDRESS values (adressRegId, '', '', '', '', '', '', '', '', C.REG_ADDRESS);
                select  S_ADDRESS.NEXTVAL into adressResId  from dual;
                insert into ADDRESS values (adressResId, '', '', '', '', '', '', '', '', C.RES_ADDRESS);

                clientStatus := 'A';
                -- ����� ������ �������
                case C.STATE
                    when 'OK' then  -- ��������
                        clientState := 'A';
                    when 'DL' then -- ������
                        clientState := 'D';
                        clientStatus := 'D';
                    when 'DR' then -- �����������
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
                            select E.LOGIN_ID into temp from EMPLOYEES E
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
                if ( creationType = 'UDBO') then
                    -- ��������� ������������� �������
                    for AC in (select ACCOUNT_NUMBER from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID))
                    loop
                        if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                            residental := '0';
                            exit;
                        end if;
                    end loop;
                elsif ( creationType = 'SBOL' ) then
                    curentConvertObject := '��������� �����';
                    -- ��������� ����� �������
                    for AC in (select ACCOUNT_NUMBER, ACCOUNT_NAME, TB from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID)) loop
                        if (clientStatus = 'A' and (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,3)='423' or ( ( substr(AC.ACCOUNT_NUMBER,1,5)='40817' or substr(AC.ACCOUNT_NUMBER,1,5)='40820' ) and getAccountType(AC.TB, AC.ACCOUNT_NAME)='O' ) ) ) then
							curentConvertObject := '��������� ���� #'||AC.ACCOUNT_NUMBER||'|'||loginID||'|'||C.CLIENT_ID;
                            insert into ACCOUNT_LINKS(ID, EXTERNAL_ID, PAYMENT_ABILITY, LOGIN_ID, ACCOUNT_NUMBER, IS_CHANGED, ACCOUNT_NAME, SHOW_IN_MAIN, SHOW_IN_SYSTEM, SHOW_OPERATIONS, SHOW_IN_ES)
                                values (S_ACCOUNT_LINKS.NEXTVAL, AC.ACCOUNT_NUMBER ||'|'|| adapter, '0', loginID, AC.ACCOUNT_NUMBER, '0', NVL(AC.ACCOUNT_NAME, ' '), '1', '1', '1', null);
                        end if;
                        if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                            residental := '0';
                        end if;
                    end loop;
                end if;

                case C.MESSAGE_SERVICE

                    when 'Email' then
                        if (C.E_MAIL is not null) then
                            tempValue := 'email';
                        end if;
                        null;
                    when 'SMS' then tempValue := 'sms';
                    else tempValue := ''; -- "None" ��� "Null"
                end case;

                curentConvertObject := '��������� �������';
                -- ��������� �������
                select S_USERS.NEXTVAL into clientID from dual;
                insert into USERS (ID, CLIENT_ID, LOGIN_ID, STATUS, FIRST_NAME, SUR_NAME, PATR_NAME, BIRTHDAY, BIRTH_PLACE, REGISTRATION_ADDRESS, RESIDENCE_ADDRESS, MESSAGE_SERVICE, E_MAIL, HOME_PHONE, JOB_PHONE, MOBILE_PHONE, MOBILE_OPERATOR, AGREEMENT_NUMBER, AGREEMENT_DATE, INSERTION_OF_SERVICE, GENDER, TRUSTING_ID, CITIZEN_RF, INN, PROLONGATION_REJECTION_DATE, STATE, DEPARTMENT_ID, CONTRACT_CANCELLATION_COUSE, SECRET_WORD, RESIDENTAL, SMS_FORMAT, DISPLAY_CLIENT_ID, USE_OFERT, CREATION_TYPE, LAST_UPDATE_DATE, REG_IN_DEPO, SNILS, MDM_STATE, USE_INTERNET_SECURITY, EMPLOYEE_ID)
                    values (clientID, C.CLIENT_ID||'|'|| adapter, loginID, clientStatus, C.FIRST_NAME, C.SUR_NAME, C.PATR_NAME, C.BIRTHDAY, C.BIRTH_PLACE, adressRegId, adressResId, tempValue, C.E_MAIL, C.HOME_PHONE, C.JOB_PHONE, phoneNumber, C.MOBILE_OPERATOR, C.AGREEMENT_NUMBER, C.AGREEMENT_DATE, C.INSERTION_OF_SERVICE, C.GENDER, null, C.CITIZEN_RF,  C.INN, null, clientState, departmentID, '�', '', residental, '1', C.CLIENT_ID , '1', creationType, sysdate, '', C.SNILS, 'NOT_SENT', '0', '');

                if ( tempValue != '' or C.E_MAIL is not null ) then
                    curentConvertObject := '��������� ���������� ���������� ��� ����������';
                    insert into PERSONAL_SUBSCRIPTION_DATA(LOGIN_ID, EMAIL_ADDRESS, MOBILE_PHONE, SMS_ENCODING)
                        values(loginID, C.E_MAIL, '', 'TRANSLIT');
                end if;

                if ( tempValue != '' ) then -- ���� ����� ����� ���� ������ ���������� ������� - ����������� ����������

                    curentConvertObject := '������������� ���������� ��� ��������� ����������';
                    select ID into distributionId from DISTRIBUTIONS where ROW_KEY = 'PaymentExecute';
                    select ID into scheduleId from SCHEDULE where DISTRIBUTION_ID = distributionId;

                    curentConvertObject := '��������� ����������';
                    select S_SUBSCRIPTIONS.NEXTVAL into subscriptionId from dual;
                    insert into SUBSCRIPTIONS(ID, LOGIN_ID, SCHEDULE_ID) values(subscriptionId, loginID, scheduleId);

                    curentConvertObject := '��������� ������ ����������';
                    if ( tempValue = 'sms' ) then
                        select ID into smsTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'sms' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, smsTemplateId, 0);
                    else
                        select ID into emailTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'email' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, emailTemplateId, 0);
                    end if;
                end if;

                curentConvertObject := '�������� ���������� �������';
                if (validateDocuments(clientDocType, C.DOC_SERIES, C.DOC_NUMBER ) = '0') then
                    raise_application_error(-20001, '������ ��� �������� ��������� ������� - ������������ ������ ��� ���-��: '||clientDocType||' ����� '|| C.DOC_SERIES||'  ����� '||C.DOC_NUMBER);
                end if;

                curentConvertObject := '��������� ��������� �������';
                -- ��������� ��������� �������
                if ( clientDocType != 'PASSPORT_WAY' ) then
                    insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, clientDocType, clientDocName, C.DOC_NUMBER, C.DOC_SERIES, C.DOC_ISSUE_DATE, C.DOC_ISSUE_BY, C.DOC_ISSUE_BY_CODE, '1', clientID, null, '1');
                else
                    insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, clientDocType, clientDocName, null, concat(C.DOC_SERIES, C.DOC_NUMBER), C.DOC_ISSUE_DATE, C.DOC_ISSUE_BY, C.DOC_ISSUE_BY_CODE, '1', clientID, null, '1');
                end if;

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

                            curentConvertObject := '��������� ������� �������'; --LOGON_NOTIFICATION_TYPE     MAIL_NOTIFICATION_TYPE     IS_BANK_OFFER_VIEWED  SHOW_PERSONAL_FINANCE
                insert into PROFILE values (loginID, S_PROFILE.NEXTVAL, regionID, '1', null, null, '1', null, null);

                curentConvertObject := '��������� XML ������������� �������';
                insert into XMLPERSONREPRESENTATION values (S_XMLPERSONREPRESENTATION.NEXTVAL, null, clientID);

                curentConvertObject := '��������� ������';
                insert into PASSWORDS values (S_PASSWORDS.NEXTVAL, 'pu', loginID, login, '1', systimestamp, cast(systimestamp+730 as timestamp(0)), '0');

                curentConvertObject := '��������� ���-�������';
                -- ��������� ���-������� (��� ���� ������ ��������� ������ �������)
                select S_PINENVELOPES.NEXTVAL into pinNumber from dual;
                insert into PINENVELOPES values (pinNumber, 1, login, 'U', login, departmentID);

                update LOGINS set PIN_ENVELOPE_ID=pinNumber where ID=loginID;

                curentConvertObject := '��������� ������';
                if (C.LIMIT is not null) then
                    bankLimit:=getBankLimit(departmentID);
                    if ( bankLimit is not null ) then
                        if ( C.LIMIT>bankLimit ) then
                            ownLimit:=bankLimit;
                        else
                            ownLimit:=C.LIMIT;
                        end if;
                        insert into INDIVIDUAL_LIMITS(ID, LOGIN_ID, START_DATE, AMOUNT, CURRENCY, IS_CHANGING)
                            values(S_INDIVIDUAL_LIMITS.NEXTVAL, loginID, systimestamp, ownLimit, 'RUB', '0');

                        curentConvertObject := '��������� ������� �� ����� ������ �� ������� ����';
                        if ( C.OPERATION_DATE=trunc(sysdate) and C.OPERSUM is not null ) then
                            updateUserLimit(loginID, ownLimit, C.OPERSUM);
                        end if;
                    end if;
                end if;
/*
**          ����������� ������ ��� ����� ������� � ���� ����, �� ������� � ���� ���� �� �������� ����� ���������� ����������
**          � �������. ������� ��������� ������ ������� � ���� ���� �� ������� �� ������ � ���� ��.
*/
            elsif (isNeedUpdate='1') then -- ��������� ������ ������� � ���� ����

                idSBOL:=C.CLIENT_ID;
                phoneNumber:='';

                curentConvertObject := '���������� �������';
                update ADDRESS set UNPARSEABLE=C.REG_ADDRESS
                    where ID=adressRegId;
                update ADDRESS set UNPARSEABLE=C.RES_ADDRESS
                    where ID=adressResId;

                clientStatus := 'A';
                -- ����� ������ �������
                case C.STATE
                    when 'OK' then  -- ��������
                        clientState := 'A';
                        delete from LOGIN_BLOCK where LOGIN_ID=loginID;
                    when 'DL' then -- ������
                        clientState := 'D';
                        clientStatus := 'D';
                        update LOGINS set DELETED='1' where ID=loginID;
                    when 'DR' then --�����������
                        clientState := 'T';
                    when 'BL' then -- ����������
                        curentConvertObject := '������ ���� ����������';
                        clientState := 'A';
                        temp := null;
                        -- ��������� ������� ����������
                        if ( instr(lower(C.LOCK_REASON),'��� �������') != 0 ) then
                            null;
                            --reasonType := 'system';
                            --blockedUntil := null;
                        elsif ( instr(lower(C.LOCK_REASON),'������') != 0 ) then
                            reasonType := 'wrongLogons';
                            select cast(systimestamp+0.0021 as timestamp(0)) into blockedUntil from dual;
                        else
                            reasonType := 'employee';
                            blockedUntil := null;
                            -- ���� ���������� � �������������, � �������� ����������� ������� � ������� �� ����������/������������� �������� (���������� 1-� �� ������)
                            -- ���� �� ����� ����������� ����������, �� ������ � exception
                            curentConvertObject := '����� ���������� ��� ���������� �������';
                            select E.LOGIN_ID into temp from EMPLOYEES E
                                inner join SCHEMEOWNS S on E.LOGIN_ID = S.LOGIN_ID
                                    where DEPARTMENT_ID = departmentID and S.SCHEME_ID = schemeEmployer and rownum = 1;
                        end if;

                        curentConvertObject := '���������� ������';
                        -- ��������� �������, ���� �� ������������ � ����, �������������� ������ ������ ����������
                        if ( instr(lower(C.LOCK_REASON),'��� �������') = 0 ) then
                            delete from LOGIN_BLOCK where LOGIN_ID=loginID;
                            insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, reasonType, C.LOCK_REASON, systimestamp, blockedUntil, temp);
                        end if;
                    else
                        raise_application_error(-20001, '������ ������� �� ���������');
                end case;

                --���� � ���� ���� ��������� ������, � �� ��������� �� ���������� �������, �� ��� ���������� ������ ��������� � �������
                if ( clientCreationType='CARD' and creationType!='CARD' ) then

                    curentConvertObject := '�������� ���������� �������';
                    if (validateDocuments(clientDocType, C.DOC_SERIES, C.DOC_NUMBER ) = '0') then
                        raise_application_error(-20001, '������ ��� �������� ��������� ������� - ������������ ������ ��� ���-��: '||clientDocType||' ����� '|| C.DOC_SERIES||'  ����� '||C.DOC_NUMBER);
                    end if;

                    curentConvertObject := '���������� ���������� ������� (PASSPORT_WAY)';
                    update DOCUMENTS set DOC_TYPE=clientDocType, DOC_NAME=clientDocName, DOC_NUMBER=C.DOC_NUMBER, DOC_SERIES=C.DOC_SERIES,
                                         DOC_ISSUE_DATE=C.DOC_ISSUE_DATE, DOC_ISSUE_BY=C.DOC_ISSUE_BY, DOC_ISSUE_BY_CODE=C.DOC_ISSUE_BY_CODE
                        where PERSON_ID=clientID and DOC_TYPE='PASSPORT_WAY';
                end if;

                -- ����������� ���� �������
                if ( creationType = 'UDBO' ) then
                    --������� ���� - �������������� ����������� ������������.
                    -- ��������� ������������� �������
                    for AC in (select ACCOUNT_NUMBER from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID))
                    loop
                        if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                            residental := '0';
                            exit;
                        end if;
                    end loop;

                elsif ( creationType = 'SBOL' ) then
                    --������� ���� - �������, ����������� � ����� ������� �� �������� ������.
                    curentConvertObject := '��������� �����';
                    -- ��������� ����� �������
                    for AC in (select ACCOUNT_NUMBER, ACCOUNT_NAME, TB from V_ACCOUNTS@promLink AL
                                where AL.CLIENT_ID = C.CLIENT_ID and not exists( select ACCOUNT_NUMBER from ACCOUNT_LINKS where LOGIN_ID=loginID and ACCOUNT_NUMBER=AL.ACCOUNT_NUMBER ))
                    loop
                        if (clientStatus = 'A' and (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,3)='423' or ( ( substr(AC.ACCOUNT_NUMBER,1,5)='40817' or substr(AC.ACCOUNT_NUMBER,1,5)='40820' ) and getAccountType(AC.TB, AC.ACCOUNT_NAME)='O' ) ) ) then
                            curentConvertObject := '��������� ���� #'||AC.ACCOUNT_NUMBER||'|'||loginID||'|'||C.CLIENT_ID;
                            insert into ACCOUNT_LINKS(ID, EXTERNAL_ID, PAYMENT_ABILITY, LOGIN_ID, ACCOUNT_NUMBER, IS_CHANGED, ACCOUNT_NAME, SHOW_IN_MAIN, SHOW_IN_SYSTEM, SHOW_OPERATIONS, SHOW_IN_ES)
                                values (S_ACCOUNT_LINKS.NEXTVAL, AC.ACCOUNT_NUMBER ||'|'|| adapter, '0', loginID, AC.ACCOUNT_NUMBER, '0', NVL(AC.ACCOUNT_NAME, ' '), '1', '1', '1', null);
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
                update USERS
                    set ID=clientID, CLIENT_ID=C.CLIENT_ID||'|'|| adapter, LOGIN_ID=loginID, STATUS=clientStatus, FIRST_NAME=C.FIRST_NAME,
                        SUR_NAME=C.SUR_NAME, PATR_NAME=C.PATR_NAME, BIRTHDAY=C.BIRTHDAY, BIRTH_PLACE=C.BIRTH_PLACE, REGISTRATION_ADDRESS=adressRegId,
                        RESIDENCE_ADDRESS=adressResId, MESSAGE_SERVICE=tempValue, E_MAIL=C.E_MAIL, HOME_PHONE=C.HOME_PHONE, JOB_PHONE=C.JOB_PHONE,
                        MOBILE_PHONE=phoneNumber, MOBILE_OPERATOR=C.MOBILE_OPERATOR, AGREEMENT_NUMBER=C.AGREEMENT_NUMBER, AGREEMENT_DATE=C.AGREEMENT_DATE,
                        INSERTION_OF_SERVICE=C.INSERTION_OF_SERVICE, GENDER=C.GENDER, TRUSTING_ID=null, CITIZEN_RF=C.CITIZEN_RF, INN=C.INN,
                        PROLONGATION_REJECTION_DATE=null, STATE=clientState, DEPARTMENT_ID=departmentID, CONTRACT_CANCELLATION_COUSE='�',
                        SECRET_WORD='', RESIDENTAL=residental, SMS_FORMAT='1', DISPLAY_CLIENT_ID=C.CLIENT_ID, USE_OFERT='1',
                        CREATION_TYPE=creationType, LAST_UPDATE_DATE=sysdate, REG_IN_DEPO='', SNILS=C.SNILS, MDM_STATE='NOT_SENT'
                where ID=clientID;

                if ( tempValue != '' or C.E_MAIL is not null ) then
                    select count(LOGIN_ID) into tempFlag from PERSONAL_SUBSCRIPTION_DATA where LOGIN_ID=loginID;
                    if (tempFlag=0) then
                        curentConvertObject := '��������� ���������� ���������� ��� ����������';
                        insert into PERSONAL_SUBSCRIPTION_DATA(LOGIN_ID, EMAIL_ADDRESS, MOBILE_PHONE, SMS_ENCODING)
                            values(loginID, C.E_MAIL, '', 'TRANSLIT');
                    else
                        curentConvertObject := '��������� ���������� ���������� ��� ����������';
                        update PERSONAL_SUBSCRIPTION_DATA set EMAIL_ADDRESS=C.E_MAIL
                            where LOGIN_ID=loginID;
                    end if;
                end if;

                select count(LOGIN_ID) into tempFlag from SUBSCRIPTIONS where LOGIN_ID=loginID;

                if ( tempValue != '' and tempFlag=0 ) then -- ���� ����� ����� ���� ������ ���������� ������� - ����������� ����������

                    curentConvertObject := '������������� ���������� ��� ��������� ����������';
                    select ID into distributionId from DISTRIBUTIONS where ROW_KEY = 'PaymentExecute';
                    select ID into scheduleId from SCHEDULE where DISTRIBUTION_ID = distributionId;

                    curentConvertObject := '��������� ����������';
                    select S_SUBSCRIPTIONS.NEXTVAL into subscriptionId from dual;
                    insert into SUBSCRIPTIONS(ID, LOGIN_ID, SCHEDULE_ID) values(subscriptionId, loginID, scheduleId);

                    curentConvertObject := '��������� ������ ����������';
                    if ( tempValue = 'sms' ) then
                        select ID into smsTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'sms' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, smsTemplateId, 0);
                    else
                        select ID into emailTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'email' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, emailTemplateId, 0);
                    end if;
                end if;

                curentConvertObject := '��������� ����� ���� ��� �������';
                update SCHEMEOWNS set SCHEME_ID=schemeClient, ACCESS_TYPE='simple'
                    where LOGIN_ID=loginID;

                curentConvertObject := '���������� ���������� ��������������';
                -- ��������� ��������� �� ���� ������������ � ������ ������������� ��������
                if ( C."simple-auth-choice" = '1' ) then
                  authChoice := 'smsp';
                  S3:=S3||'<entry key="userOptionType">sms</entry></properties>';
                else
                  authChoice := 'lp';
                  S3:=S3||'</properties>';
                end if;

                update AUTHENTICATION_MODES set ACCESS_TYPE='simple', PROPERTIES=utl_raw.cast_to_raw( S1 || authChoice || S2 || confirm�hoice || S3 ), MODE_KEY=null
                    where LOGIN_ID=loginID;

                curentConvertObject := '��������� ������� �������';
                update PROFILE set REGION_ID=regionID, SHOW_ASSISTANT='1'
                    where LOGIN_ID=loginID;

                curentConvertObject := '��������� XML ������������� �������';
                update XMLPERSONREPRESENTATION set XML=null where USER_ID=to_char(clientID);

                curentConvertObject := '��������� ������';
                if (C.LIMIT is not null) then
                    begin
                        select ID into temp_reserv from ( select ID from INDIVIDUAL_LIMITS where LOGIN_ID=loginID and (START_DATE between START_DATE and systimestamp) order by START_DATE desc ) where rownum=1;
                    exception
                        when NO_DATA_FOUND then temp_reserv:=null;
                    end;
                    bankLimit:=getBankLimit(departmentID);
                    if ( bankLimit is not null ) then
                        if ( C.LIMIT>bankLimit ) then
                            ownLimit:=bankLimit;
                        else
                            ownLimit:=C.LIMIT;
                        end if;
                        if (temp_reserv is null) then
                            insert into INDIVIDUAL_LIMITS(ID, LOGIN_ID, START_DATE, AMOUNT, CURRENCY, IS_CHANGING) values(S_INDIVIDUAL_LIMITS.NEXTVAL, loginID, systimestamp, ownLimit, 'RUB', '0');
                        else
                            update INDIVIDUAL_LIMITS set START_DATE=systimestamp, AMOUNT=ownLimit where ID=temp_reserv;
                        end if;
                        curentConvertObject := '��������� ������� �� ����� ������ �� ������� ����';
                        if ( C.OPERATION_DATE=trunc(sysdate) and C.OPERSUM is not null ) then
                            updateUserLimit(loginID, ownLimit, C.OPERSUM);
                        end if;
                    end if;
                end if;
/*
**          ����������� ������ ����� ��������� � ���� ���� � ���� ������� �������� ���������� ���������� � �������. �������
**          ��� ������ ������� ������ �� �����������, � ����������� ������� �������� � ������� ��������
*/
            elsif (isNeedUpdate='2') then -- �� ��������� ��������� ������ ������ ������ ��������� ������� �������� ������� �� ���� ���� ��
                curentConvertObject := '��������� ������������� ������� � ��������';
                select DEPARTMENT_ID, regexp_replace(CLIENT_ID, '.{1,}\|','') into departmentID, adapter from USERS where LOGIN_ID=loginID;
                idSBOL:= SBOL_ID;
				
				curentConvertObject := '��������� ������� �������';
                update PROFILE set REGION_ID=regionID, SHOW_ASSISTANT='1'
                    where LOGIN_ID=loginID;
            end if;
        end loop;

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
                  from V_PAYMENTS@promLink where CLIENT_ID = hextoraw(SBOL_ID) and kind not in ('F0400', 'FPFPR'))
        loop
            /*                              ����� �������� (OLD)
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
                                                    (NEW)
                        1	BlockingCardClaim           ���������� �����
                        2	ConvertCurrencyPayment      ����� �����
                        3	DepositorFormClaim          ��������� �� ��������� �������� ������
                        4	InternalPayment             ������� ����� ����� �������
                        5	LoanPayment                 ��������� �������
                        6	LossPassbookApplication     ������� �� ����� �������������� ������
                        7	RurPayJurSB                 ������ �����
                        8	SecuritiesTransferClaim     ��������� �� �������/����� �������� ������ �����
                        9	SecurityRegistrationClaim   ������ �� ����������� ������ ������
                        10	RecallPayment               ����� ��������
                        11	RurPayment                  ��������� ������
                        12	TaxPayment                  ������ �������
                        21	RecallDepositaryClaim       ����� ���������
                        22	RefuseLongOffer             ������ ���������� ����������� �������
                        61	AccountOpeningClaim         �������� ������
                        62	ExternalProviderPayment     ������ � �������� ������
                        63	FNSPayment                  ������ � ����� ���
                        64	PFRStatementClaim           ������ �� ��������� ������� �� ���
                        65	AccountClosingPayment       �������� ������
                        66	CreateAutoPaymentPayment    ����������
                        67	EditAutoPaymentPayment      ����������
                        68	PhizRurPayment              ��������� ������ ����������� ����
                        69	RefuseAutoPaymentPayment    ����������
            */
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

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                -- ���� ������ XML_DATA < 2.002.00 �� ��� �������� 'F0200','F0201','F0202' ����� �������/���������� ����� �� ������� xml_data_a
                if substr(P.KIND,1,1)='F' and ( substr(P.DOC_VERSION,2,1)='1' or (substr(P.DOC_VERSION,2,1)='2' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then
                    docVersion := '1';
                elsif (substr(P.KIND,1,1)='F' and ( substr(P.DOC_VERSION,2,1)='1' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.06 )) then
                    docVersion := '3';
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
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/First') as "receiver-first-name",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/Middle') as "receiver-patr-name",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/Last') as "receiver-sur-name",
                                   case
                                       when (docVersion='3') then EXTRACTVALUE(P.XML_DATA, '/*/Credit/Fio')
                                       else ''
                                   end as "receiver-full-name",
                                   EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                   case
                                       when (docVersion='1') then EXTRACTVALUE(P.XML_DATA_A,'/*/error_offline_a/message')
                                       else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                   end as REFUSING_REASON,
                                   EXTRACTVALUE(P.XML_DATA,'/*/TemplatePaymentMode') as TEMPLATE_PAYMENT_MODE
                              from dual)
                loop
                    if (PEF.TEMPLATE_PAYMENT_MODE='USE_SELF_ACCOUNT' or checkAccountsOwn(idSBOL, loginID, PEF.RECEIVER_ACCOUNT, receiverAccountType)='O') then -- ������� ����� ������ �������
                        select  ID into paymentFormID from PAYMENTFORMS where NAME = 'ConvertCurrencyPayment';
                        operationType:= 'E';
                    else
                        select ID into paymentFormID from PAYMENTFORMS where NAME = 'RurPayment';
                        operationType:= 'H';
                    end if;

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
                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;
                    if (docVersion='3') then
                        getFIO(PEF."receiver-full-name", firstName, patrName, surName);
                    else
                        surName:=PEF."receiver-sur-name";
                        firstName:=PEF."receiver-first-name";
                        patrName:=PEF."receiver-patr-name";
                    end if;
                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, surName||' '||firstName||' '||patrName, debitAmount, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName , P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', null);

                    if (PEF."payer-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                    end if;

                    curentConvertObject:='���������� �������������� ����� �������'||P.KIND||' �������� �'||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                    resourceName:='';

                    if ( substr(P.KIND,1,2) != 'CD') then
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        receiverAccountType:='A';
                    else
                        tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                        receiverAccountType:='C';
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


                    if (PEF.TEMPLATE_PAYMENT_MODE='USE_SELF_ACCOUNT' or checkAccountsOwn(idSBOL, loginID, PEF.RECEIVER_ACCOUNT, receiverAccountType)='O') then -- ������� ����� ������ �������

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

                        update BUSINESS_DOCUMENTS set SUMM_TYPE =tempValue where ID=paymentID;
                    else

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                        if (receiverAccountType='A' ) then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                        else
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourCard', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'true', '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', PEF.RECEIVER_ACCOUNT, '0');
                        end if;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', PEF.RECEIVER_ACCOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string',  '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', firstName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', patrName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', surName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', toResourceCurrency, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');

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

                        update BUSINESS_DOCUMENTS set SUMM_TYPE =tempValue where ID=paymentID;
                        
                    end if;

                end loop;

            elsif (P.KIND in ('CDPAY','CD2CD')) then -- ��������� ����� ������ �������/������� E

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

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
                                   EXTRACTVALUE(P.XML_DATA,'/*/TemplatePaymentMode') as TEMPLATE_PAYMENT_MODE,
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/Middle') as "receiver-patr-name",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/Last') as "receiver-surname",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/Fio/First') as "receiver-first-name",
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=currencyCode;
                    end if;

                    if (PEF.TEMPLATE_PAYMENT_MODE='USE_SELF_ACCOUNT' or checkAccountsOwn(idSBOL, loginID, PEF.RECEIVER_ACCOUNT, 'C')='O') then -- ������� �� ���� ����

                        select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                        operationType:= 'E';

                        curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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

                    else -- ������� �� ���� ������� ����, ����� RurPayment

                        select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                        operationType:= 'H';

                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                        curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                        if (PEF."payer-account-name" is not null) then
                            resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                        end if;

                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', currencyCode, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourCard', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'true', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', PEF.RECEIVER_ACCOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', PEF."operation-code", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', PEF.RECEIVER_ACCOUNT, '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string',  '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', PEF."receiver-patr-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', PEF."receiver-first-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', PEF."receiver-surname", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-name', 'string', PEF."receiver-account-name", '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', '', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount' ,'string', 'charge-off-field-exact', '0');
                    end if;

                end loop;

           elsif (P.KIND = 'F0100') then -- ��������� ������ H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

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
                                   end as "receiver-full-name",
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
                        getFIO(PEF."receiver-full-name", firstName, patrName, surName);
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||', �������� �'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, surName||' '||firstName||' '||patrName, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'charge-off-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');
                end loop;

            elsif (P.KIND in ('F0110','F0210','F0211','F0212')) then -- �������� ������� 	H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:='RUB';
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    if (P.KIND='F0110') then
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', null);
                    else
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, '', departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', null);
                    end if;

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', toResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify' ,'boolean', 'false', '0');

                    if (P.KIND='F0110') then
                        -- ������ �������� � ���� ����� ��������
                        tempValue:='charge-off-field-exact';
                    elsif (debitAmount is not null) then
                        -- ������ �������� � ���� ����� ����������
                        tempValue:='destination-field-exact';
                    end if;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', tempValue, '0');
                    
                    update BUSINESS_DOCUMENTS set SUMM_TYPE = tempValue where ID=paymentID;

                end loop;

            elsif (P.KIND in ('FAOPN')) then -- �������� ������	H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'AccountOpeningClaim';
                operationType:= 'G';

                curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                for PEF in (select  --EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/AvailPmtBalance/Value') as "AvailPmtBalance",
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/ClientEndDate'),1,10) as "closing-date_mig",
                                    EXTRACTVALUE(P.XML_DATA,'/*/ClientDurationText') as "ClientDurationText",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/ClientDurationValue') as "ClientDurationValue",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/ClientDurationType') as "ClientDurationType",
                                    EXTRACTVALUE(P.XML_DATA,'/*/CurrencyRate') as "Course",
                                    EXTRACTVALUE(P.XML_DATA,'/*/DebetSum/Value') as "credit-summa",                          -- ����������� �����
                                    EXTRACTVALUE(P.XML_DATA,'/*/CreditSum/Value') as "debit-summa",                            -- ����� ��������
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/DateClose/Value'),1,10) as "DateClose",  -- ���� �������� �����
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/CardNumber') as "payer-card-number",            -- ����� �����, ��� �������� ����� �� ���� � �����
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/AccInfo/Valute') as "payer-account-currency",   -- ������ ����� ��������
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/AccInfo/AccountType') as "payer-account-type",  -- ��� ����� �������� (card or deposit)
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/NameOfAccount') as "payer-account-name",        -- ������������ ����� ��������
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/ACC') as "payer-account",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountTypeInfo/InterestRate') as "interest-rate",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountTypeInfo/MinBalance') as "min-deposit-balance",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/AccountType/BchBuxEndSrok') as "BchBuxEndSrok",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/AccountType/BchBuxBeginSrok') as "BchBuxBeginSrok",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountType/MinAdditionalFee') as "min-additional-fee",
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/AccountType/PeriodEndDate'),1,10) as "closing-date",
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/AccountType/PeriodBeginDate'),1,10) as "open-date",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/AccountType/Duration') as "Duration",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountType/CurrencyIsoCode') as "receiver-account-currency",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountType/Name') as "deposit-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountType/SubCode') as "deposit-sub-type",
                                    EXTRACTVALUE(P.XML_DATA,'/*/AccountType/Code') as "deposit-type",
                                    --EXTRACTVALUE(P.XML_DATA_A,'/*/Body/CardAuthorization/AuthorizeCode') as "AUTHORIZE_CODE",
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/Body/Account') as "deposit-account", -- ����� ������������ �����
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/Head/MessUID/MessageId') as EXTERNAL_ID,
                                    case EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrCode')
                                        when '0' then ''
                                        when '-400' then '������ ���������, �������� �� ����� ���� ���������'
                                        when '-417' then '������������ �������'
                                        when '-429' then '���� ��������� �������� �� ��������� ����'
                                        when '-613' then '������������ �������'
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
                            toResourceCurrency := getAccountCurrencyCod(PEF."deposit-account");
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
                            fromResourceCurrency := getAccountCurrencyCod(PEF."payer-account");
                            if ( fromResourceCurrency = '' ) then
                                raise_application_error(-20001, '���������� ���������� ������ ����� ��������, �������� �'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;

                    if (PEF."payer-account-type"='Deposit') then
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        resourceName:= PEF."payer-account";
                    elsif (P.PAYER_CARD='Card') then
                        tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                        isIQWavePayment := true; -- �������� ��������� ����� iqwave (����� �������� ���. ����)
                        resourceName:= PEF."payer-card-number";
                    else
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        resourceName:= PEF."payer-account";
                    end if;

                    comissionCurrency:=null;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, null, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, null, clientFullName, PEF."deposit-account", clientFullName, debitAmount, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    if (PEF."payer-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                    end if;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-account-name', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-currency', 'string', fromResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-type', 'string', tempValue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'closing-date', 'string', PEF."closing-date", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'deposit-account', 'string', PEF."deposit-account", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'deposit-id', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'deposit-name', 'string', PEF."deposit-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'deposit-sub-type', 'string', PEF."deposit-sub-type", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'deposit-type', 'string', PEF."deposit-type", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount', 'string', 'charge-off-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'interest-rate', 'string', PEF."interest-rate", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'min-additional-fee', 'string', PEF."min-additional-fee", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'min-deposit-balance', 'decimal', PEF."min-deposit-balance", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'national-currency', 'string', 'RUB', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'open-date', 'string', PEF."open-date", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'operation-code', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'period-days', 'string', regexp_substr(regexp_substr(PEF."ClientDurationText", '\d{1,} \�'), '\d{1,}'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'period-months', 'string', regexp_substr(regexp_substr(PEF."ClientDurationText", '\d{1,} \�'), '\d{1,}'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'period-years', 'string', regexp_substr(regexp_substr(PEF."ClientDurationText", '\d{1,} \�'), '\d{1,}'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-currency', 'string', toResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'convertion-rate', 'decimal', PEF."Course", '0');
                end loop;

            elsif (P.KIND in ('FACLS')) then -- �������� ������ H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'AccountClosingPayment';
                operationType:='1';

                curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                for PEF in (select  EXTRACTVALUE(P.XML_DATA,'/*/Debit/FullName') as "payer-full-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debit/ACC') as "payer-account",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debit/AccInfo/Valute') as "payer-account-currency",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debit/AccInfo/CODAccountName') as "payer-account-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debit/AccInfo/AccountType') as "payer-account-type",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/Debit/IsOurAccount') as "D_IsOurAccount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/ACC') as "receiver-account",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/Valute') as "receiver-account-currency",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/CODAccountName') as "receiver-account-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/AccountType') as "receiver-account-type",
                                    --SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/Credit/DateClose/Value'),1,10) as "receiver-card-date-close",
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/CardAcctIdTo/CardNum') as "receiver-card-number",
                                    --EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/ClientFio/Last') as "payer-last-name",
                                    --EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/ClientFio/First') as "payer-first-name",
                                    --EXTRACTVALUE(P.XML_DATA_Q,'/*/Body/ClientFio/Middle') as "payer-middle-name",
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/Head/MessUID/MessageId') as EXTERNAL_ID,
                                    case EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrCode')
                                        when '0' then ''
                                        when '-400' then '������ ���������, �������� �� ����� ���� ���������'
                                        when '-417' then '������������ �������'
                                        when '-428' then '����� � �� ����� ������������ ��������� �������'
                                        when '-601' then '����� ���� �������� �����'
                                        else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                    end as REFUSING_REASON,
                                    --EXTRACTVALUE(P.XML_DATA_A,'/*/Body/MessageParam/DateClose') as "DateClose",
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/Body/MessageParam/Percent') as "Percent",
                                    --EXTRACTVALUE(P.XML_DATA_A,'/*/Body/MessageParam/QuantityDays') as "QuantityDays",
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/Body/Course') as "Course",
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/Body/SrcSumma') as "debit-summa",
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/Body/DstSumma') as "credit-summa",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debit/Balance/Value') as "Balance"
                              from dual)
                loop
                    debitAmount := getAmount(PEF."debit-summa");
                    creditAmount := getAmount(PEF."credit-summa");
                    if ( debitAmount is null and creditAmount is null ) then
                        debitAmount := getAmount(PEF."Balance");
                        if ( debitAmount is null ) then
                            debitAmount:=getAmount('0,00');
                        end if;
                    end if;
                    fromResourceCurrency := PEF."payer-account-currency";
                    toResourceCurrency := PEF."receiver-account-currency";

                    -- �������� ������� ����� ���������� � � ������
                    if (creditAmount is null) then
                        toResourceCurrency := null;
                    else
                        if (toResourceCurrency is null) then
                            toResourceCurrency := getAccountCurrencyCod(PEF."receiver-account");
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
                            fromResourceCurrency := getAccountCurrencyCod(PEF."payer-account");
                            if ( fromResourceCurrency = '' ) then
                                raise_application_error(-20001, '���������� ���������� ������ ����� ��������, �������� �'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;

                    if (PEF."receiver-account-type"!='' and PEF."receiver-account-type"='Deposit') then
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        resourceName:= PEF."receiver-account";
                    else
                        tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                        resourceName:= PEF."receiver-card-number";
                    end if;

                    comissionCurrency:=null;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, null, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, null, PEF."payer-account-name", resourceName, PEF."payer-full-name", debitAmount, PEF.EXTERNAL_ID, null, 'OnLinePaymentStateMachine', PEF."payer-account", P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    if (PEF."payer-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-currency', 'string', fromResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-currency', 'string', toResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-type', 'string', tempValue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'convertion-rate', 'decimal', PEF."Course", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-buy-rate-from', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-buy-rate-to', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-sale-rate-from', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'credit-sale-rate-to', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-buy-rate-from', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-buy-rate-to', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-sale-rate-from', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'debet-sale-rate-to', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount', 'string', 'charge-off-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'national-currency', 'string', 'RUB', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'recalculated-amount-changed', 'boolean', 'false', '0');
                end loop;

            elsif (P.KIND = 'LOATR') then -- ���������� ��������� - ����� �������� ������� H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:='H';

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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF."GROUND", loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '1', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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

            elsif (P.KIND = 'F0502' and codTB != 18 ) then -- ������ ����� ����

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                currencyCode := 'RUB';
                operationType:= 'P';

                curentConvertObject:='������ ���. ����� ������� '||P.KIND;
                for PEF in (select EXTRACTVALUE(P.XML_DATA_A, '/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/BIK') as "receiver-bic",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Debet/AccInfo/Valute') as "payer-account-currency",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Debet/AccInfo/CODAccountName') as "payer-account-name",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/CorAcc') as "receiver-cor-account",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/Name') as "receiver-bank",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Account') as "receiver-account-internal",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/FirmInn') as "receiver-inn",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Name') as RECEIVER_NAME,
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/UniqNum4000') as "UniqNum4000",
                                   -- ����� ����
                                   EXTRACTVALUE(P.XML_DATA, '/*/Purpose') as GROUND,
                                   TO_NUMBER(EXTRACTVALUE(P.XML_DATA, '/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Account') as RECEIVER_ACCOUNT,
                                   EXTRACTVALUE(P.XML_DATA_Q, '/*/messageId') as EXTERNAL_ID,
                                   case
                                       when (SUBSTR(P.DOC_VERSION,2,1)='1' or (SUBSTR(P.DOC_VERSION,2,1)='2' and to_number(SUBSTR(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then EXTRACTVALUE(P.XML_DATA_A, '/*/error_offline_a/message')
                                       else EXTRACTVALUE(P.XML_DATA_A, '/*/Head/Error/ErrMes')
                                   end as REFUSING_REASON,
                                   EXTRACT(P.XML_DATA, '/*/AddInfo') as ADDINFO,
                                   EXTRACTVALUE(P.XML_DATA_Q, '/*/executePayment_q/alias') as "alias",
                                   EXTRACTVALUE(P.XML_DATA_Q, '/*/executePayment_q/uniqueNumber') as "uniqueNumber"
                              from dual)
                loop
                    fromResourceCurrency := PEF."payer-account-currency";
                    if (fromResourceCurrency is null) then
                        fromResourceCurrency := currencyCode;
                    end if;

                    if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                       resourceName:= P.PAYER_ACCOUNT;
                    elsif (P.PAYER_CARD is not null) then
                       resourceName:= P.PAYER_CARD;
                    end if;

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, fromResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                       tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'false', '0');
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'true', '0');
                       resourceName:= P.PAYER_ACCOUNT;
                    elsif (P.PAYER_CARD is not null) then
                       tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'true', '0');
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'false', '0');
                       resourceName:= P.PAYER_CARD;
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', PEF."payer-account-name", '0');

                    if (PEF."payer-account-name" is not null) then
                        resourceName:= P.PAYER_CARD ||' "'||PEF."payer-account-name"||'"';
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', tempValue, '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', '', '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF."receiver-bic", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', PEF."receiver-cor-account", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-description', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', PEF.RECEIVER_NAME, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', PEF."receiver-account-internal", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', PEF."receiver-bic", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', PEF."receiver-bank", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', PEF."receiver-cor-account", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', PEF."receiver-inn", '0');
                    if (PEF."uniqueNumber" is not null)  then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', ltrim(PEF."uniqueNumber",'0')||'|'||adapterCPFL, '0');
                    elsif (PEF."uniqueNumber" is  null and PEF."alias" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '', '0');
                    else
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '$NCRC180804$|'||adapterCPFL, '0');
                    end if;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', PEF.RECEIVER_NAME, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', paymentState, '0');

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', PEF.EXTERNAL_ID, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'false', '0');

                    curentConvertObject:='������ � ���������� ���� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    addExtendedFields(PEF.ADDINFO, paymentID, '0');

                end loop;

            elsif (P.KIND = 'F0502' and codTB=18) then -- ������ ����� (������ ��������� � �������� ������� H ���-��� � ������� ��� ���� ����� ��� �������)
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;

                    if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                       tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                       resourceName:= P.PAYER_ACCOUNT;
                    elsif (P.PAYER_CARD is not null) then
                       tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                       resourceName:= P.PAYER_CARD;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', null);

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
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

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
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
                                   EXTRACTVALUE(P.XML_DATA,'/*/PayeeService/ServiceCode') as "ServiceCode",
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
                        fromResourceCurrency := 'RUB';
                    end if;
                    curentConvertObject:='����� ���������� ����� '||PEF."receiverId"||'|'||PEF."ServiceCode";
                    -- ���� ���������� �����
                    temp_reserv := PEF."receiverId";
                    if (PEF."ServiceCode" is null) then
                        select ID, EXTERNAL_ID into temp, tempValue from SERVICE_PROVIDERS where CODE = PEF."receiverId" and BILLING_ID = billingID and IS_ALLOW_PAYMENTS=1 and rownum<2;
                    else
                        select SP.ID, SP.EXTERNAL_ID into temp, tempValue
                        from SERVICE_PROVIDERS SP
                        left join FIELD_DESCRIPTIONS FD on FD.RECIPIENT_ID=SP.ID
                        where SP.BILLING_ID=billingID and SP.CODE=PEF."receiverId" and FD.INITIAL_VALUE=PEF."ServiceCode" and SP.IS_ALLOW_PAYMENTS='1';
                    end if;

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:='RUB';
                    end if;
                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' ��� �'||P.DOC_NUMBER;
                    if (P.AMOUNT>999999999999999 and paymentState='REFUSED') then
                        --raise_application_error(-20001, '����� ������� ������ ����������:'||P.AMOUNT);
                        amountTemp:=0;
                    else
                        amountTemp:=P.AMOUNT;
                    end if;
                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' ��� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, amountTemp, P.OPERATION_VALUTE, 0, '0', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount','string', 'destination-field-exact', '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'provider-billing-code', 'string', adapterIQW, '0');

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
            
            elsif (P.KIND = 'F0506') then --�������� � ���� 

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                currencyCode := 'RUB';
                operationType:= 'P';

                for PEF in (select 
                                extractvalue(P.XML_DATA, '/*/AuthorizationCode') as authorization_code, 
                                to_number(extractvalue(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as commission,                               
                                extractvalue(P.XML_DATA, '/*/DebetCard/CardNumber') as card_number,
                                extractvalue(P.XML_DATA, '/*/DebetCard/NameOfAccount') as payer_account_name,
                                substr(extractvalue(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as card_date_close,
                                extractvalue(P.XML_DATA, '/*/DebetCard/AccInfo/Valute') as payer_account_currency,
                                extractvalue(P.XML_DATA, '/*/Payee/Name') as receiver_name,
                                extractvalue(P.XML_DATA, '/*/Payee/BillingSystemCode') as billing_system_code,
                                extractvalue(P.XML_DATA, '/*/PayeeService/Inn') as receiver_inn,
                                extractvalue(P.XML_DATA, '/*/PayeeService/Bank/Name') as receiver_bank,
                                extractvalue(P.XML_DATA, '/*/PayeeService/Bank/BIK') as receiver_bank_bik,
                                extractvalue(P.XML_DATA, '/*/PayeeService/Account') as receiver_account,        
                                --extractvalue(P.XML_DATA, '/*/PayeeService/Id') as receiver_service_id, -- ��� ������ � �������                       
                                --extractvalue(P.XML_DATA, '/*/PayeeService/PayeeCode') as receiver_code, -- ��� ���������� � �������
                                extractvalue(P.XML_DATA, '/*/PayeeService/ServiceName') as receiver_service_name,                                
                                extractvalue(P.XML_DATA, '/*/PayeeService/KPP') as receiver_kpp,
                                extractvalue(P.XML_DATA, '/*/PayeeService/CorrAccount') as receiver_corr_account,
                                extractvalue(P.XML_DATA_A, '/*/Head/Error/ErrMes') as refusing_reason,
                                extractvalue(P.XML_DATA_Q, '/*/Head/MessUID/MessageId') as external_id,
                                xmltransform(P.XML_DATA, cardBillingXSLT) as add_info
                            from dual)
                loop

                    fromResourceCurrency := PEF.payer_account_currency;
                    if (fromResourceCurrency is null) then
                        fromResourceCurrency := currencyCode;
                    end if;

                    resourceName:= P.PAYER_CARD;

                    if (PEF.commission is null or PEF.commission='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, '', loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.commission, clientFullName, PEF.receiver_account, PEF.RECEIVER_NAME, null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, fromResourceCurrency, 0, '0', 0, to_clob(PEF.add_info), comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF.payer_account_name, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF.payer_account_name, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', PEF.card_date_close, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', PEF.payer_account_name, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', PEF.payer_account_name, '0');

                    if (PEF.payer_account_name is not null) then
                        resourceName:= P.PAYER_CARD ||' "'||PEF.payer_account_name||'"';
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF.authorization_code, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_�HECK', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'admissionDate', 'string', TO_CHAR(P.ADMISSION_DATE, 'yyyy-mm-dd'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount', 'string', P.AMOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountCurrency', 'string', fromResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountInternal', 'string', P.AMOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'authorizeCode', 'string', PEF.authorization_code, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'chargeOffDate', 'string', TO_CHAR(P.ADMISSION_DATE, 'yyyy-mm-dd'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'codeService', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'commission', 'string', PEF.commission, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', '', '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF.receiver_bank, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF.receiver_bank_bik, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', PEF.receiver_corr_account, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-description', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF.receiver_inn, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', PEF.receiver_kpp, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', PEF.receiver_name, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', PEF.receiver_account, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', PEF.receiver_bank_bik, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', PEF.receiver_bank, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', PEF.receiver_corr_account, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', PEF.receiver_inn, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '', '0'); --TODO?
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', PEF.receiver_kpp, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', PEF.receiver_name, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', '', '0'); --TODO?
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', paymentState, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', PEF.external_id, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'provider-billing-code', 'string', PEF.billing_system_code, '0');

                    --������ ���. �����
                    for extf in ( select t.name_bs, t.field_value
                                       from xmltable('/*/ClientReqs/ESNbpPaymentReq'
                                          passing P.XML_DATA
                                          columns 
                                             name_bs varchar2(6) path '/ESNbpPaymentReq/NameBS',
                                             field_value varchar2(6) path '/ESNbpPaymentReq/EnteredData') t 
                                )
                    loop
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, extf.name_bs, 'string', extf.field_value, '0');                        
                    end loop;

                end loop;

            elsif (P.KIND = 'LOAPT' and codTB = 18) then -- ���������� ��������� �� ������ ����� - ����� �������� ������� H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
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
                                   NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/AddInfo'), EXTRACTVALUE(P.XML_DATA_Q, '/*/Body/Form190Info/Purpose')) as "GROUND",
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:='RUB';
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF."GROUND", loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, '', departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."jur-receiver-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '1', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
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

            elsif (P.KIND = 'LOAPT' and codTB != 18) then -- ���������� ��������� �� ������ ����� - ����� ������� ��. ���� H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                currencyCode := 'RUB';
                operationType:= 'P';

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
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Beneficiary/UniqNum4000') as "uniq-num-4000",
                                   -- ���. ���� ����������� ���������
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                   NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'), '10') as "priority",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                   NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/AddInfo'), EXTRACTVALUE(P.XML_DATA_Q, '/*/Body/Form190Info/Purpose')) as "GROUND",
                                   EXTRACTVALUE(P.XML_DATA, '/*/PaymentProperty/CanChangeTarif/Value') as "can-change-tarif",
                                   EXTRACTVALUE(P.XML_DATA, '/*/PaymentProperty/Tarif') as "tarif",
                                   EXTRACTVALUE(P.XML_DATA, '/*/PaymentProperty/TariffName') as "tarif-name",
                                   -- ����� ����
                                   TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                   EXTRACTVALUE(P.XML_DATA,'/*/RecAcc/Account') as RECEIVER_ACCOUNT,
                                   EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                   EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes') as REFUSING_REASON,
                                   EXTRACT(P.XML_DATA, '/*/Beneficiary/AddInfo') as ADDINFO
                              from dual)
                loop
                    fromResourceCurrency := PEF."payer-account-currency";
                    if (fromResourceCurrency is null) then
                        fromResourceCurrency := currencyCode;
                    end if;

                    if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                       resourceName:= P.PAYER_ACCOUNT;
                    elsif (P.PAYER_CARD is not null) then
                       resourceName:= P.PAYER_CARD;
                    end if;

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=fromResourceCurrency;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."jur-receiver-name", null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, fromResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                       tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'false', '0');
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'true', '0');
                       resourceName:= P.PAYER_ACCOUNT;
                    elsif (P.PAYER_CARD is not null) then
                       tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'true', '0');
                       insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'false', '0');
                       resourceName:= P.PAYER_CARD;
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', PEF."payer-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', PEF."payer-account-name", '0');

                    if (PEF."payer-account-name" is not null) then
                        resourceName:= P.PAYER_CARD ||' "'||PEF."payer-account-name"||'"';
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', tempValue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', tempValue, '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', '', '0');
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', PEF."receiver-bank", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  PEF."receiver-bic", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', PEF."receiver-cor-account", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-description', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', PEF."receiver-inn", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', PEF."jur-receiver-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', PEF."receiver-account-internal", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', PEF."receiver-bic", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', PEF."receiver-bank", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', PEF."receiver-cor-account", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', PEF."receiver-inn", '0');

                    if (PEF."uniq-num-4000" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', ltrim(PEF."uniq-num-4000",'0')||'|'||adapterCPFL, '0');
                    else
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '', '0');
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', PEF."jur-receiver-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', paymentState, '0');
                    -- ��� ���� ����������� ���������:
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date' ,'date', PEF."end-date", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type' ,'string', PEF."event", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day' ,'long', PEF."payment-dates", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority' ,'long', PEF."priority", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date' ,'date', PEF."begin-date", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type' ,'string', PEF."sum-type", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-first-payment-date' ,'date', '', '0');

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', PEF.EXTERNAL_ID, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'provider-billing-code', 'string', adapterCPFL, '0');

                    if (PEF.ADDINFO is not null) then
                        curentConvertObject:='������ � ���������� ���� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                        addExtendedFields(PEF.ADDINFO, paymentID, '1');

                        curentConvertObject:='���������� ���� "AckClientBankCanChangeSumm" ���� � CLOB'||P.KIND||' �������� �'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AckClientBankCanChangeSumm', 'string', PEF."can-change-tarif", '0');

                        curentConvertObject:='���������� ���� "�������� ������" ���� � CLOB'||P.KIND||' �������� �'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tariffValue', 'string', PEF."tarif", '0');

                        curentConvertObject:='���������� ���� "�����" ���� � CLOB'||P.KIND||' �������� �'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'ServiceKind', 'string', PEF."tarif-name", '0');
                    end if;

                end loop;

            elsif (P.KIND = 'F2001') then -- ��������� �� ������ ����������	O

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LossPassbookApplication';
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
                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:='RUB';
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_ACCOUNT, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    resourceName:='';
                    if (PEF."deposit-account-name" is not null) then
                        resourceName:= P.PAYER_ACCOUNT ||' "'||PEF."deposit-account-name"||'"';
                    else
                        resourceName:= P.PAYER_ACCOUNT;
                    end if;

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'BlockingCardClaim';
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
                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:='RUB';
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

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

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-type', 'string', PEF."card-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'reason', 'string', tempVAlue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'externalId', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-name', 'string', PEF."card-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'full-name', 'string', clientFullName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-number', 'string', P.PAYER_CARD, '0');
                end loop;

            elsif (P.KIND = 'FC201') then -- �������� ���������� �����	Q

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS where "NAME" = 'BlockingCardClaim';
                operationType:= 'Q';

                curentConvertObject:='������ ���. ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                for PEF in ( select EXTRACTVALUE(P.XML_DATA_Q,'/*/POSGATE_MSG/WAY4_REQUEST/PRO_CODE') as "reason",
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/POSGATE_MSG/WAY4_REQUEST/PAN') as "card-number",
                                    -- ����� ����
                                    EXTRACTVALUE(P.XML_DATA,'/*/Purpose') as GROUND,
                                    TO_NUMBER(EXTRACTVALUE(P.XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as COMMISSION,
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/messageId') as EXTERNAL_ID,
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/WAY4_ERROR/ERR_MESS') as REFUSING_REASON
                             from dual )
                loop
                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:='RUB';
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'full-name', 'string', clientFullName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-number', 'string', PEF."card-number", '0');
                end loop;

            elsif (P.KIND in ('SZPAY')) then -- ��������� ������ ������������� �� ������� T

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LoanPayment';
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
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[5]/Sum/Money/Value') as "loan-detail5-amount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[5]/AccountId') as "loan-detail5-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[6]/Sum/Money/Value') as "loan-detail6-amount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[6]/AccountId') as "loan-detail6-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[7]/Sum/Money/Value') as "loan-detail7-amount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[7]/AccountId') as "loan-detail7-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[8]/Sum/Money/Value') as "loan-detail8-amount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[8]/AccountId') as "loan-detail8-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[9]/Sum/Money/Value') as "loan-detail9-amount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[9]/AccountId') as "loan-detail9-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[10]/Sum/Money/Value') as "loan-detail10-amount",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Credit/Detail[10]/AccountId') as "loan-detail10-name",
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=PEF."from-resource-currency";
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, PEF."from-resource-currency", departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."loan-account-number", '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

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
                    if (PEF."loan-detail5-name" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail5-name"), 'decimal', PEF."loan-detail5-amount", '0');
                    end if;
                    if (PEF."loan-detail6-name" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail6-name"), 'decimal', PEF."loan-detail6-amount", '0');
                    end if;
                    if (PEF."loan-detail7-name" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail7-name"), 'decimal', PEF."loan-detail7-amount", '0');
                    end if;
                    if (PEF."loan-detail8-name" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail8-name"), 'decimal', PEF."loan-detail8-amount", '0');
                    end if;
                    if (PEF."loan-detail9-name" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail9-name"), 'decimal', PEF."loan-detail9-amount", '0');
                    end if;
                    if (PEF."loan-detail10-name" is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, getCreditFieldName(PEF."loan-detail10-name"), 'decimal', PEF."loan-detail10-amount", '0');
                    end if;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'total-payment-amount', 'decimal', replace(P.AMOUNT,',','.'), '0');
                end loop;

            elsif (P.KIND in ('LOACR')) then -- �������� ����������� ��������� �� ������ ������� T

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LoanPayment';
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=P.OPERATION_VALUTE;
                    end if;

                    curentConvertObject:='���������� �������� ����� �������: '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, P.OPERATION_VALUTE, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."loan-account-number", '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '1', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    if (PEF."from-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."from-account-name"||'"';
                    end if;

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;

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

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, '� ������� ���� ������������� ������� ���. �'||P.DOC_NUMBER||' ��������: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RefuseLongOffer';
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

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=currencyCode;
                    end if;

                    curentConvertObject:='���������� �������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', PEF."payer-resource", P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '1', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    if (PEF."receiver-resource-type" = 'Deposit') then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource-type', 'string', 'ACCOUNT', '0');
                    else
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource-type', 'string', 'CARD', '0');
                    end if;

                    curentConvertObject:='���������� �������������� ����� ������� '||P.KIND||' �������� �'||P.DOC_NUMBER;
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

        curentConvertObject:='��������� ������� �������� (������ �����)';
        -- ���� ����� ORDER_IND ��� ������� �������
        select nvl(max(ORDER_IND)+1,1) into temp from BUSINESS_DOCUMENTS_RES where BUSINESS_DOCUMENT_ID in
        (
            select ID from BUSINESS_DOCUMENTS where LOGIN_ID=loginID and STATE_CODE='TEMPLATE'
        );
        -- ��������� ����� ������ �������/������� E
        for T_CARD in (select ID, TEMPLATE_NAME, CLIENT_ID, CARD_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME, RECEIVER_ACCOUNT, nvl(logonCardNumber, (select CL_PRODUCT from V_CARDS@promLink where CLIENT_ID = tc.CLIENT_ID and rownum < 2 )) as PAYER_CARD from V_TEMPLATES_CARD@promLink tc where (CLIENT_ID = hextoraw(SBOL_ID)) )
        loop
            if ( checkAccountsOwn(idSBOL, loginID, T_CARD.RECEIVER_ACCOUNT, 'C' )='O' ) then
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                operationType:= 'E';

                curentConvertObject:='������� �������� � ����� (�������)';
                -- ��������� ������
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                    values (paymentID, '', loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_CARD.PAYER_LNAME||' '||T_CARD.PAYER_FNAME||' '||T_CARD.PAYER_MNAME, T_CARD.RECEIVER_ACCOUNT, '', null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB', null);

                curentConvertObject:='������� ���. ����� �������� � ����� (�������)';
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', T_CARD.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource', 'string', T_CARD.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', nvl(T_CARD.CARD_CUR, 'RUB'), '0');

            else
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';

                curentConvertObject:='������� �������� �� ����� (�������)';
                -- ��������� ������
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME)
                    values (paymentID, '', loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_CARD.PAYER_LNAME||' '||T_CARD.PAYER_FNAME||' '||T_CARD.PAYER_MNAME, T_CARD.RECEIVER_ACCOUNT, '', null, '', null, 'PaymentStateMachine', T_CARD.PAYER_CARD, null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB');

                curentConvertObject:='������� �������������� ����� �������� �� ����� (�������)';
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourCard', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'true', '0');

                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', T_CARD.PAYER_CARD, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', 'RUB', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', T_CARD.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-first-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-patr-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-surname', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'ph', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'register-string', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', nvl(T_CARD.CARD_CUR, 'RUB'), '0');

            end if;

            curentConvertObject:='������� ������� (������� � �����)';
            insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, nvl( T_CARD.TEMPLATE_NAME, '������� �� ����� '||regexp_replace(T_CARD.RECEIVER_ACCOUNT, '^(\d{4})(.{1,})(\d{5})$', '\1**** ***\3' )), '1', temp);
            temp:= temp+1;
        end loop;

        for T_ACC in ( select CLIENT_ID, TEMPLATE_NAME, RECEIVER_NAME, RECEIVER_ACCOUNT, ACC_NAME, ACC_CUR, INN, AMOUNT, GROUND, IS_NOT_SBER, BIK, CORACC, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME from V_TEMPLATES_ACCOUNT@promLink where (CLIENT_ID = hextoraw(SBOL_ID)) )
        loop
            getFIO(T_ACC.RECEIVER_NAME, firstName, patrName, surName);
            if (T_ACC.AMOUNT is null) then
                currencyCode := null;
            else
                currencyCode := NVL(T_ACC.ACC_CUR,'RUB');
            end if;

            operationType:= 'H';

            if (getReciverType(T_ACC.RECEIVER_ACCOUNT)='P') then

                select ID into paymentFormID from PAYMENTFORMS where NAME = 'RurPayment';

                curentConvertObject:='������� �������� �� ���� (�������)';
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME)
                    values (paymentID, T_ACC.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', currencyCode, departmentID, temp, '1', '0', operationType, systimestamp, '', T_ACC.PAYER_LNAME||' '||T_ACC.PAYER_FNAME||' '||T_ACC.PAYER_MNAME, T_ACC.RECEIVER_ACCOUNT, T_ACC.RECEIVER_NAME, T_ACC.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB');

                curentConvertObject:='������� �������������� ����� �������� �� ����� (�������)';
                if (T_ACC.IS_NOT_SBER='0' or T_ACC.IS_NOT_SBER is null) then
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                else
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
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
            else

                select ID into paymentFormID from PAYMENTFORMS where NAME = 'JurPayment';

                curentConvertObject:='������� �������� �� ���� (�������)';
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                    values (paymentID, T_ACC.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_ACC.PAYER_LNAME||' '||T_ACC.PAYER_FNAME||' '||T_ACC.PAYER_MNAME, T_ACC.RECEIVER_ACCOUNT, T_ACC.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, T_ACC.AMOUNT, currencyCode, 0, '0', 0, null, null, 'ERIB', null);

                if (T_ACC.IS_NOT_SBER='0' or T_ACC.IS_NOT_SBER is null) then
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'ourAccount', '0');
                else
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                end if;

                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'currency', 'string', T_ACC.ACC_CUR, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', T_ACC.RECEIVER_NAME, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-first-payment-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day', 'long', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-percent', 'decimal', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority', 'long', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'national-currency', 'string', 'RUB', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', T_ACC.RECEIVER_ACCOUNT, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', T_ACC.BIK, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', T_ACC.CORACC, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', T_ACC.INN, '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '000000000', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'jur', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-document-date', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-document-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-ground', 'string', '0', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-kbk', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-okato', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-payment', 'string', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period', 'string', '��.', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period1', 'string', '��', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period2', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period3', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-status', 'string', '02', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-type', 'string', '0', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', T_ACC.ACC_CUR, '0');
            end if;

            curentConvertObject:='������� ������� (������� �� �����)';
            insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_ACC.TEMPLATE_NAME, '1', temp);
            temp:= temp+1;
        end loop;

        if (codTB!=18) then
            for T_PAYMENT in ( select CLIENT_ID, TEMPLATE_NAME, NEWNUM, RECEIVER_COR_ACC, RECEIVER_NAME, RECEIVER_ACCOUNT, RECEIVER_INN, UNIQ_NUM, ACC_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME,
                                    TO_NUMBER(EXTRACTVALUE(XML_DATA,'/*/Summa/Value'),'9999999999999999999.99') as AMOUNT,
                                    EXTRACTVALUE(XML_DATA, '/*/Bank/BIK') as "receiver-bic",
                                    EXTRACTVALUE(XML_DATA, '/*/Bank/Name') as "receiver-bank",
                                    EXTRACTVALUE(XML_DATA, '/*/Purpose') as GROUND,
                                    EXTRACT(XML_DATA, '/*/AddInfo') as ADDINFO
                               from V_TEMPLATES_PAYMENT@promLink where (CLIENT_ID = hextoraw(SBOL_ID)) )
            loop
                if (T_PAYMENT.ADDINFO is not null) then -- ���� ���� ��� ���� � �������.

                    select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                    currencyCode := 'RUB';
                    operationType:= 'P';

                    if (T_PAYMENT.AMOUNT is null) then
                        currencyCode := null;
                    else
                        currencyCode := NVL(T_PAYMENT.ACC_CUR,'RUB');
                    end if;

                    -- ��������� ������
                    curentConvertObject:='������� ������� (�������)';
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, T_PAYMENT.AMOUNT, currencyCode, 0, '0', 0, null, null, 'ERIB', null);

                    curentConvertObject:='���������� �������������� ����� ������� (������� ��������)';
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_�HECK', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'admissionDate', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount', 'string', T_PAYMENT.AMOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountCurrency', 'string', currencyCode, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountInternal', 'string', T_PAYMENT.AMOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'authorizeCode', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'chargeOffDate', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'codeService', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'commission', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentDate', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentNumber', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'nameService', 'string', '', '0');-- TODO
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', T_PAYMENT.RECEIVER_INN, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', T_PAYMENT.RECEIVER_NAME, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', T_PAYMENT.RECEIVER_ACCOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', T_PAYMENT."receiver-bic", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', T_PAYMENT."receiver-bank", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', T_PAYMENT.RECEIVER_COR_ACC, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', T_PAYMENT.RECEIVER_INN, '0');

                    if (T_PAYMENT.UNIQ_NUM is not null) then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', ltrim(T_PAYMENT.UNIQ_NUM,'0')||'|'||adapterCPFL, '0');
                    else
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '', '0');
                    end if;

                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', T_PAYMENT.RECEIVER_NAME, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', 'TEMPLATE', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'provider-billing-code', 'string', adapterCPFL, '0');

                    curentConvertObject:='������ � ���������� ���� ����� � �������';
                    addExtendedFields(T_PAYMENT.ADDINFO, paymentID, '0');

                else
                    select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'JurPayment';
                    currencyCode := 'RUB';
                    operationType:= 'H';

                    if (T_PAYMENT.AMOUNT is null) then
                        currencyCode := null;
                    else
                        currencyCode := NVL(T_PAYMENT.ACC_CUR,'RUB');
                    end if;

                    -- ��������� ������
                    curentConvertObject:='������� ������� (�������)';
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, T_PAYMENT.AMOUNT, currencyCode, 0, '0', 0, null, null, 'ERIB', null);

                    curentConvertObject:='���������� �������������� ����� ������� (������� ��������)';
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'currency', 'string', T_PAYMENT.ACC_CUR, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-sum-modify', 'boolean', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'jur-receiver-name', 'string', T_PAYMENT.RECEIVER_NAME, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-end-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-event-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-first-payment-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-pay-day', 'long', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-percent', 'decimal', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-priority', 'long', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-start-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'long-offer-sum-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'national-currency', 'string', 'RUB', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account-internal', 'string', T_PAYMENT.RECEIVER_ACCOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-address', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', T_PAYMENT."receiver-bank", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string', T_PAYMENT.NEWNUM, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', T_PAYMENT.RECEIVER_COR_ACC, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', T_PAYMENT.RECEIVER_INN, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', '000000000', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-type', 'string', 'jur', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-document-date', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-document-number', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-ground', 'string', '0', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-kbk', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-okato', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-payment', 'string', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period', 'string', '��.', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period1', 'string', '��', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period2', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period3', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-status', 'string', '02', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-type', 'string', '0', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', T_PAYMENT.ACC_CUR, '0');

                end if;

                curentConvertObject:='������� ������� (�������)';
                insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_PAYMENT.TEMPLATE_NAME, '1', temp);
                temp:= temp+1;
            end loop;
        else
            for T_PAYMENT in ( select CLIENT_ID, TEMPLATE_NAME, NEWNUM as BIC, RECEIVER_COR_ACC, RECEIVER_NAME, RECEIVER_ACCOUNT, RECEIVER_INN, UNIQ_NUM, ACC_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME,
                                    TO_NUMBER(EXTRACTVALUE(XML_DATA,'/*/Summa/Value'),'9999999999999999999.99') as AMOUNT,
                                    EXTRACTVALUE(XML_DATA, '/*/Bank/BIK') as "receiver-bic",
                                    EXTRACTVALUE(XML_DATA, '/*/Bank/Name') as "receiver-bank",
                                    EXTRACTVALUE(XML_DATA, '/*/Purpose') as GROUND
                               from V_TEMPLATES_PAYMENT@promLink where (CLIENT_ID = hextoraw(SBOL_ID)) )
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
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME)
                    values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', currencyCode, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, T_PAYMENT.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB');

                curentConvertObject:='������� �������������� ����� ������� (�������)';
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'event-object', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-subtype', 'string', 'externalAccount', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
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

                curentConvertObject:='������� ������� (�������)';
                insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_PAYMENT.TEMPLATE_NAME, '1', temp);
                temp:= temp+1;
            end loop;
        end if;

        for T_CPAYMENT in ( select TEMPLATE_NAME, 
                                coalesce(JUR_NAME, extractvalue(XML_DATA, '/*/Payee/Name')) as receiver_name,
                                coalesce(CURRENT_ACCOUNT, extractvalue(XML_DATA, '/*/PayeeService/Account')) as receiver_account, 
                                coalesce(BIC, extractvalue(XML_DATA, '/*/PayeeService/Bic')) as receiver_bank_bik, 
                                coalesce(INN, extractvalue(XML_DATA, '/*/PayeeService/Inn')) as receiver_inn, 
                                coalesce(KPP, extractvalue(XML_DATA, '/*/PayeeService/KPP')) as receiver_kpp, 
                                coalesce(CORR_ACCOUNT, extractvalue(XML_DATA, '/*/PayeeService/CorrAccount')) as receiver_corr_account, 
                                extractvalue(XML_DATA, '/*/PayeeService/Bank/Name') as receiver_bank,
                                coalesce(BS_CODE, extractvalue(XML_DATA, '/*/Payee/BillingSystemCode')) as billing_system_code, 
                                extractvalue(XML_DATA, '/*/PayeeService/ServiceName') as receiver_service_name,                                
                                xmltransform(XML_DATA, cardBillingXSLT) as add_info,
                                extractvalue(XML_DATA, '/*/AuthorizationCode') as authorization_code,                                
                                to_number(extractvalue(XML_DATA,'/*/Commission/Value'),'999999999999999D9999','NLS_NUMERIC_CHARACTERS = ''.,''') as commission,                                
                                nvl(logonCardNumber, (select CL_PRODUCT from V_CARDS@promLink where CLIENT_ID = hextoraw(SBOL_ID) and rownum < 2 )) as PAYER_CARD,
                                XML_DATA
                            from V_TEMPLATES_CPAYMENT@promLink tcp where (CLIENT_ID = hextoraw(SBOL_ID)) 
                          )
        loop

            select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
            currencyCode := 'RUB';
            operationType:= 'P';
            fromResourceCurrency := 'RUB';

            resourceName:= T_CPAYMENT.PAYER_CARD;

            if (T_CPAYMENT.commission is null or T_CPAYMENT.commission='') then
                comissionCurrency:=null;
            else
                comissionCurrency:=fromResourceCurrency;
            end if;

            curentConvertObject:='������� ������� � ����� (�������)';
            select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
            insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                values (paymentID, null, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', '�� ����������� �������� � �������� ��������.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', clientFullName, T_CPAYMENT.RECEIVER_ACCOUNT, T_CPAYMENT.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB', null);

            curentConvertObject:='������� �������������� ����� ������� � ����� (�������)';
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'true', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'false', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-type', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountName', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountSelect', 'string', resourceName, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromAccountType', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource', 'string',  resourceName, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResource', 'string', 'com.rssl.phizic.business.resources.external.CardLink', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', resourceName, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', fromResourceCurrency, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', T_CPAYMENT.authorization_code, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_�HECK', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'admissionDate', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountCurrency', 'string', fromResourceCurrency, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amountInternal', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'authorizeCode', 'string', T_CPAYMENT.authorization_code, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'chargeOffDate', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'codeService', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'commission', 'string', T_CPAYMENT.commission, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'exact-amount', 'string', 'destination-field-exact', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debt-restriction', 'boolean', 'true', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtFixed', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtRestriction', 'string', 'true', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValue-currency', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'debtValueInternal', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentDate', 'string', to_char( sysdate , 'yyyy-mm-dd'), '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'documentNumber', 'string', temp, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-our-bank', 'boolean', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'nameService', 'string', '', '0');-- TODO
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationCode', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationDate', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operationTime', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'payPeriod', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'phoneNumber', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bank', 'string', T_CPAYMENT.receiver_bank, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-bic', 'string',  T_CPAYMENT.receiver_bank_bik, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-cor-account', 'string', T_CPAYMENT.receiver_corr_account, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-description', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-inn', 'string', T_CPAYMENT.receiver_inn, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-kpp', 'string', T_CPAYMENT.receiver_kpp, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-name', 'string', T_CPAYMENT.receiver_name, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverAccount', 'string', T_CPAYMENT.receiver_account, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankCode', 'string', T_CPAYMENT.receiver_bank_bik, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverBankName', 'string', T_CPAYMENT.receiver_bank, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverCorAccount', 'string', T_CPAYMENT.receiver_corr_account, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverDescription', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverINN', 'string', T_CPAYMENT.receiver_inn, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverId', 'string', '', '0'); 
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverKPP', 'string', T_CPAYMENT.receiver_kpp, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiverName', 'string', T_CPAYMENT.receiver_name, '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'recipient', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'state', 'string', 'TEMPLATE', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-account', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-bic', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-bank-corraccount', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'transit-receiver-name', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'unp', 'string', '', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bank-details', 'boolean', 'false', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'bankDetails', 'string', 'false', '0');
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'provider-billing-code', 'string', T_CPAYMENT.billing_system_code, '0');

            --������ ���. �����
            for extf in ( select t.name_bs, t.field_value
                               from xmltable('/*/ClientReqs/ESNbpPaymentReq'
                                  passing T_CPAYMENT.XML_DATA
                                  columns 
                                     name_bs varchar2(6) path '/ESNbpPaymentReq/NameBS',
                                     field_value varchar2(6) path '/ESNbpPaymentReq/EnteredData') t 
                        )
            loop
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, extf.name_bs, 'string', extf.field_value, '0');                        
            end loop;

            curentConvertObject:='������� ������� (������� � ����)';
            insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_CPAYMENT.TEMPLATE_NAME, '1', temp);
            temp:= temp+1;

        end loop;

        -- ����������� ������� �������� ��������
        migrationFlagStatus := eskadm1.migration.EndClientMigration@promLink(idSBOL,100);
        case migrationFlagStatus
            when '-2' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL');
            when '-1' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ��� ������� � ��������� ���������������');
            when  '1' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: ������ �� � ��������� "�������� ������"');
            when  '2' then raise_application_error(-20001, '������ ��� ������������ ����� ��������: �� ������� �������� ��������� � ����� ����� ��������');
            else null;
        end case;

        -- ������� ����������� ������ � ����
        delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL;
        -- ������ ��� ��������� ���������
        if (isNeedUpdate='0') then
            insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, loginID , login, STARTTIME, systimestamp, setNumber,'O','������ ������������ �������, OTHER:'||otherObjectError,'','', codTB);
        elsif (isNeedUpdate='1') then
            insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, loginID , login, STARTTIME, systimestamp, setNumber,'O','������ ������������ �������, ������� ��� ��������� � ��������� ������� LOGIN_ID '||loginID||' (� ����������� ������, �������, �������), OTHER:'||otherObjectError,'','', codTB);
        elsif (isNeedUpdate='2') then
            insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, loginID , login, STARTTIME, systimestamp, setNumber,'O','������ ������������ �������, ������� ��� ��������� � ��������� ������� LOGIN_ID '||loginID||' (������� � �������), OTHER:'||otherObjectError,'','', codTB);
        end if;

        temp:=UnLockMigrateClient(clientFullId);
        if ( temp!=0 ) then
            raise_application_error(-20001, '������ � �������� ������������� ������� � converter_lock');
        end if;
        temp:=0;

        commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            temp:=UnLockMigrateClient(clientFullId);
            if ( temp!=0 ) then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������������� ������� �� converter_lock SBOL_ID: '|| SBOL_ID, codTB);
            end if;

            if (SQLERRM not like 'ORA-20111%') then
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
                -- ������� ����������� ������ � ����
                delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL;
                insert into CONVERTER_CLIENTS values (CurentClient, SBOL_ID, null , login, STARTTIME, SYSTIMESTAMP, setNumber,'E','������ ��� ������� ������� SBOL_ID: '|| SBOL_ID ||', ���: '||curentConvertObject||', OTHER:'||otherObjectError||', ERROR: '||OraError,'','', codTB);
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� ������� SBOL_ID: '|| SBOL_ID ||', ���: '||curentConvertObject||', ERROR: '||OraError, codTB);
                commit;
            else
                migrationFlagStatus := eskadm1.migration.EndClientMigration@promLink(idSBOL,100);
                case migrationFlagStatus
                    when '-2' then otherObjectError := otherObjectError + ' ������: ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL';
                    when '-1' then otherObjectError := otherObjectError + ' ������: ��� ������� � ��������� ���������������';
                    when '1' then otherObjectError := otherObjectError + ' ������: ������ �� � ��������� "�������� ������"';
                    when '2' then otherObjectError := otherObjectError + ' ������: �� ������� �������� ��������� � ����� ����� ��������';
                    else null;
                end case;
                -- ������� ����������� ������ � ����
                delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL;
                insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, null , login, STARTTIME, systimestamp, setNumber,'O','������ ������������ � ���� �� �� ������� ��������������� ������� �� �����, �� �������������� � ������ ����� �������� �� ���������','','', codTB);
                commit;
            end if;
    end ImportClient;

    procedure ImportEmployee(SBOL_ID in varchar2, setNumber in number) is
        suffix varchar2(3);                     -- ������� ��� ������ �����������, ��� ����������
        schemeEmployer number;                  -- ����� ������� ��� ���������� (ID �����)
        adapter varchar2(64);                   -- ������� (������������)
        defaultDepartment number;               -- �������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
        codTB varchar2(5);                      -- �� � �������� �������� ����������� ������
        -- ��������� ���������� ��� ���������� �������� ������
        loginID number :=0;                             -- ������� ID ������ ����������/�������
        temp number :=0;                                -- ���������� ��� �������� � �������
        temp_reserv number :=0;                         -- ���������� ��� �������� � �������
        departmentID number :=0;                        -- ��� �������� id �������� ������������
        authChoice varchar2 (5) :='';                   -- ������ ����� � �������
        confirm�hoice varchar2 (10) :='smsp';           -- ������ ������������� ��������
        curentConvertObject varchar2 (128) :='';		-- ������������ ������� �������������� �������� (��� ����)
        otherObjectError varchar2 (128) :='';			-- ������������ ������� �������������� �������� (��� ����)

        emplFirstName varchar2(32) :='';
        emplPatrName varchar2(32) :='';
        emplSurName varchar2(64) :='';
        STARTTIME timestamp;                -- ����� ������ ������� ����������
        OraError varchar2(1024);            -- ��� � �������� ������ oracle
        CurentEmployer number;              -- id � ������ ����������������� �����������
        idSBOL varchar2(32);                -- id ���������� � ����
        login  varchar2(64);                -- �����
        emplPassword varchar2(32);          -- ������
        emplPasswordHash varchar2(64);      -- ��� ������

    begin
        -- ��������������� �����������
        savepoint start_employee_migration;

        STARTTIME := SYSTIMESTAMP;
        select S_CONVERTER_EMPLOYEES.NEXTVAL into CurentEmployer from dual;
        otherObjectError:='';

        curentConvertObject := '������� ������ �� ���������� �� ���� ����';
        for EMP in ( select ID, USR_NAME, LOGIN, USR_DELETED, USR_LOCKED, USR_LOCK_REASON, TB, OSB, OFFICE, "ROLE" from V_EMPLOYEES@promLink where ID = SBOL_ID )
        loop
            idSBOL:= EMP.ID;
            login:=EMP.LOGIN;

            -- �������� �������������� ����������
            if (EMP.USR_DELETED != '1') then

                codTB:=to_number(ltrim(EMP.TB,'0'));

                curentConvertObject := '���������� �������� ����������';
                select SUFFIX, ADAPTER, DEFAULT_DEPARTMENT into
                        suffix,  adapter, defaultDepartment
                from CONVERTER_CONFIG where COD_TB=codTB;

                curentConvertObject := '���������� ������';
                -- ��������� �� ���������� ������� ����������� � ��������� ����� ��� ����������
                select COUNT(USER_ID) into temp from LOGINS where USER_ID = EMP.LOGIN;
                if ( temp = '0' ) then
                    select S_LOGINS.NEXTVAL into loginID from dual;
                    insert into LOGINS(ID, USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID, LOGON_DATE, LAST_LOGON_DATE, CSA_USER_ID, IP_ADDRESS, LAST_IP_ADDRESS, LAST_LOGON_CARD_NUMBER, IS_MOBILE_BANK_CONNECTED, IS_FIRST )
                        values (loginID, EMP.LOGIN, 'E', 0, '0', '', null, null, '', '', '', '', '0', '1');
                else
                    select S_LOGINS.NEXTVAL into loginID from dual;
                    insert intO LOGINS(ID, USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID, LOGON_DATE, LAST_LOGON_DATE, CSA_USER_ID, IP_ADDRESS, LAST_IP_ADDRESS, LAST_LOGON_CARD_NUMBER, IS_MOBILE_BANK_CONNECTED, IS_FIRST )
                        values (loginID, suffix || EMP.LOGIN, 'E', 0, '0', '', null, null, '', '', '', '', '0', '1');
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
        insert into CONVERTER_EMPLOYEES values (CurentEmployer, idSBOL, loginID , login, emplPassword, STARTTIME, SYSTIMESTAMP, setNumber,'O','��������� ������������ �������, OTHER:'||otherObjectError,'','', codTB);
        commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback to start_employee_migration;
            OraError:=SQLERRM;
            insert into CONVERTER_EMPLOYEES values (CurentEmployer, idSBOL, null, login, emplPassword, STARTTIME, SYSTIMESTAMP, setNumber,'E','������ ��� ������� ���������� Login: '|| login ||' ���: '||curentConvertObject||' OTHER:'||otherObjectError||' ERROR: '||OraError,'','', codTB);
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� ���������� SBOL_ID: '|| SBOL_ID ||' Login: '|| login ||', ���: '||curentConvertObject||', ERROR: '||OraError, codTB);
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
                loginID:=to_number(REC.LOGIN);
                select ID into clientID  from USERS where LOGIN_ID = loginID;
                select USER_ID into login from LOGINS where ID = loginID;
/*
                select ID into loginID from LOGINS where USER_ID = REC.LOGIN;
                select ID into clientID  from USERS where LOGIN_ID = loginID;
                login := REC.LOGIN;
*/
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

                curentConvertObject := '������� ������� ��';
                delete from MB_PAYMENT_TEMPLATE_UPDATES where LOGIN_ID = loginID;

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

                curentConvertObject := '������� ����� � ���';
                delete from PFRLINKS where LOGIN_ID = loginID;

                curentConvertObject := '������� �������� �������';
                delete from SUBSCRIPTIONS where LOGIN_ID = loginID;

                curentConvertObject := '������� ���������� ������';
                delete from LOGIN_BLOCK where LOGIN_ID = loginID;

                curentConvertObject := '������� ����������';
                delete from NOTIFICATIONS where LOGIN = login;

                curentConvertObject := '������� �����';
                delete from LOGINS where ID = loginID;

                -- ������� ����������� ������ � ����
                --delete from CONVERTER_CLIENTS where ID_SBOL = REC.ID_SBOL;
--                update CONVERTER_CLIENTS set ID_ERIB='', TIME_START=STARTTIME, TIME_STOP=SYSTIMESTAMP, STATE='D', STATE_DESCRIPTION='������ �� ������� ������� �� ����' where LOGIN=login;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '������ login: '||login||' ������.', '');
                commit;
             exception when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '������ ��� �������� ������� login: '||login||', ���: '||curentConvertObject||', ERROR: '||OraError, '');
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '������ ��� �������� ��������, ��������� �������� ����������� ERROR: '||OraError||', ��������� ������ ��������� ������ �������� �� ��������', '');
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

                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, '��������� login: '||login||' ������ �������', '');
                commit;
            exception
                when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� �������� ������ ���������� login: '||loginID||', ���: '||curentConvertObject||',ERROR: '||OraError, '');
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� �������� �����������, ��������� ����������� ERROR: '||OraError||'. ��������� ������ ��������� ������ ��� ��������.', '');
            commit;
    end DeleteEmployees;

    -- ��������� ��������
    -- flag:
    --       0 - ��������� ������� �������������� ������
    --       1 - ��������� ����
    -- rgnCode - ��� ������� ����������� ���������
    procedure MigrateClients(flag in Char, rgnCode number, sessionId number) is
        convertDelay number;                    -- �������� ����� �������������
        countForConverting number;              -- �� ������� �������� �������������� (������)
        codTB varchar2(5):='';                  -- ��� ��������, �������� �������� ���������
        countRec number :=0;                  	-- ���-�� ������� � ������
        OraError varchar2(1024);				-- �������� ������ oracle
        isStop char(1);                         -- ������� ������ ��������� ����������
        setNumber number;                       -- ����� ������� ������
        migrateStatus  integer;                 -- ������ ������������ �������
        paymentStatus number;                   -- ������ ��������, � - ����� �������������, ��� ������� �� � ���������; P - ������� � ���������, ������������� ������
        --totalClients number;                  -- ���-�� ���������� �������� ��� ��������
        minId number;
        maxId number;
        idSBOL varchar2(32);
    begin
        -- ��������� ��������� ����������
        select CONVERT_DELAY, COUNT_FOR_CONVERTING, STOP   into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;

        if (flag = '1') then
            -- ��������� ����� ��� ����������� (��� ������� ����)
            delete from CONVERTER_SET;
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, CLIENT_ID, null, 1
                from V_CLIENTS@promLink where (( STATE='OK' or STATE='DR' or STATE='BL') and RGN_CODE=rgnCode and MIG_STATE is null);
        end if;

        select min(ID), max(ID) into minId, maxId from CONVERTER_SET where SESSION_ID=sessionid;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, '������ �������� ����.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ������ �������� ��� ��������, ���-�� �������� = '||(maxId-minId+1), codTB);

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
                        when '-1'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: ��� ������� � ��������� ���������������', codTB);
                        when '1'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: �������� ��� ������', codTB);
                        when '2'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ��� ������� ������� �������� ��� ��������� �������', codTB);
                        when '3'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: � ������� ���� �������� ������', codTB);
                        when '4'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: ������ ��� ��������� MIG_STATE � MIG_TIME', codTB);
                        when '100' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: �� ������ ������� ������ RStyle', codTB);
                        when '101' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' ������ ��� ������������ ����� ��������: ������� ������� ������������ ������ �������', codTB);
                        else insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������������ ����� ��������: '||migrateStatus, codTB);
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
                -- ��������� ��������� ����������
                select CONVERT_DELAY, COUNT_FOR_CONVERTING, STOP   into
                       convertDelay, countForConverting, isStop
                from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;

--                select STOP into isStop from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;
                if ( isStop = '1') then
                    EXIT;
                end if;

            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������� ��� ���������� �������, ��� ������� ������� id '||idSBOL, codTB);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������������� ���������', codTB);
            end if;
            -- ������ ������ �������� �� �����������
            --delete from CONVERTER_SET;
            commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� ��������, ��������� ����������� ����������� ERROR: '||OraError, codTB);
            commit;
    end MigrateClients;

    -- ��������� ���� ����������� ����
    -- flag: 1 - ��������� ����
    --       0 - ��������� ������� �������������� ������ �����������
    -- rgnCode - ��� ������� ����������� ���������
    procedure MigrateEmployees(flag in Char, rgnCode number)  is

        convertDelay number;                    -- �������� ����� �������������
        countForConverting number;              -- �� ������� �������� �������������� (������)
        codTB varchar2(5):='';                  -- ��� ��������
        countRec number :=0;                  	-- ���-�� ������� � ������
        OraError varchar2(1024);				-- �������� ������ oracle
        isStop char(1);                         -- ������� ������ ��������� ����������
        setNumber number;                       -- ����� ������� ������
        --totalEmployees number;
        minId number;
        maxId number;
        idSBOL varchar2(32);
    begin
        -- ��������� ��������� ����������
        select CONVERT_DELAY, COUNT_FOR_CONVERTING, STOP   into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;

        if (flag = '1') then
            -- ��������� ����� ��� ����������� (��� ���������� ����)
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, ID, null, 1 from V_EMPLOYEES@promLink where USR_DELETED != '1' and ltrim(TB,'0') in (select COD_TB from converter_config where RGN_CODE=rgnCode);
        end if;
        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, '������ ����������� ����.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ������ ���������� ��� ��������, ���-�� ����������� = '||(maxId-minId+1), codTB);

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
                select STOP into isStop from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;
                if ( isStop = '1') then
                    EXIT;
                end if;
            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������� ��� ���������� �������, ����� ������� ����������', codTB);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'��������������� ���������', codTB);
            end if;
            -- ������ ������ ����������� �� �����������
            delete from CONVERTER_SET;
            commit;
    exception
        -- �������� ������ ��� �����������, ����� � ��� ������ � ���������� ���������
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'������ ��� ������� �����������, ��������� ����������� ����������� ERROR: '||OraError, codTB);
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

end MigratorMoscow;
