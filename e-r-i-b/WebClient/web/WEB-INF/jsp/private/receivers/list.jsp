<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>

    <c:when test="${standalone}">
         <c:set var="layoutDefinition" value="paymentList"/>
    </c:when>
    <c:otherwise>
        <c:set var="layoutDefinition" value="dictionaryBundle"/>
    </c:otherwise>

</c:choose>

<html:form action="/private/receivers/list" onsubmit="return setAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="${layoutDefinition}">
  <tiles:put name="mainmenu" value="ReceiversDictionary"/>
  <tiles:put name="submenu" type="string" value="PaymentReceiverList"/>
  <tiles:put name="pageTitle" type="string" value="Справочник получателей. Список получателей"/>
  <tiles:put name="pagingCommand" type="string" value="button.filter"/>
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
		<tiles:put name="name" value="bankName"/>
	</tiles:insert>
</tiles:put>
    <tiles:put name="menu" type="string">
        <c:choose>
            <c:when test="${standalone}">
                <tiles:insert definition="clientButton" flush="false" service="PaymentReceiverList">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add"/>
                    <tiles:put name="bundle" value="contactBundle"/>
                    <tiles:put name="image" value="iconSm_add.gif"/>
                    <tiles:put name="action" value="/private/receivers/edit.do?kind=P"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                   <tiles:put name="commandTextKey" value="button.cancel"/>
                   <tiles:put name="commandHelpKey" value="button.cancel"/>
                   <tiles:put name="bundle" value="dictionaryBundle"/>
                   <tiles:put name="image" value="iconSm_back.gif"/>
                   <tiles:put name="onclick" value="window.close();"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    </tiles:put>
<tiles:put name="data" type="string">
<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="ReceiverListDict"/>
			<tiles:put name="image" value="iconMid_usersDictionary.gif"/>
			<tiles:put name="text" value="Получатели"/>
			<tiles:put name="buttons">
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
                                a['receiverName'] = trim(r.cells[1].innerHTML);
                                a['receiverINN'] = trim(r.cells[5].innerHTML);
                                a['receiverKPP'] = trim(r.cells[6].innerHTML);
                                a['receiverAccount'] = trim(r.cells[9].innerHTML);
                                a['name'] = trim(r.cells[3].innerHTML);
                                a['account'] = trim(r.cells[8].innerHTML);
                                a['BIC'] = trim(r.cells[7].innerHTML);
                                if(r.cells.length>10)
                                    a['kind'] = trim(r.cells[10].innerHTML);
                                window.opener.setReceiverInfo(a);
                                window.close();
                                return true;
                            }
                        }
                        alert("Выберите получателя.");
                        return false;
                    }
                    var addUrl = "${phiz:calculateActionURL(pageContext,'/private/receivers/edit')}";
                    function doEdit()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkOneSelection("selectedIds", "Выберите получателя!!"))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = addUrl + "?id=" + id;
                    }
                    function removeReceiver(personId)
                    {
                        setElement("selectedIds", personId)
                        new CommandButton('button.remove').click();
                    }
                </script>
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
							<tiles:put name="image" value="iconSm_delete.gif"/>
							<tiles:put name="bundle" value="contactBundle"/>
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

                    <sl:collectionParam id="onRowClick" value="selectRow(this);" condition="${not standalone}"/>
                    <sl:collectionParam id="onRowDblClick" value="sendReceiverData(event);" condition="${not standalone}"/>

                    <sl:collectionItem title="Наименование" name="listElement" property="name"/>
                    <sl:collectionItem title="Счет" name="listElement" property="account"/>
                    <sl:collectionItem title="Банк" name="listElement" property="bankName"/>
                    <sl:collectionItem title="Комментарий" name="listElement" property="description"/>
                    <sl:collectionItem title="ИНН"  hidden="true" name="listElement" property="INN"/>
                    <sl:collectionItem title="КПП"  hidden="true">
                        <c:if test="${listElement.kind == 'J'}">
                            <bean:write name="listElement" property="KPP"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="БИК"  hidden="true" name="listElement" property="bankCode"/>
                    <sl:collectionItem title="к/с"  hidden="true" name="listElement" property="correspondentAccount"/>
                    <sl:collectionItem title="счет" hidden="true" name="listElement" property="account"/>
                    <!--Зачем это?! непонятно. Примочка для type -->
                    <sl:collectionItem title=""     hidden="true" name="listElement" property="kind"/>
                </sl:collection>
            </tiles:put>

            <tiles:put name="isEmpty" value="${empty ShowPaymentReceiverListForm.data}"/>
            <tiles:put name="emptyMessage" value="Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;получателя&nbsp;платежа,<br>
				соответствующего&nbsp;заданному&nbsp;фильтру!"/>
        </tiles:insert>
<html:hidden property="kind" name="form"/><html:hidden property="listKind" name="form"/>
</tiles:put>

</tiles:insert>

</html:form>
