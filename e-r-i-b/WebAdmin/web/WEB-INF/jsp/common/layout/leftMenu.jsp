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
    <div id="left-section">
        <div class="workspace-box subMenu">
            <div class="subMenuRT r-top ">
                <div class="subMenuRTL r-top-left">
                    <div class="subMenuRTR r-top-right">
                        <div class="subMenuRTC r-top-center">
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="subMenuRCL r-center-left">
                <div class="subMenuRCR r-center-right">
                    <div class="subMenuRC r-content">
                        <div class="subMenuTitle">
                            <span><bean:message key="left.menu.header" bundle="${messagesBundle}"/></span>
                        </div>
                        <div class="clear"></div>
                        <tiles:insert attribute="leftMenu">
                            <tiles:importAttribute name="mainmenu" ignore="true"/>
                            <tiles:importAttribute name="submenu" ignore="true"/>
                            <tiles:put name="mainmenu" value="${mainmenu}"/>
                            <tiles:put name="submenu" value="${submenu}"/>
                        </tiles:insert>
                    </div>
                </div>
            </div>
            <div class="subMenuRBL r-bottom-left">
                <div class="subMenuRBR r-bottom-right">
                    <div class="subMenuRBC r-bottom-center"></div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<script type="text/javascript">
		function openHelp(helpLink)
		{
			var h = 150 * 4;
			var w = 760;
			var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
		             ", width=" + w +
		             ", height=" + h +
		             ", left=" + (screen.width - w) / 2 +
		             ", top=" + (screen.height - h) / 2;
			openWindow(null,helpLink, "help", winpar);
		}
</script>
