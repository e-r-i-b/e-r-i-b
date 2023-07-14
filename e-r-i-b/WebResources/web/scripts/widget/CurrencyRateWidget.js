define("widget/CurrencyRateWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-style",
    "widget/_WidgetBase"
], function(lang, declare, query,  on, domStyle, _WidgetBase)
{
    return declare("widget.CurrencyRateWidget", [ _WidgetBase ],
    {
    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "Курсы"
            });
        },

        wideTableView: undefined,
        compactTableView: undefined,
        wideTableEdit: undefined,
        compactTableEdit: undefined,

        showCurrencyCodes: undefined,
        showImaCodes: undefined,
        currencyCheckBoxes: undefined,
        imaCheckBoxes: undefined,
                                                                                        
        onStartup: function()
         {
            this.inherited(arguments);

            this.wideTableView = this.findPanel("wideTable", this.panelView);
            this.compactTableView = this.findPanel("compactTable", this.panelView);
            this.wideTableEdit = this.findPanel("wideTable", this.panelEdit);
            this.compactTableEdit = this.findPanel("compactTable", this.panelEdit);

            this.showCurrencyCodes = this.settings.showCurrencyCodes;
            this.showImaCodes = this.settings.showImaCodes;

            this.currencyCheckBoxesCompact = query("input[name='currencyShowInThisWidget']", this.compactTableEdit);
            this.imaCheckBoxesCompact = query("input[name='imaShowInThisWidget']", this.compactTableEdit);

            this.currencyCheckBoxesWide = query("input[name='currencyShowInThisWidget']", this.wideTableEdit);
            this.imaCheckBoxesWide = query("input[name='imaShowInThisWidget']", this.wideTableEdit);

            on(this.currencyCheckBoxesCompact, "change", function(evt){
            var currencyCheckBoxWide = query("input[value='" + evt.target.value +"']", this.currencyCheckBoxesWide)[0];
                currencyCheckBoxWide.checked = evt.target.checked;
            });

            on(this.currencyCheckBoxesWide, "change", function(evt){
                var currencyCheckBoxCompact = query("input[value='" + evt.target.value +"']", this.currencyCheckBoxesCompact)[0];
                currencyCheckBoxCompact.checked = evt.target.checked;
            });

            on(this.imaCheckBoxesCompact, "change", function(evt){
                var imaCheckBoxWide = query("input[value='" + evt.target.value +"']", this.imaCheckBoxesWide)[0];
                imaCheckBoxWide.checked = evt.target.checked;
            });

            on(this.imaCheckBoxesWide, "change", function(evt){
                var imaCheckBoxCompact = query("input[value='" + evt.target.value +"']", this.imaCheckBoxesCompact)[0];
                imaCheckBoxCompact.checked = evt.target.checked;
            });


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

        onRefresh: function()
        {
            this.inherited(arguments);
            domStyle.set(this.wideTableView, "display", (this.settings.size == "wide" ? "block" : "none"));
            domStyle.set(this.compactTableView, "display", (this.settings.size == "compact" ? "block" : "none"));
            domStyle.set(this.wideTableEdit, "display", (this.settings.size == "wide" ? "block" : "none"));
            domStyle.set(this.compactTableEdit, "display", (this.settings.size == "compact" ? "block" : "none"));

            var showCurrencyCodes = this.editSettings.showCurrencyCodes;

            this.currencyCheckBoxesWide.forEach(
                function(item){
                    if(showCurrencyCodes.contains(item.value))
                        item.checked = true;
                    else
                        item.checked = false;
            });

            this.currencyCheckBoxesCompact.forEach(
                function(item){
                    if(showCurrencyCodes.contains(item.value))
                        item.checked = true;
                    else
                        item.checked = false;
            });

            this.showCurrencyCodes = showCurrencyCodes;
            
            var showImaCodes = this.editSettings.showImaCodes;

            this.imaCheckBoxesWide.forEach(
                function(item){
                    if(showImaCodes.contains(item.value))
                        item.checked = true;
                    else item.checked = false;
            });

            this.imaCheckBoxesCompact.forEach(
                function(item){
                    if(showImaCodes.contains(item.value))
                        item.checked = true;
                    else item.checked = false;
            });
            this.showImaCodes = showImaCodes;
        },

        onUpdate: function()
        {
            this.inherited(arguments);
            var showCurrencyCodes =[];
            var currencyCheckBoxes = this.settings.size == "wide" ? this.currencyCheckBoxesWide : this.currencyCheckBoxesCompact;
            var i=0;
            currencyCheckBoxes.forEach(
                function(item){
                    if(item.checked)
                    {
                        showCurrencyCodes[i]=item.value;
                        i++;
                    }
            });

            this.showCurrencyCodes = showCurrencyCodes;
            this.editSettings.showCurrencyCodes = this.showCurrencyCodes;

            var showImaCodes =[];
            var imaCheckBoxes = this.settings.size == "wide" ? this.imaCheckBoxesWide : this.imaCheckBoxesCompact;
            i=0;
            imaCheckBoxes.forEach(
                function(item){
                    if(item.checked)
                    {
                        showImaCodes[i]=item.value;
                        i++;
                    }
            });

            this.showImaCodes = showImaCodes;
            this.editSettings.showImaCodes = this.showImaCodes;
        },

        initDialogWindow: function()
        {
            win.init(this.domNode);
            win.open(this.settings.codename + 'WidgetDeleteDialog');
            this.buttonDelete = query("div[id='" + this.settings.codename + "WidgetDeleteButton']");
            on(this.buttonDelete, "click", lang.hitch(this, function(){
                this.close();
            }));
        },

        onValidate: function()
        {
            var rc = this.inherited(arguments);
            rc = rc && (this.showCurrencyCodes.length != 0 || this.showImaCodes != 0);
            return rc;
        }
    });
});