<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 25.04.2007
  Time: 19:20:55
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'TemplateList'}"/>
	<tiles:put name="action"  value="templates/templates.do"/>
	<tiles:put name="text"    value="Шаблоны платежей банка"/>
	<tiles:put name="title"   value="Шаблоны платежей банка"/>
</tiles:insert>

