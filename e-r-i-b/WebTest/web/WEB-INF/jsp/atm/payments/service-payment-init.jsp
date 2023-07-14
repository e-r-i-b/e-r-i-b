<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Оплата услуги по выбранному поставщику</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/atm/servicePayment" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/servicesPayments/edit.do"/>

        <tiles:put name="data">
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td>billing</td>    <td><html:text property="billing"/></td></tr>
                <tr><td>service</td>    <td><html:text property="service"/></td></tr>
                <tr><td>serviceGuid</td>    <td><html:text property="serviceGuid"/></td></tr>
                <tr><td>provider</td>   <td><html:text property="provider"/></td></tr>
                <tr><td>providerGuid</td>   <td><html:text property="providerGuid"/></td></tr>
                <tr><td colspan="2"><b>или повтор платежа</b></td></tr>
                <tr><td>id</td><td>     <html:text property="id"/></td></tr>
                <tr><td colspan="2"><b>или оплата по шаблону</b></td></tr>
                <tr><td>template</td>   <td><html:text property="template"/></td></tr>
                <tr><td colspan="2"><b>или оплата по штрих-коду</b></td></tr>
                <tr><td>barCode</td>    <td><html:text property="barCode"/></td></tr>
            </table>
            <br/>
        </tiles:put>

        <tiles:put name="operation" value="init"/>
    </tiles:insert>

</html:form>

</body>
</html:html>
