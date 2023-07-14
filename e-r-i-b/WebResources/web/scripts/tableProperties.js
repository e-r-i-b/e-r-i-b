var tablePropertiesFactory =
{
    helpers: {},

    /**
     * ����������� �������
     * @param helper ������
     */
    registerHelper: function(helper)
    {
        this.helpers[helper.propertyId] = helper;
        helper.init();
    },

    /**
     * ���������� ������ �� �����
     * @param propertyId ���� (id ���������)
     */
    getHelper: function(propertyId)
    {
        return this.helpers[propertyId];
    },

    /**
     * ���������� ��� ������������ onblur �� �������� �����
     * @param propertyId - id ���������
     * @param rowNum - ����� ������
     */
    fieldOnBlur: function(propertyId, rowNum)
    {
        setTimeout(function(){
            var helper = tablePropertiesFactory.getHelper(propertyId);
            if (document.activeElement == undefined || document.activeElement.name == undefined || document.activeElement.name == '')
            {
                if (helper._editRow != -1 && helper.checkRow(rowNum) && helper.checkDouble(rowNum))
                {
                    helper.viewRow(rowNum);

                    //��������� addButton
                    if (document.activeElement != undefined &&
                        document.activeElement.id == propertyId + '_AddButton')
                        helper.createRow();
                }
                return;
            }

            var activeElementName = document.activeElement.name;
            for (var fieldKey in helper.fieldsDescriptions)
            {
                if (('field(' + fieldKey + rowNum + ')') == activeElementName)
                    if (helper.fieldsDescriptions[fieldKey].type != 'identifier')
                        return;
            }

            if (helper.checkRow(rowNum) &&  helper.checkDouble(rowNum))
            {
                helper.viewRow(rowNum);
            }
        }, 1);
    },

    /**
     * ��������� ��������� ������ ���������� ��������� �������� �� ������� ��������
     * @param state - 'disable' (������ �� �������), 'enable' (������ �������)
     * @param missHelper - id ���������, ��� ������� ��������� ������ �� ����������
     */
    changeTablePropertiesState: function(state, missHelper)
    {
        for (var key in this.helpers)
        {
            if (missHelper != undefined && key == missHelper)
                continue;

            var helper = this.getHelper(key);
            if (state == 'disable')
                helper.changeButtonStates(false, false, false);
            else if (state == 'enable')
                helper.changeButtonStates(true, true, true);
        }
    }
};

var replication = location.href.indexOf("replication=true") != -1;

/**
 * ������ ��� �������� ��������� ��������
 * @param propertyId - ������������� ���������
 * @param fieldsDescriptions - �������� ����� ��������: ���������� ���� <�������� ����, �������� ������� ����
 *      ������ fieldsDescriptionsLoanReceptionTimeProperty  �  payment-restrictions.jsp
 * @param initValues - ��������� ���������
 * @param imagesPath - ���� � �������
 * @param uniqFields - ������ �����, ������� ������ ���� ���������
 * @param viewMode - ����� ���������
 * @param needShortTimeTemplate - ������������� ���������� ����� ��� ����� ����� �������
 */
function TablePropertiesHelper(propertyId, fieldsDescriptions, initValues, imagesPath, uniqFields, viewMode, needShortTimeTemplate)
{
    this.propertyId = propertyId;
    this.fieldsDescriptions = fieldsDescriptions;
    this.initValues = initValues;
    this.viewMode = viewMode != undefined && viewMode != null ? viewMode : false;
    this.needShortTimeTemplate = needShortTimeTemplate != undefined ? needShortTimeTemplate : false;
    this.imagesPath = imagesPath;
    this.uniqFields = uniqFields;

    this.checkboxId = this.propertyId + '_Ids'; //�������� ���� checkbox
    this.rowIdPrefix = this.propertyId + '_Tr'; //������� �������������� ������ ���������
    this.errorRowImg = this.propertyId + '_ErrorRowImg'; //������� ������ ������
    this.checkRowImg = this.propertyId + '_CheckRowImg'; //������� ������ ���������
    this.tableId = this.propertyId + '_Table'; //������������� �������
    this._editRow = -1; //������ ������������� ������

    this._calcTdCount = function()
    {
        var count = 0;
        for (var fieldName in this.fieldsDescriptions)
        {
            var field = this.fieldsDescriptions[fieldName];
            if (field.td > count)
                count = field.td;
        }

        return count;
    };

    //���������� �������� � �������
    this.tdCount = this._calcTdCount();

    this._calcIdentifierFieldKey = function()
    {
        for (var fieldKey in this.fieldsDescriptions)
        {
            if (this.fieldsDescriptions[fieldKey].type == 'identifier')
                return fieldKey;
        }
        return null;
    };

    //�������� ���� �������������� ������
    this._identifierFieldKey = this._calcIdentifierFieldKey();

    this._calcRowIndex = function()
    {
        var result = 0;
        if (this.initValues != undefined && this.initValues.length > 0)
        {
            for ( var i=0; i<this.initValues.length; i++ )
            {
                var values = this.initValues[i];
                if (result < values[this._identifierFieldKey])
                    result = parseInt(values[this._identifierFieldKey]);
            }
        }
        return result;
    };

    //������� ������ ������
    this._rowIndex = this._calcRowIndex();

    //������������� ������
    this.init = function()
    {
        if (this.initValues != undefined && this.initValues.length > 0)
        {
            for ( var i=0; i<this.initValues.length; i++ )
            {
                this.createRow(this.initValues[i]);
            }
            if (this.viewMode)
                this.changeButtonStates(false, false, false);
        }
        else
            this.changeButtonStates(this.viewMode ? false : true, false, false);
    };

    //��������� ��������� ������
    this.changeButtonStates = function(addButtonEnabled, editButtonEnabled, removeButtonEnabled)
    {
        if (addButtonEnabled != null)
        {
            if (addButtonEnabled)
                $("#"+this.propertyId+"_AddButton").removeAttr('disabled');
            else
                $("#"+this.propertyId+"_AddButton").attr('disabled', 'disabled');
        }

        if (editButtonEnabled != null)
        {
            if (editButtonEnabled)
                $("#"+this.propertyId+"_EditButton").removeAttr('disabled');
            else
                $("#"+this.propertyId+"_EditButton").attr('disabled', 'disabled');
        }

        if (removeButtonEnabled != null)
        {
            if (removeButtonEnabled)
                $("#"+this.propertyId+"_RemoveButton").removeAttr('disabled');
            else
                $("#"+this.propertyId+"_RemoveButton").attr('disabled', 'disabled');
        }
    };

    /**
     * ������� ������ � ��������� �� � �������
     * @param values - �������� ����� (���������� ��� ������������� �����)
     */
    this.createRow = function(values)
    {
        var isNew = values == undefined;

        if (this._editRow != -1 && isNew)
            return;

        if (isNew) //��������� �������������� ��������� ��������� �������� �� ��������
            tablePropertiesFactory.changeTablePropertiesState('disable', this.propertyId);

        var currentId = null;
        if (isNew)
        {
            this._rowIndex += 1;
            currentId = this._rowIndex;
        }
        else
        {
            currentId = values[this._identifierFieldKey];
            if (currentId == null)
                currentId = 0;
        }

        if (isNew)
            this._setEditRow(currentId);

        var newRow = '';
        for (var i=1; i<=this.tdCount; i++)
        {
            newRow += '<td' + (i==1 ? ' align="center"' : '') + '>';
            if (i == this.tdCount)
            {
                newRow += '<img id="' + this.errorRowImg + currentId +
                          '" src="' + this.imagesPath + '/tabPropertyError.gif" style="float:right; display:none;"/>';
                newRow += '<img id="' + this.checkRowImg + currentId +
                          '" src="' + this.imagesPath + '/tabPropertyCheck.gif" style="float:right; display:none;"' +
                          '/>';
            }
            for (var fieldKey in this.fieldsDescriptions)
            {
                var field = this.fieldsDescriptions[fieldKey];

                if (field.td == i)
                {
                    if (field.before_text != undefined)
                        newRow += ' <span>' + field.before_text + ' </span>';

                    var fieldValue = !isNew ? values[fieldKey] : '';
                    var fieldName = fieldKey + currentId;

                    var elementStyle;
                    var spanStyle;
                    if (!isNew)
                    {
                        elementStyle = 'display:none;';
                        spanStyle = '';
                    }
                    else
                    {
                        spanStyle = 'display:none;';
                        elementStyle = '';
                    }

                    var styleClass = field.style_class != undefined ? ' class="' + field.style_class + '"' : '';
                    if (field.style != undefined)
                        elementStyle += field.style;

                    if (field.type == 'identifier')
                    {
                        if (fieldValue == undefined || fieldValue == '')
                            fieldValue = currentId;

                        newRow += '<input type="checkbox" name="' + this.checkboxId + '" value="' + currentId + (replication ? '" disabled="disabled' : '') + '"/>';
                        newRow += '<input type="hidden" name="field(' + fieldName + ')" ' +
                                  'value="' + fieldValue + '"' + '/>';
                    }
                    else if (field.type == 'input')
                    {
                        newRow += '<input type="text" name="field(' + fieldName + ')" value="' + fieldValue + '"' +
                                  styleClass + ' style="' + elementStyle + '"' +
                                  ' onchange="$(\'#' + fieldName + '_Visible\').text(this.value);"' +
                                  ' onblur="tablePropertiesFactory.fieldOnBlur(\'' + this.propertyId + '\', ' + currentId + ');"' + ' />';

                        newRow += '<span id="' + fieldName + '_Visible" style="' + spanStyle + '">' + fieldValue + '</span>';
                    }
                    else if (field.type == 'select')
                    {
                        newRow += '<select id="' + fieldName + '" name="field(' + fieldName + ')" value="' + fieldValue + '"' +
                                  styleClass + ' style="' + elementStyle + '"' +
                                  ' onchange="$(\'#' + fieldName + '_Visible\').text($(\'#' + fieldName + ' option:selected\').text());"' +
                                  ' onblur="tablePropertiesFactory.fieldOnBlur(\'' + this.propertyId + '\', ' + currentId + ');"' + ' >';
                        for (var key in field.options)
                        {
                            newRow += '<option value="' + key + '"';
                            if (!isNew && fieldValue == key)
                                newRow += ' selected="true"';
                            newRow += '>';
                            newRow += field.options[key];
                            newRow += '</option>';
                        }
                        newRow += '</select>';

                        newRow += '<span id="' + fieldName + '_Visible" style="' + spanStyle + '">';
                        if (!isNew)
                            newRow += field.options[fieldValue];
                        else
                        {
                            for (var key in field.options)
                            {
                                newRow += field.options[key];
                                break;
                            }
                        }
                        newRow += '</span>';
                    }
                }
            }
            newRow += '</td>';
        }

        var el = document.createElement("tr");
        el.id = this.rowIdPrefix + currentId;
        $(el).html(newRow);
        var firstRow = $("#" + this.tableId).find("tr.tblInfHeader");
        $(el).insertAfter(firstRow);

        if (this.needShortTimeTemplate)
            $('.short-time-template').createMask(SHORT_TIME_TEMPLATE);

        $("#" + this.propertyId + "_Div").show();

        if (isNew)
        {
            this.highlightRow(currentId, 'edit');
            this._firstInputFieldFocused(currentId);
            this.changeButtonStates(null, false, true);
        }

        if (!isNew && errorDictionary != undefined)
        {
            for (var fieldKey in this.fieldsDescriptions)
            {
                if (errorDictionary[fieldKey + currentId] != undefined)
                {
                    this.editRow(currentId, 'error');
                    var errorField = this._getField(fieldKey, currentId);
                    $(errorField).focus();
                    $(errorField).css('color', 'red');
                }
            }
        }
    };

    /**
     * ��������� ������.
     * @param rowNum - ������ ������
     * @param type - ��� ��������� = error, edit, none
     */
    this.highlightRow = function(rowNum, type)
    {
        var errorRowImg = this.errorRowImg + rowNum;
        var checkRowImg = this.checkRowImg + rowNum;

        var color;
        if (type == 'error')
        {
            color = '#f7bcaa';
            $('#' + errorRowImg).show();
            $('#' + checkRowImg).hide();
        }
        else if (type == 'edit')
        {
            color = '#fcf8cd';
            $('#' + errorRowImg).hide();
            $('#' + checkRowImg).show();
        }
        else
        {
            color = 'none';
            $('#' + errorRowImg).hide();
            $('#' + checkRowImg).hide();
        }

        $('#' + this.rowIdPrefix + rowNum).css('background', color);
    };

    /**
     * js-��������� ������
     * @param rowNum - ������ ������
     */
    this.checkRow = function(rowNum)
    {
        for (var fieldKey in this.fieldsDescriptions)
        {
            var fieldDescription = this.fieldsDescriptions[fieldKey];
            if (fieldDescription.validators != undefined)
            {
                for (var i=0; i<fieldDescription.validators.length; i++)
                {
                    var validator = fieldDescription.validators[i];
                    var field = this._getField(fieldKey, rowNum);
                    if (!validator.template.validate($(field).val()))
                    {
                        $(field).focus();
                        $(field).css('color', 'red');
                        this.highlightRow(rowNum, 'error');

                        $('#' + this.propertyId + '_ErrorsContainer').show();
                        $('#' + this.propertyId + '_Errors').text(validator.message);

                        return false;
                    }
                    $(field).css('color', '');
                }
            }
        }

        for (var fieldKey in this.fieldsDescriptions)
        {
            $(this._getField(fieldKey, rowNum)).css('color', '');
        }

        this._hideErrorContainer();

        this.changeButtonStates(true, true, true);
        return true;
    };

    this.checkDouble = function(rowNum)
    {
       if (this.uniqFields != undefined)
       {
            var currValue = "";
            for (var k = 0; k < this.uniqFields.length; k++)
            {
                currValue = currValue + ";" +this._getField(this.uniqFields[k], rowNum).val();
            }
           var field = this._getField(this.uniqFields[0], rowNum);
            if (currValue != "")
            {
                for (var j=0; j < this.initValues.length; j++)
                {
                    var values = this.initValues[j];
                    var identifierFieldKey = values[this._identifierFieldKey];
                    var value = "";
                    for (var i = 0; i < this.uniqFields.length; i++)
                    {
                        value = value + ";" +this._getField(this.uniqFields[i], identifierFieldKey).val();
                    }

                    if ((identifierFieldKey != rowNum) && (currValue == value))
                    {
                        $(field).focus();
                        $(field).css('color', 'red');
                        this.highlightRow(rowNum, 'error');

                        $('#' + this.propertyId + '_ErrorsContainer').show();
                        $('#' + this.propertyId + '_Errors').text("������ ������ ��� ��������� � ������.");
                        return false;
                    }
                }
            }

            this._hideErrorContainer();

            this.changeButtonStates(true, true, true);
            return true;
       }
    };

    //������� ���������� ������
    this._hideErrorContainer = function()
    {
        $('#' + this.propertyId + '_ErrorsContainer').hide();
        $('#' + this.propertyId + '_Errors').text('');
    };

    /**
     * ������������ ������ � ����� ���������
     * @param rowNum - ������ ������
     */
    this.viewRow = function(rowNum)
    {
        this._setEditRow(-1);
        for (var fieldKey in this.fieldsDescriptions)
        {
            this._getField(fieldKey, rowNum).hide();
            $("#" + fieldKey + rowNum + "_Visible").show();
        }
        this.highlightRow(rowNum, 'none');
        tablePropertiesFactory.changeTablePropertiesState('enable');
    };

    /**
     * ����������� ������� ������������� ������
     * @param rowNum - ������ ������
     */
    this._setEditRow = function(rowNum)
    {
        this._editRow = rowNum;
    };

    /**
     * ������������ ������ � ����� �������������� � ���������� ���������
     * @param rowNum - ������ ������
     * @param highlightType - ��� ���������
     */
    this.editRow = function(rowNum, highlightType)
    {
        if (rowNum == undefined)
        {
            if(!checkOneSelection(this.checkboxId, '������� ������ ���� ������.'))
                return;
            rowNum = getRadioValue(document.getElementsByName(this.checkboxId));
        }

        this._setEditRow(rowNum);
        tablePropertiesFactory.changeTablePropertiesState('disable', this.propertyId);

        for (var fieldKey in this.fieldsDescriptions)
        {
            $("#" + fieldKey + rowNum + "_Visible").hide();
            this._getField(fieldKey, rowNum).show();
        }
        this.highlightRow(rowNum, highlightType != undefined ? highlightType : 'edit');
        this.changeButtonStates(true, false, true);
        this._firstInputFieldFocused(rowNum);
    };

    /**
     * ��������� ������ � ������ ���� ����� � ������
     * @param rowNum - ������ ������
     */
    this._firstInputFieldFocused = function(rowNum)
    {
        for (var fieldKey in this.fieldsDescriptions)
        {
            if (this.fieldsDescriptions[fieldKey].type == 'input')
            {
                this._getField(fieldKey, rowNum).focus();
                return;
            }
        }
    };

    //��� �������� ������������� ������ ���������� ���������� _editRow = -1
    // � ������������ ������ ������ ��������� ��������
    this._clearEditRow = function()
    {
        var elements = document.getElementsByName(this.checkboxId);
        for (var i = 0; i < elements.length; i++)
        {
            if (elements[i].checked && elements[i].value == this._editRow)
            {
                this._editRow = -1;
                tablePropertiesFactory.changeTablePropertiesState('enable');
                return;
            }
        }
    };

    //�������� �����, � ������������� ���������
    this.remove = function()
    {
        checkIfOneItem(this.checkboxId);
        if(!checkSelection(this.checkboxId,'�������� ������ ��� ��������.'))
            return;

        if (this._editRow != -1)
        {
            //��� �������� ������������� ������ ���������� ���������� _editRow = -1
            this._clearEditRow();
        }

        $('input[name=' + this.checkboxId + ']:checked').each(function(){
            $(this).parent().parent().remove();
        });
        if ($('input[name=' + this.checkboxId + ']').length == 0)
        {
            $("#" + this.propertyId + "_Div").hide();
        }
        if (document.getElementsByName(this.checkboxId).length == 0)
        {
            this.changeButtonStates(null, false, false);
            $("#" + this.tableId).find('input:checkbox:first').attr('checked', '');
        }
        else if (this._editRow == -1)
            this.changeButtonStates(true, true, true);

        this._hideErrorContainer();
    };

    /**
     * ���������� ������� ���� �����
     * @param fieldKey - ���� ����
     * @param rowNum - ������ ������
     */
    this._getField = function(fieldKey, rowNum)
    {
        return $(this.fieldsDescriptions[fieldKey].type + "[name=field(" + fieldKey + rowNum + ")]");
    };
};