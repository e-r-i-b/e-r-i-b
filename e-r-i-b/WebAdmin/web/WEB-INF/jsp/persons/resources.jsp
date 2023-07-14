<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/resources" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="personEdit">
<tiles:put name="submenu" type="string" value="Resources"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="edit.resources.title" bundle="personsBundle"/>
</tiles:put>
<c:set var="form" value="${ListPersonResourcesForm}"/>
<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function callResourcesOperation(event, operation, confirm, msg)
		{
			preventDefault(event);
			if (getSelectedQnt("selectedAccounts") == 0 &&
			    getSelectedQnt("selectedCards") == 0)
			{
				return groupError(msg);
			}
			callOperation(event, operation, confirm);
		}
		function checkResourcesSelection()
		{
            if (getSelectedQnt("selectedAccounts") == 0 &&
                getSelectedQnt("selectedCards") == 0)
            {
                return false;
            }
            return true;
		}

		function addResources(event, resourceType)
		{
			if (${phiz:getDepartmentById(form.activePerson.departmentId).synchKey != null})
            {
                openWindow(event,
                        "addresources.do?person=" + getElementValue("person") + "&type=" + resourceType,
                        "Resources",
                        "resizable=1,menubar=0,toolbar=0,scrollbars=1");
            }
            else
            {
                //если офис не задан, добавить счет(карту) невозможно
                var type;
                if (resourceType == "accounts")
                    type = "счет";
                else
                    type = "карту";
                alert("Невозможно добавить " + type + ". Подразделение не обслуживается в ИКФЛ.");
            }
		}
		function doAdd(ids, type)
		{
			addHidden(ids, "selectedResources");
			setElement("resourcesType", type);
			var button = new CommandButton('button.importResources', '');
			button.click();
		}
		//проверка по номеру счета
		function checkAccountNumberIfPay(account)
		{
			if (account == null)return true;
			if (!account.match(/\d{5}810\d+/))return false;
			else return true;
		}
		//проверка по номеру счета
		function checkAccountNumber()
		{
			el = document.getElementById("newAccount");
			if (el != null)
			{
				val = el.value;
				if (val.length != 20)
				{
					alert("Номер счета должен содержать 20 цифр.");
					return false;
				}
				if (!StrCheck("0123456789", val))
				{
					alert("Номер счета может содержать только цифры.");
					return false;
				}
				if (!val.match(/\b423|\b426|\b40817|\b40820\d+/))
				{
					alert("Недопустимый номер счета клиента. В системе разрешена регистрация счетов, открытых только на балансовых счетах:423xx,426xx,40817,40820.");
					return false;
				}
			}
			return true;
		}
	</script>
  	<input type="hidden" name="person" value="<%=request.getParameter("person")%>"/>
	<input type="hidden" name="resourcesType"/>
	<tiles:insert definition="clientButton" flush="false" operation="AddAccountOperation">
		<tiles:put name="commandTextKey" value="button.add.account"/>
		<tiles:put name="commandHelpKey" value="button.add.account.help"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="onclick">javascript:addResources(event,'accounts');</tiles:put>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false" operation="AddCardOperation">
		<tiles:put name="commandTextKey" value="button.add.card"/>
		<tiles:put name="commandHelpKey" value="button.add.card.help"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="onclick">javascript:addResources(event,'cards');</tiles:put>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image"  value=""/>
        <tiles:put name="action" value="/persons/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<input type="hidden" name="clientId" value="${form.activePerson.clientId}"/>
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="titleAcc"/>
	<tiles:put name="text" value="Счета"/>
    <tiles:put name="buttons">
        <c:if test="${form.needAccount}">
            <tiles:insert definition="commandButton" flush="false" operation="EditBankrollsOperation">
                <tiles:put name="commandKey" value="button.save.payment.ability"/>
                <tiles:put name="commandHelpKey" value="button.save.payment.ability.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </c:if>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.remove"/>
            <tiles:put name="commandHelpKey" value="button.removeResource.help"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="confirmText" value="Удалить выбранные счета или карты?"/>
            <tiles:put name="validationFunction">
                checkResourcesSelection();
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="list" property="accounts">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedAccounts"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <sl:collectionItem title="Счета пользователя">
                <c:if test="${not empty listElement}">
                    <bean:write name="listElement" property="number"/>
                    <c:if test="${not empty listElement.account.currency}">
                        &nbsp;<bean:write name="listElement" property="account.currency.code"/>
                    </c:if>
                </c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem
                    title="Оплата"
                    hidden="${!(form.needAccount and phiz:impliesOperation('EditBankrollsOperation','AccountManagement'))}"
                    >
                <c:set var="currencyCode" value="${listElement.account.currency.code}"/>
                <c:choose>
                    <c:when test="${('RUB' == currencyCode)||('RUR' == currencyCode)}">
						<html:multibox property="selectedPaymentAbleAccounts" style="border:none">
							<bean:write name="listElement" property="externalId"/>
						</html:multibox>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>

    <tiles:put name="isEmpty" value="${empty form.accounts}"/>
	<tiles:put name="emptyMessage" value="У пользователя нет счетов!"/>
</tiles:insert>

<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="titleCard"/>
	<tiles:put name="text" value="Карты"/>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="list" property="cards">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedCards"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <sl:collectionItem title="Пластиковые карты пользователя">
                <c:if test="${not empty listElement}">
                    <bean:define id="resourceCard" name="listElement" property="value"/>

                    <bean:write name="resourceCard" property="number"/>&nbsp;
                    <bean:write name="resourceCard" property="type"/>
                        <c:if test="${resourceCard.main}">&nbsp;(основная)</c:if>
                        <c:if test="${!resourceCard.main}">&nbsp;(дополнит.)</c:if>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>

    <tiles:put name="isEmpty" value="${empty form.cards}"/>
	<tiles:put name="emptyMessage" value="У пользователя нет карт!"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>