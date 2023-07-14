<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/reports/it/proactive/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="reportsBundle"/>
    <tiles:insert definition="reportsList">
        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.proactiveITReport" bundle="${bundle}"/></tiles:put>
        <c:set var="allBank" value="${form.allBank}"/>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message bundle="${bundle}" key="label.periodFromTo" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>
            <c:set var="operations" value="${phiz:getMapProactiveMonitoringReport(form.data)}"/>
            <c:set var="total" value="${phiz:getSumMapProactiveMonitoringReport(form.data, allBank)}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message key="label.proactiveITReport" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="label.proactive.empty" bundle="${bundle}"/></tiles:put>
                <tiles:put name="data">
                    <tr class="tblInfHeader">
                        <th class="titleTable">№</th>
                        <th class="titleTable"><bean:message key="label.operationName" bundle="${bundle}"/></th>
                        <c:if test="${allBank == false}"><th class="titleTable"><bean:message key="label.proactive.TBName" bundle="${bundle}"/></th></c:if>
                        <th class="titleTable"><bean:message key="label.proactive.allOperations" bundle="${bundle}"/></th>
                        <th class="titleTable"><bean:message key="label.proactive.errorOperations" bundle="${bundle}"/></th>
                        <th class="titleTable"><bean:message key="label.proactive.smallTimeOperations" bundle="${bundle}"/></th>
                        <th class="titleTable"><bean:message key="label.proactive.averageTimeOperations" bundle="${bundle}"/></th>
                        <th class="titleTable"><bean:message key="label.proactive.longTimeOperations" bundle="${bundle}"/></th>
                    </tr>
                    <%--вход клиента--%>
                    <c:set var="numType" value="1."/>
                    <c:set var="operationsName" value="confirmLogin,internetSecurity,loginDepartments,clientAutentication,useOfert" />
                    <c:if test="${allBank == true}"><c:set var="operationsName" value="loginPage,${operationsName}" /></c:if>
                    <c:set var="catName" value="login"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%-- Подверждение операции одноразовым паролем--%>
                    <c:set var="numType" value="2."/>
                    <c:set var="catName" value="confirmOperation"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/operationTemplate.jsp" %>

                     <%--информационно-управляющие--%>
                    <c:set var="numType" value="3."/>
                    <c:set var="operationsName"  value="cardAmount,accAmount,loans,autoPayment,longOffer,cardOperation,accountOperation,depoAccAmount,IMAccount,printAccount,printCard,graphicAbstract" />
                    <c:set var="catName" value="info"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                     <%-- Перевод на карту--%>
                    <c:set var="numType" value="4."/>
                    <c:set var="operationsName" value="internalTransferToCardSave,internalTransferToCardPreConfirm,internalTransferToCardConfirm,internalTransferToCardView" />
                    <c:set var="catName" value="internalTransferToCard"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Перевод на вклад--%>
                    <c:set var="numType" value="5."/>
                    <c:set var="operationsName"  value="internalTransferToAccountSave,internalTransferToAccountPreConfirm,internalTransferToAccountConfirm,internalTransferToAccountView" />
                    <c:set var="catName" value="internalTransferToAccount"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Перевод на счет в другом банке--%>
                    <c:set var="numType" value="6."/>
                    <c:set var="operationsName" value="rurPaymentSave,rurPaymentPreConfirm,rurPaymentConfirm,rurPaymentView" />
                    <c:set var="catName" value="rurPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--биллинговый платеж--%>
                    <c:set var="numType" value="7."/>
                    <c:set var="operationsName" value="choiceServiceProvider,billingPaymentSave,billingPaymentPreConfirm,billingPaymentConfirm,billingPaymentView" />
                    <c:set var="catName" value="billingPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Не биллинговый платеж--%>
                    <c:set var="numType" value="8."/>
                    <c:set var="operationsName" value="notBillingPaymentSave,notBillingPaymentPreConfirm,notBillingPaymentConfirm,notBillingPaymentView" />

                    <c:set var="catName" value="notBillingPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--налоговый платеж--%>
                     <c:set var="numType" value="9."/>
                     <c:set var="operationsName"
                         value="taxPaymentSave,taxPaymentPreConfirm,taxPaymentConfirm,taxPaymentView" />

                     <c:set var="catName" value="taxPayment"/>
                     <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Платеж в ФНС--%>
                    <c:set var="numType" value="10."/>
                    <c:set var="operationsName"     value="FNSPaymentSave,FNSPaymentPreConfirm,FNSPaymentConfirm,FNSPaymentView" />
                    <c:set var="catName" value="FNSPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                     <%--Оплата кредита--%>
                    <c:set var="numType" value="11."/>
                    <c:set var="operationsName" value="loanPaymentSave,loanPaymentPreConfirm,loanPaymentConfirm,loanPaymentView" />
                    <c:set var="catName" value="loanPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Заведение длительного поручения--%>
                    <c:set var="numType" value="12."/>
                    <c:set var="operationsName" value="longOfferSave,longOfferPreConfirm,longOfferConfirm,longOfferView" />
                    <c:set var="catName" value="createLongOffer"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Редактирование длительного порчения--%>
                    <c:set var="numType" value="13."/>
                    <c:set var="operationsName"   value="saveLongOfferName,showLongOffer" />
                    <c:set var="catName" value="editLongOffer"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Удаление длительного поручения--%>
                    <c:set var="numType" value="14."/>
                    <c:set var="operationsName" value="refuseLongOfferClaimSave,refuseLongOfferClaimPreConfirm,refuseLongOfferClaimConfirm,refuseLongOfferClaimView" />
                    <c:set var="catName" value="refuseLongOfferClaim"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--автоплатежи--%>
                    <c:set var="numType" value="15."/>
                    <c:set var="operationsName"  value="autoPaymentSave,autoPaymentChangeToSMS,autoPaymentConfirm,autoPaymentView" />
                    <c:set var="catName" value="createAutoPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--автоплатежи--%>
                    <c:set var="numType" value="16."/>
                    <c:set var="operationsName"  value="editAutoPaymentSave,editAutoPaymentPreConfirm,editAutoPaymentConfirm,editAutoPaymentView" />
                    <c:set var="catName" value="editAutoPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--автоплатежи--%>
                    <c:set var="numType" value="17."/>
                    <c:set var="operationsName"  value="refuseAutoPaymentSave,refuseAutoPaymentPreConfirm,refuseAutoPaymentConfirm,refuseAutoPaymentView" />
                    <c:set var="catName" value="refuseAutoPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Заведение/редактирование шаблона перевода--%>
                    <c:set var="numType" value="18."/>
                    <c:set var="operationsName"
                        value="saveTransferTemplate,confirmTransferTemplateSMS,confirmTransferTemplate,viewTransferTemplate" />

                    <c:set var="catName" value="createTransferTemplate"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--удаление шаблона перевода--%>
                    <c:set var="numType" value="19."/>
                    <c:set var="catName" value="removeTransferTemplate"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/operationTemplate.jsp" %>

                    <%--Заведение/редактирование шаблона оплаты услуг--%>
                    <c:set var="numType" value="20."/>
                    <c:set var="operationsName"
                        value="savePaymentTemplate,confirmPaymentTemplateSMS,confirmPaymentTemplate,viewPaymentTemplate" />

                    <c:set var="catName" value="createPaymentTemplate"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--удаление шаблона оплаты услуг--%>
                    <c:set var="numType" value="21."/>
                    <c:set var="catName" value="removePaymentTemplate"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/operationTemplate.jsp" %>

                    <%--создание шаблона мобильного банка--%>
                    <c:set var="numType" value="22."/>
                    <c:set var="operationsName"  value="saveSmsTemplate,preConfirmSmsTemplate,confirmSmsTemplate,viewSmsTemplate" />
                    <c:set var="catName" value="createSmsTemplate"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%-- удаление шаблона МБ--%>
                    <c:set var="numType" value="23."/>
                    <c:set var="catName" value="removeSmsTemplate"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/operationTemplate.jsp" %>

                    <%--открытие вклада--%>
                    <c:set var="numType" value="24."/>
                    <c:set var="operationsName"
                        value="choiceAccount,accountOpeningPaymentSave,accountOpeningPaymentPreConfirm,accountOpeningPaymentConfirm,accountOpeningPaymentView" />

                    <c:set var="catName" value="accountOpeningPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                  <%--закрытие вклада--%>
                    <c:set var="numType" value="25."/>
                    <c:set var="operationsName"
                        value="accountClosingPaymentSave,accountClosingPaymentPreConfirm,accountClosingPaymentConfirm,accountClosingPaymentView" />

                    <c:set var="catName" value="accountClosingPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                  <%--кредит--%>
                    <c:set var="numType" value="26."/>
                    <c:set var="operationsName"
                        value="choiceLoanProduct,loanProductClaimSave,loanProductClaimPreConfirm,loanProductClaimConfirm,loanProductClaimView" />

                    <c:set var="catName" value="loanProductClaim"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--предодобренные кредит--%>
                    <c:set var="numType" value="27."/>
                    <c:set var="operationsName"
                        value="choiceLoanOffer,loanOfferClaimSave,loanOfferClaimPreConfirm,loanOfferClaimConfirm,loanOfferClaimView" />

                    <c:set var="catName" value="loanOfferClaim"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--кредитная карта--%>
                    <c:set var="numType" value="28."/>
                    <c:set var="operationsName"
                        value="choiceLoanCardProduct,loanCardProductClaimSave,loanCardProductClaimPreConfirm,loanCardProductClaimConfirm,loanCardProductClaimView" />

                    <c:set var="catName" value="loanCardProductClaim"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--предодобренные карты--%>
                    <c:set var="numType" value="29."/>
                    <c:set var="operationsName"     value="choiceLoanCardOffer,loanCardOfferClaimSave,loanCardOfferClaimPreConfirm,loanCardOfferClaimConfirm,loanCardOfferClaimView" />
                    <c:set var="catName" value="loanCardOfferClaim"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--оплата задолженности по счету ДЕПО--%>
                    <c:set var="numType" value="30."/>
                    <c:set var="operationsName" value="depoPaymentSave,depoPaymentPreConfirm,depoPaymentConfirm,depoPaymentView" />

                    <c:set var="catName" value="depoPayment"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--письма--%>
                    <c:set var="numType" value="31."/>
                    <c:set var="catName" value="mail"/>
                    <c:set var="operationsName"
                        value="saveMail,sentMailView,receivedMailView" />
                     <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <%--Заявка об утере сберкнижки--%>
                    <c:set var="numType" value="32."/>
                    <c:set var="operationsName" value="lossPassbookApplicationSave,lossPassbookApplicationPreConfirm,lossPassbookApplicationConfirm,lossPassbookApplicationView" />
                    <c:set var="catName" value="lossPassbookApplication"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/reports/it/categoryTemplate.jsp" %>

                    <tr class="ListLine0">
                        <td class="listItem"><span class='bold'>33.</span></td>
                        <td class="listItem"><span class='bold'><bean:message key="label.proactive.total" bundle="${bundle}"/></span></td>
                         <c:if test="${allBank == false}"><td class="listItem">&nbsp;</td></c:if>
                        <td class="listItem"><c:out value="${total['Count']}" default="0"/></td>
                        <td class="listItem"><c:out value="${total['PercentError']}" default="0"/></td>
                        <td class="listItem"><c:out value="${total['SmallTimeOperation']}" default="0"/></td>
                        <td class="listItem"><c:out value="${total['AverageTimeOperation']}" default="0"/></td>
                        <td class="listItem"><c:out value="${total['LongTimeOperation']}" default="0"/></td>
                    </tr>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>