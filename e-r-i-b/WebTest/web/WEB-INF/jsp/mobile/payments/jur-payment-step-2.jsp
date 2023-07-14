<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод организации</title>
</head>

<body>
<h1>Ввод дополнительных данных</h1>

<html:form action="/mobileApi/jurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="JurPayment"/>

        <c:if test="${not empty form.response.confirmStage.confirmType}">
            <c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>
        </c:if>

        <c:if test="${empty confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>
        
        <tiles:put name="data">
            <c:choose>
                <c:when test="${not empty form.response.document.rurPayJurSBDocument}">
                    <tiles:insert page="service-payment-document-fields.jsp" flush="false">
                        <tiles:put name="document" beanName="form" beanProperty="response.document.rurPayJurSBDocument"/>
                    </tiles:insert>
                </c:when>
                <c:when test="${not empty form.response.document.jurPaymentDocument}">
                    <c:set var="document" value="${form.response.document.jurPaymentDocument}"/>
                    <tiles:insert page="fields-table.jsp" flush="false">
                        <tiles:put name="data">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                            </tiles:insert>

                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.receiverName"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.account"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.accountCurrency"/>
                            </tiles:insert>

                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.INN"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.KPP"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.bank.name"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.bank.BIC"/>
                            </tiles:insert>

                            <c:if test="${not empty document.receiver.bankDetails.bank.corAccount}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="receiver.bankDetails.bank.corAccount"/>
                                </tiles:insert>
                            </c:if>

                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                            </tiles:insert>

                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="paymentDetails.buyAmount"/>
                            </tiles:insert>

                            <c:if test="${not empty document.paymentDetails.operationCode}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="paymentDetails.operationCode"/>
                                </tiles:insert>
                            </c:if>

                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="paymentDetails.ground"/>
                            </tiles:insert>

                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="paymentDetails.taxPayment"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                    <c:if test="${not empty document.paymentDetails.commission}">
                        <b>Комиссия:</b> ${document.paymentDetails.commission.amount} ${document.paymentDetails.commission.currency.code}
                    </c:if>
                </c:when>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
