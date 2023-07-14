function getWorkSpace()
{
    return $(".g-wrapper").get(0);
}

var isClientApp = true;

//Методы для справочника регионов
/**
 * метод обработки результата ajax запроса справочника регионов
 * @param msg - ajax данные
 */
function regionAjaxResult(msg)
{
    if (trim(msg) == 'OK')
    {
        win.close('regionsDiv');
        document.getElementById('regionNameSpan').innerHTML = clickRegion.currentRegionName;
        if (clickRegion.currentRegionName == 'Все регионы')
            $('.regionsAlphabetList .currentRegionName span').text("регион не выбран");
        else
            $('.regionsAlphabetList .currentRegionName span').text(clickRegion.currentRegionName);
    }
    else if(trim(msg) == '')
        location.reload();
    else
        document.getElementById("regionsDiv").innerHTML = msg;
}

var REGION_DICTIONARY_URL = ''; // устанавливается позже в header

var clickRegion = {
    lock: false,
    currentRegionName: null,
    //клик по региону
    click: function (id, name)
    {
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.currentRegionName = name;
        regionClick(myself, id, name);
    },
    //выбор региона
    choose: function(id, name)
    {
        if (this.lock) return;
        this.lock = true;
        var myself = this;
        this.currentRegionName = name;
        selectRegion(myself, id, name);
    }
};

/**
 * Функция для справочника регионов, отвечает за клик по региону
 * @param id ИД региона
 */
function regionClick(myself, id)
{
    var params = '';
    if (id > 0)
        params = 'id=' + id;

    ajaxQuery (params, REGION_DICTIONARY_URL, function(data){regionAjaxResult(data); myself.lock = false;});
}

/**
 * Функция для справочника регионов, которая отвечает за выбор региона
 * @param id региона
 */
function selectRegion(myself, id)
{
    //Флаг select однозначно говорит, что узнанный id необходимо выбрать
    var params = 'select=true';
    params = params + '&id=' + id;
    ajaxQuery (params, REGION_DICTIONARY_URL, function(data){regionAjaxResult(data); myself.lock = false;});
}

var utils = {
    forms: {},
    timers: {},
    popup: null,
    showPopup: function(id, duration, ready, closable){
        var t = this, popup;
        function show(){
            t.overlay = t.overlay || $('#Overlay');
            popup = t.overlay.find('#'+id);
            if(!popup) return false;
            utils.popup = id;
            popup.css({'visibility': 'visible'});
            if (this.forms != undefined)
                $($(this.forms[0]).find(":input").filter(":visible").get(0)).blur();
            if(closable != undefined)
                popup.attr('onclick', 'return{closable:'+ closable +'}');
            t.overlay.fadeIn(duration != undefined ? duration : 150);
            if (id == "Complete") {
                var inputs = document.getElementsByTagName("INPUT");
                for (var i = 0; i < inputs.length; i++) {
                    inputs[i].disabled = true;
                }
            }
        }
        ready ? $(show) : show();
    },
    showCaptcha: function(){
        var t = this;
        $(function(){
            $('#Captcha').show();
            $('#Submit').addClass('afterCaptchaBlock');
        });
    },
    initTimer: function(timers){
        var t = this;
        t.timers = timers;
    },
    initErrors: function(form, errors){
        var t = this;
        t.forms[form] = errors;
    },
    showMessage: function(id){ /* убил 100 котят пушечным залпом */
        $(function(){
            $('#'+id).show();
        });
    },
    errorMsg: {
        required: 'Поле обязательно для заполнения',
        luhn: 'Неправильный номер карты. Проверьте номер.',
        decimal: 'Вводить нужно только цифры',
        login: 'Введены недопустимые символы',
        password: 'Введены недопустимые символы',
        minlength: 'Минимум {n} символов',
        maxlength: 'Максимум {n} символов',
        nearbyKeys: 'Не должно идти подряд 3 символа расположенных на клавиатуре',
        decAndLetter: 'Должен содержать минимум 1 цифру и одну букву',
        sameSymbols: 'Не должен содержать более 3-х одинаковых символов подряд',
        compare: 'Пароли не совпадают'
    },
    validator: {
        required: function(value){
            if($.trim(value).length) return false;
            return utils.errorMsg.required;
        },
        luhn: function(value){
            var a = value.split(''), total = 0;
            if(a.length < 15) return utils.errorMsg.luhn;
            a.push('null');
            a.reverse();
            for(var i = 1; i < a.length; i++){
                n = parseInt(a[i],10);

                n = (i%2) ? n : (n*2 > 9) ? n*2 - 9 : n*2;
                total += n;
            }
            if( !(total%10) ) return false;
            return utils.errorMsg.luhn;
        },
        decimal: function(value){
            var rg = /^\d+$/g;
            if( rg.test(value) ) return false;
            return utils.errorMsg.decimal;
        },
        login: function(value){
            var rg = /^([\w@\-\.]*)$/g;
            if( rg.test(value) ) return false;
            return utils.errorMsg.login;
        },
        password: function(value){
            var rg = /^([\w@\-\.!#$%^&*()+:;,]*)$/g;
            if( rg.test(value) ) return false;
            return utils.errorMsg.password;
        },
        minlength: function(value, n){
            if( value.length >= n ) return false;
            return utils.errorMsg.minlength.replace('{n}', n);
        },
        maxlength: function(value, n){
            if( value.length <= n ) return false;
            return utils.errorMsg.maxlength.replace('{n}', n);
        },
        nearbyKeys: function(){
            var testStr = '1234567890|qwertyuiop|asdfghjkl;|zxcvbnm,.|!@#$%^&*()_+';

            testStr += testStr + '|' + testStr.split('').reverse().join('');

            return function(value){
                if(!value || value.length < 3) return false;

                value = value.split('');

                for(var i = 0; i < value.length-2; i++){
                    rg = new RegExp(value[i]+value[i+1]+value[i+2],'g');
                    if(rg.test(testStr)) return utils.errorMsg.nearbyKeys;
                }
                return false;
            }
        }(),
        decAndLetter: function(value){
            var rg = /[\d]+[A-Za-z]+|[A-Za-z]+[\d]+/g;
            if(!rg.test(value)) return utils.errorMsg.decAndLetter;
            return false;
        },
        sameSymbols: function(value){
            var rg = /(.)(\1){3,}/g;
            if(rg.test(value)) return utils.errorMsg.sameSymbols;
            return false;
        }
    }
};

/*******************************************/
/* APPLICATION */
/*******************************************/
var Appl = function(params){
    var t = this;

    if($('#Overlay').length){
        t.overlay = new t.Overlay({el: $('#Overlay'), parent: t});
    }
    if($('.actions_timer').length){
        t.smsTimer = new t.SmsTimer({el: $('.actions_timer'), parent: t});
    }
    if($('.form').length){
        t.forms = [];
        $('.form').each(function(){ t.forms.push(new t.Form({el: $(this), parent: t}) ) });
    }
    if($('ul.toggle').length){
        t.spoilers = [];
        $('ul.toggle').each(function(){ t.spoilers.push(new t.ToggleList({el: $(this), parent: t})) });
    }
    if($('.b-btn').length && document.body.style.maxHeight != undefined){
        t.buttons = [];
        $('.b-btn').each(function(){ t.buttons.push(new t.Button({el: $(this), parent: t})) });
    }

    if(document.querySelector && !document.addEventListener){
        $('html').addClass('ie8');
    }
};

/*******************************************/
/* APPLICATION.FORM */
/*******************************************/
Appl.prototype.Form = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.id = t.el[0].id;

    t.errors = false;
    t.valid = false;
    t.items = [];

    t.el.find('.field').each(function(){ t.items.push(new t.Field({el: $(this), parent: t})) });
    t.el.keypress(function(e){
        var code = e.keyCode || e.which;
        if(code == 13)
        {
            preventDefault(e);
            $(".b-btn.next_btn").find("input[type='submit']").click();
            return false;
        }
    });
    t.el.submit(function(){ return t.submit() });

    t.init();
};

Appl.prototype.Form.prototype = {
    submit: function(){
        var t = this;
        if(!t.validation()) return false;
        for(var i = 0; i < t.items.length; i++){
            t.items[i].input.val(t.items[i].value);
        }
        return true;
    },
    validation: function(){
        var t = this;
        for(var i = 0; i < t.items.length; i++){
            if(!t.items[i].validate()) return false;
        }
        return true;
    },
    init: function(){
        var t = this;

        t.items[0].input.focus();
        if(!utils.forms[t.id]) return;
        for(var i = 0; i < t.items.length; i++){
            for(var k in utils.forms[t.id]){
                var fieldName = t.items[i].name;
                if(k == fieldName || ("field(" + k + ")" == fieldName)) t.items[i].initExeption(utils.forms[t.id][k]);
            }
        }
    }
};

/*******************************************/
/* APPLICATION.FORM.FIELD */
/*******************************************/
Appl.prototype.Form.prototype.Field = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.input = t.el.find('input');

    $.extend(t, t.getParams(), true);

    t.name = t.input.attr('name');
    t.valid = true;
    t.error = false;
    t.exeption = {
        value: null,
        text: null
    };

    t.value = t.input.val().toLowerCase();
    t.el.click(function(){t.input.focus();});
    t.input.focus(function(){ t.el.addClass('focus') });
    t.input.blur(function(){ t.el.removeClass('focus'); });
    t.input.change(function(e){ t.onKeyUp(e, this.value) });
    t.input.keyup(function(e){ t.onKeyUp(e, this.value) });
};

Appl.prototype.Form.prototype.Field.prototype = {
    init: function(){
        var t = this;
    },
    getParams: function(){
        var t = this, params = {};
        if(t.el[0].onclick){
            params = t.el[0].onclick();
            t.el.removeAttr('onclick');
        }
        return params || {};
    },
    onKeyUp: function(e, value){
        var t = this;
        if(t.type == 'card') t.cardKeyUp(e, value);
        else if(t.type == 'sms') t.smsKeyUp(e, value);
        else t.keyUp(e, value);
    },
    keyUp: function(e, value){
        var t = this, k = e.which;
        if(k == 13 || k == 37 || k == 39 || k == 17 || (k == 65 && e.ctrlKey)) return;
        t.removeError();

        t.value = value.toLowerCase();
        if(t.error) return;
    },
    cardKeyUp: function(e, value){
        var t = this, error, k = e.which;

        if(k == 13 || k == 37 || k == 39 || k == 17 || (k == 65 && e.ctrlKey)) return;

        t.value = value.replace(/[^\d]/g, '');
        t.input.val(t.formatCard(t.value));

        if(t.value.length < 15){
            t.el.removeClass('valid');
            t.removeError();
            return;
        }

        error = utils.validator['luhn'](t.value);
        if(error) return t.addError(error);
        else t.removeError();
        t.el.addClass('valid');
    },
    smsKeyUp: function(e, value){
        var t = this, error, k = e.which;
        if(k == 13 || k == 37 || k == 39 || k == 17 || e.ctrlKey) return;

        t.removeError();
        t.value = value.replace(/[^\d]/g, '');
        t.input.val(t.value);
    },
    checkMinLength: function(n){
        var t = this;
        if(t.value.length && t.value.length < n)
            t.addError(utils.errorMsg.minlength.replace('{n}', n));
    },
    checkMaxLength: function(n){
        var t = this;
        if(t.value.length && t.value.length > n)
            t.addError(utils.errorMsg.maxlength.replace('{n}', n));
    },
    checkCompare: function(name){
        var t = this;
        for(var i = 0; i < t.parent.items.length; i++){
            if(t.parent.items[i] !== t && t.parent.items[i].name === name && t.parent.items[i].value === t.value) return;
        }
        t.addError(utils.errorMsg.compare);
    },
    formatCard: function(value){
        var t = this;
        if(value.length < 5) return value;

        value = value.split('');
        for(var i = 3; i < value.length; i+=4){
            if(value[i] && i < 12 && i < value.length - 1) value[i] += ' ';
        }
        return value.join('');
    },
    validate: function(){
        var t = this, value = value || t.value, error;
        if(!t.validMap || !t.validMap.length) return true;
        for(var i = 0; i < t.validMap.length; i++){
            error = utils.validator[t.validMap[i]](value);
            if(error){
                t.addError(error);
                t.input.focus();
                return;
            }
        }
        t.removeError();
        if(t.minlength) t.checkMinLength(t.minlength);
        if(t.maxlength) t.checkMaxLength(t.maxlength);
        if(t.compare) t.checkCompare(t.compare);
        if(t.exeption.value) t.checkExeption(value);
        return !t.error;
    },
    addError: function(text){
        var t = this;
        if(t.error) t.removeError();
        t.error = true;
        t.el.removeClass('valid');
        t.el.addClass('error');
        t.pop = $('<div style="display:none" class="b-msg">'+text+'<i class="msg_arr"></i></div>').appendTo(t.el);
        t.pop.show();
        return false;
    },
    removeError: function(){
        var t = this;
        if(!t.error) return true;
        t.error = false;
        t.pop.remove();
        t.el.removeClass('error');
        return true;
    },
    initExeption: function(error){
        var t = this;
        if(error.value){
            t.value = error.value;
            t.exeption.value = error.value;
            t.input.val(t.exeption.value);
        }
        if(error.text){
            t.exeption.text = error.text;
            t.addError(t.exeption.text);
        }
    },
    checkExeption: function(value){
        var t = this;
        if(value == t.exeption.value) return t.addError(t.exeption.text || 'Ошибка! Неверное значение');
    }
};

/*******************************************/
/* APPLICATION.SMS-TIMER */
/*******************************************/
Appl.prototype.SmsTimer = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.timer = t.el.find('.time');
    t.interval;

    if(t.el[0].onclick){
        t.seconds = t.el[0].onclick().time;
        t.el.removeAttr('onclick');
    }

    t.timerEnd = function(){
        clearInterval(t.interval);
        return false;
    };

    t.createTime = function(seconds){
        var min = Math.floor(seconds/60);
        var sec = seconds - (min*60);

        return min + ':' + (sec.toString().length == 1 ? '0' + sec : sec);
    };

    t.initTimer = function(seconds){
        if(seconds > 120) t.el.css('visibility','hidden');
        t.timer.text(t.createTime(seconds--));

        t.interval = setInterval(function(){

            if(seconds <= 120) t.el.css('visibility','visible');
            if(seconds < 0) return t.timerEnd();
            t.timer.text(t.createTime(seconds--));

        }, 1000);
    };

    if(t.seconds) t.initTimer(t.seconds);
};


/*******************************************/
/* APPLICATION.TOGGLE-LIST */
/*******************************************/
Appl.prototype.ToggleList = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.eText = t.el.find('.dot');

    t.eText.click(function(){ t.toggle($(this));});
};

Appl.prototype.ToggleList.prototype = {
    toggle: function(el){
        var t = this, parent = el.parent();

        if( parent.hasClass('expanded') ){
            parent.removeClass('expanded');
            el.next().slideUp(150);
        } else {
            parent.addClass('expanded');
            el.next().slideDown(150);
        }
    }
};


/*******************************************/
/* APPLICATION.OVERLAY */
/*******************************************/
Appl.prototype.Overlay = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.el.html('<i class="overlay_bg"></i><div class="overlay_inner">'+t.el.html()+'</div>');

    t.inner = t.el.find('.overlay_inner');

    t.items = [];
    $('.b-popup').each(function(){ t.items.push(new t.Popup({el: $(this), parent: t})) });

    t.el.on('click', function(e){ if(e.target === t.inner[0]) t.hide() });
};

Appl.prototype.Overlay.prototype = {
    show: function(id, duration){
        var t = this;

        t.current = t.get(id);
        if(!t.current.length) return t.current = false;
        t.current.show();
    },
    hide: function(duration){
        var t = this,
                item = t.get(utils.popup);

        if(!item) return false;
        item.close();
    },
    get: function(id){
        var t = this;
        for(var i = 0; i < t.items.length; i++){
            if(id === t.items[i].id) return t.items[i];
        }
        return null;
    }
};

/*******************************************/
/* APPLICATION.OVERLAY.POPUP */
/*******************************************/
Appl.prototype.Overlay.prototype.Popup = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.params = {};
    t.beforeEnd = null;

    t.id = t.el.attr('id');
    t.eClose = t.el.find('.popup_close');

    t.eClose.click(function(){ t.close() });

    t.getParams();
};

Appl.prototype.Overlay.prototype.Popup.prototype = {
    getParams: function(){
        var t = this;
        if(t.el[0].onclick){
            t.params = t.el[0].onclick();
            t.el.removeAttr('onclick');
        }
        if(t.eClose[0] && t.eClose[0].onclick){
            t.beforeEnd = t.eClose[0].onclick.apply(t,[]);
            t.eClose.removeAttr('onclick');
        }
    },
    show: function(duration){
        var t = this;

        utils.popup = t.id;
        t.parent.el.fadeIn(duration || 150);
        t.el.css({'visibility': 'visible'});
    },
    close: function(duration){
        var t = this, end;

        if(t.beforeEnd && typeof t.beforeEnd == 'function') end = t.beforeEnd(t);
        if(t.params.closable === false || end === false) return false;

        t.parent.el.fadeOut(duration || 150, function(){
            utils.popup = '';
            t.el.css('visibility', 'hidden');
            t.parent.parent.forms[0].init();
        });
    }
};

/*******************************************/
/* APPLICATION.BUTTONS */
/*******************************************/
Appl.prototype.Button = function(params){
    var t = this;
    for(var k in params){ if(params.hasOwnProperty(k)) t[k] = params[k] }

    t.btn = t.el.find('input');

    t.press = function(e){
        if(e.which != 13) return;
        t.el.addClass('active');
        setTimeout(function(){
            if($(this).not(':focus')) t.el.removeClass('active')
        }, 200)
    };

    t.btn.focus(function(){ t.el.addClass('focus') });
    t.btn.blur(function(){ t.el.removeClass('focus') });
    t.btn.keydown(function(e){ t.press(e) });
};

/*******************************************/
/* DOM.READY */
/*******************************************/
$(function(){

    new Appl({});

});