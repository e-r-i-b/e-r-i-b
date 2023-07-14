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
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.data}">
                <loanOfferStage>
                    <c:forEach items="${form.data}" var="loanOffer">
                        <c:set var="currency" value="${loanOffer.maxLimit.currency}"/>
                        <c:forEach items="${loanOffer.conditions}" var="offerCondition">
                            <c:if test="${not empty offerCondition.amount and not(offerCondition.amount eq 0)}">
                                <option>
                                    <loanId><c:out value="${loanOffer.offerId}"/></loanId>
                                    <name><c:out value="${loanOffer.productName}"/></name>
                                    <tiles:insert definition="mobileMoneyType" flush="false">
                                        <tiles:put name="name" value="offerSum"/>
                                        <tiles:put name="currency"   beanName="currency"/>
                                        <tiles:put name="decimal" value="${offerCondition.amount}"/>
                                        <tiles:put name="noCents"  value="true"/>
                                    </tiles:insert>
                                    <rate><c:out value="${loanOffer.percentRate}"/></rate>
                                    <duration><c:out value="${offerCondition.period}"/></duration>
                                </option>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </loanOfferStage>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
