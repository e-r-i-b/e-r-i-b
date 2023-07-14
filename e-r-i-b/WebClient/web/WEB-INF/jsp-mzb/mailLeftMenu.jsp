<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<span class="headLinksLeftMenu">������</span>

<tiles:insert definition="leftMenuLink" service="ClientMailManagement">
		<tiles:put name="enabled" value="${submenu != 'MailList'}"/>
		<tiles:put name="action"  value="/private/mail/list"/>
		<tiles:put name="text"    value="������ ���������� �����"/>
		<tiles:put name="title"   value="������ ���������� �����"/>
</tiles:insert>

<tiles:insert definition="leftMenuLink" service="ClientMailManagement">
		<tiles:put name="enabled" value="${submenu != 'SentMailList'}"/>
		<tiles:put name="action"  value="/private/mail/sentList"/>
		<tiles:put name="text"    value="������ ������������ �����"/>
		<tiles:put name="title"   value="������ ������������ �����"/>
</tiles:insert>