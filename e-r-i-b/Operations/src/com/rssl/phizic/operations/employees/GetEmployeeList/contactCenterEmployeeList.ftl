SELECT {employee.*}, {department.*}, cc_area.AREA_NAME as CC_AREA_NAME FROM
    EMPLOYEES employee
JOIN
    SCHEMEOWNS schemeown ON schemeown.LOGIN_ID = employee.LOGIN_ID
 AND
   (
    (SELECT COUNT(*)
     FROM
        LOGIN_BLOCK lb
     WHERE
        employee.LOGIN_ID = lb.LOGIN_ID
       AND (lb.DATE_UNTIL is null OR (lb.DATE_FROM < :extra_blockedUntil AND lb.DATE_UNTIL > :extra_blockedUntil))) = 0
   )
JOIN
    SCHEMESSERVICES schemeservice ON schemeown.SCHEME_ID = schemeservice.SCHEME_ID
JOIN
    SERVICES service ON schemeservice.SERVICE_ID = service.ID and service.service_key = 'MailManagment'
LEFT JOIN
    DEPARTMENTS department ON employee.DEPARTMENT_ID = department.ID,
    C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area
WHERE
   cc_area_dep.C_C_AREA_ID = cc_area.id
AND
   cc_area_dep.TB = (select d.TB from DEPARTMENTS d where d.ID = employee.DEPARTMENT_ID)
<#if area_id?has_content>
    AND cc_area.id = :extra_area_id
</#if>
<#if fio?has_content>
  AND upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
</#if>
<#if employeeId?has_content>
  AND employee.ID = :extra_employeeId
</#if>

