<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод организации</title>
</head>

<body>
<h1>Выбор поставщика</h1>

<html:form action="/atm/jurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/jurPayment/edit.do"/>

        <tiles:put name="data">
            Найден(ы) поставщик(и) в БД:

            <c:forEach var="billingPayment" items="${form.response.billingPayments.payment}">
                <c:url var="servicePaymentUrl" value="/atm/servicePayment.do">
                    <c:param name="url" value="${form.url}"/>
                    <c:param name="cookie" value="${form.cookie}"/>
                    <c:param name="proxyUrl" value="${form.proxyUrl}"/>
                    <c:param name="proxyPort" value="${form.proxyPort}"/>
                    <c:param name="billing" value="${billingPayment.billing}"/>
                    <c:param name="service" value="${billingPayment.service.id}"/>
                    <c:param name="provider" value="${billingPayment.provider.id}"/>
                </c:url>
                <br/>
                <html:link href="${servicePaymentUrl}">billing=${billingPayment.billing}, service=${billingPayment.service.id}, provider=${billingPayment.provider.id}</html:link>
            </c:forEach>
        </tiles:put>
    </tiles:insert>

    <c:url var="jurPaymentUrl" value="/atm/jurPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${jurPaymentUrl}">Начать сначала</html:link>
</html:form>
<br/>

</body>
</html:html>
