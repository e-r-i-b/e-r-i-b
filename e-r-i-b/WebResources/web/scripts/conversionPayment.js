//������� ��� ������ � ������������� ���������.
var amountRoundConstant = parseFloat(0.5 / 1024);
/**
 *  �������� ����� ����������
 */
function buyCurrencyCommon(fromResourceCurrency, toResourceCurrency, buyAmount, sellAmount, needConvert)
{
    var exactAmount = ensureElement("exactAmount");
    exactAmount.value = "destination-field-exact";
    if (needConvert)           //������� ������������  "������� �" � "��������� ��"
    {
        if (fromResourceCurrency != toResourceCurrency)
        {
            if (isShowCoursePayment())      //�������� �������������� ���� ���������
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
        //���� ������� � ����� �� ���� � � ����� ������
        if (isCardToAccountPayment() && fromResourceCurrency == toResourceCurrency)
        {
            sellAmount.value = buyAmount.value;
        }
    }
}

//���������������� �� �����
function isCardToAccountPayment()
{
    return false;
}

/*
 * �������� ����� ��������
 */
function sellCurrencyCommon(fromResourceCurrency, toResourceCurrency, buyAmount, sellAmount, needConvert)
{
    var exactAmount = ensureElement("exactAmount");
    exactAmount.value = "charge-off-field-exact";
    if (needConvert)
    {
        if (fromResourceCurrency != toResourceCurrency)
        {
            if (isShowCoursePayment())      //�������� �������������� ���� ���������
            {
                var rate;
                var standardRate;
                if (!checkRates())
                    return;

                if (fromResourceCurrency == "RUB")
                {
                    //���� ������� ������ ������ (������ �������� ������)
                    rate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE"];
                    standardRate = rates[toResourceCurrency + "|" + fromResourceCurrency]["SALE_0"];
                }
                else
                {
                    //���� ������� ������ ������ (������ ������� ������)
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
 * ���������� ������ �������������� ��������. �������� ����.
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

//������� �������� ���� "��� ������" (�.�. �������������� �������� � ������ � ����������)
function setCourseGain(standartRate, rate)
{
    //���� "��� ������" ������, �� � �� ������������ �
    if (isCourseGainHide(standartRate, rate))
        return;
    var fromResource = getElementValue("fromResource");
    //��� ��������� � �������� ������ ������ ����� �������� ����
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
        //��� �������� �������
        toResourceCurrency = getElementValue('toResourceCurrency');
        if ( isEmpty(toResourceCurrency) )
            return;
    }
    //��������� �������� "��� ������"
    $("#courseGainSpan").html(parseFloat(diff).toFixed(2) + " " + currencySignMap.get(toResourceCurrency));
}

//������� ��� ����������� ���� ��� ������
function isCourseGainHide(standartRate, rate)
{
    var fromResource = getElementValue("fromResource");
    //��� ��������� � �������� ������ ������ ����� �������� ����
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

//������ ����� � ����������� �� ��������� ���������.
//�������� ����������: ����������� ��� ��� ����� �����. ��������� ������� ������� �����.
function getBuyAmount(amount, rate)
{
    if (amount != '' && !isNaN(amount))
    {
        //��� ��������� � �������� ������ ������ ����� �������� ����
        if (getFromResourceCurrencyCode() == 'RUB')
            rate = 1 / rate;
        return getAmount(amount, rate);
    }
    return "";
}

//������ ����� � ����������� �� ��������� ���������.
//�������� ����������:
//������� = ���������� ����� ����� ���������� �����, ����������� �� 2� ������ ����� �������
//�������� = �������*100, ���������� � ���������� 0,5/1024
//1) ���� �������� ������ ��������� �� ��������� � ����� 0.01
//2) ���� ����� ���� ����� �� ����� ������������� �� 2� ������ ����� ��������
function getSellAmount(amount, rate)
{
    if (amount != '' && !isNaN(amount))
    {
        //��� ��������� �� � �������� ������ ��� ������� ����� �������� ������ ����� �������� ����
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

//������ ��������� �������� ������� (���� ����� ���������������� �� �����)
function getFromResourceCurrencyCode()
{
    var fromResource = getElementValue("fromResource");
    if (fromResource != undefined && currencies != undefined)
        return currencies[fromResource];
    return "";
}

//�������� �������������� ���� ���������.
function isShowCoursePayment()
{
    return true; //��������� �����, ������������ �� �����.
}

//�������� �� ����� ��������
function checkRates()
{
    return true; //��������� �����, ������������� �� �����.
}