<%--
  User: Zhuravleva
  Date: 21.11.2006
  Time: 11:03:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<c:if test="${headerGroup == 'true'}">
<table class="MaxSize context" cellpadding="0" cellspacing="0" border="0">
<tr>
   <td>
        <span class="contextLink"><nobr><tiles:getAsString name="pageTitle"/>&nbsp;</nobr></span> 
	    <!--span оставить только! до момента реализации контекста в виде ссылок!!! -->
   </td>
</tr>
</table>
</c:if>