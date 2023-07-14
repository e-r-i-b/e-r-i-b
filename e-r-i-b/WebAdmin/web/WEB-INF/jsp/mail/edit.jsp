<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 26.02.2007
  Time: 17:07:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/mail/edit">
<tiles:insert definition="mailEdit">
<tiles:put name="submenu" type="string" value="MailList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="edit.title" bundle="mailBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/mail/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<c:set var="form" value="${EditMailForm}"/>
<tiles:put name="data" type="string">

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script language="Javascript">

	function selectRecipientType(event)
	{
		var h = (6 + 50) * 2;
		var w = 260;
		if (h > getClientHeight() - 100) h = getClientHeight() - 100;
		var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
		             ", width=" + w +
		             ", height=" + h +
		             ", left=" + (getClientWidth() - w) / 2 +
		             ", top=" + (getClientHeight() - h) / 2;
		var pwin = openWindow(null, "${phiz:calculateActionURL(pageContext, '/mail/selectRecipientType.do')}", "dialog", winpar);

		pwin.focus();
	}
	function newTemplate(recipientTypeName, event)
	{
		setElement('field(recipientType)', recipientTypeName);
		if (recipientTypeName == "G")
		{
			openGroupsDictionary(setGroupData,"C")
		}
		if (recipientTypeName == "P")
		{
			var h = 600;
			var w = 800;
//			if (h > screen.height - 100) h = screen.height - 100;
			var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
			             ", width=" + w +
			             ", height=" + h +
			             ", left=" + (getClientWidth() - w) / 2 +
			             ", top=" + (getClientHeight() - h) / 2;
			var pwin = openWindow(event, "${phiz:calculateActionURL(pageContext, '/persons/activeList.do')}", "dialog2", winpar);
			pwin.focus();
		}
	}
	function setGroupData(groupData)
	{
		setElement('field(recipientId)', groupData["id"]);
		setElement('field(recipient)', groupData["name"]);
	}
</script>
<html:hidden property="recipientId"/>
<html:hidden property="mailId"/>
<html:hidden property="field(recipientType)"/>
<html:hidden property="field(recipientId)"/>
<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="EditLetter"/>
	<tiles:put name="name" value="Редактирование письма"/>
	<tiles:put name="description" value="Используйте данную форму редактирования письма	клиенту."/>
	<tiles:put name="data">
	<tr>
		<td></td>
		<td>Номер</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<input type="text" name="field(num)" maxlength="12"
		           value="<bean:write name="form" property="field(num)" />"
		           style="width:110px;"
				   readonly="true"/></td>
	</tr>
	<tr>
		<td></td>
		<td> Получатель</td>
	</tr>
	<tr>
		<td style="width:100px;">&nbsp;</td>
		<td colspan="2">
			<html:text property="field(recipient)" readonly="true" size="60"/>
			<input type="button" class="buttWhite" style="height:18px;width:18px;"
			       onclick="selectRecipientType();" value="..."/>
		</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
		<td>Тема</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2">
			<html:textarea property="field(subject)" cols="65" rows="2"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Текст</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2">
			<html:textarea property="field(body)" cols="65" rows="9" style="text-align:justify;"/>
		</td>
	</tr>
	<tr><td></td><td colspan="3"><html:checkbox property="field(important)" value="true" style="border:0px solid white;"/><img src="${imagePath}/iconSm_important.gif" border="0"/>Обязательно для прочтения</td></tr>
	</tiles:put>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="image" value="iconSm_send.gif"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
