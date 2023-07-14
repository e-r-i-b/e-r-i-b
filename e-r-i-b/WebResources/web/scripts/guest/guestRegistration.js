/**
 * User: usachev
 * Date: 29.01.15
 */
var page = {
    $loginField: null,
    $passwordField: null,
    $confirmPasswordField: null,
    path: null,
    useCaptcha: false,
    urlCaptcha: null,
    $captchaField: null,
    notShowShotRules: true,
    documentId: null,
    documentType: null,
    $errorPopup: null,
    token: null,
    canSend: true,

    init: function ()
    {
        this.$loginField = $("#loginField");
        this.$passwordField = $("#passwordField");
        this.$confirmPasswordField = $("#confirmPasswordField");
        this.$captchaField = $("#captchaCodeField");
        this.path = guestPathRegistration;
        this.useCaptcha = needCaptcha;
        this.urlCaptcha = urlCaptcha;
        this.token = token;
        this.documentId = $.trim($("#documentID").val());
        this.documentType = $.trim($("#documentType").val());
        this.$errorPopup = $("#popupErrorDescription");
        if (this.useCaptcha)
        {
            this.showCaptcha();
        }
        else
        {
            this.closeCaptcha();
        }
    },

    /**
     * ����� ��������� ������ � �������
     * @param res ����� �� �������
     */
    callback: function (res)
    {
        page.token = res.token;
        if (res.success)
        {
            window.location.href = res.redirect;
        }
        else
        {
            var useCaptcha = res.captcha;
            if (useCaptcha != undefined)
            {
                page.useCaptcha = useCaptcha;
            }
            if (page.useCaptcha)
            {
                page.showCaptcha();
            }
            else
            {
                page.closeCaptcha();
            }
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
                    page.showMessage($("input[name=field(" + fieldName + ")]"), messageError);
                }

            }

        }
        page.canSend = true;
    },

    generateRequest: function ()
    {
        var param = "id=" + this.documentId + "&type=" + this.documentType + "&field(login)=" + this.$loginField.val()
                + "&field(password)=" + this.$passwordField.val() + "&field(confirmPassword)=" + this.$confirmPasswordField.val() + "&org.apache.struts.taglib.html.TOKEN=" + this.token;
        return param;
    },

    /**
     * �������� ������� �� ������
     * @param param ��������� �������
     */
    send: function ()
    {
        if (this.canSend)
        {
            this.canSend = false;
            if (this.isEmpty(this.$loginField.val()))
            {
                this.showMessage(this.$loginField, "����� �� ������ ���� ������");
                this.canSend = true;
                return;
            }
            if (this.isEmpty(this.$passwordField.val()))
            {
                this.showMessage(this.$passwordField, "������ �� ������ ���� ������");
                this.canSend = true;
                return;
            }
            if (this.checkCoincidence(this.$loginField.val(), this.$passwordField.val()))
            {
                this.showMessage(this.$loginField, "����� � ������ �� ������ ���������");
                this.canSend = true;
                return;
            }
            if (!this.checkCoincidence(this.$passwordField.val(), this.$confirmPasswordField.val()))
            {
                this.showMessage(this.$confirmPasswordField, "�� ��������� ������");
                this.canSend = true;
                return;
            }
            //���������� ��� ����������� ��������� � ���� input'��
            $("input").each(function ()
            {
                page.closeMessage($(this));
            });
            var param = this.generateRequest();
            param = this.setCaptcha(param);
            ajaxQuery(param, this.path, this.callback, "json", true, null);
        }
    },

    /**
     * ����������� ��������� �� ������
     * @param input ������ �� input, � ������� ���������� ����������� ������
     * @param message ��������� �� ������
     */
    showMessage: function (input, message)
    {
        $(input).addClass("errorInput");
        var error = $(input).parents('.pairFieldError').find('.error');
        error.css('display', 'block');
        error.html(message);
    },

    /**
     * �������� ��������� �� ������
     * @param input  ������ �� input, � ������� ��������� ������
     */
    closeMessage: function (input)
    {
        $(input).removeClass("errorInput");
        var error = $(input).parents('.pairFieldError').find('.error');
        error.css('display', 'none');
    },

    /**
     * ������� �������� �� ����������
     * @param string1 �������� 1
     * @param string2 �������� 2
     * @returns {boolean} ��, ���� �������� ���������. ��� � ��������� ������
     */
    checkCoincidence: function (string1, string2)
    {
        return string1 === string2;
    },

    /**
     * ��������� ����� � ��������� �������, ���� ���������
     * @param param ��������� �������
     * @returns {*} ��������� ������� � CAPTCHA, ���� ����� CAPTCHA. ����� ������� ��������� ��� ���������.
     */
    setCaptcha: function (param)
    {
        if (this.useCaptcha)
        {
            //���������� ������ � ������������������ ����� ��������.
            var captcha = $("#captchaCodeField").val();
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
     * �������� �������� �����
     */
    updateCaptcha: function ()
    {
        $("#captchaCodeField").val("");
        $("#idCaptchaImg").attr('src', this.urlCaptcha + "?id=" + Math.round(Math.random() * 1000) + "&color=0&bgcolor=0");
    },

    /**
     * �������� �� �������
     * @param string ������
     * @returns {boolean} ��, ���� �����. ��� � ��������� ������
     */
    isEmpty: function (string)
    {
        return string.length === 0;
    },

    showCaptcha: function ()
    {
        var banner = $(".bannerRegistration");
        banner.removeClass("bannerRegistration");
        banner.addClass("bannerRegistrationExtended");
        $("#blockCaptcha").css("display", "block");
        this.updateCaptcha();

    },

    closeCaptcha: function ()
    {
        var banner = $(".bannerRegistrationExtended");
        banner.removeClass("bannerRegistrationExtended");
        banner.addClass("bannerRegistration");
        $("#blockCaptcha").css("display", "none");
    }
}

{
    $(document).ready(function ()
    {
        customPlaceholder.init();
        page.init();
        $("#buttonRegistrationNewClient").bind("click", function ()
        {
            page.send();
        });
        $("#captchaCodeField").bind("keyup", function (event)
        {
            var input = $(event.target);
            input.val(input.val().toUpperCase());
        });
        var $inputTextField = $(".inputTextField");
        $inputTextField.bind("focus", function ()
        {
            if (page.notShowShotRules)
            {
                $("#IdItemUL").hide(1000);
                $("#Id-b-reg-helper").show(1000);
                page.notShowShotRules = false;
            }
        });

        $inputTextField.bind("keydown paste drop focus focusout", function (event)
        {
            var $input = $(event.target);
            var placeholder = $input.parents('.pairFieldError').find('.placeholderRegistration');
            placeholder.css("display", "none");
            setTimeout(function ()
            {
                if (!$input.val())
                {
                    placeholder.css("display", "block");
                }
            }, 30);
        });

        $inputTextField.keydown(function ()
        {
            $(this).removeClass("errorInput");
            var $error = $(this).parents('.pairFieldError').find('.error');
            $error.css('display', 'none');
        });

        $("#buttonFullRules").bind("click", function ()
        {
            win.open("fullRules");
        });

        $(".crossButton").bind("click", function (event)
        {
            win.close(event.target);
        });

        $(".placeholderRegistration").bind("click", function (event)
        {
            var $placeholder = $(event.target);
            var $input = $placeholder.parents('.pairFieldError').find('.inputTextField');
            $input.focus();
        });

        $("#updateCaptchaButtonRegistration").bind("click", function ()
        {
            page.updateCaptcha();
        });

    })
}