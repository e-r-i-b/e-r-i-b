var createUrl = document.webRoot + '/private/async/moneybox/claim/create.do';
var viewUrl = document.webRoot + '/private/async/moneybox/view.do';
var printURL = document.webRoot + '/private/moneybox/scheduleReport/print.do';
var changeURL = document.webRoot + '/private/moneyBox/changeStatus.do';
var editUrl = document.webRoot + '/private/async/moneybox/edit.do';
var editDraftClaimUrl = document.webRoot + '/private/async/moneybox/claim/edit.do';
var formEditName = 'EditMoneyBoxClaim';
var moneyBoxWinId = 'moneyBoxWinDiv';
var moneyBoxViewWinId = 'moneyBoxViewWinDiv';
var moneyBoxViewErrors = 'errorsViewMoneyBox';
var moneyBoxViewWarnings = 'warningsViewMoneyBox';

function openCreateMoneyBoxFromCardWindow(cardCode)
{
    if (typeof RSAObject != "undefined")
    {
        new RSAObject().toHiddenParameters();
    }

    var element = document.getElementById(moneyBoxWinId);
    element.dataWasLoaded = false;

    var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxWinId);
    var params = '?fromResource=' + cardCode;
    ajaxUrl.value = createUrl + params;
    win.open(moneyBoxWinId);
    return false;
}

function openCreateMoneyBoxToAccountWindow(accountCode)
{
    if (typeof RSAObject != "undefined")
    {
        new RSAObject().toHiddenParameters();
    }

    var element = document.getElementById(moneyBoxWinId);
    element.dataWasLoaded = false;

    var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxWinId);
    var params = '?toResource=' + accountCode;
    ajaxUrl.value = createUrl + params;
    win.open(moneyBoxWinId);
    return false;
}

function openCreateMoneyBoxWindowByResources(cardCode, accountCode, sellAmount)
{
    var element = document.getElementById(moneyBoxWinId);
    element.dataWasLoaded = false;

    var ajaxUrl = document.getElementById('hiddenAjaxUrl'+moneyBoxWinId);
    var params = "";
    if(cardCode!=null && cardCode!='')
        params = '?fromResource='+cardCode;

    if(accountCode!=null && accountCode!='')
    {
        if (params == "")
            params = '?';
        else
            params = params + "&";
        params = params + 'toResource='+accountCode;
    }
    params += '&longOfferStartDate=' + getNextMonthDate();
    params += '&sellAmount=' + sellAmount;
    if (typeof RSAObject != "undefined")
    {
        params += '&' + new RSAObject().toRequestParameters();
    }
    ajaxUrl.value = createUrl + params;

    win.open(moneyBoxWinId);
    return false;
}

function openViewLinkMoneyBoxWindows(id)
{
    var element = document.getElementById(moneyBoxViewWinId);
    element.dataWasLoaded = false;
    var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxViewWinId);
    var params = '?linkId='+id;
    ajaxUrl.value = viewUrl + params;
    win.aditionalData(moneyBoxViewWinId, {onAjaxLoad : function(id, data){
        win.init(ensureElement(moneyBoxViewWinId));
        if(window.mCustomScrollbars)
            mCustomScrollbars(moneyBoxViewWinId);

        var errors = $(data).find("#ajaxInactiveExternalSystemResponceError");
        if(errors.size() > 0)
        {
            errors.find(".itemDiv").each(function(){
                addMessage($(this).html());
            });
            return false;
        }

        return true;
    }});
    win.open(moneyBoxViewWinId);
    return false;
}

function openViewClaimMoneyBoxWindows(id)
{
    var element = document.getElementById(moneyBoxViewWinId);
    element.dataWasLoaded = false;
    var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxViewWinId);
    var params = '?claimId='+id;
    ajaxUrl.value = viewUrl + params;
    win.aditionalData(moneyBoxViewWinId, {onAjaxLoad : function(data){
        win.init(ensureElement(moneyBoxViewWinId));
        if(window.mCustomScrollbars)
            mCustomScrollbars(moneyBoxViewWinId);
    }});
    win.open(moneyBoxViewWinId);
    return false;
}

function onCloseMoneyBoxCreateWindow()
{
    var el = ensureElement(moneyBoxWinId);
    if (el != null)
    {
        el.isApplyMask = false;
    }
    return true;
}

function onCloseViewMoneyBoxWindows()
{
    var el = ensureElement(moneyBoxViewWinId);
    if (el != null)
    {
        el.isApplyMask = false;
    }
    return true;
}

function closeMoneyBoxQuestionWindow(winId)
{
    win.close(winId);
    win.close(moneyBoxViewWinId);
}

function openMoneyBoxQuestionWindow(winId)
{
    win.open(winId);
}

function editMoneyBox(number)
{
    win.close(moneyBoxViewWinId);
    var element = document.getElementById(moneyBoxWinId);
    element.dataWasLoaded = false;
    var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxWinId);
    var params = '?autoSubNumber=' + number;
    params += '&form=' + formEditName;
    ajaxUrl.value = editUrl + params;
    win.open(moneyBoxWinId);
}

function editMoneyBoxClaim(paymentId)
{
    win.close(moneyBoxViewWinId);
    var element = document.getElementById(moneyBoxWinId);
    element.dataWasLoaded = false;
    var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxWinId);
    var params = '?id=' + paymentId;
    params += '&form=' + formEditName;
    ajaxUrl.value = editDraftClaimUrl + params;
    win.open(moneyBoxWinId);
}

function connectMoneyBox(winName, moneyBoxId)
{
    executeOperationView('operation=button.confirmMoneyBox&id=' + moneyBoxId, document.webRoot +'/private/async/moneybox/claim/confirm.do',
    function(data){
        if (data.success)
        {
            if (data.messages != null && data.messages.length > 0)
            {
                var messages = data.messages;
                for (var k = 0; k < messages.length; k++)
                    addMessage(messages[k], null, true);
            }
            win.close(moneyBoxViewWinId);
        }
        else
        {
           processErrors(data);
        }
    });
    win.close(winName);
}

function pauseMoneyBox(winName, id)
{
    var paramId = 'id=' + id;
    var paramState = 'changePaymentStatusType=REFUSE';
    var params = paramId + '&' + paramState;
    executeOperationView(params, changeURL , moneyBoxCallBack);
    win.close(winName);
}

function resumeMoneyBox(winName, id)
{
    var paramId = 'id=' + id;
    var paramState = 'changePaymentStatusType=RECOVER';
    var params = paramId + '&' + paramState;
    executeOperationView(params, changeURL , moneyBoxCallBack);
    win.close(winName);
}

function disableMoneyBox(winName, id, type)
{
    var paramId = 'id=' + id;
    if(type=='CLAIM')
    {
        executeOperationView(paramId+'&operation=button.remove', createUrl , moneyBoxCallBack);
    }
    else
    {
        var paramState = 'changePaymentStatusType=CLOSE';
        var params = paramId + '&' + paramState;
        executeOperationView(params, changeURL , moneyBoxCallBack);
    }
    win.close(winName);
}

function executeOperationView(params, actionURL, callbackFunction)
{
    ajaxQuery(
            params,
            actionURL,
            function(data)
            {
                callbackFunction(data);
            },
            "json"
    );
}

function clearAllMessages()
{
    removeAllMessages(moneyBoxViewWarnings);
    removeAllErrors(moneyBoxViewErrors);
    $("#moneyBoxViewWinDiv .form-row").each(function ()
    {
        payInput.formRowClearError(this);
    });
}

function moneyBoxCallBack(data)
{
    removeAllMessages();
    removeAllErrors();
    clearAllMessages();
    if (data.success)
    {
        if (data.messages != null && data.messages.length > 0)
        {
            var messages = data.messages;
            for (var k = 0; k < messages.length; k++)
                addMessage(messages[k], moneyBoxViewWarnings, true);
        }
    }
    else
    {
       processErrors(data);
    }
}

function processErrors(data)
{
    if (data.errors != null && data.errors.length > 0)
    {
        var errors = data.errors;
        for (var i = 0; i < errors.length; i++)
            addError(errors[i], moneyBoxViewErrors, true);
    }

    if (data.errorFields != null && data.errorFields.length > 0)
    {
        var errorFields = data.errorFields;
        for (var j = 0; j < errorFields.length; j++)
            payInput.fieldError(errorFields[j].name, errorFields[j].value);
    }

    if (data.messages != null && data.messages.length > 0)
    {
        var messages = data.messages;
        for (var k = 0; k < messages.length; k++)
            addMessage(messages[k], moneyBoxViewWarnings, true);
    }
}

function openPrintScheduleReport(event, id)
{
    var param = "?id=" + id;
    openWindow(event, printURL + param, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
}

function getNextMonthDate()
{
    var startDate = new Date();
    var dayNumber = startDate.getDate();
    startDate.setDate(1);
    startDate.setMonth(startDate.getMonth()+1);
    var day;
    var month = ("00" + (startDate.getMonth() + 1)).slice(-2);
    var year = ("0000" + startDate.getFullYear()).slice(-4);
    if(startDate.getDaysInMonth()<dayNumber)
        day = ("00" + startDate.getDaysInMonth()).slice(-2);
    else
        day = ("00" + dayNumber).slice(-2);

    return  year + '-' + month + '-' + day;
}