<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<html:html>
<head>
    <title>����������� �������</title>
</head>

<body>
<h1>����������� �������</h1>

<html:form action="/mobileApi/createMoneyBox" show="true">
    <%@include file="edit-and-view-billing-long-offer-fields.jsp"%>
</html:form>

</body>
</html:html>
