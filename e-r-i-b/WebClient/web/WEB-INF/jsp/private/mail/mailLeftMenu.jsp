<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 04.09.2007
  Time: 10:15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="ClientMailManagement">
	<tiles:put name="enabled" value="${submenu != 'MailList'}"/>
	<tiles:put name="action"  value="/private/mail/list"/>
	<tiles:put name="text"    value="Список полученных писем"/>
	<tiles:put name="title"   value="Список полученных писем"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ClientMailManagement">
	<tiles:put name="enabled" value="${submenu != 'SentMailList'}"/>
	<tiles:put name="action"  value="/private/mail/sentList"/>
	<tiles:put name="text"    value="Список отправленных писем"/>
	<tiles:put name="title"   value="Список отправленных писем"/>
</tiles:insert>