<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
<head>
    <title>��� ����: �������� ���</title>
</head>

<body>
<h1>��� ����: �������� ���</h1>

<html:form action="/ermb/sms/sendsms" show="true">

    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>����� ��������:</td><td><html:text property="phone" size="20"/></td></tr>
        <tr><td>����� ���:</td><td><html:text property="text" size="100"/></td></tr>
        <tr><td>���������� �������:</td><td><html:text property="threadCount" size="20"/></td></tr>
        <tr>
            <td>�������������� �����</td>
            <td>
                <html:select property="application">
                    <html:option value="csa">CSA</html:option>
                    <html:option value="erib">ERIB</html:option>
                </html:select>
            </td>
        </tr>
    </table>

    <html:submit property="operation" value="send"/>
</html:form>

</body>
</html:html>