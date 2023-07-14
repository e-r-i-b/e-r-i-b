<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/payments/view" onsubmit="return setEmptyAction(event)">
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="listFormName" value="${form.metadata.listFormName}"/>
    <c:set var="paymentFormName" value="${form.metadata.listFormName}" scope="request"/>
	<c:set var="metadataPath" value="${form.metadataPath}"/>
	<c:set var="metadata" value="${form.metadata}"/>
    <c:set var="document" value="${form.document}"/>
    <c:set var="isSecuritiesTransferClaim" value="${metadata.form.name == 'SecuritiesTransferClaim'}"/>
    <c:set var="isShortLoanClaim" value="${metadata.form.name == 'ShortLoanClaim'}"/>
    <c:set var="state" value="${document.state.code}"/>
    <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q13')}"/>
    <c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                              || isSecuritiesTransferClaim
                                              || metadata.form.name == 'DepositorFormClaim'
                                              || metadata.form.name == 'RecallDepositaryClaim'}"/>

    <c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'
                                              || metadata.form.name == 'LoanCardOffer'
                                              || metadata.form.name == 'LoanProduct'
                                              || metadata.form.name == 'LoanCardProduct'
                                              || metadata.form.name == 'ShortLoanClaim'}"/>

    <c:set var="depoAccountLink" value="${isDepositaryClaim ? phiz:getDepoAccountLinkFromDocument(document) : null}"/>
    <c:set var="isLongOffer" value="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and document.longOffer}"/>
    <c:set var="isAutoSub" value="${isLongOffer && metadata.form.name == 'RurPayJurSB' || metadata.form.name == 'CreateP2PAutoTransferClaim'}"/>
    <c:set var="isEditInvoiceSub" value="${metadata.form.name == 'EditInvoiceSubscriptionClaim'}"/>
    <c:set var="isEditAutoSub" value="${metadata.form.name == 'EditAutoSubscriptionPayment' || metadata.form.name == 'EditAutoPaymentPayment' || metadata.form.name == 'EditMoneyBoxClaim' || metadata.form.name == 'EditP2PAutoTransferClaim'}"/>
    <c:set var="isModifyAutoSub" value="${metadata.form.name == 'CloseAutoSubscriptionPayment'
                                       || metadata.form.name == 'DelayAutoSubscriptionPayment'
                                       || metadata.form.name == 'RecoveryAutoSubscriptionPayment'
                                       || metadata.form.name == 'DelayP2PAutoTransferClaim'
                                       || metadata.form.name == 'RecoveryP2PAutoTransferClaim'
                                       || metadata.form.name == 'CloseP2PAutoTransferClaim'
                                       }"/>
    <c:set var="isDelayAutoSub"  value="${metadata.form.name == 'DelayAutoSubscriptionPayment' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
    <c:set var="isRecoveryAutoSub" value="${metadata.form.name == 'RecoveryAutoSubscriptionPayment' || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
    <c:set var="isCloseAutoSub" value="${metadata.form.name == 'CloseAutoSubscriptionPayment' || metadata.form.name == 'CloseP2PAutoTransferClaim'}"/>
    <c:set var="isDocInstanceOfJurPayment" value="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
    <c:set var="isTemplate" value="${form.document.template}"/>
    <c:set var="isRefuseAutoPaymentPayment" value="${metadata.form.name == 'RefuseAutoPaymentPayment' or metadata.form.name == 'RefuseLongOffer'}"/>
    <c:set var="isJurPayment" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
    <c:set var="isShortLoanClaim"  value="${metadata.form.name == 'ShortLoanClaim'}"/>
    <c:set var="isExtendedLoanClaim" value="${metadata.form.name == 'ExtendedLoanClaim'}"/>
    <c:set var="isPreapprovedLoanCardClaim" value="${metadata.form.name == 'PreapprovedLoanCardClaim'}"/>
    <c:set var="isLoanCardClaim" value="${metadata.form.name == 'LoanCardClaim'}"/>
    <c:set var="isMoneyBoxSupported" value="${metadata.form.name!='AccountOpeningClaim' && phiz:isMoneyBoxSupported(document)}"/>
    <c:set var="isCreditReportPayment" value="${metadata.form.name == 'CreditReportPayment'}"/>
    <c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment' || metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
    <c:set var="isMoneyBoxStatusModClaim" value="${metadata.form.name == 'RecoverMoneyBoxPayment' || metadata.form.name == 'RefuseMoneyBoxPayment' || metadata.form.name == 'CloseMoneyBoxPayment'}"/>
    <c:set var="isEarlyLoanRepaymentClaim" value="${metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
    <c:set var="isReportByCardClaim" value="${metadata.form.name == 'ReportByCardClaim'}"/>
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
       <c:when test="${isPreapprovedLoanCardClaim || isLoanCardClaim || isReportByCardClaim || isChangeCreditLimitClaim}">
           <c:set var="mainmenuButton" value="Cards"/>
       </c:when>
       <c:otherwise>
           <c:set var="mainmenuButton" value="Payments"/>
       </c:otherwise>
    </c:choose>
    <c:set var="external" value="${form.externalPayment}"/>
    <c:set var="fns" value="${form.fnsPayment}"/>
    <c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
    <c:set var="isServicePayment" value="${metadata.form.name == 'RurPayJurSB' && !isLongOffer}"/>

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
    <c:set var="isLoyaltyProgram" value="${metadata.form.name == 'LoyaltyProgramRegistrationClaim'}"/>

    <c:set var="isAutoPayment" value="${metadata.form.name == 'EditAutoPaymentPayment'
                                        || metadata.form.name == 'CreateAutoPaymentPayment'
                                        || metadata.form.name == 'RefuseAutoPaymentPayment'}"/>

    <%-- флажок, говорящий о том, можно ли создать по документу шаблон --%>
    <c:set var="supportedActions"  value="${phiz:getDocumentSupportedActions(document)}"/>
    <c:set var="templateSupported"  value="${supportedActions['isTemplateSupported']}"/>
    <c:set var="allowedStates"      value="${(state == 'EXECUTED' || state == 'DISPATCHED' || state == 'DELAYED_DISPATCH' || state == 'WAIT_CONFIRM')}"/>
    <c:set var="isEnableCreateTemplate" value="${allowedStates && phiz:impliesTemplateOperation(document.formName) && templateSupported}" scope="page"/>
    <c:set var="hasPfpId" value="${(metadata.form.name == 'AccountOpeningClaim' || metadata.form.name == 'IMAOpeningClaim') && not empty form.document.pfpId}"/>

    <c:set var="isITunes" value="${phiz:isITunesDocument(form.document)}"/>

    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="mode"  value="${phiz:getUserVisitingMode()}"/>
    <c:set var="isGuestLoanClaim" value="${mode == 'GUEST' && (isShortLoanClaim || isExtendedLoanClaim  || isLoanCardClaim || metadata.form.name == 'LoanCardProduct')}"/>
    <c:choose>
        <c:when test="${isGuestLoanClaim}">
            <c:set var="definition" value="guestPage"/>
        </c:when>
        <c:otherwise>
            <c:set var="isShowOnlyConnectionUDBO" value="${metadata.form.name == 'RemoteConnectionUDBOClaim' && phiz:isShowOnlyConnectionUDBO()}"/>
            <c:set var="definition" value="${isShowOnlyConnectionUDBO ? 'login' : mode != null && mode == 'PAYORDER_PAYMENT' ? 'fnsMain' : 'paymentCurrent'}"/>
        </c:otherwise>
    </c:choose>
    <tiles:insert definition="${definition}">
        <c:if test="${isGuestLoanClaim}">
            <tiles:put name="mainMenuType" value="guestMainMenu"/>
            <tiles:put name="mainmenu"     value="Index"/>
        </c:if>
       <c:if test="${mode != 'PAYORDER_PAYMENT'}">
                <tiles:put name="mainmenu" value="${mainmenuButton}" />
                <tiles:put name="submenu" type="string" value="${listFormName}"/>
                <c:if test="${isNewP2P}">
                   <tiles:put name="rightSection" value="false"/>
                </c:if>
               <c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                   <tiles:put name="headerGroup" value="true"/>
                   <tiles:put name="needHelp" value="true"/>
                   <tiles:put name="centerContent" value="true"/>
                   <tiles:put name="addStyleClass" value="udboDataWidth"/>
               </c:if>
                <%-- заголовок --%>
                <tiles:put name="pageTitle">${form.title}</tiles:put>
                <%--меню--%>
                <tiles:put name="menu" type="string">

                    <c:if test="${!form.metadata.needParent}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.addNew"/>
                            <tiles:put name="commandHelpKey" value="button.addNew"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="action" value="/private/payments/add.do?form=${form.form}"/>
                        </tiles:insert>
                    </c:if>

                    <c:if test="${form.metadata.filterForm != null}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.close"/>
                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="action"         value="/private/payments/payments.do?name=${listFormName}"/>
                    </tiles:insert>
                    </c:if>
                </tiles:put>
            <c:if test="${!isLoyaltyProgram && !isGuestLoanClaim}">
                <%-- данные --%>
                <tiles:put name="breadcrumbs">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="main" value="true"/>
                        <tiles:put name="action" value="/private/accounts.do"/>
                    </tiles:insert>
                    <c:choose>
                       <c:when test="${isShortLoanClaim or isExtendedLoanClaim or isEarlyLoanRepaymentClaim}">
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                               <tiles:put name="name"   value="Кредиты"/>
                               <tiles:put name="action" value="/private/loans/list.do"/>
                           </tiles:insert>
                       </c:when>
                       <c:when test="${isPreapprovedLoanCardClaim || isLoanCardClaim || isReportByCardClaim || isChangeCreditLimitClaim}">
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                               <tiles:put name="name"   value="Карты"/>
                               <tiles:put name="action" value="/private/cards/list.do"/>
                           </tiles:insert>
                           <c:if test="${isReportByCardClaim || isChangeCreditLimitClaim}">
                               <tiles:insert definition="breadcrumbsLink" flush="false">
                                   <tiles:put name="name"   value="${document.cardName} ${phiz:getCutCardNumber(document.cardNumber)}"/>
                                   <tiles:put name="action" value="/private/cards/info.do?id=${document.cardId}"/>
                               </tiles:insert>
                           </c:if>
                       </c:when>
                       <c:when test="${isTemplate}">
                          <tiles:insert definition="breadcrumbsLink" flush="false">
                             <tiles:put name="name" value="Мои шаблоны"/>
                             <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
                          </tiles:insert>
                       </c:when>
                       <c:when test="${isLongOffer || isEditAutoSub || isAutoSubChange || isRefuseAutoPaymentPayment}">
                          <tiles:insert definition="breadcrumbsLink" flush="false">
                             <tiles:put name="name" value="Мои автоплатежи"/>
                             <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
                          </tiles:insert>
                       </c:when>
                       <c:when test="${isDepositaryClaim}">
                          <tiles:insert definition="breadcrumbsLink" flush="false">
                             <tiles:put name="name" value="Счета депо"/>
                             <tiles:put name="action" value="/private/depo/list.do"/>
                          </tiles:insert>
                       </c:when>
                       <c:when test="${metadata.form.name == 'LoanCardOffer'}">
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                                <tiles:put name="name" value="Персональные предложения от Сбербанка:"/>
                                <tiles:put name="action" value="/private/loan/loanoffer/show.do"/>
                           </tiles:insert>
                        </c:when>
                        <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                            <tiles:insert definition="breadcrumbsLink" flush="false">
                                <tiles:put name="name" value="Кредитная история"/>
                                <tiles:put name="action" value="/private/credit/report.do"/>
                            </tiles:insert>
                        </c:when>
                       <c:otherwise>
                          <tiles:insert definition="breadcrumbsLink" flush="false">
                             <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                             <tiles:put name="action" value="/private/payments.do"/>
                          </tiles:insert>
                       </c:otherwise>
                    </c:choose>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <c:choose>
                            <c:when test="${metadata.form.name == 'RefuseLongOffer' or metadata.form.name == 'RefuseAutoPaymentPayment'}">
                                <c:set var="lastLinkName">
                                    <c:out value="Отмена автоплатежа"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isEditInvoiceSub}">
                                <c:set var="lastLinkName">
                                    <c:out value="Редактирование автопоиска"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isEditAutoSub}">
                                <c:set var="lastLinkName">
                                    <c:out value="Редактирование автоплатежа"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isDelayAutoSub}">
                                <c:set var="lastLinkName">
                                    <c:out value="Приостановка автоплатежа"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isCloseAutoSub}">
                                <c:set var="lastLinkName">
                                    <c:out value="Отключение автоплатежа"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isRecoveryAutoSub}">
                                <c:set var="lastLinkName">
                                    <c:out value="Возобновление автоплатежа"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isEditInvoiceSub}">
                                <c:set var="lastLinkName">
                                    <c:out value="Редактирование автопоиска"/>
                                </c:set>
                            </c:when>
                            <c:when test="${isLongOffer && !isMoneyBoxStatusModClaim}">
                                <c:set var="lastLinkName">
                                    <c:out value="Подключение автоплатежа"/>
                                </c:set>
                            </c:when>
                            <c:when test="${metadata.form.name == 'RurPayment'}">
                                <c:choose>
                                    <c:when test="${document.receiverSubType == 'externalAccount'}">
                                        <c:set var="lastLinkName">Перевод частному лицу в другой банк по реквизитам</c:set>
                                    </c:when>
                                    <c:when test="${document.receiverSubType == 'ourCard' or document.receiverSubType == 'ourAccount' or  document.receiverSubType == 'ourPhone'}">
                                        <c:set var="lastLinkName">Перевод клиенту Сбербанка</c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="lastLinkName">Перевод на карту в другом банке</c:set>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:set var="lastLinkName">
                                    <c:out value="${metadata.form.description}"/>
                                </c:set>
                            </c:otherwise>
                        </c:choose>
                        <tiles:put name="name" value="${lastLinkName}"/>
                        <tiles:put name="last" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </c:if>
       </c:if>
       <tiles:put name="data" type="string">
            <c:if test="${isMoneyBoxSupported}">
               <tiles:insert definition="moneyBoxWindowCreator" flush="false"/>
            </c:if>
            <script type="text/javascript">
                function addCopy(paymentId)
                {
                    <c:choose>
                        <c:when test="${metadata.form.name == 'JurPayment'}">
                            <c:set var="depoLinkId" value="${document.depoLinkId}"/>
                            <c:if test="${not empty depoLinkId}">
                                <c:set var="currentUrl" value="${phiz:calculateActionURL(pageContext, '/private/depo/info/debt.do')}"/>
                                window.location = '${currentUrl}?id=${depoLinkId}';
                            </c:if>
                            <c:if test="${empty depoLinkId}">
                                <c:set var="linkPayment" value="${phiz:getLinkPaymentByDocument(document)}"/>
                                <c:set var="currentUrl" value="${phiz:calculateActionURL(pageContext, linkPayment)}"/>
                                window.location = '${currentUrl}?copying=true&id=' + paymentId;
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="linkPayment" value="${phiz:getLinkPaymentByDocument(document)}"/>
                            <c:set var="currentUrl" value="${phiz:calculateActionURL(pageContext, linkPayment)}"/>
                            window.location = '${currentUrl}?copying=true&id=' + paymentId;
                        </c:otherwise>
                    </c:choose>
                }
                var templateUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/template.do')}";
                function saveTemplate(paymentId)
                {
                    window.location = templateUrl + "?payment="+paymentId;
                }
                function viewStatement(claimId, event)
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/private/pfr/statement.do')}?claimId=" + claimId;
                    openWindow(event, url, "", "");
                }

                function printCheck(paymentId, event)
                {
                    var printCheckUrl = "";
                    <c:choose>
                        <c:when test="${phiz:getUserVisitingMode() == 'PAYORDER_PAYMENT'}">
                            printCheckUrl = "${phiz:calculateActionURL(pageContext,'/external/payments/check_print')}";
                        </c:when>
                        <c:otherwise>
                            printCheckUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/check_print')}";
                        </c:otherwise>
                    </c:choose>

                    openWindow(event, printCheckUrl + "?id=" + paymentId, "", "resizable=1,menubar=1,toolbar=0,scrollbars=1,width=300,height=700");
                }

                var recallUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/payment')}";
                function tryRecall(paymentId)
                {
                    if('${state}' == 'DISPATCHED')
                    {
                        window.location = recallUrl + "?form=RecallDepositaryClaim&recallDocumentId=" + paymentId;
                    }
                    else
                    {
                         new CommandButton('button.withdraw').click();
                    }
                }

                function makeInvoiceSubscription()
                {
                    ajaxQuery(
                            "id=" + ${form.id} + "&operation=button.makeInvoiceSubscription",
                            "${phiz:calculateActionURL(pageContext, '/private/async/userprofile/basket/payments/payment')}",
                            function(data)
                            {
                                if(data.errors != null && data.errors.length > 0)
                                {
                                    for(var i = 0; i < data.errors.length; i++)
                                        addError(data.errors[i]);
                                }
                                else if(!isEmpty(data.redirectUrl))
                                    goTo(data.redirectUrl);
                            }, "json");
                }
            </script>

            <tiles:insert definition="paymentForm" flush="false">
                <c:choose>
                    <c:when test="${isAutoPayment}">
                        <c:set var="idFormForImage" value="isAutoPayment"/>
                    </c:when>
                    <c:when test="${isLongOffer or metadata.form.name=='RefuseLongOffer'}">
                        <c:set var="idFormForImage" value="LongOffer"/>
                    </c:when>
                    <c:when test="${metadata.form.name == 'LoanOffer' || metadata.form.name == 'LoanProduct'}">
                        <c:set var="idFormForImage" value="loanCondition"/>
                    </c:when>
                    <c:when test="${metadata.form.name == 'LoanCardOffer' || metadata.form.name == 'LoanCardProduct'}">
                        <c:set var="idFormForImage" value="cardCondition"/>
                    </c:when>
                    <c:when test="${metadata.form.name == 'VirtualCardClaim'}">
                        <c:set var="idFormForImage" value="virtualCard"/>
                    </c:when>
                    <c:when test="${metadata.form.name == 'RurPayment' && (form.document.receiverSubType=='ourCard' ||
                                    form.document.receiverSubType=='ourAccount' || form.document.receiverSubType=='ourPhone')}">
                        <c:set var="idFormForImage" value="RurPaymentSber64"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="idFormForImage" value="${metadata.form.name}"/>
                    </c:otherwise>
                </c:choose>
                <tiles:put name="id" value="${idFormForImage}"/>

                <c:choose>
                    <c:when test="${metadata.form.name == 'CloseP2PAutoTransferClaim'}">
                        <tiles:put name="contentTitle">
                            <span class="size24">Отключение автоплатежа</span>
                        </tiles:put>
                    </c:when>
                    <c:when test="${metadata.form.name == 'DelayP2PAutoTransferClaim'}">
                        <tiles:put name="contentTitle">
                            <span class="size24">Приостановка автоплатежа</span>
                        </tiles:put>
                    </c:when>
                    <c:when test="${metadata.form.name == 'EditP2PAutoTransferClaim'}">
                        <tiles:put name="contentTitle">
                            <span class="size24">Редактирование автоплатежа</span>
                        </tiles:put>
                    </c:when>
                    <c:when test="${metadata.form.name == 'RecoveryP2PAutoTransferClaim'}">
                        <tiles:put name="contentTitle">
                            <span class="size24">Возобновление автоплатежа</span>
                        </tiles:put>
                    </c:when>
                    <c:when test="${metadata.form.name == 'EditMoneyBoxClaim'}">
                        <tiles:put name="title"><span class="size24">Редактирование копилки</span></tiles:put>
                    </c:when>
                    <c:when test="${isEditInvoiceSub}">
                        <tiles:put name="title"><span class="size24">Редактирование автопоиска</span></tiles:put>
                    </c:when>
                    <c:when test="${isEditAutoSub}">
                        <tiles:put name="title"><span class="size24">Редактирование автоплатежа</span></tiles:put>
                    </c:when>
                    <c:when test="${isCloseAutoSub}">
                        <tiles:put name="title"><span class="size24">Отключение автоплатежа</span></tiles:put>
                    </c:when>
                    <c:when test="${isDelayAutoSub}">
                        <tiles:put name="title"><span class="size24">Приостановка автоплатежа</span></tiles:put>
                    </c:when>
                    <c:when test="${isRecoveryAutoSub}">
                        <tiles:put name="title"><span class="size24">Возобновление автоплатежа</span></tiles:put>
                    </c:when>
                    <c:when test="${metadata.form.name == 'RefuseAutoPaymentPayment'}">
                        <tiles:put name="title"><span class="size24">Отмена выполнения автоплатежа (регулярной операции)</span></tiles:put>
                    </c:when>
                    <c:when test="${metadata.form.name == 'CreateP2PAutoTransferClaim'}">
                        <tiles:put name="contentTitle">
                            <span class="size24">
                                <c:choose>
                                    <c:when test="${document.type.name == 'com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer'}">
                                        Автоплатеж на свою карту
                                    </c:when>
                                    <c:otherwise>
                                        Подключение автоплатежа
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </tiles:put>
                    </c:when>

                    <c:when test="${metadata.form.name == 'CreateMoneyBoxPayment'}"><tiles:put name="title"><span class="size24">Подключение копилки</span></tiles:put></c:when>
                    <c:when test="${isLongOffer && !isMoneyBoxStatusModClaim}"><tiles:put name="title"><span class="size24">Подключение автоплатежа</span></tiles:put></c:when>
                    <c:when test="${metadata.name == 'AirlineReservationPayment'}"><tiles:put name="title"><span class="size24">Оплата брони ${form.providerName}</span></tiles:put></c:when>
                    <c:when test="${external && !fns}">
                        <tiles:put name="title">
                            <span class="size24">${form.providerName}</span>
                        </tiles:put>
                    </c:when>
                    <c:when test="${external && fns}"><tiles:put name="title"><span class="size24">Оплата в адрес федеральной налоговой службы (ФНС)</span></tiles:put></c:when>
                    <c:when test="${metadata.form.name == 'VirtualCardClaim'}"><tiles:put name="title"><span class="size24"><bean:message key="virtual.card.description" bundle="paymentsBundle"/></span></tiles:put></c:when>
                    <c:when test="${metadata.form.name == 'LoanCardProduct'}"><tiles:put name="title"><span class="size24"><bean:message key="loan.credit.card.product" bundle="paymentsBundle"/></span></tiles:put></c:when>
                    <c:when test="${metadata.form.name == 'LoanProduct'}"><tiles:put name="title"><span class="size24"><bean:message key="loan.credit.product" bundle="paymentsBundle"/></span></tiles:put></c:when>
                    <c:when test="${metadata.form.name == 'RurPayment'}">
                        <c:choose>
                            <c:when test="${document.receiverSubType == 'externalAccount'}">
                                <tiles:put name="title">
                                    <span class="size24">Перевод частному лицу в другой банк по реквизитам</span>
                                </tiles:put>
                            </c:when>
                            <c:when test="${document.receiverSubType == 'ourCard' or document.receiverSubType == 'ourAccount' or  document.receiverSubType == 'ourPhone'}">
                                <tiles:put name="title">
                                    <span class="size24">Перевод клиенту Сбербанка</span>
                                </tiles:put>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="title">
                                    <span class="size24">Перевод на карту в другом банке</span>
                                </tiles:put>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${isNewP2P}">
                        <c:set var="receiverSubType" value="${form.document.receiverSubType}"/>
                        <c:choose>
                            <c:when test="${receiverSubType == 'ourAccount' || receiverSubType == 'externalAccount'}">
                                <tiles:put name="contentTitle">
                                    <span class="size24"><bean:message key="label.translate.bankAccount" bundle="paymentsBundle"/></span>
                                </tiles:put>
                            </c:when>
                            <c:when test="${receiverSubType == 'visaExternalCard' || receiverSubType == 'masterCardExternalCard' || receiverSubType == 'ourContactToOtherCard'}">
                                <tiles:put name="contentTitle">
                                    <span class="size24"><bean:message key="label.translate.card.otherbank" bundle="paymentsBundle"/></span>
                                </tiles:put>
                            </c:when>
                            <c:when test="${receiverSubType == 'yandexWallet' || receiverSubType == 'yandexWalletOurContact' || receiverSubType == 'yandexWalletByPhone'}">
                                <tiles:put name="contentTitle">
                                    <span class="size24"><bean:message key="label.translate.yandex" bundle="paymentsBundle"/></span>
                                </tiles:put>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="contentTitle">
                                    <span class="size24"><bean:message key="label.translate.ourClient" bundle="paymentsBundle"/></span>
                                </tiles:put>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${metadata.form.name == 'PreapprovedLoanCardClaim'}">
                        <tiles:put name="title">${metadata.form.description}</tiles:put>
                    </c:when>
                    <c:when test="${isEarlyLoanRepaymentClaim}">
                        <tiles:put name="contentTitle">
                            <c:choose>
                                <c:when test="${form.document.partial}">
                                    <span class="size24">Частичное досрочное погашение</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="size24">Полное погашение кредита</span>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="title">
                            <span class="size24">${metadata.form.description}</span>
                        </tiles:put>
                    </c:otherwise>
                </c:choose>
                <c:if test="${isNewP2P || metadata.form.name == 'EarlyLoanRepaymentClaim'}">
                    <tiles:put name="color" value="gradient"/>
                </c:if>

                <tiles:put name="showHeader" value="${not isShortLoanClaim and not isExtendedLoanClaim and not isPreapprovedLoanCardClaim and not isLoanCardClaim and not isNewP2P and not isReportByCardClaim and not isChangeCreditLimitClaim and not isEarlyLoanRepaymentClaim}"/>
                <tiles:put name="description">
                    <c:choose>
                        <c:when test="${metadata.form.name == 'IMAOpeningClaim'}">
                            При необходимости получения экземпляра Заявления на открытие обезличенного металлического счета (ОМС) в рамках
                            Договора банковского обслуживания Вы можете обратиться в любое подразделение Банка в пределах территории действия
                            Договора банковского обслуживания
                        </c:when>
                        <c:when test="${isLoyaltyProgram}">
                            <div class="loyaltyPaymentHeader">
                                <bean:message key="label.loyalty.registration.text" bundle="loyaltyBundle"/>
                            </div>
                        </c:when>
                        <c:when test="${isClaim or isLossPassbook}">
                            На этой странице Вы можете посмотреть статус заявки. Также отследить ход выполнения операции можно в &laquo;Истории операций&raquo;.
                        </c:when>
                        <c:when test="${isDepositaryClaim}">
                            <c:set var="claimType" value ="заявки."/>
                            <c:if test ="${metadata.form.name == 'RecallDepositaryClaim'}">
                                <c:set var="claimType" value ="отзыва документа."/>
                            </c:if>
                            <c:if test="${isSecuritiesTransferClaim || metadata.form.name == 'DepositorFormClaim'}">
                                <c:set var="claimType" value ="поручения."/>
                            </c:if>
                            На этой странице Вы можете посмотреть статус ${claimType} Также отследить ход выполнения операции можно в списке документов по счету депо.
                        </c:when>
                        <c:when test="${metadata.form.name == 'LoanOffer' || metadata.form.name == 'LoanProduct' || metadata.form.name == 'LoanCardProduct' || metadata.form.name == 'ShortLoanClaim'}">
                            На этой странице Вы можете посмотреть статус заявки. Также отследить ход выполнения операции можно в &laquo;Истории операций&raquo;.
                        </c:when>
                        <c:when test="${metadata.form.name == 'LoanCardOffer'}">
                            Уточнить, доставлена ли Ваша карта, Вы можете, позвонив в отделение по номеру ${phiz:getDepartmentPhoneNumber(document)}.
                        </c:when>
                        <c:when test="${isLongOffer}">
                            На этой странице Вы можете посмотреть статус заявки. Также отследить ход выполнения операции можно в &laquo;Истории операций&raquo;.
                        </c:when>
                        <c:when test="${metadata.form.name == 'PFRStatementClaim' && state == 'REFUSED'}">
                            <bean:message bundle="paymentsBundle" key="text.beAware"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                            Кредитный отчет - это документ, который отражает Вашу кредитную историю, т.е. обобщенную информацию по кредитным обязательствам и их состоянию на текущий момент.
                        </c:when>
                        <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"></c:when>
                        <c:otherwise>
                            На этой странице Вы можете посмотреть статус платежа. Также отследить ход выполнения операции можно в &laquo;Истории операций&raquo;.
                        </c:otherwise>
                    </c:choose>
                </tiles:put>

                <c:if test="${isExtendedLoanClaim or isShortLoanClaim}">
                    <tiles:put name="customHeader">
                        <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
                            <tiles:put name="document" beanName="form" beanProperty="document"/>
                            <tiles:put name="description" value=""/>
                        </tiles:insert>
                    </tiles:put>
                </c:if>

                <c:if test="${isEarlyLoanRepaymentClaim}">
                    <tiles:put name="customHeader">
                            <c:choose>
                                <c:when test="${form.document.partial}">
                                    <c:set var="lastLinkName">Частичное досрочное погашение</c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="lastLinkName">Полное досрочное погашение</c:set>
                                </c:otherwise>
                            </c:choose>
                    </tiles:put>
                </c:if>

                <c:if test="${mode == 'GUEST'}">
                    <tiles:put name="customHeader">
                        <tiles:insert page="/WEB-INF/jsp-sbrf/payments/loanCardClaimHeader.jsp" flush="false">
                            <tiles:put name="view" value="true"/>
                            <tiles:put name="showTitle" value="false"/>
                        </tiles:insert>
                    </tiles:put>
                </c:if>

                <c:if test="${isCreditReportPayment}">
                    <tiles:insert definition="creditReportStatusBlock" flush="false">
                        <tiles:put name="documentState" value="${state}"/>
                        <tiles:put name="creditReportStatus" value="${document.creditReportStatus}"/>
                        <tiles:put name="history" value="${not empty param.history and param.history eq 'true'}"/>
                        <tiles:put name="timeOut">
                            <c:choose>
                                <c:when test="${not empty document.executionDate}">
                                   ${phiz:isCreditReportTimeOut(document.executionDate)}
                                </c:when>
                                <c:otherwise>${false}</c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                </c:if>

                <c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                    <tiles:put name="dblLineStripe" value="true"/>
                </c:if>
                <c:if test="${not isPreapprovedLoanCardClaim and not isLoanCardClaim and not isShortLoanClaim and not isExtendedLoanClaim and not isChangeCreditLimitClaim  and not isEarlyLoanRepaymentClaim}">
                    <tiles:put name="stripe">
                        <c:if test="${!isCreditReportPayment}">
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name">
                                    <c:choose>
                                        <c:when test="${isServicePayment}">
                                            выбор услуги
                                        </c:when>
                                        <c:when test="${metadata.form.name == 'RurPayment'}">
                                            выбор получателя
                                        </c:when>
                                        <c:when test="${metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountOpeningClaimWithClose'}">
                                            выбор вклада
                                        </c:when>
                                       <c:when test="${metadata.form.name == 'PFRStatementClaim'}">
                                            выбор организации
                                        </c:when>
                                        <c:when test="${isLoanOffer}">
                                            выбор условий
                                        </c:when>
                                        <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                                            Условия договора
                                        </c:when>
                                        <c:when test="${metadata.form.name == 'IMAOpeningClaim'}">
                                            выбор металла
                                        </c:when>
                                        <c:otherwise>
                                            выбор операции
                                        </c:otherwise>
                                    </c:choose>
                                </tiles:put>
                                <tiles:put name="future" value="false"/>
                                <tiles:put name="show" value="${metadata.form.name != 'LoyaltyProgramRegistrationClaim'}"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name">
                                <c:choose>
                                    <c:when test="${metadata.form.name == 'RecallDepositaryClaim'}">
                                        причина отзыва
                                    </c:when>
                                    <c:when test="${isClaim  || isLossPassbook || isAutoPayment || isLongOffer || metadata.form.name == 'SecurityRegistrationClaim' || metadata.form.name == 'DepositorFormClaim'}">
                                        заполнение заявки
                                    </c:when>
                                    <c:when test="${isLoanOffer}">
                                        оформление заявки
                                    </c:when>
                                    <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                                        Проверка личной информации
                                    </c:when>
                                    <c:otherwise>
                                        заполнение реквизитов
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                            <tiles:put name="future" value="false"/>
                            <c:if test="${isLoyaltyProgram}">
                                <tiles:put name="width" value="50%"/>
                            </c:if>
                            <c:if test="${isCreditReportPayment}">
                                <tiles:put name="width" value="33%"/>
                            </c:if>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <c:choose>
                                <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                                    <tiles:put name="name" value="Подтверждение"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="name" value="подтверждение"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:put name="future" value="false"/>
                            <tiles:put name="show" value="${metadata.form.name != 'LoyaltyProgramRegistrationClaim'}"/>
                            <c:if test="${isCreditReportPayment}">
                                <tiles:put name="width" value="33%"/>
                            </c:if>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name">
                                <c:choose>
                                    <c:when test="${isLoanOffer}">
                                       статус
                                    </c:when>
                                    <c:when test="${isLoyaltyProgram}">
                                        статус заявки
                                    </c:when>
                                    <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                                        Cтатус заявки
                                    </c:when>
                                    <c:otherwise>
                                       статус операции
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                            <tiles:put name="current" value="true"/>
                            <c:if test="${isLoyaltyProgram}">
                                <tiles:put name="width" value="50%"/>
                            </c:if>
                            <c:if test="${isCreditReportPayment}">
                                <tiles:put name="width" value="33%"/>
                            </c:if>
                        </tiles:insert>
                    </tiles:put>
                </c:if>

				<tiles:put name="alignTable" value="center"/>
                <c:if test="${isServicePayment}">
                    <tiles:put name="name" value="Оплата услуг ${metadata.form.description}"/>
                </c:if>
				<tiles:put name="docDate" value="${form.dateString}"/>
				<tiles:put name="isServicePayment" value="${isServicePayment}"/>
                <c:if test="${(isServicePayment or external)}">
                    <c:if test="${isJurPayment}">
                        <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.document.receiverInternalId)}"/>
                        <c:if test="${not empty serviceProvider}">
                            <%-- RurPayJurSb наследник JurPayment, значит здесь достпуен поставщик услуг --%>
                            <tiles:put name="imageId" value="${serviceProvider.imageId}"/>
                        </c:if>
                    </c:if>
                </c:if>
                <c:if test="${external && mode == 'PAYORDER_PAYMENT'}">
                    <c:set var="styleFns" value="class='paymentFns'"/>
                </c:if>
				<tiles:put name="data">
				    <div ${styleFns}>
                        ${form.html}
                    </div>
				</tiles:put>
                <%-- создание шаблона --%>
                <c:if test="${isEnableCreateTemplate}">
                    <c:set var="createTemplateUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/payments/quicklyCreateTemplate')}"/>

                    <c:if test="${phiz:isScriptsRSAActive()}">
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

                        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
                        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>
                    </c:if>
                    <script type="text/javascript">
                        var templateName = "${phiz:escapeForJS(form.templateName, true)}";

                        function cancelReminderRow()
                        {
                            var createReminderRow = $('#create-reminder-row>div');
                            createReminderRow.hide();
                        }

                        <c:set var="createReminderUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/payments/quicklyCreateReminder')}"/>
                        function saveReminder(reminderName)
                        {
                            var reminderParameters = serializeDiv("create-reminder-row");
                            ajaxQuery(
                                    "payment=" + ${form.id} + "&field(templateName)=" + decodeURItoWin(reminderName) + "&" + reminderParameters,
                                    "${createReminderUrl}",
                                    function(data)
                                    {
                                        document.getElementById('oneTimePasswordWindow').innerHTML = data;
                                        win.open('oneTimePasswordWindow');
                                        confirmOperation.initLoadedData();
                                    }, null);
                        }

                        function openReminderRow()
                        {
                            var createTemplateRow = $('#create-reminder-row>div');
                            createTemplateRow.show();

                            payInput.onFocus(createTemplateRow.find('.form-row #reminderName').get(0));
                        }

                        function closeReminderForm(paymentId)
                        {
                            $('#create-reminder-row>div').hide();
                        }

                        function openTemplateRow()
                        {
                            var createTemplateRow = $('#create-template-row>div');
                            createTemplateRow.show();

                            payInput.onFocus(createTemplateRow.find('.form-row #templateName').get(0));
                        }

                        function cancelTemplateRow()
                        {
                            var createTemplateRow = $('#create-template-row>div');
                            createTemplateRow.hide();

                            payInput.fieldClearError(createTemplateRow.find(".form-row #templateName").attr('name'));
                            payInput.curentPayInput = undefined;
                            $('#create-template-row #templateName').val(templateName);
                        }

                        function closeTemplateForm(paymentId)
                        {
                            $('#create-template-row>div').hide();
                        }

                        function saveTemplate(tmpltName)
                        {
                            ajaxQuery(
                            "payment=" +  ${form.id} + "&field(templateName)=" + decodeURItoWin(tmpltName)<c:if test="${phiz:isScriptsRSAActive()}"> + new RSAObject().toRequestParameters()</c:if>,
                            "${createTemplateUrl}",
                            function(data)
                            {
                                document.getElementById('oneTimePasswordWindow').innerHTML = data;
                                win.open('oneTimePasswordWindow');
                                confirmOperation.initLoadedData();
                            }, null);

                            function initialize()
                            {
                                dom_data_collection.startInspection();
                                if (window.addEventListener)
                                {
                                    window.addEventListener('load', UIEventCollector.initEventCollection, false);
                                }
                                else if (window.attachEvent)
                                {
                                    window.attachEvent('onload', UIEventCollector.initEventCollection);
                                }
                                else
                                {
                                    window.onload = UIEventCollector.initEventCollection;
                                }
                            }
                        }

                        function saveTemplateByEnter(e)
                        {
                            var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
                            if(kk == 13)
                            {
                                $('#saveTemplateBtn').click();
                                if (e.preventDefault)
                                    e.preventDefault();
                                else
                                    e.returnValue = false;
                            }
                        }
                    </script>
                    <c:if test="${isEnableCreateTemplate && form.externalAccountPaymentAllowed}">
                    <tiles:put name="template">
                        <div <c:if test="${isNewP2P}">class="fakeFormRowNew"</c:if> style="display:none">
                            <tiles:insert definition="formRow" flush="false" >
                                <tiles:put name="title" type="string">
                                    <bean:message key="label.quicly.save.template" bundle="paymentsBundle"/>:
                                </tiles:put>
                                <tiles:put name="description" type="string">
                                    <bean:message key="quicly.save.template.description" bundle="paymentsBundle"/>
                                </tiles:put>
                                <tiles:put name="data" type="string">
                                    <div onkeypress="saveTemplateByEnter(event);">
                                        <input type="text" style="width: 200px;" maxlength="50" id="templateName" value="<c:out value='${form.templateName}'/>"/>
                                        &nbsp;
                                        <div class="rightBtns <c:if test="${isNewP2P}">rightBtnsP2P</c:if>">
                                            <tiles:insert definition="createTemplateConfirmButton" flush="false">
                                                <tiles:put name="commandTextKey"    value="button.quicly.save.template"/>
                                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                                <tiles:put name="templateNameId"    value="#templateName"/>
                                                <tiles:put name="onSaveFunction"    value="saveTemplate($('#templateName').val());"/>
                                                <tiles:put name="btnId"             value="saveTemplateBtn"/>
                                            </tiles:insert>
                                            &nbsp;
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey"    value="button.cancel"/>
                                                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                                <tiles:put name="viewType"          value="simpleLink"/>
                                                <tiles:put name="onclick"           value="cancelTemplateRow();"/>
                                            </tiles:insert>
                                            <tiles:insert definition="window" flush="false">
                                                <tiles:put name="id" value="oneTimePasswordWindow"/>
                                            </tiles:insert>
                                            <span class="createTemplateRowHint">
                                                <a class="imgHintBlock" onclick="javascript:openFAQ('${faqLink}');" title="Часто задаваемые вопросы"></a>
                                            </span>
                                        </div>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                   </c:if>
                    <div class="clear"></div>
                </c:if>
                <c:if test="${phiz:impliesService('ReminderManagment')}">
                    <tiles:put name="reminder" type="string">
                        <div style="display:none">
                            <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/saveReminderBlock.jsp" flush="false">
                                <tiles:put name="defaultName"><c:out value='${form.templateName}'/></tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </c:if>
				<tiles:put name="buttons">
                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/view-payment-buttons.jsp" flush="false"/>
                </tiles:put>
                <c:choose>
                    <c:when test="${metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim'
                                    || metadata.form.name == 'DelayP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim'
                                    || metadata.form.name == 'CreateMoneyBoxPayment' || metadata.form.name == 'EditMoneyBoxPayment' || isMoneyBoxStatusModClaim}">

                    </c:when>

                    <c:when test="${state=='DELAYED_DISPATCH' || state=='OFFLINE_DELAYED'}">
                        <tiles:put name="stamp" value="delayed"/>
                    </c:when>
                    <c:when test="${state == 'DISPATCHED' && mode == 'GUEST' && isLoanCardClaim}">
                        <tiles:put name="stamp" value="processed"/>
                    </c:when>
                    <c:when test="${(state=='DISPATCHED' && isShortLoanClaim) || (form.metadata.name != 'ExtendedLoanClaim' && (state == 'WAIT_TM' || state == 'PREADOPTED'))}">
                        <tiles:put name="stamp" value="processed"/>
                    </c:when>
                    <c:when test="${state=='DISPATCHED' || state=='STATEMENT_READY' || state=='UNKNOW' || state=='SENT' || state=='BILLING_CONFIRM_TIMEOUT'
                                    || state=='BILLING_GATE_CONFIRM_TIMEOUT' || state=='ABS_RECALL_TIMEOUT' || state=='ABS_GATE_RECALL_TIMEOUT'}">
                        <tiles:put name="stamp" value="received"/>
                    </c:when>
                    <c:when test="${state == 'ADOPTED'}">
                        <tiles:put name="stamp" value="accepted"/>
                    </c:when>
                    <c:when test="${state=='EXECUTED' || state=='SUCCESSED' || state=='TICKETS_WAITING'}">
                        <tiles:put name="stamp" value="executed"/>
                    </c:when>
                    <c:when test="${state=='REFUSED'}">
                        <c:if test="${form.metadata.name != 'PFRStatementClaim'}">
                            <tiles:put name="stamp" value="refused"/>
                        </c:if>
                    </c:when>
                    <c:when test="${state=='WAIT_CONFIRM'}">
                        <tiles:put name="stamp" value="waitconfirm"/>
                    </c:when>
                </c:choose>
                <tiles:put name="additionInfo" value="${form.additionPaymentInfo}" type="string"/>
                <tiles:put name="bankBIC" value="${form.bankBIC}"/>
				<tiles:put name="bankName" value="${form.bankName}"/>
                <tiles:put name="byCenter" value="${mode == 'PAYORDER_PAYMENT' ? 'Center' : ''}"/>
                <c:if test="${isEarlyLoanRepaymentClaim}">
                    <tiles:put name="outerData">
                        <div class="float">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.to.loan.page"/>
                                <tiles:put name="commandHelpKey" value="button.to.loan.page"/>
                                <tiles:put name="bundle"         value="loansBundle"/>
                                <tiles:put name="viewType"       value="darkGrayButton"/>
                                <tiles:put name="action"         value="/private/loans/detail.do?id=${form.document.loanLinkId}"/>
                                <tiles:put name="image"          value="back-to-catalog.png"/>
                                <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                                <tiles:put name="imagePosition"  value="left"/>
                            </tiles:insert>
                        </div>
                        <div class="floatRight">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.print"/>
                                <tiles:put name="commandHelpKey" value="button.printCheck.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="printCheck(${form.id}, event);"/>
                                <tiles:put name="image" value="print-check-gray.gif"/>
                                <tiles:put name="imageHover" value="print-check-hover.gif"/>
                                <tiles:put name="imagePosition" value="left"/>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </c:if>
                <c:if test="${isNewP2P}">
                    <tiles:put name="outerData">
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
                        <c:if test="${form.printCheck == true && !isLongOffer}">
                            <div class="floatRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.print"/>
                                    <tiles:put name="commandHelpKey" value="button.printCheck.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="onclick" value="printCheck(${form.id}, event);"/>
                                    <tiles:put name="image" value="print-check-gray.gif"/>
                                    <tiles:put name="imageHover" value="print-check-hover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                </tiles:insert>
                            </div>
                        </c:if>
                    </tiles:put>
                </c:if>
                <c:if test="${metadata.form.name == 'NewRurPayment'}">
                    <tiles:put name="rightDataArea">
                        <c:if test="${supportedActions['isCopyAllowed']}">
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
                        </c:if>
                        <c:if test="${isEnableCreateTemplate && form.externalAccountPaymentAllowed}">
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
                        </c:if>
                        <c:if test="${supportedActions['isAutoTransferAllowed']}">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.makeAutoTransfer"/>
                                <tiles:put name="commandHelpKey" value="button.addAutoPayment.help"/>
                                <tiles:put name="commandTextKey" value="button.addAutoPayment"/>
                                <tiles:put name="bundle"         value="paymentsBundle"/>
                                <tiles:put name="viewType"       value="buttonGrey"/>
                                <tiles:put name="imagePosition"  value="left"/>
                                <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/autoPay.png"/>
                                <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/autoPay-hover.png"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${phiz:impliesService('ReminderManagment') && isEnableCreateTemplate && form.externalAccountPaymentAllowed}">
                            <c:set var="reminderBundleKey" value="button.${metadata.form.name == 'RurPayJurSB' ? 'payment' : 'transfer'}.saveAsReminder"/>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="${reminderBundleKey}"/>
                                <tiles:put name="commandHelpKey" value="${reminderBundleKey}"/>
                                <tiles:put name="bundle"         value="reminderBundle"/>
                                <tiles:put name="image"          value="bellGray.png"/>
                                <tiles:put name="imageHover"     value="bellHover.png"/>
                                <tiles:put name="imagePosition"  value="left"/>
                                <tiles:put name="viewType"       value="buttonGrey"/>
                                <tiles:put name="onclick"        value="openReminderRow();"/>
                            </tiles:insert>
                        </c:if>

                    </tiles:put>
                </c:if>
			</tiles:insert>

            <c:if test="${not empty state}">
                <c:set var="stateDescr">
                    <c:choose>
                        <c:when test="${state=='UNKNOW' or state=='SENT'}">${document.defaultStateDescription}</c:when>
                        <c:when test="${state=='EXECUTED' && form.metadata.name == 'GetPrivateOperationScanClaim'}"><bean:message key="payment.state.hint.GetPrivateOperationScanClaim.EXECUTED" bundle="paymentsBundle"/></c:when>
                        <c:when test="${state=='REFUSED' && isExtendedLoanClaim}"><bean:message key="payment.el.state.hint.${state}" bundle="paymentsBundle"/></c:when>
                        <c:when test="${state=='VISIT_OFFICE' && isExtendedLoanClaim}"><c:out value="${document.visitOfficeReason}"/></c:when>
                        <c:otherwise><bean:message key="payment.state.hint.${state}" bundle="paymentsBundle"/></c:otherwise>
                    </c:choose>
                </c:set>
                <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');" onmouseout="hideLayer('stateDescription');" class="layerFon stateDescription <c:if test="${metadata.form.name == 'NewRurPayment'}">whiteHint</c:if>">
                    <c:choose>
                        <c:when test="${metadata.form.name == 'NewRurPayment'}">
                            <div class="hintsBlock">
                                <div class="hintShadowTriangle"></div>
                                <tiles:insert definition="roundBorder" flush="false">
                                    <tiles:put name="color" value="hintShadow"/>
                                    <tiles:put name="data">
                                        ${stateDescr}
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="floatMessageHeader"></div>
                            <div class="layerFonBlock">
                                ${stateDescr}
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
			</c:if>
            <c:choose>
                <c:when test="${metadata.form.name == 'PFRStatementClaim'}">
                    <script type="text/javascript">
                        replaceStateDescription();
                    </script>
                </c:when>
                <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
                    <c:set var="hdrMessage">
                        <c:choose>
                            <c:when test="${state=='EXECUTED'}">
                                <bean:message key="message.losspassbook.claim.executed" bundle="paymentsBundle"/>
                            </c:when>
                            <c:when test="${state=='DISPATCHED' or state=='DELAYED_DISPATCH'}">
                                <bean:message key="message.losspassbook.claim.delayed"  bundle="paymentsBundle"/>
                            </c:when>
                        </c:choose>
                    </c:set>
                </c:when>
                <%--Для заявок на кредитные карты по предодобренному предложению--%>
                <c:when test="${isLoanCardClaim and document.preapproved}">
                    <c:set var="hdrMessage">
                        Отлично! Заявка успешно отправлена в Банк.<br/>Карта будет выпущена и доставлена в выбранное Вами отделение. Для получения потребуется паспорт или другой документ, удостоверяющий личность. Уточнить, доставлена ли карта, можно по телефону отделения ${phiz:getDepartmentPhoneNumber(document)}
                    </c:set>
                </c:when>
                <c:when test="${isPreapprovedLoanCardClaim || isLoanCardClaim}">
                    <c:set var="hdrMessage">
                        <div class="notice withoutBr"><div class="noticeTitle"> Спасибо! Мы приняли вашу заявку</div>Наш сотрудник свяжется с вами в ближайшее время и проконсультирует по дальнейшим шагам</div>
                    </c:set>
                </c:when>
            </c:choose>
            <c:if test="${not empty hdrMessage}">
                <script type="text/javascript">
                    getFieldError({firstString : '${hdrMessage}', secondString: ''}, "warnings");
                </script>
            </c:if>
            <c:if test="${metadata.form.name == 'AirlineReservationPayment' && state == 'DISPATCHED'}">
                <script type="text/javascript">
                    addMessage("Обратите внимание! Ваша операция обрабатывается.  После успешной обработки будут выпущены билеты. Оставайтесь на текущей странице. Это может занять несколько минут.");
                </script>
            </c:if>
            <c:set var="additionalMessage" value="${phiz:removeSessionAttribute('com.rssl.phizic.web.actions.SESSION_ADDITIONAL_MESSAGE_KEY')}"/>
            <script type="text/javascript">
                addAdditionalMessage('${additionalMessage}');
            </script>
		</tiles:put>
	</tiles:insert>
    <%--для заявки на кредитный отчет удаляем условия и договор с формы и, если документ перешел в состояние "исполнен", через 5 сек перебрасываем клиента на страницу с кредитной историей--%>
    <c:if test="${metadata.form.name == 'CreditReportPayment'}">
        <script type="text/javascript">
            doOnLoad(function() {
                <c:if test="${metadata.form.name == 'CreditReportPayment' && state == 'EXECUTED' && (empty param.history or param.history eq 'false')}">
                 window.setTimeout(function(){
                        window.location = document.webRoot + '/private/credit/report.do'}, 5000);
                </c:if>

            });
        </script>
    </c:if>
</html:form>