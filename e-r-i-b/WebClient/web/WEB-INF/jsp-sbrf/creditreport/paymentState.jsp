<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>
<c:choose>
    <c:when test="${code eq 'A'}">
        <c:set var="indicator" value="1-30"/>
    </c:when>
    <c:when test="${code eq '1'}">
        <c:set var="indicator" value="31-60"/>
    </c:when>
    <c:when test="${code eq '2'}">
        <c:set var="indicator" value="61-90"/>
    </c:when>
    <c:when test="${code eq '3'}">
        <c:set var="indicator" value="91-120"/>
    </c:when>
    <c:when test="${code eq '4'}">
        <c:set var="indicator" value="121-150"/>
    </c:when>
    <c:when test="${code eq '5'}">
        <c:set var="indicator" value="151-180"/>
    </c:when>
    <c:when test="${code eq '6'}">
        <c:set var="indicator" value="181"/>
    </c:when>
    <c:when test="${code eq '8'}">
        <c:set var="indicator" value="neupl"/>
    </c:when>
    <c:when test="${code eq 'L'}">
        <c:set var="indicator" value="sud"/>
    </c:when>
    <c:when test="${code eq 'W'}">
        <c:set var="indicator" value="spis"/>
    </c:when>
</c:choose>
<c:if test="${!empty indicator}">
    <div class="cred-hist-pay-col-item">
        <span class="cred-hist-pay-col-label cred-hist-pay-col-label-${indicator}">${name}</span>
        <span class="cred-hist-pay-col-val cred-hist-pay-val-${indicator} css3">${count}</span>
    </div>
</c:if>