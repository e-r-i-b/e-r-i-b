<%@ page contentType="text/html;charset=windows-1251" language="java" autoFlush="true" buffer="none" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<html:form action="/log/csa/operations" onsubmit="return setEmptyAction(event)">
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
                <td style="font-weight:bold;" align="center"><bean:message key="label.login" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.fullName" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.department.code" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.operation.name" bundle="logBundle"/></td>
                <td style="font-weight:bold;" align="center"><bean:message key="label.operation.parameter" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.ip.address" bundle="logBundle"/></td>
				<td style="font-weight:bold;" align="center"><bean:message key="label.operation.state" bundle="logBundle"/></td>
			</tr>
			<!-- строки списка -->
			<c:forEach items="${form.list}" var="listElement">
				<c:set var="logEntry" value="${listElement}"/>
				<tr>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="operationKey"/>&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>&nbsp;</td>
					<td nowrap="true">&nbsp;
						<tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                            <tiles:put name="application" value="${logEntry.application}"/>
                        </tiles:insert>
						&nbsp;
					</td>
					<td nowrap="true"><!-- Идентификатор -->
						&nbsp;
							<c:if test="${not empty logEntry}">
								<bean:write name="logEntry" property="login"/>
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
	                        <bean:write name="logEntry" property="departmentCode"/>
	                    </c:if>
						&nbsp;
					</td>
                    <td nowrap="true">&nbsp;<bean:write name="logEntry" property="operationKeyDescription"/>&nbsp;</td>
                    <td nowrap="true">&nbsp;${logEntry.parameters}&nbsp;</td>
					<td nowrap="true">&nbsp;<bean:write name="logEntry" property="ipAddress"/>&nbsp;</td>
					<td nowrap="true" class="rightTd">&nbsp;
						&nbsp;
						<c:out value="${logEntry.operationStateDescription}"/>
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
</html:form>