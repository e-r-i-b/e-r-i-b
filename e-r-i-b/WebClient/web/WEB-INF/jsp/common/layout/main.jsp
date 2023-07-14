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
	<body onLoad="clearLoadMessage();showMessage();changeDivHeightForNetscape('content');setOperaSettings();">
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
    <c:if test="${headerGroup == 'true'}">
	<tr>
		<td colspan="2">
			<iframe id="iCenterLoadDiv" src="${imagePath}/centerLoadIframe.html" scrolling="no" marginheight="0" frameborder="0" width="250" height="70"></iframe>
			<div id="centerLoadDiv">
				<img src="${imagePath}/centerLoadDiv.gif" alt="" width="250" height="70" border="0">
			</div>
			<%@ include file="header.jsp"%>                 
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<!-- Главное меню -->
		     <%@ include file="headerMenu.jsp"%>
		</td>
	</tr>
    </c:if>
	<tr>
<!-- Основная область -->
		<td height="100%" valign="top" colspan="2">
			<table cellpadding="0" cellspacing="0" width="100%" class="MaxSize">
				<tr>
					<td width="50px"><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
					<!-- Левое меню -->
					<c:if test="${leftMenu != ''}">
						<td valign="top" width="210px">
							<%@ include file="leftSection.jsp"%>
						</td>
					</c:if>
					<!-- Рабочая область -->
					<td class="workspaceArea">
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
									<%@ include file="filterData.jsp"%>
								</td>
							</tr>
							<tr>
								<td height="100%" class="tableArea">
									<%@ include file="workspace.jsp"%>
								</td>
							</tr>
						</table>
					</td>
					<td width="50px"><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
				</tr>
	        </table>
		</td>
	</tr>
    <c:if test="${headerGroup == 'true'}">
	<tr>
		<td colspan="2">
			<%@ include file="/WEB-INF/jsp/common/layout/footer.jsp"%>
		</td>
	</tr>
    </c:if>         
</table>
</html:form>
         </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>
</body>
</html:html>