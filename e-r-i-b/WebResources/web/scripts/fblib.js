/*
*  �������, �� ���� ������� �������� ���������� "������ � ������� ��� �������� ����������"
*/

/**
 * ������, �� ������ �������� �������� ��������� "������ ������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param addBtnText - ����� ��� ������, ����������� ����� ����
 * @param fieldPrefix - ������� ��� �������� ����� �����
 * @param addBtnWidth - ������ ������ ��� ���������� �����
 * @param btnIsBelow - true, ���� ������ ���������� ������ ����� ������ ���������� �����
 */
function createComponentList(maxCount, info, addBtnText, fieldPrefix, btnTextValue, addBtnWidth, btnIsBelow, infoMessage)
{
    var infoComponentList =
    {
        btnTextValue : btnTextValue,
        fieldPrefix : fieldPrefix,
        addBtnText : addBtnText,
        // ������ � ���������� �����
        fieldNames : [],
        // ������������ ���������� ������ "�����"
        maxBlockCount : maxCount,
        // ������ ��� ���������� ����� ������
        addNewChildButton: null,
        // ���������� "�������"
        componentList: [],
        info : info,

        init : function()
        {
            this.parentDiv = this.getComponentListParentNode();

            var addButton = createComponent(this);
            var titleRow = addButton.createEmptyRow(btnTextValue, false, true);

            var html = "<div class='loan-claim-button buttWhite'>" +
                       "    <div class='buttonSelect'>" +
                       "        <div class='buttonSelectLeft'></div>" +
                       "        <div class='buttonSelectCenter'>" + addBtnText + "</div>" +
                       "        <div class='buttonSelectRight'></div>" +
                       "        <div class='clear'></div>" +
                       "    </div>" +
                       "</div>";
            var compList = this;
            var parent = $(titleRow).find('.paymentInputDiv');
            var addBtn =
                    $(html).appendTo(parent)
                    .addClass("buttWhite")
                    .attr("value", this.addBtnText)
                    .attr("name", "addNewBlockBtn")
                    .click(function() {
                        compList.addNewChild("");
                    });
            addBtn.width(addBtn.find(".buttonSelectLeft").width() + addBtn.find(".buttonSelectCenter").width() + addBtn.find(".buttonSelectRight").width() + 5); //"+5" ��������� ��� ��9.
            addBtn = addBtn.get(0);
            if (addBtnWidth != null)
                addBtn.style.width = addBtnWidth+"px";

            if (btnIsBelow)
            {
                var btnDiv = document.createElement('div');
                btnDiv.style.textAlign = "center";
                $(btnDiv).addClass(fieldPrefix + "button-wrapper");
                $(this.parentDiv).after(btnDiv);
            }
            $(titleRow).find('.paymentInputDiv').addClass(fieldPrefix + "button-parent");
            $(titleRow).find('.paymentInputDiv').append(addBtn);
            this.addNewChildButton = addBtn;

            if (infoMessage!=null)
            {
                var messasgeArea = "<div id='formMessages'>"+
                                   "    <div class='workspace-box roundBorder infMesOrange'>"+
                                   "        <div class='infoMessage'>"+
                                   "            <div class='messageContainer'>"+
                                   "                <div class='itemDiv'>"+infoMessage+"</div>"+
                                   "            </div>"+
                                   "            <div class='clear'/>"+
                                   "        </div>"+
                                   "    </div>"+
                                   "</div>";
                $(messasgeArea).appendTo(this.parentDiv);
            }
            this.initSavedChildren();
        },

        getComponentListParentNode : function()
        {
            // ���������� ������������ ������� ��� ������ ������
            // ���� ����� ����� ������������� � ���������� ����������
        },

        // ������� ���������� ��� �����, � ������� ��� ���� ����������
        initSavedChildren : function()
        {
            if (this.info == null || this.info == undefined)
                return;
            for (var i = 0; i < this.info.length; i++)
            {
                this.addNewChild(this.info[i]);
            }
        },

        addNewChild : function(info)
        {
            var newInfoComponent = this.getTypifiedComponent(info);
            var n = this.componentList.length;

            this.componentList[n] = newInfoComponent;
            if (this.componentList.length >= this.maxBlockCount)
                $(this.addNewChildButton).unbind('click');

            // ������������������� ����������� ���� ��� ����� ����
            // ������ ���� ���������������. payInput ���������� ��� �������� ���������(��. PaymentsFormHelp.js)
            payInput.setEvents(payInput);

            var buttonWrapper = $("." + fieldPrefix + "button-wrapper");
            if (buttonWrapper.find(this.addNewChildButton))
                buttonWrapper.append(this.addNewChildButton);
        },

        getTypifiedComponent : function(info)
        {
            // ����� ���������� �������������� ���������.
            // ���� ����� ����� ������������� � ���������� ����������.
        },

        // ������� ���� � ������������ � ���������� �� �����. ��� ������ �������������� ��������� ����� [�������]_[�����]_[��� ����]. �������� relative_1_surname
        updateCompletedFields : function()
        {
            for (var i = 1; i <= this.componentList.length; i++)
            {
                var childComponentDiv = this.componentList[i-1].area;

                for (var j = 1; j <= this.fieldNames.length; j++)
                {
                    var fieldName = this.fieldNames[j-1];
                    var inputField = $(childComponentDiv).find("[name$='_"+ fieldName +"']");
                    // � �������� ������ ����� ����� ���� ��������� ������� � ���������� ������. ��������, ������ �������������
                    for (var k = 0; k < inputField.length; k++)
                    {
                        inputField[k].name = this.fieldPrefix + i + "_" + fieldName;

                        var className = inputField[k].className;
                        if (className == 'moneyField')
                        {
                            inputField[k].value = inputField[k].value.replace(/\s/g, '');
                        }
                    }
                }
            }
        },

        // �������� ����-��������
        createMockFields : function()
        {
            for (var i = this.maxBlockCount; i > this.componentList.length; i--)
            {
                for (var j = 1; j <= this.fieldNames.length; j++)
                {
                    var fieldName = this.fieldNames[j-1];

                    var mockField = document.createElement('input');
                    mockField.type = "hidden";
                    mockField.name = this.fieldPrefix + i + "_" + fieldName;
                    this.parentDiv.appendChild(mockField);
                }
            }
        },
        // ����� ��������� ������� ���� � ������������ � ���������� �� ����� � ������� ����-��������, ����� ������� ������ ������ �� ���������������� �����
        updateInputInfo : function()
        {
            this.updateCompletedFields();
            this.createMockFields();
        },


        // ������� ����. ������ ��� � ������� ������ �� ������� "�����"
        removeChild : function(child)
        {
            for (var i = 0; i < this.componentList.length; i++)
            {
                if (this.componentList[i] == child)
                {
                    var nodeToRemove = this.componentList[i].area;
                    $(nodeToRemove).remove();

                    this.componentList.splice(i, 1);
                    if (this.componentList.length < this.maxBlockCount)
                    {
                        var blockList = this;
                        $(this.addNewChildButton).unbind('click');
                        $(this.addNewChildButton).click(function(){
                            blockList.addNewChild("");
                        });
                    }
                }
            }
        }

    };
    return infoComponentList;
}

/**
 * ������, �� ������ �������� �������� ��������� "����"
 * @param parentComponent - ���������-������ ������
 */
function createComponent(parentComponent)
{
    var infoComponent =
    {
        parent : parentComponent,
        area : null,

        // ������� ���������
        init : function()
        {
            // ���������� div
            var div = document.createElement('div');
            this.area = div;
            this.parent.parentDiv.appendChild(div);
        },

        /**
         * ������� ��������� �����
         * @param title - ����� ���������
         */
        createTitleRow : function(title)
        {
            var titleRow = this.createEmptyRow(null, false, true);

            this.addNewField(titleRow, null, title, "title");
        },
        /**
         * ������� ������ �����
         * @param label
         * @param required
         */
        createRow : function(label, required)
        {
            return this.createEmptyRow(label, required, false);
        },

        /**
         * ������� ������
         * @param label - ����� ��� ���� �����
         * @param required - ����������� �� ���� ��� ����������
         * @param isTitle - �������� �� ������ ����������
         */
        createEmptyRow : function(label, required, isTitle)
        {
            var rowDiv = document.createElement('div');
            if (isTitle != true)
                rowDiv.className = "form-row-addition";

            rowDiv.appendChild(this.createRowLabel(label, required));
            rowDiv.appendChild(this.createEmptyRowValue());

            var clearDiv = document.createElement('div');
            clearDiv.className = "clear";
            rowDiv.appendChild(clearDiv);

            this.area.appendChild(rowDiv);

            return rowDiv;
        },

        createEmptyRowValue : function()
        {
            var node = document.createElement('div');
            node.className = "paymentValue";

            var inputDivNode = document.createElement('div');
            inputDivNode.className = "paymentInputDiv";
            inputDivNode.name = "paymentInputDiv";

            var descriptionDivNode = document.createElement('div');
            descriptionDivNode.className = "description";
            descriptionDivNode.style.display = "none";

            var errorDivNode = document.createElement('div');
            errorDivNode.className = "errorDiv";
            errorDivNode.display = "none";

            node.appendChild(inputDivNode);
            node.appendChild(descriptionDivNode);
            node.appendChild(errorDivNode);

            return node;
        },

        /**
         * ������� ���� �����
         * @param placeholder - ������, � ������� ����� ����������� ����
         * @param name - �������� ����
         * @param value - ��������
         * @param type - ��� ����
         * @param params - ���������, ���� �����. ��� ������������� � ��������� (���� radio, check) ��� �������� ����� ����� � �������
         * @return �������� ���� ����� � ���� jquery-�������
         */
        addNewField : function(placeholder, name, value, type, params)
        {
            this.updateFieldNames(name);
            var fieldName = this.parent.fieldPrefix + name;

            var html = this.createInputField(fieldName, value, type, params);
            var inputNode = $(placeholder).find('.paymentInputDiv');
            inputNode.append(html);
            if (type == "title")
                this.initRemoveBtn(inputNode);
            if (type == "date")
                this.initDatePicker(inputNode);
            return inputNode.find(":last-child");
        },

        initDatePicker : function(inputNode)
        {
            var imageCalendar;
            if (document.webRoot == '/PhizIC')
                imageCalendar = globalUrl + '/commonSkin/images/datePickerCalendar.gif';
            else
                imageCalendar = skinUrl + '/images/calendar.gif';

            if (($(inputNode).find('.dot-date-pick').length > 0) && $(inputNode).find('.dot-date-pick').datePicker)
            {
                var dP = $(inputNode).find('.dot-date-pick').datePicker({displayClose: true, chooseImg: imageCalendar, dateFormat: 'dd.mm.yyyy'});
                dP.dpApplyMask();
            }
        },

        /**
         * �������� �������� � ������
         * @param placeholder
         * @param text
         */
        addRowDescription : function(placeholder, text)
        {
            var inputNode = $(placeholder).find('.description');
            inputNode.append(text);
        },

        /**
         * �������� ����������� ������. �� ������ ���������� ����� ����� � ����� ������
         * @param placeholder
         */
        addSpace : function(placeholder)
        {
            var inputNode = $(placeholder).find('.paymentInputDiv');
            inputNode.append('<span>&nbsp;</span>');
        },

        // ����������� ���������� ������ (��� ������ ���� ������ �� ��������)
        initPreparedSelectField : function(placeholder, name, value, param)
        {
            this.updateFieldNames(name);
            var fieldName = this.parent.fieldPrefix + name;

            var newElem = $('span#'+param+'> select').clone();
            newElem[0].id = fieldName;
            newElem[0].name = fieldName;

            $(placeholder).find('.paymentInputDiv').append(newElem);

            if (newElem[0].inited)
            {
                newElem[0].inited = false;
            }

            selectCore.init(newElem.parentNode);

            newElem.find('[value="'+value+'"]').attr("selected","selected");
            return newElem[0];
        },

        // ��������� ������ � ���������� ����� �����
        updateFieldNames : function(name)
        {
            if (name == null || name.length == 0)
                return;
            if (name.length > 0 && $.inArray(name, this.parent.fieldNames) == -1)
                this.parent.fieldNames[this.parent.fieldNames.length] = name;
        },

        // ������������ ����� ��� ���� �����
        createRowLabel : function(label, required)
        {
            var node = document.createElement('div');
            node.className = "paymentLabel";

            if (label == null || label.length == 0)
                return node;

            var htmlCode =  '<span class="paymentTextLabel">'+ label +':</span>';
            if (required)
                htmlCode = htmlCode + '<span name="asterisk_" class="asterisk" id="asterisk_">*</span>';

            node.innerHTML = htmlCode;
            return node;
        },

        createTextLabel : function(row, textLabel)
        {
            var label = document.createElement('span');
            label.innerHTML = textLabel;

            var inputNode = $(row).find('.paymentInputDiv');
            inputNode[0].appendChild(label);
        },

        // ������� ���� �����
        createInputField : function(name, val, type, params)
        {
            var value = val == undefined ? '' : val;
            var namePrefix = this.parent.componentList.length + 1;

            if (type == "text")
                return this.createTextField(name, value, params);
            else if (type == "hidden")
                return '<input size="10" type="hidden" value="' + value + '" name="' + name + '">';
            else if (type == "title")
                return '<span class="blockTitle">'+ value +'</span>';
            else if (type == "date")
                return '<input size="10" class="dot-date-pick" value="' + value + '" name="' + name + '">';
            else if (type == "radio")
            {
                var str = '';
                for (var i = 1; i <= params.length; i++)
                {
                    var check = value == i ? 'checked' : '';
                    str = str + '<div class="textTick"><input class="float" value="' + i + '" name="' + namePrefix + '_' + name + '" type="radio" ' + check + '/>' + '<label class="float">' + params[i-1] + '</label></div>';
                }
                return str;
            }
            else if (type == "check")
            {
                var check = (value == "on" || value == "true")  ? 'checked' : '';
                return '<div class="textTick"><input class="float" name="' + namePrefix + '_' + name + '" type="checkbox" ' + check + '/>' + '<label class="float">' + params[0]+ '</label></div>';
            }

            return undefined;
        },

        createTextField : function(name, value, params)
        {
            var defaultParams = {maxLength: 100, size : 50, className : ""};
            if (params == undefined)
                params = defaultParams;
            else
            {
                for (var param in defaultParams) {
                    if (params[param] == undefined)
                        params[param] = defaultParams[param];
                }
            }

            return '<input size="' + params.size + '" ' +
                    'maxlength="' + params.maxLength + '" ' +
                    'value="' + value + '" ' +
                    'class="' + params.className + '" ' +
                    'name="' + name + '" type="text">';
        },

        // ������� ������ ��� �������� �������� ����� (������������)
        initRemoveBtn : function(addTo)
        {
            var delComponent = this;

            var self = this;
            var newElem = $('div#templateRemoveBtn > div').clone();
            newElem.click(function() {
                delComponent.parent.removeChild(delComponent);
                if (self.parent.componentList.length == 0)
                    $("." + self.parent.fieldPrefix + "button-parent").append(self.parent.addNewChildButton);
            });
            newElem.click(reserveRemoveBtnFunction);
            newElem.appendTo(addTo);
            $('.blueLink').hover(function(){
                $(this).addClass('linkHoverDotted');
            }, function() {
                $(this).removeClass('linkHoverDotted');
            });
        }
    };

    infoComponent.init();
    return infoComponent;
}

function reserveRemoveBtnFunction(item)
{
}
