<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath"    value="${globalUrl}/images"/>
<c:set var="imagePath"          value="${skinUrl}/images"/>

<html:form action="/group/risk/edit" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionariesMain">
        <c:set var="bundle" value="groupsRiskBundle"/>
        <tiles:put name="submenu"   type="string" value="GroupRiskList"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="ListGroupRiskOperation" service="GroupRiskManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.list"/>
                <tiles:put name="commandHelpKey"    value="button.list.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="/group/risk/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                     <bean:message bundle="${bundle}" key="edit.title.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="${bundle}" key="edit.description.name"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.name" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="50" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.rank" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(rank)" style="width: 80px;">
                                <html:option value="HIGH">Высокая</html:option>
                                <html:option value="LOW">Низкая</html:option>
                            </html:select>
                            <html:checkbox property="field(isDefault)"/>
                            <bean:message key="label.default" bundle="${bundle}"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditGroupRiskOperation" service="GroupRiskManagment">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="action"         value="/group/risk/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
