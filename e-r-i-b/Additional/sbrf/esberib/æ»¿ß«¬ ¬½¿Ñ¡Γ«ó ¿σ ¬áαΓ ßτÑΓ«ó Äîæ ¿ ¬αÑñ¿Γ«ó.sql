DECLARE
  --------------------ВХОДНЫЕ ПАРАМЕТРЫ ДЛЯ ГЕНЕРАЦИИ----------------------
  --********************Параметры для генерации клиентов*********************
  --Основная схема БД, с которой работает WEB-приложение 
  c_PrimarySchema constant varchar2(100) := 'SBRF_118';
  --Схема в которую должны складываться запросы к шине и ответы от неё(ENH031868: Заглушки с КСШ: убрать хаотичность)
  c_SecondarySchema constant varchar2(100) := 'ESBERIB';
  --Кол-во клиентов, которые будут сгенерированы
  c_UserCount constant number := 10;
  --Список статусов клиентов
  c_UserStatusList constant varchar2(100) := 'A,T'; /*"A" - активный клиент, может работать в системе
                                                      "D" - удаленный
                                                      "T" - клиент в процессе регистрации
                                                      "E" - ошибка расторжения
                                                      "W" - на расторжении
                                                      "S" - подписание договора*/
  c_ClientId constant varchar2(100) := 'phiz-gate-cod';
  --Идентификатор департамента
  c_DepartmentId constant number := '87';
  --Тип доступа
  c_AccessType constant varchar2(10) := 'simple';
  --Тип документа клиента
  c_DocType constant varchar2(20) := 'REGULAR_PASSPORT_RF';/*Общегражданский паспорт РФ*/
  c_DocTypeNumber constant varchar2(4) := '21'; /*Паспорт гражданина РФ*/
  --Список сервисов, которые по умолчанию будут доступны генерируемым клиентам 
  c_ServicesNameList constant varchar2(4000) := 'AccountAndCardInfo,LoanInfo,DepoAccountInfo,IMAccountInfoService,PFRService';
  --------------------------------------------------------------------------------

  --********************Параметры для генерации карт клиентов*********************
  --Кол-во карт генерируемые для клиента
  c_UserCardCount constant number := 2;
  --Список кодов статусов карт 
  c_CardStatusList constant varchar2(4000) := 'Active,Stoped,Blocked';/*Stoped – Неактивная,
                                                                        Active – Активная, 
                                                                        Blocked – Заблокированная*/  
  --Список идентификаторов систем-источников продукта
  c_SystemIdList constant varchar2(4000) := 'urn:sbrfsystems:99-way,urn:sbrfsystems:40-cod';
  --Список валют счета и кредита
  c_CurrencyList constant varchar2(4000) := '810,978,840'; /* 810 - RUR, 978 - EUR, 840 - USD*/
  --Список типов карт
  c_CardTypeList constant varchar2(4000) := 'DC,CC,OV';/*DC (debit) - дебетовая,
                                                         CC (credit) - кредитная,
                                                         OV (overdraft) - овердрафтная*/
  --Список вирруальных карт
  c_VirtualCardNumber constant varchar2(4000) := '427432,547932';/*карты начинающиеся на эти числа - виртуальные*/
  ----------------------------------------------------------------------------------

  --********************Параметры для генерации счетов клиентов*********************
  --Кол-во счетов генерируемых для клиента
  c_UserDepositCount constant number := 2;
  
  --Список статусов счета
  c_DepositStatusList constant varchar2(4000) := 'Closed,Opened,Lost-passbook,Arrested';/*Arrested должен быть в списке последним!!!
                                                                                          Opened – Открыт,
                                                                                          Closed – Закрыт, 
                                                                                          Arrested – Арестован, 
                                                                                          Lost-passbook – Утеряна сберкнижка.*/
  --Список масок счетов (с них могут начинаться счета)
  c_DepAccMaskList constant varchar2(4000) := '40817,40820,42301,42307,42601,42507';
  ----------------------------------------------------------------------------------
  
  --********************Параметры для генерации ОМС клиентов*********************
  --Кол-во ОМС генерируемых для клиента
  c_UserIMACount constant number := 2;
  --Список валют для ОМС, которые будут встречаться в генерируемых данных
  c_IMACurrencyList constant varchar2(4000) := 'A99,A98,A76,A33';/*A99 - Серебро в граммах, 
                                                                   A98 - Золото в граммах, 
                                                                   A76 - платина в граммах
                                                                   A33 - палладий в граммах*/
  -------------------------------------------------------------------------------
  
  --********************Параметры для генерации кредитов клиентов*********************
  --Кол-во кредитов генерируемых для клиента
  c_UserLoanCount constant number := 2;
  ------------------------------------------------------------------------------------
  
  l_ItemIndex     number:=0;
  l_LoginId       number;
  l_LoginId_Str   varchar2(4000);
  l_UserId        number;
  l_UserId_Str   varchar2(4000);
  l_RegistrationAddress number;
  l_RegAddrId_Str   varchar2(4000);
  l_Status        varchar2(1);
  l_ClientStatusCnt    number := 0;
  l_CardStatusCnt      number := 0;
  l_SystemIdCnt        number := 0;
  l_CurrencyCnt        number := 0;
  l_CardTypeCnt        number := 0;
  l_begin         number;
  l_AccessSchemes_Id number;
  l_AccessSchemes_Str varchar2(4000);
  l_ClientFirstName   varchar2(200);
  l_ClientSurName     varchar2(200);
  l_ClientPatrName    varchar2(200);
  l_birthday          timestamp(6);
  l_birthPlace        varchar2(200);
  l_PropertiesBlob    blob;
  l_PropertiesStr     varchar2(1000);
  l_MenuItemIndex     number := 0;
  l_CardItemIndex     number := 0;
  l_DepositItemIndex  number := 0;
  l_IMAItemIndex      number := 0;
  l_LoanItemIndex     number := 0;
  l_CardHolder        varchar2(200);
  l_DocSeries         varchar2(10);
  l_DocNumber         varchar2(10);
  l_PersonInfoId      number;
  l_UserCBCode        varchar2(20);
  l_CardSystemId      varchar2(100);
  l_CardNum           varchar2(100);
  l_CardId            number;
  l_GFLId             number;
  l_DepOffice         varchar2(10);
  l_DepOsb            varchar2(10);
  l_DepTb             varchar2(10);
  l_CARD_Ins          varchar2(4000);
  l_CardStatus        varchar2(100);
  l_Currency          varchar2(100);
  l_CardType          varchar2(100);
  l_SystemId          varchar2(100);

  l_DepositId         number;
  l_DepositStateCnt   number;
  l_DepAccMaskCnt     number;
  l_DepositState      number;
  l_DepositInterAcc   number;
  l_DepositSystemId   varchar2(20);
  l_DepositAcctId     number;
  l_ClientStatus      varchar2(10);
  
  l_IMAId             number;
  l_IMACurrencyCnt    number;
  l_IMACurrency       number;
  l_IMAState          number;
  l_IMAcctId          varchar2(22);
  
  l_CreditId          number;
  l_LoanAcctId        varchar2(24);
  l_LoanStartDate     date;
BEGIN
  --Текущую схему ту, с которой работает приложение 
  execute immediate 'alter session set current_schema = '||c_PrimarySchema;
  --удаление таблиц если есть необходимость
  declare t_name   VARCHAR2 (100);
  begin 
    for cur in (SELECT table_name FROM all_tables
                 WHERE LOWER (table_name) in ('client_status_table',
                                              'card_status_table',
                                              'system_id_table',
                                              'currency_list_table',
                                              'cardtype_list_table',
                                              'depostate_list_table',
                                              'depaccmask_list_table',
                                              'imacurrency_list_table')
                 and owner = c_PrimarySchema)
    loop
      EXECUTE IMMEDIATE 'truncate table '||cur.table_name;
      EXECUTE IMMEDIATE 'drop table '||cur.table_name;
    end loop; 
      
  exception when OTHERS then NULL;
  end;
  
  begin
  /*Создание вспомогательного типа*/
  execute immediate 'CREATE OR REPLACE TYPE STRING_LIST_TYPE IS TABLE OF VARCHAR2(4000)';
  /*Функция преобразования строки с разделителем в таблицу*/
  execute immediate 
    'create or replace function str2tbl(pStr   in varchar2 /*строка для разбора*/
                                      , pDelim in varchar2 default '','' /*разделитель*/
                                       ) return STRING_LIST_TYPE is
       l_str      long default pStr || pDelim;
       l_n        number;
       l_data     STRING_LIST_TYPE := STRING_LIST_TYPE();  
     begin
       loop
         l_n := instr( l_str, pDelim );
         exit when (nvl(l_n,0) = 0);
         l_data.extend;
         l_data(l_data.count) := ltrim(rtrim(substr(l_str,1,l_n-1)));
         l_str := substr(l_str, l_n+length(pDelim));
       end loop;

       return l_data;
     end str2tbl;';
  --1.Вспомогательная таблица со статусами клиентов
  execute immediate 'create table client_status_table as '||
                       'select rownum num, s.column_value as status '||
                         'from table (cast (str2tbl('''||c_UserStatusList||''','','') as STRING_LIST_TYPE)) s';
  l_ClientStatusCnt := SQL%ROWCOUNT;

  --2.Вспомогательная таблица со статусами карт
  execute immediate 'create table card_status_table as '||
                       'select rownum num, s.column_value as status '||
                         'from table (cast (str2tbl('''||c_CardStatusList||''','','') as STRING_LIST_TYPE)) s';
  l_CardStatusCnt := SQL%ROWCOUNT;
  
  --3.Вспомогательная таблица с идентификаторами систем-источников продукта
  execute immediate 'create table system_id_table as '||
                       'select rownum num, s.column_value as system_id '||
                         'from table (cast (str2tbl('''||c_SystemIdList||''','','') as STRING_LIST_TYPE)) s';
  l_SystemIdCnt := SQL%ROWCOUNT;
  
  --4.Вспомогательная таблица со списком валют
  execute immediate 'create table currency_list_table as '||
                       'select rownum num, s.column_value as currency_code '||
                         'from table (cast (str2tbl('''||c_CurrencyList||''','','') as STRING_LIST_TYPE)) s';
  l_CurrencyCnt := SQL%ROWCOUNT;  
  
  --5.Вспомогательная таблица со списком типов карт
  execute immediate 'create table cardtype_list_table as '||
                       'select rownum num, s.column_value as card_type '||
                         'from table (cast (str2tbl('''||c_CardTypeList||''','','') as STRING_LIST_TYPE)) s';
  l_CardTypeCnt := SQL%ROWCOUNT;
  
  --6.Вспомогательная таблица со списком статусов счетов
  execute immediate 'create table depostate_list_table as '||
                       'select rownum num, s.column_value as deposit_state '||
                         'from table (cast (str2tbl('''||c_DepositStatusList||''','','') as STRING_LIST_TYPE)) s';
  l_DepositStateCnt := SQL%ROWCOUNT;

  --7.Вспомогательная таблица со списком масок счетов
  execute immediate 'create table depaccmask_list_table as '||
                       'select rownum num, s.column_value as depacc_mask '||
                         'from table (cast (str2tbl('''||c_DepAccMaskList||''','','') as STRING_LIST_TYPE)) s';
  l_DepAccMaskCnt := SQL%ROWCOUNT;
  
  --8.Вспомогательная таблица со списком валют для ОМС
  execute immediate 'create table imacurrency_list_table as '||
                       'select rownum num, s.column_value as currency_code '||
                         'from table (cast (str2tbl('''||c_IMACurrencyList||''','','') as STRING_LIST_TYPE)) s';
  l_IMACurrencyCnt := SQL%ROWCOUNT;
  
  --Проверка входных параметров
  if l_ClientStatusCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные статусы клиентов: '||c_UserStatusList);
    execute immediate 'drop table client_status_table';
  end if;
  if l_CardStatusCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные статусы карт: '||c_CardStatusList);
    execute immediate 'drop table card_status_table';
  end if;
  if l_SystemIdCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные идентификаторы систем-источников продукта: '||c_SystemIdList);
    execute immediate 'drop table system_id_table';
  end if;
  if l_CurrencyCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные коды валют: '||c_CurrencyList);
    execute immediate 'drop table currency_list_table';
  end if;
  if l_CardTypeCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные типы карт: '||c_CardTypeList);
    execute immediate 'drop table cardtype_list_table';
  end if;
  if l_DepositStateCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные статусы счетов: '||c_DepositStatusList);
    execute immediate 'drop table c_DepositStatusList';
  end if;
  if l_DepAccMaskCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные маски счетов: '||c_DepAccMaskList);
    execute immediate 'drop table depaccmask_list_table';
  end if;
  if l_IMACurrencyCnt = 0 then
    RAISE_APPLICATION_ERROR(-20900,'Ошибка обработки входных параметров. Некорректные коды валют для ОМС: '||c_IMACurrencyList);
    execute immediate 'drop table imacurrency_list_table';
  end if;
  
 
  execute immediate 'grant select on client_status_table to '|| c_SecondarySchema;
  execute immediate 'grant select on card_status_table to '|| c_SecondarySchema;
  execute immediate 'grant select on system_id_table to '|| c_SecondarySchema;
  execute immediate 'grant select on currency_list_table to '|| c_SecondarySchema;
  execute immediate 'grant select on cardtype_list_table to '|| c_SecondarySchema;
  execute immediate 'grant select on depostate_list_table to '|| c_SecondarySchema;
  execute immediate 'grant select on depaccmask_list_table to '|| c_SecondarySchema;
  execute immediate 'grant select on imacurrency_list_table to '|| c_SecondarySchema;
     
  --Генерация клиентов       
  loop
    --Текущая схема
    execute immediate 'alter session set current_schema = '||c_PrimarySchema;
	
    --Вставка схемы доступа
    execute immediate 'select s_accessschemes.nextval from dual' into l_AccessSchemes_Id;
    execute immediate
    'insert into accessschemes(id '||
                            ', name '||
                            ', category '||
                            ', personal '||
                            ', ca_admin_scheme) '||
           'values(:l_AccessSchemes_Id '||
                ', ''personal'' '||
                ', ''C'' '|| /*"C"-Категория отосящаяся к клиентам банка*/
                ', 1 '||
                ', 0)'
           using l_AccessSchemes_Id;

  --Вставка связок схемы доступа и сервисов
  execute immediate 
    'insert into schemesservices '||
         'select distinct :l_AccessSchemes_Id, s.id '||
           'from table (cast(str2tbl(:c_ServicesNameList,'','') as STRING_LIST_TYPE)) serv '||
              ', services s '||
              ', schemesservices ss '||
          'where upper(s.service_key) = upper(serv.column_value) '||
            'and ss.scheme_id (+)= :l_AccessSchemes_Id '||
            'and ss.service_id (+) = s.id '||
            'and ss.scheme_id is null ' 
     using l_AccessSchemes_Id, c_ServicesNameList, l_AccessSchemes_Id;

  --Данные по департаменту
  execute immediate 'select d.office, d.osb, d.tb from departments d where d.id = '||c_DepartmentId 
               into l_DepOffice, l_DepOsb, l_DepTb;
     
    l_ItemIndex := l_ItemIndex +1;    
    --Логин клиента
    execute immediate 'select s_logins.nextval from dual' into l_LoginId;
    
    l_UserCBCode := lpad(l_DepTb,2,'0')||
                    lpad(trunc(dbms_random.value(1,99)),2,'0')||
                    lpad(trunc(dbms_random.value(1,99)),2,'0')||
                    lpad(trunc(dbms_random.value(1,99)),2,'0');
    --Тип клиента
    l_ClientStatus := case when l_ItemIndex <= c_UserCount*0.3 then 'UDBO' else 'SBOL' end;
    
    --Добавление логина
    execute immediate
      'insert into logins(id '||
                       ', user_id '||
                       ', kind '||
                       ', wrong_logons '||
                       ', deleted '||
                       ', is_mobile_bank_connected '||
                       ', is_first '||
                       ', cb_code) '||
            'values(:l_LoginId '||
                 ', substr(:l_ClientStatus||''_''||rpad(:l_LoginId,10,''0''),1,50) '||
                 ', ''C'' '||
                 ', 0 '||
                 ', 0 '||
                 ', 0 '||
                 ', 1 '||
                 ', :l_UserCBCode '||
                  ') '
           using l_LoginId, l_ClientStatus, l_LoginId, l_UserCBCode;
    
    --Добавление адреса регистрации и места жительства
    execute immediate 'select s_address.nextval from dual' into l_RegistrationAddress;
    execute immediate
      'insert into address(id,unparseable) '||
           'values (:l_RegistrationAddress,''г.Москва, ул.Пришвина, д.8'') '
        using l_RegistrationAddress;

    --Идентификатор клиента
    execute immediate 'select s_users.nextval from dual' into l_UserId;
    --Статус клиента
    execute immediate 
       'select s.status from client_status_table s '||
        'where s.num = trunc(dbms_random.value(1,'||l_ClientStatusCnt||'))' into l_Status;
            
    --Имя
    l_ClientFirstName := 'Имя клиента '||DBMS_RANDOM.string('a',8)||' generated';
    --Фамилия
    l_ClientSurName := 'Фамилия клиента '||DBMS_RANDOM.string('a',8)||' generated';
    --Отчество
    l_ClientPatrName := 'Отчество клиента '||DBMS_RANDOM.string('a',8)||' generated';
    --Дата рождения
    l_birthday := cast(trunc(sysdate - 365*dbms_random.value(20,80)) as timestamp);
    --Место рождения
    l_birthPlace := 'Место рождения клиента '||DBMS_RANDOM.string('a',8)||' generated';
    --Доавление клиента
    l_CardHolder := l_ClientSurName;
    
    execute immediate
      'insert into users(id '||
                      ', client_id '||
                      ', login_id '||
                      ', status '||
                      ', sms_format '||
                      ', first_name '||
                      ', sur_name '||
                      ', patr_name '||
                      ', birthday '||
                      ', birth_place '||
                      ', registration_address '||
                      ', residence_address '||
                      ', mobile_phone '||
                      ', mobile_operator '||
                      ', state '||
                      ', department_id '||
                      ', residental '||
                      ', creation_type '||
                      ', last_update_date '||
                      ', mdm_state '||
                      ', use_internet_security) '||
            'values(:l_UserId '||
                 --Идентификатор клиента во внешней системе
                 ', substr(DBMS_RANDOM.string(''a'',10)||trunc(dbms_random.value(1,1000))|| '||
                          'DBMS_RANDOM.string(''a'',5)||trunc(dbms_random.value(1,100))|| '||
                          'DBMS_RANDOM.string(''a'',10), 1, trunc(dbms_random.value(10,40)))||:c_ClientId '||
                 ', :l_LoginId '||
                 --Статус клиента определяется рандомом
                 ', :l_Status '||
                 ', 0 '||
                 ', :l_ClientFirstName '||
                 ', :l_ClientSurName '||
                 ', :l_ClientPatrName '||
                 ', :l_birthday '||/*Дата рождения*/
                 ', :l_birthPlace '||/*Место рождения*/
                 ', :l_RegistrationAddress '|| /*Адрес регистрации*/
                 ', :l_RegistrationAddress '|| /*Место жительства*/
                 ', ''9031234567'' '|| /*мобильный*/
                 ', ''Сберлайн'' '||
                 ', :l_Status '||
                 ', :c_DepartmentId '||
                 ', 0 '||
                 ', :l_ClientStatus '||
                 ', SYSDATE '||
                 ', ''NOT_SENT'' '||
                 ', 1 '||
                  ') '
         using l_UserId, c_ClientId, l_LoginId, l_Status, l_ClientFirstName, l_ClientSurName, l_ClientPatrName, l_birthday, 
               l_birthPlace, l_RegistrationAddress, l_RegistrationAddress, l_Status, c_DepartmentId, l_ClientStatus;
    
    l_DocSeries := lpad(trunc(dbms_random.value(1,99)),2,'0')||lpad(trunc(dbms_random.value(1,99)),2,'0');
    l_DocNumber := lpad(trunc(dbms_random.value(1,99)),2,'0')||lpad(trunc(dbms_random.value(1,99)),2,'0')||
                   lpad(trunc(dbms_random.value(1,99)),2,'0');
    --Вставка докуметов клиента
    execute immediate
    'insert into documents(id '||
                        ', doc_type '||
                        ', doc_number '||
                        ', doc_series '||
                        ', doc_issue_date '||
                        ', doc_main '||
                        ', person_id '||
                        ', doc_identify) '||
           'values (s_documents.nextval '||
                 ', :c_DocType '||
                 ', :l_DocNumber '|| /*номер*/
                 ', :l_DocSeries '|| /*серия*/
                 ', null '|| /*', :l_birthday '|| /*дата выдачи*/
                 ', 1 '||
                 ', :l_UserId '||
                 ', 1) '
          using c_DocType, l_DocNumber, l_DocSeries, /*l_birthday+14*365,*/ l_UserId;
    
    --Вставка профиля клиента
    execute immediate
      'insert into profile(login_id, id) '||
           'values(:l_LoginId, s_profile.nextval) '
         using l_LoginId;

    --Вставка пароля
    execute immediate
      'insert into passwords(id '||
                          ', kind '||
                          ', login_id '||
                          ', value '||
                          ', active '||
                          ', issue_date '||
                          ', expire_date '||
                          ', need_change) '||
           'values(s_passwords.nextval '||
                ', ''pu'' '||
                ', :l_LoginId '||
                ', ''AD39A3788F1F7F6B7DB530291C67DB20A74D8AA1CEEB110B2599DF5448C6D9B2'' '|| /*Это пароль a1d3g5j7*/
                ', 1 '||
                ', cast(sysdate as timestamp) '||
                ', cast(to_date(''01.01.3000'',''dd.mm.yyyy'') as timestamp) '||
                ', ''0'') '
          using l_LoginId;
    
    --Вставка принадлежности клиента к схеме доступа
    execute immediate
      'insert into schemeowns(id '||
                           ', scheme_id '||
                           ', login_id '||
                           ', access_type) '||
             'values(s_schemeowns.nextval '||
                  ', :l_AccessSchemes_Id '||
                  ', :l_LoginId '||
                  ', :c_AccessType) '
         using l_AccessSchemes_Id, l_LoginId, c_AccessType;
    
    l_PropertiesStr := '<?xml version="1.0" encoding="UTF-8"?> '||
                       '<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"> '||
                       '<properties> '||
                         '<entry key="simple-auth-choice">lp</entry> '||
                         '<entry key="secure-confirm-choice">smsp</entry>'||
                         '<entry key="userOptionType">sms</entry>'||
                       '</properties>';
                       
    --Вставка настройки политики безопасности
    execute immediate
      'insert into authentication_modes(id '||
                                     ', login_id '||
                                     ', access_type '||
                                     ', properties) '||
            'values(s_authentication_modes.nextval '||
                 ', :l_LoginId '||
                 ', :c_AccessType '||
                 ', RAWTOHEX(:l_PropertiesStr)) '
         using l_LoginId, c_AccessType, l_PropertiesStr;
    
    --Вставка настроек главного менню клиента
    l_MenuItemIndex := 0;
    loop
      l_MenuItemIndex := l_MenuItemIndex + 1;
      execute immediate
      'insert into menu_links(id '||
                           ', login_id '||
                           ', link_id '||
                           ', order_ind '||
                           ', is_use) '||
             'values(s_menu_links.nextval '||
                  ', :l_LoginId '||
                  ', :l_MenuItemIndex '||
                  ', :l_MenuItemIndex '||
                  ', 1) '
         using l_LoginId, l_MenuItemIndex, l_MenuItemIndex;
       exit when l_MenuItemIndex >= 6;
    end loop;    
    
    --Статус клиента
    execute immediate 
            'select s.status from client_status_table s '||
             'where s.num = trunc(dbms_random.value(1,'||l_ClientStatusCnt||'))' into l_Status;
    
    execute immediate 'alter session set current_schema = '||c_SecondarySchema;

    --Вставка информации о физ.лице (о клиенте)
    execute immediate 'select s_personinfo.nextval from dual' into l_PersonInfoId;
    execute immediate
      'insert into PERSONINFO '||
           'select :l_PersonInfoId '||
                ', rpad(:l_UserId||trunc(dbms_random.value(1,1000)),20,''0'') '||
                ', :l_birthday '||
                ', :l_birthPlace '||
                ', null '||
                ', null '||
                ', :l_ClientSurName '||
                ', :l_ClientFirstName '||
                ', :l_ClientPatrName '||
                ', :c_DocTypeNumber '||
                ', :l_DocSeries '||
                ', :l_DocNumber '||
                ', null '||
                ', null '||
                ', :l_birthday+14*365 '||/*дата выдачи*/
                ', null '||
                ', null '||
                ', null '||
                ', ''N'' '||
             'from dual '
        using l_PersonInfoId, l_UserId, l_birthday, l_birthPlace, l_ClientSurName, l_ClientFirstName, l_ClientPatrName, c_DocTypeNumber, l_DocSeries, l_DocNumber, l_birthday;
    --Вставка запроса в GFL
    execute immediate 'select s_gfl.nextval from dual' into l_GFLId;
    execute immediate
      'insert into gfl '||
           'select :l_GFLId '||
                ', substr(:l_UserCBCode,1,8) '||
                ', cast(:l_birthday as date) '||
                ', :l_ClientSurName '||
                ', :l_ClientFirstName '||
                ', :l_ClientPatrName '||
                ', :c_DocTypeNumber '||
                ', :l_DocSeries '||
                ', :l_DocNumber '||
                ', null '||
                ', null '|| /*', cast(:l_birthday+14*365 /*дата выдачи as date) '||*/
                ', null '||
             'from dual '
         using l_GFLId, l_UserCBCode, l_birthday, l_ClientSurName, l_ClientFirstName, l_ClientPatrName, c_DocTypeNumber, l_DocSeries, l_DocNumber/*, l_birthday*/;
       
    --Вставка ответа шины на запрос GFL (BankAcctInqRq) по картам

    /******************ДОБАВЛЕНИЕ КАРТ КЛИЕНТА - начало********************/
    l_CardItemIndex := 0;
    loop
      l_CardItemIndex := l_CardItemIndex + 1;
      l_CardStatus := trunc(dbms_random.value(1,l_CardStatusCnt+0.9));
      l_Currency := trunc(dbms_random.value(1,l_CurrencyCnt+0.9));
      l_CardType := trunc(dbms_random.value(1,l_CardTypeCnt+0.9));
      l_SystemId := trunc(dbms_random.value(1,l_SystemIdCnt+0.9));
      
      execute immediate 'select s_cards.nextval from dual' into l_CardId;
      execute immediate 
        'insert into CARDS '||
             'select c.card_id '||
                  /*Номер филиала, в котором ведется договор. Не может быть пустым*/   
                  ', :l_DepOffice '||
                  /*Номер ОСБ*/
                  ', :l_DepOsb '||
                  /*Номер тербанка(ТБ) */
                  ', :l_DepTb '||
                  /*Номер ОСБ, ведущего счет карты*/
                  ', :l_DepTb_DepOsb '||
                  /*Статус карты рандомом*/
                  ', (select s.status from '||c_PrimarySchema||'.card_status_table s where s.num = :l_CardStatus) '||
                  /*Идентификатор системы-источника карты*/
                  ', c.system_id '||
                  /*Номер карты рандомом*/
                  ', substr(c.card_num||c.card_id,1,16) '||
                  /*Номер карточного счета (кроме кредитных)*/
                  ', (case when c.card_type <> ''CC'' then '||
                  ' ''42301''||c.currency_code||trunc(dbms_random.value(100000,999999))||lpad(trunc(dbms_random.value(1,99)),2,''0'')||trunc(dbms_random.value(1000,9999)) '||
                  'end) '||
                  /*вид вклада*/
                  ', 123 '|| 
                  /*подвид вклада*/
                  ', 234 '|| 
                  /*Тип карты рандомом*/
                  ', c.card_type '||
                  /*Наименование карты*/
                  ', ''Visa Classic generated'' '||
                  /*Валюта*/
                  ', decode(c.currency_code,810,''RUR'',978,''EUR'',''USD'') '||
                  /*Срок действия карты*/
                  ', c.EndDt '||
                  /*Дата платежа (приходит только для кредитных карт)*/
                  ', (case when c.card_type = ''CC'' then c.IssDt+1 end) '||
                  /*Дата выпуска карты*/
                  ', c.IssDt '||
                  /*Имя на карте*/
                  ', :l_CardHolder '||
                  /*Номер основной карты (только для дополнительной)*/
                  ', null '|| 
                  /*Тип дополнительной карты (только для дополнительной)*/
                  ', null '|| 
                  /*Срок действия карты в формате процессинга YYMM */
                  ', to_char(c.EndDt,''YYMM'') '||
                  /*Тип остатка*/
                  ', ''Avail-''||trunc(dbms_random.value(20000,999999))|| '||
                    '(case '||
                       /*если карта виртуальная*/
                       'when substr(c.card_num,1,6) in (:c_VirtualCardNumber) then null '||
                     'else '||
                       ''',AvailCash-''||trunc(dbms_random.value(20000,999999))|| '||
                       ''',AvailPmt-''||trunc(dbms_random.value(20000,999999))|| '||
                       'case '||
                         'when c.card_type in (''CC'',''OV'') then '||
                           ''',Debt-''||trunc(dbms_random.value(20000,999999))|| '||
                           ''',MinPmt-''||trunc(dbms_random.value(20000,999999))|| '||
                           ''',CR_LIMIT-''||trunc(dbms_random.value(20000,999999))|| '||
                           ''',OWN_BALANCE-''||trunc(dbms_random.value(20000,999999)) '||
                       'end '||
                     'end) '||
                  ', :l_PersonInfoId '||
                  ', null '||
               'from (select :l_CardId card_id '||
                         ', trunc(dbms_random.value(10,99))||trunc(dbms_random.value(100000000000000,999999999999999)) card_num '||
                         ', (select s.currency_code from '||c_PrimarySchema||'.currency_list_table s where s.num = :l_Currency) currency_code '||
                         ', (select upper(s.card_type) from '||c_PrimarySchema||'.cardtype_list_table s where s.num = :l_CardType) card_type '||
                         ', (select s.system_id from '||c_PrimarySchema||'.system_id_table s where s.num = :l_SystemId) system_id '||
                         ', trunc(sysdate - 365*dbms_random.value(1,3)) IssDt '||
                         ', trunc(sysdate + 365*dbms_random.value(1,3.9)) EndDt '||
                       'from dual) c '
           using l_DepOffice, l_DepOsb, l_DepTb, l_DepTb||l_DepOsb, l_CardStatus, l_CardHolder, c_VirtualCardNumber, 
                 l_PersonInfoId, l_CardId, l_Currency, l_CardType, l_SystemIdCnt;
      execute immediate
        'select c.SYSTEMID, c.CARDNUM '||
          'from CARDS c '||
         'where c.id = '||l_CardId 
         into l_CardSystemId, l_CardNum;
         
      --Вставка связки GFL запроса и карты
      execute immediate    
          'insert into gfl_products '||
                'select s_gfl_product.nextval '||
                     ', :l_GFLId '||
                     ', :l_CardId '||
                     ', ''Card'' '|| /*карты*/
                  'from dual '
              using l_GFLId, l_CardId;

      --Вставка запроса в CRDWI (получение детальной выписки по карте)
      execute immediate    
          'insert into crdwi '||
               'select s_crdwi.nextval '||
                    ', substr(:l_UserCBCode,1,8) '||
                    ', :l_CardSystemId '||
                    ', :l_CardNum '||
                    ', null '||/*', substr(:l_UserCBCode,1,6) '||*/
                    ', :l_CardId '||
                 'from dual '
               using l_UserCBCode, l_CardSystemId, l_CardNum, /*l_UserCBCode,*/ l_CardId;
       exit when l_CardItemIndex >= c_UserCardCount;
    end loop;
    /******************ДОБАВЛЕНИЕ КАРТ КЛИЕНТА - конец********************/
    
    /******************ДОБАВЛЕНИЕ СЧЕТОВ КЛИЕНТА - начало********************/
    l_DepositItemIndex := 0;
    loop
      l_DepositItemIndex := l_DepositItemIndex + 1;
      execute immediate 'select s_deposit.nextval from dual' into l_DepositId;
      l_Currency := trunc(dbms_random.value(1,l_CurrencyCnt+0.9));
      l_DepositState := trunc(dbms_random.value(1,l_DepositStateCnt+0.9));
      l_DepositInterAcc := trunc(dbms_random.value(1,l_DepAccMaskCnt+0.9));
      l_DepositSystemId := 'systemX';
      --Вставка счета клиента
      execute immediate
        'insert into deposit '||
               'select :l_DepositId '||
                      /*Номер филиала, в котором открыт счет*/
                    ', :l_DepOffice '||
                    /*Идентификатор отделения (ОСБ), в котором открыт счет*/
                    ', :l_DepOsb '||
                    /*Номер тербанка(ТБ) */
                    ', :l_DepTb '||
                    /*Номер ОСБ, ведущего счет по вкладу*/
                    ', :l_DepTb_DepOsb '||
                    /*Идентификатор системы-источника продукта*/
                    ', :l_DepositSystemId '||
                    /*Номер счета*/
                    ', ''42301''||c.currency_code||trunc(dbms_random.value(100000,999999))||lpad(trunc(dbms_random.value(1,99)),2,''0'')||trunc(dbms_random.value(1000,9999)) '||
                    /*Валюта счета*/
                    ', decode(c.currency_code,810,''RUR'',978,''EUR'',''USD'') '||
                    /*Краткое наименование счета*/
                    ', ''Вклад сберегательный generated'' '||
                    /*Вид вклада*/
                    ', 123 '||
                    /*Подвид вклада*/
                    ', 456 '||
                    /*Дата открытия счета*/
                    ', c.OpenDate '||
                    /*Статус счета (рандомом)*/
                    ', case when :l_ClientStatus = ''SBOL'' then ''Opened'' else c.deposit_state end'||
                    /*Информация, предназначенная для перечисления процентов. Номер счета*/
                    ', c.depoacc_mask||c.currency_code||trunc(dbms_random.value(100000,999999))||lpad(trunc(dbms_random.value(1,99)),2,''0'')||trunc(dbms_random.value(1000,9999)) '||
                    /*Информация, предназначенная для перечисления процентов. Номер карты*/
                    ', substr(c.card_num||:l_DepositId,1,16) '||
                    /*Срок вклада в днях */
                    ', 180 '||
                    /*Процентная ставка по вкладу*/
                    ', trunc(dbms_random.value(0.001,0.099),4) '||
                    /*Дата закрытия счета*/
                    ', case when c.deposit_state = ''Closed'' then least(c.OpenDate + 365*dbms_random.value(1,3),sysdate) end '||
                    /*Разрешено ли списание со счета*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Разрешено ли зачисление на счет*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Разрешена ли пролонгация на следующий срок*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Разрешено ли списание со счета в других ОСБ ( признак «Зеленая улица»)*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Разрешено ли зачисление на счет в других ОСБ (признак «Зеленая улица»)*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Признак наличия сберкнижки*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Права доступа к счету или карте. Для ЦОД обязатльно 2. Указывается код фронтальной системы, для которой проставляется право, значения фиксированные («BP_ES» - СмартВиста, «BP_ERIB» - СБОЛ),  а также признак отсутствия указанного права(Если false – то счет виден). Записывается в виде двух пар формата [код] - [признак], перечисленных через запятую.*/
                    ', ''BP_ES - false, BP_ERIB - false'' '||
                    /*Тип остатка*/
                    ', ''Avail-''||trunc(dbms_random.value(20000,999999))||'',AvailCash-''||trunc(dbms_random.value(10000,20000))||'',MinAvail-''||trunc(dbms_random.value(1000,9999)) '||
                    /*Информация о владельце*/
                    ', :l_PersonInfoId '||
                    /*Требуется ли  отображать в системе дополнительную информацию по вкладу (тег DepAccInfo выходных данных)*/
                    ', trunc(dbms_random.value(0,1.9)) '||
                    /*Требуется ли возвращать информацию о правах доступа к счету (тег BankAcctPermiss выходных данных)*/
                    ', 1 '||
                 'from (select (select s.currency_code from '||c_PrimarySchema||'.currency_list_table s where s.num = :l_Currency) currency_code '||
                            ', (select s.deposit_state from '||c_PrimarySchema||'.depostate_list_table s where s.num = :l_DepositState) deposit_state '||
                            ', (select s.depacc_mask from '||c_PrimarySchema||'.depaccmask_list_table s where s.num = :l_DepositInterAcc) depoacc_mask '||
                            ', trunc(dbms_random.value(10,99))||trunc(dbms_random.value(100000000000000,999999999999999)) card_num '||
                            ', trunc(sysdate - 365*dbms_random.value(1,3.9)) OpenDate '||
                         'from dual) c '
          using l_DepositId, l_DepOffice, l_DepOsb, l_DepTb, l_DepTb||l_DepOsb, l_DepositSystemId, l_ClientStatus, l_DepositId, l_PersonInfoId, l_Currency
              , l_DepositState, l_DepositInterAcc;
      
      --Вставка связки GFL и счета клиента
      execute immediate
        'insert into gfl_products '||
             'select s_gfl_product.nextval '||
                  ', :l_GFLId '||
                  ', :l_DepositId '||
                  ', ''Deposit'' '||
               'from dual '
          using l_GFLId, l_DepositId;

      execute immediate 'select d.acctid from deposit d where d.id ='||l_DepositId into l_DepositAcctId;

      --Вставка запроса в ACC_DI (получение детальной информации по вкладу, счету)
      execute immediate
        'insert into acc_di '||
             'select s_accdi.nextval '||
                  ', substr(:l_UserCBCode,1,8) '||
                  ', :l_DepositSystemId '||
                  ', :l_DepositAcctId '||
                  ', substr(:l_DepTb_DepOsb,1,6) '||
                  ', :l_DepositId '||
               'from dual '
           using l_UserCBCode, l_DepositSystemId, l_DepositAcctId, l_DepTb||l_DepOsb, l_DepositId;
      exit when l_DepositItemIndex >= c_UserDepositCount;
    end loop;
    /******************ДОБАВЛЕНИЕ СЧЕТОВ КЛИЕНТА - конец********************/

    /******************ДОБАВЛЕНИЕ ОМС КЛИЕНТА - начало********************/
    l_IMAItemIndex := 0;
    loop
      l_IMAItemIndex := l_IMAItemIndex + 1;
      --Встаавка ОМС клиента
      execute immediate 'select s_imaccount.nextval from dual' into l_IMAId;
      l_SystemId := trunc(dbms_random.value(1,l_SystemIdCnt+0.9));
      l_IMACurrency := trunc(dbms_random.value(1,l_IMACurrencyCnt+0.9));
      l_IMAState := trunc(dbms_random.value(1,l_DepositStateCnt-0.1));
      l_IMAcctId := to_char(trunc(dbms_random.value(1000000000000000000000,9999999999999999999999)));
      execute immediate
        'insert into imaccount '||
             'select :l_IMAId '||
                    /*Номер филиала, в котором ведется договор. Не может быть пустым*/   
                  ', :l_DepOffice '||
                    /*Номер ОСБ*/
                  ', :l_DepOsb '||
                    /*Номер тербанка(ТБ) */
                  ', :l_DepTb '||
                    /*Номер ОСБ, ведущего счет карты*/
                  ', :l_DepTb_DepOsb '||
                    /*Идентификатор системы-источника продукта*/
                  ', (select s.system_id from '||c_PrimarySchema||'.system_id_table s where s.num = :l_SystemId) system_id '||
                    /*Номер счета*/
                  ', :l_IMAcctId '||
                   /*Валюта счета ОМС*/
                  ', c.currency_code '||
                   /*Краткое наименование счета ОМС*/
                  ', case when c.currency_code = ''A99'' then ''Серебро в граммах generated'' '||
                         'when c.currency_code = ''A98'' then ''Золото в граммах generated'' '||
                         'when c.currency_code = ''A76'' then ''Платина в граммах generated'' '||
                    'else ''Палладий в граммах generated''/*A33*/ end '||
                   /*Дата открытия счета*/  
                  ', c.start_date '||
                   /*Дата закрытия счета*/
                  ', case when c.ima_state = ''Closed'' then least(c.start_date + 365*dbms_random.value(1,3),sysdate) end '||
                   /*Статус счета*/
                  ', c.ima_state '||
                   /*Номер договора счета ОМС*/
                  ', to_char(trunc(dbms_random.value(10000000000000000000,99999999999999999999))) '||
                   /*Информация о владельце*/
                  ', :l_PersonInfoId '||
                   /*Остаток*/
                  ', ''Avail-''||trunc(dbms_random.value(20000,999999))||'',AvailCash-''||trunc(dbms_random.value(10000,20000)) '||
               'from ( select (select s.currency_code from '||c_PrimarySchema||'.imacurrency_list_table s where s.num = :l_IMACurrency) currency_code '||
                           ', trunc(sysdate - 365*dbms_random.value(1,3.9)) start_date '||
                           ', (select s.deposit_state from '||c_PrimarySchema||'.depostate_list_table s where s.num = :l_IMAState) ima_state '||
                        'from dual ) c '
             using l_IMAId, l_DepOffice, l_DepOsb, l_DepTb, l_DepTb||l_DepOsb, l_SystemId, l_IMAcctId, l_PersonInfoId, l_IMACurrency, l_IMAState;  

      --Вставка связки GFL и ОМС клиента
      execute immediate
        'insert into gfl_products '||
               'select s_gfl_product.nextval '||
                    ', :l_GFLId '||
                    ', :l_IMAId '||
                    ', ''IMA'' '||
                 'from dual '
            using l_GFLId, l_IMAId;
      
      --Вставка запроса IMA_IS (запрос получения детальной информации по ОМС)
      execute immediate
        'insert into ima_is '||
             'select s_imais.nextval '||
                  ', substr(:l_UserCBCode,1,8) '||
                  ', (select s.system_id from '||c_PrimarySchema||'.system_id_table s where s.num = :l_SystemId) system_id '||
                  ', :l_IMAcctId '||
                  ', substr(:l_DepTb_DepOsb,1,6) '||
                  ', :l_IMAId '||
               'from dual '
            using l_UserCBCode, l_SystemId, l_IMAcctId, l_DepTb||l_DepOsb, l_IMAId;
      
      exit when l_IMAItemIndex >= c_UserIMACount;
    end loop;
    /******************ДОБАВЛЕНИЕ ОМС КЛИЕНТА - конец********************/
    
    /******************ДОБАВЛЕНИЕ КРЕДИТОВ КЛИЕНТА - начало********************/
    l_LoanItemIndex := 0;
    loop
      l_LoanItemIndex := l_LoanItemIndex + 1;
      
      --Вставка кредита клиента
      execute immediate 'select s_credit.nextval from dual' into l_CreditId;
      l_SystemId := 'Loan_System';
      l_LoanAcctId := to_char(trunc(dbms_random.value(100000000000000000000000,999999999999999999999999)));
      l_LoanStartDate := trunc(sysdate - 365*dbms_random.value(1,3.9));     
      execute immediate
        'insert into credit '||
             'select :l_CreditId '||
                    /*Идентификатор системы-источника продукта*/
                  ', :l_SystemId '||
                    /*Номер ссудного счета*/
                  ', :l_LoanAcctId '||
                    /*Номер кредитного договора*/
                  ', to_char(trunc(dbms_random.value(1000000000000000,9999999999999999))) '||
                    /*(ИД в ИАСК) Идентификатор банковского продукта*/
                  ', to_char(trunc(dbms_random.value(100000,999999))) '||
                    /*Краткое название кредита*/
                  ', ''Кредит ''||DBMS_RANDOM.string(''a'',8)||'' generated'' '||
                    /*Валюта*/
                  ', ''RUR'' '||
                    /*Сумма по договору*/
                  ', trunc(dbms_random.value(10000000,99999999)) '||
                    /*Номер филиала, в котором ведется договор*/
                  ', :l_DepOffice '||
                    /*Номер ОСБ, где ведется кредит*/
                  ', :l_DepOsb '||
                    /*Номер тербанка(ТБ) */
                  ', :l_DepTb '||
                    /*Номер ОСБ, где ведется ссудный счет*/
                  ', :l_DepTb_DepOsb '||
                    /*Признак аннуитетности (True-аннуитетный, false – дифференцированный)*/
                  ', trunc(dbms_random.value(0,1.9)) '||
                    /*Дата подписания договора*/
                  ', :l_LoanStartDate '||
                    /*Дата окончания/закрытия договора*/
                  ', least(:l_LoanStartDate + 365*dbms_random.value(1,3),sysdate) '||
                    /*Запись о заемщике*/
                  ', :l_PersonInfoId '||
                    /*Роль клиента в кредитном договоре (1 - заемщик, созаемщик; 2 - поручитель, залогодатель)*/
                  ', trunc(dbms_random.value(1,2.9)) '||
               'from dual c'
           using l_CreditId, l_SystemId, l_LoanAcctId, l_DepOffice, l_DepOsb, l_DepTb, l_DepTb||l_DepOsb, l_LoanStartDate, l_LoanStartDate, l_PersonInfoId;
      
      --Вставка связки GFL и кредита клиента
      execute immediate
        'insert into gfl_products '||
               'select s_gfl_product.nextval '||
                    ', :l_GFLId '||
                    ', :l_CreditId '||
                    ', ''Credit'' '||
                 'from dual '
            using l_GFLId, l_CreditId;

      --Вставка запроса LN_CI (получение детальной информации по кредиту)
      execute immediate
        'insert into ln_ci '||
             'select s_lnci.nextval '||
                 ', substr(:l_UserCBCode,1,8) '||
                 ', :l_SystemId '||
                 ', :l_LoanAcctId '||
                 ', substr(:l_DepTb_DepOsb,1,6) '||
                 ', :l_CreditId '||
               'from dual'
           using l_UserCBCode, l_SystemId, l_LoanAcctId, l_DepTb||l_DepOsb, l_CreditId;
                 
      exit when l_LoanItemIndex >= c_UserLoanCount;
    end loop;
    /******************ДОБАВЛЕНИЕ КРЕДИРОВ КЛИЕНТА - конец********************/
    
    exit when l_ItemIndex = c_UserCount;    
  end loop;
  
  execute immediate 'alter session set current_schema = '||c_PrimarySchema;

  --Отбираем права
  execute immediate 'revoke select on client_status_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on card_status_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on system_id_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on currency_list_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on cardtype_list_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on depostate_list_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on depaccmask_list_table from '|| c_SecondarySchema;
  execute immediate 'revoke select on imacurrency_list_table from '|| c_SecondarySchema;
 
  --чистка вспомогательных структур
  execute immediate 'drop type STRING_LIST_TYPE';
  execute immediate 'drop function str2tbl';
  --Статусы клиента
  execute immediate 'truncate table client_status_table';
  execute immediate 'drop table client_status_table';
  --Статусы карт
  execute immediate 'truncate table card_status_table';
  execute immediate 'drop table card_status_table';
  --Идентификаторы систем-источников продукта
  execute immediate 'truncate table system_id_table';
  execute immediate 'drop table system_id_table';
  --Валюты счетов
  execute immediate 'truncate table currency_list_table';
  execute immediate 'drop table currency_list_table';
  --Типы карт
  execute immediate 'truncate table cardtype_list_table';
  execute immediate 'drop table cardtype_list_table';
  --Статусы счетов
  execute immediate 'truncate table depostate_list_table';
  execute immediate 'drop table depostate_list_table';
  --Маски счетов
  execute immediate 'truncate table depaccmask_list_table';
  execute immediate 'drop table depaccmask_list_table';
  --Валюты для ОМС
  execute immediate 'truncate table imacurrency_list_table';
  execute immediate 'drop table imacurrency_list_table'; 
  --********************************************************************  
  exception when OTHERS then 
    DBMS_OUTPUT.put_line(DBMS_UTILITY.format_error_stack || DBMS_UTILITY.format_error_backtrace);
    for cur in (SELECT table_name FROM user_all_tables
                 WHERE LOWER (table_name) in ('client_status_table',
                                              'card_status_table',
                                              'system_id_table',
                                              'currency_list_table',
                                              'cardtype_list_table',
                                              'depostate_list_table',
                                              'depaccmask_list_table',
                                              'imacurrency_list_table'))
    loop
      EXECUTE IMMEDIATE 'truncate table '||cur.table_name;
      EXECUTE IMMEDIATE 'drop table '||cur.table_name;
    end loop;
  end;
END;
