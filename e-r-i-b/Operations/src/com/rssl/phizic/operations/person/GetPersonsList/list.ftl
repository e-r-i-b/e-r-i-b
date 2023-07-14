SELECT
    {person.*},
    {simpleScheme.*}
FROM
	USERS person
    LEFT JOIN SCHEMEOWNS    simpleOwn    ON person.LOGIN_ID     = simpleOwn.LOGIN_ID and simpleOwn.ACCESS_TYPE='simple'
    LEFT JOIN ACCESSSCHEMES simpleScheme ON simpleOwn.SCHEME_ID = simpleScheme.ID
	LEFT JOIN PERSONAL_SUBSCRIPTION_DATA personSubData ON person.LOGIN_ID = personSubData.LOGIN_ID
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS department on person.DEPARTMENT_ID = department.id
    </#if>
WHERE 1=1
<#if !allTbAccess>
    and exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                     WHERE ad.LOGIN_ID = :extra_employeeLoginId
                     AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (department.TB||'|*|*',
                                                             department.TB||'|'||department.OSB||'|*',
                                                             department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                             '*|*|*')
              )

</#if>
    <#if fio?has_content>
        and (
                (upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%')))
        <#if birthDay?has_content>
                or (upper(replace(replace(concat(person.FIRST_NAME, person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
            )  and person.BIRTHDAY = :extra_birthDay)
        <#else>
            )
        </#if>
    </#if>

    <#if mobile_phone?has_content>
        and upper (replace(replace(replace(replace(replace(personSubData.MOBILE_PHONE, '(', ''), '-', ''), ')', ''), ' ', ''), '+', '')) like
            upper (:extra_like_mobile_phone)
    </#if>

    <#if agreementNumber?has_content>
        and person.AGREEMENT_NUMBER like :extra_like_agreementNumber
    </#if>

    <#if state?has_content>
        and person.STATE = :extra_state
    <#else>
        and person.STATE in('A','T','E','W','S')
    </#if>

    <#if creationType?has_content>
        and person.CREATION_TYPE = :extra_creationType
    <#else>
        and person.CREATION_TYPE <> 'POTENTIAL'
    </#if>

    and (:extra_birthDay is null or person.BIRTHDAY = :extra_birthDay)

    <#if documentSeries?has_content || documentNumber?has_content>
        and person.ID in
            (select PERSON_ID from DOCUMENTS document
              where document.DOC_IDENTIFY = 1
                and (
                        <#if documentSeries?has_content>
                            replace(document.DOC_SERIES,' ') like concat(:extra_documentSeries, '%')
                            <#if documentNumber?has_content> and </#if>
                        </#if>
                        <#if documentNumber?has_content>
                            document.DOC_NUMBER like concat(:extra_documentNumber, '%')
                        </#if>
                    )

            )
    </#if>

    <#if loginId?has_content>
        and person.LOGIN_ID = :extra_loginId
    </#if>

    <#if terBank?has_content>
        and :extra_terBank = (SELECT departments.TB FROM DEPARTMENTS departments WHERE departments.ID = person.DEPARTMENT_ID)
    </#if>
    <#if blocked?has_content>
    AND (
        (:extra_blocked = -1 )
         OR (:extra_blocked = 0
                            AND (SELECT COUNT(*) FROM LOGIN_BLOCK lb
                                WHERE person.LOGIN_ID=lb.LOGIN_ID AND (lb.DATE_FROM <= :extra_blockedUntil and (lb.DATE_UNTIL is null or lb.DATE_UNTIL > :extra_blockedUntil))) = 0)
                         OR (:extra_blocked = 1
                            AND (SELECT COUNT(*) FROM LOGIN_BLOCK lb
                                WHERE person.LOGIN_ID=lb.LOGIN_ID AND (lb.DATE_FROM <= :extra_blockedUntil and (lb.DATE_UNTIL is null or lb.DATE_UNTIL > :extra_blockedUntil))) > 0)
                        )
    </#if>
ORDER BY person.SUR_NAME, person.FIRST_NAME, person.PATR_NAME ASC