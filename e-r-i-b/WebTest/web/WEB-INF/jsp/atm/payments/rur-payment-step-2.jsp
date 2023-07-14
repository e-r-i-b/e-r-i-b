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
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="data">
            <%@include file="rur-payment-document.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>