<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:html>

	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>

	<tiles:importAttribute name="needSave" ignore="true" scope="request"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
	<body onLoad="showMessage();">
    <c:catch var="errorJSP">
	<script type="text/javascript">
	document.imgPath = "${imagePath}/";
	</script>
		<input type="hidden" name="operation"/>
        <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
            <tiles:useAttribute name="messagesBundle"/>
            <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
        </tiles:insert>
<html:form show="true">
    <table cellpadding="0" cellspacing="0" class="MaxSize personalLogoBank" border="0">
	<tr>
		<td colspan="2">
			<%@ include file="headerGroupAnonymous.jsp"%>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<%@ include file="context.jsp"%>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<%@ include file="buttonMenu.jsp"%>
		</td>
	</tr>
	<tr>
	    <td class="leftMenu widthLMenu">
			<%@ include file="leftMenu.jsp"%>
		</td>
		<td class="MaxSize" valign="top" height="100%">
			<table cellpadding="0" cellspacing="0" class="MaxSize">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/common/layout/filterData.jsp"%></td>
			</tr>
			<tr>
				<td height="100%" class="vAlign"><%@ include file="workspace.jsp"%></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<%@ include file="footer.jsp"%>
		</td>
	</tr>
	</table>
</html:form>
</body>
        </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>    
</html:html>