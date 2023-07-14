<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 24.06.2009
  Time: 16:51:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
        <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>
        <script type="text/javascript">
            document.webRoot='/${phiz:loginContextName()}';
            var skinUrl = '${skinUrl}';
        </script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
        <c:if test="${phiz:isPhizIC()}">
            <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
        </c:if>
    </head>
    <body Language="JavaScript">
    <c:if test="${phiz:isPhizIC()}">
        <tiles:insert definition="googleTagManager"/>
    </c:if>
    <c:catch var="errorJSP">
		<tiles:insert attribute="data"/>
    </c:catch>
    <c:if test="${not empty errorJSP}">
        ${phiz:writeLogMessage(errorJSP)}
        <script type="text/javascript">
            window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
        </script>
    </c:if>
	</body>
</html:html>