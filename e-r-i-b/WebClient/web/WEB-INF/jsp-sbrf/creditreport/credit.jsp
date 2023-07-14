<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%--
    credit - информация по кредиту
    isDetail - детальная информация
--%>
<tiles:importAttribute/>
<div class="credit-history-credit-item-color-${blockColor} credit-history-credit-item-blue credit-history-credit-item credit-history-item-${credit.width} css3" <c:if test="${!isDetail}">onclick="selectCredit(${credit.id});"</c:if>>
    <c:if test="${credit.arrears.value!=null and credit.arrears.value!=0}">
        <div class="credit-history-item-red-info"></div>
    </c:if>
    <div class="credit-history-item-bank"><c:out value="${credit.bankName}"/></div>
    <div class="text-overflow">
        <span class="credit-history-item-title"><c:out value="${credit.name}"/></span>
        <span class="credit-history-item-credit-size"><c:out value="${credit.amount}"/></span>
        <span class="credit-history-item-credit-time"><c:out value="${credit.duration}"/></span>
    </div>

    <div class="credit-history-item-info text-overflow">
        <div class="credit-history-item-info-col">
            <div class="credit-history-item-info-col-title">ВСЕГО К ВЫПЛАТЕ</div>
            <div class="credit-history-item-info-col-value"><c:out value="${credit.balance}"/></div>
        </div>
        <div class="credit-history-item-info-col">
            <div class="credit-history-item-info-col-title"><c:out value="${credit.monthRequest}"/></div>
            <c:choose>
                <c:when test="${credit.arrears == '' or credit.arrears != '' and credit.arrears != null and credit.arrears.value == 0}">
                    <div class="credit-history-item-info-col-value"><c:out value="${credit.instalment}"/></div>
                </c:when>
                <c:otherwise>
                    <div class="credit-history-item-info-col-value red"><c:out value="${credit.instalment}"/> <span
                            class="credit-history-item-info-col-value-note">Включая просрочку <c:out value="${credit.arrears}"/></span></div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:if test="${isDetail}">
        <a id="showDetail" class="cred-hist-item-contract-show" onclick="hideOrShowCreditDetail(false);"><span>Показать информацию о договоре</span></a>
        <a id="hideDetail" class="cred-hist-item-contract-show" onclick="hideOrShowCreditDetail(true);"><span>Скрыть информацию о договоре</span></a>
    </c:if>
    <div class="credit-history-status-line" <c:if test="${credit.informer != 0}">title="Кредит выплачен на ${credit.informer}%"</c:if>>
        <div class="credit-history-status-line-value" style="width: ${credit.informer}%"></div>
    </div>
</div>
