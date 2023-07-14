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

<html:form action="/private/smsbanking/pseudonyms" onsubmit="return setEmptyAction(event)">
<script type="text/javascript" language="JavaScript">
	function viewTable(table, id, view)
	{
		var checkHide;
		for (var i = 1; i < table.rows.length; i++)
		{
			checkHide = document.getElementById(id+i);
			if (checkHide.innerHTML == "true")
			{
				table.rows[i].style.display = view ? "block" : "none";
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

	function getViewAllButtonName(accountsCount, cardsCount, depositsCount)
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

		return str;
	}

</script>
<table cellpadding="0" cellspacing="0" class="MaxSize">
<tr>
<td height="100%">
<div class="MaxSize">
<table class="MaxSize">
<tr>
<td>

<!-- список псевдонимов счетов -->
<c:set var="accountsCount" value="${phiz:size(ShowPseudonymsListForm.accountPseudonyms)}"/>
<c:if test="${accountsCount>0}">
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="AccountsListTable"/>
	<tiles:put name="image" value=""/>
	<tiles:put name="text" value="Счета"/>
    <tiles:put name="grid">
        <% int lineNumber = 0;%>
        <sl:collection id="listElement" model="list" property="accountPseudonyms">
            <%lineNumber++;%>
            <c:set var="accountLink_" value="${listElement.link}"/>
            <c:set var="account" value="${accountLink_.value}"/>

            <c:set var="currentDate" value="<%=DateHelper.getCurrentDate().getTime()%>"/>
			<c:set var="currentDateStr" value="<%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%>"/>

            <sl:collectionItem title="доступ">
                <c:choose>
					<c:when test="${listElement.hasAccess}">
						<input type="checkbox" name="selectedIds" checked="${listElement.hasAccess}" value="${listElement.id}"/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="selectedIds" value="${listElement.id}"/>
					</c:otherwise>
				</c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="Номер счета" value="${accountLink.number}"/>
            <sl:collectionItem title="Валюта"      value="${account.balance.currency.name}"/>
            <sl:collectionItem title="Тип"         value="${account.type}"/>
            <sl:collectionItem title="Псевдоним"   value="${listElement.name}"/>
                <%--используется для "Показывать закрытые счета"--%>
            <sl:collectionItem styleId="isLockAccount<%=lineNumber%>" hidden="true" value="${!account.isOpen || account.isLock}"/>
        </sl:collection>
    </tiles:put>
</tiles:insert>
</c:if>
<!-- список псевдонимов карт -->
<c:set var="cardsCount" value="${phiz:size(ShowPseudonymsListForm.cardPseudonyms)}"/>
<c:if test="${cardsCount>0}">
<br>
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="CardsListTable"/>
	<tiles:put name="image" value=""/>
	<tiles:put name="text" value="Карты"/>
	<tiles:put name="grid">
        <% int lineNumber = 0; %>
        <sl:collection id="listElement" model="list" property="cardPseudonyms">
            <%lineNumber++;%>
			<c:set var="cardLink_" value="${listElement.link}"/>
            <c:set var="cardInfo_" value="${cardLink_.card}"/>

            <sl:collectionItem title="доступ">
                <c:choose>
                    <c:when test="${listElement.hasAccess}">
                        <input type="checkbox" name="selectedIds" checked="${listElement.hasAccess}" value="${listElement.id}"/>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" name="selectedIds" value="${listElement.id}"/>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="Номер карты" value="${card.number}"/>
            <sl:collectionItem title="Валюта"      value="${cardInfo_.balance.currency.name}"/>
            <sl:collectionItem title="Тип карты"   value="${cardLink_.description}"/>
            <sl:collectionItem title="Псевдоним"   value="${listElement.name}"/>
            <sl:collectionItem styleId="isLockCard<%=lineNumber%>" hidden="true" value="${!(cardInfo_.cardState=='active')}"/>
        </sl:collection>
    </tiles:put>
</tiles:insert>
</c:if>

<!-- список псевдонимов депозитов -->
<c:set var="depositsCount" value="${phiz:size(ShowPseudonymsListForm.depositPseudonyms)}"/>
<c:if test="${depositsCount>0}">
	<br>
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="DepositsListTable"/>
	<tiles:put name="image" value=""/>
	<tiles:put name="text" value="Вклады"/>
	<tiles:put name="grid">
        <% int lineNumber = 0; %>
        <sl:collection id="listElement" model="list" property="depositPseudonyms">
            <% lineNumber++;%>
			<c:set var="depositLink_" value="${listElement.link}"/>
            <c:set var="deposit"      value="${depositLink_.deposit}"/>
            <c:set var="depositInfo_" value="${depositLink_.depositInfo}"/>

            <sl:collectionItem title="доступ">
                <c:choose>
					<c:when test="${listElement.hasAccess}">
						<input type="checkbox" name="selectedIds" checked="${listElement.hasAccess}" value="${listElement.id}"/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="selectedIds" value="${listElement.id}"/>
					</c:otherwise>
				</c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="Вид вклада" value="${deposit.description}"/>
            <sl:collectionItem title="Валюта"     name="deposit" property="amount.currency.name"/>
            <sl:collectionItem>
                <c:if test="${not empty deposit.duration}">
					<bean:write name="deposit" property="duration"/> дней
				</c:if>
            </sl:collectionItem>
            <sl:collectionItem title="">
                <c:if test="${not empty deposit.endDate}">
					<bean:write name="deposit" property="endDate.time" format="dd.MM.yyyy"/>
				</c:if>
            </sl:collectionItem>
            <sl:collectionItem title="Псевдоним" value="${listElement.name}"/>
            <sl:collectionItem styleId="isLockDeposit<%=lineNumber%>" hidden="true" value="${deposit.state == 'closed'}"/>
        </sl:collection>
	</tiles:put>
</tiles:insert>
</c:if>
<br>
<br>
<c:if test="${accountsCount+cardsCount+depositsCount>0}">
	<input name="ViewAll" id="ViewAll" type="checkbox" style="border:none" onclick="viewAll();">
		<script type="text/javascript">
			document.write(getViewAllButtonName(${accountsCount}, ${cardsCount}, ${depositsCount}));
		</script>
	</input>
</c:if>
<br>
</td>   <%--некорректно подсвечивается из-за того, что <tr> к таблицам прописан в шаблоне, а </tr> здесь--%>
</tr>
</table>
</div>
</td>
</tr>
</table>
<script type="text/javascript">
	onLoad();
</script>
</html:form>