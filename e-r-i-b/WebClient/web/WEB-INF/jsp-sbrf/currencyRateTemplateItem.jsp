<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
 ��������� ��� ����������� ����� ������/������� � ����� ������ ����� "currencyRateTemplate"

 fromCurrencyCode - ��� ������/������� "��" (���� ������ ������������ �����, �� ����� ������ "�" - "RUB")
 fromCurrencyTitle - ������������ ������������ ������ "��"
 needShowDynamic - ����� �� ���������� ����������� �������� ��������� ����� (�� ��������� "false")
--%>
<tiles:importAttribute/>
<c:set var="nationalCurrency" value="RUB"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<c:set var="tarifPlanCodeType" scope="request" value="${empty tarifPlanCodeType ? phiz:getActivePersonTarifPlanCode() : tarifPlanCodeType}"/>

<div class="rateValues">
    <%--������--%>
    <div class="currencyRateName">${fromCurrencyTitle}</div>

    <%--���� �������--%>
    <div class="rateText">
        <c:set var="rate" value="${phiz:getRateByDepartment(fromCurrencyCode, nationalCurrency, 'BUY_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
        ${formattedRate != '' ? formattedRate : '&mdash;'}

        <%--�������� �����--%>
        <c:if test="${needShowDynamic}">
            <c:choose>
                <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                    <img src="${image}/course_up.png" alt=""/>
                </c:when>
                <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                    <img src="${image}/course_down.png" alt=""/>
                </c:when>
                <c:otherwise>
                    &nbsp&nbsp&nbsp
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>

    <%--���� �������--%>
    <div class="rateText">
        <c:set var="rate" value="${phiz:getRateByDepartment(fromCurrencyCode, nationalCurrency, 'SALE_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
        ${formattedRate != '' ? formattedRate : '&mdash;'}

        <%--�������� �����--%>
        <c:if test="${needShowDynamic}">
            <c:choose>
                <c:when test="${rate != null && rate.dynamicExchangeRate == 'UP'}">
                    <img src="${image}/course_up.png" alt=""/>
                </c:when>
                <c:when test="${rate != null && rate.dynamicExchangeRate == 'DOWN'}">
                    <img src="${image}/course_down.png" alt=""/>
                </c:when>
                <c:otherwise>
                    &nbsp&nbsp&nbsp
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
</div>