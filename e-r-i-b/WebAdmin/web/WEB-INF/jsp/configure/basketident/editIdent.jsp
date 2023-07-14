<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/clientProfile/ident/editIdent"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="IdentConfig"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="configureBundle" key="settings.clientident.editidenttitle"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext,'/clientProfile/ident/editAttrib.do')}"/>
            <c:url var="editFormulaUrl" value="/clientProfile/ident/editIdent/editFormula.do">
                <c:param name="identId" value="${form.id}"/>
            </c:url>
            <input type="hidden" name="providerId" value="${form.providerId}" id="providerIdHidden">
            <script type="text/javascript">
                function sendData(add)
                {
                    if (!add) {
                        checkIfOneItem("selectedIds");
                        if (!checkSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>") || !checkOneSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>"))
                             return;
                    }

                    var selectedInput = $("[name=selectedIds]:checked");
                    location.href = '${actionUrl}?identId=${form.id}&' + (add ? ('add=true') : ('id='+selectedInput.val()));
                }

                function removeIdent()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>") || !checkOneSelection("selectedIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>"))
                         return;

                    var selectedInput = $("[name=selectedIds]:checked");
                    ajaxQuery('identId=${form.id}&id='+selectedInput.val() + "&remove=true", '${actionUrl}',
                            function () {location.reload();});
                }

                function editFormula()
                {
                    checkIfOneItem("selectedProviderIds");
                    if (!checkSelection("selectedProviderIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>") || !checkOneSelection("selectedProviderIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>"))
                         return;

                    var selectedInput = $("[name=selectedProviderIds]:checked");
                    window.location = '${editFormulaUrl}&id=' + selectedInput.val();
                }

                function openProvidDictionary()
                {
                    openProvidersDictionaryForEmployee(addFormula, 'B');
                }

                function addFormula(resource)
                {
                    window.location = '${editFormulaUrl}&externalId=' + resource['externalId'];
                }

                function removeFormula()
                {
                    checkIfOneItem("selectedProviderIds");
                    if (!checkSelection("selectedProviderIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>") || !checkOneSelection("selectedProviderIds", "<bean:message bundle="configureBundle" key="settings.clientident.selectonenode"/>"))
                         return;

                    var selectedInput = $("[name=selectedProviderIds]:checked");
                    $('#providerIdHidden').val(selectedInput.val());

                    var button = createCommandButton('button.removeFormula', 'button.removeFormula');
                    button.click();
                }
            </script>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.name"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="name" value="${form.name}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.systemid"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(systemId)"/>
                </tiles:put>
            </tiles:insert>

            <html:hidden property="id" value="${form.id}"/>
            <c:if test="${not empty form.id}">
                <div class="clear minHeightClear"></div>
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clientident.attribs"/></tiles:put>
                    <tiles:put name="showButtons" value="true"/>
                    <tiles:put name="buttons">
                        <div class="buttonMenu floatRight">
                            <div class="floatRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                                    <tiles:put name="bundle"         value="configureBundle"/>
                                    <tiles:put name="onclick"        value="sendData(true);"/>
                                    <tiles:put name="viewType"       value="blueBorder"/>
                                </tiles:insert>
                                <c:if test="${not empty form.attributes}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.edit"/>
                                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                        <tiles:put name="bundle"         value="configureBundle"/>
                                        <tiles:put name="onclick"        value="sendData(false);"/>
                                        <tiles:put name="viewType"       value="blueBorder"/>
                                    </tiles:insert>

                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.delete"/>
                                        <tiles:put name="commandHelpKey" value="button.delete.help"/>
                                        <tiles:put name="bundle"         value="configureBundle"/>
                                        <tiles:put name="onclick"        value="removeIdent();"/>
                                        <tiles:put name="viewType"       value="blueBorder"/>
                                    </tiles:insert>
                            </c:if>
                            </div>
                        </div>
                    </tiles:put>
                    <tiles:put name="id" value="IdentListTable"/>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="attributes">
                            <sl:collectionParam id="selectName"     value="selectedIds"/>
                            <sl:collectionParam id="selectType"     value="checkbox"    condition="true"/>
                            <sl:collectionParam id="selectProperty" value="first.id"/>
                            <sl:collectionParam id="onRowDblClick"  value="sendData(false);" />

                            <c:set var="identName"><bean:message bundle="configureBundle" key="settings.clientident.name"/></c:set>
                            <c:set var="systemId"><bean:message bundle="configureBundle" key="settings.clientident.systemid"/></c:set>
                            <c:set var="type"><bean:message bundle="configureBundle" key="settings.clientident.type"/></c:set>
                            <c:set var="regexp"><bean:message bundle="configureBundle" key="settings.clientident.regexp"/></c:set>
                            <c:set var="mandatory"><bean:message bundle="configureBundle" key="settings.clientident.mandatory"/></c:set>
                            <sl:collectionItem hidden="true" value="${listElement.first.id}"/>
                            <sl:collectionItem title="${identName}" value="${listElement.first.name}"/>
                            <sl:collectionItem title="${systemId}" value="${listElement.second}"/>
                            <sl:collectionItem title="${type}">
                                <c:choose>
                                    <c:when test="${listElement.first.dataType == 'TEXT'}">Текст</c:when>
                                    <c:when test="${listElement.first.dataType == 'DATE'}">Дата</c:when>
                                    <c:when test="${listElement.first.dataType == 'NUMBER'}">Целое</c:when>
                                    <c:when test="${listElement.first.dataType == 'MONEY'}">Финансовое</c:when>
                                </c:choose>
                            </sl:collectionItem>
                            <sl:collectionItem title="${regexp}" value="${listElement.first.regexp}"/>
                            <sl:collectionItem title="${mandatory}">
                                <c:choose>
                                    <c:when test="${listElement.first.mandatory}">+</c:when>
                                    <c:otherwise>&minus;</c:otherwise>
                                </c:choose>
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                </tiles:insert>
            </c:if>
            <div class="pmntFormMainButton floatRight">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"         value="imageSettingsBundle"/>
                    <tiles:put name="isDefault"      value="true"/>
                </tiles:insert>
            </div>
            <div style="margin-top: 70px" class="clear">
                <c:if test="${not empty form.id}">
                    <tiles:insert definition="tableTemplate" flush="false">
                        <tiles:put name="text"><bean:message bundle="configureBundle" key="settings.clientident.identities.links.table.name"/></tiles:put>
                        <tiles:put name="showButtons" value="true"/>
                        <tiles:put name="buttons">
                            <div class="buttonMenu floatRight">
                                <div class="floatRight">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add.help"/>
                                        <tiles:put name="bundle"         value="configureBundle"/>
                                        <tiles:put name="viewType"       value="blueBorder"/>
                                        <tiles:put name="onclick"        value="openProvidDictionary();"/>
                                    </tiles:insert>
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.edit"/>
                                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                        <tiles:put name="bundle"         value="configureBundle"/>
                                        <tiles:put name="viewType"       value="blueBorder"/>
                                        <tiles:put name="onclick"        value="editFormula();"/>
                                    </tiles:insert>
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.delete"/>
                                        <tiles:put name="commandHelpKey" value="button.delete.help"/>
                                        <tiles:put name="bundle"         value="configureBundle"/>
                                        <tiles:put name="viewType"       value="blueBorder"/>
                                        <tiles:put name="onclick"        value="removeFormula();"/>
                                    </tiles:insert>
                                </div>
                            </div>
                        </tiles:put>
                        <tiles:put name="id" value="IdentityLinksTable"/>
                        <tiles:put name="grid">
                            <c:if test="${not empty form.serviceProviders}">
                                <sl:collection id="provider" model="list" property="serviceProviders">
                                    <c:choose>
                                        <c:when test="${empty provider.id}">
                                            <tr class="tblInfHeader">
                                                <th class="titleTable" style="width:20px">
                                                    <input type="checkbox" name="isSelectAll" onclick="switchSelection('isSelectAll','selectedProviderIds');">
                                                </th>
                                                <th class="titleTable">
                                                    <bean:message bundle="configureBundle" key="settings.clientident.identities.links.table.label.provider"/>
                                                </th>
                                                <th class="titleTable">
                                                    <bean:message bundle="configureBundle" key="settings.clientident.identities.links.table.label.inn"/>
                                                </th>
                                                <th class="titleTable">
                                                    <bean:message bundle="configureBundle" key="settings.clientident.identities.links.table.label.account"/>
                                                </th>
                                                <th class="titleTable">
                                                    <bean:message bundle="configureBundle" key="settings.clientident.identities.links.table.label.service"/>
                                                </th>
                                                <th class="titleTable listItem align-center">
                                                    <img src="${imagePath}/clip.gif" title="Прикреплена графическая подсказка"/>
                                                </th>
                                                <th class="titleTable">
                                                    <bean:message bundle="configureBundle" key="settings.clientident.identities.links.table.label.state"/>
                                                </th>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                                <td>
                                                    <input type="checkbox" name="selectedProviderIds" value="${provider.id}">
                                                </td>
                                                <td>
                                                    <c:out value="${provider.name}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${provider.INN}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${provider.account}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${provider.nameService}"/>
                                                </td>
                                                <td>
                                                    <c:if test="${not empty provider.imageHelpId}">
                                                        <img src="${imagePath}/clip.gif" title="Прикреплена графическая подсказка"/>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:set var="state" value="${provider.state}"/>
                                                    <c:if test="${not empty state}">
                                                        <div id="active_${provider.id}" >
                                                            <bean:message key="label.provider.state.${state}" bundle="providerBundle"/>
                                                        </div>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="7">
                                                    <c:set var="identAttributes" value="${form.basketIndetifierType.attributes}"/>
                                                    <c:forEach var="formulaWithName" items="${form.formulas[provider.id]}">
                                                        <c:set var="formula" value="${formulaWithName[1]}"/>
                                                        <c:set var="i" value="${formula.id}"/>
                                                        <c:set var="name" value="${formulaWithName[0]}"/>
                                                        <c:set var="lastAttribute" value=""/>
                                                        <div>
                                                            <nobr>
                                                                <input type="text" value="${name}" disabled size="15">
                                                                =
                                                                <c:forEach var="attribute" items="${formula.attributes}">
                                                                    <c:set var="attributeSystemId" value="${attribute.systemId}"/>
                                                                    <c:choose>
                                                                        <c:when test="${!attribute.last}">
                                                                            <input type="text" value="${attribute.value}" disabled size="15">
                                                                            +
                                                                            <c:choose>
                                                                                <c:when test="${empty attributeSystemId}">
                                                                                    <input type="text" value="${attributeSystemId}" disabled size="15">
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <input type="text" value="${identAttributes[attributeSystemId].name}" disabled size="15">
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                            +
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <c:set var="lastAttribute" value="${attribute.value}"/>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                                <input type="text" value="${lastAttribute}" disabled size="15">
                                                            </nobr>
                                                        </div>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </sl:collection>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>

