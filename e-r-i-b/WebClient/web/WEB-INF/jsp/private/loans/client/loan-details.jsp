<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/loans/info" onsubmit="return setEmptyAction(event)">
<c:set var="isPrintForm" value="${not(empty param['print'])}"/>
<c:choose>
    <c:when test="${isPrintForm}">
         <c:set var="layout" value="loanPrint"/>
    </c:when>
    <c:otherwise>
        <c:set var="layout" value="accountInfoBundle"/>
    </c:otherwise>
</c:choose>


<script type="text/javascript" language="JavaScript">
	function print()
	{
		var win = window.open("${phiz:calculateActionURL(pageContext, '/private/loans/info.do')}?loanId=<%=request.getParameter("loanId")%>&print=true");
	}

	//количество месяцев в дате
	function monthsCount(date)
	{
		var year  = parseInt(date.substring(0,2));
		var month = parseInt(date.substring(3,5));
		var days  = parseInt(date.substring(6,date.length));

		return year*12 + month + days/30;
	}
</script>

<tiles:insert definition="${layout}">
<tiles:put name="pageTitle" type="string" value="Информация по кредиту">
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.print.help"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="onclick" value="print()"/>
		<tiles:put name="image" value=""/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.toList"/>
		<tiles:put name="commandHelpKey" value="button.toList.help"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="action" value="/private/accounts.do"/>
		<tiles:put name="image" value=""/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${LoanDetailsForm}"/>
	<c:set var="loan" value="${form.loan}"/>
	<c:set var="scheduleItem" value="${form.scheduleItem}"/>
	<c:set var="currentDate" value="<%=DateHelper.getCurrentDate().getTime()%>"/>
	<c:set var="person" value="${form.person}"/>
	<c:if test="${isPrintForm}">
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value=""/>
			<tiles:put name="image" value=""/>
			<tiles:put name="text" value=""/>
			<tiles:put name="data">
			<tr style="font-size:12pt;font-weight:bold;padding:20px;">
				<td colspan="2">
					Информация кредитному договору № <bean:write name="loan" property="agreementNumber"/>
					в АКБ "Демо" (ЗАО)
				</td>
			</tr>
			<tr>
				<td>Клиент:</td>
				<td width="100%">&nbsp;<b>
					<bean:write name="person" property="fullName"/>
				</b></td>
			</tr>
			<tr>
				<td>Дата:</td>
				<td width="100%">&nbsp;<b>
					<bean:write name="currentDate" format="dd.MM.yyyy"/>
				</b></td>
			</tr>			
			</tiles:put>
		</tiles:insert>
		<br>
	</c:if>
	<br>
	
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="creditInformation"/>
			<tiles:put name="image" value="iconMid_creditsInfo.gif"/>
			<tiles:put name="text" value="Информация по кредиту"/>
			<tiles:put name="data">
					<tr class="ListLine0">
						<td class="ListItem">Текущее состояние</td>
						<td class="ListItem">&nbsp;
							<c:choose>
								<c:when test="${loan.state=='open'}">открыт</c:when>
								<c:when test="${loan.state=='closed'}">закрыт</c:when>
							</c:choose>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Номер договора</td>
						<td class="ListItem">&nbsp;<bean:write name="loan" property="agreementNumber"/></td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Продукт</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.description == null)}">
								<bean:write name="loan" property="description"/>
						 	</c:if>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Дата начала</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.termStart == null)}">
								<bean:write name="loan" property="termStart.time" format="dd.MM.yyyy"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Дата окончания</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.termEnd == null)}">
								<bean:write name="loan" property="termEnd.time" format="dd.MM.yyyy"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Сумма кредита</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.loanAmount == null)}">
								<bean:write name="loan" property="loanAmount.decimal" format="0.00"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Валюта</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.loanAmount == null)}">
								<bean:write name="loan" property="loanAmount.currency.name"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Процентная ставка</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.rate == null)}">
								<bean:write name="loan" property="rate"/>% годовых
							</c:if>
						</td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Срок кредита</td>
						<td class="ListItem">&nbsp;
							<script type="text/javascript">document.write(monthsCount("${loan.termDuration}"))</script>
							&nbsp;месяцев
						</td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Сумма остатка по кредиту</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.balanceAmount == null)}">
								<bean:write name="loan" property="balanceAmount.decimal" format="0.00"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Дата погашения</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.nextPaymentDate == null)}">
								<bean:write name="loan" property="nextPaymentDate.time" format="dd.MM.yyyy"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Сумма погашения</td>
						<td class="ListItem">&nbsp;
							<c:set var="monthlyPayment" value="${scheduleItem.principalAmount.decimal +
								scheduleItem.interestsAmount.decimal + scheduleItem.commissionAmount.decimal}"/>
							<c:if test="${not(monthlyPayment == null)}">
								<bean:write name="monthlyPayment" format="0.00"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Сумма просроченной задолженности по кредиту</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.pastDueAmount == null)}">
								<bean:write name="loan" property="pastDueAmount.decimal" format="0.00"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine0">
						<td class="ListItem">Дата последнего платежа</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.lastPaymentDate == null)}">
								<bean:write name="loan" property="lastPaymentDate.time" format="dd.MM.yyyy"/>
							</c:if>
						</td>
					</tr>
					<tr class="ListLine1">
						<td class="ListItem">Сумма последнего платежа</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not(loan.lastPaymentAmount == null)}">
								<bean:write name="loan" property="lastPaymentAmount.decimal" format="0.00"/>
							</c:if>
						</td>
					</tr>
				</tiles:put>
			</tiles:insert>
	<br>
	<c:if test="${loan.state!='closed'}">

		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="creditInformation"/>
			<tiles:put name="image" value="iconMid_creditsGrafic.gif"/>
			<tiles:put name="text" value="График погашения кредита"/>
			<tiles:put name="head">
	        	<td width="20px">№</td>
				<td width="100px">Дата погашения</td>
				<td width="100px">Выплата по основному долгу</td>
				<td width="100px">Выплата по процентам</td>
				<td width="100px">Комиссия</td>
				<td width="100px">Сумма ежемесячного платежа</td>
				<td width="100px">Сумма для досрочного погашения</td>
			</tiles:put>
			<tiles:put name="data">
				<c:set var="lineNumber" value="0"/>
				<logic:iterate id="item" name="form" property="schedule">
					<c:set var="lineNumber" value="${lineNumber+1}"/>
					<c:set var="date" value="${item.date.time}"/>
					<tr class="ListLine${lineNumber%2}" <c:if test="${date<currentDate}">style="color:red;"</c:if>>
						<td class="ListItem" align="right">&nbsp;${lineNumber}.</td>
						<td class="ListItem">&nbsp;
							<bean:write name="item" property="date.time" format="dd.MM.yyyy"/>
						</td>
						<td class="ListItem">&nbsp;
							<bean:write name="item" property="principalAmount.decimal" format="0.00"/>
						</td>
						<td class="ListItem">&nbsp;
							<bean:write name="item" property="interestsAmount.decimal" format="0.00"/>
						</td>
						<td class="ListItem">&nbsp;
							<bean:write name="item" property="commissionAmount.decimal" format="0.00"/>
						</td>
						<td class="ListItem">&nbsp;
							<c:set var="monthlyPayment" value="${item.principalAmount.decimal+item.interestsAmount.decimal+
												item.commissionAmount.decimal}"/>
							<bean:write name="monthlyPayment" format="0.00"/>
						</td>
						<td class="ListItem">&nbsp;
							<bean:write name="item" property="earlyPaymentAmount.decimal" format="0.00"/>
						</td>
					</tr>
				</logic:iterate>
			</tiles:put>
		</tiles:insert>
	</c:if>
</tiles:put>
</tiles:insert>
</html:form>
