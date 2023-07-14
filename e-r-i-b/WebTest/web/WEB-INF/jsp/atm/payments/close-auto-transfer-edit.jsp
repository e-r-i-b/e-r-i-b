<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Автоперевод (сохранение): перевод между своими счетами/на карту частному лицу</title>
    </head>

    <body>
    <h1>Заполнение полей платежа</h1>

    <html:form action="/atm/closeP2PAutoTransfer" show="true">
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>
            <tiles:put name="operation" value="save"/>

            <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
            <c:set var="document"   value="${form.response.initialData.closeP2PAutoTransferClaim}"/>
            <%@include file="auto-transfer-claim-data.jsp"%>
        </tiles:insert>
    </html:form>
    <br/>

    </body>
</html:html>
