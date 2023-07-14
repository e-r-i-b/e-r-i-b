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
	<tiles:importAttribute name="headerGroup" ignore="true"/>
	<tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
	<body onLoad="showMessage(); setOperaSettings(); changeDivHeightForNetscape('accountDiv');">
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

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form show="true">
	<table cellpadding="0" cellspacing="0" class="MaxSize" style="margin:0px">
	<tr><c:if test="${leftMenu != ''}">
		<td valign="top" width="210px">
			<%--Ћишн€€ таблица, но иначе меню "схлопываетс€" в опере и firefox--%>
				<table cellpadding="0" cellspacing="0" style="height:100%" width="210px">
					<tr>
						<td>
							<%@ include file="leftMenu.jsp"%>
						</td>
					</tr>
				</table>
		</td>
		</c:if>
		<td width="1%" valign="middle"><img src="${imagePath}/rightSideLeftMenu.gif" alt="" width="2" height="383" border="0"></td>
		<td valign="top">
			<table cellpadding="0" cellspacing="0" class="MaxSize">
			<tr>
				<td valign="top">
					<%@ include file="header.jsp"%>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<%@ include file="headerMenu.jsp"%>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<%@ include file="buttonMenu.jsp"%>
				</td>
			</tr>
			<tr>
				<td valign="top"><%@ include file="/WEB-INF/jsp/common/layout/filter.jsp"%></td>
			</tr>
			<tr>
				<td valign="top" style="height:100%;"><%@ include file="workspace.jsp"%></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="3" valign="bottom">
			<%@ include file="/WEB-INF/jsp/common/layout/footer.jsp"%>
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