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
    ) IS_AUTOPAYMENT_SUPPORTED,
    max(providers.uuid) GUID
FROM
    SERVICE_PROVIDERS providers
    LEFT JOIN IMAGES images on providers.IMAGE_ID = images.ID
    <#if isRegion == "true">
        left join service_provider_regions spreg on providers.id = spreg.service_provider_id
    </#if>
    <#if invoiceProvider == "true">
        JOIN AUTOPAY_SETTINGS sett on sett.RECIPIENT_ID = providers.ID
     </#if>
WHERE
     <#if onlyTemplateSupported == "true">
        providers.IS_TEMPLATE_SUPPORTED = 1 AND
     </#if>
    (providers.STATE = 'ACTIVE')
    <#if invoiceProvider == "true">
        and providers.IS_AUTOPAYMENT_SUPPORTED = '1'
        and sett."TYPE" = 'INVOICE'
        and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :extra_IQWaveUUID
    </#if>
    AND
    (
        (providers.ACCOUNT_TYPE in
            -- ���� �����-�� �������� ������ ���� ��������
            ('ALL'
            --��������� ������������ ��������� ��������
            <#if isCardAccountType == "true" || clientType == "CARD">
                , 'CARD'
            </#if>
            --��������� ������������ �������� �� �����
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

    <#if isAutoPayProvider == "true">
        -- ������� �� �����������, � ����� ������� ����� ������� ����������
        AND (
                -- �������� ����������
                <#if isInternetBank == "true">
                    providers.IS_AUTOPAYMENT_SUPPORTED = 1
                    AND providers.VISIBLE_AUTOPAYMENTS_FOR_IB = 1
                <#elseif isMobileApi == "true">
                    providers.IS_AUTOPAYMENT_SUPPORTED_API = 1
                    AND providers.VISIBLE_AUTOPAYMENTS_FOR_API = 1
                <#elseif isATMApi == "true">
                    providers.IS_AUTOPAYMENT_SUPPORTED_ATM = 1
                    AND providers.VISIBLE_AUTOPAYMENTS_FOR_ATM = 1
                </#if>
                -- ��������� ���������
                and providers.ACCOUNT_TYPE in ('CARD', 'ALL')
                <#if isIQWaveAutoPaymentPermit == "false">
                    -- �� iqwave
                    and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :IQWaveUUID
                <#elseif isESBAutoPaymentPermit == "false">
                    -- �� ����� ����.
                    and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) = :IQWaveUUID
                </#if>
            )
    <#elseif isMobilebank == "true">
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
    <#else>
        <#if isInternetBank == "true">
            AND providers.VISIBLE_PAYMENTS_FOR_IB = 1
            AND providers.AVAILABLE_PAYMENTS_FOR_IB = 1
        <#elseif isMobileApi == "true">
            and providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
            and providers.VISIBLE_PAYMENTS_FOR_M_API = 1
            and providers.VERSION_API <= :versionAPI
        <#elseif isATMApi == "true">
            and providers.AVAILABLE_PAYMENTS_FOR_ATM_API = 1
            and providers.VISIBLE_PAYMENTS_FOR_ATM_API = 1
        </#if>
    </#if>

    AND EXISTS (SELECT prov_serv.service_provider_id
            FROM SERV_PROVIDER_PAYMENT_SERV prov_serv
               , PAYMENT_SERVICES serv
            WHERE providers.id = prov_serv.service_provider_id
              and serv.id = prov_serv.payment_service_id
          --��������� ��������� ������ � �������
          <#if isInternetBank == "true">
              and serv.SHOW_IN_SYSTEM=1
          <#elseif isATMApi == "true">
              and serv.SHOW_IN_ATM=1
          <#elseif isMobileApi == "true">
              and serv.SHOW_IN_API=1
          </#if>

        <#if getFinalDescendants>
            AND (prov_serv.payment_service_id = :extra_serviceId
                or prov_serv.payment_service_id in (
                    select SERVICE_ID
                    from PAYMENT_SERV_PARENTS
                    start with PARENT_ID = :extra_serviceId
                    connect by nocycle prior SERVICE_ID = PARENT_ID
                )))
        <#else>
            AND prov_serv.payment_service_id = :extra_serviceId)
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
    </#if>
    GROUP BY
        providers.name,
        images.MD5,
        providers.tip_Of_Provider,
        providers.code_recipient_sbol,
        providers.billing_id
    ORDER BY SORT_PRIORITY desc, PROVIDER_NAME