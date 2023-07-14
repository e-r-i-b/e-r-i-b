<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:if test="${not empty form.depositProducts}">
    <c:forEach items="${form.depositProducts}" var="depositEntities">
        <c:set var="tariff" value="${depositEntities.key}"/>
        <c:set var="tariffDeposits" value="${depositEntities.value}"/>


        <div class="depositTariffTitle">
            <c:forEach var="tariffPlan" items="${tariffPlans}">
                <c:if test="${tariffPlan.code == tariff}">
                    <c:out value="${tariffPlan.name}"/>
                </c:if>
            </c:forEach>
        </div>

        <%--Описание вкладных продуктов--%>
        <c:choose>
            <c:when test="${not empty tariffDeposits}">
                <div class="depositTariff">
                    <c:forEach items="${tariffDeposits}" var="depositEntity">

                        <c:if test="${not empty depositEntity.depositRates}">

                            <c:set var="period" value="${phiz:preparePeriods(depositEntity.periodList)}"/>

                            <div class="depositProductItem">
                                <div class="depositProductTitle">
                                    <span class="depositInfoLink link" onclick="editDepositEntityDetailProduct(${depositEntity.depositType}, ${tariff}, ${depositEntity.groupCode});return false;">
                                        <h2>
                                            ${depositEntity.groupName}
                                            (<c:if test="${not empty depositEntity.periodList}">на срок</c:if>${period})
                                        </h2>
                                    </span>
                                </div>

                                <table class="depositProductInfo">
                                    <tr class="borderBottom">
                                        <th class="clientMinFee">
                                            <c:choose>
                                                <c:when test="${depositEntity.withMinimumBalance}">
                                                    Неснижаемый остаток
                                                </c:when>
                                                <c:otherwise>
                                                    Минимальный взнос
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <th class="clientDepositCurrency">Валюта вклада</th>
                                        <th class="clientDepositRate">Процентная ставка</th>
                                    </tr>
                                    <c:forEach items="${depositEntity.depositDescriptions}" var="row" varStatus="rowNum">
                                        <tr>
                                            <td>
                                                От <c:out value="${phiz:formatBigDecimal(row.sumBegin)}"/>
                                            </td>
                                            <td>
                                                <c:out value="${phiz:getISOCodeExceptingRubles(row.currency)}"/>
                                            </td>
                                            <td>
                                                <div>
                                                    <c:choose>
                                                        <c:when test="${row.minRate != row.maxRate}">
                                                            <c:out value="${phiz:formatBigDecimal(row.minRate)}"/>&nbsp;...&nbsp;<c:out value="${phiz:formatBigDecimal(row.maxRate)}%"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${phiz:formatBigDecimal(row.minRate)}%"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="depositProductAddInfo">
                    Не найдены данные, удовлетворяющие условиям фильтра.
                </div>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</c:if>

