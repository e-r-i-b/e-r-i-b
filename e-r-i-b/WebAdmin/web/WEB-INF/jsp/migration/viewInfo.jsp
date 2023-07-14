<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${EditMigrationInfoForm}"/>
<c:set var="info" value="${form.migrationInfo}"/>
{
    "state": "${info.state}",
    "timeout": "${form.dataTimeout}",
    "goodCount": "${info.goodCount}",
    "badCount": "${info.badCount}",
    "endDate":
    <c:choose>
        <c:when test="${not empty info.endDate}">
            "<fmt:formatDate value="${info.endDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>"
        </c:when>
        <c:otherwise>
            "<bean:message key="form.migration.info.date.end.empty" bundle="migrationClientsBundle"/>"
        </c:otherwise>
    </c:choose>
}