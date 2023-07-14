SELECT
    providers.id                            PROVIDER_ID,
    providers.name                          PROVIDER_NAME,
    providers.description                   DESCRIPTION,
    providers.image_id                      PROVIDER_IMAGE,
    providers.IS_AUTOPAYMENT_SUPPORTED      IS_AUTOPAYMENT_SUPPORTED
FROM
    SERVICE_PROVIDERS   providers
    <#if isRegion == "true">
        left join service_provider_regions spreg on providers.id = spreg.service_provider_id and providers.IS_FEDERAL = 0
    </#if>
WHERE
    (providers.STATE = 'ACTIVE')

    AND
    providers.PAYMENTCOUNT > 0

    <#if isInternetBank == "true">
		AND providers.VISIBLE_PAYMENTS_FOR_IB = 1
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

    AND EXISTS (SELECT prov_serv.service_provider_id
            FROM SERV_PROVIDER_PAYMENT_SERV prov_serv
            WHERE providers.id = prov_serv.service_provider_id)

    <#if isATMOnly == "true">
        AND providers.AVAILABLE_PAYMENTS_FOR_ATM_API = 1
        AND providers.VISIBLE_PAYMENTS_FOR_ATM_API = 1
    </#if>

    <#if isRegion == "true">
        AND ( spreg.REGION_ID is null OR
        providers.IS_FEDERAL = 1 OR
        spreg.REGION_ID in
            (select r.id from regions r
                start with r.id = :extra_region_id
                connect by r.parent_id = prior r.id )
            <#if isParentRegionId == "true">
            OR  spreg.REGION_ID in
                (select r.id from regions r
                start with r.id = :extra_parent_region_id
                connect by r.id = prior r.parent_id)
            </#if>
        )
    <#else>
        AND providers.IS_FEDERAL = 1
    </#if>
ORDER BY providers.PAYMENTCOUNT desc