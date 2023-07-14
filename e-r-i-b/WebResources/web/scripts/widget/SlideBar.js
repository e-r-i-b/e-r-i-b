define("widget/SlideBar",[
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/_base/connect",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/dom-geometry",
    "dojo/_base/fx",
    "dojo/fx/Toggler",
    "dijit/_WidgetBase",
    "widget/_UIMixin",
    "dojo/topic"
], function(lang, declare, connect, domStyle, domClass, domGeometry, baseFx, FxToggler, WidgetBase, UIMixin, topic)
{
    /**
     * Выдвижная панель настроек виджетов/страницы в шапке
     * (слайд-бара)
     * Имеет фиксированную высоту
     */

    return declare("widget.SlideBar", [WidgetBase, UIMixin],
    {
        /**
         * Время выдвигания/задвигания в мс.
         * [readonly]
         */
        slideDuration: 400,

        /**
         * Высота слайд-бара
         * [final]
         */
        sliderHeight: undefined,

        buttonLever: undefined,
        buttonSave: undefined,
        buttonCancel: undefined,
        buttonReset: undefined,

        startup: function()
        {
            this.inherited(arguments);

            // 1. Определяем высоту слайд-бара
            this.sliderHeight = domGeometry.getContentBox(this.domNode).h;

            // 2. Устанавливаем fx-переключатель режимов
            var slidebarToggler = new FxToggler({
                node : this.domNode,
                showFunc : lang.hitch(this, this.showFunction),
                hideFunc : lang.hitch(this, this.hideFunction),
                showDuration: this.slideDuration,
                hideDuration: this.slideDuration
            });

            // 3. Вешаем fx-переключатель на рычаг слайд-бара
            this.buttonLever = this.findButton("lever");
            connect.connect(this.buttonLever, "onclick", lang.hitch(this, function()
            {
                var isHidden = domClass.contains(this.domNode, "hidden");
                if (isHidden)
                    slidebarToggler.show();
                else slidebarToggler.hide();
                this.findWebPage().setEditMode(isHidden);
            }));

            this.buttonSave = this.findButton("save");
            connect.connect(this.buttonSave, "onclick", lang.hitch(this, function()
            {
                this.findWebPage().save();
            }));

            this.buttonCancel = this.findButton("cancel");
            connect.connect(this.buttonCancel, "onclick", lang.hitch(this, function()
            {
                this.findWebPage().cancel();
            }));

            this.buttonReset = this.findButton("reset");
            connect.connect(this.buttonReset, "onclick", lang.hitch(this, function()
            {
                this.findWebPage().reset();
            }));

            topic.subscribe("hideSlidebar", lang.hitch(this, this.hide));
        },

        /**
         * Выдвинуть панель
         */
        showFunction: function(/*Object*/ args)
        {
            // Плавно меняем позицию слайд-бара с отрицательного значения до нуля
            var anim = baseFx.animateProperty(lang.mixin({
                properties: {
                    top:                { start: -this.sliderHeight, end: 0 },
                    "margin-bottom":    { start: -this.sliderHeight, end: 0 }
                }
            }, args));

            connect.connect(anim, "onBegin", lang.hitch(this, function() {
                // на время анимации сбросим курсор
                domStyle.set(this.buttonLever, "cursor", "default");
                domClass.remove(this.domNode, "hidden");
            }));

            connect.connect(anim, "onEnd", lang.hitch(this, function() {
                domStyle.set(this.buttonLever, "cursor", "");
            }));

            return anim;
        },

        /**
         * Задвинуть панель
         */
        hideFunction: function(/*Object*/ args)
        {
            // Плавно меняем позицию слайд-бара с нуля до отрицательного значения
            var anim = baseFx.animateProperty(lang.mixin({
                properties: {
                    top:                { start: 0, end: -this.sliderHeight+10 },
                    "margin-bottom":    { start: 0, end: -this.sliderHeight+10 }
                }
            }, args));

            connect.connect(anim, "onBegin", lang.hitch(this, function() {
                // на время анимации сбросим курсор
                domStyle.set(this.buttonLever, "cursor", "default");
            }));

            connect.connect(anim, "onEnd", lang.hitch(this, function() {
                domClass.add(this.domNode, "hidden");
                domStyle.set(this.buttonLever, "cursor", "");
            }));

            return anim;
        },
        
        hide: function()
        {
            this.buttonLever.click();
        }
    });
});