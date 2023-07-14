<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

<table class="buttMenuRegion" cellpadding="0" cellspacing="0">
<tr>
   <td class="menu paperSubMenu">
   <!-- Кнопки меню -->
       <div class="floatRight" style="width:100%;">
	       <table class="floatRight" cellpadding="0" cellspacing="0" height="20px">
		   <tr>
			   <td><tiles:insert attribute="menu"/></td>
			   <td class="imgButtonMenu" style="padding-left:10px;"><a href="mailto:info@sbercred.ru"><img src="${imagePath}/mail.gif" alt="" width="17" height="11" border="0" title="Отправить e-mail"></a></td>
		       <td class="imgButtonMenu"><a href="/PhizIC/private/accounts.do"><img src="${imagePath}/home.gif" alt="" width="16" height="12" border="0"title="Главная страница"></a></td>
		   </tr>
		   </table>
	   </div>
   </td>
</tr>
</table>