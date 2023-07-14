<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.rssl.phizic.gate.bankroll.AccountTransaction" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/accounts/abstract" onsubmit="return checkData(event);">

<tiles:insert definition="accountInfo">

<tiles:put name="submenu" type="string" value="Abstract"/>
<!-- ��������� -->
<tiles:put name="pageTitle" type="string">
	������������:
	<bean:write name="ShowAccountAbstractForm" property="user.fullName"/>
	. ������� �� ������
</tiles:put>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<!-- ���� -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.printAbstract"/>
		<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="onclick" value="openPrint(event)"/>
		<tiles:put name="image" value="print.gif"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value="back.gif"/>
		<tiles:put name="action"  value="/private/accounts.do"/>
	</tiles:insert>
</tiles:put>
<!-- ������ -->
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			<html:select property="selectedResources" multiple="true" size="3"
			             onkeydown="onTabClick(event,'fromDateString')">
			<!--������ ��������� ������-->
			<c:choose>
			<c:when test="${not empty ShowAccountAbstractForm.accountLinks}">
				<c:forEach var="accountLink" items="${ShowAccountAbstractForm.accountLinks}">
					<c:set var="account" value="${accountLink.value}"/>
					<html:option value="a:${accountLink.id}">
						<c:out value="${account.number}(${account.currency.name}, ${account.description})"/>
					</html:option>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<html:option value="noAccounts" disabled="true">
					<c:out value="��� ��������� ������"/>
				</html:option>
			</c:otherwise>
			</c:choose>
			<!--������ ��������� ����-->
			<c:choose>
			<c:when test="${not empty ShowAccountAbstractForm.cardLinks}">
				<c:forEach var="cardLink" items="${ShowAccountAbstractForm.cardLinks}">
						<c:set var="card" value="${cardLink.value}"/>
						<html:option value="c:${cardLink.id}">
							<c:out value="${card.number}"/>
							<c:if test="${card.main}">
								(�������� �����)
							</c:if>
							<c:if test="${not card.main}">
								(�������������� �����)
							</c:if>
						</html:option>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<html:option value="noCards" disabled="true">
					<c:out value="��� ��������� ����"/>
				</html:option>
			</c:otherwise>
			</c:choose>
			<!--������ ��������� �������-->
			<c:choose>
			<c:when test="${not empty ShowAccountAbstractForm.depositLinks}">
				<c:forEach var="depositLink" items="${ShowAccountAbstractForm.depositLinks}">
						<c:set var="depoit" value="${depositLink.deposit}"/>
						<c:set var="depoitinfo" value="${ShowAccountAbstractForm.depositInfoMap[depoit.id]}"/>
						<html:option value="d:${depoit.id}">
							<c:out value="${depoitinfo.account.number}"/>&nbsp;<c:out value="${depoit.description}"/>
						</html:option>
				</c:forEach>
			</c:when>
				<c:otherwise>
					<html:option value="noDeposits" disabled="true">
					<c:out value="��� ��������� �������"/>
				</html:option>
			</c:otherwise>
	        </c:choose>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			&nbsp;c:
			<html:text property="fromDateString" styleClass="filterInput" size="10"
			           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
			&nbsp;��&nbsp;
			<html:text property="toDateString" styleClass="filterInput" size="10"
			           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</tiles:put>
	</tiles:insert>
<script language="JavaScript">
	document.imgPath = "${imagePath}/";

	function clearMasks(event)
	{
		clearInputTemplate("fromDateString", DATE_TEMPLATE);
		clearInputTemplate("toDateString", DATE_TEMPLATE);
	}
	function checkData(event)
	{
		if (!checkPeriod("fromDateString", "toDateString", "������ �", "������ ��", true)) return false;
		setEmptyAction(event);
		return true;
	}
	function callAbstractOperation(event, action, confirm)
	{
		preventDefault(event);
		res = getElementValue("selectedResources");
		if (res == "") alert("�������� �����/�����");
		else callOperation(event, action, confirm);
	}

	function openPrint(event)
	{
		if (checkData(event) == true)
		{   /* todo: ����������� �������� ���������� ����� ����� */
			var req;
			req = 'print.do?';
			req = req + "fromDateString=<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>&";
			req = req + "toDateString=<bean:write name='ShowAccountAbstractForm' property="toDateString"/>";
			<logic:iterate id="res" name="ShowAccountAbstractForm" property="selectedResources">
				req += "&sel=<bean:write name='res'/>";
			</logic:iterate>
			openWindow(event, req);
		}
	}
</script>
</tiles:put>
<!--������-->
<tiles:put name="data" type="string">
<!--������� �� ������ -->
<%int lineNumber; %>
<c:forEach items="${ShowAccountAbstractForm.accountAbstract}" var="listElement">
	<c:set var="account" value="${listElement.key}"/>
	<c:set var="abstract" value="${listElement.value}"/>

	<span class="filter">������� �� ����� � </span><b>
	<c:out value="${account.number}"/>
	</b>
	<br/>
	<nobr>
		<span class="filter">�� ����</span>
		<b>
			�<%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>
			�&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
			&nbsp;&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
			&nbsp;�.
		</b>
		<span class="filter">(�� ������ �</span>
		<b>
			�<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd"/>
			�&nbsp;<span id="monthStrFrom${account.number}"></span>&nbsp;&nbsp;
			<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="yyyy"/>&nbsp;�.
		</b>
		<span class="filter">��</span>
			<b>
				�<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd"/>
				�&nbsp;<span id="monthStrTo${account.number}"></span>&nbsp;&nbsp;
				<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="yyyy"/>&nbsp;�.
			</b>
		<span class="filter">)</span>
		<script>
			var fromDate = '<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>';
			var toDate = '<bean:write name='ShowAccountAbstractForm' property="toDateString"/>';
			var mounthStrFrom = document.getElementById('monthStrFrom${account.number}');
			var monthStrTo = document.getElementById('monthStrTo${account.number}');
			if (mounthStrFrom && monthStrTo){
				mounthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	    </script>
	</nobr>
	<br/>
	��������:&nbsp;<bean:write name='ShowAccountAbstractForm' property="client.fullName"/><br/>
	��������:&nbsp;
	<logic:iterate id="document" name="ShowAccountAbstractForm" property="user.personDocuments" >
		<c:if test="${document.documentMain}">
	        <c:choose>
				<c:when test="${document.documentType == 'REGULAR_PASSPORT_RF'}">
					��������������� ������� ��
				</c:when>
				<c:when test="${document.documentType == 'MILITARY_IDCARD'}">
					������������� �������� ���������������
				</c:when>
				<c:when test="${document.documentType == 'SEAMEN_PASSPORT'}">
					������� ������
				</c:when>
				<c:when test="${document.documentType == 'RESIDENTIAL_PERMIT_RF'}">
					��� �� ���������� ��
				</c:when>
				<c:when test="${document.documentType == 'FOREIGN_PASSPORT_RF'}">
					����������� ������� ��
				</c:when>
				<c:when test="${document.documentType == 'OTHER'}">
					���� ��������
				</c:when>
			</c:choose>
		    &nbsp;<bean:write name='document' property="documentSeries"/>
			&nbsp;<bean:write name='document' property="documentNumber"/>;
			&nbsp;<bean:write name='document' property="documentIssueBy"/>
		</c:if>
	</logic:iterate>
	<br/>
	���� ������:&nbsp;<bean:write name='account' property="openDate.time" format="dd.MM.yyyy"/><br/>
	���� ��������� ��������:&nbsp;<fmt:formatDate value="${abstract.previousOperationDate.time}" pattern="dd.MM.yyyy"/>
	<br/>
	<br/>
	�������� ������� �� �����:&nbsp;<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
	<br/>
	<c:choose>
	<c:when test="${phiz:size(abstract.transactions)==0}">
		<%--<span class="messageTab">
		    ��� �������� �� ����� �� �������� ������.
		</span>--%>
		<br/>
			��������� ������� �� �����:&nbsp;<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
		<br/>
	</c:when>
	<c:otherwise>
<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
	<tr class="titleTable">
		<td height="20px" rowspan="2" align="center" valign="middle">����</td>
		<td rowspan="2" align="center" valign="middle">���������� ��������</td>
		<td rowspan="2" align="center" valign="middle">����� ����� ��������������</td>
		<%--<td rowspan="2" align="center" valign="middle">���</td>
		<td rowspan="2" align="center" valign="middle">������������ �����</td>--%>
		<td colspan="2"align="center" valign="middle">����� � ������ �����</td>
	</tr>
	<tr class="titleTable">
		<td align="center" valign="middle">P������</td>
		<td align="center" valign="middle">�������</td>
	</tr>
	<%lineNumber = 1;%>
	<% BigDecimal debitSum = new BigDecimal(0.0); %>
	<% BigDecimal crebitSum = new BigDecimal(0.0); %>
	<logic:iterate id="transaction" name="abstract" property="transactions">
		<% lineNumber++; %>
		<tr class="ListLine<%=lineNumber%2%>">
			<td class="ListItem" align="center" valign="center">
				<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
				&nbsp;
			</td>
			<td class="ListItem" valign="center">
				<bean:write name="transaction" property="description"/>
				&nbsp;
			</td>
			<td class="ListItem" valign="center">
				<bean:write name="transaction" property="counteragentAccount"/>
				&nbsp;
			</td>
			<%--<td class="ListItem" valign="center">
				<bean:write name="transaction" property="counteragentBank"/>
				&nbsp;
			</td>
			<td class="ListItem" valign="center">
				<bean:write name="transaction" property="counteragentBankName"/>
				&nbsp;
			</td>--%>
			<td class="ListItem" align="right" valign="center">
				<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
				<%debitSum=debitSum.add(((AccountTransaction) transaction).getDebitSum().getDecimal());%>
				&nbsp;
			</td>
			<td class="ListItem" align="right" valign="center">
				<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
				<%crebitSum=crebitSum.add(((AccountTransaction) transaction).getCreditSum().getDecimal());%>
				&nbsp;
			</td>
		</tr>
	</logic:iterate>
	<% lineNumber++; %>
	<tr class="ListLine<%=lineNumber%2%>">
		<td class="ListItem" colspan="3">
			����� �� ������
		</td>
		<td class="ListItem" align="right">
			<%--<bean:write name="debitSum" format="0.00"/>--%>
			<%=debitSum.toPlainString()%>
		</td>
		<td class="ListItem" align="right">
			<%=crebitSum.toPlainString()%>
		</td>
	</tr>
	<% lineNumber++; %>
	<tr class="ListLine2 ListLine<%=lineNumber%2%>">
		<td class="ListItem" colspan="3">
			��������� ������� �� �����
		</td>
		<td class="ListItem" colspan="2">
			<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>&nbsp;
			<bean:write name="abstract" property="closingBalance.currency.code"/>
		</td>
	</tr>
</table>
</c:otherwise>
</c:choose>
<br/>
</c:forEach>
<!--������� �� ������ -->
<c:forEach items="${ShowAccountAbstractForm.cardAbstract}" var="listElement">
	<c:set var="card" value="${listElement.key}"/>
	<c:set var="abstr" value="${listElement.value}"/>
	<c:set var="cardinfo" value="${ShowAccountAbstractForm.cardInfoMap[card]}"/>
	<c:set var="cardaccount" value="${ShowAccountAbstractForm.cardAccountMap[card]}"/>
	<c:set var="cardclient" value="${ShowAccountAbstractForm.cardClientMap[card]}"/>

	<c:if test="${card.main}">
		<span class="filter">������� �� ����� �� ������ � </span>
	</c:if>
	<c:if test="${not card.main}">
		<span class="filter">�������� �� ����� �� ������ � </span>
	</c:if>
	<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/> �.
	<span class="filter">��</span>
	<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/> �.
	<c:if test="${card.main}">
			<span class="filter">���� �������������� ������� </span>
			<%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%> �.
	</c:if>
	<br/>
	<br/>��� ��������� �����:&nbsp;<c:out value="${cardclient.fullName}"/>
	<c:if test="${card.main}">
		<br/>����� ��������:&nbsp;<c:out value="${card.agreementNumber}"/>
	</c:if>
	<c:if test="${!card.main}">
		<br/>������ �����:&nbsp;<c:out value="${cardaccount.currency.code}"/>
	</c:if>
	<br/>����� �����:&nbsp;
		<c:set var="num" value="${card.number}"/>
		<c:set var="len" value="${fn:length(num)}"/>
		<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
		&nbsp;[<c:out value="${card.description}"/>]
	<c:if test="${card.main}">
		<br/>����� �������������� �����:&nbsp;<c:out value="${cardaccount.number}"/>
	</c:if>
	<c:if test="${card.main}">
		<br/>������ �����:&nbsp;<c:out value="${cardaccount.currency.code}"/>
	</c:if>
	<c:if test="${card.main}">
		<br/>����� �������:&nbsp;<c:out value="${cardinfo.limit.decimal}"/>
	</c:if>
	<br/>
	<c:choose>
		<c:when test="${phiz:size(abstr.transactions)==0}">
			<c:if test="${card.main}">
				<br/>
				�������� ������:&nbsp;<bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>
				<br/>
				<br/>
				��������� ������:&nbsp;<bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>
				<br/>
			</c:if>
			<%--<span class="messageTab">
			    ��� �������� �� ����� �� �������� ������.
			</span>--%>
		</c:when>
		<c:otherwise>
			<c:if test="${card.main}">
				<br/>
				�������� ������ �� ���: &nbsp;<bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>
				<br/>
			</c:if>
			<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
				<tr class="titleTable">
					<td height="20px" align="center">���� ��������</td>
					<td align="center">��� ��������</td>
					<td align="center">�����</td>
					<td align="center">������</td>
					<c:if test="${card.main}">
						<td align="center">������</td>
					</c:if>
				</tr>
				<%lineNumber = 0;%>
				<logic:iterate id="transaction" name="abstr" property="transactions">
					<% lineNumber++; %>
					<tr class="ListLine<%=lineNumber%2%>">
						<td class="ListItem" align="center" valign="center">
							<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
							&nbsp;
						</td>
						<td class="ListItem" valign="center">
							<bean:write name="transaction" property="description"/>
							&nbsp;
						</td>
						<td class="ListItem" align="right" valign="center">
							<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
							&nbsp;
						</td>
						<td class="ListItem" align="right" valign="center">
							<bean:write name="transaction" property="accountCreditSum.decimal" format="0.00"/>
							&nbsp;
						</td>
						<c:if test="${card.main}">
							<td class="ListItem" align="right" valign="center">
								&nbsp;
							</td>
						</c:if>
					</tr>
				</logic:iterate>
				<c:if test="${card.main}">
					<%lineNumber ++;%>
					<tr class="ListLine<%=lineNumber%2%>">
						<td colspan="4" class="ListItem">��������� ������</td>
						<td class="ListItem"><bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>&nbsp;</td>
					</tr>
					<%lineNumber ++;%>
					<tr class="ListLine<%=lineNumber%2%>">
						<td colspan="4" class="ListItem">��������� ����� ������������</td>
						<td class="ListItem"><c:out value="${cardinfo.limit.decimal}"/></td>
					</tr>
					<%lineNumber ++;%>
					<tr class="ListLine<%=lineNumber%2%>">
						<td colspan="4" class="ListItem">�������� ������ � ������ ������ ������������</td>
						<td class="ListItem">
							<c:out value="${cardinfo.limit.decimal + abstr.closingBalance.decimal}"/>
						</td>
					</tr>
				</c:if>
			</table>
		</c:otherwise>
	</c:choose>
	<br/>
</c:forEach>
<br/>
<!--������� �� ������� -->
<c:forEach items="${ShowAccountAbstractForm.depositAbstract}" var="listElement">
	<c:set var="deposit" value="${listElement.key}"/>
	<c:set var="account" value="${ShowAccountAbstractForm.depositInfoMap[deposit.id].account}"/>
	<c:set var="abstract" value="${listElement.value}"/>

	<span class="filter">������� �� ������ </span>
	<c:out value="${deposit.description}"/> �� <c:out value="${deposit.duration}"/> ���� � <c:out value="${deposit.amount.currency.code}"/>

	<br/>
	<nobr>
		<span class="filter">�� ����</span>
		<b>
			�<%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>�
			&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
			&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
			&nbsp;�.
		</b>
		<span class="filter">(�� ������ �</span>
		<b>
			�<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="dd"/>�&nbsp;
			<span id="monthStrFrom${account.number}"></span>&nbsp;
			&nbsp;<bean:write name='ShowAccountAbstractForm' property="fromDate.time" format="yyyy"/>
			&nbsp;�.
		</b>
		<span class="filter">��</span>
		<b>
			�<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="dd"/>�
			&nbsp;<span id="monthStrTo${account.number}"></span>
			&nbsp;<bean:write name='ShowAccountAbstractForm' property="toDate.time" format="yyyy"/>
			&nbsp;�.
		</b>
		<span class="filter">)</span>
		<script>
			var fromDate = '<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>';
			var toDate = '<bean:write name='ShowAccountAbstractForm' property="toDateString"/>';
			var mounthStrFrom = document.getElementById('monthStrFrom${account.number}');
			var monthStrTo = document.getElementById('monthStrTo${account.number}');
			if (mounthStrFrom && monthStrTo){
				mounthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	    </script>
	</nobr>
	<br/>
	��������:&nbsp;<bean:write name='ShowAccountAbstractForm' property="client.fullName"/><br/>
	��������:&nbsp;
	<logic:iterate id="document" name="ShowAccountAbstractForm" property="user.personDocuments" >
		<c:if test="${document.documentMain}">
	        <c:choose>
				<c:when test="${document.documentType == 'REGULAR_PASSPORT_RF'}">
					��������������� ������� ��
				</c:when>
				<c:when test="${document.documentType == 'MILITARY_IDCARD'}">
					������������� �������� ���������������
				</c:when>
				<c:when test="${document.documentType == 'SEAMEN_PASSPORT'}">
					������� ������
				</c:when>
				<c:when test="${document.documentType == 'RESIDENTIAL_PERMIT_RF'}">
					��� �� ���������� ��
				</c:when>
				<c:when test="${document.documentType == 'FOREIGN_PASSPORT_RF'}">
					����������� ������� ��
				</c:when>
				<c:when test="${document.documentType == 'OTHER'}">
					���� ��������
				</c:when>
			</c:choose>
		    &nbsp;<bean:write name='document' property="documentSeries"/>
			&nbsp;<bean:write name='document' property="documentNumber"/>;
			&nbsp;<bean:write name='document' property="documentIssueBy"/>
		</c:if>
	</logic:iterate>
	<br/>
	���� ��������� ��������:&nbsp;<fmt:formatDate value="${abstract.previousOperationDate.time}" pattern="dd.MM.yyyy"/>
	<br/>
	<br/>
	�������� ������� �� ������:&nbsp;
	<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
	<br/><br/>
	<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
		<tr class="titleTable">
			<td height="20px" align="center" valign="middle">����</td>
			<td align="center" valign="middle">����� �������</td>
			<td align="center" valign="middle">����� �������</td>
			<td align="center" valign="middle">����� ����� ��������������</td>
			<%--<td align="center" valign="middle">���</td>
			<td align="center" valign="middle">������������ �����</td>--%>
			<td align="center" valign="middle">���������� �������</td>
		</tr>
		<%int line = 0;%>
		<% BigDecimal debitSum = new BigDecimal(0.0); %>
		<% BigDecimal crebitSum = new BigDecimal(0.0); %>
		<logic:iterate id="transaction" name="abstract" property="transactions">
			<% line++; %>
			<tr class="ListLine<%=line%2%>">
				<td class="ListItem" align="center" valign="center">
					<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
					&nbsp;
				</td>
				<td class="ListItem" align="right" valign="center">
					<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
					<%crebitSum=crebitSum.add(((AccountTransaction) transaction).getCreditSum().getDecimal());%>
					&nbsp;
				</td>
				<td class="ListItem" align="right" valign="center">
					<%debitSum=debitSum.add(((AccountTransaction) transaction).getDebitSum().getDecimal());%>
					<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentAccount"/>
					&nbsp;
				</td>
				<%--<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentBank"/>
					&nbsp;
				</td>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="counteragentBankName"/>
					&nbsp;
				</td>--%>
				<td class="ListItem" valign="center">
					<bean:write name="transaction" property="description"/>
					&nbsp;
				</td>
			</tr>
		</logic:iterate>
		<tr class="ListLine<%=line%2%>">
			<td class="ListItem">
				����� �� ������
			</td>
			<td class="ListItem" align="right" valign="center">
				<%=crebitSum.toPlainString()%>
				&nbsp;
			</td>
			<td class="ListItem" align="right" valign="center">
				<%=debitSum.toPlainString()%>
				&nbsp;
			</td>
			<td class="ListItem" colspan="4">
				&nbsp;
			</td>
		</tr>
	</table>
	<br/>
	��������� ������� �� ������:&nbsp;
	<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
	<br/>
	<br/>
</c:forEach>


<script>
	switchFilter(this);	
</script>
</tiles:put>
</tiles:insert>
</html:form>
