<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="paymentsAndClaimsList"/>
            <c:set var="formdata" value="${form.data}"/>
            <c:set var="fromStart" value="${form.fromStart}"/>
            <c:set var="filters" value="${form.filters}"/>
            <tiles:put name="grid">
                <tiles:importAttribute/>
                <c:set var="globalImagePath" value="${globalUrl}/images"/>
                <c:set var="imagePath" value="${skinUrl}/images"/>

                <sl:collection id="listElement" model="wide-list" property="data" bundle="logBundle" selectBean="businessDocument" styleClass="standartTable">
                    <sl:collectionParam id="selectType" value="checkbox"/>
                    <sl:collectionParam id="selectName" value="selectedIds"/>
                    <sl:collectionParam id="selectProperty" value="id"/>

                    <c:set var="businessDocument" value="${listElement[0]}"/>
                    <c:set var="sender" value="${listElement[1]}"/>
                    <c:set var="isDeleted" value="${phiz:isInstance(sender, 'com.rssl.phizic.business.persons.DeletedPerson')}"/>
                    <c:set var="code" value="${businessDocument.state.code}"/>
                    <c:set var="isLongOffer" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') && businessDocument.longOffer
                                                     || phiz:isInstance(businessDocument,'com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim')}"/>
                    <c:set var="isAutoSubscription" value="${businessDocument.formName == 'RurPayJurSB' && isLongOffer}"/>
                    <c:set var="isAutoSubModify" value="${phiz:isInstance(businessDocument,'com.rssl.phizic.business.documents.payments.AutoSubscriptionPaymentBase')}"/>
                    <input type="hidden" id="${businessDocument.id}" value="${businessDocument.state.code}"/>
                    <input type="hidden" id="S_${businessDocument.id}" value="${businessDocument.documentNumber}"/>
                    <c:set var="claimVievURL" value="${phiz:calculateActionURL(pageContext, '/claims/claim')}?id=${businessDocument.id}"/>
                    <sl:collectionItem title="Дата<br>создания">
                        <c:if  test="${! empty businessDocument.dateCreated}">
                            <c:choose>
                                <c:when test="${phiz:impliesService('ViewPaymentList') or phiz:impliesService('ViewPaymentListUseClientForm')}">
                                    <a href="${claimVievURL}"><bean:write name="businessDocument" property="dateCreated.time" format="dd.MM.yyyy"/></a>
                                </c:when>
                                <c:otherwise>
                                    <bean:write name="businessDocument" property="dateCreated.time" format="dd.MM.yyyy"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Время<br>создания">
                        <c:if  test="${! empty businessDocument.dateCreated}">
                            <c:choose>
                                <c:when test="${phiz:impliesService('ViewPaymentList') or phiz:impliesService('ViewPaymentListUseClientForm')}">
                                    <a href="${claimVievURL}"><bean:write name="businessDocument" property="dateCreated.time" format="HH:mm:ss"/></a>
                                </c:when>
                                <c:otherwise>
                                    <bean:write name="businessDocument" property="dateCreated.time" format="HH:mm:ss"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Номер">
                        <c:choose>
                            <c:when test="${phiz:impliesService('ViewPaymentList') or phiz:impliesService('ViewPaymentListUseClientForm')}">
                                <a href="${claimVievURL}"><c:out value="${businessDocument.documentNumber}"/></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${businessDocument.documentNumber}"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="operationUID_${businessDocument.id}" value="${businessDocument.operationUID}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Вид операции">
                        <c:if test="${isLongOffer}">
                            <img src="${skinUrl}/images/calendar.gif" alt="" border="0"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${isAutoSubscription}">
                                Создание автоплатежа (регулярной операции)
                            </c:when>
                            <c:when test="${businessDocument.formName == 'RurPayment'}">
                                <c:choose>
                                    <c:when test="${businessDocument.receiverSubType == 'externalAccount'}">
                                        Перевод частному лицу в другой банк по реквизитам
                                    </c:when>
                                    <c:when test="${businessDocument.receiverSubType == 'ourCard' or businessDocument.receiverSubType == 'ourAccount' or  businessDocument.receiverSubType == 'ourPhone' or  businessDocument.receiverSubType == 'social'}">
                                        Перевод клиенту Сбербанка
                                    </c:when>
                                    <c:otherwise>
                                        Перевод на карту в другом банке
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <bean:message bundle="auditBundle" key="paymentform.${businessDocument.formName}" failIfNone="false"/>
                            </c:otherwise>
                        </c:choose>

                    </sl:collectionItem>
                    <sl:collectionItem title="Статус">

                        <c:set var="isInstance" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DefaultClaim') ||
                                                         phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DepositOpeningClaim') ||
                                                         phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LoanClaim')}"/>

                        <%--Для заявок на изменение неснижаемого остатка и порядка уплаты процентов--%>
                        <c:set var="isAccountAgreement" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim') ||
                                                                 phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}"/>

                        <c:set var = "isExtendedLoanClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.ExtendedLoanClaim')}"/>
                        <c:set var = "isChangeCreditLimitClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ChangeCreditLimitClaim')}"/>

                        <input type="hidden" id="${businessDocument.id}isInstance" value="${isInstance}">
                        <div class="request_tag_en cursorArrow" style="display:block;" title="${businessDocument.state.description}">
                            <c:choose>
                                <c:when test="${isChangeCreditLimitClaim && code=='EXECUTED'}">
                                    Принято
                                </c:when>
                                <c:when test="${isChangeCreditLimitClaim && code=='REFUSED'}">
                                    Не принято
                                </c:when>
                                <c:when test="${not isInstance && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'))}">
                                    Обрабатывается (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${isInstance && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'))}">
                                    Принят (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${isAccountAgreement && (code=='ERROR')}">
                                    Отказан  (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.PreapprovedLoanCardClaim') && (code=='ADOPTED')}">
                                    Принята (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardClaim') && (code=='ADOPTED')}">
                                    Принята (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.ShortLoanClaim') && (code=='ADOPTED')}">
                                    Принята (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardProductClaim') && (code=='ADOPTED')}">
                                    Принята (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim') && (code=='ADOPTED')}">
                                    Принята (статус для клиента: "Исполняется банком")
                                </c:when>
                                <c:when test="${(code=='INITIAL'|| code=='INITIAL2'||code=='INITIAL3'||code=='INITIAL4'||code=='INITIAL5'||code=='INITIAL6'||code=='INITIAL7'||code=='INITIAL8'|| code=='SAVED') && isExtendedLoanClaim}">Ввод данных СБОЛ</c:when>
                                <c:when test="${code=='INITIAL' || code == 'SAVED'}">Введен (статус для клиента: "Черновик")</c:when>
                                <c:when test="${code=='WAIT_CLIENT_MESSAGE'}">Введен (статус для клиента: "Черновик")</c:when>
                                <c:when test="${code=='DRAFT'}">Частично заполнен (статус для клиента: "Черновик")</c:when>
                                <c:when test="${code=='RECEIVED'}">Получен банком получателем</c:when>
                                <c:when test="${code=='ACCEPTED'}">Одобрен</c:when>
                                <c:when test="${code=='COMPLETION'}">Требуется доработка</c:when>
                                <c:when test="${code=='CONSIDERATION'}">В рассмотрении</c:when>
                                <c:when test="${code=='APPROVED'}">Утвержден</c:when>
                                <c:when test="${code=='SUCCESSED'}">Выдан</c:when>
                                <c:when test="${code=='CANCELATION'}">Аннулирован</c:when>
                                <c:when test="${code=='MODIFICATION'}">Изменён</c:when>
                                <c:when test="${code=='RETURNED'}">Возвращен</c:when>
                                <c:when test="${code=='EXECUTED'}">Исполнен</c:when>
                                <c:when test="${code=='TICKETS_WAITING'}">Ожидание билетов</c:when>
                                <c:when test="${code=='REFUSED'}">Отказан (статус для клиента: "Отклонено банком")</c:when>
                                <c:when test="${code=='RECALLED'}">Отозван (статус для клиента: "Заявка была отменена")</c:when>
                                <c:when test="${code=='DELAYED_DISPATCH'}">Ожидается обработка</c:when>
                                <c:when test="${code=='ERROR'}">Приостановлен (статус для клиента: "Исполняется банком")<c:if test="${businessDocument.checkStatusCountResult}"> (Превышение количества проверок статуса)</c:if></c:when>
                                <c:when test="${code=='UNKNOW' or code=='SENT'}">Приостановлен (статус для клиента: "Исполняется банком")</c:when>
                                <c:when test="${code=='PARTLY_EXECUTED'}">Обрабатывается (статус для клиента: "Исполняется банком")</c:when>
                                <c:when test="${code=='WAIT_CONFIRM'}">Ожидает дополнительного подтверждения (статус для клиента: "Подтвердите в контактном центре<c:if test="${'IMSI' == businessDocument.reasonForAdditionalConfirm}"> (Смена SIM-карты)</c:if>")</c:when>
                                <c:when test="${code=='ADOPTED'}">Принята (статус для клиента: "Принята")</c:when>
                                <c:when test="${code=='DELETED'}">Удален</c:when>
                                <c:when test="${code=='INITIAL_LONG_OFFER'}">Частично заполнен (статус для клиента: "Черновик")</c:when>
                                <c:when test="${code=='BILLING_CONFIRM_TIMEOUT'}">Таймаут при подтверждении в биллинге (ЕРИБ)</c:when>
                                <c:when test="${code=='BILLING_GATE_CONFIRM_TIMEOUT'}">Таймаут при подтверждении в биллинге (шлюз)</c:when>
                                <c:when test="${code=='ABS_RECALL_TIMEOUT'}">Таймаут при отзыве в АБС (ЕРИБ)</c:when>
                                <c:when test="${code=='ABS_GATE_RECALL_TIMEOUT'}">Таймаут при отзыве в АБС (шлюз)</c:when>
                                <c:when test="${code=='OFFLINE_DELAYED'}">Ожидается обработка</c:when>
                                <c:when test="${code=='OFFLINE_SAVED'}">Введен (статус для клиента: "Черновик")</c:when>
                                <c:when test="${code=='VISIT_OFFICE'}">Передана в ВСП</c:when>
                                <c:when test="${code=='WAIT_TM'}">В работе ТМ</c:when>
                            </c:choose>
                        </div>
                    </sl:collectionItem>

                    <sl:collectionItem title="Дата<br>изменения<br>статуса">
                        <c:if test="${! empty businessDocument.changed}">
                            <bean:write name="businessDocument" property="changed" format="dd.MM.yyyy"/>
                            <bean:write name="businessDocument" property="changed" format="HH:mm:ss"/>
                        </c:if>
                    </sl:collectionItem>

                    <sl:collectionItem title="ФИО клиента">
                        <c:choose>
                            <c:when test="${isDeleted}">
                                ${sender.fullName}
                            </c:when>
                            <c:otherwise>
                                <phiz:link action="/persons/edit" serviceId="PersonManagement">
                                    <phiz:param name="person" value="${sender.id}"/>
                                    <c:out value="${sender.surName}"/>
                                    &nbsp;
                                    <c:out value="${sender.firstName}"/>
                                    &nbsp;
                                    <c:out value="${sender.patrName}"/>
                                </phiz:link>
                            </c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                    <sl:collectionItem title="Счет списания">
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                            <c:choose>
                                <c:when test="${businessDocument.chargeOffResourceType eq 'CARD'}">
                                    <c:out value="${phiz:getCutCardNumber(businessDocument.chargeOffAccount)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${businessDocument.chargeOffAccount}"/>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not empty businessDocument.chargeOffAccount}">
                                <c:set var="paymentOperationNumber" value="${paymentOperationNumber + 1}"/>
                            </c:if>
                        </c:if>
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.payments.ReIssueCardClaim')}">
                            <c:out value="${phiz:getCutCardNumber(businessDocument.cardNumber)}"/>
                        </c:if>
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}">
                            <c:out value="${phiz:getAccountLinkById(businessDocument.accountId, businessDocument.owner.login).number}"/>
                        </c:if>
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.CardReportDeliveryClaim')}">
                            <c:out value="${phiz:getCutCardNumber(businessDocument.cardNumberReportDelivery)}"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Счет зачисления">
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                            <c:choose>
                                <c:when test="${businessDocument.destinationResourceType eq 'CARD'}">
                                    <c:out value="${phiz:getCutCardNumber(businessDocument.receiverAccount)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${businessDocument.receiverAccount}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}">
                           <c:choose>
                               <c:when test="${businessDocument.interestDestinationSource == 'card'}">
                                   <c:out value="${phiz:getCutCardNumber(businessDocument.percentCardNumber)}"/>
                               </c:when>
                               <c:when test="${businessDocument.interestDestinationSource == 'account'}">
                                   <c:out value="${phiz:getFormattedAccountNumber(businessDocument.changePercentDestinationAccountNumber)}"/>
                               </c:when>
                               <c:otherwise>
                                   <c:out value="${phiz:getAccountLinkById(businessDocument.accountId, businessDocument.owner.login).number}"/>
                               </c:otherwise>
                           </c:choose>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Сумма">
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') or phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardClaim')}">
                            <c:choose>
                                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.longoffer.autopayment.AutoPayment') && businessDocument.executionEventType == 'BY_INVOICE'}">
                                    ${phiz:formatAmount(businessDocument.floorLimit)}
                                </c:when>
                                <c:when test="${!empty (businessDocument.chargeOffAmount)}">
                                    <c:set var="docTotalAmount"
                                           value="${businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
                                    <bean:write name="docTotalAmount" format="0.00"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="docTotalAmount"
                                           value="${businessDocument.destinationAmount.decimal+businessDocument.commission.decimal}"/>
                                    <bean:write name="docTotalAmount" format="0.00"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Валюта">
                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') or phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardClaim')}">
                            <c:choose>
                                <c:when test="${businessDocument.chargeOffAmount != null}">
                                    <bean:write name="businessDocument" property="chargeOffAmount.currency.code"/>
                                </c:when>
                                <c:when test="${businessDocument.destinationAmount != null}">
                                    <bean:write name="businessDocument" property="destinationAmount.currency.code"/>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Получатель">
                        <c:choose>
                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanProductClaim')
                                            || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardProductClaim')
                                            || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanOfferClaim')
                                            || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardClaim')
                                            || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardOfferClaim')}">
                                ОАО «Сбербанк России»
                            </c:when>
                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LoyaltyProgramRegistrationClaim')}">
                                Сбербанк России
                            </c:when>
                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.IMAOpeningClaim')}">
                                <div>
                                    <c:out value="${phiz:cutStringByLastSevenSymbols(businessDocument.receiverAccount)}"/>
                                </div>
                                <div>
                                    <c:out value="${businessDocument.IMAProductName}"/>
                                </div>
                                <div>
                                    <fmt:formatNumber value="${businessDocument.IMAAmount}" pattern="0.0"/>
                                    <c:out value="${phiz:getCurrencySign(businessDocument.IMACurrency)}"/>
                                    <c:out value="${phiz:normalizeMetalCode(businessDocument.IMACurrency)}"/>
                                </div>
                            </c:when>
                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                <c:out value="${businessDocument.receiverName}"/>
                                <c:if test="${businessDocument.formName == 'ExternalProviderPayment'}">
                                    <c:if test="${businessDocument.receiverFacilitator != null}">
                                        Оплата через фасилитатора: <c:out value="${businessDocument.receiverFacilitator}"/>
                                    </c:if>
                                </c:if>
                            </c:when>
                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}">
                                <c:set var="accLink" value="${phiz:getAccountLinkById(businessDocument.accountId, businessDocument.owner.login)}"/>
                                <c:out value="${accLink.name}"/><br/>
                                <c:out value="${accLink.number}"/>
                            </c:when>
                        </c:choose>
                    </sl:collectionItem>
                    <sl:collectionItem title="Идентификатор сессии">
                        <c:out value="${businessDocument.sessionId}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Канал / платформа">
                        <c:choose>
                            <c:when test="${businessDocument.creationType == 'internet'}">
                                Веб-приложение / Web-браузер <c:if test="${not empty businessDocument.deviceInfo}"> <a onclick="showAdditionalInfo('${businessDocument.id}');" class="imgA"> <img id='img${businessDocument.id}' class="openClose" src="${imagePath}/plus.gif" alt="" border="0"> </a>
                                <br/><div id='additionalInfo${businessDocument.id}' style="display:none; border: 1px solid;">${businessDocument.deviceInfo}</div> </c:if>
                            </c:when>
                            <c:when test="${businessDocument.creationType == 'sms'}">
                                SMS-приложение / ЕРМБ
                            </c:when>
                            <c:when test="${businessDocument.creationType == 'mobile'}">
                                <c:choose>
                                    <c:when test="${empty(businessDocument.deviceInfo)}">
                                        Приложение / -
                                    </c:when>
                                    <c:otherwise>
                                        Приложение / ${phiz:getPlatformText(businessDocument.deviceInfo)}
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${businessDocument.creationType == 'atm'}">
                                Терминал банка / АТМ <c:if test="${not empty businessDocument.codeATM}"> <a onclick="showAdditionalInfo('${businessDocument.id}');" class="imgA"> <img id='img${businessDocument.id}' class="openClose" src="${imagePath}/plus.gif" alt="" border="0"> </a>
                                <br/><div id='additionalInfo${businessDocument.id}' style="display:none; border: 1px solid;">${businessDocument.codeATM}</div> </c:if>
                            </c:when>
                            <c:when test="${businessDocument.creationType == 'social'}">
                                <c:choose>
                                    <c:when test="${empty(businessDocument.deviceInfo)}">
                                        Социальное приложение / -
                                    </c:when>
                                    <c:otherwise>
                                        Социальное приложение / ${phiz:getPlatformText(businessDocument.deviceInfo)}
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </sl:collectionItem>
                    <sl:collectionItem title="Подтверждение">
                        <c:set var="strategy" value="${phiz:getConfirmStrategyType(businessDocument)}"/>
                        <c:choose>
                            <c:when test="${strategy == 'none'}">
                                <bean:message bundle="auditBundle" key="label.confirm.none"/>
                            </c:when>
                            <c:when test="${strategy == 'sms'}">
                                <bean:message bundle="auditBundle" key="label.confirm.sms"/>
                            </c:when>
                            <c:when test="${strategy == 'card'}">
                                <bean:message bundle="auditBundle" key="label.confirm.card"/>
                            </c:when>
                            <c:when test="${strategy == 'cap'}">
                                <bean:message bundle="auditBundle" key="label.confirm.cap"/>
                            </c:when>
                            <c:when test="${strategy == 'need'}">
                                <bean:message bundle="auditBundle" key="label.confirm.need"/>
                            </c:when>
                            <c:when test="${strategy == 'push'}">
                                <bean:message bundle="auditBundle" key="label.confirm.push"/>
                            </c:when>
                            <c:otherwise>&nbsp;</c:otherwise>
                        </c:choose>
                        <c:if test="${strategy == 'sms' || strategy == 'card' || strategy == 'cap' || strategy == 'push'}">
                            &nbsp;<input type="button" class="buttWhite smButt" onclick="openDetails('${businessDocument.operationUID}');" value="&hellip;"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="Номер операции в БС">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment')&&(code =='EXECUTED' || code =='TICKETS_WAITING')}">
                            <c:out value="${businessDocument.billingDocumentNumber}"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.document.node.temporary">
                        <sl:collectionItemParam id="title"><bean:message bundle="auditBundle" key="label.document.node.temporary"/></sl:collectionItemParam>
                        <c:if test="${not empty businessDocument}">
                            <bean:message bundle="auditBundle" key="label.document.node.temporary.${not empty businessDocument.temporaryNodeId}"/>
                        </c:if>
                    </sl:collectionItem>
                </sl:collection>
                <script type="text/javascript">

                    function doRefuse()
                    {
                        var addUrl = "${phiz:calculateActionURL(pageContext,'/documents/refuse')}";
                        checkIfOneItem("selectedIds");
                        if (!checkSelection("selectedIds", 'Укажите одну запись.') || (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
                            return;

                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = addUrl + "?id=" + id;
                    }

                    function openMessageLogForDocument()
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkOneSelection("selectedIds", "Выберите одну запись и повторите попытку."))
                        {
                            return false;
                        }
                        var operationUID = getElementValue("operationUID_" + getFirstSelectedId("selectedIds"));
                        <c:choose>
                            <c:when test="${phiz:impliesOperation('MessageLogOperation', 'MessageLogService') or
                                            phiz:impliesOperation('MessageLogOperation', 'MessageLogServiceEmployee')}">
                                openMessageLogList(operationUID);
                            </c:when>
                            <c:otherwise>
                                openMessageLogListForERKC(operationUID);
                            </c:otherwise>
                        </c:choose>
                        return true;
                    }

                    function checkState()
                    {
                        var ids = document.getElementsByName("selectedIds");
                         for (var i = 0; i < ids.length; i++)
                         {
                             if (ids.item(i).checked)
                             {
                                 var code = $("#"+ids.item(i).value).val();
                                 var isInstance = $("#"+ids.item(i).value+"isInstance").val();
                                 if ( !(isInstance!='true' && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'|| code=='DELAYED_DISPATCH')
                                         || (code == 'UNKNOW'||code == 'SENT'||code == 'ERROR'||code == 'BILLING_CONFIRM_TIMEOUT' || code == 'TICKETS_WAITING' || code == 'BILLING_GATE_CONFIRM_TIMEOUT'))) && code!='PARTLY_EXECUTED' )
                                 {
                                     alert('Данная операция применима только для платежных документов со статусами "Приостановлен", "Обрабатывается", "Таймаут при подтверждении в биллинге", "Ожидание билетов".');
                                     return false;
                                 }
                             }
                         }
                         return true;
                    }

                    function checkStateForSpecify()
                    {
                        var ids = document.getElementsByName("selectedIds");
                        var errorString ="";
                        for (var i = 0; i < ids.length; i++)
                        {
                            if (ids.item(i).checked)
                            {
                                 var code = $("#"+ids.item(i).value).val();
                                 var docNumber = $("#S_"+ids.item(i).value).val();
                                 var isInstance = $("#"+ids.item(i).value+"isInstance").val();
                                 if ( !(isInstance!='true' && ((code=='UNKNOW'||code=='SENT'))))
                                 {
                                     errorString = errorString + docNumber +',';
                                 }
                            }
                        }
                        if (errorString.length > 0)
                        {
                            errorString = errorString.substring(0,errorString.length-1);
                            alert('Вы не можете повторно отправить на обработку в банк документы № '+ errorString +'. Отправить на обработку можно только документы со статусом «Приостановлен»');
                            return false;
                        }
                        return true;
                    }

                    function openDetails(operationUID)
                    {
                        window.open("${phiz:calculateActionURL(pageContext, '/log/confirmationInfo.do')}?filter(operationUID)=" + operationUID + "&isAudit=true",'confirmationInfo', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                    }

                    var widthClient = getClientWidth();
                    if (navigator.appName=='Microsoft Internet Explorer')
                        document.getElementById("workspaceDiv").style.width = widthClient - leftMenuSize - 100;

                    function showAdditionalInfo(id)
                    {
                        var elem = document.getElementById('additionalInfo'+id);
                        var img = document.getElementById('img'+id);
                        if (elem.style.display == "none")
                        {
                            elem.style.display = "";
                            img.src = "${imagePath}/minus.gif";
                        }
                        else
                        {
                            elem.style.display = "none";
                            img.src = "${imagePath}/plus.gif";
                        }
                    }
                </script>
            </tiles:put>
        <tiles:put name="isEmpty" value="${empty formdata}"/>
        <tiles:put name="emptyMessage">
           <c:choose>
                <c:when test="${fromStart}">
                    Для поиска операций в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                </c:when>
                <c:otherwise>
                    Не найдено ни одной операции, соответствующей заданному фильтру!
                </c:otherwise>
           </c:choose>
        </tiles:put>
        <c:if test="${not empty formdata}">
            <tiles:put name="buttons">
                <c:if test="${phiz:impliesOperation('MessageLogOperation', 'MessageLogService') or
                              phiz:impliesOperation('MessageLogOperation', 'MessageLogServiceEmployee') or
                              phiz:impliesOperation('MessageLogOperation', 'MessageLogServiceEmployeeUseClientForm')}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.messages"/>
                        <tiles:put name="commandHelpKey"    value="button.messages.help"/>
                        <tiles:put name="bundle"            value="auditBundle"/>
                        <tiles:put name="onclick"           value="openMessageLogForDocument();"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="commandButton" flush="false" service="RepeatPaymentService">
                    <tiles:put name="commandKey"        value="button.specify"/>
                    <tiles:put name="commandHelpKey"    value="button.specify.help"/>
                    <tiles:put name="bundle"            value="auditBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            return checkSelection("selectedIds", "Выберите хотя бы один документ.") && checkStateForSpecify();
                        }
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false" service="ChangeDocumentStatus">
                    <tiles:put name="commandKey"        value="button.execute"/>
                    <tiles:put name="commandHelpKey"    value="button.execute.help"/>
                    <tiles:put name="bundle"            value="auditBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            return checkSelection("selectedIds", "Выберите хотя бы один документ.") && checkState();
                        }
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" service="ChangeDocumentStatus">
                    <tiles:put name="commandTextKey"    value="button.refuse"/>
                    <tiles:put name="commandHelpKey"    value="button.refuse.help"/>
                    <tiles:put name="bundle"            value="auditBundle"/>
                    <tiles:put name="onclick"           value="doRefuse();"/>
                </tiles:insert>
            </tiles:put>
         </c:if>
        </tiles:insert>
        <c:if test="${fromStart}">
            <script type="text/javascript">
                //показываем фильтр при старте
                switchFilter(this);
            </script>
        </c:if>
