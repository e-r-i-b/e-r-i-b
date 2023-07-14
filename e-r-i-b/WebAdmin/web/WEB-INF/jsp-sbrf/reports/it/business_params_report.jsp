<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/it/business_params/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.businessParamsITReport" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message bundle="${bundle}" key="label.periodFromTo" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message key="label.businessParamsITReport" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data or (empty form.data[0] and empty form.data[1])}"/>
                <tiles:put name="emptyMessage"><bean:message key="label.empty" bundle="${bundle}"/></tiles:put>
                <tiles:put name="data">
                  <tr class="tblInfHeader">
                      <th class="titleTable"><bean:message key="label.numberTB" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.nameTB" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.businessParams" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.average" bundle="${bundle}"/></th>
                      <th class="titleTable"><bean:message key="label.max" bundle="${bundle}"/></th>
                  </tr>
                  <c:set var="oddTr" value="0"/>

                  <c:forEach var="listElement" items="${form.data[0]}">
                      <tr class="ListLine${oddTr}">
                          <td class="listItem"><bean:write name="listElement" property="tb_id" filter="true"/></td>
                          <td class="listItem"><bean:write name="listElement" property="tb_name" filter="true"/></td>
                          <td class="listItem"><bean:message  key="label.payDay" bundle="${bundle}"/></td>
                          <td class="listItem">
                              <bean:write name="listElement" property="countDocsDayAvg" format="0.00"/>
                          </td>
                          <td class="listItem"><bean:write name="listElement" property="countDocsDayMax" filter="true"/></td>
                      </tr>
                      <tr class="ListLine${oddTr}">
                          <td class="listItem"><bean:write name="listElement" property="tb_id" filter="true"/></td>
                          <td class="listItem"><bean:write name="listElement" property="tb_name" filter="true"/></td>
                          <td class="listItem"><bean:message  key="label.businessOperationsSecond" bundle="${bundle}"/></td>
                          <td class="listItem">-</td>
                          <td class="listItem"><bean:write name="listElement" property="countOperationsSecondMax" filter="true"/></td>
                      </tr>
                      <c:set var="oddTr" value="${-oddTr + 1}"/>
                  </c:forEach>

                  <c:forEach var="listElement" items="${form.data[1]}">
                      <tr class="ListLine${oddTr}">
                          <td class="listItem" colspan="2"><bean:message  key="label.SBRF" bundle="${bundle}"/></td>

                          <td class="listItem"><bean:message  key="label.clientDay" bundle="${bundle}"/></td>
                          <td class="listItem">
                              <bean:write name="listElement" property="countUsersDayAvg" format="0.00"/>
                          </td>
                          <td class="listItem"><bean:write name="listElement" property="countUsersDayMax" filter="true"/></td>
                      </tr>
                      <c:set var="oddTr" value="${-oddTr + 1}"/>
                  </c:forEach>
                </tiles:put>


            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form></html></html>