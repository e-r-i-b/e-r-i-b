<%@ page import="com.rssl.phizic.gate.longoffer.TotalAmountPeriod" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/autopayment">
<tiles:insert definition="recipientDictionariesEdit">
    <tiles:put name="needSave" type="string" value="false"/>
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isEditable" value="${frm.editable}"/>

    <tiles:put name="submenu" value="Provider/AutoPaySetting"/>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="name">
                <bean:message bundle="providerBundle" key="label.field.autopay.settings"/>
            </tiles:put>
            <tiles:put name="description">
                <bean:message bundle="providerBundle" key="label.field.autopay.settings.description"/>
            </tiles:put>
            <tiles:put name="data">
                <script type="text/javascript">

                    $(document).ready(function()
                    {
                        initAreaMaxLengthRestriction("longText1", 500);
                        initAreaMaxLengthRestriction("longText2", 500);
                        initAreaMaxLengthRestriction("longText3", 500);
                    });
                    function enableBlock(blockElement, flag)
                    {
                        $("input, textarea, select", blockElement).each(function()
                        {
                            this.disabled = !flag;
                        });
                    }

                    function toggleAvaiblePayments()
                    {
                        if (!ensureElementByName('field(autoPaymentSupported)').checked || ${!isEditable}){
                             $('[name = "field(autoPaymentVisible)"]:not([type=hidden])').attr("disabled", true);
                             $('[name = "field(autoPaymentVisible)"][type=hidden]').attr("value",$('[name = "field(autoPaymentVisible)"]:checked').val());
                        }
                        else {
                            $('[name = "field(autoPaymentVisible)"]').attr("disabled", false);
                        }
                        if (!ensureElementByName('field(autoPaymentSupportedInAPI)').checked || ${!isEditable}){
                             $('[name = "field(autoPaymentVisibleInAPI)"]:not([type=hidden])').attr("disabled", true);
                             $('[name = "field(autoPaymentVisibleInAPI)"][type=hidden]').attr("value",$('[name = "field(autoPaymentVisibleInAPI)"]:checked').val());
                        }
                        else {
                            $('[name = "field(autoPaymentVisibleInAPI)"]').attr("disabled", false);
                        }
                        if (!ensureElementByName('field(autoPaymentSupportedInATM)').checked || ${!isEditable}){
                             $('[name = "field(autoPaymentVisibleInATM)"]:not([type=hidden])').attr("disabled", true);
                             $('[name = "field(autoPaymentVisibleInATM)"][type=hidden]').attr("value",$('[name = "field(autoPaymentVisibleInATM)"]:checked').val());
                        }
                        else {
                            $('[name = "field(autoPaymentVisibleInATM)"]').attr("disabled", false);
                        }
                        if (!ensureElementByName('field(autoPaymentSupportedInSocialApi)').checked || ${!isEditable}){
                             $('[name = "field(autoPaymentVisibleInSocialApi)"]:not([type=hidden])').attr("disabled", true);
                             $('[name = "field(autoPaymentVisibleInSocialApi)"][type=hidden]').attr("value",$('[name = "field(autoPaymentVisibleInSocialApi)"]:checked').val());
                        }
                        else {
                            $('[name = "field(autoPaymentVisibleInSocialApi)"]').attr("disabled", false);
                        }
                        if (!ensureElementByName('field(autoPaymentSupportedInERMB)').checked || ${!isEditable}){
                             $('[name = "field(autoPaymentVisibleInERMB)"]:not([type=hidden])').attr("disabled", true);
                             $('[name = "field(autoPaymentVisibleInERMB)"][type=hidden]').attr("value",$('[name = "field(autoPaymentVisibleInERMB)"]:checked').val());
                        }
                        else {
                            $('[name = "field(autoPaymentVisibleInERMB)"]').attr("disabled", false);
                        }
                    }

                    function initialize()
                    {
                        <%-- дисэйблим всё что должно быть не активно --%>
                        var accessAutoPayment = ensureElement('autoPaymentSupported').checked ||ensureElement('autoPaymentSupportedInAPI').checked || ensureElement('autoPaymentSupportedInATM').checked || ensureElement('autoPaymentSupportedInERMB').checked;
                        ensureElement('autoPaymentSupported').disabled = ${!isEditable};
                        ensureElement('autoPaymentSupportedInAPI').disabled = ${!isEditable};
                        ensureElement('autoPaymentSupportedInATM').disabled = ${!isEditable};
                        ensureElement('autoPaymentSupportedInERMB').disabled = ${!isEditable};
                        ensureElement('bankomatSupported').disabled = !accessAutoPayment || ${!isEditable};
                        if(!accessAutoPayment)
                        {
                            $("tr[id$='AutoPayRow']").each(function()
                            {
                                enableBlock(this, false);
                            });
                        }
                        else
                        {
                            initializeThresholdAutoPay();
                            initializeAlwaysAutoPay();
                            initializeInvoiceAutoPay();
                        }
                        toggleAvaiblePayments();
                    }

                    $(document).ready(function() { initialize();});

                </script>

                <html:hidden property="id"/>

                <fieldset>
                    <legend>Настройка видимости в каналах</legend>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Интернет-банк
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:checkbox property="field(autoPaymentSupported)" name="frm" styleId="autoPaymentSupported" value="true" onclick="initialize()"/>
                            <label for="autoPaymentSupported"><bean:message key="label.field.access" bundle="providerBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.visible" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(autoPaymentVisible)" name="frm" value="true" style="border:0" styleId="showInSystem" disabled="${!isEditable}"/> <label for="showInSystem"><bean:message key="label.catalog" bundle="providerBundle"/></label>
                            <br>
                            <html:radio property="field(autoPaymentVisible)" name="frm" value="false" style="border:0" styleId="hideInSystem" disabled="${!isEditable}"/> <label for="hideInSystem"><bean:message key="label.search.only" bundle="providerBundle"/></label>
                            <html:hidden property="field(autoPaymentVisible)" name="frm"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            mApi
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:checkbox property="field(autoPaymentSupportedInAPI)" name="frm" styleId="autoPaymentSupportedInAPI" value="true" onclick="initialize()"/>
                            <label for="autoPaymentSupportedInAPI"><bean:message key="label.field.access" bundle="providerBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.visible" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(autoPaymentVisibleInAPI)" name="frm" value="true" style="border:0" styleId="showInAPI" disabled="${!isEditable}"/><label for="showInAPI"><bean:message key="label.catalog" bundle="providerBundle"/></label>
                            <br>
                            <html:radio property="field(autoPaymentVisibleInAPI)" name="frm" value="false" style="border:0" styleId="hideInAPI" disabled="${!isEditable}"/><label for="hideInAPI"> <bean:message key="label.search.only" bundle="providerBundle"/></label>
                            <html:hidden property="field(autoPaymentVisibleInAPI)" name="frm"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            atmApi
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:checkbox property="field(autoPaymentSupportedInATM)" name="frm" styleId="autoPaymentSupportedInATM" value="true" onclick="initialize()"/>
                            <label for="autoPaymentSupportedInATM"><bean:message key="label.field.access" bundle="providerBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.visible" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(autoPaymentVisibleInATM)" name="frm" value="true" style="border:0" styleId="showInATM" disabled="${!isEditable}"/><label for="showInATM"> <bean:message key="label.catalog" bundle="providerBundle"/></label>
                            <br>
                            <html:radio property="field(autoPaymentVisibleInATM)" name="frm" value="false" style="border:0" styleId="hideInATM" disabled="${!isEditable}"/> <label for="hideInATM"><bean:message key="label.search.only" bundle="providerBundle"/></label>
                            <html:hidden property="field(autoPaymentVisibleInATM)" name="frm"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            socialApi
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:checkbox property="field(autoPaymentSupportedInSocialApi)" name="frm" styleId="autoPaymentSupportedInSocialApi" value="true" onclick="initialize()"/>
                            <label for="autoPaymentSupportedInSocialApi"><bean:message key="label.field.access" bundle="providerBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.visible" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(autoPaymentVisibleInSocialApi)" name="frm" value="true" style="border:0" styleId="showInSocialApi" disabled="${!isEditable}"/><label for="showInATM"> <bean:message key="label.catalog" bundle="providerBundle"/></label>
                            <br>
                            <html:radio property="field(autoPaymentVisibleInSocialApi)" name="frm" value="false" style="border:0" styleId="hideInSocialApi" disabled="${!isEditable}"/> <label for="hideInATM"><bean:message key="label.search.only" bundle="providerBundle"/></label>
                            <html:hidden property="field(autoPaymentVisibleInSocialApi)" name="frm"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            ЕРМБ
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:checkbox property="field(autoPaymentSupportedInERMB)" name="frm" styleId="autoPaymentSupportedInERMB" value="true" onclick="initialize()"/>
                            <label for="autoPaymentSupportedInERMB"><bean:message key="label.field.access" bundle="providerBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.visible" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(autoPaymentVisibleInERMB)" name="frm" value="true" style="border:0" styleId="showInERMB" disabled="${!isEditable}"/> <label for="showInERMB"><bean:message key="label.catalog" bundle="providerBundle"/></label>
                            <br>
                            <html:radio property="field(autoPaymentVisibleInERMB)" name="frm" value="false" style="border:0" styleId="hideInERMB" disabled="${!isEditable}"/> <label for="hideInERMB"><bean:message key="label.search.only" bundle="providerBundle"/></label>
                            <html:hidden property="field(autoPaymentVisibleInERMB)" name="frm"/>
                        </tiles:put>
                    </tiles:insert>
                </fieldset>
                <div class="showBankomatSupported">
                    <fieldset>
                        <legend>Настройка atmApi</legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <div class="indent-top indent-down">
                                    <html:checkbox property="field(bankomatSupported)" name="frm" styleId="bankomatSupported" value="true"/>
                                    <label for="bankomatSupported"> <bean:message key="label.field.autopay.bankomat" bundle="providerBundle"/></label>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                  </fieldset>
                </div>

                <div id="thresholdAutoPayRow" class="showBilling">
                    <fieldset>
                        <legend><bean:message key="label.field.autopay.border.settings" bundle="providerBundle"/></legend>
                        <div class="indent-top indent-down">

                            <script type="text/javascript">

                                function existValue(value)
                                {
                                    var massValues = ensureElement("thresholdDiscreteValues").value.split('|');
                                    for(var i = 0; i <massValues.length; i++)
                                    {
                                        if(massValues[i]==value)
                                            return true;
                                    }

                                    return false;
                                }

                                <%-- добавить строку в таблицу допустимых значений --%>
                                function addBorderValue(value)
                                {
                                    if(value == null || value == "")
                                    {
                                        alert("Введите значение");
                                        return;
                                    }

                                    if(!/^(\d ?){1,7}((\.|,)\d{0,2})?$/.test(trim(value)))
                                    {
                                        alert("Укажите значение в правильном формате: #######.##");
                                        return;
                                    }

                                    var discreteValues = ensureElement("thresholdDiscreteValues");
                                    var replaceValue = value.replace(',', '.');
                                    replaceValue = trim(replaceValue);
                                    if(existValue(replaceValue))
                                    {
                                        alert("Значение уже добавлено в таблицу допустимых значений");
                                        return;
                                    }

                                    <%-- добавляем значение в скрытое поле --%>
                                    discreteValues.value = discreteValues.value == "" ?  replaceValue : (discreteValues.value + '|' + replaceValue);
                                    <%-- обновляем таблицу --%>
                                    updateTable();
                                }

                                <%-- обновляем таблицу на основании скрытого поля допустимых значений + сортировка --%>
                                function updateTable()
                                {
                                    var strValue = ensureElement("thresholdDiscreteValues").value;
                                     <%-- если значений нет, отображать таблицу не надо --%>
                                    if(strValue == null || strValue == "")
                                    {
                                        <%-- если значений нет, то скрываем таблицу --%>
                                        $("#divDiscreteValuesTable").hide();
                                        return;
                                    }
                                     <%-- получаем массив значений --%>
                                    var massValues = strValue.split('|');
                                    <%-- сортируем --%>
                                    massValues.sort((function(a, b){return parseFloat(a) - parseFloat(b);}));
                                    <%-- удаляем все элементы --%>
                                    $("#discreteValuesTable tr").remove(":has(td)");
                                    <%-- формируем сортированную таблицу --%>
                                    $(massValues).each(function(index, value)
                                    {
                                        $("#discreteValuesTable").append(formRowDiscreteValue(FloatToString(value, 2, ' ')));
                                    });
                                    $("#divDiscreteValuesTable").show();
                                }

                                <%-- формирование строки таблицы со значением --%>
                                function formRowDiscreteValue(value)
                                {
                                    var tr = document.createElement("tr");
                                    var checkboxCell = document.createElement("td");
                                    var valueCell = document.createElement("td");

                                    tr.appendChild(checkboxCell);
                                    tr.appendChild(valueCell);

                                    var checkbox = document.createElement("input");
                                    checkbox.setAttribute("type","checkbox");
                                    checkboxCell.appendChild(checkbox);

                                    var spanValue = document.createElement("span");
                                    spanValue.setAttribute("name", "value");
                                    spanValue.innerHTML = value;
                                    valueCell.appendChild(spanValue);

                                    valueCell.appendChild(document.createTextNode(' р.'));

                                    return tr;
                                }

                                <%-- удалить отмеченные строки из таблицы --%>
                                function removeCheckedValue()
                                {
                                    var isCkecked = false;
                                    $("#discreteValuesTable tr").each(function(index)
                                    {
                                        var checkboxElement = $(this).find("td input[type='checkbox']").get(0);
                                        if(checkboxElement == undefined || checkboxElement == null)
                                            return;

                                        if(checkboxElement.checked)
                                        {
                                           $(this).remove();
                                           isCkecked = true;
                                        }
                                    });

                                    <%-- если ни одного значения не было выбрано --%>
                                    if(!isCkecked)
                                    {
                                        alert("Не отмечена ни одна запись. Отметьте значение и нажмите на кнопку «Удалить»");
                                        return;
                                    }

                                    <%-- обновляем скрытое поле --%>
                                    updateThresholdDiscreteValues();
                                    <%-- скрываем таблицу если нет записей --%>
                                    if(ensureElement("thresholdDiscreteValues").value == '')
                                        $("#divDiscreteValuesTable").hide();
                                    else
                                        $("#divDiscreteValuesTable").show();
                                }

                                <%-- обновляем скрытое поле дискретных значений на основании таблицы --%>
                                function updateThresholdDiscreteValues()
                                {
                                    var result = "";

                                    $("#discreteValuesTable td>span[name='value']").each(function(index)
                                    {
                                        result += result != "" ? ('|' + this.innerHTML) : this.innerHTML;
                                    });

                                    ensureElement("thresholdDiscreteValues").value = result;
                                }

                                function setEnableValues(type)
                                {
                                    var flag = type == 'true';
                                    enableBlock(ensureElement('thresholdIntervalValuesBlock'), flag);
                                    enableBlock(ensureElement('thresholdDiscreteValuesBlock'), !flag);

                                    if(!flag)
                                    {
                                        <%-- блокируем кнопку добавить --%>
                                        ensureElement('addDiscreteValueButton').disabled = false;
                                    }
                                }

                                <%-- инициализация порогового автоплатежа --%>
                                function initializeThresholdAutoPay()
                                {
                                    var checkboxElement = ensureElement('thresholdAutoPay');
                                    checkboxElement.disabled = ${!isEditable};

                                    updateTable();
                                    enableBlock(ensureElement('thresholdAutoPayData'), checkboxElement.checked  && ${isEditable});
                                    if(!checkboxElement.checked  || ${!isEditable})
                                        return;

                                    var totalMaxSumFlag = ensureElement("totalMaxSumFlag").checked;
                                    enableBlock(ensureElement('totalMaxSumBlock'), totalMaxSumFlag);
                                    enableBlock(ensureElement('periodMaxSumBlock'), totalMaxSumFlag);

                                    var intervalValuesType = ensureElement("intervalValuesType");
                                    var discreteValuesType = ensureElement("discreteValuesType");

                                    if(discreteValuesType.checked)
                                    {
                                        setEnableValues(discreteValuesType.value);
                                    }
                                    else if(intervalValuesType.checked)
                                    {
                                        setEnableValues(intervalValuesType.value);
                                    }
                                    else
                                    {
                                        enableBlock(ensureElement('thresholdIntervalValuesBlock'), false);
                                        enableBlock(ensureElement('thresholdDiscreteValuesBlock'), false);
                                    }
                                }
                            </script>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <html:checkbox property="field(thresholdAutoPay)" name="frm" styleId="thresholdAutoPay" value="true" onclick="initializeThresholdAutoPay()"/>
                                    <span><bean:message key="label.field.access" bundle="providerBundle"/></span>
                                </tiles:put>
                            </tiles:insert>

                            <div id="thresholdAutoPayData">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.field.autopay.summa" bundle="providerBundle"/>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <span class="indention">от
                                        <html:text property="field(minSumThresholdAutoPay)" name="frm" size="10" maxlength="12" styleId="minSumThresholdAutoPay" styleClass="moneyField"/> руб.</span>
                                        до
                                        <html:text property="field(maxSumThresholdAutoPay)" name="frm" size="10" maxlength="12" styleId="maxSumThresholdAutoPay" styleClass="moneyField"/> руб.
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="data">
                                        <html:checkbox property="fields(accessTotalMaxSumThresholdAutoPay)" name="frm" styleId="totalMaxSumFlag" value="true"
                                                       onclick="enableBlock(ensureElement('periodMaxSumBlock'), this.checked);enableBlock(ensureElement('totalMaxSumBlock'), this.checked)"/>
                                        <bean:message key="label.field.autopay.period.max.summa" bundle="providerBundle"/>
                                        <div id="periodMaxSumBlock">
                                            <html:select property="field(periodMaxSumThresholdAutoPay)" name="frm">
                                                <c:forEach var="item" items="<%=TotalAmountPeriod.values()%>">
                                                    <html:option value="${item}">
                                                        <bean:message key="autopay.period.max.summa.${item}" bundle="providerBundle"/>
                                                    </html:option>
                                                </c:forEach>
                                            </html:select>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="data">
                                        <div id="totalMaxSumBlock">
                                            <html:text property="field(totalMaxSumThresholdAutoPay)" size="10" name="frm"/> руб.
                                        </div>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.field.autopay.border.value" bundle="providerBundle"/>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <html:radio property="field(thresholdValuesType)" value="true" onclick="setEnableValues(this.value)" styleId="intervalValuesType"/>
                                        <bean:message key="label.field.autopay.access.interval.check" bundle="providerBundle"/>
                                            <span class="labelAll" id="thresholdIntervalValuesBlock">
                                                <span class="indention">от
                                                <html:text property="field(minValueThresholdAutoPay)" name="frm" size="10" maxlength="12" styleId="minValueThresholdAutoPay" styleClass="moneyField"/> руб.</span>
                                                до
                                                <html:text property="field(maxValueThresholdAutoPay)" name="frm" size="10" maxlength="12" styleId="maxValueThresholdAutoPay" styleClass="moneyField"/> руб.
                                            </span>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="data">
                                        <html:radio property="field(thresholdValuesType)" value="false" onclick="setEnableValues(this.value)" styleId="discreteValuesType"/>
                                        <bean:message key="label.field.autopay.access.values.check" bundle="providerBundle"/>&nbsp; <br/>
                                            <span id="thresholdDiscreteValuesBlock">
                                                <html:text property="field(discreteValue)" name="frm" size="10" maxlength="12" styleId="discreteValue" styleClass="moneyField"/>р.

                                                <html:hidden property="field(thresholdDiscreteValues)" styleId="thresholdDiscreteValues"/>

                                                <input type="button"
                                                       value="<bean:message bundle="providerBundle" key="button.add.summ"/>"
                                                       onclick="addBorderValue(getStringWithoutSpace(ensureElement('discreteValue').value));"
                                                       id="addDiscreteValueButton" class="buttonGrayNew"/>

                                                <div id="divDiscreteValuesTable" style="display:none;">

                                                    <div id="removeDiscreteValueButton" class="floatRight">
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.remove"/>
                                                            <tiles:put name="commandHelpKey" value="button.remove"/>
                                                            <tiles:put name="bundle" value="providerBundle"/>
                                                            <tiles:put name="onclick">removeCheckedValue();</tiles:put>
                                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                                        </tiles:insert>
                                                    </div>

                                                    <table width="100%" cellspacing="0" cellpadding="0" class="standartTable" id="discreteValuesTable">
                                                        <tr>
                                                            <th width="20px">&nbsp;</th>
                                                            <th>
                                                                &nbsp;<bean:message bundle="providerBundle" key="label.field.autopay.access.values"/>&nbsp;
                                                            </th>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </span>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message bundle="providerBundle" key="label.field.autopay.client.hint"/>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <html:textarea rows="3" cols="35" property="field(clientHintThresholdAutoPay)" styleId="longText1"></html:textarea>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div id="alwaysAutoPayRow" class="showBilling">
                    <script type="text/javascript">
                        function initializeAlwaysAutoPay()
                        {
                            var checkboxElement = ensureElement('alwaysAutoPay');
                            checkboxElement.disabled = ${!isEditable};

                            enableBlock(ensureElement('alwaysAutoPayData'), checkboxElement.checked  && ${isEditable});
                        }
                    </script>

                    <fieldset>
                        <legend><bean:message key="label.field.autopay.always.settings" bundle="providerBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:checkbox property="field(alwaysAutoPay)" name="frm" styleId="alwaysAutoPay" value="true" onclick="initializeAlwaysAutoPay()"/>
                                <span><bean:message key="label.field.access" bundle="providerBundle"/></span>
                            </tiles:put>
                        </tiles:insert>

                        <div class="indent-top indent-down" id="alwaysAutoPayData">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.field.autopay.summa" bundle="providerBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
<span class="indention">от
                                        <html:text property="field(minSumAlwaysAutoPay)" name="frm" size="10" maxlength="12" styleId="minSumAlwaysAutoPay" styleClass="moneyField"/> руб.</span>
                                    до
                                    <html:text property="field(maxSumAlwaysAutoPay)" name="frm" size="10" maxlength="12" styleId="maxSumAlwaysAutoPay" styleClass="moneyField"/> руб.
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="providerBundle" key="label.field.autopay.client.hint"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:textarea rows="3" cols="35" name="frm" property="field(clientHintAlwaysAutoPay)" styleId="longText2" ></html:textarea>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </fieldset>
                </div>
                <div id="invoiceAutoPayRow" class="showBilling">
                    <script type="text/javascript">
                        function initializeInvoiceAutoPay()
                        {
                            var checkboxElement = ensureElement('invoiceAutoPay');
                            checkboxElement.disabled = ${!isEditable};

                            enableBlock(ensureElement('invoiceAutoPayData'), checkboxElement.checked && ${isEditable});
                        }
                    </script>

                    <fieldset>
                        <legend><bean:message key="label.field.autopay.invoice.settings" bundle="providerBundle"/></legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:checkbox property="field(invoiceAutoPay)" styleId="invoiceAutoPay" value="true" onclick="initializeInvoiceAutoPay()"/>
                                <span><bean:message key="label.field.access" bundle="providerBundle"/></span>
                            </tiles:put>
                        </tiles:insert>

                        <div class="indent-top indent-down" id="invoiceAutoPayData">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="providerBundle" key="label.field.autopay.client.hint"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:textarea rows="3" cols="35" property="field(clientHintInvoiceAutoPay)" styleId="longText3"></html:textarea>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </fieldset>
                </div>
            </tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="action" value="/private/dictionaries/provider/list.do"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false" operation="EditServiceProvidersOperation">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>