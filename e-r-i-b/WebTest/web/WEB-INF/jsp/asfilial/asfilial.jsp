<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>������������ �������������� ���� � �� ������-��������</title></head>
    <body>
    <h1>������������ �������������� ���� � �� ������-��������</h1>

    <html:form action="/asfilial" show="true">
        <ul>
            <li>
                ����� ��������� � ����
                <html:text property="ASListenerUrl"
                           value="http://localhost:8888/ASFilialListener/axis-services/ASFilialInfoServicePort"
                           size="100"/>
            </li>
            <li>
                <html:submit property="operation" value="QueryProfile"/>
                ��������� ������� ������� � ����
            </li>
            <li>
                <html:submit property="operation" value="UpdateProfile"/>
                ���������� ������� ������� � ����
            </li>
            <li>
                <html:submit property="operation" value="ConfirmPhoneHolder"/>
                ������������� ��������� ������ ���������� ��������
            </li>
            <li>
                <html:submit property="operation" value="RequestPhoneHolder"/>
                �������� ������������ ������ ���������� ��������
            </li>
        </ul>
    </html:form>
    </body>
</html:html>