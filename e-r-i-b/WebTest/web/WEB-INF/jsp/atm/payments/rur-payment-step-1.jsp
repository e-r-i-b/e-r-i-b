<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод частному лицу</title>
</head>

<body>
<h1>Перевод частному лицу</h1>

<html:form action="/atm/rurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:if test="${not empty form.response.confirmStage.confirmType}">
        <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
    </c:if>
    
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="data">
            <%@include file="rur-payment-initialData.jsp"%>
        </tiles:put>

        <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>
        <tiles:put name="formName" value="RurPayment"/>
    </tiles:insert>
    
    <c:url var="rurPaymentUrl" value="/atm/rurPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${rurPaymentUrl}">Начать сначала</html:link>    

</html:form>
</body>
</html:html>