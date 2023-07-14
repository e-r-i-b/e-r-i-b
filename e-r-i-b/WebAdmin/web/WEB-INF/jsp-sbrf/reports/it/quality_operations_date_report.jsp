<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/reports/it/quality_operations_date/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.qualityDateITReport" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message bundle="${bundle}" key="label.periodFromTo" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message key="label.qualityDateITReport" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="label.empty" bundle="${bundle}"/></tiles:put>
                <tiles:put name="data">
                  <tr class="tblInfHeader">
                      <th class="titleTable" colspan="4">&nbsp;</th>
                      <th class="titleTable" colspan="40"><bean:message key="label.typesOperations" bundle="${bundle}"/></th>
                  </tr>
                  <tr class="tblInfHeader">
                      <th class="titleTable" rowspan="2"><bean:message key="label.numberTB" bundle="${bundle}"/></th>
                      <th class="titleTable" rowspan="2"><bean:message key="label.nameTB" bundle="${bundle}"/></th>
                      <th class="titleTable" rowspan="2"><bean:message key="label.date" bundle="${bundle}"/></th>
                      <th class="titleTable" rowspan="2"><bean:message key="label.businessCharacteristics" bundle="${bundle}"/></th>

                      <th class="titleTable" colspan="21"><bean:message key="label.informationOperation" bundle="${bundle}"/></th>
                      <th class="titleTable" colspan="5"><bean:message key="label.conversion"           bundle="${bundle}"/></th>
                      <th class="titleTable" colspan="2"><bean:message key="label.repaymentOfCredit"    bundle="${bundle}"/></th>
                      <th class="titleTable" colspan="3"><bean:message key="label.paymentForServices"   bundle="${bundle}"/></th>
                      <th class="titleTable" colspan="5"><bean:message key="label.claims"               bundle="${bundle}"/></th>
                      <th class="titleTable" colspan="4"><bean:message key="label.otherOperations"   bundle="${bundle}"/></th>
                  </tr>
                  <tr class="tblInfHeader">
                      <!--  Информационные услуги -->
                      <th class="titleTable"><bean:message key="label.entryInSystem" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.clientProfil" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.confirmation" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.titlePage" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.cardsReport"  bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.cardsReportForLast10Operation" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.stopCard"     bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.stopAccount"  bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.standingOrderReport" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.countStandingOrder" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.cancelStandingOrder" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.startStandingOrder" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.operationsHistory" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.createTemplates" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.mobileTemplateView" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.mobileTemplateCreate" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.accountDEPO" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.creditInfo" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.metalAccountInfo" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.printCheck" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.infoFromPFRF" bundle="${bundle}"/></th>

                      <!-- Переводы -->
                      <th class="titleTable"><bean:message key="label.fromCardToCard" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.fromCardToAccount" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.fromAccountToCard" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.fromAccountToAccount" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.toForeignAccount" bundle="${bundle}"/></th>

                      <!-- Погашение кредита -->
                      <th class="titleTable"><bean:message key="label.creditFromCard" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.creditFromAccount" bundle="${bundle}"/></th>

                      <!-- Оплата услуг -->
                      <th class="titleTable"><bean:message key="label.toContractOrganization" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.toNotContractOrganization" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.toBillingOrganization" bundle="${bundle}"/></th>

                      <!-- Создание заявок -->
                      <th class="titleTable"><bean:message key="label.loanProduct" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.loanOffer" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.loanCardProduct" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.loanCardOffer" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.virtualCard" bundle="${bundle}"/></th>

                      <!-- Другие операции -->
                      <th class="titleTable"><bean:message key="label.initPayment" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.initTemplates" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.newsView" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.support" bundle="${bundle}"/></th>
                  </tr>
                      <c:set var="operationsTB" value="${phiz:getMapOperationTBPeriodReport(form.data, form.reportStartDate, form.reportEndDate)}"/>
                      <c:set var="oddTr" value="0"/>
                      <c:forEach var="tb" items="${operationsTB}">
                            <tr class="ListLine${oddTr}">
                                <c:set var="depsValue" value="${fn:split(tb.key, '|')}"/>
                                <td class="listItem" rowspan="5" ><c:out value="${depsValue[0]}"/></td>
                                <td class="listItem" rowspan="5" ><c:out value="${depsValue[1]}"/></td>
                                <c:set var="dateOperations" value="${fn:split(depsValue[2], '.')}"/>
                                <td class="listItem" rowspan="5" ><c:out value="${dateOperations[2]}"/>.<c:out value="${dateOperations[1]}"/>.<c:out value="${dateOperations[0]}"/></td>

                            </tr>
                            <tr  class="ListLine${oddTr}">
                                <td><bean:message key="label.allOperations" bundle="${bundle}"/></td>
                                <td class="listItem"><c:out value="${tb.value['entryInSystem']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['clientProfil']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['confirmation']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['titlePage']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReport']['Count']}" default="0"/></td>     <%-- Просмотр информации по карте = просмотру 10 последних операций--%>
                                <td class="listItem"><c:out value="${tb.value['cardsReportForLast10Operation']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopCard']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopAccount']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['standingOrderReport']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['countStandingOrder']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cancelStandingOrder']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['startStandingOrder']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['operationsHistory']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['createTemplates']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateView']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateCreate']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['accountDEPO']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditInfo']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['metalAccountInfo']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['printCheck']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['infoFromPFRF']['Count']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['fromCardToCard']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromCardToAccount']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToCard']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToAccount']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toForeignAccount']['Count']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['creditFromCard']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditFromAccount']['Count']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['toContractOrganization']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toNotContractOrganization']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toBillingOrganization']['Count']}" default="0"/></td>

                                <!-- Создание заявок -->
                                <td class="listItem"><c:out value="${tb.value['loanProduct']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanOffer']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardProduct']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardOffer']['Count']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['virtualCard']['Count']}" default="0"/></td>

                                <!-- Другие операции -->
                                <td class="titleTable"><c:out value="${tb.value['initPayment']['Count']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['initTemplates']['Count']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['newsView']['Count']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['support']['Count']}" default="0"/></td>
                           </tr>
                            <tr  class="ListLine${oddTr}">
                                <td><bean:message key="label.successOperations" bundle="${bundle}"/></td>
                                <td class="listItem"><c:out value="${tb.value['entryInSystem']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['clientProfil']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['confirmation']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['titlePage']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReport']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReportForLast10Operation']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopCard']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopAccount']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['standingOrderReport']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['countStandingOrder']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cancelStandingOrder']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['startStandingOrder']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['operationsHistory']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['createTemplates']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateView']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateCreate']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['accountDEPO']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditInfo']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['metalAccountInfo']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['printCheck']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['infoFromPFRF']['SuccessCount']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['fromCardToCard']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromCardToAccount']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToCard']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToAccount']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toForeignAccount']['SuccessCount']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['creditFromCard']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditFromAccount']['SuccessCount']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['toContractOrganization']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toNotContractOrganization']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toBillingOrganization']['SuccessCount']}" default="0"/></td>

                                <!-- Создание заявок -->
                                <td class="listItem"><c:out value="${tb.value['loanProduct']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanOffer']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardProduct']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardOffer']['SuccessCount']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['virtualCard']['SuccessCount']}" default="0"/></td>

                                <!-- Другие операции -->
                                <td class="titleTable"><c:out value="${tb.value['initPayment']['SuccessCount']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['initTemplates']['SuccessCount']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['newsView']['SuccessCount']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['support']['SuccessCount']}" default="0"/></td>
                            </tr>
                            <tr  class="ListLine${oddTr}">
                                <td><bean:message key="label.errorOperations" bundle="${bundle}"/></td>
                                <td class="listItem"><c:out value="${tb.value['entryInSystem']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['clientProfil']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['confirmation']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['titlePage']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReport']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReportForLast10Operation']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopCard']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopAccount']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['standingOrderReport']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['countStandingOrder']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['cancelStandingOrder']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['startStandingOrder']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['operationsHistory']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['createTemplates']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateView']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateCreate']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['accountDEPO']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditInfo']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['metalAccountInfo']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['printCheck']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['infoFromPFRF']['Error']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['fromCardToCard']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromCardToAccount']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToCard']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToAccount']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toForeignAccount']['Error']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['creditFromCard']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditFromAccount']['Error']}" default="0"/></td>

                                <td class="listItem"><c:out value="${tb.value['toContractOrganization']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toNotContractOrganization']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['toBillingOrganization']['Error']}" default="0"/></td>

                                <!-- Создание заявок -->
                                <td class="listItem"><c:out value="${tb.value['loanProduct']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanOffer']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardProduct']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardOffer']['Error']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['virtualCard']['Error']}" default="0"/></td>

                                <!-- Другие операции -->
                                <td class="titleTable"><c:out value="${tb.value['initPayment']['Error']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['initTemplates']['Error']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['newsView']['Error']}" default="0"/></td>
                                <td class="titleTable"><c:out value="${tb.value['support']['Error']}" default="0"/></td>
                            </tr>
                            <tr  class="ListLine${oddTr}">
                                <td><bean:message key="label.precentErrorOperations" bundle="${bundle}"/></td>
                                <td class="listItem"><c:out value="${tb.value['entryInSystem']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['clientProfil']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['confirmation']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['titlePage']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReport']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['cardsReportForLast10Operation']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopCard']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['stopAccount']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['standingOrderReport']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['countStandingOrder']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['cancelStandingOrder']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['startStandingOrder']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['operationsHistory']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['createTemplates']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateView']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['mobileTemplateCreate']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['accountDEPO']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditInfo']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['metalAccountInfo']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['printCheck']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['infoFromPFRF']['PercentError']}" default="0%"/></td>

                                <td class="listItem"><c:out value="${tb.value['fromCardToCard']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromCardToAccount']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToCard']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['fromAccountToAccount']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['toForeignAccount']['PercentError']}" default="0%"/></td>

                                <td class="listItem"><c:out value="${tb.value['creditFromCard']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['creditFromAccount']['PercentError']}" default="0%"/></td>

                                <td class="listItem"><c:out value="${tb.value['toContractOrganization']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['toNotContractOrganization']['PercentError']}" default="0%"/></td>
                                <td class="listItem"><c:out value="${tb.value['toBillingOrganization']['PercentError']}" default="0%"/></td>

                                <!-- Создание заявок -->
                                <td class="listItem"><c:out value="${tb.value['loanProduct']['PercentError']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanOffer']['PercentError']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardProduct']['PercentError']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['loanCardOffer']['PercentError']}" default="0"/></td>
                                <td class="listItem"><c:out value="${tb.value['virtualCard']['PercentError']}" default="0"/></td>

                                <!-- Другие операции -->
                                <td class="titleTable"><c:out value="${tb.value['initPayment']['PercentError']}" default="0%"/></td>
                                <td class="titleTable"><c:out value="${tb.value['initTemplates']['PercentError']}" default="0%"/></td>
                                <td class="titleTable"><c:out value="${tb.value['newsView']['PercentError']}" default="0%"/></td>
                                <td class="titleTable"><c:out value="${tb.value['support']['PercentError']}" default="0%"/></td>
                            </tr>
                        <c:set var="oddTr" value="${-oddTr + 1}"/>  
                      </c:forEach>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
