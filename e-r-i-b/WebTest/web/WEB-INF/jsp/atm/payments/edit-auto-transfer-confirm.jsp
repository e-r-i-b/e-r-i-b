<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Автоперевод (подтверждение): перевод между своими счетами/на карту частному лицу</title>
    </head>

    <body>
    <h1>Подтверждение платежа</h1>

    <html:form action="/atm/editP2PAutoTransfer" show="true">
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
            <c:set var="document"   value="${form.response.document.editP2PAutoTransferClaim}"/>
            <%@include file="auto-transfer-claim-data.jsp"%>
        </tiles:insert>
    </html:form>
    <br/>

    </body>
</html:html>
