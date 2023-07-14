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
            Протокол работы системы за период с
            <bean:write name="ViewCSASystemLogForm" property="filter(fromDate)" format="dd.MM.yyyy"/> <bean:write name="ViewCSASystemLogForm" property="filter(fromTime)" format="HH:mm:ss"/>
            по <bean:write name="ViewCSASystemLogForm" property="filter(toDate)" format="dd.MM.yyyy"/> <bean:write name="ViewCSASystemLogForm" property="filter(toTime)" format="HH:mm:ss"/>
            <script type="text/javascript">
                addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('filter(fromDate)','__.__.____');
                        clearInputTemplate('filter(toDate)','__.__.____');
                    });
            </script>
        </span>

    </h1>
		<!--данные-->
	<table border="1" cellspacing="0" cellpadding="1" style="font-family:MS Sans Serif; font-size:8pt;" id="tableTitle">
			<!-- заголовок списка -->
			<tr bgcolor="#DDDDDD">
				<td style="font-weight:bold;" align="center"><bean:message key="label.record.num" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.datetime" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.message.type" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.application" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.source" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.login" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.fullName" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.ip.address" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.session.id" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.message" bundle="logBundle"/></td>
			</tr>
			<!-- строки списка -->
			<c:forEach items="${ViewCSASystemLogForm.list}" var="listElement">
				<c:set var="logEntry" value="${listElement}"/>
				<tr>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="id"/>&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>&nbsp;</td>
					<td nowrap="true">&nbsp;
						<c:set var="messageLevel" value="${logEntry.level}"/>
						<c:choose>
							<c:when test="${messageLevel=='I'}">
								<bean:message key="system.log.layer.I" bundle="logBundle"/>
							</c:when>
							<c:when test="${messageLevel=='D'}">
								<bean:message key="system.log.layer.D" bundle="logBundle"/>
							</c:when>
							<c:when test="${messageLevel=='E'}">
								<bean:message key="system.log.layer.E" bundle="logBundle"/>
							</c:when>
						</c:choose>
						&nbsp;
					</td>
					<td nowrap="true">&nbsp;
                        <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                            <tiles:put name="application" value="${logEntry.application}"/>
                        </tiles:insert>
						&nbsp;
					</td>
					<td nowrap="true"><!-- модуль -->
                        <bean:message key="user.log.source.${logEntry.source}" bundle="logBundle"/>
                    </td>
                    <td nowrap="true"><!-- Идентификатор -->
						&nbsp;
						<bean:write name="logEntry" property="login"/>
						&nbsp;
					</td>
                    <td nowrap="true"><!-- ФИО -->
						&nbsp;
	                        <bean:write name="logEntry" property="surName"/>&nbsp;
		                    <bean:write name="logEntry" property="firstName"/>&nbsp;
		                    <bean:write name="logEntry" property="patrName"/>
	                    &nbsp;
                    </td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="ipAddress"/>&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="sessionId"/>&nbsp;</td>
					<td nowrap="true">&nbsp;<pre><bean:write name="logEntry" property="message"/></pre>&nbsp;</td>
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
