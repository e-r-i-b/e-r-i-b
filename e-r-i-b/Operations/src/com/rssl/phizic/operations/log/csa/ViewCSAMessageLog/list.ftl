SELECT
    logentry.*,
    translate_request.TRANSLATE TRANSLATE
FROM CSA_CODLOG logentry
LEFT JOIN CSA_MESSAGE_TRANSLATE translate_request ON translate_request.CODE = logentry.MESSAGE_TYPE
WHERE
    <#if logUID?has_content>
        logentry.LOG_UID = :extra_logUID
    <#else>
        logentry.START_DATE >= :extra_fromDate
        and (logentry.START_DATE <= :extra_toDate)
        <#if application?has_content>
            AND logentry.APPLICATION in (:extra_application)
        </#if>
        <#if system?has_content >
            and (logentry.SYST = :extra_system)
       </#if>
        <#if requestTag?has_content>
            and (logentry.MESSAGE_TYPE=:extra_requestTag or translate_request.TRANSLATE=:extra_requestTag)
        </#if>
        <#if requestWordWin1251?has_content>
            and (upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestWordWin1251)
                or upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestWordUTF8))
        </#if>
        <#if responceWordWin1251?has_content>
            and (upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_responceWordWin1251)
                or upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_responceWordUTF8))
        </#if>
        <#if requestresponceWordWin1251?has_content>
            and (upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestresponceWordWin1251) or upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_requestresponceWordWin1251)
                or upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestresponceWordUTF8) or upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_requestresponceWordUTF8))
        </#if>
        <#if fio?has_content>
            and upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
        </#if>
        <#if login?has_content>
            and logentry.LOGIN= :extra_login
        </#if>
        <#if birthday?has_content>
            and logentry.BIRTHDAY= :extra_birthday
        </#if>
        <#if number?has_content>
            and replace(logentry.DOC_NUMBER,' ','') = :extra_number
        </#if>
        <#if departmentCode?has_content>
            AND logentry.DEPARTMENT_CODE = :extra_departmentCode
        </#if>
        <#if promoterId?has_content>
            AND logentry.PROMOTER_ID=:extra_promoterId
        </#if>
        <#if ipAddress?has_content>
            AND logentry.IP_ADDRESS=:extra_ipAddress
        </#if>
        <#if mGUID?has_content>
            AND logentry.MGUID=:extra_mGUID
        </#if>
        <#if requestState?has_content>
            <#if requestState == "fail">
                AND logentry.ERROR_CODE != 0
            </#if>
            <#if requestState == "success">
                AND logentry.ERROR_CODE = 0
            </#if>
        </#if>
    </#if>
order by logentry.START_DATE desc

