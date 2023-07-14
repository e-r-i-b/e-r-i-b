/**
 * Объекты "блок информации о траспортных средствах" и "список блоков"
 *
 */

/**
 * Компонент "список блоков для введния информации о траспортных средствах"
 * @param maxCount - максимальное количество блоков
 * @param info - уже имеющиеся данные для заполнения блоков
 */
function createTransportList(maxCount, info)
{
    var transportList = createComponentList(maxCount, info, 'Добавить информацию о траспортном средстве', 'transport_', '<span class="form-row-title">Транспортное средство</span>');

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
     * На поля с классом moneyField устанавлиевается маска.
     *
     * 1. При нажатии кнопки добавления
     */
    if (transportList.addNewChildButton)
    {
        $(transportList.addNewChildButton).click(function ()
        {
            initMoneyFields(transportList.getComponentListParentNode());
        });
    }

    /*
     * 2. На уже созданные на странице
     */
    initMoneyFields(transportList.getComponentListParentNode());

    return transportList;
}

/**
 * Компонент "блок для введния информации о недвижимости"
 * @param info - уже имеющиеся данные для заполнения блоков
 * @param parentComponent - компонент "список блоков"
 */
function createTransportBlock(parentComponent, info)
{
    var transportComponent = createComponent(parentComponent);

    transportComponent.createTitleRow("Транспортное средство (ТС)");

    var transportTypeRow = transportComponent.createRow('Тип транспортного средства', false);
    transportComponent.initPreparedSelectField(transportTypeRow, "transportType", info.transportType, "transportTypes");

    var registrationNumberRow = transportComponent.createRow('Регистрационный номер', true);
    transportComponent.addNewField(registrationNumberRow, "registrationNumber", info.registrationNumber, "text", {maxLength: 15});
    transportComponent.addRowDescription(registrationNumberRow, "Введите номер в формате \"а000аа99рус\"");

    var brandRow = transportComponent.createRow('Марка', true);
    transportComponent.addNewField(brandRow, "brand", info.brand, "text", {maxLength: 20});

    var approxMarketValueRow = transportComponent.createRow('Примерная рыночная стоимость', true);
    transportComponent.addNewField(approxMarketValueRow, "approxMarketValue", info.approxMarketValue, "text", {className: 'moneyField', maxLength: 13});
    transportComponent.createTextLabel(approxMarketValueRow, "руб.");

    var ageOfTransportRow = transportComponent.createRow('Возраст ТС', true);
    transportComponent.addNewField(ageOfTransportRow, "ageOfTransport", info.ageOfTransport, "text", {maxLength: 2});
    transportComponent.createTextLabel(ageOfTransportRow, "лет");

    var yearOfPurchaseRow = transportComponent.createRow('Год покупки', true);
    transportComponent.addNewField(yearOfPurchaseRow, "yearOfPurchase", info.yearOfPurchase, "text", {maxLength: 4});

    return transportComponent;
}