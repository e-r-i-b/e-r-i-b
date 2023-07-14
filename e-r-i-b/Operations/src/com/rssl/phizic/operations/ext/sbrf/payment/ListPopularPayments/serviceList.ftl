SELECT
    services.id                            SERVICE_ID,
    services.name                          SERVICE_NAME,
    services.description                   SERVICE_DESCRIPTION,
    services.image_id                      SERVICE_IMAGE

FROM
    PAYMENT_SERVICES services

WHERE
    services.POPULAR = 1

    <#if isATMApi == "true">
        AND services.SHOW_IN_ATM = 1
    </#if>

ORDER BY
    services.PRIORITY,
    services.NAME