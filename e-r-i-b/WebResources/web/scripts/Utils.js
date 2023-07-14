function demo(event)
{
	preventDefault(event);
	alert("В демо-версии функция недоступна.");
}

var messages = new Array();// массив выведеных сообщений об ошибках

var mFF = (navigator.appName.indexOf("Netscape") != -1);
//Определение типа приложения
var isClientApp = (document.location.href.indexOf("PhizIC") != -1 || (document.location.href.indexOf("mobile") != -1 && document.location.href.indexOf("PhizIA") == -1));
var leftMenuSize = 210;
var termless = "Бессрочный";
var orderParametersCount = 3;
var RoundingMode = new RoundingMode();

function initPaymentTabIndex()
{
    $(".buttonGreen").live('keypress', function(event){clickIfEnterKeyPress(this, event);});
    $(".buttonGrey").live('keypress', function(event){clickIfEnterKeyPress(this, event);});
    $(".simpleLink").live('keypress', function(event){clickIfEnterKeyPress(this, event);});
    $(".boldLink").live('keypress', function(event){clickIfEnterKeyPress(this, event);});

    //все элементы ввода имеют самый высокий приоритет
    $("#payment .paymentValue input").attr('tabindex', 1);
    $("#payment .paymentValue textarea").attr('tabindex', 1);
    $("#payment .paymentValue select").attr('tabindex', 1);
    $("#payment .paymentValue .description a").attr('tabindex', 1);

    //кнопки вне buttonsArea
    $("#payment .clientButton .boldLink a").attr('tabindex', 50);
    $("#payment .commandButton .boldLink a").attr('tabindex', 50);

    //создать автоплатеж
    $("#makeLongOfferButton .commandButton a").attr('tabindex', 40);

    //кнопка отмена
    $("#payment .buttonsArea .clientButton .buttonGrey a").attr('tabindex', 30);

    //кнопка продолжить в различных вариациях
    $("#payment .buttonsArea .commandButton .buttonGreen a").attr('tabindex', 20);
    $("#payment .buttonsArea .clientButton .buttonGreen a").attr('tabindex', 20);
}

function clickIfEnterKeyPress(button, event)
{
    var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
    if(code != 13)
        return;
    button.onclick();
}

function doIfEnterKeyPress(event, action)
{
    var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
    if(code != 13)
        return;
    action();
}

function initData()
{
    formValues.init();
}

function getChangedFieldNames()
{
    return formValues.getChangedFieldNames();
}

/*
 * Создает на форме скрытый элемент с именем $$changedFields, в котором перечислены названия изменненых полей,
 * в качестве разделителя между ними используется ;
 */
function postChangedFieldNames()
{
    var hiddenElementValue = '';

    var changedFields = getChangedFieldNames();
    for (var i = 0; i < changedFields.length; i++)
    {
        /*
         * Cut prefix and brackets
         */
        var openingBracket = changedFields[i].indexOf('field(') + 'field('.length;
        var closingBracket = changedFields[i].indexOf(')');

        if (openingBracket != -1 && closingBracket != -1)
        {
            hiddenElementValue += changedFields[i].substring(openingBracket, closingBracket) + ";";
        }
        else
        {
            if (hiddenElementValue.indexOf(changedFields[i])!=-1)
            {
                hiddenElementValue += changedFields[i] + ";";
            }
        }
    }

    if (hiddenElementValue.length > 0)
    {
        var el = document.createElement('input');
        el.setAttribute('name', '$$changedFields');
        el.setAttribute('type', 'hidden');
        el.setAttribute('value', hiddenElementValue);
        document.forms[0].appendChild(el);
    }
}

function in_array(value, array)
{
    if (array == null || array == undefined)
        return false;
    for(var i = 0; i < array.length; i++)
    {
        if(array[i] == value) return true;
    }
    return false;
}

var formValues =
{
    // хэш в котором сохраняются первоначальные значения формы
    data: {},

    // инициализация data
    init : function()
    {
        this.data = this.getArrayValues();
    },

    // значение элемента формы
    getElementValue : function(element)
    {
        if (element.tagName == "INPUT")
        {
            if (element.type == "text" || element.type == "hidden")
                return element.value;
            if (this.isCheckboxOrRadio(element))
                return element.checked ? element.value : false;
        }
        if (element.tagName == "SELECT")
        {
            if(element.selectedIndex == -1 || element.options.length == 0) return null;
            return element.options[element.selectedIndex].value;
        }
        if (element.tagName == "TEXTAREA")
            return element.value;
    },

    // массив значений элементов формы
    getArrayValues : function()
    {
        var form = document.forms[0];
        var result = {};
        for (var i = 0; i < form.elements.length; i++)
        {
            //исключаем элементы из виджетов
            var widgetElement = $(form.elements[i]).parents(".widget");
            if(widgetElement.length != 0)
                continue;
            // если у элемента есть свойство name (уйдет на сервер)
            if (!isEmpty(form.elements[i].name))
            {
                var name = form.elements[i].name;
                var value = this.getElementValue(form.elements[i]);

                if (result[name] == undefined)
                    result[name] = value;
                else
                {
                    // если у нескольких эелементов формы одинаковое имя, то их значения добавляем в массив
                    if (!(result[name] instanceof Array))
                    {
                        var old = result[name];
                        result[name] = new Array();
                        result[name].push(old);
                    }
                    result[name].push(value);
                }
            }
        }
        return result;
    },

    /*
     *
     * Возвращает массив полей формы изменённых пользователем.
     *
     */
    getChangedFieldNames : function()
    {
        var result = new Array();
        if (this.equals())
        {
            return result;
        }

        var newValues = this.getArrayValues( document.forms[0] );
        for (var newValue in newValues)
        {
            if (this.isEmpty(this.data[newValue]) && this.isEmpty(newValues[newValue]))
            {
                continue;
            }

            if (this.isEmpty(this.data[newValue]) || this.isEmpty(newValues[newValue]))
            {
                result.push(newValue);
                continue;
            }

            if (!this.valueEquals(this.data[newValue], newValues[newValue]))
            {
                result.push(newValue);
            }
        }
        return result;
    },

    // проверка значения на пустоту
    isEmpty : function(value)
    {
        return value == null || value == undefined;
    },

    // сравнение значений формы предыдущих и новых
    // notChangedElemId - id элемента, по которому проверять не надо.
    equals : function(notChangedElemId)
    {
        var newValues = this.getArrayValues(document.forms[0]);
        var notChangedElemIds = null;
        if (!this.isEmpty(notChangedElemId))
            notChangedElemIds = notChangedElemId.split(',');
        if (this.count(newValues) != this.count(this.data))
            return false;
        for (var item in newValues)
        {
            if (this.isEmpty(this.data[item]) && this.isEmpty(newValues[item]))
                continue;
            if (this.isEmpty(this.data[item]) || this.isEmpty(newValues[item]))
                return false;
            if (!this.isEmpty(notChangedElemId) && (!this.isEmpty(getElement(item).id) && (getElement(item).id == notChangedElemId || in_array(item,notChangedElemIds))))
                continue;
            if (!this.valueEquals(this.data[item], newValues[item]))
                return false;
        }
        return true;
    },

    // сравнение значений формы предыдущих и тех что ввел пользователь. true - одинаковые, false - разные
    // elements - id элементов, которомые надо проверить.
    checkElements : function(elementsString)
    {
        var newValues = this.getArrayValues(document.forms[0]);
        if (this.isEmpty(elementsString))
            return true;
        var elements = null;
        if (!this.isEmpty(elementsString))
            elements = elementsString.split(',');
        for (var item in newValues)
        {
            if (!in_array(item, elements))
                continue;
            if (this.isEmpty(this.data[item]) && this.isEmpty(newValues[item]))
                continue;
            if (this.isEmpty(this.data[item]) || this.isEmpty(newValues[item]))
                return false;
            if (!this.valueEquals(this.data[item], newValues[item]))
                return false;
        }
        return true;
    },

    isNewDisplayed : function(elementId) {
        var element = elementId.split(/(\d)/)[0];
        var oldValue = this.data[element];
        var newValue = (this.getArrayValues(document.forms[0]))[element];
        if (oldValue instanceof Array && newValue instanceof Array)
        {
            for (var i = 0; i < oldValue.length; i++) {
                if (oldValue[i] == false && newValue[i] != false) {
                    return true;
                }
            }
            return false;
        }
        return oldValue == false && newValue != false;
    },

    // количество элементов хэша
    count : function(hash)
    {
        var result = 0;
        for (var item in hash)
            result++;
        return result;
    },

    // сравнение значений элемента (значения могут быть массивом)
    valueEquals : function(oldValue, newValue)
    {
        if (oldValue instanceof Array && newValue instanceof Array)
        {
            if (oldValue.length != newValue.length)
                return false;
            for (var i = 0; i < oldValue.length; i++)
                if (oldValue[i] != newValue[i])
                    return false;
            return true;
        }
        return oldValue === newValue;
    },

    // true - элемент checkbox или radio, false - другой
    isCheckboxOrRadio : function(element)
    {
        return element.tagName == "INPUT" && (element.type == "checkbox" || element.type == "radio");
    }
};

// notChangedElemId - id элемента, у которого не надо проверять изменились ли данные
function isDataChanged(notChangedElemId)
{
    return !formValues.equals(notChangedElemId);
}

//selectData - id элементов которые надо проверить
function selectDataChanged(selectData)
{
    return !formValues.checkElements(selectData);
}

function isMoreShown(elementId)
{
    return formValues.isNewDisplayed(elementId);
}

function reinitField()
{
    var processed = [];
	if (document.forms.length == 0 || isEmpty(formValues.data))
		return;
	var form = document.forms[0];
	for (var i = 0; i < form.elements.length; i++)
	{
        var element = form.elements[i];
        // если у элемента не заполнено свойство name, то его не обрабатываем
        if (formValues.data[element.name] == undefined)
            continue;
        // проверка на то, что элемент с таким именем уже обработан
        if (!formValues.isEmpty(processed[element.name]))
            continue;
        processed[element.name] = true;
        var value = formValues.data[element.name];
        // если несколько элементов с одним названием
        if (value instanceof Array)
        {
            var currentValue = 0;
            $("[name=\""+element.name+"\"]").each(function() {
                if (formValues.isCheckboxOrRadio(this))
                {
                    this.checked = false;
                    // поиск и обработка элемента по value
                    if ( $.inArray(this.value, value) != -1 )
                         this.checked = true;

                }
                else if (this.tagName == "SELECT")
                {
                    for (var i = this.options.length-1; i >= 0; i--)
                        if ($.inArray(this.options[i].value, value) != -1)
                        {
                            this.selectedIndex = i;
                            break;
                        }
                }
                else
                    this.value = value[currentValue];
                currentValue++;
            });
        }
        else
        {
            // обработка элемента с одинаковым названием
            if (formValues.isCheckboxOrRadio(element))
                element.checked = element.value == value;
            else if (element.tagName == "SELECT")
            {
                for (var j = element.options.length - 1; j >= 0; j--)
                    if (element.options[j].value == value)
                    {
                        element.selectedIndex = j;
                        break;
                    }
            }
            else
                element.value = value;
        }
	}
}

/** функция возвращает первую подстроку (до пробела)
    строки str; подстрока при этом не длиннее len **/
function breakString(str, len)
{
	if (str.length == 0)
		return '<br/>';
	if (str.length <= len)
	{
		return str;
	}
	var subLen = 0;
	var lastLen = subLen;
	do
	{
	 lastLen = subLen;
	 subLen =  str.indexOf(" ",subLen+1);
		if ((lastLen == subLen) || ((subLen == -1) && (lastLen == 0)))
			subLen = len;
		if ((subLen == -1) && (lastLen != 0))
			break;
	}
	while (subLen < len);
	if (subLen == len)
	{
		return str.substring(0,subLen);
	}
	return str.substring(0,lastLen);
}

function preventDefault(event)
{
   if  (event!=null)
   {
        try
        {
            if (event.preventDefault)
            {
                event.preventDefault();
            }
            // для IE<9
            else
            {
                event.returnValue=false;
            }
        }
        catch(e)
        {
        }
   }
}

function getRadioValue(radio)
{
	if (radio.length > 0)
	{
		for (var i = 0; i < radio.length; i++)  if (radio[i].checked) return radio[i].value;
		return null;
	}
	else  return (radio.checked ? radio.value : null);
}

function disableOrEnableRadio(radio, disable)
{
	if (radio.length > 0)
	{
		for (var i = 0; i < radio.length; i++)
            radio[i].disabled = disable;
	}
	else  radio.disable = disable;
}

function setRadioValue(radio, val)
{
	if (radio.length > 0)
	{
		for (i = 0; i < radio.length; i++)
		{
			if (radio[i].value == val)
			{
				radio[i].checked = true;
				break;
			}
		}
	}
	else if (radio.value == val) radio.checked = true;
}

function clearRadioChecked(radio)
{
    if (radio.length > 0)
    {
        for (i = 0; i < radio.length; i++)
            radio[i].checked = false;
    }
    else radio.checked = false;
}

function getElement(name)
{
	try
	{
		var el = document.getElementsByName(name);
		return el[0];
	}
	catch (e)
	{
		return null;
	}
}

function getField(name)
{
   return getElement("field("+name+")");
}

function setSelectBoxValue(name, val)
{
	var el = getElement(name);
	for (var i = 0; i < el.options.length; i++)
	{
		if (el.options[i].value == val)
		{
			el.options[i].selected = true;
			break;
		}
	}
}

function getElementValue(name)
{
	try
	{
		var element = getElement(name);
        if (element.type == "checkbox")
            return element.checked.toString();
        else
            return element.value;
	}
	catch (e)
	{
		return "";
	}
}

function setElement(name, val)
{
  try {
      $('*[name='+name+']').get(0).value = val;
  }
  catch (e) {alert(e.description);}
}

function setElementByContainName(name, val)
{
  try {
      $('*[name*='+name+']').get(0).value = val;
  }
  catch (e) {alert(e.description);}
}

function setField(name, val)
{
  setElement("field(" + name + ")", val);
}

function addOrderParameter(parameter, direction)
{
    if(direction == null)
        direction = 'ASC';
    var newOrderParameter = new Object();
    newOrderParameter.value = parameter;
    newOrderParameter.direction = direction;

    var parameters = new Array();
    parameters.push(newOrderParameter);

    for(i = 0; i < orderParametersCount-1; i++)
    {
        var parameter = getOrderParameter(i);
        if(parameter != null)
            parameters.push(parameter)
    }

    for(i = 0; i< parameters.length; i++)
    {
        if(getElement("$$order_parameter_" + i) != null)
        {
            setElement("$$order_parameter_" + i,parameters[i].value);
            setElement("$$order_parameter_" + i + "_direction",parameters[i].direction);
        }
        else
        {
            addElementToForm("$$order_parameter_" + i,parameters[i].value);
            addElementToForm("$$order_parameter_" + i + "_direction",parameters[i].direction);
        }
    }
}

function deleteOrderParameter(deletedParameter)
{
    var parameters = new Array();
    for(i = 0; i < orderParametersCount; i++)
    {
        var parameter = getOrderParameter(i);
        if(parameter != null)
            parameters.push(parameter)
    }

    for(i = 0; i< parameters.length; i++)
        if(parameters[i].value == deletedParameter)
            parameters.splice(i,1);

    for(i = 0; i< parameters.length; i++)
    {
        setElement("$$order_parameter_" + i,parameters[i].value);
        setElement("$$order_parameter_" + i + "_direction",parameters[i].direction);
    }
    for(i = parameters.length; i < orderParametersCount; i++)
    {
        if(getElement("$$order_parameter_" + i) != null)
        {
            setElement("$$order_parameter_" + i,null);
            setElement("$$order_parameter_" + i + "_direction",null);
        }
    }

}

function getOrderParameter(number)
{
    var orderParameter = getElement("$$order_parameter_"+number);
    var orderParameterDirection = getElement("$$order_parameter_"+number + "_direction");
    if(orderParameter != null)
    {
        var parameter = new Object();
        parameter.value = orderParameter.value;
        parameter.direction = orderParameterDirection.value;
        return parameter;
    }
    else
        return null;
}

//TODO удалиить как несоответствующую Стандартам Интерфейса (использование частного имени)
function callUserOperation(event, id, operation, confirm)
{
	var ids = document.getElementsByName("selectedIds");
	for (var i = 0; i < ids.length; i++)
	{
		ids[i].checked = (ids[i].value == id);
	}
	callOperation(event, operation, confirm);
}

// TODO вынести отсюда как несоответствующую Стандартам Интерфейса (простой alert рулит)
function groupError(msg)
{
	if (msg == "" || msg == null) alert("Выберите пользователей");
	else alert(msg);
	return false;
}

function getSelectedQnt(name)
{
	var ids = document.getElementsByName(name);
    var qnt = 0;
	for (var i = 0; i < ids.length; i++)  if (ids[i].checked) qnt++;
	return qnt;
}

//TODO удалиить как несоответствующую Стандартам Интерфейса
function callGroupOperation(event, operation, confirm, msg)
{
	preventDefault(event);
	if (getSelectedQnt() == 0) return groupError(msg);
	callOperation(event, operation, confirm);
}

function checkSelection(checkBoxName, msg)
{
	if (getSelectedQnt(checkBoxName) == 0){
		clearLoadMessage();
		return groupError(msg);
	}

	return true;
}

function checkOneSelection(checkBoxName, msg)
{
	if (getSelectedQnt(checkBoxName) != 1){
		clearLoadMessage();
		return groupError(msg);
	}
	return true;
}

function checkOneSelectionOrNothing(checkBoxName, msg)
{
    if (getSelectedQnt(checkBoxName)==0)
        return true;
    return checkOneSelection(checkBoxName, msg);
}

function initAccessDescription()
{
	var ids = document.getElementsByName("selectedIds");
	for (var i = 0; i < ids.length; i++)
	{
        setAccessDescription(ids[i]);
	}
}

function setAccessDescription(el)
{
	getLabel(el).innerHTML = (el.checked ? "разрешен" : "запрещен");
}

function setEmptyAction(event)
{
	preventDefault(event);
	document.forms.item(0).action = "";
	return true;
}

function getElementFromCollection(object,elementName)
{
	for(var i=0;i<object.length;i++)
	{
		if(object[i].name == elementName)
			return object[i];
	}
	return null;
}

function callOperation(event, operation, confirm)
{
	var frm = document.forms.item(0);
	preventDefault(event);

	if (confirm != null && confirm != '' && ! window.confirm(confirm))
		return false;

	if (frm.onsubmit != null && !frm.onsubmit())
		return false;

	if(window.clearMasks != null)
	{
		try { clearMasks(event); } catch (e) {}
	}

	var operationField = getElementFromCollection(frm.elements,'operation');

	if (operationField == null)
	{
		addField('hidden', 'operation', operation);
	}
	else
	{
		operationField.value = operation;
	}

    if (typeof customPlaceholder !== 'undefined')
        customPlaceholder.clearPlaceholderVal();
	frm.submit();

	return true;
}

var Message = null;
var ErrorMessage = null;

function showMessage()
{
	if (ErrorMessage != null) alert(ErrorMessage);
	if (Message != null) alert(Message);
}

function turnSelection(isSelectAll, listName, event)
{
	var frm = document.forms.item(0);
	var ids = document.getElementsByName(listName);
	turnSelectionAlg(ids, isSelectAll, event);
}

function turnSelectionAlg(ids, isSelectAll, event)
{
	for (var i = 0; i < ids.length; i++)
	{
		ids[i].checked = isSelectAll;
		try
		{
			ids[i].onclick(ids[i].id)
		}
		catch (e)
		{
		}
		preventDefault(event);
	}
}

//TODO убрать дефолтные параметры
function switchSelection(flag, list)
{
	flag = (flag == null ? "isSelectAll" : flag);
	list = (list == null ? "selectedIds" : list);

	flag = ensureElementByName(flag);

	turnSelection(flag.checked, list);
}

//TODO event не нужен
function resetForm(event)
{
	preventDefault(event);
	document.forms.item(0).reset();
}

function addParam2List(params, name, addName)
{
	var val = getElementValue(name);
	if (val.length > 0)
	{
		if (params.length > 0) params += "&";
		params += (addName == null ? name : addName);
		params += "=" + trim(val);
	}
	return params;
}

function getFirstSelectedId(name)
{
	if (name == null) name = "selectedIds";
	var ids = document.getElementsByName(name);
	for (i = 0; i < ids.length; i++) if (ids[i].checked) return ids[i].value;
	return null;
}

function buttFocus(buttId)
{
	var button;
	if ((typeof buttId) == "string")
		button = document.getElementById(buttId);
	else
		button = buttId;

	button.className = "butMenu butMenuFocus";
}

function buttUnFocus(buttId)
{
	var button;
	if ((typeof buttId) == "string")
		button = document.getElementById(buttId);
	else
		button = buttId;

	button.className = "butMenu";
}

function linkUnFocus(link, id)
{
	document.getElementById("img" + id).src = document.imgPath + "iconSm_circle.gif";
	document.getElementById("spanA" + id).className = "menuInsertText";
	link.className = "linkUnFocus";
}

function linkFocus(link, id)
{
	document.getElementById("img" + id).src = document.imgPath + "iconSm_circleRed.gif";
	document.getElementById("spanA" + id).className = "menuLinkText";
	link.className = "linkFocus";
}

function cancelEvent(event)
{
	try
	{
		event.cancelBubble = true;
		event.returnValue = false;
	}
	catch(e)
	{
		event.preventDefault();
	}
}

function trim(val)
{
    if (val == undefined || val == null)
        return "";
	var s = new String(val);
	s = s.replace(/^\s+/, "");
	return  s.replace(/\s+$/, "");
}

function clearOptions(oSelect)
{
	try
	{
		var N = oSelect.options.length;
		for (var i = 0; i < N; i++) oSelect.remove(0);
	}
	catch (e)
	{
	}
}

function addOption(oSelect, _value, _text, isSelected)
{
	var el = document.createElement("option");
	el.value = _value;
	el.text = _text;
	el.selected = isSelected;
	oSelect.options.add(el);
}
function selectRow(row, name)
{
	var nodes = row.cells[0].childNodes;
	var radio;
	if (name == null)name = 'selectedId';
	for (i = 0; i < nodes.length; i++)
	{
		radio = nodes.item(i)
		if (radio.name == name)
		{
			radio.checked = true;
		}
	}
}

function setBankInfo(bankInfo)
{
	setElement('bankName', bankInfo["name"]);
	setElement('receiverBIC', bankInfo["BIC"]);
	setElement('receiverCorAccount', bankInfo["account"]);

}

function setOperationCodeInfo(operationCodeInfo)
{
	setElement('operationCode', operationCodeInfo["operationCode"]);
}

function setReceiverInfo(receiverInfo)
{
	setElement("receiverName", receiverInfo["receiverName"]);
	setElement("receiverINN", receiverInfo["receiverINN"]);
	setElement("receiverKPP", receiverInfo["receiverKPP"]);
	setElement("receiverAccount", receiverInfo["receiverAccount"]);
	setBankInfo(receiverInfo);
}

function openSelectReceiverWindow()//TODO is it need?
{
	var params = "";
	params = addParam2List(params, "receiverName", "nameFilter");
	params = addParam2List(params, "receiverAccount", "accountFilter");
	params = addParam2List(params, "bankName", "bankNameFilter");
	if (params.length > 0) params = "&amp;" + params;
	window.open(document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo&kind=P' + params,
			'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
}

function openWindow(event, winPath, winName, winStyle)
{
	preventDefault(event);
	if (winStyle == null) winStyle = "resizable=1,menubar=1,toolbar=1,scrollbars=1";
	return window.open(winPath, winName, winStyle)
}

function openDialog(event, width, height, url)
{
	var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
	             ", width=" + width +
	             ", height=" + height +
	             ", left=" + (screen.width - width) / 2 +
	             ", top=" + (screen.height - height) / 2;
	var pwin = openWindow(event, url, "dialog", winpar);
	pwin.focus();
}

function addHidden(vals, name)
{
	var frm = document.forms.item(0);
	for (i = 0; i < vals.length; i++)
	{
		if (vals[i].checked)
		{
			var el = document.createElement("input");
			el.name = name;
			el.type = "hidden";
			el.value = vals[i].value;
			frm.appendChild(el);
		}
	}
}
function hiddenTab(count)
{
	try
	{
		if (count == 0)
		{
			document.getElementById('headTabAcc').style.display = "none";
		}
		else
		{
			document.getElementById('messageTab').style.display = "none";
		}
	}
	catch (e)
	{
	}
}

function hideTitle(id)
{
	if (id == null) id = "tableTitle";
	try
	{
		document.getElementById(id).style.display = "none";
	}
	catch(e)
	{
	}
}

/* При нажатии Enter кликает по дефолтной кнопке формы */
function onEnterKey(e)
{
	var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;

    if(kk == 13)
    {
        var cmdButton = findDefaultCommandButton();
        var cltButton = findDefaultClientButton();

        if(cmdButton != null)
        {
           clickToButton(e, cmdButton);
        }
        else if(cltButton != null)
        {
           clickToButton(e, cltButton);
        }

        saveOffset();
    }
}

function clickToButton(e, button)
{
     button.click();
      // для DOM-совместимых браузеров
     if (e.preventDefault)
     {
       e.preventDefault();
     }
    // для IE<9
     else
     {
       e.returnValue = false;
     }
}


function hideOrShowMakeLongOfferButton(hide)
{
    $(document).ready(function(){
        var elem = ensureElement("makeLongOfferButton");
        if (elem !=null)
        hideOrShow(elem, hide);
    });

}

function ensureElement(elem)
{
	if (typeof(elem) == 'string')
	{
		elem = document.getElementById(elem);
	}
	return elem;
}

function ensureElementByName(elem)
{
	if (typeof(elem) == 'string')
	{
		elem = getElement(elem);
	}
	return elem;
}

function getLabel(elem)
{
	elem = ensureElement(elem);
	return document.getElementById(elem.id + '_lbl');
}

function hideOrShow(elem, hide)
{
	elem = ensureElement(elem);

	if (hide == null)
		hide = elem.style.display != "none";

    if(elem!=null){
	    elem.style.display = hide ? "none" : "";
    }
}
/* приметяется ко всем строкам у который id.match(idMatch) */
function hideOrShowTableRows(table, hide, idMatch)
{
	table = ensureElement(table);
	var rows = table.rows;

	for (var i = 0; i < rows.length; i++)
	{
		var row = rows[i];
		if (row.id.match(idMatch))
			hideOrShow(row, hide);
	}
}

/* приметяется ко всем Body у который id.match(idMatch) */
function hideOrShowTableBodies(table, hide, idMatch)
{
	table = ensureElement(table);
	var rows = table.tBodies;

	for (var i = 0; i < rows.length; i++)
	{
		var row = rows[i];
		if (row.id.match(idMatch))
			hideOrShow(row, hide);
	}
}

function clearFields(obj)
{
	if (obj != null)
	{
		var length = 0;
		if (obj.childNodes != null)
		{
			length = obj.childNodes.length;
		}
		if (length == 0)
		{
			if (obj.tagName == "INPUT")
			{
				if (obj.type == "text")
				{
					obj.value = "";
				}
				if (obj.type == "checkbox") obj.checked = false;
			}
		}
		else
		{
			if (obj.tagName == "SELECT")
			{
				for (var i = 1; i < obj.options.length; i++)
				{
					obj.options[i].selected = false;
				}
				obj.options[0].selected = true;
			}
       		for (var i = 0; i < length; i++)
			{
				clearFields(obj.childNodes[i]);
			}
		}
	}
}

function clearFilter(event)
{
	var filterForm = document.getElementsByTagName("form")[0];
	filterForm.reset();
	cancelEventCrossBrowser(event);
}

function switchFilter(event)
{
	var filter = document.getElementById("filterFields");
	var filterButton = document.getElementById("showFilterButton");
	var isOff = (filter.style.display == "none");

	filter.style.display = (isOff ? "block" : "none");
	cancelEventCrossBrowser(event)
}

function Str2Date(sDate)
{
	var day, month, dt;
	day = parseInt(sDate.substr(0, 2), 10);
	month = parseInt(sDate.substr(3, 2), 10);
	year = parseInt(sDate.substr(6), 10);
	if (isNaN(day) || isNaN(month) || isNaN(year)) return null;
	if ((day > 31) || (day < 01) || (month > 12) || (month < 01))  return null;
	dt = new Date(month + "/" + day + "/" + year);
	if (dt.getDate() != day) return null;
	return dt;
}

function Str2Time(sTime)
{
    var hour, min, hm;
    hour = parseInt(sTime.substr(0, 2), 10);
    min = parseInt(sTime.substr(3, 2), 10);
    if (isNaN(hour) || isNaN(min) ) return null;
    if ((hour > 24) || (hour < 0) || (min > 60) || (min < 0))  return null;
    hm = new Date(0,0,0,hour,min);
    return hm;
}

var sumErr;

function checkCharset(sPattern, sCheck)
{
	var i,ch;
	for (i = 0; i < sCheck.length; i++)
	{
		ch = sCheck.charAt(i);
		if (sPattern.indexOf(ch) == -1) return false;

	}
	return true;
}

function checkNumber(id, name)
{
	var s = getElementValue(id);
	var i = parseInt(s, 10);
	if (s.length == 0) return true;
	if (!checkCharset("0123456789", s) || isNaN(i))
	{
		alert('Поле "' + name + '": может содержать только цифры');
		return false;
	}
	return true;
}

function checkSum(id, name, checkRange)
{
	var s = getElementValue(id);
	var f = parseFloat(s);
	sumErr = "";
	if (s == "") return 0;
	if (!checkCharset("0123456789.", s) || isNaN(f))
	{
		sumErr = 'Поле "' + name + '": сумма может содержать только цифры и точку';
		return null;
	}
	if (checkRange && (f < 0.01 || f > 999999999.99))
	{
		sumErr = 'Поле "' + name + '": значение суммы должно быть в диапазоне 0.01 - 999999999.99';
		return null;
	}
	return f;
}

function checkSumRange(id1, id2, name1, name2)
{
	var sErr = "";
	s1 = checkSum(id1, name1, true);
	if (s1 == null) sErr += "\n" + sumErr;
	s2 = checkSum(id2, name2, true);
	if (s2 == null) sErr += "\n" + sumErr;
	if (s1 != null && s2 != null)
		if (s1 > 0 && s2 > 0)
		{
			if (s2 < s1)
				sErr += "\nКонечная сумма должна быть больше или равна начальной!";
		}
	if (sErr != "")
	{
		alert(sErr);
		return false;
	}
	return true;
}

/**
 * Функция для проверки корректности периода С-ПО
 * @param id1  id элемента "с"
 * @param id2  id элемента "по"
 * @param name1  текст ошибки для элемента "c"
 * @param name2  текст ошибки для элемента "по"
 * @param date_template шаблон валидации даты (templateObj)
 */
function validatePeriod(id1, id2, name1, name2,date_template)
{
    var sErr = "";
    //1 валидатор формата
    var sourceDate = document.getElementById(id1).value;
    var closeDate =  document.getElementById(id2).value;

    if (!date_template.validate(sourceDate))
        sErr += "\n"+name1;

    if (!date_template.validate(closeDate))
        sErr += "\n"+name2;

   var dt1 = Str2Date(sourceDate);
   var dt2 = Str2Date(closeDate);

   if (dt2 < dt1)
    sErr += "\nКонечная дата должна быть больше или равна начальной!";

    return sErr;
}

/**
 * Функция для проверки корректности периода С-ПО.
 * @param fromDate начальная дата.
 * @param toDate конечная дата.
 */
function validateDatePeriod(fromDate, toDate)
{
    var FROM_DATE_ERROR_MESSAGE = "Пожалуйста, укажите дату начала периода в формате ДД/ММ/ГГГГ.";
    var TO_DATE_ERROR_MESSAGE   = "Пожалуйста, укажите дату окончания периода в формате ДД/ММ/ГГГГ.";
    var ERROR = "Конечная дата должна быть больше или равна начальной!";

    var dt1 = Str2Date(fromDate), dt2 = Str2Date(toDate);

    if ((dt1 == null) || !templateObj.ENGLISH_DATE.validate(fromDate))
    {
        addError(FROM_DATE_ERROR_MESSAGE);
        return false;
    }

    if ((dt2 == null) || !templateObj.ENGLISH_DATE.validate(toDate))
    {
        addError(TO_DATE_ERROR_MESSAGE);
        return false;
    }

    if (dt2 < dt1)
    {
        addError(ERROR);
        return false;
    }

    removeError(FROM_DATE_ERROR_MESSAGE);
    removeError(TO_DATE_ERROR_MESSAGE);
    removeError(ERROR);
    return true;
}

/**
 * Функция для проверки корректности периода С-ПО.
 * @param fromTime начальное время.
 * @param toTime конечное время.
 */
function validateTimePeriod(fromTime, toTime)
{
    var FROM_DATE_ERROR_MESSAGE = "Пожалуйста, укажите время начала в формате ЧЧ:ММ.";
    var TO_DATE_ERROR_MESSAGE   = "Пожалуйста, укажите время завершения ЧЧ:ММ.";
    var ERROR = "Конечное время должно быть больше или равно начальному!";

    var dt1 = Str2Time(fromTime), dt2 = Str2Time(toTime);

    if ((dt1 == null) || !templateObj.SHORT_TIME.validate(fromTime))
    {
        alert(FROM_DATE_ERROR_MESSAGE);
        return false;
    }

    if ((dt2 == null) || !templateObj.SHORT_TIME.validate(toTime))
    {
        alert(TO_DATE_ERROR_MESSAGE);
        return false;
    }

    if (dt2 < dt1)
    {
        alert(ERROR);
        return false;
    }
    return true;
}

function checkPeriod(id1, id2, name1, name2, isRequierd)
{
	var d1,d2, dt1 = null,dt2 = null;
	var sErr = "";

	d1 = getElementValue(id1);
	if (d1 != DATE_TEMPLATE && d1 != "")
	{
		dt1 = Str2Date(d1);
		if (dt1 == null)
		{
			if (d1.substr(2, 1) == "." && d1.substr(5, 1) == ".")
			{
				if (d1.substr(0, 2) > 31 || d1.substr(0, 2) < 01 || d1.substr(3, 2) > 12 || d1.substr(3, 2) < 01)
					sErr += '\nПоле "' + name1 + '":  задана некорректная дата!';
			}
			else
				sErr += '\nПоле "' + name1 + '":  задайте дату в формате ДД.ММ.ГГГГ!';
		}
	}
	d2 = getElementValue(id2);
	if (d2 != DATE_TEMPLATE && d2 != "")
	{
		dt2 = Str2Date(d2);
		if (dt2 == null)
		{
			if (d2.substr(2, 1) == "." && d2.substr(5, 1) == ".")
			{
				if (d2.substr(0, 2) > 31 || d2.substr(0, 2) < 01 || d2.substr(3, 2) > 12 || d2.substr(3, 2) < 01)
					sErr += '\nПоле "' + name2 + '":  задана некорректная дата!';
			}
			else
				sErr += '\nПоле "' + name2 + '":  задайте дату в формате ДД.ММ.ГГГГ!';
		}
	}
	if (isRequierd && ((d1 == DATE_TEMPLATE) || (d2 == DATE_TEMPLATE) || (d1 == "") || (d2 == "")))
	{

		sErr += "\nЗадайте обе даты!"
	}
	if ((dt1 != null) && (dt2 != null))
	{
		if (dt2 < dt1)
			sErr += "\nКонечная дата должна быть больше или равна начальной!"
	}
	if (sErr != "")
	{
		clearLoadMessage();
		alert(sErr);
		return false;
	}
	return true;
}

function buildList(event)
{
	preventDefault(event);
	if (checkData())
	{
		clearMasks(event);
		callOperation(event, '');
	}
}

function checkPayment(event)
{
	if(window.checkData == null)
	{
		return true;
	}
	else if (checkData())
    {
	    if (window.clearMasks != null)
	    {
	       clearMasks(event);
	    }
	    return true;
    }
    return false;
}

var days = ["день", "дня", "дней"];
var months = ["месяц", "месяца", "месяцев"];
var years = ["год", "года", "лет"];

function formatPeriod(period)
{
	var yy = new Number(period.substring(0, 2));
	if (yy > 0)
	{
		return yy + " " + selectSklonenie(yy, years);
	}

	var mm = new Number(period.substring(3, 5));
	if (mm > 0)
	{
		return mm + " " + selectSklonenie(mm, months);
	}

	var dd = new Number(period.substring(6, period.length));
	if (dd > 0)
	{
		return dd + " " + selectSklonenie(dd, days);
	}
	if (yy == 0 && mm == 0 && dd == 0)
	{
		return termless;
	}
	throw new Error("Неправильный формат [period]: " + period);
}

function formatTwoPeriods(period, period2)
{
	if (period2 != null && period2 != '' && period2 != '00-00-000' && period != period2)
	{
		var yy = new Number(period.substring(0, 2));
		var mm = new Number(period.substring(3, 5));
		var dd = new Number(period.substring(6, period.length));
		if (yy == 0 && mm == 0 && dd == 0)
		{
			return termless;
		}
		else
		{
			if (yy > 0)
			{
				var yy2 = new Number(period2.substring(0, 2));
				if (yy > yy2) throw new Error("Неправильно заданный период [period1]:" + period + ", [period2]:" + period2);
				return yy + " - " + yy2 + " " + selectSklonenie(yy2, years);
			}
			else if (mm > 0)
			{
				var mm2 = new Number(period2.substring(3, 5));
				if (mm > mm2) throw new Error("Неправильно заданный период [period1]:" + period + ", [period2]:" + period2);
				return mm + " - " + mm2 + " " + selectSklonenie(mm2, months);
			}
			else if (dd > 0)
			{
				var dd2 = new Number(period2.substring(6, period2.length));
				if (dd > dd2) throw new Error("Неправильно заданный период [period1]:" + period + ", [period2]:" + period2);
				return dd + " - " + dd2 + " " + selectSklonenie(dd2, days);
			}
			else throw new Error("Неправильный формат [period]: " + period);
		}
	}
	else
	{
		return formatPeriod(period);
	}
}

var indexes = [2, 0, 1, 1, 1, 2, 2, 2, 2, 2];
function selectSklonenie(num, descriptions)
{
	var index = Math.floor((num % 100) / 10) == 1 ? 0 : num % 10;
	return descriptions[ indexes[index] ];
}

function periodToDays(period)
{
	var yy = new Number(period.substring(0, 2));
	if (yy != 0)
	{
		return yy * 365;
	}

	var mm = new Number(period.substring(3, 5));
	if (mm != 0)
	{
		return mm * 30;
	}

	var dd = new Number(period.substring(6, period.length));
	if (dd != 0)
	{
		return dd;
	}

	throw new Error("Неправильный формат [period]: " + period);
}

Array.prototype.contains = function(item, func)
{
	if (func == null)
		func = function(val1, val2)
		{
			return val1 == val2;
		};

	for (var i = 0; i < this.length; i++)
	{
		if (func(this[i], item))
			return true;
	}

	return false;
}

function formatMoney(value)
{
	if (value == null || value == '')
		value = 0;

	return new Number(value).toFixed(2);
}

function monthToStringOnly(str1)
{
	var month;
	var date;
	date = "";
	month = str1.substring(3, 5) - 1;
	date = date + monthToStringByNumber(month);
	return date;
}

function monthToStringByNumber(month)
{
	var monthStr = "";

	switch (month)
	{
		case 0:monthStr = "января";break;
		case 1:monthStr = "февраля";break;
		case 2:monthStr = "марта";break;
		case 3:monthStr = "апреля";break;
		case 4:monthStr = "мая";break;
		case 5:monthStr = "июня";break;
		case 6:monthStr = "июля";break;
		case 7:monthStr = "августа";break;
		case 8:monthStr = "сентября";break;
		case 9:monthStr = "октября";break;
		case 10:monthStr = "ноября";break;
		case 11:monthStr = "декабря";break;
		default:monthStr = "";break;
	}
	return monthStr;
}


// TODO Deprecated (существует regex)
function StrCheck(sPattern, sCheck)
{
	var i,ch;
	for (i = 0; i < sCheck.length; i++)
	{
		ch = sCheck.charAt(i);
		if (sPattern.indexOf(ch) == -1) return false;
	}
	return true;
}

function saveAndRedirectOld(url, forcePostback)
{
	if (!forcePostback && !isDataChanged())
		return true;

	if (!confirm("Сохранить изменения и перейти на другую страницу?"))
		return false;

	findNavigationButton().click(url);

	return false;
}

//при изменении данной функции необходимо посмотреть и на subMenuClickCallBack (/WEB-INF/jsp/mail/leftMenu.jsp)
function saveAndRedirect(fullUrl, url, forcePostback)
{
	if (!forcePostback && !isDataChanged())
        {
			loadNewAction('lmi'+url,'lmInset');
			return window.location=fullUrl;
		}

	if (!confirm("Сохранить изменения и перейти на другую страницу?"))
    {
        //при нажатии кнопки "Отмена" необходимо оставаться на текущей странице и не сохранять изменения
        clearLoadMessage();
        reinitField();
        return false;
    }


	findNavigationButton().click(url);

	return false;
}

function showOrHideItems(id)
{
	var tab = document.getElementById(id);
	tab.style.display = ( tab.style.display == "block" ? "none" : "block" );
}

/**
 * Добавление поля на форму с проверкой существования этого поля.
 * @param fieldType тип поля.
 * @param fieldName имя поля.
 * @param fieldValue значение поля.
 */
function addFieldWithCheck(fieldType, fieldName, fieldValue)
{
	if (document.getElementById)
	{
		var el = $("input[name='"+ fieldName+ "']")[0];
		if (el)
		{
			if (document.all)
			{ 
				el.type = fieldType;
				el.name = fieldName;
				el.value = fieldValue;
			}
			else
			{
				el.setAttribute('type', fieldType);
				el.setAttribute('name', fieldName);
				el.setAttribute('value', fieldValue);
			}
		}
		else
		{
			addField(fieldType, fieldName, fieldValue);
		}
	}
}

function addField(fieldType, fieldName, fieldValue)
{
	if (document.getElementById)
	{
		var input = document.createElement('INPUT');
		if (document.all)
		{ // what follows should work
			// with NN6 but doesn't in M14
			input.type = fieldType;
			input.name = fieldName;
			input.value = fieldValue;
		}
		else if (document.getElementById)
		{ // so here is the
			// NN6 workaround
			input.setAttribute('type', fieldType);
			input.setAttribute('name', fieldName);
			input.setAttribute('value', fieldValue);
		}
		document.forms[0].appendChild(input);
	}
}

function addEventListenerEx(elem, eventType, delegate, capture)
{
	if (typeof(elem) == 'string')
	{
		if (!(elem = document.getElementById(elem)))
		{
			var msg = elem + ' element not found';
			alert(msg);
			throw msg;
		}
	}

	if (elem.addEventListener)
		elem.addEventListener(eventType, delegate, capture);
	else if (elem.attachEvent)
		elem.attachEvent('on' + eventType, delegate);
	else
		elem['on' + eventType] = delegate;
}

function getElementTextContent(elem)
{
	if (mFF)
	{
		return elem.textContent;
	}
	else
	{
		return elem.innerText;
	}
}

// для каждого элемента массива (array) вызывается действие (action) если элемет удвлетворяет условиям (match)
function forEach(array, action, match)
{
	for (var i = 0; i < array.length; i++)
	{
		var elem = array[i];
		if (match == null || match(elem))
			action(elem);
	}
}

//определение ширины и высоты клиентской (рабочей) области окна браузера
function getClientWidth()
{
  return document.compatMode=='CSS1Compat' && !window.opera?document.documentElement.clientWidth:document.body.clientWidth;
}

function getClientHeight()
{
  return document.compatMode=='CSS1Compat' && !window.opera?document.documentElement.clientHeight:document.body.clientHeight;
}

function cancelEventCrossBrowser(event)
{
	if (mFF) preventDefault(event);
	else cancelEvent(event);
}
//скрытие колонок списка
function hideColumn(event,colNumber,ref,tableName)
{
	var mainContent = $("div[id='RowContent"+colNumber+"']");
	var additionalContent = $("div[id='HideContent"+colNumber+"']");
	if(mainContent[0].style.display ==="none")
	{
		ref.innerHTML = ("<image src='" + document.imgPath + "iconSm_triangleLeft.gif' border='0'/>")
		mainContent.show();
		additionalContent.hide();
	}
	else
	{
		ref.innerHTML = ( "<image src='" + document.imgPath + "iconSm_triangleRight.gif' border='0'/>");
		mainContent.hide();
		additionalContent.show();
	}
	cancelEventCrossBrowser(event);
}
//всплывающие окна, привязывающиеся к координатам родительского элемента
function getElementPosition(elemId)
{
   var elem = document.getElementById(elemId);
   var w = elem.offsetWidth;
   var h = elem.offsetHeight;
   var l = 0;
   var t = 0;
   while (elem)
   {
     l += elem.offsetLeft;
     t += elem.offsetTop;
     elem = elem.offsetParent;
   }

    return {"left":l, "top":t, "width": w, "height":h};
}

// поиск родителя родителя по позициорированию
function getParentOffset(elemId)
{
   var parent = document.getElementById(elemId).parentNode;
   while (parent.tagName.toLowerCase() != 'html')
   {
       if($(parent).css("position") == "relative")
           return parent;

       parent = parent.parentNode;
   }

   return null;
}

function showLayerFull(parentId, layerId, cursor,event){
	if ((document.getElementById(layerId) && document.getElementById(parentId)) != null)
	{
		var x=event.clientX;
		var y=event.clientY;
		var parentElemPosition= getElementPosition(parentId);
		var layer = document.getElementById(layerId);
		//<%--это внешний <div> из workspace.jsp. Если он есть, то всплывающее окно в IE привязывается к его координатам--%>
		if (document.getElementById("workspaceDiv")!=null && navigator.appName=='Microsoft Internet Explorer')
		{
			var divElementPosition = getElementPosition("workspaceDiv");
			x -= divElementPosition.left;
			y  -= divElementPosition.top;
		}
		var s = screen.width-event.screenX;
		var c = document.body.offsetWidth-x;
		var width;
		var heigth;
		var scroll;
		var leftWidth=0;
		if (navigator.appName=='Microsoft Internet Explorer'){
		    width=280;
			heigth=140;
			leftWidth=document.getElementById("leftMenu").offsetWidth;
			scroll=document.getElementById("workspaceDiv").scrollTop;
			c=document.body.clientWidth-x-leftWidth;
		}
		else{
			width=270;
			heigth=101;
			scroll=window.scrollY;
		}
		if (s<width||c<width){
			if (s<width&&c<width){
				if(s>c){
					x-=x+width-document.body.clientWidth+leftWidth;
				}
				else{
					x-=event.screenX+width-screen.width;
				}
			}
			else if(s<width){
				x-=event.screenX+width-screen.width;
			}
			else{
				x-=x+width-document.body.clientWidth+leftWidth;
			}
		}
		if (event.clientY+heigth>document.body.offsetHeight){
			y-=event.clientY+heigth-document.body.offsetHeight;
		}
		layer.style.top = y+scroll;
		layer.style.left = x;
		layer.style.display = "block";

		document.getElementById(parentId).style.cursor = cursor;
	}
}

function showLayer(parentId, layerId, cursor, startFrom, topFrom)
{
	if ((document.getElementById(layerId) && document.getElementById(parentId)) != null)
	{
		var parentElemPosition= getElementPosition(parentId);
		var left = parentElemPosition.left;
		var top = parentElemPosition.top;

		var layer = document.getElementById(layerId);
        var layerPosRelative = getParentOffset(layerId);
       /* если слой имеет position:absolute а родительский элемент position:relative, то отсчет ведется от родительского элемента*/
        if($(layer).css("position") == "absolute" && layerPosRelative != null)
        {
            top -=layerPosRelative.offsetTop;
            left -=layerPosRelative.offsetLeft;
        }

        if (startFrom)
        {
            layer.style.left = left - startFrom;
        }
        else
            $(layer).css('left',left);
        if (topFrom)
            layer.style.top = top - topFrom;
        else
            $(layer).css('top',top);
		$(layer).show();

		document.getElementById(parentId).style.cursor = cursor;
	}
}

/**
 * Отображает всплывающую подсказку.
 *
 * @param parentId объект, для которого отобразить подсказку.
 * @param layerId объект, в котором отобразиьт подсказку.
 * @param cursor курсор.
 * @param message сообщение-подсказка.
 */
function showHint(parentId, layerId, cursor, message)
{
    var parent = document.getElementById(parentId);
    var layer = document.getElementById(layerId);
    layer.innerHTML = message;
    var offset = {
        "left" : ($(parent).position().left) + "px",
        "top"  : ($(parent).position().top  + parent.offsetHeight) + "px"
    }
    $(layer).css(offset);
    $(layer).show();
    $(parent).css({"cursor" : cursor});
}

function hideLayer(layerId)
{
	if (document.getElementById(layerId) != null)
	{
		var layer = document.getElementById(layerId);
		layer.style.display = "none";
	}
}

//Для сбербанка. Проверки на включение кода операции в поле Назначение платежа
function showOperationTypeList(payer, receiver)
{
   return ((isResident(receiver) || isResident(payer)) && (checkReceiverAccount(receiver) || checkPayerAccount(payer)));
}

function isResident(account) //TODO isNotResident
{
	if (account==null || account == '')
       return false;
    var accountStart = account.substring(0,5);
    return (accountStart == "40807" || accountStart == "40820" || account.substring(0,3) == "426");
}

function checkReceiverAccount(account)
{
	var accountStart = account.substring(0,5);
	var availableAccounts = new Array ("40807", "40819", "40820", "40813", "40814", "40815");
	for (var i=0; i<6; i++)
		if (accountStart == availableAccounts[i])
			return true;
    var accountMiddle = account.substring(13,15);
    if (accountStart == "40818" && (accountMiddle > 13) && (accountMiddle < 19))
       return true;
    if (account.substring(0,3) == "426")
         return true;
    return false;
}

function checkPayerAccount(account)
{
	return (account.substring(0,5) == "40820" || account.substring(0,3) == "426");
}

//Очищаем классы для элементов, иначе в Opera просто не отображаются страницы
function setOperaSettings()
{
	if (navigator.appName == 'Opera')
	{
		document.getElementById("leftMenu").className='';
		document.getElementById("workspaceDiv").className='';
	}
}
//В FF(Netscape) для отображения новостей нужно div'у задать абсолютные размеры, иначе он растягивается до размеров содержимого
function changeDivHeightForNetscape(id)
{
	if (navigator.appName=='Netscape' && document.getElementById("workspaceDiv")!=null && document.getElementById(id)!=null && document.getElementById("systemsNews")!=null)
	{
		document.getElementById("workspaceDiv").style.height = getClientHeight() - 200;
		document.getElementById("workspaceDiv").className='';
		document.getElementById("workspaceDiv").style.overflow = 'hidden';
		document.getElementById(id).style.height = getElementPosition("workspaceDiv").height - getElementPosition("systemsNews").height;
        if (getElementPosition("systemsNewsContent")!=null)
            document.getElementById(id).style.height -= getElementPosition("systemsNewsContent").height;
	}
}
//Задаем для широких списков ширину div'а, иначе растягивает окно
function changeDivSize(id)
{
	var widthClient = getClientWidth();
	if (navigator.appName=='Microsoft Internet Explorer')
		document.getElementById(id).style.width = widthClient - leftMenuSize - 10;
}
//Блок  "ожидания"
var selectLoadInset = false;
function adminBeforUnload (el, type)
{
   var mainLoadMessage = document.getElementById("centerLoadDiv");
	var bgForLoadPage = document.getElementById("bgWorkspaceRegion");
	var IE6 = navigator.userAgent.indexOf("MSIE 6.0");

	if (el !="" && document.getElementById(el) && !selectLoadInset){
		document.getElementById(el).style.display = "block";
		selectLoadInset = true;
	}
	if (mainLoadMessage && bgForLoadPage){
		bgForLoadPage.style.display = "block";
		mainLoadMessage.style.display = "block";

		var leftMenu = document.getElementById("lmInformationArea");
		if (IE6 >= 0 && leftMenu)
			mainLoadMessage.style.left = bgForLoadPage.clientWidth/2 + leftMenu.offsetWidth+50;

	}
	if (type != "lmInset" && document.getElementById("mmInsetActive")){
		document.getElementById("mmInsetActiveLeftCorner").className = "mmInsetLeftCorner";
		document.getElementById("mmInsetActive").className = "mmInset";
		document.getElementById("mmInsetActiveRightCorner").className = "mmInsetRightCorner";
	}
	if (document.getElementById("subMActiveInset"))
		document.getElementById("subMActiveInset").className = "subMInactiveInset";
}

/* Функция для центровки дива по центру рабочей области*/
function getWorkSpace (globalWorkScape)
{
  if (globalWorkScape)
    return document.getElementById("pageContent");

  var workSpace = document.getElementById("workspace");
  if (workSpace == null) // csa login page
    workSpace = document.getElementById("LoginDiv");
  if (workSpace == null)
    workSpace = $(".print")[0];
    if (workSpace == null)
    {
        workSpace = $(".mainContent.guest-payment").get(0);
    }
  return workSpace;
}

/**
 * Метод для получения координаты центра рабоченей области относительно верха окна
 * @param div
 * @param globalWorkSpace глобальный воркспейс.
 * @return topCenter середина рабочей области относительно верха окна
 *         abs абсолютное смещение рабочей области от края окна
 */
function workCenter (div, globalWorkSpace)
{
    var workSpace = getWorkSpace (globalWorkSpace); // рабочая область
    var workPosition = absPosition(workSpace); // абсолютное смещение объекта от края окна
    var winSize = screenSize(); // размер видимой части
    var scrollTop = getScrollTop(); // кол-во скроленых пикселей
    var topOffset = winSize.h/2; //смешение снизу

    var relTop = 0; // высота шапки видемой клиенту части
    if (workPosition.top - scrollTop > 0) relTop = workPosition.top - scrollTop;
    var visHeight = winSize.h; // высота рабочей облости видимая клиенту
    if ( div.offsetHeight+70 >= workSpace.offsetHeight )
    {
        var footer = document.getElementById("footer"); // подвал
        var footerPosition = absPosition(footer);
        if (winSize.h + scrollTop > footerPosition.top)
            visHeight = winSize.h - (winSize.h + scrollTop - (footerPosition.top));
        return {
            topCenter : relTop + (visHeight - relTop) / 2 + scrollTop,
            abs: workPosition
        };
    }

    if (winSize.h+scrollTop > workSpace.offsetHeight + workPosition.top) // корректировка высоты
        visHeight = winSize.h - (winSize.h+scrollTop - (workSpace.offsetHeight + workPosition.top));

    return {
        topCenter: relTop + (visHeight - relTop) / 2 + scrollTop,
        abs: workPosition
    } ;
}

/* Функция для отображения и скрытия  */
function showOrHideWaitDiv(show, tintedZIndex)
{
    var FAR_FAR_AWAY = -3300; // число px на которое необходимо сместить влево для того чтобы спрятать
    show = (show == null) ? true : show;
    var needTined = true;
    if (window.win != undefined)
        needTined = win.active == null;

    var loading = ensureElement("loading");

    if (!show)
    {
        if (needTined)
            setTintedDiv(show);
        //hide
        loading.style.left = FAR_FAR_AWAY+"px";
        win.tinedWindows(show);
        switchEvent("modalWindowScrollEvent", false);
        return;
    }

    switchEvent("modalWindowScrollEvent", true);
    win.tinedWindows(show);

    loading.divFloat = new DivFloat(loading);
    loading.style.left = "";
    setLoadingStyle();
    var scrollTimer; // таймер перед началом
    // если собитие скрола отсутствует добавляем его
    addSwitchableEvent(window, 'scroll', function () {
            clearTimeout(loading.divFloat.floatTimer);
            var reg = /\d*/
            var fromPx = parseInt(reg.exec(loading.style.top));
            clearTimeout(scrollTimer)

            scrollTimer = setTimeout(function(){
                loading.divFloat.floatEffect(fromPx, workCenter(loading).topCenter - loading.offsetHeight / 2);
                }, 50);

        }, "loadingScrollEvent");
    if (needTined)
        setTintedDiv(show);

    if (navigator.userAgent.indexOf("MSIE") >= 0)
    {
        var imgDiv = ensureElement("loadingImg");
        var src = imgDiv.getElementsByTagName('img')[0].src;
        imgDiv.innerHTML = '<img src="' + src + '"/>';
    }
}

/**
 * Функция для получения абсолютной позиции объекта
 * @param obj
 */
function absPosition(obj) {
     var left = 0;
     var top = 0;
    // workspaceDiv - необходим для правильного расчета координат в АРМ сотрудника. В клиентсткой части "workspaceDiv" нет на странице.
     while(obj && obj.id != "workspaceDiv") {
            left += obj.offsetLeft;
            top += obj.offsetTop;
            obj = obj.offsetParent;
      }
      return {left:left, top:top};
}

function setLoadingStyle ()
{
    var workspace = getWorkSpace ();
    var loading = ensureElement("loading");
    loading.style.top = workCenter (loading).topCenter - loading.offsetHeight/2 + "px";
    var position = absPosition(workspace); // Абсолютная позиция рабочей области
    loading.style.left = (position.left+workspace.offsetWidth/2 - loading.offsetWidth/2) + "px";
}


// Функция устанавливающая параметры блокируюшего дива с учетом размера workspace
function setTintedDiv(show, blockAllContent)
{
    var workspace = getWorkSpace(blockAllContent);
    if(show == null) return ;
    TintedNet.setTinted(workspace, show, blockAllContent);

    // дополнительные действия при активизации или диактивизации тонировочного слоя
    aditionalTintedAction (show);
}

// дополнительные действия для затемнения при ожидании
function aditionalTintedAction(show)
{
    if (!isClientApp) return; // данное действие актуально только для клиентского приложения
    var footer = document.getElementById("footer");
    // если футер отсутствует действия которые идут дальше не имеют смысла
    if (footer == null) return;

    TintedNet.setTinted(footer, show);
}

/**
 * Почти объект описывающий поведение маскировочных (затеняющих) плашек
 */
var TintedNet = {
    RESIZE_EVENT: "ResizeEvent", // постфикс для событий изменения размера
    tintedCount: 0,
    tinted : [],
    /**
     * Установить затемнение для элемента
     * @param element элемент
     * @param show флаг затемнения (показать/спрятать)
     */
    setTinted : function (element, show, blockAllContent) {
        element = ensureElement(element);
        var tinted = this.getTintElement(element);
        // озможно что элемента show не будет это означает что произошло событие
        // такое как ресайз окна и необходимо поменять размеры затемнения
        if (show == null)
            show = !(tinted.style.display == "none");

        if (!show) {
            hideOrShow(tinted, true);
            return;
        }


        hideOrShow(tinted, false);

        tinted.style.width = element.offsetWidth+"px";
        tinted.style.height = element.offsetHeight+"px";
        if(navigator.userAgent.indexOf("MSIE 7.0") >= 0)
            tinted.style.zIndex = getZindex(element)+(-1);
        else
            tinted.style.zIndex = getZindex(element)+(blockAllContent?11:10);

        var divs = tinted.getElementsByTagName("div");
        for (var i =0 ; i< divs.length; i++)
        {
            divs[i].style.width = tinted.style.width;
            divs[i].style.height = tinted.style.height;
        }

        switchEvent(tinted.id+this.RESIZE_EVENT, !show); // управляем собитиями
        setPositionLikeObj (tinted, element);

        //в ie 8 не работает z-index, поэтому вставляем затемняющий элемент перед всплывающим окном, для того, чтобы он не перекрывал это окно
        if(navigator.userAgent.indexOf("MSIE 8.0") >= 0)
        {
            var workspace = document.getElementById("workspace");
            var popupWin = document.getElementById("popupInfromMessagesIdWin");
            if (workspace != undefined && workspace != null && popupWin != undefined && popupWin != null)
                workspace.insertBefore(tinted, popupWin);
        }
    },
    /**
     * Получить ссылку на затемняющий контейнер который принадлежит element
     * @param element элемент который необходимо затемнять
     * @return Object затемняющий элемент
     */
    getTintElement: function (element) {
        element = ensureElement(element);
        if (element != undefined)
            if (element.tinted) return element.tinted;

        //Тонирующий элемент отсутствует, добавлем его

        var tinted = document.createElement("div"); // обертка сетки
        tinted.setAttribute("id", "tinted"+this.tintedCount);
        tinted.className = "tintedWrapper";
        document.body.appendChild(tinted); //

        tinted = document.getElementById("tinted"+this.tintedCount);
        hideOrShow(tinted, true);
        // тонировка, затемнение
        var opacity = document.createElement("div");
        opacity.className = "tinted opacity25";
        tinted.appendChild(opacity);
        // сетка
        var tintedNet = document.createElement("div");
        tintedNet.className = "tintedNet";
        tinted.appendChild(tintedNet);


        // добавляем и увеличиваем
        this.tintedCount++;
        this.tinted.push(tinted);
        // добавляем выключаемое событие на ресайз документа
        var self = this; // замыкание
        if (element != undefined)
            addSwitchableEvent(window, "resize", function () { self.setTinted(element);}, tinted.id+this.RESIZE_EVENT);
        switchEvent(tinted.id+this.RESIZE_EVENT, true); // по умолчанию событие не действительное

        if (element != undefined)
            element.tinted = tinted;
        return tinted;
    },
    /**
     * Отобразить или спрятать все затемняющие элементы
     * @param hide флаг (спраятать/показать)
     */
    hideOrShowAllTinted: function (hide) {
       for (var i = 0; i < this.tinted; i++)
            this.setTinted(this.tinted[i], !hide);
    }
};
// блок подождите
var clientBeforeUnload = {
    // тригер который отключает "загрузчик" на ближайщий вызов
    showTrigger: true,
    /**
     * метод реализующий логику отображения блока "подождите"
     * @param timeout
     */
    show: function (timeout) {
        if (!this.showTrigger)
        {
            this.showTrigger = true;
            return;
        }
        var myself = this;
        setTintedDiv(true);
        timeout = (timeout == null) ? true : timeout;
        if (timeout)
        {
            // Смещение времени перед началм показа лоудера в мс
            var timeShift = 1000; // 1c

            setTimeout(function ()
            {
                myself.show(false)
            }, timeShift);
            return;
        }
        // Для предотвращения падения при нажатии на командную кнопку в Opera
        // на страницах где нет прелоудера необходимо отлавливать и глушить скриптовую ошибку
        try
        {
            showOrHideWaitDiv();
        }
        catch (e)
        {

        }
        addSwitchableEvent(window, "resize", function ()
        {
            setLoadingStyle();
        }, "loadindDivResizeEvent");
    },

    /**
     * метод для инициализации функционала блока "Подождите"
     */
    init: function () {
        var myself = this;
        if (window.opera)
        {
            // opera не понимает событие beforeunload. Для нее устанавливаем событие onclick для каждого тега a
            var tags = document.getElementsByTagName("a");
            for (var i = 0; i < tags.length; i++)

                if ($(tags[i]).attr('href') != undefined && $(tags[i]).attr('href') != '' && tags[i].href.indexOf("#") < 0
                        && tags[i].href.indexOf("javascript:") < 0 && tags[i].target.toLowerCase() != '_blank')
                {
                    $(tags[i]).bind('click', function ()
                    {
                        execIfRedirectResolved(function(){myself.show(false)});
                    });
                }
            return;
        }

        window.onbeforeunload = function () { myself.show(); };
    }

};

//Вывод блоков "ожидания" при переходе на другое окно
function loadNewAction(el, type)
{
	if (!isClientApp)
        adminBeforUnload (el, type)
    else
        // только для Opera так как другие браузеры понимают событие beforeunload
        if (window.opera && ensureElement("loading") != null ) clientBeforeUnload.show(false);
    return true;
}

function clearLoadMessage()
{
	var loadEl = "centerLoadDiv";
	var bgForLoad = "bgWorkspaceRegion";

	var IE6 = navigator.userAgent.indexOf("MSIE 6.0");

	var mainLoadMessage = document.getElementById(loadEl);
	var bgForLoadPage = document.getElementById(bgForLoad);

	if (window.opener) {
		try
		{
			mainLoadMessage = window.opener.document.getElementById(loadEl);
			bgForLoadPage = window.opener.document.getElementById(bgForLoad);
		}
		catch(e)
		{
		}
	}

	if (mainLoadMessage) mainLoadMessage.style.display = "none";
	if (bgForLoadPage) bgForLoadPage.style.display = "none";

}

function getFieldValue(id)
{
	var elem = document.getElementById(id);
    if (elem == null) {
        return '';
    }
	if (elem.type == "checkbox"){
		return elem.checked;
	}
	return  elem.value;
}

//функции открытия справочников
function openCountryDictionary(callback, code)
{
	window.setCountryInfo = callback;

	var params = "&filter(code)=" + code;
	var win = window.open(document.webRoot+'/private/dictionary/countries.do?action=getCountryInfo' + params,
	      'Countries', "resizable=1,menubar=0,toolbar=0,scrollbars=1, height=450, width=820");
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openProvidersDictionary(callback)
{
	window.setProviderInfo = callback;

	var win = window.open(document.webRoot+'/private/dictionaries/provider/filter.do',
		           'Providers', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=900px,height=800px");

	win.moveTo(screen.width / 2 - 450, screen.height / 2 - 400);
}

//вызов справочника поставщиков услуг для сотрудника
function openProvidersDictionaryForEmployee(callback, kind, billingId)
{
	window.setProviderInfo = callback;

    if((billingId == null) || (billingId == ""))
        var win = window.open(document.webRoot + '/private/dictionaries/provider/list.do?action=getProvider&filter(kind)=' + kind,
		           'Providers', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=900px,height=800px");
    else
        var win = window.open(document.webRoot + '/private/dictionaries/provider/list.do?action=getProvider&filter(kind)=' + kind + '&filter(billingId)=' + billingId,
		           'Providers', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=900px,height=800px");

	win.moveTo(screen.width / 2 - 450, screen.height / 2 - 400);
}

function openGuestOperationConfirmLog(operationUID)
{
    var params = "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=900px,height=800px";
    var win = window.open(document.webRoot + '/log/guest/entries.do?OUID=' + operationUID, 'LogGuestEntries', params);

    win.moveTo(screen.width / 2 - 450, screen.height / 2 - 400);
}

// функция открытия справочника услуг
function openPaymentServicesDictionary(callback)
{
    window.setPaymentServiceInfo = callback;

    var params = "";
    var win = window.open(document.webRoot+'/private/dictionaries/paymentService/list.do?action=getPaymentServiceInfo' + params,
                   'PaymentService', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px, left=50, top=50");
}

function openKBKDictionary(callback, code)
{
	window.setKBKInfo = callback;

	var params = "&filter(code)=" + code;
	var win = window.open(document.webRoot+'/private/dictionary/KBK.do?action=getKBKInfo' + params,
		           'KBK', "resizable=1,menubar=0,toolbar=0,scrollbars=1")
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openContactMembersDictionary(callback, code)
{
	window.setKBKInfo = callback;

	var params = "&filter(code)=" + code;
	var win = window.open(document.webRoot+'/private/dictionary/contact/members.do?action=getBankInfo' + params,
		           'Banks', "resizable=1,menubar=0,toolbar=0,scrollbars=1")
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openNationalBanksDictionary(callback, name, bic, isOurBank)
{
	window.setBankInfo = callback;

	var params = "&filter(bankName)=" + name + "&filter(BIC)="+bic;
    if (isOurBank!=null)
        params += "&filter(ourBank)="+isOurBank
	var win = window.open(document.webRoot+'/private/dictionary/banks/national.do?action=getBankInfo' + params,
		           'Banks', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1040, height=600");
				win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openOfficesDictionary(callback, name)
{
	window.setOfficeInfo = callback;

	var params = "&filter(name)=" + name;
	var win = window.open(document.webRoot+'/private/dictionary/offices.do?action=getOfficesInfo' + params,
		           'Offices', "resizable=1,menubar=0,toolbar=0,scrollbars=1")
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openDepartmentsDictionary(callback, checkedData, level, multipleChoice)
{
	window.setDepartmentInfo = callback;
    window.____checkDepartmentsInfo = checkedData;

	var win = window.open(document.webRoot+'/departments/list.do?action=getDepartmentsInfo'
            + (level != null ? ("&level=" + level) : "")
            + ((multipleChoice != null && multipleChoice) ? "&multipleChoice=true" : ""),
		           'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px, left=50, top=50");
}

/*открывает справочник разрешенных ТБ*/
function openAllowedTBDictionary(callback, oneSelection)
{
    window.setDepartmentInfo = callback;
    var win = window.open(document.webRoot+'/dictionaries/allowedTerbanks.do?type=' + (oneSelection ? 'oneSelection' : 'manySelection'),
                   'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px, left=50, top=50");
}

function openLoanOfficesDictionary(callback)
{
	window.setLoanOfficeInfo = callback;

	var params = "";
	var win = window.open(document.webRoot+'/private/dictionary/offices/loans.do?action=getLoanOfficeInfo' + params,
		           'LoanOffices', "resizable=1,menubar=0,toolbar=0,scrollbars=1")
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openGroupsDictionary(callback, category)
{
	window.setGroupData = callback;

	var params = "";
	var win = window.open(document.webRoot+'/groups/list.do?category='+category+'&action=getGroupInfo' + params,
		           'Groups', "resizable=1,menubar=0,toolbar=0,scrollbars=1")
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function openAdaptersDictionary(callback)
{
	window.setAdapterInfo = callback;

	var win = window.open(document.webRoot+'/dictionaries/routing/adapter/list.do?action=getRegionsInfo',
		           'Regions', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px, left=50, top=50");
}

function openRegionsDictionary(callback, type)
{
    window.setRegionInfo = callback;
    var path = '/private/dictionary/regions/list.do?action=getRegionsInfo';
    if (type == "filter")
    {
        path = '/private/dictionary/regions/list.do?action=getRegionsInfoFilter';
    }
	var win = window.open(document.webRoot+path,
		           'Regions', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px, left=50, top=50");
}

function openBillingsDictionary(callback)
{
	window.setBillingInfo = callback;

	var win = window.open(document.webRoot+'/dictionaries/billing/list.do?action=getBillingInfo',
		           'Billing', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px, left=50, top=50");
}

//Справочник кодов валютных операций
function openOperationTypesDictionary(callback, code)
{
    window.setOperationCodeInfo = callback;
    var params = "fltrCode=" + code;
    var win = window.open(document.webRoot + '/private/operationCodes.do?' + params, 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
    win.moveTo(screen.width / 2 - 350, screen.height / 2 - 400);
}

function openSegmentCodeTypeDictionary(callback)
{
	window.setSegmentCodeTypeInfo = callback;

	var win = window.open(document.webRoot+'/persons/segmentCodeType/list.do',
	      'Segments', "resizable=1,menubar=0,toolbar=0,scrollbars=1, height=450, width=1000");
	win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

//открывает окно со списком шаблонов для заданного платежа
function openTemplateList(formType)
{
    var params = "formType=" + formType;
    var win = window.open('../templates.do?' + params, 'Templates', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
    win.resizeTo(width=750,height=500);
    win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

/**
 * Открыть внешнюю ссылку в новом окне
 */
function openExternalLink(url)
{
	window.open(url, 'ExternalLink', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=700px,height=800px");
}

//открыть Журнал сообщений для operationUID в отдельном окне.
function openMessageLogList(operationUID)
{
    var space = 100;
    var clientWidth = getClientWidth();
    var clientHeight = getClientHeight();
    var params = "filter(operationUID)=" + operationUID;
    var url = '/audit/log/messages';
    var win = window.open(document.webRoot + url + '.do?' + params, 'MessageLogList', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
    win.resizeTo((clientWidth-space),(clientHeight-space));
    win.moveTo(space/2, space/2);
}

//открыть Журнал сообщений для operationUID в отдельном окне.
function openMessageLogListForERKC(operationUID)
{
    var space = 100;
    var clientWidth = getClientWidth();
    var clientHeight = getClientHeight();
    var params = "filter(operationUID)=" + operationUID;
    var url = '/erkc/log/messages';
    var win = window.open(document.webRoot + url + '.do?' + params, 'MessageLogList', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
    win.resizeTo((clientWidth-space),(clientHeight-space));
    win.moveTo(space/2, space/2);
}

//переходим на страницу создания платежа по шаблону
function makePaymentByTemplate(templateId)
{
    window.location = document.webRoot + '/private/payments/payment.do?template=' +templateId;
}

//----Все для главного меню и вкладок----
var countMMIns = 0; //количество обрабатываемых вкладок, подсчитывается в mainMenuInset.jsp



	//<%--Переопределение ID вкладок на имя с номером--%>
	function setDivId(el, newName)
	{
		el.id = newName + countMMIns;
		return document.getElementById(el.id)
    }

	//<%-- Переопредение стиля вкладок для однострочного меню --%>
	function setClientStyle()
	{
		if (document.getElementById("mmTbl"))
			document.getElementById("mmTbl").className = "clientChange";
	}

	//<%-- Опредение ширины получившейся строки меню из вкладок от firstIns до endIns --%>
	function widthMMInsLine(objName, firstIns, endIns)
	{
		var widthFirstline = 0;
		for (i=firstIns; i<=endIns; i++){
			widthFirstline = widthFirstline + document.getElementById(objName+i).offsetWidth;
		}
		return  widthFirstline;
	}

	//<%-- Сброс параметров дивов, которые создавали центровку (для resize'a) --%>
	function setNullDivMMIns()
	{
        if (document.getElementById("mmTbl"))
			document.getElementById("mmTbl").className = "adminChange";

		try{
			for (i=1; i<=countMMIns; i++){
				if (document.getElementById("mmInsBSep"+i).innerHTML){
					document.getElementById("mmInsBSep"+i).innerHTML = "";
				}
				if (document.getElementById("mmInsESep"+i).innerHTML){
					document.getElementById("mmInsESep"+i).innerHTML = "";
				}
			}
		}
		catch (e){}
	}

	//<%-- Разделение вкладок для 2хстрочного меню --%>
	function break2Line()
	{
		var countFirstLine = Math.ceil(countMMIns*0.45);//<%--45% вкладок оставляем в 1й строке--%>
		var firstEolIns = document.getElementById("mmIns"+countFirstLine);//<%--Последняя вкладка 1й строки--%>
		var firstEolInsSep = document.getElementById("mmInsESep"+countFirstLine);//<%--Распорка после последней вкладки 1й строки--%>
		var firstInsSep =  document.getElementById("mmInsBSep1");//<%--Первая вкладка--%>
        var firstInsScndLine = document.getElementById("mmInsBSep"+(countFirstLine+1));
        var firstEolInsScndLine = document.getElementById("mmInsESep"+countMMIns);
		var heightMMSep = firstEolIns.offsetHeight;//<%--Высота вкладок--%>

		try{
			var makeWidthMMIns = widthMMInsLine("mmIns", countFirstLine+1, countMMIns); //<%--Общая ширина вкладок 2й строки меню--%>
			var widthMMIns = firstEolIns.offsetParent.offsetWidth;//<%--Общая ширина меню--%>//
			if (makeWidthMMIns > widthMMIns){
				if (!break3Line(0.3, 0)) return false;   //<%--Слишком много вкладок и слишком мало места - 3х строчное меню--%>
			}
		    else {
				var widthSepFirstLine = Math.ceil((widthMMIns-(firstEolIns.offsetLeft+firstEolIns.offsetWidth))/2);
                var widthSepScndLine = Math.ceil((widthMMIns-makeWidthMMIns)/2);
				setSepAtrb(firstInsSep, firstEolInsSep, widthSepFirstLine, heightMMSep);
                setSepAtrb(firstInsScndLine, firstEolInsScndLine, (widthSepScndLine-2), heightMMSep);
			}
		}
		catch(e) {}
        return true;
	}

	//<%-- Разделение вкладок для 3хстрочного меню --%>
	function break3Line(prcnt, diminish)
	{
		var countFirstLine = Math.ceil(countMMIns*prcnt);//<%--Определение количества вкладок в первой строке (0.3 от общего количества)--%>
		var countScndLine = (countMMIns - (countFirstLine*2) + countFirstLine) - diminish;//<%--Количество вкладок второй строки 100% - (30% от 1й и 30% от 3й)--%>
		diminish++;//<%--diminish - Если во второй строке (как в самой большой теоретически) ширина больше ширины меню, пробуем убрать одну вкладку в 3ю строку при следующей итерации--%>
		var firstEolIns = document.getElementById("mmIns"+countFirstLine);//%--Последняя вкладка 1й строки--%>
		var firstEolInsSep = document.getElementById("mmInsESep"+countFirstLine);//<%--Распорка после последней вкладки 1й строки --%>
		var scndEolIns = document.getElementById("mmIns"+countScndLine);//<%--Последняя вкладка 2й строки--%>
		var scndInsSep = document.getElementById("mmInsBSep"+(countFirstLine+1));//<%--1я вкладка 2й строки--%>
		var scndEolInsSep = document.getElementById("mmInsESep"+countScndLine);//<%--Распорка после последней вкладки 2й строки--%>
		var thrdInsSep = document.getElementById("mmInsBSep"+(countScndLine+1));//<%--1я вкладка 3й строки--%>
		var thrdEolInsSep = document.getElementById("mmInsESep"+countMMIns);//<%--Распорка после последней вкладки 3й строки--%>
		var firstInsSep =  document.getElementById("mmInsBSep1");//<%--1я вкладка первой строки--%>

		try{
			var widthMMIns = firstEolIns.offsetParent.offsetWidth;//<%--Ширина меню--%>
			var heightMMSep = firstEolIns.offsetHeight;//<%--Высота вкладок--%>
			var widthFirstline = widthMMInsLine("mmIns", 1, countFirstLine);//<%--Общая ширина вкладок 1й строки--%>
			var widthScndLine = widthMMInsLine("mmIns", (countFirstLine+1), countScndLine);//<%--Общая ширина вкладок 2й строки--%>
			var widthThrdLine = widthMMInsLine("mmIns", (countScndLine+1), countMMIns);//<%--Общая ширина вкладок 3й строки--%>
			var widthSepFirstLine = Math.ceil((widthMMIns - widthFirstline)/2);//<%--Ширина одной распорки 1й строки--%>
			var widthSepScndLine = Math.ceil((widthMMIns - widthScndLine)/2);//<%--Ширина одной распорки 2й строки--%>
			var widthSepThrdLine = Math.ceil((widthMMIns - widthThrdLine)/2);//<%--Ширина одной распорки 3й строки--%>
			if ((widthScndLine > widthMMIns) || (widthScndLine < 0)){
				switch (diminish){
					case 1: break3Line(0.3, 1);break;
					default: breakSmthngLine(1);break;
				}
				return false;
			}
			if (widthThrdLine > widthMMIns){
				if (!breakSmthngLine(1)) return false;
			}
			else {
				setSepAtrb(firstInsSep, firstEolInsSep, widthSepFirstLine, heightMMSep);
				setSepAtrb(scndInsSep, scndEolInsSep, widthSepScndLine, heightMMSep);
				setSepAtrb(thrdInsSep, thrdEolInsSep, widthSepThrdLine, heightMMSep);
			}
		}
		catch(e) {}
		return false;
	}

	//<%-- Бесконечное количество строк. Идет просто их центровка. --%>
	function breakSmthngLine(begIns){
		try{
			var countFirstIns = begIns; //<%--Первая вкладка в очередной строке меню--%>
			var countFirstEolIns = begIns;
			var standartOffsetTop = document.getElementById("mmIns"+countFirstIns).offsetTop;
		    //<%--Считаем вкладки, пока их уровни верхнего левого угла совпадают с верхним левым углом первой вкладкой в строке--%>
			for (i=countFirstIns; i<=countMMIns; i++){
				if (document.getElementById("mmIns"+i).offsetTop <= standartOffsetTop)
					countFirstEolIns++;
				else break;
			}
			countFirstEolIns--;

			var firstInsSep =  document.getElementById("mmInsBSep"+countFirstIns);//<%--Распорка перед первой вкладкой--%>
			var widthMMIns = document.getElementById("mmIns1").offsetParent.offsetWidth;//<%--Общая ширина меню--%>
			var eolInsSep = document.getElementById("mmInsESep"+countFirstEolIns);//<%--Распорка перед последней вкладкой в строке--%>
			var heightMMSep = document.getElementById("mmIns1").offsetHeight;//<%--Высота вкладок--%>
			var widthInsLine = widthMMInsLine("mmIns", countFirstIns, countFirstEolIns);//<%--Опредляется ширина получаемых вкладок--%>
			var widthSepInsLine = Math.ceil((widthMMIns - widthInsLine)/2);//<%--Определяется ширина распорки в строке--%>

			setSepAtrb(firstInsSep, eolInsSep, widthSepInsLine, heightMMSep);

			countFirstEolIns++;
			if (countFirstEolIns > countMMIns) return true;
			else breakSmthngLine(countFirstEolIns);//<%--Переход на следующую строку в меню--%>
		}
		catch(e){}
		return false;
	}

	//<%-- Первичное определение количества возможных строк меню --%>
	function validCountMMIns()
	{
		setNullDivMMIns(); //<%--Сброс параметров--%>
		var firstIns  = document.getElementById("mmIns1");//<%--Первая вкладка--%>
		var lastIns   = document.getElementById("mmIns"+countMMIns);//<%--Последняя вкладка--%>
		var countLine = 1;
		if (lastIns && firstIns)
			if (lastIns.offsetTop > firstIns.offsetTop){
				var centerIns = document.getElementById("mmIns"+Math.ceil(countMMIns/2));
				countLine = 2;
				if (centerIns)
					if (centerIns.offsetTop > firstIns.offsetTop && centerIns.offsetTop < lastIns.offsetTop)
						countLine = 3;
			}

		switch (countLine){
			case 1: setClientStyle();break;
			case 2: break2Line();break;
			case 3: break3Line(0.3, 0);break;
			default:return;
		}

	}
/*Только для вкладок левого меню*/
var activeName="";
	function showHide(nowActiveName, path)
	{
		if (path != "")
		{
            if (document.getElementById("locationSM")){
                if (document.getElementById("auto-generated-sub-menu")){
                    if (document.getElementById("auto-generated-sub-menu").innerHTML == "")  {
                        document.location=document.getElementById("locationSM").value;
                        return false;
                    }
                }
                else if (document.getElementById("subML2_"+nowActiveName)){
                        if (document.getElementById("subML2_"+nowActiveName).className == "displayNone"){
                            document.location=document.getElementById("locationSM").value;
                        }
                }
            }
		}

        if (nowActiveName != "" && path == "")
            activeName = nowActiveName;

		var lastActiveGroup = "subML2_"+activeName;
		var lastActiveTitle = "subMTitleL2_"+activeName;
		var nowActiveGroup = "subML2_"+nowActiveName;
		var nowActiveTitle = "subMTitleL2_"+nowActiveName;
		if (document.getElementById(lastActiveGroup))
		lastActiveGroup = document.getElementById(lastActiveGroup);
		if (document.getElementById(lastActiveTitle))
		lastActiveTitle = document.getElementById(lastActiveTitle);
		if (document.getElementById(nowActiveGroup))
		nowActiveGroup = document.getElementById(nowActiveGroup);
		if (document.getElementById(nowActiveTitle))
		nowActiveTitle = document.getElementById(nowActiveTitle);

		if (activeName != nowActiveName){
            nowActiveGroup = "subML2_"+path;
            if (document.getElementById(nowActiveGroup))
                nowActiveGroup = document.getElementById(nowActiveGroup);
            nowActiveTitle = "subMTitleL2_"+path;
            if (document.getElementById(nowActiveTitle))
                nowActiveTitle = document.getElementById(nowActiveTitle);

			lastActiveGroup.className="displayBlock";
			lastActiveTitle.className="subMAciveTitleBg";
			nowActiveGroup.className="displayBlock";
			nowActiveTitle.className="subMAciveTitleBg";
		} else
		if ((activeName == nowActiveName) && (nowActiveGroup.className == "displayBlock")){
			nowActiveGroup.className="displayNone";
			nowActiveTitle.className="subMInaciveTitleBg";
		} else
		if ((activeName == nowActiveName) && (nowActiveGroup.className == "displayNone")){
			nowActiveGroup.className="displayBlock";
			nowActiveTitle.className="subMAciveTitleBg";
		}

		activeName = nowActiveName;
	}

	//Чтобы избежать повторный вызов showHide.
	var oldSubMenuName = "";
	function initLeftMenu(nowActiveName)
	{
		var subMenuName = document.getElementById("subMenuName");
		if (subMenuName!= null && subMenuName.value.length>0 && oldSubMenuName!=subMenuName)
		{
			oldSubMenuName = subMenuName;
			showHide(nowActiveName, subMenuName.value);
		}
	}

    function setWorkspaceWidth()
    {
        if (document.getElementById("workspaceRegion") && document.getElementById("lmArea"))
                document.getElementById("workspaceRegion").style.width = (getClientWidth() - document.getElementById("lmArea").offsetWidth - 100);

    }

    function openHelp(helpLink)
    {
	    var h = 150 * 4;
		var w = 760;
		var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
		    ", width=" + w +
		    ", height=" + h +
		    ", left=" + (screen.width - w) / 2 +
		    ", top=" + (screen.height - h) / 2;
			   openWindow(null,helpLink, "help", winpar);
    }

    function openFAQ(faqLink)
    {
	    var h = 150 * 4;
		var w = 760;
		var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
		    ", width=" + w +
		    ", height=" + h +
		    ", left=" + (screen.width - w) / 2 +
		    ", top=" + (screen.height - h) / 2;
			   openWindow(null,faqLink, "faq", winpar);
    }

    function openTarif(tarifLink)
    {
	    var h = 150 * 4;
		var w = 760;
		var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
		    ", width=" + w +
		    ", height=" + h +
		    ", left=" + (screen.width - w) / 2 +
		    ", top=" + (screen.height - h) / 2;
			   openWindow(null,tarifLink, "tarif", winpar);
    }


var addUrlList = "${phiz:calculateActionURL(pageContext,'/departments/list')}";
/*Показывает или скрывает дочерние подразделения по id родительского.*/
function showChild(id)
{
    var elem = document.getElementById(id);
    if (elem != null)
    {
        if (elem.style.display == "none")
        {
            elem.style.display = ""
        }
        else
        {
            elem.style.display = "none";
        }
    }
    else
    {
        window.location = addUrlList + "?id=" + id;
    }
}
/**
 * Метод для выполнения всех скриптов полученых в ajax данных
 * @param msg ajax данные
 */
function evalAjaxJS(msg)
{
    // если в результате нет намека на скрипт до дальнейшее выполнение не требуется
   if (msg.toLowerCase().indexOf("script") == -1) return;
   // очишаем события при загрузки
   window.onload = null;
   if (window.evalAjaxJSCounter == undefined)
        window.evalAjaxJSCounter = 0;
   window.evalAjaxJSCounter++;
   var id = "tmpEvalAjaxJS"+window.evalAjaxJSCounter;
   var tmpDiv = document.createElement("div");
   tmpDiv.id = id;
   tmpDiv.style.display = "none";
   document.getElementsByTagName("body")[0].appendChild(tmpDiv);
   tmpDiv = document.getElementById(id);
   tmpDiv.innerHTML = msg;

   var script = tmpDiv.getElementsByTagName("script");
   for (var i = 0; i<script.length; i++ )
   {
       if (script[i].innerHTML == "")
          continue;
       try
       {
           if (!window.execScript)
               window.eval(script[i].innerHTML);
           else
               window.execScript(script[i].innerHTML);
       }
       catch (ex)
       {
           alert("При попытке инициализировать скрипты, полученные через AJAX, возникла ошибка:\n" + ex.message + "\n script = " + i + "\n" + script[i].innerHTML + "\n" + msg);
           throw(ex);
       }
   }
    if ( window.onload!= null ) window.onload();
    tmpDiv.parentNode.removeChild(tmpDiv);
}

/**
 * Метод для приобразования параметров из хеш массива в строку типа имя=значение?имя=значение
 * @param paramHash
 */
function convertAjaxParam (paramHash)
{
    var tmp_param = '';
    var i = 0;
    for (var name in paramHash)
    {
        if (i > 0)
            tmp_param = tmp_param + "&";
        tmp_param = tmp_param + name + "=" + encodeURIComponent(paramHash[name]);
        i++;
    }
    return tmp_param;
}

/**
 * Метод для отображения и скрытия прелоудера.
 * @param isShow
 */
function showOrHideAjaxPreloader (isShow)
{
    if (!isClientApp) return; // данный метод актуален только для клиентского приложения
    if (isShow == null) isShow = true;
    var loading = ensureElement("loading"); // необходим для хранения счетчика таймоута
    //show
    if (isShow)
    {
        document.body.style.cursor = "progress";
        if (isClientApp) loading.preloaderTimer = setTimeout(function ()
        {
            showOrHideWaitDiv(true);
        }, 1000);
        return ;
    }

    // hide
    document.body.style.cursor = "auto";
    if (loading.preloaderTimer != undefined)
        clearTimeout(loading.preloaderTimer);
    showOrHideWaitDiv(false);
}

function processErrorInResponce(data, errorAction)
{
    var s = trim(data.replace(/&nbsp;/g, ' '));
    try
    {
        var ajaxResponceErrorDiv = $(s).find("#ajaxResponceError")[0];
    }
    catch(e) {}
    if (ajaxResponceErrorDiv == undefined)
        return false;

    if (errorAction != undefined)
        return errorAction(data);
    window.location = ajaxResponceErrorDiv.innerHTML;
    return true;
}

/**
 * Функция для запросов ajax
 * @param params параметры (имя=значение?имя=значение, если параметров нет передаем null(от этого зависит тип запроса)
 * @param url
 * @param callback функция для обратного вызова
 * @param type необязательный параметр для определения типа ответа (сейчас используется только для JSON)
 * @param showPreLoader параметр, отвечающий за необходимость отображения прелоудера. По умолчанию true
 * @param errorAction функция, вызываемая при ошибке
 * @param forceIgnoreErrorAction признак, указывающий о необходимости игнорировать errorAction
 *        (processErrorInResponce может подвешивать браузер, если ответ - большой кусок текста не html)
 */
function ajaxQuery(params, url, callback, type, showPreLoader, errorAction, forceIgnoreErrorAction)
{
    var requestType = params == null ? "GET" : "POST";
    showPreLoader = showPreLoader==null?true:showPreLoader;
    /*если нет параметров отправляем GET запрос*/
    if (showPreLoader)
        showOrHideAjaxPreloader();

    var isJson = type != null && type.toLowerCase() == "json";

    var ignoreErrorAction = forceIgnoreErrorAction != null && forceIgnoreErrorAction;

    var defaultCallback = function (res)
    {
        if (showPreLoader)
            showOrHideAjaxPreloader(false);

        if (!isJson && !ignoreErrorAction && processErrorInResponce(res, errorAction))
            return false;

        if(callback)
            callback(res);
        if (!isJson) evalAjaxJS(res);
    };
    var newCallback = defaultCallback;
    var defaultError = function (httpObj, textStatus)
    {
        //если закончилась сессия то перезагружаем страницу
        if(httpObj.status == 401)
        {
            window.location.reload();
            return;
        }
    };

    if (isJson)
    {
        newCallback = function (res)
        {
            var resp;
            try
            {
                resp = $.parseJSON(res);
            }
            catch (e)
            {
                resp = null;
            }
            defaultCallback(resp);
        };
    }

    if (url == null) return false;
    /*
     * в POST-запрос вставляем токен страницы
     * имя параметра === com.rssl.phizic.web.security.PageTokenUtil.PAGE_TOKEN_ATTRIBUTE_KEY
     * значение параметра задается на основе com.rssl.phizic.web.tags.VisibleFormTag.doEndTag (текущая реализация input[name = PAGE_TOKEN])
     */
    if ("POST" == requestType)
        params += "&PAGE_TOKEN=" + $('input[name = PAGE_TOKEN]').val();
    /*jquery*/
    try
    {
        new XMLHttpRequest();
        $.ajax({
            type: requestType,
            url: url,
            data: params,
            contentType: "application/x-www-form-urlencoded; charset=utf8",
            success: newCallback,
            error: defaultError
        });
        return true;
    }
    catch (err)
    {
        /*IFRAME*/
        var xmlobj = new IFrameRequest();
        xmlobj.open(requestType, url, true);
        xmlobj.onreadystatechange = function()
        {
            if (this.readyState == 4)
            {
                checkAjaxError(this.responseText);
                newCallback(this.responseText);
            }
        };
        xmlobj.send(params);
        return true;
    }
}

//проверяем не вернулся ли ответ с ошибкой от Ajax. нужно для ie6
function checkAjaxError(data)
{
    if(data == '<H1>401 Unauthorized</H1>')
    {
        window.location.reload();
        return;
    }
}

//Размер окна браузера у клиента
function screenSize()
{
    var w, h; // w - длина, h - высота
    w = (window.innerWidth ? window.innerWidth : (document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body.offsetWidth));
    h = (window.innerHeight ? window.innerHeight : (document.documentElement.clientHeight ? document.documentElement.clientHeight : document.body.offsetHeight));
    return {w:w, h:h};
}

//Возвращает сдвиг скора от начала страницы в пикселях
function getScrollTop()
{
    var result = 0;

    if (document.documentElement.scrollTop)
        result = document.documentElement.scrollTop;

    if (document.body.scrollTop)
        result = document.body.scrollTop;

    return result;
}

function parseDate(input) {
    var parts = input.split('.');
    // new Date(year, month [, day [, hours[, minutes[, seconds[, ms]]]]])
    return new Date(parts[2], parts[1]-1, parts[0]);
}

/*Определение Internet Explorer*/
function isIE(version){
    var browserIE = false;
    if (navigator.appName == "Microsoft Internet Explorer"){
        browserIE = true;
        if (version != null && navigator.appVersion.indexOf("MSIE "+version) < 0) return false;
    }
    return browserIE;
}

/**
 * @returns {boolean} брайзер InternetExplorer если эта функция вернет true.
 */
function IE() {return '\v'=='v';}

/*Определяем тип браузера. Возможные значения name - opera, safari, firefox, chrome*/
function isBrowser(name)
{
    var result = RegExp(name).test(navigator.userAgent.toLowerCase());

    //костыль для хрома, т.к. там
    //navigator.userAgent == 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/537.4'
    if (result && name == 'safari')
        return !isBrowser('chrome');

    return result;
}

/* Функция для складирования функций необходимых выполнить после полной загрузки DOM */
function doOnLoad (funct, params)
{
    if (params)
        $(document).ready(function() {funct(params)});
    else
        $(document).ready(funct);
}

/* Функция для складирования функций */
function addClearMasks(event, funct)
{
    if (funct == null) return;
    var clearMasks = window.clearMasks;
    window.clearMasks = function (event) { if (clearMasks != null) clearMasks(event); funct(event); };
}

/**
 * проверяет существует ли сообщение на форме.
 * @param msg сообщение для проверки.
 * @param divId идентификатор messagebox'а
 */
function isMessageExist(divId, msg)
{
    if (messages[msg] == 'found')
        return true;

    /* дополнительно проверка на наличие уже выведенного сообщения */
    var items = $("#"+divId+" div[class=itemDiv]");
    for (var i = 0; i < items.length; i++) {
        // В сообщении могут быть теги, поэтому преобразовываем в спан и берем у него текст
        var span = document.createElement("span");
        span.innerHTML = msg;
        if (trim($(items[i]).text()) == trim($(span).text()))
            return true;
    }
    return false;
}

/**
 * Функция для добавления информационного сообщения (добавляет к существующим) если добавляемое сообщение ещё не выводилось
 * @param msg - сообщение для вывода
 * @param divId - элемент куда вставляется сообщение
 * @param deleteMsgIfExist - признак, полезный для случаев, когда наличие сообщения в массиве не является достаточным
 * условием того, что оно отображается на странице. Например, есть всплывающее окно, его содержимое получается
 * ajax запросом и обновляется при каком-либо действии пользователя, дабы узнать были ли на форме ранее сообщения
 * которые необходимо отобразить, в конечном итоге вызывается данная функция, соответственно сообщение есть в
 * массиве из-за чего оно не будет отображено если не задан данный признак.
 */
function addMessage(msg, divId, deleteMsgIfExist)
{
    /*проверяем нет ли добавляемого сообщения среди выведеных*/
    if(deleteMsgIfExist)
    {
        delete messages[msg];
    }

    if (divId == null) divId = 'warnings';
    if (msg.lastIndexOf(":") == msg.length - 1)
        msg = msg.substr(0, msg.length - 1);
    /*проверяем нет ли добавляемого сообщения среди выведеных*/
    if (!isMessageExist(divId, msg))
    {
        hideOrShow(ensureElement(divId), false);

        var messageDivContainer = findChildByClassName(ensureElement(divId), "messageContainer");

        messages[msg] = 'found';
        $(messageDivContainer).append("<div class='itemDiv'>"+msg+"</div>");
    }
}
/**
 * Функция для добавления сообщения об ошибке  (добавляет к существующим) если добавляемое сообщение ещё не выводилось
 *
 * @param msg сообщение для вывода
 * @param divId элемент куда вставляется сообщение
 * @param deleteMsgIfExist признак, полезный для случаев, когда наличие сообщения в массиве не является достаточным
 * условием того, что оно отображается на странице. Например, есть всплывающее окно, его содержимое получается
 * ajax запросом и обновляется при каком-либо действии пользователя, дабы узнать были ли на форме ранее ошибки
 * которые необходимо отобразить, в конечном итоге вызывается данная функция, соответственно сообщение есть в
 * массиве из-за чего оно не будет отображено если не задан данный признак. Живой пример отправка выписки по
 * карте на почту.
 *
 */
function addError(msg, divId, deleteMsgIfExist)
{
    if(deleteMsgIfExist)
    {
        delete messages[msg];
    }

    if (divId == null) divId = 'errors';
    if (msg.lastIndexOf(":") == msg.length - 1)
        msg = msg.substr(0, msg.length - 1);
    /*проверяем нет ли добавляемого сообщения среди выведеных*/
    if (!isMessageExist(divId, msg))
    {
        hideOrShow(ensureElement(divId), false);

        var errorDivMessage = findChildByClassName(ensureElement(divId), "messageContainer");

        messages[msg] = 'found';
        $(errorDivMessage).append("<div class='itemDiv'>" + msg + "</div>");
    }
}

/**
 * Добавляет сообщение о недоступности внешней системы.
 * @param msg сообщение.
 */
function addInactiveESMessage(msg)
{
    addError(msg, 'inactiveMessages', true);
}

/**
 * Удаляет сообщение о неактивности внешней системы.
 * @param msg сообщение.
 */
function removeInacvtiveESMessage(msg)
{
    removeMessage(msg, 'inactiveMessages');
}

/*  Функция удаления информационного сообщения */
function removeMessage(msg, divId)
{
    if (divId == null)
        divId = 'warnings';

    removeInformation(msg, divId);
}

/**
 * Удаляет все информационные сообщения из контейнера.
 * @param divId
 */
function removeAllMessages(divId)
{
    if (divId == null)
        divId = 'warnings';

    removeInformation(null, divId);
}

/*  Функция удаления ошибки */
function removeError(msg, divId)
{
    if (divId == null)
        divId = 'errors';

    removeInformation(msg, divId);
}
/**
 * Удаляет все ошибки из контейнера.
 * @param divId
 */
function removeAllErrors(divId)
{
    if (divId == null)
        divId = 'errors';
    removeInformation(null, divId);

}

function removeInformation(msg, divId)
{
    var errorDivMessage = findChildByClassName(ensureElement(divId), "messageContainer");
    if (errorDivMessage == null)
        return;

    var needDeleteAllMessage = msg == null;
    var numOfText = 0;
    var removeArr = new Array();

    for (var childItem in errorDivMessage.childNodes)
    {
        var child = errorDivMessage.childNodes[childItem];
        if (child != null && child.nodeType == 3)
            numOfText++;
        if(child!=null && needDeleteAllMessage)
        {
            msg = child.innerHTML;
            if (child != null && msg != null)
            {
                removeArr[removeArr.length] = child;
                delete messages[msg];
            }
        }
        else
        {
            if (child != null && child.innerHTML == msg)
            {
                removeArr[removeArr.length] = child;
                delete messages[msg];
            }
        }
    }

    for (var i = 0; i < removeArr.length; i++)
    {
        $(removeArr[i]).remove();
    }

    //Скрывается блок сообщений, если он пустой
    if (($.browser.msie && errorDivMessage.childNodes.length < 1 + numOfText) || (!$.browser.msie && errorDivMessage.childNodes.length < 1 + numOfText))
        hideOrShow(ensureElement(divId), true);
}


// получить значения фильтра
function getDateFilterParams(name)
{
    var type = getRadioValue(document.getElementsByName('filter(type' + name + ')'));
    var from = getElementValue("filter(from" + name + ")");
    var to = getElementValue("filter(to" + name + ")");
    return { type:type, from:from, to: to };
}

// Функция, следящая за правильностью использования свойства disabled полей фильтра периода
function filterDateChange (name)
{
    var type = getRadioValue(document.getElementsByName('filter(type' + name + ')'));
    var from = getElement("filter(from" + name + ")");
    var to = getElement("filter(to" + name + ")");
    if (from == null || to == null)
        return ;

    var disabled = true;
    if (type == null || type == 'period')
      disabled = false;

    to.disabled = disabled;
    from.disabled = disabled;
}
/**
 * Метод для смены значений фильтра периода по одному клику
 * @param name Название фильтра
 * @param type наименование типа на который меняем
 * @param isSimpleTrigger не обязательный параметр, указывающий, что данный
 *                          переключатель является просто переключателем. По умолчанию false
 * @param activeClass наименование класса для активной вкладки
 * @param noActiveClass наименование класса для неактивной вкладки
 */
function oneClickFilterDateChange (name, type, isSimpleTrigger, activeClass, noActiveClass)
{
    var CLASS_NAME_NO_ACTIVE = "date-filter-no-active"; //"greenSelector";
    var CLASS_NAME_ACTIVE = "date-filter-active";
    if (activeClass != undefined)
        CLASS_NAME_ACTIVE = activeClass;
    if (noActiveClass != undefined)
        CLASS_NAME_NO_ACTIVE = noActiveClass;
    var curentType = getElement('filter(type' + name + ')');
    if (isSimpleTrigger == null) isSimpleTrigger = false;
    if (curentType.value == type) return;
    // deselect
    var oldElement = document.getElementById("type"+name+curentType.value);
    if (oldElement != undefined )
        oldElement.className = CLASS_NAME_NO_ACTIVE;
    // select
    document.getElementById("type" + name + type).className = CLASS_NAME_ACTIVE;

    var filterButton = findChildByClassName(curentType.parentNode, "filterButton"); // кнопка фильтра
    var moreLink = findChildByClassName(curentType.parentNode, "showHideMoreFilterLink"); // ссылка на рассширенный фильтр
    // moreLink.innerHTML != moreLink.rel - означает что расширенный фильтр закрыт
    var moreFilterIsClosed = moreLink == undefined || moreLink != undefined && moreLink.innerHTML != moreLink.rel;
    // кнопку показать необходимо спрятать
    if (moreLink != undefined )
    {
        if (moreLink.originData != undefined )
            moreLink.originData.filterButtonDisplay = false;
    }
    // новый параметр
    if (type == 'period')
    {
        document.getElementById("type"+name+type+"Detail").style.display = '';
        if (moreLink != undefined )
        {
            if (moreLink.originData != undefined )
                moreLink.originData.filterButtonDisplay = true;

            if (moreFilterIsClosed && filterButton != undefined )
                hideOrShow(filterButton, true);
        }
        else
            if (filterButton != undefined )
                hideOrShow(filterButton, false);
    }
    else {
       if (filterButton != undefined && !isSimpleTrigger)
            hideOrShow(filterButton, true);
    }

    // текущий, прошлый
    if (curentType.value == 'period')
        document.getElementById("type"+name+curentType.value+"Detail").style.display = 'none';

    curentType.value = type;

    if (type != 'period' && moreFilterIsClosed && !isSimpleTrigger)
        callOperation(null,'button.filter');
}
/**
 * Метод для смены значений фильтра (по месяцам) периода по одному клику
 * @param name Название фильтра
 * @param type наименование типа на который меняем
 */
function oneClickFilterMonthDateChange (name, type)
{
    var CLASS_NAME_NO_ACTIVE = "date-filter-no-active"; //"greenSelector";
    var CLASS_NAME_ACTIVE = "date-filter-active";
    var curentType = getElement('filter(type' + name + ')');
    if (curentType.value == type) return;
    // deselect
    var oldElement = document.getElementById("type"+name+curentType.value);
    if (oldElement != undefined )
        oldElement.className = CLASS_NAME_NO_ACTIVE;
    // select
    document.getElementById("type" + name + type).className = CLASS_NAME_ACTIVE;

    var filterButton = findChildByClassName(curentType.parentNode, "filterButton"); // кнопка фильтра
    var moreLink = findChildByClassName(curentType.parentNode, "showHideMoreFilterLink"); // ссылка на рассширенный фильтр
    // moreLink.innerHTML != moreLink.rel - означает что расширенный фильтр закрыт
    var moreFilterIsClosed = moreLink == undefined || moreLink != undefined && moreLink.innerHTML != moreLink.rel;
    // кнопку показать необходимо спрятать
    if (moreLink != undefined )
    {
        if (moreLink.originData != undefined )
            moreLink.originData.filterButtonDisplay = false;
    }
    // новый параметр
    document.getElementById("type"+name+type+"Detail").style.display = '';
    if (moreLink != undefined )
    {
        if (moreLink.originData != undefined )
            moreLink.originData.filterButtonDisplay = true;

        if (moreFilterIsClosed && filterButton != undefined )
            hideOrShow(filterButton, true);
    }
    else
        if (filterButton != undefined )
            hideOrShow(filterButton, false);


    // текущий, прошлый
    document.getElementById("type"+name+curentType.value+"Detail").style.display = 'none';
    curentType.value = type;
}

/**
 * Метод для переключения кастомного чекбокса
 * @param triggerName - наименование чекбокса
 * @param newValue - новое значение
 */
function textTriggerChange (triggerName, newValue)
{
    var CLASS_NAME_SELECTED = "greenSelector";
    var CLASS_NAME = "greenSelector transparent";

    var curentValue = getElement(triggerName).value;
    var curentElement = document.getElementById(triggerName+"_"+curentValue);
    var newElement = document.getElementById(triggerName+"_"+newValue);
    var hiddenInput = document.getElementsByName(triggerName)[0];

    hiddenInput.value = newValue;

    curentElement.className = CLASS_NAME;
    newElement.className = CLASS_NAME_SELECTED;
}

// Дата фильтр ввод дат
function enterNumTemplateField(event, obj, periodFormat)
{
    var regexp = /[dmy]/gi;
    enterNumericTemplateFld(event, obj, periodFormat.replace(regexp, '_'))
}

// Функция скрытия битых картинок
function onImgError(source)
{
    // disable onerror to prevent endless loop
    source.onerror = "";
    source.width = 0;
    source.style.display = "none";
    return true;
}

// Ишет первого ребенка с указыным именем класса
//@return: undefined или объект
function findChildByClassName(obj, className)
{
    if (obj == undefined) return undefined;

    var result = findChildsByClassName(obj, className, 1);
    if (result.length == 0 ) return undefined;
    else return result[0];
}


/**
 * Ишет детей с указыным именем класса
 * @param obj родительский элемент относительно которого ищются дети
 * @param className имя класса
 * @param resultNum количество результатов после которого можно остановить поиск если параметр опущен (или null) ищутся все дети
 * @param result техническая информация для обеспечения рекурсии.
 * @return: массив объектов или пустой массив
 */
function findChildsByClassName(obj, className, resultNum, result)
{
    var child;
    if (result == null) result = new Array();
    if (obj == undefined) return result;

    for (var childItem in obj.childNodes)
    {
        child = obj.childNodes[childItem];
        if (child != null && child.nodeType == 1)
        {
            if (child.className != undefined && child.className.search('\\b' + className + '\\b') != -1)
            {
                result.push(child);
                if ( result.length == resultNum ) return result;
            }
            result = findChildsByClassName(child, className, resultNum, result);
            if ( result.length == resultNum ) return result;
        }
    }

    return result;
}

// Ишет первого первого родителя с указыным именем класса
//@return: undefined или объект
function findParentByClassName(obj, className)
{
    while (obj != undefined && obj.className != undefined && !findClass(obj, className))
    {
        obj = obj.parentNode;
    }

    return obj;
}

// Определить, есть ли у объекта класс
//@return: true - класс найден, false - не найден
function findClassName(obj, className)
{
   return obj != undefined && obj.className != undefined && findClass(obj, className);
}

function findClass(obj, className)
{
    return obj.className.search('\\b' + className + '\\b') >= 0;
}

// Скрыть показать операции
function showHideOperations (obj, linkId, productType, url, setDataFunc)
{
  if (obj.onShow == undefined)
  {
    var HIDE_KEY = "hide";
    var className  = "";

    var allClass = trim(obj.className).split(" ");
    for (var i=0; i< allClass.length; i++)
        if (allClass[i] != HIDE_KEY)
            className += allClass[i]+" ";

    obj.onHide = function () { this.className = className }; //  this.style.backgroundPosition = "0px 6px";
    obj.onShow = function () { this.className = className+" "+HIDE_KEY; }; // this.style.backgroundPosition = "0px -30px";
  }

  var params = "id="+linkId + "&productType=" + productType + "&operation=button.showHide";
  ajaxQuery(params, url, function(data){if (setDataFunc != null) setDataFunc();}, null, false);
  showHideRelativeElement (obj, "simpleTable", obj.rel);

  var param = getParam(obj);
  // если все элементы открытые или закрытые, то меняем заголовок блока
  if (param.state != undefined)
  {
      var titleLink = $(findParentByClassName(obj, "workspace-box")).find(".blueGrayLinkDotted");
      if (param.state == true)
        titleLink.text('Скрыть все последние операции');
      else  titleLink.text('Показать все последние операции');
  }
}

/**
 * поменять  подсказку
 * @param obj  Кнопка свернуть/развернуть
 */
function changeTitle(obj)
{
    var param = getParam(obj);

    if (param.state != undefined)
        param.state = !param.state;

    param.state ? obj.title = 'Показать последние операции' : obj.title = 'Скрыть последние операции';
}

/**
 * получить список объектов, и общие состояние элементов.
 * @param obj Кнопка свернуть/развернуть
 */
function getParam(obj)
{

    //Находим родителя
    var parent = findParentByClassName(obj, "workspace-box");
    // находим ссылкипоказать скрыти
    var hideableChilds = findChildsByClassName(parent, "hideable-element");
    var state = undefined;

    // проверяем всели элементы в одиноковом состоянии
    for (var i =0 ; i< hideableChilds.length; i++)
    {
        // необходимо для того чтобы точно знать исходное состояние
        getIsOpenState(hideableChilds[i]);

        if (state == undefined ) {
            state = hideableChilds[i].isOpen;
            continue;
        }

        if (state != hideableChilds[i].isOpen) {
            state = undefined;
            break;
        }
    }

    return  { state: state, childs: hideableChilds };
}
/**
 * спрятать все минивыписки.
 * @param obj Кнопка свернуть/развернуть
 */
function hideAllOperations (obj)
{

    if (obj.allOpen == undefined)
       obj.allOpen = true; // флаг состояний

    obj.allOpen = !obj.allOpen;
    var param = getParam(obj);

    // если все элементы открытые или закрытые, то выполняем действие обратное статусу
    if (param.state != undefined) obj.allOpen = !param.state;
    for (var i =0 ; i< param.childs.length; i++)
    {
        getIsOpenState(param.childs[i]);

        // если елемент уже в необходимом состоянии то дополнительных телодвижений делать ненадо
        if (param.childs[i].isOpen == obj.allOpen)
        {
            continue;
        }
        param.childs[i].isOpen = !obj.allOpen; // жестко устанавливаем статус предисториии
                                                // чтобы история состояния не играла роль
        param.childs[i].onclick(window.MouseEvent); // Имитируем событие нажатия на ссылку скрыть/показать
    }

    obj.allOpen = undefined;
    obj.title = "";
}

/**
 * Устанавливает состояние объекта isOpen,
 * если оно еще не было задано
 * @param obj - объект
 */
function getIsOpenState(obj)
{
    if (obj.isOpen == undefined){
        var item = findChildByClassName(obj.parentNode, "simpleTable");
        obj.isOpen = !(item.style.display == "none"); // автоопределение состояния
    }
    return obj.isOpen;
}

/**
 * Обобщенный метод для скрытия блока, лежащего в одной оболочке с контролом скрывающим его
 * У объекта могут быть события onHide и onShow которые будут выполняться при отображения или скрытия
 * @param obj объект, контрол по нажатию на который необходимо скрывать данные
 * @param relativeClassName имя класса блока, который необходимо скрыть
 * @param hiddenText текст который будет альтернативным относительно исходного состояния элемента
 */

function showHideRelativeElement (obj, relativeClassName, altText, hideSelector)
{
      var item = findChildByClassName(obj.parentNode, relativeClassName);
      var itemHiddenCount = findChildByClassName(obj.parentNode, "linksListHiddenCount");
      // Флаг состояния.
      if (obj.isOpen == undefined)
        obj.isOpen =  !(item.style.display == "none"); // автоопределение состояния
      // Если не существуют данные о текстах то сохраняем их
      if ( obj.showText == undefined )
        obj.showText = obj.isOpen?(obj.textContent||obj.innerText):altText;
      if ( obj.hideText == undefined )
        obj.hideText = !obj.isOpen?(obj.textContent||obj.innerText):altText;
      // если открытое то закрываем
      if (obj.isOpen)
      {
          obj.isOpen = false;
          obj.innerHTML = obj.hideText;
          obj.stepShow = 0;
          if (obj.onHide != undefined) obj.onHide(item);
          item.style.display = "";
          //плавное скрытие
          hideWithFadeIn(obj,item);
          setHideStyleLinkListTitle(obj,"linksListTitle");
          return ;
      }
      // отображаем
      obj.isOpen = true;
      //если в списке все элементы отобразятся после первого клика, т.е. "linksListHiddenCount" нет
      if (itemHiddenCount == undefined) {
          obj.stepShow = 1;
          obj.innerHTML = "Скрыть операции";
      }
      else
        obj.innerHTML = obj.showText;
      item.style.display = "";
      //плавное раскрытие
      showWithFadeOut(obj,item);

      if (obj.onShow != undefined) obj.onShow(item);
}

// Плавная прокрутка страницы наверх окна
function up(pos) {
    $('html,body').animate({scrollTop:pos},500);
}

//Постепенное раскрытие списка
function showWithFadeOut(obj, item) {
    var h = $(item).height();
    item.style.position = "relative";
    item.style.overflow = "hidden";
    if (item.fadeTimer != undefined)
        clearTimeout(item.fadeTimer );
    fadeOut(item, 0, h, function(){
        item.style.height = "auto";
        item.style.position = "";
        if ((obj.stepShow == 1)  && obj.id != "")
            obj.style.display = '';
    } );
}

//Постепенное скрытие списка
function hideWithFadeIn(obj,item,callback) {
    var h = $(item).height();
    item.style.position = "relative";
    item.style.overflow = "hidden";
    if (item.fadeTimer != undefined)
        clearTimeout(item.fadeTimer );
    fadeIn(item, h, 0, function(){
        item.style.height = "auto";
        item.style.overflow = "auto";
        item.style.display = "none";
        if (callback)
            callback();
    } );
    var upPos = offsetPosition(item) - 24;
    var scrollTop = window.pageYOffset || document.documentElement.scrollTop  || document.body.scrollTop;
    // прокручиваем страницу, только если не виден заголовок сворачиваемого списка
    if (scrollTop > upPos)
    {
        // Если сворачиваем список избранных ссылок, то прокручиваем страницу на самый верх. Если другой - то к заголовку списка
        $(item).parents('#favouriteLinks').size()>0 ?  up(0) :  up(upPos);
    }
}

function offsetPosition(element)
{
    var offsetTop = 0;
    do
    {
        offsetTop += element.offsetTop;
    }
    while (element = element.offsetParent);
    return offsetTop;
}

//коррекция стиля заголовка списка
function setHideStyleLinkListTitle(obj,titleClassName) {
    //при скрытии всего списка, внешний вид заголовка в исходную
    var titleItem = $(obj).parent('.'+titleClassName+'.hide')[0];
    if (titleItem != undefined)
    {
        titleItem.stepShow = undefined;
        titleItem.className = titleClassName;
    }
}

function showHideRegular(obj){
    var HIDDEN_TEXT = 'развернуть';
    var paymentsList = $(obj).closest(".grid").find("table");

    if (obj.wasOpened == undefined)
    {
      obj.wasOpened = true;
      obj.origInner = obj.innerHTML;
      obj.innerHTML = HIDDEN_TEXT;
      obj.style.backgroundPosition = "100% 110%";
      paymentsList.hide();
      return;
    }

    obj.wasOpened = undefined;
    obj.innerHTML = obj.origInner;
    obj.style.backgroundPosition = "100% -10%";
    paymentsList.show();
}





// Получить z-index по объекту.
function getZindex(obj){
   var highestIndex = 0;
   var currentIndex = 0;
   var elArray = Array();
   elArray.push(obj);
   var parent = obj.parentNode;
    while ( parent != document )
    {
        elArray.push(parent);
        parent = parent.parentNode;
    }

   for(var i=0; i < elArray.length; i++){
      if (elArray[i].currentStyle){
         currentIndex = parseFloat(elArray[i].currentStyle['zIndex']);
      }else if(window.getComputedStyle){
         currentIndex = parseFloat(document.defaultView.getComputedStyle(elArray[i],null).getPropertyValue('z-index'));
      }
      if(!isNaN(currentIndex) && currentIndex > highestIndex){ highestIndex = currentIndex; }
   }
   return(highestIndex);
}

//добавление выключаемого события
function addSwitchableEvent(elem, eventType, fn, key)
{
    if (key == null)
    {
        addEventListenerEx(elem, eventType, function(e) { fn(e); }, false);
        return ;
    }
    if (eventsKeyHash[key] != undefined)
    {
        eventsKeyHash[key] = true;
        return ;
    }

    eventsKeyHash[key] = true;
    addEventListenerEx(elem, eventType, function(e) { if ( window.eventsKeyHash[key] ) fn(e); }, false);
}
// Переключатель событий
// ! события не удаляются, а лишь выключаются
// @return: boolean true вслучаи удачи или false в случаи ошибки
var eventsKeyHash = {};
function switchEvent(key, off)
{
    if (eventsKeyHash[key] == undefined) return false;
    eventsKeyHash[key] = !((off != null)?off:eventsKeyHash[key]);
    return true;
}
//добавляем элемент на форму
function addElementToForm(name,value)
{
    var frm = document.forms[0];
    var el = document.createElement("input");
    el.setAttribute("type","hidden");
    el.setAttribute("name",name);
    el.setAttribute("value",value);
    frm.appendChild(el);
    return;
}

// Сохраняет значение вертикального смещения страниц в куки
function saveOffset(){
        // Записываем информацию в куки, используя функции из cookie.js, время жизни кук равно
        //  времени 10 мин
        setCookie("isFormSubmit", "true"); // Признак перехода с формы
        setCookie("offset", getScrollTop()); // Смещение
        //отрезание параметров по необходимости
        var urlStr = document.location; // Текущий URL
        var paramIdx = urlStr.href.indexOf('\?');
        var path = (paramIdx == -1 ? urlStr : urlStr.href.slice(0,paramIdx));
        setCookie("currentUrl", path);
}

//Сделать недоступным или доступным для редактирования елемент
function disableOrEnableElem(elemName, disable)
{
      getElement(elemName).disabled = disable;
}

//Сделать недоступным или доступным для редактирования поле
function disableOrEnableField(elemName, disable)
{
      getField(elemName).disabled = disable;
}
/**
 * Функция для установки поля статуса readOnly
 * @param elemName - название поля
 * @param state - статус (true||false)
 */
function setFieldReadOnlyState (elemName, state)
{
     getField(elemName).readOnly = state;
}

//изменение статуса отображения продукта на главной странице
function changeShowInMain(linkId, productType, url)
{
    var params = "id="+linkId + "&productType=" + productType + "&operation=button.showInMain";
    ajaxQuery(params, url, function(data){});
}
/**
 * функция для удаленея элемента из массива по его значению
 * arrayName - массив из которого удаляем элемент
 * arrayElement - элемент(значение) которое надо удалить
 * isUnique - признак того что значение уникальное (поумолчанию false)
 */
function removeElementByValue (arrayName,arrayElement, isUnique)
 {
     for(var i=0; i<arrayName.length; i++ )
        if(arrayName[i]==arrayElement)
        {
            arrayName.splice(i,1);
            if (isUnique) return ;
        }
 }

/**
 * устанавить объекту (obj) позиционирвание такое-же как и объекту likeObj
 * @param obj объект который выравниваем
 * @param likeObj объект относительно которого выравниваем
 * @param centerObj (необязательный параметр) объект - ребенок объекта obj, который будет являться центровочным
 */
function setPositionLikeObj (obj, likeObj, centerObj)
{
    obj = ensureElement(obj);
    var objPos = absPosition(obj);
    likeObj = ensureElement(likeObj);
    var likePos = absPosition(likeObj);
    if (centerObj!=null)
    {
        centerObj = ensureElement(centerObj);
        var centerObjPos = absPosition(centerObj);
        obj.style.left = objPos.left - (centerObjPos.left - likePos.left) + "px";
        obj.style.top =  objPos.top -  (centerObjPos.top - likePos.top) +"px";
    }
    else
    {
        obj.style.left = likePos.left+"px";
        obj.style.top = likePos.top+"px";
    }
}

/**
 * устанавить объекту (obj) позиционирвание под объектом afterObj
 * @param obj объект который выравниваем
 * @param afterObj объект относительно которого выравниваем
 */
function setPositionAfterObj(obj, afterObj)
{
    var afterObjPos = absPosition($(afterObj).get(0));
    $(obj).css('left', afterObjPos.left);
    $(obj).css('top', afterObjPos.top + $(afterObj).outerHeight());
}

/**
 * функция возращающая "цель" события
 * @param e
 */
function getEventTarget (e)
{
    var targ;
    if (!e) var e = window.event;
    if (e.target) targ = e.target;
    else if (e.srcElement) targ = e.srcElement;
    if (targ.nodeType == 3) // defeat Safari bug
        targ = targ.parentNode;
    return targ;
}

/**
 * Функция, собирающая GET-параметры из query string. Возвращает объект, обеспечивающий доступ к параметрам по
 * именам
 */
function mapGETParameters()
{
    var url = document.location.toString();
    url.match(/\?(.+)$/);

    var paramsArray = RegExp.$1.split('&');

    var mapGETParameters = {};
    for (var i=0; i<paramsArray.length; i++)
    {
        var pair = paramsArray[i].split('=');
        mapGETParameters[pair[0].toString()] = decodeURI(pair[1]);
    }
    return mapGETParameters;
}

/**
 * Функция, возвращающая значение GET-параметра по имени
 * @param name имя параметра
 */
function getQueryStringParameter(name)
{
    if (name == null || name == '')
    {
        return '';
    }
    var param = mapGETParameters()[name];
    return param == null ? '' : param;
}

/**
 * Метод для плавного "раскрытия" блока использует fadeObj.fadeTimer.
 * @param fadeObj - объект который раскрывается
 * @param from - начальная высота
 * @param to - конечная высота
 * @param callback - функция дергается после окончания раскрытия
 */
function fadeOut(fadeObj, from, to, callback)
{
    var hep = (to - from) / 8;
    //защита от зацикливания
    if (hep < 5 && hep > 0) hep = 5;
    if (hep < 0 && hep > -5) hep = -5;

    var halfTo = from + hep;
    // правка погрешности смещения
    if (halfTo > to)
        halfTo = to;
    fadeObj.style.height = halfTo + "px";
    if (halfTo < to)
        fadeObj.fadeTimer = setTimeout(function ()
        {
            fadeOut(fadeObj, halfTo, to, callback);
        }, 20);
    else if (callback != undefined)
        callback();
}

/**
 * Метод для плавного "скрытия" блока использует fadeObj.fadeTimer.
 * @param fadeObj - объект который скрывается
 * @param from - начальная высота
 * @param to - конечная высота
 * @param callback - функция дергается после окончания скрытия
 */
function fadeIn(fadeObj, from, to, callback)
{
    var hep = (from - to) / 8;
    //защита от зацикливания
    if (hep < 5 && hep > 0) hep = 5;
    if (hep < 0 && hep > -5) hep = -5;

    var halfTo = from - hep;
    // правка погрешности смещения
    if (halfTo < to)
        halfTo = to;
    fadeObj.style.height = halfTo + "px";
    if (halfTo > to)
        fadeObj.fadeTimer = setTimeout(function ()
        {
            fadeIn(fadeObj, halfTo, to, callback);
        }, 20);
    else if (callback != undefined)
        callback();
}

/**
 * Метод отвечающий за логику расскрытия и закрытия дополнительных параметров фильтра
 * @param obj
 * @param hiddenValue
 */
function openMoreFilter (obj, hiddenValue)
{
    var moreFilter = findChildByClassName(obj.parentNode.parentNode, "filterMore"); // контейнер расширенного фильтра
    var filterButton = findChildByClassName(obj.parentNode.parentNode, "filterButton"); // кнопка не расширенного фильтра

    var isOpenEvent = obj.innerHTML != obj.rel; // флаг отвечающий на вопрос открыть или закрыть фильтр
    // obj.originData хранилище орегинальных, истеных значений элемента
    if (obj.originData == undefined)
            obj.originData = { innerHTML : obj.innerHTML, className: obj.className};

    if (isOpenEvent) obj.originData.filterButtonDisplay = filterButton.style.display == "";

    hideOrShow(filterButton, true);

    hideOrShow(moreFilter, !isOpenEvent);

    if (isOpenEvent)
    {
        obj.innerHTML = obj.rel;
        obj.rel = obj.innerHTML;
        obj.className += " opened";
        setElement(hiddenValue, "true"); // сохраняем флаг открытие/закрытия
        return ;
    }

    setElement(hiddenValue, "false"); // сохраняем флаг открытие/закрытия

    if (obj.originData != undefined)
    {
        obj.innerHTML = obj.originData.innerHTML;
        obj.className = obj.originData.className;
    }
}
/**
 * вставляет дополнительные пробелы в слове если оно больше длинны limit
 * @param str строка
 * @param limit длинна ограничитель
 * @return возращает измененную строку
 */
function wordBreak(str, limit)
{
    var words = str.split(" ");
    var result = "";
    for (var i = 0; i < words.length; i++)
    {
        result += " ";
        if (words[i].length > limit)
            for (var j = 0; j < words[i].length / limit; j++)
            {
                if (j>0) result += " ";
                result += words[i].substr(limit*j,limit);
            }
        else
            result += words[i];
    }
    return result;
}

function RoundingMode()
{
    this.UP = 0; //До целого в большую сторону
    this.HALF_UP = 1;//До целого по правилам округления
    this.DOWN = 2; //Отбросить дробную часть
}

function rounding(number, roundMode)
{
    if(roundMode == RoundingMode.UP)
        return Math.ceil(number);
    if(roundMode == RoundingMode.HALF_UP)
        return Math.round(number);
    if(roundMode == RoundingMode.DOWN)
        return Math.floor(number);
}


// Преобразует число в строку формата 1_separator000_separator000._decimal
function FloatToString(_number,_decimal,_separator, _maxPower, _roundMode)
// _number - число любое, целое или дробное не важно
// _decimal - число знаков после запятой
// _separator - разделитель разрядов
//_maxPower - степень до которой не отображается 10^POWER, и число отображается как есть
//_roundMode - режим округления
    {
// определяем, количество знаков после точки, по умолчанию выставляется 2 знака
    var decimal=(typeof(_decimal)!='undefined')?_decimal:2;

// определяем, какой будет сепаратор [он же разделитель] между разрядами
    var separator=(typeof(_separator)!='undefined')?_separator:'';

// определяем, какой будет разряд, если число превышает 10^maxPower, то приводим к виду #.#*10^###
    var maxPower=(typeof(_maxPower)!='undefined')?_maxPower: 21;

    // определяем режим округления.
    var roundMode=(typeof(_roundMode)!='undefined')?_roundMode: RoundingMode.HALF_UP;

// преобразовываем входящий параметр к дробному числу, на всяк случай, если вдруг
// входящий параметр будет не корректным
    var r=parseFloat(_number);
    if (isNaN(r))
        return r;
// так как в javascript нет функции для фиксации дробной части после точки
// то выполняем своеобразный fix
    var exp10=Math.pow(10,decimal);// приводим к правильному множителю

    r=rounding(r*exp10, roundMode)/exp10;// округляем до необходимого числа знаков после запятой

// преобразуем к строгому, фиксированному формату, так как в случае вывода целого числа
// нули отбрасываются не корректно, то есть целое число должно
// отображаться 1.00, а не 1
    var number = Number(r);
    var rr=number.toFixed(decimal).toString().split('.');

// разделяем разряды в больших числах, если это необходимо
// то есть, 1000 превращаем 1 000
    var b=rr[0].replace(/(\d{1,3}(?=(\d{3})+(?:\.\d|\b)))/g,"\$1"+separator);
    var resultLine = b;
    if (rr.length > 1)
        resultLine += '.' + rr[1];

    // если в записи числа присутствует экспоненты, то приводим к виду #.#*10^###
    if (matchRegexp(resultLine, /\d+ ?\.\d+\e(\+|\-)\d+/))
    {
        var remainder = resultLine.split('.')[1];
        var power = remainder.replace(/\d+\e\+?/,"");
        var fractionalPart = remainder.substr(0,1);
        if (fractionalPart.length > 0)
            return b + "." + fractionalPart + "&times;10<sup>&nbsp;" + power + "</sup>";// возвращаем результат
        else
            return b + "&times;10<sup>&nbsp;" + power + "</sup>";// возвращаем результат
    }

    //  если число не превышает 10^maxPower степени, то так и вставляем
    if (number < Math.pow(10,maxPower))
        if (decimal > 0)
            return b + '.' + rr[1];
        else
            return b;

    // иначе приводим к виду #.#*10^###
    var realNumber = rr[0];
    return realNumber.substr(0,1) + "." + realNumber.substr(1,1) + "&times;10<sup>&nbsp;" + (realNumber.length - 1) + "</sup>";// возвращаем результат
}

/**
 * Открывает окно с печатной формой заявки на кредит
 * @param paymentId - id платежа
 * @param event   - нужно для кроссбраузерности
 */
function printLoan(paymentId, event)
{
    var printCheckUrl = "/PhizIC/private/payments/print.do";
    openWindow(event, printCheckUrl + "?id=" + paymentId, "", "resizable=1,menubar=1,toolbar=0,scrollbars=1,width=800,height=700");
}


/**
 * Меняет значение на элементе с одного переданного на другое переданное
 * @param elem - элемент, обладающий свойством value,
 * @param name1 - первое значение
 * @param name2 - второе значение
 */
function changeElemntValue(elem, name1, name2)
{
   elem.value = elem.value == name1 ? name2 : name1;
}

/**
 * Скрывает или показывает элементы с заданными классами
 * @param class1 - класс одного элемента
 * @param class2 - класс другого элемента
 */
function hideOrShowByClass(class1, class2)
{
    $('.' + class1).toggle();
    $('.' + class2).toggle();
}


/**
 * Копирует значение переданного элемента в значение элемента по переданному id
 * @param copyFromElement - переданный элемент, из которого копируем
 * @param copyToId - id Элемента, в который копируем значение copyFromElement
 */
function copyValueFromElementToElementById(copyFromElement, copyToId)
{
   document.getElementById(copyToId).value = copyFromElement.value;
}

/**
 * Автоклик на кнопке с id = buttonId, если значения элемента с id = textHiddenId равно значению compareValue
 * @param buttonId  - id элемента, на котором кликаем
 * @param textHiddenId - id элемента, значение которого считываем
 * @param compareValue  - значение для сравнения
 */
function autoClickLanguageButton(buttonId, textHiddenId, compareValue)
{
    var requestElement = document.getElementById(textHiddenId);
    if (requestElement != null && requestElement.value == compareValue)
         document.getElementById(buttonId).click();
}


/**
 * Метод для загрузки данных в блок единственный раз
 * @param url адрес данных
 * @param id идентификатор блока
 */
function loadAjaxDataOnce (url, id)
{
   var obj = document.getElementById(id);

   if (obj == undefined) throw "Объект с id="+id+" не найден.";

   if (obj.loadedData == url) return;

   ajaxQuery(null, url, function(data){
       obj.innerHTML = data;
   }, null, false);
   obj.loadedData = url;

}

/**
 * Проверяет можно ли разрешить действие нажатой клавише
 * @param keyCode код клавиши
 */
function canChange(keyCode)
{
    switch (keyCode)
            {
        case KEY_BACKSPACE: //BackSpace
        case KEY_DELETE:    //Delete
        case 0x21:          //PageUp
        case 0x22:          //PageDown
        case 0x2D:          //Insert
        case 0x23:          //End
        case 0x24:          //Home
        case 0x25:          //стрелка влево
        case 0x26:          //стрелка вверх
        case 0x27:          //стрелка вправо
        case 0x28:          //стрелка вниз
                 return true;
        default: return false;
    }
}

/**
 * Навешивание ограничений на поле ввода
 * @param field само поле
 * @param restrictCallbackFunction функция, которая ограничивает ввод(например, только цифры)
 * @param correctCallbackFunction функция-корректировщик, некоторые браузеры имеют свои баги,
 * функция вызывается при отпускании клавиши, и корретирует введенное значение
 */
function restrictInputKeys(field, restrictCallbackFunction, correctCallbackFunction)
{
    $(field).keypress(function(e){
        if(((e.which != 0 && e.charCode != 0) // основное условие
                    // для оперы не достаточно если не определен charCode(для старых версий)
                    && !(window.opera && e.charCode == null)
                    // необходимо проверить сочетания (ctrl+V)
                    && !e.ctrlKey)
            // т.к. он не имеет charCode, но на служебные символы не посылает события press
            || isIE())
        {
            return !restrictCallbackFunction || restrictCallbackFunction(e);
        }

        var code = e.which || e.keyCode;
        if(canChange(code))
            return true;

        /* комбинации клавиш(ctrl+C, ctrl+Z, ctrl+V, ctrl+A) */
        switch(code){
            case 0x0D: // Enter
            case 0x09: // Tab
                return true;
            /* для оперы */
            case 0x01: // ctrl+A
            case 0x41: // ctrl+A opera 9
            case 0x1A: // ctrl+Z
            case 0x5A: // ctrl+Z opera 9
            case 0x18: // ctrl+X
            case 0x58: // ctrl+X opera 9
            case 0x03: // ctrl+C
            case 0x43: // ctrl+C opera 9
            case 0x16: // ctrl+V
            case 0x56: // ctrl+V opera 9
                if(window.opera && e.ctrlKey)
                    return true;
                break;
            /*  для всех остальных */
            case 0x78:   // ctrl+X
            case 0x7A:   // ctrl+Z
            case 0x63:   // ctrl+C
            case 0x76:   // ctrl+V
            case 0x61:   // ctrl+A
                if(e.ctrlKey)
                    return true;
                break;
            case 0x11:   // ctrl opera 9
                return true;
        }

        return !restrictCallbackFunction || restrictCallbackFunction(e);
    })
    .keyup(function(e){
        if(correctCallbackFunction == null)
            return true;

        var position = getCaretPosition(this);
        /* после того как отпустили клавишу, корректируем введеное значение */
        correctCallbackFunction(e);
        setCaretPosition(this, position[0], position[1]);

    })
    .bind('paste', function(e){
        /* приходится извращаться чтобы при вставке вырезать все НЕ цифры, т.к. на этапе вставки не узнать что вставляется(за исключением некоторых новых браузерво) */
        setTimeout(function(){
            correctCallbackFunction(e);
        }, 50);
    });
}

/* считает количество символов в строке (с учетом "\n".length == 2) */
function getLineSize(line)
{
    if (line == null)
        return 0;
    /* для js \n это один символ, заменяем его на два символа и считаем длину строки (\r? для ие: там перенос строки это \r\n) */
    return line.replace(/\r?\n/g, "12").length;
}

/* подстрока от текущей с учетом, что длина символа переноса строки ("\n") равна 2 */
function getSubString(text, size)
{
    if (text == null)
        return text;

    var resultLine = "";
    var currentLineNumber = 0;

    /* делим по переносу строки */
    var lines = text.split(/\r?\n/);
    var linesNumber = lines.length;
    do
    {
        /* и собираем resultLine */
        /* текущая строка */
        var currentLine = lines[currentLineNumber];
        /* сколько еще символов влезает */
        var remaindLength = size - (resultLine.length + currentLineNumber++);
        /* если длина строки + 2 (символ переноса строки) больше остатка, то обрезаем текущую строку и пибавляем ее к результату */
        if (currentLine.length + 2 > remaindLength)
            return resultLine + currentLine.substr(0, remaindLength);
        /* значит строка влезает целиком */
        resultLine = resultLine + currentLine + "\n";
    }
    /* пока не закончатся строки */
    while (currentLineNumber < linesNumber);

    return text;
}

/**
 * Устанавливает максимально разрешенное количество введенных символов для textArea
 * @param e
 * @param textArea текущая textarea
 * @param maxLength максимальное число введенных символов
 */
function initAreaMaxLengthRestriction(textArea, maxLength, drawStats)
{
    var textarea = document.getElementById(textArea);
    $(textarea).attr("area-max-length", maxLength);
    var drawStatistics = function()
    {
         if(drawStats)
         {
             $('#statistics').html("Введено " + getLineSize(textarea.value) + " симв. из " + maxLength);
         }
    };
    if(drawStats)
    {
        $(textarea).after("<div id='statistics'></div>");
        drawStatistics();
    }
    textarea.onkeypress = function(event)
        {
            if(event == null)
            {
                if (textarea.value.length > maxLength)
                   textarea.value = textarea.value.substr(0, maxLength);
                drawStatistics();
                return true;
            }
            if (textarea.value.length < maxLength){
                drawStatistics();
                return true;
            }
            //Так же проверяет на сочетание клавиш ctrl+C, ctrl+Z, ctrl+V, ctrl+A
            if (canChange(event.keyCode) || ((event.ctrlKey) && ((event.charCode == 120) || (event.charCode == 99) || (event.charCode == 118) || (event.charCode == 97))))
            {
                drawStatistics();
                return true;
            }
            drawStatistics();
            return false;
        };
    var func = function()
        {
            if (getLineSize(textarea.value) > maxLength)
                textarea.value = getSubString(textarea.value, maxLength);
            drawStatistics();
            return true;
        };
    textarea.onkeyup = func;
    textarea.onfocus = func;
    textarea.onmousemove = func;
}

/**
 * Метод подменяющий action формы
 * @param newUrl
 */
function changeFormAction (newUrl)
{
    var frm = document.forms[0];
    frm.action = newUrl;
    frm.onsubmit = null;
}

/**
 * Помещает в хэш-мап операцию operation=methodKey и все поля формы,
 * и затем преобразует эту таблицу в строку типа имя=значение?имя=значение
 * @param methodKey
 * @param formId
 */
function ajaxQueryCallMethodParam(methodKey, formId)
{
    var result = serializeForm(formId);
    if (result.length > 0) result += "&";
    result += "operation="+methodKey;
    return result;
}

function serializeForm(formId)
{
    if (isEmpty(formId))
    {
        return $('form').serialize();
    }
    var form = $('#' + formId);
    if (form.size() == 0)
    {
        return $('form').serialize();
    }
    var serialized = form.serialize();
    if (!serialized) //сериализуемый объект не форма
    {
        return serializeDiv(formId);
    }
    return serialized;
}

/**
 * Объект для работы с всплывающим окном подтверждения
 */
var confirmOperation ={
    // тип подтверждения
    type: { card: "card", sms: "sms", cap: "cap", push: "push"},
    windowId: "oneTimePasswordWindow",
    submitIneted: false,
    followSubmit: true,
    lock: false,
    /**
     * ajax-открытие формы ввода одноразового пароля
     * @param methodKey
     * @param url
     */
    openConfirmWindow: function (methodKey, url) {
        // защита от зависание при многократном нервном нажатии на кнопку
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.initSubmit();
        ajaxQuery(
          ajaxQueryCallMethodParam(methodKey),
          url,
          function(data){
              myself.lock = false;
              if(trim(data) == '')
              {
                  window.location.reload();
                  return;
              }
              ensureElement(myself.windowId).innerHTML = data;
              win.open(myself.windowId);
              myself.initLoadedData();
          }
          );
    },
    /**
     * ajax-открытие формы ввода одноразового пароля, при условии, что вставляется jsp с тегом form
     * @param methodKey
     * @param url
     */
    openFormConfirmWindow: function (methodKey, url) {
        // защита от зависание при многократном нервном нажатии на кнопку
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.initSubmit();
        ajaxQuery(
          ajaxQueryCallMethodParam(methodKey),
          url,
          function(data){
              myself.lock = false;
              if(trim(data) == '')
              {
                  window.location.reload();
                  return;
              }
              var opWindow = ensureElement(myself.windowId);
              $(opWindow).empty();
              $(opWindow).append($(data));

              win.open(myself.windowId);
              myself.initLoadedData();
          }
          );
    },
    /**
     * ajax-валидация одноразового пароля
     * @param methodKey
     * @param url
     * @param type
     * @param completeCallback
     * @param formId
     * @param errorCallback
     */
    validateConfirmPassword: function (methodKey, url, type, completeCallback, formId, errorCallback)
    {
        var myself = this;
        // защита от зависание при многократном нервном нажатии на кнопку
        if (this.lock) return;
        // предварительная валидация
        if ( !this.validateField(type) ) return ;
        showOrHideAjaxPreloader (); // ручное управление прелоудером
        this.lock = true;
        ajaxQuery(
            ajaxQueryCallMethodParam(methodKey, formId),
            url,
            function(data){
                if(trim(data) == '')
                {
                    if (errorCallback != undefined && errorCallback != '')
                        errorCallback();
                    else
                        window.location.reload();
                }
                else if(trim(data) == 'next')
                {
                    myself.followSubmit = false;
                    if (completeCallback != undefined)
                        completeCallback();
                    else callOperation(null, 'button.nextStage');
                }
                else {
                    showOrHideAjaxPreloader (false); // прячем затемнение только если произошла ошибка валидации
                    var winErr =  $('#' + myself.windowId);
                    $(winErr).empty();
                    $(winErr).append($(data));
                    win.open(myself.windowId);
                    myself.initLoadedData();
                    myself.lock = false;
                }
            },
            null,
            false // отключаем прелоудер
        );
    },

    initLoadedData: function ()
    {
        var confirmWin = ensureElement(this.windowId);
        payInput.setEvents(payInput, confirmWin);
        // errors div
        var errorDiv = findChildByClassName(confirmWin, "warningMessage");
        if (errorDiv != undefined)
        {
            var errorsConteiner = findChildByClassName(errorDiv, "messageContainer");
            if (errorsConteiner != undefined)
            {
                var hide = trim(errorsConteiner.innerHTML)=='';
                hideOrShow(errorDiv, hide);
            }
        }

        // set focus and scroll
        var confirmInput = $("#" + this.windowId + " input.confirmInput");
        confirmInput.ready(
            function()
                {
                    // Dirty IE6 hack
                    // ИЕ имеет множество проблем, очередная проблема заложена в фокусе,
                    // он немного опаздывает и периодически не отрабатывает.
                    // Полагаю данное множество таймаутов поможет решить проблему ( мне помогло =)) )
                    if (isIE(6))
                    {
                        setTimeout(function()
                        {
                            confirmInput.focus();
                            setTimeout(function()
                            {
                                confirmInput.focus();
                                // для мобильных устройств прокручивать не надо, так как там нет центровки
                                if (isMobileDevice()) return;
                                setTimeout(function()
                                {
                                    window.scrollTo(0, 0); // так как подтверждения могут быть объемными, мотаем окно вверх
                                }, 100);
                            }, 100);
                        }, 100);
                    }
                    else
                    {
                        confirmInput.focus();
                        // для мобильных устройств прокручивать не надо, так как там нет центровки
                        if (isMobileDevice()) return;
                        window.scrollTo(0, 0); // так как подтверждения могут быть объемными, мотаем окно вверх
                    }

                }
        );
    },
    /**
     * метод для первичной валидации поля
     * @param type
     */
    validateField: function (type)
    {
        var errorText = {
            sms: "Введите пароль, полученный через sms.",
            card: "Введите одноразовый пароль с чека.",
            cap: "Введите отображенное на карте число.",
            push: "Введите пароль, полученный через push-сообщение."
        };

        var confirmWin = ensureElement(this.windowId);
        var field = findChildByClassName(confirmWin, "confirmInput");
        if (field == undefined ) return false;

        if (trim(field.value) != '' ) return true;

        addError(errorText[type], 'warningMessages', true);
        var errorDiv = findChildByClassName(confirmWin, "warningMessage");
        hideOrShow(errorDiv, false);


        var row = findParentByClassName(field, "form-row");
        var fieldTitle = findChildByClassName(row, "fieldTitle");
        fieldTitle.style.color='red';

        field.focus();
        return false;
    },

    initSubmit: function ()
    {
        if (this.submitIneted) return;
        var frm = document.forms[0];
        var myself = this;
        var defaultSubmit = frm.onsubmit;
        frm.onsubmit = function (e)
        {
            var confirmWindow = win.window[myself.windowId+"Win"];
            if (confirmWindow != undefined && confirmWindow.opened() && myself.followSubmit)
            {
                return false;
            }
            return defaultSubmit==undefined?true:defaultSubmit(e);
        };
        this.submitIneted = true;
    }

};
/**
 * возвращает туекущую системую дату в формате DD<separator>MM<separator>YYYY.
 *
 * @param separator
 */
function getCurrentDate(separator)
{
    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    return (day < 10 ? "0" + day : day) + separator
         + (month < 10 ? "0" + month: month) + separator
         + (year);
}

/**
 * Возвращает разницу во времени в миллисекундах для двух дат.
 * @param date1 первая дата.
 * @param date2 вторая дата.
 */
function getDeltaTime(date1, date2)
{
    var fun = function(date) {
        return date.getMilliseconds() + (date.getSeconds() + (date.getMinutes() + date.getHours()*60)*60)*1000;
    }

    var lt = fun(date1);
    var ct = fun(date2);
    while (ct < lt)
        ct += 1000*60*60*24;

    return ct - lt;
}

/**
 * Объект для проведения анимации.
 *
 * @param duration продолжительность анимации в миллисекундах.
 * @param onChangeFrame функция, вызываемая при смене кадра анимации
 (параметры: position [0..1] - в каком состоянии анимация).
 * @param onFinishAnimation функция, вызываемая при завершении
 анимации.
 * @param repeat зацикливание.
 * @param animateMethod метод изменения значения параметра анимации.
 * @constructor
 */
var Animator = function(duration, onChangeFrame, onFinishAnimation, repeat, animateMethod)
{
    var self = this;

    /**
     * Линейное движение. Скорость не меняется.
     *
     * @param p параметр анимации.
     * @returns number модифицированный параметр анимации.
     * @constructor
     */
    self.LINEAR = function(p)
    {
        return p;
    };

    /**
     * Изменение скорости анимации по синусу.
     *
     * @param p параметр анимации.
     * @returns number модифицированный параметр анимации.
     * @constructor
     */
    self.SINUS = function(p)
    {
        return (Math.sin(-Math.PI/2 + Math.PI*p) + 1)/2;
    };

    self.duration = duration;
    self.onChangeFrame = onChangeFrame;
    self.onFinishAnimation = onFinishAnimation;
    self.repeat = repeat;
    self.animateMethod = animateMethod == "SINUS" ? self.SINUS : self.LINEAR;

    self.startTime;
    self.interval;

    /**
     * Запускает анимацию.
     */
    self.start = function ()
    {
        self.startTime = new Date();
        self.interval = setInterval(
            function()
            {
                var dt = getDeltaTime(self.startTime, new Date());
                if (dt > self.duration)
                    dt = self.duration;
                var p = self.animateMethod((dt + 0.0)/self.duration);
                if (self.onChangeFrame)
                    self.onChangeFrame(p);
                if (dt >= self.duration) {
                    if (repeat)
                    {
                        self.startTime = new Date();
                    }
                    else
                    {
                        clearInterval(self.interval);
                        if (self.onFinishAnimation)
                            self.onFinishAnimation();
                    }
                }
            }, 20
        );
    };

    self.stop = function()
    {
        clearInterval(self.interval);
    }
};

/**
 * Проверяет количество элементов и, если это один элемент, то отмечает его.
 * Вызывается, если необходимо в списке совершить какие-либо действия (редактирование, удаление...)
 * без указания элемента.
 * @param selectedIds - название списка элементов (radio или checkbox)
 * @param func - функция для выполнения(не обезательный пораметр)
 */
function checkIfOneItem(selectedIds,func)
{
    var ids;
    if (selectedIds == null)
        selectedIds = "selectedIds";
    if (isEmpty(selectedIds) || (ids = document.getElementsByName(selectedIds)) == undefined)
        return;
    if (ids.length == 1)
        ids[0].checked = true;

    if (func !=null)
        func();
}

/**
 *замена спецсимволов HTML на соотв. последовательности
 */
function escapeHTML(text)
{
    if (text == null)
        return;

    return text.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
}

function openInf (link, swf)
{
  /* if (link.initInf == undefined)
   {
        NanoInf.init({
            source:swf,
            swfWidth:470,
            swfHeight:320,
            showWindow:false,
            transparent:false
        });
       link.initInf = true;
   }*/
   win.open('infWindow');
}

/**
 * Метод для определения является ли запрос с мобильного устройства
 * Телефона/планшета
 */
function isMobileDevice()
{
    var userAgentIgnorList = ["Mobile", "iPhone", "iPad", "iPod", "Mac"];
    for (var i=0; i<userAgentIgnorList.length; i++)
        if(navigator.userAgent.indexOf(userAgentIgnorList[i]) != -1)
            return true;

    return false;
}

/**
 * Карта < ISO-код валюты, символ валюты >
 * Заполняется в jsp.
 */
var currencySignMap = {
    map: {},
    /**
     * Ищет в карте символ валюты по ее ISO-коду и, если символ найден, то возвращает его, иначе - ISO-код.
     * @param code ISO-код валюты
     */
    get: function(code) {
        return this.map[code] == undefined ? code : this.map[code];
    }
};

function hideOrShowRegions(id)
{
    var elem = document.getElementById('otherRegions'+id);
    if (elem != undefined)
        hideOrShow('otherRegions'+id);
}

/**
 * Разрешено ли перенаправление на другую страницу.
 * По-умолчанию разрешено. На конкретных страницах необходимо переопределить функцию.
 * @return true - разрешено
 */
function redirectResolved()
{
    return true;
}

//выполняет функцию func если перенаправление разрешено
function execIfRedirectResolved(func)
{
    if (redirectResolved())
        func();
}

/**
 * запись в журнал статистики по баннерам события
 * @param bunnnerId - id баннера
 * @param type - тип события
 */
function bannerRequest(bunnnerId, type)
{
    if (bunnnerId != undefined)
    {
        if (typeof sendedBannerRequest != 'undefined')
        {
            if (sendedBannerRequest[bunnnerId])
                return;

            sendedBannerRequest[bunnnerId] = true;
        }
        var url = document.webRoot + "/private/async/advertising.do"
        var params = "operation=button." + type + "&currentBannerId=" + bunnnerId;

        ajaxQuery(params, url, function(msg){}, null, false);
    }
}

/**
 * запись в журнал статистики по ПБО
 * @param panelId
 * @param type
 */
function panelRequest(panelId, page_url)
{
    if (panelId != undefined)
    {
        var url = document.webRoot + "/private/async/quick/payment/panel.do"
        var params = "quickPaymentPanelBlokId=" + panelId

        ajaxQuery(params, url, function(msg){window.location=page_url;}, null, false);

    }
}

/**
 * Остановка "всплывающих" событий
 */
function cancelBubbling(event)
{
    event = event || window.event; // кросс-браузерно
    event.stopPropagation ? event.stopPropagation() : (event.cancelBubble=true);
}

/**
 * Проверка доступности поставщиков в текущем регионе
 * @param providerIds идентификаторы поставщиков
 * @param callback функция, которая будет вызвана с параметром ввиде мапа(id поставщика : доступность в текущем регионе)
 */
function checkProvidersRegion(providerIds, callback)
{
    ajaxQuery(
            {providerIds : providerIds},
             document.webRoot +"/private/async/provider/checkProviderRegion.do",
            callback,
            "json",
            false);
}

//Удаляет все пробелы из строки
function getStringWithoutSpace(val){
    return val.replace(/ /g, '');
}

/**
 * Возвращает позицию каретки в поле node
 */
function getCaretPosition( node ){
    try{
        if ( document.selection ){
            var sel = document.selection.createRange();
            return [ -sel.moveStart( 'character', -node.value.length ), -sel.moveEnd( 'character', -node.value.length )];
        }
        if ( node.selectionStart || node.selectionStart == '0' )
            return [ node.selectionStart, node.selectionEnd ];

    }
    catch(ignore)
    {}
    return [0,0];
}
/**
 * Устанавливает каретку в поле node на позицию pos
 */
function setCaretPosition( node, posStart, posEnd){
    try{
        // mozilla
        if( node.setSelectionRange ){
            node.setSelectionRange( posStart, posEnd != null ? posEnd : posStart);
        }
        // ie
        else if ( node.createTextRange ){
            var range = node.createTextRange();
            range.collapse( true );
            range.moveEnd( 'character', posEnd != null ? posEnd : posStart);
            range.moveStart( 'character', posStart );
            range.select();
        }
    }
    catch(ignore)
    {}
}

function setBatteryVals(id, val1, val2)
{
    if (val1==0 && val2==0)
    {
        $('#activeBattery' + id).hide();
        $('#notActiveBattery' + id).show();
    }
    else
    {
        var battery = $('#battery' + id + ' .batteryCenter');
        var innerBattery = $('#battery' + id + ' .innerBattery');
        var per = val2/(val1 + val2);
        innerBattery.css("width", parseFloat(battery.css("width"))*per);

        $('#batteryHintUp' + id + ' .hintVal').html(FloatToString(val2, 2, ' '));
        $('#batteryHintDown' + id + ' .hintVal').html(FloatToString(val1, 2, ' '));
        $('#batteryHintRight' + id + ' .hintVal').html(FloatToString(val1 + val2, 2, ' '));

        $('#activeBattery' + id).show();
        $('#notActiveBattery' + id).hide();
    }
}

function parseFloatVal(val)
{
    if(val == undefined)
        return 0;
    if (typeof(val)=='number')
        return val;

    return parseFloat(val.replace(',', '.').replace(/ /g,''));
}

/**
 * Возвращаем день начала текущего квартала
 * @param date
 */
function getStartCurrentQuarterDate(date)
{
    var month = parseInt(date.getMonth()/3) * 3 + 1;//номер 1-го месяца в квартале. Считаем от 1 до 12.
    var year = date.getFullYear();
    var day = 1;
    return new Date(month + "/" + day + "/" + year);
}

/**
 * Возвращаем теущий квартал для даты
 * @param date
 */
function parseQuarter(date)
{
    var month = date.getMonth();
    var quarter = parseInt(month/3) + 1;//получаем номер квартала от 1 до 4-х
    return quarter + ' квартал ' + date.getFullYear();
}

/**
 * Делаем переход по адресу
 * @param url
 */
function goTo(url)
{
    window.location = url;
}

/**
 * Перезагружает страницу
 */
function reload()
{
    window.location = window.location.href;
}

/* Функции для работы с загрузкой операций в "Моих финансах" */
/**
 * Отображаем блок с операциями
 * @param id
 * @param date
 */
function showOperations(id, date)
{
    if ($('#'+id).css("display") != "none")
        return;
    getOperations(id, date);
}

/**
 * Скрываем блок с операциями
 * @param elem
 */
function closeOperations(id)
{
    $("#" + id).hide();
    $("#list" + id).empty();
}

//вытаскиваем параметры из адресной строки
function getGETParams(){
    return decodeURIComponent(window.location.search.slice(1))
            .split('&')
            .reduce(function _reduce (/*Object*/ a, /*String*/ b) {
                b = b.split('=');
                a[b[0]] = b[1];
                return a;
            }, {});
}
/* Скриптовая пагинация*/

/* Проверяет буква ли */
function isLetter(ch)
{
    return ch.match(/[a-zA-Z\u0410-\u044fЁ\u0451\u00CB]/i);
}

/**
 * Подменяет поля, реализуя функцию скрытия/отображания пароля
 * @param fieldId id input'а с паролем
 * @param flag true - отобразить пароль
 * @param blockId идентификатор блока, в котором находится поле
 */
function showHidePassword(fieldId, flag, blockId)
{
    // элемент с показанынм паролем
    var passwordElement = (blockId != null ? $("#" + blockId).find("#" + fieldId) : $("#" + fieldId))
            .filter("input[type='text'],input[type='password']").get(0);

    // если элемент не нашли, ничего не делаем
    if(passwordElement == null)
        return;

    // создаем копию элемента с такими же значениями аттрибутов(старые версии браузеров не поддерживают замену атритуба type)
    // приходится извращаться
    var copyInput = document.createElement("input");
    copyInput.type  = flag ? "text" : "password";
    copyInput.id    = passwordElement.id;
    copyInput.value = passwordElement.value;
    copyInput.size  = passwordElement.size;
    copyInput.name  = passwordElement.name;
    copyInput.tabIndex = passwordElement.tabIndex;
    copyInput.onfocus = passwordElement.onfocus;
    copyInput.onkeyup = passwordElement.onkeyup;
    if (passwordElement.autocomplete  != "")
        copyInput.autocomplete = passwordElement.autocomplete;
    copyInput.style.cssText = passwordElement.style.cssText;
    if(passwordElement.maxLength != -1)
        copyInput.maxLength = passwordElement.maxLength;

    // копируем навешенные события, если есть
    var events = $(passwordElement).data('events');
    if(events != null)
    {
        $.each($(passwordElement).data('events'), function(i, event){
            $.each(event, function(i, e){
              $(copyInput).bind(e.type, e.handler);
            });
        });
    }

    $(passwordElement).replaceWith(copyInput);
}

/**
 * Функция закрытия текущей вкладки
 * т.к. Опера 12 не разрешает закрывать окна, открытые не javascript-ом, сначала открываем, а потом закрываем окно
 * взято отсюда: http://forums.asp.net/t/1348028.aspx/1
 */
function closeCurrentWindow()
{
    window.open('','_parent','');
    window.close();
}

/**
 * Выбирает группу значений в дереве (все листья узла)
 * @param id - идентификатор
 * @param elem - узел
 * @param name - name листьев
 */
function checkGroupValues(id, elemId, name)
{
    var customSelect = $('#customSelect'+id);
    customSelect.find('input[name="'+name+'"]').val(elemId);

    customSelect.find('.customSelectList').hide();
    customSelect.find('.customSelectTopText').text($('#'+elemId).text());
    customSelect.find('.customSelectHintText').text($('#'+elemId).text());
}

/**
 * Открытие окна с расширенным описанием данных(договора, правила и т.д.)
 * @param name имя расширенного описания
 */
function openExtendedDescWin(name)
{
    var win = window.open(document.webRoot + '/private/extended/data.do?name=' + name, 'ExtendedDescriptionData', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=1000,height=600");
    win.moveTo(screen.width / 2 - 350, screen.height / 2 - 400);
}
/**
 * Подставляет в источник списания счет(карту) с ненулевым остатком, если он один и другой счет не был выбран
 * @param countNotEmptyLinks - количество счетов с положительным балансом
 * @param indexNotEmptyCards - индекс счета в списке
 */
function selectDefaultFromResource(countNotEmptyLinks, indexNotEmptyCards)
{
    var select = document.getElementById('fromResource');
    //если у клиента 1 карта(счет) с положительным балансом и она еще не выбрана, то удаляем строку "выберите ... списания" и выбираем ее
    if (countNotEmptyLinks == 1 && select.selectedIndex == 0 && select.value == "")
     {
        var del  = select.options[0];
        select.removeChild(del);
        select.options[indexNotEmptyCards-1].selected = true;
     }
}

function isEmpty(value)
{
    return value == undefined || value == null || trim(value) == "";
}

function isNotEmpty(value)
{
    return !isEmpty(value);
}

/**
 * Функция считает и обновляет количество символов, введенных в поле
 * @param element - id элемента, число символов которого нужно считать
 * @param counterID - id элемента с результатом подсчета
 */
function countSymbols(element, counterID)
{
    var counter        = document.getElementById(counterID);
    var tagsRegExp     = /(<\/?)([@|#])([\w \.,!="'&?()>|/]+)|(\\$\{[\w\.\?"'() :]+\})|({\d})|(%(?=\w)\w)|(([ ]+)((?= )|(?=\t))|([\t]+))|(\r?\n?)/g;
    var hasTranslit    = element.value.match(/(<# *\w+ +translit *[ ="'\w]*>)/g) != null;

    var cleared = element.value.replace(tagsRegExp, '');
    if (hasTranslit)
    {
        cleared = cleared.replace(/[A-z]*/g, '');
    }

    counter.innerHTML = String(cleared.length);
}

function getImageSize(parent)
{
    if (typeof parent.naturalWidth == "undefined")
    {
        // IE 6/7/8
        var i = new Image();
        i.src = parent.src;
        return i.width;
    }
        // HTML5 browsers
      return parent.naturalWidth;
}

/**
 * Маскирует номер телефона
 *  '+9(999)***9999 , 9999***9999 или (999) ***-99-99
 * @param templObj формат телефонного номера PHONE_NUMBER, PHONE_NUMBER_SHORT или PHONE_NUMBER_P2P
 * @param phoneNumber номер телефона
 * @returns {string}
 */
function getMaskedPhoneNumber(templObj,phoneNumber)
{
    if (!templObj.validate(phoneNumber))
    {
        console.log("Переданный номер не соответствует заданному формату");
        return null;
    }

    if (templObj.regexp == templateObj.PHONE_NUMBER_SHORT.regexp)
    {
        return phoneNumber.substring(0,4) +"***" +  phoneNumber.substring(7);
    }
    else if (templObj.regexp == templateObj.PHONE_NUMBER.regexp)
    {
        return phoneNumber.substring(0,7) +"***" +  phoneNumber.substring(7);
    }
    else if (templObj.regexp == templateObj.PHONE_NUMBER_P2P.regexp)
    {
        return phoneNumber.substring(0,6) +"***" +  phoneNumber.substring(9);
    }
    else if (templObj.regexp == templateObj.PHONE_NUMBER_INTERNATIONAL.regexp)
    {
        //+7 (911) ***1111
        return "+" + phoneNumber.substring(0,1) + " (" + phoneNumber.substring(1,4) + ") ***" + phoneNumber.substring(7);
    }
}

/**
 * Добавляет дополнительное сообщение.
 * @param msg сообщение.
 */
function addAdditionalMessage(msg)
{
    if (msg != null && msg != "")
        addMessage(msg, 'additionalMessages', true);
}

/**
 * запись в журнал статистики по уведомлениям
 * @param notificationId - id уведомления
 * @param notificationName - название уведомления
 * @param type - тип события
 */
function notificationRequest(notificationId,notificationName, offerId, offerType, type)
{
    if (notificationId != undefined)
    {
        var url = document.webRoot + "/private/async/notification.do"
        var params = "operation=button." + type + "&currentNotificationId=" + notificationId + "&currentName=" + notificationName + "&offerId=" + offerId + "&offerType=" + offerType;
        ajaxQuery(params, url, function(msg){}, null, false);
    }
}

/**
 * Вызов метода экшена ajax-запросом
 * @param url - путь к экшену
 * @param functionName - имя функции в экшене
 */
function callAjaxActionMethod(url, functionName)
{
    var params = "operation=button.";
    ajaxQuery(params + functionName, url, function(data){}, null, false);
}

/**
 * Сериализация div-а
 * @param divId идентификатор блока
 * @returns {*|jQuery}
 */
function serializeDiv(divId)
{
    return $('#' + divId).find('input, select, textarea').serialize();
}
/**
 * Отпправка submit на указанный URL с заданными параметрами.
 * @param path - URL
 * @param params - параметры
 * @param method - POST/GET
 */
function post(path, params, method)
{
    method = method || "post";

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}

/*Создание нового окна для переадресации на сайт программы "Лояльность",
        либо отображение всплывающего окна о том что url для перехода сгенерировать не удалось*/
function loyaltyWindow(url, event)
{
    event.cancelBubble = true;
    ajaxQuery(null, url, function(data)
    {
        var url = trim(data).replace(/&amp;/g, '&');
        if (url == "error")
        {
            var error = document.getElementById("externalWebSiteErrorMsg");
            if (error != undefined)
                error.innerText = "Вы не можете посмотреть Вашу программу лояльности. Пожалуйста, повторите попытку позже.";
            var i = win.open('externalWebSiteErrorWindow');
        }
        else if(url == "")
        {
            window.location.reload();
        }
        else
        {
            var div = document.createElement('div');
            div.innerHTML = url;
            window.open(
                    div.firstChild.nodeValue,
                    'loyalty',
                    "resizable=1,menubar=0,toolbar=0,scrollbars=1"
            );
        }
    });
}