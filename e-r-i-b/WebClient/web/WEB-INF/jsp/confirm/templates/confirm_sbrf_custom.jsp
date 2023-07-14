<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>

<tiles:importAttribute/>

<input type="hidden" name="$$cryptoSignature" id="cryptoSignature"/>
<c:if test="${not empty message}">
	<div class="titleHelp"><c:out value="${message}"/></div>
</c:if>

<script src="${initParam.resourcesRealPath}/scripts/gost.js" type="text/javascript"></script>
<script type="text/javascript">
	var pk  = '${confirmRequest.privateKey}';
	var txt = '${confirmRequest.stringToSign}';
	var sig = fnHmacLikeGOST(pk, txt);
	document.getElementById("cryptoSignature").value = sig;
</script>


