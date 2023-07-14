<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="cardLink" value="${phiz:getCardLink(cardNumber)}"/>

<c:choose>
    <c:when test="${cardLink == null}">
        <c:if test="${showCardName != 'false'}">
           <div class="linkName">
               <span class="word-wrap">${cardName}</span>
           </div>
        </c:if>
        <div class="grayText">
            ${phiz:getCutCardNumber(cardNumber)}
        </div>
    </c:when>
    <c:otherwise>
        <c:set var="userCardNumber" value="${phiz:getCutCardNumber(cardLink.number)}"/>
        <c:set var="userCardName" value="${phiz:getCardUserName(cardLink)}"/>
        <c:if test="${showCardName != 'false'}">
            <div class="linkName">
                <span class="word-wrap"><bean:write name="userCardName"/></span>
            </div>
        </c:if>
        <div class="grayText">
            <bean:write name="userCardNumber"/>
        </div>
    </c:otherwise>
</c:choose>    

