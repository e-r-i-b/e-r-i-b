create or replace package MigratorMoscow is

    -- Удаляем партию клиентов из таблицы converter_set (ищем клиента по логин id) 
    -- список клиентов на удаление формируется вручную
    procedure DeleteClients;

    -- Удаляем партию клиентов из таблицы converter_set (ищем сотрудника по логин id)
    -- список сотрудников на удаление формируется вручную
    procedure DeleteEmployees;

    -- Конвертим клиентов
    -- flag: 0 - конвертим вручную сформированный список
    --       1 - конвертим всех
    -- rgnCode - код региона мигрируемых тербанков
    procedure MigrateClients(flag in Char, rgnCode number); 

    -- Конвертим всех сотрудников СБОЛ
    -- flag: 1 - конвертим всех
    --       0 - конвертим вручную сформированный список
    -- rgnCode - код региона мигрируемых тербанков
    procedure MigrateEmployees(flag in Char, rgnCode number); 
    
    -- получение кол-ва счетов, документов и шаблонов клиента
    procedure getClientObjectsCount(clientID in number, accounts out number, documents out number, templates out number);
    
    -- Функция получения пользовательского пароля
    function getEmplPassword (emplPassword out varchar2, passwordHesh out varchar2) return char;

    -- Функция получения Хэша пароля сотрудника
    function getPasswordHash ( m varchar2 ) return varchar2;

    -- Функция генерит пароль, соответствующий требованиям безопасности банка
    function getPassword return varchar2;
    
    -- Функция для получения имени доп. поля документа оплаты задолженности по кредиту, по имени параметра         
    function getCreditFieldName(detailName in varchar2) return varchar2;

end MigratorMoscow;
/
create or replace package body MigratorMoscow is

cardBillingXSLT constant xmltype:=xmltype('<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:template match="ClientReqs"><xsl:element name="Attributes"><xsl:for-each select="/ClientReqs/ESNbpPaymentReq"><xsl:element name="Attribute"><xsl:element name="NameBS"><xsl:value-of select="NameBS"/></xsl:element><xsl:element name="Description"><xsl:value-of select="Description"/></xsl:element><xsl:element name="NameVisible"><xsl:value-of select="NameVisible"/></xsl:element><xsl:element name="Comment"><xsl:value-of select="Comment"/></xsl:element><xsl:element name="Type"><xsl:value-of select="Type"/></xsl:element><xsl:choose><xsl:when test="Menu/MenuItem"><xsl:element name="Menu"><xsl:for-each select="Menu/MenuItem"><xsl:element name="MenuItem"><xsl:element name="Id"><xsl:value-of select="."/></xsl:element><xsl:element name="Value"><xsl:value-of select="."/></xsl:element></xsl:element></xsl:for-each></xsl:element></xsl:when></xsl:choose><xsl:element name="NumberPrecision"><xsl:value-of select="NumberPrecision"/></xsl:element><xsl:element name="IsRequired"><xsl:choose><xsl:when test="IsRequired"><xsl:value-of select="IsRequired"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsEditable"><xsl:choose><xsl:when test="IsEditable"><xsl:value-of select="IsEditable"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsVisible"><xsl:choose><xsl:when test="IsVisible"><xsl:value-of select="IsVisible"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsMainSum"><xsl:choose><xsl:when test="IsMainSum"><xsl:value-of select="IsMainSum"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsSum"><xsl:choose><xsl:when test="IsSum"><xsl:value-of select="IsSum"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsForBill"><xsl:choose><xsl:when test="IsForBill"><xsl:value-of select="IsForBill"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="IsKey"><xsl:choose><xsl:when test="IsKey"><xsl:value-of select="IsKey"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="RequiredForConfirmation"><xsl:choose><xsl:when test="RequiredForConfirmation"><xsl:value-of select="RequiredForConfirmation"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="SaveInTemplate"><xsl:choose><xsl:when test="SaveInTemplate"><xsl:value-of select="SaveInTemplate"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="HideInConfirmation"><xsl:choose><xsl:when test="HideInConfirmation"><xsl:value-of select="HideInConfirmation"/></xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose></xsl:element><xsl:element name="MaxLength"><xsl:value-of select="AttributeLength/MaxLength"/></xsl:element><xsl:element name="MinLength"><xsl:value-of select="AttributeLength/MinLength"/></xsl:element><xsl:copy-of select="Validators"/><xsl:element name="DefaultValue"><xsl:value-of select="DefaultValue"/></xsl:element><xsl:element name="Error"><xsl:value-of select="Error"/></xsl:element></xsl:element></xsl:for-each></xsl:element></xsl:template></xsl:stylesheet>');

    -- разбираем Ф.И.О. на Фимилию, Имя и Отчество
    procedure getFIO (fullNameOr in varchar2, firstName out varchar2, patrName out varchar2, surName out varchar2)
    is
        str varchar2(128):='';  -- строка для разбора ФИО клиента
        fullName varchar2(128):='';
        i number :=0;
        j number :=0;
    begin
        -- разбираем Ф.И.О.
        firstName :='';
        patrName :='';
        surName :='';
        fullName := trim(fullNameOr);
        i := instr(fullName,' ');
        j := instr(fullName,' ', i+1);
        if (i > '0') then
            surName := substr(fullName, 1, i-1);
            if (i < j) then
                -- получили 3 слова
                firstName := substr(fullName, i+1, j-i-1);
                patrName := substr(fullName, j+1, length(fullName)-i);
            else
                -- получили 2 слова, проверяем может разделитель между словами - "."
                firstName := substr(fullName,i+1, length(fullName)-i);
                j := instr(firstName,'.');
                if (j > 0) then
                    str := firstName;
                    firstName := substr(str, 1,j);
                    patrName := substr(str, j+1, length(str)-j);
                end if;
            end if;
        else
          -- получили только одно слово
          surName := fullName;
        end if;
    end getFIO;

    -- функция возвращает сумму как число c десяичным разделителем ','
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
                when '810' then 'RUR'-- Российский рубль
                when '840' then 'USD'-- Доллар США
                when '978' then 'EUR'-- Евро
                when '826' then 'GBP'-- Фунт стерлингов
                when '756' then 'CHF'-- Швейцарский франк
                when '752' then 'SEK'-- Шведская крона
                when '702' then 'SGD'-- Сингапурский доллар
                when '578' then 'NOK'-- Норвежская крона
                when '392' then 'JPY'-- Йена
                when '208' then 'DKK'-- Датская крона
                when '124' then 'CAD'-- Канадский доллар
                when '036' then 'AUD'-- Австралийский доллар
                else ''
            end;
        end if;
        return '';
    end getAccountCurrencyCod;

    -- Функция получения Хэша пароля сотрудника
    function getPasswordHash ( m varchar2 ) return varchar2 as language java name 'crypto.passwordHash(java.lang.String) return java.lang.String';

    -- Функция генерит пароль, соответствующий требованиям безопасности банка
    function getPassword return varchar2 as language java name 'utils.generate() return java.lang.String';

    -- Функция получения пользовательского пароля
    --  В случае ошибки при генерации пароля, или при получения хэша - выдает исключение ORA-20001
    function getEmplPassword (emplPassword out varchar2, passwordHesh out varchar2) return char is
    begin
        emplPassword := getPassword;
        if ( emplPassword = 'E' ) then
            -- не смогли сгенерить пароль, пробуем сгенерить еще раз
            dbms_lock.sleep(0.005); --  пауза для смены системного времени, иначе получим ту же последовательность, что и в предыдущей генерации
            emplPassword := getPassword;
            if ( emplPassword = 'E' ) then
                -- повторная генерация не не дала результата, генерим исключение
                raise_application_error(-20001, 'Не смогли сгенерировать пароль');
            else
                -- получаем хэш для пароля
                passwordHesh := getPasswordHash(emplPassword);
                if ( passwordHesh = 'E' ) then
                    -- не смогли получить хэш, пробуем еще раз
                    passwordHesh := getPasswordHash(emplPassword);
                    if ( passwordHesh = 'E' ) then
                        -- повторная попытка не не дала результата, генерим исключение: не смогли получить хэш для пароля
                        raise_application_error(-20001, 'Не смогли получить хэш для пароля');
                    end if;
                end if;
            end if;
        else
            -- получаем хэш для пароля
            passwordHesh := getPasswordHash(emplPassword);
            if ( passwordHesh = 'E' ) then
                -- не смогли получить хэш, пробуем еще раз
                passwordHesh := getPasswordHash(emplPassword);
                if ( passwordHesh = 'E' ) then
                    -- повторная попытка не не дала результата, генерим исключение: не смогли получить хэш для пароля
                    raise_application_error(-20001, 'Не смогли получить хэш для пароля');
                end if;
            end if;
        end if;

        return 'O';

    end getEmplPassword;

    --функция проверки документов клиента
    function validateDocuments(documentType in varchar2, documentSeries in varchar2, documentNumber in varchar2) return char is
    begin
        case documentType
            when 'REGULAR_PASSPORT_RF' then
                -- Серия общегражданского паспорта РФ должна состоять из 4 цифр, а номер из 6.
                if ( ( regexp_instr(documentSeries, '^\d{2}\s{0,}\d{2}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{6}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'SEAMEN_PASSPORT' then
                -- Серия паспорта моряка должнна состоять из 2 букв (МФ,РФ,РХ), а номер из 7 цифр.");
                if (( regexp_instr(documentSeries, '^[А-Я]{2}$') = '1' ) and ( documentSeries = 'МФ' or documentSeries='РФ' or documentSeries = 'РХ' ) and ( regexp_instr(documentNumber, '^\d{7}$') = '1')) then
                    return '1';
                else
                    return '0';
                end if;
            when 'RESIDENTIAL_PERMIT_RF' then
                -- Серия вида на жительство РФ должна состоять из 2 цифр, а номер из 7.
                if ( regexp_instr(documentSeries, '^\d{2}$') = '1' and regexp_instr(documentNumber,'^\d{7}$') = '1' ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'FOREIGN_PASSPORT_RF' then
                -- Серия заграничного паспорта должна содержать 2 цифры, а номер 7.
                if ( ( regexp_instr(documentSeries, '^\d{2}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{7}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'IMMIGRANT_REGISTRATION' then
                -- Серия свидетельства о регистрации ходатайства иммигранта о признании его беженцем в сумме с номером должна содержать 25 знаков
                if ( length(documentSeries||documentNumber) = 25 ) then
                    return '1';
                else
                    return '0';
                end if;
            when 'REFUGEE_IDENTITY' then
                -- Серия удостоверения беженца должна состоять из 1 буквы, а номер из 6 цифр
                if ( ( regexp_instr(documentSeries, '^[А-яA-z]{1}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{6}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
/*
            when 'MIGRATORY_CARD' then
                -- Серия заграничного паспорта должна содержать 2 цифры, а номер 7.
                if ( ( regexp_instr(documentSeries, '^\d{2}$') = '1' ) and ( regexp_instr(documentNumber, '^\d{7}$') = '1' ) ) then
                    return '1';
                else
                    return '0';
                end if;
*/
            else return '1';
        end case;
    end validateDocuments;

    -- функция проверки номера телефона клиента на соответствие формату
    function validatePhonNumber(phoneNumber in varchar2) return char is
    begin
        -- шаблон для номера телефона: +7 (код_оператора) номер_телефона;
        -- длина кода оператора 3-5 цифр, длина номера телефона - 5-7 цифр, число цифр в номере, с учетом +7, должно быть равно 11, cкобки обязательны
        if ( regexp_instr(phoneNumber, '^\+7( )?\(((\d{3}\)( )?\d{7})|(\d{4}\)( )?\d{6})|(\d{5}\)( )?\d{5}))$') = '1' ) then
            return '1';
        else
            return '0';
        end if;
    end validatePhonNumber;

    -- функция для приведения номера телефона клиена к формату используемому в системе
    function buildPhonNumber(phoneNumber in varchar2) return varchar2 is
        temp varchar2(32);
    begin
        select regexp_replace(phoneNumber, '\D','') into temp from dual;
        select regexp_replace(temp, '^[78]','') into temp from dual;
        select regexp_replace(temp, '(\d{3})(\d{1,})','+7(\1)\2') into temp from dual;
        return temp;
    end;

    -- функция для получения имени доп. поля документа оплаты задолженности по кредиту, по имени параметра
    function getCreditFieldName(detailName in varchar2) return varchar2 is
    begin
        return case lower(detailName)
            when 'bodydebt' then 'principal-amount'                        -- Сумма основного долга
            when 'procent' then 'interests-amount'                         -- Сумма выплат по процентам
            when 'advancebody' then 'earlyBaseDebtAmount'                  -- Досрочно, в счет основного долга
            when 'overduebody' then 'delayedDebtAmount'                    -- Просроченный основной долг
            when 'overdueprocent' then 'delayedPercentsAmount'             -- Просроченные проценты за пользование кредитом
            when 'accountpay' then 'accountOperationsAmount'               -- Плата за операции по ссудному счету
            when 'overduebodyforfeit' then 'penaltyDelayDebtAmount'        -- Просрочка основного долга (Неустойка за просрочку основного долга)
            when 'overdueprocentforfeit' then 'penaltyDelayPercentAmount'  -- Просрочка процентов (Неустойка за просроч. %%)
            when 'tax' then 'otherCostsAmount' --Сумма судебных и иных расходов по взысканию задолженности
        end;
    end;

    -- функция для получения статуса платежа и описания этого статуса
    function getPaymentState(docStateSBOL in varchar2, paymentState out varchar2, paymentStateDescription out varchar2) return char is
    begin
        case docStateSBOL
            when 'OK' then
                paymentState := 'EXECUTED';
                paymentStateDescription := 'Платеж успешно исполнен банком: денежные средства переведены на счет получателя платежа.';
            when 'RF' then
                paymentState := 'REFUSED';
                paymentStateDescription := 'Отказ в исполнении документа.';
            when 'RC' then
                paymentState := 'RECALLED';
                paymentStateDescription := 'Документ отозван Вами по какой-либо причине.';
            else
                paymentState := '';
                paymentStateDescription := '';
                return 'E';
        end case;
        return 'O';
    end;

    -- функция для получения банковского(для подразделения) активного лимита
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

    -- функция проверки наличия общих счетов в договорах из СБОЛ ЦА и ЕРИБ
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

    -- функция проверки наличия счета/карты в договорах из СБОЛ ЦА и ЕРИБ
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

    -- процедура обновляющая сумму доступную для совершения операций с подтверждением одноразовыми паролями
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

    -- функция определения типа счета (обычный или карточный)
    function getAccountType(tb in number, accName in varchar2) return char is
    begin
        if (tb is not null) then
            return 'O'; -- Обычный счет
        else
            if (accName is null) then
                return 'C'; -- Карточный счет
            end if;
            if ( regexp_instr(lower(accName), 'maestro|visa|mastercard|про100|pro 100')= 0 ) then
                return 'O'; -- Обычный счет
            else
                return 'C'; -- Карточный счет
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
                        if (cntr>60) then -- ждем минуту миграции данного клиента в другой сессии
                            res:=2; -- превышено время ожидания разблокировки клиента
                            exit;
                        end if;
                    else
                        res:=1; -- в процессе блокировки возникли ошибки
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
            return 1; -- ошибки при разблокировке
    end;

    -- функция определения типа получателя (физик или юрик)
    function getReciverType(accNumber in varchar2) return char is
    begin
        if (regexp_instr(accNumber, '^(423|426|40817|40820)\d{1,25}$') = 1) then
            return 'P';
        else
            return 'J';
        end if;
    end;

    -- процедура разбора и добавления доп. полей ЦПФЛ платежа
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

                                    --Вставка доп. полея extended-field... ЦПФЛ в DEF
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

            -- Добавление полея сумма ЦПФЛ в CLOB
            DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>amount</NameBS><Description></Description><NameVisible>Сумма</NameVisible><Comment></Comment><Type>money</Type><IsRequired>true</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>true</IsMainSum><IsSum>true</IsSum><IsForBill>false</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>false</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute>'));

            if (isLongOffer = '1') then
                -- Добавление поля "AckClientBankCanChangeSumm" ЦПФЛ в CLOB
                DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>AckClientBankCanChangeSumm</NameBS><Description></Description><NameVisible>Разрешить изменение суммы платежа при изменении тарифа</NameVisible><Comment></Comment><Type>list</Type><Menu><MenuItem><Id>true</Id><Value>Да</Value></MenuItem><MenuItem><Id>false</Id><Value>Нет</Value></MenuItem></Menu><IsRequired>true</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>true</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>true</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute>'));
                -- Добавление поля "Значение тарифа" ЦПФЛ в CLOB
                DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>tariffValue</NameBS><Description></Description><NameVisible>Значение тарифа</NameVisible><Comment></Comment><Type>string</Type><IsRequired>false</IsRequired><IsEditable>false</IsEditable><IsVisible>false</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>false</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>false</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute>'));
                -- Добавление поля "Тариф" ЦПФЛ в CLOB
                DBMS_LOB.APPEND(CPFLFields, to_clob('<Attribute><NameBS>ServiceKind</NameBS><Description></Description><NameVisible>Тариф</NameVisible><Comment></Comment><Type>string</Type><IsRequired>true</IsRequired><IsEditable>true</IsEditable><IsVisible>true</IsVisible><IsMainSum>false</IsMainSum><IsSum>false</IsSum><IsForBill>true</IsForBill><IsKey>false</IsKey><RequiredForConfirmation>true</RequiredForConfirmation><SaveInTemplate>false</SaveInTemplate><HideInConfirmation>false</HideInConfirmation><MaxLength></MaxLength><MinLength></MinLength><DefaultValue></DefaultValue><Error></Error></Attribute></Attributes>'));
            else
                DBMS_LOB.APPEND(CPFLFields, to_clob('</Attributes>'));
            end if;
            -- Добавление данных в EXTENDED_FIELDS
            update BUSINESS_DOCUMENTS set EXTENDED_FIELDS=CPFLFields where ID=paymentID;
        end if;
    end;

    procedure ImportClient(SBOL_ID in varchar2, setNumber in number) is
        -- параметры для настройки конвертора
        schemeClient number;               -- cхема доступа для клиента (ID схемы)
        adapter varchar2(64);              -- адаптер (наименование)
        adapterCPFL varchar2(64);          -- адаптер (наименование)
        adapterIQW varchar2(64);           -- адаптер (наименование UUID)
        adapterCARD varchar2(64);          -- адаптер (наименование)
        regionID number;                   -- регион в профиле клиента (для привязки услуг)
        defaultDepartment number;          -- одразделение, к которому привязываем сотрудника если не нашли подходящее по ТБ, ОСБ и ВСП (ID)
        defaultDepartmentCARD number;      -- одразделение, к которому привязываем сотрудника если не нашли подходящее по ТБ, ОСБ и ВСП (ID)
        schemeEmployer number;             -- Схема сотрудника, для привязки заблокированных клиентов
        suffix varchar2(3);                -- Суффикс для логина, при совпадении
        billingID number;                  -- ID билинговой системы для платежей (id IQW)
        codTB number;                      -- код мигрируемого Тербанка
        allowedDepartments varchar2(512);  -- коды тербанков, в рамках которых возможно объединение договоров
        -- служебные переменные для временного хранения данных
        loginID number :=0;                             -- текущий ID логина сотрудника/клиента
        temp number :=0;                                -- переменная для расчетов и условий
        temp_reserv number :=0;                         -- переменная для расчетов и условий
        str varchar2(128):='';
        departmentID number :=0;                        -- для хранения id текущего департамента
        adressRegId number:=0;                          -- id адреса регистрации
        adressResId number:=0;                          -- id адреса проживания
        clientID number:=0;                             -- id клиента
        paymentID number:=0;                            -- id платежа, для привязки доп. полей
        paymentFormID number :=0;                       -- id формы платежа
        paymentStateDescription varchar2 (128) :='';    -- описание статуса платежа
        paymentState  varchar2 (32) :='';               -- статус платежа
        clientState char(1) := '';                      -- статус клиента (‘A’ – активный;‘T’ – регистрация и т.д.)
        clientStatus char(1) := '';                     -- статус клиента (‘A’ – активный или ‘D’ – удален)
        authChoice varchar2 (5) :='';                   -- способ входа в систему
        confirmСhoice varchar2 (10) :='smsp';           -- способ подтверждения операции
        tempValue varchar2 (512) := '';                 -- для выставления значений в доп полях платежа
        resourceName varchar2 (512) := '';              -- для выставления значений в доп полях платежа
        currencyCode char(3) := '';						-- валюта платежа
        clientDocType varchar(32):='';					-- тип док-та в нашей системе
        clientDocName varchar(32):='';					-- наименование док-та в нашей системе
        operationType char(1):='';						-- тип платежа, для построения соответствия между типами операций
        isIQWavePayment boolean := false;				-- признак билинговый платеж или нет
        residental char(1):='1';						-- признак резидентности 0 – нерезидент, 1 – резидент
        docVersion char(1):='0';                        -- для проверки версии документа
        creationType varchar2(25);                      -- тип создани клиента
        logonCardNumber varchar2(20);                   -- карта по которой клиент входил в СБОЛ ЦА

        -- для формирования xml в AUTHENTICATION_MODES
        S1 varchar2(200) :='<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"><properties><entry key="simple-auth-choice">';
        S2 varchar2(50) :='</entry><entry key="secure-confirm-choice">';
        S3 varchar2(64) :='</entry>';
        curentConvertObject varchar2 (128) :='';		-- наименование текущей конвертируемой сущности (для лога)
        curentClient number;                            -- текущий импортируемый клиент (ID в таблице импортированных клиентов)
        otherObjectError varchar2 (3000) :='';			-- сообщения о работе мигратора, требующие логирования (тип миграции, поиск подразделения и т.д.)
        STARTTIME timestamp; 							-- время начала импорта сотрудника
        OraError varchar2(1024);						-- описание ошибки oracle
        idSBOL varchar2(32);                            -- id клиента в СБОЛ
        login  varchar2(32);                            -- логин клиента
        pinNumber number;                               -- номер пин конверта
        clientFirstName varchar2(32):='';               -- Имя клиента
        clientPatrName varchar2(32):='';                -- Отчество клиента
        cientSurName varchar2(64):='';                  -- Фамилия клиента
        clientFullName varchar2(256):='';               -- Полное имя
        firstName varchar2(32):='';                     -- Имя получателя
        patrName varchar2(32):='';                      -- Отчество получателя
        surName varchar2(64):='';                       -- Фамилия получателя
        reasonType varchar2(10):='';                    -- тип блокировки
        blockedUntil timestamp(0);                      -- время на которое блокируется клиент
        agreementNumber varchar2(32);                   -- номер договора
        migrationFlagStatus integer;                    -- статус флага миграции
		phoneNumber varchar2(32);                       -- номер телефона клиента
        fromResourceCurrency varchar2(3);               -- Валюта счета списания
        toResourceCurrency varchar2(3);                 -- Валюта счета зачисления
        debitAmount number;                             -- Сумма в валюте счета списания
        creditAmount number;                            -- Сумма в валюте счета зачисления
        clientCreationType varchar2(25):='';            -- Тип создания клиента для которого будем объединять договора
        isNeedUpdate char(1):='0';                      -- Признак необходимости обновления анкеты
        comissionCurrency char(3):='';                  -- Валюта комиссиии в платежах
        amountTemp number;
        bankLimit number;                               -- банковский лимит
        ownLimit number;                                -- индивидуальный лимит

        --параметры оповещений
        distributionId number := 0;
        scheduleId number := 0;
        smsTemplateId number := 0;
        emailTemplateId number := 0;
        subscriptionId number := 0;
        tempFlag number := '0';
        receiverAccountType char(1);
        clientFullId varchar2(256);                     -- идентификатор для блокировки мигриуемого клиента
      
    begin

        savepoint start_client_migration;
        otherObjectError:='';
        STARTTIME := SYSTIMESTAMP;
        select S_CONVERTER_CLIENTS.NEXTVAL into curentClient from dual;
        curentConvertObject := 'Выборка данных по клиенту из базы СБОЛ';
        -- Конвертирование клиентов
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
                            else 'M' -- если пол не указан в базе СБОЛ, то ставим мужской
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
                when 1 then raise_application_error(-20001, 'Ошибка в процессе блокировки клиента в converter_lock');
                when 2 then raise_application_error(-20001, 'Превышено время ожидания разблокировки клиента другой сессией миграции');
                else null;
            end case;
            temp:=0;

            if  ( ( C.STATE='BL') and ( instr(lower(C.LOCK_REASON),'нет средств') != 0 ) ) then -- блокированный в СБОЛ ЦА клиент по причине недостаточности средств на счете
                raise_application_error(-20111, 'Клиент заблокирован в СБОЛ ЦА по причине недостаточности средств на счете, по договоренности с банком такие договора не переносим');
            end if;

            curentConvertObject := 'Определение тербанка, к которому относится клиент';
            if ( C.RGN_CODE=99 or C.RGN_CODE=38 ) then
                if ( C.TB is null and C.IS_CARD = '1' and ( C.IS_EDBO is null or C.IS_EDBO!='1') ) then
                    codTB:=99; -- Московские карточные клиенты привязываются к ОПЕРУ 99 тербанка
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
                raise_application_error(-20001, 'Клиент не относится к тербанкам, для которых работает процедура миграции RGN_CODE='||C.RGN_CODE);
            end if;

            curentConvertObject := 'Получение настроек мигратора, для данного тербанка';
            select ADAPTER, SUFFIX, REGION_ID, DEFAULT_DEPARTMENT, SCHEME_EMPLOYER, BILLING_ID, nvl(CPFL_ADAPTER, ''), ALLOWED_CODE_TB into 
                   adapter, suffix, regionID, defaultDepartment, schemeEmployer, billingID, adapterCPFL, allowedDepartments
            from CONVERTER_CONFIG where COD_TB=codTB;	

            curentConvertObject := 'Поиск адаптера для IQW';
            select CODE into adapterIQW from BILLINGS b where b.id=billingID; 

            curentConvertObject := 'Определение типа клиента и поиск схемы прав в конфиге';
            if ( C.IS_EDBO = '1' ) then
                creationType := 'UDBO'; --договор УДБО - универсального банковского обслуживания.
                select SCHEME_CLIENT_UDBO into schemeClient from CONVERTER_CONFIG where COD_TB=codTB;
            elsif ( C.IS_CARD = '1' and ( C.IS_EDBO is null or C.IS_EDBO!='1') ) then
                creationType := 'CARD'; --карточный договор. Подключен по карте и видит только карты.
                select SCHEME_CLIENT_CARD into schemeClient from CONVERTER_CONFIG where COD_TB=codTB;
            else
                creationType := 'SBOL';  --договор СБОЛ - договор, заключаемый в нашей системе по Сбербанк ОнЛайн.
                select SCHEME_CLIENT_SBOL into schemeClient from CONVERTER_CONFIG where COD_TB=codTB;
            end if;

            --мапим типы документов
            case C.DOC_TYPE
                when '0' then   -- Паспорт гражданина РФ
                  clientDocType := 'REGULAR_PASSPORT_RF';
                  clientDocName := 'Общегражданский паспорт РФ';
                when '2' then   -- Паспорт моряка
                  clientDocType := 'SEAMEN_PASSPORT';
                  clientDocName := 'Паспорт моряка';
                when '3' then   -- Вид на жительство в РФ
                  clientDocType := 'RESIDENTIAL_PERMIT_RF';
                  clientDocName := 'Вид на жительство РФ';
                when '5' then   -- Заграничный паспорт РФ
                  clientDocType := 'FOREIGN_PASSPORT_RF';
                  clientDocName := 'Заграничный паспорт РФ';
                when '111' then -- Свидетельство о регистрации ходатайства иммигранта о признании его беженцем
                  clientDocType := 'IMMIGRANT_REGISTRATION';
                  clientDocName := 'Свидетельство о регистрации ходатайства иммигранта о признании его беженцем';
                when '113' then -- Удостоверение беженца в РФ
                  clientDocType := 'REFUGEE_IDENTITY';
                  clientDocName := 'Удостоверение беженца в РФ';
/*
                when '839' then -- Миграционная карта
                  clientDocType := 'MIGRATORY_CARD';
                  clientDocName := 'Миграционная карта';
*/
                else            -- Иной документ
                  if (creationType='CARD' and C.DOC_SERIES is null and C.DOC_NUMBER is not null) then
                     clientDocType := 'PASSPORT_WAY';
                     clientDocName := '';
                  else
                     clientDocType := 'OTHER';
                     clientDocName := 'Иной документ';
                  end if;
            end case;
/*
                                         Логика миграции договоров из СБОЛ ЦА в ЕРИБ
    ____________________________________________________________________________________________________________________
   | ЕРИБ\СБОЛ |          КАРТОЧНЫЙ            |              СБОЛ                 |             УДБО                   |
   |___________|_______________________________|___________________________________|____________________________________|
   | КАРТОЧНЫЙ | Объединяем договора, анкета   | Обновляем карточный договор в     | Обновляем карточный договор в ЕРИБ |
   |           | берется из более нового дого- | ЕРИБ до СБОЛ (анкета), добавляем  | до УДБО (анкета), добавляем историю|
   |           | вора (по дате заключения), объ| историю операций и шаблоны. При   | операций и шаблоны. При этом изме- |
   |           | единяется история операций и  | этом изменяется подразделение, к  | няется подразделение, к которому   |
   |           | шаблоны.                      | которому привязан клиент, и       | привязан клиент, и внешняя система,|
   |           |                               | внешняя система, на то, что       | на то, что указано в УДБО договоре.|
   |           |                               | указано в СБОЛ договоре.          |                                    |
   |___________|_______________________________|___________________________________|____________________________________|
   |   СБОЛ    | К СБОЛ-овскому договору в     | Проверяем на наличие общих счетов | Обновляем СБОЛ договор в ЕРИБ до   |
   |           | ЕРИБ добавляем историю        | если их нет, то мигрируем клиента.| УДБО (анкета), добавляем историю   |
   |           | операций и шаблоны из карточ- | Если есть общие счета, то объединя| операций и шаблоны. При этом изме  |
   |           | ного.                         | ем СБОЛ-овские договора, причем   | няется подразделение , к которому  |
   |           |                               | клиент получается привязан к тому | привязан клиент, и внешняя система,|
   |           |                               | подразделению, которое указано в  | на то, что указано в УДБО договоре.|
   |           |                               | более новом СБОЛ договоре. Если   |                                    |
   |           |                               | договора в разных тербанках ставим|                                    |
   |           |                               | признак неудачной миграции, в лог |                                    |
   |           |                               | пишем ошибку.                     |                                    |
   |___________|_______________________________|___________________________________|____________________________________|
   |   УДБО    | К УДБО договору в ЕРИБ добавля| К УДБО договору в ЕРИБ добавляем  | Добавляем к уже имеющемуся договору|
   |           | ем историю операций и шаблоны | историю операций и шаблоны из     | в ЕРИБ историю операций и шаблоны  |
   |           | из карточного.                | СБОЛ-овского.                     | из договора УДБО ЦА.               |
   |___________|_______________________________|___________________________________|____________________________________|
*/
            -- Проверка наличия дубля клиента в базе ЕРИБ, поиск идет по Ф.И.О., Д.У.Л. и дате рождения
            curentConvertObject := 'Проверка наличия договора клиента в базе ЕРИБ';
            begin
                curentConvertObject := 'Проверка Ф.И.О. клиента в базе ЕРИБ';
                for C_ID in ( select PERSON_ID from DOCUMENTS where replace(concat(DOC_SERIES, DOC_NUMBER),' ','')=replace(concat(C.DOC_SERIES, C.DOC_NUMBER),' ','') )
                loop
                    curentConvertObject := 'Проверка Ф.И.О. клиента в базе ЕРИБ';
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
                            curentConvertObject := 'Проверка статусов клиента в базах ЕРИБ и СБОЛ';
                            if (C.STATE in ('OK','BL')) then       -- Активный или заблокирован
                                case ERIBC.CREATION_TYPE
                                    when 'UDBO' then               -- в ЕРИБ есть клиент с УДБО
                                        isNeedUpdate:='2';         -- добавляем историю и шаблоны
                                    when 'CARD' then               -- в ЕРИБ есть карточный клиент
                                        if (creationType!='CARD') then
                                            isNeedUpdate:='1';     -- обновляем анкету, добавляем историю и шаблоны
                                        else
                                            if (ERIBC.AGREEMENT_DATE >= C.AGREEMENT_DATE) then
                                                isNeedUpdate:='2'; -- добавляем историю и шаблоны
                                            else
                                                isNeedUpdate:='1'; -- обновляем анкету, добавляем историю и шаблоны
                                            end if;
                                        end if;
                                    when 'SBOL' then               -- в ЕРИБ есть клиент с договором в "Сбербанк Онлайн"
                                        if (creationType='UDBO') then
                                            isNeedUpdate:='1';     -- обновляем анкету, добавляем историю и шаблоны
                                        elsif (creationType='CARD') then
                                            isNeedUpdate:='2';     -- добавляем историю и шаблоны
                                        else
                                            if (ERIBC.TB!=ltrim(C.TB,'0')) then
                                                raise_application_error(-20001, 'Дубли в разных тербанках');
                                            end if;
                                            -- Если нет общих счетов, просто мигрируем без объединения
                                            if ( checkAccounts(idSBOL, ERIBC.LOGIN_ID)='E' ) then
                                                if (ERIBC.AGREEMENT_DATE >= C.AGREEMENT_DATE) then
                                                    isNeedUpdate:='2'; -- добавляем историю и шаблоны
                                                else
                                                    isNeedUpdate:='1'; -- обновляем анкету, добавляем историю и шаблоны
                                                end if;
                                            else
                                                isNeedUpdate:='0'; -- просто мигрируем клиента, т.к. нет объщих счетов
                                            end if;
                                        end if;
                                end case;

                            elsif ( C.STATE = 'DR' ) then -- регистрация
                                isNeedUpdate:='2'; -- добавляем историю и шаблоны

                            elsif ( C.STATE = 'DL' ) then -- удален
                                 isNeedUpdate:='0'; -- мигрируем в архив
                            else
                                raise_application_error(-20001, 'Статус клиента не определен');
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

            otherObjectError:=otherObjectError||' Тип миграции:'||isNeedUpdate||' ';

            curentConvertObject := 'Привязка к подразделению';
            begin
                -- находим подразделение, к которому привязан клиент по кодам ТБ, ОСБ и ВСП
                if (C.OFFICE is not null) then
                    select ID into departmentID from DEPARTMENTS where TB = ltrim(C.TB,'0') and OSB = ltrim(C.OSB,'0') and OFFICE = ltrim(C.OFFICE,'0');
                else
                    select ID into departmentID from DEPARTMENTS where TB = ltrim(C.TB,'0') and OSB = ltrim(C.OSB,'0') and (OFFICE is null or OFFICE = '');
                end if;
            exception when NO_DATA_FOUND then
                departmentID := defaultDepartment;
                otherObjectError := otherObjectError||' Не найдено подразделение, пользователь привязан к дефолтному '||departmentID;
            end;
/*
**          Мигрируемый клиент не имеет договоров в базе ЕРИБ, поэтому такого клиента полноценно мигрируем
*/
            if (isNeedUpdate='0') then -- Это новый клиент и его просто мигрируем

                select S_CONVERTER_LOGINS.NEXTVAL into temp from dual;
                login := 'MB'||temp;

                -- Номера клиентов будут браться из "Мобильного банка"
                phoneNumber := '';

                curentConvertObject := 'Добавление логина клиенту';
                -- проверяем на совпадение логинов в базе и добавляем логин для клиента
                select count(USER_ID) into temp from LOGINS where USER_ID = login;
                if ( temp != '0' ) then
                    login := suffix || login;
                end if;

                --генерим ID логина для текущего клиента
                select  S_LOGINS.NEXTVAL into loginID from dual;
                -- добавляем логин для клиента
                if (C.STATE='DL') then
                    insert into LOGINS (ID, USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID, LOGON_DATE, LAST_LOGON_DATE, CSA_USER_ID, IP_ADDRESS, LAST_IP_ADDRESS, LAST_LOGON_CARD_NUMBER, IS_MOBILE_BANK_CONNECTED, IS_FIRST)
                        values (loginID, login, 'C', 0, '1', null, null, null, '', '', '', '', '0', '1');
                else
                    insert into LOGINS (ID, USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID, LOGON_DATE, LAST_LOGON_DATE, CSA_USER_ID, IP_ADDRESS, LAST_IP_ADDRESS, LAST_LOGON_CARD_NUMBER, IS_MOBILE_BANK_CONNECTED, IS_FIRST)
                        values (loginID, login, 'C', 0, '0', null, null, null, '', '', '', '', '0', '1');
                end if;

                curentConvertObject := 'Добавление адресов';
                -- добвляем адреса клиента
                select  S_ADDRESS.NEXTVAL into adressRegId from dual;
                insert into ADDRESS values (adressRegId, '', '', '', '', '', '', '', '', C.REG_ADDRESS);
                select  S_ADDRESS.NEXTVAL into adressResId  from dual;
                insert into ADDRESS values (adressResId, '', '', '', '', '', '', '', '', C.RES_ADDRESS);

                clientStatus := 'A';
                -- мапим статус клиента
                case C.STATE
                    when 'OK' then  -- активный
                        clientState := 'A';
                    when 'DL' then -- удален
                        clientState := 'D';
                        clientStatus := 'D';
                    when 'DR' then -- регистрация
                        clientState := 'T';
                    when 'BL' then -- блокирован
                        curentConvertObject := 'Разбор типа блокировки';
                        clientState := 'A';
                        temp := null;
                        -- разбираем причину блокировки
                        if ( instr(lower(C.LOCK_REASON),'нет средств') != 0 ) then
                            reasonType := 'system';
                            blockedUntil := null;
                        elsif ( instr(lower(C.LOCK_REASON),'подбор') != 0 ) then
                            reasonType := 'wrongLogons';
                            select cast(systimestamp+0.0021 as timestamp(0)) into blockedUntil from dual;
                        else
                            reasonType := 'employee';
                            blockedUntil := null;
                            -- ищем сотрудника в подразделении, к которому привязываем клиента с правами на блокировку/разблокировку клиентов (выбирается 1-й из списка)
                            -- если не нашли подходящего сотрудника, то падаем в exception
                            curentConvertObject := 'Поиск сотрудника для блокировки клиента';
                            select E.LOGIN_ID into temp from EMPLOYEES E
                                inner join SCHEMEOWNS S on E.LOGIN_ID = S.LOGIN_ID
                                    where DEPARTMENT_ID = departmentID and S.SCHEME_ID = schemeEmployer and rownum = 1;
                        end if;

                        curentConvertObject := 'Блокировка логина';
                        -- блокируем клиента, если он заблокирован в СБОЛ
                        insert into LOGIN_BLOCK	values (S_LOGIN_BLOCK.NEXTVAL, loginID, reasonType, C.LOCK_REASON, systimestamp, blockedUntil, temp);
                    else
                        raise_application_error(-20001, 'Статус клиента не определен');
                end case;

                -- Определение типа клиента
                if ( creationType = 'UDBO') then
                    -- опрделяем резидентность клиента
                    for AC in (select ACCOUNT_NUMBER from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID))
                    loop
                        if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                            residental := '0';
                            exit;
                        end if;
                    end loop;
                elsif ( creationType = 'SBOL' ) then
                    curentConvertObject := 'Добавляем счета';
                    -- добавляем счета клиента
                    for AC in (select ACCOUNT_NUMBER, ACCOUNT_NAME, TB from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID)) loop
                        if (clientStatus = 'A' and (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,3)='423' or ( ( substr(AC.ACCOUNT_NUMBER,1,5)='40817' or substr(AC.ACCOUNT_NUMBER,1,5)='40820' ) and getAccountType(AC.TB, AC.ACCOUNT_NAME)='O' ) ) ) then
							curentConvertObject := 'Добавляем счет #'||AC.ACCOUNT_NUMBER||'|'||loginID||'|'||C.CLIENT_ID;
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
                    else tempValue := ''; -- "None" или "Null"
                end case;

                curentConvertObject := 'Добавляем клиента';
                -- добавляем клиента
                select S_USERS.NEXTVAL into clientID from dual;
                insert into USERS (ID, CLIENT_ID, LOGIN_ID, STATUS, FIRST_NAME, SUR_NAME, PATR_NAME, BIRTHDAY, BIRTH_PLACE, REGISTRATION_ADDRESS, RESIDENCE_ADDRESS, MESSAGE_SERVICE, E_MAIL, HOME_PHONE, JOB_PHONE, MOBILE_PHONE, MOBILE_OPERATOR, AGREEMENT_NUMBER, AGREEMENT_DATE, INSERTION_OF_SERVICE, GENDER, TRUSTING_ID, CITIZEN_RF, INN, PROLONGATION_REJECTION_DATE, STATE, DEPARTMENT_ID, CONTRACT_CANCELLATION_COUSE, SECRET_WORD, RESIDENTAL, SMS_FORMAT, DISPLAY_CLIENT_ID, USE_OFERT, CREATION_TYPE, LAST_UPDATE_DATE, REG_IN_DEPO, SNILS, MDM_STATE, USE_INTERNET_SECURITY, EMPLOYEE_ID)
                    values (clientID, C.CLIENT_ID||'|'|| adapter, loginID, clientStatus, C.FIRST_NAME, C.SUR_NAME, C.PATR_NAME, C.BIRTHDAY, C.BIRTH_PLACE, adressRegId, adressResId, tempValue, C.E_MAIL, C.HOME_PHONE, C.JOB_PHONE, phoneNumber, C.MOBILE_OPERATOR, C.AGREEMENT_NUMBER, C.AGREEMENT_DATE, C.INSERTION_OF_SERVICE, C.GENDER, null, C.CITIZEN_RF,  C.INN, null, clientState, departmentID, 'С', '', residental, '1', C.CLIENT_ID , '1', creationType, sysdate, '', C.SNILS, 'NOT_SENT', '0', '');

                if ( tempValue != '' or C.E_MAIL is not null ) then
                    curentConvertObject := 'Добавляем контактную информацию для оповещений';
                    insert into PERSONAL_SUBSCRIPTION_DATA(LOGIN_ID, EMAIL_ADDRESS, MOBILE_PHONE, SMS_ENCODING)
                        values(loginID, C.E_MAIL, '', 'TRANSLIT');
                end if;

                if ( tempValue != '' ) then -- если задан какой либо способ оповещения клиента - настраиваем оповещения

                    curentConvertObject := 'Инициализация переменных для настройки оповещений';
                    select ID into distributionId from DISTRIBUTIONS where ROW_KEY = 'PaymentExecute';
                    select ID into scheduleId from SCHEDULE where DISTRIBUTION_ID = distributionId;

                    curentConvertObject := 'Добавляем оповещение';
                    select S_SUBSCRIPTIONS.NEXTVAL into subscriptionId from dual;
                    insert into SUBSCRIPTIONS(ID, LOGIN_ID, SCHEDULE_ID) values(subscriptionId, loginID, scheduleId);

                    curentConvertObject := 'Добавляем шаблон оповещения';
                    if ( tempValue = 'sms' ) then
                        select ID into smsTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'sms' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, smsTemplateId, 0);
                    else
                        select ID into emailTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'email' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, emailTemplateId, 0);
                    end if;
                end if;

                curentConvertObject := 'Проверка документов клиента';
                if (validateDocuments(clientDocType, C.DOC_SERIES, C.DOC_NUMBER ) = '0') then
                    raise_application_error(-20001, 'Ошибка при проверке докуменов клиента - некорректные данные Тип док-та: '||clientDocType||' Серия '|| C.DOC_SERIES||'  Номер '||C.DOC_NUMBER);
                end if;

                curentConvertObject := 'Добавляем документы клиента';
                -- добавляем документы клиента
                if ( clientDocType != 'PASSPORT_WAY' ) then
                    insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, clientDocType, clientDocName, C.DOC_NUMBER, C.DOC_SERIES, C.DOC_ISSUE_DATE, C.DOC_ISSUE_BY, C.DOC_ISSUE_BY_CODE, '1', clientID, null, '1');
                else
                    insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, clientDocType, clientDocName, null, concat(C.DOC_SERIES, C.DOC_NUMBER), C.DOC_ISSUE_DATE, C.DOC_ISSUE_BY, C.DOC_ISSUE_BY_CODE, '1', clientID, null, '1');
                end if;

                curentConvertObject := 'Задаем схему прав для клиента';
                -- задаем схему прав для клиента
                insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeClient, loginID, 'simple');

                curentConvertObject := 'Добавляем параметры аутентификации';
                -- выставлям параметры на вход пользователя и способ подтверждения операций
                if ( C."simple-auth-choice" = '1' ) then
                      authChoice := 'smsp';
                      S3:=S3||'<entry key="userOptionType">sms</entry></properties>';
                else
                    authChoice := 'lp';
                    S3:=S3||'</properties>';
                end if;

                -- от какого признака брать способ подтверждения операций
                insert into AUTHENTICATION_MODES values (S_AUTHENTICATION_MODES.NEXTVAL, loginID, 'simple', utl_raw.cast_to_raw( S1 || authChoice || S2 || confirmСhoice || S3 ), null);

                            curentConvertObject := 'Добавляем профиль клиента'; --LOGON_NOTIFICATION_TYPE     MAIL_NOTIFICATION_TYPE     IS_BANK_OFFER_VIEWED  SHOW_PERSONAL_FINANCE
                insert into PROFILE values (loginID, S_PROFILE.NEXTVAL, regionID, '1', null, null, '1', null, null);

                curentConvertObject := 'Добавляем XML представление клиента';
                insert into XMLPERSONREPRESENTATION values (S_XMLPERSONREPRESENTATION.NEXTVAL, null, clientID);

                curentConvertObject := 'Добавляем пароль';
                insert into PASSWORDS values (S_PASSWORDS.NEXTVAL, 'pu', loginID, login, '1', systimestamp, cast(systimestamp+730 as timestamp(0)), '0');

                curentConvertObject := 'Добавляем пин-конверт';
                -- добавляем пин-конверт (без него нельзя сохранить анкету клиента)
                select S_PINENVELOPES.NEXTVAL into pinNumber from dual;
                insert into PINENVELOPES values (pinNumber, 1, login, 'U', login, departmentID);

                update LOGINS set PIN_ENVELOPE_ID=pinNumber where ID=loginID;

                curentConvertObject := 'Добавляем лимиты';
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

                        curentConvertObject := 'Обновляем остаток от суммы лимита на текущий день';
                        if ( C.OPERATION_DATE=trunc(sysdate) and C.OPERSUM is not null ) then
                            updateUserLimit(loginID, ownLimit, C.OPERSUM);
                        end if;
                    end if;
                end if;
/*
**          Мигрируемый клиент уже имеет договор в базе ЕРИБ, но договор в базе СБОЛ ЦА содержит более актуальную информацию
**          о клиенте. Поэтому обновляем анкету клиента в базе ЕРИБ на данными из анкеты в СБОЛ ЦА.
*/
            elsif (isNeedUpdate='1') then -- Обновляем анкету клиента в базе ЕРИБ

                idSBOL:=C.CLIENT_ID;
                phoneNumber:='';

                curentConvertObject := 'Обновление адресов';
                update ADDRESS set UNPARSEABLE=C.REG_ADDRESS
                    where ID=adressRegId;
                update ADDRESS set UNPARSEABLE=C.RES_ADDRESS
                    where ID=adressResId;

                clientStatus := 'A';
                -- мапим статус клиента
                case C.STATE
                    when 'OK' then  -- активный
                        clientState := 'A';
                        delete from LOGIN_BLOCK where LOGIN_ID=loginID;
                    when 'DL' then -- удален
                        clientState := 'D';
                        clientStatus := 'D';
                        update LOGINS set DELETED='1' where ID=loginID;
                    when 'DR' then --регистрация
                        clientState := 'T';
                    when 'BL' then -- блокирован
                        curentConvertObject := 'Разбор типа блокировки';
                        clientState := 'A';
                        temp := null;
                        -- разбираем причину блокировки
                        if ( instr(lower(C.LOCK_REASON),'нет средств') != 0 ) then
                            null;
                            --reasonType := 'system';
                            --blockedUntil := null;
                        elsif ( instr(lower(C.LOCK_REASON),'подбор') != 0 ) then
                            reasonType := 'wrongLogons';
                            select cast(systimestamp+0.0021 as timestamp(0)) into blockedUntil from dual;
                        else
                            reasonType := 'employee';
                            blockedUntil := null;
                            -- ищем сотрудника в подразделении, к которому привязываем клиента с правами на блокировку/разблокировку клиентов (выбирается 1-й из списка)
                            -- если не нашли подходящего сотрудника, то падаем в exception
                            curentConvertObject := 'Поиск сотрудника для блокировки клиента';
                            select E.LOGIN_ID into temp from EMPLOYEES E
                                inner join SCHEMEOWNS S on E.LOGIN_ID = S.LOGIN_ID
                                    where DEPARTMENT_ID = departmentID and S.SCHEME_ID = schemeEmployer and rownum = 1;
                        end if;

                        curentConvertObject := 'Блокировка логина';
                        -- блокируем клиента, если он заблокирован в СБОЛ, предварительно чистим старые блокировки
                        if ( instr(lower(C.LOCK_REASON),'нет средств') = 0 ) then
                            delete from LOGIN_BLOCK where LOGIN_ID=loginID;
                            insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, reasonType, C.LOCK_REASON, systimestamp, blockedUntil, temp);
                        end if;
                    else
                        raise_application_error(-20001, 'Статус клиента не определен');
                end case;

                --Если в ЕРИБ есть карточный клиент, и мы мигрируем не карточного клиента, то при обновлении анкеты обновляем и ПАСПОРТ
                if ( clientCreationType='CARD' and creationType!='CARD' ) then

                    curentConvertObject := 'Проверка документов клиента';
                    if (validateDocuments(clientDocType, C.DOC_SERIES, C.DOC_NUMBER ) = '0') then
                        raise_application_error(-20001, 'Ошибка при проверке докуменов клиента - некорректные данные Тип док-та: '||clientDocType||' Серия '|| C.DOC_SERIES||'  Номер '||C.DOC_NUMBER);
                    end if;

                    curentConvertObject := 'Обновление документов клиента (PASSPORT_WAY)';
                    update DOCUMENTS set DOC_TYPE=clientDocType, DOC_NAME=clientDocName, DOC_NUMBER=C.DOC_NUMBER, DOC_SERIES=C.DOC_SERIES,
                                         DOC_ISSUE_DATE=C.DOC_ISSUE_DATE, DOC_ISSUE_BY=C.DOC_ISSUE_BY, DOC_ISSUE_BY_CODE=C.DOC_ISSUE_BY_CODE
                        where PERSON_ID=clientID and DOC_TYPE='PASSPORT_WAY';
                end if;

                -- Определение типа клиента
                if ( creationType = 'UDBO' ) then
                    --договор УДБО - универсального банковского обслуживания.
                    -- опрделяем резидентность клиента
                    for AC in (select ACCOUNT_NUMBER from V_ACCOUNTS@promLink where (CLIENT_ID = C.CLIENT_ID))
                    loop
                        if (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,5)='40820') then
                            residental := '0';
                            exit;
                        end if;
                    end loop;

                elsif ( creationType = 'SBOL' ) then
                    --договор СБОЛ - договор, заключаемый в нашей системе по Сбербанк ОнЛайн.
                    curentConvertObject := 'Добавляем счета';
                    -- добавляем счета клиента
                    for AC in (select ACCOUNT_NUMBER, ACCOUNT_NAME, TB from V_ACCOUNTS@promLink AL
                                where AL.CLIENT_ID = C.CLIENT_ID and not exists( select ACCOUNT_NUMBER from ACCOUNT_LINKS where LOGIN_ID=loginID and ACCOUNT_NUMBER=AL.ACCOUNT_NUMBER ))
                    loop
                        if (clientStatus = 'A' and (substr(AC.ACCOUNT_NUMBER,1,3)='426' or substr(AC.ACCOUNT_NUMBER,1,3)='423' or ( ( substr(AC.ACCOUNT_NUMBER,1,5)='40817' or substr(AC.ACCOUNT_NUMBER,1,5)='40820' ) and getAccountType(AC.TB, AC.ACCOUNT_NAME)='O' ) ) ) then
                            curentConvertObject := 'Добавляем счет #'||AC.ACCOUNT_NUMBER||'|'||loginID||'|'||C.CLIENT_ID;
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
                    else tempValue := ''; -- "None" или "Null"
                end case;

                curentConvertObject := 'Добавляем клиента';
                update USERS
                    set ID=clientID, CLIENT_ID=C.CLIENT_ID||'|'|| adapter, LOGIN_ID=loginID, STATUS=clientStatus, FIRST_NAME=C.FIRST_NAME,
                        SUR_NAME=C.SUR_NAME, PATR_NAME=C.PATR_NAME, BIRTHDAY=C.BIRTHDAY, BIRTH_PLACE=C.BIRTH_PLACE, REGISTRATION_ADDRESS=adressRegId,
                        RESIDENCE_ADDRESS=adressResId, MESSAGE_SERVICE=tempValue, E_MAIL=C.E_MAIL, HOME_PHONE=C.HOME_PHONE, JOB_PHONE=C.JOB_PHONE,
                        MOBILE_PHONE=phoneNumber, MOBILE_OPERATOR=C.MOBILE_OPERATOR, AGREEMENT_NUMBER=C.AGREEMENT_NUMBER, AGREEMENT_DATE=C.AGREEMENT_DATE,
                        INSERTION_OF_SERVICE=C.INSERTION_OF_SERVICE, GENDER=C.GENDER, TRUSTING_ID=null, CITIZEN_RF=C.CITIZEN_RF, INN=C.INN,
                        PROLONGATION_REJECTION_DATE=null, STATE=clientState, DEPARTMENT_ID=departmentID, CONTRACT_CANCELLATION_COUSE='С',
                        SECRET_WORD='', RESIDENTAL=residental, SMS_FORMAT='1', DISPLAY_CLIENT_ID=C.CLIENT_ID, USE_OFERT='1',
                        CREATION_TYPE=creationType, LAST_UPDATE_DATE=sysdate, REG_IN_DEPO='', SNILS=C.SNILS, MDM_STATE='NOT_SENT'
                where ID=clientID;

                if ( tempValue != '' or C.E_MAIL is not null ) then
                    select count(LOGIN_ID) into tempFlag from PERSONAL_SUBSCRIPTION_DATA where LOGIN_ID=loginID;
                    if (tempFlag=0) then
                        curentConvertObject := 'Добавляем контактную информацию для оповещений';
                        insert into PERSONAL_SUBSCRIPTION_DATA(LOGIN_ID, EMAIL_ADDRESS, MOBILE_PHONE, SMS_ENCODING)
                            values(loginID, C.E_MAIL, '', 'TRANSLIT');
                    else
                        curentConvertObject := 'Обновляем контактную информацию для оповещений';
                        update PERSONAL_SUBSCRIPTION_DATA set EMAIL_ADDRESS=C.E_MAIL
                            where LOGIN_ID=loginID;
                    end if;
                end if;

                select count(LOGIN_ID) into tempFlag from SUBSCRIPTIONS where LOGIN_ID=loginID;

                if ( tempValue != '' and tempFlag=0 ) then -- если задан какой либо способ оповещения клиента - настраиваем оповещения

                    curentConvertObject := 'Инициализация переменных для настройки оповещений';
                    select ID into distributionId from DISTRIBUTIONS where ROW_KEY = 'PaymentExecute';
                    select ID into scheduleId from SCHEDULE where DISTRIBUTION_ID = distributionId;

                    curentConvertObject := 'Добавляем оповещение';
                    select S_SUBSCRIPTIONS.NEXTVAL into subscriptionId from dual;
                    insert into SUBSCRIPTIONS(ID, LOGIN_ID, SCHEDULE_ID) values(subscriptionId, loginID, scheduleId);

                    curentConvertObject := 'Добавляем шаблон оповещения';
                    if ( tempValue = 'sms' ) then
                        select ID into smsTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'sms' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, smsTemplateId, 0);
                    else
                        select ID into emailTemplateId from MESSAGE_TEMPLATES where CHANNEL = 'email' and DISTRIBUTION_ID = distributionId;
                        insert into SUBSCRIPTION_TEMPLATES(SUBSCRIPTION_ID, TEMPLATE_ID, LIST_INDEX) values(subscriptionId, emailTemplateId, 0);
                    end if;
                end if;

                curentConvertObject := 'Обновляем схему прав для клиента';
                update SCHEMEOWNS set SCHEME_ID=schemeClient, ACCESS_TYPE='simple'
                    where LOGIN_ID=loginID;

                curentConvertObject := 'Обновление параметров аутентификации';
                -- выставлям параметры на вход пользователя и способ подтверждения операций
                if ( C."simple-auth-choice" = '1' ) then
                  authChoice := 'smsp';
                  S3:=S3||'<entry key="userOptionType">sms</entry></properties>';
                else
                  authChoice := 'lp';
                  S3:=S3||'</properties>';
                end if;

                update AUTHENTICATION_MODES set ACCESS_TYPE='simple', PROPERTIES=utl_raw.cast_to_raw( S1 || authChoice || S2 || confirmСhoice || S3 ), MODE_KEY=null
                    where LOGIN_ID=loginID;

                curentConvertObject := 'Обновляем профиль клиента';
                update PROFILE set REGION_ID=regionID, SHOW_ASSISTANT='1'
                    where LOGIN_ID=loginID;

                curentConvertObject := 'Обновляем XML представление клиента';
                update XMLPERSONREPRESENTATION set XML=null where USER_ID=to_char(clientID);

                curentConvertObject := 'Добавляем лимиты';
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
                        curentConvertObject := 'Обновляем остаток от суммы лимита на текущий день';
                        if ( C.OPERATION_DATE=trunc(sysdate) and C.OPERSUM is not null ) then
                            updateUserLimit(loginID, ownLimit, C.OPERSUM);
                        end if;
                    end if;
                end if;
/*
**          Мигрируемый клиент имеет договоров в базе ЕРИБ и этот договор содержит актуальную информацию о клиенте. Поэтому
**          для такого клиента анкета не обновляется, а добавляются история операций и шаблоны платежей
*/
            elsif (isNeedUpdate='2') then -- Не требуется обновлять анкету клинта просто добавляем историю операций клиента из базы СБОЛ ЦА
                curentConvertObject := 'Получение подразделения клиента и адаптера';
                select DEPARTMENT_ID, regexp_replace(CLIENT_ID, '.{1,}\|','') into departmentID, adapter from USERS where LOGIN_ID=loginID;
                idSBOL:= SBOL_ID;
				
				curentConvertObject := 'Обновляем профиль клиента';
                update PROFILE set REGION_ID=regionID, SHOW_ASSISTANT='1'
                    where LOGIN_ID=loginID;
            end if;
        end loop;

            curentConvertObject := 'Добавляем историю операций';
            -- добавляем испорию операций (платежи)
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
            /*                              Формы платежей (OLD)
                        1	BlockingCardClaim           Блокировка карты
                        2	ConvertCurrencyPayment      Обмен валют
                        3	InternalPayment             Перевод между моими счетами
                        4	LossPassbookApplication     Заявить об утере сберегательной книжки
                        5	RurPayJurSB                 Оплата услуг
                        6	RecallPayment               Отзыв перевода
                        7	RurPayment                  Перевести деньги
                        8	TaxPayment                  Оплата налогов
                        9   LoanPayment                 Погашение кредита
                        10	RefuseLongOffer             Отмена выполнения регулярного платежа
                                                    (NEW)
                        1	BlockingCardClaim           Блокировка карты
                        2	ConvertCurrencyPayment      Обмен валют
                        3	DepositorFormClaim          Поручение на получение анкетных данных
                        4	InternalPayment             Перевод между моими счетами
                        5	LoanPayment                 Погашение кредита
                        6	LossPassbookApplication     Заявить об утере сберегательной книжки
                        7	RurPayJurSB                 Оплата услуг
                        8	SecuritiesTransferClaim     Поручение на перевод/прием перевода ценных бумаг
                        9	SecurityRegistrationClaim   Заявка на регистрацию ценной бумаги
                        10	RecallPayment               Отзыв перевода
                        11	RurPayment                  Перевести деньги
                        12	TaxPayment                  Оплата налогов
                        21	RecallDepositaryClaim       Отзыв документа
                        22	RefuseLongOffer             Отмена выполнения регулярного платежа
                        61	AccountOpeningClaim         Открытие вклада
                        62	ExternalProviderPayment     Оплата с внешеней ссылки
                        63	FNSPayment                  Оплата с сайта ФНС
                        64	PFRStatementClaim           Заявка на получение выписки из ПФР
                        65	AccountClosingPayment       Закрытие вклада
                        66	CreateAutoPaymentPayment    Автоплатеж
                        67	EditAutoPaymentPayment      Автоплатеж
                        68	PhizRurPayment              Перевести деньги физическому лицу
                        69	RefuseAutoPaymentPayment    Автоплатеж
            */
            if (P.AMOUNT is null) then
                currencyCode := null; -- валюта платежа (дефолтное значение)
            else
                currencyCode := nvl(P.OPERATION_VALUTE, 'RUB'); -- валюта платежа (дефолтное значение)
            end if;

            operationType := '';	-- тип операции в нашей системе
            isIQWavePayment := false;
            resourceName := '';

            curentConvertObject:='Добавление платежа '||P.KIND;

            -- переводов между своими счетами/картами (обмен валют) E
            if (P.KIND in ('CDPA0','CDPA1','CDPA2','CD2C0','CD2C1','CD2C2','F0200','F0201','F0202')) then

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                -- если версия XML_DATA < 2.002.00 то для операций 'F0200','F0201','F0202' суммы писания/зачисления берем из ответов xml_data_a
                if substr(P.KIND,1,1)='F' and ( substr(P.DOC_VERSION,2,1)='1' or (substr(P.DOC_VERSION,2,1)='2' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then
                    docVersion := '1';
                elsif (substr(P.KIND,1,1)='F' and ( substr(P.DOC_VERSION,2,1)='1' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.06 )) then
                    docVersion := '3';
                else
                    docVersion := '0';
                end if;

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- общие поля
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
                    if (PEF.TEMPLATE_PAYMENT_MODE='USE_SELF_ACCOUNT' or checkAccountsOwn(idSBOL, loginID, PEF.RECEIVER_ACCOUNT, receiverAccountType)='O') then -- перевод между своими счетами
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

                    -- проверка наличия суммы зачисления и её валюты
                    if (creditAmount is null) then
                        toResourceCurrency := null;
                    else
                        if (toResourceCurrency is null) then
                            toResourceCurrency := getAccountCurrencyCod(PEF.RECEIVER_ACCOUNT);
                            if ( toResourceCurrency = '' ) then
                                raise_application_error(-20001, 'Невозможно определить валюту суммы зачисления, документ №'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;
                    -- проверка наличия суммы списания и её валюты
                    if (debitAmount is null) then
                        fromResourceCurrency := null;
                    else
                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(P.PAYER_ACCOUNT);
                            if ( fromResourceCurrency = '' ) then
                                raise_application_error(-20001, 'Невозможно определить валюту суммы списания, документ №'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;

                    if (P.PAYER_ACCOUNT is not null and P.PAYER_CARD is null) then
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        resourceName:= P.PAYER_ACCOUNT;
                    elsif (P.PAYER_CARD is not null) then
                        tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                        isIQWavePayment := true; -- документ отправлен через iqwave (нужно добавить доп. поля)
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
                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, surName||' '||firstName||' '||patrName, debitAmount, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName , P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', null);

                    if (PEF."payer-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                    end if;

                    curentConvertObject:='Добавление дополнительных полей платежа'||P.KIND||' документ №'||P.DOC_NUMBER;
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


                    if (PEF.TEMPLATE_PAYMENT_MODE='USE_SELF_ACCOUNT' or checkAccountsOwn(idSBOL, loginID, PEF.RECEIVER_ACCOUNT, receiverAccountType)='O') then -- перевод между своими счетами

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

                        -- перевод с карты
                        if isIQWavePayment then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', PEF."AUTHORIZE_CODE", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'string', PEF."from-card-expire-date", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', PEF."to-card-expire-date", '0');
                        end if;

                        if (creditAmount is not null) then
                            -- точное значение в поле сумма зачисления
                            tempValue:='destination-field-exact';
                        elsif (debitAmount is not null) then
                            -- точное значение в поле сумма списания
                            tempValue:='charge-off-field-exact';
                        else
                            raise_application_error(-20001, 'Не определена ни сумма списания, ни сумма зачисления, документ №'||P.DOC_NUMBER);
                        end if;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', tempValue, '0');

                        update BUSINESS_DOCUMENTS set SUMM_TYPE =tempValue where ID=paymentID;
                    else

                        curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                            -- точное значение в поле сумма зачисления
                            tempValue:='destination-field-exact';
                        elsif (debitAmount is not null) then
                            -- точное значение в поле сумма списания
                            tempValue:='charge-off-field-exact';
                        else
                            raise_application_error(-20001, 'Не определена ни сумма списания, ни сумма зачисления, документ №'||P.DOC_NUMBER);
                        end if;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', tempValue, '0');

                        update BUSINESS_DOCUMENTS set SUMM_TYPE =tempValue where ID=paymentID;
                        
                    end if;

                end loop;

            elsif (P.KIND in ('CDPAY','CD2CD')) then -- переводов между своими счетами/картами E

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "payer-account-name",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "payer-account-currency",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/Valute') as "receiver-account-currency",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Credit/AccInfo/CODAccountName') as "receiver-account-name",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as "from-card-expire-date",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/CardEndDate/Value'),1,10) as "to-card-expire-date",
                                   EXTRACTVALUE(P.XML_DATA,'/*/NonresCode') as "operation-code",
                                   -- общие поля
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
                       isIQWavePayment := true; -- документ отправлен через iqwave (нужно добавить доп поля)
                       resourceName:= P.PAYER_CARD;
                    end if;

                    if (PEF.COMMISSION is null or PEF.COMMISSION='') then
                        comissionCurrency:=null;
                    else
                        comissionCurrency:=currencyCode;
                    end if;

                    if (PEF.TEMPLATE_PAYMENT_MODE='USE_SELF_ACCOUNT' or checkAccountsOwn(idSBOL, loginID, PEF.RECEIVER_ACCOUNT, 'C')='O') then -- перевод на свой счет

                        select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                        operationType:= 'E';

                        curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                        curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

                        -- перевод с карты
                        if isIQWavePayment then
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', PEF."from-card-expire-date", '0');
                        else
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-account-name', 'string', PEF."payer-account-name", '0');
                            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-account-name', 'string', PEF."receiver-account-name", '0');
                        end if;

                    else -- перевод на счет другого лица, форма RurPayment

                        select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                        operationType:= 'H';

                        select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                        curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

           elsif (P.KIND = 'F0100') then -- перевести деньги H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';

                -- если версия XML_DATA < 1.002.06 то поле FIO содержит инициалы плательщика одной строкой
                if (substr(P.DOC_VERSION,2,1)='1' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.06) then
                    docVersion := '1';
                elsif (substr(P.DOC_VERSION,2,1)='1' or (substr(P.DOC_VERSION,2,1)='2' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 2.00 )) then
                    docVersion := '2';
                else
                    docVersion := '0';
                end if;

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||', документ №'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, surName||' '||firstName||' '||patrName, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    if (PEF."payer-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."payer-account-name"||'"';
                    end if;

                    curentConvertObject:='Вставка дополнительных полей платежа';
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

            elsif (P.KIND in ('F0110','F0210','F0211','F0212')) then -- рублевый перевод 	H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';

                --для операции F0110 для версии XML_DATA < 01.006.00 нудно брать данные из поля Beneficiary
                if (P.KIND='F0110' and substr(P.DOC_VERSION,2,1)='1' and to_number(substr(P.DOC_VERSION,6,4),'0.00') < 6) then
                    tempValue := '/*/Beneficiary';
                else
                    tempValue := '/*/СreditNotSber';
                end if;

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    if (P.KIND='F0110') then
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', null);
                    else
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, '', departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', null);
                    end if;

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                        -- точное значение в поле сумма списания
                        tempValue:='charge-off-field-exact';
                    elsif (debitAmount is not null) then
                        -- точное значение в поле сумма зачисления
                        tempValue:='destination-field-exact';
                    end if;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', tempValue, '0');
                    
                    update BUSINESS_DOCUMENTS set SUMM_TYPE = tempValue where ID=paymentID;

                end loop;

            elsif (P.KIND in ('FAOPN')) then -- Открытие вклада	H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'AccountOpeningClaim';
                operationType:= 'G';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in (select  --EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/AvailPmtBalance/Value') as "AvailPmtBalance",
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/ClientEndDate'),1,10) as "closing-date_mig",
                                    EXTRACTVALUE(P.XML_DATA,'/*/ClientDurationText') as "ClientDurationText",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/ClientDurationValue') as "ClientDurationValue",
                                    --EXTRACTVALUE(P.XML_DATA,'/*/ClientDurationType') as "ClientDurationType",
                                    EXTRACTVALUE(P.XML_DATA,'/*/CurrencyRate') as "Course",
                                    EXTRACTVALUE(P.XML_DATA,'/*/DebetSum/Value') as "credit-summa",                          -- зачисленная сумма
                                    EXTRACTVALUE(P.XML_DATA,'/*/CreditSum/Value') as "debit-summa",                            -- сумма списания
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/DateClose/Value'),1,10) as "DateClose",  -- дата закрытия карты
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/CardNumber') as "payer-card-number",            -- номер карты, при переводе денег на счет с карты
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/AccInfo/Valute') as "payer-account-currency",   -- валюта счета списания
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/AccInfo/AccountType') as "payer-account-type",  -- тип счета списания (card or deposit)
                                    EXTRACTVALUE(P.XML_DATA,'/*/SourceAccount/NameOfAccount') as "payer-account-name",        -- наименование счета списания
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
                                    EXTRACTVALUE(P.XML_DATA_A,'/*/Body/Account') as "deposit-account", -- номер открываемого счета
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/Head/MessUID/MessageId') as EXTERNAL_ID,
                                    case EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrCode')
                                        when '0' then ''
                                        when '-400' then 'Ошибка обработки, операция не может быть выполнена'
                                        when '-417' then 'Недостаточно средств'
                                        when '-429' then 'Дата окончания выпадает на нерабочий день'
                                        when '-613' then 'Недостаточно средств'
                                        else EXTRACTVALUE(P.XML_DATA_A,'/*/Head/Error/ErrMes')
                                    end as REFUSING_REASON
                              from dual)
                loop
                    debitAmount := getAmount(PEF."debit-summa");
                    creditAmount := getAmount(PEF."credit-summa");
                    fromResourceCurrency := PEF."payer-account-currency";
                    toResourceCurrency := PEF."receiver-account-currency";

                    -- проверка наличия суммы зачисления и её валюты
                    if (creditAmount is null) then
                        toResourceCurrency := null;
                    else
                        if (toResourceCurrency is null) then
                            toResourceCurrency := getAccountCurrencyCod(PEF."deposit-account");
                            if ( toResourceCurrency = '' ) then
                                raise_application_error(-20001, 'Невозможно определить валюту суммы зачисления, документ №'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;
                    -- проверка наличия суммы списания и её валюты
                    if (debitAmount is null) then
                        fromResourceCurrency := null;
                    else
                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(PEF."payer-account");
                            if ( fromResourceCurrency = '' ) then
                                raise_application_error(-20001, 'Невозможно определить валюту суммы списания, документ №'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;

                    if (PEF."payer-account-type"='Deposit') then
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        resourceName:= PEF."payer-account";
                    elsif (P.PAYER_CARD='Card') then
                        tempValue := 'com.rssl.phizic.business.resources.external.CardLink';
                        isIQWavePayment := true; -- документ отправлен через iqwave (нужно добавить доп. поля)
                        resourceName:= PEF."payer-card-number";
                    else
                        tempValue := 'com.rssl.phizic.business.resources.external.AccountLink';
                        resourceName:= PEF."payer-account";
                    end if;

                    comissionCurrency:=null;

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, null, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, null, clientFullName, PEF."deposit-account", clientFullName, debitAmount, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'period-days', 'string', regexp_substr(regexp_substr(PEF."ClientDurationText", '\d{1,} \д'), '\d{1,}'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'period-months', 'string', regexp_substr(regexp_substr(PEF."ClientDurationText", '\d{1,} \м'), '\d{1,}'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'period-years', 'string', regexp_substr(regexp_substr(PEF."ClientDurationText", '\d{1,} \г'), '\d{1,}'), '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'to-resource-currency', 'string', toResourceCurrency, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'convertion-rate', 'decimal', PEF."Course", '0');
                end loop;

            elsif (P.KIND in ('FACLS')) then -- Закрытие вклада H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'AccountClosingPayment';
                operationType:='1';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                        when '-400' then 'Ошибка обработки, операция не может быть выполнена'
                                        when '-417' then 'Недостаточно средств'
                                        when '-428' then 'Отказ – со счета производится погашение кредита'
                                        when '-601' then 'Истек срок действия карты'
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

                    -- проверка наличия суммы зачисления и её валюты
                    if (creditAmount is null) then
                        toResourceCurrency := null;
                    else
                        if (toResourceCurrency is null) then
                            toResourceCurrency := getAccountCurrencyCod(PEF."receiver-account");
                            if ( toResourceCurrency = '' ) then
                                raise_application_error(-20001, 'Невозможно определить валюту суммы зачисления, документ №'||P.DOC_VERSION);
                            end if;
                        end if;
                    end if;
                    -- проверка наличия суммы списания и её валюты
                    if (debitAmount is null) then
                        fromResourceCurrency := null;
                    else
                        if (fromResourceCurrency is null) then
                            fromResourceCurrency := getAccountCurrencyCod(PEF."payer-account");
                            if ( fromResourceCurrency = '' ) then
                                raise_application_error(-20001, 'Невозможно определить валюту суммы списания, документ №'||P.DOC_VERSION);
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                        insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                            values (paymentID, null, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, null, PEF."payer-account-name", resourceName, PEF."payer-full-name", debitAmount, PEF.EXTERNAL_ID, null, 'OnLinePaymentStateMachine', PEF."payer-account", P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, creditAmount, toResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

            elsif (P.KIND = 'LOATR') then -- длительное поручение - форма рублевый перевод H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:='H';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- доп. поля длительного поручения
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                   NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'),'10') as "priority",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/AddInfo') as "GROUND",
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF."GROUND", loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF."receiver-surname"||' '||PEF."receiver-first-name"||' '||PEF."receiver-patr-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '1', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    -- доп поля длительного поручения:
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

            elsif (P.KIND = 'F0502' and codTB != 18 ) then -- оплата услуг ЦПФЛ

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                currencyCode := 'RUB';
                operationType:= 'P';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, fromResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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

                    curentConvertObject:='Разбор и добавление ЦПФЛ полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    addExtendedFields(PEF.ADDINFO, paymentID, '0');

                end loop;

            elsif (P.KIND = 'F0502' and codTB=18) then -- оплата услуг (сейчас конвертим в РУБЛЕВЫЙ ПЕРЕВОД H так-как в Байкале нет ЦПФЛ полей для платежа)
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in (select EXTRACTVALUE(P.XML_DATA_A,'/*/Body/AuthorizeCode') as "AUTHORIZE_CODE",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/BIK') as "receiver-bic",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Debet/AccInfo/Valute') as "payer-account-currency",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/CorAcc') as "receiver-cor-account",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Bank/Name') as "receiver-bank",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/Account') as "receiver-account-internal",
                                   EXTRACTVALUE(P.XML_DATA, '/*/Beneficiary/FirmInn') as "receiver-inn",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Beneficiary/Name') as RECEIVER_NAME,
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, fromResourceCurrency, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', null);

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

            elsif (P.KIND in ('F0504')) then -- оплата услуг (билинг) P

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                currencyCode := 'RUB';
                operationType:= 'P';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- общие поля
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
                    curentConvertObject:='Поиск поставщика услуг '||PEF."receiverId"||'|'||PEF."ServiceCode";
                    -- ищем поставщика услуг
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
                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' док №'||P.DOC_NUMBER;
                    if (P.AMOUNT>999999999999999 and paymentState='REFUSED') then
                        --raise_application_error(-20001, 'Сумма платежа больше допустимой:'||P.AMOUNT);
                        amountTemp:=0;
                    else
                        amountTemp:=P.AMOUNT;
                    end if;
                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' док №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, PEF.RECEIVER_NAME, null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', P.PAYER_CARD, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, amountTemp, P.OPERATION_VALUTE, 0, '0', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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
            
            elsif (P.KIND = 'F0506') then --биллинги с карт 

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
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
                                --extractvalue(P.XML_DATA, '/*/PayeeService/Id') as receiver_service_id, -- код услуги в билинге                       
                                --extractvalue(P.XML_DATA, '/*/PayeeService/PayeeCode') as receiver_code, -- код поставщика в билинге
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, '', loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.commission, clientFullName, PEF.receiver_account, PEF.RECEIVER_NAME, null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, fromResourceCurrency, 0, '0', 0, to_clob(PEF.add_info), comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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

                    --разбор доп. полей
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

            elsif (P.KIND = 'LOAPT' and codTB = 18) then -- длительное поручение на оплату услуг - форма рублевый перевод H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- доп. поля длительного поручения
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                   NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'), '10') as "priority",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                   EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                   NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/AddInfo'), EXTRACTVALUE(P.XML_DATA_Q, '/*/Body/Form190Info/Purpose')) as "GROUND",
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF."GROUND", loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, '', departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."jur-receiver-name", null, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, toResourceCurrency, 0, '1', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    -- доп поля длительного поручения:
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

            elsif (P.KIND = 'LOAPT' and codTB != 18) then -- длительное поручение на оплату услуг - форма перевод юр. лицу H

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                currencyCode := 'RUB';
                operationType:= 'P';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- доп. поля длительного поручения
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
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, null, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."receiver-account-internal", PEF."jur-receiver-name", null, PEF.EXTERNAL_ID, null, 'BillingPaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, P.AMOUNT, fromResourceCurrency, 0, '0', 0, null, comissionCurrency, 'ERIB', 'destination-field-exact');

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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
                    -- доп поля длительного поручения:
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
                        curentConvertObject:='Разбор и добавление ЦПФЛ полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                        addExtendedFields(PEF.ADDINFO, paymentID, '1');

                        curentConvertObject:='Добавление поля "AckClientBankCanChangeSumm" ЦПФЛ в CLOB'||P.KIND||' документ №'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AckClientBankCanChangeSumm', 'string', PEF."can-change-tarif", '0');

                        curentConvertObject:='Добавление поля "Значение тарифа" ЦПФЛ в CLOB'||P.KIND||' документ №'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tariffValue', 'string', PEF."tarif", '0');

                        curentConvertObject:='Добавление поля "Тариф" ЦПФЛ в CLOB'||P.KIND||' документ №'||P.DOC_NUMBER;
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'ServiceKind', 'string', PEF."tarif-name", '0');
                    end if;

                end loop;

            elsif (P.KIND = 'F2001') then -- заявление об утрате сберкнижки	O

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LossPassbookApplication';
                operationType:= 'O';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in (select EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "deposit-account-name",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "amount-currency",
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, '', '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', P.PAYER_ACCOUNT, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    resourceName:='';
                    if (PEF."deposit-account-name" is not null) then
                        resourceName:= P.PAYER_ACCOUNT ||' "'||PEF."deposit-account-name"||'"';
                    else
                        resourceName:= P.PAYER_ACCOUNT;
                    end if;

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'receiver-account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount-currency', 'string', PEF."amount-currency", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account-number', 'string', P.PAYER_ACCOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'amount-or-passbook', 'string', '1', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'deposit-account-name', 'string', PEF."deposit-account-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'deposit-account-type', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'deposit-account', 'string', P.PAYER_ACCOUNT, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'money-or-transfer', 'string', '1', '0');
                end loop;

            elsif (P.KIND = 'CRBLK') then -- операция блокировки карты	Q

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'BlockingCardClaim';
                operationType:= 'Q';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in ( select EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/AccInfo/CODAccountName') as "card-name",
                                    EXTRACTVALUE(P.XML_DATA,'/*/BlockReason') as "reason",
                                    -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-type', 'string', PEF."card-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'reason', 'string', tempVAlue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card', 'string', resourceName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'externalId', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-name', 'string', PEF."card-name", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'full-name', 'string', clientFullName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-number', 'string', P.PAYER_CARD, '0');
                end loop;

            elsif (P.KIND = 'FC201') then -- операция блокировки карты	Q

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS where "NAME" = 'BlockingCardClaim';
                operationType:= 'Q';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                for PEF in ( select EXTRACTVALUE(P.XML_DATA_Q,'/*/POSGATE_MSG/WAY4_REQUEST/PRO_CODE') as "reason",
                                    EXTRACTVALUE(P.XML_DATA_Q,'/*/POSGATE_MSG/WAY4_REQUEST/PAN') as "card-number",
                                    -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-type', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'reason', 'string', tempVAlue, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card', 'string', PEF."card-number", '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'account', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'externalId', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-name', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'full-name', 'string', clientFullName, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'card-number', 'string', PEF."card-number", '0');
                end loop;

            elsif (P.KIND in ('SZPAY')) then -- заявление оплаты задолженности по кредиту T

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LoanPayment';
                operationType:= 'T';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, PEF."from-resource-currency", departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."loan-account-number", '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    if (PEF."from-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."from-account-name"||'"';
                    end if;

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||', документ №'||P.DOC_NUMBER;
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

            elsif (P.KIND in ('LOACR')) then -- создание длительного поручения на оплату кредита T

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'LoanPayment';
                operationType:= 'T';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in ( select EXTRACTVALUE(P.XML_DATA,'/*/Credit/Account/ACC') as "loan-account-number",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/Valute') as "from-resource-currency",
                                    EXTRACTVALUE(P.XML_DATA,'/*/Debet/AccInfo/CODAccountName') as "from-account-name",
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/DebetCard/DateClose/Value'),1,10) as "from-card-expire-date",
                                    EXTRACTVALUE(P.XML_DATA,'/*/ClientCredit/CreditSumma/CurrencyCode') as "loan-currency",
                                    EXTRACTVALUE(P.XML_DATA,'/*/ClientCredit/CreditSumma/Money/Value') as "loan-amount",
                                    -- доп. поля длительного поручения
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateBegin/Value'), 1, 10) as "begin-date",
                                    SUBSTR(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/DateEnd/Value'), 1, 10) as "end-date",
                                    NVL(EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/Priority/Value'),'10') as "priority",
                                    EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "period",
                                    EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/PayDay/Value') as "payment-dates",
                                    EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/ExeEventCode') as "event",
                                    EXTRACTVALUE(P.XML_DATA, '/*/LOProperty/SummaKindCode') as "sum-type",
                                    -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа: '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, P.OPERATION_VALUTE, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF."loan-account-number", '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', resourceName, P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '1', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    if (PEF."from-account-name" is not null) then
                        resourceName:= resourceName ||' "'||PEF."from-account-name"||'"';
                    end if;

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;

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

            elsif (P.KIND in ('LODEL')) then -- документ отмены длительного поручения R

                if ( getPaymentState(P.STATE_CODE, paymentState, paymentStateDescription )='E' ) then
                    raise_application_error(-20001, 'У клиента есть незавершенные платежи док. №'||P.DOC_NUMBER||' операция: '||P.KIND);
                end if;

                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RefuseLongOffer';
                operationType:= 'R';

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
                for PEF in (select EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderInfo/FormNumber/Value') as "parent-id",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderProperty/DateBegin/Value'), 1, 10) as "start-date",
                                   SUBSTR(EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderProperty/DateEnd/Value'), 1, 10) as "end-date",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Order/Debet/ACC') as "payer-resource",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Order/Debet/AccInfo/AccountType') as "payer-resource-type",
                                   case EXTRACTVALUE(P.XML_DATA,'/*/Order/OrderProperty/ExeEventCode')
                                       when 'ONCE_IN_MONTH' then 'ежемесячно'
                                       when 'ONCE_IN_QUARTER' then 'каждые 3 месяца (ежеквартально)'
                                       when 'ONCE_IN_HALFYEAR' then 'раз в полгода'
                                       when 'ONCE_IN_YEAR' then 'раз в год'
                                       when 'BY_ANY_RECEIPT' then 'по любому зачислению'
                                       when 'BY_CAPITAL' then 'по капитализации'
                                       when 'BY_SALARY' then 'по зачислению зарплаты'
                                       when 'BY_PENSION' then 'по зачислению пенсии'
                                       when 'BY_PERCENT' then 'по зачислению процентов'
                                       when 'ON_OVER_DRAFT' then 'при возникновении овердрафта'
                                       when 'ON_REMAIND' then 'остаток на счете списания больше минимального остатка'
                                       when 'ON_EVENT' then 'по событию'
                                   end as "execution-event-type",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Order/CreditAccount/Account') as "receiver-resource",
                                   EXTRACTVALUE(P.XML_DATA,'/*/Order/CreditAccount/AccountType') as "receiver-resource-type",
                                   -- общие поля
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

                    curentConvertObject:='Добавление основных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
                    select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, PEF.GROUND, loginID, paymentFormID, P.ADMISSION_DATE, null, paymentState, paymentStateDescription, currencyCode, departmentID, P.DOC_NUMBER, '1', '0', operationType, P.CREATION_DATE, PEF.COMMISSION, clientFullName, PEF.RECEIVER_ACCOUNT, '', P.AMOUNT, PEF.EXTERNAL_ID, null, 'PaymentStateMachine', PEF."payer-resource", P.OPERATION_DATE, P.ADMISSION_DATE, P.ADMISSION_DATE, P.DOCUMENT_DATE, PEF.REFUSING_REASON, idSBOL ||'|'|| adapter, null, null, null, 0, '1', 0, null, comissionCurrency, 'ERIB', 'charge-off-field-exact');

                    if (PEF."receiver-resource-type" = 'Deposit') then
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource-type', 'string', 'ACCOUNT', '0');
                    else
                        insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'receiver-resource-type', 'string', 'CARD', '0');
                    end if;

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.KIND||' документ №'||P.DOC_NUMBER;
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

        end loop; -- платежи

        curentConvertObject:='Добавляем шаблоны платежей (разбор полей)';
        -- ищем новый ORDER_IND для шаблона клиента
        select nvl(max(ORDER_IND)+1,1) into temp from BUSINESS_DOCUMENTS_RES where BUSINESS_DOCUMENT_ID in
        (
            select ID from BUSINESS_DOCUMENTS where LOGIN_ID=loginID and STATE_CODE='TEMPLATE'
        );
        -- переводов между своими счетами/картами E
        for T_CARD in (select ID, TEMPLATE_NAME, CLIENT_ID, CARD_CUR, PAYER_LNAME, PAYER_FNAME, PAYER_MNAME, RECEIVER_ACCOUNT, nvl(logonCardNumber, (select CL_PRODUCT from V_CARDS@promLink where CLIENT_ID = tc.CLIENT_ID and rownum < 2 )) as PAYER_CARD from V_TEMPLATES_CARD@promLink tc where (CLIENT_ID = hextoraw(SBOL_ID)) )
        loop
            if ( checkAccountsOwn(idSBOL, loginID, T_CARD.RECEIVER_ACCOUNT, 'C' )='O' ) then
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                operationType:= 'E';

                curentConvertObject:='Вставка перевода с карты (шаблоны)';
                -- добавляем платеж
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                    values (paymentID, '', loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_CARD.PAYER_LNAME||' '||T_CARD.PAYER_FNAME||' '||T_CARD.PAYER_MNAME, T_CARD.RECEIVER_ACCOUNT, '', null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB', null);

                curentConvertObject:='Вставка доп. полей перевода с карты (шаблоны)';
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

                curentConvertObject:='Вставка перевода на карту (шаблоны)';
                -- добавляем платеж
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME)
                    values (paymentID, '', loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_CARD.PAYER_LNAME||' '||T_CARD.PAYER_FNAME||' '||T_CARD.PAYER_MNAME, T_CARD.RECEIVER_ACCOUNT, '', null, '', null, 'PaymentStateMachine', T_CARD.PAYER_CARD, null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB');

                curentConvertObject:='Вставка дополнительных полей перевода на карту (шаблоны)';
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

            curentConvertObject:='Вставка шаблона (перевод с карты)';
            insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, nvl( T_CARD.TEMPLATE_NAME, 'Перевод на карту '||regexp_replace(T_CARD.RECEIVER_ACCOUNT, '^(\d{4})(.{1,})(\d{5})$', '\1**** ***\3' )), '1', temp);
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

                curentConvertObject:='Вставка перевода на счет (шаблоны)';
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME)
                    values (paymentID, T_ACC.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', currencyCode, departmentID, temp, '1', '0', operationType, systimestamp, '', T_ACC.PAYER_LNAME||' '||T_ACC.PAYER_FNAME||' '||T_ACC.PAYER_MNAME, T_ACC.RECEIVER_ACCOUNT, T_ACC.RECEIVER_NAME, T_ACC.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB');

                curentConvertObject:='Вставка дополнительных полей перевода со счета (шаблоны)';
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

                curentConvertObject:='Вставка перевода на счет (шаблоны)';
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                    values (paymentID, T_ACC.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_ACC.PAYER_LNAME||' '||T_ACC.PAYER_FNAME||' '||T_ACC.PAYER_MNAME, T_ACC.RECEIVER_ACCOUNT, T_ACC.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, T_ACC.AMOUNT, currencyCode, 0, '0', 0, null, null, 'ERIB', null);

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
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period', 'string', 'МС.', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period1', 'string', 'МС', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period2', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period3', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-status', 'string', '02', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-type', 'string', '0', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'date', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', T_ACC.ACC_CUR, '0');
            end if;

            curentConvertObject:='Вставка шаблона (перевод со счета)';
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
                if (T_PAYMENT.ADDINFO is not null) then -- есть ЦПФЛ доп поля в платеже.

                    select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                    currencyCode := 'RUB';
                    operationType:= 'P';

                    if (T_PAYMENT.AMOUNT is null) then
                        currencyCode := null;
                    else
                        currencyCode := NVL(T_PAYMENT.ACC_CUR,'RUB');
                    end if;

                    -- добавляем платеж
                    curentConvertObject:='Вставка платежа (шаблоны)';
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, T_PAYMENT.AMOUNT, currencyCode, 0, '0', 0, null, null, 'ERIB', null);

                    curentConvertObject:='Добавление дополнительных полей платежа (шаблоны платежей)';
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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

                    curentConvertObject:='Разбор и добавление ЦПФЛ полей в шаблоне';
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

                    -- добавляем платеж
                    curentConvertObject:='Вставка платежа (шаблоны)';
                    select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                    insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                        values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, T_PAYMENT.AMOUNT, currencyCode, 0, '0', 0, null, null, 'ERIB', null);

                    curentConvertObject:='Добавление дополнительных полей платежа (шаблоны платежей)';
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
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period', 'string', 'МС.', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period1', 'string', 'МС', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period2', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-period3', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-status', 'string', '02', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'tax-type', 'string', '0', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-resource-currency', 'string', T_PAYMENT.ACC_CUR, '0');

                end if;

                curentConvertObject:='Вставка шаблона (платежи)';
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

                -- добавляем платеж
                curentConvertObject:='Вставка платежа (шаблоны)';
                select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME)
                    values (paymentID, T_PAYMENT.GROUND, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', currencyCode, departmentID, temp, '1', '0', operationType, systimestamp, '', T_PAYMENT.PAYER_LNAME||' '||T_PAYMENT.PAYER_FNAME||' '||T_PAYMENT.PAYER_MNAME, T_PAYMENT.RECEIVER_ACCOUNT, T_PAYMENT.RECEIVER_NAME, T_PAYMENT.AMOUNT, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB');

                curentConvertObject:='Вставка дополнительных полей платежа (шаблоны)';
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

                curentConvertObject:='Вставка шаблона (платежи)';
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

            curentConvertObject:='Вставка платежа с карты (шаблоны)';
            select S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
            insert into BUSINESS_DOCUMENTS (ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID, STATE_CODE, STATE_DESCRIPTION, CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, COMMISSION_CURRENCY, SYSTEM_NAME, SUMM_TYPE)
                values (paymentID, null, loginID, paymentFormID, systimestamp, null, 'TEMPLATE', 'Вы подтвердили документ в Шаблонах платежей.', null, departmentID, temp, '1', '0', operationType, systimestamp, '', clientFullName, T_CPAYMENT.RECEIVER_ACCOUNT, T_CPAYMENT.RECEIVER_NAME, null, '', null, 'PaymentStateMachine', '', null, null, null, systimestamp, '', idSBOL ||'|'|| adapter, null, null, null, 0, '0', 0, null, null, 'ERIB', null);

            curentConvertObject:='Вставка дополнительных полей платежа с карты (шаблоны)';
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
            insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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

            --разбор доп. полей
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

            curentConvertObject:='Вставка шаблона (платежи с карт)';
            insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, T_CPAYMENT.TEMPLATE_NAME, '1', temp);
            temp:= temp+1;

        end loop;

        -- проставляем признак успешной миграции
        migrationFlagStatus := eskadm1.migration.EndClientMigration@promLink(idSBOL,100);
        case migrationFlagStatus
            when '-2' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: неверно указан код результата. Должно быть mig_OK или mig_FAIL');
            when '-1' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: нет клиента с указанным идентификатором');
            when  '1' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: клиент не в состоянии "миграция начата"');
            when  '2' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: не удалось обновить состояние и время конца миграции');
            else null;
        end case;

        -- удаляем дублирующую запись в логе
        delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL;
        -- ошибок нет фиксируем изменения
        if (isNeedUpdate='0') then
            insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, loginID , login, STARTTIME, systimestamp, setNumber,'O','Клиент импортирован успешно, OTHER:'||otherObjectError,'','', codTB);
        elsif (isNeedUpdate='1') then
            insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, loginID , login, STARTTIME, systimestamp, setNumber,'O','Клиент импортирован успешно, договор был объединен с договором клиента LOGIN_ID '||loginID||' (с обновлением анкеты, платежи, шаблоны), OTHER:'||otherObjectError,'','', codTB);
        elsif (isNeedUpdate='2') then
            insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, loginID , login, STARTTIME, systimestamp, setNumber,'O','Клиент импортирован успешно, договор был объединен с договором клиента LOGIN_ID '||loginID||' (платежи и шаблоны), OTHER:'||otherObjectError,'','', codTB);
        end if;

        temp:=UnLockMigrateClient(clientFullId);
        if ( temp!=0 ) then
            raise_application_error(-20001, 'Ошибка в процессе разблокировки клиента в converter_lock');
        end if;
        temp:=0;

        commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            temp:=UnLockMigrateClient(clientFullId);
            if ( temp!=0 ) then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при разблокировке клиента из converter_lock SBOL_ID: '|| SBOL_ID, codTB);
            end if;

            if (SQLERRM not like 'ORA-20111%') then
                rollback to start_client_migration;
                OraError:=SQLERRM;
                migrationFlagStatus := eskadm1.migration.EndClientMigration@promLink(idSBOL,-1);
                case migrationFlagStatus
                    when '-2' then otherObjectError := otherObjectError + ' Ошибка: неверно указан код результата. Должно быть mig_OK или mig_FAIL';
                    when '-1' then otherObjectError := otherObjectError + ' Ошибка: нет клиента с указанным идентификатором';
                    when '1' then otherObjectError := otherObjectError + ' Ошибка: клиент не в состоянии "миграция начата"';
                    when '2' then otherObjectError := otherObjectError + ' Ошибка: не удалось обновить состояние и время конца миграции';
                    else null;
                end case;
                -- удаляем дублирующую запись в логе
                delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL;
                insert into CONVERTER_CLIENTS values (CurentClient, SBOL_ID, null , login, STARTTIME, SYSTIMESTAMP, setNumber,'E','Ошибка при импорте клиента SBOL_ID: '|| SBOL_ID ||', Шаг: '||curentConvertObject||', OTHER:'||otherObjectError||', ERROR: '||OraError,'','', codTB);
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при импорте клиента SBOL_ID: '|| SBOL_ID ||', Шаг: '||curentConvertObject||', ERROR: '||OraError, codTB);
                commit;
            else
                migrationFlagStatus := eskadm1.migration.EndClientMigration@promLink(idSBOL,100);
                case migrationFlagStatus
                    when '-2' then otherObjectError := otherObjectError + ' Ошибка: неверно указан код результата. Должно быть mig_OK или mig_FAIL';
                    when '-1' then otherObjectError := otherObjectError + ' Ошибка: нет клиента с указанным идентификатором';
                    when '1' then otherObjectError := otherObjectError + ' Ошибка: клиент не в состоянии "миграция начата"';
                    when '2' then otherObjectError := otherObjectError + ' Ошибка: не удалось обновить состояние и время конца миграции';
                    else null;
                end case;
                -- удаляем дублирующую запись в логе
                delete from CONVERTER_CLIENTS where ID_SBOL = idSBOL;
                insert into CONVERTER_CLIENTS values (curentClient, SBOL_ID, null , login, STARTTIME, systimestamp, setNumber,'O','Клиент заблокирован в СБОЛ ЦА по причине недостаточности средств на счете, по договоренности с банком такие договора не переносим','','', codTB);
                commit;
            end if;
    end ImportClient;

    procedure ImportEmployee(SBOL_ID in varchar2, setNumber in number) is
        suffix varchar2(3);                     -- Суффикс для логина сотрудников, при совпадении
        schemeEmployer number;                  -- Схема доступа для сотрудника (ID схемы)
        adapter varchar2(64);                   -- адаптер (наименование)
        defaultDepartment number;               -- подразделение, к которому привязываем сотрудника если не нашли подходящее по ТБ, ОСБ и ВСП (ID)
        codTB varchar2(5);                      -- ТВ к которому привязан мигрируемый клиент
        -- служебные переменные для временного хранения данных
        loginID number :=0;                             -- Текущий ID логина сотрудника/клиента
        temp number :=0;                                -- Переменная для расчетов и условий
        temp_reserv number :=0;                         -- Переменная для расчетов и условий
        departmentID number :=0;                        -- для хранения id текущего департамента
        authChoice varchar2 (5) :='';                   -- способ входа в систему
        confirmСhoice varchar2 (10) :='smsp';           -- способ подтверждения операции
        curentConvertObject varchar2 (128) :='';		-- наименование текущей конвертируемой сущности (для лога)
        otherObjectError varchar2 (128) :='';			-- наименование текущей конвертируемой сущности (для лога)

        emplFirstName varchar2(32) :='';
        emplPatrName varchar2(32) :='';
        emplSurName varchar2(64) :='';
        STARTTIME timestamp;                -- время начала импорта сотрудника
        OraError varchar2(1024);            -- код и описание ошибки oracle
        CurentEmployer number;              -- id в списке сконвертированных сотрудников
        idSBOL varchar2(32);                -- id сотрудника в СБОЛ
        login  varchar2(64);                -- логин
        emplPassword varchar2(32);          -- пароль
        emplPasswordHash varchar2(64);      -- ХЭШ пароля

    begin
        -- Конвертирование сотрудников
        savepoint start_employee_migration;

        STARTTIME := SYSTIMESTAMP;
        select S_CONVERTER_EMPLOYEES.NEXTVAL into CurentEmployer from dual;
        otherObjectError:='';

        curentConvertObject := 'Выборка данных по сотруднику из базы СБОЛ';
        for EMP in ( select ID, USR_NAME, LOGIN, USR_DELETED, USR_LOCKED, USR_LOCK_REASON, TB, OSB, OFFICE, "ROLE" from V_EMPLOYEES@promLink where ID = SBOL_ID )
        loop
            idSBOL:= EMP.ID;
            login:=EMP.LOGIN;

            -- Начинаем конвертировать сотрудника
            if (EMP.USR_DELETED != '1') then

                codTB:=to_number(ltrim(EMP.TB,'0'));

                curentConvertObject := 'Считывание настроек конвертера';
                select SUFFIX, ADAPTER, DEFAULT_DEPARTMENT into
                        suffix,  adapter, defaultDepartment
                from CONVERTER_CONFIG where COD_TB=codTB;

                curentConvertObject := 'Добавление логина';
                -- проверяем на совпадение логинов сотрудников и добавляем логин для сотрудника
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

                curentConvertObject := 'Блокировка логина';
                -- блокируем сотрудника, если он заблокирован в СБОЛ
                if (EMP.USR_LOCKED = '1') then
                    insert into LOGIN_BLOCK	values (S_LOGIN_BLOCK.NEXTVAL, loginID, 'wrongLogons', EMP.USR_LOCK_REASON, systimestamp, cast(systimestamp + 0.0021 as timestamp(0)), null);
                end if;

                -- находим подразделение, к которому привязан сотрудник по кодам ТБ, ОСБ и ВСП
                curentConvertObject := 'Добавление подразделения';
                begin
                    -- находим подразделение, к которому привязан клиент по кодам ТБ, ОСБ и ВСП
                    if (EMP.OFFICE is not null) then
                        select ID into departmentID from DEPARTMENTS where TB = ltrim(EMP.TB,'0') and OSB = ltrim(EMP.OSB,'0') and OFFICE = ltrim(EMP.OFFICE,'0');
                    else
                        select ID into departmentID from DEPARTMENTS where (TB = ltrim(EMP.TB,'0') and OSB = ltrim(EMP.OSB,'0') and (OFFICE is null or OFFICE = ''));
                    end if;
                exception when NO_DATA_FOUND then departmentID := defaultDepartment;
                    otherObjectError := 'Не найдено подразделение, пользователь привязан к дефолтному';
                end;

                -- разбираем Ф.И.О. сотрудника на Фамилию Имя и Отчество, т.к. в базе СБОЛ это хранится одной строкой
                getFIO(EMP.USR_NAME, emplFirstName, emplPatrName, emplSurName);
                if (emplFirstName is null)then
                    emplFirstName := ' '; -- если в базе (СБОЛ) нет имени сотрудника, то забиваю пробелом т.к. поле не может быть пустым
                end if;

                curentConvertObject := 'Добавление сотрудника';
                -- добавляем сотрудника
                insert into EMPLOYEES values (S_EMPLOYEES.NEXTVAL, loginID, emplFirstName, emplSurName, emplPatrName, null, null, null, departmentID, null, 0);

                curentConvertObject := 'Привязка сотрудника к схеме прав';
                -- привязываем сотрудника к схеме прав
                select ID_SCHEME_ERIB into schemeEmployer from CONVERTER_ACCESSSCHEMES where ID_SCHEME_SBOL = EMP."ROLE";
                insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeEmployer, loginID, 'employee');

                curentConvertObject := 'Настройка политики безопасности';
                -- настраиваем политики безопасности
                insert into AUTHENTICATION_MODES values(S_AUTHENTICATION_MODES.NEXTVAL, loginID, 'employee', null, null);

                curentConvertObject := 'Генерация пароля';
                if ( getEmplPassword(emplPassword, emplPasswordHash) = 'O' ) then
                    curentConvertObject := 'Добавление пароля';
                    insert into PASSWORDS values(S_PASSWORDS.NEXTVAL, 'pu', loginID, emplPasswordHash, '1', systimestamp, cast(systimestamp + 730 as timestamp(0)), '1');
                end if;
                curentConvertObject := '';
           end if;
        end loop;
        -- критических ошибок в процессе конвертации нет, фиксируем изменения
        insert into CONVERTER_EMPLOYEES values (CurentEmployer, idSBOL, loginID , login, emplPassword, STARTTIME, SYSTIMESTAMP, setNumber,'O','Сотрудник импортирован успешно, OTHER:'||otherObjectError,'','', codTB);
        commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback to start_employee_migration;
            OraError:=SQLERRM;
            insert into CONVERTER_EMPLOYEES values (CurentEmployer, idSBOL, null, login, emplPassword, STARTTIME, SYSTIMESTAMP, setNumber,'E','Ошибка при импорте сотрудника Login: '|| login ||' Шаг: '||curentConvertObject||' OTHER:'||otherObjectError||' ERROR: '||OraError,'','', codTB);
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при импорте сотрудника SBOL_ID: '|| SBOL_ID ||' Login: '|| login ||', Шаг: '||curentConvertObject||', ERROR: '||OraError, codTB);
            commit;
    end ImportEmployee;

    --  Процедура удаляет всех клиентов из списка на удаление,
    --  список на удаление создается вручную в таблице converter_set, заполняется поле логин
    procedure DeleteClients is

        loginID number :=0;                             -- Текущий ID логина клиента
        clientID number :=0;                            -- ID клиента
        adressRegId number:=0;                          -- id адреса регистрации
        adressResId number:=0;                          -- id адреса проживания
        curentConvertObject varchar2 (128) :='';		-- наименование текущей конвертируемой сущности (для лога)
        STARTTIME timestamp; 							-- время начала импорта сотрудника
        OraError varchar2(1024);						-- описание ошибки oracle
        idSBOL varchar2(32);                            -- id клиента в СБОЛ
        login  varchar2(32);                            -- логин клиента
        migrateStatus  integer;                 		-- статус мигрируемого клиента
    begin
        -- Удаление клиентов
        for REC IN ( select ID_SBOL, LOGIN from CONVERTER_SET )
        loop
            begin
                STARTTIME := SYSTIMESTAMP;
                curentConvertObject := 'Поиск клиента';
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
                    curentConvertObject := 'Удаляем шаблоны платежа '||P.ID;
                    delete from BUSINESS_DOCUMENTS_RES where BUSINESS_DOCUMENT_ID = P.ID;
                    curentConvertObject := 'Удаляем доп. поля платежа '||P.ID;
                    delete from DOCUMENT_EXTENDED_FIELDS where PAYMENT_ID = P.ID;
                    curentConvertObject := 'Удаляем платеж '||P.ID;
                    delete from BUSINESS_DOCUMENTS where ID = P.ID;
                end loop;

                curentConvertObject := 'Удаляем счета';
                delete from ACCOUNT_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем карты';
                delete from CARD_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем шаблоны МБ';
                delete from MB_PAYMENT_TEMPLATE_UPDATES where LOGIN_ID = loginID;

                curentConvertObject := 'Убираем привязку к ПИН';
                update LOGINS SET PIN_ENVELOPE_ID=null where ID=loginID;

                curentConvertObject := 'Удаляем пин конверт';
                delete PINENVELOPES where USER_ID = login;

                curentConvertObject := 'Удаляем пароль';
                delete from PASSWORDS  where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем XML представление клиента';
                delete from XMLPERSONREPRESENTATION where USER_ID = login;

                curentConvertObject := 'Удаляем профиль клиента';
                delete from PROFILE where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем параметры входа и подтверждения операций';
                delete from AUTHENTICATION_MODES where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем привязку к схеме прав для клиента';
                delete from SCHEMEOWNS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем документы клиента';
                delete from DOCUMENTS where PERSON_ID = clientID;

                curentConvertObject := 'Удаляем документы клиента';
                delete from DOCUMENTS where PERSON_ID = clientID;

                curentConvertObject := 'Удаляем адреса клиента';
                select REGISTRATION_ADDRESS,RESIDENCE_ADDRESS into adressRegId,adressResId from USERS where ID = clientID;
                update USERS set REGISTRATION_ADDRESS = null, RESIDENCE_ADDRESS = null, LOGIN_ID = null where LOGIN_ID = loginID;
                delete from ADDRESS where ID = adressRegId;
                delete from ADDRESS where ID = adressResId;

                curentConvertObject := 'Удаляем клиента';
                delete from USERS where ID = clientID;

                curentConvertObject := 'Удаляем SMS пароли клиента';
                delete from SMSPASSWORDS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем подписки на рассылки';
                delete from PERSONAL_SUBSCRIPTION_DATA where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем получателей платежей';
                delete from RECEIVERS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем настройки пунктов верхнего меню';
                delete from MENU_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем избранные операции';
                delete from FAVOURITE_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем счета';
                delete from DEPO_ACCOUNT_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем линки на металические счета';
                delete from IMACCOUNT_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем линки на кредиты';
                delete from LOAN_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем линки на длительные поручения';
                delete from LONG_OFFER_LINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем линки в ПФР';
                delete from PFRLINKS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем подписки клиента';
                delete from SUBSCRIPTIONS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем блокировку логина';
                delete from LOGIN_BLOCK where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем оповещения';
                delete from NOTIFICATIONS where LOGIN = login;

                curentConvertObject := 'Удаляем логин';
                delete from LOGINS where ID = loginID;

                -- удаляем дублирующую запись в логе
                --delete from CONVERTER_CLIENTS where ID_SBOL = REC.ID_SBOL;
--                update CONVERTER_CLIENTS set ID_ERIB='', TIME_START=STARTTIME, TIME_STOP=SYSTIMESTAMP, STATE='D', STATE_DESCRIPTION='Данные по клиенту удалены из базы' where LOGIN=login;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, 'Клиент login: '||login||' удален.', '');
                commit;
             exception when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, 'Ошибка при удалении клиента login: '||login||', Шаг: '||curentConvertObject||', ERROR: '||OraError, '');
                commit;
             end;
          end loop;
          -- все данные из набора обработаны, чистим его
          delete from CONVERTER_set;
          commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, 'Ошибка при удалении клиентов, процедура удалении остановлена ERROR: '||OraError||', требуется ручная обработка списка клиентов на удаление', '');
            commit;
    end DeleteClients;

    --  Процедура удаляет всех сотрудников из списка на удаление,
    --  список на удаление создается вручную в таблице converter_set, заполняется поле логин
    procedure DeleteEmployees is
        curentConvertObject varchar2 (128) :='';		-- наименование текущей конвертируемой сущности (для лога)
        loginID number :=0;                             -- Текущий ID логина сотрудника
        STARTTIME timestamp;                            -- время начала удаления сотрудника
        OraError varchar2(1024);                        -- код и описание ошибки при работе процедуры
        login  varchar2(32);                            -- логин сотрудника

    begin
        for REC IN ( select LOGIN from CONVERTER_SET )
        loop
            begin
                STARTTIME := SYSTIMESTAMP;

                select ID into loginID from LOGINS where USER_ID = REC.LOGIN;
                login := REC.LOGIN;

                curentConvertObject := 'Удаление пароля';
                delete from PASSWORDS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаление политики безопасности';
                delete from AUTHENTICATION_MODES where LOGIN_ID = loginID;

                curentConvertObject := 'Отвязываем сотрудника от схемы прав';
                delete from SCHEMEOWNS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаление сотрудника';
                delete from EMPLOYEES where LOGIN_ID = loginID;

                curentConvertObject := 'Снимаем блокировку с логина';
                delete from LOGIN_BLOCK	where LOGIN_ID = loginID;

                curentConvertObject := 'Удаление логина сотрудника';
                delete from LOGINS where ID = loginID;

                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP, 'Сотрудник login: '||login||' удален успешно', '');
                commit;
            exception
                when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при удалении данных сотрудника login: '||loginID||', Шаг: '||curentConvertObject||',ERROR: '||OraError, '');
                commit;
            end;
        end loop;
        delete from CONVERTER_SET;
        commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при удалении сотрудников, процедура остановлена ERROR: '||OraError||'. Требуется ручная обработка набора для удаления.', '');
            commit;
    end DeleteEmployees;

    -- Конвертим клиентов
    -- flag:
    --       0 - конвертим вручную сформированный список
    --       1 - конвертим всех
    -- rgnCode - код региона мигрируемых тербанков
    procedure MigrateClients(flag in Char, rgnCode number, sessionId number) is
        convertDelay number;                    -- Задержка между конвертациями
        countForConverting number;              -- По сколько клиентов конвертировать (партия)
        codTB varchar2(5):='';                  -- Код тербанка, клиентов которого мигрируем
        countRec number :=0;                  	-- Кол-во записей в наборе
        OraError varchar2(1024);				-- описание ошибки oracle
        isStop char(1);                         -- признак ручной остановки конвертера
        setNumber number;                       -- номер текущей партии
        migrateStatus  integer;                 -- статус мигрируемого клиента
        paymentStatus number;                   -- Статус платежей, О - можно импортировать, все платежи не в обработке; P - платежи в обработке, импортировать нельзя
        --totalClients number;                  -- кол-во отобранных клиентов для миграции
        minId number;
        maxId number;
        idSBOL varchar2(32);
    begin
        -- считываем настройки конвертера
        select CONVERT_DELAY, COUNT_FOR_CONVERTING, STOP   into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;

        if (flag = '1') then
            -- формируем набор для конвертации (все клиенты СБОЛ)
            delete from CONVERTER_SET;
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, CLIENT_ID, null, 1
                from V_CLIENTS@promLink where (( STATE='OK' or STATE='DR' or STATE='BL') and RGN_CODE=rgnCode and MIG_STATE is null);
        end if;

        select min(ID), max(ID) into minId, maxId from CONVERTER_SET where SESSION_ID=sessionid;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, 'Список клиентов пуст.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Создан список клиентов для миграции, кол-во клиентов = '||(maxId-minId+1), codTB);

        -- выставляем номер партии
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
                        when '-1'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' Ошибка при высставлении флага миграции: нет клиента с указанным идентификатором', codTB);
                        when '1'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' Ошибка при высставлении флага миграции: миграция уже начата', codTB);
                        when '2'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' для данного клиента миграция уже проведена успешно', codTB);
                        when '3'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' Ошибка при высставлении флага миграции: у клиента есть активная сессия', codTB);
                        when '4'   then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' Ошибка при высставлении флага миграции: ошибка при установке MIG_STATE и MIG_TIME', codTB);
                        when '100' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' Ошибка при высставлении флага миграции: не смогли открыть сессию RStyle', codTB);
                        when '101' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'ID: '||idSBOL||' Ошибка при высставлении флага миграции: профиль клиента заблокирован другой сессией', codTB);
                        else insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при высставлении флага миграции: '||migrateStatus, codTB);
                    end case;
                end if;

                -- если сконвертировали партию, обнуляемся и делаем паузу
                if (countRec >= countForConverting) then
                    countRec := 0;
                    -- увеличиваем счетчик партий
                    select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
                    if (convertDelay != 0) then
                        dbms_lock.sleep(convertDelay);
                    end if;
                end if;
                -- если выставлен флаг остановки, то заканчиваем конвертирование
                -- считываем настройки конвертера
                select CONVERT_DELAY, COUNT_FOR_CONVERTING, STOP   into
                       convertDelay, countForConverting, isStop
                from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;

--                select STOP into isStop from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;
                if ( isStop = '1') then
                    EXIT;
                end if;

            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Конвертер был остановлен вручную, при импорте клиента id '||idSBOL, codTB);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Конвертирование завершено', codTB);
            end if;
            -- чистим список клиентов на конвертацию
            --delete from CONVERTER_SET;
            commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при импорте клиентов, процедура конвертации остановлена ERROR: '||OraError, codTB);
            commit;
    end MigrateClients;

    -- конвертит всех сотрудников СБОЛ
    -- flag: 1 - конвертим всех
    --       0 - конвертим вручную сформированный список сотрудников
    -- rgnCode - код региона мигрируемых тербанков
    procedure MigrateEmployees(flag in Char, rgnCode number)  is

        convertDelay number;                    -- Задержка между конвертациями
        countForConverting number;              -- По сколько клиентов конвертировать (партия)
        codTB varchar2(5):='';                  -- код Тербанка
        countRec number :=0;                  	-- Кол-во записей в наборе
        OraError varchar2(1024);				-- описание ошибки oracle
        isStop char(1);                         -- признак ручной остановки конвертера
        setNumber number;                       -- номер текущей партии
        --totalEmployees number;
        minId number;
        maxId number;
        idSBOL varchar2(32);
    begin
        -- считываем настройки конвертера
        select CONVERT_DELAY, COUNT_FOR_CONVERTING, STOP   into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;

        if (flag = '1') then
            -- формируем набор для конвертации (все сотрудники СБОЛ)
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, ID, null, 1 from V_EMPLOYEES@promLink where USR_DELETED != '1' and ltrim(TB,'0') in (select COD_TB from converter_config where RGN_CODE=rgnCode);
        end if;
        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, 'Список сотрудников пуст.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Создан список сотрудниов для миграции, кол-во сотрудников = '||(maxId-minId+1), codTB);

        -- выставляем номер партии
        select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
        commit;
        for ind IN minId..maxId
            loop
                select ID_SBOL into idSBOL from CONVERTER_SET where ID = ind;
                ImportEmployee(idSBOL, setNumber);
                countRec := countRec + 1;
                -- если сконвертировали партию, обнуляемся и делаем паузу
                if (countRec >= countForConverting) then
                    countRec := 0;
                    -- увеличиваем счетчик партий
                    select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
                    if (convertDelay != 0) then
                        dbms_lock.sleep(convertDelay);
                    end if;
                end if;
                -- если выставлен флаг остановки, то заканчиваем конвертирование
                select STOP into isStop from CONVERTER_CONFIG where RGN_CODE=rgnCode and COD_TB=rgnCode;
                if ( isStop = '1') then
                    EXIT;
                end if;
            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Конвертер был остановлен вручную, после импорта сотрудника', codTB);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Конвертирование завершено', codTB);
            end if;
            -- чистим список сотрудников на конвертацию
            delete from CONVERTER_SET;
            commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, SYSTIMESTAMP,'Ошибка при импорте сотрудников, процедура конвертации остановлена ERROR: '||OraError, codTB);
            commit;
    end MigrateEmployees;

    -- получение кол-ва счетов, документов и шаблонов клиента
    procedure getClientObjectsCount(clientID in number, accounts out number, documents out number, templates out number) is
        loginID number;
    begin
        -- получаем логин id по id клиента
        select LOGIN_ID into loginID from USERS where ID = clientID;
        -- получаем кол-во счетов клиента
        select count(ID) into accounts from ACCOUNT_LINKS where LOGIN_ID = loginID;
        -- получаем кол-во платежей клиента
        select count(ID) into documents from BUSINESS_DOCUMENTS where LOGIN_ID = loginID and STATE_CODE not in ('TEMPLATE','SAVED_TEMPLATE');
        -- получаем кол-во шаблонов клиента
        select count(ID) into templates from BUSINESS_DOCUMENTS_RES
           where BUSINESS_DOCUMENT_ID in (select ID from BUSINESS_DOCUMENTS where LOGIN_ID = loginID );
    end;

end MigratorMoscow;
