SELECT
    prov.ID PROVIDER_ID,
    prov.NAME PROVIDER_NAME,
    prov.INN PROVIDER_INN,
    prov.ACCOUNT PROVIDER_ACCOUNT,
    prov.IMAGE_ID IMAGE_ID,
    prov.SORT_PRIORITY SORT_PRIORITY
FROM
   SERVICE_PROVIDERS prov
WHERE prov.ID in (
    SELECT
        min(providers.ID)
    FROM
        SERVICE_PROVIDERS providers
         <#if isRegion == "true">
            LEFT JOIN service_provider_regions spreg on providers.id = spreg.service_provider_id
         </#if>
    WHERE
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

        <#if isRegion == "true">
            AND ( spreg.REGION_ID is null OR
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

        and
        (( not(:extra_search_long is null) and (providers.INN LIKE :extra_like_search_long or providers.ACCOUNT LIKE :extra_like_search_long) )
            or ( not(:extra_search is null)
                    and (
                        upper(providers.NAME) LIKE upper(:extra_like_search)
                        or upper(providers.ALIAS) LIKE upper(:extra_like_search)
                        or upper(providers.LEGAL_NAME)  LIKE upper(:extra_like_search)
                    ))
            or ((:extra_search is null) and (:extra_search_long is null))
        )
        <#-- поставщики должды быть сгруппированы по CODERECIPIENTSBOL + CODEBS,
        CODEBS уникален поэтому группировать можно по BILLING_ID-->
        GROUP BY providers.CODE_RECIPIENT_SBOL, providers.BILLING_ID)

ORDER BY SORT_PRIORITY desc, prov.NAME