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
		Вклады
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
				<html:option value="">Все</html:option>
				<html:option value="30">До 30 дней</html:option>
				<html:option value="90">От 31 до 90 дней</html:option>
				<html:option value="180">От 91 до 180 дней</html:option>
				<html:option value="360">От 181 дней до 1 года</html:option>
				<html:option value="1080">От 1 до 3 лет</html:option>
				<html:option value="more">Свыше 3 лет</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.state"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="O">Открытый</html:option>
				<html:option value="C">Закрытый</html:option>
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
				<html:option value="">Все</html:option>
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
				<html:option value="">Все</html:option>
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
		if (!checkSumRange("filter(fromSum)", "filter(toSum)", "Сумма с", "Сумма по")) return false;
		if (!checkPeriod("filter(fromOpenDate)", "filter(toOpenDate)", "Дата открытия с", "Дата открытия по")) return false;
		return (checkPeriod("filter(fromCloseDate)", "filter(toCloseDate)", "Дата закрытия с", "Дата закрытия по"));
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
	<tiles:put name="text" value="Вклады"/>

	<tiles:put name="data">
		<td width="20px">
			  <input name="isSelectAll" type="checkbox" style="border:none"
			         onclick="switchSelection()"/>
		  </td>
		  <td>Вид вклада</td>
		  <td width="60px">Сумма</td>
		  <td width="40px">Валюта</td>
		  <td width="60px">Срок</td>
		  <td width="40px">%</td>
		  <td width="100px">Дата открытия</td>
		  <td width="100px">Дата окончания</td>
		  <td width="60px">Статуc</td>
		  <td width="120px">Договор</td>
	  </tr>
	  <!-- строки списка депозитов -->
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
			         <bean:write name="deposit" property="duration"/> дней
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
						  Открыт
					  </c:when>
					  <c:when test="${deposit.state=='closed'}">
						  Закрыт
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
  <span class="workspaceTitle" height="40px">Вклады</span>
  <!--список депозитов -->
  <table cellspacing="0" cellpadding="0" class="userTab" width="100%">
	  <tr class="titleTable">

  </table>
  </span>
	<% if (lineNumber == 0)
	{ %>
	<script type="text/javascript">hideTitle("DepositsTable");</script>
	<center>
            <span class="messageTab">
              Нет вкладов, соответствующих заданному фильтру.
           </span>
	</center>
	<%} %>
--%>
</tiles:put>
</tiles:insert>
</html:form>