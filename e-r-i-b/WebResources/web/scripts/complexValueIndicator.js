/**
 * Максимальное количество подряд идущих символов
 * @param value строка
 */
function maxCountRepeat(value, isCaseSensitive)
{
    if(value.length == 0)
        return 0;

    var maxSize = 1;
    var counter = 1;
    var currentChar = value.charAt(i);

    for(var i = 1; i<value.length; i++)
    {
        var ch = value.charAt(i);
        if((isCaseSensitive && currentChar == ch)
                || (!isCaseSensitive && currentChar.toLowerCase() == ch.toLowerCase()))
        {
            counter++;
            if(counter > maxSize)
                maxSize = counter;
        }
        else
        {
            counter = 1;
            currentChar = ch;
        }
    }

    return maxSize;
}

/**
 * Индикатор сложности пароля
 * @param indicatorId id div'а с индикатором
 * @param valueStateValidate функия для определения цвета индикатора
 * @param stateDependFunction дополнительная функция, аргументом в кторой будет state полученый в результате проверки. Или null в случае пустого значения поля.
 * @param typeInterface с каким внешним видом работаем
 */
function Indicator(indicatorId, valueStateValidate, stateDependFunction, typeInterface)
{
    this.minLength = 8;
    this.indicatorId = indicatorId + "Indicator";
    this.valueStateValidate = valueStateValidate != null ? valueStateValidate : function(value)
    {
        return "green";
    };
    this.stateDependFunction = stateDependFunction;
    this.typeInterface = typeInterface;

    this.check = function(password)
    {
        if(password.maxLength == -1
                // ИЕ в случае отсутствия аттрибута maxlength выставляет максимальное значение 2147483647
                // поэтому условно ограничиваемся 1000
                || password.maxLength > 1000)
            password.maxLength = 30;

        // ширина заполения
        var width = (password.value.length / password.maxLength) * 100;

        // если заполнения нет, очишаем
        if(width == 0)
        {
            this.reset();
            if (stateDependFunction != null)
                stateDependFunction(null);
            return;
        }

        // состояние пароля
        var state = this.valueStateValidate(password.value);

        // устанавливаем цвет и размер и сообщение
        if (typeInterface != "new")
            this.fillState(state, width);
        this.fillMessage(state, password.value.length);
        if (stateDependFunction != null)
            stateDependFunction(state);
    };

    this.init = function(fieldId, blockId)
    {
        var self = this;
        var field = (blockId != null) ? $("#" + blockId).find("#" + fieldId).get(0) : $("#" + fieldId).get(0);
        if(field == null)
            return;
        
        $(field).keyup(function(e){
             self.check(this);
        })
        .bind('paste', function(e){
             self.check(this);
        });

        $(field).blur(function(e){
            self.check(this);
        })

        self.check(field);
    };

    this.setMinLength = function(minLength)
    {
        this.minLength = minLength;
    };

    this.fillMessage = function(state, length)
    {
        var message = "";

        switch(state)
        {
            case "red":
                message = "Слишком короткий";
                break;

            case "green":
                message = "Надежный";
                break;

            case "orange":
                message = "Простой";
                break;
        };

        if (typeInterface != "new")
        {
            $("#" + this.indicatorId + " .message-indicator").text(message + ", " + length + " символов")
        }
        else
        {
            if (state == "orange")
                state = "red";
            $("#" + this.indicatorId + " .message-indicator").text(message);
        }

        $("#" + this.indicatorId + " .message-indicator")
                .removeClass("green-text red-text orange-text")
                .addClass(message == "" ? "" : (state + "-text"));
    };

    this.fillState = function(state, width)
    {
        $("#" + this.indicatorId + " .fill-state")
                .removeClass("green-state red-state orange-state")
                .addClass(state + "-state");

        $("#" + this.indicatorId + " .left-gray-state")
                .removeClass("left-green-state left-red-state left-orange-state")
                .addClass(width > 0 ? ("left-" + state + "-state") : "");

        $("#" + this.indicatorId + " .right-gray-state")
                .removeClass("right-green-state right-red-state right-orange-state")
                .addClass(width >= 100 ? ("right-" + state + "-state") : "");

        $("#" + this.indicatorId + " .fill-state")
                .width((width > 100 ? 100 : width) + "%");
    };

    this.reset = function()
    {
        $("#" + this.indicatorId + " .fill-state")
                .removeClass("green-state red-state orange-state");

        $("#" + this.indicatorId + " .left-gray-state")
                .removeClass("left-green-state left-red-state left-orange-state");

        $("#" + this.indicatorId + " .right-gray-state")
                .removeClass("right-green-state right-red-state right-orange-state");

        $("#" + this.indicatorId + " .message-indicator").text("")
                .removeClass("green-text red-text orange-text");

        $("#" + this.indicatorId + " .fill-state").width("0%");
    };
}

/**
 * Функция, проверяющая что в значении value нет поледовательно нажатых клавиш
 * @param value значение, которое проверяется
 * @param length длина подпоследовательноти
 */
function sequenceLengthValidate(value, length)
{
    var sequences = new Array();
    sequences[sequences.length] = "Ё1234567890-=";
    sequences[sequences.length] = "!@#$%^&*()_+";
    sequences[sequences.length] = "QWERTYUIOP";
    sequences[sequences.length] = "ASDFGHJKL;";
    sequences[sequences.length] = "ZXCVBNM,.";
    sequences[sequences.length] = "ЙЦУКЕНГШЩЗХЪ";
    sequences[sequences.length] = "ФЫВАПРОЛДЖЭ";
    sequences[sequences.length] = "ЯЧСМИТЬБЮ.";

    var valueUpperCase = value.toUpperCase();
    for(var i = 0; i < sequences.length; i++)
    {
        var validator = new SubsequenceLengthValidator(sequences[i], length);
        if(!validator.validate(valueUpperCase)
                // и обратную строку
                || !validator.validate(valueUpperCase.split('').reverse().join('')))
            return false;
    }
    return true;
}

/**
 * Валидатор, проверяющий, содержится ли в переданном значении последовательность длины length из последовательности sequence
 * @param sequence строка, по которой проверяются подпоследовательности
 * @param length длина подпоседовательности
 */
function SubsequenceLengthValidator(sequence, length)
{
    this.sequence = sequence;
    this.length = length;

    this.validate = function(value)
    {
        if(value == null || value == "")
            return true;

        if(length > sequence.length)
            return true;

        for(var i = 0; i < value.length; i++)
        {
            var toIndex = i + this.length;
            if(toIndex > value.length)
                break;

            if(sequence.indexOf(value.substr(i, this.length)) >= 0)
                return false;
        }

        return true;
    };
}

/**
 * Функция для определения сложности логина по требования нового цса
 * @param value
 */
function stateComplexLoginCsa(value)
{
    if(value.length < this.minLength)
        return "red";

    if(maxCountRepeat(value, false) <= 3
            && !/[0-9]{10}/.test(value) && !/[А-Яа-я]/.test(value))
        return "green";

    return "orange";
}

/**
 * Функция для определения сложности пароля по требования нового цса
 * @param value
 */
function stateComplexPasswordCsa(value)
{
    // если длина меньше 8 символов
    if(value.length < 8)
        return "red";

    if (maxCountRepeat(value, true) <= 3 // масимально
            && /.*[0-9].*/.test(value)   // содержатся ли цифры
            && /.*[A-Za-z].*/.test(value) //содержатся ли английские буквы
            && !/[А-Яа-я]/.test(value) //нет русских букв
            && sequenceLengthValidate(value, 4) // не содержит более 3-х символов, расположенных рядом в одном ряду клавиатуры
            && /^([\w@\-\.!#$%^&*()+:;,]*)$/g.test(value)   //нет запрещенных спец символов
            )
        return "green";

    return "orange";
}