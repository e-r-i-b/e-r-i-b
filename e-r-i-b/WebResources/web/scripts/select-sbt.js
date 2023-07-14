doOnLoad (function()
        {
        (function($){

            var ie = (function(d, w){
                        return !d.all ? false : w.atob ? 10 : d.addEventListener ? 9 : d.querySelector ? 8 : w.XMLHttpRequest ? 7 : d.compatMode ? 6 : 'older';
                    })(document, window),

                        scBarWidth = (function(){
                            var el = $('<div style="position:absolute;"><div style="overflow-y:scroll;"/></div>').appendTo('body'), w = el.width() || 17;
                            el.remove();
                            return w;
                        })(),

                        htmlSelect = '<div class="b-select"><i class="select_arr"></i><a class="select_text" tabindex="0"></a></div>',
        htmlDrop = [
                        '<div class="b-select-drop" tabindex="0">',
                        '<div class="select-drop_inner"'+(scBarWidth ? 'style="margin-right:-'+ scBarWidth +'px;"':'')+'><!-- items --></div>',
                        '<div class="select-drop_bar"><div class="select-drop_run"></div></div>',
                        '</div>'
                    ].join('');


            function JSelect(elem, options){
                this.cfg = $.extend({
                    clNames: '',
                    maxHeight: 250
                }, options);

                if(elem.nodeName == 'SELECT'){
                    this.$elem = $(htmlSelect).insertAfter(elem).append($(elem).addClass('select_orig'));
                } else {
                    this.$elem = $(elem);
                }

                this.$text = this.$elem.find('.select_text');
                this.$orig = this.$elem.find('.select_orig');
                this.$drop = null;
                this.expanded = false;

                this.init();
            }

            JSelect.prototype = {
                constructor: JSelect,
                $domelem: $(document.documentElement),
                init: function(){
                    this.$text.text(this.$orig.find('option:selected').text());
                    this.$elem.addClass(this.cfg.clNames);

                    this.$text.bind('mousedown', $.proxy(this.expand, this));
                    this.$text.bind('focus', $.proxy(this.focus, this));
                    this.$orig.bind('change', $.proxy(this.change, this));
                },
                focus: function(){
                    this.$elem.addClass('-focus');
                    this.actionsOn();
                },
                expand: function(){
                    if(this.$drop) return;
                    this.createDrop();
                    this.actionsOn();
                    this.showDrop();
                    this.expanded = true;
                    this.$domelem.trigger('select:open', this);
                    this.$domelem.bind('select:open', $.proxy(this.check, this));
                    this.$domelem.bind('mousedown', $.proxy(this.check, this));
                },
                collapse: function(){
                    this.hideDrop();
                    this.expanded = false;
                    this.$elem.removeClass('-focus');
                    this.$text.unbind('select:open', $.proxy(this.check, this));
                    this.$text.unbind('mousedown', $.proxy(this.check, this));
                },
                check: function(e, sel){
                    if(sel && this === sel) return;
                    if(
                            this.$elem[0] !== e.target &&
                                    this.$elem.children('' + e.target.nodeName)[0] !== e.target
                            ) this.collapse();
                },
                change: function(){
                    this.$text.text(this.$orig.find('option:selected').text());
                    this.hideDrop();
                },
                createDrop: function(){
                    var t = this,
                            options = this.$orig[0].options,
                            elemPos = this.$elem.offset(),
                            htmlItems = '',
                            styles = {
                                top: this.$elem.height(),
                                left: 0,
                                fontSize: this.$elem.css('font-size'),
                                width: this.$elem.outerWidth() - 2
                            }, a;

                    for(var i = 0, l = options.length; i < l; i++){
                        htmlItems += '<a class="select_drop-item'+ (options[i].selected ? ' selected' : '') + (options[i].disabled ? ' disabled"' : '" rel="' + options[i].value +'" tabindex="0"') +'>'+ options[i].text +'</a>';
                    }

                    this.$drop = $(htmlDrop.replace('<!-- items -->', htmlItems)).css(styles);
                    this.$drop.find('.select-drop_inner').css('max-height', this.cfg.maxHeight);

                    this.$drop.bind('mousedown', function(e){
                        e.stopPropagation();
                        e.preventDefault();
                        if(e.target.nodeName == 'A' && e.target.rel){
                            t.$orig.val(e.target.rel).change();
                        }
                    });
                },
                showDrop: function(){
                    this.$drop.appendTo(this.$elem);
                    this.$elem.addClass('-expanded');
                    this.initScroll(this.$drop);
                },
                hideDrop: function(){
                    if(!this.$drop) return;
                    this.$drop = !this.$drop.remove();
                    this.$elem.removeClass('-expanded');
                },
                actionsOff: function(){
                    this.$text.unbind('keydown mouseup');
                    $(window).unbind('resize', $.proxy(this.actionsOff, this));
                },
                actionsOn: function(){
                    var t = this;

                    this.actionsOff();

                    this.$text.bind('keydown', $.proxy(function(e){
                        var cur, val;
                        if(e.which == 32 && !this.$drop) {
                            this.hideDrop();
                            this.expand();
                            return false;
                        }
                        if(e.which == 27 && this.$drop) {
                            this.hideDrop();
                            return false;
                        }
                        if(e.which == 40 && !this.$drop) {
                            cur = this.$orig.find('option:selected');
                            val = cur.next().length ? cur.next().val() : this.$orig.find('option:first-child').val();
                            t.$orig.val(val).change();
                            return false;
                        }
                        if(e.which == 38 && !this.$drop) {
                            cur = this.$orig.find('option:selected');
                            val = cur.prev().length ? cur.prev().val() : this.$orig.find('option:last-child').val();
                            t.$orig.val(val).change();
                            return false;
                        }
                    }, this));

                    $(window).bind('resize', $.proxy(this.collapse, this));
                },
                initScroll: function($drop){
                    var $list = $drop.find('.select-drop_inner'),
                            height = $list.height(),
                            scHeight = $list[0].scrollHeight,
                            body = $('body'),
                            scroll = 0,
                            barHeight, runHeight, $bar, $runner, startY;

                    if(scHeight > height) {
                        $bar = $drop.find('.select-drop_bar').show();
                        barHeight = $bar.height();
                        runHeight = Math.round(height / scHeight * barHeight);
                        $runner = $drop.find('.select-drop_run').height(runHeight);

                        function barMove(e){
                            var distance = e.pageY - startY;
                            $list.scrollTop(((distance / barHeight) * (scHeight - height)) + scroll);
                            return false;
                        }

                        function clearMove(){
                            //scroll = $list.scrollTop();
                            body.unbind('mouseup mouseleave', clearMove).unbind('mousemove', barMove);
                        }

                        $list.bind('scroll', function(){
                            $runner.css('top', ($list.scrollTop() / (scHeight - height)) * (barHeight - runHeight));
                        });

                        $bar.bind('mousedown', function(e){
                            startY = e.pageY;
                            scroll = $list.scrollTop();
                            body.bind('mousemove', barMove).bind('mouseup mouseleave', clearMove);
                            return false;
                        });
                    }
                }
            };


            $.fn.jSelect = function(options){
                return this.each(function(){

                    if (!$.data(this, 'jSelect') && (this.nodeName == 'SELECT' || /b-select/gi.test(this.className))) {
                        $.data(this, 'jSelect', new JSelect( this, options ));
                    }

                });
            };

        })(jQuery);

        if (isMobileDevice()) return; //забота о мобильных устройствах
        $('.selectSbtM').jSelect({ clNames: '_size-m'});
        $('.selectSbtS').jSelect({ clNames: '_size-s'});
    }
);


