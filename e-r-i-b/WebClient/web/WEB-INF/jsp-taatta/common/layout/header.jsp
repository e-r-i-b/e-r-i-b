<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:if test="${headerGroup == 'true'}">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<table cellspacing="0" cellpadding="0" width="100%" class="headerStyle">
<tr>
   <td>&nbsp;</td>
   <td align="center" width="70%" class="headerUnder">
	  <img src="${globalImagePath}/IKfF.gif" alt="" border="0">
   </td>
   <td>&nbsp;</td>
</tr>
</table>
</c:if>
