<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<% pageContext.getRequest().setAttribute("mode", "Users");%>
<html:form action="/persons/activeList">
<tiles:insert definition="dictionary">
<tiles:put name="submenu" type="string" value="PersonList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="personsBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
	<nobr>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel.help"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="onclick" value="window.close()"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</nobr>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.surName"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="name" value="surName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.pinEnvelopeNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="name" value="pinEnvelopeNumber"/>
		<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.firstName"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.agreementNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="name" value="agreementNumber"/>
		<tiles:put name="maxlength" value="16"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.patrName"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="name" value="patrName"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.status"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="filterSelect" style="width:100px">
				<html:option value="0">Активный</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<script type="text/javascript">
		function initTemplates()
		{
			//setInputTemplate('passportSeries', PASSPORT_SERIES_TEMPLATE);
			//setInputTemplate('passportNumber', PASSPORT_NUMBER_TEMPLATE);
		}

		initTemplates();
		function sendClientInfo(event)
		{
		    var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			for (i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var a = new Array(2);
					a['name'] = trim(r.cells[1].innerHTML);
					a['id'] = ids.item(i).value;
					window.opener.setGroupData(a);
					window.close();
					return;
				}
			}
			alert("Выберите клиента.");
		}
	</script>
</tiles:put>
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>        
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="personList"/>
        <tiles:put name="text"  value=""/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.select"/>
                <tiles:put name="commandHelpKey" value="button.select.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="onclick" value="sendClientInfo(event)"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" bundle="personsBundle" selectBean="person">
                <sl:collectionParam id="selectType" value="radio"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>
                <sl:collectionParam id="onRowDblClick"  value="sendClientInfo(event);"/>
                <c:set var="allPassCount" value="${listElement[0]}"/>
		        <c:set var="passCount" value="${listElement[1]}"/>
		        <c:set var="person" value="${listElement[2]}"/>
		        <c:set var="login" value="${listElement[3]}"/>
                <c:set var="status" value="${person.status}"/>

                <sl:collectionItem title="label.FIO">
                    ${person.surName} ${person.firstName} ${person.patrName}
                </sl:collectionItem>
                <sl:collectionItem title="ПИН-конверт" name="login" property="userId"/>
                <sl:collectionItem title="Номер договора" name="person" property="agreementNumber"/>
            </sl:collection>
        </tiles:put>

        <tiles:put name="isEmpty" value="${empty ListPersonsForm.data}"/>
        <tiles:put name="emptyMessage" value="Не найдено&nbsp;ни&nbsp;одного&nbsp;клиента,<br>
				соответствующего&nbsp;заданному&nbsp;фильтру!"/>
 </tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
