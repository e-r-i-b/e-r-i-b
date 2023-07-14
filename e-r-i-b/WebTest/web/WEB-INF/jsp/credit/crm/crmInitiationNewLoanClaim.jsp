<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
    <head>
        <title>��������� �� CRM �������� ����� ������ � ETSM</title>
    </head>

    <body>
    <h1>��������� �� CRM �������� ����� ������ � ETSM</h1>

    <html:form action="/crm/loanclaim/initiationNew" show="true">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
        <html:submit property="operation" value="send"/>
        <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
            <tr><td>RqUID:</td><td><html:text property="eribSendETSMApplRqUID" size="20"/></td></tr>
            <tr><td>XML:</td><td><html:textarea property="eribSendETSMApplRqXML" rows="30" cols="100"/></td></tr>
        </table>
        <br/>
        <br/>
    </html:form>
    <a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/messages')}">��������� � ������</a>
    </body>
</html:html>