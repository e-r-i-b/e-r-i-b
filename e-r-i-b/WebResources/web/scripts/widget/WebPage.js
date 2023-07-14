define("widget/WebPage", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/_base/connect",
    "dojo/dom-geometry",
    "widget/widgetDraggable",
    "dojo/_base/xhr",
    "dijit/_WidgetBase",
    "dijit/_Container",
    "dojo/topic",
    "dojo/query",
    "dojo/dom-class",
    "dojo/_base/kernel",
    "dojo/dom-attr"    
], function(lang, declare, connect, domGeometry, WidgetDraggable, xhr, WidgetBase, Container, topic, query, domClass, dojo, domAttr)
{
    /**
     * Веб-страница
     */
    return declare("widget.WebPage", [ WidgetBase, Container ],
    {
        draggable: undefined,

        /**
         * Контейнер виджетов центральной секции
         */
        centerContainer: undefined,

        /**
         * Контейнер виджетов секции бокового меню
         */
        sideContainer: undefined,

        /**
         * Каталог виджетов
         */
        catalog: undefined,

        containerSortingInstalled: false,

        /**
         * Куда переходим в случае протухшей сессии
         */
        loginURL: undefined,

        /**
         * Адрес экшена, отвечающего за работу с сохранением страницы
         * Передаётся с сервера во время загрузки контейнера
         * [readonly]
         */
        url: undefined,

        ///////////////////////////////////////////////////////////////////////
        editMode :  false,

        skinSelector: undefined,

        tabContainer: undefined,

        constructor: function()
        {
            this.draggable = new WidgetDraggable();
        },

        /**
         * Регистрация панели выбора скинов
         * Вызывается из панели выбора скинов
         * @param skinSelector - панель выбора скинов
         */
        registerSkinSelector: function(skinSelector)
        {
            this.skinSelector = skinSelector;
        },

        /**
         * Регистрация панели закладок
         * Вызывается из панели закладок
         * @param tabContainer - панель закладок
         */
        registerTabContainer: function(tabContainer)
        {
            this.tabContainer = tabContainer;
        },

        /**
         * Вызывается из виджет-контейнера
         * @param container
         */
        registerWidgetContainer: function(container)
        {
            if (container.location == 'left') {
                this.sideContainer = container;
                this.draggable.sideContainer = container;
                console.log("Загружен виджет-контейнер левой секции");
            }
            else if (container.location == 'center') {
                this.centerContainer = container;
                this.draggable.centerContainer = container;
                console.log("Загружен виджет-контейнер центральной секции");
            }
            else if (container.location == 'right') {
                this.draggable.sideContainer = this.sideContainer = container;
                console.log("Загружен виджет-контейнер правой секции");
            }
            else return;

            if (this.centerContainer != undefined && this.sideContainer != undefined)
                this.installContainerSorting();

            connect.connect(this, "onModeChanged", this, function() {
                container.onWebPageModeChanged();
            });

            connect.connect(container, "onAuthorizationRequired", lang.hitch(this, this.gotoLoginPage));
        },

        gotoLoginPage : function()
        {
            window.location.href = this.loginURL;
        },

        registerWidgetCatalog: function(catalog)
        {
            this.catalog = catalog;
            this.catalog.draggable = this.draggable;
            connect.connect(this.catalog, "onAddWidget", lang.hitch(this, function(widgetDefinition, container) {
                if (container != undefined)
                    container.addWidget(widgetDefinition);
            }));
            console.log("Загружен каталог виджетов");
        },

        installContainerSorting: function()
        {
            if (this.containerSortingInstalled)
                return;
            this.setClassListItem();
            var self = this;
            $("#sortable").sortable({
                handle: $("div.sortable-handle"),
                cursor: 'move',
                opacity: 0.6,
                scroll: false,
                start: function(event, ui)
                {
                    ui.placeholder.width(ui.helper.width());
                },
                stop: function(event, ui)
                {
                    if (!isIE(""))
                        return;
                    var mousePosition = self.centerContainer.draggable.mousePageXY(false);
                    var handle = ui.item.has("#workspace").get(0) != undefined ?
                        $("#workspace .sortable-handle") :
                        $("#right-section .sortable-handle");
                    var handlePosition = handle.offset();
                    if (!(mousePosition.x > handlePosition.left &&
                        mousePosition.x < handlePosition.left + handle.width() &&
                        mousePosition.y > handlePosition.top &&
                        mousePosition.y < handlePosition.top + handle.height()))
                        handle.parent().removeClass("show");
                },
                update: function()
                {
                    var center = self.centerContainer;
                    var sidebar = self.sideContainer;
                    if (center != undefined && sidebar != undefined) {
                        var centerLeft = domGeometry.position(center.domNode).x;
                        var sidebarLeft = domGeometry.position(sidebar.domNode).x;
                        sidebar.location = (sidebarLeft < centerLeft) ? 'left' : 'right';
                        if (sidebar.location == 'left')
                            domClass.toggle(sidebar.domNode, "compact");
                        self.setClassListItem();
                        sidebar.save();
                    }
                }
            });
            $("#sortable").sortable('disable');

            this.containerSortingInstalled = true;
        },

        setClassListItem: function()
        {
            if (this.centerContainer != undefined && this.sideContainer != undefined) {
                var workspace = query("div[id='workspace']")[0];
                var rightSection = query("div[id='right-section']")[0];
                domClass.toggle(rightSection, "first-item", this.sideContainer.location == 'left');
                domClass.toggle(workspace, "last-item", this.sideContainer.location == 'left');
                domClass.toggle(workspace, "first-item", this.sideContainer.location == 'right');
                domClass.toggle(rightSection, "last-item", this.sideContainer.location == 'right');
            }
        },
        
        setEditMode: function(isEditMode)
        {
            // если режим не измненился ничего не делаем
            if (this.editMode == isEditMode)
                return;

            this.editMode = isEditMode;

            // в зависимости от режима отключаем/подключаем  Sortable для контейнеров и убираем/добавляем подсветку
            this.updateContainerSortable();

            this.onModeChanged();
        },

        updateContainerSortable: function()
        {
            if (this.editMode)
            {
                $('.sortable-handle').hover(
                    function()
                    {
                        $(this).parent().addClass("show");
                    },
                    function()
                    {
                        $(this).parent().removeClass("show");
                    }
                );
                $("#sortable").sortable('enable');
            }
            else
            {
                $('.sortable-handle').unbind('mouseenter mouseleave');
                $("#sortable").sortable('disable');
            }
        },

        createInput: function(form, name, value)
        {
            dojo.create("input",
            {
                type: "hidden",
                name: name,
                value: value
            }, form, "last");
        },

        createFormReload: function()
        {
            var result = new dojo.create("form",
            {
                action: window.location.href,
                method: "POST"
            });
            this.createInput(result, "skinId", this.skinSelector.currentSkinId);
            this.createInput(result, "sideMenuLocation", this.sideContainer.location);
            this.createInput(result, "PAGE_TOKEN", $('input[name = PAGE_TOKEN]').val());
            this.createInput(result, "operation", "save");
            return result;
        },
        //сохранение всей страницы с виджетами с предварительной проверкой: применены ли все изменения в виджетах
        save: function()
        {
            showOrHideAjaxPreloader(true);
            var notSavedWidgets = this.findChangedWidgets(this.centerContainer) + this.findChangedWidgets(this.sideContainer);
            if (notSavedWidgets.length > 0)
            {
                $("#notSavedChangedWidgetsWin .notSavedWidgetNames").text(notSavedWidgets);
                win.open('notSavedChangedWidgets');
                showOrHideAjaxPreloader(false);
                return;
            }

            var skinId;
            //для сохранения скина определям текущую выбранную панель и проверям, что скин изменился
            if (domAttr.get(this.tabContainer.getSelectedPane().domNode, "id") == "skinSelector")
            {
                // создаем форму с полями расположения бокового меню и скином и сабмитим ее
                this.createFormReload().submit();
                return;
            }

            xhr.post(
            {
                url : this.url,
                content: {
                    operation : "save",
                    skinId: skinId,
                    sideMenuLocation: this.sideContainer.location,
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function(/*response*/)
                {
                    console.log("Виджеты сохраненны");
                    this.setEditMode(false);
                    topic.publish("hideSlidebar");
                    showOrHideAjaxPreloader(false);
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    console.error("Сбой при сохранении виджетов");
                    showOrHideAjaxPreloader(false);
                    if (ioargs.xhr.status == 401)
                        this.gotoLoginPage();
                 })
            });
        },
        
        cancel: function()
        {
            showOrHideAjaxPreloader(true);
            xhr.post(
            {
                url : this.url,
                content: {
                    operation : "cancel",
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function()
                {
                    console.log("Изменения отменены.");
                    location.reload();
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    showOrHideAjaxPreloader(false);
                    console.error("Сбой при отмене изменений");
                    if (ioargs.xhr.status == 401)
                        this.gotoLoginPage();
                 })
            });
        },

        reset: function()
        {
            showOrHideAjaxPreloader(true);
            xhr.post(
            {
                url : this.url,
                content: {
                    operation : "reset",
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function(/*response*/)
                {
                    console.log("Настройки по умолчанию восстановлены.");
                    location.reload();
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    showOrHideAjaxPreloader(false);
                    console.error("Сбой при сбросе настроек");
                    if (ioargs.xhr.status == 401)
                        this.gotoLoginPage();
                 })
            });
        },

        /* Поиск непримененных виджетов */
        findChangedWidgets: function(container)
        {
            var result = "";
            if (container == undefined)
                return result;

            var windows = container.getChildren();
            for (var i = 0; i < windows.length; i++)
            {
                if(windows[i].isWidgetChanged())
                {
                    if (result.length > 0)
                        result += ", " + windows[i].widget.settings['title'];
                    else
                        result = windows[i].widget.settings['title'];
                }
            }
            return result;
        },

        /**
         * Возвращает количество виджетов на странице по codename из дефиниции
         * @param definitionCodename - codename из дефиниции
         */
        getWidgetCount: function(definitionCodename)
        {
            var result = 0;
            if (this.centerContainer != undefined)
                result += this.centerContainer.getWidgetCount(definitionCodename);
            if (this.sideContainer != undefined)
                result += this.sideContainer.getWidgetCount(definitionCodename);
            return result;
        },
        ///////////////////////////////////////////////////////////////////////
        // События веб-страницы

        onModeChanged : function()
        {
        }
    });
});
