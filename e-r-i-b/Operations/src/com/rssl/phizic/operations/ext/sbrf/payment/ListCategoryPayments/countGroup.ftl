SELECT
count(distinct servGroup.id) count
FROM
PAYMENT_SERVICE_CATEGORIES  category JOIN
(SELECT id FROM PAYMENT_SERVICES WHERE PAYMENT_SERVICES.PARENT_ID is null AND PAYMENT_SERVICES.SYSTEM = 0 AND PAYMENT_SERVICES.VISIBLE_IN_SYSTEM = 1) servGroup
    ON category.PAYMENT_SERVICES_ID = servGroup.id    AND category.category = :extra_categoryId
 JOIN PAYMENT_SERVICES serv ON  serv.parent_id = servGroup.id  AND serv.SYSTEM = 0 AND serv.VISIBLE_IN_SYSTEM = 1
WHERE
exists (
SELECT providers.*
FROM
    SERV_PROVIDER_PAYMENT_SERV provider_payment JOIN SERVICE_PROVIDERS providers ON providers.id =  provider_payment.service_provider_id
    LEFT OUTER JOIN SERVICE_PROVIDER_REGIONS serviceProviderRegions ON serviceProviderRegions.SERVICE_PROVIDER_ID = providers.ID
WHERE
    (PAYMENT_SERVICE_ID = serv.id or PAYMENT_SERVICE_ID = servGroup.id)
	<#if isInternetBank == "true" && isMobilebank != "true">
        AND providers.VISIBLE_PAYMENTS_FOR_IB = 1
    </#if>
    AND
    providers.KIND <> 'T'

    AND
    providers.STATE = 'ACTIVE'

    AND not(providers.BILLING_ID is null)

    <#if isMobilebank == "true">
        AND (providers.ID in  (
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

    <#if isMobileApi == "true">
        and providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
        and providers.VERSION_API <= :versionAPI
    </#if>

    <#if isATMApi == "true">
        and providers.AVAILABLE_PAYMENTS_FOR_ATM_API = 1
        and providers.VISIBLE_PAYMENTS_FOR_ATM_API = 1
    </#if>

    <#if isAutoPayProvider == "true">
        -- фильтра на поставщиков, в адрес которых можно создать шинный автоплатеж
        AND (
                -- доступен автоплатеж
                <#if isMobileApi == "true">
                    providers.IS_AUTOPAYMENT_SUPPORTED_API = 1
                <#elseif isATMApi == "true">
                    providers.IS_AUTOPAYMENT_SUPPORTED_ATM = 1
                <#elseif isInternetBank == "true">
                    providers.IS_AUTOPAYMENT_SUPPORTED = 1
                </#if>
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

    AND
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

        -- указан список внешних ID, не содержащих услугу
        AND not(providers.BILLING_ID is null)
        AND ( :externalIdCount=0 OR
                SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '@')+1) IN (:servicelessExternalIdList)
                )
        <#if isRegion == "true">
            AND (:extra_region_id is  NULL
                    OR providers.IS_FEDERAL = 1
                    OR serviceProviderRegions.REGION_ID is NULL
                    OR serviceProviderRegions.REGION_ID in
                       (select ID from REGIONS regions
                            START WITH regions.ID = :extra_region_id
                            CONNECT BY regions.PARENT_ID = PRIOR regions.ID)
                    <#if isParentRegionId == "true">
                    OR serviceProviderRegions.REGION_ID in
                       (select ID from REGIONS regions
                            START WITH regions.ID = :extra_parent_region_id
                            CONNECT BY regions.ID = PRIOR regions.PARENT_ID)
                    </#if>
                    )
        </#if>
)