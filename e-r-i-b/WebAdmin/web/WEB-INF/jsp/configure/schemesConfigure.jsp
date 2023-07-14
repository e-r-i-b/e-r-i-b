<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/schemes/configure" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="showCheckbox" value="${form.replication}"/>

<tiles:insert definition="propertiesForm">
    <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
   	<tiles:put name="submenu" type="string" value="Schemes"/>

        <tiles:put name="data" type="string">

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="schemesConfigure"/>
            <tiles:put name="name" value="—хема прав доступа."/>
            <tiles:put name="description" value="»спользуйте форму дл€ изменени€ схемы прав доступа сотрудника."/>
            <tiles:put name="data">

                <c:if test="${showCheckbox == 'true'}">
                    <input type="checkbox" id="replicationSelect" value="com.rssl.iccs.default.scheme.employee" name="selectedProperties"/>
                    <label for="replicationSelect" id="replicationSelectLabel"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
                </c:if>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        —хема прав сотрудника
                    </tiles:put>
                    <tiles:put name="data">
                        <c:set var="helpers" value="${form.helpers}"/>
                        <html:select property="field(com.rssl.iccs.default.scheme.employee)" disabled="${showCheckbox == 'true'}">
                            <html:option value="">
                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                            </html:option>
                            <c:forEach var="helper" items="${helpers}">
                                <c:set var="helperCategory" value="${helper.category}"/>
                                <c:if test="${fn:length(helpers) > 1}">
                                    <optgroup label="<bean:message key="label.scheme.category.${helperCategory}" bundle="schemesBundle"/>">
                                </c:if>
                                <c:set var="accessSchemes" value="${helper.schemes}"/>
                                <c:forEach var="scheme" items="${accessSchemes}">
                                    <html:option value="${scheme.id}">
                                        <c:out value="${scheme.name}"/>
                                    </html:option>
                                </c:forEach>
                                <c:if test="${fn:length(helpers) > 1}">
                                    </optgroup>
                                </c:if>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        —хема прав доступа, котора€ автоматически назначаетс€ при создании
                        нового сотрудника банка в системе.
                    </tiles:put>
                </tiles:insert>

            </tiles:put>
    </tiles:insert>
    </tiles:put>
    <tiles:put name="formButtons">
        <tiles:insert definition="commandButton" flush="false" operation="SchemeConfigureOperation">
            <tiles:put name="commandKey"         value="button.save"/>
            <tiles:put name="commandHelpKey"     value="button.save.help"/>
            <tiles:put name="bundle"             value="commonBundle"/>
            <tiles:put name="isDefault"            value="true"/>
            <tiles:put name="postbackNavigation" value="true"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false" operation="SchemeConfigureOperation">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle"         value="commonBundle"/>
            <tiles:put name="onclick"        value="resetForm(event)"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>