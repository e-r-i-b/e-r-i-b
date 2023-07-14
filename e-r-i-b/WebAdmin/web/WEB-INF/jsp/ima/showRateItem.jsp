<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tr>
    <td class="sellingRate">Курс покупки</td>
    <c:forEach items="${ratesCodesOrder}" var="code">
        <td>&nbsp;
            <c:set var="key" value="${code}${ratesTarifPlan}"/>
            <c:set var="rate" value="${rates[key]}"/>
            <c:choose>
                <c:when test="${!empty rate}">
                    <c:set var="buy" value="${rate.buy}"/>
                    <c:choose>
                        <c:when test="${buy.tarifPlanCodeType == ratesTarifPlan}">
                            ${phiz:getFormattedCurrencyRate(buy,2)} ${phiz:getCurrencyName(rate.toCurrency)}
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>&mdash;</c:otherwise>
            </c:choose>
        </td>
    </c:forEach>
</tr>
<tr>
    <td class="sellingRate">Курс продажи</td>
    <c:forEach items="${ratesCodesOrder}" var="code">
        <td>&nbsp;
            <c:set var="key" value="${code}${ratesTarifPlan}"/>
            <c:set var="rate" value="${rates[key]}"/>
            <c:choose>
                <c:when test="${!empty rate}">
                    <c:set var="sale" value="${rate.sale}"/>
                    <c:choose>
                        <c:when test="${sale.tarifPlanCodeType == ratesTarifPlan}">
                            ${phiz:getFormattedCurrencyRate(sale,2)} ${phiz:getCurrencyName(rate.toCurrency)}
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>&mdash;</c:otherwise>
            </c:choose>
        </td>
    </c:forEach>
</tr>