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
<tiles:put name="pageTitle" type="string">
	<bean:message key="edit.title" bundle="mailBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.save"/>
		<tiles:put name="commandHelpKey" value="button.save.help"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="image" value="send.gif"/>
		<tiles:put name="isDefault" value="true"/>
		<tiles:put name="postbackNavigation" value="true"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="image" value="back.gif"/>
		<tiles:put name="action" value="/mail/list.do"/>
	</tiles:insert>
</tiles:put>

<c:set var="form" value="${EditMailForm}"/>
<tiles:put name="data" type="string">
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
			var pwin = openWindow(event, "${phiz:calculateActionURL(pageContext, '/persons/list.do?action=getClientInfo')}", "dialog2", winpar);
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

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
	    <c:set var="imagePath" value="${skinUrl}/images"/>

<table cellpadding="0" cellspacing="0" class="editNewsTab">
	<tr>
		<td align="right" valign="middle" style="padding-left:60px;"><img
				src="${imagePath}/letter.gif" border="0"/></td>
		<td colspan="3">
			<table class="MaxSize">
				<tr>
					<td align="center" class="silverBott paperTitle">
						<table height="100%" width="420px" cellspacing="0" cellpadding="0">
							<tr>
								<td class="titleHelp"><br>
									<span class="formTitle">Редактирование письма</span>
									<br/>
									Используйте данную форму редактирования письма
									клиенту.
								</td>
							</tr>
						</table>
					</td>
					<td width="100%">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
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
		<td>Дата</td>
		<td>Время</td>
	</tr>
	<tr>
		<td></td>
		<td><input type="text" name="field(date)" maxlength="10"
		           value="<bean:write name="form" property="field(date)" format="dd.MM.yyyy"/>"
		           style="width:110px;"
		           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"
				   readonly="true"/></td>
		<td><input type="text" name="field(time)" maxlength="5"
		           value="<bean:write name="form" property="field(time)" format="HH:mm"/>"
		           style="width:110px;"
				   readonly="true"/></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td> Получатель</td>
	</tr>
	<tr>
		<td></td>
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
	<tr><td colspan="2">&nbsp;</td></tr>
</table>
</tiles:put>
</tiles:insert>
</html:form>
