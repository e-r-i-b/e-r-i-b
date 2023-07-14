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

<html:form action="/private/cards/print" onsubmit="return setEmptyAction(event)">
<bean:define id="cardLink" name="PrintCardInfoForm" property="cardLink"/>
<bean:define id="cardAccount" name="cardLink" property="cardAccount"/>
<bean:define id="card" name="cardLink" property="card"/>
<bean:define id="cardInfo" name="cardLink" property="card"/>
<bean:define id="overdraftInfo" name="cardLink" property="currentOverdraftInfo"/>
<bean:define id="cardClient" name="cardLink" property="cardClient"/>
<bean:define id="user" name="PrintCardInfoForm" property="user"/>
<bean:define id="cardAccountClient" name="PrintCardInfoForm" property="cardAccountClient"/>

<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">
<br/>
<table cellspacing="0" cellpadding="0" class="userTab" width="80%" align="center">
<tr>
	<td width="20px"></td>
	<td>
		<table>
			<tr>
				<td colspan="2">
					Информация по карте №<b>
					<c:set var="num" value="${cardLink.number}"/>
			        <c:set var="len" value="${fn:length(num)}"/>
					<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
				</b> в АКБ "Демо" (ЗАО)
				<td>
			</tr>
			<tr>
				<td>Клиент:</td>
				<td width="100%">&nbsp;<b>
					<bean:write name="user"  property="fullName"/>
				</b></td>
			</tr>
			<tr>
				<td>Дата:</td>
				<td>&nbsp;<b><%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%>
				</b></td>
			</tr>
		</table>
		<br><br>
	</td>
</tr>
<tr>
<td></td>
<td>
<table>
	<% int lineNumber = 0;%>
	<c:if test="${not cardLink.main}">
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
			<bean:write name="cardLink" property="description"/>
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
			<c:set var="num" value="${cardLink.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">Срок действия карты</td>
		<td class="ListItem">
			<bean:write name="cardLink" property="expireDate.time" format="dd.MM.yyyy"/>
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
					<c:when test="${cardLink.main}">
						Текущий остаток:
						<bean:write name="cardInfo" property="balance.decimal" format="0.00"/>
						<c:if test="${cardType=='overdraft'}">
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
<br/>
<br/>
<br/>
<c:if test="${cardLink.main}">
	<sl:collection model="list"
				   property="additionalCardInfoSet"
				   id="cardInfoMapEntry"
				   bundle="cardInfoBundle">

		<c:set var="cardlink" value="${cardInfoMapEntry}"/>
		<c:set var="cardinfo" value="${cardlink.value}"/>
		<c:set var="expireDate" value="${cardlink.expireDate.time}"/>
		<c:set var="cardType" value="${cardlink.card.cardType}"/>

		<sl:collectionItem title="card.list.header.number">
			<c:set var="num" value="${cardlink.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.kind">
            "${cardType.displayDescription}"
			&nbsp;
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.type">
			<c:out value="${cardlink.description}"/>
			&nbsp;(дополнит.)
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.currency" property="value.balance.currency.name"/>
		<sl:collectionItem title="сard.list.header.state" property="value.statusDescription"/>
		<sl:collectionItem title="сard.list.header.expiredate">
			<fmt:formatDate value="${expireDate}" pattern="MM.yyyy"/>
		</sl:collectionItem>
	</sl:collection>
</c:if>
</td>
</table>
</tiles:put>
</tiles:insert>
</html:form>
