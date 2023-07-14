define("widget/_ScrollableMixin", [
    "dojo/_base/declare",
    "dojo/query",
    "dojo/dom-geometry"
], function(declare, query, domGeom)
{
    /**
     * Вспомогательные методы для работы с нодами виджета
     */
    return declare("widget._ScrollableMixin", [],
    {
        /**
         * Высота скроллируемой части виджета напоминания об оплате кредита
         */
        LOAN_NOTIFICATION_SCROLLABLE_HEIGHT: 72,
        /**
         * Высота скроллируемой части виджета мобильного баланса
         */
        MOBILE_BALANCE_SCROLLABLE_HEIGHT: 51,

        WEATHER_SCROLLABLE_HEIGHT: 110,

        /**
         * Дефолтная функция определения высоты скроллируемой части виджета 
         * @param widgetViewHeight - высота виджета в режиме просмотра
         */
        defaultHeight: function(widgetViewHeight)
        {
            var viewport = query(".viewport", this.domNode);
            var widgetEditHeight = domGeom.position(this.domNode, true).h;
            return domGeom.position(viewport[0], true).h + widgetViewHeight - widgetEditHeight;
        },
        /**
         * Высота кнопок-стрелок в скроллбаре
         */
        arrowsHeight: function()
        {
            var arrowUp = query(".arrow-up", this.domNode)[0];
            var arrowDown = query(".arrow-down", this.domNode)[0];
            return domGeom.position(arrowUp, true).h + domGeom.position(arrowDown, true).h;
        },
        /**
         * Высота скроллируемой части виджета напоминания ою оплате кредита
         */
        loanNotificationHeight: function()
        {
            return this.LOAN_NOTIFICATION_SCROLLABLE_HEIGHT;
        },
        /**
         * Высота скроллируемой части виджета мобильного баланса
         */
        mobileBalanceHeight: function()
        {
            return this.MOBILE_BALANCE_SCROLLABLE_HEIGHT;
        },
        /**
         * Высота скроллируемой части виджета погоды
         */
        weatherHeight: function()
        {
            return this.WEATHER_SCROLLABLE_HEIGHT;
        }
    });
});
