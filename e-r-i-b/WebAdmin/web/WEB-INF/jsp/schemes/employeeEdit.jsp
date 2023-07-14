<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/schemes/employee/edit" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="helpers" value="${form.helpers}" scope="request"/>
    <c:set var="readOnly" value="${false}" scope="request"/>
    <c:set var="currentSchemeCategory" scope="request">${form.category}</c:set>

    <tiles:insert definition="schemesMain">
        <tiles:put name="submenu" type="string" value="List"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name"><bean:message key="scheme.access.form.edit.name" bundle="schemesBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="scheme.access.form.edit.description" bundle="schemesBundle"/></tiles:put>
                <tiles:put name="data">
                    <fieldset>
                        <legend><bean:message key="scheme.access.form.edit.fields.group.name" bundle="schemesBundle"/></legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="title">
                                <bean:message key="scheme.access.form.edit.fields.scheme" bundle="schemesBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:select styleId="categorySelector" property="category" onchange="selectGroupManager.changeCategory(this.value);">
                                    <c:forEach var="helper" items="${helpers}">
                                        <c:set var="helperCategory" value="${helper.category}"/>
                                        <html:option value="${helperCategory}" key="label.scheme.category.${helperCategory}" bundle="schemesBundle"/>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="title">
                                <bean:message key="scheme.access.form.edit.fields.name" bundle="schemesBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="name" size="60"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="scheme.access.form.edit.fields.access" bundle="schemesBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:checkbox styleId="CAAdminSchemeSelector" property="field(CAAdminScheme)" disabled="${not form.allowEditCASchemes}" onclick="refreshAllowCAOrVSP();"/>
                                <label for="CAAdminSchemeSelector"><bean:message key="scheme.access.form.edit.fields.access.ca" bundle="schemesBundle"/></label><br/>
                                <html:checkbox styleId="VSPEmployeeSchemeSelector" property="field(VSPEmployeeScheme)" disabled="${form.vspEmployee or form.allowEditCASchemes}" onclick="refreshAllowCAOrVSP();"/>
                                <label for="VSPEmployeeSchemeSelector"><bean:message key="scheme.access.form.edit.fields.access.vsp" bundle="schemesBundle"/></label>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <script type="text/javascript">
                        function refreshAllowCAOrVSP()
                        {
                            <c:if test="${form.allowEditCASchemes and not form.vspEmployee}">
                                var caEmployee = $('#CAAdminSchemeSelector');
                                var vspEmployee = $('#VSPEmployeeSchemeSelector');

                                if (caEmployee.is(':checked'))
                                    vspEmployee.attr('disabled', 'disabled');
                                else
                                    vspEmployee.removeAttr('disabled');

                                if (vspEmployee.is(':checked'))
                                    caEmployee.attr('disabled', 'disabled');
                                else
                                    caEmployee.removeAttr('disabled');
                            </c:if>
                        }

                        function checkFillName()
                        {
                            return getElementValue("name") !="" ? true : alert('<bean:message key="scheme.access.form.edit.message.name.empty" bundle="schemesBundle"/>');
                        }
                    </script>

                    <jsp:include page="editServicesGroupsInformationTable.jsp"/>

                </tiles:put>

            </tiles:insert>

            <div class="buttonsArea">
                <div class="float backButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="scheme.access.form.edit.button.back"/>
                        <tiles:put name="commandHelpKey" value="scheme.access.form.edit.button.back.help"/>
                        <tiles:put name="bundle"         value="schemesBundle"/>
                        <tiles:put name="viewType"       value="blueGrayLink"/>
                        <tiles:put name="action"         value="/schemes/list"/>
                    </tiles:insert>
                </div>
                <div class="floatRight">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="scheme.access.form.edit.button.cancel"/>
                        <tiles:put name="commandHelpKey" value="scheme.access.form.edit.button.cancel.help"/>
                        <tiles:put name="bundle"         value="schemesBundle"/>
                        <c:choose>
                            <c:when test="${not empty form.id}">
                                <tiles:put name="action" value="/schemes/employee/edit?id=${form.id}"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="action" value="/schemes/employee/edit?scope=${form.scope}"/>
                            </c:otherwise>
                        </c:choose>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="schemesBundle"/>
                        <tiles:put name="validationFunction" value="checkFillName();"/>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>