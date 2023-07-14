<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/settings/addressbook/synchronization" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="EditSmartAddressBookSynchronizationSettings"/>
        <tiles:put name="data">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message key="title.UAK.sync.setting" bundle="configureBundle"/></tiles:put>
                <tiles:put name="description" value=""/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function() {
                            $("input[name=field(com.rssl.iccs.addressbook.timeforstart)]").createMask(SHORT_TIME_TEMPLATE);
                        });
                    </script>

                    <table>
                        <c:set var="notAvailable" value="${not phiz:impliesServiceRigid('EditSmartAddressBookSynchronizationSettings')}"/>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.timeforstart"/>
                            <tiles:put name="fieldDescription"><bean:message key="label.UAK.timeForSync" bundle="configureBundle"/></tiles:put>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="textSize" value="1"/>
                            <tiles:put name="fieldHint"><bean:message key="label.UAK.timeForSync.hint" bundle="configureBundle"/></tiles:put>
                            <tiles:put name="disabled" value="${notAvailable}"/>
                            <tiles:put name="requiredField" value="true"/>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.smart.show.sb.client.attribute"/>
                            <tiles:put name="fieldDescription"><bean:message key="label.show.SB.client.attribute" bundle="configureBundle"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint" value="none"/>
                            <tiles:put name="disabled" value="${notAvailable}"/>
                        </tiles:insert>
                    </table>
                </tiles:put>
            </tiles:insert>

            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false" service="EditSmartAddressBookSynchronizationSettings">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>