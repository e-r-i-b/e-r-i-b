<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<c:set var="mode"  value="${phiz:getUserVisitingMode()}"/>
<c:set var="form"  value="${phiz:currentForm(pageContext)}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                          || metadata.form.name == 'SecuritiesTransferClaim'
                                          || metadata.form.name == 'DepositorFormClaim'
                                          || metadata.form.name == 'RecallDepositaryClaim'}"/>
<c:set var="isClaim" value="${metadata.form.name == 'ReIssueCardClaim'
                               || metadata.form.name == 'LossPassbookApplication'
                               || metadata.form.name == 'IMAOpeningClaim'
                               || metadata.form.name == 'AccountOpeningClaim'
                               || metadata.form.name == 'AccountOpeningClaimWithClose'
                               || metadata.form.name == 'VirtualCardClaim'
                               || metadata.form.name == 'PFRStatementClaim'
                               || metadata.form.name == 'ChangeDepositMinimumBalanceClaim'
                               || metadata.form.name == 'AccountChangeInterestDestinationClaim'
                               || metadata.form.name == 'AccountClosingPayment'
                               || metadata.form.name == 'BlockingCardClaim'
                               || metadata.form.name == 'LoyaltyProgramRegistrationClaim'
                               || metadata.form.name == 'RefuseLongOffer'
                               || metadata.form.name == 'RemoteConnectionUDBOClaim'
                               || metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
<c:set var="isLongOffer" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and form.document.longOffer}"/>
<c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'}"/>
<c:set var="isLoanCardOffer" value="${metadata.form.name == 'LoanCardOffer'}"/>
<c:set var="isLoanProduct" value="${  metadata.form.name == 'LoanProduct' || metadata.form.name == 'LoanCardProduct'}"/>
<c:set var="depoLinkId" value="${param['depoLinkId']}"/>
<c:set var="isAutoSub" value="${isLongOffer && metadata.form.name == 'RurPayJurSB' || metadata.form.name == 'CreateP2PAutoTransferClaim'}"/>
<c:set var="isAutoSubParams" value="${isAutoSub && form.document.state.code == 'INITIAL_LONG_OFFER'}"/>
<c:set var="isEditAutoSub" value="${metadata.form.name == 'EditAutoSubscriptionPayment' || metadata.form.name == 'EditAutoPaymentPayment' || metadata.form.name == 'EditP2PAutoTransferClaim'}"/>
<c:set var="isDelayAutoSub"  value="${metadata.form.name == 'DelayAutoSubscriptionPayment' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
<c:set var="isCloseAutoSub"  value="${metadata.form.name == 'CloseAutoSubscriptionPayment' || metadata.form.name == 'CloseP2PAutoTransferClaim'}"/>
<c:set var="isRecoveryAutoSub"  value="${metadata.form.name == 'RecoveryAutoSubscriptionPayment' || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
<c:set var="isLoyaltyRegistration"  value="${metadata.form.name == 'LoyaltyProgramRegistrationClaim'}"/>
<c:set var="isChangeDepositMinimumBalance" value="${metadata.form.name == 'ChangeDepositMinimumBalanceClaim'}"/>
<c:set var="isAccountChangeInterestDestinationClaim" value="${metadata.form.name == 'AccountChangeInterestDestinationClaim'}"/>
<c:set var="isRefuseLongOffer" value="${metadata.form.name == 'RefuseLongOffer'}"/>

<c:set var="isAutoSubChange"  value="${metadata.form.name == 'CloseAutoSubscriptionPayment'
                                          || metadata.form.name == 'DelayAutoSubscriptionPayment'
                                          || metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"/>
<c:set var="isAutoSubP2PChange"  value="${metadata.form.name == 'CloseP2PAutoTransferClaim'
                                          || metadata.form.name == 'DelayP2PAutoTransferClaim'
                                          || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
<c:set var="isJurPayment"        value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
<c:set var="isShortLoanClaim"    value="${metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="isExtendedLoanClaim" value="${metadata.form.name == 'ExtendedLoanClaim'}"/>
<c:set var="isLoanCardClaim"     value="${metadata.form.name == 'LoanCardClaim'}"/>
<c:set var="isExtendedLoanClaimNeedCall" value="${metadata.form.name == 'ExtendedLoanClaim' and
          (form.document.state.code == 'INITIAL2' or
           form.document.state.code == 'INITIAL3' or
           form.document.state.code == 'INITIAL4' or
           form.document.state.code == 'INITIAL5' or
           form.document.state.code == 'INITIAL6' or
           form.document.state.code == 'INITIAL7' or
           form.document.state.code == 'INITIAL8' or
           form.document.state.code == 'SAVED')}"/>

<c:set var="isExtendedLoanClaimInitial7" value="${metadata.form.name == 'ExtendedLoanClaim' and form.document.state.code == 'INITIAL7'}"/>

<c:set var="commonImagePath"       value="${globalUrl}/commonSkin/images"/>
<c:set var="formName"              value="${metadata.form.name}"/>
<c:set var="isLoanCardClaim"       value="${formName == 'LoanCardClaim'}"/>
<c:set var="isCreditReportPayment" value="${formName == 'CreditReportPayment'}"/>
<c:set var="isShowOnlyConnectionUDBO" value="${metadata.form.name == 'RemoteConnectionUDBOClaim' && phiz:isShowOnlyConnectionUDBO()}"/>
<c:set var="isChangeCreditLimitClaim" value="${metadata.form.name == 'ChangeCreditLimitClaim'}"/>
<c:set var="isEarlyLoanRepaymentClaim" value="${metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
 <c:choose>
   <c:when test="${isDepositaryClaim}">
       <c:set var="mainmenuButton" value="Depo"/>
   </c:when>
   <c:when test="${isEditAutoSub || isAutoSubChange || isRefuseLongOffer  || isLongOffer || isAutoSubP2PChange}">
       <c:set var="mainmenuButton" value=""/>
   </c:when>
   <c:when test="${isShortLoanClaim || isExtendedLoanClaim}">
       <c:set var="mainmenuButton" value="Loans"/>
   </c:when>
   <c:otherwise>
       <c:set var="mainmenuButton" value="Payments"/>
   </c:otherwise>
</c:choose>

<c:set var="externalPayment" value="${guest ? false : form.externalPayment}"/>

<c:set var="hasPfpId" value="${(metadata.form.name == 'AccountOpeningClaim' || metadata.form.name == 'IMAOpeningClaim') && not empty form.document.pfpId}"/>

<div class="buttonsArea <c:if test="${formName == 'NewRurPayment'}">newButtonsArea</c:if> <c:if test="${isExtendedLoanClaimNeedCall}">questionButtonsArea</c:if> <c:if test="${isChangeCreditLimitClaim}">limitsButtonsArea</c:if>">
<c:if test="${formName == 'ExtendedLoanClaim'}">
    <input type="hidden" name="transactionToken" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
</c:if>
<c:if test="${metadata.form.name == 'RurPayment' and form.document.state.code == 'WAIT_CLIENT_MESSAGE' || metadata.form.name == 'CreateP2PAutoTransferClaim' and form.document.state.code == 'INITIAL_LONG_OFFER'}">
    <div class="backToService backToServiceBottom" style="float: left!important;">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.edit"/>
            <tiles:put name="commandHelpKey" value="button.edit.help"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
            <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
    </div>
</c:if>
<c:if test="${metadata.form.name == 'EarlyLoanRepaymentClaim'}">
    <div class="backToProduct">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.back.to.loan"/>
            <tiles:put name="commandHelpKey" value="button.back.to.loan"/>
            <tiles:put name="bundle" value="loansBundle"/>
            <tiles:put name="viewType" value="uppercaseType"/>
            <tiles:put name="action" value="private/loans/detail.do?id=${form.document.loanLinkId}"/>
            <tiles:put name="image"       value="backIcons2.png"/>
            <tiles:put name="imageHover"     value="backIconsHover2.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
    </div>

    <div class="newButtonsLeftArea">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandTextKey" value="button.placeAnOrder"/>
            <tiles:put name="commandHelpKey" value="button.placeAnOrder"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="stateObject" value="document"/>
            <tiles:put name="validationFunction" value="checkPayment()"/>
        </tiles:insert>
    </div>
</c:if>
<c:choose>
    <c:when test="${formName == 'FNSPayment'}">
        <c:set var="person" value="${phiz:getPersonInfo()}"/>
        <c:set var="countFnsDocuments" value="${phiz:getAllCountFnsDocuments(person)}"/>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.back"/>
            <tiles:put name="commandHelpKey" value="button.back.help"/>
            <tiles:put name="bundle" value="fnsBundle"/>
            <tiles:put name="action" value="${countFnsDocuments > 1 ? '/external/fns/list' : '/external/payments/system/end'}"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
    </c:when>
    <c:when test="${phiz:getUserVisitingMode() != 'PAYORDER_PAYMENT' and (formName == 'ExternalProviderPayment' or formName == 'AirlineReservationPayment')}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.back"/>
            <tiles:put name="commandHelpKey" value="button.back.help"/>
            <tiles:put name="bundle" value="claimsBundle"/>
            <tiles:put name="onclick" value="win.open('backOrCancelWindow');"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="backOrCancelWindow"/>
            <tiles:put name="data" type="string">
                <div class="messageContainer">
                    Отменить оплату текущего счета или отложить счет?
                </div>

                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.showOrderList"/>
                        <tiles:put name="commandHelpKey" value="button.showOrderList"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="action" value="/private/payments/internetShops/orderList"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancelOrder"/>
                        <tiles:put name="commandHelpKey" value="button.cancelOrder"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="onclick" value="cancelOrder();"/>
                        <tiles:put name="isDefault" value="true"/>
                    </tiles:insert>

                    <c:set var="cancelOrderUrl" value="${phiz:calculateActionURL(pageContext,'/private/payments/internetShops/orderCancel')}"/>
                    <script type="text/javascript">
                        function cancelOrder()
                        {
                            <c:choose>
                                <c:when test="${not empty form.orderId}">
                                    location.href = '${cancelOrderUrl}?orderId=${form.orderId}&pageType=view';
                                </c:when>
                                <c:when test="${not empty form.document.id}">
                                    location.href = '${cancelOrderUrl}?id=${form.document.id}&pageType=view';
                                </c:when>
                            </c:choose>
                            showOrHideWaitDiv(true);
                        }
                    </script>
                </div>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:when test="${mode != null && mode == 'PAYORDER_PAYMENT'}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel.link"/>
            <tiles:put name="commandHelpKey" value="button.cancel.link.help"/>
            <tiles:put name="bundle" value="fnsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="action" value="/external/payments/system/end"/>
        </tiles:insert>
    </c:when>
    <c:when test="${formName == 'NewRurPayment' || isShowOnlyConnectionUDBO || isChangeCreditLimitClaim}"></c:when>
    <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim' && form.document.state.code != 'INITIAL'}"></c:when>
    <c:when test="${metadata.form.name == 'ExtendedLoanClaim' && form.document.state.code != 'INITIAL'}"></c:when>
    <c:when test="${isExtendedLoanClaim && form.document.inWaitTM && not phiz:isAdminApplication()}"></c:when>
    <c:when test="${guest and not registeredGuest}"></c:when>
    <c:when test="${metadata.form.name == 'EarlyLoanRepaymentClaim'}"></c:when>
    <c:otherwise>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.back"/>
            <tiles:put name="commandHelpKey" value="button.back.help"/>
            <tiles:put name="bundle" value="claimsBundle"/>
            <c:choose>
                <c:when test="${guest}">
                    <tiles:put name="action" value="/guest/index"/>
                </c:when>
                <c:when test="${isDepositaryClaim}">
                    <tiles:put name="action" value="/private/depo/list"/>
                </c:when>
                <c:when test="${isAccountChangeInterestDestinationClaim}">
                    <tiles:put name="action" value="private/accounts/info.do?id=${form.document.accountId}"/>
                </c:when>
                <c:when test="${isShortLoanClaim || isExtendedLoanClaim}">
                    <tiles:put name="action" value="/private/loans/list"/>
                </c:when>
                <c:when test="${metadata.form.name == 'LoanCardProduct'}">
                    <tiles:put name="action" value="/private/cards/list"/>
                </c:when>
                <c:when test="${isLoanCardClaim}">
                    <tiles:put name="action" value="/private/cards/list"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="action" value="/private/payments"/>
                </c:otherwise>
            </c:choose>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
    </c:otherwise>
</c:choose>

<c:set var="buttonSaveText" value="button.save"/>
<c:set var="buttonSaveTextHelp" value="button.save.help"/>

<c:choose>
    <c:when test="${isDepositaryClaim || isLongOffer}">
        <c:set var="buttonBundle" value="commonBundle"/>
    </c:when>
    <c:otherwise>
        <c:set var="buttonBundle" value="paymentsBundle"/>
    </c:otherwise>
</c:choose>

<c:if test="${metadata.form.name == 'RurPayment' || metadata.form.name== 'InternalPayment' || metadata.form.name == 'JurPayment'}">
    <c:set var="buttonSaveText"     value="button.transfer"/>
    <c:set var="buttonSaveTextHelp" value="button.transfer.help"/>
</c:if>
<c:if test="${metadata.form.name == 'ConvertCurrencyPayment' || metadata.form.name == 'IMAPayment'}">
    <c:set var="buttonSaveText"     value="button.exchange"/>
    <c:set var="buttonSaveTextHelp" value="button.exchange.help"/>
</c:if>
<c:if test="${metadata.form.name == 'RurPayJurSB'}">
    <c:set var="buttonSaveText"     value="button.exit"/>
    <c:set var="buttonSaveTextHelp" value="button.exit"/>
</c:if>

<c:if test="${isClaim}">
    <c:choose>
        <c:when test="${metadata.form.name == 'ReIssueCardClaim'}">
            <c:set var="buttonSaveText"     value="button.reissue"/>
            <c:set var="buttonSaveTextHelp" value="button.reissue.help"/>
            <c:set var="buttonBundle"       value="claimsBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
            <c:set var="buttonSaveText" value="button.send"/>
            <c:set var="buttonSaveTextHelp" value="button.send.help"/>
        </c:when>
        <c:when test="${metadata.form.name == 'IMAOpeningClaim'}">
            <c:set var="buttonSaveText" value="button.confirm"/>
            <c:set var="buttonSaveTextHelp" value="button.confirm.help"/>
            <c:set var="buttonBundle"       value="claimsBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'BlockingCardClaim'}">
            <c:set var="buttonSaveText" value="button.block"/>
            <c:set var="buttonSaveTextHelp" value="button.block.help"/>
            <c:set var="buttonBundle"       value="claimsBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountOpeningClaimWithClose'}">
            <c:set var="buttonSaveText" value="button.text.open"/>
            <c:set var="buttonSaveTextHelp" value="button.text.open"/>
            <c:set var="buttonBundle"       value="claimsBundle"/>
            <c:set var="infoActionUrl">
                <c:choose>
                    <c:when test="${phiz:isUseCasNsiDictionaries()}">
                        ${phiz:calculateActionURL(pageContext, '/private/async/depositProduct/details.do?id=')}${form.document.accountType}&group=${form.document.accountGroup}&segment=${form.document.segment}
                    </c:when>
                    <c:otherwise>
                        ${phiz:calculateActionURL(pageContext, '/private/async/deposits/details.do?id=')}${form.document.accountId}&group=${form.document.accountGroup}&segment=${form.document.segment}
                    </c:otherwise>
                </c:choose>
            </c:set>
            <c:set var="depositDetailWinId" value="depositDetail"/>
            <script type="text/javascript">
                function showDetails()
                {
                    win.open('${depositDetailWinId}');
                    return false;
                }
            </script>

            <tiles:insert definition="window" flush="false">
                <tiles:put name="id"          value="${depositDetailWinId}" />
                <tiles:put name="loadAjaxUrl" value="${infoActionUrl}" />
            </tiles:insert>
        </c:when>
        <c:when test="${metadata.form.name == 'VirtualCardClaim'}">
            <c:set var="infoActionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/dictionaries/mobileOperators/list')}"/>
            <c:set var="moblieOperatorWinId" value="mobileOperatorId"/>
            <script type="text/javascript">
                function showMobileOperators()
                {
                    win.open('${moblieOperatorWinId}');
                    return false;
                }
            </script>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id"          value="${moblieOperatorWinId}" />
                <tiles:put name="loadAjaxUrl" value="${infoActionUrl}" />
            </tiles:insert>
        </c:when>
        <c:when test="${metadata.form.name == 'RefuseLongOffer' || (metadata.form.name == 'RemoteConnectionUDBOClaim' && form.document.state.code == 'INITIAL')}">
            <c:set var="buttonSaveText" value="button.next"/>
            <c:set var="buttonBundle" value="commonBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'PFRStatementClaim'}">
            <c:set var="buttonSaveText" value="button.send"/>
            <c:set var="buttonSaveTextHelp" value="button.send.help"/>
            <c:set var="buttonBundle"       value="claimsBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'ChangeDepositMinimumBalanceClaim' or metadata.form.name == 'AccountChangeInterestDestinationClaim'}">
            <c:set var="buttonSaveText"     value="button.next"/>
            <c:set var="buttonSaveTextHelp" value="button.next.help"/>
            <c:set var="buttonBundle"       value="commonBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'AccountClosingPayment'}">
            <c:set var="buttonSaveText" value="button.text.close"/>
            <c:set var="buttonSaveTextHelp" value="button.text.close"/>
            <c:set var="buttonBundle"   value="claimsBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim' && form.document.state.code != 'INITIAL'}">
            <c:set var="buttonSaveTextHelp" value="button.accept"/>
            <c:set var="buttonSaveText" value="button.accept"/>
            <c:set var="buttonBundle" value="commonBundle"/>
        </c:when>
        <c:when test="${metadata.form.name == 'LoyaltyProgramRegistrationClaim'}">
            <c:set var="buttonSaveText" value="button.send"/>
            <c:set var="buttonSaveTextHelp" value="button.send"/>
            <c:set var="buttonBundle" value="loyaltyBundle"/>
        </c:when>
        <c:otherwise>
            <c:set var="buttonSaveText" value="button.text.block"/>
            <c:set var="buttonBundle" value="claimsBundle"/>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${isLongOffer}">
    <c:set var="buttonSaveText"     value="button.save"/>
    <c:set var="buttonSaveTextHelp" value="button.save.help"/>
</c:if>
<c:if test="${isLoanOffer or isLoanCardOffer or metadata.form.name == 'VirtualCardClaim'}">
    <c:set var="buttonSaveText" value="button.send.help"/>
    <c:set var="buttonSaveTextHelp" value="button.send.help"/>
</c:if>
<c:if test="${isLoanProduct}">
    <c:set var="buttonSaveText" value="button.send.loanProduct"/>
    <c:set var="buttonSaveTextHelp" value="button.send.loanProduct.help"/>
</c:if>
<c:if test="${metadata.form.name == 'RefuseAutoPaymentPayment'}">
    <c:set var="buttonSaveText" value="button.next"/>
    <c:set var="buttonBundle" value="commonBundle"/>
</c:if>
<c:if test="${isAutoSub}">
    <c:set var="buttonSaveText"     value="button.next"/>
    <c:set var="buttonBundle" value="commonBundle"/>
</c:if>
<c:if test="${isEditAutoSub || isAutoSubChange || isAutoSubP2PChange}">
    <c:set var="buttonSaveText"     value="button.placeAnOrder"/>
    <c:set var="buttonSaveTextHelp" value="button.placeAnOrder"/>
</c:if>
<c:if test="${isAutoSubParams}">
    <c:set var="buttonSaveText"     value="button.connect"/>
    <c:set var="buttonSaveTextHelp" value="button.connect.help"/>
</c:if>
<c:if test="${isShortLoanClaim}">
    <c:set var="buttonSaveText"     value="button.getcredit"/>
    <c:set var="buttonSaveTextHelp" value="button.getcredit.help"/>
</c:if>
<c:if test="${isExtendedLoanClaim}">
    <c:set var="buttonSaveText"     value="button.further"/>
    <c:set var="buttonSaveTextHelp" value="button.further.help"/>
</c:if>
<c:if test="${metadata.form.name == 'LoanCardClaim'}">
    <c:set var="buttonSaveText" value="button.preapprovedLoanCard"/>
    <c:set var="buttonSaveTextHelp" value="button.preapprovedLoanCard.help"/>
</c:if>


<c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim' && form.document.state.code != 'INITIAL'}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.not.accept"/>
        <tiles:put name="commandHelpKey" value="button.not.accept.help"/>
        <tiles:put name="bundle" value="commonBundle"/>
        <tiles:put name="viewType" value="buttonGrey"/>
        <tiles:put name="action" value="/private/udbo/cancel"/>
    </tiles:insert>
</c:if>

<c:if test="${not empty form.document}">
    <c:if test="${isExtendedLoanClaimNeedCall}"><div class="btnNote"></c:if>
    <c:if test="${not (isExtendedLoanClaim && form.document.inWaitTM && not phiz:isAdminApplication()) and not isChangeCreditLimitClaim and not isEarlyLoanRepaymentClaim}">
        <tiles:insert definition="commandButton" flush="false">
            <c:choose>
                <c:when test="${formName == 'NewRurPayment'}">
                    <tiles:put name="commandTextKey" value="button.transfer"/>
                    <tiles:put name="commandHelpKey" value="button.transfer.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="commandTextKey" value="${buttonSaveText}"/>
                    <tiles:put name="commandHelpKey" value="${buttonSaveTextHelp}"/>
                    <tiles:put name="bundle" value="${buttonBundle}"/>
                </c:otherwise>
            </c:choose>
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="stateObject" value="document"/>
            <tiles:put name="validationFunction" value="checkPayment()"/>
            <c:if test="${not empty sessionScope['fromBanner']}">
                <tiles:put name="fromBanner" value="${sessionScope['fromBanner']}"/>
            </c:if>
        </tiles:insert>
    </c:if>
    <c:if test="${isChangeCreditLimitClaim}">
        <jsp:include page="../loans/changeCreditLimitWindow.jsp" flush="false"/>
        <script type="text/javascript">
            function  changeLimit(type){
                $("#feedbackType").val(type);
                if (type == 'ACCEPT')
                    new CommandButton('button.save').click('', false);
                else
                    win.open("changeCreditLimitMess");
            }
        </script>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.changeLimit"/>
            <tiles:put name="commandHelpKey" value="button.changeLimit"/>
            <tiles:put name="bundle" value="loanOfferBundle"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="onclick" value="changeLimit('ACCEPT')"/>
        </tiles:insert>
        <div class="cancelBtn">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.changeLimit.cancel"/>
                <tiles:put name="commandHelpKey" value="button.changeLimit.cancel"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
                <tiles:put name="bundle" value="loanOfferBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="onclick" value="changeLimit('CANCEL')"/>
            </tiles:insert>
        </div>
    </c:if>
    <c:if test="${phiz:isLoanClaimNeedCall(form.document)}">
        <div class="completeByPhone">
            <div class="orangeRoundNote css3">
                <i class="orange-note_arr"></i>
                <div class="orange-note_content">
                    <div>
                        <bean:message key="loan.callMe.infoMessage" bundle="commonBundle"/>
                        <%--Хотите продолжить заполнение заявки по телефону? Сотрудник банка Вам поможет--%>
                    </div>
                    <c:set var="fillByPhoneLink">
                        <bean:message key="loan.callMe.fillByPhoneLink" bundle="commonBundle"/>
                    </c:set>
                        <%--Заполнить по телефону--%>
                    <div class="callMeWindowLink note_link" title="${fillByPhoneLink}"
                         onclick="callFromBankWindowShow(false);" >
                        <%--onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"--%>
                        ${fillByPhoneLink}
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        </div>
    </c:if>
    <div class="clear"></div>
</c:if>

<c:if test="${(metadata.form.name == 'ExternalProviderPayment'
                || metadata.form.name == 'AirlineReservationPayment') && mode != 'PAYORDER_PAYMENT'}">
    <div class="so-shopOrderOperations">
        <div class="invoiceOperations">
            <html:hidden property="field(chooseDelayDateOrder)" name="form" styleId="chooseDelayDateOrder"/>
            <div class="delayButtonMargin">
                <a onclick="" class="delayButton delayIcon" id="delayButton">
                    <span class="blueGrayLinkDotted"><bean:message key="button.delay" bundle="basketBundle"/></span>
                </a>
            </div>
            <div class="removeButtonMargin">
                <a class="removeButton removeButtonIcon float" id="removeButtonLink">
                    <span class="redButton"><bean:message key="button.remove" bundle="basketBundle"/></span>
                </a>
                <div class="clear"></div>
                <span class="removeInvoiceHint">
                    <bean:message key="button.remove.hint" bundle="basketBundle"/>
                </span>
            </div>
            <tiles:insert definition="confirmationButton" flush="false">
                <tiles:put name="winId" value="deleteConfirmation${type}${trueId}"/>
                <tiles:put name="title"><bean:message key="message.confirm.delete.title" bundle="basketBundle"/></tiles:put>
                <tiles:put name="currentBundle"  value="basketBundle"/>
                <tiles:put name="confirmCommandKey" value="button.removeInvoice"/>
                <tiles:put name="message"><bean:message key="message.confirm.delete" bundle="basketBundle"/></tiles:put>
                <tiles:put name="afterConfirmFunction" value="cancelOrder();"/>
                <tiles:put name="openElementId" value="removeButtonLink"/>
            </tiles:insert>
        </div>
    </div>
    <script type="text/javascript">

        function delayOrder()
        {
            var delayDate = document.getElementById('chooseDelayDateOrder').value;
            var params = 'needSaveMessage=false&field(chooseDelayDateOrder)='+delayDate;
            executeOperation('delay',params);
        }

        function clearAllMessages()
        {
            removeAllMessages();
            removeAllErrors();
            $("#payment .form-row").each(function(){
                payInput.formRowClearError(this);
            });
        }

        function executeOperation(operationName, params)
        {

            ajaxQuery(
                    params + "&orderId=${form.document.orderUuid}&operation=button." + operationName,
                    "${phiz:calculateActionURL(pageContext, '/private/payments/internetShops/payment.do')}",
                    function(data)
                    {
                        clearAllMessages();

                        if (data.success)
                        {
                            if (data.messages.length > 0)
                                post("${phiz:calculateActionURL(pageContext, '/private/payments.do')}", {successMessage: data.messages[0]});
                            else
                                document.location.href = "${phiz:calculateActionURL(pageContext, '/private/payments/internetShops/orderList.do')}"
                        }

                        if(data.errors != null && data.errors.length > 0)
                        {
                            var errors = data.errors;
                            for(var i = 0; i <errors.length; i++)
                                addError(errors[i]);
                        }

                        if(data.errorFields != null && data.errorFields.length > 0)
                        {
                            var errorFields = data.errorFields;
                            for(var j = 0; j <errorFields.length; j++)
                                payInput.fieldError(errorFields[j].name, errorFields[j].value);
                        }

                        if(data.messages != null && data.messages.length > 0)
                        {
                            var messages = data.messages;
                            for(var k = 0; k <messages.length; k++)
                                addMessage(messages[k]);
                        }
                    },
                    "json"
            );
        }

        $(function(){
            var chooseDateElem = $("#delayButton").closest("div").get(0);
            $("#chooseDelayDateOrder")
                .datePicker({displayClose: true, altField: chooseDateElem, dateFormat: 'dd.mm.yyyy', startDate: new Date().asString('dd.mm.yyyy')})
                .unbind('change')
                .change(function(){
                    delayOrder();
                });
        });

    </script>
</c:if>
</div>

<c:if test="${(not isLongOffer and not isAutoPayment and not isChangeDepositMinimumBalance) and (metadata.form.name != 'LoanCardOffer' and metadata.form.name != 'LoanCardProduct' and
            metadata.form.name != 'LoanProduct' and !externalPayment and !isDepositaryClaim and
            metadata.form.name != 'JurPayment' and metadata.form.name != 'LoanOffer' and metadata.form.name != 'NewRurPayment' and metadata.form.name != 'EarlyLoanRepaymentClaim' and
            metadata.form.name != 'AccountOpeningClaim' and metadata.form.name != 'AccountOpeningClaimWithClose' and metadata.form.name != 'IMAOpeningClaim' and metadata.form.name != 'RemoteConnectionUDBOClaim' and
            metadata.form.name != 'RefuseLongOffer') and not isJurPayment and not isLoyaltyRegistration and not isShortLoanClaim and not isExtendedLoanClaim and not isLoanCardClaim and not isChangeCreditLimitClaim}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.goto.select.service"/>
        <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="buttonGrey"/>
        <tiles:put name="action" value="/private/payments"/>
        <tiles:put name="image"       value="backIcon.png"/>
        <tiles:put name="imageHover"     value="backIconHover.png"/>
        <tiles:put name="imagePosition"  value="left"/>
    </tiles:insert>
</c:if>

<c:if test="${isChangeDepositMinimumBalance}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.goto.select.rules"/>
        <tiles:put name="commandHelpKey" value="button.goto.select.rules"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="buttonGrey"/>
        <tiles:put name="action" value="private/accounts/info.do?id=${form.document.accountId}"/>
        <tiles:put name="image"       value="backIcon.png"/>
        <tiles:put name="imageHover"     value="backIconHover.png"/>
        <tiles:put name="imagePosition"  value="left"/>
    </tiles:insert>
</c:if>

<c:if test="${formName == 'IMAOpeningClaim' and !hasPfpId}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.back"/>
        <tiles:put name="commandHelpKey" value="button.back.help"/>
        <tiles:put name="bundle" value="imaBundle"/>
        <tiles:put name="action" value="/private/ima/products/list"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
    </tiles:insert>
</c:if>

<c:if test="${isDepositaryClaim}">
    <c:choose>
        <c:when test="${metadata.form.name != 'RecallDepositaryClaim'}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.goto.depoAccounts.list"/>
                <tiles:put name="commandHelpKey" value="button.goto.depoAccounts.list"/>
                <tiles:put name="bundle" value="depoBundle"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
                <tiles:put name="action" value="/private/depo/list"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <c:set var="depoLink"
                   value="${phiz:getDepoAccountLinkFromDocument(form.document)}"/>
            <c:if test="${not empty depoLink}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.goto.depoDocuments.list"/>
                    <tiles:put name="commandHelpKey" value="button.goto.depoDocuments.list"/>
                    <tiles:put name="bundle" value="depoBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action"
                               value="/private/depo/documents.do?id=${depoLink.id}"/>
                </tiles:insert>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:if>

<c:if test="${(isLoanProduct || isLoanOffer || isLoanCardOffer) && (!param['changeLimit'])}">
    <%--Для заявки на кредитный продукт или кредитную карту выводим специфичную ссылку назад --%>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey"
                   value="${metadata.form.name == 'LoanProduct' || metadata.form.name == 'LoanOffer' ? 'button.prev.loanProduct' : 'button.prev.loanCardProduct'}"/>
        <tiles:put name="commandHelpKey"
                   value="${metadata.form.name == 'LoanProduct' || metadata.form.name == 'LoanOffer' ? 'button.prev.loanProduct.help' : 'button.prev.loanCardProduct.help'}"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <c:choose>
            <c:when test="${metadata.form.name == 'LoanProduct'}">
                <tiles:put name="action" value="/private/payments/loan_product.do"/>
            </c:when>
            <c:when test="${metadata.form.name == 'LoanOffer'}">
                <tiles:put name="action" value="/private/payments/loan_offer.do"/>
            </c:when>
            <c:otherwise>
                <script type="text/javascript">
                    function getLoanURL()
                    {
                        var temp = "${phiz:calculateActionURL(pageContext, '/private/payments/loan_card_offer.do')}";
                        if ("${param['changeType']}")
                            temp = temp + '?changeType=true';
                        var income = ensureElement('income');
                        if (income != null)
                        {
                            temp = "${phiz:calculateActionURL(pageContext, '/private/payments/loan_card_product.do')}";
                            if (income.value != null && income.value != '')
                                temp += "?income=" + income.value;
                        }
                        window.location = temp;
                    }
                </script>
                <tiles:put name="onclick">getLoanURL();</tiles:put>
            </c:otherwise>
        </c:choose>
    </tiles:insert>
</c:if>

<c:if test="${isExtendedLoanClaimNeedCall && not form.document.inWaitTM}">
    <div class="buttonArea">
        <div class="float">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.edit"/>
                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
                <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
                <tiles:put name="imagePosition"  value="left"/>
            </tiles:insert>
        </div>
        <%--Заказать обратный звонок для КУКО--%>
        <tiles:insert page="/WEB-INF/jsp-sbrf/loans/callFromBankBlock.jsp" flush="false"/>
    </div>
</c:if>

<c:if test="${not isClaim && not externalPayment && not isLongOffer && not isAutoPayment && not isCreditReportPayment}">
    <c:if test="${isJurPayment}">
        <%--выводим кнопку "Назад" только для биллинговых платежей--%>
        <c:choose>
            <c:when test="${empty form.document.receiverInternalId}">
                <%--Для внешнего получателя переходим на общую форму оплаты юрику--%>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.prev.help"/>
                    <tiles:put name="commandHelpKey" value="button.prev.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="action"
                               value="/private/payments/jurPayment/edit.do?id=${form.id}"/>
                    <tiles:put name="image"       value="backIcon.png"/>
                    <tiles:put name="imageHover"     value="backIconHover.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <%--Для внутреннего получателя переходим на первый шаг биллинговой оплаты--%>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.prev"/>
                    <tiles:put name="commandHelpKey" value="button.prev.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="imagePosition"  value="left"/>
                    <tiles:put name="imageUrl"       value="${commonImagePath}/backIcon.png"/>
                    <tiles:put name="imageHover"     value="${commonImagePath}/backIconHover.png"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
        <div class="clear"></div>
    </c:if>
    <%--выводим кнопку "Назад" для формы JurPayment--%>
    <c:if test="${metadata.form.name == 'JurPayment'}">
        <c:set var="prefix" value="/private/payments/jurPayment/edit.do"/>
        <%--исключаем ситуацию подстановки вместо &copy знака копирайта--%>
        <c:set var="suffix"
               value="${param['copy'] != null ? fn:replace('copy=%', '%', param['copy']) : ''}"/>

        <c:forEach var="item" items="${param}">
            <c:if test="${item.key != 'operation' && item.key != 'copy'}">
                <c:set var="suffix">
                    ${suffix}<c:if test="${fn:length(suffix) > 0}">&</c:if><c:out
                        value="${item.key}=${item.value}"/>
                </c:set>
            </c:if>
        </c:forEach>

        <c:set var="action">
            ${prefix}<c:if test="${fn:length(suffix) > 0}">?${suffix}</c:if>
        </c:set>
        <c:if test="${not empty depoLinkId}">
            <c:set var="action" value="/private/depo/info/debt.do?id=${depoLinkId}"/>
        </c:if>
        <c:set var="quot" value="\""/>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.prev.help"/>
            <tiles:put name="commandHelpKey" value="button.prev.help"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="action">${fn:replace(action, quot ,"%22")}</tiles:put>
            <tiles:put name="image"       value="backIcon.png"/>
            <tiles:put name="imageHover"     value="backIconHover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
        <div class="clear"></div>
    </c:if>
</c:if>
<c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim' && !isShowOnlyConnectionUDBO}">
    <%--кнопка назад для Удаленного подключения УДБО--%>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.prev"/>
        <tiles:put name="commandHelpKey" value="button.prev.help"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="action" value="/private/accounts.do"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
    </tiles:insert>
</c:if>
<%-- кнопка назад для автоплатежей --%>
<c:if test="${isLongOffer}">
    <c:choose>
        <c:when test="${isEditAutoSub || isDelayAutoSub || isCloseAutoSub || isRecoveryAutoSub || isAutoSubP2PChange}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.prev"/>
                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="action" value="private/autosubscriptions/info.do?id=${form.document.number}"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
            </tiles:insert>
        </c:when>
        <c:when test="${metadata.form.name == 'CreateP2PAutoTransferClaim'}">
            <c:choose>
                <c:when test="${form.document.state.code == 'INITIAL_LONG_OFFER'}"/>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.prev.help"/>
                        <tiles:put name="commandHelpKey"    value="button.prev.help"/>
                        <tiles:put name="bundle"            value="paymentsBundle"/>
                        <tiles:put name="action"            value="private/autopayment/select-category-provider.do"/>
                        <tiles:put name="viewType"          value="buttonGrey"/>
                        <tiles:put name="image"       value="backIcon.png"/>
                        <tiles:put name="imageHover"     value="backIconHover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${isAutoSubParams}">
             <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.edit"/>
                <tiles:put name="commandTextKey" value="button.prev"/>
                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                 <tiles:put name="viewType" value="buttonGrey"/>
                 <tiles:put name="imagePosition"  value="left"/>
                 <tiles:put name="imageUrl"       value="${commonImagePath}/backIcon.png"/>
                 <tiles:put name="imageHover"     value="${commonImagePath}/backIconHover.png"/>
            </tiles:insert>
        </c:when>
        <c:when test="${isAutoSub || metadata.form.name == 'CreateAutoPaymentPayment'}">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.prev"/>
                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="imagePosition"  value="left"/>
                <tiles:put name="imageUrl"       value="${commonImagePath}/backIcon.png"/>
                <tiles:put name="imageHover"     value="${commonImagePath}/backIconHover.png"/>
            </tiles:insert>
        </c:when>
        <c:when test="${metadata.form.name == 'EditAutoPaymentPayment'
                         || metadata.form.name == 'RefuseAutoPaymentPayment'}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.prev"/>
                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="action" value="private/autopayments/info.do?id=${form.document.autoPaymentLinkId}"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
            </tiles:insert>
        </c:when>
        <c:when test="${metadata.form.name == 'CreateMoneyBoxPayment'
                         || metadata.form.name == 'EditMoneyBoxClaim'}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.prev"/>
                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="action" value="private/payments/common.do?&status=all"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
             <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.back.longOffer"/>
                <tiles:put name="commandHelpKey" value="button.back.longOffer.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:if>

<c:if test="${metadata.form.name == 'AccountOpeningClaim' and !hasPfpId or metadata.form.name == 'AccountOpeningClaimWithClose'}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.toChooseDepositList"/>
        <tiles:put name="commandHelpKey" value="button.toChooseDepositList.help"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <tiles:put name="action" value="/private/deposits/products/list.do?form=${metadata.form.name}"/>
    </tiles:insert>
</c:if>

<c:if test="${hasPfpId}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.toEditPfpPortfolio"/>
        <tiles:put name="commandHelpKey" value="button.toEditPfpPortfolio.help"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${form.document.pfpId}&portfolioId=${form.document.pfpPortfolioId}"/>
    </tiles:insert>
</c:if>

<c:if test="${metadata.form.name == 'RefuseLongOffer'}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.prev"/>
        <tiles:put name="commandHelpKey" value="button.prev.help"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="action" value="private/longOffers/info.do?id=${form.document.longOfferId}"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
    </tiles:insert>
</c:if>

<c:if test="${phiz:isScriptsRSAActive() and isExtendedLoanClaimInitial7}">
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/AC_OETags.js"></script>
</c:if>