<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute name="headerMenu" ignore="true"/>
<c:if test="${headerGroup == 'true'}">

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<table cellpadding="0" cellspacing="0" width="100%" class="bgBookmark">
<tr>
	<td width="60px;">&nbsp;</td>
	<c:if test="${headerMenu == 'true'}">
	  <td class="bookmark">
		<tiles:insert definition="bookmarks">
			<tiles:importAttribute name="mainmenu" ignore="true"/>
			<tiles:put name="mainmenu" value="${mainmenu}"/>
		</tiles:insert>
	</td>
	<td class="pageTitleMenu paperTitleMenu" width="60px;">
		<span><html:link action="/logoff" styleClass="MenuItem" title="�����">�����&nbsp;</html:link></span><span><img src="${globalImagePath}/exitXP.gif" alt="" height="12px" width="12px" border="0"></span>
	</td>
	</c:if>
</tr>
</table>
</c:if>
