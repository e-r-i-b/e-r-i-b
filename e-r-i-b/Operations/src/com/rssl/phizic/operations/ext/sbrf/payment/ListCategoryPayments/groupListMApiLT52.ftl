SELECT
    group_id,
    group_name,
    group_description,
    group_Image,
    group_ImageName,
    service_ID,
    service_name
FROM (
    SELECT
        servGroup.id group_id,
        servGroup.name group_name,
        servGroup.DESCRIPTION group_description,
        servGroup.IMAGE_ID group_Image,
        servGroup.IMAGE_NAME group_ImageName,
        serv.id service_ID,
        serv.name service_name,
        serv.priority priority,
        dense_rank() over(partition by serv.PARENT_ID order  by serv.name) row_num,
        dense_rank() over(order  by servGroup.id) group_num
    FROM
        PAYMENT_SERVICE_CATEGORIES  category JOIN
        (SELECT id, name, DESCRIPTION, IMAGE_ID, IMAGE_NAME FROM PAYMENT_SERVICES_OLD pso WHERE pso.PARENT_ID is null AND pso.SYSTEM = 0 AND pso.VISIBLE_IN_SYSTEM = 1) servGroup
            ON category.PAYMENT_SERVICES_ID = servGroup.id    AND category.category = :extra_categoryId
         JOIN PAYMENT_SERVICES_OLD serv ON  serv.parent_id = servGroup.id  AND serv.SYSTEM = 0 AND serv.VISIBLE_IN_SYSTEM = 1
    WHERE
    exists (
        SELECT providers.*
        FROM
            SERV_PROV_PAYM_SERV_OLD provider_payment JOIN SERVICE_PROVIDERS providers ON providers.id =  provider_payment.service_provider_id
            LEFT OUTER JOIN SERVICE_PROVIDER_REGIONS serviceProviderRegions ON serviceProviderRegions.SERVICE_PROVIDER_ID = providers.ID
        WHERE
            (PAYMENT_SERVICE_ID = serv.id or PAYMENT_SERVICE_ID = servGroup.id)
            AND
            providers.KIND <> 'T'

            AND
            providers.STATE = 'ACTIVE'

            AND
            not(providers.BILLING_ID is null)

            and providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
            and providers.VERSION_API <= :versionAPI

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
                    AND  (providers.IS_FEDERAL = 1
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
    )
    WHERE
        row_num <= 1
        and (group_num > :extra_first and group_num <= :extra_last)
ORDER BY group_id, priority, service_name ASC