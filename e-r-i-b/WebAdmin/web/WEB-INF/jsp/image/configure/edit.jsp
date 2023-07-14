<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/image/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="EditImageSettings"/>
        <tiles:put name="pageName" type="string"><bean:message key="settings.edit.form.name" bundle="imageSettingsBundle"/></tiles:put>
        <tiles:put name="formAlign" value="center"/>

        <tiles:put name="data">
            <table border="0">
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="iccs.properties.image.max.size.advertising"/>
                    <tiles:put name="fieldDescription"><bean:message key="settings.edit.form.field.advertising.image.size.name" bundle="imageSettingsBundle"/></tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="7"/>
                    <tiles:put name="textMaxLength" value="5"/>
                    <tiles:put name="inputDesc"><b><bean:message key="settings.edit.form.fields.image.size.unit" bundle="imageSettingsBundle"/></b> </tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>
                <tr>
                    <td colspan="2">
                        <fieldset>
                            <legend><bean:message key="settings.edit.form.fields.group.providers.name" bundle="imageSettingsBundle"/></legend>
                            <table>
                                <tiles:insert definition="propertyField" flush="false">
                                    <tiles:put name="fieldName" value="iccs.properties.image.max.size.providerLogo"/>
                                    <tiles:put name="fieldDescription"><bean:message key="settings.edit.form.fields.group.providers.field.logo.name" bundle="imageSettingsBundle"/></tiles:put>
                                    <tiles:put name="showHint" value="none"/>
                                    <tiles:put name="textSize" value="7"/>
                                    <tiles:put name="textMaxLength" value="5"/>
                                    <tiles:put name="inputDesc"><b><bean:message key="settings.edit.form.fields.image.size.unit" bundle="imageSettingsBundle"/></b> </tiles:put>
                                    <tiles:put name="imagePath" value="${imagePath}"/>
                                </tiles:insert>
                                <tiles:insert definition="propertyField" flush="false">
                                    <tiles:put name="fieldName" value="iccs.properties.image.max.size.providerPanel"/>
                                    <tiles:put name="fieldDescription"><bean:message key="settings.edit.form.fields.group.providers.field.panel.name" bundle="imageSettingsBundle"/></tiles:put>
                                    <tiles:put name="showHint" value="none"/>
                                    <tiles:put name="textSize" value="7"/>
                                    <tiles:put name="textMaxLength" value="5"/>
                                    <tiles:put name="inputDesc"><b><bean:message key="settings.edit.form.fields.image.size.unit" bundle="imageSettingsBundle"/></b> </tiles:put>
                                    <tiles:put name="imagePath" value="${imagePath}"/>
                                </tiles:insert>
                                <tiles:insert definition="propertyField" flush="false">
                                    <tiles:put name="fieldName" value="iccs.properties.image.max.size.providerHelp"/>
                                    <tiles:put name="fieldDescription"><bean:message key="settings.edit.form.fields.group.providers.field.help.name" bundle="imageSettingsBundle"/></tiles:put>
                                    <tiles:put name="showHint" value="none"/>
                                    <tiles:put name="textSize" value="7"/>
                                    <tiles:put name="textMaxLength" value="5"/>
                                    <tiles:put name="inputDesc"><b><bean:message key="settings.edit.form.fields.image.size.unit" bundle="imageSettingsBundle"/></b> </tiles:put>
                                    <tiles:put name="imagePath" value="${imagePath}"/>
                                </tiles:insert>
                            </table>
                        </fieldset>
                    </td>
                </tr>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="imageSettingsBundle"/>
                <tiles:put name="action"         value="/settings/image/edit"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"         value="imageSettingsBundle"/>
                <tiles:put name="isDefault"      value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>