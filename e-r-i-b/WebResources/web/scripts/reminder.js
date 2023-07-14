function ReminderManager(item, params)
{
    var t = this;
    t.item = $(item);
    t.params = params;
    t.changed = 0;

    if(item.onclick)
    {
        var commonParams = item.onclick();
        for(var k in commonParams)
        {
            if(commonParams.hasOwnProperty(k))
                t.params[k] = commonParams[k];
        }
    }

    t["enableReminder"] = t.item.find("input[name='field(enableReminder)']");
    t["dayOfMonth"]     = t.item.find("input[name='field(dayOfMonth)']");
    t["monthOfQuarter"] = t.item.find("input[name='field(monthOfQuarter)']");
    t["onceDate"]       = t.item.find("input[name='field(onceDate)']");
    t["type"]           = t.item.find("input[name='field(reminderType)']");

    t.item.find(".reminder-add-button").click(function(){
        t.enable();
    });

    t.item.find(".reminder-remove-button").click(function(){
        win.open("confirmDisableReminder" + t.params["id"]);
    });

    t.item.find(".visible-reminder-type").click(function(){
        showOrHideOperationBlock(t.item.find(".listOfOperation").get(0));
    });

    var dateField = t.item.find("input[name='field(chooseDateInvoice)']");
    t.item.find(".item-reminder-type").each(function(){
        var itemType = $(this), typeValue = this.onclick();
        this.removeAttribute('onclick');

        itemType.click(function(e){
            if(t.params["self"])
            {
                t.changed++;
                t.save();
            }
            cancelEventCrossBrowser(e);
            dateField.dpSetShowQuarter(typeValue == 'ONCE_IN_QUARTER').dpRerenderCalendar();
            t["type"].val(typeValue);
            t.updateParameters();

        });
    });

    var chooseDateElem = t.item.find(".visible-reminder-values>span").get(0);
    dateField.datePicker({displayClose: true, altField: chooseDateElem, dateFormat: 'dd.mm.yyyy', showYearMonth: true, startDate: new Date().asString('dd.mm.yyyy') })
            .change(function(){
                if(t.params["self"])
                {
                    t.changed++;
                    t.save();
                }
                var nextPayDate = new Str2Date(this.value);
                t["dayOfMonth"].val(nextPayDate.getDate());
                t["monthOfQuarter"].val((nextPayDate.getMonth()%3 + 1));
                t["onceDate"].val(this.value);
                t.updateParameters();
            });

    var confirmDisableWin = win.getWinItem("confirmDisableReminder" + t.params["id"]);
    if(confirmDisableWin != null)
        confirmDisableWin.manager = t;
}

ReminderManager.prototype = {

    updateParameters : function()
    {
        var t = this;
        var visibleEventType = t.item.find(".visible-reminder-type");
        var visibleValues = t.item.find(".visible-reminder-values>span");

        var nearDate;
        switch(t["type"].val())
        {
            case 'ONCE':
            {
                var chooseDate = new Str2Date(t["onceDate"].val());
                visibleEventType.text('ќднократно, ');
                visibleValues.text(chooseDate.getDate() + " " + monthToStringByNumber(chooseDate.getMonth()) + " " + chooseDate.getFullYear());
                t.item.find(".near-remind-date").text("");
                break;
            }

            case 'ONCE_IN_MONTH':
            {
                visibleEventType.text('≈жемес€чно,');
                visibleValues.text(t["dayOfMonth"].val() + "-го числа");
                nearDate = DateUtils.getNearDateByMonth(new Date(), t["dayOfMonth"].val());
                t.item.find(".near-remind-date").text("Ѕлижайший " + nearDate.getDate() + " " + monthToStringByNumber(nearDate.getMonth()) + " " + nearDate.getFullYear());
                break;
            }
            case 'ONCE_IN_QUARTER':
            {
                visibleEventType.text('≈жеквартально,');
                visibleValues.text(t["dayOfMonth"].val() + "-го числа " + t["monthOfQuarter"].val() + "-го мес€ца");
                nearDate = DateUtils.getNearDateByQuarter(new Date(), t["monthOfQuarter"].val(), t["dayOfMonth"].val());
                t.item.find(".near-remind-date").text("Ѕлижайший " + nearDate.getDate() + " " + monthToStringByNumber(nearDate.getMonth()) + " " + nearDate.getFullYear());
            }
        }
    },

    save : function()
    {
        var t = this;
        setTimeout(function()
        {
            if(t.changed > 1)
            {
                t.changed--;
                return;
            }

            t.item.find(".reminder-icon").each(function(){
                var iconBlock = $(this), icon = iconBlock.find("img");
                var source = icon.show().attr("src");
                iconBlock.css("background-image", "none");
                icon.attr("src", "").attr("src", source);
            });

            ajaxQuery(
                    "operation=button.changeReminderInfo&id=" + t.params["id"] + "&" + serializeDiv("reminder" + t.params["id"]),
                    "/PhizIC/private/async/payments/quicklyCreateReminder.do",
                    function(data)
                    {
                        if(data.state == "success")
                        {
                        }
                    }, "json", false);
            t.changed--;
        }, 3000);
    },

    disable : function()
    {
        var t = this;

        if(t.params["self"])
        {
            t.changed++;
            t.save();
        }
        t.item.find(".reminder-add-button").show();
        t.item.find(".reminder-remove-button").hide();
        t.item.find(".form-reminder-parameters").hide();
        t.item.closest(".reminder-row").find(".paymentTextLabel").hide();
        t["enableReminder"].val(false);
    },

    enable : function()
    {
        var t = this;

        if(t.params["self"])
        {
            t.changed++;
            t.save();
        }
        t.item.find(".reminder-add-button").hide();
        t.item.find(".reminder-remove-button").show();
        t.item.find(".form-reminder-parameters").show();
        t.item.closest(".reminder-row").find(".paymentTextLabel").show();
        t["enableReminder"].val(true);
    }
};

var DateUtils = {

    getNearDateByMonth : function(date, dateOfMonth)
    {
        var t = this;
        var currentDayOfMonth = date.getDate();
        var currentMonth = date.getMonth();
        if(currentDayOfMonth > dateOfMonth)
            date.setMonth(date.getMonth() + 1);
        if ((currentMonth +1) < date.getMonth())
            date.setMonth(date.getMonth() - 1);
        t.setDayOfMonth(date, dateOfMonth);
        return date;
    },

    getNearDateByQuarter : function(date, monthOfQuarter, dateOfMonth)
    {
        var t = this;
        var currentDayOfMonth = date.getDate();
        var currentMonthOfQuarter = t.getMonthOfQuarter(date);
        var currentMonth = date.getMonth();

        if(monthOfQuarter < currentMonthOfQuarter)
        {
            date.setMonth(date.getMonth() + (3 - (currentMonthOfQuarter - monthOfQuarter)));
        }
        else if(monthOfQuarter > currentMonthOfQuarter)
        {
            date.setMonth(date.getMonth() + (monthOfQuarter - currentMonthOfQuarter));
        }
        else if(currentDayOfMonth > dateOfMonth)
        {
            date.setMonth(date.getMonth() + 3);
        }
        if ((currentMonth +1) < date.getMonth())
        {
            date.setMonth(date.getMonth() - 1);
        }

        t.setDayOfMonth(date, dateOfMonth);
        return date;
    },

    getMonthOfQuarter : function(date)
    {
        return date.getMonth() % 3 + 1;
    },

    setDayOfMonth : function(date, dateOfMonth)
    {
        var daysOfMonth = 33 - new Date(date.getFullYear(), date.getMonth(), 33).getDate();
        date.setDate(daysOfMonth < dateOfMonth ? daysOfMonth : dateOfMonth);
    }
};

function processReminderCommand(el, command)
{
    if(el == null || isEmpty(command))
        return;

    var reminderWin = $(el).closest(".window");
    if(reminderWin.size() == 0)
        return;

    var reminderWinItem = reminderWin.get(0);
    var winId = reminderWinItem.id.substring(0, reminderWinItem.id.length - 3);

    var manager = win.getWinItem(winId).manager;
    if(manager == null || manager[command] == null || (typeof manager[command]) != "function")
        return;

    manager[command]();
}

$(function(){
    $(".form-reminder-block").each(function(){
        new ReminderManager(this, {});
    });
});