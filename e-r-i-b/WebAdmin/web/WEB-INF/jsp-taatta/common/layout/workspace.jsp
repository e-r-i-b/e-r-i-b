<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 15:53:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<table cellspacing="0" cellpadding="0" class="workspaceRegion MaxSize">
<tr>
   <td>
   <div>
   <!--данные-->
      <tiles:insert attribute="data"/>
	  <c:if test="${needSave == 'true'}">
		<script type="text/javascript">initData();</script>
	  </c:if>
   </div>
   </td>
</tr>
</table>
