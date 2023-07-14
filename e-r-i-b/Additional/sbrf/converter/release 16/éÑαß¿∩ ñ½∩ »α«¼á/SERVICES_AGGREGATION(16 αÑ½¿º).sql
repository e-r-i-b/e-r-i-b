alter table SERVICE_PROVIDERS_AGGR add (HASH_KEY varchar2(16));

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

    cursor CURS_MODIFIED_RECORDS is -- ËÁÏÂÌÂÌËˇ ‚ ‡„Â„‡ˆËË ÔÓÒÚ‡‚˘ËÍÓ‚
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
        select /*+materialize*/ 
          t.*,
          ora_hash(PROVIDER_NAME||ALIAS||LEGAL_NAME||CODE_RECIPIENT_SBOL||NAME_B_SERVICE||BILLING_ID||SORT_PRIORITY||UUID||INN||ACCOUNT||IMAGE_ID||IMAGE_UPDATE_TIME||IMAGE_MD5||H_IMAGE_ID||H_IMAGE_MD5||H_IMAGE_UPDATE_TIME||IS_BAR_SUPPORTED||SUB_TYPE||AVAILABLE_PAYMENTS||AVAILABLE_ESB_AUTOP||AVAILABLE_IQW_AUTOP||AVAILABLE_BASKET||AVAILABLE_TEMPLATES||AVAILABLE_MB_TEMPLATES||SERVICE_NAME||SERVICE_IMAGE||SERVICE_GUID||SHOW_SERVICE||GROUP_ID||GROUP_NAME||GROUP_IMAGE||GROUP_GUID||SHOW_GROUP||CATEGORY_ID||CATEGORY_NAME||CATEGORY_IMAGE||CATEGORY_GUID||SHOW_CATEGORY) HASH_KEY
        from (
          select 
            sp.ID ID,
            CHANEL,
            sp.NAME PROVIDER_NAME,
            sp.ALIAS,
            sp.LEGAL_NAME,
            sp.CODE_RECIPIENT_SBOL,
            sp.NAME_SERVICE NAME_B_SERVICE,
            sp.BILLING_ID,
            sp.SORT_PRIORITY,
            sp.UUID UUID,
            sp.INN,
            sp.ACCOUNT,
            sp.IMAGE_ID,
            i.UPDATE_TIME IMAGE_UPDATE_TIME,
            i.MD5 IMAGE_MD5,
            sp.IMAGE_HELP_ID H_IMAGE_ID,
            ih.MD5 H_IMAGE_MD5,
            ih.UPDATE_TIME H_IMAGE_UPDATE_TIME,
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
              when CHANEL='WEB' and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
              when CHANEL='MAPI'and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
              when CHANEL='ATMAPI' then null
              else null
            end as AVAILABLE_BASKET,
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
            ----’À≈¡Õ€≈  –Œÿ »--------------------------------------------------------------
            services.ID SERVICE_ID,
            services.NAME SERVICE_NAME,
            services.IMAGE_NAME SERVICE_IMAGE,
            services.CODE SERVICE_GUID,
            case
              when CHANEL='WEB' and services.SHOW_IN_SYSTEM='1'  then 1
              when CHANEL='MAPI' and services.SHOW_IN_API='1'    then 1
              when CHANEL='ATMAPI' and services.SHOW_IN_ATM='1'  then 1
              else 0
            end as SHOW_SERVICE,
            servGroup.ID GROUP_ID,
            servGroup.NAME GROUP_NAME,
            servGroup.IMAGE_NAME GROUP_IMAGE,
            servGroup.CODE GROUP_GUID,
            decode(servGroup.ID, null, null, 
            case
              when CHANEL='WEB' and servGroup.SHOW_IN_SYSTEM='1'  then 1
              when CHANEL='MAPI' and servGroup.SHOW_IN_API='1'    then 1
              when CHANEL='ATMAPI' and servGroup.SHOW_IN_ATM='1'  then 1
              else 0
            end ) as SHOW_GROUP,
            category.ID CATEGORY_ID,
            category.NAME CATEGORY_NAME,
            category.IMAGE_NAME CATEGORY_IMAGE,
            category.CODE CATEGORY_GUID,
            decode(category.ID, null, null,
            case
              when CHANEL='WEB' and category.SHOW_IN_SYSTEM='1'  then 1
              when CHANEL='MAPI' and category.SHOW_IN_API='1'    then 1
              when CHANEL='ATMAPI' and category.SHOW_IN_ATM='1'  then 1
              else 0
            end ) as SHOW_CATEGORY
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
        ) t
        where
          AVAILABLE_PAYMENTS     is not null or
          AVAILABLE_ESB_AUTOP    is not null or
          AVAILABLE_IQW_AUTOP    is not null or
          AVAILABLE_TEMPLATES    is not null or
          AVAILABLE_MB_TEMPLATES is not null or
          AVAILABLE_BASKET       is not null
      ),
      CURRENT_AGGR as (
        select /*+materialize*/ 
          providers.*, regions.PROVIDER_REGION_ID REGION_ID, PKEY_CODE P_KEY
        from AVAILABLE_PROVIDERS providers
        left join REL_REGIONS regions on regions.SERVICE_PROVIDER_ID=providers.ID      
      ),
      MODIFIED_RECORDS as (
        select 
          s.*, c.*
        from ( 
          select P_KEY S_P_KEY, REGION_ID S_REGION_ID, CHANEL S_CHANEL, SERVICE_ID S_SERVICE_ID, ID S_ID, HASH_KEY S_HASH_KEY from SERVICE_PROVIDERS_AGGR where P_KEY=PKEY_CODE 
        ) s
        full outer join CURRENT_AGGR c on s.S_P_KEY=c.P_KEY and s.S_REGION_ID=c.REGION_ID and s.S_CHANEL=c.CHANEL and s.S_SERVICE_ID=c.SERVICE_ID and s.S_ID=c.ID
        where s.S_HASH_KEY is null or c.HASH_KEY is null or s.S_HASH_KEY!=c.HASH_KEY
      )   
      select * from MODIFIED_RECORDS;

  begin

    select
      time, ( select time from AGGREGATION_STATE where key='catalog.last.update.timestamp' ) ctime into aggregationTime, catalogTime
    from AGGREGATION_STATE
    where
      key='aggregation.last.update.timestamp' for update nowait;

    if ( catalogTime >  aggregationTime or aggregationTime is null ) then

      update AGGREGATION_STATE set time = systimestamp where key='aggregation.last.update.timestamp';

      update PROPERTIES set PROPERTY_VALUE = 3-to_number(PROPERTY_VALUE)
        where PROPERTY_KEY='com.rssl.iccs.phizic.catalog.aggreagation.current.partition' and CATEGORY='phiz'
          return PROPERTY_VALUE into PKEY_CODE;

      ----—≈–¬»—€---------------------------------------------------------------
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
        select /*+materialize*/ * from (
          select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by r.PARENT_ID = prior r.ID
          union
          select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by prior r.PARENT_ID = r.ID
          union
          select 0, ID REL_REGION_ID from REGIONS r --ÙÂ‰Â‡Î¸ÌÓÒÚ¸
          union
          select 0, -1 REL_REGION_ID from DUAL r -- -1 ‰Îˇ ÙÂ‰Â‡Î¸Ì˚ı
        )
      ),
      ALL_SERVICE_PROVIDERS as (
        select /*+materialize*/ * from (
          ----Â„ËÓÌ Ó·ÒÎÛÊË‚‡ÌËˇ Ò Û˜ÂÚÓÏ ÙÂ‰Â‡Î¸ÌÓÒÚË Ë „ÛÔÔ‡ "·ÂÁ Û˜ÂÚ‡ Â„ËÓÌ‡" REGION_ID -1
          select sp.*, decode(sp.IS_FEDERAL, '1', 0, nvl(spr.REGION_ID, 0)) PROVIDER_REGION_ID
          from SERVICE_PROVIDERS sp
          left join SERVICE_PROVIDER_REGIONS spr on spr.SERVICE_PROVIDER_ID=decode(sp.IS_FEDERAL, '1', 0, sp.ID)
          where sp.STATE='ACTIVE'
          union all
          select sp.*, -1 PROVIDER_REGION_ID from SERVICE_PROVIDERS sp where sp.STATE='ACTIVE' and sp.IS_FEDERAL!='1'
        )
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
        select /*+materialize*/ * from (
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
	  
      ----PL/SQL Merge œŒ—“¿¬Ÿ» Œ¬----------------------------------------------
      for t in CURS_MODIFIED_RECORDS loop

        if ( t.S_ID is not null and t.ID is not null ) then

          update SERVICE_PROVIDERS_AGGR s
            set s.PROVIDER_NAME=t.PROVIDER_NAME, s.ALIAS=t.ALIAS, s.LEGAL_NAME=t.LEGAL_NAME, s.CODE_RECIPIENT_SBOL=t.CODE_RECIPIENT_SBOL, s.NAME_B_SERVICE=t.NAME_B_SERVICE, s.BILLING_ID=t.BILLING_ID, s.SORT_PRIORITY=t.SORT_PRIORITY, s.UUID=t.UUID, s.INN=t.INN, s.ACCOUNT=t.ACCOUNT, s.IMAGE_ID=t.IMAGE_ID, s.IMAGE_UPDATE_TIME=t.IMAGE_UPDATE_TIME, s.IMAGE_MD5=t.IMAGE_MD5, s.H_IMAGE_ID=t.H_IMAGE_ID, s.H_IMAGE_UPDATE_TIME=t.H_IMAGE_UPDATE_TIME, s.H_IMAGE_MD5=t.H_IMAGE_MD5, s.IS_BAR_SUPPORTED=t.IS_BAR_SUPPORTED, s.SUB_TYPE=t.SUB_TYPE, s.AVAILABLE_PAYMENTS=t.AVAILABLE_PAYMENTS, s.AVAILABLE_ESB_AUTOP=t.AVAILABLE_ESB_AUTOP, s.AVAILABLE_IQW_AUTOP=t.AVAILABLE_IQW_AUTOP, s.AVAILABLE_BASKET=t.AVAILABLE_BASKET, s.AVAILABLE_TEMPLATES=t.AVAILABLE_TEMPLATES, s.AVAILABLE_MB_TEMPLATES=t.AVAILABLE_MB_TEMPLATES, s.SERVICE_NAME=t.SERVICE_NAME, s.SERVICE_IMAGE=t.SERVICE_IMAGE, s.SERVICE_GUID=t.SERVICE_GUID, s.SHOW_SERVICE=t.SHOW_SERVICE, s.GROUP_ID=t.GROUP_ID, s.GROUP_NAME=t.GROUP_NAME, s.GROUP_IMAGE=t.GROUP_IMAGE, s.GROUP_GUID=t.GROUP_GUID, s.SHOW_GROUP=t.SHOW_GROUP, s.CATEGORY_ID=t.CATEGORY_ID, s.CATEGORY_NAME=t.CATEGORY_NAME, s.CATEGORY_IMAGE=t.CATEGORY_IMAGE, s.CATEGORY_GUID=t.CATEGORY_GUID, s.SHOW_CATEGORY=t.SHOW_CATEGORY, s.HASH_KEY=t.HASH_KEY
              where t.S_P_KEY=s.P_KEY and t.S_REGION_ID=s.REGION_ID and t.S_CHANEL=s.CHANEL and t.S_SERVICE_ID=s.SERVICE_ID and t.S_ID=s.ID;

        elsif ( t.S_ID is not null and t.ID is null) then
    
          delete from SERVICE_PROVIDERS_AGGR s 
            where t.S_P_KEY=s.P_KEY and t.S_REGION_ID=s.REGION_ID and t.S_CHANEL=s.CHANEL and t.S_SERVICE_ID=s.SERVICE_ID and t.S_ID=s.ID;

        elsif (t.S_ID is null) then

          insert into SERVICE_PROVIDERS_AGGR s ( ID, PROVIDER_NAME, ALIAS, LEGAL_NAME, CODE_RECIPIENT_SBOL, NAME_B_SERVICE, BILLING_ID, SORT_PRIORITY, UUID, INN, ACCOUNT, IMAGE_ID, IMAGE_UPDATE_TIME, IMAGE_MD5, H_IMAGE_ID, H_IMAGE_UPDATE_TIME, H_IMAGE_MD5, IS_BAR_SUPPORTED, SUB_TYPE, CHANEL, REGION_ID, AVAILABLE_PAYMENTS, AVAILABLE_ESB_AUTOP, AVAILABLE_IQW_AUTOP, AVAILABLE_BASKET, AVAILABLE_TEMPLATES, AVAILABLE_MB_TEMPLATES, SERVICE_ID, SERVICE_NAME, SERVICE_IMAGE, SERVICE_GUID, SHOW_SERVICE, GROUP_ID, GROUP_NAME, GROUP_IMAGE, GROUP_GUID, SHOW_GROUP, CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE, CATEGORY_GUID, SHOW_CATEGORY, P_KEY, HASH_KEY )
            values ( t.ID, t.PROVIDER_NAME, t.ALIAS, t.LEGAL_NAME, t.CODE_RECIPIENT_SBOL, t.NAME_B_SERVICE, t.BILLING_ID, t.SORT_PRIORITY, t.UUID, t.INN, t.ACCOUNT, t.IMAGE_ID, t.IMAGE_UPDATE_TIME, t.IMAGE_MD5, t.H_IMAGE_ID, t.H_IMAGE_UPDATE_TIME, t.H_IMAGE_MD5, t.IS_BAR_SUPPORTED, t.SUB_TYPE, t.CHANEL, t.REGION_ID, t.AVAILABLE_PAYMENTS, t.AVAILABLE_ESB_AUTOP, t.AVAILABLE_IQW_AUTOP, t.AVAILABLE_BASKET, t.AVAILABLE_TEMPLATES, t.AVAILABLE_MB_TEMPLATES, t.SERVICE_ID, t.SERVICE_NAME, t.SERVICE_IMAGE, t.SERVICE_GUID, t.SHOW_SERVICE, t.GROUP_ID, t.GROUP_NAME, t.GROUP_IMAGE, t.GROUP_GUID, t.SHOW_GROUP, t.CATEGORY_ID, t.CATEGORY_NAME, t.CATEGORY_IMAGE, t.CATEGORY_GUID, t.SHOW_CATEGORY, t.P_KEY, t.HASH_KEY ); 

        end if;
        
      end loop;

--========================================================================================
-- ¬˚ÍÎ˛˜ÂÌÓ ‰Ó ËÒÔ‡‚ÎÂÌËˇ Oracle Bug 9133601 ORA-600[kcbgcur_9] from MERGE into an IOT 
--========================================================================================
--      merge into SERVICE_PROVIDERS_AGGR s 
--      using (
--        with
--        CHANELS as (
--          select 'MAPI' CHANEL from dual union all select 'WEB' CHANEL from dual union all select 'ATMAPI' CHANEL from dual
--        ),
--        PROVIDERS_REGIONS as (
--          select id SERVICE_PROVIDER_ID, -1 PROVIDER_REGION_ID from SERVICE_PROVIDERS --‚ÒÂ Â„ËÓÌ˚
--          union
--          select
--            SP.ID SERVICE_PROVIDER_ID, decode(sp.IS_FEDERAL, '1', 0, nvl(spr.REGION_ID, 0)) PROVIDER_REGION_ID
--          from SERVICE_PROVIDERS sp
--          left join SERVICE_PROVIDER_REGIONS spr on spr.SERVICE_PROVIDER_ID=decode(sp.IS_FEDERAL, '1', 0, sp.ID)
--        ),
--        ALL_REGIONS as (
--          select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by r.PARENT_ID = prior r.ID
--          union
--          select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by prior r.PARENT_ID = r.ID
--          union
--          select 0, ID REL_REGION_ID from REGIONS r --ÙÂ‰Â‡Î¸ÌÓÒÚ¸
--        ),
--        REL_REGIONS as (
--          select SERVICE_PROVIDER_ID, PROVIDER_REGION_ID from (
--            select
--              SERVICE_PROVIDER_ID, decode(providers.PROVIDER_REGION_ID, -1, -1, regions.REL_REGION_ID) PROVIDER_REGION_ID
--            from PROVIDERS_REGIONS providers
--            left join ALL_REGIONS regions on regions.ID=providers.PROVIDER_REGION_ID
--          ) group by SERVICE_PROVIDER_ID, PROVIDER_REGION_ID
--        ),
--        AVAILABLE_PROVIDERS as (
--          select /* + materialize */ 
--            t.*,
--            ora_hash(PROVIDER_NAME||ALIAS||LEGAL_NAME||CODE_RECIPIENT_SBOL||NAME_B_SERVICE||BILLING_ID||SORT_PRIORITY||UUID||INN||ACCOUNT||IMAGE_ID||IMAGE_UPDATE_TIME||IMAGE_MD5||H_IMAGE_ID||H_IMAGE_MD5||H_IMAGE_UPDATE_TIME||IS_BAR_SUPPORTED||SUB_TYPE||AVAILABLE_PAYMENTS||AVAILABLE_ESB_AUTOP||AVAILABLE_IQW_AUTOP||AVAILABLE_BASKET||AVAILABLE_TEMPLATES||AVAILABLE_MB_TEMPLATES||SERVICE_NAME||SERVICE_IMAGE||SERVICE_GUID||SHOW_SERVICE||GROUP_ID||GROUP_NAME||GROUP_IMAGE||GROUP_GUID||SHOW_GROUP||CATEGORY_ID||CATEGORY_NAME||CATEGORY_IMAGE||CATEGORY_GUID||SHOW_CATEGORY) HASH_KEY
--          from (
--            select 
--              sp.ID ID,
--              CHANEL,
--              sp.NAME PROVIDER_NAME,
--              sp.ALIAS,
--              sp.LEGAL_NAME,
--              sp.CODE_RECIPIENT_SBOL,
--              sp.NAME_SERVICE NAME_B_SERVICE,
--              sp.BILLING_ID,
--              sp.SORT_PRIORITY,
--              sp.UUID UUID,
--              sp.INN,
--              sp.ACCOUNT,
--              sp.IMAGE_ID,
--              i.UPDATE_TIME IMAGE_UPDATE_TIME,
--              i.MD5 IMAGE_MD5,
--              sp.IMAGE_HELP_ID H_IMAGE_ID,
--              ih.MD5 H_IMAGE_MD5,
--              ih.UPDATE_TIME H_IMAGE_UPDATE_TIME,
--              sp.IS_BAR_SUPPORTED,
--              sp.SUB_TYPE,
--              ----‘”Õ »ŒÕ¿À‹ÕŒ—“‹ ¬  ¿Õ¿À¿’---------------------------------------------------
--              case
--                when CHANEL='WEB'    and AVAILABLE_PAYMENTS_FOR_IB = '1'      then decode(VISIBLE_PAYMENTS_FOR_IB, '1', 'A', 'S')
--                when CHANEL='MAPI'   and AVAILABLE_PAYMENTS_FOR_M_API = '1'   then decode(VISIBLE_PAYMENTS_FOR_M_API, '1', 'A', 'S')
--                when CHANEL='ATMAPI' and AVAILABLE_PAYMENTS_FOR_ATM_API = '1' then decode(VISIBLE_PAYMENTS_FOR_ATM_API, '1', 'A', 'S')
--                else null
--              end as AVAILABLE_PAYMENTS,
--              case
--                when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
--                when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
--                when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_ATM, '1', 'A', 'S')
--                else null
--              end as AVAILABLE_ESB_AUTOP,
--              case
--                when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
--                when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
--                when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_ATM, '1', 'A', 'S')
--                else null
--              end as AVAILABLE_IQW_AUTOP,
--              case
--                when CHANEL='WEB' and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
--                when CHANEL='MAPI'and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
--                when CHANEL='ATMAPI' then null
--                else null
--              end as AVAILABLE_BASKET,
--              case
--                when CHANEL='WEB' and AVAILABLE_PAYMENTS_FOR_IB='1' and IS_TEMPLATE_SUPPORTED='1' then decode(VISIBLE_PAYMENTS_FOR_IB, '1', 'A', 'S')
--                when CHANEL='MAPI'   then null
--                when CHANEL='ATMAPI' then null
--                else null
--              end as AVAILABLE_TEMPLATES,
--              case
--                when CHANEL='WEB' and IS_MOBILEBANK='1' and ( select count(1) from FIELD_DESCRIPTIONS where RECIPIENT_ID=sp.ID and IS_KEY='1' )=1 then 'A'
--                when CHANEL='MAPI'   then null
--                when CHANEL='ATMAPI' then null
--                else null
--              end as AVAILABLE_MB_TEMPLATES,
--              ----’À≈¡Õ€≈  –Œÿ »--------------------------------------------------------------
--              services.ID SERVICE_ID,
--              services.NAME SERVICE_NAME,
--              services.IMAGE_NAME SERVICE_IMAGE,
--              services.CODE SERVICE_GUID,
--              case
--                when CHANEL='WEB' and services.SHOW_IN_SYSTEM='1'  then 1
--                when CHANEL='MAPI' and services.SHOW_IN_API='1'    then 1
--                when CHANEL='ATMAPI' and services.SHOW_IN_ATM='1'  then 1
--                else 0
--              end as SHOW_SERVICE,
--              servGroup.ID GROUP_ID,
--              servGroup.NAME GROUP_NAME,
--              servGroup.IMAGE_NAME GROUP_IMAGE,
--              servGroup.CODE GROUP_GUID,
--              decode(servGroup.ID, null, null, 
--              case
--                when CHANEL='WEB' and servGroup.SHOW_IN_SYSTEM='1'  then 1
--                when CHANEL='MAPI' and servGroup.SHOW_IN_API='1'    then 1
--                when CHANEL='ATMAPI' and servGroup.SHOW_IN_ATM='1'  then 1
--                else 0
--              end ) as SHOW_GROUP,
--              category.ID CATEGORY_ID,
--              category.NAME CATEGORY_NAME,
--              category.IMAGE_NAME CATEGORY_IMAGE,
--              category.CODE CATEGORY_GUID,
--              decode(category.ID, null, null,
--              case
--                when CHANEL='WEB' and category.SHOW_IN_SYSTEM='1'  then 1
--                when CHANEL='MAPI' and category.SHOW_IN_API='1'    then 1
--                when CHANEL='ATMAPI' and category.SHOW_IN_ATM='1'  then 1
--                else 0
--              end ) as SHOW_CATEGORY
--            from SERVICE_PROVIDERS sp
--            join SERV_PROVIDER_PAYMENT_SERV spps on spps.SERVICE_PROVIDER_ID = sp.ID
--            left join IMAGES i on i.ID=sp.IMAGE_ID
--            left join IMAGES ih on ih.ID=sp.IMAGE_HELP_ID
--            ----’À≈¡Õ€≈  –Œÿ »--------------------------------------------------------------
--            inner join PAYMENT_SERVICES services on spps.PAYMENT_SERVICE_ID = services.ID
--            left join PAYMENT_SERV_PARENTS parents on services.id = parents.SERVICE_ID
--            left join PAYMENT_SERVICES servGroup on parents.PARENT_ID = servGroup.id
--            left join PAYMENT_SERV_PARENTS parents2 on servGroup.id = parents2.SERVICE_ID
--            left join PAYMENT_SERVICES category on parents2.PARENT_ID = category.id
--            ---- ¿Õ¿À€ Œ¡—À”∆»¬¿Õ»ﬂ---------------------------------------------------------
--            inner join CHANELS on 1=1
--            where sp.STATE='ACTIVE'
--          ) t
--          where
--            AVAILABLE_PAYMENTS     is not null or
--            AVAILABLE_ESB_AUTOP    is not null or
--            AVAILABLE_IQW_AUTOP    is not null or
--            AVAILABLE_TEMPLATES    is not null or
--            AVAILABLE_MB_TEMPLATES is not null or
--            AVAILABLE_BASKET       is not null
--        ),
--        CURRENT_AGGR as (
--          select /* + materialize */ 
--            providers.*, regions.PROVIDER_REGION_ID REGION_ID, PKEY_CODE P_KEY
--          from AVAILABLE_PROVIDERS providers
--          left join REL_REGIONS regions on regions.SERVICE_PROVIDER_ID=providers.ID      
--        ),
--        MODIFIED_RECORDS as (
--          select 
--            s.*, c.*
--          from ( 
--            select P_KEY S_P_KEY, REGION_ID S_REGION_ID, CHANEL S_CHANEL, SERVICE_ID S_SERVICE_ID, ID S_ID, HASH_KEY S_HASH_KEY from SERVICE_PROVIDERS_AGGR where P_KEY=PKEY_CODE 
--          ) s
--          full outer join CURRENT_AGGR c on s.S_P_KEY=c.P_KEY and s.S_REGION_ID=c.REGION_ID and s.S_CHANEL=c.CHANEL and s.S_SERVICE_ID=c.SERVICE_ID and s.S_ID=c.ID
--          where s.S_HASH_KEY is null or c.HASH_KEY is null or s.S_HASH_KEY!=c.HASH_KEY
--        )   
--        select * from MODIFIED_RECORDS
--      ) t on ( t.S_P_KEY=s.P_KEY and t.S_REGION_ID=s.REGION_ID and t.S_CHANEL=s.CHANEL and t.S_SERVICE_ID=s.SERVICE_ID and t.S_ID=s.ID)
--      when matched then update set 
--        s.PROVIDER_NAME=decode(t.ID, null, s.PROVIDER_NAME, t.PROVIDER_NAME), s.ALIAS=decode(t.ID, null, s.ALIAS, t.ALIAS), s.LEGAL_NAME=decode(t.ID, null, s.LEGAL_NAME, t.LEGAL_NAME), s.CODE_RECIPIENT_SBOL=decode(t.ID, null, s.CODE_RECIPIENT_SBOL, t.CODE_RECIPIENT_SBOL), s.NAME_B_SERVICE=decode(t.ID, null, s.NAME_B_SERVICE, t.NAME_B_SERVICE), s.BILLING_ID=decode(t.ID, null, s.BILLING_ID, t.BILLING_ID), s.SORT_PRIORITY=decode(t.ID, null, s.SORT_PRIORITY, t.SORT_PRIORITY), s.UUID=decode(t.ID, null, s.UUID, t.UUID), s.INN=decode(t.ID, null, s.INN, t.INN), s.ACCOUNT=decode(t.ID, null, s.ACCOUNT, t.ACCOUNT), s.IMAGE_ID=decode(t.ID, null, s.IMAGE_ID, t.IMAGE_ID), s.IMAGE_UPDATE_TIME=decode(t.ID, null, s.IMAGE_UPDATE_TIME, t.IMAGE_UPDATE_TIME), s.IMAGE_MD5=decode(t.ID, null, s.IMAGE_MD5, t.IMAGE_MD5), s.H_IMAGE_ID=decode(t.ID, null, s.H_IMAGE_ID, t.H_IMAGE_ID), s.H_IMAGE_UPDATE_TIME=decode(t.ID, null, s.H_IMAGE_UPDATE_TIME, t.H_IMAGE_UPDATE_TIME), s.H_IMAGE_MD5=decode(t.ID, null, s.H_IMAGE_MD5, t.H_IMAGE_MD5), s.IS_BAR_SUPPORTED=decode(t.ID, null, s.IS_BAR_SUPPORTED, t.IS_BAR_SUPPORTED), s.SUB_TYPE=decode(t.ID, null, s.SUB_TYPE, t.SUB_TYPE), s.AVAILABLE_PAYMENTS=decode(t.ID, null, s.AVAILABLE_PAYMENTS, t.AVAILABLE_PAYMENTS), s.AVAILABLE_ESB_AUTOP=decode(t.ID, null, s.AVAILABLE_ESB_AUTOP, t.AVAILABLE_ESB_AUTOP), s.AVAILABLE_IQW_AUTOP=decode(t.ID, null, s.AVAILABLE_IQW_AUTOP, t.AVAILABLE_IQW_AUTOP), s.AVAILABLE_BASKET=decode(t.ID, null, s.AVAILABLE_BASKET, t.AVAILABLE_BASKET), s.AVAILABLE_TEMPLATES=decode(t.ID, null, s.AVAILABLE_TEMPLATES, t.AVAILABLE_TEMPLATES), s.AVAILABLE_MB_TEMPLATES=decode(t.ID, null, s.AVAILABLE_MB_TEMPLATES, t.AVAILABLE_MB_TEMPLATES), s.SERVICE_NAME=decode(t.ID, null, s.SERVICE_NAME, t.SERVICE_NAME), s.SERVICE_IMAGE=decode(t.ID, null, s.SERVICE_IMAGE, t.SERVICE_IMAGE), s.SERVICE_GUID=decode(t.ID, null, s.SERVICE_GUID, t.SERVICE_GUID), s.SHOW_SERVICE=decode(t.ID, null, s.SHOW_SERVICE, t.SHOW_SERVICE), s.GROUP_ID=decode(t.ID, null, s.GROUP_ID, t.GROUP_ID), s.GROUP_NAME=decode(t.ID, null, s.GROUP_NAME, t.GROUP_NAME), s.GROUP_IMAGE=decode(t.ID, null, s.GROUP_IMAGE, t.GROUP_IMAGE), s.GROUP_GUID=decode(t.ID, null, s.GROUP_GUID, t.GROUP_GUID), s.SHOW_GROUP=decode(t.ID, null, s.SHOW_GROUP, t.SHOW_GROUP), s.CATEGORY_ID=decode(t.ID, null, s.CATEGORY_ID, t.CATEGORY_ID), s.CATEGORY_NAME=decode(t.ID, null, s.CATEGORY_NAME, t.CATEGORY_NAME), s.CATEGORY_IMAGE=decode(t.ID, null, s.CATEGORY_IMAGE, t.CATEGORY_IMAGE), s.CATEGORY_GUID=decode(t.ID, null, s.CATEGORY_GUID, t.CATEGORY_GUID), s.SHOW_CATEGORY=decode(t.ID, null, s.SHOW_CATEGORY, t.SHOW_CATEGORY), s.HASH_KEY=decode(t.ID, null, s.HASH_KEY, t.HASH_KEY)
--        delete where t.ID is null
--      when not matched then insert ( ID, PROVIDER_NAME, ALIAS, LEGAL_NAME, CODE_RECIPIENT_SBOL, NAME_B_SERVICE, BILLING_ID, SORT_PRIORITY, UUID, INN, ACCOUNT, IMAGE_ID, IMAGE_UPDATE_TIME, IMAGE_MD5, H_IMAGE_ID, H_IMAGE_UPDATE_TIME, H_IMAGE_MD5, IS_BAR_SUPPORTED, SUB_TYPE, CHANEL, REGION_ID, AVAILABLE_PAYMENTS, AVAILABLE_ESB_AUTOP, AVAILABLE_IQW_AUTOP, AVAILABLE_BASKET, AVAILABLE_TEMPLATES, AVAILABLE_MB_TEMPLATES, SERVICE_ID, SERVICE_NAME, SERVICE_IMAGE, SERVICE_GUID, SHOW_SERVICE, GROUP_ID, GROUP_NAME, GROUP_IMAGE, GROUP_GUID, SHOW_GROUP, CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE, CATEGORY_GUID, SHOW_CATEGORY, P_KEY, HASH_KEY )
--        values ( t.ID, t.PROVIDER_NAME, t.ALIAS, t.LEGAL_NAME, t.CODE_RECIPIENT_SBOL, t.NAME_B_SERVICE, t.BILLING_ID, t.SORT_PRIORITY, t.UUID, t.INN, t.ACCOUNT, t.IMAGE_ID, t.IMAGE_UPDATE_TIME, t.IMAGE_MD5, t.H_IMAGE_ID, t.H_IMAGE_UPDATE_TIME, t.H_IMAGE_MD5, t.IS_BAR_SUPPORTED, t.SUB_TYPE, t.CHANEL, t.REGION_ID, t.AVAILABLE_PAYMENTS, t.AVAILABLE_ESB_AUTOP, t.AVAILABLE_IQW_AUTOP, t.AVAILABLE_BASKET, t.AVAILABLE_TEMPLATES, t.AVAILABLE_MB_TEMPLATES, t.SERVICE_ID, t.SERVICE_NAME, t.SERVICE_IMAGE, t.SERVICE_GUID, t.SHOW_SERVICE, t.GROUP_ID, t.GROUP_NAME, t.GROUP_IMAGE, t.GROUP_GUID, t.SHOW_GROUP, t.CATEGORY_ID, t.CATEGORY_NAME, t.CATEGORY_IMAGE, t.CATEGORY_GUID, t.SHOW_CATEGORY, t.P_KEY, t.HASH_KEY ) 
--          where t.S_ID is null;

    end if;

    commit;
  end;

end;
/