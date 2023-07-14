<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>

<c:if test="${not empty payment}">
    <c:set var="bank" value="${payment.receiverBank}"/>
    <c:set var="isCardArrested" value="${form.autoSubscriptionCardState != null && form.autoSubscriptionCardState == 'ARRESTED'}"/>
    <fieldset>
        <table class="additional-product-info">
            <tr>
                <td class="text-align-right field"> <bean:message bundle="autoPaymentInfoBundle" key="label.receiver"/>:</td>
                <td><span class="bold"><c:out value="${phiz:getReceiverNameForViewViaDetailInfo(payment)}"/></span>  <br/>
                    <span class="italicInfo">${phiz:getCutCardNumber(payment.receiverAccount)}
                </td>
            </tr>
            <tr>
                <td class="text-align-right field"><bean:message bundle="autoPaymentInfoBundle" key="label.from.card"/>:</td>
                <td>
                    <span class="bold word-wrap">
                        <c:if test="${ not empty cardLink}">
                            <c:out value="${phiz:getCutCardNumber(payment.chargeOffCard)}"/>&nbsp;
                            <c:out value="${cardLink.name}"/>&nbsp;<c:out value="${phiz:getCurrencySign(cardLink.rest.currency.code)}"/>
                        </c:if>
                    </span>
                </td>
            </tr>

            <tr>
                <td class="text-align-right field"></td>
                <td>
                    <span class="bold"><bean:message bundle="autoPaymentInfoBundle" key="label.executionEvent"/>:</span><br/>
                    <c:out value="${autoSubscriptionLink.startExecutionDetail}"/>
                    <c:if test="${not empty payment.nextPayDate}">
                       <br/><bean:message bundle="autoPaymentInfoBundle" key="label.next"/>: <span class="bold"><bean:write name="payment" property="nextPayDate.time" format="dd.MM.yyyy" filter="true"/> </span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="text-align-right field"><bean:message bundle="autoPaymentInfoBundle" key="label.amount"/>:</td>
                <td><span class="bold"><c:out value="${phiz:formatAmount(payment.amount)}"/></span></td>
            </tr>


            <tr>
                <td class="text-align-right field"><bean:message bundle="autoPaymentInfoBundle" key="label.autopaymentName"/>:</td>
                <td><span class="bold word-wrap"><c:out value="${payment.friendlyName}"/></span></td>
            </tr>
            <c:if test="${payment.messageToRecipient != '' && (paymentType == 'ExternalCardsTransferToOurBankLongOffer' || paymentType == 'ExternalCardsTransferToOtherBankLongOffer')}">
                <tr>
                    <td class="text-align-right field"><bean:message bundle="autoPaymentInfoBundle" key="label.messageToRecipient"/>:</td>
                    <td><span class="bold word-wrap"><c:out value="${payment.messageToRecipient}"/></span></td>
                </tr>
            </c:if>
            <c:if test="${not empty paymentGraphicMenu}">
                ${paymentGraphicMenu}
            </c:if>
        </table>
    </fieldset>
</c:if>

<div id="autoPaymentStatus" class="layerFon hintWindow autoPaymentHint">
</div>

<div class="buttonsArea">
    <c:set var="autoPayStatusType" value="${autoSubscriptionLink.autoPayStatusType}"/>

    <%--редактировать автоплатеж--%>
    <c:if test="${(autoPayStatusType == 'Active' || autoPayStatusType == 'Paused') && !isCardArrested}">
            <tiles:insert definition="clientButton" service="EditP2PAutoTransferClaim" operation="CreateFormPaymentOperation" flush="false">
                <tiles:put name="commandTextKey"    value="button.edit"/>
                <tiles:put name="commandHelpKey"    value="button.edit"/>
                <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
                <tiles:put name="action"            value="private/payments/payment.do?form=EditP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
            </tiles:insert>
    </c:if>

    <%--возобновить выполнение автоплатежа--%>
    <c:if test="${autoPayStatusType == 'Paused' && !isCardArrested}">
        <tiles:insert definition="clientButton" service="RecoveryP2PAutoTransferClaim" flush="false">
            <tiles:put name="commandTextKey"    value="button.restart"/>
            <tiles:put name="commandHelpKey"    value="button.restart.help"/>
            <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
            <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
            <tiles:put name="action"            value="private/payments/payment.do?form=RecoveryP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
        </tiles:insert>
    </c:if>

    <%--приостановить выполнение автоплатежа--%>
    <c:if test="${autoPayStatusType == 'Confirmed' || autoPayStatusType == 'ErrorRegistration' || autoPayStatusType == 'Active'}">
        <tiles:insert definition="clientButton" service="DelayP2PAutoTransferClaim" flush="false">
            <tiles:put name="commandTextKey"    value="button.pause"/>
            <tiles:put name="commandTextKey"    value="button.pause"/>
            <tiles:put name="commandHelpKey"    value="button.pause.help"/>
            <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
            <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
            <tiles:put name="action"            value="private/payments/payment.do?form=DelayP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
        </tiles:insert>
    </c:if>

    <%--закрыть автоплатеж--%>
    <c:if test="${autoPayStatusType == 'Confirmed' || autoPayStatusType == 'ErrorRegistration' || autoPayStatusType == 'Active' || autoPayStatusType == 'Paused'}">
        <tiles:insert definition="clientButton" service="CloseP2PAutoTransferClaim" flush="false">
            <tiles:put name="commandTextKey"    value="button.close"/>
            <tiles:put name="commandHelpKey"    value="button.close.help"/>
            <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
            <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
            <tiles:put name="action"            value="private/payments/payment.do?form=CloseP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
        </tiles:insert>
    </c:if>
</div>
