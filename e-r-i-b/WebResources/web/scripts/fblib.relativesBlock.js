/**
 * Объекты "блок информации о родственниках" и "список блоков"
 *
 */

/**
 * Компонент "список блоков для введния информации о родственниках"
 * @param maxCount - максимальное количество блоков
 * @param info - уже имеющиеся данные для заполнения блоков
 */
function createRelativesList(maxCount, info)
{
    var relativesList = createComponentList(maxCount, info, 'Добавить информацию о родственнике', 'relative_', '<span class="form-row-title size20">Родственники</span>', 300, true);

    relativesList.getTypifiedComponent = function(info)
    {
        return createRelative(this, info);
    };

    relativesList.getComponentListParentNode = function()
    {
        return document.getElementById("relativeArea");
    };

    relativesList.init();
    relativesList.updateCompletedFields();

    var inputDivNode = document.createElement('div');
    inputDivNode.className = "paymentInputDiv";
    var blockComment = document.createElement('p');
    blockComment.className = "noNecessarily";
    blockComment.innerHTML = "Вам необязательно заполнять данный раздел";
    inputDivNode.appendChild(blockComment);
    $('#relativeArea').find('.paymentValue')[0].appendChild(inputDivNode);

    return relativesList;
}

/**
 * Компонент "блок для введния информации о родственниках"
 * @param info - уже имеющиеся данные для заполнения блоков
 * @param parentComponent - компонент "список блоков"
 */
function createRelative(parentComponent, info)
{
    var relativeComponent = createComponent(parentComponent);

    relativeComponent.createTitleRow("Информация о родственнике");

    var relativeTypeRow = relativeComponent.createEmptyRow('Степень родства', false);
    relativeComponent.initPreparedSelectField(relativeTypeRow, "relativeType", info.relativeType, "relativeTypes");

    //Параметры для отрисовки текстовых полей ввода
    var inputParams = {maxLength: 120, size : 50};

    var surnameRow = relativeComponent.createRow('Фамилия', true);
    relativeComponent.addNewField(surnameRow, "surname", info.surname, "text", inputParams);

    var nameRow = relativeComponent.createRow('Имя', true);
    relativeComponent.addNewField(nameRow, "name", info.name, "text", inputParams);

    var patrRow = relativeComponent.createRow('Отчество', false);
    relativeComponent.addNewField(patrRow, "patrName", info.patrName, "text", inputParams);

    var birthdayRow = relativeComponent.createRow('Дата рождения', true);
    relativeComponent.addNewField(birthdayRow, "birthday", info.birthday, "date");

    var dependentRow = relativeComponent.createRow('Находится на иждивении?', true);
    var params = ["Да", "Нет"];
    relativeComponent.addNewField(dependentRow, "dependent", info.dependent, "radio", params);

    var creditRow = relativeComponent.createRow('Есть кредиты в Сбербанке?', true);
    params = ["Да", "Нет", "Не знаю"];
    relativeComponent.addNewField(creditRow, "credit", info.credit, "radio", params);

    var employeeRow = relativeComponent.createRow(null, false);
    params = ["Является сотрудником Сбербанка"];
    var employeeInput = relativeComponent.addNewField(employeeRow, "employee", info.employee, "check", params);

    // 8. Место работы родственника, если он является сотрудником Сбербанка
    // 8.1 Добавляем строку поля и само поле
    var employeePlaceRow = $(relativeComponent.createRow('Место работы', true));
    var employeePlaceValue = info.employeePlace!=null && info.employeePlace.length !=0 ? info.employeePlace.replace(new RegExp("\"",'g'), "&quot;") : info.employeePlace;
    relativeComponent.addNewField(employeePlaceRow, "employeePlace", employeePlaceValue, "text", { maxLength : 50, size : 50 });

    // 8.2 На флажок "Является сотрудником Сбербанка" вешаем функцию,
    // которая будет показывать/скрывать поле Место Работы в зависимости от значения флажка
    var showOrHideEmployeePlace = function()
    {
        if (employeeInput.find('input').is(':checked'))
            employeePlaceRow.css('display', '');
        else employeePlaceRow.css('display', 'none');
    };

    employeeInput.change(showOrHideEmployeePlace);

    // 8.3 Показываем/прячем поле
    showOrHideEmployeePlace();

    return relativeComponent;
}