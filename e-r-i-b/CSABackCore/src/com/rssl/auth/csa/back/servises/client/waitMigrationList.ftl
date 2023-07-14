SELECT
    {profiles.*}
FROM
    CSA_PROFILES profiles
    JOIN CSA_PROFILE_NODES profileNodes ON profileNodes.PROFILE_ID = profiles.ID AND profileNodes.PROFILE_TYPE = 'MAIN'
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
    <#if agreementNumber?has_content>
        <#--
         Опорный объект: CSA_PROFILES_AGREEMENT_NUM_I
         Предикаты доступа: access("PROFILES"."AGREEMENT_NUMBER"=:EXTRA_AGREEMENTNUMBER)
         Кардинальность: 1
        -->
        AND profiles.AGREEMENT_NUMBER = :extra_agreementNumber
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
    AND profileNodes.NODE_ID = :extra_nodeId
    AND exists(SELECT 1 FROM CSA_PROFILE_NODES existingProfileNodes WHERE existingProfileNodes.PROFILE_ID = profiles.ID AND existingProfileNodes.STATE = 'WAIT_MIGRATION')
