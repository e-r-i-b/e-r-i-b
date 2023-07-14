<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="loanLink" value="${phiz:getLoanLink(accountNumber)}"/>

<c:choose>
    <c:when test="${loanLink == null}">
        <div class="grayText">
            ${accountNumber}
        </div>
    </c:when>
    <c:otherwise>
        <c:set var="userLoanNumber" value="${loanLink.number}"/>
        <c:set var="userLoanName" value="${loanLink.name}"/>
        <div class="linkName">
            <bean:write name="userLoanName"/>
        </div>
        <div class="grayText">
            <bean:write name="userLoanNumber"/>
        </div>
    </c:otherwise>
</c:choose>
