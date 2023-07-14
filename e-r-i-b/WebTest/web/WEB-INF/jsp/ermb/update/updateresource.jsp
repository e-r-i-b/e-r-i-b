<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
<head>
    <title>���������� ���� �� ��������� �������� �������</title>
</head>

<body>
<h1>����: ��������� �������� �������</h1>

<html:form action="/ermb/update/updateresource" show="true">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <h2>������ �������</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>�������</td><td><html:text property="lastname" size="20"/></td></tr>
        <tr><td>���</td><td><html:text property="firstname" size="20"/></td></tr>
        <tr><td>��������</td><td><html:text property="middlename" size="20"/></td></tr>
        <tr><td>���� ��������</td><td><html:text property="birthday" size="20"/></td></tr>
        <tr><td>��� ���������</td><td><html:text property="idType" size="20"/></td></tr>
        <tr><td>����� ���������</td><td><html:text property="idSeries" size="20"/></td></tr>
        <tr><td>����� ���������</td><td><html:text property="idNum" size="20"/></td></tr>
        <tr><td>��� �����</td><td><html:text property="issuedBy" size="20"/></td></tr>
        <tr><td>���� ������</td><td><html:text property="issueDt" size="20"/></td></tr>
        <tr><td>����� �������� (��)</td><td><html:text property="tb" size="3"/></td></tr>
    </table>

    <h2>������� �������, � ������� ������� ���������</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>��� �������� (���������� ��������: account, card, loan)</td><td><html:text property="resourceType" size="20"/></td></tr>
        <tr><td>����� ��������</td><td><html:text property="resourceNumber" size="20"/></td></tr>
    </table>

    <h2>��� ��������, � �������� ��������� �������<html:text property="productTb" size="20"/></h2>

    <html:submit property="operation" value="send"/>
</html:form>

</body>
</html:html>