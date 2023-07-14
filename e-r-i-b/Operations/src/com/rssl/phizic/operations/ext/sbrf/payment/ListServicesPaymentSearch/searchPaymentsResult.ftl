<#--
    Опорный объект: PAYMENT_SERVICES
    Предикаты доступа: фулскан
    Кардинальность: несколько десятков
-->
SELECT
    category.id CATEGORY_ID,
    cast(trim(category.name) as varchar2(256)) CATEGORY_NAME,
    servGroup.ID GROUP_ID,
    cast(trim(servGroup.NAME) as varchar2(256)) GROUP_NAME,
    services.ID SERVICES_ID,
    cast(trim(services.NAME) as varchar2(256)) SERVICES_NAME,
    null PROVIDER_ID,
<#if orderType = "byRegion">
    null PROVIDER_NAME,
<#else>
    cast(trim(services.NAME) as varchar2(256)) PROVIDER_NAME,
</#if>
    services.IMAGE_ID IMAGE_ID,
    services.IMAGE_NAME IMAGE_NAME,
    null PROVIDER_INN,
    null PROVIDER_ACCOUNT,
    100-services.PRIORITY SORT_PRIORITY,
    null NAME_B_SERVICE,
    'service' type,
    (case  when category.SHOW_IN_SYSTEM ='0' or servGroup.SHOW_IN_SYSTEM= '0' or services.SHOW_IN_SYSTEM='0' then '0'
    else '1' end) SHOW_BREADCRUMBS
from
    PAYMENT_SERVICES services
    left join PAYMENT_SERV_PARENTS on services.id = PAYMENT_SERV_PARENTS.SERVICE_ID
    left join PAYMENT_SERVICES servGroup on PAYMENT_SERV_PARENTS.PARENT_ID = servGroup.id
    left join PAYMENT_SERV_PARENTS parent2 on servGroup.id = parent2.SERVICE_ID
    left join PAYMENT_SERVICES category on parent2.PARENT_ID = category.id
WHERE upper(services.NAME)  LIKE upper(:extra_like_search)

UNION ALL
<#--
      Опорный объект: SP_AGRR_PK
      Предикаты доступа: access("PROVIDERS"."P_KEY"=:EXTRA_PKEY AND "PROVIDERS"."REGION_ID"=TO_NUMBER(:EXTRA_REGION_ID) AND "PROVIDERS"."CHANEL"=:EXTRA_CHANEL)
      Кардинальность: если не выбран регион оплаты, то около 90К, для выбранного региона - менее 10К
-->
select
    providers.CATEGORY_ID CATEGORY_ID,
    providers.CATEGORY_NAME CATEGORY_NAME,
    providers.GROUP_ID GROUP_ID,
    providers.GROUP_NAME GROUP_NAME,
    providers.SERVICE_ID SERVICES_ID,
    providers.SERVICE_NAME SERVICES_NAME,

    providers.ID PROVIDER_ID,
    providers.PROVIDER_NAME PROVIDER_NAME,
    providers.IMAGE_ID IMAGE_ID,
    null    IMAGE_NAME,

    providers.INN PROVIDER_INN,
    providers.ACCOUNT PROVIDER_ACCOUNT,

    providers.SORT_PRIORITY SORT_PRIORITY,
    providers.NAME_B_SERVICE NAME_B_SERVICE,
    'provider' type,
    providers.SHOW_SERVICE SHOW_BREADCRUMBS

from SERVICE_PROVIDERS_AGGR providers
where
    providers.CHANEL = :extra_chanel
    and providers.P_KEY = :extra_pkey
    and providers.REGION_ID = :extra_region_id
    and
        (
        ('PAYMENTS' in (:extra_functionality) and  providers.AVAILABLE_PAYMENTS is not null) or
        ('ESB_AUTOPAYMENTS' in (:extra_functionality) and  providers.AVAILABLE_ESB_AUTOP is not null) or
        ('IQW_AUTOPAYMENTS' in (:extra_functionality) and  providers.AVAILABLE_IQW_AUTOP is not null) or
        ('TEMPLATES' in (:extra_functionality) and  providers.AVAILABLE_TEMPLATES is not null) or
        ('MB_TEMPLATES' in (:extra_functionality) and  providers.AVAILABLE_MB_TEMPLATES is not null) or
        ('BASKET' in (:extra_functionality) and  providers.AVAILABLE_BASKET is not null)
        )
    and
        (
        (:extra_like_search_long is not null and (providers.INN LIKE :extra_like_search_long or providers.ACCOUNT LIKE :extra_like_search_long)) or
        (:extra_like_search is not null and (upper(providers.PROVIDER_NAME) LIKE upper(:extra_like_search) or upper(providers.ALIAS) LIKE upper(:extra_like_search)  or upper(providers.LEGAL_NAME)  LIKE upper(:extra_like_search)))
        )
<#if paymentList?has_content>
UNION ALL
<#--
    Опорный объект: PAYMENTFORMS
    Предикаты доступа: фулскан
    Кардинальность: несколько десятков
-->
SELECT
    null CATEGORY_ID,
<#if orderType= "byRegion">
    cast(name as varchar2(256)) category_name,
<#else>
    cast('Прочие операции' as varchar2(256)) category_name,
</#if>
    null GROUP_ID,
<#if orderType= "byRegion">
    cast('Платежи' as varchar2(128)) GROUP_NAME,
<#else>
    null GROUP_NAME,
</#if>
    null SERVICES_ID,
    null SERVICES_NAME,
    id PROVIDER_ID,
    DESCRIPTION PROVIDER_NAME,
    null IMAGE_ID,
    null IMAGE_NAME,
    null PROVIDER_INN,
    null PROVIDER_ACCOUNT,
    0 SORT_PRIORITY,
    null NAME_B_SERVICE,
    'payment' type,
    '0'  SHOW_BREADCRUMBS

FROM PAYMENTFORMS
            WHERE upper(PAYMENTFORMS.DESCRIPTION) LIKE upper(:extra_like_search)
            AND PAYMENTFORMS.NAME in (
                SELECT SERVICES.SERVICE_KEY  FROM SCHEMEOWNS join SCHEMESSERVICES on SCHEMEOWNS.SCHEME_ID = SCHEMESSERVICES.SCHEME_ID
                JOIN SERVICES on SCHEMESSERVICES.SERVICE_ID = SERVICES.ID where SCHEMEOWNS.login_id = :extra_loginId
            )
            AND  PAYMENTFORMS.NAME in (:extra_paymentList)
</#if>
<#if orderType= "byRegion">
ORDER BY type desc, SORT_PRIORITY desc, PROVIDER_NAME, CATEGORY_NAME, GROUP_NAME, SERVICES_NAME
</#if>

<#if orderType= "byName">
ORDER BY PROVIDER_NAME, type desc, SORT_PRIORITY desc, CATEGORY_NAME, GROUP_NAME, SERVICES_NAME
</#if>

<#if orderType= "byService">
ORDER BY SERVICES_NAME, type desc, SORT_PRIORITY desc, PROVIDER_NAME, CATEGORY_NAME, GROUP_NAME
</#if>