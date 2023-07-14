SELECT logentry.*
FROM (
    SELECT
        guestLog.ID,
        guestLog.START_DATE,
        guestLog.EXECUTION_TIME,
        guestLog.DESCRIPTION,
        guestLog.DESCRIPTION_KEY,
        guestLog.PARAMETERS,
        guestLog.SUCCESS,
        guestLog.APPLICATION,
        guestLog.OPERATION_KEY,
        guestLog.IP_ADDRESS,
        guestLog.SESSION_ID,
        guestLog.SUR_NAME,
        guestLog.PATR_NAME,
        guestLog.FIRST_NAME,
        guestLog.DEPARTMENT_NAME,
        guestLog.DOC_SERIES,
        guestLog.DOC_NUMBER,
        guestLog.BIRTHDATE,
        guestLog.TB,
        guestLog.NODE_ID,
        guestLog.THREAD_INFO,
        guestLog.PHONE_NUMBER,
        guestLog.GUEST_LOGIN,
        guestLog.GUEST_CODE
    FROM GUEST_USERLOG guestLog
    WHERE
        (guestLog.START_DATE >= :extra_fromDate)
        AND (guestLog.START_DATE <= :extra_toDate)

        <#if fio?has_content>
        <#--
            Опорный объект: I_G_UL_FIO_DATE
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
            Опорный объект: I_G_UL_PHONE_DATE
            Предикаты доступа: access("GUESTLOG"."PHONE_NUMBER"=:EXTRA_PHONENUMBER AND
                               SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
                               SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))
            Кардинальность: Есть пагинация (стоп условие - количество записей на странице: 10, 20, 50)
        -->
        AND guestLog.PHONE_NUMBER = :extra_phoneNumber
        </#if>

        AND (:extra_number IS NULL OR replace(guestLog.DOC_NUMBER, ' ', '') like :extra_like_number)
        AND (:extra_series IS NULL OR replace(guestLog.DOC_SERIES, ' ', '') like :extra_like_series)
        AND (:extra_type IS NULL OR guestLog.SUCCESS = :extra_type)
        AND (:extra_birthday IS NULL OR guestLog.BIRTHDATE = :extra_birthday)

        <#if !allTbAccess>
        AND (
            (:extra_TB IS NULL AND guestLog.TB IS NULL)
            OR exists(SELECT 1 FROM ALLOWED_DEPARTMENTS${linkName} ad
                WHERE ad.LOGIN_ID = :extra_personLoginId
                AND ad.TB = guestLog.TB
                )
            )
        </#if>

        AND (:extra_TB IS NULL OR guestLog.TB = :extra_TB)

    ORDER BY guestLog.START_DATE DESC
) logentry