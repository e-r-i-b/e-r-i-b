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
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="accountInfo">
<tiles:put name="pageTitle" type="string">Мой банк</tiles:put>
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

	function getViewAllButtonName(accountsCount, cardsCount, depositsCount, loansCount)
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
		if (loansCount>0)
		{
			if (!firstEl) str +="/";
			str += "кредиты";
		}

		return str;
	}
</script>

    <table cellpadding="0" cellspacing="0" class="MaxSize">
        <tr>
            <td class="MaxSize">
            <div class="MaxSize" id="content">
            <table class="MaxSize">
                <tr>
                    <td valign="top">

            <!--список счетов -->
            <c:set var="accountsCount" value="${phiz:size(ShowAccountsForm.accounts)}"/>
            <c:if test="${accountsCount>0}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="image" value="iconMid_accounts.gif"/>
                    <tiles:put name="text" value="Счета"/>

                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="accounts" styleId="AccountsListTable">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedAccountsIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>

                            <c:set var="id" value="${lineNumber}"/>
                            <sl:collectionItem title="Номер счета">
                                <c:if test="${not empty listElement}">
                                    <nobr>
                                        <phiz:link action="/private/accounts/abstract"
                                                   serviceId="AccountAndCardInfo">
                                            <phiz:param name="list" value="selected"/>
                                            <phiz:param name="accountIds" value="${listElement.id}"/>
                                            <bean:write name="listElement" property="number"/>
                                        </phiz:link>
                                        <c:if test="${!listElement.account.accountState!='OPENED'}">
                                                <img src="${imagePath}/iconSm_lock.gif" width="12px" height="12px" alt="" border="0"/>
                                        </c:if>
                                    </nobr>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Тип">
                                <c:if test="${not empty listElement.account.type}">
                                    <phiz:link action="/private/accounts/info" serviceId="AccountAndCardInfo">
                                        <phiz:param name="accountId" value="${listElement.id}"/>
                                        &nbsp;<bean:write name="listElement" property="account.type"/>
                                    </phiz:link>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Остаток"  width="100px">
                                <c:if test="${not empty listElement.account.balance}">
                                    <phiz:link action="/private/accounts/abstract"
                                            serviceId="Abstract">
                                       <phiz:param name="list" value="selected"/>
                                       <phiz:param name="accountIds" value="${listElement.id}"/>
                                       <phiz:param name="day" value="1"/>
                                        &nbsp;<bean:write name="listElement" property="account.balance.decimal" format="0.00"/>
                                    </phiz:link>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Валюта"  width="100px">
                                <c:if test="${not empty listElement.currency}">
                                    &nbsp;<bean:write name="listElement" property="account.currency.name"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Посл. операция"  width="100px">
                                <c:if test="${not empty listElement.account.lastTransactionDate}">
                                    &nbsp;<bean:write name="listElement" property="account.lastTransactionDate.time" format="dd.MM.yyyy"/>&nbsp;
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem hidden="true">
                                <span id="isLockAccount${stateNumberA}" style="display:none;">${!listElement.account.accountState!='OPENED'}</span>
                            </sl:collectionItem>
                            <c:set value="${stateNumberA+1}" var="stateNumberA"/>
                    </sl:collection>

                </tiles:put>
                </tiles:insert>
               <br>
            </c:if>
            <!--список карт -->
            <c:set var="cardsCount" value="${phiz:size(ShowAccountsForm.cards)}"/>
            <c:if test="${cardsCount>0}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="image" value="iconMid_cards.gif"/>
                    <tiles:put name="text" value="Карты"/>
                    <tiles:put name="grid">
                        <sl:collection id="cardLink" model="list" property="cards" styleId="CardsListTable">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedCardsIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>
                            <c:set var="card" value="${cardLink.value}"/>
                            <c:set var="cardInfo" value="${cardLink.cardInfo}"/>
                            <c:set var="warningDate" value="${phiz:addToDate(currentDate, 0,0, ShowAccountsForm.warningPeriod)}"/>
                            <c:set var="expireDate" value="${cardLink.expireDate.time}"/>
                            <c:set var="warning" value="${warningDate.time >= expireDate.time}"/>
                            <sl:collectionItem title="Номер">
                                <nobr>
                                    <c:if test="${warning}">
                                    <%--TODO вынести в css--%>
                                        <span style="color:red">
                                    </c:if>
                                    <c:set var="num" value="${cardLink.number}"/>
                                    <c:set var="len" value="${fn:length(num)}"/>
                                    <c:choose>
                                    <c:when test="${!cardLink.main}">
                                        <phiz:link action="/private/accounts/abstract" serviceId="Abstract">
                                            <phiz:param name="list" value="selected"/>
                                            <phiz:param name="cardIds" value="${cardLink.id}"/>
                                            <phiz:param name="day" value="1"/>
                                            <c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
                                        </phiz:link>
                                    </c:when>
                                    <c:otherwise>
                                        <phiz:link action="/private/accounts/abstract" serviceId="Abstract">
                                            <phiz:param name="list" value="selected"/>
                                            <phiz:param name="cardIds" value="${cardLink.id}"/>
                                            <c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
                                        </phiz:link>
                                    </c:otherwise>
                                    </c:choose>
                                    <c:if test="${warning}">
                                        </span>
                                    </c:if>
                                </nobr>
                            </sl:collectionItem>

                            <sl:collectionItem title="Тип">
                                <phiz:link action="/private/cards/info" serviceId="AccountAndCardInfo">
                                    <phiz:param name="cardId" value="${cardLink.id}"/>
                                    <c:out value="${cardLink.description}"/>&nbsp;
                                    <c:if test="${cardLink.main}">(основная)</c:if>
                                    <c:if test="${!cardLink.main}">(дополнит.)</c:if>
                                &nbsp;
                                </phiz:link>
                            </sl:collectionItem>

                            <sl:collectionItem title="Остаток">
                                <c:choose>
                                    <c:when test="${card.card.cardType=='overdraft'}">
                                        <phiz:link action="/private/cards/info" serviceId="AccountAndCardInfo">
                                            <phiz:param name="cardId" value="${cardLink.id}"/>
                                            <c:out value="Показать"/>
                                        </phiz:link>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${cardLink.main}">
                                            <c:set var="balance" value="${cardInfo.balance.decimal}"/>
                                        </c:if>
                                        <c:out value="${balance}"/>
                                    </c:otherwise>
                                </c:choose>
                            </sl:collectionItem>

                            <sl:collectionItem title="Валюта">
                                <c:out value="${cardLink.cardInfo.balance.currency.name}"/>
                                &nbsp;
                            </sl:collectionItem>

                            <sl:collectionItem title="Статус">
                                <c:out value="${cardLink.cardInfo.statusDescription}"/>
                                &nbsp;
                            </sl:collectionItem>

                            <sl:collectionItem title="Срок действия">
                                <c:if test="${warning}">
                                    <B>
                                </c:if>
                                <fmt:formatDate value="${cardLink.expireDate.time}" pattern="MM.yyyy"/>
                                <c:if test="${warning}">
                                    </B>
                                </c:if>
                                &nbsp;
                            </sl:collectionItem>

                             <sl:collectionItem hidden="true">
                                <span id="isLockCard${stateNumberC}">${!(cardLink.cardInfo.cardState=='active')}</span>
                            </sl:collectionItem>
                            <c:set var="stateNumberC" value="${stateNumberC+1}"/>
                        </sl:collection>
                    </tiles:put>
                </tiles:insert>
               <br/>
            </c:if>

            <!--список депозитов -->
            <c:set var="depositsCount" value="${phiz:size(ShowAccountsForm.deposits)}"/>
            <c:if test="${depositsCount>0}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="image" value="iconMid_deposits.gif"/>
                    <tiles:put name="text" value="Вклады"/>
                     <tiles:put name="grid">
                        <sl:collection id="depositElement" model="list" property="deposits" styleId="DepositsListTable">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedDepositsIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>
                            <c:set var="deposit" value="${depositElement.deposit}"/>

                            <sl:collectionItem title="Вид вклада">
                                    <phiz:link action="/private/deposit/info" serviceId="DepositDetails">
                                        <phiz:param name="depositId" value="${depositElement.id}"/>
                                            ${deposit.description}
                                        </phiz:link>
                            </sl:collectionItem>

                            <sl:collectionItem title="Сумма" width="60px">
                                <c:if test="${not empty deposit.amount}">
                                    <bean:write name="deposit" property="amount.decimal" format="0.00"/>
                                </c:if>&nbsp;
                            </sl:collectionItem>

                            <sl:collectionItem title="Валюта" width="100px">
                                <c:if test="${not empty deposit.amount}">
                                    <bean:write name="deposit" property="amount.currency.name"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Срок" width="70px">
                                <c:if test="${not empty deposit.duration}">
                                    <bean:write name="deposit" property="duration"/> дней
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="%" width="40px">
                                <c:if test="${not empty deposit.interestRate}">
                                    <bean:write name="deposit" property="interestRate"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Дата открытия"  width="140px">
                                <c:if test="${not empty deposit.openDate}">
                                    <bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>&nbsp;
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Дата окончания"  width="140px">
                                <c:if test="${not empty deposit.endDate}">
                                    <bean:write name="deposit" property="endDate.time" format="dd.MM.yyyy"/>&nbsp;
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem hidden="true">
                                <span id="isLockDeposit${stateNumberD}">${deposit.state == "closed"}</span>
                            </sl:collectionItem>
                            <c:set value="${stateNumberD+1}" var="stateNumberD"/>
                        </sl:collection>
                    </tiles:put>
                </tiles:insert>
                <br/>
            </c:if>

            <!--список кредитов -->
            <c:set var="loansCount" value="${phiz:size(ShowAccountsForm.loans)}"/>
            <c:if test="${loansCount>0}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="image" value="iconMid_credits.gif"/>
                    <tiles:put name="text" value="Кредиты"/>
                    <tiles:put name="grid">
                        <sl:collection id="loan" model="list" property="loans" styleId="LoansListTable">

                            <c:set var="scheduleItem" value="${ShowAccountsForm.scheduleItems[loan.id]}"/>
                            <sl:collectionItem title="Номер договора"  width="120px">
                                <c:if test="${not empty loan.agreementNumber}">
                                    <bean:write name="loan" property="agreementNumber"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Продукт">
                                <c:if test="${not empty loan.description}">
                                    <phiz:link action="/private/loans/info" serviceId="LoanDetails">
                                        <phiz:param name="loanId" value="${loan.id}"/>
                                        <bean:write name="loan" property="description"/>
                                    </phiz:link>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Дата окончания"  width="90px">
                                 <c:if test="${not empty loan.termEnd}">
                                    <bean:write name="loan" property="termEnd.time" format="dd.MM.yyyy"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Сумма кредита"  width="120px">
                                <c:if test="${not empty loan.loanAmount}">
                                    <bean:write name="loan" property="loanAmount.decimal" format="0.00"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Валюта"  width="70px">
                                <c:if test="${not empty loan.loanAmount}">
                                    <bean:write name="loan" property="loanAmount.currency.name"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Дата погашения"  width="90px">
                                <c:if test="${scheduleItem!=null}">
                                    <bean:write name="scheduleItem" property="date.time" format="dd.MM.yyyy"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Сумма погашения"  width="100px">
                                <c:if test="${scheduleItem!=null}">
                                    <bean:write name="scheduleItem" property="totalPaymentAmount.decimal" format="0.00"/>
                                </c:if>
                            </sl:collectionItem>

                            <sl:collectionItem title="Сумма просрочки"  width="100px">
                                <c:choose>
                                    <c:when test="${loan.pastDueAmount.decimal>0}">
                                        Вам нужно срочно оплатить <bean:write name="loan" property="pastDueAmount.decimal" format="0.00"/>
                                    </c:when>
                                    <c:otherwise>0</c:otherwise>
                                </c:choose>
                            </sl:collectionItem>
                            <sl:collectionItem hidden="true">
                                <span id="isLockLoan${stateNunberL}">${loan.state=="closed"}</span>
                            </sl:collectionItem>
                            <c:set var="stateNunberL" value="${stateNunberL+1}"/>
                        </sl:collection>
                    </tiles:put>
                </tiles:insert>
                <br/>
                <br>
            </c:if>

            <c:if test="${accountsCount+cardsCount+depositsCount+loansCount>0}">
                <input name="ViewAll" id="ViewAll" type="checkbox" style="border:none" onclick="viewAll();">
                    <script type="text/javascript">
                        document.write(getViewAllButtonName(${accountsCount}, ${cardsCount}, ${depositsCount}, ${loansCount}));
                    </script>
                </input>
                <br>
            </c:if>
                    </td>
                </tr>
            </table>
            </div>
            </td >
        </tr>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="news" value="${form.news}"/>

        <c:if test="${not empty news}">
        <tr>
            <td style="padding-top:3mm; vertical-align:bottom;" id="systemsNews">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="systemsNewsContents"/>
                    <tiles:put name="image" value="iconMid_news.gif"/>
                    <tiles:put name="text" value="Новости системы"/>
                    <tiles:put name="data">
                                <!-- краткие новости -->
                                <%--<c:set var="form" value="${phiz:currentForm(pageContext)}"/>--%>
                                <%--<c:set var="news" value="${form.news}"/>--%>
                                <c:set var="newsNumber" value="0"/>
                                    <c:forEach var="list" items="${news}">
                                        <c:if test="${newsNumber<'2'}">
                                            <c:set var="newsDate" value="${list.newsDate.time}"/>
                                            <tr class="tblInfHeader">
                                                <td class="newsTitle">
                                                    ${phiz:processBBCode(list.title)}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="newsText">
                                                    &nbsp;
                                                    <c:if test="${list.important}">
                                                        <img src="${imagePath}/iconSm_importantNews.gif"
                                                            alt="">
                                                    </c:if>
                                                    &nbsp;
                                                    ${phiz:processBBCode(list.shortText)}&nbsp;&nbsp;&nbsp;
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><span class="newsDate"><bean:write name='newsDate' format="dd.MM.yyyy"/></span> |
                                                    <a href="/PhizIC/private/news/list.do#newsId<c:out value='${list.id}'/>"
                                                       class="asterisk">Подробнее..</a>
                                                </td>
                                            </tr>
                                            <c:set var="newsNumber" value="${newsNumber+1}"/>
                                        </c:if>
                                    </c:forEach>
                    </tiles:put>
                    <tiles:put name="emptyMessage">&nbsp;</tiles:put>
                    <tiles:put name="isEmpty" value="${newsNumber=='0'}"/>
                </tiles:insert>
              </td>
         </tr>
     </c:if>
     </table>

<script type="text/javascript">
	onLoad();
</script>
</tiles:put>
</tiles:insert>
</html:form>

