<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Закрытие вкладов</title>
    </head>

    <body>
    <h1>Закрытие вкладов</h1>

    <html:form action="/atm/AccountClosingPayment" show="true">
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <tiles:put name="data">
                <b>Дополнительные параметры не требуются</b>
                <br/>
                <br/>
            </tiles:put>

            <tiles:put name="operation" value="init"/>
            <tiles:put name="formName" value="AccountClosingPayment"/>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
