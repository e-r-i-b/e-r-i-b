define("widget/PersonalMenuWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/dom-attr",
    "dojo/_base/xhr",
    "dojo/_base/json",
    "widget/_WidgetBase"
], function(lang, declare, query, on, dom, domConstruct, domStyle, domAttr, xhr, json, _WidgetBase)
{
    /**
     * Виджет "доступные средства"
     */

    return declare("widget.PersonalMenuWidget", [ _WidgetBase ],
    {
     templatesVisibility: undefined,
     favouriteLinksVisibility: undefined,

     templatesCheckBoxes: undefined,
     favouriteLinksCheckBoxes: undefined,

     buttonCancel: undefined,
     buttonSave: undefined,

    ///////////////////////////////////////////////////////////////////////
    // Умолчательные настройки виджета

        defaultSettings: function()
          {
            return lang.mixin(this.inherited(arguments), {
                title: "Личное меню"
            });
          },

        onStartup: function()
           {
               this.inherited(arguments);

               this.templatesVisibility = this.settings.templatesVisibility;
               this.favouriteLinksVisibility = this.settings.favouriteLinksVisibility;

               this.templatesCheckBoxes = query("input[name='templateShowInThisWidget']", this.panelEdit);
               this.favouriteLinksCheckBoxes = query("input[name='favouriteLinkShowInThisWidget']", this.panelEdit);

               // Подключаем кнопки

               var linkUp = query("div.linkUp", this.panelEdit);
               on(linkUp, "click", function(evt){
                  linkMove(evt.target);
               });

               var linkDown = query("div.linkDown", this.panelEdit);
               on(linkDown, "click", function(evt){
                  linkMove(evt.target);
               });

               this.buttonCancel = this.findButton("cancel", this.panelEdit);
               on(this.buttonCancel, "click", lang.hitch(this, function(){
                    this.reset();
                    if (this.validate()) {
                        this.mode = "view";
                    }
                    this.refresh();
                }));

               this.buttonSave = this.findButton("save", this.panelEdit);
               on(this.buttonSave, "click", lang.hitch(this, function(){
                    this.update();

                    if (this.validate()) {
                        this.save();
                    }
                }));
           },

        onRefresh: function()
            {
                this.inherited(arguments);

                var editPanel = this.panelEdit;
                var templatesVisibility = this.editSettings.templatesVisibility;
                var beforeElement = query(".PersonalMenuTemplatesList", editPanel);
                i=0;
                var linkUp;
                var linkDown;
                templatesVisibility.forEach(
                    function(item){
                    var templateId = query("tr[templateId = '" + item.id + "']", editPanel);
                    if(i==0)
                    {
                        domConstruct.place(templateId[0], beforeElement[0], "first");
                        linkUp = query("tr[templateId = '" + item.id + "'] div.linkUp", editPanel);
                        domStyle.set(linkUp[0], "display", "none");
                        linkDown = query("tr[templateId = '" + item.id + "'] div.linkDown", editPanel);
                        domStyle.set(linkDown[0], "display", "block");
                    }
                    else
                    {
                        domConstruct.place(templateId[0], beforeElement[0], "after");
                        if(i==templatesVisibility.length-1)
                        {
                            linkUp = query("tr[templateId = '" + item.id + "'] div.linkUp", editPanel);
                            domStyle.set(linkUp[0], "display", "block");
                            linkDown = query("tr[templateId = '" + item.id + "'] div.linkDown", editPanel);
                            domStyle.set(linkDown[0], "display", "none");
                        }
                        else
                        {
                            linkUp = query("tr[templateId = '" + item.id + "'] div.linkUp", editPanel);
                            domStyle.set(linkUp[0], "display", "block");
                            linkDown = query("tr[templateId = '" + item.id + "'] div.linkDown", editPanel);
                            domStyle.set(linkDown[0], "display", "block");
                        }
                    }
                    beforeElement = templateId;
                    i++;
                });

                var templatesCheckBoxes = this.templatesCheckBoxes;
                i=0;
                templatesCheckBoxes.forEach(
                    function(item){
                        if(templatesVisibility[i].visible)
                            item.checked = true;
                        else item.checked = false;
                        i++;
                });

                this.templatesVisibility = templatesVisibility;

                var favouriteLinksVisibility = this.editSettings.favouriteLinksVisibility;
                beforeElement = query(".PersonalMenuFavouriteLinksList", editPanel);
                i=0;
                favouriteLinksVisibility.forEach(
                    function(item){
                        var favouriteLinkId = query("tr[favouriteLinkId = '" + item.id + "']", editPanel);
                        if(i==0)
                        {
                            domConstruct.place(favouriteLinkId[0], beforeElement[0], "first");
                            linkUp = query("tr[favouriteLinkId = '" + item.id + "'] div.linkUp", editPanel);
                            domStyle.set(linkUp[0], "display", "none");
                            linkDown = query("tr[favouriteLinkId = '" + item.id + "'] div.linkDown", editPanel);
                            domStyle.set(linkDown[0], "display", "block");
                        }
                        else
                        {
                            domConstruct.place(favouriteLinkId[0], beforeElement[0], "after");
                            if(i==favouriteLinksVisibility.length-1)
                            {
                                linkUp = query("tr[favouriteLinkId = '" + item.id + "'] div.linkUp", editPanel);
                                domStyle.set(linkUp[0], "display", "block");
                                linkDown = query("tr[favouriteLinkId = '" + item.id + "'] div.linkDown", editPanel);
                                domStyle.set(linkDown[0], "display", "none");
                            }
                            else
                            {
                                linkUp = query("tr[favouriteLinkId = '" + item.id + "'] div.linkUp", editPanel);
                                domStyle.set(linkUp[0], "display", "block");
                                linkDown = query("tr[favouriteLinkId = '" + item.id + "'] div.linkDown", editPanel);
                                domStyle.set(linkDown[0], "display", "block");
                            }
                        }
                        beforeElement = favouriteLinkId;
                        i++;
                    });

                var favouriteLinksCheckBoxes = this.favouriteLinksCheckBoxes;
                i=0;
                favouriteLinksCheckBoxes.forEach(
                    function(item){
                        if(favouriteLinksVisibility[i].visible)
                            item.checked = true;
                        else item.checked = false;
                        i++;
                });

                this.favouriteLinksVisibility = favouriteLinksVisibility;
            },

        onUpdate: function()
        {
            this.inherited(arguments);
            var templatesVisibility = [];
            var templatesCheckBoxes = query("input[name='templateShowInThisWidget']", this.panelEdit );
            i=0;
            templatesCheckBoxes.forEach(
                function(item){
                    templatesVisibility[i] = new Object();
                    templatesVisibility[i].id = item.value;
                    if(!item.checked)
                        templatesVisibility[i].visible = false;
                    else
                        templatesVisibility[i].visible = true;
                    i++;
            });

            this.templatesVisibility = templatesVisibility;
            this.editSettings.templatesVisibility = this.templatesVisibility;

            var favouriteLinksVisibility = [];
            var favouriteLinksCheckBoxes = query("input[name='favouriteLinkShowInThisWidget']", this.panelEdit );
            i=0;
            favouriteLinksCheckBoxes.forEach(
                function(item){
                    favouriteLinksVisibility[i] = new Object();
                    favouriteLinksVisibility[i].id = item.value;
                    if(!item.checked)
                        favouriteLinksVisibility[i].visible = false;
                    else
                        favouriteLinksVisibility[i].visible = true;
                    i++;
            });

            this.favouriteLinksVisibility = favouriteLinksVisibility;
            this.editSettings.favouriteLinksVisibility = this.favouriteLinksVisibility;
        }
        
        });
});
