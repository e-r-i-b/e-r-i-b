<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<c:set var="personId" value="${empty param.employeeId or param.employeeId==0 ? '$$newId' : param.employeeId}"/>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

</td></tr>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'EmployeesEdit'}"/>
	<tiles:put name="action" value="/employees/edit.do?employeeId=${personId}"/>
	<tiles:put name="text" value="Общие сведения"/>
	<tiles:put name="title" value="Общие сведения о сотруднике"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="AssignEmployeeAccess">
	<tiles:put name="enabled" value="${submenu != 'Access'}"/>
	<tiles:put name="action" value="/employees/access.do?employeeId=${personId}"/>
	<tiles:put name="text" value="Права доступа"/>
	<tiles:put name="title" value="Права сотрудника на доступ к операциям системы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditAllowedDepartmentsOperation">
	<tiles:put name="enabled" value="${submenu != 'ListDepartments'}"/>
	<tiles:put name="action" value="/employees/allowedDepartments.do?employeeId=${personId}"/>
	<tiles:put name="text" value="Доступ к подразделениям"/>
	<tiles:put name="title" value="Подразделения, к которым имеет доступ сотрудник"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="EmployeeGroupManagement">
	<tiles:put name="enabled" value="${submenu != 'GroupList'}"/>
	<tiles:put name="action" value="/employees/groups.do?employeeId=${personId}"/>
	<tiles:put name="text" value="Группы"/>
	<tiles:put name="title" value="Работа с группами"/>
</tiles:insert>

<c:if test="${personId == '$$newId' || not phiz:isAdmin(personId)}">
    <tiles:insert definition="leftMenuInset" service="PersonalManagerInformationManagement">
        <tiles:put name="enabled" value="${submenu != 'PersonalManager'}"/>
        <tiles:put name="action" value="/employees/manager.do?employeeId=${personId}"/>
        <tiles:put name="text" value="Информация о персональном менеджере"/>
        <tiles:put name="title" value="Редактирование информации персонального менеджера"/>
    </tiles:insert>
</c:if>
