<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>


<html>
<head>
    <title>Тест обратного ws</title>
</head>
<body>
<h3>Запрос и ответ/ошибка</h3>
<html:form action="/monitoring/fraud/ws" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <table border="1">
        <tr><td>Запрос:</td><td>${form.request}</td></tr>
        <c:if test="${not empty form.response}">
            <tr><td>Ответ: </td><td>${form.response}</td></tr>
        </c:if>
        <c:if test="${not empty form.error}">
            <tr><td>Ошибка: </td><td>${form.error}</td></tr>
        </c:if>
    </table>
</html:form>
</body>
</html>