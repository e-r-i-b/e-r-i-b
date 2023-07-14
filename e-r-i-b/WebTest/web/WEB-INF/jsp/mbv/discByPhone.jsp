<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>Тестирование взаимодействия ЕРИБ с МБВ</title></head>
    <body>
    <h1>Метод отключения договора МБВ по номеру телефона</h1>

    <html:form action="/mbv" show="true">
        <html:hidden property="MBVMigratorUrl"/>
        <jsp:include page="byPhone.jsp" flush="false"/>
        <html:submit property="operation" value="discByPhone"/>
        <html:submit property="operation" value="Back"/>
    </html:form>
    </body>
</html:html>