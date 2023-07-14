define("widget/VkGroupsWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/on",    
    "dojo/dom-construct",
    "dojo/dom-geometry",
    "dojo/dom-style",
    "widget/script",
    "widget/_WidgetBase"
], function(lang, declare, on, domConstruct, domGeometry, domStyle, script, _WidgetBase)
{
    var VK_API_URL = "//userapi.com/js/api/openapi.js?52";

    return declare("widget.VkGroupsWidget", [ _WidgetBase ],
    {

        ///////////////////////////////////////////////////////////////////////
        // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {

                // код отображаемой группы (http://vk.com/bankdruzey),
                // сгенерирован на странице http://vk.com/developers.php?oid=-1&p=Groups
                groupCode: 22522055
            });
        },

        fieldSize: undefined,

        vkApiLoaded: false,

        buttonCancel: undefined,

        buttonSave: undefined,

        compactPanel : undefined,

        waiterPanel : undefined,

        onStartup: function()
        {
            this.inherited(arguments);

            this.panelWide = this.findPanel("wide");
            this.panelCompact = this.findPanel("compact");

            // 1. Подключаем поля
            this.fieldSize = this.findField("size");
            // Поскольку fieldSize - это кастомный селект,
            // событие onchange должно быть установлено таким образом
            this.fieldSize.onchange = lang.hitch(this, function(){
                this.editSettings.size = this.fieldSize.value;
            });


            // 2. Подключаем кнопки
            this.buttonCancel = this.findButton("cancel");
            on(this.buttonCancel, "click", lang.hitch(this, function(){
                this.reset();
                if (this.validate()) {
                    this.mode = "view";
                }
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

            this.waiterPanel = this.findPanel("waiter");

            script.include("vk-api", VK_API_URL, "VK", lang.hitch(this, function(){
                this.vkApiLoaded = true;
                this.refresh();
            }));
        },

        onUpdate: function()
        {
            this.inherited(arguments);
        },

        onRefresh: function()
        {
            this.inherited(arguments);

            // 1. Поля режима отображения

            domStyle.set(this.waiterPanel, "display", (this.vkApiLoaded ? "none" : "block"));

            if (this.vkApiLoaded)
            {
                if (this.compactPanel != undefined) {
                    domConstruct.destroy(this.compactPanel);
                    this.compactPanel = undefined;
                }

                var id = "vk_groups_" + this.codename;
                this.compactPanel = domConstruct.place('<div id="' + id + '">', this.panelView, "first");

                var box = domGeometry.position(this.window.domNode);
                VK.Widgets.Group(id, {mode: 2, width: box.w-36, height: 200}, this.defaultSettings().groupCode);
            }

            // 2. Поля режима редактирования

            this.toggleNodeAttrib(this.fieldSize, "disabled", this.canResize());
            this.fieldSize.value = this.editSettings.size;

        }
    });
});
