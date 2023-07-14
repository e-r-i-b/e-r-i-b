<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--
��������� ��� ����������� ����� ������� �� �������� ������� � ��������
title -  �������� �������
active - �������� �������
position -  ����������� ������� �������
onclick - �������� �� �����
largeText - ������� ������������� ��������� ������ ������� �� 2 ������
--%>

<%--
��� ������� ��������� ������ ����  ����������� ������ activeSecondMenu greenFirst
��� ������� ����������� ������ ����  ����������� ������ grayFirst newSecondMenuFirst

��� ������������� �������� ������� ����  ����������� ����� activeSecondMenu

��� ���������� ��������� ������ ����  ����������� ������  activeSecondMenu greenLast
��� ���������� ����������� ������ ����  ����������� ����� grayLast

���� 4 �������, �� ��� ������ ����������� ����� newSecondMenu
���� 3 �������, �� ��� ������ ����������� ������ newSecondMenu newSecondMenu3
���� 2 �������, �� ��� ������ ����������� ������ newSecondMenu newSecondMenu2
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
