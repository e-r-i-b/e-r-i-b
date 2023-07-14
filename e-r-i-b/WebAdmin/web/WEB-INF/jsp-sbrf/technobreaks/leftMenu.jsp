<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="ManageTechnoBreaks">
    <tiles:put name="enabled" value="${submenu != 'ListTechnoBreak'}"/>
    <tiles:put name="action"  value="/technobreak/list.do"/>
    <tiles:put name="text">
        <bean:message key="technobreak.dictionary.header" bundle="technobreaksBundle"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message key="technobreak.dictionary.header" bundle="technobreaksBundle"/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="EditAutoStopService">
    <tiles:put name="enabled" value="${submenu != 'EditAutoStopSettings'}"/>
    <tiles:put name="action"  value="/settings/technobreaks/auto.do"/>
	<tiles:put name="text">
        <bean:message key="technobreak.auto.edit.header" bundle="technobreaksBundle"/>
    </tiles:put>
	<tiles:put name="title">
        <bean:message key="technobreak.auto.edit.header" bundle="technobreaksBundle"/>
    </tiles:put>
</tiles:insert>