<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:html>
	<tiles:importAttribute name="needSave" ignore="true" scope="request"/>
	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>
	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
    <head>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/KeyboardUtils.js"></script>
    </head>
	<body onLoad="clearLoadMessage();showMessage();">
    <c:catch var="errorJSP">
	<script type="text/javascript">
	document.imgPath = "${imagePath}/";
	</script>
		<input type="hidden" name="operation"/>
        <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
            <tiles:useAttribute name="messagesBundle"/>
            <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
	        <c:set var="bundleName" value="${messagesBundle}"/>
        </tiles:insert>
	<tiles:importAttribute name="headerGroup" ignore="true"/>
	<tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>

	<html:form show="true">

    <table cellpadding="0" cellspacing="0" class="MaxSize">

        <tr>
            <td rowspan="3"><img src="${globalImagePath}/1x1.gif" alt="" width="30" height="1" border="0"></td>
            <td>
                <%@ include file="headerGroup.jsp"%>
            </td>
            <td rowspan="3"><img src="${globalImagePath}/1x1.gif" alt="" width="30" height="1" border="0"></td>
        </tr>



<!-- Основная область -->
        <tr>
		<td height="100%" valign="top" colspan="2">
			<table cellpadding="0" cellspacing="0" width="100%" class="MaxSize">
				<tr>
					<!-- Левое меню -->
					<c:if test="${leftMenu != ''}">
						<td valign="top">
							<%@ include file="leftSection.jsp"%>
						</td>
					</c:if>
					<!-- Рабочая область -->
					<td id="workspace" width="100%" class="workspaceArea">
						<table cellpadding="0" cellspacing="0" class="MaxSize">
							<tr>
								<!-- Меню кнопок -->
								<td class="btnMenu">
									<%@ include file="buttonMenu.jsp"%>
								</td>
							</tr>
							<tr>
								<!--Фильтр. Может потом класс поменяем-->
								<td class="tableArea">
									<%@ include file="filter.jsp"%>
								</td>
							</tr>
							<tr>
								<td height="100%" class="tableArea">
									<%@ include file="workspace.jsp"%>
								</td>
							</tr>
						</table>
					</td>
					<td><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
				</tr>
	        </table>
		</td>
	</tr>
	<tr>
		<td>
			<%@ include file="/WEB-INF/jsp/common/layout/footer.jsp"%>
		</td>
	</tr>
</table>
</html:form>
</body>
    </c:catch>
    <c:if test="${not empty errorJSP}">
            ${phiz:processExceptionEntry(errorJSP,pageContext)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
     </c:if>
</html:html>