define("widget/_WidgetBase", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "widget/log",
    "dojo/query",
    "dojo/on",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/_base/event",
    "dijit/_WidgetBase",
    "dijit/_Contained",
    "widget/_UIMixin",
    "widget/_ScrollableMixin",
    "dojo/dom-geometry"
], function(lang, declare, log, query, on, domClass, domStyle, event, WidgetBase, Contained, UIMixin, ScrollableMixin, domGeom)
{
    /**
     * Базовый класс виджета
     */
    return declare("widget._WidgetBase", [ WidgetBase, Contained, UIMixin, ScrollableMixin ],
    {
        ///////////////////////////////////////////////////////////////////////
        // Поля виджета

        /**
         * Кодовое наименование виджета
         * [readonly]
         */
        codename: undefined,

        /**
         * Режим работы виджета: "view" - просмотр, "edit" - настройка
         */
        mode: "view",

        /**
         * Флажок "виджет может менять размер от компактного к полному и обратно"
         * [readonly]
         */
        sizeable: undefined,

        /**
         * Текущие настройки виджета
         */
        settings: undefined,

        /**
         * Копия настроек для режима редактирования
         */
        editSettings: undefined,

        /**
         * Умолчательные настройки виджета
         */
        defaultSettings: function()
        {
            return { rollUp: false };
        },

        /**
         * Путь к директории ресурсов
         * Напр., /PhizIC-res/8/
         * Задаётся в jsp
         * [readonly]
         */
         resourcesRealPath: undefined,
        
        ///////////////////////////////////////////////////////////////////////
        // Кнопки и граф.компоненты виджета

        /**
         * Окно с виджетом
         */
        window: undefined,

        /**
         * Рабочая область виджета
         * Включает панели отобажения и редактирования
         * Не включает шапку
         */
        clientPane: undefined,

        /**
         * Шапка
         * Включает кнопки управления
         */
        panelTop: undefined,

        /**
         * Панелька заголовка в шапка
         * Не включает кнопки управления
         */
        panelTitle: undefined,

        /**
         * Панель ссылок в шапке
         */
        panelControl: undefined,

        /**
         * Панелька для режима отображения
         */
        panelView: undefined,

        /**
         * Панелька для режима настройки
         */
        panelEdit: undefined,

        buttonSize: undefined,

        buttonEdit: undefined,

        buttonClose: undefined,

        buttonRoll: undefined,

        ///////////////////////////////////////////////////////////////////////
        // Атрибуты виджета

        isViceWidget : function()
        {
            return false;
        },

        ///////////////////////////////////////////////////////////////////////
        // Операции над виджетом

        /**
         * Инициализировать виджет.
         * Операция используется только во внешних по отношению к виджету компонентах
         * (окно, контейнер)
         */
        startup: function()
        {
            this.inherited(arguments);

            this.window = this.getParent();

            try
            {
                this.settings = lang.mixin(this.defaultSettings(), this.settings);
                this.editSettings = lang.clone(this.settings);

                this.onStartup();

                if (!this.validate())
                    this.mode = 'edit';

                this.refresh();
            }
            catch (e)
            {
                log.error("Сбой на загрузке виджета " + this.codename, e);
                this.discard();
            }
        },

        /**
         * Перерисовать виджет.
         */
        refresh: function()
        {
            try
            {
                this.onRefresh();
            }
            catch (e)
            {
                log.error("Сбой на перерисовке виджета " + this.codename, e);
                this.discard();
            }
        },

        /**
         * Обновить виджет значениями из контролов.
         */
        update: function()
        {
            try
            {
                this.onUpdate();
            }
            catch (e)
            {
                log.error("Сбой на обновлении виджета значениями из контролов " + this.codename, e);
                this.discard();
            }
        },

        /**
         * Провалидировать виджет.
         * @return true, если валидация прошла успешно
         */
        validate: function()
        {
            try
            {
                return this.onValidate();
            }
            catch (e)
            {
                log.error("Сбой на валидации виджета " + this.codename, e);
                this.discard();
                return false;
            }
        },

        /**
         * Сохранить настройки виджета.
         */
        save: function()
        {
            var oldSize = this.settings.size;
            var newSize = this.editSettings.size;
            this.settings = lang.clone(this.editSettings);
            this.window.saveWidget(this.editSettings);
            if (oldSize != newSize) {
                this.window.size = newSize;
                this.window.container.onWidgetResized(this.codename, newSize);
            }
            // Дальше делать с виджетом ничего нельзя, т.к. сохранение может за собой повлечь его полную перезагрузку
        },

        /**
         * Сбросить текущие настройки виджета в сохранённые.
         * Для обновления окна затем нужно вызвать refresh()
         */
        reset: function()
        {
            delete this.editSettings;
            this.editSettings = lang.clone(this.settings);
        },

        /**
         * Удалить виджет.
         */
        close: function()
        {
            this.window.closeWidget(this);
        },

        /**
         * Забраковать виджет
         */
        discard : function()
        {
            this.window.discardWidget();
        },
        
        getTitle: function()
        {
            return trim(this.settings.title);
        },

        getControl: function()
        {
            return trim(this.settings.control);
        },
        canResize: function()
        {
            if (this.buttonSize == undefined)
                return false;
            return "visible" == domStyle.get(this.buttonSize, "visibility");
        },

        stopBlinking: function()
        {
            if (this.settings.blinking) {
                var settings = lang.clone(this.settings);
                settings.blinking = false;
                this.window.saveWidget(settings);
                this.settings.blinking = false;
                this.editSettings.blinking = false;
                $(this.domNode).fadeTo("fast", 1);
            }
        },

        ///////////////////////////////////////////////////////////////////////
        // События виджета

        /**
         * Вызывается при первичной инициализации виджета.
         */
        onStartup: function()
        {
            this.panelTitle = this.findPanel("title");
            this.clientPane = query(".clientPane", this.domNode)[0];
            this.panelTop = query(".r-top", this.domNode)[0];

            // 1. Подключаем панели отображения и редактирования
            this.panelView = this.findPanel("view");
            this.panelEdit = this.findPanel("edit");

            this.panelControl = this.findPanel("linksControl");

            // (2) Подключаем кнопку размера виджета
            this.buttonSize = this.findButton("size");
            if (this.buttonSize != undefined)
                on(this.buttonSize, "click", lang.hitch(this, this.onSize));

            // (3) Подключаем кнопку "Настройка"
            this.buttonEdit = this.findButton("edit");
            if (this.buttonEdit != undefined)
                on(this.buttonEdit, "click", lang.hitch(this, this.onEdit));

            // 4. Подключаем кнопку "Закрыть"
            this.buttonClose = this.findButton("close");
            on(this.buttonClose, "click", lang.hitch(this, this.onClose));

            // 5. Подключаем кнопку "Свернуть"
            this.buttonRoll = this.findButton("roll");
            on(this.buttonRoll, "click", lang.hitch(this, this.onRoll));

            // 6. Подключаем обработчик события "Выключить мигалку"
            on(this.domNode, "click", lang.hitch(this, this.stopBlinking));

            // 7. Инициализируем подсистему кастомных селектов
            selectCore.init(this.domNode);
        },

        /**
         * Возвращает режим веб-страницы
         * @return 'view' / 'edit'
         */
        getWebPageMode : function()
        {
            return this.window.getWebPageMode();
        },

        /**
         * Вызывается, когда нужно перерисовать виджет
         */
        onRefresh: function()
        {

            // 1. Атрибуты окна
            domClass.toggle(this.window.domNode, "compact", this.settings.size == "compact");

            if (this.mode == "edit")
            {
                // высота виджета в режиме просмотра:
                var widgetViewHeight = domGeom.position(this.domNode, true).h;
            }
            // 2. Просмотр/редактирование виджета
            domStyle.set(this.panelView, "display", (this.mode == "view") ? "block" : "none");
            domStyle.set(this.panelEdit, "display", (this.mode == "edit") ? "block" : "none");
            if (this.mode == "edit" && this._findSingle(".viewport", this.domNode) != undefined)
            {
                var height = this.getHeightScrollableData(widgetViewHeight);
                var viewport = query(".viewport", this.domNode);
                viewport.style("height", height + "px");
                //вызвать плагин для скроллинга данных
                tinyscrollbar(query(".scroll-edit-widget", this.domNode)[0],
                {
                    axis: "y",
                    size: height - this.arrowsHeight(),
                    leftOrUpArrow: $(query(".arrow-up", this.domNode)[0]),
                    rightOrDownArrow: $(query(".arrow-down", this.domNode)[0])
                });
            }

            if (this.buttonEdit != undefined) {
                domClass.toggle(this.buttonEdit, "widgetButtonSettings", this.mode != "edit");
                domClass.toggle(this.buttonEdit, "widgetActiveButtonSettings", this.mode == "edit");
            }

            // 3. Размер виджета
            domClass.toggle(this.domNode, "compact", this.settings.size == "compact");

            // 4. Свёрнутость виджета
            var rollUp = this.settings.rollUp;
            domClass.toggle(this.domNode, "rollUp", rollUp);
            domClass.toggle(this.buttonRoll, "triangularArrowUp", !rollUp);
            domClass.toggle(this.buttonRoll, "triangularArrowDown", rollUp);
            domStyle.set(this.clientPane, "display", !rollUp ? "block" : "none");

            // 5. Название виджета в шапке
            var title = trim(this.getTitle());
            if (title != "")
                this.setLabelText("title", this.panelTitle, title);

            var control = trim(this.getControl());
            if (control != "")
                this.setLabelText("control", this.panelControl, control);

            // 6. Кнопка размера виджета в шапке
            if (this.buttonSize != undefined) {
                var visible = true;
                if (this.sizeable != undefined)
                    visible = visible && this.sizeable;
                visible = visible && (this.window.container.location == "center");
                domStyle.set(this.buttonSize, "visibility", visible ? "visible" : "hidden");
            }

            if (this.getWebPageMode() == 'edit'){
                $(this.panelControl).hide();
                $(this.buttonRoll).show();
                $(this.buttonClose).show();
                $(this.buttonEdit).show();
                $(this.buttonSize).show();
            }
            else
            {
                $(this.buttonRoll).hide();
                $(this.buttonClose).hide();
                $(this.buttonEdit).hide();
                $(this.buttonSize).hide();
                $(this.panelControl).show();
            }

            this.blink();
        },
        /**
         * Расчет высоты скроллируемых данных
         * @param widgetViewHeight - высота виджета в режиме просмотра
         */
        getHeightScrollableData: function(widgetViewHeight)
        {
            return this.defaultHeight(widgetViewHeight);
        },

        blink: function()
        {
            if (!this.settings.blinking)
                return;

            var self = this;
            $(this.panelTop).fadeTo('slow', 0.7, function()
            {
                $(this).fadeTo('normal', 0.99, function()
                {
                    self.blink();
                });
            });
        },
        
        /**
         * Вызывается при обновлении (настроек) виджета
         * значениями из полей панели настроек
         */
        onUpdate: function()
        {
            // заглушка
        },

        /**
         * Вызывается при валидации (настроек) виджета
         * @return true, если валидация прошла успешно
         */
        onValidate: function()
        {
            // заглушка
            return true;
        },

        onSize: function(evt)
        {
            this.settings.blinking = false;

            var size = this.settings.size;
            size = (size == "compact") ? "wide" : "compact";
            var settings = lang.clone(this.settings);
            settings.size = size;
            this.window.saveWidget(settings);
            this.settings.size = size;
            this.editSettings.size = size;
            this.window.size = size;
            this.window.container.onWidgetResized(this.codename, size);

            this.refresh();

            event.stop(evt);
        },

        onEdit: function(evt)
        {
            this.settings.blinking = false;

            if (this.mode == "view") {
                this.settings.rollUp = false;
                this.mode = "edit";
            }
            else
            {
                this.reset();
                if (this.validate())
                    this.mode = "view";
            }
            this.refresh();

            event.stop(evt);
        },

        onRoll: function(evt)
        {
            this.settings.blinking = false;

            var rollUp = this.settings.rollUp;
            rollUp = !rollUp;
            var settings = lang.clone(this.settings);
            settings.rollUp = rollUp;
            if (!this.forbiddenMode)
                this.window.saveWidget(settings);
            this.settings.rollUp = rollUp;
            this.editSettings.rollUp = rollUp;
            this.refresh();

            event.stop(evt);
        },

        onSaved: function()
        {
            this.update();
            this.refresh();
        },

        onClose: function(evt)
        {
            this.close();
            event.stop(evt);
        },

        /**
         * Перевод виджета в компактный режим
         * Изменяет настройки виджета и делает refresh
         * Необходимо для перетаскивания виджета из каталога в боковое меню
         */
        setupCompactMode: function()
        {
            this.settings.size = "compact";
            this.editSettings.size = "compact";
            this.window.size = "compact";
            this.refresh();
        },

        /**
         * Данное событие возникает перед началом сортировки (движения) виджета.
         */
        onStartMove: function()
        {
            
        },

        /**
         * Данное событие возникает перед окончанием сортировки виджета.
         */
        onStopMove: function()
        {
            
        }
    });
});
