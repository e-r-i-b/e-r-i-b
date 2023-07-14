SELECT
    {person.*},
    {login.*},
    {simpleScheme.*}
FROM
    (select USERS.*
     from USERS
     <#if mailId?has_content>
     where USERS.LOGIN_ID IN (SELECT recipient.RECIPIENT_ID
                              FROM RECIPIENTS recipient
                              WHERE recipient.MAIL_ID = :mailId)
     <#else>
         inner join GROUP_ELEMENTS grel on grel.GROUP_ID = :groupId and USERS.LOGIN_ID = grel.LOGIN_ID
     </#if>) person
    join      LOGINS        login        on person.LOGIN_ID     = login.ID
    left join SCHEMEOWNS    simpleOwn    on person.LOGIN_ID     = simpleOwn.LOGIN_ID and simpleOwn.ACCESS_TYPE = 'simple'
    left join ACCESSSCHEMES simpleScheme on simpleOwn.SCHEME_ID = simpleScheme.ID
    left join PERSONAL_SUBSCRIPTION_DATA personSubData on person.LOGIN_ID = personSubData.LOGIN_ID
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS department on person.DEPARTMENT_ID = department.id
    </#if>
WHERE 1=1
	/*доступные департаменты*/
    <#if !allTbAccess>
    AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                     WHERE ad.LOGIN_ID = :extra_employeeLoginId
                     AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (department.TB||'|*|*',
                                                             department.TB||'|'||department.OSB||'|*',
                                                             department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                             '*|*|*')
              )

    </#if>
	/*тербанк*/
     <#if terBank?has_content>
        and :extra_terBank = (SELECT departments.TB FROM DEPARTMENTS departments WHERE departments.ID = person.DEPARTMENT_ID)
     </#if>
	/*тип договора*/
	 <#if creationType?has_content>
        and person.CREATION_TYPE = :extra_creationType
     </#if>
	 /*дата рождения*/
	 <#if birthDay?has_content>
        and person.BIRTHDAY = :extra_birthDay
    </#if>
	/*ФИО*/
	<#if fio?has_content>
        and upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>
	/*Login_ID*/
	<#if loginId?has_content>
        and person.LOGIN_ID = :extra_loginId
    </#if>
	 /*серия и номер документа*/
	 <#if documentSeries?has_content || documentNumber?has_content>
        and person.ID in
            (select PERSON_ID from DOCUMENTS document
              where document.DOC_IDENTIFY = 1
                and (
                        <#if documentSeries?has_content>
                            document.DOC_SERIES like concat(:extra_documentSeries, '%')
                            <#if documentNumber?has_content> and </#if>
                        </#if>
                        <#if documentNumber?has_content>
                            document.DOC_NUMBER like concat(:extra_documentNumber, '%')
                        </#if>
                    )
            )
    </#if>
	 /*номер договора*/
     <#if agreementNumber?has_content>
        and person.AGREEMENT_NUMBER like :extra_like_agreementNumber
    </#if>
    <#if blocked?has_content>
    AND (
         (:extra_blocked = -1 ) OR
         (:extra_blocked = 0 AND (SELECT COUNT(*) FROM LOGIN_BLOCK lb
                                   WHERE person.LOGIN_ID=lb.LOGIN_ID
                                     AND (lb.DATE_FROM <= :extra_blockedUntil 
                                     AND (lb.DATE_UNTIL is null or lb.DATE_UNTIL > :extra_blockedUntil))) = 0) OR
         (:extra_blocked = 1 AND (SELECT COUNT(*)FROM LOGIN_BLOCK lb
                                   WHERE person.LOGIN_ID=lb.LOGIN_ID
                                     AND (lb.DATE_FROM <= :extra_blockedUntil
                                     AND (lb.DATE_UNTIL is null or lb.DATE_UNTIL > :extra_blockedUntil))) > 0)
        )
    </#if>
	/*состояние*/
	<#if state?has_content>
        and person.STATE = :extra_state
    <#else>
        and person.STATE in('A','T','E','W','S')
    </#if>
	/*Мобильный телефон*/
	<#if mobile_phone?has_content>
        and upper (replace(replace(replace(replace(replace(personSubData.MOBILE_PHONE, '(', ''), '-', ''), ')', ''), ' ', ''), '+', '')) like
            upper (:extra_like_mobile_phone)
    </#if>
ORDER BY person.SUR_NAME, person.FIRST_NAME, person.PATR_NAME ASC