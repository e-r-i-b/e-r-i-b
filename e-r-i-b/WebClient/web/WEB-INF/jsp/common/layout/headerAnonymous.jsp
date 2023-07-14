<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:if test="${headerGroup == 'true'}">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
	<script type="text/javascript">
		function openHelp(helpLink)
		{
			var h = 150 * 4;
			var w = 400;
			var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
		             ", width=" + w +
		             ", height=" + h +
		             ", left=" + (screen.width - w) / 2 +
		             ", top=" + (screen.height - h) / 2;
			openWindow(null,helpLink, "help", winpar);
		}
	</script>

	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<!-- Логотип -->
			<td width="30%" class="hdrLogo">
				<img src="${globalImagePath}/logoIB.gif" alt="" width="77" height="66" border="0">
			</td>
			<!-- Название системы -->
			<td class="hdrSystemTitle">Интернет-клиент<br><span>для физических лиц</span></td>
			<!-- Статическая информация -->
			<td width="30%" align="right">
				<br>
				<table cellpadding="0" cellspacing="0" class="statInfTbl" border="0">
				<tr>
					<td class="whiteBg"><img src="${imagePath}/statInf_topLeftCorner.gif" alt="" width="21" height="7" border="0"></td>
					<td class="statInfTopBg"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
				</tr>
				<tr>
					<td><img src="${imagePath}/statInf_middleLeftShadow.gif" alt="" width="21" height="11" border="0"></td>
					<td class="statInfMiddleBg"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
				</tr>
				<tr>
					<td class="statInfHelpArea">&nbsp;</td>
					<td class="statInfHelpArea">
						<span><img src="${imagePath}/iconMid_help.gif" alt="" width="15" height="15" border="0"></span>
						<span><a href="" onclick="openHelp('${helpLink}'); return false;" title="Помошь">Помощь</a></span>
					</td>
				</tr>
				<tr>
					<td class="whiteBg"><img src="${imagePath}/statInf_btmLeftCorner.gif" alt="" width="21" height="7" border="0"></td>
					<td class="statInfBtmBg"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
				</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if>
