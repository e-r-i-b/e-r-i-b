define([
    "dojo/_base/declare",
    "dojo/_base/connect",
    "widget/_UIMixin",
    "dojo/dom-attr",
    "dijit/layout/TabController",
    "dijit/layout/TabContainer"
], function(declare, connect, UIMixin, domAttr, TabController, TabContainer)
{
    /**
     * Контроллер для контейнера закладок.
     * Переопределен с целью возможности выполнения функции, определенной пользователем на закладке.
     * Функция определяется аттрибутом select. 
     */
    var TabControllerEx = declare("widget.TabControllerEx", TabController, {
        onButtonClick: function(page){
            if (page.select)
            {
                var func = new Function(page.select);
                func();
            }
            else
                this.inherited(arguments);
        }
    });

    /**
     * Контейнер закладок в выдвижной панели.
     * Переопределяет контроллер.
     * Также содержит в себе текущую выбранную панель.
     */
    return declare("widget.TabContainerEx", [TabContainer, UIMixin], {
        controllerWidget: "widget.TabControllerEx",

        selectedPane: undefined,

        startup: function()
        {
            this.inherited(arguments);

            var webPage = this.findWebPage();
            webPage.registerTabContainer(this);
            var panes = this.getChildren();
            var self = this;
            for (var i = 0; i < panes.length; i++)
                connect.connect(panes[i], "onShow", function()
                {
                    self.selectedPane = this;
                });
        },

        isSkinSelector: function()
        {
            return domAttr.get(this.selectedPane.domNode, "id") == "skinSelector";
        },

        getSelectedPane: function()
        {
            return this.selectedPane;
        }
    });
});