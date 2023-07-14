SELECT logentry.* FROM USERLOG logentry
WHERE
    (logentry.start_date >= :extra_fromDate)
    AND (logentry.start_date <= :extra_toDate)

    <#if application?has_content>
    <#--
        Опорный объект: UL_APP_DATE_INDEX
        Предикаты доступа: "LOGENTRY"."APPLICATION"=:EXTRA_APPLICATION AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
    -->
        AND logentry.APPLICATION in (:extra_application)
    </#if>

    <#if fio?has_content>
    <#--
        Опорный объект: UL_FIO_DATE_INDEX
        Предикаты доступа: (UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
    -->
        AND upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>

    <#if number?has_content>
    <#--
        Опорный объект: UL_DI_DATE_INDEX
        Предикаты доступа: "LOGENTRY"."DOC_NUMBER"=:EXTRA_NUMBER AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
    -->
        AND logentry.DOC_NUMBER = :extra_number
    </#if>

    <#if loginId?has_content>
    <#--
        Опорный объект: UL_LOGIN_DATE_INDEX
        Предикаты доступа: "LOGENTRY"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
    -->
        AND logentry.LOGIN_ID = :extra_loginId
    </#if>

    AND (:extra_series IS NULL OR replace(logentry.DOC_SERIES,' ') like :extra_like_series)
    AND (:extra_type IS NULL OR logentry.SUCCESS = :extra_type)
    AND (:extra_birthday IS NULL OR logentry.BIRTHDAY = :extra_birthday)

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

ORDER BY logentry.start_date desc