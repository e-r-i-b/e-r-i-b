<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:html>
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
	<script type="text/javascript">
		document.imgPath = "${imagePath}/";
	</script>

	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>

	<tiles:importAttribute name="needSave" ignore="true" scope="request"/>

	<body onLoad="showMessage();">
        <c:catch var="errorJSP">
		<input type="hidden" name="operation"/>
        <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
            <tiles:useAttribute name="messagesBundle"/>
            <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
	        <c:set var="bundleName" value="${messagesBundle}"/>
        </tiles:insert>

    <table cellpadding="0" cellspacing="0" class="MaxSize">
	<tr>
		<td colspan="3">
			<%@ include file="header.jsp"%>
		</td>
	</tr>
	<tr>
		<td colspan="3">
		     <%@ include file="headerMenu.jsp"%>
		</td>
	</tr>
	<tr>
	    <td class="leftMenu widthLMenu">
		    <c:if test="${headerMenu == 'true'}">
				<%@ include file="/WEB-INF/jsp/common/layout/leftMenu.jsp"%>
		    </c:if>
		</td>
		<td class="MaxSize" valign="top">
			<table cellpadding="0" cellspacing="0" class="MaxSize" border="0">
			<tr>
				<td>
					 <%@ include file="context.jsp"%>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<%@ include file="buttonMenu.jsp"%>
				</td>
			</tr>
			<tr>
				<td><%@ include file="/WEB-INF/jsp/common/layout/filter.jsp"%></td>
			</tr>
			<tr>
				<td valign="top" style="height:100%;"><%@ include file="workspace.jsp"%></td>
			</tr>
			</table>
		</td>
		<td valign="top">
			<c:if test="${leftMenu != ''}">
				<%@ include file="rightMenu.jsp"%>
			</c:if>
		</td>
	</tr>
	</table>
</body>
        </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>    
</html:html>