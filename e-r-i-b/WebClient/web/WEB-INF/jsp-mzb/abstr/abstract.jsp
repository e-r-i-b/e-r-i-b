<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/accounts/abstract" onsubmit="return true;">

<tiles:insert definition="abstractMain">

<tiles:put name="submenu" type="string" value="Abstract/${pageContext.request.queryString}"/>
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">
	Пользователь:
	<bean:write name="ShowAccountAbstractForm" property="user.fullName"/>
	. Выписки по счетам
</tiles:put>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<!-- меню -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.printAbstract"/>
		<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="onclick" value="openPrint(event)"/>
		<tiles:put name="image" value="print.gif"/>
	</tiles:insert>
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value="back.gif"/>
	</tiles:insert>
</tiles:put>
<!-- фильтр -->
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			<html:select property="selectedResources" multiple="true" size="3"
			             onkeydown="onTabClick(event,'fromDateString')">
				<!--список доступных счетов-->
				<c:forEach var="accountLink" items="${ShowAccountAbstractForm.accountLinks}">
					<c:set var="account" value="${accountLink.value}"/>
					<html:option value="a:${accountLink.id}"><c:out
							value="${account.number}(${account.currency.name}, ${account.description})"/></html:option>
				</c:forEach>
				<!--список доступных карт-->
				<c:forEach var="cardLink" items="${ShowAccountAbstractForm.cardLinks}">
					<c:set var="card" value="${cardLink.value}"/>
					<html:option value="c:${cardLink.id}">
						<c:set var="num" value="${card.number}"/>
						<c:set var="len" value="${fn:length(num)}"/>
						<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
						<c:if test="${card.main}">
							(основная карта)
						</c:if>
						<c:if test="${not card.main}">
							(дополнительная карта)
						</c:if>
					</html:option>
				</c:forEach>
				<!--список доступных вкладов-->
				<c:forEach var="depositLink" items="${ShowAccountAbstractForm.depositLinks}">
					<c:set var="depoit" value="${depositLink.deposit}"/>
					<html:option value="d:${depoit.id}">
						<c:out value="${depoit.description}"/>
					</html:option>
	            </c:forEach>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			&nbsp;c:
			<html:text property="fromDateString" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
			&nbsp;по&nbsp;
			<html:text property="toDateString" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</tiles:put>
	</tiles:insert>

<script language="JavaScript">
	document.imgPath = "${imagePath}/";

	function clearMasks(event)
	{
		clearInputTemplate("fromDateString", DATE_TEMPLATE);
		clearInputTemplate("toDateString", DATE_TEMPLATE);
	}
	function checkData()
	{
		if (!checkPeriod("fromDateString", "toDateString", "Период с", "Период по", true)) return false;
		return true;
	}
	function callAbstractOperation(event, action, confirm)
	{
		preventDefault(event);
		res = getElementValue("selectedResources");
		if (res == "") alert("Выберите счета/карты");
		else callOperation(event, action, confirm);
	}

	function openPrint(event)
	{
		if (checkData(event) == true)
		{   /* todo: реализовать передачу параметров через форму */
			var req;
			req = 'print.do?';
			req = req + "fromDateString=<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>&";
			req = req + "toDateString=<bean:write name='ShowAccountAbstractForm' property="toDateString"/>";
		<logic:iterate id="res" name="ShowAccountAbstractForm" property="selectedResources">
			req += "&sel=<bean:write name='res'/>";
		</logic:iterate>
			openWindow(event, req,"","resizable=1,menubar=1,toolbar=1,scrollbars=1,width=700");
		}
	}

	function clearFilterAbstract()
	{
		var elemSelect= getElement("selectedResources");
		for (var i = 0; i < elemSelect.options.length; i++)
			elemSelect.options[i].selected = true;
		var fromDate = getElement("fromDateString");
		var toDate = getElement("toDateString");
		//По умолчанию построим выписку с начала текущего месяца
		var CurrentDate = new Date();
		var curDate = CurrentDate.getDate();
		var curMonth = CurrentDate.getMonth() + 1;
		if (curDate<10) curDate='0'+curDate;
		if (curMonth<10) curMonth='0'+curMonth;
		fromDate.innerText = "01." + curMonth+"." + CurrentDate.getFullYear();
		fromDate.value = "01." + curMonth+"." + CurrentDate.getFullYear();
		toDate.innerText = curDate+"." + curMonth+"." + CurrentDate.getFullYear();
		toDate.value = curDate+"." + curMonth+"." + CurrentDate.getFullYear();
	}
</script>
</tiles:put>
<!--данные-->
<tiles:put name="data" type="string">
<!--выписки по счетам -->
<c:forEach items="${ShowAccountAbstractForm.accountAbstract}" var="listElement">
	<c:set var="account" value="${listElement.key}"/>
	<c:set var="abstract" value="${listElement.value}"/>

	<span class="filter">ВЫПИСКА ПО СЧЕТУ № </span><b>
	<c:out value="${account.number}"/>
</b>
	<nobr>
		<span class="filter">На дату</span>
		<b>
			«<%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>»
			&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
			&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
			&nbsp;г.
		</b>
		<span class="filter">(за период с</span>
		<b>
			«<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd"/>
			»&nbsp;<span id="monthStrFrom${account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		<span class="filter">по</span>
		<b>
			«<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd"/>»&nbsp;
			<span id="monthStrTo${account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		<span class="filter">)</span>
		<script>
			var fromDate = '<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>';
			var toDate = '<bean:write name='ShowAccountAbstractForm' property="toDateString"/>';
			var mounthStrFrom = document.getElementById('monthStrFrom${account.number}');
			var monthStrTo = document.getElementById('monthStrTo${account.number}');
			if (mounthStrFrom && monthStrTo){
				mounthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	    </script>
	</nobr>
	<br/>
	<br/>
	Валюта счета:
	<c:out value="${account.currency.code}"/>
	<br/>
	<br/>
	Входящий остаток:&nbsp;
	<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
	<br/><br/>
	<c:choose>
	<c:when test="${phiz:size(abstract.accountTransactions)==0}">
		<span class="messageTab">
		    Нет операций по счету за заданный период.
		</span>
		<br/>
	</c:when>
	<c:otherwise>
	<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
		<tr class="titleTable">
			<td height="20px" align="center" valign="middle">Дата операции</td>
			<td align="center" valign="middle">Сумма прихода</td>
			<td align="center" valign="middle">Сумма расхода</td>
			<td align="center" valign="middle">Корреспондент</td>
			<td align="center" valign="middle">Счет корреспондента</td>
			<td align="center" valign="middle">Банк корреспондента</td>
			<td align="center" valign="middle">Назначение платежа</td>
		</tr>
		<logic:iterate id="transaction" name="abstract" property="accountTransactions" indexId="i">
			<tr class="ListLine${i mod 2}">
				<td class="ListItem" align="center" valign="center">
					<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
					&nbsp;
				</td>
				<td class="ListItem" align="right" valign="center">
					<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
					&nbsp;
				</td>
				<td class="ListItem" align="right" valign="center">
					<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragent"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentAccount"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentBank"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="description"/>
					&nbsp;
				</td>
			</tr>
		</logic:iterate>
	</table>
	</c:otherwise>
	</c:choose>
	<br/>
	Исходящий остаток:&nbsp;
	<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
	<br/>
	<br/>
</c:forEach>
<!--выписки по картам -->
<c:forEach items="${ShowAccountAbstractForm.cardAbstract}" var="listElement" >
<c:set var="card" value="${listElement.key}"/>
<c:set var="abstr" value="${listElement.value}"/>
<c:set var="cardinfo" value="${ShowAccountAbstractForm.cardInfoMap[card]}"/>
<c:set var="cardaccount" value="${ShowAccountAbstractForm.cardAccountMap[card]}"/>
<c:set var="cardclient" value="${ShowAccountAbstractForm.cardClientMap[card]}"/>
<c:set var="cardoverdraftinfo" value="${ShowAccountAbstractForm.cardOverdraftInfoMap[card]}"/>

<c:if test="${card.main}">
	<span class="filter">Выписка по карте за период с </span>
</c:if>
<c:if test="${not card.main}">
	<span class="filter">Список операций карте за период с </span>
</c:if>
	<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/> г.
<span class="filter">по</span>
	<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/> г.
<c:if test="${card.main}">
	<span class="filter">Дата предоставления выписки </span>
	<%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%> г.
</c:if>
<br/>
<br/>ФИО держателя карты:&nbsp;
<c:out value="${cardclient.fullName}"/>
<br/>Номер карты:&nbsp;
<c:set var="num" value="${card.number}"/>
<c:set var="len" value="${fn:length(num)}"/>
<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
&nbsp;[<c:out value="${card.description}"/>]
<c:if test="${card.main}">
	<br/>Номер спецкарточного счета:&nbsp;
	<c:out value="${cardaccount.number}"/>
	</br><br/>Входящий остаток:&nbsp;
	<bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/>
</c:if>
<br/><br/>
<c:choose>
	<c:when test="${phiz:size(abstr.operations)==0}">
		<span class="messageTab">
		    Нет операций по карте за заданный период.
		</span>
		</br>
	</c:when>
	<c:otherwise>
		<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
			<tr class="titleTable">
				<td height="20px" align="center">Дата списания</td>
				<td height="20px" align="center">Дата операции</td>
				<td align="center">Приход</td>
				<td align="center">Расход</td>
				<td align="center">Сумма в валюте счета</td>
				<td align="center">Вал</td>
				<td align="center">Сумма операции</td>
				<td align="center">Вал</td>
				<td align="center">Содержание операции</td>
			</tr>
			<logic:iterate id="transaction" name="abstr" property="operations" type="com.rssl.phizic.gate.bankroll.CardOperation" indexId="i">
				<c:set var="sum" value="${transaction.creditSum.decimal + transaction.debitSum.decimal}"/>

				<tr class="ListLine${i mod 2}">
					<td class="ListItem" align="center" valign="center">
						<c:if test="${not empty transaction.operationDate}">
							<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" align="center" valign="center">
						<c:if test="${not empty transaction.chargeDate}">
							<bean:write name="transaction" property="chargeDate.time" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" align="right" valign="center">
						<c:if test="${not empty transaction.creditSum}">
							<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" align="right" valign="center">
						<c:if test="${not empty transaction.debitSum}">
							<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
					</td>

					<td class="ListItem" align="center">
						<bean:write name="sum"  format="0.00"/>
						&nbsp;
					</td>

					<td class="ListItem" align="center">
						<c:if test="${not empty transaction.debitSum && not empty transaction.debitSum.currency}">
							<bean:write name="transaction" property="debitSum.currency.code" format="0.00"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" align="center">
							<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td class="ListItem" align="center">
						<c:if test="${not empty transaction.accountDebitSum && not empty transaction.accountDebitSum.currency}">
							<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
	</c:otherwise>
</c:choose>
<c:if test="${card.main}">
	<br/>Исходящий остаток:&nbsp;
	<bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/>
	<br/>
	<br/>
	<c:if test="${phiz:size(abstr.unsettledOperations)>0}">
		<span class="filter">Операции, по которым ожидается списание:</span>
		<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
			<tr class="titleTable">
				<td height="20px" align="center">Дата операции</td>
				<td align="center">Сумма в валюте карты</td>
				<td align="center">Сумма оплаты</td>
				<td align="center">Код валюты</td>
				<td align="center">Содержание операции</td>
			</tr>
			<logic:iterate id="transaction" name="abstr" property="unsettledOperations" type="com.rssl.phizic.gate.bankroll.CardOperation" indexId="i">
				<c:set var="istrndebit" value="${transaction.creditSum.decimal + transaction.debitSum.decimal == transaction.debitSum.decimal}"/>
				<tr class="ListLine${i mod 2}">
					<td class="ListItem" align="center" valign="center">
						<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>

					<td class="ListItem" align="center">
						<c:if test="${istrndebit}">
							<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						</c:if>
						<c:if test="${not istrndebit}">
							<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" align="center">
						<c:if test="${istrndebit}">
							<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
						</c:if>
						<c:if test="${not istrndebit}">
							<bean:write name="transaction" property="accountCreditSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" align="center">
						<c:if test="${istrndebit}">
							<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
						</c:if>
						<c:if test="${not istrndebit}">
							<bean:write name="transaction" property="accountCreditSum.currency.code" format="0.00"/>
						</c:if>
						&nbsp;
					</td>
					<td class="ListItem" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
		<br/>
	</c:if>
	<c:if test="${card.cardType=='overdraft'}">
		<span class="filter">Сведения по овердрафту и штрафам:</span>
		<table cellspacing="0" cellpadding="0" class="userTab" width="80%" align="center">
			<tr class="ListLine0">
				<td class="ListItem">Дата открытия овердрафта</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="openDate.time" format="dd.MM.yyyy"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Лимит овердрафта</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="limit.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма просроченного долга</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="unsettledDebtSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма штрафа за просрочку</td>
				<td class="ListItem">
					<c:if test="${not empty cardoverdraftinfo.unsettledPenalty}">
						<bean:write name="cardoverdraftinfo" property="unsettledPenalty.decimal" format="0.00"/>
					</c:if>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма текущего овердрафта</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="currentOverdraftSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма процентов по овердрафту</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="rate.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма технического овердрафта</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="technicalOverdraftSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма штрафа за технический овердрафт</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="technicalPenalty.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Общая сумма долга</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="totalDebtSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Дата выноса долга на просрочку</td>
				<td class="ListItem">
					<c:if test="${not empty cardoverdraftinfo.unsetltedDebtCreateDate}">
						<bean:write name="cardoverdraftinfo" property="unsetltedDebtCreateDate.time" format="dd.MM.yyyy"/>
					</c:if>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма доступного лимита</td>
				<td class="ListItem">
					<bean:write name="cardoverdraftinfo" property="availableLimit.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
		</table>
	</c:if>
</c:if>
<br/>
<br/>
</c:forEach>
<!--выписки по счетам -->
<c:forEach items="${ShowAccountAbstractForm.depositAbstract}" var="listElement">
	<c:set var="account" value="${listElement.key}"/>
	<c:set var="abstract" value="${listElement.value}"/>

	<span class="filter">ВЫПИСКА ПО СЧЕТУ № </span><b>
	<c:out value="${account.number}"/>
</b>
	<nobr>
		<span class="filter">На дату</span>
		<b>
			«<%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>»
			&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
			&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
			&nbsp;г.
		</b>
		<span class="filter">(за период с</span>
		<b>
			«<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd"/>»&nbsp;
			<span id="monthStrFrom${account.number}"></span>&nbsp;
			&nbsp;<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		<span class="filter">по</span>
		<b>
			«<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd"/>»
			&nbsp;<span id="monthStrTo${account.number}"></span>
			&nbsp;<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		<span class="filter">)</span>
		<script>
			var fromDate = '<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>';
			var toDate = '<bean:write name='ShowAccountAbstractForm' property="toDateString"/>';
			var mounthStrFrom = document.getElementById('monthStrFrom${account.number}');
			var monthStrTo = document.getElementById('monthStrTo${account.number}');
			if (mounthStrFrom && monthStrTo){
				mounthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	    </script>
	</nobr>
	<br/>
	<br/>
	Валюта счета:
	<c:out value="${account.currency.code}"/>
	<br/>
	<br/>
	Входящий остаток:&nbsp;
	<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
	<br/><br/>
	<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
		<tr class="titleTable">
			<td height="20px" align="center" valign="middle">Дата операции</td>
			<td align="center" valign="middle">Сумма прихода</td>
			<td align="center" valign="middle">Сумма расхода</td>
			<td align="center" valign="middle">Корреспондент</td>
			<td align="center" valign="middle">Корреспондентский счет</td>
			<td align="center" valign="middle">Банк корреспондента</td>
			<td align="center" valign="middle">Назначение платежа</td>
		</tr>
		<%int lineNumber = 0;%>
		<logic:iterate id="transaction" name="abstract" property="accountTransactions">
			<% lineNumber++; %>
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem" align="center" valign="center">
					<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
					&nbsp;
				</td>
				<td class="ListItem" align="right" valign="center">
					<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
					&nbsp;
				</td>
				<td class="ListItem" align="right" valign="center">
					<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragent"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentAccount"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentBank"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="description"/>
					&nbsp;
				</td>
			</tr>
		</logic:iterate>
	</table>
	<br/>
	Исходящий остаток:&nbsp;
	<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
	<br/>
	<br/>
</c:forEach>
<script>
	switchFilter(this);
</script>
</tiles:put>
</tiles:insert>
</html:form>
