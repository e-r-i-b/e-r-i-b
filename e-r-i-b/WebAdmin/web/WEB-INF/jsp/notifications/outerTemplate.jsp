<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/outerTemplate" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="EditOuterTemplateOperation"/>
        <tiles:put name="replicateAccessOperation" value="EditOuterTemplateOperation"/>
        <tiles:put name="pageName" type="string">
            <bean:message bundle="configureBundle" key="settings.outer.template.title"/>
        </tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="configureBundle" key="settings.outer.template.desc"/></tiles:put>
        <tiles:put name="additionalStyle" value="propertiesForm"/>

        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizia.notifications.outerTemplatePath"/>
                <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.outer.template.path.field.name"/></tiles:put>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="textSize" value="40"/>
                <tiles:put name="textMaxLength" value="100"/>
                <tiles:put name="requiredField" value="true" />
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false"
                          operation="EditOuterTemplateOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false"
                          operation="EditOuterTemplateOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="window.location.reload();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
