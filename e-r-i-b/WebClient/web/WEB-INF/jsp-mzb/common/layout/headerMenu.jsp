<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<c:if test="${headerGroup == 'true'}">
	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
<table cellpadding="0" cellspacing="0" width="100%">
<tr>
	  <td width="1%" valign="top"><img src="${imagePath}/headMenuLSide.gif" alt="" width="12" height="22" border="0"></td>
	  <td class="bookmark bgBookmark" width="100%">
		<tiles:insert definition="bookmarks">
			<tiles:importAttribute name="mainmenu" ignore="true"/>
			<tiles:put name="mainmenu" value="${mainmenu}"/>
		</tiles:insert>
	  <span><html:link action="/logoff" styleClass="MenuItem" title="Выход">Выход&nbsp;<img src="${globalImagePath}/exitXP.gif" alt="" height="12px" width="12px" border="0"></html:link></span>
	  </td>
	<td width="1%" valign="top"><img src="${imagePath}/headMenuRSide.gif" alt="" width="16" height="22" border="0"></td>
</tr>
</table>
</c:if>
