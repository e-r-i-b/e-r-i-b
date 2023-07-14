$.fx.prototype.cur = function()
{
    if ( this.elem[this.prop] != null && (!this.elem.style || this.elem.style[this.prop] == null) )
    {
        return this.elem[ this.prop ];
    }

    var r = parseFloat( jQuery.css( this.elem, this.prop ) );
    return typeof r == 'undefined' ? 0 : r;
};

/**
 * Настройки для автодополнения по-умолчанию
 * @constructor
 */
function DEFAULT_AUTO_COMPLETE_OPTIONS()
{
    /*
     * URL источника данных
     */
    this.url = null;

    /*
     * Строка содержит дополнительные параметры запроса
     */
    this.extraParams = '';

    /*
     * CSS класс поля для ввода запроса
     */
    this.inputClass = '';

    /*
     * Разделитель строк данных пришедших с сервера. Не используется если url не установлен
     */
    this.lineSeparator = '@';

    /*
     * Разделитель ячеек. Не используется если url не установлен
     */
    this.cellSeparator = '|';

    /*
     * Задержка времени отправки запроса на сервер. Запрос отправляется на сервер с момента когда пользователь
     * закончил ввод в поле + время указанное в переменной delay
     */
    this.delay = 0;

    /*
     * Минимальное число симолов после которого начинается поиск
     */
    this.minChars = 3;

    /*
     * CSS класс списка результатов
     */
    this.resultsClass = 'searchBlockList';

    /*
     * Хранилище локальных данных(оно же хранит данные получаемые с сервера)
     */
    this.data = new LiveSearchInputData();

    /*
     * Метод должен устанавливать размеры элемента внитри которого отображаются данные
     */
    this.customAutoSize = function() {};

    /**
     * Метод отслеживает перемещение курсора и скроллирует список при необходимости
     */
    this.moveScroll = function() {};

    /*
     * Здесь описан для дальнейшего переопределения
     * Данный метод срабатывает после того как отработает parseData определенный в autocomplete.js
     */
    this.parseData = function(data, query) {};

    /*
     * Здесь описан для дальнейшего переопределения.
     * Данный метод вызывается внутри autocomplete.js и должен возвращать html-элемент который затем будет
     * отображаться в списке результатов поиска.
     */
    this.formatItem = function(row, i, num) {};

    /*
     * Здесь описан для дальнейшего переопределения, поскольку кэш определенный в autocomplete.js в том виде
     * какой он там есть практически не используется.
     */
    this.addToCache = function(query, data) {};

    /*
     * Здесь описан для дальнейшего переопределения.
     * Вызывается при выборе какого-либо элемента из списка вариантов
     */
    this.onItemSelect = function(element)     {};

    /*
     * Здесь описан для дальнейшего переопределения.
     * Должен возвращать данные из кэша.
     */
    this.loadFromCache = function(query) {};

    /*
     * Данный метод определяет куда будет добавлен html элемент служащий для отображения результатов
     * поиска.
     */
    this.append2CustomPlace = function(element) {};

    /*
     * Нижеследующие параметры не изменять, если нет понимания что они делают.
     */
    this.cacheLength   = 1;
    this.matchContains = 1;
    this.matchSubset   = true;
    this.greyStyle     = false;
    this.selectFirst   = false;
    this.operaIgnore   = true;
    this.loadingClass  = null;
}

/**
 * @constructor
 */
function QueryCache()
{
    var query = '';

    return {
        contain : function (q)
        {
            if (!query)
            {
                query = q;
                return false;
            }

            if (query.length > q.length)
            {
                query = q;
                return false;
            }
            else
            {
                if (q.indexOf(query) > -1)
                {
                    return true;
                }
                else
                {
                    query = q;
                    return false;
                }
            }
        },

        clear : function ()
        {
            query = '';
        }
    }
}

/**
 * @constructor
 */
function LiveSearchInputData() {}

/**
 *
 */
LiveSearchInputData.prototype =
{
    constructor: LiveSearchInputData,
    data: [],
    options:
    {
        keyField       : 'code',
        valueField     : 'value',
        formattedField : 'formatted',
        keysField      : 'codes',
        fieldDelimiter : ':'
    },

    /**
     * Возвращает все данные.
     *
     * @returns {Array}
     */
    getAll : function()
    {
        return this.data;
    },

    /**
     * Ищет по значению ключевого поля элемент данных. В качестве ключевого поля выступает поле с именем
     * указанным в options.keyField
     *
     * @param   key ключ искомого элемента
     * @returns {Object} в случае если элемент не найден возвращает null
     */
    findDataByKey : function (key)
    {
        for (var i=0; i<this.data.length; i++)
        {
            if (this.data[i][this.options.keyField] == key)
            {
                return this.data[i];
            }
        }

        return null;
    },

    /**
     * Служит для добавления нового элемента данных
     *
     * @param {Object} value объект в формате { [options.keyField]: 'value', [options.valueField]: 'value', [someOther]: 'value', ...}
     */
    appendData : function(value)
    {
        this.data.push(value);
    },

    /**
     * Очищает данные
     */
    clear : function ()
    {
        this.data = [];
    }
};

/**
 * Данный класс описывает поле с живым поиском.
 *
 * Если подразумевается, что данные будут приходить с какого-либо сервера, то необходимо учитывать
 * что данная реализация ожидает их получить в следующем формате:
 *
 * [Имя поля записи 1]:значение|[Имя поля записи 1]:значение|...@[Имя поля записи 2]:значение|[Имя поля записи 2]:значение|...@...
 * Данная строка будет в массив объектов:
 * [{
 *    [Имя поля записи 1] : значение,
 *    [Имя поля записи 1] : значение,
 *    ...
 *  },
 *  {
 *    [Имя поля записи 2] : значение,
 *    [Имя поля записи 2] : значение,
 *    ...
 * }].
 *
 * @constructor
 * @see mixinObjects.js
 * @see jquery.autocomplete.js
 */
function LiveSearchInput()
{
    mixin(new ObjectUtils(),      this);
    mixin(new UIElementBinding(), this);

    this.requiredParameters = [];

    this.elements =
    {
        content                   : null,
        searchInput               : null,
        dragger_container         : null,
        searchSelectShadow        : null,
        liveSearchResultContainer : null,
        customScrollBox           : null
    };

    this.config =
    {
        z_index             : null,
        options             : new DEFAULT_AUTO_COMPLETE_OPTIONS(),
        maxHeight           : 187,
        autoCompleteEnabled : true
    };

    this.data        = this.config.options.data;
    this.dataOptions = this.data.options;
}

LiveSearchInput.prototype =
{
    constructor : LiveSearchInput,
    selected    : null,
    queryCache  : new QueryCache(),

    onComplete : function(value) {},

    onItemSelectListener : function(element)
    {
        if (element.hasChildNodes())
        {
            var childNode = element.childNodes[0];
            if (childNode)
            {
                this.selected = this.data.findDataByKey(childNode.id);

                if(this.selected)
                {
                    this.elements.searchInput.value = this.selected[this.dataOptions.valueField];
                    this.onComplete(this.selected);
                }
            }
        }
    },

    bindAutoComplete : function()
    {
        this.setConfig(null);

        if (this.config.autoCompleteEnabled && !this.hasOwnProperty('autoCompleteTied'))
        {
            this['autoCompleteTied'] = true;
            new jQuery.autocomplete(this.elements.searchInput, this.config.options);
        }

        return this;
    },

    formatItem : function(value, i, num)
    {
        var a = document.createElement('a');
        a.id        = value[this.dataOptions.keyField];
        a.innerHTML = value[this.dataOptions.formattedField];
        return a.outerHTML;
    },


    addToCache : function(query, source)
    {
        if (source)
        {
            for (var i=0; i<source.length; i++)
            {
                this.appendData(source[i]);
            }
        }
    },

    loadFromCache : function(query)
    {
        if (!query)
        {
            return null;
        }

        if (this.isRemote() && this.requestEnabled())
        {
            var cache = this.queryCache;

            // Если такой запрос уже был - ищем среди локальных данных
            if (cache.contain(query))
            {
                return this.search(query, this.data.getAll());
            }
            //Если такого запроса ещё не было - нужно делать запрос на сервер
            else
            {
                // Очищается локальное хранилище
                this.clearData();
                return null;
            }
        }
        // Если не предполагается ajax-запросов для получения данных то сразу топаем искать среди локальных данных
        else
        {
            return this.search(query, this.data.getAll());
        }
    },

    search : function(text, sources)
    {
        var found = [];
        if (!text || !sources || sources.length == 0)
        {
            return found;
        }

        /*
         * Делается из строки массив + сортируется по убиванию длины слов
         */
        var words = text.split(/\s+/).sort(function (current, next)
        {
            if (!current)
            {
                return 1;
            }

            if (!next)
            {
                return -1;
            }

            return current.length > next.length ? -1 : 1;
        });

        var test    = String();
        var replace = String();
        var group   = 1;

        for (var i=0; i<words.length; i++)
        {
            if (words[i])
            {
                words[i] = words[i].replace(new RegExp("\\(", 'g'), "\\(");
                words[i] = words[i].replace(new RegExp("\\)", 'g'), "\\)");
                if (i==0)
                {
                    test    += ('(' + words[i] + ')');
                    replace += ('<b>$' + group++);
                }
                else
                {
                    test    += ('|(' + words[i] + ')');
                    replace += ('$'  + group++);
                }

                if (i == (words.length - 1))
                {
                    replace += '</b>';
                }
            }
            else
            {
                replace += '</b>';
            }
        }

        if (test.length > 0)
        {
            var regexp = eval('/' + test + '/gi');
            for (var j=0; j<sources.length; j++)
            {
                var temp = {};
                {
                    var element = sources[j];
                    var match   = regexp.exec(element[this.dataOptions.valueField]);

                    if (match)
                    {
                        this.reWriteObject(temp, element, true);
                        temp[this.dataOptions.formattedField] = element[this.dataOptions.valueField].replace(regexp, replace);

                        found[found.length ++] = temp;
                    }
                }
            }
        }

        return found;
    },

    /**
     * @see UIElementBinding#bind2
     */
    onBind2 : function()
    {
        this.StringTrimSupport();
        this.bindAutoComplete();
        this.customAutoSize();
        return this;
    },

    parseData : function (data, query)
    {
        var values = [];
        for (var i=0; i<data.length; i++)
        {
            var source = data[i];
            var value  = {};

            for (var j=0; j<source.length; j++)
            {
                var pair = source[j].split(this.dataOptions.fieldDelimiter);
                value[pair[0]] = pair[1];
            }

            var temp = this.findByValueName(values, value.value);
            if (temp == null)
            {
                value['codes'] = value['code'];
                values.push(value);
            }
            else
            {
                temp['codes'] = temp['codes'] + "," + value['code'];
            }
        }

        return this.search(query, values);
    },

    findByValueName : function(values, valueName)
    {
        for (var i = 0; i < values.length; i++)
        {
            if (values[i].value == valueName)
                return values[i];
        }
        return null;
    },

    appendData : function(object)
    {
        if (object && object !== undefined && typeof object == 'object')
        {
            this.data.appendData(object);
        }

        return this;
    },

    clearData : function (force)
    {
        if (this.isRemote() || force)
        {
            this.data.clear();
        }
    },

    /**
     * Добавляет, заменяет, или удаляет параметр строки ajax запроса
     * @param name имя параметра
     * @param value значение параметра (если значение не определено, то параметр удаляется)
     */
    addParameter : function(name, value)
    {
        if (name && typeof name == 'string')
        {
            var params  = this.config.options.extraParams;
            // если параметры существуют
            if (params)
            {
                // преобразуем строку параметров в массив
                var list  = params.split('&');
                // признак того, что параметр уже существует
                var found = false;

                // цикл по массиву, где содержатся элементы вида - name=value
                for (var i=0; i<list.length; i++)
                {
                    // разбиваем элемент массива на подмассив, где первый элемент имя параметра, второй, его значение
                    var pair = list[i].split('=');
                    // если имя параметра равно искомому, тогда заменяем его значение на новое
                    if (pair[0] == name)
                    {
                        pair[1] = value;
                        found   = true;
                    }
                    // если значение параметра не определено, то удаляем параметр из списка параметров
                    if (!value && found)
                    {
                        list.splice(i, 1);
                        break;
                    }
                    // заменяеи модмассив на строку и записываем в массив параметров
                    list[i] = pair.join('=');
                }

                // если параметр не существует, тогда добавляем его
                if (!found)
                {
                    if (value)
                        list[list.length] = name.concat('=', value);
                }
                // заменяеи модмассив на строку и записываем в массив параметров
                params = list.join('&');
            }
            // если параметров еще не было, тогда добавляем первый
            else
            {
                if (value)
                    params = params.concat(name, '=', value);
            }

            this.clearData();
            this.queryCache.clear();
            this.config.options.extraParams = params;
        }
    },

    append2CustomPlace : function(element)
    {
        this.elements.content.appendChild(element);
    },

    setConfig : function (q3config)
    {
        var _this  = this;
        var config = this.config;

        this.reWriteObject(config, q3config, false);

        if (config.z_index)
        {
            $(this.elements.searchSelectShadow).css('z-index', this.config.z_index);
        }

        this.config.options.onItemSelect = function (element)
        {
            _this.onItemSelectListener(element);
        };

        this.config.options.formatItem = function (row, i, num)
        {
            return _this.formatItem(row, i, num);
        };

        this.config.options.loadFromCache = function (query)
        {
            return _this.loadFromCache(query);
        };

        this.config.options.addToCache = function(query, data)
        {
            return _this.addToCache(query, data);
        };

        this.config.options.parseData = function(data, query)
        {
            return _this.parseData(data, query);
        };

        this.config.options.append2CustomPlace = function(element)
        {
            _this.append2CustomPlace(element);
        };

        this.config.options.customAutoSize = function ()
        {
            _this.customAutoSize();
        };

        this.config.options.moveScroll = function (step)
        {
            _this.moveScroll(step);
        };

        this.elements.searchInput.onfocus = function()
        {
            _this.selected = null;
        };

        this.elements.searchInput.onblur = function()
        {
            if (!_this.selected)
            {
                var found = _this.search(this.value, _this.data.getAll());
                if (found && found.length == 1)
                {
                    var id    = found[0][_this.dataOptions.keyField];
                    var value = found[0][_this.dataOptions.valueField];

                    if (this.value.length == value.trim().length)
                    {
                        var _elem = document.getElementById(id);
                        if (_elem)
                        {
                            _elem.parentNode.click(window.event);
                        }

                        _this.onComplete(found[0]);
                    }
                }
            }
        };
    },

    moveScroll : function(step)
    {
        $(this.elements.customScrollBox).trigger('mousewheel', [0 - step, 0 - step]);
    },

    customAutoSize : function()
    {
        var content     = this.elements.content;
        var container   = this.elements.liveSearchResultContainer;
        var dagger      = this.elements.dragger_container;
        var blockScroll = false;

        if (content.offsetHeight == 0)
        {
            dagger.style.height    = '0';
            container.style.height = '0';
        }
        else if (content.offsetHeight < container.offsetHeight)
        {
            if (container.offsetHeight > this.config.maxHeight)
            {
                dagger.style.height    = this.config.maxHeight + 'px';
                container.style.height = this.config.maxHeight + 'px';
            }
            else
            {
                dagger.style.height    = content.offsetHeight + 1 + 'px';
                container.style.height = content.offsetHeight + 1 + 'px';

                blockScroll = true;
            }

            $(this.elements.liveSearchResultContainer).mCustomScrollbar("vertical", 0, "easeOutCirc", 1, "auto", "yes", "no", 0);

            if (blockScroll)
            {
                $(this.elements.customScrollBox).unbind('mousewheel');
            }
        }
        else
        {
            if (content.offsetHeight < this.config.maxHeight)
            {
                dagger.style.height    = content.offsetHeight + 1 + 'px';
                container.style.height = content.offsetHeight + 1 + 'px';
            }
            else
            {
                dagger.style.height    = this.config.maxHeight + 'px';
                container.style.height = this.config.maxHeight + 'px';
            }

            $(this.elements.liveSearchResultContainer).mCustomScrollbar("vertical", 0, "easeOutCirc", 1, "auto", "yes", "no", 0);
        }
    },

    isRemote : function()
    {
        var url = this.config.options.url;
        return !(url == null || url === undefined || url.length == 0);
    },

    requestEnabled : function()
    {
        if (this.requiredParameters.length == 0)
        {
            return true;
        }

        var params = this.config.options.extraParams;
        if (params && params.length > 0)
        {
            var list  = params.split('&');
            var pairs = [];

            for (var i=0; i<list.length; i++)
            {
                pairs.push( list[i].split('=') );
            }

            for (var x=0; x<this.requiredParameters.length; x++)
            {
                var has = false;

                for (var y=0; y<pairs.length; y++)
                {
                    if (this.requiredParameters[x] == pairs[y][0])
                    {
                        has = true;

                        if (!pairs[y][1] || pairs[y][1] === undefined)
                        {
                            return false;
                        }
                    }
                }

                if (!has)
                {
                    return false;
                }
            }

            return true;
        }
        else
        {
            return this.requiredParameters.length == 0;
        }
    },

    setRequiredParameters : function(parameters)
    {
        if (parameters && parameters instanceof Array)
        {
            this.requiredParameters = parameters;
        }
    }
};