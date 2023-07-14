/**
 * Новый компонент, служит для сортировки виджетов. Унаследован от компонента jQuery draggable.
 * В нем возникла необходимость для сортировки виджетов в ie8, до этого сортировка в ie8 была невозможна.
 * Фактически переопределен метод _mouseMove, который определен в jquery.ui.mouse, исправлена следующая строка:
 * if ($.browser.msie && !(document.documentMode >= 9) && !event.button), в которой определяется режим совместимости
 * браузеров ie. Остальные унаследованные методы (_create, _setOption, destroy) являются обязательными
 * при реализации нового компонента.
 */
(function($) {
    // Arguments to .extend are returned object, merge object 1, merge object 2
    $.widget('ui.extenddraggable', $.ui.draggable, {
        _create: function ()
        {
            this.element.data("draggable", this);
            $.ui.draggable.prototype._create.call(this);
        },

        _setOption: function (key, value)
        {
            $.ui.draggable.prototype._setOption.apply(this, arguments);
        },

        _mouseMove: function(event) {
            if ($.browser.msie && !(document.documentMode >= 8) && !event.button)
                return this._mouseUp(event);

            if (this._mouseStarted)
            {
                this._mouseDrag(event);
                return event.preventDefault()
            }

            if (this._mouseDistanceMet(event) && this._mouseDelayMet(event))
                (this._mouseStarted = this._mouseStart(this._mouseDownEvent, event) !== false) ? this._mouseDrag(event) : this._mouseUp(event);

            return !this._mouseStarted;
        },

        destroy : function () {
            $.ui.draggable.prototype.destroy.call(this);
        }
    });
})(jQuery);