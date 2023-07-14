define("widget/OffersWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/on",
    "dojo/dom-class",
    "widget/_WidgetBase"
], function(lang, declare, on, domClass, _WidgetBase)
{
    return declare("widget.OffersWidget", [ _WidgetBase ],
    {
        buttonNumberOfItems3: undefined,
        buttonNumberOfItems6: undefined,
        buttonNumberOfItems12: undefined,
        
        onStartup: function()
        {
            this.inherited(arguments);

            // Подключаем кнопки
            this.buttonNumberOfItems3 = this.findButton("numberOfItems3");
            on(this.buttonNumberOfItems3, "click", lang.hitch(this, function()
            {
                this.editSettings.numberOfShowItems = 3;
                this.refresh();
            }));

            this.buttonNumberOfItems6 = this.findButton("numberOfItems6");
            on(this.buttonNumberOfItems6, "click", lang.hitch(this, function()
            {
                this.editSettings.numberOfShowItems = 6;
                this.refresh();
            }));

            this.buttonNumberOfItems12 = this.findButton("numberOfItems12");
            on(this.buttonNumberOfItems12, "click", lang.hitch(this, function()
            {
                this.editSettings.numberOfShowItems = 12;
                this.refresh();
            }));

            this.buttonCancel = this.findButton("cancel");
            on(this.buttonCancel, "click", lang.hitch(this, function()
            {
                this.reset();
                if (this.validate()) {
                    this.mode = "view";
                }
                this.refresh();
            }));

            this.buttonSave = this.findButton("save");
            on(this.buttonSave, "click", lang.hitch(this, function()
            {
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
            domClass.toggle(this.buttonNumberOfItems3, "greenSelector", this.editSettings.numberOfShowItems == "3");
            domClass.toggle(this.buttonNumberOfItems6, "greenSelector", this.editSettings.numberOfShowItems == "6");
            domClass.toggle(this.buttonNumberOfItems12, "greenSelector", this.editSettings.numberOfShowItems == "12");
        }
    });
});
