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
				window.opener.httpFileInstalled();
				window.close();
			}
			addEventListenerEx(window, 'load', onLoadFn, false);
		</script>
		<p>
			Подождите, идет установка HTTPFile.
			Размер установочного комплекта: 40Kb.
		</p>

		<OBJECT CLASSID="CLSID:2AF0C7B1-9389-11D8-869A-0020ED529CEE"
		        CODEBASE="${initParam.resourcesRealPath}/cabs/HTTPFile.cab#version=2,3,0,0"
		        style="display:none;">
		</OBJECT>
	</tiles:put>
</tiles:insert>
