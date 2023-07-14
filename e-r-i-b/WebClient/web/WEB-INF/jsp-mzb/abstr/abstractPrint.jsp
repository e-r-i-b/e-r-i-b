<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="com.rssl.phizic.gate.bankroll.AccountTransaction" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 08.12.2006
  Time: 11:43:03
  To change this template use File | Settings | File Templates.
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

<html:form action="/private/accounts/print">

<tiles:insert definition="printDoc">
	<tiles:put name="data" type="string">
	<c:set var="break" value="0"/>
<div style="font-size:9pt; width: 160mm;">
	<!--выписки по счетам -->
	<c:forEach items="${PrintAccountAbstractForm.accountAbstract}" var="listElement">
		<c:if test="${break > 0}">
			 <br style="page-break-after:always;">
		</c:if>
		<c:set var="break" value="1"/>
		<c:set var="account" value="${listElement.key}"/>
		<c:set var="abstract" value="${listElement.value}"/>
		<br/>
		<span>Банк: АКБ "МОСКОВСКИЙ ЗАЛОГОВЫЙ БАНК" (ЗАО)</span>
		<br/>
		<span style="text-align:center;width:100%">ВЫПИСКА ПО СЧЕТУ</span>
		<br/>

		<table style="font-size:9pt">
			<tr>
				<td style="text-align:left;">
					Номер счета:
				</td>
				<td style="text-align:left;">
					<c:out value="${account.number}"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					Валюта счета:
				</td>
				<td style="text-align:left;">
					<c:out value="${account.currency.code}"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					Период:
				</td>
				<td style="text-align:left;">
					c:&nbsp;
					<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>&nbsp;
					г.&nbsp;по&nbsp;
					<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>&nbsp;
					г.
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<b>Входящий остаток:</b>&nbsp
				</td>
				<td style="text-align:left;">
					&nbsp;<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
				</td>
			</tr>
		</table>
		<br/>
		<c:choose>
		<c:when test="${phiz:size(abstract.accountTransactions)==0}">
			<span>
				<b>Нет операций по счету за заданный период.</b>
			</span>
			</br>
		</c:when>
		<c:otherwise>
		<table id="listTbl" cellspacing="0" cellpadding="0" style="font-size:8pt;" class="bdTable">
			<tr>
				<td height="20px" align="center" valign="middle" class="bdCell"><b>Дата операции</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Сумма прихода</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Сумма расхода</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Корреспондент</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Корреспондентский счет</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Банк корреспондента</b></td>
				<td align="center" valign="middle" class="bdCellLast"><b>Назначение платежа</b></td>
			</tr>
			<logic:iterate id="transaction" name="abstract" property="accountTransactions">
				<tr>
					<td  class="bdCell" align="center" valign="center">
						<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
					<td  class="bdCell" align="right" valign="center">
						<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td  class="bdCell" align="right" valign="center">
						<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td  class="bdCell" valign="center">
						<bean:write name="transaction" property="counteragent"/>
						&nbsp;
					</td>
					<td  class="bdCell" valign="center">
						<bean:write name="transaction" property="counteragentAccount"/>
						&nbsp;
					</td>
					<td  class="bdCell" valign="center">
						<bean:write name="transaction" property="counteragentBank"/>
						&nbsp;
					</td>
					<td  class="bdCellLast" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
		</c:otherwise>
		</c:choose>
		<br/>
		<b>Исходящий остаток:</b>&nbsp;
		<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
		<br/>
		<br/>
	</c:forEach>
	<!--выписки по картам -->
	<c:forEach items="${PrintAccountAbstractForm.cardAbstract}" var="listElement" >
	<c:if test="${break > 0}">
			 <br style="page-break-after:always;">
		</c:if>
		<c:set var="break" value="1"/>
	<c:set var="card" value="${listElement.key}"/>
	<c:set var="abstr" value="${listElement.value}"/>
	<c:set var="cardinfo" value="${PrintAccountAbstractForm.cardInfoMap[card]}"/>
	<c:set var="cardaccount" value="${PrintAccountAbstractForm.cardAccountMap[card]}"/>
	<c:set var="cardclient" value="${PrintAccountAbstractForm.cardClientMap[card]}"/>
	<c:set var="cardoverdraftinfo" value="${PrintAccountAbstractForm.cardOverdraftInfoMap[card]}"/>

	<c:if test="${card.main}">
		<span style="padding-top:5px; font-size:12pt">Банк: АКБ "МОСКОВСКИЙ ЗАЛОГОВЫЙ БАНК" (ЗАО)</span>
		<br/>
		<br/>
		<table width="100%">
			<tr>
				<td width="100%" style="text-align:center;font-size:12pt">
				ВЫПИСКА ПО КАРТЕ
				</td>
			</tr>
		</table>
		<br/>
		<br/>
	</c:if>
	<c:if test="${not card.main}">
		<span>Список операций по карте </span>
	</c:if>


	<table width="100%" style="font-size:9pt">
		<tr>
			<td style="text-align:left;width:20%">
				ФИО держателя:
			</td>
			<td style="text-align:left;width:80%">
				<c:out value="${cardclient.fullName}"/>
			</td>
		</tr>
		<tr>
			<td style="text-align:left;width:20%">
			    Номер карточки:
			</td>
			<td style="text-align:left;width:20%">
				<c:set var="num" value="${card.number}"/>
				<c:set var="len" value="${fn:length(num)}"/>
				<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
				&nbsp;[<c:out value="${card.description}"/>]
			</td>
		</tr>
		<tr>
			<c:if test="${card.main}">
				<td style="text-align:left;width:20%">
					Номер лицевого счета:
				</td>
				<td style="text-align:left;width:20%">
					<c:out value="${cardaccount.number}"/>
				</td>
			</c:if>
		</tr>
		<tr>
			<td style="text-align:left;width:20%">
			   	Период:
			</td>
			<td style="text-align:left;width:20%">
				c&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>&nbsp;
				г.&nbsp;по&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>&nbsp;
			    г.
			</td>
		</tr>
	</table>
	<c:if test="${card.main}">
	<br/><b>Входящий остаток:</b>&nbsp;
		<bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/>
	</c:if>
	<br/>
	<br/>
	<c:choose>
		<c:when test="${phiz:size(abstr.operations)==0}">
			<span class="messageTab" style="color:#000000">
			    Нет операций по карте за заданный период.
			</span>
			</br>
		</c:when>
		<c:otherwise>
				<table id="listTbl" style="font-size:8pt; padding-right:0px; padding-left:0px;width:98%" class="bdTable" cellpadding="0" cellspacing="0">
					<tr bgcolor="#cccccc">
						<td height="20px" align="center" class="bdCell"><b>Дата списания</b></td>
						<td height="20px" align="center" class="bdCell"><b>Дата операции</b></td>
						<td align="center" class="bdCell"><b>Приход</b></td>
						<td align="center" class="bdCell"><b>Расход</b></td>
						<td align="center" class="bdCell"><b>Сумма в валюте счета</b></td>
						<td align="center" class="bdCell"><b>Вал</b></td>
						<td align="center" class="bdCell"><b>Сумма операции</b></td>
						<td align="center" class="bdCell"><b>Вал</b></td>
						<td align="center" class="bdCellLast"><b>Содержание операции</b></td>
					</tr>
					<logic:iterate id="transaction" name="abstr" property="operations" type="com.rssl.phizic.gate.bankroll.CardOperation">
						<c:set var="sum" value="${transaction.creditSum.decimal + transaction.debitSum.decimal}"/>

						<tr>
							<td class="bdCell" align="center" valign="center">
								<c:if test="${not empty transaction.chargeDate}">
									<bean:write name="transaction" property="chargeDate.time" format="dd.MM.yyyy"/>
								</c:if>
								&nbsp;
							</td>
							<td class="bdCell" align="center" valign="center">
								<c:if test="${not empty transaction.operationDate.time}">
									<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
								</c:if>
								&nbsp;
							</td>
							<td class="bdCell" align="right" valign="center">
								<c:if test="${not empty transaction.creditSum}">
									<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
								</c:if>
								&nbsp;
							</td>
							<td class="bdCell" align="right" valign="center">
								<c:if test="${not empty transaction.debitSum}">
									<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
								</c:if>
								&nbsp;
							</td>

							<td class="bdCell" align="center">
								<bean:write name="sum"  format="0.00"/>
								&nbsp;
							</td>

							<td class="bdCell" align="center">
								<c:if test="${not empty transaction.debitSum}">
									<bean:write name="transaction" property="debitSum.currency.code" format="0.00"/>
								</c:if>
								&nbsp;
							</td>
							<td class="bdCell" align="center">
									<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
								&nbsp;
							</td>
							<td class="bdCell" align="center">
									<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
								&nbsp;
							</td>
							<td class="bdCellLast" valign="center">
								<bean:write name="transaction" property="description"/>
								&nbsp;
							</td>
						</tr>
					</logic:iterate>
				</table>
		</c:otherwise>
	</c:choose>
	<c:if test="${card.main}">
		<br/><b>Исходящий остаток:</b>&nbsp;
		<bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/>
		<br/>
		<br/>
		<c:if test="${phiz:size(abstr.unsettledOperations)>0}">
			<span><b>Операции, по которым ожидается списание:</b></span>
			<table class="bdTable" id="listTbl" cellspacing="0" cellpadding="0" width="100%" style="font-size:9pt">
				<tr>
					<td height="20px" align="center" class="bdCell"><b>Дата операции</b></td>
					<td align="center" class="bdCell"><b>Сумма в валюте карты</b></td>
					<td align="center" class="bdCell"><b>Сумма оплаты</b></td>
					<td align="center" class="bdCell"><b>Код валюты</b></td>
					<td align="center" class="bdCellLast"><b>Содержание операции</b></td>
				</tr>

				<logic:iterate id="transaction" name="abstr" property="unsettledOperations" type="com.rssl.phizic.gate.bankroll.CardOperation">
					<c:set var="istrndebit" value="${transaction.creditSum.decimal + transaction.debitSum.decimal == transaction.debitSum.decimal}"/>

					<tr>
						<td class="bdCell" align="center" valign="center">
							<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
							&nbsp;
						</td>

						<td class="bdCell" align="center">
							<c:if test="${istrndebit}">
								<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
							</c:if>
							<c:if test="${not istrndebit}">
								<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
							</c:if>
							&nbsp;
						</td>
						<td class="bdCell" align="center">
							<c:if test="${istrndebit}">
								<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
							</c:if>
							<c:if test="${not istrndebit}">
								<bean:write name="transaction" property="accountCreditSum.decimal" format="0.00"/>
							</c:if>
							&nbsp;
						</td>
						<td class="bdCell" align="center">
							<c:if test="${istrndebit}">
								<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
							</c:if>
							<c:if test="${not istrndebit}">
								<bean:write name="transaction" property="accountCreditSum.currency.code" format="0.00"/>
							</c:if>
							&nbsp;
						</td>
						<td class="bdCellLast" valign="center">
							<bean:write name="transaction" property="description"/>
							&nbsp;
						</td>
					</tr>
				</logic:iterate>
			</table>
			<br/>
		</c:if>
		<c:if test="${card.cardType=='overdraft'}">
			<br/>
			<span>Сведения по овердрафту и штрафам:</span>
			<table style="width:100%" cellpadding="0"cellspacing="0">
			<tr>
				<td>
			<table cellspacing="0" cellpadding="3" class="bdTable" width="60%" align="left" style="font-size:9pt; padding-left:0px; padding-right:0px">
				<tr>
					<td class="bdCell"><b>Дата открытия овердрафта</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="openDate.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Лимит овердрафта</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="limit.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма просроченного долга</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="unsettledDebtSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма штрафа за просрочку</b></td>
					<td class="bdCellLast">
						<c:if test="${not empty cardoverdraftinfo.unsettledPenalty}">
							<bean:write name="cardoverdraftinfo" property="unsettledPenalty.decimal" format="0.00"/>
							&nbsp;
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма текущего овердрафта</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="currentOverdraftSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма процентов по овердрафту</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="rate.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма технического овердрафта</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="technicalOverdraftSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма штрафа за технический овердрафт</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="technicalPenalty.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Общая сумма долга</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="totalDebtSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Дата выноса долга на просрочку</b></td>
					<td class="bdCellLast">
						<c:if test="${not empty cardoverdraftinfo.unsetltedDebtCreateDate}">
							<bean:write name="cardoverdraftinfo" property="unsetltedDebtCreateDate.time" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bdCell"><b>Сумма доступного лимита</b></td>
					<td class="bdCellLast">
						<bean:write name="cardoverdraftinfo" property="availableLimit.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
			</table>
				</td>
			</tr>
		</c:if>
	</c:if>

	<!--<br/><br/><br/>-->
	<tr>
		<td>  <br/><br/>
	<table width="100%">
		<tr>
			<td width="100%" style="text-align:center;font-size:12pt">
	<%--<span style="text-align:center;width:100%;">--%>


		Уважаемый&nbsp;владелец&nbsp;карты!&nbsp;<br/>
		Извещаем&nbsp;Вас,&nbsp;что&nbsp;cрок&nbsp;действия&nbsp;Вашей&nbsp;карты&nbsp;заканчивается&nbsp;<bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/> г. <br/>
		Для&nbsp;замены карты,&nbsp;приглашаем&nbsp;Вас&nbsp;посетить&nbsp;Банк&nbsp;за&nbsp;5&nbsp;дней<br/>
		до&nbsp;окончания&nbsp;срока&nbsp;ее&nbsp;действия.                            <br/>
		 <br/>

		В&nbsp;случае&nbsp;несогласия&nbsp;с&nbsp;указанной&nbsp;в&nbsp;выписке&nbsp;суммы&nbsp;операции&nbsp;претензии&nbsp;принимаются&nbsp;в&nbsp;срок    <br/>
		не&nbsp;позднее&nbsp;30&nbsp;календарных&nbsp;дней&nbsp;со&nbsp;дня&nbsp;операции.&nbsp;Претензии,&nbsp;поступившие&nbsp;в&nbsp;Банк&nbsp;по&nbsp;истечении   <br/>
		30&nbsp;календарных&nbsp;дней&nbsp;со&nbsp;дня&nbsp;операции,&nbsp;Банком&nbsp;не&nbsp;рассматриваются.                           <br/>
	<!--</span>-->
			</td>
		</tr>
	</table>
		</td>
		</tr>
			</table>
	</c:forEach>

	<!--выписки по вкладам -->
	<c:forEach items="${PrintAccountAbstractForm.depositAbstract}" var="listElement">
		<c:if test="${break > 0}">
			 <br style="page-break-after:always;">
		</c:if>
		<c:set var="break" value="1"/>
		<c:set var="account" value="${listElement.key}"/>
		<c:set var="abstract" value="${listElement.value}"/>

		<span>Банк: АКБ "МОСКОВСКИЙ ЗАЛОГОВЫЙ БАНК" (ЗАО)</span>
		<br/>
		<span style="text-align:center;width:100%">ВЫПИСКА ПО СЧЕТУ</span>
		<br/>

		<table style="font-size:9pt">
			<tr>
				<td style="text-align:left;">
					Номер счета:
				</td>
				<td style="text-align:left;">
					<c:out value="${account.number}"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					Валюта счета:
				</td>
				<td style="text-align:left;">
					<c:out value="${account.currency.code}"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					Входящий остаток:
				</td>
				<td style="text-align:left;">
					&nbsp;<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					Период:
				</td>
				<td style="text-align:left;">
					c:&nbsp;
					<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>&nbsp;
					г.&nbsp;по&nbsp;
					<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>&nbsp;
					г.
				</td>
			</tr>
		</table>
		<br/>
		<table id="listTbl" cellspacing="0" cellpadding="0" style="font-size:8pt" class="bdTable">
			<tr>
				<td height="20px" align="center" valign="middle" class="bdCell"><b>Дата операции</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Сумма прихода</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Сумма расхода</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Корреспондент</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Корреспондентский счет</b></td>
				<td align="center" valign="middle" class="bdCell"><b>Банк корреспондента</b></td>
				<td align="center" valign="middle" class="bdCellLast"><b>Назначение платежа</b></td>
			</tr>

			<logic:iterate id="transaction" name="abstract" property="accountTransactions">
				<tr>
					<td class="bdCell" align="center" valign="center">
						<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
					<td class="bdCell" align="right" valign="center">
						<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td class="bdCell" align="right" valign="center">
						<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td class="bdCell" valign="center">
						<bean:write name="transaction" property="counteragent"/>
						&nbsp;
					</td>
					<td class="bdCell" valign="center">
						<bean:write name="transaction" property="counteragentAccount"/>
						&nbsp;
					</td>
					<td class="bdCell" valign="center">
						<bean:write name="transaction" property="counteragentBank"/>
						&nbsp;
					</td>
					<td class="bdCellLast" valign="center">
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
		var monthStrFrom = document.all('monthStrFrom');
		var monthStrTo = document.all('monthStrTo');
		var fromDate = '<bean:write name='PrintAccountAbstractForm' property="fromDateString"/>';
		var toDate = '<bean:write name='PrintAccountAbstractForm' property="toDateString"/>';
		if (monthStrFrom && monthStrTo)
			if (monthStrFrom.length > 0)
				for (var i = 0; i < monthStrFrom.length; i++)
				{
					monthStrFrom[i].innerHTML = monthToStringOnly(fromDate);
					monthStrTo[i].innerHTML = monthToStringOnly(toDate);
				}
			else
			{
				monthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	</script>
</div>
	</tiles:put>
</tiles:insert>
</html:form>