<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 18.09.2008
  Time: 19:45:22
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<%--
Информационный блок, который отображается в левом меню

	text    - название группы шаблонов
	image   - картинка группы (если вообще будут картинки)
	data    - содержание группы
--%>


<div class="tmpltGroup">
<table cellpadding="0" cellspacing="0">
<tr>
	<td class="tmpltGroupBgTopLeftCorner"></td>
	<td class="tmpltGroupBgTop"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
	<td class="tmpltGroupBgTopRightCorner"></td>
</tr>
<tr>
	<td class="tmpltGroupBgLeftCorner">&nbsp;</td>
	<td class="tmpltGroupBg" valign="top">
		<table cellspacing="3" cellpadding="3" width="100%" height="100%" class="tmpltGroupInfArea">
		<tr>
			<td class="tmpltGroupTitle">${text}</td>
		</tr>
		<tr>			
			<td valign="top" width="300px;">
				${data}
			</td>
		</tr>
		</table>
	</td>
	<td class="tmpltGroupBgRightCorner">&nbsp;</td>
</tr>
<tr>
	<td class="tmpltGroupBgBtmLeftCorner"></td>
	<td class="tmpltGroupBgBtm"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
	<td class="tmpltGroupBgBtmRightCorner"></td>
</tr>
</table>
</div>
