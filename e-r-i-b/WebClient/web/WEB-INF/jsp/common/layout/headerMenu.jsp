<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>
	<% String currentDate = String.format("%1$td.%1$tm.%1$tY", Calendar.getInstance().getTime());%>
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
			<td valign="bottom"><img src="${imagePath}/mm_leftCorner.gif" alt="" border="0"></td>
			<td class="mmDateInf"><span>Cегодня:</span><%=currentDate.substring(0,2)%>
					<script type="text/javascript">
						document.write(monthToStringOnly('<%=currentDate%>'));
					</script>
				<span>
					<%=currentDate.substring(6,10)%>
				</span>
				</td>
			<td width="100%" class="mainMenuTbl"><center>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<!-- Вкладки -->
						<td noWrap="true" align="center">
							  <tiles:insert definition="mainMenu">
								  <tiles:importAttribute name="mainmenu" ignore="true"/>
								  <tiles:put name="mainmenu" value="${mainmenu}"/>
                                  <tiles:importAttribute name="enabledLink" ignore="true"/>
                                  <tiles:put name="enabledLink" value="${enabledLink}"/>
							  </tiles:insert>
						</td>
					</tr>
				</table></center>			
			</td>
			<td class="mmExitBtnBorder"><img src="${globalImagePath}/1x1.gif" alt="" width="2" height="1" border="0"></td>
			<td class="mmExitBtn">
				<span><html:link action="/logoff" styleClass="MenuItem" title="Выход">Выход&nbsp;<img src="${globalImagePath}/exitXP.gif" alt="" height="12px" width="12px" border="0"></html:link></span>
			</td>
			<td valign="bottom"><img src="${imagePath}/mm_rightCorner.gif" alt="" border="0"></td>
			<td><img src="${globalImagePath}/1x1.gif" alt="" width="50" height="1" border="0"></td>
		</tr>
	</table>
