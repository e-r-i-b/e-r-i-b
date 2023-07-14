function InvoiceManager(params)
{
    this.params = params;
}

InvoiceManager.prototype =
{
    open : function()
    {
        var t = this;
        if (t.params["type"]== 'page')
        {
            document.location.href = t.params["pageUrl"];
        }
        else if(t.params["type"] == "window")
        {
            var element = ensureElement(t.params["winId"]);
            element.dataWasLoaded = false;

            var ajaxUrl = ensureElement('hiddenAjaxUrl' + t.params["winId"]);

            var viewUrl = t.params["viewUrl"];
            ajaxUrl.value =  viewUrl + (viewUrl.indexOf("?") <0 ? "?id=" : "&id=") + t.params["id"];

            win.aditionalData(t.params["winId"], {onAjaxLoad : function(data){
                t.init();
            }});

            win.open(t.params["winId"]);
        }
        else if(t.params["type"] == "block")
        {
            ajaxQuery ("id=" + t.params["id"], t.params["viewUrl"], function(data){
                if(t.params["preLoadBlock"] != null && (typeof t.params["preLoadBlock"]) == "function")
                    t.params["preLoadBlock"](data);
                $("#" + t.params["blockId"]).html(data);
                t.init();
            });
        }
    },

    init : function()
    {
        var t = this;
        var invoiceId = t.params["id"];
        var invoiceBlock = $("#" + t.getBlockId());

        invoiceBlock.find("*[name='org.apache.struts.taglib.html.TOKEN']").remove();
        $(".invoice-process-messages").find(".invoice-message").remove();

        invoiceBlock.find("a.removeButton").click(function(){
            var deleteConfirm = "deleteConfirmationinvoice" + invoiceId + "_confirmation";
            if(typeof window[deleteConfirm] == 'function')
                window[deleteConfirm]();
            else
                t.remove();
        });

        invoiceBlock.find(".invoiceView, .window").each(function(){
            this.manager = t;
        });

        var chooseDateElem = $("#delayButton_" + invoiceId).closest("div").get(0);
        $("#chooseDelayDateInnerinvoice_" + invoiceId)
                .datePicker({displayClose: true, altField: chooseDateElem, dateFormat: 'dd.mm.yyyy', startDate: new Date().asString('dd.mm.yyyy')})
                .unbind('change')
                .change(function(){
                    t.delay();
                });


        selectCore.init(invoiceBlock.get(0));
        win.init(ensureElement('invoicePaymentFormDiv' + invoiceId));
    },

    executeOperation : function(operationName)
    {
        var t = this;
        var params = serializeDiv(t.getBlockId());
        if(t.token != null)
            params += "&org.apache.struts.taglib.html.TOKEN=" + t.token;

        showOrHideWaitDiv(true);
        ajaxQuery(
                params + "&operation=button." + operationName,
                t.params["actionUrl"],
                function(data)
                {
                    t.clearAllMessages();
                    var block = $("#" + t.getBlockId());

                    if (data.success)
                    {
                        reload();
                        return;
                    }

                    showOrHideWaitDiv(false);
                    if(data.errors != null && data.errors.length > 0)
                    {
                        var errors = data.errors;
                        for(var i = 0; i <errors.length; i++)
                            addError(errors[i],'editEntityErrors'+t.params["id"], true);
                    }

                    if(data.errorFields != null && data.errorFields.length > 0)
                    {
                        var errorFields = data.errorFields;
                        for(var j = 0; j <errorFields.length; j++)
                            payInput.fieldError(errorFields[j].name, errorFields[j].value, 'invoicePaymentFormDiv'+t.params["id"]);
                    }

                    if(data.messages != null && data.messages.length > 0)
                    {
                        var messages = data.messages;
                        for(var k = 0; k <messages.length; k++)
                            addMessage(messages[k],'editEntityWarnings'+t.params["id"], true);
                    }

                    if(data.confirm != null && data.confirm)
                    {
                        block.find(".confirmArea").show();
                        block.find(".buttonsArea").hide();
                    }

                    if(data.token != null)
                        t.token = data.token;
                },
                "json", false
        );
    },

    clearAllMessages : function()
    {
        var t = this;
        removeAllMessages('editEntityWarnings' + t.params["id"]);
        removeAllErrors('editEntityErrors' + t.params["id"]);
        $("#payment .form-row").each(function(){
            payInput.formRowClearError(this);
        });
    },

    getBlockId : function()
    {
        var t = this;
        switch(t.params["type"])
        {
            case "window" :
                return t.params["winId"];
            case "block" :
                return t.params["blockId"];
            default :
                throw new Error("Тип без контейнера [" + t.params["type"] + "]");
        }
    },

    confirm : function()
    {
        var t = this;
        t.executeOperation("confirm");
        $("#" + t.getBlockId()).find("input[name='$$confirmSmsPassword']").val("");
    },

    pay : function()
    {
        var t = this;
        t.executeOperation("payInvoice");
    },

    delay : function()
    {
        var t = this;
        t.executeOperation("delay");
    },

    remove : function()
    {
        var t = this;
        t.executeOperation("delete");
    }
};

function processInvoice(el, command)
{
    if(el == null || isEmpty(command))
        return;

    var invoice = $(el).closest(".invoiceView, .window");
    if(invoice.size() == 0)
        return;

    var manager = invoice.get(0).manager;
    if(manager == null || manager[command] == null || (typeof manager[command]) != "function")
        return;

    manager[command]();
}

$(function(){
    $(".invoice-message").each(function(){
        var t = $(this), answers = t.find(".invoice-process-answer");

        if(answers.size() == 0)
        {
            setTimeout(function(){
                t.remove();
            }, 5000);
        }
        else
        {
            answers.each(function()
            {
                var click = this.onclick;
                if(click == null)
                    return false;

                var params = click();
                this.removeAttribute('onclick');
                $(this).click(function(){
                    if(!params["answer"])
                    {
                        ajaxQuery(
                                "operation=button.removeGeneratedSub&id=" + params["id"],
                                "/PhizIC/private/async/basket/subscription/claim.do",
                                function(data){}, null, false);
                    }

                    t.remove();
                });
            });
        }
    });
});
