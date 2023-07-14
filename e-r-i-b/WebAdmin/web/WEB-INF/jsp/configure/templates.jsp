<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<html:form action="/configure/Templates"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="Templates"/>

        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">

        <tiles:importAttribute/>

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="personsConfigure"/>
            <tiles:put name="name" value="Настройка шаблонов"/>
            <tiles:put name="description" value=""/>
            <tiles:put name="data">
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.payment.templates.message.planForDeacitivate"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clienttemplates.message.plainingForDeactivate"/></tiles:put>
                        <tiles:put name="fieldType" value="textarea"/>
                        <tiles:put name="showHint">bottom</tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.clienttemplates.message.plainingForDeactivate.hint"/></tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        <tiles:put name="textMaxLength" value="75"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="not.active.provider.message"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clienttemplates.message.inactive"/></tiles:put>
                        <tiles:put name="fieldType" value="textarea"/>
                        <tiles:put name="showHint">bottom</tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.clienttemplates.message.inactive.hint"/></tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        <tiles:put name="textMaxLength" value="75"/>
                    </tiles:insert>
                </table>
            </tiles:put>
        </tiles:insert>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.revoke"/>
                <tiles:put name="commandHelpKey" value="button.revoke.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>
</tiles:put>
</tiles:insert>
</html:form>
