SELECT
    {message.*}
FROM
    INFORM_MESSAGES message
WHERE
    (:extra_keyWord is null OR message.text like :extra_like_keyWord)
    AND  (:extra_name is null OR message.name like :extra_like_name)
    AND  ( message.state = 'TEMPLATE' )
<#if withAllowedDepartments && !allTbAccess>
    AND message.id in (
                     SELECT msgDepartments.MESSAGE_ID
                     FROM
                        MESSAGES_DEPARTMENTS msgDepartments
                     WHERE exists (select 1 from ALLOWED_DEPARTMENTS allowedDepartments
                                                   WHERE allowedDepartments.LOGIN_ID = :extra_employeeLoginId
                                                     AND (allowedDepartments.TB = msgDepartments.TB
                                                     OR  allowedDepartments.TB = '*')
                                                     AND allowedDepartments.OSB = '*'
                                                     AND allowedDepartments.VSP = '*'
                                                         )
    )
</#if>
ORDER BY message.ID DESC