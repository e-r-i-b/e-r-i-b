SELECT
    {person.*},
    {login.*},
    {simpleScheme.*},
    {activeDocument.*}
FROM
    USERS person
    JOIN LOGINS login ON person.LOGIN_ID = login.id

    LEFT JOIN SCHEMEOWNS    simpleOwn    ON person.LOGIN_ID     = simpleOwn.LOGIN_ID and simpleOwn.ACCESS_TYPE='simple'
    LEFT JOIN ACCESSSCHEMES simpleScheme ON simpleOwn.SCHEME_ID = simpleScheme.ID

    LEFT JOIN DOCUMENTS activeDocument ON activeDocument.PERSON_ID = person.ID and activeDocument.DOC_MAIN = 1 and activeDocument.DOC_IDENTIFY = 1
    <#if !allTbAccess>
        LEFT JOIN DEPARTMENTS department on person.DEPARTMENT_ID = department.id
    </#if>
WHERE person.STATE = 'D'
    <#if fio?has_content>
        and upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>

    <#if documentSeries?has_content>
        and activeDocument.DOC_SERIES like concat(:extra_documentSeries, '%')
    </#if>

    <#if documentNumber?has_content>
        and activeDocument.DOC_NUMBER like concat(:extra_documentNumber, '%')
    </#if>

    <#if loginId?has_content>
        and login.ID = :extra_loginId
    </#if>

    <#if agreementNumber?has_content>
        and person.AGREEMENT_NUMBER = :extra_agreementNumber
    </#if>

    and person.TRUSTING_ID IS NULL
    <#if !allTbAccess>
    and exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                         WHERE ad.LOGIN_ID = :extra_employeeLoginId
                         AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (department.TB||'|*|*',
                                                                 department.TB||'|'||department.OSB||'|*',
                                                                 department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                                 '*|*|*')
                  ) 
    </#if>
ORDER BY person.SUR_NAME, person.FIRST_NAME, person.PATR_NAME ASC