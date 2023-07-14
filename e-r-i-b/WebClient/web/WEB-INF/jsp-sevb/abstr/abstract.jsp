<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	.bdBottomRight{border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.bdRight{border-right:1px solid #000000;}
	.bdTop{border-top:1px solid #000000;}
	.bdBottomLeftRight{border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.bdLeftRight{border-right:1px solid #000000;border-left:1px solid #000000;}
</style>

<c:set var="accountAbstractMap" value="${form.accountAbstract}"/>
<c:set var="cardAbstractMap" value="${form.cardAbstract}"/>
<c:set var="user" value="${form.user}"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:choose>
	<c:when test="${empty accountAbstractMap and empty cardAbstractMap}">
			<table width="100%">
				<tr>
					<td align="center" class="messageTab"><br>
						Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;счета,<br>
						соответствующего&nbsp;заданному&nbsp;фильтру!
					</td>
				</tr>
			</table>
	</c:when>
	<c:otherwise>
	<div class="MaxSize" style="overflow:auto;">

	<c:set var="count" value="0"/>
		<c:forEach items="${accountAbstractMap}" var="listElement">


		<c:if test="${count > 0}"><br style="page-break-after:always;"></c:if>
		<c:set var="count" value="${count+1}"/>

			<c:set var="accountLink" value="${listElement.key}"/>
			<c:set var="account" value="${accountLink.value}"/>
			<c:set var="accountInfo" value="${accountLink.accountInfo}"/>
			<c:set var="abstract" value="${listElement.value}"/>

			<c:set var="formNumber" value="204-c"/>
			<c:set var="isCurrentAccount" value="${fn:startsWith(account.number, '40817') || fn:startsWith(account.number, '40820')}"/>
			
	<c:if test="${isCurrentAccount=='true'}">
		<c:set var="formNumber" value="319"/>
	</c:if>

		<table class="font10" style="width:150mm;margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm; font-family:Times New Roman;">
		<tr>
		<td>
		<table class="font10" width="100%" style="font-family:Times New Roman;">
			<tr>
				<td><img src="${globalImagePath}/miniLogoSbrf.jpg"/>
				</td>
				<td align="left" width="30%">
					<nobr>Сбербанк&nbsp;России</nobr>
				</td>
				<td align="right" width="70%">
					<nobr>Ф. № ${formNumber}</nobr>
				</td>
			</tr>
		</table>
		<tr>
			<td>
				${accountLink.office.name}
			</td>
		</tr>
		<tr>
			<td>
				№ ${accountLink.office.code.fields[branch]}/${accountLink.office.code.fields[office]}
			</td>
		</tr>
		<tr>
			<td align="center">
				Выписка из лицевого счета
			</td>
		</tr>
<c:choose>
	<c:when test="${isCurrentAccount=='true'}">
		<tr><td>&nbsp;</td></tr>
		<tr><td>Номер лицевого счета: №${account.number}</td></tr>
		<tr><td>Номер договора: № ${accountInfo.agreementNumber}</td></tr>
		<tr><td>Дата договора:  ${phiz:formatDateWithStringMonth(account.openDate)}г.</td></tr>
		<tr><td>Счет: ${account.description} в ${account.currency.code}</td></tr>
		<tr><td>Размер процентной ставки: ${account.interestRate}</td></tr>
		<tr><td>Владелец: ${accountLink.fullname}</td></tr>
		<tr>
			<td>
				Выписка составлена за период с ${phiz:formatDateWithStringMonth(abstract.fromDate)}г. по ${phiz:formatDateWithStringMonth(abstract.toDate)}г.
			</td>
		</tr>
		<c:if test="${not empty abstract.previousOperationDate}">
			<tr><td>Дата предыдущей операции по счету: ${phiz:formatDateWithStringMonth(abstract.previousOperationDate)} г.</td></tr>
		</c:if>
		<tr><td>Входящий остаток: <bean:write name="abstract" property="openingBalance.decimal" format="0.00"/></td></tr>

		<tr>
			<td>
				<table class="font8 bdTop" cellpadding="0" cellspacing="0"  width="100%" style="font-family:Times New Roman;">
				<tr>
					<td valign="top" class="textPadding bdLeftRight" align="center" width="11%">Дата совершения операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="11%">Номер документа</td>
					<td valign="top" class="textPadding bdRight" align="center" width="10%">Вид ( шифр) операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">Наименование операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">Номер корреспондирующего счета</td>
					<td colspan="2" class="textPadding bdBottomRight"align="center" width="25%">Сумма операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">Остаток по счету</td>
				</tr>
				<tr>
					<td class="bdBottomLeftRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight" align="center" width="12.5%">По Дт<br/>счета</td>
					<td class="bdBottomRight" align="center" width="12.5%">По Кт<br/>счета</td>
					<td class="bdBottomRight">&nbsp;</td>
				</tr>
					<c:set var="debitSummary" value= "0"/>
					<c:set var="creditSummary" value= "0"/>
					<c:forEach items="${abstract.transactions}" var="transaction">
						<tr>
							<td class="bdBottomLeftRight textPadding" align="center">
								<c:if test="${!empty transaction.date}">
									<bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight textPadding" align="center">
								<c:if test="${!empty transaction.documentNumber}">
									${transaction.documentNumber}
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight textPadding" align="center">
								<c:if test="${!empty transaction.operationCode}">
									${transaction.operationCode}
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight textPadding" align="center">
								<c:if test="${!empty transaction.description}">
									${transaction.description}
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight textPadding" align="center">
								<c:if test="${!empty transaction.counteragentAccount}">
									${transaction.counteragentAccount}
								</c:if>&nbsp;
							</td>							
							<td class="bdBottomRight textPadding" align="center">
								<c:if test="${!empty transaction.debitSum}">
									<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
									<c:set var="debitSummary" value= "${debitSummary+transaction.debitSum.decimal}"/>
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight textPadding" align="center" width="12.5%">
								<c:if test="${!empty transaction.creditSum}">
									<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
									<c:set var="creditSummary" value= "${creditSummary+transaction.creditSum.decimal}"/>
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight textPadding" align="center" width="12.5%">
								<c:if test="${!empty transaction.balance}">
									<bean:write name="transaction" property="balance.decimal" format="0.00"/>
								</c:if>&nbsp;
							</td>
						</tr>
					</c:forEach>
				<xsl:apply-templates select="row" mode="f204"/>
				<tr>
					<td colspan="2" align="center">Итого обороты</td>
					<td align="center">&nbsp;</td>
					<td align="center">&nbsp;</td>
					<td align="center">&nbsp;</td>					
					<td align="right" class="textPadding" width="12.5%"><bean:write name="debitSummary" format="0.00"/> </td>
					<td align="right" class="textPadding" width="12.5%"><bean:write name="creditSummary" format="0.00"/> </td>
					<td align="center">&nbsp;</td>
				</tr>
				</table>

			</td>
		</tr>
		
		<tr><td>Исходящий остаток: <bean:write name="abstract" property="closingBalance.decimal" format="0.00"/></td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td>
				СЧЕТ № ${account.number}
			</td>
		</tr>
		<tr>
			<xsl:variable name="curCode" select="currencyCode"/>
			<td>
				по вкладу: ${account.description} в ${account.currency.code}
			</td>
		</tr>
		<tr>
			<td>
				<c:choose>
					<c:when test="${empty accountInfo.closeDate}">
						Срок вклада: до востребования
					</c:when>
					<c:otherwise>
						Срок вклада: ${phiz:formatDateWithStringMonth(accountInfo.closeDate)} г.
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>
				Вкладчик: ${accountLink.fullname}
			</td>
		</tr>
		<tr>
			<td>
				Счет открыт: ${phiz:formatDateWithStringMonth(account.openDate)} г.
			</td>
		</tr>
		<tr>
			<td>
			   за период с ${phiz:formatDateWithStringMonth(abstract.fromDate)} г.по ${phiz:formatDateWithStringMonth(abstract.toDate)} г.
			</td>
		</tr>
		<c:if test="${not empty abstract.previousOperationDate}">
		<tr>
			<td>				
				Дата предыдущей операции по счету: ${phiz:formatDateWithStringMonth(abstract.previousOperationDate)} г.
			</td>
		</tr>
		</c:if>
		<tr>
			<td>
				Входящий остаток: <bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
			</td>
		</tr>
		</td>
		</tr>
		<tr>
			<td>
				<table class="font8 bdTop" cellpadding="0" cellspacing="0"  width="100%" style="font-family:Times New Roman;">
				<tr>
					<td valign="top" class="textPadding bdLeftRight" align="center" width="11%">Дата совершения операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="11%">Номер документа</td>
					<td valign="top" class="textPadding bdRight" align="center" width="10%">Вид ( шифр) операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">Наименование операции</td>
					<td colspan="2" class="textPadding bdBottomRight"align="center" width="25%">Сумма операции</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">Номер корреспондирующего счета</td>
				</tr>
				<tr>
					<td class="bdBottomLeftRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight" align="center" width="12.5%">По Дт<br/>счета</td>
					<td class="bdBottomRight" align="center" width="12.5%">По Кт<br/>счета</td>
					<td class="bdBottomRight">&nbsp;</td>
				</tr>
					<c:set var="debitSummary" value= "0"/>
					<c:set var="creditSummary" value= "0"/>
					<c:forEach items="${abstract.transactions}" var="transaction">
						<tr>
							<td class="textPadding bdBottomLeftRight" align="center">
								<c:if test="${!empty transaction.date}">
									<bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
								</c:if>&nbsp;
							</td>
							<td class="textPadding bdBottomRight" align="center">
								<c:if test="${!empty transaction.documentNumber}">
									${transaction.documentNumber}
								</c:if>&nbsp;
							</td>
							<td class="textPadding bdBottomRight" align="center">
								<c:if test="${!empty transaction.operationCode}">
									${transaction.operationCode}
								</c:if>&nbsp;
							</td>
							<td class="textPadding bdBottomRight" align="center">
								<c:if test="${!empty transaction.description}">
									${transaction.description}
								</c:if>&nbsp;
							</td>
							<td class="bdBottomRight" align="center" width="12.5%">
								<c:if test="${!empty transaction.debitSum}">
									<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
									<c:set var="debitSummary" value= "${debitSummary+transaction.debitSum.decimal}"/>
								</c:if>
							</td>
							<td class="bdBottomRight" align="center" width="12.5%">
								<c:if test="${!empty transaction.creditSum}">
									<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
									<c:set var="creditSummary" value= "${creditSummary+transaction.creditSum.decimal}"/>
								</c:if>
							</td>
							<td class="textPadding bdBottomRight" align="center">
								<c:if test="${!empty transaction.counteragentAccount}">
									${transaction.counteragentAccount}
								</c:if>&nbsp;
							</td>
						</tr>
					</c:forEach>
				<xsl:apply-templates select="row" mode="f204"/>
				<tr>
					<td colspan="2" align="center">Итого обороты</td>
					<td align="center">&nbsp;</td>
					<td align="center">&nbsp;</td>
					<td align="right" class="textPadding" width="12.5%"><bean:write name="debitSummary" format="0.00"/> </td>
					<td align="right" class="textPadding" width="12.5%"><bean:write name="creditSummary" format="0.00"/> </td>
					<td align="center">&nbsp;</td>
				</tr>
				</table>

			</td>
		</tr>
		<tr>
			<td>
				Исходящий остаток: <bean:write name="abstract" property="closingBalance.decimal" format="0.00"/> <br></br><br></br>
			</td>
		</tr>
		<tr>
			<td>
				Выписка составлена по запросу ${user.fullName}<br></br><br></br>
			</td>
		</tr>
		<tr>
			<td>
				Дата составления выписки: ${phiz:formatDateWithStringMonth(phiz:currentDate())} г.<br></br><br></br><br></br>
			</td>
		</tr>
		<tr>
			<td>
				Исполнитель:
			</td>
		</tr>

		</table>
	</c:otherwise>
</c:choose>
		</c:forEach>

		<%-- выписка по картам--%>
		<%@ include file="/WEB-INF/jsp-sevb/abstr/cardAbstract.jsp"%>
	</div>
	</c:otherwise>
</c:choose>