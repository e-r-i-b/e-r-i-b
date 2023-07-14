<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<table class="buttMenuRegion" cellpadding="0" cellspacing="0" style="table-layout: fixed; width:100%">
<tr>
   <td class="menu paperSubMenu" valign="top">
   <!--  нопки меню -->
       <div class="floatRight" style="width:100%">
	       <table class="floatRight" cellpadding="0" cellspacing="0">
		   <tr>
			   <td><tiles:insert attribute="menu"/></td>
		   </tr>
		   </table>
	   </div>
   </td>
</tr>
<tr>
	<td align="center"><img src="${imagePath}/buttMenuBottom.gif" alt="" width="382" height="2" border="0"></td>
</tr>
</table>

