<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<html:form action="/private/autosubscriptions/info" onsubmit="return setEmptyAction(event)">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="items" value="${form.scheduleItems}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="paymentInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/payment/info?id=')}"/>
<c:set var="url" value="${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/info?id=')}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="AutoSubscription"/>
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

    <c:set var="autoSubscriptionLink" value="${form.link}"/>
    <c:set var="payment" value="${form.autoSubscriptionInfo}"/>
    <c:set var="paymentType" value="${payment.type.simpleName}"/>
    <c:set var="isP2Psubscription" value="${paymentType == 'InternalCardsTransferLongOffer' || paymentType == 'ExternalCardsTransferToOurBankLongOffer' ||
        paymentType == 'ExternalCardsTransferToOtherBankLongOffer'}"/>
    <c:set var="isCardArrested" value="${form.autoSubscriptionCardState != null && form.autoSubscriptionCardState == 'ARRESTED'}"/>
    <c:if test="${not empty payment}">
        <c:set var="bank" value="${payment.receiverBank}"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">

                function showHidePeriods(showPeriod)
                {
                    if (showPeriod)
                    {
                        $("#betweenDates1").hide();
                        $("#betweenDates2").show();
                        $("#showPayments").show();
                    }
                    else
                    {
                        $("#betweenDates2").hide();
                        $("#showPayments").hide();
                        $("#betweenDates1").show();
                    }
                }

                function showHidePaymentFilter(showPeriod)
                {
                    if ($("#showPaymentForPeriod").val() == showPeriod + "")
                        return;
                    document.getElementById("lastPayments").className = (showPeriod ? 'in' : '') + "activeRectButton";
                    showHidePeriods(showPeriod);
                    $("#showPaymentForPeriod").val(showPeriod);
                    if (!showPeriod)
                    {
                        findCommandButton('button.showScheduleReport').click('', false);
                    }
                }

                function openPrintScheduleReport(event)
                {
                    var url = "${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/info/printScheduleReport.do')}";
                    var params = "?id=" + ${form.id};
                    params += "&showPaymentForPeriod="+$("#showPaymentForPeriod").val();
                    if ($("#showPaymentForPeriod").val())
                    {
                        var toDate = getElementValue("filter(toDate)");
                        var fromDate = getElementValue("filter(fromDate)");

                        if (!validateDatePeriod(fromDate, toDate))
                        {
                            return;
                        }

                        params += "&fromDateString=" + fromDate;
                        params += "&toDateString=" + toDate;
                    }

                    openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                }

                $(document).ready(function() {showHidePeriods(${form.showPaymentForPeriod})});
            </script>
            <div id="payment">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Автоплатеж (Регулярная операция)"/>
                <tiles:put name="data">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/longOfferInfo.png"/>
                        <tiles:put name="description">
                                На данной странице можно просмотреть созданный автоплатеж, приостановить, возобновить действие автоплатежа, 
                                распечатать график платежей и при необходимости отменить выполнение автоплатежа.
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>

                    <c:set var="autoSubscriptionLink" value="${form.link}"/>
                    <c:set var="cardLink" value="${autoSubscriptionLink.cardLink}"/>
                    <c:set var="payment"  value="${form.autoSubscriptionInfo}"/>
                    <c:choose>
                        <c:when test="${isP2Psubscription}">
                            <%@ include file="/WEB-INF/jsp/common/autosubscription/p2pDetailInfo.jsp" %>
                        </c:when>
                        <c:otherwise>
                            <c:set var="autosubscriptionType" value="autoPayment"/>
                            <%@ include file="/WEB-INF/jsp/common/autosubscription/detailInfo.jsp" %>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${!isP2Psubscription}">
                        <div class="buttonsArea">
                            <c:set var="autoPayStatusType" value="${autoSubscriptionLink.autoPayStatusType}"/>

                            <%--редактировать автоплатеж--%>
                            <c:if test="${(autoPayStatusType == 'Active' || autoPayStatusType == 'Paused') && !isCardArrested}">
                                <tiles:insert definition="clientButton" service="EditAutoPaymentPayment" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.edit"/>
                                    <tiles:put name="commandHelpKey"    value="button.edit"/>
                                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                                    <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
                                    <tiles:put name="action"            value="private/payments/payment.do?form=EditAutoSubscriptionPayment&autoSubNumber=${form.id}"/>
                                </tiles:insert>
                            </c:if>

                            <%--возобновить выполнение автоплатежа--%>
                            <c:if test="${autoPayStatusType == 'Paused' && !isCardArrested}">
                                <tiles:insert definition="clientButton" service="RecoveryAutoSubscriptionPayment" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.restart"/>
                                    <tiles:put name="commandHelpKey"    value="button.restart.help"/>
                                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                                    <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
                                    <tiles:put name="action"            value="private/payments/payment.do?form=RecoveryAutoSubscriptionPayment&autoSubNumber=${form.id}"/>
                                </tiles:insert>
                            </c:if>

                            <%--приостановить выполнение автоплатежа--%>
                            <c:if test="${autoPayStatusType == 'Confirmed' || autoPayStatusType == 'ErrorRegistration' || autoPayStatusType == 'Active'}">
                                <tiles:insert definition="clientButton" service="DelayAutoSubscriptionPayment" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.pause"/>
                                    <tiles:put name="commandTextKey"    value="button.pause"/>
                                    <tiles:put name="commandHelpKey"    value="button.pause.help"/>
                                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                                    <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
                                    <tiles:put name="action"            value="private/payments/payment.do?form=DelayAutoSubscriptionPayment&autoSubNumber=${form.id}"/>
                                </tiles:insert>
                            </c:if>

                            <%--закрыть автоплатеж--%>
                            <c:if test="${autoPayStatusType == 'Confirmed' || autoPayStatusType == 'ErrorRegistration' || autoPayStatusType == 'Active' || autoPayStatusType == 'Paused'}">
                                <tiles:insert definition="clientButton" service="CloseAutoSubscriptionPayment" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.close"/>
                                    <tiles:put name="commandHelpKey"    value="button.close.help"/>
                                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                                    <tiles:put name="viewType"          value="blueGrayLink marginLeft45"/>
                                    <tiles:put name="action"            value="private/payments/payment.do?form=CloseAutoSubscriptionPayment&autoSubNumber=${form.id}"/>
                                </tiles:insert>
                            </c:if>
                        </div>
                    </c:if>
                    <div class="clear">&nbsp;</div>
                    <input type="hidden" id="showPaymentForPeriod" name="showPaymentForPeriod" value="${form.showPaymentForPeriod}"/>
                    <%-- прямоугольные кнопки: "последние 10" и "за период" --%>
                    <div class="paymentListTitle">График платежей</div>
                    <table class="tblFilter">
                        <tr class="rectButtonFrame">
                        <td class="${form.showPaymentForPeriod ? 'in' : ''}activeRectButton" id="lastPayments" onclick="showHidePaymentFilter(false)">
                            Последние 10
                        </td>
                        <td${form.showPaymentForPeriod ? ' style="display: none;"' : ' style="display: table-cell;"'} class="inactiveRectButton" id="betweenDates1" onclick="showHidePaymentFilter(true)">
                            За период
                        </td>
                        <td${form.showPaymentForPeriod ? ' style="display: table-cell;"' : ' style="display: none;"'} class="activeRectButton" style="max-height: 35px;" id="betweenDates2">
                            <div class="float">
                                <nobr>
                                    За период&nbsp;c&nbsp;
                                    <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(fromDate)" format="dd/MM/yyyy"/>'
                                           size="10" name="filter(fromDate)" class="date-pick"/>
                                    &nbsp;по&nbsp;
                                    <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(toDate)" format="dd/MM/yyyy"/>'
                                           size="10" name="filter(toDate)" class="date-pick"/>
                                </nobr>
                            </div>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.showScheduleReport"/>
                                <tiles:put name="commandHelpKey" value="button.showScheduleReport"/>
                                <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                <tiles:put name="viewType" value="icon"/>
                                <tiles:put name="image" value="period-find.png"/>
                            </tiles:insert>
                        </td>
                        </tr>
                    </table>
                    <%-- график платежей --%>
                    <div class="regular-payment-graph${not empty items ? ' paymentListBorder' : ''}">
                        <c:set var="modelPaymentGraphic" value="simple-pagination"/>
                        <tiles:insert definition="simpleTableTemplate" flush="false">
                            <tiles:put name="grid">
                                <%@ include file="/WEB-INF/jsp/common/autosubscription/paymentsGraphic.jsp" %> 
                            </tiles:put>
                            <tiles:put name="isEmpty" value="${empty items}"/>
                            <tiles:put name="emptyMessage">
                                <c:choose>
                                    <c:when test="${not empty form.textUpdateSheduleItemsError}">
                                        ${form.textUpdateSheduleItemsError}
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${form.showPaymentForPeriod}">
                                                В рамках автоплатежа не было выполнено ни одной оплаты за указанный период.
                                            </c:when>
                                            <c:otherwise>
                                                В рамках автоплатежа не было выполнено ни одной оплаты.
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <div class="buttonAreaLeft ">
                        <c:if test="${not empty items}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.scheduleReport.print"/>
                                <tiles:put name="commandHelpKey" value="button.scheduleReport.print"/>
                                <tiles:put name="bundle" value="autoPaymentInfoBundle"/>
                                <tiles:put name="onclick">openPrintScheduleReport(event)</tiles:put>
                                <tiles:put name="isDefault" value="true"/>
                            </tiles:insert>
                        </c:if>
                    </div>
                </tiles:put>
            </tiles:insert>
            </div>
        </tiles:put>
    </c:if>
</tiles:insert>

</html:form>
