<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<script type="text/javascript">
    function HintBlock()
    {
        var autoPaymentHint = $('#autoPaymentStatus');
        var parentText = $('.hintText');
        autoPaymentHint.css("margin-left", parentText.width()/2 - autoPaymentHint.width()/2);
    }
</script>

<fieldset>
    <table class="additional-product-info">
        <c:if test="${not empty payment.autopayNumber}">
            <tr>
                <td class="text-align-right field">Номер автоплатежа:</td>
                <td>
                    <span class="bold"><c:out value="${payment.autopayNumber}"/></span>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2">
                <h2 class="additionalInfoHeader fontForH2">Получатель</h2>
            </td>
        </tr>
        <tr>
            <td class="text-align-right field">Получатель:</td>
            <td>
                <c:set var="type" value="${payment.type.simpleName}"/>
                <span class="bold">
                    <c:if test="${'ExternalCardsTransferToOurBankLongOffer' == type || 'ExternalCardsTransferToOtherBankLongOffer' == type}">
                        <c:out value="${phiz:getReceiverNameForViewViaDetailInfo(payment)}"/><br/>
                        <c:if test="${not empty payment.receiverCard}">
                            <c:out value="${phiz:getCutCardNumber(payment.receiverCard)}"/>
                        </c:if>
                    </c:if>
                    <c:if test="${'InternalCardsTransferLongOffer' == type}">
                        <c:if test="${not empty payment.receiverCard}">
                            <c:set var="toCardLink" value="${phiz:getCardLink(payment.receiverCard)}"/>
                            <c:choose>
                                <c:when test="${not empty toCardLink}">
                                    <span class="bold word-wrap">
                                        <c:out value="${phiz:getCutCardNumber(toCardLink.number)}"/>
                                        &nbsp;
                                        <c:out value="${toCardLink.name}"/>
                                        &nbsp;
                                        <c:out value="${phiz:getCurrencySign(toCardLink.currency.code)}"/>
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="bold word-wrap">
                                        <c:out value="${phiz:getCutCardNumber(payment.receiverCard)}"/>
                                    </span>
                                </c:otherwise>
                            </c:choose>
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
            <td class="text-align-right field"></td>
            <td>
                <span class="bold">Платеж осуществляется:</span><br/>
                <c:out value="${autoSubscriptionLink.startExecutionDetail}"/>
                <c:if test="${not empty payment.nextPayDate}">
                    <br/>Ближайший:<span class="bold"><bean:write name="payment" property="nextPayDate.time" format="dd.MM.yyyy" filter="true"/> </span>
                </c:if>
            </td>
        </tr>
        <c:if test="${not empty payment.updateDate}">
            <tr>
                <td class="text-align-right field">Дата изменения:</td>
                <td>
                    <span class="bold">
                        <bean:write name="payment" property="updateDate.time" format="dd.MM.yyyy" filter="true"/>
                    </span>
                </td>
            </tr>
        </c:if>
        <tr>
            <td class="text-align-right field">Сумма:</td>
            <td><span class="bold"><c:out value="${phiz:formatAmount(payment.amount)}"/></span></td>
        </tr>
        <tr>
            <td class="text-align-right field">Наименование автоплатежа:</td>
            <td><span class="bold word-wrap"><c:out value="${payment.friendlyName}"/></span></td>
        </tr>
        <tr>
            <td class="text-align-right field">Статус автоплатежа:</td>
            <td>
                <c:if test="${not empty payment.autoPayStatusType}">
                    <script type="text/javascript">
                        var textForHint = "<div class=\"floatMessageHeader\"></div><div class=\"layerFonBlock\">${payment.reasonDescription}</div>";
                    </script>
                    <div id="stateDescriptionAutoPayment" class="hintText" style="width:40px;"
                         onmouseover="showHint('stateDescriptionAutoPayment', 'autoPaymentStatus', 'pointer', textForHint); HintBlock()"
                         onmouseout="hideLayer('autoPaymentStatus');">
                        <c:out value="${payment.autoPayStatusType.description}"/>
                    </div>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="text-align-right field"><br><br></td>
            <td></td>
        </tr>
    </table>
</fieldset>

