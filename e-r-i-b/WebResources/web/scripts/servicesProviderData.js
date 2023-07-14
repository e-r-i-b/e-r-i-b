function ServicesPaymentForm(isUseProfileDocuments, socialNetUserId)
{
    var t = this;
    t.providers = [];
    t.fromResources = [];
    t.currentProvider = null;
    t.socialNetUserId = socialNetUserId;
    t.isUseProfileDocuments = isUseProfileDocuments;
    t.otherUserSocialAccount = null;
}

ServicesPaymentForm.prototype = {
    addProvider : function(provider)
    {
        var t = this;
        provider.parent = t;
        t.providers.push(provider);
    },

    addFromResource : function(resource)
    {
        var t = this;
        t.fromResources.push(resource);
    },

    getProviderById : function(id)
    {
        var t = this;
        if (id == -1)
            return t.providers[0];
        for(var i = 0; i < t.providers.length; i++)
        {
            if(t.providers[i].id == id)
                return t.providers[i];
        }
        return null;
    },

    updateAccessProviderRegion : function(map)
    {
        var t = this;
        for(var i = 0; i < t.providers.length; i++)
            t.providers[i].accessCurrentRegion = map[provider.id];

        t.addMessageRegionProvider(ensureElement("recipientId").value);
    },

    addMessageRegionProvider : function(provId)
    {
        var t = this;
        var message = "ѕолучатель зарегистрирован в другом регионе оплаты.";
        if(!t.getProviderById(provId).accessCurrentRegion)
        {
            addMessage(message);
        }
        else
        {
            removeMessage(message);
        }
    },

    getAccounts : function()
    {
        var t = this;
        var res = [];
        for(var i = 0; i < t.fromResources.length; i++)
        {
            if(t.fromResources[i].type == 'account')
                res.push(t.fromResources[i]);
        }
        return res;
    },

    getAddAccountsInfo: function()
    {
        var countAccounts = 0;
        var countNotNullAcc = 0;
        var indexNotNullAcc;

        var t = this;
        for(var i = 0; i < t.fromResources.length; i++)
        {
            var resource = t.fromResources[i];
            if(resource.type != 'account')
                continue;

            countAccounts++;
            if(resource.rest != 0)
            {
                countNotNullAcc++;
                indexNotNullAcc = resource.code;
            }
        }

        return {countAccounts: countAccounts, countNotNullAcc: countNotNullAcc, indexNotNullAcc: indexNotNullAcc};
    },

    getCards : function()
    {
        var t = this;
        var res = [];
        for(var i = 0; i < t.fromResources.length; i++)
        {
            if(t.fromResources[i].type == 'card')
                res.push(t.fromResources[i]);
        }
        return res;
    },

    getAddCardsInfo: function()
    {
        var countCards = 0;
        var countNotNullCards = 0;
        var indexNotNullCards;

        var t = this;
        for(var i = 0; i < t.fromResources.length; i++)
        {
            var resource = t.fromResources[i];
            if(resource.type != 'card')
                continue;

            countCards++;
            if(resource.rest != 0)
            {
                countNotNullCards++;
                indexNotNullCards = resource.code;
            }
        }

        return {countCards: countCards, countNotNullCards: countNotNullCards, indexNotNullCards: indexNotNullCards};
    },

    updateFromResource : function()
    {
        var t = this;
        var accType = t.currentProvider.accountType;
        var fromResource = ensureElement("fromResource");
        //«апомним выбранный источник списани€, чтобы после переформировани€ списка выбрать его снова, если допустимо
        var oldValFromResource = '';
        if (fromResource.selectedIndex >= 0)
            oldValFromResource = fromResource.options[fromResource.selectedIndex].value;
        //очищаем селект
        fromResource.length = 0;
        var needEmptySelect = true;
        if (oldValFromResource == '')
        {
            var cardsInfo = t.getAddCardsInfo();
            var accountsInfo = t.getAddAccountsInfo();
            if (accType == "ALL")
            {
                needEmptySelect  = accountsInfo['countAccounts'] + cardsInfo['countCards'] != 1;
                if (cardsInfo['countNotNullCards'] + accountsInfo['countNotNullAcc'] == 1)
                {
                    needEmptySelect = false;
                    oldValFromResource = accountsInfo['indexNotNullAcc'] != undefined ? accountsInfo['indexNotNullAcc'] : cardsInfo['indexNotNullCards'];
                }
            }
            else if (accType == "CARD")
            {
                needEmptySelect  = cardsInfo['countCards'] != 1;
                if (cardsInfo['countNotNullCards'] == 1)
                {
                    needEmptySelect = false;
                    oldValFromResource = cardsInfo['indexNotNullCards'];
                }
            }
            else if (accType == "DEPOSIT")
            {
                needEmptySelect  = accountsInfo['countAccounts'] != 1;
                if (accountsInfo['countNotNullAcc'] == 1)
                {
                    needEmptySelect = false;
                    oldValFromResource = accountsInfo['indexNotNullAcc'];
                }
            }
        }
        //добавл€ем нужные источнии списани€
        $.each(t.fromResources, function() {
            var code = this.code, val = this.view;
            if (fromResource.length == 0 && oldValFromResource == '' && needEmptySelect)
            {
                fromResource.options[fromResource.length] = new Option("¬ыберите счет/карту списани€", "");
            }
            if(accType == "ALL")
            {
                fromResource.options[fromResource.length] = new Option(val, code);
            }
            else if(accType == "DEPOSIT" && code.indexOf("account:") != -1)
            {
                fromResource.options[fromResource.length] = new Option(val, code);
            }
            else if(accType == "CARD" && code.indexOf("card:") != -1)
            {
                fromResource.options[fromResource.length] = new Option(val, code);
            }
            //„тобы не слетало значение источника списани€
            if (code == oldValFromResource)
                fromResource.selectedIndex = fromResource.length-1;
        });
        //если ни одного источника не добавлено, то добавл€ем пустой option дл€ корректного отображени€
        if(fromResource.length == 0)
            fromResource.options[fromResource.length] = new Option("Ќет доступных счетов и карт", "");

        selectCore.init(ensureElement('paymentForm'));
    },

    updateProviderFields : function(haveImageHelp)
    {
        var t = this;
        var fields = t.currentProvider.getFields();

        var table = ensureElement('paymentForm');
        table.innerHTML = ""; // очищаем
        table.style.display = (fields.length > 0) ? "block" : "none";

        var hasSocialNetField;
        for(var i = 0; i < fields.length; i++)
        {
            if(t.currentProvider.isSocialNetProvider && fields[i].isSocialNet)
            {
                table.appendChild(fields[i].createRowElement("selectUserChoice", "ѕокупка ќ ", true, true, t.createSocialNetButtons(fields[i].externalId), "", ""));
                table.appendChild(fields[i].createRowElement("selectedUserSocialId", "ћой аккаунт", true, true, t.socialNetUserId, "", ""));
                hasSocialNetField = fields[i].externalId;
            }

            table.appendChild(fields[i].getRowElement(haveImageHelp, t.currentProvider.iTunes));
        }

        if (hasSocialNetField)
            t.selectUserForSocial(true, hasSocialNetField);
        // навешиваем всевозможные обработчики дл€ вновь добавленных полей
        payInput.setEvents(payInput, table);
        initMoneyFields(table);
        initDatePicker();
    },

    createSocialNetButtons : function(fieldExternalId)
    {
        var t = this;
        var buttons = document.createElement("div");
        buttons.id = "receiverSubTypeControl";
        buttons.appendChild(t.createButtonDiv("forMeButton", "inner firstButton", "ƒл€ себ€", true, fieldExternalId));
        buttons.appendChild(t.createButtonDiv("forOtherButton", "inner lastButton", "ƒл€ другого пользовател€", false, fieldExternalId));
        return buttons;
    },

    createButtonDiv : function(id, className, text, isUser, fieldExternalId)
    {
        var t = this;
        var button = document.createElement("div");
        button.id = id;
        button.className = className;
        button.appendChild(document.createTextNode(text));
        button.onclick = function(){
            t.selectUserForSocial(isUser, fieldExternalId);
        };
        return button;
    },

    selectUserForSocial : function(isUser, fieldExternalId)
    {
        var t = this;
        $('#receiverSubTypeControl').find('div').removeClass('activeButton');
        var socialPaymentFieldField = $("#" + fieldExternalId);
        var socialPaymentFieldFieldRow = $("#" + fieldExternalId + "Row");
        if (isUser)
        {
            socialPaymentFieldFieldRow.hide();
            $("#selectedUserSocialIdRow").show();
            $('#forMeButton').addClass('activeButton');
            t.otherUserSocialAccount = socialPaymentFieldField.val();
            socialPaymentFieldField.val(t.socialNetUserId);
        }
        else
        {
            socialPaymentFieldFieldRow.show();
            $("#selectedUserSocialIdRow").hide();
            $('#forOtherButton').addClass('activeButton');
            socialPaymentFieldField.val(t.otherUserSocialAccount);
        }
    },

    showProvider : function(id)
    {
        var t = this;
        payInput.fieldClearError("recipient");
        if (id == "")
       {
           t.showEmptyProvider();
            return;
       }

        t.currentProvider = t.getProviderById(id);

        var provId = t.currentProvider.id;
        var nameProviderElement = ensureElement("nameProvider");
        var nameAccountLabel = ensureElement("accountLabel");

        if(t.currentProvider.iTunes)
            nameAccountLabel.innerHTML = "—чет списани€";
        else
            nameAccountLabel.innerHTML = "ќплата с";
        $("#resourceSelectRow").show();
        if (!$.isEmptyObject(nameProviderElement))
            nameProviderElement.innerHTML =  t.currentProvider.providersName;

        var currentProviderImageHelp = t.currentProvider.imageHelpSrc;
        var haveImageHelp = currentProviderImageHelp != undefined && currentProviderImageHelp != '';
        if (haveImageHelp)
        {
            $(".paymentImageHelp").show();
            $("#imageHelpSrc").val(currentProviderImageHelp);
        }
        else
            $(".paymentImageHelp").hide();

        setElement("recipient",provId);
        t.updateProviderFields(haveImageHelp);
        t.updateFromResource();
        t.addMessageRegionProvider(provId);
    },

    //показываем форму, с пустым полем "выберите услугу",т.е. с невыбранным поставщиком
    showEmptyProvider : function()
    {
        var t = this;
        t.currentProvider = t.getProviderById(-1);

        var nameProviderElement = ensureElement("nameProvider");
        if (!$.isEmptyObject(nameProviderElement))
            nameProviderElement.innerHTML =  t.currentProvider.providersName;

        var currentProviderImageHelp = t.currentProvider.imageHelpSrc;
        var haveImageHelp = currentProviderImageHelp != undefined && currentProviderImageHelp != '';
        if (haveImageHelp)
        {
            $(".paymentImageHelp").show();
            $("#imageHelpSrc").val(currentProviderImageHelp);
        }
        else
            $(".paymentImageHelp").hide();
        //в поле "выберите услугу выбираем "выберите услугу"
        setElement("recipient","");
        //скрываем поле выбора счета списани€
        $("#resourceSelectRow").hide();
        //скрываем доп пол€
        var table = ensureElement('paymentForm');
        table.innerHTML = ""; // очищаем
        table.style.display = "none";
    },
    postUpdateFieldValue : function()
    {
        var t = this;
        t.updateCheckBoxField();
    },

    updateCheckBoxField : function()
    {
        var t = this;
        var providerFields = t.currentProvider.getFields();
        if (providerFields == null)
            return;

        for(var i = 0; i < providerFields.length; i++ )
        {
            if(providerFields[i].type != 'set')
                continue;
            // ищем поле в котором хран€тс€ значени€ отмеченных чекбоксов
            var checkboxValues = ensureElement("field(" + providerFields[i].externalId + ")");
            // если оно пустое, то переходим к следующему множеству --%>
            if(checkboxValues == null || checkboxValues.value == '')
                continue;
            // значени€ выбранных чекбоксов хран€тс€ ввиде значение1@значение2@значение3
            var values = checkboxValues.value.split("@");
            // находим чекбоксы которые отмечены и ставим атрибут checked
            for(var j = 0; j < values.length; j++)
                $('#' + providerFields[i].externalId + '_' + values[j]).attr("checked", "true");
        }
    }
};

ServicesPaymentForm.prototype.FromResource = function(type, code, view, rest){
    var t = this;
    t.type = type;
    t.code = code;
    t.view = view;
    t.rest = rest;
};

ServicesPaymentForm.prototype.Provider = function(id, providersName, accountType, accessAutoPayment, fields, accessCurrentRegion, iqw, imageHelpSrc, iTunes, isSocialNetProvider){
    var t = this;
    t.id = id;
    t.providersName = providersName;
    t.accountType = accountType;
    t.accessAutoPayment = accessAutoPayment;
    t.fields = fields;
    t.accessCurrentRegion = accessCurrentRegion;
    t.iqw = iqw;
    t.imageHelpSrc = imageHelpSrc;
    t.iTunes = iTunes;
    t.fields = [];
    t.isSocialNetProvider = isSocialNetProvider;
};

ServicesPaymentForm.prototype.Provider.prototype = {
    addField : function(field)
    {
        var t = this;
        field.parent = t;
        t.fields.push(field);
    },

    getFields : function()
    {
        var t = this;
        return t.fields;
    },

    getSocialPaymentField : function()
    {
        var t = this;
        for(var i = 0; i < t.fields.length; i++)
        {
            if(t.fields[i].isSocialNet)
                return t.fields[i];
        }
        return null;
    }
};

ServicesPaymentForm.prototype.Provider.prototype.Field = function(id, name, description, type, defaultValue, externalId, maxLength, holderId, required, hint, visible, editable, listValues, disabled, extendedDescId, isMaskValue, businessSubType, isSocialNet)
{
    var t = this;
    t.id = id;
    t.name = name;
    t.description = description;
    t.type = type;
    t.defaultValue = defaultValue;
    t.externalId = externalId;
    t.maxLength = maxLength;
    t.holderId = holderId;
    t.required = required;
    t.hint    =  hint;
    t.listValues = listValues;
    t.visible = visible;
    t.editable = editable;
    t.disabled = disabled;
    t.extendedDescId = extendedDescId;
    t.isMaskValue = isMaskValue;
    t.businessSubType = businessSubType;
    t.isSocialNet = isSocialNet;
};

ServicesPaymentForm.prototype.Provider.prototype.Field.prototype =
{
    getRowElement : function(imageHelp, iTunes)
    {
        var t = this;
        var el;
        var name = t.name;
        switch(t.type)
        {
            case "string":
            case "number":
            case "date":
            case "integer":
            case "money":
                el = t.createInput(t.type);
                break;
            case "set":
                el = t.createSet();
                break;
            case "list":
                el = t.createList();
                break;
            case "choice":
                el = t.createChoice();
                name = null; // в данном случае его отображать не нужно
        }

        return t.createRowElement(t.externalId, name, t.required, t.visible, el, t.createHint(), imageHelp && t.visible && t.editable, t.type, t.businessSubType, iTunes);
    },

    createInput : function(type)
    {
        var t = this;
        var className = "";
        if(type == "date")
            className = "dot-date-pick";
        else if(type=="money")
            className = "moneyField";

        var input = document.createElement("input");
        input.id = t.externalId;
        input.className = className + (t.isMaskValue ? " masked-phone-number" : "");
        input.type = "text";
        input.name = 'field('+t.externalId+')';
        input.readOnly = !t.editable;
        input.disabled = (!t.editable && t.visible) || t.disabled;
        input.value = t.defaultValue;

        var maxLength = parseInt(t.maxLength);
        if(!isNaN(maxLength) && maxLength != null)
        {
            var size = maxLength + 3;
            input.size = size > 50 ? 50 : size;
            input.maxLength = maxLength;
        }

        if(t.businessSubType == 'phone')
        {
            con = document.createElement("span");
            con.appendChild(document.createTextNode("+7 "));
            con.appendChild(input);
            return con;
        }

        if(type=="money")
        {
            con = document.createElement("span");
            con.appendChild(input);
            con.appendChild(document.createTextNode(" руб."));
            return con;
        }

        return input;
    },

    createList : function()
    {
        var t = this;
        var select = document.createElement("select");
        select.id = t.externalId;
        select.name = 'field('+t.externalId+')';
        select.readOnly = !t.editable;
        select.disabled = t.disabled;

        for(var i = 0; i < t.listValues.length; i++)
        {
            var option = document.createElement("option");
            option.selected = t.defaultValue == t.listValues[j];
            option.setAttribute('value', t.listValues[i]);
            option.appendChild(document.createTextNode(t.listValues[i]));
            select.appendChild(option);
        }

        return select;
    },

    createSet : function()
    {
        var t = this;
        var container = document.createElement("span");
        var set = getElement("field(" + t.externalId + ")");
        var checkedSetValuesAsArray;
        var checkedSetValuesAsString = "";

        for (var j = 0; j < t.listValues.length; j++)
        {
            var checked  = false;
            var setValue = t.listValues[j];
            //если значени€ уже заданы на форме
            if (set != null)
            {
                checkedSetValuesAsString = set.value;
                checkedSetValuesAsArray  = checkedSetValuesAsString.split("@");

                for (var v = 0; v < checkedSetValuesAsArray.length; v++)
                {
                    if (checkedSetValuesAsArray[v] == setValue)
                    {
                        checked = true;
                        break;
                    }
                }
            }
            //если открыли впервые, то устанавлмваем значение по-умолчанию
            else if (t.defaultValue == setValue)
            {
                checked = true;
                if (checkedSetValuesAsString != "")
                    checkedSetValuesAsString = checkedSetValuesAsString + "@";
                checkedSetValuesAsString = checkedSetValuesAsString + setValue;
            }

                //формируем поле сета
            var checkbox = document.createElement("input");
            checkbox.id = t.externalId + "_" + setValue;
            checkbox.type = "checkbox";
            checkbox.disabled = t.disabled;
            checkbox.checked = checked;
            checkbox.onclick = function(){
                t.changeSetValue(this);
            };
            container.appendChild(checkbox);
            container.appendChild($("<span>&nbsp;" + setValue + "</span>").get(0));
            container.appendChild(document.createElement("br"));
        }

        container.appendChild(t.createHidden(t.externalId, t.externalId, checkedSetValuesAsString));
        return container;
    },

    createHidden : function(id, name, value)
    {
        var t = this;
        var input = document.createElement("input");
        input.id = id;
        input.type = "hidden";
        input.name = 'field('+ id +')';
        input.value = value;
        return input;
    },

    createChoice : function()
    {
        var t = this;
        var beginLink = '<a href="#" onclick="openExtendedDescWin(\'' + t.extendedDescId + '\'); return false;">', endLink = '</a>';
        var desc = t.name.replace("\[url\]", beginLink).replace('\[/url\]', endLink);
        var input = document.createElement("input");
        input.id = t.externalId;
        input.type = "checkbox";
        input.name = "field("+ t.externalId +")";
        input.value = "true";
        var container = document.createElement("span");
        container.appendChild(input);
        container.appendChild(document.createTextNode('\u00A0'));
        container.appendChild($("<span>" + desc + "</span>").get(0));
        return container;
    },

    createHint : function()
    {
        var t = this;
        var hint;
        if (t.description == '' && t.hint != '')
        {
            hint = '&nbsp;<a href="#" onclick="payInput.openDetailClick(this); return false;"> ак заполнить это поле?</a>'+
            '<div class="detail" style="display: none">'+t.hint+'</div>';
        }
        else if (t.description != '' && t.hint != '')
        {
            hint = t.description +'&nbsp;<a href="#" onclick="payInput.openDetailClick(this); return false;">ѕодробнее...</a>'   +
                    '<div class="detail" style="display: none">'+t.hint+'</div>';
        }
        else
        {
            hint = t.description;
        }

        return hint;
    },

    changeSetValue : function(checkBox)
    {
        var checkBoxId    = checkBox.id;
        var checkBoxValue = checkBoxId.substr(checkBoxId.indexOf("_") + 1, checkBoxId.length);

        var set      = ensureElement(checkBoxId.substr(0, checkBoxId.indexOf("_")));
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
    },

    createRowElement : function(externalId, fieldName, required, isVisible, value, hint, isImageHelpProvider, type, businessSubType, iTunes)
    {
        var t = this;
        var imageHelpProvider =
                '<div class="paymentValue" id="imageHelpProviderField' + externalId + '">'+
                    '<a href="#" class="imageHelpTitle" onclick="paymentImageHelpFieldAction(' + "'" + externalId + "'" + ');return false;">образец квитанции</a>'+
                    '<div class="imageHelp" style="display:none"></div>'+
                '</div>';

        var strRowElement =
                '<div class=' + ((iTunes && externalId == 'Summ') ? '"form-row rowFirstITunes"' : '"form-row"')  + (isVisible ? '' : 'style="display:none"') + ' id="'+ externalId +'Row" onclick="payInput.onClick(this)">' +
                    '<div class="paymentLabel"><span class="paymentTextLabel">'+ (fieldName == null ? '' : fieldName) +'</span>'+ (required ? '<span class="asterisk" id="asterisk_">*</span>' : '') + (fieldName != null ? ':' : '') + '</div>' +
                    '<div class="paymentValue">'  +
                        '<div class="paymentInputDiv"></div>'+
                        '<div style="display: none;" class="description">'+ hint + (isImageHelpProvider ? imageHelpProvider : '') + '</div>' +
                        '<div style="display: none;" class="errorDiv"></div>'  +
                    '</div>' +
                    '<div class="clear">'+
                    '</div>' +
                '</div>';

        var el = $(strRowElement);
        var payValue = el.find(".paymentInputDiv");

        payValue.append(value); // innerHTML нельз€, т.к. тер€ютс€ навешенные обработчики
        if(t.parent.parent.isUseProfileDocuments)
        {
            var res = document.createElement("input");
            res.name = t.externalId + "___document";
            res.type = "hidden";

            payValue.append(t.createProfileDocumentsLink(el));
            payValue.append(res);
        }

        return el.get(0);
    },

    createProfileDocumentsLink : function(ref)
    {
        var container = document.createElement("span");
        container.className = "document-info";
        $(container).click(function(){
            var winItem = win.getWinItem('profileDocuments');
            if(winItem == null)
                return;

            winItem.referFormRow = ref;
            win.open('profileDocuments');
        });

        container.innerHTML = '<span>ћои документы</span>';
        return container;
    }
};
