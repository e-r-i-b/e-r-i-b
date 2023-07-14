<#--
   Опорный объект:NEWS_INDEX
   Предикаты доступа: access("NEWS"."START_PUBLISH_DATE"<CURRENT_DATE)
   Кардинальность: количество новостей опубликованных на текущий момент.
-->
SELECT
    {news.*},{newsRes.*}
FROM
    NEWS news
    left join NEWS_RES newsRes on news.UUID = newsRes.UUID and newsRes.LOCALE_ID = :extra_localeId
<#--
   В данном ветвлении добавляется join с таблицей NEWS_DEPARTMENT для фильтрации новостей по тербанкам. Предикаты доступа не меняются.
   Опорный объект:NEWS_INDEX
   Предикаты доступа: access("NEWS"."START_PUBLISH_DATE"<CURRENT_DATE)
   Кардинальность: количество новостей опубликованных на текущий момент.
-->
<#if departmentTB?has_content>
    join NEWS_DEPARTMENT newsDepartment on news.ID = newsDepartment.NEWS_ID and newsDepartment.TB = :extra_departmentTB
</#if>
WHERE
    news.TYPE = :extra_type
    AND current_date > news.START_PUBLISH_DATE and (news.END_PUBLISH_DATE is null or news.END_PUBLISH_DATE > current_date)
    AND (:extra_fromDate IS NULL OR news.NEWS_DATE >= :extra_fromDate)
    AND (:extra_toDate IS NULL OR :extra_toDate > news.NEWS_DATE)
    AND (:extra_important IS NULL OR :extra_important = '' OR news.IMPORTANT = :extra_important)
    AND (:extra_search IS NULL OR :extra_search = '' OR upper(newsRes.TITLE) like upper(:extra_like_search) OR upper(newsRes.SHORT_TEXT) like upper(:extra_like_search) OR upper(newsRes.TEXT) like upper(:extra_like_search))
ORDER BY news.NEWS_DATE DESC, news.IMPORTANT