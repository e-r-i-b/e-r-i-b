<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<%--
    card - информация по карте
    isDetail - детальная информация
--%>
<tiles:importAttribute/>
<div class="credit-history-credit-item-color-${blockColor} credit-history-credit-item credit-history-credit-item-red cred-hist-item-cart css3" <c:if test="${!isDetail}">onclick="selectCredit(${card.id});"</c:if>>
    <c:if test="${card.arrears.value!=null and card.arrears.value!=0}">
        <div class="credit-history-item-red-info"></div>
    </c:if>
    <div class="credit-history-item-bank">${card.bankName}</div>
    <div class="credit-history-item-title text-overflow" title="${card.name}">${card.name}</div>
    <span class="credit-history-item-credit-size">${card.creditLimit}</span>

    <div class="credit-history-item-info nowrapWhiteSpace">
        <div class="credit-history-item-info-col width80">
            <div class="credit-history-item-info-col-title">ДОЛГ</div>
            <div class="credit-history-item-info-col-value  text-overflow" title="${card.balance}">${card.balance}</div>
        </div>
        <div class="credit-history-item-info-col-divider"></div>
        <div class="credit-history-item-info-col width80">
            <div class="credit-history-item-info-col-title">МИН. ПЛАТЕЖ</div>
            <c:choose>
                <c:when test="${card.instalment != ''}">
                    <c:set var="installment" value="${card.instalment}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="installment">0&nbsp;<bean:message key="currency.rub" bundle="financesBundle"/></c:set>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${card.arrears == '' or card.arrears != '' and card.arrears != null and card.arrears.value == 0}">
                    <div class="credit-history-item-info-col-value text-overflow" title="${installment}">${installment}</div>
                </c:when>
                <c:otherwise>
                    <div class="credit-history-item-info-col-value text-overflow red" title="${installment}">${installment}</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:if test="${card.arrears != '' and card.arrears.value != 0}">
        <span class="credit-history-item-info-col-value-note">
            Включая просрочку ${phiz:formatDecimalToAmountRound(card.arrears.value, true)}
            <span class="rouble">
                ${phiz:getCurrencySignForBKI(card.arrears.currency)}
            </span>
        </span>
    </c:if>
    <div class="credit-history-credit-item-red-bg1 css3"></div>
    <div class="credit-history-credit-item-red-bg2 css3"></div>
</div>

<c:if test="${isDetail}">
    <div class="cred-hist-item-cart-detail css3">
        <a id="showDetail" class="cred-hist-item-contract-show" onclick="hideOrShowCreditDetail(false);">
            <span>Показать информацию</span>
            <span>о договоре</span>
        </a>
        <a id="hideDetail" class="cred-hist-item-contract-show contract-hide" onclick="hideOrShowCreditDetail(true);">
            <span>Скрыть информацию</span>
            <span>о договоре</span>
        </a>
    </div>
</c:if>
