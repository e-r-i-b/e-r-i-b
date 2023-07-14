SELECT
    {employee.*}
FROM
    EMPLOYEES employee
    JOIN LOGINS login ON employee.LOGIN_ID = login.ID AND
                            (
                             (:extra_soughtBlockedState = -1 )
                             OR (:extra_soughtBlockedState = 0 AND (SELECT COUNT(*) FROM LOGIN_BLOCK lb WHERE login.id=lb.LOGIN_ID AND (lb.DATE_UNTIL is null or (lb.DATE_FROM < :extra_soughtBlockedUntil and lb.DATE_UNTIL > :extra_soughtBlockedUntil))) = 0)
                             OR (:extra_soughtBlockedState = 1 AND (SELECT COUNT(*) FROM LOGIN_BLOCK lb WHERE login.id=lb.LOGIN_ID AND (lb.DATE_UNTIL is null or (lb.DATE_FROM < :extra_soughtBlockedUntil and lb.DATE_UNTIL > :extra_soughtBlockedUntil))) > 0)
                            )
    LEFT JOIN SCHEMEOWNS employee_1_ ON employee.LOGIN_ID = employee_1_.LOGIN_ID
    LEFT JOIN DEPARTMENTS department ON employee.DEPARTMENT_ID = department.ID
WHERE 1=1
<#if soughtFIO?has_content>
    and upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_soughtFIO, ' ', ''), '-', ''), '%'))
</#if>
<#if soughtInfo?has_content>
  AND upper(employee.INFO) like upper(:extra_like_soughtInfo)
</#if>
<#if soughtLogin?has_content>
  AND login.USER_ID like :extra_like_soughtLogin
</#if>
<#if soughtTB?has_content>
  AND department.TB    = :extra_soughtTB
</#if>
<#if soughtBranchCode?has_content>
  AND nvl(department.OSB, 'NULL')    = :extra_soughtBranchCode
</#if>
<#if soughtDepartmentCode?has_content>
  AND nvl(department.OFFICE, 'NULL') = :extra_soughtDepartmentCode
</#if>
<#if soughtId?has_content>
  AND employee.ID = :extra_soughtId
</#if>
<#if !seekerAllDepartments>
  AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                   WHERE ad.LOGIN_ID = :extra_seekerLoginId
                   AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                                           department.TB||'|*|*',
                                                           department.TB||'|'||department.OSB||'|*',
                                                           department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                           '*|*|*')
            )
</#if>
  ORDER BY employee.SUR_NAME,employee.FIRST_NAME,employee.PATR_NAME ASC