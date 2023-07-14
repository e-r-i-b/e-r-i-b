<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%--
  User: Zhuravleva
  Date: 09.10.2007
  Time: 17:20:16
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
<c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>

<table cellpadding="0" cellspacing="0" border="0">
<tr>
	<td class="infoTitle"><br>Сегодня <%=DateHelper.toString(new Date())%></td>
</tr>
<tr>
	<td valign="top">
		<br>
		<table cellpadding="2" cellspacing="" class="rateOfExchange" border="0" width="200px">
		<tr>
			<td colspan="5" class="borderB"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
		<tr>
			<td class="borderT">&nbsp;</td>
			<td colspan="2" align="center" class="titleLeftMenu borderT orangeLine" width="100%">Курсы валют ЦБ</td>
			<td class="borderT">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="center" colspan="2" style="font-weight:normal;"><%=DateHelper.toString(new Date())%></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="center">USD</td>
			<td align="center" style="font-weight:normal;">
				<c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','USD', 'CB', department, tarifPlanCodeType), 2)}"/>
				<c:choose>
					<c:when test="${rate != ''}">${rate}</c:when>
					<c:otherwise>&mdash;</c:otherwise>
				</c:choose>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="center">EUR</td>
			<td align="center" style="font-weight:normal;">
				<c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','EUR', 'CB', department, tarifPlanCodeType), 2)}"/>
				<c:choose>
					<c:when test="${rate != ''}">${rate}</c:when>
					<c:otherwise>&mdash;</c:otherwise>
				</c:choose>
			<td>&nbsp;</td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
		</table>
	</td>
</tr>
</table>