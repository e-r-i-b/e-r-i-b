<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>
<tiles:importAttribute/>

<html:form action="/private/payments/loan/cardOffer">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <loanCardOfferStage>
                <c:forEach items="${form.data}" var="conditionProductByOffer">
                    <option>
                        <loanId><c:out value="${conditionProductByOffer.conditionId}"/></loanId>
                        <c:choose>
                            <c:when test="${phiz:getApiVersionNumber().major >= 9}">
                                <offerId><c:out value="${conditionProductByOffer.offerId}"/></offerId>
                            </c:when>
                            <c:otherwise>
                                <offerId><c:out value="${conditionProductByOffer.offerId.id}"/></offerId>
                            </c:otherwise>
                        </c:choose>
                        <name><c:out value="${conditionProductByOffer.name}"/></name>
                        <c:if test="${not empty conditionProductByOffer.gracePeriodDuration}">
                            <gracePeriodDuration><c:out value="${conditionProductByOffer.gracePeriodDuration}"/></gracePeriodDuration>
                            <gracePeriodInterestRate><c:out value="${conditionProductByOffer.gracePeriodInterestRate}"/></gracePeriodInterestRate>
                        </c:if>
                        <c:set var="sum" value="${conditionProductByOffer.maxLimit}"/>
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="maxLimit" />
                            <tiles:put name="money" beanName="sum"/>
                            <tiles:put name="noCents"  value="true"/>
                        </tiles:insert>
                        <offerInterestRate><c:out value="${conditionProductByOffer.offerInterestRate}"/></offerInterestRate>
                        <c:set var="sum" value="${conditionProductByOffer.firstYearPayment}"/>
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="firstYearPayment" />
                            <tiles:put name="money" beanName="sum"/>
                        </tiles:insert>
                        <c:set var="sum" value="${conditionProductByOffer.nextYearPayment}"/>
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="nextYearPayment" />
                            <tiles:put name="money" beanName="sum"/>
                        </tiles:insert>
                        <c:if test="${not empty conditionProductByOffer.terms}">
                            <terms><c:out value="${conditionProductByOffer.terms}"/></terms>
                        </c:if>
                    </option>
                </c:forEach>
            </loanCardOfferStage>
        </tiles:put>
        <c:if test="${!form.loanCardClaimAvailable}">
            <tiles:put name="status">${STATUS_CRITICAL_ERROR}</tiles:put>
        </c:if>
    </tiles:insert>
</html:form>
