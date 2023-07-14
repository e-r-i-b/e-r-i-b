<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/clientProfile/clientbasketinfo/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="ClientInfoConfig"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="configureBundle" key="settings.basketinfo.editbasketinfotitle"/></tiles:put>
        <tiles:put name="data" type="string">

            <div class = "messageInfoDiv">
                <div class="paymentLabel">
                        ${form.messageTittle}
                </div>
            </div>
            <div class = "messageInfoDiv">
                <div class="paymentLabel">
                    <textarea name="field(message)" cols="18" rows="8">${form.message}</textarea>
                </div>
            </div>

            <div class="pmntFormMainButton floatRight">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"         value="imageSettingsBundle"/>
                    <tiles:put name="isDefault"      value="true"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>

