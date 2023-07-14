/**
 * Вспомогательные функции для работы с поставщиками
 */

/**
 * Для интернет-магазинов задает предупреждающий вопрос
 */
function checkProviderType(isInternetShop)
{
    return isInternetShop == 'true' ? confirm("Данная операция приведет к отключению функционала, связанного с получателем") : true;
}

$(document).ready(function()
{
    if(document.getElementById("shortText") != null)
        initAreaMaxLengthRestriction("shortText", 255);
});

function openNationalBanksDictionary(callback, name, bic, isOurBank)
{
    window.setBankInfo = callback;

    var params = "&filter(bankName)=" + name + "&filter(BIC)="+bic;
    if (isOurBank!=null)
        params += "&filter(ourBank)="+isOurBank
    var win = window.open(document.webRoot+'/private/multiblock/dictionary/banks/national.do?action=getBankInfo' + params,
            'Banks', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1040, height=600");
    win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
}

function setBankInfo(resource)
{
    setElementByContainName('bankName', resource['name']);
    setElementByContainName('bankCode', resource['BIC']);
    setElementByContainName('corAccount', resource['account']);
}

function setPaymentServiceInfo(resource)
{
    setElementByContainName('serviceId', resource['id']);
    setElementByContainName('serviceName', resource['name']);
}

function setBillingInfo(resource)
{
    setElementByContainName('billingId', resource['id']);
    setElementByContainName('billingName', resource['name']);
}

function setDepartmentInfo(resource)
{
    setElementByContainName('departmentId', resource['id']);
    setElementByContainName('departmentName', resource['name']);
}

function doPrintProvider(id)
{
    var url = document.webRoot + "/dictionaries/provider/print.do";
    window.open(url + "?id=" + id, "", "width=700,height=750,resizable=1,menubar=1,toolbar=0,scrollbars=yes");
}
function selectProviderType(type)
{
    var classNameShow = "";
    var classNameHide = [".showBilling", ".showTax", ".showExternal"];
    switch (type)
    {
        case "TAXATION" :
            classNameShow = classNameHide[1];
            break;
        case "EXTERNAL" :
            classNameShow = classNameHide[2];
            break;
        default:
            classNameShow = classNameHide[0];
            break;
    }

    for (var i = 0; i < classNameHide.length; i++)
        $(classNameHide[i]).hide();

    $(classNameShow).show();
}
function clearBankDetailFields()
{
    ensureElement("inn").value            = "";
    ensureElement("kpp").value            = "";
    ensureElement("account").value        = "";
    ensureElement("bankCode").value       = "";
    ensureElement("bankName").value       = "";
    ensureElement("corAccount").value     = "";
}

function openAliasWindow(url, id)
{
    window.open(url + "?id=" + id, "", "width=" + (getClientWidth() - 100) + ",height=" + (getClientHeight() - 100) + ",resizable=0,menubar=1,toolbar=0,scrollbars=1");
}

function validateSelection()
{
    return checkSelection("selectedIds", 'Выберите алиас') ;
}

function toogleSmsTemplateAdditionalInfo()
{
    var standart = ensureElementByName('field(standartSmsTemplate)').checked;
    toogleElement('field(format)',!standart);
    toogleElement('field(defaultFormat)',standart);
    toogleElement('field(example)',!standart);
    toogleElement('field(defaultExample)',standart);
}
function toogleElement(elemName,on)
{
    var elem= ensureElementByName(elemName);
    elem.style.display = on?"":"none";
}
