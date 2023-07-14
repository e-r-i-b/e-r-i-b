(function($){

    var _pname = 'jDebtsScroller',

    defaults = {};

    function JDebtsScroller($elem, options){
        this.cfg = $.extend(defaults, options);

        this.$elem = $($elem);
        this.$body = this.$elem.find('.debts_body');
        this.$scroll = this.$elem.find('.debts_scroll');
        this.$doc = $(document.documentElement);

        this.createScroll();
    }

    JDebtsScroller.prototype = {
        constructor: JDebtsScroller,
        createScroll: function(){
            this.$bar = this.$elem.find('.debts_bar');
            this.$thumbs = this.$elem.find('.debts_thumbs');
            this.$run = this.$elem.find('.debts_run');

            this.createThumbs();
            this.initScroll();
        },
        createThumbs: function(){
            var thumbsHTML = '', tr, cell, isMonth, clName, amount, isLoan;
            var text;
            this.$body.find('tr').each(function(){
				tr = $(this);
                cell = tr.find('.debts_cell').eq(0);
                isMonth = cell.hasClass('month');
                isLoan = tr.attr('class').indexOf("loan_row") > -1;
                text = isMonth && isLoan ? cell.text().split(' ')[1].substring(0,3) : cell.text();
                clName = tr.attr('class').replace(/(debts_year|debts_row|loan_row)/, '');
                amount = tr.find('.cred-hist-report_b-month-p').eq(0);
                if (isMonth && amount.text() == "")
                    tr.remove();

                if (isLoan)
                    thumbsHTML += '<div class="cred-hist-report-scroller-'+ (isMonth ? 'month' : 'year') + '">'+ text +'</div>';
                else
                    thumbsHTML += '<div class="debts_'+ (isMonth ? 'm' : 'y') + clName +'">'+ text +'</div>';
            });
            this.$thumbs.html(thumbsHTML);
            this.$bar.height(this.$bar.height());
        },
        initScroll: function(){
            var t = this;

            t.scrollScH = t.$scroll[0].scrollHeight;

            t.height = t.$scroll.outerHeight();
            t.maxScroll = t.scrollScH - t.height;
            t.scroll = t.$scroll.scrollTop();

            t.thumbsHeight = t.$thumbs.height();
            t.thumbsScH = t.$thumbs[0].scrollHeight;
            t.thumbsMaxSc = t.thumbsScH - t.thumbsHeight;
			
			t.barHeight = t.$bar.height();
            t.runHeight = Math.ceil(t.height / t.scrollScH * (t.thumbsScH));

            t.$run.height(t.runHeight);

            t.mouseWHevt = 'onwheel' in document ? 'wheel' :
                            window.MouseScrollEvent ? 'DOMMouseScroll' :
                           'mousewheel';


                t.$scroll.bind('scroll', function(){
                t.runScroll = Math.ceil((t.$scroll.scrollTop() / t.maxScroll) * (t.barHeight - t.runHeight));
                t.thumbsSc = Math.ceil((t.$scroll.scrollTop() / t.maxScroll) * t.thumbsMaxSc);
                t.$run.css({top: t.runScroll});
                t.$thumbs.scrollTop(t.thumbsSc);
            });

            t.$run.bind('mousedown', function(e){
                t.startY = e.pageY;
                t.scroll = t.$scroll.scrollTop();

                t.$doc.bind('mousemove', move);
                t.$doc.bind('mouseup mouseleave', clear);

                return false;
            });

            this.$bar.bind(t.mouseWHevt, function(e){
                var ev = e.originalEvent,
                    delta = ev.deltaY || ev.detail || ev.wheelDelta;

                delta = (Math.abs(delta) < 30 && !ev.detail) ? Math.abs(delta)/delta * 100 : delta;

                console.log(delta);

                t.$scroll.scrollTop(t.$scroll.scrollTop() + delta);
                return false;
            });

            function move(e){
                var ev;
                console.log(e);
                var posY = (e.pageY - t.startY) / (t.barHeight - t.runHeight) * t.maxScroll;
                console.log('>>>>>', posY, t.scroll+' = '+t.$scroll.scrollTop(), t.startY, e.pageY);
                t.$scroll.scrollTop(posY + t.scroll);

                return false;
            }

            function clear(){
                t.scroll = t.$scroll.scrollTop();
                t.$doc.unbind('mousemove', move);
                t.$doc.unbind('mouseup mouseleave', clear);
            }
        }
    };
    $.fn[_pname] = function(options){
        return this.each(function(){
            if(!$.data(this, 'jDebtsScroller')){
                $.data(this, 'jDebtsScroller', new JDebtsScroller(this, options));
            }
        });
    };

})(jQuery);