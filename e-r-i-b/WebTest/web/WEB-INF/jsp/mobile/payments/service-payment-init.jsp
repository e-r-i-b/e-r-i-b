<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>������ ������ �� ���������� ����������</title>
</head>

<body>
<h1>������������� �������</h1>

<html:form action="/mobileApi/servicePayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/servicesPayments/edit.do"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td>billing</td>        <td><html:text property="billing"/></td></tr>
                <tr><td>service</td>        <td><html:text property="service"/></td></tr>
                <tr><td>provider</td>       <td><html:text property="provider"/></td></tr>
                <c:if test="${form.version >= 5.00}">
                    <tr><td colspan="2">&nbsp;&nbsp; ���</td></tr>
                    <tr><td colspan="2">������ �������</td></tr>
                    <tr><td>id</td><td><html:text property="id"/></td></tr>
                    <tr><td colspan="2">&nbsp;&nbsp; ���</td></tr>
                    <tr><td colspan="2">������ �� �������</td></tr>
                    <tr><td>template</td><td><html:text property="template"/></td></tr>
                </c:if>
                <c:if test="${form.version >= 7.00}">
                    <tr><td colspan="2">&nbsp;&nbsp; ���</td></tr>
                    <tr><td colspan="2">������ �� �����-����</td></tr>
                    <tr><td>barCode</td><td><html:text property="barCode"/></td></tr>
                </c:if>
                <c:if test="${form.version >= 8.00}">
                    <tr><td colspan="2">&nbsp;&nbsp; ���</td></tr>
                    <tr><td colspan="2">������ ������������ ��������</td></tr>
                    <tr><td>trustedRecipientId</td><td><html:text property="trustedRecipientId"/></td></tr>
                </c:if>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
