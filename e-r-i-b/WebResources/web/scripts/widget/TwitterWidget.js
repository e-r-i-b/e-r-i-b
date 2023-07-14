define("widget/TwitterWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "widget/script",
    "widget/_WidgetBase",
    "dojo/dom-construct",
    "dojo/query",
    "dojo/dom-geometry"    
], function(lang, declare, script, _WidgetBase, domConstruct, query, domGeom)
{
    var TWITTER_API_URL = "//platform.twitter.com/widgets.js";

    return declare("widget.TwitterWidget", [ _WidgetBase ],
    {
        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "Twitter"
            });
        },

        size: undefined,

        INTERVAL: 5,

        intervalID: undefined,

        containerIFrame: undefined,

        ///////////////////////////////////////////////////////////////////////

        onStartup: function()
        {
            this.inherited(arguments);

            this.containerIFrame = query(".twitter-iframe-widget", this.domNode)[0];
            var body = query("body")[0];
            domConstruct.place(this.containerIFrame, body, "first");
            script.include("twitter-api", TWITTER_API_URL, "twttr");
            var self = this;
            var selfDomNode = self.domNode;
            var content = query(".whiteTopRT", this.domNode)[0];
            this.intervalID = window.setInterval(function()
            {
                if (!self.containerIFrame)
                    return;
                self.containerIFrame.style.display = !self.getParent() ? "none" : "block";
                domGeom.setContentSize(self.containerIFrame, {w: selfDomNode.clientWidth - 40});
                var widgetContent = domGeom.getContentBox(content);
                var iframeContent = domGeom.getContentBox(self.containerIFrame);
                var temp = (widgetContent.w / 2) - (iframeContent.w / 2);
                var position = domGeom.position(selfDomNode, true);
                domGeom.setMarginBox(self.containerIFrame, {l: position.x + temp, t: position.y + 30});
            }, this.INTERVAL);
        },

        onStartMove: function()
        {
            this.styleChange(25);
        },

        onStopMove: function()
        {
            this.styleChange(20);
        },

        styleChange: function(zIndex)
        {
            this.containerIFrame.style.zIndex = zIndex;
        },

        destroy: function(/*Boolean*/ preserveDom)
        {
            this.inherited(arguments);
            clearInterval(this.intervalID);
            var body = query("body")[0];
            body.removeChild(this.containerIFrame);
        }
    });
});
