<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 16.11.2006
  Time: 9:01:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'List'}"/>
	<tiles:put name="action"  value="/employees/list.do"/>
	<tiles:put name="text"    value="Список сотрудников"/>
	<tiles:put name="title"   value="Список сотрудников"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="EmployeeGroupManagement">
	<tiles:put name="enabled" value="${submenu != 'Groups'}"/>
	<tiles:put name="action"  value="/groups/list.do?category=A"/>
	<tiles:put name="text"    value="Группы"/>
	<tiles:put name="title"   value="Группы сотрудников"/>
</tiles:insert>

