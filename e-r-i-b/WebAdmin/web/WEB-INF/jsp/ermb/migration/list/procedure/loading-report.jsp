<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/ermb/migration/loading">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="migrationMain">
        <tiles:put name="submenu" type="string" value="Load"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="loading.title" bundle="migrationBundle"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert page="/WEB-INF/jsp/ermb/migration/list/taskReport/report.jsp" flush="false">
                <tiles:put name="action" value="/ermb/migration/loading"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
