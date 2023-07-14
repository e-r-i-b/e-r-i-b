<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/gate/services/configure" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
    <tiles:put name="tilesDefinition" value="seviceIQWaveConfigure"/>
        <tiles:put name="submenu" type="string" value="SeviceIQWaveConfigure"/>
        <tiles:put name="additionalStyle" value="propertiesForm"/>
        <tiles:put name="replicateAccessOperation" value="SeviceIQWaveConfigureOperation"/>
        <tiles:put name="pageName"><bean:message bundle="gateServiceConfigBundle" key="label.configure.name"/></tiles:put>
        <tiles:put name="pageDescription" ><bean:message bundle="gateServiceConfigBundle" key="label.configure.description"/></tiles:put>
        <tiles:put name="data" type="string">
            <table class="alignTop">
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="restriction.number.requests.state.operation.CheckPaymentExecutionJob"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="gateServiceConfigBundle" key="label.configure.field.restriction.number.requests"/></tiles:put>
                    <tiles:put name="requiredField" value="true"/>
                    <tiles:put name="showHint" value="right"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="10"/>
                    <tiles:put name="inputDesc">
                        <div class="clear"></div>
                        <div class="propertyDesc"><bean:message bundle="gateServiceConfigBundle" key="label.configure.field.restriction.number.requests.description"/></div>
                    </tiles:put>
                </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false"
                          operation="SeviceIQWaveConfigureOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false"
                          operation="SeviceIQWaveConfigureOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="window.location.reload();"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="formAlign" value="center"/>
    </tiles:insert>
</html:form>
