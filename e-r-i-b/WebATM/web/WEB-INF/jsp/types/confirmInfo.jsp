<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:importAttribute ignore="true"/>

<%--
Информация о способе подтверждения
confirmRequest - реквест подтверждения

--%>
<c:if test="${not empty confirmRequest and (confirmRequest.strategyType eq 'sms' or confirmRequest.strategyType eq 'card')}">
    <c:set var="smsTimeToLive">${requestScope['smsCurrentTimeToLive']}</c:set>
    <c:set var="smsAttemptsLeft">${requestScope['smsAttemptsLeft']}</c:set>
    <confirmInfo>
        <c:choose>
            <c:when test="${confirmRequest.strategyType eq 'sms'}">
                <type>smsp</type>
                <smsp>
                    <lifeTime>${smsTimeToLive}</lifeTime>
                    <attemptsRemain>${smsAttemptsLeft}</attemptsRemain>
                </smsp>
            </c:when>
            <c:when test="${confirmRequest.strategyType eq 'card'}">
                <type>cardp</type>
                <cardp>
                    <passwordNumber>${confirmRequest.passwordNumber}</passwordNumber>
                    <cardNumber>${confirmRequest.cardNumber}</cardNumber>
                    <c:if test="${!empty confirmRequest.passwordsLeft}">
                        <passwordsLeft>${confirmRequest.passwordsLeft}</passwordsLeft>
                    </c:if>
                </cardp>
            </c:when>
        </c:choose>
    </confirmInfo>
</c:if>