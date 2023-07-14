SELECT
    logentry.*,
    translate_request.TRANSLATE TRANSLATE
FROM GUEST_CSA_CODLOG logentry
LEFT JOIN CSA_MESSAGE_TRANSLATE translate_request ON translate_request.CODE = logentry.MESSAGE_TYPE
WHERE
    logentry.START_DATE >= :extra_fromDate
    and (logentry.START_DATE <= :extra_toDate)
    <#--
         Опорный объект: I_G_CSA_CL_PHONE_DATE
         Предикаты доступа: "LOGENTRY"."PHONE_NUMBER"=:EXTRA_PHONE AND SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE))
                            AND SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
         Кардинальность: все сообщения гостя с указанным номером телефона.(стоп условие-пагинация на странице)
     -->
    <#if phone?has_content>
        AND logentry.PHONE_NUMBER = :extra_phone
    </#if>
    <#--
         Опорный объект: I_G_CSA_CL_FIO
         Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE
                            UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%')
                            AND SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE))
                            AND SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
         Кардинальность: сообщения зарегистрированного гостя с указанным ФИО. Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
     -->
    <#if fio?has_content>
        AND upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>
    AND (:extra_ipAddress IS NULL OR logentry.IP_ADDRESS=:extra_ipAddress)
    AND (:extra_number IS NULL OR replace(logentry.DOC_NUMBER,' ','') = :extra_number)
    AND (:extra_application is NULL OR logentry.APPLICATION in (:extra_application))
    AND (:extra_system IS NULL OR logentry.SYST = :extra_system)
    AND (:extra_requestTag IS NULL OR (logentry.MESSAGE_TYPE=:extra_requestTag or translate_request.TRANSLATE=:extra_requestTag))
    AND (:extra_like_requestWordWin1251 is NULL OR (upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestWordWin1251)
        or upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestWordUTF8)))
    AND (:extra_like_responceWordWin1251 IS NULL OR (upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_responceWordWin1251)
        or upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_responceWordUTF8)))
    AND (:extra_like_requestresponceWordWin1251 IS NULL OR (upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestresponceWordWin1251) or upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_requestresponceWordWin1251)
        or upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestresponceWordUTF8) or upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_requestresponceWordUTF8)))
    AND (:extra_birthday IS NULL OR logentry.BIRTHDATE= :extra_birthday)
    AND (:extra_departmentCode IS NULL OR logentry.DEPARTMENT_CODE = :extra_departmentCode)
    AND (:extra_requestState IS NULL OR ((:extra_requestState = 'fail' AND logentry.ERROR_CODE != 0) OR (:extra_requestState = 'success' AND logentry.ERROR_CODE = 0)))
order by logentry.START_DATE desc

