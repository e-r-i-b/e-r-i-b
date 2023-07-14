define("widget/IFrameWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/on",
    "widget/_WidgetBase"
], function(lang, declare, on, _WidgetBase)
{
    return declare("widget.IFrameWidget", [ _WidgetBase ],
    {
        ///////////////////////////////////////////////////////////////////////
        // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "IFrameWidget"
            });
        },

        fieldSize: undefined,

        fieldTitle: undefined,

        buttonCancel: undefined,

        buttonSave: undefined,

        onStartup: function()
        {
           this.inherited(arguments);

            // 1. Подключаем поля
            this.fieldSize = this.findField("size");

            if (this.fieldSize != undefined)
            {
                // Поскольку fieldSize - это кастомный селект,
                // событие onchange должно быть установлено таким образом
                this.fieldSize.onchange = lang.hitch(this, function(){
                    this.editSettings.size = this.fieldSize.value;
                });
            }

            this.fieldTitle = this.findField("title");

            // 2. Подключаем кнопки
            this.buttonCancel = this.findButton("cancel");
            on(this.buttonCancel, "click", lang.hitch(this, function(){
                this.reset();
                this.mode = "view";
                this.refresh();
            }));

            this.buttonSave = this.findButton("save");
            on(this.buttonSave, "click", lang.hitch(this, function(){
                this.update();
                if (this.validate()) {
                    this.save();
                    this.mode = "view";
                }
                this.refresh();
            }));
        },

        onUpdate: function()
        {
            this.inherited(arguments);

            var title = this.fieldTitle.value;
            if (trim(title) == "")
                title = this.defaultSettings().title;
            this.editSettings.title = title;

        },

        onRefresh: function()
        {
            this.inherited(arguments);

            // 2. Выставляем значения для полей режима редактирования
            if (this.fieldSize != undefined) {
                this.toggleNodeAttrib(this.fieldSize, "disabled", this.canResize());
                this.fieldSize.value = this.editSettings.size;
            }

            this.fieldTitle.value = this.editSettings.title;

        }
    });
});
