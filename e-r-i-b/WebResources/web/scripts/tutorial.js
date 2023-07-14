function Tutorial(config){
    var t = this, body,
        cursor = {
            cursorTimer: null,
            lastX: 0,
            lastY: 0,
            hovered: false,
            disabled: false
        };

    t.element = null;
    t.visible = false;

    t.options = {
        animationSpeed: 150, /* config.animationSpeed скорость анимации показа\скрытия туториала */
        showTrigger: null, /* config.showTrigger селектор jq, нода по клику на которую показываем туториал */
        removeTrigger: null, /* config.removeTrigger селектор jq, нода по клику на которую УДАЛЯЕМ туториал полностью */
        showOnInit: false, /* config.showOnInit == true для показа туториала сразу после инициализации */
        current: 0 /* config.current - индекс текущего показываемого шага (0 - первый) */
    };

    t.init = function(config){
        if(t.element && t.element.length) t.element.remove();
        $.extend(t.options, config);

        body = $('body');

        if(t.options.tutorialHTML){
            t.element = drawTutorial(t.options.tutorialHTML);
        } else if(t.options.tutorialElem){
            t.element = $(t.options.tutorialElem);
        }

        t.elems = {
            "steps": t.element.find('.tutorial_step'),
            "exit": t.element.find('.tutorial_exit'),
            "linkElems": t.element.find('a'),
            "cursor": t.element.find('.tutorial_cursor')
        };

        t.stepsSize = t.elems['steps'].length - 1;
        if(t.options.showOnInit) t.show();
        initListeners();
    };

    t.next = function(){
        if (t.options.current === 1) {
            showAvatarLoadButton();
        }
        else if (t.options.current === 2) {
            hideAvatarLoadButton();
        }
        if(t.options.current === t.stepsSize){
            t.options.current = 0;
            t.hide();
            showMinimizedPromo();
            return;
        }

        t.options.current++;
        showStep(t.options.current);
    };

    t.show = function(index, callback){
        showStep(index);
        if(!t.options.visible){
            t.options.visible = true;
            t.element.height($(document).height());
            //если браузер IE8, то fadeIn не используем
            if (isIE(8))
                t.element.show(callback);
            else
                t.element.fadeIn(t.options.animationSpeed, callback);
            enableCursor();
        }
    };

    t.hide = function(callback){
        t.options.visible = false;
        disableCursor();
        //если браузер IE8, то fadeOut не используем
        if (isIE(8))
            t.element.hide(callback);
        else
            t.element.fadeOut(t.options.animationSpeed, callback);
    };

    t.remove = function(){
        if(!t.options.visible) remove();
        else t.hide(remove);

        function remove(callback){
            $(t.options.showTrigger).unbind('click');
            $(t.options.removeTrigger).unbind('click');
            t.element.remove();
            for(var k in t) if(t.hasOwnProperty(k)) delete t[k];
            if(callback && typeof callback == 'function') callback();
        }
    };

    function enableCursor(){
        cursor.disabled = false;
        t.element.bind('mousemove', function(e){ setCursorPos(e.clientX, e.clientY) });
        t.elems['cursor'].bind('mouseleave', function(){ t.elems['cursor'].fadeOut(100); cursor.hovered = false; });
        t.elems['cursor'].bind('mouseenter', function(){ cursor.hovered = true; });
    }

    function disableCursor(){
        cursor.disabled = true;
        t.element.unbind('mousemove');
        clearTimeout(cursor.cursorPosTimer);
        clearTimeout(cursor.cursorTimer);
        t.elems['cursor'].fadeOut(100);
    }

    function showStep(index){
        if(typeof index === 'number'){
            t.options.current = index > t.stepsSize ? t.stepsSize : index < 0 ? 0 : index;
        }
        checkImageLoad(t.elems['steps'].eq(t.options.current));
        t.elems['steps'].addClass('hidden').eq(t.options.current).removeClass('hidden');
    }

    function drawTutorial(tutorialHTML){
        return $(tutorialHTML).appendTo(body);
    }

    function initListeners(){
        t.element.click(function(){ t.next() });
        t.elems['exit'].click(function(){ t.hide(); t.options.current--; });
        $(t.options.showTrigger).bind('click', function(){ t.show() });
        $(t.options.removeTrigger).bind('click', function(){ t.remove() });
        t.elems['linkElems'].bind('mouseenter', function(){ disableCursor() });
        t.elems['linkElems'].bind('mouseleave', function(){ enableCursor() });
        t.element.bind('mouseleave', function(){ disableCursor() });
        t.element.bind('mouseenter', function(){ enableCursor() });
    }

    function setCursorPos(x, y){
        if(cursor.disabled) return;

        clearTimeout(cursor.cursorPosTimer);
        clearTimeout(cursor.cursorTimer);
        cursor.cursorPosTimer = setTimeout(function(){

            if(cursor.hovered) return;

            t.elems['cursor'].hide();

            cursor.cursorTimer = setTimeout(function(){
                t.elems['cursor'].css({left: x, top: y}).fadeIn(300);
                cursor.lastX = x;
                cursor.lastY = y;
            }, 800);

        }, 100);
    }

    function checkImageLoad(step){
        if(step.data('loaded') === true) return;

        var img = step.find('img'),
            imgObj = new Image();

        imgObj.src = img[0].src;

        if(!imgObj.complete){
            step.addClass('__loading');
            imgObj.onload = function(){
                step.removeClass('__loading __error');
                step.data('loaded', true);
            };
            imgObj.onerror = function(){
                step.removeClass('__loading').addClass('__error')
            };
        } else step.data('loaded', true);
    }

    if(config) t.init(config);
}
