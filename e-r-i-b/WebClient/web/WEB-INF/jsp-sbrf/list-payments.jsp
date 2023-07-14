<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 28.04.2008
  Time: 14:39:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.rssl.phizic.web.client.payments.forms.ShowMetaPaymentListForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/payments/common" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>

    <tiles:insert definition="historyInfo">
        <tiles:put name="mainmenu" value="Payments"/>
        <tiles:put name="pageTitle" value="Журнал операций"/>
        <%-- данные --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openMoreFilterHistory (obj, hiddenValue)
                {
                    var moreFilter = $(".filterMore");
                    var isOpenEvent = !moreFilter.is(":visible"); // флаг отвечающий на вопрос открыть или закрыть фильтр
                    hideOrShow(moreFilter[0]);
                    if (isOpenEvent)
                    {
                        setElement(hiddenValue, "true");
                        return;
                    }
                    setElement(hiddenValue, "false"); // сохраняем флаг открытие/закрытия
                }
            </script>
            <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
            <c:set var="accounts" value="form.accounts"/>
            <div id="history">
                <c:set var="showOperationsButton" value="${phiz:showOperationsButton()}" scope="request"/>
                <c:if test="${showOperationsButton}">
                    <div class="operationsHistoryBunner">
                        <c:choose>
                            <c:when test="${phiz:impliesService('ReminderManagment')}">
                                <img src="${globalUrl}/commonSkin/images/bunner_operations_history_reminder.png" alt="Баннер" border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${globalUrl}/commonSkin/images/bunner_operations_history.png" alt="Баннер" border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title"><bean:message bundle="paymentsBundle" key="label.history.title"/></tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_pmnt_history.png"/>
                            <tiles:put name="description">
                                <bean:message bundle="paymentsBundle" key="label.history.description.text"/>
                            </tiles:put>
                        </tiles:insert>
                        <%-- Фильтр --%>
                        <div class="float" onkeypress="onEnterKey(event);">
                            <html:text property="filter(receiverName)" size="65"  maxlength="256" styleClass="customPlaceholder search" title="Название операции или поставщика"/>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.history.find"/>
                                <tiles:put name="commandHelpKey" value="button.history.find.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                            </tiles:insert>
                        </div>
                        <div class="extendFilterButton">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.show.filter"/>
                                <tiles:put name="commandHelpKey" value="button.show.filter"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="onclick" value="openMoreFilterHistory(this, 'filterState(filterCounter1)'); return false;" />
                                <tiles:put name="viewType" value="blueGrayLinkDotted" />
                                <tiles:put name="image" value="icon-sortable-filter.gif"/>
                                <tiles:put name="imagePosition" value="left"/>
                                <tiles:put name="btnId" value="filterCounterTag1"/>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>
                        <script type="text/javascript">
                            var actionFilterURL = "${phiz:calculateActionURL(pageContext, '/private/async/filter')}";
                            function clearFilter()
                            {
                                ajaxQuery("url=/private/payments/common", actionFilterURL, clearFilterParams, null, true);
                            }
                        </script>
                        <tiles:insert definition="filter" flush="false">
                            <tiles:put name="showButtonOpenMore" value="false"/>
                            <tiles:put name="clearButtonBundle" value="paymentsBundle"/>
                            <tiles:put name="clearButtonPosition" value="right"/>
                            <tiles:put name="clearButtonStyle" value="blueGrayLinkDotted"/>
                            <tiles:put name="showClearButton" value="true"/>
                            <%--Для очистки полей фильтра с дефолтными значениями--%>
                            <tiles:put name="hiddenData">
                                <table class="payment-templates-filter">
                                    <tr class="fieldTitle"><td>Операция</td><td>Списано со счета</td><td>Период</td></tr>
                                    <tr>
                                        <td>
                                            <script type="text/javascript">
                                                var autoPayable = {};

                                                function setAutoPayable()
                                                {
                                                    var ids = ensureElement("filter(formId)").value;
                                                    ensureElement("filter(autoPayment)").value = autoPayable[ids];
                                                }
                                            </script>

                                            <html:select property="filter(formId)" value="${form.filters['formId']}" onclick="setAutoPayable()" onchange="setAutoPayable()"
                                                         styleId="filter(formId)" styleClass="operationNameSelect">
                                                <html:option value="">Все</html:option>
                                                <c:forEach items="${form.filterPaymentForms}" var="frm">
                                                    <c:set var="formName">
                                                        <bean:message bundle="paymentsBundle" key="paymentform.${frm.name}" failIfNone="false"/>
                                                    </c:set>
                                                    <c:if test="${not empty formName}">
                                                        <html:option value="${frm.ids}">
                                                            <c:out value="${formName}"/>
                                                        </html:option>
                                                    </c:if>

                                                    <script type="text/javascript">
                                                        autoPayable['${frm.ids}'] = '${frm.autoPayment}';
                                                    </script>

                                                </c:forEach>
                                            </html:select>

                                            <html:hidden property="filter(autoPayment)" styleId="filter(autoPayment)"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${phiz:isInstance(form.filters['account'], 'com.rssl.phizic.business.resources.external.ExternalResourceLinkBase')}">
                                                    <c:set var="accountCode" value="${form.filters['account'].code}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="accountCode" value="${form.filters['account']}"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <html:select property="filter(account)" value="${accountCode}" styleClass="productSelect">
                                                <html:option value="">Все</html:option>
                                                <%--Счета--%>
                                                <c:forEach items="${form.accounts}" var="account">
                                                    <html:option value="${account.code}">
                                                        <c:out value="${phiz:getFormattedAccountNumber(account.number)} [${account.name}] ${phiz:getCurrencySign(account.currency.code)}"/>
                                                    </html:option>
                                                </c:forEach>
                                                <%--ОМС--%>
                                                <c:forEach items="${form.imaccounts}" var="imaccount">
                                                    <html:option value="${imaccount.code}" >
                                                        ${phiz:getFormattedIMAccountNumber(imaccount.number)}&nbsp;
                                                        [<c:out value="${imaccount.name}"/>]&nbsp;
                                                        <c:if test="${not empty imaccount.currency}">
                                                            ${phiz:normalizeCurrencyCode(imaccount.currency.code)}
                                                        </c:if>
                                                    </html:option>
                                                </c:forEach>
                                                <%--Карты--%>
                                                <c:forEach items="${form.cards}" var="card">
                                                    <html:option value="${card.code}" >
                                                        <c:out value="${phiz:getCutCardNumber(card.number)} [${card.name}] ${phiz:getCurrencySign(card.currency.code)}"/>
                                                    </html:option>
                                                </c:forEach>
                                            </html:select>
                                        </td>
                                        <td>
                                            <tiles:insert definition="newFilterDataPeriod" flush="false">
                                                <tiles:put name="name" value="Date"/>
                                            </tiles:insert>
                                        </td>
                                    </tr>
                                    <tr class="fieldTitle"><td><div class="amountTitle">Сумма</div>      Валюта</td><td>Статус</td><td></td></tr>
                                    <tr><td><div class="amountTitle"><html:text property="filter(fromAmount)" maxlength="20" styleClass="moneyField" title="введите минимальную сумму"/>
                                        - <html:text property="filter(toAmount)"  maxlength="20" styleClass="moneyField" title="введите максимальную сумму"/>&nbsp; </div>
                                        <html:select property="filter(amountCurrency)" styleClass="currencySelect">
                                            <html:option value="">все</html:option>
                                            <html:option value="RUB">руб</html:option>
                                            <html:option value="USD">$</html:option>
                                            <html:option value="EUR">&euro;</html:option>
                                        </html:select>
                                    </td>
                                        <td>
                                            <html:select property="filter(clientState)" value="${form.filters['clientState']}"
                                                         styleId="filter(clientState)" styleClass="stateSelect" >
                                                <html:option value="">Все</html:option>
                                                <html:option value="SAVED"             bundle="paymentsBundle" key="payment.state.SAVED"/>
                                                <html:option value="COMPLITE"          bundle="paymentsBundle" key="pfp.state.COMPLITE"/>
                                                <html:option value="EXECUTED"          bundle="paymentsBundle" key="payment.state.EXECUTED"/>
                                                <html:option value="SENDED"            bundle="paymentsBundle" key="payment.state.DISPATCH_OR_SENDED"/>
                                                <html:option value="WAIT_CONFIRM"      bundle="paymentsBundle" key="payment.state.WAIT_CONFIRM"/>
                                                <html:option value="DELAYED_DISPATCH"  bundle="paymentsBundle" key="payment.state.DELAYED_DISPATCH"/>
                                                <html:option value="REFUSED"           bundle="paymentsBundle" key="payment.state.REFUSED"/>
                                                <html:option value="RECALLED"          bundle="paymentsBundle" key="payment.state.RECALLED"/>
                                                <html:option value="NOT_COMPLITE"      bundle="paymentsBundle" key="pfp.state.NOT_COMPLITE"/>
                                            </html:select>
                                        </td><td></td></tr>
                                </table>
                                <br/>
                            </tiles:put>
                            <tiles:put name="hideFilterButton" value="true"/>
                            <tiles:put name="buttonKey" value="button.history.find"/>
                            <tiles:put name="buttonTextKey" value="button.filter"/>
                            <tiles:put name="buttonBundle" value="paymentsBundle"/>
                        </tiles:insert>
                        <%-- /Фильтр --%>
                        <div class="clear"></div>
                        <tiles:insert definition="historyTemplate" flush="false"/>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
