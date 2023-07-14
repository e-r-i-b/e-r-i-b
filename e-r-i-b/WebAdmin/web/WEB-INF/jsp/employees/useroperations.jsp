<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/employees/access" onsubmit="return setEmptyAction(event);">
	<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="employee" scope="request" value="${form.employee}"/>
    <c:set var="subform"       value="${form.employeeAccess}"/>
    <c:set var="useExternalId" value="${phiz:isMultiblockMode()}"/>
    <c:set var="helpers"       value="${subform.helpers}"           scope="request"/>
    <c:set var="readOnly"      value="${subform.denyCustomRights}"  scope="request"/>
    <c:set var="policy"        value="${subform.policy}"/>
    <c:set var="accessType"    value="${policy.accessType}"/>
    <c:set var="helpers"       value="${subform.helpers}"/>
    <c:set var="namePrefix"    value="${accessType}Access"/>
    <c:set var="idPrefix"      value="${accessType}"/>
    <c:set var="currentSchemeCategory" scope="request">${subform.category}</c:set>

    <tiles:insert definition="employeesEdit">
        <tiles:put name="submenu" type="string" value="Access"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.closeResources.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="action" value="/employees/list"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="pageTitle" type="string"><bean:message key="edit.operations.title" bundle="employeesBundle"/></tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function validateManagerInfo()
                {
                    if($('[id$=_A_chk]:not(:disabled):checked').length > 0)
                    {
                        if(!confirm("Если Вы назначите данному сотруднику права администратора, то информация о персональном менеджере будет удалена."))
                        {
                            resetForm(event);
                            return false;
                        }
                    }
                    return true;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="userOperations"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="AssignEmployeeAccessOperation">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.saveOperation.help"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <c:if test="${not empty employee && (not empty employee.managerId ||
                                      not empty employee.managerEMail || not empty employee.managerLeadEMail ||
                                      not empty employee.managerPhone)}">
                            <tiles:put name="validationFunction" value="validateManagerInfo();"/>
                        </c:if>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        /**
                         * В массиве хранятся схемы с прикрепленными к ним сервисами
                         */
                        var schemes = new Array();
                        /**
                         * Инициализирует список сервисов для каждой схемы
                         * @param schemeId идентификатор схемы прав
                         * @param serviceId идентификатор сервиса
                         */
                        function initServicesForScheme(schemeId, serviceKey)
                        {
                            var found = getSchemeInfo(schemeId);
                            if(found == null)
                            {
                                found = new Object();
                                found.schemeId = schemeId;
                                found.services = new Array();
                                schemes[schemes.length] = found;
                            }
                            found.services[found.services.length] = serviceKey;
                        }

                        function getSchemeInfo(schemeId)
                        {
                            for (var i=0; i<schemes.length; i++)
                            {
                                var currentScheme = schemes[i];
                                if (currentScheme.schemeId == schemeId)
                                    return currentScheme;
                            }
                            return null;
                        }

                        function getServices(schemeId)
                        {
                            var schemeInfo = getSchemeInfo(schemeId);
                            if (schemeInfo == null)
                                return new Array();
                            return schemeInfo.services;
                        }

                        function updateSelectView(select)
                        {
                            select.parent().find('[id^=customSelect] .selectData')[0].style.color = select.find('option:selected').css('color');
                        }

                        function onChangeScheme(select)
                        {
                            var currentSelect = $(select);
                            var selectedOption = currentSelect.find('option:selected');
                            var selectedSchemeId = selectedOption.attr('id');
                            var selectedSchemeIdLength = selectedSchemeId.length;
                            var newCategory = selectedSchemeId.substring(selectedSchemeIdLength-1, selectedSchemeIdLength);

                            $('[name=${namePrefix}.category]').val(newCategory);

                            selectGroupManager.changeCategory(newCategory);

                            updateSelectView(currentSelect);

                            if (selectedOption.val() == 'personal' + newCategory)
                                return;

                            selectGroupManager.selectServices(getServices(currentSelect.val()));
                        }

                        <c:if test="${not readOnly}">
                            function selectPersonal(category)
                            {
                                var sel = $('[name=${namePrefix}.accessSchemeId]');
                                sel.val('personal' + category);
                                updateSelectView(sel);
                            }

                            //для подсветки индивидуальных прав
                            doOnLoad(function()
                            {
                                updateSelectView($('[name=${namePrefix}.accessSchemeId]'));
                                var oldSelectGroup = selectGroupManager.selectGroup;

                                selectGroupManager.selectGroup = function(id, mode, activate)
                                {
                                    selectPersonal($('[name=${namePrefix}.category]').val());
                                    return oldSelectGroup(id, mode, activate);
                                }
                            });
                        </c:if>
                    </script>
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="tblSettingsBeforeInf">
                                <div class="form-row">
                                    <div class="paymentLabel">
                                        <bean:message key="label.scheme" bundle="personsBundle"/>
                                    </div>
                                    <div class="paymentValue">
                                        <html:hidden property="${namePrefix}.category" styleId="categorySelector"/>
                                        <html:select property="${namePrefix}.accessSchemeId" onchange="onChangeScheme(this)">
                                            <html:option value="" style="color:gray">
                                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                                            </html:option>
                                            <c:forEach var="helper" items="${helpers}">
                                                <c:set var="helperCategory" value="${helper.category}"/>
                                                <optgroup label="<bean:message key='label.scheme.category.${helperCategory}' bundle='schemesBundle'/>">
                                                    <c:if test="${not readOnly}">
                                                        <html:option value="personal${helperCategory}" style="color:red;" styleId="${idPrefix}_personal_${helperCategory}">
                                                            Индивидуальные права
                                                        </html:option>
                                                    </c:if>
                                                    <c:set var="accessSchemes" value="${helper.schemes}"/>
                                                    <c:forEach var="scheme" items="${accessSchemes}">
                                                        <c:choose>
                                                            <c:when test="${useExternalId}">
                                                                <c:set var="itemId" value="${scheme.externalId}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="itemId" value="${scheme.id}"/>
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <script type="text/javascript">
                                                            <c:forEach var="service" items="${scheme.services}">
                                                                initServicesForScheme('${itemId}', '${service.key}');
                                                            </c:forEach>
                                                        </script>

                                                        <html:option value="${itemId}" style="color:black;" styleId="${idPrefix}_${itemId}_${helperCategory}">
                                                            <c:out value="${scheme.name}"/>
                                                        </html:option>
                                                    </c:forEach>
                                                </optgroup>
                                            </c:forEach>
                                        </html:select>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </tiles:put>
            </tiles:insert>
            <jsp:include page="../schemes/editServicesGroupsInformationTable.jsp"/>
        </tiles:put>
    </tiles:insert>
</html:form>
