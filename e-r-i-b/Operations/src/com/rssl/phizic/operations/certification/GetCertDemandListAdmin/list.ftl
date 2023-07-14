SELECT {certDemand.*},
               {person.*}
FROM   CERT_DEMANDS certDemand
       JOIN USERS person ON certDemand.LOGIN_ID = person.LOGIN_ID
WHERE person.STATE ='A'
    AND ( NOT certDemand.STATUS = 'E' )
<#if surName?has_content>
    AND upper(person.SUR_NAME) like upper(:extra_like_surName)
</#if>
<#if firstName?has_content>
    AND upper(person.FIRST_NAME) like upper(:extra_like_firstName)
</#if>
<#if patrName?has_content>
    AND upper(person.PATR_NAME) like upper(:extra_like_patrName)
</#if>
<#if status?has_content>
    AND certDemand.STATUS = :extra_status
</#if>
<#if signed?has_content>
    AND certDemand.signed = :extra_signed
</#if>
<#if issueDateTo?has_content>
    AND certDemand.ISSUE_DATE <= :extra_issueDateTo
</#if>
<#if issueDateFrom?has_content>
    AND :extra_issueDateFrom <= certDemand.ISSUE_DATE 
</#if>
  ORDER BY certDemand.STATUS