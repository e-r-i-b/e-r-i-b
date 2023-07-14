<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<tiles:importAttribute name="headerGroup" ignore="true"/>
<c:if test="${headerGroup == 'true'}">
<table cellpadding="0" cellspacing="0" width="100%" class="personalLogoBank">
<tr>
	<td colspan="2">
		<%@ include file="header.jsp"%>
	</td>
</tr>
<tr>
	<td colspan="2">
	     <%@ include file="headerMenu.jsp"%>
	</td>
</tr>
</table>
</c:if>