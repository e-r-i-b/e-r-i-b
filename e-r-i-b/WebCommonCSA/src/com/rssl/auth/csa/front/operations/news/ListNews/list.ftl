SELECT
    {news.*}
FROM
    NEWS news
WHERE (news.TYPE = 'LOGIN_PAGE' OR news.TYPE = 'EVERYWHERE') and current_date > news.START_PUBLISH_DATE and (news.END_PUBLISH_DATE is null or news.END_PUBLISH_DATE > current_date)
<#if regionTB?has_content>
    AND news.ID in ( select news_dep.NEWS_ID from NEWS_DEPARTMENT news_dep where news_dep.TB = :regionTB)
</#if>
    and (news.NEWS_DATE >= :extra_fromDate)	and (news.NEWS_DATE <= :extra_toDate)
<#if seach?has_content>
    AND upper(news.TITLE) like upper(:extra_like_seach)
</#if>
<#if important?has_content>
       AND news.IMPORTANT = :extra_important
</#if>
ORDER BY news.NEWS_DATE DESC,  news.IMPORTANT, news.TITLE