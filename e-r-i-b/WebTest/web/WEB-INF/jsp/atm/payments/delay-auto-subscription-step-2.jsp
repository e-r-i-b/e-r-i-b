<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>ѕриостановление автоплатежа (шина)</title>
</head>

<body>
<h1>ѕриостановление автоплатежа (шина): подтверждение</h1>

<html:form action="/atm/delayAutoSubscriptionPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.delayAutoSubscriptionDocument}"/>
            <tiles:insert page="auto-subscription-payment-fields.jsp" flush="false">
                <tiles:put name="autoSubPayment" beanName="document"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>