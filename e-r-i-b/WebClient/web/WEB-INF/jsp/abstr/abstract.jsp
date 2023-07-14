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

<html:form action="/private/accounts/abstract" onsubmit="return checkData(event);">

<tiles:insert definition="accountInfo">
<tiles:put name="submenu" type="string" value="Abstract/${pageContext.request.queryString}"/>
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">Выписка</tiles:put>

<!-- меню -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.printAbstract"/>
		<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="onclick" value="openPrint(event)"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="action"  value="/private/accounts.do"/>
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
				<c:choose>
                    <c:when test="${not empty ShowAccountAbstractForm.accountLinks}">
                        <c:forEach var="accountLink" items="${ShowAccountAbstractForm.accountLinks}">
                            <html:option value="a:${accountLink.id}">
                                <c:out value="${accountLink.number}(${accountLink.currency.name}, ${accountLink.description})"/>
                            </html:option>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <html:option value="noAccounts" disabled="true">
                            <c:out value="Нет доступных счетов"/>
                        </html:option>
                    </c:otherwise>
                </c:choose>
                    <!--список доступных карт-->
                <c:choose>
                <c:when test="${not empty ShowAccountAbstractForm.cardLinks}">
                    <c:forEach var="cardLink" items="${ShowAccountAbstractForm.cardLinks}">
                        <html:option value="c:${cardLink.id}">
                            <c:set var="num" value="${cardLink.number}"/>
                            <c:set var="len" value="${fn:length(num)}"/>
                            <c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
                            <c:if test="${cardLink.main}">
                                (основная карта)
                            </c:if>
                            <c:if test="${not cardLink.main}">
                                (дополнительная карта)
                            </c:if>
                        </html:option>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <html:option value="noCards" disabled="true">
                        <c:out value="Нет доступных карт"/>
                    </html:option>
                </c:otherwise>
                </c:choose>
                    <!--список доступных вкладов-->
                <c:choose>
                    <c:when test="${not empty ShowAccountAbstractForm.depositLinks}">
                        <c:forEach var="depositLink" items="${ShowAccountAbstractForm.depositLinks}">
                            <c:set var="deposit" value="${depositLink.deposit}"/>
                            <html:option value="d:${depositLink.id}">
                                <c:out value="${deposit.description}"/>
                            </html:option>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <html:option value="noDeposits" disabled="true">
                            <c:out value="Нет доступных вкладов"/>
                        </html:option>
                    </c:otherwise>
                </c:choose>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			&nbsp;c:
			<html:text property="fromDateString" size="10"
					   onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
			&nbsp;по&nbsp;
			<html:text property="toDateString" size="10"
					   onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</tiles:put>
	</tiles:insert>
	
<script language="JavaScript">

	function clearMasks(event)
	{
		clearInputTemplate("fromDateString", DATE_TEMPLATE);
		clearInputTemplate("toDateString", DATE_TEMPLATE);
	}
	function checkData(event)
	{
		if (!checkPeriod("fromDateString", "toDateString", "Период с", "Период по", true)) return false;
		setEmptyAction(event);
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
</script>
</tiles:put>
<!--данные-->
<tiles:put name="data" type="string">
<!--выписки по счетам -->
<%int lineNumber; %>
<c:set var="tableId" value="0"/>
<c:forEach items="${ShowAccountAbstractForm.accountAbstract}" var="listElement">
	<c:set var="account" value="${listElement.key}"/>
	<c:set var="resourceAbstract" value="${listElement.value}"/>

	<span>ВЫПИСКА ПО СЧЕТУ № </span><b>	<c:out value="${account.number}"/></b>
   	<br/>
	<br/>
	<nobr>
		На дату
		<b>
			&quot;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>&quot;
			&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
			&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
			&nbsp;г.
		</b>
		(за период с
		<b>
			&quot;<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd"/>&quot;
			&nbsp;<span id="monthStrFrom${account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		по
		<b>
			&quot;<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd"/>&quot;&nbsp;
			<span id="monthStrTo${account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		)
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
	<b><c:out value="${account.currency.code}"/></b>
	<br/>
	<br/>
	Входящий остаток:&nbsp;
	<b><bean:write name="resourceAbstract" property="openingBalance.decimal" format="0.00"/></b>
	<br/>
    <c:set var="tableId" value="${tableId+1}"/>

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="AbstractTable${tableId}"/>
		<tiles:put name="text" value="Выписка по счету"/>
		<tiles:put name="image" value="iconMid_accounts.gif"/>
		<tiles:put name="head">
	    	<td height="20px" align="center" valign="middle">Дата операции</td>
			<td align="center" valign="middle">Сумма прихода</td>
			<td align="center" valign="middle">Сумма расхода</td>
			<td align="center" valign="middle">Корреспондент</td>
			<td align="center" valign="middle">Корреспондентский счет</td>
			<td align="center" valign="middle">Банк корреспондента</td>
			<td align="center" valign="middle">Назначение платежа</td>
		</tiles:put>
		<tiles:put name="data">
		<%lineNumber = 0;%>
		<logic:iterate id="transaction" name="resourceAbstract" property="transactions">
			<% lineNumber++; %>
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem" align="center" valign="center">
                   <nobr>
					<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
					&nbsp;
                   </nobr>
				</td>
				<td class="ListItem" align="right" valign="center">
                   <nobr>
					<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
					&nbsp;
                   </nobr>
				</td>
				<td class="ListItem" align="right" valign="center">
                   <nobr>
					<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
					&nbsp;
                   </nobr>
				</td>
				<td class="ListItem" valign="center">
                   <nobr>
					<bean:write name="transaction" property="counteragent"/>
					&nbsp;
                   </nobr>
				</td>
				<td class="ListItem" valign="center">
                   <nobr>
					<bean:write name="transaction" property="counteragentAccount"/>
					&nbsp;
                   </nobr>
				</td>
				<td class="ListItem" valign="center">
                   <nobr>
					<bean:write name="transaction" property="counteragentBank"/>
					&nbsp;
                   </nobr>
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="description"/>
					&nbsp;
				</td>
			</tr>
		</logic:iterate>
		</tiles:put>
		<tiles:put name="isEmpty" value="${phiz:size(resourceAbstract.transactions)==0}"/>
		<tiles:put name="emptyMessage" value="Нет операций по счету за заданный период"/>
	</tiles:insert>
	<br/>
	Исходящий остаток:&nbsp;
	<b><bean:write name="resourceAbstract" property="closingBalance.decimal" format="0.00"/></b>
	<br/>
	<br/>
</c:forEach>
<!--выписки по картам -->
 <c:set var="tableId" value="0"/>
<c:forEach items="${ShowAccountAbstractForm.cardAbstract}" var="listElement" >
<c:set var="cardlink" value="${listElement.key}"/>
<c:set var="cardinfo" value="${cardlink.cardInfo}"/>
<c:set var="abstr" value="${listElement.value}"/>
<c:set var="cardaccount" value="${cardlink.cardAccount}"/>
<c:set var="currentOverdraftInfo" value="${cardlink.currentOverdraftInfo}"/>

<c:if test="${cardlink.main}">
	Выписка по карте за период с
</c:if>
<c:if test="${not cardlink.main}">
	Список операций по карте за период с 
</c:if>
	<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>
 г.
    по
	<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>
 г.
<c:if test="${cardlink.main}">
<br/>	Дата предоставления выписки
	<%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%>
	г.
</c:if>
<br/>
<br/>ФИО держателя карты:&nbsp;
<b><c:out value="${cardlink.cardClient.fullName}"/></b>
<br/>Номер карты:&nbsp;
<b><c:set var="num" value="${cardlink.number}"/>
<c:set var="len" value="${fn:length(num)}"/>
<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
&nbsp;[<c:out value="${cardlink.description}"/>]</b>
<c:if test="${cardlink.main}">
	<br/>Номер спецкарточного счета:&nbsp;
	<b><c:out value="${cardlink.cardAccount.number}"/></b>
	<br/>Входящий баланс:&nbsp;
	<b><bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/></b>
</c:if>
<br/>
<c:choose>
	<c:when test="${phiz:size(abstr.unsettledOperations)==0 && phiz:size(abstr.transactions)==0}">
		<span class="messageTab">
		    Нет операций по карте за заданный период.
		</span>
	</c:when>
	<c:otherwise>
         <c:set var="tableId" value="${tableId+1}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="AbstractTableCard${tableId}"/>
		<tiles:put name="text" value="Выписка по карте"/>
		<tiles:put name="image" value="iconMid_cards.gif"/>
		<tiles:put name="head">
	    	<td height="20px" align="center">Дата списания</td>
			<td height="20px" align="center">Дата операции</td>
			<td align="center">Приход</td>
			<td align="center">Расход</td>
			<td align="center">Сумма в валюте счета</td>
			<td align="center">Вал</td>
			<td align="center">Сумма операции</td>
			<td align="center">Вал</td>
			<td align="center">Содержание операции</td>
		</tiles:put>
		<tiles:put name="data">
			<%lineNumber = 0;%>
			<logic:iterate id="transaction" name="abstr" property="transactions" type="com.rssl.phizic.gate.bankroll.CardOperation">
				<c:set var="sum" value="${transaction.creditSum.decimal + transaction.debitSum.decimal}"/>
				<% lineNumber++; %>
				<tr class="ListLine<%=lineNumber%2%>">
					<td class="ListItem" align="center" valign="center">
                      <nobr>
						<c:if test="${not empty transaction.date.time}">
							<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="center" valign="center">
                      <nobr>
						<c:if test="${not empty transaction.operationDate.time}">
							<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="right" valign="center">
                      <nobr>
						<c:if test="${not empty transaction.creditSum}">
							<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="right" valign="center">
                      <nobr>
						<c:if test="${not empty transaction.debitSum}">
							<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>

					<td class="ListItem" align="center">
                      <nobr>
						<bean:write name="sum"  format="0.00"/>
						&nbsp;
                      </nobr>
					</td>

					<td class="ListItem" align="center">
                      <nobr>
						<c:if test="${not empty transaction.debitSum}">
							<bean:write name="transaction" property="debitSum.currency.code" format="0.00"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="center">
                      <nobr>
							<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="center">
                      <nobr>
							<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</tiles:put>
		<tiles:put name="isEmpty" value="${phiz:size(abstr.unsettledOperations)==0 && phiz:size(abstr.transactions)==0}"/>
		<tiles:put name="emptyMessage" value="Нет операций по карте за заданный период"/>
	</tiles:insert>
	</c:otherwise>	
</c:choose>
<c:if test="${cardlink.main}">
	<br/>Исходящий баланс:&nbsp;
	<b><bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/></b>
	<br/>
	<br/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="listTbl"/>
		<tiles:put name="text" value="Операции, по которым ожидается списание"/>
		<tiles:put name="image" value="iconMid_discarding.gif"/>
		<tiles:put name="head">
	    	<td height="20px" align="center">Дата операции</td>
			<td align="center">Сумма в валюте карты</td>
			<td align="center">Сумма оплаты</td>
			<td align="center">Код валюты</td>
			<td align="center">Содержание операции</td>
		</tiles:put>
		<tiles:put name="data">
			<%lineNumber = 0;%>
			<logic:iterate id="transaction" name="abstr" property="unsettledOperations" type="com.rssl.phizic.gate.bankroll.CardOperation">
				<c:set var="istrndebit" value="${transaction.creditSum.decimal + transaction.debitSum.decimal == transaction.debitSum.decimal}"/>
				<% lineNumber++; %>
				<tr class="ListLine<%=lineNumber%2%>">
					<td class="ListItem" align="center" valign="center">
                      <nobr>
						<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
						&nbsp;
                      </nobr>
					</td>

					<td class="ListItem" align="center">
                      <nobr>
						<c:if test="${istrndebit}">
							<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						</c:if>
						<c:if test="${not istrndebit}">
							<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="center">
                      <nobr>
						<c:if test="${istrndebit}">
							<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
						</c:if>
						<c:if test="${not istrndebit}">
							<bean:write name="transaction" property="accountCreditSum.decimal" format="0.00"/>
						</c:if>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="center">
                      <nobr>
						<c:if test="${istrndebit}">
							<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
						</c:if>
						<c:if test="${not istrndebit}">
							<bean:write name="transaction" property="accountCreditSum.currency.code" format="0.00"/>
						</c:if>
						&nbsp;
                      </nobr>                          
					</td>
					<td class="ListItem" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</tiles:put>
		<tiles:put name="isEmpty" value="${phiz:size(abstr.unsettledOperations)==0}"/>
	</tiles:insert>
		<br/>
	<c:if test="${cardlink.value.cardType=='overdraft'}">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="listTbl"/>
		<tiles:put name="image" value="iconMid_overdraft.gif"/>
		<tiles:put name="text" value="Сведения по овердрафту и штрафам"/>
		<tiles:put name="data">
			<tr class="ListLine0">
				<td class="ListItem">Дата открытия овердрафта</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="openDate.time" format="dd.MM.yyyy"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Лимит овердрафта</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="limit.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма просроченного долга</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="unsettledDebtSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма штрафа за просрочку</td>
				<td class="ListItem">
					<c:if test="${not empty currentOverdraftInfo.unsettledPenalty}">
						<bean:write name="currentOverdraftInfo" property="unsettledPenalty.decimal" format="0.00"/>
					</c:if>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма текущего овердрафта</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="currentOverdraftSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма процентов по овердрафту</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="rate.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма технического овердрафта</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="technicalOverdraftSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Сумма штрафа за технический овердрафт</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="technicalPenalty.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Общая сумма долга</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="totalDebtSum.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine1">
				<td class="ListItem">Дата выноса долга на просрочку</td>
				<td class="ListItem">
					<c:if test="${not empty currentOverdraftInfo.unsetltedDebtCreateDate}">
						<bean:write name="currentOverdraftInfo" property="unsetltedDebtCreateDate.time" format="dd.MM.yyyy"/>
					</c:if>
					&nbsp;
				</td>
			</tr>
			<tr class="ListLine0">
				<td class="ListItem">Сумма доступного лимита</td>
				<td class="ListItem">
					<bean:write name="currentOverdraftInfo" property="availableLimit.decimal" format="0.00"/>
					&nbsp;
				</td>
			</tr>
		</tiles:put>
	</tiles:insert>
	</c:if>
</c:if>
<br/>
<br/>
</c:forEach>
<!--выписки по вкладам -->
<c:set var="tableId" value="0"/>
<c:forEach items="${ShowAccountAbstractForm.depositAbstract}" var="listElement">
	<c:set var="depositLink" value="${listElement.key}"/>
	<c:set var="resourceAbstract" value="${listElement.value}"/>

	ВЫПИСКА ПО СЧЕТУ № <b>
	<c:out value="${depositLink.depositInfo.account.number}"/>
</b><br /><br />
	<nobr>
		На дату
		<b>
			&quot;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>&quot;&nbsp;
			<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
			&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
			&nbsp;г.
		</b>
		(за период с
		<b>
			&quot;<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd"/>&quot;
			&nbsp;<span id="monthStrFrom${depositLink.depositInfo.account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		по
		<b>
			&quot;<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd"/>&quot;
			&nbsp;<span id="monthStrTo${depositLink.depositInfo.account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="yyyy"/>
			&nbsp;г.
		</b>
		)
		<script>
			var fromDate = '<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>';
			var toDate = '<bean:write name='ShowAccountAbstractForm' property="toDateString"/>';
			var mounthStrFrom = document.getElementById('monthStrFrom${depositLink.depositInfo.account.number}');
			var monthStrTo = document.getElementById('monthStrTo${depositLink.depositInfo.account.number}');
			if (mounthStrFrom && monthStrTo){
				mounthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	    </script>
	</nobr>
	<br/>
	<br/>
	Валюта счета:
	<b><c:out value="${depositLink.depositInfo.account.currency.code}"/></b>
	<br/>
	<br/>
	Входящий остаток:&nbsp;
	<b><bean:write name="resourceAbstract" property="openingBalance.decimal" format="0.00"/></b>
	<br/>
    <c:set var="tableId" value="${tableId+1}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="listTbl${tableId}"/>
		<tiles:put name="image" value="iconMid_accounts.gif"/>
		<tiles:put name="text" value="Выписка по счету"/>
		<tiles:put name="head">
	    	<td height="20px" align="center" valign="middle">Дата операции</td>
			<td align="center" valign="middle">Сумма прихода</td>
			<td align="center" valign="middle">Сумма расхода</td>
			<td align="center" valign="middle">Корреспондент</td>
			<td align="center" valign="middle">Корреспондентский счет</td>
			<td align="center" valign="middle">Банк корреспондента</td>
			<td align="center" valign="middle">Назначение платежа</td>
		</tiles:put>
		<tiles:put name="data">
		<%lineNumber = 0;%>
		<logic:iterate id="transaction" name="resourceAbstract" property="transactions">
			<% lineNumber++; %>
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem" align="center" valign="center">
                  <nobr>
					<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
					&nbsp;
                  </nobr>
				</td>
				<td class="ListItem" align="right" valign="center">
                  <nobr>
					<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
					&nbsp;
                  </nobr>
				</td>
				<td class="ListItem" align="right" valign="center">
                  <nobr>
					<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
					&nbsp;
                  </nobr>
				</td>
				<td class="ListItem" valign="center">
                  <nobr>
					<bean:write name="transaction" property="counteragent"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
                  <nobr>
					<bean:write name="transaction" property="counteragentAccount"/>
					&nbsp;
                  </nobr>
				</td>
				<td class="ListItem" valign="center">
                  <nobr>
					<bean:write name="transaction" property="counteragentBank"/>
					&nbsp;
                  </nobr>                      
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="description"/>
					&nbsp;                                                           
				</td>
			</tr>
		</logic:iterate>
		</tiles:put>
		<tiles:put name="isEmpty" value="${phiz:size(resourceAbstract.transactions)==0}"/>
		<tiles:put name="emptyMessage" value="Нет операций по вкладу за заданный период"/>
	</tiles:insert>
	<br/>
	Исходящий остаток:&nbsp;
	<b><bean:write name="resourceAbstract" property="closingBalance.decimal" format="0.00"/></b>
	<br/>
	<br/>
</c:forEach>
<script>
	switchFilter(this);
</script>
</tiles:put>
</tiles:insert>
</html:form>
