<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Открытие вклада</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/atm/accountOpeningClaim" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td>depositId</td>    <td><html:text property="depositId"/></td></tr>
                <tr><td>depositType</td>    <td><html:text property="depositType"/></td></tr>
                <tr><td>depositGroup</td>    <td><html:text property="depositGroup"/></td></tr>
                <tr><td>atmPlace</td>    <td><html:text property="atmPlace"/></td></tr>
                <tr><td>atmTB</td>    <td><html:text property="atmTB"/></td></tr>
                <tr><td>atmOSB</td>    <td><html:text property="atmOSB"/></td></tr>
                <tr><td>atmVSP</td>    <td><html:text property="atmVSP"/></td></tr>
            </table>
            <br/>
        </tiles:put>

        <tiles:put name="operation" value="init"/>
        <tiles:put name="formName" value="AccountOpeningClaim"/>
    </tiles:insert>
</html:form>

</body>
</html:html>