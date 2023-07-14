create or replace PACKAGE SRB_IKFL."SECURITY_PACK" is

    type TIPInfo is record (
        LOGIN_DATE    INPUT_REGISTER_JOURNAL.LOGIN_DATE%type,
        LOGIN_ID      INPUT_REGISTER_JOURNAL.LOGIN_ID%type,
        IP_ADDRESS    INPUT_REGISTER_JOURNAL.IP_ADDRESS%type,
        APPLICATION   INPUT_REGISTER_JOURNAL.APPLICATION%type
    );

    type TIPInfoList is table of TIPInfo;


    type TClientProfile is record (
        SUR_NAME                USERS.SUR_NAME%type,
        FIRST_NAME              USERS.FIRST_NAME%type,
        PATR_NAME               USERS.PATR_NAME%type,
        LOGIN_ID                USERS.LOGIN_ID%type,
        BIRTHDAY                USERS.BIRTHDAY%type,
        BIRTH_PLACE             USERS.BIRTH_PLACE%type,
        E_MAIL                  PERSONAL_SUBSCRIPTION_DATA.EMAIL_ADDRESS%type,
        MOBILE_PHONE            PERSONAL_SUBSCRIPTION_DATA.MOBILE_PHONE%type,
        GENDER                  USERS.GENDER%type,
        DOCUMENT                varchar2(64)
    );

    type TClientProfileList is table of TClientProfile;

    type TClientLogins is record (
        LOGIN_ID      USERS.LOGIN_ID%type
    );

    type TClientLoginsList is table of TClientLogins;

    /*
      INTERNAL_TRANSFER("InternalPayment", "Перевод между своими счетами и картами"),                             //перевод между моими счетами/картами
      CONVERT_CURRENCY_TRANSFER("ConvertCurrencyPayment", "Обмен валют"),                                         //обмен валют
      IMA_PAYMENT("IMAPayment", "Покупка/продажа металла"),                                                       //покупка/продажа металла
      LOAN_PAYMENT("LoanPayment", "Погашение кредита"),                                                           //погашение кредита
      INDIVIDUAL_TRANSFER("RurPayment", "Перевод частному лицу"),                                                 //перевод частеому лицу
      EXTERNAL_PAYMENT_SYSTEM_TRANSFER("RurPayJurSB", "Оплата услуг"),                                            //оплата услуг по произвольным реквизитам
      INTERNAL_PAYMENT_SYSTEM_TRANSFER("RurPayJurSB", "Оплата услуг"),                                            //оплата услуг бил. поставщику из нашей БД
      JURIDICAL_TRANSFER("JurPayment", "Перевод организации"),                                                    //общаяя форма перевода организации
      SECURITIES_TRANSFER_CLAIM("SecuritiesTransferClaim", "Поручение на перевод/прием перевода ценных бумаг");   //поручение на перевод/прием перевода ценных бумаг
    */
    type TClientTemplate is record (
        ID                      PAYMENTS_DOCUMENTS.ID%type,
        CHARGEOFF_RESOURCE      PAYMENTS_DOCUMENTS.CHARGEOFF_RESOURCE%type,
        DESTINATION_RESOURCE    PAYMENTS_DOCUMENTS.DESTINATION_RESOURCE%type,
        AMOUNT                  PAYMENTS_DOCUMENTS.DESTINATION_AMOUNT%type,
        CURRENCY                PAYMENTS_DOCUMENTS.DESTINATION_CURRENCY%type,
        LOGIN_ID                LOGINS.ID%type,
        IP_ADDRESS              LOGINS.IP_ADDRESS%type,
        LAST_IP_ADDRESS         LOGINS.LAST_IP_ADDRESS%type,
        IS_OWN_OPERATION        char(1)
    );
    type TClientTemplatesList is table of TClientTemplate;


    --Выставление в журнал фиктивной операции, для превышения лимита, для конкретного клиента
    function limit_fraud(
            f_login_id          in number,
            f_restriction_type  in varchar2,
                -- GROUP_RISK - лимит по группе риска
                -- DAYLY_OBSTRUCTION - заградительный лимит
                -- ALL_GROUPS_RISK  - лимит по всем нужным группам риска
            f_group_risk        in varchar2
                -- Группа риска:
                --  NORMAL
                --  HIGH
        ) return char;
        -- 0 - блокировка установлена
        -- 1 - ошибка в процессе получения данных по login_id клиента
        -- 2 - не найден активный лимит
        -- 3 - ошибка при добавлении лимита клиенту
        -- 4 - неверно задан тип ограничения

    --блокировка операций клиента с оповещением клиента
    function limit_fraud_unusual_ip(
        f_login_id          in number,
        f_restriction_type  in varchar2,
            -- GROUP_RISK - лимит по группе риска
            -- DAYLY_OBSTRUCTION - заградительный лимит
            -- ALL_GROUPS_RISK  - лимит по всем нужным группам риска
        f_group_risk        in varchar2,
            -- Группа риска:
            --  NORMAL
            --  HIGH
        f_message           in varchar2
            -- Сообщение в лог для сотрудника
    ) return char;
    -- 0 - блокировка установлена
    -- 1 - ошибка в процессе получения данных по login_id клиента
    -- 2 - не найден активный лимит
    -- 3 - ошибка при добавлении лимита клиенту
    -- 4 - неверно задан тип ограничения
	-- 5 - не удалось вставить запись для оповещения клиента


    --Получаем IP адреса с которых входил клиент за последние 6 месяцев
    function getIP(
            f_login_id          in number
        ) return security_pack.TIPInfoList pipelined;

    --Получение данных клиента
    function getClientProfile(
        f_login_id          in number
    ) return security_pack.TClientProfileList pipelined;


    --Получаем login_id клиента по Ф.И.О. + Д.Р. + Д.У.Л.+ ТБ
    --    f_SUR_NAME   - Фамилия
    --    f_FIRST_NAME - Имя
    --    f_PATR_NAME  - Отчество
    --    f_BIRTHDAY   - Дата рождения
    --    f_PASSPORT   - Документ удостоверяющий личность
    --    f_TB         - Тербанк, к которому относится клиент, если не задан клиент ищется во всех тербанках
    function getClientLogins(
      f_SUR_NAME     in   users.SUR_NAME%type,
      f_FIRST_NAME   in   users.FIRST_NAME%type,
      f_PATR_NAME    in   users.PATR_NAME%type,
      f_BIRTHDAY     in   users.BIRTHDAY%type,
      f_PASSPORT     in   varchar2,
      f_TB           in   varchar2
    ) return security_pack.TClientLoginsList pipelined;

    --Получаем шаблонов клиента
    function getTemplates(
            f_id          in number
        ) return security_pack.TClientTemplatesList pipelined;

    procedure BLOCK_FRAUD(
      arg_value IN varchar2,
      arg_type number,
      retval out varchar2,
      message_for_cc in varchar2,
      arg_emp_login in varchar2
    );

/*7. Получение карты входа клиента==============================================
    input:
        f_login_id(number)    -   LOGIN_ID клиента
    output:
        возможные значения:
            LAST_LOGON_CARD_NUMBER  VARCHAR2(20)    -   Карта последнего входа
            'null'                                  -   Профиль не найден
            'error'                                 -   Ошибка

*/
    function getLastLogonCardNumber(
        f_login_id  in number
    )
    return varchar2;
--==============================================================================

end security_pack;
/

create or replace 
PACKAGE BODY            SRB_IKFL."SECURITY_PACK" is

    LIMIT_ETYPE_GROUP_RISK   constant varchar2(10):='GROUP_RISK';
    LIMIT_ETYPE_AMOUNT       constant varchar2(33):='OBSTRUCTION_FOR_AMOUNT_OPERATIONS';
    LIMIT_ETYPE_IMSI         constant varchar2(4) :='IMSI';

    RESTRICTION_TYPE_AMOUNT     constant varchar2(13) :='AMOUNT_IN_DAY';
    RESTRICTION_TYPE_OPERATION  constant varchar2(22) :='OPERATION_COUNT_IN_DAY';

    --Идентификаторы групп риска в Сбербанк ОнЛ@йн
    NORMAL_GROUP_RISK       constant number(3):=7;
    HIGH_GROUP_RISK         constant number(3):=8;
    CONVERSION_GROUP_RISK   constant number(3):=9;
    SOCCARD_GROUP_RISK      constant number(3):=10;

    --Сумма лимита для группы риска
    AMOUNT_FOR_BLOCK        constant number:=3000000;
    --Сумма заградительного лимита
    AMOUNT_COM_BLOCK        constant number:=1000000;
    --Кол-во операций
    COUNT_FOR_BLOCK         constant number:=300;

    --Минимальный ID, с которого начинаем показывать шаблоны
    TEMPLATES_MIN_ID        constant number:=1000001000;
    --Кол-во показываемых шаблонов
    TEMPLATES_RNUM          constant number:=51;

    type TCommulativeLimit is record (
        EXTERNAL_ID             DOCUMENT_OPERATIONS_JOURNAL.EXTERNAL_ID%type,
        DOCUMENT_EXTERNAL_ID    DOCUMENT_OPERATIONS_JOURNAL.DOCUMENT_EXTERNAL_ID%type,
        OPERATION_DATE          DOCUMENT_OPERATIONS_JOURNAL.OPERATION_DATE%type,
        PROFILE_ID              DOCUMENT_OPERATIONS_JOURNAL.PROFILE_ID%type,
        AMOUNT                  DOCUMENT_OPERATIONS_JOURNAL.AMOUNT%type,
        AMOUNT_CURRENCY         DOCUMENT_OPERATIONS_JOURNAL.AMOUNT_CURRENCY%type,
        OPERATION_TYPE          DOCUMENT_OPERATIONS_JOURNAL.OPERATION_TYPE%type,
        CHANNEL_TYPE            DOCUMENT_OPERATIONS_JOURNAL.CHANNEL_TYPE%type,
        LIMITS_INFO             DOCUMENT_OPERATIONS_JOURNAL.LIMITS_INFO%type,
        EXTERNAL_CARD           DOCUMENT_OPERATIONS_JOURNAL.EXTERNAL_CARD%type,
        EXTERNAL_PHONE          DOCUMENT_OPERATIONS_JOURNAL.EXTERNAL_PHONE%type
    );

    procedure logMessage(header in varchar2, message in varchar2) as
    begin
        --dbms_output.put_line(' [D] '||rpad(header, 80)||message);
        null;
    end;

    --добавить ограничение для операций клиента
    procedure set_user_limit(gProfileId in number, gAmount in number, channelType in varchar2) is
    begin

        --мобильные приложения
        insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
            values(upper(sys_guid()), '10000000000000000000000000000001', sysdate, gProfileId, gAmount, 'RUB', 'commit', 'MOBILE_API', channelType, '', '');
        --интернет клиент
        insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
            values(upper(sys_guid()), '10000000000000000000000000000001', sysdate, gProfileId, gAmount, 'RUB', 'commit', 'INTERNET_CLIENT', channelType, '', '');
        --ВСП
        insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
            values(upper(sys_guid()), '10000000000000000000000000000001', sysdate, gProfileId, gAmount, 'RUB', 'commit', 'VSP', channelType, '', '');
        --КЦ
        insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
            values(upper(sys_guid()), '10000000000000000000000000000001', sysdate, gProfileId, gAmount, 'RUB', 'commit', 'CALL_CENTR', channelType, '', '');
        --устройство самообслуживания
        insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
            values(upper(sys_guid()), '10000000000000000000000000000001', sysdate, gProfileId, gAmount, 'RUB', 'commit', 'SELF_SERVICE_DEVICE', channelType, '', '');
        --ЕРМБ
        insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
            values(upper(sys_guid()), '10000000000000000000000000000001', sysdate, gProfileId, gAmount, 'RUB', 'commit', 'ERMB_SMS', channelType, '', '');

    end;

    --блокировка операций клиента
    function limit_fraud(
        f_login_id          in number,
        f_restriction_type  in varchar2,
            -- GROUP_RISK - лимит по группе риска
            -- DAYLY_OBSTRUCTION - заградительный лимит
            -- ALL_GROUPS_RISK  - лимит по всем нужным группам риска
        f_group_risk        in varchar2
            -- Группа риска:
            --  NORMAL
            --  HIGH
    ) return char is
    -- 0 - блокировка установлена
    -- 1 - ошибка в процессе получения данных по login_id клиента
    -- 2 - не найден активный лимит
    -- 3 - ошибка при добавлении лимита клиенту
    -- 4 - неверно задан тип ограничения

        pragma autonomous_transaction;

        tb_department_id      number;
        client_department_id  number;

        lProfileId number;
    begin
        --получаем id тербанка, к которому отностися клиент
        begin
            select id into lProfileId from users u where u.login_id=f_login_id;
        exception
            when NO_DATA_FOUND then return '1';
        end;

    case f_restriction_type
      when 'GROUP_RISK' then
        case f_group_risk
          when 'NORMAL' then set_user_limit(lProfileId,AMOUNT_FOR_BLOCK,'GROUP_RISK,AMOUNT_IN_DAY,F538FFCC7E59874BE044001517E40AF0;');
          when 'HIGH' then set_user_limit(lProfileId,AMOUNT_FOR_BLOCK,'GROUP_RISK,AMOUNT_IN_DAY,F538FFCC7E5A874BE044001517E40AF0;');
          else return '4';
        end case;
      when 'DAYLY_OBSTRUCTION' then set_user_limit(lProfileId,AMOUNT_FOR_BLOCK,'OBSTRUCTION_FOR_AMOUNT_OPERATIONS,AMOUNT_IN_DAY,;');
      when 'ALL_GROUPS_RISK' then set_user_limit(lProfileId,AMOUNT_FOR_BLOCK,'GROUP_RISK,AMOUNT_IN_DAY,F538FFCC7E59874BE044001517E40AF0;GROUP_RISK,AMOUNT_IN_DAY,F538FFCC7E5A874BE044001517E40AF0;');
      else return '4';
    end case;

        commit;
        return '0'; -- блокировка установлена

    exception
        when OTHERS then
            logMessage('ERROR ', SQLERRM);
            rollback;
            return '3';
    end;

    -- запись в лог оповещений
    procedure add_unusual_ip_notification(f_login_id in number, f_message in varchar2) is
		pragma autonomous_transaction;    
    begin
        insert into UNUSUAL_IP_NOTIFICATIONS (ID, DATE_CREATED, LOGIN_ID, ATTEMPTS_COUNT, MESSAGE)
            values (S_UNUSUAL_IP_NOTIFICATIONS.nextVal, sysdate, f_login_id, 0, f_message);
        commit;
    end;

    --блокировка операций клиента с оповещением клиента
    function limit_fraud_unusual_ip(
        f_login_id          in number,
        f_restriction_type  in varchar2,
            -- GROUP_RISK - лимит по группе риска
            -- DAYLY_OBSTRUCTION - заградительный лимит
            -- ALL_GROUPS_RISK  - лимит по всем нужным группам риска
        f_group_risk        in varchar2,
            -- Группа риска:
            --  NORMAL
            --  HIGH
        f_message           in varchar2
    ) return char is
    -- 0 - блокировка установлена
    -- 1 - ошибка в процессе получения данных по login_id клиента
    -- 2 - не найден активный лимит
    -- 3 - ошибка при добавлении лимита клиенту
    -- 4 - неверно задан тип ограничения
    -- 5 - не удалось вставить запись для оповещения клиента

        limit_fraud_result char;
    begin
        -- блокировка операций клиента
        limit_fraud_result := limit_fraud(f_login_id, f_restriction_type, f_group_risk);
        if (limit_fraud_result <> '0') then
            return limit_fraud_result;
        end if;
            
        begin
            add_unusual_ip_notification(f_login_id, f_message);
        exception
            when OTHERS then return '5';
        end;
        return '0';
    end;

    --Получение IP адресов, с которых входил клиент за последние 6 месяцев
    function getIP(
        f_login_id          in number
    ) return security_pack.TIPInfoList pipelined is
        ip TIPInfo;
    begin
        if (f_login_id is not null) then
          for ip in ( select LOGIN_DATE, LOGIN_ID, IP_ADDRESS, APPLICATION from INPUT_REGISTER_JOURNAL where LOGIN_ID=f_login_id and LOGIN_DATE > add_months(sysdate, -6))
          loop
              pipe row(ip);
          end loop;
        end if;
    end;

    --Получение данных клиента
    function getClientProfile(
        f_login_id          in number
    ) return security_pack.TClientProfileList pipelined is
    begin
        if (f_login_id is not null) then
            for cprofile in (
                        select
                          u.sur_name, u.first_name, u.patr_name, u.login_id,
                          u.birthday, u.birth_place, psd.email_address ,psd.mobile_phone,
                          u.gender,
                          coalesce(
                            (select DOC_SERIES||' '||DOC_NUMBER||' '||DOC_TYPE from documents where person_id=u.id and doc_type='REGULAR_PASSPORT_RF'),
                            (select DOC_SERIES||' '||DOC_NUMBER||' '||DOC_TYPE from documents where person_id=u.id and rownum < 2)
                          ) as document
                        from users u
                        left join personal_subscription_data psd on psd.login_id=u.login_id
                        where u.login_id=f_login_id
           )loop
                pipe row(cprofile);
            end loop;
        end if;
    end;

    --Получаем login_id клиента по Ф.И.О. + Д.Р. + Д.У.Л.+ ТБ
    --    f_SUR_NAME   - Фамилия
    --    f_FIRST_NAME - Имя
    --    f_PATR_NAME  - Отчество
    --    f_BIRTHDAY   - Дата рождения
    --    f_PASSPORT   - Документ удостоверяющий личность
    --    f_TB         - Тербанк, к которому относится клиент, если не задан клиент ищется во всех тербанках
    function getClientLogins(
      f_SUR_NAME     in   users.SUR_NAME%type,
      f_FIRST_NAME   in   users.FIRST_NAME%type,
      f_PATR_NAME    in   users.PATR_NAME%type,
      f_BIRTHDAY     in   users.BIRTHDAY%type,
      f_PASSPORT     in   varchar2,
      f_TB           in   varchar2
    ) return security_pack.TClientLoginsList pipelined is
    begin
        if (f_SUR_NAME is not null and f_PASSPORT is not null) then
          for cprofile in (
            select login_id from users u
            inner join documents d on d.person_id=u.id
            inner join departments dep on dep.id=u.department_id
            where
                upper(replace(replace(SUR_NAME||FIRST_NAME||PATR_NAME,' ',''),'-',''))=upper(replace(replace(f_SUR_NAME||f_FIRST_NAME||f_PATR_NAME,' ',''),'-',''))
            and replace(DOC_SERIES||DOC_NUMBER,' ','')=replace(f_PASSPORT,' ','')
            and (f_BIRTHDAY is null or BIRTHDAY=f_BIRTHDAY)
            and (f_TB is null or dep.tb=f_TB or dep.tb=decode(f_TB, '99', '38', '38', '99'))
          )loop
                pipe row(cprofile);
          end loop;
        end if;
    end;

    --Получаем шаблонов клиента
    function getTemplates(
      f_id          in number
    ) return security_pack.TClientTemplatesList pipelined is
      s_id number;
    begin
        if (f_id is not null) then
          s_id:=greatest(f_id, TEMPLATES_MIN_ID);
        else
          select max(id)-TEMPLATES_RNUM+1 into s_id from payments_documents where id > TEMPLATES_MIN_ID;
        end if;
        if (s_id is not null) then
            for templates in (
              select * from (
                select
                  pd.ID,
                  pd.CHARGEOFF_RESOURCE,
                  pd.DESTINATION_RESOURCE,
                  NVL(pd.DESTINATION_AMOUNT, pd.CHARGEOFF_AMOUNT) AMOUNT,
                  NVL(pd.DESTINATION_CURRENCY, pd.CHARGEOFF_CURRENCY) CURRENCY,
                  l.ID LOGIN_ID,
                  l.IP_ADDRESS,
                  l.LAST_IP_ADDRESS,
                  decode(pd.FORM_TYPE, 'INTERNAL_TRANSFER', 'Y', 'CONVERT_CURRENCY_TRANSFER', 'Y','N') IS_OWN_OPERATION
                from PAYMENTS_DOCUMENTS pd
                inner join USERS u on u.ID=pd.CLIENT_GUID
                inner join LOGINS l on l.ID=u.LOGIN_ID
                where
                    pd.ID> s_id
                order by pd.ID
              )
              where rownum < TEMPLATES_RNUM
            ) loop
                pipe row(templates);
            end loop;
        end if;
    end;

    procedure BLOCK_FRAUD(arg_value IN varchar2, arg_type number, retval out varchar2, message_for_cc in varchar2, arg_emp_login in varchar2) is
      lMessageForCC varchar2(512);
	  v_blocker_id number;
    begin

        /* 0 - поиск клиента по ip адресу входа
           1 - поиск клиента по карте (любая карта принадлежащая клиенту)
           2 - поиск клиента по его login_id
        */
      savepoint start_login_block;

      lMessageForCC:= substr(nvl(message_for_cc, 'Fraud-monitoring, зафиксирована вирусная активность в отношении клиента'), 1, 512);

		select nvl(l3.ID,nvl(l2.ID,l1.ID)) into v_blocker_id from (
			select ID from LOGINS
			where KIND = 'E' and DELETED = '0' and rownum <= 1
		) l1
		left join (
			select ID from LOGINS
			where USER_ID = 'Pinchuk-AM'
		) l2 on 1=1
		left join (
			select ID from LOGINS
			where USER_ID = nvl(arg_emp_login,'XXXXXX')
		) l3 on 1=1;

      case arg_type
        when 0 then
            begin
                for i in (select id from logins where IP_ADDRESS=arg_value) loop
                    insert into LOGIN_BLOCK (id, login_id, reason_type, reason_description, date_from, date_until, blocker_id) values
                    (s_login_block.NEXTVAL, i.id, 'employee', lMessageForCC, sysdate,null,v_blocker_id);
                    retval:=concat(concat(retval, i.id),', ');
                end loop;
                retval:=concat(retval, ' логины заблокированы');
           end;
        when 1 then
            begin

                insert into LOGIN_BLOCK (id, login_id, reason_type, reason_description, date_from, date_until, blocker_id) values
                    (s_login_block.NEXTVAL, (select login_id from card_links where card_number=arg_value), 'employee', lMessageForCC, sysdate,null,v_blocker_id);
                 retval:='клиент заблокирован';
            end;
        when 2 then
            begin

                insert into LOGIN_BLOCK (id, login_id, reason_type, reason_description, date_from, date_until, blocker_id) values
                    (s_login_block.NEXTVAL, arg_value, 'employee', lMessageForCC, sysdate,null,v_blocker_id);
                retval:='клиент заблокирован';
            end;
      end case;

      commit;
      exception
        when NO_DATA_FOUND then retval:='не найен логин по указанным параметрам';
        when OTHERS then
          begin
              if SQLCODE = -2291 then retval:='указанного логина не существует';
                  else if SQLCODE = -1422 then retval:='По указанным параметрам найдено более 1 клиента';
                      else retval:=concat('Другая ошибка ', SQLERRM);
                  end if;
              end if;
          end;
      rollback to start_login_block;
    end;

/*7. Получение карты входа клиента============================================*/
    function getLastLogonCardNumber(
        f_login_id  in number
    )
    return varchar2 is
        v_card_number varchar2(20);
    begin
        select LAST_LOGON_CARD_NUMBER into v_card_number from LOGINS where ID = f_login_id;
        return v_card_number;
    exception
        when NO_DATA_FOUND then return 'null';
        when others then return 'error';
    end;
--==============================================================================

end security_pack;
/