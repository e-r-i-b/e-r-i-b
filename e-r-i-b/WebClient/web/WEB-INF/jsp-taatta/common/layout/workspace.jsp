<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 15:53:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<div class="workspaceRegion MaxSize">
	<!--данные-->
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
			<td height="100%">
				<div>
					<tiles:insert attribute="data"/>
					<c:if test="${needSave == 'true'}">
						<script type="text/javascript">initData();</script>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</div>

