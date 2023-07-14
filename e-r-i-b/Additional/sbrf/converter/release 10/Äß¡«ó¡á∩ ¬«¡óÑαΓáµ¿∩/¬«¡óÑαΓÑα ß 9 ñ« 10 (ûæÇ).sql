-- ����� �������:  44412
-- ����������� ����� ��������
alter table BLOCKINGRULES add (RESUMING_TIME timestamp not null)
/
alter table BLOCKINGRULES modify(MESSAGE varchar2(1024))
/

-- ����� �������:  46082
-- ������ �������� ���(���� ������� ��������)
alter table CSA_CODLOG add (
	PROMOTER_ID  varchar2(100)   null,
	MGUID        varchar2(32)    null,
	IP_ADDRESS   varchar2(15)    null,
	ERROR_CODE   varchar2(10)    null
)
/

alter table CSA_CODLOG drop (
	PERSON_ID,
	DEPARTMENT_NAME,
	DEPARTMENT_ID,
	LOGIN_ID
)
/
/*
	���� ����� ������ ������� ����� �������, �� ������������ ���� ������ ��������:
	
alter table CSA_CODLOG rename column PERSON_ID to PERSON_ID_1010101
/
alter table CSA_CODLOG SET UNUSED column PERSON_ID_1010101
/
alter table CSA_CODLOG rename column DEPARTMENT_NAME to DEP_NAME_1010101
/
alter table CSA_CODLOG SET UNUSED column DEP_NAME_1010101
/
alter table CSA_CODLOG rename column DEPARTMENT_ID to DEP_ID_1010101
/
alter table CSA_CODLOG SET UNUSED column DEP_ID_1010101
/
alter table CSA_CODLOG rename column LOGIN_ID to LOGIN_ID_1010101
/
alter table CSA_CODLOG SET UNUSED column LOGIN_ID_1010101
/
--���� ����� ���������, �� ������� ���������� ���� ��� ������������ �����
ALTER TABLE CSA_CODLOG DROP UNUSED COLUMNS CHECKPOINT 10000
/
*/

create index CSA_CL_IP_DATA_INDEX on CSA_CODLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_CL_IP_DATA_INDEX noparallel
/

create index CSA_CL_MGUID_DATE_INDEX on CSA_CODLOG (
   MGUID ASC,
   START_DATE DESC
) 
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_CL_MGUID_DATE_INDEX noparallel
/

create index CSA_CL_PROMO_ID_DATE_INDEX on CSA_CODLOG (
   PROMOTER_ID ASC,
   START_DATE DESC
) 
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_CL_PROMO_ID_DATE_INDEX noparallel
/

--CSA_CONNECTORS
drop index CSA_CONNECTORS_U_LOGIN 
/
create unique index CSA_CONNECTORS_U_LOGIN on CSA_CONNECTORS (
   UPPER(LOGIN) ASC
)
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_CONNECTORS_U_LOGIN noparallel
/

create index CSA_CONNECTORS_CTS on CSA_CONNECTORS (
   CARD_NUMBER ASC,
   TYPE ASC,
   STATE ASC
)
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_CONNECTORS_CTS noparallel
/

-- ����� �������:  45332
-- ���. ������ ����������� ��� ����
alter table CSA_OPERATIONS modify ( "USER_ID" varchar2(10) null )
/


-- ����� �������:  44593
-- ��� ������ � �������� �������� �6. ������� �������.
create index CSA_PASSWORDS_CC on CSA_PASSWORDS (
   CONNECTOR_ID ASC,
   CREATION_DATE ASC
) 
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_PASSWORDS_CC noparallel
/

-- ����� �������:  46049
drop index CSA_PROFILES_UNIVERSAL_ID
/

create unique index CSA_PROFILES_UNIVERSAL_ID on CSA_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','')  ASC
)
local tablespace CSAINDEXES parallel 64 
/
alter index CSA_PROFILES_UNIVERSAL_ID noparallel
/

-- ����� �������:  45574
-- ���������� ������ � ����� PROPERTY_KEY � CATEGORY ������� PROPERTIES
create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
/

/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
/
create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*���������� �������� ���������� �� ������ ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID ����������*/
                , con.guid                            /*GUID ����������*/
                , con.creation_date connection_date   /*���� �����������*/
                , con.card_number                     /*����� �����*/
                , con.device_info  mobile_app_type    /*��� ����������*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*�������� � �������� ��������������� ���������*/
              and upper(con.type) = 'MAPI'/*��������� �� ������ � mAPI, ��������� ��� ����������� ���������� ����������*/;
      else /*��������� ������ ���������� �� ���, ��������(�����+�����), ���� �������� � ��������*/
        /*�������� ������� ������*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'������ ������� ������ ��� ������ GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID ����������*/
               , con.guid                           /*GUID ����������*/
               , con.creation_date connection_date  /*���� �����������*/
               , con.card_number                    /*����� �����*/
               , con.device_info  mobile_app_type   /*��� ����������*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                                                                                                                                                            CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*�������� � �������� ��������������� ���������*/
             and upper(con.type) = 'MAPI'/*��������� �� ������ � mAPI, ��������� ��� ����������?? ���������� ����������*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'������ ������� ������ ��� ������ GetClientConnectorByAlias');
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
     if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
       RAISE_APPLICATION_ERROR(-20900,'������ ������� ������ ��� ������ SetPromoClientSessionLog');
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
       RAISE_APPLICATION_ERROR(-20900,'�� ��������� ��������� ��� ������������� ������� (profile_id) �������');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- ����� �������:  43567
-- �����������:  CHG046532 ����� �������� CSABackApp � ��(������� ��� ����������� �������� �� csa-back.properties � log.properties � ������� PROPERTIES, ���� ��������� �� ����� ���������, ��� ����� ������� �� ������ properties)

-- log.propertiers

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Core', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Gate', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Scheduler', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Cache', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Web', '4', 'csa-back.log.properties');

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.SystemLogWriter', 'com.rssl.phizic.logging.system.DatabaseSystemLogWriter', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName', 'CSA2Log', 'csa-back.log.properties');

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.MessageLogWriter', 'com.rssl.phizic.logging.system.DatabaseSystemLogWriter', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName', 'CSA2Log', 'csa-back.log.properties');

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.messagesLog.level.jdbc', 'on', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.messagesLog.level.iPas', 'on', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.messagesLog.level.mobile', 'on', 'csa-back.log.properties');


-- csa-back.properties

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.integration.ipas.url', 'http://csa.sbrf.ru/csa_ipas/services/iPASWS1', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.confirmation.timeout', '600', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.confirmation.code.length', '5', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars', '0123456789', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.session.timeout', '24', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.registration.user.deny-multiple', 'true', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.registration.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.authentication.blocking.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.authentication.failed.limit', '3', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.password-restoration.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.max.connectors', '10', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.request.limit', '10', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval', '600', 'csa-back.properties');


