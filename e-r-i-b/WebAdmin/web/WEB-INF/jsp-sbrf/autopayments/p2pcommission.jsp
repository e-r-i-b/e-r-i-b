<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/autopayments/p2pcommission" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="AutoPaymentP2PNewCommission"/>
        <tiles:put name="pageTitle"><bean:message bundle="configureBundle" key="settings.autopayments.p2pcommission.title"/></tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="configureBundle" key="settings.autopayments.p2pcommission.description.title"/></tiles:put>

        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizia.autopayments.p2p.getCommissionFromWay4"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.autopayments.p2pcommission"/></tiles:put>
                    <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.autopayments.p2pcommission.switcher"/></tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="fieldType" value="switcher"/>
                </tiles:insert>
            </table>
            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false"
                              operation="EditSettingsMenuOperation">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false"
                              operation="EditSettingsMenuOperation">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>
