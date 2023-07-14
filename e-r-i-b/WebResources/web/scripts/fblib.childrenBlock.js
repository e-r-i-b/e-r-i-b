/**
 * ������� "���� ���������� � �����" � "������ ������"
 *
 */

/**
 * ��������� "������ ������ ��� ������� ���������� � �����"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createChildrenList(maxCount, info)
{
    var childrenList = createComponentList(maxCount, info, '�������� ���������� � �����', 'child_', '<span class="form-row-title size20">����</span>', 285, true);

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
 * ��������� "���� ��� ������� ���������� � �����"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createChildBlock(parentComponent, info)
{
    var childComponent = createComponent(parentComponent);

    childComponent.createTitleRow("���������� � �������");

    var childTypeRow = childComponent.createRow('������� �������', false);
    childComponent.initPreparedSelectField(childTypeRow, "relativeType", info.relativeType, "childrenTypes");

    var surnameRow = childComponent.createRow('�������', true);
    childComponent.addNewField(surnameRow, "surname", info.surname, "text", { maxLength : 120, size : 50 });

    var nameRow = childComponent.createRow('���', true);
    childComponent.addNewField(nameRow, "name", info.name, "text", { maxLength : 120, size : 50 });

    var patrRow = childComponent.createRow('��������', false);
    childComponent.addNewField(patrRow, "patrName", info.patrName, "text", { maxLength : 120, size : 50});

    var birthdayRow = childComponent.createRow('���� ��������', true);
    childComponent.addNewField(birthdayRow, "birthday", info.birthday, "date", null);

    var dependentRow = childComponent.createRow('��������� �� ���������?', true);
    childComponent.addNewField(dependentRow, "dependent", info.dependent, "radio", ["��", "���"]);

    var creditRow = childComponent.createRow('���� ������� � ���������?', true);
    childComponent.addNewField(creditRow, "credit", info.credit, "radio", ["��", "���", "�� ����"]);

    var employeeRow = childComponent.createRow(null, false);
    var employeeInput = childComponent.addNewField(employeeRow, "employee", info.employee, "check", ["�������� ����������� ���������"]);

    // 8. ����� ������ ������, ���� �� �������� ����������� ���������
    // 8.1 ��������� ������ ���� � ���� ����
    var employeePlaceRow = $(childComponent.createRow('����� ������', true));
    var childPlaceValue = info.employeePlace!=null && info.employeePlace.length !=0 ? info.employeePlace.replace(new RegExp("\"",'g'), "&quot;") : info.employeePlace;
    childComponent.addNewField(employeePlaceRow, "employeePlace", childPlaceValue, "text", { maxLength : 50, size : 50 });

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

    return childComponent;
}
