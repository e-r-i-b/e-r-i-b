<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/clientProfile/ident/editIdent/editFormula" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="attributes" value="${form.attributes}"/>
    <c:set var="fieldDescriptions" value="${form.fieldDescriptions}"/>
    <c:set var="identId" value="${form.identId}"/>
    <c:set var="formulas" value="${form.formulas}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <c:url var="cancelUrl" value="/clientProfile/ident/editIdent.do">
        <c:param name="id" value="${identId}"/>
    </c:url>

    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="IdentConfig"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="configureBundle" key="settings.clientident.editFormula"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <input type="hidden" name="id" value="${form.id}">
            <input type="hidden" name="identId" value="${form.identId}">
            <input type="hidden" name="fieldIds" id="formulaIdsId">
            <input type="hidden" name="newFieldIds" id="newFormulaIdsId">

            <script type="text/javascript">
                var rowId = 0;
                var previousSelectValue;
                var maxFormulasSize = ${phiz:size(fieldDescriptions)};

                function addFormula()
                {
                    addFormulaBody();
                    refreshDisplayButtons();
                }

                function refreshDisplayButtons()
                {
                    var formulasSize = $('select[id^=providerSelect]').size();

                    var addButton = document.getElementById("addButtonId");
                    addButton.style.display = formulasSize == maxFormulasSize ? 'none' : 'block';

                    var deleteButton = document.getElementById("deleteButtonId");
                    deleteButton.style.display = formulasSize == 0 ? 'none' : 'block';

                    var saveButton = document.getElementById("saveButtonId");
                    saveButton.style.display = formulasSize == 0 ? 'none' : 'block';
                }

                function addFormulaBody()
                {
                    var table = document.getElementById("formulaTable");
                    if (table.getElementsByTagName("TBODY").length == 0)
                    {
                        table.appendChild(document.createElement("TBODY"));
                    }

                    var tBody = table.getElementsByTagName("TBODY")[0];

                    var newRow = document.createElement("TR");
                    var newRowId = document.createAttribute("id");
                    newRowId.value = "newFormulaTr_" + rowId;
                    newRow.setAttributeNode(newRowId);

                    var newTdCheckBox = document.createElement("TD");
                    newTdCheckBox.innerHTML = addCheckBox(rowId);
                    newRow.appendChild(newTdCheckBox);

                    var newTdProviderField = document.createElement("TD");
                    newTdProviderField.appendChild(addProviderField(rowId));
                    newRow.appendChild(newTdProviderField);

                    var newTdEq = document.createElement("TD");
                    newTdEq.innerHTML = '=';
                    newRow.appendChild(newTdEq);

                    addAttributes(newRow, rowId);

                    tBody.appendChild(newRow);
                    rowId++;

                    var selectCore = new SelectCore();
                    selectCore.init();
                }

                function addCheckBox(id)
                {
                    return '<input type="checkbox" name="newSelectedIds" id="formula_' + id + '" value="' + id + '">';
                }

                function addProviderField(id)
                {
                    var propertySelect = document.createElement("select");

                    var selectName = document.createAttribute("name");
                    selectName.value = 'field(new_providerField_' + id + ')';
                    propertySelect.setAttributeNode(selectName);

                    var selectId = document.createAttribute("id");
                    selectId.value = 'providerSelect_' + id + 'New';
                    propertySelect.setAttributeNode(selectId);

                    var onfocus = document.createAttribute("onfocus");
                    onfocus.value = 'savePreviousSelectValue(this)';
                    propertySelect.setAttributeNode(onfocus);

                    var onchange = document.createAttribute("onchange");
                    onchange.value = 'rebuildSelects(this)';
                    propertySelect.setAttributeNode(onchange);

                    var options = "";
                    var selected = false;

                    <c:forEach var="description" items="${fieldDescriptions}" varStatus="status">
                        <c:set var="index" value="${status.count - 1}"/>

                        options = $('select[id^=providerSelect_] option[value=${description.externalId}]:selected');
                        selected = options.size() == 0 && !selected;

                        if (selected)
                        {
                            $('select[id^=providerSelect]:not(select[id=' + selectId.value + ']) option[value=${description.externalId}]').hide();
                        }

                        propertySelect.options[${index}] = new Option("${description.name}", "${description.externalId}", false, selected);
                        propertySelect.options[${index}].style.display = options.size() != 0 ? 'none' : 'block';

                    </c:forEach>

                    return propertySelect;
                }

                function addAttributes(row, id)
                {
                    <c:forEach var="attribute" items="${attributes}" varStatus="status">
                        <c:set var="index" value="${status.count - 1}"/>

                        row.appendChild(getValueTd(id, '${index}'));
                        row.appendChild(getAddSymbol());
                        row.appendChild(getIdentTd(id, '${index}'));
                        row.appendChild(getAddSymbol());

                    </c:forEach>

                    row.appendChild(getLastTd(id));
                }

                function getIdentTd(id, index)
                {
                    var identTd = document.createElement("TD");
                    var identSelect = document.createElement("select");

                    var selectName = document.createAttribute("name");
                    selectName.value = 'field(new_identTypeField_' + index + '_' + id + ')';
                    identSelect.setAttributeNode(selectName);

                    var selectId = document.createAttribute("id");
                    selectId.value = 'identTypeSelect' + id + '_' + index;
                    identSelect.setAttributeNode(selectId);

                    var onfocus = document.createAttribute("onfocus");
                    onfocus.value = 'savePreviousSelectValue(this)';
                    identSelect.setAttributeNode(onfocus);

                    var onchange = document.createAttribute("onchange");
                    onchange.value = 'rebuildSelects(this)';
                    identSelect.setAttributeNode(onchange);

                    identSelect.options[0] = new Option("\u00A0", "");
                    <c:forEach var="attribute" items="${attributes}" varStatus="status">
                        <c:set var="attributeIndex" value="${status.count}"/>
                        identSelect.options[${attributeIndex}] = new Option("${attribute.name}", "${attribute.systemId}");
                    </c:forEach>

                    identTd.appendChild(identSelect);
                    return identTd;
                }

                function getValueTd(id, index)
                {
                    var valueTd = document.createElement("TD");
                    var valueField = document.createElement("input");

                    var valueFieldName = document.createAttribute("name");
                    valueFieldName.value = 'field(new_valueField_' + index + '_' + id + ')';

                    var typeField = document.createAttribute("type");
                    typeField.value = 'text';

                    var fieldSize = document.createAttribute("size");
                    fieldSize.value = '20';

                    valueField.setAttributeNode(valueFieldName);
                    valueField.setAttributeNode(fieldSize);
                    valueField.setAttributeNode(typeField);

                    valueTd.appendChild(valueField);
                    return valueTd;
                }

                function getLastTd(id)
                {
                    var lastTd = document.createElement("TD");
                    var lastField = document.createElement("input");

                    var lastFieldName = document.createAttribute("name");
                    lastFieldName.value = 'field(new_lastField_' + id + ')';

                    var typeField = document.createAttribute("type");
                    typeField.value = 'text';

                    var fieldSize = document.createAttribute("size");
                    fieldSize.value = '20';

                    lastField.setAttributeNode(lastFieldName);
                    lastField.setAttributeNode(fieldSize);
                    lastField.setAttributeNode(typeField);

                    lastTd.appendChild(lastField);
                    return lastTd;
                }

                function getAddSymbol()
                {
                    var newTdAdd = document.createElement("TD");
                    newTdAdd.innerHTML = '+';

                    return newTdAdd;
                }

                function removeFormula()
                {
                    removeFormulas("selectedIds", "formulaTr_", "");
                    removeFormulas("newSelectedIds", "newFormulaTr_", "New");
                    refreshDisplayButtons();
                }

                function removeFormulas(name, formulaTrName, postfix)
                {
                    var table = document.getElementById("formulaTable").getElementsByTagName("TBODY")[0];
                    var ids = $("[name=" + name + "]:checked");
                    var size = ids.length;

                    for (var i = 0; i < size; i++)
                    {
                        var idValue = ids[i].value;
                        var selectedOption = $('#providerSelect_' + idValue + postfix + ' :selected').val();
                        $('select[id^=providerSelect]:not(select[id=providerSelect_' + idValue + postfix + ']) option[value=' + selectedOption +']').show();

                        var row = document.getElementById(formulaTrName + idValue);
                        table.removeChild(row);
                    }
                }

                function cancel()
                {
                    window.location = '${cancelUrl}';
                }

                function savePreviousSelectValue(select)
                {
                    previousSelectValue = select.value;
                }

                function rebuildSelects(thisSelect, skipPrevious)
                {
                    var id = thisSelect.id;
                    var select = id.substr(0, id.indexOf('_'));
                    var hideOptionValue = $('#' + id + ' :selected').val();

                    if (hideOptionValue != '')
                    {
                        $('select[id^='+ select + ']:not(select[id=' + id + ']) option[value=' + hideOptionValue +']').hide();
                    }

                    if (!skipPrevious)
                    {
                        $('select[id^='+ select + ']:not(select[id=' + id + ']) option[value=' + previousSelectValue +']').show();
                    }
                }

                function init()
                {
                    $('select').each(function(){
                        rebuildSelects(this, true);
                    });

                    refreshDisplayButtons();
                }

                doOnLoad(function(){
                    init();
                });
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="showButtons" value="true"/>
                <tiles:put name="buttons">
                    <div class="buttonMenu floatRight">
                        <div class="floatRight">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="btnId"          value="addButtonId"/>
                                <tiles:put name="commandTextKey" value="button.add"/>
                                <tiles:put name="commandHelpKey" value="button.add.help"/>
                                <tiles:put name="bundle"         value="configureBundle"/>
                                <tiles:put name="viewType"       value="blueBorder"/>
                                <tiles:put name="onclick"        value="addFormula();"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="btnId"          value="deleteButtonId"/>
                                <tiles:put name="commandTextKey" value="button.delete"/>
                                <tiles:put name="commandHelpKey" value="button.delete.help"/>
                                <tiles:put name="bundle"         value="configureBundle"/>
                                <tiles:put name="viewType"       value="blueBorder"/>
                                <tiles:put name="onclick"        value="removeFormula()"/>
                            </tiles:insert>
                        </div>
                    </div>
                </tiles:put>
                <tiles:put name="id" value="IdentityLinksTable"/>
                <tiles:put name="data">
                    <sl:collection id="provider" model="list" property="serviceProviders">
                        <sl:collectionItem title="Наименование поставщика" property="name"/>
                        <sl:collectionItem title="ИНН"          property="INN"/>
                        <sl:collectionItem title="Счет"         property="account"/>
                        <sl:collectionItem title="Услуга"       property="nameService"/>
                        <sl:collectionItem styleClass="listItem align-center">
                            <sl:collectionItemParam id="title"><img src="${imagePath}/clip.gif" title="Прикреплена графическая подсказка"/></sl:collectionItemParam>
                            <sl:collectionItemParam id="value">
                                <c:if test="${not empty provider.imageHelpId}">
                                    <img src="${imagePath}/clip.gif" title="Прикреплена графическая подсказка"/>
                                </c:if>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="Статус">
                            <c:set var="state" value="${provider.state}"/>
                            <c:if test="${not empty state}">
                                <div id="active_${provider.id}" >
                                    <bean:message key="label.provider.state.${state}" bundle="providerBundle"/>
                                </div>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                    <table id="formulaTable">
                        <c:forEach var="formula" items="${formulas}">
                            <c:set var="i" value="${formula.id}"/>
                            <tr id="formulaTr_${i}">
                                <td>
                                    <input type="checkbox" name="selectedIds" id="formula_${i}" value="${i}">
                                </td>
                                <td>
                                    <html:select property="field(providerField_${i})" size="40" styleId="providerSelect_${i}" onfocus="savePreviousSelectValue(this)" onchange="rebuildSelects(this)">
                                        <c:forEach var="description" items="${fieldDescriptions}">
                                            <html:option value="${description.externalId}">
                                                <c:out value="${description.name}"/>
                                            </html:option>
                                        </c:forEach>
                                    </html:select>
                                </td>
                                <td>
                                    =
                                </td>
                                <c:forEach var="attribute" items="${attributes}" varStatus="status">
                                    <c:set var="index" value="${status.count - 1}"/>
                                    <td>
                                        <html:text property="field(valueField_${index}_${i})" size="20"/>
                                    </td>
                                    <td>
                                        +
                                    </td>
                                    <td>
                                        <html:select property="field(identTypeField_${index}_${i})" size="40" styleId="identTypeSelect${i}_${index}" onfocus="savePreviousSelectValue(this)" onchange="rebuildSelects(this)">
                                            <html:option value="">&nbsp;</html:option>
                                            <c:forEach var="attribute" items="${attributes}">
                                                <html:option value="${attribute.systemId}">
                                                    <c:out value="${attribute.name}"/>
                                                </html:option>
                                            </c:forEach>
                                        </html:select>
                                    </td>
                                    <td>
                                        +
                                    </td>
                                </c:forEach>
                                <td>
                                    <html:text property="field(lastField_${i})" size="20"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </tiles:put>
            </tiles:insert>
            <div class="pmntFormMainButton floatRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle"         value="imageSettingsBundle"/>
                    <tiles:put name="onclick"        value="cancel();"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="id"             value="saveButtonId"/>
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"         value="imageSettingsBundle"/>
                    <tiles:put name="isDefault"      value="true"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            var ids = document.getElementsByName("selectedIds");
                            var formulaIds = '';

                            for (var i = 0; i < ids.length; i++)
                            {
                                formulaIds += ids[i].value;
                                if (i + 1 != ids.length)
                                {
                                    formulaIds += ',';
                                }
                            }

                            var newIds = document.getElementsByName("newSelectedIds");
                            var newFormulaIds = '';

                            for (var i = 0; i < newIds.length; i++)
                            {
                                newFormulaIds += newIds[i].value;
                                if (i + 1 != newIds.length)
                                {
                                    newFormulaIds += ',';
                                }
                            }

                            $('#formulaIdsId').val(formulaIds);
                            $('#newFormulaIdsId').val(newFormulaIds);
                            return true;
                        }
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
