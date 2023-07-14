<%--
  Created by IntelliJ IDEA.
  User: EgorovaA
  Date: 06.11.13
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<!-- Информация по OMC -->
<c:if test="${imAccountLink != null}">
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images" scope="request"/>

    <div id="sortable" class="sortableRows">
        <html:hidden property="sortedIMAccountIds" value="${imAccountLink.id}"/>
        <div class="tinyProductImg">
            <c:choose>
                <c:when test="${imAccountLink.state == 'closed' || imAccountLink.state == 'lost_passbook'}">
                    <img src="${imagePath}/ima_type/imaccount32Blocked.jpg">
                </c:when>
                <c:otherwise>
                    <img src="${imagePath}/ima_type/imaccount32.jpg">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="left">
            <div class="titleBlock">
                <span id="productTitle">
                    <c:choose>
                        <c:when test="${not empty imAccountLink.name}">
                            <c:set var="linkName"><c:out value="${imAccountLink.name}"/></c:set>
                            ${phiz:changeWhiteSpaces(linkName)}
                        </c:when>
                        <c:when test="${not empty imAccountLink.currency}">
                            <c:out value="${imAccountLink.currency.name} (${phiz:normalizeCurrencyCode(imAccountLink.currency.code)})"/>
                        </c:when>
                    </c:choose>
                </span>
                <div class="lightness"></div>
            </div>
            <span class="productNumber">${phiz:changeWhiteSpaces(imAccountLink.number)}</span>
        </div>
        <div class="right">
            <span class="prodStatus bold">${phiz:formatAmount(imAccountLink.imAccount.balance)}</span>
        </div>
    </div>
</c:if>
