/**
 * @constructor
 */
function Slider()
{
    /**
     * Html элементы объекта
     */
    this.elements = {};

    /**
     * Конфигурация объекта
     */
    this.configuration =
    {
        attached : false
    };

    this.sliderValues =
    {
        minimum : 0,
        maximum : 1
    };

    /**
     * Расчитывает положение элемента
     * @param element
     * @returns {{leftX: number,
     *            leftY: number,
     *            rightX: number,
     *            rightY: number}}
     */
    this.calculateElementPosition = function(element)
    {
        var x = 0;
        var y = 0;

        var offsetParent = element;

        while(true)
        {
            x += offsetParent.offsetLeft;
            y += offsetParent.offsetTop;

            if(offsetParent.offsetParent === null)
            {
                break;
            }

            offsetParent = offsetParent.offsetParent;
        }

        return {
            leftX  : x,
            leftY  : y,
            rightX : x + element.offsetWidth,
            rightY : y + element.offsetHeight
        }
    };

    /**
     * Расчитывает координаты события мышки
     * @param event
     * @returns {{positionX: number,
     *            positionY: number}}
     */
    this.calculateMouseEventPosition = function(event)
    {
        event = event || window.event;

        return {
            positionX : event.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft),
            positionY : event.clientY + (document.documentElement.scrollTop  || document.body.scrollTop)
        };
    };

    /**
     * Делает то же что и стандартный, соответственно в некоторых случаях не работает.
     * @param event
     */
    this.preventDefault = function (event)
    {
        event = event || window.event;

        if (event.preventDefault)
        {
            event.preventDefault();
        }
        else
        {
            event.returnValue = false;
        }
    }
}

Slider.prototype =
{
    constructor   : Slider,

    /**
     * Верхний элемент html представления объекта
     */
    parent        : null,

    /**
     * Если в качестве аргумента указан объект - то он возвращается без изменений.
     * Если функция то вычисляется результат её работы, и, если это объект то возвращается он, если нет,
     * кидается ошибка.
     *
     * @param   {Object, Function} argument
     * @returns {*}
     */
    calculateObject : function (argument)
    {
        if (argument)
        {
            if (typeof argument === 'object')
            {
                return argument;
            }

            if (typeof argument === 'function')
            {
                var object = argument.call(this);

                if (!object || (object && typeof object === 'object'))
                {
                    return object;
                }
                else
                {
                    throw new Error('Функция должна возвращать объект.');
                }
            }
        }

        return null;
    },

    /**
     * Позволяет как добавлять новые свойства к объекту configuration так и изменять уже существующие
     * @param   {Object, Function} configuration
     * @returns {*}
     */
    extendConfiguration : function(configuration)
    {
        var object = this.calculateObject(configuration);

        if (object)
        {
            for (var field in object)
            {
                if (object.hasOwnProperty(field))
                {
                    this.configuration[field] = object[field];
                }
            }

            this.onExtendConfiguration();
        }

        return this;
    },

    /**
     * Отрабатывает после метода extendConfiguration
     */
    onExtendConfiguration : function() {},

    /**
     * @param {String} className
     * @param {Node} child
     */
    getElementByClassName : function(className, child)
    {
        if (!this.parent)
        {
            throw new Error('Объект необходимо привязать к html представлению.');
        }

        if (!className || !(typeof className === 'string'))
        {
            throw new Error('Аргумент className является обязательным и должен иметь тип string.');
        }

        var rootElement = (child || this.parent);

        if (rootElement.className)
        {
            var names = rootElement.className.split(' ');

            for (var i = 0; i < names.length; i++)
            {
                if (names[i] == className)
                {
                    return rootElement;
                }
            }
        }

        var element = null;

        if (rootElement.hasChildNodes())
        {
            var nodes = rootElement.childNodes;

            for (var j=0; j<nodes.length; j++)
            {
                element = this.getElementByClassName(className, nodes[j]);

                if (element)
                {
                    break;
                }
            }
        }

        return element;
    },

    /**
     * Привязать js-объект к html представлению
     * @param {HTMLElement} parent
     */
    bind2 : function(parent)
    {
        if (!parent)
        {
            throw new Error('Аргумент функции bind должен быть указан.');
        }

        if (!(parent.nodeType && parent.nodeType == 1))
        {
            throw new Error('Объект можно привязать только к html элементу.');
        }

        this.parent = parent;

        for (var field_1 in this.elements)
        {
            if (this.elements.hasOwnProperty(field_1))
            {
                this.elements[field_1] = this.getElementByClassName(field_1, null);
            }
        }

        /*
         * Проверка соответствия html представления объекту
         */
        for (var field_2 in this.elements)
        {
            if (this.elements.hasOwnProperty(field_2))
            {
                if (!this.elements[field_2])
                {
                    throw new Error('HTML представление не соответствует объекту.');
                }
            }
        }

        this.extendConfiguration({attached: true});
        this.onBind2();
    },

    /**
     * Вызывается после того как отработал метод bind2
     */
    onBind2 : function() {},

    /**
     * Вызывается когда изменяется значение поля ввода
     * @param {Object} value
     */
    valueChangeListener : function(value) {},

    /**
     * Должен возвращать адаптированное значение для объектов наследников.
     * @returns {{}}
     */
    getAdaptiveValue : function()
    {
        return {};
    }
};

/*
 * ------------------------------------------------------------------
 * Далее описание объекта "ползунок" с полосой прогресса
 * ------------------------------------------------------------------
 */

/**
 * @constructor
 */
function ValueSlider()
{
    Slider.call(this);

    this.configuration =
    {
        minimumStep       : 1,
        includeMinimum    : true,
        includeMaximum    : true,
        maximumOnStartup  : true,
        incorrectInput    : /[^(0-9|\.|,)]/,
        formatting        : {
            enabled            : false,
            decimalSeparator   : ',',
            thousandsSeparator : ' ',
            handler            : function() {},
            field              : 'unknown'
        }
    };

    this.elements =
    {
        sliderInput     : null,
        sliderStrip     : null,
        sliderArrow     : null,
        sliderContainer : null,
        fakeSliderInput : null
    };
}

/**
 * @type {Slider}
 */
ValueSlider.prototype = new Slider();

/**
 * @type {*}
 */
ValueSlider.prototype.constructor = ValueSlider;

/**
 * Возвращает значения. Исходя из того, включены ли верхняя или нижняя граница вычитает/прибавляет минимально
 * возможный шаг.
 *
 * @returns {{minimum: number,
 *           maximum: number}}
 */
ValueSlider.prototype.getValues = function ()
{
    return {
        maximum : (this.configuration.includeMaximum) ? this.sliderValues.maximum : this.sliderValues.maximum - this.configuration.minimumStep,
        minimum : (this.configuration.includeMinimum) ? this.sliderValues.minimum : this.sliderValues.minimum + this.configuration.minimumStep
    }
};

/**
 * @param {Object, Function} values
 */
ValueSlider.prototype.extendValues = function(values)
{
    var object = this.calculateObject(values);

    if (object)
    {
        for (var field in object)
        {
            if (object.hasOwnProperty(field))
            {
                if (object[field] && object[field] !== undefined)
                {
                    this.sliderValues[field] = object[field];
                }
            }
        }

        this.onExtendValues();
    }
};

/**
 * Расчитывает координаты для элементов управления исходя из того где произошло собитие
 * @param event
 * @returns {{sliderArrowPosition: number, sliderStripPosition: number}}
 */
ValueSlider.prototype.calculateControlsPositions = function (event)
{
    var mousePosition   = this.calculateMouseEventPosition(event);
    var elementPosition = this.calculateElementPosition(this.elements.sliderContainer);

    var position = mousePosition.positionX - elementPosition.leftX;
    if (position >= 0 && position <= elementPosition.rightX - elementPosition.leftX)
    {
        return {
            sliderArrowPosition : (position - this.elements.sliderArrow.half),
            sliderStripPosition : (position)
        }
    }
    else if (position < 0)
    {
        return {
            sliderArrowPosition : -(this.elements.sliderArrow.half),
            sliderStripPosition : 0
        }
    }
    else
    {
        return {
            sliderArrowPosition : (elementPosition.rightX - elementPosition.leftX - this.elements.sliderArrow.half),
            sliderStripPosition : (elementPosition.rightX - elementPosition.leftX)
        }
    }
};

/**
 * Рассчитывает значение исходя из положения управляющих элементов
 * @param {Object} positions координаты события мышки
 */
ValueSlider.prototype.calculateValueByControlsPositions = function(positions)
{
    var values = this.getValues();

    if (positions)
    {
        /*
         * Рассчитываются размеры контейнера в котором перемещаются стрелка и полоса загрузки
         */
        var position = this.calculateElementPosition(this.elements.sliderContainer);

        /*
         * Высчитывается процент заполнения полосы прогресса
         */
        var percent = (positions.sliderStripPosition * 100 / (position.rightX - position.leftX));

        return Math.round(values.minimum + ((values.maximum - values.minimum) * percent / 100));
    }

    return values.maximum;
};

/**
 * Расчитывает координаты для элементов управления исходя из того какое значение пришло в аргументе
 * @param value
 * @returns {{sliderArrowPosition: number,
 *            sliderStripPosition: number}}
 */
ValueSlider.prototype.calculateControlsPositionsByValue = function(value)
{
    var values          = this.getValues();
    var elementPosition = this.calculateElementPosition(this.elements.sliderContainer);

    if (value <= values.minimum)
    {
        return {
            sliderArrowPosition : - this.elements.sliderArrow.half,
            sliderStripPosition : 0
        }
    }
    else if (value >= values.maximum)
    {
        return {
            sliderArrowPosition : elementPosition.rightX - elementPosition.leftX - this.elements.sliderArrow.half,
            sliderStripPosition : elementPosition.rightX - elementPosition.leftX
        }
    }
    else
    {
        var size       = elementPosition.rightX - elementPosition.leftX;
        var absMaximum = values.maximum - values.minimum;
        var absValue   = value - values.minimum;

        /*
         * Позиция для управляющих элементов
         */
        var position   = Math.round(absValue * size  / absMaximum);

        return {
            sliderArrowPosition : position - this.elements.sliderArrow.half,
            sliderStripPosition : position
        }
    }
};

ValueSlider.prototype.valueChangeListener = function(value)
{
    Slider.prototype.valueChangeListener.call(this, value);

    var config = this.configuration;

    if (config.formatting.enabled)
    {
        if (config.formatting.handler && typeof config.formatting.handler === 'function')
        {
            config.formatting.handler.call(this, this.parent, config.formatting.field);
        }
    }
};

ValueSlider.prototype.getAdaptiveValue = function()
{
    if (!this.configuration.attached)
    {
        throw new Error('Сначало объект необходимо привязать к html.');
    }

    return this.elements.sliderInput.value;
};

ValueSlider.prototype.onBind2 = function()
{
    if (this.configuration.attached)
    {
        this.elements.sliderArrow.half = Math.round(this.elements.sliderArrow.offsetWidth / 2);

        var value     = null;
        var positions = null;

        if (this.configuration.maximumOnStartup)
        {
            value     = this.getValues().maximum;
            positions = this.calculateControlsPositionsByValue(value);
        }
        else
        {
            value     = this.getValues().minimum;
            positions = {
                sliderArrowPosition: -this.elements.sliderArrow.half,
                sliderStripPosition: 0
            };
        }

        this.elements.sliderInput.value       = value;
        this.elements.fakeSliderInput.value   = value;
        this.elements.sliderArrow.style.left  = positions.sliderArrowPosition + 'px';
        this.elements.sliderStrip.style.width = positions.sliderStripPosition + 'px';

        this.valueChangeListener({source: value, number: value});
    }
};

/**
 * Вызывается после того как отработал метод extendValues
 */
ValueSlider.prototype.onExtendValues = function()
{
    this.onBind2();
};

ValueSlider.prototype.onExtendConfiguration = function()
{
    this.onBind2();
};

/**
 * @param value
 */
ValueSlider.prototype.checkValue = function(value)
{
    var object = {
        source : value,
        number : value
    };

    if (value)
    {
        var thousandsToken = new RegExp('\\' + this.configuration.formatting.thousandsSeparator, 'g');
        var decimalToken   = new RegExp('\\' + this.configuration.formatting.decimalSeparator,   'g');

        object.number = value.replace(decimalToken,   '.')
                .replace(thousandsToken, '');

        if (isNaN(object.number))
        {
            if (this.configuration.incorrectInput.test(value))
            {
                object.source = '';
                object.number = '';
            }
        }
    }

    return object;
};

ValueSlider.prototype.onkeyup = function(event, val)
{
    var value      = this.checkValue(val);
    var calculated = null;

    if (!value.number)
    {
        calculated = {
            sliderArrowPosition: -this.elements.sliderArrow.half,
            sliderStripPosition: 0
        };
    }
    else
    {
        calculated = this.calculateControlsPositionsByValue(value.number);
    }

    this.elements.sliderInput.value       = value.number;
    this.elements.sliderArrow.style.left  = calculated.sliderArrowPosition + 'px';
    this.elements.sliderStrip.style.width = calculated.sliderStripPosition + 'px';

    this.valueChangeListener(value);
};

ValueSlider.prototype.onblur  = function(event, val)
{
    var value  = this.checkValue(val);
    var values = this.getValues();

    if (!value.number || value.number > values.maximum)
    {
        value.number = values.maximum;
        value.source = values.maximum;
    }
    else if (value.number < values.minimum)
    {
        value.number = values.minimum;
        value.source = values.minimum;
    }

    var calculated = this.calculateControlsPositionsByValue(value.number);

    this.elements.sliderInput.value       = value.number;
    this.elements.fakeSliderInput.value   = value.source;
    this.elements.sliderArrow.style.left  = calculated.sliderArrowPosition + 'px';
    this.elements.sliderStrip.style.width = calculated.sliderStripPosition + 'px';

    this.valueChangeListener(value);
};

/**
 * @param event
 */
ValueSlider.prototype.onmousedownContainer = function(event)
{
    if ((event.which && event.which == 3) || (event.button && event.button == 2))
    {
        return;
    }

    var elementPositions    = this.calculateControlsPositions(event);
    var calculatedValue     = this.calculateValueByControlsPositions(elementPositions);
    var calculatedPositions = this.calculateControlsPositionsByValue(calculatedValue);

    this.elements.sliderInput.value       = calculatedValue;
    this.elements.fakeSliderInput.value   = calculatedValue;
    this.elements.sliderArrow.style.left  = calculatedPositions.sliderArrowPosition + 'px';
    this.elements.sliderStrip.style.width = calculatedPositions.sliderStripPosition + 'px';

    this.valueChangeListener(calculatedValue);
};

/**
 * @param event
 */
ValueSlider.prototype.onmousedownArrow = function(event)
{
    document.sliderHolder = this;

    /* Устраняет залипания */
    this.preventDefault(event);
};

/**
 * @param event
 */
ValueSlider.prototype.onmousemove = function(event)
{
    var holder = document.sliderHolder;

    if (holder && holder !== undefined)
    {
        var positions       = holder.calculateControlsPositions(event);
        var calculatedValue = holder.calculateValueByControlsPositions(positions);

        holder.elements.sliderInput.value       = calculatedValue;
        holder.elements.fakeSliderInput.value   = calculatedValue;
        holder.elements.sliderArrow.style.left  = positions.sliderArrowPosition + 'px';
        holder.elements.sliderStrip.style.width = positions.sliderStripPosition + 'px';

        holder.valueChangeListener( calculatedValue );

        /* Устраняет залипания */
        holder.preventDefault(event);
    }
};

/**
 * @param event
 */
ValueSlider.prototype.onmouseup = function(event)
{
    var holder = document.sliderHolder;

    if (holder && holder !== undefined)
    {
        var positions = holder.calculateControlsPositionsByValue(holder.elements.sliderInput.value);
        holder.elements.sliderArrow.style.left  = positions.sliderArrowPosition + 'px';
        holder.elements.sliderStrip.style.width = positions.sliderStripPosition + 'px';

        document.sliderHolder = null;
    }
};

ValueSlider.prototype.bind2 = function (parent)
{
    var _this  = this;
    var _event = function(event)
    {
        return (event && event !== undefined) ? event : window.event;
    };

    /*
     * Вызывается метод родителя
     */
    Slider.prototype.bind2.call(this, parent);

    /*
     * Устнановка необходимых свойств и обработчиков
     */
    this.elements.fakeSliderInput.onkeyup = function(event)
    {
        _this.onkeyup(_event(event), this.value);
    };

    this.elements.fakeSliderInput.onblur  = function(event)
    {
        _this.onblur(_event(event), this.value);
    };

    this.elements.sliderContainer.onmousedown = function(event)
    {
        _this.onmousedownContainer(_event(event));
    };

    this.elements.sliderArrow.onmousedown = function(event)
    {
        _this.onmousedownArrow(_event(event));
    };

    document.body.onmousemove = function(event)
    {
        _this.onmousemove(_event(event));
    };

    document.body.onmouseup = function(event)
    {
        _this.onmouseup(_event(event));
    };
};

delete ValueSlider.prototype.elements;
delete ValueSlider.prototype.sliderValues;
delete ValueSlider.prototype.configuration;

/**
 * @constructor
 */
function DateSlider()
{
    ValueSlider.call(this);
}

/**
 * @type {ValueSlider}
 */
DateSlider.prototype = new ValueSlider();

/**
 * @type {*}
 */
DateSlider.prototype.constructor = DateSlider;

/**
 * Возвращает значение поля в виде объекта со свойствами:
 * years  - количество лет,
 * months - количество месяцев.
 *
 * Например, если в поле введено 15 то метод вернёт объект {years: 1, months: 3}
 *
 * @returns {{years: number,
 *            months: number}}
 */
DateSlider.prototype.getAdaptiveValue = function ()
{
    var value  = ValueSlider.prototype.getAdaptiveValue.call(this) >> 0;

    return {
        years  : (value / 12) >> 0,
        months : (value % 12)
    }
};

delete DateSlider.prototype.elements;
delete DateSlider.prototype.sliderValues;
delete DateSlider.prototype.configuration;

/**
 * @constructor
 */
function HidedArrowSlider()
{
    ValueSlider.call(this);

    this.extendConfiguration({ arrowDisabled: false });
}

/**
 * @type {ValueSlider}
 */
HidedArrowSlider.prototype = new ValueSlider();

/**
 * @type {*}
 */
HidedArrowSlider.prototype.constructor = HidedArrowSlider;

/**
 * Поскольку для данных объектов есть возможность скрытия стрелки, нужно блокировать обычное поведение объекта
 * при наступлении данного события в случае скрытой стрелки.
 * @param event
 * @param val
 */
HidedArrowSlider.prototype.onblur = function(event, val)
{
    if (this.configuration.arrowDisabled)
    {
        var value = this.checkValue(val);

        this.elements.sliderInput.value     = value.number;
        this.elements.fakeSliderInput.value = value.source;
        this.valueChangeListener(value);
        return;
    }

    ValueSlider.prototype.onblur.call(this, event, val);
};

HidedArrowSlider.prototype.onkeyup = function(event, val)
{
    if (this.configuration.arrowDisabled)
    {
        var value = this.checkValue(val);

        this.elements.sliderInput.value     = value.number;
        this.valueChangeListener(value);
        return;
    }

    ValueSlider.prototype.onkeyup.call(this, event, val);
};

HidedArrowSlider.prototype.onBind2 = function()
{
    if (this.configuration.arrowDisabled)
    {
        this.elements.sliderInput.value     = '';
        this.elements.fakeSliderInput.value = '';

        return;
    }

    ValueSlider.prototype.onBind2.call(this);
};

/**
 * Здесь добавлена проверка настройки регулирующей отображение стрелки.
 * Если установлено, что стрелка должна быть скрыта - она скрывается.
 */
HidedArrowSlider.prototype.onExtendConfiguration = function()
{
    if (this.configuration.attached)
    {
        if (this.configuration.arrowDisabled)
        {
            this.elements.sliderInput.value = '';
            this.elements.sliderContainer.style.display = 'none';
        }
        else
        {
            this.elements.sliderContainer.style.display = '';
        }

        ValueSlider.prototype.onExtendConfiguration.call(this);
    }
};

delete HidedArrowSlider.prototype.elements;
delete HidedArrowSlider.prototype.sliderValues;
delete HidedArrowSlider.prototype.configuration;