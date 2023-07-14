define("widget/WidgetPicture",[
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/on",
    "dijit/_WidgetBase",
    "widget/_UIMixin"
], function(lang, declare, on, WidgetBase, UIMixin)
{
    /**
     * Картинка виджета в Каталоге виджетов
     */
    return declare("widget.WidgetPicture", [WidgetBase, UIMixin],
    {
        /**
         * Кодификатор виджет-дефиниции
         * [readonly]
         */
        codename: undefined,

        /**
         * Имя виджета для отображения пользователю
         * [readonly]
         */
        username: undefined,

        /**
         * Интернет-адрес экшена, обрабатывающего HTTP-запросы от виджета
         * [readonly]
         */
        url: undefined,

        /**
         * Исходный размер нового виджета
         */
        initialSize: undefined,

        /**
         * Флажок "виджет может менять размер от компактного к полному и обратно"
         */
        sizeable: undefined,

        /**
         * Дефиниция виджета
         */
        definition: undefined,

        ///////////////////////////////////////////////////////////////////////

        startup: function()
        {
            this.inherited(arguments);

            var addButton = this.findButton("add");
            on(addButton, "click", lang.hitch(this, function() {
                this.onAdd();
            }));
        },

        /**
         * Перехватывается в каталоге
         */
        onAdd: function()
        {
        }
    });
});
