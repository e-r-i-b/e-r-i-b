<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<html:form action="${mode != null && mode == 'PAYORDER_PAYMENT' ? '/external/payments/confirm' : '/private/payments/confirm'}" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
<c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
<c:set var="listFormName" value="${form.metadata.listFormName}"/>
<c:set var="paymentFormName" value="${form.metadata.listFormName}" scope="request"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                      || metadata.form.name == 'SecuritiesTransferClaim'
                                      || metadata.form.name == 'DepositorFormClaim'
                                      || metadata.form.name == 'RecallDepositaryClaim'}"/>
<c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'
                                              || metadata.form.name == 'LoanCardOffer'
                                              || metadata.form.name == 'LoanProduct'
                                              || metadata.form.name == 'LoanCardProduct'}"/>
<c:set var="isShortLoan" value="${ metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="isDocInstanceOfJurPayment" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
<c:if test="${isDocInstanceOfJurPayment}">
    <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.document.receiverInternalId)}"/>
</c:if>
<c:set var="isTemplate" value="${form.document.template}"/>
<c:set var="isEditAutoSub" value="${metadata.form.name == 'EditAutoSubscriptionPayment' || metadata.form.name == 'EditAutoPaymentPayment' || metadata.form.name == 'EditP2PAutoTransferClaim'}"/>
<c:set var="isLongOffer" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and form.document.longOffer}"/>
<c:set var="isAutoSubChange" value="${metadata.form.name == 'CloseAutoSubscriptionPayment'
                                              || metadata.form.name == 'DelayAutoSubscriptionPayment'
                                              || metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"/>
<c:set var="isAutoSubP2PChange"  value="${metadata.form.name == 'CloseP2PAutoTransferClaim'
                                          || metadata.form.name == 'DelayP2PAutoTransferClaim'
                                          || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
<c:set var="isRefuseAutoPaymentPayment" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl')}"/>
<c:set var="isRefuseLongOffer" value="${metadata.form.name == 'RefuseLongOffer'}"/>
<c:set var="isShortLoanClaim"  value="${metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="isExtendedLoanClaim" value="${metadata.form.name == 'ExtendedLoanClaim'}"/>
<c:set var="isRecoveryAutoSub" value="${metadata.form.name == 'RecoveryAutoSubscriptionPayment' || metadata.form.name == 'RecoveryP2PAutoTransferClaim'}"/>
<c:set var="isDelayAutoSub" value="${metadata.form.name == 'DelayAutoSubscriptionPayment' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
<c:set var="isCloseAutoSub" value="${metadata.form.name == 'CloseAutoSubscriptionPayment' || metadata.form.name == 'CloseP2PAutoTransferClaim'}"/>
<c:set var="isCreditReportPayment" value="${metadata.form.name == 'CreditReportPayment'}"/>
<c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment' || metadata.form.name == 'CreateP2PAutoTransferClaim' || metadata.form.name == 'EditP2PAutoTransferClaim' || metadata.form.name == 'RecoveryP2PAutoTransferClaim' || metadata.form.name == 'CloseP2PAutoTransferClaim' || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
<c:set var="isEarlyLoanRepaymentClaim" value="${metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
<c:set var="isChangeCreditLimitClaim" value="${metadata.form.name == 'ChangeCreditLimitClaim'}"/>
<c:choose>
    <c:when test="${isDepositaryClaim}">
        <c:set var="mainmenuButton" value="Depo"/>
    </c:when>
    <c:when test="${isTemplate || isLongOffer || isEditAutoSub || isAutoSubChange || isRefuseAutoPayment || isRefuseLongOffer || isAutoSubP2PChange}">
        <c:set var="mainmenuButton" value=""/>
    </c:when>
    <c:when test="${isShortLoanClaim or isExtendedLoanClaim}">
        <c:set var="mainmenuButton" value="Loans"/>
    </c:when>
    <c:when test="${isChangeCreditLimitClaim}">
        <c:set var="mainmenuButton" value="Cards"/>
    </c:when>
    <c:otherwise>
        <c:set var="mainmenuButton" value="Payments"/>
    </c:otherwise>
</c:choose>

<c:set var="isFns" value="${form.fns}"/>
<c:set var="external" value="${form.external}"/>
<c:set var="fns" value="${form.fns}"/>
<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<c:set var="isAutoSub" value="${isLongOffer && metadata.form.name == 'RurPayJurSB'}"/>

<c:set var="mode"  value="${phiz:getUserVisitingMode()}"/>
<c:set var="isGuestLoanClaim" value="${mode == 'GUEST' && (isShortLoanClaim || isExtendedLoanClaim || metadata.form.name == 'LoanCardProduct')}"/>
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
    <c:if test="${not empty confirmRequest and not empty form.autoConfirmRequestType}">
        <tiles:put name="needShowMessages" value="false"/>
    </c:if>
    <c:if test="${mode != 'PAYORDER_PAYMENT'}">
            <tiles:put name="mainmenu" value="${mainmenuButton}"/>
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
            <tiles:put name="pageTitle" type="string">
                <c:out value="${form.formDescription}"/>
            </tiles:put>
            <%--меню--%>
            <tiles:put name="menu" type="string">
                <c:if test="${!form.metadata.needParent}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.addNew"/>
                        <tiles:put name="commandHelpKey" value="button.addNew"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="action" value="/private/payments/add.do?form=${form.formName}"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${form.metadata.filterForm != null}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.close"/>
                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="action" value="/private/payments/payments.do?name=${listFormName}"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>

            <%--"хлебные крошки"--%>
            <c:if test="${!isGuestLoanClaim}">
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
                        <c:when test="${isTemplate}">
                            <tiles:insert definition="breadcrumbsLink" flush="false">
                                <tiles:put name="name" value="Мои шаблоны"/>
                                <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${isLongOffer || isEditAutoSub || isAutoSubChange || isRefuseAutoPayment || isRefuseLongOffer  || isAutoSubP2PChange}}">
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
                                <tiles:put name="name" value="Предложения банка"/>
                                <tiles:put name="action" value="/private/loan/loanoffer/show.do"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                            <tiles:insert definition="breadcrumbsLink" flush="false">
                                <tiles:put name="name" value="Кредитная история"/>
                                <tiles:put name="action" value="/private/credit/report.do"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${isChangeCreditLimitClaim}">
                            <tiles:insert definition="breadcrumbsLink" flush="false">
                                <tiles:put name="name" value="Карты"/>
                                <tiles:put name="action" value="/private/cards/list.do"/>
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
                            <c:when test="${metadata.form.name == 'RurPayment'}">
                                <c:choose>
                                    <c:when test="${form.document.receiverSubType == 'externalAccount'}">
                                        <c:set var="lastLinkName"><c:out value="Перевод частному лицу в другой банк"/></c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="lastLinkName"><c:out value="Перевод клиенту Сбербанка"/></c:set>
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

<tiles:put name="data" type="string"> ${confirmTemplate}
<c:set var="isServicePayment" value="${metadata.form.name == 'RurPayJurSB' && !isLongOffer}"/>
<c:set var="isClaim" value="${metadata.form.name == 'BlockingCardClaim'
                                    || (metadata.form.name == 'LossPassbookApplication')
                                    || (metadata.form.name == 'AccountClosingPayment'
                                    || (metadata.form.name == 'ReIssueCardClaim'))
                                    || metadata.form.name == 'CreateMoneyBoxPayment'}"/>
<c:set var="isAutoPayment" value="${metadata.form.name == 'EditAutoPaymentPayment'
                                     || metadata.form.name == 'CreateAutoPaymentPayment'
                                     || metadata.form.name == 'RefuseAutoPaymentPayment'}"/>

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
<tiles:put name="id" value="${idFormForImage}">
</tiles:put>
<c:choose>
    <c:when test="${isRefuseAutoPayment}">
        <tiles:put name="title">
            <span class="size24">Отмена выполнения автоплатежа (регулярной операции)</span>
        </tiles:put>
    </c:when>
    <c:when test="${metadata.form.name == 'EditMoneyBoxClaim'}">
        <tiles:put name="title">
            <span class="size24">Редактирование копилки</span>
        </tiles:put>
    </c:when>
    <c:when test="${isEditAutoSub}">
        <tiles:put name="title">
            <span class="size24">Редактирование автоплатежа</span>
        </tiles:put>
    </c:when>
    <c:when test="${isCloseAutoSub}">
        <tiles:put name="title">
            <span class="size24">Отключение автоплатежа</span>
        </tiles:put>
    </c:when>
    <c:when test="${isDelayAutoSub}">
        <tiles:put name="title">
            <span class="size24">Приостановка автоплатежа</span>
        </tiles:put>
    </c:when>
    <c:when test="${isRecoveryAutoSub}">
        <tiles:put name="title">
            <span class="size24">Возобновление автоплатежа</span>
        </tiles:put>
    </c:when>
    <c:when test="${metadata.form.name == 'CreateMoneyBoxPayment'}">
        <tiles:put name="title">
            <span class="size24">Подключение копилки</span>
        </tiles:put>
    </c:when>
    <c:when test="${isLongOffer}">
        <tiles:put name="title">
            <span class="size24">Подключение автоплатежа</span>
        </tiles:put>
    </c:when>
    <c:when test="${metadata.name == 'AirlineReservationPayment'}"><tiles:put name="title" value="Оплата брони ${form.providerName}"/></c:when>
    <c:when test="${external && !fns}">
        <tiles:put name="title">
            <span class="size24">${form.providerName}</span>
        </tiles:put>
    </c:when>
    <c:when test="${external && fns}">
        <tiles:put name="title">
            <span class="size24">Оплата в адрес федеральной налоговой службы (ФНС)</span>
        </tiles:put>
    </c:when>
    <c:when test="${metadata.form.name == 'VirtualCardClaim'}"><tiles:put name="title"><span class="size24"><bean:message key="virtual.card.description" bundle="paymentsBundle"/></span></tiles:put></c:when>
    <c:when test="${metadata.form.name == 'LoanCardProduct'}"><tiles:put name="title"><span class="size24"><bean:message key="loan.credit.card.product" bundle="paymentsBundle"/></span></tiles:put></c:when>
    <c:when test="${metadata.form.name == 'LoanProduct'}"><tiles:put name="title"><span class="size24"><bean:message key="loan.credit.product" bundle="paymentsBundle"/></span></tiles:put></c:when>
    <c:when test="${metadata.form.name == 'RurPayment'}">
        <c:choose>
            <c:when test="${form.document.receiverSubType == 'externalAccount'}">
                <tiles:put name="title">
                    <span class="size24">Перевод частному лицу в другой банк</span>
                </tiles:put>
            </c:when>
            <c:when test="${form.document.receiverSubType == 'visaExternalCard' || form.document.receiverSubType == 'masterCardExternalCard'}">
                <tiles:put name="title">
                    <span class="size24">Перевод на карту в другом банке</span>
                </tiles:put>
            </c:when>
            <c:otherwise>
                <tiles:put name="title">
                    <span class="size24">Перевод клиенту Сбербанка</span>
                </tiles:put>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${isNewP2P}">
        <c:set var="receiverSubType" value="${form.document.receiverSubType}"/>
        <c:choose>
            <c:when test="${receiverSubType == 'ourAccount' || receiverSubType == 'externalAccount'}">
                <tiles:put name="title">
                    <span class="size24"><bean:message key="label.translate.bankAccount" bundle="paymentsBundle"/></span>
                </tiles:put>
            </c:when>
            <c:when test="${receiverSubType == 'yandexWallet' || receiverSubType == 'yandexWalletOurContact' || receiverSubType == 'yandexWalletByPhone'}">
                <tiles:put name="title">
                    <span class="size24"><bean:message key="label.translate.yandex" bundle="paymentsBundle"/></span>
                </tiles:put>
            </c:when>
            <c:when test="${receiverSubType == 'visaExternalCard' || receiverSubType == 'masterCardExternalCard' || receiverSubType == 'ourContactToOtherCard'}">
                <tiles:put name="title">
                    <span class="size24"><bean:message key="label.translate.card.otherbank" bundle="paymentsBundle"/></span>
                </tiles:put>
            </c:when>
            <c:otherwise>
                <tiles:put name="title">
                    <span class="size24"><bean:message key="label.translate.ourClient" bundle="paymentsBundle"/></span>
                </tiles:put>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${isCreditReportPayment}">
        <c:set var="document" value="${form.document}"/>
        <c:set var="state" value="${document.state.code}"/>
        <tiles:insert definition="creditReportStatusBlock" flush="false">
            <tiles:put name="documentState" value="${state}"/>
            <tiles:put name="creditReportStatus" value="${document.creditReportStatus}"/>
            <tiles:put name="history" value="${not empty param.history and param.history eq 'true'}"/>
            <tiles:put name="timeOut" value="false"/>
        </tiles:insert>
    </c:when>
    <c:when test="${isEarlyLoanRepaymentClaim}">
        <c:choose>
            <c:when test="${form.document.partial}">
                <span class="size24">Частичное досрочное погашение</span>
            </c:when>
            <c:otherwise>
                <span class="size24">Полное досрочное погашение</span>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <tiles:put name="title">
            <span class="size24">${metadata.form.description}</span>
        </tiles:put>
    </c:otherwise>
</c:choose>

<tiles:put name="alignTable" value="center"/>
<c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
    <tiles:put name="dblLineStripe" value="true"/>
</c:if>
<c:if test="${not isShortLoanClaim and not isExtendedLoanClaim and not isChangeCreditLimitClaim}">
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
        </tiles:insert>
    </c:if>
    <tiles:insert definition="stripe" flush="false">
        <tiles:put name="name">
            <c:choose>
                <c:when test="${metadata.form.name == 'RecallDepositaryClaim'}">
                    причина отзыва
                </c:when>
                <c:when test="${isEditAutoSub}">
                    заполнение реквизитов
                </c:when>
                <c:when test="${isClaim|| isAutoPayment || isLongOffer}">
                    заполнение заявки
                </c:when>
                <c:when test="${isLoanOffer}">
                    оформление заявки
                </c:when>
                <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                    Проверка личной информации
                </c:when>
                <c:otherwise>
                    заполнение заявки
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <tiles:put name="future" value="false"/>
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
        <tiles:put name="current" value="true"/>
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
                <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
                    Cтатус заявки
                </c:when>
                <c:otherwise>
                    статус операции
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <c:if test="${isCreditReportPayment}">
            <tiles:put name="width" value="33%"/>
        </c:if>
    </tiles:insert>
</tiles:put>
</c:if>
<tiles:put name="showHeader" value="${not isShortLoanClaim and not isExtendedLoanClaim and not isNewP2P and not isEarlyLoanRepaymentClaim and not isChangeCreditLimitClaim}"/>
<tiles:put name="description">
    <c:choose>
        <c:when test="${metadata.form.name == 'InternalPayment'
                                or metadata.form.name == 'ConvertCurrencyPayment'
                                or (metadata.form.name == 'LoanPayment' and phiz:getPersonLoanRole(form.document) == 'borrower')
                                or (isServicePayment and  confirmRequest.strategyType == 'none')}">
            Внимательно проверьте реквизиты платежа и нажмите на кнопку «Подтвердить».
        </c:when>
        <c:when test="${metadata.form.name == 'RurPayment'}">
            Внимательно проверьте реквизиты перевода. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${(metadata.form.name == 'RurPayJurSB' && isLongOffer) || isAutoSubChange || isEditAutoSub  || isAutoSubP2PChange}">
            Внимательно проверьте реквизиты заявки. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'JurPayment'
                                or (metadata.form.name == 'LoanPayment' and phiz:getPersonLoanRole(form.document) == 'guarantor')
                                or metadata.form.name == 'RurPayJurSB' or metadata.form.name == 'ExternalProviderPayment'}">
            Внимательно проверьте реквизиты платежа. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountOpeningClaimWithClose'}">
            <c:choose>
                <c:when test="${form.document.depositTariffPlanCode != 0}">
                    Внимательно проверьте реквизиты заявки и ознакомьтесь с условиями открытия вклада.
                    После этого установите галочку в поле «Я согласен с условиями Договора о вкладе» и нажмите на кнопку «Подтвердить по SMS»
                </c:when>
                <c:otherwise>
                    Внимательно проверьте реквизиты заявки и ознакомьтесь с условиями открытия вклада.
                    После этого установите галочку в поле «Я согласен с условиями Договора о вкладе» и нажмите на кнопку
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${form.document.needInitialFee}">
                    «Подтвердить».
                </c:when>
                <c:otherwise>
                    «Подтвердить по SMS».
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${metadata.form.name == 'AccountClosingPayment'}">
            В результате совершения операции рассчитанная сумма остатка средств на вкладе будет
            перечислена на вклад или карту с учетом курса конверсии. Если Вы согласны с условиями
            закрытия вклада, то нажмите на кнопку «Подтвердить».
        </c:when>
        <c:when test="${metadata.form.name == 'PFRStatementClaim'}">
            Для подтверждения запроса выписки из ПФР нажмите на кнопку «Подтвердить».
        </c:when>
        <c:when test="${metadata.form.name == 'CreateAutoPaymentPayment'}">
            Внимательно проверьте реквизиты платежа. После этого подтвердите операцию SMS-паролем.
        </c:when>
        <c:when test="${metadata.form.name == 'RefuseAutoPaymentPayment'
                                 or metadata.form.name == 'RefuseLongOffer'}">
            Для подтверждения <b>отмены выполнения</b> автоплатежа нажмите на кнопку "Подтвердить".
        </c:when>
        <c:when test="${metadata.form.name == 'ReIssueCardClaim'}">
             Внимательно проверьте, правильно ли Вы заполнили реквизиты заявки. Затем подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'DepositorFormClaim'}">
            Внимательно проверьте реквизиты поручения. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'SecurityRegistrationClaim'}">
            Внимательно проверьте реквизиты заявки. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'RecallDepositaryClaim'}">
            Внимательно проверьте реквизиты отзыва документа. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'SecuritiesTransferClaim'}">
            Внимательно проверьте, правильно ли Вы заполнили реквизиты поручения. После этого подтвердите операцию SMS-паролем или паролем с чека.
        </c:when>
        <c:when test="${metadata.form.name == 'LoanOffer'   ||
                        metadata.form.name == 'LoanProduct' ||
                        metadata.form.name == 'LoanCardProduct'  ||
                        metadata.form.name == 'VirtualCardClaim'}">

            Внимательно проверьте, правильно ли Вы заполнили реквизиты заявки. Для подтверждения операции нажмите на кнопку «Подтвердить».
        </c:when>
        <c:when test="${metadata.form.name == 'LoanCardOffer'}">
            Пожалуйста, подтвердите выпуск карты на указанных ниже условиях.
        </c:when>
        <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
            Внимательно проверьте, правильно ли Вы заполнили реквизиты заявления об утере сберегательной книжки. Для подтверждения операции нажмите на кнопку "Подтвердить по SMS" либо "Подтвердить чеком".
        </c:when>
        <c:when test="${metadata.form.name == 'AirlineReservationPayment'}">
            Внимательно проверьте реквизиты платежа. После этого подтвердите операцию SMS-паролем или паролем с чека.<br/>
            <span class="text-gray">Обратите внимание: после подтверждения операции Вы не сможете отозвать данный платеж.</span>
        </c:when>
        <c:when test="${metadata.form.name == 'IMAOpeningClaim'}">
            Внимательно проверьте реквизиты заявки и ознакомьтесь с условиями открытия ОМС, щелкнув по ссылке «Просмотр условий договора». После этого установите галочку в поле «С условиями договора согласен» и нажмите на кнопку «Продолжить»
        </c:when>
        <c:when test="${metadata.form.name == 'ChangeDepositMinimumBalanceClaim'}">
            Внимательно проверьте реквизиты заявки и ознакомьтесь с условиями дополнительного соглашения, щелкнув по ссылке «Просмотр условий дополнительного соглашения». После этого установите галочку в поле «С условиями дополнительного соглашения согласен» и нажмите кнопку «Подтвердить».
        </c:when>
        <c:when test="${metadata.form.name == 'AccountChangeInterestDestinationClaim'}">
            Внимательно проверьте реквизиты заявки и ознакомьтесь с условиями дополнительного соглашения, щелкнув по ссылке «Просмотр условий дополнительного соглашения». После этого установите галочку в поле «С условиями дополнительного соглашения согласен» и нажмите кнопку «Подтвердить».
        </c:when>
        <c:when test="${metadata.form.name == 'CreditReportPayment'}">
            Внимательно проверьте реквизиты платежа, а также ознакомьтесь с условиями договора о предоставлении отчета и условиями передачи и обработки информации из кредитного отчета. После этого установите галочку в поля «Я согласен с договором на оказание услуг по предоставлению кредитного отчета» и «Я согласен с условиями передачи и обработки информации из кредитного отчета». Далее подтвердите документ одноразовым паролем.
        </c:when>
        <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"></c:when>
        <c:otherwise>
            <bean:message key="message.payment.confirm.text" bundle="paymentsBundle"/>
        </c:otherwise>
    </c:choose>
</tiles:put>

<%--если документ - это расширенная или короткая заявка на кредит - нужна специальная шапка и блок с параметрами кредита--%>
<c:if test="${isExtendedLoanClaim or isShortLoanClaim}">
    <tiles:put name="customHeader">
        <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
            <tiles:put name="document" beanName="form" beanProperty="document"/>
            <c:choose>
                <c:when test="${isExtendedLoanClaim}">
                    <tiles:put name="description" value="Отлично! Анкета заполнена. Пожалуйста, проверьте введённые данные"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="description" value=""/>
                </c:otherwise>
            </c:choose>
        </tiles:insert>
    </tiles:put>
</c:if>
<c:if test="${isEarlyLoanRepaymentClaim}">
    <tiles:put name="customHeader">
        <c:choose>
            <c:when test="${form.document.partial}">
                Пожалуйста, обеспечьте наличие суммы на счете к дате платежа. Досрочно<br /> погашается основной долг и проценты на дату платежа. Оплата возможна только<br /> в рабочие банковские дни.
            </c:when>
            <c:otherwise>
                Пожалуйста, обеспечьте наличие суммы на счете к дате платежа. При полном погашении уплачивается основной долг и проценты за пользование кредитом.
            </c:otherwise>
        </c:choose>
    </tiles:put>
</c:if>

<tiles:put name="isServicePayment" value="${isServicePayment}"/>
<c:if test="${(isServicePayment or external) and serviceProvider != null}">
    <%-- RurPayJurSb наследник JurPayment, значит здесь достпуен поставщик услуг --%>
    <tiles:put name="imageId" value="${serviceProvider.imageId}"/>
</c:if>
<c:if test="${external && mode == 'PAYORDER_PAYMENT'}">
    <c:set var="styleFns" value="class='paymentFns'"/>
</c:if>
<tiles:put name="data">

    <c:set var="documentType">
        <c:choose>
            <c:when test="${isClaim}">
                заявление
            </c:when>
            <c:when test="${metadata.form.name == 'RecallDepositaryClaim'}">
                отзыв
            </c:when>
            <c:when test="${metadata.form.name == 'SecurityRegistrationClaim'}">
                заявку
            </c:when>
            <c:when test="${metadata.form.name == 'SecuritiesTransferClaim' || metadata.form.name == 'DepositorFormClaim'}">
                поручение
            </c:when>
            <c:when test="${metadata.form.name == 'RefuseAutoPaymentPayment'
                                 || metadata.form.name == 'RefuseLongOffer'}">
                реквизиты на отмену автоплатежа
            </c:when>
            <c:otherwise>
                реквизиты платежа
            </c:otherwise>
        </c:choose>
    </c:set>

    <div ${styleFns}>
       ${form.html}
    </div>

    <tiles:put name="buttons">
        <tiles:insert page="/WEB-INF/jsp-sbrf/payments/confirm-payment-buttons.jsp" flush="false">
            <c:set var="hasServiceProvider" value="${not empty serviceProvider and serviceProvider != null}"/>
            <tiles:put name="hasServiceProvider" value="${hasServiceProvider}"/>
            <c:if test="${hasServiceProvider}">
                <tiles:put name="serviceProviderEditPaymentSupported" value="${serviceProvider.editPaymentSupported}"/>
            </c:if>
        </tiles:insert>
    </tiles:put>

</tiles:put>
<tiles:put name="byCenter" value="${mode == 'PAYORDER_PAYMENT' ? 'Center' : ''}"/>
</tiles:insert>
<c:if test="${not empty form.document.state.code}">
    <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');"
         onmouseout="hideLayer('stateDescription');" class="layerFon stateDescription">
         <div class="floatMessageHeader"></div>
         <div class="layerFonBlock">
            <bean:message key="payment.state.hint.${form.document.state.code}" bundle="paymentsBundle"/>
         </div>
    </div>
</c:if>
<c:if test="${isFns}">
    <script type="text/javascript">
        getFieldError({firstString : 'Вы не можете оплачивать налоги за других физических лиц по законодательству РФ!', secondString : "Пожалуйста, проверьте фамилию, имя, отчество и ИНН плательщика"}, "warnings");
    </script>
</c:if>
<c:if test="${metadata.form.name == 'PFRStatementClaim'}">
    <script type="text/javascript">
        replaceStateDescription();
    </script>
</c:if>
</tiles:put>
</tiles:insert>

</html:form>
