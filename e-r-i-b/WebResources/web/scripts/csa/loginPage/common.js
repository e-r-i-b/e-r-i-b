'use strict';

var app = {

    $elem: $('body'),
    $wnd: $(window),
    $doc: $(document),
    $wrp: $('.g-wrapper'),
	$side: $('.g-side'),

    ie: document.all && document.querySelector ? 8 : false,

    getParams: function(node){
        node = node.length ? node[0] : node;
        if(node.nodeType && node.nodeType === 1 && node.onclick){
            var params = node.onclick();
            return $.isPlainObject(params) ? params : {};
        } else return {};
    },
	
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
	}()),
	
	setSizes: function(){
        clearTimeout(app.timer);
        app.timer = setTimeout(function(){
            app.$wrp.css('min-height', app.$side.outerHeight(true)+ 'px');
        });
    }
};

var overlay;
$(function(){

    $('#loginForm').jForm();
    $('#Secure').jSecure();

    $('#Backgrounds').jBgSlider({
        duration: 750,
        sync: $('#Slides .slides_slide')
    });

    overlay = new JOverlay('.g-overlay');

	app.setSizes();
    app.$wnd.on('resize', app.setSizes);
    //overlay.showPopup('MultiRegFix');

    if(app.ie) {
		app.$wnd.JMediaHeightIe([43, 48]);
	}
});

(function($){

    var _name = 'jBgSlider';

    function JBgSlider( elem, options ){
        this.$elem = $(elem);
		this.$wrapper = this.$elem.parent();

        this.cfg = $.extend({
            auto: 15000,
            duration: 500,
            sync: $('')
        }, options, app.getParams(elem));

        this.$slides = this.$elem.find('.bg_slide');
        this.$paging = this.$elem.find('.bg_paging');

		this.switchSlide = this.switchSlide();
        this.current = {slide: null, bg: null};
        this.animate = false;

        this.cfg.sync.on('mouseenter', $.proxy(this.disableAutoSlide, this));
        this.cfg.sync.on('mouseleave', $.proxy(this.enableAutoSlide, this));
        app.$wnd.on('mouseenter', $.proxy(this.enableAutoSlide, this));
        app.$wnd.on('mouseleave mouseout', $.proxy(this.disableAutoSlide, this));
        this.$paging.on('click', $.proxy(this.clickSwitch, this));
        app.$wnd.on('mousewheel', $.proxy(this.wheelSwitch, this));
        app.$wnd.on('DOMMouseScroll', $.proxy(this.wheelSwitch, this));

		this.init();
        this.setSlide(0);
        this.enableAutoSlide();
    } 

    JBgSlider.prototype = {
        constructor: JBgSlider,
        init: function(){
			
            if(app.ie) {
				var t = this;
				$.fn.jBackgroundSize && t.$slides.jBackgroundSize();
				t.$elem.height(t.$wrapper.height());
				app.$wnd.on('resize', function(){
					t.$elem.height(t.$wrapper.height());
				});
			}
        },
        drawPaging: function(index){
            var bullets = '';
            for(var i = 0, l = this.$slides.length; i < l; i++){
                bullets += '<a class="bg_page'+ (index == i ? ' current' : '') + '"></a>';
            }
            this.$paging.html(bullets);
        },
        setSlide: function(index){
            this.current.slide = this.$slides.eq(index).css({visibility: 'visible', display:'block', opacity: '1'});
            this.current.sync = this.cfg.sync.eq(index).css({visibility: 'visible', display:'block', opacity: '1'});
            this.drawPaging(index);
            this.$elem.trigger('slider:change', {
                url: this.current.slide.css('background-image'),
                duration: 0
            });
        },
		animJS: function(index){
			if(this.animate || index == this.current.slide.index()) return;
            this.animate = true;
            this.current.slide.fadeOut(this.cfg.duration);
            this.current.sync.fadeOut(this.cfg.duration * 0.75);
			
            this.current.slide = this.$slides.eq(index);
            this.current.sync = this.cfg.sync.eq(index);
			
            this.current.slide.fadeIn(this.cfg.duration);
            this.current.sync.fadeIn(this.cfg.duration * 1.25, $.proxy(function(){
                this.animate = false;
            }, this));
			
            this.drawPaging(index);
            this.$elem.trigger('slider:change', {
                url: this.current.slide.css('background-image'),
                duration: this.cfg.duration
            });
		},
		animCSS3: function(index){
			if(this.animate || index == this.current.slide.index()) return;
            this.animate = true;
			
			this.current.slide.addClass('animFadeOut').css({opacity: '0', zIndex: '15'});
			this.current.sync.addClass('animFadeOut').css({opacity: '0', zIndex: '15'});
			
			setTimeout($.proxy(function(slide, sync){
				slide.css({visibility: 'hidden', opacity: '', zIndex: ''}).removeClass('animFadeOut');
				sync.css({visibility: 'hidden', opacity: '', zIndex: ''}).removeClass('animFadeOut');
			}, this, this.current.slide, this.current.sync), 1250);
			
            this.current.slide = this.$slides.eq(index);
            this.current.sync = this.cfg.sync.eq(index);
			
			this.current.slide.addClass('animFadeIn').css({visibility: 'visible', opacity: '1'});
			this.current.sync.addClass('animFadeIn').css({visibility: 'visible', opacity: '1'});
			
			setTimeout($.proxy(function(slide, sync){
				this.current.slide.removeClass('animFadeIn');
				this.current.sync.removeClass('animFadeIn');
				this.animate = false;
			}, this), 1250);
			
			
            this.drawPaging(index);
            this.$elem.trigger('slider:change', {
                url: this.current.slide.css('background-image'),
                duration: this.cfg.duration
            });
		},
		switchSlide: function(){
            if(app.getCSS3('transition')) {
				this.$slides.addClass('css3transition');
				this.cfg.sync.addClass('css3transition');
				return this.animCSS3;
			} else {
				return this.animJS;
			}
        },
        clickSwitch: function(e){
            this.enableAutoSlide();
            this.switchSlide($(e.target).index());
        },
        wheelSwitch: function(e){
            if(this.animate || app.$wrp.height() > app.$wnd.height()) return;
            var ev = e.originalEvent,
                delta = (ev.wheelDelta) ? ev.wheelDelta * 1 : ev.detail*-1,
                index, elem;

            if (delta > 0) {
                elem = this.current.slide.prev();
                index = elem.length ? elem.index() : this.$slides.length - 1;
            } else {
                elem = this.current.slide.next();
                index = elem.length ? elem.index() : 0;
            }
            this.enableAutoSlide();
            this.switchSlide(index);
        },
        enableAutoSlide: function(){
            clearInterval(this.autoAnim);
            this.autoAnim = setInterval($.proxy(function(){
                var elem = this.current.slide.next(),
                    index = elem.length ? elem.index() : 0;

                this.switchSlide(index);
            }, this), this.cfg.auto);
        },
        disableAutoSlide: function(){
            clearInterval(this.autoAnim);
        }
    };

    $.fn[_name] = function(options){
        return this.each(function(){
            if (!$.data(this, _name)) {
                $.data(this, _name, new JBgSlider( this, options ));
            }
        });
    };

})(jQuery);


/* Secure */
(function($){

    var _name = 'jSecure';

    function JSecure( elem, options ){
        this.$elem = $(elem);

        this.cfg = $.extend({}, options, app.getParams(elem));

        this.$trigger = this.$elem.find('.secure_next');
        this.$block = this.$elem.find('.secure_blocks');
        this.$notes = this.$elem.find('.secure_block');

        this.current = this.$notes.hide().eq(0).show();

        this.$trigger.on('click', $.proxy(this.showNext, this));

		app.$wnd.on('resize', $.proxy(this.resize, this));
    }

    JSecure.prototype = {
        constructor: JSecure,
        showNext: function(){
            var nextNote = this.current.next();
            nextNote = nextNote.length ? nextNote : this.$notes.eq(0);

            this.$block.height(this.$block.height());
            this.current.css('position', 'absolute').fadeOut(250);
            this.current = nextNote.css('position', 'absolute').fadeIn(250);
            this.$block.animate({height: nextNote.height()}, 250);
        },
		
		resize: function(){
			this.$block.height(this.current.height());
		}




    };

    $.fn[_name] = function(options){
        return this.each(function(){
            if (!$.data(this, _name)) {
                $.data(this, _name, new JSecure( this, options ));
            }
        });
    };

})(jQuery);


(function($){







    var _name = 'jNews';

    function JNews( elem, options ){
        this.$elem = $(elem);
        this.$inner = this.$elem.find('.news_inner');
        this.$blur = this.$elem.find('.news_blur');
        this.$fade = this.$elem.find('.news_fade');
		
		this.changePic = this.changePic();

        app.$wnd.on('resize', $.proxy(this.refresh, this));
        $('#Backgrounds').on('slider:change', $.proxy(this.changePic, this));

        this.refresh();
    }

    JNews.prototype = {
        constructor: JNews,
        changePic: function(){
			if(app.getCSS3('transition')) {
				return this.animCSS3;
			} else {
				return this.animJS;
			}
        },
		animJS: function(e, data){
			var tmpBlur = this.$blur.clone().css({'background-image': data.url,'z-index': 1}).fadeTo(0,0);
            this.$blur.fadeTo(data.duration , 0);
            tmpBlur.insertAfter(this.$blur).fadeTo(data.duration, 1, $.proxy(function(){
                this.$blur.css('background-image', data.url).fadeTo(0, 1);
                tmpBlur.remove();
            }, this));
		},
		animCSS3: function(e, data){
			var tmpBlur = this.$blur.clone().css({'background-image': data.url,'z-index': 1, opacity: '0'});
			
			this.$fade.addClass('animShortFade');
			this.$blur.addClass('animFadeOut').css('opacity', '0');
			tmpBlur.insertBefore(this.$blur).addClass('animFadeIn').css('opacity', '1');
			
			setTimeout($.proxy(function(){
				this.$fade.removeClass('animShortFade');
			}, this), 650);
			
			setTimeout($.proxy(function(){
				this.$blur.remove();
				this.$blur = tmpBlur.removeClass('animFadeIn');
			}, this), 1250);
		},
        refresh: function(){
            this.$blur.css({
                width: app.$wrp.width(),
                height: app.$wrp.height(),
                left: -this.$inner.offset().left,
                top: -this.$inner.offset().top
            });
        }
    };

    $.fn[_name] = function(options){
        return this.each(function(){
            if (!$.data(this, _name) && !app.ie) {
                $.data(this, _name, new JNews( this, options ));
            }
        });
    };

})(jQuery);


/* Form */
(function($){

    var _name = 'jForm';

    function JForm( elem, options){
        this.$elem = $(elem);

        this.cfg = $.extend({}, options, app.getParams(elem));

        this.validator = new this.Validator();
        this.fields = this.createFields('.b-field');

        this.fields[0].input.focus();
    }

    JForm.prototype = {
        constructor: JForm,
        createFields: function(selector){
            var arr = [], t = this;
            this.$elem.find('.b-field').each(function(){
                arr.push(new t.Field({elem: $(this)}));
            });
            return arr;
        },
        submit: function(){
            var t = this;
            if(t.validate())
                t.response();
        },
        clearVSPValidator: function(){
            var t = this;
            t.fields[3].valid = {};
        },
        fillVSPValidator: function(){
            var t = this;
            t.fields[3].valid = {required: true, minLen: 3, maxLen: 7};
        },
        validate: function(){
            var t = this, result;

            for(var i = 0, len = t.fields.length; i < len; i++){
                result = t.validator.test(t.fields[i].getValue(), t.fields[i].valid,  t.fields[i].type);
                if(result){
                    t.fields[i].addError(result);
                    return false;
                } else t.fields[i].removeError();
            }
            return true;
        },

        response: function(){
            submitData();
        }
    };

    JForm.prototype.Validator = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this,

            check = {
                required: function(value, prop, type){
                    return $.trim(value) ? false : (t.ERRORS['required_' + type] || t.ERRORS.required);
                },
                minLen: function(value, prop){
                    return value.length >= prop ? false : t.ERRORS.minLen.replace('{n}', prop);
                },
                maxLen: function(value, prop){
                    return value.length <= prop ? false : t.ERRORS.maxLen.replace('{n}', prop);
                }
            };

        t.test = function(value, validProps, type){
            for(var k in validProps){
                if(!check[k]) continue;
                var result = check[k](value, validProps[k], type);
                if(result) return result;
            }
            return false;
        };
    };

    JForm.prototype.Validator.prototype.ERRORS = {
        required: 'Заполните поле',
        minLen: 'Введите минимум {n} символа',
        maxLen: 'Введите максимум {n} символов',
        required_login: 'Введите идентификатор или&nbsp;логин пользователя',
        required_password: 'Введите пароль',
        required_captcha: 'Введите код с картинки'
    };

    /* APPL.FORM.FIELD */
    JForm.prototype.Field = function(params){
        for(var k in params) if(params.hasOwnProperty(k)) this[k] = params[k];

        var t = this;

        t.input = t.elem.find('.field_input,.field_select').eq(0);
        t.holder = t.elem.find('.field_title');
        t.codeHead = t.elem.find('.field_head');
        t.codeImg = t.elem.find('.field_captcha');

        t.codeHead.click($.proxy(this.refreshCaptcha, this));
        t.input.on('focus', $.proxy(this.focus, this));
        t.input.on('blur', $.proxy(this.blur, this));
        t.input.on('keydown paste propertychange drop', $.proxy(this.valInput, this));
        t.elem.on('mouseenter', $.proxy(this.hoverIn, this));
        t.elem.on('mouseleave', $.proxy(this.hoverOut, this));

		if(t.elem.hasClass('.field_input')){
            t.elem.val('');
        }
        t.getParams();
    };

    JForm.prototype.Field.prototype = {
        constructor: JForm.prototype.Field,
        focus: function(){
            this.elem.addClass('-focus');
            this.removeError();
        },
        blur: function(){
            this.elem.removeClass('-focus');
            if(!this.input.val()) this.holder.show();
        },
        hoverIn: function(){
            this.elem.addClass('-hover');
        },
        hoverOut: function(){
            this.elem.removeClass('-hover');
        },
        refreshCaptcha: function(){
            var url =  this.codeImg[0].src.split('?')[0];
            this.codeImg[0].src = url + '?ts=' + (Math.random() * 1000);
        },
        valInput: function(){
            var t = this;
            t.holder.hide();
            setTimeout(function(){
                if(!t.input.val()) t.holder.show();
            }, 30);
        },
        getParams: function(){
            var t = this, params = {};

            if(t.elem[0].onclick){
                params = t.elem[0].onclick();
                for(var k in params) if(params.hasOwnProperty(k)) t[k] = params[k];
            }
        },
        getValue: function(){
            return this.input.val();
        },
        addError: function(text){
            var t = this,
                pop = $('<div class="field_error">'+ text +'</div>');

            pop.one('click', function(){
                t.removeError();
                t.input.focus();
            });

            t.removeError();
            t.elem.addClass('-error').append(pop);
        },
        removeError: function(){
            var t = this;
            t.elem.removeClass('-error').find('.field_error').off().remove();
        }
    };

    $.fn[_name] = function(options){
        return this.each(function(){
            if (!$.data(this, _name)) {
                $.data(this, _name, new JForm( this, options ));
            }
        });
    };

})(jQuery);

 function JOverlay(elem){
     this.$elem = $(elem);
     this.$popups = this.$elem.find('.b-popup');

     this.showPopup = function(popupID, html){
         var popup = this.$popups.filter('#' + popupID);
         if(!popup.length) return;
         if(html) popup.find('.popup_inner').html(html);
         this.$elem.css('visibility', 'hidden').show();
         popup.show().css('margin-top', - (popup.outerHeight() / 2) + 'px');
         this.$elem.css('visibility', '').hide().fadeIn(300);
     };

     this.hidePopup = function(){
         this.$elem.fadeOut(300, $.proxy(function(){
             this.$popups.hide().css({'margin-top':'','margin-left':''});
         }, this));
     };

     this.$elem.on('click', '.popup_close', $.proxy(this.hidePopup, this));
 }


(function($){

    $.fn.JMediaHeightIe = function(max){
        max = (max || []).sort().reverse();
        var wnd = $(this),body = $('body');
        wnd.on('resize', check);
        check();
        function check(){
            var h = wnd.height(), px = parseInt(body.css('font-size'), 10);
            body[0].className = '';
            for(var i = 0, l = max.length; i < l; i++){
                if(h <= max[i] * px)  body[0].className = 'maxHeight'+ max[i] +'em';
            }
        }
    };

})(jQuery);