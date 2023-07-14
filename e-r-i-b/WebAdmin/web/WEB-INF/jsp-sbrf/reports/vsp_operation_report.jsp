<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/reports/operations/vsp/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="BusinessReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.countOperationVSP" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message bundle="${bundle}" key="label.periodFromTo" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message key="label.countOperationVSP" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="label.operation.empty" bundle="${bundle}"/></tiles:put>
                <tiles:put name="data">
                  <tr class="tblInfHeader">
                      <th class="titleTable"  rowspan="3"><bean:message key="label.numberTB" bundle="${bundle}"/></th>
                      <th class="titleTable"  rowspan="3"><bean:message key="label.nameTB" bundle="${bundle}"/></th>
                      <th class="titleTable"  rowspan="3"><bean:message key="label.numberOSB" bundle="${bundle}"/></th>
                      <th class="titleTable"  rowspan="3"><bean:message key="label.numberVSP" bundle="${bundle}"/></th>
                      <th class="titleTable"  colspan="39"><bean:message key="label.typesOperations" bundle="${bundle}"/></th>
                  </tr>
                  <tr class="tblInfHeader">
                      <th class="titleTable"  colspan="20"><bean:message key="label.informationOperation" bundle="${bundle}"/></th>
                      <th class="titleTable"  colspan="5"><bean:message key="label.conversion" bundle="${bundle}"/></th>
                      <th class="titleTable"  colspan="2"><bean:message key="label.repaymentOfCredit" bundle="${bundle}"/></th>
                      <th class="titleTable"  colspan="3"><bean:message key="label.paymentForServices" bundle="${bundle}"/></th>
                      <th class="titleTable"  colspan="5"><bean:message key="label.claims" bundle="${bundle}"/></th>
                      <th class="titleTable"  colspan="4"><bean:message key="label.otherOperations" bundle="${bundle}"/></th>
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

                  <c:set var="oddTr" value="0"/>
                  <c:set var="vspHash" value="${phiz:getOperationsReportByVsp(form.data)}"/>
                  <c:forEach var="departmentsInfo" items="${vspHash}">
                      <tr class="ListLine${oddTr}">

                          <c:forEach var="depsValue" items="${fn:split(departmentsInfo.key, '|')}">
                            <td class="listItem"><c:out value="${depsValue}"/></td>
                          </c:forEach>

                          <td class="listItem"><c:out value="${departmentsInfo.value['entryInSystem']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['clientProfil']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['confirmation']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['titlePage']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['cardsReportForLast10Operation']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['cardsReportForLast10Operation']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['stopCard']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['stopAccount']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['standingOrderReport']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['countStandingOrder']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['cancelStandingOrder']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['operationsHistory']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['createTemplates']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['mobileTemplateView']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['mobileTemplateCreate']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['accountDEPO']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['creditInfo']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['metalAccountInfo']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['printCheck']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['infoFromPFRF']}" default="0"/></td>   
                  
                          <td class="listItem"><c:out value="${departmentsInfo.value['fromCardToCard']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['fromCardToAccount']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['fromAccountToCard']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['fromAccountToAccount']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['toForeignAccount']}" default="0"/></td>

                          <td class="listItem"><c:out value="${departmentsInfo.value['creditFromCard']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['creditFromAccount']}" default="0"/></td>

                          <td class="listItem"><c:out value="${departmentsInfo.value['toContractOrganization']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['toNotContractOrganization']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['toBillingOrganization']}" default="0"/></td>

                          <!-- Создание заявок -->
                          <td class="listItem"><c:out value="${departmentsInfo.value['loanProduct']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['loanOffer']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['loanCardProduct']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['loanCardOffer']}" default="0"/></td>
                          <td class="listItem"><c:out value="${departmentsInfo.value['virtualCard']}" default="0"/></td>

                          <!-- Другие операции -->
                          <td class="titleTable"><c:out value="${departmentsInfo.value['initPayment']}" default="0"/></td>
                          <td class="titleTable"><c:out value="${departmentsInfo.value['initTemplates']}" default="0"/></td>
                          <td class="titleTable"><c:out value="${departmentsInfo.value['newsView']}" default="0"/></td>
                          <td class="titleTable"><c:out value="${departmentsInfo.value['support']}" default="0"/></td>                          
                       </tr>
                      <c:set var="oddTr" value="${-oddTr + 1}"/>
                 </c:forEach>

                </tiles:put>


            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form></html>
