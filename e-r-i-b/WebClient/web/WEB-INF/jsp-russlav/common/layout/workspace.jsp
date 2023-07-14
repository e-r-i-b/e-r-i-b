<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 15:53:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
	<!--данные-->
<div class="workspaceRegion MaxSize">
	<tiles:insert attribute="data"/>
</div>
<c:if test="${needSave == 'true'}">
	<script type="text/javascript">
		initData();
	</script>
</c:if>


   
