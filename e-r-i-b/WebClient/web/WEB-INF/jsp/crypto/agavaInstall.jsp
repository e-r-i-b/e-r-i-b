<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 12.03.2007
  Time: 12:31:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="installCrypto">
	<tiles:put name="data" type="string">
		<script language="JavaScript">
			function onLoadFn()
			{
				window.opener.AgavaInstalled();
				window.close();
			}
			addEventListenerEx(window, 'load', onLoadFn, false);
		</script>

		<p>Идет установка СКЗИ "Агава".
			Размер установочного комплекта: 700Kb.<br>
		</p>

		<OBJECT CLASSID='CLSID:92345678-6789-5647-3829-ABC893456789'
		        CODEBASE='${initParam.resourcesRealPath}/cabs/Agava.cab#version=2,0,0,0'
		        style='display:none;' id='agavaObj'>
		</OBJECT>
	</tiles:put>
</tiles:insert>
