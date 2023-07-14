<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>


<html:form action="/employee/settings/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="EmployeeSettings"/>
        <tiles:put name="data">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message key="settings.employee.menuitem" bundle="configureBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="label.employee.settings.header.description" bundle="personsBundle"/></tiles:put>
                <tiles:put name="data">
                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.employeeSettings.timeToBlockAccountByInactivity"/>
                            <tiles:put name="fieldDescription"><bean:message key="label.time.to.block.account.by.inactivity" bundle="personsBundle"/></tiles:put>
                            <tiles:put name="textMaxLength" value="3"/>
                            <tiles:put name="textSize" value="1"/>
                            <tiles:put name="disabled" value="${not phiz:impliesServiceRigid('EmployeeSettingsService')}"/>
                            <tiles:put name="showHint" value="none"/>
                            <tiles:put name="inputDesc"><bean:message key="label.day.short" bundle="personsBundle"/></tiles:put>
                        </tiles:insert>
                    </table>
                </tiles:put>
            </tiles:insert>

            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false" service="EmployeeSettingsService">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" service="EmployeeSettingsService">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>