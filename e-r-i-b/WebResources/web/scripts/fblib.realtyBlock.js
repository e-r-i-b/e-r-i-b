/**
 * Объекты "блок информации о недвижимости" и "список блоков"
 *
 */

/**
 * Компонент "список блоков для введния информации о недвижимости"
 * @param maxCount - максимальное количество блоков
 * @param info - уже имеющиеся данные для заполнения блоков
 */
function createRealtyList(maxCount, info)
{
    var realtyList = createComponentList(maxCount, info, 'Добавить информацию о недвижимости', 'realty_', '<span class="form-row-title">Недвижимость</span>');

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
     * На поля с классом moneyField устанавлиевается маска.
     *
     * 1. При нажатии кнопки добавления
     */
    if (realtyList.addNewChildButton)
    {
        $(realtyList.addNewChildButton).click(function ()
        {
            initMoneyFields(realtyList.getComponentListParentNode());
        });
    }

    /*
     * 2. На уже созданные на странице
     */
    initMoneyFields(realtyList.getComponentListParentNode());

    return realtyList;
}

/**
 * Компонент "блок для введния информации о недвижимости"
 * @param info - уже имеющиеся данные для заполнения блоков
 * @param parentComponent - компонент "список блоков"
 */
function createRealtyBlock(parentComponent, info)
{
    var realtyComponent = createComponent(parentComponent);

    realtyComponent.createTitleRow("Недвижимость");

    var realtyTypeRow = realtyComponent.createRow('Вид недвижимости', false);
    realtyComponent.initPreparedSelectField(realtyTypeRow, "realtyType", info.realtyType, "realtyTypes");

    var addressRow = realtyComponent.createRow('Адрес', true);
    realtyComponent.addNewField(addressRow, "address", info.address, "text", {maxLength: 200});

    var yearOfPurchaseRow = realtyComponent.createRow('Год приобретения', true);
    realtyComponent.addNewField(yearOfPurchaseRow, "yearOfPurchase", info.yearOfPurchase, "text", {maxLength: 4});

    var squareRow = realtyComponent.createRow('Площадь', true);
    realtyComponent.addNewField(squareRow, "square", info.square, "text", {className: 'moneyField', maxLength: 13});
    realtyComponent.initPreparedSelectField(squareRow, "typeOfSquareUnit", info.typeOfSquareUnit, "typeOfSquareUnits");

    var approxMarketValueRow = realtyComponent.createRow('Примерная рыночная стоимость', true);
    realtyComponent.addNewField(approxMarketValueRow, "approxMarketValue", info.approxMarketValue, "text", {className: 'moneyField', maxLength: 20});
    realtyComponent.createTextLabel(approxMarketValueRow, "руб.");

    return realtyComponent;
}