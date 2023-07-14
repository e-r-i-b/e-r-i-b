<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/security" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="configureBundle"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="pageName"><bean:message key="settings.security.page.title" bundle="configureBundle"/></tiles:put>
        <tiles:put name="submenu" type="string" value="EditSettingsSecurityOperation"/>
        <tiles:put name="additionalStyle" value="propertiesForm"/>
        <tiles:put name="replicateAccessOperation" value="EditSettingsSecurityOperation"/>
        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.business.limits.security.level.setting.start.date"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="${bundle}" key="settings.security.page.field.startDate"/></tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="styleClass" value="dot-date-pick"/>
                    <tiles:put name="format" value="dd.MM.yyyy"/>
                    <tiles:put name="showHint">none</tiles:put>
                    <tiles:put name="tdStyle" value="rightAlign"/>
                    <tiles:put name="requiredField" value="true"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.business.limits.security.level.setting.less.count.days"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="${bundle}" key="settings.security.page.field.daysNumber.start"/></tiles:put>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                    <tiles:put name="inputDesc"><bean:message bundle="${bundle}" key="settings.security.page.field.daysNumber.end"/></tiles:put>
                    <tiles:put name="showHint">none</tiles:put>
                    <tiles:put name="tdStyle" value="rightAlign"/>
                    <tiles:put name="requiredField" value="true"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByERIB"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="${bundle}" key="settings.security.page.field.channels"/></tiles:put>
                    <tiles:put name="fieldType" value="checkbox"/>
                    <tiles:put name="inputDesc"><bean:message bundle="${bundle}" key="settings.security.page.field.channels.inERIB"/></tiles:put>
                    <tiles:put name="showHint">none</tiles:put>
                    <tiles:put name="tdStyle" value="rightAlign"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByCC"/>
                    <tiles:put name="fieldType" value="checkbox"/>
                    <tiles:put name="inputDesc"><bean:message bundle="${bundle}" key="settings.security.page.field.channels.inCC"/></tiles:put>
                    <tiles:put name="showHint">none</tiles:put>
                    <tiles:put name="tdStyle" value="rightAlign"/>
                    <tiles:put name="emptyFieldDescription" value="true"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByATM"/>
                    <tiles:put name="fieldType" value="checkbox"/>
                    <tiles:put name="inputDesc"><bean:message bundle="${bundle}" key="settings.security.page.field.channels.inATM"/></tiles:put>
                    <tiles:put name="showHint">none</tiles:put>
                    <tiles:put name="tdStyle" value="rightAlign"/>
                    <tiles:put name="emptyFieldDescription" value="true"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByVSP"/>
                    <tiles:put name="fieldType" value="checkbox"/>
                    <tiles:put name="inputDesc"><bean:message bundle="${bundle}" key="settings.security.page.field.channels.inVSP"/></tiles:put>
                    <tiles:put name="showHint">none</tiles:put>
                    <tiles:put name="tdStyle" value="rightAlign"/>
                    <tiles:put name="emptyFieldDescription" value="true"/>
                </tiles:insert>
            </table>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditSettingsSecurityOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditSettingsSecurityOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="window.location.reload();"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="formAlign" value="center"/>
    </tiles:insert>
</html:form>
