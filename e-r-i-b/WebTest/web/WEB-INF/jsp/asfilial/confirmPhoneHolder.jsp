<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:html>
    <%--������ ���� (((%--%>
    <jsp:include page="addPhone.jsp"/>
        ready(function()
        {
            addPhoneField(query("#addphone"),"phoneNumber","mobilePhoneOperator",null,"phone","last");
        });
    });
</script>
<script></script>

<head><title>������������ �������������� ���� � �� ������-��������</title></head>
<body>
<h1>������� � ������������� ��������� ������ ���������� ��������.</h1>

<html:form action="/asfilial" show="true">
<html:hidden property="ASListenerUrl"/>
    <table>
        <tr>
            <td>
                <a id="addphone" href="#">�������� �����</a>
            </td>
        </tr>
        <tr>
            <td>
                ����� ��������, ��������� ������������� ���������[1-n]
            </td>
            <td>
                <ul id="phone"></ul>
            </td>
        </tr>

    </table>
    <html:submit property="operation" value="ConfirmPhoneHolder"/>
<html:submit property="operation" value="Back"/>
</html:form>
</body>
</html:html>