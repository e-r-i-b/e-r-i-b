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
		var win = window.open("${phiz:calculateActionURL(pageContext, '/private/cards/print.do')}?cardId=<%=request.getParameter("cardId")%>");
//		win.resizeTo(800, 700);
//		win.moveTo(100, 10);
	}
</script>

<tiles:insert definition="accountInfo">
<tiles:put name="pageTitle" type="string" value="���������� �� �����">
</tiles:put>
<tiles:put name="mainmenu" value="Info"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.print.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
		<tiles:put name="onclick" value="Print()"/>
		<tiles:put name="image" value="${imagePath}/print.gif"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.toList"/>
		<tiles:put name="commandHelpKey" value="button.toList.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
		<tiles:put name="action" value="/private/accounts.do"/>
		<tiles:put name="image" value="${imagePath}/back.gif"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<bean:define id="cardAccount" name="ShowCardInfoForm" property="cardAccount"/>
<bean:define id="card" name="ShowCardInfoForm" property="card"/>
<bean:define id="cardInfo" name="ShowCardInfoForm" property="card"/>
<bean:define id="cardClient" name="ShowCardInfoForm" property="cardClient"/>
<bean:define id="cardAccountClient" name="ShowCardInfoForm" property="cardAccountClient"/>
<br>
<table width="80%" align="center">
	<tr><td><font class='listPayPart'>���������� �� �����</font></td></tr>
	</table>
<table cellspacing="0" cellpadding="0" class="userTab" width="80%" align="center">
<% int lineNumber = 0;%>
	<c:if test="${not card.main}">
		<tr class="ListLine<%=lineNumber%2%>">
			<td class="ListItem">��� ��������� �������� �����</td>
	<%--�� ����� ���� "��� ��������� �������������� �����"
	 �� BUG006491 ��������� ���-��� �� ����� �� ������������� ��--%>
			<td class="ListItem">
				<nobr>
					<bean:write name="cardAccountClient" property="fullName"/>
					&nbsp;
				</nobr>
			</td>
		</tr>
    </c:if>
	<%lineNumber++;%>
	<tr class="ListLine0">
		<td class="ListItem">��� ��������� �����</td>
		<td class="ListItem">
			<nobr>
				<bean:write name="cardClient" property="fullName"/>
				&nbsp;
			</nobr>
		</td>
	</tr>
	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">��� �����</td>
		<td class="ListItem">
			<bean:write name="card" property="description"/>
			&nbsp;
		</td>
	</tr>
	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">����� � ���� ��������</td>
		<td class="ListItem">
			<nobr>
				<bean:write name="card" property="agreementNumber"/>
				&nbsp;��&nbsp;
				<bean:write name="card" property="agreementDate.time" format="dd.MM.yyyy"/>
			</nobr>
		</td>
	</tr>
	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">����� �����</td>
		<td class="ListItem">
			<c:set var="num" value="${card.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
			&nbsp;
		</td>
	</tr>	
	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">���� �������� �����</td>
		<td class="ListItem">
			<bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">����� �������������� �����</td>
		<td class="ListItem">
			<bean:write name="cardAccount" property="number"/>
			&nbsp;
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">����� �������������</td>
		<td class="ListItem">
			<c:choose>
				<c:when test="${!empty ShowCardInfoForm.overdraftInfo}">
					<bean:define id="overdraftInfo" name="ShowCardInfoForm" property="overdraftInfo"/>
					<bean:write name="overdraftInfo" property="limit.decimal"
									            format="0.00"/>
				</c:when>
				<c:otherwise>
					0
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">��������� ��������</td>
		<td class="ListItem">
			<c:choose>
			<c:when test="${card.main && !empty ShowCardInfoForm.overdraftInfo}">
				<fmt:formatNumber value="${cardInfo.balance.decimal+overdraftInfo.limit.decimal-
					overdraftInfo.currentOverdraftSum.decimal}" pattern="0.00"/>								
			</c:when>
			<c:when test="${card.main && empty ShowCardInfoForm.overdraftInfo}">
				<fmt:formatNumber value="${cardInfo.balance.decimal}" pattern="0.00"/>
			</c:when>
			<c:otherwise>
				<bean:write name="cardInfo" property="limit.decimal"
				            format="0.00"/>
			</c:otherwise>
			</c:choose>
			&nbsp;
		</td>
	</tr>

	<c:if test="${card.main}">
		<%lineNumber++;%>
		<tr class="ListLine<%=lineNumber%2%>">
			<td class="ListItem">������� �������������</td>
			<td class="ListItem">
				<c:choose>
					<c:when test="${!empty ShowCardInfoForm.overdraftInfo}">						
						<bean:write name="overdraftInfo" property="totalDebtSum.decimal"
											            format="0.00"/>
					</c:when>
					<c:otherwise>
						0
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:if>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">������</td>
		<td class="ListItem">
			<bean:write name="cardInfo" property="balance.currency.name"/>
		</td>
	</tr>

	<%lineNumber++;%>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem">���� ��������� ��������</td>
		<td class="ListItem">
			<c:if test="${not empty cardInfo.lastOperationDate}">
				<bean:write name="cardInfo" property="lastOperationDate.time" format="dd.MM.yyyy"/>
			</c:if>
			&nbsp;
		</td>
	</tr>
</table><br>
<c:if test="${card.main}">
	<c:set var="warningPeriod" value="${ShowCardInfoForm.warningPeriod}"/>
	<c:set var="currentDate" value="<%=DateHelper.getCurrentDate().getTime()%>"/>
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
				<%--TODO ������� � css--%>
				<span style="color:red">
			</c:if>
			<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
			<c:if test="${warning}">
				</span>
			</c:if>
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.type">
			<c:out value="${card.description}"/>
			&nbsp;
			<c:if test="${!ismain}">(��������.)</c:if>
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.currency" property="value.balance.currency.name"/>
		<sl:collectionItem title="�ard.list.header.state" property="value.statusDescription"/>
		<sl:collectionItem title="�ard.list.header.expiredate">
			<c:if test="${warning}">
				<%--TODO ������� � css--%>
				<B>
			</c:if>
			<fmt:formatDate value="${expireDate}" pattern="MM.yyyy"/>
			<c:if test="${warning}">
				</B>
			</c:if>
		</sl:collectionItem>
	</sl:collection>
</c:if>
</tiles:put>
</tiles:insert>
</html:form>
