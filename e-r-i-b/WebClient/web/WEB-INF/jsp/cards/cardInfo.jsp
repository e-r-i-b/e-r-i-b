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
<tiles:put name="mainmenu" value="Info"/>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.print.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
		<tiles:put name="onclick" value="Print()"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.toList"/>
		<tiles:put name="commandHelpKey" value="button.toList.help"/>
		<tiles:put name="bundle" value="accountInfoBundle"/>
		<tiles:put name="action" value="/private/accounts.do"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<bean:define id="cardLink" name="ShowCardInfoForm"  property="cardLink"/>
<bean:define id="cardAccount" name="cardLink" property="cardAccount"/>
<bean:define id="card" name="cardLink" property="card"/>
<bean:define id="cardInfo" name="cardLink" property="card"/>
<bean:define id="overdraftInfo" name="cardLink" property="currentOverdraftInfo"/>
<bean:define id="cardClient" name="cardLink" property="cardClient"/>
<bean:define id="cardAccountClient" name="ShowCardInfoForm" property="cardAccountClient"/>

<br/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="image" value="iconMid_cardsInfo.gif"/>
		<tiles:put name="text" value="���������� �� �����"/>
		<tiles:put name="data">
			<% int lineNumber = 0;%>
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem">��� ��������� �����</td>
				<td class="ListItem">
					<nobr>
				<%--�� ����� ���� "��� ��������� �������������� �����"
			 �� BUG006491 ��������� ���-��� �� ����� �� ������������� ��--%>
						<bean:write name="cardAccountClient" property="fullName"/>
						&nbsp;
					</nobr>
				</td>
			</tr>
			<%lineNumber++;%>
			<c:if test="${!cardLink.main}">
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem">��� ��������� �������������� �����</td>
				<td class="ListItem">
					<nobr>
						<bean:write name="cardClient" property="fullName"/>
						&nbsp;
					</nobr>
				</td>
			</tr>
			<%lineNumber++;%>
			</c:if>
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem">��� �����</td>
				<td class="ListItem">
					<bean:write name="cardLink" property="description"/>
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
					<c:set var="num" value="${cardLink.number}"/>
					<c:set var="len" value="${fn:length(num)}"/>
					<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
					&nbsp;
				</td>
			</tr>
			<%lineNumber++;%>
			<tr class="ListLine<%=lineNumber%2%>">
				<td class="ListItem">���� �������� �����</td>
				<td class="ListItem">
					<bean:write name="cardLink" property="expireDate.time" format="dd.MM.yyyy"/>
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
				<td class="ListItem">��������� ��������</td>
				<td class="ListItem">
					<nobr>
						<c:choose>
							<c:when test="${cardLink.main}">
								������� �������:
								<bean:write name="cardInfo" property="balance.decimal" format="0.00"/>
								<c:if test="${card.cardType=='overdraft'}">
									&nbsp;
									����� �������� ����������:
									<bean:write name="overdraftInfo" property="currentOverdraftSum.decimal"
												format="0.00"/>

								</c:if>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${cardInfo.limit.decimal=='0.00'}">
										��� ������
									</c:when>
									<c:otherwise>
										<bean:write name="cardInfo" property="limit.decimal"
											 format="0.00"/>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						&nbsp;
					</nobr>
				</td>
			</tr>
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
		</tiles:put>
	</tiles:insert>

<c:if test="${cardLink.main and (not empty ShowCardInfoForm.additionalCardInfoSet)}">
	<br/>
	<c:set var="warningPeriod" value="${ShowCardInfoForm.warningPeriod}"/>
	<c:set var="currentDate" value="<%=DateHelper.getCurrentDate().getTime()%>"/>
	<!--<table width="100%" cellspacing="5"><tr><td>-->
	<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="CardsListTable"/>
	<tiles:put name="image" value="iconMid_addCardsInfo.gif"/>
	<tiles:put name="text" value="���������� �� ���.�����"/>
	<tiles:put name="grid"> <tr><td>
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

		<c:set var="expireDate" value="${cardLink.expireDate.time}"/>
		<c:set var="warning" value="${warningDate.time >= expireDate.time}"/>

		<sl:collectionItem title="card.list.header.number" action="/private/cards/info?cardId=${cardlink.id}">
			<c:set var="num" value="${cardLink.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:if test="${warning}">
				<%--TODO ������� � css--%>
				<span style="color:red">
			</c:if>
			<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
			<c:if test="${warning}">
				</span>
			</c:if>
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.kind">
            "${cardType.displayDescription}"
			&nbsp;
		</sl:collectionItem>
		<sl:collectionItem title="card.list.header.type">
			<c:out value="${cardLink.description}"/>
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
	</sl:collection>  </td></tr>
	</tiles:put>
	</tiles:insert>
	<!--</td></tr></table>-->
</c:if>
</tiles:put>
</tiles:insert>
</html:form>
