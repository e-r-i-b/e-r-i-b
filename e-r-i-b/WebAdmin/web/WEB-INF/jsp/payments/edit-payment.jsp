<%@ page import="com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/payment" onsubmit="return setEmptyAction()">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.name}"/>
	<c:set var="metadataPath" value="${form.metadataPath}"/>
	<c:set var="metadata" value="${form.metadata}"/>

	<tiles:insert definition="paymentCurrent">
		<!-- заголовок -->
		<tiles:put name="pageTitle" type="string">
			<c:out value="${metadata.form.description}"/>
		</tiles:put>

		<tiles:put name="submenu" type="string" value="${metadataPath}"/>

		<!-- меню -->
		<tiles:put name="menu" type="string">

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="action" value="/private/payments/payments.do?name=${listFormName}"/>
			</tiles:insert>

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.goto.forms.list"/>
				<tiles:put name="commandHelpKey" value="button.goto.forms.list.help"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="action" value="/private/payments.do"/>
			</tiles:insert>

		</tiles:put>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
		<script type="text/javascript">
			function enterTemplateName(event)
			{
				var h = 100;
                var w = 400;
                openDialog(event,w,h, "${phiz:calculateActionURL(pageContext, "/private/enterTemplateName.do")}");
			}
			function saveAsTemplate(templateName)
			{
				setElement('templateName',templateName);
				var button = new CommandButton('button.saveAsTemplate','button.saveAsTemplate', 'document');
				button.click();
			}
			<%--alert("${metadataPath}");--%>
		</script>
		<html:hidden property="templateName"/>

		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="${metadata.form.name}"/>
			<tiles:put name="name" value="${metadata.form.description}"/>
			<tiles:put name="description" value="${metadata.form.detailedDescription}"/>
			<tiles:put name="data">
				<span onkeypress="onEnterKey(event);">
					<%=((CreatePaymentForm) request.getAttribute("EditPaymentForm")).getHtml()%>
				</span>
			</tiles:put>
			<tiles:put name="buttons">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save.help"/>
					<tiles:put name="bundle" value="paymentsBundle"/>
					<tiles:put name="validationFunction" value="checkPayment()"/>
					<tiles:put name="isDefault" value="true"/>
					<tiles:put name="image" value="iconSm_save.gif"/>
					<tiles:put name="stateObject" value="document"/>
                    <tiles:put name="postbackNavigation" value="true"/>
				</tiles:insert>

				<tiles:insert definition="clientButton" flush="false" service="${listFormName}" operation="EditTemplateOperation">
					<tiles:put name="commandTextKey" value="button.saveAsTemplate"/>
					<tiles:put name="commandHelpKey" value="button.saveAsTemplate"/>
					<tiles:put name="bundle" value="paymentsBundle"/>
					<tiles:put name="onclick" value="enterTemplateName(event)"/>
					<tiles:put name="image" value="iconSm_saveAsTemplate.gif"/>
				</tiles:insert>
                
                <c:if test="${form.form=='LoanClaim'}">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.saveAsDraft"/>
					<tiles:put name="commandHelpKey" value="button.saveAsDraft.help"/>
					<tiles:put name="bundle" value="paymentsBundle"/>
					<tiles:put name="image" value="iconSm_saveAsTemplate.gif"/>
					<tiles:put name="stateObject" value="document"/>
				</tiles:insert>
				</c:if>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>

		</tiles:put>
	</tiles:insert>
</html:form>
