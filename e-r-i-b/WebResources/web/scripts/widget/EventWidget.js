define("widget/EventWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-class",
    "widget/_WidgetBase"
], function(lang, declare, query,  on, domClass, _WidgetBase)
{
    return declare("widget.EventWidget", [ _WidgetBase ],
    {

    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "События"
            });
        },

        buttonNumberOfItems3: undefined,
        buttonNumberOfItems5: undefined,
        buttonNumberOfItems10: undefined,

        onStartup: function()
        {
            this.inherited(arguments);
            // Подключаем кнопки

            this.buttonNumberOfItems3 = this.findButton("numberOfItems3");
            on(this.buttonNumberOfItems3, "click", lang.hitch(this, function(){
                this.editSettings.numberOfShowItems = 3;
                this.refresh();
            }));

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
            var currentWidget = this;
            getRegionSelector('regionsDiv').addListener(function(){currentWidget.window.container.loadWidget(currentWidget.window, false);});
        },

       onRefresh: function()
          {
              this.inherited(arguments);
              domClass.toggle(this.buttonNumberOfItems3, "greenSelector", this.editSettings.numberOfShowItems == "3");
              domClass.toggle(this.buttonNumberOfItems5, "greenSelector", this.editSettings.numberOfShowItems == "5");
              domClass.toggle(this.buttonNumberOfItems10, "greenSelector", this.editSettings.numberOfShowItems == "10");
          }
    });
});