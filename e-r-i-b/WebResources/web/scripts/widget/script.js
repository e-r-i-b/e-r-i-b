define("widget/script", [
    "dojo/io/script",
    "widget/log"
], function(dojoIOScript, log)
{
    /**
     * Работа с js-скриптами
     */

    var cls;

    cls =
    {
        /**
         * Загружает внешний java-script, если он ещё не загружен
         * @param scriptId - ID тега скрипта, в который будет загружен скрипт
         *  (для избежания повторов загрузки)
         * @param checkString - глобальная переменная, которая должна появиться после загрузки
         * @param scriptURL - путь к скрипту
         * @param onload - вызывается по окончании загрузки
         */
        include: function(/*String*/ scriptId, /*String*/ scriptURL, checkString, /*Function ?*/ onload)
        {
            var jsonpArgs =
            {
                url: scriptURL,

                checkString: checkString,

                load: function()
                {
                    console.log("Скрипт " + scriptURL + " загружен");

                    if (onload != undefined)
                        onload();
                },

                error: function(error)
                {
                    log.error("Не удалось загрузить скрипт " + scriptURL, error);
                }
            };
            dojoIOScript.get(jsonpArgs);
        }
    };

    return cls;
});
