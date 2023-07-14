<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/reports/it/quality_date">
    <c:set value="reportsBundle" var="bundle"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="reports">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="ITReport/QualityDateItReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.qualityDateITReport" bundle="${bundle}"/></tiles:put>

        <tiles:put name="data" type="string">

                <jsp:include page="../reportParams.jsp">
                    <jsp:param name="period" value="true"/>
                    <jsp:param name="level" value="1"/>
                    <jsp:param name="type_report" value="it"/>
                </jsp:include>



        </tiles:put>

    </tiles:insert>
</html:form>