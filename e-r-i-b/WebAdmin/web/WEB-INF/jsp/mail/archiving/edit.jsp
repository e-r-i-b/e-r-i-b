<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/mail/archiving/edit">

<tiles:insert definition="propertiesForm">
    <tiles:put name="tilesDefinition" value="mailEdit"/>
    <tiles:put name="submenu" type="string" value="EditArchivingMail"/>
    <tiles:put name="pageName">
        <bean:message key="edit.archiving.title" bundle="mailBundle"/>
    </tiles:put>
    <tiles:put name="pageDescription"><bean:message bundle="mailBundle" key="edit.archiving.description"/></tiles:put>
    <tiles:put name="data">
        <c:set var="form" value="${EditArchivingMailForm}"/>
        <span class="bold"><bean:message key="label.archivation" bundle="mailBundle"/>:</span>

        <table>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.mail.archive.folder"/>
                <tiles:put name="fieldDescription"><bean:message key="label.path" bundle="mailBundle"/></tiles:put>
                <tiles:put name="textSize" value="30"/>
                <tiles:put name="textMaxLength" value="128"/>
                <tiles:put name="showHint">none</tiles:put>
                <tiles:put name="tdStyle" value="rightAlign"/>
            </tiles:insert>

            <c:set var="parameter" value="com.rssl.iccs.mail.archive.incoming.mail"/>
            <c:set var="label"><bean:message bundle="configureBundle" key="settings.mail.archiving.incoming.label"/></c:set>
            <%@ include file="archivingSettings.jsp"%>

            <c:set var="parameter" value="com.rssl.iccs.mail.archive.outgoing.mail"/>
            <c:set var="label"><bean:message bundle="configureBundle" key="settings.mail.archiving.outgoing.label"/></c:set>
            <%@ include file="archivingSettings.jsp"%>

            <c:set var="parameter" value="com.rssl.iccs.mail.archive.deleted.mail"/>
            <c:set var="label"><bean:message bundle="configureBundle" key="settings.mail.archiving.deleted.label"/></c:set>
            <%@ include file="archivingSettings.jsp"%>
        </table>
    </tiles:put>

    <tiles:put name="formButtons">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey"     value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle"  value="mailBundle"/>
            <tiles:put name="onclick" value="javascript:resetForm(event)"/>
        </tiles:insert>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="bundle" value="mailBundle"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="postbackNavigation" value="true"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>
