/**
 * Объекты "блок информации о детях" и "список блоков"
 *
 */

/**
 * Компонент "список блоков для введния информации о детях"
 * @param maxCount - максимальное количество блоков
 * @param info - уже имеющиеся данные для заполнения блоков
 */
function createChildrenList(maxCount, info)
{
    var childrenList = createComponentList(maxCount, info, 'Добавить информацию о детях', 'child_', '<span class="form-row-title size20">Дети</span>', 285, true);

    childrenList.getTypifiedComponent = function(info)
    {
        return createChildBlock(this, info);
    };

    childrenList.getComponentListParentNode = function()
    {
        return document.getElementById("childrenArea");
    };

    childrenList.init();
    childrenList.updateCompletedFields();
    return childrenList;
}

/**
 * Компонент "блок для введния информации о детях"
 * @param info - уже имеющиеся данные для заполнения блоков
 * @param parentComponent - компонент "список блоков"
 */
function createChildBlock(parentComponent, info)
{
    var childComponent = createComponent(parentComponent);

    childComponent.createTitleRow("Информация о ребенке");

    var childTypeRow = childComponent.createRow('Степень родства', false);
    childComponent.initPreparedSelectField(childTypeRow, "relativeType", info.relativeType, "childrenTypes");

    var surnameRow = childComponent.createRow('Фамилия', true);
    childComponent.addNewField(surnameRow, "surname", info.surname, "text", { maxLength : 120, size : 50 });

    var nameRow = childComponent.createRow('Имя', true);
    childComponent.addNewField(nameRow, "name", info.name, "text", { maxLength : 120, size : 50 });

    var patrRow = childComponent.createRow('Отчество', false);
    childComponent.addNewField(patrRow, "patrName", info.patrName, "text", { maxLength : 120, size : 50});

    var birthdayRow = childComponent.createRow('Дата рождения', true);
    childComponent.addNewField(birthdayRow, "birthday", info.birthday, "date", null);

    var dependentRow = childComponent.createRow('Находится на иждивении?', true);
    childComponent.addNewField(dependentRow, "dependent", info.dependent, "radio", ["Да", "Нет"]);

    var creditRow = childComponent.createRow('Есть кредиты в Сбербанке?', true);
    childComponent.addNewField(creditRow, "credit", info.credit, "radio", ["Да", "Нет", "Не знаю"]);

    var employeeRow = childComponent.createRow(null, false);
    var employeeInput = childComponent.addNewField(employeeRow, "employee", info.employee, "check", ["Является сотрудником Сбербанка"]);

    // 8. Место работы ребёнка, если он является сотрудником Сбербанка
    // 8.1 Добавляем строку поля и само поле
    var employeePlaceRow = $(childComponent.createRow('Место работы', true));
    var childPlaceValue = info.employeePlace!=null && info.employeePlace.length !=0 ? info.employeePlace.replace(new RegExp("\"",'g'), "&quot;") : info.employeePlace;
    childComponent.addNewField(employeePlaceRow, "employeePlace", childPlaceValue, "text", { maxLength : 50, size : 50 });

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

    return childComponent;
}
