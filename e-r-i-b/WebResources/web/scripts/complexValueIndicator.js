/**
 * ������������ ���������� ������ ������ ��������
 * @param value ������
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
 * ��������� ��������� ������
 * @param indicatorId id div'� � �����������
 * @param valueStateValidate ������ ��� ����������� ����� ����������
 * @param stateDependFunction �������������� �������, ���������� � ������ ����� state ��������� � ���������� ��������. ��� null � ������ ������� �������� ����.
 * @param typeInterface � ����� ������� ����� ��������
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
                // �� � ������ ���������� ��������� maxlength ���������� ������������ �������� 2147483647
                // ������� ������� �������������� 1000
                || password.maxLength > 1000)
            password.maxLength = 30;

        // ������ ���������
        var width = (password.value.length / password.maxLength) * 100;

        // ���� ���������� ���, �������
        if(width == 0)
        {
            this.reset();
            if (stateDependFunction != null)
                stateDependFunction(null);
            return;
        }

        // ��������� ������
        var state = this.valueStateValidate(password.value);

        // ������������� ���� � ������ � ���������
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
                message = "������� ��������";
                break;

            case "green":
                message = "��������";
                break;

            case "orange":
                message = "�������";
                break;
        };

        if (typeInterface != "new")
        {
            $("#" + this.indicatorId + " .message-indicator").text(message + ", " + length + " ��������")
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
 * �������, ����������� ��� � �������� value ��� �������������� ������� ������
 * @param value ��������, ������� �����������
 * @param length ����� ��������������������
 */
function sequenceLengthValidate(value, length)
{
    var sequences = new Array();
    sequences[sequences.length] = "�1234567890-=";
    sequences[sequences.length] = "!@#$%^&*()_+";
    sequences[sequences.length] = "QWERTYUIOP";
    sequences[sequences.length] = "ASDFGHJKL;";
    sequences[sequences.length] = "ZXCVBNM,.";
    sequences[sequences.length] = "������������";
    sequences[sequences.length] = "�����������";
    sequences[sequences.length] = "���������.";

    var valueUpperCase = value.toUpperCase();
    for(var i = 0; i < sequences.length; i++)
    {
        var validator = new SubsequenceLengthValidator(sequences[i], length);
        if(!validator.validate(valueUpperCase)
                // � �������� ������
                || !validator.validate(valueUpperCase.split('').reverse().join('')))
            return false;
    }
    return true;
}

/**
 * ���������, �����������, ���������� �� � ���������� �������� ������������������ ����� length �� ������������������ sequence
 * @param sequence ������, �� ������� ����������� ���������������������
 * @param length ����� ��������������������
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
 * ������� ��� ����������� ��������� ������ �� ���������� ������ ���
 * @param value
 */
function stateComplexLoginCsa(value)
{
    if(value.length < this.minLength)
        return "red";

    if(maxCountRepeat(value, false) <= 3
            && !/[0-9]{10}/.test(value) && !/[�-��-�]/.test(value))
        return "green";

    return "orange";
}

/**
 * ������� ��� ����������� ��������� ������ �� ���������� ������ ���
 * @param value
 */
function stateComplexPasswordCsa(value)
{
    // ���� ����� ������ 8 ��������
    if(value.length < 8)
        return "red";

    if (maxCountRepeat(value, true) <= 3 // ����������
            && /.*[0-9].*/.test(value)   // ���������� �� �����
            && /.*[A-Za-z].*/.test(value) //���������� �� ���������� �����
            && !/[�-��-�]/.test(value) //��� ������� ����
            && sequenceLengthValidate(value, 4) // �� �������� ����� 3-� ��������, ������������� ����� � ����� ���� ����������
            && /^([\w@\-\.!#$%^&*()+:;,]*)$/g.test(value)   //��� ����������� ���� ��������
            )
        return "green";

    return "orange";
}