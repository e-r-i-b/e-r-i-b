/**
 * Компонент "список блоков для введения данных о дополнительных картах"
 * @param maxCount - максимальное количество блоков
 * @param info - уже имеющиеся данные для заполнения блоков
 */
function createCardsList(maxCount, info)
{
    var cardsList = createComponentList(maxCount, info, 'Добавить карту', 'cardBlank_', null, null ,true);

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
 * Компонент "блок для введния данных о дополнительной карте"
 * @param info - уже имеющиеся данные для заполнения блоков
 * @param parentComponent - компонент "список блоков"
 */
function createCard(parentComponent, info)
{
    var cardComponent = createComponent(parentComponent);

    cardComponent.createTitleRow("Зарплатная/пенсионная карта");

    var cardTypeRow = cardComponent.createRow('Тип', true);
    cardComponent.initPreparedSelectField(cardTypeRow, "type", info.type, "additionalCardTypes");

    var cardSelectRow = cardComponent.createRow('Выберите карту из списка', true);
    cardComponent.initPreparedSelectField(cardSelectRow, "resource", info.code, "additionalCards");

    var inputParams = {maxLength: 24, size : 62};
    var customNumberRow = cardComponent.createRow('Номер карты', true);
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
                                  
