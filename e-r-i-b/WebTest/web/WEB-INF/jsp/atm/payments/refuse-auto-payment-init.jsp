<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Отмена автоплатежа</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/atm/refuseAutoPayment" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td>linkId</td>    <td><input type="text" name="linkId"> </td></tr>
            </table>
            <br/>
        </tiles:put>

        <tiles:put name="operation" value="init"/>
        <tiles:put name="formName" value="RefuseAutoPaymentPayment"/>
    </tiles:insert>
</html:form>

</body>
</html:html>