<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">
<tiles:insert definition="accountInfo">
<tiles:put name="pageTitle" type="string">
	Пользователь:
	<bean:write name="ShowAccountsForm" property="user.fullName"/>
	. Счета
</tiles:put>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false" service="Abstract">
		<tiles:put name="commandKey" value="button.showAccountCertificate"/>
		<tiles:put name="commandHelpKey" value="button.showAccountCertificate.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<script type="text/javascript" language="JavaScript">
	function viewTable(table, id, view)
	{
		var checkHide;

		for (var i = 1; i < table.rows.length; i++)
		{
			checkHide = document.getElementById(id+i);
			if (checkHide.innerHTML == "true")
			{
				table.rows[i].style.display = view ? "" : "none";
			}
		}
	}

	function onLoad()
	{
		var accountsListTable = document.getElementById("AccountsListTable");
		var cardsListTable = document.getElementById("CardsListTable");
		var depositsListTable = document.getElementById("DepositsListTable");
		var loansListTable = document.getElementById("LoansListTable");

		if (accountsListTable != null)
			viewTable(accountsListTable,"isLockAccount", false);
		if (cardsListTable != null)
			viewTable(cardsListTable,"isLockCard", false);
		if (depositsListTable != null)
			viewTable(depositsListTable,"isLockDeposit", false);
		if (loansListTable != null)
			viewTable(loansListTable,"isLockLoan", false);
	}

	function viewAll()
	{
		var accountsListTable = document.getElementById("AccountsListTable");
		var cardsListTable = document.getElementById("CardsListTable");
		var depositsListTable = document.getElementById("DepositsListTable");
		var loansListTable = document.getElementById("LoansListTable");
		var viewAll = document.getElementById("ViewAll");

		if (viewAll != null)
		{
			if (accountsListTable != null)
				viewTable(accountsListTable,"isLockAccount", viewAll.checked);
			if (cardsListTable != null)
				viewTable(cardsListTable,"isLockCard", viewAll.checked);
			if (depositsListTable != null)
				viewTable(depositsListTable,"isLockDeposit", viewAll.checked);
			if (loansListTable != null)
				viewTable(loansListTable,"isLockLoan", viewAll.checked);
		}
	}

	function changeDivSize()
	{
		var widthClient = getClientWidth();
		if (navigator.appName=='Microsoft Internet Explorer')
			document.getElementById("clientsTables").style.width = widthClient - leftMenuSize - 5;
	}

	function getViewAllButtonName(accountsCount, cardsCount, depositsCount, loanCount)
	{
		var str = "Показывать неактивные ";
		var firstEl = true;
		if (accountsCount>0)
		{
			str += "счета";
			firstEl = false;
		}
		if (cardsCount>0)
		{
			if (!firstEl) str +="/";
			str += "карты";
			firstEl = false;
		}
		if (depositsCount>0)
		{
			if (!firstEl) str +="/";
			str += "вклады";
			firstEl = false;
		}
		if (loanCount>0)
		{
			if (!firstEl) str +="/";
			str += "кредиты";
		}

		return str;
	}
</script>
<table cellpadding="0" cellspacing="0" class="MaxSize">
<tr>
<td height="100%">
<div id="clientsTables">
<script type="text/javascript" language="JavaScript">
	if (navigator.appName=='Microsoft Internet Explorer') changeDivSize();
</script>
<table class="MaxSize"><tr><td>
<% int lineNumber = 0;%>
<!--список счетов -->
<c:set var="accountsCount" value="${phiz:size(ShowAccountsForm.accountsSet)}"/>
<c:if test="${accountsCount>0}">
	<span id="AccountsTable">
		<span class="workspaceTitle" height="40px">Счета</span>

		<table cellspacing="0" cellpadding="0" class="userTab" width="98%" align="center" id="AccountsListTable">
			<tr class="titleTable">
				<td width="20px">
					<input name="isSelectAllAccounts" type="checkbox" style="border:none"
						   onclick="switchSelection('isSelectAllAccounts','selectedAccountsIds')"/>
				</td>
				<td>Номер счета</td>
				<td>Тип</td>
				<td width="100px">Остаток</td>
				<td width="100px">Валюта</td>
				<td width="100px">ДПД</td>
			</tr>
			<c:set var="currentDate" value="<%=DateHelper.getCurrentDate().getTime()%>"/>
			<c:set var="currentDateStr" value="<%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%>"/>
			<!-- строки списка счетов -->
			<logic:iterate id="entry" name="ShowAccountsForm" property="accountsSet">
				<%lineNumber++;%>
				<bean:define id="accountLink" name="entry" property="key"/>
				<bean:define id="account" name="entry" property="key.value"/>
				<bean:define id="accountInfo" name="entry" property="value"/>

				<tr class="ListLine<%=lineNumber%2%>">
					<td align=center class="ListItem">
						<html:multibox property="selectedAccountsIds"
									   style="border:none;">${accountLink.id}</html:multibox>
					</td>
					<td class="ListItem">
						<nobr>
							<phiz:link action="/private/accounts/info"
									   serviceId="AccountAndCardInfo">
                                <phiz:param name="accountId" value="${accountLink.id}"/>
								${account.number}
							</phiz:link>
							<c:if test="${!accountInfo.isOpen || accountInfo.isLock}">
								<img src="${imagePath}/lock.gif" width="12px" height="12px"
									 alt="" border="0"/>
							</c:if>
						</nobr>
					</td>
					<td class="ListItem" nowrap="true">
						<bean:write name="account" property="type"/>
						&nbsp;
					</td>
					<td class="ListItem" nowrap="true">
						<phiz:link action="/private/accounts/abstract"
								   serviceId="Abstract">
                            <phiz:param name="accountIds" value="${accountLink.id}"/>
                            <phiz:param name="list" value="selected"/>
                            <phiz:param name="datePeriod" value="day"/>
							<bean:write name="accountInfo" property="balance.decimal" format="0.00"/>
							&nbsp;
						</phiz:link>
					</td>
					<td class="ListItem" nowrap="true">
						<bean:write name="accountInfo" property="balance.currency.name"/>
						&nbsp;
					</td>
					<td class="ListItem" nowrap="true">
						<bean:write name="accountInfo" property="lastTransactionDate.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
					<td id="isLockAccount<%=lineNumber%>" style="display:none">${!accountInfo.isOpen || accountInfo.isLock}</td>
				</tr>
			</logic:iterate>
		</table>
	</span>
</c:if>
<!--список карт -->
<c:set var="cardsCount" value="${phiz:size(ShowAccountsForm.cardsInfoSet)}"/>
<c:if test="${cardsCount>0}">
	<br>
	<span id="CardsTable">
		<span class="workspaceTitle" height="40px">Карты</span>
		<table cellspacing="0" cellpadding="0" class="userTab" width="98%" align="center" id="CardsListTable">
			<tr class="titleTable">
				<td width="20px">
					<input name="isSelectAllCards" type="checkbox" style="border:none"
						   onclick="switchSelection('isSelectAllCards','selectedCardsIds')"/>
				</td>
				<td>Номер карты</td>
				<td width="100px">Тип карты</td>
				<td width="100px">Остаток</td>
				<td width="100px">Валюта</td>
				<td width="100px">Статус</td>
				<td width="100px">Действительна по</td>
			</tr>
	        <% lineNumber = 0; %>
			<logic:iterate id="entry" name="ShowAccountsForm" property="cardsInfoSet">
				<%lineNumber++;%>
				<c:set var="cardlink" value="${entry.key}"/>
				<c:set var="cardinfo" value="${entry.value}"/>
				<c:set var="card" value="${cardlink.value}"/>
				<c:set var="cardoverdraftinfo" value="${ShowAccountsForm.cardsOverdraftInfoMap[cardlink]}"/>
				<c:set var="ismain" value="${card.main}"/>
				<c:set var="warningDate"
					   value="${phiz:addToDate(currentDate, 0,0, ShowAccountsForm.warningPeriod)}"/>
				<c:set var="expireDate" value="${card.expireDate.time}"/>
				<c:set var="warning" value="${warningDate.time >= expireDate.time}"/>
				<c:set var="cardType" value="${card.cardType}"/>

				<c:set var="cardsAccountInfo" value="${ShowAccountsForm.cardsAccountInfoMap[cardlink]}"/>

				<tr class="ListLine<%=lineNumber%2%>">
					<td align=center class="ListItem">
						<html:multibox property="selectedCardsIds"
									   style="border:none;">${cardlink.id}</html:multibox>
					</td>
					<td class="ListItem">
						<nobr>
							<phiz:link action="/private/accounts/abstract"
								   serviceId="Abstract">
                                <phiz:param name="cardIds" value="${cardlink.id}"/>
                                <phiz:param name="list" value="selected"/>
                                <phiz:param name="datePeriod" value="day"/>
								<c:if test="${warning}">
									<%--TODO вынести в css--%>
									<span style="color:red">
								</c:if>
								<c:set var="num" value="${card.number}"/>
								<c:set var="len" value="${fn:length(num)}"/>
								<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
								<c:if test="${warning}">
									</span>
								</c:if>
							</phiz:link>
						</nobr>
					</td>
					<td class="ListItem" nowrap="true">
					<phiz:link action="/private/cards/info"
									   serviceId="AccountAndCardInfo">
                        <phiz:param name="cardId" value="${cardlink.id}"/>
						<c:out value="${card.description}"/>
						&nbsp;
						<c:if test="${ismain}">(основная)</c:if>
						<c:if test="${!ismain}">(дополнит.)</c:if>
						&nbsp;
					</phiz:link>
					</td>
					<td class="ListItem" nowrap="true">
						<phiz:link action="/private/accounts/abstract" serviceId="Abstract">
                            <phiz:param name="cardIds" value="${cardlink.id}"/>
                            <phiz:param name="list" value="selected"/>
                            <phiz:param name="datePeriod" value="day"/>
							<c:if test="${ismain}">
								<c:set var="balance" value="${cardinfo.balance.decimal}"/>
								<c:if test="${cardType=='overdraft'}">
									<c:if test="${cardoverdraftinfo.currentOverdraftSum.decimal!=null}">

									<c:set var="balance" value="-${cardoverdraftinfo.currentOverdraftSum.decimal}"/>
									
									</c:if>
								</c:if>

								<c:out value="${balance}"/>
							</c:if>
						</phiz:link>
						&nbsp;
					</td>
					<td class="ListItem" nowrap="true">
						<c:out value="${cardinfo.balance.currency.name}"/>
						&nbsp;
					</td>
					<td class="ListItem" nowrap="true">
						<c:out value="${cardinfo.statusDescription}"/>
						&nbsp;
					</td>
					<td class="ListItem" nowrap="true">
						<c:if test="${warning}">
							<%--TODO вынести в css--%>
						<B>
							</c:if>
							<fmt:formatDate value="${expireDate}" pattern="MM.yyyy"/>
							<c:if test="${warning}">
						</B>
						</c:if>
						&nbsp;
					</td>
					<td id="isLockCard<%=lineNumber%>" style="display:none">${!(cardinfo.cardState=='active')}</td>
				</tr>
			</logic:iterate>
		</table>
	</span>
</c:if>
<!--список депозитов -->
<c:set var="depositsCount" value="${phiz:size(ShowAccountsForm.deposits)}"/>
<c:if test="${depositsCount>0}">
	<br>
	<span id="DepositsTable">
		<span class="workspaceTitle" height="40px">Вклады</span>
		<table cellspacing="0" cellpadding="2" class="userTab" width="98%" align="center" id="DepositsListTable">
			<tr class="titleTable">
				<td width="20px">
					<input name="isSelectAllDeposits" type="checkbox" style="border:none"
						   onclick="switchSelection('isSelectAllDeposits','selectedDepositsIds')"/>
				</td>
				<td>Вид вклада</td>
				<td width="60px">Сумма</td>
				<td width="100px">Валюта</td>
				<td width="70px">Срок</td>
				<td width="40px">%</td>
				<td width="140px">Дата открытия</td>
				<td width="140px">Дата окончания</td>
			</tr>
			<!-- строки списка депозитов -->
			<% lineNumber = 0; %>
			<logic:iterate id="depositList" name="ShowAccountsForm" property="deposits">
				<% lineNumber++;%>
				<c:set var="deposit" value="${depositList[0]}"/>
				<c:set var="depositState" value="${deposit.state}"/>
				<c:set var="depositinfo" value="${depositList[1]}"/>

				<tr class="ListLine<%=lineNumber%2%>">
					<td align=center class="ListItem">
						<html:multibox property="selectedDepositsIds"
									   style="border:none;">${deposit.id}</html:multibox>
					</td>
					<td class="ListItem">
						<phiz:link action="/private/deposit/info"
								   serviceId="DepositDetails">
                                <phiz:param name="depositId" value="${deposit.id}"/>
							${depositinfo.account.number}&nbsp;${deposit.description}
						</phiz:link>
					</td>
					<td class="ListItem" align="right">
						<c:if test="${not empty deposit.amount}">
							<bean:write name="deposit" property="amount.decimal" format="0.00"/>
						</c:if>
						&nbsp;</td>
					<td class="ListItem" align="center">&nbsp;
						<c:if test="${not empty deposit.amount}">
							<bean:write name="deposit" property="amount.currency.name"/>
						</c:if>
						&nbsp;</td>
					<td class="ListItem">&nbsp;
						<c:if test="${not empty deposit.duration}">
							<bean:write name="deposit" property="duration"/>
							дней
						</c:if>
					</td>
					<td class="ListItem">&nbsp;
						<c:if test="${not empty deposit.interestRate}">
							<bean:write name="deposit" property="interestRate"/>
						</c:if>
					</td>
					<td class="ListItem" align="left">
						<bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
					<td class="ListItem" nowrap="true" align="left" style="vertical-align:middle;">
						<c:if test="${not empty deposit.endDate}">
							<bean:write name="deposit" property="endDate.time" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
					</td>
					<td id="isLockDeposit<%=lineNumber%>" style="display:none">${depositState == "closed"}</td>
				</tr>
			</logic:iterate>
		</table>
	</span>
</c:if>
<!--список кредитов -->
<c:set var="loanCount" value="${phiz:size(ShowAccountsForm.loans)}"/>
<c:if test="${loanCount>0}">
	<br>
	<span id="LoansTable">
		<span class="workspaceTitle" height="40px">Кредиты</span>
		<table cellspacing="0" cellpadding="2" class="userTab" width="98%" align="center" id="LoansListTable">
			<tr class="titleTable">
				<td width="120px">Номер договора</td>
				<td>Продукт</td>
				<td width="90px">Дата окончания</td>
				<td width="120px">Сумма кредита</td>
				<td width="70px">Валюта</td>
				<td width="90px">Дата погашения</td>
				<td width="100px">Сумма погашения</td>
				<td width="100px">Сумма просрочки</td>
			</tr>
			<!-- строки списка кредитов -->
			<% lineNumber = 0; %>
			<logic:iterate id="loan" name="ShowAccountsForm" property="loans">
				<c:set var="scheduleItem" value="${ShowAccountsForm.scheduleItems[loan.id]}"/>
				<% lineNumber++;%>
				<tr class="ListLine<%=lineNumber%2%>">
					<td class="ListItem">&nbsp;
						<bean:write name="loan" property="agreementNumber"/>
					</td>
					<td class="ListItem" align="center">&nbsp;
						<phiz:link action="/private/loans/info" serviceId="LoanDetails">
                            <phiz:param name="loanId" value="${loan.id}"/>
							<bean:write name="loan" property="description"/>
						</phiz:link>
					</td>
					<td class="ListItem">&nbsp;
						<bean:write name="loan" property="termEnd.time" format="dd.MM.yyyy"/>
					</td>
					<td class="ListItem">&nbsp;
						<bean:write name="loan" property="loanAmount.decimal" format="0.00"/>
					</td>
					<td class="ListItem">&nbsp;
						<bean:write name="loan" property="loanAmount.currency.name"/>
					</td>
					<td class="ListItem">&nbsp;
						<c:if test="${scheduleItem!=null}">
						<bean:write name="scheduleItem" property="date.time" format="dd.MM.yyyy"/>
						</c:if>
					</td>
					<td class="ListItem">&nbsp;
						<c:if test="${scheduleItem!=null}">
						<bean:write name="scheduleItem" property="totalPaymentAmount.decimal" format="0.00"/>
						</c:if>
					</td>
					<td class="ListItem">&nbsp;
						<c:choose>
							<c:when test="${loan.pastDueAmount.decimal>0}">
								Вам нужно срочно оплатить <bean:write name="loan" property="pastDueAmount.decimal" format="0.00"/>
							</c:when>
							<c:otherwise>0</c:otherwise>
					    </c:choose>
					<td>
					<td id="isLockLoan<%=lineNumber%>" style="display:none">${loan.state=="closed"}</td>
				</tr>
			</logic:iterate>
		</table>
	</span>
</c:if>
<br>
<br>
<c:if test="${accountsCount+cardsCount+depositsCount+loanCount>0}">
	<input name="ViewAll" id="ViewAll" type="checkbox" style="border:none" onclick="viewAll();">
		<script type="text/javascript">
			document.write(getViewAllButtonName(${accountsCount}, ${cardsCount}, ${depositsCount}, ${loanCount}));
		</script>
	</input>
</c:if>
<br>
</td>
</tr>
</table>
</div>
</td>
</tr>
<tr>
	<td class="newsTitle topLine" id="systemsNews">Новости системы</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" cellpadding="0" class="MaxSize">
			<tr>
				<td>
					<!-- краткие новости -->
					<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
					<c:set var="news" value="${form.news}"/>
					<c:set var="newsNumber" value="0"/>
					<table cellpadding="0" cellspacing="0" border="0" style="padding-left:6px;padding-right:6px; border-spacing:0px; line-height:normal">
						<c:forEach var="list" items="${news}">
							<c:if test="${newsNumber<'2'}">
								<c:set var="newsDate" value="${list.newsDate.time}"/>
								<tr>
									<td colspan="2">
											<a href="/PhizIC/private/news/list.do#newsId<c:out value='${list.id}'/>"
												style="color:#A43E98;font-weight:normal; text-decoration:underline; text-align:justify;"><c:out value='${list.title}'/></a>
									</td>
								</tr>
								<tr>
									<td align="center" valign="middle">&nbsp;
										<c:if test="${list.important}"><img
												src="${imagePath}/important.gif"
												alt=""></c:if>&nbsp;
									</td>
									<td style="vertical-align:middle;text-align:justify;<c:if test='${list.important}'>color:red;</c:if>"
									    width="100%">
											${phiz:processBBCode(list.shortText)}&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="2" class="shortList dateNews"><bean:write name='newsDate' format="dd.MM.yyyy"/> |
										<a href="/PhizIC/private/news/list.do#newsId<c:out value='${list.id}'/>"
										   class="asterisk">Подробнее...</a>
									</td>
								</tr>
								<c:set var="newsNumber" value="${newsNumber+1}"/>
							</c:if>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<script type="text/javascript">
	 <c:if test="${newsNumber=='0'}">
		 document.getElementById("systemsNews").style.display = "none";
	 </c:if>
	onLoad();
</script>
</tiles:put>
</tiles:insert>
</html:form>