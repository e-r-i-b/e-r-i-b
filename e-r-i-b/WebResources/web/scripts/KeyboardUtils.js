/**
 * Обработка события нажатия клавиши Enter на странице логина
 * @param event оригинальное событие
 */
function onEnterKeyPress(event, func)
{
    event = event || window.event;
    var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
    // если не enter - пропускаем
    if(code != 13)
        return;

    preventDefault(event);
    func();
}
