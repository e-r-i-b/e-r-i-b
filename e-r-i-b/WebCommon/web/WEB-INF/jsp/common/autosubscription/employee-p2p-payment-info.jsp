<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>

<c:set var="payment"        value="${form.payment}"/>
<c:set var="cardLink"       value="${form.cardLink}"/>
<c:set var="subscription"   value="${form.subscriptionLink.value}"/>

<fieldset>
    <table class="additional-product-info">
        <tr>
            <td colspan="2">
                <h2 class="additionalInfoHeader fontForH2">Получатель</h2>
            </td>
        </tr>
        <tr>
            <td class="text-align-right field">Получатель:</td>
            <td>
                <c:set var="type" value="${subscription.type.simpleName}"/>
                <span class="bold">
                    <c:if test="${'ExternalCardsTransferToOurBankLongOffer' == type || 'ExternalCardsTransferToOtherBankLongOffer' == type}">
                        <c:out value="${subscription.receiverName}"/><br/>
                        <c:if test="${not empty payment.receiverCard}">
                            <c:out value="${phiz:getCutCardNumber(payment.receiverCard)}"/>
                        </c:if>
                    </c:if>
                    <c:if test="${'InternalCardsTransferLongOffer' == type}">
                        <c:if test="${not empty payment.receiverCard}">
                            <c:set var="toCardLink" value="${phiz:getCardLink(payment.receiverCard)}"/>
                            <c:if test="${not empty cardLink}">
                                <span class="bold word-wrap">
                                    <c:out value="${phiz:getCutCardNumber(toCardLink.number)}"/>
                                    &nbsp;
                                    <c:out value="${toCardLink.name}"/>
                                    &nbsp;
                                    <c:out value="${phiz:getCurrencySign(toCardLink.currency.code)}"/>
                                </span>
                            </c:if>
                        </c:if>
                    </c:if>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <h2 class="additionalInfoHeader fontForH2">Плательщик</h2>
            </td>
        </tr>
        <tr>
            <td class="text-align-right field" width="254">Карта списания:</td>
            <td>
                <span class="bold word-wrap">
                    <c:if test="${not empty cardLink}">
                        <c:out value="${phiz:getCutCardNumber(payment.chargeOffCard)}"/>
                        &nbsp;
                        <c:out value="${cardLink.name}"/>
                        &nbsp;
                        <c:out value="${phiz:getCurrencySign(cardLink.rest.currency.code)}"/>
                    </c:if>
                </span>
            </td>
        </tr>
        <tr>
            <td class="text-align-right field">Дата операции:</td>
            <td><span class="bold"><c:out value="${phiz:formatDateDependsOnSysDate(payment.executionDate, true, false)}"/></span></td>
        </tr>
        <c:if test="${not empty payment.amount}">
            <tr>
                <td class="text-align-right field">Сумма операции:</td>
                <td><span class="bold"><c:out value="${phiz:formatAmount(payment.amount)}"/></span></td>
            </tr>
        </c:if>
        <c:if test="${not empty payment.commission}">
            <tr>
                <td class="text-align-right field">Комиссия:</td>
                <td><span class="bold"><c:out value="${phiz:formatAmount(payment.commission)}"/></span></td>
            </tr>
        </c:if>
        <tr>
            <td class="text-align-right field">Статус:</td>
            <td><span class="bold">Исполнен</span></td>
        </tr>
        <tr>
            <td class="text-align-right field"><br><br></td>
            <td></td>
        </tr>
    </table>
</fieldset>