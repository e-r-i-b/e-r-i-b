<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>ѕриостановка автоплатежа (шина)</title>
</head>

<body>
<h1>ѕриостановка автоплатежа (шина): сохранение</h1>

<html:form action="/mobileApi/delayAutoSubscriptionPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="DelayAutoSubscriptionPayment"/>
        <tiles:put name="operation" value="save"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.delayAutoSubscriptionPayment}"/>
            <tiles:insert page="auto-subscription-payment-fields.jsp" flush="false">
                <tiles:put name="autoSubPayment" beanName="initialData"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>