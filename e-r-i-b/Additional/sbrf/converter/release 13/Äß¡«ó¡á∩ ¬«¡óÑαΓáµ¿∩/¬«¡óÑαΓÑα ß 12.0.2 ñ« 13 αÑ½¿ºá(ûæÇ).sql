--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 56210
-- Комментарий: BUG064676: Добавить признак «Статус приватности» в профиль клиента в АРМ-сотрудника
CREATE OR REPLACE package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor);
   procedure GetClientIncognito (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2, pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
/

CREATE OR REPLACE package body CSA_CONNECTORS_PKG as
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
   procedure GetClientIncognito (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2, pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if pConnectorGUID is not null then 
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
      else 
        
        if pSurName is null or pFirstName is null or pPassport is null or pBirthdate is null or pTB is null then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognitoByParams');
        end if;
       
        open pOutCursor for        
            select pr.incognito client_incognito
               from csa_profiles pr
            where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                               CONNECT BY instr(pTB, ',', 1, level-1) > 0);
      end if;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии: 56495
-- Комментарий: CHG065516: Переделать привязку новостей к департаментам
alter table NEWS_DEPARTMENT drop constraint PK_NEWS_DEPARTMENT
/
alter table NEWS_DEPARTMENT drop column DEPARTMENT_ID 
/
alter table NEWS_DEPARTMENT modify TB varchar2(4)
/
alter table NEWS_DEPARTMENT add constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, TB) using index (
	create unique index PK_NEWS_DEPARTMENT on NEWS_DEPARTMENT(NEWS_ID, TB) tablespace CSAINDEXES 
)
/
alter table NEWS_DEPARTMENT drop column NAME
/

-- Номер ревизии: 57206
-- Комментарий: CHG066358: Разобраться с регионами в ЦСА
truncate table USERS_REGIONS
/ 
drop sequence S_USERS_REGIONS
/
drop index INDEX_CSA_USER_ID
/
alter table USERS_REGIONS drop constraint PK_USERS_REGIONS
/
alter table USERS_REGIONS drop (
	ID,
	LAST_USE_DATE,
	CSA_USER_ID
)
/
alter table USERS_REGIONS add PROFILE_ID NUMBER not null
/
alter table USERS_REGIONS add constraint PK_USERS_REGIONS primary key(PROFILE_ID) using index (
	create unique index PK_USERS_REGIONS on USERS_REGIONS(PROFILE_ID) tablespace CSAINDEXES 
)
/

-- Номер ревизии: 57710
-- Комментарий: Перенос уровня безопасности в ЦСА. Часть 1
alter table CSA_CONNECTORS add (SECURITY_TYPE varchar2(28))
/
alter table CSA_PROFILES add (SECURITY_TYPE varchar2(28))
/
update CSA_CONNECTORS set SECURITY_TYPE = 'LOW'
    where TYPE in ('ATM','DISPOSABLE','TERMINAL')
/

-- Номер ревизии: 57777
-- Комментарий: Доработки работы с резервными блоками. Признак "частичного" входа.
update CSA_PROFILE_NODES SET STATE = 'PROCESS_MIGRATION' WHERE STATE = 'MIGRATION'
/

-- Номер ревизии: 58842
-- Номер версии: 1.18
-- Комментарий: BUG069393: [ЕРМБ] необходимо реализовать CSATransportSmsResponseListener 

alter table CSA_NODES add (SMS_WITH_IMSI_QUEUE_NAME varchar2(64))
/
alter table CSA_NODES add (SMS_WITH_IMSI_FACTORY_NAME varchar2(64))
/
update CSA_NODES set SMS_WITH_IMSI_QUEUE_NAME = 'jms/ermb/transport/CheckIMSIRsQueue'
/
update CSA_NODES set SMS_WITH_IMSI_FACTORY_NAME = 'jms/ermb/transport/CheckIMSIRsQCF'
/
alter table CSA_NODES modify (SMS_WITH_IMSI_QUEUE_NAME not null)
/
alter table CSA_NODES modify (SMS_WITH_IMSI_FACTORY_NAME not null)
/
alter table CSA_NODES modify (SERVICE_PROFILE_QUEUE_NAME default null)
/
alter table CSA_NODES modify (SERVICE_PROFILE_FACTORY_NAME default null)
/
alter table CSA_NODES modify (SERVICE_CLIENT_QUEUE_NAME default null)
/
alter table CSA_NODES modify (SERVICE_CLIENT_FACTORY_NAME default null)
/
alter table CSA_NODES modify (SERVICE_RESOURCE_QUEUE_NAME default null)
/
alter table CSA_NODES modify (SERVICE_RESOURCE_FACTORY_NAME default null)
/

-- Номер ревизии: 58784
-- Комментарий: BUG023097: Переделать автоматическую публикацию новостей.
alter table NEWS RENAME COLUMN AUTOMATIC_PUBLISH_DATE TO START_PUBLISH_DATE
/
alter table NEWS RENAME COLUMN CANCEL_PUBLISH_DATE TO END_PUBLISH_DATE
/
create index I_NEWS_DATE  ON NEWS(
    START_PUBLISH_DATE, 
    END_PUBLISH_DATE
) tablespace CSAINDEXES 
/
drop index NEWS_INDEX 
/
update NEWS set START_PUBLISH_DATE = current_date where START_PUBLISH_DATE is null and STATE = 'PUBLISHED'
/
alter table NEWS drop column STATE
/

-- Номер ревизии: 59146
-- Комментарий: ENH067465: Многоблочность - настройка для деловой среды в ЦСА Бэк 
alter table CSA_NODES ADD LISTENER_HOSTNAME VARCHAR2(100) 
/
update CSA_NODES SET LISTENER_HOSTNAME = HOSTNAME
/
alter table CSA_NODES MODIFY LISTENER_HOSTNAME NOT NULL
/

