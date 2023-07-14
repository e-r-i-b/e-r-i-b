<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute name="headerGroup" ignore="true"/>
<c:if test="${headerGroup == 'true'}">
	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
<table cellspacing="0" cellpadding="0" width="100%" class="headerStyle">
<tr>
   <td width="200px;">&nbsp;</td>
   <td align="center" style="border-bottom:2px groove #9BD2C8;">
	  <img src="${globalImagePath}/logoElSber.gif" alt="" border="0">
   </td>
   <td width="200px;" align="right">
	   <a href="http://www.softlab.ru/solutions/InterBank/">
	      <img src="${globalImagePath}/logoIBNew.gif" alt="" border="0">
	   </a>
   </td>
</tr>
</table>
</c:if>
