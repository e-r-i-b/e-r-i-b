<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--loanOffer - предодобренный продукт.--%>
<c:if test="${loanCardOffer!=null}">
    <loanCardOffer>
        <name>
            <c:choose>
                <c:when test="${loanCardOffer.offerType.value eq '2' or loanCardOffer.offerType.value eq '3'}">
                    <c:set var="creditCardLink" value="${phiz:getClientCreditCard()}"/>
                    <c:out value="${creditCardLink.name}"/>
                </c:when>
                <c:otherwise>
                    ¬аша кредитна€ карта
                </c:otherwise>
            </c:choose>
        </name>
        <maxLimitAmount><fmt:formatNumber value="${loanCardOffer.maxLimit.decimal}" pattern="0.00"/></maxLimitAmount>
        <tiles:insert definition="currencyType" flush="false">
            <tiles:put name="currency" beanName="loanCardOffer" beanProperty="maxLimit.currency"/>
        </tiles:insert>
        <offerType><c:out value="${loanCardOffer.offerType.value}"/></offerType>
        <c:if test="${loanCardOffer.newCardType != null }">
        	<newCardType><c:out value="${loanCardOffer.newCardType}"/></newCardType>
        </c:if>
    </loanCardOffer>
</c:if>
