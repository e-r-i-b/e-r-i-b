<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
    <head>
        <title>���������� ���� � �������� ����������� �����</title>
    </head>

    <body>
    <h1>����: �������� ����������� �����</h1>
    �������� ��� �����<br/>
    �� ��� ����� ������� ������ ������ ���� �� ��������� ���������� (CSHTX). ������ �� ������ ������������ �� ��.<br/>

    <html:form action="/ermb/update/servicefeeresult" enctype="multipart/form-data" show="true">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <html:file property="file"/><br/><br/>
        <html:radio property="success" value="true">�������� ��������</html:radio><br/>
        <html:radio property="success" value="false">���������� ��������</html:radio><br/><br/>
        <html:submit property="operation" value="send"/><br/><br/>
        <html:textarea property="status" cols="100" rows="100" readonly="true" style="border:0px"/><br/>
    </html:form>

    </body>
</html:html>