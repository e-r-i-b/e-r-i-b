<%--
  User: Zhuravleva
  Date: 02.11.2007
  Time: 12:59:29
--%>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
<c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>

<table cellpadding="0" cellspacing="0" class="MaxSize">
	<br/>
    <br/>
    <tr>
		<td><b class="headLinksLeftMenu">
			Cегодня: <span class="menuInsertText backTransparent">
     	       <%=DateHelper.toString(new Date())%></span></b>
		</td>
	</tr>
	<tr><td>
		<table class="linkTab backTransparent" width="80%"><tr><td>&nbsp;</td></tr></table>
	</td></tr>
	<tr><td class="headLinksLeftMenu">Курсы валют</td></tr>
		<tr>
			<td align="center">
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
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<c:if test="${phiz:writeCard() == 'true'}">
		<tr><td align="center"><img src="${imagePath}/sectionLeftMenu.gif" alt="" width="98" height="3" border="0"></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="headLinksLeftMenu">Активная карта ключей:</td></tr>
			<tr>
				<td>
					<c:set var="card" value="${phiz:activeCard()}"/>
					<c:choose>
						<c:when test="${card != null}">
							<c:set var="date" value="${card.activationDate}"/>
							<c:set var="style" value="menuInsertText backTransparent"/>
							<c:if test="${card.validPasswordsCount < 5}">
								<c:set var="style" value="attention"/>
							</c:if>
				  <span>Номер:&nbsp;${card.number}&nbsp;<span><br/>
				<span>Дата активации:&nbsp;<%out.println(String.format("%1$td.%1$tm.%1$tY", new Object[]{pageContext.getAttribute("date")}));%><span><br/>
				<span>Количество ключей:&nbsp;${card.passwordsCount}/${card.validPasswordsCount}<span><br/>
						</c:when>
						<c:otherwise>
                       <span class="attention">
	                       У вас нет активной карты ключей
	                   </span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		<tr><td>&nbsp;</td></tr>
		</c:if>
</table>