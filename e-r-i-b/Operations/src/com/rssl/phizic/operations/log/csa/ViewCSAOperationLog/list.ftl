SELECT  
        operations.TYPE as descriptionKey,
        operations.STATE as success,
        operations.CREATION_DATE as startDate,
        operations.USER_ID as login,
        'CSABack' as application,
        operations.OUID as operationKey,
        operations.PARAMS as parameters,
        operations.IP_ADDRESS as IPAddress,
        profiles.FIRST_NAME as firstName,
        profiles.SUR_NAME as surName,
        profiles.PATR_NAME as patrName,
        profiles.BIRTHDATE as birthDay,              
        profiles.PASSPORT as docNumber,
        profiles.TB as departmentCode
FROM CSA_OPERATIONS operations
JOIN CSA_PROFILES profiles ON operations.PROFILE_ID = profiles.ID
WHERE
    (operations.CREATION_DATE >= :extra_fromDate)
    AND (operations.CREATION_DATE <= :extra_toDate)
    <#if fio?has_content>
        and upper(replace(replace(concat(concat(profiles.SUR_NAME, profiles.FIRST_NAME), profiles.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>
    <#if number?has_content>
        and replace(profiles.PASSPORT,' ','') = :extra_number
    </#if>
    <#if birthday?has_content>
        and profiles.BIRTHDATE = :extra_birthday
    </#if>
    <#if type?has_content>
        AND operations.TYPE in (:extra_type)
    </#if>
    <#if state?has_content>
        AND operations.STATE = :extra_state
    </#if>
    <#if login?has_content>
        AND operations.USER_ID = :extra_login
    </#if>
    <#if departmentCode?has_content>
        AND profiles.TB = :extra_departmentCode
    </#if>
order by operations.CREATION_DATE desc