<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</title></head>
    <body>
    <h1>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</h1>

    <html:form action="/asfilial" show="true">
        <html:textarea property="messagesText" style="height:354px;width:1150px"/>
        <br/>
        <html:submit property="operation" value="Back"/>
    </html:form>
    </body>
</html:html>