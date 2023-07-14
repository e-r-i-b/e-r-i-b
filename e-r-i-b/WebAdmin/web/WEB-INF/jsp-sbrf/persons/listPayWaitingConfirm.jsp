<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/payment_wait_confirm/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="OperationsForConfirm"/>
        <tiles:put name="needSave" value="false"/>

        <c:set var="bundle" value="personsBundle"/>
        <tiles:put name="pageTitle" type="string">
	        <bean:message key="edit.payment.title" bundle="${bundle}"/>
        </tiles:put>

        <%-- ФИЛЬТР --%>
        <tiles:put name="filter" type="string">
            <c:set var="colCount" value="3" scope="request"/>
            <%-- Период --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.period"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                   <bean:message bundle="${bundle}" key="filter.period.from"/>&nbsp;
                   <span style="font-weight:normal;overflow:visible;cursor:default;">
                       <input type="text"
                               size="10" name="filter(fromDate)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                       <input type="text"
                               size="8" name="filter(fromTime)"  class="time-template"
                               value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                               onkeydown="onTabClick(event,'filter(toDate)');"/>
                   </span>
                   &nbsp;<bean:message bundle="${bundle}" key="filter.period.to"/>&nbsp;
                   <span style="font-weight:normal;cursor:default;">
                       <input type="text"
                               size="10" name="filter(toDate)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                       <input type="text"
                               size="8" name="filter(toTime)"   class="time-template"
                               value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                   </span>
                </tiles:put>
            </tiles:insert>

            <%-- Вид операции --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.operation.type"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(formName)" >
                        <html:option value="">Все</html:option>
                        <html:option value="InternalPayment">Перевод между своими счетами</html:option>
                        <html:option value="RurPayment">Перевод клиенту Сбербанка или частному лицу в другой банк</html:option>
                        <html:option value="JurPayment">Перевод организации</html:option>
                        <html:option value="InvoicePayment">Оплата задолженности</html:option>
                        <html:option value="RurPayJurSB">Оплата услуг</html:option>
                        <html:option value="LongOffer">Автоплатеж</html:option>
                        <html:option value="IMAPayment">Покупка/продажа металла</html:option>
                        <html:option value="AccountClosingPayment">Заявка на закрытие вклада</html:option>
                        <html:option value="CreditReportPayment">Получение кредитного отчета</html:option>
                        <html:option value="RemoteConnectionUDBOClaim">Подключие всех возможностей Сбербанк Онлайн</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

             <%-- Получатель --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.receiver"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(receiverName)" size="20" maxlength="100"/>
                </tiles:put>
             </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"/>

             <%-- Статус операции --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.operation.state"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(state)" >
                        <html:option value="WAIT_CONFIRM">Ожидает дополнительного подтверждения</html:option>
                        <html:option value="DISPATCHED">Подтвержден</html:option>
                        <html:option value="DELAYED_DISPATCH">Ожидает обработки</html:option>
                        <html:option value="EXECUTED">Исполнен</html:option>
                        <html:option value="UNKNOW,SENT">Приостановлен</html:option>
                        <html:option value="REFUSED">Отказан</html:option>
                        <html:option value="DISPATCHED">Обрабатывается</html:option>
                        <html:option value="ALL">Все</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

             <%-- № платежа/заявки --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.number.payment"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(number)" size="20" maxlength="10"/>
                </tiles:put>
             </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"/>

             <%-- Подтвержден --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.confirm"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(additionConfirm)" >
                        <html:option value=""></html:option>
                        <html:option value="internet">Через КЦ</html:option>
                        <html:option value="atm">Через УС</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

             <%-- Подтвердил сотрудник --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.confirm.employee"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(confirmEmployee)" size="20" maxlength="50"/>
                </tiles:put>
            </tiles:insert>

        </tiles:put>

        <%-- ДАННЫЕ --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openDetails(operationUID)
                {
                    window.open("${phiz:calculateActionURL(pageContext, '/log/confirmationInfo.do')}?filter(operationUID)=" + operationUID + "&isAudit=true",'confirmationInfo', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                }
                addClearMasks(null,
                        function(event)
                        {
                            clearInputTemplate('filter(fromDate)', '__.__.____');
                            clearInputTemplate('filter(toDate)', '__.__.____');
                            clearInputTemplate('filter(fromTime)', '__:__:__');
                            clearInputTemplate('filter(toTime)', '__:__:__');
                        });
            </script>
            <c:if test="${form.activePerson.securityType != 'LOW'}">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="redBlock"/>
                    <tiles:put name="data">
                        Действует режим ограниченной функциональности
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <jsp:include page="/WEB-INF/jsp-sbrf/persons/existsAnotherNodeDocumentMessage.jsp" flush="false"/>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="limitsList"/>
                <tiles:put name="grid">
                    <sl:collection id="document" model="list" property="data">
                        <c:set var="state" value="${document.state.code}"/>
                        <sl:collectionItem title="Дата и время">
                            <c:choose>
                                <%--Дата исполнения документа для состояний "Исполнен", "Отказан"--%>
                                <c:when test="${(document.executionDate != null)&&(state == 'EXECUTED' || state == 'REFUSED')}">
                                    <c:set var="statusChangedDate" value="${document.executionDate.time}"/>
                                </c:when>
                                <%--Дата подтверждения для статусов "Исполнен", "Отказан"--%>
                                <c:when test="${(document.admissionDate != null)&&(state == 'EXECUTED' || state == 'REFUSED')}">
                                    <c:set var="statusChangedDate" value="${document.admissionDate.time}"/>
                                </c:when>
                                <%--CONFIRMED - "Подтвержден" (для них admissionDate = null, а дата подтверждения в "changed")--%>
                                <%--DISPATCHED - Документ подтвержден и передан на обработку в банк. Дата подтверждения в "changed"--%>
                                <c:when test="${state == 'CONFIRMED' || state == 'DISPATCHED'}">
                                    <c:set var="statusChangedDate" value="${document.changed}"/>
                                </c:when>
                                <%--Дата совершения операции клиентом для статуса "Не подтвержден в КЦ"--%>
                                <c:when test="${state == 'WAIT_CONFIRM'}">
                                    <c:set var="statusChangedDate" value="${document.operationDate.time}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="statusChangedDate" value="${document.dateCreated.time}"/>
                                </c:otherwise>
                            </c:choose>

                            <table>
                            <tr>
                                <td style="white-space:nowrap;border-style:none;">
                                    <a href="${phiz:calculateActionURL(pageContext,"/persons/payment_wait_confirm/edit.do")}?id=${document.id}">
                                        <fmt:formatDate value="${statusChangedDate}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </a>
                                </td>
                                <td align="center" width="16px" style="border-style:none;">
                                    <c:set var="channel" value="${document.creationType}"/>
                                    <c:choose>
                                        <c:when test="${channel == 'mobile'}">
                                            <img border="0" alt="" width="10" height="15" src="${imagePath}/icon-mobile.gif" title="Операция выполнена в мобильном приложении"/>
                                        </c:when>
                                        <c:when test="${channel == 'social'}">
                                            <img border="0" alt="" width="10" height="15" src="${imagePath}/icon-social.gif" title="Операция выполнена через социальные сети"/>
                                        </c:when>
                                        <c:when test="${channel == 'atm'}">
                                            <img border="0" alt="" width="16" height="16" src="${imagePath}/icon-atm.gif" title="Операция совершена через устройство самообслуживания"/>
                                        </c:when>
                                        <c:when test="${channel == 'sms'}">
                                            <img border="0" alt="" width="16" height="16" src="${imagePath}/icon-ermb.gif" title="Операция совершена через ЕРМБ"/>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            </table>
                        </sl:collectionItem>

                        <sl:collectionItem title="Номер">
                            <sl:collectionItemParam id="action" value="/persons/payment_wait_confirm/edit.do?id=${document.id}"/>
                            ${document.documentNumber}
                        </sl:collectionItem>

                        <sl:collectionItem title="Вид операции">
                            <c:if test="${document.byTemplate}">
                                <img border="0" alt="" src="${imagePath}/icon-byTemplate.gif" title="Операция совершена по шаблону"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${document.longOffer}">Автоплатеж</c:when>
                                <c:when test="${document.formName == 'InternalPayment'}">Перевод между своими счетами и картами</c:when>
                                <c:when test="${document.formName == 'RurPayJurSB'}">Оплата услуг</c:when>
                                <c:when test="${document.formName == 'RurPayment'}">
                                    <c:choose>
                                        <c:when test="${document.receiverSubType == 'externalAccount'}">
                                            Перевод частному лицу в другой банк по реквизитам
                                        </c:when>
                                        <c:when test="${document.receiverSubType == 'ourCard' or document.receiverSubType == 'ourAccount' or  document.receiverSubType == 'ourPhone'}">
                                            Перевод клиенту Сбербанка
                                        </c:when>
                                        <c:otherwise>
                                            Перевод на карту в другом банке
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${document.formName == 'NewRurPayment'}">
                                    <c:choose>
                                        <c:when test="${document.receiverSubType == 'ourAccount' || document.receiverSubType == 'externalAccount'}">
                                                <bean:message key="label.translate.bankAccount" bundle="paymentsBundle"/>
                                        </c:when>
                                        <c:when test="${document.receiverSubType == 'visaExternalCard' || document.receiverSubType == 'masterCardExternalCard' || document.receiverSubType == 'ourContactToOtherCard'}">
                                                <bean:message key="label.translate.card.otherbank" bundle="paymentsBundle"/>
                                        </c:when>
                                        <c:when test="${document.receiverSubType == 'yandexWallet' || document.receiverSubType == 'yandexWalletOurContact' || document.receiverSubType == 'yandexWalletByPhone'}">
                                                <bean:message key="label.translate.yandex" bundle="paymentsBundle"/>
                                        </c:when>
                                        <c:otherwise>
                                                <bean:message key="label.translate.ourClient" bundle="paymentsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${document.formName == 'InvoicePayment'}">Оплата задолженности</c:when>
                                <c:when test="${document.formName == 'JurPayment'}">Перевод организации</c:when>
                                <c:when test="${document.formName == 'IMAPayment'}">Покупка/продажа металла</c:when>
                                <c:when test="${document.formName == 'ConvertCurrencyPayment'}">Обмен валют</c:when>
                                <c:when test="${document.formName == 'CreditReportPayment'}">Получение кредитного отчета</c:when>
                                <c:when test="${phiz:isExternalPayment(document)}">Оплата услуг с внешнего сайта</c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <c:set var="style" value="${state == 'WAIT_CONFIRM' ? 'red' : ''}"/>

                        <sl:collectionItem title="Статус" styleClass="${style}">
                            <c:set var="infoMessage">
                                <img border="0" alt="" src="${imagePath}/info.gif" title="Статус, отображаемый у клиента в истории операций и на форме платежа"/>
                            </c:set>
                            <c:choose>
                                <c:when test="${state == 'WAIT_CONFIRM'}">Ожидает дополнительного подтверждения <span class="text-dark-gray">(Подтвердите в контактном центре <c:if test="${'IMSI' == document.reasonForAdditionalConfirm}"> (Смена SIM-карты)</c:if>${infoMessage})</span></c:when>
                                <c:when test="${state == 'DISPATCHED'}">Обрабатывается (Исполняется банком${infoMessage})</c:when>
                                <c:when test="${state == 'INITIAL' || state == 'SAVED'}">Введен (Черновик${infoMessage})</c:when>
                                <c:when test="${state == 'DELAYED_DISPATCH'}">Ожидает обработки</c:when>
                                <c:when test="${state == 'CONFIRMED'}">Подтвержден</c:when>
                                <c:when test="${state == 'EXECUTED'}">Исполнен</c:when>
                                <c:when test="${state == 'UNKNOW' or state == 'SENT'}">Приостановлен (Исполняется банком${infoMessage})</c:when>
                                <c:when test="${state == 'REFUSED'}">Отказан (Отклонено банком${infoMessage})</c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="ФИО клиента">
                             ${document.payerName}
                        </sl:collectionItem>

                        <sl:collectionItem title="Счет списания">
                            <c:choose>
                                <c:when test="${document.chargeOffResourceType eq 'CARD'}">
                                    ${phiz:getCutCardNumber(document.chargeOffAccount)}
                                </c:when>
                                <c:otherwise>
                                      ${document.chargeOffAccount}
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="Счет зачисления">
                            <c:if test="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                 <c:choose>
                                    <c:when test="${document.destinationResourceType eq 'CARD' || document.destinationResourceType eq 'EXTERNAL_CARD'}">
                                        ${phiz:getCutCardNumber(document.receiverAccount)}
                                    </c:when>
                                    <c:otherwise>
                                        ${document.receiverAccount}
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="Сумма">
                            <c:choose>
                                <c:when test="${document.chargeOffAmount != null}">
                                    <bean:write name="document" property="chargeOffAmount.decimal" format="0.00"/>
                                </c:when>
                                <c:when test="${document.destinationAmount != null}">
                                    <bean:write name="document" property="destinationAmount.decimal" format="0.00"/>
                                </c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="Валюта">
                             <c:choose>
                                <c:when test="${document.chargeOffAmount != null}">
                                    <bean:write name="document" property="chargeOffAmount.currency.code"/>
                                </c:when>
                                <c:when test="${document.destinationAmount != null}">
                                    <bean:write name="document" property="destinationAmount.currency.code"/>
                                </c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="Получатель">
                            <c:if test="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                <bean:write name="document" property="receiverName"/>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="Подтверждение">
                            <c:choose>
                                <c:when test="${document.additionalOperationChannel == 'internet'}">Через КЦ</c:when>
                                <c:when test="${document.additionalOperationChannel == 'atm'}">Через УС</c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="Подтвердил сотрудник">
                            <c:if test="${not empty document.confirmEmployee}">
                                <bean:write name="document" property="confirmEmployee"/>
                            </c:if>
                        </sl:collectionItem>
                                           <sl:collectionItem title="Идентификатор сессии">
                        <c:out value="${document.sessionId}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Подтверждение">
                        <c:set var="strategy" value="${phiz:getConfirmStrategyType(document)}"/>
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
                        <c:if test="${strategy == 'sms' || strategy == 'card'}">
                            &nbsp;<input type="button" class="buttWhite smButt" onclick="openDetails('${document.operationUID}');" value="&hellip;"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.document.node.temporary">
                        <sl:collectionItemParam id="title"><bean:message bundle="auditBundle" key="label.document.node.temporary"/></sl:collectionItemParam>
                        <c:if test="${not empty document}">
                            <bean:message bundle="auditBundle" key="label.document.node.temporary.${not empty document.temporaryNodeId}"/>
                        </c:if>
                    </sl:collectionItem>
                    </sl:collection>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                </tiles:put>
                <tiles:put name="emptyMessage">
                    <bean:message key="list.payment.empty" bundle="${bundle}"/>
                </tiles:put>                          
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>