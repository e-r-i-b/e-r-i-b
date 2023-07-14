CREATE OR REPLACE PACKAGE Migrator is

    -- Удаляем партию клиентов из таблицы converter_set (ищем клиента по логину) 
    -- список клиентов на удаление формируется вручную
    procedure DeleteClients;

    -- Удаляем партию клиентов из таблицы converter_set (ищем сотрудника по логину)
    -- список сотрудников на удаление формируется вручную
    procedure DeleteEmployees;

    -- Конвертим всех клиентов СБОЛ
    -- flag: 1 - конвертим всех
    --       0 - конвертим вручную сформированный список
    procedure MigrateClients(flag in Char); 

    -- Конвертим всех сотрудников СБОЛ
    -- flag: 1 - конвертим всех
    --       0 - конвертим вручную сформированный список
    procedure MigrateEmployees(flag in Char); 
    
end Migrator;
/
CREATE OR REPLACE PACKAGE BODY Migrator is

    -- разбираем Ф.И.О. на Фимилию, Имя и Отчество
    procedure getFIO (fullName in varchar2, firstName out varchar2, patrName out varchar2, surName out varchar2)
    is
        str varchar2(128):='';  -- строка для разбора ФИО клиента
        i number :=0;
        j number :=0;
    begin
        -- разбираем Ф.И.О.
        firstName :='';
        patrName :='';
        surName :='';
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

    -- функция возвращает сумму как число c десятичным разделителем ','
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
        -- параметры для настройки конвертора
        schemeClient number;               -- cхема доступа для клиента (ID схемы)   (Сейчас схема от Восточно-Сибирского Банка) для Среднерусского Банка(ID=90344)
        adapter varchar2(64);              -- адаптер (наименование)
        regionID number;                   -- регион в профиле клиента (для привязки услуг)
        defaultDepartment number;          -- одразделение, к которому привязываем сотрудника если не нашли подходящее по ТБ, ОСБ и ВСП (ID)
        schemeEmployer number;             -- Схема сотрудника, для привязки заблокированных клиентов
        suffix varchar2(3);                -- Суффикс для логина, при совпадении
        billingID number;                  -- ID билинговой системы для платежей (id IQW)
        -- служебные переменные для временного хранения данных
        loginID number :=0;                             -- текущий ID логина клиента
        loginIDSevB number :=0;                         -- текущий ID логина клиента в базе Сев. Банка
        temp number :=0;                                -- переменная для расчетов и условий
        departmentID number :=0;                        -- для хранения id текущего департамента
        adressRegId number:=0;                          -- id адреса регистрации
        adressResId number:=0;                          -- id адреса проживания
        clientID number:=0;                             -- id клиента
        paymentID number:=0;                            -- id платежа, для привязки доп. полей
        paymentFormID number :=0;                       -- id формы платежа
        tempValue varchar2 (64) := '';                  -- для выставления значений в доп полях платежа
        operationType char(1):='';                      -- тип платежа, для построения соответствия между типами операций
        kind char(1):='';                               -- тип документа (P-платеж T-шаблон платежа)
        clientState char(1):='';
        templateOrderID number :=0;
        codeService  varchar2(32) := '';
        signatureID number;
        login  varchar2(32):='';                        -- логин клиента
        pinNumber number;                               -- номер пин конверта
        firstName varchar2(32):='';                     -- Имя получателя
        patrName varchar2(32):='';                      -- Отчество получателя
        surName varchar2(64):='';                       -- Фамилия получателя
        migrationFlagStatus integer;                    -- статус флага миграции
        fromResourceCurrency varchar2(3);               -- Валюта счета списания
        toResourceCurrency varchar2(3);                 -- Валюта счета зачисления
        debitAmount number;                             -- Сумма в валюте счета списания
        creditAmount number;                            -- Сумма в валюте счета зачисления
        paymentStateDescription varchar2 (128) :='';    -- описание статуса платежа
        isGorod char(1):='';                            -- признак плавежа из Города
        curentConvertObject varchar2 (128) :='';        -- наименование текущей конвертируемой сущности (для лога)
        CurentClient varchar2(32);                      -- текущий импортируемый клиент (ID в таблице импортированных клиентов)
        otherObjectError varchar2 (128) :='';           -- наименование текущей конвертируемой сущности (для лога)
        STARTTIME timestamp;                            -- время начала импорта клиента
        OraError varchar2(1024);                        -- описание ошибки oracle
    begin
        savepoint start_client_migration;
        curentConvertObject := 'Считывание параметров конвертера';
        otherObjectError:='';
        STARTTIME := systimestamp;
        select  S_CONVERTER_CLIENTS.NEXTVAL into CurentClient from dual;

        select "scheme_Client", "adapter", "suffix", "region_id", "default_Department", "scheme_Employer","billing_id" into
               schemeClient, adapter, suffix, regionID, defaultDepartment, schemeEmployer,billingID
        from CONVERTER_CONFIG where "id"='1';

        curentConvertObject := 'Выборка данных по клиенту из базы Сев. Банка'||SEVB_ID;
        -- Конвертирование клиентов
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

            curentConvertObject := 'Привязка к подразделению';
            begin
                for D in ( select TB, OSB, OFFICE from DEPARTMENTS@sevbLink where ID = C.DEPARTMENT_ID )
                loop
                    -- находим подразделение, к которому привязан клиент по кодам ТБ, ОСБ и ВСП
                    if (D.OFFICE is not null) then
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and OFFICE = D.OFFICE;
                    else
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and (OFFICE is null or OFFICE = '');
                    end if;
                end loop;
            exception
                when NO_DATA_FOUND then
                    departmentID := defaultDepartment;
                    otherObjectError := 'Не найдено подразделение, пользователь привязан к дефолтному';
            end;

            curentConvertObject := 'Добавление логина клиенту';
            for L in ( select USER_ID, KIND, WRONG_LOGONS, DELETED, PIN_ENVELOPE_ID from LOGINS@sevbLink where ID = loginIDSevB )
            loop
                if (L.USER_ID is null) then
                    raise_application_error(-20001, 'Отсутствует логин клиента');
                end if;
                login := 'SB' || L.USER_ID;
                select S_LOGINS.NEXTVAL into loginID from dual;
                insert into LOGINS values (loginID, login, L.KIND, L.WRONG_LOGONS, L.DELETED, null, null, null, '', '', '', '');
            end loop;

            curentConvertObject := 'Добавление адресов';
            select  S_ADDRESS.NEXTVAL into adressRegId from dual;
            insert into ADDRESS select adressRegId, POSTALCODE, PROVINCE, DISTRICT, CITY, STREET, HOUSE, BUILDING, FLAT, '' from ADDRESS@sevbLink where ID = C.REGISTRATION_ADDRESS;
            select  S_ADDRESS.NEXTVAL into adressResId  from dual;
            insert into ADDRESS select adressResId, POSTALCODE, PROVINCE, DISTRICT, CITY, STREET, HOUSE, BUILDING, FLAT, '' from ADDRESS@sevbLink where ID = C.RESIDENCE_ADDRESS;

            curentConvertObject := 'Проверка блокировки сотрудника';
            -- проверка блокировки клиента
            for BL in ( select REASON_TYPE, REASON_DESCRIPTION, DATE_FROM, DATE_UNTIL, BLOCKER_ID from LOGIN_BLOCK@sevbLink where LOGIN_ID = loginIDSevB )
            loop
                if ( BL.BLOCKER_ID is not null ) then
                    curentConvertObject := 'Поиск сотрудника для блокировки клиента';
                    -- ищем сотрудника заблокиовавшего клиента, если не нашли, то падаем в exception
                    select ID into temp from EMPLOYEES where LOGIN_ID = ( select ID from LOGINS where USER_ID = ( select USER_ID from LOGINS@sevbLink where id = ( select LOGIN_ID from EMPLOYEES@sevbLink where ID = BL.BLOCKER_ID ) ) );
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, temp);
                else
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, null);
                end if;
            end loop;

            if ( clientState!='D' ) then -- для архивных клиентов счета не переносятся
              curentConvertObject := 'Добавляем счета';
              for AC in ( select PAYMENT_ABILITY, ACCOUNT_NUMBER, IS_CHANGED from ACCOUNT_LINKS@sevbLink where ( LOGIN_ID = loginIDSevB ) )
              loop
                  tempValue := substr(AC.ACCOUNT_NUMBER,1,3);
                  -- карточные и металлические счета не переносим
                  if ( tempValue='423' or tempValue='426') then
                      insert into ACCOUNT_LINKS values ( S_ACCOUNT_LINKS.NEXTVAL, AC.ACCOUNT_NUMBER ||'|'|| adapter, AC.PAYMENT_ABILITY, loginID, AC.ACCOUNT_NUMBER, AC.IS_CHANGED, ' ' , '1', '1' );
                  end if;
              end loop;
            end if;

            curentConvertObject := 'Добавляем клиента';
            select  S_USERS.NEXTVAL into clientID from dual;
            insert into USERS values (clientID, login||'|'|| adapter, loginID, C.STATUS, C.FIRST_NAME, C.SUR_NAME, C.PATR_NAME, C.BIRTHDAY, C.BIRTH_PLACE, adressRegId, adressResId, C.MESSAGE_SERVICE, C.E_MAIL, C.HOME_PHONE, C.JOB_PHONE, C.MOBILE_PHONE, C.MOBILE_OPERATOR, C.AGREEMENT_NUMBER, C.AGREEMENT_DATE, C.INSERTION_OF_SERVICE, C.GENDER, C.TRUSTING_ID, C.CITIZEN_RF,  C.INN, C.PROLONGATION_REJECTION_DATE, C.STATE, departmentID, C.CONTRACT_CANCELLATION_COUSE, C.SECRET_WORD, C.RESIDENTAL, C.SMS_FORMAT, login , C.USE_OFERT, '', 'SBOL', '', '', sysdate, '');

            curentConvertObject := 'Добавляем документы клиента';
            for D in ( select DOC_TYPE, DOC_NAME, DOC_NUMBER, DOC_SERIES, DOC_ISSUE_DATE, DOC_ISSUE_BY, DOC_ISSUE_BY_CODE, DOC_MAIN, PERSON_ID, DOC_TIME_UP_DATE, DOC_IDENTIFY from DOCUMENTS@sevbLink where PERSON_ID = C.ID )
            loop
                insert into DOCUMENTS values (S_DOCUMENTS.NEXTVAL, D.DOC_TYPE, D.DOC_NAME, D.DOC_NUMBER, D.DOC_SERIES, D.DOC_ISSUE_DATE, D.DOC_ISSUE_BY, D.DOC_ISSUE_BY_CODE, D.DOC_MAIN, clientID, D.DOC_TIME_UP_DATE, D.DOC_IDENTIFY);
            end loop;

        end loop;

        curentConvertObject := 'Задаем схему прав для клиента';
        insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeClient, loginID, 'simple');

        curentConvertObject := 'Добавляем параметры аутентификации';
        -- выставлям параметры на вход пользователя и способ подтверждения операций
        insert into AUTHENTICATION_MODES select S_AUTHENTICATION_MODES.NEXTVAL, loginID, ACCESS_TYPE, PROPERTIES, MODE_KEY from AUTHENTICATION_MODES@sevbLink where LOGIN_ID = loginIDSevB;

        curentConvertObject := 'Добавляем профиль клиента';
        insert into PROFILE values (loginID, S_PROFILE.NEXTVAL, regionID, '1');

        curentConvertObject := 'Добавляем XML представление клиента';
        insert into XMLPERSONREPRESENTATION values (S_XMLPERSONREPRESENTATION.NEXTVAL, null, clientID);

        curentConvertObject := 'Добавляем пароль';
        insert into PASSWORDS select S_PASSWORDS.NEXTVAL, KIND, loginID, VALUE, ACTIVE, ISSUE_DATE, EXPIRE_DATE, NEED_CHANGE from PASSWORDS@sevbLink where LOGIN_ID = loginIDSevB;

        curentConvertObject := 'Добавляем пин-конверт'; --(без него нельзя сохранить анкету клиента)
        select  S_PINENVELOPES.NEXTVAL into pinNumber from dual;

        insert into PINENVELOPES values (pinNumber, 1, login, 'U', login, departmentID);

        update LOGINS set PIN_ENVELOPE_ID=pinNumber where ID=loginID;

        curentConvertObject := 'Добавляем историю операций';
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
            curentConvertObject:='Добавляем подпись платежа';
            if ( P.SIGNATURE_ID is not null ) then
                select S_SIGNATURES.NEXTVAL into signatureID from dual;
                insert into SIGNATURES select signatureID, SIGNATURE_TEXT, OPERATION_ID, SESSION_ID, CHECK_DATE from SIGNATURES@sevbLink where ID=P.SIGNATURE_ID;
            else
                signatureID:=null;
            end if;
/*
        История операций раскладывается по ID формы из Северного банка. Поэтому перед запуском мигратора необходимо проверить
    соответствие ID форм платежей в базе Северного банка и миграторе.

                                           Формы платежей Северного Банка
                        1	AccountClosingClaim	        Заявка на закрытие счета по вкладу      (-)
                        2	AccountOpeningClaim	        Заявка на открытие счета по вкладу      (-)
                        4	ConvertCurrencyPayment	    Обменять валюту
                        3	BlockingCardClaim           Блокировка карты                        (-)
                        5	InternalPayment	            Перевод между счетами
                        8	RurPayJurSB	                Оплатить товары или услуги
                        6	LossPassbookApplication	    Заявить об утере сберегательной книжки  (-)
                        7	PurchaseCurrencyPayment	    Покупка иностранной валюты
                        9	SaleCurrencyPayment	        Продажа иностранной валюты
                        10	RecallPayment	            Отзыв перевода                          (-)
                        11	RurPayment                  Перевести деньги

                                           Формы платежей ЕРИБ
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
*/
            curentConvertObject:='Проверка статуса платежа';
            kind := 'P';
            case P.STATE_CODE
                when 'INITIAL' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Вы создали, но еще не сохранили документ');
                when 'SAVED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Вы сохранили, но еще не подтвердили платеж.');
                when 'DELAYED_DISPATCH' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Ожидается передача документа на обработку в банк.');
                when 'DISPATCHED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Платеж подтвержден и передан на обработку в банк.');
                when 'SUCCESSED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Платеж успешно исполнен банком: денежные средства переведены на счет получателя платежа.');
                when 'PARTLY_EXECUTED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Платеж подтвержден и передан на обработку в банк.');
                when 'EXECUTED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Платеж успешно исполнен банком: денежные средства переведены на счет получателя платежа.');
                when 'SEND' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Платеж подтвержден и передан на обработку в банк.');
                when 'SAVED_TEMPLATE' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Вы подтвердили документ в Шаблонах платежей.');
                when 'TEMPLATE' then
                    paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Вы сохранили документ в Шаблонах платежей.');
                    kind := 'T';
                    templateOrderID := templateOrderID + 1;
                when 'REFUSED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Вам отказано в исполнении операции по какой-либо причине.');
                when 'RECALLED' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Документ отозван Вами по какой-либо причине.');
                when 'ERROR' then paymentStateDescription := NVL(P.STATE_DESCRIPTION, 'Платеж не может быть исполнен, Вам необходимо обратиться в банк.');
                else
                    raise_application_error(-20001, 'Статус платежа не определен док. №'||P.DOC_NUMBER);
            end case;

            operationType := '';	-- тип операции ЕРИБ
            curentConvertObject:='Добавление платежа '||P.DOC_NUMBER;
/*
                        4	ConvertCurrencyPayment	    Обменять валюту
                        7	PurchaseCurrencyPayment	    Покупка иностранной валюты
                        9	SaleCurrencyPayment	        Продажа иностранной валюты
*/
            if ( P.FORM_ID in ('4', '7', '9')) then -- переводов между своими счетами/картами (обмен валют) E
                select ID into paymentFormID from PAYMENTFORMS where NAME = 'ConvertCurrencyPayment';
                operationType:= 'E';

                curentConvertObject:='Добавление основных полей платежа '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, '', departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, null, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, '', 0, '0', 0);

                curentConvertObject:='Разбор доп. полей платежа '||P.DOC_NUMBER;
                for PEF in ( select "NAME", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                loop
                    case PEF."NAME"
                        when 'buy-amount' then creditAmount := getAmount(PEF."VALUE");  -- Сумма покупаемой валюты
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
                        when 'sellAmount' then debitAmount := getAmount(PEF."VALUE");  -- Сумма продаваемой валюты
                        when 'type' then
                            if ( PEF."VALUE" = '2' ) then
                                -- точное значение в поле сумма зачисления
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', 'destination-field-exact', '0');
                            else
                                -- точное значение в поле сумма списания
                                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID,'exact-amount' ,'string', 'charge-off-field-exact', '0');
                            end if;
                        else null ;
                    end case;
                end loop;

                curentConvertObject:='Добавление основных полей платежа '||P.DOC_NUMBER;
                update BUSINESS_DOCUMENTS set CURRENCY=fromResourceCurrency, AMOUNT=debitAmount, DESTINATION_AMOUNT=creditAmount, DESTINATION_CURRENCY=toResourceCurrency where ID=paymentID;

                curentConvertObject:='Добавление дополнительных полей платежа '||P.DOC_NUMBER;
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
                    curentConvertObject:='Вставка шаблона';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
/*
                        5	InternalPayment	            Перевод между счетами
*/
            elsif (P.FORM_ID = '5') then -- переводов между своими счетами	E
                select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'InternalPayment';
                operationType:= 'E';

                curentConvertObject:='Добавление основных полей платежа '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, P.CURRENCY, departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, P.AMOUNT, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='Разбор доп. полей платежа '||P.DOC_NUMBER;
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

                curentConvertObject:='Добавление дополнительных полей платежа '||P.DOC_NUMBER;
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'external-card-number', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'is-external-card', 'boolean', 'false', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'operation-code', 'string', '', '0');
                insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'to-card-expire-date', 'string', '', '0');

                if (kind='T') then
                    curentConvertObject:='Вставка шаблона';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
/*
                        11	RurPayment	                Перевести деньги
*/
            elsif (P.FORM_ID = '11') then -- перевести деньги H
                select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                operationType:= 'H';
                temp :=0;

                curentConvertObject:='Добавление основных полей платежа '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, P.CURRENCY, departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, P.AMOUNT, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='Разбор доп. полей платежа '||P.KIND;
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

                curentConvertObject:='Вставка дополнительных полей платежа';
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
                    curentConvertObject:='Вставка шаблона';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
/*
                        8	RurPayJurSB	                Оплатить товары или услуги
*/
            elsif (P.FORM_ID = '8') then -- оплата услуг P

                select "VALUE" into tempValue from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID and "NAME"='appointment';
                if ( tempValue = 'gorod') then -- Платеж в пользу поставщика "Город"
                    isGorod:='1';
                    select ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayJurSB';
                    operationType:= 'P';
                else
                    isGorod:='0';
                    select  ID into paymentFormID from PAYMENTFORMS PF where PF.NAME = 'RurPayment';
                    operationType:= 'H';
                    temp :=0;
                end if;

                curentConvertObject:='Добавление основных полей платежа '||P.DOC_NUMBER;
                select  S_BUSINESS_DOCUMENTS.NEXTVAL into paymentID from dual;
                insert into BUSINESS_DOCUMENTS values (paymentID, P.GROUND, loginID, paymentFormID, P.CHANGED, signatureID, P.STATE_CODE, paymentStateDescription, P.CURRENCY, departmentID, P.DOC_NUMBER, P.CREATION_TYPE, P.ARCHIVE, operationType, P.CREATION_DATE, P.COMMISSION, P.PAYER_NAME, P.RECEIVER_ACCOUNT, P.RECEIVER_NAME, P.AMOUNT, P.EXTERNAL_ID, P.EXTERNAL_OFFICE_ID, 'PaymentStateMachine', P.PAYER_ACCOUNT , P.OPERATION_DATE, P.ADMISSION_DATE, P.EXECUTION_DATE, P.DOCUMENT_DATE, P.REFUSING_REASON, login ||'|'|| adapter, null, null, null, 0, '0', 0);

                curentConvertObject:='Разбор доп. полей платежа '||P.DOC_NUMBER;
                if ( isGorod = '1' ) then
                    for PEF in ( select "NAME", "TYPE", "VALUE", "IS_CHANGED" from DOCUMENT_EXTENDED_FIELDS@sevbLink where PAYMENT_ID = P.ID )
                    loop
                        case PEF."NAME"
                            when 'register-string' then null;
                            when 'register-number' then null;
                            when 'receiverId' then
                                curentConvertObject:='Поиск поставщика услуг '||PEF."VALUE";
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

                    curentConvertObject:='Добавление дополнительных полей платежа '||P.DOC_NUMBER;
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-type', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'fromResourceType', 'string', 'com.rssl.phizic.business.resources.external.AccountLink', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-resource-currency', 'string', P.CURRENCY, '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'from-card-expire-date', 'date', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'cards-chargeoff-support', 'string', 'false', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'accounts-chargeoff-support', 'string', 'true', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'AUTHORIZE_CODE', 'string', '', '0');
                    insert into DOCUMENT_EXTENDED_FIELDS values (S_DOCUMENT_EXTENDED_FIELDS.nextval, paymentID, 'SALES_СHECK', 'string', '', '0');
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

                    curentConvertObject:='Вставка дополнительных полей платежа';
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
                    curentConvertObject:='Вставка шаблона';
                    insert into BUSINESS_DOCUMENTS_RES values (S_BUSINESS_DOCUMENTS_RES.NEXTVAL, paymentID, P.TEMPLATE_NAME, '1', templateOrderID);
                end if;
            end if;

        end loop; -- платежи

        -- проставляем признак успешной миграции
        migrationFlagStatus:= migration.EndClientMigration@sevbLink(loginIDSevB,'100');
        case migrationFlagStatus
            when '-2' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: неверно указан код результата. Должно быть mig_OK или mig_FAIL');
            when '-1' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: нет клиента с указанным идентификатором');
            when  '1' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: клиент не в состоянии "миграция начата"');
            when  '2' then raise_application_error(-20001, 'Ошибка при высставлении флага миграции: не удалось обновить состояние и время конца миграции');
            else null;
        end case;
        -- ошибок нет фиксируем изменения
        insert into CONVERTER_CLIENTS values (CurentClient, loginIDSevB, loginID , login, STARTTIME, systimestamp, setNumber,'O','Клиент импортирован успешно, OTHER:'||otherObjectError,'','');
        commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback to start_client_migration;
            OraError:=SQLERRM;
            migrationFlagStatus := migration.EndClientMigration@sevbLink(loginIDSevB,'-1');
            case migrationFlagStatus
                when '-2' then otherObjectError := otherObjectError + ' Ошибка: неверно указан код результата. Должно быть mig_OK или mig_FAIL';
                when '-1' then otherObjectError := otherObjectError + ' Ошибка: нет клиента с указанным идентификатором';
                when '1' then otherObjectError := otherObjectError + ' Ошибка: клиент не в состоянии "миграция начата"';
                when '2' then otherObjectError := otherObjectError + ' Ошибка: не удалось обновить состояние и время конца миграции';
                else null;
            end case;
            insert into CONVERTER_CLIENTS values (CurentClient, SEVB_ID, null , login, STARTTIME, systimestamp, setNumber,'E','Ошибка при импорте клиента SEVB_ID: '|| SEVB_ID ||', Шаг: '||curentConvertObject||', OTHER:'||otherObjectError||', ERROR: '||OraError,'','');
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при импорте клиента SEVB_ID: '|| SEVB_ID ||', Шаг: '||curentConvertObject||', ERROR: '||OraError);
            commit;
    end ImportClient;

    procedure ImportEmployee(SEVB_ID in varchar2, setNumber in number) is
        -- параметры для настройки конвертора
        suffix varchar2(3);                     -- Суффикс для логина сотрудников, при совпадении
        schemeEmployer number;                  -- Схема доступа для сотрудника (ID схемы)
        adapter varchar2(64);                   -- адаптер (наименование)
        defaultDepartment number;               -- подразделение, к которому привязываем сотрудника если не нашли подходящее по ТБ, ОСБ и ВСП (ID)

        -- служебные переменные для временного хранения данных
        loginID number :=0;                             -- Текущий ID логина сотрудника/клиента
        temp number :=0;                                -- Переменная для расчетов и условий
        temp_reserv number :=0;                         -- Переменная для расчетов и условий
        departmentID number :=0;                        -- для хранения id текущего департамента
        authChoice varchar2 (5) :='';                   -- способ входа в систему
        confirmСhoice varchar2 (10) :='smsp';           -- способ подтверждения операции TODO откуда брать этот параметр:1
        curentConvertObject varchar2 (128) :='';		-- наименование текущей конвертируемой сущности (для лога)
        otherObjectError varchar2 (128) :='';			-- наименование текущей конвертируемой сущности (для лога)

        emplFirstName varchar2(32) :='';
        emplPatrName varchar2(32) :='';
        emplSurName varchar2(64) :='';
        STARTTIME timestamp;                -- время начала импорта сотрудника
        OraError varchar2(1024);            -- код и описание ошибки oracle
        CurentEmployer number;              -- id в списке сконвертированных сотрудников
        loginIDSevB number :=0;             -- текущий ID логина сотрудника в базе Сев. Банка
        idSBOL varchar2(32);                -- id сотрудника в СБОЛ
        login  varchar2(32);                -- логин
        emplPassword varchar2(32);          -- пароль

    begin
        -- Конвертирование сотрудников
        -- считываем настройки
        curentConvertObject := 'Считывание настроек конвертера';
        select "suffix", "adapter", "default_Department" into
                suffix,  adapter, defaultDepartment
        from CONVERTER_CONFIG where "id"='1';

        STARTTIME := systimestamp;
        select S_CONVERTER_EMPLOYEES.NEXTVAL into CurentEmployer from dual;
        otherObjectError:='';

        curentConvertObject := 'Выборка данных по сотруднику из базы СБОЛ';
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

            -- Начинаем конвертировать сотрудника
            curentConvertObject := 'Добавление логина';
            for L in ( select USER_ID, KIND, WRONG_LOGONS, DELETED from LOGINS@sevbLink where ID = loginIDSevB )
            loop
                login := L.USER_ID;
                select S_LOGINS.NEXTVAL into loginID from dual;
                insert into LOGINS values (loginID, login, L.KIND, L.WRONG_LOGONS, L.DELETED, null, null, null, '', '', '','');
            end loop;

            curentConvertObject := 'Проверка блокировки сотрудника';
            -- проверка блокировки клиента
            for BL in ( select REASON_TYPE, REASON_DESCRIPTION, DATE_FROM, DATE_UNTIL, BLOCKER_ID from LOGIN_BLOCK@sevbLink where LOGIN_ID = loginIDSevB )
            loop
                if ( BL.BLOCKER_ID is not null ) then
                    curentConvertObject := 'Поиск сотрудника для блокировки клиента';
                    -- ищем сотрудника заблокиовавшего клиента, если не нашли, то падаем в exception
                    select ID into temp from EMPLOYEES where LOGIN_ID = ( select ID from LOGINS where USER_ID = ( select USER_ID from LOGINS@sevbLink where id = ( select LOGIN_ID from EMPLOYEES@sevbLink where ID = BL.BLOCKER_ID ) ) );
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, temp);
                else
                    insert into LOGIN_BLOCK values (S_LOGIN_BLOCK.NEXTVAL, loginID, BL.REASON_TYPE, BL.REASON_DESCRIPTION, BL.DATE_FROM, BL.DATE_UNTIL, null);
                end if;
            end loop;

            curentConvertObject := 'Привязка к подразделению';
            begin
                for D in ( select TB, OSB, OFFICE from DEPARTMENTS@sevbLink where ID = EMP.DEPARTMENT_ID )
                loop
                    -- находим подразделение, к которому привязан сотрудник по кодам ТБ, ОСБ и ВСП
                    if (D.OFFICE is not null) then
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and OFFICE = D.OFFICE;
                    else
                        select ID into departmentID from DEPARTMENTS where TB = D.TB and OSB = D.OSB and (OFFICE is null or OFFICE = '');
                    end if;
                end loop;
            exception
                when NO_DATA_FOUND then
                    departmentID := defaultDepartment;
                    otherObjectError := 'Не найдено подразделение, пользователь привязан к дефолтному';
            end;

            curentConvertObject := 'Добавление сотрудника';
            insert into EMPLOYEES values (S_EMPLOYEES.NEXTVAL, loginID, EMP.FIRST_NAME, EMP.SUR_NAME, EMP.PATR_NAME, EMP.INFO, EMP.E_MAIL, EMP.MOBILE_PHONE, departmentID, null, EMP.SMS_FORMAT);

            curentConvertObject := 'Привязка сотрудника к схеме прав';
            select ID_SCHEME_ERIB into schemeEmployer from CONVERTER_ACCESSSCHEMES where ID_SCHEME_SBOL = ( select SCHEME_ID from SCHEMEOWNS@sevbLink where LOGIN_ID = loginIDSevB );
            insert into SCHEMEOWNS values (S_SCHEMEOWNS.NEXTVAL, schemeEmployer, loginID, 'employee');
            curentConvertObject := 'Настройка политики безопасности';
            -- настраиваем политики безопасности
            insert into AUTHENTICATION_MODES values(S_AUTHENTICATION_MODES.NEXTVAL, loginID, 'employee', null, null);

            curentConvertObject := 'Добавление пароля';
            -- добавляем пароль
            insert into PASSWORDS select S_PASSWORDS.NEXTVAL, KIND, loginID, VALUE, ACTIVE, ISSUE_DATE, EXPIRE_DATE, NEED_CHANGE from PASSWORDS@sevbLink where LOGIN_ID = loginIDSevB;
            curentConvertObject := '';
        end loop;
        -- критических ошибок в процессе конвертации нет, фиксируем изменения
        insert into CONVERTER_EMPLOYEES values (CurentEmployer, loginIDSevB, loginID , login, emplPassword, STARTTIME, systimestamp, setNumber,'O','Сотрудник импортирован успешно, OTHER:'||otherObjectError,'','');
        commit;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
            OraError:=SQLERRM;
            insert into CONVERTER_EMPLOYEES values (CurentEmployer, loginIDSevB, null, login, emplPassword, STARTTIME, systimestamp, setNumber,'E','Ошибка при импорте сотрудника Login: '|| login ||' Шаг: '||curentConvertObject||' OTHER:'||otherObjectError||' ERROR: '||OraError,'','');
            insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при импорте сотрудника SEVB_ID: '|| SEVB_ID ||' Login: '|| login ||', Шаг: '||curentConvertObject||', ERROR: '||OraError);
            commit;
    end ImportEmployee;

    --  Процедура удаляет всех клиентов из списка на удаление,
    --  список на удаление создается вручную в таблице converter_set, заполняется поле логин
    procedure DeleteClients is

        loginID number :=0;                             -- Текущий ID логина клиента
        clientID number :=0;                            -- ID клиента
        adressRegId number:=0;                          -- id адреса регистрации
        adressResId number:=0;                          -- id адреса проживания
        curentConvertObject varchar2 (128) :='';        -- наименование текущей конвертируемой сущности (для лога)
        STARTTIME timestamp;                    -- время начала импорта сотрудника
        OraError varchar2(1024);                -- описание ошибки oracle
        idSBOL varchar2(32);                    -- id клиента в СБОЛ
        login  varchar2(32);                    -- логин клиента
        migrateStatus  integer;                 -- статус мигрируемого клиента
    begin
        -- Удаление клиентов
        for REC IN ( select ID_SBOL, LOGIN from CONVERTER_SET )
        loop
            begin 
                STARTTIME := systimestamp;
                curentConvertObject := 'Поиск клиента';
                loginID:=to_number(REC.LOGIN);
                select ID into clientID  from USERS where LOGIN_ID = loginID;
                select USER_ID into login from LOGINS where ID = loginID;

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
                
                for CL in ( select "ID" from CARD_LINKS where LOGIN_ID = loginID )
                loop
                  curentConvertObject := 'Удаляем отправленную выписку';
                  delete from SENDED_ABSTRACTS where CARDLINK_ID = CL.ID;
                  curentConvertObject := 'Удаляем карты';
                  delete from CARD_LINKS where "ID" = CL.ID;
                end loop;

                for M in ( select ID from MAIL where SENDER_ID=loginID )
                loop
                  curentConvertObject := 'Убираем получателя письма';
                  delete from RECIPIENTS where MAIL_ID=M.ID;

                  curentConvertObject := 'Убираем письма';
                  delete from MAIL where ID=M.ID;
                end loop;
                
                curentConvertObject := 'Убираем шаблоны для мобильного банка';
                delete from MB_PAYMENT_TEMPLATE_UPDATES where LOGIN_ID=loginID;
                
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

                curentConvertObject := 'Удаляем подписки клиента';
                delete from SUBSCRIPTIONS where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем блокировку логина';
                delete from LOGIN_BLOCK where LOGIN_ID = loginID;

                curentConvertObject := 'Удаляем оповещения';
                delete from NOTIFICATIONS where LOGIN = login;

                curentConvertObject := 'Удаляем логин';
                delete from LOGINS where ID = loginID;

                -- удаляем дублирующую запись в логе
                delete from CONVERTER_CLIENTS where ID_ERIB = REC.ID_SBOL;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, 'Клиент login: '||login||' удален.');
                commit;
             exception when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, 'Ошибка при удалении клиента login: '||login||', Шаг: '||curentConvertObject||', ERROR: '||OraError);
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, 'Ошибка при удалении клиентов, процедура удалении остановлена ERROR: '||OraError||', требуется ручная обработка списка клиентов на удаление');
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
        for REC in ( select LOGIN from CONVERTER_SET )
        loop
            begin
                STARTTIME := systimestamp;
                loginID:=to_number(REC.LOGIN);
                select USER_ID into login from LOGINS where ID = loginID;

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

                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp, 'Сотрудник login: '||login||' удален успешно');
                commit;
            exception
                when OTHERS then
                rollback;
                    OraError:=SQLERRM;
                    insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при удалении данных сотрудника login: '||loginID||', Шаг: '||curentConvertObject||',ERROR: '||OraError);
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
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при удалении сотрудников, процедура остановлена ERROR: '||OraError||'. Требуется ручная обработка набора для удаления.');
            commit;
    end DeleteEmployees;

    -- конвертит всех клиентов СБОЛ
    -- flag: 1 - конвертим всех
    --       0 - конвертим вручную сформированный список клиентов
    procedure MigrateClients(flag in Char) is

        convertDelay number;                    -- Задержка между конвертациями
        countForConverting number;              -- По сколько клиентов конвертировать (партия)

        countRec number :=0;                  	-- Кол-во записей в наборе
        OraError varchar2(1024);                -- описание ошибки oracle
        isStop char(1);                         -- признак ручной остановки конвертера
        setNumber number;                       -- номер текущей партии
        migrateStatus  integer;                 -- статус мигрируемого клиента
        paymentStatus number;                   -- Статус платежей, О - можно импортировать, все платежи не в обработке; P - платежи в обработке, импортировать нельзя
        minId number;
        maxId number;
        idSevb varchar2(32);
        loginID varchar2(32);
    begin
        if (flag = '1') then
            -- формируем набор для конвертации (все клиенты СБОЛ)
            delete from CONVERTER_SET;
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, "ID", "LOGIN_ID" from USERS@sevbLink where STATE != 'S';
        end if;

        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, 'Список клиентов пуст.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Создан список клиентов для миграции, кол-во клиентов = '||(maxId-minId+1));

        -- считываем настройки конвертера
        select "convert_Delay" ,"count_For_Converting", "stop" into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where "id"='1';

        -- выставляем номер партии
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
                        when '-1' then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' Ошибка при высставлении флага миграции: нет клиента с указанным идентификатором');
                        when '1'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' Ошибка при высставлении флага миграции: миграция уже начата');
                        when '2'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' для данного клиента миграция уже проведена успешно');
                        when '3'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' Ошибка при высставлении флага миграции: у клиента есть активная сессия');
                        when '4'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' Ошибка при высставлении флага миграции: ошибка при установке MIG_STATE и MIG_TIME');
                        when '5'  then insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'ID: '||idSevb||' Ошибка при высставлении флага миграции: миграция отменена, в процессе миграции возникли ошибки');
                        else insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при высставлении флага миграции: '||migrateStatus);
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
                select "stop" into isStop from CONVERTER_CONFIG where "id"='1';
                if ( isStop = '1') then
                    EXIT;
                end if;
            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Конвертер был остановлен вручную, при импорте клиента id '||idSevb);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Конвертирование завершено');
            end if;
            -- чистим список клиентов на конвертацию
            delete from CONVERTER_SET;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при импорте клиентов, процедура конвертации остановлена ERROR: '||OraError);
            commit;
    end MigrateClients;

    -- конвертит всех сотрудников СБОЛ
    -- flag: 1 - конвертим всех
    --       0 - конвертим вручную сформированный список сотрудников
    procedure MigrateEmployees(flag in Char)  is

        convertDelay number;                    -- Задержка между конвертациями
        countForConverting number;              -- По сколько клиентов конвертировать (партия)
        countRec number :=0;                  	-- Кол-во записей в наборе
        OraError varchar2(1024);				-- описание ошибки oracle
        isStop char(1);                         -- признак ручной остановки конвертера
        setNumber number;                       -- номер текущей партии
        minId number;
        maxId number;
        idSevb number;
    begin
        if (flag = '1') then
            -- формируем набор для конвертации (все сотрудники Сев. Банка)
            insert into CONVERTER_SET select S_CONVERTER_SET.NEXTVAL, ID, LOGIN_ID from EMPLOYEES@sevbLink;
        end if;
        select min(ID), max(ID) into minId, maxId from CONVERTER_SET;
        if ((minId is null)OR(maxId is null)) then
            raise_application_error(-20001, 'Список сотрудников пуст.');
        end if;
        insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Создан список сотрудниов для миграции, кол-во сотрудников = '||(maxId-minId+1));
        -- считываем настройки конвертера
        select "convert_Delay" ,"count_For_Converting", "stop" into
               convertDelay, countForConverting, isStop
        from CONVERTER_CONFIG where "id"='1';
        -- выставляем номер партии
        select S_CONVERTER_SET_NUMBER.NEXTVAL into setNumber from dual;
        commit;
        for ind IN minId..maxId
            loop
                select ID_SBOL into idSevb from CONVERTER_SET where ID = ind;
                ImportEmployee(idSevb, setNumber);
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
                select "stop" into isStop from CONVERTER_CONFIG where "id"='1';
                if ( isStop = '1') then
                    EXIT;
                end if;
            end loop;

            if (isStop = '1') then
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Конвертер был остановлен вручную, после импорта сотрудника id:'||idSevb);
            else
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Конвертирование завершено');
            end if;
            -- чистим список сотрудников на конвертацию
            delete from CONVERTER_SET;
    exception
        -- вылетела ошибка при конвертации, пишем в лог ошибку и откатываем изменения
        when OTHERS then
            rollback;
                OraError:=SQLERRM;
                insert into CONVERTER_LOG values (S_CONVERTER_LOG.NEXTVAL, systimestamp,'Ошибка при импорте сотрудников, процедура конвертации остановлена ERROR: '||OraError);
            commit;
    end MigrateEmployees;

end Migrator;
/