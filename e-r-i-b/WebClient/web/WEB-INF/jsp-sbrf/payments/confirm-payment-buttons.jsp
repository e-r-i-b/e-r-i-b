<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="isDocInstanceOfJurPayment" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
<c:set var="hasPfpId" value="${(metadata.form.name == 'AccountOpeningClaim' || metadata.form.name == 'IMAOpeningClaim') && not empty form.document.pfpId}"/>
<c:set var="isITunes" value="${phiz:isITunesDocument(form.document)}"/>
<c:set var="isEarlyLoanRepaymentClaim" value="${metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
<c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'
                                              || metadata.form.name == 'LoanCardOffer'
                                              || metadata.form.name == 'LoanProduct'
                                              || metadata.form.name == 'LoanCardProduct'}"/>


<c:set var="isExtendedLoanClaim" value="${metadata.form.name == 'ExtendedLoanClaim'}"/>
<c:set var="isShortLoanClaim"  value="${metadata.form.name == 'ShortLoanClaim'}"/>
<c:set var="external" value="${form.external}"/>
<c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                      || metadata.form.name == 'SecuritiesTransferClaim'
                                      || metadata.form.name == 'DepositorFormClaim'
                                      || metadata.form.name == 'RecallDepositaryClaim'}"/>
<c:set var="isEarlyLoanRepaymentClaim" value="${metadata.form.name == 'EarlyLoanRepaymentClaim'}"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>

<c:set var="message">
    <c:choose>
        <c:when test="${confirmRequest.strategyType eq 'card'}">
            <c:choose>
                <c:when test="${metadata.form.name == 'RurPayment' or metadata.form.name == 'JurPayment' or metadata.form.name == 'LoanPayment' or metadata.form.name == 'RurPayJurSB' or metadata.form.name == 'CreateAutoPaymentPayment'}">
                    <bean:message key="card.payments.security.message" bundle="securityBundle"/>
                </c:when>
                <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
                    <bean:message key="card.payments.security.message" bundle="securityBundle"/>
                    &nbsp;После исполнения заявки Вам необходимо в течение 3-х дней обратиться в отделение банка, где открыт счет.
                </c:when>
                <c:otherwise>
                    <bean:message key="card.payments.security.param.message" bundle="securityBundle"
                                  arg0="${documentType}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${confirmRequest.strategyType eq 'sms'}">
            <c:choose>
                <c:when test="${metadata.form.name == 'RurPayment' or metadata.form.name == 'JurPayment' or metadata.form.name == 'LoanPayment' or metadata.form.name == 'RurPayJurSB' or metadata.form.name == 'CreateAutoPaymentPayment' or isEarlyLoanRepaymentClaim}">
                    <bean:message key="sms.payments.security.message" bundle="securityBundle"/>
                </c:when>
                <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
                    <bean:message key="sms.payments.message" bundle="securityBundle"/>
                    &nbsp;После исполнения заявки Вам необходимо в течение 3-х дней обратиться в отделение банка, где открыт счет.
                </c:when>
                <c:when test="${metadata.form.name == 'CreditReportPayment'}">
                    <bean:message key="sms.payments.security.param.message" bundle="securityBundle" arg0="${documentType}"/>
                    &nbsp;Вводом пароля Вы подтверждаете оформление платежа за получение кредитного отчета, а так же согласие с договором на оказание услуг по предоставлению кредитного отчета и с условиями передачи и обработки информации из кредитного отчета.
                </c:when>
                <c:otherwise>
                    <bean:message key="sms.payments.security.param.message" bundle="securityBundle"
                                  arg0="${documentType}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${confirmRequest.strategyType eq 'cap'}">
            <c:choose>
                <c:when test="${metadata.form.name == 'RurPayment' or metadata.form.name == 'JurPayment' or metadata.form.name == 'LoanPayment' or metadata.form.name == 'RurPayJurSB' or metadata.form.name == 'CreateAutoPaymentPayment'}">
                    <bean:message key="cap.payments.security.message" bundle="securityBundle"/>
                </c:when>
                <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
                    <bean:message key="cap.payments.message" bundle="securityBundle"/>
                    &nbsp;После исполнения заявки Вам необходимо в течение 3-х дней обратиться в отделение банка, где открыт счет.
                </c:when>
                <c:otherwise>
                    <bean:message key="cap.payments.security.param.message" bundle="securityBundle"
                                  arg0="${documentType}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            Внимательно проверьте, правильно ли Вы заполнили ${documentType}. Для подтверждения операции нажмите на кнопку &laquo;Подтвердить&raquo;.
        </c:otherwise>
    </c:choose>
</c:set>

    <div class="clear"></div>
    <c:if test="${metadata.name != 'RefuseLongOffer'
                    and metadata.name != 'RefuseAutoPaymentPayment' and metadata.form.name != 'RemoteConnectionUDBOClaim'
                    and (metadata.name != 'LoanCardOffer' or phiz:existCreditCardOffer(form.document))
                    and (!isDocInstanceOfJurPayment or not hasServiceProvider or serviceProviderEditPaymentSupported)}">
        <c:set var="backToServiceClass" value="backToService"/>
        <%-- Если есть strategy подтверждения, то необходимо отображать кнопки в 2 ряда.--%>
        <c:if test="${not empty confirmRequest and confirmRequest.strategyType ne 'none' and !hasPfpId}">
            <c:set var="backToServiceClass" value="backToService backToServiceBottom"/>
        </c:if>
         <c:if  test="${!isITunes && !isEarlyLoanRepaymentClaim && fromExtendedLoanClaim != true}">
            <div class="backToService backToServiceBottom <c:if test="${isEarlyLoanRepaymentClaim}">backToProduct</c:if> float">
                <c:if test="${hasPfpId}">
                    <div class="firstLineLink">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.toEditPfpPortfolio"/>
                            <tiles:put name="commandHelpKey" value="button.toEditPfpPortfolio.help"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="blueGrayLink"/>
                            <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${form.document.pfpId}&portfolioId=${form.document.pfpPortfolioId}"/>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                </c:if>
                   <tiles:insert definition="commandButton" flush="false">
                       <tiles:put name="commandKey" value="button.edit"/>
                       <tiles:put name="commandHelpKey" value="button.edit.help"/>
                       <tiles:put name="bundle" value="paymentsBundle"/>
                       <tiles:put name="stateObject" value="document"/>
                       <tiles:put name="viewType" value="buttonGrey"/>
                       <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
                       <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
                       <tiles:put name="imagePosition"  value="left"/>
                   </tiles:insert>
            </div>
        </c:if>
        <c:if  test="${isEarlyLoanRepaymentClaim}">
            <div class="backToProduct">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="stateObject" value="document"/>
                    <tiles:put name="viewType" value="uppercaseType"/>
                    <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcons2.png"/>
                    <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconsHover2.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                </tiles:insert>
            </div>
            <div class="newButtonsLeftArea">
                <c:if test="${not empty confirmRequest}">
                    <c:choose>
                        <c:when test="${mode == 'PAYORDER_PAYMENT'}">
                            <c:set var="ajaxActionUrl" value="/external/async/payments/confirm"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="ajaxActionUrl" value="/private/async/payments/confirm"/>
                        </c:otherwise>
                    </c:choose>
                    <span class="clientButton chooseConfirmStrategy">
                        <tiles:insert definition="confirmButtons" flush="false">
                            <tiles:put name="ajaxUrl" value="${ajaxActionUrl}"/>
                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                            <tiles:put name="message" value="${message}"/>
                            <tiles:put name="formName" value="${metadata.form.name}"/>
                            <tiles:put name="mode" value="${mode}"/>
                            <tiles:put name="stateObject" value="document"/>
                            <tiles:put name="winConfirmName" value="popupConfirmEarlyPayment"/>
                        </tiles:insert>
                    </span>
                </c:if>
            </div>
        </c:if>
    </c:if>
    <div class="buttonsArea">
        <c:if test="${isLoanOffer || metadata.form.name == 'VirtualCardClaim' || metadata.form.name == 'ReIssueCardClaim'}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.loanOffer.print"/>
                <tiles:put name="commandHelpKey" value="button.loanOffer.print.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="printLoan(${form.id}, event);"/>
            </tiles:insert>
        </c:if>
        <c:if test="${not isExtendedLoanClaim and not empty param.history and param.history eq 'true'}">
            <tiles:insert definition="confirmationButton" flush="false">
                <tiles:put name="winId" value="confirmation"/>
                <tiles:put name="title" value="Подтверждение удаления документа"/>
                <tiles:put name="currentBundle" value="commonBundle"/>
                <tiles:put name="confirmCommandKey" value="button.remove"/>
                <tiles:put name="buttonViewType" value="buttonGrey"/>
                <tiles:put name="message"><bean:message key="confirm.text" bundle="paymentsBundle"/></tiles:put>
            </tiles:insert>
        </c:if>
    </div>

    <div class="clear"></div>
    <div class="buttonsArea ${isITunes ? 'buttonAreaITunes' : ''} <c:if test="${form.formName == 'NewRurPayment'}">newButtonsArea</c:if>">
        <c:choose>
            <c:when test="${mode != 'PAYORDER_PAYMENT' and (phiz:isInternetShopOrAeroflotPayment(form.document))}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="claimsBundle"/>
                    <tiles:put name="action" value="/private/payments/internetShops/orderList"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                </tiles:insert>
            </c:when>
            <c:when test="${mode != null and mode == 'PAYORDER_PAYMENT' and external}">
                <%--  никаких кнопок здесь не нужно --%>
            </c:when>
            <c:when test="${isLoanOffer || metadata.form.name == 'VirtualCardClaim' || metadata.form.name == 'NewRurPayment' || metadata.form.name == 'RemoteConnectionUDBOClaim' || isEarlyLoanRepaymentClaim}">
                <%--  не выводим даже пустой div --%>
            </c:when>

            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <c:choose>
                        <c:when test="${isDepositaryClaim}">
                            <tiles:put name="action" value="/private/depo/list"/>
                        </c:when>
                        <c:when test="${isShortLoanClaim or isExtendedLoanClaim}">
                            <tiles:put name="action" value="/private/loans/list"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'LoanCardProduct'}">
                            <tiles:put name="action" value="/private/cards/list"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'AccountChangeInterestDestinationClaim'}">
                            <tiles:put name="action" value="private/accounts/info.do?id=${form.document.accountId}"/>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="action" value="/private/payments"/>
                        </c:otherwise>
                    </c:choose>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    <c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy,'sms')}"/>
    <c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
    <c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>
    <c:if test="${hasSMS || hasCard || hasCap || mode == 'PAYORDER_PAYMENT'}">

        <c:if test="${mode == 'PAYORDER_PAYMENT'}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel.link"/>
                <tiles:put name="commandHelpKey" value="button.cancel.link.help"/>
                <tiles:put name="bundle" value="fnsBundle"/>
                <tiles:put name="viewType" value="boldLink"/>
                <tiles:put name="action" value="/external/payments/system/end"/>
            </tiles:insert>
        </c:if>
    </c:if>

    <c:choose>
        <c:when test="${not empty confirmRequest and !isEarlyLoanRepaymentClaim}">
            <c:choose>
                <c:when test="${mode == 'PAYORDER_PAYMENT'}">
                    <c:set var="ajaxActionUrl" value="/external/async/payments/confirm"/>
                </c:when>
                <c:when test="${fromExtendedLoanClaim eq true}">
                    <c:set var="ajaxActionUrl" value="/private/async/payments/accountOpeningClaim/confirm"/>
                </c:when>
                <c:otherwise>
                    <c:set var="ajaxActionUrl" value="/private/async/payments/confirm"/>
                </c:otherwise>
            </c:choose>
            <span class="clientButton chooseConfirmStrategy">
                <tiles:insert definition="confirmButtons" flush="false">
                    <tiles:put name="ajaxUrl" value="${ajaxActionUrl}"/>
                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                    <tiles:put name="message" value="${message}"/>
                    <tiles:put name="formName" value="${metadata.form.name}"/>
                    <tiles:put name="mode" value="${mode}"/>
                    <tiles:put name="stateObject" value="document"/>
                </tiles:insert>
            </span>
        </c:when>
        <c:when test="${isEarlyLoanRepaymentClaim}"></c:when>
        <c:otherwise>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.confirm"/>
                <tiles:put name="commandHelpKey" value="button.dispatch.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="stateObject" value="document"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>

    <c:if test="${phiz:isScriptsRSAActive()}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>

        <script type="text/javascript">
            <%-- формирование основных данных для ФМ --%>
            new RSAObject().toHiddenParameters();
        </script>
    </c:if>
    <script type="text/javascript">
        doOnLoad(function()
        {
            var button = findCommandButton("button.dispatch");
            if (button)
            {
                button.validationFunction = function()
                {
                    <c:choose>
                        <c:when test="${metadata.form.name == 'AccountOpeningClaim' or
                                        metadata.form.name eq 'AccountOpeningClaimWithClose' or
                                        metadata.form.name eq 'IMAOpeningClaim' or
                                        metadata.form.name eq 'ChangeDepositMinimumBalanceClaim' or
                                        metadata.form.name eq 'AccountChangeInterestDestinationClaim'or
                                        metadata.form.name eq 'CreditReportPayment'}">
                            return checkClientAgreesCondition(button.commandText);
                        </c:when>
                        <c:otherwise>
                            return true;
                        </c:otherwise>
                    </c:choose>
                }
            }
        });
    </script>

    <div class="clear"></div>
    </div>
    <c:if test="${metadata.form.name == 'RemoteConnectionUDBOClaim'}">
        <%--кнопка назад для Удаленного подключения УДБО--%>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandTextKey" value="button.prev"/>
            <tiles:put name="commandKey" value="button.edit"/>
            <tiles:put name="commandHelpKey" value="button.edit.help"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="viewType" value="blueGrayLink"/>
        </tiles:insert>
    </c:if>
<c:if  test="${isITunes}">
    <div class="backToService backToServiceBottom rowITunes" style="float: left;">
        <c:if test="${hasPfpId}">
            <div class="firstLineLink">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.toEditPfpPortfolio"/>
                    <tiles:put name="commandHelpKey" value="button.toEditPfpPortfolio.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${form.document.pfpId}&portfolioId=${form.document.pfpPortfolioId}"/>
                </tiles:insert>
            </div>
            <div class="clear"></div>
        </c:if>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.edit"/>
            <tiles:put name="commandHelpKey" value="button.edit.help"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="stateObject" value="document"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
            <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
    </div>
</c:if>