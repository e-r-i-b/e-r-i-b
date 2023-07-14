(function(){

    var app,

    /* Текста всех ошибок валидатора */
            ERRORS = {
                required: 'Поле обязательно для&nbsp;заполнения',
                minLen: 'Минимум {n} символов',
                maxLen: 'Максимум {n} символов',
                email: 'Неправильный адрес электронной почты',
                captcha: 'Введенный код не&nbsp;совпадает с&nbsp;кодом на&nbsp;картинке',
                card: 'Вы неправильно указали номер карты. Пожалуйста, проверьте количество цифр введенного номера карты и&nbsp;их последовательность.',
                sms_required: 'Введите цифровой пятизначный SMS-пароль',
                card_required: 'Необходимо ввести номер вашей карты Сбербанка',
                login: 'Недопустимые символы. Проверьте раскладку клавиатуры',
                login_letter: 'Логин должен содержать хотя бы одну букву',
                login_number: 'Логин должен содержать хотя бы одну цифру',
                login_let3: 'Логин не должен содержать более 3-х одинаковых символов подряд',
                login_key3: 'Логин не должен содержать более 3-х символов, расположенных в&nbsp;одном ряду клавиатуры',
                login_maxLen: 'Слишком длинный логин. Максимально {n} символов',
                login_minLen: 'Слишком короткий логин. Минимальная длина {n} символов',
                login_avail: 'Указанный логин занят. Пользователь с&nbsp;таким логином уже зарегистрирован в&nbsp;системе. Попробуйте использовать другой.',
                password: 'Недопустимые символы. Проверьте раскладку клавиатуры',
                password_letter: 'Пароль должен содержать хотя бы одну букву',
                password_number: 'Пароль должен содержать хотя бы одну цифру',
                password_let3: 'Пароль не должен содержать более 3-х одинаковых символов подряд',
                password_key3: 'Пароль не должен содержать более 3-х символов, расположенных в&nbsp;одном ряду клавиатуры',
                password_maxLen: 'Слишком длинный пароль. Максимально {n} символов',
                password_minLen: 'Слишком короткий пароль. Минимальная длина {n} символов',
                password_differ: 'Пароль не должен совпадать с&nbsp;логином',
                password_compare: 'Введенные пароли не&nbsp;совпадают',
                password_avail: 'Введенный пароль совпадает с&nbsp;предыдущим. Пожалуйста, укажите другой пароль',
                timer_get_sms: 'Срок действия пароля истек, получите новый пароль, нажав на ссылку &quot;Выслать новый SMS-пароль&quot;',
                timer_get_reg: 'Срок действия пароля истек, пройдите регистрацию заново',
                timer_get_recover: 'Срок действия пароля истёк, начните процесс восстановления пароля заново',
                timer_default: 'Срок действия пароля истёк, начните процесс заново'
            },

    /* Объект с хелперами */
            utils = {

                /* Извлекаем объект с параметрами из onclick атрибута элемента виджета */
                getParams: function (node){
                    var params = {};

                    if(node && node.onclick){
                        params = node.onclick();
                        node.removeAttribute('onclick');
                    }

                    return params;
                },

                /* а-ля конструктор виджетов.
                 @name -> [String] Имя конструктора объекта
                 @parent -> [Object] содержащий конструктор
                 @params -> [Object] с параметрами  */
                createWidget: function(name, parent, params){
                    var arr = [], els = params.el;

                    /* Обязательное наличие jq элемента в params.el */
                    if(!els || !els.length) return [];

                    els.each(function(i){
                        params.el = $(this);
                        params.w_type = name;
                        arr.push(new parent[name](params));
                    });

                    return arr;
                },

                loadImages: function(srcArr, callback){
                    var len = srcArr.length,
                            complete = 0, image;

                    for(var i = 0; i < len; i++){
                        image = new Image();
                        image.onload = image.onerror = function(){
                            complete++;
                            if(complete === len && $.isFunction(callback)) callback();
                        };
                        image.src = srcArr[i];
                    }
                },

                /* Высчитываем ширину скроллбара браузера */
                getBarWidth: function(){
                    var el1 = $('<div style="width:100px;height:10px"></div>'),
                            el2 = $('<div style="position:absolute;overflow-y:scroll;height:5px"></div>'),
                            barWidth = el2.append(el1).appendTo('body').width() - 100;
                    el2.remove();
                    return barWidth;
                },

                testIeVersion: function(){
                    var d = document, w = window;
                    return !d.all ? false : w.atob ? 10 : d.addEventListener ? 9 : d.querySelector ? 8 : w.XMLHttpRequest ? 7 : d.compatMode ? 6 : 'older';
                },

                /* Проверяем поддержку CSS3 + получаем вендорное свойство:
                 в Хроме проверяем 'transform' получаем -> '-webkit-transform'
                 в браузерах не поддерщивающих, возвращается пустая строка.
                 результат кешируется */
                getCSS3: (function (){
                    var styles = document.documentElement.style,
                            prfxs = ['ms', 'o', 'Moz', 'webkit'],
                            rgxFunc = function(str){ return (str.charAt(1) || str.charAt(0)).toUpperCase() },
                            supported = {};

                    return function(prop){
                        return prop in supported ? supported[prop] : (prop in styles ? prop : function(l, prp){
                            prp = prp.replace(/(-.)|(^.)/g, rgxFunc);
                            while(l--){
                                if(!(prfxs[l] + prp in styles)) continue;
                                supported[prop] = ('-'+ prfxs[l] +'-'+ prop).toLowerCase();
                                return supported[prop];
                            }
                            return supported[prop] = '';
                        }(prfxs.length, prop));
                    }
                }())
            };


    /* APPLICATION: конструктор объекта приложения */
    var App = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        app = this;
        app.params = utils.getParams(app.el[0]);
        app.ie = utils.testIeVersion();
        app.step = 0;
        app.barWidth = utils.getBarWidth();

        app.token = app.params.token || null;
        app.pageToken = $('input[name = PAGE_TOKEN]').val() || null;
        app.pageType = $('input[name = page_type]').val() || null;

        app.slider = utils.createWidget('Slider', app, {el: $('.b-slider')})[0];
        app.forms = app.slider ? [] : utils.createWidget('Form', app, {el: $('.b-form')});
        app.promo = utils.createWidget('Promo', app, {el: $('.b-promo')})[0];
        app.overlay = utils.createWidget('Overlay', app, {el: $('.g-overlay')})[0];
        app.background = utils.createWidget('Background', app, {el: $('.b-svg')})[0];
        app.accordion = utils.createWidget('Accordion', app, {el: $('.b-accordion')});
        app.actions = utils.createWidget('Action', app, {el: $('.b-action')});
        app.timer = utils.createWidget('Timer', app, {el: $('.b-timer')})[0];
        app.regMessage = utils.createWidget('RegisteredMessage', app, {el: $('#IsRegisteredUser')})[0];

        app.showError = function(errorData){
            if(!errorData || !app.background || !app.slider) return;

            app.errorVisible = true;
            app.background.showErrorBg(1000);
            app.slider.current.showError(errorData, 700);
            app.slider.current.form.fields[0].addError({type: 'sms', popup: false, delay: 100});
        };

        app.showRegisterNote = function(show){
            if(!app.regMessage) return;

            if (app.errorVisible)
            {
                app.slider.current.hideError(700);
                app.background.hideErrorBg(700, function(){
                    if(show) app.regMessage.show();
                    else app.regMessage.hide();
                });
            }
            else
            {
                if(show) app.regMessage.show();
                else app.regMessage.hide();
            }
        };

        app.showPopup = function(popupData){
            if(!app.overlay) return;

            if(!popupData && app.overlay.visible)  app.overlay.hide();
            else app.overlay.show(popupData);
        };

        app.parseXHR = function(target, data){
            for(var k in data) {
                if (!data.hasOwnProperty(k)) continue;

                switch(k) {
                    case 'redirect':
                        location.href = encodeURI(data.redirect);
                        return;
                    case 'state':
                        if(data.state === 'success'  && app.slider){
                            if('goToStep' in data){
                                if($.isNumeric(+data.goToStep)) app.slider.switchPage(+data.goToStep, 1000);
                            } else app.slider.nextPage(1000);
                        }
                        break;

                    case 'token':
                        app.token = data.token;
                        break;

                    case 'captcha':
                        if(data.captcha) (app.forms[0] || app.slider.current.form).captcha.show();
                        else (app.forms[0] || app.slider.current.form).captcha.hide();
                        break;

                    case 'showError':
                        app.showError(data.showError);
                        break;

                    case 'errors':
                        (app.forms[0] || app.slider.current.form).setErrors(data.errors);
                        break;

                    case 'timer':
                        if(app.timer && $.isNumeric(+data.timer)) app.timer.set(data.timer);
                        break;

                    case 'isRegistered':
                        app.showRegisterNote(data.isRegistered);
                        break;

                    case 'showPopup':
                        app.showPopup(data.showPopup);
                        break;
                }
            }
        };
    };

    /* APPLICATION.REG-MESSAGE */
    App.prototype.RegisteredMessage = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                $slider = $('.b-slider'),
                $steps = $('.b-steps'),
                $expander = $('.b-promo-expander'),
                duration = 400;

        t.show = function(callback){
            t.shown = true;
            $steps.fadeOut(duration / 2);
            $expander.fadeOut(duration / 2);
            $slider.fadeOut(duration / 2, function(){
                t.el.fadeIn(duration);
                if($.isFunction(callback)) callback();
            });
        };

        t.hide = function(callback){
            t.shown = false;
            t.el.fadeOut(duration, function(){
                $slider.fadeIn(duration / 2);
                $steps.fadeIn(duration / 2);
                $expander.fadeIn(duration / 2);
                if($.isFunction(callback)) callback();
            });
        };
    };


    /* APPLICATION.PROMO */
    App.prototype.Promo = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this;

        var imageDim = 2.012,
                elExpander = $('.b-promo-expander');

        t.el.show();
        t.elPromoImage = t.el.find('.promo_pic');
        t.visible = new RegExp("external").test(location.href) || t.el.height();

        t.el.click(function(){ return t.hide(400) });
        elExpander.click(function(){ return t.show(400) });
        app.el.on('slider.switch.start', function(){ elExpander.hide(); });

        t.hide = function(duration, permanent, callback){
            if(permanent) elExpander.hide();

            disableScroll();
            t.el.animate({height: 0, opacity: 0}, duration, function(){
                if(!permanent){
                    elExpander.show();
                    (app.forms[0] || app.slider.current.form).setFocus();
                }
                t.visible = false;
                enableScroll();
                if($.isFunction(callback)) callback();
            });

            $('body, html').animate({scrollTop: 0}, duration);
            return false;
        };

        t.show = function(duration){
            var currentHeight = t.elPromoImage.width() / imageDim;

            if(!duration){
                elExpander.hide();
                t.el.css('height','auto').fadeTo(0, 1);
                t.visible = true;
                return;
            }

            disableScroll();
            elExpander.hide();

            t.el.animate({height: currentHeight, opacity: 1}, duration, function(){
                t.visible = true;
                t.el.css('height','auto');
                enableScroll();
            });
        };

        utils.loadImages([t.elPromoImage[0].src], function(){
            imageDim = t.elPromoImage.width() / t.elPromoImage.outerHeight(true);
        });

        if(t.visible) t.show();
        else elExpander.show();

        function disableScroll(){
            document.onmousewheel = function(){ return false };
        }

        function enableScroll(){
            document.onmousewheel = function(){};
        }
    };


    /* APPLICATION.SLIDER */
    App.prototype.Slider = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                config = utils.getParams(t.el[0]),
                currentIndex = 0,
                $inner = t.el.find('.slider_inner');

        t.elInner = $inner;

        t.current = null;
        t.steps = utils.createWidget('Steps', t, {el: $('.b-steps'), parent: t})[0];
        t.pages = utils.createWidget('Page', t, {el: t.el.find('.slider_page'), parent: t});

        t.switchPage = function(index, duration){
            if(t.current === t.pages[index]) return;

            app.el.trigger('slider.switch.start');

            if(app.regMessage && app.regMessage.shown){
                app.regMessage.hide();
                doSwitch(index, 0);
                return;
            }

            if(app.promo && app.promo.visible){
                app.promo.hide(450, true, function(){ doSwitch(index, duration) });
                return;
            }

            if(app.background && app.background.visible){
                t.current.hideError(700);
                app.background.hideErrorBg(700, function(){ doSwitch(index, duration) });
                return;
            }

            doSwitch(index, duration);
        };

        t.nextPage = function(duration){
            if(currentIndex + 1 >= t.pages.length) return;
            t.switchPage(currentIndex + 1, duration);
        };

        function doSwitch(index, duration){
            t.steps.switchStep(index, duration);

            currentIndex = index;
            t.current = t.pages[currentIndex];

            for(var i = 0; i < t.pages.length; i++){
                if(t.pages[i] !== t.current) t.pages[i].deactivate(duration);
                else t.pages[i].activate(duration);
            }

            if(utils.getCSS3('transition')){
                $inner.css(utils.getCSS3('transition'), 'left '+ (duration) +'ms, height '+ (duration) +'ms');
                $inner.css({left: -(100 * index) +'%', height: t.current.height});
            } else {
                $inner.animate({left: -(100 * index) +'%', height: t.current.height}, duration);
            }
        }

        t.switchPage(config.start || currentIndex, 0);
    };


    /* APPLICATION.Steps */
    App.prototype.Slider.prototype.Steps = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                $step = t.el.find('.steps_item'),
                $mark = t.el.find('.steps_mark'),
                $oval = t.el.find('.steps_oval'),
                $num = $mark.find('.steps_num');

        t.curIndex = 0;

        t.switchStep = function(index, duration){
            t.curIndex = index;
            t.duration = duration;

            $step.removeClass('current');
            $num.fadeOut(duration * 0.2);
            $oval.animate({top: 10}, duration * 0.3, function(){
                t.duration = duration;
                if(utils.getCSS3('transition')){
                    $mark.css(utils.getCSS3('transition'), 'left '+ (t.duration * 0.6) +'ms');
                    $mark.css({left: (33.3 * index + '%')});
                    setTimeout(doAfterAnim, t.duration * 0.6);
                } else {
                    $mark.animate({left: (33.3 * index + '%')}, t.duration * 0.6, doAfterAnim);
                }
            });
        };

        function doAfterAnim(){
            $oval.animate({top: 0}, t.duration * 0.3);
            $num.text(t.curIndex + 1).fadeIn(t.duration * 0.5);
            $step.eq(t.curIndex).addClass('current');
        }

        t.switchStep(t.curIndex, 0);
    };


    /* APPLICATION.SLIDER.PAGE */
    App.prototype.Slider.prototype.Page = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this;

        t.height = t.el.height();
        t.id = t.el.attr('id');

        t.elWrapper = t.el.find('.page_content-wrapper');
        t.elRules = t.el.find('.page_content-rules');
        t.elErrors = t.el.find('.page_content-errors');
        t.elSmsTitles = t.el.find('.sms-helper_title');
        t.elRegTitles = t.el.find('.reg-helper_title');
        t.elRegTitles.eq(0).addClass('abcde').text('');
        t.elRegTitles.eq(1).addClass('abc').text('');

        t.templates = {};
        t.currentContent = t.elWrapper;

        t.el.find('.b-trigger[data-show]').click(function(){
            var showFunc = $(this).attr('data-show');

            if(showFunc === 'showHelper') t.toggleContentBlock('elWrapper');
            if(showFunc === 'showRules') t.toggleContentBlock('elRules');
        });

        t.el.css({opacity: '0', visibility: 'hidden', height: '1px'});
    };

    App.prototype.Slider.prototype.Page.prototype = {
        activate: function(duration){
            var t = this;

            t.form = utils.createWidget('Form', app, {el: t.el.find('.b-form')})[0];
            t.helper = utils.createWidget('Helper', app, {el: t.el.find('.b-helper')});

            if(utils.getCSS3('transition')){
                t.el.css(utils.getCSS3('transition'), 'opacity '+ duration + 'ms');
                t.el.css({visibility: '', height: '', opacity: '1'});
                setTimeout(doAfter, duration);
            } else {
                t.el.css({visibility: '', height: ''}).animate({opacity: 1}, duration, doAfter);
            }

            function doAfter(){
                t.el.removeAttr('style');
                t.form.setFocus();
            }
        },
        deactivate: function(duration){
            var t = this;

            if(utils.getCSS3('transition')){
                t.el.css(utils.getCSS3('transition'), 'opacity '+ (duration * 0.5) + 'ms');
                t.el.css('opacity', '0');
                setTimeout(doAfter, duration * 0.5);
            } else {
                t.el.animate({opacity: 0}, duration * 0.5, doAfter);
            }

            function doAfter(){
                t.el.removeAttr('style').css({visibility: 'hidden', height: '1px'});
                t.el.css(utils.getCSS3('transition'), '');
            }
        },
        showError: function(data, duration, callback){
            var t = this;

            //t.parent.elInner.css('height', '');
            t.currentContent.fadeOut(duration / 2, function(){
                var templateElement = t.el.find('#'+ data.id),
                        htmlData;

                if(!templateElement.length) return;

                if(!t.templates[data.id]) t.templates[data.id] = templateElement.html();
                htmlData = t.templates[data.id];

                if(data.params) for(var k in data.params){
                    htmlData = htmlData.split('{'+ k +'}').join(data.params[k]);
                }

                templateElement.html(htmlData);

                t.elErrors.fadeIn(duration / 2, function(){
                    t.currentContent = t.elErrors;
                    if($.isFunction(callback)) callback();
                });
            });
        },

        hideError: function(duration, callback){
            var t = this;

            //t.parent.elInner.css('height', '');
            t.el.css('height', t.el.height());
            t.elErrors.fadeOut(duration / 3, function(){
                t.el.css('height', '');
                t.elWrapper.fadeIn(duration, function(){
                    t.currentContent = t.elWrapper;
                    if($.isFunction(callback)) callback();
                });
            });
        },

        toggleContentBlock: function(elName, duration){
            var t = this;
            if(!t[elName].length) return;

            t.parent.elInner.css('height', '');
            t.el.css('height', t.el.height());
            t[elName].css({'right': '-30px'});
            t.currentContent.animate({opacity: 0, right: -30}, duration, function(){

                t.currentContent.css({display: 'none', right: '', opacity: ''});
                t[elName].css({display: 'block', opacity: 0});
                t.el.css('height', '');

                t[elName].animate({opacity: 1, right: 0}, duration);
                t.currentContent = t[elName];
            });
        }
    };


    /* APPLICATION.FORM */
    App.prototype.Form = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this;

        t.id = t.el.attr('id');

        t.elHelpTitles = $('.rules_col-title');
        t.errors = 0;
        t.validDelay = 1000;
        t.enabled = true;
        t.params = utils.getParams(t.el[0]);

        t.submitter = utils.createWidget('Button', app, {el: t.el.find('#'+ t.id +'Submit'), parent: t})[0];
        t.fields = utils.createWidget('Field', t, {el: t.el.find('.b-field'), parent: t});
        t.captcha = t.el.find('.b-captcha').length ? t.captchaInit(t.el.find('.b-captcha')) : false;

        t.el.submit(function(){ return t.submit(); });

        t.el.trigger('reset');
        t.setFocus();
        t.submitter.disable();
    };

    App.prototype.Form.prototype = {
        submit: function(){
            var t = this;
            if(t.enabled && t.validate()) t.response();
            return false;
        },
        response: function(){
            var t = this;
            t.enabled = t.submitter.loadStart();
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: t.params.actionPath,
                data: $.param(t.createData()),
                success: function(data){
                    t.enabled = t.submitter.loadFinish();
                    app.parseXHR(t, data);
                },
                error: function(data, textStatus){
                    if (textStatus == "parsererror" && data.responseText.indexOf(".do") > 0)
                    {
                        /*необходимо для корректной работы newDesignOldPassword */
                        window.location.reload();
                    }
                    else
                    {
                        t.enabled = t.submitter.loadFinish();
                        app.overlay.show({id: 'SysError'});
                    }
                }
            });
        },

        createData: function(){
            var t = this,
                    params = {};

            if(t.captcha && t.captcha.active){
                t.captcha.value = t.captcha.value.split('');
                for(var i = 0; i < t.captcha.value.length; i++){
                    t.captcha.value[i] = t.captcha.value[i].charCodeAt(0);
                }
                t.captcha.value = t.captcha.value.join('_');
            }

            for(var i = 0; i < t.fields.length; i++){
                params[t.fields[i].name] = encodeURI(t.fields[i].value);
            }

            if(t.params.operation){ params.operation = t.params.operation; }
            if(t.params.data){ $.extend(params, t.params.data); }
            if(app.token){ params['org.apache.struts.taglib.html.TOKEN'] = app.token; }
            if(app.pageToken){ params.PAGE_TOKEN = app.pageToken; }

            return params;
        },

        validate: function(){
            var t = this,  errors = 0;

            for(var i = 0; i < t.fields.length; i++){
                if(!t.fields[i].validate()) errors++;
            }
            t.enabled = errors ? t.submitter.disable() : t.submitter.enable();
            return t.enabled;
        },
        setErrors: function(errors){
            var t = this, field;

            errors = errors.reverse();

            for(var i = errors.length - 1; i >= 0; i--){
                if(field = t.findField('name', errors[i].name)){
                    field.addError({
                        type: errors[i].type,
                        param: errors[i].param,
                        value: errors[i].value,
                        text: errors[i].text,
                        delay: 100
                    });
                    field.isValid(errors[i].value, function(t, i, value){
                        t.tested.splice(i, 1);
                    });
                }
            }

            t.enabled = t.submitter.disable();
        },
        findField: function(prop, value){
            var t = this;
            for(var i = 0; i < t.fields.length; i++){
                if(t.fields[i][prop] === value || t.fields[i][prop] === 'field(' + value + ')') return t.fields[i];
            }
            return false;
        },
        setFocus: function(){
            var t = this;
        }
    };


    /* APPLICATION.FORM.FIELD */
    App.prototype.Form.prototype.Field = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this;

        t.elInput = t.el.find('.field_input');
        t.elInner = t.el.find('.field_inner');

        t.params = utils.getParams(t.el[0]);
        t.type = t.params.type;
        t.id = t.el.attr('id');
        t.name = t.elInput.attr('name');
        t.value = (t.elInput != undefined && t.elInput != null) ? t.elInput.val() : '';

        t.validDelay = parseInt($('input[name = hintDelay]').val());
        t.validDelay = isNaN(t.validDelay) ? 4000 : t.validDelay;

        t.queryTimer = null;
        if(t.params.type) t.params.valid[t.params.type] = true;

        t.TPL_ERROR = '<div class="b-error"><div class="error_inner">{text}</div><i class="error_arr"></i></div>';

        t.elInput.on('blur', function(e){ t.blur(e); });
        t.elInput.on('focus', function(e){ t.focus(e); });
        t.elInput.on('paste', function(e){ return t.action(e); });
        t.elInput.on('keydown', function(e){ return t.action(e); });
        t.parent.el.on('reset', function(){ t.init(); });

        t.init();
        t.initPlaceholder();
    };

    App.prototype.Form.prototype.Field.prototype = {
        init: function(){
            var t = this;

            t.changed = (t.elInput != undefined && t.elInput != null &&
                        t.elInput.val() != undefined && t.elInput.val() != null && t.elInput.val() != "");
            t.error = null;
            t.exclude = [];
            t.tested = [];
            t.focusIn = false;
            clearTimeout(t.valueTimeout);
            clearTimeout(t.timer);
        },

        initPlaceholder: function(){
            var t = this,
                    label = $('<label class="field_placeholder" for="'+ t.elInput.attr('id') +'">'+ t.elInput.attr('placeholder') +'</label>');

            t.elInput.removeAttr('placeholder').after(label);
            if($.trim(t.elInput.val())) label.hide();

            t.elInput.on('keydown', function(e){
                if (!e.metaKey && !e.altKey && !e.shiftKey) label.hide();
                else if (!t.elInput.val()) label.show();
            }).on('keyup', function(e){
                        if (t.elInput.val()) label.hide();
                        else label.show();
                    }).on('blur', function(e){
                        if(!t.elInput.val()) label.show();
                        else label.hide();
                    });
        },

        setCaretPos: function(pos){
            var t = this, range;

            if(t.elInput[0].setSelectionRange){
                t.elInput[0].setSelectionRange(pos, pos);
                return;
            }

            range = t.elInput[0].createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        },

        getCaretPos: function(){
            var t = this, selection;


            if (t.elInput[0].selectionStart || t.elInput[0].selectionStart == '0'){
                return t.elInput[0].selectionStart;
            }

            selection = document.selection.createRange();
            selection.moveStart('character', -t.elInput[0].value.length);
            return selection.text.length;
        },

        action: function(e){
            var t = this;

            if($.inArray(e.which, [9,16,17,18,37,39]) >= 0){
                return true;
            } else if( (e.metaKey || e.altKey || e.ctrlKey) && e.type != "paste" ){
                return true;
            } else if(e.which === 13){
                t.parent.submit();
                return false;
            }

            t.removeError(true);

            t.changed = true;
            t.set();
        },

        getLenChanges: function(value){
            var t = this, word;

            t.caretPos = t.getCaretPos();
            word = value.substring(0, t.caretPos);

            if(t.params.replace){
                t.caretPos -= word.length - word.replace(t.params.replace, '').length;
            }
            return t.caretPos;
        },

        set: function(callback){
            var t = this, value, oldLen;

            if (t.elInput[0].className.indexOf('rsa') != -1)
                return true;

            clearTimeout(t.valueTimeout);
            t.valueTimeout = setTimeout(function(){
                value = t.elInput.val().toLowerCase();

                t.caretPos = t.getLenChanges(value);
                t.value = t.params.replace ? value.replace(t.params.replace, '') : value;
                t.elInput.val(t.editValue(t.value));
                if(t.focused) t.setCaretPos(t.caretPos);

                t.parent.validate();

                if(t.params.fill) $(t.params.fill).text(value);
                if($.isFunction(callback)) callback();
            }, 50);

        },

        addError: function(params, isAuto){
            var t = this;

            clearTimeout(t.timer);
            var delay;

            if (t.error)
                delay = t.error.delay || t.validDelay;
            else
                delay = t.validDelay;

            t.timer = setTimeout(function(){

                t.removeError();

                params.text = params.text || (ERRORS[params.type] || ERRORS[t.type +'_'+ params.type] || ERRORS[t.type]);
                params.text = params.param ? params.text.replace('{n}', params.param) : params.text;

                t.error = $.extend({
                    value: '', /* учитывая интервалы - опасная штука */
                    type: t.type || 'default',
                    text: '',
                    param: '',
                    popup: true,
                    pop: null,
                    delay: null
                }, params);

                t.el.addClass('error');
                t.addPop(isAuto);

                /* Если в исключениях нет ошибки - добавляем */
                if(!t.isExclude(params.value) && params.type !== 'compare' && params.type !== 'differ'){
                    t.exclude.push(t.error);
                }

            }, delay);

            return false;
        },

        removeError: function(onlyHide){
            var t = this;
            clearTimeout(t.timer);
            if(!t.error) return true;
            if(t.error.pop) t.deletePop();
            if(!onlyHide) t.error = null;
            t.el.removeClass('error');
            return true;
        },

        addPop: function(isAuto){
            var t = this,
                    fields = t.parent.fields;

            if(!t.error || !t.error.popup || (isAuto && t.parent.focusIn && t.parent.focusIn !== t)) return;
            for(var i = 0; i < fields.length; i++) fields[i].deletePop();
            t.error.pop = $(t.TPL_ERROR.replace('{text}', t.error.text));
            t.elInner.append(t.error.pop);
        },

        deletePop: function(){
            var t = this;
            if(t.error && t.error.pop) t.error.pop.remove();
        },

        focus: function(){
            var t = this;
            t.parent.focusIn = t;
            t.focused = true;
            t.el.addClass('focus');
            t.addPop();
            $('body').addClass(t.type + 'InputFocus');
        },

        blur: function(){
            var t = this;
            t.parent.focusIn = null;
            t.focused = false;
            t.el.removeClass('focus');
            t.set();
            $('body').removeClass(t.type + 'InputFocus');
        },

        validate: function(){
            var t = this, exclude;

            if(t.params.valid.required && !t.changed) return false;
            if(exclude = t.isExclude(t.value)) return t.addError(exclude, true);
            for(var k in t.params.valid){
                if(!t.params.valid.hasOwnProperty(k)) continue;
                if(!t.validator[k](t, t.value, t.params.valid[k], true)){
                    return t.addError({type: k, param: t.params.valid[k], value: t.value}, true);
                }
            }
            if(t.params.compare && !t.compare(t.params.compare)) return t.addError({type: 'compare', value: t.value}, true);
            if(t.params.differ && !t.differ(t.params.differ)) return t.addError({type: 'differ', value: t.value}, true);
            if(t.params.avail && !t.validator.avail(t, t.value)) return false;
            return t.removeError();
        },

        compare: function(id){
            var t = this;
            for(var i = 0; i < t.parent.fields.length; i++){
                if(t.parent.fields[i].id === id) return t.parent.fields[i].value === t.value;
            }
        },

        differ: function(id){
            var t = this;
            for(var i = 0; i < t.parent.fields.length; i++){
                if(t.parent.fields[i].id === id) return t.parent.fields[i].value !== t.value;
            }
        },

        isExclude: function(value){
            var t = this;
            for(var i = 0; i < t.exclude.length; i++){
                if(t.exclude[i].value === value) return t.exclude[i];
            }
            return false;
        },

        isValid: function(value, callback){
            var t = this;
            for(var i = 0; i < t.tested.length; i++){
                if(t.tested[i] === value){
                    if($.isFunction(callback)) callback(t, i, value);
                    return true;
                }
            }
            return false;
        },

        editValue: function(value){
            var t = this,
                    len = value.length < 13 ? value.length : 13;

            if(!t.type || t.params.type !== 'card') return value;

            if(value.length < 5) return value;
            value = value.split('');
            for(var i = 0; i < len; i++){
                if(!((i + 1) % 4) && i < len - 1){
                    value[i] += ' ';
                    if(i + 1 < t.caretPos) t.caretPos++;
                }
            }
            return value.join('');
        },

        validator: {
            required: function(t, value){
                return !!$.trim(value);
            },
            maxLen: function(t, value, len){
                return value ? value.length <= len : true;
            },
            minLen: function(t, value, len){
                return value ? value.length >= len : true;
            },
            letter: function(t, value){
                return value ? /[a-z]+/g.test(value) : true;
            },
            number: function(t, value){
                return value ? /\d/g.test(value) : true;
            },
            let3: function(t, value){
                var rg = /(.)(\1){3,}/g;
                return value ? !rg.test(value) : true;
            },
            email: function(t, value){
                return value ? /^[-._a-z0-9]+@(?:[a-z0-9][-a-z0-9]+\.)+[a-z]{2,6}$/gi.test(value) : true;
            },
            card: function(t, value){
                var a = value.split(''), total = 0, num = [0,1,2,3,4,5,6,7,8,9], l = num.length;
                while(l--) if(new RegExp('^'+num[l]+'+$','g').test(value)) return false;
                a.push('X');
                a.reverse();
                for(var i = 1; i < a.length; i++){
                    n = parseInt(a[i], 10);
                    n = (i%2) ? n : (n*2 > 9) ? n*2 - 9 : n*2;
                    total += n;
                }
                return !(total%10);
            },
            login: function(t, value){
                var rg = /^([\w@\-\.]*)$/g;
                return value ? rg.test(value) : true;
            },
            password: function(t, value){
                var rg = /^([\w@\-\.!#$%^&*()+:;,]*)$/g;
                return value ? rg.test(value) : true;
            },
            sms: function(){
                return true;
            },
            key3: function(){
                var testStr = '1234567890|йцукенгшщзхъ|фывапролджэ|ячсмитьбю|qwertyuiop|asdfghjkl;|zxcvbnm,.|!@#$%^&*()_+';
                testStr += testStr + '|' + testStr.split('').reverse().join('');
                return function(t, value){
                    if(!value || value.length < 4) return false;
                    var rgxStr = '';
                    value = value.split('');
                    for(var i = 0; i < value.length - 3; i++){
                        for(var j = 0; j < 4; j++) rgxStr += ('\\' + value[i + j]);
                        rg = new RegExp(rgxStr,'g');
                        if(rg.test(testStr)) return false;
                    }
                    return true;
                };
            }(),
            avail: function(t, value){
                var params = {};
                if(!value) return false;
                if(t.parent.captcha && t.parent.captcha.active) return true;
                if(t.available && t.available === value) return true;
                if(t.isValid(value)) return true;
                clearTimeout(t.queryTimer);
                t.queryTimer = setTimeout(function(){
                    params[t.name] = value;
                    if(app.token){ params['org.apache.struts.taglib.html.TOKEN'] = app.token; }
                    if(app.pageToken){ params.PAGE_TOKEN = app.pageToken; }
                    if(t.params.avail.data){ $.extend(params, t.params.avail.data) }
                    $.ajax({
                        type: "POST",
                        dataType: 'json',
                        url: t.params.avail.path,
                        data: $.param(params),
                        success: function(data){
                            switch(data.state){
                                case 'success':
                                    t.available = value;
                                    t.removeError();
                                    t.parent.validate();
                                    t.tested.push(value);
                                    break;
                                case 'fail':
                                    t.addError({type: 'avail'}, true);
                                    t.available = false;
                                    t.el.focus();
                                    break;
                            }
                            if(data.captcha !== undefined && t.parent.captcha){
                                data.captcha ? t.parent.captcha.show() : t.parent.captcha.hide();
                            }
                        },
                        error: function(data){
                            app.overlay.show({id: 'SysError'});
                        }
                    });
                }, 500);
                return false;
            }
        }
    };

    /* APPLICATION.FORM.CAPTCHA */
    App.prototype.Form.prototype.captchaInit = function(element){
        var form = this,
                t = utils.createWidget('Field', form, {el: element, parent: form, active: !!form.captcha})[0];

        t.name = 'field(captchaCode)';
        t.elRefresh = t.el.find('.captcha_refresh');
        t.elImage = t.el.find('.captcha_image');
        t.imageSrc = t.params.url || t.elImage[0].src.split('?')[0] || '';

        t.elRefresh.add(t.elImage).click(function(){ t.refresh(); return false; });

        t.refresh = function(){
            t.elImage[0].src = t.imageSrc +'?noc='+ Math.random();
        };

        t.show = function(){
            $('.slider_inner').css('height', '');
            t.refresh();
            t.el.show();
            form.el.addClass('hasCaptcha');
            t.active = true;
            t.parent.fields.push(t);
        };

        t.hide = function(){
            if(!t.active) return;
            t.el.hide();
            form.el.removeClass('hasCaptcha');
            t.active = false;
            t.parent.fields.pop();
        };

        if(t.active) t.show();
        else t.hide();

        return t;
    };

    /* APPLICATION.TIMER */
    App.prototype.Timer = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                config = utils.getParams(t.el[0]),
                secTimer = null;

        t.set = function(time){
            t.el.text(time);

            if(time <= 121) {
                t.el.show(200);
            }  else {
                t.el.hide(200);
            }

            clearInterval(secTimer);
            secTimer = setInterval(function(){
                if(time <= 121) t.el.fadeIn(200);
                if(time === 0){
                    if (app.PAGE_TOKEN){
                        app.overlay.show({
                            text: ERRORS.timer_get_sms
                        });
                    } else if (app.pageType === 'registration'){
                        app.overlay.show({
                            text: ERRORS.timer_get_reg,
                            onCloseRedirect: location.href
                        });
                    } else if (app.pageType === 'recover'){
                        app.overlay.show({
                            text: ERRORS.timer_get_recover,
                            onCloseRedirect: location.href
                        });
                    } else {
                        app.overlay.show({
                            text: ERRORS.timer_default,
                            onCloseRedirect: location.href
                        });
                    }
                    return t.stop()
                }
                t.el.text(--time);
            }, 1000);
        };

        t.stop = function(){
            clearInterval(secTimer);
            t.el.hide();
            return false;
        };

        app.el.on('slider.switch.start', function(){
            clearInterval(secTimer);
            t.stop();
        });

        t.el.hide();
    };


    /* APPLICATION.ACTION */
    App.prototype.Action = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                config = utils.getParams(t.el[0]);

        t.send = function(){
            var params = {};

            if(!config.actionPath) return false;

            if(app.token){ params['org.apache.struts.taglib.html.TOKEN'] = app.token; }
            if(app.pageToken){ params.PAGE_TOKEN = app.pageToken; }

            $.ajax({
                type: "POST",
                dataType: 'json',
                url: config.actionPath,
                data: $.extend(params, config.data || {}),
                success: function(data){
                    app.parseXHR(t, data);
                },
                error: function(){
                    app.overlay.show({id: 'SysError'});
                }
            });

            return false;
        };

        t.el.click(function(){ return t.send(); });
    };


    /* APPLICATION.HELPER */
    App.prototype.Helper = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                config = utils.getParams(t.el[0]);

        t.showElement = function(){
            t.el.fadeOut(300, function(){
                config.show.fadeIn(300);
            });
        };

        if(config.show){
            setTimeout(function(){ t.showElement() }, config.interval || 0);
        }
    };


    /* APPLICATION.BUTTON */
    App.prototype.Button = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                enabled = true,
                $input = t.el.find('.btn_hidden');

        t.enable = function (){
            enabled = true;
            t.el.removeClass('disabled');
            $input.prop('disabled', false);
            return enabled;
        };

        t.disable = function(){
            enabled = false;
            t.el.addClass('disabled');
            $input.prop('disabled', true);
            return enabled;
        };

        t.loadStart = function(){
            t.el.addClass('loading');
            return t.disable();
        };

        t.loadFinish = function(){
            t.el.removeClass('loading');
            return t.disable();
        };

        $input.on({
            'focus': function(){ t.el.addClass('hover') },
            'blur': function(){ t.el.removeClass('hover') },
            'mousedown': function(){ t.el.addClass('active') },
            'mouseup': function(){ t.el.removeClass('active') },
            'mouseenter': function(){ t.el.addClass('hover') },
            'mouseleave': function(){ t.el.removeClass('hover active') }
        });
    };


    /* APPLICATION.ACCORDION */
    App.prototype.Accordion = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                $items = t.el.find('.accordion_item');

        $items.each(function(){
            var $item = $(this),
                    $title = $item.find('.accordion_title .dash'),
                    $content = $item.find('.accordion_body');

            $title.click(function(){
                if($content.is(':visible')) $content.slideUp(200);
                else $content.slideDown(200);
            });
        });

    };


    /* APPLICATION.OVERLAY */
    App.prototype.Overlay = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,
                popups = utils.createWidget('Popup', t, {el: t.el.find('.b-popup'), parent: t}),
                triggers = $('[data-popup]'),
                current = null,
                disabled = false,
                onClose = function(){};

        triggers.click(function(){ show({id: $(this).attr('data-popup')}) });

        t.elBg = $('<i class="overlay_bg"></i>').prependTo(t.el);
        t.elBg.width(t.elBg.width() - t.barWidth);

        t.el.on('click', '.overlay_bg, .popup_close, .back_text', hide);

        function resize(){
            current.el.css({
                top: current.getTopPos(),
                left: current.getLeftPos()
            });

            t.el.css({'height': $(window).height()});
        }

        function show(params, callback){
            current = find(params.id || 'CustomMessage');
            if(!current) return;

            if(params.onCloseRedirect && !params.disabled){
                onClose = function(){ location.href = params.onCloseRedirect };
            }

            if(params.text){
                current.el.find('.b-message').html(params.text);
                if(params.text.length < 45) current.el.addClass('centered');
            }
            if(params.disabled){
                disabled = true;
                current.el.find('.popup_close').hide();
            } else {
                current.el.find('.popup_close').show();
            }

            app.el.blur();
            current.el.show();

            $('input').blur();
            $(window).on('resize', resize);
            app.el.on('keydown', disableKeys);
            t.el.fadeTo(0,0).show();
            resize();
            t.el.animate({opacity: 1}, 150, function(){
                if($.isFunction(callback)) callback();
            });
            $('html').css({'overflow':'hidden','padding-right': app.barWidth});
        }

        function hide(callback){
            if(!current || disabled) return false;
            else onClose();
            t.el.animate({opacity: 0}, 150, function(){
                t.el.hide();
                restore();
                $(window).off('resize', resize);
                app.el.off('keydown', disableKeys);
                t.el.find('.centered').removeClass('centered');
                $('html').css({'overflow':'','padding-right': ''});
                if($.isFunction(callback)) callback();
            });
        }

        function find(id){
            for(var i = 0; i < popups.length; i++){
                if(id === popups[i].id) return popups[i];
            }
            return 0;
        }

        function restore(){
            current = null;
            disabled = false;
            for(var i = 0; i < popups.length; i++){
                popups[i].el.hide().find('.popup_close').show();
            }
        }

        function disableKeys(e){
            e.stopPropagation();
            if(e.which == 9) return false;
        }

        return {
            show: show,
            hide: hide
        }
    };


    /* APPLICATION.OVERLAY.POPUP */
    App.prototype.Overlay.prototype.Popup = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this;

        t.id = t.el.attr('id');
        t.buttons = utils.createWidget('Button', app, {el: t.el.find('.b-btn')});

        t.init();
    };

    App.prototype.Overlay.prototype.Popup.prototype = {
        init: function () {
            var t = this;

            if (t.id === 'CancelReasonPopup') t.initCancelReason();
        },
        getTopPos: function () {
            var t = this,
                    parentH = t.parent.el.height(),
                    elemH = t.el.height();

            return parentH > elemH ? (parentH - elemH) / 2 | 0 : 0;
        },
        getLeftPos: function () {
            var t = this,
                    parentW = t.parent.el.width(),
                    elemW = t.el.width();

            return parentW > elemW ? (parentW - elemW) / 2 | 0 : 0;
        },
        initCancelReason: function () {
            var t = this,
                    form = t.el.find('form'),
                    lines = t.el.find('.cancel-form_line'),
                    areas = t.el.find('.cancel-form_area textarea'),
                    button = t.el.find('.b-btn'),
                    closeBtn = form.parents('.b-popup').eq(0).find('.popup_close'),
                    enableSubmit = false,
                    ie6789 = app.ie && app.ie < 10;

            areas.prop('disabled', true);
            form.trigger('reset');

            lines.each(function () {
                var line = $(this),
                        radio = line.find('.cancel-form_radio');

                radio.change(function () {
                    var area = $(this).parent().parent().find('textarea');

                    if ($(this).prop('checked')) {
                        lines.removeClass('checked');
                        line.addClass('checked');
                        areas.prop('disabled', true).val(ie6789 ? areas.attr('placeholder') : '');
                        if (area.length) {
                            enableSubmit = false;
                            area.prop('disabled', false).focus();
                            button.addClass('disabled').find('.btn_hidden').prop('disabled', true);
                        } else {
                            button.removeClass('disabled').find('.btn_hidden').prop('disabled', false);
                            enableSubmit = true;
                        }
                    }
                });
            });

            areas.parent().click(function () {
                $(this).prev().find('input').prop('checked', true);
                $(this).find('textarea').prop('disabled', false).focus();
                button.addClass('disabled').find('.btn_hidden').prop('disabled', true);
            });

            if (ie6789) {
                areas.on({
                    'focus': function () {
                        if (areas.val() === areas.attr('placeholder')) areas.val('');
                    },
                    'blur': function () {
                        if (!$.trim(areas.val())) areas.val(areas.attr('placeholder'));
                    }

                }).val(areas.attr('placeholder'));
            }

            areas.keyup(function () {
                if ($.trim(this.value)) {
                    button.removeClass('disabled').find('.btn_hidden').prop('disabled', false);
                    enableSubmit = true;
                } else {
                    button.addClass('disabled').find('.btn_hidden').prop('disabled', true);
                    enableSubmit = false;
                }
            });

            form.find('.back_text').click(function(){
                button.addClass('disabled').find('.btn_hidden').prop('disabled', true);
            });

            closeBtn.on('click', function () {
                form.find('.back_text').click();
            });

            form.on('submit', function () {
                return enableSubmit;
            });
        }
    };

    /* APPLICATION.BACKGROUND */
    App.prototype.Background = function(params){
        var t = this;
        for(var k in params) if(params.hasOwnProperty(k)) t[k] = params[k];

        t.visible = false;

        var supportSvg = !!document.createElementNS && !!document.createElementNS('http://www.w3.org/2000/svg', "svg").createSVGRect,
                elInner = $('<div class="svg_inner" style="width:100%;height:100%;opacity:0;filter:alpha(opacity=0);"></div>').appendTo(t.el),
                w, h;

        t.showErrorBg = function(duration, callback){
            elInner.html(draw()).animate({opacity: 1}, duration || 1000, function(){
                t.visible = true;
                $(window).on('resize', resize);
                if($.isFunction(callback)) callback();
            });
        };

        t.hideErrorBg = function(duration, callback){
            $(window).off('resize', resize);
            elInner.animate({opacity: 0}, duration || 1000, function(){
                elInner.empty();
                t.visible = false;
                if($.isFunction(callback)) callback();
            });
        };

        function draw(){
            calculateSizes();
            return supportSvg ? drawSvg() :  drawVml();
        }

        function drawSvg(){
            var elements = '<path d="M'+ (((w*0.42)|0)) +','+ (h+1) +' H'+ (w-17) +' c9,1,17-4,17-14 V212 L'+ (((w*0.53)|0)+1) +',161" style="fill:#e68107"></path>' +
                    '<line x1="'+ (((w*0.42)-1|0)) +'" y1="'+ (h+1) +'" x2="'+ (((w*0.53+1)|0)) +'" y2="161" style="stroke:#cccccc;stroke-width:1" />' +
                    '<line x1="'+ (((w*0.53+1)|0)) +'" y1="161" x2="'+ (((w*0.45)|0)) +'" y2="0" style="stroke:#cccccc;stroke-width:1" />' +
                    '<line x1="'+ (((w*0.53+1)|0)) +'" y1="161" x2="'+ w +'" y2="212" style="stroke:#cccccc;stroke-width:1" />';
            return '<svg xmlns="http://www.w3.org/2000/svg" xlink="http://www.w3.org/1999/xlink" style="position:relative" width="'+ w +'" height="'+ h +'">'+ elements +'</svg>';
        }

        function drawVml(){
            document.namespaces.add('v',"urn:schemas-microsoft-com:vml", "#default#VML");
            /* if(app.ie < 8) document.createStyleSheet().cssText = 'v\\:*{behavior: url(#default#VML);}'; */

            var elements = '<v:shape style="position:absolute;bottom:0;right:0;width:'+w+'px;height:'+h+'px;display:block;" path = "m '+w+','+h+' l '+(((w*0.42)|0))+','+h+' '+(((w*0.53+1)|0))+',162 '+w+',213 x e" coordsize="'+w+' '+h+'" strokecolor="#e68107" fillcolor="#e68107"></v:shape>' +
                    '<v:line from="'+ (((w*0.42)-1|0)) +','+(h+1)+'" to="'+ (((w*0.53+1)|0)) +',161" strokecolor="#cccccc" strokeweight="1px" />' +
                    '<v:line from="'+ (((w*0.53+1)|0)) +',161" to="'+ (((w*0.45)|0)) +',0" strokecolor="#cccccc" strokeweight="1px" />' +
                    '<v:line from="'+ (((w*0.53+1)|0)) +',161" to="'+ w +',212" strokecolor="#cccccc" strokeweight="1px" />';

            return elements;
        }

        function calculateSizes(){
            w = t.el.width();
            h = t.el.height();
        }

        function resize(){
            elInner.html(draw());
        }
    };


    /* APPLICATION.INIT */
    $(function(){
        new App({el: $('body')});
    });

}());
