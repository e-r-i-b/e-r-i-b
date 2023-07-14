<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/claims/confirm" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>
	<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>

	<tiles:insert definition="loanAnonymousMain">
		<tiles:put name="submenu" type="string" value="${form.metadataPath}"/>
		<!-- заголовок -->
		<tiles:put name="pageTitle" type="string">
			<c:out value="${form.formDescription}"/>
		</tiles:put>

		<!--меню-->
		<%--<tiles:put name="menu" type="string">

			<c:if test="${not confirmRequest.error}">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.confirm"/>
					<tiles:put name="commandHelpKey" value="button.confirm.help"/>
					<tiles:put name="bundle" value="claimsBundle"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="default" value="true"/>
				</tiles:insert>
			</c:if>
			
		</tiles:put>--%>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="data">

				${form.html}

				<tr><td colspan="2">
					<c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
					<tiles:insert  definition="${confirmTemplate}" flush="false">
						<tiles:put name="confirmRequest" beanName="confirmRequest"/>
						<tiles:put name="message" value="Проверьте правильность заполнения полей документа и нажмите кнопку \"Подтвердить\", чтобы передать документ в банк на исполнение."/>
					</tiles:insert>
				</td></tr>
			</tiles:put>

			<tiles:put name="buttons">
				<c:if test="${not confirmRequest.error}">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.dispatch"/>
					<tiles:put name="commandHelpKey" value="button.dispatch.help"/>
					<tiles:put name="bundle" value="claimsBundle"/>
					<tiles:put name="image" value="iconSm_confirm.gif"/>
					<tiles:put name="isDefault" value="true"/>
					<tiles:put name="stateObject" value="document"/>
				</tiles:insert>
			</c:if>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</tiles:put>

	</tiles:insert>

</html:form>

