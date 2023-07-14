/**
 * ������� "���� ���������� � ������������" � "������ ������"
 *
 */

/**
 * ��������� "������ ������ ��� ������� ���������� � ������������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createRealtyList(maxCount, info)
{
    var realtyList = createComponentList(maxCount, info, '�������� ���������� � ������������', 'realty_', '<span class="form-row-title">������������</span>');

    realtyList.getTypifiedComponent = function(info)
    {
        return createRealtyBlock(this, info);
    };

    realtyList.getComponentListParentNode = function()
    {
        return document.getElementById("realtyArea");
    };

    realtyList.init();
    realtyList.updateCompletedFields();

    /*
     * �� ���� � ������� moneyField ���������������� �����.
     *
     * 1. ��� ������� ������ ����������
     */
    if (realtyList.addNewChildButton)
    {
        $(realtyList.addNewChildButton).click(function ()
        {
            initMoneyFields(realtyList.getComponentListParentNode());
        });
    }

    /*
     * 2. �� ��� ��������� �� ��������
     */
    initMoneyFields(realtyList.getComponentListParentNode());

    return realtyList;
}

/**
 * ��������� "���� ��� ������� ���������� � ������������"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createRealtyBlock(parentComponent, info)
{
    var realtyComponent = createComponent(parentComponent);

    realtyComponent.createTitleRow("������������");

    var realtyTypeRow = realtyComponent.createRow('��� ������������', false);
    realtyComponent.initPreparedSelectField(realtyTypeRow, "realtyType", info.realtyType, "realtyTypes");

    var addressRow = realtyComponent.createRow('�����', true);
    realtyComponent.addNewField(addressRow, "address", info.address, "text", {maxLength: 200});

    var yearOfPurchaseRow = realtyComponent.createRow('��� ������������', true);
    realtyComponent.addNewField(yearOfPurchaseRow, "yearOfPurchase", info.yearOfPurchase, "text", {maxLength: 4});

    var squareRow = realtyComponent.createRow('�������', true);
    realtyComponent.addNewField(squareRow, "square", info.square, "text", {className: 'moneyField', maxLength: 13});
    realtyComponent.initPreparedSelectField(squareRow, "typeOfSquareUnit", info.typeOfSquareUnit, "typeOfSquareUnits");

    var approxMarketValueRow = realtyComponent.createRow('��������� �������� ���������', true);
    realtyComponent.addNewField(approxMarketValueRow, "approxMarketValue", info.approxMarketValue, "text", {className: 'moneyField', maxLength: 20});
    realtyComponent.createTextLabel(approxMarketValueRow, "���.");

    return realtyComponent;
}