<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
    <head>
        <title>Инициализация автопоиска счетов</title>
    </head>

    <body>
        <h1>Инициализация автопоиска счетов</h1>

        <html:form action="/mobileApi/createInvoiceSubscription" show="true">
            <tiles:insert definition="mobile" flush="false">
                <tiles:put name="address"   value="/private/basket/subscriptions/create.do"/>
                <tiles:put name="formName"  value="CreateInvoiceSubscriptionPayment"/>
                <tiles:put name="operation" value="init"/>

                <tiles:put name="data">
                    <table>
                        <tr>
                            <td>recipient</td>
                            <td>
                                <input type="text" name="recipient"/>
                            </td>
                        </tr>
                        <tr>
                            <td>accountingEntityId</td>
                            <td>
                                <input type="text" name="accountingEntityId"/>
                            </td>
                        </tr>
                    </table>
                </tiles:put>
            </tiles:insert>
        </html:form>
    </body>
</html:html>