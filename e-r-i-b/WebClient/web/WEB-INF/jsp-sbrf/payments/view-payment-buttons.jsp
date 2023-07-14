<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="document" value="${form.document}"/>
<c:set var="isSecuritiesTransferClaim" value="${metadata.form.name == 'SecuritiesTransferClaim'}"/>
<c:set var="isShortLoanClaim" value="${metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="state" value="${document.state.code}"/>
<c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                          || isSecuritiesTransferClaim
                                          || metadata.form.name == 'DepositorFormClaim'
                                          || metadata.form.name == 'RecallDepositaryClaim'}"/>

<c:set var="isNewRurPayment" value="${metadata.form.name == 'NewRurPayment'}"/>
<c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'
                                          || metadata.form.name == 'LoanCardOffer'
                                          || metadata.form.name == 'LoanProduct'
                                          || metadata.form.name == 'LoanCardProduct'
                                          || metadata.form.name == 'ShortLoanClaim'}"/>

<c:set var="depoAccountLink" value="${isDepositaryClaim ? phiz:getDepoAccountLinkFromDocument(document) : null}"/>
<c:set var="isLongOffer" value="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and document.longOffer}"/>
<c:set var="isAutoSub" value="${isLongOffer && metadata.form.name == 'RurPayJurSB' || metadata.form.name == 'CreateP2PAutoTransferClaim'}"/>
<c:set var="isEditAutoSub" value="${metadata.form.name == 'EditAutoSubscriptionPayment' || metadata.form.name == 'EditAutoPaymentPayment' || metadata.form.name == 'EditMoneyBoxClaim' || metadata.form.name == 'EditP2PAutoTransferClaim'}"/>
<c:set var="isModifyAutoSub" value="${metadata.form.name == 'CloseAutoSubscriptionPayment'
                                   || metadata.form.name == 'DelayAutoSubscriptionPayment'
                                   || metadata.form.name == 'RecoveryAutoSubscriptionPayment'
                                   || metadata.form.name == 'DelayP2PAutoTransferClaim'
                                   || metadata.form.name == 'RecoveryP2PAutoTransferClaim'
                                   || metadata.form.name == 'CloseP2PAutoTransferClaim'
                                   }"/>
<c:set var="isTemplate" value="${form.document.template}"/>
<c:set var="isRefuseAutoPaymentPayment" value="${metadata.form.name == 'RefuseAutoPaymentPayment' or metadata.form.name == 'RefuseLongOffer'}"/>
<c:set var="isShortLoanClaim"  value="${metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="isExtendedLoanClaim" value="${metadata.form.name == 'ExtendedLoanClaim'}"/>
<c:set var="isPreapprovedLoanCardClaim" value="${metadata.form.name == 'PreapprovedLoanCardClaim'}"/>
<c:set var="isLoanCardClaim" value="${metadata.form.name == 'LoanCardClaim'}"/>
<c:set var="isMoneyBoxSupported" value="${metadata.form.name!='AccountOpeningClaim' && phiz:isMoneyBoxSupported(document)}"/>
<c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment' || metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
<c:set var="isShowOnlyConnectionUDBO" value="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"/>
<c:set var="isReportByCardClaim" value="${metadata.form.name == 'ReportByCardClaim'}"/>
<c:set var="isEarlyLoanRepaymentClaim" value="${metadata.form.name =='EarlyLoanRepaymentClaim'}"/>
<c:set var="isChangeCreditLimitClaim" value="${metadata.form.name == 'ChangeCreditLimitClaim'}"/>
<c:choose>
   <c:when test="${isDepositaryClaim}">
       <c:set var="mainmenuButton" value="Depo"/>
   </c:when>
   <c:when test="${isTemplate || isLongOffer || isEditAutoSub || isAutoSubChange || isRefuseAutoPaymentPayment}">
       <c:set var="mainmenuButton" value=""/>
   </c:when>
   <c:when test="${isShortLoanClaim or isExtendedLoanClaim}">
       <c:set var="mainmenuButton" value="Loans"/>
   </c:when>
   <c:when test="${isPreapprovedLoanCardClaim || isLoanCardClaim}">
       <c:set var="mainmenuButton" value="Cards"/>
   </c:when>
   <c:otherwise>
       <c:set var="mainmenuButton" value="Payments"/>
   </c:otherwise>
</c:choose>
<c:set var="external" value="${form.externalPayment}"/>
<c:set var="fns" value="${form.fnsPayment}"/>
<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>

<c:set var="isClaim" value="${metadata.form.name == 'BlockingCardClaim'
                                || metadata.form.name == 'RefuseLongOffer'
                                || metadata.form.name == 'AccountClosingPayment'
                                || metadata.form.name == 'AccountOpeningClaim'
                                || metadata.form.name == 'AccountOpeningClaimWithClose'
                                || metadata.form.name == 'LoyaltyProgramRegistrationClaim'
                                || metadata.form.name == 'ReIssueCardClaim'
                                || metadata.form.name == 'RefundGoodsClaim'
                                || metadata.form.name == 'RollbackOrderClaim'
                                || metadata.form.name == 'ChangeDepositMinimumBalanceClaim'
                                || metadata.form.name == 'AccountChangeInterestDestinationClaim'
                                || metadata.form.name == 'IMAOpeningClaim'
                                || metadata.form.name == 'CreateMoneyBoxPayment'
                                || metadata.form.name == 'EditMoneyBoxClaim'}" scope="page"/>

<c:set var="isLossPassbook" value="${metadata.form.name == 'LossPassbookApplication'}"/>

<c:set var="isAutoPayment" value="${metadata.form.name == 'EditAutoPaymentPayment'
                                    || metadata.form.name == 'CreateAutoPaymentPayment'
                                    || metadata.form.name == 'RefuseAutoPaymentPayment'}"/>

<%-- флажок, говорящий о том, можно ли создать по документу шаблон --%>
<c:set var="supportedActions"  value="${phiz:getDocumentSupportedActions(document)}"/>
<c:set var="templateSupported"  value="${supportedActions['isTemplateSupported']}"/>
<c:set var="allowedStates"      value="${(state == 'EXECUTED' || state == 'DISPATCHED' || state == 'DELAYED_DISPATCH' || state == 'WAIT_CONFIRM')}"/>
<c:set var="isEnableCreateTemplate" value="${allowedStates && phiz:impliesTemplateOperation(document.formName) && templateSupported}" scope="page"/>
<c:set var="hasPfpId" value="${(metadata.form.name == 'AccountOpeningClaim' || metadata.form.name == 'IMAOpeningClaim') && not empty form.document.pfpId}"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="isITunes" value="${phiz:isITunesDocument(form.document)}"/>


<%-- для шинных автоплатежей не отображаем кнопку печати --%>
<c:if test="${form.printCheck == true && !isLongOffer && !isNewRurPayment && metadata.form.name != 'RemoteConnectionUDBOClaim' && !isEarlyLoanRepaymentClaim}">
    <div class="immitate">
        <div class="paymentLabel"></div>
        <div class="paymentValue">
        <%--<c:if test="${(form.intermediate == true || form.successed == true) && (form.metadata.name == 'RurPayment' || form.metadata.name == 'RurPayJurSB')}">--%>
        <tiles:insert definition="clientButton" flush="false">
            <c:choose>
                <c:when test="${form.metadata.name == 'ExtendedLoanClaim' or form.metadata.name == 'ReportByCardClaim'}">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="commandTextKey" value="button.printCheck"/>
                    <tiles:put name="commandHelpKey" value="button.printCheck.help"/>
                </c:otherwise>
            </c:choose>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="onclick" value="printCheck(${form.id}, event);"/>
            <tiles:put name="image" value="print-check.gif"/>
            <tiles:put name="imageHover" value="print-check-hover.gif"/>
            <tiles:put name="imagePosition" value="left"/>
        </tiles:insert>
        </div>
    </div>
</c:if>

<c:if test="${isLoanOffer || form.metadata.name == 'VirtualCardClaim' || form.metadata.name == 'ReIssueCardClaim'}">
    <div class="immitate">
        <div class="paymentLabel"></div>
        <div class="paymentValue">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.loanOffer.print"/>
                <tiles:put name="commandHelpKey" value="button.loanOffer.print.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="printLoan(${form.id}, event);"/>
                <tiles:put name="image" value="print-check.gif"/>
                <tiles:put name="imageHover" value="print-check-hover.gif"/>
                <tiles:put name="imagePosition" value="left"/>
            </tiles:insert>
        </div>
    </div>
</c:if>

<c:if test="${metadata.form.name == 'PreapprovedLoanCardClaim'}">
    <div class="immitate">
        <table>
            <tr>
                <td class="Width255 LabelAll"></td>
                <td>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print"/>
                        <tiles:put name="commandHelpKey" value="button.print.help"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="printLoan(${form.id}, event);"/>
                        <tiles:put name="image" value="print-check.gif"/>
                        <tiles:put name="imageHover" value="print-check-hover.gif"/>
                        <tiles:put name="imagePosition" value="left"/>
                    </tiles:insert>
                </td>
            </tr>
        </table>
    </div>
</c:if>

<c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim' && form.printCheck == true}">
    <div class="immitate">
        <div class="paymentLabel"></div>
        <div class="paymentValue">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.print"/>
                <tiles:put name="commandHelpKey" value="button.print"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="printCheck(${form.id}, event);"/>
                <tiles:put name="image" value="print-check.gif"/>
                <tiles:put name="imageHover" value="print-check-hover.gif"/>
                <tiles:put name="imagePosition" value="left"/>
            </tiles:insert>
        </div>
    </div>
</c:if>

<c:if test="${hasPfpId}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.toEditPfpPortfolio"/>
        <tiles:put name="commandHelpKey" value="button.toEditPfpPortfolio.help"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${document.pfpId}&portfolioId=${document.pfpPortfolioId}"/>
    </tiles:insert>
</c:if>

<%--
<c:if test="${form.canWithdraw}">
    <tiles:insert definition="clientButton" service="RecallPayment" flush="false">
        <tiles:put name="commandTextKey" value="button.withdraw"/>
        <tiles:put name="commandHelpKey" value="button.withdraw.help"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="image" value="iconSm_withdraw.gif"/>
        <tiles:put name="action"
           value="/private/payments/add.do?form=RecallPayment&parentId=${form.id}"/>
    </tiles:insert>
</c:if>

<tiles:insert definition="commandButton" flush="false">
    <tiles:put name="commandKey"     value="button.edit"/>
    <tiles:put name="commandHelpKey" value="button.edit.help"/>
    <tiles:put name="image"          value="iconSm_edit.gif"/>
    <tiles:put name="bundle"         value="paymentsBundle"/>
    <tiles:put name="stateObject"    value="document"/>
</tiles:insert>
--%>

<div class="buttonsArea <c:if test="${metadata.form.name != 'RemoteConnectionUDBOClaim' && metadata.form.name !='EarlyLoanRepaymentClaim'}">dividerArea</c:if> ${isITunes?'rowITunes':''}" style="${isITunes?'padding-top:10px; border-bottom:none;':''}"/>
   <%-- Кнопку "Отозвать" не отображаем, если стутус документа "Исполнен" (BUG051747, BUG048646) --%>
   <c:if test="${(isSecuritiesTransferClaim
                   || form.metadata.name == 'SecurityRegistrationClaim'
                   || form.metadata.name == 'DepositorFormClaim')
                       && state != 'EXECUTED'
                       && state != 'RECALLED'
                       && depoAccountLink.depoAccount.state != 'closed'}">
       <tiles:insert definition="clientButton" service="RecallDepositaryClaim" flush="false">
           <tiles:put name="commandTextKey" value="button.withdraw"/>
           <tiles:put name="commandHelpKey" value="button.withdraw.help"/>
           <tiles:put name="bundle" value="paymentsBundle"/>
           <tiles:put name="onclick"  value="tryRecall(${form.id});"/>
       </tiles:insert>
    </c:if>

   <c:if test="${state == 'OFFLINE_DELAYED'}">
           <tiles:insert definition="confirmationButton" flush="false">
               <tiles:put name="winId" value="recallConfirmation"/>
               <tiles:put name="title" value="Подтверждение отзыва документа"/>
               <tiles:put name="confirmCommandKey" value="button.withdraw"/>
               <tiles:put name="currentBundle" value="paymentsBundle"/>
               <tiles:put name="buttonViewType" value="blueGrayLink"/>
               <tiles:put name="message"><bean:message key="confirm.recall.text" bundle="paymentsBundle"/></tiles:put>
           </tiles:insert>
    </c:if>

    <c:if test="${((form.metadata.name != 'RecallPayment'
                && !isAutoPayment
                && !isLossPassbook
                && !isClaim
                && !external
                && !isDepositaryClaim
                && !isLoanOffer
                && !isPreapprovedLoanCardClaim
                && !isLoanCardClaim
                && !isReportByCardClaim
                && form.metadata.name != 'PFRStatementClaim'
                && form.metadata.name != 'GetPrivateOperationScanClaim'
                && form.metadata.name != 'VirtualCardClaim'
                && form.metadata.name != 'IMAOpeningClaim'
                && metadata.form.name != 'NewRurPayment'
                && form.metadata.name != 'CreditReportPayment'
                && form.metadata.name != 'EarlyLoanRepaymentClaim')
                    || (isSecuritiesTransferClaim && depoAccountLink.depoAccount.state != 'closed'))
                        && !isAutoSub
                        && !isEditAutoSub
                        && !isModifyAutoSub
                        && form.metadata.name != 'LoyaltyProgramRegistrationClaim'}">
        <c:choose>
            <c:when test="${isDepositaryClaim}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.addCopyDepo"/>
                    <tiles:put name="commandHelpKey" value="button.addCopyDepo"/>
                    <tiles:put name="bundle"         value="paymentsBundle"/>
                    <tiles:put name="onclick"        value="addCopy(${form.id});"/>
                    <tiles:put name="viewType"       value="buttonGrey"/>
                    <tiles:put name="image"          value="repeat-payment.png"/>
                    <tiles:put name="imageHover"     value="repeat-payment-hover.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </c:when>
            <c:when test="${supportedActions['isCopyAllowed']}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.addCopy"/>
                    <tiles:put name="commandHelpKey" value="button.addCopy"/>
                    <tiles:put name="bundle"         value="paymentsBundle"/>
                    <tiles:put name="onclick"        value="addCopy(${form.id});"/>
                    <tiles:put name="viewType"       value="buttonGrey"/>
                    <tiles:put name="image"          value="repeat-payment.png"/>
                    <tiles:put name="imageHover"     value="repeat-payment-hover.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </c:when>
        </c:choose>
    </c:if>

<c:if test="${isEnableCreateTemplate && form.externalAccountPaymentAllowed && metadata.form.name != 'NewRurPayment'}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.saveAsTemplate"/>
            <tiles:put name="commandHelpKey" value="button.saveAsTemplate"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <tiles:put name="image"          value="saveAsTemplate.png"/>
            <tiles:put name="imageHover"     value="saveAsTemplate-hover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
            <tiles:put name="viewType"       value="buttonGrey"/>
            <tiles:put name="onclick"        value="openTemplateRow();"/>
        </tiles:insert>

        <c:if test="${phiz:impliesService('ReminderManagment')}">
            <c:set var="reminderBundleKey" value="button.${metadata.form.name == 'RurPayJurSB' ? 'payment' : 'transfer'}.saveAsReminder"/>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="${reminderBundleKey}"/>
                <tiles:put name="commandHelpKey" value="${reminderBundleKey}"/>
                <tiles:put name="bundle"         value="reminderBundle"/>
                <tiles:put name="image"          value="bellBtn.png"/>
                <tiles:put name="imageHover"     value="bellBtn-hover.png"/>
                <tiles:put name="imagePosition"  value="left"/>
                <tiles:put name="viewType"       value="buttonGrey"/>
                <tiles:put name="onclick"        value="openReminderRow();"/>
            </tiles:insert>
        </c:if>
    </c:if>

<c:set var="isNewRurPayment" value="${metadata.form.name == 'NewRurPayment'}"/>
    <c:if test="${!isNewRurPayment && supportedActions['isAutoPaymentAllowed']}">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey"     value="button.makeLongOffer"/>
            <tiles:put name="commandHelpKey" value="button.addAutoPayment.help"/>
            <tiles:put name="commandTextKey" value="button.addAutoPayment"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <tiles:put name="viewType"       value="buttonGrey"/>
            <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/connect-autopayment.png"/>
            <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/connect-autopayment-hover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
    </c:if>
    <c:if test="${!isNewRurPayment && supportedActions['isAutoTransferAllowed']}">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey"     value="button.makeAutoTransfer"/>
            <tiles:put name="commandHelpKey" value="button.addAutoPayment.help"/>
            <tiles:put name="commandTextKey" value="button.addAutoPayment"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <tiles:put name="viewType"       value="buttonGrey"/>
            <tiles:put name="imagePosition"  value="left"/>
            <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/connect-autopayment.png"/>
            <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/connect-autopayment-hover.png"/>
        </tiles:insert>
    </c:if>

    <c:if test="${isMoneyBoxSupported}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.makeMoneyBox"/>
            <tiles:put name="commandHelpKey" value="button.makeMoneyBox.help"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <tiles:put name="onclick"        value="openCreateMoneyBoxWindowByResources('${document.chargeOffResourceLink.code}','${document.destinationResourceLink.code}', '${not empty document.exactAmount? document.exactAmount.decimal: 0}');"/>
            <tiles:put name="viewType"       value="buttonGrey"/>
            <tiles:put name="imageHover"     value="moneyBoxHover"/>
        </tiles:insert>
    </c:if>

    <c:if test="${mode != null and mode == 'PAYORDER_PAYMENT' and external}">
        <c:if test="${fns}">
            <c:set var="countFnsDocuments" value="${phiz:getAllCountFnsDocuments(person)}"/>
        </c:if>

        <c:set var="backUrl" value="${document.extendedAttributes['backUrl']}"/>
        <%--<c:set var="staticBackUrl" value="${document.extendedAttributes['staticBackUrl']}"/>--%>
        <c:set var="backUrlAction" value="${document.extendedAttributes['backUrlAction']}"/>

        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="${fns and countFnsDocuments > 0 ? 'button.listFNS' : 'button.exit'}"/>
            <tiles:put name="commandHelpKey" value="${fns and countFnsDocuments > 0 ? 'button.listFNS.help' : 'button.exit.help'}"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <c:choose>
                <c:when test="${fns and countFnsDocuments > 0}">
                     <tiles:put name="action" value="/external/fns/list.do"/>
                </c:when>
                <c:when test="${ (backUrlAction eq true) && (not empty backUrl)}">
                    <c:set var="logoffUrl" value="${phiz:calculateActionURL(pageContext,'/logoff')}"/>
                    <tiles:put name="onclick">
                        ajaxQuery(null,'${logoffUrl}', function(){},null,false);
                        window.location = '${backUrl}';
                    </tiles:put>
                </c:when>
                <c:otherwise>
                    <tiles:put name="action" value="/external/payments/system/end.do"/>
                    <tiles:put name="enabled" value="${(form.metadata.name == 'AirlineReservationPayment' and (state == 'DISPATCHED' or state == 'WAIT_CONFIRM')) ? false : true}"/>
                </c:otherwise>
            </c:choose>
        </tiles:insert>
    </c:if>

    <c:if test="${form.metadata.name == 'PFRStatementClaim' and state == 'EXECUTED'}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.viewStatement"/>
            <tiles:put name="commandHelpKey" value="button.viewStatement"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <tiles:put name="image"          value=""/>
            <tiles:put name="onclick"        value="viewStatement(${form.id}, this);"/>
        </tiles:insert>
    </c:if>

    <c:if test="${supportedActions['isSupportCreateInvoiceSubscription'] && phiz:impliesOperation('CreateInvoiceSubscriptionDocumentOperation', 'CreateInvoiceSubscriptionPayment')}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.makeInvoiceSubscription"/>
            <tiles:put name="commandHelpKey" value="button.makeInvoiceSubscription.help"/>
            <tiles:put name="bundle"         value="paymentsBundle"/>
            <tiles:put name="viewType"       value="buttonGrey"/>
            <tiles:put name="onclick"        value="makeInvoiceSubscription();"/>
            <tiles:put name="image"          value="autoSearchIcon.png"/>
            <tiles:put name="imageHover"     value="autoSearchIconHover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
    </c:if>

    <%--
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.editTemplate"/>
        <tiles:put name="commandHelpKey" value="button.editTemplate"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="image"  value="iconSm_copy.gif"/>
        <tiles:put name="onclick" value="saveTemplate(${form.id});"/>
    </tiles:insert>
    --%>
</div>

<div class="additionalButtonBlock">
    <c:choose>
        <c:when test="${not empty param['internetOrder'] and param['internetOrder']=='true' and (phiz:isInternetShopOrAeroflotPayment(document))}">
            <div class="float">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.prev.internetShops"/>
                    <tiles:put name="commandHelpKey" value="button.prev.internetShops.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="action" value="/private/payments/internetShops/orderList"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                </tiles:insert>
            </div>
        </c:when>
        <c:when test="${(mode != null and mode == 'PAYORDER_PAYMENT' and external) or metadata.form.name == 'CreditReportPayment' or isNewP2P or isEarlyLoanRepaymentClaim}">
            <%--  никаких кнопок здесь не нужно --%>
        </c:when>
        <c:when test="${isDepositaryClaim && depoAccountLink != null}">
            <div class="float">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.documentsList"/>
                    <tiles:put name="commandHelpKey" value="button.documentsList.help"/>
                    <tiles:put name="bundle"         value="paymentsBundle"/>
                    <tiles:put name="viewType"       value="blueGrayLink"/>
                    <tiles:put name="action"         value="/private/depo/documents.do?id=${depoAccountLink.id}"/>
                </tiles:insert>
            </div>
        </c:when>
        <c:when test="${metadata.form.name != 'RefuseLongOffer' and (isClaim or isLossPassbook) and phiz:impliesService('PaymentList')}">
            <div class="floatBackLink">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.historyOper"/>
                    <tiles:put name="commandHelpKey" value="button.historyOper.help"/>
                    <tiles:put name="bundle"         value="paymentsBundle"/>
                    <tiles:put name="viewType"       value="darkGrayButton"/>
                    <tiles:put name="action"         value="/private/payments/common.do?status=all"/>
                    <tiles:put name="image"          value="back-to-catalog.png"/>
                    <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </div>
        </c:when>
        <c:when test="${isShortLoanClaim or isExtendedLoanClaim or (mode == 'GUEST' && isLoanCardClaim)}">
            <div class="float">
                <span id="backToServicesButton">
                    <c:choose>
                        <c:when test="${phiz:isGuest()}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.backToMain"/>
                                <tiles:put name="commandHelpKey" value="button.backToMain"/>
                                <tiles:put name="bundle"         value="sbnkdBundle"/>
                                <tiles:put name="viewType"       value="darkGrayButton"/>
                                <tiles:put name="action"         value="/guest/index.do"/>
                                <tiles:put name="image"          value="back-to-catalog.png"/>
                                <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                                <tiles:put name="imagePosition"  value="left"/>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.loans"/>
                                <tiles:put name="commandHelpKey" value="button.loans.help"/>
                                <tiles:put name="bundle"         value="paymentsBundle"/>
                                <tiles:put name="viewType"       value="darkGrayButton"/>
                                <tiles:put name="action"         value="/private/loans/list.do"/>
                                <tiles:put name="image"          value="back-to-catalog.png"/>
                                <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                                <tiles:put name="imagePosition"  value="left"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </span>
            </div>
        </c:when>
        <c:when test="${metadata.form.name == 'PreapprovedLoanCardClaim'}">
            <div class="backLinkDivider"></div>
                <span id="backToServicesButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cards"/>
                        <tiles:put name="commandHelpKey" value="button.cards.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="viewType"       value="darkGrayButton"/>
                        <tiles:put name="action"         value="/private/cards/list.do"/>
                        <tiles:put name="image"          value="back-to-catalog.png"/>
                        <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </span>
        </c:when>
        <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
            <div class="backLinkDivider"></div>
                <span id="backToServicesButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.loans"/>
                        <tiles:put name="commandHelpKey" value="button.loans.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="viewType"       value="darkGrayButton"/>
                        <tiles:put name="action"         value="/private/accounts"/>
                        <tiles:put name="image"          value="back-to-catalog.png"/>
                        <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </span>
        </c:when>
        <c:when test="${isReportByCardClaim || isChangeCreditLimitClaim}">
            <div class="float">
                <span id="backToServicesButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.goToCard"/>
                        <tiles:put name="commandHelpKey" value="button.goToCard.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="viewType"       value="darkGrayButton"/>
                        <tiles:put name="action"         value="/private/cards/info.do?id=${document.cardId}"/>
                        <tiles:put name="image"          value="back-to-catalog.png"/>
                        <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </span>
            </div>
        </c:when>
        <c:otherwise>
            <div class="float">
                <span id="backToServicesButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.history"/>
                        <tiles:put name="commandHelpKey" value="button.history.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="viewType"       value="darkGrayButton"/>
                        <tiles:put name="action"         value="/private/payments.do"/>
                        <tiles:put name="image"          value="back-to-catalog.png"/>
                        <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </span>
            </div>
        </c:otherwise>
    </c:choose>

    <!--не отображаем кнопку если не зашли через историю операций -->
    <div class="floatRight">
        <c:if test="${state=='WAIT_CONFIRM' and not empty param.history and param.history eq 'true' and not empty document.id}">
            <tiles:insert definition="confirmationButton" flush="false">
                <tiles:put name="winId" value="confirmation"/>
                <tiles:put name="title" value="Подтверждение удаления документа"/>
                <tiles:put name="currentBundle"  value="commonBundle"/>
                <tiles:put name="confirmCommandKey" value="button.remove"/>
                <tiles:put name="buttonViewType" value="buttonRed"/>
                <tiles:put name="message"><bean:message key="confirm.text" bundle="paymentsBundle"/></tiles:put>
                <tiles:put name="buttonImage"          value="removeIcon.png"/>
                <tiles:put name="urlImageHover"     value="removeIconHover.png"/>
                <tiles:put name="iconPosition"  value="left"/>
            </tiles:insert>
        </c:if>
    </div>
</div>

