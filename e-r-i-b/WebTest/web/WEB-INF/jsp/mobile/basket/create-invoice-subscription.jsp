<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>Создание подписки из истории операции</title>
</head>

<body>
<h1>Создание подписки из истории операции: инициализация</h1>

<html:form action="/mobileApi/createInvoiceSubscriptionPayment" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/basket/payments/payment.do"/>
        <tiles:put name="formName" value="CreateInvoiceSubscriptionPayment"/>
        <tiles:put name="operation" value="init"/>
        <tiles:put name="data">
            ИД операции для создания подписки
            <table>
                <tr><td>operationId:</td>    <td><html:text property="id"/></td></tr>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
