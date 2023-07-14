define("widget/WidgetCatalog",[
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/_base/declare",
    "dojo/_base/connect",
    "dijit/_WidgetBase",
    "dijit/_Container",
    "dijit/_Contained",
    "widget/_UIMixin"
], function(lang, array, declare, connect, WidgetBase, Container, Contained, UIMixin)
{
    /**
     * Каталог виджетов, доступных пользователю для установки на страницу
     */
    return declare("widget.WidgetCatalog", [WidgetBase, Container, Contained, UIMixin],
    {
        draggable: undefined,

        startup: function()
        {
            this.inherited(arguments);

            var webPage = this.findWebPage();
            webPage.registerWidgetCatalog(this);
            var currContainer = this.draggable.centerContainer;
            //если центрального контейнера нет, будем добавлять виджеты в боковое меню
            if (currContainer == undefined)
                currContainer = this.draggable.sideContainer;
            array.forEach(this.getChildren(), lang.hitch(this, function(picture) {
                connect.connect(picture, "onAdd", lang.hitch(this, function() {
                    this.onAddWidget({
                        username:   picture.username,
                        codename:   picture.codename,
                        url:        picture.url,
                        size:       picture.initialSize,
                        sizeable:   picture.sizeable,
                        definition: picture.definition
                    }, currContainer);
                }));

                this.draggable.installDraggingCatalog(picture.domNode, lang.hitch(this, function() {
                    this.onAddWidget({
                        username:   picture.username,
                        codename:   picture.codename,
                        url:        picture.url,
                        size:       picture.initialSize,
                        sizeable:   picture.sizeable,
                        definition: picture.definition
                    }, this.draggable.placeholderContainer());
                }));
            }));
            this.installScrollbar();
        },

        installScrollbar: function()
        {
            tinyscrollbar($('.widget-catalog-border').get(0),
            {
                axis: 'x',
                size: 749,
                wheel: 120,
                start: function()
                {
                    $(".widget-catalog-border").disableTextSelect();
                    $(".button-widget-catalog").disableTextSelect();
                },
                stop: function()
                {
                    $(".widget-catalog-border").enableTextSelect();
                    $(".button-widget-catalog").enableTextSelect();
                },
                leftOrUpArrow: $(".widget-catalog-border .scroll-arrow-left"),
                rightOrDownArrow: $(".widget-catalog-border .scroll-arrow-right")
            });

            $(".left-button-widget-catalog").click(function()
            {
                $(".scroll-arrow-left").click();
            });

            $(".right-button-widget-catalog").click(function()
            {
                $(".scroll-arrow-right").click();
            });
        },

        /**
         * Вызывается при нажатии на картинку виджета
         * Перехватывается в WebPage
         * @param definition
         */
        onAddWidget: function(definition, container)
        {
        }
    });
});
