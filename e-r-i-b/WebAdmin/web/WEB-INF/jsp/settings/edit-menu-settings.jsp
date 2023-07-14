<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/menu" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="EditSettingsMenuOperation"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="configureBundle" key="settings.menu.page.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="configureBundle" key="settings.menu.page.desc"/></tiles:put>
                <tiles:put name="data">

                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.business.simple.newsCount"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.menu.page.field.news.count"/></tiles:put>
                            <tiles:put name="textSize" value="20"/>
                            <tiles:put name="textMaxLength" value="20"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.menu.page.field.news.count.desc"/></tiles:put>
                            <tiles:put name="tdStyle" value="rightAlign"/>
                            <tiles:put name="requiredField" value="true"/>
                        </tiles:insert>
                    </table>

                </tiles:put>

            </tiles:insert>
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
