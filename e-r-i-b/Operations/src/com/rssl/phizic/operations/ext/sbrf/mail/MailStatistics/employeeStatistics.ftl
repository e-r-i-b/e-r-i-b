select MAIL_ID, ARRIVE_TIME, PROCESSING_TIME, ST as STATE, EMPLOYEE_FIO, cc_area.AREA_NAME as AREA_NAME from
(
    select
        mail.id as MAIL_ID,
        mail.CREATION_DATE as ARRIVE_TIME,
        (select min(m.CREATION_DATE) from MAIL m where mail.id = m.PARENT_ID and m.state <> 'TEMPLATE' and m.STATE <> 'EMPLOYEE_DRAFT')  as processing_time,
        concat(concat(concat(employee.SUR_NAME, ' '), concat( employee.FIRST_NAME, ' ' )), employee.PATR_NAME) as EMPLOYEE_FIO,
        (select d.TB from DEPARTMENTS d where d.ID = employee.DEPARTMENT_ID) as E_DAPARTMENT,
        person.DEPARTMENT_ID as U_DEPARTMENT,
        recipient.STATE as ST
    from
        MAIL mail
    left join
        RECIPIENTS recipient on mail.ID = recipient.MAIL_ID
    left join
        EMPLOYEES employee on mail.EMPLOYEE_ID = employee.LOGIN_ID
    left join
        USERS person on mail.SENDER_ID = person.LOGIN_ID
    where
        recipient.DELETED = '0'
    AND
        (mail.STATE != 'CLIENT_DRAFT')
    AND
        mail.state <> 'TEMPLATE'
    AND
        mail.DIRECTION = 'ADMIN'
    AND recipient.STATE in (:extra_state)
    AND mail.TYPE in (:extra_type)
    <#if theme?has_content>
        AND (mail.SUBJECT_ID = :extra_theme)
    </#if>
    <#if response_method?has_content>
        AND (mail.RESPONSE_METHOD = :extra_response_method)
    </#if>
    AND mail.CREATION_DATE >= :extra_fromDate
    AND mail.CREATION_DATE <  :extra_toDate

    union all

    select
        mail.id as MAIL_ID,
        mail.CREATION_DATE as ARRIVE_TIME,
        null as PROCESSING_TIME,
        concat(concat(concat(employee.SUR_NAME, ' '), concat( employee.FIRST_NAME, ' ' )), employee.PATR_NAME) as EMPLOYEE_FIO,
        (select d.TB from DEPARTMENTS d where d.ID = employee.DEPARTMENT_ID) as E_DAPARTMENT,
        null as U_DEPARTMENT,
        (case when
                 mail.STATE = 'NEW' and PARENT_ID is null
            then
                'NEW_EPLOYEE_MAIL'
            else (case when
                mail.STATE = 'NEW' and PARENT_ID is not null
            then
                'ANSWER_EPLOYEE_MAIL'
            else (case when
                mail.STATE = 'EMPLOYEE_DRAFT' and PARENT_ID is null
            then
                 'NONE'
            else
                mail.STATE
                 end)
                 end)
        end) as ST
    from
        MAIL mail
    left join
        EMPLOYEES employee on mail.SENDER_ID = employee.LOGIN_ID
    where
        mail.state <> 'TEMPLATE'
    AND
        mail.DIRECTION = 'CLIENT'
    AND (
           (
               'NONE' in (:extra_state)
             AND
               mail.STATE = 'EMPLOYEE_DRAFT'
             AND
               mail.PARENT_ID is NULL
           )
           OR
           (
               'NEW_EPLOYEE_MAIL' in (:extra_state)
             AND
               mail.STATE = 'NEW'
             AND
               mail.PARENT_ID is NULL
           )
           OR
           (
               'ANSWER_EPLOYEE_MAIL' in (:extra_state)
             AND
               mail.STATE = 'NEW'
             AND
               mail.PARENT_ID is NOT NULL

           )
        )
    AND mail.DELETED = '0'
    AND mail.TYPE in (:extra_type)
    <#if theme?has_content>
         AND (mail.SUBJECT_ID = :extra_theme)
    </#if>
    <#if response_method?has_content>
         AND (mail.RESPONSE_METHOD = :extra_response_method)
    </#if>
         AND mail.CREATION_DATE >= :extra_fromDate
         AND mail.CREATION_DATE <  :extra_toDate
) stats
left join
    C_CENTER_AREAS_DEPARTMENTS cc_area_department on cc_area_department.TB = stats.E_DAPARTMENT
left join
    CONTACT_CENTER_AREAS cc_area on cc_area_department.C_C_AREA_ID = cc_area.ID
order by stats.ARRIVE_TIME