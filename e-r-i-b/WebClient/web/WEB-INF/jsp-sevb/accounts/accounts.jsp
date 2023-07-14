<%@ page import="com.rssl.phizic.logging.operations.LogEntry" %>
<%@ page import="com.rssl.phizic.business.operations.restrictions.CardRestriction" %>
<%@ page import="com.rssl.phizic.business.resources.external.AccountFilter" %>
<%@ page import="com.rssl.phizic.gate.bankroll.Account" %>
<%@ page import="com.rssl.phizic.gate.bankroll.Card" %>
<%@ page import="com.rssl.phizic.operations.account.GetAccountAbstractOperation" %>
<%@ page import="com.rssl.phizic.operations.card.GetCardAbstractOperation"%>
<%@ page import="com.rssl.phizic.web.util.RestrictionUtil"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">

<tiles:insert definition="accountInfo">
<tiles:put name="pageTitle" type="string">
	Пользователь: <bean:write name="ShowAccountsForm" property="user.fullName"/>. Счета и карты
</tiles:put>
<tiles:put name="mainmenu" value="Info"/>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function redirectToLossPassbookApplication()
		{
			var controlName = 'selectedAccountsIds';

			if(!checkOneSelection(controlName ,'Выберите один счет'))
				return;

			var id = getFirstSelectedId(controlName);
			var number = getElementValue('accountNumber'+id);
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/payment.do?form=LossPassbookApplication')}"/>
			window.location = '${url}&accountSelect='+number;
		}

		function redirectToBlockingCardClaim()
		{
			var controlName = 'selectedCardsIds';

			if(!checkOneSelection(controlName ,'Выберите одну карту'))
				return;

			var id = getFirstSelectedId(controlName);
			var number = getElementValue('cardNumber'+id);
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/payment.do?form=BlockingCardClaim')}"/>
			window.location = '${url}&card='+number;
		}

		function isAllCurrentAccounts()
		{
			var accounts = document.getElementsByName("selectedAccountsIds");
			var checkOne = false;
			var allCurrent = true;
			for (i = 0; i < accounts.length; i++)
			{
				if (accounts[i].checked)
				{
					checkOne = true;
					var r = accounts.item(i).parentNode.parentNode.cells[1].textContent;
					if (trim(r).substring(0, 5) != "40817" && trim(r).substring(0, 5) != "40820")
					{
						allCurrent = false;
						break;
					}
				}
			}
			if (allCurrent && checkOne)
			{
				alert("Выбраны только текущие счета. По текущим счетам справка не выдается.");
				return;
			}
			return true;
		}

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

			if (accountsListTable != null)
				viewTable(accountsListTable,"isLockAccount", false);
			if (cardsListTable != null)
				viewTable(cardsListTable,"isLockCard", false);
		}

		function viewAll()
		{
			var accountsListTable = document.getElementById("AccountsListTable");
			var cardsListTable = document.getElementById("CardsListTable");
			var viewAll = document.getElementById("ViewAll");
			if (viewAll != null)
			{
				if (accountsListTable != null)
					viewTable(accountsListTable,"isLockAccount", viewAll.checked);
				if (cardsListTable != null)
					viewTable(cardsListTable,"isLockCard", viewAll.checked);
			}
		}

		function getViewAllButtonName(accountsCount, cardsCount)
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

			return str;
		}

	</script>
<c:if test="${phiz:userHasCards()}">

		<tiles:insert definition="clientButton" service="BlockingCardClaim" flush="false">
             <tiles:put name="commandHelpKey"     value="button.lockCard.help"/>
             <tiles:put name="commandTextKey" value="button.lockCard"/>
             <tiles:put name="bundle"         value="commonBundle"/>
			 <tiles:put name="onclick">
					redirectToBlockingCardClaim()
				</tiles:put>
             <tiles:put name="image"          value="lock.gif"/>
        </tiles:insert>
</c:if>
		<tiles:insert definition="clientButton" service="LossPassbookApplication" flush="false">
             <tiles:put name="commandHelpKey"     value="button.lockAccount.help"/>
             <tiles:put name="commandTextKey" value="button.lockAccount"/>
             <tiles:put name="bundle"         value="commonBundle"/>
			 <tiles:put name="onclick">
					redirectToLossPassbookApplication()
				</tiles:put>
             <tiles:put name="image"          value="lock.gif"/>
        </tiles:insert>

		<tiles:insert definition="commandButton" flush="false" operation="GetAccountAbstractExtendedOperation">
             <tiles:put name="commandKey"     value="button.showAccountInformation"/>
             <tiles:put name="commandHelpKey" value="button.showAccountInformation.help"/>
             <tiles:put name="bundle"         value="commonBundle"/>
			 <tiles:put name="validationFunction">
				    isAllCurrentAccounts()
			 </tiles:put>
             <tiles:put name="image"          value="Information.gif"/>
        </tiles:insert>

        <tiles:insert definition="commandButton" flush="false" operation="GetAccountAbstractOperation">
             <tiles:put name="commandKey"     value="button.showAccountCertificate"/>
             <tiles:put name="commandHelpKey" value="button.showAccountCertificate.help"/>
             <tiles:put name="bundle"         value="commonBundle"/>
             <tiles:put name="image"          value="Information.gif"/>
        </tiles:insert>
</tiles:put>


<tiles:put name="data" type="string">
<div class="MaxSize" style="overflow:auto;">
<%
	int lineNumber = 0;
	boolean isEmptyPage=true;
%>
<c:if test="${phiz:impliesOperation('GetAccountsOperation','AccountAndCardInfo')}">
<c:set var="accountsCount" value="${phiz:size(ShowAccountsForm.accounts)}"/>
<c:if test="${accountsCount>0}">
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="AccountsListTable"/>
	<tiles:put name="text" value="Счета"/>
	<tiles:put name="head">
		  <td width="20px">
			  <input name="isSelectAllAccounts" type="checkbox" style="border:none"
			         onclick="switchSelection('isSelectAllAccounts','selectedAccountsIds')"/>
		  </td>
		  <td width="150px">Номер счета</td>
		  <td nowrap="true">Тип</td>
		  <td width="100px">Остаток</td>
		  <td width="140px" title="Макс.сумма для снятия">Макс. сумма для снятия</td>
          <td width="50px" nowrap="true">Валюта</td>
	</tiles:put>
	<tiles:put name="data">
	  <!-- строки списка счетов -->
	  <%
         isEmptyPage=true;
         AccountFilter accountRestriction = (AccountFilter)RestrictionUtil.createRestriction("Abstract", GetAccountAbstractOperation.class);
	  %>
	  <logic:iterate id="accountLink" name="ShowAccountsForm" property="accounts">
		  <bean:define id="account" name="accountLink" property="value"/>
		<bean:define id="accountInfo" name="accountLink" property="accountInfo"/>
		  <%
			  lineNumber++;
              isEmptyPage=false;
              pageContext.setAttribute("passRestriction", Boolean.valueOf(accountRestriction.accept((Account) account)));
		  %>
		  <tr class="ListLine<%=lineNumber%2%>">
			  <td align=center class="ListItem">
				  <c:if test="${passRestriction}">
					  <html:multibox property="selectedAccountsIds"
					                 style="border:none;">${accountLink.id}</html:multibox>
				  </c:if>
				  <c:if test="${!passRestriction}">&nbsp;</c:if>
	          </td>
			  <td class="ListItem">
				<nobr>
				  <html:hidden value="${account.number}" property="accountNumber${accountLink.id}"/>
				  <c:if test="${passRestriction}">
					  <phiz:link action="/private/accounts/abstract"
					             serviceId="Abstract">
                          <phiz:param name="accountIds" value="${accountLink.id}"/>
                          <phiz:param name="list" value="selected"/>
						  ${account.number}
					  </phiz:link>
				  </c:if>
				  <c:if test="${!passRestriction}">
					  ${account.number}
				  </c:if>
				  <c:if test="${!accountInfo.isOpen || accountInfo.isLock}">
				        <img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/>
				  </c:if>
				</nobr>
			  </td>
			  <td class="ListItem">
				  <bean:write name="account" property="type"/>
			  </td>
			  <td class="ListItem" nowrap="true" align="right">
				  <bean:write name="accountInfo" property="balance.decimal" format="0.00"/>&nbsp;
			  </td>
              <td class="ListItem" nowrap="true" align="right">
                  <bean:write name="accountInfo" property="maxSumWrite.decimal" format="0.00"/>&nbsp;
              </td>
              <td class="ListItem" nowrap="true" align="center">
                  <bean:write name="accountInfo" property="balance.currency.code"/>
              </td>
			<%--используется для "Показывать закрытые счета"--%>
			  <td id="isLockAccount<%=lineNumber%>" style="display:none">${!accountInfo.isOpen || accountInfo.isLock}</td>
		  </tr>
	  </logic:iterate>
	</tiles:put>
</tiles:insert>
</c:if>
</c:if>
  <!--список карт1 -->
<c:if test="${phiz:impliesOperation('GetCardsOperation','AccountAndCardInfo')}">
<c:set var="cardsCount" value="${phiz:size(ShowAccountsForm.cards)}"/>
<c:if test="${cardsCount>0}">
<br>
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="CardsListTable"/>
	<tiles:put name="text" value="Карты"/>
	<tiles:put name="head">
		  <td width="20px">
			  <input name="isSelectAllCards" type="checkbox" style="border:none"
			         onclick="switchSelection('isSelectAllCards','selectedCardsIds')"/>
		  </td>
		  <td width="150px">Номер карты</td>
		  <td width="100px" nowrap="true">Тип карты</td>
   		  <td width="40px">Валюта</td>
		  <td width="60px">Баланс карты</td>
		  <td width="60px">Лимит получения наличных</td>
		  <td width="60px">Лимит покупок</td>
		  <td>Статус</td>
		  <td width="60px" title="Срок действия карты">Срок действия карты</td>
	</tiles:put>
	<tiles:put name="data">
	  <!-- строки списка карт -->
	  <%
		  lineNumber = 0;
		  CardRestriction cardRestriction = (CardRestriction)RestrictionUtil.createRestriction("Abstract", GetCardAbstractOperation.class);
	  %>
	  <logic:iterate id="cardLink" name="ShowAccountsForm" property="cards">
		   <bean:define id="card" name="cardLink" property="value"/>
		   <bean:define id="cardInfo" name="cardLink" property="cardInfo"/>
		  <%
			  lineNumber++;
              isEmptyPage=false;
              pageContext.setAttribute("passRestriction", Boolean.valueOf(cardRestriction.accept((Card) card)));
		  %>
		  <tr class="ListLine<%=lineNumber%2%>">
			  <td align=center class="ListItem">                 
				  <c:if test="${passRestriction}">
					  <html:multibox property="selectedCardsIds"
					                 style="border:none;">${cardLink.id}</html:multibox>
				  </c:if>
				  <c:if test="${!passRestriction}">&nbsp;</c:if>
			  </td>
			  <td class="ListItem">
				  <html:hidden value="${cardLink.id}" property="cardNumber${cardLink.id}"/>
				  <c:if test="${passRestriction}">
					  <phiz:link action="/private/accounts/abstract" serviceId="Abstract">
                          <phiz:param name="cardIds" value="${cardLink.id}"/>
                          <phiz:param name="list" value="selected"/>
						  ${phiz:getCutCardNumber(card.number)}
					  </phiz:link>
				  </c:if>
				  <c:if test="${!passRestriction}">${card.number}</c:if>
			  </td>
			  <td class="ListItem">
				  <bean:write name="card" property="type"/>
				  <c:if test="${card.main}">&nbsp;(основная)</c:if>
				  <c:if test="${!card.main}">&nbsp;(дополнит.)</c:if>
			  </td>
			  <td class="ListItem" align="center">
				  <bean:write name="cardInfo" property="balance.currency.code"/>
			  </td>			  
			  <td class="ListItem" nowrap="true" align="right">
				  <bean:write name="cardInfo" property="balance.decimal" format="0.00"/>&nbsp;
			  </td>
			  <td class="ListItem" nowrap="true" align="right">
				  <bean:write name="cardInfo" property="cashLimit.decimal" format="0.00"/>
				  &nbsp;
			  </td>
			  <td class="ListItem" nowrap="true" align="right">
				  <bean:write name="cardInfo" property="purchaseLimit.decimal" format="0.00"/>
				  &nbsp;
			  </td>
			  <td class="ListItem">
				  <nobr><bean:write name="cardInfo" property="statusDescription"/>&nbsp;</nobr>
			  </td>
			  <td class="ListItem" align="center">
				  <c:choose>
					  <c:when test="${phiz:warnUserCardJustAboutToExpire(card.expireDate)}">
						 &nbsp;<span style="color:red;font-weight:bold;"><bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/></span>&nbsp;
				      </c:when>
					  <c:otherwise>
						  &nbsp;<bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/>&nbsp;
					  </c:otherwise>
				  </c:choose>
			  </td>
			  <td id="isLockCard<%=lineNumber%>" style="display:none">${!(card.cardState=='active')}</td>
		  </tr>
	  </logic:iterate>
	</tiles:put>
</tiles:insert>
</c:if>
</c:if>
  <span id="logTable">
  <span class="workspaceTitle" height="40px">Список последних операций</span>
  <!--Список последних операций -->
  <table cellspacing="0" cellpadding="2" class="userTab" width="100%">
	  <tr class="titleTable">
		  <td> № записи </td>
		  <td width="120px">Дата и время</td>
		  <td width="*" align="left">Наименование операции</td>
	  </tr>
	  <!-- строки списка депозитов -->
	  <% lineNumber = 0;
		  String templ = "Идентификатор платежа=";
		  String id="";
	  %>
	  <c:if test="${!empty ShowAccountsForm.lastLoginLogEntry}">
		  <c:set var="loginEntry" value="${ShowAccountsForm.lastLoginLogEntry}"/>
		   <% lineNumber = 1;%>
		   <tr class="ListLine<%=lineNumber%2%>">
			  <td class="ListItem" align="center">&nbsp;<b><%=lineNumber%></b>&nbsp;</td>
			  <td class="ListItem" align="center"><b><bean:write name="loginEntry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>&nbsp;</b></td>
			  <td class="ListItem" align="left">&nbsp;
				  <b>
				  Последний вход в систему был произведен с IP-адреса:&nbsp;
				  <bean:write name="loginEntry" property="ipAddress"/>
				  </b>
			  </td>
		  </tr>
	  </c:if>
	  <script type="text/javascript">
		  function showPaymentInfo(id)
		  {
			  openWindow(null,'../private/payments/printMem.do?id='+id);

		  }
	  </script>
	  <logic:iterate id="logEntry" name="ShowAccountsForm" property="logEntries" length="10">
		  <% lineNumber++;
			  isEmptyPage = false;
		  %>
		  <%  LogEntry log = (LogEntry)logEntry;
			  String param = log.getParameters();
			  int start = param.indexOf(templ);
			  int end = param.indexOf(';');
			  id=null;
			  if((start!=-1)&&(end!=-1))id = param.substring(start+templ.length(),end);
		  %>
		  <tr class="ListLine<%=lineNumber%2%>">
			  <td class="ListItem" align="center">&nbsp;<%=lineNumber%>&nbsp;</td>
			  <td class="ListItem" align="center"><bean:write name="logEntry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>&nbsp;</td>
			  <td class="ListItem" align="left">&nbsp;
				  <%
					  if(id==null){
				  %>
				  	<bean:write name="logEntry" property="description"/>&nbsp;
				  <%
					  }else{
				  %>
				   <a href="javascript:showPaymentInfo(<%=id%>);"><bean:write name="logEntry" property="description"/></a>
			  	  <%
					  }
				  %>
			  </td>
		  </tr>
	  </logic:iterate>
  </table>
  </span>
<c:if test="${phiz:impliesService('PrintAccountAbstract') or phiz:impliesService('Abstract') or  phiz:impliesService('AccountAndCardInfo')}">
  <% if (lineNumber == 0)
  { %>
  <script type="text/javascript">hideTitle("logTable");</script>
  <%} %>
  <% if (isEmptyPage){ %>
  <center>
    <span class="messageTab">
        У пользователя нет доступных счетов и карт.
    </span>
  </center>
  <%}%>
</c:if>
	<br>
	<br>
	<c:if test="${accountsCount+cardsCount>0}">
		<input name="ViewAll" id="ViewAll" type="checkbox" style="border:none" onclick="viewAll();">
			<script type="text/javascript">
				document.write(getViewAllButtonName(${accountsCount}, ${cardsCount}));
			</script>
		</input>
	</c:if>
<script type="text/javascript">
	onLoad();
</script>
</div>
</tiles:put>
</tiles:insert>
</html:form>