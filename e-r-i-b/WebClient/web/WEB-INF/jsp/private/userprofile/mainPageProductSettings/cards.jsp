<%--
  Created by IntelliJ IDEA.
  User: EgorovaA
  Date: 06.11.13
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--Информация по вкладам--%>
<c:if test="${cardLink != null}">
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="card" value="${cardLink.card}"/>
    <c:set var="description" value="${cardLink.description}"/>
    <c:choose>
        <c:when test="${not empty card.cardLevel and card.cardLevel == 'MQ'}">
            <c:set var="imgCode" value="mq" />
        </c:when>
        <c:otherwise>
            <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
        </c:otherwise>
    </c:choose>

    <div id="sortable" class="sortableRows">
        <html:hidden property="sortedCardIds" value="${cardLink.id}"/>
        <div class="tinyProductImg">
            <c:choose>
                <c:when test="${card.cardState != 'active'}">
                    <img src="${imagePath}/cards_type/icon_cards_${imgCode}32Blocked.jpg">
                </c:when>
                <c:otherwise>
                    <img src="${imagePath}/cards_type/icon_cards_${imgCode}32.gif">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="left">
            <div class="titleBlock">
                <c:set var="linkName"><c:out value="${cardLink.name}"/></c:set>
                <span id="productTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                <div class="lightness"></div>
            </div>
            <c:set var="formattedNumber" value="${phiz:getCutCardNumber(cardLink.number)}"/>
            <span class="productNumber">${phiz:changeWhiteSpaces(formattedNumber)}</span>
        </div>
        <div class="right">
            <span class="prodStatus bold">${phiz:formatAmount(cardLink.card.availableLimit)}</span>
        </div>
    </div>
</c:if>