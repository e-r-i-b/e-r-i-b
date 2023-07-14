<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 16.11.2007
  Time: 18:53:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/errors/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="serviceMain">
	<tiles:put name="submenu" type="string" value="ErrorMessages"/>
	<tiles:put name="pageTitle" type="string">
        <bean:message key="list.title" bundle="errorsBundle"/>
    </tiles:put>

<!--меню-->
<tiles:put name="menu" type="string">
	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/errors/edit')}";

		function editMessage()
		{
			if(!checkOneSelection("selectedIds", "Выберите одно сообщение"))
				return;

			var id = getRadioValue(document.getElementsByName("selectedIds"))

			window.location = addUrl + "?id=" + id;
		}
	</script>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add.help"/>
		<tiles:put name="bundle"         value="errorsBundle"/>
		<tiles:put name="action"        value="/errors/edit.do"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.edit"/>
		<tiles:put name="commandHelpKey" value="button.edit.help"/>
		<tiles:put name="bundle"         value="errorsBundle"/>
		<tiles:put name="onclick" value="editMessage()"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>

	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey"     value="button.remove"/>
		<tiles:put name="commandHelpKey" value="button.remove.help"/>
		<tiles:put name="bundle"         value="errorsBundle"/>
		<tiles:put name="validationFunction">
			checkSelection("selectedIds", "Выберите сообщения");
		</tiles:put>
		<tiles:put name="confirmText"    value="Удалить выбранные сообщения?"/>
        <tiles:put name="viewType" value="buttonGrey"/>
	</tiles:insert>

</tiles:put>

<!--фильтр-->
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.regularExpression"/>
			<tiles:put name="bundle" value="errorsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="regExp"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.typeError"/>
			<tiles:put name="bundle" value="errorsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(errorType)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="1">Клиентская</html:option>
					<html:option value="2">Системная</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.system"/>
			<tiles:put name="bundle" value="errorsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(system)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="1">Retail</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
</tiles:put>

<!-- данные -->
<tiles:put name="data" type="string">
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="errorList"/>
        <tiles:put name="text"  value=""/>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <sl:collectionItem title="Регулярное выражение" name="listElement" property="regExp">
                    <sl:collectionItemParam id="action" value="/errors/edit.do?id=${errorMessage.id}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Система">
                    <sl:collectionItemParam id="value" value="Retail" condition="${listElement.system=='Retail'}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Тип ошибки">
                    <sl:collectionItemParam id="value" value="Клиентская" condition="${listElement.errorType=='Client'}"/>
                    <sl:collectionItemParam id="value" value="Системная"  condition="${listElement.errorType=='Validation'}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Сообщение в ИКФЛ" name="listElement" property="message"/>
            </sl:collection>
        </tiles:put>

        <tiles:put name="isEmpty" value="${empty ListErrorMessageForm.data}"/>
        <tiles:put name="emptyMessage" value="Не найдено ни одного сообщения, <br/>соответствующего
			заданному фильтру!"/>
    </tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>