<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<c:set var="widgetPictureBaseURL" value="${globalUrl}/commonSkin/images/widget-picture"/>
<c:set var="widgetActionURL" value="${phiz:calculateActionURL(pageContext, path)}"/>

<c:set var="widgetPicture" value="${picture}"/>
<c:if test="${empty widgetPicture}">
    <%-- Если картинка не указана явно, определяем по кодовому имени --%>
    <c:set var="widgetPicture">${codename}.png</c:set>
</c:if>

<div data-dojo-type="widget.WidgetPicture" class="WidgetPicture">
    <script type="dojo/connect">
        this.codename    = "${codename}";
        this.username    = "${username}";
        this.url         = "${widgetActionURL}";
        this.initialSize = "${initialSize}";
        this.sizeable    = ${sizeable == 'true'};
        this.definition  = ${definition};
    </script>
    <c:if test="${isNovelty}">
        <img src="${globalUrl}/commonSkin/images/widget-novelty.gif" class="widget-novelty"/>
    </c:if>

    <div class="icon" align="center">
        <img src="${widgetPictureBaseURL}/${widgetPicture}" alt="" border="0"/>
    </div>
    <div class="text">
        <div class="title" title="${username}"><c:out value="${username}"/></div>
        <span class="annotation"><c:out value="${description}"/></span>
    </div>

    <div button="add" class="button" align="center">добавить</div>
</div>
