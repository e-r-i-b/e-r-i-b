<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="listFormName" value="${form.metadata.name}"/>
<c:set var="currentPage" value="${form.searchPage}"/>
<c:set var="itemsPerPage" value="${form.itemsPerPage}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">

    function writeFields()
    {
        var provId = document.getElementById("recipientId").value;
        $("#nameProvider").html(providersName[provId]);
        setElement("recipient",provId);
        <%-- берем пол€ поставщика --%>
        var fieldsDecs = providers[provId];
        var table = document.getElementById('additionFields');

        var innerHTML = "";
        if (fieldsDecs != null)
        {
            for (var i = 0; i < fieldsDecs.length; i++)
            {
                var required = "";
                var input = "";
                var hint  = "";
                var readonly="";

                if(fieldsDecs[i].editable=="false")
                    readonly = "readonly";

                if (fieldsDecs[i].required == "true")
                    required = '<span class="asterisk" id="asterisk_">*</span>';

                var fieldName = fieldsDecs[i].name;
                var type = fieldsDecs[i].type;

                if((type == "string") || (type == "number") || (type == "date") ||  (type == "integer") || (type == "money"))
                {
                    var size = parseInt(fieldsDecs[i].maxLength) + 3;
                    input = '<input id="' + fieldsDecs[i].externalId + '" type="text"' + readonly
                            + ' name="field(' + fieldsDecs[i].externalId + ')" value="'+ fieldsDecs[i].defaultValue
                            + '" size="'+ (size > 50 ? 50 : size) +'" maxlength="'+ fieldsDecs[i].maxLength + '"' + (type == 'date' ?' class="dot-date-pick"' : type == "money"? ' class="moneyField"': "") + '"/>';
                }
                else if(type == 'list')
                {
                    var listValues = fieldsDecs[i].listValues;
                    var str = '<select id="'+fieldsDecs[i].externalId+'" name="field('+fieldsDecs[i].externalId+')" >';

                    for(var j = 0; j < listValues.length; j++)
                        str = str + '<option value="'+listValues[j]  + '"'
                                + (fieldsDecs[i].defaultValue == listValues[j] ? 'selected' : '') + ">" + listValues[j] + '</option>';

                    input = str + '</select>';
                }
                else if (type == 'set')
                {
                    var str = "";
                    var set = getElement("field(" + fieldsDecs[i].externalId + ")");
                    var allSetValues = fieldsDecs[i].listValues;
                    var checkedSetValuesAsString = "";

                    for (var j = 0; j < allSetValues.length; j++)
                    {
                        var checked  = "";
                        var setValue = allSetValues[j];

                        if (set != null)
                        {
                            //если значени€ уже заданы на форме
                            checkedSetValuesAsString = set.value;
                            var checkedSetValuesAsArray  = checkedSetValuesAsString.split("@");

                            for (var v = 0; v < checkedSetValuesAsArray.length; v++)
                            {
                                if (checkedSetValuesAsArray[v] == setValue)
                                {
                                    checked = "checked";
                                    break;
                                }
                            }
                        }
                        else if (fieldsDecs[i].defaultValue == setValue)
                        {
                            //если открыли впервые, то устанавлмваем значение по-умолчанию
                            checked = "checked";
                            if (checkedSetValuesAsString != "")
                                checkedSetValuesAsString = checkedSetValuesAsString + "@";
                            checkedSetValuesAsString = checkedSetValuesAsString + setValue;
                        }

                        //формируем поле сета
                        var checkBoxId = fieldsDecs[i].externalId + "_" + setValue;
                        str = str + "<input type='checkbox' id='"+ checkBoxId + "'" + checked + " onclick='javascript:changeSetValue(ensureElement(&quot;" + checkBoxId + "&quot;))'>&nbsp;" + setValue + "</input><br/>";
                    }

                    input = str + "<input type='hidden' id='field(" + fieldsDecs[i].externalId + ")' name='field("+ fieldsDecs[i].externalId + ")' value='" + checkedSetValuesAsString + "'/>";
                }

                if (fieldsDecs[i].description == '' && fieldsDecs[i].hint != '')
                {
                    hint = '&nbsp;<a href="#" onclick="payInput.openDetailClick(this); return false;"> ак заполнить это поле?</a>'+
                           '<div class="detail" style="display: none">'+fieldsDecs[i].hint+'</div>';
                }
                else if (fieldsDecs[i].description != '' && fieldsDecs[i].hint != '')
                {
                    hint = fieldsDecs[i].description +'&nbsp;<a href="#" onclick="payInput.openDetailClick(this); return false;">ѕодробнее...</a>'   +
                            '<div class="detail" style="display: none">'+fieldsDecs[i].hint+'</div>';
                }
                else
                {
                    hint = fieldsDecs[i].description;
                }

                var isVisible = (fieldsDecs[i].visible == "true");
                innerHTML = innerHTML +
                        '<div ' + (isVisible ? '' : 'style="display:none"') + ' onclick="payInput.onClick(this)">'
                        + '<div class="paymentLabel">' + fieldName + required + '</div>'
                        + '<div class="paymentValue">' + input + '<div style="display: none" class="description">' + hint
                        + '</div></div></div>';
            }
        }
        table.innerHTML = '<fieldset ' + (innerHTML ? 'style="display:block"' : 'style="display:none"')+'>' +
                                '<div class="form-row">' +
                                    innerHTML +
                                '</div>' +
                           '</fieldset>';

        initMoneyFields($("#additionFields"));
    }

    function fieldProp(id, name, description, type, defaultValue, externalId, maxLength, holderId, required, hint, visible, editable, listValues)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.defaultValue = defaultValue;
        this.externalId = externalId;
        this.maxLength = maxLength;
        this.holderId = holderId;
        this.required = required;
        this.hint    =  hint;
        this.listValues = listValues;
        this.visible = visible;
        this.editable = editable;
    }

    var providers = new Array();
    var providersName = new Array();
    var accessAutoPayment = new Array();
    <%-- массив с типом счета списани€ у поставщика --%>
    var accountTypes = new Array();
    <%-- мап с картами списани€ --%>
    var fromResourses = new Object();

    function initialize()
    {
        <c:if test="${not empty form.providers}">
            var i = 0;
            var holderId = 0;
            var firstID ="${form.recipient}";

            <c:forEach var="provider" items="${form.providers}">
               providersName[${provider.id}] = "${phiz:escapeForJS(provider.name, true)}";
               accountTypes[${provider.id}] = "${provider.accountType}";
            </c:forEach>

            if (firstID=="") firstID = ${form.providers[0].id};
            <c:forEach var="field" items="${form.fieldsDescription}">
                holderId = ${field.holderId}; // идентификатор поставщика
                <%-- если массива с пол€ми нет, значит обратились в первый раз. Ќужно создать, чтоб добавить поле --%>
                if(providers[holderId] == null)
                    providers[holderId] = new Array();

                <%-- ћассив полей --%>
                var fields  = providers[holderId];
                <%-- определ€ем конец массива --%>
                i = fields.length;

                var arrayListValues = new Array();
                <logic:iterate id="value" name="field" property="listValues" indexId="i">
                    arrayListValues[${i}]=("${value}");
                </logic:iterate>
                <%-- добавл€ем поле в конец списка --%>
                <c:set var="fieldVisibility" value="${(field.key && field.visible)}"/>
                fields[i] = new fieldProp(
                        "${field.id}",
                        "${phiz:escapeForJS(field.name, true)}",
                        "${phiz:escapeForJS(field.description, true)}",
                        "${field.type}",
                        "${phiz:escapeForJS(field.defaultValue, true)}",
                        "${field.externalId}",
                        "${field.maxLength}",
                        "${field.holderId}",
                        "${field.required}",
                        "${phiz:escapeForJS(field.hint, true)}",
                        "${fieldVisibility}",
                        "${field.editable}",
                        arrayListValues
                        )
            </c:forEach>

            <c:if test="${empty form.recipient}">
                setElement("recipient", firstID);
            </c:if>

            <%-- заполн€ем массив картам --%>
            <c:forEach items="${form.chargeOffResources}" var="resource" >
                <c:if test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                    fromResourses['${resource.code}'] = "${phiz:getCutCardNumber(resource.number)} [${phiz:escapeForJS(resource.name, true)}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}";
                </c:if>
            </c:forEach>

            writeFields();
            initDatePicker();
            <c:forEach var="field" items="${form.fields}">
                if (document.getElementsByName("field(${field.key})").length > 0)
                    setElement("field(${field.key})", "${phiz:escapeForJS(field.value, true)}");
            </c:forEach>
            updateCheckBoxField(firstID);
        </c:if>
        initMoneyFields($("#additionFields"));
    }

    <%-- обновление чекбоксов --%>
    function updateCheckBoxField(recepientId)
    {
        var providerFields = providers[recepientId];
        if (providerFields != null)
        {
            for(var i = 0; i < providerFields.length; i++ )
            {
                if(providerFields[i].type != 'set')
                    continue;
                <%-- ищем поле в котором хран€тс€ значени€ отмеченных чекбоксов --%>
                var checkboxValues = ensureElement("field(" + providerFields[i].externalId + ")");
                <%-- если оно пустое, то переходим к следующему множеству --%>
                if(checkboxValues == null || checkboxValues.value == '')
                    continue;
                <%-- значени€ выбранных чекбоксов хран€тс€ ввиде значение1@значение2@значение3 --%>
                var values = checkboxValues.value.split("@");
                <%-- находим чекбоксы которые отмечены и ставим атрибут checked --%>
                for(var j = 0; j < values.length; j++)
                    $('#' + providerFields[i].externalId + '_' + values[j]).attr("checked", 'true')
            }
        }
    }

    function changeSetValue(checkBox)
    {
        var checkBoxId    = checkBox.id;
        var checkBoxValue = checkBoxId.substr(checkBoxId.indexOf("_") + 1, checkBoxId.length);

        var set      = ensureElement("field(" + checkBoxId.substr(0, checkBoxId.indexOf("_")) + ")");
        var setValue = set.value;
        var checked  = checkBox.checked;

        //если значение этого checkBox'а не внесено в значение set'а и он сейчас выделен
        if (setValue.indexOf(checkBoxId) < 0 && checked)
        {
            if (setValue != "")
                setValue = setValue + "@";
            setValue = setValue + checkBoxValue;
        }

        //убираем выделение
        if (!checked)
        {
            if (setValue.indexOf(checkBoxValue) > 0)
                setValue = setValue.replace("@" + checkBoxValue, "");

            if (setValue.indexOf(checkBoxValue) == 0)
            {
                var replaceValue = setValue.indexOf("@") > 0 ? checkBoxValue + "@" : checkBoxValue;
                setValue = setValue.replace(replaceValue, "");
            }
        }

        set.value = setValue;
    }

    $(document).ready(function(){
         initialize();
    });
</script>

<html:hidden name="form" property="service" />
<html:hidden name="form" property="back" />
<html:hidden name="form" property="id" />
<html:hidden name="form" property="template" />
<html:hidden property="personal" name="form"/>
<input type="hidden" name="searchPage" id="searchPage" value="${form.searchPage}">

<c:choose>
    <c:when test="${form.recipient != null}">
        <html:hidden name="form" property="recipient" styleId="recipient"/>
    </c:when>
    <c:otherwise>
        <html:hidden name="form" property="recipient" value="-1" styleId="recipient"/>
    </c:otherwise>
</c:choose>

<%-- ******************************** область с данными ****************************  --%>
<div class="form-row">
    <div class="paymentLabel">
        ѕолучатель <span class="asterisk">*</span>
    </div>
    <div class="paymentValue">
        <div class="paymentInputDiv">
            <div id="nameProvider"></div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<div class="form-row">
    <div class="paymentLabel">
        ”слуга <span class="asterisk">*</span>
    </div>
    <div class="paymentValue">
        <c:choose>
            <c:when test="${empty form.providers}">
                <span style="color: red">${noProviderMessage}</span>
            </c:when>
            <c:otherwise>
                <html:select property="recipient" onchange="writeFields();" styleId="recipientId">
                    <c:forEach items="${form.providers}" var="provider" >
                        <html:option value="${provider.id}">${provider.nameService}</html:option>
                    </c:forEach>
                </html:select>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="clear"></div>
</div>
<div class="form-row">
    <div class="paymentLabel">
        ќплата с <span class="asterisk">*</span>
    </div>
    <div class="paymentValue">
        <html:select property="fromResource" styleId="fromResource" styleClass="paymentCutWidthSelect">
            <c:forEach items="${form.chargeOffResources}" var="resource" >
                <c:if test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                    <html:option value="${resource.code}">
                        <c:out value="${phiz:getCutCardNumber(resource.number)} [${resource.description}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                    </html:option>
                </c:if>
            </c:forEach>
            <c:if test="${empty form.chargeOffResources}">
                <html:option value="">
                    не найдено карт
                </html:option>
            </c:if>
        </html:select>
    </div>
    <div class="clear"></div>
</div>

<c:if test="${empty form.chargeOffResources and form.activePerson.login.lastUserVisitingMode == 'EMPLOYEE_INPUT_BY_CARD'}">
    <div class="form-row">
        <div class="paymentLabel"></div>
        <div class="paymentValue">
             арта не находитс€ по причине расхождени€ паспортных данных.
        </div>
        <div class="clear"></div>
    </div>
</c:if>

<div  id="additionFields">
    <fieldset style="display: none;">
    </fieldset>
</div>



