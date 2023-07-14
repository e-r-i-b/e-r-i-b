<%@ page contentType="text/html;charset=windows-1251" language="java" autoFlush="true" buffer="none" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html>
<head>
	<meta content="text/html; charset=windows-1251" http-equiv="Content-Type">
</head>
<body>
<c:catch var="errorJSP">
<h1>
<span style="font-family:MS Sans Serif; font-size:10pt;font-weight:bold">
    Протокол обмена сообщениями за период с
    <bean:write name="ViewCSAMessageLogForm" property="filter(fromDate)" format="dd.MM.yyyy"/> <bean:write name="ViewCSAMessageLogForm" property="filter(fromTime)" format="HH:mm:ss"/>
    по <bean:write name="ViewCSAMessageLogForm" property="filter(toDate)" format="dd.MM.yyyy"/> <bean:write name="ViewCSAMessageLogForm" property="filter(toTime)" format="HH:mm:ss"/>
</span>
</h1>
<!--данные-->
<script type="text/javascript">
    addClearMasks(null,
            function(event)
            {
                clearInputTemplate('filter(fromDate)', '__.__.____');
                clearInputTemplate('filter(toDate)', '__.__.____');
            });
</script>
<table border="1" cellspacing="0" cellpadding="1" width="100%" style="font-family:MS Sans Serif; font-size:8pt;"
		id="tableTitle">
	<!-- заголовок списка -->
	<tr bgcolor="#DDDDDD">
		<td style="font-weight:bold;" align="center"><bean:message key="label.record.num" bundle="logBundle"/></td>
		<td style="font-weight:bold;" align="center"><bean:message key="label.datetime" bundle="logBundle"/></td>
		<td style="font-weight:bold;" align="center"><bean:message key="label.application" bundle="logBundle"/></td>
		<td style="font-weight:bold;" align="center"><bean:message key="label.system" bundle="logBundle"/></td>
        <td style="font-weight:bold;" align="center">ТБ</td>
		<td style="font-weight:bold;" align="center"><bean:message key="label.request.tag" bundle="logBundle"/></td>
		<td style="font-weight:bold;" align="center" width="100"><bean:message key="label.request" bundle="logBundle"/></td>
		<td style="font-weight:bold;" align="center" width="100"><bean:message key="label.responce" bundle="logBundle"/></td>
	</tr>
	<!-- строки списка -->
	<c:forEach items="${ViewCSAMessageLogForm.list}" var="listElement">
		<c:set var="logEntry" value="${listElement[0]}"/>
        <tr>
			<td nowrap="true">&nbsp;<bean:write name="logEntry" property="id"/>&nbsp;</td>
			<td nowrap="true" class="ListItem"><bean:write name="logEntry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>&nbsp;</td>
			<td nowrap="true">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${logEntry.application}"/>
                </tiles:insert>
            </td>
			<td nowrap="true">
				<c:out value="${logEntry.system}"/>
			</td>
            <!-- подразделение -->
            <td nowrap="true">
                &nbsp;<bean:write name="logEntry" property="departmentCode"/>&nbsp;
            </td>
			<td>&nbsp;<bean:write name="logEntry" property="messageType"/>&nbsp;</td>
			<td nowrap="true">&nbsp;<pre><bean:write name="logEntry" property="messageRequest"/></pre>&nbsp;</td>
			<td nowrap="true">&nbsp;<pre><bean:write name="logEntry" property="messageResponse"/></pre>&nbsp;</td>
		</tr>
	</c:forEach>
</table>
</body>
        </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>
</html>
