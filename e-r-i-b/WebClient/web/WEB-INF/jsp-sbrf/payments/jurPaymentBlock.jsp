<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:url var="jurPayAction" value="/private/payments/jurPayment/edit.do"/>
<div class="popularPaymentsBlock">
    <c:if test="${not isSearch}">
        <div class="receiptDotted"></div>
    </c:if>
    <div class="paymentOnReceipt" onclick="window.location = '${jurPayAction}';">
        <a class="popularPaymentsLink">
            <span>Не нашли подходящий<br /> раздел, но знаете<br /> реквизиты?</span>
        </a>
    </div>
</div>