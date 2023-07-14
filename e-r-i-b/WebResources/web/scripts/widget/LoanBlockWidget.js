define("widget/LoanBlockWidget", [
    "dojo",
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/_base/fx",
    "dojo/_base/xhr",
    "widget/GenericWidget",
    "widget/ProductBlockWidget"
], function(dojo, lang, declare, query,  on, domClass, domStyle, fx,xhr,  GenericWidget, ProductBlockWidget)
{
    return declare("widget.LoanBlockWidget", [GenericWidget, ProductBlockWidget],
    {

    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "Ваши кредиты"
            });
        },

        notify :undefined,
        loanNotifyDayCount : undefined,
        notNotifiedLoanLinkIds : undefined,
        blinkingLoanIds : undefined,

        notifyRadioButton : undefined,
        notNotifyRadioButton : undefined,

        loanNotifyDayCountField : undefined,

        notifyLoanButtons : undefined,

        anim : undefined,

        stoppedBlinkingByUser : undefined,

        onStartup: function()
        {
            this.inherited(arguments);
            this.notify = this.settings.notify;
            this.loanNotifyDayCount = this.settings.loanNotifyDayCount;
            this.notNotifiedLoanLinkIds = this.settings.notNotifiedLoanLinkIds;
            this.notifyRadioButton = query("input.notify", this.panelEdit)[0];
            this.notNotifyRadioButton = query("input.notNotify", this.panelEdit)[0];
            this.notifyLoanButtons = query(".notificationButton", this.panelEdit );
            this.loanNotifyDayCountField = this.findField("notifyDayCount", this.panelEdit);


            //клик на виджет - выключить мигалку
            // Вешаем обработчики на шапку, panelView и кнопку редактирования, чтобы при нажатии на нее мигалка тоже отключалась
            on(this.panelTop, "click", lang.hitch(this, this.stopBlinking));
            on(this.panelView, "click", lang.hitch(this, this.stopBlinking));
            on(this.buttonEdit, "click", lang.hitch(this, this.stopBlinking));

            var self = this;
            //кнопки "Напоминать" - "Не напоминать" для каждого кредита
            on(this.notifyLoanButtons, "click", function(evt){
                var isDisabled = domClass.contains(evt.target.parentElement, "lock hasLayout");
                if(!isDisabled)
                {
                    var isNotify = domClass.contains(evt.target.parentElement, "notify");
                    self.changeNotifyButton(evt.target.parentElement, isNotify);
                }
                cancelBubbling(evt);
            });

            //вешаем обработчики на чекбоксы "показывать в этом виджете", чтобы при клике на них изменять кнопки "напоминать" - "не напоминать"
            on(this.checkBoxes, "click", function(evt){

                var notifyButton = query(".notificationButton[id='" + evt.target.value +"']", this.panelEdit)[0];
                if(evt.target.checked)
                {
                    self.changeNotifyButton(notifyButton, true);
                    if(self.editSettings.notify)
                        self.changeNotifyButtonAble(notifyButton, true);
                }
                else
                {
                  self.changeNotifyButton(notifyButton, false);
                  self.changeNotifyButtonAble(notifyButton, false);
                }
            });

            //такие же обработчики на радио-кнопки - для того, чтобы изменять доступность кнопок "напоминать" - "не напоминать"
            on(this.notifyRadioButton, "click", lang.hitch(this, function(){
                self.editSettings.notify = true;
                self.loanNotifyDayCountField.disabled = false;
                var notifyLoanButtons = this.notifyLoanButtons;
                notifyLoanButtons.forEach(
                    function(item){
                        var checkBox = query("input[name='showInThisWidget'][value='" + item.id + "']", this.panelEdit)[0];
                        if(checkBox.checked)
                            self.changeNotifyButtonAble(item, true);
                });
            }));

            on(this.notNotifyRadioButton, "click", lang.hitch(this, function(){
                self.editSettings.notify = false;
                self.loanNotifyDayCountField.disabled = true;
                var notifyLoanButtons = this.notifyLoanButtons;
                notifyLoanButtons.forEach(
                    function(item){
                            self.changeNotifyButtonAble(item, false);
                });
            }));

            win.init(this.domNode);
        },

        onRefresh: function()
        {
            this.inherited(arguments);
            var self = this;

            if(this.notify)
            {
               this.notifyRadioButton.checked = true;
               this.loanNotifyDayCountField.disabled = false;

               var notifyLoanButtons = this.notifyLoanButtons;
               var i=0;
               notifyLoanButtons.forEach(
                    function(item){
                        if(self.productsVisibility[i].visible)
                            self.changeNotifyButtonAble(item, true);
                        else
                            self.changeNotifyButtonAble(item, false);
                        i++;
               });

               var blinkingLoans = query(".loanProductCard.blinkLoan .roundPlateLeft", this.domNode);

               if(blinkingLoans.length != 0)
               {
                   this.blinkWidget(this.panelTop);
                   blinkingLoans.forEach(
                        function(item){
                            domStyle.set(item, "backgroundColor", "#FF9900");
                            self.blinkWidget(item);
                   });
               }
            }
            else
            {
               this.notNotifyRadioButton.checked = true;
               this.loanNotifyDayCountField.disabled = true;

               notifyLoanButtons = this.notifyLoanButtons;
               notifyLoanButtons.forEach(
                    function(item){
                            self.changeNotifyButtonAble(item, false);
               });
            }


            var notNotifiedLoanLinkIds = this.editSettings.notNotifiedLoanLinkIds;
            notifyLoanButtons = this.notifyLoanButtons;
            notifyLoanButtons.forEach(
                function(item){
                    var condition = !notNotifiedLoanLinkIds.contains(item.id);
                    self.changeNotifyButton(item, condition);
            });
            this.notNotifiedLoanLinkIds = notNotifiedLoanLinkIds;
            this.loanNotifyDayCountField.value = this.loanNotifyDayCount;
        },

        onUpdate: function()
        {
            this.inherited(arguments);
            if(this.notifyRadioButton.checked)
                this.notify = true;
            else
                this.notify = false;
            this.editSettings.notify = this.notify;

            var notNotifiedLoanLinkIds =[];

            var notifyLoanButtons = this.notifyLoanButtons;
            var i=0;
            notifyLoanButtons.forEach(
                function(item){
                    var notify = domClass.contains(item, "notify");
                    if(notify)
                    {
                        notNotifiedLoanLinkIds[i]=item.id;
                        i++;
                    }
            });

            this.notNotifiedLoanLinkIds = notNotifiedLoanLinkIds;
            this.editSettings.notNotifiedLoanLinkIds = this.notNotifiedLoanLinkIds;
            this.editSettings.loanNotifyDayCount = this.loanNotifyDayCountField.value;
        },

        save: function()
        {
            this.stoppedBlinkingByUser = false;
            this.inherited(arguments);
        },

        //вызывается, когда нужно мигать
        blinkWidget: function(obj)
        {
            if(this.stoppedBlinkingByUser)
                return;
            var self = this;
            $(obj).fadeTo('slow', 0.7, function()
            {
                $(this).fadeTo('normal', 0.99, function()
                {
                    self.blinkWidget(obj);
                });
            });
        },

        //вызывается, когда пользователь остановил мигание
        stopBlinking : function()
        {
            var self = this;
            xhr.post(
            {
            url : self.window.url,

            content: {
                operation : "stopBlinking",
                codename : self.window.codename,
                PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
            },

            load : lang.hitch(self, function(response, ioargs)
            {
                this.stoppedBlinkingByUser = true;
                return response;

            }),

            error : lang.hitch(self, function(response, ioargs)
            {
                return response;
            })
            });
        },

        // Поменять кнопку "Напоминать"- "Не напоминать"
        changeNotifyButton: function(node, condition)
        {
            var text = query(".buttonSelectCenter", node)[0];
            if(condition)
                text.innerHTML = "не напоминать";
            else
                text.innerHTML = "напоминать";

            domClass.toggle(node, "notNotify", condition);
            domClass.toggle(node, "notify", !condition);
        },

        //Поменять активность кнопки напоминания об оплате кредита
        changeNotifyButtonAble: function(node, able)
        {
            domClass.toggle(node, "lock hasLayout", !able);
            domClass.toggle(node, "", able);
        }
    });
});
