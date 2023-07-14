<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/loyaltyProgram/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <tiles:put name="submenu" type="string" value="LoyaltyProgramSettings"/>
        <tiles:put name="pageName" type="string">
            <bean:message bundle="configureBundle" key="settings.loyalty.page.title"/>
        </tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="configureBundle" key="settings.loyalty.page.desc"/></tiles:put>
        <tiles:put name="data" type="string">
            <fieldset>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.loyalty.base.url"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loyalty.url"/></tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.loyalty.url.desc"/></tiles:put>
                        <tiles:put name="showHint" value="bottom"/>
                        <tiles:put name="textSize" value="30"/>
                        <tiles:put name="textMaxLength" value="100"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.loyalty.pub.cert"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loyalty.pub.cert"/></tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.loyalty.pub.cert.desc"/></tiles:put>
                        <tiles:put name="showHint" value="bottom"/>
                        <tiles:put name="textSize" value="30"/>
                        <tiles:put name="textMaxLength" value="100"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.loyalty.priv.cert"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loyalty.priv.cert"/></tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.loyalty.priv.cert.desc"/></tiles:put>
                        <tiles:put name="showHint" value="bottom"/>
                        <tiles:put name="textSize" value="30"/>
                        <tiles:put name="textMaxLength" value="100"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.loyalty.store.type"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loyalty.store.type"/></tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.loyalty.store.type.desc"/></tiles:put>
                        <tiles:put name="showHint" value="bottom"/>
                        <tiles:put name="textSize" value="30"/>
                        <tiles:put name="textMaxLength" value="100"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.loyalty.store.path"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loyalty.store.path"/></tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.loyalty.store.path.desc"/></tiles:put>
                        <tiles:put name="showHint" value="bottom"/>
                        <tiles:put name="textSize" value="30"/>
                        <tiles:put name="textMaxLength" value="100"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.loyalty.store.password"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.loyalty.store.passw"/></tiles:put>
                        <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.loyalty.store.passw.desc"/></tiles:put>
                        <tiles:put name="showHint" value="bottom"/>
                        <tiles:put name="textSize" value="30"/>
                        <tiles:put name="textMaxLength" value="100"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                </table>
            </fieldset>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" >
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
