<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<c:set var="person" value="${phiz:getPersonInfo()}"/>

<c:if test="${person != null}">
    <c:set var="personId" value="${person.id}"/>
    <c:set var="login"  value="${person.login}"/>
    <c:set var="isNewOrTemplate" value="${person.status == 'T'}"/>
    <c:set var="isCancelation" value="${person.status == 'W'}"/>
    <c:set var="isErrorCancelation" value="${person.status == 'E'}"/>
    <c:set var="isSignAgreement" value="${empty person or person.status == 'S'}"/>
    <c:set var="isUDBO" value="${person.creationType == 'UDBO'}"/>
</c:if>