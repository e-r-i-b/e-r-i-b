  <%--User: Zhuravleva--%>
  <%--Date: 07.05.2009--%>
  <%--Time: 16:36:29--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute name="headerGroup" ignore="true"/>
<c:if test="${headerGroup == 'true'}">
	<table cellpadding="0" cellspacing="0" width="100%" class="personalLogoBank" border="0" >
	<tr>
		<td colspan="2">
			<iframe id="iCenterLoadDiv" src="${skinUrl}/images/centerLoadIframe.html" scrolling="no" marginheight="0" frameborder="0" width="250" height="70"></iframe>
			<div id="centerLoadDiv">
				<img src="${skinUrl}/images/centerLoadDiv.gif" alt="" width="250" height="70" border="0">
			</div>
			<%@ include file="header.jsp"%>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<!-- Главное меню -->
		     <%@ include file="headerMenu.jsp"%>
		</td>
	</tr>
	</table>
</c:if>