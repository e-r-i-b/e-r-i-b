<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:useAttribute name="action" scope="page" ignore="true"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="paymentForm" flush="false">
    <tiles:put name="data">
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.task.status" bundle="migrationBundle"/>
            </tiles:put>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <c:out value="${form.status}"/>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="buttons">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.return"/>
            <tiles:put name="commandHelpKey" value="button.return"/>
            <tiles:put name="bundle"         value="commonBundle"/>
            <tiles:put name="action"         value="${action}"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert/>
