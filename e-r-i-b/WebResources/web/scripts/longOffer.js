/*
������� ������� ���� ���������� �������
*/
function LongOfferDate(event, beginDate)
{
    var WRONG_DAY_WARNING = "���� � ������ ����� ������ ����, ��� �������� ���������� �����, ������ ����� �������� ��������� ���� ������.";

    this.event  = event;
    this.beginDate = beginDate;
    this.getNextDate    = function()
    {
        var currentDate = this.beginDate;
        if (isEmpty(currentDate))
            return;

        var day = getElementValue(this.event + "_DAY");
        var month;

        if (this.event == "ONCE_IN_MONTH")
        {
            //���� ���� � ���� ������
            if (day >= currentDate.getDate())
            {
                return newActualDate(currentDate.getFullYear(), currentDate.getMonth(), day);
            }

            month = (currentDate.getMonth() == 11) ? 0 : currentDate.getMonth() + 1;
        }
        else if (this.event == "ONCE_IN_QUARTER" || this.event == "ONCE_IN_HALFYEAR")
        {
            var months = getElementValue(this.event + "_MONTHS").split("|");
            for (var m in months)
            {
                var nextMonth = parseInt(months[m] - 1);
                if (nextMonth == currentDate.getMonth())
                {
                    //���� ���� � ���� ������
                    if (day >= currentDate.getDate())
                    {
                        return newActualDate(currentDate.getFullYear(), currentDate.getMonth(), day);
                    }
                }
                if (nextMonth > currentDate.getMonth())
                {
                    month = nextMonth;
                    break;
                }
            }

            if (parseInt(months[months.length - 1] - 1) <= currentDate.getMonth())
                month = months[0] - 1;
        }
        else if (this.event == "ONCE_IN_YEAR")
        {
            month = parseInt(getElementValue(this.event + "_MONTHS") - 1);
            if (month == currentDate.getMonth())
            {
                //���� ���� ������� � ���� ������
                if (day >= currentDate.getDate())
                {
                    return newActualDate(currentDate.getFullYear(), currentDate.getMonth(), day);
                }

                return newActualDate(currentDate.getFullYear() + 1, currentDate.getMonth(), day);
            }
        }
        else
        {
            return currentDate;
        }

        //���� ��������� ����� � ���� ����
        if (currentDate.getMonth() < month)
        {
            return newActualDate(currentDate.getFullYear(), month, day);
        }

        //���� ��������� ����� � ��������� ����
        if (currentDate.getMonth() > month)
        {
            return new newActualDate(currentDate.getFullYear() + 1, month, day);
        }

        function newActualDate(year, month, day)
        {
            var maxDayInMonth = getCountDayOfMonth(year, month);
            return new Date(year, month, (day < maxDayInMonth) ? day : maxDayInMonth);
        }
    }
    this.validate   = function()
    {
        var day = getElementValue(this.event + "_DAY");

        if (isNaN(this.beginDate.getDate()))
            return undefined;

        if (isEmpty(day))
            return undefined;

        if (isNaN(parseInt(day)))
            return false;

        if (!(/^\d{1,2}$/.test(day)) || day > 31 || day < 1)
            return false;

        removeMessage(WRONG_DAY_WARNING);

        if (day > 28)
            addMessage(WRONG_DAY_WARNING);

        if (this.getNextDate() == undefined)
            return undefined;

        return true;
    };
    this.toString    = function()
    {
        var date = this.getNextDate();
        return getDualValue(date.getDate()) + "." + getDualValue((date.getMonth() + 1)) + "." + date.getFullYear();

        function getDualValue(value)
        {
            return (value.toString().length > 1) ? value : "0".concat(value);
        }
    };
}

/*
���������� ������������� �� ��� �����������
*/
function isPeriodic(event)
{
    return event == "ONCE_IN_MONTH" || event == "ONCE_IN_QUARTER" || event == "ONCE_IN_HALFYEAR" || event == "ONCE_IN_YEAR";
}

/*
�������� ����
*/
function createDate(date)
{
    return new Date(date.substr(6,4), parseInt(date.substr(3,2), 10)-1, date.substr(0,2));
}

function isEmpty(value)
{
    return value == null || value == "";
}

//���������� ���� � ������
function getCountDayOfMonth(year, month)
{
    return (32 - new Date(year, month, 32).getDate());
}

/**
 * ������������� ������� ���� � ���� input � ��������������� elemId.
 *
 * @param elemId id input ��������.
 */
function setCurrentDateToInput(elemId)
{
    var jqueryName = "#" + elemId;
    if ($(jqueryName) == '')
        return;

    if ($(jqueryName).val() == '')
       $(jqueryName).val(getCurrentDate("."));
}