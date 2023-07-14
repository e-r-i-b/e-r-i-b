SELECT
    paymentDocuments.ID id,
    paymentDocuments.CLIENT_CREATION_DATE creationDate,
    paymentDocuments.CLIENT_CREATION_CHANNEL createChannelType,
    paymentDocuments.DOCUMENT_NUMBER templateNumber,
    paymentDocuments.TEMPLATE_NAME templateName,
    paymentDocuments.CLIENT_OPERATION_DATE confirmDate,
    paymentDocuments.CLIENT_OPERATION_CHANNEL confirmChannelType,
    paymentDocuments.EMPLOYEE_OPERATION_DATE stateChangeDate,
    paymentDocuments.FORM_TYPE formType,
    paymentDocuments.STATE_CODE status,
    paymentDocuments.CHARGEOFF_RESOURCE chargeOffResource,
    paymentDocuments.DESTINATION_RESOURCE destinationResource,
        case when paymentDocuments.SUMM_TYPE = 'charge-off-field-exact' then paymentDocuments.CHARGEOFF_AMOUNT
            else paymentDocuments.DESTINATION_AMOUNT end amount,
        case when paymentDocuments.SUMM_TYPE = 'charge-off-field-exact' then paymentDocuments.CHARGEOFF_CURRENCY
            else paymentDocuments.DESTINATION_CURRENCY end amountCurrency,
    paymentDocuments.RECEIVER_NAME receiverName,
    paymentDocuments.CONFIRMED_EMPLOYEE_FULL_NAME employeeName,
    paymentDocuments.TEMPLATE_STATE_CODE templateState,
    (select unique extendedFields.VALUE from PAYMENTS_DOCUMENTS_EXT extendedFields where paymentDocuments.ID = extendedFields.PAYMENT_ID AND extendedFields.NAME = 'fromResourceType') fromResourceType,
    case when paymentDocuments.FORM_TYPE = 'INDIVIDUAL_TRANSFER'
        then (case when (select unique extendedFields.VALUE from PAYMENTS_DOCUMENTS_EXT extendedFields where paymentDocuments.ID = extendedFields.PAYMENT_ID AND extendedFields.NAME = 'externalCard') = 'true' then 'com.rssl.phizic.business.resources.external.CardLink' else 'com.rssl.phizic.business.resources.external.AccountLink' end)
        else (select unique extendedFields.VALUE from PAYMENTS_DOCUMENTS_EXT extendedFields where paymentDocuments.ID = extendedFields.PAYMENT_ID AND extendedFields.NAME = 'toResourceType') end toResourceType
FROM
    PAYMENTS_DOCUMENTS paymentDocuments
WHERE
        paymentDocuments.CLIENT_GUID = :extra_clientGUID

        <#if creationDateFrom?has_content>
            AND paymentDocuments.CLIENT_CREATION_DATE >= :extra_creationDateFrom
        </#if>
        <#if creationDateTo?has_content>
            AND paymentDocuments.CLIENT_CREATION_DATE <= :extra_creationDateTo
        </#if>
        <#if confirmDateFrom?has_content>
            AND paymentDocuments.CLIENT_OPERATION_DATE >= :extra_confirmDateFrom
        </#if>
        <#if confirmDateTo?has_content>
            AND paymentDocuments.CLIENT_OPERATION_DATE <= :extra_confirmDateTo
        </#if>
        <#if stateChangeDateFrom?has_content>
            AND paymentDocuments.EMPLOYEE_OPERATION_DATE >= :extra_stateChangeDateFrom
        </#if>
        <#if stateChangeDateTo?has_content>
            AND paymentDocuments.EMPLOYEE_OPERATION_DATE <= :extra_stateChangeDateTo
        </#if>
        <#if receiverName?has_content>
            AND upper(paymentDocuments.RECEIVER_NAME) like upper(:extra_like_receiverName)
        </#if>
        <#if templateName?has_content>
            AND upper(paymentDocuments.TEMPLATE_NAME) like upper(:extra_like_templateName)
        </#if>
        <#if templateNumber?has_content>
            AND paymentDocuments.DOCUMENT_NUMBER = :extra_templateNumber
        </#if>
        <#if createChannel?has_content>
            AND paymentDocuments.CLIENT_CREATION_CHANNEL = :extra_createChannel
        </#if>
        <#if confirmChannel?has_content>
            AND paymentDocuments.CLIENT_OPERATION_CHANNEL = :extra_confirmChannel
        </#if>
        <#if employeeStateChange?has_content>
            AND upper(replace(paymentDocuments.CONFIRMED_EMPLOYEE_FULL_NAME,' ','')) like upper(replace(:extra_like_employeeStateChange,' ',''))
        </#if>
        <#if amountFrom?has_content>
            AND ((paymentDocuments.CHARGEOFF_AMOUNT >= :extra_amountFrom AND paymentDocuments.SUMM_TYPE = 'charge-off-field-exact')
                OR (paymentDocuments.DESTINATION_AMOUNT >= :extra_amountFrom AND paymentDocuments.SUMM_TYPE = 'destination-field-exact')
                )
        </#if>
        <#if amountTo?has_content>
            AND ((paymentDocuments.CHARGEOFF_AMOUNT <= :extra_amountTo AND paymentDocuments.SUMM_TYPE = 'charge-off-field-exact')
                OR (paymentDocuments.DESTINATION_AMOUNT <= :extra_amountTo AND paymentDocuments.SUMM_TYPE = 'destination-field-exact')
                )
        </#if>
        <#if formType?has_content>
            AND paymentDocuments.FORM_TYPE in (:extra_formType)
        </#if>
        <#if status?has_content>
            AND paymentDocuments.STATE_CODE in (:extra_status)
        </#if>
        <#if showDeleted == "false">
            AND paymentDocuments.TEMPLATE_STATE_CODE != 'REMOVED'
        </#if>
ORDER BY paymentDocuments.CLIENT_CREATION_DATE desc


        

        





        

        




        
