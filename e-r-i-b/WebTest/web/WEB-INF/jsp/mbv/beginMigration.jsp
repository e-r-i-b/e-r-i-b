<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>������������ �������������� ���� � ���</title></head>
    <body>
    <h1>������ �� ������ �������� �� ���, ���, �� �������</h1>

    <html:form action="/mbv" show="true">
        <html:hidden property="MBVMigratorUrl"/>
        <jsp:include page="byClientIdentity.jsp" flush="false"/>
        <html:submit property="operation" value="beginMigration"/>
        <html:submit property="operation" value="Back"/>
    </html:form>
    </body>
</html:html>