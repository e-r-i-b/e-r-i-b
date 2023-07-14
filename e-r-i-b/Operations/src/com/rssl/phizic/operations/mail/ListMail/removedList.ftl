<#--TODO описать план запроса, предикаты опорной сущности Исполнитель Михайлов О.Е. -->
SELECT
            mail.id as mId,
            mail.PARENT_ID as mParentId,
            mail.NUM as mNum,
            mail.TYPE as mType,
            mail.STATE as mState,
            mail.SUBJECT as mSubject,
            mail.BODY as mBody,
            mail.CREATION_DATE as mCreationDate,
            mail.SENDER_ID as mSender,
            mail.EMPLOYEE_ID as mEmployee,
            mail.DIRECTION as mDirection,
            mail.PHONE as mPhone,
            mail.E_MAIL as mEMail,
            mail.IMPORTANT as mImportant,
            mail.FILE_NAME as mFileName,
            mail.ATTACH as mData,
            mail.DELETED as mDeleted,
            mail.IS_SHOW as mShow,
            mail.RECIPIENT_TYPE as rtype,
            mail.RECIPIENT_ID as personId,
            mail.RESPONSE_METHOD as mResponseMethod,
            mail.SUBJECT_ID as mTheme,
            mail.RESPONSE_TIME as respTime,
            'PERSON' as ctype,
            login.USER_ID as lUserId,
            concat(concat(concat(employees.SUR_NAME, ' '), concat( employees.FIRST_NAME, ' ' )), employees.PATR_NAME) as Employee_FIO,
            mail.RECIPIENT_NAME as User_FIO,
            rmState.DESCRIPTION as mailStateDescription,
            rmType.DESCRIPTION as mailTypeDescription,
            rmDirect.DESCRIPTION as mailDirectionDescription,
            rmState.DESCRIPTION as rState,
            mResponseMethod.DESCRIPTION as mResponseMethodDescription,
            mailSubj.DESCRIPTION as msDescription,
            user_TB.NAME as TB_NAME,
            cc_area.AREA_NAME as AREA_NAME
        FROM
            MAIL mail LEFT JOIN RECIPIENTS recipient ON mail.ID = recipient.MAIL_ID
            JOIN USERS person ON mail.SENDER_ID = person.LOGIN_ID
            LEFT JOIN EMPLOYEES employees ON employees.LOGIN_ID = mail.EMPLOYEE_ID
            LEFT JOIN LOGINS login ON employees.LOGIN_ID = login.ID
            JOIN MAIL_ENUMS_DICTIONARY rmState ON recipient.STATE = rmState.CODE AND rmState.ENUM_NAME like '%RecipientMailState'
            JOIN MAIL_ENUMS_DICTIONARY rmType ON mail.TYPE = rmType.CODE AND rmType.ENUM_NAME like '%.MailType'
            JOIN MAIL_ENUMS_DICTIONARY rmDirect ON mail.DIRECTION = rmDirect.CODE AND rmDirect.ENUM_NAME like '%MailDirection'
            LEFT JOIN MAIL_SUBJECTS mailSubj ON mail.SUBJECT_ID = mailSubj.ID
            LEFT JOIN MAIL_ENUMS_DICTIONARY mResponseMethod ON mail.RESPONSE_METHOD = mResponseMethod.CODE AND mResponseMethod.ENUM_NAME like '%MailResponseMethod'
            <#if !allTbAccess>
                LEFT JOIN DEPARTMENTS recipientDepartment ON mail.RECIPIENT_ID = recipientDepartment.ID
            </#if>
            ,DEPARTMENTS user_TB, C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area
   		WHERE
              (mail.STATE <> 'TEMPLATE')
              AND (mail.STATE != 'EMPLOYEE_DRAFT' OR mail.PARENT_ID IS NULL)
              AND (recipient.DELETED='1')
              AND
                  cc_area_dep.C_C_AREA_ID = cc_area.id
              AND
                  cc_area_dep.TB = (select d.TB FROM DEPARTMENTS d where d.ID = employees.DEPARTMENT_ID)
              AND user_TB.ID = (
                  SELECT department.id FROM DEPARTMENTS department
                  WHERE department.TB = (select d.TB FROM DEPARTMENTS d where d.ID = person.DEPARTMENT_ID)
                  AND nvl(department.OSB, 'NULL') = 'NULL'
                  AND nvl(department.OFFICE, 'NULL') = 'NULL')
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
              <#if mailType?has_content>
                  AND (mail.DIRECTION = :extra_mailType)
              </#if>
              <#if clientType?has_content>
                  AND (mail.RECIPIENT_TYPE = :extra_clientType)
              </#if>
              <#if name?has_content>
                  AND (upper(person.FIRST_NAME) LIKE upper(:extra_like_name) OR upper(person.SUR_NAME) LIKE upper(:extra_like_name) OR upper(person.PATR_NAME) LIKE upper(:extra_like_name))
              </#if>
              <#if firstNameEmpl?has_content>
                  AND (upper(employees.FIRST_NAME) LIKE upper(:extra_like_firstNameEmpl))
              </#if>
              <#if surNameEmpl?has_content>
                  AND (upper(employees.SUR_NAME) LIKE upper(:extra_like_surNameEmpl))
              </#if>
              <#if surNameEmpl?has_content>
                  AND (upper(employees.PATR_NAME) LIKE upper(:extra_like_partNameEmpl))
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

        UNION ALL
        SELECT
            mail1.id as mId,
            mail1.PARENT_ID as mParentId,
            mail1.NUM as mNum,
            mail1.TYPE as mType,
            mail1.STATE as mState,
            mail1.SUBJECT as mSubject,
            mail1.BODY as mBody,
            mail1.CREATION_DATE as mCreationDate,
            mail1.SENDER_ID as mSender,
            mail1.EMPLOYEE_ID as mEmployee,
            mail1.DIRECTION as mDirection,
            mail1.PHONE as mPhone,
            mail1.E_MAIL as mEMail,
            mail1.IMPORTANT as mImportant,
            mail1.FILE_NAME as mFileName,
            mail1.ATTACH as mData,
            mail1.DELETED as mDeleted,
            mail1.IS_SHOW as mShow,
            mail1.RECIPIENT_TYPE as rtype,
            mail1.RECIPIENT_ID as personId,
            mail1.RESPONSE_METHOD as mResponseMethod,
            mail1.SUBJECT_ID as mTheme,
            mail1.RESPONSE_TIME as respTime,
            mail1.RECIPIENT_TYPE as ctype,
            login.USER_ID as lUserId,
            concat(concat(concat(employees.SUR_NAME, ' '), concat( employees.FIRST_NAME, ' ' )), employees.PATR_NAME) as Employee_FIO,
            mail1.RECIPIENT_NAME as User_FIO,
            '' as mailStateDescription,
            rmType.DESCRIPTION as mailTypeDescription,
            rmDirect.DESCRIPTION as mailDirectionDescription,
            '' as rState,
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
			JOIN MAIL_ENUMS_DICTIONARY rmDirect ON mail1.DIRECTION = rmDirect.CODE AND rmDirect.ENUM_NAME like '%MailDirection'
			LEFT JOIN MAIL_ENUMS_DICTIONARY mResponseMethod ON mail1.RESPONSE_METHOD = mResponseMethod.CODE AND mResponseMethod.ENUM_NAME like '%MailResponseMethod'
			LEFT JOIN MAIL_SUBJECTS mailSubj ON mail1.SUBJECT_ID = mailSubj.ID,
            C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area
        WHERE
           cc_area_dep.C_C_AREA_ID = cc_area.id
        AND
            cc_area_dep.TB = (select d.TB FROM DEPARTMENTS d where d.ID = employees.DEPARTMENT_ID)
        AND
            mail1.ID in
            (
            SELECT
                mail.id
                FROM
                    MAIL mail LEFT JOIN RECIPIENTS recipient ON mail.ID = recipient.MAIL_ID
                    LEFT JOIN USERS person ON recipient.RECIPIENT_ID = person.LOGIN_ID
                    LEFT JOIN EMPLOYEES employees ON employees.LOGIN_ID = mail.SENDER_ID
                    LEFT JOIN LOGINS login ON employees.LOGIN_ID = login.ID
                    JOIN MAIL_ENUMS_DICTIONARY rmType ON mail.TYPE = rmType.CODE AND rmType.ENUM_NAME like '%MailType'
                    JOIN MAIL_ENUMS_DICTIONARY rmDirect ON mail.DIRECTION = rmDirect.CODE AND rmDirect.ENUM_NAME like '%MailDirection'
                    LEFT JOIN MAIL_SUBJECTS mailSubj ON mail.SUBJECT_ID = mailSubj.ID
                    <#if !allTbAccess>
                        LEFT JOIN DEPARTMENTS empDepartment ON employees.DEPARTMENT_ID = empDepartment.ID
                    </#if>
                    <#if user_TB?has_content>,DEPARTMENTS user_TB</#if>, C_CENTER_AREAS_DEPARTMENTS cc_area_dep, CONTACT_CENTER_AREAS cc_area
                WHERE
                      (mail.DIRECTION = 'CLIENT')
                      AND (mail.DELETED = '1')
                      AND (mail.STATE <> 'TEMPLATE')
                      AND (mail.STATE != 'EMPLOYEE_DRAFT' OR mail.PARENT_ID IS NULL)
                      AND
                          cc_area_dep.C_C_AREA_ID = cc_area.id
                      AND
                          cc_area_dep.TB = (select d.TB FROM DEPARTMENTS d where d.ID = employees.DEPARTMENT_ID)
                      <#if user_TB?has_content>
                      AND user_TB.ID = (SELECT department.id FROM DEPARTMENTS department
                          WHERE department.TB = (select d.TB FROM DEPARTMENTS d where d.ID = person.DEPARTMENT_ID)
                          AND nvl(department.OSB, 'NULL') = 'NULL'
                          AND nvl(department.OFFICE, 'NULL') = 'NULL')
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
                      <#if mailType?has_content>
                          AND (mail.DIRECTION = :extra_mailType)
                      </#if>
                      <#if firstNameEmpl?has_content>
                          AND (upper(employees.FIRST_NAME) LIKE upper(:extra_like_firstNameEmpl))
                      </#if>
                      <#if surNameEmpl?has_content>
                          AND (upper(employees.SUR_NAME) LIKE upper(:extra_like_surNameEmpl))
                      </#if>
                      <#if partNameEmpl?has_content>
                          AND (upper(employees.PATR_NAME) LIKE upper(:extra_like_partNameEmpl))
                      </#if>
                      <#if login?has_content>
                          AND (login.USER_ID LIKE :extra_like_login)
                      </#if>
                      <#if theme?has_content>
                          AND (mail.SUBJECT_ID = :extra_theme)
                      </#if>
                      <#if response_method?has_content>
                          AND (mail.RESPONSE_METHOD = :extra_response_method)
                      </#if>
                      <#if recipientType?has_content>
                           AND
                           <#if recipientType=="PERSON">
                                (mail.RECIPIENT_TYPE = 'PERSON')
                                <#if name?has_content>
                                    AND (upper(person.FIRST_NAME) LIKE upper(:extra_like_name) OR upper(person.SUR_NAME) LIKE upper(:extra_like_name) OR upper(person.PATR_NAME) LIKE upper(:extra_like_name))
                                </#if>
                           <#elseif recipientType=="GROUP">
                               (mail.RECIPIENT_TYPE = 'GROUP')
                               <#if name?has_content>
                                   AND (upper(mail.RECIPIENT_NAME) LIKE upper(:extra_like_name))
                               </#if>
                           </#if>
                      <#else>
                           <#if name?has_content>
                              AND upper(person.FIRST_NAME) LIKE upper(:extra_like_name) OR upper(person.SUR_NAME) LIKE upper(:extra_like_name) OR upper(person.PATR_NAME) LIKE upper(:extra_like_name) OR upper(mail.RECIPIENT_NAME) LIKE upper(:extra_like_name)
                           </#if>
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
                      <#if user_TB?has_content>
                            AND user_TB.TB = :extra_user_TB
                      </#if>
                      <#if area_uuid?has_content>
                           AND cc_area.uuid = :extra_area_uuid
                      </#if>
            )