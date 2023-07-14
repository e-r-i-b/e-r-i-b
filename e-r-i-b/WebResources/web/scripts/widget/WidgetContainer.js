define("widget/WidgetContainer", [
    "dojo/_base/array",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/NodeList-traverse",
    "dijit/layout/_LayoutWidget",
    "widget/widgetDraggable",
    "dojo/_base/lang",
    "dojo/_base/xhr",
    "dojo/_base/json",
    "dojo/dom-class",
    "dojo/dom-attr",
    "widget/WidgetWindow",
    "widget/ViceWidget",
    "widget/_UIMixin"
], function(array, declare, query, nodeListTraverse, LayoutWidgetBase, WidgetDraggable, lang, xhr, json, domClass, domAttr, WidgetWindow, ViceWidget, UIMixin)
{
    /**
     * Контейнер виджетов
     */
    return declare("widget.WidgetContainer", [ LayoutWidgetBase, UIMixin ],
    {
        /**
         * Кодовое имя контейнера
         * (main, sidemenu и т.п.)
         * [readonly]
         */
        codename: "",

        /**
         * Положение на странице
         * left/center/right
         * [readonly]
         */
        location: "",

        /**
         * Адрес экшена, отвечающего за работу с сохранением параметров раскладки
         * Передаётся с сервера во время загрузки контейнера
         * [readonly]
         */
        url: undefined,

        /**
         * Идентификатор логина
         * Используется для генерации кодфикаторов новых виджетов
         * Передаётся с сервера во время загрузки контейнера
         * [readonly]
         */
        loginId: undefined,

        /**
         * Диджит веб-страницы
         */
        webPage : undefined,

        /**
         * Выставляется в WebPage
         */
        draggable: undefined,

        widgets: undefined,

        /**
         * Вице-виджет - ВрИО виджета пока последний не загружен
         */
        viceWidgetPrototype: undefined,

        /**
         * Максимальное количество одинаковых виджетов на странице
         */
        MAX_IDENT_WIDGETS: 5,

        isCenter: function()
        {
            return this.location == 'center';
        },

        startup: function()
        {
            this.webPage = this.getParent();
            this.webPage.registerWidgetContainer(this);
            this.draggable = this.webPage.draggable;

            this.inherited(arguments);

            // 1. Прототип для вице-виджета
            this.viceWidgetPrototype = query(".widget.ViceWidget", this.domNode)[0];

            // 2. Грузим виджеты
            array.forEach(this.getWidgetWindows(), lang.hitch(this, function(window) {
                this.loadWidget(window);
            }));

            this.updateWidgetDraggable();
            this.draggable.removeAndLayout(this);
        },

        /**
         * Сохранение раскладки
         */
        save: function()
        {
            this.draggable.deleteDiv("placeholder", this.widgets);
            xhr.post(
            {
                url : this.url,
                content: {
                    operation : "save",
                    layout : json.toJson(this.widgets),
                    location : this.location,
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function(/*response*/)
                {
                    console.log("Раскладка сохранена");
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    console.error("Сбой при сохранении раскладки");
                    if (ioargs.xhr.status == 401)
                        this.onAuthorizationRequired();
                })
            });
        },

        isMainPage: function()
        {
            return !$.isEmptyObject($("div[codename='main']").get(0));
        },

        getWebPageMode : function()
        {
            return this.webPage.editMode ? 'edit' : 'view';
        },

        /**
         * Возвращает окна виджетов контейнера
         */
        getWidgetWindows : function()
        {
            return this.getChildren();
        },

        ///////////////////////////////////////////////////////////////////////
        // Добавление нового виджета

        createViceWidget: function(widgetWindow)
        {
            var viceWidget = new ViceWidget({
                codename : widgetWindow.codename,
                settings : { title : widgetWindow.title, size : widgetWindow.size },
                sizeable : widgetWindow.sizeable
            });
            if (widgetWindow.widget != undefined)
                viceWidget.settings = lang.clone(widgetWindow.widget.settings);
            viceWidget.domNode = lang.clone(this.viceWidgetPrototype);
            return viceWidget;
        },

        addWidget: function(widgetDefinition, settings, widgetWindowErr)
        {
            if (widgetWindowErr)
            {
                widgetWindowErr.destroyRecursive(false);
                this.draggable.widgetRemove(widgetWindowErr.codename, this);
            }

            if (!this.validWidgetCount(widgetDefinition.definition))
                return;

            var codename = this.generateWidgetCodename(widgetDefinition);
            var settingsAsJson = "";
            if (settings != undefined)
                settingsAsJson = json.toJson(settings);

            // 1. Создаём виджет-окно
            var widgetWindow = new WidgetWindow({
                codename : codename,
                title : widgetDefinition.username,
                size : widgetDefinition.size,
                sizeable : widgetDefinition.sizeable,
                url : widgetDefinition.url,
                definition: widgetDefinition.definition
            });
            widgetWindow.placeAt(this.domNode, "first");
            widgetWindow.startup();

            // 2. Создаём вице-виджет на время загрузки виджета
            var viceWidget = this.createViceWidget(widgetWindow);
            widgetWindow.set('content', viceWidget);
            widgetWindow.widget = viceWidget;
            viceWidget.startup();

            // 3. Уведомляем механизм перемещения о добавлении виджета
            this.draggable.addWidget(codename, this);
            this.draggable.moved(this);
            
            // 4. Отправляем на сервер ajax-запрос на добавление нового виджета
            widgetWindow.loading = true;
            xhr.post(
            {
                url: widgetDefinition.url,
                content: {
                    operation: 'add',
                    page: this.codename,
                    codename: codename,
                    defname: widgetDefinition.codename,
                    settings: settingsAsJson,
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function(response)
                {
                    widgetWindow.set('content', response);
                    widgetWindow.widget = widgetWindow.getChildren()[0];
                    if (!this.isCenter())
                        widgetWindow.widget.setupCompactMode();
                    this.draggable.moved(this);
                    console.log("Виджет " + codename + " добавлен");
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    // показываем виджет с ошибкой
                    console.error("Не удалось добавить виджет " + widgetDefinition.codename);

                    var updateFunction = lang.hitch(this, function() {
                        this.addWidget(widgetDefinition,settings,widgetWindow);
                    });
                    this.showNotAbaliableState(widgetWindow, updateFunction);

                    if (ioargs.xhr.status == 401)
                        this.onAuthorizationRequired();
                }),

                handle : lang.hitch(this, function()
                {
                    widgetWindow.loading = false;
                    if (widgetWindow.broken)
                        this.discardWidget(widgetWindow);
                })
            });
        },

        /**
         * Возвращает количество виджетов в контейнере
         * @param definitionCodename - codename из дефиниции
         */
        getWidgetCount: function(definitionCodename)
        {
            var result = 0;
            array.forEach(this.getWidgetWindows(), lang.hitch(this, function(window) {
                if (window.definition.codename == definitionCodename)
                    result += 1;
            }));
            return result;
        },

        /**
         * Возвращает true, если количество виджетов на странице не больше максимального, false - в другом случае
         * @param definition - дефиниция виджета
         */
        validWidgetCount: function(definition)
        {
            var count = this.webPage.getWidgetCount(definition.codename);
            if (count + 1 > definition.maxCount)
            {
                this.showMessageMaxCount(definition.maxCountMessage);
                return false;
            }
            return true;
        },

        generateWidgetCodename : function(widgetDefinition)
        {
            // При изменении алогритма см. WidgetService.generateWidgetCodename()
            return widgetDefinition.codename + this.loginId + '_' + (new Date()).getTime();
        },

        ///////////////////////////////////////////////////////////////////////
        // Загрузка виджета

        loadWidget: function(widgetWindow, afterError)
        {
            if (widgetWindow.loadMode == "async" || afterError)
            {
                // Создаём вице-виджет на время загрузки виджета
                var viceWidget = this.createViceWidget(widgetWindow);
                widgetWindow.set('content', viceWidget);
                widgetWindow.widget = viceWidget;
                viceWidget.startup();

                widgetWindow.loading = true;
                xhr.post(
                {
                    url : widgetWindow.url,
                    content: {
                        page: this.codename,
                        codename : widgetWindow.codename,
                        PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                    },

                    load : lang.hitch(this, function(response)
                    {
                        widgetWindow.set('content', response);
                        widgetWindow.widget = widgetWindow.getChildren()[0];
                        console.log("Виджет " + widgetWindow.codename + " загружен асинхронно");
                        this.draggable.moved(this);
                    }),

                    error : lang.hitch(this, function(response, ioargs)
                    {
                        console.error("Сбой при загрузке виджета " + widgetWindow.codename);

                        var updateFunction = lang.hitch(this, function() {
                            this.loadWidget(widgetWindow, true);
                        });
                        this.showNotAbaliableState(widgetWindow, updateFunction);

                        if (ioargs.xhr.status == 401)
                            this.onAuthorizationRequired();
                    }),

                    handle : lang.hitch(this, function()
                    {
                        widgetWindow.loading = false;
                        if (widgetWindow.broken)
                            this.discardWidget(widgetWindow);
                    })
                });
            }
            else if (widgetWindow.loadMode == "sync")
            {
                // произошла ошибка во время загрузки
                if (widgetWindow.broken)
                    this.discardWidget(widgetWindow);
                else widgetWindow.widget = widgetWindow.getChildren()[0];
                this.draggable.moved(this);
                console.log("Виджет " + widgetWindow.codename + " загружен синхронно");
            }

        },

        ///////////////////////////////////////////////////////////////////////
        // Сохранение виджета

        /**
         * Сохраняет виджет ajax-запросом на сервер
         * @param widgetWindow
         * @param settings - настройки виджета или undefined, если не поменялись
         */
        saveWidget: function(widgetWindow, settings)
        {
            var settingsAsJson = "";
            if (settings != undefined)
                settingsAsJson = json.toJson(settings);

            // Создаём вице-виджет на время сохранения виджета
            var oldWidget = widgetWindow.widget;
            widgetWindow.removeChild(oldWidget);

            var viceWidget = this.createViceWidget(widgetWindow);
            widgetWindow.set('content', viceWidget);
            widgetWindow.widget = viceWidget;
            viceWidget.startup();

            widgetWindow.loading = true;
            xhr.post(
            {
                url : widgetWindow.url,
                content: {
                    operation : "save",
                    page : this.codename,
                    codename : widgetWindow.codename,
                    settings : settingsAsJson,
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function(response /*, ioargs*/)
                {
                    if (trim(response) != "")
                    {
                        widgetWindow.set('content', response);
                        widgetWindow.widget = widgetWindow.getChildren()[0];
                        oldWidget.destroyRecursive(false);
                        widgetWindow.widget.onSaved();
                        console.log("Виджет " + widgetWindow.codename + " сохранён и перезагружен");
                        this.draggable.moved(this);
                    }
                    else
                    {
                        widgetWindow.set('content', oldWidget);
                        widgetWindow.widget = oldWidget;
                        widgetWindow.widget.onSaved();
                        console.log("Виджет " + widgetWindow.codename + " сохранён");
                    }
                    viceWidget.destroyRecursive(false);
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    console.error("Сбой при сохранении виджета " + widgetWindow.codename);
                    widgetWindow.widget = oldWidget;

                    var updateFunction = lang.hitch(this, function() {
                        this.saveWidget(widgetWindow, settings);
                    });
                    this.showNotAbaliableState(widgetWindow, updateFunction);

                    if (ioargs.xhr.status == 401)
                        this.onAuthorizationRequired();
                }),

                handle : lang.hitch(this, function()
                {
                    widgetWindow.loading = false;
                    if (widgetWindow.broken)
                        this.discardWidget(widgetWindow);
                })
            });
        },

        showNotAbaliableState: function(widgetWindow, updateFunction)
        {
            widgetWindow.widget.notAvailableMode = true;
            $("#"+widgetWindow.id+" .ajaxLoader").hide();
            $("#"+widgetWindow.id+" .notAvaliableWidget").show();
            $("#"+widgetWindow.id+" .buttonGreen").bind("click", updateFunction);
        },

        onAuthorizationRequired: function()
        {
            // метод переопределяется в WebPage
        },

        ///////////////////////////////////////////////////////////////////////
        // Удаление виджета

        /**
         * Закрывает и удаляет виджет
         */
        closeWidget: function(widgetWindow)
        {
            var codename = widgetWindow.codename;

            widgetWindow.loading = true;
            xhr.post(
            {
                url : widgetWindow.url,
                content: {
                    operation : "remove",
                    codename : codename,
                    PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val()
                },

                load : lang.hitch(this, function(/*response*/)
                {
                    // 1. Удаляем окно виджета
                    widgetWindow.destroyRecursive(false);

                    this.draggable.widgetRemove(codename, this);

                    console.log("Виджет " + codename + " удалён");
                }),

                error : lang.hitch(this, function(response, ioargs)
                {
                    console.error("Сбой при удалении виджета " + codename);
                    if (ioargs.xhr.status == 401)
                        this.onAuthorizationRequired();
                }),

                handle : lang.hitch(this, function()
                {
                    widgetWindow.loading = false;
                    if (widgetWindow.broken)
                        this.discardWidget(widgetWindow);
                })
            });
        },

        /**
         * Вызывается на изменении размера виджета (wide <-> compact)
         * @param codename
         */
        onWidgetResized: function(codename, newSize)
        {
            this.draggable.widgetResize(this, codename, newSize);
        },
        /**
         * Вызывается, когда необходимо подключить/отключить функциональность draggable
         */
        updateWidgetDraggable: function(){
            var widgetDomNodes = query(this.draggable.widgetsToString(this), this.domNode);
            if (this.getWebPageMode() == 'edit')
                $(widgetDomNodes).draggable('enable');
            else
                $(widgetDomNodes).draggable('disable');
        },

        discardWidget: function(widgetWindow)
        {
            if (widgetWindow.loading)
            {
                widgetWindow.broken = true;
            }

            else
            {
                var widget = widgetWindow.widget;
                if (widget == undefined || !widget.isViceWidget())
                {
                    if (widget != undefined)
                        widgetWindow.removeChild(widget);

                    var viceWidget = this.createViceWidget(widgetWindow);
                    widgetWindow.set('content', viceWidget);
                    widgetWindow.widget = viceWidget;
                    viceWidget.startup();

                    if (widget != undefined)
                        widget.destroyRecursive(false);

                    this.showNotAbaliableState(widgetWindow, lang.hitch(this, function() {
                        this.loadWidget(widgetWindow, true);
                    }));
                }
                
                widgetWindow.broken = false;
            }
        },

        ///////////////////////////////////////////////////////////////////////
        // События виджет-контейнера

        /**
         * Изменился режим страницы (просмотр <-> редактирование)
         */
        onWebPageModeChanged : function()
        {
            // в зависимости от режима отключаем/подключаем  draggable для виджетов
            this.updateWidgetDraggable();

            array.forEach(this.getWidgetWindows(), function(window) {
                window.onWebPageModeChanged();
            });
        }
    });
});
