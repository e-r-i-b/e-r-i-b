--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSA_IKFL
/

-- Номер ревизии: 66448
-- Комментарий: BUG069682 Не закрываются курсоры в базе ЦСА(Удалены неиспользуемые хранимки)
create or replace package CSA_CONNECTORS_PKG as
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
/

create or replace package body CSA_CONNECTORS_PKG as
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
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии: 67729
-- Комментарий: Stand-In. форма состояния миграции
create index CSA_IKFL.I_CSA_PROFILE_NODES_STATE ON CSA_IKFL.CSA_PROFILE_NODES (
    STATE
) parallel 32 tablespace CSAINDEXES
/
alter index CSA_IKFL.I_CSA_PROFILE_NODES_STATE noparallel
/

-- Номер ревизии: 66769
-- Комментарий: Разделить коннекторы mAPI и socialAPI в ЦСА
update CSA_IKFL.CSA_CONNECTORS c set c.TYPE = 'SOCIAL' where upper(c.DEVICE_INFO) in ('FACEBOOK', 'VKONTAKTE', 'ODNOKLAS')
/

-- Номер ревизии: 68273
-- Комментарий: Stand-In. Доработка функционала определения блока сотрудника
alter table CSA_IKFL.CSA_NODES add ADMIN_AVAILABLE char(1)
/
update CSA_IKFL.CSA_NODES set ADMIN_AVAILABLE = '1'
/
alter table CSA_IKFL.CSA_NODES modify ADMIN_AVAILABLE not null
/
