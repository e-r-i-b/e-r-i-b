-- Ограничивание выборки из GUEST_CODLOG по firstRow + maxRows
 SELECT logentry.* FROM (
     SELECT
         logentry.ID ID,
         logentry.START_DATE START_DATE,
         logentry.SYST SYST,
         logentry.MESSAGE_TYPE MESSAGE_TYPE,
         logentry.APPLICATION APPLICATION,
         logentry.DEPARTMENT_NAME DEPARTMENT_NAME,
         logentry.SESSION_ID SESSION_ID,
         translate_request.TRANSLATE TRANSLATE_MESS,
         'GUEST' TYPE,
         NODE_ID NODE_ID,
         PHONE_NUMBER PHONE_NUMBER
     FROM GUEST_CODLOG logentry
     LEFT JOIN MESSAGE_TRANSLATE translate_request ON translate_request.CODE = logentry.MESSAGE_TYPE
     WHERE
         logentry.START_DATE >= :extra_fromDate
         AND (logentry.START_DATE <= :extra_toDate)
        <#--
            Опорный объект: I_G_CL_PHONE_DATE
            Предикаты доступа: "LOGENTRY"."PHONE_NUMBER"=:EXTRA_PHONE AND SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE))
                               AND SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
            Кардинальность: все сообщения гостя с указанным номером телефона.(стоп условие-пагинация на странице)
        -->
         <#if phone?has_content>
             AND logentry.PHONE_NUMBER = :extra_phone
         </#if>
         <#if fio?has_content>
         <#--
             Опорный объект: I_G_CL_FIO_DATE
             Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE
                                UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
             Кардинальность: сообщения зарегистрированного гостя с указанным ФИО. Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
         -->
             AND UPPER(REPLACE(REPLACE(logentry.SUR_NAME||logentry.FIRST_NAME||logentry.PATR_NAME,' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:extra_fio,' ',''),'-','')||'%')
         </#if>
         AND (:extra_number IS NULL OR logentry.DOC_NUMBER = :extra_number)

         AND (:extra_system IS NULL OR logentry.SYST = :extra_system)
         AND (:extra_requestTag IS NULL OR logentry.MESSAGE_TYPE = :extra_requestTag OR translate_request.TRANSLATE = :extra_requestTag)
         AND (:extra_requestWordWin1251 IS NULL
              OR upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestWordWin1251)
              OR upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestWordUTF8))
         AND (:extra_responceWordWin1251 IS NULL
             OR upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_responceWordWin1251)
             OR upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_responceWordUTF8))
         AND (:extra_requestresponceWordWin1251 IS NULL
             OR upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestresponceWordWin1251)
             OR upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_requestresponceWordWin1251)
             OR upper(logentry.MESSAGE_DEMAND) like upper(:extra_like_requestresponceWordUTF8)
             OR upper(logentry.MESSAGE_ANSWER) like upper(:extra_like_requestresponceWordUTF8))

         AND (:extra_birthday IS NULL OR logentry.BIRTHDATE= :extra_birthday)
         AND (:extra_series IS NULL OR replace(logentry.DOC_SERIES,' ') like :extra_like_series)

         <#-- Добавление if согласовано. Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет -->
         <#if !allTbAccess>
             AND (
                 (:extra_TB IS NULL AND logentry.TB IS NULL)
                 OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                           WHERE ad.LOGIN_ID = :extra_personLoginId
                           AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                   logentry.TB||'|*|*')
                 )
             )
         </#if>
         AND (:extra_TB IS NULL OR (logentry.TB = :extra_TB))
         ) logEntry
WHERE ROWNUM < (:firstRow + :maxRows)
ORDER BY START_DATE DESC, ID DESC


