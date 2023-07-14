define("widget/SkinSelector",[
    "dojo/_base/declare",
    "dojo/_base/connect",
    "dojo/dom-class",
    "dijit/_WidgetBase",
    "widget/_UIMixin",
    "dojo/query",
    "dojo/dom-attr"
], function(declare, connect, domClass, WidgetBase, UIMixin, query, domAttr)
{
    /**
     * Выбор скина
     */
    return declare("widget.SkinSelector", [WidgetBase, UIMixin],
    {
        /**
         * первоначальный скин
         */
        oldSkinId: undefined,

        /**
         * текущий выбранный скин
         */
        currentSkinId: undefined,

        startup: function()
        {
            this.inherited(arguments);

            var webPage = this.findWebPage();
            webPage.registerSkinSelector(this);
            this.oldSkinId = this.currentSkinId = this.getSkinId();
            var skins = query(".previewSkins .skin a", this.domNode);
            var self = this;
            for (var i = 0; i < skins.length; i++)
                connect.connect(skins[i], "onclick", function()
                {
                    var oldSkin = query(".activeSkin", self.domNode)[0];
                    domClass.remove(oldSkin, "activeSkin");
                    domClass.add(this, "activeSkin");
                    self.currentSkinId = domAttr.get(this, "skinId");
                });
            this.installScrollbar();
        },

        installScrollbar: function()
        {
            tinyscrollbar($('.previewSkins').get(0),
            {
                axis: 'x',
                size: 398,
                start: function()
                {
                    $(".previewSkins").disableTextSelect();
                },
                stop: function()
                {
                    $(".previewSkins").enableTextSelect();
                },
                enable: function()
                {
                    $(".button-skin-selector").show();
                },
                disable: function()
                {
                    $(".button-skin-selector").hide();
                },
                leftOrUpArrow: $(".previewSkins .arrow-up"),
                rightOrDownArrow: $(".previewSkins .arrow-down")
            });

            $(".left-button-skin-selector").click(function()
            {
                $(".previewSkins .arrow-up").click();
            });

            $(".right-button-skin-selector").click(function()
            {
                $(".previewSkins .arrow-down").click();
            });
        },

        isChange: function()
        {
            return this.oldSkinId != this.currentSkinId;
        },

        getSkinId: function()
        {
            var currentSkin = query(".activeSkin", this.domNode)[0];
            return domAttr.get(currentSkin, "skinId");
        }
    });
});