select clm.ID as document_number, clm.CREATION_DATE as document_date, clm.PHONE as owner_phone,
    clm.PERSON_LAST_NAME as owner_surname, clm.PERSON_MIDDLE_NAME as owner_patrname, clm.PERSON_FIRST_NAME as owner_name
from ISSUE_CARD_CLAIM clm
where
    <#if phone?has_content>
        <#--
            Опорный объект:  IDX_ISS_CARD_PHONE_DATE
            Предикаты доступа:  access("CLM"."PHONE"=:PHONE AND "CLM"."CREATION_DATE">=TO_TIMESTAMP(:FROMDATE) AND "CLM"."CREATION_DATE"<=TO_TIMESTAMP(:TODATE))
            Кардинальность: 10, 20, 50 (ограничивается количеством выводимых записей на странице)
        -->
        clm.PHONE = :extra_phone and
    <#else>
        <#--
            Опорный объект:  IDX_ISS_CARD_LOGIN_DATE
            Предикаты доступа:  access("CLM"."LOGIN"=:LOGIN AND "CLM"."CREATION_DATE">=TO_TIMESTAMP(:FROMDATE) AND "CLM"."CREATION_DATE"<=TO_TIMESTAMP(:TODATE))
            Кардинальность: 10, 20, 50 (ограничивается количеством выводимых записей на странице)
        -->
        clm.LOGIN = :extra_login and
    </#if>
    clm.CREATION_DATE >= :extra_startDate and clm.CREATION_DATE <= :extra_endDate and clm.IS_GUEST = '1'
order by document_date desc