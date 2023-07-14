<%@ page import="com.rssl.phizicgate.esberibgate.mock.rate.EribRatesMessagingService" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    if(request.getParameter("operation") != null)
    {
        EribRatesMessagingService service = new EribRatesMessagingService();
        service.makeRequest(Boolean.parseBoolean(request.getParameter("urlType")));
    }
%>

<html:html>
    <head>
        <title>���������� ������ �����</title>
    </head>

    <body>
        <h1>����: ��������� ������ �������</h1>

        <form action="" method="POST">
            ������� ����� ���-�������:
            <select name="urlType">
                <option value="false">��������� �� �������� ����������</option>
                <option value="true">��������� �� ������-�����</option>
            </select>

            <html:submit property="operation" value="�������� �����"/>
        </form>

    </body>
</html:html>