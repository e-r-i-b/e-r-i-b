<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<c:forEach var="currency" items="${currencyRates}" varStatus="iterator">
    <c:set var="currencyKey" value="${currency.key}"/>
    <c:set var="currencyValue" value="${currency.value}"/>
    <c:set var="notRateByTarifPlan">
        <td align="center">&mdash;</td>
        <td align="center">&mdash;</td>
    </c:set>
    <tr>
        <td class="sellingRate"><c:out value="${currencyKey.name}"/></td>
        <%--мапа с курсами покупки/продажи валют в разрезе тарифных планов--%>
        <c:forEach items="${tarifPlanCodeRatesOrder}" var="tarifPlanCode">
            <c:set var="rate" value="${currencyValue[tarifPlanCode]}"/>
            <c:choose>
                <c:when test="${!empty rate}">
                    <c:set var="buy" value="${rate.buy}"/>
                    <td align="center">
                        ${phiz:getFormattedCurrencyRate(buy,2)} ${phiz:getCurrencyName(rate.toCurrency)}
                    </td>
                    <c:set var="sale" value="${rate.sale}"/>
                    <td align="center">
                        ${phiz:getFormattedCurrencyRate(sale,2)} ${phiz:getCurrencyName(rate.toCurrency)}
                    </td>
                </c:when>
                <c:otherwise>${notRateByTarifPlan}</c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</c:forEach>
