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
						��&nbsp;�������&nbsp;��&nbsp;������&nbsp;�����,<br>
						����������������&nbsp;���������&nbsp;�������!
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
					<nobr>��������&nbsp;������</nobr>
				</td>
				<td align="right" width="70%">
					<nobr>�. � ${formNumber}</nobr>
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
				� ${accountLink.office.code.fields[branch]}/${accountLink.office.code.fields[office]}
			</td>
		</tr>
		<tr>
			<td align="center">
				������� �� �������� �����
			</td>
		</tr>
<c:choose>
	<c:when test="${isCurrentAccount=='true'}">
		<tr><td>&nbsp;</td></tr>
		<tr><td>����� �������� �����: �${account.number}</td></tr>
		<tr><td>����� ��������: � ${accountInfo.agreementNumber}</td></tr>
		<tr><td>���� ��������:  ${phiz:formatDateWithStringMonth(account.openDate)}�.</td></tr>
		<tr><td>����: ${account.description} � ${account.currency.code}</td></tr>
		<tr><td>������ ���������� ������: ${account.interestRate}</td></tr>
		<tr><td>��������: ${accountLink.fullname}</td></tr>
		<tr>
			<td>
				������� ���������� �� ������ � ${phiz:formatDateWithStringMonth(abstract.fromDate)}�. �� ${phiz:formatDateWithStringMonth(abstract.toDate)}�.
			</td>
		</tr>
		<c:if test="${not empty abstract.previousOperationDate}">
			<tr><td>���� ���������� �������� �� �����: ${phiz:formatDateWithStringMonth(abstract.previousOperationDate)} �.</td></tr>
		</c:if>
		<tr><td>�������� �������: <bean:write name="abstract" property="openingBalance.decimal" format="0.00"/></td></tr>

		<tr>
			<td>
				<table class="font8 bdTop" cellpadding="0" cellspacing="0"  width="100%" style="font-family:Times New Roman;">
				<tr>
					<td valign="top" class="textPadding bdLeftRight" align="center" width="11%">���� ���������� ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="11%">����� ���������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="10%">��� ( ����) ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">������������ ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">����� ������������������ �����</td>
					<td colspan="2" class="textPadding bdBottomRight"align="center" width="25%">����� ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">������� �� �����</td>
				</tr>
				<tr>
					<td class="bdBottomLeftRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight" align="center" width="12.5%">�� ��<br/>�����</td>
					<td class="bdBottomRight" align="center" width="12.5%">�� ��<br/>�����</td>
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
					<td colspan="2" align="center">����� �������</td>
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
		
		<tr><td>��������� �������: <bean:write name="abstract" property="closingBalance.decimal" format="0.00"/></td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td>
				���� � ${account.number}
			</td>
		</tr>
		<tr>
			<xsl:variable name="curCode" select="currencyCode"/>
			<td>
				�� ������: ${account.description} � ${account.currency.code}
			</td>
		</tr>
		<tr>
			<td>
				<c:choose>
					<c:when test="${empty accountInfo.closeDate}">
						���� ������: �� �������������
					</c:when>
					<c:otherwise>
						���� ������: ${phiz:formatDateWithStringMonth(accountInfo.closeDate)} �.
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>
				��������: ${accountLink.fullname}
			</td>
		</tr>
		<tr>
			<td>
				���� ������: ${phiz:formatDateWithStringMonth(account.openDate)} �.
			</td>
		</tr>
		<tr>
			<td>
			   �� ������ � ${phiz:formatDateWithStringMonth(abstract.fromDate)} �.�� ${phiz:formatDateWithStringMonth(abstract.toDate)} �.
			</td>
		</tr>
		<c:if test="${not empty abstract.previousOperationDate}">
		<tr>
			<td>				
				���� ���������� �������� �� �����: ${phiz:formatDateWithStringMonth(abstract.previousOperationDate)} �.
			</td>
		</tr>
		</c:if>
		<tr>
			<td>
				�������� �������: <bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
			</td>
		</tr>
		</td>
		</tr>
		<tr>
			<td>
				<table class="font8 bdTop" cellpadding="0" cellspacing="0"  width="100%" style="font-family:Times New Roman;">
				<tr>
					<td valign="top" class="textPadding bdLeftRight" align="center" width="11%">���� ���������� ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="11%">����� ���������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="10%">��� ( ����) ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">������������ ��������</td>
					<td colspan="2" class="textPadding bdBottomRight"align="center" width="25%">����� ��������</td>
					<td valign="top" class="textPadding bdRight" align="center" width="15%">����� ������������������ �����</td>
				</tr>
				<tr>
					<td class="bdBottomLeftRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight">&nbsp;</td>
					<td class="bdBottomRight" align="center" width="12.5%">�� ��<br/>�����</td>
					<td class="bdBottomRight" align="center" width="12.5%">�� ��<br/>�����</td>
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
					<td colspan="2" align="center">����� �������</td>
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
				��������� �������: <bean:write name="abstract" property="closingBalance.decimal" format="0.00"/> <br></br><br></br>
			</td>
		</tr>
		<tr>
			<td>
				������� ���������� �� ������� ${user.fullName}<br></br><br></br>
			</td>
		</tr>
		<tr>
			<td>
				���� ����������� �������: ${phiz:formatDateWithStringMonth(phiz:currentDate())} �.<br></br><br></br><br></br>
			</td>
		</tr>
		<tr>
			<td>
				�����������:
			</td>
		</tr>

		</table>
	</c:otherwise>
</c:choose>
		</c:forEach>

		<%-- ������� �� ������--%>
		<%@ include file="/WEB-INF/jsp-sevb/abstr/cardAbstract.jsp"%>
	</div>
	</c:otherwise>
</c:choose>