<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/deposit/info" onsubmit="return setEmptyAction(event)">
<bean:define id="deposit" name="ShowDepositInfoForm" property="deposit"/>
<bean:define id="depositInfo" name="ShowDepositInfoForm" property="depositInfo"/>
<script type="text/javascript" language="JavaScript">
	function Print()
	{
		var win = window.open("${phiz:calculateActionURL(pageContext, '/private/deposit/print.do')}?depositId=<%=request.getParameter("depositId")%>");
		win.resizeTo(800, 700);
		win.moveTo(100, 10);
	}
</script>
<tiles:insert definition="depositInfo">
<tiles:put name="pageTitle" type="string">
	<bean:message key="application.title" bundle="depositInfoBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.print.help"/>
		<tiles:put name="bundle" value="depositInfoBundle"/>
		<tiles:put name="onclick" value="Print()"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.toList"/>
		<tiles:put name="commandHelpKey" value="button.toList.help"/>
		<tiles:put name="bundle" value="depositInfoBundle"/>
		<tiles:put name="action" value="/private/accounts.do"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="text" value="Информация по вкладу"/>		
		<tiles:put name="image" value="iconMid_deposits.gif"/>
		<tiles:put name="data">
			<tr class="ListLine0">
				<td class="ListItem">Название вида вклада</td>
				<td class="ListItem">
					<bean:write name="deposit" property="description"/>
					&nbsp;</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма вклада</td>
				<td class="ListItem">
					<nobr>
						<bean:write name="deposit" property="amount.decimal" format="0.00"/>

					</nobr>
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Валюта вклада</td>
				<td class="ListItem">
					<bean:write name="deposit" property="amount.currency.code"/>
					&nbsp;</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Срок вклада</td>
				<td class="ListItem">
					<c:if test="${not empty deposit.duration}">
						<bean:write name="deposit" property="duration"/>
						дней
					</c:if>
					&nbsp;</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Процентная ставка</td>
				<td class="ListItem">
						<bean:write name="deposit" property="interestRate"/>&nbsp;
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Номер депозитного счета</td>
				<td class="ListItem">
					<bean:write name="depositInfo" property="account.number"/>
					&nbsp;</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Признак пролонгации вклада на следующий срок</td>
				<td class="ListItem">
					<%--<c:set var="renewal" value="depositInfo.renewalAllowed"/>--%>
					<bean:define id="renewal" name="depositInfo" property="renewalAllowed"/>
					<c:choose>
						<c:when test="${renewal}">
							Пролонгируемый
						</c:when>
						<c:otherwise>
							Непролонгируемый
						</c:otherwise>
					</c:choose>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма неснижаемого остатка вклада</td>
				<td class="ListItem">
					<c:choose>
						<c:when test="${!empty depositInfo.minBalance}">
							<bean:write name="depositInfo" property="minBalance.decimal"/>
						</c:when>
						<c:otherwise>
							<bean:write name="deposit" property="amount.decimal" format="0.00"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Номер счета для перечисления причисленных процентов</td>
				<td class="ListItem">
					<c:if test="${!empty depositInfo.percentAccount}">
						<bean:write name="depositInfo" property="percentAccount.number"/>
					</c:if>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Номер счета для перечисления средств по истечении срока вклада</td>
				<td class="ListItem">
					<logic:iterate id="entry" name="ShowDepositInfoForm" property="accountsSet">
						<bean:define id="account" name="entry" property="key"/>
						<bean:write name="account" property="number"/>&nbsp;
					</logic:iterate>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Текущее состояние</td>
				<td class="ListItem">
					<c:choose>
						<c:when test="${deposit.state=='open'}">
							Открыт
						</c:when>
						<c:otherwise>
							Закрыт
						</c:otherwise>
					</c:choose>
					&nbsp;</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Дата открытия</td>
				<td class="ListItem">
					<c:if test="${!empty deposit.openDate}">
						<bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>
					</c:if>&nbsp;
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Дата закрытия</td>
				<td class="ListItem">
					<c:choose>
						<c:when test="${deposit.state=='closed'}">
							<c:if test="${!empty deposit.closeDate}">
								<bean:write name="deposit" property="closeDate.time" format="dd.MM.yyyy"/>
							</c:if>
						</c:when>
						<c:otherwise>
                            <c:if test="${!empty deposit.endDate}">
							    <bean:write name="deposit" property="endDate.time" format="dd.MM.yyyy"/>
                            </c:if>
						</c:otherwise>
					</c:choose>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Номер договора</td>
				<td class="ListItem">
					<bean:write name="depositInfo" property="agreementNumber"/>
					&nbsp;</td>
			</tr>
		</tiles:put>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
