<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/configure/period" onsubmit="return setEmptyAction();">
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="pfpConfigure"/>
        <tiles:put name="submenu" type="string" value="editDefaultPeriod"/>
        <tiles:put name="replicateAccessOperation" value="EditDefaultPeriodSettingOperation"/>
        <tiles:put name="pageName">
            <bean:message bundle="pfpConfigureBundle" key="period.default.label.edit.title"/>
        </tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="pfpConfigureBundle" key="period.default.label.edit.description"/></tiles:put>
        <tiles:put name="data" type="string">

        <table>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.pfp.settings.period.default"/>
                <tiles:put name="fieldDescription"><bean:message bundle="pfpConfigureBundle" key="period.default.label.edit.field.value"/></tiles:put>
                <tiles:put name="inputDesc"><b><bean:message bundle="pfpConfigureBundle" key="period.default.label.edit.field.value.unit"/></b></tiles:put>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="textSize" value="4"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>
        </table>

        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="clientButton" flush="false" operation="EditDefaultPeriodSettingOperation">
                <tiles:put name="commandTextKey"    value="period.default.button.cancel"/>
                <tiles:put name="commandHelpKey"    value="period.default.button.cancel.help"/>
                <tiles:put name="bundle"            value="pfpConfigureBundle"/>
                <tiles:put name="action"            value="/pfp/configure/period.do"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" operation="EditDefaultPeriodSettingOperation" flush="false" >
                <tiles:put name="commandKey"         value="button.save"/>
                <tiles:put name="commandTextKey"     value="period.default.button.save"/>
                <tiles:put name="commandHelpKey"     value="period.default.button.save.help"/>
                <tiles:put name="bundle"             value="pfpConfigureBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="isDefault"            value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>