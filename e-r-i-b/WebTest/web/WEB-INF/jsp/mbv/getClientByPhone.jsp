<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript">

    </script>

    <head><title>Тестирование взаимодействия ЕРИБ с МБВ</title></head>
    <body>
    <h1> Метод  проверки принадлежности номера телефона к МБВ </h1>

    <html:form action="/mbv" show="true">
        <html:hidden property="MBVMigratorUrl"/>
        <jsp:include page="byPhone.jsp" flush="false"/>
        <html:submit property="operation" value="getClientByPhone"/>
        <html:submit property="operation" value="Back"/>
    </html:form>
    </body>
</html:html>