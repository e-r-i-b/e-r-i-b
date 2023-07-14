<%@ page import="com.rssl.phizic.web.actions.payments.forms.CreatePaymentCaptchaForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loan/claim" onsubmit="return setEmptyAction(event)">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>
	<c:set var="metadataPath" value="${form.metadataPath}"/>

	<tiles:insert definition="loanAnonymousMain">
		<tiles:put name="submenu" type="string" value="${metadataPath}"/>

		<!-- меню -->
		<tiles:put name="menu" type="string">

			<tiles:insert definition="clientButton" flush="fase">
				<tiles:put name="commandTextKey" value="button.newClaim"/>
				<tiles:put name="commandHelpKey" value="button.newClaim.help"/>
				<tiles:put name="bundle" value="securityBundle"/>
				<tiles:put name="onclick" value="confirm('Вы хотите создать новую заявку, без сохранения текущей?')"/>
				<tiles:put name="action" value="/loan/claims/create.do?force=true"/>
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
			
			<%--${form.html}--%>
			<span onkeypress="onEnterKey(event);">
				<%=((CreatePaymentCaptchaForm) request.getAttribute("CreatePaymentCaptchaForm")).getHtml()%>
			</span>

				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td class="Width220 LabelAll">
						&nbsp;&nbsp;<b>Для сохранения введите символы, представленные на картинке:</b>
					</td>
					<td>
						<img src="${initParam.resourcesRealPath}/<%=((CreatePaymentCaptchaForm) request.getAttribute("CreatePaymentCaptchaForm")).getCaptchaId()%>-captcha"/>
					</td>
				</tr>
				<tr><td colspan="2"><input type="hidden" name="captchaId" value="<%=((CreatePaymentCaptchaForm) request.getAttribute("CreatePaymentCaptchaForm")).getCaptchaId()%>"/></td></tr>
				<tr>
					<td></td>
					<td>
					<table>
						<tr>
							<td>
								<input name="captchaCode" maxlength="10"/>
							</td>
							<td>
								<tiles:insert definition="commandButton" flush="false">
									<tiles:put name="commandKey"     value="button.capthca.refresh"/>
									<tiles:put name="commandHelpKey" value="button.capthca.refresh.help"/>
									<tiles:put name="bundle"         value="securityBundle"/>
								</tiles:insert>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</tiles:put>
			<tiles:put name="buttons">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save.help"/>
					<tiles:put name="bundle"         value="claimsBundle"/>
					<tiles:put name="isDefault"        value="true"/>
					<tiles:put name="image" value="iconSm_save.gif"/>
					<tiles:put name="stateObject" value="document"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
