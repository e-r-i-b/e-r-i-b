<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/fields/edit" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="recipientDictionariesEdit">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="isEditable" value="${frm.editable}"/>
        <c:set var="isNew"      value="${empty frm.fieldId}"/>
        <tiles:put name="submenu" value="Provider/Fields" type="string"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message bundle="providerBundle" key="edit.field.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="providerBundle" key="edit.field.description"/></tiles:put>
                <tiles:put name="data">
                    <html:hidden property="id" styleId="id"/>

                    <fieldset>
                        <legend><bean:message key="label.general.parameters" bundle="providerBundle"/></legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.provider" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(providerName)" size="40" name="frm" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.billing" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(billingName)" size="40" name="frm" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.payment.service" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(serviceName)" size="40" name="frm" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                    <fieldset>
                        <legend><bean:message key="label.field.parameters" bundle="providerBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.externalid" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(exteranlId)" size="40" maxlength="40" name="frm" disabled="${!isEditable}" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.name" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(name)" styleId="name" size="40" maxlength="60" name="frm" disabled="${!isEditable}"  styleClass="contactInput"/><br/>
                                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Link.gif" id="extDescIdLinkButton" style="display: none" onmousedown="changeSelText('url', ['name']);"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.description" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:textarea property="field(description)" name="frm"  disabled="${!isEditable}" styleId="shortText1" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.hint" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:textarea property="field(hint)" name="frm" disabled="${!isEditable}" styleId="shortText2" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.type" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="field(type)" name="frm" styleId="field(type)" disabled="${!isEditable}" styleClass="contactInput" onchange="updateFieldProperties()">
                                    <html:option value="string"     key="label.field.type.string"   bundle="providerBundle"/>
                                    <html:option value="number"     key="label.field.type.number"   bundle="providerBundle"/>
                                    <html:option value="integer"    key="label.field.type.integer"  bundle="providerBundle"/>
                                    <html:option value="date"       key="label.field.type.date"     bundle="providerBundle"/>
                                    <html:option value="list"       key="label.field.type.list"     bundle="providerBundle"/>
                                    <html:option value="set"        key="label.field.type.set"      bundle="providerBundle"/>
                                    <html:option value="money"      key="label.field.type.money"    bundle="providerBundle"/>
                                    <html:option value="choice"     key="label.field.type.choice"   bundle="providerBundle"/>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <div id="extendedDescIdRow" style="display: none">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.field.extended.description" bundle="providerBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text styleId="extendedDescId" property="field(extendedDescId)" maxlength="50" name="frm" disabled="${!isEditable}" size="40" styleClass="contactInput"/>&nbsp;
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.length.mаx" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text styleId="field(maxlength)" property="field(maxlength)" maxlength="100" name="frm" disabled="${!isEditable}" size="40" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.length.min" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text styleId="field(minlength)" property="field(minlength)" maxlength="100" name="frm" disabled="${!isEditable}" size="40" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.number.precision" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text styleId="field(numberPrecision)" property="field(numberPrecision)" maxlength="20" name="frm" disabled="${!isEditable}" size="40" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.requisites" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <div style="float:left;">
                                    <c:if test="!isEditable">
                                        <c:set var="disable" value="disable"/>
                                    </c:if>
                                    <html:hidden property="field(requisiteTypes)" styleId="field(requisiteTypes)" name="frm"/>
                                    <select id="requisiteTypeSelect" class="contactInput" ${disable}>
                                        <option value="default" selected="true"><bean:message key="default.option.select.requisites" bundle="providerBundle"/></option>
                                        <c:forEach var="requisiteType" items="${frm.requisiteTypeList}">
                                            <option value="${requisiteType.description}">${requisiteType.description}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div style="float:left;">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add.help"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="addRequisiteType()"/>
                                        <tiles:put name="enabled"        value="${isEditable}"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </div>
                                <div class="clear"></div>
                                <div class="smallDynamicGrid" id="requisiteTypesTable_Div" style="display:none;">
                                    <tiles:insert definition="gridTableTemplate" flush="false">
                                        <tiles:put name="id" value="requisiteTypesTable"/>
                                        <tiles:put name="data"/>
                                    </tiles:insert>
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.mandatory" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(mandatory)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput" styleId="mandatory"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.editable" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox styleId="field(editable)" property="field(editable)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.visible" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(visible)" name="frm" disabled="${!isEditable}"  value="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.sum" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox styleId="field(sum)" property="field(sum)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.key" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(key)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput" onclick="javascript:setMandatory(); javascript:enableSubType();" styleId="key"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.is.for.bill" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(isForBill)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput"  styleId="isForBill"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.is.include.in.sms" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(isIncludeInSMS)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput"  styleId="isIncludeInSMS"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.is.save.in.template" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(isSaveInTemplate)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput" styleId="isSaveInTemplate"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.is.hide.in.confirmation" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(isHideInConfirmation)" name="frm" disabled="${!isEditable}" value="true" styleClass="contactInput" styleId="isHideInConfirmation"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.business.subtype" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="field(businessSubType)" disabled="${!isEditable}" styleId="field(businessSubType)">
                                    <html:option value="">Ќе указан</html:option>
                                    <html:option value="wallet">»нтернет-кошелек</html:option>
                                    <html:option value="phone">ћобильный телефон</html:option>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.defaultvalue" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(defaultValue)" name="frm" disabled="${!isEditable}" maxlength="50" size="40" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.values" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:textarea styleId="field(values)" property="field(values)" name="frm" disabled="${!isEditable}"  styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.mask" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(mask)" name="frm" disabled="${!isEditable}" styleId="field(mask)"maxlength="50" size="40" styleClass="contactInput float"/>&nbsp;
                                <tiles:insert definition="floatMessage" flush="false">
                                    <tiles:put name="id" value="floatMessage"/>
                                    <tiles:put name="text">
                                        ”казанна€ маска будет накладыватьс€ на введенное в поле клиентом значение дл€ отображени€ на форме просмотра. «амен€емые символы обозначаютс€ '#', маскируемые '*'.
                                        Ќапример #(###)***####.
                                    </tiles:put>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                    <script type="text/javascript">
                         $(document).ready(function()
                        {
                            initAreaMaxLengthRestriction("shortText1", 200);
                            initAreaMaxLengthRestriction("shortText2", 200);
                            initAreaMaxLengthRestriction("field(values)", 500);
                        });

                        function setMandatory()
                        {
                            var m = document.getElementById("mandatory");
                            var k = document.getElementById("key");

                            if (k.checked)
                                if (!m.checked)
                                    m.checked = true;
                        }
                        function enableSubType()
                        {
                            var isKey = document.getElementById("key");
                            var businessSubType = document.getElementById("field(businessSubType)");
                            if (isKey.checked)
                            {
                                businessSubType.disabled = false;
                            }
                            else
                            {
                                 businessSubType.disabled = true;
                                 businessSubType.value = "";
                            }
                            
                        }
                        function updateFieldProperties()
                        {
                            var el = document.getElementById("field(type)").value;

                            var maxlength = document.getElementById("field(maxlength)");
                            var minlength = document.getElementById("field(minlength)");
                            var numberPrecision = document.getElementById("field(numberPrecision)");
                            var sum = document.getElementById("field(sum)");
                            var editable = document.getElementById("field(editable)");
                            var valuesField = document.getElementById("field(values)");
                            var mask = document.getElementById("field(mask)");
                            var businessSubType = document.getElementById("field(businessSubType)");

                            if (el == "list" || el == "set")
                            {
                                valuesField.disabled = false;
                            }
                            else
                            {
                                valuesField.disabled = true;
                                valuesField.value = "";
                            }

                            maxlength.disabled = false;
                            minlength.disabled = false;
                            numberPrecision.disabled = false;
                            mask.disabled = false;
                            businessSubType.disabled = false;
                            sum.disabled = false;
                            editable.disabled = false;
                                editable.onclick = null;
                            if (el == "date")
                            {
                                maxlength.value = "";
                                maxlength.disabled = true;
                                minlength.value = "";
                                minlength.disabled = true;
                                businessSubType.disabled = true;
                                businessSubType.value = "";
                            }
                            if (el != "number" && el != "money")
                            {
                                sum.checked = false;
                                sum.disabled = true;
                            }
                            if (el == "list")
                            {
                                editable.checked = true;
                                editable.onclick = function() {editable.checked = true;};
                            }
                            if (el == "set")
                            {
                                editable.checked = true;
                                editable.onclick = function() {editable.checked = true;};
                            }
                            if(el == "money")
                            {
                                sum.disabled = false;
                                isPhone.disabled = true;
                                isPhone.checked = false;
                                businessSubType.disabled = true;
                                businessSubType.value = "";
                            }
                            if (el != "number")
                            {
                                numberPrecision.disabled = true;
                                numberPrecision.value = "";
                            }
                            if(el != "string")
                            {
                                mask.disabled = true;
                                mask.value="";
                            }
                            if(el == "choice")
                            {
                                $("#extDescIdLinkButton, #extendedDescIdRow").show();
                            }
                            else
                            {
                                $("#extDescIdLinkButton, #extendedDescIdRow").hide();
                            }

                            enableSubType();
                        }

                        // добавить выбранный реквизит
                        function addRequisiteType()
                        {
                            var requisiteType = document.getElementById("requisiteTypeSelect");

                            if (requisiteType.value != requisiteType.options[0].value)
                            {
                                appendRequisiteType(requisiteType.value);
                                requisiteType.options[0].selected = true;
                                return true;
                            }
                            else
                                alert("¬ведите значение в поле '–еквизиты'");

                            return false;
                        }

                        //вставить реквизит в страницу
                        function appendRequisiteType(value)
                        {
                            var removeRequisiteTypeTagLink  = "<span><a onclick=\"removeRequisiteType('#requisiteType" + requisiteTypeCount + "')\">”далить</a></span>";
                            var requisiteTypeTd             = "<td id=\"requisiteTypeTd" + requisiteTypeCount + "\"><span>" + value + "</span></td>"
                            var removeButtonTd              = "<td>" + removeRequisiteTypeTagLink + "</td>";
                            var newRequisityTypeTag         = "<tr id=\"requisiteType" + requisiteTypeCount++ + "\">" + requisiteTypeTd + removeButtonTd + "</tr>";

                            $("#requisiteTypesTable_Div").css({'display':'block'});
                            $("#requisiteTypesTableData").append(newRequisityTypeTag);
                        }

                        //удалить реквизит
                        function removeRequisiteType(id)
                        {
                            $(id).remove();
                            // если реквизитов нет, то скрываем таблицу
                            if ($("td[id*='requisiteTypeTd']").size() == 0)
                            {
                                $("#requisiteTypesTable_Div").css({'display':'none'});
                            }
                        }

                        //добавить полученные с формы реквизиты
                        function addRequisiteTypes()
                        {
                            var requisiteTypes = getElementValue("field(requisiteTypes)");
                            if (requisiteTypes != null && requisiteTypes != "")
                            {
                                var requisiteTypeArray = requisiteTypes.split(",");
                                for (var i = 0; i <requisiteTypeArray.length; i++)
                                {
                                    appendRequisiteType(requisiteTypeArray[i]);
                                }
                            }
                        }

                        var requisiteTypeCount = 0;

                        <c:if test="${isEditable}">
                            updateFieldProperties();
                        </c:if>
                         
                        $(document).ready(function(){
                            addRequisiteTypes();
                        });
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="action" value="/dictionaries/provider/fields/list.do?id=${frm.id}"/>
                    </tiles:insert>
                    <c:if test="${isEditable}">
                        <tiles:insert definition="commandButton" flush="false" operation="EditServiceProviderFieldOperation">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                            <tiles:put name="validationFunction" value="updateFieldRequisiteTypes()"/>
                        </tiles:insert>
                    </c:if>

                    <script type="text/javascript">
                        //установить в скрытое поле field(requisiteTypes) список реквизитов в виде строки через разделитель ","
                        function updateFieldRequisiteTypes()
                        {
                            //массив установленных реквизитов
                            var result = "";
                            //записываем реквизиты в строку
                            $("td[id*='requisiteTypeTd'] span").each(function(index){
                                if(index != 0)
                                    result += ",";
                                result += $(this).text();
                            });
                            setElement("field(requisiteTypes)", result);
                            return true;
                        }
                    </script>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>