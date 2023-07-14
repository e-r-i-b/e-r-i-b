<%--
  User: Kosyakova
  Date: 10.01.2007
  Time: 18:55:50
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layoutDefinition" value="receiversList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="dictionaryBundle"/>
	</c:otherwise>
</c:choose>
<html:form action="/private/contact/receivers/list" onsubmit="return setAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="${layoutDefinition}">
<tiles:put name="submenu" type="string" value="ReceiverContact"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="listReceivers.title" bundle="contactBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function setAction(event)
		{
			preventDefault(event);
		<c:if test="${!standalone}">
			var frm = document.forms.item(0);
			frm.action = frm.action + "?action=getReceiverInfo";
		</c:if>
		<c:if test="${standalone}">
			setEmptyAction(event);
		</c:if>
			return true;
		}
		function sendReceiverData(event)
		{
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			for (var i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var a = new Array(8);
					a['contactBankCode'] = trim(getElementTextContent(r.cells[3]));
					a['addInfo'] = trim(getElementTextContent(r.cells[4]));
					a['contactBankName'] = trim(getElementTextContent(r.cells[5]));
					a['contactBankPhone'] = trim(getElementTextContent(r.cells[6]));
					a['surName'] = trim(getElementTextContent(r.cells[7]));
					a['firstName'] = trim(getElementTextContent(r.cells[8]));
					a['patrName'] = trim(getElementTextContent(r.cells[9]));
					a['contactBankAddress'] = trim(getElementTextContent(r.cells[10]));
					a['contactBankCity'] = trim(getElementTextContent(r.cells[11]));
					a['birthDay'] = trim(getElementTextContent(r.cells[12]));
					window.opener.setReceiverInfo(a);
					window.close();
					return true;
				}
			}
			alert("Выберите получателя.");
			return false;
		}

		var addUrl = "${phiz:calculateActionURL(pageContext,'/private/contact/receivers/edit')}";
		function doEdit()
		{
            checkIfOneItem("selectedIds");
			if (!checkOneSelection("selectedIds", "Выберите получателя!!"))
				return;
			var id = getRadioValue(document.getElementsByName("selectedIds"));
			window.location = addUrl + "?id=" + id;
		}
	</script>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add"/>
		<tiles:put name="image" value="iconSm_add.gif"/>
		<tiles:put name="bundle" value="contactBundle"/>
	<tiles:put name="action" value="private/contact/receivers/edit.do?kind=C"/>
				</tiles:insert>
		<c:if test="${!standalone}">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel"/>
				<tiles:put name="bundle" value="dictionaryBundle"/>
				<tiles:put name="onclick" value="javascript:window.close()"/>
			</tiles:insert>
		</c:if>
</tiles:put>

<%--<c:if test="${!standalone}">--%>

	<tiles:put name="filter" type="string">
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.surName"/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="name" value="surName"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="name" value="firstName"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="name" value="patrName"/>
		</tiles:insert>
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.contactBankCode"/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="name" value="contactBankCode"/>
		</tiles:insert>
	</tiles:put>
<%--</c:if>--%>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="ReceiverList"/>
		<tiles:put name="image" value="iconMid_usersDictionary.gif"/>
		<tiles:put name="text" value="Получатели"/>
		<tiles:put name="buttons">
			<c:choose>
				<c:when test="${standalone}">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey" value="button.edit"/>
						<tiles:put name="commandHelpKey" value="button.edit.help"/>
						<tiles:put name="bundle" value="contactBundle"/>
						<tiles:put name="image" value="iconSm_edit.gif"/>
						<tiles:put name="onclick" value="doEdit();"/>
					</tiles:insert>
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey" value="button.remove"/>
						<tiles:put name="commandHelpKey" value="button.remove.help"/>
						<tiles:put name="bundle" value="contactBundle"/>
						<tiles:put name="image" value="iconSm_delete.gif"/>
						<tiles:put name="confirmText"
								   value="Вы действительно хотите удалить выбранных получателей?"/>
						<tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
							    return checkSelection('selectedIds', 'Выберите получателя');
                            }
						</tiles:put>
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey" value="button.choose"/>
						<tiles:put name="commandHelpKey" value="button.choose"/>
						<tiles:put name="bundle" value="dictionaryBundle"/>
						<tiles:put name="onclick" value="javascript:sendReceiverData(event)"/>
					</tiles:insert>
				</c:otherwise>
			</c:choose>
		</tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionParam id="selectType" value="checkbox" condition="${standalone}"/>
                <sl:collectionParam id="selectType" value="radio"    condition="${not standalone}"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <sl:collectionParam id="onRowClick"        value="selectRow(this, 'selectedIds');"/>
                <sl:collectionParam id="onRowDblClick"  value="sendReceiverData(event);" condition="${not standalone}"/>

                <sl:collectionItem title="№" value="${lineNumber}"/>
                <c:set var="lineNumber" value="${lineNumber + 1}"/>
                <sl:collectionItem title="ФИО">
                    <c:if test="${not empty listElement}">
                        ${listElement.surName}&nbsp;${listElement.firstName}&nbsp;${listElement.patrName}&nbsp;
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="Код банка" value="${listElement.bank.code}"/>
                <sl:collectionItem title="Доп. информация" value="${listElement.addInfo}"/>
                <sl:collectionItem hidden="true" value="${listElement.bank.name}"/>
                <sl:collectionItem hidden="true" value="${listElement.bank.phone}"/>
                <sl:collectionItem hidden="true" value="${listElement.surName}"/>
                <sl:collectionItem hidden="true" value="${listElement.firstName}"/>
                <sl:collectionItem hidden="true" value="${listElement.patrName}"/>
                <sl:collectionItem hidden="true" value="${listElement.bank.address}"/>
                <sl:collectionItem hidden="true" value="${listElement.bank.city}"/>
                <sl:collectionItem hidden="true" value="${listElement.birthDay}"/>
            </sl:collection>
        </tiles:put>

        <tiles:put name="isEmpty"      value="${empty form.data}"/>
        <tiles:put name="emptyMessage" value="У пользователя нет получателей."/>
    </tiles:insert>
    <html:hidden property="kind" name="form"/>
</tiles:put>
</tiles:insert>
</html:form>
