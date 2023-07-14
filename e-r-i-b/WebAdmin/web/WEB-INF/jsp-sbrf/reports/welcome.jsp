<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set value="reportsBundle" var="bundle"/>

<tiles:insert definition="reports">
        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.title" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orange"/>
                <tiles:put name="data">
                    <bean:message key="label.welcome" bundle="${bundle}"/>
                </tiles:put>
            </tiles:insert>
	    </tiles:put>
</tiles:insert>