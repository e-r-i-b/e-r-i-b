<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/autotransfer/info" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="paymentInfoUrl" value="${phiz:calculateActionURL(pageContext, '/autotransfer/payment/info?id=')}"/>

<tiles:insert definition="autoSubscriptions">
<tiles:put name="submenu" type="string" value="AutoTransfers"/>
<tiles:put name="data" type="string">
<script type="text/javascript">

    function showHidePaymentFilter(showPeriod)
    {
        if (document.getElementById("showPaymentForPeriod").value == showPeriod.toString())
            return;
        document.getElementById("showPaymentForPeriod").value = showPeriod;
        if (!showPeriod)
        {
            findCommandButton('button.showScheduleReport').click('', false);
        }
        document.getElementById("enableButtonShowScheduleReport").value = showPeriod;
        disableDateInput();
    }

    function disableDateInput()
    {
        document.getElementById("inputToDate").disabled = document.getElementById("showPaymentForPeriod").value != "true";
        document.getElementById("inputFromDate").disabled = document.getElementById("showPaymentForPeriod").value != "true";
    }

    doOnLoad(disableDateInput);

    function openPrintScheduleReport(event)
    {
        var url = "${phiz:calculateActionURL(pageContext,'/autotransfer/info/printScheduleReport.do')}";
        var params = "?id=" + ${form.id};

        params += "&showPaymentForPeriod="+document.getElementById("showPaymentForPeriod").value;
        if (document.getElementById("showPaymentForPeriod").value == "true")
        {
            var toDate = getElementValue("filter(toDate)");
            var fromDate = getElementValue("filter(fromDate)");

            if (!checkPeriod("filter(fromDate)", "filter(toDate)", "���� ������","���� ���������",true))
                return;
            params += "&fromDateString=" + fromDate;
            params += "&toDateString=" + toDate;
        }
        openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
    }
</script>

<!--����-->
<tiles:put name="menu" type="string">
    <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
</tiles:put>

<c:set var="paymentGraphicMenu">
    <tr>
        <td class="valignTop text-align-right field">������:</td>
        <td>
            <table cellpadding="0" cellspacing="0">
                <tr>
                    <td><input type="radio" name="periodView" value="lastPayments" onchange="showHidePaymentFilter(false);" ${!form.showPaymentForPeriod ? "checked" : ""}>10 ��������� ��������</td>
                    <td></td>
                </tr>
                <tr>
                    <td>
                        <input type="radio" name="periodView" value="betweenDates" onchange="showHidePaymentFilter(true);" ${form.showPaymentForPeriod ? "checked" : ""}>
                        �� ������&nbsp;c&nbsp;
                        <input id="inputFromDate" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(fromDate)" format="dd/MM/yyyy"/>'
                               size="10" name="filter(fromDate)" class="date-pick"
                               style="margin-top: -6px; margin-bottom: -6px;"/>
                        &nbsp;��&nbsp;
                        <input id="inputToDate" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(toDate)" format="dd/MM/yyyy"/>'
                               size="10" name="filter(toDate)" class="date-pick"
                               style="margin-top: -6px; margin-bottom: -6px;"/>
                    </td>
                    <td>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"        value="button.showScheduleReport"/>
                            <tiles:put name="commandHelpKey"    value="button.showScheduleReport"/>
                            <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                            <tiles:put name="viewType"          value="blueBorder"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    return (document.getElementById("enableButtonShowScheduleReport").value == "true");
                                }
                            </tiles:put>
                        </tiles:insert>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</c:set>

<tiles:insert definition="paymentForm" flush="false">
    <tiles:put name="id"            value="reports"/>
    <tiles:put name="name"          value="��������� ���������� �� ������������"/>
    <tiles:put name="description"       value="�� ������ �������� �� ������ ���������� ��������� ���������� �� ������������, �������� ������ � ������ ������������."/>
    <tiles:put name="additionalStyle"   value=" bottomWidth"/>
    <tiles:put name="data">
        <c:set var="autoSubscriptionLink" value="${form.link}"/>
        <c:set var="cardLink" value="${autoSubscriptionLink.cardLink}"/>
        <c:set var="payment"  value="${form.autoSubscriptionInfo}"/>
        <%@ include file="/WEB-INF/jsp/common/autosubscription/employee-p2p-detail-info.jsp" %>

        <input type="hidden" id="showPaymentForPeriod" name="showPaymentForPeriod" value="${form.showPaymentForPeriod}"/>
        <!--������ �� ������ ���������� ����� �������� "10 ��������� ��������", �� � ������ �������� �� "10 ��������� ��������" ������ ��������� ������� click -->
        <input type="hidden" id="enableButtonShowScheduleReport" name="enableButtonShowScheduleReport" value="${form.showPaymentForPeriod}"/>

        <c:set var="items" value="${form.scheduleItems}"/>
        <c:set var="fromEmployee" value="true"/>
        <c:set var="modelPaymentGraphic" value="list"/>
        <tiles:insert definition="simpleTableTemplate" flush="false" >
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
                        � ������ ������������ �� ���� ��������� �� ����� ������<c:if test="${form.showPaymentForPeriod}"> �� ��������� ������</c:if>.
                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </tiles:insert>
    </tiles:put>

    <c:if test="${payment != null}">
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" service="AutoTransfersManagement" flush="false">
                <tiles:put name="commandTextKey"    value="button.scheduleReport.print"/>
                <tiles:put name="commandHelpKey"    value="button.scheduleReport.print"/>
                <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                <tiles:put name="viewType"          value="buttonGreen"/>
                <tiles:put name="onclick">openPrintScheduleReport(event)</tiles:put>
                <tiles:put name="isDefault"         value="true"/>
            </tiles:insert>

            <c:set var="autoPayStatusType" value="${autoSubscriptionLink.autoPayStatusType}"/>

            <%--������������� ����������--%>
            <c:set var="isEditAvailableByState" value="${autoPayStatusType == 'Active' || autoPayStatusType == 'Paused'}"/>
            <c:set var="isEditAvailable" value="${isEditAvailableByState && phiz:impliesServiceRigid('EmployeeEditP2PAutoTransferClaim')}"/>
            <c:if test="${isEditAvailable}">
                <tiles:insert definition="clientButton" service="EmployeeEditP2PAutoTransferClaim" flush="false">
                    <tiles:put name="commandTextKey"    value="button.edit"/>
                    <tiles:put name="commandHelpKey"    value="button.edit"/>
                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                    <tiles:put name="viewType"          value="buttonGrey"/>
                    <tiles:put name="action"            value="/private/payments/edit.do?form=EditP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
                </tiles:insert>
            </c:if>

            <%--����������� ���������� �����������--%>
            <c:set var="isRecoveryAvailableByState" value="${autoPayStatusType == 'Paused'}"/>
            <c:set var="isRecoveryAvailable" value="${isRecoveryAvailableByState && phiz:impliesServiceRigid('EmployeeRecoveryP2PAutoTransferClaim')}"/>
            <c:if test="${isRecoveryAvailable}">
                <tiles:insert definition="clientButton" service="EmployeeRecoveryP2PAutoTransferClaim" flush="false">
                    <tiles:put name="commandTextKey"    value="button.restart"/>
                    <tiles:put name="commandHelpKey"    value="button.restart.help"/>
                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                    <tiles:put name="viewType"          value="buttonGrey"/>
                    <tiles:put name="action"            value="/private/payments/edit.do?form=RecoveryP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
                </tiles:insert>
            </c:if>

            <%--������������� ���������� �����������--%>
            <c:set var="isDelayAvailableByState" value="${autoPayStatusType == 'Confirmed' || autoPayStatusType == 'ErrorRegistration' || autoPayStatusType == 'Active'}"/>
            <c:set var="isDelayAvailable" value="${isDelayAvailableByState && phiz:impliesServiceRigid('EmployeeDelayP2PAutoTransferClaim')}"/>
            <c:if test="${isDelayAvailable}">
                <tiles:insert definition="clientButton" service="EmployeeDelayP2PAutoTransferClaim" flush="false">
                    <tiles:put name="commandTextKey"    value="button.pause"/>
                    <tiles:put name="commandHelpKey"    value="button.pause.help"/>
                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                    <tiles:put name="viewType"          value="buttonGrey"/>
                    <tiles:put name="action"            value="/private/payments/edit.do?form=DelayP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
                </tiles:insert>
            </c:if>

            <%--������� ����������--%>
            <c:set var="isCloseAvailableByState" value="${autoPayStatusType == 'Confirmed' || autoPayStatusType == 'ErrorRegistration' || autoPayStatusType == 'Active' || autoPayStatusType == 'Paused'}"/>
            <c:set var="isCloseAvailable" value="${isCloseAvailableByState && phiz:impliesServiceRigid('EmployeeCloseP2PAutoTransferClaim')}"/>
            <c:if test="${isCloseAvailable}">
                <tiles:insert definition="clientButton" service="EmployeeCloseP2PAutoTransferClaim" flush="false">
                    <tiles:put name="commandTextKey"    value="button.close"/>
                    <tiles:put name="commandHelpKey"    value="button.close.help"/>
                    <tiles:put name="bundle"            value="autoPaymentInfoBundle"/>
                    <tiles:put name="viewType"          value="buttonGrey"/>
                    <tiles:put name="action"            value="/private/payments/edit.do?form=CloseP2PAutoTransferClaim&autoSubNumber=${form.id}"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </c:if>
</tiles:insert>
<script type="text/javascript">
    addClearMasks(null,
            function(event)
            {
                clearInputTemplate('filter(fromDate)', '__.__.____');
                clearInputTemplate('filter(toDate)', '__.__.____');
            });
</script>
</tiles:put>
</tiles:insert>
</html:form>