<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Оплата по шаблону мобильного банка</title>
</head>

<body>
<h1>Оплата по шаблону мобильного банка</h1>

<html:form action="/atm/mobileBankTemplatePayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:choose>
                <c:when test="${not empty form.response.document and not empty form.response.document.rurPaymentDocument}">
                    <%@include file="rur-payment-document.jsp"%>
                </c:when>
                <c:when test="${not empty form.response.document and not empty form.response.document.rurPayJurSBDocument}">
                    <%@include file="service-payment-document.jsp"%>
                </c:when>
            </c:choose>
        </tiles:put>
        
        <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>
    </tiles:insert>
</html:form>
<br/>

</body>
</html:html>
