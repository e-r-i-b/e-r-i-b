/**
 * ������� "���� ���������� � ����������� ���������" � "������ ������"
 *
 */

/**
 * ��������� "������ ������ ��� ������� ���������� � ����������� ���������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createTransportList(maxCount, info)
{
    var transportList = createComponentList(maxCount, info, '�������� ���������� � ����������� ��������', 'transport_', '<span class="form-row-title">������������ ��������</span>');

    transportList.getTypifiedComponent = function(info)
    {
        return createTransportBlock(this, info);
    };

    transportList.getComponentListParentNode = function()
    {
        return document.getElementById("transportArea");
    };

    transportList.init();
    transportList.updateCompletedFields();

    /*
     * �� ���� � ������� moneyField ���������������� �����.
     *
     * 1. ��� ������� ������ ����������
     */
    if (transportList.addNewChildButton)
    {
        $(transportList.addNewChildButton).click(function ()
        {
            initMoneyFields(transportList.getComponentListParentNode());
        });
    }

    /*
     * 2. �� ��� ��������� �� ��������
     */
    initMoneyFields(transportList.getComponentListParentNode());

    return transportList;
}

/**
 * ��������� "���� ��� ������� ���������� � ������������"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createTransportBlock(parentComponent, info)
{
    var transportComponent = createComponent(parentComponent);

    transportComponent.createTitleRow("������������ �������� (��)");

    var transportTypeRow = transportComponent.createRow('��� ������������� ��������', false);
    transportComponent.initPreparedSelectField(transportTypeRow, "transportType", info.transportType, "transportTypes");

    var registrationNumberRow = transportComponent.createRow('��������������� �����', true);
    transportComponent.addNewField(registrationNumberRow, "registrationNumber", info.registrationNumber, "text", {maxLength: 15});
    transportComponent.addRowDescription(registrationNumberRow, "������� ����� � ������� \"�000��99���\"");

    var brandRow = transportComponent.createRow('�����', true);
    transportComponent.addNewField(brandRow, "brand", info.brand, "text", {maxLength: 20});

    var approxMarketValueRow = transportComponent.createRow('��������� �������� ���������', true);
    transportComponent.addNewField(approxMarketValueRow, "approxMarketValue", info.approxMarketValue, "text", {className: 'moneyField', maxLength: 13});
    transportComponent.createTextLabel(approxMarketValueRow, "���.");

    var ageOfTransportRow = transportComponent.createRow('������� ��', true);
    transportComponent.addNewField(ageOfTransportRow, "ageOfTransport", info.ageOfTransport, "text", {maxLength: 2});
    transportComponent.createTextLabel(ageOfTransportRow, "���");

    var yearOfPurchaseRow = transportComponent.createRow('��� �������', true);
    transportComponent.addNewField(yearOfPurchaseRow, "yearOfPurchase", info.yearOfPurchase, "text", {maxLength: 4});

    return transportComponent;
}