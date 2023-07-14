<%--
  Created by IntelliJ IDEA.
  User: EgorovaA
  Date: 06.11.13
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--Информация по счетам депо--%>
<c:if test="${depoAccountLink != null}">
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images" scope="request"/>

    <div id="sortable" class="sortableRows">
        <html:hidden property="sortedDepoAccountIds" value="${depoAccountLink.id}"/>
        <div class="tinyProductImg">
            <c:set var="isLock" value="${depoAccountLink.depoAccount.state != 'open'}"/>
            <c:choose>
                <c:when test="${isLock}">
                    <img src="${imagePath}/products/icon_depositariy32Blocked.jpg">
                </c:when>
                <c:otherwise>
                    <img src="${imagePath}/products/icon_depositariy32.jpg">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="left">
            <div class="titleBlock">
                <c:set var="linkName"><c:out value="${depoAccountLink.name}"/></c:set>
                <span id="productTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                <div class="lightness"></div>
            </div>
            <span class="productNumber">
                <c:choose>
                    <c:when test="${empty depoAccountLink.name}">
                        ${phiz:changeWhiteSpaces(depoAccountLink.accountNumber)}
                    </c:when>
                    <c:otherwise>
                        (${phiz:changeWhiteSpaces(depoAccountLink.accountNumber)})
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
        <div class="right">
            <span class="prodStatus bold">${depoAccountLink.depoAccount.agreementNumber}</span>
        </div>
    </div>
</c:if>