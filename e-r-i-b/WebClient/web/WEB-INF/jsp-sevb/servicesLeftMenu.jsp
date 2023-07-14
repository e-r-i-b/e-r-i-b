<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%@ include file="currentDate.jsp" %>

<c:if test="${phiz:impliesOperation('ShowCurrenciesRateOperation','CurrenciesRateInfo')}">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
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
<script type="text/javascript">
   document.imgPath="${imagePath}/";
</script>
<br/>
<span class="infoTitle backTransparent">Сервис</span>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
	<td>
        <phiz:menuLink action="/private/service" serviceId="ChangePersonPassword" id="f1">
         Смена пароля<br>
        </phiz:menuLink>
    </td>
</table>