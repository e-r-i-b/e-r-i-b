//Отвечает за переключением местоположения sidemenu
define("widget/WebPageFormat",[
        "dojo/_base/declare",
        "dojo/dom-construct",
        "dojo/query",
        "dojo/dom-class",
        "dijit/_WidgetBase",
        "dijit/registry",
        "dojo/dom-attr"
        ], function(declare, domConstruct, query, domClass, _WidgetBase, dijitRegistry, domAttr){
        declare("widget.WebPageFormat", [_WidgetBase], {

            //расположение sidemenu (left,right) 
            direction: "",

            startup: function()
            {
                this.inherited(arguments);
                this.eventSetup();
                domClass.add(this.domNode, "formatWidget");
                this.init(this.conversion(this.direction));
            },

            init: function(location)
            {
                domClass.add(this.domNode, "formatWidget" + location);
                domClass.add(this.domNode, "formatWidget" + location + this.getSuffix(location) + "Out");
            },

            onMouseOver: function()
            {
                this.mouseHandler("Out", "Over");
            },

            onMouseOut: function()
            {
                this.mouseHandler("Over", "Out");
            },

            eventSetup: function()
            {
                this.connect(this.domNode, "onmouseover", "onMouseOver");
                this.connect(this.domNode, "onmouseout", "onMouseOut");
                this.connect(this.domNode, "onclick", "onClick");
            },

            conversion: function(direction)
            {
                return direction == "left" ? "Left" : "Right";
            },

            getSuffix: function(location)
            {
                var currentLocation = this.conversion(this.getSidemenuLocation());
                return location == "Left" ? currentLocation == "Left" ? "Current" : "" :
                    currentLocation == "Right" ? "Current" : "";
            },

            mouseHandler: function(ending1, ending2)
            {
                var location = this.conversion(this.direction);
                var suffix = this.getSuffix(location);
                domClass.remove(this.domNode, "formatWidget" + location + suffix + ending1);
                domClass.add(this.domNode, "formatWidget" + location + suffix + ending2);
            },
            
            onClick: function()
            {
                if (domAttr.get(this.domNode, "class").indexOf("Current") != -1)
                    return false;
                var webPage = this.globalFindWebPage();
                webPage.sideContainer.location = this.getSidemenuLocation() == "left" ? "right" : "left";
                webPage.sideContainer.location == "left" ?
                    this.replaceContainerClasses("last-item", "first-item") :
                    this.replaceContainerClasses("first-item", "last-item");

                var list = query("#sortable")[0];
                var firstItemList = query(".first-item")[0].parentNode;
                domConstruct.place(firstItemList, list, "first");

                var left =  query('.formatWidgetLeft')[0];
                var right = query('.formatWidgetRight')[0];
                if (this.direction == "left")
                {
                    domClass.remove(left, "formatWidgetLeftOver");
                    domClass.add(left, "formatWidgetLeftCurrentOver");
                    domClass.remove(right, "formatWidgetRightCurrentOut");
                    domClass.add(right, "formatWidgetRightOut");
                }
                else
                {
                    domClass.remove(left, "formatWidgetLeftCurrentOut");
                    domClass.add(left, "formatWidgetLeftOut");
                    domClass.remove(right, "formatWidgetRightOver");
                    domClass.add(right, "formatWidgetRightCurrentOver");
                }
            },

            globalFindWebPage: function()
            {
                return dijitRegistry.byNode(query(".WebPage")[0]);
            },

            replaceContainerClasses: function(firstItemClass, lastItemClass)
            {
                domClass.replace(query("#workspace")[0], firstItemClass, lastItemClass);
                domClass.replace(query("#right-section")[0], lastItemClass, firstItemClass);
            },

            getSidemenuLocation: function()
            {
                return domClass.contains(query("#right-section")[0], "last-item") ? "right" : "left";
            }
        });
    });