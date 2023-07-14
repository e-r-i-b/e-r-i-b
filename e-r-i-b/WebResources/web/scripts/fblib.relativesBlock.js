/**
 * ������� "���� ���������� � �������������" � "������ ������"
 *
 */

/**
 * ��������� "������ ������ ��� ������� ���������� � �������������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createRelativesList(maxCount, info)
{
    var relativesList = createComponentList(maxCount, info, '�������� ���������� � ������������', 'relative_', '<span class="form-row-title size20">������������</span>', 300, true);

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
    blockComment.innerHTML = "��� ������������� ��������� ������ ������";
    inputDivNode.appendChild(blockComment);
    $('#relativeArea').find('.paymentValue')[0].appendChild(inputDivNode);

    return relativesList;
}

/**
 * ��������� "���� ��� ������� ���������� � �������������"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createRelative(parentComponent, info)
{
    var relativeComponent = createComponent(parentComponent);

    relativeComponent.createTitleRow("���������� � ������������");

    var relativeTypeRow = relativeComponent.createEmptyRow('������� �������', false);
    relativeComponent.initPreparedSelectField(relativeTypeRow, "relativeType", info.relativeType, "relativeTypes");

    //��������� ��� ��������� ��������� ����� �����
    var inputParams = {maxLength: 120, size : 50};

    var surnameRow = relativeComponent.createRow('�������', true);
    relativeComponent.addNewField(surnameRow, "surname", info.surname, "text", inputParams);

    var nameRow = relativeComponent.createRow('���', true);
    relativeComponent.addNewField(nameRow, "name", info.name, "text", inputParams);

    var patrRow = relativeComponent.createRow('��������', false);
    relativeComponent.addNewField(patrRow, "patrName", info.patrName, "text", inputParams);

    var birthdayRow = relativeComponent.createRow('���� ��������', true);
    relativeComponent.addNewField(birthdayRow, "birthday", info.birthday, "date");

    var dependentRow = relativeComponent.createRow('��������� �� ���������?', true);
    var params = ["��", "���"];
    relativeComponent.addNewField(dependentRow, "dependent", info.dependent, "radio", params);

    var creditRow = relativeComponent.createRow('���� ������� � ���������?', true);
    params = ["��", "���", "�� ����"];
    relativeComponent.addNewField(creditRow, "credit", info.credit, "radio", params);

    var employeeRow = relativeComponent.createRow(null, false);
    params = ["�������� ����������� ���������"];
    var employeeInput = relativeComponent.addNewField(employeeRow, "employee", info.employee, "check", params);

    // 8. ����� ������ ������������, ���� �� �������� ����������� ���������
    // 8.1 ��������� ������ ���� � ���� ����
    var employeePlaceRow = $(relativeComponent.createRow('����� ������', true));
    var employeePlaceValue = info.employeePlace!=null && info.employeePlace.length !=0 ? info.employeePlace.replace(new RegExp("\"",'g'), "&quot;") : info.employeePlace;
    relativeComponent.addNewField(employeePlaceRow, "employeePlace", employeePlaceValue, "text", { maxLength : 50, size : 50 });

    // 8.2 �� ������ "�������� ����������� ���������" ������ �������,
    // ������� ����� ����������/�������� ���� ����� ������ � ����������� �� �������� ������
    var showOrHideEmployeePlace = function()
    {
        if (employeeInput.find('input').is(':checked'))
            employeePlaceRow.css('display', '');
        else employeePlaceRow.css('display', 'none');
    };

    employeeInput.change(showOrHideEmployeePlace);

    // 8.3 ����������/������ ����
    showOrHideEmployeePlace();

    return relativeComponent;
}