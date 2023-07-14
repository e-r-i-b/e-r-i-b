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
<html:form action="/private/payments/autopayment" onsubmit="return setEmptyAction()">
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
    <c:set var="depoLinkId" value="${param['depoLinkId']}"/>
    <c:set var="isAutoSub" value="${isLongOffer && metadata.form.name == 'RurPayJurSB'}"/>
    <c:set var="isAutoSubParams" value="${isAutoSub && form.document.state.code == 'INITIAL_LONG_OFFER'}"/>
    <c:set var="isEditAutoSub" value="${metadata.form.name == 'EditAutoSubscriptionPayment' || metadata.form.name == 'EditAutoPaymentPayment'}"/>
    <c:set var="isDelayAutoSub"  value="${metadata.form.name == 'DelayAutoSubscriptionPayment'}"/>
    <c:set var="isCloseAutoSub"  value="${metadata.form.name == 'CloseAutoSubscriptionPayment'}"/>
    <c:set var="isRecoveryAutoSub"  value="${metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"/>
    <c:set var="isLoyaltyRegistration"  value="${metadata.form.name == 'LoyaltyProgramRegistrationClaim'}"/>

    <c:set var="isAutoSubChange" value="${metadata.form.name == 'CloseAutoSubscriptionPayment'
                                              || metadata.form.name == 'DelayAutoSubscriptionPayment'
                                              || metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"/>
    <c:set var="isJurPayment" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>

     <c:choose>
       <c:when test="${isDepositaryClaim}">
           <c:set var="mainmenuButton" value="Depo"/>
       </c:when>
       <c:when test="${isLongOffer || isEditAutoSub || isAutoSubChange || isCloseAutoSub || isDelayAutoSub}">
           <c:set var="mainmenuButton" value=""/>
       </c:when>
       <c:otherwise>
           <c:set var="mainmenuButton" value="Payments"/>
       </c:otherwise>
    </c:choose>

    <c:set var="externalPayment" value="${form.externalPayment}"/>


	<tiles:insert definition="${mode != null && mode == 'PAYORDER_PAYMENT' ? 'fnsMain' : 'paymentMain'}">
        <c:if test="${mode != 'PAYORDER_PAYMENT'}">
                <%-- заголовок --%>
                <tiles:put name="pageTitle" type="string">
                    <c:out value="${metadata.form.description}"/>
                </tiles:put>

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
                            <tiles:put name="action" value="/private/autopayment/select-category-provider"/>
                        </tiles:insert>

                </tiles:put>
               <tiles:put name="breadcrumbs">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="main" value="true"/>
                        <tiles:put name="action" value="/private/accounts.do"/>
                    </tiles:insert>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" value="Мои автоплатежи"/>
                        <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
                    </tiles:insert>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" value="Подключение автоплатежа"/>
                        <tiles:put name="last" value="true"/>
                    </tiles:insert>

                </tiles:put>
        </c:if>


		<%-- собственно данные --%>
		<tiles:put name="data" type="string">
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
                                    || metadata.form.name == 'ReIssueCardClaim'}"/>
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
            <c:when test="${metadata.form.name == 'VirtualCardClaim'}">
                <c:set var="idFormForImage" value="virtualCard"/>
            </c:when>
            <c:otherwise>
                <c:set var="idFormForImage" value="${metadata.form.name}"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="id" value="${idFormForImage}"></tiles:put>
            <c:choose>
                <c:when test="${metadata.form.name=='RefuseLongOffer' || metadata.form.name=='RefuseAutoPaymentPayment'}"><c:set var="favouriteName" value="Отмена выполнения автоплатежа (регулярной операции)"/></c:when>
                <c:when test="${isEditAutoSub}"><c:set var="favouriteName" value="Редактирование автоплатежа"/></c:when>
                <c:when test="${metadata.form.name == 'CloseAutoSubscriptionPayment'}"><c:set var="favouriteName" value="Отключение автоплатежа"/></c:when>
                <c:when test="${metadata.form.name == 'DelayAutoSubscriptionPayment'}"><c:set var="favouriteName" value="Приостановка автоплатежа"/></c:when>
                <c:when test="${metadata.form.name == 'RecoveryAutoSubscriptionPayment'}"><c:set var="favouriteName" value="Возобновление автоплатежа"/></c:when>
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
                            <c:set var="favouriteName"><c:out value="Перевод частному лицу в другой банк"/></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="favouriteName"><c:out value="Перевод клиенту Сбербанка"/></c:set>
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
                    <c:when test="${isEditAutoSub}">
                        На данной странице Вы можете изменить параметры выполнения автоплатежа (регулярной операции).
                    </c:when>
                    <c:when test="${metadata.form.name == 'CloseAutoSubscriptionPayment'}">
                        Для того чтобы отменить автоплатеж, проверьте реквизиты заявки и нажмите на кнопку «Оформить заявку»
                    </c:when>
                    <c:when test="${metadata.form.name == 'DelayAutoSubscriptionPayment'}">
                        Для того чтобы приостановить автоплатеж, проверьте реквизиты заявки и нажмите на кнопку «Оформить заявку».
                    </c:when>
                    <c:when test="${metadata.form.name == 'RecoveryAutoSubscriptionPayment'}">
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
                                 || metadata.form.name == 'RefuseAutoPaymentPayment' || metadata.form.name == 'RefuseLongOffer'}">
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
                                    or metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountClosingPayment' or metadata.form.name == 'LoanPayment'
                                    or metadata.form.name == 'VirtualCardClaim' or metadata.form.name == 'AccountOpeningClaimWithClose'}">
                            <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                            <span class="text-red">*</span>
                            <span class="text-gray">.</span>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
            <tiles:put name="stripe">
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
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name">
                        <c:choose>
                            <c:when test="${metadata.form.name == 'RecallDepositaryClaim'}">
                                причина отзыва
                            </c:when>
                            <c:when test="${((isClaim && metadata.form.name != 'PFRStatementClaim')|| isAutoPayment || isLongOffer || metadata.form.name == 'SecurityRegistrationClaim' || metadata.form.name == 'DepositorFormClaim') && !isEditAutoSub}">
                                заполнение заявки
                            </c:when>
                            <c:otherwise>
                                заполнение реквизитов
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="current" value="true"/>
                    <c:if test="${isLoyaltyRegistration}">
                        <tiles:put name="width" value="50%"/>
                    </c:if>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="подтверждение"/>
                    <tiles:put name="show" value="${!isLoyaltyRegistration}"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name">
                        <c:choose>
                            <c:when test="${isLoyaltyRegistration}">
                                статус заявки
                            </c:when>
                            <c:otherwise>
                                статус операции
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <c:if test="${isLoyaltyRegistration}">
                        <tiles:put name="width" value="50%"/>
                    </c:if>
                </tiles:insert>
             </tiles:put>

            <c:if test="${(isServicePayment or externalPayment)}">
                <c:if test="${isJurPayment}">
                    <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.document.receiverInternalId)}"/>
                    <c:if test="${not empty serviceProvider}">
                        <%-- RurPayJurSb наследник JurPayment, значит здесь достпуен поставщик услуг --%>
                        <tiles:put name="imageId" value="${serviceProvider.imageId}"/>
                    </c:if>
                </c:if>
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
            <tiles:put name="buttons">
                <div class="buttonsArea">
                <c:choose>
		            <c:when test="${form.formName == 'FNSPayment'}">
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
		            <c:when test="${mode != null && mode == 'PAYORDER_PAYMENT'}">
			            <tiles:insert definition="clientButton" flush="false">
				            <tiles:put name="commandTextKey" value="button.cancel.link"/>
				            <tiles:put name="commandHelpKey" value="button.cancel.link.help"/>
				            <tiles:put name="bundle" value="fnsBundle"/>
				            <tiles:put name="viewType" value="boldLink"/>
				            <tiles:put name="action" value="/external/payments/system/end"/>
			            </tiles:insert>
            		</c:when>
	                <c:otherwise>
		                <tiles:insert definition="clientButton" flush="false">
			                <tiles:put name="commandTextKey" value="button.back"/>
			                <tiles:put name="commandHelpKey" value="button.back.help"/>
			                <tiles:put name="bundle" value="claimsBundle"/>
                			<tiles:put name="action" value="${isDepositaryClaim ? '/private/depo/list' : '/private/autopayment/select-category-provider'}"/>
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
                        </c:when>
                        <c:when test="${metadata.form.name == 'LossPassbookApplication'}">
                            <c:set var="buttonSaveText" value="button.send"/>
                            <c:set var="buttonSaveTextHelp" value="button.send.help"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'IMAOpeningClaim'}">
                            <c:set var="buttonSaveText" value="button.open"/>
                            <c:set var="buttonSaveTextHelp" value="button.open.help"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountOpeningClaimWithClose'}">
                            <c:set var="buttonSaveText" value="button.open"/>
                            <c:set var="buttonSaveTextHelp" value="button.open.help"/>
                            <c:set var="infoActionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/deposits/details.do?id=')}${form.document.accountId}&group=${form.document.accountGroup}&segment=${form.document.segment}"/>
                            <c:set var="depositDetailWinId" value="depositDetail"/>
                            <script type="text/javascript">
                                function showDetails()
                                {
                                    win.open('${depositDetailWinId}');
                                    return false;
                                };
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
                                };
                            </script>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id"          value="${moblieOperatorWinId}" />
                                <tiles:put name="loadAjaxUrl" value="${infoActionUrl}" />
                            </tiles:insert>
                        </c:when>
                        <c:when test="${metadata.form.name == 'RefuseLongOffer'}">
                            <c:set var="buttonSaveText" value="button.next"/>
                            <c:set var="buttonBundle" value="commonBundle"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'PFRStatementClaim'}">
                            <c:set var="buttonSaveText" value="button.send"/>
                            <c:set var="buttonSaveTextHelp" value="button.send.help"/>
                        </c:when>
                        <c:when test="${metadata.form.name == 'AccountClosingPayment'}">
                            <c:set var="buttonSaveText" value="button.text.close"/>
                            <c:set var="buttonBundle"   value="claimsBundle"/>
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
                <c:if test="${isLoanOffer or metadata.form.name == 'VirtualCardClaim'}">
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
                <c:if test="${isEditAutoSub || isAutoSubChange}">
                    <c:set var="buttonSaveText"     value="button.placeAnOrder"/>
                    <c:set var="buttonSaveTextHelp" value="button.placeAnOrder"/>
                </c:if>
                <c:if test="${isAutoSubParams}">
                    <c:set var="buttonSaveText"     value="button.placeAnOrder"/>
                    <c:set var="buttonSaveTextHelp" value="button.placeAnOrder"/>
                </c:if>



                <c:if test="${not empty param.history and param.history eq 'true' and not empty form.document.id}">
                    <tiles:insert definition="confirmationButton" flush="false">
                        <tiles:put name="winId" value="confirmation"/>
                        <tiles:put name="title" value="Подтверждение удаления документа"/>
                        <tiles:put name="currentBundle"  value="commonBundle"/>
                        <tiles:put name="confirmCommandKey" value="button.remove"/>
                        <tiles:put name="buttonViewType" value="buttonGrey"/>
                        <tiles:put name="message"><bean:message key="confirm.text" bundle="paymentsBundle"/></tiles:put>
                    </tiles:insert>
                </c:if>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandTextKey" value="${buttonSaveText}"/>
                    <tiles:put name="commandHelpKey" value="${buttonSaveTextHelp}"/>
                    <tiles:put name="bundle" value="${buttonBundle}"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="stateObject" value="document"/>
                    <tiles:put name="validationFunction" value="checkPayment()"/>
                    <c:if test="${not empty sessionScope['fromBanner']}">
                        <tiles:put name="fromBanner" value="${sessionScope['fromBanner']}"/>
                    </c:if>
                </tiles:insert>
                <div class="clear"></div>
                </div>

                <c:if test="${(not isLongOffer and not isAutoPayment) and (metadata.form.name != 'LoanCardOffer' and metadata.form.name != 'LoanCardProduct' and
                            metadata.form.name != 'LoanProduct' and !externalPayment and !isDepositaryClaim and
                            metadata.form.name != 'JurPayment' and metadata.form.name != 'LoanOffer' and
                            metadata.form.name != 'AccountOpeningClaim' and metadata.form.name != 'AccountOpeningClaimWithClose' and metadata.form.name != 'IMAOpeningClaim' and
                            metadata.form.name != 'RefuseLongOffer') and not isJurPayment}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                        <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="action" value="/private/autopayment/select-category-provider"/>
                        <tiles:put name="image"       value="backIcon.png"/>
                        <tiles:put name="imageHover"     value="backIconHover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </c:if>

		        <c:if test="${form.formName == 'IMAOpeningClaim'}">
 		            <tiles:insert definition="clientButton" flush="false">
			            <tiles:put name="commandTextKey" value="button.back"/>
			            <tiles:put name="commandHelpKey" value="button.back.help"/>
			            <tiles:put name="bundle" value="imaBundle"/>
                		<tiles:put name="action" value="/private/ima/products/list"/>
			            <tiles:put name="viewType" value="boldLink"/>
		            </tiles:insert>
                </c:if>

                <c:if test="${isDepositaryClaim}">
                    <c:choose>
                        <c:when test="${metadata.form.name != 'RecallDepositaryClaim'}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.goto.depoAccounts.list"/>
                                <tiles:put name="commandHelpKey" value="button.goto.depoAccounts.list"/>
                                <tiles:put name="bundle" value="depoBundle"/>
                                <tiles:put name="viewType" value="boldLink"/>
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
                                    <tiles:put name="viewType" value="boldLink"/>
                                    <tiles:put name="action"
                                               value="/private/depo/documents.do?id=${depoLink.id}"/>
                                </tiles:insert>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${(isLoanProduct || isLoanOffer) && (!param['changeLimit'])}">
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

                <c:if test="${not isClaim && not externalPayment && not isLongOffer && not isAutoPayment}">
                    <c:if test="${isJurPayment}">
                        <%--выводим кнопку "Назад" только для биллинговых платежей--%>
                        <c:choose>
                            <c:when test="${empty form.document.receiverInternalId}">
                                <%--Для внешнего получателя переходим на общую форму оплаты юрику--%>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.prev"/>
                                    <tiles:put name="commandHelpKey" value="button.prev.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                    <tiles:put name="action"
                                               value="/private/payments/jurPayment/edit.do?id=${form.id}"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <%--Для внутреннего получателя переходим на первый шаг биллинговой оплаты--%>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.prev"/>
                                    <tiles:put name="commandHelpKey" value="button.prev.help"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="viewType" value="blueGrayLink"/>
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
                            <tiles:put name="commandTextKey" value="button.prev"/>
                            <tiles:put name="commandHelpKey" value="button.prev.help"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="blueGrayLink"/>
                            <tiles:put name="action">${fn:replace(action, quot ,"%22")}</tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                    </c:if>
                </c:if>
                <%-- кнопка назад для автоплатежей --%>
                <c:if test="${isLongOffer}">
                    <c:choose>
                        <c:when test="${isEditAutoSub || isDelayAutoSub || isCloseAutoSub || isRecoveryAutoSub}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.prev"/>
                                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="action" value="private/autosubscriptions/info.do?id=${form.document.number}"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${isAutoSubParams}">
                             <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.edit"/>
                                <tiles:put name="commandTextKey" value="button.prev"/>
                                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${isAutoSub || metadata.form.name == 'CreateAutoPaymentPayment'}">
                             <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.prev"/>
                                <tiles:put name="commandTextKey" value="button.prev"/>
                                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${metadata.form.name == 'EditAutoPaymentPayment'
                                         || metadata.form.name == 'RefuseAutoPaymentPayment'}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.prev"/>
                                <tiles:put name="commandHelpKey" value="button.prev.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="action" value="private/autopayments/info.do?id=${form.document.autoPaymentLinkId}"/>
                                <tiles:put name="viewType" value="boldLink"/>
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

                <c:if test="${metadata.form.name == 'AccountOpeningClaim' or metadata.form.name == 'AccountOpeningClaimWithClose'}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.toChooseDepositList"/>
                        <tiles:put name="commandHelpKey" value="button.toChooseDepositList.help"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="boldLink"/>
                        <tiles:put name="action" value="/private/deposits/products/list.do?form=${metadata.form.name}"/>
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
            </tiles:put>

            <tiles:put name="byCenter" value="${mode == 'PAYORDER_PAYMENT' ? 'Center' : ''}"/>
		</tiles:insert>
        <c:if test="${form.formName == 'FNSPayment'}">
            <script type="text/javascript">
                getFieldError({firstString : 'Вы не можете оплачивать налоги за других физических лиц по законодательству РФ!', secondString : "Пожалуйста, проверьте фамилию, имя, отчество и ИНН плательщика"},  "warnings");
            </script>
        </c:if>
		</tiles:put>
	</tiles:insert>
</html:form>
