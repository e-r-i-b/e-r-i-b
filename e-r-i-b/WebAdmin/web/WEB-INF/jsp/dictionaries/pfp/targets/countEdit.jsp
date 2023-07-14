<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/targets/count/edit" onsubmit="return setEmptyAction();">
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="editPFPTargetCount"/>
        <tiles:put name="submenu" type="string" value="targetCountEdit"/>
        <tiles:put name="pageName"><bean:message bundle="pfpTargetsBundle" key="targetCount.label.edit.name"/></tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="pfpTargetsBundle" key="targetCount.label.edit.description"/></tiles:put>
        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="target.count"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="pfpTargetsBundle" key="targetCount.label.edit.field.count"/></tiles:put>
                    <tiles:put name="requiredField" value="true"/>
                    <tiles:put name="textSize" value="4"/>
                    <tiles:put name="textMaxLength" value="2"/>
                    <tiles:put name="showHint" value="none"/>
                </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"         value="button.save"/>
                <tiles:put name="commandTextKey"     value="targetCount.button.save"/>
                <tiles:put name="commandHelpKey"     value="targetCount.button.save.help"/>
                <tiles:put name="bundle"             value="pfpTargetsBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="isDefault"            value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="formAlign" value="center"/>
    </tiles:insert>
</html:form>