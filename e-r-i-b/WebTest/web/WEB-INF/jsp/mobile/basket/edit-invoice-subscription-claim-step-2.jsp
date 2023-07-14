<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Редактирование автопоиска</title>
    </head>

    <body>
    <h1>Редактирование автопоиска</h1>

    <html:form action="/mobileApi/editInvoiceSubscriptionClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/basket/subscriptions/editSubscription.do"/>
            <tiles:put name="formName" value="EditInvoiceSubscriptionClaim"/>
            <tiles:put name="data">
                <c:if test="${not empty form.response and not empty form.response.document}">
                    <c:set var="document" value="${form.response.document.editInvoiceSubscriptionClaim}"/>
                    <tiles:insert page="../payments/fields-table.jsp" flush="false">
                        <tiles:put name="data">
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="nameService"/>
                            </tiles:insert>
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="invoiceAccountName"/>
                            </tiles:insert>

                            <c:set var="receiver" value="${document.receiver}"/>
                            <c:if test="${not empty receiver}">
                                <c:if test="${not empty receiver.name}">
                                    <tiles:insert page="../payments/field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="name"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty receiver.inn}">
                                    <tiles:insert page="../payments/field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="inn"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${not empty receiver.account}">
                                    <tiles:insert page="../payments/field.jsp" flush="false">
                                        <tiles:put name="field" beanName="receiver" beanProperty="account"/>
                                    </tiles:insert>
                                </c:if>
                                <c:set var="recipientBank" value="${receiver.recipientBank}"/>
                                <c:if test="${not empty recipientBank}">
                                    <c:if test="${not empty recipientBank.name}">
                                        <tiles:insert page="../payments/field.jsp" flush="false">
                                            <tiles:put name="field" beanName="recipientBank" beanProperty="name"/>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty recipientBank.bic}">
                                        <tiles:insert page="../payments/field.jsp" flush="false">
                                            <tiles:put name="field" beanName="recipientBank" beanProperty="bic"/>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty recipientBank.corAccount}">
                                        <tiles:insert page="../payments/field.jsp" flush="false">
                                            <tiles:put name="field" beanName="recipientBank" beanProperty="corAccount"/>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <c:set var="requisites" value="${document.requisites}"/>
                            <c:if test="${not empty requisites}">
                                <c:forEach items="${requisites.field}" varStatus="i">
                                    <tiles:insert page="../payments/field.jsp" flush="false">
                                        <tiles:put name="field" beanName="requisites" beanProperty="field[${i.index}]"/>
                                    </tiles:insert>
                                </c:forEach>
                            </c:if>
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="subscriptionName"/>
                            </tiles:insert>
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                            </tiles:insert>
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="accountingEntity"/>
                            </tiles:insert>
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="eventType"/>
                            </tiles:insert>
                            <tiles:insert page="../payments/field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="dayPay"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </c:if>

            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>