<%--
  User: IIvanova
  Date: 16.05.2006
  Time: 13:04:53
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loan/claim/show">
<c:set var="frm" value="${ShowLoanClaimForm}"/>
<c:set var="claim" value="${frm.claim}"/>
<c:set var="claimAttributes" value="${claim.attributes}"/>
<tiles:insert definition="loanAnonymousMain">
	<tiles:put name="pageTitle" type="string">
		Просмотр результата рассмотрения заявки на кредит.
	</tiles:put>
	<tiles:put name="submenu" type="string" value="LoanClaimList"/>


<tiles:put name="menu" type="string">
	<c:if test="${claim.state.code == 'H'}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.edit"/>
			<tiles:put name="commandHelpKey" value="button.edit"/>
			<tiles:put name="image" value="edit.gif"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="action" value="/loan/claim.do?guid=${frm.guid}"/>
		</tiles:insert>
	</c:if>
	<c:if test="${claim.state.code != 'H'}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.show"/>
			<tiles:put name="commandHelpKey" value="button.show"/>
			<tiles:put name="bundle" value="loansBundle"/>
			<tiles:put name="action" value="/loan/claim/view.do?guid=${frm.guid}"/>
		</tiles:insert>
	</c:if>
</tiles:put>
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
	<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon" style="width:550px;">
		<tr>
			<td align="right" valign="middle"><img src="${imagePath}/CreditClaim.gif" border="0"/></td>
			<td colspan="2" >
				<table class="MaxSize paymentTitleFon">
				<tr>
					<td align="center" class="silverBott paperTitle">
						<table height="100%" width="340px" cellspacing="0" cellpadding="0" class="paymentTitleFon">
							<tr>
								<td class="titleHelp">
									<span class="formTitle">Информация по заявке на кредит.</span>																		
								</td>
							</tr>
						</table>
					</td>
					<td width="100%">&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Номер заявки:</b></td>
			<td>
				<bean:write name='claim' property="documentNumber"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll"><b>&nbsp;&nbsp;ФИО клиента:</b></td>
			<td>
				<c:out value="${claimAttributes['surname'].stringValue}"/>
				<c:out value="${claimAttributes['name'].stringValue}"/>
				<c:out value="${claimAttributes['patronymic'].stringValue}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Кредитный продукт:</b></td>
			<td>
				<c:out value="${claimAttributes['product-name'].stringValue}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Сумма кредита:</b></td>
			<td>
				<c:out value="${claimAttributes['client-request-amount'].stringValue}"/>&nbsp;
				<c:out value="${claimAttributes['loan-currency'].stringValue}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Срок кредита:</b></td>
			<td>
				<c:out value="${claimAttributes['client-request-term'].stringValue}"/>&nbsp;месяцев
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Статус заявки:</b></td>
			<td>
				<bean:message key="claim.state.${claim.state.code}" bundle="loansBundle"/>
			</td>
		</tr>
		<c:if test="${not empty claimAttributes['bank-comment'].stringValue}">
			<tr>
				<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Комментарий сотрудника банка:</b></td>
				<td>
					<c:out value="${claimAttributes['bank-comment'].stringValue}"/>
				</td>
			</tr>
		</c:if>
		<tr><td colspan="2">&nbsp;</td></tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>