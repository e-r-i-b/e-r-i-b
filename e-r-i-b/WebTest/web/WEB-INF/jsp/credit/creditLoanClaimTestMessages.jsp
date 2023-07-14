<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
    <head><title>Тест кредитных заявок</title></head>
    <body>
        <h3>Тест кредитных заявок</h3>
        <html:form action="/crm/loanclaim/messages" show="true">
            <ul>
                <li><a href="${phiz:calculateActionURL(pageContext,'/credit/loanclaim')}">Тест смены статуса расширенной заявки на кредит</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/credit/crm/loanclaim')}">Розничный CRM</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/initiationNew')}">Отправка заявки из CRM через ЕРИБ в ETSM в «ручном режиме»</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/searchApplicationRs')}">Ответ на запрос поиска кредитных предложений(SearchApplicationRs)</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/initiateConsumerProductOfferRq')}">Запрос согласия на оферту(InitiateConsumerProductOfferRq)</a></li>
                <li><a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/offerResultTicket')}">Квитанция, отправляемая в ответ на запрос подтверждения оферты(OfferResultTicket)</a></li>
            </ul>
        </html:form>
        <a href="${phiz:calculateActionURL(pageContext,'/index')}">На главную</a>
    </body>
</html:html>
