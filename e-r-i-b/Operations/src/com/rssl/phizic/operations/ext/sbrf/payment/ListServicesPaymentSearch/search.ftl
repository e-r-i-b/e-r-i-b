select
    category.id CATEGORY_ID,
    category.name CATEGORY_NAME,
    servGroup.ID GROUP_ID,
    servGroup.NAME GROUP_NAME,
    services.ID SERVICES_ID,
    services.NAME SERVICES_NAME,
    null PROVIDER_ID,
    null PROVIDER_NAME,
    null PROVIDER_INN,
    null PROVIDER_ACCOUNT,
    100-services.PRIORITY SORT_PRIORITY,
    'service' type,
    (case  when category.SHOW_IN_SYSTEM ='0' or servGroup.SHOW_IN_SYSTEM= '0' or services.SHOW_IN_SYSTEM='0' then '0'
    else '1' end) SHOW_BREADCRUMBS
from
    (select id,name,SHOW_IN_SYSTEM, PRIORITY from PAYMENT_SERVICES where upper(NAME)  LIKE upper(:extra_like_search)) services
    left join PAYMENT_SERV_PARENTS on services.id = PAYMENT_SERV_PARENTS.SERVICE_ID
    left join PAYMENT_SERVICES servGroup on PAYMENT_SERV_PARENTS.PARENT_ID = servGroup.id
    left join PAYMENT_SERV_PARENTS parent2 on servGroup.id = parent2.SERVICE_ID
    left join PAYMENT_SERVICES category on parent2.PARENT_ID = category.id
 <#if serviceId?has_content>
    where servGroup.id = :extra_serviceId or category.id = :extra_serviceId
 </#if>
UNION

SELECT
    category.id CATEGORY_ID,
    category.name CATEGORY_NAME,
    servGroup.ID GROUP_ID,
    servGroup.NAME GROUP_NAME,
    services.ID SERVICES_ID,
    services.NAME SERVICES_NAME,
    min(provider.ID) PROVIDER_ID,
    provider.NAME PROVIDER_NAME,
    provider.INN PROVIDER_INN,
    provider.ACCOUNT PROVIDER_ACCOUNT,
    max(provider.SORT_PRIORITY) SORT_PRIORITY,
    'provider' type,
    (case  when category.SHOW_IN_SYSTEM ='0' or servGroup.SHOW_IN_SYSTEM= '0' or services.SHOW_IN_SYSTEM='0' then '0'
    else '1' end) SHOW_BREADCRUMBS
FROM
    (SELECT
        providers.id,
        name,
        inn,
        account,
        CODE_RECIPIENT_SBOL,
        SORT_PRIORITY,
        BILLING_ID,
    <#if isAutoPayProvider == "true">
        VISIBLE_AUTOPAYMENTS_FOR_IB VISIBLE_PAYMENTS_FOR_IB
    <#else>
        VISIBLE_PAYMENTS_FOR_IB VISIBLE_PAYMENTS_FOR_IB
    </#if>
    FROM SERVICE_PROVIDERS    providers
 <#if isRegion == "true">
    LEFT JOIN service_provider_regions spreg on providers.id = spreg.service_provider_id
 </#if>
 <#if invoiceProvider == "true">
    JOIN AUTOPAY_SETTINGS sett on sett.RECIPIENT_ID = providers.ID
 </#if>
    WHERE
             <#if onlyTemplateSupported == "true">
                providers.IS_TEMPLATE_SUPPORTED = 1 AND
             </#if>
            (
                (providers.ACCOUNT_TYPE in
                    -- хоть какие-то переводы должны быть доступны
                    ('ALL'
                    --поставщик поддерживает карточные переводы
                    <#if isCardAccountType == "true" || clientType == "CARD">
                        , 'CARD'
                    </#if>
                    --поставщик поддерживает переводы со счёта
                    <#if isDepositType == "true">
                        , 'DEPOSIT'
                    </#if>
                    )
                )
                <#if isFederalProvider == "true">
                OR
                (
                    providers.ACCOUNT_TYPE in ('CARD', 'ALL')
                    AND
                    providers.IS_FEDERAL = 1
                )
                </#if>
            )

            and  (providers.STATE = 'ACTIVE')
            <#if invoiceProvider == "true">
                and providers.IS_AUTOPAYMENT_SUPPORTED = '1'
                and sett."TYPE" = 'INVOICE'
                and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :extra_IQWaveUUID
            </#if>
            <#if isInternetBank == "true" && isAutoPayProvider != "true" && isMobilebank != "true">
                AND providers.AVAILABLE_PAYMENTS_FOR_IB = 1
            </#if>
            <#if isMobilebank == "true">
                and
                (providers.ID in  (
                        SELECT ID FROM SERVICE_PROVIDERS sp
                            WHERE STATE = 'ACTIVE' AND IS_MOBILEBANK = 1
                                AND EXISTS (
                                    SELECT 1 FROM FIELD_DESCRIPTIONS providerField
                                    WHERE providerField.RECIPIENT_ID = sp.ID
                                      AND providerField.IS_KEY = 1
                                    GROUP BY sp.ID
                                    HAVING COUNT(sp.ID) = 1
                                )
                        )
                   )
            </#if>
            <#if isAutoPayProvider == "true">
                and
                (
                    -- доступен автоплатеж
                    providers.IS_AUTOPAYMENT_SUPPORTED = 1
                    -- карточный поставщик
                    and providers.ACCOUNT_TYPE in ('CARD', 'ALL')
                    <#if isIQWaveAutoPaymentPermit == "false">
                        -- не iqwave
                        and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :IQWaveUUID
                    <#elseif isESBAutoPaymentPermit == "false">
                        -- не через шину.
                        and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) = :IQWaveUUID
                    </#if>
                )
            </#if>
            <#if isRegion == "true">
              and  ( spreg.REGION_ID is null OR
                    providers.IS_FEDERAL = 1 OR
                    spreg.REGION_ID in
                      (select r.id from regions r
                            start with r.id = :extra_region_id
                            connect by r.parent_id = prior r.id )
                    <#if isParentRegionId == "true">
                       or spreg.REGION_ID in
                        (select r.id from regions r
                            start with r.id = :extra_parent_region_id
                            connect by r.id = prior r.parent_id)
                    </#if>
               )
            </#if>
            AND
            (
            <#if search_long?has_content>
                providers.INN LIKE :extra_like_search_long
                or providers.ACCOUNT LIKE :extra_like_search_long
                or
            </#if>
            <#if search?has_content>
                upper(providers.NAME) LIKE upper(:extra_like_search)
                or upper(providers.ALIAS) LIKE upper(:extra_like_search)
                or upper(providers.LEGAL_NAME)  LIKE upper(:extra_like_search)
            </#if>
            )
) provider
join SERV_PROVIDER_PAYMENT_SERV provider_payment ON provider_payment.SERVICE_PROVIDER_ID  = provider.ID
join PAYMENT_SERVICES services on provider_payment.PAYMENT_SERVICE_ID = services.ID
left join PAYMENT_SERV_PARENTS parents on services.id = parents.SERVICE_ID
left join PAYMENT_SERVICES servGroup on parents.PARENT_ID = servGroup.id
left join PAYMENT_SERV_PARENTS parents2 on servGroup.id = parents2.SERVICE_ID
left join PAYMENT_SERVICES category on parents2.PARENT_ID = category.id
<#if serviceId?has_content>
WHERE
    category.id = :extra_serviceId
    or servGroup.id  = :extra_serviceId
    or services.id = :extra_serviceId
</#if>
GROUP BY
    category.id,
    category.name,
    servGroup.ID,
    servGroup.NAME,
    services.ID,
    services.NAME,
    provider.NAME,
    provider.INN,
    provider.ACCOUNT,
    provider.CODE_RECIPIENT_SBOL,
    provider.BILLING_ID,
    category.SHOW_IN_SYSTEM,
    servGroup.SHOW_IN_SYSTEM,
    services.SHOW_IN_SYSTEM
<#if needSearchPayments == "true">
    UNION
    SELECT
        null CATEGORY_ID,
        cast(name as nvarchar2(256)) CATEGORY_NAME,
        null GROUP_ID,
        null GROUP_NAME,
        null SERVICES_NAME,
        null SERVICES_ID,
        id PROVIDER_ID,
        DESCRIPTION PROVIDER_NAME,
        null PROVIDER_INN,
        null PROVIDER_ACCOUNT,
        0 SORT_PRIORITY,
        'payment' type,
        '0'

    FROM PAYMENTFORMS
            WHERE upper(PAYMENTFORMS.DESCRIPTION) LIKE upper(:extra_like_search)
            AND PAYMENTFORMS.NAME in (
                SELECT SERVICES.SERVICE_KEY  FROM SCHEMEOWNS join SCHEMESSERVICES on SCHEMEOWNS.SCHEME_ID = SCHEMESSERVICES.SCHEME_ID
                JOIN SERVICES on SCHEMESSERVICES.SERVICE_ID = SERVICES.ID where SCHEMEOWNS.login_id = :extra_loginId
            )
            AND  PAYMENTFORMS.NAME in (:extra_paymentList)

 </#if>
ORDER BY type desc, SORT_PRIORITY desc, PROVIDER_NAME,CATEGORY_NAME, GROUP_NAME, SERVICES_NAME