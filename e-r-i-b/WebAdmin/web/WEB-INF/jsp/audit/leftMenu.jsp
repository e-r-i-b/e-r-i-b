<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<!--Заголовок раздела-->
<tiles:insert definition="leftMenuInset" service="ViewPaymentList">
	<tiles:put name="enabled" value="${submenu != 'BusinessDocumentList'}"/>
	<tiles:put name="action"  value="/audit/businessDocument.do?status=admin"/>	
	<tiles:put name="text"    value="Журнал платежей и заявок"/>
	<tiles:put name="title"   value="Журнал платежей и заявок"/>
</tiles:insert>

