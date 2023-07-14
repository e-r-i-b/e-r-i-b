<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
    Область на странице (центральная, боковое меню и т.п.)
    widgetContainerCodename - кодовое имя виджет-контейнера (или пусто, если контейнер не нужен)
--%>
<%--@elvariable id="widgetContainerCodename" type="java.lang.String"--%>

<c:catch var="errorWidgetContainer">
    <%-- Контейнер с виджетами --%>
    <c:if test="${not empty widgetContainerCodename}">
        <c:url var="widgetContainerURL" value="/private/async/widgetContainer.do" context="/">
            <c:param name="codename" value="${widgetContainerCodename}"/>
            <c:param name="operation" value=""/>
        </c:url>
        <tiles:insert page="${widgetContainerURL}" flush="false"/>
    </c:if>
</c:catch>

<%-- TODO (виджеты): что делаем с ошибкой в контейнере? --%>
<c:if test="${not empty errorWidgetContainer}">
    ${phiz:writeLogMessage(errorWidgetContainer)}
</c:if>
