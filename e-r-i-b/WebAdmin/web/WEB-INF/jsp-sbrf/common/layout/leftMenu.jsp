<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 13:15:10
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>
<tiles:importAttribute name="additionalInfoBlock" ignore="true" scope="request"/>
<c:if test="${leftMenu != ''}">
<input type="hidden" name="$$forceRedirect"/>
	<table cellspacing="0" cellpadding="1">
	<c:if test="${additionalInfoBlock != ''}">
	<tr>
    	<td>
			<tiles:insert attribute="additionalInfoBlock">
			<tiles:put name="additionalInfoBlock" value="${additionalInfoBlock}"/>
			</tiles:insert>
		</td>
	</tr>
	</c:if>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="subMTitleLeftCorner"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="1" border="0"></td>
					<td class="subMTitleBg"></td>
					<td class="subMTitleRightCorner"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="1" border="0"></td>
				</tr>
				<tr>
					<td class="subMTextTitleLeftCorner"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="1" border="0"></td>
					<td class="subMTextTitle"><bean:message key="left.menu.header" bundle="${messagesBundle}"/></td>
					<td class="subMTextTitleRightCorner"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="1" border="0"></td>
				</tr>
				<tr>
					<td class="subMBtmTitleLeftCorner"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="1" border="0"></td>
					<td class="subMBtmTitleBg"></td>
					<td class="subMBtmTitleRightCorner"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="1" border="0"></td>
				</tr>
			</table>
		</td>
	</tr>
 		   <tiles:insert attribute="leftMenu">
				<tiles:importAttribute name="mainmenu" ignore="true"/>
				<tiles:importAttribute name="submenu" ignore="true"/>
				<tiles:put name="mainmenu" value="${mainmenu}"/>
				<tiles:put name="submenu" value="${submenu}"/>
		   </tiles:insert>

    <tr>
		<td><span class="subMShadow" width="95%"><img src="${globalUrl}/images/1x1.gif" alt="" width="1" height="3" border="0"></span></td>
	</tr>
	</table>
</c:if>
<script type="text/javascript">
		function openHelp(helpLink)
		{
			var h = 150 * 4;
			var w = 400;
			var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
		             ", width=" + w +
		             ", height=" + h +
		             ", left=" + (screen.width - w) / 2 +
		             ", top=" + (screen.height - h) / 2;
			openWindow(null,helpLink, "help", winpar);
		}
</script>
