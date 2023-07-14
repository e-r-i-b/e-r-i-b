define("widget/LastPaymentsWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-class",
    "dojo/dom-style",
    "widget/_WidgetBase"
], function(lang, declare, query,  on, domClass,domStyle, _WidgetBase)
{
    return declare("widget.LastPaymentsWidget", [ _WidgetBase ],
    {

    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "Последние платежи"
            });
        },

        buttonNumberOfItems5: undefined,
        buttonNumberOfItems10: undefined,
        buttonNumberOfItems20: undefined,

        wideTableView: undefined,
        compactTableView: undefined,
        wideTableEdit: undefined,
        compactTableEdit: undefined,

        onStartup: function()
        {
            this.inherited(arguments);

            this.wideTableView = this.findPanel("wideTable", this.panelView);
            this.compactTableView = this.findPanel("compactTable", this.panelView);
            this.wideTableEdit = this.findPanel("wideTable", this.panelEdit);
            this.compactTableEdit = this.findPanel("compactTable", this.panelEdit);
            //подключаем кнопки
            this.buttonNumberOfItems5 = this.findButton("numberOfItems5");
            on(this.buttonNumberOfItems5, "click", lang.hitch(this, function(){
                this.editSettings.numberOfShowItems = 5;
                this.refresh();
            }));

            this.buttonNumberOfItems10 = this.findButton("numberOfItems10");
            on(this.buttonNumberOfItems10, "click", lang.hitch(this, function(){
                this.editSettings.numberOfShowItems = 10;
                this.refresh();
            }));

            this.buttonNumberOfItems20 = this.findButton("numberOfItems20");
            on(this.buttonNumberOfItems20, "click", lang.hitch(this, function(){
                this.editSettings.numberOfShowItems = 20;
                this.refresh();
            }));

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
        },

        onRefresh: function()
        {
            this.inherited(arguments);
            domStyle.set(this.wideTableView, "display", (this.settings.size == "wide" ? "block" : "none"));
            domStyle.set(this.compactTableView, "display", (this.settings.size == "compact" ? "block" : "none"));
            domStyle.set(this.wideTableEdit, "display", (this.settings.size == "wide" ? "block" : "none"));
            domStyle.set(this.compactTableEdit, "display", (this.settings.size == "compact" ? "block" : "none"));

            domClass.toggle(this.buttonNumberOfItems5, "greenSelector", this.editSettings.numberOfShowItems == "5");
            domClass.toggle(this.buttonNumberOfItems10, "greenSelector", this.editSettings.numberOfShowItems == "10");
            domClass.toggle(this.buttonNumberOfItems20, "greenSelector", this.editSettings.numberOfShowItems == "20");
        }
    });
});