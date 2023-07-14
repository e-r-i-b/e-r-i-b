<%@ page import="com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/claims/claim" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>
	<c:set var="metadataPath" value="${form.metadataPath}"/>

	<c:set var="layoutDefinition" value="claimMain"/>
	<c:if test="${form.form=='LoanClaim'}">
	    <c:set var="layoutDefinition" value="loanMain"/>
	</c:if>

	<tiles:insert definition="${layoutDefinition}">
		<tiles:put name="submenu" type="string" value="${metadataPath}"/>

		<!-- меню -->
		<tiles:put name="menu" type="string">

			<c:if test="${form.form=='LoanClaim'}">
				<tiles:insert definition="clientButton" flush="fase">
					<tiles:put name="commandTextKey" value="button.newClaim"/>
					<tiles:put name="commandHelpKey" value="button.newClaim.help"/>
					<tiles:put name="bundle" value="securityBundle"/>
					<tiles:put name="onclick" value="confirm('Вы хотите создать новую заявку, без сохранения текущей?')"/>
					<tiles:put name="action" value="/private/loans/claim.do?force=true"/>
				</tiles:insert>
			</c:if>			

			<c:if test="${form.form!='LoanClaim'}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.listClaims"/>
					<tiles:put name="commandHelpKey" value="button.listClaims.help"/>
					<tiles:put name="bundle"         value="claimsBundle"/>
					<tiles:put name="action"         value="/private/claims.do"/>
				</tiles:insert>
			</c:if>

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle"         value="claimsBundle"/>
				<tiles:put name="action"         value="/private/claims/claims.do?name=${listFormName}"/>
			</tiles:insert>

		</tiles:put>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
			<html:hidden property="form"/>
			<html:hidden property="templateName"/>

			<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="${form.metadata.form.name}"/>
			<tiles:put name="name" value="${form.metadata.form.description}"/>
			<tiles:put name="description" value="${form.metadata.form.detailedDescription}"/>
			<tiles:put name="data">

			<span onkeypress="onEnterKey(event);">
				<%=((CreatePaymentForm) request.getAttribute("CreatePaymentForm")).getHtml()%>
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
