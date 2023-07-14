<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 13:15:10
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
<tiles:importAttribute name="leftMenu" ignore="true" scope="request"/>    
<c:if test="${leftMenu != ''}">
<div class="Data divLMenu" id="leftMenu" style="height:100%;overflow:auto;">
	<table cellspacing="0" cellpadding="0" class="widthLMenu personalHeight">
		<tr>
            <td valign="top">
                <div>
                    <input type="hidden" name="$$forceRedirect"/>
                    <tiles:insert attribute="leftMenu">
                        <tiles:importAttribute name="mainmenu" ignore="true"/>
                        <tiles:importAttribute name="submenu" ignore="true"/>
                        <tiles:put name="mainmenu" value="${mainmenu}"/>
                        <tiles:put name="submenu" value="${submenu}"/>
                    </tiles:insert>
                    <br>
                    <br>
                    <table width="93%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="infoTitle insetBody"
                                onmouseover="javascript:this.className = 'mOverHref';"
                                onmouseout="javascript:this.className = 'insetBody';">
                                <a href="javascript:openHelp('${helpLink}');" title="Помощь"
                                style="cursor:pointer;width:93%;height:100%;">
                                <img src="/PhizIC-res/images/help2.gif" border="0" width="12" height="12">&nbsp;
                                <span class="menuInsertText">К</span>онтекстная справка</a>
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



