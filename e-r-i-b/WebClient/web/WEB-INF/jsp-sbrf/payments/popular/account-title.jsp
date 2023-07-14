<%--
  Внешние переменные используемые в данном файле
  accountNumber    - номер счета
  accountName      - название счета
--%>
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
        <span class="bold">
            ${phiz:getFormattedAccountNumber(accountNumber)}
        </span>
        <span class="word-wrap"><c:out value="${accountName}"/></span>
    </c:when>
    <c:otherwise>
        <c:set var="userAccountName" value="${accountLink.name}"/>
         <span class="bold">${phiz:getFormattedAccountNumber(accountNumber)}</span>
         <span class="word-wrap whole-words"><bean:write name="userAccountName"/></span>
    </c:otherwise>
</c:choose>
