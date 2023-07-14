SELECT
    {quick_payment_panel.*}
FROM
    QUICK_PAYMENT_PANELS quick_payment_panel
WHERE
    quick_payment_panel.STATE != 'DELETED'
<#if name?has_content>
    AND upper(quick_payment_panel.NAME) like upper(:extra_like_name)
</#if>
<#if state?has_content>
    AND quick_payment_panel.STATE = :extra_state
</#if>
<#if fromDate?has_content>
    AND (quick_payment_panel.END_DATE >= :extra_fromDate OR quick_payment_panel.END_DATE IS NULL)
</#if>
<#if toDate?has_content>
    AND quick_payment_panel.START_DATE <= :extra_toDate
</#if>
<#if TB?has_content>
    and quick_payment_panel.ID IN
        (SELECT departments.Q_P_PANEL_ID
        FROM Q_P_PANELS_DEPARTMENTS departments
        WHERE departments.TB = :extra_TB)
<#else>
    <#if !allTbAccess>
        and quick_payment_panel.ID IN
            (SELECT qppDepartments.Q_P_PANEL_ID
               FROM Q_P_PANELS_DEPARTMENTS qppDepartments
              WHERE exists(select 1 from ALLOWED_DEPARTMENTS allowedDepartments
                            WHERE allowedDepartments.LOGIN_ID = :extra_employeeLoginId
                            AND (allowedDepartments.TB = qppDepartments.TB
                            OR allowedDepartments.TB = '*')
                            AND allowedDepartments.OSB = '*'
                            AND allowedDepartments.VSP = '*'
                           )
            )
    </#if>
</#if>
ORDER BY quick_payment_panel.ID DESC