<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>


<c:if test="${empty useExternalId}">
    <c:set var="useExternalId" value="${false}"/>
</c:if>

<c:set var="policy"             value="${subform.policy}"/>
<c:set var="accessType"         value="${policy.accessType}"/>
<c:set var="helpers"            value="${subform.helpers}"/>
<c:set var="currentCategory"    value="${subform.category}"/>
<c:set var="helpersCount"       value="${fn:length(helpers)}"/>
<c:set var="namePrefix"         value="${accessType}Access"/>
<c:set var="idPrefix"           value="${accessType}"/>
<c:set var="isCAAdmin"          value="${phiz:isCAAdmin()}"/>

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
    function initServicesForScheme(schemeId, serviceId)
    {
        var found = getSchemeInfo(schemeId);
        if(found == null)
        {
            found = new Object();
            found.schemeId = schemeId;
            found.services = new Array();
            schemes[schemes.length] = found;
        }
        found.services[found.services.length] = serviceId;
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
        select.parent().find('[id^=customSelect]')[0].style.color = select.find('option:selected').css('color');
    }

    function onChangeScheme(select)
    {
        var currentSelect = $(select);
        var selectedOption = currentSelect.find('option:selected');
        var selectedSchemeId = selectedOption.attr('id');
        var selectedSchemeIdLength = selectedSchemeId.length;
        var newCategory = selectedSchemeId.substring(selectedSchemeIdLength-1, selectedSchemeIdLength);

        $('[name=${namePrefix}.category]').val(newCategory);

        $('[id$=_ServicesTable]:not([id^=${accessType}_' + newCategory + ']) [id$=_chk]').attr('disabled', 'disabled');
        $('[id$=_ServicesTable]:not([id^=${accessType}_' + newCategory + ']) [name$=_${accessType}_isSelectAll]').removeAttr('checked');
        $('[id$=_ServicesTable][id^=${accessType}_' + newCategory + '] .listItem [id$=_' + newCategory + '_chk]').removeAttr('disabled');
        $('[id$=_ServicesTable]:not([id^=${accessType}_' + newCategory + ']) [id$=_chk]').removeAttr('checked');
        $('[id$=_ServicesTable]:not([id^=${accessType}_' + newCategory + ']) [id$=_chk_lbl]').text('запрещен');

        updateSelectView(currentSelect);

        if (selectedOption.val() == 'personal' + newCategory)
            return;

        var allCheckbox = $('[id$=_ServicesTable][id^=${accessType}_' + newCategory + '] [id$=_chk]');
        allCheckbox.removeAttr('checked');
        allCheckbox.parent().find('label').text('запрещен');
        var services = getServices(selectedOption.val());
        for(var i=0; i < services.length; i++)
        {
            var service = $('[id=${accessType}_srv' + services[i] + '_' + newCategory + '_chk]');
            service.attr('checked', 'checked');
            service.parent().find('label').text('разрешен');
        }
    }

    function setFullAccess(category)
    {
        selectPersonal(category);
        if ($('[name=' + category + '_${accessType}_isSelectAll]').is(':checked'))
        {
            $('[id$=_ServicesTable][id^=${accessType}_' + category + '] .listItem [id$=_chk]').attr('checked', 'checked');
            $('[id$=_ServicesTable][id^=${accessType}_' + category + '] .listItem [id$=_chk_lbl]').text('разрешен');
        }
        else
        {
            $('[id$=_ServicesTable][id^=${accessType}_' + category + '] [id$=_chk]').removeAttr('checked');
            $('[id$=_ServicesTable][id^=${accessType}_' + category + '] .listItem [id$=_chk_lbl]').text('запрещен');
        }
    }

    function setPersonal(serviceSelector)
    {
        var selectedServiceId = $(serviceSelector).attr('id');
        $(serviceSelector).parent().find('label').text($(serviceSelector).is(':checked')? 'разрешен': 'запрещен');
        var selectedServiceIdLength = selectedServiceId.length;
        var newCategory = selectedServiceId.substring(selectedServiceIdLength-5, selectedServiceIdLength-4);
        selectPersonal(newCategory);
    }

    function selectPersonal(category)
    {
        var sel = getElement('${namePrefix}.accessSchemeId');
        sel.value = 'personal' + category;
        sel.onchange();
    }
    <c:if test="${not subform.denyCustomRights}">
        //для подсветки индивидуальных прав
        doOnLoad(function(){updateSelectView($('[name=${namePrefix}.accessSchemeId]'));});
    </c:if>
</script>

<table cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tblSettingsBeforeInf">
            <c:set var="isDisabledAccess" value="${false}"/>
            <c:if test="${accessType != 'employee'}">
                <c:set var="isDisabledAccess" value="${not subform.enabled}"/>
                <script type="text/javascript">
                    function onAccessChanged()
                    {
                        if ($('#${idPrefix}_enabled').is(':checked'))
                            $('[name*=${idPrefix}]:not(#${idPrefix}_enabled)').removeAttr('disabled');
                        else
                            $('[name*=${idPrefix}]:not(#${idPrefix}_enabled)').attr('disabled', 'disabled');
                    }
                </script>
                <div class="form-row">
                    <div class="paymentLabel">
                        <b>Интернет-доступ</b>
                    </div>
                    <div class="paymentValue"></div>
                    <div class="clear"></div>
                </div>

                <div class="form-row">
                    <div class="paymentLabel">
                        <c:out value="${policy.description}"/>
                    </div>
                    <div class="paymentValue">
                        <div class="paymentInputDiv">
                            <html:checkbox styleId="${idPrefix}_enabled" property="${namePrefix}.enabled" onclick="onAccessChanged()" disabled="${not isShowSaves}"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="form-row">
                    <div class="paymentLabel">
                        Подтверждение входа
                    </div>
                    <div class="paymentValue">
                        <c:set var="choice" value="${policy.authenticationChoice}"/>
                        <c:if test="${not (empty choice)}">
                            <html:select property="${namePrefix}.properties(${choice.property})" disabled="${isDisabledAccess or not isShowSaves}">
                                <c:forEach var="option" items="${choice.options}">
                                    <html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
                                </c:forEach>
                            </html:select>
                        </c:if>
                    </div>
                    <div class="clear"></div>
                </div>

                <div class="form-row">
                    <div class="paymentLabel">
                        Подтверждение операций
                    </div>
                    <div class="paymentValue">
                        <c:set var="choice" value="${policy.confirmationChoice}"/>
                        <c:if test="${not (empty choice)}">
                            <html:select property="${namePrefix}.properties(${choice.property})" disabled="${isDisabledAccess or not isShowSaves}">
                                <c:forEach var="option" items="${choice.options}">
                                    <html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
                                </c:forEach>
                            </html:select>
                        </c:if>
                    </div>
                    <div class="clear"></div>
                </div>

                <c:if test="${phiz:impliesOperation('SetUserRegistrationModeOperation', 'PersonAccessManagement')}">
                    <div class="form-row">
                        <div class="paymentLabel">
                            Режим самостоятельной регистрации
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <html:select property="userRegistrationMode" disabled="${isDisabledAccess or not isShowSaves}">
                                    <html:option value="DEFAULT">По умолчанию (не задано)</html:option>
                                    <html:option value="SOFT">Мягкий режим</html:option>
                                    <html:option value="HARD">Жесткий режим</html:option>
                                </html:select>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </c:if>
            </c:if>

            <div class="form-row">
                <div class="paymentLabel">
                <bean:message key="label.scheme" bundle="personsBundle"/>
            </div>
            <div class="paymentValue">
                <html:hidden property="${namePrefix}.category"/>
                <html:select property="${namePrefix}.accessSchemeId" onchange="onChangeScheme(this)" disabled="${isDisabledAccess}">
                    <html:option value="" style="color:gray">
                        <bean:message key="label.noSchemes" bundle="personsBundle"/>
                    </html:option>
                    <c:forEach var="helper" items="${helpers}">
                        <c:set var="helperCategory" value="${helper.category}"/>
                        <c:if test="${helpersCount > 1}">
                            <optgroup label="<bean:message key='label.scheme.category.${helperCategory}' bundle='schemesBundle'/>">
                        </c:if>
                        <c:if test="${not subform.denyCustomRights}">
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
                                initServicesForScheme('${itemId}', '${service.id}');
                                </c:forEach>
                            </script>

                            <html:option value="${itemId}" style="color:black;" styleId="${idPrefix}_${itemId}_${helperCategory}">
                                <c:out value="${scheme.name}"/>
                            </html:option>
                        </c:forEach>
                        <c:if test="${helpersCount > 1}">
                            </optgroup>
                        </c:if>
                    </c:forEach>
                </html:select>
            </div>
                <div class="clear"></div>
            </div>
        </td>
    </tr>
    <c:forEach var="helper" items="${helpers}">
        <c:set var="services" value="${phiz:sort(helper.services, subform.servicesComparator)}"/>
        <c:set var="helperCategory" value="${helper.category}"/>
        <c:set var="operationsByService" value="${subform.operationsByServiceMap}"/>

        <tr>
            <td class="needBorder">
                <table cellpadding="0" cellspacing="0" id="${idPrefix}_${helperCategory}_ServicesTable" class="standartTable" width="100%">
                    <tr class="tblInfHeader">
                        <td width="93%" height="20px">Услуга</td>
                        <td width="85px" height="20px" nowrap="true" align="left">
                            <input name="${helperCategory}_${accessType}_isSelectAll"
                                   value="on"
                                   style="border:medium none;"
                                   type="checkbox"<c:if test="${isDisabledAccess or subform.denyCustomRights}"> disabled</c:if>
                                   onclick="setFullAccess('${helperCategory}')">
                            Доступ
                        </td>
                    </tr>
                    <c:forEach var="service" items="${services}" varStatus="li">
                        <c:set var="lineNumber"      value="${li.count}"/>
                        <c:set var="checkboxId"      value="${idPrefix}_srv${service.id}_${helperCategory}_chk"/>
                        <c:set var="operationsRowId" value="${idPrefix}_srv_${service.id}_or"/>
                        <c:set var="isCAAdminService"  value="${service.caAdminService}"/>
                        <c:set var="isDisabledForUserService" value="${(isCAAdminService && !isCAAdmin)}"/>

                        <tr class="listLine${lineNumber % 2}">
                            <td class="listItem"  valign="top">
                                <c:set var="listItemDisable" value="${isDisabledForUserService ? 'listItemDisabled' : ''}"/>
                                <a href="javascript:hideOrShow('${operationsRowId}')" title="Показать/скрыть операции" class="${listItemDisable}">
                                    <c:out value="${service.name}"/>
                                </a>
                            </td>
                            <c:set var="listItemStyle" value="${isDisabledForUserService ? 'listItemDisabled' : 'listItem'}"/>
                            <c:if test="${isDisabledForUserService}">
                                <c:set var="property" value="${namePrefix}.caadminServices" />
                                <%--если checkbox задизайблен, то он не сабмитится даже если он checked=true--%>
                                <html:hidden property="${namePrefix}.disabledServices" value="${service.id}"/>
                            </c:if>

                            <td class="${listItemStyle}" >
                                <c:set var='additionalAttributes'/>
                                <c:set var="labelValue" value="запрещен"/>
                                <c:set var='onclick' value=' onclick="setPersonal(this);"'/>
                                <c:if test="${isDisabledAccess or isDisabledForUserService or currentCategory ne helperCategory}">
                                    <c:set var='additionalAttributes' value='${additionalAttributes} disabled="disabled"'/>
                                </c:if>
                                <c:if test="${subform.denyCustomRights}">
                                    <c:set var='onclick' value=' onclick="return false;"'/>
                                </c:if>
                                <c:if test="${phiz:containsInArray(subform.selectedServices, service.id)}">
                                    <c:set var='additionalAttributes' value='${additionalAttributes} checked="checked"'/>
                                    <c:set var="labelValue" value="разрешен"/>
                                </c:if>
                                <input type="checkbox" id="${checkboxId}" value="${service.id}" name="${namePrefix}.selectedServices"${additionalAttributes}${onclick}/>
                                <label id="${checkboxId}_lbl" for="${checkboxId}">${labelValue}</label>
                            </td>
                        </tr>
                        <tr id="${operationsRowId}" style="display: none;">
                            <td colspan="2">
                                <table cellspacing="0" cellpadding="0" width="100%" class="noBorder tblInsideTd">
                                    <c:forEach var="operation" items="${operationsByService[service]}">
                                        <tr class="listLine${lineNumber % 2}">
                                            <td class="listItem" valign="top" width="100%" style="padding-left:10px;">
                                                <bean:write name="operation" property="name"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>