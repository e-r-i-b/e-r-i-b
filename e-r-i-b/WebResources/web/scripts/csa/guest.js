/**
 * User: usachev
 * Date: 29.12.14
 * Прощу прощения за стол ужасный код.
 */

var page = {
    step: "step1",
    useCaptcha: false,
    $buttonNext: null,
    $blockStep1: null,
    $blockStep2: null,
    $blockCaptcha: null,
    $titleWithPhone: null,
    $inputPhone: null,
    $inputSmsPassword: null,
    $titlePhone: null,
    userPhone: null,
    focused: true,
    path: null,
    token: null,
    moduleUrl: null,
    urlCaptcha: null,
    authToken: null,
    timer: null,
    marginTop: -100,
    height: 0,
    typeClaim: null,
    $errorPopup: null,
    $linkOnTimer: null,
    errorMessage: {
        reWritePassword: "Введен неверный пароль. Попробуйте еще раз",
        enterCaptcha: 'Введите код с картинки',
        inputAllDigit: 'Введите все цифры номера мобильного телефона',
        inputPhoneNumber: 'Введите номер вашего мобильного телефона'
    },

    /**
     * Инициализация станицы
     * @param path Адрес на который нужно отправлять запросы
     * @param modulesUrl Имя текущего приложения
     * @param useCaptcha Нужно ли использовать CAPTCHA
     * @param typeClaim Тип заявки
     */
    init: function (path, modulesUrl, useCaptcha, typeClaim)
    {
        this.path = path;
        this.moduleUrl = modulesUrl;
        this.urlCaptcha = modulesUrl + "/captcha.png";
        this.useCaptcha = useCaptcha;
        this.$blockCaptcha = $('#blockCaptcha');
        this.typeClaim = typeClaim;
        if (this.useCaptcha)
        {
            this.updateCaptcha();
            this.$blockCaptcha.css('display', 'block');
        }
        $(".field").each(function ()
        {
            $(this).val("");
        });
        this.$titlePhone = $("#phone");
        this.$buttonNext = $("buttonNext");
        this.$buttonNext.active = true;
        this.$blockStep1 = $('#step1');
        this.$blockStep2 = $('#step2');
        this.$titleWithPhone = $("#titleWithPhone");
        this.$inputPhone = $('#phoneNumber');
        this.$inputPhone.createMask('+7 (999) 999-99-99');
        this.$inputPhone.showMaskOnFocus();
        this.$inputSmsPassword = $("#confirmPassword");
        this.$errorPopup = $("#popupErrorDescription");
        $("input[type=text]").keydown(function ()
        {
            $(this).removeClass("errorInput");
            var $error = $(this).parents('.line').find('.error');
            $error.css('display', 'none');
        });
        this.$linkOnTimer = $("#timer");
    },

    /**
     * Метод перехода на следующий шаг
     */
    nextStep: function ()
    {
        if (this.$buttonNext.active)
        {
            this.$buttonNext.active = false;
            var captcha = $("#captcha");
            if (this.useCaptcha && captcha.val() == '')
            {
                this.showMessage(captcha, this.errorMessage.enterCaptcha);
                return;
            }
            var param;
            switch (this.step)
            {
                //Если текущий шаг первый
                case "step1":
                    var phone = this.$inputPhone.val();
                    if (this.validate(this.adapter(phone)))
                    {
                        this.userPhone = phone;
                        param = "field(phoneNumber)=" + this.adapter(phone) + "&operation=begin" + "&field(request)=" + this.typeClaim;
                        this.send(param);
                    }
                    else
                    {
                        var message = phone != '' ? this.errorMessage.inputAllDigit : this.errorMessage.inputPhoneNumber;
                        this.showMessage(this.$inputPhone, message);
                        this.$buttonNext.active = true;
                    }
                    break;
                //Если текущий шаг второй
                case "step2":
                    var password = this.$inputSmsPassword.val();
                    param = "field(confirmPassword)=" + password + "&operation=next&org.apache.struts.taglib.html.TOKEN=" + this.token;
                    this.send(param);
                    break;
            }
        }
    },

    /**
     * Возвращение на первый шаг для изменения ноера телефона
     */
    changePhone: function ()
    {
        this.transferToStepOne();
    },

    /**
     * Валидация номера телефона
     * @param phone Номер телефона
     * @returns {boolean} Да, если номер корректен. Нет, в противном случае.
     */
    validate: function (phone)
    {
        return new RegExp('7\\d{10}').test(phone);
    },

    /**
     * Метод обработки ответа с сервера
     * @param res Ответ от сервера
     */
    callback: function (res)
    {
        if (res.token != undefined)
        {
            page.token = res.token;
        }
        if (res.authToken != undefined)
        {
            page.authToken = res.authToken;
        }
        if (res.SMSAttemptsEnded != undefined && res.SMSAttemptsEnded === true)
        {
            win.open("SMSAttemptsEnded");
        }
        //Активируем кнопку "Продолжить"
        page.$buttonNext.active = true;
        //Если произошла ошибка, то показываем её и остаёмся на текущем этапе
        if (res.errors != undefined)
        {
            var errors = res.errors;
            page.$errorPopup.html("");
            for (var i = 0; i < errors.length; i++)
            {
                var fieldName = errors[i].field;
                var messageError = errors[i].message;
                if (fieldName === "popupError")
                {
                    page.$errorPopup.html(page.$errorPopup.html() + "<li>" + messageError + "</li>");
                    win.open("popupError");
                }
                else
                {
                    page.showMessage($("#" + fieldName), messageError);
                }
            }
            return;
        }

        page.resetCaptcha(res.captcha);

        if (res.timer != undefined)
        {
            page.setTimer(res.timer);
            page.runTimer();
        }

        if (res.success == true)
        {
            switch (page.step)
            {
                //Если текущий шаг первый
                case "step1":
                    page.transferToStepTwo();
                    break;
                //Если текущий шаг второй
                case "step2":
                    showOrHideAjaxPreloader(true);
                    var redirect = res.redirect;
                    redirect += "?" + "AuthToken=" + page.authToken + "&claimType=" + page.typeClaim;
                    window.location.href = redirect;
                    break;
            }
        }
        else
        {
            switch (page.step)
            {
                case "step2":
                    page.showMessage(page.$inputSmsPassword, page.errorMessage.reWritePassword);
                    break;
            }
        }
    },

    /**
     * Отправка запроса на сервер
     * @param param Параменты запроса
     */
    send: function (param)
    {
        param = this.setCaptcha(param);
        ajaxQuery(param, this.path, this.callback, "json", true, null);
    },

    resetCaptcha: function (value)
    {
        page.useCaptcha = value;
        if (page.useCaptcha)
        {
            page.updateCaptcha();
        }
        page.$blockCaptcha.css("display", page.useCaptcha ? 'block' : 'none');
    },

    /**
     * Установка капчи в параменты запроса, если требуется
     * @param param Параметры запроса
     * @returns {*} Параметры запроса с CAPTCHA, если нужна CAPTCHA. Иначе входные параметры без изменений.
     */
    setCaptcha: function (param)
    {
        if (this.useCaptcha)
        {
            //Перегоняем строку в последовательность кодов символов.
            var captcha = $("#captcha").val();
            captcha = captcha.split('');
            for (var i = 0; i < captcha.length; i++)
            {
                captcha[i] = captcha[i].charCodeAt(0);
            }
            captcha = captcha.join('_');
            param += "&field(captchaCode)=" + captcha;
        }
        return param;
    },

    /**
     * Переход на шаг 1
     */
    transferToStepOne: function ()
    {
        this.step = "step1";
        this.$blockStep1.css("display", "block");
        this.$blockStep2.css("display", "none");
        this.$titleWithPhone.css("display", "none");
        $("#captcha").val("");
        this.$blockCaptcha.css("display", this.useCaptcha ? 'block' : 'none');
        this.$buttonNext.html("Продолжить");
        this.$inputSmsPassword.val('');
        this.showPlaceholder(this.$inputPhone);
        //Cбрасываем все ругательные сообщения у всех input'ов
        $("input[type=text]").each(function ()
        {
            $(this).removeClass("errorInput");
            var $error = $(this).parents('.line').find('.error');
            $error.css('display', 'none');
        });
        this.$inputPhone.val("");
        this.showPlaceholder(this.$inputPhone);
    },

    /**
     * Переход на шаг 2
     */
    transferToStepTwo: function ()
    {
        this.$titlePhone.html(this.userPhone);
        this.step = "step2";
        this.$blockStep1.css("display", "none");
        this.$blockStep2.css("display", "block");
        this.$titleWithPhone.css("display", "block");
        $("#captcha").val("");
        this.$blockCaptcha.css("display", this.useCaptcha ? 'block' : 'none');
        this.$buttonNext.html("Подтвердить");
    },

    /**
     * Отображение сообщения об ошибке
     * @param input Ссылка на input, в котором содержатся некоректные данные
     * @param message Сообщение об ощибке
     */
    showMessage: function (input, message)
    {
        $(input).addClass("errorInput");
        var error = $(input).parents('.line').find('.error');
        error.css('display', 'block');
        error.html(message);
    },

    /**
     * Закрытие сообщения об ощибке
     * @param input  Ссылка на input, в котором произошла ошибка
     */
    closeMessage: function (input)
    {
        $(input).removeClass("errorInput");
        var error = $(input).parents('.line').find('.error');
        error.css('display', 'none');
    },

    /**
     * Обновить картинку капчи
     */
    updateCaptcha: function ()
    {
        $("#idCaptchaImg").attr('src', this.urlCaptcha + "?id=" + Math.round(Math.random() * 1000) + "&color=0");
    },

    /**
     * Функция-адаптер. Предназначена для конвертирования телефона в понятный для сервера формат.
     * @param phone Телефон
     * @returns {string} Конвертированный телефон.
     */
    adapter: function (phone)
    {
        var bufPhone;
        bufPhone = phone.substr(1, 1);
        bufPhone += phone.substr(4, 3);
        bufPhone += phone.substr(9, 3);
        bufPhone += phone.substr(13, 2);
        bufPhone += phone.substr(16, 2);
        return bufPhone;
    },

    setTimer: function (timer)
    {
        this.timer = timer;
    },

    getMaskedPhone: function (phone)
    {
        var bufPhone;
        bufPhone = phone.substr(0, 9);
        bufPhone += "••• ";
        bufPhone += phone.substr(13, 2);
        bufPhone += " ";
        bufPhone += phone.substr(16, 2);
        return bufPhone;
    },

    /**
     * Устанавливает каретку в поле node на позицию pos
     */
    setCaretPosition: function (node, posStart, posEnd)
    {
        try
        {
            // mozilla
            if (node.setSelectionRange)
            {
                node.setSelectionRange(posStart, posEnd != null ? posEnd : posStart);
            }
            // ie
            else if (node.createTextRange)
            {
                var range = node.createTextRange();
                range.collapse(true);
                range.moveEnd('character', posEnd != null ? posEnd : posStart);
                range.moveStart('character', posStart);
                range.select();
            }
        }
        catch (ignore)
        {
        }
    },

    /**
     * Отобразить placeholder у поля $input
     * @param $input Поле, у которого нужно отобразить placeholder
     */
    showPlaceholder: function ($input)
    {
        var placeholder = $input.parents('.line').find('.placeholder');
        placeholder.css("display", "none");
        setTimeout(function ()
        {
            if (!$input.val())
            {
                placeholder.css("display", "block");
            }
        }, 30);
    },

    runTimer: function ()
    {
        page.$linkOnTimer.html(page.timer);
        if (page.secTimer != undefined && page.secTimer != null)
        {
            clearInterval(page.secTimer);
        }
        page.secTimer = setInterval(function ()
        {
            if (page.timer === 0)
            {
                clearInterval(page.secTimer);
            }
            else
            {
                page.$linkOnTimer.html(--page.timer);
            }
        }, 1000)
    }

};

(function ()
{

    $(document).ready(function ()
    {
        $(".crossButtonAction").bind("click", function (event)
        {
            win.close(event.target);
            page.transferToStepOne();
        });
        $("#buttonNext").bind("click", function ()
        {
            page.nextStep();
        });
        $("#updateCaptchaButton").bind("click", function ()
        {
            page.updateCaptcha();
        });
        $("#buttonChangePhone").bind("click", function ()
        {
            page.changePhone();
            page.resetCaptcha();
        });
        $(".field").bind("keydown paste drop focus focusout", function (event)
        {
            var $input = $(event.target);
            page.showPlaceholder($input);
        });

        $("#phoneNumber").bind("focus", function (event)
        {
            var $input = $(event.target);
            var phone = $input.val();
            var pos = 0;
            while (phone[pos] != '_' && pos < phone.length)
            {
                pos++;
            }
            page.setCaretPosition(document.getElementById("phoneNumber"), pos, pos);
        });

        $(".placeholder").bind("click", function (event)
        {
            var placeholder = $(event.target);
            placeholder.css("display", "none");
            var input = placeholder.parents('.line').find('.field');
            input.focus();
        });

        $("#captcha").bind("keyup", function (event)
        {
            var input = $(event.target);
            input.val(input.val().toUpperCase());
        });

        $("#hasNotLoginInSBOL").bind("click", function (event)
        {
            $("#clientHasAccount").hide();
            $("#forAllPeopleBlock").show();
        });

    })
})();