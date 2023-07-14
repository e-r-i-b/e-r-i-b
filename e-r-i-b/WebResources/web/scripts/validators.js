var VALIDATE_MIN_LETTERS_NAME = "minValue";
var VALIDATE_MAX_LETTERS_NAME = "maxValue";
var VALIDATE_REGEXP_LETTERS_NAME = "regexp";

var UNPARSEBLE_ADDRESS_LETTERS_REGEXP = /^([а-я]|[А-Я]|[a-z]|[A-Z]|\s|-|'|\.|\\|,|:|;|\)|\(|\d|\№)+$/;
var HOUSE_BUILDING_LETTERS_REGEXP = /^([а-я]|[А-Я]|\s|\.|\d)+$/;
var DISTINCT_LETTERS_REGEXP = /([а-я]|[А-Я]|\s|-)+$/;
var STREET_LETTERS_REGEXP = /^([а-я]|[А-Я]|\s|-|\.)+$/;
var DECIMAL_LETTERS_REGEXP = /^\d+$/;
var PHONE_LETTERS_REGEXP = /^(\+|\s|-|\)|\(|\d)+$/;
var EMAIL_PHONE_LETTERS_REGEXP = /^([a-z]|[A-Z]|-|\.|\d|_|@)+$/;
var SNILS_LETTERS_REGEXP = /^(\d{3}-\d{3}-\d{3} \d{2})+$/;
var EMAIL_REGEXP = /^[^\sа-яА-Я]+@[^\sа-яА-Я]+\.[^\sа-яА-Я]+$/;


function validate(obj, currentValidators)
{

    //валидируем на минимальное значение
    var min = currentValidators.VALIDATE_MIN_LETTERS_NAME;
    var max = currentValidators.VALIDATE_MAX_LETTERS_NAME;
    var regexp = currentValidators.VALIDATE_REGEXP_LETTERS_NAME;

    var msgs = [];

    if (min != undefined && obj.value.length < min)
    {
        if (obj.value.length == 0)
            msgs.push("Пожалуйста, заполните данное поле.");
        else
            msgs.push("Пожалуйста, проверьте количество символов в этом поле.");
    }

    if (max != undefined && obj.value.length > max)
    {
        msgs.push("Пожалуйста, проверьте количество символов в этом поле.");
    }

    if (regexp != undefined && !matchRegexp(obj.value, regexp))
    {
        msgs.push("Пожалуйста, проверьте, правильно ли Вы заполнили данное поле.");
    }

    return msgs;
}

function matchRegexp(value, regexp)
{
    //Нечего сравнивать с шаблоном    
    if (value == null || value.length <= 0)
        return true;
    return regexp.test(value);    
}