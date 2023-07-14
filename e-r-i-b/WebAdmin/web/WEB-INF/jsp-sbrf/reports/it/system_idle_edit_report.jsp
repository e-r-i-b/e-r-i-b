<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/reports/it/system_idle">
    <c:set var="bundle" value="reportsBundle"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="reports">
        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport/SystemIdleItReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.systemIdleITReport" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
                <jsp:include page="../reportParams.jsp">
                    <jsp:param name="period" value="true"/>
                    <jsp:param name="level" value="0"/>
                    <jsp:param name="type_report" value="it"/>
                </jsp:include>
        </tiles:put>
    </tiles:insert>
</html:form>