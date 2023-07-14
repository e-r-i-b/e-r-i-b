<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/active_users/vsp/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="BusinessReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.countActiveUsersVSP" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report" value="${phiz:dateToString(form.reportStartDate.time)}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text"><bean:message key="label.countActiveUsersVSP" bundle="${bundle}"/> <bean:message key="label.fromDate" bundle="${bundle}" arg0="${date_report}"/></tiles:put>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data" bundle="${bundle}">
                            <sl:collectionItem  title="label.numberTB" property="tb_id"/>
                            <sl:collectionItem  title="label.nameTB" property="tb_name"/>
                            <sl:collectionItem  title="label.numberOSB" property="osb_id"/>
                            <sl:collectionItem  title="label.numberVSP" property="vsp_id"/>
                            <sl:collectionItem  title="label.countUDBOAll" property="count_UDBO_all"/>
                            <sl:collectionItem  title="label.countSBOLAll" property="count_SBOL_all"/>
                            <sl:collectionItem  title="label.countLoginsAndPasswords"><c:if test="${listElement.count_getLogins != 0}"><c:out value="${listElement.count_getLogins}"/></c:if></sl:collectionItem>
                            <sl:collectionItem  title="label.countAuth" property="count_auth"/>
                        </sl:collection>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage"><bean:message key="label.empty" bundle="${bundle}"/></tiles:put>
                    <input name="id" type="hidden" value="${form.id}"/> 
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>