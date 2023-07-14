<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 17.11.2006
  Time: 13:32:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<span class="infoTitle backTransparent">Сертификаты</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="180px">
	<tr>
		<td>
<tiles:insert definition="leftMenuLink" service="CertificationOperation">
		<tiles:put name="enabled" value="${submenu != 'DemandList'}"/>
		<tiles:put name="action"  value="/private/certification/list.do"/>
		<tiles:put name="text"    value="Список запросов"/>
		<tiles:put name="title"   value="Список запросов"/>
</tiles:insert>
		</td>
	</tr>
</table>