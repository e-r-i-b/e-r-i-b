<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>–едактирование автопоиска</title>
    </head>

    <body>
    <h1>–едактирование автопоиска: инициализаци€</h1>

    <html:form action="/mobileApi/editInvoiceSubscriptionClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/basket/subscriptions/editSubscription.do"/>
            <tiles:put name="formName" value="EditInvoiceSubscriptionClaim"/>
            <tiles:put name="operation" value="init"/>

            <tiles:put name="data">
                ID редактируемого автопоиска:
                <table>
                    <tr><td>subscriptionId:</td>    <td><html:text property="subscriptionId"/></td></tr>
                </table>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
