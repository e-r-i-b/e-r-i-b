<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 03.11.2006
  Time: 10:45:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Create'}"/>
	<tiles:put name="action"  value="/pin/createRequest.do"/>
	<tiles:put name="text"    value="������ �� ������ ���-���������"/>
	<tiles:put name="title"   value="������ �� ������ ���-���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Upload'}"/>
	<tiles:put name="action"  value="/pin/upload.do"/>
	<tiles:put name="text"    value="�������� ������� ��������"/>
	<tiles:put name="title"   value="�������� ������� ��������"/>
</tiles:insert>