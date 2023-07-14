<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание автоплатежа по оплате услуги по выбранному поставщику</title>
</head>

<body>
<h1>Инициализация платежа</h1>

<html:form action="/mobileApi/createAutoPayment" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/servicesPayments/edit.do"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td>billing</td>    <td><html:text property="billing"/></td></tr>
                <tr><td>service</td>    <td><html:text property="service"/></td></tr>
                <tr><td>provider</td>   <td><html:text property="provider"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
