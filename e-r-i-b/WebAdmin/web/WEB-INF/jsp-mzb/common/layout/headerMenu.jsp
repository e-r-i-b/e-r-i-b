<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
<table cellpadding="0" cellspacing="0" width="100%" class="bgBookmark" style="border-bottom:1px solid #c5c5c5;">
<tr>
	  <td width="142px;">&nbsp;</td>
	  <td class="bookmark">
		<tiles:insert definition="bookmarks">
			<tiles:importAttribute name="mainmenu" ignore="true"/>
			<tiles:put name="mainmenu" value="${mainmenu}"/>
		</tiles:insert>
   	</td>
	<td class="pageTitleMenu paperTitleMenu" width="60px;">
		<span><html:link action="/logoff" styleClass="MenuItem" title="Выход">Выход&nbsp;<img src="${globalImagePath}/exitXP.gif" alt="" height="12px" width="12px" border="0"></html:link></span>
	</td>
</tr>
</table>

