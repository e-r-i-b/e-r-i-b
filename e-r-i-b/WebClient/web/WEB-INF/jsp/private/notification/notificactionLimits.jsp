<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/notification/limits"  onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="dictionary">
		<tiles:put type="string" name="messagesBundle" value="notification"/>
        <tiles:put name="pageTitle" type="string">
			Задание параметров оповещений: остатки на счетах и картах
		</tiles:put>
         <!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="onclick" value="sendLimitData(event);"/>
				<tiles:put name="commandTextKey" value="button.select"/>
				<tiles:put name="commandHelpKey" value="button.select"/>
				<tiles:put name="bundle" value="notificationsBundle"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel"/>
				<tiles:put name="bundle" value="notificationsBundle"/>
				<tiles:put name="onclick" value="window.close();"/>
			</tiles:insert>
		</tiles:put>
		<!--данные-->
		<tiles:put name="data" type="string">


	<script type="text/javascript">
		function sendLimitData(event)
		{
			var accountIds = document.getElementsByName("accountSelectedIds");
			var cardIds = document.getElementsByName("cardSelectedIds");
			preventDefault(event);
			var result = '';
			//alert('Счета');
			for (i = 0; i < accountIds.length; i++)
			{
				if (accountIds.item(i).checked)
				{
					var number = accountIds.item(i).value;
					var limit = getElementValue('accountsLimit('+ number + ')');
					if( checkLimit(limit, number, "account") )
						result += 'a'+number+':'+ limit + ';';
					else return;
					//alert('Номер ' +number+ '.Ограничение ' + limit + 'Результат ' + result);
				}
			}
			//alert('Карты');
			for (i = 0; i < cardIds.length; i++)
			{
				if (cardIds.item(i).checked)
				{
					var id = cardIds.item(i).value;
					var limit = getElementValue('cardsLimit('+ id + ')');

					var r = cardIds.item(i).parentNode.parentNode;
					var cardNumber = trim(r.cells[1].innerHTML);

					if( checkLimit(limit, cardNumber, "card") )
						result += 'c'+id+':'+limit+';';
					else return;
					//alert('Номер ' +id+ '.Ограничение ' + limit + 'Результат ' + result);
				}
			}
			window.opener.setLimitInfo(result);
			window.close();
			return;
		}
		function checkLimit(limit,number,type)
		{
			if( limit==null || limit.length == 0 || (!limit.match(/^-?\d+([\.]\d{1,2})?$/)) )
			{
				var mess = "Не задан остаток для ";
				if(type == 'account')
					alert(mess + "счета номер " + number);
				else
					alert(mess + "карты номер " + number);
				return false;
			}
			return true;
		}
	</script>

				<c:set var="form" value="${ShowNotificationLimitsForm}"/>
				<c:set var="cards" value="${form.userCards}"/>
				<c:set var="accounts" value="${form.userAccounts}"/>
				<c:set var="selectedCards" value="${form.cardSelectedIds}"/>
				<c:set var="selectedAccounts" value="${form.accountSelectedIds}"/>

				<tiles:insert definition="tableTemplate" flush="false">
				<c:choose>
				<c:when test="${accounts != '[]'}">
					<% int lineNumber = 0; %>
					<tiles:put name="id" value="accountsTable"/>
					<tiles:put name="image" value="iconMid_accountsSettings.gif"/>
					<tiles:put name="text" value="Счета. Параметры оповещений"/>
					<tiles:put name="head">
	                	<td width="20px" align=center>
						  <input name="isSelectAllAccounts" type="checkbox" style="border:none"
								 onclick="switchSelection('isSelectAllAccounts','accountSelectedIds')"/>
						</td>
						<td width="200px">Номер счета</td>
						<td>Остаток</td>
					</tiles:put>
					<tiles:put name="data">
						<c:forEach items="${accounts}" var="account">
						<%lineNumber++;%>
							<tr  class="ListLine<%=lineNumber%2%>">
								<td align=center class="ListItem" width="20px">
									<html:multibox property="accountSelectedIds"
										style="border:none;">${account.number}</html:multibox>
								</td>
								<td class="ListItem">
									${account.number}
								</td>
								<td class="ListItem">
									<html:text name="form" property="accountsLimit(${account.number})" size="7" maxlength="7" style="text-align:right"/>&nbsp;
								</td>
							</tr>
						</c:forEach>
			       </tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="isEmpty" value="true"/>
					<tiles:put name="emptyMessage">Нет доступных счетов!</tiles:put>
				</c:otherwise>
				</c:choose>
				</tiles:insert>

				<!-- заголовок списка карт-->
			<tiles:insert definition="tableTemplate" flush="false">
				<c:choose>
				<c:when test="${cards != '[]'}">
					<% int lineNumber = 0; %>
					<tiles:put name="id" value="accountsTable"/>
					<tiles:put name="image" value="iconMid_cardsSettings.gif"/>
					<tiles:put name="text" value="Карты. Параметры оповещений"/>
					<tiles:put name="head">
	               	    <td width="20px" align=center>
						  <input name="isSelectAllCards" type="checkbox" style="border:none"
								 onclick="switchSelection('isSelectAllCards','cardSelectedIds')"/>
						</td>
						<td width="200px">Номер Карты</td>
						<td>Остаток</td>
					</tiles:put>
					<tiles:put name="data">
						<!-- строки списка -->
						<c:forEach items="${cards}" var="card">
							<%lineNumber++;%>
							<c:set var="num" value="${card.number}"/>
							<c:set var="len" value="${fn:length(num)}"/>
							<tr  class="ListLine<%=lineNumber%2%>">
								<td align=center class="ListItem" width="20px">
									<html:multibox property="cardSelectedIds"
										style="border:none;">${card.id}</html:multibox>
								</td>
								<td class="ListItem">
									<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
								</td>
								<td class="ListItem"><html:text property="cardsLimit(${card.id})" size="7" maxlength="7" style="text-align:right"/>&nbsp;&nbsp;</td>
							</tr>
						</c:forEach>
					</tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="isEmpty" value="true"/>
					<tiles:put name="emptyMessage">Нет доступных карт!</tiles:put>
				</c:otherwise>
				</c:choose>
				</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>