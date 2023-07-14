<%@ page import="com.rssl.phizic.gate.GateSingleton" %>
<%@ page import="com.rssl.phizic.gate.currency.CurrencyRateService" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ include file="currentDate.jsp" %>

<c:if test="${phiz:impliesOperation('ShowCurrenciesRateOperation','CurrenciesRateInfo')}">
<c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
<c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>

<span class="infoTitle">Курсы валют</span>
<br/>
<br/>
				<table class="UserTab backTransparent" cellpadding="0" cellspacing="0">
				<tr style="padding-top:8px;" class="titleTable">
					<td>Валюта</td>
					<td>Покупка</td>
					<td class="titleTable">Продажа</td>
				</tr>
				<tr>
					<td class="ListItem">USD</td>
					<td class="ListItem" align="center" >
						<c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','USD', 'BUY', department, tarifPlanCodeType), 2)}"/>
						<c:choose>
							<c:when test="${rate != ''}">${rate}</c:when>
							<c:otherwise> &mdash; </c:otherwise>
						</c:choose>
					</td>
					<td class="ListItem" align="center">
					    <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('USD','RUB', 'SALE', department, tarifPlanCodeType), 2)}"/>
						<c:choose>
							<c:when test="${rate != ''}">${rate}</c:when>
							<c:otherwise> &mdash; </c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="ListItem">EUR</td>
					<td class="ListItem" align="center">
						<c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','EUR', 'BUY', department, tarifPlanCodeType), 2)}"/>
						<c:choose>
							<c:when test="${rate != ''}">${rate}</c:when>
							<c:otherwise> &mdash; </c:otherwise>
						</c:choose>
					</td>
					<td class="ListItem" align="center">
					    <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('EUR','RUB', 'SALE', department, tarifPlanCodeType), 2)}"/>
						<c:choose>
							<c:when test="${rate != ''}">${rate}</c:when>
							<c:otherwise> &mdash; </c:otherwise>
						</c:choose>
					</td>
				</tr>
</table>
</c:if>
<%@ include file="accounts/leftMenu.jsp" %>