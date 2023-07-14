<%--
  Created by IntelliJ IDEA.
  User: kligina
  Date: 02.02.2011
  Time: 11:19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--
Компонент для отображения одной вкладки
style - стиль вкладки (активная/неактиваная)
title - название вкладки или заголовок иконки
iconUrl - путь к иконке
action - struts action куда осуществляется переход при нажатии на вкладки
--%>

<tiles:importAttribute/>

<%--общее количество закладок--%>
<c:if test="${empty tabItemCounter}">
    <c:set var="tabItemCounter" value="0" scope="request"/>
</c:if>
<c:set var="tabItemCounter" value="${tabItemCounter + 1}" scope="request"/>

<%--количество закладок с иконками--%>
<c:if test="${!empty iconUrl}">
    <c:if test="${empty iconCounter}">
        <c:set var="iconCounter" value="0" scope="request"/>
    </c:if>
    <c:set var="iconCounter" value="${iconCounter + 1}" scope="request"/>
</c:if>

<c:choose>
    <%--если закладка с иконкой, то отображаем ее с max-width = 4% (определено в классе icon)--%>
    <c:when test="${!empty iconUrl}">
        <li class="${style} icon">
            <c:if test="${fn:startsWith(style,'active')}">
                <a href="#">
                    <img src="${iconUrl}" alt="${title}" title="${title}"/>
                </a>
            </c:if>
            <c:if test="${fn:startsWith(style,'inactive')}">
                <html:link action="${action}" onclick="return redirectResolved();">
                    <img src="${iconUrl}" alt="${title}" title="${title}"/>
                </html:link>
            </c:if>
        </li>
    </c:when>
    <%--если закладка с текстом, то max-width рассчитывается в tabs.jsp и определяется в классе text--%>
    <c:otherwise>
        <li class="${style} text">
            <c:if test="${fn:startsWith(style,'active')}">
                <a href="#" >${title}</a>
            </c:if>
            <c:if test="${fn:startsWith(style,'inactive')}">
                <html:link action="${action}" onclick="return redirectResolved();">${title}</html:link>
            </c:if>
        </li>
    </c:otherwise>
</c:choose>
