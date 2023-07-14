SELECT
SERVICE_ID,
SERVICE_NAME,
SERVICE_DESC,
SERVICE_IMAGE,
SERVICE_IMAGE_NAME,
min(PROVIDERID) PROVIDER_ID,
PROVIDER_NAME,
max(PRIORITY) PROVIDER_SORT_PRIORITY
FROM
(SELECT
  services.ID SERVICE_ID,
  services.name SERVICE_NAME,
  services.description SERVICE_DESC,
  services.image_id SERVICE_IMAGE,
  services.image_name SERVICE_IMAGE_NAME,
  providers.id PROVIDERID,
  providers.name PROVIDER_NAME,
  providers.SORT_PRIORITY PRIORITY,
  providers.CODE_RECIPIENT_SBOL CODE_RECIPIENT_SBOL,
  dense_rank() over(partition by services.id order  by providers.CODE_RECIPIENT_SBOL) row_num,
  dense_rank() over(order  by services.name) serv_num
FROM
    PAYMENT_SERVICES_OLD services

    INNER JOIN SERV_PROV_PAYM_SERV_OLD prov_serv ON services.id = prov_serv.payment_service_id  and  services.VISIBLE_IN_SYSTEM = 1
    INNER JOIN SERVICE_PROVIDERS providers ON prov_serv.service_provider_id = providers.id
    <#if isRegion == "true">
        left join service_provider_regions spreg on providers.id = spreg.service_provider_id
    </#if>
WHERE
    services.parent_id = :extra_parentId AND services.system = 0

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

    and
    (providers.STATE = 'ACTIVE')

    and providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
    and providers.VERSION_API <= :versionAPI

     <#if isRegion == "true">
        AND  ( providers.IS_FEDERAL = 1 OR
                spreg.REGION_ID is null OR
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
    ORDER BY SERVICE_NAME
)
WHERE
    row_num <= 1
    and (serv_num > :extra_first and serv_num <= :extra_last)
GROUP BY  SERVICE_ID, SERVICE_NAME,  SERVICE_DESC, SERVICE_IMAGE,   SERVICE_IMAGE_NAME,  PROVIDER_NAME, CODE_RECIPIENT_SBOL
ORDER BY SERVICE_NAME, PROVIDER_SORT_PRIORITY desc, PROVIDER_NAME

