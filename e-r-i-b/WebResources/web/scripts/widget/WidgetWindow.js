define("widget/WidgetWindow", [
    "dojo/_base/declare",
    "dojo/dom-class",
    "dojo/dom-attr",
    "dojox/layout/ContentPane",
    "dijit/_Container",
    "dijit/_Contained"
], function(declare, domClass, domAttr, ContentPane, Container, Contained)
{
    /**
     * Окно виджета
     */
    return declare("widget.WidgetWindow", [ ContentPane, Container, Contained ],
    {
        /**
         * Кодовое обозначение виджета
         * [readonly]
         */
        codename : "",

        title : "",

        size : "",

        /**
         * Флажок "виджет может менять размер от компактного к полному и обратно"
         * [readonly]
         */
        sizeable: undefined,
        
        /**
         * Режим загрузки виджета,
         *  "sync" - синхронный: виджет загружается вместе со страницей
         *  "async" - асинхронный: виджет загружается отдельным ajax-запросом
         * [readonly]
         */
        loadMode : "",

        /**
         * Адрес виджет-экшена
         * [readonly]
         */
        url : "",

        /**
         * Контейнер виджетов
         */
        container : undefined,

        widget: undefined,

        /**
         * Дефиниция виджета
         */
        definition: undefined,

        ///////////////////////////////////////////////////////////////////////

        /**
         * Возвращает режим веб-страницы
         * @return 'view' / 'edit'
         */
        getWebPageMode : function()
        {
            return this.container.getWebPageMode();
        },

        startup : function()
        {
            this.container = this.getParent();

            this.inherited(arguments);

            domClass.add(this.domNode, "WidgetWindow");
            domAttr.set(this.domNode, "codename", this.codename);
        },

        /**
         * Сохраняет виджет
         */
        saveWidget: function(widgetSettings)
        {
            // Передаём задачу начальнику
            this.container.saveWidget(this, widgetSettings);
        },

        /**
         * Закрывает и удаляет виджет
         */
        closeWidget: function(/*widget*/)
        {
            // Передаём задачу начальнику
            this.container.closeWidget(this);
        },

        isWidgetChanged: function()
        {
            return this.widget.mode == "edit";
        },

        discardWidget : function()
        {
            // Передаём задачу начальнику
            this.container.discardWidget(this);
        },

        ///////////////////////////////////////////////////////////////////////
        // События окна

        /**
         * Изменился режим страницы (просмотр <-> редактирование)
         */
        onWebPageModeChanged : function()
        {
            if (this.widget != undefined)
                this.widget.refresh();
        }
    });
});
