SELECT logentry.*
FROM (
    -- Ограничивание объединения SYSTEMLOG, CODLOG и USERLOG по firstRow + maxRows
    SELECT o.* FROM (
        -- Сортировка объединения SYSTEMLOG, CODLOG и USERLOG
        SELECT o.* FROM (
            -- Ограничивание выборки из SYSTEMLOG по firstRow + maxRows
            <#if showSystemLog?has_content && showSystemLog>
            SELECT o.* FROM (
                -- Выборка из SYSTEMLOG
                SELECT
                    'SYSTEM' AS TYPE,
                    ID,
                    LOGIN_ID,
                    START_DATE,
                    APPLICATION,
                    '' AS OPERATION,
                    IP_ADDRESS,
                    SESSION_ID,
                    MSG_LEVEL AS MESSAGE_TYPE,
                    MESSAGE_SOURCE AS MODULE,
                    '' AS REQUEST_TYPE,
                    '' AS REQUEST_TYPE_TRANSLATE,
                    '' AS SYSTEM,
                    '0' as paramsEmpty,
                    SUR_NAME,
                    PATR_NAME,
                    FIRST_NAME,
                    DEPARTMENT_NAME,
                    NODE_ID
                FROM SYSTEMLOG
                WHERE
                    (START_DATE >= :extra_fromDate)
                     AND (START_DATE <= :extra_toDate)

                    <#if application?has_content>
                    <#--
                        Опорный объект: SL_APP_DATE_INDEX
                        Предикаты доступа: "SYSTEMLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                     </#if>

                    <#if sessionId?has_content>
                    <#--
                        Опорный объект: SL_SESSION_DATE_INDEX
                        Предикаты доступа: "SYSTEMLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        Опорный объект: SL_FIO_DATE_INDEX
                        Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        Опорный объект: SL_LOGIN_DATE_INDEX
                        Предикаты доступа: "SYSTEMLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        Опорный объект: SL_DI_DATE_INDEX
                        Предикаты доступа: "SYSTEMLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Зависит от :extra_fromDate и :extra_toDate
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- Добавление if согласовано. Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет -->
                    <#if !allTbAccess>
                        AND (
                            (:extra_TB IS NULL AND TB IS NULL)
                            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                                      WHERE ad.LOGIN_ID = :extra_personLoginId
                                      AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                              TB||'|*|*',
                                                                              TB||'|'||OSB||'|*',
                                                                              TB||'|'||OSB||'|'||VSP)
                                      )
                        )
                    </#if>

                    AND (:extra_TB IS NULL
                        OR (
                            TB = :extra_TB
                            AND (
                                (:extra_withChildren IS NOT NULL AND (:extra_OSB IS NULL OR OSB = :extra_OSB))
                                OR (
                                    :extra_withChildren IS NULL
                                    AND (
                                        :extra_OSB IS NOT NULL
                                        AND OSB = :extra_OSB
                                        AND (:extra_VSP IS NOT NULL AND VSP = :extra_VSP OR :extra_VSP IS NULL AND VSP IS NULL)
                                        OR
                                        :extra_OSB IS NULL
                                        AND OSB IS NULL
                                        AND VSP IS NULL
                                    )
                                )
                            )
                        )
                    )

            ) o WHERE ROWNUM < (:firstRow + :maxRows)
            </#if>

            <#if showSystemLog?has_content && showSystemLog && (showMessageLog?has_content && showMessageLog || showUserLog?has_content && showUserLog)>
                UNION ALL
            </#if>

            <#if showMessageLog?has_content && showMessageLog >
            -- Ограничивание выборки из CODLOG по firstRow + maxRows
            SELECT o.* FROM (
                -- Выборка из CODLOG
                SELECT
                    'MESSAGE' AS TYPE,
                    CODLOG.ID,
                    LOGIN_ID,
                    START_DATE,
                    APPLICATION,
                    '' AS OPERATION,
                    '' AS IP_ADDRESS,
                    decode(:extra_sessionId, NULL, '', SESSION_ID) AS SESSION_ID,
                    '' AS MESSAGE_TYPE,
                    NULL AS MODULE,
                    MESSAGE_TYPE AS REQUEST_TYPE,
                    message_translate_request.translate AS REQUEST_TYPE_TRANSLATE,
                    SYST AS SYSTEM,
                    '0' as paramsEmpty,
                    SUR_NAME,
                    PATR_NAME,
                    FIRST_NAME,
                    DEPARTMENT_NAME,
                    NODE_ID
                FROM CODLOG
                LEFT JOIN MESSAGE_TRANSLATE message_translate_request ON message_translate_request.CODE = MESSAGE_TYPE
                WHERE
                    (START_DATE >= :extra_fromDate)
                    AND (START_DATE <= :extra_toDate)

                    <#if application?has_content>
                    <#--
                        Опорный объект: CL_APP_DATE_INDEX
                        Предикаты доступа: "CODLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                    </#if>

                    <#if sessionId?has_content>
                    <#--
                        Опорный объект: CL_SESSION_DATE_INDEX
                        Предикаты доступа: "CODLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        Опорный объект: CL_FIO_DATE_INDEX
                        Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        Опорный объект: CL_LOGIN_DATE_INDEX
                        Предикаты доступа: "CODLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        Опорный объект: CL_DI_DATE_INDEX
                        Предикаты доступа: "CODLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- Добавление if согласовано. Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет -->
                    <#if !allTbAccess>
                        AND (
                            (:extra_TB IS NULL AND TB IS NULL)
                            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                                               WHERE ad.LOGIN_ID = :extra_personLoginId
                                               AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                                        TB||'|*|*',
                                                                                        TB||'|'||OSB||'|*',
                                                                                        TB||'|'||OSB||'|'||VSP)
                            )
                        )
                    </#if>

                    AND (:extra_TB IS NULL
                        OR (
                            TB = :extra_TB
                            AND (
                                (:extra_withChildren IS NOT NULL AND (:extra_OSB IS NULL OR OSB = :extra_OSB))
                                OR (
                                    :extra_withChildren IS NULL
                                    AND (
                                        :extra_OSB IS NOT NULL
                                        AND OSB = :extra_OSB
                                        AND (:extra_VSP IS NOT NULL AND VSP = :extra_VSP OR :extra_VSP IS NULL AND VSP IS NULL)
                                        OR
                                        :extra_OSB IS NULL
                                        AND OSB IS NULL
                                        AND VSP IS NULL
                                    )
                                )
                            )
                        )
                    )
            ) o WHERE ROWNUM < (:firstRow + :maxRows)

            UNION ALL
            -- Ограничивание выборки из FINANCIAL_CODLOG по firstRow + maxRows
            SELECT o.* FROM (
                -- Выборка из FINANCIAL_CODLOG
                SELECT
                    'MESSAGE_F' AS TYPE,
                    FINANCIAL_CODLOG.ID,
                    LOGIN_ID,
                    START_DATE,
                    APPLICATION,
                    '' AS OPERATION,
                    '' AS IP_ADDRESS,
                    decode(:extra_sessionId, NULL, '', SESSION_ID) AS SESSION_ID,
                    '' AS MESSAGE_TYPE,
                    NULL AS MODULE,
                    MESSAGE_TYPE AS REQUEST_TYPE,
                    message_translate_request.translate AS REQUEST_TYPE_TRANSLATE,
                    SYST AS SYSTEM,
                    '0' as paramsEmpty,
                    SUR_NAME,
                    PATR_NAME,
                    FIRST_NAME,
                    DEPARTMENT_NAME,
                    NODE_ID
                FROM FINANCIAL_CODLOG
                LEFT JOIN MESSAGE_TRANSLATE message_translate_request ON message_translate_request.CODE = MESSAGE_TYPE
                WHERE
                    (START_DATE >= :extra_fromDate)
                    AND (START_DATE <= :extra_toDate)

                    <#if application?has_content>
                    <#--
                        Опорный объект: FCL_APP_DATE_INDEX
                        Предикаты доступа: "FINANCIAL_CODLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                    </#if>

                    <#if sessionId?has_content>
                    <#--
                        Опорный объект: FCL_SESSION_DATE_INDEX
                        Предикаты доступа: "FINANCIAL_CODLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        Опорный объект: FCL_FIO_DATE_INDEX
                        Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        Опорный объект: FCL_LOGIN_DATE_INDEX
                        Предикаты доступа: "FINANCIAL_CODLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        Опорный объект: FCL_DI_DATE_INDEX
                        Предикаты доступа: "FINANCIAL_CODLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет(аналогично другим журналам) -->
                    <#if !allTbAccess>
                        AND (
                            (:extra_TB IS NULL AND TB IS NULL)
                            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                                               WHERE ad.LOGIN_ID = :extra_personLoginId
                                               AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                                        TB||'|*|*',
                                                                                        TB||'|'||OSB||'|*',
                                                                                        TB||'|'||OSB||'|'||VSP)
                            )
                        )
                    </#if>

                    AND (:extra_TB IS NULL
                        OR (
                            TB = :extra_TB
                            AND (
                                (:extra_withChildren IS NOT NULL AND (:extra_OSB IS NULL OR OSB = :extra_OSB))
                                OR (
                                    :extra_withChildren IS NULL
                                    AND (
                                        :extra_OSB IS NOT NULL
                                        AND OSB = :extra_OSB
                                        AND (:extra_VSP IS NOT NULL AND VSP = :extra_VSP OR :extra_VSP IS NULL AND VSP IS NULL)
                                        OR
                                        :extra_OSB IS NULL
                                        AND OSB IS NULL
                                        AND VSP IS NULL
                                    )
                                )
                            )
                        )
                    )
            ) o WHERE ROWNUM < (:firstRow + :maxRows)

            </#if>

            <#if showMessageLog?has_content && showMessageLog && showUserLog?has_content && showUserLog>
                UNION ALL
            </#if>

            <#if showUserLog?has_content && showUserLog>
            -- Ограничивание выборки из USERLOG по firstRow + maxRows
            SELECT o.* FROM (
                -- Выборка из USERLOG
                SELECT
                    'USER' AS TYPE,
                    ID,
                    LOGIN_ID,
                    START_DATE,
                    APPLICATION,
                    DESCRIPTION AS OPERATION,
                    IP_ADDRESS,
                    SESSION_ID,
                    SUCCESS AS MESSAGE_TYPE,
                    NULL AS MODULE,
                    '' AS REQUEST_TYPE,
                    '' AS REQUEST_TYPE_TRANSLATE,
                    '' AS SYSTEM,
                    NVL2(PARAMETERS, '0','1') as paramsEmpty,
                    SUR_NAME,
                    PATR_NAME,
                    FIRST_NAME,
                    DEPARTMENT_NAME,
                    NODE_ID
                FROM USERLOG
                WHERE
                    (START_DATE >= :extra_fromDate)
                    AND (START_DATE <= :extra_toDate)

                    <#if application?has_content>
                    <#--
                        Опорный объект: UL_APP_DATE_INDEX
                        Предикаты доступа: "USERLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                    </#if>

                    <#if sessionId?has_content>
                    <#--
                        Опорный объект: UL_SESSION_DATE_INDEX
                        Предикаты доступа: "USERLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        Опорный объект: UL_FIO_DATE_INDEX
                        Предикаты доступа: (UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        Опорный объект: UL_LOGIN_DATE_INDEX
                        Предикаты доступа: "USERLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        Опорный объект: UL_DI_DATE_INDEX
                        Предикаты доступа: "USERLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- Добавление if согласовано. Если не добавить if, оракл все равно ходит по ДБ линкам, хотя запрос в них не выполняет -->
                    <#if !allTbAccess>
                        AND (
                            (:extra_TB IS NULL AND TB IS NULL)
                            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                                      WHERE ad.LOGIN_ID = :extra_personLoginId
                                      AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in ('*|*|*',
                                                                              TB||'|*|*',
                                                                              TB||'|'||OSB||'|*',
                                                                              TB||'|'||OSB||'|'||VSP)
                            )
                        )
                    </#if>

                    AND (:extra_TB IS NULL
                        OR (
                            TB = :extra_TB
                            AND (
                                (:extra_withChildren IS NOT NULL AND (:extra_OSB IS NULL OR OSB = :extra_OSB))
                                OR (
                                    :extra_withChildren IS NULL
                                    AND (
                                        :extra_OSB IS NOT NULL
                                        AND OSB = :extra_OSB
                                        AND (:extra_VSP IS NOT NULL AND VSP = :extra_VSP OR :extra_VSP IS NULL AND VSP IS NULL)
                                        OR
                                        :extra_OSB IS NULL
                                        AND OSB IS NULL
                                        AND VSP IS NULL
                                    )
                                )
                            )
                        )
                    )
            ) o WHERE ROWNUM < (:firstRow + :maxRows)
            </#if>
        ) o
        ORDER BY START_DATE DESC, ID DESC
    ) o
    WHERE ROWNUM < (:firstRow + :maxRows)
) logentry