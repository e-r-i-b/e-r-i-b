define("widget/ProductBlockWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "widget/GenericWidget"
], function(lang, declare, query,  on, GenericWidget)
{
    return declare("widget.ProductBlockWidget", [ GenericWidget ],
    {
        
    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments));
        },
        
        widgetId: undefined,

        checkBoxes: undefined,

        hiddenProductsCount: undefined,
        productsVisibility: undefined,

        buttonCancel: undefined,

        buttonSave: undefined,

        buttonDelete: undefined,

        onStartup: function()
        {
            this.inherited(arguments);
            this.productsVisibility = this.settings.productsVisibility;
            this.widgetId = this.settings.codename;
            this.checkBoxes = query("input[name='showInThisWidget']", this.panelEdit );

            // Подключаем кнопки
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
                else{
                  this.initDialogWindow();
                }
                this.refresh();
            }));
            
        },

        onUpdate: function()
        {
            this.inherited(arguments);
            var productsVisibility =[];
            var hiddenProductsCount = 0;
            var checkBoxes = this.checkBoxes;
            var i=0;
            checkBoxes.forEach(
                function(item){
                    productsVisibility[i] = new Object();
                    productsVisibility[i].id = item.value;
                    if(!item.checked)
                    {
                        hiddenProductsCount++;
                        productsVisibility[i].visible = false;
                    }
                    else
                    {
                        productsVisibility[i].visible = true;
                    }
                    i++;
            });

            this.productsVisibility = productsVisibility;
            this.hiddenProductsCount = hiddenProductsCount;
            this.editSettings.productsVisibility = this.productsVisibility;
        },

        onValidate: function()
        {
            var rc = this.inherited(arguments);
            rc = rc && (this.hiddenProductsCount != this.checkBoxes.length);
            return rc;            
        },

        onRefresh: function()
        {
            this.inherited(arguments);
            var productsVisibility = this.editSettings.productsVisibility;
            var checkBoxes = this.checkBoxes;
            var i=0;
            checkBoxes.forEach(
                function(item){
                    if(productsVisibility[i].visible)
                        item.checked = true;
                    else item.checked = false;
                    i++;
            });
            this.productsVisibility = productsVisibility;
        },

        onClose: function()
        {
           this.inherited(arguments);
        },

        initDialogWindow: function()
        {
            win.init(this.domNode);
            win.open(this.widgetId + 'WidgetDeleteDialog');
            this.buttonDelete = query("div[id='" + this.widgetId + "WidgetDeleteButton']");
            on(this.buttonDelete, "click", lang.hitch(this, function(){
                this.close();
            }));
        },

        getTitle: function()
        {
            return undefined;
        }

    });
});