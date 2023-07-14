<%@ page contentType="charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- Откуда, счет карта вклад --%>
<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
    <c:if test="${not empty businessDocument.chargeOffAccount}">
        <c:choose>
            <c:when test="${businessDocument.chargeOffResourceType == 'CARD'}">
                <tiles:insert page="history-card-title.jsp" flush="false">
                    <tiles:put name="cardNumber" value="${businessDocument.chargeOffAccount}"/>
                    <tiles:put name="cardName" value="${businessDocument.fromAccountName}"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert page="history-account-title.jsp" flush="false">
                    <tiles:put name="accountNumber" value="${businessDocument.chargeOffAccount}"/>
                    <tiles:put name="accountName" value="${businessDocument.fromAccountName}"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    </c:if>
</c:if>
<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.BlockingCardClaim')}">
    <c:if test="${not empty businessDocument.cardNumber}">
        <tiles:insert page="history-card-title.jsp" flush="false">
            <tiles:put name="cardNumber" value="${businessDocument.cardNumber}"/>
            <tiles:put name="cardName" value="${businessDocument.fromAccountName}"/>
        </tiles:insert>
    </c:if>
</c:if>
<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LossPassbookApplication')}">
    <c:if test="${not empty businessDocument.depositAccount}">
        <tiles:insert page="history-account-title.jsp" flush="false">
            <tiles:put name="accountNumber" value="${businessDocument.depositAccount}"/>
            <tiles:put name="accountName" value="${businessDocument.fromAccountName}"/>
        </tiles:insert>
    </c:if>
</c:if>
