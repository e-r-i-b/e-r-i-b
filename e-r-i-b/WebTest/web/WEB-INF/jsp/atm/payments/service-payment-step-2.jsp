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
<h1>Последующие шаги оплаты</h1>

<html:form action="/atm/servicePayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <c:if test="${not empty form.response.confirmStage.confirmType}">
            <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
        </c:if>


        <tiles:put name="data">
            <%@include file="service-payment-document.jsp"%>
        </tiles:put>

        <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>
    </tiles:insert>

    <c:url var="servicePaymentUrl" value="/atm/servicePayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${servicePaymentUrl}">Начать сначала</html:link>
</html:form>
<br/>

</body>
</html:html>
