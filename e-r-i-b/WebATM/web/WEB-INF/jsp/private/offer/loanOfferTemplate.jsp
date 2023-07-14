<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--loanOffer - предодобренный продукт.--%>
<c:if test="${loanOffer!=null}">
    <loanOffer>
        <name><c:out value="${loanOffer.productName}"/></name>
        <procantRate><c:out value="${loanOffer.percentRate}"/></procantRate>
        <maxDuration>
            <c:choose>
                <c:when test="${loanOffer.duration >= 12}">
                    <c:set var="yearNr" value="${loanOffer.duration div 12}"/>
                    <c:out value="${fn:substringBefore(yearNr,'.')}-${loanOffer.duration mod 12}-0"/>
                </c:when>
                <c:otherwise>
                    <c:out value="0-${loanOffer.duration}-0"/>
                </c:otherwise>
            </c:choose>
        </maxDuration>
        <maxLimitAmount><fmt:formatNumber value="${loanOffer.maxLimit.decimal}" pattern="0.00"/></maxLimitAmount>
        <tiles:insert definition="currencyType" flush="false">
            <tiles:put name="currency" beanName="loanOffer" beanProperty="maxLimit.currency"/>
        </tiles:insert>
    </loanOffer>
</c:if>

