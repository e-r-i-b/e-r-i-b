<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html:form action="/private/payments/loan/cardProduct">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <loanCardProductStage>
                <c:forEach var="form_data" items="${form.data}">
                    <option>
                        <c:set var="product" value="${form_data}"/>

                        <loanId><c:out value="${product.id}"/></loanId>
                        <changeDate><c:out value="${product.changeDate.timeInMillis}"/></changeDate>
                        <name><c:out value="${product.name}"/></name>
                        <c:if test="${product.allowGracePeriod}">
                            <gracePeriod>
                                <duration><c:out value="${product.gracePeriodDuration}"/></duration>
                                <interestRate><c:out value="${product.gracePeriodInterestRate}"/></interestRate>
                            </gracePeriod>
                        </c:if>
                        <creditLimit>
                            <c:set var="sum" value="${product.minCreditLimit}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="min" />
                                <tiles:put name="money" beanName="sum"/>
                                <tiles:put name="noCents" value="true"/>
                            </tiles:insert>
                            <c:set var="sum" value="${product.maxCreditLimit}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="max" />
                                <tiles:put name="money" beanName="sum"/>
                                <tiles:put name="noCents" value="true"/>
                            </tiles:insert>
                            <includeMax><c:out value="${product.isMaxCreditLimitInclude}"/></includeMax>
                        </creditLimit>
                        <offerInterestRate><c:out value="${product.offerInterestRate}"/></offerInterestRate>
                            <c:set var="sum" value="${product.firstYearPayment}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="firstYearPayment" />
                                <tiles:put name="money" beanName="sum"/>
                            </tiles:insert>
                            <c:set var="sum" value="${product.nextYearPayment}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="nextYearPayment" />
                                <tiles:put name="money" beanName="sum"/>
                            </tiles:insert>
                        <c:if test="${not empty product.additionalTerms}">
                            <terms><c:out value="${product.additionalTerms}"/></terms>
                        </c:if>
                    </option>
                </c:forEach>
            </loanCardProductStage>
        </tiles:put>
    </tiles:insert>
</html:form>
