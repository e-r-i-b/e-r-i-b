<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>��������� ������� ��������� ������� �� ���� �������</title>
</head>

<body>
<h1>>��������� ������� ��������� ������� �� ���� �������</h1>

<html:form action="/mobileApi/changeStateFundRequest" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/fund/response.do"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td colspan="2">��� ��������� ������� ������� �� ���� ������� ��������� ����</td></tr>
                <tr><td>id(������� ������������� ��������� ������� - externalId � ������� FUND_SENDER_RESPONSES)</td><td><html:text property="id"/></td></tr>
                <tr><td>state(������ READ, REJECT, SUCCESS)</td><td><html:text property="state"/></td></tr>
                <tr><td>sum(����� �������)</td><td><html:text property="sum"/></td></tr>
                <tr><td>message(��������� ����������)</td><td><html:text property="message"/></td></tr>
                <tr><td>closeConfirm(������������� �������� ������� ��� ��������� ����� �������)</td><td><html:text property="closeConfirm"/></td></tr>
                <tr><td>fromResource(����� ��������)</td><td><html:text property="fromResource"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>

