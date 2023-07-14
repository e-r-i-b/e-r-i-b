SELECT logentry.*
FROM (
    -- ������������� ����������� SYSTEMLOG, CODLOG � USERLOG �� firstRow + maxRows
    SELECT o.* FROM (
        -- ���������� ����������� SYSTEMLOG, CODLOG � USERLOG
        SELECT o.* FROM (
            -- ������������� ������� �� SYSTEMLOG �� firstRow + maxRows
            <#if showSystemLog?has_content && showSystemLog>
            SELECT o.* FROM (
                -- ������� �� SYSTEMLOG
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
                        ������� ������: SL_APP_DATE_INDEX
                        ��������� �������: "SYSTEMLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                     </#if>

                    <#if sessionId?has_content>
                    <#--
                        ������� ������: SL_SESSION_DATE_INDEX
                        ��������� �������: "SYSTEMLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        ������� ������: SL_FIO_DATE_INDEX
                        ��������� �������: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        ������� ������: SL_LOGIN_DATE_INDEX
                        ��������� �������: "SYSTEMLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        ������� ������: SL_DI_DATE_INDEX
                        ��������� �������: "SYSTEMLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ������� �� :extra_fromDate � :extra_toDate
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- ���������� if �����������. ���� �� �������� if, ����� ��� ����� ����� �� �� ������, ���� ������ � ��� �� ��������� -->
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
            -- ������������� ������� �� CODLOG �� firstRow + maxRows
            SELECT o.* FROM (
                -- ������� �� CODLOG
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
                        ������� ������: CL_APP_DATE_INDEX
                        ��������� �������: "CODLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                    </#if>

                    <#if sessionId?has_content>
                    <#--
                        ������� ������: CL_SESSION_DATE_INDEX
                        ��������� �������: "CODLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        ������� ������: CL_FIO_DATE_INDEX
                        ��������� �������: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        ������� ������: CL_LOGIN_DATE_INDEX
                        ��������� �������: "CODLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        ������� ������: CL_DI_DATE_INDEX
                        ��������� �������: "CODLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- ���������� if �����������. ���� �� �������� if, ����� ��� ����� ����� �� �� ������, ���� ������ � ��� �� ��������� -->
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
            -- ������������� ������� �� FINANCIAL_CODLOG �� firstRow + maxRows
            SELECT o.* FROM (
                -- ������� �� FINANCIAL_CODLOG
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
                        ������� ������: FCL_APP_DATE_INDEX
                        ��������� �������: "FINANCIAL_CODLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                    </#if>

                    <#if sessionId?has_content>
                    <#--
                        ������� ������: FCL_SESSION_DATE_INDEX
                        ��������� �������: "FINANCIAL_CODLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        ������� ������: FCL_FIO_DATE_INDEX
                        ��������� �������: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        ������� ������: FCL_LOGIN_DATE_INDEX
                        ��������� �������: "FINANCIAL_CODLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        ������� ������: FCL_DI_DATE_INDEX
                        ��������� �������: "FINANCIAL_CODLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- ���� �� �������� if, ����� ��� ����� ����� �� �� ������, ���� ������ � ��� �� ���������(���������� ������ ��������) -->
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
            -- ������������� ������� �� USERLOG �� firstRow + maxRows
            SELECT o.* FROM (
                -- ������� �� USERLOG
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
                        ������� ������: UL_APP_DATE_INDEX
                        ��������� �������: "USERLOG"."APPLICATION"=:EXTRA_APPLICATION AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND APPLICATION in (:extra_application)
                    </#if>

                    <#if sessionId?has_content>
                    <#--
                        ������� ������: UL_SESSION_DATE_INDEX
                        ��������� �������: "USERLOG"."SESSION_ID"=:EXTRA_SESSIONID AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND SESSION_ID = :extra_sessionId
                    </#if>

                    <#if fio?has_content>
                    <#--
                        ������� ������: UL_FIO_DATE_INDEX
                        ��������� �������: (UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                    </#if>

                    <#if loginId?has_content>
                    <#--
                        ������� ������: UL_LOGIN_DATE_INDEX
                        ��������� �������: "USERLOG"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND LOGIN_ID = :extra_loginId
                    </#if>

                    <#if number?has_content>
                    <#--
                        ������� ������: UL_DI_DATE_INDEX
                        ��������� �������: "USERLOG"."DOC_NUMBER"=:EXTRA_NUMBER AND
                                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
                        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
                    -->
                        AND DOC_NUMBER = :extra_number
                    </#if>

                    AND (:extra_birthday IS NULL OR BIRTHDAY = :extra_birthday)
                    AND (:extra_series IS NULL OR replace(DOC_SERIES,' ') like :extra_like_series)

                    <#-- ���������� if �����������. ���� �� �������� if, ����� ��� ����� ����� �� �� ������, ���� ������ � ��� �� ��������� -->
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