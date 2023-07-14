<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>
<tiles:importAttribute/>

<%--
��������� ��� ����������� ��������� �������
name - �����
current - �������� �� ������� �������(true - ��������)
future - ���� true - ������� ����� ��������, ���� false - ����� �������
abs - true - ����������� ��������(������ ��������� �� �����)
width - ������ �����
--%>

<c:set var="additionClass" value="${abs ? 'abstract' : ''}"/>
<c:choose>
    <c:when test="${current}">
        <li class="step current ${additionClass}" <c:if test="${not empty width}">style="width:${width}"</c:if> >
            <span class="step_text">${name}</span>
            <i class="step_arr"></i>
        </li>
    </c:when>
    <c:when test="${future}">
        <li class="step future ${additionClass}" <c:if test="${not empty width}">style="width:${width}"</c:if> >
            <span class="step_text">${name}</span>
            <i class="step_arr"></i>
        </li>
    </c:when>
    <c:otherwise>
        <li class="step past ${additionClass}" <c:if test="${not empty width}">style="width:${width}"</c:if> >
            <span class="step_text">${name}</span>
            <i class="step_arr"></i>
        </li>
    </c:otherwise>
</c:choose>