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

--��������������� �����
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2, pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
/
create or replace package body CSA_CONNECTORS_PKG is
   /*��������� ������������ ��������� ���������� ������� �� ����������. ������������ � ���� �������:
   - ��������� ������ ������������ ���������� ������� �� ���, ��������(�����+�����), ���� �������� � ��������;
   - ��������� ����������� ������������� ���������� ���������� ��� ������ �� ID*/
   procedure GetConnectedMobileDevices(pSurName in varchar2,        /*������� �������*/
                                       pFirstName in varchar2,      /*���*/
                                       pPatrName in varchar2,       /*��������*/
                                       pPassport in varchar2,       /*����� � ����� ��������*/
                                       pBirthdate in timestamp,     /*���� ��������*/
                                       pTB in varchar2,             /*����� �������� � ����������*/
                                       pAppId in varchar2,          /*ID ������������� ����������*/
                                       pFlag in varchar2,           /*������������ �������������� ��������� ������*/
                                       pOutCursor out sys_refcursor /*������������ ����������*/) is
   begin
     if pAppId is not null then /*���������� �������� ���������� �� ������ ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID ����������*/
                , con.guid                            /*GUID ����������*/
                , con.creation_date connection_date   /*���� �����������*/
                , con.card_number                     /*����� �����*/
                , con.device_info  mobile_app_type    /*��� ����������*/
                , con.device_id  device_id           /*ID ����������*/
                , con.push_supported  push_supported /*������� ����������� push-�����������*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*�������� � �������� ��������������� ���������*/
              and upper(con.type) = 'MAPI'/*��������� �� ������ � mAPI, ��������� ��� ����������� ���������� ����������*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
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
               , con.device_id  device_id           /*ID ����������*/
               , con.push_supported  push_supported /*������� ����������� push-�����������*/
            from csa_connectors con, csa_profiles pr
           where
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                            CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*�������� � �������� ��������������� ���������*/
             and upper(con.type) = 'MAPI'/*��������� �� ������ � mAPI, ��������� ��� ������������ ���������� ����������*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;

   /*��������� ���������� ������� �� ��� ������*/
   procedure GetClientConnectorByAlias(pAlias in varchar2,          /*����� �������*/
                                       pOutCursor out sys_refcursor /*��� ������ ����������*/) is
   begin
     if (pAlias is null) then
        RAISE_APPLICATION_ERROR(-20900,'������ ������� ������ ��� ������ GetClientConnectorByAlias');
     end if;

     open pOutCursor for
          select con.*
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;

   /*����������� ����� ������� � �������� ����������*/
   procedure SetPromoClientSessionLog(pConnectorGUID in varchar2,     /*GUID ���������� �������*/
                                      pOperationUID in varchar2,      /*OUID �������� �������*/
                                      pPromoterSessionId in varchar2, /*ID �������� ������ ����������*/
                                      pPromoClientLogId out varchar2  /*ID ����������� ������ ����*/) is
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


