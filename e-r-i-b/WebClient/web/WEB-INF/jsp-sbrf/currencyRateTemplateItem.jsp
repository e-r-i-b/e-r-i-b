<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
 Компонент для отображения курса валюты/металла в блоке курсов валют "currencyRateTemplate"

 fromCurrencyCode - код валюты/металла "из" (курс всегда относительно рубля, по этому валюта "в" - "RUB")
 fromCurrencyTitle - отображаемое наименование валюты "из"
 needShowDynamic - нужно ли отображать треугольник динамики изменения курса (по умолчанию "false")
--%>
<tiles:importAttribute/>
<c:set var="nationalCurrency" value="RUB"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<c:set var="tarifPlanCodeType" scope="request" value="${empty tarifPlanCodeType ? phiz:getActivePersonTarifPlanCode() : tarifPlanCodeType}"/>

<div class="rateValues">
    <%--Валюта--%>
    <div class="currencyRateName">${fromCurrencyTitle}</div>

    <%--Курс покупки--%>
    <div class="rateText">
        <c:set var="rate" value="${phiz:getRateByDepartment(fromCurrencyCode, nationalCurrency, 'BUY_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
        ${formattedRate != '' ? formattedRate : '&mdash;'}

        <%--Динамика курса--%>
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

    <%--Курс продажи--%>
    <div class="rateText">
        <c:set var="rate" value="${phiz:getRateByDepartment(fromCurrencyCode, nationalCurrency, 'SALE_REMOTE', currentDepartment4Rate, tarifPlanCodeType)}"/>
        <c:set var="formattedRate" value="${phiz:getFormattedCurrencyRate(rate, 2)}"/>
        ${formattedRate != '' ? formattedRate : '&mdash;'}

        <%--Динамика курса--%>
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