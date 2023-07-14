<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 08.12.2008
  Time: 12:17:11
  To change this template use File | Settings | File Templates.

  выписка по картам
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<c:forEach items="${cardAbstractMap}" var="listElement">

		<c:if test="${count > 0}"><br style="page-break-after:always;"></c:if>
		<c:set var="count" value="${count+1}"/>

		<c:set var="cardLink" value="${listElement.key}"/>
		<c:set var="card" value="${cardLink.value}"/>
		<c:set var="cardInfo" value="${cardLink.card}"/>
		<c:set var="abstract" value="${listElement.value}"/>

		<table class="font10" style="width:150mm;margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm; font-family:Times New Roman;">
		<tr>
			<td>
				<img src="${globalImagePath}/sbrfLogoCut.jpg"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<b>Выписка о 10 последних операциях по карте</b>
			</td>
		</tr>
		<tr>
			<td>
				Карта:  ${phiz:getCutCardNumber(card.number)}
			</td>
		</tr>
		<tr>
			<td>
				Держатель карты: ${card.ownerFirstName} ${card.ownerLastName}
			</td>
		</tr>
		<tr>
			<td>
				Вам доступно: <bean:write name="cardInfo" property="balance.decimal" format="0.00"/>
			</td>
		</tr>
		<tr>
			<td>
				Из них:
			</td>
		</tr>
		<tr>
			<td>
				<table class="font10" width="100%" style="font-family:Times New Roman;">
					<tr>
						<td width="40px" rowspan="2">&nbsp;</td>
						<td align="left">Для оплаты товаров и услуг: <bean:write name="cardInfo" property="purchaseLimit.decimal" format="0.00"/></td>
					</tr>
					<tr>
						<td align="left">Для получения наличных: <bean:write name="cardInfo" property="cashLimit.decimal" format="0.00"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="font8 bdTop" cellpadding="0" cellspacing="0"  width="100%" style="font-family:Times New Roman;">
				<tr>
					<td valign="top" class="bdBottomLeftRight textPadding" align="center" width="11%"><b>Дата операции</b></td>
					<td valign="top" class="bdBottomRight textPadding" align="center" width="11%"><b>Сумма операции</b></td>
					<td valign="top" class="bdBottomRight textPadding" align="center" width="10%"><b>Валюта</b></td>
					<td valign="top" class="bdBottomRight textPadding" align="center" width="15%"><b>Тип операции</b></td>
				</tr>
					<c:forEach items="${abstract.transactions}" var="transaction">
						<tr>
							<td class="bdBottomLeftRight textPadding" align="center">
								<c:if test="${!empty transaction.date}">
									<bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
								</c:if>&nbsp;
							</td>

							<c:if test="${!empty transaction.debitSum}">
								<td class="bdBottomRight textPadding" align="center">
									+<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
									<c:set var="debitSummary" value= "${debitSummary+transaction.debitSum.decimal}"/>
									</td>
								<td class="bdBottomRight textPadding" align="center">${transaction.debitSum.currency.code}</td>
							</c:if>
							<c:if test="${!empty transaction.creditSum}">
								<td class="bdBottomRight textPadding" align="center">
									-<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
									<c:set var="creditSummary" value= "${creditSummary+transaction.creditSum.decimal}"/>
								</td>
								<td class="bdBottomRight textPadding" align="center">${transaction.creditSum.currency.code}</td>
							</c:if>
							<td class="bdBottomRight textPadding" align="center">&nbsp;
								<c:if test="${!empty transaction.description}">
									${transaction.description}
								</c:if>&nbsp;
							</td>
						</tr>
					</c:forEach>
				</table>

			</td>
		</tr>

		</table>
		</c:forEach>
