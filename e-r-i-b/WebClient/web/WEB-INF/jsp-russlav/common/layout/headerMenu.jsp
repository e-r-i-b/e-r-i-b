<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<c:if test="${headerGroup == 'true'}">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<table cellpadding="0" cellspacing="0" width="100%" class="bgBookmark silverBott" style="height:33px;">
<tr> <c:if test="${headerMenu == 'true'}">
	  <td class="bookmark"><a>ИНФОРМАЦИЯ&nbsp;&raquo;</a></td>
	  <td class="bookmark"><a href="http://www.russlavbank.com">Новости банка</a></td>
	  <td class="bookmark"><a href="http://www.contact-sys.com">Новости Contact</a></td>
	  <td class="bookmark"><a href="#">Услуги</a></td>
	  <td class="bookmark"><a href="#">Тарифы</a></td>
	  <td class="bookmark"><a href="/PhizIC/private/deposits.do">Вклады</a></td>
	  <td class="bookmark"><a href="#">Помощь</a></td>
	  <td class="bookmark" width="60px;height:">
		<span><html:link action="/logoff" styleClass="MenuItem" title="Выход">Выход&nbsp;</html:link></span><span><img src="${globalImagePath}/exitXP.gif" alt="" height="12px" width="12px" border="0"></span>
	  </td>
	</c:if>
	<td width="1px">&nbsp;</td>
</tr>
</table>
</c:if>
