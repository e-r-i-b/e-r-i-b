<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="form" value="${GuestEntryForm}"/>
{
    "success": ${form.successNextStage},
    <c:if test="${not empty form.redirect}">
        "redirect": "${form.redirect}",
    </c:if>
    "authToken" : "${form.operationInfo.authToken}",
    "isClient" : ${form.operationInfo.userRegistered}
}