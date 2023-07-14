<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/service">  
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<tiles:insert definition="services">
	<tiles:put name="submenu" type="string" value="changePassword"/>	
		<!-- ��������� -->
		<tiles:put name="pageTitle" type="string">����� ������</tiles:put>

		<!-- ������ -->
		<tiles:put name="data" type="string">
			<script type="text/javascript">
				function checkPassword()
				{
					var oldPassword        = document.getElementById('oldPassword').value;
					var newPassword        = document.getElementById('newPassword').value;
					var confirmNewPassword = document.getElementById('confirmNewPassword').value;

					try{ preventDefault(event);}catch(e){};					
					if (oldPassword == '')
					{
						alert("������� ������ ������");
						return false;
					}
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

					return true;
				}
			</script>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="ChangePassword"/>
				<tiles:put name="name" value="��������� ������ �� ���� � �������"/>
				<tiles:put name="description" value="����������� ������ ����� ��� ��������� ������ �� ���� � �������."/>
				<tiles:put name="data">
				<tr>
					<td class="Width120 LabelAll"><b>&nbsp;&nbsp;�������&nbsp;������&nbsp;������:</b></td>
					<td>
						<input type="password" id="oldPassword" name="oldPassword"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll"><b>&nbsp;&nbsp;�������&nbsp;�����&nbsp;������:</b></td>
					<td>
						<input type="password" id="newPassword" name="newPassword"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll"><b>&nbsp;&nbsp;���������&nbsp;�����&nbsp;������:</b></td>
					<td>
						<input type="password" id="confirmNewPassword" name="confirmNewPassword"/>
					</td>
				</tr>
				</tiles:put>
				<tiles:put name="buttons">
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"         value="button.changePassword"/>
						<tiles:put name="commandHelpKey"     value="button.changePassword.help"/>
						<tiles:put name="bundle"             value="paymentsBundle"/>
						<tiles:put name="validationFunction" value="checkPassword()"/>
						<tiles:put name="image" value="iconSm_save.gif"/>
						<tiles:put name="isDefault" value="true"/>
					</tiles:insert>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
