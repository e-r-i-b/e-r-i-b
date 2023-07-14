SELECT department.id as id,
       department.name as name,
       <#if !checkChild>
        0
       <#else>
       (
            select count(1) from DEPARTMENTS child
            where
                (child.TB = department.TB) AND
                (department.OSB is null and child.OSB is not null -- любые дети ТБ
                or
                (department.OFFICE is null and department.OSB = nvl(child.OSB, 'NULL') and child.OFFICE is not NULL)) --дюбые дети ОСБ
                AND rownum<2
       )
       </#if> as hasChild,
      <#if allTbAccess>
        1
      <#else>
        (select count(ad.LOGIN_ID) from ALLOWED_DEPARTMENTS ad where ad.LOGIN_ID = :extra_employeeLoginId
                              AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (department.TB||'|*|*',
                                                                      department.TB||'|'||department.OSB||'|*',
                                                                      department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                                      '*|*|*'))
      </#if>
       allowed,
       department.TB      as TB,
       department.OSB     as OSB,
       department.OFFICE  as VSP
  FROM DEPARTMENTS department
<#if parent?has_content>
  , (select * from DEPARTMENTS where ID= :extra_parent) parentDep
</#if>
  WHERE
    (:extra_like_name is null or upper(department.NAME) like upper(:extra_like_name))
<#if parent?has_content>
    AND parentDep.TB = department.TB
    AND
        (parentDep.OSB is null AND nvl(department.OSB, 'NULL') != 'NULL' AND nvl(department.OFFICE, 'NULL') = 'NULL' -- выбираем ОСБ
        OR
        nvl(department.OSB, 'NULL') = parentDep.OSB AND nvl(department.OFFICE, 'NULL') != 'NULL') -- выбираем ВСП.
<#else>
    AND DECODE(department.OFFICE||department.OSB,NULL,department.TB,NULL) is not null -- получаем только ТБ
</#if>

<#if !allTbAccess>
AND
(exists( SELECT 1 FROM ALLOWED_DEPARTMENTS ad
            WHERE ad.LOGIN_ID = :extra_employeeLoginId
                AND (ad.TB = '*' or department.TB = ad.TB)
                AND (ad.OSB= '*' or department.OSB is null or nvl(department.OSB, 'NULL') = nvl(ad.OSB, 'NULL'))
                AND (ad.VSP= '*' or department.OFFICE is null or nvl(department.OFFICE, 'NULL') = nvl(ad.VSP, 'NULL'))
))
</#if>
ORDER BY department.name