SELECT {cert.*}
FROM
    CERT_OWN cert
    LEFT JOIN LOGINS login ON cert.LOGIN_ID = login.ID
    LEFT JOIN USERS person ON person.LOGIN_ID = cert.LOGIN_ID
WHERE 1=1
<#if firstName?has_content>
    AND upper(person.FIRST_NAME) like upper(:extra_like_firstName)
</#if>
<#if surName?has_content>
    AND upper(person.SUR_NAME) like upper(:extra_like_surName)
</#if>
<#if patrName?has_content>
    AND upper(person.PATR_NAME) like upper(:extra_like_patrName)
</#if>
<#if pinNumber?has_content>
    AND upper(login.USER_ID) LIKE upper(:extra_like_pinNumber)
</#if>
<#if status?has_content>
    AND cert.STATUS = :extra_status
<#else>
    cert.STATUS in ('N','A','E','B')
</#if>
<#if startDate?has_content>
    AND (:extra_startDate is null or :extra_startDate = '' or cert.START_DATE >= :extra_startDate)
</#if>
<#if endDate?has_content>
    AND (:extra_endDate is null or :extra_endDate = '' or :extra_endDate > cert.END_DATE)
</#if>
order by cert.STATUS