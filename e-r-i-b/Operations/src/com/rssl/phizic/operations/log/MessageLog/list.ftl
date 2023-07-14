SELECT logentry.*
FROM (
    -- Ограничивание объединения FINANCIAL_CODLOG и CODLOG по firstRow + maxRows
    SELECT o.* FROM (
        -- Сортировка объединения FINANCIAL_CODLOG и CODLOG
        SELECT o.* FROM (
        -- Ограничивание выборки из CODLOG по firstRow + maxRows
            SELECT o.* FROM (
                SELECT
                    logentry.ID ID,
                    logentry.START_DATE START_DATE,
                    logentry.SYST SYST,
                    logentry.MESSAGE_TYPE MESSAGE_TYPE,
                    logentry.APPLICATION APPLICATION,
                    logentry.DEPARTMENT_NAME DEPARTMENT_NAME,
                    logentry.SESSION_ID SESSION_ID,
                    translate_request.TRANSLATE TRANSLATE_MESS,
                   'OTHER' TYPE,
                    NODE_ID
                FROM CODLOG logentry
                LEFT JOIN MESSAGE_TRANSLATE translate_request ON translate_request.CODE = logentry.MESSAGE_TYPE
                WHERE
                    <#if operationUID?has_content>
                    <#--
                        Опорный объект: CL_OUID_INDEX
                        Предикаты доступа: "LOGENTRY"."OPERATION_UID"=:EXTRA_OPERATIONUID
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        logentry.OPERATION_UID = :extra_operationUID
                        AND (:extra_fromDate IS NULL OR logentry.START_DATE >= :extra_fromDate)
                        AND (:extra_toDate IS NULL OR logentry.START_DATE <= :extra_toDate)
                    <#else>
                        logentry.START_DATE >= :extra_fromDate
                        AND (logentry.START_DATE <= :extra_toDate)
                    </#if>

                    <#if application?has_content>
                    <#--
                        Опорный объект: CL_APP_DATE_INDEX
                        Предикаты доступа: "LOGENTRY"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND logentry.APPLICATION in (:extra_application)
                    </#if>

                    <#if fio?has_content>
                    <#--
                        Опорный объект: CL_FIO_DATE_INDEX
                        Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        Опорный объект: CL_LOGIN_DATE_INDEX
                        Предикаты доступа: "LOGENTRY"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND logentry.LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        Опорный объект: CL_DI_DATE_INDEX
                        Предикаты доступа: "LOGENTRY"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND logentry.DOC_NUMBER = :extra_number
                    </#if>

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

                    AND (:extra_birthday IS NULL OR logentry.BIRTHDAY= :extra_birthday)
                    AND (:extra_series IS NULL OR replace(logentry.DOC_SERIES,' ') like :extra_like_series)

                    <#-- Добавление if согласовано. Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет -->
                    <#if !allTbAccess>
                        AND (
                            (:extra_TB IS NULL AND logentry.TB IS NULL)
                            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                                      WHERE ad.LOGIN_ID = :extra_personLoginId
                                      AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                              logentry.TB||'|*|*',
                                                                              logentry.TB||'|'||logentry.OSB||'|*',
                                                                              logentry.TB||'|'||logentry.OSB||'|'||logentry.VSP)
                            )
                        )
                    </#if>

                    AND (:extra_TB IS NULL
                        OR (
                            logentry.TB = :extra_TB
                            AND (
                                (:extra_withChildren IS NOT NULL AND (:extra_OSB IS NULL OR logentry.OSB = :extra_OSB))
                                OR (
                                    :extra_withChildren IS NULL
                                    AND (
                                        :extra_OSB IS NOT NULL
                                        AND logentry.OSB = :extra_OSB
                                        AND (:extra_VSP IS NOT NULL AND logentry.VSP = :extra_VSP OR :extra_VSP IS NULL AND logentry.VSP IS NULL)
                                        OR
                                        :extra_OSB IS NULL
                                        AND logentry.OSB IS NULL
                                        AND logentry.VSP IS NULL
                                    )
                                )
                            )
                        )
                    )
            ) o WHERE ROWNUM < (:firstRow + :maxRows)

            UNION ALL
            -- Ограничивание выборки из FINANCIAL_CODLOG по firstRow + maxRows
            SELECT o.* FROM (
                SELECT
                    logentry.ID ID,
                    logentry.START_DATE START_DATE,
                    logentry.SYST SYST,
                    logentry.MESSAGE_TYPE MESSAGE_TYPE,
                    logentry.APPLICATION APPLICATION,
                    logentry.DEPARTMENT_NAME DEPARTMENT_NAME,
                    logentry.SESSION_ID SESSION_ID,
                    translate_request.TRANSLATE TRANSLATE_MESS,
                   'FINANCE' TYPE,
                    NODE_ID
                FROM FINANCIAL_CODLOG logentry
                LEFT JOIN MESSAGE_TRANSLATE translate_request ON translate_request.CODE = logentry.MESSAGE_TYPE
                WHERE
                    <#if operationUID?has_content>
                    <#--
                        Опорный объект: FCL_OUID_INDEX
                        Предикаты доступа: "LOGENTRY"."OPERATION_UID"=:EXTRA_OPERATIONUID
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        logentry.OPERATION_UID = :extra_operationUID
                        AND (:extra_fromDate IS NULL OR logentry.START_DATE >= :extra_fromDate)
                        AND (:extra_toDate IS NULL OR logentry.START_DATE <= :extra_toDate)
                    <#else>
                        logentry.START_DATE >= :extra_fromDate
                        AND (logentry.START_DATE <= :extra_toDate)
                    </#if>

                    <#if application?has_content>
                    <#--
                        Опорный объект: FCL_APP_DATE_INDEX
                        Предикаты доступа: "LOGENTRY"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND logentry.APPLICATION in (:extra_application)
                    </#if>

                    <#if fio?has_content>
                    <#--
                        Опорный объект: FCL_FIO_DATE_INDEX
                        Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        Опорный объект: FCL_LOGIN_DATE_INDEX
                        Предикаты доступа: "LOGENTRY"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND logentry.LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        Опорный объект: FCL_DI_DATE_INDEX
                        Предикаты доступа: "LOGENTRY"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND logentry.DOC_NUMBER = :extra_number
                    </#if>

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

                    AND (:extra_birthday IS NULL OR logentry.BIRTHDAY= :extra_birthday)
                    AND (:extra_series IS NULL OR replace(logentry.DOC_SERIES,' ') like :extra_like_series)

                    <#-- Добавление if согласовано. Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет -->
                    <#if !allTbAccess>
                        AND (
                            (:extra_TB IS NULL AND logentry.TB IS NULL)
                            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                                      WHERE ad.LOGIN_ID = :extra_personLoginId
                                      AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                              logentry.TB||'|*|*',
                                                                              logentry.TB||'|'||logentry.OSB||'|*',
                                                                              logentry.TB||'|'||logentry.OSB||'|'||logentry.VSP)
                            )
                        )
                    </#if>

                    AND (:extra_TB IS NULL
                        OR (
                            logentry.TB = :extra_TB
                            AND (
                                (:extra_withChildren IS NOT NULL AND (:extra_OSB IS NULL OR logentry.OSB = :extra_OSB))
                                OR (
                                    :extra_withChildren IS NULL
                                    AND (
                                        :extra_OSB IS NOT NULL
                                        AND logentry.OSB = :extra_OSB
                                        AND (:extra_VSP IS NOT NULL AND logentry.VSP = :extra_VSP OR :extra_VSP IS NULL AND logentry.VSP IS NULL)
                                        OR
                                        :extra_OSB IS NULL
                                        AND logentry.OSB IS NULL
                                        AND logentry.VSP IS NULL
                                    )
                                )
                            )
                        )
                    )
            ) o WHERE ROWNUM < (:firstRow + :maxRows)
        ) o
        ORDER BY START_DATE DESC, ID DESC
    ) o
    WHERE ROWNUM < (:firstRow + :maxRows)
) logentry


