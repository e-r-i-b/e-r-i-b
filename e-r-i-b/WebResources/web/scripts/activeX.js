
 /**
 * получение объекта ActiveX по его ProgID
 * @param progId - индефикатор ActiveX. Как правило состиоит из имении файла и названия класса(например: SBRF.Server)
 * @return - ссылка на ActiveX объект.
 */
function createActvieXObject(progId)
{
    try
    {
        if (window.ActiveXObject)
        {
            return new ActiveXObject(progId)
        }
        else if (window.GeckoActiveXObject)
        {
            return new GeckoActiveXObject(progId)
        }
        else
        {
            alert("Этот браузер не поддерживает создание ActiveX объектов.\n"+
                 "Пожалуйста используйте Internet Explorer версии 6 и выше.");
        }
    }
    catch (err)
    {
        window.alert("Ошибка при создании ActiveX объекта.\n\n"+ err.message);
        if (err.message.indexOf("80004005") >= 0)
        {
            window.alert("Отредактируйте настройки Вашего бразуера так, "+
                              "чтобы настройки безопаcности разрешали выполнение ActiveX.");
        }
    }
}