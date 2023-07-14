var VALIDATE_MIN_LETTERS_NAME = "minValue";
var VALIDATE_MAX_LETTERS_NAME = "maxValue";
var VALIDATE_REGEXP_LETTERS_NAME = "regexp";

var UNPARSEBLE_ADDRESS_LETTERS_REGEXP = /^([�-�]|[�-�]|[a-z]|[A-Z]|\s|-|'|\.|\\|,|:|;|\)|\(|\d|\�)+$/;
var HOUSE_BUILDING_LETTERS_REGEXP = /^([�-�]|[�-�]|\s|\.|\d)+$/;
var DISTINCT_LETTERS_REGEXP = /([�-�]|[�-�]|\s|-)+$/;
var STREET_LETTERS_REGEXP = /^([�-�]|[�-�]|\s|-|\.)+$/;
var DECIMAL_LETTERS_REGEXP = /^\d+$/;
var PHONE_LETTERS_REGEXP = /^(\+|\s|-|\)|\(|\d)+$/;
var EMAIL_PHONE_LETTERS_REGEXP = /^([a-z]|[A-Z]|-|\.|\d|_|@)+$/;
var SNILS_LETTERS_REGEXP = /^(\d{3}-\d{3}-\d{3} \d{2})+$/;
var EMAIL_REGEXP = /^[^\s�-��-�]+@[^\s�-��-�]+\.[^\s�-��-�]+$/;


function validate(obj, currentValidators)
{

    //���������� �� ����������� ��������
    var min = currentValidators.VALIDATE_MIN_LETTERS_NAME;
    var max = currentValidators.VALIDATE_MAX_LETTERS_NAME;
    var regexp = currentValidators.VALIDATE_REGEXP_LETTERS_NAME;

    var msgs = [];

    if (min != undefined && obj.value.length < min)
    {
        if (obj.value.length == 0)
            msgs.push("����������, ��������� ������ ����.");
        else
            msgs.push("����������, ��������� ���������� �������� � ���� ����.");
    }

    if (max != undefined && obj.value.length > max)
    {
        msgs.push("����������, ��������� ���������� �������� � ���� ����.");
    }

    if (regexp != undefined && !matchRegexp(obj.value, regexp))
    {
        msgs.push("����������, ���������, ��������� �� �� ��������� ������ ����.");
    }

    return msgs;
}

function matchRegexp(value, regexp)
{
    //������ ���������� � ��������    
    if (value == null || value.length <= 0)
        return true;
    return regexp.test(value);    
}