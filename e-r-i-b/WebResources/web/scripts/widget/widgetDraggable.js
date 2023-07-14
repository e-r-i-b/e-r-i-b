define("widget/widgetDraggable", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/dom-class",
    "dojo/dom-geometry",
    "dijit/registry"
], function(lang, declare, query, domClass, domGeom, dijitRegistry)
{
    return declare("widget.Draggable", [],
    {
        RIGHT_SECTION: "right-section",

        CENTER_SECTION: "center-section",

        HEADER_SECTION: "header-section",

        WIDE_WIDTH: 720,

        COMPACT_WIDTH: 355,

        SIDE_WIDTH: 220,

        PICTURE_PLACEHOLDER_HEIGHT: 200,

        PLACEHOLDER: "<div id='placeholder' codename='placeholder'></div>",

        WIDGET_WRAPPER: "<div class='widget-wrapper'></div>",

        WIDGET_WRAPPER_CLEAR: "<div class='widget-wrapper clear'></div>",

        centerContainer: undefined,

        sideContainer: undefined,

        currentSection: undefined,

        findWidget: function(codeName)
        {
            return $("div[codename='" + codeName + "']");
        },

        isArray: function(value)
        {
            return typeof(value) != "string";
        },

        remove: function(container)
        {
            var widgets = container.widgets;
            for (var i = 0; i < widgets.length; i++)
            {
                if (this.isArray(widgets[i])) // массив
                {
                    for (var j = 0; j < widgets[i].length; j++)
                        for (var k = 0; k < widgets[i][j].length; k++)
                        {
                            this.findWidget(widgets[i][j][k]).hide();
                            $("#header").append(this.findWidget(widgets[i][j][k]));
                        }
                }
                else // строка
                {
                    this.findWidget(widgets[i]).hide();
                    $("#header").append(this.findWidget(widgets[i]));
                }
            }

            query(".widget-wrapper, .widget-wrapper.clear", container.domNode).forEach(function(node){
                $(node).remove();
            });
        },

        layout: function(container)
        {
            var widgets = container.widgets;
            var widget;
            for (var i = 0; i < widgets.length; i++)
            {
                if (this.isArray(widgets[i])) // массив
                {
                    if (widgets[i][0].length != 0 || widgets[i][1].length != 0)
                    {
                        for (var j = 0; j < widgets[i].length; j++)
                        {
                            var div = $(this.WIDGET_WRAPPER);
                            if (j > 0)
                                div.addClass("widget-wrapper-next");
                            $(container.domNode).append(div);
                            for (var k = 0; k < widgets[i][j].length; k++)
                            {
                                widget = this.findWidget(widgets[i][j][k]);
                                div.append(widget);
                                widget.show();
                            }
                        }
                        $(container.domNode).append(this.WIDGET_WRAPPER_CLEAR);
                    }
                }
                else // строка
                {
                    widget = this.findWidget(widgets[i]);
                    $(container.domNode).append(widget);
                    widget.show();
                }
            }
        },

        widgetsToString: function(container)
        {
            var result = "";
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (this.isArray(container.widgets[i]))
                {
                    for (var j = 0; j < container.widgets[i].length; j++)
                        for (var k = 0; k < container.widgets[i][j].length; k++)
                            result += "div[codename='" + container.widgets[i][j][k] + "']" + ", ";
                }
                else
                    result += "div[codename='" + container.widgets[i] + "']" + ", ";
            }
            return result.substr(0, result.length - 2);
        },

        positionInWorkspace: function($element)
        {
            var top = 0;
            var left = 0;
            var element = $element.get(0);
            while (element)
            {
                top += parseInt(element.offsetTop);
                left += parseInt(element.offsetLeft);
                element = element.offsetParent;
            }
            return {"left": left, "top": top};
        },

        setInDivs: function(sourceId, destId, ar)
        {
            for (var i = 0; i < ar.length; i++)
            {
                if (this.isArray(ar[i])) // массив
                {
                    for (var j = 0; j < ar[i].length; j++)
                        for (var k = 0; k < ar[i][j].length; k++)
                            if (ar[i][j][k] == sourceId)
                            {
                                ar[i][j][k] = destId;
                                return;
                            }
                }
                else // строка
                    if (ar[i] == sourceId)
                    {
                        ar[i] = destId;
                        return;
                    }
            }
        },

        mousePageXY: function(e)
        {
            var x = 0, y = 0;
            if (!e)
                e = window.event;
            if (e.pageX || e.pageY)
            {
                x = e.pageX;
                y = e.pageY;
            }
            else if (e.clientX || e.clientY)
            {
                x = e.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft) - document.documentElement.clientLeft;
                y = e.clientY + (document.documentElement.scrollTop || document.body.scrollTop) - document.documentElement.clientTop;
            }
            return {"x": x, "y": y};
        },

        getFirstInDivs: function(container, index)
        {
            if (!this.isArray(container.widgets[0]))
                return container.widgets[0];

            if (container.widgets[0][index].length > 0)
                return container.widgets[0][index][0];

            return this.getNextInDivs(container, 0, index, 0);
        },

        getNextInDivs: function(container, i, j, k)
        {
            if (k != null)
            {
                if (k + 1 < container.widgets[i][j].length)
                    return container.widgets[i][j][k + 1];
            }
            if (i + 1 >= container.widgets.length)
                return null;

            for (var a = i + 1; a < container.widgets.length; a++)
            {
                if (this.isArray(container.widgets[a]))
                {
                    if (container.widgets[a][j].length != 0)
                        return container.widgets[a][j][0];
                }
                else
                    return container.widgets[a];
            }
            return null;
        },

        center: function(element)
        {
            return this.positionInWorkspace(element).top + (element.height() / 2);
        },

        centerHelper: function(ui)
        {
            return ui.offset.top + (ui.helper.height() / 2);
        },

        vertCenter: function(element)
        {
            return this.positionInWorkspace(element).left + (element.width() / 2);
        },

        isLong: function(element)
        {
            if (this.isPicture(element))
                return this.getObject(element).initialSize != "compact";
            return (!domClass.contains(element, "compact"))
        },

        isPicture: function(element)
        {
            return domClass.contains(element, "WidgetPicture");
        },

        changePlaceholder: function(ar)
        {
            for (var a = 0; a < ar.length; a++)
            {
                if (this.isArray(ar[a])) // массив
                {
                    for (var b = 0; b < ar[a].length; b++)
                        for (var c = 0; c < ar[a][b].length; c++)
                            if (ar[a][b][c] == "placeholder")
                            {
                                ar[a][b][c] = "placeholder1";
                                return;
                            }
                }
                else // строка
                    if (ar[a] == "placeholder")
                    {
                        ar[a] = "placeholder1";
                        return;
                    }
            }
        },

        deleteDiv: function(codeName, ar)
        {
            for (var a = 0; a < ar.length; a++)
            {
                if (this.isArray(ar[a])) // массив
                {
                    for (var b = 0; b < ar[a].length; b++)
                        for (var c = 0; c < ar[a][b].length; c++)
                            if (ar[a][b][c] == codeName)
                            {
                                ar[a][b].splice(c, 1);
                                return;
                            }
                }
                else // строка
                    if (ar[a] == codeName)
                    {
                        ar.splice(a, 1);
                        return;
                    }
            }
        },

        clearEmptyInDivs: function(container)
        {
            var deletedId = [];
            var i;
            for (i = 0; i < container.widgets.length; i++)
            {
                var empty = true;
                if (this.isArray(container.widgets[i])) // массив
                {
                    for (var j = 0; j < container.widgets[i].length; j++)
                        if (container.widgets[i][j].length != 0)
                        {
                            empty = false;
                            break;
                        }
                    if (empty)
                        deletedId.push(i);
                }
            }
            if (deletedId.length > 0)
            {
                var temp = [];
                for (i = 0; i < container.widgets.length; i++)
                {
                    if ($.inArray(i, deletedId) < 0)
                        temp.push(container.widgets[i]);
                }
                container.widgets = temp;
            }
        },

        mergeDivs: function(container)
        {
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (this.isArray(container.widgets[i]) && i + 1 < container.widgets.length)
                {
                    var a = i + 1;
                    var dest = container.widgets[i];
                    while (this.isArray(container.widgets[a]) && a < container.widgets.length)
                    {
                        // записать
                        var source = container.widgets[a];
                        for (var j = 0; j < source.length; j++)
                        {
                            for (var k = 0; k < source[j].length; k++)
                                dest[j].push(source[j][k]);
                        }
                        // удалить
                        container.widgets.splice(a, 1);
                    }
                }
            }
        },

        intersectWithSmallDiv: function(container, i, j, k, codeName)
        {
            var ar0 = [];
            var ar1 = [];
            var a;
            for (a = 0; a < container.widgets[i][j].length; a++)
            {
                if (a <= k)
                    ar0.push(container.widgets[i][j][a]);
                else
                    ar1.push(container.widgets[i][j][a]);
            }

            var temp = $(this.findWidget(ar0[ar0.length - 1]));
            var maxTop = this.positionInWorkspace(temp).top + temp.height();
            var ar2 = [];
            var ar3 = [];
            var b = j == 0 ? 1 : 0;

            if (container.widgets[i][b].length > 0)
            {
                for (a = 0; a < container.widgets[i][b].length; a++)
                {
                    temp = $(this.findWidget(container.widgets[i][b][a]));
                    if (this.positionInWorkspace(temp).top + temp.height() <= maxTop)
                        ar2.push(container.widgets[i][b][a]);
                    else
                        ar3.push(container.widgets[i][b][a]);
                }
            }
            var ar = [];
            if (j == 0)
            {
                ar.push(ar0);
                ar.push(ar2);
            }
            else
            {
                ar.push(ar2);
                ar.push(ar0);
            }
            container.widgets[i] = ar;
            this.changePlaceholder(container.widgets);

            ar = [];
            if (j == 0)
            {
                ar.push(ar1);
                ar.push(ar3);
            }
            else
            {
                ar.push(ar3);
                ar.push(ar1);
            }
            container.widgets.splice(i + 1, 0, codeName);
            container.widgets.splice(i + 2, 0, ar);

            this.deleteDiv("placeholder1", container.widgets);
        },

        removeAndLayout: function(container)
        {
            this.remove(container);
            this.layout(container);
        },

        refresh: function(container)
        {
            this.clearEmptyInDivs(container);
            this.mergeDivs(container);
            this.removeAndLayout(container);
        },

        equal: function(old, widgets)
        {
            if (old.length != widgets.length)
                return false;
            for (var i = 0; i < widgets.length; i++)
            {
                if (this.isArray(widgets[i])) // массив
                {
                    if (old[i].length != widgets[i].length)
                        return false;
                    for (var j = 0; j < widgets[i].length; j++)
                    {
                        if (old[i][j].length != widgets[i][j].length)
                            return false;
                        for (var k = 0; k < widgets[i][j].length; k++)
                            if (old[i][j][k] != widgets[i][j][k])
                                return false;
                    }
                }
                else // строка
                    if (old[i] != widgets[i])
                        return false;
            }
            return true;
        },

        relativePosition: function(container)
        {
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (this.isArray(container.widgets[i])) // массив
                {
                    for (var j = 0; j < container.widgets[i].length; j++)
                        for (var k = 0; k < container.widgets[i][j].length; k++)
                            this.findWidget(container.widgets[i][j][k]).css("position", "relative");
                }
                else // строка
                    this.findWidget(container.widgets[i]).css("position", "relative");
            }
        },

        moved: function(container)
        {
            if (container.widgets.length == 0)
                return;
            var widgetDomNodes = query(this.widgetsToString(container), container.domNode);
            $(widgetDomNodes).extenddraggable(
            {
                handle: $("div.greenTopTitle, .grayRT.r-top, div.whiteTopTitle, div.orangeTopTitle"),
                cursor: "move",
                zIndex: 20,
                start: lang.hitch(this, function(event, ui)
                {
                    $(container).data("startingScrollTop", $(document).scrollTop());
                    var widgetWindow = this.getObject(ui.helper.get(0));
                    widgetWindow.widget.onStartMove();
                    this.startDrag(ui, container);
                }),
                stop: lang.hitch(this, function(event, ui)
                {
                    var widgetWindow = this.getObject(ui.helper.get(0));
                    widgetWindow.widget.onStopMove();
                    this.stopDrag(ui, container);
                }),
                drag: lang.hitch(this, function(event, ui)
                {
                    var st = parseInt($(container).data("startingScrollTop"));
                    ui.position.top -= $(document).scrollTop() - st;
                    var mousePosition = this.mousePageXY(event);
                    var workspace = $("#workspace");
                    var condition = mousePosition.x > $("body").width() || mousePosition.y > workspace.offset().top + workspace.height();
                    $(widgetDomNodes).draggable("option", "scroll", !condition);
                    container.isCenter() ? this.dragCenterSection(event, ui, container) : this.dragRightSection(event, ui, container);
                })
            });
        },

        startDrag: function(ui, container)
        {
            var top = this.positionInWorkspace(ui.helper).top;
            var left = this.positionInWorkspace(ui.helper).left;
            var height = ui.helper.height();
            ui.helper.after(this.PLACEHOLDER);
            this.setInDivs(ui.helper.attr("codename"), "placeholder", container.widgets);
            this.findWidget("placeholder").height(height);
            this.findWidget("placeholder").width(ui.helper.width());
            ui.helper.css("position", "absolute");
            $(container.domNode).append(ui.helper);
            ui.helper.css("margin-top", top);
            ui.helper.css("margin-left", left);
        },

        stopDrag: function(ui, container)
        {
            ui.helper.css("left", "");
            ui.helper.css("top", "");
            ui.helper.css("margin-top", "");
            ui.helper.css("margin-left", "");
            this.currentSection = undefined;
            if (this.sectionIsChange(container, ui))
            {
                var changedContainer = container == this.centerContainer ? this.sideContainer : this.centerContainer;
                this.stopCommon(changedContainer, ui);
                this.moved(changedContainer);
                var widgetWindow = dijitRegistry.byNode(ui.helper.get(0));
                this.relocateWidget(widgetWindow, container, changedContainer);
            }
            else
            {
                this.stopCommon(container, ui);
                container.save();
            }
        },

        stopCommon: function(container, ui)
        {
            this.setInDivs("placeholder", ui.helper.attr("codename"), container.widgets);
            $("#placeholder").replaceWith(ui.helper);
            this.relativePosition(container);
        },

        allowChangeSection: function(fromContainer, helper)
        {
            if (fromContainer == this.sideContainer)
                return true;
            var window = this.getObject(helper.get(0));
            if (window != undefined)
                return (window.size == "compact") || window.sizeable;
            return false;
        },

        sectionIsChange: function(container, ui)
        {
            var changedSection = container == this.centerContainer ? this.RIGHT_SECTION : this.CENTER_SECTION;
            return this.inSection(ui) == changedSection && this.allowChangeSection(container, ui.helper);
        },

        dragCenterSection: function(event, ui, container)
        {
            if (this.sectionIsChange(container, ui))
            {
                this.dragRightSection(event, ui, this.sideContainer);
                return;
            }
            if (this.currentSection != undefined && this.currentSection == this.RIGHT_SECTION)
                this.deleteDiv("placeholder", this.sideContainer.widgets);
            this.currentSection = this.CENTER_SECTION;
            var tempWidth = this.findWidget("placeholder").width();
            if (!domClass.contains(ui.helper.get(0), "compact"))
                this.findWidget("placeholder").width(720);
            else
                this.findWidget("placeholder").width(355);
            if (tempWidth == 220)
                this.removeAndLayout(container);    
            var temp;
            if (container.widgets.length == 0)
            {
                container.widgets.splice(0, 0, [["placeholder"], []]);
                this.removeAndLayout(container);
            }
            var old;
            var workspaceCenter = this.positionInWorkspace($(this.centerContainer.domNode)).left + ($(this.centerContainer.domNode).width() / 2);
            var index = this.mousePageXY(event).x > workspaceCenter ? 1 : 0;
            var next = this.getFirstInDivs(container, index);
            if (next == null) // все виджеты находятся в одной колонке, должны установить placeholder
            {
                if (!domClass.contains(ui.helper.get(0), "compact"))
                    temp = "placeholder";
                else
                    temp = index == 0 ? [["placeholder"], []] : [[], ["placeholder"]];
                this.changePlaceholder(container.widgets);
                container.widgets.splice(0, 0, temp);
                this.deleteDiv("placeholder1", container.widgets);
                this.refresh(container);
                return;
            }
            else if (this.centerHelper(ui) < this.center(this.findWidget(next))) // первый элемент
            {
                old = lang.clone(container.widgets);
                temp = this.isLong(ui.helper.get(0)) ? "placeholder" :
                    (index == 0 ? [["placeholder"], []] : [[], ["placeholder"]]);
                this.changePlaceholder(container.widgets);
                container.widgets.splice(0, 0, temp);
                this.deleteDiv("placeholder1", container.widgets);
                this.clearEmptyInDivs(container);
                this.mergeDivs(container);
                if (!this.equal(old, container.widgets))
                    this.removeAndLayout(container);
            }
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (container.widgets[i] == "placeholder")
                    continue;
                if (this.isArray(container.widgets[i])) // массив
                {
                    for (var j = 0; j < container.widgets[i].length; j++)
                        for (var k = 0; k < container.widgets[i][j].length; k++)
                        {
                            if (index == j && container.widgets[i][j][k] != "placeholder")
                            {
                                next = this.getNextInDivs(container, i, j, k);
                                if (this.isLong(ui.helper.get(0)))
                                {
                                    if ((this.centerHelper(ui) > this.center($(this.findWidget(container.widgets[i][j][k])))) && (next == null || this.centerHelper(ui) < this.center($(this.findWidget(next)))))
                                    {
                                        old = lang.clone(container.widgets);
                                        this.intersectWithSmallDiv(container, i, j, k, "placeholder");
                                        this.clearEmptyInDivs(container);
                                        this.mergeDivs(container);
                                        if (!this.equal(old, container.widgets))
                                            this.removeAndLayout(container);
                                        return;
                                    }
                                }
                                else
                                {
                                    if ((this.centerHelper(ui) > this.center($(this.findWidget(container.widgets[i][j][k])))) && (next == null || this.centerHelper(ui) < this.center($(this.findWidget(next)))))
                                    {
                                        old = lang.clone(container.widgets);
                                        this.changePlaceholder(container.widgets);
                                        container.widgets[i][j].splice(k + 1, 0, "placeholder");
                                        this.deleteDiv("placeholder1", container.widgets);
                                        this.clearEmptyInDivs(container);
                                        this.mergeDivs(container);
                                        if (!this.equal(old, container.widgets))
                                            this.removeAndLayout(container);
                                        return;
                                    }
                                }
                            }
                        }
                }
                else // строка
                {
                    next = this.getNextInDivs(container, i, index, null);
                    if (this.isLong(ui.helper.get(0)))
                    {
                        if ((this.centerHelper(ui) > this.center($(this.findWidget(container.widgets[i])))) && (next == null || this.centerHelper(ui) < this.center($(this.findWidget(next)))))
                        {
                            old = lang.clone(container.widgets);
                            this.changePlaceholder(container.widgets);
                            container.widgets.splice(i + 1, 0, "placeholder");
                            this.deleteDiv("placeholder1", container.widgets);
                            if (!this.equal(old, container.widgets))
                                this.removeAndLayout(container);
                            return;
                        }
                    }
                    else
                    {
                        if ((this.centerHelper(ui) > this.center($(this.findWidget(container.widgets[i])))) && (next == null || this.centerHelper(ui) < this.center($(this.findWidget(next)))))
                        {
                            old = lang.clone(container.widgets);
                            this.deleteDiv("placeholder", container.widgets);
                            temp = index == 0 ? [["placeholder"], []] : [[], ["placeholder"]];
                            container.widgets.splice(i + 1, 0, temp);
                            this.clearEmptyInDivs(container);
                            this.mergeDivs(container);
                            if (!this.equal(old, container.widgets))
                                this.removeAndLayout(container);
                            return;
                        }
                    }
                }
            }
        },

        dragRightSection: function(event, ui, container)
        {
            if (this.sectionIsChange(container, ui))
            {
                this.dragCenterSection(event, ui, this.centerContainer);
                return;
            }
            if (this.currentSection != undefined && this.currentSection == this.CENTER_SECTION)
                this.deleteDiv("placeholder", this.centerContainer.widgets);
            this.currentSection = this.RIGHT_SECTION;

            var tempWidth = this.findWidget("placeholder").width();
            this.findWidget("placeholder").width(this.SIDE_WIDTH);
            if (tempWidth != this.SIDE_WIDTH)
                this.removeAndLayout(container);
            if (container.widgets.length == 0)
            {
                container.widgets.splice(0, 0, "placeholder");
                this.removeAndLayout(container);
            }
            var next;
            var old;
            if (this.centerHelper(ui) < this.center(this.findWidget(container.widgets[0]))) // первый элемент
            {
                old = lang.clone(container.widgets);
                this.changePlaceholder(container.widgets);
                container.widgets.splice(0, 0, "placeholder");
                this.deleteDiv("placeholder1", container.widgets);
                if (!this.equal(old, container.widgets))
                    this.removeAndLayout(container);
            }
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (container.widgets[i] == "placeholder")
                    continue;
                next = this.getNextInRightSection(container, i);
                if ((this.centerHelper(ui) > this.center(this.findWidget(container.widgets[i]))) && (next == null || this.centerHelper(ui) < this.center(this.findWidget(next))))
                {
                    old = lang.clone(container.widgets);
                    this.changePlaceholder(container.widgets);
                    container.widgets.splice(i + 1, 0, "placeholder");
                    this.deleteDiv("placeholder1", container.widgets);
                    if (!this.equal(old, container.widgets))
                        this.removeAndLayout(container);
                    return;
                }
            }
        },

        addWidget: function(codeName, container)
        {
            if (!this.isAdded()) // добавление по кнопке
            {
                !domClass.contains(this.findWidget(codeName).get(0), "compact") ?
                    container.widgets.unshift(codeName) : //длинный
                    container.widgets.unshift([[codeName], []]); //короткий
                this.refresh(container);
                container.save();
            }
            else // добавление перемещением
            {
                this.setInDivs("placeholder", codeName, container.widgets);
                $("#placeholder").replaceWith(this.findWidget(codeName));
                container.save();
            }
        },

        findIndexesByName: function(container, codeName)
        {
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (this.isArray(container.widgets[i])) // массив
                {
                    for (var j = 0; j < container.widgets[i].length; j++)
                        for (var k = 0; k < container.widgets[i][j].length; k++)
                            if (container.widgets[i][j][k] == codeName)
                                return {i: i, j: j, k: k};
                }
            }
            return {i: -1};
        },

        resizeToWide: function(container, codeName)
        {
            var indexes = this.findIndexesByName(container, codeName);
            if (indexes.i == -1)
                return;

            var ar0 = [];
            var ar1 = [];
            var a;
            for (a = 0; a < container.widgets[indexes.i][indexes.j].length; a++)
            {
                if (a < indexes.k)
                    ar0.push(container.widgets[indexes.i][indexes.j][a]);
                else if (a > indexes.k)
                    ar1.push(container.widgets[indexes.i][indexes.j][a]);
            }

            var temp = $(this.findWidget(ar0[ar0.length - 1]));
            var maxTop = this.positionInWorkspace(temp).top + temp.height();
            var ar2 = [];
            var ar3 = [];
            var b = indexes.j == 0 ? 1 : 0;

            if (container.widgets[indexes.i][b].length > 0)
            {
                for (a = 0; a < container.widgets[indexes.i][b].length; a++)
                {
                    temp = $(this.findWidget(container.widgets[indexes.i][b][a]));
                    if (this.positionInWorkspace(temp).top + temp.height() <= maxTop)
                        ar2.push(container.widgets[indexes.i][b][a]);
                    else
                        ar3.push(container.widgets[indexes.i][b][a]);
                }
            }
            var ar = [];
            if (indexes.j == 0)
            {
                ar.push(ar0);
                ar.push(ar2);
            }
            else
            {
                ar.push(ar2);
                ar.push(ar0);
            }
            container.widgets[indexes.i] = ar;

            ar = [];
            if (indexes.j == 0)
            {
                ar.push(ar1);
                ar.push(ar3);
            }
            else
            {
                ar.push(ar3);
                ar.push(ar1);
            }
            container.widgets.splice(indexes.i + 1, 0, codeName);
            container.widgets.splice(indexes.i + 2, 0, ar);
        },

        widgetResize: function(container, codeName, newSize)
        {
            // A. Размер не поменялся => ничего не делаем
            if ((newSize == "wide") == this.isLong(this.findWidget(codeName).get(0)))
                return;

            // B. В боковом контейнере тоже делать нечего
            if (container == this.sideContainer)
                return;

            // C. Есть чего поменять
            if (newSize == "compact")
                this.setInDivs(codeName, [[codeName], []], container.widgets);
            else // wide
                this.resizeToWide(container, codeName);
            this.refresh(container);
            container.save();
        },

        widgetRemove: function(codeName, container)
        {
            for (var i = 0; i < container.widgets.length; i++)
            {
                if (this.isArray(container.widgets[i])) // массив
                {
                    for (var j = 0; j < container.widgets[i].length; j++)
                        for (var k = 0; k < container.widgets[i][j].length; k++)
                            if (container.widgets[i][j][k] == codeName)
                                container.widgets[i][j].splice(k, 1);
                }
                else // строка
                    if (container.widgets[i] == codeName)
                        container.widgets.splice(i, 1);
            }
            this.refresh(container);
            container.save();
        },

        getNextInRightSection: function(container, i)
        {
            if (i + 1 >= container.widgets.length)
                return null;
            return container.widgets[i + 1];
        },

        inSection: function(ui)
        {
            // A. Нет ни одного контейнера
            if (this.centerContainer == undefined && this.sideContainer == undefined)
                return undefined;
            // B. Нет центрального контейнера
            if (this.centerContainer == undefined)
                return this.RIGHT_SECTION;
            // C. Нет бокового контейнера
            if (this.sideContainer == undefined)
                return this.CENTER_SECTION;

            // Есть оба контейнера
            var windowCenterX = ui.offset.left + (ui.helper.width() / 2);
            var centerBox = domGeom.position(this.centerContainer.domNode, true);
            var sideBox = domGeom.position(this.sideContainer.domNode, true);

            // D Боковой контейнер слева
            if (sideBox.x < centerBox.x) {
                if (windowCenterX < centerBox.x)
                    return this.RIGHT_SECTION;
                else return this.CENTER_SECTION;
            }

            // E Боковой контейнер справа
            if (windowCenterX > sideBox.x)
                return this.RIGHT_SECTION;
            else return this.CENTER_SECTION;
        },

        relocateWidget: function(widgetWindow, fromContainer, toContainer)
        {
            widgetWindow.container = toContainer;
            var widget = widgetWindow.widget;

            if (widget != undefined) {
                widget.settings.size = "compact";
                widget.editSettings.size = "compact";
                widget.window.size = "compact";
                widgetWindow.container.saveWidget(widgetWindow, widget.settings);
            }
            
            // 2. Сохраняем раскладку
            fromContainer.save();
            toContainer.save();
        },

        installDraggingCatalog: function(node, onAddWidget)
        {
            var self = this;
            $(node).draggable(
            {
                cursor: "move",
                zIndex: 20,
                helper: "clone",
                appendTo: $("body"),
                drag: function(event, ui)
                {
                    var section = self.pictureSection(ui);
                    if (section == self.HEADER_SECTION)
                    {
                        if (self.isAdded())
                        {
                            var container = self.placeholderContainer();
                            self.deleteDiv("placeholder", container.widgets);
                            $("#placeholder").remove();
                            self.refresh(container);
                        }
                        return;
                    }
                    else
                    {
                        if (self.canAddOrSortable(ui, section))
                        {
                            if (!self.isAdded())
                                self.addPicture(ui, section);
                            else
                                section == self.CENTER_SECTION ? self.dragCenterSection(event, ui, self.centerContainer) : self.dragRightSection(event, ui, self.sideContainer);
                        }
                    }
                },
                stop: function(event, ui)
                {
                    if (self.isAdded())
                        onAddWidget();
                    ui.helper.remove();
                    self.refresh(self.centerContainer);
                    self.refresh(self.sideContainer);
                }
            });
        },

        canAddOrSortable: function(ui, section)
        {
            return !(section == this.RIGHT_SECTION && this.onlyWide(ui.helper.get(0)));
        },

        isAdded: function()
        {
            return this.placeholderSection() != this.HEADER_SECTION;
        },

        getObject: function(element)
        {
            return dijitRegistry.byNode(element);
        },

        onlyWide: function(element)
        {
            var object = this.getObject(element);
            return object.initialSize == "wide" && !object.sizeable;
        },

        addClassIfNotExist: function(helper, newClass)
        {
            helper.addClass(function()
            {
                return !helper.hasClass(newClass) ? newClass : "";
            });
        },

        addPicture: function(ui, section)
        {
            var container = section == this.CENTER_SECTION ? this.centerContainer : this.sideContainer;
            this.showPlaceholderPicture(ui, section, container);
            container.widgets.splice(0, 0, this.placeholderAsArrayItem(ui, section));
        },

        showPlaceholderPicture: function(ui, section, container)
        {
            var placeholder;
            if (container.widgets.length == 0)
                placeholder = $(this.PLACEHOLDER).appendTo($(container.domNode));
            else
            {
                var firstWidget = this.getFirstInDivs(container, 0);
                placeholder = $(this.PLACEHOLDER).insertBefore(this.findWidget(firstWidget));
            }
            placeholder.height(this.PICTURE_PLACEHOLDER_HEIGHT);
            placeholder.width(section == this.RIGHT_SECTION ? this.SIDE_WIDTH :
                (this.isLong(ui.helper.get(0)) ? this.WIDE_WIDTH : this.COMPACT_WIDTH));
            var picture = this.getObject(ui.helper.get(0));
            this.addClassIfNotExist(ui.helper, picture.initialSize);
        },

        placeholderAsArrayItem: function(ui, section)
        {
            if (section == this.RIGHT_SECTION)
                return "placeholder";
            return this.isLong(ui.helper.get(0)) ? "placeholder" : [["placeholder"], []];
        },

        pictureSection: function(ui)
        {
            var header = $("#header");
            return ((ui.offset.top + (ui.helper.height() / 2)) < this.positionInWorkspace(header).top + header.height()) ?
                this.HEADER_SECTION : this.inSection(ui);
        },

        placeholderContainer: function()
        {
            var section = this.placeholderSection();
            return section == this.CENTER_SECTION ? this.centerContainer :
                   section == this.RIGHT_SECTION ? this.sideContainer : undefined;
        },

        placeholderSection: function()
        {
            return !$.isEmptyObject($("#workspace #placeholder").get(0)) ? this.CENTER_SECTION :
                   !$.isEmptyObject($("#right-section #placeholder").get(0)) ? this.RIGHT_SECTION :
                   this.HEADER_SECTION;
        }
    });
});
