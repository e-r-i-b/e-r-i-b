<#--TODO описать план запроса, предикаты опорной сущности Исполнитель Михайлов О.Е. -->
SELECT
        mail1.id as mId,
        mail1.NUM as mNum,
        mail1.TYPE as mType,
        mail1.STATE as mState,
        mail1.SUBJECT as mSubject,
        mail1.CREATION_DATE as mCreationDate,
        concat(concat(concat(employees.SUR_NAME, ' '), concat( employees.FIRST_NAME, ' ' )), employees.PATR_NAME) as Employee_FIO,
        mail1.RECIPIENT_NAME as User_FIO,
		person.ID as personId,
		rmType.DESCRIPTION as mailTypeDescription,
		mlState.DESCRIPTION as mailStateDescription,
		login.USER_ID as lUserId,
		mResponseMethod.DESCRIPTION as mResponseMethodDescription,
		mailSubj.DESCRIPTION as msDescription,
        (SELECT department.NAME FROM DEPARTMENTS department
         WHERE department.TB = (select TB from DEPARTMENTS d where d.ID = person.DEPARTMENT_ID)
         AND nvl(department.OSB, 'NULL') = 'NULL'
         AND nvl(department.OFFICE, 'NULL') = 'NULL') as TB_NAME,
        cc_area.AREA_NAME as AREA_NAME
	FROM
           MAIL mail1
		LEFT JOIN EMPLOYEES employees ON employees.LOGIN_ID = mail1.SENDER_ID
		LEFT JOIN LOGINS login ON employees.LOGIN_ID = login.ID
		LEFT JOIN USERS person ON mail1.RECIPIENT_ID = person.LOGIN_ID
		JOIN MAIL_ENUMS_DICTIONARY rmType ON mail1.TYPE = rmType.CODE AND rmType.ENUM_NAME like '%MailType'
		JOIN MAIL_ENUMS_DICTIONARY mlState ON mail1.STATE = mlState.CODE AND mlState.ENUM_NAME like '%.MailState'
		LEFT JOIN MAIL_ENUMS_DICTIONARY mResponseMethod ON mail1.RESPONSE_METHOD = mResponseMethod.CODE AND mResponseMethod.ENUM_NAME like '%MailResponseMethod'
		LEFT JOIN MAIL_SUBJECTS mailSubj ON mail1.SUBJECT_ID = mailSubj.ID,
        C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area
	WHERE
        cc_area_dep.C_C_AREA_ID = cc_area.id
    AND
        cc_area_dep.TB = (select d.TB from DEPARTMENTS d where d.ID = employees.DEPARTMENT_ID)
    AND
	    mail1.id in
	(
           SELECT
               mail.id
           FROM
               MAIL mail
               LEFT JOIN RECIPIENTS recipients ON mail.ID = recipients.MAIL_ID
               LEFT JOIN USERS person ON recipients.RECIPIENT_ID = person.LOGIN_ID
               LEFT JOIN EMPLOYEES employees ON employees.LOGIN_ID = mail.SENDER_ID
               LEFT JOIN LOGINS login ON employees.LOGIN_ID = login.ID
               JOIN MAIL_ENUMS_DICTIONARY rmType ON mail.TYPE = rmType.CODE AND rmType.ENUM_NAME like '%MailType'
               JOIN MAIL_ENUMS_DICTIONARY mlState ON mail.STATE = mlState.CODE AND mlState.ENUM_NAME like '%.MailState'
               LEFT JOIN MAIL_ENUMS_DICTIONARY mResponseMethod ON mail.RESPONSE_METHOD = mResponseMethod.CODE AND mResponseMethod.ENUM_NAME like '%MailResponseMethod'
               LEFT JOIN MAIL_SUBJECTS mailSubj ON mail.SUBJECT_ID = mailSubj.ID
               <#if !allTbAccess>
                   LEFT JOIN DEPARTMENTS empDepartment ON employees.DEPARTMENT_ID = empDepartment.ID
               </#if>
               <#if user_TB?has_content>
               ,DEPARTMENTS user_TB
               </#if>
               ,C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area

           WHERE
                mail.DIRECTION = 'CLIENT'
           AND  (mail.STATE <> 'TEMPLATE')
           AND  (mail.STATE != 'EMPLOYEE_DRAFT' OR mail.PARENT_ID IS NULL)
           AND  mail.DELETED = '0'
           AND
                cc_area_dep.C_C_AREA_ID = cc_area.id
           AND
                cc_area_dep.TB = (select d.TB from DEPARTMENTS d where d.ID = employees.DEPARTMENT_ID)
           <#if user_TB?has_content>
           AND  user_TB.ID = (
                    SELECT department.id FROM DEPARTMENTS department
                    WHERE department.TB = (select TB from DEPARTMENTS d where d.ID = person.DEPARTMENT_ID)
                    AND nvl(department.OSB, 'NULL') = 'NULL'
                    AND nvl(department.OFFICE, 'NULL') = 'NULL'
                )

                 AND user_TB.TB = :extra_user_TB
           </#if>
           <#if area_uuid?has_content>
                AND cc_area.uuid = :extra_area_uuid
           </#if>
           <#if !allTbAccess>
                   AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                              WHERE ad.LOGIN_ID = :extra_employeeLoginId
                              AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (
                                                               empDepartment.TB||'|*|*',
                                                               empDepartment.TB||'|'||empDepartment.OSB||'|*',
                                                               empDepartment.TB||'|'||empDepartment.OSB||'|'||empDepartment.OFFICE,
                                                               '*|*|*')
              )
           </#if>
           <#if type?has_content>
               AND (mail.TYPE = :extra_type)
           </#if>
           <#if fromDate?has_content>
               AND (mail.CREATION_DATE >= :extra_fromDate)
           </#if>
           <#if toDate?has_content>
               AND (mail.CREATION_DATE < :extra_toDate)
           </#if>
           <#if subject?has_content>
               AND (upper(mail.SUBJECT) LIKE upper(:extra_like_subject))
           </#if>
           <#if num?has_content>
               AND (mail.NUM = :extra_num)
           </#if>
           <#if fioEmpl?has_content>
               AND upper(replace(replace(concat(concat(employees.SUR_NAME, employees.FIRST_NAME), employees.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fioEmpl, ' ', ''), '-', ''), '%'))
           </#if>
           <#if login?has_content>
               AND (login.USER_ID LIKE :extra_like_login)
           </#if>
           <#if response_method?has_content>
               AND (mail.RESPONSE_METHOD = :extra_response_method)
           </#if>
           <#if theme?has_content>
               AND (mail.SUBJECT_ID = :extra_theme)
           </#if>
           <#if isAttach?has_content>
               AND (:extra_isAttach = CASE
                                         WHEN mail.ATTACH IS NOT NULL THEN 'not null'
                                         WHEN mail.ATTACH IS NULL THEN 'null'
                                      END)
           </#if>
           <#if recipientType?has_content>
               AND
               <#if recipientType=="PERSON">
                   (mail.RECIPIENT_TYPE = 'PERSON')
                   <#if fio?has_content>
                       AND upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                   </#if>
               <#elseif recipientType=="GROUP">
                   (mail.RECIPIENT_TYPE = 'GROUP')
                   AND (upper(mail.RECIPIENT_NAME) LIKE upper(:extra_like_groupName))
               </#if>
           <#else>
               <#if fio?has_content>
                   AND upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
               </#if>
               <#if groupName?has_content>
                   AND (upper(mail.RECIPIENT_NAME) LIKE upper(:extra_like_groupName))
               </#if>
           </#if>

    )