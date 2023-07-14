/**
 * ��������� ������� ������� ������� Enter �� �������� ������
 * @param event ������������ �������
 */
function onEnterKeyPress(event, func)
{
    event = event || window.event;
    var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
    // ���� �� enter - ����������
    if(code != 13)
        return;

    preventDefault(event);
    func();
}
