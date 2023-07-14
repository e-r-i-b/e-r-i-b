define("widget/ViceWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/on",
    "widget/_WidgetBase"
], function(lang, declare, on, _WidgetBase)
{
    return declare("widget.ViceWidget", [ _WidgetBase ],
    {
        /**
         * Режим "Виджет недоступен"
         */
        notAvailableMode: false,
        /**
         * Режим "Виджет отключен"
         */
        forbiddenMode: false,

        isViceWidget : function()
        {
            return true;
        },

        onEdit: function()
        {
        },

        onStartup: function()
        {
            this.inherited(arguments);

            if (this.forbiddenMode)
                this.showForbiddenMode();
        },

        onRoll: function()
        {

            this.inherited(arguments);
        },

        showForbiddenMode: function()
        {
            $("#"+this.window.id+" .ajaxLoader").hide();
            $("#"+this.window.id+" .forbiddenWidget").show();    
        }
    });
});
