<#--TODO описать план запроса, предикаты опорной сущности Исполнитель Михайлов О.Е. -->
SELECT
    mail.id as mId,
    mail.NUM as mNum,
    mail.TYPE as mType,
    mail.SUBJECT as mSubject,
    mail.CREATION_DATE as mCreationDate,
    concat(concat(concat(employee.SUR_NAME, ' '), concat( employee.FIRST_NAME, ' ' )), employee.PATR_NAME) as Employee_FIO,
    person.ID as User_ID,
    concat(concat(concat(person.SUR_NAME, ' '), concat( person.FIRST_NAME, ' ' )), person.PATR_NAME) as User_FIO,
    rmState.DESCRIPTION as mailStateDescription,
    rmType.DESCRIPTION as mailTypeDescription,
    login.USER_ID as lUserId,
    mResponseMethod.DESCRIPTION as mResponseMethodDescription,
    mailSubj.DESCRIPTION as msDescription,
    user_TB.NAME as TB_NAME,
    cc_area.AREA_NAME as AREA_NAME,
    recipient.STATE as recipientState
FROM
    MAIL mail
    JOIN USERS person ON mail.SENDER_ID = person.LOGIN_ID
    LEFT JOIN EMPLOYEES employee ON mail.EMPLOYEE_ID = employee.LOGIN_ID
    LEFT JOIN LOGINS login ON login.ID = employee.LOGIN_ID
    LEFT JOIN RECIPIENTS recipient ON mail.ID = recipient.MAIL_ID
    JOIN MAIL_ENUMS_DICTIONARY rmState ON recipient.STATE = rmState.CODE AND rmState.ENUM_NAME like '%RecipientMailState'
    LEFT JOIN MAIL_ENUMS_DICTIONARY mResponseMethod ON mail.RESPONSE_METHOD = mResponseMethod.CODE AND mResponseMethod.ENUM_NAME like '%MailResponseMethod'
    JOIN MAIL_ENUMS_DICTIONARY rmType ON mail.TYPE = rmType.CODE AND rmType.ENUM_NAME like '%MailType'
    LEFT JOIN MAIL_SUBJECTS mailSubj ON mail.SUBJECT_ID = mailSubj.ID
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS recipientDepartment ON mail.RECIPIENT_ID = recipientDepartment.ID
    </#if>
    ,DEPARTMENTS user_TB, C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area
WHERE
    (mail.STATE <> 'CLIENT_DRAFT')
    AND (mail.STATE <> 'TEMPLATE')
    AND mail.DIRECTION = 'ADMIN'
    AND recipient.DELETED = '0'
    AND person.STATUS IN ('A', 'T')
    AND (recipient.STATE IN (:extra_state))
    AND
       cc_area_dep.C_C_AREA_ID = cc_area.id
    AND
       cc_area_dep.TB = (
           SELECT
               d.TB
           FROM
               DEPARTMENTS d WHERE d.ID = employee.DEPARTMENT_ID
       )
    AND user_TB.ID = (SELECT department.id FROM DEPARTMENTS department
        WHERE department.TB = (select d.TB from DEPARTMENTS d where d.ID = person.DEPARTMENT_ID)
        AND nvl(department.OSB, 'NULL') = 'NULL'
        AND nvl(department.OFFICE, 'NULL') = 'NULL')
    <#if fio?has_content>
        AND upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>
    <#if fioEmpl?has_content>
        AND upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fioEmpl, ' ', ''), '-', ''), '%'))
    </#if>
    <#if employeeLogin?has_content>
        AND (login.USER_ID LIKE :extra_like_employeeLogin OR login.USER_ID IS NULL)
    </#if>
    <#if fromDate?has_content>
        AND (mail.CREATION_DATE >= :extra_fromDate)
    </#if>
    <#if toDate?has_content>
        AND (mail.CREATION_DATE < :extra_toDate)
    </#if>
    <#if type?has_content>
        AND (mail.TYPE = :extra_type)
    </#if>
    <#if subject?has_content>
        AND (upper(mail.SUBJECT) like upper(:extra_subject))
    </#if>
    <#if num?has_content>
        AND (mail.NUM = :extra_num)
    </#if>
    <#if isAttach?has_content>
      AND (:extra_isAttach = CASE
                                 WHEN mail.ATTACH IS NOT NULL THEN 'not null'
                                 WHEN mail.ATTACH IS NULL THEN 'null'
                             END)
    </#if>
    <#if response_method?has_content>
        AND (mail.RESPONSE_METHOD = :extra_response_method)
    </#if>
    <#if theme?has_content>
        AND (mail.SUBJECT_ID = :extra_theme)
    </#if>
    <#if !allTbAccess>
        AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                   WHERE ad.LOGIN_ID = :extra_employeeLoginId
                   AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                                        recipientDepartment.TB||'|*|*',
                                                        recipientDepartment.TB||'|'||recipientDepartment.OSB||'|*',
                                                        recipientDepartment.TB||'|'||recipientDepartment.OSB||'|'||recipientDepartment.OFFICE,
                                                        '*|*|*')
              )
    </#if>
    <#if user_TB?has_content>
         AND user_TB.TB = :extra_user_TB
    </#if>
    <#if area_uuid?has_content>
        AND cc_area.uuid = :extra_area_uuid
    </#if>
union all
SELECT
mail.id as mId,
    mail.NUM as mNum,
    mail.TYPE as mType,
    mail.SUBJECT as mSubject,
    mail.CREATION_DATE as mCreationDate,
    concat(concat(concat(employee.SUR_NAME, ' '), concat( employee.FIRST_NAME, ' ' )), employee.PATR_NAME) as Employee_FIO,
    person.ID as User_ID,
    concat(concat(concat(person.SUR_NAME, ' '), concat( person.FIRST_NAME, ' ' )), person.PATR_NAME) as User_FIO,
    rmState.DESCRIPTION as mailStateDescription,
    rmType.DESCRIPTION as mailTypeDescription,
    login.USER_ID as lUserId,
    mResponseMethod.DESCRIPTION as mResponseMethodDescription,
    mailSubj.DESCRIPTION as msDescription,
    user_TB.NAME as TB_NAME,
    '' as AREA_NAME,
    recipient.STATE as recipientState
FROM
    MAIL mail
    JOIN USERS person ON mail.SENDER_ID = person.LOGIN_ID
    LEFT JOIN EMPLOYEES employee ON mail.EMPLOYEE_ID = employee.LOGIN_ID
    LEFT JOIN LOGINS login ON login.ID = employee.LOGIN_ID
    LEFT JOIN RECIPIENTS recipient ON mail.ID = recipient.MAIL_ID
    JOIN MAIL_ENUMS_DICTIONARY rmState ON recipient.STATE = rmState.CODE AND rmState.ENUM_NAME like '%RecipientMailState'
    LEFT JOIN MAIL_ENUMS_DICTIONARY mResponseMethod ON mail.RESPONSE_METHOD = mResponseMethod.CODE AND mResponseMethod.ENUM_NAME like '%MailResponseMethod'
    JOIN MAIL_ENUMS_DICTIONARY rmType ON mail.TYPE = rmType.CODE AND rmType.ENUM_NAME like '%MailType'
    LEFT JOIN MAIL_SUBJECTS mailSubj ON mail.SUBJECT_ID = mailSubj.ID
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS recipientDepartment ON mail.RECIPIENT_ID = recipientDepartment.ID
    </#if>
    ,DEPARTMENTS user_TB
WHERE
    (mail.STATE <> 'CLIENT_DRAFT')
    AND (mail.STATE <> 'TEMPLATE')
    AND mail.DIRECTION = 'ADMIN'
    AND recipient.DELETED = '0'
    AND person.STATUS IN ('A', 'T')
    AND (recipient.STATE ='NEW') AND ('NEW' IN (:extra_state))
    AND employee.LOGIN_ID is null
    AND user_TB.ID = (SELECT department.id FROM DEPARTMENTS department
        WHERE department.TB = (select d.TB from DEPARTMENTS d where d.ID = person.DEPARTMENT_ID)
        AND nvl(department.OSB, 'NULL') = 'NULL'
        AND nvl(department.OFFICE, 'NULL') = 'NULL')
    <#if fio?has_content>
        AND upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>
    <#if fioEmpl?has_content>
        AND (
              (
                   upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fioEmpl, ' ', ''), '-', ''), '%'))
              ) OR (
                   upper(replace(replace(concat(concat(employee.SUR_NAME, employee.FIRST_NAME), employee.PATR_NAME), ' ', ''), '-', '')) is null
              )
        )
    </#if>
    <#if employeeLogin?has_content>
        AND (login.USER_ID LIKE :extra_like_employeeLogin OR login.USER_ID IS NULL)
    </#if>
    <#if fromDate?has_content>
        AND (mail.CREATION_DATE >= :extra_fromDate)
    </#if>
    <#if toDate?has_content>
        AND (mail.CREATION_DATE < :extra_toDate)
    </#if>
    <#if type?has_content>
        AND (mail.TYPE = :extra_type)
    </#if>
    <#if subject?has_content>
        AND (upper(mail.SUBJECT) like upper(:extra_subject))
    </#if>
    <#if num?has_content>
        AND (mail.NUM = :extra_num)
    </#if>
    <#if isAttach?has_content>
      AND (:extra_isAttach = CASE
                                 WHEN mail.ATTACH IS NOT NULL THEN 'not null'
                                 WHEN mail.ATTACH IS NULL THEN 'null'
                             END)
    </#if>
    <#if response_method?has_content>
        AND (mail.RESPONSE_METHOD = :extra_response_method)
    </#if>
    <#if theme?has_content>
        AND (mail.SUBJECT_ID = :extra_theme)
    </#if>
    <#if !allTbAccess>
        AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                   WHERE ad.LOGIN_ID = :extra_employeeLoginId
                   AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                                        recipientDepartment.TB||'|*|*',
                                                        recipientDepartment.TB||'|'||recipientDepartment.OSB||'|*',
                                                        recipientDepartment.TB||'|'||recipientDepartment.OSB||'|'||recipientDepartment.OFFICE,
                                                        '*|*|*')
              )
    </#if>
    <#if user_TB?has_content>
         AND user_TB.TB = :extra_user_TB
    </#if>
