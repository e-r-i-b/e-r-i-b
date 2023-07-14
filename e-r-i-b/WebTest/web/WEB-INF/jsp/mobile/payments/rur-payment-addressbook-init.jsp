<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Универсальный перевод на карту частному лицу</title>
</head>

<body>
<h1>Инициализация перевода</h1>

<html:form action="/mobileApi/rurPaymentAddressBook" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="NewRurPayment"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            <table>
                <tr><td colspan="2">Параметры перевода</td></tr>
                <tr><td>Номер карты получателя:</td><td><html:text property="externalCardNumber"/></td></tr>
                <tr><td>Номер телефона получателя:</td><td><html:text property="externalPhoneNumber"/></td></tr>
                <tr><td>ИД получателя в адресной книге:</td><td><html:text property="externalContactId"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>