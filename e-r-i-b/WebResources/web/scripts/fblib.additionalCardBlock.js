/**
 * ��������� "������ ������ ��� �������� ������ � �������������� ������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createCardsList(maxCount, info)
{
    var cardsList = createComponentList(maxCount, info, '�������� �����', 'cardBlank_', null, null ,true);

    cardsList.getTypifiedComponent = function(info)
    {
        return createCard(this, info);
    };

    cardsList.getComponentListParentNode = function()
    {
        return document.getElementById("additionalCardsArea");
    };

    cardsList.init();
    cardsList.updateCompletedFields();
    return cardsList;
}

/**
 * ��������� "���� ��� ������� ������ � �������������� �����"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createCard(parentComponent, info)
{
    var cardComponent = createComponent(parentComponent);

    cardComponent.createTitleRow("����������/���������� �����");

    var cardTypeRow = cardComponent.createRow('���', true);
    cardComponent.initPreparedSelectField(cardTypeRow, "type", info.type, "additionalCardTypes");

    var cardSelectRow = cardComponent.createRow('�������� ����� �� ������', true);
    cardComponent.initPreparedSelectField(cardSelectRow, "resource", info.code, "additionalCards");

    var inputParams = {maxLength: 24, size : 62};
    var customNumberRow = cardComponent.createRow('����� �����', true);
    cardComponent.addNewField(customNumberRow, "rowCustomNumber", info.customNumber, "text", inputParams);
    cardComponent.addNewField(customNumberRow, "customNumber", info.customNumber, "hidden", {});
    $(customNumberRow).find('input:text').keyup(function()
    {
        $(customNumberRow).find('input:hidden').val( this.value.replace(/[^\d]*/g, '') );
    });
    $(customNumberRow).find('input:text').createMask('9999 9999 9999 9999 9999');

    hideOrShowNumberRow($(cardSelectRow).find('select'));

    return cardComponent;
}
                                  
