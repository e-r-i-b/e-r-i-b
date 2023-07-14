<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/logging/entriesLog/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="loggingEdit">
    <tiles:put name="submenu" type="string" value="EditEntriesLoggingLevel"/>
	<tiles:put name="pageTitle" type="string">
		<bean:message key="edit.input.log.title" bundle="loggingBundle"/>
	</tiles:put>

	<tiles:put name="menu" type="string">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="postbackNavigation" value="true"/>
			<tiles:put name="bundle" value="loggingBundle"/>
			<tiles:put name="image" value=""/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</tiles:put>

<tiles:put name="data" type="string">
    <c:set var="prefix" value="com.rssl.phizic.logging.entries."/>
    <%@ include file="editAutoArchivationSettings.jsp" %>
</tiles:put>
</tiles:insert>
</html:form>
</html>