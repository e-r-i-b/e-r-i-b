<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание автоплатежа перевода между своими счетами</title>
</head>

<body>
<h1>Создание автоплатежа перевода между своими счетами: инициализация платежа</h1>

<html:form action="/atm/internalPaymentLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address"   value="/private/payments/payment.do"/>
        <tiles:put name="formName"  value="InternalPayment"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">
            Дополнительные параметры не требуются
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>