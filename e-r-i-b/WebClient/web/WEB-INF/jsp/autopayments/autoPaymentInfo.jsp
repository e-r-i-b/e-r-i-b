<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<tiles:importAttribute/>
<html:form action="/private/autopayments/info" onsubmit="return setEmptyAction(event)">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<c:set var="link" value="${form.link}"/>
<c:set var="cardLink" value="${link.cardLink}"/>
<c:set var="card" value="${cardLink.value}"/>
<c:set var="isMockCard" value="${phiz:isInstance(card,'com.rssl.phizic.business.cards.MockCard')}"/>
<c:set var="crdInfo" value="${cardLink.card}"/>
<c:if test="${link != null}">
    <c:set var="payment" value="${link.value}"/>
    <c:set var="isInvoice" value="${payment.executionEventType == 'BY_INVOICE'}"/>
</c:if>
<c:set var="items" value="${form.scheduleItems}"/>
<c:set var="isMock" value="${phiz:isInstance(payment, 'com.rssl.phizic.business.longoffer.autopayment.mock.MockAutoPayment')}"/>
<c:set var="refuseUrl" value="private/payments/payment.do?form=RefuseAutoPaymentPayment&linkId=${link.id}"/>
<c:set var="editUrl" value="private/payments/payment.do?form=EditAutoPaymentPayment&linkId=${link.id}"/>

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="AutoPayments"/>
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
            <tiles:put name="name" value="Автоплатеж (регулярная операция)"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="data" type="string">
        <div id="payment">
            <c:if test="${link != null}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Автоплатеж (регулярная операция)"/>
                    <tiles:put name="data">

                        <script type="text/javascript">
                            function openPrintInfo(event)
                            {
                                var url = "${phiz:calculateActionURL(pageContext, '/private/autopayments/info/print.do')}";
                                var params = "?id=" + ${form.id};

                                openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar-0,scrollbar=1");
                            }

                            function openPrintScheduleReport(event)
                            {
                                var url = "${phiz:calculateActionURL(pageContext, '/private/autopayments/info/printScheduleReport.do')}";
                                var params = "?id=" + ${form.id};

                                var toDate = getElementValue("filterToDate");
                                var fromDate = getElementValue("filterFromDate");

                                if (!validateDatePeriod(fromDate, toDate))
                                {
                                    return;
                                }

                                params += "&fromDateString=" + fromDate;
                                params += "&toDateString=" + toDate;

                                openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                            }
                        </script>
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${image}/autoPaymentInfo.jpg"/>
                            <tiles:put name="description">
                                На данной странице можно просмотреть созданный Вами автоплатеж(регулярную операцию), распечатать график
                                платежей и при необходимости отредактировать или отменить выполнение автоплатежа(регулярной операции).
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                        <fieldset>
                            <table class="additional-product-info">
                                <c:if test="${!isMock}">
                                    <tr>
                                        <td class="align-right field">Получатель:</td>
                                        <td><span class="bold"><bean:write name="payment" property="receiverName"/></span></td>
                                    </tr>
                                    <tr>
                                        <td class="align-right field">${form.requisiteName}:</td>
                                        <td><span class="bold"><bean:write name="payment" property="requisite"/></span></td>
                                    </tr>
                                    <tr>
                                        <td class="align-right field">Оплата с:</td>
                                        <td>
                                            <span class="bold">
                                                <c:set var="cutCardNumber" value="${phiz:getCutCardNumber(payment.cardNumber)}"/>
                                                <c:out value="${cutCardNumber}"/>&nbsp;
                                                <c:if test="${not empty cardLink && !isMockCard && cardLink.showInSystem}">
                                                    <c:out value="[${cardLink.name}]"/>&nbsp;
                                                    <c:if test="${crdInfo.availableCashLimit != null}">
                                                        <bean:write name="crdInfo" property="availableCashLimit.decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(crdInfo.availableCashLimit.currency.code)}
                                                    </c:if>
                                                </c:if>
                                            </span>
                                        </td>
                                    </tr>
                                    <c:if test="${!isInvoice}">
                                        <tr>
                                            <td class="align-right field">Сумма:</td>
                                            <td>
                                                <span class="summ">
                                                <bean:write name="payment" property="amount.decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(payment.amount.currency.code)}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:if>
                                <tr>
                                    <td colspan="2">
                                        <h2 class="additionalInfoHeader">Параметры автоплатежа (регулярной операции)</h2>
                                    </td>
                                </tr>
                                <c:if test="${!isMock}">
                                    <c:set var="floorLimit" value="${payment.floorLimit}"/>
                                    <tr>
                                        <td class="align-right field">Повторяется:</td>
                                        <td>
                                            <span class="bold">
                                                <c:choose>
                                                    <c:when test="${not empty floorLimit && !isInvoice}">
                                                        При снижении баланса до&nbsp;
                                                        <bean:write name="floorLimit" property="decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(floorLimit.currency.code)}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <bean:write name="payment" property="executionEventType.description"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </td>
                                    </tr>
                                    <c:if test="${not empty floorLimit && isInvoice}">
                                        <tr>
                                            <td class="align-right field">Максимальный размер платежа:</td>
                                            <td>
                                                <span class="bold">
                                                    <bean:write name="floorLimit" property="decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(floorLimit.currency.code)}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${empty floorLimit}">
                                        <tr>
                                            <td class="align-right field">Число месяца:</td>
                                            <td><span class="bold"><bean:write name="payment" property="startDate.time" format="dd"/></span></td>
                                        </tr>
                                    </c:if>
                                    <%-- для порогового еще есть максимальная сумма платежей в определенный период  --%>
                                    <c:if test="${payment.executionEventType == 'REDUSE_OF_BALANCE'}">
                                        <c:choose>
                                            <c:when test="${payment.totalAmountPeriod != null}">
                                                <c:set var="totalAmountPeriod" value="${payment.totalAmountPeriod}"/>
                                                <c:set var="totalAmountLimit"  value="${payment.totalAmountLimit}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="totalAmountPeriod" value="${phiz:getProviderAutoPayTotalAmountPeriod(link)}"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${not empty totalAmountPeriod}">
                                            <tr>
                                                <td class="align-right field">
                                                    Максимальная сумма в <bean:message key="autopay.period.max.summa.${totalAmountPeriod}" bundle="providerBundle"/>:
                                                </td>
                                                <td>
                                                    <c:set var="totalAmountLimit" value="${payment.totalAmountLimit}"/>
                                                    <span class="bold">
                                                        <c:choose>
                                                            <c:when test="${not empty totalAmountLimit}">
                                                                <bean:write name="totalAmountLimit" property="decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(totalAmountLimit.currency.code)}
                                                            </c:when>
                                                            <c:otherwise>
                                                                Не задана
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <tr>
                                    <td class="align-right field">Название:</td>
                                    <td><span class="word-wrap bold"><bean:write name="link" property="name"/></span></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div class="buttonsArea">
                                            <c:if test="${payment.reportStatus=='ACTIVE'}">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.edit"/>
                                                    <tiles:put name="commandHelpKey" value="button.edit"/>
                                                    <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                                    <tiles:put name="action" value="${editUrl}"/>
                                                </tiles:insert>
                                            </c:if>
                                            <c:if test="${payment.reportStatus=='NEW' || payment.reportStatus=='ACTIVE' ||
                                                          payment.reportStatus=='UPDATING' || payment.reportStatus=='BLOCKED'}">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.abstract.print"/>
                                                    <tiles:put name="commandHelpKey" value="button.abstract.print"/>
                                                    <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                                    <tiles:put name="onclick">openPrintInfo(event)</tiles:put>
                                                </tiles:insert>
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.refuse"/>
                                                    <tiles:put name="commandHelpKey" value="button.refuse"/>
                                                    <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                                    <tiles:put name="action" value="${refuseUrl}"/>
                                                </tiles:insert>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <h2 class="additionalInfoHeader">График исполнения</h2>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="filterAlign">
                                        <div class="fltrText">Показать график за период с&nbsp;</div>
                                        <div>
                                            <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(fromDate)" format="dd/MM/yyyy"/>'
                                                   size="10" name="filter(fromDate)" class="date-pick"/>
                                            <input name="filterFromDate" type="hidden" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(fromDate)" format="dd/MM/yyyy"/>'>
                                        </div>
                                        <div class="fltrText">&nbsp;по&nbsp;</div>
                                        <div>
                                            <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(toDate)" format="dd/MM/yyyy"/>'
                                                   size="10" name="filter(toDate)" class="date-pick"/>
                                            <input name="filterToDate" type="hidden" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(toDate)" format="dd/MM/yyyy"/>'>
                                        </div>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.showScheduleReport"/>
                                            <tiles:put name="commandHelpKey" value="button.showScheduleReport"/>
                                            <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                            <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                                        </tiles:insert>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div class="regular-payment-graph">
                                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                                <tiles:put name="grid">
                                                    <sl:collection id="item" name="items" model="no-pagination">
                                                        <sl:collectionItem>
                                                            ${phiz:formatDateWithStringMonth(item.date)}
                                                        </sl:collectionItem>
                                                        <sl:collectionItem>
                                                            <c:if test="${not empty item.amount}">
                                                                <bean:write name="item" property="amount.decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(item.amount.currency.code)}
                                                            </c:if>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem>
                                                            <c:choose>
                                                                <c:when test="${item.state == 'SUCCESS'}">исполнен</c:when>
                                                                <c:otherwise>отказан</c:otherwise>
                                                            </c:choose>
                                                        </sl:collectionItem>
                                                    </sl:collection>
                                                </tiles:put>
                                                <tiles:put name="isEmpty" value="${empty items}"/>
                                                <tiles:put name="emptyMessage">
                                                    В рамках платежа не было ни одной оплаты.
                                                </tiles:put>
                                            </tiles:insert>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div class="buttonsArea">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.showAllScheduleReport"/>
                                                <tiles:put name="commandHelpKey" value="button.showAllScheduleReport"/>
                                                <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                                <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                                                <tiles:put name="onclick">new CommandButton('button.showAllScheduleReport').click()</tiles:put>
                                            </tiles:insert>
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.scheduleReport.print"/>
                                                <tiles:put name="commandHelpKey" value="button.scheduleReport.print"/>
                                                <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                                <tiles:put name="viewType" value="blueGrayLink"/>
                                                <tiles:put name="onclick">openPrintScheduleReport(event)</tiles:put>
                                            </tiles:insert>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </div>
    </tiles:put>
</tiles:insert>

</html:form>
