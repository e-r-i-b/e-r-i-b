create or replace package SERVICES_AGGREGATION is
  procedure Refresh;
end;
/
create or replace package body SERVICES_AGGREGATION is

  IQWAVE_CODE   constant varchar2(16):='%iqwave%';

  

  procedure Refresh is
    PKEY_CODE       number;
    aggregationTime AGGREGATION_STATE.TIME%TYPE;
    catalogTime     AGGREGATION_STATE.TIME%TYPE;
  begin

  
    select 
      time, ( select time from AGGREGATION_STATE where key='catalog.last.update.timestamp' ) ctime into aggregationTime, catalogTime
    from AGGREGATION_STATE 
    where 
      key='aggregation.last.update.timestamp' for update nowait;

    if ( catalogTime >  aggregationTime or aggregationTime is null) then

      update AGGREGATION_STATE set time = systimestamp where key='aggregation.last.update.timestamp';
	  
      update PROPERTIES set PROPERTY_VALUE = 3-to_number(PROPERTY_VALUE) 
        where PROPERTY_KEY='com.rssl.iccs.phizic.catalog.aggreagation.current.partition' and CATEGORY='phiz' 
          return PROPERTY_VALUE into PKEY_CODE;
  
      delete from PAYMENT_SERVICES_AGGR where P_KEY = PKEY_CODE;
  
      insert into PAYMENT_SERVICES_AGGR( SERVICE_ID, PARENT_SERVICE_ID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, GUID, CHANEL, REGION_ID, PRIORITY, AVAILABLE, P_KEY )
      with
      AVAILABLE_MAP as (
        select 'PAYMENTS' AVAILABLE from dual union all 
        select 'ESB_AUTOPAYMENTS' AVAILABLE from dual union all
        select 'IQW_AUTOPAYMENTS' AVAILABLE from dual union all
        select 'TEMPLATES' AVAILABLE from dual union all
        select 'MB_TEMPLATES' AVAILABLE from dual union all
        select 'BASKET' AVAILABLE from dual
      ),
      CHANELS as (
        select 'MAPI' CHANEL from dual union all select 'WEB' CHANEL from dual union all select 'ATMAPI' CHANEL from dual
      ),
      ALL_REGIONS as (
        select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by r.PARENT_ID = prior r.ID
        union
        select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by prior r.PARENT_ID = r.ID
        union
        select 0, ID REL_REGION_ID from REGIONS r --ÙÂ‰Â‡Î¸ÌÓÒÚ¸
        union
      select 0, -1 REL_REGION_ID from DUAL r -- -1 ‰Îˇ ÙÂ‰Â‡Î¸Ì˚ı	  
      ),
      ALL_SERVICE_PROVIDERS as (
        ----Â„ËÓÌ Ó·ÒÎÛÊË‚‡ÌËˇ Ò Û˜ÂÚÓÏ ÙÂ‰Â‡Î¸ÌÓÒÚË Ë „ÛÔÔ‡ "·ÂÁ Û˜ÂÚ‡ Â„ËÓÌ‡" REGION_ID -1
        select sp.*, decode(sp.IS_FEDERAL, '1', 0, nvl(spr.REGION_ID, 0)) PROVIDER_REGION_ID
        from SERVICE_PROVIDERS sp 
        left join SERVICE_PROVIDER_REGIONS spr on spr.SERVICE_PROVIDER_ID=decode(sp.IS_FEDERAL, '1', 0, sp.ID)
        where sp.STATE='ACTIVE'
        union all
        select sp.*, -1 PROVIDER_REGION_ID from SERVICE_PROVIDERS sp where sp.STATE='ACTIVE' and sp.IS_FEDERAL!='1'
      ),
      AVAILABLE_PROVIDERS as (
        select * from (
          select
            CHANEL,
            spps.PAYMENT_SERVICE_ID SERVICE_ID,
            PROVIDER_REGION_ID,
            sp.ID   PROVIDER_ID,
            ----‘”Õ »ŒÕ¿À‹ÕŒ—“‹ ¬  ¿Õ¿À¿’---------------------------------------------------
            case  
              when CHANEL='WEB'    and AVAILABLE_PAYMENTS_FOR_IB = '1'      and VISIBLE_PAYMENTS_FOR_IB = '1'       then 1
              when CHANEL='MAPI'   and AVAILABLE_PAYMENTS_FOR_M_API = '1'   and VISIBLE_PAYMENTS_FOR_M_API = '1'    then 1
              when CHANEL='ATMAPI' and AVAILABLE_PAYMENTS_FOR_ATM_API = '1' and VISIBLE_PAYMENTS_FOR_ATM_API = '1'  then 1
              else 0 
            end as AVAILABLE_PAYMENTS,
            case  
              when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and VISIBLE_AUTOPAYMENTS_FOR_IB='1'  and EXTERNAL_ID not like IQWAVE_CODE then 1
              when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and VISIBLE_AUTOPAYMENTS_FOR_API='1' and EXTERNAL_ID not like IQWAVE_CODE then 1
              when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and VISIBLE_AUTOPAYMENTS_FOR_ATM='1' and EXTERNAL_ID not like IQWAVE_CODE then 1
              else 0 
            end as AVAILABLE_ESB_AUTOP,
            case 
              when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and VISIBLE_AUTOPAYMENTS_FOR_IB='1'  and EXTERNAL_ID like IQWAVE_CODE then 1
              when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and VISIBLE_AUTOPAYMENTS_FOR_API='1' and EXTERNAL_ID like IQWAVE_CODE then 1
              when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and VISIBLE_AUTOPAYMENTS_FOR_ATM='1' and EXTERNAL_ID like IQWAVE_CODE then 1
              else 0 
            end as AVAILABLE_IQW_AUTOP,
            case     
              when CHANEL='WEB' and AVAILABLE_PAYMENTS_FOR_IB='1' and VISIBLE_PAYMENTS_FOR_IB='1' and IS_TEMPLATE_SUPPORTED='1' then 1
              when CHANEL='MAPI'   then 0
              when CHANEL='ATMAPI' then 0
              else 0 
            end as AVAILABLE_TEMPLATES,
            case     
              when CHANEL='WEB' and IS_MOBILEBANK='1' and ( select count(1) from FIELD_DESCRIPTIONS where RECIPIENT_ID=sp.ID and IS_KEY=1 )=1 then 1
              when CHANEL='MAPI'   then 0
              when CHANEL='ATMAPI' then 0
              else 0 
            end as AVAILABLE_MB_TEMPLATES,
            case     
              when CHANEL='WEB' and IS_AUTOPAYMENT_SUPPORTED='1'     and VISIBLE_AUTOPAYMENTS_FOR_IB='1'  and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then 1
              when CHANEL='MAPI'and IS_AUTOPAYMENT_SUPPORTED_API='1' and VISIBLE_AUTOPAYMENTS_FOR_API='1' and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then 1
              when CHANEL='ATMAPI' then 0
              else 0
            end as AVAILABLE_BASKET
          from ALL_SERVICE_PROVIDERS sp
          join SERV_PROVIDER_PAYMENT_SERV spps on spps.SERVICE_PROVIDER_ID = sp.ID     
          ---- ¿Õ¿À€ Œ¡—À”∆»¬¿Õ»ﬂ---------------------------------------------------------
          inner join CHANELS on 1=1
        )
        where 
          AVAILABLE_PAYMENTS     = 1 or 
          AVAILABLE_ESB_AUTOP    = 1 or 
          AVAILABLE_IQW_AUTOP    = 1 or 
          AVAILABLE_TEMPLATES    = 1 or 
          AVAILABLE_MB_TEMPLATES = 1 or 
          AVAILABLE_BASKET       = 1
      ),
      CHANEL_SERVICES as (
        select 
          CHANEL,
          ID SERVICE_ID, 
          case 
            when CHANEL='WEB' and SHOW_IN_SYSTEM='1'  then 1
            when CHANEL='MAPI' and SHOW_IN_API='1'    then 1
            when CHANEL='ATMAPI' and SHOW_IN_ATM='1'  then 1
            else 0
          end as SHOW,
          CODE GUID, 
          NAME SERVICE_NAME, 
          IMAGE_ID, 
          IMAGE_NAME,
          PRIORITY
        from PAYMENT_SERVICES s
        inner join CHANELS on 1=1
      ),
      AVAILABLE_SERVICES_TREE as (
        select * from (
          select
            s1.CHANEL CHANEL_L1, s1.SERVICE_ID SERVICE_L1, nvl(s2.SERVICE_ID, -1) PARENT_L1, s1.SHOW SHOW_L1, s1.GUID GUID_L1, s1.SERVICE_NAME SERVICE_NAME_L1, s1.IMAGE_ID IMAGE_ID_L1, s1.IMAGE_NAME IMAGE_NAME_L1, s1.PRIORITY PRIORITY_L1,
            s2.CHANEL CHANEL_L2, s2.SERVICE_ID SERVICE_L2, nvl(s3.SERVICE_ID, -1) PARENT_L2, s2.SHOW SHOW_L2, s2.GUID GUID_L2, s2.SERVICE_NAME SERVICE_NAME_L2, s2.IMAGE_ID IMAGE_ID_L2, s2.IMAGE_NAME IMAGE_NAME_L2, s2.PRIORITY PRIORITY_L2,
            s3.CHANEL CHANEL_L3, s3.SERVICE_ID SERVICE_L3, -1                     PARENT_L3, s3.SHOW SHOW_L3, s3.GUID GUID_L3, s3.SERVICE_NAME SERVICE_NAME_L3, s3.IMAGE_ID IMAGE_ID_L3, s3.IMAGE_NAME IMAGE_NAME_L3, s3.PRIORITY PRIORITY_L3
          from CHANEL_SERVICES s1
          left join PAYMENT_SERV_PARENTS p1 on p1.SERVICE_ID = s1.SERVICE_ID 
          left join CHANEL_SERVICES s2 on s2.SERVICE_ID=p1.PARENT_ID and s1.CHANEL=s2.CHANEL
          left join PAYMENT_SERV_PARENTS p2 on s2.SERVICE_ID = p2.SERVICE_ID
          left join CHANEL_SERVICES s3 on s3.SERVICE_ID=p2.PARENT_ID and s2.CHANEL=s3.CHANEL
        )
        where 
           (SHOW_L1=1 and SHOW_L2 is null and SHOW_L3 is null) 
        or (SHOW_L1=1 and SHOW_L2=1 and SHOW_L3 is null) 
        or (SHOW_L1=1 and SHOW_L2=1 and SHOW_L3=1)
      ),
      AVAILABLE_SERVICES as (
        select SERVICE_L1 LEAF_SERVICE, CHANEL_L3 CHANEL, SERVICE_L3 SERVICE_ID, PARENT_L3 PARENT_ID, SHOW_L3 SHOW, GUID_L3 GUID, SERVICE_NAME_L3 SERVICE_NAME, IMAGE_ID_L3 IMAGE_ID, IMAGE_NAME_L3 IMAGE_NAME, PRIORITY_L3 PRIORITY
          from AVAILABLE_SERVICES_TREE where SERVICE_L1 is not null and SERVICE_L2 is not null and SERVICE_L3 is not null
        union
        select SERVICE_L1 LEAF_SERVICE, CHANEL_L2 CHANEL, SERVICE_L2 SERVICE_ID, PARENT_L2 PARENT_ID, SHOW_L2 SHOW, GUID_L2 GUID, SERVICE_NAME_L2 SERVICE_NAME, IMAGE_ID_L2 IMAGE_ID, IMAGE_NAME_L2 IMAGE_NAME, PRIORITY_L2 PRIORITY
          from AVAILABLE_SERVICES_TREE where SERVICE_L1 is not null and SERVICE_L2 is not null
        union
        select SERVICE_L1 LEAF_SERVICE, CHANEL_L1 CHANEL, SERVICE_L1 SERVICE_ID, PARENT_L1 PARENT_ID, SHOW_L1 SHOW, GUID_L1 GUID, SERVICE_NAME_L1 SERVICE_NAME, IMAGE_ID_L1 IMAGE_ID, IMAGE_NAME_L1 IMAGE_NAME, PRIORITY_L1 PRIORITY
          from AVAILABLE_SERVICES_TREE where SERVICE_L1 is not null
      )
      select 
        SERVICE_ID, PARENT_ID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, GUID, CHANEL, REGION_ID, PRIORITY, AVAILABLE, PKEY_CODE 
      from (
        select 
          SERVICE_ID, PARENT_ID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, GUID, CHANEL, REGION_ID, PRIORITY, AVAILABLE
        from (
          select 
            REGION_ID, CHANEL, SERVICE_ID, PARENT_ID, GUID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, PRIORITY,
            decode(max(AVAILABLE_PAYMENTS), 1, 1, null)     AVAILABLE_PAYMENTS, 
            decode(max(AVAILABLE_ESB_AUTOP), 1, 1, null)    AVAILABLE_ESB_AUTOP,
            decode(max(AVAILABLE_IQW_AUTOP), 1, 1, null)    AVAILABLE_IQW_AUTOP, 
            decode(max(AVAILABLE_TEMPLATES), 1, 1, null)    AVAILABLE_TEMPLATES, 
            decode(max(AVAILABLE_MB_TEMPLATES), 1, 1, null) AVAILABLE_MB_TEMPLATES, 
            decode(max(AVAILABLE_BASKET), 1, 1, null)       AVAILABLE_BASKET
          from (
            select 
              decode(providers.PROVIDER_REGION_ID, -1, -1, regions.REL_REGION_ID) REGION_ID, services.CHANEL, services.SERVICE_ID, services.PARENT_ID, services.SHOW, services.GUID, services.SERVICE_NAME, services.IMAGE_ID, services.IMAGE_NAME, services.PRIORITY,
              ----ƒŒ—“”œÕŒ—“‹---------------------------------------------------------------
              providers.AVAILABLE_PAYMENTS, providers.AVAILABLE_ESB_AUTOP, providers.AVAILABLE_IQW_AUTOP, providers.AVAILABLE_TEMPLATES, providers.AVAILABLE_MB_TEMPLATES, providers.AVAILABLE_BASKET
            from AVAILABLE_PROVIDERS providers
            inner join AVAILABLE_SERVICES services on services.LEAF_SERVICE=providers.SERVICE_ID and services.CHANEL=providers.CHANEL 
            left join ALL_REGIONS regions on regions.ID=providers.PROVIDER_REGION_ID
          )
          group by REGION_ID, CHANEL, SERVICE_ID, PARENT_ID, GUID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, PRIORITY
        )
        inner join AVAILABLE_MAP on 1=1
        where 
          ( AVAILABLE_PAYMENTS=1     and AVAILABLE='PAYMENTS')         or
          ( AVAILABLE_ESB_AUTOP=1    and AVAILABLE='ESB_AUTOPAYMENTS') or
          ( AVAILABLE_IQW_AUTOP=1    and AVAILABLE='IQW_AUTOPAYMENTS') or 
          ( AVAILABLE_TEMPLATES=1    and AVAILABLE='TEMPLATES')        or 
          ( AVAILABLE_MB_TEMPLATES=1 and AVAILABLE='MB_TEMPLATES')     or 
          ( AVAILABLE_BASKET=1       and AVAILABLE='BASKET')
      );
  
      delete from SERVICE_PROVIDERS_AGGR where P_KEY = PKEY_CODE;
  
      insert into SERVICE_PROVIDERS_AGGR ( 
        ----œŒ—“¿¬Ÿ» -----------------------------------------------------------------
        ID, REGION_ID, CHANEL, PROVIDER_NAME,  ALIAS, LEGAL_NAME, CODE_RECIPIENT_SBOL, NAME_B_SERVICE, BILLING_ID, SORT_PRIORITY, UUID, INN, ACCOUNT, 
        IMAGE_ID, IMAGE_UPDATE_TIME, IMAGE_MD5, H_IMAGE_ID, H_IMAGE_MD5, H_IMAGE_UPDATE_TIME, IS_BAR_SUPPORTED, SUB_TYPE,
        ----ƒŒ—“”œÕŒ—“‹---------------------------------------------------------------
        AVAILABLE_PAYMENTS, AVAILABLE_ESB_AUTOP, AVAILABLE_IQW_AUTOP, AVAILABLE_BASKET, AVAILABLE_TEMPLATES, AVAILABLE_MB_TEMPLATES, 
        ----’À≈¡Õ€≈  –Œÿ »------------------------------------------------------------
        SERVICE_ID, SERVICE_NAME, SERVICE_IMAGE, SERVICE_GUID, SHOW_SERVICE, GROUP_ID, GROUP_NAME, GROUP_IMAGE, GROUP_GUID, SHOW_GROUP, CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE, CATEGORY_GUID, SHOW_CATEGORY, P_KEY 
      )
      with 
      CHANELS as (
        select 'MAPI' CHANEL from dual union all select 'WEB' CHANEL from dual union all select 'ATMAPI' CHANEL from dual
      ),
      PROVIDERS_REGIONS as (
        select id SERVICE_PROVIDER_ID, -1 PROVIDER_REGION_ID from SERVICE_PROVIDERS --‚ÒÂ Â„ËÓÌ˚
        union 
        select 
          SP.ID SERVICE_PROVIDER_ID, decode(sp.IS_FEDERAL, '1', 0, nvl(spr.REGION_ID, 0)) PROVIDER_REGION_ID 
        from SERVICE_PROVIDERS sp
        left join SERVICE_PROVIDER_REGIONS spr on spr.SERVICE_PROVIDER_ID=decode(sp.IS_FEDERAL, '1', 0, sp.ID)
      ),
      ALL_REGIONS as (
        select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by r.PARENT_ID = prior r.ID
        union
        select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by prior r.PARENT_ID = r.ID
        union
        select 0, ID REL_REGION_ID from REGIONS r --ÙÂ‰Â‡Î¸ÌÓÒÚ¸
      ), 
      REL_REGIONS as (
        select SERVICE_PROVIDER_ID, PROVIDER_REGION_ID from (
          select 
            SERVICE_PROVIDER_ID, decode(providers.PROVIDER_REGION_ID, -1, -1, regions.REL_REGION_ID) PROVIDER_REGION_ID
          from PROVIDERS_REGIONS providers
          left join ALL_REGIONS regions on regions.ID=providers.PROVIDER_REGION_ID
        ) group by SERVICE_PROVIDER_ID, PROVIDER_REGION_ID
      ),
      AVAILABLE_PROVIDERS as (
        select * from (
          select
            CHANEL,
            sp.ID   PROVIDER_ID,
            sp.UUID PROVIDER_UUID,
            sp.NAME PROVIDER_NAME,
            sp.SORT_PRIORITY,
            sp.INN,
            sp.LEGAL_NAME,
            sp.ALIAS,
            sp.ACCOUNT,
            sp.NAME_SERVICE NAME_B_SERVICE, 
            sp.IMAGE_ID,
            i.MD5,
            i.UPDATE_TIME IMAGE_UPDATE_TIME,
            sp.IMAGE_HELP_ID H_IMAGE_ID,
            ih.MD5 H_IMAGE_MD5,
            ih.UPDATE_TIME H_IMAGE_UPDATE_TIME,
            sp.CODE_RECIPIENT_SBOL,
            sp.BILLING_ID,
            sp.EXTERNAL_ID,
            sp.IS_BAR_SUPPORTED,
            sp.SUB_TYPE,  
            ----‘”Õ »ŒÕ¿À‹ÕŒ—“‹ ¬  ¿Õ¿À¿’---------------------------------------------------
            case  
              when CHANEL='WEB'    and AVAILABLE_PAYMENTS_FOR_IB = '1'      then decode(VISIBLE_PAYMENTS_FOR_IB, '1', 'A', 'S')
              when CHANEL='MAPI'   and AVAILABLE_PAYMENTS_FOR_M_API = '1'   then decode(VISIBLE_PAYMENTS_FOR_M_API, '1', 'A', 'S')
              when CHANEL='ATMAPI' and AVAILABLE_PAYMENTS_FOR_ATM_API = '1' then decode(VISIBLE_PAYMENTS_FOR_ATM_API, '1', 'A', 'S')
              else null 
            end as AVAILABLE_PAYMENTS,
            case  
              when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
              when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
              when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_ATM, '1', 'A', 'S')
              else null 
            end as AVAILABLE_ESB_AUTOP,
            case 
              when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
              when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
              when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_ATM, '1', 'A', 'S')
              else null 
            end as AVAILABLE_IQW_AUTOP,
            case     
              when CHANEL='WEB' and AVAILABLE_PAYMENTS_FOR_IB='1' and IS_TEMPLATE_SUPPORTED='1' then decode(VISIBLE_PAYMENTS_FOR_IB, '1', 'A', 'S')
              when CHANEL='MAPI'   then null
              when CHANEL='ATMAPI' then null
              else null 
            end as AVAILABLE_TEMPLATES,
            case     
              when CHANEL='WEB' and IS_MOBILEBANK='1' and ( select count(1) from FIELD_DESCRIPTIONS where RECIPIENT_ID=sp.ID and IS_KEY='1' )=1 then 'A'
              when CHANEL='MAPI'   then null
              when CHANEL='ATMAPI' then null
              else null 
            end as AVAILABLE_MB_TEMPLATES,
            case     
              when CHANEL='WEB' and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
              when CHANEL='MAPI'and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
              when CHANEL='ATMAPI' then null
              else null 
            end as AVAILABLE_BASKET,
            ----’À≈¡Õ€≈  –Œÿ »--------------------------------------------------------------
            category.ID CATEGORY_ID,
            category.NAME CATEGORY_NAME,
            category.IMAGE_NAME CATEGORY_IMAGE,
            category.CODE CATEGORY_GUID,
            case 
              when CHANEL='WEB' and category.SHOW_IN_SYSTEM='1'  then 1
              when CHANEL='MAPI' and category.SHOW_IN_API='1'    then 1
              when CHANEL='ATMAPI' and category.SHOW_IN_ATM='1'  then 1
              else 0
            end as SHOW_CATEGORY,
            servGroup.ID GROUP_ID,
            servGroup.NAME GROUP_NAME,
            servGroup.IMAGE_NAME GROUP_IMAGE,
            servGroup.CODE GROUP_GUID,
            case 
              when CHANEL='WEB' and servGroup.SHOW_IN_SYSTEM='1'  then 1
              when CHANEL='MAPI' and servGroup.SHOW_IN_API='1'    then 1
              when CHANEL='ATMAPI' and servGroup.SHOW_IN_ATM='1'  then 1
              else 0
            end as SHOW_GROUP,
            services.ID SERVICE_ID,
            services.NAME SERVICE_NAME,
            services.IMAGE_NAME SERVICE_IMAGE,
            services.CODE SERVICE_GUID,
            case 
              when CHANEL='WEB' and services.SHOW_IN_SYSTEM='1'  then 1
              when CHANEL='MAPI' and services.SHOW_IN_API='1'    then 1
              when CHANEL='ATMAPI' and services.SHOW_IN_ATM='1'  then 1
              else 0
            end as SHOW_SERVICE
          from SERVICE_PROVIDERS sp 
          join SERV_PROVIDER_PAYMENT_SERV spps on spps.SERVICE_PROVIDER_ID = sp.ID 
          left join IMAGES i on i.ID=sp.IMAGE_ID
          left join IMAGES ih on ih.ID=sp.IMAGE_HELP_ID
          ----’À≈¡Õ€≈  –Œÿ »--------------------------------------------------------------
          inner join PAYMENT_SERVICES services on spps.PAYMENT_SERVICE_ID = services.ID
          left join PAYMENT_SERV_PARENTS parents on services.id = parents.SERVICE_ID
          left join PAYMENT_SERVICES servGroup on parents.PARENT_ID = servGroup.id
          left join PAYMENT_SERV_PARENTS parents2 on servGroup.id = parents2.SERVICE_ID
          left join PAYMENT_SERVICES category on parents2.PARENT_ID = category.id
          ---- ¿Õ¿À€ Œ¡—À”∆»¬¿Õ»ﬂ---------------------------------------------------------
          inner join CHANELS on 1=1        
          where sp.STATE='ACTIVE'
        )
        where 
          AVAILABLE_PAYMENTS     is not null or 
          AVAILABLE_ESB_AUTOP    is not null or 
          AVAILABLE_IQW_AUTOP    is not null or 
          AVAILABLE_TEMPLATES    is not null or 
          AVAILABLE_MB_TEMPLATES is not null or 
          AVAILABLE_BASKET       is not null
      )    
      select 
        ----œŒ—“¿¬Ÿ» -----------------------------------------------------------------
        PROVIDER_ID, regions.PROVIDER_REGION_ID REGION_ID, CHANEL, PROVIDER_NAME, ALIAS, LEGAL_NAME, CODE_RECIPIENT_SBOL, NAME_B_SERVICE, BILLING_ID, SORT_PRIORITY, PROVIDER_UUID UUID, INN, ACCOUNT,
        IMAGE_ID, IMAGE_UPDATE_TIME,  MD5, H_IMAGE_ID, H_IMAGE_MD5, H_IMAGE_UPDATE_TIME, IS_BAR_SUPPORTED, SUB_TYPE,   
        ----ƒŒ—“”œÕŒ—“‹---------------------------------------------------------------
        AVAILABLE_PAYMENTS, AVAILABLE_ESB_AUTOP, AVAILABLE_IQW_AUTOP, AVAILABLE_BASKET, AVAILABLE_TEMPLATES, AVAILABLE_MB_TEMPLATES,
        ----’À≈¡Õ€≈  –Œÿ »------------------------------------------------------------
        SERVICE_ID, SERVICE_NAME, SERVICE_IMAGE, SERVICE_GUID, SHOW_SERVICE, GROUP_ID, GROUP_NAME, GROUP_IMAGE, GROUP_GUID, decode(GROUP_ID, null, null, SHOW_GROUP) SHOW_GROUP, CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE, CATEGORY_GUID, decode(CATEGORY_ID, null, null, SHOW_CATEGORY) SHOW_CATEGORY, PKEY_CODE P_KEY
      from AVAILABLE_PROVIDERS providers
      left join REL_REGIONS regions on regions.SERVICE_PROVIDER_ID=providers.PROVIDER_ID
      order by REGION_ID, CHANEL, SERVICE_ID, PROVIDER_ID;
  
    end if;

    commit;
  end;
  
end;
/