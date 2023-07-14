<%--
  Created by IntelliJ IDEA.
  User: EgorovaA
  Date: 06.11.13
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--Информация по вкладам--%>
<c:if test="${accountLink != null}">
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="account" value="${accountLink.account}" scope="request"/>
    <c:set var="accountState" value="${account.accountState}" scope="request"/>

    <div id="sortable" class="sortableRows">
        <html:hidden property="sortedAccountIds" value="${accountLink.id}"/>
        <div class="tinyProductImg">
            <c:choose>
                <c:when test="${accountState == 'OPENED' || accountState == 'LOST_PASSBOOK'}">
                    <img src="${imagePath}/deposits_type/account32.jpg">
                </c:when>
                <c:otherwise>
                    <img src="${imagePath}/deposits_type/account32Blocked.jpg">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="left">
            <div class="titleBlock">
                <c:set var="linkName"><c:out value="${accountLink.name}"/></c:set>
                <span id="productTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                <div class="lightness"></div>
            </div>
            <c:set var="formattedNumber" value="${phiz:getFormattedAccountNumber(accountLink.account.number)}"/>
            <span class="productNumber">${phiz:changeWhiteSpaces(formattedNumber)}</span>
        </div>
        <div class="right">
            <span class="prodStatus bold">${phiz:formatAmount(accountLink.account.balance)}</span>
        </div>
    </div>
</c:if>
