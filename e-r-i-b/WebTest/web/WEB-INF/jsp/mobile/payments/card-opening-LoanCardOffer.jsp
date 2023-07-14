<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>ќформить за€вку на прдодобренную кредитную карту</title>
    </head>

    <body>
    <h1>ќформить за€вку на прдодобренную кредитную карту</h1>

<html:form action="/mobileApi/cardOpeningClaim" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>
        <tiles:put name="formName" value="LoanCardOffer"/>
        <c:if test="${empty form.response.confirmStage.confirmType}">
            <tiles:put name="operation" value="next"/>
        </c:if>

        <tiles:put name="data">
            <c:choose>
                <c:when test="${not empty form.response.initialData.loanCardOffer}">
                    <c:set var="initialData" value="${form.response.initialData.loanCardOffer}"/>
                    <tiles:insert page="fields-table.jsp" flush="false">
                        <tiles:put name="data">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="loan"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="offerId"/>
                            </tiles:insert>
                            <c:if test="${initialData.offerType.stringType.value  == '2'}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="changeLimit"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="idWay"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="tb"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="osb"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="vsp"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="passportNumber"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="offerType"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="creditCard"/>
                            </tiles:insert>
                            <c:if test="${initialData.offerType.stringType.value  != '2'}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="interestRate"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="firstYearPayment"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="firstYearPaymentCurrency"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="nextYearPayment"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="nextYearPaymentCurrency"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="amount"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="currency"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="surName"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="firstName"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="patrName"/>
                            </tiles:insert>
                            <c:if test="${initialData.offerType.stringType.value  != '2'}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="place"/>
                                </tiles:insert>
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="initialData" beanProperty="duration"/>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </c:when>
                <c:when test="${not empty form.response.confirmStage.confirmType}">
                    <c:set var="document" value="${form.response.document.loanCardOfferDocument}"/>
                    <tiles:insert page="fields-table.jsp" flush="false">
                        <tiles:put name="data">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="offerType"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="creditCard"/>
                            </tiles:insert>
                            <c:if test="${not empty document.interestRate}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="interestRate"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty document.firstYearPayment}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="firstYearPayment"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty document.firstYearPaymentCurrency}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="firstYearPaymentCurrency"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty document.nextYearPayment}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="nextYearPayment"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty document.nextYearPaymentCurrency}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="nextYearPaymentCurrency"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="amount"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="currency"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="surName"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="firstName"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="patrName"/>
                            </tiles:insert>
                            <c:if test="${not empty document.place}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="place"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty document.duration}">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="duration"/>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </c:when>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
