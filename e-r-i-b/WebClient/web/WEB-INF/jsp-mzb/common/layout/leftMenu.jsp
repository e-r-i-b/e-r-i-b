<%--
  User: Zhuravleva
  Date: 10.07.2007
  Time: 17:53:37
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
<tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>
<c:if test="${leftMenu != ''}">
	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
	<div class="leftMenu MaxSize" id="leftMenu">
	<!--данные-->
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr valign="top">
			<td height="100%">
				<div>
	<table cellspacing="0" cellpadding="0" class="widthLMenu personalHeight paddingLR">
	<tr>
		<td><%@ include file="generalInfoLM.jsp"%></td>
	</tr>
	<tr><td align="center"><img src="${imagePath}/sectionLeftMenu.gif" alt="" width="98" height="3" border="0"></td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
       <td valign="top"><br>
	       <table cellpadding="0" cellspacing="0">
		   <tr>
			  <td>
		       <input type="hidden" name="$$forceRedirect"/>
		       <tiles:insert attribute="leftMenu">
			       <tiles:importAttribute name="mainmenu" ignore="true"/>
			       <tiles:importAttribute name="submenu" ignore="true"/>
			       <tiles:put name="mainmenu" value="${mainmenu}"/>
			       <tiles:put name="submenu" value="${submenu}"/>
		       </tiles:insert>
			   </td>
		  </tr>
		  <tr><td>&nbsp;</td></tr>
		  <tr><td align="center">
		       <img src="${imagePath}/sectionLeftMenu.gif"
		            alt=""
		            width="98" height="3" border="0">
			  </td>
		  </tr>
		  <tr><td>&nbsp;</td></tr>
		  <tr>
			  <td>
		       <img src="${globalImagePath}/help2.gif" border="0" width="12" height="12">&nbsp;
		       <nobr/>
		       <span class="headLinksLeftMenu">Контекстная справка</span>
		       <table cellpadding="0" cellspacing="0" width="180px" class="linkTab backTransparent">
			       <tr>
				       <td width="10px">&nbsp;</td>
				       <td width="1%" class="insetSide"><img src="${globalImagePath}/1x1.gif" border="0"
				                                             width="4px" height="18px"></td>
				       <td class="insetBody"
				           onmouseover="javascript:this.className = 'mOverHref';"
				           onmouseout="javascript:this.className = 'insetBody';">
					       <a href="javascript:openHelp('${helpLink}');" title="Помощь"
					          style="cursor:pointer;width:93%;height:100%;"><span
							       class="menuInsertText">П</span>омощь</a>
				       </td>
			       </tr>
			       <tr>
				       <td colspan="2"></td>
				       <td align="left" valign="top"><img src="${imagePath}/insetBottom.gif"
				                                          border="0" height="4px"></td>
			       </tr>
		       </table>
			  </td>
		   </tr>
	       </table>
        </td>
	</tr>
	</table>
	</div>
	</td>
	</tr>
</table>
</div>
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
			openWindow(null,helpLink, "dialog", winpar);
		}
</script>