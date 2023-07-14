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
		<tiles:put name="mainmenu" value="Claims"/>
		<tiles:put name="submenu" type="string" value="${metadataPath}"/>

		<!-- меню -->
		<tiles:put name="menu" type="string">

			<tiles:insert definition="clientButton" flush="fase">
				<tiles:put name="commandTextKey" value="button.newClaim"/>
				<tiles:put name="commandHelpKey" value="button.newClaim.help"/>
				<tiles:put name="bundle" value="securityBundle"/>
				<tiles:put name="onclick" value="confirm('Вы хотите создать новую заявку, без сохранения текущей?')"/>
				<tiles:put name="image" value="add.gif"/>
				<tiles:put name="action" value="/loan/claims/create.do?force=true"/>
			</tiles:insert>

			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle"         value="claimsBundle"/>
				<tiles:put name="image"          value="save.gif"/>
				<tiles:put name="isDefault"        value="true"/>
			</tiles:insert>

		</tiles:put>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
		<span onkeypress="onEnterKey(event);">
			<%=((CreatePaymentCaptchaForm) request.getAttribute("CreatePaymentCaptchaForm")).getHtml()%>
			<table>
				<tr>
					<td class="Label" style="text-align:left;">
						&nbsp;&nbsp;<b>Для сохранения введите символы, представленные на картинке.</b>
						<img src="${initParam.resourcesRealPath}/<%=((CreatePaymentCaptchaForm) request.getAttribute("CreatePaymentCaptchaForm")).getCaptchaId()%>-captcha"/>
						<input type="hidden" name="captchaId" value="<%=((CreatePaymentCaptchaForm) request.getAttribute("CreatePaymentCaptchaForm")).getCaptchaId()%>"/>
						<input name="captchaCode" maxlength="10"/><br>
						<tiles:insert definition="commandButton" flush="false">
							<tiles:put name="commandKey"     value="button.capthca.refresh"/>
							<tiles:put name="commandHelpKey" value="button.capthca.refresh.help"/>
							<tiles:put name="bundle"         value="securityBundle"/>
						</tiles:insert>
					</td>
				</tr>
			</table>
		</span>
		</tiles:put>
	</tiles:insert>
</html:form>
