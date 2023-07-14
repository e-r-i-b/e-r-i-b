<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body>
<tiles:insert definition="googleTagManager"/>
<c:catch var="errorJSP">
	<c:choose>
		<c:when test="${PrintLongOfferForm.isValid}">
			<c:forEach var="current" items="${PrintLongOfferForm.HTML}">
				<c:out value="${current}" escapeXml="false"/>
				<br style="page-break-after:always;">
			</c:forEach>
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				alert("${PrintLongOfferForm.message}");
				window.close();
			</script>
		</c:otherwise>
	</c:choose>
</c:catch>
<c:if test="${not empty errorJSP}">
     ${phiz:writeLogMessage(errorJSP)}
     <script type="text/javascript">
         window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
     </script>
</c:if>
</body>
</html>