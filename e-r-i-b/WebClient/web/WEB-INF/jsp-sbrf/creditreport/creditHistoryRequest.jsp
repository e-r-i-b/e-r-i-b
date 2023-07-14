<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%--
    providerId - провайдер
    cost - стоимость отчета
    waitingNew - отчет ожидается
--%>
<tiles:importAttribute/>
<c:choose>
    <c:when test="${waitingNew}">
        <div class="we-are-preparing-cr-history-right">
            <img src="${globalUrl}/commonSkin/images/clock.png" alt="" />
            <div class="we-are-preparing-cr-history-text-right">
                Мы готовим Вашу обновлённую кредитную историю
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="b-get-cred-hist-up-rep">
            <div class="b-get-cred-history-up-top">Узнайте Ваш кредитный рейтинг прямо сейчас — обновите кредитный отчёт</div>
            <div class="b-get-cred-hist-up-rep-button">
                <phiz:link action="/private/payments/creditReportPayment/edit">
                    <phiz:param name="recipient" value="${providerId}"/>
                    <c:out value="Обновить отчет"/>
                </phiz:link>
            </div>
            <div class="b-get-cred-history-up-bottom">стоимость ${phiz:formatDecimalToAmountRound(cost.value,true)} ${phiz:getCurrencySignForBKI(cost.currency)}</div>
        </div>
    </c:otherwise>
</c:choose>
