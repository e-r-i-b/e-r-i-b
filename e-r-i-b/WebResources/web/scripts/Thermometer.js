var thermometerManager = {
    thermometersHash : {},
    addThermometer : function(thermometer)
    {
        this.thermometersHash[thermometer.id] = thermometer;
    },
    getThermometer : function(id)
    {
        return this.thermometersHash[id];
    }
};

function Thermometer(id,value,maxValue,showDribbleHint,color)
{
    this.id = id;
    this.value = value;
    this.maxValue = maxValue;
    this.showDribbleHint = showDribbleHint == undefined ? true : showDribbleHint;
    this.color = color == undefined ? 'green' : color;

    this.setValue = function(value)
    {
        this.value = value;
    };

    this.setMaxValue = function(maxValue)
    {
        this.maxValue = maxValue;
    };

    this.setColor = function(color)
    {
        this.color = color;
    };
    
    this.scale = function()
    {
        var scale = $('#thermometer' + id + ' .thermometerScale');
        scale.attr('fullWidth', scale.width());
    };
    /**
     * –асчитывает ширину внутренних блоков "градусника"     
     */
    this.calculateWidths = function()
    {
        var thermometer = $('#thermometer' + this.id);
        var fullWidth = thermometer.width();

        var thermometerBg = $(thermometer).find('.thermometerBg');
        var bdSidesWidth = $(thermometerBg).find('.thermometerRight').width() + $(thermometerBg).find('.thermometerLeft').width();
        $(thermometerBg).find('.thermometerCenter').width(fullWidth - bdSidesWidth);

        var thermometerScaleBg = $(thermometer).find('.thermometerScaleBg');
        var bdScaleSidesWidth = $(thermometerScaleBg).find('.thermometerRight').width() + $(thermometerScaleBg).find('.thermometerLeft').width();
        $(thermometerScaleBg).find('.thermometerCenter').width(fullWidth - bdSidesWidth - bdScaleSidesWidth);

        var thermometerScale = $(thermometer).find('.thermometerScale');
        var scaleSidesWidth = $(thermometerScale).find('.thermometerRight').width() + $(thermometerScale).find('.thermometerLeft').width();
        $(thermometerScale).find('.thermometerCenter').width(fullWidth - bdSidesWidth - scaleSidesWidth - 4);
    };
    /**
     * ѕерерисовка шкалы компонента "√радусник"
     */
    this.redraw = function()
    {
        var thermometer = $('#thermometer' + this.id);
        var scale = thermometer.find('.thermometerScale');
        var hint = thermometer.find('.dribbleHint');
        if (this.value < 0) // шкалу пр€чем
        {
            scale.hide();
            hint.hide();
            return;
        }

        scale.show();
        thermometer.removeClass('redThermometer greenThermometer orangeThermometer').addClass(this.color+'Thermometer');
        var scalePercent = this.value / this.maxValue;
        var newScaleWidth = Math.round(scalePercent * scale.attr('fullWidth')); // новое значение длины шкалы
        var sidesWidth = scale.find('.thermometerRight').width() + scale.find('.thermometerLeft').width(); // длина уголков шкалы
        var newScaleCenterWidth = (newScaleWidth - sidesWidth) > 0 ? (newScaleWidth - sidesWidth) : 0 ;

        scale.find('.thermometerCenter').width(newScaleCenterWidth);

        if (this.showDribbleHint)
        {
            // пересчитываем ширину капельки
            var hintCenter = hint.find('.dribbleCenter');
            var hintSpan =  hint.find('span');
            hint.show();
            hint.css('width', 'auto');
            hintSpan.text(FloatToString(this.value, $('#ShowCents').is(':checked') ? 2 : 0, ' ', 21, RoundingMode.DOWN));
            hint.css('width', hintCenter.innerWidth());
            hint.css('left', newScaleCenterWidth);
        }
        else
        {
            hint.hide();
        }
    };

    this.addMouseenter = function(enterScript)
    {
        var scale = $('#thermometer' + this.id + ' .thermometerScale');
        scale.unbind("mouseenter");
        scale.bind("mouseenter",
            function(){enterScript();}
        );
    };

    this.addMouseleave = function(leaveScript)
    {
        var scale = $('#thermometer' + this.id + ' .thermometerScale');
        scale.unbind("mouseleave");
        scale.bind("mouseleave",
            function(){leaveScript();}
        );
    };
}