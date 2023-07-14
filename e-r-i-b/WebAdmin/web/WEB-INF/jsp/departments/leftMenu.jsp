<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
	<c:set var="departmentId" value="${empty param.id ? '$$newId' : param.id}"/>

    <tiles:insert definition="leftMenuInset">
		<tiles:put name="enabled" value="${submenu != 'List'}"/>
		<tiles:put name="action"  value="/departments/list.do"/>
		<tiles:put name="text"    value="Подразделения банка"/>
		<tiles:put name="title"   value="Список подразделений банка"/>
	</tiles:insert>

    <tiles:insert definition="leftMenuInset" operation="ReplicateDepartmentsBackgroundOperation">
		<tiles:put name="enabled" value="${submenu != 'ListBackgroundTasks'}"/>
		<tiles:put name="action"  value="/departments/replication/task/list.do"/>
		<tiles:put name="text"    value="Фоновые задачи"/>
		<tiles:put name="title"   value="Список фоновых задач"/>
	</tiles:insert>
