<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
<head>
    <title>����: ����������� ��� �������� ���������</title>
</head>

<body>
<h1>����: ����������� ��� �������� ���������</h1>

<html:form action="/ermb/update/updatePhones" show="true">

    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td colspan="2">���������� � ������������</td></tr>
        <tr><td>�������:</td><td><html:text property="surName" size="20"/></td></tr>
        <tr><td>���:</td><td><html:text property="firstName" size="100"/></td></tr>
        <tr><td>��������:</td><td><html:text property="patrName" size="20"/></td></tr>
        <tr><td>���� �������� (��.��.���� ��:��:��):</td><td><html:text property="birthDate" size="20"/></td></tr>
        <tr><td>���:</td><td><html:text property="passport" size="20"/></td></tr>
        <tr><td>��:</td><td><html:text property="cbCode" size="20"/></td></tr>
        <tr><td colspan="2">�������������� �������� �������</td></tr>
        <tr><td>����� ��������� ��������:</td><td><html:text property="phoneNumber" size="20"/></td></tr>
        <tr><td colspan="2">��������� ��������</td></tr>
        <tr><td>������� ���������:</td><td><html:checkbox property="deleteDuplicates"/></td></tr>
        <tr><td>������ ����������� ��������� (����� �������):</td><td><html:text property="addPhones" size="20"/></td></tr>
        <tr><td>������ ��������� ��������� (����� �������):</td><td><html:text property="removePhones" size="20"/></td></tr>
    </table>

    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>������:</td></tr>
        <tr><td><html:textarea property="errors" readonly="true" rows="5" style="width:100%"/></td></tr>
    </table>

    <html:submit property="operation" value="send"/>
</html:form>

</body>
</html:html>