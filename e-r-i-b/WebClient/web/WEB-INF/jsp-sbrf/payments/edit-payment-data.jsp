<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="listFormName" value="${form.metadata.name}"/>
<c:set var="paymentFormName" value="${form.metadata.name}" scope="request"/>
<c:set var="metadataPath" value="${form.metadataPath}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="faqTemplate" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q13')}"/>
<c:set var="faqLongOffer" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm18')}"/>
<c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                          || metadata.form.name == 'SecuritiesTransferClaim'
                                          || metadata.form.name == 'DepositorFormClaim'
                                          || metadata.form.name == 'RecallDepositaryClaim'}"/>
<c:set var="isLongOffer" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and form.document.longOffer}"/>
<c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'}"/>
<c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment' || metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
<c:set var="isLoanCardOffer"   value="${metadata.form.name == 'LoanCardOffer'}"/>

<c:set var="isLoanProduct" value="${  metadata.form.name == 'LoanProduct' || metadata.form.name == 'LoanCardProduct'}"/>
<c:set var="depoLinkId" value="${param['depoLinkId']}"/>
<c:set var="isAutoSub" value="${isLongOffer && metadata.form.name == 'RurPayJurSB' || metadata.form.name == 'CreateP2PAutoTransferClaim'}"/>
<c:set var="isAutoSubParams" value="${isAutoSub && form.document.state.code == 'INITIAL_LONG_OFFER'}"/>
<c:set var="isEditAutoSub" value="${metadata.form.name == 'EditAutoSubscriptionPayment' || metadata.form.name == 'EditAutoPaymentPayment'|| metadata.form.name == 'EditP2PAutoTransferClaim'}"/>
<c:set var="isDelayAutoSub"  value="${metadata.form.name == 'DelayAutoSubscriptionPayment' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
<c:set var="isCloseAutoSub"  value="${metadata.form.name == 'CloseAutoSubscriptionPayment' || metadata.form.name == 'CloseP2PAutoTransferClaim'}"/>
<c:set var="isRecoveryAutoSub"  value="${metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"/>
<c:set var="isLoyaltyRegistration"  value="${metadata.form.name == 'LoyaltyProgramRegistrationClaim'}"/>
<c:set var="isChangeDepositMinimumBalance" value="${metadata.form.name == 'ChangeDepositMinimumBalanceClaim'}"/>
<c:set var="isAccountChangeInterestDestinationClaim" value="${metadata.form.name == 'AccountChangeInterestDestinationClaim'}"/>
<c:set var="isRefuseLongOffer" value="${metadata.form.name == 'RefuseLongOffer'}"/>

<c:set var="isAutoSubChange"  value="${metadata.form.name == 'CloseAutoSubscriptionPayment'
                                          || metadata.form.name == 'DelayAutoSubscriptionPayment'
                                          || metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"/>
<c:set var="isJurPayment"     value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
<c:set var="isShortLoanClaim" value="${metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="isExtendedLoanClaim" value="${metadata.form.name == 'ExtendedLoanClaim'}"/>
<c:set var="isETSMLoanOffer" value="${metadata.form.name == 'ETSMLoanOffer'}"/>
<c:set var="isLoanCardClaim" value="${metadata.form.name == 'LoanCardClaim'}"/>
<c:set var="isRecoveryAutoPayment" value="${metadata.form.name == 'RecoveryAutoSubscriptionPayment'  || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
<c:set var="isAutoSubP2PChange"  value="${metadata.form.name == 'CloseP2PAutoTransferClaim'
                                          || metadata.form.name == 'DelayP2PAutoTransferClaim'
                                          || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
<c:set var="isCreditReportPayment" value="${metadata.form.name == 'CreditReportPayment'}"/>
<c:set var="isChangeCreditLimitClaim" value="${metadata.form.name == 'ChangeCreditLimitClaim'}"/>
<c:set var="isEarlyLoanRepaymentClaim" value = "${metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
<c:set var="providerState" value=""/>
<c:if test="${isJurPayment}">
    <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.document.receiverInternalId)}"/>
    <c:if test="${not empty serviceProvider}">
        <c:set var="providerState" value="${serviceProvider.state}"/>
    </c:if>
</c:if>

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
   <c:when test="${isLoanCardClaim || isChangeCreditLimitClaim}">
       <c:set var="mainmenuButton" value="Cards"/>
   </c:when>
   <c:otherwise>
       <c:set var="mainmenuButton" value="Payments"/>
   </c:otherwise>
</c:choose>

<c:set var="externalPayment" value="${form.externalPayment}"/>

<c:set var="hasPfpId" value="${(metadata.form.name == 'AccountOpeningClaim' || metadata.form.name == 'IMAOpeningClaim') && not empty form.document.pfpId}"/>

<c:set var="isShowOnlyConnectionUDBO" value="${metadata.form.name == 'RemoteConnectionUDBOClaim' && phiz:isShowOnlyConnectionUDBO()}"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="isGuestLoanClaim" value="${person == null && (isShortLoanClaim || isExtendedLoanClaim || metadata.form.name == 'LoanCardProduct')}"/>
<c:choose>
    <c:when test="${isGuestLoanClaim}">
        <c:set var="definition" value="guestMain"/>
    </c:when>
    <c:otherwise>
        <c:set var="definition" value="${isShowOnlyConnectionUDBO ? 'login' : mode != null && mode == 'PAYORDER_PAYMENT' ? 'fnsMain' : 'paymentMain'}"/>
    </c:otherwise>
</c:choose>
<tiles:insert definition="${definition}">
    <c:if test="${mode != 'PAYORDER_PAYMENT'}">
        <%-- заголовок --%>
        <tiles:put name="pageTitle" type="string">
            <c:out value="${metadata.form.description}"/>
        </tiles:put>

        <c:if test="${isNewP2P}">
            <tiles:put name="rightSection" value="false"/>
        </c:if>
        <c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
            <tiles:put name="headerGroup" value="true"/>
            <tiles:put name="needHelp" value="true"/>
            <tiles:put name="centerContent" value="true"/>
            <tiles:put name="addStyleClass" value="udboDataWidth"/>
        </c:if>

        <tiles:put name="mainmenu" value="${mainmenuButton}"/>
        <tiles:put name="submenu" type="string" value="${listFormName}"/>
            <%-- меню --%>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="action" value="/private/payments/payments.do?name=${listFormName}"/>
            </tiles:insert>

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.goto.forms.list"/>
                <tiles:put name="commandHelpKey" value="button.goto.forms.list.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="action" value="/private/payments.do"/>
            </tiles:insert>
        </tiles:put>
        <c:if test="${!isLoyaltyRegistration && !isGuestLoanClaim}">
            <tiles:put name="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="main" value="true"/>
                    <tiles:put name="action" value="/private/accounts.do"/>
                </tiles:insert>
                <c:choose>
                   <c:when test="${isShortLoanClaim || isExtendedLoanClaim || isEarlyLoanRepaymentClaim}">
                       <tiles:insert definition="breadcrumbsLink" flush="false">
                           <tiles:put name="name"   value="Кредиты"/>
                           <tiles:put name="action" value="/private/loans/list.do"/>
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
                            <tiles:put name="name" value="Предложения банка"/>
                            <tiles:put name="action" value="/private/loan/loanoffer/show.do"/>
                        </tiles:insert>
                   </c:when>
                    <c:when test="${isLoanCardClaim || isChangeCreditLimitClaim}">
                        <tiles:insert definition="breadcrumbsLink" flush="false">
                            <tiles:put name="name" value="Карты"/>
                            <tiles:put name="action" value="/private/cards/list.do"/>
                        </tiles:insert>
                   </c:when>
                    <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                        <tiles:insert definition="breadcrumbsLink" flush="false">
                            <tiles:put name="name" value="Кредитная история"/>
                            <tiles:put name="action" value="/private/credit/report.do"/>
                        </tiles:insert>
                    </c:when>
                   <c:otherwise>
                       <c:if test="${not (isEditAutoSub || isAutoSubChange || isRefuseLongOffer || isLongOffer || isAutoSubP2PChange)}">
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                                <tiles:put name="action" value="/private/payments.do"/>
                           </tiles:insert>
                       </c:if>
                       <c:if test="${metadata.form.name == 'LoanCardProduct'}">
                           <c:if test="${not empty param.income}">
                               <tiles:insert definition="breadcrumbsLink" flush="false">
                                   <tiles:put name="name" value="Выбор дохода"/>
                                   <tiles:put name="action" value="/private/payments/income_level.do"/>
                               </tiles:insert>
                           </c:if>
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                               <tiles:put name="name" value="Условия по кредитным картам"/>
                               <c:choose>
                                   <c:when test="${param.income == null}">
                                       <tiles:put name="action" value="/private/payments/loan_card_product.do"/>
                                   </c:when>
                                   <c:otherwise>
                                       <tiles:put name="action" value="/private/payments/loan_card_product.do?income=${param.income}"/>
                                   </c:otherwise>
                               </c:choose>
                           </tiles:insert>
                       </c:if>
                       <c:if test="${metadata.form.name == 'LoanProduct'}">
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                               <tiles:put name="name" value="Условия по кредитам"/>
                               <tiles:put name="action" value="/private/payments/loan_product.do"/>
                           </tiles:insert>
                       </c:if>
                       <%--Ссылка на категорию--%>
                       <c:set var="payMsg" value=""/>
                       <c:if test="${form.category != 'DEPOSITS_AND_LOANS' && form.category != 'PFR'}">
                          <c:set var="payMsg" value="Оплата: "/>
                       </c:if>
                       <c:if test="${not empty form.category and not isLongOffer and form.category ne 'TRANSFER'}">
                           <tiles:insert definition="breadcrumbsLink" flush="false">
                               <tiles:put name="name">${payMsg}<bean:message key="category.operations.${form.category}" bundle="paymentServicesBundle"/></tiles:put>
                               <tiles:put name="action" value="/private/payments/category.do?categoryId=${form.category}"/>
                               <tiles:put name="last" value="false"/>
                           </tiles:insert>
                       </c:if>
                   </c:otherwise>
                </c:choose>
                <c:if test="${metadata.form.name == 'LoanCardOffer'}">
                    <c:set var="offerId" value="${form.document.offerId}"/>
                    <c:if test="${phiz:getLoanCardOfferType(offerId) ne 'changeLimit'}">
                        <tiles:insert definition="breadcrumbsLink" flush="false">
                            <tiles:put name="name" value="Индивидуальные условия по кредитным картам"/>
                            <tiles:put name="action" value="/private/payments/loan_card_offer.do"/>
                        </tiles:insert>
                    </c:if>
                </c:if>
                <c:if test="${isLongOffer || isRefuseLongOffer || isEditAutoSub || isAutoSubChange || isAutoSubP2PChange}">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" value="Мои автоплатежи"/>
                        <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <c:choose>
                        <c:when test="${metadata.form.name == 'RefuseLongOffer' or metadata.form.name == 'RefuseAutoPaymentPayment'}">
                            <c:set var="lastLinkName">
                                <c:out value="Отмена автоплатежа"/>
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
                        <c:when test="${isRecoveryAutoPayment}">
                            <c:set var="lastLinkName">
                                <c:out value="Возобновление автоплатежа"/>
                            </c:set>
                        </c:when>
                        <c:when test="${isEditAutoSub}">
                            <c:set var="lastLinkName">
                                <c:out value="Редактирование автоплатежа"/>
                            </c:set>
                        </c:when>
                        <c:when test="${isLongOffer}">
                            <c:set var="lastLinkName">
                                <c:out value="Подключение автоплатежа"/>
                            </c:set>
                        </c:when>
                        <c:when test="${metadata.form.name == 'LoanCardOffer'}">
                            <c:set var="lastLinkName"><c:out value="Заявка на кредитную карту"/></c:set>
                        </c:when>
                        <c:when test="${metadata.form.name == 'RurPayment'}">
                            <c:choose>
                                <c:when test="${form.document.receiverSubType == 'externalAccount'}">
                                    <c:set var="lastLinkName">Перевод частному лицу в другой банк по реквизитам</c:set>
                                </c:when>
                                <c:when test="${form.document.receiverSubType == 'ourCard' or form.document.receiverSubType == 'ourAccount' or  form.document.receiverSubType == 'ourPhone'}">
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


    <%-- собственно данные --%>
    <tiles:put name="data" type="string">
        <c:if test="${isShowOnlyConnectionUDBO}">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="redLight"/>
                <tiles:put name="data">
                    <div class="notice noticeAttention">
                        <div class="noticeTitle"> Для продолжения работы необходимо заключить Договор банковского обслуживания</div>
                        Договор является обязательным условием для работы в интернет-банке
                    </div>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="warningBlock" flush="false">
                <tiles:put name="regionSelector" value="warnings"/>
                <tiles:put name="needWarning" value="false"/>
                <tiles:put name="isDisplayed" value="false"/>
            </tiles:insert>
        </c:if>

    <script type="text/javascript">
        $(document).ready(function(){
            initPaymentTabIndex();
        });

        var moneyFormat =
        {
            <logic:iterate id="element" collection="${phiz:getMoneyFormat()}" indexId="i">
                <c:if test="${i > 0}">,</c:if>
                ${element.key} : "${element.value}"
            </logic:iterate>
        };
        <%-- Заполняем карту < ISO-код валюты, символ валюты > --%>
        <c:forEach var="entry" items="${phiz:getCurrencySignMap()}">
            currencySignMap.map['${entry.key}'] = '${entry.value}';
        </c:forEach>
    </script>
    <html:hidden property="form"/>
    <html:hidden property="template"/>
    <c:if test="${form.document.state == 'INITIAL'
                    && (metadata.form.name == 'InternalPayment'
                        || metadata.form.name == 'RurPayment'
                            && not empty form.document.receiverSubType
                            && form.document.receiverSubType != 'externalAccount'
                        || metadata.form.name == 'NewRurPayment')
                    && phiz:isNeedToShowP2PPromo()}">
        <c:set var="metadataFormName" value="${metadata.form.name}" scope="request"/>
        <c:if test="${phiz:impliesService('CreateP2PAutoTransferClaim')}">
            <jsp:include page="/WEB-INF/jsp-sbrf/payments/p2p-banner.jsp"/>
        </c:if>
    </c:if>
    <tiles:insert page="paymentContext.jsp" flush="false"/>
    <c:if test="${metadata.form.name == 'LoanPayment'}">
        <jsp:include page="../loans/annLoanMessageWindow.jsp" flush="false"/>
    </c:if>
    <c:set var="isServicePayment" value="${metadata.form.name == 'RurPayJurSB' && !isLongOffer}"/>
    <c:set var="isClaim" value="${ metadata.form.name == 'BlockingCardClaim'
                                || metadata.form.name == 'LossPassbookApplication'
                                || metadata.form.name == 'RefuseLongOffer'
                                || metadata.form.name == 'PFRStatementClaim'
                                || metadata.form.name == 'AccountOpeningClaim'
                                || metadata.form.name == 'AccountOpeningClaimWithClose'
                                || metadata.form.name == 'AccountClosingPayment'
                                || metadata.form.name == 'IMAOpeningClaim'
                                || metadata.form.name == 'LoyaltyProgramRegistrationClaim'
                                || metadata.form.name == 'VirtualCardClaim'
                                || metadata.form.name == 'ReIssueCardClaim'
                                || metadata.form.name == 'ChangeDepositMinimumBalanceClaim'
                                || metadata.form.name == 'AccountChangeInterestDestinationClaim'}"/>
    <c:set var="isAutoPayment" value="${metadata.form.name == 'EditAutoPaymentPayment'
                                     || metadata.form.name == 'CreateAutoPaymentPayment'
                                     || metadata.form.name == 'RefuseAutoPaymentPayment'}"/>
    <c:set var="isLongOfferPayment" value="${metadata.form.name == 'InternalPayment'
                                          || metadata.form.name == 'ConvertCurrencyPayment'
                                          || metadata.form.name == 'RurPayment'
                                          || metadata.form.name == 'JurPayment'
                                          || metadata.form.name == 'LoanPayment'
                                          || metadata.form.name == 'RurPayJurSB'}"/>


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
        <c:when test="${isLoanCardClaim}">
            <c:set var="idFormForImage" value="cardClaim"/>
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
    <tiles:put name="id" value="${idFormForImage}"></tiles:put>
        <c:choose>
            <c:when test="${metadata.form.name=='RefuseLongOffer' || metadata.form.name=='RefuseAutoPaymentPayment'}"><c:set var="favouriteName" value="Отмена выполнения автоплатежа (регулярной операции)"/></c:when>
            <c:when test="${metadata.form.name == 'EditMoneyBoxClaim'}"><c:set var="favouriteName" value="Редактирование копилки"/></c:when>
            <c:when test="${isEditAutoSub}"><c:set var="favouriteName" value="Редактирование автоплатежа"/></c:when>
            <c:when test="${isCloseAutoSub}"><c:set var="favouriteName" value="Отключение автоплатежа"/></c:when>
            <c:when test="${isDelayAutoSub}"><c:set var="favouriteName" value="Приостановка автоплатежа"/></c:when>
            <c:when test="${isRecoveryAutoPayment}"><c:set var="favouriteName" value="Возобновление автоплатежа"/></c:when>
            <c:when test="${metadata.form.name == 'CreateMoneyBoxPayment'}"><c:set var="favouriteName" value="Подключение копилки"/></c:when>
            <c:when test="${(isLongOffer and not(isAutoPayment))}"><c:set var="favouriteName" value="Подключение автоплатежа"/></c:when>
            <c:when test="${isAutoPayment}"><c:set var="favouriteName" value="Подключение автоплатежа"/></c:when>
            <c:when test="${externalPayment && form.formName == 'FNSPayment'}"><c:set var="favouriteName" value="Оплата в адрес федеральной налоговой службы (ФНС)"/></c:when>
            <c:when test="${externalPayment && form.formName == 'ExternalProviderPayment'}"><c:set var="favouriteName" value="Оплата заказа ${form.providerName}"/></c:when>
            <c:when test="${externalPayment && form.formName == 'AirlineReservationPayment'}"><c:set var="favouriteName" value="Оплата брони ${form.providerName}"/></c:when>
            <c:when test="${metadata.form.name == 'VirtualCardClaim'}"><c:set var="favouriteName"><bean:message key="virtual.card.description" bundle="paymentsBundle"/></c:set></c:when>
            <c:when test="${metadata.form.name == 'LoanCardProduct'}"><c:set var="favouriteName"><bean:message key="loan.credit.card.product" bundle="paymentsBundle"/></c:set></c:when>
            <c:when test="${metadata.form.name == 'LoanProduct'}"><c:set var="favouriteName"><bean:message key="loan.credit.product" bundle="paymentsBundle"/></c:set></c:when>
            <c:when test="${metadata.form.name == 'RurPayment'}">
                <c:choose>
                    <c:when test="${form.document.receiverSubType == 'externalAccount'}">
                        <c:set var="favouriteName">Перевод частному лицу в другой банк по реквизитам</c:set>
                    </c:when>
                    <c:when test="${form.document.receiverSubType == 'ourCard' or form.document.receiverSubType == 'ourAccount' or  form.document.receiverSubType == 'ourPhone'}">
                        <c:set var="favouriteName">Перевод клиенту Сбербанка</c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="favouriteName">Перевод на карту в другом банке</c:set>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${isEarlyLoanRepaymentClaim}">
                <c:choose>
                    <c:when test="${form.document.partial}">
                        <c:set var="favouriteName">Частичное досрочное погашение</c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="favouriteName">Полное досрочное погашение</c:set>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise><c:set var="favouriteName" value="${metadata.form.description}"/></c:otherwise>
        </c:choose>
        <tiles:put name="title" value="${favouriteName}"/>
        <tiles:put name="name" value="${favouriteName}"/>
        <tiles:put name="isServicePayment" value="${isServicePayment}"/>
        <tiles:put name="description">
            <c:choose>
                <c:when test="${metadata.form.name == 'PFRStatementClaim'}">
                    <c:choose>
                        <c:when test="${form.document.sendMethod == 'USING_PASPORT_WAY'}">
                            На данной странице Вы можете создать заявку на получение выписки из Пенсионного фонда России.
                            Обратите внимание: заявка формируется по Вашим паспортным данным.
                        </c:when>
                        <c:otherwise>
                            На данной странице Вы можете создать заявку на получение выписки из Пенсионного фонда России.
                            Обратите внимание: заявка формируется по страховому номеру индивидуального лицевого счета.
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${isAutoSubParams}">
                    Заполните поля формы и нажмите кнопку «Оформить заявку».<br/>
                    <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                    <span class="text-red">*</span>
                    <span class="text-gray">.</span>
                </c:when>
                <c:when test="${isAutoSub}">
                    Для того чтобы оформить автоплатеж заполните поля формы и нажмите на кнопку «Продолжить».<br/>
                </c:when>
                <c:when test="${isLoyaltyRegistration}">
                    <div class="loyaltyPaymentHeader">
                        <bean:message key="label.loyalty.registration.text" bundle="loyaltyBundle"/>  <br/>
                    </div>
                </c:when>
                <c:when test="${isEditAutoSub}">
                    На данной странице Вы можете изменить параметры выполнения автоплатежа (регулярной операции).
                </c:when>
                <c:when test="${isCloseAutoSub}">
                    Для того чтобы отменить автоплатеж, проверьте реквизиты заявки и нажмите на кнопку «Оформить заявку»
                </c:when>
                <c:when test="${isDelayAutoSub}">
                    Для того чтобы приостановить автоплатеж, проверьте реквизиты заявки и нажмите на кнопку «Оформить заявку».
                </c:when>
                <c:when test="${isRecoveryAutoPayment}">
                    Для того чтобы возобновить автоплатеж, проверьте реквизиты заявки и нажмите на кнопку «Оформить заявку».
                </c:when>
                <c:when test="${isLongOffer and not(isAutoPayment)}">
                    Используйте эту страницу, если Вы часто совершаете один и тот же платеж. Для создания автоплатежа (регулярной операции) заполните его параметры и нажмите на кнопку «Сохранить».
                    После рассмотрения банком данного платежа операция будет выполняться автоматически в соответствии с заданными параметрами.
                </c:when>
                <c:when test="${isServicePayment}">
                    Заполните поля формы и нажмите на кнопку «Продолжить».
                    <br/>
                    <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                    <span class="text-red">*</span>
                    <span class="text-gray">.</span>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${(metadata.form.name == 'LoanCardOffer' && !param['changeLimit']) || metadata.form.name == 'DepositorFormClaim'
                             || metadata.form.name == 'RefuseAutoPaymentPayment' || metadata.form.name == 'RefuseLongOffer' || isLoanCardClaim}">
                            <c:out value="${metadata.form.detailedDescription}" escapeXml="false"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'LoanCardOffer' && param['changeLimit']}">
                            <c:set var="bankCardLoanLink" value="${phiz:getBankCardLoanLink()}"/>
                            Заполните форму и нажмите на кнопку «Отправить заявку». Лимит по Вашей кредитной карте будет изменен в течение 1-2 рабочих дней.
                            Подробнее условия  получения  кредитной карты на специальных условиях  можно посмотреть <a href='${bankCardLoanLink}' target='_blank'>здесь</a>.
                        </c:when>
                        <c:when test="${metadata.form.name == 'IMAPayment'}">
                            <c:set var="imaDescriptionInfo0" value="${metadata.form.detailedDescription}"/>
                            <c:out value="${imaDescriptionInfo0}"/><span class="bold">${imaDescriptionInfo1}</span>${imaDescriptionInfo2}
                        </c:when>
                        <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
                            <c:out value="${metadata.form.detailedDescription}"/>
                            <%--todo убрать после того как во всех ТБ на КСШ будет реализован фукционал подачи заявки об утере сберкнижки (CHG039252)--%>
                            <c:if test="${not phiz:impliesService('LossPassbookApplicationThroughKSSH')}">
                                <%--Если заявка не через КСШ, то дополняем текст сообщением--%>
                                Для получения дубликата сберкнижки или для закрытия счета Вам необходимо явиться в учреждение Сбербанка России, где открыт счет.
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${metadata.form.detailedDescription}"/>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <c:if test="${metadata.form.name == 'InternalPayment' or metadata.form.name == 'ConvertCurrencyPayment' or metadata.form.name == 'RurPayment' or metadata.form.name == 'JurPayment'
                                or metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountClosingPayment' or metadata.form.name == 'LoanPayment' or isLoanOffer or isLoanProduct
                                or metadata.form.name == 'VirtualCardClaim' or metadata.form.name == 'AccountOpeningClaimWithClose' or metadata.form.name == 'ChangeDepositMinimumBalanceClaim'
                                or metadata.form.name == 'AccountChangeInterestDestinationClaim' or metadata.form.name == 'CreditReportPayment'}">
                        <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                        <span class="text-red">*</span>
                        <span class="text-gray">.</span>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <%--если документ - это расширенная заявка на кредит - нужна специальная шапка и блок с параметрами кредита--%>
        <c:if test="${isExtendedLoanClaim}">
           <tiles:put name="customHeader">
               <tiles:insert page="/WEB-INF/jsp-sbrf/payments/extendedLoanClaimHeader.jsp" flush="false"/>
           </tiles:put>
        </c:if>

        <c:if test="${isETSMLoanOffer}">
            <tiles:put name="customHeader">
                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/ETSMLoanOfferHeader.jsp" flush="false"/>
            </tiles:put>
        </c:if>

        <%--если документ - это короткая заявка на кредит - нужна специальная шапка и блок с параметрами кредита--%>
        <c:if test="${isShortLoanClaim}">
            <tiles:put name="customHeader">
                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/shortLoanClaimHeader.jsp" flush="false"/>
            </tiles:put>
        </c:if>

        <c:if test="${isChangeCreditLimitClaim}">
            <tiles:put name="customHeader">
                <div class="underHeadDesc"><bean:message key="label.ChangeCreditLimitClaim.desc" bundle="loanOfferBundle"/></div>
            </tiles:put>
        </c:if>
        <c:if test="${isEarlyLoanRepaymentClaim}">
            <tiles:put name="customHeader">
                <c:choose>
                    <c:when test="${form.document.partial}">
                        <div class="h-desc">Пожалуйста, обеспечьте наличие суммы на счете к дате платежа. Досрочно <br /> погашается основной долг и проценты на дату платежа. Оплата возможна только<br /> в рабочие банковские дни.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="h-desc">Пожалуйста, обеспечьте наличие суммы на счете к дате платежа. При полном погашении уплачивается основной долг и проценты за пользование кредитом.</div>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </c:if>
        <c:if test="${isLoyaltyRegistration}">
            <tiles:put name="favouriteArea">
                <div class="underHeader">
                    <bean:message key="label.loyalty.registration.desc" bundle="loyaltyBundle"/>
                </div>
            </tiles:put>
        </c:if>
        <c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
            <tiles:put name="dblLineStripe" value="true"/>
        </c:if>
        <c:if test="${not  (isShortLoanClaim || isExtendedLoanClaim || isLoanCardClaim || isChangeCreditLimitClaim)}">
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
                            <c:when test="${isLoanOffer || isLoanProduct || isLoanCardOffer}">
                                выбор условий
                            </c:when>
                            <c:when test="${metadata.form.name == 'IMAOpeningClaim'}">
                                выбор металла
                            </c:when>
                            <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                                Условия договора
                            </c:when>
                            <c:otherwise>
                                выбор операции
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="future" value="false"/>
                    <c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim' && form.document.state.code == 'INITIAL'}">
                        <tiles:put name="current" value="true"/>
                    </c:if>
                    <tiles:put name="show" value="${metadata.form.name != 'LoyaltyProgramRegistrationClaim'}"/>
                </tiles:insert>
            </c:if>

            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name">
                    <c:choose>
                        <c:when test="${metadata.form.name == 'RecallDepositaryClaim'}">
                            причина отзыва
                        </c:when>
                        <c:when test="${((isClaim && metadata.form.name != 'PFRStatementClaim')|| isAutoPayment || isLongOffer || metadata.form.name == 'SecurityRegistrationClaim' || metadata.form.name == 'DepositorFormClaim') && !isEditAutoSub}">
                            заполнение заявки
                        </c:when>
                        <c:when test="${isLoanOffer || isLoanProduct || isLoanCardOffer}">
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
                <c:if test="${metadata.form.name != 'RemoteConnectionUDBOClaim' || form.document.state.code != 'INITIAL'}">
                    <tiles:put name="current" value="true"/>
                </c:if>
                <c:if test="${isLoyaltyRegistration}">
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
                <tiles:put name="show" value="${!isLoyaltyRegistration}"/>
                <c:if test="${isCreditReportPayment}">
                    <tiles:put name="width" value="33%"/>
                </c:if>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name">
                    <c:choose>
                        <c:when test="${isLoanOffer || isLoanProduct || isLoanCardOffer}">
                            статус
                        </c:when>
                        <c:when test="${isLoyaltyRegistration}">
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
                <c:if test="${isLoyaltyRegistration}">
                    <tiles:put name="width" value="50%"/>
                </c:if>
                <c:if test="${isCreditReportPayment}">
                    <tiles:put name="width" value="33%"/>
                </c:if>
            </tiles:insert>
         </tiles:put>
        </c:if>

        <tiles:put name="showHeader" value="${not (isShortLoanClaim || isExtendedLoanClaim || isETSMLoanOffer || isNewP2P || isChangeCreditLimitClaim || isEarlyLoanRepaymentClaim)}"/>
        <c:if test="${!externalPayment and not isClaim and metadata.form.name != 'RecallDepositaryClaim' and metadata.form.name != 'RemoteConnectionUDBOClaim' and not isServicePayment and not isLongOffer and not isLoanOffer and not isLoanCardOffer and not isLoanProduct and not isShortLoanClaim and not isLoanCardClaim and not isCreditReportPayment and not isChangeCreditLimitClaim and not isEarlyLoanRepaymentClaim}">
            <tiles:put name="favouriteArea">
                <tiles:insert definition="addToFavouriteButton" flush="false">
                    <tiles:put name="formName"      value="${favouriteName}"/>
                    <tiles:put name="typeFormat"    value="PAYMENT_LINK"/>
                    <tiles:put name="productId"     value="${form.id}"/>
                    <tiles:put name="dopParam">
                        form=${form.form}<c:if test="${'RurPayment' == idFormForImage}">&receiverSubType=${form.document.receiverSubType}</c:if>&template=${form.template}
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
        </c:if>
        <c:if test="${(isServicePayment or externalPayment) and serviceProvider != null}">
            <%-- RurPayJurSb наследник JurPayment, значит здесь достпуен поставщик услуг --%>
            <tiles:put name="imageId" value="${serviceProvider.imageId}"/>
        </c:if>
        <c:if test="${externalPayment && mode == 'PAYORDER_PAYMENT'}">
            <c:set var="styleFns" value="class='paymentFns'"/>
        </c:if>
        <tiles:put name="data">
            <div onkeypress="onEnterKey(event);">
                <div id="paymentForm" ${styleFns}>
                    ${form.html}
                </div>
            </div>
        </tiles:put>

        <c:if test="${providerState != 'MIGRATION' and (not isClaim and not isLoanOffer  and not isLoanCardOffer and not isLongOffer and not isLoanProduct and not isDepositaryClaim and not externalPayment or metadata.form.name == 'SecuritiesTransferClaim') and metadata.form.name != 'LoyaltyProgramRegistrationClaim' and empty depoLinkId}">

            <%-- кнопка создания автоплатежа --%>
            <c:set var="personInfo" value="${phiz:getPersonInfo()}"/>
            <c:set var="impliesCreateLongOfferPaymentForJur" value="${phiz:impliesService('CreateLongOfferPaymentForJur')}"/>
            <c:set var="impliesCreateLongOfferPaymentForRur" value="${phiz:impliesService('CreateLongOfferPaymentForRur')}"/>
            <c:set var="impliesCreateLongOfferPaymentForOther" value="${phiz:impliesService('CreateLongOfferPayment')}"/>

            <%--проверка прав на доступность создания регилярного платежа или автоплатежа по шинной технологии для выбранной услуги
                 в зависимости от услуги , проверяем есть ли права на тот или иной сервис --%>
            <c:choose>
                <c:when test="${metadata.form.name == 'RurPayment'}">
                    <c:set var="allowedAutoPayment" value="${impliesCreateLongOfferPaymentForRur}"/>
                </c:when>
                <c:when test="${metadata.form.name == 'JurPayment'}">
                    <c:set var="allowedAutoPayment" value="${impliesCreateLongOfferPaymentForJur}"/>
                </c:when>
                <c:when test="${metadata.form.name == 'InternalPayment' || metadata.form.name == 'ConvertCurrencyPayment' || metadata.form.name == 'LoanPayment'}">
                    <c:set var="allowedAutoPayment" value="${impliesCreateLongOfferPaymentForOther}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="allowedAutoPayment" value="false"/>
                </c:otherwise>
            </c:choose>

            <c:if test="${allowedAutoPayment && (not isDepositaryClaim) && personInfo.creationType=='UDBO' && isLongOfferPayment}">
                <tiles:put name="autopay">
                    <div id="saveLongOfferRow">
                        <div class="paymentLabel">
                            <span class="paymentTextLabel">&nbsp;</span>
                        </div>
                        <div id="makeLongOfferButton" class="paymentValue">
                            <div class="paymentInputDiv bold">
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey"     value="button.makeLongOffer"/>
                                    <tiles:put name="commandHelpKey" value="button.makeLongOffer"/>
                                    <tiles:put name="bundle"         value="paymentsBundle"/>
                                    <tiles:put name="viewType"       value="blueGrayLink"/>
                                </tiles:insert>
                                <img class="pointer" src="${skinUrl}/images/icon_RegularPayment.gif" alt="" onclick="findCommandButton('button.makeLongOffer').click();"/>
                                <a class="imgHintBlock" onclick="javascript:openFAQ('${faqLongOffer}');" title="Часто задаваемые вопросы"></a>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
             </c:if>
        </c:if>
        <tiles:put name="buttons">
            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/edit-payment-data-buttons.jsp" flush="false">
                <tiles:put name="guest" value="false"/>
                <tiles:put name="registeredGuest" value="false"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="byCenter" value="${mode == 'PAYORDER_PAYMENT' ? 'Center' : ''}"/>
    </tiles:insert>

    <c:set var="stateCode" value="${form.document.state.code}"/>
    <c:if test="${not empty stateCode && isExtendedLoanClaim}">
        <c:set var="stateDescr">
            <bean:message key="payment.state.hint.${stateCode}" bundle="paymentsBundle"/>
        </c:set>
        <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');" onmouseout="hideLayer('stateDescription');" class="layerFon stateDescription">
            <div class="floatMessageHeader"></div>
            <div class="layerFonBlock">
                ${stateDescr}
            </div>
        </div>
    </c:if>

    <c:if test="${form.formName == 'FNSPayment'}">
        <script type="text/javascript">
            getFieldError({firstString : 'Вы не можете оплачивать налоги за других физических лиц по законодательству РФ!', secondString : "Пожалуйста, проверьте фамилию, имя, отчество и ИНН плательщика"},  "warnings");
        </script>
    </c:if>
    </tiles:put>
</tiles:insert>
