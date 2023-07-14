<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--
 омпонент дл€ отображени€ одной вкладки на странице ѕлатежи и переводы
title -  название вкладки
active - активна€ вкладка
position -  определение позиции вкладки
onclick - действие по клику
largeText - признак необходимости разбиени€ текста вкладки на 2 строки
--%>

<%--
ƒл€ первого активного пункта меню  присваиваем классы activeSecondMenu greenFirst
ƒл€ первого неактивного пункта меню  присваиваем классы grayFirst newSecondMenuFirst

ƒл€ промежуточных активных пунктах меню  присваиваем класс activeSecondMenu

ƒл€ последнего активного пункта меню  присваиваем классы  activeSecondMenu greenLast
ƒл€ последнего неактивного пункта меню  присваиваем класс grayLast

≈сли 4 вкладки, то дл€ списка присваиваем класс newSecondMenu
≈сли 3 вкладки, то дл€ списка присваиваем классы newSecondMenu newSecondMenu3
≈сли 2 вкладки, то дл€ списка присваиваем классы newSecondMenu newSecondMenu2
--%>


<tiles:importAttribute/>
<c:set var="activeClass" value=""/>
<c:if test="${active == 'true' }">
    <c:set var="activeClass" value="activeSecondMenu"/>
</c:if>

<c:if test="${largeText == true}">
    <c:set var="largeClass" value="largeText"/>
</c:if>
<li id="${id}" class="${activeClass} ${largeClass}">
    <div class="leftCornerTab"></div>
    <div class="innerTab">
        <c:choose>
            <c:when test="${not empty action}">
                <html:link action="${action}" onclick="return redirectResolved();"><span>${title}</span></html:link>
            </c:when>
            <c:when test="${not empty url}">
                <phiz:link url="${fn:trim(url)}" onclick="return redirectResolved();"><span>${title}</span></phiz:link>
            </c:when>
            <c:when test="${not empty onclick}">
                <a href="#" onclick="${onclick}"><span>${title}</span></a>
            </c:when>
            <c:when test="${not empty id}">
                <a><span>${title}</span></a>
            </c:when>
            <c:otherwise>
                <a href="#"><span>${title}</span></a>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="rightCornerTab"></div>
</li>
