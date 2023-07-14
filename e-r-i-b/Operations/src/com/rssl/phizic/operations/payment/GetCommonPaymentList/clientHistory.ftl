<#--
Опорный элемент: BUSINESS_DOCUMENTS_I_LOGIN
Предикаты доступа: access("DOCUMENT"."LOGIN_ID"=TO_NUMBER(:LOGINID) AND
               "DOCUMENT"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
               "DOCUMENT"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
Кардинальность: 10, 20, 50 (стоп условие - количество записей на странице. Пагинация)
-->
select document.ID as entityId, document.ID as documentId, document.DEPARTMENT_ID as departmentId, document.DOC_NUMBER as documentNumber, document.STATE_CODE as stateCode, document.STATE_DESCRIPTION as stateDescription, document.SYSTEM_NAME as systemName,
        document.COMMISSION as commission, document.COMMISSION_CURRENCY as commissionCurrency, document.ARCHIVE as archive, document.CREATION_TYPE as creationType, document.CLIENT_OPERATION_TYPE as clientOperationChannel,
        document.REFUSING_REASON as refusingReason, document.CREATION_DATE as dateCreated, document.OPERATION_DATE as operationDate, document.ADMISSION_DATE as admissionDate,
        document.EXECUTION_DATE as executionDate, document.DOCUMENT_DATE as documentDate, document.STATE_MACHINE_NAME as stateMachineName, document.TEMPLATE_ID as templateId,
        document.CREATION_SOURCE_TYPE as creationSourceType, document.CONFIRM_STRATEGY_TYPE as confirmStrategyType, document.OPERATION_UID as operationUID,
        document.SESSION_ID as sessionId, document.PROMO_CODE as promoCode, document.CREATED_EMPLOYEE_LOGIN_ID as createdEmployeeLoginId, document.CONFIRMED_EMPLOYEE_LOGIN_ID as confirmedEmployeeLoginId,
        document.CODE_ATM as codeATM, document.ADDITION_CONFIRM as additionalOperationChannel, document.LOGIN_TYPE as loginType,
        document.FORM_ID as formId, document.KIND as kind,
        document.EXTERNAL_ID as externalId, document.EXTERNAL_OFFICE_ID as externalOfficeId, document.EXTERNAL_OWNER_ID as externalOwnerId,
        document.PAYER_ACCOUNT as payerAccount, document.CONFIRM_EMPLOYEE as confirmEmployee,
        document.AMOUNT as chargeOffAmount, document.CURRENCY as chargeOffCurrency, document.DESTINATION_AMOUNT as destinationAmount, document.DESTINATION_CURRENCY as destinationCurrency, document.SUMM_TYPE as summType,
        document.GROUND as ground, document.RECEIVER_NAME as receiverName,
        document.IS_LONG_OFFER as longOffer, document.RECEIVER_ACCOUNT as receiverAccount,
        document.PROVIDER_EXTERNAL_ID as providerExternalId, document.RECIPIENT_ID as receiverInternalId,
        document.BILLING_DOCUMENT_NUMBER as billingDocumentNumber, document.EXTENDED_FIELDS as extendedFields,
        document.PAYER_ACCOUNT as chargeOffAccount, document.PAYER_NAME as payerName, document.LOGIN_ID as loginId, document.COUNT_ERROR as countError, document.ACCESS_AUTOPAY_SCHEMES as autoPaySchemeAsString,
        null as pfpId,
        null as pfpDate,
        null as pfpState,
        null as pfpEmployee,
        null as firId,
        null as firSum,
        null as firCard,
        null as firDate,
        null as claimId,
        document.CREATION_DATE sort_date
    from ( SELECT
            document.*
        FROM
            BUSINESS_DOCUMENTS  document  left join PAYMENTFORMS on document.FORM_ID = PAYMENTFORMS.id
    WHERE
           document.LOGIN_ID = :loginId
           AND (document.CREATION_DATE >= :extra_fromDate)
           AND (document.CREATION_DATE <= :extra_toDate)

           -- Фильтруем старые неподтвержденные документы
           AND (document.STATE_CODE not in ('INITIAL', 'DRAFT','SAVED') or :notConfirmDocumentsDate is null or document.CREATION_DATE > :notConfirmDocumentsDate)
           -- Фильтруем старые документы, ожидающие дополнительного подтверждения
           AND (document.STATE_CODE not in ('WAIT_CONFIRM') or :waitConfirmDocumentsDate is null or document.CREATION_DATE > :waitConfirmDocumentsDate)

           AND ((:extra_empty_formId = 1 and :extra_pfpSelected = 0 and :extra_firSelected = 0 and :extra_issueCardSelected = 0 and :extra_loanCardClaimSelected = 0 and :extra_extLoanClaimSelected = 0) OR
               (
                   document.FORM_ID IN (:extra_formId1,:extra_formId2,:extra_formId3,:extra_formId4,:extra_formId5,:extra_formId6,:extra_formId7,:extra_formId8,:extra_formId9,:extra_formId10)
                   AND document.IS_LONG_OFFER = DECODE(:extra_autoPayment, 'true', 1, 'false', 0, document.IS_LONG_OFFER)
               )
           )
           AND (:extra_creationType is null OR document.CREATION_TYPE = :extra_creationType)

           AND (:extra_account is null OR upper(document.PAYER_ACCOUNT)  = upper(:extra_account))

           AND (:extra_clientState is null
               OR (:extra_clientState = 'SAVED' AND document.STATE_CODE in ('SAVED', 'INITIAL', 'DRAFT', 'INITIAL_LONG_OFFER', 'OFFLINE_SAVED'))
               OR (:extra_clientState = 'SENDED' AND document.STATE_CODE in ('SENDED', 'DISPATCH', 'DISPATCHED', 'PARTLY_EXECUTED', 'UNKNOW', 'SENT', 'ERROR', 'ADOPTED'))
               OR (document.STATE_CODE = :extra_clientState)
           )

           AND document.STATE_CODE NOT IN ('DRAFTTEMPLATE', 'TEMPLATE', 'SAVED_TEMPLATE', 'DELETED','WAIT_CONFIRM_TEMPLATE')
           AND  not (document.KIND = 'Z' and  document.STATE_CODE = 'DRAFT')

           AND (:extra_fromAmount is null OR (document.AMOUNT >= :extra_fromAmount OR (document.AMOUNT is null AND document.DESTINATION_AMOUNT >= :extra_fromAmount)))
           AND (:extra_toAmount  is null OR (document.AMOUNT <= :extra_toAmount   OR (document.AMOUNT is null AND document.DESTINATION_AMOUNT <= :extra_toAmount)))
           AND (:extra_amountCurrency is null OR upper(document.currency) = upper(:extra_amountCurrency) OR upper(document.destination_currency) = upper(:extra_amountCurrency))

           AND document.KIND NOT IN ('W','U','V','X','CS','DS','RS','RI')
           AND document.CONFIRMED_EMPLOYEE_LOGIN_ID is null

           AND (:extra_receiverName is null or replace(document.receiver_account || document.PAYER_ACCOUNT || upper(document.RECEIVER_NAME) || upper(PAYMENTFORMS.DESCRIPTION), ' ') like upper(replace(:extra_like_receiverName, ' ')))

           AND (:extra_paymentStatus IS NULL
               OR (:extra_paymentStatus = 'ATM_STATES_BATCH' AND document.STATE_CODE in ('EXECUTED', 'REFUSED', 'RECALLED', 'ERROR', 'DELAYED_DISPATCH', 'DISPATCHED', 'WAIT_CONFIRM', 'UNKNOW', 'SENT', 'SUCCESSED', 'PARTLY_EXECUTED', 'ADOPTED', 'OFFLINE_DELAYED'))
               OR document.STATE_CODE = :extra_paymentStatus)

      ) document

UNION ALL

    <#--
    Опорный элемент: LOAN_CLAIMS_LICD_IDX
    Предикаты доступа: access("CLAIM"."OWNER_LOGIN_ID"=TO_NUMBER(:LOGINID) AND
    "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
    Кардинальность: 10, 20, 50 (стоп условие - количество записей на странице. Пагинация)
    -->

    select claim.ID as entityId, claim.ID as documentId, null as departmentId,  null as documentNumber, claim.STATE_CODE as stateCode, claim.STATE_DESCRIPTION as stateDescription, null as systemName,
            null as comission, null as commissionCurrency, claim.ARCHIVE as archive, claim.CREATE_CHANNEL as creationType, claim.CONFIRM_CHANNEL as clientOperationChannel,
            claim.REFUSING_REASON as refusingReason, claim.CREATION_DATE as dateCreated, claim.OPERATION_DATE as operationDate, claim.ADMISSION_DATE as admissionDate,
            claim.EXECUTION_DATE as executionDate, claim.DOCUMENT_DATE as documentDate, null as stateMachineName, null as templateId,
            claim.CREATION_SOURCE_TYPE as creationSourceType, claim.CONFIRM_STRATEGY_TYPE as confirmStrategyType, claim.OPERATION_UID as operationUID,
            null as sessionId, null as promoCode, claim.CREATE_MANAGER_LOGIN_ID as createdEmployeeLoginId, claim.CONFIRM_MANAGER_LOGIN_ID as confirmedEmployeeLoginId,
            null as codeATM, claim.ADDITION_CONFIRM as additionalOperationChannel, claim.OWNER_LOGIN_TYPE as loginType,
            null as formId, 'EL' as kind,
            claim.EXTERNAL_ID as externalId, null as externalOfficeId, null as externalOwnerId,
            null as payerAccount, null as confirmEmployee, null as chargeOffAmount, null as chargeOffCurrency,
            null as destinationAmount, null as destinationCurrency, null as summType,
            null as ground, null as receiverName,
            null as longOffer, null as receiverAccount, null as providerExternalId, null as receiverInternalId,
            null as billingDocumentNumber, null as extendedFields,
            null as chargeOffAccount, null as payerName, claim.OWNER_LOGIN_ID as loginId, null as countError, null as autoPaySchemeAsString,
            null as pfpId,
            null as pfpDate,
            null as pfpState,
            null as pfpEmployee,
            null as firId,
            null as firSum,
            null as firCard,
            null as firDate,
            null as claimId,
            claim.CREATION_DATE sort_date
        FROM ( SELECT
                claim.*
            FROM
                LOAN_CLAIMS  claim

        WHERE
            claim.OWNER_LOGIN_ID = :loginId
            AND (claim.CREATION_DATE >= :extra_fromDate)
            AND (claim.CREATION_DATE <= :extra_toDate)

            -- Фильтруем старые неподтвержденные заявки
            AND (claim.STATE_CODE not in ('INITIAL', 'DRAFT','SAVED') or :notConfirmDocumentsDate is null or claim.CREATION_DATE > :notConfirmDocumentsDate)
            -- Фильтруем старые заявки, ожидающие дополнительного подтверждения
            AND (claim.STATE_CODE not in ('WAIT_CONFIRM') or :waitConfirmDocumentsDate is null or claim.CREATION_DATE > :waitConfirmDocumentsDate)

            AND (:extra_creationType is null OR claim.CREATE_CHANNEL = :extra_creationType)

            AND (:extra_clientState is null
                OR (:extra_clientState = 'SAVED' AND claim.STATE_CODE in ('SAVED', 'INITIAL', 'DRAFT', 'INITIAL_LONG_OFFER', 'OFFLINE_SAVED'))
                OR (:extra_clientState = 'SENDED' AND claim.STATE_CODE in ('SENDED', 'DISPATCH', 'DISPATCHED', 'PARTLY_EXECUTED', 'UNKNOW', 'SENT', 'ERROR', 'ADOPTED'))
                OR (claim.STATE_CODE = :extra_clientState)
            )

            AND ((:extra_empty_formId = 1 and :extra_pfpSelected = 0 and :extra_firSelected = 0 and :extra_issueCardSelected = 0 and :extra_loanCardClaimSelected = 0) OR :extra_extLoanClaimSelected = 1)

            AND claim.STATE_CODE NOT IN ('DRAFTTEMPLATE', 'TEMPLATE', 'SAVED_TEMPLATE', 'DELETED','WAIT_CONFIRM_TEMPLATE')

            AND claim.CONFIRM_MANAGER_LOGIN_ID is null

            AND (:extra_paymentStatus IS NULL OR claim.STATE_CODE = :extra_paymentStatus)
          ) claim
UNION ALL
    <#--
    Опорный элемент: LOAN_CCLAIMS_LICD_IDX
    Предикаты доступа:
    access("LOANCARDCLAIM"."OWNER_LOGIN_ID"=TO_NUMBER(:LOGINID) AND "
           "LOANCARDCLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "
           "LOANCARDCLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))"

    Кардинальность: 10, 20, 50 (стоп условие - количество записей на странице. Пагинация)
    -->
    select loanCardClaim.ID as entityId,
           loanCardClaim.ID as documentId,
           null as departmentId,
           null as documentNumber,
           loanCardClaim.STATE_CODE as stateCode,
           loanCardClaim.STATE_DESCRIPTION as stateDescription,
           null as systemName,
           null as comission,
           null as commissionCurrency,
           loanCardClaim.ARCHIVE as archive,
           loanCardClaim.CREATE_CHANNEL as creationType,
           loanCardClaim.CONFIRM_CHANNEL as clientOperationChannel,
           loanCardClaim.REFUSING_REASON as refusingReason,
           loanCardClaim.CREATION_DATE as dateCreated,
           loanCardClaim.OPERATION_DATE as operationDate,
           loanCardClaim.ADMISSION_DATE as admissionDate,
           loanCardClaim.EXECUTION_DATE as executionDate,
           loanCardClaim.DOCUMENT_DATE as documentDate,
           null as stateMachineName,
           null as templateId,
           loanCardClaim.CREATION_SOURCE_TYPE as creationSourceType,
           loanCardClaim.CONFIRM_STRATEGY_TYPE as confirmStrategyType,
           loanCardClaim.OPERATION_UID as operationUID,
           null as sessionId,
           null as promoCode,
           null as createdEmployeeLoginId,
           null as confirmedEmployeeLoginId,
           null as codeATM,
           loanCardClaim.ADDITION_CONFIRM as additionalOperationChannel,
           loanCardClaim.OWNER_LOGIN_TYPE as loginType,
           null as formId,
           'LCC' as kind,
           loanCardClaim.EXTERNAL_ID as externalId,
           null as externalOfficeId,
           null as externalOwnerId,
           null as payerAccount,
           null as confirmEmployee,
           null as chargeOffAmount,
           null as chargeOffCurrency,
           null as destinationAmount,
           null as destinationCurrency,
           null as summType,
           null as ground,
           null as receiverName,
           null as longOffer,
           null as receiverAccount,
           null as providerExternalId,
           null as receiverInternalId,
           null as billingDocumentNumber,
           null as extendedFields,
           null as chargeOffAccount,
           null as payerName,
           loanCardClaim.OWNER_LOGIN_ID as loginId,
           null as countError,
           null as autoPaySchemeAsString,
           null as pfpId,
           null as pfpDate,
           null as pfpState,
           null as pfpEmployee,
           null as firId,
           null as firSum,
           null as firCard,
           null as firDate,
           null as claimId,
           loanCardClaim.CREATION_DATE sort_date
        FROM ( SELECT
                loanCardClaim.*
            FROM
                LOAN_CARD_CLAIMS loanCardClaim

        WHERE
            loanCardClaim.OWNER_LOGIN_ID = :loginId
            AND (loanCardClaim.CREATION_DATE >= :extra_fromDate)
            AND (loanCardClaim.CREATION_DATE <= :extra_toDate)
            AND (:extra_creationType is null OR loanCardClaim.CREATE_CHANNEL = :extra_creationType)
            AND (:extra_clientState is null
                OR (:extra_clientState = 'SENDED' AND loanCardClaim.STATE_CODE IN ('DISPATCHED', 'ADOPTED'))
                OR (loanCardClaim.STATE_CODE = :extra_clientState)
            )

            AND ((:extra_empty_formId = 1 and :extra_pfpSelected = 0 and :extra_firSelected = 0 and :extra_issueCardSelected = 0 and :extra_extLoanClaimSelected = 0) OR :extra_loanCardClaimSelected = 1)

            AND loanCardClaim.STATE_CODE <> 'DELETED'
            AND (:extra_paymentStatus IS NULL OR loanCardClaim.STATE_CODE = :extra_paymentStatus)
          ) loanCardClaim
<#if showPfp>
    UNION ALL
    <#--
    Опорный элемент: DXFK_FINANCE_PROFILE_TO_LOGINS
    Предикаты доступа: access("PFP"."LOGIN_ID"=TO_NUMBER(:LOGINID))
    Кардинальность: 10, 20, 50 (стоп условие - количество записей на странице. Пагинация)
    -->
    SELECT
        pfp.ID as entityId, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null,
        pfp.ID as pfpId,
        pfp.CREATION_DATE as pfpDate,
        pfp.STATE_CODE as pfpState,
        pfp.EMPLOYEE_FIO as pfpEmployee,
        null as firId,
        null as firSum,
        null as firCard,
        null as firDate,
        null as claimId,
        pfp.CREATION_DATE sort_date
    FROM
        PERSONAL_FINANCE_PROFILE pfp
    WHERE
        pfp.LOGIN_ID = :loginId
        AND (pfp.CREATION_DATE >= :extra_fromDate)
        AND (pfp.CREATION_DATE <= :extra_toDate)

        AND (:extra_clientState is null
            OR (:extra_clientState = 'NOT_COMPLITE' AND pfp.STATE_CODE not in ('COMPLITE', 'COMPLITE_EMPLOYEE'))
            OR (:extra_clientState = 'COMPLITE' AND pfp.STATE_CODE in ('COMPLITE', 'COMPLITE_EMPLOYEE'))
            OR (:extra_clientState not in ('NOT_COMPLITE', 'COMPLITE') AND pfp.STATE_CODE = :extra_clientState)
        )
        AND (:extra_receiverName is null or upper('Проведение финансового планирования') like upper(replace(:extra_like_receiverName, ' ')))
        AND  :extra_extLoanClaimSelected != 1
        AND  :extra_loanCardClaimSelected != 1
</#if>
<#if showFir>
    UNION ALL
    <#--
    Опорный элемент: I_FUND_I_REQUESTS_LOGIN
    Предикаты доступа: access("FIR"."LOGIN_ID"=TO_NUMBER(:LOGINID) AND
                       "FIR"."CREATED_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
                       "FIR"."CREATED_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
    Кардинальность: 10, 20, 50 (стоп условие - количество записей на странице. Пагинация)
    -->
    SELECT
        fir.ID as entityId, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null,
        null as pfpId,
        null as pfpDate,
        null as pfpState,
        null as pfpEmployee,
        fir.ID as firId,
        fir.REQUIRED_SUM as firSum,
        fir.TO_RESOURCE as firCard,
        fir.CREATED_DATE as firDate,
        null as claimId,
        fir.CREATED_DATE sort_date
    FROM
        FUND_INITIATOR_REQUESTS fir
    WHERE
        fir.LOGIN_ID = :loginId
        AND (:extra_fromDate <= fir.CREATED_DATE) AND (fir.CREATED_DATE <= :extra_toDate)

        AND (fir.REQUIRED_SUM is null
            OR ((:extra_fromAmount is null OR :extra_fromAmount <= fir.REQUIRED_SUM)  AND (:extra_toAmount is null OR fir.REQUIRED_SUM <= :extra_toAmount))
        )
        AND (:extra_receiverName is null
            OR ('ЗАПРОСНАСБОРСРЕДСТВ' like upper(replace(:extra_like_receiverName, ' ')))
            OR (upper(replace(fir.TO_RESOURCE, ' ')) like upper(replace(:extra_like_receiverName, ' ')))
        )
        AND  :extra_extLoanClaimSelected != 1
        AND  :extra_loanCardClaimSelected != 1
</#if>
<#if showIssueCard>
    UNION ALL
    <#--
    Опорный объект:  IDX_ISSUE_CARD_OWNER_DATE
    Предикаты доступа: ("ISSUECARD"."OWNER_ID"=TO_NUMBER(:LOGINID) AND "ISSUECARD"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE)
                  AND "ISSUECARD"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
    Кардинальность: 10, 20, 50 (стоп условие - количество записей на странице. Пагинация)
    Вызывается при просмотре истории операций.

    -->
    SELECT
        issueCard.ID as entityId, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null,
        null as pfpId,
        issueCard.CREATION_DATE as pfpDate,
        issueCard.STATUS as pfpState,
        issueCard.ALL_CARD_NAMES as pfpEmployee,
        null as firId,
        null as firSum,
        null as firCard,
        null as firDate,
		issueCard.ID as claimId,
		issueCard.CREATION_DATE sort_date
    FROM
		ISSUE_CARD_CLAIM issueCard

    WHERE
		issueCard.OWNER_ID = :loginId
		AND issueCard.IS_GUEST = '0'
        AND (issueCard.CREATION_DATE >= :extra_fromDate)
        AND (issueCard.CREATION_DATE <= :extra_toDate)

        AND (:extra_clientState is null
            OR (:extra_clientState = 'NOT_COMPLITE' AND issueCard.STATUS = 'ERROR')
            OR (:extra_clientState in ('COMPLITE', 'EXECUTED') AND issueCard.STATUS = 'EXECUTED')
            OR (:extra_clientState not in ('NOT_COMPLITE', 'COMPLITE', 'EXECUTED') AND issueCard.STATUS = :extra_clientState)
        )
        AND (:extra_receiverName is null or upper('Заявка Сбербанк на каждый день') like upper(replace(:extra_like_receiverName, ' ')))
        AND  :extra_extLoanClaimSelected != 1
        AND  :extra_loanCardClaimSelected != 1
</#if>
order by sort_date DESC
<#--КОММЕНТАРИЙ ДЛЯ РАЗРАБОТЧИКА: ЕСЛИ ЗАПРОС ИЗМЕНИЛСЯ, ПРОВЕРЬ А НЕ ОТЛОМАЛ ЛИ ТЫ ИСТОРИЮ ОПЕРАЦИЙ В MAPI, ATM и socialAPI-->