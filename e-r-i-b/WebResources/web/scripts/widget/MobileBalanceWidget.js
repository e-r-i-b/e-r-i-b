define("widget/MobileBalanceWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-style",
    "widget/_WidgetBase"
], function(lang, declare, query, on, domStyle, _WidgetBase)
{
    return declare("widget.MobileBalanceWidget", [ _WidgetBase ],
    {
        ///////////////////////////////////////////////////////////////////////
        // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "Мобильный баланс"
            });
        },

        fieldSize: undefined,

        fieldProvider: undefined,

        fieldPhoneNumber: undefined,

        fieldPassword: undefined,

        buttonCancel: undefined,

        buttonSave: undefined,

        panelWide : undefined,

        panelCompact : undefined,

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

            this.fieldProvider = this.findField("provider");
            this.fieldProvider.onchange = lang.hitch(this, function(){
                this.editSettings.provider = this.fieldProvider.value;
            });
           
            this.fieldPhoneNumber = this.findField("phoneNumber");
            on(this.fieldPhoneNumber, "change", lang.hitch(this, function(){
                this.editSettings.phoneNumber = this.fieldPhoneNumber.value;
            }));

            this.fieldPassword = this.findField("password");
            on(this.fieldPassword, "change", lang.hitch(this, function(){
                this.editSettings.password = this.fieldPassword.value;
            }));

            this.fieldThresholdValue = this.findField("thresholdValue");
            on(this.fieldThresholdValue, "change", lang.hitch(this, function(){
                this.editSettings.thresholdValue = this.fieldThresholdValue.value;
            }));

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

        },

        onUpdate: function()
        {
            this.inherited(arguments);

            this.editSettings.provider = this.fieldProvider.value;
            this.editSettings.phoneNumber = this.fieldPhoneNumber.value;
            this.editSettings.password = this.fieldPassword.value;
            this.editSettings.thresholdValue = this.fieldThresholdValue.value;
        },

        onValidate: function()
        {
            var rc = this.inherited(arguments);
            rc = rc && (trim(this.editSettings.provider) != "");
            rc = rc && (trim(this.editSettings.phoneNumber) != "");
            rc = rc && (trim(this.editSettings.password) != "");
            rc = rc && (trim(this.editSettings.thresholdValue) != "");
            return rc;
        },

        onRefresh: function()
        {
            this.inherited(arguments);

            // 1. Поля режима отображения
            var phoneStr = trim(this.settings.phoneNumber);

            domStyle.set(this.panelCompact, "display", (this.settings.size == "compact") ? "block" : "none");
            domStyle.set(this.panelWide, "display", (this.settings.size == "wide") ? "block" : "none");

            if (phoneStr != "")
            {
                var formatPhone = '+7 ' + phoneStr.substring(0,3) + ' ' + phoneStr.substring(3,6) + ' ' + phoneStr.substring(6);
                this.setLabelText("phoneNumber", this.panelView, formatPhone);
            }
            this.setLabelText("provider", this.panelView, trim(this.settings.provider));

            // 2. Поля режима редактирования
            phoneStr = trim(this.editSettings.phoneNumber);
            var passwordStr = trim(this.editSettings.password);
            var thresholdStr = trim(this.editSettings.thresholdValue);

            this.toggleNodeAttrib(this.fieldSize, "disabled", this.canResize());
            this.fieldSize.value = this.editSettings.size;

            var provider = trim(this.editSettings.provider);
            if (provider == "") {
                var options = query("option", this.fieldProvider);
                if (options.length > 0)
                    provider = options[0].value;
            }
            this.fieldProvider.value = provider;

            if (phoneStr != "")
                this.fieldPhoneNumber.value = phoneStr;
            if (passwordStr != "")
                this.fieldPassword.value = passwordStr;
            if (thresholdStr != "")
                this.fieldThresholdValue.value = thresholdStr.replace(' ', '').replace(',', '.');
        },

        getHeightScrollableData: function(widgetViewHeight)
        {
            return this.mobileBalanceHeight();
        }
    });
});
