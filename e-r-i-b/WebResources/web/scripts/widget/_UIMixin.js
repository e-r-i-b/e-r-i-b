define("widget/_UIMixin", [
    "dojo/_base/declare",
    "dojo/query",
    "dojo/dom-attr",
    "dojo/NodeList-traverse",
    "dijit/registry"
], function(declare, query, domAttr, nodeListTraverse, dijitRegistry)
{
    /**
     * Вспомогательные методы для работы с нодами виджета
     */
    return declare("widget._UIMixin", [],
    {
        findWebPage: function()
        {
            var nl = new query.NodeList(this.domNode);
            nl = nl.closest(".WebPage");
            if (nl.length == 0)
                return undefined;
            return dijitRegistry.byNode(nl[0]);
        },

        findPanel: function(panelName, panel)
        {
            return this._findSingle("[panel='" + panelName + "']", panel || this.domNode);
        },

        findButton: function(buttonName, panel)
        {
            return this._findSingle("[button='" + buttonName + "']", panel || this.domNode);
        },

        findField: function(fieldName, panel)
        {
            return this._findSingle("[field='" + fieldName + "']", panel || this.domNode);
        },

        findLabel: function(labelName, panel)
        {
            return this._findSingle("[label='" + labelName + "']", panel || this.domNode);
        },
        findById: function(elemId)
        {
            return this._findSingle("#"+elemId,this.domNode);
        },

        setLabelText: function(labelName, panel, labelValue)
        {
            if (labelValue == undefined)
                labelValue = "";
            var label = this.findLabel(labelName, panel);
            if (label != undefined)
                $(label).text(labelValue);
        },

        findImage: function(imageName, panel)
        {
            return this._findSingle("[image='" + imageName + "']", panel || this.domNode);
        },

        setImageUrl: function(imageName, panel, url)
        {
            if (url == undefined)
                url = "";
            var image = this.findImage(imageName, panel);
            if (image != undefined)
                image.src = url;
        },

        setImageTitle: function(imageName, panel, title)
        {
            if (title == undefined)
                title = "";
            var image = this.findImage(imageName, panel);
            if (image != undefined)
                image.title = title;
        },

        _findSingle: function(queryString, node)
        {
            var nl = query(queryString, node);
            if (nl.length == 0)
                return undefined;
            return nl[0];
        },
        
        toggleNodeAttrib: function(node, attrib, condition)
        {
            if (condition)
                domAttr.remove(node, attrib);
            else domAttr.set(node, attrib, "");
        },

        /**
         * Отображает всплывающее окно с сообщением о превышении количества виджетов на странице
         * @param message - сообщение
         */
        showMessageMaxCount: function(message)
        {
            win.aditionalData("errorManyIdenticWidgets",
            {
                onOpen: function()
                {
                    $("#errorManyIdenticWidgets span.many-same-widget-text").text(message);
                    return true;
                }
            });
            win.open("errorManyIdenticWidgets");
        }
    });
});
