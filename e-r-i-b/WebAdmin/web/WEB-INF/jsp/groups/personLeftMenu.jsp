<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 10.11.2006
  Time: 16:12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<!--Заголовок раздела-->
<c:set var="groupId" value="${empty param.id ? '$$newId' : param.id}"/>
<c:set var="category" value="${empty param.category ? '' : param.category}"/>


<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'General'}"/>
	<tiles:put name="action"  value="/groups/edit.do?id=${groupId}&category=${category}"/>
	<tiles:put name="text"    value="Общие"/>
	<tiles:put name="title"   value="Общие сведения о группе"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Content'}"/>
	<tiles:put name="action"  value="/groups/containPerson.do?id=${groupId}&category=${category}"/>
	<tiles:put name="text"    value="Список клиентов"/>
	<tiles:put name="title"   value="Список клиентов"/>
</tiles:insert>