// Функции для форм открытия вкладов

var rates   = new Array();
var accounts    = new Array();
var currencies  = new Array();
var cardAccounts = new Array();
var gSegment;
var gPriorSegment = false;
var gPromoCodeName;

var accountOpeningClaim =
{
    previosDate: ''
};

function removeError()
{
    var closingDatePayInput = payInput.getParenDivByName('closingDate');
    closingDatePayInput.error = false;
    var errorDiv = findChildByClassName(closingDatePayInput, payInput.ERROR_DIV_CLASS_NAME);
    if (errorDiv != undefined)
    {
        errorDiv.style.display = 'none';
    }
    if (payInput.lastClickedRow != undefined && payInput.lastClickedRow.id == 'depositPeriodRow')
    {
        payInput.onClick(closingDatePayInput);
    }
    else
    {
        payInput.rollBack(closingDatePayInput);
    }
}

function hideOrShowCourse()
{
    showOrHidePremierWarning(false);
    var courseRow           = ensureElement('courseRow');
    var standartCourseRow   = ensureElement('standartCourseRow');
    var courseGainRow       = ensureElement('courseGainRow');
    var toResourceCurrency  = getElementValue('toResourceCurrency');
    var fromResource        = getElementValue('fromResource');
    if (fromResource != "")
    {
        var fromResourceCurrency = currencies[fromResource];
        if (fromResourceCurrency!=undefined && toResourceCurrency!=undefined && toResourceCurrency != fromResourceCurrency)
        {
            if (isShowCoursePayment())      //переводы поддерживающие курс конверсии
            {
                var rate;
                var rateShowMsg;
                var standartRate;
                if (fromResourceCurrency == "RUB")
                {
                    rate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE"];
                    rateShowMsg = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_SHOW_MSG"];
                    standartRate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_0"];
                }
                else
                {
                    rate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY"];
                    rateShowMsg = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_SHOW_MSG"];
                    standartRate = rates[fromResourceCurrency + "|" + toResourceCurrency]["BUY_0"];
                }

                if (!isEmpty(rate))
                {
                    showCourse(fromResourceCurrency, toResourceCurrency, rate, standartRate,rateShowMsg);
                    hideOrShow(courseRow, false);
                    hideOrShow(standartCourseRow, false);
                    hideOrShow(courseGainRow, false);
                    return;
                }
            }
        }
    }
    hideOrShow(courseRow, true);
    hideOrShow(standartCourseRow, true);
    hideOrShow(courseGainRow, true);
}

function setOperationCodeInfo(operationCodeInfo)
{
    setElement('operationCode', "{VO" + operationCodeInfo["operationCode"] + "}");
}

function showHideOperationCodeRow()
{
    var fromResource = ensureElement('fromResource').value;
    var account = accounts[fromResource];
    var accountNumber = account != undefined ? account.accountNumber : "";
    var accountFromNum = (fromResource.indexOf('card:') != -1) ? cardAccounts[fromResource] : accountNumber;
    var hide = !isResident(accountFromNum);

    hideOrShow(ensureElement("operationCodeRow"), hide);
    if (hide)
        setElement("operationCode", "");
}

function showHideOperationTypeRow()
{
    var fromResource = getElementValue("fromResource");
    var toCurrency = getElementValue("toResourceCurrency");
    var courseRow = ensureElement("courseRow");
    var standartCourseRow = ensureElement("standartCourseRow");
    var courseGainRow = ensureElement("courseGainRow");
    var buyAmountRow = ensureElement("buyAmountRow");
    var buyAmountLabel = ensureElement("buyAmountTextLabel");
    var sellAmountRow = ensureElement("sellAmountRow");

    hideOrShow(buyAmountRow, false);
    hideOrShow(sellAmountRow, true);
    hideOrShow(courseRow, true);
    hideOrShow(standartCourseRow, true);
    hideOrShow(courseGainRow, true);

    buyAmountLabel.innerHTML = "Сумма:";

    if (fromResource != "")
    {
        // очищаем конверсию, т.к нужная установится в hideOrShowCourse
        ensureElement("courseValue").value = "";
        ensureElement("premierShowMsg").value = "";
        var standartCourseElement = ensureElement("standartCourseValue");
        if (standartCourseElement != null && standartCourseElement != undefined)
            standartCourseElement.value = "";

        var fromCurrency = currencies[fromResource];

        $("#sellAmountCurrency").text(currencySignMap.get(fromCurrency));
        $("#buyAmountCurrency").text(currencySignMap.get(toCurrency));

        if (isCardToAccountPayment())       //При открытии с карты заполнять можно только сумму зачисления
        {
            hideOrShow(sellAmountRow, true);
            hideOrShow(buyAmountRow, false);
            buyAmountLabel.innerHTML = "Сумма зачисления:";
        }
        else if (fromCurrency != toCurrency)//для неравных валют 2 суммы
        {
            hideOrShow(sellAmountRow, false);
            hideOrShow(courseRow, false);
            hideOrShow(standartCourseRow, false);
            hideOrShow(courseGainRow, false);
            buyAmountLabel.innerHTML = "Сумма зачисления:";
        }
        else //валюты равны ->1 сумма (списания)
        {
            hideOrShow(sellAmountRow, true);
            buyAmountLabel.innerHTML = "Сумма:";
        }
    }
}

function buyCurrency()
{
    var toResourceCurrency = getElementValue("toResourceCurrency");
    var fromResource = getElementValue("fromResource");
    var exactAmount = ensureElement("exactAmount");
    var sellAmount = ensureElement("sellAmount") || {value : ''};
    var buyAmount  = ensureElement("buyAmount")  || {value : ''};
    buyCurrencyCommon(currencies[fromResource], toResourceCurrency, buyAmount, sellAmount, fromResource != "");
}

function sellCurrency()
{
    var toResourceCurrency = getElementValue("toResourceCurrency");
    var fromResource = getElementValue("fromResource");
    var exactAmount = ensureElement("exactAmount");
    var sellAmount = ensureElement("sellAmount") || {value : ''};
    var buyAmount  = ensureElement("buyAmount")  || {value : ''};
    sellCurrencyCommon(currencies[fromResource], toResourceCurrency, buyAmount, sellAmount, fromResource != "");
}

function showCourse(fromCurrency, toCurrency, rate, standartRate, rateShowMsg)
{
    $("#course").html(rate + " " + currencySignMap.get(fromCurrency) + "<i>&#8594;</i>" + currencySignMap.get(toCurrency));
    $("#standartCourseSpan").html(standartRate + " " +  currencySignMap.get(fromCurrency) + "&#8594;" + currencySignMap.get(toCurrency));

    setCourseGain(standartRate, rate);

    ensureElement("courseValue").value = rate;
    ensureElement("premierShowMsg").value = rateShowMsg;

    var standartCourseElement = ensureElement("standartCourseValue");
    if ( isNotEmpty(standartCourseElement) )
        standartCourseElement.value = standartRate;

    showOrHidePremierWarning(rateShowMsg);
}

function getAmount(amount, rate)
{
    if (amount != '' && !isNaN(amount))
        return parseFloat((parseFloat(amount)*rate).toFixed(4)).toFixed(2);
    return "";
}

function isShowCoursePayment()
{
    var fromResource = getElementValue("fromResource");
    return !(fromResource.indexOf('card:') != -1 || fromResource.indexOf('card:') != -1);
}

function isCardToAccountPayment()
{
    var fromResource = getElementValue("fromResource");
    return fromResource.indexOf('card:') != -1;
}

function isAccountToPayment()
{
    var fromResource = getElementValue("fromResource");

    return fromResource.indexOf('account:') != -1;
}

function fillCurrency()
{
    var exactAmount = ensureElement("exactAmount");
    if (!isEmpty(exactAmount))
    {
        if (exactAmount.value == "charge-off-field-exact")
            sellCurrency();
        if (exactAmount.value == "destination-field-exact")
            buyCurrency();
    }
}

function setDepositClosingDate()
{
    var documentDateStr = getElement('openDate').value.split(".");

    var dayFrom = parseInt(documentDateStr[0], 10);
    var monthFrom = parseInt(documentDateStr[1], 10);
    var yearFrom = parseInt(documentDateStr[2], 10);

    var days = parseInt(getElement("periodDays").value, 10);
    var months = parseInt(getElement("periodMonths").value, 10);
    var years = parseInt(getElement("periodYears").value, 10);

    if (isNaN(days))
        days = 0;
    if (isNaN(months))
        months = 0;
    if (isNaN(years))
        years = 0;

    var date = new Date(yearFrom, monthFrom - 1, dayFrom);
    date.addYears(years);
    date.addMonths(months);
    date.addDays(days);

    var year = ("0000" + date.getFullYear()).slice(-4);
    var month = ("00" + (date.getMonth() + 1)).slice(-2);
    var day = ("00" + date.getDate()).slice(-2);

    var sourceDate = day + "." + month + "." + year;
    setElement("closingDate", sourceDate);
    $('.dot-date-pick').dpSetSelected($('.dot-date-pick').val());
}

function setDepositPeriod()
{
    var sourceDate = getElement('closingDate').value;
    var closeDateStr = getElement('closingDate').value.split(".");
    var CLOSING_DATE_MESSAGE = "Срок вклада должен быть больше текущей даты.";

    // Если срок вклада не указан - уходим
    if (closeDateStr == "")
    {
        return;
    }
    else if (!templateObj.DATA.validate(sourceDate))
    {
        return;
    }

    var documentDateStr = getElement('openDate').value.split(".");
    var dayFrom = parseInt(documentDateStr[0], 10);
    var monthFrom = parseInt(documentDateStr[1], 10);
    var yearFrom = parseInt(documentDateStr[2], 10);

    var dayTo = parseInt(closeDateStr[0], 10);
    var monthTo = parseInt(closeDateStr[1], 10);
    var yearTo = parseInt(closeDateStr[2], 10);

    var dateFrom = new Date(yearFrom, monthFrom - 1, dayFrom).zeroTime();
    var dateTo = new Date(yearTo, monthTo - 1, dayTo).zeroTime();

    if (dateFrom.getTime() >= dateTo.getTime())
    {
        addMessage(CLOSING_DATE_MESSAGE);
        setElement("periodDays", "");
        setElement("periodMonths", "");
        setElement("periodYears", "");
        return;
    }

    var years = 0;
    var months = 0;
    var days = 0;

    var start = new Date(yearFrom, monthFrom - 1, dayFrom).zeroTime();
    while (start.addYears(1).getTime() <= dateTo.getTime())
    {
        years++;
    }

    start.setTime(dateFrom.addYears(years).getTime());
    while (start.addMonths(1).getTime() <= dateTo.getTime())
    {
        months++;
    }

    start.setTime(dateFrom.addMonths(months).getTime());
    while (start.addDays(1).getTime() <= dateTo.getTime())
    {
        days++;
    }

    removeMessage(CLOSING_DATE_MESSAGE);
    setElement("periodDays", days);
    setElement("periodMonths", months);
    setElement("periodYears", years);

    $('.dot-date-pick').dpSetSelected($('.dot-date-pick').val());
}

function setPercentPaymentOrderToCard(value)
{
    var percentTransferCardSource = getElement("percentTransferCardSource");
    if (percentTransferCardSource == undefined)
        return;
    percentTransferCardSource.disabled = !value;
    if (value)
    {
        percentTransferCardSource.selectedIndex = 0;
        if($('div[name=helpMessageIfCardUsing]')) {
            $('div[name=helpMessageIfCardUsing]').show();
        }
    }
    else
    {
        if($('div[name=helpMessageIfCardUsing]')) {
            $('div[name=helpMessageIfCardUsing]').hide();
        }
    }
}

function setPeriodRadioButton(s)
{
    if (s)
    {
        getElement("periodDays").readOnly = false;
        getElement("periodMonths").readOnly = false;
        getElement("periodYears").readOnly = false;
        getElement("closingDate").readOnly = true;
    }
    else
    {
        getElement("periodDays").readOnly = true;
        getElement("periodMonths").readOnly = true;
        getElement("periodYears").readOnly = true;
        getElement("closingDate").readOnly = false;
    }
    setKeyUpFunctionOnPeriodFields();
    $('.dot-date-pick')[s ? 'addClass' : 'removeClass']('dp-disabled');
}

function setKeyUpFunctionOnPeriodFields()
{
    getElement("periodDays").onkeyup = getElement("periodDays").readOnly ? '' : function(){setDepositClosingDate();updateScrollVal();};
    getElement("periodMonths").onkeyup = getElement("periodMonths").readOnly ? '' : function(){setDepositClosingDate();updateScrollVal();};
    getElement("periodYears").onkeyup = getElement("periodYears").readOnly ? '' : function(){setDepositClosingDate();updateScrollVal();};
}

function checkRadioButton(val)
{
    if (val == '')
    {
        $('input[type=radio][value=period]').eq(0).attr('checked', true);
        setKeyUpFunctionOnPeriodFields();
    }
    else
    {
        $('input[type=radio][value='+val+']').eq(0).attr('checked', true);
        setPeriodRadioButton('period' == val);
    }
}

function checkPercentTransferSourceRadio(val)
{
    if (val == '')
    {
        $('input[type=radio][value=card]').eq(0).attr('checked', true);
        val = 'card';
    }
    else
    {
        $('input[type=radio][value='+val+']').eq(0).attr('checked', true);
    }
    setPercentPaymentOrderToCard(val == 'card');
}

function calculateDays()
{
    var sourceDate = getElement('closingDate').value;
    var closeDateStr = sourceDate.split(".");
    var days = 0;

    // Если срок вклада не указан - уходим
    if (closeDateStr == "")
    {
        return days;
    }
    else if (!templateObj.DATA.validate(sourceDate))
    {
        return days;
    }

    var documentDateStr = getElement('openDate').value.split(".");
    var dayFrom = parseInt(documentDateStr[0], 10);
    var monthFrom = parseInt(documentDateStr[1], 10);
    var yearFrom = parseInt(documentDateStr[2], 10);

    var dayTo = parseInt(closeDateStr[0], 10);
    var monthTo = parseInt(closeDateStr[1], 10);
    var yearTo = parseInt(closeDateStr[2], 10);

    var dateFrom = new Date(yearFrom, monthFrom - 1, dayFrom, 12);
    var dateTo = new Date(yearTo, monthTo - 1, dayTo, 12);

    while (increaseDay(dateFrom).getTime() <= dateTo.getTime())
    {
        if (!(dateFrom.getDate() == 29 && dateFrom.getMonth() == 2))
            days++;
    }

    return days;
}

function increaseDay(date)
{
    date.setDate(date.getDate() + 1);
    return date;
}

function setInterestRate(balanceNode)
{
    $("#interestRateResult").html(balanceNode.rate + "%");
    setElement("interestRate", balanceNode.rate);

    setElement("depositSubType", balanceNode.subType);
    setElement("minAdditionalFee", balanceNode.minAdditionalFee);
    if (gSegment != undefined && gSegment != '' && !gPriorSegment && parseInt(gSegment) > 1)
    {
        if (parseInt(balanceNode.segmentCode) > 1)
        {
            setElement("promoCodeName", gPromoCodeName);
            setElement("usePromoRate", 'true');
            hideOrShow("promoCodeName", false);
            $('#interestRateResult').parent().addClass('promoInterestRate')
        }
        else
        {
            setElement("promoCodeName", '');
            setElement("usePromoRate", 'false');
            hideOrShow("promoCodeName", true);
            $('#interestRateResult').parent().removeClass()
        }
    }
    else if (parseInt(balanceNode.segmentCode) == 1)
        setElement("isPension", "true");
    else if (parseInt(balanceNode.segmentCode) == 0)
        setElement("isPension", "false");
}

function addCurrenciesOptions(toResourceCurrency, prevCurrencyValue, mapCurrencies)
{
    var firstCurrencies = new Array('RUB','USD','EUR');

    for (var i = 0; i < firstCurrencies.length; i++)
    {
        var mappedCurrency = mapCurrencies[firstCurrencies[i]];
        if (mappedCurrency != undefined)
        {
            addOption(toResourceCurrency, mappedCurrency, currencySignMap.get(mappedCurrency), prevCurrencyValue == mappedCurrency);
            delete mapCurrencies[mappedCurrency];
        }
    }
    for (var key in mapCurrencies)
    {
        addOption(toResourceCurrency, key, currencySignMap.get(key), prevCurrencyValue == key);
    }
}

function removeFieldErrorMessage(fieldName)
{
    if (fieldName == undefined)
        return;
    payInput.fieldError(fieldName, '');
    payInput.getParenDivByName(fieldName).error = false;
    payInput.getParenDivByName(fieldName).className = payInput.CLASS_NAME;
}

function calculateRate(withMinimumBalance)
{
    // Сбрасываем предыдущее сообщение об ошибке
    removeFieldErrorMessage('interestRate');

    // Если один из двух возможных вариантов указания периода/даты заполнен, а второй нет, надо пересчитать дату
    var cDateVal = getElement('closingDate').value.length != 0;
    var pDaysVal = true;
    var pDays = parseInt(getElement("periodDays").value, 10);
    var pMonths = parseInt(getElement("periodMonths").value, 10);
    var pYears = parseInt(getElement("periodYears").value, 10);
    if (isNaN(pDays) && isNaN(pMonths) && isNaN(pYears))
        pDaysVal = false;
    if (cDateVal != pDaysVal)
        setDepositClosingDate();

    // Для простановки корректного периода
    setDepositPeriod();
    var periodDays = ("0000" + getElement("periodDays").value).slice(-4);
    var periodMonths = ("00" + getElement("periodMonths").value).slice(-2);
    var periodYears = ("00" + getElement("periodYears").value).slice(-2);
    var period = parseInt((periodYears + periodMonths + periodDays), 10);
    // Валюта
    var currency = getElement('toResourceCurrency').value;
    // Минимальный баланс
    var balances = depProduct[currency].balances;
    if (!withMinimumBalance)
    {

        var summ = getStringWithoutSpace(ensureElement("buyAmount").value);
        if (isEmpty(summ))
        {
            setElement("interestRate", "");
            $("#interestRateResult").html("");
            return;
        }

        var accountSumm = parseFloat(summ);

        var actualMinBalance = -1;
        for (var i in balances)
        {
            var balance = balances[i];
            var minBal = parseFloat(balance.balance);
            if (accountSumm >= minBal && actualMinBalance < minBal)
            {
                actualMinBalance = balance.balance;
            }
        }
        if (actualMinBalance == -1)
        {
            $("#interestRateResult").html("");
            setElement("interestRate", "");
            payInput.fieldError('interestRate', 'Сумма вклада не соответствует условиям вклада.<br/>Укажите другую сумму вклада. С условиями вклада можно ознакомиться <a id="depositDetailLink" href="#" onclick="showDetails();" class="depositProductInfoLink link">здесь</a>.');
            return;
        }

        getElement('minDepositBalance').value = actualMinBalance;
    }
    var minBalance = getElement('minDepositBalance').value;

    // Если еще что-то не установлено - уходим

    var interestRates = new Object();
    var sortedInterestRates = new Object();
    var sortedInterestPromoRates = new Object();
    var countOfRates = 0;
    for (var i in balances)
    {
        var balance = balances[i];
        if (balance.balance == minBalance)
        {
            // тут мы определили, что клиент задал максимальный срок вклада
            if (balance.toPeriod == undefined)
            {
                if (period == parseInt(balance.fromPeriod, 10))
                {
                    interestRates[i] = balance;
                    countOfRates++;
                }
            }
            else
            {
                if (period < parseInt(balance.toPeriod, 10) && period >= parseInt(balance.fromPeriod, 10))
                {
                    interestRates[i] = balance;
                    countOfRates++;
                }
            }
        }

    }
    var j = 0;
    var q = 0;
    var maxInterestDate = 0;
    var maxInterestPromoDate = 0;
    if (countOfRates > 1)
    {
        for (var h in interestRates)
        {
            var rateSegmentCode = interestRates[h].segmentCode;
            if (rateSegmentCode != '1' && rateSegmentCode != '0' && rateSegmentCode != '')
            {
                // Срок в днях
                var daysPromo = calculateDays();
                if ((daysPromo <= parseInt(interestRates[h].toDays, 10) || interestRates[h].toPeriod == undefined) && daysPromo >= parseInt(interestRates[h].fromDays, 10))
                {
                    sortedInterestPromoRates[q] = interestRates[h];
                    q++;
                    if (interestRates[h].interestDate > maxInterestPromoDate)
                        maxInterestPromoDate = interestRates[h].interestDate;
                }
            }
        }
        for (var m in sortedInterestPromoRates)
        {
            if (sortedInterestPromoRates[m].interestDate == maxInterestPromoDate)
            {
                setInterestRate(sortedInterestPromoRates[m]);
                return;
            }
        }

        for (var k in interestRates)
        {
            // Срок в днях
            var days = calculateDays();
            if ((days <= parseInt(interestRates[k].toDays, 10) || interestRates[k].toPeriod == undefined) && days >= parseInt(interestRates[k].fromDays, 10))
            {
                sortedInterestRates[j] = interestRates[k];
                j++;
                if (interestRates[k].interestDate > maxInterestDate)
                    maxInterestDate = interestRates[k].interestDate;
            }
        }
        for (var n in sortedInterestRates)
        {
            if (sortedInterestRates[n].interestDate == maxInterestDate)
            {
                setInterestRate(sortedInterestRates[n]);
                return;
            }
        }
    }
    else
    {
        if (interestRates != null)
        {
            for (var l in  interestRates)
            {
                setInterestRate(interestRates[l]);
                return;
            }
        }
    }
    var periodData = getElement("periodDays").value + getElement("periodMonths").value + getElement("periodYears").value;
    if (periodData == null || periodData == "")
        return;
    // Депозитный продукт не найден, => пишем сообщение о необходимости ввести другой срок
    $("#interestRateResult").html("");
    setElement("interestRate", "");
    payInput.fieldError('interestRate', 'Введённый срок вклада не соответствует условиям вклада.<br/>Укажите другой срок вклада. С условиями вклада можно ознакомиться <a id="depositDetailLink" href="#" onclick="showDetails();" class="depositProductInfoLink link">здесь</a>.');
}

function fillDPByCurrency(currencyCode, interestDateBegin, minBalance, period, periodInDays, interestRate, id, minAdditionalFee, indexes, mapCurrencies, prevCurrencyValue, withClose, segmentCode)
{
    var interestDateParts = interestDateBegin.split(".");
    var interestDate = parseInt(interestDateParts[0] + interestDateParts[1] + interestDateParts[2], 10);

    if (depProduct[currencyCode] == null)
    {
        depProduct[currencyCode] = {};
        depProduct[currencyCode].currency = currencyCode;
        if (prevCurrencyValue == '') prevCurrencyValue = currencyCode;
        mapCurrencies[currencyCode] = withClose ? true : currencyCode;
        depProduct[currencyCode].balances = {};
        indexes[currencyCode] = 0;
    }

    depProduct[currencyCode].balances["bal" + indexes[currencyCode]] = {};
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].balance = minBalance;
    var period = period.split("U");
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].fromPeriod = period[0].split("-").join("");
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].toPeriod = period[1] == undefined ? undefined : period[1].split("-").join("");
    var periodInDays = periodInDays.split("-");
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].fromDays = periodInDays[0];
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].toDays = periodInDays[1];
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].rate = interestRate;
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].interestDate = interestDate;
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].subType = id;
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].minAdditionalFee = minAdditionalFee;
    depProduct[currencyCode].balances["bal" + indexes[currencyCode]].segmentCode = segmentCode;
    indexes[currencyCode] = indexes[currencyCode] + 1;
}

function fillProductWithRate(currencyCode, interestRate, minBalance, periodFromTo, indexes, subType, interestDate, segmentCode)
{
    var period = periodFromTo.split('U');
    var curCode = getElement('toResourceCurrency').value;
    if (curCode == '')curCode = 'RUB';
    var interestDateParts = interestDate.split(".");
    var interestDate = parseInt(interestDateParts[0] + interestDateParts[1] + interestDateParts[2], 10);

    if (currencyCode == curCode)
    {
        if (actualDepositRates[subType] == null)
        {
            actualDepositRates[subType] = {};
            actualDepositRates[subType].rates = [];
            actualDepositRates[subType].interestDate = interestDate;
            actualDepositRates[subType].rates.push(interestRate);
            actualDepositRates[subType].segmentCode = segmentCode;
        }
        // если новая ставка - промо, а уже добавленная - нет
        else if (isPromoCode(segmentCode) && !isPromoCode(actualDepositRates[subType].segmentCode))
        {
            actualDepositRates[subType].rates = [];
            actualDepositRates[subType].interestDate = interestDate;
            actualDepositRates[subType].rates.push(interestRate);
            actualDepositRates[subType].segmentCode = segmentCode;
        }
        // если обе ставки - промо или обе не промо, выбираем самую позднюю
        else if ((isPromoCode(segmentCode) && isPromoCode(actualDepositRates[subType].segmentCode)) || (!isPromoCode(segmentCode) && !isPromoCode(actualDepositRates[subType].segmentCode)))
        {
            if (interestDate > actualDepositRates[subType].interestDate)
            {
                actualDepositRates[subType].rates = [];
                actualDepositRates[subType].interestDate = interestDate;
                actualDepositRates[subType].rates.push(interestRate);
                actualDepositRates[subType].segmentCode = segmentCode;
            }
            else if (interestDate == actualDepositRates[subType].interestDate)
            {
                actualDepositRates[subType].rates.push(interestRate);
            }
        }

        if (depProductByRate[interestRate] == null)
        {
            depProductByRate[interestRate] = new Object();
            depProductByRate[interestRate].rate = interestRate;
            depProductByRate[interestRate].data = new Array();
            withMaxPeriod[interestRate] = new Array();
            withMinBalance[interestRate] = new Object();
            indexes[interestRate] = 0;
        }
        depProductByRate[interestRate].data[indexes[interestRate]] = new Object();
        depProductByRate[interestRate].data[indexes[interestRate]].balance = minBalance;
        depProductByRate[interestRate].data[indexes[interestRate]].fromPeriod = period[0].split("-").join("");
        depProductByRate[interestRate].data[indexes[interestRate]].toPeriod = period[1] == undefined ? undefined : period[1].split("-").join("");
        depProductByRate[interestRate].data[indexes[interestRate]].interestDate = interestDate;
        depProductByRate[interestRate].data[indexes[interestRate]].subType = subType;
        depProductByRate[interestRate].data[indexes[interestRate]].segmentCode = segmentCode;

        var fromPeriod = period[0];
        if (fromPeriod != undefined)
        {
            fromPeriod = fromPeriod.split("-");
            depProductByRate[interestRate].data[indexes[interestRate]].fromPeriodYears = fromPeriod[0];
            depProductByRate[interestRate].data[indexes[interestRate]].fromPeriodMonths = fromPeriod[1];
            depProductByRate[interestRate].data[indexes[interestRate]].fromPeriodDays = fromPeriod[2];
        }

        var depProductInterest =  depProductByRate[interestRate].data[indexes[interestRate]];
        var maxPeriodInterest = withMaxPeriod[interestRate][0];

        if (withMaxPeriod[interestRate].length == 0 || (withMaxPeriod[interestRate][0].toPeriod == depProductByRate[interestRate].data[indexes[interestRate]].toPeriod && isNewRatePrior(depProductInterest, maxPeriodInterest)))
        {
            withMaxPeriod[interestRate].push(depProductByRate[interestRate].data[indexes[interestRate]]);
        }
        else if (depProductByRate[interestRate].data[indexes[interestRate]].fromPeriod > withMaxPeriod[interestRate][0].fromPeriod && isNewRatePrior(depProductInterest, maxPeriodInterest))
        {
            withMaxPeriod[interestRate] = [];
            withMinBalance[interestRate] = {};
            withMaxPeriod[interestRate].push(depProductByRate[interestRate].data[indexes[interestRate]]);
        }

        for (var i = 0; i < withMaxPeriod[interestRate].length; ++i)
        {
            var priorBySegment =  isNewRatePrior(withMaxPeriod[interestRate][i], withMinBalance[interestRate]);
            if (!withMinBalance[interestRate].balance || parseFloat(withMinBalance[interestRate].balance) >= parseFloat(withMaxPeriod[interestRate][i].balance) || (priorBySegment && withMinBalance[interestRate].subType == withMaxPeriod[interestRate][i].subType))
            {
                withMinBalance[interestRate] = withMaxPeriod[interestRate][i];
            }
        }
        indexes[interestRate] = indexes[interestRate] + 1;
    }

    /**
     * Определить актуальную ставку с учетом кода сегмента и даты начала действия.
     * Приоритет имеет ставка с сегментом, соответствующему промокоду (т.е. не 1 и не 0).
     * При одинаковых значениях приоритетности ставки выбираем по наиболее поздней дате начала
     */
    function isNewRatePrior (newRate, savedRate)
    {
        if(isPromoCode(newRate.segmentCode) && !isPromoCode(savedRate.segmentCode))
            return true;
        else if (!isPromoCode(newRate.segmentCode) && isPromoCode(savedRate.segmentCode))
            return false;

        return newRate.interestDate > savedRate.interestDate;

    }

    function isPromoCode(segmentCode)
    {
        return segmentCode != '1' && segmentCode != '0' && segmentCode != '';
    }
}