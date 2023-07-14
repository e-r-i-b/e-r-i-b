alter table BLOCKINGRULES set unused (
	ERIB,
	MAPI,
	MAPI_MESSAGE,
	ATM,
	ATM_MESSAGE,
	ERMB,
	ERMB_MESSAGE
)
/

drop index CONNECTORS_PHONE_INDEX
/

alter table CSA_PROFILES set unused (INCOGNITO)
/
alter table BLOCKINGRULES rename column ERIB_MESSAGE to MESSAGE
/

--Восстанавливаем пакет
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2, pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
/
create or replace package body CSA_CONNECTORS_PKG is
   /*Получение подключенных мобильных приложений клиента по параметрам. Используется в двух случаях:
   - получение списка подключенных приложений клиента по ФИО, паспорту(серия+номер), дате рождения и тербанку;
   - получение конкретного подключенного мобильного приложения или списка по ID*/
   procedure GetConnectedMobileDevices(pSurName in varchar2,        /*фамилия клиента*/
                                       pFirstName in varchar2,      /*имя*/
                                       pPatrName in varchar2,       /*отчество*/
                                       pPassport in varchar2,       /*серия и номер паспорта*/
                                       pBirthdate in timestamp,     /*дата рождения*/
                                       pTB in varchar2,             /*номер тербанка с синонимами*/
                                       pAppId in varchar2,          /*ID подключенного приложения*/
                                       pFlag in varchar2,           /*использовать дополнительные параметры поиска*/
                                       pOutCursor out sys_refcursor /*подключенные приложения*/) is
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
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
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
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрацияя мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;

   /*Получение коннектора клиента по его алиасу*/
   procedure GetClientConnectorByAlias(pAlias in varchar2,          /*алиас клиента*/
                                       pOutCursor out sys_refcursor /*все данные коннектора*/) is
   begin
     if (pAlias is null) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
     end if;

     open pOutCursor for
          select con.*
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;

   /*Логирование входа клиента с участием промоутера*/
   procedure SetPromoClientSessionLog(pConnectorGUID in varchar2,     /*GUID коннектора клиента*/
                                      pOperationUID in varchar2,      /*OUID операции клиента*/
                                      pPromoterSessionId in varchar2, /*ID активной сессии промоутера*/
                                      pPromoClientLogId out varchar2  /*ID добавленной записи лога*/) is
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
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
end CSA_CONNECTORS_PKG;
/


