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

<html:form action="/mobileApi/jurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/jurPayment/edit.do"/>

        <tiles:put name="data">
            Найден(ы) поставщик(и) в БД:

            <c:forEach var="billingPayment" items="${form.response.billingPayments.payment}">
                <c:url var="servicePaymentUrl" value="/mobileApi/servicePayment.do">
                    <c:param name="url" value="${form.url}"/>
                    <c:param name="cookie" value="${form.cookie}"/>
                    <c:param name="proxyUrl" value="${form.proxyUrl}"/>
                    <c:param name="proxyPort" value="${form.proxyPort}"/>
                    <c:param name="version" value="${form.version}"/>
                    <c:param name="billing" value="${billingPayment.billing}"/>
                    <c:param name="service" value="${billingPayment.service.id}"/>
                    <c:param name="provider" value="${billingPayment.provider.id}"/>
                </c:url>
                <html:link href="${servicePaymentUrl}">billing=${billingPayment.billing}, service=${billingPayment.service.id}, provider=${billingPayment.provider.id}</html:link>
            </c:forEach>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
