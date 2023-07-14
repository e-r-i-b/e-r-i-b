<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<c:if test="${headerGroup == 'true'}">

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

	<% String currentDate = String.format("%1$td.%1$tm.%1$tY", Calendar.getInstance().getTime());%>
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
			<td valign="bottom"><img src="${imagePath}/mm_leftCorner.gif" alt="" width="10" height="41" border="0"></td>
			<td class="mmDateInf"><span>Cегодня:</span><%=currentDate.substring(0,2)%>
					<script type="text/javascript">
						document.write(monthToStringOnly('<%=currentDate%>'));
					</script>
				<span>
					<%=currentDate.substring(6,10)%>
				</span>
				</td>
			<td width="100%" class="mainMenuTbl">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<!-- Вкладки -->
						<td noWrap="true" align="center">
							  &nbsp;
						</td>
					</tr>
				</table>
			</td>
			<td class="mmExitBtnBorder"><img src="${globalImagePath}/1x1.gif" alt="" width="2" height="1" border="0"></td>
			<td class="mmExitBtn">
				&nbsp;
			</td>
			<td valign="bottom"><img src="${imagePath}/mm_rightCorner.gif" alt="" width="10" height="41" border="0"></td>
			<td><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
		</tr>
	</table>
</c:if>