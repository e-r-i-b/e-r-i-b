<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/contract/okr/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="BusinessReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.countContractOKR" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report" value="${phiz:dateToString(form.reportStartDate.time)}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text"><bean:message key="label.countContractOKR" bundle="${bundle}" arg0="${date_report}"/> </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage"><bean:message key="label.empty2" bundle="${bundle}"/></tiles:put>

                     <tiles:put name="data">
                        <c:set var="oddTr" value="0"/>
                        <tr class="tblInfHeader">
                            <th class="titleTable" rowspan="2"><bean:message key="label.numberTB" bundle="${bundle}"/></th>
                            <th class="titleTable" rowspan="2"><bean:message key="label.nameTB" bundle="${bundle}"/></th>
                            <th class="titleTable" rowspan="2"><bean:message key="label.numberOSB" bundle="${bundle}"/></th>
                            <th class="titleTable" rowspan="2"><bean:message key="label.numberVSP" bundle="${bundle}"/></th>
                            <th class="titleTable" rowspan="2"><bean:message key="label.employeName" bundle="${bundle}"/></th>
                            <th class="titleTable" colspan="3"><bean:message key="label.countUDBOAll" bundle="${bundle}"/></th>
                            <th class="titleTable" colspan="3"><bean:message key="label.countSBOLAll" bundle="${bundle}"/></th>
                        </tr>
                        <tr class="tblInfHeader">
                            <th class="titleTable"><bean:message key="label.countUDBOAllVSP" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countYear"       bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countMonth"      bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countSBOLAllVSP" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countYear"       bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countMonth"      bundle="${bundle}"/></th>
                        </tr>
                        <c:forEach var="listElement" items="${form.data}">
                            <tr  class="ListLine${oddTr}">
                                <td class="listItem"><bean:write name="listElement" property="tb_id"/></td>
                                <td class="listItem"><bean:write name="listElement" property="tb_name" filter="true"/></td>
                                <td class="listItem"><bean:write name="listElement" property="osb_id"/></td>
                                <td class="listItem"><bean:write name="listElement" property="vsp_id"/></td>
                                <td class="listItem"><bean:write name="listElement" property="employe_name" filter="true"/></td>
                                <td class="listItem"><bean:write name="listElement" property="count_UDBO_all"/></td>
                                <td class="listItem"><bean:write name="listElement" property="count_UDBO_year"/></td>
                                <td class="listItem"><bean:write name="listElement" property="count_UDBO_month"/></td>
                                <td class="listItem"><bean:write name="listElement" property="count_SBOL_all"/></td>
                                <td class="listItem"><bean:write name="listElement" property="count_SBOL_year"/></td>
                                <td class="listItem"><bean:write name="listElement" property="count_SBOL_month"/></td>
                            </tr>
                            <c:set var="oddTr" value="${-oddTr + 1}"/>
                        </c:forEach>
                    </tiles:put>
        </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>