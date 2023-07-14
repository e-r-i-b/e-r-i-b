<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="accountLink" value="${phiz:getAccountLink(accountNumber)}"/>

<c:choose>
    <c:when test="${accountLink == null}">
        ${accountName} ${accountNumber}
    </c:when>
    <c:otherwise>
        <c:set var="userAccountNumber" value="${accountLink.number}"/>
        <c:set var="userAccountName" value="${accountLink.name}"/>
        <bean:write name="userAccountName"/> <bean:write name="userAccountNumber"/>
    </c:otherwise>
</c:choose>
