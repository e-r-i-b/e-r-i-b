SELECT
regions.name REGION_NAME,
category.id CATEGORY_ID,
category.name CATEGORY_NAME,
servGroup.ID GROUP_ID,
servGroup.NAME GROUP_NAME,
services.ID SERVICES_ID,
services.NAME SERVICES_NAME,
min(providers.ID) PROVIDER_ID,
providers.NAME PROVIDER_NAME,
max(providers.IMAGE_ID) IMAGE_ID,
providers.KPP PROVIDER_KPP,
max(providers.SORT_PRIORITY) SORT_PRIORITY,
'allRegion' TYPE,
providers.IS_AUTOPAYMENT_SUPPORTED IS_AUTOPAYMENT_SUPPORTED,
providers.IS_AUTOPAYMENT_SUPPORTED_API IS_AUTOPAYMENT_SUPPORTED_API,
providers.IS_AUTOPAYMENT_SUPPORTED_ATM IS_AUTOPAYMENT_SUPPORTED_ATM
FROM
(
SELECT
ID,
provider.NAME NAME,
IMAGE_ID,
KPP,
ACCOUNT,
SORT_PRIORITY,
IS_AUTOPAYMENT_SUPPORTED,
IS_AUTOPAYMENT_SUPPORTED_API,
IS_AUTOPAYMENT_SUPPORTED_ATM
FROM SERVICE_PROVIDERS provider
WHERE
provider.STATE = 'ACTIVE'
AND
provider.AVAILABLE_PAYMENTS_FOR_IB = '1'
AND
(
provider.account_type in (:extra_accountType)
AND provider.INN = :extra_INN
AND provider.ACCOUNT = :extra_account
AND provider.BANK_CODE = :extra_BIC
)
) providers
LEFT JOIN IMAGES images ON providers.IMAGE_ID = images.ID
join SERV_PROVIDER_PAYMENT_SERV provider_payment on providers.ID = provider_payment.service_provider_id
left join PAYMENT_SERVICES services on provider_payment.PAYMENT_SERVICE_ID = services.id
left join PAYMENT_SERV_PARENTS parent2 on services.id = parent2.service_id
left join PAYMENT_SERVICES servGroup on parent2.parent_id = servGroup.id
left join PAYMENT_SERV_PARENTS parents on servGroup.id = parents.service_id
left join PAYMENT_SERVICES category on category.id = parents.parent_id
JOIN SERVICE_PROVIDER_REGIONS spr on providers.ID = spr.SERVICE_PROVIDER_ID JOIN REGIONS on regions.id = spr.REGION_ID

GROUP BY
regions.name,
category.id,
category.name,
servGroup.ID,
servGroup.NAME,
services.ID,
services.NAME,
providers.NAME,
images.MD5,
providers.KPP,
providers.IS_AUTOPAYMENT_SUPPORTED,
providers.IS_AUTOPAYMENT_SUPPORTED_API,
providers.IS_AUTOPAYMENT_SUPPORTED_ATM
ORDER BY REGION_NAME, SORT_PRIORITY desc, PROVIDER_NAME, CATEGORY_NAME, GROUP_NAME, SERVICES_NAME

