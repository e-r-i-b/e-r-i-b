SELECT logentry.* FROM SYSTEMLOG logentry
WHERE
    (logentry.START_DATE >= :extra_fromDate)
    AND (logentry.START_DATE <= :extra_toDate)

    <#if application?has_content>
    <#--
        ������� ������: SL_APP_DATE_INDEX
        ��������� �������: "LOGENTRY"."APPLICATION"=:EXTRA_APPLICATION AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
    -->
        AND logentry.APPLICATION IN (:extra_application)
    </#if>

    <#if fio?has_content>
    <#--
        ������� ������: SL_FIO_DATE_INDEX
        ��������� �������: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                           SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                           SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
    -->
        AND (
            upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
           )
    </#if>

    <#if number?has_content>
    <#--
        ������� ������: SL_DI_DATE_INDEX
        ��������� �������: "LOGENTRY"."DOC_NUMBER"=:EXTRA_NUMBER AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
    -->
        AND logentry.DOC_NUMBER = :extra_number
    </#if>

    <#if ipAddres?has_content>
    <#--
        ������� ������: SL_IP_DATE_INDEX
        ��������� �������: "LOGENTRY"."IP_ADDRESS"=:EXTRA_IPADDRES AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
    -->
        AND (logentry.IP_ADDRESS = :extra_ipAddres)
    </#if>

    <#if errorId?has_content>
    <#--
        ������� ������: SL_PK
        ��������� �������: "LOGENTRY"."ID"=TO_NUMBER(:EXTRA_ERRORID)
        ��������������: 1
    -->
        AND logentry.ID = :extra_errorId
    </#if>

    <#if loginId?has_content>
    <#--
        ������� ������: SL_LOGIN_DATE_INDEX
        ��������� �������: "LOGENTRY"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
                            SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                            SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
        ��������������: ���� ��������� (���� ������� - ���������� ������� �� ��������: 10, 20, 50)
    -->
        AND logentry.LOGIN_ID = :extra_loginId
    </#if>

    AND (:extra_series IS NULL OR replace(logentry.DOC_SERIES, ' ') like :extra_like_series)
    AND (:extra_source IS NULL OR logentry.MESSAGE_SOURCE = :extra_source)
    AND (:extra_messageType IS NULL OR logentry.MSG_LEVEL = :extra_messageType)
    AND (:extra_birthday IS NULL OR logentry.BIRTHDAY = :extra_birthday)
    AND (:extra_messageWord IS NULL OR (upper(logentry.MESSAGE) like upper(:extra_like_messageWord)))

    <#-- ���������� if �����������. ���� �� �������� if, ����� ��� ����� ����� �� �� ������, ���� ������ � ��� �� ��������� -->
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

ORDER BY logentry.start_date desc
