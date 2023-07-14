<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/cards/info" onsubmit="return setEmptyAction(event)">
<script type="text/javascript" language="JavaScript">
	function Print()
	{
		var win = window.open("${phiz:calculateActionURL(pageContext, 'private/cards/print.do')}?cardId=<%=request.getParameter("cardId")%>");
//		win.resizeTo(800, 700);
//		win.moveTo(100, 10);
	}
</script>

<tiles:insert definition="accountInfo">
<tiles:put name="mainmenu" value="Info"/>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.toList"/>
		<tiles:put name="commandHelpKey" value="button.toList.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
		<tiles:put name="action" value="/private/accounts.do"/>
		<tiles:put name="image" value="back.gif"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.print.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
		<tiles:put name="onclick" value="Print()"/>
		<tiles:put name="image" value="print.gif"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<bean:define id="cardAccount" name="ShowCardInfoForm" property="cardAccount"/>
<bean:define id="card" name="ShowCardInfoForm" property="card"/>
<bean:define id="cardInfo" name="ShowCardInfoForm" property="card"/>
<bean:define id="overdraftInfo" name="ShowCardInfoForm" property="overdraftInfo"/>
<bean:define id="cardClient" name="ShowCardInfoForm" property="cardClient"/>
<bean:define id="cardAccountClient" name="ShowCardInfoForm" property="cardAccountClient"/>
<bean:define id="additionalCardInfo" name="ShowCardInfoForm" property="additionalCardInfoSet"/>
<br/>
<table cellspacing="0" cellpadding="0" class="userTab" width="80%" align="center">
	<% int lineNumber = 0;%>
	<c:if test="${not card.main}">
		<tr class="ListLine<%=lineNumber%2%>">
			<td class="ListItem">ФИО держателя основной карты</td>
	<%--На самом деле "ФИО владельца спецкарточного счета"
	 см BUG006491 Детальная инф-ция по карте не соответствует РО--%>
			<td class="ListItem">
				<nobr>
					<bean:write name="cardAccountClient" property="fullName"/>
					&nbsp;
				</nobr>
			</td>
		</tr>
	</c:if>
	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">ФИО держателя карты</td>
		<td class="ListItem">
			<nobr>
				<bean:write name="cardClient" property="fullName"/>
				&nbsp;
			</nobr>
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Тип карты</td>
		<td class="ListItem">
			<bean:write name="card" property="description"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Номер и дата договора</td>
		<td class="ListItem">
			<nobr>
				<bean:write name="card" property="agreementNumber"/>
				&nbsp;от&nbsp;
				<bean:write name="card" property="agreementDate.time" format="dd.MM.yyyy"/>
			</nobr>
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Номер карты</td>
		<td class="ListItem">
			<c:set var="num" value="${card.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Срок действия карты</td>
		<td class="ListItem">
			<bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Номер спецкарточного счета</td>
		<td class="ListItem">
			<bean:write name="cardAccount" property="number"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Доступные средства</td>
		<td class="ListItem">
			<nobr>
				<c:choose>
					<c:when test="${card.main}">
						Текущий остаток:
						<bean:write name="cardInfo" property="balance.decimal" format="0.00"/>
						<c:if test="${card.cardType=='overdraft'}">
							&nbsp;
							Сумма текущего овердрафта:
							<bean:write name="overdraftInfo" property="currentOverdraftSum.decimal"
							            format="0.00"/>

						</c:if>
					</c:when>
					<c:otherwise>
						<bean:write name="cardInfo" property="limit.decimal"
						            format="0.00"/>
					</c:otherwise>
				</c:choose>
				&nbsp;
			</nobr>
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Валюта</td>
		<td class="ListItem">
			<bean:write name="cardInfo" property="balance.currency.name"/>
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Дата последней операции</td>
		<td class="ListItem">
			<c:if test="${not empty cardInfo.lastOperationDate}">
				<bean:write name="cardInfo" property="lastOperationDate.time" format="dd.MM.yyyy"/>
			</c:if>
			&nbsp;
		</td>
	</tr>
</table>
<c:if test="${card.main}">
	<br/>
	<c:set var="warningPeriod" value="${ShowCardInfoForm.warningPeriod}"/>
	<c:set var="currentDate" value="<%=DateHelper.getCurrentDate().getTime()%>"/>
<table width="100%" cellspacing="5">
	<tr><td>
		<c:if test="${not empty additionalCardInfo}">
		<span class="filter">Дополнительные карты</span><br/>
	</td></tr>
	<tr><td>
		</c:if>
	<sl:collection model="list"
				   property="additionalCardInfoSet"
				   id="cardInfoMapEntry"
				   bundle="cardInfoBundle">

		<c:set var="cardlink" value="${cardInfoMapEntry}"/>
		<c:set var="card" value="${cardlink.value}"/>
		<c:set var="cardinfo" value="${card}"/>
		<c:set var="ismain" value="${card.main}"/>
		<c:set var="cardType" value="${card.cardType}"/>

		<c:set var="warningDate"
			   value="${phiz:addToDate(currentDate, 0,0, warningPeriod)}"/>

		<c:set var="expireDate" value="${card.expireDate.time}"/>
		<c:set var="warning" value="${warningDate.time >= expireDate.time}"/>
		<sl:collectionItem title="card.list.header.number" action="/private/cards/info?cardId=${cardlink.id}">
			<c:set var="num" value="${card.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:if test="${warning}">
				<%--TODO вынести в css--%>
				<span style="color:red">
			</c:if>
			<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
			<c:if test="${warning}">
				</span>
			</c:if>
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.kind">
			<c:choose>
				<c:when test="${cardType=='overdraft'}">
					"Овердрафт"
				</c:when>
				<c:when test="${cardType=='debit'}">
					"Дебетовая"
				</c:when>
				<c:when test="${cardType=='credit'}">
					"Кредитная"
				</c:when>
			</c:choose>
			&nbsp;
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.type">
			<c:out value="${card.description}"/>
			&nbsp;
			<c:if test="${!ismain}">(дополнит.)</c:if>
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.currency" property="value.balance.currency.name"/>
		<sl:collectionItem title="сard.list.header.state" property="value.statusDescription"/>
		<sl:collectionItem title="сard.list.header.expiredate">
			<c:if test="${warning}">
				<%--TODO вынести в css--%>
				<B>
			</c:if>
			<fmt:formatDate value="${expireDate}" pattern="MM.yyyy"/>
			<c:if test="${warning}">
				</B>
			</c:if>
		</sl:collectionItem>
	</sl:collection>
</td></tr></table>
</c:if>
</tiles:put>
</tiles:insert>
</html:form>
