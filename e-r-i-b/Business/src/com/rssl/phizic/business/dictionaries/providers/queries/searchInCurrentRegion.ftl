SELECT
    category.id CATEGORY_ID,
    category.name CATEGORY_NAME,
    servGroup.ID GROUP_ID,
    servGroup.NAME GROUP_NAME,
    services.ID SERVICES_ID,
    services.NAME SERVICES_NAME,
    min(provider.ID) PROVIDER_ID,
    provider.NAME PROVIDER_NAME,
    max(provider.IMAGE_ID) IMAGE_ID,
    provider.KPP PROVIDER_KPP,
    max(provider.SORT_PRIORITY) SORT_PRIORITY,
    null REGION_NAME,
    'homeRegion' TYPE,
    provider.IS_AUTOPAYMENT_SUPPORTED IS_AUTOPAYMENT_SUPPORTED,
    provider.IS_AUTOPAYMENT_SUPPORTED_API IS_AUTOPAYMENT_SUPPORTED_API,
    provider.IS_AUTOPAYMENT_SUPPORTED_ATM IS_AUTOPAYMENT_SUPPORTED_ATM
FROM
(select
providers.ID,
providers.NAME,
providers.IMAGE_ID,
providers.KPP,
providers.ACCOUNT,
providers.SORT_PRIORITY,
providers.CODE_RECIPIENT_SBOL,
providers.BILLING_ID,
providers.IS_AUTOPAYMENT_SUPPORTED,
providers.IS_AUTOPAYMENT_SUPPORTED_API,
providers.IS_AUTOPAYMENT_SUPPORTED_ATM
from
SERVICE_PROVIDERS providers
<#if isRegion == "true">
    LEFT JOIN service_provider_regions spreg on providers.id = spreg.service_provider_id
</#if>
WHERE
providers.STATE = 'ACTIVE'
AND
providers.AVAILABLE_PAYMENTS_FOR_IB = '1'
<#if isRegion == "true">
    AND (
    spreg.REGION_ID is null OR
    providers.IS_FEDERAL = 1 OR
    spreg.REGION_ID in
    (
        select r.id from regions r
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
and
(
providers.account_type in (:extra_accountType)
AND providers.INN = :extra_INN
AND providers.ACCOUNT = :extra_account
AND providers.BANK_CODE = :extra_BIC
)
) provider
LEFT JOIN IMAGES images on provider.IMAGE_ID = images.ID
join SERV_PROVIDER_PAYMENT_SERV provider_payment ON provider_payment.SERVICE_PROVIDER_ID  = provider.ID
join PAYMENT_SERVICES services on provider_payment.PAYMENT_SERVICE_ID = services.ID
left join PAYMENT_SERV_PARENTS parents on services.id = parents.SERVICE_ID
left join PAYMENT_SERVICES servGroup on parents.PARENT_ID = servGroup.id
left join PAYMENT_SERV_PARENTS parents2 on servGroup.id = parents2.SERVICE_ID
left join PAYMENT_SERVICES category on parents2.PARENT_ID = category.id

GROUP BY
category.id,
category.name,
servGroup.ID,
servGroup.NAME,
services.ID,
services.NAME,
provider.NAME,
images.MD5,
provider.KPP,
provider.CODE_RECIPIENT_SBOL,
provider.BILLING_ID,
provider.IS_AUTOPAYMENT_SUPPORTED,
provider.IS_AUTOPAYMENT_SUPPORTED_API,
provider.IS_AUTOPAYMENT_SUPPORTED_ATM
ORDER BY SORT_PRIORITY desc, PROVIDER_NAME, CATEGORY_NAME, GROUP_NAME, SERVICES_NAME

