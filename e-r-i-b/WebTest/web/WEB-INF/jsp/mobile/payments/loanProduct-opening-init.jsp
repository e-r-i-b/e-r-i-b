<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Заявки на кредит </title>
    </head>

    <body>
    <h1>Оформление заявки на кредит </h1>

    <html:form action="/mobileApi/loanProductOpeningClaim" show="true">
        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/payments/loan/claim.do"/>
            <tiles:put name="operation" value="init"/>

            <tiles:put name="data">
                Дополнительные параметры не требуются
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
