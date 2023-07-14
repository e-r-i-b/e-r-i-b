<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/settings/thresholdNotification" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="EditThresholdNotificationSettingsOperation"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Настройка оповещения о превышении порога"/>
                <tiles:put name="data">
                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="addressBookSync.thresholdNotification.periodType"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.periodType"/></tiles:put>
                            <tiles:put name="fieldType" value="select"/>
                            <tiles:put name="selectItems">DAY@<bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.periodType.day"/>|MONTH@<bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.periodType.month"/></tiles:put>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>

                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="addressBookSync.thresholdNotification.thresholdCount"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.thresholdCount"/></tiles:put>
                            <tiles:put name="textSize" value="5"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>

                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="addressBookSync.thresholdNotification.emailText"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.emailText"/></tiles:put>
                            <tiles:put name="fieldType" value="textarea"/>
                            <tiles:put name="textMaxLength" value="50"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.email.desc"/></tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>

                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="addressBookSync.thresholdNotification.email"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.addressBook.thresholdNotification.email"/></tiles:put>
                            <tiles:put name="textMaxLength" value="70"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="paymentLabel"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                    </table>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="false"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="window.location.reload();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>