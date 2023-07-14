function PaymentReceiver()
{
    this.receiverIdentifierMask = null;  //установленная в текущий момент маска ввода в поле "Получатель"
    this.lastReceiverIdentifierValue = ""; //последнее введенное в поле получатель значение
    this.isShowLogicErrors = false;
    this.selectedContact = null;
    this.yandexMessageShow = false;
    this.isOurBankCard = false;
    this.staticImagesUrl = '';
    this.smallAvatarPath = '';
    this.iconAvatarPath = '';
    this.isByTemplate = false;
    this.cancelInputFocused = false;
    this.isPhoneLimitExceeded = false; // признак достижения лимита запросов по номеру телефона

    /**
     * Инициализация обработчика
     * @param paymentType тип платежа
     * @param initData начальные данные
     * @param isOurBankCard признак перевода в на карту клиента сбербанка.
     * @param staticImagesUrl - путь до аватара получателя
     * @param resourceRoot - путь до стат.ресурсов
     *
     */
    this.init = function(paymentType, initData, isOurBankCard, staticImagesUrl, resourceRoot)
    {
        this.staticImagesUrl = staticImagesUrl + "/SMALL";
        this.smallAvatarPath = resourceRoot + '/commonSkin/images/profile/SMALL.png';
        this.iconAvatarPath = resourceRoot + '/commonSkin/images/profile/ICON.png';
        this.isOurBankCard = isOurBankCard != '' && isOurBankCard != 'false';
        initPaymentReceiverHelp(paymentType);
        if (initData == null)
            return;

        this.yandexMessageShow = initData.yandexMessageShow == 'true';
        this.isByTemplate = initData.isByTemplate == 'true';

        addReceiverIdentifierInput(this);
        if((paymentType == 'ourContact' && initData.contactName != '') || (paymentType == 'ourContactToOtherCard' && initData.contactName != '') || paymentType == 'yandexWalletOurContact' ||
                (paymentType == 'ourPhone' && initData.contactName != '') || (paymentType == 'yandexWalletByPhone' && initData.contactName != ''))
        {
            var data = {'fullName': initData.contactName, 'phone': initData.maskedPhoneNumber, 'cardNumber': initData.externalCardNumber,
                'sberbankClient': initData.contactSberbank, "avatarPath" : initData.avatarPath};
            this.sendContactData(data, false);
        }
        else if(paymentType == 'ourCard')
        {
            var input = $('#receiverIdentifier');
            if (initData.externalCardNumber != '')
            {
                this.setCardValue(input, initData.externalCardNumber);
                showEnteredIdentifier($(input).val());
                setReceiverIdentifier(initData.externalCardNumber, null, null, null);
            }
        }
        else if(paymentType == 'masterCardExternalCard' || paymentType == 'visaExternalCard')
        {
            var input = $('#receiverIdentifier');
            if (initData.externalCardNumber != '')
            {
                this.setCardValue(input, initData.externalCardNumber);
                showEnteredIdentifier($(input).val());
                setReceiverIdentifier(initData.externalCardNumber, null, null, null);
            }
            else if (initData.externalPhoneNumber != '')
            {
                this.setPhoneValue(input, '+7' + initData.externalPhoneNumber);
                setReceiverIdentifier(null, initData.externalPhoneNumber, null, null);
            }
        }
        else if (paymentType == 'ourPhone' || paymentType == 'ourContactToOtherCard')
        {
            this.setPhoneValue($('#receiverIdentifier'), '+7' + initData.externalPhoneNumber);
            showEnteredIdentifier('+7 ' + initData.externalPhoneNumber);
            setReceiverIdentifier(null, initData.externalPhoneNumber, null, null);
        }
        else if (paymentType == 'yandexWalletByPhone')
        {
            if (initData.externalPhoneNumber != undefined)
            {
                this.setPhoneValue($('#receiverIdentifier'), '+7' + initData.externalPhoneNumber);
                showEnteredIdentifier('+7 ' + initData.externalPhoneNumber);
                setReceiverIdentifier(null, initData.externalPhoneNumber, null, null);
            }
            $('#enteredIdentifier .bluePointer').addClass("ya-reciever");
            $('#selectedContactEl .bluePointer').addClass("ya-reciever");
            $('#receiverIdentifier').addClass("ya-reciever");
            $('#receiverIdentifierContaner').addClass("ya-reciever");
            $('#yandexPay').show();
        }
        else if (paymentType == 'yandexWallet')
        {
            this.setYandeValue($('#receiverIdentifier'), initData.externalWalletNumber);
            if (initData.externalWalletNumber != '')
            {
                showEnteredIdentifier(initData.externalWalletNumber);
                setReceiverIdentifier(null, null, null, initData.externalWalletNumber);
            }
            $('#enteredIdentifier .bluePointer').addClass("ya-reciever");
            $('#selectedContactEl .bluePointer').addClass("ya-reciever");
            $('#yandexPay').show();
        }

        var input = $('#receiverIdentifier');
        if (!compareTitle(input))
            input.removeClass('inputPlaceholder');

        if (paymentType != 'masterCardExternalCard' && paymentType != 'visaExternalCard' && paymentType != 'ourContactToOtherCard' && initData.receiverName != '')
        {
            $('#mbContactInfoFIO').text(initData.receiverName);
            showMbContactInfo(this);
            initMbContactAvatar(this, initData.avatarPath);
        }

        var receiver = $('#receiverIdentifier');
        var contactEl = $('#editContactEl');
        //убираем карандаш, если платеж по шаблону (поле недоступно для редактирования)
        if($(receiver) && $(receiver).attr("disabled") && $(contactEl))
        {
            $(contactEl).removeClass('sugestEdit');
        }
    };

    /**
     * смена типа платежа (на карту сбера, на карту другого банка, на счет)
     * вызывается при нажатии клиентом соот. кнопок, расположенных выше платежной формы
     * @param oldPaymentType - тип платежа, установленный до этого
     * @param paymentType - новый тип платежа
     * @param clearReceiver - требуется ли очистка данных о получателе платежа, хранящихся в js-переменных
     */
    this.changePaymentType = function(oldPaymentType, paymentType, clearReceiver)
    {
        if (oldPaymentType == paymentType)
            return;
        if (paymentType == '' && isPaymentToOurClient(oldPaymentType))
            return;
        if (paymentType == 'yandexWallet' && isYandexPayment(oldPaymentType))
            return;
        if (paymentType == 'ourContactToOtherCard' && isPaymentToOtherCard(oldPaymentType))
            return;

        if (clearReceiver)
        {
            clearContactData(this);
            addReceiverIdentifierInput(this);
        }

        this.lastReceiverIdentifierValue = "";
        hideLogicErrors(this);
        payInput.fieldClearError('receiverIdentifier');
        var existContact = isNotEmpty($('#externalContactId').val());

        var newReceiverTitle = getPaymentReceiverTitle(paymentType);
        var yandexPayment = isYandexPayment(paymentType);
        if (yandexPayment || isYandexPayment(oldPaymentType))
        {
            if (existContact || this.receiverIdentifierMask == 'phone')
                updatePaymentReceiverTitle(newReceiverTitle);
            else
            {
                clearContactData(this);
                addReceiverIdentifierInput(this);
            }

            initPaymentReceiverHelp(paymentType);
            hideOrShow('yandexPay', !yandexPayment);
            var receiverIdentifierContaner = $('#receiverIdentifierContaner');
            if (yandexPayment)
            {
                receiverIdentifierContaner.addClass("ya-reciever");
                $('#enteredIdentifier .bluePointer').addClass("ya-reciever");
                $('#selectedContactEl .bluePointer').addClass("ya-reciever");
            }
            else
            {
                receiverIdentifierContaner.removeClass("ya-reciever");
                $('.bluePointer').removeClass("ya-reciever");
            }
        }

        if (existContact)
            this.changeContactData();
        else if (isEmpty($('#receiverIdentifier').val()))
            addReceiverIdentifierInput(this);

        if (yandexPayment)
            hideMbContactInfo();
        else
            showMbContactInfo(this);

        if (yandexPayment)
        {
            if (this.receiverIdentifierMask == 'phone')
                updateReceiverSubType('yandexWalletByPhone');
            else if (existContact)
                updateReceiverSubType('yandexWalletOurContact');
            else
                updateReceiverSubType('yandexWallet');
        }
        else if (isPaymentToOtherCard(paymentType))
        {
            if (existContact)
                updateReceiverSubType('ourContactToOtherCard');
            else if (this.receiverIdentifierMask == 'card')
                updateReceiverSubType(getOtherCardPaymentType($('#receiverIdentifier').val()));
        }
        else if (isPaymentToOurClient(paymentType))
        {
            if (this.receiverIdentifierMask == 'phone')
                updateReceiverSubType('ourPhone');
            else if (existContact)
                updateReceiverSubType('ourContact');
            else if (this.receiverIdentifierMask == 'card')
                updateReceiverSubType('ourCard');
        }

        var hintSpan = $("#phoneHintSpan");
        var spanHtml = hintSpan.html();

        if (this.isPhoneLimitExceeded)
        {
            if(yandexPayment)
            {
                hideLogicErrors(self);
                $("#phoneHint").html('Укажите номер в формате<br /> +79211234567');
                hintSpan.html(hintSpan.html().replace('<div class="strikePhone"></div>',''));
            }
            else
            {
                showLogicError('isLimitExceededError', self);
                $("#phoneHint").text('До 00:00 по московскому времени вам будут доступны только переводы клиентам Сбербанка по адресной книге и переводы по номеру карты');
                if(hintSpan.html().indexOf('<div class="strikePhone"></div>') < 0)
                    hintSpan.html('<div class="strikePhone"></div>' + spanHtml);
            }
        }
        else
        {
            hintSpan.html(hintSpan.html().replace('<div class="strikePhone"></div>',''));
        }

    };

    /**
     * Несмотря на название функции занимается она отображением (а не сменой) соотв.
     * в зависимости от типа платежа данных о получателе.
     */
    this.changeContactData = function()
    {
        var paymentType = $('#receiverSubType').val();
        if (isYandexPayment(paymentType))
        {
            $('#contactAvatarContainer').hide();
            $('#yandexIcon').show();
            $('#contactCardContainer').hide();
            $('#selectedContactPhone').show();
        }
        else
        {
            $('#contactAvatarContainer').show();
            $('#yandexIcon').hide();
            if (isPaymentToOurClient(paymentType))
            {
                $('#contactCardContainer').hide();
                $('#selectedContactPhone').show();
            }
            else
            {
                $('#selectedContactPhone').hide();
                $('#contactCardContainer').show();
                if (isNotEmpty(this.selectedContact.cardNumber))
                {
                   $('#selectedContactCardEl').show();
                   $('#NoCardEl').hide();
                }
                else
                {
                    $('#selectedContactCardEl').hide();
                    $('#NoCardEl').show();
                }
            }
        }
    };

    /**
     * Открытие справочника АК
     */
    this.openAddressBook = function()
    {
        var win = window.open(document.webRoot + '/private/userprofile/addressbook.do?dictionary=true',
                'AddressBook', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=700,height=650");
        win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
    };

    /**
     * Отображение/скрытие значка (карандаш) редактирования поля "Получатель"
     * @param show - true - отображать
     */
    this.showEditContact = function(show)
    {
        if (show)
            $('#editContactEl').show();
        else
            $('#editContactEl').hide();
    };

    /**
     * Редактирование выбранного контакта.
     * Вызывается при нажатии на блок с информацией о получателе
     */
    this.editSelectedContact = function()
    {
        var input = $('#receiverIdentifier');
        //если оплата по шаблону, то поле получателя некликабельно
        if(input && !input.attr("disabled"))
        {
            $('#selectedContactEl').hide();
            var externalPhoneNumber = $('#externalPhoneNumber').val();
            this.setPhoneValue(input, '+7' + (isNotEmpty(externalPhoneNumber) ? externalPhoneNumber : this.selectedContact.phone));
            this.lastReceiverIdentifierValue = input.val();
            input.show();
            showCancelInputButton(input.val());
            input.parents('.ya-reciever').removeClass('clearFon');
            input.focus();
            input.select();
        }
    };

    /**
     * Несмотря на название функция реализует отображение данных о получателе
     * @param data- данные
     * @param fromAddressBook - true, если данные получены при выборе из справочника АК
     */
    this.sendContactData = function(data, fromAddressBook)
    {
        var receiverIdentifier = $('#receiverIdentifier');
        receiverIdentifier.hide();
        $('#cancelIdentifierInput').hide();
        receiverIdentifier.parents('.ya-reciever').addClass('clearFon');
        $('#enteredIdentifierContent').text('');
        $('#enteredIdentifier').hide();
        payInput.fieldClearError('receiverIdentifier');
        clearMbContactData();
        hideMbContactInfo();
        hideLogicErrors(this);

        var paymentType = $('#receiverSubType').val();
        var isOtherCardPaymentType = isPaymentToOtherCard(paymentType);
        this.selectedContact = data;
        $('#selectedContactNameEl').text(data.fullName);
        var contactCardContainer = $('#contactCardContainer');
        var contactCardEl = $('#selectedContactCardEl');
        contactCardEl.text(data.cardNumber);
        $('#selectedContactPhoneEl').text(data.phone);
        if (isOtherCardPaymentType)
        {
            $('#selectedContactPhone').hide();
            contactCardContainer.show();

            if (data.cardNumber != '')
            {
                $('#NoCardEl').hide();
                contactCardEl.show();
                hideOrShow(ensureElement("messageToReceiverRow"), false);
            }
            else
            {
                hideOrShow(ensureElement("messageToReceiverRow"), true);
                contactCardEl.hide();
                $('#NoCardEl').show();
                if (data.sberbankClient == 'true')
                    showLogicError('noCardErrorOurClient', this);
                else
                    showLogicError('noCardErrorNotOurClient', this);
            }

            updateReceiverSubType('ourContactToOtherCard');
            $('#externalCardNumber').val(data.cardNumber);
        }
        else
        {
            $('#selectedContactPhone').show();
            contactCardContainer.hide();
        }

        $('#contactName').val(data.fullName);
        $('#contactSberbank').val(data.sberbankClient);
        $('#avatarPath').val(data.avatarPath);
        $('#contactAvatar').attr('src', data.avatarPath ? (this.staticImagesUrl + data.avatarPath) : this.smallAvatarPath);
        if (isYandexPayment(paymentType))
        {
            $('#contactAvatarContainer').hide();
            $('#yandexIcon').show();
        }
        else
        {
            $('#contactAvatarContainer').show();
            $('#yandexIcon').hide();
        }

        if(data.sberbankClient == 'true')
            $('#isOurClient').show();
        else
            $('#isOurClient').hide();

        $('#selectedContactEl').show();
        if(paymentType == 'yandexWalletOurContact'){
            $('#selectedContactEl').find('.bluePointer').addClass("ya-reciever");
            $('#enteredIdentifier').find('.bluePointer').addClass("ya-reciever");
            $('#receiverIdentifierContaner').addClass("ya-reciever");
            $('#receiverIdentifier').addClass("ya-reciever");
            $('#yandexPay').show();
        }

        if (fromAddressBook)
            setReceiverIdentifier('', data.phone, data.id, '');
        else
            setReceiverIdentifier('', null, data.id, '');

        var self = this;
        if (fromAddressBook)
        {
            if (isPaymentToOurClient(paymentType))
            {
                updateReceiverSubType('ourContact');
                sendContactRequest('contactId=' + data.id, true,
                    function(res)
                    {
                        if(isNotEmpty(res))
                            setContactMBKData(res, self, true);
                    });
            }
            else if(isYandexPayment(paymentType))
            {
                updateReceiverSubType('yandexWalletOurContact');
            }
        }

        if(isYandexPayment(paymentType) && this.yandexMessageShow)
            hideOrShow(ensureElement("messageToReceiverRow"),   false);
    };

    /**
     * Установка в поле "Получатель" номер телефона получателя
     * @param input - поле "Получатель"
     * @param value - номер телефона
     */
    this.setPhoneValue = function(input, value)
    {
        input.val(value);
        if (this.receiverIdentifierMask != 'phone')
        {
            input.createMask(PHONE_TEMPLATE_P2P_NEW, ' ');
            this.receiverIdentifierMask = 'phone';
        }
        input.removeClass('inputPlaceholder');
    };

    /**
     * Установка в поле "Получатель" номер карты получателя
     * @param input - поле "Получатель"
     * @param value - номер карты
     */
    this.setCardValue = function(input, value)
    {
        input.val(value);
        if (this.receiverIdentifierMask != 'card' && (value.indexOf('•') < 0) && (value.indexOf('*') < 0))
        {
            input.createMask(CARD_TEMPLATE_P2P_NEW, ' ');
            this.receiverIdentifierMask = 'card';
        }
        input.removeClass('inputPlaceholder');
    };

    /**
     * Установка в поле "Получатель" номер яндекс-кошелька
     * @param input - поле "Получатель"
     * @param value - номер яндекс-кошелька
     */
    this.setYandeValue = function(input, value)
    {
        input.val(value);
        if (this.receiverIdentifierMask != 'wallet')
        {
            input.createMask(YANDEX_WALLET_P2P_NEW, ' ');
            this.receiverIdentifierMask = 'wallet';
        }
        input.removeClass('inputPlaceholder');
        $('#receiverIdentifierContaner').addClass("ya-reciever");
    };

    /**
     * Обновить заголовок поля "Получатель"
     * @param title - заголовок
     */
    function updatePaymentReceiverTitle(title)
    {
        var receiverIdentifier = $('#receiverIdentifier');
        if (receiverIdentifier)
            receiverIdentifier.attr('title', title);
    }

    /**
     * Возвращает тип карты
     * @param card - номер карты
     */
    function getOtherCardPaymentType(card)
    {
        return card.substr(0, 1) == '4' ? 'visaExternalCard' : 'masterCardExternalCard';
    }

    /**
     * Инициализация подсказки к полю "Получатель"
     * @param paymentType - текущий тип платежа
     */
    function initPaymentReceiverHelp(paymentType)
    {
        if (isYandexPayment(paymentType))
        {
            $('#receiverHelpFormatType').text('Кошелек Яндекс.Деньги');
            $('#receiverHelpFormatDetail').text('10-33 цифры');
            $('#cardHintSpan').text('номер кошелька');
            $('#cardHint').text('Введите 10-33 цифр номера кошелька получателя');
        }
        else
        {
            $('#receiverHelpFormatType').text('Номер карты');
            $('#receiverHelpFormatDetail').text('15-19 цифр');
            $('#cardHintSpan').text('номер карты');
            $('#cardHint').text('Введите 15-19 цифр номера карты получателя');
        }
    }

    /**
     * Отправка запроса наполучение инфы по контакту
     * @param param - параметры запроса
     * @param showHelp - признак необходимости отображения анимированной подсказки во время поиска
     * @param callback - функция, выполняющаяся после выполнения запроса
     */
    function sendContactRequest(param, showHelp, callback)
    {
        if (showHelp)
            showHideIdentifyByPhone(true);
        ajaxQuery(param, document.webRoot + '/private/async/userprofile/getContactInfo.do', callback, "json", false);
    }

    /**
     * Переход в режим редактирования получателя платежа
     */
    this.editEnteredIdentifier = function()
    {
        $('#enteredIdentifier').hide();
        $('#enteredIdentifierContent').text('');
        var receiverIdentifier = $('#receiverIdentifier');
        receiverIdentifier.show();
        showCancelInputButton(receiverIdentifier.val());
        receiverIdentifier.parents('.ya-reciever').removeClass('clearFon');
        receiverIdentifier.focus();
        receiverIdentifier.select();
    };

    /**
     * Отображать ли поле "смс-сообщение получателю"
     */
    this.showMessageField = function()
    {
        var paymentType = $('#receiverSubType').val();
        return isPaymentToOurClient(paymentType) || paymentType == "ourContactToOtherCard" || this.isOurBankCard
                || (this.yandexMessageShow && (paymentType == "yandexWalletOurContact" || paymentType == "yandexWalletByPhone"));
    };

    /**
     * возвращает аниматор подсказки
     */
    var identifyByPhoneAnim = new Animator(4850, function(p){
        $("#identifyByPhone .shine").css("margin-left", Math.round(-406 + 232*p));
    }, "" , true);

    /**
     * отображение/скрытие подсказки "Идентификация по номеру телефона"
     * @param isShow - true - отображать
     */
    function showHideIdentifyByPhone(isShow)
    {
        if(isShow)
        {
            identifyByPhoneAnim.start();
            $('#identifyByPhone').show();
        }
        else
        {
            $('#identifyByPhone').hide();
            identifyByPhoneAnim.stop();
        }
    }

    /**
     * Показать/скрыть кнопку очистки поля "Получатель"
     * @param value - текст из поля
     */
    function showCancelInputButton(value)
    {
        if (value.length > 0)
            $("#cancelIdentifierInput").show();
        else
            $("#cancelIdentifierInput").hide();
    }

    /**
     * Очистка поля "Получатель"
     */
    this.cancelInput = function()
    {
        var receiverIdentifier = $('#receiverIdentifier');
        receiverIdentifier.val("");
        receiverIdentifier.keyup();
    };

    /**
     * Добавляет input элемент (поле "Получатель") на страницу
     */
    function addReceiverIdentifierInput(self)
    {
        $('#receiverIdentifier').remove();
        $('#receiverIdentifierContaner').append('<input id="receiverIdentifier" name="receiverIdentifier" type="text" maxlength="19" ' + (self.isByTemplate ? 'disabled' : '') +
                ' title="' + getPaymentReceiverTitle($('#receiverSubType').val()) + '" class="customPlaceholder" />');

        var receiverIdentifier = $('#receiverIdentifier');
        receiverIdentifier.blur(function (e) {
            setTimeout(function(){
                blurReceiverIdentifier(e.target.value, self);
            }, 150);
        });

        receiverIdentifier.click(function (e) {
            if (customPlaceholder.getCurrentVal(e.target) == '' || customPlaceholder.getCurrentVal(e.target) == null )
                this.selectionStart = this.selectionEnd = 0;
        });

        receiverIdentifier.keypress(function (e) {
            var charCode = e.charCode;
            if (!charCode)
                charCode = e.keyCode;

            var input = $(e.target);

            var value = trim(customPlaceholder.getCurrentVal(e.target));
            if ((e.ctrlKey && charCode == 118) || (e.shiftKey && charCode == 45))
            {
                if (value == '')
                {
                    input.removeClass('inputPlaceholder');
                }

                return true;
            }


            if (value == '')
            {
                var paymentToYandex = isYandexPayment($('#receiverSubType').val());
                var isCard = 52<=charCode && charCode<=54;
                var isWallet = 49<=charCode && charCode<=58;
                var isPlus = charCode==43;
                if (!isPlus && !(isCard && !paymentToYandex || isWallet && paymentToYandex))
                    return false;

                if (isPlus)
                {
                    input.val(String.fromCharCode(charCode));
                    input.removeClass('inputPlaceholder');
                    return false;
                }

                if (isCard && self.receiverIdentifierMask != 'card' && !paymentToYandex)
                {
                    self.setCardValue(input, String.fromCharCode(charCode));
                    input.caretSelect(1);
                    return false;
                }

                if (isWallet && self.receiverIdentifierMask != 'wallet' && paymentToYandex)
                {
                    self.setYandeValue($('#receiverIdentifier'), String.fromCharCode(charCode));
                    input.caretSelect(1);
                    return false;
                }
            }

            if (value == '+')
            {
                if (charCode==55 && self.receiverIdentifierMask != 'phone')
                {
                    self.setPhoneValue(input, '+7');
                    input.caretSelect(3);
                }
                if (charCode == 8 || charCode == 46)
                    $(e.target).val('');

                return false;
            }
        });

        receiverIdentifier.keyup(function (e) {
            var value = customPlaceholder.getCurrentVal(e.target);
            if (self.lastReceiverIdentifierValue != value)
            {
                payInput.fieldClearError('receiverIdentifier');
                hideLogicErrors(self);
                clearContactData(self);
                hideMbContactInfo();
                self.lastReceiverIdentifierValue = '';
            }
            showCancelInputButton(trim(value));

            if (trim(value) == '' && self.receiverIdentifierMask != null)
            {
                addReceiverIdentifierInput(self);
                self.receiverIdentifierMask = null;
                $('#receiverIdentifier').focus();
            }
        });
        customPlaceholder.init($('#receiverIdentifierRow'), true);
        $('#selectedContactEl').hide();
        hideMbContactInfo();
        $('#enteredIdentifier').hide();
        self.receiverIdentifierMask = null;
    }

    /**
     * формирует подсказку к полю "Получатель"
     * @param payType - тип платежа
     */
    function getPaymentReceiverTitle(payType)
    {
        if (isYandexPayment(payType))
            return 'Введите телефон или номер кошелька';
        else
            return 'Введите телефон или номер карты';
    }

    /**
     * Скрытие блока ошибок
     */
    function hideLogicErrors(self)
    {
        if (!self.isShowLogicErrors)
            return;

        $('.logicError').each(function(e){$(this).hide()});
        $('#errorContainer').hide();
        self.isShowLogicErrors = false;
    }

    /**
     * Отображение блока ошибок
     */
    function showLogicError(errorId, self)
    {
        self.isShowLogicErrors = true;
        $('.logicError').each(function(e){$(this).hide()});
        $('#' + errorId).show();
        $('#errorContainer').show();
    }

    /**
     * Установка фокуса в поле "Получатель"
     */
    function receiverIdentifierFocus()
    {
        setTimeout(function(){$('#receiverIdentifier').focus()}, 100);
    }

    /**
     * магия, выполныемая при потере фокуса полем "Получатель"
     * @param val - значение в поле "Получатель"
     */
    function blurReceiverIdentifier(val, self)
    {
        if (val == null)
            return;
        var value = trim(val);
        var isTitle = compareTitle(ensureElement('receiverIdentifier'));

        if (self.selectedContact != null)
        {
            $('#receiverIdentifier').hide();
            $('#cancelIdentifierInput').hide();
            $('#selectedContactEl').show();
            showMbContactInfo(self);
            return;
        }

        if (self.lastReceiverIdentifierValue == value || isTitle || value == '')
        {
            if (value != '' && !isTitle)
                showEnteredIdentifier(value);
            if (value == '')
                clearReceiverIdentifier();
            return;
        }

        clearContactData(self);

        var paymentType = $('#receiverSubType').val();
        var paymentToOurClient = isPaymentToOurClient(paymentType);
        var paymentToYandex = isYandexPayment(paymentType);
        var firstChar = value.substring(0, 1);
        var valueWithoutSpace = value.replace(/\s+/g, '');

        if ((templateObj.PHONE_NUMBER_SHORT.validate(valueWithoutSpace)))
        {
            if (firstChar == '7')
            {
                addReceiverIdentifierInput(self);
                var el = $('#receiverIdentifier');
                self.setPhoneValue(el, '+' + value);
                value = el.val();
            }
            else if (firstChar == '8')
            {
                addReceiverIdentifierInput(self);
                var el = $('#receiverIdentifier');
                self.setPhoneValue(el, '+7' + value.substring(1, value.length));
                value = el.val();
            }
        }
        else if (templateObj.PHONE_NUMBER_2.validate(valueWithoutSpace) && (valueWithoutSpace.substring(1, 2) == '7'))
        {
            addReceiverIdentifierInput(self);
            var el = $('#receiverIdentifier');
            self.setPhoneValue(el, value);
            value = el.val();
        }
        else if (!paymentToYandex && templateObj.CARD_TEMPLATE_SIMPLE.validate(valueWithoutSpace) && (firstChar == '4' || firstChar == '5' || firstChar == '6'))
        {
            addReceiverIdentifierInput(self);
            var el = $('#receiverIdentifier');
            self.setCardValue(el, value);
            value = trim(el.val());
        }
        else if (paymentToYandex && templateObj.YANDEX_WALLET_TEMPLATE_P2P_NEW.validate(valueWithoutSpace))
        {
            addReceiverIdentifierInput(self);
            var el = $('#receiverIdentifier');
            self.setYandeValue(el, value);
            value = trim(el.val());
        }

        self.lastReceiverIdentifierValue = value;
        var isPhone = templateObj.PHONE_NUMBER_P2P_NEW.validate(value);
        var isCard = templateObj.CARD_TEMPLATE_P2P_NEW.validate(value);
        var isWallet = templateObj.YANDEX_WALLET_TEMPLATE_P2P_NEW.validate(value);
        if (self.receiverIdentifierMask == 'phone')
        {
            setReceiverIdentifier('', value.substring(2, value.length), '');
            if (paymentToOurClient)
                updateReceiverSubType('ourPhone');
            else if (paymentToYandex)
                updateReceiverSubType('yandexWalletByPhone');
        }
        else if (self.receiverIdentifierMask == 'card')
        {
            setReceiverIdentifier(value.replace(/ /g, ''), '', '', '');
            if (paymentToOurClient)
            {
                if (paymentType == "ourCard" && receiverSubTypeButton != undefined && isPaymentToOtherCard(receiverSubTypeButton))
                {
                    var type = getOtherCardPaymentType(value);
                    updateReceiverSubType(type);
                    paymentType = type;
                    paymentToOurClient = false;
                }
                else
                    updateReceiverSubType('ourCard');

            }
            else
                updateReceiverSubType(getOtherCardPaymentType(value));
        }
        else if (self.receiverIdentifierMask == 'wallet')
        {
            setReceiverIdentifier('', '', '', value);
            updateReceiverSubType('yandexWallet');
        }

        if (isPaymentToOtherCard(paymentType) && isCard && !checkViaLuhn(value.replace(/ /g, '')))
        {
            payInput.fieldError("receiverIdentifier", 'Вы указали некорректный номер карты получателя. Пожалуйста, проверьте номер карты.');
            receiverIdentifierFocus();
        }
        else if (!isTitle && !isPhone && !(isCard && !paymentToYandex || isWallet && paymentToYandex))
        {
            payInput.fieldError("receiverIdentifier", "Поле должно содержать номер телефона или номер " + (paymentToYandex ? " кошелька." : " карты."));
            receiverIdentifierFocus();
        }
        else
        {
            showEnteredIdentifier(value);

            payInput.fieldClearError('receiverIdentifier');
            if (paymentToOurClient)
            {
                if (isPhone)
                {
                    sendContactRequest('phone=' + value, true,
                        function(data)
                        {
                            if (isNotEmpty(data))
                            {
                                var existContact = false;
                                if (data.id)
                                {
                                    <!--найден в АК-->
                                    self.sendContactData(data, false);
                                    existContact = true;
                                }
                                setContactMBKData(data, self, existContact);
                            }
                        });
                }
                else if (isCard)
                {
                    showCardIcon(value);
                    ajaxQuery('card=' + value.replace(/ /g, ''), document.webRoot + '/private/async/payments/findClientByCard.do',
                            function(data)
                            {
                                if(isNotEmpty(data))
                                {
                                    if (data.cardCurrency)
                                    {
                                        ensureElement("isErrorCurrency").value = "false";
                                        $('#externalCardCurrency').val(data.cardCurrency);
                                    }
                                    else
                                    {
                                        ensureElement("isErrorCurrency").value = "true";
                                        $('#externalCardCurrency').val("");
                                    }
                                    refreshForm();
                                    $('#mbContactInfoFIO').text(data.fio);
                                    showMbContactInfo(self);
                                    initMbContactAvatar(self, null);
                                }
                                else
                                {
                                    showLogicError('notOurCardError', self);
                                }
                            },
                            "json", false);
                }
            }
            else if (isPaymentToOtherCard(paymentType))
            {
                if (isPhone)
                {
                    sendContactRequest('phone=' + value, true,
                        function(data)
                        {
                            if (isNotEmpty(data))
                            {
                                var existContact = false;
                                if (data.id)
                                {
                                    <!--найден в АК-->
                                    self.sendContactData(data, false);
                                    existContact = true
                                }
                                setContactMBKData(data, self, existContact);
                            }
                        });
                }
                else if (isCard)
                {
                    showCardIcon(value);
                    ajaxQuery('card=' + value.replace(/ /g, ''), document.webRoot + '/private/async/payments/findClientByCard.do',
                            function(data)
                            {
                                if(isNotEmpty(data))
                                {
                                    $('#mbContactInfoFIO').text(data.fio);
                                    showMbContactInfo(self);
                                    initMbContactAvatar(self, null);
                                    updateReceiverSubType('ourCard');
                                    showLogicError('isOurCardError', self);
                                    self.isOurBankCard = true;
                                }
                                else
                                {
                                    self.isOurBankCard = false;
                                    $('#messageToReceiverRow').hide();
                                }

                            },
                            "json", false);
                }
            }
            else if (paymentToYandex)
            {
                if (isPhone)
                {
                    if (self.yandexMessageShow)
                        hideOrShow(ensureElement("messageToReceiverRow"),   false);

                    sendContactRequest('paymentType=yandexWallet&phone=' + value, false,
                            function(data)
                            {
                                if (isNotEmpty(data))
                                {
                                    if (data.id)
                                    {
                                        <!--найден в АК-->
                                        self.sendContactData(data, false);
                                    }
                                }
                            });
                }
                else
                    hideOrShow(ensureElement("messageToReceiverRow"),   true);
            }

        }
    }

    /**
     * Отображение иконки с типом карты
     * @param cardNumber - номер карты
     */
    function showCardIcon(cardNumber)
    {
        if (!isPaymentToCard($('#receiverSubType').val()))
            return;
        var type = cardNumber.substr(0, 1);
        var cardIcon = $('#cardIcon');
        if (type == '4')
            cardIcon.attr('class', 'cardVisa');
        else if (type == '5' || type == '6')
            cardIcon.attr('class', 'cardMasterCard');
        else return;

        cardIcon.show();
    }

    /**
     * Скрытие input "Получатель" и отображение введенного в поле значения в span-элементе
     * @param value
     */
    function showEnteredIdentifier(value)
    {
        $('#cardIcon').hide();
        var receiverIdentifier = $('#receiverIdentifier');
        receiverIdentifier.hide();
        $('#cancelIdentifierInput').hide();
        receiverIdentifier.parents('.ya-reciever').addClass('clearFon');
        $('#enteredIdentifierContent').text(value);
        $('#enteredIdentifier').show();
        showCardIcon(value);
    }

    /**
     * Определяет тип платежа
     */
    function isPaymentToOtherCard(paymentType)
    {
        return paymentType == 'masterCardExternalCard' || paymentType == 'visaExternalCard' || paymentType == "ourContactToOtherCard";
    }

    /**
     * Определяет тип платежа
     */
    function isPaymentToCard(paymentType)
    {
        return paymentType == 'ourCard' || paymentType == 'masterCardExternalCard' || paymentType == 'visaExternalCard';
    }

    /**
     * Определяет тип платежа
     */
    function isPaymentToOurClient(paymentType)
    {
        return paymentType == "" || paymentType == "ourCard" || paymentType == "ourPhone" || paymentType == "ourContact";
    }

    /**
     * Определяет тип платежа
     */
    function isYandexPayment(paymentType)
    {
        return paymentType == "yandexWallet" || paymentType == "yandexWalletOurContact" || paymentType == "yandexWalletByPhone";
    }

    /**
     * Установка на форму данных о получателе, полученные из МБК
     * @param data - данные
     * @param existContact - true,если контакт есть в АК
     */
    function setContactMBKData(data, self, existContact)
    {
        showHideIdentifyByPhone(false);
        var toOtherCard = isPaymentToOtherCard($('#receiverSubType').val());
        var hintSpan = $("#phoneHintSpan");
        var spanHtml = hintSpan.html();
        //достигнут ли лимит запросов по номеру телефона
        if (data.limitExceeded == "true")
        {
            self.isPhoneLimitExceeded = true;
            showLogicError('isLimitExceededError', self);
            $("#phoneHint").text('До 00:00 по московскому времени вам будут доступны только переводы клиентам Сбербанка по адресной книге и переводы по номеру карты');
            hintSpan.html('<div class="strikePhone"></div>' + spanHtml);
            return;
        }
        else
        {
            self.isPhoneLimitExceeded = false;
            hintSpan.html(hintSpan.html().replace('<div class="strikePhone"></div>',''));
        }

        if (data.externalSystemError == "true")
        {
            showLogicError('isExternalSystemError', self);
            $("#phoneHint").text('По техническим причинам вы не можете выполнить данную операцию. Пожалуйста, повторите попытку позже.');
            hintSpan.html('<div class="strikePhone"></div>' + spanHtml);
            return;
        }

        if (data.wayFIO)
        {
            if (!toOtherCard)
            {
                if (data.cardCurrency)
                {
                    ensureElement("isErrorCurrency").value = "false";
                    $('#externalCardCurrency').val(data.cardCurrency);
                    refreshForm();
                }
                else
                {
                    ensureElement("isErrorCurrency").value = "true";
                    $('#externalCardCurrency').val("");
                    refreshForm();
                }
            }
            else
            {
                showLogicError('isOurClientError', self);
            }

            $('#mbContactInfoFIO').text(data.wayFIO);
            showMbContactInfo(self);
            initMbContactAvatar(self, existContact ? null : data.avatarPath);
        }
        else
        {
            <!--контакт не найден в ЕРМБ/МБК-->
            if (toOtherCard)
                showLogicError('notFoundClientError', self);
            else
                showLogicError('notOurClientError', self);
        }

        if(existContact && data.sberbankClient == 'true')
            $('#isOurClient').show();
        else
            $('#isOurClient').hide();
    }

    /**
     * Установка данных о получателе в hidden-поля
     * @param card - номер карты
     * @param phone - номер телефона
     * @param contact - контакт из АК
     * @param yandexWallet - номер яндекс-кошелька
     */
    function setReceiverIdentifier(card, phone, contact, yandexWallet)
    {
        if (card != null)
            $('#externalCardNumber').val(card);
        if (phone != null)
            $('#externalPhoneNumber').val(phone);

        if(contact != null)
            $('#externalContactId').val(contact);
        if (yandexWallet != null)
            $('#externalWalletNumber').val(yandexWallet);
    }

    /**
     * Очистка hidden-полей
     */
    function clearReceiverIdentifier()
    {
        $('#externalCardNumber').val('');
        $('#externalPhoneNumber').val('');
        $('#externalContactId').val('');
        $('#externalWalletNumber').val('');
    }

    /**
     * Очистка дивов с информацие о получателе
     */
    function clearContactData(self)
    {
        if (self.selectedContact != null)
        {
            self.selectedContact = null;
            $('#externalContactId').val('');
            $('#contactName').val('');
            $('#contactSberbank').val('');
            $('#avatarPath').val('');
        }

        clearMbContactData();
    }

    /**
     * Очистка дивов с информацие о получателе
     */
    function clearMbContactData()
    {
        $('#mbAvatarIconImg').attr('src', '');
        $('#mbContactInfoFIO').text('');
    }

    /**
     * Скрытие блока с инфой из МБК о получателе
     */
    function hideMbContactInfo()
    {
        $("#mbContactInfo").hide();
    }

    /**
     * отображение аватара контакта
     * @param avatarPath - путь по аватара
     */
    function initMbContactAvatar(self, avatarPath)
    {
        var existContact = isNotEmpty($('#externalContactId').val());
        var mbContactInfoFIO = $('#mbContactInfoFIO');
        if (existContact)
        {
            <!--не отображаем аватар-->
            $('#mbAvatarIcon').hide();
            mbContactInfoFIO.removeClass("allItems");
        }
        else if (self.receiverIdentifierMask == 'phone' && avatarPath)
        {
            <!--отображаем аватар контакта-->
            $('#avatarPath').val(avatarPath);
            $('#mbAvatarIconImg').attr('src', self.staticImagesUrl + avatarPath);
            $('#mbAvatarIcon').show();
            mbContactInfoFIO.addClass("allItems");
        }
        else
        {
            <!--отображаем аватар-заглушку-->
            $('#avatarPath').val('');
            $('#mbAvatarIconImg').attr('src', self.iconAvatarPath);
            $('#mbAvatarIcon').show();
            mbContactInfoFIO.addClass("allItems");
        }
    }

    /**
     * Отображение инфы о получателе из МБК
     */
    function showMbContactInfo(self)
    {
        var mbContactInfoFIO = $('#mbContactInfoFIO');
        if (isEmpty(mbContactInfoFIO.text()))
            return;

        $('#receiverIdentifierRow').find('.paymentValueNew').addClass('float'); /*for IE8*/
        $("#mbContactInfo").show();
    }

    /**
     * Проверка номера карты по Луну
     * @param number - номер карты
     */
    function checkViaLuhn(number)
    {
        var zero = '0';


        var checksum = 0;

        for (var i = 0; number.length > i; i++)
        {

            var currentDigitCost = number.charAt(i) - zero;

            if (i % 2 == number.length % 2)
            {
                currentDigitCost *= 2;
                if (currentDigitCost > 9)
                    currentDigitCost-=9;
            }
            checksum += currentDigitCost;
        }

        checksum %= 10;

        return (checksum == 0)
    }
}

var paymentReceiver = new PaymentReceiver;