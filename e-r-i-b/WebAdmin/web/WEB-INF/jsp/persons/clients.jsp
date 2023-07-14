<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/persons/clients/list" onsubmit="this.onsubmit = function(){ alert('Ваш запрос обрабатывается, нажмите OК для продолжения'); return false; }; preventDefault(event); return true;">

<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
	<bean:message key="list.clients.title" bundle="personsBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="action" value="/persons/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>
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
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.birthDay"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="name" value="birthDate"/>
        <tiles:put name="template" value="DATE_TEMPLATE"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="ClientsList"/>
		<tiles:put name="text" value="Клиенты"/>
        <tiles:put name="buttons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.import"/>
                <tiles:put name="commandHelpKey" value="button.import.help.list"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите клиентов для импорта');
                    }
                </tiles:put>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="grid">
			<sl:collection id="item" bundle="personsBundle" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
		        <sl:collectionItem title="label.clientId" property="displayId" width="10px"/>
                <sl:collectionItem title="label.FIO" property="fullName"/>
				<sl:collectionItem title="label.birthDay">
					<c:if test="${!empty item.birthDay}">
                        <bean:write name="item" property="birthDay.time" format="dd.MM.yyyy"/>
					</c:if>&nbsp;
				</sl:collectionItem>
				<sl:collectionItem title="label.document">
					<c:if test="${!empty item}">
						<logic:iterate id="document" name="item" property="documents">
							<bean:write name="document" property="docSeries"/>&nbsp;<bean:write name="document" property="docNumber"/>&nbsp;
						</logic:iterate>
					</c:if>&nbsp;
				</sl:collectionItem>
			</sl:collection>
		</tiles:put>

		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного клиента,<br>соответствующего заданному фильтру!"/>
	</tiles:insert>
</tiles:put>
</tiles:insert>

</html:form>
