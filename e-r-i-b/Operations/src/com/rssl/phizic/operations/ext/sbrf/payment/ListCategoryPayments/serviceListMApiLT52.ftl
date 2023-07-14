SELECT
    GROUP_ID,
    GROUP_NAME,
    GROUP_DESCRIPTION,
    IMAGE_ID,
    group_ImageName,
    min(PROVIDERID) PROVIDER_ID,
    PROVIDER_NAME,
    max(PRIORITY) SORT_PRIORITY,
    CODE_RECIPIENT_SBOL
FROM (
    SELECT
        servGroup.ID GROUP_ID,
        servGroup.NAME GROUP_NAME,
        servGroup.DESCRIPTION GROUP_DESCRIPTION,
        servGroup.IMAGE_ID IMAGE_ID,
        servGroup.IMAGE_NAME group_ImageName,
        providers.ID PROVIDERID,
        providers.NAME PROVIDER_NAME,
        providers.SORT_PRIORITY PRIORITY,
        providers.CODE_RECIPIENT_SBOL CODE_RECIPIENT_SBOL,
        dense_rank() over(partition by servGroup.ID order  by providers.CODE_RECIPIENT_SBOL) row_num,
        dense_rank() over(order  by servGroup.id) group_num
    FROM
        (SELECT p.ID, p.NAME, p.DESCRIPTION, p.IMAGE_ID,p.PRIORITY, p.IMAGE_NAME
                FROM PAYMENT_SERVICES_OLD p,
                PAYMENT_SERVICE_CATEGORIES  category
        WHERE p.PARENT_ID is null
                AND p.SYSTEM = 0
                AND p.VISIBLE_IN_SYSTEM = 1
                AND category.PAYMENT_SERVICES_ID = p.id
                AND category.category = :extra_categoryId) servGroup
        INNER JOIN SERV_PROV_PAYM_SERV_OLD provider_payment on provider_payment.PAYMENT_SERVICE_ID = servGroup.ID
        INNER JOIN SERVICE_PROVIDERS providers ON provider_payment.SERVICE_PROVIDER_ID  = providers.ID
        <#if isRegion == "true">
            left join service_provider_regions spreg on providers.id = spreg.service_provider_id
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

        and providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
        and providers.VERSION_API <= :versionAPI

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
)
WHERE
    row_num <= 1
    and (group_num > :extra_first and group_num <= :extra_last)
GROUP BY  GROUP_ID, GROUP_NAME,  GROUP_DESCRIPTION,   IMAGE_ID,  group_ImageName,  PROVIDER_NAME, CODE_RECIPIENT_SBOL
ORDER BY GROUP_ID, SORT_PRIORITY desc, PROVIDER_NAME