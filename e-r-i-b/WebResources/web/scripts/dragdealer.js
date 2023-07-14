/**
 * Dragdealer JS v0.9.5
 * http://code.ovidiu.ch/dragdealer-js
 *
 * Copyright (c) 2010, Ovidiu Chereches
 * MIT License
 * http://legal.ovidiu.ch/licenses/MIT
 */

/* Cursor */

var ARRAY_DESCR_quarter = ['1 квартал', '2 квартал', '3 квартал', '4 квартал'];
var ARRAY_DESCR_month = ['январь', 'февраль', 'март', 'апрель', 'май', 'июнь', 'июль', 'август', 'сентябрь', 'октябрь', 'ноябрь', 'декабрь'];

var Cursor =
{
	x: 0, y: 0,
	init: function()
	{
		if (this.inited) return;

        this.setEvent('mouse');
		this.setEvent('touch');
        this.inited = true;
	},
	setEvent: function(type)
	{
		var moveHandler = document['on' + type + 'move'] || function(){};
		document['on' + type + 'move'] = function(e)
		{
			moveHandler(e);
			Cursor.refresh(e);
		}
	},
	refresh: function(e)
	{
		if(!e)
		{
			e = window.event;
		}
		if(e.type == 'mousemove')
		{
			this.set(e);
		}
		else if(e.touches)
		{
			this.set(e.touches[0]);
		}
	},
	set: function(e)
	{
		if(e.pageX || e.pageY)
		{
			this.x = e.pageX;
			this.y = e.pageY;
		}
		else if(e.clientX || e.clientY)
		{
			this.x = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
			this.y = e.clientY + document.body.scrollTop + document.documentElement.scrollTop;
		}
	}
};

/* Position */

var Position =
{
	get: function(obj)
	{
		var curleft = curtop = 0;
		if(obj.offsetParent)
		{
			do
			{
				curleft += obj.offsetLeft;
				curtop += obj.offsetTop;
			}
			while((obj = obj.offsetParent) && !$(obj).hasClass("window"));
		}
        if ($(obj).hasClass("window"))
        {
            curleft += obj.offsetLeft;
            curtop += obj.offsetTop;
        }
		return [curleft, curtop];
	}
};

/* Dragdealer */

var Dragdealer = function(wrapper, options, handleId)
{
    Cursor.init();

    if(typeof(wrapper) == 'string')
	{
		wrapper = document.getElementById(wrapper);
	}
	if(!wrapper)
	{
		return;
	}

    var handle;
    if (handleId)
        handle = document.getElementById(handleId);
    else
	    handle = document.getElementById(wrapper.id + 'Inner');

	if(!handle)
	{
		return;
	}
	this.init(wrapper, handle, options || {});
	this.setup();
};
Dragdealer.prototype =
{
	init: function(wrapper, handle, options)
	{
		this.wrapper = wrapper;
		this.handle = handle;
		this.options = options;

		this.disabled = this.getOption('disabled', false);
		this.horizontal = this.getOption('horizontal', true);
		this.vertical = this.getOption('vertical', false);
		this.slide = this.getOption('slide', true);
        this.xStep = this.getOption('xStep', 0);
        this.yStep = this.getOption('yStep', 0);
		this.snap = this.getOption('snap', false);
		this.loose = this.getOption('loose', false);
		this.speed = this.getOption('speed', 0) / 100;
		this.xPrecision = this.getOption('xPrecision', 0);
		this.yPrecision = this.getOption('yPrecision', 0);
        this.fieldName = this.getOption('fieldName','field(scroll)');//name поля привязанного к ползунку
        this.minValue = this.getOption('minValue',0);
        this.maxValue = this.getOption('maxValue',100);
        this.step = this.getOption('step', 1);//шаг по сетке
        this.round = this.getOption('round', 0);//шаг по сетке
        this.yMinPossibleVal = this.getOption('yMinPossibleVal', this.minValue);
        this.yMaxPossibleVal = this.getOption('yMaxPossibleVal', this.maxValue);

		this.callback = options.callback || null;
		this.animationCallback = options.animationCallback || null;

		this.yInterval = [this.getYMinPossibleVal(), this.getYMaxPossibleVal()];
        this.bounds = {
			left: options.left || 0, right: -(options.right || 0),
			top: options.top || 0, bottom: -(options.bottom || 0),
			x0: 0, x1: 0, xRange: 0,
			y0: 0, y1: 0, yRange: 0
		};
		this.value = {
			prev: [-1, -1],
			current: [options.x || 0, options.y || 0],
			target: [options.x || 0, options.y || 0]
		};
		this.offset = {
			wrapper: [0, 0],
			mouse: [0, 0],
			prev: [-999999, -999999],
			current: [0, 0],
			target: [0, 0]
		};
		this.change = [0, 0];

		this.activity = false;
		this.dragging = false;
		this.tapping = false;
	},
    getYMinPossibleVal: function(name, defaultValue)
	{
        var yMinPossibleVal = (this.yMinPossibleVal - this.minValue)/(this.maxValue - this.minValue);
        if (yMinPossibleVal > 0 && this.snap && this.yStep > 1)
             yMinPossibleVal = Math.round(yMinPossibleVal*(this.yStep - 1))/(this.yStep - 1);
        return yMinPossibleVal;
	},
    getYMaxPossibleVal: function(name, defaultValue)
	{
        var yMaxPossibleVal = (this.yMaxPossibleVal - this.minValue)/(this.maxValue - this.minValue);
        if (yMaxPossibleVal > 0 && this.snap && this.yStep > 1)
             yMaxPossibleVal = Math.round(yMaxPossibleVal*(this.yStep - 1))/(this.yStep - 1);
        return yMaxPossibleVal;
	},
	getOption: function(name, defaultValue)
	{
		return this.options[name] !== undefined ? this.options[name] : defaultValue;
	},
	setup: function()
	{
		this.setWrapperOffset();
		this.setBoundsPadding();
		this.setBounds();

		this.addListeners();
	},
	setWrapperOffset: function()
	{
		this.offset.wrapper = Position.get(this.wrapper);
	},
	setBoundsPadding: function()
	{
		if(!this.bounds.left && !this.bounds.right)
		{
			this.bounds.left = Position.get(this.handle)[0] - this.offset.wrapper[0];
			this.bounds.right = -this.bounds.left;
		}
		if(!this.bounds.top && !this.bounds.bottom)
		{
			this.bounds.top = Position.get(this.handle)[1] - this.offset.wrapper[1];
			this.bounds.bottom = -this.bounds.top;
		}
	},
	setBounds: function()
	{
		this.bounds.x0 = this.bounds.left;
		this.bounds.x1 = this.wrapper.offsetWidth + this.bounds.right;
		this.bounds.xRange = (this.bounds.x1 - this.bounds.x0) - this.handle.offsetWidth;

		this.bounds.y0 = this.bounds.top;
		this.bounds.y1 = this.wrapper.offsetHeight + this.bounds.bottom;
		this.bounds.yRange = (this.bounds.y1 - this.bounds.y0) - this.handle.offsetHeight;

		this.bounds.xStep = 1 / (this.xPrecision || Math.max(this.wrapper.offsetWidth, this.handle.offsetWidth));
		this.bounds.yStep = 1 / (this.yPrecision || Math.max(this.wrapper.offsetHeight, this.handle.offsetHeight));
	},
	addListeners: function()
	{
		var self = this;

        // Необходимо для ие8. См. BUG059605.
		this.wrapper.onselectstart = function()
		{
			return false;
		};
		this.handle.onmousedown = this.handle.ontouchstart = function(e)
		{
			self.handleDownHandler(e);
		};
//		this.wrapper.onmousedown = this.wrapper.ontouchstart = function(e)
//		{
//			self.wrapperDownHandler(e);
//		};
		var mouseUpHandler = document.onmouseup || function(){};
		document.onmouseup = function(e)
		{
			mouseUpHandler(e);
			self.documentUpHandler(e);
		};
		var touchEndHandler = document.ontouchend || function(){};
		document.ontouchend = function(e)
		{
			touchEndHandler(e);
			self.documentUpHandler(e);
		};
		var resizeHandler = window.onresize || function(){};
		window.onresize = function(e)
		{
			resizeHandler(e);
			self.documentResizeHandler(e);
		};
//		this.wrapper.onmousemove = function(e)
//		{
//			self.activity = true;
//		}
//		this.wrapper.onclick = function(e)
//		{
//			return !self.activity;
//		}

		this.interval = setInterval(function(){ self.animate() }, 50);
		self.animate(false, true);
	},
	handleDownHandler: function(e)
	{
		this.activity = false;
		Cursor.refresh(e);

		this.preventDefaults(e, true);
		this.startDrag();
		this.cancelEvent(e);
	},
	wrapperDownHandler: function(e)
	{
		Cursor.refresh(e);

		this.preventDefaults(e, true);
		this.startTap();
	},
	documentUpHandler: function(e)
	{
		this.stopDrag();
		this.stopTap();
		//this.cancelEvent(e);
	},
	documentResizeHandler: function(e)
	{
		this.setWrapperOffset();
		this.setBounds();

		this.update();
	},
	enable: function()
	{
		this.disabled = false;
		this.handle.className = this.handle.className.replace(/\s?disabled/g, '');
	},
	disable: function()
	{
		this.disabled = true;
		this.handle.className += ' disabled';
	},
    setXYByStep: function(x, y)
	{
		this.setValue(
            this.xStep > 1 ? Math.round(x*(this.xStep - 1))/(this.xStep - 1) : x,
            this.yStep > 1 ? Math.round(y*(this.yStep - 1))/(this.yStep - 1) : y
		);
	},
	setValue: function(x, y, snap)
	{
		this.setTargetValue([x, y || 0]);
		if(snap)
		{
			this.groupCopy(this.value.current, this.value.target);
		}
	},
	startTap: function(target)
	{
		if(this.disabled)
		{
			return;
		}
		this.tapping = true;

		if(target === undefined)
		{
			target = [
				Cursor.x - this.offset.wrapper[0] - (this.handle.offsetWidth / 2),
				Cursor.y - this.offset.wrapper[1] - (this.handle.offsetHeight / 2)
			];
		}
		this.setTargetOffset(target);
	},
	stopTap: function()
	{
		if(this.disabled || !this.tapping)
		{
			return;
		}
		this.tapping = false;

		this.setTargetValue(this.value.current);
		this.result();
	},
	startDrag: function()
	{
		if(this.disabled)
		{
			return;
		}

        this.setWrapperOffset();
		this.offset.mouse = [
			Cursor.x - Position.get(this.handle)[0],
			Cursor.y - Position.get(this.handle)[1]
		];

		this.dragging = true;
	},
	stopDrag: function()
	{
		if(this.disabled || !this.dragging)
		{
			return;
		}
		this.dragging = false;

		var target = this.groupClone(this.value.current);
		if(this.slide)
		{
			var ratioChange = this.change;
			target[0] += ratioChange[0] * 4;
			target[1] += ratioChange[1] * 4;
		}
		this.setTargetValue(target);

		this.result();
	},
	feedback: function()
	{
		var value = this.value.current;
		if(this.snap && (this.xStep > 1 || this.yStep > 1))
		{
			value = this.getClosestSteps(value);
		}
		if(!this.groupEqual(value, this.value.prev))
		{
			if(typeof(this.animationCallback) == 'function')
			{
				this.animationCallback(value[0], value[1]);
			}
			this.groupCopy(this.value.prev, value);
		}
	},
	result: function()
	{
		if(typeof(this.callback) == 'function')
		{
			this.callback(this.value.target[0], this.value.target[1]);
		}
	},
	animate: function(direct, first)
	{
		if(direct && !this.dragging)
		{
			return;
		}
		if(this.dragging)
		{
			var prevTarget = this.groupClone(this.value.target);

			var offset = [
				Cursor.x - this.offset.wrapper[0] - this.offset.mouse[0],
				Cursor.y - this.offset.wrapper[1] - this.offset.mouse[1]
			];
			this.setTargetOffset(offset, this.loose);

			this.change = [
				this.value.target[0] - prevTarget[0],
				this.value.target[1] - prevTarget[1]
			];
		}
		if(this.dragging || first)
		{
			this.groupCopy(this.value.current, this.value.target);
		}
		if(this.dragging || this.glide() || first)
		{
			this.update();
			this.feedback();
		}
	},
	glide: function()
	{
		var diff = [
			this.value.target[0] - this.value.current[0],
			this.value.target[1] - this.value.current[1]
		];
		if(!diff[0] && !diff[1])
		{
			return false;
		}
		if(Math.abs(diff[0]) > this.bounds.xStep || Math.abs(diff[1]) > this.bounds.yStep)
		{
			this.value.current[0] += diff[0] * this.speed;
			this.value.current[1] += diff[1] * this.speed;
		}
		else
		{
			this.groupCopy(this.value.current, this.value.target);
		}
		return true;
	},
	update: function()
	{
        // Вертикальный движок не даем сдвинуть при мин. Х выше разрешенного значения.
        if (this.vertical && this.value.current[0] == 0 && this.value.current[1] < this.yInterval[0])
            this.value.current[1] = this.yInterval[0];
        // Вертикальный движок не даем сдвинуть при макс. Х ниже разрешенного значения.
        else if (this.vertical && this.value.current[0] == 1 && this.value.current[1] > this.yInterval[1])
            this.value.current[1] = this.yInterval[1];

        if(!this.snap)
		{
			this.offset.current = this.getOffsetsByRatios(this.value.current);
		}
		else
		{
			this.offset.current = this.getOffsetsByRatios(
				this.getClosestSteps(this.value.current)
			);
		}

		this.show();
	},
	show: function()
	{
		if(!this.groupEqual(this.offset.current, this.offset.prev))
		{
            // Если ползунок резко дернуть вверх и немного в сторону (по диагонали), то он должен сдвинуться вверх;
            // если сдвинется вверх до упора (или уже находится в этом положении), то еще надо сдвинуть в сторону
            if(this.horizontal && (parseInt($(this.handle).css('top')) == 0 || this.offset.current[1] == 0))
			{
                this.handle.style.left = String(this.offset.current[0]) + 'px';
			}
            if(this.vertical)
			{
                this.handle.style.top = String(this.offset.current[1]) + 'px';
			}

			this.groupCopy(this.offset.prev, this.offset.current);
		}
	},
	setTargetValue: function(value, loose)
	{
		var target = loose ? this.getLooseValue(value) : this.getProperValue(value);

		this.groupCopy(this.value.target, target);
		this.offset.target = this.getOffsetsByRatios(target);
	},
	setTargetOffset: function(offset, loose)
	{
		var value = this.getRatiosByOffsets(offset);
		var target = loose ? this.getLooseValue(value) : this.getProperValue(value);

		this.groupCopy(this.value.target, target);
		this.offset.target = this.getOffsetsByRatios(target);
	},
    setXY: function(x, y)
    {
        var value = [x, y];
        if(!this.snap)
		{
			this.offset.current = this.getOffsetsByRatios(value);
		}
		else
		{
			this.offset.current = this.getOffsetsByRatios(
				this.getClosestSteps(value)
			);
		}
    },
	getLooseValue: function(value)
	{
		var proper = this.getProperValue(value);
		return [
			proper[0] + ((value[0] - proper[0]) / 4),
			proper[1] + ((value[1] - proper[1]) / 4)
		];
	},
	getProperValue: function(value)
	{
		var proper = this.groupClone(value);

		proper[0] = Math.max(proper[0], 0);
		proper[1] = Math.max(proper[1], 0);
		proper[0] = Math.min(proper[0], 1);
		proper[1] = Math.min(proper[1], 1);

		if((!this.dragging && !this.tapping) || this.snap)
		{
			if(this.xStep > 1 || this.yStep > 1)
			{
				proper = this.getClosestSteps(proper);
			}
		}
		return proper;
	},
	getRatiosByOffsets: function(group)
	{
		return [
			this.getRatioByOffset(group[0], this.bounds.xRange, this.bounds.x0),
			this.getRatioByOffset(group[1], this.bounds.yRange, this.bounds.y0)
		];
	},
	getRatioByOffset: function(offset, range, padding)
	{
		return range ? (offset - padding) / range : 0;
	},
	getOffsetsByRatios: function(group)
	{
		return [
			this.getOffsetByRatio(group[0], this.bounds.xRange, this.bounds.x0),
			this.getOffsetByRatio(group[1], this.bounds.yRange, this.bounds.y0)
		];
	},
	getOffsetByRatio: function(ratio, range, padding)
	{
		return Math.round(ratio * range) + padding;
	},
	getClosestSteps: function(group)
	{
		return [
            this.xStep > 1 ? Math.round(group[0]*(this.xStep - 1))/(this.xStep - 1) : group[0],
            this.yStep > 1 ? Math.round(group[1]*(this.yStep - 1))/(this.yStep - 1) : group[1]
		];
	},
	groupEqual: function(a, b)
	{
		return a[0] == b[0] && a[1] == b[1];
	},
	groupCopy: function(a, b)
	{
		a[0] = b[0];
		a[1] = b[1];
	},
	groupClone: function(a)
	{
		return [a[0], a[1]];
	},
	preventDefaults: function(event, selection)
	{
		if(!event)
		{
			event = window.event;
		}
		if(event.preventDefault)
		{
			event.preventDefault();
		}
		event.returnValue = false;
	},
	cancelEvent: function(e)
	{
		if(!e)
		{
			e = window.event;
		}
		if(e.stopPropagation)
		{
			e.stopPropagation();
		}
		e.cancelBubble = true;
	},
    drawByXY: function(x, y)
    {
        this.setXY(x, y);
        if(!this.vertical || this.horizontal && $(this.handle).position().top == 0)
        {
            this.handle.style.left = String(this.offset.current[0]) + 'px';
        }
        if(this.vertical)
        {
            this.handle.style.top = String(this.offset.current[1]) + 'px';
        }
    },
    initScroll: function(currValue)
    {
        var length = this.maxValue - this.minValue;
        var x = 0;
        if (length > 0)
            x = (currValue - this.minValue)/(length);
        this.drawByXY(x,0);
    },
    calcXStep: function()
    {
        this.xStep = (this.maxValue - this.minValue)/this.step + 1;
    },
    getValue: function()
    {
        return $('input[name='+this.fieldName +']').val();
    }
};


// Функции для работы одномерного и двумерного "ползунков"

// Выставление позиции ползунка по значению из input
function setScrollVal(id, drag)
{
    var currValue = $('input[name='+drag.fieldName +']').val();
    currValue = parseFloatVal(currValue);

    if (isNaN(currValue) || currValue < drag.minValue)
        currValue = drag.minValue;

    else if (currValue > drag.maxValue)
        currValue = drag.maxValue;

    $('input[name=' + drag.fieldName + ']').val(currValue.toFixed(drag.round));
    redrawHorizScroll(id, drag, currValue);
}

function redrawHorizScroll(id, drag, currValue)
{
    drag.initScroll(currValue);
    drawActiv(id);
}

// Двумерный ползунок
// Отрисовка активной части ползунка (для горизонтального) и установка значения в инпут
function setHorizPosition (id, drag)
{
    drawActiv (id);

    var xScrollInner = $('#horizScroll' + id + 'Inner');
    var innerWidth = $("#Main" + id)[0].offsetWidth - xScrollInner.position().left;
    var currVal = 1 - innerWidth/$("#Main" + id)[0].offsetWidth;
    var inputVal = drag.step * Math.round((currVal * (drag.maxValue - drag.minValue) + drag.minValue)/drag.step);
    $('input[name=' + drag.fieldName + ']').val(inputVal.toFixed(drag.round));
}

// Отрисовка активной части ползунка
function drawActiv (id)
{
    var xScrollInner = $('#horizScroll' + id + 'Inner');
    // Вычисляем размер неактивной шкалы
    var deltaWidth = $("#Main" + id)[0].offsetWidth - xScrollInner.position().left;
    var innerWidth = deltaWidth > 0 ? deltaWidth : 0;
    $("#InnerScroll" + id).css('width', innerWidth+"px");
}

function drawActivScale(id)
{
    var xScrollInner = $('#horizScroll' + id + 'Inner');
    // Вычисляем размер неактивной шкалы
    var innerWidth = xScrollInner.position().left + 1;
    $("#Main" + id).css('width', innerWidth+6+"px");
    $("#InnerScroll" + id).css('width', innerWidth+"px");
}

// Отрисовка активной части ползунка (для вертикального)
function setVerticalPosition (id, fieldName, currVal, minVal, maxVal)
{
    // Вычисляем размер активной шкалы
    var innerHeight = $("#yInnerScroll"+id)[0].offsetHeight * currVal;
    $("#yMain" + id).css('height', innerHeight+"px");

    $('input[name=' + fieldName + ']').val(Math.round(currVal * (maxVal - minVal) + minVal));
}

var VERT_SCROLL_MARGIN = 11;

// Синхронизация ползунков по горизонтали
function setXScrollPosition(id)
{
    var yScrollInner = $('#vertScroll' + id + 'Inner');
    var xScrollInner = $('#horizScroll' + id + 'Inner');
    xScrollInner.offset({top: xScrollInner.offset().top, left: yScrollInner.offset().left});
    setYScroll(id);
}
function setYScrollPosition(id)
{
    var yScrollInner = $('#vertScroll' + id + 'Inner');
    var xScrollInner = $('#horizScroll' + id + 'Inner');
    yScrollInner.offset({top: yScrollInner.offset().top, left: xScrollInner.offset().left});
    setYScroll(id);
}
function correctYScrollPosition(id, xDrag, yDrag)
{
    var xCurrValue = $('input[name='+xDrag.fieldName +']').val();
    var yCurrValue = $('input[name='+yDrag.fieldName +']').val();

    if (xCurrValue == xDrag.minValue && yCurrValue < yDrag.yMinPossibleVal)
    {
        yCurrValue = yDrag.yMinPossibleVal;
        var y = (yCurrValue - yDrag.minValue)/(yDrag.maxValue - yDrag.minValue);
        yDrag.drawByXY(0,y);
        setVerticalPosition (id, yDrag.fieldName, y, yDrag.minValue, yDrag.maxValue);
    }
    else if (xCurrValue == xDrag.maxValue && yCurrValue > yDrag.yMaxPossibleVal)
    {
        yCurrValue = yDrag.yMaxPossibleVal;
        var y = (yCurrValue - yDrag.minValue)/(yDrag.maxValue - yDrag.minValue);
        yDrag.drawByXY(0,y);
        setVerticalPosition (id, yDrag.fieldName, y, yDrag.minValue, yDrag.maxValue);
    }
}

/**
 * Обновляем поле содержащее общее значение ползунка
 * @param totalFieldName
 */
function updateTotalField(xFieldName, yFieldName, totalFieldName)
{
    var xCurrValue = $('input[name='+xFieldName +']').val();
    var yCurrValue = $('input[name='+yFieldName +']').val();
    var month = (yCurrValue - 1) * 3 + 1;
    if(month < 10)
        month = '0' + month;
    $('input[name='+totalFieldName +']').val('01/' + month + '/' + xCurrValue);
}

// Синхронизация вертикальной шкалы с положением ползунков
function setYScroll(id)
{
    var yScrollInner = $('#vertScroll' + id + 'Inner');
    var y = $("#yScroll" + id);
    y.offset({top: y.offset().top, left: (parseFloat(yScrollInner.offset().left) + VERT_SCROLL_MARGIN)});
    y.css('left', (parseFloat(yScrollInner.css('left').replace("px", "")) + VERT_SCROLL_MARGIN) + 'px');
}

// Выставление позиции ползунков и вертикальной шкалы по заданным значениям
function initScrolls(id, horizDrag, vertDrag, xCurrValue, yCurrValue)
{
    var x = (xCurrValue - horizDrag.minValue)/(horizDrag.maxValue - horizDrag.minValue);
    var y = (yCurrValue - vertDrag.minValue)/(vertDrag.maxValue - vertDrag.minValue);
    horizDrag.drawByXY(x,0);
    vertDrag.drawByXY(x,y);
    horizDrag.animationCallback(x,0);
    vertDrag.animationCallback(x,y);
    setYScroll(id);
}

// Выставление позиции ползунков по горизонтали по значению из input
function setXScrollVal(id, xDrag, yDrag)
{
    var xCurrValue = parseFloatVal($('input[name='+xDrag.fieldName +']').val());
    var yCurrValue = parseFloatVal($('input[name='+yDrag.fieldName +']').val());

    if (isNaN(xCurrValue) || xCurrValue < xDrag.minValue)
    {
        xCurrValue = xDrag.minValue;
        yCurrValue = yDrag.yMinPossibleVal;
        $('input[name=' + xDrag.fieldName + ']').val(xCurrValue);
    }
    else if (xCurrValue > xDrag.maxValue)
    {
        xCurrValue = xDrag.maxValue;
        yCurrValue = yDrag.yMaxPossibleVal;
        $('input[name=' + xDrag.fieldName + ']').val(xCurrValue);
    }


    if (xCurrValue == xDrag.minValue && (isNaN(yCurrValue) || yCurrValue < yDrag.yMinPossibleVal))
        yCurrValue = yDrag.yMinPossibleVal;

    if (xCurrValue == xDrag.maxValue && (isNaN(yCurrValue) || yCurrValue > yDrag.yMaxPossibleVal))
        yCurrValue = yDrag.yMaxPossibleVal;

    initScrolls(id, xDrag, yDrag, xCurrValue, yCurrValue);
}

// Выставление позиции ползунка по вертикали по значению из input
function setYScrollVal(id, xDrag, yDrag)
{
    var xCurrValue = parseFloatVal($('input[name='+xDrag.fieldName +']').val());
    var yCurrValue = parseFloatVal($('input[name='+yDrag.fieldName +']').val());

     if (isNaN(yCurrValue) || yCurrValue < yDrag.minValue)
    {
        yCurrValue = yDrag.minValue;
        $('input[name=' + yDrag.fieldName + ']').val(yCurrValue);
    }
    else if (yCurrValue > yDrag.maxValue)
    {
        yCurrValue = yDrag.maxValue;
        $('input[name=' + yDrag.fieldName + ']').val(yCurrValue);
    }
    if ((isNaN(xCurrValue) || xCurrValue == xDrag.minValue) && yCurrValue < yDrag.yMinPossibleVal)
        yCurrValue = yDrag.yMinPossibleVal;

    if ((isNaN(xCurrValue) || xCurrValue == xDrag.maxValue) && yCurrValue > yDrag.yMaxPossibleVal)
        yCurrValue = yDrag.yMaxPossibleVal;

    initScrolls(id, xDrag, yDrag, xCurrValue, yCurrValue);
}

// Добавление надписей на вертикальную шкалу
function initYScrollDescr(id, yMinValue, yMaxValue, yScrollType)
{
    var divs = $('#yScroll' + id + ' .yScrollDesc');
    for (var i = 0; i < divs.length; i++)
    {
        var div = $(divs.get(i));
        if (yScrollType != undefined)
            div.text(yScrollType[i+1]);
    }
    initYScrollDescrPos(id, yMinValue, yMaxValue);
}

// Добавление позиционирования надписей на вертикальной шкале
function initYScrollDescrPos(id, yMinValue, yMaxValue)
{
    var y = $('#yScroll' + id + " .yScrollCenter");
    var height = y.height() - 10;
    var count = yMaxValue - yMinValue;
    var step = 1/count;

    var divs = $('#yScroll' + id + ' .yScrollDesc');
    for (var i = 0; i < count; i++)
    {
        var div = $(divs.get(i));
        div.css('top', Math.round(step*(i + 1)*height));
        var w = $(div).width() + 10;
        div.css('left', -w);
        div.css('width', w + 20);
    }
}

/**
 * Рисует деления на шкале ползунка
 * @param id - ид ползунка
 * @param count - кол-во делений
 */
function drawDivisions(id, fullWidth, count)
{
    var scrollerDivisionsBlock = $('#'+id+' .scaleDivisions');
    var step = 1/count;
    for (var i = 0; i < count; i++)
    {
        scrollerDivisionsBlock.append('<div class="scaleDivision"></div>');
        var div = $(scrollerDivisionsBlock.find(".scaleDivision").get(i+1));
        div.css('left', Math.round(step*(i + 1)*fullWidth));
    }
}

/**
 * Добавляет подписи года для шкалы ползунка
 * @param id - ид ползунка
 * @param minDate - минимальная дата
 * @param maxDate - максимальная дата
 * @param count - всего значений на шкале
 */
function drawYearsOnDivisions(id, minDate, maxDate, count)
{
    var scale = $('#dateScroller'+id+' .dragdealer');
    var scrollerDescrBlock = $('#dateScroller'+id+' .scaleDescriptions');

    var step = 1/count; // шаг шкалы
    var fullWidth = scale.width() - 10; // 10 - отступ шкалы
    var date = new Date(minDate.getFullYear()+1, 0, 1); // 1 января следующего года
    var division = 12 - minDate.getMonth(); // первое деление у которого надо подписать год
    var i = 0;
    while(date < maxDate)
    {
        scrollerDescrBlock.append('<div class="scaleDescription">'+date.getFullYear()+'</div>');
        var div = $(scrollerDescrBlock.find(".scaleDescription").get(i++));
        div.css('left', Math.round(step*division*fullWidth) - div.width()/2);
        division += 12;

        date.setFullYear(date.getFullYear()+1);
    }
}

/**
 * Устанавливает значение (дату) в поле
 * @param drag - ползунок
 * @param x - координата ползунка
 * @param minDate - минимальная дата
 */
function setDateValue(drag, x, minDate)
{
    var months = Math.round(x*drag.maxValue);
    var newDate = new Date(minDate.getFullYear(), minDate.getMonth(), 1);
    newDate.setMonth(newDate.getMonth() + months);
    $('input[name=' + drag.fieldName + ']').val(formatMonth(newDate.getMonth()+1) + '/' + newDate.getFullYear());
}

function formatMonth(month)
{
    return month > 9 ? month : '0' + month;
}

/**
 * По введеному в поле значению (дата) перерисовывается активная часть ползунка
 * @param drag - ползунок
 * @param id - ид ползунка
 * @param minDate - минимальная дата
 */
function redrawActiveScale(drag, id, minDate, maxDate)
{
    var currDateStr = $('input[name=' + drag.fieldName + ']').val();
    var currDate = new Date(currDateStr.slice(3), getValidMonth(currDateStr.slice(0,2)-1), 1);

    if (currDate < minDate)
        currDate = minDate;
    else if (currDate > maxDate)
        currDate = maxDate;

    var currVal = (currDate.getFullYear() - minDate.getFullYear())*12 + (currDate.getMonth() - minDate.getMonth());
    drag.setXYByStep(currVal/drag.maxValue,0);

    drag.initScroll(currVal);
    drawActivScale(id);
    $('input[name=' + drag.fieldName + ']').val(formatMonth(currDate.getMonth()+1) + '/' + currDate.getFullYear());
}

function getValidMonth(month)
{
    return month > 11 ? 11 : month;
}

/* Скрипты для работы со скроллерами, у которых несколько движков */

var multiplyScrollUtils = {

    drags:[],
    account_COLOR: "green",
    IMA_COLOR:"purple",
    fund_COLOR: "orange",
    insurance_COLOR: "blue",
    trustManaging_COLOR: "red",
    pension_COLOR: "darkBlue",

    /**
     * Добавить новый движок
     * @param id - ид скроллера
     * @param color - цвет
     * @param title - заголовок в легенде
     * @param value - значение = предыдущие значения движков + значение текущего движка
     * @param minValue - минимальное значение скроллера
     * @param maxValue - масимальное значение скроллера
     * @param needSlider - необходимо ли отображать движок
     */
    addMultiplyScroll: function(num, id, key, color, title, value, minValue, maxValue, unit, callback)
    {
        $('<div />', {className: 'multiplyScroll ' + color})
            .prependTo('#multiplyScroll' + id + ' .multiplyScrollsBlock')
            .append($('<div />', {className: 'scrollLeft'}))
            .append($('<div />', {className: 'scrollCenter'}))
            .append($('<div />', {className: 'scrollRight'}));

        this.addLegendItem(id, num, key, color, title, value);

            $('#multiplyScroll' + id).append('<div id="horizScroll'+id+color+'Inner" class="scrollInner handle">' +
                                             '<input type="hidden" name="scrollColor" value="'+color+'"/>' +
                                             '<input type="hidden" name="scrollNum" value="'+num+'"/>' +
                                             '</div>');

        if (isIE(6))
        {
            var bgImgSrc = globalUrl+'/commonSkin/images/multiplyHorizScroll.png';
            var el = document.getElementById;
            $('#horizScroll'+id+color+'Inner').css('filter', "progid:DXImageTransform.Microsoft.AlphaImageLoader(src="+bgImgSrc+",sizingMethod='scale')");
        }

        var drag = new Dragdealer('multiplyScroll'+id,
            {
                snap: true,
                xStep: maxValue - minValue + 1,
                fieldName: '',
                minValue: minValue,
                maxValue: maxValue,
                step: 1,
                round: 0
            },
            'horizScroll'+id+color+'Inner'
        );

        drag.animationCallback = function(x, y){
            multiplyScrollUtils.setMultiplyScrollPosition(id, color, drag);
            multiplyScrollUtils.recalcMultiplyScrollValues(id, maxValue, unit);
        };

        if (callback != undefined)
        {
            drag.callback = function(x, y){
                callback();
            };
        }

        drag.initScroll(value);
        this.setMultiplyScrollPosition(id, color, drag);
        this.drags[id+''+num] = {"drag": drag, "color": color, "unit": unit, "maxValue": maxValue};
    },

    updateDragDealerByValues : function (id, values)
    {
        var value = 0;
        for(var i = 0; i < values.length - 1; i++)
        {
            var drag     = this.drags[id+''+i].drag;
            var color    = this.drags[id+''+i].color;
            var unit     = this.drags[id+''+i].unit;
            var maxValue = this.drags[id+''+i].maxValue;
            value = value + parseInt(values[i].percent);
            drag.initScroll(value);
            this.setMultiplyScrollPosition(id, color);
        }
        this.recalcMultiplyScrollValues(id, maxValue, unit);
    },

    /**
     * Добавить эламент в легенду
     * @param id - ид скроллера
     * @param color - цвет
     * @param title - заголовок в легенде
     */
    addLegendItem: function(id, num, key, color, title, value)
    {
        $('<div />', {className: 'scrollLegend ' + color})
            .appendTo('#multiplyScrollArea' + id + ' .multiplyScrollLegend')
            .append($('<div />', {className: 'scrollLegendValueBox float'}))
            .append($('<div />', {className: 'scrollLegendTitle float'}));

        $('#multiplyScrollArea' + id + ' .' + color + ' .scrollLegendTitle').text(title);
        $('#multiplyScrollArea' + id + ' .' + color + ' .scrollLegendValueBox')
            .append($('<div />', {className: 'scrollLegendLeft'}))
            .append('<div class="scrollLegendCenter"><span class="scrollLegendValue bold"></span><input type="hidden" id="scrolValue'+id+''+num+'" value="'+value+'" name="'+key+'"/></div>')
            .append($('<div />', {className: 'scrollLegendRight'}));
    },

    /**
     * Перерисовать цветную часть скроллера относительно конкретного движка
     * @param id - ид скроллера
     * @param color - цвет
     * @param drag
     */
    setMultiplyScrollPosition: function(id, color)
    {
        var allNum = $('#multiplyScroll' + id + ' .scrollInner').length;
        var scrollInner = $('#horizScroll' + id + color + 'Inner'); // движок
        var pos = scrollInner.position().left; // позиция движка
        var num = parseInt(scrollInner.find('input[name=scrollNum]').val()); // номер текущего движка
        this.redrawMultiplyScrollColorLine(id, scrollInner);

        var allPrev = scrollInner.prevAll('.scrollInner');
        var allNext = scrollInner.nextAll('.scrollInner');

        for (var i = 0; i < allPrev.length; i++)
        {
            var currScrollInner = $(allPrev.get(i));
            var currPos = currScrollInner.position().left;
            var currNum = parseInt(currScrollInner.find('input[name=scrollNum]').val()); // номер движка

            if (currPos >= pos && currNum < num)
            {
                this.setBeforeByStep(scrollInner, currScrollInner, num-currNum);
                currScrollInner.css('left', pos);
                this.redrawMultiplyScrollColorLine(id, currScrollInner);
            }
            else if (currPos >= pos && currNum > num)
            {
                scrollInner.after(currScrollInner);
                this.redrawMultiplyScrollColorLine(id, currScrollInner);
            }
            else if (currPos <= pos && currNum > num)
            {
                currScrollInner.css('left', pos);
                this.redrawMultiplyScrollColorLine(id, currScrollInner);
            }
        }

        for (var i = 0; i < allNext.length; i++)
        {
            var currScrollInner = $(allNext.get(i));
            var currPos = currScrollInner.position().left;
            var currNum = parseInt(currScrollInner.find('input[name=scrollNum]').val()); // номер движка

            if (currPos <= pos && currNum > num)
            {
                this.setBeforeByStep(scrollInner, currScrollInner, currNum-num);
                currScrollInner.css('left', pos);
                this.redrawMultiplyScrollColorLine(id, currScrollInner);
            }
        }
    },

    setBeforeByStep: function(beforeElem, elem, step)
    {
        for (var i = 0; i < step; i++)
        {
            beforeElem.before(elem);
            beforeElem = elem.prev();
        }
    },

    /**
     * Отрисовка цветных (активных) частей ползунка
     * @param id - ид ползунка
     * @param scroll - движок
     */
    redrawMultiplyScrollColorLine: function(id, scroll)
    {
        var currScroll = $(scroll); // движок
        var currColor = currScroll.find('input[name=scrollColor]').val(); // цвет текущего движка
        var colorLine = $("#multiplyScroll" + id + ' .' + currColor); // цветная полоска для этого движка
        colorLine.show();
        var currPos = currScroll.position().left; // позиция движка
        var sideWidth = colorLine.find('.scrollRight').width(); // ширина закругления цветной полоски

        var newWidth = 0;
        if (currPos > 0)
            newWidth = 2*sideWidth > currPos ? 0 : (currPos - sideWidth);
        else
            colorLine.hide(); // движок находится на 0 => скрываем его цветную полоску

        colorLine.find('.scrollCenter').css('width', newWidth);
    },

    /**
     * Пересчитывает значения движков
     * @param id - ид движка
     */
    recalcMultiplyScrollValues: function(id, maxValue, unit)
    {
        var fullWidth = $('#multiplyScroll' + id + ' .multiplyScrollBackground').width() - 8; // 8px - половина "движка"
        var scrolls = $('#multiplyScroll' + id + ' .scrollInner'); // все движки
        var summ = 0;

        for (var i = 0; i < scrolls.length; i++)
        {
            var pos = $(scrolls.get(i)).position().left;
            var percent = Math.round(pos*100/fullWidth - summ);
            summ = summ + percent;

            $($('#multiplyScrollArea' + id + ' .scrollLegendValue').get(i)).text(percent.toString()+unit);
            $('#scrolValue'+id+''+i).val(percent);
        }

        // у последнего нет движка: его значение остаток от 100%
        $($('#multiplyScrollArea' + id + ' .scrollLegendValue').get(i)).text((maxValue - summ).toString()+unit);
        $('#scrolValue'+id+''+i).val(maxValue - summ);
    },

    /**
     * Добавляет подписи для шкалы ползунка
     * @param id - ид ползунка
     * @param fullWidth - полная ширина ползунка
     * @param minValue - минимальное значение
     * @param maxValue - максимальное значение
     * @param count - количество подписей
     */
    drawValuesOnDivisions: function(id, fullWidth, minValue, maxValue, count)
    {
        var scrollerDescrBlock = $('#'+id+' .scaleDescriptions');
        var step = 1/(count-1); // шаг шкалы
        var stepVal = maxValue/(count-1);
        var divisionValue = minValue; // значение
        for(var i=0; i < count; i++)
        {
            scrollerDescrBlock.append('<div class="scaleDescription">'+divisionValue+'</div>');
            var div = $(scrollerDescrBlock.find(".scaleDescription").get(i));
            div.css('left', Math.round(step*fullWidth*i));
            divisionValue += stepVal;
        }

        $('#'+id+' .scaleDescription:first').hide(); // первое значение у деления не отображаем
        $('#'+id+' .scaleDescription:last').hide(); // последнее значение у деления не отображаем 
    }
};

/* Скрипты для работы со скроллером для выбора интервала */
function RangeScroll()
{
    this.id = null;
    this.firstDrag = null;
    this.secondDrag = null;
    this.step = null;

    /**
     * Инициализация ползунка
     * @param id - ид скроллера
     * @param minValue - минимальное значение скроллера
     * @param maxValue - масимальное значение скроллера
     * @param step - шаг с которым увеличиваются значения
     * @param startFieldName - имя поля для начала интервала
     * @param endFieldName - имя поля для конца интервала
     * @param callback
     */
    this.initScroll = function(id, minValue, maxValue, step, startFieldName, endFieldName, callback)
    {
        this.id = id;
        var mod = (maxValue - minValue) % step;
        if (mod > 0) maxValue += step - mod; // длинна отрезка должна быть кратна step

        this.firstDrag = this.createDrag(id, 1, minValue, maxValue, step, startFieldName, minValue, callback);
        this.secondDrag = this.createDrag(id, 2, minValue, maxValue, step, endFieldName, maxValue, callback);
        var mySelf = this;
        this.firstDrag.animationCallback = function(x, y){
            mySelf.setScrollPosition(1);
            mySelf.recalcScrollValue(mySelf.firstDrag, x);
            mySelf.correctScrollValues(1, mySelf.firstDrag, mySelf.secondDrag);
        };
        this.secondDrag.animationCallback = function(x, y){
            mySelf.setScrollPosition(2);
            mySelf.recalcScrollValue(mySelf.secondDrag, x);
            mySelf.correctScrollValues(2, mySelf.firstDrag, mySelf.secondDrag);
        };

        this.setScrollPosition(1);
        this.drawDivisions(minValue, maxValue, step);
        this.drawValuesOnDivisions(minValue, maxValue, step);
    };

    /**
     * Создать новый движок
     * @param id - ид скроллера
     * @param num - номер движка
     * @param minValue - минимальное значение скроллера
     * @param maxValue - масимальное значение скроллера
     * @param step - шаг с которым увеличиваются значения
     * @param fieldName - имя поля
     * @param value - начальное значение
     * @param callback
     */
    this.createDrag = function(id, num, minValue, maxValue, step, fieldName, value, callback)
    {
        var drag = new Dragdealer('rangeScroll'+id,
            {
                snap: true,
                xStep: (maxValue - minValue)/step  + 1,
                fieldName: fieldName,
                minValue: minValue,
                maxValue: maxValue,
                step: step
            },
            'horizScroll'+num+'Inner'
        );

        if (isIE(6))
        {
            var bgImgSrc = globalUrl+'/commonSkin/images/multiplyHorizScroll.png';
            var el = document.getElementById;
            $('#horizScroll'+num+'Inner').css('filter', "progid:DXImageTransform.Microsoft.AlphaImageLoader(src="+bgImgSrc+",sizingMethod='scale')");
        }

        if (callback != undefined)
        {
            drag.callback = function(x, y){
                callback();
            };
        }
        drag.initScroll(value);
        $('input[name='+fieldName +']').val(value);

        return drag;
    };

    /**
     * Перерисовать цветную часть скроллера относительно движков
     * @param id - ид скроллера
     * @param num
     */
    this.setScrollPosition = function(num)
    {
        var currInner = $('#horizScroll'+num+'Inner');
        var currPos = currInner.position().left;

        if (num == 1)
        {
            var secondInner = $('#horizScroll2Inner');
            var secondPos = secondInner.position().left; // позиция второго движка
            if (currPos >= secondPos)
            {
                currInner.before(secondInner);
                secondInner.css('left', currPos);
            }
            else
                currInner.after(secondInner);
        }
        else if (num == 2)
        {
            var firstInner = $('#horizScroll1Inner');
            var firstPos = firstInner.position().left; // позиция первого движка
            if (currPos <= firstPos)
            {
                currInner.before(firstInner);
                firstInner.css('left', currPos);
            }
        }

        this.redrawScrollActiveLine();
    };

    /**
     * Отрисовка активной части ползунка
     * @param id - ид ползунка
     */
    this.redrawScrollActiveLine = function()
    {
        var scroll = $("#rangeScroll" + this.id);
        var activeLine = scroll.find('.rangeScroll'); // активная полоска
        var firstPos = scroll.find('#horizScroll1Inner').position().left; // позиция первого движка
        var secondPos = scroll.find('#horizScroll2Inner').position().left; // позиция второго движка
        var sideWidth = activeLine.find('.scrollRight').width(); // ширина закругления цветной полоски

        var newWidth = 2*sideWidth > (secondPos - firstPos) ? 0 : (secondPos - firstPos - sideWidth);
        activeLine.find('.scrollCenter').css('width', newWidth);
        if (firstPos > 0 && newWidth == 0)
            activeLine.css('left', firstPos-5); // "-3" необходимо для того чтобы зеленая часть полностью пряталась за ползунком
        else
            activeLine.css('left', firstPos);
    };

    /**
     * Пересчитывает значения движков
     * @param drag - ид движок
     * @param x - координата движка (от 0 до 1)
     */
    this.recalcScrollValue = function(drag, x)
    {
        var value = Math.round((x * (drag.maxValue - drag.minValue) + drag.minValue));
        $('input[name='+drag.fieldName +']').val(value);
    };

    this.correctScrollValues = function(num, firstDrag, secondDrag)
    {
        var startField = $('input[name='+firstDrag.fieldName +']');
        var endField = $('input[name='+secondDrag.fieldName +']');
        switch (num)
        {
            case 1:
                if (endField.val() < startField.val())
                    endField.val(startField.val());
                break;
            case 2:
                if (endField.val() < startField.val())
                    startField.val(endField.val());
                break;
        }
    };

    this.drawDivisions = function(minValue, maxValue, valueStep)
    {
        var scrollerDivisionsBlock = $('#rangeScrollArea'+this.id+' .scaleDivisions');
        scrollerDivisionsBlock.empty();
        scrollerDivisionsBlock.append('<div class="scaleDivision first"></div>');
        var fullWidth = $('#rangeScroll'+this.id).width() - 19;
        var count = (maxValue - minValue)/valueStep;
        var step = 1/count;
        for (var i = 0; i < Math.floor(count); i++)
        {
            scrollerDivisionsBlock.append('<div class="scaleDivision"></div>');
            var div = $(scrollerDivisionsBlock.find(".scaleDivision").get(i+1));
            div.css('left', Math.round(step*(i+1)*fullWidth));
        }
    };

    /**
     * Добавляет подписи для шкалы ползунка
     * @param id - ид ползунка
     * @param minValue - минимальное значение
     * @param maxValue - максимальное значение
     * @param valueStep - шаг
     */
    this.drawValuesOnDivisions = function(minValue, maxValue, valueStep)
    {
        var scrollerDescrBlock = $('#rangeScrollArea'+this.id+' .scaleDescriptions');
        scrollerDescrBlock.empty();
        var fullWidth = $('#rangeScroll'+this.id).width() - 19;
        var count = (maxValue - minValue)/valueStep;
        var step = 1/count;
        var divisionValue = minValue; // значение
        for(var i=0; i <= Math.floor(count); i++)
        {
            scrollerDescrBlock.append('<div class="scaleDescription">'+divisionValue+'</div>');
            var div = $(scrollerDescrBlock.find(".scaleDescription").get(i));
            div.css('left', Math.round(step*fullWidth*i));
            divisionValue += valueStep;
        }
    };

    this.update = function(length, step)
    {
        this.step = step;
        this.firstDrag.maxValue = this.firstDrag.minValue + length;
        this.firstDrag.step = step;
        this.firstDrag.calcXStep();
        this.firstDrag.initScroll(this.firstDrag.minValue);
        $('input[name='+this.firstDrag.fieldName +']').val(this.firstDrag.minValue);

        this.secondDrag.maxValue = this.secondDrag.minValue + length;
        this.secondDrag.step = step;
        this.secondDrag.calcXStep();
        this.secondDrag.initScroll(this.secondDrag.maxValue);
        $('input[name='+this.secondDrag.fieldName +']').val(this.secondDrag.maxValue);

        this.setScrollPosition(1);
        this.drawDivisions(this.firstDrag.minValue, this.firstDrag.maxValue, step);
        this.drawValuesOnDivisions(this.firstDrag.minValue, this.firstDrag.maxValue, step);
    };

    this.getFromValue = function()
    {
        return this.firstDrag.getValue();
    };

    this.getToValue = function()
    {
        return this.secondDrag.getValue();
    };
}