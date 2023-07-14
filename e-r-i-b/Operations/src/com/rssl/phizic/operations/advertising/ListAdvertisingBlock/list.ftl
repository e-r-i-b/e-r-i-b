SELECT {advertising.*}
FROM ADVERTISINGS advertising
WHERE 
    advertising.STATE != 'DELETED'
<#if name?has_content>
    AND upper(advertising.NAME) like upper(:extra_like_name)
</#if>
<#if orderIndex?has_content>
    AND advertising.ORDER_INDEX = :extra_orderIndex
</#if>
<#if state?has_content>
    AND advertising.STATE = :extra_state
</#if>
<#if fromDate?has_content>
    AND (advertising.END_DATE >= :extra_fromDate OR advertising.END_DATE IS NULL)
</#if>
<#if toDate?has_content>
    AND advertising.START_DATE <= :extra_toDate
</#if>
<#if departmentId?has_content>
    and advertising.ID IN
        (SELECT departments.ADVERTISING_ID
        FROM ADVERTISINGS_DEPARTMENTS departments
        WHERE departments.TB = :extra_departmentId)
<#else>
    <#if !allTbAccess>
        AND advertising.ID IN
            (SELECT advDepartments.ADVERTISING_ID
               FROM ADVERTISINGS_DEPARTMENTS advDepartments
               WHERE exists (select 1 from ALLOWED_DEPARTMENTS allowedDepartments
                             WHERE allowedDepartments.LOGIN_ID = :extra_employeeLoginId
                                AND (allowedDepartments.TB = advDepartments.TB
                                OR  allowedDepartments.TB = '*')
                                AND allowedDepartments.OSB = '*'
                                AND allowedDepartments.VSP = '*'
                             )
            )
    </#if>
</#if>
ORDER BY advertising.ID DESC