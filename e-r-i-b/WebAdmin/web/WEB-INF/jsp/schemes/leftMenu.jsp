<%--
  User: Zhuravleva
  Date: 23.04.2009
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'List'}"/>
    <tiles:put name="action"  value="/schemes/list.do"/>
    <tiles:put name="text"    value="Схемы прав"/>
	<tiles:put name="title"   value="Создание схем прав для назначения пользователю"/>
</tiles:insert>