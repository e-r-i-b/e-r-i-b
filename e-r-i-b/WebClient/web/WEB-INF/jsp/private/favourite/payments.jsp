<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<html:form action="/private/async/favourite/list/list-auto-payments" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="activePaymentsCount" value="${phiz:size(form.activePayments)}"/>
    <c:set var="waitingConfirmPaymentsCount" value="${phiz:size(form.waitingConfirmPayments)}"/>
    <c:set var="suspendedPaymentsCount" value="${phiz:size(form.suspendedPayments)}"/>
    <c:set var="archivePaymentsCount" value="${phiz:size(form.archivePayments)}"/>
    <%--автоплажеи карта-карта--%>
    <c:set var="activeP2PPaymentsCount" value="${phiz:size(form.activeP2PPayments)}"/>
    <c:set var="waitingConfirmP2PPaymentsCount" value="${phiz:size(form.waitingConfirmP2PPayments)}"/>
    <c:set var="suspendedP2PPaymentsCount" value="${phiz:size(form.suspendedP2PPayments)}"/>
    <c:set var="archiveP2PPaymentsCount" value="${phiz:size(form.archiveP2PPayments)}"/>

    <c:set var="longOfferInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/longOffers/info?id=')}"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/favourite/list/list-auto-payments')}"/>
    <c:set var="saveUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/favourite/save/auto-payments')}"/>
    <c:set var="autoPaymentInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/autopayments/info?id=')}"/>
    <c:set var="autoSubsctiptionUrl" value="${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/info?id=')}"/>
    <c:set var="faqLinkRegularPayments" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm17')}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

    <c:set var="refuseLongOfferUrl" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=RefuseLongOffer')}&longOfferId="/>
    <c:set var="EditAutoSubscriptionPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=EditAutoSubscriptionPayment')}&autoSubNumber="/>
    <c:set var="CloseAutoSubscriptionURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=CloseAutoSubscriptionPayment')}&autoSubNumber="/>
    <c:set var="RecoveryAutoSubscriptionPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=RecoveryAutoSubscriptionPayment')}&autoSubNumber="/>
    <c:set var="DelayAutoSubscriptionPaymentUrl" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=DelayAutoSubscriptionPayment')}&autoSubNumber="/>
    <c:set var="EditAutoPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=EditAutoPaymentPayment&linkId=')}"/>
    <c:set var="refuseAutoPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=RefuseAutoPaymentPayment&linkId=')}"/>

    <c:set var="impliesRefuseLongOffer" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'RefuseLongOffer')}"/>
    <c:set var="impliesEditAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'EditAutoSubscriptionPayment')}"/>
    <c:set var="impliesCloseAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'CloseAutoSubscriptionPayment')}"/>
    <c:set var="impliesRecoveryAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'RecoveryAutoSubscriptionPayment')}"/>
    <c:set var="impliesDelayAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'DelayAutoSubscriptionPayment')}"/>
    <c:set var="impliesEditAutoPaymentPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'EditAutoPaymentPayment')}"/>
    <c:set var="impliesRefuseAutoPaymentPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'RefuseAutoPaymentPayment')}"/>

    <c:set var="RecoveryCardToCardAutoSubscriptionPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=RecoveryP2PAutoTransferClaim')}&autoSubNumber="/>
    <c:set var="impliesRecoveryCardToCardAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'RecoveryP2PAutoTransferClaim')}"/>

    <c:set var="DelayCardToCardAutoSubscriptionPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=DelayP2PAutoTransferClaim')}&autoSubNumber="/>
    <c:set var="impliesDelayCardToCardAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'DelayP2PAutoTransferClaim')}"/>

    <c:set var="CloseCardToCardAutoSubscriptionPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=CloseP2PAutoTransferClaim')}&autoSubNumber="/>
    <c:set var="impliesCloseCardToCardAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'CloseP2PAutoTransferClaim')}"/>

    <c:set var="editP2PAutoSubscriptionPaymentURL" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=EditP2PAutoTransferClaim')}&autoSubNumber="/>
    <c:set var="impliesEditP2PAutoSubscriptionPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'EditP2PAutoTransferClaim')}"/>

    <c:if test="${not empty form.productId}">
        <c:set var="url">${url}?productId=<c:out value="${form.productId}"/>&card=<c:out value="${form.card}"/></c:set>
    </c:if>

    <c:choose>
        <c:when test="${activePaymentsCount + waitingConfirmPaymentsCount + suspendedPaymentsCount + archivePaymentsCount + activeP2PPaymentsCount + archiveP2PPaymentsCount + waitingConfirmP2PPaymentsCount + suspendedP2PPaymentsCount > 0}">
            <div class="smallTitlePosition">
                <div class="titleItemsSmall float">
                    <h2>Мои автоплатежи</h2>
                </div>

                <c:if test="${phiz:impliesOperation('CreateESBAutoPayOperation','ClientCreateAutoPayment') || phiz:impliesOperation('CreateAutoPaymentOperation','CreateAutoPaymentPayment')}">
                    <div class="floatRight">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"     value="button.addAutoPayment"/>
                            <tiles:put name="commandHelpKey" value="button.addAutoPayment"/>
                            <tiles:put name="bundle"         value="paymentsBundle"/>
                            <tiles:put name="action" value="/private/autopayment/select-category-provider.do"/>
                        </tiles:insert>
                    </div>
                </c:if>
            </div>
            <div class="clear"></div>
            <c:if test="${waitingConfirmP2PPaymentsCount + waitingConfirmPaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3>Ожидают подтверждения</h3>
                </div>
            </c:if>
            <c:if test="${waitingConfirmP2PPaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3><bean:message key="label.autopayment.on.card" bundle="favouriteBundle"/></h3>
                </div>

                <tiles:insert definition="regularPaymentTable"  flush="false">
                    <tiles:put name="type" value="p2p"/>
                    <tiles:put name="fieldName" value="waitingConfirmP2PPayments"/>
                    <tiles:put name="paymentsCount" value="${waitingConfirmP2PPaymentsCount}"/>
                    <tiles:put name="state" value="WaitForAccept"/>
                    <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                    <tiles:put name="EditAutoSubscriptionPaymentURL" value="${editP2PAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="CloseAutoSubscriptionURL" value="${CloseCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                    <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                    <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditP2PAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                    <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                    <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                    <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                    <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                    <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${waitingConfirmPaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3><bean:message key="label.autopayment.for.service" bundle="favouriteBundle"/></h3>
                </div>
                <tiles:insert definition="regularPaymentTable" flush="false">
                    <tiles:put name="fieldName" value="waitingConfirmPayments"/>
                    <tiles:put name="paymentsCount" value="${waitingConfirmPaymentsCount}"/>
                    <tiles:put name="state" value="WaitForAccept"/>
                    <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayAutoSubscriptionPaymentUrl}"/>
                    <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                    <tiles:put name="EditAutoSubscriptionPaymentURL" value="${EditAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="CloseAutoSubscriptionURL" value="${CloseAutoSubscriptionURL}"/>
                    <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                    <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                    <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                    <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                    <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                    <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                    <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                    <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${activeP2PPaymentsCount + activePaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3>Активные автоплатежи</h3>
                </div>
            </c:if>
            <c:if test="${activeP2PPaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3><bean:message key="label.autopayment.on.card" bundle="favouriteBundle"/></h3>
                </div>
                <tiles:insert definition="regularPaymentTable" flush="false">
                    <tiles:put name="type" value="p2p"/>
                    <tiles:put name="fieldName" value="activeP2PPayments"/>
                    <tiles:put name="paymentsCount" value="${activeP2PPaymentsCount}"/>
                    <tiles:put name="state" value="Active"/>
                    <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                    <tiles:put name="EditAutoSubscriptionPaymentURL" value="${editP2PAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="CloseAutoSubscriptionURL" value="${CloseCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                    <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                    <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditP2PAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                    <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                    <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                    <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                    <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                    <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${activePaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3><bean:message key="label.autopayment.for.service" bundle="favouriteBundle"/></h3>
                </div>
                <tiles:insert definition="regularPaymentTable" flush="false">
                    <tiles:put name="fieldName" value="activePayments"/>
                    <tiles:put name="paymentsCount" value="${activePaymentsCount}"/>
                    <tiles:put name="state" value="Active"/>
                    <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayAutoSubscriptionPaymentUrl}"/>
                    <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                    <tiles:put name="EditAutoSubscriptionPaymentURL" value="${EditAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="CloseAutoSubscriptionURL" value="${CloseAutoSubscriptionURL}"/>
                    <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                    <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                    <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                    <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                    <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                    <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                    <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                    <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${suspendedP2PPaymentsCount + suspendedPaymentsCount> 0}">
                <div class="titleItems smallTitlePosition">
                    <h3>Приостановленные автоплатежи</h3>
                </div>
            </c:if>
            <c:if test="${suspendedP2PPaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3><bean:message key="label.autopayment.on.card" bundle="favouriteBundle"/></h3>
                </div>
                <tiles:insert definition="regularPaymentTable" flush="false">
                    <tiles:put name="type" value="p2p"/>
                    <tiles:put name="fieldName" value="suspendedP2PPayments"/>
                    <tiles:put name="paymentsCount" value="${suspendedP2PPaymentsCount}"/>
                    <tiles:put name="state" value="Paused"/>
                    <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                    <tiles:put name="EditAutoSubscriptionPaymentURL" value="${editP2PAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="CloseAutoSubscriptionURL" value="${CloseCardToCardAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                    <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                    <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditP2PAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                    <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryCardToCardAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                    <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                    <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                    <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                    <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${suspendedPaymentsCount > 0}">
                <div class="titleItems smallTitlePosition">
                    <h3><bean:message key="label.autopayment.for.service" bundle="favouriteBundle"/></h3>
                </div>

                <tiles:insert definition="regularPaymentTable" flush="false">
                    <tiles:put name="fieldName" value="suspendedPayments"/>
                    <tiles:put name="paymentsCount" value="${suspendedPaymentsCount}"/>
                    <tiles:put name="state" value="Paused"/>
                    <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayAutoSubscriptionPaymentUrl}"/>
                    <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                    <tiles:put name="EditAutoSubscriptionPaymentURL" value="${EditAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryAutoSubscriptionPaymentURL}"/>
                    <tiles:put name="CloseAutoSubscriptionURL" value="${CloseAutoSubscriptionURL}"/>
                    <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                    <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                    <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                    <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryAutoSubscriptionPayment}"/>
                    <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                    <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                    <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                    <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                    <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${archiveP2PPaymentsCount + archivePaymentsCount > 0}">
                <tiles:insert definition="archivePaymentsBlock" flush="false">
                    <tiles:put name="title" value="Архив автоплатежей"/>
                    <tiles:put name="blockId" value="archivePayments"/>
                    <tiles:put name="data">
                        <c:if test="${archiveP2PPaymentsCount > 0}">
                            <div class="titleItems smallTitlePosition">
                                <h3><bean:message key="label.autopayment.on.card" bundle="favouriteBundle"/></h3>
                            </div>
                            <tiles:insert definition="regularPaymentTable" flush="false">
                                <tiles:put name="type" value="p2p"/>
                                <tiles:put name="fieldName" value="archiveP2PPayments"/>
                                <tiles:put name="paymentsCount" value="${archiveP2PPaymentsCount}"/>
                                <tiles:put name="state" value="Archive"/>
                                <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayCardToCardAutoSubscriptionPaymentURL}"/>
                                <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                                <tiles:put name="EditAutoSubscriptionPaymentURL" value="${editP2PAutoSubscriptionPaymentURL}"/>
                                <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryCardToCardAutoSubscriptionPaymentURL}"/>
                                <tiles:put name="CloseAutoSubscriptionURL" value="${CloseCardToCardAutoSubscriptionPaymentURL}"/>
                                <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                                <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                                <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditP2PAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseCardToCardAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayCardToCardAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                                <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryCardToCardAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                                <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                                <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                                <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                                <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${archivePaymentsCount > 0}">
                            <div class="titleItems smallTitlePosition">
                                <h3><bean:message key="label.autopayment.for.service" bundle="favouriteBundle"/></h3>
                            </div>
                            <tiles:insert definition="regularPaymentTable" flush="false">
                                <tiles:put name="fieldName" value="archivePayments"/>
                                <tiles:put name="paymentsCount" value="${archivePaymentsCount}"/>
                                <tiles:put name="state" value="Archive"/>
                                <tiles:put name="DelayAutoSubscriptionPaymentUrl" value="${DelayAutoSubscriptionPaymentUrl}"/>
                                <tiles:put name="EditAutoPaymentURL" value="${EditAutoPaymentURL}"/>
                                <tiles:put name="EditAutoSubscriptionPaymentURL" value="${EditAutoSubscriptionPaymentURL}"/>
                                <tiles:put name="RecoveryAutoSubscriptionPaymentURL" value="${RecoveryAutoSubscriptionPaymentURL}"/>
                                <tiles:put name="CloseAutoSubscriptionURL" value="${CloseAutoSubscriptionURL}"/>
                                <tiles:put name="refuseAutoPaymentURL" value="${refuseAutoPaymentURL}"/>
                                <tiles:put name="refuseLongOfferUrl" value="${refuseLongOfferUrl}"/>
                                <tiles:put name="impliesEditAutoSubscriptionPayment" value="${impliesEditAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesCloseAutoSubscriptionPayment" value="${impliesCloseAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesDelayAutoSubscriptionPayment" value="${impliesDelayAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesEditAutoPaymentPayment" value="${impliesEditAutoPaymentPayment}"/>
                                <tiles:put name="impliesRecoveryAutoSubscriptionPayment" value="${impliesRecoveryAutoSubscriptionPayment}"/>
                                <tiles:put name="impliesRefuseAutoPaymentPayment" value="${impliesRefuseAutoPaymentPayment}"/>
                                <tiles:put name="impliesRefuseLongOffer" value="${impliesRefuseLongOffer}"/>
                                <tiles:put name="autoSubsctiptionUrl" value="${autoSubsctiptionUrl}"/>
                                <tiles:put name="longOfferInfoUrl" value="${longOfferInfoUrl}"/>
                                <tiles:put name="autoPaymentInfoUrl" value="${autoPaymentInfoUrl}"/>
                            </tiles:insert>
                        </c:if>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="emptyText greenBlock">
                <c:if test="${phiz:impliesOperation('CreateESBAutoPayOperation','ClientCreateAutoPayment') || phiz:impliesOperation('CreateAutoPaymentOperation','CreateAutoPaymentPayment')}">
                    <div class="floatRight">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"     value="button.addAutoPayment"/>
                            <tiles:put name="commandHelpKey" value="button.addAutoPayment"/>
                            <tiles:put name="bundle"         value="paymentsBundle"/>
                            <tiles:put name="action" value="/private/autopayment/select-category-provider.do"/>
                        </tiles:insert>
                    </div>
                </c:if>
                <div class="clear">&nbsp;</div>
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <span class="normal relative">
                            <c:out value="${form.emptyListMessage}"/>
                        <br/>
                        <a href="" onclick="openFAQ('${faqLinkRegularPayments}'); return false;">Подробнее»</a></span>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:otherwise>
    </c:choose>
</html:form>

<script type="text/javascript">
    try
    {
        doOnLoad(function(){
            $( ".sort" ).sortable({
                items: "li:not(.listInfHeader)",
                helper : "clone",
                axis: "y",
                cancel : ".text-highlight",
                containment: "parent",
                tolerance: "pointer",
                placeholder: "ui-state-highlight",
                start: function( event, ui ) {
                    ui.helper.find(".listOfOperation").mouseout();
                    ui.helper.css('cursor','move');
                },
                update: function( event, ui ) {
                    var parameters = "";
                    $(this).find("li:not(.listInfHeader) input[type='hidden']").each(function(index){
                        parameters += ((index != 0) ? "&sortAutoPayments=" : "sortAutoPayments=") + $(this).val();
                    });
                    ajaxQuery(parameters, '${saveUrl}', function(data){});
                }});
        });
    } catch (e) { }
</script>