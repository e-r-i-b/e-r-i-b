<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
    <head>
        <title>Настройка автопоиска счетов по услуге. Заполнение формы</title>
    </head>

    <body>
        <h1>Настройка автопоиска счетов по услуге. Заполнение формы</h1>

        <html:form action="/mobileApi/createInvoiceSubscription" show="true">
            <tiles:insert definition="mobile" flush="false">
                <tiles:put name="address"   value="/private/userprofile/basket/payments/payment.do"/>
                <tiles:put name="formName"  value="CreateInvoiceSubscriptionForm"/>
                <tiles:put name="operation" value="save"/>

                <tiles:put name="data">
                    ${form.response.document.createInvoiceSubscriptionPaymentDocument}

                    <%@include file="/WEB-INF/jsp/mobile/basket/invoice-subscription-fields.jsp"%>
                </tiles:put>
            </tiles:insert>
        </html:form>
    </body>
</html:html>