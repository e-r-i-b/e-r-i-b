define("widget/log", [], function()
{
    /**
     * Методы логирования
     */

    var cls;

    cls =
    {
        /**
         * Логирование исключения
         * @param message - текст ошибки
         * @param exception - исключение
         */
        error : function(/*String*/ message, /*Exception?*/ exception)
        {
            if (exception == undefined)
                console.error(message);
            else console.error(message, exception.stack);
        }
    };

    return cls;
});
