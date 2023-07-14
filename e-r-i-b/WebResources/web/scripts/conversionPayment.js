//Функции для работы с конверсиоными платежами.
var amountRoundConstant = parseFloat(0.5 / 1024);
/**
 *  Поменяли сумму зачисления
 */
function buyCurrencyCommon(fromResourceCurrency, toResourceCurrency, buyAmount, sellAmount, needConvert)
{
    var exactAmount = ensureElement("exactAmount");
    exactAmount.value = "destination-field-exact";
    if (needConvert)           //условие заполнености  "списать с" и "перевести на"
    {
        if (fromResourceCurrency != toResourceCurrency)
        {
            if (isShowCoursePayment())      //переводы поддерживающие курс конверсии
            {
                var rate;
                var standardRate;
                if (!checkRates())
                    return;

                if (fromResourceCurrency == "RUB")
                {
                    rate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE"];
                    standardRate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_0"];
                }
                else
                {
                    rate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY"];
                    standardRate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_0"];
                }

                if (!isEmpty(rate))
                {
                    refreshConversionArea(exactAmount.value, buyAmount.value, standardRate, rate);
                }
                else
                {
                    clearConversionArea(exactAmount.value);
                }
            }
            else
            {
                clearConversionArea(exactAmount.value);
            }

            if (sellAmount && sellAmount !== undefined && buyAmount.value == '')
            {
                exactAmount.value = '';
            }
        }
        //если перевод с карты на счет и в одной валюте
        if (isCardToAccountPayment() && fromResourceCurrency == toResourceCurrency)
        {
            sellAmount.value = buyAmount.value;
        }
    }
}

//Переопределяется по месту
function isCardToAccountPayment()
{
    return false;
}

/*
 * Поменяли сумму списания
 */
function sellCurrencyCommon(fromResourceCurrency, toResourceCurrency, buyAmount, sellAmount, needConvert)
{
    var exactAmount = ensureElement("exactAmount");
    exactAmount.value = "charge-off-field-exact";
    if (needConvert)
    {
        if (fromResourceCurrency != toResourceCurrency)
        {
            if (isShowCoursePayment())      //переводы поддерживающие курс конверсии
            {
                var rate;
                var standardRate;
                if (!checkRates())
                    return;

                if (fromResourceCurrency == "RUB")
                {
                    //Курс продажи валюты банком (клиент покупает валюту)
                    rate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE"];
                    standardRate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_0"];
                }
                else
                {
                    //Курс покупки валюты банком (клиент продает валюту)
                    rate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY"];
                    standardRate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_0"];
                }
                if (!isEmpty(rate))
                {
                    refreshConversionArea(exactAmount.value, sellAmount.value, standardRate, rate);
                }
                else
                {
                    clearConversionArea(exactAmount.value);
                }
            }
            else
            {
                clearConversionArea(exactAmount.value);
            }

            if ((sellAmount.value == "") && (buyAmount.value == ""))
            {
                exactAmount.value = "";
            }
        }
    }
}

/**
 * Обновление данных конверсионного перевода. Пересчет сумм.
 */
function refreshConversionArea(exactAmount, amountValue, standardRate, rate)
{
    hideOrShow(ensureElement("courseRow"), false);
    hideOrShow(ensureElement("standartCourseRow"), false);

    hideOrShow(ensureElement("courseGainRow"), false);
    isCourseGainHide(standardRate, rate);

    var buyAmountRow = ensureElement("buyAmount");
    var sellAmountRow = ensureElement("sellAmount");

    if (exactAmount == "charge-off-field-exact" && buyAmountRow != null)
        $(buyAmountRow).setMoneyValue(getBuyAmount(amountValue.replace(",", ".").replace(/ /g, ""), rate));
    if (exactAmount == "destination-field-exact" && sellAmountRow != null)
        $(sellAmountRow).setMoneyValue(getSellAmount(amountValue.replace(",", ".").replace(/ /g, ""), rate));

    setCourseGain(standardRate, rate);
}

//Подсчет значения поля "Моя выгода" (м.б. переопределена локально в формах с конверсией)
function setCourseGain(standartRate, rate)
{
    //Если "Моя выгода" скрыта, то и не рассчитываем её
    if (isCourseGainHide(standartRate, rate))
        return;
    var fromResource = getElementValue("fromResource");
    //Для переводов с рублевых счетов всегда берем обратный курс
    if ( currencies[fromResource] == 'RUB' )
    {
        rate = 1 / rate;
        standartRate = 1 / standartRate;
    }
    var sellAmountValue = getSellAmountValue();
    var diff;
    if ( !isEmpty(sellAmountValue.replace(/ /g,"")) )
    {
        diff = parseFloat((parseFloat(rate) - parseFloat(standartRate)) * parseFloat(sellAmountValue.replace(",",".").replace(/ /g,"")) * 100) / 100;
    }

    var toResource   = getElementValue("toResource");
    var toResourceCurrency = currencies[toResource];
    if ( isEmpty(toResourceCurrency) )
    {
        //для открытия вкладов
        toResourceCurrency = getElementValue('toResourceCurrency');
        if ( isEmpty(toResourceCurrency) )
            return;
    }
    //установка значения "Моя выгода"
    $("#courseGainSpan").html(parseFloat(diff).toFixed(2) + " " + currencySignMap.get(toResourceCurrency));
}

//Скрытие или отображение поля моя выгода
function isCourseGainHide(standartRate, rate)
{
    var fromResource = getElementValue("fromResource");
    //Для переводов с рублевых счетов всегда берем обратный курс
    if ( currencies[fromResource] == 'RUB' )
    {
        rate = 1 / rate;
        standartRate = 1 / standartRate;
    }
    var diff = parseFloat(rate) - parseFloat(standartRate);
    if (diff < 0)
    {
        $('#courseGainRow').hide();
        return true;
    }
    return false;
}

function getSellAmountValue()
{
    var sellAmount = ensureElement("sellAmount");
    if ( isNotEmpty(sellAmount) )
        return isEmpty(sellAmount.value) ? '0' : sellAmount.value;
    return '0';
}

function clearConversionArea(exactAmount)
{
    hideOrShow(ensureElement("courseRow"), true);
    hideOrShow(ensureElement("standartCourseRow"), true);
    hideOrShow(ensureElement("courseGainRow"), true);

    if (exactAmount == "charge-off-field-exact")
        ensureElement("buyAmount").value = "";
    if (exactAmount == "destination-field-exact")
        ensureElement("sellAmount").value = "";

    $("#course").text("");
    $("#standartCourseSpan").text("");
    $("#courseGainSpan").text("");

    ensureElement("courseValue").value = "";
    ensureElement("premierShowMsg").value = "";
    var standartCourseElement = ensureElement("standartCourseValue");
    if (standartCourseElement != null && standartCourseElement != undefined)
        standartCourseElement.value = "";
}

//Расчет суммы с округлением по заданному алгоритму.
//Алгоритм округления: отбрасываем все что после сотых. Зачисляем клиенту меньшую сумму.
function getBuyAmount(amount, rate)
{
    if (amount != '' && !isNaN(amount))
    {
        //Для переводов с рублевых счетов всегда берем обратный курс
        if (getFromResourceCurrencyCode() == 'RUB')
            rate = 1 / rate;
        return getAmount(amount, rate);
    }
    return "";
}

//Расчет суммы с округлением по заданному алгоритму.
//Алгоритм округления:
//остаток = полученная сумма минус полученная сумма, округленная до 2х знаков после запятой
//значение = остаток*100, сравниваем с константой 0,5/1024
//1) если значение больше константы то добавляем к сумме 0.01
//2) если менше либо равно то сумма откруггленная до 2х знаков после запятной
function getSellAmount(amount, rate)
{
    if (amount != '' && !isNaN(amount))
    {
        //Для переводов не с рублевых счетов при расчете суммы списания всегда берем обратный курс
        if (getFromResourceCurrencyCode() != 'RUB')
            rate = 1 / rate;
        return getAmount(amount, rate);
    }
    return "";
}

function getAmount(amount, rate)
{
    var convertedSum = parseFloat(amount * rate).toFixed(13);
    var resultSum = parseInt(convertedSum * 100) / 100;
    var rest = convertedSum - resultSum;
    if (amountRoundConstant < rest * 100)
        return parseFloat(resultSum + parseFloat(0.01)).toFixed(2);
    return parseFloat(resultSum).toFixed(2);
}

//Валюта источника списания средств (если нужно переопределяется по месту)
function getFromResourceCurrencyCode()
{
    var fromResource = getElementValue("fromResource");
    if (fromResource != undefined && currencies != undefined)
        return currencies[fromResource];
    return "";
}

//переводы поддерживающие курс конверсии.
function isShowCoursePayment()
{
    return true; //дефолтный метод, переопредяем по месту.
}

//доступны ли курсы конверии
function checkRates()
{
    return true; //дефолтный метод, переопределям по месту.
}