<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>������������ �������������� ���� � ���</title></head>
    <body>
    <h1>����� ���������� ���������� �������� ������� </h1>

    <html:form action="/mbv" show="true">
        <html:hidden property="MBVMigratorUrl"/>
        <table>
            <tr>
                <td>
                    ID ��������
                </td>
                <td>
                    <html:text property="migrationID" maxlength="32" size="32"/>
                </td>
            </tr>
        </table>
        <html:submit property="operation" value="commitMigration"/>
        <html:submit property="operation" value="Back"/>
    </html:form>
    </body>
</html:html>