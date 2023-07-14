<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/persons/empowered/list" onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="personEdit">
		<tiles:put name="submenu" type="string" value="EmpoweredPersons"/>
	<tiles:put name="pageTitle" type="string">
		<bean:message key="list.empowered-list.title" bundle="personsBundle"/>
	</tiles:put>
<tiles:put name="menu" type="string">
	<c:set var="form" value="${EmpoweredPersonsListForm}"/>
	<c:set var="person" value="${form.activePerson}"/>
    <c:set var="department" value="${phiz:getDepartmentById(form.activePerson.departmentId)}"/>
	<c:set var="isShowSaves" value="${not (person.status == 'T' && department.synchKey != null && phiz:isAgreementSignMandatory()) }"/>

	<input type="hidden" name="person" value="<%=request.getParameter("person")%>"/>
		<c:if test="${isShowSaves}">
			<tiles:insert definition="clientButton" flush="false" operation="EditEmpoweredPersonOperation">
				<tiles:put name="commandTextKey"     value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="image"   value=""/>
                <tiles:put name="action" value="/persons/empowered/edit.do?person=${person.id}" />
                <tiles:put name="viewType" value="blueBorder"/>
 			</tiles:insert>
		</c:if>		
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close"/>
			<tiles:put name="bundle"  value="commonBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="action"  value="/persons/list.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
    <script type="text/javascript">
        var addUrl = "${phiz:calculateActionURL(pageContext,'/persons/empowered/edit')}";
        function doEdit()
        {
            checkIfOneItem("selectedIds");
            if (!checkOneSelection("selectedIds", 'Выберите одного представителя'))
                return;
            var id = getRadioValue(document.getElementsByName("selectedIds"));
            window.location = addUrl + "?person=" + ${person.id} + "&id=" + id;
        }
    </script>
<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="emproweredPersonsList"/>
		<tiles:put name="text" value="Список представителей"/>
		<tiles:put name="buttons">
			<tiles:insert definition="clientButton" flush="false" operation="EditEmpoweredPersonOperation">
				<tiles:put name="commandTextKey"     value="button.edit"/>
				<tiles:put name="commandHelpKey" value="button.edit"/>
				<tiles:put name="bundle"  value="personsBundle"/>
				<tiles:put name="onclick" value="doEdit()"/>
			</tiles:insert>
			<c:if test="${isShowSaves}">
				<tiles:insert definition="commandButton" flush="false" operation="DeleteEmpoweredPersonOperation">
					<tiles:put name="commandKey"     value="button.remove"/>
					<tiles:put name="commandHelpKey" value="button.removeEmpoweredPerson.help"/>
					<tiles:put name="bundle"  value="personsBundle"/>
					<tiles:put name="confirmText" value="Вы действительно хотите удалить выбранных представителей?"/>
					<tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            return checkSelection('selectedIds', 'Выберите представителя');
                        }
					</tiles:put>
				</tiles:insert>
			</c:if>
		</tiles:put>
        <tiles:put name="grid">
	        </script>
            <sl:collection id="empowered" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                <c:set var="lineNumber" value="0"/>

                <sl:collectionItem title="№">
                    <c:out value="${lineNumber + 1}"/>
                </sl:collectionItem>
                <sl:collectionItem title="ID" property="id"/>
                <sl:collectionItem title="ФИО" property="fullName">
                    <c:if test="${empowered.status == 'A'}">
						<c:if test="${!empty empowered.login.blocks}"><img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/></c:if>
					</c:if>
                    <sl:collectionItemParam
                            id="action"
                            value="/persons/empowered/edit.do?person=${form.person}&id=${empowered.id}"
                            condition="${phiz:impliesOperation('EditEmpoweredPersonOperation','EmpoweredPersonManagement')}"
                            />
                </sl:collectionItem>
                <sl:collectionItem title="Логин" name="empowered" property="login.userId"/>
                <sl:collectionItem title="Документ">
                    <c:if test="${!empty empowered}">
                        <logic:iterate id="document" name="empowered" property="personDocuments">
                            <c:if test="${document.documentMain}">
                                <c:choose>
                                    <c:when  test="${(document.documentType == 'REGULAR_PASSPORT_RF')}">
                                        <nobr>Общегражданский паспорт РФ</nobr>
                                    </c:when>
                                    <c:when  test="${(document.documentType == 'MILITARY_IDCARD')}">
                                        <nobr>Удостоверение личности военнослужащего</nobr>
                                    </c:when>
                                    <c:when  test="${(document.documentType == 'SEAMEN_PASSPORT')}">
                                        <nobr>Паспорт моряка</nobr>
                                    </c:when>
                                    <c:when  test="${(document.documentType == 'RESIDENTIAL_PERMIT_RF')}">
                                        <nobr>Вид на жительство РФ</nobr>
                                    </c:when>
                                    <c:when  test="${(document.documentType == 'FOREIGN_PASSPORT_RF')}">
                                        <nobr>Заграничный паспорт РФ</nobr>
                                    </c:when>
                                    <c:when  test="${(document.documentType == 'OTHER')}">
                                       <nobr>
                                           <c:if test="${document.documentType!=null}">
                                               <bean:write name="document" property="documentName"/>
                                           </c:if>
                                            &nbsp;
                                       </nobr>
                                    </c:when>
                                </c:choose>
                                <c:if test="${not empty document.documentSeries}">
                                    &nbsp;<bean:write name="document" property="documentSeries"/>&nbsp;
                                </c:if>
                                <c:if test="${not empty document.documentNumber}">
                                    &nbsp;<bean:write name="document" property="documentNumber"/>&nbsp;
                                </c:if>
                            </c:if>
                        </logic:iterate>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="Статус">
                    <sl:collectionItemParam id="value" value="Активный" condition="${empowered.status=='A' && empty empowered.login.blocks}"/>
                    <sl:collectionItemParam id="value" value="Подключение" condition="${empowered.status=='T'}"/>
                    <sl:collectionItemParam id="value" value="На расторжении" condition="${empowered.status=='W'}"/>
                    <sl:collectionItemParam id="value" value="Ошибка расторжения" condition="${empowered.status=='E'}"/>
                    <sl:collectionItemParam id="value" value="Заблокирован" condition="${empowered.status=='A' && not empty empowered.login.blocks}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного представителя."/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>