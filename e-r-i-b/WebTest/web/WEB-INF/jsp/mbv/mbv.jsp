<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>������������ �������������� ���� � ���</title></head>
    <body>
    <h1>������������ �������������� ���� � ���</h1>

    <html:form action="/mbv" show="true">
        <ul>
            <li>
                ����� ��������� � ���
                <html:text property="MBVMigratorUrl"
                           value="http://localhost:8888/PhizIC-test/axis-services/MBVMigrator"
                           size="100"/>
            </li>
            <li>
                <html:submit property="operation" value="getClientByPhone"/>
               ����� ��� ����������� ���, ���, ��  �������, ������������� � ���,  �� ������ ��������
            </li>
            <li>
                <html:submit property="operation" value="clientAccPh"/>
                ��������� ������ �������,������ ��������� � �������� ���������� �������������  � ���
            </li>
            <li>
                <html:submit property="operation" value="beginMigration"/>
               ����� ������ �������� ������� �� ���
            </li>
            <li>
                <html:submit property="operation" value="commitMigration"/>
                ����� ���������� ���������� �������� �������
            </li>
            <li>
                <html:submit property="operation" value="rollbackMigration"/>
                ����� ������ ������ ���������� �������� �������
            </li>
            <li>
                <html:submit property="operation" value="discByPhone"/>
                ����� ���������� �������� ��� �� ������ ��������
            </li>
        </ul>
    </html:form>
    </body>
</html:html>