<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Оформить заявку на кредитную карту</title>
    </head>

    <body>
    <h1>Оформить заявку на кредитную карту </h1>

    <html:form action="/atm/cardOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <tiles:put name="data">
                <c:choose>
                    <c:when test="${not empty form.response.initialData.loanCardProduct}">
                        <c:set var="initialData" value="${form.response.initialData.loanCardProduct}"/>
                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <c:if test="${not empty initialData.income}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="initialData" beanProperty="income"/>
                                        </tiles:insert>
                                    </c:if>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="loan"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="changeDate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="minLimit"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="maxLimit"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="maxLimitInclude"/>
                                    </tiles:insert>
                                    <c:if test="${not empty initialData.additionalTerms}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="initialData" beanProperty="additionalTerms"/>
                                        </tiles:insert>
                                    </c:if>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="gracePeriodDuration"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="gracePeriodInterestRate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="cardProductId"/>
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
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="homePhone"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="workPhone"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="mobilePhone"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="email"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="freeTime"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                    <c:when test="${not empty form.response.confirmStage.confirmType}">
                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <c:set var="payment" value="${form.response.document.loanCardProductDocument}"/>
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="documentNumber"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                </c:choose>
            </tiles:put>

            <c:if test="${empty form.response.confirmStage.confirmType}">
                <tiles:put name="operation" value="next"/>
            </c:if>

            <tiles:put name="formName" value="LoanCardProduct"/>
        </tiles:insert>
    </html:form>

    </body>
    <c:url var="cardOpeningClaimUrl" value="/atm/cardOpeningClaim.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${cardOpeningClaimUrl}">Начать сначала</html:link>
</html:html>
