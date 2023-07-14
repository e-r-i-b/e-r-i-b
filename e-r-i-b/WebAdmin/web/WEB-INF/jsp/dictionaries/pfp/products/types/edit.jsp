<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/types/edit" enctype="multipart/form-data" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="ptoductDictionaryType" value="${form.fields.type}"/>
    <c:set var="availableChangeDiagramView" value="${ptoductDictionaryType eq 'ACCOUNT' or
                                                     ptoductDictionaryType eq 'FUND' or
                                                     ptoductDictionaryType eq 'IMA' or
                                                     ptoductDictionaryType eq 'TRUST_MANAGING'}"/>
    <c:set var="availableChangeTableView" value="${ptoductDictionaryType eq 'ACCOUNT' or
                                                   ptoductDictionaryType eq 'FUND' or
                                                   ptoductDictionaryType eq 'IMA' or
                                                   ptoductDictionaryType eq 'TRUST_MANAGING' or
                                                   ptoductDictionaryType eq 'COMPLEX_INSURANCE' or
                                                   ptoductDictionaryType eq 'COMPLEX_INVESTMENT'}"/>
    <c:set var="availableChangeView" value="${availableChangeDiagramView or availableChangeTableView}"/>

    <c:set var="axisStepMaxValue" value="${form.diagramAxisStepMaxValue}"/>
    <c:set var="axisStepMaxCount" value="${form.diagramAxisStepMaxCount}"/>
    <c:set var="tableColumnNameMaxCount" value="${form.tableParametersMaxColumnCount}"/>
    <tiles:insert definition="editPFPProductType">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="pfpProductTypeBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpProductTypeBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.type"/>:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <html:hidden property="fields(type)"/>
                            <bean:message bundle="pfpProductTypeBundle" key="type.${form.fields.type}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="58" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.use"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <div>
                                <html:radio property="fields(use)" value="true"  styleId="fieldUseTrue"/>
                                <label for="fieldUseTrue" class="bold"><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.use.true"/></label>
                            </div>
                            <div>
                                <html:radio property="fields(use)" value="false" styleId="fieldUseFalse"/>
                                <label for="fieldUseFalse" class="bold"><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.use.false"/></label>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.targetGroup"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <jsp:include page="../setSegmentCodeTypeArea.jsp" flush="false">
                                    <jsp:param name="entityPrefix" value="product.type"/>
                                </jsp:include>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.icon"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false">
                                <tiles:put name="id" value="ProductTypeImage"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${availableChangeView}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnView"/>:
                            </tiles:put>
                            <tiles:put name="data">
                                <c:if test="${availableChangeDiagramView}">
                                    <div>
                                        <html:checkbox property="fields(useOnDiagram)" value="true" styleId="fieldUseOnDiagram" onclick="hideOrShow('useOnDiagramFields', !this.checked);"/>
                                        <label for="fieldUseOnDiagram" class="bold"><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.selector"/></label>
                                    </div>
                                </c:if>
                                <c:if test="${availableChangeTableView}">
                                    <div>
                                        <html:checkbox property="fields(useOnTable)" value="true" styleId="fieldUseOnTable" onclick="hideOrShow('useOnTableFields', !this.checked);"/>
                                        <label for="fieldUseOnTable" class="bold"><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnTable.selector"/></label>
                                    </div>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>

                        <c:if test="${availableChangeDiagramView}">
                            <div id="useOnDiagramFields"<c:if test="${not form.fields['useOnDiagram']}"> style="display:none"</c:if>>
                                <fieldset>
                                    <legend><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram"/></legend>
                                    <c:set var="axisStepNameDefault"><bean:message bundle='pfpProductTypeBundle' key='form.edit.fields.useOnDiagram.axis.steps.name.hint'/></c:set>
                                    <script type="text/javascript">
                                        function addAxisStep()
                                        {
                                            var rowContainer = $('#xAxisStepRowContainer');
                                            if (rowContainer.find('tr').length  >= ${axisStepMaxCount})
                                            {
                                                alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.full" arg0="${axisStepMaxCount}"/>');
                                                return;
                                            }

                                            var axisStepCountContainer = $('#xAxisStepCount');
                                            var count = axisStepCountContainer.val();
                                            var row =   '<tr id="xAxisStepRow' + count + '">' +
                                                    '<td><input type="checkbox"/></td>' +
                                                    '<td>' +
                                                    '<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.from"/>' +
                                                    '</td>' +
                                                    '<td>' +
                                                    '<input type="text" name="fields(xAxisStepFrom' + count + ')" size="3" maxlength="2"/>' +
                                                    '</td>' +
                                                    '<td>' +
                                                    '<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.to"/>' +
                                                    '</td>' +
                                                    '<td>' +
                                                    '<input type="text" name="fields(xAxisStepTo' + count + ')" size="3" maxlength="2"/>' +
                                                    '</td>' +
                                                    '<td>' +
                                                    '<input type="text" name="fields(xAxisStepName' + count + ')" title="${axisStepNameDefault}" class="customPlaceholder" size="40" maxlength="50"/>'+
                                                    '</td>' +
                                                    '</tr>';
                                            rowContainer.append(row);
                                            customPlaceholder.init(rowContainer);
                                            $('#xAxisStepRemoveButton').show();
                                            axisStepCountContainer.val(++count);
                                        }
                                        function removeAxisStep()
                                        {
                                            var rowContainer = $('#xAxisStepRowContainer');
                                            rowContainer.find(':checked').each(function(){
                                                $(this).parents('tr[id^=xAxisStepRow]').remove();
                                            });
                                            if (rowContainer.find('tr').length == 0)
                                                $('#xAxisStepRemoveButton').hide();
                                        }
                                    </script>

                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <html:checkbox property="fields(useZero)" styleId="</label>" value="true"/>&nbsp;
                                            <label for="</label>"><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.zero.use"/></label>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.x.name"/>:
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:text property="fields(xAxisName)" size="50" maxlength="50"/>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps"/>:
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:checkbox property="fields(xAxisStepUse)" value="true" styleId="xAxisStepUse" onclick="hideOrShow('xAxisStepContainer', !this.checked);"/>
                                            <label for="xAxisStepUse" class="bold"><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.use"/></label>
                                        </tiles:put>
                                    </tiles:insert>

                                    <div id="xAxisStepContainer"<c:if test="${not form.fields['xAxisStepUse']}"> style="display:none"</c:if>>
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="data">
                                                <c:set var="xAxisStepCount" value="${form.fields.xAxisStepCount}"/>
                                                <c:if test="${empty xAxisStepCount}">
                                                    <c:set var="xAxisStepCount" value="0"/>
                                                </c:if>
                                                <input type="hidden" name="fields(xAxisStepCount)" value="${xAxisStepCount}" id="xAxisStepCount"/>

                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="form.edit.fields.useOnDiagram.axis.steps.button.add"/>
                                                    <tiles:put name="commandHelpKey" value="form.edit.fields.useOnDiagram.axis.steps.button.add"/>
                                                    <tiles:put name="bundle" value="pfpProductTypeBundle"/>
                                                    <tiles:put name="onclick" value="addAxisStep();"/>
                                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                                </tiles:insert>

                                                <c:set var="xAxisStepDisplay" value=""/>
                                                <c:if test="${xAxisStepCount <= 0}"><c:set var="xAxisStepDisplay" value="style='display:none'"/> </c:if>

                                                <div id="xAxisStepRemoveButton" ${xAxisStepDisplay}>
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="form.edit.fields.useOnDiagram.axis.steps.button.remove"/>
                                                        <tiles:put name="commandHelpKey" value="form.edit.fields.useOnDiagram.axis.steps.button.remove"/>
                                                        <tiles:put name="bundle" value="pfpProductTypeBundle"/>
                                                        <tiles:put name="onclick" value="removeAxisStep();"/>
                                                        <tiles:put name="viewType" value="buttonGrayNew"/>
                                                    </tiles:insert>
                                                </div>
                                                <div class="clear"></div>

                                                <table id="xAxisStepRowContainer">
                                                    <c:if test="${xAxisStepCount > 0}">
                                                        <c:forEach var="row" begin="0" end="${xAxisStepCount-1}">
                                                            <tr id="xAxisStepRow${row}">
                                                                <td><input type="checkbox"/></td>
                                                                <td>
                                                                    <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.from"/>
                                                                </td>
                                                                <td>
                                                                    <html:text property="fields(xAxisStepFrom${row})" size="3" maxlength="2"/>
                                                                </td>
                                                                <td>
                                                                    <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.to"/>
                                                                </td>
                                                                <td>
                                                                    <html:text property="fields(xAxisStepTo${row})" size="3" maxlength="2"/>
                                                                </td>
                                                                <td>
                                                                    <html:text property="fields(xAxisStepName${row})"
                                                                               title="${axisStepNameDefault}"
                                                                               styleClass="customPlaceholder"
                                                                               size="32"
                                                                               maxlength="50"/>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                </table>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.y.name"/>:
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:text property="fields(yAxisName)" size="50" maxlength="50"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </fieldset>
                            </div>
                        </c:if>
                        <c:if test="${availableChangeTableView}">
                            <div id="useOnTableFields"<c:if test="${not form.fields['useOnTable']}"> style="display:none"</c:if>>
                                <fieldset>
                                    <legend><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnTable"/></legend>
                                    <script type="text/javascript">
                                        function addColumnsStep()
                                        {
                                            var rowContainer = $('#tableColumnNameRowContainer');
                                            if (rowContainer.find('tr').length  >= ${tableColumnNameMaxCount})
                                            {
                                                alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnTable.columns.message.full" arg0="${tableColumnNameMaxCount}"/>');
                                                return;
                                            }

                                            var tableColumnCountContainer = $('#tableColumnNameCount');
                                            var count = tableColumnCountContainer.val();
                                            var row =   '<tr id="tableColumnNameRow' + count + '">' +
                                                            '<td><input type="checkbox" value="' + count + '"/></td>' +
                                                            '<td>' +
                                                                '<input type="text" name="fields(tableColumnName' + count + ')" size="40" maxlength="50"/>' +
                                                            '</td>' +
                                                            '<td>' +
                                                                '<img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown areaBlockMove" style="visibility: visible;" onmousedown="tableColumnNameChangeRow(this);"/>' +
                                                                '<img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp areaBlockMove" style="visibility: visible;" onmousedown="tableColumnNameChangeRow(this);"/>' +
                                                            '</td>' +
                                                        '</tr>';
                                            rowContainer.append(row);
                                            onChangeColumnNames();
                                            $('#tableColumnNameRemoveButton').show();
                                            tableColumnCountContainer.val(++count);
                                        }

                                        function removeColumnsStep()
                                        {
                                            var rowContainer = $('#tableColumnNameRowContainer');
                                            rowContainer.find(':checked').each(function(){
                                                $(this).parents('tr[id^=tableColumnNameRow]').remove();
                                            });
                                            if (rowContainer.find('tr').length == 0)
                                                $('#tableColumnNameRemoveButton').hide();

                                            onChangeColumnNames();
                                        }

                                        function tableColumnNameChangeRow(element)
                                        {
                                            var row = $(element).parents("tr:first");
                                            if ($(element).is(".buttonUp"))
                                                row.insertBefore(row.prev());
                                            else
                                                row.insertAfter(row.next());

                                            onChangeColumnNames();
                                        }

                                        function onChangeColumnNames()
                                        {
                                            updateOrder();
                                            hideArrows();
                                        }

                                        function updateOrder()
                                        {
                                            $('[name^=fields(order]').each(function(){
                                                $(this).val('');
                                            });
                                            $('tr[id^=tableColumnNameRow]').each(function(index, element){
                                                var orderMappingContainer = $('input[name=fields(order' + index + ')]');
                                                var currentRowNum = $(element).find('input[type=checkbox]').val();
                                                orderMappingContainer.val(currentRowNum);
                                            });
                                        }

                                        function hideArrows()
                                        {
                                            var table = $("#tableColumnNameRowContainer");
                                            table.find('tr img').css("visibility","visible");
                                            table.find('tr:first .buttonUp').css("visibility","hidden");
                                            table.find('tr:last .buttonDown').css("visibility","hidden");
                                        }

                                        $(document).ready(hideArrows);
                                    </script>

                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnTable.columns"/>:
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <c:set var="tableColumnNameCount" value="${form.fields.tableColumnNameCount}"/>
                                            <c:if test="${empty tableColumnNameCount}">
                                                <c:set var="tableColumnNameCount" value="0"/>
                                            </c:if>
                                            <input type="hidden" name="fields(tableColumnNameCount)" value="${tableColumnNameCount}" id="tableColumnNameCount"/>

                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="form.edit.fields.useOnTable.columns.button.add"/>
                                                <tiles:put name="commandHelpKey" value="form.edit.fields.useOnTable.columns.button.add"/>
                                                <tiles:put name="bundle" value="pfpProductTypeBundle"/>
                                                <tiles:put name="onclick" value="addColumnsStep();"/>
                                                <tiles:put name="viewType" value="buttonGrayNew"/>
                                            </tiles:insert>


                                            <c:set var="tableColumnNameDisplay" value=""/>
                                            <c:if test="${tableColumnNameCount <= 0}"><c:set var="tableColumnNameDisplay" value="style='display:none'"/> </c:if>

                                            <div id="tableColumnNameRemoveButton" ${tableColumnNameDisplay}>
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="form.edit.fields.useOnTable.columns.button.remove"/>
                                                    <tiles:put name="commandHelpKey" value="form.edit.fields.useOnTable.columns.button.remove"/>
                                                    <tiles:put name="bundle" value="pfpProductTypeBundle"/>
                                                    <tiles:put name="onclick" value="removeColumnsStep();"/>
                                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                                </tiles:insert>
                                            </div>

                                            <table id="tableColumnNameRowContainer">
                                                <c:if test="${tableColumnNameCount > 0}">
                                                    <c:forEach var="row" begin="0" end="${tableColumnNameCount-1}">
                                                        <tr id="tableColumnNameRow${row}">
                                                            <td><input type="checkbox" value="${row}"/><html:hidden property="field(columnId${row})"/></td>
                                                            <td>
                                                                <html:text property="fields(tableColumnName${row})" size="40" maxlength="50"/>
                                                            </td>
                                                            <td>
                                                                <img src="${skinUrl}/images/bth_down.gif"
                                                                     title="вниз"
                                                                     class="buttonDown areaBlockMove"
                                                                     style="visibility: visible;"
                                                                     onmousedown="tableColumnNameChangeRow(this);"/><img src="${skinUrl}/images/bth_up.gif"
                                                                                                                         title="вверх"
                                                                                                                         class="buttonUp areaBlockMove"
                                                                                                                         style="visibility: visible;"
                                                                                                                         onmousedown="tableColumnNameChangeRow(this);"/>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                            </table>
                                            <c:forEach var="row" begin="0" end="4">
                                                <html:hidden property="fields(order${row})" value="${row}"/>
                                            </c:forEach>
                                        </tiles:put>
                                    </tiles:insert>
                                </fieldset>
                            </div>
                        </c:if>
                    </c:if>

                    <fieldset>
                        <legend><bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.link"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.link.name"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="fields(linkName)" size="58" maxlength="100"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.link.hint"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="fields(linkHint)" size="58" maxlength="500"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListProductTypeParametersOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpProductTypeBundle"/>
                        <tiles:put name="action"            value="/pfp/products/types/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditProductTypeParametersOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpProductTypeBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                <c:if test="${availableChangeView}">
                                    var isNumber = function(num)
                                    {
                                        return !isNaN(num);
                                    }

                                    var checkAxis = function()
                                    {
                                        var noError = true;
                                        var crossingCheck = [];
                                        var rowCount = 0;
                                        if ($('tr[id^=xAxisStepRow]').size() == 0)
                                        {
                                            alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.empty"/>');
                                            noError = false;
                                            return false;
                                        }
                                        $('tr[id^=xAxisStepRow]').each(function(){
                                            var tr = $(this);
                                            var from = parseInt(tr.find('[name^=fields(xAxisStepFrom]').val());
                                            var to = parseInt(tr.find('[name^=fields(xAxisStepTo]').val());
                                            var nameField = tr.find('[name^=fields(xAxisStepName]');
                                            if (isNumber(from) || isNumber(to) || nameField.val() != nameField.attr('title'))
                                            {
                                                if (isNaN(from))
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.hasEmpty.from"/>');
                                                    noError = false;
                                                    return false;
                                                }

                                                if (isNaN(to))
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.hasEmpty.to"/>');
                                                    noError = false;
                                                    return false;
                                                }

                                                if (nameField.val() == nameField.attr('title'))
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.hasEmpty.name"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                if (from < 0)
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.lessZero.from"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                if (from > ${axisStepMaxValue})
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.biggerMax.from" arg0="6"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                if (to < 0)
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.lessZero.to"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                if (to > ${axisStepMaxValue})
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.biggerMax.to" arg0="6"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                if (to <= from)
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.toLessFrom"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                if ($.inArray(2 * from, crossingCheck) != -1 || $.inArray(2 * to, crossingCheck) != -1)
                                                {
                                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.hasCrossing"/>');
                                                    noError = false;
                                                    return false;
                                                }
                                                for (var i = 2 * from + 1; i < 2 * to; i++)
                                                {
                                                    if ($.inArray(i, crossingCheck) == -1)
                                                    {
                                                        crossingCheck.push(i);
                                                    }
                                                    else
                                                    {
                                                        alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnDiagram.axis.steps.message.hasCrossing"/>');
                                                        noError = false;
                                                        return false;
                                                    }
                                                }
                                            }
                                        });
                                        for (var i = 0; i < ${axisStepMaxValue};i++)
                                        {
                                            if ($.inArray(2 * i + 1, crossingCheck) == -1 && noError)
                                            {
                                                alert('Последняя координата предыдущего шага должна быть первой координатой следующего шага. Пожалуйста, проверьте диапазоны шагов.\nОбратите внимание: для сохранения шагов первая координата на оси должна быть 0, а последняя 6.');
                                                noError = false;
                                                return false;
                                            }
                                        }
                                        if (!noError)
                                            return false;

                                        return true;
                                    };

                                    if ($('#fieldUseOnDiagram').is(':checked') && $('#xAxisStepUse').is(':checked') && !checkAxis())
                                        return false;

                                    var checkTable = function()
                                    {
                                        var result = false;
                                        $('tr[id^=tableColumnNameRow]').each(function(){
                                            if ($(this).find('input[name^=fields(tableColumnName]').val() != '')
                                            {
                                                result = true;
                                                return false;
                                            }
                                        });
                                        return result;
                                    }

                                    if ($('#fieldUseOnTable').is(':checked') && !checkTable())
                                    {
                                        alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.useOnTable.columns.message.empty"/>');
                                        return false;
                                    }
                                </c:if>
                            
                                if (getImageObject('ProductTypeImage').isEmpty())
                                {
                                    alert('<bean:message bundle="pfpProductTypeBundle" key="form.edit.fields.icon.message.isEmpty"/>');
                                    return false;
                                }
                                return $('#fieldUseFalse').is(':checked') || checkSegment();
                            }
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>