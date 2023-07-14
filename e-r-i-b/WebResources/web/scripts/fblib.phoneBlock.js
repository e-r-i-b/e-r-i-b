/**
 * ��������� "������ ������ ��� �������� ���������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createPhonesList(maxCount, info)
{
    var phonesList = createComponentList(maxCount, info, '�������� �������', 'phoneBlank_');

    phonesList.getTypifiedComponent = function(info)
    {
        return createPhone(this, info);
    };

    phonesList.getComponentListParentNode = function()
    {
        return document.getElementById("additionalPhonesArea");
    };

    phonesList.init();
    phonesList.updateCompletedFields();
    return phonesList;
}

/**
 * ��������� "���� ��� ������� ��������"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createPhone(parentComponent, info)
{
    var phoneComponent = createComponent(parentComponent);

    phoneComponent.createTitleRow("�������������� �������");

    var phoneTypeRow = phoneComponent.createEmptyRow('��� ��������', true, false);
    var partIdOfSelectTag = "type";
    var selectTagId = phoneComponent.parent.fieldPrefix + partIdOfSelectTag;

    var selectTag = phoneComponent.initPreparedSelectField(phoneTypeRow, partIdOfSelectTag, info.phoneType, "phoneBlankTypes");
    var phoneNumberRow = phoneComponent.createRow('����� ��������', true);
    phoneComponent.createTextLabel(phoneNumberRow, "+");
    phoneComponent.addSpace(phoneNumberRow);

    var inputCountryParams = {maxLength: 4, size : 4};
    var countryCode = info.country == undefined || info.country == '' ? 7 : info.country;
    var countryInput = phoneComponent.addNewField(phoneNumberRow, "country", countryCode , "text", inputCountryParams);
    phoneComponent.addSpace(phoneNumberRow);

    var $selectedTypeOfPhone = $("#" + selectTagId + " option:selected");
    var telecomMaxLength = $selectedTypeOfPhone.val() == "MOBILE" ? 3 : 7;

    var inputTelecomParams = {maxLength: telecomMaxLength, size : telecomMaxLength};
    var telecomInput = phoneComponent.addNewField(phoneNumberRow, "telecom", info.telecom, "text", inputTelecomParams);
    phoneComponent.addSpace(phoneNumberRow);

    var inputParams = {maxLength: 7, size : 7};
    var numberInput =  phoneComponent.addNewField(phoneNumberRow, "number", info.number, "text", inputParams);
    phoneComponent.addSpace(phoneNumberRow);

    // �������� ������ � ���������� (���������� � �����)
    if (info.number != undefined)
    {
        $(phoneNumberRow).find('.paymentInputDiv').children().wrapAll("<span class='floatLeft'></span>");
        var newElem = $('span#popUpMessage> div').clone();
        $(phoneNumberRow).find('.paymentInputDiv').append(newElem);
        var clear = "<div class='clear'></div>";
        $(phoneNumberRow).find('.paymentInputDiv').append(clear);

        // �������� ������� ���������
        $('.simpleHintBlock').hover(function(){
            $(this).find('.simpleHint').css('display', 'block');
        }, function() {
            $(this).find('.simpleHint').css('display', 'none');
        });
    }

    phoneComponent.addRowDescription(phoneNumberRow, getTextForAdditionPhoneNumberField(telecomMaxLength));

    //���������� ������� "��������� ��������" �� select, ������� �������� �� ����� ���� ������ ��������
    selectTag.onchange = function(){

        var $inputTelecom = $(telecomInput);
        var $selectedTypeOfPhone = $("#" + selectTagId + " option:selected");

        var currentTelecomMaxLength = $inputTelecom.attr("maxLength");
        var telecomMaxLength = $selectedTypeOfPhone.val() == "MOBILE" ? 3 : 7;

        if(currentTelecomMaxLength === telecomMaxLength){
            return;
        }

        var $countryFieldName = $(countryInput);
        var $numberFieldName = $(numberInput);

        $inputTelecom.attr("maxLength", telecomMaxLength);
        $inputTelecom.attr("size", telecomMaxLength);

        if ( $selectedTypeOfPhone.val() == "MOBILE"){
            $countryFieldName.val("7");
            $inputTelecom.val("");
            $numberFieldName.val("");
        }
        $(phoneNumberRow).find(".description").html(getTextForAdditionPhoneNumberField(telecomMaxLength));
    };

    return phoneComponent;
}

/**
 * ��������� ��������� ��� ���� "����� ������"
 * @param  {int} prefixLenth ����� ��������
 * @return {string} ���������
 */
function getTextForAdditionPhoneNumberField(prefixLenth){
    return "������� ��� ����� �������� ��� ������� � ������� (��� ������ �� 4 ����) (������� �� " + prefixLenth + " ����) (��� ����� �� 7 ����)";
}