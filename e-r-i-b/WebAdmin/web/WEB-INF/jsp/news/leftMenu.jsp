<%--
  User: Zhuravleva
  Date: 23.04.2009
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>	

    <tiles:insert definition="leftMenuInset" service="NewsManagment">
		<tiles:put name="enabled" value="${submenu != 'List'}"/>
		<tiles:put name="action"  value="/news/list.do"/>
		<tiles:put name="text"    value="События системы"/>
		<tiles:put name="title"   value="Список событий банка"/>
	</tiles:insert>
    <tiles:insert definition="leftMenuInset" service="NewsLoginPageManagment">
		<tiles:put name="enabled" value="${submenu != 'ListNewsLoginPage'}"/>
		<tiles:put name="action"  value="/news/login/page/list.do"/>
		<tiles:put name="text"    value="События на странице входа"/>
		<tiles:put name="title"   value="Список событий для страницы входа"/>
	</tiles:insert>