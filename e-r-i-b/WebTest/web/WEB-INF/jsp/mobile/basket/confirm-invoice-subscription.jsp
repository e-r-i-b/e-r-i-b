<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание подписки из истории операции</title>
</head>

<body>
<h1>Создание подписки из истории операции: подтверждение</h1>

<c:set var="currentUrl" value="/mobileApi/createInvoiceSubscriptionPayment"/>
<c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, currentUrl)}"/>
<html:form action="${currentUrl}" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="response" value="${form.response}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/userprofile/basket/payments/confirm.do"/>
        <c:if test="${not empty response.document.form}">
            <tiles:put name="formName" value="${response.document.form}"/>
        </c:if>
        <tiles:put name="data">
            <%@ include file="/WEB-INF/jsp/mobile/basket/invoice-subscription-fields.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
