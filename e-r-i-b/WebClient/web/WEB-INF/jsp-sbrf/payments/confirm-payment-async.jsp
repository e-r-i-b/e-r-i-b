<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<c:choose>
    <c:when test="${mode == 'PAYORDER_PAYMENT'}">
        <c:set var="thisActionUrl" value="/external/async/payments/confirm"/>
    </c:when>
    <c:otherwise>
        <c:set var="thisActionUrl" value="/private/async/payments/confirm"/>
    </c:otherwise>
</c:choose>
<html:form action="${thisActionUrl}" updateAtributes="false">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="formName" value="${metadata.form.name}"/>
    <c:set var="document" value="${form.document}"/>
    <c:set var="isLongOffer" value="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and document.longOffer}"/>
    <c:set var="isAutoSubChange" value="${formName == 'CloseAutoSubscriptionPayment'
                                              || formName == 'DelayAutoSubscriptionPayment'
                                              || formName == 'RecoveryAutoSubscriptionPayment'
                                              || formName == 'EditAutoSubscriptionPayment'}"/>
    <c:set var="isInvoiceSubChange" value="${formName == 'CreateInvoiceSubscriptionPayment'
                                                || formName == 'DelayInvoiceSubscriptionClaim'
                                                || formName == 'RecoveryInvoiceSubscriptionClaim'
                                                || formName == 'CloseInvoiceSubscriptionClaim'}"/>
    <c:set var="documentType">
        <c:choose>
            <c:when test="${formName == 'BlockingCardClaim'
                                || (formName == 'LossPassbookApplication')
                                || (formName == 'AccountClosingPayment')}">
                заявление
            </c:when>
            <c:when test="${formName == 'RecallDepositaryClaim'}">
                отзыв
            </c:when>
            <c:when test="${formName == 'SecurityRegistrationClaim'
                                || (formName == 'AccountOpeningClaim')
                                || metadata.name == 'VirtualCardClaim'
                                || formName == 'ExtendedLoanClaim'}">
                заявку
            </c:when>
            <c:when test="${formName == 'SecuritiesTransferClaim' || formName == 'DepositorFormClaim'}">
                поручение
            </c:when>
            <c:when test="${formName == 'RefuseAutoPaymentPayment'
                                 || formName == 'RefuseLongOffer'}">
                реквизиты на отмену автоплатежа
            </c:when>
            <c:otherwise>
                реквизиты платежа
            </c:otherwise>
        </c:choose>
    </c:set>

    <c:choose>
        <c:when test="${(not empty confirmRequest and confirmRequest.preConfirm) or (not empty form.fields and form.fields.confirmByCard and (empty form.fields.confirmCardId))}">
            <c:set var="message">
                <c:choose>
                    <c:when test="${(confirmRequest.strategyType eq 'card') or (not empty form.fields and form.fields.confirmByCard)}">
                        <c:choose>
                            <c:when test="${formName == 'RurPayment' or formName == 'JurPayment' or formName == 'LoanPayment' or formName == 'RurPayJurSB' or formName == 'CreateAutoPaymentPayment'}">
                                <bean:message key="card.payments.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${formName == 'LossPassbookApplication'}">
                                <bean:message key="card.payments.security.message" bundle="securityBundle"/>
                                &nbsp;После исполнения заявки Вам необходимо в течение 3-х дней обратиться в отделение банка, где открыт счет.
                            </c:when>
                            <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                                <bean:message key="card.credit.report.payment.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"></c:when>
                            <c:otherwise>
                                <c:if test="${formName == 'NewRurPayment'}">
                                    <div class="confirmWinText"></c:if>
                                    <bean:message key="card.payments.security.param.message" bundle="securityBundle"
                                              arg0="${documentType}"/>
                                <c:if test="${formName == 'NewRurPayment'}"></div></c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType eq 'sms'}">
                        <c:choose>
                            <c:when test="${formName == 'CreateInvoiceSubscriptionPayment'}"/>
                            <c:when test="${formName == 'RurPayment' or formName == 'JurPayment' or formName == 'LoanPayment' or formName == 'RurPayJurSB' or formName == 'CreateAutoPaymentPayment'}">
                                <bean:message key="sms.payments.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${formName == 'LossPassbookApplication'}">
                                <bean:message key="sms.payments.message" bundle="securityBundle"/>
                                &nbsp;После исполнения заявки Вам необходимо в течение 3-х дней обратиться в отделение банка, где открыт счет.
                            </c:when>
                            <c:when test="${formName == 'ExtendedLoanClaim'
                                            || formName == 'ShortLoanClaim'}">
                                <bean:message key="sms.extended.loan.claim.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                                <bean:message key="sms.credit.report.payment.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${formName == 'NewRurPayment'}">
                                <div class="confirmWinText">
                                    <bean:message key="sms.p2pPayments.security.param.message" bundle="securityBundle"/>
                                </div>
                            </c:when>
                            <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"></c:when>
                            <c:otherwise>
                                <bean:message key="sms.payments.security.param.message" bundle="securityBundle"
                                              arg0="${documentType}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType eq 'cap'}">
                        <c:choose>
                            <c:when test="${formName == 'RurPayment' or formName == 'JurPayment' or formName == 'LoanPayment' or formName == 'RurPayJurSB' or formName == 'CreateAutoPaymentPayment'}">
                                <bean:message key="cap.payments.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${formName == 'LossPassbookApplication'}">
                                <bean:message key="cap.payments.security.message" bundle="securityBundle"/>
                                &nbsp;После исполнения заявки Вам необходимо в течение 3-х дней обратиться в отделение банка, где открыт счет.
                            </c:when>
                            <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"></c:when>
                            <c:otherwise>
                                <bean:message key="cap.payments.security.param.message" bundle="securityBundle"
                                              arg0="${documentType}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${formName == 'ExtendedLoanClaim'}">
                                <bean:message key="push.extended.loan.claim.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                                <bean:message key="push.credit.report.payment.security.message" bundle="securityBundle"/>
                            </c:when>
                            <c:when test="${formName == 'NewRurPayment'}">
                                <div class="confirmWinText">
                                    <bean:message key="push.p2pPayments.security.param.message" bundle="securityBundle"/>
                                </div>
                            </c:when>
                            <c:when test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}"></c:when>
                            <c:otherwise>
                                Внимательно проверьте, правильно ли Вы заполнили ${documentType}. Для подтверждения операции нажмите на кнопку &laquo;Подтвердить&raquo;.
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:set>

            <c:choose>
                <c:when test="${not empty form.fields and form.fields.confirmByCard}">
                    <c:set var="confirmTemplate" value="confirm_card"/>
                </c:when>
                <c:otherwise>
                    <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                </c:otherwise>
            </c:choose>
            <tiles:insert definition="${confirmTemplate}" flush="false">
                <c:if test="${not empty form.autoConfirmRequestType}">
                    <tiles:put name="showActionMessages" value="true"/>
                </c:if>
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                <tiles:put name="message" value="${message}"/>
                <tiles:put name="guest" value="${form.guest}"/>
                <c:set var="documentShortNumber" value=""/>
                <c:if test="${formName == 'ExtendedLoanClaim'}">
                    <c:set var="documentShortNumber" value="${form.document.bkiClaimNumber}"/>
                </c:if>
                <tiles:put name="documentShortNumber" value="${documentShortNumber}"/>
                <c:if test="${confirmRequest.strategyType == 'card'}">
                    <tiles:put name="cardNumber" value="${phiz:getCutCardNumber(form.fields.confirmCard)}"/>
                </c:if>
                <c:if test="${formName == 'AccountOpeningClaim'}">
                    <c:set var="buttonName"><bean:message key="button.dispatch" bundle="securityBundle"/></c:set>
                    <tiles:put name="validationFunction" value="checkClientAgreesCondition('${buttonName}');"/>
                </c:if>
                <c:choose>
                    <c:when test="${formName == 'SecurityRegistrationClaim' or formName == 'AccountOpeningClaim' or metadata.name == 'VirtualCardClaim'
                                    or formName == 'ReIssueCardClaim' or formName == 'ExtendedLoanClaim'}">
                        <tiles:put name="confirmableObject" value="claim"/>
                    </c:when>
                    <c:when test="${formName == 'DepositorFormClaim'}">
                        <tiles:put name="confirmableObject" value="DepositorFormClaim"/>
                    </c:when>
                    <c:when test="${formName == 'SecuritiesTransferClaim'}">
                        <tiles:put name="confirmableObject" value="securitiesTransferClaim"/>
                    </c:when>
                    <c:when test="${formName == 'LossPassbookApplication'}">
                        <tiles:put name="confirmableObject" value="LossPassbookApplication"/>
                    </c:when>
                    <c:when test="${formName == 'RecallDepositaryClaim'}">
                        <tiles:put name="confirmableObject" value="recall"/>
                    </c:when>
                    <c:when test="${formName == 'RefuseAutoPaymentPayment'
                                 || formName == 'RefuseLongOffer'}">
                        <tiles:put name="confirmableObject" value="RefuseAutopayment"/>
                    </c:when>
                    <c:when test="${(formName == 'RurPayJurSB' && isLongOffer) || isAutoSubChange}">
                        <tiles:put name="confirmableObject" value="createAutoSubscription"/>
                    </c:when>
                    <c:when test="${formName == 'CreateInvoiceSubscriptionPayment'}">
                        <tiles:put name="confirmableObject" value="CreateInvoiceSubscriptionPayment"/>
                        <tiles:put name="buttonType" value="singleRow"/>
                        <tiles:put name="showCancelButton" value="false"/>
                    </c:when>
                    <c:when test="${phiz:isITunesDocument(document)}">
                        <tiles:put name="confirmableObject" value="iTunes"/>
                    </c:when>
                    <c:when test="${formName == 'NewRurPayment'}">
                        <c:set var="receiverSubType" value="${form.document.receiverSubType}"/>
                        <c:choose>
                            <c:when test="${receiverSubType == 'externalAccount'}">
                                <tiles:put name="confirmableObject" value="translateToExternalAccount"/>
                            </c:when>
                            <c:when test="${receiverSubType == 'yandexWallet' || receiverSubType == 'yandexWalletOurContact' || receiverSubType == 'yandexWalletByPhone'}">
                                <tiles:put name="confirmableObject" value="translateYandexWallet"/>
                            </c:when>
                            <c:when test="${receiverSubType == 'visaExternalCard' || receiverSubType == 'masterCardExternalCard' || receiverSubType == 'ourContactToOtherCard'}">
                                <tiles:put name="confirmableObject" value="translateToExternalCard"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="confirmableObject" value="translateOurClient"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${formName == 'RemoteConnectionUDBOClaim'}">
                        <tiles:put name="confirmableObject" value="RemoteConnectionUDBO"/>
                    </c:when>
                    <c:when test="${formName == 'EarlyLoanRepaymentClaim'}">
                        <c:choose>
                            <c:when test="${form.document.partial}">
                                <tiles:put name="confirmableObject" value="${formName}Partial"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="confirmableObject" value="${formName}Full"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
                <tiles:put name="byCenter" value="${mode == 'PAYORDER_PAYMENT' ? 'Center' : ''}"/>
                <tiles:put name="confirmCommandKey" value="button.dispatch"/>
                <tiles:put name="data">
                    <c:choose>
                        <c:when test="${isInvoiceSubChange}"/> <%-- для операций по подписке на инвойсы не нужна форма с полями --%>
                        <c:when test="${formName == 'ExtendedLoanClaim'}">
                            <tiles:insert page="/WEB-INF/jsp-sbrf/loans/extendedLoanClaimConfirmParamsBlock.jsp" flush="false">
                                <tiles:put name="document" beanName="form" beanProperty="document"/>
                                <tiles:put name="guest" value="${form.guest}"/>
                                <tiles:put name="mobileBankExist" value="${form.mobileBankExist}"/>
                            </tiles:insert>
                            <c:if test="${form.guest && form.mobileBankExist}">
                                <hr/>
                                Для отправки заявки, необходимо подтвердить Ваше согласие на запрос банком информации из Бюро кредитных историй. Для этого Вам нужно:
                                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/bki-number-message.jsp" flush="false">
                                    <tiles:put name="documentShortNumber" value="${documentShortNumber}"/>
                                    <tiles:put name="bankPhoneForSms" value="900"/>
                                </tiles:insert>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            ${form.html}
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
                <tiles:put name="useAjax" value="true"/>
                <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
            <script type="text/javascript">

                <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
                    <c:set var="errorEscape" value="${phiz:escapeForJS(error, true)}"/>
                    if (window.addError != undefined)
                    {
                        var error = "${errorEscape}";
                        addError(error, null, true);
                    }
                </phiz:messages>

                <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
                <c:set var="messageEscape" value="${phiz:escapeForJS(messages, true)}"/>
                    if (window.addMessage != undefined)
                    {
                        var message = "${messageEscape}";
                        addMessage(message);
                    }
                </phiz:messages>

                if (window.win != undefined)
                    win.close(confirmOperation.windowId);
            </script>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript">
        removeStateAttr();
    </script>
    <c:if test="${metadata.form.name == 'CreditReportPayment'}">
        <script type="text/javascript">
            var oneTimePasswordWindow = $('#oneTimePasswordWindow');

            $(oneTimePasswordWindow).find('.okb-dogovor-top').remove();
            $(oneTimePasswordWindow).find('.okb-dogovor-text').remove();

            var agreeWithContractCheckbox = $(oneTimePasswordWindow).find('#agreeWithContract');
            $(agreeWithContractCheckbox).attr('checked', 'checked');
            $(agreeWithContractCheckbox).attr('disabled', 'disabled');

            var agreeWithConditionsCheckbox = $(oneTimePasswordWindow).find('#agreeWithConditions');
            $(agreeWithConditionsCheckbox).attr('checked', 'checked');
            $(agreeWithConditionsCheckbox).attr('disabled', 'disabled');
        </script>
    </c:if>
</html:form>