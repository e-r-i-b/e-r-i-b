<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="accountLink" value="${phiz:getAccountLink(accountNumber)}"/>

<c:choose>
    <c:when test="${accountLink == null || empty (accountLink.number)}">
        <div class="linkName">
            <span class="word-wrap"><c:out value="${accountName}"/></span>
        </div>
        <div class="grayText">
            ${phiz:getFormattedAccountNumber(accountNumber)}
        </div>
    </c:when>
    <c:otherwise>
        <c:set var="userAccountNumber" value="${phiz:getFormattedAccountNumber(accountLink.number)}"/>
        <c:set var="userAccountName" value="${accountLink.name}"/>
        <div class="linkName">
            <span class="word-wrap"><bean:write name="userAccountName"/></span>
        </div>
        <div class="grayText">
            <bean:write name="userAccountNumber"/>
        </div>
    </c:otherwise>
</c:choose>
