<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<table class="buttMenuRegion" cellpadding="0" cellspacing="0">
<tr>
   <td><img src="${imagePath}/leftLine.jpg" alt="" width="36px" height="22px" border="0"></td>
   <td class="menu paperSubMenu">
   <!--  нопки меню -->
       <div class="floatRight"><tiles:insert attribute="menu"/></div> 
   </td>
   <td><img src="${imagePath}/rightLine.jpg" alt="" width="36px" height="22px" border="0"></td>
</tr>
</table>

