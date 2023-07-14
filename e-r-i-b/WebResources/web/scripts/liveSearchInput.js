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
 * ��������� ��� �������������� ��-���������
 * @constructor
 */
function DEFAULT_AUTO_COMPLETE_OPTIONS()
{
    /*
     * URL ��������� ������
     */
    this.url = null;

    /*
     * ������ �������� �������������� ��������� �������
     */
    this.extraParams = '';

    /*
     * CSS ����� ���� ��� ����� �������
     */
    this.inputClass = '';

    /*
     * ����������� ����� ������ ��������� � �������. �� ������������ ���� url �� ����������
     */
    this.lineSeparator = '@';

    /*
     * ����������� �����. �� ������������ ���� url �� ����������
     */
    this.cellSeparator = '|';

    /*
     * �������� ������� �������� ������� �� ������. ������ ������������ �� ������ � ������� ����� ������������
     * �������� ���� � ���� + ����� ��������� � ���������� delay
     */
    this.delay = 0;

    /*
     * ����������� ����� ������� ����� �������� ���������� �����
     */
    this.minChars = 3;

    /*
     * CSS ����� ������ �����������
     */
    this.resultsClass = 'searchBlockList';

    /*
     * ��������� ��������� ������(��� �� ������ ������ ���������� � �������)
     */
    this.data = new LiveSearchInputData();

    /*
     * ����� ������ ������������� ������� �������� ������ �������� ������������ ������
     */
    this.customAutoSize = function() {};

    /**
     * ����� ����������� ����������� ������� � ����������� ������ ��� �������������
     */
    this.moveScroll = function() {};

    /*
     * ����� ������ ��� ����������� ���������������
     * ������ ����� ����������� ����� ���� ��� ���������� parseData ������������ � autocomplete.js
     */
    this.parseData = function(data, query) {};

    /*
     * ����� ������ ��� ����������� ���������������.
     * ������ ����� ���������� ������ autocomplete.js � ������ ���������� html-������� ������� ����� �����
     * ������������ � ������ ����������� ������.
     */
    this.formatItem = function(row, i, num) {};

    /*
     * ����� ������ ��� ����������� ���������������, ��������� ��� ������������ � autocomplete.js � ��� ����
     * ����� �� ��� ���� ����������� �� ������������.
     */
    this.addToCache = function(query, data) {};

    /*
     * ����� ������ ��� ����������� ���������������.
     * ���������� ��� ������ ������-���� �������� �� ������ ���������
     */
    this.onItemSelect = function(element)     {};

    /*
     * ����� ������ ��� ����������� ���������������.
     * ������ ���������� ������ �� ����.
     */
    this.loadFromCache = function(query) {};

    /*
     * ������ ����� ���������� ���� ����� �������� html ������� �������� ��� ����������� �����������
     * ������.
     */
    this.append2CustomPlace = function(element) {};

    /*
     * ������������� ��������� �� ��������, ���� ��� ��������� ��� ��� ������.
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
     * ���������� ��� ������.
     *
     * @returns {Array}
     */
    getAll : function()
    {
        return this.data;
    },

    /**
     * ���� �� �������� ��������� ���� ������� ������. � �������� ��������� ���� ��������� ���� � ������
     * ��������� � options.keyField
     *
     * @param   key ���� �������� ��������
     * @returns {Object} � ������ ���� ������� �� ������ ���������� null
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
     * ������ ��� ���������� ������ �������� ������
     *
     * @param {Object} value ������ � ������� { [options.keyField]: 'value', [options.valueField]: 'value', [someOther]: 'value', ...}
     */
    appendData : function(value)
    {
        this.data.push(value);
    },

    /**
     * ������� ������
     */
    clear : function ()
    {
        this.data = [];
    }
};

/**
 * ������ ����� ��������� ���� � ����� �������.
 *
 * ���� ���������������, ��� ������ ����� ��������� � ������-���� �������, �� ���������� ���������
 * ��� ������ ���������� ������� �� �������� � ��������� �������:
 *
 * [��� ���� ������ 1]:��������|[��� ���� ������ 1]:��������|...@[��� ���� ������ 2]:��������|[��� ���� ������ 2]:��������|...@...
 * ������ ������ ����� � ������ ��������:
 * [{
 *    [��� ���� ������ 1] : ��������,
 *    [��� ���� ������ 1] : ��������,
 *    ...
 *  },
 *  {
 *    [��� ���� ������ 2] : ��������,
 *    [��� ���� ������ 2] : ��������,
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

            // ���� ����� ������ ��� ��� - ���� ����� ��������� ������
            if (cache.contain(query))
            {
                return this.search(query, this.data.getAll());
            }
            //���� ������ ������� ��� �� ���� - ����� ������ ������ �� ������
            else
            {
                // ��������� ��������� ���������
                this.clearData();
                return null;
            }
        }
        // ���� �� �������������� ajax-�������� ��� ��������� ������ �� ����� ������ ������ ����� ��������� ������
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
         * �������� �� ������ ������ + ����������� �� �������� ����� ����
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
     * ���������, ��������, ��� ������� �������� ������ ajax �������
     * @param name ��� ���������
     * @param value �������� ��������� (���� �������� �� ����������, �� �������� ���������)
     */
    addParameter : function(name, value)
    {
        if (name && typeof name == 'string')
        {
            var params  = this.config.options.extraParams;
            // ���� ��������� ����������
            if (params)
            {
                // ����������� ������ ���������� � ������
                var list  = params.split('&');
                // ������� ����, ��� �������� ��� ����������
                var found = false;

                // ���� �� �������, ��� ���������� �������� ���� - name=value
                for (var i=0; i<list.length; i++)
                {
                    // ��������� ������� ������� �� ���������, ��� ������ ������� ��� ���������, ������, ��� ��������
                    var pair = list[i].split('=');
                    // ���� ��� ��������� ����� ��������, ����� �������� ��� �������� �� �����
                    if (pair[0] == name)
                    {
                        pair[1] = value;
                        found   = true;
                    }
                    // ���� �������� ��������� �� ����������, �� ������� �������� �� ������ ����������
                    if (!value && found)
                    {
                        list.splice(i, 1);
                        break;
                    }
                    // �������� ��������� �� ������ � ���������� � ������ ����������
                    list[i] = pair.join('=');
                }

                // ���� �������� �� ����������, ����� ��������� ���
                if (!found)
                {
                    if (value)
                        list[list.length] = name.concat('=', value);
                }
                // �������� ��������� �� ������ � ���������� � ������ ����������
                params = list.join('&');
            }
            // ���� ���������� ��� �� ����, ����� ��������� ������
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