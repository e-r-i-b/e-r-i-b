<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 26.01.2009
  Time: 16:12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/persons/clients/list" onsubmit="this.onsubmit = function(){ alert('Ваш запрос обрабатывается, нажмите OК для продолжения'); return false; }; preventDefault(event); return true;">

<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
	<bean:message key="list.clients.title" bundle="personsBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.multiply.import"/>
		<tiles:put name="commandHelpKey" value="button.import.help.list"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="validationFunction" value="checkSelected();"/>
		<tiles:put name="isDefault" value="true"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>		
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="action" value="/persons/list.do"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>
</tiles:put>
<script type="text/javascript">
	function checkSelected()
	{
		return (checkSelection('selectedIds', 'Выберите клиента'));
	}
</script>
<!--фильтр-->
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.retailId"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="name" value="id"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.surName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="name" value="surName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="name" value="patrName"/>
	</tiles:insert>

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

	<script type="text/javascript">
		document.imgPath = "${imagePath}/";
		function importClient(event, noClientMessage, importOperation)
		{
			var id = getFirstSelectedId();
			preventDefault(event);
			if (id == null)
			{
				alert(noClientMessage);
				return;
			}
			var frm = window.opener.document.forms.item(0);
			frm.action = '';
			frm.elements.namedItem('operation').value = importOperation;
			frm.elements.namedItem('clientId').value = id;
			frm.submit();
			window.close();
		}
		function initTemplates()
		{
		}

		function clearMasks(event)
		{
		}
		initTemplates();
		function submit(event)

		{
			var frm = window.opener.document.forms.item(0);

			frm.action = '';

			frm.submit();

		}
	</script>
</tiles:put>

<tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
			<td height="100%">
					<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
						<!-- заголовок списока пользователей-->
						<tr class="titleTable">
							<td>
								<html:checkbox property="isSelectAll" style="border:none"
								               onclick="switchSelection()"/>
							</td>
							<td>ID</td>
							<td>
								<bean:message key="label.FIO" bundle="personsBundle"/>
							</td>
							<td>
								<bean:message key="label.birthDay" bundle="personsBundle"/>
							</td>
							<td>
								<bean:message key="label.document" bundle="personsBundle"/>
							</td>
						</tr>
						<!-- строки списка пользователей -->
						<c:set var="lineNumber" value="0"/>
						<logic:iterate id="client" name="ListClientsForm" property="clients"
						               length="listLimit+1">
							<c:set var="lineNumber" value="${lineNumber+1}"/>
							<tr class="ListLine${lineNumber%2}"
							    ondblclick="javascript:importClient(event, '<bean:message key="com.rssl.phizic.web.persons.noSelectedClients" bundle="personsBundle"/>',
                                                 '<bean:message key="button.import" bundle="personsBundle"/>');">
								<c:choose>
									<c:when test="${lineNumber<=ListClientsForm.listLimit}">
										<td align=center class="ListItem" width="20px">
											<html:multibox property="selectedIds" style="border:none">
												<bean:write name="client" property="id"/>
											</html:multibox>
										</td>
										<td align=center class="ListItem">
											<bean:write name="client" property="displayId"/>&nbsp;
										</td>
										<td class="ListItem">
											<bean:write name="client" property="fullName"/>&nbsp;
										</td>
										<td class="ListItem" align="center">
											<c:if test="${!empty client.birthDay}">
					                            <bean:write name="client" property="birthDay.time" format="dd.MM.yyyy"/>
											</c:if>&nbsp;
										</td>
										<td class="ListItem">
										<logic:iterate id="document" name="client" property="documents">
											&nbsp;
											<bean:write name="document" property="docSeries"/>
											&nbsp;
											<bean:write name="document" property="docNumber"/>
											&nbsp;
										</logic:iterate>&nbsp;
										</td>
									</c:when>
									<c:otherwise>
										<td colspan="5" class="listItem">
											<b>
												В результате поиска найдено слишком много клиентов.
												На экран выведены первые <c:out value="${ListClientsForm.listLimit}"/>.
												Задайте более жесткие условия поиска.
											</b>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</logic:iterate>
					</table>
					<c:if test="${lineNumber==0}">
						<table id="messageTab" width="100%">
							<tr>
								<td width="50%">&nbsp;</td>
								<td align="center" class="messageTab"><br>
									Не найдено&nbsp;ни&nbsp;одного&nbsp;клиента,<br>
									соответствующего&nbsp;заданному&nbsp;фильтру!
								</td>
								<td width="50%">&nbsp;</td>
							</tr>
						</table>
						<script>hideTitle();</script>
					</c:if>
			</td>
		</tr>
	</table>
</tiles:put>

</tiles:insert>

</html:form>