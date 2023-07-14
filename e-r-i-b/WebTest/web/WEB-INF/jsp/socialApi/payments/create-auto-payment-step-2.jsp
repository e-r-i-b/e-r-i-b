<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<html:html>
<head>
    <title>Создание автоплатежа</title>
</head>

<body>
<h1>Создание автоплатежа оплаты услуг</h1>

<html:form action="/mobileApi/createAutoPayment" show="true">
    <%@include file="edit-and-view-billing-long-offer-fields.jsp"%>
</html:form>

</body>
</html:html>
