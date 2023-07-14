function initMoneyFields(parentNode)
{
    formatFieldsByClassname(parentNode, 'moneyField');
}

function formatFieldsByClassname(parentNode, className)
{
    var parent      = parentNode == undefined ? document : parentNode;
    var moneyFields = $(parent).find('.'.concat(className));

    if ($.isEmptyObject(moneyFields.get(0)))
        return;

    moneyFields.initMoneyField();
    moneyFields.each(function ()
    {
        var currentInput = $(this);
        // ���� ������� maxLength �� �����:
        // � �� == -1
        // � ie == 2147483647
        if (this.maxDigitCount == undefined && !(this.maxLength <= 0 || this.maxLength == 2147483647))
        {
            this.maxDigitCount = this.maxLength;
            //��� �� $(this).removeAttr("maxlength") == $(this).attr("maxlength", 0),
            //������� ��������� ������� (����� ������� �������� ������� ��������� ������. 4/3 -- ������� ������ �����)
            if (isIE())
            {
                currentInput.attr("maxlength", Math.floor(this.maxDigitCount * 4 / 3));
            }
            else
            {
                currentInput.removeAttr("maxlength");
            }
        }

        currentInput.setMoneyValue(currentInput.val());
        //����� ����� ����������� ����� �������� ��������� ��������
        formValues.data[currentInput.attr('name')] = currentInput.val();
    });
}

(function ($)
{
    $.fn.extend({
        initMoneyField: function ()
        {
            if (!$.event._moneyFieldCache) $.event._moneyFieldCache = [];

            return this.each(
                function ()
                {
                    if (!this._moneyFieldId)
                    {
                        this._moneyFieldId = $.event.guid++;
                        $.event._moneyFieldCache[this._moneyFieldId] = new MoneyField(this);
                    }

                    var controller = $.event._moneyFieldCache[this._moneyFieldId];

                    controller.init();
                }
            )
        },
        setMoneyValue: function (value) {_forwardToController.call(this, '_setMoneyValue', value);},
        setPrecision: function (value) {_forwardToController.call(this, '_setPrecision', value);},
        setValidMoneyRegexp: function (value) {_forwardToController.call(this, '_setValidMoneyRegexp', value);},
        setFullMoneyRegexp: function (value) {_forwardToController.call(this, '_setFullMoneyRegexp', value);},
        setIntegerMoneyRegexp: function (value) {_forwardToController.call(this, '_setIntegerMoneyRegexp', value);},
        setDelimiterChar: function (value) {_forwardToController.call(this, '_setDelimiterChar', value);}
    });
    var _forwardToController = function (functionName, param1, param2, param3, param4)
    {
        return this.each(
            function ()
            {
                var controller = _getController(this);
                if (controller)
                {
                    controller[functionName](param1, param2, param3, param4);
                }
            }
        );
    };

    function MoneyField(elem)
    {
        this.elem = elem,
        this._MONEY_FORMAT_REGEXP = /(\d{1,3}(?=(\d{3})+(?:(\.|,)\d|\b)))/g,
        this._VALID_MONEY_REGEXP = /^-?\d*((\.|,)\d{0,2})?$/,
        this._FULL_MONEY_REGEXP = /^-?\d+(\.|,)\d{2}$/,
        this._INTEGER_MONEY_REGEXP = /^-?\d+$/,
        this._DELIMETER_REGEXP = /(\.|,)/,
        this._DELIMITER_CHAR = ' ';
        this.precision = 2;

        $.extend(
            MoneyField.prototype,
            {
                init: function ()
                {
                    var controller = this;
                    $(this.elem).keyup(function (e) {return controller.keyUpHandler(e)});
                    $(this.elem).keydown(function (e) {return controller.keyDownHandler(e)});
                },
                _setPrecision: function (precision)
                {
                    this.precision = precision;
                    this._VALID_MONEY_REGEXP = new RegExp("^-?\\d*((\\.|,)\\d{0," + precision + "})?$");
                    this._FULL_MONEY_REGEXP = new RegExp("^-?\\d+(\\.|,)\\d{" + precision + "}?$");
                },
                _setValidMoneyRegexp: function (regExp)
                {
                    this._VALID_MONEY_REGEXP = regExp;
                },
                _setFullMoneyRegexp: function (regExp)
                {
                    this._FULL_MONEY_REGEXP = regExp;
                },
                _setIntegerMoneyRegexp: function (regExp)
                {
                    this._INTEGER_MONEY_REGEXP = regExp;
                },
                _setDelimiterChar: function (delimiterChar)
                {
                    this._DELIMITER_CHAR = delimiterChar;
                },
                _setMoneyValue: function (value)
                {
                    //������ ������
                    var settingValue = typeof value == "number" ? value.toString() : value;
                    var withoutSpaceValue = getStringWithoutSpace(settingValue);
                    if (this._VALID_MONEY_REGEXP.test(withoutSpaceValue))
                        settingValue = withoutSpaceValue.replace(this._MONEY_FORMAT_REGEXP, "$1" + this._DELIMITER_CHAR);

                    $(this.elem).val(settingValue);
                },
                updateMoneyField: function (node)
                {
                    //���������� ����������������� ������� �����
                    var usePreventPosition = false;
                    var controller = this;
                    var getMoneyFormatString = function (sourceString)
                    {
                        //������� �� ��������
                        var isValidValue = function (val)
                        {
                            return controller._VALID_MONEY_REGEXP.test(val);
                        };
                        var withoutSpaceValue = getStringWithoutSpace(sourceString);

                        if (isValidValue(withoutSpaceValue))
                            return withoutSpaceValue.replace(controller._MONEY_FORMAT_REGEXP, "$1" + controller._DELIMITER_CHAR);
                        else
                        {
                            try
                            {
                                var currValue = parseFloat(withoutSpaceValue);
                                var value = parseFloat(Math.floor(currValue * Math.pow(10, controller.precision)) / Math.pow(10, controller.precision)).toFixed(controller.precision) + '';
                                if (isValidValue(value))
                                {
                                    return value.replace(controller._MONEY_FORMAT_REGEXP, "$1" + controller._DELIMITER_CHAR);
                                }
                            }
                            catch (e){}
                        }

                        usePreventPosition = true;
                        return node.preventValue;
                    };
                    //������� ���������� �������� � ������
                    var getSpaceCount = function (val)
                    {
                        return val.split(' ').length - 1;
                    };

                    var originalValue = node.value;
                    var withoutSpace = getStringWithoutSpace(originalValue);

                    //���� ������ ����������� �� ���������� �������� ��������
                    if (node.maxDigitCount != undefined)
                    {
                        //���� ������� ctrl+V � ����� ������� ����� ��������, �� �������� �� ������� ����� ��������
                        var pasteSymbolCount = Math.max(0, withoutSpace.length - node.maxDigitCount);
                        if (pasteSymbolCount != 0)
                            withoutSpace = withoutSpace.substr(0, node.maxDigitCount);
                    }
                    var formatedValue = getMoneyFormatString(withoutSpace);
                    var caretPosition = getCaretPosition(node)[0];
                    //� ������� ������� ���������� ���������� ����������� ��������
                    caretPosition += getSpaceCount(formatedValue.substr(0, caretPosition)) - getSpaceCount(originalValue.substr(0, caretPosition));

                    if (usePreventPosition)
                        caretPosition = node.preventPosition[0];
                    node.value = formatedValue;
                    setCaretPosition(node, caretPosition);
                },
                keyDownHandler: function (e)
                {
                    var node = getEventTarget(e);
                    var val = node.value;
                    var withoutSpace = getStringWithoutSpace(val);
                    if (this._VALID_MONEY_REGEXP.test(withoutSpace))
                    {
                        node.preventValue = val;
                        node.preventPosition = getCaretPosition(node);
                    }
                    if (e.keyCode == KEY_ENTER)
                    {
                        this.updateMoneyField(node);
                        return true;
                    }

                    var pos = node.preventPosition;
                    // ��������� ������� ����� �������� ��� �������� � ���� ����� �������� ��������� ������
                    if (e.keyCode == KEY_BACKSPACE && pos[0] - 1 > 0 && val.split('')[pos[0] - 1] == this._DELIMITER_CHAR)
                    {
                        setCaretPosition(node, pos[0] - 1);
                        return true;
                    }

                    // ��������� ������� ����� ������� ��� �������� � ���� ������� �� � ����� ������ � ����� �� ��������� ������
                    if (e.keyCode == KEY_DELETE && pos[0] == pos[1] && pos[0] < val.length && val.split('')[pos[0]] == this._DELIMITER_CHAR)
                    {
                        setCaretPosition(node, pos[0] + 1);
                        return true;
                    }

                    //��������� �������� ������ �� �������� �� ���������� � ��������� ������� � ctrl+X ���������
                    if (0 <= e.keyCode && e.keyCode < 45 ||
                            e.keyCode == KEY_BACKSPACE || e.keyCode == KEY_DELETE ||
                            e.keyCode >= 112 && e.keyCode <= 123 ||
                            e.ctrlKey && e.keyCode == 88)
                        return true;

                    var valueLength = withoutSpace.length;
                    var notFullField = node.maxDigitCount == undefined || valueLength < node.maxDigitCount;
                    //���� '-' �������� ������ � ������ ����
                    if (valueLength > 0 && e.keyCode == 109)
                        return false;

                    //������ ����� ��� ������� ������ ���� �� ��� � � ������������ � �������� ��������� ����(this.precision)
                    if (notFullField && this.precision != 0 && val.length - pos[1] <= this.precision && (e.keyCode == 110 || e.keyCode == 188 || e.keyCode == 190 || e.keyCode == 191))
                        return this._INTEGER_MONEY_REGEXP.test(withoutSpace);

                    if (!(e.keyCode >= 48 && e.keyCode <= 57 ||     //��������� ������� ������ �����
                            e.keyCode >= 96 && e.keyCode <= 105 ||   //��������� ������� ������ �����
                            e.ctrlKey && e.keyCode == 86 ||           //��������� ctrl+v
                            e.ctrlKey && e.keyCode == 45 ||           //��������� ctrl+insert
                            e.shiftKey && e.keyCode == 45 ||          //��������� shift+insert
                            e.ctrlKey && e.keyCode == 67 ||           //��������� ctrl+c
                            e.ctrlKey && e.keyCode == 65))            //��������� ctrl+a
                        return false;
                    if (notFullField && this._FULL_MONEY_REGEXP.test(withoutSpace))
                        return pos[0] <= val.search(this._DELIMETER_REGEXP) || pos[0] != pos [1];

                    return notFullField || pos[0] != pos [1] && !(e.ctrlKey && e.keyCode == 86);
                },
                keyUpHandler: function (e)
                {
                    // �� ��������� � ��������� �������, �� �������� �����
                    if (e.ctrlKey && e.keyCode != 86 && e.keyCode != 88 || e.keyCode != KEY_BACKSPACE && 0 < e.keyCode && e.keyCode < 45 && e.keyCode != 32)
                        return true;

                    this.updateMoneyField(getEventTarget(e));
                    return true;
                }
            }
        )
    }

    function _getController(elem)
    {
        if (elem._moneyFieldId) return $.event._moneyFieldCache[elem._moneyFieldId];
        return false;
    };
})(jQuery);

$(document).ready(function ()
{
    initMoneyFields();
});
