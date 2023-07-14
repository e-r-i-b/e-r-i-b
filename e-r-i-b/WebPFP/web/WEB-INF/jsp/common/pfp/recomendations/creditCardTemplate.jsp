<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<div class="choosePfpCreditCardBlock <c:if test="${not empty isLast && isLast}">lastPfpCreditCardBlock</c:if> <c:if test="${isChoose == 'true'}">clickedPfpCreditCard</c:if>" onclick="${click}">
    <div class="pfpCreditCardInfoBlock">
        <div class="float">
            <img src="${imgUrl}">
        </div>
        <div class="pfpCreditCardName">
            <c:out value="${title}"/>
        </div>
    </div>
    <div class="chooseBackgroundTriangle"></div>
    <div class="chooseTriangle"></div>
</div>
<c:if test="${not empty isLast && isLast}">
    <div class="clear"></div>
</c:if>


