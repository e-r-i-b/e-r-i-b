<%--
  User: IIvanova
  Date: 16.05.2006
  Time: 13:04:53
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/private/deposits" onsubmit="return checkData();">
<tiles:insert definition="depositMain">
	<tiles:put name="pageTitle" type="string">
		������
	</tiles:put>
	<tiles:put name="submenu" type="string" value="deposits"/>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.sum"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="name" value="Sum"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.duration"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(duration)" styleClass="select">
				<html:option value="">���</html:option>
				<html:option value="30">�� 30 ����</html:option>
				<html:option value="90">�� 31 �� 90 ����</html:option>
				<html:option value="180">�� 91 �� 180 ����</html:option>
				<html:option value="360">�� 181 ���� �� 1 ����</html:option>
				<html:option value="1080">�� 1 �� 3 ���</html:option>
				<html:option value="more">����� 3 ���</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.state"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="select">
				<html:option value="">���</html:option>
				<html:option value="O">��������</html:option>
				<html:option value="C">��������</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.openDate"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="name" value="OpenDate"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.depositType"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(depositType)" styleClass="select">
				<html:option value="">���</html:option>
				<logic:iterate id="kind" name="DepositsForm" property="depositKinds">
					<html:option value="${kind}">${kind}</html:option>
				</logic:iterate>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.sumCurrency"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(sumCurrency)" styleClass="select">
				<html:option value="">���</html:option>
				<html:option value="RUB">RUB</html:option>
				<html:option value="USD">USD</html:option>
				<html:option value="EUR">EUR</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.closeDate"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="name" value="CloseDate"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>

<script type="text/javascript">
	document.imgPath = "${initParam.resourcesRealPath}/images/";
	function initTemplates()
	{
	}
	function clearMasks(event)
	{
		clearInputTemplate("filter(fromOpenDate)", DATE_TEMPLATE);
		clearInputTemplate("filter(toOpenDate)", DATE_TEMPLATE);
		clearInputTemplate("filter(fromCloseDate)", DATE_TEMPLATE);
		clearInputTemplate("filter(toCloseDate)", DATE_TEMPLATE);
	}

	function checkData()
	{
		if (!checkSumRange("filter(fromSum)", "filter(toSum)", "����� �", "����� ��")) return false;
		if (!checkPeriod("filter(fromOpenDate)", "filter(toOpenDate)", "���� �������� �", "���� �������� ��")) return false;
		return (checkPeriod("filter(fromCloseDate)", "filter(toCloseDate)", "���� �������� �", "���� �������� ��"));
	}
	function submit(event)
	{
		var frm = window.opener.document.forms.item(0);
		frm.action = '';
		frm.submit();
	}
	initTemplates();
</script>
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.products"/>
		<tiles:put name="commandHelpKey" value="button.products.help"/>
		<tiles:put name="bundle" value="depositsBundle"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">

<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="DepositsTable"/>
	<tiles:put name="image" value="iconMid_deposits.gif"/>
	<tiles:put name="text" value="������"/>

	<tiles:put name="data">
		<td width="20px">
			  <input name="isSelectAll" type="checkbox" style="border:none"
			         onclick="switchSelection()"/>
		  </td>
		  <td>��� ������</td>
		  <td width="60px">�����</td>
		  <td width="40px">������</td>
		  <td width="60px">����</td>
		  <td width="40px">%</td>
		  <td width="100px">���� ��������</td>
		  <td width="100px">���� ���������</td>
		  <td width="60px">�����c</td>
		  <td width="120px">�������</td>
	  </tr>
	  <!-- ������ ������ ��������� -->
	  <% int lineNumber = 0; %>
	  <logic:iterate id="depositLink" name="DepositsForm" property="deposits">
		  <bean:define id="deposit" name="depositLink" property="deposit"/>
		  <bean:define id="depositInfo" name="depositLink" property="depositInfo"/>
		  <% lineNumber++; %>
		  <tr class="ListLine<%=lineNumber%2%>">
			  <td align=center class="ListItem">
				  <html:multibox property="selectedIds" style="border:none">
					  <bean:write name="deposit" property="id"/>
				  </html:multibox>
			  </td>
			  <td class="ListItem">&nbsp;
				  <bean:write name="deposit" property="description"/>
			  </td>
			  <td class="ListItem" align="right">
				  <c:if test="${not empty deposit.amount}">
				     <bean:write name="deposit" property="amount.decimal" format="0.00"/>
				  </c:if>
				  &nbsp;</td>
			  <td class="ListItem" align="center">&nbsp;
				  <c:if test="${not empty deposit.amount}">
				     <bean:write name="deposit" property="amount.currency.code"/>
				  </c:if>
				  &nbsp;</td>
			  <td class="ListItem">&nbsp;
				  <c:if test="${not empty deposit.duration}">
			         <bean:write name="deposit" property="duration"/> ����
				  </c:if>
			  </td>
			  <td class="ListItem">&nbsp;
				  <c:if test="${not empty deposit.interestRate}">
				     <bean:write name="deposit" property="interestRate"/>
				  </c:if>
			  </td>
			  <td class="ListItem" align="center">&nbsp;
				  <c:if test="${not empty deposit.openDate}">
				     <bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>
				  </c:if>
				  &nbsp;</td>
			  <td class="ListItem" align="center">&nbsp;
		          <c:if test="${(not empty deposit.openDate) && (not empty deposit.duration)}">
				     <c:set var="closeDate" value="${phiz:addToDate(deposit.openDate.time, 0,0, deposit.duration)}"/>
				     <bean:write name="closeDate" format="dd.MM.yyyy"/>
				  </c:if>
				  &nbsp;</td>
			  <td class="ListItem" align="center">&nbsp;
				  <c:choose>
					  <c:when test="${deposit.state=='open'}">
						  ������
					  </c:when>
					  <c:when test="${deposit.state=='closed'}">
						  ������
					  </c:when>
				  </c:choose>
				  &nbsp;
			  </td>
			  <td class="ListItem">&nbsp;
				  <c:if test="${not empty depositInfo.agreementNumber}">
				     <bean:write name="depositInfo" property="agreementNumber"/>
				  </c:if>
			  </td>
		  </tr>
	  </logic:iterate>
	</tiles:put>
</tiles:insert>
<%--
  <span id="DepositsTable">
  <span class="workspaceTitle" height="40px">������</span>
  <!--������ ��������� -->
  <table cellspacing="0" cellpadding="0" class="userTab" width="100%">
	  <tr class="titleTable">

  </table>
  </span>
	<% if (lineNumber == 0)
	{ %>
	<script type="text/javascript">hideTitle("DepositsTable");</script>
	<center>
            <span class="messageTab">
              ��� �������, ��������������� ��������� �������.
           </span>
	</center>
	<%} %>
--%>
</tiles:put>
</tiles:insert>
</html:form>