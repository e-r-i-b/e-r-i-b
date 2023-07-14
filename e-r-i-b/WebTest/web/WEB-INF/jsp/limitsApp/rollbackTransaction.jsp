<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 27.01.14
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
<head>
    <title>Откат транзации в приложении лимитов</title>
</head>
<body>
<H1>Лимиты: откат тразакции</H1>
<html:form action="/limits/rollback" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <h2>Транзакция:</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr>
            <td>Внешний идентификатор записи</td>
            <td><html:text property="externalId" size="40"/></td>
        </tr>
        <tr>
            <td>Внешний идентификатор платежа</td>
            <td><html:text property="documentExternalId" size="40"/></td>
        </tr>
        <tr>
            <td>Дата исполнения (YYYY-MM-DDTHH:mm:SS.fff)</td>
            <td><html:text property="operationDate" size="40"/></td>
        </tr>
    </table>

    <html:textarea property="error" rows="10" cols="50"/>

    <html:submit property="operation" value="send"/>
</html:form>
</body>
</html:html>