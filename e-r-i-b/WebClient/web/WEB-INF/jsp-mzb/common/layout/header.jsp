<%--
  User: Zhuravleva
  Date: 10.07.2007
  Time: 11:17:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute name="headerMenu" ignore="true"/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${headerGroup == 'true'}">
	<table cellspacing="0" cellpadding="0" width="100%" class="MaxSize paddingLR headerStyle">
	<tr>		
		<td class="context"><%@ include file="context.jsp"%></td>
		<td align="right"><img src="${globalImagePath}/textLogoMZB.gif" alt="" width="308" height="17" border="0"></td>
	</tr>
	</table>	
</c:if>
