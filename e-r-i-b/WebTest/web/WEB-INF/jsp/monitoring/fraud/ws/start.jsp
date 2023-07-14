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
    <h3>Заполните поле запроса</h3>
    <html:form action="/monitoring/fraud/ws" show="true">
        <html:textarea property="request" styleId="request" cols="100" rows="20"/><br/>
        <html:submit property="operation" value="send"/>
    </html:form>
</body>
</html>