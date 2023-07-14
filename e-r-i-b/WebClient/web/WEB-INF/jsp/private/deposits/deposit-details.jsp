<%@ page import="com.rssl.phizic.web.common.client.deposits.DepositDetailsForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/deposits/details" onsubmit="return setEmptyAction(event)">

	<tiles:insert definition="depositMain">

		<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

		<!-- заголовок -->
		<tiles:put name="pageTitle" type="string">
			¬клад "<bean:write name="DepositDetailsForm" property="name"/>"
		</tiles:put>

		<!-- меню -->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.calculator"/>
				<tiles:put name="commandHelpKey" value="button.calculator"/>
				<tiles:put name="bundle" value="depositsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/private/deposits/calculator.do?id=${form.id}"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close"/>
				<tiles:put name="bundle" value="depositsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/private/deposits/products/list.do"/>
			</tiles:insert>
		</tiles:put>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="creditInformation"/>
			<tiles:put name="image" value="iconMid_banksDeposits.gif"/>
			<tiles:put name="text" value="ƒепозит '${form.name}'"/>
			<tiles:put name="description" value="${form.description}"/>			
			<tiles:put name="data">
			<%=((DepositDetailsForm)request.getAttribute("DepositDetailsForm")).getHtml().toString()%>
		    </tiles:put>
		</tiles:insert>
		</tiles:put>

	</tiles:insert>

</html:form>

