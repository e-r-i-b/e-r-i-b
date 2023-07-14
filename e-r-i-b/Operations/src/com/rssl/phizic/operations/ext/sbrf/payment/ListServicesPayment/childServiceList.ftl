with available_services as ( --услуги по доступным поставщикам
                             select sp2ps.PAYMENT_SERVICE_ID
                               from SERVICE_PROVIDERS providers 
                      <#if isRegion == "true"> left join SERVICE_PROVIDER_REGIONS spreg on spreg.SERVICE_PROVIDER_ID = providers.id </#if>
                      <#if invoiceProvider == "true">
                          JOIN AUTOPAY_SETTINGS sett on sett.RECIPIENT_ID = providers.ID
                      </#if>
                                  , SERV_PROVIDER_PAYMENT_SERV sp2ps
                              where providers.STATE = 'ACTIVE'
                              <#if invoiceProvider == "true">
                                  and providers.IS_AUTOPAYMENT_SUPPORTED = '1'
                                  and sett."TYPE" = 'INVOICE'
                                  and SUBSTR(providers.EXTERNAL_ID, INSTR(providers.EXTERNAL_ID, '|', -1) + 1) <> :extra_IQWaveUUID
                              </#if>
                                and providers.id = sp2ps.SERVICE_PROVIDER_ID
                              <#if onlyTemplateSupported == "true">
                                and providers.IS_TEMPLATE_SUPPORTED = 1
                              </#if>
                            -- ПОСТАВЩИКИ, В АДРЕС КОТОРЫХ ВОЗМОЖЕН АВТОПЛАТЕЖ
                            <#if isAutoPayProvider == "true">
                              <#if isInternetBank == "true">
                                and providers.IS_AUTOPAYMENT_SUPPORTED = 1
                                and providers.VISIBLE_AUTOPAYMENTS_FOR_IB = 1
                              <#elseif isATMApi == "true">
                                and providers.IS_AUTOPAYMENT_SUPPORTED_ATM = 1
                                and providers.VISIBLE_AUTOPAYMENTS_FOR_ATM = 1
                              <#elseif isMobileApi == "true">
                                and providers.IS_AUTOPAYMENT_SUPPORTED_API = 1
                                and providers.VISIBLE_AUTOPAYMENTS_FOR_API = 1
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
                            -- ПОСТАВЩИКИ МОБИЛЬНОГО БАНКА
                            <#elseif isMobilebank == "true">
                                and providers.IS_MOBILEBANK = 1
                                and EXISTS (SELECT 1 FROM FIELD_DESCRIPTIONS providerField
                                             WHERE providerField.RECIPIENT_ID = providers.ID
                                               AND providerField.IS_KEY = 1
                                             GROUP BY providers.ID
                                             HAVING COUNT(providers.ID) = 1)
                            --ИНАЧЕ
                            <#else>
                              <#if isInternetBank == "true">
                                AND providers.VISIBLE_PAYMENTS_FOR_IB = 1
                                AND providers.AVAILABLE_PAYMENTS_FOR_IB = 1
                              <#elseif isATMApi == "true">
                                AND providers.VISIBLE_PAYMENTS_FOR_ATM_API = 1
                                AND providers.AVAILABLE_PAYMENTS_FOR_ATM_API = 1
                              <#elseif isMobileApi == "true">
                                AND providers.VISIBLE_PAYMENTS_FOR_M_API = 1
                                AND providers.AVAILABLE_PAYMENTS_FOR_M_API = 1
                              </#if>
                            </#if>
                            <#if isRegion == "true">
                                and (providers.IS_FEDERAL = 1 or
                                     spreg.REGION_ID is null or
                                     spreg.REGION_ID in (select r.id from regions r
                                                         start with r.id = :extra_region_id
                                                         connect by r.parent_id = prior r.id) or
                                     spreg.REGION_ID in (select r.id from regions r
                                                         start with r.id = :extra_parent_region_id
                                                         connect by r.id = prior r.parent_id)
                                    )
                            </#if>
                             group by sp2ps.PAYMENT_SERVICE_ID )
select SERVICE_ID,
       SERVICE_NAME,
       SERVICE_IMAGE,
       SERVICE_IMAGE_NAME,
       SERVICE_DESCRIPTION,
       GUID
  from ( --доступные услуги с учетом поставщиков и иерархии подкатегориий услуг
         select distinct
                serv.ID SERVICE_ID,
                serv.NAME SERVICE_NAME,
                serv.IMAGE_ID SERVICE_IMAGE,
                serv.IMAGE_NAME SERVICE_IMAGE_NAME,
                serv.description SERVICE_DESCRIPTION,
                serv.PRIORITY,
                serv.code GUID
           from available_services avs
              , PAYMENT_SERVICES serv join payment_serv_parents psp on serv.id = psp.service_id and psp.parent_id = :extra_parentId
          where 1=1
          <#if isInternetBank == "true">
            and serv.SHOW_IN_SYSTEM=1
          <#elseif isATMApi == "true">
            and serv.SHOW_IN_ATM=1
          <#elseif isMobileApi == "true">
            and serv.SHOW_IN_API=1
          </#if>
            and avs.payment_service_id = serv.id
          )
order by PRIORITY, SERVICE_ID