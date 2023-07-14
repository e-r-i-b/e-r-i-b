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
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="pageTitle" type="string">
	Пользователь:
	<bean:write name="ShowAccountsForm" property="user.fullName"/>
	. Счета
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false">
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

		if (accountsListTable != null)
			viewTable(accountsListTable,"isLockAccount", false);
		if (cardsListTable != null)
			viewTable(cardsListTable,"isLockCard", false);
		if (depositsListTable != null)
			viewTable(depositsListTable,"isLockDeposit", false);
	}

	function viewAll()
	{
		var accountsListTable = document.getElementById("AccountsListTable");
		var cardsListTable = document.getElementById("CardsListTable");
		var depositsListTable = document.getElementById("DepositsListTable");
		var viewAll = document.getElementById("ViewAll");
		if (viewAll != null)
		{
			if (accountsListTable != null)
				viewTable(accountsListTable,"isLockAccount", viewAll.checked);
			if (cardsListTable != null)
				viewTable(cardsListTable,"isLockCard", viewAll.checked);
			if (depositsListTable != null)
				viewTable(depositsListTable,"isLockDeposit", viewAll.checked);
		}
	}

	function changeDivSize()
	{
		var widthClient = getClientWidth();
		if (navigator.appName=='Microsoft Internet Explorer')
			document.getElementById("clientsTables").style.width = widthClient - leftMenuSize - 5;
	}
</script>
<table cellpadding="0" cellspacing="0" class="MaxSize">
<tr>
<td height="100%">
<div class="MaxSize">
<table class="MaxSize"><tr><td>
<% int lineNumber = 0;%>
<!--список счетов -->
<c:set var="accountsCount" value="${phiz:size(ShowAccountsForm.accountsSet)}"/>
<c:if test="${accountsCount>0}">
	<span id="AccountsTable">
		<span class="workspaceTitle" height="40px">Счета</span>

		<table cellspacing="0" cellpadding="0" class="userTab" width="98%" align="center" id="AccountsListTable">
			<tr class="titleTable">
				  <td width="20px" align=center>
					  <input name="isSelectAllAccounts" type="checkbox" style="border:none"
							 onclick="switchSelection('isSelectAllAccounts','selectedAccountsIds')"/>
				  </td>
				<td>Номер счета</td>
				<td>Тип</td>
				<td width="100px">Остаток</td>
				<td width="100px">Валюта</td>
				<td width="100px">Посл.операция</td>
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
					  <td align=center class="ListItem" width="20px">
							  <html:multibox property="selectedAccountsIds"
											 style="border:none;">${accountLink.id}</html:multibox>
					  </td>
					<td class="ListItem">
						<nobr>
							<phiz:link action="/private/accounts/abstract">
                                <phiz:param name="list" value="selected"/>
                                <phiz:param name="accountIds" value="${accountLink.id}"/>
								${account.number}
							</phiz:link>
							<c:if test="${!accountInfo.isOpen || accountInfo.isLock}">
								<img src="${imagePath}/lock.gif" width="12px" height="12px"
									 alt="" border="0"/>
							</c:if>
						</nobr>
					</td>
					<td class="ListItem" nowrap="true">
						<nobr>
							<phiz:link action="/private/accounts/info">
                                <phiz:param name="accountId" value="${accountLink.id}"/>
								<bean:write name="account" property="type"/>&nbsp;
							</phiz:link>
						</nobr>
					</td>
					<td class="ListItem" nowrap="true">
						<phiz:link action="/private/accounts/abstract">
                            <phiz:param name="list" value="selected"/>
                            <phiz:param name="accountIds" value="${accountLink.id}"/>
                            <phiz:param name="day" value="1"/>
							<bean:write name="accountInfo" property="balance.decimal" format="0.00"/>&nbsp;
						</phiz:link>
					</td>
					<td class="ListItem" nowrap="true">
						<bean:write name="accountInfo" property="balance.currency.name"/>&nbsp;
					</td>
					<td class="ListItem" nowrap="true">&nbsp;
						<c:if test="${not empty accountInfo.lastTransactionDate}">
							<bean:write name="accountInfo" property="lastTransactionDate.time" format="dd.MM.yyyy"/>&nbsp;
						</c:if>
					</td>
						<%--используется для "Показывать закрытые счета"--%>
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
			<tr class="titleTable" align="center">
				<td width="20px">
					<input name="isSelectAllCards" type="checkbox" style="border:none" align="center"
						   onclick="switchSelection('isSelectAllCards','selectedCardsIds')"/>
				</td>
				<td>Номер</td>
				<!--<td>Вид карты</td>-->
				<td width="100px">Тип</td>
				<td width="100px">Остаток</td>
				<td width="100px">Валюта</td>
				<td width="100px">Статус</td>
				<td width="100px">Срок действия</td>
			</tr>
			<% lineNumber = 0; %>
			<logic:iterate id="entry" name="ShowAccountsForm" property="cardsInfoSet">
				<%lineNumber++;%>
				<c:set var="cardlink" value="${entry.key}"/>
				<c:set var="cardinfo" value="${entry.value}"/>
				<c:set var="card" value="${cardlink.value}"/>
				<c:set var="cardoverdraftinfo" value="${ShowAccountsForm.cardsOverdraftInfoMap[cardlink]}"/>
				<c:set var="ismain" value="${card.main}"/>
				<c:set var="warningDate" value="${phiz:addToDate(currentDate, 0,0, ShowAccountsForm.warningPeriod)}"/>
				<c:set var="expireDate" value="${card.expireDate.time}"/>
				<c:set var="warning" value="${warningDate.time >= expireDate.time}"/>
				<c:set var="cardType" value="${card.cardType}"/>
				<tr class="ListLine<%=lineNumber%2%>">
					<td align=center class="ListItem" width="20px">
							<html:multibox property="selectedCardsIds"
										   style="border:none;">${cardlink.id}</html:multibox>
					</td>
					<td class="ListItem">
						<nobr>
							<c:if test="${warning}">
							<%--TODO вынести в css--%>
								<span style="color:red">
							</c:if>
							<c:set var="num" value="${card.number}"/>
							<c:set var="len" value="${fn:length(num)}"/>
							<c:choose>
							<c:when test="${!ismain}">
								<phiz:link action="/private/accounts/abstract">
                                    <phiz:param name="list" value="selected"/>
                                    <phiz:param name="cardIds" value="${cardlink.id}"/>
									<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
								</phiz:link>
							</c:when>
							<c:otherwise>
								<phiz:link action="/private/accounts/abstract">
                                    <phiz:param name="list" value="selected"/>
                                    <phiz:param name="cardIds" value="${cardlink.id}"/>
									<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
								</phiz:link>
							</c:otherwise>
							</c:choose>
							<c:if test="${warning}">
								</span>
							</c:if>
						</nobr>
					</td>

					<td class="ListItem" nowrap="true">
						<phiz:link action="/private/cards/info">
                            <phiz:param name="cardId" value="${cardlink.id}"/>
							<c:out value="${card.description}"/>&nbsp;
							<c:if test="${ismain}">(основная)</c:if>
							<c:if test="${!ismain}">(дополнит.)</c:if>
						&nbsp;
						</phiz:link>
					</td>
					<td class="ListItem" nowrap="true">
						<c:choose>
							<c:when test="${card.cardType=='overdraft'}">
								<span style="text-align:center;" class=MaxSize>
									<phiz:link action="/private/cards/info">
                                       <phiz:param name="cardId" value="${cardlink.id}"/>
										<c:out value="Показать"/>
									</phiz:link>
								</span>
							</c:when>
							<c:otherwise>
								<c:if test="${ismain}">
									<phiz:link action="/private/accounts/abstract">
                                        <phiz:param name="list" value="selected"/>
                                        <phiz:param name="cardIds" value="${cardlink.id}"/>
                                        <phiz:param name="day" value="1"/>
   									    <c:set var="balance" value="${cardinfo.balance.decimal}"/>
									</phiz:link>
								</c:if>
								<phiz:link action="/private/accounts/abstract">
                                    <phiz:param name="list" value="selected"/>
                                    <phiz:param name="cardIds" value="${cardlink.id}"/>
                                    <phiz:param name="day" value="1"/>
								   <c:out value="${balance}"/>
								</phiz:link>
							</c:otherwise>
						</c:choose>
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
							<B style="color:red">
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
				<c:set var="deposit"       value="${depositList[0]}" />
				<c:set var="depositState" value="${deposit.state}"/>
				<tr class="ListLine<%=lineNumber%2%>">
					<td class="ListItem">
						<phiz:link action="/private/deposit/info">
                            <phiz:param name="depositId" value="${deposit.id}"/>
							${deposit.description}
						</phiz:link>
					</td>
					<td class="ListItem" align="right">
						<c:if test="${not empty deposit.amount}">
							<phiz:link action="/private/accounts/abstract">
                                <phiz:param name="list" value="selected"/>
                                <phiz:param name="depositIds" value="${deposit.id}"/>
                                <phiz:param name="day" value="1"/>
							   <bean:write name="deposit" property="amount.decimal" format="0.00"/>
							</phiz:link>
						</c:if>&nbsp;
					</td>
					<td class="ListItem" align="center">&nbsp;
						<c:if test="${not empty deposit.amount}">
							<bean:write name="deposit" property="amount.currency.name"/>
						</c:if>&nbsp;
					</td>
					<td class="ListItem">&nbsp;
						<c:if test="${not empty deposit.duration}">
							<bean:write name="deposit" property="duration"/> дней
						</c:if>
					</td>
					<td class="ListItem">&nbsp;
						<c:if test="${not empty deposit.interestRate}">
							<bean:write name="deposit" property="interestRate"/>
						</c:if>
					</td>
					<td class="ListItem" align="left">
						<c:if test="${not empty deposit.openDate}">
							<bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>&nbsp;
						</c:if>
					</td>
					<td class="ListItem" nowrap="true" align="left" style="vertical-align:middle;">
						<c:if test="${not empty deposit.endDate}">
							<bean:write name="deposit" property="endDate.time" format="dd.MM.yyyy"/>&nbsp;
						</c:if>
					</td>
					<td id="isLockDeposit<%=lineNumber%>" style="display:none">${depositState == "closed"}</td>
				</tr>
			</logic:iterate>
		</table>
	</span>
</c:if>
<br>
<br>
<c:if test="${accountsCount+cardsCount+depositsCount>0}">
	<input name="ViewAll" id="ViewAll" type="checkbox"
		style="border:none" onclick="viewAll();">Показать закрытые счета/вклады/карты </input>
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
					<table cellpadding="0" cellspacing="0" border="0" class="shortList" style="padding-left:6px;padding-right:6px;">
						<c:forEach var="list" items="${news}">
							<c:if test="${newsNumber<'2'}">
								<c:set var="newsDate" value="${list.newsDate.time}"/>
								<tr>
									<td colspan="2">
											<b>${phiz:processBBCode(list.title)}</b>
									</td>
								</tr>
								<tr>
									<td align="center" valign="top">&nbsp;
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
										   class="asterisk">Подробнее..</a>
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