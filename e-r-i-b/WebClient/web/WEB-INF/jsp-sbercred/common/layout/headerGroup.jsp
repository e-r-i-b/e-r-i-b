<%--
  User: Zhuravleva
  Date: 05.09.2007
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute name="headerGroup" ignore="true"/>
<c:if test="${headerGroup == 'true'}">

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

<table cellpadding="0" cellspacing="0" width="100%" class="headRight">
<tr>
	<td class="headLeft"><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
	<td class="generalHeadMenu paddingTB"><img src="${globalImagePath}/logoBank.gif" alt="" width="285" height="42" border="0">
		<tiles:insert definition="bookmarks">
			<tiles:importAttribute name="mainmenu" ignore="true"/>
			<tiles:put name="mainmenu" value="${mainmenu}"/>
		</tiles:insert>
		<span><html:link action="/logoff" styleClass="MenuItem" title="Выход">Выход&nbsp;</html:link></span>
	</td>
	<td class="headRight">&nbsp;</td>
</tr>
</table>
</c:if>
