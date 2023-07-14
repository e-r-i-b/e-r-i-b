<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>–едактирование копилки(операци€ подтверждена)</title>
</head>

<body>
<h1>–едактирование копилки(операци€ подтверждена)</h1>

<html:form action="/mobileApi/editMoneyBox" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/moneyboxes/edit.do"/>
    </tiles:insert>
</html:form>

</body>
</html:html>
