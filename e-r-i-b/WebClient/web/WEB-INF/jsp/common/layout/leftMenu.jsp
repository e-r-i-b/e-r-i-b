<%--
  User: Zhuravleva
  Date: 10.07.2007
  Time: 17:53:37
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>
<c:if test="${leftMenu != ''}">
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:importAttribute name="messagesBundle" ignore="true" scope="request"/>

	<input type="hidden" name="$$forceRedirect"/>
	<table cellpadding="0" cellspacing="1">       
		<tr>
			<td>          
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="subMTitleLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
						<td class="subMTitleBg"></td>
						<td class="subMTitleRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
					</tr>
					<tr>
						<td class="subMTextTitleLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
						<td class="subMTextTitle"><bean:message key="left.menu.header" bundle="${messagesBundle}"/></td>
						<td class="subMTextTitleRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
					</tr>
					<tr>
						<td class="subMBtmTitleLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
						<td class="subMBtmTitleBg"></td>
						<td class="subMBtmTitleRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
					</tr>
				</table>
			</td>
		</tr>
		<%-- <tr><td> прописывается внутри leftMenuInset или leftMenuInsetGroup (аналогично необходимо сделать leftMenuLink) --%>
			<tiles:insert attribute="leftMenu">
				<tiles:importAttribute name="mainmenu" ignore="true"/>
				<tiles:importAttribute name="submenu" ignore="true"/>
				<tiles:put name="mainmenu" value="${mainmenu}"/>
				<tiles:put name="submenu" value="${submenu}"/>
			</tiles:insert>
		<tr>
			<td><span class="subMShadow" width="95%"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="3" border="0"></span></td>
		</tr>
	</table>
</c:if>