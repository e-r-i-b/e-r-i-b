/*
 Скрипты для страницы оформления заявки Сбербанк на каждый день
*/
//названия карточек, для которых доступна бонусная программа(по этим названиям идет поиск в мапе)
var visaClassicPG = 'RUR Visa Classic основная Подари жизнь';
var visaClassic = 'RUR Visa Classic основная';
var visaGoldPG = 'RUR Visa Gold основная Подари жизнь';
var visaGold = 'RUR Visa Gold основная';
var currencyMap       = {};
var cardListBonus      = {};
cardListBonus[visaClassicPG] = {
    name:visaClassicPG,
    nameForUser: "VISA Classic",
    CODEWAY4:'PRRCBVCP-Z',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "PG",
    CREDIT: "",
    cardTypeNumber: "111705",
    SHOW_BONUS: true
};
cardListBonus[visaGoldPG] = {
    name: visaGoldPG,
    nameForUser: "VISA Gold",
    CODEWAY4:'PRRCBVGP-Z',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "PG",
    CREDIT: "0",
    cardTypeNumber: "111718",
    SHOW_BONUS: true
};
var cardList      = {};
cardList[visaGold] = {
    name: visaGold,
    nameForUser: "VISA Gold",
    CODEWAY4:'PRRCBVGP--',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "0",
    BONUS: "0",
    CREDIT: "0",
    cardTypeNumber: "111718",
    SHOW_BONUS: true
};
cardList[visaClassic] = {
    name:visaClassic,
    nameForUser: "VISA Classic",
    CODEWAY4:'PRRCBVCP--',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "0",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111705",
    SHOW_BONUS: true
};
cardList['RUR MC standard основная'] = {
    name:"RUR MC standard основная",
    nameForUser: "Mastercard Standard",
    CODEWAY4:'PRRCBMSP--',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111703"
};
cardList['RUR MC Gold основная'] = {
    name:"RUR MC Gold основная",
    nameForUser: "Mastercard Gold",
    CODEWAY4:'PRRCBMGP--',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "0",
    cardTypeNumber: "111724"
};

cardList['RUR MC standard Бесконтактная основная, личная'] = {
    name:"RUR MC standard Бесконтактная основная, личная",
    nameForUser: "Mastercard Standard Бесконтактная",
    CODEWAY4:'PRRCBMQP--',
    CODEWAY4SHORT: "PRRC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111767"
};
currencyMap['RUB'] = cardList;

cardList = {};
cardList['USD MC standard основная'] = {
    name:"USD MC standard основная",
    nameForUser: "Mastercard Standard",
    CODEWAY4:'PRUCBMSP--',
    CODEWAY4SHORT: "PRUC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111703"
};
cardList['USD MC Gold основная'] = {
    name:"USD MC Gold основная",
    nameForUser: "Mastercard Gold",
    CODEWAY4:'PRUCBMGP--',
    CODEWAY4SHORT: "PRUC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "0",
    cardTypeNumber: "111724"
};
cardList['USD MC standard Бесконтактная основная, личная'] = {
    name:"USD MC standard Бесконтактная основная, личная",
    nameForUser: "Mastercard Standard Бесконтактная",
    CODEWAY4:'PRUCBMQP--',
    CODEWAY4SHORT: "PRUC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111767"
};
cardList['USD Visa Gold основная'] = {
    name:"USD Visa Gold основная",
    nameForUser: "VISA Gold",
    CODEWAY4:'PRUCBVGP--',
    CODEWAY4SHORT: "PRUC--",
    BIOAPPLET: "0",
    PINPAC: "0",
    BONUS: "0",
    CREDIT: "0",
    cardTypeNumber: "111718"
};
cardList['USD Visa Classic основная'] = {
    name:"USD Visa Classic основная",
    nameForUser: "VISA Classic",
    CODEWAY4:'PRUCBVCP--',
    CODEWAY4SHORT: "PRUC--",
    BIOAPPLET: "0",
    PINPAC: "0",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111705"
};
currencyMap['USD'] = cardList;

cardList = {};
cardList['EUR MC standard основная'] = {
    name:"EUR MC standard основная",
    nameForUser: "Mastercard Standard",
    CODEWAY4:'PRECBMSP--',
    CODEWAY4SHORT: "PREC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111703"
};
cardList['EUR MC Gold основная'] = {
    name:"EUR MC Gold основная",
    nameForUser: "Mastercard Gold",
    CODEWAY4:'PRECBMGP--',
    CODEWAY4SHORT: "PREC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "0",
    cardTypeNumber: "111724"
};
cardList['EUR MC standard Бесконтактная основная, личная'] = {
    name:"EUR MC standard Бесконтактная основная, личная",
    nameForUser: "Mastercard Standard Бесконтактная",
    CODEWAY4:'PRECBMQP--',
    CODEWAY4SHORT: "PREC--",
    BIOAPPLET: "0",
    PINPAC: "1",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111767"
};
cardList['EUR VISA Classic основная'] = {
    name:"EUR VISA Classic основная",
    nameForUser: "VISA Classic",
    CODEWAY4:'PRECBVCP--',
    CODEWAY4SHORT: "PREC--",
    BIOAPPLET: "0",
    PINPAC: "0",
    BONUS: "0",
    CREDIT: "",
    cardTypeNumber: "111705"
};
cardList['EUR Visa Gold основная'] = {
    name:"EUR Visa Gold основная",
    nameForUser: "VISA Gold",
    CODEWAY4:'PRECBVGP--',
    CODEWAY4SHORT: "PREC--",
    BIOAPPLET: "0",
    PINPAC: "0",
    BONUS: "0",
    CREDIT: "0",
    cardTypeNumber: "111718"
};

currencyMap['EUR'] = cardList;

function initCardList(index, type){
    var curr = $("#cardCurrency"+index).val();
    var cardList = currencyMap[curr];
    var select = "";
    for(var key in cardList)
    {
        var card = cardList[key];
        var selected = "";
        if (card.name == type ||
                (card.name == visaClassic && type == visaClassicPG) ||
                (card.name == visaGold && type == visaGoldPG))
            selected = "selected";
        select = select + "<option value='"+card.name+"' "+selected+">"+card.nameForUser+"</option>";
    }
    $("#cardTypeSelect"+index).html(select);

}
//Для карт с бонусной программой
function changeCard(index)
{
    var checkBonus = $("#agreeTick" + index).attr('checked');
    var cardName = $("#cardTypeSelect" + index).val();
    var cardList = currencyMap['RUB'];
    if (cardName.indexOf('Visa Classic') > 0)
    {
        if (checkBonus == true)
        {
            setCardData(cardListBonus[visaClassicPG], index);
        }
        else
        {
            setCardData(cardList[visaClassic], index);
        }
    }
    if (cardName.indexOf('Visa Gold') > 0)
    {
        if (checkBonus == true)
        {
            setCardData(cardListBonus[visaGoldPG], index);
        }
        else
        {
            setCardData(cardList[visaGold], index);
        }
    }
}
function changeCardList(index){
    initCardList(index);
    changeCardInfo(index, $("#cardTypeSelect"+index).val());
    //document.getElementById('agreeTick').checked = false;
}

function changeCardInfo(index, newValue)
{
    var curr = $("#cardCurrency"+index).val();
    if (newValue == undefined || newValue == "")
        newValue=$("#cardTypeSelect"+index).val();
    else
        $("#cardTypeSelect"+index).val(newValue);
    var cardList = currencyMap[curr];
    var cardInfo = cardList[newValue];
    if (newValue == visaClassicPG)
        cardInfo = cardListBonus[visaClassicPG];
    if (newValue == visaGoldPG)
        cardInfo = cardListBonus[visaGoldPG];
    setCardData(cardInfo, index);
}

function setCardData(cardInfo, index)
{
    $("#cardCODEWAY4"+index).val(cardInfo.CODEWAY4);
    $("#cardCODEWAY4SHORT"+index).val(cardInfo.CODEWAY4SHORT);
    $("#cardBIOAPPLET"+index).val(cardInfo.BIOAPPLET);
    $("#cardPINPAC"+index).val(cardInfo.PINPAC);
    $("#cardBONUSCODE"+index).val(cardInfo.BONUS);
    $("#cardCreditLimit"+index).val(cardInfo.CREDIT);
    $("#cardName"+index).val(cardInfo.nameForUser);
    $("#cardType"+index).val(cardInfo.name);
    $("#cardTypeNumber"+index).val(cardInfo.cardTypeNumber);
    if (cardInfo.SHOW_BONUS == true)
    {

        if (cardInfo.BONUS != '0')
            $("#agreeTick"+index).attr("checked", true);
        else
            $("#agreeTick"+index).removeAttr("checked");
        $("#availableBonus"+index).show();
    }
    else
    {
        $("#availableBonus"+index).hide();
    }
    $("#cardTypeSelect"+index).parent().find(".select_text").html(cardInfo.nameForUser);
}

// Добавление новой карты
//   openOnLoadPage - признак того, что нове окно с картой открывается автоматически
//   (т.е. в каком-то из полей данных по карте пользователем допущена ошибка ошибка)

function addNewCard(cardCount, openOnLoadPage)
{
    $("#cardCount").val(parseInt(cardCount) + 1);
    win.open("addCardWin");

    if(openOnLoadPage)
    {
        initCardList(cardCount);
        changeCardInfo(cardCount, $("input[name='field(cardType" + cardCount + ")']").val());
    }
    else
    {
        changeCardList(cardCount);
    }
}

 //редактирование карты
function editCard(cardNumber)
{
    win.open("editCardWin"+cardNumber);
    var type = $("#cardType" + cardNumber).val();
    initCardList(cardNumber, type);
    changeCardInfo(cardNumber, type);
    $('#editCardNumber').val(cardNumber);
}

//переход на следующий шаг
function nextStage(cardNum)
{
    initClientInfo(cardNum);
    new CommandButton("button.continue").click();
}

//добавление карты
function addCard(cardNum)
{
   initClientInfo(cardNum);
   new CommandButton("button.addCard").click();
}


function initClientInfo(cardNum)
{
    $("input[name='field(firstNameAndLastNameLatin)']").val($("#firstNameAndLastNameLatin"+cardNum).val());
    $("input[name='field(lastName)']").val($("#lastName"+cardNum).val());
    $("input[name='field(firstName)']").val($("#firstName"+cardNum).val());
    $("input[name='field(patrName)']").val($("#patrName"+cardNum).val());
    $("input[name='field(email)']").val($("#email"+cardNum).val());
    $("input[name='field(gender)']").val(getRadioValue($("input[name='gender"+cardNum+"']")));
    $("input[name='patrNameAbsent']").val($("#patrNameAbsent"+cardNum).attr("checked"));
}

function checkPatrName(el, num)
{
    var fieldPatrName =$('#patrName'+num)[0];
    fieldPatrName.disabled = el.checked;
    if(el.checked)
    {
        $('#patrNameAbsent').val('on');
    }
}
var REGULAR_PASSPORT_RF = 'REGULAR_PASSPORT_RF';
var REGULAR_PASSPORT_USSR = 'REGULAR_PASSPORT_USSR';
var RESIDENTIAL_PERMIT_RF = 'RESIDENTIAL_PERMIT_RF';
var TEMPORARY_PERMIT = 'TEMPORARY_PERMIT';
var FOREIGN_PASSPORT = 'FOREIGN_PASSPORT';
var MILITARY_IDCARD = 'MILITARY_IDCARD';
var OFFICER_IDCARD =  'OFFICER_IDCARD';

//Заполнение персональных данных
function changeDocType(needErase)
{
    var docType = $("#docType").val();
    if(needErase)
        $('#seriesAndNumber').remove();
    switch(docType)
    {
        case REGULAR_PASSPORT_RF:
            $('#seriesAndNumberContainer').html('<input type="text" name="field(seriesAndNumber)" size="45" value="" id="seriesAndNumber">');
            $('#seriesAndNumber').createMask(PASSPORT_NUMBER_AND_SERIES_TEMPLATE);
            break;

        case RESIDENTIAL_PERMIT_RF:
        case TEMPORARY_PERMIT:
        case FOREIGN_PASSPORT:
            oldValue = '';
            $('#seriesAndNumberContainer').html('<input type="text" name="field(seriesAndNumber)" size="45" value="" id="seriesAndNumber" onkeyup="otherDocsNumberHandler(event);">');
            break;
        case MILITARY_IDCARD:
        case OFFICER_IDCARD:
            $('#seriesAndNumberContainer').html('<input type="text" name="field(seriesAndNumber2)" size="45" value="" id="seriesAndNumber2">');
            $('#seriesAndNumberBuf').html('<input type="hidden" name="field(seriesAndNumber)" id="seriesAndNumber">');
            $('#seriesAndNumber2').createMask(MILITARY_IDENTITY_CARD_NUMBER);
            $('#seriesAndNumber2').focusout(function(eventObject)
            {
                $("#seriesAndNumber").val($("#seriesAndNumber2").val());
                if($("#seriesAndNumber").val().indexOf("_") > 0)
                    $("#seriesAndNumber").val($("#seriesAndNumber").val().substring(0,$("#seriesAndNumber").val().lastIndexOf("_")));
            });
            break;
        case REGULAR_PASSPORT_USSR:
            oldValue = '';
            $('#seriesAndNumberContainer').html('<input type="text" name="field(seriesAndNumber)" size="45" value="" id="seriesAndNumber" onkeyup="ussrPassportHandler(event);">');
            break;
    }
    if(needErase)
        eraseDocInputInfo();
}

function eraseDocInputInfo()
{
    $('#seriesAndNumber').val(null);
    $('#officeCode').val(null);
    $('#issuedBy').val(null);
    $('#fieldIssueDate').val(null);
    $('#fieldExpireDate').val(null);
}

var oldValue = "";
var FIRST_EXPR_EN  =  /^[IiVvXxLlCcMm]+$/;
var FIRST_EXPR_RU  =  /^[1УуХхЛлСсМм]+$/;
var SECOND_EXPR =  /^-$/;
var THIRD_EXPR  =  /^-[А-Яа-я]{1,2}(\s)?$/;
var FORTH_EXPR  =  /^-[А-Яа-я]{1,2}(\s)[0-9]{1,6}$/;

function romanExprHandler(expr)
{
    var cifs = expr.split("");
    var FIRST_EXPR = FIRST_EXPR_EN;
    for(var i=0; i <= cifs.length - 1; i++)
    {
        //На основании первого символа решаем, символы какого алфавита принимать дальше
        if(i == 0)
        {
            if(FIRST_EXPR_RU.test(cifs[i]))
            {
                FIRST_EXPR = FIRST_EXPR_RU;
            }
        }
        if(!FIRST_EXPR.test(cifs[i]))
            if(i > 0 && cifs[i] == '-')
                return true; // если встретили тире, то выходим из проверки
            else
                return false;
    }
    return true;
}
function ussrPassportHandler(event)
{
    var input = event.currentTarget;
    var newValue = input.value;
    var afterRoman;

    if(oldValue != newValue)
    {
        if(newValue != '')
        {
            if(oldValue.length - newValue.length == 1)
            {
                if(oldValue != newValue + oldValue.substring(oldValue.length - 1, oldValue.length))
                {
                    input.value = "";
                }
            }
            if(romanExprHandler(newValue))
            {
                afterRoman = newValue.indexOf("-") > 0 ? newValue.substring(newValue.indexOf("-"), newValue.length) : "";

                if(afterRoman != "")
                {
                    if(!FORTH_EXPR.test(afterRoman))
                    {
                        if(!THIRD_EXPR.test(afterRoman))
                        {
                            if(!SECOND_EXPR.test(afterRoman))
                            {
                                input.value = oldValue;
                            }
                        }
                    }
                }
            }
            else
            {
                input.value = oldValue;
            }
        }
    }
    oldValue = input.value.toUpperCase();
    input.value = input.value.toUpperCase();
}

var OTHER_DOCS_NUMBER_EXPR  =  /^[А-Яа-я0-9a-zA-Z]{1,25}$/;
var EMPTY_EXPR = /^$/;
function otherDocsNumberHandler(event)
{
    var input = event.currentTarget;
    var newValue = input.value;
    if(!OTHER_DOCS_NUMBER_EXPR.test(newValue))
    {   if(EMPTY_EXPR.test(newValue))
        {
            oldValue = newValue;
        }
        else
        {
            input.value = oldValue;
        }
    }
    else
    {
        oldValue = newValue;
    }
}

var REFILL_ON_SUM_EXPR_INT  =  /^[0-9]+$/;
var REFILL_ON_SUM_EXPR_FLOAT  =  /^[0-9]+(\.|,)[0-9]{0,2}$/;
var REFILL_ON_SUM_EXPR_WITH_COMMA  =  /^[0-9]+(,)[0-9]*$/;
var oldValueAutopay = "";

function refillOnSumFieldHandler()
{
    var input = $("#refillOnSum");
    var newValue = $(input).val();
    if(!(REFILL_ON_SUM_EXPR_INT.test(newValue) || REFILL_ON_SUM_EXPR_FLOAT.test(newValue)))
    {   if(EMPTY_EXPR.test(newValue))
        {
            oldValueAutopay = newValue;
        }
        else
        {
            $(input).val(oldValueAutopay);
        }
    }
    else
    {
        if(REFILL_ON_SUM_EXPR_WITH_COMMA.test(newValue))
        {
            newValue = newValue.replace(',','.');
            $(input).val(newValue);
        }
        oldValueAutopay = newValue;
    }
}
function checkRFCitizenship(el)
{
    var nationalityField = $('#nationality').parent().parent().parent();
    if(!el.checked)
    {
        $(nationalityField).css('display','block');
    }
    else
    {
        $(nationalityField).css('display','none');
    }
}

function checkRegistration(el)
{
    if(!el.checked)
    {
        $('#residentialBlock').css('display','block');
    }
    else
    {
        $('#residentialBlock').css('display','none');
    }
}

//Добавление услуг

function selectTariff(tariff)
{
    var otherTariff = (tariff == 'full' ? 'econom' : 'full');
    if($('.clicked_tariff')){
        $('.clicked_tariff').removeClass('stopAnim');
    }
    $("#tariff_" + tariff).addClass('clicked_tariff').removeClass('click_out');
    $("#tariff_" + otherTariff).removeClass('clicked_tariff').addClass('click_out').removeClass('noAnimate');
    $('#icon_mb_' + tariff).show();
    $('#mobileBankTariff').val(tariff);
}
function checkBonusProgram(elem, isChecked)
{
    if(isChecked)
    {
        elem.checked = false;
    }
    else
    {
        elem.checked = true;
    }
}

function removeCard(number)
{
    $('#removeCardNumber').val(number);
    new CommandButton("button.removeCard").click();
}

function toEdit()
{
    new CommandButton("button.backToEdit").click();
}

function showRequiredDocFields(doc)
{
   switch(doc)
   {
       case REGULAR_PASSPORT_RF:
           $('#officeCodeRow').show();
           $('#issuedByRow').show();
           $('#expireDateRow').hide();
           break;

       case REGULAR_PASSPORT_USSR:
       case MILITARY_IDCARD:
       case OFFICER_IDCARD:
           $('#officeCodeRow').hide();
           $('#issuedByRow').show();
           $('#expireDateRow').hide();
           break;

       case RESIDENTIAL_PERMIT_RF:
       case TEMPORARY_PERMIT:
           $('#officeCodeRow').hide();
           $('#issuedByRow').show();
           $('#expireDateRow').show();
           break;

       case FOREIGN_PASSPORT:
           $('#officeCodeRow').hide();
           $('#issuedByRow').hide();
           $('#expireDateRow').hide();
           break;
   }
}

var oldValueNames = "";
var SMALL_CHAR_SYMBOL_EXPR  =  /^[а-яa-z]+/;
function userNamesHandler(input)
{
    var newValue = $(input).val();

    newValue = newValue.replace(/ /g,"");

    if(oldValue != newValue)
    {
        if(SMALL_CHAR_SYMBOL_EXPR.test(newValue))
        {
            $(input).val(newValue.substring(0,1).toUpperCase() + newValue.substring(1, newValue.length));
        }
        oldValueNames = newValue;
    }
}
