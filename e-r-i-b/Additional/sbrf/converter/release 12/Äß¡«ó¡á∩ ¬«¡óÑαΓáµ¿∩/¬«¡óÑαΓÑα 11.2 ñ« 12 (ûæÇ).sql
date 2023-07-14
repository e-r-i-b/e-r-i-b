--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 53700
-- Комментарий: Перенос аутентификации ЕРМБ в ЦСА.
alter table CSA_CONNECTORS add (PHONE_NUMBER varchar2(20))
/
create unique index CONNECTORS_PHONE_INDEX on CSA_CONNECTORS( PHONE_NUMBER ) 
parallel 32 tablespace CSAINDEXES 
/
alter index CONNECTORS_PHONE_INDEX noparallel
/
alter table CSA_CONNECTORS modify (CARD_NUMBER null)
/
alter table CSA_CONNECTORS modify (USER_ID null)
/

-- Номер ревизии: 54491
-- Комментарий: BUG060888: mAPI. CSA. Не устанавливается AuthenticationContext
-- Комментарий: Сохранение токена безопасности
alter table CSA_CONNECTORS add (
	API_VERSION            varchar2(4)    null,
	DEVICE_SECURITY_TOKEN  varchar2(4000) null
)
/

-- Номер ревизии: 53807
-- Номер ревизии: 54595
-- Комментарий: Перенос аутентификации ЕРМБ в ЦСА. Информация об очередях блоков.
insert into CSA_NODES values (1, 'основной', 'online.sberbank.ru', 1, 1, 0, 0, 'jms/ermb/sms/SmsQueue', 'jms/ermb/sms/SmsQCF', 'jms/dictionary/SynchDictionaryQueue','jms/dictionary/SynchDictionaryQCF','jms/ermb/auxiliary/ConfirmProfileQueue','jms/ermb/auxiliary/ConfirmProfileQCF','jms/ermb/auxiliary/UpdateClientQueue','jms/ermb/auxiliary/UpdateClientQCF','jms/ermb/auxiliary/UpdateResourceQueue','jms/ermb/auxiliary/UpdateResourceQCF')
/

-- Номер ревизии: 54470
-- Комментарий: реализация технологических перерывов в разрезе канала
alter table BLOCKINGRULES rename column MESSAGE to ERIB_MESSAGE
/
alter table BLOCKINGRULES modify ERIB_MESSAGE null
/
alter table BLOCKINGRULES add (
	ERIB            char    default 0 not null,
	MAPI            char    default 0 not null,
	MAPI_MESSAGE    varchar2(1024),
	ATM             char    default 0 not null,
	ATM_MESSAGE     varchar2(1024),
	ERMB            char    default 0 not null,
	ERMB_MESSAGE    varchar2(1024)	
)
/

-- Номер ревизии: 54876
-- Комментарий: Реализовать фильтрацию списка ЦСА клиентов.
alter table CSA_PROFILES add (
	AGREEMENT_NUMBER   varchar2(16),
	CREATION_TYPE      varchar2(25),
	PHONE              varchar2(16)
)
/

create index CSA_PROFILES_AGREEMENT_NUM_I on CSA_PROFILES ( AGREEMENT_NUMBER asc ) parallel 32 tablespace CSAINDEXES 
/
alter index CSA_PROFILES_AGREEMENT_NUM_I noparallel
/
create index CSA_PROFILES_PHONE_INDEX on CSA_PROFILES ( PHONE ASC ) parallel 32 tablespace CSAINDEXES 
/
alter index CSA_PROFILES_PHONE_INDEX noparallel
/


-- Номер ревизии: 54890
-- Комментарий: Определение региона с сайта www.sberbank.ru
alter table REGIONS add EN_NAME varchar2(128)
/

update REGIONS set EN_NAME='altaikrai' where CODE='01000'
/

update REGIONS set EN_NAME='primorskykrai' where CODE='05000'
/

update REGIONS set EN_NAME='stavropol' where CODE='07000'
/

update REGIONS set EN_NAME='khabarovsk' where CODE='08000'
/

update REGIONS set EN_NAME='volgograd' where CODE='18000'
/

update REGIONS set EN_NAME='samara' where CODE='36000'
/

update REGIONS set EN_NAME='adygea' where CODE='79000'
/

update REGIONS set EN_NAME='buryatia' where CODE='81000'
/

update REGIONS set EN_NAME='dagestan' where CODE='82000'
/

update REGIONS set EN_NAME='ingushetia' where CODE='26000'
/

update REGIONS set EN_NAME='kabardinobalkaria' where CODE='83000'
/

update REGIONS set EN_NAME='mariel' where CODE='88000'
/

update REGIONS set EN_NAME='mordovia' where CODE='89000'
/

update REGIONS set EN_NAME='tuva' where CODE='93000'
/

update REGIONS set EN_NAME='moscow' where CODE='45000'
/

update REGIONS set EN_NAME='karelia' where CODE='86000'
/

update REGIONS set EN_NAME='khakassia' where CODE='95000'
/

update REGIONS set EN_NAME='chuvashia' where CODE='97000'
/

update REGIONS set EN_NAME='zabaykalskykrai' where CODE='76000'
/

update REGIONS set EN_NAME='krasnodar' where CODE='03000'
/

update REGIONS set EN_NAME='krasnoyarsk' where CODE='04000'
/

update REGIONS set EN_NAME='perm' where CODE='57000'
/

update REGIONS set EN_NAME='amur' where CODE='10000'
/

update REGIONS set EN_NAME='arkhangelsk' where CODE='11000'
/

update REGIONS set EN_NAME='astrakhan' where CODE='12000'
/

update REGIONS set EN_NAME='belgorod' where CODE='14000'
/

update REGIONS set EN_NAME='bryansk' where CODE='15000'
/

update REGIONS set EN_NAME='vologda' where CODE='19000'
/

update REGIONS set EN_NAME='voronezh' where CODE='20000'
/

update REGIONS set EN_NAME='ivanovo' where CODE='24000'
/

update REGIONS set EN_NAME='irkutsk' where CODE='25000'
/

update REGIONS set EN_NAME='kaliningrad' where CODE='27000'
/

update REGIONS set EN_NAME='kaluga' where CODE='29000'
/

update REGIONS set EN_NAME='kirov' where CODE='33000'
/

update REGIONS set EN_NAME='kostroma' where CODE='34000'
/

update REGIONS set EN_NAME='lipetsk' where CODE='42000'
/

update REGIONS set EN_NAME='magadan' where CODE='44000'
/

update REGIONS set EN_NAME='murmansk' where CODE='47000'
/

update REGIONS set EN_NAME='nizhnynovgorod' where CODE='22000'
/

update REGIONS set EN_NAME='novgorod' where CODE='49000'
/

update REGIONS set EN_NAME='pskov' where CODE='58000'
/

update REGIONS set EN_NAME='rostov' where CODE='60000'
/

update REGIONS set EN_NAME='ryazan' where CODE='61000'
/

update REGIONS set EN_NAME='smolensk' where CODE='66000'
/

update REGIONS set EN_NAME='tambov' where CODE='68000'
/

update REGIONS set EN_NAME='tver' where CODE='28000'
/

update REGIONS set EN_NAME='tula' where CODE='70000'
/

update REGIONS set EN_NAME='chelyabinsk' where CODE='75000'
/

update REGIONS set EN_NAME='yaroslavl' where CODE='78000'
/

update REGIONS set EN_NAME='saintpetersburg' where CODE='40000'
/

update REGIONS set EN_NAME='penza' where CODE='56000'
/

update REGIONS set EN_NAME='saratov' where CODE='63000'
/

update REGIONS set EN_NAME='vladimir' where CODE='17000'
/

update REGIONS set EN_NAME='kemerovo' where CODE='32000'
/

update REGIONS set EN_NAME='kurgan' where CODE='37000'
/

update REGIONS set EN_NAME='ulyanovsk' where CODE='73000'
/

update REGIONS set EN_NAME='novosibirsk' where CODE='50000'
/

update REGIONS set EN_NAME='omsk' where CODE='52000'
/

update REGIONS set EN_NAME='sverdlovsk' where CODE='65000'
/

update REGIONS set EN_NAME='tomsk' where CODE='69000'
/

update REGIONS set EN_NAME='tyumen' where CODE='71000'
/

update REGIONS set EN_NAME='bashkortostan' where CODE='80000'
/

update REGIONS set EN_NAME='komi' where CODE='87000'
/

update REGIONS set EN_NAME='tatarstan' where CODE='92000'
/

update REGIONS set EN_NAME='udmurtia' where CODE='94000'
/

update REGIONS set EN_NAME='kursk' where CODE='38000'
/

update REGIONS set EN_NAME='oryol' where CODE='54000'
/

update REGIONS set EN_NAME='sakha' where CODE='98000'
/

update REGIONS set EN_NAME='moscowoblast' where CODE='46000'
/

update REGIONS set EN_NAME='orenburg' where CODE='53000'
/

--костыль из-за проблем с UNDO
-- Номер ревизии: 54712
-- Комментарий: Настройка приватности в клиентском приложении ЕРИБ
alter table CSA_PROFILES add (INCOGNITO char(1))
/
--запускаем csa_update_profiles.bat

alter table CSA_PROFILES modify (INCOGNITO char(1) default '0' not null)
/

-- Номер ревизии: 53928
-- Комментарий: ЦСА БЭК. userId удален из операции и контекста идентификации.
/* Делаем, когда припимаем решение о ВНЕДРЕНИИ!!!
alter table CSA_OPERATIONS set unused (USER_ID)
/
*/



-- Номер ревизии: 55432
-- Комментарий: BUG063489: Доработать джоб актуализации справочника "Инкогнито" для пакетной обработки клиентов.
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
/

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if pSurName is null or pFirstName is null or pPassport is null or pBirthdate is null or pTB is null then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if nvl(pConnectorGUID, pOperationUID) is null or pPromoterSessionId is null then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if (pConnectorGUID is null) then
           RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognito');
        end if;
          
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
   end;
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor) as
   begin
      if (rowPosition is null or rate is null) then
           open pOutCursor for
               select profiles.FIRST_NAME  first_name,
                      profiles.SUR_NAME  sur_name, 
                      profiles.PATR_NAME  patr_name, 
                      profiles.PASSPORT passport, 
                      profiles.BIRTHDATE birthdate, 
                      profiles.TB tb
                                              
                      from CSA_PROFILES profiles 
                      where incognito = '1';
         
      else
           open pOutCursor for
               select res.* from
                   (select rownum rn, profiles.FIRST_NAME  first_name, 
                                       profiles.SUR_NAME  sur_name, 
                                       profiles.PATR_NAME  patr_name, 
                                       profiles.PASSPORT passport, 
                                       profiles.BIRTHDATE birthdate, 
                                       profiles.TB tb
                                       
                                       from CSA_PROFILES profiles 
                                       where incognito = '1' and rownum < rowPosition*rate
                                       )res 
                                       where res.rn >= (rowPosition*rate-rate);
      
      
      end if;
   end;
end CSA_CONNECTORS_PKG;
/