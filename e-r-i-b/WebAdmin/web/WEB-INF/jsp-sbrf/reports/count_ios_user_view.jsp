<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/count/ios/report/view" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="reportAbstract" value="${form.reportAbstract}"/>
    <c:set var="reportMap" value="${form.reportMap}"/>
    <c:set var="reportDate" value="${phiz:dateToString(reportAbstract.startDate.time)}"/>
    <c:set var="bundle" value="reportsBundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="BusinessReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.countIOSReport" bundle="${bundle}"/></tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.countIOSReport.unload"/>
                <tiles:put name="commandHelpKey" value="button.countIOSReport.unload"/>
                <tiles:put name="bundle"         value="${bundle}"/>
                <tiles:put name="image"          value=""/>
                <tiles:put name="autoRefresh"    value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text"><bean:message key="label.countIOSReport" bundle="${bundle}"/> <bean:message key="label.fromDate" bundle="${bundle}" arg0="${reportDate}"/></tiles:put>
                    <tiles:put name="data">
                        <tr class="tblInfHeader">
                            <th class="titleTable"><bean:message key="label.countIOSReport.tbName" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countIOSReport.osbName" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countIOSReport.vspName" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countIOSReport.count" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countIOSReport.lastCount" bundle="${bundle}"/></th>
                        </tr>
                        <c:set var="totalCountAll" value="0"/>
                        <c:set var="lastCountAll" value="0"/>
                        <c:forEach items="${reportMap}" var="tbReport">
                            <c:set var="totalCountTb" value="0"/>
                            <c:set var="lastCountTb" value="0"/>
                            <tr>
                                <td rowspan="${phiz:getTbColspanValue(tbReport.value)}">
                                    ${tbReport.key}
                                </td>
                            <c:forEach items="${tbReport.value}" var="osbReport">                                
                                <td rowspan="${phiz:size(osbReport.value)}">
                                    <c:out value="${osbReport.key}" default="&nbsp;" escapeXml="false"/>
                                </td>
                                <c:set var="totalCountOSB" value="0"/>
                                <c:set var="lastCountOSB" value="0"/>
                                <c:forEach items="${osbReport.value}" var="report">
                                    <td>
                                        <c:out value="${report.vspName}" default="&nbsp;" escapeXml="false"/>
                                    </td>
                                    <td>
                                        ${report.totalCount}
                                    </td>
                                    <td>
                                        ${report.lastCount}
                                    </td>
                            </tr>
                            <tr>
                                    <c:set var="totalCountOSB" value="${totalCountOSB + report.totalCount}"/>
                                    <c:set var="lastCountOSB" value="${lastCountOSB + report.lastCount}"/>
                                </c:forEach>
                                <td colspan="2">
                                    <bean:message key="label.countIOSReport.totalCountOSB" bundle="${bundle}"/>
                                </td>
                                <td>
                                    ${totalCountOSB}
                                </td>
                                <td>
                                    ${lastCountOSB}
                                </td>
                            </tr>
                            <tr>
                                <c:set var="totalCountTb" value="${totalCountTb + totalCountOSB}"/>
                                <c:set var="lastCountTb" value="${lastCountTb + lastCountOSB}"/>
                            </c:forEach>
                            <td colspan="3">
                                <bean:message key="label.countIOSReport.totalCountTB" bundle="${bundle}"/>
                            </td>
                            <td>
                                ${totalCountTb}
                            </td>
                            <td>
                                ${lastCountTb}
                            </td>
                            </tr>
                            <c:set var="totalCountAll" value="${totalCountAll + totalCountTb}"/>
                            <c:set var="lastCountAll" value="${lastCountAll + lastCountTb}"/>
                        </c:forEach>
                        <tr>
                            <td colspan="3">
                                <bean:message key="label.countIOSReport.totalCountAll" bundle="${bundle}"/>
                            </td>
                            <td>
                                ${totalCountAll}
                            </td>
                            <td>
                                ${lastCountAll}
                            </td>
                        </tr>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty reportMap}"/>
                    <tiles:put name="emptyMessage"><bean:message key="label.countIOSReport.empty" bundle="${bundle}"/></tiles:put>
            </tiles:insert>
            <script type="text/javascript">
                doOnLoad(function(){
                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                    {
                         <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/reports/count/ios/report/downloading')}"/>
                         clientBeforeUnload.showTrigger=false;
                         goTo('${downloadFileURL}');
                         clientBeforeUnload.showTrigger=false;
                    }    
                });
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>