<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/persons/receivers/list" onsubmit="return setEmptyAction(event);">

	<tiles:insert definition="personEdit">
		<tiles:put name="submenu" type="string" value="PaymentReceiversI"/>

		<tiles:put name="pageTitle" type="string" value="Список получателей физ.лиц."/>

		<tiles:put name="menu" type="string">
			<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
			<c:set var="person" value="${form.activePerson}"/>
			<c:set var="isShowSaves" value="${not (person.status == 'T' && phiz:isAgreementSignMandatory()) }"/>

			<input type="hidden" name="person" value="<%=request.getParameter("person")%>"/>
			<c:if test="${isShowSaves}">
				<tiles:insert definition="clientButton" flush="false" operation="EditPaymentReceiverOperationAdmin">
					<tiles:put name="commandTextKey" value="button.add"/>
					<tiles:put name="commandHelpKey" value="button.add"/>
					<tiles:put name="bundle"  value="personsBundle"/>
					<tiles:put name="image"   value=""/>
					<tiles:put name="action"  value="/persons/receivers/edit.do?person=${param.person}&kind=P"/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/persons/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/persons/receivers/edit')}?person="+${param.person};
				function doEdit()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", "Выберите получателя!!"))
						return;
					var id = getRadioValue(document.getElementsByName("selectedIds"));
					window.location = addUrl + "&id=" + id;
				}
			</script>
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="receiverList"/>
                    <tiles:put name="text" value=""/>
                    <tiles:put name="buttons">
                        <tiles:insert definition="clientButton" flush="false" operation="EditPaymentReceiverOperationAdmin">
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit"/>
                            <tiles:put name="bundle" value="personsBundle"/>
                            <tiles:put name="onclick" value="doEdit();"/>
                        </tiles:insert>
                        <c:if test="${isShowSaves}">
                        <tiles:insert definition="commandButton" flush="false" operation="RemovePaymentReceiverOperation">
                            <tiles:put name="commandKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove.receiver.help"/>
                            <tiles:put name="bundle" value="personsBundle"/>
                            <tiles:put name="confirmText" value="Вы действительно хотите удалить выбранных получателей?"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection('selectedIds', 'Выберите получателей');
                                }
                            </tiles:put>
                        </tiles:insert>
                        </c:if>
                    </tiles:put>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data" bundle="personsBundle">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>

                            <sl:collectionItem title="label.number" value="${lineNumber + 1}"/>
                            <sl:collectionItem title="label.receiver.alias" value="${listElement.alias}"/>
                            <sl:collectionItem title="label.receiver.fio" value="${listElement.name}">
                                <sl:collectionItemParam
                                        id="action"
                                        value="/persons/receivers/edit.do?person=${param.person}&kind=P&id=${listElement.id}"
                                        condition="${phiz:impliesOperation('EditPaymentReceiverOperationAdmin', 'PersonManagement')}"
                                        />
                            </sl:collectionItem>
                            <sl:collectionItem title="label.receiver.account"    value="${listElement.account}"/>
                            <sl:collectionItem title="label.receiver.bank.title" value="${listElement.bankName}"/>
                            <sl:collectionItem title="label.receiver.bank.bic"   value="${listElement.bankCode}"/>
                            <sl:collectionItem title="label.receiver.bank.corAccount" value="${listElement.correspondentAccount}"/>
                        </sl:collection>
                        <tiles:put name="image" value="iconMid_usersDictionary.gif"/>
			            <tiles:put name="text" value="Получатели"/>
                    </tiles:put>

                    <tiles:put name="isEmpty"      value="${empty form.data}"/>
                    <tiles:put name="emptyMessage" value="Не найдено&nbsp;ни&nbsp;одного&nbsp;получателя.<br>"/>

			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>