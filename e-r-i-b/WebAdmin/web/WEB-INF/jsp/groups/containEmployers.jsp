<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 15.11.2006
  Time: 15:36:11
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
<script>
	function CallDictionary()
	{
		var path = "${phiz:calculateActionURL(pageContext, '/groups/employeeDictionary.do')}?groupId=${GroupsContainEmployersForm.id}";
		window.open(path, "", "resizable=1,menubar=1,toolbar=1,scrollbars=1");
	}
</script>
<html:form action="/groups/containEmployers" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="employersGroup">
<tiles:put name="submenu" type="string" value="Content"/>
<tiles:put name="needSave" type="string" value="false"/>
<tiles:put name="pageTitle" type="string">
	Сотрудники
</tiles:put>
<tiles:put name="menu" type="string">
	
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add"/>
		<tiles:put name="bundle" value="groupsBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="onclick" value="CallDictionary()"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
	<nobr>
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
		<tiles:put name="name" value="brunchCode"/>
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
		<tiles:put name="name" value="patrName"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

	<script type="text/javascript">
		function setEmployers(logins)
		{
			var el = document.getElementById("employeeLogins");
			el.value = logins;
			var button = new CommandButton('button.add','');
			button.click();
		}
	</script>
	<input type="hidden" name="category" value="${GroupsContainEmployersForm.category}"/>
	<input type="hidden" id="employeeLogins" name="employeeLogins" value=""/>

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="groups"/>
        <tiles:put name="buttons">
             <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                <tiles:put name="bundle" value="employeesBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите одного сотрудника');
                    }
                </tiles:put>
                <tiles:put name="confirmText" value="Удалить выбраных сотрудников?"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" selectBean="employee">
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
                    &nbsp;
					<c:if test="${not empty branchCode}">
						<c:out value="branchCode"/>
					</c:if>
					<c:if test="${not empty departmentCode}">/
						<c:out value="departmentCode"/>
					</c:if>
                    &nbsp;
                </sl:collectionItem>
                <sl:collectionItem title="Доп. информация" name="employee" property="info"/>
                <sl:collectionItem title="Логин" name="employee" property="login.userId"/>
                <sl:collectionItem title="Схема прав доступа">
                    <%-- Сделать нормальное определение персональной схемы доступа --%>
					<c:choose>
						<c:when test="${empty scheme}">
							<span class="errorText">Нет&nbsp;схемы&nbsp;прав</span>
						</c:when>
						<c:when test="${scheme.name=='personal'}">
						    <span class="errorText">Индивидуальные&nbsp;права</span>
						</c:when>
						<c:otherwise>
							<nobr><bean:write name="scheme" property="name"/></nobr>
						</c:otherwise>
					</c:choose>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>

		<tiles:put name="isEmpty" value="${empty GroupsContainEmployersForm.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного сотрудника,<br>соответствующего данному фильтру."/>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>