<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>�������� ����������� �������� �������� ����</title>
</head>

<body>
<h1>������������� ������ �� ����������</h1>

<html:form action="/mobileApi/rurPaymentLongOffer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="RurPayment"/>
        <tiles:put name="operation" value="makeLongOffer"/>

        <tiles:put name="data">
            <c:set var="initialData" value="${form.response.initialData.rurPayment}"/>
            longOfferAllowed: <input value="${initialData.longOfferAllowed}" size="5" readonly><br/>
            <%@include file="rur-payment-initialData.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
