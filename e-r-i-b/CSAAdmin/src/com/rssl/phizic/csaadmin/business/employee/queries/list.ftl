SELECT
    {employee.*}
FROM
    EMPLOYEES employee
    JOIN LOGINS login ON employee.LOGIN_ID = login.ID
                            <#if soughtBlockedState != -1>
                                AND
                                <#if soughtBlockedState = 0>
                                    not
                                </#if>
                                exists (SELECT 1 FROM LOGIN_BLOCK lb WHERE login.id=lb.LOGIN_ID AND (lb.DATE_UNTIL is null or (lb.DATE_FROM < :extra_soughtBlockedUntil and lb.DATE_UNTIL > :extra_soughtBlockedUntil)))
                            </#if>
WHERE (:extra_soughtInfo is NULL OR upper(employee.INFO) like upper(:extra_like_soughtInfo))
<#if soughtId?has_content>
  AND employee.ID = :extra_soughtId
</#if>
<#if soughtFIO?has_content>
    and upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_soughtFIO, ' ', ''), '-', ''), '%'))
</#if>
<#if soughtLogin?has_content>
  AND login.NAME like :extra_soughtLogin || '%'
</#if>
<#if soughtTB?has_content>
  AND employee.TB    = :extra_soughtTB
</#if>
<#if soughtBranchCode?has_content>
  AND nvl(employee.OSB, 'NULL') = :extra_soughtBranchCode
</#if>
<#if soughtDepartmentCode?has_content>
  AND nvl(employee.VSP, 'NULL') = :extra_soughtDepartmentCode
</#if>
<#if !seekerAllDepartments>
    AND EXISTS (SELECT 1
                FROM ALLOWED_DEPARTMENTS ad
                WHERE ad.LOGIN_ID = :extra_seekerLoginId AND (ad.TB || '|' || ad.OSB || '|' || ad.VSP = '*|*|*' OR
                                                              ad.TB || '|' || ad.OSB || '|' || ad.VSP = employee.TB || '|*|*' OR
                                                              ad.TB || '|' || ad.OSB || '|' || ad.VSP = employee.TB || '|' || employee.OSB || '|*' OR
                                                              ad.TB || '|' || ad.OSB || '|' || ad.VSP = employee.TB || '|' || employee.OSB || '|' || employee.VSP))
</#if>
ORDER BY employee.SUR_NAME,employee.FIRST_NAME,employee.PATR_NAME ASC