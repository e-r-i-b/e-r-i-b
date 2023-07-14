<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:importAttribute  ignore="true"/>

<html:form action="/private/payments/loan/offerClaim">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.data}">
                <LoanOfferStage>
                    <c:forEach items="${form.data}" var="loanOffer">
                    <c:set var="currency" value="${loanOffer.maxLimit.currency}"/>
                        <c:forEach items="${loanOffer.conditions}" var="condition">
                            <option>
                                <loanId><c:out value="${loanOffer.offerId}"/></loanId>
                                <name><c:out value="${loanOffer.productName}"/></name>
                                <c:set var="amount" value="${condition.amount}"/>
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="offerSum"/>
                                    <tiles:put name="currency"   beanName="currency"/>
                                    <tiles:put name="decimal"  beanName="amount"/>
                                </tiles:insert>
                                <rate><c:out value="${condition.rate}"/></rate>
                                <duration>${condition.period}</duration>
                            </option>
                        </c:forEach>
                    </c:forEach>
                </LoanOfferStage>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
