<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Создание автоплатежа P2P</title>
</head>

<body>
<h1>Создание автоплатежа P2P</h1>

<html:form action="/mobileApi/createP2PAutoTransfer" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.createP2PAutoTransferClaimDocument}"/>
            <%@include file="p2p-auto-payment-document-fields.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
