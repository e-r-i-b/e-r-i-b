<#--
    Опорный объект: EXCEPTION_ENTRY
    Предикаты доступа:  -
    Кардинальность: количество элементов в зависимости от decoratedException
                    возвращаем замапленые сущности (на проме 25) или
                    незамапленные сущности(около 20 тысяч)
                    (с учётом пагинации)
-->
SELECT
    {exceptionEntry.*}
FROM
    EXCEPTION_ENTRY exceptionEntry
WHERE
<#if exceptionEntryType = 'internal'>
    KIND = 'I'
<#else>
    KIND = 'E'
</#if>
<#if id?has_content>
<#--
     Опорный объект:    PK_EXCEPTION_ENTRY
     Предикаты доступа: access("EXCEPTIONENTRY"."ID"=TO_NUMBER(:EXTRA_ID))
     Кардинальность:    1
-->
    and exceptionEntry.id = :extra_id
</#if>
<#if operationType?has_content>
    and upper(exceptionEntry.operation) like upper(:extra_like_operationType)
</#if>
<#if application?has_content>
    and exceptionEntry.application = :extra_application
</#if>
<#if system?has_content>
    and exceptionEntry.SYSTEM = :extra_system
</#if>
<#if errorCode?has_content>
    and exceptionEntry.ERROR_CODE = :extra_errorCode
</#if>
<#if decoratedException>
    and exists(select 1 from EXCEPTION_MAPPINGS${linkName} em where em.HASH = exceptionEntry.HASH)
<#else>
    and not exists(select 1 from EXCEPTION_MAPPINGS${linkName} em where em.HASH = exceptionEntry.HASH)
</#if>
ORDER BY exceptionEntry.id DESC