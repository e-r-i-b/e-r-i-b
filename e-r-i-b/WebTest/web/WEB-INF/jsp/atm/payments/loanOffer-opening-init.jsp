<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>«а€вки на предодобренный кредит </title>
    </head>

    <body>
    <h1>ќформление за€вки на предодобренный кредит </h1>

    <html:form action="/atm/loanOfferOpeningClaim" show="true">
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/loan/offerClaim.do"/>

            <tiles:put name="data">
                <b>ƒополнительные параметры не требуютс€</b>
                <br/>
                <br/>
            </tiles:put>

            <tiles:put name="operation" value="init"/>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
