SELECT
    {DOCUMENTS.*}
FROM
    BUSINESS_DOCUMENTS DOCUMENTS
    JOIN SERVICE_PROVIDERS PROVIDERS ON DOCUMENTS.PROVIDER_EXTERNAL_ID = PROVIDERS.EXTERNAL_ID
    <#if serviceProviders?has_content>
        AND DOCUMENTS.PROVIDER_EXTERNAL_ID in (:extra_serviceProviders)
    </#if>
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS department on providers.DEPARTMENT_ID = department.id
    </#if>
WHERE
    DOCUMENTS.STATE_CODE = 'EXECUTED'
    AND PROVIDERS.BILLING_ID = :extra_billingId
    AND PROVIDERS.KIND = 'B'
    AND DOCUMENTS.EXECUTION_DATE>= :extra_unloadPeriodDateFrom AND :extra_unloadPeriodDateTo >= DOCUMENTS.EXECUTION_DATE
    <#if !allTbAccess>
        AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                            WHERE ad.LOGIN_ID = :extra_employeeLoginId
                            AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (department.TB||'|*|*',
                                                         department.TB||'|'||department.OSB||'|*',
                                                         department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                         '*|*|*')
                    )
    </#if>
    AND LENGTH(DOCUMENTS.PAYER_ACCOUNT)>19
ORDER BY PROVIDERS.CODE
