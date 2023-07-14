<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

{
    <c:set var="form" value="${SearchContactForm}"/>
    "ids" : {
        <c:set var="commaSet" value="false"/>
        <c:forEach var="id" items="${form.foundIds}">
            <c:if test="${commaSet == 'true'}">
                ,
            </c:if>
            <c:set var="commaSet" value="true"/>
            "<c:out value="${id}"/>" : "1"
        </c:forEach>
    }
    ,"error": ${form.error}
}