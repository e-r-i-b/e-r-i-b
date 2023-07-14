<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:html>
<head>
    <title>Оплата по шаблону мобильного банка</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/atm/mobileBankTemplatePayment" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td>SMStemplate</td>    <td><html:text property="SMStemplate"/></td></tr>
            </table>
            <br/>
        </tiles:put>

        <tiles:put name="operation" value="init"/>
    </tiles:insert>

</html:form>

</body>
</html:html>
