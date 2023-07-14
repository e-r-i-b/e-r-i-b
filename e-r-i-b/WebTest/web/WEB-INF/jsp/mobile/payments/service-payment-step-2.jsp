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

<html:form action="/mobileApi/servicePayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile">
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
</html:form>

</body>
</html:html>
