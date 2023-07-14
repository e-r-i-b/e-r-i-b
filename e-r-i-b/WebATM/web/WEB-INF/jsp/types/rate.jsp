<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<%--
  Компонента для формирования курса валюты
  mainCode - ISO код главной валюты
  code - код второй валюты
  department - департамент текущего пользователя
--%>

<c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
<c:set var="rate" value="${phiz:getRateByDepartment(code, mainCode, 'BUY_REMOTE', department, tarifPlanCodeType)}"/>
<%-- Покупка валюты code --%>
<c:if test="${rate != null}">
    <rate>                               <%-- ${rate}RUB = 1USD --%>
        <from>
            <code>${mainCode}</code>
            <amount>${phiz:getFormattedCurrencyRate(rate, 4)}</amount>
        </from>
        <to>
            <code>${phiz:normalizeMetalCode(code)}</code>
        </to>
        <c:if test="${rate.dynamicExchangeRate != 'NONE'}">
            <trend>${rate.dynamicExchangeRate}</trend>
        </c:if>
    </rate>
</c:if>
<%-- Продажа валюты code --%>
<c:set var="rate" value="${phiz:getRateByDepartment(code, mainCode, 'SALE_REMOTE', department, tarifPlanCodeType)}"/>
<c:if test="${rate != null}">
    <rate>
        <from>
            <code>${phiz:normalizeMetalCode(code)}</code>
        </from>
        <to>
            <code>${mainCode}</code>
            <amount>${phiz:getFormattedCurrencyRate(rate, 4)}</amount>
        </to>                          <%-- 1USD = ${rate}RUB --%>
        <c:if test="${rate.dynamicExchangeRate != 'NONE'}">
            <trend>${rate.dynamicExchangeRate}</trend>
        </c:if>
    </rate>
</c:if>