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

		<!-- ��������� -->
		<tiles:put name="pageTitle" type="string">
			����� "<bean:write name="DepositDetailsForm" property="name"/>"
		</tiles:put>

		<!-- ���� -->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close"/>
				<tiles:put name="bundle" value="depositsBundle"/>
				<tiles:put name="image" value="back.gif"/>
				<tiles:put name="action" value="/private/deposits/products/list.do"/>
			</tiles:insert>
		</tiles:put>

		<!-- ���������� ������ -->
		<tiles:put name="data" type="string">

			<%=((DepositDetailsForm)request.getAttribute("DepositDetailsForm")).getHtml().toString()%>

		</tiles:put>

	</tiles:insert>

</html:form>

