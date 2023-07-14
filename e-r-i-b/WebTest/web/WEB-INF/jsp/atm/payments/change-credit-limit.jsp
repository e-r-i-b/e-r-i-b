<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
<head>
    <title>ќтказ или согласие с кредитным лимитом</title>
</head>

<body>
<h1>ќтказ или согласие с кредитным лимитом: инициализаци€</h1>

<html:form action="/atm/changeCreditLimitClaim" show="true">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="formName" value="ChangeCreditLimitClaim"/>
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="operation" value="init"/>

        <tiles:put name="data">

        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
