SELECT
    document.ID as documentId, document.DEPARTMENT_ID as departmentId, document.DOC_NUMBER as documentNumber, document.STATE_CODE as stateCode, document.STATE_DESCRIPTION as stateDescription, document.SYSTEM_NAME as systemName,
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
    {person.*}
FROM
    BUSINESS_DOCUMENTS document
    JOIN USERS person ON document.LOGIN_ID = person.LOGIN_ID
    <#if dul?has_content>
        JOIN DOCUMENTS passport ON passport.PERSON_ID = person.ID and passport.DOC_MAIN = 1
            and replace(passport.DOC_SERIES||passport.DOC_NUMBER,' ') = :extra_dul
    </#if>
    <#if employeeFIO?has_content>
        LEFT JOIN EMPLOYEES employees ON employees.LOGIN_ID = document.CONFIRMED_EMPLOYEE_LOGIN_ID
    </#if>
    <#if nameOSB?has_content>
        LEFT JOIN SERVICE_PROVIDERS providers ON document.PROVIDER_EXTERNAL_ID = providers.EXTERNAL_ID
        LEFT JOIN DEPARTMENTS departments ON departments.ID = providers.DEPARTMENT_ID
    </#if>
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS docDepartment ON docDepartment.ID = document.DEPARTMENT_ID
    </#if>
WHERE
    (document.CREATION_DATE >= :extra_fromDate) and (document.CREATION_DATE <= :extra_toDate)

    <#if fio?has_content>
        and upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>

    <#if loginId?has_content>
        and document.LOGIN_ID = :extra_loginId
    </#if>
    and document.doc_number = nvl(:extra_number, document.doc_number)

    and :extra_empty_state!=1 and document.STATE_CODE in (:extra_state1,:extra_state2,:extra_state3,:extra_state4,:extra_state5,:extra_state6,:extra_state7,:extra_state8,:extra_state9,:extra_state10)

    AND document.STATE_CODE NOT IN ('TEMPLATE','SAVED_TEMPLATE','DRAFTTEMPLATE','WAIT_CONFIRM_TEMPLATE')
    AND (:extra_hideInitialPayments = 0 OR document.STATE_CODE NOT IN ('INITIAL','SAVED','DRAFT','INITIAL_LONG_OFFER'))
    AND (:extra_hideDeleted = 0 OR document.STATE_CODE !='DELETED')

    and (:extra_birthday is null or person.BIRTHDAY = :extra_birthday)

    and (:extra_fromAmount is null or
        (document.AMOUNT >= :extra_fromAmount
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT >= :extra_fromAmount)
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT is null))
    )

    and (:extra_toAmount is null or
        (document.AMOUNT <= :extra_toAmount
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT <= :extra_toAmount)
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT is null))
    )

    AND ((:extra_empty_formId = 1 AND :extra_extLoanClaimSelected = 0) OR
        (document.FORM_ID IN (:extra_formId1,:extra_formId2,:extra_formId3,:extra_formId4,:extra_formId5,:extra_formId6,:extra_formId7,:extra_formId8,:extra_formId9,:extra_formId10)
        AND document.IS_LONG_OFFER = DECODE(:extra_autoPayment, 'true', 1, 'false', 0, document.IS_LONG_OFFER))
    )

    and (:extra_creationType is null or document.CREATION_TYPE = :extra_creationType)

    AND (:extra_receiverName is null OR upper(document.RECEIVER_NAME) LIKE upper(:extra_like_receiverName))

    and (:extra_madeOperationId is null or  UPPER(document.billing_document_number) LIKE UPPER(:extra_like_madeOperationId))

    <#if !allTbAccess>
        AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                         WHERE ad.LOGIN_ID = :employeeLoginId
                         AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                                docDepartment.TB||'|*|*',
                                                docDepartment.TB||'|'||docDepartment.OSB||'|*',
                                                docDepartment.TB||'|'||docDepartment.OSB||'|'||docDepartment.OFFICE,
                                                '*|*|*')
                    )

    </#if>

    <#if employeeFIO?has_content>
        and upper(replace(replace(concat(concat(employees.SUR_NAME, employees.FIRST_NAME), employees.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_employeeFIO, ' ', ''), '-', ''), '%'))
    </#if>
    <#if nameOSB?has_content>
        and UPPER(departments.name) LIKE UPPER(:extra_like_nameOSB)
    </#if>
UNION ALL SELECT
    document.ID as documentId, document.DEPARTMENT_ID as departmentId, document.DOC_NUMBER as documentNumber, document.STATE_CODE as stateCode, document.STATE_DESCRIPTION as stateDescription, document.SYSTEM_NAME as systemName,
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
    {person.*}
FROM
    BUSINESS_DOCUMENTS document
    JOIN USERS person ON document.LOGIN_ID = person.LOGIN_ID
    <#if dul?has_content>
        JOIN DOCUMENTS passport ON passport.PERSON_ID = person.ID and passport.DOC_MAIN = 1
            and replace(passport.DOC_SERIES||passport.DOC_NUMBER,' ') = :extra_dul
    </#if>
    <#if employeeFIO?has_content>
        LEFT JOIN EMPLOYEES employees ON employees.LOGIN_ID = document.CONFIRMED_EMPLOYEE_LOGIN_ID
    </#if>
    <#if nameOSB?has_content>
        LEFT JOIN SERVICE_PROVIDERS providers ON document.PROVIDER_EXTERNAL_ID = providers.EXTERNAL_ID
        LEFT JOIN DEPARTMENTS departments ON departments.ID = providers.DEPARTMENT_ID
    </#if>
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS docDepartment ON docDepartment.ID = document.DEPARTMENT_ID
    </#if>
WHERE
    (document.CREATION_DATE >= :extra_fromDate) and (document.CREATION_DATE <= :extra_toDate)

    <#if fio?has_content>
        and upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>

    <#if loginId?has_content>
        and document.LOGIN_ID = :extra_loginId
    </#if>

    and document.doc_number = nvl(:extra_number, document.doc_number)

    and :extra_empty_state=1

    AND document.STATE_CODE NOT IN ('TEMPLATE','SAVED_TEMPLATE','DRAFTTEMPLATE','WAIT_CONFIRM_TEMPLATE')
    AND (:extra_hideInitialPayments = 0 OR document.STATE_CODE NOT IN ('INITIAL','SAVED','DRAFT','INITIAL_LONG_OFFER'))
    AND (:extra_hideDeleted = 0 OR document.STATE_CODE !='DELETED')

    and (:extra_birthday is null or person.BIRTHDAY = :extra_birthday)

    and (:extra_fromAmount is null or
        (document.AMOUNT >= :extra_fromAmount
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT >= :extra_fromAmount)
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT is null))
    )

    and (:extra_toAmount is null or
        (document.AMOUNT <= :extra_toAmount
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT <= :extra_toAmount)
            or (document.AMOUNT is null and document.DESTINATION_AMOUNT is null))
    )

    AND ((:extra_empty_formId = 1 AND :extra_extLoanClaimSelected = 0) OR
        (document.FORM_ID IN (:extra_formId1,:extra_formId2,:extra_formId3,:extra_formId4,:extra_formId5,:extra_formId6,:extra_formId7,:extra_formId8,:extra_formId9,:extra_formId10)
        AND document.IS_LONG_OFFER = DECODE(:extra_autoPayment, 'true', 1, 'false', 0, document.IS_LONG_OFFER))
    )

    and (:extra_creationType is null or document.CREATION_TYPE = :extra_creationType)

    AND (:extra_receiverName is null OR upper(document.RECEIVER_NAME) LIKE upper(:extra_like_receiverName))

    and (:extra_madeOperationId is null or  UPPER(document.billing_document_number) LIKE UPPER(:extra_like_madeOperationId))

    <#if !allTbAccess>
        AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                         WHERE ad.LOGIN_ID = :employeeLoginId
                         AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                                docDepartment.TB||'|*|*',
                                                docDepartment.TB||'|'||docDepartment.OSB||'|*',
                                                docDepartment.TB||'|'||docDepartment.OSB||'|'||docDepartment.OFFICE,
                                                '*|*|*')
                    )

    </#if>

    <#if employeeFIO?has_content>
        and upper(replace(replace(concat(concat(employees.SUR_NAME, employees.FIRST_NAME), employees.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_employeeFIO, ' ', ''), '-', ''), '%'))
    </#if>
    <#if nameOSB?has_content>
        and UPPER(departments.name) LIKE UPPER(:extra_like_nameOSB)
    </#if>

UNION ALL SELECT
        claim.ID as documentId, null as departmentId,  claim.DOC_NUMBER as documentNumber, claim.STATE_CODE as stateCode, claim.STATE_DESCRIPTION as stateDescription, null as systemName,
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
        {person.*}
FROM
    LOAN_CLAIMS claim
    JOIN USERS person ON claim.OWNER_LOGIN_ID = person.LOGIN_ID
    <#if dul?has_content>
        <#--
            Опорный объект: DUL_INDEX
            Предикаты доступа:
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
            access("CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
            access("PASSPORT"."PERSON_ID"="PERSON"."ID")
            access(REPLACE("DOC_SERIES"||"DOC_NUMBER",' ')=:EXTRA_DUL)
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        JOIN DOCUMENTS passport ON passport.PERSON_ID = person.ID and passport.DOC_MAIN = 1
            and replace(passport.DOC_SERIES||passport.DOC_NUMBER,' ') = :extra_dul
    </#if>
    <#if employeeFIO?has_content>
        <#--
            Опорный объект: FIO_EMPLOYEE_INDEX
            Предикаты доступа:
            access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_EMPLOYEEFIO,' ',''),'-','')||'%'))
            access("EMPLOYEES"."LOGIN_ID"="CLAIM"."CONFIRM_MANAGER_LOGIN_ID")
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID"(+))
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        RIGHT JOIN EMPLOYEES employees ON employees.LOGIN_ID = claim.CONFIRM_MANAGER_LOGIN_ID
    </#if>
WHERE
    (claim.CREATION_DATE >= :extra_fromDate) and (claim.CREATION_DATE <= :extra_toDate)

    <#if fio?has_content>
        <#--
            Опорный объект: FIO_BD_PERSON_INDEX
            Предикаты доступа:
            access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%'))
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID" AND "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        and upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>

    <#if loginId?has_content>
        <#--
            Опорный объект: IDX_USR_LOGIN
            Предикаты доступа:
            access("PERSON"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID))
            access("CLAIM"."OWNER_LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        and claim.OWNER_LOGIN_ID = :extra_loginId
    </#if>

    <#if number?has_content>
        <#--
            Опорный объект: LOAN_CLAIMS_DOCNUM_IDX
            Предикаты доступа:
            access("CLAIM"."DOC_NUMBER"=:EXTRA_NUMBER)
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        and claim.doc_number = :extra_number
    </#if>

    <#if !empty_state>
        <#--
            Опорный объект: LOAN_CLAIMS_STCD_IDX
            Предикаты доступа:
            access(("CLAIM"."STATE_CODE"=:EXTRA_STATE1 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE2 OR
            "CLAIM"."STATE_CODE"=:EXTRA_STATE3 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE4 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE5 OR
            "CLAIM"."STATE_CODE"=:EXTRA_STATE6 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE7 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE8 OR
            "CLAIM"."STATE_CODE"=:EXTRA_STATE9 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE10) AND
            "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        and claim.STATE_CODE in (:extra_state1,:extra_state2,:extra_state3,:extra_state4,:extra_state5,:extra_state6,:extra_state7,:extra_state8,:extra_state9,:extra_state10)
    </#if>

    AND claim.STATE_CODE NOT IN ('TEMPLATE','SAVED_TEMPLATE','DRAFTTEMPLATE','WAIT_CONFIRM_TEMPLATE')
    AND (:extra_hideInitialPayments = 0 OR claim.STATE_CODE NOT IN ('INITIAL','SAVED','DRAFT','INITIAL_LONG_OFFER'))
    AND (:extra_hideDeleted = 0 OR claim.STATE_CODE !='DELETED')

    and (:extra_birthday is null or person.BIRTHDAY = :extra_birthday)

    AND (:extra_empty_formId = 1 OR :extra_extLoanClaimSelected = 1)

    and (:extra_creationType is null or claim.CREATE_CHANNEL = :extra_creationType)

    <#if !allTbAccess>
        <#--
            Опорный объект: PK_ALLOWED_DEPARTMENTS
            Предикаты доступа: access("AD"."LOGIN_ID"=TO_NUMBER(:EMPLOYEELOGINID))
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
             WHERE ad.LOGIN_ID = :employeeLoginId
             AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (claim.TB||'|*|*', claim.TB||'|'||claim.OSB||'|*', claim.TB||'|'||claim.OSB||'|'||claim.VSP,'*|*|*')
        )

    </#if>

    <#if employeeFIO?has_content>
        <#--
            Опорный объект: FIO_EMPLOYEE_INDEX
            Предикаты доступа:
            access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_EMPLOYEEFIO,' ',''),'-','')||'%'))
            access("EMPLOYEES"."LOGIN_ID"="CLAIM"."CONFIRM_MANAGER_LOGIN_ID")
            access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID"(+))
            Кардинальность: стоп условие - количество записей на странице (пагинация)
        -->
        and upper(replace(replace(concat(concat(employees.SUR_NAME, employees.FIRST_NAME), employees.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_employeeFIO, ' ', ''), '-', ''), '%'))
    </#if>
UNION ALL
SELECT
        claim.ID as documentId,
        null as departmentId,
        claim.DOC_NUMBER as documentNumber,
        claim.STATE_CODE as stateCode,
        claim.STATE_DESCRIPTION as stateDescription,
        null as systemName,
        null as comission,
        null as commissionCurrency,
        claim.ARCHIVE as archive,
        claim.CREATE_CHANNEL as creationType,
        claim.CONFIRM_CHANNEL as clientOperationChannel,
        claim.REFUSING_REASON as refusingReason,
        claim.CREATION_DATE as dateCreated,
        claim.OPERATION_DATE as operationDate,
        claim.ADMISSION_DATE as admissionDate,
        claim.EXECUTION_DATE as executionDate, claim.DOCUMENT_DATE as documentDate,
        null as stateMachineName,
        null as templateId,
        claim.CREATION_SOURCE_TYPE as creationSourceType,
        claim.CONFIRM_STRATEGY_TYPE as confirmStrategyType,
        claim.OPERATION_UID as operationUID,
        null as sessionId,
        null as promoCode,
        null as createdEmployeeLoginId,
        null as confirmedEmployeeLoginId,
        null as codeATM,
        claim.ADDITION_CONFIRM as additionalOperationChannel,
        claim.OWNER_LOGIN_TYPE as loginType,
        null as formId,
        'LCC' as kind,
        claim.EXTERNAL_ID as externalId,
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
        claim.OWNER_LOGIN_ID as loginId,
        null as countError,
        null as autoPaySchemeAsString,
        {person.*}
FROM
    LOAN_CARD_CLAIMS claim
    JOIN USERS person ON claim.OWNER_LOGIN_ID = person.LOGIN_ID
    <#if dul?has_content>
    <#--
        Опорный объект: DUL_INDEX
        Предикаты доступа:
        access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
        access("CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
        access("PASSPORT"."PERSON_ID"="PERSON"."ID")
        access(REPLACE("DOC_SERIES"||"DOC_NUMBER",' ')=:EXTRA_DUL)
        Кардинальность: стоп условие - количество записей на странице (пагинация)
    -->
    JOIN DOCUMENTS passport
                ON passport.PERSON_ID = person.ID
               AND passport.DOC_MAIN = 1
               AND replace(passport.DOC_SERIES||passport.DOC_NUMBER,' ') = :extra_dul
    </#if>
WHERE
    (claim.CREATION_DATE >= :extra_fromDate) AND (claim.CREATION_DATE <= :extra_toDate)

    <#if fio?has_content>
    <#--
        Опорный объект: FIO_BD_PERSON_INDEX
        Предикаты доступа:
        access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%'))
        access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID" AND "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
        Кардинальность: стоп условие - количество записей на странице (пагинация)
    -->
    AND UPPER(REPLACE(REPLACE(CONCAT(CONCAT(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) LIKE UPPER(CONCAT(REPLACE(REPLACE(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>

    <#if loginId?has_content>
    <#--
        Опорный объект: IDX_USR_LOGIN
        Предикаты доступа:
        access("PERSON"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID))
        access("CLAIM"."OWNER_LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
        Кардинальность: стоп условие - количество записей на странице (пагинация)
    -->
    AND claim.OWNER_LOGIN_ID = :extra_loginId
    </#if>

    <#if number?has_content>
    <#--
        Опорный объект: LOAN_CCLAIMS_DOCNUM_IDX
        Предикаты доступа:
        access("CLAIM"."DOC_NUMBER"=:EXTRA_NUMBER)
        access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
        Кардинальность: стоп условие - количество записей на странице (пагинация)
    -->
    AND claim.doc_number = :extra_number
    </#if>

    <#if !empty_state>
    <#--
        Опорный объект: LOAN_CCLAIMS_STCD_IDX
        Предикаты доступа:
        access(("CLAIM"."STATE_CODE"=:EXTRA_STATE1 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE2 OR
        "CLAIM"."STATE_CODE"=:EXTRA_STATE3 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE4 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE5 OR
        "CLAIM"."STATE_CODE"=:EXTRA_STATE6 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE7 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE8 OR
        "CLAIM"."STATE_CODE"=:EXTRA_STATE9 OR "CLAIM"."STATE_CODE"=:EXTRA_STATE10) AND
        "CLAIM"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "CLAIM"."CREATION_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
        access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
        access("CLAIM"."OWNER_LOGIN_ID"="PERSON"."LOGIN_ID")
        Кардинальность: стоп условие - количество записей на странице (пагинация)
    -->
    AND claim.STATE_CODE IN (:extra_state1,:extra_state2,:extra_state3,:extra_state4,:extra_state5,:extra_state6,:extra_state7,:extra_state8,:extra_state9,:extra_state10)
    </#if>

    AND (:extra_hideInitialPayments = 0 OR claim.STATE_CODE NOT IN ('INITIAL','SAVED'))
    AND (:extra_hideDeleted  = 0 OR claim.STATE_CODE !='DELETED')
    AND (:extra_empty_formId = 1 OR :extra_extLoanClaimSelected = 1)
    AND (:extra_birthday     IS NULL OR person.BIRTHDAY      = :extra_birthday)
    AND (:extra_creationType IS NULL OR claim.CREATE_CHANNEL = :extra_creationType)

    <#if !allTbAccess>
    <#--
        Опорный объект: PK_ALLOWED_DEPARTMENTS
        Предикаты доступа: access("AD"."LOGIN_ID"=TO_NUMBER(:EMPLOYEELOGINID))
        Кардинальность: стоп условие - количество записей на странице (пагинация)
    -->
    AND EXISTS(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                WHERE ad.LOGIN_ID = :employeeLoginId
                  AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (claim.TB||'|*|*', claim.TB||'|'||claim.OSB||'|*', claim.TB||'|'||claim.OSB||'|'||claim.VSP,'*|*|*'))
    </#if>
order by dateCreated desc