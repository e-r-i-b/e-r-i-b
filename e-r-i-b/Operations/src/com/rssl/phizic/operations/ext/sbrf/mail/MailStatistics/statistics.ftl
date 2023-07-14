<#--
    Опорный объект: MAIL_CREATION_DATE_INDX
    Предикаты доступа:  access("MAIL"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
                        "MAIL"."CREATION_DATE"<TO_TIMESTAMP(:EXTRA_TODATE))
    Кардинальность: количество писем с FROMDATE по TODATE
-->
select stats.ST, count(*) as counter
from(
    SELECT
        CASE
            when mail.DIRECTION = 'CLIENT' and mail.deleted = '0' and mail.STATE = 'NEW' and mail.PARENT_ID is null then 'NEW_EPLOYEE_MAIL'
            when mail.DIRECTION = 'CLIENT' and mail.deleted = '0' and mail.STATE = 'NEW' and mail.PARENT_ID is not null then 'ANSWER_EPLOYEE_MAIL'
            when mail.DIRECTION = 'CLIENT' and mail.deleted = '0' and mail.STATE = 'EMPLOYEE_DRAFT' and mail.PARENT_ID is null then 'NONE'
            when mail.DIRECTION = 'ADMIN'  and recipient.deleted = '0' and mail.STATE != 'CLIENT_DRAFT' then recipient.STATE
            when mail.DIRECTION = 'CLIENT' and mail.DELETED = '1' and mail.STATE != 'EMPLOYEE_DRAFT' then 'DELETED'
            when mail.DIRECTION = 'ADMIN' and recipient.DELETED = '1' then 'DELETED'
            else mail.STATE
        END as ST
        <#--
            Опорный объект: MAIL_CREATION_DATE_INDX
            Предикаты доступа:  access("MAIL"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
                                "MAIL"."CREATION_DATE"<TO_TIMESTAMP(:EXTRA_TODATE))
            Кардинальность: количество писем с FROMDATE по TODATE
            Добавляется доступ к таблицам для каждой записи в письмах
                USERS по индексу IDX_USR_LOGIN(2 раза, так как клиента надо проверить как получателя или как отправителя),
                DEPARTMENTS по первичному ключу
        -->
        <#if user_TB?has_content>
            , CASE
                when mail.DIRECTION = 'CLIENT' then userRecipient.DEPARTMENT_ID
                else userSender.DEPARTMENT_ID
            END as USER_DEPARTMENT
        </#if>
    FROM
        MAIL mail
        LEFT JOIN RECIPIENTS recipient ON mail.ID = recipient.MAIL_ID
        <#--
            Опорный объект: MAIL_CREATION_DATE_INDX
            Предикаты доступа:  access("MAIL"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
                                "MAIL"."CREATION_DATE"<TO_TIMESTAMP(:EXTRA_TODATE))
            Кардинальность: количество писем с FROMDATE по TODATE
            Добавляется доступ к таблицам для каждой записи в письмах
                USERS по индексу IDX_USR_LOGIN(2 раза, так как клиента надо проверить как получателя или как отправителя),
                DEPARTMENTS по первичному ключу
        -->
        <#if user_TB?has_content>
            LEFT JOIN USERS userRecipient ON mail.RECIPIENT_ID = userRecipient.LOGIN_ID
            LEFT JOIN USERS userSender ON mail.SENDER_ID = userSender.LOGIN_ID
        </#if>
        <#--
            Опорный объект: MAIL_CREATION_DATE_INDX
            Предикаты доступа:  access("MAIL"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
                                "MAIL"."CREATION_DATE"<TO_TIMESTAMP(:EXTRA_TODATE))
            Кардинальность: количество писем с FROMDATE по TODATE
            Добавляется доступ к таблицам для каждой записи в письмах
                EMPLOYEES по индексу IDX_EMP_LOGIN
                DEPARTMENTS по первичному ключу
                поиск по индексу PK_C_CENTER_AREAS_DEPARTMENTS
        -->
        <#if area_uuid?has_content>
            LEFT JOIN EMPLOYEES employee ON mail.EMPLOYEE_ID = employee.LOGIN_ID
            LEFT JOIN DEPARTMENTS empDepartment on empDepartment.ID = employee.DEPARTMENT_ID
            LEFT JOIN C_CENTER_AREAS_DEPARTMENTS cc_area_dep on empDepartment.TB = cc_area_dep.TB
            INNER JOIN CONTACT_CENTER_AREAS area on cc_area_dep.C_C_AREA_ID = area.id
            AND area.UUID in (:extra_area_uuid)
        </#if>
    WHERE
        mail.TYPE in (:extra_type)
        AND (:extra_theme is null OR mail.SUBJECT_ID = :extra_theme)
        AND (:extra_response_method is null OR mail.RESPONSE_METHOD = :extra_response_method)
        AND mail.CREATION_DATE >= :extra_fromDate
        AND mail.CREATION_DATE <  :extra_toDate
        AND mail.state not in ('EMPLOYEE_DRAFT','CLIENT_DRAFT','TEMPLATE')
) stats
    <#--
        Опорный объект: MAIL_CREATION_DATE_INDX
        Предикаты доступа:  access("MAIL"."CREATION_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND
                            "MAIL"."CREATION_DATE"<TO_TIMESTAMP(:EXTRA_TODATE))
        Кардинальность: количество писем с FROMDATE по TODATE
        Добавляется доступ к таблицам для каждой записи в письмах
            USERS по индексу IDX_USR_LOGIN(2 раза, так как клиента надо проверить как получателя или как отправителя),
            DEPARTMENTS по первичному ключу
    -->
    <#if user_TB?has_content>
        INNER JOIN DEPARTMENTS userDepartment on userDepartment.ID = stats.USER_DEPARTMENT
        AND userDepartment.TB in (:extra_user_TB)
    </#if>
  WHERE
      ST in (:extra_state)


group by ST
order by ST