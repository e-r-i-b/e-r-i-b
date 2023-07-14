<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/settings/connect/udbo/editRules" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="configEdit">
        <c:set var="bundle" value="configureBundle"/>
        <tiles:put name="submenu" type="string" value="ConnectUDBOSettings"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editDepartments"/>
                <tiles:put name="name">
                    <bean:message key="settings.connect.udbo.edit.rules" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="settings.connect.udbo.edit.rules.description" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="settings.connect.udbo.edit.rules.startDate" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="startDate"><bean:write name="form" property="fields.startDate" format="dd.MM.yyyy"/></c:set>
                            <html:text property="field(startDate)" value="${startDate}" maxlength="10" styleClass="dot-date-pick"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="settings.connect.udbo.edit.rules.text" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <p><bean:message key="settings.connect.udbo.edit.rules.text.loadHint" bundle="${bundle}"/></p>
                            <div><html:file property="rulesFile"/></div>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${not empty form.rulesText}">
                        <html:textarea styleId="UDBORulesText" property="rulesText" cols="55" rows="10" disabled="true"/>
                    </c:if>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="localeBundle"/>
                        <tiles:put name="action" value="/settings/connect/udbo"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>