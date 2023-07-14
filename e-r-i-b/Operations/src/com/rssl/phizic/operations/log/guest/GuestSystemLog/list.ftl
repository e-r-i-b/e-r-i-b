SELECT logentry.*
FROM (
    SELECT
        guestLog.ID,
        guestLog.MSG_LEVEL,
        guestLog.START_DATE,
        guestLog.GUEST_CODE,
        guestLog.APPLICATION,
        guestLog.MESSAGE,
        guestLog.SESSION_ID,
        guestLog.IP_ADDRESS,
        guestLog.MESSAGE_SOURCE,
        guestLog.FIRST_NAME,
        guestLog.SUR_NAME,
        guestLog.PATR_NAME,
        guestLog.DOC_NUMBER,
        guestLog.DOC_SERIES,
        guestLog.BIRTHDATE,
        guestLog.TB,
        guestLog.LOGIN,
        guestLog.PHONE_NUMBER,
        guestLog.THREAD_INFO,
        guestLog.NODE_ID,
        guestLog.DEPARTMENT_NAME
    FROM GUEST_SYSTEMLOG guestLog
    WHERE
        (guestLog.START_DATE >= :extra_fromDate)
        AND (guestLog.START_DATE <= :extra_toDate)

        <#if fio?has_content>
        <#--
            Опорный объект: I_G_SL_FIO_DATE
            Предикаты доступа: access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
                                SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
            Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
        -->
        AND (
            upper(replace(replace(concat(concat(guestLog.SUR_NAME, guestLog.FIRST_NAME), guestLog.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
        )
        </#if>

        <#if phoneNumber?has_content>
        <#--
            Опорный объект: I_G_SL_PHONE_DATE
            Предикаты доступа: access("GUESTLOG"."PHONE_NUMBER"=:EXTRA_PHONENUMBER AND
                                SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                                SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
            Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
        -->
        AND guestLog.PHONE_NUMBER = :extra_phoneNumber
        </#if>

        AND (:extra_number IS NULL OR replace(guestLog.DOC_NUMBER, ' ', '') like :extra_like_number)
        AND (:extra_series IS NULL OR replace(guestLog.DOC_SERIES, ' ', '') like :extra_like_series)
        AND (:extra_birthday IS NULL OR guestLog.BIRTHDATE = :extra_birthday)
        AND (:extra_messageType IS NULL OR guestLog.MSG_LEVEL = :extra_messageType)
        AND (:extra_ipAddres IS NULL OR guestLog.IP_ADDRESS = :extra_ipAddres)
        AND (:extra_messageWord IS NULL OR (upper(guestLog.MESSAGE) like upper(:extra_like_messageWord)))
        AND (:extra_source IS NULL OR guestLog.MESSAGE_SOURCE = :extra_source)
        AND (:extra_errorId IS NULL OR guestLog.ID = :extra_errorId)

        <#if !allTbAccess>
            AND (
                (:extra_TB IS NULL AND guestLog.TB IS NULL)
                OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                    WHERE ad.LOGIN_ID = :extra_personLoginId
                    AND ad.TB = guestLog.TB
                    )
            )
        </#if>
    ORDER BY guestLog.START_DATE DESC
) logentry
