/**
 * ��������� "������ ������ ��� �������� ������ � �������������� �������"
 * @param maxCount - ������������ ���������� ������
 * @param info - ��� ��������� ������ ��� ���������� ������
 */
function createAccountsList(maxCount, info)
{
    var accountsList = createComponentList(maxCount, info, '�������� ����', 'accountBlank_', null, null ,true);

    accountsList.getTypifiedComponent = function(info)
    {
        return createAccount(this, info);
    };

    accountsList.getComponentListParentNode = function()
    {
        return document.getElementById("additionalAccountsArea");
    };

    accountsList.init();
    accountsList.updateCompletedFields();
    return accountsList;
}

/**
 * ��������� "���� ��� ������� ������ � �������������� ������"
 * @param info - ��� ��������� ������ ��� ���������� ������
 * @param parentComponent - ��������� "������ ������"
 */
function createAccount(parentComponent, info)
{
    var accountComponent = createComponent(parentComponent);

    accountComponent.createTitleRow("����������/���������� ����");

    var accountTypeRow = accountComponent.createRow('���', true);
    accountComponent.initPreparedSelectField(accountTypeRow, "type", info.type, "additionalAccountTypes");

    var accountSelectRow = accountComponent.createRow('�������� ���� �� ������', true);
    accountComponent.initPreparedSelectField(accountSelectRow, "resource", info.code, "additionalAccounts");

    var inputParams = {maxLength: 24, size : 62};
    var customNumberRow = accountComponent.createRow('����� �����', true);
    accountComponent.addNewField(customNumberRow, "rowCustomNumber", info.customNumber, "text", inputParams);
    accountComponent.addNewField(customNumberRow, "customNumber", info.customNumber, "hidden", {});
    $(customNumberRow).find('input:text').keyup(function()
    {
        $(customNumberRow).find('input:hidden').val( this.value.replace(/[^\d]*/g, '') );
    });
    $(customNumberRow).find('input:text').createMask('9999 9999 9999 9999 9999');

    hideOrShowNumberRow($(accountSelectRow).find('select'));

    return accountComponent;
}

//����� ��� ���� � ������
function hideOrShowNumberRow(select)
{
    var component = $(select).parent().parent().parent().parent();
    if ($(select).find(':selected').val() == "")
        $(component).find('[name*="_customNumber"]').parents('div[class*="form-row"]').css('display', '');
    else
        $(component).find('[name*="_customNumber"]').parents('div[class*="form-row"]').css('display', 'none');
}