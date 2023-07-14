SELECT
    {offerNotification.*}
FROM
    PERSONAL_OFFER_NOTIFICATION offerNotification
WHERE
    offerNotification.STATE != 'DELETED'
<#if name?has_content>
    AND upper(offerNotification.NAME) like upper(:extra_like_name)
</#if>
<#if orderIndex?has_content>
    AND offerNotification.ORDER_INDEX = :extra_orderIndex
</#if>
<#if state?has_content>
    AND offerNotification.STATE = :extra_state
</#if>
<#if fromDate?has_content>
    AND (offerNotification.END_DATE >= :extra_fromDate OR offerNotification.END_DATE IS NULL)
</#if>
<#if toDate?has_content>
    AND offerNotification.START_DATE <= :extra_toDate
</#if>
<#if departmentId?has_content>
    and offerNotification.ID IN
        (SELECT departments.PERSONAL_OFFER_ID
         FROM PERSONAL_OFFER_DEPARTMENTS departments
         WHERE departments.TB = :extra_departmentId)
<#else>
    <#if !allTbAccess>
        AND offerNotification.ID IN
            (SELECT
                offerDepartments.PERSONAL_OFFER_ID
            FROM
                PERSONAL_OFFER_DEPARTMENTS offerDepartments
            WHERE
                exists (
                    SELECT
                        1
                    FROM
                        ALLOWED_DEPARTMENTS allowedDepartments
                    WHERE
                        allowedDepartments.LOGIN_ID = :extra_employeeLoginId
                        AND (allowedDepartments.TB = offerDepartments.TB
                        OR  allowedDepartments.TB = '*')
                        AND allowedDepartments.OSB = '*'
                        AND allowedDepartments.VSP = '*'
                        )
            )
    </#if>
</#if>
ORDER BY offerNotification.ID DESC