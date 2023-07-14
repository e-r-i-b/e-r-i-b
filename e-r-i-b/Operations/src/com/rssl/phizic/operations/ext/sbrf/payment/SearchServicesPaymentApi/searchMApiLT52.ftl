SELECT
    providers.BILLING_ID billingId,

    providers.ID serviceId,
    providers.NAME_SERVICE serviceName,
    providers.IMAGE_ID serviceImageId,
    providerImages.UPDATE_TIME serviceImageDate,

    providers.ID providerId,
    providers.NAME providerName,
    providers.IMAGE_ID providerImageId,
    providerImages.UPDATE_TIME providerImageDate,

    case
        when providers.ACCOUNT_TYPE in ('CARD', 'ALL')
            and ( (:IQWaveAutoPaymentPermit = 1 and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) = :IQWaveUUID)
                or (:ESBAutoPaymentPermitSince51 = 1 and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :IQWaveUUID) )
        then providers.IS_AUTOPAYMENT_SUPPORTED
        else '0'
    end autoPaymentSupported,
    providers.IS_BAR_SUPPORTED barSupported,
    providers.INN inn,
    providers.ACCOUNT account,

    groups.ID groupId,
    groups.NAME groupName,
    groups.DESCRIPTION groupDescription,
    groups.IMAGE_ID groupImageId,
    groupImages.UPDATE_TIME groupImageDate,
    groups.IMAGE_NAME groupImageUrl,

    parents.ID parentId,
    parents.NAME parentName,
    parents.DESCRIPTION parentDescription,
    parents.IMAGE_ID parentImageId,
    parentImages.UPDATE_TIME parentImageDate,
    parents.IMAGE_NAME parentImageUrl
FROM
    SERVICE_PROVIDERS providers
    LEFT JOIN SERV_PROV_PAYM_SERV_OLD provider_payment ON provider_payment.SERVICE_PROVIDER_ID = providers.ID
    LEFT JOIN PAYMENT_SERVICES_OLD groups ON groups.ID = provider_payment.PAYMENT_SERVICE_ID AND groups.SYSTEM = 0 AND groups.VISIBLE_IN_SYSTEM = 1
    LEFT JOIN PAYMENT_SERVICES_OLD parents ON parents.ID = groups.PARENT_ID AND parents.SYSTEM = 0 AND parents.VISIBLE_IN_SYSTEM = 1
    LEFT JOIN IMAGES providerImages ON providerImages.ID = providers.IMAGE_ID
    LEFT JOIN IMAGES groupImages ON groupImages.ID = groups.IMAGE_ID
    LEFT JOIN IMAGES parentImages ON parentImages.ID = parents.IMAGE_ID
WHERE
    providers.KIND <> 'T'
    and providers.KIND <> 'I'
    AND providers.STATE = 'ACTIVE'
    AND providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
    AND providers.VERSION_API <= :versionAPI
    AND (:clientType <> 'CARD' OR (providers.ACCOUNT_TYPE = 'CARD' OR providers.ACCOUNT_TYPE = 'ALL'))
    AND providers.BILLING_ID is not null
    -- хоть каке-то переводы должны быть доступны
    AND (:cardProvidersAllowed = 1 or :accountProvidersAllowed = 1  or :extra_federalProvidersAllowed = 1)
    and (
            -- A. Либо поставщик поддерживает переводы со счёта
            ( (providers.ACCOUNT_TYPE in ('DEPOSIT', 'ALL')) and (:accountProvidersAllowed = 1) ) or
            -- B. Либо поставщик поддерживает карточные переводы
            ( (providers.ACCOUNT_TYPE in ('CARD', 'ALL')) and
              -- карточные переводы разрешены для любых поставщиков (1) либо только для федеральных (2)
              (:cardProvidersAllowed = 1 or (:cardProvidersAllowed = 2 and :extra_federalProvidersAllowed = 1 and providers.IS_FEDERAL = 1))
            )
        )
    -- указан список внешних ID, не содержащих услугу
    AND ( :externalIdCount=0 OR SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '@')+1) IN (:servicelessExternalIdList) )

    AND ( :extra_region_id is null
        OR providers.IS_FEDERAL = 1
        OR providers.ID in
           (
            select id from service_providers
            minus
            select service_provider_id from service_provider_regions
            union all
            select spr.service_provider_id
            from
                (select r.id from regions r
                    start with r.id = :extra_region_id
                    connect by r.parent_id = prior r.id
                union all
                select r.id from regions r where level>1
                    start with r.id = :extra_region_id
                    connect by r.id = prior r.parent_id)
                region_ids,
                service_provider_regions spr
            where spr.region_id = region_ids.id
            )
    )
    and
    (( not(:extra_search_long is null) and (providers.INN LIKE :extra_like_search_long or providers.ACCOUNT LIKE :extra_like_search_long) )
        or ( not(:extra_search is null) and (upper(providers.NAME) LIKE upper(:extra_like_search) or upper(groups.NAME) LIKE upper(:extra_like_search)
        or upper(providers.ALIAS) LIKE upper(:extra_like_search) or upper(providers.LEGAL_NAME)  LIKE upper(:extra_like_search)
        or upper(parents.NAME)  LIKE upper(:extra_like_search))
    ))
ORDER BY serviceId, providerName ASC
