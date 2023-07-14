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
   <td align="center" width="70%" class="headerUnder" style="background:url('${imagePath}/headerBG.gif');background-repeat:repeat-x;">
      <%--<span class="floatLeft"><img src="${imagePath}/LogoBankHeaderLeft.gif" alt="" border="0"></span>--%>
      <%--<span class="floatRight"><img src="${imagePath}/LogoBankHeaderRight.gif" alt="" border="0"></span>--%>
	  <img src="${globalImagePath}/LogoBank.gif" alt="" border="0">
   </td>
</tr>
</table>
</c:if>
