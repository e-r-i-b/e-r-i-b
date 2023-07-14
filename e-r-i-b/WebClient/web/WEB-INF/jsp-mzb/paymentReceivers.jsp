<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>

    <c:when test="${standalone}">
         <c:set var="layoutDefinition" value="transferList"/>
    </c:when>
    <c:otherwise>
        <c:set var="layoutDefinition" value="dictionaryBundle"/>
    </c:otherwise>

</c:choose>

<html:form action="/private/receivers/list" onsubmit="return setAction(event);">

<tiles:insert definition="${layoutDefinition}">
<tiles:put name="submenu" type="string" value="PaymentReceivers"/>
<tiles:put name="pageTitle" type="string" value="Справочник получателей"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="menu" type="string">
<script type="text/javascript">
	document.imgPath = "${imagePath}/";
	function setAction(event)
	{
		preventDefault(event);
	<c:if test="${!standalone}">
		var frm = document.forms.item(0);
		frm.action = frm.action + "?action=getReceiverInfo";
	</c:if>
		return true;
	}
	function sendReceiverData(event)
	{
		var ids = document.getElementsByName("selectedId");
		preventDefault(event);
		for (i = 0; i < ids.length; i++)
		{
			if (ids.item(i).checked)
			{
				var r = ids.item(i).parentNode.parentNode;
				var a = new Array(7);
				a['receiverName'] = trim(r.cells[1].innerHTML);
				a['receiverINN'] = trim(r.cells[5].innerHTML);
				a['receiverKPP'] = trim(r.cells[6].innerHTML);
				a['receiverAccount'] = trim(r.cells[9].innerHTML);
				a['name'] = trim(r.cells[3].innerHTML);
				a['account'] = trim(r.cells[8].innerHTML);
				a['BIC'] = trim(r.cells[7].innerHTML);
				a['type'] = trim(r.cells[10].innerHTML);
				window.opener.setReceiverInfo(a);
				window.close();
				return true;
			}
		}
		alert("Выберите получателя.");
		return false;
	}
</script>
<c:choose>
	<c:when test="${standalone}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.add"/>
			<tiles:put name="commandHelpKey" value="button.add"/>
			<tiles:put name="bundle" value="contactBundle"/>
			<tiles:put name="image" value="add.gif"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
		</tiles:insert>
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.edit"/>
			<tiles:put name="commandHelpKey" value="button.edit.help"/>
			<tiles:put name="bundle" value="contactBundle"/>
			<tiles:put name="image" value="edit.gif"/>
			<tiles:put name="validationFunction">
				checkOneSelection('selectedIds', 'Выберите одного получателя')
			</tiles:put>
		</tiles:insert>
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.remove"/>
			<tiles:put name="commandHelpKey" value="button.remove.help"/>
			<tiles:put name="bundle" value="contactBundle"/>
			<tiles:put name="image" value="delete.gif"/>
			<tiles:put name="confirmText"
			           value="Вы действительно хотите удалить выбранных представителей?"/>
			<tiles:put name="validationFunction">
				checkSelection('selectedIds', 'Выберите получателя')
			</tiles:put>
		</tiles:insert>
	</c:when>
	<c:otherwise>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.choose"/>
			<tiles:put name="commandHelpKey" value="button.choose"/>
			<tiles:put name="image" value="select.gif"/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="onclick" value="javascript:sendReceiverData(event)"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel"/>
			<tiles:put name="image" value="back.gif"/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="onclick" value="javascript:window.close()"/>
		</tiles:insert>
	</c:otherwise>
</c:choose>

</tiles:put>
 
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.name"/>
		<tiles:put name="bundle" value="dictionaryBundle"/>
		<tiles:put name="name" value="name"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="bundle" value="dictionaryBundle"/>
		<tiles:put name="name" value="account"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.bank"/>
		<tiles:put name="bundle" value="dictionaryBundle"/>
		<tiles:put name="name" value="bank"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
<c:choose>
<c:when test="${not empty ShowPaymentReceiverListForm.receivers}">
<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
<tr style="height:20px" class="titleTable">
	<td width="20px">
		<c:choose>
			<c:when test="${standalone}">
				<input type="checkbox" name="isSelectAll" style="border:none"
				       onclick="switchSelection()"/>
			</c:when>
			<c:otherwise>&nbsp;</c:otherwise>
		</c:choose>
	</td>
	<td nowrap="true">Наименование</td>
	<td>Счет</td>
	<td>Банк</td>
	<td>Комментарий</td>
	<td style="display:none">ИНН</td>
	<td style="display:none">КПП</td>
	<td style="display:none">БИК</td>
	<td style="display:none">к/с</td>
	<td style="display:none">счет</td>
</tr>

<c:set var="lineNumber" value="0"/>
<logic:iterate id="entry" name="ShowPaymentReceiverListForm" property="receivers">
	<c:set var="receiver" value="${entry[0]}"/>
	<c:set var="bank" value="${entry[1]}"/>
	<c:set var="lineNumber" value="${lineNumber+1}"/>
	<c:choose>
		<c:when test="${standalone}">
			<tr class="ListLine
			<c:out value="${lineNumber%2}"/>
			"
			title="ИНН/КПП:&nbsp;
			<bean:write name="receiver" property="INN"/>
			/
			<bean:write name="receiver" property="KPP"/>
			, БИК:&nbsp;
			<bean:write name="receiver" property="BIC"/>
			, к/c:&nbsp;
			<bean:write name="receiver" property="correspondentAccount"/>
			"
			>
		</c:when>
		<c:otherwise>
			<tr class="ListLine
			<c:out value="${lineNumber%2}"/>
			"
			onclick="selectRow(this);"
			ondblclick="sendReceiverData();"
			title="ИНН/КПП:&nbsp;
			<bean:write name="receiver" property="INN"/>
			/
			<bean:write name="receiver" property="KPP"/>
			, БИК:&nbsp;
			<bean:write name="receiver" property="BIC"/>
			, к/c:&nbsp;
			<bean:write name="receiver" property="correspondentAccount"/>
			"
			>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${lineNumber<=ShowPaymentReceiverListForm.listLimit}">
			<td align=center class="ListItem">
				<c:choose>
					<c:when test="${standalone}">
						<html:multibox property="selectedIds" style="border:none">
							<bean:write name="receiver" property="id"/>
						</html:multibox>
					</c:when>
					<c:otherwise>
						<html:radio property="selectedId" value="${lineNumber}"
						            style="border:none"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="listItem">
				<bean:write name="receiver" property="name"/>
			</td>
			<td class="listItem">&nbsp;
				<bean:write name="receiver" property="account"/>
				&nbsp;
				<bean:write name="receiver" property="currencyCode"/>
			</td>
			<td class="listItem">
				<bean:write name="bank" property="name"/>
			</td>
			<td class="listItem">
				<bean:write name="receiver" property="description"/>
				&nbsp;
			</td>
			<td style="display:none">
				<bean:write name="receiver" property="INN"/>
			</td>
			<td style="display:none">
				<bean:write name="receiver" property="KPP"/>
			</td>
			<td style="display:none">
				<bean:write name="receiver" property="BIC"/>
			</td>
			<td style="display:none">
				<bean:write name="receiver" property="correspondentAccount"/>
			</td>
			<td style="display:none">
				<bean:write name="receiver" property="account"/>
			</td>
			<td style="display:none">
				<bean:write name="receiver" property="type"/>
			</td>

		</c:when>
		<c:otherwise>
			<td colspan="8" class="listItem">
				<b>
					В результате поиска найдено слишком много получателей.
					На экран выведены первые
					<c:out value="${ShowPaymentReceiverListForm.listLimit}"/>
					.
					Задайте более жесткие условия поиска.
				</b>
			</td>
		</c:otherwise>
	</c:choose>
	</tr>
</logic:iterate>
</table>
</c:when>
<c:otherwise>
	<table width="100%">
		<tr>
			<td align="center" class="messageTab"><br>
				Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;получателя&nbsp;платежа,<br>
				соответствующего&nbsp;заданному&nbsp;фильтру!
			</td>
		</tr>
	</table>
</c:otherwise>
</c:choose>
</tiles:put>

</tiles:insert>

</html:form>
