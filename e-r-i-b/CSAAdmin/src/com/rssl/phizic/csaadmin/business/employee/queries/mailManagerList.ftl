<#--
   Опорный объект: ACCESSSCHEMES_MAIL
   Предикаты доступа:
      access(DECODE("MAIL_MANAGEMENT",'1',1,NULL)=1)
   Кардинальность: 25
-->
SELECT
    employee.ID ID,
    login.NAME EXTERNAL_ID,
    employee.SUR_NAME || ' ' || employee.FIRST_NAME || ' ' || employee.PATR_NAME NAME,
    decode(department.OSB, null, '', department.OSB || decode(department.OFFICE, null, '', '/' || department.OFFICE) || ' ') || department.NAME DEPARTMENT,
    cc_area.AREA_NAME AREA
FROM
    EMPLOYEES employee
    JOIN
        LOGINS login ON employee.LOGIN_ID = login.ID and not exists (SELECT 1
                                                                     FROM LOGIN_BLOCK lb
                                                                     WHERE login.ID = lb.LOGIN_ID AND
                                                                           (lb.DATE_UNTIL is null OR (lb.DATE_FROM < :extra_soughtBlockedUntil AND lb.DATE_UNTIL > :extra_soughtBlockedUntil)))
    JOIN
        ACCESSSCHEMES scheme ON scheme.ID = login.ACCESSSCHEME_ID
    JOIN
        DEPARTMENTS department ON department.TB                  = employee.TB AND
                                  nvl(department.OSB, 'NULL')    = nvl(employee.OSB, 'NULL') AND
                                  nvl(department.OFFICE, 'NULL') = nvl(employee.VSP, 'NULL')
    JOIN
        C_CENTER_AREAS_DEPARTMENTS cc_area_dep ON cc_area_dep.TB = department.TB
    JOIN
        CONTACT_CENTER_AREAS cc_area ON cc_area_dep.C_C_AREA_ID = cc_area.ID
WHERE
    decode(scheme.MAIL_MANAGEMENT, '1', 1, null) = 1
    <#if soughtFIO?has_content>
        <#--
           Опорный объект: I_EMPLOYEES_FIO
           Предикаты доступа:
              access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_SOUGHTFIO,' ',''),'-','')||'%'))
           Кардинальность: зависит от :EXTRA_SOUGHTFIO
        -->
        AND upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_soughtFIO, ' ', ''), '-', ''), '%'))
    </#if>
    <#if soughtId?has_content>
        <#--
           Опорный объект: PK_EMPLOYEES
           Предикаты доступа:
              access("EMPLOYEE"."ID"=TO_NUMBER(:EXTRA_SOUGHTID))
           Кардинальность: 1
        -->
        AND employee.ID = :extra_soughtId
    </#if>
    <#if soughtArea?has_content>
        <#--
           Опорный объект: I_CONTACT_CENTER_AREAS_UUID
           Предикаты доступа:
              access("CC_AREA"."UUID"=:EXTRA_SOUGHTAREA)
           Кардинальность: 1
        -->
        AND cc_area.UUID = :extra_soughtArea
    </#if>

