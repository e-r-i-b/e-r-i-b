<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
<head>
    <title>Отмена автоплатежа (P2P)</title>
</head>

<body>
<h1>Отмена автоплатежа (P2P): инициализация</h1>

<html:form action="/mobileApi/closeP2PAutoTransferClaim" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="CloseP2PAutoTransferClaim"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td>autoSubNumber</td>    <td><html:text property="autoSubNumber"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
