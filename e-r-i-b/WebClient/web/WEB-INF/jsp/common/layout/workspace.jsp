<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 15:53:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<div class="workspaceRegion MaxSize" id="workspaceDiv" style="position:relative;">
	<table class="MaxSize" id="bgWorkspaceRegion">
		<tr><td>&nbsp;</td></tr>
	</table>
	<iframe class="MaxSize" id="iBgWorkspaceRegion" marginheight="0" marginwidth="0" frameborder="0"></iframe>
	<!--данные-->
    <div style="position:absolute;width:100%;">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
			<td height="100%" valign="top">
				<div>        
					<tiles:insert attribute="data"/>
					<c:if test="${needSave == 'true'}">
						<script type="text/javascript">$(document).ready(initData);</script>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>