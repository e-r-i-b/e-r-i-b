<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<input type="hidden" name="$$cryptoSignature" id="cryptoSignature"/>
<fieldset>
<table class="pmntConfirm" cellpadding="0" cellspacing="0">
	<c:if test="${not confirmRequest.error}">
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td class="Width220 LabelAll">
				&nbsp;&nbsp;<b>Для подтверждения введите символы, представленные на картинке:</b>
			</td>
			<td colspan="2">
				<img src="${initParam.resourcesRealPath}/${confirmRequest.captchaId}-captcha"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input name="$$confirmCaptchaCode" maxlength="10"/>
			</td>
			<td>
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.capthca.refresh"/>
					<tiles:put name="commandHelpKey" value="button.capthca.refresh.help"/>
					<tiles:put name="bundle"         value="securityBundle"/>
					<tiles:put name="onclick">window.location = window.location</tiles:put>
				</tiles:insert>
			</td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
	</c:if>
</table>
<table>
	<c:if test="${not empty message}">
		<tr class="titleHelp"><td>${message}</td></tr>
	</c:if>

	<c:if test="${confirmRequest.error}">
		<tr>
			<td class="error">
				<c:out value="${confirmRequest.errorMessage}"/>
			</td>
		</tr>
	</c:if>
</table>
</fieldset>


