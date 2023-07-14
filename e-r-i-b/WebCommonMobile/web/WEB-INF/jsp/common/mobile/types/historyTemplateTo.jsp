<%@ page contentType="charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%-- Куда --%>
<c:choose>
    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.InternalTransfer')}">
        <c:choose>
            <c:when test="${businessDocument.destinationResourceType == 'CARD'}">
                <tiles:insert page="history-card-title.jsp" flush="false">
                    <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                    <tiles:put name="cardName" value="${businessDocument.toAccountName}"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert page="history-account-title.jsp" flush="false">
                    <tiles:put name="accountNumber" value="${businessDocument.receiverAccount}"/>
                    <tiles:put name="accountName" value="${businessDocument.toAccountName}"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
        <c:set var="isRurPayment" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RurPayment')}"/>
        <c:choose>
            <c:when test="${isRurPayment and businessDocument.receiverType eq 'ph'}">
                <c:set var="receiverFirstName" value="${businessDocument.receiverFirstName}"/>
                <c:set var="receiverSurName"   value="${businessDocument.receiverSurName}"/>
                <c:set var="receiverPatrName"  value="${businessDocument.receiverPatrName}"/>

                <c:out value="${phiz:getFormattedPersonName(receiverFirstName, receiverSurName, receiverPatrName)}"/>
                <c:choose>
                    <c:when test="${businessDocument.destinationResourceType == 'EXTERNAL_CARD' or businessDocument.destinationResourceType == 'CARD'}">
                        ${phiz:getCutCardNumber(businessDocument.receiverAccount)}
                    </c:when>
                    <c:otherwise>
                        ${businessDocument.receiverAccount}
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:out value="${businessDocument.receiverName}"/>
                <c:if test="${businessDocument.formName ne 'RurPayJurSB'}">
                    ${businessDocument.receiverAccount}
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:when>
</c:choose>