define("widget/LoanNotificationWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-style",
    "widget/_WidgetBase"
], function(lang, declare, query, on, domStyle, _WidgetBase)
{
    /**
     * Виджет напоминания об оплате кредита
     */
    return declare("widget.LoanNotificationWidget", [ _WidgetBase ],
    {
        ///////////////////////////////////////////////////////////////////////
        // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                loanNotifyDayCount: 5
            });
        },

        ///////////////////////////////////////////////////////////////////////
        // Поля виджета

        /**
         * Адрес экшена, который отвечает за отображение справочника регионов
         * [readonly]
         */
        regionURL: undefined,

        //панель полного вида
        panelWide: undefined,
        //панель компактного вида
        panelCompact: undefined,
        ///////////////////////////////////////////////////////////////////////
        // Кнопки и граф.компоненты виджета

        fieldSize: undefined,

        fieldLoanAccountNumber: undefined,

        fieldLoanNotifyDayCount: undefined,

        buttonCancel: undefined,

        buttonSave: undefined,

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

            this.fieldLoanAccountNumber = this.findField("loanAccountNumber");
            this.fieldLoanAccountNumber.onchange = lang.hitch(this, function(){
                this.editSettings.loanAccountNumber = this.fieldLoanAccountNumber.value;
            });

            this.fieldLoanNotifyDayCount = this.findField("loanNotifyDayCount");
            on(this.fieldLoanNotifyDayCount, "change", lang.hitch(this, function(){
                this.editSettings.loanNotifyDayCount = this.fieldLoanNotifyDayCount.value;
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

            win.init(this.domNode);
        },

        onValidate: function()
        {
            var rc = this.inherited(arguments);
            rc = rc && (trim(this.editSettings.loanAccountNumber) != "");
            return rc;
        },

        onRefresh: function()
        {
            this.inherited(arguments);

            if (this.panelCompact != undefined)
                domStyle.set(this.panelCompact, "display", (this.settings.size == "compact") ? "block" : "none");
            if (this.panelWide != undefined)
                domStyle.set(this.panelWide, "display", (this.settings.size == "wide") ? "block" : "none");

            // 1. Настраиваем панель просмотра
            var loanAccountNumber = trim(this.settings.loanAccountNumber);
            if (loanAccountNumber != "")
                this.setLabelText("loanAccountNumber", this.panelView, loanAccountNumber);

            // 2. Выставляем значения для полей режима редактирования
            loanAccountNumber = trim(this.editSettings.loanAccountNumber);
            var loanNotifyDayCount = trim(this.editSettings.loanNotifyDayCount);
            //Поле "Вид"
            this.toggleNodeAttrib(this.fieldSize, "disabled", this.canResize());
            this.fieldSize.value = this.editSettings.size;

            //Поле "Кредит"
            if (loanAccountNumber == "") {
                var options = query("option", this.fieldLoanAccountNumber);
                if (options.length > 0)
                    loanAccountNumber = options[0].value;
            }
            this.fieldLoanAccountNumber.value = loanAccountNumber;

            //Поле "Оповещать за"
            this.fieldLoanNotifyDayCount.value = loanNotifyDayCount;
        },

        getHeightScrollableData: function(widgetViewHeight)
        {
            return this.loanNotificationHeight();
        },

        onUpdate: function()
        {
            this.inherited(arguments);
            //Кол-во дней оповещения перед очередной оплатой кредита
            this.editSettings.loanNotifyDayCount = trim(this.fieldLoanNotifyDayCount.value);
            //Номер кредита
            this.editSettings.loanAccountNumber = trim(this.fieldLoanAccountNumber.value);
        },

        getTitle: function()
        {
            return undefined;
        }

    });
});
