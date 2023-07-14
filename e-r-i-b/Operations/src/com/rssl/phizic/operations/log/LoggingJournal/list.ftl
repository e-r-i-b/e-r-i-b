SELECT
    lcentry.*,
    (
        SELECT entry.TYPE
        FROM OPERATION_CONFIRM_LOG entry
        WHERE entry.OPERATION_UID = lcentry.OPERATION_UID AND (entry.STATE='SUCCESSFUL' OR entry.STATE='CONF_SUCCESS') AND ROWNUM = 1
    ) confirmType,
    (
        SELECT '1'
        FROM OPERATION_CONFIRM_LOG entry
        WHERE entry.OPERATION_UID = lcentry.OPERATION_UID AND ROWNUM = 1
    ) hasTry
FROM INPUT_REGISTER_JOURNAL lcentry
WHERE
    lcentry.login_date >= :extra_fromDate AND lcentry.login_date <= :extra_toDate
    AND (:extra_application is null or lcentry.APPLICATION = :extra_application)
    AND (:extra_birthday is null or lcentry.BIRTHDAY = :extra_birthday)
    <#if dul?has_content>
    <#--
        Опорный объект: RL_DUL_DATE_INDEX
        Предикаты доступа: (UPPER(REPLACE("DOC_SERIES",' ','')||REPLACE("DOC_NUMBER",' ',''))=UPPER(REPLACE(:EXTRA_DUL,' ','')) AND
               "LCENTRY"."LOGIN_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "LCENTRY"."LOGIN_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
     -->
        AND UPPER(REPLACE(lcentry.DOC_SERIES, ' ', '')||REPLACE(lcentry.DOC_NUMBER, ' ', '')) = UPPER(REPLACE(:extra_dul, ' ', ''))
    </#if>
    <#if loginId?has_content>
    <#--
        Опорный объект: RL_LOGIN_DATE_INDEX
        Предикаты доступа: "LCENTRY"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
               SYS_OP_DESCEND("LOGIN_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
               SYS_OP_DESCEND("LOGIN_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
     -->
        AND lcentry.LOGIN_ID = :extra_loginId
    </#if>
    <#if fio?has_content>
    <#--
        Опорный объект: RL_FIO_DATE_INDEX
        Предикаты доступа: UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE
               UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND SYS_OP_DESCEND("LOGIN_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:E
               XTRA_TODATE)) AND SYS_OP_DESCEND("LOGIN_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))
     -->
        AND upper(replace(replace(concat(concat(lcentry.SUR_NAME, lcentry.FIRST_NAME), lcentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
    </#if>
ORDER BY lcentry.login_date DESC
