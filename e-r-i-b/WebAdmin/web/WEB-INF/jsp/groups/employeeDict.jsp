<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 15.11.2006
  Time: 16:56:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/groups/employeeDictionary">
<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
	<bean:message key="employeeList.title" bundle="groupsBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function sendEmployeeData(event)
		{
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			var idString = "";
			for (var i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					idString = idString + ids.item(i).value + ";";
				}
			}

			if (idString == "")
				alert("Выберите сотрудника.");
			else
			{
				window.opener.setEmployers(idString);
				window.close();
				return;
			}
		}
	</script>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey"     value="button.return"/>
        <tiles:put name="commandHelpKey" value="button.return"/>
        <tiles:put name="bundle"  value="commonBundle"/>
        <tiles:put name="image"   value=""/>
        <tiles:put name="onclick" value="window.close();"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.sName"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="surName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.brunchNumber"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="branchCode"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.info"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="info"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.fName"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.officeNumber"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="departmentCode"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.status"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="filterSelect" style="width:100px">
				<html:option value="">Все</html:option>
				<html:option value="0">Активный</html:option>
				<html:option value="1">Заблокирован</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.pName"/>
		<tiles:put name="bundle" value="employeesBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="patrNam"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <input type="hidden" name="groupId" value=${ShowEmployeeDictionaryForm.groupId}/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="employeesGDct"/>
        <tiles:put name="buttons">
             <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.choose"/>
				<tiles:put name="commandHelpKey" value="button.choose"/>
				<tiles:put name="bundle"  value="commonBundle"/>
		        <tiles:put name="onclick" value="sendEmployeeData(event)"/>
			</tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection model="list" id="listElement" property="data" selectBean="employee">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="branchCode" value="${listElement[0]}"/>
			    <c:set var="departmentCode" value="${listElement[1]}"/>
			    <c:set var="employee" value="${listElement[2]}"/>
			    <c:set var="scheme" value="${listElement[3]}"/>
			    <c:set var="blocked" value="${!empty employee.login.blocks}"/>

                <sl:collectionItem title="ФИО">
					<c:if test="${blocked}">
                        <img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/>
                    </c:if>
                    <c:if test="${not empty employee}">
					    &nbsp;${employee.surName} ${employee.firstName} ${employee.patrName}&nbsp;
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="Подразделение">
                    <sl:collectionItemParam id="value" value="${branchCode}" condition="${not empty branchCode}"/>
                    <sl:collectionItemParam id="value" value="${departmentCode}" condition="${not empty departmentCode}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Доп. информация" name="employee" property="info"/>
                <sl:collectionItem title="Логин" name="employee" property="login.userId"/>
                <sl:collectionItem title="Схема прав доступа" name="scheme" property="name">
                    <sl:collectionItemParam id="styleClass" value="errorText" condition="${empty scheme || scheme.name=='personal'}"/>
                    <sl:collectionItemParam id="value" value="Нет&nbsp;схемы&nbsp;прав" condition="${empty scheme}"/>
                    <sl:collectionItemParam id="value" value="Индивидуальные" condition="${scheme.name=='personal'}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>

		<tiles:put name="isEmpty" value="${empty ShowEmployeeDictionaryForm.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного сотрудника, <br/>соответствующего
				заданному фильтру!"/>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>