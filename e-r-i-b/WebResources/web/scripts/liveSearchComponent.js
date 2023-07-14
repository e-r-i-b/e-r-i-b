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
 * Объект данного типа содержит в себе данные и методы для компонента поиска (LiveSearch).
 * @constructor
 */
var LiveSearchData = function()
{
    var data = [];

    return {

        /**
         * Возвращает все данные. Элементы индексированы не по порядку (1,2,3..n) а по некоторому ключу с которым каждый
         * элемент был добавлен
         *
         * @returns {Array}
         */
        getAll : function()
        {
            return data;
        },

        /**
         * Ищет по ключу элемент данных
         *
         * @param key ключ искомого элемента
         * @returns {*} в случае если элемент не найден возвращает null
         */
        findDataByKey : function (key)
        {
            if (data.hasOwnProperty(key))
            {
                return data[key];
            }

            return null;
        },

        /**
         * Служит для добавления нового элемента данных
         *
         * @param key ключ элемента
         * @param text текст
         * @param selected является ли этот элемент выбранным по-умолчанию
         */
        appendData : function(key, text, selected)
        {
            data[key] = {key: key, text: text, selected: (selected ? true : false)};
        }
    }
};

var LiveSearch = function()
{
    var hasSearchResult = false;

    var searchInput;
    var liveSearchValueInput;

    var dropDownContainer;
    var dropDownContainerList;

    var searchResultContainer;
    var searchResultContainerList;

    var searchSelectList;
    var liveSearchComponent;
    var searchSelectTopText;
    var searchSelect;
    var data;

    var onSelectCallback = function(selected) {};

    /**
     * Включает для элемента подсветку.
     * @param element
     */
    function enableHighlight(element)
    {
        if (element)
        {
            $(element).mouseenter(function ()
            {
                $(this).addClass('needLightHover');

            }).mouseout(function()
            {
                $(this).removeClass('needLightHover');
            });
        }
    }

    /**
     * Включает для указанного div`a пролистывание элементов.
     * Необходимо вызывать каждый раз как div отображается, если он конечно скрывается.
     *
     * @param element
     */
    function enableScrolls(element)
    {
        if (element)
        {
            element.mCustomScrollbar("vertical", 0, "easeOutCirc", 1.05, "auto", "yes", "no", 0);
        }
    }

    /**
     * Ищет среди данных, элементы, в которых свойство text совпадает или целиком с аргументом text, или частично.
     *
     * @param text строка которую необходимо искать
     * @returns {Array} массив найденных данных
     */
    function search(text)
    {
        var found   = [];
        var isEmpty = true;

        if (!text)
        {
            found['empty'] = isEmpty;
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
            var all    = data.getAll();
            var regexp = new RegExp(test, 'gi');

            for (var key in all)
            {
                if (all.hasOwnProperty(key))
                {
                    var element = all[key];
                    var match   = regexp.exec(element.text);

                    if (match)
                    {
                        isEmpty = false;

                        found[key] = {
                            key:      element.key,
                            text:     element.text.replace(regexp, replace),
                            selected: element.selected
                        };
                    }
                }
            }
        }

        /*
         * В ассоциативном массиве длину через length не получить, приходится так
         */
        found['empty'] = isEmpty;
        return found;
    }

    /**
     * Подсвечивает в строке указанную подстроку.
     * Внимание! Подстветка заключается в том, чтобы указанную подстроку обернуть в тэг <b>, фактически, текст в последующем
     * вставляется в элемент, поэтому, если будете переделывать реализацию, то думайте сперва как подсветить.
     *
     * @param text текст в котором необходимо выделить подстроку
     * @param substring выделяемая подстрока
     *
     * @returns {string}
     */
    function highlight(text, substring)
    {
        if (!substring)
        {
            return text;
        }

        var begin = text.indexOf(substring);
        var   end = begin + substring.length;
        var  bold = $(document.createElement('b')).text( text.substring(begin, end) )[0];

        return ''.concat(text.substring(0,   begin))
                 .concat(bold.outerHTML)
                 .concat(text.substring(end, text.length));
    }

    /**
     * Имеет несколько назначений:
     *
     * 1. Когда пользователь вводит поисковый запрос удаляет предыдущий результат поиска;
     * 2. При нажатии на кнопку очистки также очищает строку поиска.
     *
     * @param clearSearch параметр указывает то что необходимо очистить также поле для ввода запроса.
     */
    function clear(clearSearch)
    {
        hasSearchResult = false;

        if (clearSearch)
        {
            searchInput.val('поиск');
            searchInput.addClass("inputPlaceholder");
        }

        /*
         * Скрыть блок результатов и удалить результат поиска
         */
        searchResultContainer.css('display', 'none');
        searchResultContainerList.children().remove();
    }

    /**
     * Отрабатывает при выборе какого-либо элемента в списке
     */
    function listElementClick(id, parent)
    {
        var selected = data.findDataByKey(id);
        if (selected)
        {
            if (parent)
            {
                parent.css('display', 'none');
            }

            liveSearchValueInput.val(selected.key);
            searchSelectTopText.text(selected.text);

            onSelectCallback(selected);
            clear(true);
        }
    }

    /**
     * Отрабатывает при нажатии клавиши.
     * Получает введенную пользователем строку и отсылает для поиска, в случае найденных результатов заполняет блок
     * результатов.
     *
     * @param value введенная пользователем строка
     */
    function searchInputOnKeyUp(value)
    {
        clear(false);

        var found = search( value );
        if (found['empty'] == true)
        {
            hasSearchResult = false;
            return;
        }
        else
        {
            hasSearchResult = true;
        }

        for (var key in found)
        {
            if (found.hasOwnProperty(key))
            {
                if (key == 'empty')
                {
                    continue;
                }

                var element = createResultElement(key, found[key].text);

                $(element).appendTo(searchResultContainerList);
                $(element).click(function()
                {
                    listElementClick(this.id, searchSelectList);
                });

                enableHighlight(element);
            }
        }

        /*
         * Отображаем блок с результатами если что-нибудь найдено
         */
        searchResultContainer.css('display', '');
        enableScrolls(searchResultContainer);
    }

    /**
     * При потере фокуса проверяется результат поиска, если есть результатов нет - то строка поиска очищается, в противном
     * случае очистка строки поиска не производится.
     *
     * @param forceClear выполнять очистку строки поиска в любом случае найдено что-либо или нет
     */
    function searchFocusOut(forceClear)
    {
        if (!forceClear && hasSearchResult)
        {
            return;
        }

        clear(true);
    }

    /**
     * Обрабатывает когда курсор покидает компонент, при этом должны выполняться следующие действия:
     *
     * 1. Если поле поиска заполнено и есть найденные данные блок содержщий результат поиска и строку поиска не скрывается;
     * 2. Если поле поиска заполнено и нет найденных данных строка поиска скрывается, при этом она автоматически должна будет
     * очищена;
     * 3. Если поле поиска не заполненно строка поиска скрывается (в сущности тоже что и 2ой пункт).
     */
    function liveSearchComponentLeave()
    {
        if (!hasSearchResult)
        {
            searchSelectList.css('display', 'none');
            clear(true);
        }
    }

    /**
     * Просто отображает блок со строкой поиска и результатами поиска
     */
    function liveSearchComponentEnter()
    {
        if (dropDownContainer.css('display') == 'none')
        {
            searchSelectList.css('display', '');
        }
    }

    /**
     * Функция отрабатывает когда нажата стрелка декоративного select'а и выполняет следующие действия:
     * 1. Если строка поиска и результат поиска показаны пользователю, то эти компоненты скрываются и при этом выполняется
     * их очистка;
     * 2. Если строка поиска и результат поиска были скрыты, то они отображаются пользователю.
     */
    function searchSelectClick()
    {
        if (searchSelectList.css('display') == 'none')
        {
            searchSelectList.css('display',  '');
            dropDownContainer.css('display', 'none');
        }
        else
        {
            searchSelectList.css ('display', 'none');
            dropDownContainer.css('display', '');

            enableScrolls(dropDownContainer);
            clear(true);
        }
    }

    /**
     * Создает элемент списка
     *
     * @param id
     * @param text
     * @returns {HTMLElement}
     */
    function createResultElement(id, text)
    {
        var li = document.createElement('li');
        var a  = document.createElement('a');

        $(li).attr('id', id);
        $(a).html( text );
        $(a).appendTo(li);

        return li;
    }

    return {

        /**
         *
         * @param parentId
         * @param liveSearchData данные для заполнения компонента. Данный аргумент должен быть объектом типа LiveSearchData
         * @param properties дополнительные свойства компонента. Данный компонент должен быть объектом. Может включать
         * следуюющие поля:
         *
         * liveSearchValueInputName - в данном поле хранится название для аттрибута name hidden элемента, который будет
         * передавать значение выбранного в компоненте элемента.
         */
        init : function(parentId, liveSearchData, properties)
        {
            if (!parentId)
            {
                throw new Error('Необходимо указать id элемента в который обёрнут компонент!');
            }

            if (liveSearchData && typeof liveSearchData !== 'object')
            {
                throw new Error('Параметр liveSearchData должен быть объектом!');
            }

            if (properties && typeof properties !== 'object')
            {
                throw new Error('Параметр properties должен быть объектом!');
            }

            liveSearchComponent       = $('#' + parentId).find('.liveSearchComponent');
            liveSearchValueInput      = $(liveSearchComponent).find('.liveSearchValueInput');

            searchSelectList          = $(liveSearchComponent).find('.liveSearchSelectList');
            searchInput               = $(liveSearchComponent).find('.searchInput');
            searchResultContainer     = $(liveSearchComponent).find('.liveSearchResultContainer');
            searchResultContainerList = $(searchResultContainer).find('.searchBlockList > ul');

            searchSelect              = $(liveSearchComponent).find('.liveSearchSelect');
            searchSelectTopText       = $(liveSearchComponent).find('.liveSearchSelectTopText');
            dropDownContainer         = $(liveSearchComponent).find('.liveSearchDropDownContainer');
            dropDownContainerList     = $(dropDownContainer).find('.searchBlockList > ul');

            if (liveSearchData)
            {
                this.setLiveSearchData(liveSearchData);
            }

            if (properties)
            {
                if (properties.hasOwnProperty('liveSearchValueInputName'))
                {
                    liveSearchValueInput.attr('name', properties.liveSearchValueInputName);
                }
            }

            liveSearchComponent.mouseenter(function ()
            {
                liveSearchComponentEnter();

            }).mouseleave(function()
            {
                liveSearchComponentLeave();
            });

            searchSelect.click(function ()
            {
                searchSelectClick();
            });

            searchInput.keyup(function()
            {
                searchInputOnKeyUp($(this).val());

            }).focusout(function()
            {
                searchFocusOut(false);
            });

            $('.roundCloseIcon').click(function()
            {
                clear(true);
            });

            customPlaceholder.init();
        },

        setLiveSearchData : function (liveSearchData)
        {
            data = liveSearchData;

            if (data)
            {
                var all = data.getAll();
                var sel = null;

                for (var key in all)
                {
                    if (all.hasOwnProperty(key))
                    {
                        var element = createResultElement(key, all[key].text);

                        if (all[key].selected)
                        {
                            sel = element;
                        }

                        $(element).appendTo(dropDownContainerList);
                        $(element).click(function()
                        {
                            listElementClick(this.id, dropDownContainer);
                        });

                        enableHighlight(element);
                    }
                }

                if (sel)
                {
                    listElementClick(sel.id, null);
                }
            }
        },

        setOnSelectCallback : function(callback)
        {
            if (callback && typeof callback == 'function')
            {
                onSelectCallback = callback;
            }
        }
    }
};