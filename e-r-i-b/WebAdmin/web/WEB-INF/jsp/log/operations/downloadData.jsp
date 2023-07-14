<%@ page contentType="text/html;charset=windows-1251" language="java" autoFlush="true" buffer="none" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

    
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:catch var="errorJSP">
		<h1>
            <span style="font-family:MS Sans Serif; font-size:10pt;font-weight:bold">
                Протокол работы пользователей за период с
                <bean:write name="form" property="filter(fromDate)" format="dd.MM.yyyy"/> <bean:write name="form" property="filter(fromTime)" format="HH:mm:ss"/>
                по <bean:write name="form" property="filter(toDate)" format="dd.MM.yyyy"/> <bean:write name="form" property="filter(toTime)" format="HH:mm:ss"/>
            </span>

        </h1>
        <script type="text/javascript">
            addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('filter(fromDate)','__.__.____');
                        clearInputTemplate('filter(toDate)','__.__.____');
                    });
        </script>
	<table border="1" cellspacing="0" cellpadding="1" style="font-family:MS Sans Serif; font-size:8pt;" id="tableTitle">
			<!-- заголовок списка -->
			<tr bgcolor="#DDDDDD">
				<td style="font-weight:bold;" align="center"><bean:message key="label.record.num" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.datetime" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.application" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.id" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.fullName" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.department.name" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.operation.name" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.operation.parameter" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.ip.address" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.session.id" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.message.type" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.operation.result" bundle="logBundle"/></td>
			</tr>
			<!-- строки списка -->
			<c:forEach items="${form.list}" var="listElement">
				<c:set var="logEntry" value="${listElement}"/>
				<tr>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="id"/>&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>&nbsp;</td>
					<td nowrap="true">&nbsp;
						<c:choose>
							<c:when test="${logEntry.application=='PhizIA'}">
								<bean:message key="user.log.application.admin" bundle="logBundle"/>
							</c:when>
							<c:when test="${logEntry.application=='PhizIC'}">
								<bean:message key="user.log.application.client" bundle="logBundle"/>
							</c:when>
						</c:choose>
						&nbsp;
					</td>
					<td nowrap="true"><!-- Идентификатор -->
						&nbsp;
							<c:if test="${not empty logEntry}">
								<bean:write name="logEntry" property="loginId"/>
							</c:if>
						&nbsp;
					</td>
                    <td nowrap="true"><!-- ФИО -->
						&nbsp;
                        <c:choose>
                                <c:when test="${(not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName)}">
                                      <bean:write name="logEntry" property="surName"/>&nbsp;
                                      <bean:write name="logEntry" property="firstName"/>&nbsp;
                                      <bean:write name="logEntry" property="patrName"/>
                                </c:when>
                                <c:otherwise> ${logEntry.userId}</c:otherwise>
                        </c:choose>
	                    &nbsp;
                    </td>
					<td nowrap="true">&nbsp;
	                    <c:if test="${not empty logEntry}">
	                        <bean:write name="logEntry" property="departmentName"/>
	                    </c:if>
						&nbsp;
					</td>
                    <td nowrap="true">&nbsp;<bean:write name="logEntry" property="description"/>&nbsp;</td>
                    <td nowrap="true">&nbsp;${logEntry.parameters}&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="ipAddress"/>&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="sessionId"/>&nbsp;</td>
					<td nowrap="true" class="rightTd">&nbsp;
						&nbsp;
						<c:choose>
                            <c:when test="${logEntry.type=='Security'}">
                                <bean:message key="user.log.type.security" bundle="logBundle"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="user.log.type.other" bundle="logBundle"/>
                            </c:otherwise>
                        </c:choose>
					</td>
					<td nowrap="true">
						&nbsp;
						<c:choose>
                            <c:when test="${logEntry.type=='S'}">
                                <bean:message key="user.log.message.successfully" bundle="logBundle"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="user.log.message.failure" bundle="logBundle"/>
                            </c:otherwise>
                        </c:choose>
						&nbsp;
                    </td>
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
