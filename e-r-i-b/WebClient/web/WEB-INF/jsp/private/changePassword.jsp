<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/needChangePassword">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="list">
<!-- ��������� -->
<tiles:put name="pageTitle" type="string">����� ������</tiles:put>

<!--���� -->
<tiles:put name="menu" type="string">
	<c:if test="${form.passwordNumber != null}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.changePassword"/>
			<tiles:put name="commandHelpKey" value="button.changePassword.help"/>
			<tiles:put name="image" value="iconSm_save.gif"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="validationFunction" value="checkPassword()"/>
		</tiles:insert>
	</c:if>
</tiles:put>
<!-- ������ -->
<tiles:put name="data" type="string">

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

	<script type="text/javascript">
		function checkPassword()
		{
			var newPassword        = getElementValue('newPassword');
			var confirmNewPassword = getElementValue('confirmNewPassword');
			var changePasswordForm = getElementValue('password');
			preventDefault(event);

			if (newPassword == '')
			{
				alert("������� ����� ������");
				return false;
			}

			if (confirmNewPassword == '')
			{
				alert("������� ����� ������ ��� ���");
				return false;
			}

			if (newPassword != confirmNewPassword)
			{
				alert("����� ������ ������ ���������");
				return false;
			}

			if (changePasswordForm == '')
			{
				alert("������� ���� ��� ������������� ������");
				return false;
			}

			return true;
		}
	</script>

	<table cellspacing="2" cellpadding="0" onkeypress="onEnterKey(event);">
		<tr>
			<td colspan="3" height="20px">&nbsp;
		</tr>
		<tr>
			<td colspan="3" class="messageTab" height="40px" style="vertical-align:top">
				<img src="${globalImagePath}/info.gif" alt="" border="0"/>&nbsp;&nbsp;���
				������ ������ ���������� ������� ������, �������� ��� ������.
			</td>
		</tr>
		<c:choose>
			<c:when test="${form.cardNumber != null}">
				<tr>
					<td colspan="3" class="listPayment">&nbsp;&nbsp;��������� ������ �� ���� � �������</td>
				</tr>
				<tr>
					<td nowrap="true" class="Label">&nbsp;������� ����� ������:&nbsp;</td>
					<td>
						<html:password name="ChangePasswordForm" property="newPassword"/>
					</td>
					<td width="100%">&nbsp;</td>
				</tr>
				<tr>
					<td nowrap="true" class="Label">&nbsp;��������� ����� ������:&nbsp;</td>
					<td colspan="2">
						<html:password name="ChangePasswordForm" property="confirmNewPassword"/>

					</td>
				</tr>
				<tr>
					<td colspan="3" class="Label" style="text-align:left">
						<c:choose>
							<c:when test="${form.passwordNumber == null}">
								&nbsp;&nbsp;<span class="menuInsertText" style="font-weight:bold;color:red">�
								��� ��� ��������� �������</span>
							</c:when>
							<c:otherwise>
								&nbsp;&nbsp;<b>��� ������������� ����� ������ ������� ���� �
								<bean:write name="ChangePasswordForm" property="passwordNumber"/>
								�� ����� �
								<bean:write name="ChangePasswordForm" property="cardNumber"/>
								</b>
								<html:password name="ChangePasswordForm" property="password" maxlength="10"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</tiles:put>
</tiles:insert>
</html:form>
