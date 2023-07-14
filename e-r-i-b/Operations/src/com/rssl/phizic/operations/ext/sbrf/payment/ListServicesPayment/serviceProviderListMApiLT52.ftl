SELECT
    min(providers.id) PROVIDER_ID,
    providers.name PROVIDER_NAME,
    max(providers.image_id) PROVIDER_IMAGE,
    providers.tip_Of_Provider TIP_OF_PROVIDER,
    providers.code_recipient_sbol CODE_RECIPIENT_SBOL,
    providers.billing_id BILLING_ID,
    max(providers.SORT_PRIORITY) SORT_PRIORITY,
    max(
        case when
            providers.IS_AUTOPAYMENT_SUPPORTED = 1
            and providers.ACCOUNT_TYPE in ('CARD', 'ALL')
            <#if isIQWaveAutoPaymentPermit == "false">
                and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :IQWaveUUID
            <#elseif isESBAutoPaymentPermit == "false">
                and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) = :IQWaveUUID
            </#if>
        then '1'
        else '0' END
    ) IS_AUTOPAYMENT_SUPPORTED
FROM
    SERVICE_PROVIDERS providers
    LEFT JOIN IMAGES images on providers.IMAGE_ID = images.ID
    <#if isRegion == "true">
        left join service_provider_regions spreg on providers.id = spreg.service_provider_id
    </#if>
WHERE
     <#if onlyTemplateSupported == "true">
        providers.IS_TEMPLATE_SUPPORTED = 1 AND
     </#if>
    (providers.STATE = 'ACTIVE')

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

    and providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
    and providers.VERSION_API <= :versionAPI    

    AND EXISTS (SELECT prov_serv.service_provider_id
            FROM SERV_PROV_PAYM_SERV_OLD prov_serv
            WHERE providers.id = prov_serv.service_provider_id
            AND prov_serv.payment_service_id = :extra_serviceId)

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
    </#if>
    GROUP BY
        providers.name,
        images.MD5,
        providers.tip_Of_Provider,
        providers.code_recipient_sbol,
        providers.billing_id
    ORDER BY SORT_PRIORITY desc, PROVIDER_NAME