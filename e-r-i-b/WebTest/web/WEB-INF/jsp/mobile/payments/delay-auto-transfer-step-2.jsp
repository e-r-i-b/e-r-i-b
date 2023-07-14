<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>ѕриостановление автоплатежа (P2P)</title>
</head>

<body>
<h1>ѕриостановление автоплатежа (P2P): подтверждение</h1>

<html:form action="/mobileApi/delayP2PAutoTransferClaim" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.delayP2PAutoTransferClaimDocument}"/>
            <%@include file="p2p-auto-payment-document-fields.jsp"%>
        </tiles:put>

    </tiles:insert>
</html:form>
</body>
</html:html>