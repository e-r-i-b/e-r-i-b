<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

{
    <c:set var="form" value="${EditContactStatusForm}"/>
    "remove" : "<c:out value="${form.remove}"/>"
    <c:if test="${not empty form.error}">
        ,"error": "${form.error}"
    </c:if>
}
