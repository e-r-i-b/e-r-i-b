<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.TwitterWidget"/>
    <tiles:put name="borderColor" value="whiteTop"/>
    <tiles:put name="editable" value="false"/>
    <tiles:put name="cssClassname" value="twitter-widget"/>

    <tiles:put name="viewPanel">
        <div class="twitter-iframe-widget">
            <a class="twitter-timeline"
               href="//twitter.com/sberbank"
               data-widget-id="${form.twitterId}"
               height="350">Твиты пользователя @sberbank</a>
        </div>
    </tiles:put>
</tiles:insert>
