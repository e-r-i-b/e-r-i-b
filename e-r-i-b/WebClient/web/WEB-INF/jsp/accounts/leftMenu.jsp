<%--
  User: Zhuravleva
  Date: 24.05.2006
  Time: 18:57:20
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract/list=accounts'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=accounts"/>
	<tiles:put name="text"    value="Выписка по счетам"/>
	<tiles:put name="title"   value="Получение выписки по счетам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract/list=cards'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=cards"/>
	<tiles:put name="text"    value="Выписка по картам"/>
	<tiles:put name="title"   value="Получение выписки по картам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="Abstract">
	<tiles:put name="enabled" value="${submenu != 'Abstract'}"/>
	<tiles:put name="action"  value="/private/accounts/abstract.do?list=deposits"/>
	<tiles:put name="text"    value="Выписка по вкладам"/>
	<tiles:put name="title"   value="Получение выписки по вкладам"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ViewNewsManagment">
	<tiles:put name="enabled" value="${submenu != 'newsManagment'}"/>
	<tiles:put name="action" value="/private/news/list"/>
	<tiles:put name="text" value="Новости"/>
</tiles:insert>