<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/it/quality_operations_period/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.qualityPeriodITReport" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message bundle="${bundle}" key="label.periodFromTo" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message key="label.qualityPeriodITReport" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="${bundle}">
                        <sl:collectionItem  title="label.numberTB"                  property="tb_id"/>
                        <sl:collectionItem  title="label.nameTB"                    property="tb_name" />
                        <sl:collectionItem  title="label.allOperations"             property="count"/>
                        <sl:collectionItem  title="label.successOperations"         property="countSuccessOperations" />
                        <sl:collectionItem  title="label.errorOperations"           property="countErrorOperations"/>
                        <sl:collectionItem  title="label.precentErrorOperations"    property="percentErrorOperations"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="label.empty" bundle="${bundle}"/></tiles:put>
                <input name="id" type="hidden" value="${form.id}"/> 
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>