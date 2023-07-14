SELECT
    {profiles.*}
FROM
    CSA_PROFILES profiles
    LEFT JOIN CSA_CONNECTORS connectors ON connectors.PROFILE_ID = profiles.ID
    LEFT JOIN CSA_LOGINS login  ON login.CONNECTOR_ID = connectors.ID
WHERE
    ((:extra_creationType is null and (profiles.CREATION_TYPE is null or profiles.CREATION_TYPE != 'POTENTIAL')) or profiles.CREATION_TYPE = :extra_creationType)
<#if document?has_content>
    <#--
     Опорный элемент: CSA_PROFILES_DUL_BIRTH_INDEX
     Предикат доступа: access(REPLACE(REPLACE("PASSPORT",' ',''),'-','')=REPLACE(REPLACE(:EXTRA_DOCUMENT,' ',''),'-',''))
     Кардинальность: все профили клиента с указанным номером паспорта.
    -->
    AND (replace(replace(profiles.PASSPORT,' ',''),'-','') = replace(replace(:extra_document,' ',''),'-',''))
</#if>
    AND (:extra_birthday is null or profiles.BIRTHDATE = :extra_birthday)
<#if searchType ='byIPasLogin'>
    <#--
     Опорный объект: CSA_CONNECTORS_UTS
     Предикаты доступа: access("CONNECTORS"."USER_ID"=:EXTRA_LOGIN AND "CONNECTORS"."TYPE"='TERMINAL')
     Кардинальность: 1
    -->
    AND connectors.USER_ID = :extra_login
    AND connectors.TYPE = 'TERMINAL'
<#elseif searchType ='byLogin'>
    <#--
     Опорный объект: IDX_CSA_LOGINS
     Предикаты доступа: access(UPPER("LOGIN") LIKE UPPER(REPLACE(REPLACE(:EXTRA_LOGIN,'%'),'?'))||'%')
     Кардинальность: зависит от параметра :extra_login
    -->
    AND upper(login.LOGIN) like upper(replace(replace(:extra_login, '%'), '?'))||'%'
<#else>
    AND NOT EXISTS (SELECT 1
          FROM CSA_CONNECTORS tempConnectors
          WHERE tempConnectors.PROFILE_ID = connectors.PROFILE_ID AND tempConnectors.state !='CLOSED'  AND
                (tempConnectors.LAST_SESSION_DATE is not null AND connectors.LAST_SESSION_DATE is not null AND tempConnectors.LAST_SESSION_DATE > connectors.LAST_SESSION_DATE
                or tempConnectors.LAST_SESSION_DATE is not null AND connectors.LAST_SESSION_DATE is null
                or tempConnectors.LAST_SESSION_DATE is null AND connectors.LAST_SESSION_DATE is null AND tempConnectors.CREATION_DATE > connectors.CREATION_DATE))
</#if>
<#if agreementNumber?has_content>
    <#--
     Опорный объект: CSA_PROFILES_AGREEMENT_NUM_I
     Предикаты доступа: access("PROFILES"."AGREEMENT_NUMBER"=:EXTRA_AGREEMENTNUMBER)
     Кардинальность: 1
    -->
    AND profiles.AGREEMENT_NUMBER = :extra_agreementNumber
</#if>
<#if phoneNumber?has_content>
    <#--
     Опорный объект: CSA_PROFILES_PHONE_INDEX
     Предикаты доступа: access("PROFILES"."PHONE"=:EXTRA_PHONENUMBER)
     Кардинальность: 1
    -->
    AND profiles.PHONE = :extra_phoneNumber
</#if>
<#if fio?has_content>
    <#--
     Опорный объект: CSA_PROFILES_FIO_INDEX
     Предикаты доступа: access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) UPPER (REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%'))
     Кардинальность: количество профилей с одинаковым ФИО
    -->
    AND upper(replace(replace(concat(concat(profiles.SUR_NAME, profiles.FIRST_NAME), profiles.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
</#if>
    AND profiles.TB in (:extra_tbList)
    AND (connectors.state is null OR connectors.state !='CLOSED')

<#if phoneNumber?has_content>
    UNION

    SELECT
        {profiles.*}
    FROM
        CSA_PROFILES profiles
        LEFT JOIN CSA_CONNECTORS connectors ON connectors.PROFILE_ID = profiles.ID
        LEFT JOIN CSA_LOGINS login  ON login.CONNECTOR_ID = connectors.ID
    WHERE
        ((:extra_creationType is null and (profiles.CREATION_TYPE is null or profiles.CREATION_TYPE != 'POTENTIAL')) or profiles.CREATION_TYPE = :extra_creationType)
    <#if document?has_content>
        <#--
         Опорный элемент: CSA_PROFILES_DUL_BIRTH_INDEX
         Предикат доступа: access(REPLACE(REPLACE("PASSPORT",' ',''),'-','')=REPLACE(REPLACE(:EXTRA_DOCUMENT,' ',''),'-',''))
         Кардинальность: все профили клиента с указанным номером паспорта.
        -->
        AND (replace(replace(profiles.PASSPORT,' ',''),'-','') = replace(replace(:extra_document,' ',''),'-',''))
	</#if>
        AND (:extra_birthday is null or profiles.BIRTHDATE = :extra_birthday)
	<#if searchType ='byIPasLogin'>
        <#--
         Опорный объект: CSA_CONNECTORS_UTS
         Предикаты доступа: access("CONNECTORS"."USER_ID"=:EXTRA_LOGIN AND "CONNECTORS"."TYPE"='TERMINAL')
         Кардинальность: 1
        -->
        AND connectors.USER_ID = :extra_login
        AND connectors.TYPE = 'TERMINAL'
	<#elseif searchType ='byLogin'>
        <#--
            Опорный объект: IDX_CSA_LOGINS
            Предикаты доступа: access(UPPER("LOGIN") LIKE UPPER(REPLACE(REPLACE(:EXTRA_LOGIN,'%'),'?'))||'%')
            Кардинальность: зависит от параметра :extra_login
        -->
        AND upper(login.LOGIN) like upper(replace(replace(:extra_login, '%'), '?'))||'%'
	<#else>
        AND NOT EXISTS (SELECT 1
              FROM CSA_CONNECTORS tempConnectors
              WHERE tempConnectors.PROFILE_ID = connectors.PROFILE_ID AND tempConnectors.state !='CLOSED'  AND
                    (tempConnectors.LAST_SESSION_DATE is not null AND connectors.LAST_SESSION_DATE is not null AND tempConnectors.LAST_SESSION_DATE > connectors.LAST_SESSION_DATE
                    or tempConnectors.LAST_SESSION_DATE is not null AND connectors.LAST_SESSION_DATE is null
                    or tempConnectors.LAST_SESSION_DATE is null AND connectors.LAST_SESSION_DATE is null AND tempConnectors.CREATION_DATE > connectors.CREATION_DATE))
	</#if>
	<#if agreementNumber?has_content>
        <#--
         Опорный объект: CSA_PROFILES_AGREEMENT_NUM_I
         Предикаты доступа: access("PROFILES"."AGREEMENT_NUMBER"=:EXTRA_AGREEMENTNUMBER)
         Кардинальность: 1
        -->
        AND profiles.AGREEMENT_NUMBER = :extra_agreementNumber
	</#if>
	<#if phoneNumber?has_content>
        <#--
         Опорный объект: CONNECTORS_PHONE_INDEX
         Предикаты доступа: access("ERMBCONNECTORS"."PHONE_NUMBER"=:EXTRA_PHONENUMBER)
         Кардинальность: 1
        -->
        AND EXISTS (
            SELECT
                1
            FROM
                CSA_CONNECTORS ermbConnectors
            WHERE
                ermbConnectors.PROFILE_ID = profiles.ID
                AND ermbConnectors.TYPE = 'ERMB'
                AND ermbConnectors.PHONE_NUMBER = :extra_phoneNumber
            )
	</#if>
	<#if fio?has_content>
        <#--
         Опорный объект: CSA_PROFILES_FIO_INDEX
         Предикаты доступа: access(UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) UPPER (REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%'))
         Кардинальность: количество профилей с одинаковым ФИО
        -->
        AND upper(replace(replace(concat(concat(profiles.SUR_NAME, profiles.FIRST_NAME), profiles.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
	</#if>
        AND profiles.TB in (:extra_tbList)
        AND (connectors.state is null OR connectors.state !='CLOSED')
</#if>
