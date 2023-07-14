SELECT {person.*},
       {login.*},
       {simpleScheme.*}
  FROM (select USERS.login_id id
         from USERS
         where USERS.login_id is not null
         minus
         select login_id from group_elements  where group_id = :groupId) loginIds
  JOIN users person ON person.login_id=loginIds.id
  JOIN LOGINS login ON person.LOGIN_ID = login.id
  <#if !allTbAccess || groupTB?has_content>
    LEFT JOIN DEPARTMENTS department on person.department_id = department.id
  </#if>
  <#if groupTB?has_content>
    AND department.TB =:groupTB
  </#if>
  <#if groupOSB?has_content>
    AND department.OSB =:groupOSB
  </#if>
  <#if groupOffice?has_content>
    AND department.OFFICE =:groupOffice
  </#if>
  <#if !allTbAccess>
    AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                   WHERE ad.LOGIN_ID = :employeeLoginId
                   AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                            department.TB||'|*|*',
                                            department.TB||'|'||department.OSB||'|*',
                                            department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                            '*|*|*')
              )
  </#if>
  LEFT JOIN SCHEMEOWNS    simpleOwn      ON person.LOGIN_ID          = simpleOwn.LOGIN_ID and simpleOwn.ACCESS_TYPE='simple'
  LEFT JOIN ACCESSSCHEMES simpleScheme   ON simpleOwn.SCHEME_ID      = simpleScheme.ID
  LEFT JOIN DOCUMENTS 	   activeDocument ON activeDocument.PERSON_ID = person.ID and activeDocument.DOC_MAIN = 1
  LEFT JOIN PERSONAL_SUBSCRIPTION_DATA personSubData ON person.LOGIN_ID = personSubData.LOGIN_ID
 WHERE 1=1
 /*тербанк*/
 <#if terBank?has_content>
   and :extra_terBank = (SELECT departments.TB FROM DEPARTMENTS departments WHERE departments.ID = person.DEPARTMENT_ID)
 </#if>
 /*тип договора*/
 <#if creationType?has_content>
   AND person.CREATION_TYPE = :extra_creationType
 <#else>
   AND person.CREATION_TYPE <> 'POTENTIAL'
 </#if>
 /*дата рождения*/
 <#if birthDay?has_content>
   and person.BIRTHDAY = :extra_birthDay
 </#if>
 /*ФИО*/
 <#if fio?has_content>
   AND upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
 </#if>
 /*Login_ID*/
 <#if loginId?has_content>
   AND person.LOGIN_ID = :extra_loginId
 </#if>
 /*серия и номер документа*/
 <#if documentSeries?has_content || documentNumber?has_content>
   AND person.ID in
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
 /*номер заявления*/
 <#if agreementNumber?has_content>
   AND person.AGREEMENT_NUMBER like :extra_like_agreementNumber
 </#if>
 /*состояние*/
 <#if state?has_content>
   AND person.STATE = :extra_state
 <#else>
   AND person.STATE in('A','T','E','W','S')
 </#if>
 /*блокированность клиента*/
 <#if blocked?has_content>
   AND (
        (:extra_blocked = -1 ) OR
        (:extra_blocked = 0 AND (select count(*) from LOGIN_BLOCK lb
                                  where person.LOGIN_ID=lb.LOGIN_ID
                                    and (lb.DATE_FROM <= :extra_blockedUntil and (lb.DATE_UNTIL is null or lb.DATE_UNTIL > :extra_blockedUntil))) = 0) OR
        (:extra_blocked = 1 AND (select count(*) from LOGIN_BLOCK lb
                                  where person.LOGIN_ID=lb.LOGIN_ID
                                    and (lb.DATE_FROM <= :extra_blockedUntil and (lb.DATE_UNTIL is null or lb.DATE_UNTIL > :extra_blockedUntil))) > 0)
       )
 </#if>
 /*Мобильный телефон*/
 <#if mobile_phone?has_content>
   AND upper (replace(replace(replace(replace(replace(personSubData.MOBILE_PHONE, '(', ''), '-', ''), ')', ''), ' ', ''), '+', '')) like upper (:extra_like_mobile_phone)
 </#if>
   ORDER BY person.SUR_NAME, person.FIRST_NAME, person.PATR_NAME ASC