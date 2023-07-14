<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/finances/automaticReCategorization" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="autoReCategorizationSettings"/>
        <tiles:put name="submenu" type="string" value="AutoReCategorizationSettings"/>
        <tiles:put name="replicateAccessOperation" value="AutoReCategorizationSettingsOperation"/>
        <tiles:put name="pageName" type="string"><bean:message bundle="financesOptionsBundle" key="label.pageTitle.automaticReCategorization"/></tiles:put>
        <tiles:put name="pageDescription" type="string"><bean:message bundle="financesOptionsBundle" key="label.description.automaticReCategorization"/></tiles:put>
        <tiles:put name="formAlign" value="center"/>
        <tiles:put name="data">
            <fieldset>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="alf.operations.linking.enabled"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="financesOptionsBundle" key="label.linking.enabled"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="linking.max.date.diff"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="financesOptionsBundle" key="label.automaticReCategorization"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="3"/>
                        <tiles:put name="inputDesc"><b><bean:message bundle="financesOptionsBundle" key="label.seconds"/></b></tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                </table>
            </fieldset>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="AutoReCategorizationSettingsOperation">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>