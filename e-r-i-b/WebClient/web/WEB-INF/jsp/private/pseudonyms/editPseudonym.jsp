<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>

<html:form action="/private/smsbanking/editPseudonym" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="dictionary">
<tiles:put name="pageTitle" type="string" value="Редактирование псевдонима"/>
	<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function sendPseudonymData(event)
		{
			var name = document.getElementById("pseudonym").value;
			if (name.length < 3)
			{
				alert("Длина псевдонима должна быть от 3 до 10 символов");
				return;
			}
			preventDefault(event);

			window.opener.setPseudonymInfo(${EditPseudonymForm.pseudonymId}, name);
			window.close();
			return true;
		}
	</script>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="image"          value="iconSm_save.gif"/>
			<tiles:put name="bundle"         value="loansBundle"/>
			<tiles:put name="onclick"        value="javascript:sendPseudonymData(event)"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel.help"/>
			<tiles:put name="image" value="back.gif"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="onclick" value="javascript:window.close()"/>
		</tiles:insert>
	</tiles:put>

	<%-- данные --%>
	<tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
			<td nowrap="true" class="Label">
			&nbsp;Псевдоним&nbsp;
			</td>
			<td height="100%">
				<input size="20" maxlength="10" id="pseudonym" name="pseudonym" value="${EditPseudonymForm.pseudonym}"/>
			</td>
			<td width="100%">&nbsp;</td>
		</tr>
	</table>
	</tiles:put>

</tiles:insert>

</html:form>