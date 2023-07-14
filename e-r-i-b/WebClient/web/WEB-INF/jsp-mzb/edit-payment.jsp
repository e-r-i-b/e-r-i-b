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
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>
	<c:set var="metadataPath" value="${form.metadataPath}"/>
	<c:set var="insertDef" value="transferList"/>

	<c:if test="${listFormName == 'GoodsAndServicesPayment'}">
		<c:set var="insertDef" value="paymentMain"/>
	</c:if>

	<tiles:insert definition="${insertDef}">

		<tiles:put name="submenu" type="string" value="${metadataPath}"/>

		<!-- меню -->
		<tiles:put name="menu" type="string">

			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="image" value="save.gif"/>
				<tiles:put name="validationFunction" value="checkPayment()"/>
				<tiles:put name="isDefault" value="true"/>
			</tiles:insert>

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.saveAsTemplate"/>
				<tiles:put name="commandHelpKey" value="button.saveAsTemplate"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="image" value="SaveAsPatt.gif"/>
				<tiles:put name="onclick" value="enterTemplateName(event)"/>
			</tiles:insert>

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="image" value="back.gif"/>
				<tiles:put name="action" value="/private/payments/payments.do?name=${listFormName}"/>
			</tiles:insert>

		</tiles:put>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
		<script type="text/javascript">
			function enterTemplateName(event)
			{
				var h = 100;
                var w = 400;
                openDialog(event,w,h, "${phiz:calculateActionURL(pageContext, '/private/enterTemplateName.do')}");
			}
			function saveAsTemplate(templateName)
			{
				setElement('templateName',templateName);
				var button = new CommandButton('button.saveAsTemplate','');
				button.click();
			}
		</script>
		<html:hidden property="templateName"/>
						<span onkeypress="onEnterKey(event);">
							<%=((CreatePaymentForm) request.getAttribute("EditPaymentForm")).getHtml()%>
						</span>
		</tiles:put>
	</tiles:insert>
</html:form>
